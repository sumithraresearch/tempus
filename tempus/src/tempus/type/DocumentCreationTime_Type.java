
/* First created by JCasGen Thu Apr 14 14:53:30 BST 2016 */
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
public class DocumentCreationTime_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocumentCreationTime_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocumentCreationTime_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocumentCreationTime(addr, DocumentCreationTime_Type.this);
  			   DocumentCreationTime_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocumentCreationTime(addr, DocumentCreationTime_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocumentCreationTime.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("tempus.type.DocumentCreationTime");
 
  /** @generated */
  final Feature casFeat_filename;
  /** @generated */
  final int     casFeatCode_filename;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getFilename(int addr) {
        if (featOkTst && casFeat_filename == null)
      jcas.throwFeatMissing("filename", "tempus.type.DocumentCreationTime");
    return ll_cas.ll_getStringValue(addr, casFeatCode_filename);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setFilename(int addr, String v) {
        if (featOkTst && casFeat_filename == null)
      jcas.throwFeatMissing("filename", "tempus.type.DocumentCreationTime");
    ll_cas.ll_setStringValue(addr, casFeatCode_filename, v);}
    
  
 
  /** @generated */
  final Feature casFeat_value;
  /** @generated */
  final int     casFeatCode_value;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getValue(int addr) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "tempus.type.DocumentCreationTime");
    return ll_cas.ll_getStringValue(addr, casFeatCode_value);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setValue(int addr, String v) {
        if (featOkTst && casFeat_value == null)
      jcas.throwFeatMissing("value", "tempus.type.DocumentCreationTime");
    ll_cas.ll_setStringValue(addr, casFeatCode_value, v);}
    
  
 
  /** @generated */
  final Feature casFeat_timexId;
  /** @generated */
  final int     casFeatCode_timexId;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTimexId(int addr) {
        if (featOkTst && casFeat_timexId == null)
      jcas.throwFeatMissing("timexId", "tempus.type.DocumentCreationTime");
    return ll_cas.ll_getStringValue(addr, casFeatCode_timexId);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTimexId(int addr, String v) {
        if (featOkTst && casFeat_timexId == null)
      jcas.throwFeatMissing("timexId", "tempus.type.DocumentCreationTime");
    ll_cas.ll_setStringValue(addr, casFeatCode_timexId, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DocumentCreationTime_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_filename = jcas.getRequiredFeatureDE(casType, "filename", "uima.cas.String", featOkTst);
    casFeatCode_filename  = (null == casFeat_filename) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_filename).getCode();

 
    casFeat_value = jcas.getRequiredFeatureDE(casType, "value", "uima.cas.String", featOkTst);
    casFeatCode_value  = (null == casFeat_value) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_value).getCode();

 
    casFeat_timexId = jcas.getRequiredFeatureDE(casType, "timexId", "uima.cas.String", featOkTst);
    casFeatCode_timexId  = (null == casFeat_timexId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_timexId).getCode();

  }
}



    