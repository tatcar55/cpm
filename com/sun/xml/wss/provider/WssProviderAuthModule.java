/*     */ package com.sun.xml.wss.provider;
/*     */ 
/*     */ import com.sun.enterprise.security.jauth.AuthPolicy;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.WssProviderSecurityEnvironment;
/*     */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.SecurityConfigurationXmlReader;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WssProviderAuthModule
/*     */   implements ModuleOptions, ConfigurationStates
/*     */ {
/*  73 */   protected SecurityPolicy _policy = null;
/*  74 */   protected WssProviderSecurityEnvironment _sEnvironment = null;
/*     */   
/*     */   private boolean runtimeUsernamePassword = false;
/*     */   
/*     */   private static final String SIGN_POLICY = "com.sun.xml.wss.impl.policy.mls.SignaturePolicy";
/*     */   private static final String ENCRYPT_POLICY = "com.sun.xml.wss.impl.policy.mls.EncryptionPolicy";
/*     */   private static final String TIMESTAMP_POLICY = "com.sun.xml.wss.impl.policy.mls.TimestampPolicy";
/*     */   private static final String AUTHENTICATION_POLICY = "com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy";
/*     */   private static final String USERNAMETOKEN_POLICY = "com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy.UsernameTokenBinding";
/*     */   private static final String USERNAMETOKEN = "UsernameToken";
/*     */   private static final String BODY = "Body";
/*     */   public static final String REQUESTER_SUBJECT = "REQUESTER_SUBJECT";
/*     */   public static final String REQUESTER_KEYID = "REQUESTER_KEYID";
/*     */   public static final String REQUESTER_ISSUERNAME = "REQUESTER_ISSUERNAME";
/*     */   public static final String REQUESTER_SERIAL = "REQUESTER_SERIAL";
/*     */   public static final String SELF_SUBJECT = "SELF_SUBJECT";
/*  90 */   protected int optimize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean configOptimizeAttribute = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(AuthPolicy requestPolicy, AuthPolicy responsePolicy, CallbackHandler handler, Map options, boolean isClientAuthModule) {
/* 115 */     boolean debugON = false;
/* 116 */     String bg = (String)options.get("debug");
/* 117 */     if (bg != null && bg.equals("true")) debugON = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     String securityConfigurationURL = (String)options.get("security.config");
/* 124 */     String signAlias = (String)options.get("signature.key.alias");
/* 125 */     String encryptAlias = (String)options.get("encryption.key.alias");
/*     */     
/*     */     try {
/* 128 */       InputStream is = null;
/* 129 */       if (securityConfigurationURL != null) {
/* 130 */         is = new BufferedInputStream(new FileInputStream(new File(securityConfigurationURL)));
/*     */       
/*     */       }
/* 133 */       else if (this instanceof ServerSecurityAuthModule) {
/* 134 */         is = getClass().getResourceAsStream("wss-server-config-2.0.xml");
/*     */       } else {
/* 136 */         is = getClass().getResourceAsStream("wss-client-config-2.0.xml");
/*     */       } 
/*     */       
/* 139 */       this._policy = (SecurityPolicy)SecurityConfigurationXmlReader.createDeclarativeConfiguration(is);
/* 140 */       int request_policy_state = 8;
/* 141 */       int response_policy_state = 8;
/* 142 */       if (requestPolicy != null) {
/* 143 */         request_policy_state = resolveConfigurationState(requestPolicy, true, isClientAuthModule);
/*     */       }
/* 145 */       if (responsePolicy != null) {
/* 146 */         response_policy_state = resolveConfigurationState(responsePolicy, false, isClientAuthModule);
/*     */       }
/* 148 */       String obj = (String)options.get("dynamic.username.password");
/* 149 */       if (obj != null) {
/* 150 */         this.runtimeUsernamePassword = obj.equalsIgnoreCase("true");
/*     */       }
/* 152 */       if (isClientAuthModule) {
/* 153 */         augmentConfiguration(response_policy_state, true, handler, debugON, signAlias, encryptAlias);
/* 154 */         augmentConfiguration(request_policy_state, false, handler, debugON, signAlias, encryptAlias);
/*     */       } else {
/* 156 */         augmentConfiguration(response_policy_state, false, handler, debugON, signAlias, encryptAlias);
/* 157 */         augmentConfiguration(request_policy_state, true, handler, debugON, signAlias, encryptAlias);
/*     */       } 
/* 159 */       this._sEnvironment = new WssProviderSecurityEnvironment(handler, options);
/* 160 */     } catch (Exception e) {
/* 161 */       throw new RuntimeException(e);
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
/*     */   public int resolveConfigurationState(AuthPolicy policy, boolean isRequestPolicy, boolean isClientAuthModule) {
/* 173 */     boolean orderForValidation = isClientAuthModule ? (!isRequestPolicy) : (isRequestPolicy);
/*     */ 
/*     */     
/* 176 */     boolean sourceAuthRequired = policy.isSourceAuthRequired();
/* 177 */     boolean recipientAuthRequired = policy.isRecipientAuthRequired();
/* 178 */     boolean senderAuthRequired = policy.isSenderAuthRequired();
/* 179 */     boolean contentAuthRequired = policy.isContentAuthRequired();
/* 180 */     boolean beforeContent = policy.isRecipientAuthBeforeContent(orderForValidation);
/* 181 */     int configurationState = -1;
/* 182 */     if (sourceAuthRequired && !recipientAuthRequired) {
/* 183 */       if (senderAuthRequired)
/* 184 */       { configurationState = 2; }
/* 185 */       else if (contentAuthRequired)
/* 186 */       { configurationState = 3; } 
/* 187 */     } else if (!sourceAuthRequired && recipientAuthRequired) {
/* 188 */       configurationState = 1;
/* 189 */     } else if (sourceAuthRequired && recipientAuthRequired) {
/* 190 */       if (beforeContent) {
/* 191 */         if (senderAuthRequired) {
/* 192 */           configurationState = 4;
/* 193 */         } else if (contentAuthRequired) {
/* 194 */           configurationState = 6;
/*     */         }
/*     */       
/* 197 */       } else if (senderAuthRequired) {
/* 198 */         configurationState = 5;
/* 199 */       } else if (contentAuthRequired) {
/* 200 */         configurationState = 7;
/*     */       } 
/*     */     } else {
/*     */       
/* 204 */       configurationState = 8;
/*     */     } 
/* 206 */     if (configurationState == -1)
/*     */     {
/* 208 */       throw new RuntimeException("AuthPolicy configuration error: Invalid policy specification");
/*     */     }
/* 210 */     return configurationState;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection getEncryptPolicies(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 216 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/* 217 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 218 */     while (it.hasNext()) {
/* 219 */       WSSPolicy policy = it.next();
/* 220 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy) && 
/* 221 */         !hasEncryptUsernamePolicy((EncryptionPolicy)policy, mPolicy)) {
/* 222 */         requiredElements.add(policy);
/*     */       }
/*     */     } 
/*     */     
/* 226 */     if (requiredElements.isEmpty()) {
/* 227 */       throw new RuntimeException("Operation/Requirement (" + translate2configurationName("com.sun.xml.wss.impl.policy.mls.EncryptionPolicy", senderConfiguration) + ") not specified " + "in the Config. file is required by the policy");
/*     */     }
/*     */ 
/*     */     
/* 231 */     return requiredElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection getEncryptPoliciesOptional(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 237 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/* 238 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 239 */     while (it.hasNext()) {
/* 240 */       WSSPolicy policy = it.next();
/* 241 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy) && 
/* 242 */         !hasEncryptUsernamePolicy((EncryptionPolicy)policy, mPolicy)) {
/* 243 */         requiredElements.add(policy);
/*     */       }
/*     */     } 
/*     */     
/* 247 */     return requiredElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection getSignPolicies(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 253 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/* 254 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 255 */     while (it.hasNext()) {
/* 256 */       WSSPolicy policy = it.next();
/* 257 */       if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy)) {
/* 258 */         requiredElements.add(policy);
/*     */       }
/*     */     } 
/* 261 */     if (requiredElements.isEmpty()) {
/* 262 */       throw new RuntimeException("Operation/Requirement (" + translate2configurationName("com.sun.xml.wss.impl.policy.mls.SignaturePolicy", senderConfiguration) + ") not specified " + "in the Config. file is required by the policy");
/*     */     }
/*     */ 
/*     */     
/* 266 */     return requiredElements;
/*     */   }
/*     */   
/*     */   private WSSPolicy getUsernamePolicy(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 270 */     WSSPolicy usernamePolicy = null;
/* 271 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 272 */     while (it.hasNext()) {
/* 273 */       WSSPolicy policy = it.next();
/* 274 */       if (PolicyTypeUtil.authenticationTokenPolicy((SecurityPolicy)policy) && 
/* 275 */         policy.getFeatureBinding() != null && PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)policy.getFeatureBinding())) {
/*     */         
/* 277 */         if (senderConfiguration && !this.runtimeUsernamePassword) {
/* 278 */           setUsernamePassword((AuthenticationTokenPolicy)policy, handler);
/*     */         }
/* 280 */         usernamePolicy = policy;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 285 */     if (usernamePolicy == null) {
/* 286 */       throw new RuntimeException("Operation/Requirement (" + translate2configurationName("com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy.UsernameTokenBinding", senderConfiguration) + ") not specified " + "in the Config. file is required by the policy");
/*     */     }
/*     */ 
/*     */     
/* 290 */     return usernamePolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection getUsernamePolicies(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 295 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/* 296 */     WSSPolicy encryptUsernamePolicy = null;
/* 297 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 298 */     while (it.hasNext()) {
/* 299 */       WSSPolicy policy = it.next();
/* 300 */       if (PolicyTypeUtil.authenticationTokenPolicy((SecurityPolicy)policy)) {
/* 301 */         if (policy.getFeatureBinding() != null && PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)policy.getFeatureBinding())) {
/*     */           
/* 303 */           if (senderConfiguration && !this.runtimeUsernamePassword) {
/* 304 */             setUsernamePassword((AuthenticationTokenPolicy)policy, handler);
/*     */           }
/* 306 */           requiredElements.add(policy);
/*     */         }  continue;
/* 308 */       }  if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy) && 
/* 309 */         isEncryptUsernamePolicy((EncryptionPolicy)policy, mPolicy)) {
/* 310 */         encryptUsernamePolicy = policy;
/*     */       }
/*     */     } 
/*     */     
/* 314 */     if (requiredElements.isEmpty()) {
/* 315 */       throw new RuntimeException("Operation/Requirement (" + translate2configurationName("com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy.UsernameTokenBinding", senderConfiguration) + ") not specified " + "in the Config. file is required by the policy");
/*     */     }
/*     */ 
/*     */     
/* 319 */     if (encryptUsernamePolicy != null) {
/* 320 */       requiredElements.add(encryptUsernamePolicy);
/*     */     }
/* 322 */     return requiredElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection getEncryptUsernamePolicies(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 328 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/* 329 */     WSSPolicy eBU = getEncryptBodyUsernamePolicy(mPolicy);
/* 330 */     if (eBU != null) {
/* 331 */       Collection ePolicies = getNonBodyUsernameEncryptPolicies(mPolicy, handler, senderConfiguration);
/*     */       
/* 333 */       requiredElements.addAll(ePolicies);
/* 334 */       requiredElements.add(getUsernamePolicy(mPolicy, handler, senderConfiguration));
/* 335 */       requiredElements.add(eBU);
/*     */     } else {
/*     */       
/* 338 */       Collection<? extends WSSPolicy> ePolicies = getEncryptPoliciesOptional(mPolicy, handler, senderConfiguration);
/* 339 */       requiredElements.addAll(ePolicies);
/* 340 */       requiredElements.addAll(getUsernamePolicies(mPolicy, handler, senderConfiguration));
/*     */     } 
/* 342 */     if (requiredElements.isEmpty()) {
/* 343 */       throw new RuntimeException("Operation/Requirement (" + translate2configurationName("com.sun.xml.wss.impl.policy.mls.EncryptionPolicy", senderConfiguration) + ") not specified " + "in the Config. file is required by the policy");
/*     */     }
/*     */ 
/*     */     
/* 347 */     return requiredElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection getUsernameEncryptPolicies(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) throws PolicyGenerationException {
/* 353 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/* 354 */     WSSPolicy eUB = getEncryptUsernameBodyPolicy(mPolicy);
/* 355 */     if (eUB != null) {
/* 356 */       requiredElements.add(getUsernamePolicy(mPolicy, handler, senderConfiguration));
/* 357 */       requiredElements.add(eUB);
/* 358 */       Collection<? extends WSSPolicy> ePolicies = getNonBodyUsernameEncryptPolicies(mPolicy, handler, senderConfiguration);
/*     */       
/* 360 */       requiredElements.addAll(ePolicies);
/*     */     } else {
/* 362 */       requiredElements.addAll(getUsernamePolicies(mPolicy, handler, senderConfiguration));
/*     */       
/* 364 */       Collection<? extends WSSPolicy> ePolicies = getEncryptPoliciesOptional(mPolicy, handler, senderConfiguration);
/* 365 */       requiredElements.addAll(ePolicies);
/*     */     } 
/* 367 */     if (requiredElements.isEmpty()) {
/* 368 */       throw new RuntimeException("Operation/Requirement (" + translate2configurationName("com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy.UsernameTokenBinding", senderConfiguration) + ") not specified " + "in the Config. file is required by the policy");
/*     */     }
/*     */ 
/*     */     
/* 372 */     return requiredElements;
/*     */   }
/*     */   
/*     */   private WSSPolicy getTimestampPolicy(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) {
/* 376 */     WSSPolicy timestampPolicy = null;
/* 377 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 378 */     while (it.hasNext()) {
/* 379 */       WSSPolicy policy = it.next();
/* 380 */       if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)policy)) {
/* 381 */         timestampPolicy = policy;
/*     */         break;
/*     */       } 
/*     */     } 
/* 385 */     return timestampPolicy;
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
/*     */   private void augmentConfiguration(int requiredState, boolean modifyReceiverSettings, CallbackHandler handler, boolean debugON, String signAlias, String encryptAlias) throws PolicyGenerationException {
/*     */     Collection signPolicies;
/* 398 */     MessagePolicy mPolicy = null;
/* 399 */     DeclarativeSecurityConfiguration dConfiguration = (DeclarativeSecurityConfiguration)this._policy;
/* 400 */     boolean senderConfiguration = false;
/* 401 */     if (requiredState == 8) {
/* 402 */       if (modifyReceiverSettings) {
/* 403 */         mPolicy = dConfiguration.receiverSettings();
/* 404 */         mPolicy.removeAll();
/*     */       } else {
/*     */         
/* 407 */         mPolicy = dConfiguration.senderSettings();
/* 408 */         mPolicy.removeAll();
/*     */       } 
/* 410 */       if (debugON) {
/* 411 */         mPolicy.dumpMessages(true);
/*     */       }
/*     */       return;
/*     */     } 
/* 415 */     if (modifyReceiverSettings) {
/* 416 */       mPolicy = dConfiguration.receiverSettings();
/*     */     } else {
/* 418 */       mPolicy = dConfiguration.senderSettings();
/* 419 */       senderConfiguration = !senderConfiguration;
/*     */     } 
/* 421 */     Collection newMPolicy = null;
/* 422 */     WSSPolicy ts = getTimestampPolicy(mPolicy, handler, senderConfiguration);
/* 423 */     boolean requireTimestampPolicy = false;
/* 424 */     switch (requiredState) {
/*     */       
/*     */       case 1:
/* 427 */         newMPolicy = getEncryptPolicies(mPolicy, handler, senderConfiguration);
/* 428 */         mPolicy.removeAll();
/* 429 */         mPolicy.appendAll(newMPolicy);
/*     */         break;
/*     */       
/*     */       case 2:
/* 433 */         newMPolicy = getUsernamePolicies(mPolicy, handler, senderConfiguration);
/* 434 */         mPolicy.removeAll();
/* 435 */         mPolicy.appendAll(newMPolicy);
/* 436 */         if (!modifyReceiverSettings && this.configOptimizeAttribute) {
/* 437 */           this.optimize = 4;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 3:
/* 442 */         newMPolicy = getSignPolicies(mPolicy, handler, senderConfiguration);
/* 443 */         requireTimestampPolicy = !newMPolicy.isEmpty();
/* 444 */         mPolicy.removeAll();
/* 445 */         mPolicy.appendAll(newMPolicy);
/* 446 */         if (!modifyReceiverSettings && this.configOptimizeAttribute) {
/* 447 */           this.optimize = 1;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 4:
/* 452 */         newMPolicy = getEncryptUsernamePolicies(mPolicy, handler, senderConfiguration);
/* 453 */         mPolicy.removeAll();
/* 454 */         mPolicy.appendAll(newMPolicy);
/*     */         break;
/*     */       
/*     */       case 5:
/* 458 */         newMPolicy = getUsernameEncryptPolicies(mPolicy, handler, senderConfiguration);
/* 459 */         mPolicy.removeAll();
/* 460 */         mPolicy.appendAll(newMPolicy);
/*     */         break;
/*     */       
/*     */       case 6:
/* 464 */         newMPolicy = getEncryptPolicies(mPolicy, handler, senderConfiguration);
/* 465 */         signPolicies = getSignPolicies(mPolicy, handler, senderConfiguration);
/* 466 */         requireTimestampPolicy = !signPolicies.isEmpty();
/* 467 */         newMPolicy.addAll(signPolicies);
/* 468 */         mPolicy.removeAll();
/* 469 */         mPolicy.appendAll(newMPolicy);
/*     */         break;
/*     */       
/*     */       case 7:
/* 473 */         newMPolicy = getSignPolicies(mPolicy, handler, senderConfiguration);
/* 474 */         requireTimestampPolicy = !newMPolicy.isEmpty();
/* 475 */         newMPolicy.addAll(getEncryptPolicies(mPolicy, handler, senderConfiguration));
/* 476 */         mPolicy.removeAll();
/* 477 */         mPolicy.appendAll(newMPolicy);
/* 478 */         if (!modifyReceiverSettings && this.configOptimizeAttribute) {
/* 479 */           this.optimize = 2;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 485 */     if (ts != null && requireTimestampPolicy) {
/* 486 */       mPolicy.prepend((SecurityPolicy)ts);
/*     */     }
/* 488 */     if (debugON) {
/* 489 */       mPolicy.dumpMessages(true);
/*     */     }
/* 491 */     augmentSignAlias(mPolicy, signAlias);
/* 492 */     augmentEncryptAlias(mPolicy, encryptAlias);
/*     */   }
/*     */   private String translate2configurationName(String opName, boolean senderConfiguration) {
/* 495 */     String value = null;
/* 496 */     if (opName == "com.sun.xml.wss.impl.policy.mls.SignaturePolicy") {
/* 497 */       value = senderConfiguration ? "xwss:Sign" : "xwss:RequireSignature";
/*     */     }
/* 499 */     else if (opName == "com.sun.xml.wss.impl.policy.mls.EncryptionPolicy") {
/* 500 */       value = senderConfiguration ? "xwss:Encrypt" : "xwss:RequireEncryption";
/*     */     }
/* 502 */     else if (opName == "com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy.UsernameTokenBinding") {
/* 503 */       value = senderConfiguration ? "xwss:UsernameToken" : "xwss:RequireUsernameToken";
/* 504 */     }  return value;
/*     */   }
/*     */   private boolean isEncryptUsernamePolicy(EncryptionPolicy policy, MessagePolicy mPolicy) {
/* 507 */     EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */     
/* 509 */     int numTargets = fb.getTargetBindings().size();
/* 510 */     if (numTargets != 1)
/* 511 */       return false; 
/* 512 */     Iterator<Target> it1 = fb.getTargetBindings().iterator();
/* 513 */     Target target = it1.next();
/* 514 */     if (target.getType() == "uri")
/*     */     {
/* 516 */       return uriIsUsernameToken(mPolicy, target.getValue());
/*     */     }
/* 518 */     int idx = target.getValue().indexOf("UsernameToken");
/* 519 */     if (idx > -1) {
/* 520 */       return true;
/*     */     }
/*     */     
/* 523 */     return false;
/*     */   }
/*     */   private boolean hasEncryptUsernamePolicy(EncryptionPolicy policy, MessagePolicy mPolicy) {
/* 526 */     EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */     
/* 528 */     Iterator<Target> it = fb.getTargetBindings().iterator();
/* 529 */     while (it.hasNext()) {
/* 530 */       Target target = it.next();
/* 531 */       if (target.getType() == "uri")
/*     */       {
/* 533 */         return uriIsUsernameToken(mPolicy, target.getValue());
/*     */       }
/* 535 */       int idx = target.getValue().indexOf("UsernameToken");
/* 536 */       if (idx > -1) {
/* 537 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 541 */     return false;
/*     */   }
/*     */   private boolean uriIsUsernameToken(MessagePolicy mPolicy, String uri) {
/* 544 */     String fragment = uri;
/* 545 */     if (uri.startsWith("#")) {
/* 546 */       fragment = uri.substring(1);
/*     */     }
/* 548 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 549 */     while (it.hasNext()) {
/* 550 */       WSSPolicy policy = it.next();
/* 551 */       if (PolicyTypeUtil.authenticationTokenPolicy((SecurityPolicy)policy)) {
/* 552 */         MLSPolicy feature = policy.getFeatureBinding();
/* 553 */         if (feature != null && PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)feature)) {
/* 554 */           AuthenticationTokenPolicy.UsernameTokenBinding fb = (AuthenticationTokenPolicy.UsernameTokenBinding)feature;
/*     */           
/* 556 */           if (fragment.equals(fb.getUUID())) {
/* 557 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 562 */     return false;
/*     */   }
/*     */   private WSSPolicy getEncryptBodyUsernamePolicy(MessagePolicy mPolicy) {
/* 565 */     WSSPolicy ret = null;
/* 566 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 567 */     while (it.hasNext()) {
/* 568 */       WSSPolicy policy = it.next();
/* 569 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy)) {
/* 570 */         EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */         
/* 572 */         int numTargets = fb.getTargetBindings().size();
/* 573 */         if (numTargets <= 1) {
/*     */           continue;
/*     */         }
/* 576 */         if (hasBodyFollowedByUsername(fb.getTargetBindings())) {
/* 577 */           ret = policy;
/* 578 */           return ret;
/*     */         } 
/*     */       } 
/*     */     } 
/* 582 */     return ret;
/*     */   }
/*     */   private WSSPolicy getEncryptUsernameBodyPolicy(MessagePolicy mPolicy) {
/* 585 */     WSSPolicy ret = null;
/* 586 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 587 */     while (it.hasNext()) {
/* 588 */       WSSPolicy policy = it.next();
/* 589 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy)) {
/* 590 */         EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */         
/* 592 */         int numTargets = fb.getTargetBindings().size();
/* 593 */         if (numTargets <= 1) {
/*     */           continue;
/*     */         }
/* 596 */         if (hasUsernameFollowedByBody(fb.getTargetBindings())) {
/* 597 */           ret = policy;
/* 598 */           return ret;
/*     */         } 
/*     */       } 
/*     */     } 
/* 602 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasUsernameFollowedByBody(ArrayList<Target> targets) {
/* 607 */     Target t = targets.get(0);
/* 608 */     int idx = t.getValue().indexOf("UsernameToken");
/* 609 */     if (idx == -1) {
/* 610 */       return false;
/*     */     }
/* 612 */     return true;
/*     */   }
/*     */   
/*     */   private boolean hasBodyFollowedByUsername(ArrayList<Target> targets) {
/* 616 */     Target t = targets.get(targets.size() - 1);
/* 617 */     int idx = t.getValue().indexOf("UsernameToken");
/* 618 */     if (idx == -1) {
/* 619 */       return false;
/*     */     }
/* 621 */     return true;
/*     */   }
/*     */   
/*     */   private void setUsernamePassword(AuthenticationTokenPolicy policy, CallbackHandler handler) {
/* 625 */     AuthenticationTokenPolicy.UsernameTokenBinding up = (AuthenticationTokenPolicy.UsernameTokenBinding)policy.getFeatureBinding();
/*     */     
/* 627 */     NameCallback nameCallback = new NameCallback("Username: ");
/* 628 */     PasswordCallback pwdCallback = new PasswordCallback("Password: ", false);
/*     */     
/*     */     try {
/* 631 */       Callback[] cbs = { nameCallback, pwdCallback };
/* 632 */       handler.handle(cbs);
/* 633 */     } catch (Exception e) {
/* 634 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 637 */     up.setUsername(nameCallback.getName());
/* 638 */     up.setPassword(new String(pwdCallback.getPassword()));
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection getNonBodyUsernameEncryptPolicies(MessagePolicy mPolicy, CallbackHandler handler, boolean senderConfiguration) {
/* 643 */     Collection<WSSPolicy> requiredElements = new ArrayList();
/*     */     
/* 645 */     Iterator<WSSPolicy> it = mPolicy.iterator();
/* 646 */     while (it.hasNext()) {
/* 647 */       WSSPolicy policy = it.next();
/* 648 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy) && 
/* 649 */         !hasEncryptBodyPolicy((EncryptionPolicy)policy) && !hasEncryptUsernamePolicy((EncryptionPolicy)policy, mPolicy))
/*     */       {
/* 651 */         requiredElements.add(policy);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 656 */     return requiredElements;
/*     */   }
/*     */   private boolean hasEncryptBodyPolicy(EncryptionPolicy policy) {
/* 659 */     EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*     */     
/* 661 */     Iterator<Target> it = fb.getTargetBindings().iterator();
/* 662 */     while (it.hasNext()) {
/* 663 */       Target target = it.next();
/* 664 */       int idx = target.getValue().indexOf("Body");
/* 665 */       if (idx > -1) {
/* 666 */         return true;
/*     */       }
/*     */     } 
/* 669 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isOptimized(SOAPMessage msg) {
/* 673 */     if (msg.getClass().getName().equals("com.sun.xml.messaging.saaj.soap.ver1_1.ExpressMessage1_1Impl") || msg.getClass().getName().equals("com.sun.xml.messaging.saaj.soap.ver1_2.ExpressMessage1_2Impl")) {
/* 674 */       return true;
/*     */     }
/* 676 */     return false;
/*     */   }
/*     */   private void augmentSignAlias(MessagePolicy mPolicy, String signAlias) {
/* 679 */     if (signAlias == null) {
/*     */       return;
/*     */     }
/* 682 */     for (Iterator<WSSPolicy> it = mPolicy.iterator(); it.hasNext(); ) {
/* 683 */       WSSPolicy sp = it.next();
/* 684 */       MLSPolicy mLSPolicy = sp.getKeyBinding();
/* 685 */       if (sp instanceof com.sun.xml.wss.impl.policy.mls.SignaturePolicy && 
/* 686 */         mLSPolicy != null && mLSPolicy instanceof AuthenticationTokenPolicy.X509CertificateBinding) {
/* 687 */         AuthenticationTokenPolicy.X509CertificateBinding x509KB = (AuthenticationTokenPolicy.X509CertificateBinding)mLSPolicy;
/*     */         
/* 689 */         String certId = x509KB.getCertificateIdentifier();
/* 690 */         if (certId != null) {
/* 691 */           x509KB.setCertificateIdentifier(signAlias);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void augmentEncryptAlias(MessagePolicy mPolicy, String encryptAlias) {
/* 698 */     if (encryptAlias == null) {
/*     */       return;
/*     */     }
/* 701 */     for (Iterator<WSSPolicy> it = mPolicy.iterator(); it.hasNext(); ) {
/* 702 */       WSSPolicy sp = it.next();
/* 703 */       MLSPolicy mLSPolicy = sp.getKeyBinding();
/* 704 */       if (sp instanceof EncryptionPolicy && 
/* 705 */         mLSPolicy != null && mLSPolicy instanceof AuthenticationTokenPolicy.X509CertificateBinding) {
/* 706 */         AuthenticationTokenPolicy.X509CertificateBinding x509KB = (AuthenticationTokenPolicy.X509CertificateBinding)mLSPolicy;
/*     */         
/* 708 */         String certId = x509KB.getCertificateIdentifier();
/* 709 */         if (certId != null)
/* 710 */           x509KB.setCertificateIdentifier(encryptAlias); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\WssProviderAuthModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */