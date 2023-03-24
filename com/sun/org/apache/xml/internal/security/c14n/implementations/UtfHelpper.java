package com.sun.org.apache.xml.internal.security.c14n.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class UtfHelpper {
  static final void writeByte(String paramString, OutputStream paramOutputStream, Map paramMap) throws IOException {
    byte[] arrayOfByte = (byte[])paramMap.get(paramString);
    if (arrayOfByte == null) {
      arrayOfByte = getStringInUtf8(paramString);
      paramMap.put(paramString, arrayOfByte);
    } 
    paramOutputStream.write(arrayOfByte);
  }
  
  static final void writeCharToUtf8(char paramChar, OutputStream paramOutputStream) throws IOException {
    byte b;
    int i;
    if (paramChar < '') {
      paramOutputStream.write(paramChar);
      return;
    } 
    if ((paramChar >= '?' && paramChar <= '?') || (paramChar >= '?' && paramChar <= '?')) {
      paramOutputStream.write(63);
      return;
    } 
    if (paramChar > '߿') {
      char c1 = (char)(paramChar >>> 12);
      i = 224;
      if (c1 > '\000')
        i |= c1 & 0xF; 
      paramOutputStream.write(i);
      i = 128;
      b = 63;
    } else {
      i = 192;
      b = 31;
    } 
    char c = (char)(paramChar >>> 6);
    if (c > '\000')
      i |= c & b; 
    paramOutputStream.write(i);
    paramOutputStream.write(0x80 | paramChar & 0x3F);
  }
  
  static final void writeStringToUtf8(String paramString, OutputStream paramOutputStream) throws IOException {
    int i = paramString.length();
    byte b = 0;
    while (b < i) {
      byte b1;
      int j;
      char c1 = paramString.charAt(b++);
      if (c1 < '') {
        paramOutputStream.write(c1);
        continue;
      } 
      if ((c1 >= '?' && c1 <= '?') || (c1 >= '?' && c1 <= '?')) {
        paramOutputStream.write(63);
        continue;
      } 
      if (c1 > '߿') {
        char c = (char)(c1 >>> 12);
        j = 224;
        if (c > '\000')
          j |= c & 0xF; 
        paramOutputStream.write(j);
        j = 128;
        b1 = 63;
      } else {
        j = 192;
        b1 = 31;
      } 
      char c2 = (char)(c1 >>> 6);
      if (c2 > '\000')
        j |= c2 & b1; 
      paramOutputStream.write(j);
      paramOutputStream.write(0x80 | c1 & 0x3F);
    } 
  }
  
  public static final byte[] getStringInUtf8(String paramString) {
    int i = paramString.length();
    boolean bool = false;
    byte[] arrayOfByte = new byte[i];
    byte b1 = 0;
    byte b2 = 0;
    while (b1 < i) {
      byte b;
      byte b3;
      char c1 = paramString.charAt(b1++);
      if (c1 < '') {
        arrayOfByte[b2++] = (byte)c1;
        continue;
      } 
      if ((c1 >= '?' && c1 <= '?') || (c1 >= '?' && c1 <= '?')) {
        arrayOfByte[b2++] = 63;
        continue;
      } 
      if (!bool) {
        byte[] arrayOfByte1 = new byte[3 * i];
        System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b2);
        arrayOfByte = arrayOfByte1;
        bool = true;
      } 
      if (c1 > '߿') {
        char c = (char)(c1 >>> 12);
        b3 = -32;
        if (c > '\000')
          b3 = (byte)(b3 | c & 0xF); 
        arrayOfByte[b2++] = b3;
        b3 = -128;
        b = 63;
      } else {
        b3 = -64;
        b = 31;
      } 
      char c2 = (char)(c1 >>> 6);
      if (c2 > '\000')
        b3 = (byte)(b3 | c2 & b); 
      arrayOfByte[b2++] = b3;
      arrayOfByte[b2++] = (byte)(0x80 | c1 & 0x3F);
    } 
    if (bool) {
      byte[] arrayOfByte1 = new byte[b2];
      System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, b2);
      arrayOfByte = arrayOfByte1;
    } 
    return arrayOfByte;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\c14n\implementations\UtfHelpper.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */