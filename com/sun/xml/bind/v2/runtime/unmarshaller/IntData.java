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
/*     */ public class IntData
/*     */   extends Pcdata
/*     */ {
/*     */   private int data;
/*     */   private int length;
/*     */   
/*     */   public void reset(int i) {
/*  68 */     this.data = i;
/*  69 */     if (i == Integer.MIN_VALUE) {
/*  70 */       this.length = 11;
/*     */     } else {
/*  72 */       this.length = (i < 0) ? (stringSizeOfInt(-i) + 1) : stringSizeOfInt(i);
/*     */     } 
/*     */   }
/*  75 */   private static final int[] sizeTable = new int[] { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };
/*     */ 
/*     */ 
/*     */   
/*     */   private static int stringSizeOfInt(int x) {
/*  80 */     for (int i = 0;; i++) {
/*  81 */       if (x <= sizeTable[i])
/*  82 */         return i + 1; 
/*     */     } 
/*     */   }
/*     */   public String toString() {
/*  86 */     return String.valueOf(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public int length() {
/*  91 */     return this.length;
/*     */   }
/*     */   
/*     */   public char charAt(int index) {
/*  95 */     return toString().charAt(index);
/*     */   }
/*     */   
/*     */   public CharSequence subSequence(int start, int end) {
/*  99 */     return toString().substring(start, end);
/*     */   }
/*     */   
/*     */   public void writeTo(UTF8XmlOutput output) throws IOException {
/* 103 */     output.text(this.data);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\IntData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */