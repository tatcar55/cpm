package com.sun.xml.bind.v2.model.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface ElementPropertyInfo<T, C> extends PropertyInfo<T, C> {
  List<? extends TypeRef<T, C>> getTypes();
  
  QName getXmlName();
  
  boolean isCollectionRequired();
  
  boolean isCollectionNillable();
  
  boolean isValueList();
  
  boolean isRequired();
  
  Adapter<T, C> getAdapter();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\ElementPropertyInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */