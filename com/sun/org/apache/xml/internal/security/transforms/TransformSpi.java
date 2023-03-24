package com.sun.org.apache.xml.internal.security.transforms;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public abstract class TransformSpi {
  protected Transform _transformObject = null;
  
  protected void setTransform(Transform paramTransform) {
    this._transformObject = paramTransform;
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, OutputStream paramOutputStream, Transform paramTransform) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, ParserConfigurationException, SAXException {
    return enginePerformTransform(paramXMLSignatureInput, paramTransform);
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput, Transform paramTransform) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, ParserConfigurationException, SAXException {
    try {
      TransformSpi transformSpi = (TransformSpi)getClass().newInstance();
      transformSpi.setTransform(paramTransform);
      return transformSpi.enginePerformTransform(paramXMLSignatureInput);
    } catch (InstantiationException instantiationException) {
      throw new TransformationException("", instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new TransformationException("", illegalAccessException);
    } 
  }
  
  protected XMLSignatureInput enginePerformTransform(XMLSignatureInput paramXMLSignatureInput) throws IOException, CanonicalizationException, InvalidCanonicalizerException, TransformationException, ParserConfigurationException, SAXException {
    throw new UnsupportedOperationException();
  }
  
  protected abstract String engineGetURI();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\TransformSpi.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */