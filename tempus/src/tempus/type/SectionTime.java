

/* First created by JCasGen Tue Sep 20 10:40:49 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class SectionTime extends TimeRelationAnnotationElement {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SectionTime.class);
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
  protected SectionTime() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SectionTime(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SectionTime(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SectionTime(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (SectionTime_Type.featOkTst && ((SectionTime_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.SectionTime");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SectionTime_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (SectionTime_Type.featOkTst && ((SectionTime_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.SectionTime");
    jcasType.ll_cas.ll_setStringValue(addr, ((SectionTime_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: sectionTimeType

  /** getter for sectionTimeType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSectionTimeType() {
    if (SectionTime_Type.featOkTst && ((SectionTime_Type)jcasType).casFeat_sectionTimeType == null)
      jcasType.jcas.throwFeatMissing("sectionTimeType", "tempus.type.SectionTime");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SectionTime_Type)jcasType).casFeatCode_sectionTimeType);}
    
  /** setter for sectionTimeType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionTimeType(String v) {
    if (SectionTime_Type.featOkTst && ((SectionTime_Type)jcasType).casFeat_sectionTimeType == null)
      jcasType.jcas.throwFeatMissing("sectionTimeType", "tempus.type.SectionTime");
    jcasType.ll_cas.ll_setStringValue(addr, ((SectionTime_Type)jcasType).casFeatCode_sectionTimeType, v);}    
  }

    