package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigesterOutputStream extends ByteArrayOutputStream {
  static final byte[] none = "error".getBytes();
  
  final MessageDigestAlgorithm mda;
  
  static Logger log = Logger.getLogger(DigesterOutputStream.class.getName());
  
  public DigesterOutputStream(MessageDigestAlgorithm paramMessageDigestAlgorithm) {
    this.mda = paramMessageDigestAlgorithm;
  }
  
  public byte[] toByteArray() {
    return none;
  }
  
  public void write(byte[] paramArrayOfbyte) {
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(int paramInt) {
    this.mda.update((byte)paramInt);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    log.log(Level.FINE, "Pre-digested input:");
    StringBuffer stringBuffer = new StringBuffer(paramInt2);
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
      stringBuffer.append((char)paramArrayOfbyte[i]); 
    log.log(Level.FINE, stringBuffer.toString());
    this.mda.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] getDigestValue() {
    return this.mda.digest();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\DigesterOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */