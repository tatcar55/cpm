/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private String primaryType;
/*     */   private String subType;
/*     */   private ParameterList list;
/*     */   
/*     */   public ContentType(String s) throws WebServiceException {
/*  74 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */     
/*  78 */     HeaderTokenizer.Token tk = h.next();
/*  79 */     if (tk.getType() != -1)
/*  80 */       throw new WebServiceException(); 
/*  81 */     this.primaryType = tk.getValue();
/*     */ 
/*     */     
/*  84 */     tk = h.next();
/*  85 */     if ((char)tk.getType() != '/') {
/*  86 */       throw new WebServiceException();
/*     */     }
/*     */     
/*  89 */     tk = h.next();
/*  90 */     if (tk.getType() != -1)
/*  91 */       throw new WebServiceException(); 
/*  92 */     this.subType = tk.getValue();
/*     */ 
/*     */     
/*  95 */     String rem = h.getRemainder();
/*  96 */     if (rem != null) {
/*  97 */       this.list = new ParameterList(rem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryType() {
/* 106 */     return this.primaryType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubType() {
/* 114 */     return this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseType() {
/* 125 */     return this.primaryType + '/' + this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String name) {
/* 136 */     if (this.list == null) {
/* 137 */       return null;
/*     */     }
/* 139 */     return this.list.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterList getParameterList() {
/* 149 */     return this.list;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\ContentType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */