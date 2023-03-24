/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyBindingBase
/*     */   extends WSSPolicy
/*     */ {
/*  49 */   public static final String INCLUDE_ONCE = "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Once".intern();
/*  50 */   public static final String INCLUDE_ONCE_VER2 = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Once".intern();
/*  51 */   public static final String INCLUDE_NEVER = "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Never".intern();
/*  52 */   public static final String INCLUDE_NEVER_VER2 = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Never".intern();
/*  53 */   public static final String INCLUDE_ALWAYS_TO_RECIPIENT = "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient".intern();
/*  54 */   public static final String INCLUDE_ALWAYS = "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always".intern();
/*  55 */   public static final String INCLUDE_ALWAYS_TO_RECIPIENT_VER2 = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToRecipient".intern();
/*  56 */   public static final String INCLUDE_ALWAYS_VER2 = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Always".intern();
/*     */   
/*     */   protected boolean policyToken = false;
/*  59 */   protected String includeToken = INCLUDE_ALWAYS;
/*     */ 
/*     */   
/*     */   protected String issuer;
/*     */   
/*     */   protected byte[] claims;
/*     */   
/*     */   protected String claimsDialect;
/*     */ 
/*     */   
/*     */   public boolean policyTokenWasSet() {
/*  70 */     return this.policyToken;
/*     */   }
/*     */   
/*     */   public void setPolicyTokenFlag(boolean flag) {
/*  74 */     this.policyToken = flag;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String include) {
/*  78 */     if (INCLUDE_ONCE.equals(include)) {
/*  79 */       throw new UnsupportedOperationException("IncludeToken Policy ONCE is not yet Supported");
/*     */     }
/*  81 */     this.includeToken = include;
/*  82 */     this.policyToken = true;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/*  86 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIssuer(String issuer) {
/*  90 */     this.issuer = issuer;
/*     */   }
/*     */   
/*     */   public String getIssuer() {
/*  94 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public void setClaims(byte[] claims) {
/*  98 */     this.claims = claims;
/*     */   }
/*     */   
/*     */   public byte[] getClaims() {
/* 102 */     return this.claims;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\KeyBindingBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */