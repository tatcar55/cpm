package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authToken", propOrder = {"authInfo"})
public class AuthToken {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String authInfo;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String operator;
  
  public String getAuthInfo() {
    return this.authInfo;
  }
  
  public void setAuthInfo(String paramString) {
    this.authInfo = paramString;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
  
  public String getOperator() {
    return this.operator;
  }
  
  public void setOperator(String paramString) {
    this.operator = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\AuthToken.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */