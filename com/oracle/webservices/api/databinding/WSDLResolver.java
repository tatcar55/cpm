package com.oracle.webservices.api.databinding;

import javax.xml.transform.Result;
import javax.xml.ws.Holder;

public interface WSDLResolver {
  Result getWSDL(String paramString);
  
  Result getAbstractWSDL(Holder<String> paramHolder);
  
  Result getSchemaOutput(String paramString, Holder<String> paramHolder);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\WSDLResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */