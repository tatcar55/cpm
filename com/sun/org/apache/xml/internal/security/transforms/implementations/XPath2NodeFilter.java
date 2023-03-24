package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.util.Set;
import org.w3c.dom.Node;

class XPath2NodeFilter implements NodeFilter {
  boolean hasUnionNodes;
  
  boolean hasSubstractNodes;
  
  boolean hasIntersectNodes;
  
  Set unionNodes;
  
  Set substractNodes;
  
  Set intersectNodes;
  
  int inSubstract = -1;
  
  int inIntersect = -1;
  
  int inUnion = -1;
  
  XPath2NodeFilter(Set paramSet1, Set paramSet2, Set paramSet3) {
    this.unionNodes = paramSet1;
    this.hasUnionNodes = !paramSet1.isEmpty();
    this.substractNodes = paramSet2;
    this.hasSubstractNodes = !paramSet2.isEmpty();
    this.intersectNodes = paramSet3;
    this.hasIntersectNodes = !paramSet3.isEmpty();
  }
  
  public int isNodeInclude(Node paramNode) {
    byte b = 1;
    if (this.hasSubstractNodes && rooted(paramNode, this.substractNodes)) {
      b = -1;
    } else if (this.hasIntersectNodes && !rooted(paramNode, this.intersectNodes)) {
      b = 0;
    } 
    if (b == 1)
      return 1; 
    if (this.hasUnionNodes) {
      if (rooted(paramNode, this.unionNodes))
        return 1; 
      b = 0;
    } 
    return b;
  }
  
  public int isNodeIncludeDO(Node paramNode, int paramInt) {
    byte b = 1;
    if (this.hasSubstractNodes) {
      if (this.inSubstract == -1 || paramInt <= this.inSubstract)
        if (inList(paramNode, this.substractNodes)) {
          this.inSubstract = paramInt;
        } else {
          this.inSubstract = -1;
        }  
      if (this.inSubstract != -1)
        b = -1; 
    } 
    if (b != -1 && this.hasIntersectNodes && (this.inIntersect == -1 || paramInt <= this.inIntersect))
      if (!inList(paramNode, this.intersectNodes)) {
        this.inIntersect = -1;
        b = 0;
      } else {
        this.inIntersect = paramInt;
      }  
    if (paramInt <= this.inUnion)
      this.inUnion = -1; 
    if (b == 1)
      return 1; 
    if (this.hasUnionNodes) {
      if (this.inUnion == -1 && inList(paramNode, this.unionNodes))
        this.inUnion = paramInt; 
      if (this.inUnion != -1)
        return 1; 
      b = 0;
    } 
    return b;
  }
  
  static boolean rooted(Node paramNode, Set paramSet) {
    if (paramSet.contains(paramNode))
      return true; 
    for (Node node : paramSet) {
      if (XMLUtils.isDescendantOrSelf(node, paramNode))
        return true; 
    } 
    return false;
  }
  
  static boolean inList(Node paramNode, Set paramSet) {
    return paramSet.contains(paramNode);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\XPath2NodeFilter.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */