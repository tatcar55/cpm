/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*    */ 
/*    */ import com.sun.xml.ws.rx.rm.faults.AbstractSoapFaultException;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RmRuntimeVersion;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Detail;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InvalidAcknowledgementException
/*    */   extends AbstractSoapFaultException
/*    */ {
/*    */   private static final long serialVersionUID = 647447570493203088L;
/*    */   private final List<Sequence.AckRange> ackedRanges;
/*    */   
/*    */   public InvalidAcknowledgementException(String sequenceId, long messageIdentifier, List<Sequence.AckRange> ackedRanges) {
/* 79 */     super(LocalizationMessages.WSRM_1125_ILLEGAL_MESSAGE_ID(sequenceId, Long.valueOf(messageIdentifier)), "The SequenceAcknowledgement violates the cumulative Acknowledgement invariant.", false);
/*    */ 
/*    */ 
/*    */     
/* 83 */     this.ackedRanges = ackedRanges;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractSoapFaultException.Code getCode() {
/* 88 */     return AbstractSoapFaultException.Code.Sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public QName getSubcode(RmRuntimeVersion rv) {
/* 93 */     return rv.protocolVersion.invalidAcknowledgementFaultCode;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Detail getDetail(RuntimeContext rc) {
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\InvalidAcknowledgementException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */