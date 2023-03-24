/*     */ package com.sun.xml.wss.impl.policy.verifier;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.PolicyViolationException;
/*     */ import com.sun.xml.wss.impl.WSSAssertion;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.spi.PolicyVerifier;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessagePolicyVerifier
/*     */   implements PolicyVerifier
/*     */ {
/*  93 */   private ProcessingContext ctx = null;
/*     */   
/*     */   private TargetResolver targetResolver;
/*  96 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessagePolicyVerifier(ProcessingContext ctx, TargetResolver targetResolver) {
/* 102 */     this.ctx = ctx;
/* 103 */     this.targetResolver = targetResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyPolicy(SecurityPolicy ip, SecurityPolicy ap) throws PolicyViolationException {
/* 113 */     MessagePolicy actualPolicy = (MessagePolicy)ap;
/* 114 */     MessagePolicy inferredSecurityPolicy = (MessagePolicy)ip;
/* 115 */     JAXBFilterProcessingContext context = null;
/* 116 */     if (this.ctx instanceof JAXBFilterProcessingContext) {
/* 117 */       context = (JAXBFilterProcessingContext)this.ctx;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (actualPolicy != null && 
/* 123 */       actualPolicy.isSSL() && context != null && !context.isSecure()) {
/* 124 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1601_SSL_NOT_ENABLED());
/* 125 */       throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1601_SSL_NOT_ENABLED());
/*     */     } 
/*     */ 
/*     */     
/* 129 */     if (actualPolicy == null || actualPolicy.size() <= 0) {
/* 130 */       if (inferredSecurityPolicy != null && inferredSecurityPolicy.size() > 0)
/*     */       {
/* 132 */         if (!checkAllowExtraTimestamp(inferredSecurityPolicy)) {
/* 133 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0805_POLICY_NULL());
/* 134 */           throw new PolicyViolationException("ERROR: Policy for the service could not be obtained");
/*     */         }  } 
/*     */     } else {
/* 137 */       if (inferredSecurityPolicy == null || inferredSecurityPolicy.size() <= 0) {
/* 138 */         throw new PolicyViolationException("ERROR: No security header found in the message");
/*     */       }
/*     */       try {
/* 141 */         for (int i = 0; i < actualPolicy.size(); i++) {
/* 142 */           WSSPolicy actualPol = (WSSPolicy)actualPolicy.get(i);
/* 143 */           if (PolicyTypeUtil.isSecondaryPolicy(actualPol)) {
/* 144 */             processSecondaryPolicy(actualPol, inferredSecurityPolicy);
/* 145 */           } else if (PolicyTypeUtil.isPrimaryPolicy(actualPol)) {
/* 146 */             processPrimaryPolicy(actualPol, inferredSecurityPolicy);
/*     */           }
/*     */         
/*     */         } 
/* 150 */       } catch (Exception e) {
/* 151 */         throw new PolicyViolationException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isEncryptedSignature(WSSPolicy actualPol, WSSPolicy inferredPol) {
/* 157 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPol) && PolicyTypeUtil.encryptionPolicy((SecurityPolicy)inferredPol)) {
/*     */       
/* 159 */       EncryptionPolicy pol = (EncryptionPolicy)inferredPol;
/* 160 */       EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)pol.getFeatureBinding();
/*     */       
/* 162 */       if (fb.encryptsSignature()) {
/* 163 */         return true;
/*     */       }
/*     */     } 
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processSecondaryPolicy(WSSPolicy actualPol, MessagePolicy inferredSecurityPolicy) throws XWSSecurityException {
/*     */     try {
/* 178 */       if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)actualPol)) {
/* 179 */         boolean found = false;
/* 180 */         for (int j = 0; j < inferredSecurityPolicy.size(); j++) {
/* 181 */           WSSPolicy pol = (WSSPolicy)inferredSecurityPolicy.get(j);
/* 182 */           if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)pol)) {
/* 183 */             inferredSecurityPolicy.remove((SecurityPolicy)pol);
/* 184 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 188 */         if (!found);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 196 */       else if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)actualPol.getFeatureBinding())) {
/* 197 */         boolean found = false;
/* 198 */         for (int j = 0; j < inferredSecurityPolicy.size(); j++) {
/* 199 */           WSSPolicy pol = (WSSPolicy)inferredSecurityPolicy.get(j);
/* 200 */           if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)pol)) {
/* 201 */             AuthenticationTokenPolicy.UsernameTokenBinding actual = (AuthenticationTokenPolicy.UsernameTokenBinding)actualPol.getFeatureBinding();
/*     */             
/* 203 */             AuthenticationTokenPolicy.UsernameTokenBinding inferred = (AuthenticationTokenPolicy.UsernameTokenBinding)pol;
/*     */             
/* 205 */             if (inferred.hasNoPassword() && !actual.hasNoPassword()) {
/* 206 */               throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Empty Password specified, Authentication of Username Password Token Failed", null, true);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 212 */             if (actual.getUseCreated() == true && !inferred.getUseCreated()) {
/* 213 */               throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Invalid Username Password Token. Missing Created ", null, true);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 219 */             if (actual.getUseNonce() == true && !inferred.getUseNonce()) {
/* 220 */               throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Invalid Username Password Token. Missing Nonce ", null, true);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 226 */             inferredSecurityPolicy.remove((SecurityPolicy)pol);
/* 227 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 231 */         if (!found && 
/* 232 */           !((WSSPolicy)actualPol.getFeatureBinding()).isOptional()) {
/* 233 */           throw new XWSSecurityException("Policy Verification error:UsernameToken not found in message but occurs in configured policy");
/*     */         
/*     */         }
/*     */       }
/* 237 */       else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)actualPol.getFeatureBinding())) {
/* 238 */         boolean found = false;
/* 239 */         for (int j = 0; j < inferredSecurityPolicy.size(); j++) {
/* 240 */           WSSPolicy pol = (WSSPolicy)inferredSecurityPolicy.get(j);
/* 241 */           if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)pol)) {
/*     */ 
/*     */             
/* 244 */             inferredSecurityPolicy.remove((SecurityPolicy)pol);
/* 245 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 249 */         if (!found && 
/* 250 */           !((WSSPolicy)actualPol.getFeatureBinding()).isOptional()) {
/* 251 */           throw new XWSSecurityException("Policy Verification error:SAML Token not found in message but occurs in configured policy");
/*     */         
/*     */         }
/*     */       }
/* 255 */       else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)actualPol.getFeatureBinding())) {
/* 256 */         boolean found = false;
/* 257 */         for (int j = 0; j < inferredSecurityPolicy.size(); j++) {
/* 258 */           WSSPolicy pol = (WSSPolicy)inferredSecurityPolicy.get(j);
/* 259 */           if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)pol)) {
/* 260 */             AuthenticationTokenPolicy.SAMLAssertionBinding actual = (AuthenticationTokenPolicy.SAMLAssertionBinding)actualPol.getFeatureBinding().getKeyBinding();
/*     */             
/* 262 */             AuthenticationTokenPolicy.SAMLAssertionBinding inferred = (AuthenticationTokenPolicy.SAMLAssertionBinding)pol;
/*     */             
/* 264 */             inferredSecurityPolicy.remove((SecurityPolicy)pol);
/* 265 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 269 */         if (!found) {
/* 270 */           throw new XWSSecurityException("Policy Verification error:SAML token  not found in message but occurs in configured policy");
/*     */         }
/*     */       } 
/* 273 */     } catch (WssSoapFaultException e) {
/* 274 */       throw e;
/* 275 */     } catch (Exception e) {
/* 276 */       throw new XWSSecurityException(e);
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
/*     */ 
/*     */   
/*     */   private void processPrimaryPolicy(WSSPolicy actualPol, MessagePolicy inferredSecurityPolicy) throws XWSSecurityException {
/* 290 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPol)) {
/* 291 */       SignaturePolicy actualSignPolicy = (SignaturePolicy)actualPol;
/* 292 */       boolean isEndorsing = ((SignaturePolicy.FeatureBinding)actualSignPolicy.getFeatureBinding()).isEndorsingSignature();
/* 293 */       boolean isPrimary = ((SignaturePolicy.FeatureBinding)actualSignPolicy.getFeatureBinding()).isPrimarySignature();
/* 294 */       int nth = 0;
/* 295 */       WSSPolicy pol = getFirstPrimaryPolicy(inferredSecurityPolicy, isEndorsing, nth++);
/* 296 */       if (pol == null && isOptionalPolicy(actualSignPolicy) == true) {
/*     */         return;
/*     */       }
/* 299 */       if (pol == null) {
/* 300 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0268_ERROR_POLICY_VERIFICATION());
/* 301 */         throw new XWSSecurityException("Policy verification error:Missing Signature Element");
/*     */       } 
/*     */ 
/*     */       
/* 305 */       if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)pol)) {
/* 306 */         SignaturePolicy inferredPol = (SignaturePolicy)pol;
/*     */         
/* 308 */         boolean isKBTrue = verifyKeyBinding(actualSignPolicy.getKeyBinding(), inferredPol.getKeyBinding(), false);
/*     */         
/* 310 */         while (!isKBTrue && !isPrimary) {
/* 311 */           pol = getFirstPrimaryPolicy(inferredSecurityPolicy, isEndorsing, nth++);
/* 312 */           if (pol == null && isOptionalPolicy(actualSignPolicy) == true) {
/*     */             return;
/*     */           }
/* 315 */           if (pol == null) {
/* 316 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0268_ERROR_POLICY_VERIFICATION());
/* 317 */             throw new XWSSecurityException("Policy verification error:Missing Signature Element - perhaps a second supporting signature or Incorrect Key types or references were used in Signature");
/*     */           } 
/*     */ 
/*     */           
/* 321 */           inferredPol = (SignaturePolicy)pol;
/* 322 */           isKBTrue = verifyKeyBinding(actualSignPolicy.getKeyBinding(), inferredPol.getKeyBinding(), false);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 327 */         boolean isTBTrue = verifySignTargetBinding((SignaturePolicy.FeatureBinding)actualSignPolicy.getFeatureBinding(), (SignaturePolicy.FeatureBinding)inferredPol.getFeatureBinding());
/*     */ 
/*     */         
/* 330 */         inferredSecurityPolicy.remove((SecurityPolicy)pol);
/* 331 */         if (!isKBTrue) {
/* 332 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 333 */           throw new XWSSecurityException("Policy verification error: Incorrect Key types or references were used in Signature");
/*     */         } 
/*     */         
/* 336 */         if (!isTBTrue) {
/* 337 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 338 */           throw new XWSSecurityException("Policy verification error: One or more Signed Parts could not be validated");
/*     */         } 
/*     */         
/* 341 */         checkTargets(actualPol, pol);
/*     */ 
/*     */       
/*     */       }
/* 345 */       else if (!isEncryptedSignature(actualPol, pol)) {
/*     */ 
/*     */         
/* 348 */         if (checkTargetPresence(actualPol)) {
/* 349 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 350 */           throw new XWSSecurityException("Signature Policy verification error: Looking for a Signature Element  in Security header, but found " + pol + ".");
/*     */         } 
/*     */       } else {
/* 353 */         inferredSecurityPolicy.remove((SecurityPolicy)pol);
/*     */       }
/*     */     
/* 356 */     } else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)actualPol)) {
/* 357 */       EncryptionPolicy actualEncryptionPolicy = (EncryptionPolicy)actualPol;
/* 358 */       WSSPolicy pol = getFirstPrimaryPolicy(inferredSecurityPolicy, false, 0);
/* 359 */       if (pol == null) {
/* 360 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0269_ERROR_ENCRYPTIONPOLICY_VERIFICATION());
/* 361 */         throw new XWSSecurityException("Encryption Policy verification error:Missing encryption element");
/*     */       } 
/*     */ 
/*     */       
/* 365 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)pol)) {
/* 366 */         EncryptionPolicy inferredPol = (EncryptionPolicy)pol;
/*     */         
/* 368 */         boolean isKBTrue = verifyKeyBinding(actualEncryptionPolicy.getKeyBinding(), inferredPol.getKeyBinding(), true);
/*     */ 
/*     */         
/* 371 */         boolean isTBTrue = verifyEncTargetBinding((EncryptionPolicy.FeatureBinding)actualEncryptionPolicy.getFeatureBinding(), (EncryptionPolicy.FeatureBinding)inferredPol.getFeatureBinding());
/*     */ 
/*     */         
/* 374 */         inferredSecurityPolicy.remove((SecurityPolicy)pol);
/* 375 */         if (!isKBTrue) {
/* 376 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 377 */           throw new XWSSecurityException("Encryption Policy verification error: Incorrect Key types or references were used in encryption");
/*     */         } 
/*     */         
/* 380 */         if (!isTBTrue) {
/* 381 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 382 */           throw new XWSSecurityException("Policy verification error: One or more encrypted parts could not be validated");
/*     */         } 
/*     */         
/* 385 */         List<Target> inferredList = ((EncryptionPolicy.FeatureBinding)pol.getFeatureBinding()).getTargetBindings();
/* 386 */         List<Target> actualList = ((EncryptionPolicy.FeatureBinding)actualPol.getFeatureBinding()).getTargetBindings();
/* 387 */         if (actualList.size() > inferredList.size()) {
/* 388 */           int nthEncrypt = 0;
/* 389 */           EncryptionPolicy inferredPol2 = getNthEncryptionPolicy(inferredSecurityPolicy, nthEncrypt);
/* 390 */           while (inferredPol2 != null) {
/* 391 */             boolean isKBTrue2 = verifyKeyBinding(actualEncryptionPolicy.getKeyBinding(), inferredPol2.getKeyBinding(), true);
/*     */             
/* 393 */             boolean isTBTrue2 = verifyEncTargetBinding((EncryptionPolicy.FeatureBinding)actualEncryptionPolicy.getFeatureBinding(), (EncryptionPolicy.FeatureBinding)inferredPol2.getFeatureBinding());
/*     */             
/* 395 */             if (!isKBTrue2 || !isTBTrue2) {
/* 396 */               nthEncrypt++;
/* 397 */               inferredPol2 = getNthEncryptionPolicy(inferredSecurityPolicy, nthEncrypt); continue;
/*     */             } 
/* 399 */             List<Target> moreTargets = ((EncryptionPolicy.FeatureBinding)inferredPol2.getFeatureBinding()).getTargetBindings();
/* 400 */             for (Target moreTarget : moreTargets) {
/* 401 */               ((EncryptionPolicy.FeatureBinding)inferredPol.getFeatureBinding()).addTargetBinding(moreTarget);
/*     */             }
/* 403 */             if (actualList.size() == inferredList.size()) {
/* 404 */               inferredSecurityPolicy.remove((SecurityPolicy)inferredPol2);
/*     */               break;
/*     */             } 
/* 407 */             inferredSecurityPolicy.remove((SecurityPolicy)inferredPol2);
/* 408 */             nthEncrypt++;
/* 409 */             inferredPol2 = getNthEncryptionPolicy(inferredSecurityPolicy, nthEncrypt);
/*     */           } 
/*     */         } 
/*     */         
/* 413 */         checkTargets(actualPol, pol);
/*     */ 
/*     */       
/*     */       }
/* 417 */       else if (checkTargetPresence(actualPol)) {
/* 418 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0206_POLICY_VIOLATION_EXCEPTION());
/* 419 */         throw new XWSSecurityException("Encryption Policy verification error: Looking for an Encryption Element  in Security header, but found " + pol + ".");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTargets(WSSPolicy actualPol, WSSPolicy inferredPol) throws XWSSecurityException {
/* 430 */     List<Target> inferredTargets = null;
/* 431 */     List<Target> actualTargets = null;
/*     */     
/* 433 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPol)) {
/*     */       
/* 435 */       SignaturePolicy.FeatureBinding inferredFeatureBinding = (SignaturePolicy.FeatureBinding)inferredPol.getFeatureBinding();
/*     */       
/* 437 */       SignaturePolicy.FeatureBinding actualFeatureBinding = (SignaturePolicy.FeatureBinding)actualPol.getFeatureBinding();
/*     */ 
/*     */       
/* 440 */       inferredTargets = inferredFeatureBinding.getTargetBindings();
/* 441 */       actualTargets = actualFeatureBinding.getTargetBindings();
/*     */     }
/* 443 */     else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)actualPol)) {
/*     */       
/* 445 */       EncryptionPolicy.FeatureBinding inferredFeatureBinding = (EncryptionPolicy.FeatureBinding)inferredPol.getFeatureBinding();
/*     */       
/* 447 */       EncryptionPolicy.FeatureBinding actualFeatureBinding = (EncryptionPolicy.FeatureBinding)actualPol.getFeatureBinding();
/*     */ 
/*     */       
/* 450 */       inferredTargets = inferredFeatureBinding.getTargetBindings();
/* 451 */       actualTargets = actualFeatureBinding.getTargetBindings();
/*     */     } 
/*     */     
/* 454 */     this.targetResolver.resolveAndVerifyTargets(actualTargets, inferredTargets, actualPol);
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
/*     */   private boolean verifyKeyBinding(MLSPolicy actualKeyBinding, MLSPolicy inferredKeyBinding, boolean isEncryptPolicy) throws XWSSecurityException {
/* 467 */     boolean verified = false;
/* 468 */     if (actualKeyBinding != null && inferredKeyBinding != null) {
/* 469 */       if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 471 */         AuthenticationTokenPolicy.UsernameTokenBinding act = (AuthenticationTokenPolicy.UsernameTokenBinding)actualKeyBinding;
/* 472 */         AuthenticationTokenPolicy.UsernameTokenBinding inf = (AuthenticationTokenPolicy.UsernameTokenBinding)inferredKeyBinding;
/*     */         
/* 474 */         if (act.getUseCreated() == true && !inf.getUseCreated())
/* 475 */           throw new XWSSecurityException("Policy verification error: Invalid Usernametoken, Missing Created"); 
/* 476 */         if (act.getUseNonce() == true && !inf.getUseNonce()) {
/* 477 */           throw new XWSSecurityException("Policy verification error: Invalid Usernametoken, Missing Nonce");
/*     */         }
/* 479 */         verified = true;
/*     */       }
/* 481 */       else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 494 */         verified = true;
/* 495 */       } else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 497 */         verified = true;
/* 498 */       } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 500 */         verified = verifyKeyBinding(actualKeyBinding.getKeyBinding(), inferredKeyBinding.getKeyBinding(), isEncryptPolicy);
/*     */ 
/*     */         
/* 503 */         if (((SymmetricKeyBinding)inferredKeyBinding).usesEKSHA1KeyBinding() && PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)actualKeyBinding.getKeyBinding())) {
/* 504 */           verified = true;
/*     */         }
/* 506 */       } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */ 
/*     */         
/* 509 */         verified = true;
/* 510 */       } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */ 
/*     */         
/* 513 */         verified = true;
/* 514 */       } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */ 
/*     */         
/* 517 */         verified = verifyKeyBinding((MLSPolicy)((DerivedTokenKeyBinding)actualKeyBinding).getOriginalKeyBinding(), (MLSPolicy)((DerivedTokenKeyBinding)inferredKeyBinding).getOriginalKeyBinding(), isEncryptPolicy);
/*     */       
/*     */       }
/* 520 */       else if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 522 */         MLSPolicy ikbkb = inferredKeyBinding.getKeyBinding();
/* 523 */         if (isEncryptPolicy && PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)ikbkb)) {
/* 524 */           verified = true;
/*     */         }
/* 526 */       } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 528 */         MLSPolicy ikbkb = inferredKeyBinding.getKeyBinding();
/* 529 */         if (isEncryptPolicy && PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)ikbkb)) {
/* 530 */           verified = true;
/*     */         }
/* 532 */       } else if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 534 */         MLSPolicy ikbkb = inferredKeyBinding.getKeyBinding();
/* 535 */         if (isEncryptPolicy && PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ikbkb)) {
/* 536 */           verified = true;
/*     */         }
/* 538 */       } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 540 */         MLSPolicy ikbkb = inferredKeyBinding.getKeyBinding();
/* 541 */         if (isEncryptPolicy && PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)ikbkb)) {
/* 542 */           verified = true;
/*     */         }
/* 544 */       } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)inferredKeyBinding)) {
/*     */ 
/*     */         
/* 547 */         verified = true;
/* 548 */       } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 550 */         MLSPolicy akbkb = actualKeyBinding.getKeyBinding();
/* 551 */         if (isEncryptPolicy && PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)akbkb)) {
/* 552 */           verified = true;
/*     */         }
/* 554 */       } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 556 */         MLSPolicy akbkb = actualKeyBinding.getKeyBinding();
/* 557 */         if (isEncryptPolicy && PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)akbkb)) {
/* 558 */           verified = true;
/*     */         }
/* 560 */       } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)actualKeyBinding)) {
/*     */         
/* 562 */         if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)inferredKeyBinding) && PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)((DerivedTokenKeyBinding)actualKeyBinding).getOriginalKeyBinding()))
/*     */         {
/*     */           
/* 565 */           verified = true;
/*     */         }
/* 567 */       } else if (PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)actualKeyBinding) && PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)inferredKeyBinding)) {
/*     */         
/* 569 */         verified = true;
/*     */       } 
/*     */     }
/*     */     
/* 573 */     return verified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifySignTargetBinding(SignaturePolicy.FeatureBinding actualFeatureBinding, SignaturePolicy.FeatureBinding inferredFeatureBinding) throws XWSSecurityException {
/* 584 */     String actualCanonAlgo = actualFeatureBinding.getCanonicalizationAlgorithm();
/* 585 */     String inferredCanonAlgo = inferredFeatureBinding.getCanonicalizationAlgorithm();
/*     */     
/* 587 */     if (actualCanonAlgo == null || inferredCanonAlgo == null) {
/* 588 */       throw new XWSSecurityException("ActualCanonicalizationAlgorithm or InferredCanonicalizationAlgorithm  is null while verifying SignatureTargetBinding");
/*     */     }
/*     */     
/* 591 */     if (actualCanonAlgo.length() > 0 && inferredCanonAlgo.length() > 0 && 
/* 592 */       !inferredCanonAlgo.equals(actualCanonAlgo)) {
/* 593 */       log.warning("Receiver side requirement verification failed, canonicalization algorithm received in the message is " + inferredCanonAlgo + " policy requires " + actualCanonAlgo);
/*     */ 
/*     */       
/* 596 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 600 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifyEncTargetBinding(EncryptionPolicy.FeatureBinding actualFeatureBinding, EncryptionPolicy.FeatureBinding inferredFeatureBinding) {
/* 611 */     String rDA = inferredFeatureBinding.getDataEncryptionAlgorithm();
/* 612 */     String cDA = actualFeatureBinding.getDataEncryptionAlgorithm();
/* 613 */     if (cDA != null && cDA.length() > 0 && 
/* 614 */       !cDA.equals(rDA)) {
/* 615 */       log.warning("Receiver side requirement verification failed, DataEncryptionAlgorithm specified in the receiver requirements did match with DataEncryptionAlgorithm used to encrypt the message.Configured DataEncryptionAlgorithm is " + cDA + "  DataEncryptionAlgorithm used in the" + "message is " + rDA);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 620 */       return false;
/*     */     } 
/*     */     
/* 623 */     return true;
/*     */   }
/*     */   
/*     */   private EncryptionPolicy getNthEncryptionPolicy(MessagePolicy securityPolicy, int nth) throws XWSSecurityException {
/*     */     try {
/* 628 */       int count = nth;
/* 629 */       for (int i = 0; i < securityPolicy.size(); i++) {
/* 630 */         WSSPolicy pol = (WSSPolicy)securityPolicy.get(i);
/* 631 */         if (PolicyTypeUtil.isPrimaryPolicy(pol) && PolicyTypeUtil.encryptionPolicy((SecurityPolicy)pol) && 
/* 632 */           !((EncryptionPolicy.FeatureBinding)pol.getFeatureBinding()).encryptsIssuedToken())
/*     */         {
/*     */           
/* 635 */           if (count > 0) {
/* 636 */             count--;
/*     */           } else {
/*     */             
/* 639 */             return (EncryptionPolicy)pol;
/*     */           } 
/*     */         }
/*     */       } 
/* 643 */     } catch (Exception e) {
/* 644 */       throw new XWSSecurityException(e);
/*     */     } 
/* 646 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private WSSPolicy getFirstPrimaryPolicy(MessagePolicy securityPolicy, boolean isEndorsingSign, int nth) throws XWSSecurityException {
/*     */     try {
/* 653 */       int count = nth;
/* 654 */       if (!isEndorsingSign) {
/* 655 */         for (int i = 0; i < securityPolicy.size(); i++) {
/* 656 */           WSSPolicy pol = (WSSPolicy)securityPolicy.get(i);
/* 657 */           if (PolicyTypeUtil.isPrimaryPolicy(pol))
/*     */           {
/* 659 */             if (!PolicyTypeUtil.encryptionPolicy((SecurityPolicy)pol) || !((EncryptionPolicy.FeatureBinding)pol.getFeatureBinding()).encryptsIssuedToken())
/*     */             {
/*     */               
/* 662 */               if (count > 0) {
/* 663 */                 if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)pol)) {
/* 664 */                   count--;
/*     */                 }
/* 666 */               } else if (nth == 0 || PolicyTypeUtil.signaturePolicy((SecurityPolicy)pol)) {
/*     */ 
/*     */                 
/* 669 */                 return pol;
/*     */               } 
/*     */             }
/*     */           }
/*     */         } 
/*     */       } else {
/* 675 */         for (int i = count; i < securityPolicy.size(); i++) {
/* 676 */           WSSPolicy pol = (WSSPolicy)securityPolicy.get(i);
/* 677 */           if (PolicyTypeUtil.isPrimaryPolicy(pol) && PolicyTypeUtil.signaturePolicy((SecurityPolicy)pol)) {
/* 678 */             SignaturePolicy signPol = (SignaturePolicy)pol;
/* 679 */             SignaturePolicy.FeatureBinding fb = (SignaturePolicy.FeatureBinding)signPol.getFeatureBinding();
/* 680 */             for (int no_of_sig_targets = 0; no_of_sig_targets < fb.getTargetBindings().size(); no_of_sig_targets++) {
/* 681 */               SignatureTarget target = fb.getTargetBindings().get(no_of_sig_targets);
/* 682 */               if ("{http://www.w3.org/2000/09/xmldsig#}Signature".equals(target.getValue()))
/* 683 */                 return pol; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 688 */     } catch (Exception e) {
/* 689 */       throw new XWSSecurityException(e);
/*     */     } 
/* 691 */     return null;
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
/*     */   private void correctIncludeTokenPolicy(AuthenticationTokenPolicy.X509CertificateBinding x509Bind, WSSAssertion wssAssertion) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   4: astore_3
/*     */     //   5: aload_1
/*     */     //   6: pop
/*     */     //   7: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER : Ljava/lang/String;
/*     */     //   10: aload_3
/*     */     //   11: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   14: ifne -> 29
/*     */     //   17: aload_1
/*     */     //   18: pop
/*     */     //   19: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER_VER2 : Ljava/lang/String;
/*     */     //   22: aload_3
/*     */     //   23: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   26: ifeq -> 100
/*     */     //   29: ldc 'Direct'
/*     */     //   31: aload_1
/*     */     //   32: invokevirtual getReferenceType : ()Ljava/lang/String;
/*     */     //   35: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   38: ifeq -> 154
/*     */     //   41: aload_2
/*     */     //   42: ifnull -> 91
/*     */     //   45: aload_2
/*     */     //   46: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   49: ldc 'MustSupportRefKeyIdentifier'
/*     */     //   51: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   56: ifeq -> 68
/*     */     //   59: aload_1
/*     */     //   60: ldc 'Identifier'
/*     */     //   62: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   65: goto -> 154
/*     */     //   68: aload_2
/*     */     //   69: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   72: ldc 'MustSupportRefThumbprint'
/*     */     //   74: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   79: ifeq -> 154
/*     */     //   82: aload_1
/*     */     //   83: ldc 'Thumbprint'
/*     */     //   85: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   88: goto -> 154
/*     */     //   91: aload_1
/*     */     //   92: ldc 'Identifier'
/*     */     //   94: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   97: goto -> 154
/*     */     //   100: aload_1
/*     */     //   101: pop
/*     */     //   102: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*     */     //   105: aload_3
/*     */     //   106: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   109: ifne -> 148
/*     */     //   112: aload_1
/*     */     //   113: pop
/*     */     //   114: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*     */     //   117: aload_3
/*     */     //   118: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   121: ifne -> 148
/*     */     //   124: aload_1
/*     */     //   125: pop
/*     */     //   126: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*     */     //   129: aload_3
/*     */     //   130: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   133: ifne -> 148
/*     */     //   136: aload_1
/*     */     //   137: pop
/*     */     //   138: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*     */     //   141: aload_3
/*     */     //   142: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   145: ifeq -> 154
/*     */     //   148: aload_1
/*     */     //   149: ldc 'Direct'
/*     */     //   151: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   154: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #697	-> 0
/*     */     //   #698	-> 5
/*     */     //   #700	-> 29
/*     */     //   #701	-> 41
/*     */     //   #702	-> 45
/*     */     //   #703	-> 59
/*     */     //   #704	-> 68
/*     */     //   #705	-> 82
/*     */     //   #708	-> 91
/*     */     //   #711	-> 100
/*     */     //   #715	-> 148
/*     */     //   #717	-> 154
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	155	0	this	Lcom/sun/xml/wss/impl/policy/verifier/MessagePolicyVerifier;
/*     */     //   0	155	1	x509Bind	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*     */     //   0	155	2	wssAssertion	Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   5	150	3	iTokenType	Ljava/lang/String;
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
/*     */   public void printInferredSecurityPolicy(MessagePolicy inferredSecurityPolicy) throws Exception {
/* 720 */     StringBuffer buffer = new StringBuffer();
/* 721 */     if (inferredSecurityPolicy == null) {
/* 722 */       buffer.append("Security Policy not set\n");
/*     */     } else {
/* 724 */       buffer.append("Size of Policy:: " + inferredSecurityPolicy.size() + "\n");
/* 725 */       for (int i = 0; i < inferredSecurityPolicy.size(); i++) {
/* 726 */         WSSPolicy pol = (WSSPolicy)inferredSecurityPolicy.get(i);
/* 727 */         if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)pol)) {
/* 728 */           buffer.append("Timestamp Policy\n");
/* 729 */         } else if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)pol)) {
/* 730 */           buffer.append("UsernameToken Policy\n");
/* 731 */         } else if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)pol)) {
/* 732 */           buffer.append("Signature Policy\n");
/* 733 */           SignaturePolicy sigPol = (SignaturePolicy)pol;
/* 734 */           SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)sigPol.getFeatureBinding();
/*     */           
/* 736 */           ArrayList<SignatureTarget> targets = featureBinding.getTargetBindings();
/* 737 */           buffer.append("\tCanonicalizationAlgorithm" + featureBinding.getCanonicalizationAlgorithm() + "\n");
/* 738 */           buffer.append("\t Targets\n");
/* 739 */           for (int j = 0; j < targets.size(); j++) {
/* 740 */             SignatureTarget target = targets.get(j);
/* 741 */             buffer.append("\t " + j + ":Type:" + target.getType() + "\n");
/* 742 */             buffer.append("\t  Value:" + target.getValue() + "\n");
/* 743 */             buffer.append("\t  DigestAlgorithm:" + target.getDigestAlgorithm() + "\n");
/* 744 */             ArrayList<SignatureTarget.Transform> transforms = target.getTransforms();
/*     */             
/* 746 */             if (transforms != null) {
/* 747 */               buffer.append("\t  Transforms::\n");
/* 748 */               for (int k = 0; k < transforms.size(); k++) {
/* 749 */                 buffer.append("\t    " + ((SignatureTarget.Transform)transforms.get(k)).getTransform() + "\n");
/*     */               }
/*     */             } 
/*     */           } 
/* 753 */           MLSPolicy keyBinding = sigPol.getKeyBinding();
/* 754 */           if (keyBinding != null) {
/* 755 */             buffer.append("\tKeyBinding\n");
/* 756 */             printKeyBinding(keyBinding, buffer);
/*     */           } 
/* 758 */         } else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)pol)) {
/* 759 */           buffer.append("Encryption Policy\n");
/* 760 */           EncryptionPolicy encPol = (EncryptionPolicy)pol;
/* 761 */           EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)encPol.getFeatureBinding();
/*     */           
/* 763 */           ArrayList<EncryptionTarget> targets = featureBinding.getTargetBindings();
/* 764 */           buffer.append("\t Targets\n");
/* 765 */           for (int j = 0; j < targets.size(); j++) {
/* 766 */             EncryptionTarget target = targets.get(j);
/* 767 */             buffer.append("\t " + j + ":" + "Type:" + target.getType() + "\n");
/* 768 */             buffer.append("\t  Value:" + target.getValue() + "\n");
/* 769 */             buffer.append("\t  ContentOnly:" + target.getContentOnly() + "\n");
/* 770 */             buffer.append("\t  DataEncryptionAlgorithm:" + target.getDataEncryptionAlgorithm() + "\n");
/*     */           } 
/* 772 */           MLSPolicy keyBinding = encPol.getKeyBinding();
/* 773 */           if (keyBinding != null) {
/* 774 */             buffer.append("\tKeyBinding\n");
/* 775 */             printKeyBinding(keyBinding, buffer);
/*     */           } 
/* 777 */         } else if (PolicyTypeUtil.signatureConfirmationPolicy((SecurityPolicy)pol)) {
/* 778 */           buffer.append("SignatureConfirmation Policy\n");
/*     */         } else {
/* 780 */           buffer.append(pol + "\n");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printKeyBinding(MLSPolicy keyBinding, StringBuffer buffer) {
/* 790 */     if (keyBinding != null) {
/* 791 */       if (keyBinding instanceof AuthenticationTokenPolicy.X509CertificateBinding) {
/* 792 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = (AuthenticationTokenPolicy.X509CertificateBinding)keyBinding;
/*     */         
/* 794 */         buffer.append("\t  X509CertificateBinding\n");
/* 795 */         buffer.append("\t    ValueType:" + x509Binding.getValueType() + "\n");
/* 796 */         buffer.append("\t    ReferenceType:" + x509Binding.getReferenceType() + "\n");
/* 797 */       } else if (keyBinding instanceof AuthenticationTokenPolicy.SAMLAssertionBinding) {
/* 798 */         AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)keyBinding;
/*     */         
/* 800 */         buffer.append("\t  SAMLAssertionBinding\n");
/*     */         
/* 802 */         buffer.append("\t    ReferenceType:" + samlBinding.getReferenceType() + "\n");
/* 803 */       } else if (keyBinding instanceof SymmetricKeyBinding) {
/* 804 */         SymmetricKeyBinding skBinding = (SymmetricKeyBinding)keyBinding;
/* 805 */         buffer.append("\t  SymmetricKeyBinding\n");
/* 806 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = (AuthenticationTokenPolicy.X509CertificateBinding)skBinding.getKeyBinding();
/*     */         
/* 808 */         if (x509Binding != null) {
/* 809 */           buffer.append("\t     X509CertificateBinding\n");
/* 810 */           buffer.append("\t       ValueType:" + x509Binding.getValueType() + "\n");
/* 811 */           buffer.append("\t       ReferenceType:" + x509Binding.getReferenceType() + "\n");
/*     */         } 
/* 813 */       } else if (keyBinding instanceof com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding) {
/* 814 */         buffer.append("\t  IssuedTokenKeyBinding\n");
/*     */       }
/* 816 */       else if (keyBinding instanceof com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding) {
/* 817 */         buffer.append("\t  SecureConversationTokenKeyBinding\n");
/*     */       }
/* 819 */       else if (keyBinding instanceof DerivedTokenKeyBinding) {
/* 820 */         buffer.append("\t  DerivedTokenKeyBinding\n");
/* 821 */         DerivedTokenKeyBinding dtkBinding = (DerivedTokenKeyBinding)keyBinding;
/* 822 */         buffer.append("\t  OriginalKeyBinding:\n");
/* 823 */         printKeyBinding((MLSPolicy)dtkBinding.getOriginalKeyBinding(), buffer);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkTargetPresence(WSSPolicy actualPol) throws XWSSecurityException {
/* 830 */     List<Target> actualTargets = null;
/* 831 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)actualPol)) {
/* 832 */       SignaturePolicy.FeatureBinding actualFeatureBinding = (SignaturePolicy.FeatureBinding)actualPol.getFeatureBinding();
/*     */       
/* 834 */       actualTargets = actualFeatureBinding.getTargetBindings();
/* 835 */     } else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)actualPol)) {
/* 836 */       EncryptionPolicy.FeatureBinding actualFeatureBinding = (EncryptionPolicy.FeatureBinding)actualPol.getFeatureBinding();
/*     */       
/* 838 */       actualTargets = actualFeatureBinding.getTargetBindings();
/*     */     } 
/*     */     
/* 841 */     return this.targetResolver.isTargetPresent(actualTargets);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkAllowExtraTimestamp(MessagePolicy inferredSecurityPolicy) {
/* 846 */     if (inferredSecurityPolicy.size() > 1) {
/* 847 */       return false;
/*     */     }
/* 849 */     SecurityPolicy pol = null;
/*     */     try {
/* 851 */       pol = inferredSecurityPolicy.get(0);
/* 852 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 855 */     if (pol instanceof com.sun.xml.wss.impl.policy.mls.TimestampPolicy) {
/* 856 */       return true;
/*     */     }
/* 858 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isOptionalPolicy(SignaturePolicy actualSignPolicy) {
/* 862 */     if (((WSSPolicy)actualSignPolicy.getKeyBinding()).isOptional() == true) {
/* 863 */       return true;
/*     */     }
/*     */     try {
/* 866 */       if ((WSSPolicy)actualSignPolicy.getKeyBinding().getKeyBinding() != null && ((WSSPolicy)actualSignPolicy.getKeyBinding().getKeyBinding()).isOptional() == true) {
/* 867 */         return true;
/*     */       }
/* 869 */     } catch (PolicyGenerationException ex) {}
/*     */ 
/*     */ 
/*     */     
/* 873 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\MessagePolicyVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */