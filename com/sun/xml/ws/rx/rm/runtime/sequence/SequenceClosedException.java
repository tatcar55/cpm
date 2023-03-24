/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*    */ 
/*    */ import com.sun.xml.ws.rx.rm.faults.AbstractSoapFaultException;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RmRuntimeVersion;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
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
/*    */ public class SequenceClosedException
/*    */   extends AbstractSoapFaultException
/*    */ {
/*    */   private static final long serialVersionUID = -3121993473458842931L;
/*    */   private final String sequenceId;
/*    */   
/*    */   public SequenceClosedException(String sequenceId, String message) {
/* 73 */     super(message, "The Sequence is closed and cannot accept new messages.", false);
/*    */     
/* 75 */     this.sequenceId = sequenceId;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractSoapFaultException.Code getCode() {
/* 80 */     return AbstractSoapFaultException.Code.Sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public QName getSubcode(RmRuntimeVersion rv) {
/* 85 */     return rv.protocolVersion.sequenceClosedFaultCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public Detail getDetail(RuntimeContext rc) {
/* 90 */     return (new AbstractSoapFaultException.DetailBuilder(rc)).addSequenceIdentifier(this.sequenceId).build();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\SequenceClosedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */