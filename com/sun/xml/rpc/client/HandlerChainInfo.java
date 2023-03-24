package com.sun.xml.rpc.client;

import java.util.Iterator;
import java.util.List;
import javax.xml.rpc.handler.HandlerInfo;

public interface HandlerChainInfo extends List {
  Iterator getHandlers();
  
  List getHandlerList();
  
  void addHandler(HandlerInfo paramHandlerInfo);
  
  String[] getRoles();
  
  void setRoles(String[] paramArrayOfString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\HandlerChainInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */