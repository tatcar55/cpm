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

public class ClassificationSchemeImpl extends RegistryEntryImpl implements ClassificationScheme, Serializable {
  transient boolean childrenLoaded = false;
  
  transient boolean isPredefined = false;
  
  ArrayList children = new ArrayList();
  
  public ClassificationSchemeImpl() {
    this.childrenLoaded = false;
  }
  
  public ClassificationSchemeImpl(Key paramKey) {
    this();
    this.key = paramKey;
  }
  
  public ClassificationSchemeImpl(Concept paramConcept) throws JAXRException {
    this(paramConcept.getKey());
    this.name = paramConcept.getName();
    this.description = paramConcept.getDescription();
  }
  
  public ClassificationSchemeImpl(Key paramKey, String paramString1, String paramString2) {
    this(paramKey);
    this.description = new InternationalStringImpl(paramString1);
    this.name = new InternationalStringImpl(paramString2);
  }
  
  public ClassificationSchemeImpl(String paramString1, String paramString2, String paramString3) {
    this(new KeyImpl(paramString1), paramString2, paramString3);
  }
  
  public ClassificationSchemeImpl(String paramString1, String paramString2) {
    this();
    this.name = new InternationalStringImpl(paramString1);
    this.description = new InternationalStringImpl(paramString2);
  }
  
  public void addChildConcept(Concept paramConcept) throws JAXRException {
    if (paramConcept != null && !this.children.contains(paramConcept)) {
      ((ConceptImpl)paramConcept).setClassificationScheme(this);
      this.children.add(paramConcept);
      this.childrenLoaded = true;
      setIsModified(true);
    } 
  }
  
  public void addChildConcepts(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<ConceptImpl> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext()) {
        ConceptImpl conceptImpl = iterator.next();
        addChildConcept(conceptImpl);
      } 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Objects_in_collection_must_be_Concepts"), classCastException);
    } 
  }
  
  public void removeChildConcept(Concept paramConcept) throws JAXRException {
    if (paramConcept != null) {
      this.children.remove(paramConcept);
      setIsModified(true);
    } 
  }
  
  public void removeChildConcepts(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      this.children.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public int getChildConceptCount() throws JAXRException {
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
  
  public boolean isExternal() {
    return (this.children.size() == 0);
  }
  
  public boolean isPredefined() {
    return this.isPredefined;
  }
  
  public void setPredefined(boolean paramBoolean) {
    this.isPredefined = paramBoolean;
  }
  
  public boolean childrenLoaded() {
    return this.childrenLoaded;
  }
  
  public void setChildrenLoaded(boolean paramBoolean) {
    this.childrenLoaded = paramBoolean;
  }
  
  public void addExternalLink(ExternalLink paramExternalLink) throws JAXRException {
    if (this.externalLinks.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:ExternalLink_already_exists,_cannot_add_more.")); 
    if (paramExternalLink != null) {
      ExternalLinkImpl externalLinkImpl = (ExternalLinkImpl)paramExternalLink;
      externalLinkImpl.addLinkedObject(this);
      this.externalLinks.add(externalLinkImpl);
      setIsModified(true);
    } 
  }
  
  public void addExternalLinks(Collection paramCollection) throws JAXRException {
    if (this.externalLinks.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:ExternalLink_already_exists,_cannot_add_more.")); 
    if (paramCollection != null) {
      if (paramCollection.size() > 1)
        throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Cannot_add_more_than_one_ExternalLink")); 
      Iterator<ExternalLinkImpl> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          ExternalLinkImpl externalLinkImpl = iterator.next();
          externalLinkImpl.addLinkedObject(this);
          this.externalLinks.add(externalLinkImpl);
          setIsModified(true);
        } 
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Objects_in_collection_must_be_ExternalLinks"), classCastException);
      } 
    } 
  }
  
  public void setExternalLinks(Collection paramCollection) throws JAXRException {
    if (paramCollection == null) {
      this.externalLinks.clear();
      return;
    } 
    if (paramCollection.size() > 1)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ClassificationSchemeImpl:Cannot_set_more_than_one_ExternalLink.")); 
    this.externalLinks.clear();
    addExternalLinks(paramCollection);
  }
  
  public int getValueType() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setValueType(int paramInt) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ClassificationSchemeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */