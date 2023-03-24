/*     */ package com.sun.xml.ws.security.policy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum SecurityPolicyVersion
/*     */ {
/*  50 */   SECURITYPOLICY200507("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Once", "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Never", "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient", "http://schemas.xmlsoap.org/ws/2005/07/securitypolicy/IncludeToken/Always")
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNamespaceURI()
/*     */     {
/*  58 */       return this.namespaceUri;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenOnce() {
/*  63 */       return this.includeTokenOnce;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenNever() {
/*  68 */       return this.includeTokenNever;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlwaysToRecipient() {
/*  73 */       return this.includeTokenAlwaysToRecipient;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlways() {
/*  78 */       return this.includeTokenAlways;
/*     */     }
/*     */   },
/*     */   
/*  82 */   SECURITYPOLICY12NS("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Once", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Never", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToRecipient", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/Always")
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNamespaceURI()
/*     */     {
/*  90 */       return this.namespaceUri;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenOnce() {
/*  95 */       return this.includeTokenOnce;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenNever() {
/* 100 */       return this.includeTokenNever;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlwaysToRecipient() {
/* 105 */       return this.includeTokenAlwaysToRecipient;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlways() {
/* 110 */       return this.includeTokenAlways;
/*     */     }
/*     */   },
/*     */   
/* 114 */   SECURITYPOLICY200512("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200512", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200512/IncludeToken/Once", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200512/IncludeToken/Never", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200512/IncludeToken/AlwaysToRecipient", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200512/IncludeToken/Always")
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNamespaceURI()
/*     */     {
/* 122 */       return this.namespaceUri;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenOnce() {
/* 127 */       return this.includeTokenOnce;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenNever() {
/* 132 */       return this.includeTokenNever;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlwaysToRecipient() {
/* 137 */       return this.includeTokenAlwaysToRecipient;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlways() {
/* 142 */       return this.includeTokenAlways;
/*     */     }
/*     */   },
/*     */   
/* 146 */   MS_SECURITYPOLICY200507("http://schemas.microsoft.com/ws/2005/07/securitypolicy", "http://schemas.microsoft.com/ws/2005/07/securitypolicy/IncludeToken/Once", "http://schemas.microsoft.com/ws/2005/07/securitypolicy/IncludeToken/Never", "http://schemas.microsoft.com/ws/2005/07/securitypolicy/IncludeToken/AlwaysToRecipient", "http://schemas.microsoft.com/ws/2005/07/securitypolicy/IncludeToken/Always")
/*     */   {
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNamespaceURI()
/*     */     {
/* 154 */       return this.namespaceUri;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenOnce() {
/* 159 */       return this.includeTokenOnce;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenNever() {
/* 164 */       return this.includeTokenNever;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlwaysToRecipient() {
/* 169 */       return this.includeTokenAlwaysToRecipient;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIncludeTokenAlways() {
/* 174 */       return this.includeTokenAlways;
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String namespaceUri;
/*     */ 
/*     */ 
/*     */   
/*     */   public final String includeTokenOnce;
/*     */ 
/*     */ 
/*     */   
/*     */   public final String includeTokenNever;
/*     */ 
/*     */ 
/*     */   
/*     */   public final String includeTokenAlwaysToRecipient;
/*     */ 
/*     */ 
/*     */   
/*     */   public final String includeTokenAlways;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SecurityPolicyVersion(String nsUri, String includeOnce, String includeNever, String includeAlwaysToRecipient, String includeAlways) {
/* 204 */     this.namespaceUri = nsUri;
/* 205 */     this.includeTokenOnce = includeOnce;
/* 206 */     this.includeTokenNever = includeNever;
/* 207 */     this.includeTokenAlwaysToRecipient = includeAlwaysToRecipient;
/* 208 */     this.includeTokenAlways = includeAlways;
/*     */   }
/*     */   
/*     */   public abstract String getNamespaceURI();
/*     */   
/*     */   public abstract String getIncludeTokenOnce();
/*     */   
/*     */   public abstract String getIncludeTokenNever();
/*     */   
/*     */   public abstract String getIncludeTokenAlwaysToRecipient();
/*     */   
/*     */   public abstract String getIncludeTokenAlways();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\SecurityPolicyVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */