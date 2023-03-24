/*     */ package com.sun.xml.ws.transport.tcp.grizzly;
/*     */ 
/*     */ import com.sun.enterprise.web.portunif.ProtocolFinder;
/*     */ import com.sun.enterprise.web.portunif.util.ProtocolInfo;
/*     */ import com.sun.istack.NotNull;
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
/*     */ public final class WSTCPProtocolFinder
/*     */   implements ProtocolFinder
/*     */ {
/*     */   public void find(@NotNull ProtocolInfo protocolInfo) throws IOException {
/*  77 */     SelectionKey key = protocolInfo.key;
/*  78 */     SocketChannel socketChannel = (SocketChannel)key.channel();
/*  79 */     ByteBuffer byteBuffer = protocolInfo.byteBuffer;
/*     */     
/*  81 */     int loop = 0;
/*  82 */     int count = -1;
/*     */     
/*  84 */     if (protocolInfo.bytesRead == 0) {
/*     */       
/*  86 */       try { while (socketChannel.isOpen() && (count = socketChannel.read(byteBuffer)) > -1) {
/*     */ 
/*     */           
/*  89 */           if (count == 0) {
/*  90 */             loop++;
/*  91 */             if (loop > 2)
/*     */               break;  continue;
/*     */           } 
/*  94 */           if (count > 0) {
/*  95 */             protocolInfo.bytesRead += count;
/*     */           }
/*     */         }  }
/*  98 */       catch (IOException ex) {  }
/*     */       finally
/* 100 */       { if (count == -1) {
/*     */           return;
/*     */         } }
/*     */     
/*     */     }
/*     */     
/* 106 */     int curPosition = byteBuffer.position();
/* 107 */     int curLimit = byteBuffer.limit();
/*     */ 
/*     */     
/* 110 */     if (curPosition < "vnd.sun.ws.tcp".length()) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     byteBuffer.flip();
/*     */ 
/*     */ 
/*     */     
/* 118 */     try { byte[] protocolBytes = new byte["vnd.sun.ws.tcp".length()];
/* 119 */       byteBuffer.get(protocolBytes);
/* 120 */       String incomeProtocolId = new String(protocolBytes);
/* 121 */       if ("vnd.sun.ws.tcp".equals(incomeProtocolId)) {
/* 122 */         protocolInfo.protocol = "vnd.sun.ws.tcp";
/* 123 */         protocolInfo.byteBuffer = byteBuffer;
/* 124 */         protocolInfo.socketChannel = (SocketChannel)key.channel();
/*     */         
/* 126 */         protocolInfo.isSecure = false;
/*     */       }  }
/* 128 */     catch (BufferUnderflowException bue) {  }
/*     */     finally
/* 130 */     { byteBuffer.limit(curLimit);
/* 131 */       byteBuffer.position(curPosition); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\grizzly\WSTCPProtocolFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */