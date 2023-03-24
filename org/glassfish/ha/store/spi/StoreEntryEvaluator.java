package org.glassfish.ha.store.spi;

import java.io.Serializable;

public interface StoreEntryEvaluator<K, V> extends Serializable {
  Object eval(K paramK, V paramV);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\StoreEntryEvaluator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */