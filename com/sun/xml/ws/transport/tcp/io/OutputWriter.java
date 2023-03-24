/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.util.DumpUtils;
/*     */ import com.sun.xml.ws.transport.tcp.util.SelectorFactory;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.SocketChannel;
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
/*     */ 
/*     */ 
/*     */ public final class OutputWriter
/*     */ {
/*  63 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.dump");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void flushChannel(SocketChannel socketChannel, ByteBuffer bb) throws IOException {
/*  72 */     if (logger.isLoggable(Level.FINEST)) {
/*  73 */       Socket socket = socketChannel.socket();
/*  74 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1070_OUTPUT_WRITER_DUMP(socket.getInetAddress().getHostAddress(), Integer.valueOf(socketChannel.socket().getPort())));
/*  75 */       logger.log(Level.FINEST, DumpUtils.dumpBytes(bb));
/*     */     } 
/*     */     
/*  78 */     SelectionKey key = null;
/*  79 */     Selector writeSelector = null;
/*  80 */     int attempts = 0;
/*     */     try {
/*  82 */       while (bb.hasRemaining()) {
/*  83 */         int len = socketChannel.write(bb);
/*  84 */         attempts++;
/*  85 */         if (len < 0) {
/*  86 */           throw new EOFException();
/*     */         }
/*     */         
/*  89 */         if (len == 0) {
/*  90 */           if (writeSelector == null) {
/*  91 */             writeSelector = SelectorFactory.getSelector();
/*  92 */             if (writeSelector == null) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*  98 */           key = socketChannel.register(writeSelector, 4);
/*     */           
/* 100 */           if (writeSelector.select(30000L) == 0) {
/* 101 */             if (attempts > 2) {
/* 102 */               Socket socket = socketChannel.socket();
/* 103 */               throw new IOException(MessagesMessages.WSTCP_0019_PEER_DISCONNECTED(socket.getInetAddress().getHostAddress(), Integer.valueOf(socketChannel.socket().getPort())));
/*     */             }  continue;
/*     */           } 
/* 106 */           attempts--;
/*     */           continue;
/*     */         } 
/* 109 */         attempts = 0;
/*     */       } 
/*     */     } finally {
/*     */       
/* 113 */       if (key != null) {
/* 114 */         key.cancel();
/* 115 */         key = null;
/*     */       } 
/*     */       
/* 118 */       if (writeSelector != null) {
/*     */         
/* 120 */         writeSelector.selectNow();
/* 121 */         SelectorFactory.returnSelector(writeSelector);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void flushChannel(SocketChannel socketChannel, ByteBuffer[] bb) throws IOException {
/* 132 */     if (logger.isLoggable(Level.FINEST)) {
/* 133 */       Socket socket = socketChannel.socket();
/* 134 */       logger.log(Level.FINEST, MessagesMessages.WSTCP_1070_OUTPUT_WRITER_DUMP(socket.getInetAddress().getHostAddress(), Integer.valueOf(socketChannel.socket().getPort())));
/* 135 */       logger.log(Level.FINEST, DumpUtils.dumpBytes(bb));
/*     */     } 
/* 137 */     SelectionKey key = null;
/* 138 */     Selector writeSelector = null;
/* 139 */     int attempts = 0;
/*     */     try {
/* 141 */       while (hasRemaining(bb)) {
/* 142 */         long len = socketChannel.write(bb);
/* 143 */         attempts++;
/* 144 */         if (len < 0L) {
/* 145 */           throw new EOFException();
/*     */         }
/*     */         
/* 148 */         if (len == 0L) {
/* 149 */           if (writeSelector == null) {
/* 150 */             writeSelector = SelectorFactory.getSelector();
/* 151 */             if (writeSelector == null) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 157 */           key = socketChannel.register(writeSelector, 4);
/*     */           
/* 159 */           if (writeSelector.select(30000L) == 0) {
/* 160 */             if (attempts > 2) {
/* 161 */               Socket socket = socketChannel.socket();
/* 162 */               throw new IOException(MessagesMessages.WSTCP_0019_PEER_DISCONNECTED(socket.getInetAddress().getHostAddress(), Integer.valueOf(socketChannel.socket().getPort())));
/*     */             }  continue;
/*     */           } 
/* 165 */           attempts--;
/*     */           continue;
/*     */         } 
/* 168 */         attempts = 0;
/*     */       } 
/*     */     } finally {
/*     */       
/* 172 */       if (key != null) {
/* 173 */         key.cancel();
/* 174 */         key = null;
/*     */       } 
/*     */       
/* 177 */       if (writeSelector != null) {
/*     */         
/* 179 */         writeSelector.selectNow();
/* 180 */         SelectorFactory.returnSelector(writeSelector);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean hasRemaining(ByteBuffer[] bb) {
/* 186 */     for (int i = bb.length - 1; i >= 0; i--) {
/* 187 */       if (bb[i].hasRemaining()) {
/* 188 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 192 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\OutputWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */