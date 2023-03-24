package com.sun.xml.ws.api.model;

import com.sun.xml.bind.api.Bridge;
import javax.jws.WebParam;
import javax.xml.namespace.QName;

public interface Parameter {
  SEIModel getOwner();
  
  JavaMethod getParent();
  
  QName getName();
  
  Bridge getBridge();
  
  WebParam.Mode getMode();
  
  int getIndex();
  
  boolean isWrapperStyle();
  
  boolean isReturnValue();
  
  ParameterBinding getBinding();
  
  ParameterBinding getInBinding();
  
  ParameterBinding getOutBinding();
  
  boolean isIN();
  
  boolean isOUT();
  
  boolean isINOUT();
  
  boolean isResponse();
  
  Object getHolderValue(Object paramObject);
  
  String getPartName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */