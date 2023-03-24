/*     */ package com.sun.xml.ws.transport.tcp.servicechannel;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.server.ServerConnectionSession;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPAdapter;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPAdapterRegistry;
/*     */ import com.sun.xml.ws.transport.tcp.util.BindingUtils;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelSettings;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionSession;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Resource;
/*     */ import javax.jws.WebParam;
/*     */ import javax.jws.WebResult;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.ws.Holder;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebService
/*     */ public class ServiceChannelWSImpl
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */   
/*     */   @Resource
/*     */   private WebServiceContext wsContext;
/*     */ 
/*     */   
/*     */   public void initiateSession() throws ServiceChannelException {
/*  81 */     ChannelContext serviceChannelContext = getChannelContext();
/*  82 */     ConnectionSession connectionSession = serviceChannelContext.getConnectionSession();
/*  83 */     logger.log(Level.FINE, MessagesMessages.WSTCP_1140_SOAPTCP_SESSION_OPEN(Integer.valueOf(connectionSession.hashCode())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebResult(name = "channelId")
/*     */   public int openChannel(@WebParam(name = "targetWSURI", mode = WebParam.Mode.IN) String targetWSURI, @WebParam(name = "negotiatedMimeTypes", mode = WebParam.Mode.INOUT) Holder<List<String>> negotiatedMimeTypes, @WebParam(name = "negotiatedParams", mode = WebParam.Mode.INOUT) Holder<List<String>> negotiatedParams) throws ServiceChannelException {
/*  92 */     ChannelContext serviceChannelContext = getChannelContext();
/*  93 */     ServerConnectionSession connectionSession = (ServerConnectionSession)serviceChannelContext.getConnectionSession();
/*     */     
/*  95 */     WSTCPAdapterRegistry adapterRegistry = getAdapterRegistry();
/*     */     
/*  97 */     WSTCPURI tcpURI = WSTCPURI.parse(targetWSURI);
/*  98 */     TCPAdapter adapter = adapterRegistry.getTarget(tcpURI);
/*  99 */     if (adapter == null) throw new ServiceChannelException(ServiceChannelErrorCode.UNKNOWN_ENDPOINT_ADDRESS, MessagesMessages.WSTCP_0034_WS_ENDPOINT_NOT_FOUND(targetWSURI));
/*     */     
/* 101 */     BindingUtils.NegotiatedBindingContent serviceSupportedContent = BindingUtils.getNegotiatedContentTypesAndParams(adapter.getEndpoint().getBinding());
/*     */ 
/*     */     
/* 104 */     ((List)negotiatedMimeTypes.value).retainAll(serviceSupportedContent.negotiatedMimeTypes);
/* 105 */     if (((List)negotiatedMimeTypes.value).isEmpty()) {
/* 106 */       throw new ServiceChannelException(ServiceChannelErrorCode.CONTENT_NEGOTIATION_FAILED, MessagesMessages.WSTCP_0033_CONTENT_NEGOTIATION_FAILED(targetWSURI, serviceSupportedContent.negotiatedMimeTypes));
/*     */     }
/*     */     
/* 109 */     ((List)negotiatedParams.value).retainAll(serviceSupportedContent.negotiatedParams);
/*     */     
/* 111 */     int channelId = connectionSession.getNextAvailChannelId();
/* 112 */     ChannelSettings channelSettings = new ChannelSettings((List)negotiatedMimeTypes.value, (List)negotiatedParams.value, channelId, adapter.getEndpoint().getServiceName(), tcpURI);
/* 113 */     ChannelContext openedChannelContext = new ChannelContext((ConnectionSession)connectionSession, channelSettings);
/* 114 */     SOAPVersion soapVersion = adapter.getEndpoint().getBinding().getSOAPVersion();
/* 115 */     Codec defaultCodec = adapter.getEndpoint().createCodec();
/* 116 */     ChannelContext.configureCodec(openedChannelContext, soapVersion, defaultCodec);
/*     */     
/* 118 */     connectionSession.registerChannel(openedChannelContext);
/*     */     
/* 120 */     if (logger.isLoggable(Level.FINE)) {
/* 121 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1141_SOAPTCP_CHANNEL_OPEN(Integer.valueOf(connectionSession.hashCode()), Integer.valueOf(openedChannelContext.getChannelId()), targetWSURI));
/*     */     }
/* 123 */     return channelId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeChannel(@WebParam(name = "channelId", mode = WebParam.Mode.IN) int channelId) throws ServiceChannelException {
/* 128 */     ChannelContext serviceChannelContext = getChannelContext();
/* 129 */     ServerConnectionSession connectionSession = (ServerConnectionSession)serviceChannelContext.getConnectionSession();
/*     */     
/* 131 */     if (connectionSession.findWSServiceContextByChannelId(channelId) != null) {
/* 132 */       if (logger.isLoggable(Level.FINE)) {
/* 133 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1142_SOAPTCP_CHANNEL_CLOSE(Integer.valueOf(connectionSession.hashCode()), Integer.valueOf(channelId)));
/*     */       }
/* 135 */       connectionSession.deregisterChannel(channelId);
/*     */     } else {
/* 137 */       if (logger.isLoggable(Level.WARNING)) {
/* 138 */         logger.log(Level.WARNING, MessagesMessages.WSTCP_0035_UNKNOWN_CHANNEL_UD("Session: " + connectionSession.hashCode() + " Channel-id: " + channelId));
/*     */       }
/* 140 */       throw new ServiceChannelException(ServiceChannelErrorCode.UNKNOWN_CHANNEL_ID, MessagesMessages.WSTCP_0035_UNKNOWN_CHANNEL_UD(Integer.valueOf(channelId)));
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   private ChannelContext getChannelContext() {
/* 145 */     MessageContext messageContext = this.wsContext.getMessageContext();
/* 146 */     return (ChannelContext)messageContext.get("channelContext");
/*     */   }
/*     */   @NotNull
/*     */   private WSTCPAdapterRegistry getAdapterRegistry() {
/* 150 */     MessageContext messageContext = this.wsContext.getMessageContext();
/* 151 */     return (WSTCPAdapterRegistry)messageContext.get("AdapterRegistry");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\ServiceChannelWSImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */