package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;

public class ClassificationImpl extends RegistryObjectImpl implements Classification, Serializable {
  private Concept concept;
  
  private RegistryObject classifiedObject;
  
  private ClassificationScheme classificationScheme;
  
  private String value;
  
  public ClassificationImpl() {}
  
  public ClassificationImpl(ClassificationScheme paramClassificationScheme, String paramString1, String paramString2) {
    this();
    this.classificationScheme = paramClassificationScheme;
    this.name = new InternationalStringImpl(paramString1);
    this.value = paramString2;
  }
  
  public ClassificationImpl(Concept paramConcept) {
    this();
    this.concept = paramConcept;
  }
  
  public Concept getConcept() throws JAXRException {
    return this.concept;
  }
  
  public void setConcept(Concept paramConcept) throws JAXRException {
    this.concept = paramConcept;
    setIsModified(true);
  }
  
  public ClassificationScheme getClassificationScheme() throws JAXRException {
    return isExternal() ? this.classificationScheme : this.concept.getClassificationScheme();
  }
  
  public void setClassificationScheme(ClassificationScheme paramClassificationScheme) throws JAXRException {
    this.classificationScheme = paramClassificationScheme;
    setIsModified(true);
    this.concept = null;
  }
  
  public String getValue() throws JAXRException {
    return (this.concept != null) ? this.concept.getValue() : this.value;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
    setIsModified(true);
    this.concept = null;
  }
  
  public RegistryObject getClassifiedObject() throws JAXRException {
    return this.classifiedObject;
  }
  
  public void setClassifiedObject(RegistryObject paramRegistryObject) throws JAXRException {
    if (paramRegistryObject != null) {
      this.classifiedObject = paramRegistryObject;
      setIsModified(true);
    } 
  }
  
  public boolean isExternal() {
    return (this.concept == null);
  }
  
  public Key getKey() throws JAXRException {
    if (this.classifiedObject == null || this.classifiedObject.getKey() == null)
      return null; 
    String str = null;
    if (isExternal()) {
      if (this.classificationScheme == null || this.classificationScheme.getKey() == null)
        return null; 
      str = this.classifiedObject.getKey().getId() + ":" + this.classificationScheme.getKey().getId() + ":" + this.value;
    } else {
      if (this.concept == null || this.concept.getKey() == null)
        return null; 
      str = this.classifiedObject.getKey().getId() + ":" + this.concept.getKey().getId();
    } 
    return new KeyImpl(str);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ClassificationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */