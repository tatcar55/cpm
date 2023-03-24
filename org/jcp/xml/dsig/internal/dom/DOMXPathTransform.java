package org.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public final class DOMXPathTransform extends ApacheTransform {
  public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramTransformParameterSpec == null)
      throw new InvalidAlgorithmParameterException("params are required"); 
    if (!(paramTransformParameterSpec instanceof XPathFilterParameterSpec))
      throw new InvalidAlgorithmParameterException("params must be of type XPathFilterParameterSpec"); 
    this.params = paramTransformParameterSpec;
  }
  
  public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
    super.init(paramXMLStructure, paramXMLCryptoContext);
    unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
  }
  
  private void unmarshalParams(Element paramElement) {
    String str = paramElement.getFirstChild().getNodeValue();
    NamedNodeMap namedNodeMap = paramElement.getAttributes();
    if (namedNodeMap != null) {
      int i = namedNodeMap.getLength();
      HashMap hashMap = new HashMap(i);
      for (byte b = 0; b < i; b++) {
        Attr attr = (Attr)namedNodeMap.item(b);
        String str1 = attr.getPrefix();
        if (str1 != null && str1.equals("xmlns"))
          hashMap.put(attr.getLocalName(), attr.getValue()); 
      } 
      this.params = new XPathFilterParameterSpec(str, hashMap);
    } else {
      this.params = new XPathFilterParameterSpec(str);
    } 
  }
  
  public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
    super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
    XPathFilterParameterSpec xPathFilterParameterSpec = (XPathFilterParameterSpec)getParameterSpec();
    Element element = DOMUtils.createElement(this.ownerDoc, "XPath", "http://www.w3.org/2000/09/xmldsig#", DOMUtils.getSignaturePrefix(paramXMLCryptoContext));
    element.appendChild(this.ownerDoc.createTextNode(xPathFilterParameterSpec.getXPath()));
    for (Map.Entry entry : xPathFilterParameterSpec.getNamespaceMap().entrySet())
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + (String)entry.getKey(), (String)entry.getValue()); 
    this.transformElem.appendChild(element);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMXPathTransform.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */