package tempus.pipeline;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.cleartk.ml.jar.Train;

import tempus.collectionreader.THYMECorpusReader;
import tempus.consumer.THYMECorpusWriter;
import tempus.eventannotator.EventAnnotator;
import tempus.eventannotator.attributes.EventDegreeAnnotator;
import tempus.eventannotator.attributes.EventDocTimeRelAnnotator;
import tempus.eventannotator.attributes.EventModalityAnnotator;
import tempus.eventannotator.attributes.EventPolarityAnnotator;
import tempus.eventannotator.attributes.EventTypeAnnotator;
import tempus.timexannotator.TimexDATEAnnotator;
import tempus.timexannotator.TimexDURATIONAnnotator;
import tempus.timexannotator.TimexPREPOSTEXPAnnotator;
import tempus.timexannotator.TimexQUANTIFIERAnnotator;
import tempus.timexannotator.TimexSETAnnotator;
import tempus.timexannotator.TimexTIMEAnnotator;





public class TempusTHYMETrainTestAnnotatorPipeline {

	static Logger logger = Logger.getLogger(TempusTHYMETrainTestAnnotatorPipeline.class);


	/*private static String outputDirectory = "tempus";
	private static String predictionoutputdirectory = outputDirectory+"/results";
	private static String modeloutputdirectory = outputDirectory+"/models";

	private static String trainingdatadirectory;
	private static String testdatadirectory;
	private static String sourcefiledirectory;

	private static String keywordfile;*/
	private String outputDirectory;
	private String predictionoutputdirectory;
	private String modeloutputdirectory;

	private String trainingdatadirectory;
	private String testdatadirectory;
	private String sourcefiledirectory;

	private String keywordfile;
	
	TempusTHYMETrainTestAnnotatorPipeline(){
		
	}

	public static void main(String... args) throws Exception {

		// This class performs a full training and test cycle on the THYME data that was released in the 2015 Clinical TempEval Challenge.
		
		boolean training = true;		
		boolean event = true;
		boolean timex3 = true;

		
		try {
			TempusTHYMETrainTestAnnotatorPipeline mainPipeline = new TempusTHYMETrainTestAnnotatorPipeline();
			mainPipeline.parseCommandLineArguments(args);
			
			mainPipeline.run(training, 0, event, timex3);
			training = false;
			mainPipeline.run(training, 0, event, timex3);

		} catch(Exception e) {
			logger.fatal("Error parsing command line arguments and running the pipeline.");
			e.printStackTrace();
		}

	}

	private void run(boolean training, int fold, boolean event,  boolean timex3) throws Exception{

		String dataDirectories;

		if(training)
			dataDirectories = this.trainingdatadirectory;
		else
			dataDirectories = this.testdatadirectory;


		String sourceFileNames;
		if(training)
			sourceFileNames = this.sourcefiledirectory+"train";
		else
			sourceFileNames = this.sourcefiledirectory+"dev";

		CollectionReader reader;

		if(training)
			reader = THYMECorpusReader.getCollectionReader(dataDirectories, sourceFileNames, "true");
		else
			reader = THYMECorpusReader.getCollectionReader(dataDirectories, sourceFileNames, "false");


		// The pipeline of annotators
		AggregateBuilder builder = new AggregateBuilder();

		// Segment annotator
		builder.add(org.apache.ctakes.core.ae.SimpleSegmentAnnotator.createAnnotatorDescription());
		// Sentence annotator
		builder.add(org.apache.ctakes.core.ae.SentenceDetector.createAnnotatorDescription());
		// Tokenizer
		builder.add(org.apache.ctakes.core.ae.TokenizerAnnotatorPTB.createAnnotatorDescription());
		// LVG
		builder.add(org.apache.ctakes.lvg.ae.LvgAnnotator.createAnnotatorDescription());
		// Tokenizer
		builder.add(org.apache.ctakes.contexttokenizer.ae.ContextDependentTokenizerAnnotator.createAnnotatorDescription());
		// Pos tagger
		builder.add(org.apache.ctakes.postagger.POSTagger.createAnnotatorDescription());



		//builder.add(org.apache.ctakes.chunker.ae.Chunker.createAnnotatorDescription());
		//String[] np = new String[2];
		//np[0] = "NP";
		//np[1] = "NP";
		//builder.add(org.apache.ctakes.chunker.ae.adjuster.ChunkAdjuster.createAnnotatorDescription(np, 1));
		//String[] npppnp = new String[3];
		//npppnp[0] = "NP";
		//npppnp[1] = "PP";
		//npppnp[2] = "NP";
		//builder.add(org.apache.ctakes.chunker.ae.adjuster.ChunkAdjuster.createAnnotatorDescription(npppnp, 2));

		//builder.add(org.apache.ctakes.dependency.parser.ae.ClearNLPDependencyParserAE.createAnnotatorDescription());

		//Object[] params = new Object[2];
		//params[0] = new GenericCleartkAnalysisEngine();
		//params[1] = PolarityCleartkAnalysisEngine.class;


		//builder.add(AnalysisEngineFactory.createEngineDescription(CopyNPChunksToLookupWindowAnnotations.class));
		//builder.add(AnalysisEngineFactory.createEngineDescription(RemoveEnclosedLookupWindows.class));
		//builder.add(AnalysisEngineFactory.createEngineDescription(ConstituencyParser.class));

		// The annotators, configured to write training data
		if(training){

			// Event spans
			builder.add(EventAnnotator.getWriterDescription(this.modeloutputdirectory+"/event"));

			// Event attributes
			builder.add(EventPolarityAnnotator.getWriterDescription(this.modeloutputdirectory+"/polarity", keywordfile));
			builder.add(EventModalityAnnotator.getWriterDescription(this.modeloutputdirectory+"/modality", keywordfile));
			builder.add(EventTypeAnnotator.getWriterDescription(this.modeloutputdirectory+"/type", keywordfile));
			builder.add(EventDegreeAnnotator.getWriterDescription(this.modeloutputdirectory+"/degree", keywordfile));
			builder.add(EventDocTimeRelAnnotator.getWriterDescription(this.modeloutputdirectory+"/doctimerel"));

			// TIMEX3 PER TYPE
			builder.add(TimexDATEAnnotator.getWriterDescription(this.modeloutputdirectory+"/date"));
			builder.add(TimexTIMEAnnotator.getWriterDescription(this.modeloutputdirectory+"/time"));
			builder.add(TimexDURATIONAnnotator.getWriterDescription(this.modeloutputdirectory+"/duration"));
			builder.add(TimexPREPOSTEXPAnnotator.getWriterDescription(this.modeloutputdirectory+"/prepostexp"));
			builder.add(TimexQUANTIFIERAnnotator.getWriterDescription(this.modeloutputdirectory+"/quantifier"));
			builder.add(TimexSETAnnotator.getWriterDescription(this.modeloutputdirectory+"/set"));


		}

		else{
			builder.add(EventAnnotator.getClassifierDescription(this.modeloutputdirectory+"/event/model.jar"));

			builder.add(EventPolarityAnnotator.getClassifierDescription(this.modeloutputdirectory+"/polarity/model.jar", keywordfile));
			builder.add(EventModalityAnnotator.getClassifierDescription(this.modeloutputdirectory+"/modality/model.jar", keywordfile));
			builder.add(EventTypeAnnotator.getClassifierDescription(this.modeloutputdirectory+"/type/model.jar", keywordfile));
			builder.add(EventDegreeAnnotator.getClassifierDescription(this.modeloutputdirectory+"/degree/model.jar", keywordfile));
			builder.add(EventDocTimeRelAnnotator.getClassifierDescription(this.modeloutputdirectory+"/doctimerel/model.jar"));

			builder.add(TimexDATEAnnotator.getClassifierDescription(this.modeloutputdirectory+"/date/model.jar"));
			builder.add(TimexTIMEAnnotator.getClassifierDescription(this.modeloutputdirectory+"/time/model.jar"));
			builder.add(TimexDURATIONAnnotator.getClassifierDescription(this.modeloutputdirectory+"/duration/model.jar"));
			builder.add(TimexPREPOSTEXPAnnotator.getClassifierDescription(this.modeloutputdirectory+"/prepostexp/model.jar"));
			builder.add(TimexQUANTIFIERAnnotator.getClassifierDescription(this.modeloutputdirectory+"/quantifier/model.jar"));
			builder.add(TimexSETAnnotator.getClassifierDescription(this.modeloutputdirectory+"/set/model.jar"));

			builder.add(THYMECorpusWriter.createAnnotatorDescription(this.predictionoutputdirectory+"/dev"));
		}
		// Run the pipeline of annotators on each of the CASes produced by the reader
		SimplePipeline.runPipeline(reader, builder.createAggregateDescription());

		if(training){
			logger.info("Training...");

			// Train a classifier on the training data, and package it into a .jar file
			// C parameter settings were evaluated separately on a portion of the THYME training data using
			// scripts provided by Liblinear/LibSvm team
			// NOTE: this was only done for Event and Timex3 types, not the Event attribute classifiers
			String[] args = new String[2];
			args[0] = "-c";
			args[1] = "0.25";
			Train.main(new File(this.modeloutputdirectory+"/event"), args);
			Train.main(new File(this.modeloutputdirectory+"/polarity"), args);
			Train.main(new File(this.modeloutputdirectory+"/modality"), args);
			Train.main(new File(this.modeloutputdirectory+"/type"), args);
			Train.main(new File(this.modeloutputdirectory+"/degree"), args);
			Train.main(new File(this.modeloutputdirectory+"/doctimerel"), args);


			// date param:
			args[1] = "0.5"; 
			Train.main(new File(this.modeloutputdirectory+"/date"), args);
			// time param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/time"), args);
			// prepostexp param:
			args[1] = "1.0";
			Train.main(new File(this.modeloutputdirectory+"/prepostexp"), args);
			// duration param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/duration"), args);
			// quantifier param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/quantifier"), args);
			// set param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/set"), args);

			logger.info("training completed.");

		}
	}

	private void parseCommandLineArguments(String[] args){
		Options options = new Options();
		options.addOption("h", "help", false, "show help and required command line arguments");
		options.addOption("od", "outputdirectory", true, "outputdirectory to write predictions and models");
		options.addOption("trd", "trainingdatadirectory", true, "training data directory with THYME annotations");
		options.addOption("ted", "testdatadirectory", true, "test data directory with THYME annotations");
		options.addOption("sd", "sourcefiledirectory", true, "source file directory with original texts");
		options.addOption("kw", "keywordfile", true, "file with semantic modifier keywords in pyConText format - these are required as features for some of the event attribute types");
		CommandLineParser parser = new BasicParser();
		try{
			CommandLine line = parser.parse(options, args); 
			if (line.hasOption("od")) {
				logger.info("Setting outputdirectory to: "+line.getOptionValue("od"));
				this.outputDirectory = line.getOptionValue("od");
				this.predictionoutputdirectory = this.outputDirectory+"/results";
				this.modeloutputdirectory = this.outputDirectory+"/models";
			}
			else {
				logger.fatal("Missing outputdirectory (-od)");
				help(options);
			}
			if (line.hasOption("trd")) {
				logger.info("Training data directory is: "+line.getOptionValue("trd"));
				this.trainingdatadirectory = line.getOptionValue("trd");
			}
			else {
				logger.fatal("Missing trainingdatadirectory (-trd)");
				help(options);
			}
			if (line.hasOption("ted")) {
				logger.info("Test data directory is: "+line.getOptionValue("ted"));
				this.testdatadirectory = line.getOptionValue("ted");
			}
			else {
				logger.fatal("Missing testdatadirectory (-ted)");
				help(options);
			}
			if (line.hasOption("sd")) {
				logger.info("Source file data directory is: "+line.getOptionValue("sd"));
				this.sourcefiledirectory = line.getOptionValue("sd");
			}
			else {
				logger.fatal("Missing source file data directory (-sd)");
				help(options);
			}
			if (line.hasOption("kw")) {
				logger.info("Keywordfile is: "+line.getOptionValue("kw"));
				this.keywordfile = line.getOptionValue("kw");
			}
			else {
				logger.fatal("Missing keyword file (-kw)");
				help(options);
			}
		}catch(Exception e) {
			logger.fatal("Error parsing command line arguments.");
			e.printStackTrace();
		}
		
	}
	private static void help(Options options) {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("-od [outputdirectory] -trd [trainingdatadirectory] -ted [testdatadirectory] -sd [sourcefiledirectory] -kw [keywordfile]", options);
		System.exit(0);
	}
}



