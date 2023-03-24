package com.sun.xml.ws.rx.message;

import java.io.Serializable;

public interface RxMessage {
  String getCorrelationId();
  
  byte[] toBytes();
  
  State getState();
  
  public static interface State extends Serializable {
    RxMessage toMessage();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\message\RxMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */