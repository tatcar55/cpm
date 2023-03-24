package com.sun.xml.bind.api;

public abstract class RawAccessor<B, V> {
  public abstract V get(B paramB) throws AccessorException;
  
  public abstract void set(B paramB, V paramV) throws AccessorException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\api\RawAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */