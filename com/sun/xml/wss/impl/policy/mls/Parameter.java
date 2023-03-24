/*    */ package com.sun.xml.wss.impl.policy.mls;
/*    */ 
/*    */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Parameter
/*    */   implements TransformParameterSpec
/*    */ {
/* 60 */   private String paramName = null;
/* 61 */   private String paramValue = null;
/*    */ 
/*    */   
/*    */   public Parameter() {}
/*    */   
/*    */   public Parameter(String name, String value) {
/* 67 */     this.paramName = name;
/* 68 */     this.paramValue = value;
/*    */   }
/*    */   
/*    */   public String getParamName() {
/* 72 */     return this.paramName;
/*    */   }
/*    */   
/*    */   public void setParamName(String paramName) {
/* 76 */     this.paramName = paramName;
/*    */   }
/*    */   
/*    */   public String getParamValue() {
/* 80 */     return this.paramValue;
/*    */   }
/*    */   
/*    */   public void setParamValue(String paramValue) {
/* 84 */     this.paramValue = paramValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */