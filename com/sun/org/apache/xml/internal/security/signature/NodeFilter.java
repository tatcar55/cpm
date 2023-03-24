package com.sun.org.apache.xml.internal.security.signature;

import org.w3c.dom.Node;

public interface NodeFilter {
  int isNodeInclude(Node paramNode);
  
  int isNodeIncludeDO(Node paramNode, int paramInt);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\NodeFilter.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */