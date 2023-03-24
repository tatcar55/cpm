package com.sun.xml.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface Element<T, C> extends TypeInfo<T, C> {
  QName getElementName();
  
  Element<T, C> getSubstitutionHead();
  
  ClassInfo<T, C> getScope();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\Element.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */