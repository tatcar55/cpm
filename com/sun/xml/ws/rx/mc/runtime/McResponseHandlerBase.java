/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.MessagePendingElement;
/*     */ import com.sun.xml.ws.rx.util.AbstractResponseHandler;
/*     */ import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class McResponseHandlerBase
/*     */   extends AbstractResponseHandler
/*     */   implements Fiber.CompletionCallback
/*     */ {
/*  61 */   private static final Logger LOGGER = Logger.getLogger(McResponseHandlerBase.class);
/*     */   
/*     */   protected final McConfiguration configuration;
/*     */   protected final MakeConnectionSenderTask mcSenderTask;
/*     */   
/*     */   protected McResponseHandlerBase(McConfiguration configuration, MakeConnectionSenderTask mcSenderTask, SuspendedFiberStorage suspendedFiberStorage, String correlationId) {
/*  67 */     super(suspendedFiberStorage, correlationId);
/*     */     
/*  69 */     this.configuration = configuration;
/*  70 */     this.mcSenderTask = mcSenderTask;
/*     */   }
/*     */   
/*     */   protected McResponseHandlerBase(McConfiguration configuration, MakeConnectionSenderTask mcSenderTask, SuspendedFiberStorage suspendedFiberStorage) {
/*  74 */     super(suspendedFiberStorage, null);
/*     */     
/*  76 */     this.configuration = configuration;
/*  77 */     this.mcSenderTask = mcSenderTask;
/*     */   }
/*     */   
/*     */   protected final void processMakeConnectionHeaders(@NotNull Message responseMessage) throws RxRuntimeException {
/*  81 */     assert responseMessage != null;
/*     */ 
/*     */     
/*  84 */     if (responseMessage.hasHeaders()) {
/*  85 */       MessagePendingElement messagePendingHeader = readHeaderAsUnderstood(responseMessage, (this.configuration.getRuntimeVersion()).protocolVersion.messagePendingHeaderName);
/*  86 */       if (messagePendingHeader != null && messagePendingHeader.isPending().booleanValue()) {
/*  87 */         this.mcSenderTask.scheduleMcRequest();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final <T> T readHeaderAsUnderstood(Message message, QName headerName) throws RxRuntimeException {
/*  94 */     Header header = message.getHeaders().get(headerName, true);
/*  95 */     if (header == null) {
/*  96 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 101 */       T result = (T)header.readAsJAXB(this.configuration.getRuntimeVersion().getUnmarshaller(this.configuration.getAddressingVersion()));
/* 102 */       return result;
/* 103 */     } catch (JAXBException ex) {
/* 104 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(String.format("Error unmarshalling header %s", new Object[] { headerName }), ex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McResponseHandlerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */