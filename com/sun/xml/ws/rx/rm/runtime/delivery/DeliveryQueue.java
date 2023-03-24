package com.sun.xml.ws.rx.rm.runtime.delivery;

import com.sun.xml.ws.rx.RxRuntimeException;
import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;

public interface DeliveryQueue {
  public static final long UNLIMITED_BUFFER_SIZE = -1L;
  
  void put(ApplicationMessage paramApplicationMessage) throws RxRuntimeException;
  
  long getRemainingMessageBufferSize();
  
  void onSequenceAcknowledgement();
  
  void close();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\DeliveryQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */