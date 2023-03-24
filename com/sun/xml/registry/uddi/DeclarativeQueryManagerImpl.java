package com.sun.xml.registry.uddi;

import javax.xml.registry.BulkResponse;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.Query;
import javax.xml.registry.UnsupportedCapabilityException;

public class DeclarativeQueryManagerImpl extends QueryManagerImpl {
  public Query createQuery(int paramInt, String paramString) throws InvalidRequestException, JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse executeQuery(Query paramQuery) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\DeclarativeQueryManagerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */