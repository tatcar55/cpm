/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.EncryptionProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.HarnessUtil;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.apachecrypto.DecryptionProcessor;
/*     */ import com.sun.xml.wss.impl.apachecrypto.EncryptionProcessor;
/*     */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*     */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptionFilter
/*     */ {
/* 115 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.filter", "com.sun.xml.wss.logging.impl.filter.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticationTokenPolicy.UsernameTokenBinding createUntBinding(FilterProcessingContext context, AuthenticationTokenPolicy.UsernameTokenBinding untBinding) throws XWSSecurityException {
/* 129 */     AuthenticationTokenPolicy.UsernameTokenBinding binding = (AuthenticationTokenPolicy.UsernameTokenBinding)untBinding.clone();
/* 130 */     JAXBFilterProcessingContext opContext = (JAXBFilterProcessingContext)context;
/* 131 */     EncryptionPolicy encPolicy = (EncryptionPolicy)context.getSecurityPolicy();
/*     */ 
/*     */     
/* 134 */     UsernameToken unToken = null;
/* 135 */     if (context.getusernameTokenBinding() == null) {
/* 136 */       unToken = new UsernameToken(opContext.getSOAPVersion());
/*     */     }
/* 138 */     else if (untBinding.getUUID().equals(context.getusernameTokenBinding().getUUID())) {
/* 139 */       unToken = context.getusernameTokenBinding().getUsernameToken();
/*     */     } else {
/* 141 */       unToken = new UsernameToken(opContext.getSOAPVersion());
/*     */     } 
/*     */     
/*     */     try {
/* 145 */       binding = UsernameTokenDataResolver.setSaltandIterationsforUsernameToken((FilterProcessingContext)opContext, unToken, encPolicy, binding);
/* 146 */     } catch (UnsupportedEncodingException ex) {
/* 147 */       throw new XWSSecurityException("error occurred while decoding the salt in username token", ex);
/* 148 */     } catch (XWSSecurityException ex) {
/* 149 */       throw ex;
/*     */     } 
/* 151 */     if (binding.getUseNonce() && unToken.getNonceValue() == null) {
/* 152 */       unToken.setNonce(binding.getNonce());
/* 153 */       String creationTime = "";
/* 154 */       TimestampPolicy tPolicy = (TimestampPolicy)binding.getFeatureBinding();
/* 155 */       if (tPolicy == null) tPolicy = (TimestampPolicy)binding.newTimestampFeatureBinding(); 
/* 156 */       creationTime = tPolicy.getCreationTime();
/* 157 */       unToken.setCreationTime(creationTime);
/*     */     } 
/* 159 */     if (binding.getUseCreated() && unToken.getCreatedValue() == null) {
/* 160 */       String creationTime = "";
/* 161 */       TimestampPolicy tPolicy = (TimestampPolicy)binding.getFeatureBinding();
/* 162 */       if (tPolicy == null) tPolicy = (TimestampPolicy)binding.newTimestampFeatureBinding(); 
/* 163 */       creationTime = tPolicy.getCreationTime();
/* 164 */       unToken.setCreationTime(creationTime);
/*     */     } 
/* 166 */     binding.setUsernameToken(unToken);
/* 167 */     String dataEncAlgo = null;
/* 168 */     if (context.getAlgorithmSuite() != null) {
/* 169 */       dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */     } else {
/* 171 */       dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
/*     */     } 
/* 173 */     SecretKey sKey = binding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 174 */     binding.setSecretKey(sKey);
/* 175 */     return binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(FilterProcessingContext context) throws XWSSecurityException {
/* 185 */     if (!context.isInboundMessage()) {
/*     */       
/* 187 */       EncryptionPolicy policy = (EncryptionPolicy)context.getSecurityPolicy();
/* 188 */       EncryptionPolicy resolvedPolicy = policy;
/*     */       
/* 190 */       boolean wss11Receiver = "true".equals(context.getExtraneousProperty("EnableWSS11PolicyReceiver"));
/* 191 */       boolean wss11Sender = "true".equals(context.getExtraneousProperty("EnableWSS11PolicySender"));
/* 192 */       boolean sendEKSHA1 = (wss11Receiver && wss11Sender && getReceivedSecret(context) != null);
/* 193 */       boolean wss10 = !wss11Sender;
/*     */       
/* 195 */       if (!context.makeDynamicPolicyCallback()) {
/* 196 */         DerivedTokenKeyBinding derivedTokenKeyBinding; WSSPolicy keyBinding = (WSSPolicy)policy.getKeyBinding();
/* 197 */         String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*     */         
/* 199 */         EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */         
/* 201 */         String tmp = featureBinding.getDataEncryptionAlgorithm();
/* 202 */         if ((tmp == null || "".equals(tmp)) && 
/* 203 */           context.getAlgorithmSuite() != null) {
/* 204 */           tmp = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 210 */         if (tmp != null && !"".equals(tmp)) {
/* 211 */           dataEncAlgo = tmp;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 216 */         if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/* 217 */           DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)keyBinding.clone();
/* 218 */           WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*     */           
/* 220 */           if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)originalKeyBinding)) {
/* 221 */             AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)originalKeyBinding.clone();
/*     */ 
/*     */             
/* 224 */             SymmetricKeyBinding skb = new SymmetricKeyBinding();
/* 225 */             skb.setKeyBinding((MLSPolicy)ckBindingClone);
/*     */             
/* 227 */             dtk.setOriginalKeyBinding((WSSPolicy)skb);
/* 228 */             derivedTokenKeyBinding = dtk;
/* 229 */           } else if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)originalKeyBinding)) {
/* 230 */             AuthenticationTokenPolicy.UsernameTokenBinding ckBindingClone = (AuthenticationTokenPolicy.UsernameTokenBinding)originalKeyBinding.clone();
/*     */             
/* 232 */             SymmetricKeyBinding skb = new SymmetricKeyBinding();
/* 233 */             skb.setKeyBinding((MLSPolicy)ckBindingClone);
/* 234 */             dtk.setOriginalKeyBinding((WSSPolicy)skb);
/* 235 */             derivedTokenKeyBinding = dtk;
/*     */           } 
/*     */         } 
/* 238 */         if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)derivedTokenKeyBinding))
/* 239 */         { AuthenticationTokenPolicy.UsernameTokenBinding binding = createUntBinding(context, (AuthenticationTokenPolicy.UsernameTokenBinding)derivedTokenKeyBinding);
/* 240 */           context.setUsernameTokenBinding(binding); }
/* 241 */         else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)derivedTokenKeyBinding))
/*     */         { try {
/* 243 */             AuthenticationTokenPolicy.X509CertificateBinding binding = (AuthenticationTokenPolicy.X509CertificateBinding)derivedTokenKeyBinding.clone();
/*     */ 
/*     */             
/* 246 */             String certIdentifier = binding.getCertificateIdentifier();
/*     */             
/* 248 */             X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, false);
/*     */             
/* 250 */             binding.setX509Certificate(cert);
/*     */             
/* 252 */             context.setX509CertificateBinding(binding);
/*     */           }
/* 254 */           catch (Exception e) {
/* 255 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1413_ERROR_EXTRACTING_CERTIFICATE(), e);
/* 256 */             throw new XWSSecurityException(e);
/*     */           }  }
/* 258 */         else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)derivedTokenKeyBinding))
/* 259 */         { AuthenticationTokenPolicy.KerberosTokenBinding binding = (AuthenticationTokenPolicy.KerberosTokenBinding)derivedTokenKeyBinding.clone();
/*     */           
/* 261 */           String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/* 262 */           KerberosContext krbContext = null;
/* 263 */           if (encodedRef != null) {
/* 264 */             krbContext = context.getKerberosContext();
/*     */           }
/* 266 */           if (krbContext != null) {
/* 267 */             byte[] kerberosToken = krbContext.getKerberosToken();
/* 268 */             binding.setTokenValue(kerberosToken);
/*     */             
/* 270 */             SecretKey sKey = krbContext.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 271 */             binding.setSecretKey(sKey);
/*     */           } else {
/* 273 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1423_KERBEROS_CONTEXT_NOTSET());
/* 274 */             throw new XWSSecurityException("WSS1423.kerberos.context.notset");
/*     */           } 
/* 276 */           context.setKerberosTokenBinding(binding); }
/* 277 */         else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)derivedTokenKeyBinding))
/*     */         { try {
/* 279 */             SymmetricKeyBinding binding = (SymmetricKeyBinding)derivedTokenKeyBinding.clone();
/*     */             
/* 281 */             String keyIdentifier = binding.getKeyIdentifier();
/* 282 */             SecretKey sKey = null;
/*     */             
/* 284 */             WSSPolicy ckBinding = (WSSPolicy)binding.getKeyBinding();
/* 285 */             if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)ckBinding)) {
/* 286 */               if (!sendEKSHA1) {
/* 287 */                 AuthenticationTokenPolicy.UsernameTokenBinding untbinding = createUntBinding(context, (AuthenticationTokenPolicy.UsernameTokenBinding)ckBinding);
/* 288 */                 context.setUsernameTokenBinding(untbinding);
/*     */               } 
/* 290 */             } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)ckBinding)) {
/*     */               try {
/* 292 */                 if (!sendEKSHA1) {
/* 293 */                   AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)ckBinding.clone();
/*     */                   
/* 295 */                   String certIdentifier = ckBindingClone.getCertificateIdentifier();
/* 296 */                   X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, false);
/*     */                   
/* 298 */                   ckBindingClone.setX509Certificate(cert);
/* 299 */                   context.setX509CertificateBinding(ckBindingClone);
/*     */                 } 
/* 301 */               } catch (Exception e) {
/* 302 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1413_ERROR_EXTRACTING_CERTIFICATE(), e);
/* 303 */                 throw new XWSSecurityException(e);
/*     */               } 
/* 305 */             } else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 306 */               AuthenticationTokenPolicy.KerberosTokenBinding ckBindingClone = (AuthenticationTokenPolicy.KerberosTokenBinding)ckBinding;
/*     */ 
/*     */               
/* 309 */               String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/* 310 */               KerberosContext krbContext = null;
/* 311 */               if (encodedRef != null) {
/* 312 */                 krbContext = context.getKerberosContext();
/*     */               }
/* 314 */               if (krbContext != null) {
/* 315 */                 byte[] kerberosToken = krbContext.getKerberosToken();
/* 316 */                 ckBindingClone.setTokenValue(kerberosToken);
/* 317 */                 sKey = krbContext.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 318 */                 ckBindingClone.setSecretKey(sKey);
/*     */               } else {
/* 320 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1423_KERBEROS_CONTEXT_NOTSET());
/* 321 */                 throw new XWSSecurityException("WSS1423.kerberos.context.notset");
/*     */               } 
/* 323 */               context.setKerberosTokenBinding(ckBindingClone);
/*     */             } 
/*     */             
/* 326 */             if (!PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 327 */               if (!keyIdentifier.equals(MessageConstants._EMPTY)) {
/* 328 */                 sKey = context.getSecurityEnvironment().getSecretKey(context.getExtraneousProperties(), keyIdentifier, true);
/*     */               
/*     */               }
/* 331 */               else if (sendEKSHA1) {
/* 332 */                 sKey = getReceivedSecret(context);
/* 333 */               } else if (wss11Sender || wss10) {
/* 334 */                 sKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*     */               } 
/*     */             }
/*     */             
/* 338 */             binding.setSecretKey(sKey);
/* 339 */             context.setSymmetricKeyBinding(binding);
/* 340 */           } catch (Exception e) {
/*     */             
/* 342 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1414_ERROR_EXTRACTING_SYMMETRICKEY(new Object[] { e.getMessage() }));
/* 343 */             throw new XWSSecurityException(e);
/*     */           }  }
/* 345 */         else { WSSPolicy wSSPolicy; if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)derivedTokenKeyBinding)) {
/*     */ 
/*     */             
/* 348 */             wSSPolicy = (WSSPolicy)policy.getKeyBinding();
/*     */             
/* 350 */             DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */             
/* 352 */             dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 353 */             dynamicContext.inBoundMessage(false);
/*     */             
/* 355 */             AuthenticationTokenPolicy.SAMLAssertionBinding binding = (AuthenticationTokenPolicy.SAMLAssertionBinding)wSSPolicy;
/*     */             
/* 357 */             binding.isReadOnly(true);
/*     */             
/* 359 */             AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/*     */ 
/*     */             
/* 362 */             if (context.getExtraneousProperty("incoming_saml_assertion") == null) {
/* 363 */               AuthenticationTokenPolicy.SAMLAssertionBinding resolvedSAMLBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)context.getExtraneousProperties().get("Saml_Assertion_Client_Cache");
/*     */ 
/*     */               
/* 366 */               if (resolvedSAMLBinding == null) {
/*     */                 
/* 368 */                 resolvedSAMLBinding = context.getSecurityEnvironment().populateSAMLPolicy(context.getExtraneousProperties(), binding, dynamicContext);
/*     */                 
/* 370 */                 context.getExtraneousProperties().put("Saml_Assertion_Client_Cache", resolvedSAMLBinding);
/* 371 */                 samlBinding = resolvedSAMLBinding;
/*     */               } 
/*     */             } else {
/* 374 */               Object assertion = context.getExtraneousProperty("incoming_saml_assertion");
/* 375 */               if (assertion instanceof Element) {
/* 376 */                 samlBinding.setAssertion((Element)assertion);
/*     */               }
/*     */             } 
/*     */             
/* 380 */             policy.setKeyBinding((MLSPolicy)samlBinding);
/* 381 */             resolvedPolicy = policy;
/* 382 */           } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)wSSPolicy)) {
/*     */             
/* 384 */             SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)wSSPolicy;
/* 385 */             SecurityUtil.resolveSCT(context, sctBinding);
/*     */           }
/* 387 */           else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)wSSPolicy)) {
/* 388 */             IssuedTokenKeyBinding itkb = (IssuedTokenKeyBinding)wSSPolicy;
/* 389 */             SecurityUtil.resolveIssuedToken(context, itkb);
/* 390 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)wSSPolicy)) {
/* 391 */             DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)wSSPolicy.clone();
/* 392 */             WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*     */             
/* 394 */             if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)originalKeyBinding)) {
/* 395 */               SymmetricKeyBinding symmBinding = (SymmetricKeyBinding)originalKeyBinding.clone();
/* 396 */               SecretKey sKey = null;
/*     */               
/* 398 */               WSSPolicy ckBinding = (WSSPolicy)originalKeyBinding.getKeyBinding();
/* 399 */               if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)ckBinding)) {
/*     */                 try {
/* 401 */                   if (!sendEKSHA1) {
/* 402 */                     AuthenticationTokenPolicy.UsernameTokenBinding untbinding = createUntBinding(context, (AuthenticationTokenPolicy.UsernameTokenBinding)ckBinding);
/* 403 */                     context.setUsernameTokenBinding(untbinding);
/*     */                   } 
/* 405 */                 } catch (Exception e) {
/* 406 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1433_ERROR_EXTRACTING_USERNAMETOKEN(), e);
/* 407 */                   throw new XWSSecurityException(e);
/*     */                 } 
/*     */               }
/* 410 */               if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)ckBinding)) {
/*     */                 try {
/* 412 */                   if (!sendEKSHA1) {
/* 413 */                     AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)ckBinding.clone();
/*     */                     
/* 415 */                     String certIdentifier = ckBindingClone.getCertificateIdentifier();
/* 416 */                     X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, false);
/*     */                     
/* 418 */                     ckBindingClone.setX509Certificate(cert);
/* 419 */                     context.setX509CertificateBinding(ckBindingClone);
/*     */                   } 
/* 421 */                 } catch (Exception e) {
/* 422 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1413_ERROR_EXTRACTING_CERTIFICATE(), e);
/* 423 */                   throw new XWSSecurityException(e);
/*     */                 } 
/* 425 */               } else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 426 */                 AuthenticationTokenPolicy.KerberosTokenBinding ckBindingClone = (AuthenticationTokenPolicy.KerberosTokenBinding)ckBinding;
/*     */                 
/* 428 */                 String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/* 429 */                 KerberosContext krbContext = null;
/* 430 */                 if (encodedRef != null) {
/* 431 */                   krbContext = context.getKerberosContext();
/*     */                 }
/* 433 */                 if (krbContext != null) {
/* 434 */                   byte[] kerberosToken = krbContext.getKerberosToken();
/* 435 */                   ckBindingClone.setTokenValue(kerberosToken);
/* 436 */                   sKey = krbContext.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 437 */                   ckBindingClone.setSecretKey(sKey);
/*     */                 } else {
/* 439 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1423_KERBEROS_CONTEXT_NOTSET());
/* 440 */                   throw new XWSSecurityException("WSS1423.kerberos.context.notset");
/*     */                 } 
/* 442 */                 context.setKerberosTokenBinding(ckBindingClone);
/*     */               } 
/*     */               
/* 445 */               if (!PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 446 */                 if (sendEKSHA1) {
/* 447 */                   sKey = getReceivedSecret(context);
/* 448 */                 } else if (wss11Sender || wss10) {
/* 449 */                   sKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*     */                 } 
/*     */               }
/* 452 */               symmBinding.setSecretKey(sKey);
/* 453 */               context.setSymmetricKeyBinding(symmBinding);
/* 454 */             } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*     */               
/* 456 */               SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)originalKeyBinding;
/* 457 */               SecurityUtil.resolveSCT(context, sctBinding);
/* 458 */             } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/* 459 */               IssuedTokenKeyBinding itkb = (IssuedTokenKeyBinding)originalKeyBinding;
/* 460 */               SecurityUtil.resolveIssuedToken(context, itkb);
/*     */             } 
/*     */           } else {
/* 463 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1422_UNSUPPORTED_KEYBINDING_ENCRYPTION_POLICY());
/* 464 */             throw new XWSSecurityException("Unsupported KeyBinding for EncryptionPolicy");
/*     */           }  }
/*     */       
/*     */       } else {
/*     */         
/*     */         try {
/* 470 */           policy.isReadOnly(true);
/*     */ 
/*     */           
/* 473 */           DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */           
/* 475 */           dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 476 */           dynamicContext.inBoundMessage(false);
/*     */           
/* 478 */           DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)policy, (DynamicPolicyContext)dynamicContext);
/*     */           
/* 480 */           ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 481 */           HarnessUtil.makeDynamicPolicyCallback(dynamicCallback, context.getSecurityEnvironment().getCallbackHandler());
/*     */ 
/*     */           
/* 484 */           resolvedPolicy = (EncryptionPolicy)dynamicCallback.getSecurityPolicy();
/*     */         }
/* 486 */         catch (Exception e) {
/* 487 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1412_ERROR_PROCESSING_DYNAMICPOLICY(new Object[] { e.getMessage() }));
/* 488 */           throw new XWSSecurityException(e);
/*     */         } 
/*     */       } 
/*     */       
/* 492 */       context.setSecurityPolicy((SecurityPolicy)resolvedPolicy);
/* 493 */       encrypt(context);
/*     */     }
/*     */     else {
/*     */       
/* 497 */       if (context.makeDynamicPolicyCallback()) {
/* 498 */         WSSPolicy policy = (WSSPolicy)context.getSecurityPolicy();
/* 499 */         EncryptionPolicy resolvedPolicy = null;
/*     */         
/*     */         try {
/* 502 */           ((EncryptionPolicy)policy).isReadOnly(true);
/* 503 */           DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */           
/* 506 */           dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 507 */           dynamicContext.inBoundMessage(true);
/*     */           
/* 509 */           DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)policy, (DynamicPolicyContext)dynamicContext);
/*     */           
/* 511 */           ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 512 */           HarnessUtil.makeDynamicPolicyCallback(dynamicCallback, context.getSecurityEnvironment().getCallbackHandler());
/*     */ 
/*     */           
/* 515 */           resolvedPolicy = (EncryptionPolicy)dynamicCallback.getSecurityPolicy();
/*     */         }
/* 517 */         catch (Exception e) {
/* 518 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1420_DYNAMIC_POLICY_SIGNATURE(new Object[] { e.getMessage() }));
/* 519 */           throw new XWSSecurityException(e);
/*     */         } 
/* 521 */         context.setSecurityPolicy((SecurityPolicy)resolvedPolicy);
/*     */       } 
/*     */       
/* 524 */       DecryptionProcessor.decrypt(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void encrypt(FilterProcessingContext context) throws XWSSecurityException {
/* 534 */     if (context instanceof JAXBFilterProcessingContext) {
/* 535 */       (new EncryptionProcessor()).process((JAXBFilterProcessingContext)context);
/*     */     } else {
/* 537 */       EncryptionProcessor.encrypt(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SecretKey getReceivedSecret(FilterProcessingContext context) {
/* 546 */     SecretKey sKey = null;
/* 547 */     sKey = (SecretKey)context.getExtraneousProperty("SecretKeyValue");
/* 548 */     return sKey;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\EncryptionFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */