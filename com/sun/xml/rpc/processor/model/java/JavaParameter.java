/*    */ package com.sun.xml.rpc.processor.model.java;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.model.Parameter;
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
/*    */ public class JavaParameter
/*    */ {
/*    */   private String name;
/*    */   private JavaType type;
/*    */   private Parameter parameter;
/*    */   private boolean holder;
/*    */   private String holderName;
/*    */   
/*    */   public JavaParameter() {}
/*    */   
/*    */   public JavaParameter(String name, JavaType type, Parameter parameter) {
/* 40 */     this(name, type, parameter, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JavaParameter(String name, JavaType type, Parameter parameter, boolean holder) {
/* 46 */     this.name = name;
/* 47 */     this.type = type;
/* 48 */     this.parameter = parameter;
/* 49 */     this.holder = holder;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String s) {
/* 57 */     this.name = s;
/*    */   }
/*    */   
/*    */   public JavaType getType() {
/* 61 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(JavaType t) {
/* 65 */     this.type = t;
/*    */   }
/*    */   
/*    */   public Parameter getParameter() {
/* 69 */     return this.parameter;
/*    */   }
/*    */   
/*    */   public void setParameter(Parameter p) {
/* 73 */     this.parameter = p;
/*    */   }
/*    */   
/*    */   public boolean isHolder() {
/* 77 */     return this.holder;
/*    */   }
/*    */   
/*    */   public void setHolder(boolean b) {
/* 81 */     this.holder = b;
/*    */   }
/*    */   
/*    */   public String getHolderName() {
/* 85 */     return this.holderName;
/*    */   }
/*    */   
/*    */   public void setHolderName(String holderName) {
/* 89 */     this.holderName = holderName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaParameter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */