package org.jcp.xml.dsig.internal.dom;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
import javax.xml.crypto.dsig.spec.XPathType;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public final class DOMXPathFilter2Transform extends ApacheTransform {
  public void init(TransformParameterSpec paramTransformParameterSpec) throws InvalidAlgorithmParameterException {
    if (paramTransformParameterSpec == null)
      throw new InvalidAlgorithmParameterException("params are required"); 
    if (!(paramTransformParameterSpec instanceof XPathFilter2ParameterSpec))
      throw new InvalidAlgorithmParameterException("params must be of type XPathFilter2ParameterSpec"); 
    this.params = paramTransformParameterSpec;
  }
  
  public void init(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws InvalidAlgorithmParameterException {
    super.init(paramXMLStructure, paramXMLCryptoContext);
    try {
      unmarshalParams(DOMUtils.getFirstChildElement(this.transformElem));
    } catch (MarshalException marshalException) {
      throw (InvalidAlgorithmParameterException)(new InvalidAlgorithmParameterException()).initCause(marshalException);
    } 
  }
  
  private void unmarshalParams(Element paramElement) throws MarshalException {
    ArrayList arrayList = new ArrayList();
    while (paramElement != null) {
      String str1 = paramElement.getFirstChild().getNodeValue();
      String str2 = DOMUtils.getAttributeValue(paramElement, "Filter");
      if (str2 == null)
        throw new MarshalException("filter cannot be null"); 
      XPathType.Filter filter = null;
      if (str2.equals("intersect")) {
        filter = XPathType.Filter.INTERSECT;
      } else if (str2.equals("subtract")) {
        filter = XPathType.Filter.SUBTRACT;
      } else if (str2.equals("union")) {
        filter = XPathType.Filter.UNION;
      } else {
        throw new MarshalException("Unknown XPathType filter type" + str2);
      } 
      NamedNodeMap namedNodeMap = paramElement.getAttributes();
      if (namedNodeMap != null) {
        int i = namedNodeMap.getLength();
        HashMap hashMap = new HashMap(i);
        for (byte b = 0; b < i; b++) {
          Attr attr = (Attr)namedNodeMap.item(b);
          String str = attr.getPrefix();
          if (str != null && str.equals("xmlns"))
            hashMap.put(attr.getLocalName(), attr.getValue()); 
        } 
        arrayList.add(new XPathType(str1, filter, hashMap));
      } else {
        arrayList.add(new XPathType(str1, filter));
      } 
      paramElement = DOMUtils.getNextSiblingElement(paramElement);
    } 
    this.params = new XPathFilter2ParameterSpec(arrayList);
  }
  
  public void marshalParams(XMLStructure paramXMLStructure, XMLCryptoContext paramXMLCryptoContext) throws MarshalException {
    super.marshalParams(paramXMLStructure, paramXMLCryptoContext);
    XPathFilter2ParameterSpec xPathFilter2ParameterSpec = (XPathFilter2ParameterSpec)getParameterSpec();
    String str1 = DOMUtils.getNSPrefix(paramXMLCryptoContext, "http://www.w3.org/2002/06/xmldsig-filter2");
    String str2 = (str1 == null || str1.length() == 0) ? "xmlns" : ("xmlns:" + str1);
    List list = xPathFilter2ParameterSpec.getXPathList();
    byte b = 0;
    int i = list.size();
    while (b < i) {
      XPathType xPathType = list.get(b);
      Element element = DOMUtils.createElement(this.ownerDoc, "XPath", "http://www.w3.org/2002/06/xmldsig-filter2", str1);
      element.appendChild(this.ownerDoc.createTextNode(xPathType.getExpression()));
      DOMUtils.setAttribute(element, "Filter", xPathType.getFilter().toString());
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", str2, "http://www.w3.org/2002/06/xmldsig-filter2");
      for (Map.Entry entry : xPathType.getNamespaceMap().entrySet())
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + (String)entry.getKey(), (String)entry.getValue()); 
      this.transformElem.appendChild(element);
      b++;
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMXPathFilter2Transform.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */