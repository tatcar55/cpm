package com.sun.xml.ws.spi.db;

public interface PropertyGetter {
  Class getType();
  
  <A> A getAnnotation(Class<A> paramClass);
  
  Object get(Object paramObject);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\PropertyGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */