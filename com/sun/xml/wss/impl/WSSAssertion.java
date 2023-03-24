/*    */ package com.sun.xml.wss.impl;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSAssertion
/*    */ {
/*    */   Set<String> requiredPropSet;
/* 62 */   String version = "1.0";
/*    */   public static final String MUSTSUPPORT_REF_THUMBPRINT = "MustSupportRefThumbprint";
/*    */   
/*    */   public WSSAssertion(Set<String> props, String version) {
/* 66 */     this.requiredPropSet = props;
/*    */     
/* 68 */     if (this.requiredPropSet == null) {
/* 69 */       this.requiredPropSet = new HashSet<String>();
/*    */     }
/* 71 */     this.version = version;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final String MUSTSUPPORT_REF_ENCRYPTED_KEY = "MustSupportRefEncryptedKey";
/*    */   
/*    */   public static final String REQUIRE_SIGNATURE_CONFIRMATION = "RequireSignatureConfirmation";
/*    */   
/*    */   public static final String MUST_SUPPORT_CLIENT_CHALLENGE = "MustSupportClientChallenge";
/*    */   
/*    */   public static final String MUST_SUPPORT_SERVER_CHALLENGE = "MustSupportServerChallenge";
/*    */   
/*    */   public static final String REQUIRE_CLIENT_ENTROPY = "RequireClientEntropy";
/*    */   public static final String REQUIRE_SERVER_ENTROPY = "RequireServerEntropy";
/*    */   public static final String MUST_SUPPORT_ISSUED_TOKENS = "MustSupportIssuedTokens";
/*    */   public static final String MUSTSUPPORT_REF_ISSUER_SERIAL = "MustSupportRefIssuerSerial";
/*    */   public static final String REQUIRE_EXTERNAL_URI_REFERENCE = "RequireExternalUriReference";
/*    */   public static final String REQUIRE_EMBEDDED_TOKEN_REF = "RequireEmbeddedTokenReference";
/*    */   public static final String MUST_SUPPORT_REF_KEYIDENTIFIER = "MustSupportRefKeyIdentifier";
/*    */   
/*    */   public Set getRequiredProperties() {
/* 92 */     return this.requiredPropSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getType() {
/* 99 */     return this.version;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\WSSAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */