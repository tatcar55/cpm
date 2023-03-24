/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
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
/*     */ public class SecurityPolicyUtil
/*     */ {
/*  65 */   private static final QName signaturePolicy = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*  66 */   private static final QName usernameTokenPolicy = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
/*  67 */   private static final QName x509TokenPolicy = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
/*  68 */   private static final QName timestampPolicy = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSignedPartsEmpty(SignedParts sp) {
/*  75 */     if (!sp.hasBody() && !sp.hasAttachments() && 
/*  76 */       !sp.getHeaders().hasNext()) {
/*  77 */       return true;
/*     */     }
/*     */     
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isEncryptedPartsEmpty(EncryptedParts ep) {
/*  84 */     if (!ep.hasBody() && !ep.hasAttachments() && 
/*  85 */       !ep.getTargets().hasNext()) {
/*  86 */       return true;
/*     */     }
/*     */     
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public static String convertToXWSSConstants(String type) {
/*  93 */     if (type.contains("RequireThumbprintReference"))
/*  94 */       return "Thumbprint"; 
/*  95 */     if (type.contains("RequireKeyIdentifierReference"))
/*  96 */       return "Identifier"; 
/*  97 */     if (type.contains("RequireIssuerSerialReference")) {
/*  98 */       return "IssuerSerialNumber";
/*     */     }
/* 100 */     throw new UnsupportedOperationException(type + "  is not supported");
/*     */   }
/*     */   
/*     */   public static void setName(Target target, WSSPolicy policy) {
/* 104 */     if (target.getType() == "uri") {
/* 105 */       target.setPolicyQName(getQNameFromPolicy(policy));
/*     */     }
/*     */   }
/*     */   
/*     */   private static QName getQNameFromPolicy(WSSPolicy policy) {
/* 110 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy))
/* 111 */       return signaturePolicy; 
/* 112 */     if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)policy))
/* 113 */       return timestampPolicy; 
/* 114 */     if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)policy))
/* 115 */       return x509TokenPolicy; 
/* 116 */     if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)policy))
/* 117 */       return usernameTokenPolicy; 
/* 118 */     if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)policy))
/* 119 */       return MessageConstants.SCT_NAME; 
/* 120 */     if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)policy)) {
/* 121 */       return new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SAMLToken");
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public static void setCanonicalizationMethod(SignaturePolicy.FeatureBinding spFB, AlgorithmSuite algorithmSuite) {
/* 127 */     if (algorithmSuite != null && algorithmSuite.getAdditionalProps().contains("InclusiveC14N")) {
/* 128 */       spFB.setCanonicalizationAlgorithm("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
/*     */     } else {
/* 130 */       spFB.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */     } 
/*     */     
/* 133 */     if (algorithmSuite != null && algorithmSuite.getAdditionalProps().contains("InclusiveC14NWithCommentsForCm")) {
/* 134 */       spFB.setCanonicalizationAlgorithm("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
/* 135 */     } else if (algorithmSuite != null && algorithmSuite.getAdditionalProps().contains("ExclusiveC14NWithCommentsForCm")) {
/* 136 */       spFB.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#WithComments");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SecurityPolicyVersion getSPVersion(PolicyAssertion pa) {
/* 141 */     String nsUri = pa.getName().getNamespaceURI();
/* 142 */     SecurityPolicyVersion spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/* 143 */     return spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SecurityPolicyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */