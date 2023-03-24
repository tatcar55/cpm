/*     */ package com.sun.xml.ws.api.model;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParameterBinding
/*     */ {
/*  62 */   public static final ParameterBinding BODY = new ParameterBinding(Kind.BODY, null);
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final ParameterBinding HEADER = new ParameterBinding(Kind.HEADER, null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final ParameterBinding UNBOUND = new ParameterBinding(Kind.UNBOUND, null);
/*     */ 
/*     */   
/*     */   public final Kind kind;
/*     */ 
/*     */   
/*     */   private String mimeType;
/*     */ 
/*     */ 
/*     */   
/*     */   public static ParameterBinding createAttachment(String mimeType) {
/*  82 */     return new ParameterBinding(Kind.ATTACHMENT, mimeType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Kind
/*     */   {
/*  89 */     BODY, HEADER, UNBOUND, ATTACHMENT;
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
/*     */   private ParameterBinding(Kind kind, String mimeType) {
/* 105 */     this.kind = kind;
/* 106 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     return this.kind.toString();
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
/*     */   public String getMimeType() {
/* 125 */     if (!isAttachment())
/* 126 */       throw new IllegalStateException(); 
/* 127 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public boolean isBody() {
/* 131 */     return (this == BODY);
/*     */   }
/*     */   
/*     */   public boolean isHeader() {
/* 135 */     return (this == HEADER);
/*     */   }
/*     */   
/*     */   public boolean isUnbound() {
/* 139 */     return (this == UNBOUND);
/*     */   }
/*     */   
/*     */   public boolean isAttachment() {
/* 143 */     return (this.kind == Kind.ATTACHMENT);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\ParameterBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */