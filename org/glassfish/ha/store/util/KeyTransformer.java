package org.glassfish.ha.store.util;

public interface KeyTransformer<K> {
  byte[] keyToByteArray(K paramK);
  
  K byteArrayToKey(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\stor\\util\KeyTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */