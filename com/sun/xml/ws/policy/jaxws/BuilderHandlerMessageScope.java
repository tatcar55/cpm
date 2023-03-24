/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapExtender;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
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
/*     */ final class BuilderHandlerMessageScope
/*     */   extends BuilderHandler
/*     */ {
/*     */   private final QName service;
/*     */   private final QName port;
/*     */   private final QName operation;
/*     */   private final QName message;
/*     */   private final Scope scope;
/*     */   
/*     */   enum Scope
/*     */   {
/*  66 */     InputMessageScope,
/*  67 */     OutputMessageScope,
/*  68 */     FaultMessageScope;
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
/*     */   BuilderHandlerMessageScope(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject, Scope scope, QName service, QName port, QName operation, QName message) {
/*  80 */     super(policyURIs, policyStore, policySubject);
/*  81 */     this.service = service;
/*  82 */     this.port = port;
/*  83 */     this.operation = operation;
/*  84 */     this.scope = scope;
/*  85 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  95 */     if (this == obj) {
/*  96 */       return true;
/*     */     }
/*     */     
/*  99 */     if (!(obj instanceof BuilderHandlerMessageScope)) {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     BuilderHandlerMessageScope that = (BuilderHandlerMessageScope)obj;
/* 104 */     boolean result = true;
/*     */     
/* 106 */     result = (result && ((this.policySubject == null) ? (that.policySubject == null) : this.policySubject.equals(that.policySubject)));
/* 107 */     result = (result && ((this.scope == null) ? (that.scope == null) : this.scope.equals(that.scope)));
/* 108 */     result = (result && ((this.message == null) ? (that.message == null) : this.message.equals(that.message)));
/* 109 */     if (this.scope != Scope.FaultMessageScope) {
/* 110 */       result = (result && ((this.service == null) ? (that.service == null) : this.service.equals(that.service)));
/* 111 */       result = (result && ((this.port == null) ? (that.port == null) : this.port.equals(that.port)));
/* 112 */       result = (result && ((this.operation == null) ? (that.operation == null) : this.operation.equals(that.operation)));
/*     */     } 
/*     */     
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     int hashCode = 19;
/* 121 */     hashCode = 31 * hashCode + ((this.policySubject == null) ? 0 : this.policySubject.hashCode());
/* 122 */     hashCode = 31 * hashCode + ((this.message == null) ? 0 : this.message.hashCode());
/* 123 */     hashCode = 31 * hashCode + ((this.scope == null) ? 0 : this.scope.hashCode());
/* 124 */     if (this.scope != Scope.FaultMessageScope) {
/* 125 */       hashCode = 31 * hashCode + ((this.service == null) ? 0 : this.service.hashCode());
/* 126 */       hashCode = 31 * hashCode + ((this.port == null) ? 0 : this.port.hashCode());
/* 127 */       hashCode = 31 * hashCode + ((this.operation == null) ? 0 : this.operation.hashCode());
/*     */     } 
/* 129 */     return hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPopulate(PolicyMapExtender policyMapExtender) throws PolicyException {
/*     */     PolicyMapKey mapKey;
/* 135 */     if (Scope.FaultMessageScope == this.scope) {
/* 136 */       mapKey = PolicyMap.createWsdlFaultMessageScopeKey(this.service, this.port, this.operation, this.message);
/*     */     } else {
/* 138 */       mapKey = PolicyMap.createWsdlMessageScopeKey(this.service, this.port, this.operation);
/*     */     } 
/*     */     
/* 141 */     if (Scope.InputMessageScope == this.scope) {
/* 142 */       for (PolicySubject subject : getPolicySubjects()) {
/* 143 */         policyMapExtender.putInputMessageSubject(mapKey, subject);
/*     */       }
/* 145 */     } else if (Scope.OutputMessageScope == this.scope) {
/* 146 */       for (PolicySubject subject : getPolicySubjects()) {
/* 147 */         policyMapExtender.putOutputMessageSubject(mapKey, subject);
/*     */       }
/* 149 */     } else if (Scope.FaultMessageScope == this.scope) {
/* 150 */       for (PolicySubject subject : getPolicySubjects())
/* 151 */         policyMapExtender.putFaultMessageSubject(mapKey, subject); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\BuilderHandlerMessageScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */