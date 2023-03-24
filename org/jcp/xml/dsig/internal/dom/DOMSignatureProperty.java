package org.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.SignatureProperty;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DOMSignatureProperty extends DOMStructure implements SignatureProperty {
  private final String id;
  
  private final String target;
  
  private final List content;
  
  static final boolean $assertionsDisabled;
  
  public DOMSignatureProperty(List paramList, String paramString1, String paramString2) {
    if (paramString1 == null)
      throw new NullPointerException("target cannot be null"); 
    if (paramList == null)
      throw new NullPointerException("content cannot be null"); 
    if (paramList.isEmpty())
      throw new IllegalArgumentException("content cannot be empty"); 
    ArrayList arrayList = new ArrayList(paramList);
    byte b = 0;
    int i = arrayList.size();
    while (b < i) {
      if (!(arrayList.get(b) instanceof XMLStructure))
        throw new ClassCastException("content[" + b + "] is not a valid type"); 
      b++;
    } 
    this.content = Collections.unmodifiableList(arrayList);
    this.target = paramString1;
    this.id = paramString2;
  }
  
  public DOMSignatureProperty(Element paramElement) throws MarshalException {
    this.target = DOMUtils.getAttributeValue(paramElement, "Target");
    if (this.target == null)
      throw new MarshalException("target cannot be null"); 
    this.id = DOMUtils.getAttributeValue(paramElement, "Id");
    NodeList nodeList = paramElement.getChildNodes();
    int i = nodeList.getLength();
    ArrayList arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++)
      arrayList.add(new DOMStructure(nodeList.item(b))); 
    if (arrayList.isEmpty())
      throw new MarshalException("content cannot be empty"); 
    this.content = Collections.unmodifiableList(arrayList);
  }
  
  public List getContent() {
    return this.content;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getTarget() {
    return this.target;
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(document, "SignatureProperty", "http://www.w3.org/2000/09/xmldsig#", paramString);
    DOMUtils.setAttributeID(element, "Id", this.id);
    DOMUtils.setAttribute(element, "Target", this.target);
    byte b = 0;
    int i = this.content.size();
    while (b < i) {
      DOMStructure dOMStructure = this.content.get(b);
      DOMUtils.appendChild(element, dOMStructure.getNode());
      b++;
    } 
    paramNode.appendChild(element);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof SignatureProperty))
      return false; 
    SignatureProperty signatureProperty = (SignatureProperty)paramObject;
    boolean bool = (this.id == null) ? ((signatureProperty.getId() == null) ? true : false) : this.id.equals(signatureProperty.getId());
    return (equalsContent(signatureProperty.getContent()) && this.target.equals(signatureProperty.getTarget()) && bool);
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 50;
  }
  
  private boolean equalsContent(List paramList) {
    int i = paramList.size();
    if (this.content.size() != i)
      return false; 
    for (byte b = 0; b < i; b++) {
      XMLStructure xMLStructure1 = paramList.get(b);
      XMLStructure xMLStructure2 = this.content.get(b);
      if (xMLStructure1 instanceof DOMStructure) {
        if (!(xMLStructure2 instanceof DOMStructure))
          return false; 
        Node node1 = ((DOMStructure)xMLStructure1).getNode();
        Node node2 = ((DOMStructure)xMLStructure2).getNode();
        if (!DOMUtils.nodesEqual(node2, node1))
          return false; 
      } else if (!xMLStructure2.equals(xMLStructure1)) {
        return false;
      } 
    } 
    return true;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMSignatureProperty.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */