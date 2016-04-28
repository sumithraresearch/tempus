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

import tempus.collectionreader.I2b2Corpus2012Reader;
import tempus.consumer.I2b2Corpus2012Writer;
import tempus.eventannotator.EventAnnotator;
import tempus.eventannotator.attributes.EventModalityAnnotator;
import tempus.eventannotator.attributes.EventPolarityAnnotator;
import tempus.eventannotator.attributes.EventTypeAnnotator;
import tempus.timexannotator.TimexDATEAnnotator;
import tempus.timexannotator.TimexDURATIONAnnotator;
import tempus.timexannotator.TimexFREQUENCYAnnotator;
import tempus.timexannotator.TimexTIMEAnnotator;





public class Tempus2012i2b2TrainTestAnnotatorPipeline {

	static Logger logger = Logger.getLogger(Tempus2012i2b2TrainTestAnnotatorPipeline.class);


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
	
	Tempus2012i2b2TrainTestAnnotatorPipeline(){
		
	}

	public static void main(String... args) throws Exception {

		// This class performs a full training and test cycle on the THYME data that was released in the 2015 Clinical TempEval Challenge.
		
		boolean training = true;		
		boolean event = true;
		boolean timex3 = true;

		
		try {
			Tempus2012i2b2TrainTestAnnotatorPipeline mainPipeline = new Tempus2012i2b2TrainTestAnnotatorPipeline();
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


		CollectionReader reader;

		if(training)
			reader = I2b2Corpus2012Reader.getCollectionReader(dataDirectories, "true");
		else
			reader = I2b2Corpus2012Reader.getCollectionReader(dataDirectories, "false");


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

			// TIMEX3 PER TYPE
			builder.add(TimexDATEAnnotator.getWriterDescription(this.modeloutputdirectory+"/date"));
			builder.add(TimexTIMEAnnotator.getWriterDescription(this.modeloutputdirectory+"/time"));
			builder.add(TimexDURATIONAnnotator.getWriterDescription(this.modeloutputdirectory+"/duration"));
			builder.add(TimexFREQUENCYAnnotator.getWriterDescription(this.modeloutputdirectory+"/frequency"));


		}

		else{
			builder.add(EventAnnotator.getClassifierDescription(this.modeloutputdirectory+"/event/model.jar"));

			builder.add(EventPolarityAnnotator.getClassifierDescription(this.modeloutputdirectory+"/polarity/model.jar", keywordfile));
			builder.add(EventModalityAnnotator.getClassifierDescription(this.modeloutputdirectory+"/modality/model.jar", keywordfile));
			builder.add(EventTypeAnnotator.getClassifierDescription(this.modeloutputdirectory+"/type/model.jar", keywordfile));

			builder.add(TimexDATEAnnotator.getClassifierDescription(this.modeloutputdirectory+"/date/model.jar"));
			builder.add(TimexTIMEAnnotator.getClassifierDescription(this.modeloutputdirectory+"/time/model.jar"));
			builder.add(TimexDURATIONAnnotator.getClassifierDescription(this.modeloutputdirectory+"/duration/model.jar"));
			builder.add(TimexFREQUENCYAnnotator.getClassifierDescription(this.modeloutputdirectory+"/frequency/model.jar"));

			builder.add(I2b2Corpus2012Writer.createAnnotatorDescription(this.predictionoutputdirectory+"/dev"));
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
			logger.info("Event training completed");
			Train.main(new File(this.modeloutputdirectory+"/polarity"), args);
			logger.info("Event polarity training completed");
			Train.main(new File(this.modeloutputdirectory+"/modality"), args);
			logger.info("Event modality training completed");
			Train.main(new File(this.modeloutputdirectory+"/type"), args);
			logger.info("Event type training completed");


			// date param:
			args[1] = "0.5"; 
			Train.main(new File(this.modeloutputdirectory+"/date"), args);
			logger.info("TIMEX3 date training completed");
			// time param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/time"), args);
			logger.info("TIMEX3 time training completed");
			// duration param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/duration"), args);
			logger.info("TIMEX3 duration training completed");
			// quantifier param:
			args[1] = "0.5";
			Train.main(new File(this.modeloutputdirectory+"/frequency"), args);
			logger.info("TIMEX3 frequency training completed");


			logger.info("training completed.");

		}
	}

	private void parseCommandLineArguments(String[] args){
		Options options = new Options();
		options.addOption("h", "help", false, "show help and required command line arguments");
		options.addOption("od", "outputdirectory", true, "outputdirectory to write predictions and models");
		options.addOption("trd", "trainingdatadirectory", true, "training data directory with THYME annotations");
		options.addOption("ted", "testdatadirectory", true, "test data directory with THYME annotations");
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
		formater.printHelp("-od [outputdirectory] -trd [trainingdatadirectory] -ted [testdatadirectory] -kw [keywordfile]", options);
		System.exit(0);
	}
}



