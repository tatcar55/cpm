package com.sun.xml.ws.spi.db;

public interface PropertyAccessor<B, V> {
  V get(B paramB) throws DatabindingException;
  
  void set(B paramB, V paramV) throws DatabindingException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\PropertyAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */