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
/*     */ public final class MessageNumberRolloverException
/*     */   extends AbstractSoapFaultException
/*     */ {
/*     */   private static final long serialVersionUID = 7692916640741305184L;
/*     */   private long messageNumber;
/*     */   private String sequenceId;
/*     */   
/*     */   public long getMessageNumber() {
/*  73 */     return this.messageNumber;
/*     */   }
/*     */   
/*     */   public String getSequenceId() {
/*  77 */     return this.sequenceId;
/*     */   }
/*     */   
/*     */   public MessageNumberRolloverException(String sequenceId, long messageNumber) {
/*  81 */     super(LocalizationMessages.WSRM_1138_MESSAGE_NUMBER_ROLLOVER(sequenceId, Long.valueOf(messageNumber)), "The maximum value for wsrm:MessageNumber has been exceeded.", true);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.messageNumber = messageNumber;
/*  87 */     this.sequenceId = sequenceId;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractSoapFaultException.Code getCode() {
/*  92 */     return AbstractSoapFaultException.Code.Sender;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getSubcode(RmRuntimeVersion rv) {
/*  97 */     return rv.protocolVersion.messageNumberRolloverFaultCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public Detail getDetail(RuntimeContext rc) {
/* 102 */     return (new AbstractSoapFaultException.DetailBuilder(rc)).addSequenceIdentifier(this.sequenceId).addMaxMessageNumber(this.messageNumber).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\MessageNumberRolloverException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */