package com.sun.xml.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface MapPropertyInfo<T, C> extends PropertyInfo<T, C> {
  QName getXmlName();
  
  boolean isCollectionNillable();
  
  NonElement<T, C> getKeyType();
  
  NonElement<T, C> getValueType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\MapPropertyInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */