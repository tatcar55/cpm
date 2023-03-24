/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.ws.addressing.v200408.WsaTubeHelperImpl;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.ws.message.FaultDetailHeader;
/*     */ import com.sun.xml.ws.resources.AddressingMessages;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import javax.xml.ws.soap.SOAPBinding;
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
/*     */ abstract class WsaTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   @NotNull
/*     */   protected final WSDLPort wsdlPort;
/*     */   protected final WSBinding binding;
/*     */   final WsaTubeHelper helper;
/*     */   @NotNull
/*     */   protected final AddressingVersion addressingVersion;
/*     */   protected final SOAPVersion soapVersion;
/*     */   private final boolean addressingRequired;
/*     */   
/*     */   public WsaTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  99 */     super(next);
/* 100 */     this.wsdlPort = wsdlPort;
/* 101 */     this.binding = binding;
/* 102 */     addKnownHeadersToBinding(binding);
/* 103 */     this.addressingVersion = binding.getAddressingVersion();
/* 104 */     this.soapVersion = binding.getSOAPVersion();
/* 105 */     this.helper = getTubeHelper();
/* 106 */     this.addressingRequired = AddressingVersion.isRequired(binding);
/*     */   }
/*     */   
/*     */   public WsaTube(WsaTube that, TubeCloner cloner) {
/* 110 */     super(that, cloner);
/* 111 */     this.wsdlPort = that.wsdlPort;
/* 112 */     this.binding = that.binding;
/* 113 */     this.helper = that.helper;
/* 114 */     this.addressingVersion = that.addressingVersion;
/* 115 */     this.soapVersion = that.soapVersion;
/* 116 */     this.addressingRequired = that.addressingRequired;
/*     */   }
/*     */   
/*     */   private void addKnownHeadersToBinding(WSBinding binding) {
/* 120 */     for (AddressingVersion addrVersion : AddressingVersion.values()) {
/* 121 */       binding.addKnownHeader(addrVersion.actionTag);
/* 122 */       binding.addKnownHeader(addrVersion.faultDetailTag);
/* 123 */       binding.addKnownHeader(addrVersion.faultToTag);
/* 124 */       binding.addKnownHeader(addrVersion.fromTag);
/* 125 */       binding.addKnownHeader(addrVersion.messageIDTag);
/* 126 */       binding.addKnownHeader(addrVersion.relatesToTag);
/* 127 */       binding.addKnownHeader(addrVersion.replyToTag);
/* 128 */       binding.addKnownHeader(addrVersion.toTag);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processException(Throwable t) {
/* 134 */     return super.processException(t);
/*     */   }
/*     */   
/*     */   protected WsaTubeHelper getTubeHelper() {
/* 138 */     if (this.binding.isFeatureEnabled(AddressingFeature.class))
/* 139 */       return new WsaTubeHelperImpl(this.wsdlPort, null, this.binding); 
/* 140 */     if (this.binding.isFeatureEnabled(MemberSubmissionAddressingFeature.class))
/*     */     {
/* 142 */       return (WsaTubeHelper)new WsaTubeHelperImpl(this.wsdlPort, null, this.binding);
/*     */     }
/*     */     
/* 145 */     throw new WebServiceException(AddressingMessages.ADDRESSING_NOT_ENABLED(getClass().getSimpleName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Packet validateInboundHeaders(Packet packet) {
/*     */     SOAPFault sOAPFault;
/*     */     FaultDetailHeader faultDetailHeader;
/*     */     try {
/* 159 */       checkMessageAddressingProperties(packet);
/* 160 */       return packet;
/* 161 */     } catch (InvalidAddressingHeaderException e) {
/* 162 */       LOGGER.log(Level.WARNING, this.addressingVersion.getInvalidMapText() + ", Problem header:" + e.getProblemHeader() + ", Reason: " + e.getSubsubcode(), (Throwable)e);
/*     */       
/* 164 */       sOAPFault = this.helper.createInvalidAddressingHeaderFault(e, this.addressingVersion);
/* 165 */       faultDetailHeader = new FaultDetailHeader(this.addressingVersion, this.addressingVersion.problemHeaderQNameTag.getLocalPart(), e.getProblemHeader());
/* 166 */     } catch (MissingAddressingHeaderException e) {
/* 167 */       LOGGER.log(Level.WARNING, this.addressingVersion.getMapRequiredText() + ", Problem header:" + e.getMissingHeaderQName(), (Throwable)e);
/* 168 */       sOAPFault = this.helper.newMapRequiredFault(e);
/* 169 */       faultDetailHeader = new FaultDetailHeader(this.addressingVersion, this.addressingVersion.problemHeaderQNameTag.getLocalPart(), e.getMissingHeaderQName());
/*     */     } 
/*     */     
/* 172 */     if (sOAPFault != null) {
/*     */       
/* 174 */       if (this.wsdlPort != null && packet.getMessage().isOneWay(this.wsdlPort)) {
/* 175 */         return packet.createServerResponse(null, this.wsdlPort, null, this.binding);
/*     */       }
/*     */       
/* 178 */       Message m = Messages.create(sOAPFault);
/* 179 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 180 */         m.getMessageHeaders().add((Header)faultDetailHeader);
/*     */       }
/*     */       
/* 183 */       return packet.createServerResponse(m, this.wsdlPort, null, this.binding);
/*     */     } 
/*     */     
/* 186 */     return packet;
/*     */   }
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
/*     */   protected void checkMessageAddressingProperties(Packet packet) {
/* 202 */     checkCardinality(packet);
/*     */   }
/*     */   
/*     */   final boolean isAddressingEngagedOrRequired(Packet packet, WSBinding binding) {
/* 206 */     if (AddressingVersion.isRequired(binding)) {
/* 207 */       return true;
/*     */     }
/* 209 */     if (packet == null) {
/* 210 */       return false;
/*     */     }
/* 212 */     if (packet.getMessage() == null) {
/* 213 */       return false;
/*     */     }
/* 215 */     if (packet.getMessage().getMessageHeaders() != null) {
/* 216 */       return false;
/*     */     }
/* 218 */     String action = AddressingUtils.getAction(packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*     */ 
/*     */     
/* 221 */     if (action == null) {
/* 222 */       return true;
/*     */     }
/* 224 */     return true;
/*     */   }
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
/*     */   protected void checkCardinality(Packet packet) {
/* 242 */     Message message = packet.getMessage();
/* 243 */     if (message == null) {
/* 244 */       if (this.addressingRequired) {
/* 245 */         throw new WebServiceException(AddressingMessages.NULL_MESSAGE());
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 250 */     Iterator<Header> hIter = message.getMessageHeaders().getHeaders(this.addressingVersion.nsUri, true);
/*     */     
/* 252 */     if (!hIter.hasNext()) {
/*     */       
/* 254 */       if (this.addressingRequired)
/*     */       {
/* 256 */         throw new MissingAddressingHeaderException(this.addressingVersion.actionTag, packet);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 262 */     boolean foundFrom = false;
/* 263 */     boolean foundTo = false;
/* 264 */     boolean foundReplyTo = false;
/* 265 */     boolean foundFaultTo = false;
/* 266 */     boolean foundAction = false;
/* 267 */     boolean foundMessageId = false;
/* 268 */     boolean foundRelatesTo = false;
/* 269 */     QName duplicateHeader = null;
/*     */     
/* 271 */     while (hIter.hasNext()) {
/* 272 */       Header h = hIter.next();
/*     */ 
/*     */       
/* 275 */       if (!isInCurrentRole(h, this.binding)) {
/*     */         continue;
/*     */       }
/*     */       
/* 279 */       String local = h.getLocalPart();
/* 280 */       if (local.equals(this.addressingVersion.fromTag.getLocalPart())) {
/* 281 */         if (foundFrom) {
/* 282 */           duplicateHeader = this.addressingVersion.fromTag;
/*     */           break;
/*     */         } 
/* 285 */         foundFrom = true; continue;
/* 286 */       }  if (local.equals(this.addressingVersion.toTag.getLocalPart())) {
/* 287 */         if (foundTo) {
/* 288 */           duplicateHeader = this.addressingVersion.toTag;
/*     */           break;
/*     */         } 
/* 291 */         foundTo = true; continue;
/* 292 */       }  if (local.equals(this.addressingVersion.replyToTag.getLocalPart())) {
/* 293 */         if (foundReplyTo) {
/* 294 */           duplicateHeader = this.addressingVersion.replyToTag;
/*     */           break;
/*     */         } 
/* 297 */         foundReplyTo = true;
/*     */         try {
/* 299 */           h.readAsEPR(this.addressingVersion);
/* 300 */         } catch (XMLStreamException e) {
/* 301 */           throw new WebServiceException(AddressingMessages.REPLY_TO_CANNOT_PARSE(), e);
/*     */         }  continue;
/* 303 */       }  if (local.equals(this.addressingVersion.faultToTag.getLocalPart())) {
/* 304 */         if (foundFaultTo) {
/* 305 */           duplicateHeader = this.addressingVersion.faultToTag;
/*     */           break;
/*     */         } 
/* 308 */         foundFaultTo = true;
/*     */         try {
/* 310 */           h.readAsEPR(this.addressingVersion);
/* 311 */         } catch (XMLStreamException e) {
/* 312 */           throw new WebServiceException(AddressingMessages.FAULT_TO_CANNOT_PARSE(), e);
/*     */         }  continue;
/* 314 */       }  if (local.equals(this.addressingVersion.actionTag.getLocalPart())) {
/* 315 */         if (foundAction) {
/* 316 */           duplicateHeader = this.addressingVersion.actionTag;
/*     */           break;
/*     */         } 
/* 319 */         foundAction = true; continue;
/* 320 */       }  if (local.equals(this.addressingVersion.messageIDTag.getLocalPart())) {
/* 321 */         if (foundMessageId) {
/* 322 */           duplicateHeader = this.addressingVersion.messageIDTag;
/*     */           break;
/*     */         } 
/* 325 */         foundMessageId = true; continue;
/* 326 */       }  if (local.equals(this.addressingVersion.relatesToTag.getLocalPart())) {
/* 327 */         foundRelatesTo = true; continue;
/* 328 */       }  if (local.equals(this.addressingVersion.faultDetailTag.getLocalPart())) {
/*     */         continue;
/*     */       }
/*     */       
/* 332 */       System.err.println(AddressingMessages.UNKNOWN_WSA_HEADER());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 337 */     if (duplicateHeader != null) {
/* 338 */       throw new InvalidAddressingHeaderException(duplicateHeader, this.addressingVersion.invalidCardinalityTag);
/*     */     }
/*     */ 
/*     */     
/* 342 */     boolean engaged = foundAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     if (engaged || this.addressingRequired)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 358 */       checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageId, foundRelatesTo);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean isInCurrentRole(Header header, WSBinding binding) {
/* 367 */     if (binding == null)
/* 368 */       return true; 
/* 369 */     return ((SOAPBinding)binding).getRoles().contains(header.getRole(this.soapVersion));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final WSDLBoundOperation getWSDLBoundOperation(Packet packet) {
/* 375 */     if (this.wsdlPort == null)
/* 376 */       return null; 
/* 377 */     QName opName = packet.getWSDLOperation();
/* 378 */     if (opName != null)
/* 379 */       return this.wsdlPort.getBinding().get(opName); 
/* 380 */     return null;
/*     */   }
/*     */   
/*     */   protected void validateSOAPAction(Packet packet) {
/* 384 */     String gotA = AddressingUtils.getAction(packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*     */ 
/*     */     
/* 387 */     if (gotA == null)
/* 388 */       throw new WebServiceException(AddressingMessages.VALIDATION_SERVER_NULL_ACTION()); 
/* 389 */     if (packet.soapAction != null && !packet.soapAction.equals("\"\"") && !packet.soapAction.equals("\"" + gotA + "\"")) {
/* 390 */       throw new InvalidAddressingHeaderException(this.addressingVersion.actionTag, this.addressingVersion.actionMismatchTag);
/*     */     }
/*     */   }
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
/*     */   protected abstract void validateAction(Packet paramPacket);
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
/*     */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageId, boolean foundRelatesTo) {
/* 415 */     if (!foundAction)
/* 416 */       throw new MissingAddressingHeaderException(this.addressingVersion.actionTag, packet); 
/* 417 */     validateSOAPAction(packet);
/*     */   }
/* 419 */   private static final Logger LOGGER = Logger.getLogger(WsaTube.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */