/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityPolicyAssertionCreator
/*     */   implements PolicyAssertionCreator
/*     */ {
/*  63 */   private static HashSet<String> implementedAssertions = new HashSet<String>();
/*  64 */   private static final String[] nsSupportedList = new String[] { SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "http://schemas.microsoft.com/ws/2005/07/securitypolicy", SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  73 */     implementedAssertions.add("AlgorithmSuite");
/*  74 */     implementedAssertions.add("AsymmetricBinding");
/*  75 */     implementedAssertions.add("Address");
/*  76 */     implementedAssertions.add("EncryptedElements");
/*  77 */     implementedAssertions.add("EncryptedParts");
/*  78 */     implementedAssertions.add("EncryptionToken");
/*  79 */     implementedAssertions.add("EndorsingSupportingTokens");
/*  80 */     implementedAssertions.add("EndpointReference");
/*     */     
/*  82 */     implementedAssertions.add("Header");
/*  83 */     implementedAssertions.add("HttpsToken");
/*  84 */     implementedAssertions.add("IssuedToken");
/*  85 */     implementedAssertions.add("Issuer");
/*  86 */     implementedAssertions.add("InitiatorToken");
/*  87 */     implementedAssertions.add("InitiatorSignatureToken");
/*  88 */     implementedAssertions.add("InitiatorEncryptionToken");
/*     */     
/*  90 */     implementedAssertions.add("KerberosToken");
/*     */     
/*  92 */     implementedAssertions.add("Lifetime");
/*  93 */     implementedAssertions.add("Layout");
/*     */     
/*  95 */     implementedAssertions.add("ProtectionToken");
/*     */     
/*  97 */     implementedAssertions.add("RecipientToken");
/*  98 */     implementedAssertions.add("RecipientSignatureToken");
/*  99 */     implementedAssertions.add("RecipientEncryptionToken");
/* 100 */     implementedAssertions.add("RelToken");
/* 101 */     implementedAssertions.add("RequestSecurityTokenTemplate");
/* 102 */     implementedAssertions.add("RequiredElements");
/*     */     
/* 104 */     implementedAssertions.add("SamlToken");
/* 105 */     implementedAssertions.add("SecurityContextToken");
/* 106 */     implementedAssertions.add("SecureConversationToken");
/* 107 */     implementedAssertions.add("SignedElements");
/* 108 */     implementedAssertions.add("SignedSupportingTokens");
/* 109 */     implementedAssertions.add("SignedEndorsingSupportingTokens");
/* 110 */     implementedAssertions.add("SignedParts");
/* 111 */     implementedAssertions.add("SpnegoContextToken");
/* 112 */     implementedAssertions.add("SupportingTokens");
/* 113 */     implementedAssertions.add("SignatureToken");
/* 114 */     implementedAssertions.add("SymmetricBinding");
/*     */     
/* 116 */     implementedAssertions.add("TransportBinding");
/* 117 */     implementedAssertions.add("TransportToken");
/* 118 */     implementedAssertions.add("Trust10");
/*     */     
/* 120 */     implementedAssertions.add("UsernameToken");
/* 121 */     implementedAssertions.add("UseKey");
/*     */     
/* 123 */     implementedAssertions.add("Wss10");
/* 124 */     implementedAssertions.add("Wss11");
/* 125 */     implementedAssertions.add("X509Token");
/* 126 */     implementedAssertions.add("KeyStore");
/* 127 */     implementedAssertions.add("SessionManagerStore");
/* 128 */     implementedAssertions.add("TrustStore");
/* 129 */     implementedAssertions.add("CallbackHandler");
/* 130 */     implementedAssertions.add("CallbackHandlerConfiguration");
/* 131 */     implementedAssertions.add("Validator");
/* 132 */     implementedAssertions.add("ValidatorConfiguration");
/* 133 */     implementedAssertions.add("CertStore");
/* 134 */     implementedAssertions.add("KerberosConfig");
/* 135 */     implementedAssertions.add("RsaToken");
/*     */ 
/*     */     
/* 138 */     implementedAssertions.add("KeyValueToken");
/* 139 */     implementedAssertions.add("EncryptedSupportingTokens");
/* 140 */     implementedAssertions.add("SignedEncryptedSupportingTokens");
/* 141 */     implementedAssertions.add("SignedEndorsingEncryptedSupportingTokens");
/* 142 */     implementedAssertions.add("EndorsingEncryptedSupportingTokens");
/* 143 */     implementedAssertions.add("Trust13");
/* 144 */     implementedAssertions.add("IssuerName");
/* 145 */     implementedAssertions.add("Claims");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getSupportedDomainNamespaceURIs() {
/* 156 */     return nsSupportedList;
/*     */   }
/*     */   protected Class getClass(AssertionData assertionData) throws AssertionCreationException {
/* 159 */     String className = "";
/*     */     try {
/* 161 */       className = assertionData.getName().getLocalPart();
/*     */ 
/*     */       
/* 164 */       if ("CertStore".equals(className)) {
/* 165 */         className = "CertStoreConfig";
/*     */       }
/* 167 */       return Class.forName("com.sun.xml.ws.security.impl.policy." + className);
/* 168 */     } catch (ClassNotFoundException ex) {
/* 169 */       if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 170 */         Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0110_ERROR_LOCATING_CLASS("com.sun.xml.ws.security.impl.policy" + className), ex);
/*     */       }
/* 172 */       throw new AssertionCreationException(assertionData, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PolicyAssertion createAssertion(AssertionData assertionData, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative, PolicyAssertionCreator policyAssertionCreator) throws AssertionCreationException {
/* 177 */     String localName = assertionData.getName().getLocalPart();
/* 178 */     if (implementedAssertions.contains(localName)) {
/* 179 */       Class<PolicyAssertion> cl = null;
/* 180 */       cl = getClass(assertionData);
/*     */       
/* 182 */       Constructor<PolicyAssertion> cons = null;
/*     */       
/*     */       try {
/* 185 */         cons = getConstructor(cl);
/*     */       
/*     */       }
/* 188 */       catch (NoSuchMethodException ex) {
/* 189 */         if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 190 */           Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0111_ERROR_OBTAINING_CONSTRUCTOR(assertionData.getName()), ex);
/*     */         }
/* 192 */         throw new AssertionCreationException(assertionData, ex);
/* 193 */       } catch (SecurityException ex) {
/* 194 */         if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 195 */           Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0111_ERROR_OBTAINING_CONSTRUCTOR(assertionData.getName()), ex);
/*     */         }
/*     */ 
/*     */         
/* 199 */         throw new AssertionCreationException(assertionData, ex);
/*     */       } 
/* 201 */       if (cons != null) {
/*     */         try {
/* 203 */           return cons.newInstance(new Object[] { assertionData, nestedAssertions, nestedAlternative });
/* 204 */         } catch (IllegalArgumentException ex) {
/* 205 */           if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 206 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0112_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/*     */           
/* 209 */           throw new AssertionCreationException(assertionData, ex);
/* 210 */         } catch (InvocationTargetException ex) {
/* 211 */           if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 212 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0112_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/* 214 */           throw new AssertionCreationException(assertionData, ex);
/* 215 */         } catch (InstantiationException ex) {
/* 216 */           if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 217 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0112_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/* 219 */           throw new AssertionCreationException(assertionData, ex);
/* 220 */         } catch (IllegalAccessException ex) {
/* 221 */           if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 222 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0112_ERROR_INSTANTIATING(assertionData.getName()));
/*     */           }
/* 224 */           throw new AssertionCreationException(assertionData, ex);
/*     */         } 
/*     */       }
/*     */       try {
/* 228 */         return cl.newInstance();
/* 229 */       } catch (InstantiationException ex) {
/* 230 */         if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 231 */           Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0112_ERROR_INSTANTIATING(assertionData.getName()));
/*     */         }
/* 233 */         throw new AssertionCreationException(assertionData, ex);
/* 234 */       } catch (IllegalAccessException ex) {
/* 235 */         if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 236 */           Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0112_ERROR_INSTANTIATING(assertionData.getName()));
/*     */         }
/* 238 */         throw new AssertionCreationException(assertionData, ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     return policyAssertionCreator.createAssertion(assertionData, nestedAssertions, nestedAlternative, policyAssertionCreator);
/*     */   }
/*     */ 
/*     */   
/*     */   private Constructor getConstructor(Class cl) throws NoSuchMethodException {
/* 249 */     return cl.getConstructor(new Class[] { AssertionData.class, Collection.class, AssertionSet.class });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecurityPolicyAssertionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */