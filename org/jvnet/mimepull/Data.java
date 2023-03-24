package org.jvnet.mimepull;

import java.nio.ByteBuffer;

interface Data {
  int size();
  
  byte[] read();
  
  long writeTo(DataFile paramDataFile);
  
  Data createNext(DataHead paramDataHead, ByteBuffer paramByteBuffer);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\Data.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */