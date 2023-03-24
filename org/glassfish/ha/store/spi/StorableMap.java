package org.glassfish.ha.store.spi;

import java.util.Collection;

public interface StorableMap<K, V> {
  Collection<K> getNewKeys();
  
  Collection<K> getModifiedKeys();
  
  Collection<K> getDeletedKeys();
  
  V get(K paramK);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\StorableMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */