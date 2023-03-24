package com.sun.xml.ws.api.model;

import com.sun.xml.bind.api.Bridge;

public interface CheckedException {
  SEIModel getOwner();
  
  JavaMethod getParent();
  
  Class getExceptionClass();
  
  Class getDetailBean();
  
  Bridge getBridge();
  
  ExceptionType getExceptionType();
  
  String getMessageName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\CheckedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */