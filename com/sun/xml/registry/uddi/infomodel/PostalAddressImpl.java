package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.PostalAddress;

public class PostalAddressImpl extends ExtensibleObjectImpl implements PostalAddress, Serializable {
  private ClassificationScheme postalScheme;
  
  private String city = new String();
  
  private String country = new String();
  
  private String postalCode = new String();
  
  private String stateOrProvince = new String();
  
  private String street = new String();
  
  private String streetNumber = new String();
  
  private String addressType = new String();
  
  public PostalAddressImpl() {}
  
  public PostalAddressImpl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
    this();
    this.streetNumber = paramString1;
    this.street = paramString2;
    this.city = paramString3;
    this.stateOrProvince = paramString4;
    this.country = paramString5;
    this.postalCode = paramString6;
    this.addressType = paramString7;
  }
  
  public String getStreet() throws JAXRException {
    return this.street;
  }
  
  public void setStreet(String paramString) throws JAXRException {
    this.street = paramString;
  }
  
  public String getStreetNumber() throws JAXRException {
    return this.streetNumber;
  }
  
  public void setStreetNumber(String paramString) throws JAXRException {
    this.streetNumber = paramString;
  }
  
  public String getCity() throws JAXRException {
    return this.city;
  }
  
  public void setCity(String paramString) throws JAXRException {
    this.city = paramString;
  }
  
  public String getStateOrProvince() throws JAXRException {
    return this.stateOrProvince;
  }
  
  public void setStateOrProvince(String paramString) throws JAXRException {
    this.stateOrProvince = paramString;
  }
  
  public String getPostalCode() throws JAXRException {
    return this.postalCode;
  }
  
  public void setPostalCode(String paramString) throws JAXRException {
    this.postalCode = paramString;
  }
  
  public String getCountry() throws JAXRException {
    return this.country;
  }
  
  public void setCountry(String paramString) throws JAXRException {
    this.country = paramString;
  }
  
  public String getType() throws JAXRException {
    return this.addressType;
  }
  
  public void setType(String paramString) throws JAXRException {
    this.addressType = paramString;
  }
  
  public ClassificationScheme getPostalScheme() throws JAXRException {
    return this.postalScheme;
  }
  
  public void setPostalScheme(ClassificationScheme paramClassificationScheme) throws JAXRException {
    this.postalScheme = paramClassificationScheme;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\PostalAddressImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */