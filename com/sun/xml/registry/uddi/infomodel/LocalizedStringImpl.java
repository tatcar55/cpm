package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.Locale;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.LocalizedString;

public class LocalizedStringImpl implements LocalizedString, Serializable {
  private String charsetName = "UTF-8";
  
  private Locale locale = Locale.getDefault();
  
  private String value;
  
  public LocalizedStringImpl() {}
  
  public LocalizedStringImpl(Locale paramLocale, String paramString) {
    this();
    this.locale = paramLocale;
    this.value = paramString;
  }
  
  public Locale getLocale() throws JAXRException {
    return this.locale;
  }
  
  public void setLocale(Locale paramLocale) throws JAXRException {
    this.locale = paramLocale;
  }
  
  public String getValue() throws JAXRException {
    return this.value;
  }
  
  public void setValue(String paramString) throws JAXRException {
    this.value = paramString;
  }
  
  public String getCharsetName() throws JAXRException {
    return this.charsetName;
  }
  
  public void setCharsetName(String paramString) throws JAXRException {
    this.charsetName = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\LocalizedStringImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */