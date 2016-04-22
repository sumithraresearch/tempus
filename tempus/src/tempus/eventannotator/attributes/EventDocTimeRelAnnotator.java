package tempus.eventannotator.attributes;

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
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.CleartkSequenceAnnotator;
import org.cleartk.ml.Feature;
import org.cleartk.ml.Instance;
import org.cleartk.ml.feature.extractor.CleartkExtractor;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Following;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Preceding;
import org.cleartk.ml.feature.extractor.CoveredTextExtractor;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;
import org.cleartk.ml.feature.extractor.TypePathExtractor;
import org.cleartk.ml.jar.DefaultDataWriterFactory;
import org.cleartk.ml.jar.DirectoryDataWriterFactory;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.ml.liblinear.LibLinearStringOutcomeDataWriter;

import tempus.type.Event;
import tempus.type.Section;

import com.google.common.collect.Lists;



public class EventDocTimeRelAnnotator extends CleartkAnnotator<String> {
	
	private Logger logger = Logger.getLogger(EventDocTimeRelAnnotator.class);


	private List<NamedFeatureExtractor1<BaseToken>> tokenFeatureExtractors;
	private List<CleartkExtractor<Event, BaseToken>> contextExtractors;



	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		// add features: word, character pattern, stem, pos
		this.tokenFeatureExtractors = Lists.newArrayList();

		this.tokenFeatureExtractors.add(new CoveredTextExtractor<BaseToken>());

		this.tokenFeatureExtractors.add(new TypePathExtractor<BaseToken>(BaseToken.class, "partOfSpeech"));
		this.tokenFeatureExtractors.add(new TypePathExtractor<BaseToken>(BaseToken.class, "normalizedForm"));

		// add window of features before and after
		this.contextExtractors = Lists.newArrayList();
		for (FeatureExtractor1<BaseToken> extractor : this.tokenFeatureExtractors) {
			this.contextExtractors.add(new CleartkExtractor<Event, BaseToken>(BaseToken.class, extractor, new Preceding(
					5), new Following(5)));
		}

	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		logger.info("processing doctimerels");

		for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {
			List<List<Feature>> tokenFeatureLists = new ArrayList<List<Feature>>();

			for (Event event : JCasUtil.selectCovered(jCas, Event.class, sentence)) {

				List<Feature> features = new ArrayList<Feature>();

				for (CleartkExtractor<Event, BaseToken> extractor : this.contextExtractors) {
					features.addAll(extractor.extractWithin(jCas, event, sentence));
				}
				List<BaseToken> tokens = JCasUtil.selectCovered(jCas, BaseToken.class, event);
				for(BaseToken token : tokens){
					for (FeatureExtractor1<BaseToken> extractor : this.tokenFeatureExtractors) {
						features.addAll(extractor.extract(jCas, token));
					}
				}

				Map<Event,Collection<Section>> coveringSection = JCasUtil.indexCovering(jCas,
						Event.class,
						Section.class);

				List<Section> s = new ArrayList<Section>(coveringSection.get(event));
				String sectionid;
				if(s.size()>0)
					sectionid = s.get(0).getId();
				else
					sectionid = "NO_SECTION_FOUND";
				features.add(new Feature("Section", sectionid));
				String tense = "NAN";
				for(BaseToken bt : JCasUtil.selectCovered(jCas, BaseToken.class, sentence)){
					String pos = bt.getPartOfSpeech();
					if(pos.equals("VBD")){
						tense = "PAST_TENSE";
					}
					String word = bt.getCoveredText();
					if(word.equalsIgnoreCase("will")){
						tense = "FUTURE";
					}
				}
				features.add(new Feature("SENTENCETENSE", tense));

				tokenFeatureLists.add(features);
				if (this.isTraining()) {

					String attribute = event.getDoctimerel();
					if (attribute == null) {
						attribute = "OVERLAP";
					}
					Instance<String> instance = new Instance<String>();
					instance.addAll(features);
					instance.setOutcome(attribute);
					this.dataWriter.write(instance);


				} else {
					String label = this.classifier.classify(features);
					event.setDoctimerel(label);
				}
			}

		}

	}


	public static AnalysisEngineDescription getClassifierDescription(String modelFileName)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				EventDocTimeRelAnnotator.class,
				CleartkSequenceAnnotator.PARAM_IS_TRAINING, false,
				GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH,
				modelFileName);
	}

	public static AnalysisEngineDescription getWriterDescription(String outputDirectory)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				EventDocTimeRelAnnotator.class,
				CleartkAnnotator.PARAM_IS_TRAINING, true,
				DirectoryDataWriterFactory.PARAM_OUTPUT_DIRECTORY,
				outputDirectory,
				DefaultDataWriterFactory.PARAM_DATA_WRITER_CLASS_NAME,
				LibLinearStringOutcomeDataWriter.class.getName());
	}

	


}
