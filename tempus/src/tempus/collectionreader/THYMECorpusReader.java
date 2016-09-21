package tempus.collectionreader;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tempus.type.DocTime;
import tempus.type.DocumentCreationTime;
import tempus.type.Event;
import tempus.type.Section;
import tempus.type.SectionTime;
import tempus.type.TemporalLink;
import tempus.type.TimeRelationAnnotationElement;
import tempus.type.Timex3;
//CHANGED type system for TIMEX3

/**
 * @author Sumithra Velupillai, based on readers from the HeidelTime package
 *
 */
public class THYMECorpusReader extends CollectionReader_ImplBase {

	private Logger logger = Logger.getLogger(THYMECorpusReader.class);
	// uima descriptor parameter name
	private static String PARAM_INPUTDIR = "InputDirectory";
	private static String PARAM_INPUTDIR_SOURCE = "InputDirectorySourceFiles";
	private static String PARAM_TRAINING = "Training";

	private boolean training = true;
	private Integer numberOfDocuments = 0;
	private Integer numberOfSourceDocuments = 0;

	private Queue<File> files = new LinkedList<File>();
	private HashMap<String, File> source_files = new HashMap<String, File>();

	public static CollectionReader getCollectionReader(
			String dataDirectories,
			String sourceFileNames,
			String training) throws ResourceInitializationException {

		return CollectionReaderFactory.createReader(
				THYMECorpusReader.class,
				null,
				PARAM_INPUTDIR,
				dataDirectories,
				PARAM_INPUTDIR_SOURCE,
				sourceFileNames,
				PARAM_TRAINING,
				training);
	}

	public static CollectionReaderDescription getCollectionReaderDescription(
			String dataDirectories,
			String sourceFileNames,
			String training) throws ResourceInitializationException {
		return CollectionReaderFactory.createReaderDescription(
				THYMECorpusReader.class,
				null,
				PARAM_INPUTDIR,
				dataDirectories,
				PARAM_INPUTDIR_SOURCE,
				sourceFileNames,
				PARAM_TRAINING,
				training);
	}

	public void initialize() throws ResourceInitializationException {
		String dirPath = (String) getConfigParameterValue(PARAM_INPUTDIR);
		dirPath = dirPath.trim();

		populateFileList(dirPath);

		String source_dirPath = (String) getConfigParameterValue(PARAM_INPUTDIR_SOURCE);
		source_dirPath = source_dirPath.trim();
		populateSourceFileList(source_dirPath);
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

		try {
			fillJCas(jcas);
		} catch (ResourceInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// give an indicator that a file has been processed
		//System.err.print(".");

	}

	private void fillJCas(JCas jcas) throws IOException, ResourceInitializationException, ParserConfigurationException, SAXException {
		// grab a file to process
		File f = files.poll();
		//System.out.println(files);

		//TODO: this needs to be more general
		String strippedname = f.getName();
		strippedname = strippedname.replaceAll(".Temporal-Entity-Adjudication.gold.completed.xml", "");
		strippedname = strippedname.replaceAll(".Gold_Temporal_Relations.xml", "");
		strippedname = strippedname.replaceAll(".Gold_Temporal_Entities.xml", "");
		strippedname = strippedname.replaceAll(".Temporal-Relation.gold.completed.xml", "");
		strippedname = strippedname.replaceAll(".Temporal-Entity.gold.completed.xml", "");
		strippedname = strippedname.replaceAll(".THYME_TIMEX3_mapped_training.gold.completed.xml", "");
		strippedname = strippedname.replaceAll("i2b2_training.gold.completed.xml", "xml\\.txt");

		File s_file = source_files.get(strippedname);

		logger.info("File: "+f.getName());
		logger.info(", source file: "+s_file.getName());


		if(!s_file.exists()){
			throw new ResourceInitializationException();
		}
		else{

			// read in source text to inputstream

			InputStream inputStream = new FileInputStream(s_file);



			byte[] source_bytes = IOUtils.toByteArray(inputStream);
			InputStream inputStream_temp = new FileInputStream(s_file);
			String sourceTextString = IOUtils.toString(inputStream_temp, "UTF-8");

			jcas.setDocumentText(sourceTextString);

			fillJCasWithSections(jcas);

			setDocTime(jcas, f.getName());

			//add training instances from xml if training
			if(this.training)
				fillJCasWithAnnotations(jcas, f, source_bytes);





			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(inputStream_temp);

		}// end else - file exists


	}






	public boolean hasNext() throws IOException, CollectionException {
		// TODO Auto-generated method stub
		return files.size() > 0;
	}

	public Progress[] getProgress() {
		// TODO Auto-generated method stub
		return new Progress[] { new ProgressImpl(numberOfDocuments-files.size(), numberOfDocuments , Progress.ENTITIES) };
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		files.clear();

	}

	private void populateFileList(String dirPath) throws ResourceInitializationException {

		ArrayList<File> myFiles = new ArrayList<File>();
		File dir = new File(dirPath);


		// check if the given directory path is valid
		if(!dir.exists() || !dir.isDirectory())
			throw new ResourceInitializationException();
		else
			myFiles = getAllAnnotationFiles(myFiles, dir);

		// check for existence and readability; add handle to the list
		for(File f : myFiles) {
			if(!f.exists() || !f.isFile() || !f.canRead() || f.getName().startsWith("._")) {
				logger.error("File \""+f.getAbsolutePath()+"\" was ignored because it either didn't exist, wasn't a file or wasn't readable.");
			} else {
				files.add(f);
			}
		}


		numberOfDocuments = files.size();
	}

	private ArrayList<File> getAllAnnotationFiles(ArrayList<File> allFiles, File filepath){

		for(File f : filepath.listFiles()){
			if(f.isDirectory())
				allFiles = getAllAnnotationFiles(allFiles, f);
			else{
				//TODO: this is not a good check
				if(f.getName().endsWith(".xml"))
					//if(f.getName().endsWith("Temporal-Relation.gold.completed.xml") || f.getName().endsWith("Temporal-Entity.gold.completed.xml"))
					allFiles.add(f);
			}
		}
		return allFiles;

	}

	private void populateSourceFileList(String dirPath) throws ResourceInitializationException {

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
			} else {
				String filename = f.getName();
				source_files.put(filename, f);
			}
		}
		//System.out.println(source_files);
		numberOfSourceDocuments = files.size();
	}

	// get a specific source file given an annotation file name
	// TODO: remove hardcoded string values to remove from file name?
	private File getSourcefile(File f, File sourcepath){
		File sourcefile;
		String strippedname = f.getName();
		strippedname = strippedname.replaceAll(".Temporal-Entity-Adjudication.gold.completed.xml", "");
		strippedname = strippedname.replaceAll(".Gold_Temporal_Relations.xml", "");
		strippedname = strippedname.replaceAll(".Gold_Temporal_Entities.xml", "");
		strippedname = strippedname.replaceAll(".Temporal-Relation.gold.completed.xml", "");
		strippedname = strippedname.replaceAll(".Temporal-Entity.gold.completed.xml", "");
		sourcefile = new File(sourcepath.getAbsolutePath(), strippedname);
		return sourcefile;
	}


	// loop through node list to find a specific node name
	private String getNamedChild(NodeList n, String name){
		boolean found = false;

		for(int i = 0; i < n.getLength(); i++){
			Node node = n.item(i);
			if(node.getNodeName().equals(name)){
				found = true;
				return node.getTextContent();
			}
		}
		if(!found){
			logger.error("Something is wrong");
		}
		return null;
	}

	// get a node list of children given a specific name
	private NodeList getNamedChildNodeList(NodeList n, String name){

		for(int i = 0; i < n.getLength(); i++){
			Node node = n.item(i);
			if(node.getNodeName().equals(name))
				return node.getChildNodes();
		}
		return null;
	}

	public Queue<File> getAnnotatedFiles(){
		return this.files;
	}

	public HashMap<String, File> getSourceFiles(){
		return this.source_files;
	}

	private void setDocTime(JCas jcas, String filename){
		String text = jcas.getDocumentText();
		String[] textlines = text.split("\n");
		for(String line : textlines){
			if(line.contains("start_date")){
				String doctime = line;
				doctime = line.replaceAll(".*?start_date=\"", "");
				//System.out.println("DOCTIME: "+doctime);
				//doctime = doctime.replaceAll("start_date=\"", "");
				//System.out.println("DOCTIME: "+doctime);
				doctime = doctime.replaceAll("\".*", "");
				//System.out.println("DOCTIME: "+doctime);
				DocumentCreationTime dct = new DocumentCreationTime(jcas);
				//System.out.println("BEGIN: "+text.indexOf("start_date=\""+doctime));
				//System.out.println("END: "+(text.indexOf("start_date=\""+doctime)+doctime.length()));
				dct.setBegin(text.indexOf("start_date=\""+doctime)+12);
				dct.setEnd((text.indexOf("start_date=\""+doctime)+doctime.length()+12));
				dct.setFilename(filename);
				dct.setValue(doctime);
				dct.setTimexId("dct0");
				dct.addToIndexes();

			}
		}
	}

	private void fillJCasWithSections(JCas jcas){
		String text = jcas.getDocumentText();
		String[] textlines = text.split("\n");
		int position = 0;
		HashMap<String, Integer[]> sections = new HashMap<String, Integer[]>();
		for(String line : textlines){
			position += line.length()+1;
			if(line.contains("start section id")){
				String id = line.replaceAll("\\[start section id\\=\"", "");
				id = id.replaceAll("\"]", "");
				//System.out.println("Section id: "+id);

				Integer[] beginend;
				if(sections.containsKey(id))
					beginend = sections.get(id);
				else{
					beginend = new Integer[2];
				}
				beginend[0] = position;
				sections.put(id, beginend);
				//Section section = new Section(jcas);
				//section.setId(id);
				//section.setBegin(position);
			}
			if(line.contains("end section id")){
				String id = line.replaceAll("\\[end section id\\=\"", "");
				id = id.replaceAll("\"]", "");
				Integer[] beginend;
				if(sections.containsKey(id))
					beginend = sections.get(id);
				else{
					beginend = new Integer[2];
				}
				if(position>text.length())
					position = text.length();
				beginend[1] = position;
				sections.put(id, beginend);	
			}
		}
		Set<String> unannotated_sections = new TreeSet<String>();
		//unannotated_sections.add("20104");
		//unannotated_sections.add("20105");
		//unannotated_sections.add("20116");
		//unannotated_sections.add("20138");
		for(Map.Entry<String, Integer[]> me : sections.entrySet()){
			String id = me.getKey();
			Integer[] positions = me.getValue();
			// TODO: REMOVE UNANNOTATED SECTIONS ALREADY HERE
			if(unannotated_sections.contains(id))
				System.out.println("IGNORING SECTION: "+id);
			else{
				//System.out.println("NOW INCLUDING SECTION: "+id);
				Section section = new Section(jcas);
				section.setId(id);
				if(positions[0] == null)
					positions[0] =0;
				section.setBegin(positions[0]);
				if(positions[1]==null)
					positions[1] = jcas.getDocumentText().length();
				section.setEnd(positions[1]);
				section.addToIndexes();
			}
		}
	}

	private void fillJCasWithAnnotations(JCas jcas, File f, byte[] source_bytes) throws ParserConfigurationException, SAXException, IOException{
		// parse annotation xml-file
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document doc = db.parse(f);

		doc.getDocumentElement().normalize();

		// get the entities
		NodeList nList = doc.getElementsByTagName("entity");

		// Save all timexes and events for relations
		TreeMap<String, Timex3> timexes = new TreeMap<String, Timex3>();
		TreeMap<String, Event> events = new TreeMap<String, Event>();

		TreeMap<String, TimeRelationAnnotationElement> relationElement = new TreeMap<String, TimeRelationAnnotationElement>();


		// loop over all entities
		for(int n = 0; n < nList.getLength(); n++){
			Node textNode = nList.item(n);
			// get children
			NodeList children = textNode.getChildNodes();

			// get specific nodes
			String id = getNamedChild(children, "id");
			String span = getNamedChild(children, "span");
			// parse span offsets
			String[] spans;
			if(span.contains(";")){
				spans = span.split(";");
				String[] spans2 = spans[0].split(",");
				spans = spans2;
				//logger.log(Level.WARN, "MULTIPLE SPANS! Not handled correctly at the moment. File: "+f.getName()+", id: "+id+", spans: "+span);
			}
			else
				spans = span.split(",");
			Integer span1 = Integer.parseInt(spans[0]);
			Integer span2 = Integer.parseInt(spans[1]);



			// default string value for cases that are out of bounds
			String textstring = "OUT OF BOUNDS";
			if(source_bytes.length>span2)
				textstring = new String(source_bytes, span1, span2-span1);
			//else
			//logger.log(Level.WARN, "OUT OF BOUNDS: "+f.getAbsolutePath()+": "+id);
			//logger.log(Level.INFO, "File: "+f.getName()+": "+textstring);

			String surrounding_text = "TEST";
			Integer surrounding_span1 = span1-30;
			Integer surrounding_span2 = span2+30;

			if(source_bytes.length>surrounding_span2 && surrounding_span1>0)
				surrounding_text = new String(source_bytes, surrounding_span1, surrounding_span2-surrounding_span1);

			String entity_type = getNamedChild(children, "type");

			// In the annotation files, there are also xml nodes named "adjudication", 
			// not sure which to use in these cases, 
			// have chosen to use only nodes under "annotations"
			String parent_type = textNode.getParentNode().getNodeName();


			if(entity_type.equals("EVENT") && parent_type.equals("annotations")){
				// event nodes have properties children - save all these
				NodeList childnodes = getNamedChildNodeList(children, "properties");
				if(childnodes == null)
					logger.error(f.getAbsolutePath()+": "+id);
				String dtr = getNamedChild(childnodes, "DocTimeRel");
				String event_type = getNamedChild(childnodes, "Type");
				String degree = getNamedChild(childnodes, "Degree");
				String polarity = getNamedChild(childnodes, "Polarity");
				String contextual_modality = getNamedChild(childnodes, "ContextualModality");
				String contextual_aspect = getNamedChild(childnodes, "ContextualAspect");
				String permanence = getNamedChild(childnodes, "Permanence");
				// create a THYME_Entity_Event with all info

				Event e = new Event(jcas);
				e.setBegin(span1);
				e.setEnd(span2);
				e.setContextualaspect(contextual_aspect);
				e.setId(id);
				e.setPolarity(polarity);
				e.setContextualmodality(contextual_modality);
				e.setDegree(degree);
				e.setDoctimerel(dtr);
				e.setEventClass(event_type);
				e.setPermanence(permanence);
				//TODO: MAKE SURE ALL ATTRIBUTES ARE SET - CHANGE TYPE SYSTEM!
				e.addToIndexes();
				// TODO: could this be removed and use below instead?
				events.put(id, e);
				relationElement.put(id, e);

				//THYME_Entity_Event tee = new THYME_Entity_Event(id, entity_type, dtr, 
				//		event_type, degree, polarity, contextual_modality, 
				//		contextual_aspect, permanence, textstring, span1, span2);


			}
			else if(entity_type.equals("TIMEX3")&& parent_type.equals("annotations")){
				NodeList childnodes = getNamedChildNodeList(children, "properties");
				// currently, TIMEX annotations only contain type information, 
				// no normalized values
				String timex_type = getNamedChild(childnodes, "Class");



				Timex3 tm = new Timex3(jcas);
				tm.setBegin(span1);
				tm.setEnd(span2);
				tm.setTimex3Type(timex_type);

				tm.addToIndexes();
				timexes.put(id, tm);
				relationElement.put(id, tm);

			}
			else if(entity_type.equals("SECTIONTIME")&& parent_type.equals("annotations")){

				SectionTime st = new SectionTime(jcas);
				st.setBegin(span1);
				st.setEnd(span2);
				st.setId(id);

				st.addToIndexes();
				relationElement.put(id, st);


			}
			else if(entity_type.equals("DOCTIME")&& parent_type.equals("annotations")){

				DocTime st = new DocTime(jcas);
				st.setBegin(span1);
				st.setEnd(span2);
				st.setId(id);

				st.addToIndexes();
				relationElement.put(id, st);


			}
			else{
				logger.error("SOMETHING IS WRONG, A TYPE HAS NOT BEEN ADDED: "+id);

			}


		}// end for-loop entity


		// get the relations
		NodeList nListRelations = doc.getElementsByTagName("relation");


		// loop over all relations
		for(int n = 0; n < nListRelations.getLength(); n++){
			Node textNode = nListRelations.item(n);
			// get children
			NodeList relationchildren = textNode.getChildNodes();

			// get specific nodes
			String id = getNamedChild(relationchildren, "id");

			String relation_type = getNamedChild(relationchildren, "type");
			//System.out.println(relation_type);

			// In the annotation files, there are also xml nodes named "adjudication", 
			// not sure which to use in these cases, 
			// have chosen to use only nodes under "annotations"
			String parent_type = textNode.getParentNode().getNodeName();


			if(parent_type.equals("annotations")){
				// relation nodes have properties children - save all these
				NodeList childnodes = getNamedChildNodeList(relationchildren, "properties");
				//if(childnodes == null)
				//	logger.log(Level.WARN, f.getAbsolutePath()+": "+id);
				String source = getNamedChild(childnodes, "Source");
				String relation_subtype = getNamedChild(childnodes, "Type");
				String target = getNamedChild(childnodes, "Target");
				
				TimeRelationAnnotationElement to = relationElement.get(target);
				TimeRelationAnnotationElement from = relationElement.get(source);
				
				//TODO: could this be removed?
//				Timex3 tmptimexto = timexes.get(target);
//				Event tmpeventto = events.get(target);
//				TimeRelationAnnotationElement to = null;
//				if(tmptimexto instanceof Timex3)
//					to = tmptimexto;
//				else
//					to = tmpeventto;
//				if(to==null){
//					logger.error("Target entity is null! Probably SectionTime");
//				}
//				Timex3 tmptimexfrom = timexes.get(source);
//				Event tmpeventfrom = events.get(source);
//				TimeRelationAnnotationElement from = null;
//				if(tmptimexfrom instanceof Timex3)
//					from = tmptimexfrom;
//				else
//					from = tmpeventfrom;
//				if(from==null){
//					logger.error("Source entity is null! Probably SectionTime");
//				}
				// create a THYME_Relation with all info
				//THYME_Relation tr = new THYME_Relation(id, relation_type, source, target, relation_subtype);

				//TODO: Sectiontime (and more?) are sometimes part of TLINKS, these are not read in at the moment
				if(from!=null&&to!=null){
					TemporalLink tl = new TemporalLink(jcas);
					tl.setTemporalLinkType(relation_subtype);
					tl.setTemporalLinkSuperType(relation_type);
					tl.setFromId(from);
					tl.setToId(to);
					tl.setId(id);
					tl.setBegin(from.getBegin());
					tl.setEnd(to.getEnd());
					tl.addToIndexes();
				}
				else{
					if(from==null)
						logger.error("Source entity is null!");
					else if(to==null)
						logger.error("Target entity is null!");
					else
						logger.error("Something wrong with relation assignment");
				}




			}

		}// end for-loop relation



	}
}



