package org.jcp.xml.dsig.internal;

import com.sun.org.apache.xml.internal.security.utils.UnsyncByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigesterOutputStream extends OutputStream {
  private boolean buffer = false;
  
  private UnsyncByteArrayOutputStream bos;
  
  private final MessageDigest md;
  
  private static Logger log = Logger.getLogger("org.jcp.xml.dsig.internal");
  
  public DigesterOutputStream(MessageDigest paramMessageDigest) {
    this(paramMessageDigest, false);
  }
  
  public DigesterOutputStream(MessageDigest paramMessageDigest, boolean paramBoolean) {
    this.md = paramMessageDigest;
    this.buffer = paramBoolean;
    if (paramBoolean)
      this.bos = new UnsyncByteArrayOutputStream(); 
  }
  
  public void write(byte[] paramArrayOfbyte) {
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(int paramInt) {
    if (this.buffer)
      this.bos.write(paramInt); 
    this.md.update((byte)paramInt);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (this.buffer)
      this.bos.write(paramArrayOfbyte, paramInt1, paramInt2); 
    if (log.isLoggable(Level.FINER)) {
      log.log(Level.FINER, "Pre-digested input:");
      StringBuffer stringBuffer = new StringBuffer(paramInt2);
      for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
        stringBuffer.append((char)paramArrayOfbyte[i]); 
      log.log(Level.FINER, stringBuffer.toString());
    } 
    this.md.update(paramArrayOfbyte, paramInt1, paramInt2);
  }
  
  public byte[] getDigestValue() {
    return this.md.digest();
  }
  
  public InputStream getInputStream() {
    return this.buffer ? new ByteArrayInputStream(this.bos.toByteArray()) : null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jcp\xml\dsig\internal\DigesterOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */