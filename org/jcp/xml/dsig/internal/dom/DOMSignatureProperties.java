package org.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.SignatureProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DOMSignatureProperties extends DOMStructure implements SignatureProperties {
  private final String id;
  
  private final List properties;
  
  static final boolean $assertionsDisabled;
  
  public DOMSignatureProperties(List paramList, String paramString) {
    if (paramList == null)
      throw new NullPointerException("properties cannot be null"); 
    if (paramList.isEmpty())
      throw new IllegalArgumentException("properties cannot be empty"); 
    ArrayList arrayList = new ArrayList(paramList);
    byte b = 0;
    int i = arrayList.size();
    while (b < i) {
      if (!(arrayList.get(b) instanceof javax.xml.crypto.dsig.SignatureProperty))
        throw new ClassCastException("properties[" + b + "] is not a valid type"); 
      b++;
    } 
    this.properties = Collections.unmodifiableList(arrayList);
    this.id = paramString;
  }
  
  public DOMSignatureProperties(Element paramElement) throws MarshalException {
    this.id = DOMUtils.getAttributeValue(paramElement, "Id");
    NodeList nodeList = paramElement.getChildNodes();
    int i = nodeList.getLength();
    ArrayList arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++) {
      Node node = nodeList.item(b);
      if (node.getNodeType() == 1)
        arrayList.add(new DOMSignatureProperty((Element)node)); 
    } 
    if (arrayList.isEmpty())
      throw new MarshalException("properties cannot be empty"); 
    this.properties = Collections.unmodifiableList(arrayList);
  }
  
  public List getProperties() {
    return this.properties;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(document, "SignatureProperties", "http://www.w3.org/2000/09/xmldsig#", paramString);
    DOMUtils.setAttributeID(element, "Id", this.id);
    byte b = 0;
    int i = this.properties.size();
    while (b < i) {
      DOMSignatureProperty dOMSignatureProperty = this.properties.get(b);
      dOMSignatureProperty.marshal(element, paramString, paramDOMCryptoContext);
      b++;
    } 
    paramNode.appendChild(element);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof SignatureProperties))
      return false; 
    SignatureProperties signatureProperties = (SignatureProperties)paramObject;
    boolean bool = (this.id == null) ? ((signatureProperties.getId() == null) ? true : false) : this.id.equals(signatureProperties.getId());
    return (this.properties.equals(signatureProperties.getProperties()) && bool);
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 49;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMSignatureProperties.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */