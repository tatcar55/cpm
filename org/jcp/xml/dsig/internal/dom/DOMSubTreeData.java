package org.jcp.xml.dsig.internal.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.xml.crypto.NodeSetData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DOMSubTreeData implements NodeSetData {
  private boolean excludeComments;
  
  private Iterator ni;
  
  private Node root;
  
  public DOMSubTreeData(Node paramNode, boolean paramBoolean) {
    this.root = paramNode;
    this.ni = new DelayedNodeIterator(paramNode, paramBoolean);
    this.excludeComments = paramBoolean;
  }
  
  public Iterator iterator() {
    return this.ni;
  }
  
  public Node getRoot() {
    return this.root;
  }
  
  public boolean excludeComments() {
    return this.excludeComments;
  }
  
  static class DelayedNodeIterator implements Iterator {
    private Node root;
    
    private List nodeSet;
    
    private ListIterator li;
    
    private boolean withComments;
    
    DelayedNodeIterator(Node param1Node, boolean param1Boolean) {
      this.root = param1Node;
      this.withComments = !param1Boolean;
    }
    
    public boolean hasNext() {
      if (this.nodeSet == null) {
        this.nodeSet = dereferenceSameDocumentURI(this.root);
        this.li = this.nodeSet.listIterator();
      } 
      return this.li.hasNext();
    }
    
    public Object next() {
      if (this.nodeSet == null) {
        this.nodeSet = dereferenceSameDocumentURI(this.root);
        this.li = this.nodeSet.listIterator();
      } 
      if (this.li.hasNext())
        return this.li.next(); 
      throw new NoSuchElementException();
    }
    
    public void remove() {
      throw new UnsupportedOperationException();
    }
    
    private List dereferenceSameDocumentURI(Node param1Node) {
      ArrayList arrayList = new ArrayList();
      if (param1Node != null)
        nodeSetMinusCommentNodes(param1Node, arrayList, null); 
      return arrayList;
    }
    
    private void nodeSetMinusCommentNodes(Node param1Node1, List param1List, Node param1Node2) {
      NamedNodeMap namedNodeMap;
      Node node1;
      Node node2;
      switch (param1Node1.getNodeType()) {
        case 1:
          namedNodeMap = param1Node1.getAttributes();
          if (namedNodeMap != null) {
            byte b = 0;
            int i = namedNodeMap.getLength();
            while (b < i) {
              param1List.add(namedNodeMap.item(b));
              b++;
            } 
          } 
          param1List.add(param1Node1);
        case 9:
          node1 = null;
          for (node2 = param1Node1.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            nodeSetMinusCommentNodes(node2, param1List, node1);
            node1 = node2;
          } 
          break;
        case 3:
        case 4:
          if (param1Node2 != null && (param1Node2.getNodeType() == 3 || param1Node2.getNodeType() == 4))
            return; 
        case 7:
          param1List.add(param1Node1);
          break;
        case 8:
          if (this.withComments)
            param1List.add(param1Node1); 
          break;
      } 
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\dom\DOMSubTreeData.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */