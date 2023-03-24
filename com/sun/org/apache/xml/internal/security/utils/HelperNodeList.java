package com.sun.org.apache.xml.internal.security.utils;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HelperNodeList implements NodeList {
  ArrayList nodes = new ArrayList(20);
  
  boolean _allNodesMustHaveSameParent = false;
  
  public HelperNodeList() {
    this(false);
  }
  
  public HelperNodeList(boolean paramBoolean) {
    this._allNodesMustHaveSameParent = paramBoolean;
  }
  
  public Node item(int paramInt) {
    return this.nodes.get(paramInt);
  }
  
  public int getLength() {
    return this.nodes.size();
  }
  
  public void appendChild(Node paramNode) throws IllegalArgumentException {
    if (this._allNodesMustHaveSameParent && getLength() > 0 && item(0).getParentNode() != paramNode.getParentNode())
      throw new IllegalArgumentException("Nodes have not the same Parent"); 
    this.nodes.add(paramNode);
  }
  
  public Document getOwnerDocument() {
    return (getLength() == 0) ? null : XMLUtils.getOwnerDocument(item(0));
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\HelperNodeList.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */