package com.sun.xml.rpc.streaming;

import javax.xml.namespace.QName;

public interface Attributes {
  int getLength();
  
  boolean isNamespaceDeclaration(int paramInt);
  
  QName getName(int paramInt);
  
  String getURI(int paramInt);
  
  String getLocalName(int paramInt);
  
  String getPrefix(int paramInt);
  
  String getValue(int paramInt);
  
  int getIndex(QName paramQName);
  
  int getIndex(String paramString1, String paramString2);
  
  int getIndex(String paramString);
  
  String getValue(QName paramQName);
  
  String getValue(String paramString1, String paramString2);
  
  String getValue(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\Attributes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */