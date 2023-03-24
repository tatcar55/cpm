/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelCreator;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelWSImpl;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPError;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPException;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSTCPDelegate
/*     */   implements WSTCPAdapterRegistry, TCPMessageListener
/*     */ {
/*  68 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*  71 */   private final Map<String, TCPAdapter> fixedUrlPatternEndpoints = new HashMap<String, TCPAdapter>();
/*  72 */   private final List<TCPAdapter> pathUrlPatternEndpoints = new ArrayList<TCPAdapter>();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile TCPAdapter serviceChannelWSAdapter;
/*     */ 
/*     */ 
/*     */   
/*     */   private WSTCPAdapterRegistry customWSRegistry;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomWSRegistry(@NotNull WSTCPAdapterRegistry customWSRegistry) {
/*  85 */     this.customWSRegistry = customWSRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAdapters(@NotNull String contextPath, @NotNull List<TCPAdapter> adapters) {
/*  91 */     for (TCPAdapter adapter : adapters) {
/*  92 */       registerEndpointUrlPattern(contextPath, adapter);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeAdapters(@NotNull String contextPath, @NotNull List<TCPAdapter> adapters) {
/*  98 */     for (TCPAdapter adapter : adapters) {
/*  99 */       String urlPattern = contextPath + adapter.urlPattern;
/* 100 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1100_WSTCP_DELEGATE_DEREGISTER_ADAPTER(urlPattern));
/*     */       
/* 102 */       if (this.fixedUrlPatternEndpoints.remove(urlPattern) == null) {
/* 103 */         this.pathUrlPatternEndpoints.remove(adapter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerEndpointUrlPattern(@NotNull String contextPath, @NotNull TCPAdapter adapter) {
/* 111 */     String urlPattern = contextPath + adapter.urlPattern;
/* 112 */     logger.log(Level.FINE, MessagesMessages.WSTCP_1101_WSTCP_DELEGATE_REGISTER_ADAPTER(urlPattern));
/*     */     
/* 114 */     if (urlPattern.endsWith("/*")) {
/* 115 */       this.pathUrlPatternEndpoints.add(adapter);
/*     */     }
/* 117 */     else if (!this.fixedUrlPatternEndpoints.containsKey(urlPattern)) {
/*     */ 
/*     */       
/* 120 */       this.fixedUrlPatternEndpoints.put(urlPattern, adapter);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TCPAdapter getTarget(@NotNull WSTCPURI tcpURI) {
/* 129 */     TCPAdapter result = null;
/* 130 */     String path = tcpURI.path;
/* 131 */     if (path != null) {
/* 132 */       result = this.fixedUrlPatternEndpoints.get(path);
/* 133 */       if (result == null) {
/* 134 */         for (TCPAdapter candidate : this.pathUrlPatternEndpoints) {
/* 135 */           if (path.startsWith(candidate.getValidPath())) {
/* 136 */             result = candidate;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 143 */     if (result == null && this.customWSRegistry != null) {
/* 144 */       if (logger.isLoggable(Level.FINE)) {
/* 145 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1102_WSTCP_DELEGATE_GOING_TO_CUSTOM_REG(tcpURI));
/*     */       }
/*     */       
/* 148 */       return this.customWSRegistry.getTarget(tcpURI);
/*     */     } 
/*     */     
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onError(ChannelContext channelContext, WSTCPError error) throws IOException {
/* 160 */     sendErrorResponse(channelContext, error);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMessage(@NotNull ChannelContext channelContext) throws IOException {
/* 168 */     if (logger.isLoggable(Level.FINE)) {
/* 169 */       Connection connection = channelContext.getConnection();
/* 170 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1103_WSTCP_DELEGATE_ON_MESSAGE(connection.getHost(), Integer.valueOf(connection.getPort()), connection.getLocalHost(), Integer.valueOf(connection.getLocalPort())));
/*     */     } 
/*     */     
/*     */     try {
/* 174 */       TCPAdapter target = null;
/* 175 */       if (channelContext.getChannelId() > 0) {
/* 176 */         WSTCPURI tcpURI = channelContext.getTargetWSURI();
/* 177 */         target = getTarget(tcpURI);
/*     */       } else {
/* 179 */         target = getServiceChannelWSAdapter();
/*     */       } 
/*     */       
/* 182 */       if (target != null) {
/* 183 */         target.handle(channelContext);
/*     */       } else {
/* 185 */         TCPAdapter.sendErrorResponse(channelContext, WSTCPError.createNonCriticalError(1, MessagesMessages.WSTCP_0026_UNKNOWN_CHANNEL_ID(Integer.valueOf(channelContext.getChannelId()))));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 190 */     catch (WSTCPException e) {
/* 191 */       Connection connection = channelContext.getConnection();
/* 192 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0023_TARGET_EXEC_ERROR(connection.getHost(), Integer.valueOf(connection.getPort())), (Throwable)e);
/*     */       
/* 194 */       sendErrorResponse(channelContext, e.getError());
/*     */       
/* 196 */       if (e.getError().isCritical()) {
/* 197 */         channelContext.getConnectionSession().close();
/*     */       }
/* 199 */     } catch (JAXWSExceptionBase e) {
/* 200 */       Connection connection = channelContext.getConnection();
/* 201 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0023_TARGET_EXEC_ERROR(connection.getHost(), Integer.valueOf(connection.getPort())), (Throwable)e);
/*     */       
/* 203 */       sendErrorResponse(channelContext, WSTCPError.createNonCriticalError(0, MessagesMessages.WSTCP_0025_GENERAL_CHANNEL_ERROR(MessagesMessages.WSTCP_0004_CHECK_SERVER_LOG())));
/*     */     }
/* 205 */     catch (IOException e) {
/* 206 */       Connection connection = channelContext.getConnection();
/* 207 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0023_TARGET_EXEC_ERROR(connection.getHost(), Integer.valueOf(connection.getPort())), e);
/* 208 */       throw e;
/* 209 */     } catch (Exception e) {
/* 210 */       Connection connection = channelContext.getConnection();
/* 211 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0023_TARGET_EXEC_ERROR(connection.getHost(), Integer.valueOf(connection.getPort())), e);
/* 212 */       sendErrorResponse(channelContext, WSTCPError.createNonCriticalError(0, MessagesMessages.WSTCP_0025_GENERAL_CHANNEL_ERROR(MessagesMessages.WSTCP_0004_CHECK_SERVER_LOG())));
/*     */     } finally {
/*     */       
/* 215 */       if (logger.isLoggable(Level.FINE)) {
/* 216 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1104_WSTCP_DELEGATE_ON_MESSAGE_COMPLETED());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 222 */     logger.log(Level.FINE, MessagesMessages.WSTCP_1105_WSTCP_DELEGATE_DESTROY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TCPAdapter getServiceChannelWSAdapter() throws Exception {
/* 231 */     if (this.serviceChannelWSAdapter == null) {
/* 232 */       registerServiceChannelWSAdapter();
/*     */     }
/*     */     
/* 235 */     return this.serviceChannelWSAdapter;
/*     */   }
/*     */   
/*     */   private void sendErrorResponse(ChannelContext channelContext, WSTCPError error) throws IOException {
/*     */     try {
/* 240 */       TCPAdapter.sendErrorResponse(channelContext, error);
/* 241 */     } catch (Throwable e) {
/* 242 */       logger.log(Level.SEVERE, MessagesMessages.WSTCP_0002_SERVER_ERROR_MESSAGE_SENDING_FAILED(), e);
/* 243 */       throw new IOException(e.getClass().getName() + ": " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void registerServiceChannelWSAdapter() throws Exception {
/* 248 */     if (this.serviceChannelWSAdapter == null) {
/* 249 */       WSEndpoint<ServiceChannelWSImpl> endpoint = ServiceChannelCreator.getServiceChannelEndpointInstance();
/* 250 */       String serviceNameLocal = endpoint.getServiceName().getLocalPart();
/*     */       
/* 252 */       this.serviceChannelWSAdapter = new TCPServiceChannelWSAdapter(serviceNameLocal, "/servicechannel", endpoint, this);
/*     */ 
/*     */ 
/*     */       
/* 256 */       registerEndpointUrlPattern("/service", this.serviceChannelWSAdapter);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\WSTCPDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */