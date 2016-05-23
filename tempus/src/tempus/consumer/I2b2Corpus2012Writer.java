package tempus.consumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import tempus.type.DocumentCreationTime;
import tempus.type.Event;
import tempus.type.TemporalLink;
import tempus.type.Timex3;

public class I2b2Corpus2012Writer extends JCasAnnotator_ImplBase {


	private Logger logger = Logger.getLogger(I2b2Corpus2012Writer.class);
	
	public static final String PARAM_OUTPUTDIR = "OutputDir";

	// counter for outputting documents. gets increased in case there is no DCT/filename info 
	private static volatile Integer outCount = 0;

	private File mOutputDir;

	public static AnalysisEngineDescription createAnnotatorDescription(String outputdir) throws ResourceInitializationException{
		//return AnalysisEngineFactory.createEngineDescription(ThymeWriter.class, ThymeWriter.PARAM_OUTPUTDIR, "/Volumes/BLULABTHYME/tiny_example_out");
		return AnalysisEngineFactory.createEngineDescription(I2b2Corpus2012Writer.class, I2b2Corpus2012Writer.PARAM_OUTPUTDIR, outputdir);
	}

	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		mOutputDir = new File((String) aContext.getConfigParameterValue(PARAM_OUTPUTDIR));

		if (!mOutputDir.exists()) {
			if(!mOutputDir.mkdirs()) {
				logger.error("Couldn't create non-existent folder "+mOutputDir.getAbsolutePath());
				throw new ResourceInitializationException();
			}
		}

		if(!mOutputDir.canWrite()) {
			logger.error("Folder "+mOutputDir.getAbsolutePath()+" is not writable.");
			throw new ResourceInitializationException();
		}
	}

	public void process(JCas jcas) {

		DocumentCreationTime dct = (DocumentCreationTime) jcas.getAnnotationIndex(DocumentCreationTime.type).iterator().next();


		String filename = dct.getFilename();

		String docString = buildDocumentToi2b2String(jcas, filename);
		writeDocument(docString, filename);

	}



	/**
	 * Creates a DOM Document filled with all of the timex3s that are in the jcas.
	 * @param jcas
	 * @param dct the document's DCT
	 * @return
	 */
	private String buildDocumentToi2b2String(JCas jcas, String filename) {
		StringBuffer docString = new StringBuffer();

		//docString.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		docString.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");

		// create the i2b2 root element
		docString.append("<ClinicalNarrativeTemporalAnnotation>\n");

		// add the text
		docString.append("<TEXT>");
		docString.append("<![CDATA[\n");
		docString.append(jcas.getDocumentText());
		docString.append("]]></TEXT>\n");


		// add TAGS

		docString.append("<TAGS>\n");


		// Events


		FSIterator it_event = jcas.getAnnotationIndex(Event.type).iterator();
		while(it_event.hasNext()) {
			Event e = (Event) it_event.next();
			String eventtext = e.getCoveredText();
			eventtext = eventtext.replaceAll("\n", "");
			// create the EVENT element
			//TODO - index should be incremented with one if file if to be readable in i2b2 annotation tool!!
			// this should be done with flag?
			docString.append("<EVENT" +
					" id=\""+
					e.getId()+
					"\" start=\""
					//+(e.getBegin()+1)+
					+(e.getBegin())+
					"\" end=\""
					//+(e.getEnd()+1)+
					+(e.getEnd())+
					"\" text=\""+
					eventtext+	
					"\" modality=\""+
					e.getContextualmodality()+	
					"\" polarity=\""+
					e.getPolarity()+
					"\" type=\""+e.getEventClass()+"\" />\n");

		}	


		
		FSIterator it_timex = jcas.getAnnotationIndex(Timex3.type).iterator();
		while(it_timex.hasNext()) {

			Timex3 e = (Timex3) it_timex.next();
			String timextext = e.getCoveredText();
			timextext = timextext.replaceAll("\n", "");

			docString.append("<TIMEX3 id=\""+
					e.getId()+"\" start=\""+
					//(e.getBegin()+1)+
					(e.getBegin())+
					"\" end=\""+
					//(e.getEnd()+1)+
					(e.getEnd())+
					"\" text=\""+
					timextext+
					"\" type=\""+
					e.getTimex3Type()+
					"\" val=\""+
					e.getVal()+
					"\" mod=\""+
					e.getMod()+"\" />\n");


		}
		
		int timerelationcounter = 0;
		FSIterator<Annotation> it_timerelation = jcas.getAnnotationIndex(TemporalLink.type).iterator();
		while(it_timerelation.hasNext()) {
			timerelationcounter++;
			TemporalLink e = (TemporalLink) it_timerelation.next();
			String fromtext = e.getFromId().getCoveredText();
			fromtext = fromtext.replaceAll("\n", "");
			String totext = e.getToId().getCoveredText();
			totext = totext.replaceAll("\n", "");
			//<TLINK id="TL64" fromID="E53" fromText="admitted" toID="E49" toText="readmitted" type="SIMULTANEOUS" />
			docString.append("<TLINK id=\""+
					"TL"+timerelationcounter+"\" fromID=\""+
					e.getFromId().getId()+
					"\" fromText=\""+
					fromtext+
					"\" toID=\""+
					e.getToId().getId()+
					"\" toText=\""+
					totext+
					"\" type=\""+e.getTemporalLinkType()+"\" />\n");
		}
		
		docString.append("</TAGS>\n");
		docString.append("</ClinicalNarrativeTemporalAnnotation>\n");



		return docString.toString();
	}


	

	/**
	 * writes a populated DOM xml(timeml) document to a given directory/file 
	 * @param xmlDoc xml dom object
	 * @param filename name of the file that gets appended to the set output path
	 */
	private void writeDocument(String docString, String filename) {
		// create output file handle
		String dir = filename;
		//dir = dir.replaceAll("\\..*", "");
		System.out.println("DIR: "+dir);

		//File tempDir = new File(mOutputDir, dir);

		//if(!tempDir.exists())
		//	tempDir.mkdir();

		if(!filename.endsWith(".xml"))
			filename = filename+".xml";
		//File outFile = new File(tempDir.getAbsolutePath(), filename); 
		File outFile = new File(mOutputDir, filename); 

		BufferedWriter bw = null;
		try {
			// create a buffered writer for the output file
			bw = new BufferedWriter(new FileWriter(outFile));

			bw.write(docString);


		} catch (IOException e) { // something went wrong with the bufferedwriter
			e.printStackTrace();
			logger.error("File "+outFile.getAbsolutePath()+" could not be written.");
		} finally { // clean up for the bufferedwriter
			try {
				bw.close();
			} catch(IOException e) {
				e.printStackTrace();
				logger.error("File "+outFile.getAbsolutePath()+" could not be closed.");
			}
		}
	}

	public static synchronized Integer getOutCount() {
		return outCount++;
	}


}
