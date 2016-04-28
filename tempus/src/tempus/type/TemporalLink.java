

/* First created by JCasGen Thu Apr 28 11:48:38 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Apr 28 12:03:49 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class TemporalLink extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TemporalLink.class);
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
  protected TemporalLink() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TemporalLink(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TemporalLink(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TemporalLink(JCas jcas, int begin, int end) {
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
  //* Feature: fromId

  /** getter for fromId - gets 
   * @generated
   * @return value of the feature 
   */
  public TimeRelationAnnotationElement getFromId() {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_fromId == null)
      jcasType.jcas.throwFeatMissing("fromId", "tempus.type.TemporalLink");
    return (TimeRelationAnnotationElement)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_fromId)));}
    
  /** setter for fromId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFromId(TimeRelationAnnotationElement v) {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_fromId == null)
      jcasType.jcas.throwFeatMissing("fromId", "tempus.type.TemporalLink");
    jcasType.ll_cas.ll_setRefValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_fromId, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: toId

  /** getter for toId - gets 
   * @generated
   * @return value of the feature 
   */
  public TimeRelationAnnotationElement getToId() {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_toId == null)
      jcasType.jcas.throwFeatMissing("toId", "tempus.type.TemporalLink");
    return (TimeRelationAnnotationElement)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_toId)));}
    
  /** setter for toId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setToId(TimeRelationAnnotationElement v) {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_toId == null)
      jcasType.jcas.throwFeatMissing("toId", "tempus.type.TemporalLink");
    jcasType.ll_cas.ll_setRefValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_toId, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.TemporalLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.TemporalLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: temporalLinkType

  /** getter for temporalLinkType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTemporalLinkType() {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_temporalLinkType == null)
      jcasType.jcas.throwFeatMissing("temporalLinkType", "tempus.type.TemporalLink");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_temporalLinkType);}
    
  /** setter for temporalLinkType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTemporalLinkType(String v) {
    if (TemporalLink_Type.featOkTst && ((TemporalLink_Type)jcasType).casFeat_temporalLinkType == null)
      jcasType.jcas.throwFeatMissing("temporalLinkType", "tempus.type.TemporalLink");
    jcasType.ll_cas.ll_setStringValue(addr, ((TemporalLink_Type)jcasType).casFeatCode_temporalLinkType, v);}    
  }

    