package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.AuditableEvent;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.User;

public class AuditableEventImpl extends RegistryObjectImpl implements AuditableEvent, Serializable {
  public User getUser() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Timestamp getTimestamp() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public int getEventType() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public RegistryObject getRegistryObject() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\AuditableEventImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */