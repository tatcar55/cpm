package com.sun.org.apache.xml.internal.security.signature;

public class MissingResourceFailureException extends XMLSignatureException {
  private static final long serialVersionUID = 1L;
  
  Reference uninitializedReference = null;
  
  public MissingResourceFailureException(String paramString, Reference paramReference) {
    super(paramString);
    this.uninitializedReference = paramReference;
  }
  
  public MissingResourceFailureException(String paramString, Object[] paramArrayOfObject, Reference paramReference) {
    super(paramString, paramArrayOfObject);
    this.uninitializedReference = paramReference;
  }
  
  public MissingResourceFailureException(String paramString, Exception paramException, Reference paramReference) {
    super(paramString, paramException);
    this.uninitializedReference = paramReference;
  }
  
  public MissingResourceFailureException(String paramString, Object[] paramArrayOfObject, Exception paramException, Reference paramReference) {
    super(paramString, paramArrayOfObject, paramException);
    this.uninitializedReference = paramReference;
  }
  
  public void setReference(Reference paramReference) {
    this.uninitializedReference = paramReference;
  }
  
  public Reference getReference() {
    return this.uninitializedReference;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\signature\MissingResourceFailureException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */