package org.jcp.xml.dsig.internal.dom;

import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMTransform extends DOMStructure implements Transform {
  protected TransformService spi;
  
  static final boolean $assertionsDisabled;
  
  public DOMTransform(TransformService paramTransformService) {
    this.spi = paramTransformService;
  }
  
  public DOMTransform(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
    String str = DOMUtils.getAttributeValue(paramElement, "Algorithm");
    try {
      this.spi = TransformService.getInstance(str, "DOM");
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      try {
        this.spi = TransformService.getInstance(str, "DOM", paramProvider);
      } catch (NoSuchAlgorithmException noSuchAlgorithmException1) {
        throw new MarshalException(noSuchAlgorithmException1);
      } 
    } 
    try {
      this.spi.init(new DOMStructure(paramElement), paramXMLCryptoContext);
    } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
      throw new MarshalException(invalidAlgorithmParameterException);
    } 
  }
  
  public final AlgorithmParameterSpec getParameterSpec() {
    return this.spi.getParameterSpec();
  }
  
  public final String getAlgorithm() {
    return this.spi.getAlgorithm();
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = null;
    if (paramNode.getLocalName().equals("Transforms")) {
      element = DOMUtils.createElement(document, "Transform", "http://www.w3.org/2000/09/xmldsig#", paramString);
    } else {
      element = DOMUtils.createElement(document, "CanonicalizationMethod", "http://www.w3.org/2000/09/xmldsig#", paramString);
    } 
    DOMUtils.setAttribute(element, "Algorithm", getAlgorithm());
    this.spi.marshalParams(new DOMStructure(element), paramDOMCryptoContext);
    paramNode.appendChild(element);
  }
  
  public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext) throws TransformException {
    return this.spi.transform(paramData, paramXMLCryptoContext);
  }
  
  public Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, OutputStream paramOutputStream) throws TransformException {
    return this.spi.transform(paramData, paramXMLCryptoContext, paramOutputStream);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof Transform))
      return false; 
    Transform transform = (Transform)paramObject;
    return (getAlgorithm().equals(transform.getAlgorithm()) && DOMUtils.paramsEqual(getParameterSpec(), transform.getParameterSpec()));
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 58;
  }
  
  Data transform(Data paramData, XMLCryptoContext paramXMLCryptoContext, DOMSignContext paramDOMSignContext) throws MarshalException, TransformException {
    marshal(paramDOMSignContext.getParent(), DOMUtils.getSignaturePrefix(paramDOMSignContext), paramDOMSignContext);
    return transform(paramData, paramXMLCryptoContext);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMTransform.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */