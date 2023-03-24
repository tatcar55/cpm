package com.sun.xml.registry.common.tools.bindings_v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"content"})
@XmlRootElement(name = "namepattern")
public class Namepattern {
  @XmlValue
  protected String content;
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String paramString) {
    this.content = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\bindings_v3\Namepattern.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */