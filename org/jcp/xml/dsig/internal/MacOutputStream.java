package org.jcp.xml.dsig.internal;

import java.io.ByteArrayOutputStream;
import javax.crypto.Mac;

public class MacOutputStream extends ByteArrayOutputStream {
  private final Mac mac;
  
  public MacOutputStream(Mac paramMac) {
    this.mac = paramMac;
  }
  
  public void write(byte[] paramArrayOfbyte) {
    super.write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    this.mac.update(paramArrayOfbyte);
  }
  
  public void write(int paramInt) {
    super.write(paramInt);
    this.mac.update((byte)paramInt);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    super.write(paramArrayOfbyte, paramInt1, paramInt2);
    this.mac.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\MacOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */