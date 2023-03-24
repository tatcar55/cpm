package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;

public class ConceptImpl extends RegistryObjectImpl implements Concept, Serializable {
  transient boolean childrenLoaded = false;
  
  transient boolean isPredefined = false;
  
  String value;
  
  ConceptImpl parentConcept;
  
  ClassificationScheme classificationScheme;
  
  ArrayList children = new ArrayList();
  
  public ConceptImpl() {
    this.childrenLoaded = false;
  }
  
  public ConceptImpl(Key paramKey) {
    this();
    this.key = paramKey;
  }
  
  public ConceptImpl(Key paramKey, String paramString1, String paramString2) {
    this(paramKey);
    this.description = new InternationalStringImpl(paramString1);
    this.name = new InternationalStringImpl(paramString2);
  }
  
  public ConceptImpl(RegistryObject paramRegistryObject, String paramString1, String paramString2) throws JAXRException {
    this();
    if (paramRegistryObject instanceof ClassificationScheme) {
      ((ClassificationScheme)paramRegistryObject).addChildConcept(this);
    } else if (paramRegistryObject instanceof Concept) {
      ((Concept)paramRegistryObject).addChildConcept(this);
    } 
    this.name = new InternationalStringImpl(paramString1);
    this.value = paramString2;
  }
  
  public String getValue() throws JAXRException {
    if (this.value == null)
      getObject(); 
    return this.value;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
    setIsModified(true);
  }
  
  public void addChildConcept(Concept paramConcept) throws JAXRException {
    if (paramConcept != null && !this.children.contains(paramConcept)) {
      ((ConceptImpl)paramConcept).setParentConcept(this);
      this.children.add(paramConcept);
      setIsModified(true);
    } 
  }
  
  public void addChildConcepts(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<Concept> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext()) {
        Concept concept = iterator.next();
        addChildConcept(concept);
      } 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConceptImpl:Objects_in_collection_must_be_concepts"), classCastException);
    } 
  }
  
  public void removeChildConcept(Concept paramConcept) {
    if (paramConcept != null) {
      this.children.remove(paramConcept);
      setIsModified(true);
    } 
  }
  
  public void removeChildConcepts(Collection<?> paramCollection) {
    if (paramCollection != null) {
      this.children.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public int getChildConceptCount() {
    return this.children.size();
  }
  
  public Collection getChildrenConcepts() throws JAXRException {
    return (Collection)this.children.clone();
  }
  
  public Collection getDescendantConcepts() throws JAXRException {
    ArrayList arrayList = new ArrayList(this.children);
    for (Concept concept : this.children) {
      if (concept.getChildConceptCount() > 0)
        arrayList.addAll(concept.getDescendantConcepts()); 
    } 
    return arrayList;
  }
  
  public Concept getParentConcept() throws JAXRException {
    return this.parentConcept;
  }
  
  public ClassificationScheme getClassificationScheme() throws JAXRException {
    return (this.classificationScheme != null) ? this.classificationScheme : ((this.parentConcept != null) ? this.parentConcept.getClassificationScheme() : null);
  }
  
  public void setClassificationScheme(ClassificationScheme paramClassificationScheme) {
    this.classificationScheme = paramClassificationScheme;
  }
  
  public String getPath() throws JAXRException {
    return (this.parentConcept == null) ? ("/" + this.classificationScheme.getKey().getId() + "/" + this.value) : ("/" + this.parentConcept.getPath() + "/" + this.value);
  }
  
  public RegistryObject getParent() {
    return (RegistryObject)((this.parentConcept != null) ? this.parentConcept : this.classificationScheme);
  }
  
  public boolean isPredefined() {
    return this.isPredefined;
  }
  
  public void setPredefined(boolean paramBoolean) {
    this.isPredefined = paramBoolean;
  }
  
  public boolean getChildrenLoaded() throws JAXRException {
    return this.childrenLoaded;
  }
  
  public void setParentConcept(Concept paramConcept) {
    if (paramConcept instanceof ConceptImpl)
      this.parentConcept = (ConceptImpl)paramConcept; 
  }
  
  public void addExternalLink(ExternalLink paramExternalLink) throws JAXRException {
    if (this.externalLinks.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConceptImpl:ExternalLink_already_exists,_cannot_add_more.")); 
    if (paramExternalLink != null) {
      ExternalLinkImpl externalLinkImpl = (ExternalLinkImpl)paramExternalLink;
      externalLinkImpl.addLinkedObject(this);
      this.externalLinks.add(externalLinkImpl);
      setIsModified(true);
    } 
  }
  
  public void addExternalLinks(Collection paramCollection) throws JAXRException {
    if (this.externalLinks.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConceptImpl:ExternalLink_already_exists,_cannot_add_more.")); 
    if (paramCollection != null) {
      if (paramCollection.size() > 1)
        throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConceptImpl:Cannot_add_more_than_one_ExternalLink")); 
      Iterator<ExternalLink> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext())
          addExternalLink(iterator.next()); 
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConceptImpl:Objects_in_collection_must_be_ExternalLinks"), classCastException);
      } 
    } 
  }
  
  public void setExternalLinks(Collection paramCollection) throws JAXRException {
    if (paramCollection == null) {
      this.externalLinks.clear();
      return;
    } 
    if (paramCollection.size() > 1)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConceptImpl:Cannot_set_more_than_one_link.")); 
    this.externalLinks.clear();
    addExternalLinks(paramCollection);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ConceptImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */