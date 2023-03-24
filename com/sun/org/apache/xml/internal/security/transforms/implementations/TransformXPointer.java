package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;

public class TransformXPointer extends TransformSpi {
  public static final String implementedTransformURI = "http://www.w3.org/TR/2001/WD-xptr-20010108";
  
  protected String engineGetURI() {
    return "http://www.w3.org/TR/2001/WD-xptr-20010108";
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws TransformationException {
    Object[] arrayOfObject = { "http://www.w3.org/TR/2001/WD-xptr-20010108" };
    throw new TransformationException("signature.Transform.NotYetImplemented", arrayOfObject);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\TransformXPointer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */