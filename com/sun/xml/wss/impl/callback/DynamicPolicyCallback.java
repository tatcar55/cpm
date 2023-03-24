/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import javax.security.auth.callback.Callback;
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
/*     */ public class DynamicPolicyCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   boolean isDynamicSecurityPolicy = false;
/*     */   SecurityPolicy _policy;
/*     */   DynamicPolicyContext _ctx;
/*     */   
/*     */   public DynamicPolicyCallback(SecurityPolicy _policy, DynamicPolicyContext _ctx) throws PolicyGenerationException {
/*  91 */     checkType(_policy);
/*     */     
/*  93 */     this._policy = _policy;
/*  94 */     this._ctx = _ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityPolicy getSecurityPolicy() {
/* 102 */     return this._policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicPolicyContext getDynamicContext() {
/* 109 */     return this._ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticPolicyContext getStaticContext() {
/* 116 */     return this._ctx.getStaticPolicyContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecurityPolicy(SecurityPolicy _policy) {
/* 124 */     if (this.isDynamicSecurityPolicy) {
/* 125 */       checkType0(_policy);
/*     */       
/* 127 */       this._policy = _policy;
/*     */     } else {
/* 129 */       if (!this._policy.getType().equals(_policy.getType()))
/*     */       {
/* 131 */         throw new UnsupportedOperationException("Can not change object instance for WSSPolicy");
/*     */       }
/*     */       
/* 134 */       this._policy = _policy;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDynamicSecurityPolicy() {
/* 140 */     return this.isDynamicSecurityPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkType(SecurityPolicy policy) throws PolicyGenerationException {
/*     */     try {
/* 147 */       if (PolicyTypeUtil.dynamicSecurityPolicy(policy)) {
/* 148 */         this.isDynamicSecurityPolicy = true;
/*     */       }
/* 150 */       else if (!Class.forName("com.sun.xml.wss.impl.policy.mls.WSSPolicy").isAssignableFrom(policy.getClass())) {
/*     */ 
/*     */         
/* 153 */         throw new PolicyGenerationException("Invalid SecurityPolicy type");
/*     */       }
/*     */     
/* 156 */     } catch (ClassNotFoundException cnfe) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkType0(SecurityPolicy policy) {
/* 161 */     if (!PolicyTypeUtil.messagePolicy(policy))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 166 */       throw new IllegalArgumentException("Invalid SecurityPolicy type " + policy + ", Expected MessagePolicy");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\DynamicPolicyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */