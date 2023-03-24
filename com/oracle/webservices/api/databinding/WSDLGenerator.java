package com.oracle.webservices.api.databinding;

import java.io.File;

public interface WSDLGenerator {
  WSDLGenerator inlineSchema(boolean paramBoolean);
  
  WSDLGenerator property(String paramString, Object paramObject);
  
  void generate(WSDLResolver paramWSDLResolver);
  
  void generate(File paramFile, String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\WSDLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */