/*    */ package com.sun.xml.ws.security.opt.crypto;
/*    */ 
/*    */ import java.security.spec.AlgorithmParameterSpec;
/*    */ import javax.xml.crypto.AlgorithmMethod;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlgorithmMethod
/*    */   implements AlgorithmMethod
/*    */ {
/* 57 */   private AlgorithmParameterSpec algSpec = null;
/* 58 */   private String alg = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAlgorithm(String alg) {
/* 65 */     this.alg = alg;
/*    */   }
/*    */   
/*    */   public String getAlgorithm() {
/* 69 */     return this.alg;
/*    */   }
/*    */   
/*    */   public void setParameterSpec(AlgorithmParameterSpec algSpec) {
/* 73 */     this.algSpec = algSpec;
/*    */   }
/*    */   
/*    */   public AlgorithmParameterSpec getParameterSpec() {
/* 77 */     return this.algSpec;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\AlgorithmMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */