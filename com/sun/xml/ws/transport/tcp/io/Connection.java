/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.pool.ByteBufferStreamPool;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.ByteBufferFactory;
/*     */ import com.sun.xml.ws.transport.tcp.util.FrameType;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
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
/*     */ 
/*     */ public final class Connection
/*     */ {
/*  64 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp");
/*     */ 
/*     */   
/*  67 */   private static ByteBufferStreamPool<FramedMessageInputStream> byteBufferInputStreamPool = new ByteBufferStreamPool(FramedMessageInputStream.class);
/*     */   
/*  69 */   private static ByteBufferStreamPool<FramedMessageOutputStream> byteBufferOutputStreamPool = new ByteBufferStreamPool(FramedMessageOutputStream.class);
/*     */   
/*     */   private SocketChannel socketChannel;
/*     */   
/*     */   private WeakReference<BufferedMessageInputStream> inputStreamRef;
/*     */   
/*     */   private FramedMessageInputStream inputStream;
/*     */   
/*     */   private FramedMessageOutputStream outputStream;
/*     */   
/*     */   private boolean isDirectMode;
/*     */   
/*     */   private int messageId;
/*     */   
/*     */   private int channelId;
/*     */   private int contentId;
/*     */   
/*     */   public Connection(SocketChannel socketChannel) {
/*  87 */     this.inputStream = (FramedMessageInputStream)byteBufferInputStreamPool.take();
/*  88 */     this.outputStream = (FramedMessageOutputStream)byteBufferOutputStreamPool.take();
/*  89 */     setSocketChannel(socketChannel);
/*     */   }
/*     */   
/*     */   public SocketChannel getSocketChannel() {
/*  93 */     return this.socketChannel;
/*     */   }
/*     */   
/*     */   public void setSocketChannel(SocketChannel socketChannel) {
/*  97 */     this.socketChannel = socketChannel;
/*  98 */     this.inputStream.setSocketChannel(socketChannel);
/*  99 */     this.outputStream.setSocketChannel(socketChannel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareForReading() throws IOException {
/* 106 */     if (this.inputStreamRef != null) {
/* 107 */       BufferedMessageInputStream is = this.inputStreamRef.get();
/*     */       
/* 109 */       if (this.inputStream.isMessageInProcess() && is != null && !is.isClosed()) {
/* 110 */         is.bufferMessage();
/* 111 */         logger.log(Level.FINEST, MessagesMessages.WSTCP_1050_CONNECTION_BUFFERING_IS(Integer.valueOf(is.getBufferedSize())));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (this.inputStream.isMessageInProcess()) {
/* 117 */       this.inputStream.skipToEndOfMessage();
/*     */     }
/*     */     
/* 120 */     this.inputStream.reset();
/* 121 */     this.outputStream.reset();
/*     */     
/* 123 */     this.inputStream.forceHeaderRead();
/*     */     
/* 125 */     this.channelId = this.inputStream.getChannelId();
/* 126 */     this.messageId = this.inputStream.getMessageId();
/*     */     
/* 128 */     if (FrameType.isFrameContainsParams(this.messageId)) {
/* 129 */       this.contentId = this.inputStream.getContentId();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream openInputStream() throws IOException {
/* 138 */     BufferedMessageInputStream is = new BufferedMessageInputStream(this.inputStream);
/* 139 */     this.inputStreamRef = new WeakReference<BufferedMessageInputStream>(is);
/* 140 */     return is;
/*     */   }
/*     */   
/*     */   public OutputStream openOutputStream() throws IOException {
/* 144 */     this.outputStream.setChannelId(this.channelId);
/* 145 */     this.outputStream.setMessageId(this.messageId);
/* 146 */     this.outputStream.setContentId(this.contentId);
/*     */     
/* 148 */     this.outputStream.buildHeader();
/* 149 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 153 */     this.outputStream.flushLast();
/*     */   }
/*     */   
/*     */   public boolean isDirectMode() {
/* 157 */     return this.isDirectMode;
/*     */   }
/*     */   
/*     */   public void setDirectMode(boolean isDirectMode) {
/* 161 */     this.isDirectMode = isDirectMode;
/* 162 */     this.inputStream.setDirectMode(isDirectMode);
/* 163 */     this.outputStream.setDirectMode(isDirectMode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChannelId() {
/* 169 */     return this.channelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChannelId(int channelId) {
/* 176 */     this.channelId = channelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageId() {
/* 183 */     return this.messageId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessageId(int messageId) {
/* 190 */     this.messageId = messageId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContentId() {
/* 197 */     return this.contentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentId(int contentId) {
/* 204 */     this.contentId = contentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, String> getContentProperties() {
/* 211 */     return this.inputStream.getContentProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentProperty(int key, String value) {
/* 218 */     this.outputStream.setContentProperty(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInputStreamByteBuffer(ByteBuffer messageBuffer) {
/* 226 */     this.inputStream.setByteBuffer(messageBuffer);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 230 */     if (this.inputStream != null) {
/* 231 */       byteBufferInputStreamPool.release(this.inputStream);
/* 232 */       this.inputStream = null;
/*     */     } 
/*     */     
/* 235 */     if (this.outputStream != null) {
/* 236 */       byteBufferOutputStreamPool.release(this.outputStream);
/* 237 */       this.outputStream = null;
/*     */     } 
/*     */     
/* 240 */     this.socketChannel.close();
/*     */   }
/*     */   
/*     */   public static Connection create(String host, int port) throws IOException {
/* 244 */     if (logger.isLoggable(Level.FINE)) {
/* 245 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1051_CONNECTION_OPEN_TCP_SOCKET(host, Integer.valueOf(port)));
/*     */     }
/* 247 */     SocketChannel socketChannel = SocketChannel.open();
/* 248 */     Socket socket = socketChannel.socket();
/* 249 */     socket.connect(new InetSocketAddress(host, port));
/* 250 */     socketChannel.configureBlocking(false);
/*     */     
/* 252 */     Connection connection = new Connection(socketChannel);
/*     */     
/* 254 */     ByteBuffer byteBuffer = ByteBufferFactory.allocateView(4096, false);
/* 255 */     byteBuffer.position(0);
/* 256 */     byteBuffer.limit(0);
/*     */     
/* 258 */     connection.setInputStreamByteBuffer(byteBuffer);
/*     */     
/* 260 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 265 */     close();
/* 266 */     super.finalize();
/*     */   }
/*     */   
/*     */   public String getHost() {
/* 270 */     return getHost(this.socketChannel);
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 274 */     return getPort(this.socketChannel);
/*     */   }
/*     */   
/*     */   public String getLocalHost() {
/* 278 */     return getLocalHost(this.socketChannel);
/*     */   }
/*     */   
/*     */   public int getLocalPort() {
/* 282 */     return getLocalPort(this.socketChannel);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 287 */     return "host: " + getHost() + " port: " + getPort();
/*     */   }
/*     */   
/*     */   public static String getHost(SocketChannel socketChannel) {
/* 291 */     return socketChannel.socket().getInetAddress().getHostAddress();
/*     */   }
/*     */   
/*     */   public static int getPort(SocketChannel socketChannel) {
/* 295 */     return socketChannel.socket().getPort();
/*     */   }
/*     */   
/*     */   public static String getLocalHost(SocketChannel socketChannel) {
/* 299 */     return socketChannel.socket().getLocalAddress().getHostAddress();
/*     */   }
/*     */   
/*     */   public static int getLocalPort(SocketChannel socketChannel) {
/* 303 */     return socketChannel.socket().getLocalPort();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\Connection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */