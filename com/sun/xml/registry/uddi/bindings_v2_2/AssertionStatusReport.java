package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assertionStatusReport", propOrder = {"assertionStatusItem"})
public class AssertionStatusReport {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<AssertionStatusItem> assertionStatusItem;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String operator;
  
  public List<AssertionStatusItem> getAssertionStatusItem() {
    if (this.assertionStatusItem == null)
      this.assertionStatusItem = new ArrayList<AssertionStatusItem>(); 
    return this.assertionStatusItem;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\AssertionStatusReport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */