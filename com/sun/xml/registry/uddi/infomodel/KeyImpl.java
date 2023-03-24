package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Key;

public class KeyImpl implements Key, Serializable {
  private String id;
  
  public KeyImpl() {}
  
  public KeyImpl(String paramString) {
    this.id = paramString;
  }
  
  public String getId() throws JAXRException {
    return this.id;
  }
  
  public void setId(String paramString) {
    this.id = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\KeyImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */