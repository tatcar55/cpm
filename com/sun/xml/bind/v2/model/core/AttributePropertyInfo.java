package com.sun.xml.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface AttributePropertyInfo<T, C> extends PropertyInfo<T, C>, NonElementRef<T, C> {
  NonElement<T, C> getTarget();
  
  boolean isRequired();
  
  QName getXmlName();
  
  Adapter<T, C> getAdapter();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\AttributePropertyInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */