package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.infomodel.ExtensibleObject;
import javax.xml.registry.infomodel.Slot;

public class ExtensibleObjectImpl implements ExtensibleObject, Serializable {
  private HashMap slots = new HashMap<Object, Object>();
  
  public void addSlot(Slot paramSlot) throws JAXRException {
    if (paramSlot == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ExtensibleObjectImpl:Slot_cannot_be_null")); 
    this.slots.put(paramSlot.getName(), paramSlot);
  }
  
  public void addSlots(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<Slot> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        addSlot(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ExtensibleObjectImpl:Objects_in_collection_must_be_Slots"), classCastException);
    } 
  }
  
  public Slot getSlot(String paramString) throws JAXRException {
    return (Slot)this.slots.get(paramString);
  }
  
  public Collection getSlots() throws JAXRException {
    return new ArrayList(this.slots.values());
  }
  
  public void removeSlot(String paramString) throws JAXRException {
    this.slots.remove(paramString);
  }
  
  public void removeSlots(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<String> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        removeSlot(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ExtensibleObjectImpl:Objects_in_collection_must_be_Strings"), classCastException);
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ExtensibleObjectImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */