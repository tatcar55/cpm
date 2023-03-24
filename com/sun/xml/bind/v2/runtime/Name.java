/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Name
/*     */   implements Comparable<Name>
/*     */ {
/*     */   public final String nsUri;
/*     */   public final String localName;
/*     */   public final short nsUriIndex;
/*     */   public final short localNameIndex;
/*     */   public final short qNameIndex;
/*     */   public final boolean isAttribute;
/*     */   
/*     */   Name(int qNameIndex, int nsUriIndex, String nsUri, int localIndex, String localName, boolean isAttribute) {
/*  85 */     this.qNameIndex = (short)qNameIndex;
/*  86 */     this.nsUri = nsUri;
/*  87 */     this.localName = localName;
/*  88 */     this.nsUriIndex = (short)nsUriIndex;
/*  89 */     this.localNameIndex = (short)localIndex;
/*  90 */     this.isAttribute = isAttribute;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  94 */     return '{' + this.nsUri + '}' + this.localName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName toQName() {
/* 101 */     return new QName(this.nsUri, this.localName);
/*     */   }
/*     */   
/*     */   public boolean equals(String nsUri, String localName) {
/* 105 */     return (localName.equals(this.localName) && nsUri.equals(this.nsUri));
/*     */   }
/*     */   
/*     */   public int compareTo(Name that) {
/* 109 */     int r = this.nsUri.compareTo(that.nsUri);
/* 110 */     if (r != 0) return r; 
/* 111 */     return this.localName.compareTo(that.localName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\Name.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */