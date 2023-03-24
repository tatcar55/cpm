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
/*    */ public class SequenceTerminatedException
/*    */   extends AbstractSoapFaultException
/*    */ {
/*    */   private static final long serialVersionUID = -4689338956255310299L;
/*    */   private final AbstractSoapFaultException.Code code;
/*    */   private final String sequenceId;
/*    */   
/*    */   public SequenceTerminatedException(String sequenceId, String message, AbstractSoapFaultException.Code code) {
/* 74 */     super(message, "The Sequence has been terminated due to an unrecoverable error.", true);
/*    */     
/* 76 */     this.code = code;
/* 77 */     this.sequenceId = sequenceId;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractSoapFaultException.Code getCode() {
/* 82 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public QName getSubcode(RmRuntimeVersion rv) {
/* 87 */     return rv.protocolVersion.sequenceTerminatedFaultCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public Detail getDetail(RuntimeContext rc) {
/* 92 */     return (new AbstractSoapFaultException.DetailBuilder(rc)).addSequenceIdentifier(this.sequenceId).build();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\SequenceTerminatedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */