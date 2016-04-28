package tempus.timexannotator;

import java.util.ArrayList;
import java.util.List;

import org.apache.ctakes.typesystem.type.syntax.BaseToken;
import org.apache.ctakes.typesystem.type.textspan.Sentence;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.ml.CleartkSequenceAnnotator;
import org.cleartk.ml.Feature;
import org.cleartk.ml.Instances;
import org.cleartk.ml.chunking.BioChunking;
import org.cleartk.ml.feature.extractor.CleartkExtractor;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Following;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Preceding;
import org.cleartk.ml.feature.extractor.CoveredTextExtractor;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;
import org.cleartk.ml.feature.extractor.TypePathExtractor;
import org.cleartk.ml.feature.function.CapitalTypeFeatureFunction;
import org.cleartk.ml.feature.function.CharacterCategoryPatternFunction;
import org.cleartk.ml.feature.function.FeatureFunctionExtractor;
import org.cleartk.ml.feature.function.LowerCaseFeatureFunction;
import org.cleartk.ml.feature.function.NumericTypeFeatureFunction;
import org.cleartk.ml.jar.DefaultDataWriterFactory;
import org.cleartk.ml.jar.DirectoryDataWriterFactory;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.ml.liblinear.LibLinearStringOutcomeDataWriter;
import org.cleartk.ml.viterbi.DefaultOutcomeFeatureExtractor;
import org.cleartk.ml.viterbi.ViterbiDataWriterFactory;

import tempus.type.Section;
import tempus.type.Timex3;

import com.google.common.collect.Lists;

public class TimexFREQUENCYAnnotator extends CleartkSequenceAnnotator<String>{

	private Logger logger = Logger.getLogger(TimexFREQUENCYAnnotator.class);

	private List<NamedFeatureExtractor1<BaseToken>> tokenFeatureExtractors;

	private List<CleartkExtractor<BaseToken, BaseToken>> contextFeatureExtractors;

	//ADDED EXPERIMENT for 2016 challenge
//	private FeatureExtractor1<Event> added_event_features;

	private BioChunking<BaseToken, Timex3> chunking;

	private FeatureExtractor1<BaseToken> added_features;


	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		// define chunking type
		this.chunking = new BioChunking<BaseToken, Timex3>(BaseToken.class, Timex3.class);

		// add features: word, character pattern, stem, pos
		this.tokenFeatureExtractors = Lists.newArrayList();

		this.tokenFeatureExtractors.add(new CoveredTextExtractor<BaseToken>());
		NamedFeatureExtractor1<BaseToken> ex = CharacterCategoryPatternFunction.createExtractor();
		this.tokenFeatureExtractors.add(ex);
		this.tokenFeatureExtractors.add(new TypePathExtractor<BaseToken>(BaseToken.class, "partOfSpeech"));

		// add window of features before and after
		this.contextFeatureExtractors = Lists.newArrayList();
		for (FeatureExtractor1<BaseToken> extractor : this.tokenFeatureExtractors) {
			this.contextFeatureExtractors.add(new CleartkExtractor<BaseToken, BaseToken>(BaseToken.class, extractor, new Preceding(
					5), new Following(3)));
		}

		this.added_features = new FeatureFunctionExtractor<BaseToken>(
				new CoveredTextExtractor<BaseToken>(),
				new CapitalTypeFeatureFunction(),
				new NumericTypeFeatureFunction(),
				new LowerCaseFeatureFunction()
				);
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		logger.info("Processing FREQUENCIES");

		for(Section section : JCasUtil.select(jCas, Section.class)){
			List<Sentence> sentences = JCasUtil.selectCovered(jCas, Sentence.class, section);
			// classify tokens within each sentence
			for(Sentence sentence : sentences){
				List<BaseToken> tokens = JCasUtil.selectCovered(jCas, BaseToken.class, sentence);

				// extract features for all tokens
				List<List<Feature>> featureLists = new ArrayList<List<Feature>>();

				for (BaseToken token : tokens) {
					List<Feature> features = new ArrayList<Feature>();
					for (FeatureExtractor1<BaseToken> extractor : this.tokenFeatureExtractors) {
						features.addAll(extractor.extract(jCas, token));
					}
					for (CleartkExtractor<BaseToken, BaseToken> extractor : this.contextFeatureExtractors) {
						features.addAll(extractor.extractWithin(jCas, token, sentence));
					}

					//List<Event> followingEvents = JCasUtil.selectFollowing(Event.class, token, 1);

/*					// ADDED FEATURE for 2016 challenge
					String f = "FOLLOWING_EVENT";
					String v = "NONE";
					if(followingEvents.size()>0)
						v = followingEvents.get(0).getCoveredText();
					Feature following_event = new Feature(f, v);
					features.add(following_event);

					List<Event> precedingEvents = JCasUtil.selectPreceding(Event.class, token, 1);

					// ADDED FEATURE for 2016 challenge
					String f2 = "PRECEDING_EVENT";
					String v2 = "NONE";
					if(precedingEvents.size()>0)
						v2 = precedingEvents.get(0).getCoveredText();
					Feature preceding_event = new Feature(f2, v2);
					features.add(preceding_event);*/

					//added features
					features.addAll(this.added_features.extract(jCas, token));


					//ADDED HEIDELTIME FEATURE
/*					Map<BaseToken,Collection<de.unihd.dbs.uima.types.heideltime.Timex3>> coveringSection = JCasUtil.indexCovering(jCas,
							BaseToken.class,
							de.unihd.dbs.uima.types.heideltime.Timex3.class
							);

					List<de.unihd.dbs.uima.types.heideltime.Timex3> s = new ArrayList<de.unihd.dbs.uima.types.heideltime.Timex3>(coveringSection.get(token));
					Feature ht;
					if(s.size()>0){
						ht = new Feature("IN_HEIDELTIME", "yes");
						//String heideltimestring = s.get(0).getCoveredText();
						//System.out.println(heideltimestring);
					}
					//List<de.unihd.dbs.uima.types.heideltime.Timex3> heideltime = JCasUtil.selectCovering(de.unihd.dbs.uima.types.heideltime.Timex3.class, token);

					//if(heideltime.size()>0){
					//	ht = new Feature("IN_HEIDELTIME", "yes");
					//}
					else
						ht = new Feature("IN_HEIDELTIME", "no");
					features.add(ht);*/

					features.add(new Feature("Section", section.getId()));
					featureLists.add(features);
				}



				//end added features
				// during training, convert Times to chunk labels and write the training Instances
				if (this.isTraining()) {
					List<Timex3> dates = new ArrayList<Timex3>();
					List<Timex3> times = JCasUtil.selectCovered(jCas, Timex3.class, sentence);
					for(Timex3 date : times){
						if(date.getTimex3Type().equals("FREQUENCY")){							
							dates.add(date);
						}
					}
					List<String> outcomes = this.chunking.createOutcomes(jCas, tokens, dates);
					this.dataWriter.write(Instances.toInstances(outcomes, featureLists));
				}

				// during prediction, convert chunk labels to Times and add them to the CAS
				else {
					List<String> outcomes = this.classifier.classify(featureLists);
					this.chunking.createChunks(jCas, tokens, outcomes);
				}
			}// end for sentence
			//}// end if section id equals
		}// end for section

		// add IDs and type to all predicted Timex3s
		int timeIndex = 1;
		for (Timex3 time : JCasUtil.select(jCas, Timex3.class)) {
			if(time.getTimex3Type()==null){
				time.setTimex3Type("FREQUENCY");
				time.setId("TFREQUENCY"+timeIndex);
			}
			timeIndex += 1;
		}
	}
	public static AnalysisEngineDescription getClassifierDescription(String modelFileName)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				TimexFREQUENCYAnnotator.class,
				CleartkSequenceAnnotator.PARAM_IS_TRAINING, false,
				GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH,
				modelFileName);
	}

	public static AnalysisEngineDescription getWriterDescription(String outputDirectory)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				TimexFREQUENCYAnnotator.class,
				CleartkSequenceAnnotator.PARAM_IS_TRAINING, true,
				CleartkSequenceAnnotator.PARAM_DATA_WRITER_FACTORY_CLASS_NAME,
				ViterbiDataWriterFactory.class.getName(),
				DirectoryDataWriterFactory.PARAM_OUTPUT_DIRECTORY,
				outputDirectory,
				ViterbiDataWriterFactory.PARAM_DELEGATED_DATA_WRITER_FACTORY_CLASS,
				DefaultDataWriterFactory.class.getName(),
				DefaultDataWriterFactory.PARAM_DATA_WRITER_CLASS_NAME,
				//MaxentStringOutcomeDataWriter.class.getName(),
				LibLinearStringOutcomeDataWriter.class.getName(),
				ViterbiDataWriterFactory.PARAM_OUTCOME_FEATURE_EXTRACTOR_NAMES,
				new String[] { DefaultOutcomeFeatureExtractor.class.getName() });
	}


}

