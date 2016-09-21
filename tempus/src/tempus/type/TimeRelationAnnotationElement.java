

/* First created by JCasGen Thu Apr 28 11:48:38 BST 2016 */
package tempus.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * XML source: /Users/sumithra/git/tempus/tempus/src/tempus/type/typeSystemDescriptor.xml
 * @generated */
public class TimeRelationAnnotationElement extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TimeRelationAnnotationElement.class);
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
  protected TimeRelationAnnotationElement() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TimeRelationAnnotationElement(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TimeRelationAnnotationElement(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TimeRelationAnnotationElement(JCas jcas, int begin, int end) {
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
  //* Feature: Id

  /** getter for Id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (TimeRelationAnnotationElement_Type.featOkTst && ((TimeRelationAnnotationElement_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "tempus.type.TimeRelationAnnotationElement");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TimeRelationAnnotationElement_Type)jcasType).casFeatCode_Id);}
    
  /** setter for Id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (TimeRelationAnnotationElement_Type.featOkTst && ((TimeRelationAnnotationElement_Type)jcasType).casFeat_Id == null)
      jcasType.jcas.throwFeatMissing("Id", "tempus.type.TimeRelationAnnotationElement");
    jcasType.ll_cas.ll_setStringValue(addr, ((TimeRelationAnnotationElement_Type)jcasType).casFeatCode_Id, v);}    
  }

    