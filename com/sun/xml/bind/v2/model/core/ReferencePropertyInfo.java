package com.sun.xml.bind.v2.model.core;

import java.util.Collection;
import java.util.Set;
import javax.xml.namespace.QName;

public interface ReferencePropertyInfo<T, C> extends PropertyInfo<T, C> {
  Set<? extends Element<T, C>> getElements();
  
  Collection<? extends TypeInfo<T, C>> ref();
  
  QName getXmlName();
  
  boolean isCollectionNillable();
  
  boolean isCollectionRequired();
  
  boolean isMixed();
  
  WildcardMode getWildcard();
  
  C getDOMHandler();
  
  boolean isRequired();
  
  Adapter<T, C> getAdapter();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\ReferencePropertyInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */