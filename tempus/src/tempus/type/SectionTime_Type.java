
/* First created by JCasGen Tue Sep 20 10:40:49 BST 2016 */
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

/** 
 * Updated by JCasGen Tue Sep 20 11:53:12 BST 2016
 * @generated */
public class SectionTime_Type extends TimeRelationAnnotationElement_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SectionTime_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SectionTime_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SectionTime(addr, SectionTime_Type.this);
  			   SectionTime_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SectionTime(addr, SectionTime_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SectionTime.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("tempus.type.SectionTime");
 
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
      jcas.throwFeatMissing("id", "tempus.type.SectionTime");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "tempus.type.SectionTime");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sectionTimeType;
  /** @generated */
  final int     casFeatCode_sectionTimeType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSectionTimeType(int addr) {
        if (featOkTst && casFeat_sectionTimeType == null)
      jcas.throwFeatMissing("sectionTimeType", "tempus.type.SectionTime");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionTimeType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionTimeType(int addr, String v) {
        if (featOkTst && casFeat_sectionTimeType == null)
      jcas.throwFeatMissing("sectionTimeType", "tempus.type.SectionTime");
    ll_cas.ll_setStringValue(addr, casFeatCode_sectionTimeType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public SectionTime_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_sectionTimeType = jcas.getRequiredFeatureDE(casType, "sectionTimeType", "uima.cas.String", featOkTst);
    casFeatCode_sectionTimeType  = (null == casFeat_sectionTimeType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionTimeType).getCode();

  }
}



    