package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;

public class ExternalLinkImpl extends RegistryObjectImpl implements ExternalLink, Serializable {
  private ArrayList registryObjects = new ArrayList();
  
  private String externalURI;
  
  private URIValidatorImpl validator = new URIValidatorImpl();
  
  private RegistryObject parent;
  
  public ExternalLinkImpl() throws JAXRException {}
  
  public ExternalLinkImpl(String paramString) throws JAXRException {
    this();
    setExternalURI(paramString);
  }
  
  public ExternalLinkImpl(String paramString1, String paramString2) throws JAXRException {
    this(paramString1);
    this.description = new InternationalStringImpl(paramString2);
  }
  
  public Collection getLinkedObjects() throws JAXRException {
    return (Collection)this.registryObjects.clone();
  }
  
  public void setValidateURI(boolean paramBoolean) throws JAXRException {
    this.validator.setValidateURI(paramBoolean);
  }
  
  public boolean getValidateURI() {
    return this.validator.getValidateURI();
  }
  
  public String getExternalURI() throws JAXRException {
    return this.externalURI;
  }
  
  public void setExternalURI(String paramString) throws JAXRException {
    this.validator.validate(paramString);
    this.externalURI = paramString;
    setIsModified(true);
  }
  
  void addLinkedObject(RegistryObject paramRegistryObject) {
    this.registryObjects.add(paramRegistryObject);
  }
  
  public Key getKey() throws JAXRException {
    Iterator<RegistryObjectImpl> iterator = this.registryObjects.iterator();
    if (iterator.hasNext()) {
      RegistryObjectImpl registryObjectImpl = iterator.next();
      int i = registryObjectImpl.getSequenceId(this);
      return new KeyImpl(this.externalURI + ":" + i);
    } 
    return null;
  }
  
  void setParent(RegistryObject paramRegistryObject) {
    this.parent = paramRegistryObject;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ExternalLinkImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */