

/* First created by JCasGen Thu Apr 14 14:56:21 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class Event extends TimeRelationAnnotationElement {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Event.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Event() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Event(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Event(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Event(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: contextualaspect

  /** getter for contextualaspect - gets 
   * @generated
   * @return value of the feature 
   */
  public String getContextualaspect() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_contextualaspect == null)
      jcasType.jcas.throwFeatMissing("contextualaspect", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_contextualaspect);}
    
  /** setter for contextualaspect - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setContextualaspect(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_contextualaspect == null)
      jcasType.jcas.throwFeatMissing("contextualaspect", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_contextualaspect, v);}    
   
    
  //*--------------*
  //* Feature: contextualmodality

  /** getter for contextualmodality - gets 
   * @generated
   * @return value of the feature 
   */
  public String getContextualmodality() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_contextualmodality == null)
      jcasType.jcas.throwFeatMissing("contextualmodality", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_contextualmodality);}
    
  /** setter for contextualmodality - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setContextualmodality(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_contextualmodality == null)
      jcasType.jcas.throwFeatMissing("contextualmodality", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_contextualmodality, v);}    
   
    
  //*--------------*
  //* Feature: polarity

  /** getter for polarity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPolarity() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_polarity);}
    
  /** setter for polarity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPolarity(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_polarity == null)
      jcasType.jcas.throwFeatMissing("polarity", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_polarity, v);}    
   
    
  //*--------------*
  //* Feature: degree

  /** getter for degree - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDegree() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_degree == null)
      jcasType.jcas.throwFeatMissing("degree", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_degree);}
    
  /** setter for degree - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDegree(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_degree == null)
      jcasType.jcas.throwFeatMissing("degree", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_degree, v);}    
   
    
  //*--------------*
  //* Feature: doctimerel

  /** getter for doctimerel - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDoctimerel() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_doctimerel == null)
      jcasType.jcas.throwFeatMissing("doctimerel", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_doctimerel);}
    
  /** setter for doctimerel - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDoctimerel(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_doctimerel == null)
      jcasType.jcas.throwFeatMissing("doctimerel", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_doctimerel, v);}    
   
    
  //*--------------*
  //* Feature: eventClass

  /** getter for eventClass - gets 
   * @generated
   * @return value of the feature 
   */
  public String getEventClass() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_eventClass == null)
      jcasType.jcas.throwFeatMissing("eventClass", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_eventClass);}
    
  /** setter for eventClass - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEventClass(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_eventClass == null)
      jcasType.jcas.throwFeatMissing("eventClass", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_eventClass, v);}    
   
    
  //*--------------*
  //* Feature: permanence

  /** getter for permanence - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPermanence() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_permanence == null)
      jcasType.jcas.throwFeatMissing("permanence", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_permanence);}
    
  /** setter for permanence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPermanence(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_permanence == null)
      jcasType.jcas.throwFeatMissing("permanence", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_permanence, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.Event");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Event_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Event_Type.featOkTst && ((Event_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.Event");
    jcasType.ll_cas.ll_setStringValue(addr, ((Event_Type)jcasType).casFeatCode_id, v);}    
  }

    