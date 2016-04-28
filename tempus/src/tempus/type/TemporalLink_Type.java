
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
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Apr 28 12:03:49 BST 2016
 * @generated */
public class TemporalLink_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TemporalLink_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TemporalLink_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TemporalLink(addr, TemporalLink_Type.this);
  			   TemporalLink_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TemporalLink(addr, TemporalLink_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TemporalLink.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("tempus.type.TemporalLink");
 
  /** @generated */
  final Feature casFeat_fromId;
  /** @generated */
  final int     casFeatCode_fromId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getFromId(int addr) {
        if (featOkTst && casFeat_fromId == null)
      jcas.throwFeatMissing("fromId", "tempus.type.TemporalLink");
    return ll_cas.ll_getRefValue(addr, casFeatCode_fromId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFromId(int addr, int v) {
        if (featOkTst && casFeat_fromId == null)
      jcas.throwFeatMissing("fromId", "tempus.type.TemporalLink");
    ll_cas.ll_setRefValue(addr, casFeatCode_fromId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_toId;
  /** @generated */
  final int     casFeatCode_toId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getToId(int addr) {
        if (featOkTst && casFeat_toId == null)
      jcas.throwFeatMissing("toId", "tempus.type.TemporalLink");
    return ll_cas.ll_getRefValue(addr, casFeatCode_toId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setToId(int addr, int v) {
        if (featOkTst && casFeat_toId == null)
      jcas.throwFeatMissing("toId", "tempus.type.TemporalLink");
    ll_cas.ll_setRefValue(addr, casFeatCode_toId, v);}
    
  
 
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
      jcas.throwFeatMissing("id", "tempus.type.TemporalLink");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "tempus.type.TemporalLink");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_temporalLinkType;
  /** @generated */
  final int     casFeatCode_temporalLinkType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTemporalLinkType(int addr) {
        if (featOkTst && casFeat_temporalLinkType == null)
      jcas.throwFeatMissing("temporalLinkType", "tempus.type.TemporalLink");
    return ll_cas.ll_getStringValue(addr, casFeatCode_temporalLinkType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTemporalLinkType(int addr, String v) {
        if (featOkTst && casFeat_temporalLinkType == null)
      jcas.throwFeatMissing("temporalLinkType", "tempus.type.TemporalLink");
    ll_cas.ll_setStringValue(addr, casFeatCode_temporalLinkType, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public TemporalLink_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_fromId = jcas.getRequiredFeatureDE(casType, "fromId", "tempus.type.TimeRelationAnnotationElement", featOkTst);
    casFeatCode_fromId  = (null == casFeat_fromId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_fromId).getCode();

 
    casFeat_toId = jcas.getRequiredFeatureDE(casType, "toId", "tempus.type.TimeRelationAnnotationElement", featOkTst);
    casFeatCode_toId  = (null == casFeat_toId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_toId).getCode();

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_temporalLinkType = jcas.getRequiredFeatureDE(casType, "temporalLinkType", "uima.cas.String", featOkTst);
    casFeatCode_temporalLinkType  = (null == casFeat_temporalLinkType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_temporalLinkType).getCode();

  }
}



    