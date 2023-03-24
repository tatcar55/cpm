/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeNS
/*     */   implements Cloneable, Comparable
/*     */ {
/*     */   private String uri;
/*     */   private String prefix;
/*     */   private boolean written = false;
/*  63 */   byte[] utf8Data = null;
/*  64 */   int code = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUri() {
/*  70 */     return this.uri;
/*     */   }
/*     */   
/*     */   public void setUri(String uri) {
/*  74 */     this.uri = uri;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/*  78 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) {
/*  82 */     this.prefix = prefix;
/*     */   }
/*     */   
/*     */   public boolean isWritten() {
/*  86 */     return this.written;
/*     */   }
/*     */   
/*     */   public void setWritten(boolean written) {
/*  90 */     this.written = written;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  94 */     AttributeNS attrNS = new AttributeNS();
/*  95 */     attrNS.setPrefix(this.prefix);
/*  96 */     attrNS.setUri(this.uri);
/*  97 */     return attrNS;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 101 */     if (!(obj instanceof AttributeNS)) {
/* 102 */       return false;
/*     */     }
/* 104 */     AttributeNS attrNS = (AttributeNS)obj;
/* 105 */     if (this.uri == null || this.prefix == null) {
/* 106 */       return false;
/*     */     }
/* 108 */     if (this.prefix.equals(attrNS.getPrefix()) && this.uri.equals(attrNS.getUri())) {
/* 109 */       return true;
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 115 */     if (this.code == 0) {
/* 116 */       if (this.uri != null) {
/* 117 */         this.code = this.uri.hashCode();
/*     */       }
/* 119 */       if (this.prefix != null) {
/* 120 */         this.code += this.prefix.hashCode();
/*     */       }
/*     */     } 
/* 123 */     return this.code;
/*     */   }
/*     */   
/*     */   public byte[] getUTF8Data(ByteArrayOutputStream tmpBuffer) {
/* 127 */     if (this.utf8Data == null) {
/*     */       try {
/* 129 */         BaseCanonicalizer.outputAttrToWriter("xmlns", this.prefix, this.uri, tmpBuffer);
/* 130 */         this.utf8Data = tmpBuffer.toByteArray();
/* 131 */       } catch (Exception ex) {
/* 132 */         this.utf8Data = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 137 */     return this.utf8Data;
/*     */   }
/*     */   
/*     */   public int compareTo(Object cmp) {
/* 141 */     return sortNamespaces(cmp, this);
/*     */   }
/*     */   
/*     */   protected int sortNamespaces(Object object, Object object0) {
/* 145 */     AttributeNS attr = (AttributeNS)object;
/* 146 */     AttributeNS attr0 = (AttributeNS)object0;
/*     */     
/* 148 */     String lN = attr.getPrefix();
/* 149 */     String lN0 = attr0.getPrefix();
/* 150 */     return lN.compareTo(lN0);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 154 */     this.utf8Data = null;
/* 155 */     this.prefix = null;
/* 156 */     this.written = false;
/* 157 */     this.uri = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\AttributeNS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */