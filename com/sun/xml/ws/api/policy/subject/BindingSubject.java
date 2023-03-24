/*     */ package com.sun.xml.ws.api.policy.subject;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.resources.BindingApiMessages;
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
/*     */ public class BindingSubject
/*     */ {
/*     */   private enum WsdlMessageType
/*     */   {
/*  61 */     NO_MESSAGE,
/*  62 */     INPUT,
/*  63 */     OUTPUT,
/*  64 */     FAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum WsdlNameScope
/*     */   {
/*  75 */     SERVICE,
/*  76 */     ENDPOINT,
/*  77 */     OPERATION,
/*  78 */     MESSAGE;
/*     */   }
/*     */   
/*  81 */   private static final Logger LOGGER = Logger.getLogger(BindingSubject.class);
/*     */   
/*     */   private final QName name;
/*     */   private final WsdlMessageType messageType;
/*     */   private final WsdlNameScope nameScope;
/*     */   private final BindingSubject parent;
/*     */   
/*     */   BindingSubject(QName name, WsdlNameScope scope, BindingSubject parent) {
/*  89 */     this(name, WsdlMessageType.NO_MESSAGE, scope, parent);
/*     */   }
/*     */   
/*     */   BindingSubject(QName name, WsdlMessageType messageType, WsdlNameScope scope, BindingSubject parent) {
/*  93 */     this.name = name;
/*  94 */     this.messageType = messageType;
/*  95 */     this.nameScope = scope;
/*  96 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public static BindingSubject createBindingSubject(QName bindingName) {
/* 100 */     return new BindingSubject(bindingName, WsdlNameScope.ENDPOINT, null);
/*     */   }
/*     */   
/*     */   public static BindingSubject createOperationSubject(QName bindingName, QName operationName) {
/* 104 */     BindingSubject bindingSubject = createBindingSubject(bindingName);
/* 105 */     return new BindingSubject(operationName, WsdlNameScope.OPERATION, bindingSubject);
/*     */   }
/*     */   
/*     */   public static BindingSubject createInputMessageSubject(QName bindingName, QName operationName, QName messageName) {
/* 109 */     BindingSubject operationSubject = createOperationSubject(bindingName, operationName);
/* 110 */     return new BindingSubject(messageName, WsdlMessageType.INPUT, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public static BindingSubject createOutputMessageSubject(QName bindingName, QName operationName, QName messageName) {
/* 114 */     BindingSubject operationSubject = createOperationSubject(bindingName, operationName);
/* 115 */     return new BindingSubject(messageName, WsdlMessageType.OUTPUT, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public static BindingSubject createFaultMessageSubject(QName bindingName, QName operationName, QName messageName) {
/* 119 */     if (messageName == null) {
/* 120 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(BindingApiMessages.BINDING_API_NO_FAULT_MESSAGE_NAME()));
/*     */     }
/* 122 */     BindingSubject operationSubject = createOperationSubject(bindingName, operationName);
/* 123 */     return new BindingSubject(messageName, WsdlMessageType.FAULT, WsdlNameScope.MESSAGE, operationSubject);
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 127 */     return this.name;
/*     */   }
/*     */   
/*     */   public BindingSubject getParent() {
/* 131 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isBindingSubject() {
/* 135 */     if (this.nameScope == WsdlNameScope.ENDPOINT) {
/* 136 */       return (this.parent == null);
/*     */     }
/*     */     
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOperationSubject() {
/* 144 */     if (this.nameScope == WsdlNameScope.OPERATION && 
/* 145 */       this.parent != null) {
/* 146 */       return this.parent.isBindingSubject();
/*     */     }
/*     */     
/* 149 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isMessageSubject() {
/* 153 */     if (this.nameScope == WsdlNameScope.MESSAGE && 
/* 154 */       this.parent != null) {
/* 155 */       return this.parent.isOperationSubject();
/*     */     }
/*     */     
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInputMessageSubject() {
/* 162 */     return (isMessageSubject() && this.messageType == WsdlMessageType.INPUT);
/*     */   }
/*     */   
/*     */   public boolean isOutputMessageSubject() {
/* 166 */     return (isMessageSubject() && this.messageType == WsdlMessageType.OUTPUT);
/*     */   }
/*     */   
/*     */   public boolean isFaultMessageSubject() {
/* 170 */     return (isMessageSubject() && this.messageType == WsdlMessageType.FAULT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 175 */     if (this == that) {
/* 176 */       return true;
/*     */     }
/*     */     
/* 179 */     if (that == null || !(that instanceof BindingSubject)) {
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     BindingSubject thatSubject = (BindingSubject)that;
/* 184 */     boolean isEqual = true;
/*     */     
/* 186 */     isEqual = (isEqual && ((this.name == null) ? (thatSubject.name == null) : this.name.equals(thatSubject.name)));
/* 187 */     isEqual = (isEqual && this.messageType.equals(thatSubject.messageType));
/* 188 */     isEqual = (isEqual && this.nameScope.equals(thatSubject.nameScope));
/* 189 */     isEqual = (isEqual && ((this.parent == null) ? (thatSubject.parent == null) : this.parent.equals(thatSubject.parent)));
/*     */     
/* 191 */     return isEqual;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 196 */     int result = 23;
/*     */     
/* 198 */     result = 29 * result + ((this.name == null) ? 0 : this.name.hashCode());
/* 199 */     result = 29 * result + this.messageType.hashCode();
/* 200 */     result = 29 * result + this.nameScope.hashCode();
/* 201 */     result = 29 * result + ((this.parent == null) ? 0 : this.parent.hashCode());
/*     */     
/* 203 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 208 */     StringBuilder result = new StringBuilder("BindingSubject[");
/* 209 */     result.append(this.name).append(", ").append(this.messageType);
/* 210 */     result.append(", ").append(this.nameScope).append(", ").append(this.parent);
/* 211 */     return result.append("]").toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\subject\BindingSubject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */