/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodInfo
/*     */ {
/*  37 */   private Method method = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodInfo() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodInfo(Method method) {
/*  49 */     this.method = method;
/*     */   }
/*     */   
/*     */   public int getModifiers() {
/*  53 */     if (this.method != null) {
/*  54 */       return this.method.getModifiers();
/*     */     }
/*     */     
/*  57 */     return 2;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  61 */     if (this.method != null) {
/*  62 */       return this.method.getName();
/*     */     }
/*     */     
/*  65 */     return "";
/*     */   }
/*     */   
/*     */   public Class[] getParameterTypes() {
/*  69 */     if (this.method != null) {
/*  70 */       return this.method.getParameterTypes();
/*     */     }
/*     */     
/*  73 */     return getParameterTypes(0);
/*     */   }
/*     */   
/*     */   public Class[] getParameterTypes(int parameterCount) {
/*  77 */     if (this.method != null) {
/*  78 */       return this.method.getParameterTypes();
/*     */     }
/*     */     
/*  81 */     return new Class[parameterCount];
/*     */   }
/*     */   
/*     */   public int getParameterCount() {
/*  85 */     if (this.method != null) {
/*  86 */       return (this.method.getParameterTypes()).length;
/*     */     }
/*     */     
/*  89 */     return -1;
/*     */   }
/*     */   
/*     */   public Class getReturnType() {
/*  93 */     if (this.method != null) {
/*  94 */       return this.method.getReturnType();
/*     */     }
/*     */     
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public Method getMethodObject() {
/* 101 */     return this.method;
/*     */   }
/*     */   
/*     */   public boolean matches(String methodName, OperationInfo operation) {
/* 105 */     if (!operation.getName().getLocalPart().equals(methodName)) {
/* 106 */       return false;
/*     */     }
/* 108 */     if (this.method != null && 
/* 109 */       getParameterCount() != operation.getParameterCount()) {
/* 110 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\MethodInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */