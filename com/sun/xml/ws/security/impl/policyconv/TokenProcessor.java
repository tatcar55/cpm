/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.addressing.policy.Address;
/*     */ import com.sun.xml.ws.security.impl.policy.Constants;
/*     */ import com.sun.xml.ws.security.impl.policy.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policy.UsernameToken;
/*     */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.IssuedToken;
/*     */ import com.sun.xml.ws.security.policy.KerberosToken;
/*     */ import com.sun.xml.ws.security.policy.KeyValueToken;
/*     */ import com.sun.xml.ws.security.policy.RsaToken;
/*     */ import com.sun.xml.ws.security.policy.SamlToken;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.policy.UserNameToken;
/*     */ import com.sun.xml.ws.security.policy.X509Token;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.KeyBindingBase;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class TokenProcessor
/*     */ {
/*     */   protected boolean isServer = false;
/*     */   protected boolean isIncoming = false;
/*  85 */   private PolicyID pid = null;
/*  86 */   SymmetricKeyBinding skb = new SymmetricKeyBinding();
/*     */   
/*     */   public TokenProcessor(boolean isServer, boolean isIncoming, PolicyID pid) {
/*  89 */     this.isServer = isServer;
/*  90 */     this.isIncoming = isIncoming;
/*  91 */     this.pid = pid;
/*     */   }
/*     */   
/*     */   protected void setX509TokenRefType(AuthenticationTokenPolicy.X509CertificateBinding x509CB, X509Token token) {
/*  95 */     Set tokenRefTypes = token.getTokenRefernceType();
/*  96 */     if (!tokenRefTypes.isEmpty()) {
/*  97 */       if (tokenRefTypes.contains("RequireThumbprintReference")) {
/*  98 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/*  99 */           Constants.logger.log(Level.FINEST, " ReferenceType set to KeyBinding" + x509CB + " is Thumbprint");
/*     */         }
/* 101 */         x509CB.setReferenceType(SecurityPolicyUtil.convertToXWSSConstants("RequireThumbprintReference"));
/* 102 */       } else if (tokenRefTypes.contains("RequireKeyIdentifierReference")) {
/* 103 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 104 */           Constants.logger.log(Level.FINEST, " ReferenceType set to KeyBinding" + x509CB + " is KeyIdentifier");
/*     */         }
/* 106 */         x509CB.setReferenceType(SecurityPolicyUtil.convertToXWSSConstants("RequireKeyIdentifierReference"));
/* 107 */       } else if (tokenRefTypes.contains("RequireIssuerSerialReference")) {
/* 108 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 109 */           Constants.logger.log(Level.FINEST, " ReferenceType set to KeyBinding " + x509CB + " is IssuerSerial");
/*     */         }
/* 111 */         x509CB.setReferenceType(SecurityPolicyUtil.convertToXWSSConstants("RequireIssuerSerialReference"));
/*     */       } else {
/* 113 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 114 */           Constants.logger.log(Level.FINEST, " ReferenceType " + x509CB + " set is DirectReference");
/*     */         }
/* 116 */         x509CB.setReferenceType("Direct");
/*     */       } 
/*     */     } else {
/* 119 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/* 120 */         Constants.logger.log(Level.FINEST, " ReferenceType set is REQUIRE_THUMBPRINT_REFERENCE");
/*     */       }
/* 122 */       x509CB.setReferenceType("Direct");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setKerberosTokenRefType(AuthenticationTokenPolicy.KerberosTokenBinding kerberosBinding, KerberosToken token) {
/* 128 */     Set tokenRefTypes = token.getTokenRefernceType();
/* 129 */     if (!tokenRefTypes.isEmpty()) {
/* 130 */       if (tokenRefTypes.contains("RequireKeyIdentifierReference")) {
/* 131 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 132 */           Constants.logger.log(Level.FINEST, " ReferenceType set to KeyBinding" + kerberosBinding + " is KeyIdentifier");
/*     */         }
/* 134 */         kerberosBinding.setReferenceType(SecurityPolicyUtil.convertToXWSSConstants("RequireKeyIdentifierReference"));
/*     */       } else {
/* 136 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 137 */           Constants.logger.log(Level.FINEST, " ReferenceType " + kerberosBinding + " set is DirectReference");
/*     */         }
/* 139 */         kerberosBinding.setReferenceType("Direct");
/*     */       } 
/*     */     } else {
/* 142 */       if (Constants.logger.isLoggable(Level.FINEST)) {
/* 143 */         Constants.logger.log(Level.FINEST, " ReferenceType set is DirectReference");
/*     */       }
/* 145 */       kerberosBinding.setReferenceType("Direct");
/*     */     } 
/*     */   }
/*     */   protected void setUsernameTokenRefType(AuthenticationTokenPolicy.UsernameTokenBinding untBinding, UsernameToken unToken) {
/* 149 */     untBinding.setReferenceType("Direct");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addKeyBinding(Binding binding, WSSPolicy policy, Token token, boolean ignoreDK) throws PolicyException {
/* 154 */     PolicyAssertion tokenAssertion = (PolicyAssertion)token;
/* 155 */     SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion(tokenAssertion);
/* 156 */     if (PolicyUtil.isUsernameToken(tokenAssertion, spVersion)) {
/* 157 */       AuthenticationTokenPolicy.UsernameTokenBinding untBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/* 158 */       UsernameToken unToken = (UsernameToken)tokenAssertion;
/* 159 */       untBinding.setUUID(token.getTokenId());
/* 160 */       setUsernameTokenRefType(untBinding, unToken);
/*     */       
/* 162 */       setTokenInclusion((KeyBindingBase)untBinding, (Token)tokenAssertion);
/* 163 */       setTokenValueType(untBinding, tokenAssertion);
/* 164 */       untBinding.isOptional(tokenAssertion.isOptional());
/* 165 */       if (unToken.getIssuer() != null) {
/* 166 */         Address addr = unToken.getIssuer().getAddress();
/* 167 */         if (addr != null)
/* 168 */           untBinding.setIssuer(addr.getURI().toString()); 
/* 169 */       } else if (unToken.getIssuerName() != null) {
/* 170 */         untBinding.setIssuer(unToken.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 173 */       if (unToken.getClaims() != null) {
/* 174 */         untBinding.setClaims(unToken.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 177 */       untBinding.setUseCreated(unToken.useCreated());
/* 178 */       untBinding.setUseNonce(unToken.useNonce());
/*     */       
/* 180 */       if (!ignoreDK && unToken.isRequireDerivedKeys()) {
/* 181 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/*     */         
/* 183 */         if (binding instanceof AsymmetricBinding && ((AsymmetricBinding)binding).getInitiatorToken() != null) {
/* 184 */           this.skb.setKeyBinding((MLSPolicy)untBinding);
/* 185 */           dtKB.setOriginalKeyBinding((WSSPolicy)this.skb);
/* 186 */           policy.setKeyBinding((MLSPolicy)dtKB);
/*     */         } else {
/* 188 */           dtKB.setOriginalKeyBinding((WSSPolicy)untBinding);
/* 189 */           policy.setKeyBinding((MLSPolicy)dtKB);
/*     */         } 
/* 191 */         dtKB.setUUID(this.pid.generateID());
/*     */       }
/* 193 */       else if (unToken.isRequireDerivedKeys()) {
/* 194 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 195 */         if (binding instanceof AsymmetricBinding && ((AsymmetricBinding)binding).getInitiatorToken() != null) {
/* 196 */           this.skb.setKeyBinding((MLSPolicy)untBinding);
/* 197 */           dtKB.setOriginalKeyBinding((WSSPolicy)this.skb);
/* 198 */           policy.setKeyBinding((MLSPolicy)dtKB);
/*     */         } else {
/* 200 */           dtKB.setOriginalKeyBinding((WSSPolicy)untBinding);
/* 201 */           policy.setKeyBinding((MLSPolicy)dtKB);
/*     */         } 
/* 203 */         dtKB.setUUID(this.pid.generateID());
/*     */       }
/* 205 */       else if (binding instanceof AsymmetricBinding && ((AsymmetricBinding)binding).getInitiatorToken() != null) {
/* 206 */         this.skb.setKeyBinding((MLSPolicy)untBinding);
/* 207 */         policy.setKeyBinding((MLSPolicy)this.skb);
/*     */       } else {
/* 209 */         policy.setKeyBinding((MLSPolicy)untBinding);
/*     */       }
/*     */     
/*     */     }
/* 213 */     else if (PolicyUtil.isX509Token(tokenAssertion, spVersion)) {
/* 214 */       AuthenticationTokenPolicy.X509CertificateBinding x509CB = new AuthenticationTokenPolicy.X509CertificateBinding();
/*     */       
/* 216 */       X509Token x509Token = (X509Token)tokenAssertion;
/* 217 */       x509CB.setUUID(token.getTokenId());
/* 218 */       setX509TokenRefType(x509CB, x509Token);
/* 219 */       setTokenInclusion((KeyBindingBase)x509CB, (Token)tokenAssertion);
/* 220 */       setTokenValueType(x509CB, tokenAssertion);
/* 221 */       x509CB.isOptional(tokenAssertion.isOptional());
/*     */       
/* 223 */       if (x509Token.getIssuer() != null) {
/* 224 */         Address addr = x509Token.getIssuer().getAddress();
/* 225 */         if (addr != null)
/* 226 */           x509CB.setIssuer(addr.getURI().toString()); 
/* 227 */       } else if (x509Token.getIssuerName() != null) {
/* 228 */         x509CB.setIssuer(x509Token.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 231 */       if (x509Token.getClaims() != null) {
/* 232 */         x509CB.setClaims(x509Token.getClaims().getClaimsAsBytes());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 237 */       if (!ignoreDK && x509Token.isRequireDerivedKeys()) {
/* 238 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 239 */         dtKB.setOriginalKeyBinding((WSSPolicy)x509CB);
/* 240 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 241 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/*     */         
/* 244 */         policy.setKeyBinding((MLSPolicy)x509CB);
/*     */       }
/*     */     
/* 247 */     } else if (PolicyUtil.isSamlToken(tokenAssertion, spVersion)) {
/* 248 */       AuthenticationTokenPolicy.SAMLAssertionBinding sab = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/*     */       
/* 250 */       SamlToken samlToken = (SamlToken)tokenAssertion;
/* 251 */       sab.setUUID(token.getTokenId());
/* 252 */       sab.setSTRID(token.getTokenId());
/* 253 */       sab.setReferenceType("Direct");
/* 254 */       setTokenInclusion((KeyBindingBase)sab, (Token)tokenAssertion);
/* 255 */       sab.isOptional(tokenAssertion.isOptional());
/*     */ 
/*     */       
/* 258 */       if (samlToken.getIssuer() != null) {
/* 259 */         Address addr = samlToken.getIssuer().getAddress();
/* 260 */         if (addr != null)
/* 261 */           sab.setIssuer(addr.getURI().toString()); 
/* 262 */       } else if (samlToken.getIssuerName() != null) {
/* 263 */         sab.setIssuer(samlToken.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 266 */       if (samlToken.getClaims() != null) {
/* 267 */         sab.setClaims(samlToken.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 270 */       if (samlToken.isRequireDerivedKeys()) {
/* 271 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 272 */         dtKB.setOriginalKeyBinding((WSSPolicy)sab);
/* 273 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 274 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 276 */         policy.setKeyBinding((MLSPolicy)sab);
/*     */       } 
/* 278 */     } else if (PolicyUtil.isIssuedToken(tokenAssertion, spVersion)) {
/* 279 */       IssuedTokenKeyBinding itkb = new IssuedTokenKeyBinding();
/* 280 */       setTokenInclusion((KeyBindingBase)itkb, (Token)tokenAssertion);
/*     */       
/* 282 */       itkb.setUUID(((Token)tokenAssertion).getTokenId());
/* 283 */       itkb.setSTRID(token.getTokenId());
/* 284 */       IssuedToken it = (IssuedToken)tokenAssertion;
/* 285 */       itkb.isOptional(tokenAssertion.isOptional());
/* 286 */       if (it.getRequestSecurityTokenTemplate() != null) {
/* 287 */         itkb.setTokenType(it.getRequestSecurityTokenTemplate().getTokenType());
/*     */       }
/*     */       
/* 290 */       if (it.getIssuer() != null) {
/* 291 */         Address addr = it.getIssuer().getAddress();
/* 292 */         if (addr != null)
/* 293 */           itkb.setIssuer(addr.getURI().toString()); 
/* 294 */       } else if (it.getIssuerName() != null) {
/* 295 */         itkb.setIssuer(it.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 298 */       if (it.getClaims() != null) {
/* 299 */         itkb.setClaims(it.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 302 */       if (it.isRequireDerivedKeys()) {
/* 303 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 304 */         dtKB.setOriginalKeyBinding((WSSPolicy)itkb);
/* 305 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 306 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 308 */         policy.setKeyBinding((MLSPolicy)itkb);
/*     */       } 
/* 310 */     } else if (PolicyUtil.isSecureConversationToken(tokenAssertion, spVersion)) {
/* 311 */       SecureConversationTokenKeyBinding sct = new SecureConversationTokenKeyBinding();
/* 312 */       SecureConversationToken sctPolicy = (SecureConversationToken)tokenAssertion;
/* 313 */       sct.isOptional(tokenAssertion.isOptional());
/* 314 */       if (sctPolicy.getIssuer() != null) {
/* 315 */         Address addr = sctPolicy.getIssuer().getAddress();
/* 316 */         if (addr != null)
/* 317 */           sct.setIssuer(addr.getURI().toString()); 
/* 318 */       } else if (sctPolicy.getIssuerName() != null) {
/* 319 */         sct.setIssuer(sctPolicy.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 322 */       if (sctPolicy.getClaims() != null) {
/* 323 */         sct.setClaims(sctPolicy.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 326 */       if (sctPolicy.isRequireDerivedKeys()) {
/* 327 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 328 */         dtKB.setOriginalKeyBinding((WSSPolicy)sct);
/* 329 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 330 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 332 */         policy.setKeyBinding((MLSPolicy)sct);
/*     */       } 
/* 334 */       setTokenInclusion((KeyBindingBase)sct, (Token)tokenAssertion);
/*     */       
/* 336 */       sct.setUUID(((Token)tokenAssertion).getTokenId());
/*     */     }
/* 338 */     else if (PolicyUtil.isKerberosToken(tokenAssertion, spVersion)) {
/* 339 */       AuthenticationTokenPolicy.KerberosTokenBinding kerbBinding = new AuthenticationTokenPolicy.KerberosTokenBinding();
/* 340 */       KerberosToken kerbToken = (KerberosToken)tokenAssertion;
/* 341 */       kerbBinding.setUUID(token.getTokenId());
/* 342 */       setTokenInclusion((KeyBindingBase)kerbBinding, (Token)tokenAssertion);
/* 343 */       setTokenValueType(kerbBinding, tokenAssertion);
/* 344 */       kerbBinding.isOptional(tokenAssertion.isOptional());
/*     */       
/* 346 */       if (kerbToken.getIssuer() != null) {
/* 347 */         Address addr = kerbToken.getIssuer().getAddress();
/* 348 */         if (addr != null)
/* 349 */           kerbBinding.setIssuer(addr.getURI().toString()); 
/* 350 */       } else if (kerbToken.getIssuerName() != null) {
/* 351 */         kerbBinding.setIssuer(kerbToken.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 354 */       if (kerbToken.getClaims() != null) {
/* 355 */         kerbBinding.setClaims(kerbToken.getClaims().getClaimsAsBytes());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (!ignoreDK && kerbToken.isRequireDerivedKeys()) {
/* 361 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 362 */         dtKB.setOriginalKeyBinding((WSSPolicy)kerbBinding);
/* 363 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 364 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/*     */         
/* 367 */         policy.setKeyBinding((MLSPolicy)kerbBinding);
/*     */       }
/*     */     
/* 370 */     } else if (PolicyUtil.isRsaToken((PolicyAssertion)token, spVersion)) {
/* 371 */       AuthenticationTokenPolicy.KeyValueTokenBinding rsaTB = new AuthenticationTokenPolicy.KeyValueTokenBinding();
/* 372 */       RsaToken rsaToken = (RsaToken)tokenAssertion;
/* 373 */       rsaTB.isOptional(tokenAssertion.isOptional());
/* 374 */       rsaTB.setUUID(token.getTokenId());
/* 375 */       setTokenInclusion((KeyBindingBase)rsaTB, (Token)tokenAssertion);
/* 376 */       policy.setKeyBinding((MLSPolicy)rsaTB);
/* 377 */     } else if (PolicyUtil.isKeyValueToken((PolicyAssertion)token, spVersion)) {
/* 378 */       AuthenticationTokenPolicy.KeyValueTokenBinding rsaTB = new AuthenticationTokenPolicy.KeyValueTokenBinding();
/* 379 */       KeyValueToken rsaToken = (KeyValueToken)tokenAssertion;
/* 380 */       rsaTB.setUUID(token.getTokenId());
/* 381 */       rsaTB.isOptional(tokenAssertion.isOptional());
/* 382 */       setTokenInclusion((KeyBindingBase)rsaTB, (Token)tokenAssertion);
/* 383 */       policy.setKeyBinding((MLSPolicy)rsaTB);
/*     */     } else {
/* 385 */       throw new UnsupportedOperationException("addKeyBinding for " + token + "is not supported");
/*     */     } 
/* 387 */     if (Constants.logger.isLoggable(Level.FINEST)) {
/* 388 */       Constants.logger.log(Level.FINEST, "KeyBinding type " + policy.getKeyBinding() + "has been added to policy" + policy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setTokenInclusion(KeyBindingBase xwssToken, Token token) throws PolicyException {
/* 394 */     boolean change = false;
/* 395 */     SecurityPolicyVersion spVersion = token.getSecurityPolicyVersion();
/* 396 */     String iTokenType = token.getIncludeToken();
/* 397 */     if (this.isServer && !this.isIncoming) {
/* 398 */       if (!spVersion.includeTokenAlways.equals(iTokenType)) {
/* 399 */         if (iTokenType.endsWith("AlwaysToInitiator")) {
/* 400 */           xwssToken.setIncludeToken(spVersion.includeTokenAlwaysToRecipient);
/* 401 */           if (Constants.logger.isLoggable(Level.FINEST)) {
/* 402 */             Constants.logger.log(Level.FINEST, "Token Inclusion value of INCLUDE ONCE has been set to Token" + xwssToken);
/*     */           }
/*     */           return;
/*     */         } 
/* 406 */         xwssToken.setIncludeToken(spVersion.includeTokenNever);
/* 407 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 408 */           Constants.logger.log(Level.FINEST, "Token Inclusion value of INCLUDE NEVER has been set to Token" + xwssToken);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 413 */     } else if (!this.isServer && this.isIncoming) {
/* 414 */       if (spVersion.includeTokenAlwaysToRecipient.equals(iTokenType) || spVersion.includeTokenOnce.equals(iTokenType)) {
/*     */         
/* 416 */         xwssToken.setIncludeToken(spVersion.includeTokenNever);
/* 417 */         if (Constants.logger.isLoggable(Level.FINEST))
/* 418 */           Constants.logger.log(Level.FINEST, "Token Inclusion value of INCLUDE NEVER has been set to Token" + xwssToken); 
/*     */         return;
/*     */       } 
/* 421 */       if (iTokenType.endsWith("AlwaysToInitiator")) {
/* 422 */         xwssToken.setIncludeToken(spVersion.includeTokenAlwaysToRecipient);
/* 423 */         if (Constants.logger.isLoggable(Level.FINEST)) {
/* 424 */           Constants.logger.log(Level.FINEST, "Token Inclusion value of INCLUDE ONCE has been set to Token" + xwssToken);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 430 */     if (Constants.logger.isLoggable(Level.FINEST)) {
/* 431 */       Constants.logger.log(Level.FINEST, "Token Inclusion value of" + iTokenType + " has been set to Token" + xwssToken);
/*     */     }
/* 433 */     if (spVersion == SecurityPolicyVersion.SECURITYPOLICY200507) {
/* 434 */       xwssToken.setIncludeToken(iTokenType);
/*     */     
/*     */     }
/* 437 */     else if (spVersion.includeTokenAlways.equals(iTokenType)) {
/* 438 */       xwssToken.setIncludeToken(spVersion.includeTokenAlways);
/* 439 */     } else if (spVersion.includeTokenAlwaysToRecipient.equals(iTokenType)) {
/* 440 */       xwssToken.setIncludeToken(spVersion.includeTokenAlwaysToRecipient);
/* 441 */     } else if (spVersion.includeTokenNever.equals(iTokenType)) {
/* 442 */       xwssToken.setIncludeToken(spVersion.includeTokenNever);
/* 443 */     } else if (spVersion.includeTokenOnce.equals(iTokenType)) {
/* 444 */       xwssToken.setIncludeToken(spVersion.includeTokenOnce);
/* 445 */     } else if (iTokenType.endsWith("AlwaysToInitiator")) {
/* 446 */       xwssToken.setIncludeToken(spVersion.includeTokenNever);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WSSPolicy getWSSToken(Token token) throws PolicyException {
/* 453 */     SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion((PolicyAssertion)token);
/* 454 */     if (PolicyUtil.isUsernameToken((PolicyAssertion)token, spVersion)) {
/* 455 */       AuthenticationTokenPolicy.UsernameTokenBinding key = null;
/* 456 */       key = new AuthenticationTokenPolicy.UsernameTokenBinding();
/*     */       try {
/* 458 */         key.newTimestampFeatureBinding();
/* 459 */       } catch (PolicyGenerationException ex) {
/* 460 */         throw new PolicyException(ex);
/*     */       } 
/* 462 */       key.setUUID(token.getTokenId());
/* 463 */       key.isOptional(((PolicyAssertion)token).isOptional());
/* 464 */       setTokenInclusion((KeyBindingBase)key, token);
/* 465 */       UserNameToken ut = (UserNameToken)token;
/* 466 */       key.setUseCreated(ut.useCreated());
/* 467 */       key.setUseNonce(ut.useNonce());
/* 468 */       if (!ut.hasPassword()) {
/* 469 */         key.setNoPassword(true);
/* 470 */       } else if (ut.useHashPassword()) {
/* 471 */         key.setDigestOn(true);
/* 472 */         key.setUseNonce(true);
/*     */       } 
/*     */       
/* 475 */       if (ut.getIssuer() != null) {
/* 476 */         Address addr = ut.getIssuer().getAddress();
/* 477 */         if (addr != null)
/* 478 */           key.setIssuer(addr.getURI().toString()); 
/* 479 */       } else if (ut.getIssuerName() != null) {
/* 480 */         key.setIssuer(ut.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 483 */       if (ut.getClaims() != null) {
/* 484 */         key.setClaims(ut.getClaims().getClaimsAsBytes());
/*     */       }
/*     */ 
/*     */       
/* 488 */       return (WSSPolicy)key;
/* 489 */     }  if (PolicyUtil.isSamlToken((PolicyAssertion)token, spVersion)) {
/* 490 */       AuthenticationTokenPolicy.SAMLAssertionBinding key = null;
/* 491 */       key = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/* 492 */       setTokenInclusion((KeyBindingBase)key, token);
/* 493 */       key.setAssertionType("SV");
/*     */       
/* 495 */       key.setUUID(token.getTokenId());
/* 496 */       key.setSTRID(token.getTokenId());
/* 497 */       key.isOptional(((PolicyAssertion)token).isOptional());
/* 498 */       SamlToken samlToken = (SamlToken)token;
/* 499 */       if (samlToken.getIssuer() != null) {
/* 500 */         Address addr = samlToken.getIssuer().getAddress();
/* 501 */         if (addr != null)
/* 502 */           key.setIssuer(addr.getURI().toString()); 
/* 503 */       } else if (samlToken.getIssuerName() != null) {
/* 504 */         key.setIssuer(samlToken.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 507 */       if (samlToken.getClaims() != null) {
/* 508 */         key.setClaims(samlToken.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 511 */       return (WSSPolicy)key;
/* 512 */     }  if (PolicyUtil.isIssuedToken((PolicyAssertion)token, spVersion)) {
/* 513 */       IssuedTokenKeyBinding key = new IssuedTokenKeyBinding();
/* 514 */       setTokenInclusion((KeyBindingBase)key, token);
/*     */       
/* 516 */       key.setUUID(token.getTokenId());
/* 517 */       key.setSTRID(token.getTokenId());
/*     */       
/* 519 */       IssuedToken it = (IssuedToken)token;
/* 520 */       if (it.getIssuer() != null) {
/* 521 */         Address addr = it.getIssuer().getAddress();
/* 522 */         if (addr != null)
/* 523 */           key.setIssuer(addr.getURI().toString()); 
/* 524 */       } else if (it.getIssuerName() != null) {
/* 525 */         key.setIssuer(it.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 528 */       if (it.getClaims() != null) {
/* 529 */         key.setClaims(it.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 532 */       return (WSSPolicy)key;
/* 533 */     }  if (PolicyUtil.isSecureConversationToken((PolicyAssertion)token, spVersion)) {
/* 534 */       SecureConversationTokenKeyBinding key = new SecureConversationTokenKeyBinding();
/* 535 */       setTokenInclusion((KeyBindingBase)key, token);
/*     */       
/* 537 */       key.setUUID(token.getTokenId());
/* 538 */       key.isOptional(((PolicyAssertion)token).isOptional());
/* 539 */       SecureConversationToken sct = (SecureConversationToken)token;
/* 540 */       if (sct.getIssuer() != null) {
/* 541 */         Address addr = sct.getIssuer().getAddress();
/* 542 */         if (addr != null)
/* 543 */           key.setIssuer(addr.getURI().toString()); 
/* 544 */       } else if (sct.getIssuerName() != null) {
/* 545 */         key.setIssuer(sct.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 548 */       if (sct.getClaims() != null) {
/* 549 */         key.setClaims(sct.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 552 */       return (WSSPolicy)key;
/* 553 */     }  if (PolicyUtil.isX509Token((PolicyAssertion)token, spVersion)) {
/* 554 */       AuthenticationTokenPolicy.X509CertificateBinding xt = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 555 */       xt.setUUID(token.getTokenId());
/*     */       
/* 557 */       setTokenInclusion((KeyBindingBase)xt, token);
/* 558 */       setX509TokenRefType(xt, (X509Token)token);
/* 559 */       xt.isOptional(((PolicyAssertion)token).isOptional());
/* 560 */       X509Token x509Token = (X509Token)token;
/* 561 */       if (x509Token.getIssuer() != null) {
/* 562 */         Address addr = x509Token.getIssuer().getAddress();
/* 563 */         if (addr != null)
/* 564 */           xt.setIssuer(addr.getURI().toString()); 
/* 565 */       } else if (x509Token.getIssuerName() != null) {
/* 566 */         xt.setIssuer(x509Token.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 569 */       if (x509Token.getClaims() != null) {
/* 570 */         xt.setClaims(x509Token.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 573 */       return (WSSPolicy)xt;
/* 574 */     }  if (PolicyUtil.isRsaToken((PolicyAssertion)token, spVersion) || PolicyUtil.isKeyValueToken((PolicyAssertion)token, spVersion)) {
/* 575 */       AuthenticationTokenPolicy.KeyValueTokenBinding rsaToken = new AuthenticationTokenPolicy.KeyValueTokenBinding();
/* 576 */       rsaToken.setUUID(token.getTokenId());
/* 577 */       setTokenInclusion((KeyBindingBase)rsaToken, token);
/*     */       
/* 579 */       return (WSSPolicy)rsaToken;
/*     */     } 
/* 581 */     if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 582 */       Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0107_UNKNOWN_TOKEN_TYPE(token));
/*     */     }
/*     */     
/* 585 */     throw new UnsupportedOperationException("Unsupported  " + token + "format");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTokenValueType(AuthenticationTokenPolicy.X509CertificateBinding x509CB, PolicyAssertion tokenAssertion) {
/* 590 */     NestedPolicy policy = tokenAssertion.getNestedPolicy();
/* 591 */     if (policy == null) {
/*     */       return;
/*     */     }
/* 594 */     AssertionSet as = policy.getAssertionSet();
/* 595 */     Iterator<PolicyAssertion> itr = as.iterator();
/* 596 */     while (itr.hasNext()) {
/* 597 */       PolicyAssertion policyAssertion = itr.next();
/* 598 */       if (policyAssertion.getName().getLocalPart().equals("WssX509V1Token11") || policyAssertion.getName().getLocalPart().equals("WssX509V1Token10")) {
/* 599 */         x509CB.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1"); continue;
/* 600 */       }  if (policyAssertion.getName().getLocalPart().equals("WssX509V3Token10") || policyAssertion.getName().getLocalPart().equals("WssX509V3Token11")) {
/* 601 */         x509CB.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTokenValueType(AuthenticationTokenPolicy.KerberosTokenBinding kerberosBinding, PolicyAssertion tokenAssertion) {
/* 608 */     NestedPolicy policy = tokenAssertion.getNestedPolicy();
/* 609 */     if (policy == null) {
/*     */       return;
/*     */     }
/* 612 */     AssertionSet as = policy.getAssertionSet();
/* 613 */     Iterator<PolicyAssertion> itr = as.iterator();
/* 614 */     while (itr.hasNext()) {
/* 615 */       PolicyAssertion policyAssertion = itr.next();
/* 616 */       if (policyAssertion.getName().getLocalPart().equals("WssKerberosV5ApReqToken11")) {
/* 617 */         kerberosBinding.setValueType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ"); continue;
/* 618 */       }  if (policyAssertion.getName().getLocalPart().equals("WssGssKerberosV5ApReqToken11")) {
/* 619 */         kerberosBinding.setValueType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void setTokenValueType(AuthenticationTokenPolicy.UsernameTokenBinding utb, PolicyAssertion tokenAssertion) {
/* 625 */     NestedPolicy policy = tokenAssertion.getNestedPolicy();
/* 626 */     if (policy == null) {
/*     */       return;
/*     */     }
/* 629 */     AssertionSet as = policy.getAssertionSet();
/* 630 */     Iterator<PolicyAssertion> itr = as.iterator();
/* 631 */     while (itr.hasNext()) {
/* 632 */       PolicyAssertion policyAssertion = itr.next();
/* 633 */       if (policyAssertion.getName().getLocalPart().equals("WssUsernameToken10") || policyAssertion.getName().getLocalPart().equals("WssUsernameToken11")) {
/* 634 */         utb.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0"); continue;
/* 635 */       }  if (policyAssertion.getName().getLocalPart().equals("WssUsernameToken10") || policyAssertion.getName().getLocalPart().equals("WssUsernameToken11"))
/* 636 */         utb.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\TokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */