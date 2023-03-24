package com.sun.org.apache.xml.internal.security.exceptions;

import com.sun.org.apache.xml.internal.security.utils.I18n;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

public class XMLSecurityRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  protected Exception originalException = null;
  
  protected String msgID;
  
  public XMLSecurityRuntimeException() {
    super("Missing message string");
    this.msgID = null;
    this.originalException = null;
  }
  
  public XMLSecurityRuntimeException(String paramString) {
    super(I18n.getExceptionMessage(paramString));
    this.msgID = paramString;
    this.originalException = null;
  }
  
  public XMLSecurityRuntimeException(String paramString, Object[] paramArrayOfObject) {
    super(MessageFormat.format(I18n.getExceptionMessage(paramString), paramArrayOfObject));
    this.msgID = paramString;
    this.originalException = null;
  }
  
  public XMLSecurityRuntimeException(Exception paramException) {
    super("Missing message ID to locate message string in resource bundle \"com/sun/org/apache/xml/internal/security/resource/xmlsecurity\". Original Exception was a " + paramException.getClass().getName() + " and message " + paramException.getMessage());
    this.originalException = paramException;
  }
  
  public XMLSecurityRuntimeException(String paramString, Exception paramException) {
    super(I18n.getExceptionMessage(paramString, paramException));
    this.msgID = paramString;
    this.originalException = paramException;
  }
  
  public XMLSecurityRuntimeException(String paramString, Object[] paramArrayOfObject, Exception paramException) {
    super(MessageFormat.format(I18n.getExceptionMessage(paramString), paramArrayOfObject));
    this.msgID = paramString;
    this.originalException = paramException;
  }
  
  public String getMsgID() {
    return (this.msgID == null) ? "Missing message ID" : this.msgID;
  }
  
  public String toString() {
    String str1 = getClass().getName();
    String str2 = getLocalizedMessage();
    if (str2 != null) {
      str2 = str1 + ": " + str2;
    } else {
      str2 = str1;
    } 
    if (this.originalException != null)
      str2 = str2 + "\nOriginal Exception was " + this.originalException.toString(); 
    return str2;
  }
  
  public void printStackTrace() {
    synchronized (System.err) {
      super.printStackTrace(System.err);
      if (this.originalException != null)
        this.originalException.printStackTrace(System.err); 
    } 
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter) {
    super.printStackTrace(paramPrintWriter);
    if (this.originalException != null)
      this.originalException.printStackTrace(paramPrintWriter); 
  }
  
  public void printStackTrace(PrintStream paramPrintStream) {
    super.printStackTrace(paramPrintStream);
    if (this.originalException != null)
      this.originalException.printStackTrace(paramPrintStream); 
  }
  
  public Exception getOriginalException() {
    return this.originalException;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\exceptions\XMLSecurityRuntimeException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */