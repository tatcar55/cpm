package org.jcp.xml.dsig.internal.dom;

import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.Provider;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import org.w3c.dom.Element;

public class DOMCanonicalizationMethod extends DOMTransform implements CanonicalizationMethod {
  static final boolean $assertionsDisabled;
  
  public DOMCanonicalizationMethod(TransformService paramTransformService) throws InvalidAlgorithmParameterException {
    super(paramTransformService);
  }
  
  public DOMCanonicalizationMethod(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
    super(paramElement, paramXMLCryptoContext, paramProvider);
  }
  
  public Data canonicalize(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
    return transform(paramData, paramXMLCryptoContext);
  }
  
  public Data canonicalize(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
    return transform(paramData, paramXMLCryptoContext, paramOutputStream);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof CanonicalizationMethod))
      return false; 
    CanonicalizationMethod canonicalizationMethod = (CanonicalizationMethod)paramObject;
    return (getAlgorithm().equals(canonicalizationMethod.getAlgorithm()) && DOMUtils.paramsEqual(getParameterSpec(), canonicalizationMethod.getParameterSpec()));
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 42;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMCanonicalizationMethod.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */