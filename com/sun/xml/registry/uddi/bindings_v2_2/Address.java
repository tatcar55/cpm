package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "address", propOrder = {"addressLine"})
public class Address {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<AddressLine> addressLine;
  
  @XmlAttribute
  protected String sortCode;
  
  @XmlAttribute
  protected String tModelKey;
  
  @XmlAttribute
  protected String useType;
  
  public List<AddressLine> getAddressLine() {
    if (this.addressLine == null)
      this.addressLine = new ArrayList<AddressLine>(); 
    return this.addressLine;
  }
  
  public String getSortCode() {
    return this.sortCode;
  }
  
  public void setSortCode(String paramString) {
    this.sortCode = paramString;
  }
  
  public String getTModelKey() {
    return this.tModelKey;
  }
  
  public void setTModelKey(String paramString) {
    this.tModelKey = paramString;
  }
  
  public String getUseType() {
    return this.useType;
  }
  
  public void setUseType(String paramString) {
    this.useType = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\Address.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */