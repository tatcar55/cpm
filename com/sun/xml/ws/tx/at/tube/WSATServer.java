package com.sun.xml.ws.tx.at.tube;

import com.sun.xml.ws.api.message.HeaderList;

public interface WSATServer {
  void doHandleRequest(HeaderList paramHeaderList, TransactionalAttribute paramTransactionalAttribute);
  
  void doHandleResponse(TransactionalAttribute paramTransactionalAttribute);
  
  void doHandleException(Throwable paramThrowable);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */