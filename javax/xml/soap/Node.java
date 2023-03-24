package javax.xml.soap;

import org.w3c.dom.Node;

public interface Node extends Node {
  String getValue();
  
  void setValue(String paramString);
  
  void setParentElement(SOAPElement paramSOAPElement) throws SOAPException;
  
  SOAPElement getParentElement();
  
  void detachNode();
  
  void recycleNode();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\Node.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */