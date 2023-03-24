package com.sun.xml.registry.uddi;

import java.io.Serializable;
import java.security.Principal;
import java.util.ResourceBundle;

public class UDDIPrincipal implements Principal, Serializable {
  private String name;
  
  public UDDIPrincipal(String paramString) {
    if (paramString == null)
      throw new NullPointerException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIPrincipal:illegal_null_input")); 
    this.name = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String toString() {
    return "SamplePrincipal:  " + this.name;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof UDDIPrincipal))
      return false; 
    UDDIPrincipal uDDIPrincipal = (UDDIPrincipal)paramObject;
    return getName().equals(uDDIPrincipal.getName());
  }
  
  public int hashCode() {
    return this.name.hashCode();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\UDDIPrincipal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */