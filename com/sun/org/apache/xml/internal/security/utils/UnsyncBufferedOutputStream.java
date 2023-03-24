package com.sun.org.apache.xml.internal.security.utils;

import java.io.IOException;
import java.io.OutputStream;

public class UnsyncBufferedOutputStream extends OutputStream {
  final OutputStream out;
  
  final byte[] buf = bufCahce.get();
  
  static final int size = 8192;
  
  private static ThreadLocal bufCahce = new ThreadLocal() {
      protected synchronized Object initialValue() {
        return new byte[8192];
      }
    };
  
  int pointer = 0;
  
  public UnsyncBufferedOutputStream(OutputStream paramOutputStream) {
    this.out = paramOutputStream;
  }
  
  public void write(byte[] paramArrayOfbyte) throws IOException {
    write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    int i = this.pointer + paramInt2;
    if (i > 8192) {
      flushBuffer();
      if (paramInt2 > 8192) {
        this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
        return;
      } 
      i = paramInt2;
    } 
    System.arraycopy(paramArrayOfbyte, paramInt1, this.buf, this.pointer, paramInt2);
    this.pointer = i;
  }
  
  private final void flushBuffer() throws IOException {
    if (this.pointer > 0)
      this.out.write(this.buf, 0, this.pointer); 
    this.pointer = 0;
  }
  
  public void write(int paramInt) throws IOException {
    if (this.pointer >= 8192)
      flushBuffer(); 
    this.buf[this.pointer++] = (byte)paramInt;
  }
  
  public void flush() throws IOException {
    flushBuffer();
    this.out.flush();
  }
  
  public void close() throws IOException {
    flush();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\UnsyncBufferedOutputStream.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */