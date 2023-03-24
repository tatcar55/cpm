package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignerOutputStream extends ByteArrayOutputStream {
  static final byte[] none = "error".getBytes();
  
  final SignatureAlgorithm sa;
  
  static Logger log = Logger.getLogger(SignerOutputStream.class.getName());
  
  public SignerOutputStream(SignatureAlgorithm paramSignatureAlgorithm) {
    this.sa = paramSignatureAlgorithm;
  }
  
  public byte[] toByteArray() {
    return none;
  }
  
  public void write(byte[] paramArrayOfbyte) {
    try {
      this.sa.update(paramArrayOfbyte);
    } catch (XMLSignatureException xMLSignatureException) {
      throw new RuntimeException("" + xMLSignatureException);
    } 
  }
  
  public void write(int paramInt) {
    try {
      this.sa.update((byte)paramInt);
    } catch (XMLSignatureException xMLSignatureException) {
      throw new RuntimeException("" + xMLSignatureException);
    } 
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    log.log(Level.FINE, "Canonicalized SignedInfo:");
    StringBuffer stringBuffer = new StringBuffer(paramInt2);
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
      stringBuffer.append((char)paramArrayOfbyte[i]); 
    log.log(Level.FINE, stringBuffer.toString());
    try {
      this.sa.update(paramArrayOfbyte, paramInt1, paramInt2);
    } catch (XMLSignatureException xMLSignatureException) {
      throw new RuntimeException("" + xMLSignatureException);
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\SignerOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */