package com.sun.org.apache.xml.internal.security.c14n.implementations;

public class Canonicalizer11_WithComments extends Canonicalizer11 {
  public Canonicalizer11_WithComments() {
    super(true);
  }
  
  public final String engineGetURI() {
    return "http://www.w3.org/2006/12/xml-c14n11#WithComments";
  }
  
  public final boolean engineGetIncludeComments() {
    return true;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\Canonicalizer11_WithComments.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */