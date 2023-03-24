package com.sun.xml.ws.rx.rm.runtime.sequence;

import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
import java.util.List;

public interface SequenceData {
  String getSequenceId();
  
  long getExpirationTime();
  
  String getBoundSecurityTokenReferenceId();
  
  long getLastMessageNumber();
  
  boolean getAckRequestedFlag();
  
  void setAckRequestedFlag(boolean paramBoolean);
  
  long getLastAcknowledgementRequestTime();
  
  void setLastAcknowledgementRequestTime(long paramLong);
  
  long getLastActivityTime();
  
  Sequence.State getState();
  
  void setState(Sequence.State paramState);
  
  long incrementAndGetLastMessageNumber(boolean paramBoolean);
  
  void registerReceivedUnackedMessageNumber(long paramLong) throws DuplicateMessageRegistrationException;
  
  void markAsAcknowledged(long paramLong);
  
  boolean isFailedOver(long paramLong);
  
  void attachMessageToUnackedMessageNumber(ApplicationMessage paramApplicationMessage);
  
  ApplicationMessage retrieveMessage(String paramString);
  
  List<Long> getUnackedMessageNumbers();
  
  List<Long> getLastMessageNumberWithUnackedMessageNumbers();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\SequenceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */