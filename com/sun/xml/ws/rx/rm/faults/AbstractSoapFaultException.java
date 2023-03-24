/*     */ package com.sun.xml.ws.rx.rm.faults;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmRuntimeVersion;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
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
/*     */ public abstract class AbstractSoapFaultException
/*     */   extends RxRuntimeException
/*     */ {
/*     */   private final boolean mustTryTodeliver;
/*     */   private final String faultReasonText;
/*     */   
/*     */   public enum Code
/*     */   {
/*  65 */     Sender {
/*     */       QName asQName(SOAPVersion sv) {
/*  67 */         return sv.faultCodeClient;
/*     */       }
/*     */     },
/*  70 */     Receiver {
/*     */       QName asQName(SOAPVersion sv) {
/*  72 */         return sv.faultCodeServer;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     abstract QName asQName(SOAPVersion param1SOAPVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractSoapFaultException(String exceptionMessage, String faultReasonText, boolean mustTryToDeliver, Throwable cause) {
/*  83 */     super(exceptionMessage, cause);
/*     */     
/*  85 */     this.faultReasonText = faultReasonText;
/*  86 */     this.mustTryTodeliver = mustTryToDeliver;
/*     */   }
/*     */   
/*     */   protected AbstractSoapFaultException(String exceptionMessage, String faultReasonText, boolean mustTryToDeliver) {
/*  90 */     super(exceptionMessage);
/*     */     
/*  92 */     this.faultReasonText = faultReasonText;
/*  93 */     this.mustTryTodeliver = mustTryToDeliver;
/*     */   }
/*     */   
/*     */   public abstract Code getCode();
/*     */   
/*     */   public abstract QName getSubcode(RmRuntimeVersion paramRmRuntimeVersion);
/*     */   
/*     */   public final String getReason() {
/* 101 */     return this.faultReasonText;
/*     */   }
/*     */   
/*     */   public abstract Detail getDetail(RuntimeContext paramRuntimeContext);
/*     */   
/*     */   public boolean mustTryToDeliver() {
/* 107 */     return this.mustTryTodeliver;
/*     */   }
/*     */   
/*     */   public Packet toRequest(RuntimeContext rc) {
/* 111 */     return rc.communicator.createRequestPacket(createSoapFaultMessage(rc, true), getProperFaultActionForAddressingVersion(rc.rmVersion, rc.addressingVersion), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet toResponse(RuntimeContext rc, Packet request) {
/* 118 */     return rc.communicator.createResponsePacket(request, createSoapFaultMessage(rc, true), getProperFaultActionForAddressingVersion(rc.rmVersion, rc.addressingVersion));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Message createSoapFaultMessage(RuntimeContext rc, boolean attachSequenceFaultElement) {
/*     */     try {
/* 126 */       SOAPFault soapFault = rc.soapVersion.saajSoapFactory.createFault();
/*     */ 
/*     */       
/* 129 */       if (this.faultReasonText != null) {
/* 130 */         soapFault.setFaultString(this.faultReasonText, Locale.ENGLISH);
/*     */       }
/*     */       
/* 133 */       Detail detail = getDetail(rc);
/*     */       
/* 135 */       switch (rc.soapVersion) {
/*     */         case SOAP_11:
/* 137 */           soapFault.setFaultCode(getSubcode(rc.rmVersion));
/*     */           break;
/*     */         case SOAP_12:
/* 140 */           soapFault.setFaultCode(getCode().asQName(rc.soapVersion));
/* 141 */           soapFault.appendFaultSubcode(getSubcode(rc.rmVersion));
/* 142 */           if (detail != null) {
/* 143 */             soapFault.addChildElement(detail);
/*     */           }
/*     */           break;
/*     */         default:
/* 147 */           throw new RxRuntimeException("Unsupported SOAP version: '" + rc.soapVersion.toString() + "'");
/*     */       } 
/*     */       
/* 150 */       Message soapFaultMessage = Messages.create(soapFault);
/*     */       
/* 152 */       if (attachSequenceFaultElement && rc.soapVersion == SOAPVersion.SOAP_11) {
/* 153 */         soapFaultMessage.getHeaders().add(rc.protocolHandler.createSequenceFaultElementHeader(getSubcode(rc.rmVersion), detail));
/*     */       }
/*     */       
/* 156 */       return soapFaultMessage;
/*     */     }
/* 158 */     catch (SOAPException ex) {
/* 159 */       throw new RxRuntimeException("Error creating a SOAP fault", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String getProperFaultActionForAddressingVersion(RmRuntimeVersion rmVersion, AddressingVersion addressingVersion) {
/* 169 */     return (addressingVersion == AddressingVersion.MEMBER) ? addressingVersion.getDefaultFaultAction() : rmVersion.protocolVersion.wsrmFaultAction;
/*     */   }
/*     */   
/*     */   protected static final class DetailBuilder
/*     */   {
/*     */     private final RuntimeContext rc;
/*     */     private final Detail detail;
/*     */     
/*     */     public DetailBuilder(RuntimeContext rc) {
/* 178 */       this.rc = rc;
/*     */       
/*     */       try {
/* 181 */         this.detail = rc.soapVersion.saajSoapFactory.createDetail();
/* 182 */       } catch (SOAPException ex) {
/* 183 */         throw new RxRuntimeException("Error creating a SOAP fault detail", ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Detail build() {
/* 188 */       return this.detail;
/*     */     }
/*     */     
/*     */     public DetailBuilder addSequenceIdentifier(String sequenceId) {
/*     */       try {
/* 193 */         this.detail.addDetailEntry(new QName(this.rc.rmVersion.protocolVersion.protocolNamespaceUri, "Identifier")).setValue(sequenceId);
/* 194 */       } catch (SOAPException ex) {
/* 195 */         throw new RxRuntimeException("Error creating a SOAP fault detail", ex);
/*     */       } 
/*     */       
/* 198 */       return this;
/*     */     }
/*     */     
/*     */     public DetailBuilder addMaxMessageNumber(long number) {
/*     */       try {
/* 203 */         this.detail.addDetailEntry(new QName(this.rc.rmVersion.protocolVersion.protocolNamespaceUri, "MaxMessageNumber")).setValue(Long.toString(number));
/* 204 */       } catch (SOAPException ex) {
/* 205 */         throw new RxRuntimeException("Error creating a SOAP fault detail", ex);
/*     */       } 
/*     */       
/* 208 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public DetailBuilder addSequenceAcknowledgement(List<Sequence.AckRange> ackedRanges) {
/* 214 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\faults\AbstractSoapFaultException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */