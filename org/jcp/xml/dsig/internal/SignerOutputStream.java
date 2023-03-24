package org.jcp.xml.dsig.internal;

import java.io.ByteArrayOutputStream;
import java.security.Signature;
import java.security.SignatureException;

public class SignerOutputStream extends ByteArrayOutputStream {
  private final Signature sig;
  
  public SignerOutputStream(Signature paramSignature) {
    this.sig = paramSignature;
  }
  
  public void write(byte[] paramArrayOfbyte) {
    super.write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    try {
      this.sig.update(paramArrayOfbyte);
    } catch (SignatureException signatureException) {
      throw new RuntimeException("" + signatureException);
    } 
  }
  
  public void write(int paramInt) {
    super.write(paramInt);
    try {
      this.sig.update((byte)paramInt);
    } catch (SignatureException signatureException) {
      throw new RuntimeException("" + signatureException);
    } 
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    super.write(paramArrayOfbyte, paramInt1, paramInt2);
    try {
      this.sig.update(paramArrayOfbyte, paramInt1, paramInt2);
    } catch (SignatureException signatureException) {
      throw new RuntimeException("" + signatureException);
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\SignerOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */