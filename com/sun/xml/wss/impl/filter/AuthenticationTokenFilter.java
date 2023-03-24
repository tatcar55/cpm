/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.X509TokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*     */ import com.sun.xml.wss.core.UsernameToken;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.HarnessUtil;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*     */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPPart;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthenticationTokenFilter
/*     */ {
/*  96 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.filter", "com.sun.xml.wss.logging.impl.filter.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processUserNameToken(FilterProcessingContext context) throws XWSSecurityException {
/* 106 */     if (context.isInboundMessage()) {
/* 107 */       getUserNameTokenFromMessage(context);
/*     */     } else {
/* 109 */       addUserNameTokenToMessage(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processSamlToken(FilterProcessingContext context) throws XWSSecurityException {
/* 118 */     if (context.isInboundMessage()) {
/* 119 */       ImportSamlAssertionFilter.process(context);
/*     */     } else {
/* 121 */       ExportSamlAssertionFilter.process(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processIssuedToken(FilterProcessingContext context) throws XWSSecurityException {
/* 131 */     if (!context.isInboundMessage()) {
/* 132 */       addIssuedTokenToMessage(context);
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
/*     */   private static void getUserNameTokenFromMessage(FilterProcessingContext context) throws XWSSecurityException {
/* 144 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/* 145 */     SecurityHeader wsseSecurity = secureMessage.findSecurityHeader();
/* 146 */     UsernameToken token = null;
/*     */     
/* 148 */     if (context.getMode() == 0) {
/*     */       
/* 150 */       if (context.makeDynamicPolicyCallback()) {
/*     */         
/*     */         try {
/* 153 */           AuthenticationTokenPolicy authenticationTokenPolicy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/*     */ 
/*     */ 
/*     */           
/* 157 */           AuthenticationTokenPolicy.UsernameTokenBinding userNamePolicy = (AuthenticationTokenPolicy.UsernameTokenBinding)authenticationTokenPolicy.getFeatureBinding();
/*     */           
/* 159 */           userNamePolicy.isReadOnly(true);
/*     */           
/* 161 */           DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */           
/* 164 */           dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 165 */           dynamicContext.inBoundMessage(true);
/* 166 */           DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)userNamePolicy, (DynamicPolicyContext)dynamicContext);
/*     */           
/* 168 */           ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 169 */           HarnessUtil.makeDynamicPolicyCallback(dynamicCallback, context.getSecurityEnvironment().getCallbackHandler());
/*     */ 
/*     */           
/* 172 */           authenticationTokenPolicy.setFeatureBinding((MLSPolicy)dynamicCallback.getSecurityPolicy());
/*     */         }
/* 174 */         catch (Exception e) {
/* 175 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1427_ERROR_ADHOC(), e);
/* 176 */           throw new XWSSecurityException(e);
/*     */         } 
/*     */       }
/* 179 */       AuthenticationTokenPolicy policy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/*     */       
/* 181 */       NodeList nodeList = wsseSecurity.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
/*     */       
/* 183 */       if (nodeList.getLength() <= 0) {
/* 184 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1400_NOUSERNAME_FOUND());
/* 185 */         throw new XWSSecurityException("No Username token found ,Receiver requirement not met");
/* 186 */       }  if (nodeList.getLength() > 1) {
/* 187 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1401_MORETHANONE_USERNAME_FOUND());
/* 188 */         throw new XWSSecurityException("More than one Username token found, Receiver requirement not met");
/*     */       } 
/*     */       
/* 191 */       SOAPElement userNameTokenElement = (SOAPElement)nodeList.item(0);
/* 192 */       token = new UsernameToken(userNameTokenElement, policy.isBSP());
/* 193 */       token.isBSP(policy.isBSP());
/*     */     }
/*     */     else {
/*     */       
/* 197 */       if (context.getMode() == 1) {
/* 198 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1402_ERROR_POSTHOC());
/* 199 */         throw new XWSSecurityException("Internal Error: Called UsernameTokenFilter in POSTHOC Mode");
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 204 */         token = new UsernameToken(wsseSecurity.getCurrentHeaderElement());
/* 205 */       } catch (XWSSecurityException ex) {
/* 206 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1403_IMPORT_USERNAME_TOKEN(), (Throwable)ex);
/* 207 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Exception while importing Username Password Token", ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     String username = token.getUsername();
/* 215 */     String password = token.getPassword();
/* 216 */     String passwordDigest = token.getPasswordDigest();
/* 217 */     String passwordType = token.getPasswordType();
/* 218 */     String nonce = token.getNonce();
/* 219 */     String created = token.getCreated();
/* 220 */     boolean authenticated = false;
/*     */     
/* 222 */     if (context.getMode() == 0) {
/*     */       
/* 224 */       AuthenticationTokenPolicy policy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/* 225 */       AuthenticationTokenPolicy.UsernameTokenBinding utBinding = (AuthenticationTokenPolicy.UsernameTokenBinding)policy.getFeatureBinding();
/*     */ 
/*     */ 
/*     */       
/* 229 */       if (utBinding.getDigestOn() && passwordDigest == null) {
/* 230 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1404_NOTMET_DIGESTED());
/* 231 */         throw new XWSSecurityException("Receiver Requirement for Digested Password has not been met");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 236 */       if (!utBinding.getDigestOn() && passwordDigest != null) {
/* 237 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1405_NOTMET_PLAINTEXT());
/* 238 */         throw new XWSSecurityException("Receiver Requirement for Plain-Text Password has not been met, Received token has Password-Digest");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 243 */       if (utBinding.getUseNonce() && nonce == null) {
/* 244 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1406_NOTMET_NONCE());
/* 245 */         throw new XWSSecurityException("Receiver Requirement for nonce has not been met");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 250 */       if (!utBinding.getUseNonce() && nonce != null) {
/* 251 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1407_NOTMET_NONONCE());
/* 252 */         throw new XWSSecurityException("Receiver Requirement for no nonce has not been met, Received token has a nonce specified");
/*     */       }
/*     */     
/*     */     }
/* 256 */     else if (context.getMode() == 3) {
/*     */       
/* 258 */       AuthenticationTokenPolicy.UsernameTokenBinding sp = new AuthenticationTokenPolicy.UsernameTokenBinding();
/* 259 */       if (passwordDigest != null) {
/* 260 */         sp.setDigestOn(true);
/*     */       }
/* 262 */       if (nonce != null) {
/* 263 */         sp.setUseNonce(true);
/*     */       }
/* 265 */       context.getInferredSecurityPolicy().append((SecurityPolicy)sp);
/*     */     } 
/*     */     
/*     */     try {
/* 269 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText" == passwordType) {
/* 270 */         authenticated = context.getSecurityEnvironment().authenticateUser(context.getExtraneousProperties(), username, password);
/*     */       } else {
/* 272 */         authenticated = context.getSecurityEnvironment().authenticateUser(context.getExtraneousProperties(), username, passwordDigest, nonce, created);
/*     */       } 
/*     */ 
/*     */       
/* 276 */       if (!authenticated) {
/* 277 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1408_FAILED_SENDER_AUTHENTICATION());
/* 278 */         XWSSecurityException xwse = new XWSSecurityException("Invalid Username Password Pair");
/*     */         
/* 280 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Authentication of Username Password Token Failed", xwse);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 286 */       if (log.isLoggable(Level.FINEST)) {
/* 287 */         log.log(Level.FINEST, "Password Validated.....");
/*     */       }
/*     */       
/* 290 */       long maxClockSkew = 300000L;
/* 291 */       long freshnessLmt = 300000L;
/* 292 */       long maxNonceAge = 900000L;
/*     */       
/* 294 */       if (context.getMode() == 0) {
/*     */         
/* 296 */         AuthenticationTokenPolicy authPolicy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/*     */ 
/*     */         
/* 299 */         AuthenticationTokenPolicy.UsernameTokenBinding policy = (AuthenticationTokenPolicy.UsernameTokenBinding)authPolicy.getFeatureBinding();
/*     */ 
/*     */ 
/*     */         
/* 303 */         if (created != null) {
/* 304 */           TimestampPolicy tPolicy = (TimestampPolicy)policy.getFeatureBinding();
/* 305 */           maxClockSkew = tPolicy.getMaxClockSkew();
/* 306 */           freshnessLmt = tPolicy.getTimestampFreshness();
/*     */         } 
/* 308 */         maxNonceAge = policy.getMaxNonceAge();
/*     */       } 
/*     */       
/* 311 */       if (created != null) {
/* 312 */         context.getSecurityEnvironment().validateCreationTime(context.getExtraneousProperties(), created, maxClockSkew, freshnessLmt);
/*     */       }
/*     */ 
/*     */       
/* 316 */       if (log.isLoggable(Level.FINEST) && created != null) {
/* 317 */         log.log(Level.FINEST, "CreationTime Validated.....");
/*     */       }
/*     */       
/* 320 */       if (nonce != null) {
/*     */         try {
/* 322 */           if (!context.getSecurityEnvironment().validateAndCacheNonce(context.getExtraneousProperties(), nonce, created, maxNonceAge)) {
/* 323 */             XWSSecurityException xwse = new XWSSecurityException("Invalid/Repeated Nonce value for Username Token");
/*     */ 
/*     */             
/* 326 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1406_NOTMET_NONCE(), (Throwable)xwse);
/* 327 */             throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Invalid/Repeated Nonce value for Username Token", xwse);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 332 */         catch (com.sun.xml.wss.NonceManager.NonceException ex) {
/* 333 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1406_NOTMET_NONCE(), (Throwable)ex);
/* 334 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Invalid/Repeated Nonce value for Username Token", ex);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     }
/* 341 */     catch (XWSSecurityException xwsse) {
/* 342 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1408_FAILED_SENDER_AUTHENTICATION(), (Throwable)xwsse);
/* 343 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, xwsse.getMessage(), xwsse);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), username, password);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticationTokenPolicy.UsernameTokenBinding resolveUserNameTokenData(FilterProcessingContext context, UsernameToken token, UsernameToken unToken, AuthenticationTokenPolicy policy) throws XWSSecurityException {
/* 370 */     if (!context.makeDynamicPolicyCallback()) {
/*     */ 
/*     */ 
/*     */       
/* 374 */       AuthenticationTokenPolicy.UsernameTokenBinding userNamePolicy = (AuthenticationTokenPolicy.UsernameTokenBinding)policy.getFeatureBinding();
/*     */       
/* 376 */       String userName = userNamePolicy.getUsername();
/* 377 */       String password = userNamePolicy.getPassword();
/*     */       
/* 379 */       if (userName == null || "".equals(userName)) {
/* 380 */         userName = context.getSecurityEnvironment().getUsername(context.getExtraneousProperties());
/*     */       }
/*     */       
/* 383 */       if (userName == null || "".equals(userName)) {
/* 384 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1409_INVALID_USERNAME_TOKEN());
/* 385 */         throw new XWSSecurityException("Username has not been set");
/*     */       } 
/*     */       
/* 388 */       if (token != null) {
/* 389 */         token.setUsername(userName);
/*     */       } else {
/* 391 */         unToken.setUsernameValue(userName);
/*     */       } 
/* 393 */       if (!userNamePolicy.hasNoPassword() && (password == null || "".equals(password))) {
/* 394 */         password = context.getSecurityEnvironment().getPassword(context.getExtraneousProperties());
/*     */       }
/* 396 */       if (!userNamePolicy.hasNoPassword()) {
/* 397 */         if (password == null) {
/* 398 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1424_INVALID_USERNAME_TOKEN());
/* 399 */           throw new XWSSecurityException("Password for the username has not been set");
/*     */         } 
/* 401 */         if (token != null) {
/* 402 */           token.setPassword(password);
/*     */         } else {
/* 404 */           unToken.setPasswordValue(password);
/*     */         } 
/* 406 */       }  return userNamePolicy;
/*     */     } 
/*     */     
/*     */     try {
/* 410 */       AuthenticationTokenPolicy.UsernameTokenBinding userNamePolicy = (AuthenticationTokenPolicy.UsernameTokenBinding)policy.getFeatureBinding();
/*     */       
/* 412 */       userNamePolicy.isReadOnly(true);
/*     */       
/* 414 */       DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */       
/* 417 */       dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 418 */       dynamicContext.inBoundMessage(false);
/* 419 */       DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)userNamePolicy, (DynamicPolicyContext)dynamicContext);
/*     */       
/* 421 */       ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 422 */       HarnessUtil.makeDynamicPolicyCallback(dynamicCallback, context.getSecurityEnvironment().getCallbackHandler());
/*     */ 
/*     */ 
/*     */       
/* 426 */       AuthenticationTokenPolicy.UsernameTokenBinding resolvedPolicy = (AuthenticationTokenPolicy.UsernameTokenBinding)dynamicCallback.getSecurityPolicy();
/*     */ 
/*     */       
/* 429 */       if (token != null) {
/* 430 */         token.setUsername(resolvedPolicy.getUsername());
/* 431 */         token.setPassword(resolvedPolicy.getPassword());
/*     */       } else {
/* 433 */         unToken.setUsernameValue(resolvedPolicy.getUsername());
/* 434 */         unToken.setPasswordValue(resolvedPolicy.getPassword());
/*     */       } 
/* 436 */       return resolvedPolicy;
/*     */     }
/* 438 */     catch (Exception e) {
/* 439 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1403_IMPORT_USERNAME_TOKEN(), e);
/* 440 */       throw new XWSSecurityException(e);
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
/*     */   
/*     */   public static void addUserNameTokenToMessage(FilterProcessingContext context) throws XWSSecurityException {
/* 455 */     if (context instanceof JAXBFilterProcessingContext) {
/* 456 */       JAXBFilterProcessingContext opContext = (JAXBFilterProcessingContext)context;
/* 457 */       SecurityHeader secHeader = opContext.getSecurityHeader();
/*     */       
/* 459 */       AuthenticationTokenPolicy authPolicy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/* 460 */       UsernameToken unToken = new UsernameToken(opContext.getSOAPVersion());
/*     */ 
/*     */       
/* 463 */       AuthenticationTokenPolicy.UsernameTokenBinding policy = resolveUserNameTokenData((FilterProcessingContext)opContext, null, unToken, authPolicy);
/*     */ 
/*     */       
/* 466 */       if (policy.getUseNonce()) {
/* 467 */         unToken.setNonce(policy.getNonce());
/*     */       }
/* 469 */       if (policy.getDigestOn()) {
/* 470 */         unToken.setDigestOn();
/*     */       }
/*     */       
/* 473 */       if (policy.getUseNonce() || policy.getDigestOn() || policy.getUseCreated()) {
/* 474 */         String creationTime = "";
/* 475 */         TimestampPolicy tPolicy = (TimestampPolicy)policy.getFeatureBinding();
/* 476 */         creationTime = tPolicy.getCreationTime();
/* 477 */         unToken.setCreationTime(creationTime);
/*     */       } 
/*     */ 
/*     */       
/* 481 */       if (policy.hasNoPassword()) {
/* 482 */         String creationTime = "";
/* 483 */         TimestampPolicy tPolicy = (TimestampPolicy)policy.getFeatureBinding();
/* 484 */         creationTime = tPolicy.getCreationTime();
/* 485 */         unToken.setCreationTime(creationTime);
/*     */       } 
/*     */       
/* 488 */       String wsuId = policy.getUUID();
/* 489 */       if (wsuId != null && !wsuId.equals("")) {
/* 490 */         unToken.setId(wsuId);
/*     */       }
/* 492 */       secHeader.add((SecurityHeaderElement)unToken);
/*     */     } else {
/* 494 */       SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/* 495 */       SOAPPart soapPart = secureMessage.getSOAPPart();
/*     */       
/* 497 */       AuthenticationTokenPolicy authPolicy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/* 498 */       UsernameToken token = new UsernameToken(soapPart, "");
/*     */       
/* 500 */       AuthenticationTokenPolicy.UsernameTokenBinding policy = resolveUserNameTokenData(context, token, null, authPolicy);
/*     */ 
/*     */       
/* 503 */       if (policy.getUseNonce()) {
/* 504 */         token.setNonce(policy.getNonce());
/*     */       }
/* 506 */       if (policy.getDigestOn()) {
/* 507 */         token.setDigestOn();
/*     */       }
/*     */       
/* 510 */       if (policy.getUseNonce() || policy.getDigestOn() || policy.getUseCreated()) {
/* 511 */         String creationTime = "";
/* 512 */         TimestampPolicy tPolicy = (TimestampPolicy)policy.getFeatureBinding();
/* 513 */         creationTime = tPolicy.getCreationTime();
/* 514 */         token.setCreationTime(creationTime);
/*     */       } 
/*     */       
/* 517 */       if (policy.hasNoPassword()) {
/* 518 */         String creationTime = "";
/* 519 */         TimestampPolicy tPolicy = (TimestampPolicy)policy.getFeatureBinding();
/* 520 */         creationTime = tPolicy.getCreationTime();
/* 521 */         token.setCreationTime(creationTime);
/*     */       } 
/* 523 */       SecurityHeader wsseSecurity = secureMessage.findOrCreateSecurityHeader();
/* 524 */       String wsuId = policy.getUUID();
/* 525 */       if (wsuId != null && !wsuId.equals("")) {
/* 526 */         XMLUtil.setWsuIdAttr(token.getAsSoapElement(), wsuId);
/*     */       }
/* 528 */       wsseSecurity.insertHeaderBlock((SecurityHeaderBlock)token);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addIssuedTokenToMessage(FilterProcessingContext context) throws XWSSecurityException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual getSecurityPolicy : ()Lcom/sun/xml/wss/impl/policy/SecurityPolicy;
/*     */     //   4: checkcast com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy
/*     */     //   7: astore_1
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual getFeatureBinding : ()Lcom/sun/xml/wss/impl/policy/MLSPolicy;
/*     */     //   12: checkcast com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding
/*     */     //   15: astore_2
/*     */     //   16: aload_2
/*     */     //   17: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: pop
/*     */     //   23: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*     */     //   26: aload_3
/*     */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   30: ifne -> 69
/*     */     //   33: aload_2
/*     */     //   34: pop
/*     */     //   35: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*     */     //   38: aload_3
/*     */     //   39: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   42: ifne -> 69
/*     */     //   45: aload_2
/*     */     //   46: pop
/*     */     //   47: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*     */     //   50: aload_3
/*     */     //   51: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   54: ifne -> 69
/*     */     //   57: aload_2
/*     */     //   58: pop
/*     */     //   59: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*     */     //   62: aload_3
/*     */     //   63: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   66: ifeq -> 73
/*     */     //   69: iconst_1
/*     */     //   70: goto -> 74
/*     */     //   73: iconst_0
/*     */     //   74: istore #4
/*     */     //   76: aload_0
/*     */     //   77: instanceof com/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext
/*     */     //   80: ifeq -> 391
/*     */     //   83: aload_0
/*     */     //   84: checkcast com/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext
/*     */     //   87: astore #5
/*     */     //   89: aload #5
/*     */     //   91: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*     */     //   94: astore #6
/*     */     //   96: aconst_null
/*     */     //   97: astore #7
/*     */     //   99: aconst_null
/*     */     //   100: astore #8
/*     */     //   102: aload #5
/*     */     //   104: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*     */     //   107: ifnonnull -> 149
/*     */     //   110: aload_2
/*     */     //   111: invokevirtual getUUID : ()Ljava/lang/String;
/*     */     //   114: astore #9
/*     */     //   116: aload #5
/*     */     //   118: aload #9
/*     */     //   120: invokevirtual getIssuedTokenContext : (Ljava/lang/String;)Lcom/sun/xml/ws/security/IssuedTokenContext;
/*     */     //   123: astore #10
/*     */     //   125: aload #10
/*     */     //   127: ifnull -> 149
/*     */     //   130: aload #5
/*     */     //   132: aload #10
/*     */     //   134: invokevirtual setTrustContext : (Lcom/sun/xml/ws/security/IssuedTokenContext;)V
/*     */     //   137: aload #10
/*     */     //   139: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*     */     //   144: checkcast com/sun/xml/ws/security/trust/GenericToken
/*     */     //   147: astore #8
/*     */     //   149: aload #8
/*     */     //   151: ifnull -> 199
/*     */     //   154: aload #8
/*     */     //   156: invokevirtual getElement : ()Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*     */     //   159: astore #7
/*     */     //   161: aload #7
/*     */     //   163: ifnonnull -> 199
/*     */     //   166: aload #8
/*     */     //   168: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*     */     //   171: checkcast org/w3c/dom/Element
/*     */     //   174: astore #9
/*     */     //   176: new com/sun/xml/ws/security/opt/impl/message/GSHeaderElement
/*     */     //   179: dup
/*     */     //   180: aload #9
/*     */     //   182: invokespecial <init> : (Lorg/w3c/dom/Element;)V
/*     */     //   185: astore #7
/*     */     //   187: aload #7
/*     */     //   189: aload #8
/*     */     //   191: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   194: invokeinterface setId : (Ljava/lang/String;)V
/*     */     //   199: aload #8
/*     */     //   201: ifnull -> 234
/*     */     //   204: iload #4
/*     */     //   206: ifeq -> 234
/*     */     //   209: aload #5
/*     */     //   211: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*     */     //   214: aload #7
/*     */     //   216: invokeinterface getId : ()Ljava/lang/String;
/*     */     //   221: invokevirtual getChildElement : (Ljava/lang/String;)Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*     */     //   224: ifnonnull -> 234
/*     */     //   227: aload #6
/*     */     //   229: aload #7
/*     */     //   231: invokevirtual add : (Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;)V
/*     */     //   234: aconst_null
/*     */     //   235: aload_2
/*     */     //   236: invokevirtual getSTRID : ()Ljava/lang/String;
/*     */     //   239: if_acmpeq -> 388
/*     */     //   242: aload #8
/*     */     //   244: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   247: astore #9
/*     */     //   249: new com/sun/xml/ws/security/opt/impl/util/WSSElementFactory
/*     */     //   252: dup
/*     */     //   253: aload #5
/*     */     //   255: invokevirtual getSOAPVersion : ()Lcom/sun/xml/ws/api/SOAPVersion;
/*     */     //   258: invokespecial <init> : (Lcom/sun/xml/ws/api/SOAPVersion;)V
/*     */     //   261: astore #10
/*     */     //   263: aload #10
/*     */     //   265: invokevirtual createKeyIdentifier : ()Lcom/sun/xml/ws/security/opt/impl/reference/KeyIdentifier;
/*     */     //   268: astore #11
/*     */     //   270: aload #11
/*     */     //   272: aload #9
/*     */     //   274: invokevirtual setValue : (Ljava/lang/String;)V
/*     */     //   277: aconst_null
/*     */     //   278: astore #12
/*     */     //   280: aload #7
/*     */     //   282: ifnull -> 322
/*     */     //   285: aload #7
/*     */     //   287: invokeinterface getNamespaceURI : ()Ljava/lang/String;
/*     */     //   292: astore #13
/*     */     //   294: ldc 'urn:oasis:names:tc:SAML:1.0:assertion'
/*     */     //   296: aload #13
/*     */     //   298: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   301: ifeq -> 308
/*     */     //   304: ldc 'http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID'
/*     */     //   306: astore #12
/*     */     //   308: ldc 'urn:oasis:names:tc:SAML:2.0:assertion'
/*     */     //   310: aload #13
/*     */     //   312: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   315: ifeq -> 322
/*     */     //   318: ldc 'http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID'
/*     */     //   320: astore #12
/*     */     //   322: aload #11
/*     */     //   324: aload #12
/*     */     //   326: invokevirtual setValueType : (Ljava/lang/String;)V
/*     */     //   329: aload #10
/*     */     //   331: aload #11
/*     */     //   333: invokevirtual createSecurityTokenReference : (Lcom/sun/xml/ws/security/opt/api/reference/Reference;)Lcom/sun/xml/ws/security/opt/impl/keyinfo/SecurityTokenReference;
/*     */     //   336: astore #13
/*     */     //   338: aload_2
/*     */     //   339: invokevirtual getSTRID : ()Ljava/lang/String;
/*     */     //   342: astore #14
/*     */     //   344: aload #13
/*     */     //   346: aload #14
/*     */     //   348: invokevirtual setId : (Ljava/lang/String;)V
/*     */     //   351: new com/sun/xml/ws/security/opt/impl/crypto/SSEData
/*     */     //   354: dup
/*     */     //   355: aload #7
/*     */     //   357: iconst_0
/*     */     //   358: aload #5
/*     */     //   360: invokevirtual getNamespaceContext : ()Lorg/jvnet/staxex/NamespaceContextEx;
/*     */     //   363: invokespecial <init> : (Lcom/sun/xml/ws/security/opt/api/SecurityElement;ZLorg/jvnet/staxex/NamespaceContextEx;)V
/*     */     //   366: astore #15
/*     */     //   368: aload #5
/*     */     //   370: invokevirtual getElementCache : ()Ljava/util/HashMap;
/*     */     //   373: aload #14
/*     */     //   375: aload #15
/*     */     //   377: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   380: pop
/*     */     //   381: aload #6
/*     */     //   383: aload #13
/*     */     //   385: invokevirtual add : (Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;)V
/*     */     //   388: goto -> 566
/*     */     //   391: aload_0
/*     */     //   392: invokevirtual getSecurableSoapMessage : ()Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*     */     //   395: astore #5
/*     */     //   397: aload #5
/*     */     //   399: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*     */     //   402: astore #6
/*     */     //   404: aconst_null
/*     */     //   405: astore #7
/*     */     //   407: aload_0
/*     */     //   408: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*     */     //   411: ifnonnull -> 451
/*     */     //   414: aload_2
/*     */     //   415: invokevirtual getUUID : ()Ljava/lang/String;
/*     */     //   418: astore #8
/*     */     //   420: aload_0
/*     */     //   421: aload #8
/*     */     //   423: invokevirtual getIssuedTokenContext : (Ljava/lang/String;)Lcom/sun/xml/ws/security/IssuedTokenContext;
/*     */     //   426: astore #9
/*     */     //   428: aload #9
/*     */     //   430: ifnull -> 451
/*     */     //   433: aload_0
/*     */     //   434: aload #9
/*     */     //   436: invokevirtual setTrustContext : (Lcom/sun/xml/ws/security/IssuedTokenContext;)V
/*     */     //   439: aload #9
/*     */     //   441: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*     */     //   446: checkcast com/sun/xml/ws/security/trust/GenericToken
/*     */     //   449: astore #7
/*     */     //   451: aload #7
/*     */     //   453: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*     */     //   456: checkcast org/w3c/dom/Element
/*     */     //   459: astore #8
/*     */     //   461: aload #6
/*     */     //   463: aload #8
/*     */     //   465: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*     */     //   468: astore #9
/*     */     //   470: aload #9
/*     */     //   472: ifnull -> 490
/*     */     //   475: iload #4
/*     */     //   477: ifeq -> 490
/*     */     //   480: aload #5
/*     */     //   482: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*     */     //   485: aload #9
/*     */     //   487: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*     */     //   490: aload_0
/*     */     //   491: aload #9
/*     */     //   493: invokevirtual setIssuedSAMLToken : (Lorg/w3c/dom/Element;)V
/*     */     //   496: aconst_null
/*     */     //   497: aload_2
/*     */     //   498: invokevirtual getSTRID : ()Ljava/lang/String;
/*     */     //   501: if_acmpeq -> 566
/*     */     //   504: aload #7
/*     */     //   506: invokevirtual getId : ()Ljava/lang/String;
/*     */     //   509: astore #10
/*     */     //   511: new com/sun/xml/wss/core/SecurityTokenReference
/*     */     //   514: dup
/*     */     //   515: aload #5
/*     */     //   517: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*     */     //   520: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*     */     //   523: astore #11
/*     */     //   525: aload #11
/*     */     //   527: aload_2
/*     */     //   528: invokevirtual getSTRID : ()Ljava/lang/String;
/*     */     //   531: invokevirtual setWsuId : (Ljava/lang/String;)V
/*     */     //   534: new com/sun/xml/wss/impl/keyinfo/KeyIdentifierStrategy
/*     */     //   537: dup
/*     */     //   538: aload #10
/*     */     //   540: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   543: astore #12
/*     */     //   545: aload #12
/*     */     //   547: aload #11
/*     */     //   549: aload_0
/*     */     //   550: invokevirtual getSecurableSoapMessage : ()Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*     */     //   553: invokevirtual insertKey : (Lcom/sun/xml/wss/core/SecurityTokenReference;Lcom/sun/xml/wss/impl/SecurableSoapMessage;)V
/*     */     //   556: aload #5
/*     */     //   558: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*     */     //   561: aload #11
/*     */     //   563: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*     */     //   566: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #541	-> 0
/*     */     //   #542	-> 8
/*     */     //   #543	-> 16
/*     */     //   #544	-> 21
/*     */     //   #549	-> 76
/*     */     //   #550	-> 83
/*     */     //   #551	-> 89
/*     */     //   #553	-> 96
/*     */     //   #554	-> 99
/*     */     //   #555	-> 102
/*     */     //   #556	-> 110
/*     */     //   #557	-> 116
/*     */     //   #558	-> 125
/*     */     //   #559	-> 130
/*     */     //   #560	-> 137
/*     */     //   #564	-> 149
/*     */     //   #565	-> 154
/*     */     //   #566	-> 161
/*     */     //   #567	-> 166
/*     */     //   #568	-> 176
/*     */     //   #569	-> 187
/*     */     //   #572	-> 199
/*     */     //   #573	-> 209
/*     */     //   #574	-> 227
/*     */     //   #578	-> 234
/*     */     //   #580	-> 242
/*     */     //   #581	-> 249
/*     */     //   #582	-> 263
/*     */     //   #583	-> 270
/*     */     //   #584	-> 277
/*     */     //   #585	-> 280
/*     */     //   #586	-> 285
/*     */     //   #587	-> 294
/*     */     //   #588	-> 304
/*     */     //   #591	-> 308
/*     */     //   #592	-> 318
/*     */     //   #595	-> 322
/*     */     //   #596	-> 329
/*     */     //   #597	-> 338
/*     */     //   #598	-> 344
/*     */     //   #600	-> 351
/*     */     //   #601	-> 368
/*     */     //   #602	-> 381
/*     */     //   #605	-> 388
/*     */     //   #606	-> 391
/*     */     //   #607	-> 397
/*     */     //   #608	-> 404
/*     */     //   #609	-> 407
/*     */     //   #610	-> 414
/*     */     //   #611	-> 420
/*     */     //   #612	-> 428
/*     */     //   #613	-> 433
/*     */     //   #614	-> 439
/*     */     //   #618	-> 451
/*     */     //   #619	-> 461
/*     */     //   #620	-> 470
/*     */     //   #621	-> 480
/*     */     //   #623	-> 490
/*     */     //   #625	-> 496
/*     */     //   #626	-> 504
/*     */     //   #627	-> 511
/*     */     //   #628	-> 525
/*     */     //   #630	-> 534
/*     */     //   #631	-> 545
/*     */     //   #632	-> 556
/*     */     //   #635	-> 566
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   116	33	9	itPolicyId	Ljava/lang/String;
/*     */     //   125	24	10	ictx	Lcom/sun/xml/ws/security/IssuedTokenContext;
/*     */     //   176	23	9	element	Lorg/w3c/dom/Element;
/*     */     //   294	28	13	issuedTokenNS	Ljava/lang/String;
/*     */     //   249	139	9	itId	Ljava/lang/String;
/*     */     //   263	125	10	elementFactory	Lcom/sun/xml/ws/security/opt/impl/util/WSSElementFactory;
/*     */     //   270	118	11	ref	Lcom/sun/xml/ws/security/opt/impl/reference/KeyIdentifier;
/*     */     //   280	108	12	valueType	Ljava/lang/String;
/*     */     //   338	50	13	secTokRef	Lcom/sun/xml/ws/security/opt/impl/keyinfo/SecurityTokenReference;
/*     */     //   344	44	14	strId	Ljava/lang/String;
/*     */     //   368	20	15	data	Ljavax/xml/crypto/Data;
/*     */     //   89	299	5	opContext	Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*     */     //   96	292	6	secHeader	Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*     */     //   99	289	7	issuedTokenElement	Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*     */     //   102	286	8	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*     */     //   420	31	8	itPolicyId	Ljava/lang/String;
/*     */     //   428	23	9	ictx	Lcom/sun/xml/ws/security/IssuedTokenContext;
/*     */     //   511	55	10	itId	Ljava/lang/String;
/*     */     //   525	41	11	tokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*     */     //   545	21	12	strat	Lcom/sun/xml/wss/impl/keyinfo/KeyIdentifierStrategy;
/*     */     //   397	169	5	secureMessage	Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*     */     //   404	162	6	soapPart	Ljavax/xml/soap/SOAPPart;
/*     */     //   407	159	7	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*     */     //   461	105	8	element	Lorg/w3c/dom/Element;
/*     */     //   470	96	9	tokenEle	Ljavax/xml/soap/SOAPElement;
/*     */     //   0	567	0	context	Lcom/sun/xml/wss/impl/FilterProcessingContext;
/*     */     //   8	559	1	authPolicy	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy;
/*     */     //   16	551	2	itkb	Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*     */     //   21	546	3	itType	Ljava/lang/String;
/*     */     //   76	491	4	includeToken	Z
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void processX509Token(FilterProcessingContext context) throws XWSSecurityException {
/* 644 */     if (context.isInboundMessage()) {
/*     */       return;
/*     */     }
/*     */     
/* 648 */     AuthenticationTokenPolicy authPolicy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/*     */     
/* 650 */     AuthenticationTokenPolicy.X509CertificateBinding policy = (AuthenticationTokenPolicy.X509CertificateBinding)authPolicy.getFeatureBinding();
/*     */ 
/*     */ 
/*     */     
/* 654 */     X509Certificate cert = context.getSecurityEnvironment().getDefaultCertificate(context.getExtraneousProperties());
/*     */     
/* 656 */     if (cert == null) {
/* 657 */       throw new XWSSecurityException("No default X509 certificate was provided");
/*     */     }
/*     */     
/* 660 */     AuthenticationTokenPolicy.X509CertificateBinding policyClone = (AuthenticationTokenPolicy.X509CertificateBinding)policy.clone();
/* 661 */     policyClone.setX509Certificate(cert);
/*     */ 
/*     */     
/* 664 */     if (context instanceof JAXBFilterProcessingContext) {
/* 665 */       JAXBFilterProcessingContext opContext = (JAXBFilterProcessingContext)context;
/* 666 */       ((NamespaceContextEx)opContext.getNamespaceContext()).addWSSNS();
/* 667 */       X509TokenBuilder x509TokenBuilder = new X509TokenBuilder(opContext, policyClone);
/* 668 */       x509TokenBuilder.process();
/*     */     } else {
/* 670 */       SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/* 671 */       String wsuId = policy.getUUID();
/* 672 */       if (wsuId == null) {
/* 673 */         wsuId = secureMessage.generateId();
/*     */       }
/* 675 */       SecurityUtil.checkIncludeTokenPolicy(context, policyClone, wsuId);
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
/*     */   public static void processRSAToken(FilterProcessingContext context) throws XWSSecurityException {
/* 687 */     if (context.isInboundMessage())
/*     */       return; 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\AuthenticationTokenFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */