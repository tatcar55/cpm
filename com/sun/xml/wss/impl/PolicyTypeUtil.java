/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyTypeUtil
/*     */ {
/*     */   public static final String SEC_POLICY_CONTAINER_TYPE = "SecurityPolicyContainer";
/*     */   public static final String DYN_SEC_POLICY_TYPE = "DynamicSecurityPolicy";
/*     */   public static final String SEC_POLICY_ALTERNATIVES_TYPE = "SecurityPolicyAlternatives";
/*     */   public static final String BOOLEAN_COMPOSER_TYPE = "BooleanComposer";
/*     */   public static final String APP_SEC_CONFIG_TYPE = "ApplicationSecurityConfiguration";
/*     */   public static final String DECL_SEC_CONFIG_TYPE = "DeclarativeSecurityConfiguration";
/*     */   public static final String MESSAGEPOLICY_CONFIG_TYPE = "MessagePolicy";
/*     */   public static final String AUTH_POLICY_TYPE = "AuthenticationTokenPolicy";
/*     */   public static final String SIGNATURE_POLICY_TYPE = "SignaturePolicy";
/*     */   public static final String ENCRYPTION_POLICY_TYPE = "EncryptionPolicy";
/*     */   public static final String TIMESTAMP_POLICY_TYPE = "TimestampPolicy";
/*     */   public static final String SIGNATURE_CONFIRMATION_POLICY_TYPE = "SignatureConfirmationPolicy";
/*     */   public static final String USERNAMETOKEN_TYPE = "UsernameTokenBinding";
/*     */   public static final String X509CERTIFICATE_TYPE = "X509CertificateBinding";
/*     */   public static final String SAMLASSERTION_TYPE = "SAMLAssertionBinding";
/*     */   public static final String SYMMETRIC_KEY_TYPE = "SymmetricKeyBinding";
/*     */   public static final String KERBEROS_BST_TYPE = "KerberosTokenBinding";
/*     */   public static final String RSATOKEN_TYPE = "RsaTokenBinding";
/*     */   public static final String PRIVATEKEY_BINDING_TYPE = "PrivateKeyBinding";
/*     */   public static final String ENCRYPTION_POLICY_FEATUREBINDING_TYPE = "EncryptionPolicy.FeatureBinding";
/*     */   public static final String SIGNATURE_POLICY_FEATUREBINDING_TYPE = "SignaturePolicy.FeatureBinding";
/*     */   public static final String DERIVED_TOKEN_KEY_BINDING = "DerivedTokenKeyBinding";
/*     */   public static final String ISSUED_TOKEN_KEY_BINDING = "IssuedTokenKeyBinding";
/*     */   public static final String SECURE_CONVERSATION_TOKEN_KEY_BINDING = "SecureConversationTokenKeyBinding";
/*     */   public static final String MANDATORY_TARGET_POLICY_TYPE = "MandatoryTargetPolicy";
/*     */   public static final String MANDATORY_TARGET_FEATUREBINDING_TYPE = "MandatoryTargetPolicy.FeatureBinding";
/*     */   
/*     */   public static boolean isPrimaryPolicy(WSSPolicy policy) {
/*  89 */     if (policy == null) return false;
/*     */     
/*  91 */     if (signaturePolicy((SecurityPolicy)policy) || encryptionPolicy((SecurityPolicy)policy)) {
/*  92 */       return true;
/*     */     }
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSecondaryPolicy(WSSPolicy policy) {
/*  98 */     if (policy == null) return false; 
/*  99 */     if (authenticationTokenPolicy((SecurityPolicy)policy) || timestampPolicy((SecurityPolicy)policy)) {
/* 100 */       return true;
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean signaturePolicyFeatureBinding(SecurityPolicy policy) {
/* 106 */     if (policy == null) return false; 
/* 107 */     return (policy.getType() == "SignaturePolicy.FeatureBinding");
/*     */   }
/*     */   
/*     */   public static boolean encryptionPolicyFeatureBinding(SecurityPolicy policy) {
/* 111 */     if (policy == null) return false; 
/* 112 */     return (policy.getType() == "EncryptionPolicy.FeatureBinding");
/*     */   }
/*     */   
/*     */   public static boolean privateKeyBinding(SecurityPolicy policy) {
/* 116 */     if (policy == null) return false; 
/* 117 */     return (policy.getType() == "PrivateKeyBinding");
/*     */   }
/*     */   
/*     */   public static boolean encryptionPolicy(SecurityPolicy policy) {
/* 121 */     if (policy == null) return false; 
/* 122 */     return (policy.getType() == "EncryptionPolicy");
/*     */   }
/*     */   
/*     */   public static boolean signaturePolicy(SecurityPolicy policy) {
/* 126 */     if (policy == null) return false; 
/* 127 */     return (policy.getType() == "SignaturePolicy");
/*     */   }
/*     */   
/*     */   public static boolean timestampPolicy(SecurityPolicy policy) {
/* 131 */     if (policy == null) return false; 
/* 132 */     return (policy.getType() == "TimestampPolicy");
/*     */   }
/*     */   
/*     */   public static boolean signatureConfirmationPolicy(SecurityPolicy policy) {
/* 136 */     if (policy == null) return false; 
/* 137 */     return (policy.getType() == "SignatureConfirmationPolicy");
/*     */   }
/*     */   
/*     */   public static boolean authenticationTokenPolicy(SecurityPolicy policy) {
/* 141 */     if (policy == null) return false; 
/* 142 */     return (policy.getType() == "AuthenticationTokenPolicy");
/*     */   }
/*     */   
/*     */   public static boolean usernameTokenPolicy(SecurityPolicy policy) {
/* 146 */     if (policy == null) return false; 
/* 147 */     return (policy.getType() == "UsernameTokenBinding");
/*     */   }
/*     */   
/*     */   public static boolean usernameTokenBinding(SecurityPolicy policy) {
/* 151 */     if (policy == null) return false; 
/* 152 */     return (policy.getType() == "UsernameTokenBinding");
/*     */   }
/*     */   
/*     */   public static boolean x509CertificateBinding(SecurityPolicy policy) {
/* 156 */     if (policy == null) return false; 
/* 157 */     return (policy.getType() == "X509CertificateBinding");
/*     */   }
/*     */   
/*     */   public static boolean keyValueTokenBinding(SecurityPolicy policy) {
/* 161 */     if (policy == null) return false; 
/* 162 */     return (policy.getType() == "RsaTokenBinding");
/*     */   }
/*     */   
/*     */   public static boolean kerberosTokenBinding(SecurityPolicy policy) {
/* 166 */     if (policy == null) return false; 
/* 167 */     return (policy.getType() == "KerberosTokenBinding");
/*     */   }
/*     */   
/*     */   public static boolean samlTokenPolicy(SecurityPolicy policy) {
/* 171 */     if (policy == null) return false; 
/* 172 */     return (policy.getType() == "SAMLAssertionBinding");
/*     */   }
/*     */   
/*     */   public static boolean symmetricKeyBinding(SecurityPolicy policy) {
/* 176 */     if (policy == null) return false; 
/* 177 */     return (policy.getType() == "SymmetricKeyBinding");
/*     */   }
/*     */   
/*     */   public static boolean booleanComposerPolicy(SecurityPolicy policy) {
/* 181 */     if (policy == null) return false; 
/* 182 */     return (policy.getType() == "BooleanComposer");
/*     */   }
/*     */   
/*     */   public static boolean dynamicSecurityPolicy(SecurityPolicy policy) {
/* 186 */     if (policy == null) return false; 
/* 187 */     return (policy.getType() == "DynamicSecurityPolicy");
/*     */   }
/*     */   
/*     */   public static boolean messagePolicy(SecurityPolicy policy) {
/* 191 */     if (policy == null) return false; 
/* 192 */     return (policy.getType() == "MessagePolicy");
/*     */   }
/*     */   
/*     */   public static boolean applicationSecurityConfiguration(SecurityPolicy policy) {
/* 196 */     if (policy == null) return false; 
/* 197 */     return (policy.getType() == "ApplicationSecurityConfiguration");
/*     */   }
/*     */   
/*     */   public static boolean declarativeSecurityConfiguration(SecurityPolicy policy) {
/* 201 */     if (policy == null) return false; 
/* 202 */     return (policy.getType() == "DeclarativeSecurityConfiguration");
/*     */   }
/*     */   
/*     */   public static boolean derivedTokenKeyBinding(SecurityPolicy policy) {
/* 206 */     if (policy == null) return false; 
/* 207 */     return (policy.getType() == "DerivedTokenKeyBinding");
/*     */   }
/*     */   
/*     */   public String getTIMESTAMP_POLICY_TYPE() {
/* 211 */     return "TimestampPolicy";
/*     */   }
/*     */   
/*     */   public static boolean issuedTokenKeyBinding(SecurityPolicy policy) {
/* 215 */     if (policy == null) return false; 
/* 216 */     return (policy.getType() == "IssuedTokenKeyBinding");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean secureConversationTokenKeyBinding(SecurityPolicy policy) {
/* 221 */     if (policy == null) return false; 
/* 222 */     return (policy.getType() == "SecureConversationTokenKeyBinding");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMandatoryTargetPolicy(SecurityPolicy policy) {
/* 227 */     if (policy == null) return false; 
/* 228 */     return (policy.getType() == "MandatoryTargetPolicy");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\PolicyTypeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */