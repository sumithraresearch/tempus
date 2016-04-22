package tempus.consumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.ctakes.typesystem.type.textsem.TimeMention;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import tempus.type.DocumentCreationTime;
import tempus.type.Event;

/**
 * Class for writing THYME-corpus type annotations to document
 * Requires Annotations of type Section, Sentence, BaseToken, TimeMention, Event
 * 
 * @author Sumithra Velupillai
 */

public class THYMECorpusWriter extends JCasAnnotator_ImplBase{
	
	private Logger logger = Logger.getLogger(THYMECorpusWriter.class);

	public static final String PARAM_OUTPUTDIR = "OutputDir";
	// Parameter only used for file naming conventinon for the TempEval evaluation script (system prediction files need different names)
	public static final String PARAM_TRAINING = "false";

	private boolean training;

	// counter for outputting documents. gets increased in case there is no DCT/filename info 
	private static volatile Integer outCount = 0;

	private File mOutputDir;

	public static AnalysisEngineDescription createAnnotatorDescription(String outputdir) throws ResourceInitializationException{
		return AnalysisEngineFactory.createEngineDescription(THYMECorpusWriter.class, THYMECorpusWriter.PARAM_OUTPUTDIR, outputdir, THYMECorpusWriter.PARAM_TRAINING, "false");
	}

	public static AnalysisEngineDescription createAnnotatorDescription(String outputdir, String training) throws ResourceInitializationException{
		return AnalysisEngineFactory.createEngineDescription(THYMECorpusWriter.class, THYMECorpusWriter.PARAM_OUTPUTDIR, outputdir, THYMECorpusWriter.PARAM_TRAINING, training);
	}

	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);
		String t = (String) aContext.getConfigParameterValue(PARAM_TRAINING);
		if(t.equals("false"))
			training = false;
		else
			training = true;
		mOutputDir = new File((String) aContext.getConfigParameterValue(PARAM_OUTPUTDIR));

		if (!mOutputDir.exists()) {
			if(!mOutputDir.mkdirs()) {
				//TODO: add logging!
				//logger.debug(component, "Couldn't create non-existent folder "+mOutputDir.getAbsolutePath());
				logger.error("Couldn't create non-existent folder "+mOutputDir.getAbsolutePath());
				throw new ResourceInitializationException();
			}
		}

		if(!mOutputDir.canWrite()) {
			//TODO: add logging!				
			logger.error("Folder "+mOutputDir.getAbsolutePath()+" is not writable.");
			throw new ResourceInitializationException();
		}
	}

	public void process(JCas jcas) {

		DocumentCreationTime dct = (DocumentCreationTime) jcas.getAnnotationIndex(DocumentCreationTime.type).iterator().next();


		String filename = dct.getFilename();

		//TODO: more generic file(name) handling
		filename = filename.replaceAll("\\.txt", "");
		if(training)
			filename += "-Temporal_relations";
		else
			filename += "-Temporal-Relation.system.completed";
		
		//TODO: add logging
		logger.info(filename);

		String docString = buildThymeDocumentToString(jcas, filename);
		writeThymeDocument(docString, filename);


	}


	/**
	 * Creates a DOM Document filled with all of the THYME Annotations that are in the jcas.
	 * @param jcas
	 * @param dct the document's DCT
	 * @return
	 */
	private String buildThymeDocumentToString(JCas jcas, String filename) {
		StringBuffer docString = new StringBuffer();

		docString.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");

		// create the Anaphora root element
		docString.append("<data>\n");


		// add annotation nodes

		docString.append("<annotations>\n");


		// create and fill the TEXT tag
		Integer offset = 0;
		//Element textEl = doc.createElement("annotations");
		//rootEl.appendChild(textEl);

		// DOCTIME

		/*<entity>
			<id>130@e@ID041_clinic_118@gold</id>
			<span>40,50</span>
			<type>DOCTIME</type>
			<parentsType>TemporalEntities</parentsType>
			<properties>
			</properties>
		</entity>*/
		/*		FSIterator it_doctime = jcas.getAnnotationIndex(Dct.type).iterator();
			int doctimecounter = 0;
			while(it_doctime.hasNext()) {
				doctimecounter++;
				Dct dct = (Dct) it_doctime.next();
				docString.append("<entity>\n");
				docString.append("<id>");
				docString.append("D"+doctimecounter);
				docString.append("</id>\n");
				docString.append("<span>");
				docString.append(dct.getBegin()+","+dct.getEnd());
				docString.append("</span>\n");
				docString.append("<type>DOCTIME</type>\n");
				docString.append("<parentsType>TemporalEntities</parentsType>\n");
				docString.append("<properties>\n");
				docString.append("</properties>\n");
				docString.append("</entity>\n");
			}*/

		/*		// SECTIONTIME
			FSIterator it_sectime = jcas.getAnnotationIndex(SectionTime.type).iterator();
			int sectimecounter = 0;
			while(it_sectime.hasNext()) {
				sectimecounter++;
				SectionTime sec = (SectionTime) it_sectime.next();
				docString.append("<entity>\n");
				docString.append("<id>");
				docString.append("S"+sectimecounter);
				docString.append("</id>\n");
				docString.append("<span>");
				docString.append(sec.getBegin()+","+sec.getEnd());
				docString.append("</span>\n");
				docString.append("<type>SECTIONTIME</type>\n");
				docString.append("<parentsType>TemporalEntities</parentsType>\n");
				docString.append("<properties>\n");
				docString.append("</properties>\n");
				docString.append("</entity>\n");
			}*/

		FSIterator it_event = jcas.getAnnotationIndex(Event.type).iterator();
		int counter = 0;
		while(it_event.hasNext()) {
			counter++;
			Event e = (Event) it_event.next();
			// create the EVENT element
			docString.append("<entity>\n");

			docString.append("<id>");
			String id = "";
			if(e.getId()==null)
				id += "E"+counter;
			else
				id = e.getId();
			docString.append(id);
			docString.append("</id>\n");

			docString.append("<span>");
			docString.append((String.valueOf(e.getBegin())+","+String.valueOf(e.getEnd())));
			docString.append("</span>\n");

			docString.append("<type>");
			docString.append("EVENT");
			docString.append("</type>\n");


			docString.append("<parentsType>");
			docString.append("TemporalEntities");
			docString.append("</parentsType>\n");

			docString.append("<properties>\n");

			docString.append("<DocTimeRel>");
			String doctimerel = e.getDoctimerel();
			if(doctimerel== null)
				doctimerel = "OVERLAP";
			docString.append(doctimerel);
			docString.append("</DocTimeRel>\n");

			docString.append("<Type>");
			String type = e.getEventClass();
			if(type == null)
				type = "N/A";
			docString.append(type);
			docString.append("</Type>\n");

			docString.append("<Degree>");
			String degree = e.getDegree();
			if(degree == null)
				degree = "N/A";
			docString.append(degree);
			docString.append("</Degree>\n");

			docString.append("<Polarity>");
			String pol = e.getPolarity();
			//default is POS
			if(pol == null)
				pol = "POS";

			docString.append(pol);
			docString.append("</Polarity>\n");


			docString.append("<ContextualModality>");
			String conmod = e.getContextualmodality();
			if(conmod == null)
				conmod = "ACTUAL";
			docString.append(conmod);
			docString.append("</ContextualModality>\n");

			docString.append("<ContextualAspect>");
			String conasp = e.getContextualaspect();
			if(conasp == null)
				conasp = "N/A";
			docString.append(conasp);
			docString.append("</ContextualAspect>\n");

			docString.append("<Permanence>");
			String permanence = e.getPermanence();
			if(permanence == null)
				permanence = "UNDETERMINED";
			docString.append(permanence);
			docString.append("</Permanence>\n");

			docString.append("</properties>\n");

			docString.append("</entity>\n");



		}



		// new counter for all timexes
		int timecounter = 0;

		//Timexes
		FSIterator it_timex = jcas.getAnnotationIndex(TimeMention.type).iterator();
		while(it_timex.hasNext()) {

			// dates come first
			timecounter++;
			TimeMention e = (TimeMention) it_timex.next();

			docString.append("<entity>\n");

			docString.append("<id>");
			//docString.append("T"+timecounter);
			docString.append(e.getTimeClass()+e.getId());
			docString.append("</id>\n");

			docString.append("<span>");
			docString.append((String.valueOf(e.getBegin())+","+String.valueOf(e.getEnd())));
			docString.append("</span>\n");

			docString.append("<type>");
			docString.append("TIMEX3");
			docString.append("</type>\n");


			docString.append("<parentsType>");
			docString.append("TemporalEntities");
			docString.append("</parentsType>\n");

			docString.append("<properties>\n");

			docString.append("<Class>");
			String timextype = e.getTimeClass();
			if(timextype == null)
				timextype = "NOTYPE";
			docString.append(timextype);
			docString.append("</Class>\n");

			docString.append("</properties>\n");
			docString.append("</entity>\n");


		}



		docString.append("</annotations>\n");
		docString.append("</data>\n");



		return docString.toString();
	}



	/**
	 * writes a populated DOM xml(timeml) document to a given directory/file 
	 * @param xmlDoc xml dom object
	 * @param filename name of the file that gets appended to the set output path
	 */
	private void writeThymeDocument(String docString, String filename) {
		// create output file handle
		//TODO: better, more generic file handling!
		String dir = filename;
		dir = dir.replaceAll("-Temporal-Relation\\.system\\.completed", "");
		dir = dir.replaceAll("\\..*", "");
		dir = dir.replaceAll("-Temporal_relations", "");

		logger.info("DIRECTORY: "+dir);

		File tempDir = new File(mOutputDir, dir);

		if(!tempDir.exists())
			tempDir.mkdir();

		if(!filename.endsWith(".xml"))
			filename = filename+".xml";
		File outFile = new File(tempDir.getAbsolutePath(), filename); 

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



