/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.dev.ProtocolMessageHandler;
/*     */ import com.sun.xml.ws.rx.mc.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.util.ResumeFiberException;
/*     */ import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WsMcResponseHandler
/*     */   extends McResponseHandlerBase
/*     */ {
/*  62 */   private static final Logger LOGGER = Logger.getLogger(WsMcResponseHandler.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<String, ProtocolMessageHandler> actionToProtocolHandlerMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WsMcResponseHandler(McConfiguration configuration, MakeConnectionSenderTask mcSenderTask, SuspendedFiberStorage suspendedFiberStorage, Map<String, ProtocolMessageHandler> protocolHandlerMap) {
/*  72 */     super(configuration, mcSenderTask, suspendedFiberStorage);
/*     */     
/*  74 */     this.actionToProtocolHandlerMap = protocolHandlerMap;
/*     */   }
/*     */   
/*     */   public void onCompletion(Packet response) {
/*     */     try {
/*  79 */       Message responseMessage = response.getMessage();
/*     */       
/*  81 */       if (responseMessage == null) {
/*  82 */         LOGGER.warning(LocalizationMessages.WSMC_0112_NO_RESPONSE_RETURNED());
/*     */         
/*     */         return;
/*     */       } 
/*  86 */       if (!responseMessage.hasHeaders()) {
/*  87 */         LOGGER.severe(LocalizationMessages.WSMC_0113_NO_WSMC_HEADERS_IN_RESPONSE());
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       processMakeConnectionHeaders(responseMessage);
/*     */       
/*  93 */       if (responseMessage.isFault()) {
/*     */         
/*  95 */         String faultAction = responseMessage.getHeaders().getAction(this.configuration.getAddressingVersion(), this.configuration.getSoapVersion());
/*  96 */         if ((this.configuration.getRuntimeVersion()).protocolVersion.isFault(faultAction)) {
/*  97 */           SOAPFault fault = null;
/*     */           try {
/*  99 */             fault = responseMessage.readAsSOAPMessage().getSOAPBody().getFault();
/* 100 */           } catch (SOAPException ex) {
/* 101 */             throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0114_ERROR_UNMARSHALLING_SOAP_FAULT(), ex));
/*     */           } 
/*     */           
/* 104 */           throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0115_UNEXPECTED_PROTOCOL_ERROR(fault.getFaultString())));
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       Header wsaRelatesToHeader = responseMessage.getHeaders().get((this.configuration.getAddressingVersion()).relatesToTag, false);
/* 109 */       if (wsaRelatesToHeader != null) {
/*     */         
/* 111 */         setCorrelationId(wsaRelatesToHeader.getStringContent());
/*     */         try {
/* 113 */           resumeParentFiber(response);
/*     */           return;
/* 115 */         } catch (ResumeFiberException ex) {
/* 116 */           LOGGER.warning(LocalizationMessages.WSMC_0116_RESUME_PARENT_FIBER_ERROR(), (Throwable)ex);
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       LOGGER.finer(LocalizationMessages.WSMC_0117_PROCESSING_RESPONSE_AS_PROTOCOL_MESSAGE());
/* 121 */       Header wsaActionHeader = responseMessage.getHeaders().get((this.configuration.getAddressingVersion()).actionTag, false);
/* 122 */       if (wsaActionHeader != null) {
/* 123 */         String wsaAction = wsaActionHeader.getStringContent();
/* 124 */         ProtocolMessageHandler handler = this.actionToProtocolHandlerMap.get(wsaAction);
/* 125 */         if (handler != null) {
/* 126 */           LOGGER.finer(LocalizationMessages.WSMC_0118_PROCESSING_RESPONSE_IN_PROTOCOL_HANDLER(wsaAction, handler.getClass().getName()));
/*     */ 
/*     */ 
/*     */           
/* 130 */           handler.processProtocolMessage(response);
/*     */         } else {
/* 132 */           LOGGER.warning(LocalizationMessages.WSMC_0119_UNABLE_TO_FIND_PROTOCOL_HANDLER(wsaAction));
/*     */         } 
/*     */       } else {
/* 135 */         LOGGER.severe(LocalizationMessages.WSMC_0120_WSA_ACTION_HEADER_MISSING());
/*     */       } 
/*     */     } finally {
/* 138 */       this.mcSenderTask.clearMcRequestPendingFlag();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onCompletion(Throwable error) {
/*     */     try {
/* 144 */       LOGGER.warning(LocalizationMessages.WSMC_0121_FAILED_TO_SEND_WSMC_REQUEST(), error);
/* 145 */       this.suspendedFiberStorage.resumeAllFibers(error);
/*     */     } finally {
/* 147 */       this.mcSenderTask.clearMcRequestPendingFlag();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\WsMcResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */