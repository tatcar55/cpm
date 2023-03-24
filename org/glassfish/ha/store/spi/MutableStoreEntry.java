package org.glassfish.ha.store.spi;

public interface MutableStoreEntry extends Storable {
  void _markStoreEntryAsDirty();
  
  void _markAsDirty(int paramInt);
  
  void _markAsClean(int paramInt);
  
  void _markStoreEntryAsClean();
  
  void _setOwnerId(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\MutableStoreEntry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */