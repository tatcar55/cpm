package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.activation.DataHandler;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.ExtrinsicObject;

public class ExtrinsicObjectImpl extends RegistryEntryImpl implements ExtrinsicObject, Serializable {
  public String getMimeType() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setMimeType(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public boolean isOpaque() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setOpaque(boolean paramBoolean) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public DataHandler getRepositoryItem() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setRepositoryItem(DataHandler paramDataHandler) throws UnsupportedCapabilityException, JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ExtrinsicObjectImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */