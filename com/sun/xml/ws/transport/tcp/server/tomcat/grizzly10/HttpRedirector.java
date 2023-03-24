/*     */ package com.sun.xml.ws.transport.tcp.server.tomcat.grizzly10;
/*     */ 
/*     */ import com.sun.enterprise.web.connector.grizzly.OutputWriter;
/*     */ import com.sun.enterprise.web.connector.grizzly.ssl.SSLOutputWriter;
/*     */ import com.sun.enterprise.web.connector.grizzly.ssl.SSLSelectorThread;
/*     */ import com.sun.enterprise.web.portunif.util.ProtocolInfo;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.tomcat.util.buf.Ascii;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRedirector
/*     */ {
/*     */   private static final String NEWLINE = "\r\n";
/*     */   private final int redirectPort;
/*  88 */   private static String ipAddress = "127.0.0.1";
/*     */   private static final String headers = "\r\nConnection:close\r\nCache-control: private\r\n\r\n";
/*     */   
/*     */   static {
/*     */     try {
/*  93 */       ipAddress = InetAddress.getLocalHost().getHostName();
/*  94 */     } catch (Throwable t) {}
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
/* 110 */   private static final ByteBuffer SC_FOUND = ByteBuffer.wrap("HTTP/1.1 302 Moved Temporarily\r\n".getBytes());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRedirector(int redirectPort) {
/* 119 */     this.redirectPort = redirectPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void redirectSSL(ProtocolInfo protocolInfo) throws IOException {
/* 129 */     String host = replacePort(parseHost(protocolInfo.byteBuffer));
/* 130 */     if (host == null) {
/* 131 */       host = ipAddress + ":" + this.redirectPort;
/*     */     }
/*     */     
/* 134 */     redirectSSL(protocolInfo, protocolInfo.isRequestedTransportSecure ? new String("Location: https://" + host) : new String("Location: http://" + host));
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
/*     */   private final void redirectSSL(ProtocolInfo protocolInfo, String httpHeaders) throws IOException {
/* 150 */     SSLOutputWriter.flushChannel(protocolInfo.socketChannel, SC_FOUND.slice(), protocolInfo.outputBB, protocolInfo.sslEngine);
/*     */ 
/*     */ 
/*     */     
/* 154 */     SSLOutputWriter.flushChannel(protocolInfo.socketChannel, ByteBuffer.wrap((httpHeaders + protocolInfo.requestURI + "\r\nConnection:close\r\nCache-control: private\r\n\r\n").getBytes()), protocolInfo.outputBB, protocolInfo.sslEngine);
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
/*     */   public final void redirect(ProtocolInfo protocolInfo) throws IOException {
/* 169 */     String host = replacePort(parseHost(protocolInfo.byteBuffer));
/* 170 */     if (host == null) {
/* 171 */       host = ipAddress + ":" + protocolInfo.socketChannel.socket().getLocalPort();
/*     */     }
/*     */     
/* 174 */     redirect(protocolInfo, protocolInfo.isRequestedTransportSecure ? new String("Location: https://" + host) : new String("Location: http://" + host));
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
/*     */   private final void redirect(ProtocolInfo protocolInfo, String httpHeaders) throws IOException {
/* 189 */     OutputWriter.flushChannel(protocolInfo.socketChannel, SC_FOUND.slice());
/* 190 */     OutputWriter.flushChannel(protocolInfo.socketChannel, ByteBuffer.wrap((httpHeaders + protocolInfo.requestURI + "\r\nConnection:close\r\nCache-control: private\r\n\r\n").getBytes()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parseHost(ByteBuffer byteBuffer) {
/* 201 */     boolean isFound = false;
/*     */     
/* 203 */     int curPosition = byteBuffer.position();
/* 204 */     int curLimit = byteBuffer.limit();
/*     */ 
/*     */     
/* 207 */     if (byteBuffer.position() == 0) {
/* 208 */       return ipAddress;
/*     */     }
/* 210 */     byteBuffer.position(0);
/* 211 */     byteBuffer.limit(curPosition);
/*     */ 
/*     */     
/* 214 */     int state = 0;
/* 215 */     int start = 0;
/* 216 */     int end = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 222 */       while (byteBuffer.hasRemaining()) {
/* 223 */         StringBuilder sb; byte c = (byte)Ascii.toLower(byteBuffer.get());
/* 224 */         switch (state) {
/*     */           case 0:
/* 226 */             if (c == 104) {
/* 227 */               state = 1; continue;
/*     */             } 
/* 229 */             state = 0;
/*     */             continue;
/*     */           
/*     */           case 1:
/* 233 */             if (c == 111) {
/* 234 */               state = 2; continue;
/*     */             } 
/* 236 */             state = 0;
/*     */             continue;
/*     */           
/*     */           case 2:
/* 240 */             if (c == 115) {
/* 241 */               state = 3; continue;
/*     */             } 
/* 243 */             state = 0;
/*     */             continue;
/*     */           
/*     */           case 3:
/* 247 */             if (c == 116) {
/* 248 */               state = 4; continue;
/*     */             } 
/* 250 */             state = 0;
/*     */             continue;
/*     */           
/*     */           case 4:
/* 254 */             if (c == 58) {
/* 255 */               state = 5; continue;
/*     */             } 
/* 257 */             state = 0;
/*     */             continue;
/*     */           
/*     */           case 5:
/* 261 */             sb = new StringBuilder();
/* 262 */             while (c != 13 && c != 10) {
/* 263 */               sb.append((char)c);
/* 264 */               c = byteBuffer.get();
/*     */             } 
/* 266 */             return sb.toString().trim();
/*     */         } 
/* 268 */         throw new IllegalArgumentException("Unexpected state");
/*     */       } 
/*     */       
/* 271 */       return null;
/* 272 */     } catch (BufferUnderflowException bue) {
/* 273 */       return null;
/*     */     } finally {
/* 275 */       if (end > 0) {
/* 276 */         byteBuffer.position(start);
/* 277 */         byteBuffer.limit(end);
/*     */       } else {
/* 279 */         byteBuffer.limit(curLimit);
/* 280 */         byteBuffer.position(curPosition);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(Throwable ex) {
/* 290 */     Logger logger = SSLSelectorThread.logger();
/* 291 */     if (logger.isLoggable(Level.WARNING)) {
/* 292 */       logger.log(Level.WARNING, "Redirector", ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private String replacePort(String host) {
/* 297 */     int delim = host.indexOf(':');
/* 298 */     if (delim != -1) {
/* 299 */       return host.substring(0, delim + 1) + this.redirectPort;
/*     */     }
/*     */     
/* 302 */     return host + ':' + this.redirectPort;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\tomcat\grizzly10\HttpRedirector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */