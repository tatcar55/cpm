package com.sun.xml.registry.uddi.infomodel;

import com.sun.xml.registry.uddi.RegistryServiceImpl;
import java.io.Serializable;
import java.util.Collection;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Slot;

public class AssociationImpl extends RegistryObjectImpl implements Association, Serializable {
  boolean isExtramural = false;
  
  boolean isConfirmedBySourceOwner = false;
  
  boolean isConfirmedByTargetOwner = false;
  
  private RegistryObject sourceObject;
  
  private RegistryObject targetObject;
  
  private Concept associationType;
  
  private boolean keyFieldsSet = false;
  
  private boolean keyGenerated = false;
  
  public AssociationImpl() {}
  
  public AssociationImpl(RegistryObject paramRegistryObject, Concept paramConcept) throws JAXRException {
    this.targetObject = paramRegistryObject;
    this.associationType = paramConcept;
  }
  
  public boolean isConfirmedBySourceOwner() throws JAXRException {
    return this.isConfirmedBySourceOwner;
  }
  
  public boolean isConfirmedByTargetOwner() throws JAXRException {
    return this.isConfirmedByTargetOwner;
  }
  
  public void setIsConfirmedBySourceOwner(boolean paramBoolean) throws JAXRException {
    this.isConfirmedBySourceOwner = paramBoolean;
  }
  
  public void setIsConfirmedByTargetOwner(boolean paramBoolean) throws JAXRException {
    this.isConfirmedByTargetOwner = paramBoolean;
  }
  
  public boolean isConfirmed() throws JAXRException {
    return (this.isConfirmedBySourceOwner && this.isConfirmedByTargetOwner);
  }
  
  public RegistryObject getSourceObject() throws JAXRException {
    return this.sourceObject;
  }
  
  public void setSourceObject(RegistryObject paramRegistryObject) throws JAXRException {
    this.sourceObject = paramRegistryObject;
    checkObjects();
    if (areKeyFieldsSet()) {
      this.keyGenerated = false;
      createKey();
    } 
  }
  
  public RegistryObject getTargetObject() throws JAXRException {
    return this.targetObject;
  }
  
  public void setTargetObject(RegistryObject paramRegistryObject) throws JAXRException {
    this.targetObject = paramRegistryObject;
    checkObjects();
    if (areKeyFieldsSet()) {
      this.keyGenerated = false;
      createKey();
    } 
  }
  
  public Concept getAssociationType() throws JAXRException {
    return this.associationType;
  }
  
  public void setAssociationType(Concept paramConcept) throws JAXRException {
    this.associationType = paramConcept;
    if (areKeyFieldsSet()) {
      this.keyGenerated = false;
      createKey();
    } 
  }
  
  public boolean isExtramural() throws JAXRException {
    return checkExtramural();
  }
  
  public void setIsExtramural(boolean paramBoolean) throws JAXRException {
    this.isExtramural = paramBoolean;
  }
  
  public void createKey() throws JAXRException {
    if (!this.keyGenerated && this.keyFieldsSet) {
      String str1 = this.sourceObject.getKey().getId();
      String str2 = this.targetObject.getKey().getId();
      String str3 = this.associationType.getValue();
      StringBuffer stringBuffer = new StringBuffer(400);
      stringBuffer.append(str1);
      stringBuffer.append(":");
      stringBuffer.append(str2);
      stringBuffer.append(":");
      stringBuffer.append(str3);
      this.key = new KeyImpl(stringBuffer.toString());
      this.keyGenerated = true;
    } 
  }
  
  boolean areKeyFieldsSet() throws JAXRException {
    if (this.sourceObject != null && this.targetObject != null && this.associationType != null) {
      Key key1 = this.sourceObject.getKey();
      Key key2 = this.targetObject.getKey();
      String str = this.associationType.getValue();
      if (key1 != null && key2 != null && str != null)
        this.keyFieldsSet = true; 
    } 
    return this.keyFieldsSet;
  }
  
  private void checkObjects() throws JAXRException {
    if (this.sourceObject == null || this.targetObject == null)
      return; 
    Key key1 = this.sourceObject.getKey();
    Key key2 = this.targetObject.getKey();
    if (key1 == null && key2 == null) {
      this.isConfirmedBySourceOwner = true;
      this.isConfirmedByTargetOwner = true;
      this.isExtramural = isExtramural();
    } 
  }
  
  private boolean checkExtramural() throws JAXRException {
    if (this.sourceObject != null && this.targetObject != null)
      if (((RegistryObjectImpl)this.sourceObject).isLoaded() && ((RegistryObjectImpl)this.targetObject).isLoaded()) {
        Slot slot1 = this.sourceObject.getSlot("authorizedName");
        Collection<E> collection = slot1.getValues();
        Slot slot2 = this.targetObject.getSlot("authorizedName");
        Collection collection1 = slot2.getValues();
        if (!collection.isEmpty() && !collection1.isEmpty()) {
          if (!collection.iterator().next().equals(collection1.iterator().next())) {
            this.isExtramural = true;
          } else {
            this.isExtramural = false;
          } 
          return this.isExtramural;
        } 
      } else {
        return (((RegistryObjectImpl)this.sourceObject).isNew() || ((RegistryObjectImpl)this.targetObject).isNew()) ? checkUserExtramural() : checkUserExtramural();
      }  
    return this.isExtramural;
  }
  
  private boolean checkUserExtramural() throws JAXRException {
    if (this.sourceObject != null && this.targetObject != null) {
      RegistryServiceImpl registryServiceImpl1 = (RegistryServiceImpl)this.sourceObject.getLifeCycleManager().getRegistryService();
      RegistryServiceImpl registryServiceImpl2 = (RegistryServiceImpl)this.targetObject.getLifeCycleManager().getRegistryService();
      String str1 = registryServiceImpl1.getCurrentUser();
      String str2 = registryServiceImpl2.getCurrentUser();
      if (str1.equals(str2)) {
        this.isExtramural = false;
      } else {
        this.isExtramural = true;
      } 
    } 
    return this.isExtramural;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\AssociationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */