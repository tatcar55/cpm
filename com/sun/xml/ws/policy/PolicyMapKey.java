/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PolicyMapKey
/*     */ {
/*  61 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapKey.class);
/*     */   
/*     */   private final QName service;
/*     */   
/*     */   private final QName port;
/*     */   private final QName operation;
/*     */   private final QName faultMessage;
/*     */   private PolicyMapKeyHandler handler;
/*     */   
/*     */   PolicyMapKey(QName service, QName port, QName operation, PolicyMapKeyHandler handler) {
/*  71 */     this(service, port, operation, null, handler);
/*     */   }
/*     */   
/*     */   PolicyMapKey(QName service, QName port, QName operation, QName faultMessage, PolicyMapKeyHandler handler) {
/*  75 */     if (handler == null) {
/*  76 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0046_POLICY_MAP_KEY_HANDLER_NOT_SET()));
/*     */     }
/*     */     
/*  79 */     this.service = service;
/*  80 */     this.port = port;
/*  81 */     this.operation = operation;
/*  82 */     this.faultMessage = faultMessage;
/*  83 */     this.handler = handler;
/*     */   }
/*     */   
/*     */   PolicyMapKey(PolicyMapKey that) {
/*  87 */     this.service = that.service;
/*  88 */     this.port = that.port;
/*  89 */     this.operation = that.operation;
/*  90 */     this.faultMessage = that.faultMessage;
/*  91 */     this.handler = that.handler;
/*     */   }
/*     */   
/*     */   public QName getOperation() {
/*  95 */     return this.operation;
/*     */   }
/*     */   
/*     */   public QName getPort() {
/*  99 */     return this.port;
/*     */   }
/*     */   
/*     */   public QName getService() {
/* 103 */     return this.service;
/*     */   }
/*     */   
/*     */   void setHandler(PolicyMapKeyHandler handler) {
/* 107 */     if (handler == null) {
/* 108 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0046_POLICY_MAP_KEY_HANDLER_NOT_SET()));
/*     */     }
/*     */     
/* 111 */     this.handler = handler;
/*     */   }
/*     */   
/*     */   public QName getFaultMessage() {
/* 115 */     return this.faultMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 120 */     if (this == that) {
/* 121 */       return true;
/*     */     }
/*     */     
/* 124 */     if (that == null) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     if (that instanceof PolicyMapKey) {
/* 129 */       return this.handler.areEqual(this, (PolicyMapKey)that);
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     return this.handler.generateHashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     StringBuffer result = new StringBuffer("PolicyMapKey(");
/* 143 */     result.append(this.service).append(", ").append(this.port).append(", ").append(this.operation).append(", ").append(this.faultMessage);
/* 144 */     return result.append(")").toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyMapKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */