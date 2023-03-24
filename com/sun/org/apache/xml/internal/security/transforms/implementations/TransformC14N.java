package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315OmitComments;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import java.io.OutputStream;

public class TransformC14N extends TransformSpi {
  public static final String implementedTransformURI = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
  
  protected String engineGetURI() {
    return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws CanonicalizationException {
    return enginePerformTransform(paramXMLSignatureInput, null, paramTransform);
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws CanonicalizationException {
    Canonicalizer20010315OmitComments canonicalizer20010315OmitComments = new Canonicalizer20010315OmitComments();
    if (paramOutputStream != null)
      canonicalizer20010315OmitComments.setWriter(paramOutputStream); 
    byte[] arrayOfByte = null;
    arrayOfByte = canonicalizer20010315OmitComments.engineCanonicalize(paramXMLSignatureInput);
    XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(arrayOfByte);
    if (paramOutputStream != null)
      xMLSignatureInput.setOutputStream(paramOutputStream); 
    return xMLSignatureInput;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\TransformC14N.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */