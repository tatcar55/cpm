/*     */ package com.sun.xml.ws.transport.tcp.server.tomcat.grizzly10;
/*     */ 
/*     */ import com.sun.enterprise.web.portunif.ProtocolFinder;
/*     */ import com.sun.enterprise.web.portunif.util.ProtocolInfo;
/*     */ import java.io.IOException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.SocketChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRedirectorProtocolFinder
/*     */   implements ProtocolFinder
/*     */ {
/*     */   public void find(ProtocolInfo protocolInfo) {
/*  79 */     SelectionKey key = protocolInfo.key;
/*  80 */     SocketChannel socketChannel = (SocketChannel)key.channel();
/*  81 */     ByteBuffer byteBuffer = protocolInfo.byteBuffer;
/*     */     
/*  83 */     int loop = 0;
/*  84 */     int count = -1;
/*     */     
/*  86 */     if (protocolInfo.bytesRead == 0) {
/*     */       try {
/*  88 */         while (socketChannel.isOpen() && (count = socketChannel.read(byteBuffer)) > -1)
/*     */         {
/*     */ 
/*     */           
/*  92 */           loop++;
/*  93 */           if (count == 0 && loop > 2) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*  99 */       catch (IOException ex) {
/*     */       
/*     */       } finally {
/* 102 */         protocolInfo.bytesRead = count;
/* 103 */         if (count == -1) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 109 */     int curPosition = byteBuffer.position();
/* 110 */     int curLimit = byteBuffer.limit();
/*     */ 
/*     */     
/* 113 */     if (byteBuffer.position() == 0) {
/* 114 */       protocolInfo.byteBuffer = byteBuffer;
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     byteBuffer.position(0);
/* 119 */     byteBuffer.limit(curPosition);
/* 120 */     int state = 0;
/* 121 */     int start = 0;
/* 122 */     int end = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     try { while (byteBuffer.hasRemaining()) {
/* 129 */         byte c = byteBuffer.get();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 135 */         switch (state) {
/*     */           case 0:
/* 137 */             if (c == 32) {
/* 138 */               state = 1;
/* 139 */               start = byteBuffer.position();
/*     */             } 
/*     */             continue;
/*     */           case 1:
/* 143 */             if (c == 32) {
/* 144 */               state = 2;
/* 145 */               end = byteBuffer.position() - 1;
/* 146 */               byteBuffer.position(start);
/* 147 */               byte[] requestURI = new byte[end - start];
/* 148 */               byteBuffer.get(requestURI);
/* 149 */               protocolInfo.requestURI = new String(requestURI);
/*     */             } 
/*     */             continue;
/*     */           case 2:
/* 153 */             if (c == 47) {
/* 154 */               protocolInfo.protocol = protocolInfo.isSecure ? "redirect-https" : "redirect-http";
/*     */               
/* 156 */               protocolInfo.byteBuffer = byteBuffer;
/* 157 */               protocolInfo.socketChannel = (SocketChannel)key.channel();
/*     */               return;
/*     */             } 
/*     */             continue;
/*     */         } 
/*     */         
/* 163 */         throw new IllegalArgumentException("Unexpected state");
/*     */       }
/*     */        }
/* 166 */     catch (BufferUnderflowException bue) {  }
/*     */     finally
/* 168 */     { byteBuffer.limit(curLimit);
/* 169 */       byteBuffer.position(curPosition);
/* 170 */       protocolInfo.bytesRead = byteBuffer.position(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\tomcat\grizzly10\HttpRedirectorProtocolFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */