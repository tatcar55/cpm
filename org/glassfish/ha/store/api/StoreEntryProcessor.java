package org.glassfish.ha.store.api;

import java.io.Serializable;

public interface StoreEntryProcessor<K, V extends Serializable> extends Serializable {
  Serializable process(K paramK, V paramV);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\api\StoreEntryProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */