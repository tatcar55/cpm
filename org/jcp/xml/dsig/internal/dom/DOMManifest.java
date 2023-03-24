package org.jcp.xml.dsig.internal.dom;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.Manifest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class DOMManifest extends DOMStructure implements Manifest {
  private final List references;
  
  private final String id;
  
  static final boolean $assertionsDisabled;
  
  public DOMManifest(List paramList, String paramString) {
    if (paramList == null)
      throw new NullPointerException("references cannot be null"); 
    ArrayList arrayList = new ArrayList(paramList);
    if (arrayList.isEmpty())
      throw new IllegalArgumentException("list of references must contain at least one entry"); 
    byte b = 0;
    int i = arrayList.size();
    while (b < i) {
      if (!(arrayList.get(b) instanceof javax.xml.crypto.dsig.Reference))
        throw new ClassCastException("references[" + b + "] is not a valid type"); 
      b++;
    } 
    this.references = Collections.unmodifiableList(arrayList);
    this.id = paramString;
  }
  
  public DOMManifest(Element paramElement, XMLCryptoContext paramXMLCryptoContext, Provider paramProvider) throws MarshalException {
    this.id = DOMUtils.getAttributeValue(paramElement, "Id");
    Element element = DOMUtils.getFirstChildElement(paramElement);
    ArrayList arrayList = new ArrayList();
    while (element != null) {
      arrayList.add(new DOMReference(element, paramXMLCryptoContext, paramProvider));
      element = DOMUtils.getNextSiblingElement(element);
    } 
    this.references = Collections.unmodifiableList(arrayList);
  }
  
  public String getId() {
    return this.id;
  }
  
  public List getReferences() {
    return this.references;
  }
  
  public void marshal(Node paramNode, String paramString, DOMCryptoContext paramDOMCryptoContext) throws MarshalException {
    Document document = DOMUtils.getOwnerDocument(paramNode);
    Element element = DOMUtils.createElement(document, "Manifest", "http://www.w3.org/2000/09/xmldsig#", paramString);
    DOMUtils.setAttributeID(element, "Id", this.id);
    byte b = 0;
    int i = this.references.size();
    while (b < i) {
      DOMReference dOMReference = this.references.get(b);
      dOMReference.marshal(element, paramString, paramDOMCryptoContext);
      b++;
    } 
    paramNode.appendChild(element);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof Manifest))
      return false; 
    Manifest manifest = (Manifest)paramObject;
    boolean bool = (this.id == null) ? ((manifest.getId() == null) ? true : false) : this.id.equals(manifest.getId());
    return (bool && this.references.equals(manifest.getReferences()));
  }
  
  public int hashCode() {
    assert false : "hashCode not designed";
    return 46;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMManifest.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */