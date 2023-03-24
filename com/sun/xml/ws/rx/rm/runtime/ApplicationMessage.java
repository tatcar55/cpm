package com.sun.xml.ws.rx.rm.runtime;

import com.sun.xml.ws.rx.message.RxMessage;
import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;

public interface ApplicationMessage extends RxMessage {
  String getSequenceId();
  
  long getMessageNumber();
  
  void setSequenceData(String paramString, long paramLong);
  
  AcknowledgementData getAcknowledgementData();
  
  void setAcknowledgementData(AcknowledgementData paramAcknowledgementData);
  
  int getNextResendCount();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ApplicationMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */