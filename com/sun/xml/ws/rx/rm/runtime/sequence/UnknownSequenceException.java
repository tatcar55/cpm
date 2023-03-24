/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*     */ 
/*     */ import com.sun.xml.ws.rx.rm.faults.AbstractSoapFaultException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmRuntimeVersion;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UnknownSequenceException
/*     */   extends AbstractSoapFaultException
/*     */ {
/*     */   private static final long serialVersionUID = 8837409835889666590L;
/*     */   private final String sequenceId;
/*     */   
/*     */   public UnknownSequenceException(String sequenceId) {
/*  83 */     super(LocalizationMessages.WSRM_1124_NO_SUCH_SEQUENCE_ID_REGISTERED(sequenceId), "The value of wsrm:Identifier is not a known Sequence identifier.", true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.sequenceId = sequenceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSequenceId() {
/*  96 */     return this.sequenceId;
/*     */   }
/*     */   
/*     */   public AbstractSoapFaultException.Code getCode() {
/* 100 */     return AbstractSoapFaultException.Code.Sender;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getSubcode(RmRuntimeVersion rv) {
/* 105 */     return rv.protocolVersion.unknownSequenceFaultCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public Detail getDetail(RuntimeContext rc) {
/* 110 */     return (new AbstractSoapFaultException.DetailBuilder(rc)).addSequenceIdentifier(this.sequenceId).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\UnknownSequenceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */