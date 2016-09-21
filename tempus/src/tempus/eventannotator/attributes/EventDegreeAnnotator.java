package tempus.eventannotator.attributes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.ctakes.typesystem.type.syntax.BaseToken;
import org.apache.ctakes.typesystem.type.textspan.Sentence;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.Feature;
import org.cleartk.ml.Instance;
import org.cleartk.ml.feature.extractor.CleartkExtractor;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Following;
import org.cleartk.ml.feature.extractor.CleartkExtractor.Preceding;
import org.cleartk.ml.feature.extractor.CoveredTextExtractor;
import org.cleartk.ml.feature.extractor.TypePathExtractor;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction;
import org.cleartk.ml.feature.function.CharacterNgramFeatureFunction.Orientation;
import org.cleartk.ml.feature.function.FeatureFunctionExtractor;
import org.cleartk.ml.jar.DefaultDataWriterFactory;
import org.cleartk.ml.jar.DirectoryDataWriterFactory;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.ml.liblinear.LibLinearStringOutcomeDataWriter;

import tempus.eventannotator.attributes.feature.EventModalityAttributeExtractor;
import tempus.eventannotator.attributes.feature.EventPolarityAttributeExtractor;
import tempus.eventannotator.attributes.feature.EventTypeAttributeExtractor;
import tempus.type.Event;
import tempus.type.Section;

import com.google.common.collect.Lists;


public class EventDegreeAnnotator extends CleartkAnnotator<String> {

	private Logger logger = Logger.getLogger(EventDegreeAnnotator.class);

	public static final String PARAM_KEYWORDFILE = "keywords";
	@ConfigurationParameter(
			name = PARAM_KEYWORDFILE,
			mandatory = true,
			defaultValue = "lexicalKnowledgeBase_assertions.txt",
			description = "File with semantic modifier keywords"
			)
	private String keywordfile;

	protected List<CleartkExtractor<Event, BaseToken>> contextExtractors;
	protected List<CleartkExtractor<Event, Event>> contextEventExtractors;

	protected HashMap<String, ArrayList<String>> keywords;


	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);


		// add window of features before and after
		this.contextExtractors = Lists.newArrayList();
		this.contextExtractors.add(new CleartkExtractor<Event, BaseToken>(
				BaseToken.class,
				new CoveredTextExtractor<BaseToken>(),
				new Preceding(4),
				new Following(4)));

		this.contextExtractors.add(new CleartkExtractor<Event, BaseToken>(
				BaseToken.class,
				new TypePathExtractor<BaseToken>(BaseToken.class, "partOfSpeech"),
				new Preceding(4),
				new Following(4)));

		this.contextExtractors.add(new CleartkExtractor<Event, BaseToken>(
				BaseToken.class,
				new FeatureFunctionExtractor<BaseToken>(
						new CoveredTextExtractor<BaseToken>(),
						new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 2),
						new CharacterNgramFeatureFunction(Orientation.RIGHT_TO_LEFT, 0, 3)),
						new Preceding(4),
						new Following(4)));

		this.contextEventExtractors = Lists.newArrayList();
		this.contextEventExtractors.add(new CleartkExtractor<Event, Event>(
				Event.class,
				new EventModalityAttributeExtractor<Event>(),
				new Preceding(2)));

		this.contextEventExtractors.add(new CleartkExtractor<Event, Event>(
				Event.class,
				new EventPolarityAttributeExtractor<Event>(),
				new Preceding(2)));
		this.contextEventExtractors.add(new CleartkExtractor<Event, Event>(
				Event.class,
				new EventTypeAttributeExtractor<Event>(),
				new Preceding(2)));

		try {
			keywords = populateKeywordList();
		} catch (IOException e) {
			logger.error("Error opening the keyword file: "+this.keywordfile);
			e.printStackTrace();
		}
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		logger.info("Processing event degrees");


		for(Section section : JCasUtil.select(jCas, Section.class)){
			for (Sentence sentence : JCasUtil.selectCovered(jCas, Sentence.class, section)) {
				List<List<Feature>> tokenFeatureLists = new ArrayList<List<Feature>>();

				for (Event event : JCasUtil.selectCovered(jCas, Event.class, sentence)) {
					//System.out.println(token.getCoveredText());
					List<Feature> features = new ArrayList<Feature>();

					for (CleartkExtractor<Event, BaseToken> extractor : this.contextExtractors) {
						features.addAll(extractor.extractWithin(jCas, event, sentence));
					}

					for(CleartkExtractor<Event, Event> extractor : this.contextEventExtractors){
						features.addAll(extractor.extractWithin(jCas, event, section));
					}

					features.addAll(getKeywordFeature(sentence));
					features.add(new Feature("SectionId", section.getId()));
					tokenFeatureLists.add(features);
					if (this.isTraining()) {

						String attribute = event.getDegree();
						if (attribute == null) {
							attribute = "N/A";
						}
						Instance<String> instance = new Instance<String>();
						instance.addAll(features);
						instance.setOutcome(attribute);
						this.dataWriter.write(instance);


					} else {
						String label = this.classifier.classify(features);
						event.setDegree(label);
					}
				}


			}
		}

	}


	public static AnalysisEngineDescription getClassifierDescription(String modelFileName, String keywordFile)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				EventDegreeAnnotator.class,
				EventDegreeAnnotator.PARAM_KEYWORDFILE,
				keywordFile,
				CleartkAnnotator.PARAM_IS_TRAINING, false,
				GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH,
				modelFileName);
	}




	public static AnalysisEngineDescription getWriterDescription(String outputDirectory, String keywordFile)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				EventDegreeAnnotator.class,
				EventDegreeAnnotator.PARAM_KEYWORDFILE,
				keywordFile,
				CleartkAnnotator.PARAM_IS_TRAINING, true,
				CleartkAnnotator.PARAM_DATA_WRITER_FACTORY_CLASS_NAME,
				DefaultDataWriterFactory.class.getName(),
				DefaultDataWriterFactory.PARAM_DATA_WRITER_CLASS_NAME,
				LibLinearStringOutcomeDataWriter.class.getName(),
				DirectoryDataWriterFactory.PARAM_OUTPUT_DIRECTORY,
				outputDirectory);
	}

	private HashMap<String, ArrayList<String>> populateKeywordList() throws IOException{
		String DEFAULT_KEYWORD_FILE = this.keywordfile;
		BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_KEYWORD_FILE));

		HashMap<String, ArrayList<String>> cues = new HashMap<String, ArrayList<String>>();
		String line = reader.readLine();
		while(line !=null){
			String[] cols = line.split("\t");
			String literal = cols[0];
			String category = cols[1];
			ArrayList<String> cue_values = cues.get(category);
			if(cue_values==null)
				cue_values = new ArrayList<String>();
			cue_values.add(literal);
			sortList(cue_values);
			cues.put(category, cue_values);
			line = reader.readLine();
		}
		reader.close();

		return cues;



	}

	private List<Feature> getKeywordFeature(Sentence sentence){
		List<Feature> kwf = new ArrayList<Feature>();

		ArrayList<String> cuetypes = new ArrayList<String>();
		cuetypes.add("LITTLE");
		cuetypes.add("MOST");
		for(String c : cuetypes){
			for(String k : this.keywords.get(c)){
				if(sentence.getCoveredText().toLowerCase().contains(k)){
					kwf.add(new Feature("CueInSentence", c));
					break;
				}
			}
		}
		if(kwf.size()==0)
			kwf.add(new Feature("CueInSentence", "NONE"));

		return kwf;
	}

	private static void sortList(ArrayList<String> list_to_sort) throws IOException{

		Collections.sort(list_to_sort,new Comparator<String>()
				{
			public int compare(String s1,String s2)
			{
				return s2.length() - s1.length();
			}
				});


	}
}



