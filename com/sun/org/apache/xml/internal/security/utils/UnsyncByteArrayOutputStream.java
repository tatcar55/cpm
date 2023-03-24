package com.sun.org.apache.xml.internal.security.utils;

import java.io.OutputStream;

public class UnsyncByteArrayOutputStream extends OutputStream {
  private static ThreadLocal bufCahce = new ThreadLocal() {
      protected synchronized Object initialValue() {
        return new byte[8192];
      }
    };
  
  byte[] buf = bufCahce.get();
  
  int size = 8192;
  
  int pos = 0;
  
  public void write(byte[] paramArrayOfbyte) {
    int i = this.pos + paramArrayOfbyte.length;
    if (i > this.size)
      expandSize(); 
    System.arraycopy(paramArrayOfbyte, 0, this.buf, this.pos, paramArrayOfbyte.length);
    this.pos = i;
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    int i = this.pos + paramInt2;
    if (i > this.size)
      expandSize(); 
    System.arraycopy(paramArrayOfbyte, paramInt1, this.buf, this.pos, paramInt2);
    this.pos = i;
  }
  
  public void write(int paramInt) {
    if (this.pos >= this.size)
      expandSize(); 
    this.buf[this.pos++] = (byte)paramInt;
  }
  
  public byte[] toByteArray() {
    byte[] arrayOfByte = new byte[this.pos];
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.pos);
    return arrayOfByte;
  }
  
  public void reset() {
    this.pos = 0;
  }
  
  void expandSize() {
    int i = this.size << 2;
    byte[] arrayOfByte = new byte[i];
    System.arraycopy(this.buf, 0, arrayOfByte, 0, this.pos);
    this.buf = arrayOfByte;
    this.size = i;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\UnsyncByteArrayOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */