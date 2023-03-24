/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TCPSettings
/*     */ {
/*  47 */   private static final TCPSettings instance = new TCPSettings();
/*     */   
/*     */   private static final String ENCODING_MODE_PROPERTY = "com.sun.xml.ws.transport.tcp.encodingMode";
/*     */   
/*     */   private static final String OUTPUT_BUFFER_GROWING_PROPERTY = "com.sun.xml.ws.transport.tcp.output.bufferGrow";
/*     */   private static final String OUTPUT_BUFFER_GROWING_LIMIT_PROPERTY = "com.sun.xml.ws.transport.tcp.output.bufferGrowLimit";
/*     */   private EncodingMode encodingMode;
/*     */   private boolean isOutputBufferGrow;
/*     */   private int outputBufferGrowLimit;
/*     */   
/*     */   public enum EncodingMode
/*     */   {
/*  59 */     XML,
/*  60 */     FI_STATELESS,
/*  61 */     FI_STATEFUL,
/*  62 */     FI_ALL;
/*     */   }
/*     */   
/*     */   private TCPSettings() {
/*  66 */     gatherSettings();
/*     */   }
/*     */   
/*     */   public static TCPSettings getInstance() {
/*  70 */     return instance;
/*     */   }
/*     */   
/*     */   public EncodingMode getEncodingMode() {
/*  74 */     return this.encodingMode;
/*     */   }
/*     */   
/*     */   public boolean isOutputBufferGrow() {
/*  78 */     return this.isOutputBufferGrow;
/*     */   }
/*     */   
/*     */   public int getOutputBufferGrowLimit() {
/*  82 */     return this.outputBufferGrowLimit;
/*     */   }
/*     */   
/*     */   private void gatherSettings() {
/*  86 */     if (System.getProperty("com.sun.xml.ws.transport.tcp.encodingMode") != null) {
/*  87 */       String encodingModeS = System.getProperty("com.sun.xml.ws.transport.tcp.encodingMode");
/*  88 */       if ("xml".equalsIgnoreCase(encodingModeS)) {
/*  89 */         this.encodingMode = EncodingMode.XML;
/*  90 */       } else if ("FIStateless".equalsIgnoreCase(encodingModeS)) {
/*  91 */         this.encodingMode = EncodingMode.FI_STATELESS;
/*     */       } else {
/*  93 */         this.encodingMode = EncodingMode.FI_STATEFUL;
/*     */       } 
/*     */     } else {
/*  96 */       this.encodingMode = EncodingMode.FI_STATEFUL;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     this.isOutputBufferGrow = (System.getProperty("com.sun.xml.ws.transport.tcp.output.bufferGrow") == null || Boolean.getBoolean("com.sun.xml.ws.transport.tcp.output.bufferGrow"));
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.outputBufferGrowLimit = Integer.getInteger("com.sun.xml.ws.transport.tcp.output.bufferGrowLimit", 65536).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\TCPSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */