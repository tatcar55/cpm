/*     */ package com.sun.xml.ws.transport.tcp.client;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionSession;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceChannelTransportPipe
/*     */   extends TCPTransportPipe
/*     */ {
/*  63 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.client");
/*     */ 
/*     */   
/*     */   public ServiceChannelTransportPipe(@NotNull ClientTubeAssemblerContext context) {
/*  67 */     super(context);
/*     */   }
/*     */   
/*     */   public ServiceChannelTransportPipe(ClientTubeAssemblerContext context, int customTCPPort) {
/*  71 */     super(context, customTCPPort);
/*     */   }
/*     */   
/*     */   private ServiceChannelTransportPipe(ServiceChannelTransportPipe that, TubeCloner cloner) {
/*  75 */     super(that, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet process(Packet packet) {
/*  80 */     if (logger.isLoggable(Level.FINE)) {
/*  81 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1001_TCP_SERVICE_TP_PROCESS_ENTER(packet.endpointAddress));
/*     */     }
/*  83 */     ChannelContext channelContext = null;
/*  84 */     WSConnectionManager wsConnectionManager = WSConnectionManager.getInstance();
/*     */     
/*     */     try {
/*  87 */       ContentType ct = this.defaultCodec.getStaticContentType(packet);
/*     */       
/*  89 */       channelContext = this.clientTransport.getConnectionContext();
/*  90 */       if (channelContext != null) {
/*  91 */         if (logger.isLoggable(Level.FINE)) {
/*  92 */           logger.log(Level.FINE, MessagesMessages.WSTCP_1002_TCP_SERVICE_TP_PROCESS_TRANSPORT_REUSE());
/*     */         }
/*  94 */         wsConnectionManager.lockConnection(channelContext.getConnectionSession());
/*     */       } else {
/*     */         
/*  97 */         if (logger.isLoggable(Level.FINE)) {
/*  98 */           logger.log(Level.FINE, MessagesMessages.WSTCP_1003_TCP_SERVICE_TP_PROCESS_TRANSPORT_CREATE());
/*     */         }
/* 100 */         ConnectionSession connectionSession = (ConnectionSession)packet.invocationProperties.get("tcpSession");
/*     */         
/* 102 */         channelContext = connectionSession.getServiceChannelContext();
/* 103 */         this.clientTransport.setup(channelContext);
/*     */       } 
/*     */       
/* 106 */       this.clientTransport.setContentType(ct.getContentType());
/*     */ 
/*     */ 
/*     */       
/* 110 */       writeTransportSOAPActionHeaderIfRequired(channelContext, ct, packet);
/*     */       
/* 112 */       if (logger.isLoggable(Level.FINE)) {
/* 113 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1004_TCP_SERVICE_TP_PROCESS_ENCODE(ct.getContentType()));
/*     */       }
/* 115 */       this.defaultCodec.encode(packet, this.clientTransport.openOutputStream());
/*     */       
/* 117 */       if (logger.isLoggable(Level.FINE)) {
/* 118 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1005_TCP_SERVICE_TP_PROCESS_SEND());
/*     */       }
/* 120 */       this.clientTransport.send();
/*     */       
/* 122 */       if (logger.isLoggable(Level.FINE)) {
/* 123 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1006_TCP_SERVICE_TP_PROCESS_OPEN_PREPARE_READING());
/*     */       }
/* 125 */       InputStream replyInputStream = this.clientTransport.openInputStream();
/*     */       
/* 127 */       if (logger.isLoggable(Level.FINE)) {
/* 128 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1007_TCP_SERVICE_TP_PROCESS_OPEN_PROCESS_READING(Integer.valueOf(this.clientTransport.getStatus()), this.clientTransport.getContentType()));
/*     */       }
/* 130 */       if (this.clientTransport.getStatus() != 2) {
/* 131 */         String contentTypeStr = this.clientTransport.getContentType();
/*     */         
/* 133 */         Packet reply = packet.createClientResponse(null);
/* 134 */         this.defaultCodec.decode(replyInputStream, contentTypeStr, reply);
/*     */         
/* 136 */         reply.addSatellite((PropertySet)this.clientTransport);
/* 137 */         return reply;
/*     */       } 
/* 139 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0016_ERROR_WS_EXECUTION_ON_SERVER(this.clientTransport.getError()));
/* 140 */       throw new WSTCPException(this.clientTransport.getError());
/*     */     }
/* 142 */     catch (WebServiceException wex) {
/* 143 */       abortSession(channelContext);
/* 144 */       throw wex;
/* 145 */     } catch (Exception ex) {
/* 146 */       abortSession(channelContext);
/* 147 */       this.clientTransport.setup(null);
/*     */       
/* 149 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0017_ERROR_WS_EXECUTION_ON_CLIENT(), ex);
/* 150 */       throw new WebServiceException(MessagesMessages.WSTCP_0017_ERROR_WS_EXECUTION_ON_CLIENT(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 156 */     return new ServiceChannelTransportPipe(this, cloner);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\client\ServiceChannelTransportPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */