package com.sun.xml.ws.api.wsdl.parser;

import com.sun.istack.NotNull;
import java.util.List;
import javax.xml.transform.Source;

public abstract class ServiceDescriptor {
  @NotNull
  public abstract List<? extends Source> getWSDLs();
  
  @NotNull
  public abstract List<? extends Source> getSchemas();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\wsdl\parser\ServiceDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */