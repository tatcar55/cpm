package com.sun.xml.bind.v2.model.core;

import java.util.List;

public interface ClassInfo<T, C> extends MaybeElement<T, C> {
  ClassInfo<T, C> getBaseClass();
  
  C getClazz();
  
  String getName();
  
  List<? extends PropertyInfo<T, C>> getProperties();
  
  boolean hasValueProperty();
  
  PropertyInfo<T, C> getProperty(String paramString);
  
  boolean hasProperties();
  
  boolean isAbstract();
  
  boolean isOrdered();
  
  boolean isFinal();
  
  boolean hasSubClasses();
  
  boolean hasAttributeWildcard();
  
  boolean inheritsAttributeWildcard();
  
  boolean declaresAttributeWildcard();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\ClassInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */