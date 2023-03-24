/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.addressing.WsaActionUtil;
/*     */ import com.sun.xml.ws.api.model.CheckedException;
/*     */ import com.sun.xml.ws.api.model.ExceptionType;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CheckedExceptionImpl
/*     */   implements CheckedException
/*     */ {
/*     */   private final Class exceptionClass;
/*     */   private final TypeInfo detail;
/*     */   private final ExceptionType exceptionType;
/*     */   private final JavaMethodImpl javaMethod;
/*     */   private String messageName;
/*  69 */   private String faultAction = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckedExceptionImpl(JavaMethodImpl jm, Class exceptionClass, TypeInfo detail, ExceptionType exceptionType) {
/*  82 */     this.detail = detail;
/*  83 */     this.exceptionType = exceptionType;
/*  84 */     this.exceptionClass = exceptionClass;
/*  85 */     this.javaMethod = jm;
/*     */   }
/*     */   
/*     */   public AbstractSEIModelImpl getOwner() {
/*  89 */     return this.javaMethod.owner;
/*     */   }
/*     */   
/*     */   public JavaMethod getParent() {
/*  93 */     return this.javaMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getExceptionClass() {
/* 101 */     return this.exceptionClass;
/*     */   }
/*     */   
/*     */   public Class getDetailBean() {
/* 105 */     return (Class)this.detail.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bridge getBridge() {
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   public XMLBridge getBond() {
/* 114 */     return getOwner().getXMLBridge(this.detail);
/*     */   }
/*     */   
/*     */   public TypeInfo getDetailType() {
/* 118 */     return this.detail;
/*     */   }
/*     */   
/*     */   public ExceptionType getExceptionType() {
/* 122 */     return this.exceptionType;
/*     */   }
/*     */   
/*     */   public String getMessageName() {
/* 126 */     return this.messageName;
/*     */   }
/*     */   
/*     */   public void setMessageName(String messageName) {
/* 130 */     this.messageName = messageName;
/*     */   }
/*     */   
/*     */   public String getFaultAction() {
/* 134 */     return this.faultAction;
/*     */   }
/*     */   
/*     */   public void setFaultAction(String faultAction) {
/* 138 */     this.faultAction = faultAction;
/*     */   }
/*     */   
/*     */   public String getDefaultFaultAction() {
/* 142 */     return WsaActionUtil.getDefaultFaultAction(this.javaMethod, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\CheckedExceptionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */