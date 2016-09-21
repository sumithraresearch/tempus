
/* First created by JCasGen Thu Apr 28 11:48:38 BST 2016 */
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
public class Timex3_Type extends TimeRelationAnnotationElement_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Timex3_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Timex3_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Timex3(addr, Timex3_Type.this);
  			   Timex3_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Timex3(addr, Timex3_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Timex3.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("tempus.type.Timex3");
 
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
      jcas.throwFeatMissing("id", "tempus.type.Timex3");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "tempus.type.Timex3");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_timex3Type;
  /** @generated */
  final int     casFeatCode_timex3Type;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTimex3Type(int addr) {
        if (featOkTst && casFeat_timex3Type == null)
      jcas.throwFeatMissing("timex3Type", "tempus.type.Timex3");
    return ll_cas.ll_getStringValue(addr, casFeatCode_timex3Type);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTimex3Type(int addr, String v) {
        if (featOkTst && casFeat_timex3Type == null)
      jcas.throwFeatMissing("timex3Type", "tempus.type.Timex3");
    ll_cas.ll_setStringValue(addr, casFeatCode_timex3Type, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mod;
  /** @generated */
  final int     casFeatCode_mod;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMod(int addr) {
        if (featOkTst && casFeat_mod == null)
      jcas.throwFeatMissing("mod", "tempus.type.Timex3");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mod);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMod(int addr, String v) {
        if (featOkTst && casFeat_mod == null)
      jcas.throwFeatMissing("mod", "tempus.type.Timex3");
    ll_cas.ll_setStringValue(addr, casFeatCode_mod, v);}
    
  
 
  /** @generated */
  final Feature casFeat_val;
  /** @generated */
  final int     casFeatCode_val;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getVal(int addr) {
        if (featOkTst && casFeat_val == null)
      jcas.throwFeatMissing("val", "tempus.type.Timex3");
    return ll_cas.ll_getStringValue(addr, casFeatCode_val);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setVal(int addr, String v) {
        if (featOkTst && casFeat_val == null)
      jcas.throwFeatMissing("val", "tempus.type.Timex3");
    ll_cas.ll_setStringValue(addr, casFeatCode_val, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Timex3_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_timex3Type = jcas.getRequiredFeatureDE(casType, "timex3Type", "uima.cas.String", featOkTst);
    casFeatCode_timex3Type  = (null == casFeat_timex3Type) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_timex3Type).getCode();

 
    casFeat_mod = jcas.getRequiredFeatureDE(casType, "mod", "uima.cas.String", featOkTst);
    casFeatCode_mod  = (null == casFeat_mod) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mod).getCode();

 
    casFeat_val = jcas.getRequiredFeatureDE(casType, "val", "uima.cas.String", featOkTst);
    casFeatCode_val  = (null == casFeat_val) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_val).getCode();

  }
}



    