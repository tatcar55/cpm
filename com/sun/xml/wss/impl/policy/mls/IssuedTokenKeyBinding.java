/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IssuedTokenKeyBinding
/*     */   extends KeyBindingBase
/*     */   implements LazyKeyBinding
/*     */ {
/*  59 */   String strId = null;
/*     */   
/*     */   private String realId;
/*     */   
/*     */   private String tokenType;
/*     */   
/*     */   public IssuedTokenKeyBinding() {
/*  66 */     setPolicyIdentifier("IssuedTokenKeyBinding");
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  70 */     IssuedTokenKeyBinding itb = new IssuedTokenKeyBinding();
/*     */     
/*  72 */     itb.setUUID(getUUID());
/*  73 */     itb.setIncludeToken(getIncludeToken());
/*  74 */     itb.setPolicyTokenFlag(policyTokenWasSet());
/*  75 */     itb.setSTRID(this.strId);
/*  76 */     return itb;
/*     */   }
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/*  80 */     if (!PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)policy)) {
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  85 */     return true;
/*     */   }
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/*  89 */     return equals(policy);
/*     */   }
/*     */   
/*     */   public String getType() {
/*  93 */     return "IssuedTokenKeyBinding";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSTRID(String id) {
/* 102 */     if (isReadOnly()) {
/* 103 */       throw new RuntimeException("Can not set Issued Token STRID : Policy is ReadOnly");
/*     */     }
/*     */     
/* 106 */     this.strId = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSTRID() {
/* 115 */     return this.strId;
/*     */   }
/*     */   
/*     */   public String getRealId() {
/* 119 */     return this.realId;
/*     */   }
/*     */   
/*     */   public void setRealId(String realId) {
/* 123 */     this.realId = realId;
/*     */   }
/*     */   public void setTokenType(String tokenType) {
/* 126 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 130 */     return this.tokenType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\IssuedTokenKeyBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */