/*     */ package com.sun.org.apache.xml.internal.resolver.helpers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Debug
/*     */ {
/*  36 */   protected int debug = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDebug(int newDebug) {
/*  45 */     this.debug = newDebug;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDebug() {
/*  50 */     return this.debug;
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
/*     */   public void message(int level, String message) {
/*  65 */     if (this.debug >= level) {
/*  66 */       System.out.println(message);
/*     */     }
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
/*     */   public void message(int level, String message, String spec) {
/*  83 */     if (this.debug >= level) {
/*  84 */       System.out.println(message + ": " + spec);
/*     */     }
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
/*     */   public void message(int level, String message, String spec1, String spec2) {
/* 103 */     if (this.debug >= level) {
/* 104 */       System.out.println(message + ": " + spec1);
/* 105 */       System.out.println("\t" + spec2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\resolver\helpers\Debug.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */