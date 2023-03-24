package com.sun.org.apache.xml.internal.security.transforms;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

public class TransformationException extends XMLSecurityException {
  private static final long serialVersionUID = 1L;
  
  public TransformationException() {}
  
  public TransformationException(String paramString) {
    super(paramString);
  }
  
  public TransformationException(String paramString, Object[] paramArrayOfObject) {
    super(paramString, paramArrayOfObject);
  }
  
  public TransformationException(String paramString, Exception paramException) {
    super(paramString, paramException);
  }
  
  public TransformationException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
    super(paramString, paramArrayOfObject, paramException);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\TransformationException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */