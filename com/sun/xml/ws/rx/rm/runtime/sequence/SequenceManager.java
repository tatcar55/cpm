package com.sun.xml.ws.rx.rm.runtime.sequence;

import com.sun.xml.ws.commons.MOMRegistrationAware;
import com.sun.xml.ws.rx.util.TimeSynchronizer;
import java.util.Map;
import org.glassfish.gmbal.AMXMetadata;
import org.glassfish.gmbal.Description;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedObject;

@ManagedObject
@Description("Reliable Messaging Sequence Manager")
@AMXMetadata(type = "WSRMSequenceManager")
public interface SequenceManager extends TimeSynchronizer, MOMRegistrationAware {
  public static final String MANAGED_BEAN_NAME = "RMSequenceManager";
  
  @ManagedAttribute
  @Description("All RM sequences")
  Map<String, Sequence> sequences();
  
  @ManagedAttribute
  @Description("Collection of sequence ID pairs that form an RM session")
  Map<String, String> boundSequences();
  
  @ManagedAttribute
  @Description("Unique identifier of the WS endpoint for which this particular sequence manager will be used")
  String uniqueEndpointId();
  
  @ManagedAttribute
  @Description("Determines whether this implementation of SeqenceManager is persistent")
  boolean persistent();
  
  @ManagedAttribute
  @Description("Number of concurrently opened (not terminated) inbound sequences (determines number of concurrent RM sessions)")
  long concurrentlyOpenedInboundSequencesCount();
  
  Sequence closeSequence(String paramString) throws UnknownSequenceException;
  
  Sequence createOutboundSequence(String paramString1, String paramString2, long paramLong) throws DuplicateSequenceException;
  
  Sequence createInboundSequence(String paramString1, String paramString2, long paramLong) throws DuplicateSequenceException;
  
  String generateSequenceUID();
  
  Sequence getSequence(String paramString) throws UnknownSequenceException;
  
  Sequence getInboundSequence(String paramString) throws UnknownSequenceException;
  
  Sequence getOutboundSequence(String paramString) throws UnknownSequenceException;
  
  boolean isValid(String paramString);
  
  Sequence terminateSequence(String paramString) throws UnknownSequenceException;
  
  void bindSequences(String paramString1, String paramString2) throws UnknownSequenceException;
  
  Sequence getBoundSequence(String paramString) throws UnknownSequenceException;
  
  boolean onMaintenance();
  
  void invalidateCache();
  
  void dispose();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\SequenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */