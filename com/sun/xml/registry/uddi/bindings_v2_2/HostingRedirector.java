package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hostingRedirector")
public class HostingRedirector {
  @XmlAttribute(required = true)
  protected String bindingKey;
  
  public String getBindingKey() {
    return this.bindingKey;
  }
  
  public void setBindingKey(String paramString) {
    this.bindingKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\HostingRedirector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */