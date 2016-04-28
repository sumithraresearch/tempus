/**
 * 
 */
package tempus.collectionreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tempus.type.DocumentCreationTime;
import tempus.type.Event;
import tempus.type.Section;
import tempus.type.TemporalLink;
import tempus.type.TimeRelationAnnotationElement;
import tempus.type.Timex3;


/**
 * @author Sumithra Velupillai, based on readers from the HeidelTime package
 *
 */
public class I2b2Corpus2012Reader extends CollectionReader_ImplBase {
	private Class<?> component = this.getClass();

	private Logger logger = Logger.getLogger(I2b2Corpus2012Reader.class);

	// uima descriptor parameter name
	private static String PARAM_INPUTDIR = "InputDirectory";

	private static String PARAM_TRAINING = "Training";

	private boolean training = true;

	private Integer numberOfDocuments = 0;

	private Queue<File> files = new LinkedList<File>();

	public static CollectionReader getCollectionReader(
			String dataDirectories,
			String training) throws ResourceInitializationException {

		return CollectionReaderFactory.createReader(
				I2b2Corpus2012Reader.class,
				null,
				PARAM_INPUTDIR,
				dataDirectories,
				PARAM_TRAINING,
				training);
	}

	public static CollectionReaderDescription getCollectionReaderDescription(
			String dataDirectories,
			String training) throws ResourceInitializationException {
		return CollectionReaderFactory.createReaderDescription(
				I2b2Corpus2012Reader.class,
				null,
				PARAM_INPUTDIR,
				dataDirectories,
				PARAM_TRAINING,
				training);
	}

	public void initialize() throws ResourceInitializationException {
		String dirPath = (String) getConfigParameterValue(PARAM_INPUTDIR);
		dirPath = dirPath.trim();

		populateFileList(dirPath);
		String t = (String) getConfigParameterValue(PARAM_TRAINING);
		if(t.equals("true"))
			this.training=true;
		else
			this.training = false;
	}

	public void getNext(CAS aCAS) throws IOException, CollectionException {
		JCas jcas;

		try {
			jcas = aCAS.getJCas();
		} catch (CASException e) {
			throw new CollectionException(e);
		}

		fillJCas(jcas);


		// give an indicator that a file has been processed
		//System.err.print(".");
	}

	private void fillJCas(JCas jcas) {
		// grab a file to process
		File f = files.poll();
		try {
			// create xml parsing facilities
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// parse input xml file
			Document doc = db.parse(f);

			doc.getDocumentElement().normalize();


			// get the <text> tag's content to set the document text
			NodeList nList = doc.getElementsByTagName("TEXT");
			Node textNode = nList.item(0);
			String text = textNode.getTextContent();

			

			jcas.setDocumentText(text);
			// TODO: add better handling of Sections - there are no sections in the i2b2 corpus.
			// Adding the entire document instead
			Section section = new Section(jcas);
			section.setBegin(0);
			section.setEnd(text.length());
			section.setId("S0");
			section.addToIndexes();

			// get the <dct> timex tag's value attribute for the dct
			Boolean gotDCT = false;
			String dctText = null;
			try {
				nList = doc.getDocumentElement().getElementsByTagName("SECTIME");
				//nList = ((Element) nList.item(0)).getElementsByTagName("TIMEX3"); // timex3 tag
				Node dctTimex = nList.item(0);
				//System.out.println("FIRST TIMEX?: "+nList.item(0).getAttributes().getNamedItem("dvalue"));
				//System.out.println("Storlek: "+nList.getLength());

				dctText = text;

				gotDCT = true;
			} catch(Exception e) {
				gotDCT = false;
			}
			//TODO: this should be removed
			if(!gotDCT) 
				try { // try a different location for the DCT timex element
					nList = doc.getDocumentElement().getElementsByTagName("TEXT");
					nList = ((Element) nList.item(0)).getElementsByTagName("TIMEX3"); // timex3 tag
					Node dctTimex = nList.item(0);
					NamedNodeMap dctTimexAttr = dctTimex.getAttributes();
					if(dctTimexAttr.getNamedItem("functionInDocument") != null && dctTimexAttr.getNamedItem("functionInDocument").getTextContent().equals("CREATION_TIME")) {
						Node dctValue = dctTimexAttr.getNamedItem("value");
						dctText = dctValue.getTextContent();
					}
					gotDCT = true;
				} catch(Exception e) {
					gotDCT = false;
				}

			// get the document id
			String filename = f.getName();

			System.out.println("FILENAME: "+filename);
			DocumentCreationTime dct = new DocumentCreationTime(jcas);
			dct.setBegin(0);
			dct.setEnd(text.length());
			dct.setFilename(filename);
			dct.setValue(dctText);
			dct.setTimexId("t0");
			dct.addToIndexes();

			//add training instances from xml if training
			if(this.training)
				fillJCasWithAnnotations(jcas, doc);

		} catch(Exception e) {
			logger.error("File "+f.getAbsolutePath()+" could not be properly parsed.");  
			e.printStackTrace();

		}
	}

	private void fillJCasWithAnnotations(JCas jcas, Document doc){
		
		HashMap<String, TimeRelationAnnotationElement> allRelationElements = new HashMap<String, TimeRelationAnnotationElement>();
		
		NodeList nList = doc.getElementsByTagName("EVENT");
		for(int n = 0; n < nList.getLength(); n++){
			Node i2b2Node = nList.item(n);
			// get attributes
			Element node = (Element) i2b2Node;

			// get specific nodes
			String id = node.getAttribute("id");
			String startspan = node.getAttribute("start");
			String endspan = node.getAttribute("end");
			String modality = node.getAttribute("modality");
			String polarity = node.getAttribute("polarity");
			String eventType = node.getAttribute("type");

			Integer span1 = Integer.parseInt(startspan);
			Integer span2 = Integer.parseInt(endspan);
			
			Event e = new Event(jcas);
			e.setBegin(span1);
			e.setEnd(span2);
			e.setContextualmodality(modality);
			e.setPolarity(polarity);
			e.setEventClass(eventType);
			e.setId(id);
			e.addToIndexes();
			
			TimeRelationAnnotationElement tmp = (TimeRelationAnnotationElement) e;
			allRelationElements.put(id, tmp);
			
		}
		
		//TODO: The TimeMention type is not sufficient for the i2b2 Timex3 annotations, change!
		nList = doc.getElementsByTagName("TIMEX3");
		for(int n = 0; n < nList.getLength(); n++){
			Node i2b2Node = nList.item(n);
			// get attributes
			Element node = (Element) i2b2Node;

			// get specific nodes
			String id = node.getAttribute("id");
			
			String startspan = node.getAttribute("start");
			String endspan = node.getAttribute("end");
			String val = node.getAttribute("val");
			String mod = node.getAttribute("mod");
			String t3Type = node.getAttribute("type");

			Integer span1 = Integer.parseInt(startspan);
			Integer span2 = Integer.parseInt(endspan);
			
			Timex3 tm = new Timex3(jcas);
			tm.setBegin(span1);
			tm.setEnd(span2);
			tm.setTimex3Type(t3Type);
			tm.setMod(mod);
			tm.setVal(val);
			tm.setId(id);
			tm.addToIndexes();
			TimeRelationAnnotationElement tmp = (TimeRelationAnnotationElement) tm;
			allRelationElements.put(id, tmp);
			
		}
		
				nList = doc.getElementsByTagName("TLINK");
				for(int n = 0; n < nList.getLength(); n++){
					Node i2b2Node = nList.item(n);
					// get attributes
					Element node = (Element) i2b2Node;

					// get specific nodes
					String id = node.getAttribute("id");
					String fromID = node.getAttribute("fromID");
					String toID = node.getAttribute("toID");
					String tlType = node.getAttribute("type");
					
					TimeRelationAnnotationElement fromElement = allRelationElements.get(fromID);
					TimeRelationAnnotationElement toElement = allRelationElements.get(toID);
					//System.err.println("TESTING LINKS: "+toElement.getCoveredText());
					
					TemporalLink tr = new TemporalLink(jcas);
					tr.setFromId(fromElement);
					tr.setToId(toElement);
					tr.setTemporalLinkType(tlType);
					tr.setId(id);
					tr.addToIndexes();
					
				}


	}

	public boolean hasNext() throws IOException, CollectionException {
		return files.size() > 0;
	}

	public Progress[] getProgress() {
		return new Progress[] { new ProgressImpl(numberOfDocuments-files.size(), numberOfDocuments , Progress.ENTITIES) };
	}

	public void close() throws IOException {
		files.clear();
	}

	private void populateFileList(String dirPath) throws ResourceInitializationException {
		ArrayList<File> myFiles = new ArrayList<File>();
		File dir = new File(dirPath);

		// check if the given directory path is valid
		if(!dir.exists() || !dir.isDirectory())
			throw new ResourceInitializationException();
		else
			myFiles.addAll(Arrays.asList(dir.listFiles()));

		// check for existence and readability; add handle to the list
		for(File f : myFiles) {
			if(!f.exists() || !f.isFile() || !f.canRead()) {
				logger.error("File \""+f.getAbsolutePath()+"\" was ignored because it either didn't exist, wasn't a file or wasn't readable.");
			} else if(!f.getName().endsWith("xml")){
				logger.info("Ignoring "+f.getAbsolutePath()+" - not an xml file");
			}
			else {
				// create xml parsing facilities
				DocumentBuilder db;
				try {
					db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					// parse input xml file - if invalid xml, don't include
					Document doc = db.parse(f);
					files.add(f);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					logger.error("Problem parsing xml for "+f.getAbsolutePath()+" (ParserConfigurationException)");
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					logger.error("Problem parsing xml for "+f.getAbsolutePath()+" (SAXException)");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("Problem with file "+f.getAbsolutePath());
				}
				
			}
		}

		numberOfDocuments = files.size();
	}
}
