package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "result", propOrder = {"errInfo"})
public class Result {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected ErrInfo errInfo;
  
  @XmlAttribute(required = true)
  protected int errno;
  
  @XmlAttribute
  protected KeyType keyType;
  
  public ErrInfo getErrInfo() {
    return this.errInfo;
  }
  
  public void setErrInfo(ErrInfo paramErrInfo) {
    this.errInfo = paramErrInfo;
  }
  
  public int getErrno() {
    return this.errno;
  }
  
  public void setErrno(int paramInt) {
    this.errno = paramInt;
  }
  
  public KeyType getKeyType() {
    return this.keyType;
  }
  
  public void setKeyType(KeyType paramKeyType) {
    this.keyType = paramKeyType;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\Result.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */