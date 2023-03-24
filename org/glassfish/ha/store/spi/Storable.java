package org.glassfish.ha.store.spi;

import java.util.Set;

public interface Storable {
  long _getVersion();
  
  Set<String> _getDirtyAttributeNames();
  
  <T> T _getAttributeValue(String paramString, Class<T> paramClass);
  
  String _getOwnerInstanceName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\Storable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */