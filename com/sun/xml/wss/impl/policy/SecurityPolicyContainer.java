/*     */ package com.sun.xml.wss.impl.policy;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityPolicyContainer
/*     */   implements SecurityPolicy
/*     */ {
/*  59 */   protected HashMap _ctx2PolicyMap = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecurityPolicy(StaticPolicyContext ctx, SecurityPolicy policy) {
/*  70 */     ArrayList<SecurityPolicy> al = (ArrayList)this._ctx2PolicyMap.get(ctx);
/*     */     
/*  72 */     if (al != null) {
/*  73 */       al.add(policy);
/*     */     } else {
/*  75 */       al = new ArrayList<SecurityPolicy>();
/*  76 */       al.add(policy);
/*  77 */       this._ctx2PolicyMap.put(ctx, al);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getSecurityPolicies(StaticPolicyContext ctx) {
/*  89 */     ArrayList list = (ArrayList)this._ctx2PolicyMap.get(ctx);
/*     */     
/*  91 */     if (list != null)
/*  92 */       return list.iterator(); 
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getAllContexts() {
/* 101 */     return this._ctx2PolicyMap.keySet().iterator();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getSecurityPolicies(StaticPolicyContext sCtx, DynamicPolicyContext dCtx) throws PolicyGenerationException {
/* 116 */     ArrayList hs0 = (ArrayList)this._ctx2PolicyMap.get(sCtx);
/*     */     
/* 118 */     ArrayList<Object> hs1 = new ArrayList();
/*     */     
/* 120 */     Iterator i = hs0.iterator();
/* 121 */     while (i.hasNext()) {
/* 122 */       Object obj = i.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       hs1.add(obj);
/*     */     } 
/*     */     
/* 141 */     return hs1.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 148 */     return "SecurityPolicyContainer";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\SecurityPolicyContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */