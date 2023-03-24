/*     */ package com.sun.xml.ws.transport.tcp.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.client.ClientTransportException;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ConnectionCacheFactory;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ConnectionFinder;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ContactInfo;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.OutboundConnectionCache;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.io.DataInOutUtils;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelException;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.stubs.ServiceChannelWSImpl;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.stubs.ServiceChannelWSImplService;
/*     */ import com.sun.xml.ws.transport.tcp.util.BindingUtils;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelSettings;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionManagementSettings;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionSession;
/*     */ import com.sun.xml.ws.transport.tcp.util.SessionAbortedException;
/*     */ import com.sun.xml.ws.transport.tcp.util.SessionCloseListener;
/*     */ import com.sun.xml.ws.transport.tcp.util.Version;
/*     */ import com.sun.xml.ws.transport.tcp.util.VersionController;
/*     */ import com.sun.xml.ws.transport.tcp.util.VersionMismatchException;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.BindingProvider;
/*     */ import javax.xml.ws.Holder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSConnectionManager
/*     */   implements ConnectionFinder<ConnectionSession>, SessionCloseListener<ConnectionSession>
/*     */ {
/*  88 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.client");
/*     */ 
/*     */   
/*  91 */   private static final WSConnectionManager instance = new WSConnectionManager();
/*     */ 
/*     */   
/*  94 */   private final Map<ConnectionSession, Thread> lockedConnections = new WeakHashMap<ConnectionSession, Thread>();
/*     */   
/*     */   public static WSConnectionManager getInstance() {
/*  97 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private volatile OutboundConnectionCache<ConnectionSession> connectionCache;
/*     */   
/*     */   private WSConnectionManager() {
/* 104 */     ConnectionManagementSettings settings = ConnectionManagementSettings.getSettingsHolder().getClientSettings();
/*     */     
/* 106 */     int highWatermark = settings.getHighWatermark();
/* 107 */     int numberToReclaim = settings.getNumberToReclaim();
/* 108 */     int maxParallelConnections = settings.getMaxParallelConnections();
/*     */     
/* 110 */     this.connectionCache = ConnectionCacheFactory.makeBlockingOutboundConnectionCache("SOAP/TCP client side cache", highWatermark, numberToReclaim, maxParallelConnections, logger);
/*     */ 
/*     */     
/* 113 */     if (logger.isLoggable(Level.FINE)) {
/* 114 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1044_CONNECTION_MANAGER_CLIENT_SIDE_CONNECTION_CACHE(Integer.valueOf(highWatermark), Integer.valueOf(maxParallelConnections), Integer.valueOf(numberToReclaim)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ChannelContext openChannel(@NotNull WSTCPURI uri, @NotNull WSService wsService, @NotNull WSBinding wsBinding, @NotNull Codec defaultCodec) throws InterruptedException, IOException, ServiceChannelException, VersionMismatchException {
/* 123 */     if (logger.isLoggable(Level.FINE)) {
/* 124 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1030_CONNECTION_MANAGER_ENTER(uri, wsService.getServiceName(), wsBinding.getBindingID(), defaultCodec.getClass().getName()));
/*     */     }
/*     */ 
/*     */     
/* 128 */     ConnectionSession session = (ConnectionSession)this.connectionCache.get((ContactInfo)uri, this);
/* 129 */     ChannelContext channelContext = session.findWSServiceContextByURI(uri);
/* 130 */     if (channelContext == null) {
/* 131 */       lockConnection(session);
/* 132 */       channelContext = session.findWSServiceContextByURI(uri);
/* 133 */       if (channelContext == null) {
/* 134 */         channelContext = doOpenChannel(session, uri, wsService, wsBinding, defaultCodec);
/*     */       }
/*     */     } 
/*     */     
/* 138 */     if (logger.isLoggable(Level.FINE)) {
/* 139 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1033_CONNECTION_MANAGER_RETURN_CHANNEL_CONTEXT(Integer.valueOf(channelContext.getChannelId())));
/*     */     }
/* 141 */     return channelContext;
/*     */   }
/*     */   
/*     */   public void closeChannel(@NotNull ChannelContext channelContext) {
/* 145 */     ConnectionSession connectionSession = channelContext.getConnectionSession();
/* 146 */     ServiceChannelWSImpl serviceChannelWSImplPort = getSessionServiceChannel(connectionSession);
/*     */ 
/*     */     
/* 149 */     try { lockConnection(connectionSession);
/* 150 */       serviceChannelWSImplPort.closeChannel(channelContext.getChannelId());
/* 151 */       connectionSession.deregisterChannel(channelContext); }
/* 152 */     catch (SessionAbortedException e)
/*     */     {  }
/* 154 */     catch (InterruptedException e) {  }
/* 155 */     catch (ServiceChannelException e) {  }
/*     */     finally
/* 157 */     { freeConnection(connectionSession); }
/*     */   
/*     */   }
/*     */   
/*     */   public void lockConnection(@NotNull ConnectionSession connectionSession) throws InterruptedException, SessionAbortedException {
/* 162 */     synchronized (connectionSession) {
/*     */       while (true) {
/* 164 */         Thread thread = this.lockedConnections.get(connectionSession);
/* 165 */         if (thread == null) {
/* 166 */           this.lockedConnections.put(connectionSession, Thread.currentThread()); return;
/*     */         } 
/* 168 */         if (thread.equals(Thread.currentThread())) {
/*     */           return;
/*     */         }
/* 171 */         connectionSession.wait(500L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void freeConnection(@NotNull ConnectionSession connectionSession) {
/* 177 */     this.connectionCache.release((Connection)connectionSession, 0);
/* 178 */     synchronized (connectionSession) {
/* 179 */       this.lockedConnections.remove(connectionSession);
/* 180 */       connectionSession.notify();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void abortConnection(@NotNull ConnectionSession connectionSession) {
/* 185 */     this.connectionCache.close((Connection)connectionSession);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ConnectionSession createConnectionSession(@NotNull WSTCPURI tcpURI) throws VersionMismatchException, ServiceChannelException {
/*     */     try {
/* 193 */       if (logger.isLoggable(Level.FINE)) {
/* 194 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1034_CONNECTION_MANAGER_CREATE_SESSION_ENTER(tcpURI));
/*     */       }
/* 196 */       Connection connection = Connection.create(tcpURI.host, tcpURI.getEffectivePort());
/* 197 */       doSendMagicAndCheckVersions(connection);
/* 198 */       ConnectionSession connectionSession = new ClientConnectionSession(connection, this);
/*     */       
/* 200 */       ServiceChannelWSImplService serviceChannelWS = new ServiceChannelWSImplService();
/* 201 */       ServiceChannelWSImpl serviceChannelWSImplPort = serviceChannelWS.getServiceChannelWSImplPort();
/* 202 */       connectionSession.setAttribute("ServicePipeline", serviceChannelWSImplPort);
/*     */       
/* 204 */       BindingProvider bindingProvider = (BindingProvider)serviceChannelWSImplPort;
/* 205 */       bindingProvider.getRequestContext().put("tcpSession", connectionSession);
/*     */       
/* 207 */       if (logger.isLoggable(Level.FINE)) {
/* 208 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1035_CONNECTION_MANAGER_INITIATE_SESSION());
/*     */       }
/*     */ 
/*     */       
/* 212 */       serviceChannelWSImplPort.initiateSession();
/*     */       
/* 214 */       return connectionSession;
/* 215 */     } catch (IOException e) {
/*     */       
/* 217 */       throw new ClientTransportException(MessagesMessages.localizableWSTCP_0015_ERROR_PROTOCOL_VERSION_EXCHANGE(), e);
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
/*     */   @NotNull
/*     */   private ChannelContext doOpenChannel(@NotNull ConnectionSession connectionSession, @NotNull WSTCPURI targetWSURI, @NotNull WSService wsService, @NotNull WSBinding wsBinding, @NotNull Codec defaultCodec) throws IOException, ServiceChannelException {
/* 231 */     if (logger.isLoggable(Level.FINEST)) {
/* 232 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1036_CONNECTION_MANAGER_DO_OPEN_CHANNEL_ENTER());
/*     */     }
/* 234 */     ServiceChannelWSImpl serviceChannelWSImplPort = getSessionServiceChannel(connectionSession);
/*     */ 
/*     */     
/* 237 */     BindingUtils.NegotiatedBindingContent negotiatedContent = BindingUtils.getNegotiatedContentTypesAndParams(wsBinding);
/*     */     
/* 239 */     if (logger.isLoggable(Level.FINEST)) {
/* 240 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1037_CONNECTION_MANAGER_DO_OPEN_WS_CALL(targetWSURI, negotiatedContent.negotiatedMimeTypes, negotiatedContent.negotiatedParams));
/*     */     }
/*     */     
/* 243 */     Holder<List<String>> negotiatedMimeTypesHolder = new Holder<List<String>>(negotiatedContent.negotiatedMimeTypes);
/* 244 */     Holder<List<String>> negotiatedParamsHolder = new Holder<List<String>>(negotiatedContent.negotiatedParams);
/* 245 */     int channelId = serviceChannelWSImplPort.openChannel(targetWSURI.toString(), negotiatedMimeTypesHolder, negotiatedParamsHolder);
/*     */ 
/*     */ 
/*     */     
/* 249 */     ChannelSettings settings = new ChannelSettings((List)negotiatedMimeTypesHolder.value, (List)negotiatedParamsHolder.value, channelId, wsService.getServiceName(), targetWSURI);
/*     */ 
/*     */     
/* 252 */     if (logger.isLoggable(Level.FINEST)) {
/* 253 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1038_CONNECTION_MANAGER_DO_OPEN_PROCESS_SERVER_SETTINGS(settings));
/*     */     }
/* 255 */     ChannelContext channelContext = new ChannelContext(connectionSession, settings);
/*     */     
/* 257 */     ChannelContext.configureCodec(channelContext, wsBinding.getSOAPVersion(), defaultCodec);
/*     */     
/* 259 */     if (logger.isLoggable(Level.FINEST)) {
/* 260 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1039_CONNECTION_MANAGER_DO_OPEN_REGISTER_CHANNEL(Integer.valueOf(channelContext.getChannelId())));
/*     */     }
/* 262 */     connectionSession.registerChannel(channelContext);
/* 263 */     return channelContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private ServiceChannelWSImpl getSessionServiceChannel(@NotNull ConnectionSession connectionSession) {
/* 270 */     return (ServiceChannelWSImpl)connectionSession.getAttribute("ServicePipeline");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionSession find(ContactInfo<ConnectionSession> contactInfo, Collection<ConnectionSession> idleConnections, Collection<ConnectionSession> busyConnections) throws IOException {
/* 276 */     WSTCPURI wsTCPURI = (WSTCPURI)contactInfo;
/* 277 */     ConnectionSession lru = null;
/* 278 */     for (ConnectionSession connectionSession : idleConnections) {
/* 279 */       if (connectionSession.findWSServiceContextByURI(wsTCPURI) != null) {
/* 280 */         return connectionSession;
/*     */       }
/* 282 */       if (lru == null) lru = connectionSession;
/*     */     
/*     */     } 
/* 285 */     if (lru != null || this.connectionCache.canCreateNewConnection(contactInfo)) return lru;
/*     */     
/* 287 */     for (ConnectionSession connectionSession : busyConnections) {
/* 288 */       if (connectionSession.findWSServiceContextByURI(wsTCPURI) != null) {
/* 289 */         return connectionSession;
/*     */       }
/* 291 */       if (lru == null) lru = connectionSession;
/*     */     
/*     */     } 
/* 294 */     return lru;
/*     */   }
/*     */   
/*     */   public void notifySessionClose(ConnectionSession connectionSession) {
/* 298 */     if (logger.isLoggable(Level.FINE)) {
/* 299 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1043_CONNECTION_MANAGER_NOTIFY_SESSION_CLOSE(connectionSession.getConnection()));
/*     */     }
/* 301 */     freeConnection(connectionSession);
/*     */   }
/*     */   
/*     */   private static void doSendMagicAndCheckVersions(Connection connection) throws IOException, VersionMismatchException {
/* 305 */     VersionController versionController = VersionController.getInstance();
/* 306 */     Version framingVersion = versionController.getFramingVersion();
/* 307 */     Version connectionManagementVersion = versionController.getConnectionManagementVersion();
/*     */     
/* 309 */     if (logger.isLoggable(Level.FINE)) {
/* 310 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1040_CONNECTION_MANAGER_DO_CHECK_VERSION_ENTER(framingVersion, connectionManagementVersion));
/*     */     }
/* 312 */     connection.setDirectMode(true);
/*     */     
/* 314 */     OutputStream outputStream = connection.openOutputStream();
/* 315 */     outputStream.write("vnd.sun.ws.tcp".getBytes("US-ASCII"));
/*     */     
/* 317 */     DataInOutUtils.writeInts4(outputStream, new int[] { framingVersion.getMajor(), framingVersion.getMinor(), connectionManagementVersion.getMajor(), connectionManagementVersion.getMinor() });
/*     */ 
/*     */ 
/*     */     
/* 321 */     connection.flush();
/* 322 */     if (logger.isLoggable(Level.FINE)) {
/* 323 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1041_CONNECTION_MANAGER_DO_CHECK_VERSION_SENT());
/*     */     }
/*     */     
/* 326 */     InputStream inputStream = connection.openInputStream();
/* 327 */     int[] versionInfo = new int[4];
/*     */     
/* 329 */     DataInOutUtils.readInts4(inputStream, versionInfo, 4);
/*     */     
/* 331 */     Version serverFramingVersion = new Version(versionInfo[0], versionInfo[1]);
/* 332 */     Version serverConnectionManagementVersion = new Version(versionInfo[2], versionInfo[3]);
/*     */     
/* 334 */     connection.setDirectMode(false);
/*     */     
/* 336 */     boolean success = versionController.isVersionSupported(serverFramingVersion, serverConnectionManagementVersion);
/*     */     
/* 338 */     if (!success)
/* 339 */       throw new VersionMismatchException(MessagesMessages.WSTCP_0006_VERSION_MISMATCH(), serverFramingVersion, serverConnectionManagementVersion); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\client\WSConnectionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */