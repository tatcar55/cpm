package com.sun.xml.bind.v2.model.core;

import java.util.Set;

public interface RegistryInfo<T, C> {
  Set<TypeInfo<T, C>> getReferences();
  
  C getClazz();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\RegistryInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */