
/* First created by JCasGen Thu Apr 14 14:56:21 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Apr 28 12:03:49 BST 2016
 * @generated */
public class Event_Type extends TimeRelationAnnotationElement_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Event_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Event_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Event(addr, Event_Type.this);
  			   Event_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Event(addr, Event_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Event.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("tempus.type.Event");
 
  /** @generated */
  final Feature casFeat_contextualaspect;
  /** @generated */
  final int     casFeatCode_contextualaspect;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getContextualaspect(int addr) {
        if (featOkTst && casFeat_contextualaspect == null)
      jcas.throwFeatMissing("contextualaspect", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_contextualaspect);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setContextualaspect(int addr, String v) {
        if (featOkTst && casFeat_contextualaspect == null)
      jcas.throwFeatMissing("contextualaspect", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_contextualaspect, v);}
    
  
 
  /** @generated */
  final Feature casFeat_contextualmodality;
  /** @generated */
  final int     casFeatCode_contextualmodality;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getContextualmodality(int addr) {
        if (featOkTst && casFeat_contextualmodality == null)
      jcas.throwFeatMissing("contextualmodality", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_contextualmodality);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setContextualmodality(int addr, String v) {
        if (featOkTst && casFeat_contextualmodality == null)
      jcas.throwFeatMissing("contextualmodality", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_contextualmodality, v);}
    
  
 
  /** @generated */
  final Feature casFeat_polarity;
  /** @generated */
  final int     casFeatCode_polarity;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPolarity(int addr) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_polarity);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPolarity(int addr, String v) {
        if (featOkTst && casFeat_polarity == null)
      jcas.throwFeatMissing("polarity", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_polarity, v);}
    
  
 
  /** @generated */
  final Feature casFeat_degree;
  /** @generated */
  final int     casFeatCode_degree;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDegree(int addr) {
        if (featOkTst && casFeat_degree == null)
      jcas.throwFeatMissing("degree", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_degree);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDegree(int addr, String v) {
        if (featOkTst && casFeat_degree == null)
      jcas.throwFeatMissing("degree", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_degree, v);}
    
  
 
  /** @generated */
  final Feature casFeat_doctimerel;
  /** @generated */
  final int     casFeatCode_doctimerel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDoctimerel(int addr) {
        if (featOkTst && casFeat_doctimerel == null)
      jcas.throwFeatMissing("doctimerel", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_doctimerel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDoctimerel(int addr, String v) {
        if (featOkTst && casFeat_doctimerel == null)
      jcas.throwFeatMissing("doctimerel", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_doctimerel, v);}
    
  
 
  /** @generated */
  final Feature casFeat_eventClass;
  /** @generated */
  final int     casFeatCode_eventClass;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getEventClass(int addr) {
        if (featOkTst && casFeat_eventClass == null)
      jcas.throwFeatMissing("eventClass", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_eventClass);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setEventClass(int addr, String v) {
        if (featOkTst && casFeat_eventClass == null)
      jcas.throwFeatMissing("eventClass", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_eventClass, v);}
    
  
 
  /** @generated */
  final Feature casFeat_permanence;
  /** @generated */
  final int     casFeatCode_permanence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPermanence(int addr) {
        if (featOkTst && casFeat_permanence == null)
      jcas.throwFeatMissing("permanence", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_permanence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPermanence(int addr, String v) {
        if (featOkTst && casFeat_permanence == null)
      jcas.throwFeatMissing("permanence", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_permanence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "tempus.type.Event");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "tempus.type.Event");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Event_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_contextualaspect = jcas.getRequiredFeatureDE(casType, "contextualaspect", "uima.cas.String", featOkTst);
    casFeatCode_contextualaspect  = (null == casFeat_contextualaspect) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_contextualaspect).getCode();

 
    casFeat_contextualmodality = jcas.getRequiredFeatureDE(casType, "contextualmodality", "uima.cas.String", featOkTst);
    casFeatCode_contextualmodality  = (null == casFeat_contextualmodality) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_contextualmodality).getCode();

 
    casFeat_polarity = jcas.getRequiredFeatureDE(casType, "polarity", "uima.cas.String", featOkTst);
    casFeatCode_polarity  = (null == casFeat_polarity) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_polarity).getCode();

 
    casFeat_degree = jcas.getRequiredFeatureDE(casType, "degree", "uima.cas.String", featOkTst);
    casFeatCode_degree  = (null == casFeat_degree) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_degree).getCode();

 
    casFeat_doctimerel = jcas.getRequiredFeatureDE(casType, "doctimerel", "uima.cas.String", featOkTst);
    casFeatCode_doctimerel  = (null == casFeat_doctimerel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_doctimerel).getCode();

 
    casFeat_eventClass = jcas.getRequiredFeatureDE(casType, "eventClass", "uima.cas.String", featOkTst);
    casFeatCode_eventClass  = (null == casFeat_eventClass) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_eventClass).getCode();

 
    casFeat_permanence = jcas.getRequiredFeatureDE(casType, "permanence", "uima.cas.String", featOkTst);
    casFeatCode_permanence  = (null == casFeat_permanence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_permanence).getCode();

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

  }
}



    