package com.sun.xml.ws.spi.db;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.WSDLGenerator;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import java.util.Map;

public interface DatabindingProvider {
  boolean isFor(String paramString);
  
  void init(Map<String, Object> paramMap);
  
  Databinding create(DatabindingConfig paramDatabindingConfig);
  
  WSDLGenerator wsdlGen(DatabindingConfig paramDatabindingConfig);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\DatabindingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */