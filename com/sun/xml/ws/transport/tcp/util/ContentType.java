/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ContentType
/*     */ {
/*     */   private String mimeType;
/*  51 */   private final Map<String, String> parameters = new HashMap<String, String>(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/*  57 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public Map<String, String> getParameters() {
/*  61 */     return this.parameters;
/*     */   }
/*     */   
/*     */   public void parse(String contentType) throws WSTCPException {
/*  65 */     this.parameters.clear();
/*     */     
/*  67 */     int mimeDelim = contentType.indexOf(';');
/*  68 */     if (mimeDelim == -1) {
/*  69 */       this.mimeType = contentType.trim().toLowerCase();
/*     */       return;
/*     */     } 
/*  72 */     this.mimeType = contentType.substring(0, mimeDelim).trim().toLowerCase();
/*     */ 
/*     */     
/*  75 */     int delim = mimeDelim + 1;
/*     */     
/*  77 */     while (delim < contentType.length()) {
/*  78 */       int nextDelim = contentType.indexOf(';', delim);
/*  79 */       if (nextDelim == -1) nextDelim = contentType.length();
/*     */       
/*  81 */       int eqDelim = contentType.indexOf('=', delim);
/*  82 */       if (eqDelim == -1) eqDelim = nextDelim;
/*     */       
/*  84 */       String key = contentType.substring(delim, eqDelim).trim();
/*  85 */       String value = contentType.substring(eqDelim + 1, nextDelim).trim();
/*  86 */       this.parameters.put(key, value);
/*     */       
/*  88 */       delim = nextDelim + 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  94 */     if (o != null && o instanceof ContentType) {
/*  95 */       ContentType ctToCompare = (ContentType)o;
/*  96 */       return (this.mimeType.equals(ctToCompare.mimeType) && ctToCompare.parameters.equals(this.parameters));
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return this.mimeType.hashCode() ^ this.parameters.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ContentType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */