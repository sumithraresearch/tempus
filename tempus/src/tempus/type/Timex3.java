

/* First created by JCasGen Thu Apr 28 11:48:38 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class Timex3 extends TimeRelationAnnotationElement {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Timex3.class);
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
  protected Timex3() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Timex3(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Timex3(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Timex3(JCas jcas, int begin, int end) {
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
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.Timex3");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "tempus.type.Timex3");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: timex3Type

  /** getter for timex3Type - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTimex3Type() {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_timex3Type == null)
      jcasType.jcas.throwFeatMissing("timex3Type", "tempus.type.Timex3");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_timex3Type);}
    
  /** setter for timex3Type - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTimex3Type(String v) {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_timex3Type == null)
      jcasType.jcas.throwFeatMissing("timex3Type", "tempus.type.Timex3");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_timex3Type, v);}    
   
    
  //*--------------*
  //* Feature: mod

  /** getter for mod - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMod() {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_mod == null)
      jcasType.jcas.throwFeatMissing("mod", "tempus.type.Timex3");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_mod);}
    
  /** setter for mod - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMod(String v) {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_mod == null)
      jcasType.jcas.throwFeatMissing("mod", "tempus.type.Timex3");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_mod, v);}    
   
    
  //*--------------*
  //* Feature: val

  /** getter for val - gets 
   * @generated
   * @return value of the feature 
   */
  public String getVal() {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_val == null)
      jcasType.jcas.throwFeatMissing("val", "tempus.type.Timex3");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_val);}
    
  /** setter for val - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setVal(String v) {
    if (Timex3_Type.featOkTst && ((Timex3_Type)jcasType).casFeat_val == null)
      jcasType.jcas.throwFeatMissing("val", "tempus.type.Timex3");
    jcasType.ll_cas.ll_setStringValue(addr, ((Timex3_Type)jcasType).casFeatCode_val, v);}    
  }

    