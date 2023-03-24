package com.sun.xml.ws.api.message;

import com.sun.xml.ws.api.WSBinding;
import java.util.Iterator;
import java.util.Set;
import javax.xml.namespace.QName;

public interface MessageHeaders {
  void understood(Header paramHeader);
  
  void understood(QName paramQName);
  
  void understood(String paramString1, String paramString2);
  
  Header get(String paramString1, String paramString2, boolean paramBoolean);
  
  Header get(QName paramQName, boolean paramBoolean);
  
  Iterator<Header> getHeaders(String paramString1, String paramString2, boolean paramBoolean);
  
  Iterator<Header> getHeaders(String paramString, boolean paramBoolean);
  
  Iterator<Header> getHeaders(QName paramQName, boolean paramBoolean);
  
  Iterator<Header> getHeaders();
  
  boolean add(Header paramHeader);
  
  Header remove(QName paramQName);
  
  Header remove(String paramString1, String paramString2);
  
  boolean addOrReplace(Header paramHeader);
  
  Set<QName> getUnderstoodHeaders();
  
  Set<QName> getNotUnderstoodHeaders(Set<String> paramSet, Set<QName> paramSet1, WSBinding paramWSBinding);
  
  boolean isUnderstood(Header paramHeader);
  
  boolean isUnderstood(QName paramQName);
  
  boolean isUnderstood(String paramString1, String paramString2);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\MessageHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */