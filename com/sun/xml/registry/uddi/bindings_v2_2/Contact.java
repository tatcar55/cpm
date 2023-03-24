package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contact", propOrder = {"description", "personName", "phone", "email", "address"})
public class Contact {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String personName;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Phone> phone;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Email> email;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Address> address;
  
  @XmlAttribute
  protected String useType;
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public String getPersonName() {
    return this.personName;
  }
  
  public void setPersonName(String paramString) {
    this.personName = paramString;
  }
  
  public List<Phone> getPhone() {
    if (this.phone == null)
      this.phone = new ArrayList<Phone>(); 
    return this.phone;
  }
  
  public List<Email> getEmail() {
    if (this.email == null)
      this.email = new ArrayList<Email>(); 
    return this.email;
  }
  
  public List<Address> getAddress() {
    if (this.address == null)
      this.address = new ArrayList<Address>(); 
    return this.address;
  }
  
  public String getUseType() {
    return this.useType;
  }
  
  public void setUseType(String paramString) {
    this.useType = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\Contact.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */