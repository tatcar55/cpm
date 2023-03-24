package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "get_authToken")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "get_authToken")
public class GetAuthToken {
  @XmlAttribute(required = true)
  protected String cred;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String userID;
  
  public String getCred() {
    return this.cred;
  }
  
  public void setCred(String paramString) {
    this.cred = paramString;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
  
  public String getUserID() {
    return this.userID;
  }
  
  public void setUserID(String paramString) {
    this.userID = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\GetAuthToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */