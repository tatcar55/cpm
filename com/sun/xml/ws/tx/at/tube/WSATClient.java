package com.sun.xml.ws.tx.at.tube;

import com.sun.xml.ws.api.message.Header;
import java.util.List;
import java.util.Map;

public interface WSATClient {
  List<Header> doHandleRequest(TransactionalAttribute paramTransactionalAttribute, Map<String, Object> paramMap);
  
  boolean doHandleResponse(Map<String, Object> paramMap);
  
  void doHandleException(Map<String, Object> paramMap);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */