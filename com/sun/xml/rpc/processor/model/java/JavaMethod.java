/*     */ package com.sun.xml.rpc.processor.model.java;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaMethod
/*     */ {
/*     */   private String name;
/*     */   
/*     */   public JavaMethod() {}
/*     */   
/*     */   public JavaMethod(String name) {
/*  44 */     this.name = name;
/*  45 */     this.returnType = null;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  49 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  53 */     this.name = name;
/*     */   }
/*     */   
/*     */   public JavaType getReturnType() {
/*  57 */     return this.returnType;
/*     */   }
/*     */   
/*     */   public void setReturnType(JavaType returnType) {
/*  61 */     this.returnType = returnType;
/*     */   }
/*     */   
/*     */   public boolean hasParameter(String paramName) {
/*  65 */     for (int i = 0; i < this.parameters.size(); i++) {
/*  66 */       if (paramName.equals(((JavaParameter)this.parameters.get(i)).getName()))
/*     */       {
/*     */         
/*  69 */         return true;
/*     */       }
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParameter(JavaParameter param) {
/*  78 */     if (hasParameter(param.getName())) {
/*  79 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  81 */     this.parameters.add(param);
/*     */   }
/*     */   
/*     */   public Iterator getParameters() {
/*  85 */     return this.parameters.iterator();
/*     */   }
/*     */   
/*     */   public int getParameterCount() {
/*  89 */     return this.parameters.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getParametersList() {
/*  94 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParametersList(List l) {
/*  99 */     this.parameters = l;
/*     */   }
/*     */   
/*     */   public boolean hasException(String exception) {
/* 103 */     return this.exceptions.contains(exception);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addException(String exception) {
/* 109 */     if (hasException(exception)) {
/* 110 */       throw new ModelException("model.uniqueness");
/*     */     }
/* 112 */     this.exceptions.add(exception);
/*     */   }
/*     */   
/*     */   public Iterator getExceptions() {
/* 116 */     return this.exceptions.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getExceptionsList() {
/* 121 */     return this.exceptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExceptionsList(List l) {
/* 126 */     this.exceptions = l;
/*     */   }
/*     */   
/*     */   public String getDeclaringClass() {
/* 130 */     return this.declaringClass;
/*     */   }
/*     */   public void setDeclaringClass(String declaringClass) {
/* 133 */     this.declaringClass = declaringClass;
/*     */   }
/*     */ 
/*     */   
/* 137 */   private List parameters = new ArrayList();
/* 138 */   private List exceptions = new ArrayList();
/*     */   private JavaType returnType;
/*     */   private String declaringClass;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\java\JavaMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */