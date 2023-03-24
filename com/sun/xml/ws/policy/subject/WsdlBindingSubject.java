/*     */ package com.sun.xml.ws.policy.subject;
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
/*     */ public class WsdlBindingSubject
/*     */ {
/*     */   public enum WsdlMessageType
/*     */   {
/*  62 */     NO_MESSAGE,
/*  63 */     INPUT,
/*  64 */     OUTPUT,
/*  65 */     FAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum WsdlNameScope
/*     */   {
/*  76 */     SERVICE,
/*  77 */     ENDPOINT,
/*  78 */     OPERATION,
/*  79 */     MESSAGE;
/*     */   }
/*     */   
/*  82 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(WsdlBindingSubject.class);
/*     */   
/*     */   private final QName name;
/*     */   private final WsdlMessageType messageType;
/*     */   private final WsdlNameScope nameScope;
/*     */   private final WsdlBindingSubject parent;
/*     */   
/*     */   WsdlBindingSubject(QName name, WsdlNameScope scope, WsdlBindingSubject parent) {
/*  90 */     this(name, WsdlMessageType.NO_MESSAGE, scope, parent);
/*     */   }
/*     */   
/*     */   WsdlBindingSubject(QName name, WsdlMessageType messageType, WsdlNameScope scope, WsdlBindingSubject parent) {
/*  94 */     this.name = name;
/*  95 */     this.messageType = messageType;
/*  96 */     this.nameScope = scope;
/*  97 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public static WsdlBindingSubject createBindingSubject(QName bindingName) {
/* 101 */     return new WsdlBindingSubject(bindingName, WsdlNameScope.ENDPOINT, null);
/*     */   }
/*     */   
/*     */   public static WsdlBindingSubject createBindingOperationSubject(QName bindingName, QName operationName) {
/* 105 */     WsdlBindingSubject bindingSubject = createBindingSubject(bindingName);
/* 106 */     return new WsdlBindingSubject(operationName, WsdlNameScope.OPERATION, bindingSubject);
/*     */   }
/*     */   
/*     */   public static WsdlBindingSubject createBindingMessageSubject(QName bindingName, QName operationName, QName messageName, WsdlMessageType messageType) {
/* 110 */     if (messageType == null) {
/* 111 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0083_MESSAGE_TYPE_NULL()));
/*     */     }
/* 113 */     if (messageType == WsdlMessageType.NO_MESSAGE) {
/* 114 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0084_MESSAGE_TYPE_NO_MESSAGE()));
/*     */     }
/* 116 */     if (messageType == WsdlMessageType.FAULT && messageName == null) {
/* 117 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0085_MESSAGE_FAULT_NO_NAME()));
/*     */     }
/* 119 */     WsdlBindingSubject operationSubject = createBindingOperationSubject(bindingName, operationName);
/* 120 */     return new WsdlBindingSubject(messageName, messageType, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 124 */     return this.name;
/*     */   }
/*     */   
/*     */   public WsdlMessageType getMessageType() {
/* 128 */     return this.messageType;
/*     */   }
/*     */   
/*     */   public WsdlBindingSubject getParent() {
/* 132 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isBindingSubject() {
/* 136 */     if (this.nameScope == WsdlNameScope.ENDPOINT) {
/* 137 */       return (this.parent == null);
/*     */     }
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBindingOperationSubject() {
/* 145 */     if (this.nameScope == WsdlNameScope.OPERATION && 
/* 146 */       this.parent != null) {
/* 147 */       return this.parent.isBindingSubject();
/*     */     }
/*     */     
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isBindingMessageSubject() {
/* 154 */     if (this.nameScope == WsdlNameScope.MESSAGE && 
/* 155 */       this.parent != null) {
/* 156 */       return this.parent.isBindingOperationSubject();
/*     */     }
/*     */     
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 164 */     if (this == that) {
/* 165 */       return true;
/*     */     }
/*     */     
/* 168 */     if (that == null || !(that instanceof WsdlBindingSubject)) {
/* 169 */       return false;
/*     */     }
/*     */     
/* 172 */     WsdlBindingSubject thatSubject = (WsdlBindingSubject)that;
/* 173 */     boolean isEqual = true;
/*     */     
/* 175 */     isEqual = (isEqual && ((this.name == null) ? (thatSubject.name == null) : this.name.equals(thatSubject.name)));
/* 176 */     isEqual = (isEqual && this.messageType.equals(thatSubject.messageType));
/* 177 */     isEqual = (isEqual && this.nameScope.equals(thatSubject.nameScope));
/* 178 */     isEqual = (isEqual && ((this.parent == null) ? (thatSubject.parent == null) : this.parent.equals(thatSubject.parent)));
/*     */     
/* 180 */     return isEqual;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 185 */     int result = 23;
/*     */     
/* 187 */     result = 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
/* 188 */     result = 31 * result + this.messageType.hashCode();
/* 189 */     result = 31 * result + this.nameScope.hashCode();
/* 190 */     result = 31 * result + ((this.parent == null) ? 0 : this.parent.hashCode());
/*     */     
/* 192 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 197 */     StringBuilder result = new StringBuilder("WsdlBindingSubject[");
/* 198 */     result.append(this.name).append(", ").append(this.messageType);
/* 199 */     result.append(", ").append(this.nameScope).append(", ").append(this.parent);
/* 200 */     return result.append("]").toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\subject\WsdlBindingSubject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */