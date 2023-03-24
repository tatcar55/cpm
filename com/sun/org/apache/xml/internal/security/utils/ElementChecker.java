package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface ElementChecker {
  void guaranteeThatElementInCorrectSpace(ElementProxy paramElementProxy, Element paramElement) throws XMLSecurityException;
  
  boolean isNamespaceElement(Node paramNode, String paramString1, String paramString2);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\ElementChecker.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */