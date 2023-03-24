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
/*     */ public final class ContentType
/*     */ {
/*     */   private String primaryType;
/*     */   private String subType;
/*     */   private ParameterList list;
/*     */   
/*     */   public ContentType() {}
/*     */   
/*     */   public ContentType(String primaryType, String subType, ParameterList list) {
/*  78 */     this.primaryType = primaryType;
/*  79 */     this.subType = subType;
/*  80 */     if (list == null)
/*  81 */       list = new ParameterList(); 
/*  82 */     this.list = list;
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
/*     */   public ContentType(String s) throws ParseException {
/*  94 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */     
/*  98 */     HeaderTokenizer.Token tk = h.next();
/*  99 */     if (tk.getType() != -1)
/* 100 */       throw new ParseException(); 
/* 101 */     this.primaryType = tk.getValue();
/*     */ 
/*     */     
/* 104 */     tk = h.next();
/* 105 */     if ((char)tk.getType() != '/') {
/* 106 */       throw new ParseException();
/*     */     }
/*     */     
/* 109 */     tk = h.next();
/* 110 */     if (tk.getType() != -1)
/* 111 */       throw new ParseException(); 
/* 112 */     this.subType = tk.getValue();
/*     */ 
/*     */     
/* 115 */     String rem = h.getRemainder();
/* 116 */     if (rem != null)
/* 117 */       this.list = new ParameterList(rem); 
/*     */   }
/*     */   
/*     */   public ContentType copy() {
/* 121 */     return new ContentType(this.primaryType, this.subType, this.list.copy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrimaryType() {
/* 129 */     return this.primaryType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSubType() {
/* 137 */     return this.subType;
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
/* 148 */     return this.primaryType + '/' + this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameter(String name) {
/* 157 */     if (this.list == null) {
/* 158 */       return null;
/*     */     }
/* 160 */     return this.list.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterList getParameterList() {
/* 170 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrimaryType(String primaryType) {
/* 178 */     this.primaryType = primaryType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubType(String subType) {
/* 186 */     this.subType = subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(String name, String value) {
/* 197 */     if (this.list == null) {
/* 198 */       this.list = new ParameterList();
/*     */     }
/* 200 */     this.list.set(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameterList(ParameterList list) {
/* 208 */     this.list = list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 219 */     if (this.primaryType == null || this.subType == null) {
/* 220 */       return null;
/*     */     }
/* 222 */     StringBuffer sb = new StringBuffer();
/* 223 */     sb.append(this.primaryType).append('/').append(this.subType);
/* 224 */     if (this.list != null)
/*     */     {
/*     */ 
/*     */       
/* 228 */       sb.append(this.list.toString());
/*     */     }
/* 230 */     return sb.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean match(ContentType cType) {
/* 253 */     if (!this.primaryType.equalsIgnoreCase(cType.getPrimaryType())) {
/* 254 */       return false;
/*     */     }
/* 256 */     String sType = cType.getSubType();
/*     */ 
/*     */     
/* 259 */     if (this.subType.charAt(0) == '*' || sType.charAt(0) == '*') {
/* 260 */       return true;
/*     */     }
/*     */     
/* 263 */     if (!this.subType.equalsIgnoreCase(sType)) {
/* 264 */       return false;
/*     */     }
/* 266 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean match(String s) {
/*     */     try {
/* 287 */       return match(new ContentType(s));
/* 288 */     } catch (ParseException pex) {
/* 289 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\ContentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */