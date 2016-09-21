

/* First created by JCasGen Thu Apr 14 14:53:30 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class DocumentCreationTime extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocumentCreationTime.class);
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
  protected DocumentCreationTime() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocumentCreationTime(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocumentCreationTime(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocumentCreationTime(JCas jcas, int begin, int end) {
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
  //* Feature: filename

  /** getter for filename - gets 
   * @generated
   * @return value of the feature 
   */
  public String getFilename() {
    if (DocumentCreationTime_Type.featOkTst && ((DocumentCreationTime_Type)jcasType).casFeat_filename == null)
      jcasType.jcas.throwFeatMissing("filename", "tempus.type.DocumentCreationTime");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentCreationTime_Type)jcasType).casFeatCode_filename);}
    
  /** setter for filename - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setFilename(String v) {
    if (DocumentCreationTime_Type.featOkTst && ((DocumentCreationTime_Type)jcasType).casFeat_filename == null)
      jcasType.jcas.throwFeatMissing("filename", "tempus.type.DocumentCreationTime");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentCreationTime_Type)jcasType).casFeatCode_filename, v);}    
   
    
  //*--------------*
  //* Feature: value

  /** getter for value - gets 
   * @generated
   * @return value of the feature 
   */
  public String getValue() {
    if (DocumentCreationTime_Type.featOkTst && ((DocumentCreationTime_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "tempus.type.DocumentCreationTime");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentCreationTime_Type)jcasType).casFeatCode_value);}
    
  /** setter for value - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setValue(String v) {
    if (DocumentCreationTime_Type.featOkTst && ((DocumentCreationTime_Type)jcasType).casFeat_value == null)
      jcasType.jcas.throwFeatMissing("value", "tempus.type.DocumentCreationTime");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentCreationTime_Type)jcasType).casFeatCode_value, v);}    
   
    
  //*--------------*
  //* Feature: timexId

  /** getter for timexId - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTimexId() {
    if (DocumentCreationTime_Type.featOkTst && ((DocumentCreationTime_Type)jcasType).casFeat_timexId == null)
      jcasType.jcas.throwFeatMissing("timexId", "tempus.type.DocumentCreationTime");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocumentCreationTime_Type)jcasType).casFeatCode_timexId);}
    
  /** setter for timexId - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTimexId(String v) {
    if (DocumentCreationTime_Type.featOkTst && ((DocumentCreationTime_Type)jcasType).casFeat_timexId == null)
      jcasType.jcas.throwFeatMissing("timexId", "tempus.type.DocumentCreationTime");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocumentCreationTime_Type)jcasType).casFeatCode_timexId, v);}    
  }

    