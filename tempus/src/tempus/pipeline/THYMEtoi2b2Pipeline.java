package tempus.pipeline;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.fit.pipeline.SimplePipeline;

import tempus.collectionreader.THYMECorpusReader;
import tempus.consumer.I2b2Corpus2012Writer;





public class THYMEtoi2b2Pipeline {

	static Logger logger = Logger.getLogger(THYMEtoi2b2Pipeline.class);

	private String outputDirectory;
	private String predictionoutputdirectory;

	private String trainingdatadirectory;
	private String testdatadirectory;
	private String sourcefiledirectory;

	THYMEtoi2b2Pipeline(){

	}

	public static void main(String... args) throws Exception {

		// This class reads in THYME annotations and writes in i2b2 format - nothing else

		boolean training = true;		
		boolean event = true;
		boolean timex3 = true;


		try {
			THYMEtoi2b2Pipeline mainPipeline = new THYMEtoi2b2Pipeline();
			mainPipeline.parseCommandLineArguments(args);

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

		builder.add(I2b2Corpus2012Writer.createAnnotatorDescription(this.predictionoutputdirectory));

		// Run the pipeline of annotators on each of the CASes produced by the reader
		SimplePipeline.runPipeline(reader, builder.createAggregateDescription());

	}

	private void parseCommandLineArguments(String[] args){
		Options options = new Options();
		options.addOption("h", "help", false, "show help and required command line arguments");
		options.addOption("od", "outputdirectory", true, "outputdirectory to write predictions and models");
		options.addOption("trd", "trainingdatadirectory", true, "training data directory with THYME annotations");
		options.addOption("sd", "sourcefiledirectory", true, "source file directory with original texts");
		CommandLineParser parser = new BasicParser();
		try{
			CommandLine line = parser.parse(options, args); 
			if (line.hasOption("od")) {
				logger.info("Setting outputdirectory to: "+line.getOptionValue("od"));
				this.outputDirectory = line.getOptionValue("od");
				this.predictionoutputdirectory = this.outputDirectory+"/results";
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
			
			if (line.hasOption("sd")) {
				logger.info("Source file data directory is: "+line.getOptionValue("sd"));
				this.sourcefiledirectory = line.getOptionValue("sd");
			}
			else {
				logger.fatal("Missing source file data directory (-sd)");
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
		formater.printHelp("-od [outputdirectory] -trd [trainingdatadirectory] -sd [sourcefiledirectory]", options);
		System.exit(0);
	}
}



