package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessEntityExt", propOrder = {"businessEntity", "any"})
public class BusinessEntityExt {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected BusinessEntity businessEntity;
  
  @XmlAnyElement(lax = true)
  protected List<Object> any;
  
  public BusinessEntity getBusinessEntity() {
    return this.businessEntity;
  }
  
  public void setBusinessEntity(BusinessEntity paramBusinessEntity) {
    this.businessEntity = paramBusinessEntity;
  }
  
  public List<Object> getAny() {
    if (this.any == null)
      this.any = new ArrayList(); 
    return this.any;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BusinessEntityExt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */