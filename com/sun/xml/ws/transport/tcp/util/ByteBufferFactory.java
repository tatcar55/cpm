/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteBufferFactory
/*     */ {
/*  62 */   public static int defaultCapacity = 9000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static int capacity = 4000000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteBuffer byteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ByteBuffer allocateView(int size, boolean direct) {
/*  90 */     if (byteBuffer == null || byteBuffer.capacity() - byteBuffer.limit() < size)
/*     */     {
/*  92 */       if (direct) {
/*  93 */         byteBuffer = ByteBuffer.allocateDirect(capacity);
/*     */       } else {
/*  95 */         byteBuffer = ByteBuffer.allocate(capacity);
/*     */       } 
/*     */     }
/*  98 */     byteBuffer.limit(byteBuffer.position() + size);
/*  99 */     ByteBuffer view = byteBuffer.slice();
/* 100 */     byteBuffer.position(byteBuffer.limit());
/*     */     
/* 102 */     return view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ByteBuffer allocateView(boolean direct) {
/* 110 */     return allocateView(defaultCapacity, direct);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ByteBufferFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */