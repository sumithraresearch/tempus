package tempus.eventannotator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import org.cleartk.ml.feature.extractor.TypePathExtractor;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction.Orientation;
import org.cleartk.ml.feature.function.FeatureFunctionExtractor;
import org.cleartk.ml.feature.function.LowerCaseFeatureFunction;
import org.cleartk.ml.jar.DefaultDataWriterFactory;
import org.cleartk.ml.jar.DirectoryDataWriterFactory;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.ml.liblinear.LibLinearStringOutcomeDataWriter;
import org.cleartk.ml.viterbi.DefaultOutcomeFeatureExtractor;
import org.cleartk.ml.viterbi.ViterbiDataWriterFactory;

import tempus.type.Event;
import tempus.type.Section;

import com.google.common.collect.Lists;


/**
 * Class for Event annotations in the THYME corpus
 * Requires Annotations of type Section, Sentence, BaseToken, TimeMention
 * Defined features were those our team (BluLab) used in the 2015 Clinical TempEval challenge
 * Based on examples from the tutorials in the clearTK package: https://cleartk.github.io/cleartk/ 
 * 
 * @author Sumithra Velupillai
 */

public class EventAnnotator extends CleartkSequenceAnnotator<String>{

	private Logger logger = Logger.getLogger(EventAnnotator.class);


	protected List<FeatureExtractor1<BaseToken>> tokenFeatureExtractors;
	private FeatureExtractor1<BaseToken> tokenFeatureExtractor2;

	protected List<CleartkExtractor<BaseToken, BaseToken>> contextExtractors;

	private BioChunking<BaseToken, Event> chunking;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		this.tokenFeatureExtractors = Lists.newArrayList();
		this.tokenFeatureExtractors.add(new CoveredTextExtractor<BaseToken>());
		this.tokenFeatureExtractors.add(new TypePathExtractor<BaseToken>(BaseToken.class, "partOfSpeech"));


		// add window of features before and after
		this.contextExtractors = Lists.newArrayList();

		this.tokenFeatureExtractor2 = new FeatureFunctionExtractor<BaseToken>(
				new CoveredTextExtractor<BaseToken>(),
				new LowerCaseFeatureFunction(),
				new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 2)
				);
		for (FeatureExtractor1<BaseToken> extractor : this.tokenFeatureExtractors) {
			this.contextExtractors.add(new CleartkExtractor<BaseToken, BaseToken>(BaseToken.class, extractor, new Preceding(
					2), new Following(2)));
		}

		// define chunking type
		this.chunking = new BioChunking<BaseToken, Event>(BaseToken.class, Event.class);

	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		
		logger.info("Processing events");

		for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
			List<List<Feature>> tokenFeatureLists = new ArrayList<List<Feature>>();
			

			List<BaseToken> tokens = JCasUtil.selectCovered(jCas, BaseToken.class, sentence);
			for (BaseToken token : JCasUtil.selectCovered(jCas, BaseToken.class, sentence)) {

				List<Feature> features = new ArrayList<Feature>();
				for (FeatureExtractor1<BaseToken> extractor : this.tokenFeatureExtractors) {
					features.addAll(extractor.extract(jCas, token));
				}
				for (CleartkExtractor<BaseToken, BaseToken> extractor : this.contextExtractors) {
					features.addAll(extractor.extractWithin(jCas, token, sentence));
				}
				features.addAll(this.tokenFeatureExtractor2.extract(jCas, token));
				//Adding section feature
				Map<BaseToken,Collection<Section>> coveringSection = JCasUtil.indexCovering(jCas,
						BaseToken.class,
						Section.class);

				List<Section> s = new ArrayList<Section>(coveringSection.get(token));
				String sectionid = "";
				if(s.size()<1)
					sectionid = "NONE";
				else
					sectionid = s.get(0).getId();

				features.add(new Feature("Section", sectionid));
				tokenFeatureLists.add(features);
			}
			if (this.isTraining()) {
				List<Event> events = JCasUtil.selectCovered(jCas, Event.class, sentence);

				List<String> outcomes = this.chunking.createOutcomes(jCas, tokens, events);
				this.dataWriter.write(Instances.toInstances(outcomes, tokenFeatureLists));

			} else {

				List<String> outcomes = this.classifier.classify(tokenFeatureLists);
				this.chunking.createChunks(jCas, tokens, outcomes);
			}

		}
		// add IDs to all Events
		int eventIndex = 1;
		for (Event event : JCasUtil.select(jCas, Event.class)) {
			if (event.getId() == null) {
				String id = "E"+eventIndex;
				event.setId(id);
				eventIndex += 1;
			}
		}
	}



	public static AnalysisEngineDescription getClassifierDescription(String modelFileName)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				EventAnnotator.class,
				CleartkSequenceAnnotator.PARAM_IS_TRAINING, false,
				GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH,
				modelFileName);
	}

	public static AnalysisEngineDescription getWriterDescription(String outputDirectory)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				EventAnnotator.class,
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


