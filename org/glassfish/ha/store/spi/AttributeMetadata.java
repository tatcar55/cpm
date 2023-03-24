package org.glassfish.ha.store.spi;

import java.lang.reflect.Method;

public interface AttributeMetadata<S, T> {
  String getName();
  
  Class<T> getAttributeType();
  
  Method getGetterMethod();
  
  Method getSetterMethod();
  
  boolean isVersionAttribute();
  
  boolean isHashKeyAttribute();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\AttributeMetadata.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */