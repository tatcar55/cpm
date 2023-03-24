package com.sun.xml.bind.v2.model.core;

import java.util.Collection;

public interface ElementInfo<T, C> extends Element<T, C> {
  ElementPropertyInfo<T, C> getProperty();
  
  NonElement<T, C> getContentType();
  
  T getContentInMemoryType();
  
  T getType();
  
  ElementInfo<T, C> getSubstitutionHead();
  
  Collection<? extends ElementInfo<T, C>> getSubstitutionMembers();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\ElementInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */