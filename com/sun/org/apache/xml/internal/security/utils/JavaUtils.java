package com.sun.org.apache.xml.internal.security.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaUtils {
  static Logger log = Logger.getLogger(JavaUtils.class.getName());
  
  public static byte[] getBytesFromFile(String paramString) throws FileNotFoundException, IOException {
    byte[] arrayOfByte = null;
    FileInputStream fileInputStream = new FileInputStream(paramString);
    try {
      UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
      byte[] arrayOfByte1 = new byte[1024];
      int i;
      while ((i = fileInputStream.read(arrayOfByte1)) > 0)
        unsyncByteArrayOutputStream.write(arrayOfByte1, 0, i); 
      arrayOfByte = unsyncByteArrayOutputStream.toByteArray();
    } finally {
      fileInputStream.close();
    } 
    return arrayOfByte;
  }
  
  public static void writeBytesToFilename(String paramString, byte[] paramArrayOfbyte) {
    FileOutputStream fileOutputStream = null;
    try {
      if (paramString != null && paramArrayOfbyte != null) {
        File file = new File(paramString);
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(paramArrayOfbyte);
        fileOutputStream.close();
      } else {
        log.log(Level.FINE, "writeBytesToFilename got null byte[] pointed");
      } 
    } catch (IOException iOException) {
      if (fileOutputStream != null)
        try {
          fileOutputStream.close();
        } catch (IOException iOException1) {} 
    } 
  }
  
  public static byte[] getBytesFromStream(InputStream paramInputStream) throws IOException {
    null = null;
    UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    int i;
    while ((i = paramInputStream.read(arrayOfByte)) > 0)
      unsyncByteArrayOutputStream.write(arrayOfByte, 0, i); 
    return unsyncByteArrayOutputStream.toByteArray();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\JavaUtils.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */