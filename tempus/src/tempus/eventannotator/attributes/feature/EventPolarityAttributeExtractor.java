package tempus.eventannotator.attributes.feature;

import java.util.Collections;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;

import tempus.type.Event;




public class EventPolarityAttributeExtractor<T extends Annotation> implements NamedFeatureExtractor1<T> {

	private String featureName = "polarity";

	public String getFeatureName() {
		return this.featureName;
	}

	public List<Feature> extract(JCas jCas, Annotation focusAnnotation) {

		Event e = (Event) focusAnnotation;
		String eventattributetype = e.getPolarity();
		if(eventattributetype==null)
			eventattributetype = "POS";
		// create a single feature from the text
		Feature feature = new Feature(this.featureName, eventattributetype);
		return Collections.singletonList(feature);
	}
}




