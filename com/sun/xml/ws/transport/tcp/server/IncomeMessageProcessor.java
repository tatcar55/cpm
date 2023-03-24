/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ConnectionCacheFactory;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.InboundConnectionCache;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.io.DataInOutUtils;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelContext;
/*     */ import com.sun.xml.ws.transport.tcp.util.ChannelSettings;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionManagementSettings;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionSession;
/*     */ import com.sun.xml.ws.transport.tcp.util.SessionCloseListener;
/*     */ import com.sun.xml.ws.transport.tcp.util.Version;
/*     */ import com.sun.xml.ws.transport.tcp.util.VersionController;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPError;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.WeakHashMap;
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
/*     */ public final class IncomeMessageProcessor
/*     */   implements SessionCloseListener
/*     */ {
/*  77 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*     */   private final TCPMessageListener listener;
/*     */ 
/*     */   
/*     */   private final Properties properties;
/*     */ 
/*     */   
/*     */   private volatile InboundConnectionCache<ServerConnectionSession> connectionCache;
/*     */   
/*  88 */   private static Map<Integer, IncomeMessageProcessor> portMessageProcessors = new HashMap<Integer, IncomeMessageProcessor>(1);
/*     */   
/*     */   private final Map<SocketChannel, ServerConnectionSession> connectionSessionMap;
/*     */   
/*     */   public static IncomeMessageProcessor registerListener(int port, @NotNull TCPMessageListener listener, @NotNull Properties properties) {
/*  93 */     IncomeMessageProcessor processor = new IncomeMessageProcessor(listener, properties);
/*  94 */     portMessageProcessors.put(Integer.valueOf(port), processor);
/*  95 */     return processor;
/*     */   }
/*     */   
/*     */   public static void releaseListener(int port) {
/*  99 */     portMessageProcessors.remove(Integer.valueOf(port));
/*     */   }
/*     */   @Nullable
/*     */   public static IncomeMessageProcessor getMessageProcessorForPort(int port) {
/* 103 */     return portMessageProcessors.get(Integer.valueOf(port));
/*     */   }
/*     */   
/*     */   private IncomeMessageProcessor(@NotNull TCPMessageListener listener) {
/* 107 */     this(listener, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IncomeMessageProcessor(@NotNull TCPMessageListener listener, @Nullable Properties properties) {
/* 177 */     this.connectionSessionMap = new WeakHashMap<SocketChannel, ServerConnectionSession>();
/*     */     this.listener = listener;
/*     */     this.properties = properties;
/*     */   }
/*     */   @Nullable
/* 182 */   private ServerConnectionSession getConnectionSession(@NotNull SocketChannel socketChannel) { ServerConnectionSession connectionSession = this.connectionSessionMap.get(socketChannel);
/* 183 */     if (connectionSession == null) {
/* 184 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 188 */     connectionSession.getConnection().setSocketChannel(socketChannel);
/* 189 */     return connectionSession; }
/*     */   public void process(@NotNull ByteBuffer messageBuffer, @NotNull SocketChannel socketChannel) throws IOException { if (logger.isLoggable(Level.FINE))
/*     */       logger.log(Level.FINE, MessagesMessages.WSTCP_1080_INCOME_MSG_PROC_ENTER(Connection.getHost(socketChannel), Integer.valueOf(Connection.getPort(socketChannel))));  if (this.connectionCache == null)
/*     */       setupInboundConnectionCache();  ServerConnectionSession connectionSession = getConnectionSession(socketChannel); if (connectionSession == null) { if (logger.isLoggable(Level.FINE))
/* 193 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1081_INCOME_MSG_CREATE_NEW_SESSION());  connectionSession = createConnectionSession(socketChannel, messageBuffer); if (connectionSession != null) { this.connectionCache.requestReceived((Connection)connectionSession); this.connectionCache.responseSent((Connection)connectionSession); offerConnectionSession(connectionSession); } else { logger.log(Level.WARNING, MessagesMessages.WSTCP_0006_VERSION_MISMATCH()); }  return; }  Connection connection = connectionSession.getConnection(); connection.setInputStreamByteBuffer(messageBuffer); this.connectionCache.requestReceived((Connection)connectionSession); try { do { connection.prepareForReading(); int channelId = connection.getChannelId(); ChannelContext channelContext = connectionSession.findWSServiceContextByChannelId(channelId); if (channelContext != null) { this.listener.onMessage(channelContext); } else { ChannelContext fakeChannelContext = createFakeChannelContext(channelId, connectionSession); this.listener.onError(fakeChannelContext, WSTCPError.createNonCriticalError(1, MessagesMessages.WSTCP_0026_UNKNOWN_CHANNEL_ID(Integer.valueOf(channelId)))); }  } while (messageBuffer.hasRemaining()); } finally { offerConnectionSession(connectionSession); this.connectionCache.responseSent((Connection)connectionSession); }  } private void offerConnectionSession(@NotNull ServerConnectionSession connectionSession) { this.connectionSessionMap.put(connectionSession.getConnection().getSocketChannel(), connectionSession);
/*     */ 
/*     */     
/* 196 */     connectionSession.getConnection().setSocketChannel(null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeConnectionSessionBySocketChannel(@NotNull SocketChannel socketChannel) {
/* 203 */     this.connectionSessionMap.remove(socketChannel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ServerConnectionSession createConnectionSession(@NotNull SocketChannel socketChannel, @NotNull ByteBuffer messageBuffer) throws IOException {
/* 214 */     Connection connection = new Connection(socketChannel);
/* 215 */     connection.setInputStreamByteBuffer(messageBuffer);
/* 216 */     if (!checkMagicAndVersionCompatibility(connection)) {
/* 217 */       connection.close();
/* 218 */       return null;
/*     */     } 
/*     */     
/* 221 */     return new ServerConnectionSession(connection, this);
/*     */   }
/*     */   
/*     */   private boolean checkMagicAndVersionCompatibility(@NotNull Connection connection) throws IOException {
/* 225 */     logger.log(Level.FINE, MessagesMessages.WSTCP_1082_INCOME_MSG_VERSION_CHECK_ENTER());
/*     */     
/* 227 */     connection.setDirectMode(true);
/* 228 */     InputStream inputStream = connection.openInputStream();
/*     */     
/* 230 */     byte[] magicBuf = new byte["vnd.sun.ws.tcp".length()];
/* 231 */     DataInOutUtils.readFully(inputStream, magicBuf);
/* 232 */     String magic = new String(magicBuf, "US-ASCII");
/* 233 */     if (!"vnd.sun.ws.tcp".equals(magic)) {
/* 234 */       logger.log(Level.WARNING, MessagesMessages.WSTCP_0020_WRONG_MAGIC(magic));
/* 235 */       return false;
/*     */     } 
/*     */     
/* 238 */     int[] versionInfo = new int[4];
/*     */     
/* 240 */     DataInOutUtils.readInts4(inputStream, versionInfo, 4);
/*     */     
/* 242 */     Version clientFramingVersion = new Version(versionInfo[0], versionInfo[1]);
/* 243 */     Version clientConnectionManagementVersion = new Version(versionInfo[2], versionInfo[3]);
/*     */     
/* 245 */     VersionController versionController = VersionController.getInstance();
/*     */     
/* 247 */     boolean isSupported = versionController.isVersionSupported(clientFramingVersion, clientConnectionManagementVersion);
/*     */ 
/*     */     
/* 250 */     OutputStream outputStream = connection.openOutputStream();
/*     */     
/* 252 */     Version framingVersion = isSupported ? clientFramingVersion : versionController.getClosestSupportedFramingVersion(clientFramingVersion);
/*     */     
/* 254 */     Version connectionManagementVersion = isSupported ? clientConnectionManagementVersion : versionController.getClosestSupportedConnectionManagementVersion(clientConnectionManagementVersion);
/*     */ 
/*     */     
/* 257 */     DataInOutUtils.writeInts4(outputStream, new int[] { framingVersion.getMajor(), framingVersion.getMinor(), connectionManagementVersion.getMajor(), connectionManagementVersion.getMinor() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     connection.flush();
/*     */ 
/*     */     
/* 265 */     connection.setDirectMode(false);
/*     */     
/* 267 */     logger.log(Level.FINE, MessagesMessages.WSTCP_1083_INCOME_MSG_VERSION_CHECK_RESULT(clientFramingVersion, clientConnectionManagementVersion, framingVersion, connectionManagementVersion, Boolean.valueOf(isSupported)));
/* 268 */     return isSupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyClosed(@NotNull SocketChannel socketChannel) {
/* 276 */     if (this.connectionCache != null) {
/* 277 */       this.connectionCache.close((Connection)getConnectionSession(socketChannel));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySessionClose(@NotNull ConnectionSession connectionSession) {
/* 287 */     removeConnectionSessionBySocketChannel(connectionSession.getConnection().getSocketChannel());
/*     */   }
/*     */   
/*     */   private synchronized void setupInboundConnectionCache() {
/* 291 */     if (this.connectionCache == null) {
/* 292 */       ConnectionManagementSettings settings = ConnectionManagementSettings.getSettingsHolder().getServerSettings();
/*     */ 
/*     */       
/* 295 */       int highWatermark = settings.getHighWatermark();
/* 296 */       int numberToReclaim = settings.getNumberToReclaim();
/*     */       
/* 298 */       this.connectionCache = ConnectionCacheFactory.makeBlockingInboundConnectionCache("SOAP/TCP server side cache", highWatermark, numberToReclaim, logger);
/*     */ 
/*     */       
/* 301 */       if (logger.isLoggable(Level.FINE)) {
/* 302 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1084_INCOME_MSG_SERVER_SIDE_CONNECTION_CACHE(Integer.valueOf(highWatermark), Integer.valueOf(numberToReclaim)));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelContext createFakeChannelContext(int channelId, @NotNull ConnectionSession connectionSession) {
/* 314 */     return new ChannelContext(connectionSession, new ChannelSettings(Collections.emptyList(), Collections.emptyList(), channelId, null, null));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\IncomeMessageProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */