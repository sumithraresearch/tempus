

/* First created by JCasGen Tue Sep 20 10:40:49 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class DocTime extends TimeRelationAnnotationElement {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocTime.class);
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
  protected DocTime() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DocTime(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DocTime(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DocTime(JCas jcas, int begin, int end) {
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
    if (DocTime_Type.featOkTst && ((DocTime_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.DocTime");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DocTime_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (DocTime_Type.featOkTst && ((DocTime_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.DocTime");
    jcasType.ll_cas.ll_setStringValue(addr, ((DocTime_Type)jcasType).casFeatCode_id, v);}    
  }

    