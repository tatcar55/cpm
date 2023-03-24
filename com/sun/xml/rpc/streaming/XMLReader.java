package com.sun.xml.rpc.streaming;

import java.util.Iterator;
import javax.xml.namespace.QName;

public interface XMLReader {
  public static final int BOF = 0;
  
  public static final int START = 1;
  
  public static final int END = 2;
  
  public static final int CHARS = 3;
  
  public static final int PI = 4;
  
  public static final int EOF = 5;
  
  int next();
  
  int nextContent();
  
  int nextElementContent();
  
  int getState();
  
  QName getName();
  
  String getURI();
  
  String getLocalName();
  
  Attributes getAttributes();
  
  String getValue();
  
  int getElementId();
  
  int getLineNumber();
  
  String getURI(String paramString);
  
  Iterator getPrefixes();
  
  XMLReader recordElement();
  
  void skipElement();
  
  void skipElement(int paramInt);
  
  void close();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */