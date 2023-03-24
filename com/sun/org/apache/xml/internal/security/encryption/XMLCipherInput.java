package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Attr;

public class XMLCipherInput {
  private static Logger logger = Logger.getLogger(XMLCipher.class.getName());
  
  private CipherData _cipherData;
  
  private int _mode;
  
  public XMLCipherInput(CipherData paramCipherData) throws XMLEncryptionException {
    this._cipherData = paramCipherData;
    this._mode = 2;
    if (this._cipherData == null)
      throw new XMLEncryptionException("CipherData is null"); 
  }
  
  public XMLCipherInput(EncryptedType paramEncryptedType) throws XMLEncryptionException {
    this._cipherData = (paramEncryptedType == null) ? null : paramEncryptedType.getCipherData();
    this._mode = 2;
    if (this._cipherData == null)
      throw new XMLEncryptionException("CipherData is null"); 
  }
  
  public byte[] getBytes() throws XMLEncryptionException {
    return (this._mode == 2) ? getDecryptBytes() : null;
  }
  
  private byte[] getDecryptBytes() throws XMLEncryptionException {
    String str = null;
    if (this._cipherData.getDataType() == 2) {
      logger.log(Level.FINE, "Found a reference type CipherData");
      CipherReference cipherReference = this._cipherData.getCipherReference();
      Attr attr = cipherReference.getURIAsAttr();
      XMLSignatureInput xMLSignatureInput = null;
      try {
        ResourceResolver resourceResolver = ResourceResolver.getInstance(attr, null);
        xMLSignatureInput = resourceResolver.resolve(attr, (String)null);
      } catch (ResourceResolverException resourceResolverException) {
        throw new XMLEncryptionException("empty", resourceResolverException);
      } 
      if (xMLSignatureInput != null) {
        logger.log(Level.FINE, "Managed to resolve URI \"" + cipherReference.getURI() + "\"");
      } else {
        logger.log(Level.FINE, "Failed to resolve URI \"" + cipherReference.getURI() + "\"");
      } 
      Transforms transforms = cipherReference.getTransforms();
      if (transforms != null) {
        logger.log(Level.FINE, "Have transforms in cipher reference");
        try {
          Transforms transforms1 = transforms.getDSTransforms();
          xMLSignatureInput = transforms1.performTransforms(xMLSignatureInput);
        } catch (TransformationException transformationException) {
          throw new XMLEncryptionException("empty", transformationException);
        } 
      } 
      try {
        return xMLSignatureInput.getBytes();
      } catch (IOException iOException) {
        throw new XMLEncryptionException("empty", iOException);
      } catch (CanonicalizationException canonicalizationException) {
        throw new XMLEncryptionException("empty", canonicalizationException);
      } 
    } 
    if (this._cipherData.getDataType() == 1) {
      str = this._cipherData.getCipherValue().getValue();
    } else {
      throw new XMLEncryptionException("CipherData.getDataType() returned unexpected value");
    } 
    logger.log(Level.FINE, "Encrypted octets:\n" + str);
    byte[] arrayOfByte = null;
    try {
      arrayOfByte = Base64.decode(str);
    } catch (Base64DecodingException base64DecodingException) {
      throw new XMLEncryptionException("empty", base64DecodingException);
    } 
    return arrayOfByte;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\XMLCipherInput.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */