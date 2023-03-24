/*     */ package com.sun.xml.ws.policy.spi;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AssertionCreationException
/*     */   extends PolicyException
/*     */ {
/*     */   private final AssertionData assertionData;
/*     */   
/*     */   public AssertionCreationException(AssertionData assertionData, String message) {
/*  65 */     super(message);
/*  66 */     this.assertionData = assertionData;
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
/*     */   public AssertionCreationException(AssertionData assertionData, String message, Throwable cause) {
/*  80 */     super(message, cause);
/*  81 */     this.assertionData = assertionData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionCreationException(AssertionData assertionData, Throwable cause) {
/*  91 */     super(cause);
/*  92 */     this.assertionData = assertionData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionData getAssertionData() {
/* 101 */     return this.assertionData;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\spi\AssertionCreationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */