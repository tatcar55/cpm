package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

public class XMLEncryptionException extends XMLSecurityException {
  private static final long serialVersionUID = 1L;
  
  public XMLEncryptionException() {}
  
  public XMLEncryptionException(String paramString) {
    super(paramString);
  }
  
  public XMLEncryptionException(String paramString, Object[] paramArrayOfObject) {
    super(paramString, paramArrayOfObject);
  }
  
  public XMLEncryptionException(String paramString, Exception paramException) {
    super(paramString, paramException);
  }
  
  public XMLEncryptionException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
    super(paramString, paramArrayOfObject, paramException);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\XMLEncryptionException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */