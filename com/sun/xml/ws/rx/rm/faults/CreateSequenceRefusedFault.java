/*     */ package com.sun.xml.ws.rx.rm.faults;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
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
/*     */ public class CreateSequenceRefusedFault
/*     */   extends AbstractSoapFaultException
/*     */ {
/*     */   private static final long serialVersionUID = 1533003947712389030L;
/*     */   private static final String REASON = "The Create Sequence request has been refused by the RM Destination.";
/*     */   private final AbstractSoapFaultException.Code code;
/*     */   
/*     */   public CreateSequenceRefusedFault(String exceptionMessage, AbstractSoapFaultException.Code code) {
/*  70 */     super(exceptionMessage, "The Create Sequence request has been refused by the RM Destination.", true);
/*  71 */     this.code = code;
/*     */   }
/*     */   
/*     */   public CreateSequenceRefusedFault(String exceptionMessage, AbstractSoapFaultException.Code code, Throwable cause) {
/*  75 */     super(exceptionMessage, "The Create Sequence request has been refused by the RM Destination.", true, cause);
/*     */     
/*  77 */     this.code = code;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractSoapFaultException.Code getCode() {
/*  82 */     return this.code;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getSubcode(RmRuntimeVersion rv) {
/*  87 */     return rv.protocolVersion.createSequenceRefusedFaultCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public Detail getDetail(RuntimeContext rc) {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet toRequest(RuntimeContext rc) {
/*  97 */     return rc.communicator.createRequestPacket(createSoapFaultMessage(rc, false), getProperFaultActionForAddressingVersion(rc.rmVersion, rc.addressingVersion), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet toResponse(RuntimeContext rc, Packet request) {
/* 105 */     return rc.communicator.createResponsePacket(request, createSoapFaultMessage(rc, false), getProperFaultActionForAddressingVersion(rc.rmVersion, rc.addressingVersion));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\faults\CreateSequenceRefusedFault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */