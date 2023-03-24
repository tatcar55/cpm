package org.glassfish.ha.store.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface BackingStoreFactory {
  <K extends java.io.Serializable, V extends java.io.Serializable> BackingStore<K, V> createBackingStore(BackingStoreConfiguration<K, V> paramBackingStoreConfiguration) throws BackingStoreException;
  
  BackingStoreTransaction createBackingStoreTransaction();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\api\BackingStoreFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */