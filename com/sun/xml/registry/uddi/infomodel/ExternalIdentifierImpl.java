package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.ExternalIdentifier;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;

public class ExternalIdentifierImpl extends RegistryObjectImpl implements ExternalIdentifier, Serializable {
  private String value;
  
  private RegistryObject registryObject;
  
  private ClassificationScheme identificationScheme;
  
  public ExternalIdentifierImpl() {}
  
  public ExternalIdentifierImpl(Key paramKey, String paramString1, String paramString2) {
    this();
    this.key = paramKey;
    this.name = new InternationalStringImpl(paramString1);
    this.value = paramString2;
    try {
      if (keyFieldsSet())
        createKey(); 
    } catch (JAXRException jAXRException) {}
  }
  
  public ExternalIdentifierImpl(ClassificationScheme paramClassificationScheme, String paramString1, String paramString2) {
    this();
    this.identificationScheme = paramClassificationScheme;
    this.name = new InternationalStringImpl(paramString1);
    this.value = paramString2;
    try {
      if (keyFieldsSet())
        createKey(); 
    } catch (JAXRException jAXRException) {}
  }
  
  public RegistryObject getRegistryObject() throws JAXRException {
    return this.registryObject;
  }
  
  public void setRegistryObject(RegistryObject paramRegistryObject) throws JAXRException {
    this.registryObject = paramRegistryObject;
    if (keyFieldsSet())
      createKey(); 
  }
  
  public String getValue() throws JAXRException {
    return this.value;
  }
  
  public void setValue(String paramString) throws JAXRException {
    this.value = paramString;
    setIsModified(true);
    if (keyFieldsSet())
      createKey(); 
  }
  
  public ClassificationScheme getIdentificationScheme() throws JAXRException {
    return this.identificationScheme;
  }
  
  public void setIdentificationScheme(ClassificationScheme paramClassificationScheme) throws JAXRException {
    this.identificationScheme = paramClassificationScheme;
    setIsModified(true);
    if (keyFieldsSet())
      createKey(); 
  }
  
  boolean keyFieldsSet() throws JAXRException {
    boolean bool = true;
    if (isRetrieved() && (this.registryObject == null || this.identificationScheme == null || this.value == null)) {
      bool = false;
      if (this.registryObject.getKey() == null || this.identificationScheme.getKey() == null)
        bool = false; 
    } 
    return bool;
  }
  
  Key createKey() throws JAXRException {
    String str1 = null;
    String str2 = null;
    if (isRetrieved()) {
      if (this.registryObject != null)
        str1 = this.registryObject.getKey().getId(); 
      if (this.identificationScheme != null)
        str2 = this.identificationScheme.getKey().getId(); 
    } 
    StringBuffer stringBuffer = new StringBuffer(400);
    stringBuffer.append(str1);
    stringBuffer.append(":");
    stringBuffer.append(str2);
    stringBuffer.append(":");
    stringBuffer.append(this.value);
    this.key = new KeyImpl(stringBuffer.toString());
    return this.key;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ExternalIdentifierImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */