/*     */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContentDisposition
/*     */ {
/*     */   private String disposition;
/*     */   private ParameterList list;
/*     */   
/*     */   public ContentDisposition() {}
/*     */   
/*     */   public ContentDisposition(String disposition, ParameterList list) {
/*  77 */     this.disposition = disposition;
/*  78 */     this.list = list;
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
/*     */   public ContentDisposition(String s) throws ParseException {
/*  91 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */     
/*  95 */     HeaderTokenizer.Token tk = h.next();
/*  96 */     if (tk.getType() != -1)
/*  97 */       throw new ParseException(); 
/*  98 */     this.disposition = tk.getValue();
/*     */ 
/*     */     
/* 101 */     String rem = h.getRemainder();
/* 102 */     if (rem != null) {
/* 103 */       this.list = new ParameterList(rem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisposition() {
/* 112 */     return this.disposition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String name) {
/* 122 */     if (this.list == null) {
/* 123 */       return null;
/*     */     }
/* 125 */     return this.list.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterList getParameterList() {
/* 136 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisposition(String disposition) {
/* 145 */     this.disposition = disposition;
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
/*     */   public void setParameter(String name, String value) {
/* 157 */     if (this.list == null) {
/* 158 */       this.list = new ParameterList();
/*     */     }
/* 160 */     this.list.set(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameterList(ParameterList list) {
/* 169 */     this.list = list;
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
/*     */   public String toString() {
/* 181 */     if (this.disposition == null) {
/* 182 */       return null;
/*     */     }
/* 184 */     if (this.list == null) {
/* 185 */       return this.disposition;
/*     */     }
/* 187 */     StringBuffer sb = new StringBuffer(this.disposition);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     sb.append(this.list.toString(sb.length() + 21));
/* 193 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\ContentDisposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */