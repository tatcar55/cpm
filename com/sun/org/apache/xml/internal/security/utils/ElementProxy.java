package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public abstract class ElementProxy {
  static Logger log = Logger.getLogger(ElementProxy.class.getName());
  
  protected Element _constructionElement = null;
  
  protected String _baseURI = null;
  
  protected Document _doc = null;
  
  static ElementChecker checker = new ElementCheckerImpl.InternedNsChecker();
  
  static HashMap _prefixMappings = new HashMap();
  
  static HashMap _prefixMappingsBindings = new HashMap();
  
  public abstract String getBaseNamespace();
  
  public abstract String getBaseLocalName();
  
  public ElementProxy() {}
  
  public ElementProxy(Document paramDocument) {
    if (paramDocument == null)
      throw new RuntimeException("Document is null"); 
    this._doc = paramDocument;
    this._constructionElement = createElementForFamilyLocal(this._doc, getBaseNamespace(), getBaseLocalName());
  }
  
  protected Element createElementForFamilyLocal(Document paramDocument, String paramString1, String paramString2) {
    Element element = null;
    if (paramString1 == null) {
      element = paramDocument.createElementNS(null, paramString2);
    } else {
      String str1 = getBaseNamespace();
      String str2 = getDefaultPrefix(str1);
      if (str2 == null || str2.length() == 0) {
        element = paramDocument.createElementNS(paramString1, paramString2);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", paramString1);
      } else {
        String str3 = null;
        String str4 = getDefaultPrefixBindings(str1);
        StringBuffer stringBuffer = new StringBuffer(str2);
        stringBuffer.append(':');
        stringBuffer.append(paramString2);
        str3 = stringBuffer.toString();
        element = paramDocument.createElementNS(paramString1, str3);
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", str4, paramString1);
      } 
    } 
    return element;
  }
  
  public static Element createElementForFamily(Document paramDocument, String paramString1, String paramString2) {
    Element element = null;
    String str = getDefaultPrefix(paramString1);
    if (paramString1 == null) {
      element = paramDocument.createElementNS(null, paramString2);
    } else if (str == null || str.length() == 0) {
      element = paramDocument.createElementNS(paramString1, paramString2);
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", paramString1);
    } else {
      element = paramDocument.createElementNS(paramString1, str + ":" + paramString2);
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", getDefaultPrefixBindings(paramString1), paramString1);
    } 
    return element;
  }
  
  public void setElement(Element paramElement, String paramString) throws XMLSecurityException {
    if (paramElement == null)
      throw new XMLSecurityException("ElementProxy.nullElement"); 
    log.log(Level.FINE, "setElement(" + paramElement.getTagName() + ", \"" + paramString + "\"");
    this._doc = paramElement.getOwnerDocument();
    this._constructionElement = paramElement;
    this._baseURI = paramString;
  }
  
  public ElementProxy(Element paramElement, String paramString) throws XMLSecurityException {
    if (paramElement == null)
      throw new XMLSecurityException("ElementProxy.nullElement"); 
    log.log(Level.FINE, "setElement(\"" + paramElement.getTagName() + "\", \"" + paramString + "\")");
    this._doc = paramElement.getOwnerDocument();
    this._constructionElement = paramElement;
    this._baseURI = paramString;
    guaranteeThatElementInCorrectSpace();
  }
  
  public final Element getElement() {
    return this._constructionElement;
  }
  
  public final NodeList getElementPlusReturns() {
    HelperNodeList helperNodeList = new HelperNodeList();
    helperNodeList.appendChild(this._doc.createTextNode("\n"));
    helperNodeList.appendChild(getElement());
    helperNodeList.appendChild(this._doc.createTextNode("\n"));
    return helperNodeList;
  }
  
  public Document getDocument() {
    return this._doc;
  }
  
  public String getBaseURI() {
    return this._baseURI;
  }
  
  void guaranteeThatElementInCorrectSpace() throws XMLSecurityException {
    checker.guaranteeThatElementInCorrectSpace(this, this._constructionElement);
  }
  
  public void addBigIntegerElement(BigInteger paramBigInteger, String paramString) {
    if (paramBigInteger != null) {
      Element element = XMLUtils.createElementInSignatureSpace(this._doc, paramString);
      Base64.fillElementWithBigInteger(element, paramBigInteger);
      this._constructionElement.appendChild(element);
      XMLUtils.addReturnToElement(this._constructionElement);
    } 
  }
  
  public void addBase64Element(byte[] paramArrayOfbyte, String paramString) {
    if (paramArrayOfbyte != null) {
      Element element = Base64.encodeToElement(this._doc, paramString, paramArrayOfbyte);
      this._constructionElement.appendChild(element);
      if (!XMLUtils.ignoreLineBreaks())
        this._constructionElement.appendChild(this._doc.createTextNode("\n")); 
    } 
  }
  
  public void addTextElement(String paramString1, String paramString2) {
    Element element = XMLUtils.createElementInSignatureSpace(this._doc, paramString2);
    Text text = this._doc.createTextNode(paramString1);
    element.appendChild(text);
    this._constructionElement.appendChild(element);
    XMLUtils.addReturnToElement(this._constructionElement);
  }
  
  public void addBase64Text(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte != null) {
      Text text = XMLUtils.ignoreLineBreaks() ? this._doc.createTextNode(Base64.encode(paramArrayOfbyte)) : this._doc.createTextNode("\n" + Base64.encode(paramArrayOfbyte) + "\n");
      this._constructionElement.appendChild(text);
    } 
  }
  
  public void addText(String paramString) {
    if (paramString != null) {
      Text text = this._doc.createTextNode(paramString);
      this._constructionElement.appendChild(text);
    } 
  }
  
  public BigInteger getBigIntegerFromChildElement(String paramString1, String paramString2) throws Base64DecodingException {
    return Base64.decodeBigIntegerFromText(XMLUtils.selectNodeText(this._constructionElement.getFirstChild(), paramString2, paramString1, 0));
  }
  
  public byte[] getBytesFromChildElement(String paramString1, String paramString2) throws XMLSecurityException {
    Element element = XMLUtils.selectNode(this._constructionElement.getFirstChild(), paramString2, paramString1, 0);
    return Base64.decode(element);
  }
  
  public String getTextFromChildElement(String paramString1, String paramString2) {
    Text text = (Text)XMLUtils.selectNode(this._constructionElement.getFirstChild(), paramString2, paramString1, 0).getFirstChild();
    return text.getData();
  }
  
  public byte[] getBytesFromTextChild() throws XMLSecurityException {
    return Base64.decode(XMLUtils.getFullTextChildrenFromElement(this._constructionElement));
  }
  
  public String getTextFromTextChild() {
    return XMLUtils.getFullTextChildrenFromElement(this._constructionElement);
  }
  
  public int length(String paramString1, String paramString2) {
    byte b = 0;
    for (Node node = this._constructionElement.getFirstChild(); node != null; node = node.getNextSibling()) {
      if (paramString2.equals(node.getLocalName()) && paramString1 == node.getNamespaceURI())
        b++; 
    } 
    return b;
  }
  
  public void setXPathNamespaceContext(String paramString1, String paramString2) throws XMLSecurityException {
    String str;
    if (paramString1 == null || paramString1.length() == 0)
      throw new XMLSecurityException("defaultNamespaceCannotBeSetHere"); 
    if (paramString1.equals("xmlns"))
      throw new XMLSecurityException("defaultNamespaceCannotBeSetHere"); 
    if (paramString1.startsWith("xmlns:")) {
      str = paramString1;
    } else {
      str = "xmlns:" + paramString1;
    } 
    Attr attr = this._constructionElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", str);
    if (attr != null) {
      if (!attr.getNodeValue().equals(paramString2)) {
        Object[] arrayOfObject = { str, this._constructionElement.getAttributeNS((String)null, str) };
        throw new XMLSecurityException("namespacePrefixAlreadyUsedByOtherURI", arrayOfObject);
      } 
      return;
    } 
    this._constructionElement.setAttributeNS("http://www.w3.org/2000/xmlns/", str, paramString2);
  }
  
  public static void setDefaultPrefix(String paramString1, String paramString2) throws XMLSecurityException {
    if (_prefixMappings.containsValue(paramString2)) {
      Object object = _prefixMappings.get(paramString1);
      if (!object.equals(paramString2)) {
        Object[] arrayOfObject = { paramString2, paramString1, object };
        throw new XMLSecurityException("prefix.AlreadyAssigned", arrayOfObject);
      } 
    } 
    if ("http://www.w3.org/2000/09/xmldsig#".equals(paramString1))
      XMLUtils.dsPrefix = paramString2; 
    _prefixMappings.put(paramString1, paramString2.intern());
    if (paramString2.length() == 0) {
      _prefixMappingsBindings.put(paramString1, "xmlns");
    } else {
      _prefixMappingsBindings.put(paramString1, ("xmlns:" + paramString2).intern());
    } 
  }
  
  public static String getDefaultPrefix(String paramString) {
    return (String)_prefixMappings.get(paramString);
  }
  
  public static String getDefaultPrefixBindings(String paramString) {
    return (String)_prefixMappingsBindings.get(paramString);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\ElementProxy.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */