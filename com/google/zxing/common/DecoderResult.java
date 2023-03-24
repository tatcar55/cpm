/*     */ package com.google.zxing.common;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DecoderResult
/*     */ {
/*     */   private final byte[] rawBytes;
/*     */   private final String text;
/*     */   private final List<byte[]> byteSegments;
/*     */   private final String ecLevel;
/*     */   private Integer errorsCorrected;
/*     */   private Integer erasures;
/*     */   private Object other;
/*     */   private final int structuredAppendParity;
/*     */   private final int structuredAppendSequenceNumber;
/*     */   
/*     */   public DecoderResult(byte[] rawBytes, String text, List<byte[]> byteSegments, String ecLevel) {
/*  44 */     this(rawBytes, text, byteSegments, ecLevel, -1, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecoderResult(byte[] rawBytes, String text, List<byte[]> byteSegments, String ecLevel, int saSequence, int saParity) {
/*  53 */     this.rawBytes = rawBytes;
/*  54 */     this.text = text;
/*  55 */     this.byteSegments = byteSegments;
/*  56 */     this.ecLevel = ecLevel;
/*  57 */     this.structuredAppendParity = saParity;
/*  58 */     this.structuredAppendSequenceNumber = saSequence;
/*     */   }
/*     */   
/*     */   public byte[] getRawBytes() {
/*  62 */     return this.rawBytes;
/*     */   }
/*     */   
/*     */   public String getText() {
/*  66 */     return this.text;
/*     */   }
/*     */   
/*     */   public List<byte[]> getByteSegments() {
/*  70 */     return this.byteSegments;
/*     */   }
/*     */   
/*     */   public String getECLevel() {
/*  74 */     return this.ecLevel;
/*     */   }
/*     */   
/*     */   public Integer getErrorsCorrected() {
/*  78 */     return this.errorsCorrected;
/*     */   }
/*     */   
/*     */   public void setErrorsCorrected(Integer errorsCorrected) {
/*  82 */     this.errorsCorrected = errorsCorrected;
/*     */   }
/*     */   
/*     */   public Integer getErasures() {
/*  86 */     return this.erasures;
/*     */   }
/*     */   
/*     */   public void setErasures(Integer erasures) {
/*  90 */     this.erasures = erasures;
/*     */   }
/*     */   
/*     */   public Object getOther() {
/*  94 */     return this.other;
/*     */   }
/*     */   
/*     */   public void setOther(Object other) {
/*  98 */     this.other = other;
/*     */   }
/*     */   
/*     */   public boolean hasStructuredAppend() {
/* 102 */     return (this.structuredAppendParity >= 0 && this.structuredAppendSequenceNumber >= 0);
/*     */   }
/*     */   
/*     */   public int getStructuredAppendParity() {
/* 106 */     return this.structuredAppendParity;
/*     */   }
/*     */   
/*     */   public int getStructuredAppendSequenceNumber() {
/* 110 */     return this.structuredAppendSequenceNumber;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\DecoderResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */