package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Slot;

public class SlotImpl implements Slot, Serializable {
  private String name;
  
  private String slotType;
  
  private ArrayList values = new ArrayList();
  
  public SlotImpl() {}
  
  public SlotImpl(String paramString1, String paramString2, String paramString3) {
    this();
    this.name = paramString1;
    this.slotType = paramString3;
    this.values.add(paramString2);
  }
  
  public SlotImpl(String paramString1, Collection paramCollection, String paramString2) {
    this();
    this.name = paramString1;
    this.slotType = paramString2;
    this.values.addAll(paramCollection);
  }
  
  public String getName() throws JAXRException {
    return this.name;
  }
  
  public void setName(String paramString) throws JAXRException {
    this.name = paramString;
  }
  
  public String getSlotType() throws JAXRException {
    return this.slotType;
  }
  
  public void setSlotType(String paramString) throws JAXRException {
    this.slotType = paramString;
  }
  
  public Collection getValues() throws JAXRException {
    return (Collection)this.values.clone();
  }
  
  public void setValues(Collection paramCollection) throws JAXRException {
    this.values = new ArrayList();
    if (paramCollection != null)
      this.values.addAll(paramCollection); 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\SlotImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */