/*     */ package com.sun.xml.ws.security.secconv.impl.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.secconv.client.SCTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenProvider;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.impl.policyconv.IntegrityAssertionProcessor;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SignatureTargetCreator;
/*     */ import com.sun.xml.ws.security.secconv.WSSCFactory;
/*     */ import com.sun.xml.ws.security.secconv.WSSCPlugin;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class SCTokenProviderImpl
/*     */   implements IssuedTokenProvider
/*     */ {
/*  80 */   private static final WSSCPlugin scp = WSSCFactory.newSCPlugin();
/*  81 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.secconv", "com.sun.xml.ws.security.secconv.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private Map<String, IssuedTokenContext> issuedTokenContextMap = new HashMap<String, IssuedTokenContext>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private Map<String, SecurityContextTokenInfo> securityContextTokenMap = new HashMap<String, SecurityContextTokenInfo>();
/*     */   
/*     */   private boolean tokenExpired = false;
/*     */   
/*     */   public void issue(IssuedTokenContext ctx) throws WSTrustException {
/*  98 */     SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/*  99 */     if (this.issuedTokenContextMap.get(sctConfig.getTokenId()) != null) {
/* 100 */       IssuedTokenContext tmpCtx = null;
/*     */       try {
/* 102 */         tmpCtx = getSecurityContextToken(sctConfig.getTokenId(), sctConfig.checkTokenExpiry());
/* 103 */       } catch (WSSecureConversationException ex) {
/* 104 */         if (sctConfig.isClientOutboundMessage()) {
/* 105 */           if (log.isLoggable(Level.FINE)) {
/* 106 */             log.log(Level.FINE, "SecureConversationToken expired");
/*     */           }
/* 108 */           this.tokenExpired = true;
/* 109 */           renew(ctx);
/* 110 */           this.tokenExpired = false;
/* 111 */           tmpCtx = this.issuedTokenContextMap.get(sctConfig.getTokenId());
/*     */         } else {
/* 113 */           throw new WSSecureConversationException(ex);
/*     */         } 
/*     */       } 
/* 116 */       if (tmpCtx != null) {
/* 117 */         ctx.setCreationTime(tmpCtx.getCreationTime());
/* 118 */         ctx.setExpirationTime(tmpCtx.getExpirationTime());
/* 119 */         ctx.setProofKey(tmpCtx.getProofKey());
/* 120 */         ctx.setSecurityToken(tmpCtx.getSecurityToken());
/* 121 */         ctx.setAttachedSecurityTokenReference(tmpCtx.getAttachedSecurityTokenReference());
/* 122 */         ctx.setUnAttachedSecurityTokenReference(tmpCtx.getUnAttachedSecurityTokenReference());
/* 123 */         if (tmpCtx.getSecurityToken() != null && ((SecurityContextToken)tmpCtx.getSecurityToken()).getInstance() != null) {
/*     */           
/* 125 */           String sctInfoKey = ((SecurityContextToken)tmpCtx.getSecurityToken()).getIdentifier().toString() + "_" + ((SecurityContextToken)tmpCtx.getSecurityToken()).getInstance();
/*     */           
/* 127 */           ctx.setSecurityContextTokenInfo(getSecurityContextTokenInfo(sctInfoKey));
/*     */         } 
/*     */       } else {
/* 130 */         throw new WSTrustException("IssuedTokenContext for Token id " + sctConfig.getTokenId() + " not found in the client cache.");
/*     */       } 
/* 132 */     } else if (!sctConfig.isClientOutboundMessage()) {
/* 133 */       ctx.getSecurityPolicy().clear();
/*     */     } else {
/* 135 */       scp.process(ctx);
/* 136 */       String sctId = ((SecurityContextToken)ctx.getSecurityToken()).getIdentifier().toString();
/* 137 */       sctConfig = new DefaultSCTokenConfiguration((DefaultSCTokenConfiguration)sctConfig, sctId);
/* 138 */       ctx.getSecurityPolicy().clear();
/* 139 */       ctx.getSecurityPolicy().add(sctConfig);
/* 140 */       addSecurityContextToken(((SecurityContextToken)ctx.getSecurityToken()).getIdentifier().toString(), ctx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancel(IssuedTokenContext ctx) throws WSTrustException {
/* 145 */     SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/* 146 */     if (this.issuedTokenContextMap.get(sctConfig.getTokenId()) != null) {
/* 147 */       scp.processCancellation(ctx);
/* 148 */       clearSessionCache(sctConfig.getTokenId(), ctx);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renew(IssuedTokenContext ctx) throws WSTrustException {
/* 153 */     SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/* 154 */     MessagePolicy msgPolicy = (MessagePolicy)sctConfig.getOtherOptions().get("MessagePolicy");
/* 155 */     if (this.issuedTokenContextMap.get(sctConfig.getTokenId()) != null) {
/* 156 */       ctx = this.issuedTokenContextMap.get(sctConfig.getTokenId());
/* 157 */       SCTokenConfiguration origSCTConfig = ctx.getSecurityPolicy().get(0);
/* 158 */       if (this.tokenExpired && origSCTConfig.isRenewExpiredSCT()) {
/* 159 */         scp.processRenew(ctx);
/* 160 */         String sctInfoKey = ((SecurityContextToken)ctx.getSecurityToken()).getIdentifier().toString() + "_" + ((SecurityContextToken)ctx.getSecurityToken()).getInstance();
/*     */         
/* 162 */         addSecurityContextTokenInfo(sctInfoKey, ctx.getSecurityContextTokenInfo());
/*     */       } else {
/* 164 */         throw new WSSecureConversationException("SecureConversation session for session Id:" + sctConfig.getTokenId() + "has expired.");
/*     */       } 
/* 166 */     } else if (msgPolicy != null) {
/*     */       try {
/* 168 */         if (sctConfig.addRenewPolicy()) {
/* 169 */           appendEndorsingSCTRenewPolicy(msgPolicy);
/*     */         } else {
/* 171 */           deleteRenewPolicy(msgPolicy);
/*     */         } 
/* 173 */       } catch (PolicyGenerationException e) {
/* 174 */         throw new WSTrustException(e.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void validate(IssuedTokenContext ctx) throws WSTrustException {}
/*     */ 
/*     */   
/*     */   private void addSecurityContextToken(String key, IssuedTokenContext itctx) {
/* 184 */     this.issuedTokenContextMap.put(key, itctx);
/*     */   }
/*     */   
/*     */   private void addSecurityContextTokenInfo(String key, SecurityContextTokenInfo sctInfo) {
/* 188 */     this.securityContextTokenMap.put(key, sctInfo);
/*     */   }
/*     */   
/*     */   private void clearSessionCache(String sctId, IssuedTokenContext ctx) {
/* 192 */     this.securityContextTokenMap.remove(sctId + "_" + ((SecurityContextToken)ctx.getSecurityToken()).getInstance());
/* 193 */     this.issuedTokenContextMap.remove(sctId);
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
/*     */   private IssuedTokenContext getSecurityContextToken(String key, boolean expiryCheck) throws WSSecureConversationException {
/* 206 */     IssuedTokenContext ctx = this.issuedTokenContextMap.get(key);
/*     */     
/* 208 */     if (ctx != null && expiryCheck) {
/* 209 */       SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/* 210 */       String maxClockSkew = (String)sctConfig.getOtherOptions().get("maxClockSkew");
/*     */ 
/*     */       
/* 213 */       Calendar c = new GregorianCalendar();
/* 214 */       long offset = c.get(15);
/* 215 */       if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 216 */         offset += c.getTimeZone().getDSTSavings();
/*     */       }
/* 218 */       long beforeTime = c.getTimeInMillis();
/* 219 */       long currentTime = beforeTime - offset;
/* 220 */       if (maxClockSkew != null) {
/* 221 */         currentTime -= Long.parseLong(maxClockSkew);
/*     */       }
/*     */       
/* 224 */       c.setTimeInMillis(currentTime);
/*     */       
/* 226 */       Date currentTimeInDateFormat = c.getTime();
/*     */ 
/*     */       
/* 229 */       if (!currentTimeInDateFormat.before(ctx.getExpirationTime())) {
/* 230 */         throw new WSSecureConversationException("SecureConversation session for session Id: " + key + " has expired.");
/*     */       }
/*     */     } 
/* 233 */     return ctx;
/*     */   }
/*     */   
/*     */   private SecurityContextTokenInfo getSecurityContextTokenInfo(String key) {
/* 237 */     SecurityContextTokenInfo ctx = this.securityContextTokenMap.get(key);
/* 238 */     return ctx;
/*     */   }
/*     */   
/*     */   private void appendEndorsingSCTRenewPolicy(MessagePolicy policy) throws PolicyGenerationException {
/* 242 */     SignaturePolicy sp = scp.getRenewSignaturePolicy();
/* 243 */     SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/* 244 */     List list = policy.getPrimaryPolicies();
/* 245 */     Iterator<SecurityPolicy> i = list.iterator();
/* 246 */     boolean addedSigTarget = false;
/* 247 */     while (i.hasNext()) {
/* 248 */       SecurityPolicy primaryPolicy = i.next();
/* 249 */       if (PolicyTypeUtil.signaturePolicy(primaryPolicy)) {
/* 250 */         SignaturePolicy sigPolicy = (SignaturePolicy)primaryPolicy;
/* 251 */         IntegrityAssertionProcessor iAP = new IntegrityAssertionProcessor(scp.getAlgorithmSuite(), true);
/* 252 */         SignatureTargetCreator stc = iAP.getTargetCreator();
/* 253 */         SignatureTarget sigTarget = stc.newURISignatureTarget(sigPolicy.getUUID());
/* 254 */         SecurityPolicyUtil.setName((Target)sigTarget, (WSSPolicy)sigPolicy);
/* 255 */         spFB.addTargetBinding(sigTarget);
/* 256 */         spFB.isEndorsingSignature(true);
/* 257 */         addedSigTarget = true;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     if (!addedSigTarget) {
/* 265 */       List sList = policy.getSecondaryPolicies();
/* 266 */       Iterator<SecurityPolicy> j = sList.iterator();
/* 267 */       while (j.hasNext()) {
/* 268 */         SecurityPolicy secPolicy = j.next();
/* 269 */         if (PolicyTypeUtil.timestampPolicy(secPolicy)) {
/* 270 */           TimestampPolicy tsPolicy = (TimestampPolicy)secPolicy;
/* 271 */           IntegrityAssertionProcessor iAP = new IntegrityAssertionProcessor(scp.getAlgorithmSuite(), true);
/* 272 */           SignatureTargetCreator stc = iAP.getTargetCreator();
/* 273 */           SignatureTarget sigTarget = stc.newURISignatureTarget(tsPolicy.getUUID());
/* 274 */           SecurityPolicyUtil.setName((Target)sigTarget, (WSSPolicy)tsPolicy);
/* 275 */           spFB.addTargetBinding(sigTarget);
/* 276 */           spFB.isEndorsingSignature(true);
/* 277 */           addedSigTarget = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 282 */     if (addedSigTarget) {
/* 283 */       policy.append((SecurityPolicy)sp);
/*     */     }
/*     */   }
/*     */   
/*     */   private void deleteRenewPolicy(MessagePolicy policy) {
/* 288 */     ArrayList list = policy.getPrimaryPolicies();
/* 289 */     Iterator<SecurityPolicy> i = list.iterator();
/* 290 */     while (i.hasNext()) {
/* 291 */       SecurityPolicy primaryPolicy = i.next();
/* 292 */       if (PolicyTypeUtil.signaturePolicy(primaryPolicy)) {
/* 293 */         SignaturePolicy sigPolicy = (SignaturePolicy)primaryPolicy;
/* 294 */         if (sigPolicy.getUUID().equals("_99")) {
/* 295 */           policy.remove((SecurityPolicy)sigPolicy);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\impl\client\SCTokenProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */