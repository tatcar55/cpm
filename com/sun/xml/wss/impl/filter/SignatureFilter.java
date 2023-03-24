/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.SignatureProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.HarnessUtil;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*     */ import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
/*     */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*     */ import com.sun.xml.wss.impl.dsig.SignatureProcessor;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.PrivateKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureFilter
/*     */ {
/* 122 */   private static Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.filter", "com.sun.xml.wss.logging.impl.filter.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticationTokenPolicy.UsernameTokenBinding createUntBinding(FilterProcessingContext context, AuthenticationTokenPolicy.UsernameTokenBinding untBinding, int firstByte) throws XWSSecurityException {
/* 136 */     AuthenticationTokenPolicy.UsernameTokenBinding binding = (AuthenticationTokenPolicy.UsernameTokenBinding)untBinding.clone();
/* 137 */     JAXBFilterProcessingContext opContext = (JAXBFilterProcessingContext)context;
/* 138 */     SignaturePolicy authPolicy = (SignaturePolicy)context.getSecurityPolicy();
/* 139 */     UsernameToken unToken = null;
/* 140 */     if (context.getusernameTokenBinding() == null) {
/* 141 */       unToken = new UsernameToken(opContext.getSOAPVersion());
/*     */     }
/* 143 */     else if (untBinding.getUUID().equals(context.getusernameTokenBinding().getUUID())) {
/* 144 */       unToken = context.getusernameTokenBinding().getUsernameToken();
/*     */     } else {
/* 146 */       unToken = new UsernameToken(opContext.getSOAPVersion());
/*     */     } 
/*     */     
/*     */     try {
/* 150 */       binding = UsernameTokenDataResolver.setSaltandIterationsforUsernameToken((FilterProcessingContext)opContext, unToken, authPolicy, binding, firstByte);
/* 151 */     } catch (UnsupportedEncodingException ex) {
/* 152 */       throw new XWSSecurityException("error occurred while decoding the salt in username token", ex);
/* 153 */     } catch (XWSSecurityException ex) {
/* 154 */       throw ex;
/*     */     } 
/* 156 */     if (binding.getUseNonce() && unToken.getNonceValue() == null) {
/* 157 */       unToken.setNonce(binding.getNonce());
/* 158 */       String creationTime = "";
/* 159 */       TimestampPolicy tPolicy = (TimestampPolicy)binding.getFeatureBinding();
/* 160 */       if (tPolicy == null) tPolicy = (TimestampPolicy)binding.newTimestampFeatureBinding(); 
/* 161 */       creationTime = tPolicy.getCreationTime();
/* 162 */       unToken.setCreationTime(creationTime);
/*     */     } 
/* 164 */     if (binding.getUseCreated() && unToken.getCreatedValue() == null) {
/* 165 */       String creationTime = "";
/* 166 */       TimestampPolicy tPolicy = (TimestampPolicy)binding.getFeatureBinding();
/* 167 */       if (tPolicy == null) tPolicy = (TimestampPolicy)binding.newTimestampFeatureBinding(); 
/* 168 */       creationTime = tPolicy.getCreationTime();
/* 169 */       unToken.setCreationTime(creationTime);
/*     */     } 
/* 171 */     binding.setUsernameToken(unToken);
/* 172 */     String dataEncAlgo = null;
/* 173 */     if (context.getAlgorithmSuite() != null) {
/* 174 */       dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */     } else {
/* 176 */       dataEncAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */     } 
/* 178 */     SecretKey sKey = binding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 179 */     binding.setSecretKey(sKey);
/* 180 */     return binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(FilterProcessingContext context) throws XWSSecurityException {
/* 190 */     if (!context.isInboundMessage()) {
/*     */       
/* 192 */       WSSPolicy policy = (WSSPolicy)context.getSecurityPolicy();
/* 193 */       SignaturePolicy resolvedPolicy = (SignaturePolicy)policy;
/*     */       
/* 195 */       if (!context.makeDynamicPolicyCallback()) {
/*     */         DerivedTokenKeyBinding derivedTokenKeyBinding;
/* 197 */         WSSPolicy keyBinding = (WSSPolicy)((SignaturePolicy)policy).getKeyBinding();
/* 198 */         if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/* 199 */           DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)keyBinding.clone();
/* 200 */           WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*     */           
/* 202 */           if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)originalKeyBinding)) {
/* 203 */             AuthenticationTokenPolicy.UsernameTokenBinding ckBindingClone = (AuthenticationTokenPolicy.UsernameTokenBinding)originalKeyBinding.clone();
/*     */             
/* 205 */             SymmetricKeyBinding skb = new SymmetricKeyBinding();
/* 206 */             skb.setKeyBinding((MLSPolicy)ckBindingClone);
/* 207 */             dtk.setOriginalKeyBinding((WSSPolicy)skb);
/* 208 */             derivedTokenKeyBinding = dtk;
/*     */           } 
/*     */         } 
/*     */         
/* 212 */         if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)derivedTokenKeyBinding))
/* 213 */         { AuthenticationTokenPolicy.UsernameTokenBinding binding = createUntBinding(context, (AuthenticationTokenPolicy.UsernameTokenBinding)derivedTokenKeyBinding, 1);
/* 214 */           context.setUsernameTokenBinding(binding); }
/* 215 */         else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)derivedTokenKeyBinding))
/*     */         { try {
/* 217 */             AuthenticationTokenPolicy.X509CertificateBinding binding = (AuthenticationTokenPolicy.X509CertificateBinding)derivedTokenKeyBinding.clone();
/* 218 */             String certIdentifier = binding.getCertificateIdentifier();
/* 219 */             String algorithm = binding.getKeyAlgorithm();
/* 220 */             if ("http://www.w3.org/2000/09/xmldsig#hmac-sha1".equals(algorithm)) {
/* 221 */               X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, false);
/* 222 */               binding.setX509Certificate(cert);
/*     */             
/*     */             }
/* 225 */             else if (certIdentifier == null || "".equals(certIdentifier)) {
/*     */               
/* 227 */               WSSPolicy ckBinding = (WSSPolicy)binding.getKeyBinding();
/*     */               
/* 229 */               if (ckBinding == null) {
/* 230 */                 ckBinding = (WSSPolicy)binding.newPrivateKeyBinding();
/*     */               }
/*     */               
/* 233 */               if (context.getSecurityEnvironment().getClass().getName().equals("com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl")) {
/*     */                 
/* 235 */                 SignatureKeyCallback.PrivKeyCertRequest request = ((DefaultSecurityEnvironmentImpl)context.getSecurityEnvironment()).getDefaultPrivKeyCertRequest(context.getExtraneousProperties());
/*     */ 
/*     */ 
/*     */                 
/* 239 */                 binding.setX509Certificate(request.getX509Certificate());
/* 240 */                 if (request.getX509Certificate() == null) {
/* 241 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1421_NO_DEFAULT_X_509_CERTIFICATE_PROVIDED());
/* 242 */                   throw new XWSSecurityException("No default X509Certificate was provided");
/*     */                 } 
/* 244 */                 ((PrivateKeyBinding)ckBinding).setPrivateKey(request.getPrivateKey());
/*     */               } else {
/* 246 */                 X509Certificate cert = context.getSecurityEnvironment().getDefaultCertificate(context.getExtraneousProperties());
/*     */                 
/* 248 */                 if (cert == null) {
/* 249 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1421_NO_DEFAULT_X_509_CERTIFICATE_PROVIDED());
/* 250 */                   throw new XWSSecurityException("No default X509Certificate was provided");
/*     */                 } 
/* 252 */                 binding.setX509Certificate(cert);
/* 253 */                 PrivateKey pk = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*     */                 
/* 255 */                 ((PrivateKeyBinding)ckBinding).setPrivateKey(pk);
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 260 */             else if (context.getSecurityEnvironment().getClass().getName().equals("com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl")) {
/*     */               
/* 262 */               SignatureKeyCallback.AliasPrivKeyCertRequest request = ((DefaultSecurityEnvironmentImpl)context.getSecurityEnvironment()).getAliasPrivKeyCertRequest(certIdentifier);
/*     */ 
/*     */ 
/*     */               
/* 266 */               binding.setX509Certificate(request.getX509Certificate());
/* 267 */               if (request.getX509Certificate() == null) {
/* 268 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1421_NO_DEFAULT_X_509_CERTIFICATE_PROVIDED());
/* 269 */                 throw new XWSSecurityException("No X509Certificate was provided");
/*     */               } 
/*     */               
/* 272 */               WSSPolicy ckBinding = (WSSPolicy)binding.getKeyBinding();
/*     */               
/* 274 */               if (PolicyTypeUtil.privateKeyBinding((SecurityPolicy)ckBinding)) {
/* 275 */                 ((PrivateKeyBinding)ckBinding).setPrivateKey(request.getPrivateKey());
/*     */               }
/* 277 */               else if (ckBinding == null) {
/*     */ 
/*     */                 
/* 280 */                 ((PrivateKeyBinding)binding.newPrivateKeyBinding()).setPrivateKey(request.getPrivateKey());
/*     */               } else {
/*     */                 
/* 283 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1416_UNSUPPORTED_KEYBINDING());
/* 284 */                 throw new XWSSecurityException("Unsupported KeyBinding for X509CertificateBinding");
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 290 */               X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, true);
/*     */ 
/*     */               
/* 293 */               binding.setX509Certificate(cert);
/* 294 */               WSSPolicy ckBinding = (WSSPolicy)binding.getKeyBinding();
/* 295 */               PrivateKey key = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), certIdentifier);
/*     */ 
/*     */               
/* 298 */               if (PolicyTypeUtil.privateKeyBinding((SecurityPolicy)ckBinding)) {
/* 299 */                 ((PrivateKeyBinding)ckBinding).setPrivateKey(key);
/*     */               }
/* 301 */               else if (ckBinding == null) {
/*     */                 
/* 303 */                 ((PrivateKeyBinding)binding.newPrivateKeyBinding()).setPrivateKey(key);
/*     */               } else {
/*     */                 
/* 306 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1416_UNSUPPORTED_KEYBINDING());
/* 307 */                 throw new XWSSecurityException("Unsupported KeyBinding for X509CertificateBinding");
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 317 */             context.setX509CertificateBinding(binding);
/*     */           }
/* 319 */           catch (Exception e) {
/* 320 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1417_EXCEPTION_PROCESSING_SIGNATURE(new Object[] { e.getMessage() }));
/* 321 */             throw new XWSSecurityException(e);
/*     */           }  }
/* 323 */         else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)derivedTokenKeyBinding))
/* 324 */         { AuthenticationTokenPolicy.KerberosTokenBinding binding = (AuthenticationTokenPolicy.KerberosTokenBinding)derivedTokenKeyBinding.clone();
/* 325 */           String algorithm = binding.getKeyAlgorithm();
/*     */ 
/*     */           
/* 328 */           String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/* 329 */           KerberosContext krbContext = null;
/* 330 */           if (encodedRef != null) {
/* 331 */             krbContext = context.getKerberosContext();
/*     */           }
/* 333 */           String dataEncAlgo = null;
/* 334 */           if (context.getAlgorithmSuite() != null) {
/* 335 */             dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */           } else {
/* 337 */             dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*     */           } 
/*     */           
/* 340 */           if (krbContext != null) {
/* 341 */             byte[] kerberosToken = krbContext.getKerberosToken();
/* 342 */             binding.setTokenValue(kerberosToken);
/*     */             
/* 344 */             SecretKey sKey = krbContext.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 345 */             binding.setSecretKey(sKey);
/*     */           } else {
/* 347 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1423_KERBEROS_CONTEXT_NOTSET());
/* 348 */             throw new XWSSecurityException("WSS1423.kerberos.context.notset");
/*     */           } 
/*     */           
/* 351 */           context.setKerberosTokenBinding(binding); }
/* 352 */         else { WSSPolicy wSSPolicy; if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)derivedTokenKeyBinding)) {
/*     */             
/* 354 */             wSSPolicy = (WSSPolicy)((SignaturePolicy)policy).getKeyBinding();
/* 355 */             AuthenticationTokenPolicy.SAMLAssertionBinding binding = (AuthenticationTokenPolicy.SAMLAssertionBinding)wSSPolicy;
/*     */             
/* 357 */             if (binding.getAssertion() != null || binding.getAssertionReader() != null || binding.getAuthorityBinding() != null) {
/*     */               
/* 359 */               binding.setAssertion((Element)null);
/* 360 */               binding.setAuthorityBinding(null);
/* 361 */               binding.setAssertion((XMLStreamReader)null);
/*     */             } 
/*     */             
/* 364 */             binding.isReadOnly(true);
/*     */ 
/*     */             
/* 367 */             DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */             
/* 370 */             dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 371 */             dynamicContext.inBoundMessage(false);
/* 372 */             AuthenticationTokenPolicy.SAMLAssertionBinding resolvedSAMLBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)context.getExtraneousProperties().get("Saml_Assertion_Client_Cache");
/*     */ 
/*     */             
/* 375 */             if (resolvedSAMLBinding == null) {
/*     */               
/* 377 */               resolvedSAMLBinding = context.getSecurityEnvironment().populateSAMLPolicy(context.getExtraneousProperties(), binding, dynamicContext);
/*     */               
/* 379 */               context.getExtraneousProperties().put("Saml_Assertion_Client_Cache", resolvedSAMLBinding);
/*     */             } 
/* 381 */             if (resolvedSAMLBinding.getAssertion() == null && resolvedSAMLBinding.getAuthorityBinding() == null && resolvedSAMLBinding.getAssertionReader() == null) {
/*     */               
/* 383 */               log.log(Level.SEVERE, LogStringsMessages.WSS_1418_SAML_INFO_NOTSET());
/* 384 */               throw new XWSSecurityException("None of SAML Assertion, SAML AuthorityBinding information was set into  the Policy by the CallbackHandler");
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 389 */             policy.setKeyBinding((MLSPolicy)resolvedSAMLBinding);
/* 390 */             resolvedPolicy = (SignaturePolicy)policy;
/*     */           }
/* 392 */           else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)wSSPolicy)) {
/*     */             
/*     */             try {
/* 395 */               String dataEncAlgo = null;
/* 396 */               if (context.getAlgorithmSuite() != null) {
/* 397 */                 dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */               } else {
/* 399 */                 dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*     */               } 
/*     */ 
/*     */               
/* 403 */               SymmetricKeyBinding binding = (SymmetricKeyBinding)wSSPolicy.clone();
/*     */               
/* 405 */               String keyIdentifier = binding.getKeyIdentifier();
/* 406 */               SecretKey sKey = null;
/*     */               
/* 408 */               WSSPolicy ckBinding = (WSSPolicy)binding.getKeyBinding();
/* 409 */               boolean wss11Receiver = "true".equals(context.getExtraneousProperty("EnableWSS11PolicyReceiver"));
/* 410 */               boolean wss11Sender = "true".equals(context.getExtraneousProperty("EnableWSS11PolicySender"));
/* 411 */               boolean wss10 = !wss11Sender;
/* 412 */               boolean sendEKSHA1 = (wss11Receiver && wss11Sender && getReceivedSecret(context) != null);
/* 413 */               if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)ckBinding)) {
/*     */                 try {
/* 415 */                   if (!sendEKSHA1) {
/* 416 */                     AuthenticationTokenPolicy.UsernameTokenBinding untbinding = createUntBinding(context, (AuthenticationTokenPolicy.UsernameTokenBinding)ckBinding, 2);
/* 417 */                     context.setUsernameTokenBinding(untbinding);
/* 418 */                     sKey = untbinding.getSecretKey();
/*     */                   } 
/* 420 */                 } catch (Exception e) {
/* 421 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1433_ERROR_EXTRACTING_USERNAMETOKEN(), e);
/* 422 */                   throw new XWSSecurityException(e);
/*     */                 } 
/* 424 */               } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)ckBinding)) {
/*     */                 try {
/* 426 */                   if (!sendEKSHA1) {
/* 427 */                     AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)ckBinding.clone();
/*     */                     
/* 429 */                     String certIdentifier = ckBindingClone.getCertificateIdentifier();
/* 430 */                     X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, false);
/*     */                     
/* 432 */                     ckBindingClone.setX509Certificate(cert);
/* 433 */                     context.setX509CertificateBinding(ckBindingClone);
/*     */                   } 
/* 435 */                 } catch (Exception e) {
/* 436 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1413_ERROR_EXTRACTING_CERTIFICATE(), e);
/* 437 */                   throw new XWSSecurityException(e);
/*     */                 }
/*     */               
/* 440 */               } else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 441 */                 AuthenticationTokenPolicy.KerberosTokenBinding ckBindingClone = (AuthenticationTokenPolicy.KerberosTokenBinding)ckBinding;
/*     */ 
/*     */                 
/* 444 */                 String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/* 445 */                 KerberosContext krbContext = null;
/* 446 */                 if (encodedRef != null) {
/* 447 */                   krbContext = context.getKerberosContext();
/*     */                 }
/* 449 */                 if (krbContext != null) {
/* 450 */                   byte[] kerberosToken = krbContext.getKerberosToken();
/* 451 */                   ckBindingClone.setTokenValue(kerberosToken);
/*     */                   
/* 453 */                   sKey = krbContext.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 454 */                   ckBindingClone.setSecretKey(sKey);
/*     */                 } else {
/* 456 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1423_KERBEROS_CONTEXT_NOTSET());
/* 457 */                   throw new XWSSecurityException("WSS1423.kerberos.context.notset");
/*     */                 } 
/* 459 */                 context.setKerberosTokenBinding(ckBindingClone);
/*     */               } 
/* 461 */               if (!PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 462 */                 if (!binding.getKeyIdentifier().equals(MessageConstants._EMPTY)) {
/* 463 */                   sKey = context.getSecurityEnvironment().getSecretKey(context.getExtraneousProperties(), keyIdentifier, true);
/*     */                 
/*     */                 }
/* 466 */                 else if (sendEKSHA1) {
/* 467 */                   sKey = getReceivedSecret(context);
/* 468 */                 } else if (wss11Sender || wss10) {
/*     */                   
/* 470 */                   sKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*     */                 } 
/*     */               }
/*     */               
/* 474 */               binding.setSecretKey(sKey);
/* 475 */               context.setSymmetricKeyBinding(binding);
/* 476 */             } catch (Exception e) {
/*     */               
/* 478 */               log.log(Level.SEVERE, LogStringsMessages.WSS_1414_ERROR_EXTRACTING_SYMMETRICKEY(new Object[] { e.getMessage() }));
/* 479 */               throw new XWSSecurityException(e);
/*     */             } 
/* 481 */           } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)wSSPolicy)) {
/* 482 */             IssuedTokenKeyBinding itkb = (IssuedTokenKeyBinding)wSSPolicy;
/* 483 */             SecurityUtil.resolveIssuedToken(context, itkb);
/*     */           }
/* 485 */           else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)wSSPolicy)) {
/*     */             
/* 487 */             DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)wSSPolicy.clone();
/* 488 */             WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*     */             
/* 490 */             if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*     */               
/* 492 */               String dataEncAlgo = null;
/* 493 */               if (context.getAlgorithmSuite() != null) {
/* 494 */                 dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */               } else {
/* 496 */                 dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*     */               } 
/*     */ 
/*     */               
/* 500 */               SymmetricKeyBinding symmBinding = (SymmetricKeyBinding)originalKeyBinding.clone();
/* 501 */               SecretKey sKey = null;
/* 502 */               boolean wss11Receiver = "true".equals(context.getExtraneousProperty("EnableWSS11PolicyReceiver"));
/* 503 */               boolean wss11Sender = "true".equals(context.getExtraneousProperty("EnableWSS11PolicySender"));
/* 504 */               boolean wss10 = !wss11Sender;
/* 505 */               boolean sendEKSHA1 = (wss11Receiver && wss11Sender && getReceivedSecret(context) != null);
/*     */               
/* 507 */               WSSPolicy ckBinding = (WSSPolicy)originalKeyBinding.getKeyBinding();
/* 508 */               if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)ckBinding)) {
/*     */                 try {
/* 510 */                   if (!sendEKSHA1) {
/* 511 */                     AuthenticationTokenPolicy.UsernameTokenBinding untbinding = createUntBinding(context, (AuthenticationTokenPolicy.UsernameTokenBinding)ckBinding, 2);
/* 512 */                     context.setUsernameTokenBinding(untbinding);
/*     */                   } 
/* 514 */                 } catch (Exception e) {
/* 515 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1433_ERROR_EXTRACTING_USERNAMETOKEN(), e);
/* 516 */                   throw new XWSSecurityException(e);
/*     */                 }
/*     */               
/* 519 */               } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)ckBinding)) {
/*     */                 try {
/* 521 */                   if (!sendEKSHA1) {
/* 522 */                     AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)ckBinding.clone();
/*     */                     
/* 524 */                     String certIdentifier = ckBindingClone.getCertificateIdentifier();
/* 525 */                     X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certIdentifier, false);
/*     */                     
/* 527 */                     ckBindingClone.setX509Certificate(cert);
/* 528 */                     context.setX509CertificateBinding(ckBindingClone);
/*     */                   } 
/* 530 */                 } catch (Exception e) {
/* 531 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1413_ERROR_EXTRACTING_CERTIFICATE(), e);
/* 532 */                   throw new XWSSecurityException(e);
/*     */                 } 
/* 534 */               } else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 535 */                 AuthenticationTokenPolicy.KerberosTokenBinding ckBindingClone = (AuthenticationTokenPolicy.KerberosTokenBinding)ckBinding;
/*     */                 
/* 537 */                 String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/* 538 */                 KerberosContext krbContext = null;
/* 539 */                 if (encodedRef != null) {
/* 540 */                   krbContext = context.getKerberosContext();
/*     */                 }
/* 542 */                 if (krbContext != null) {
/* 543 */                   byte[] kerberosToken = krbContext.getKerberosToken();
/* 544 */                   ckBindingClone.setTokenValue(kerberosToken);
/* 545 */                   sKey = krbContext.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/* 546 */                   ckBindingClone.setSecretKey(sKey);
/*     */                 } else {
/* 548 */                   log.log(Level.SEVERE, LogStringsMessages.WSS_1423_KERBEROS_CONTEXT_NOTSET());
/* 549 */                   throw new XWSSecurityException("WSS1423.kerberos.context.notset");
/*     */                 } 
/* 551 */                 context.setKerberosTokenBinding(ckBindingClone);
/*     */               } 
/* 553 */               if (!PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 554 */                 if (sendEKSHA1) {
/* 555 */                   sKey = getReceivedSecret(context);
/* 556 */                 } else if (wss11Sender || wss10) {
/* 557 */                   sKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*     */                 } 
/*     */               }
/* 560 */               symmBinding.setSecretKey(sKey);
/* 561 */               context.setSymmetricKeyBinding(symmBinding);
/* 562 */             } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*     */               
/* 564 */               SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)originalKeyBinding;
/* 565 */               SecurityUtil.resolveSCT(context, sctBinding);
/* 566 */             } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/* 567 */               IssuedTokenKeyBinding itkb = (IssuedTokenKeyBinding)originalKeyBinding;
/* 568 */               SecurityUtil.resolveIssuedToken(context, itkb);
/*     */             }
/*     */           
/* 571 */           } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)wSSPolicy)) {
/*     */             
/* 573 */             SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)wSSPolicy;
/* 574 */             SecurityUtil.resolveSCT(context, sctBinding);
/* 575 */           } else if (PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)wSSPolicy)) {
/*     */             
/* 577 */             AuthenticationTokenPolicy.KeyValueTokenBinding binding = (AuthenticationTokenPolicy.KeyValueTokenBinding)wSSPolicy.clone();
/*     */           } else {
/* 579 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1419_UNSUPPORTED_KEYBINDING_SIGNATURE());
/* 580 */             throw new XWSSecurityException("Unsupported KeyBinding for SignaturePolicy");
/*     */           }  }
/*     */       
/*     */       } else {
/* 584 */         ((SignaturePolicy)policy).isReadOnly(true);
/*     */         
/*     */         try {
/* 587 */           DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */           
/* 590 */           dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 591 */           dynamicContext.inBoundMessage(false);
/*     */           
/* 593 */           DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)policy, (DynamicPolicyContext)dynamicContext);
/*     */           
/* 595 */           ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 596 */           HarnessUtil.makeDynamicPolicyCallback(dynamicCallback, context.getSecurityEnvironment().getCallbackHandler());
/*     */           
/* 598 */           resolvedPolicy = (SignaturePolicy)dynamicCallback.getSecurityPolicy();
/*     */         }
/* 600 */         catch (Exception e) {
/* 601 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1420_DYNAMIC_POLICY_SIGNATURE(new Object[] { e.getMessage() }));
/* 602 */           throw new XWSSecurityException(e);
/*     */         } 
/*     */       } 
/*     */       
/* 606 */       context.setSecurityPolicy((SecurityPolicy)resolvedPolicy);
/*     */       
/* 608 */       sign(context);
/*     */     }
/*     */     else {
/*     */       
/* 612 */       if (context.makeDynamicPolicyCallback()) {
/* 613 */         WSSPolicy policy = (WSSPolicy)context.getSecurityPolicy();
/* 614 */         SignaturePolicy resolvedPolicy = null;
/* 615 */         ((SignaturePolicy)policy).isReadOnly(true);
/*     */         
/*     */         try {
/* 618 */           DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */           
/* 621 */           dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 622 */           dynamicContext.inBoundMessage(true);
/*     */           
/* 624 */           DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)policy, (DynamicPolicyContext)dynamicContext);
/*     */           
/* 626 */           ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 627 */           HarnessUtil.makeDynamicPolicyCallback(dynamicCallback, context.getSecurityEnvironment().getCallbackHandler());
/*     */ 
/*     */           
/* 630 */           resolvedPolicy = (SignaturePolicy)dynamicCallback.getSecurityPolicy();
/* 631 */         } catch (Exception e) {
/* 632 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1420_DYNAMIC_POLICY_SIGNATURE(new Object[] { e.getMessage() }));
/* 633 */           throw new XWSSecurityException(e);
/*     */         } 
/* 635 */         context.setSecurityPolicy((SecurityPolicy)resolvedPolicy);
/*     */       } 
/*     */       
/* 638 */       SignatureProcessor.verify(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sign(FilterProcessingContext context) throws XWSSecurityException {
/* 648 */     if (context instanceof JAXBFilterProcessingContext) {
/* 649 */       SignatureProcessor.sign((JAXBFilterProcessingContext)context);
/*     */     } else {
/* 651 */       SignatureProcessor.sign(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SecretKey getReceivedSecret(FilterProcessingContext context) {
/* 661 */     SecretKey sKey = null;
/* 662 */     sKey = (SecretKey)context.getExtraneousProperty("SecretKeyValue");
/* 663 */     return sKey;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\SignatureFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */