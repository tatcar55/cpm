package org.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMXSLTTransform extends ApacheTransform {
  public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramTransformParameterSpec == null)
      throw new InvalidAlgorithmParameterException("params are required"); 
    if (!(paramTransformParameterSpec instanceof XSLTTransformParameterSpec))
      throw new InvalidAlgorithmParameterException("unrecognized params"); 
    this.params = paramTransformParameterSpec;
  }
  
  public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
    super.init(paramXMLStructure, paramXMLCryptoContext);
    unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
  }
  
  private void unmarshalParams(Element paramElement) {
    this.params = new XSLTTransformParameterSpec(new DOMStructure(paramElement));
  }
  
  public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
    super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
    XSLTTransformParameterSpec xSLTTransformParameterSpec = (XSLTTransformParameterSpec)getParameterSpec();
    Node node = ((DOMStructure)xSLTTransformParameterSpec.getStylesheet()).getNode();
    DOMUtils.appendChild(this.transformElem, node);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMXSLTTransform.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */