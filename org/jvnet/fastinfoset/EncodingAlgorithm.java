package org.jvnet.fastinfoset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface EncodingAlgorithm {
  Object decodeFromBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws EncodingAlgorithmException;
  
  Object decodeFromInputStream(InputStream paramInputStream) throws EncodingAlgorithmException, IOException;
  
  void encodeToOutputStream(Object paramObject, OutputStream paramOutputStream) throws EncodingAlgorithmException, IOException;
  
  Object convertFromCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws EncodingAlgorithmException;
  
  void convertToCharacters(Object paramObject, StringBuffer paramStringBuffer) throws EncodingAlgorithmException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\EncodingAlgorithm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */