/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.output.Pcdata;
/*     */ import com.sun.xml.bind.v2.runtime.output.UTF8XmlOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IntArrayData
/*     */   extends Pcdata
/*     */ {
/*     */   private int[] data;
/*     */   private int start;
/*     */   private int len;
/*     */   private StringBuilder literal;
/*     */   
/*     */   public IntArrayData(int[] data, int start, int len) {
/*  75 */     set(data, start, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int[] data, int start, int len) {
/*  91 */     this.data = data;
/*  92 */     this.start = start;
/*  93 */     this.len = len;
/*  94 */     this.literal = null;
/*     */   }
/*     */   
/*     */   public int length() {
/*  98 */     return getLiteral().length();
/*     */   }
/*     */   
/*     */   public char charAt(int index) {
/* 102 */     return getLiteral().charAt(index);
/*     */   }
/*     */   
/*     */   public CharSequence subSequence(int start, int end) {
/* 106 */     return getLiteral().subSequence(start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder getLiteral() {
/* 113 */     if (this.literal != null) return this.literal;
/*     */     
/* 115 */     this.literal = new StringBuilder();
/* 116 */     int p = this.start;
/* 117 */     for (int i = this.len; i > 0; i--) {
/* 118 */       if (this.literal.length() > 0) this.literal.append(' '); 
/* 119 */       this.literal.append(this.data[p++]);
/*     */     } 
/*     */     
/* 122 */     return this.literal;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 126 */     return this.literal.toString();
/*     */   }
/*     */   
/*     */   public void writeTo(UTF8XmlOutput output) throws IOException {
/* 130 */     int p = this.start;
/* 131 */     for (int i = this.len; i > 0; i--) {
/* 132 */       if (i != this.len)
/* 133 */         output.write(32); 
/* 134 */       output.text(this.data[p++]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\IntArrayData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */