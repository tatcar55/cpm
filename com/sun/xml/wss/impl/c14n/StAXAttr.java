/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXAttr
/*     */   implements Comparable
/*     */ {
/*  58 */   private String prefix = "";
/*  59 */   private String value = null;
/*  60 */   private String localName = null;
/*  61 */   private String uri = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  67 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) {
/*  71 */     if (prefix == null) {
/*     */       return;
/*     */     }
/*  74 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalName() {
/*  80 */     return this.localName;
/*     */   }
/*     */   
/*     */   public void setLocalName(String localName) {
/*  84 */     this.localName = localName;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  88 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  92 */     this.value = value;
/*     */   }
/*     */   
/*     */   public String getUri() {
/*  96 */     return this.uri;
/*     */   }
/*     */   
/*     */   public void setUri(String uri) {
/* 100 */     if (uri == null) {
/*     */       return;
/*     */     }
/* 103 */     this.uri = uri;
/*     */   }
/*     */   
/*     */   public int compareTo(Object cmp) {
/* 107 */     return sortAttributes(cmp, this);
/*     */   }
/*     */   
/*     */   protected int sortAttributes(Object object, Object object0) {
/* 111 */     StAXAttr attr = (StAXAttr)object;
/* 112 */     StAXAttr attr0 = (StAXAttr)object0;
/* 113 */     String uri = attr.getUri();
/* 114 */     String uri0 = attr0.getUri();
/* 115 */     int result = uri.compareTo(uri0);
/* 116 */     if (result == 0) {
/* 117 */       String lN = attr.getLocalName();
/* 118 */       String lN0 = attr0.getLocalName();
/* 119 */       result = lN.compareTo(lN0);
/*     */     } 
/* 121 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\StAXAttr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */