/*     */ package com.sun.xml.ws.transport.tcp.server.tomcat.grizzly10;
/*     */ 
/*     */ import com.sun.enterprise.web.connector.grizzly.ByteBufferInputStream;
/*     */ import com.sun.enterprise.web.portunif.ProtocolHandler;
/*     */ import com.sun.enterprise.web.portunif.util.ProtocolInfo;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRedirectorProtocolHandler
/*     */   implements ProtocolHandler
/*     */ {
/*     */   private static final int DEFAULT_HTTP_HEADER_BUFFER_SIZE = 49152;
/*  62 */   protected String[] protocols = new String[] { "redirect-https", "redirect-http" };
/*     */ 
/*     */   
/*     */   private HttpRedirector redirector;
/*     */ 
/*     */   
/*     */   private int redirectPort;
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRedirectorProtocolHandler(int redirectPort) {
/*  73 */     this.redirectPort = redirectPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(ProtocolInfo protocolInfo) throws IOException {
/*  84 */     if (this.redirector == null) {
/*  85 */       this.redirector = new HttpRedirector(this.redirectPort);
/*     */     }
/*     */     
/*  88 */     if (protocolInfo.protocol.equalsIgnoreCase("https")) {
/*  89 */       this.redirector.redirectSSL(protocolInfo);
/*     */     } else {
/*  91 */       this.redirector.redirect(protocolInfo);
/*     */     } 
/*  93 */     protocolInfo.keepAlive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     ByteBuffer tmpBuffer = protocolInfo.byteBuffer;
/* 101 */     tmpBuffer.clear();
/* 102 */     ByteBufferInputStream is = new ByteBufferInputStream(tmpBuffer);
/*     */     try {
/* 104 */       is.setReadTimeout(2);
/* 105 */       is.setSelectionKey(protocolInfo.key);
/* 106 */       int count = 0;
/* 107 */       while (tmpBuffer.hasRemaining() && count < 49152) {
/* 108 */         tmpBuffer.position(tmpBuffer.limit());
/* 109 */         int readBytes = is.read();
/* 110 */         if (readBytes == -1)
/* 111 */           break;  count += readBytes;
/*     */       } 
/* 113 */     } catch (IOException e) {
/*     */     
/*     */     } finally {
/* 116 */       is.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getProtocols() {
/* 127 */     return this.protocols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean expireKey(SelectionKey key) {
/* 137 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\tomcat\grizzly10\HttpRedirectorProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */