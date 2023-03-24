/*     */ package com.sun.xml.ws.runtime.dev;
/*     */ 
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.security.secconv.WSSecureConversationRuntimeException;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.io.Serializable;
/*     */ import java.net.URI;
/*     */ import java.security.Key;
/*     */ import java.security.KeyPair;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ import org.glassfish.ha.store.api.BackingStoreFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SessionManagerImpl
/*     */   extends SessionManager
/*     */ {
/*  94 */   private Map<String, Session> sessionMap = new HashMap<String, Session>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private Map<String, IssuedTokenContext> issuedTokenContextMap = new HashMap<String, IssuedTokenContext>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private Map<String, SecurityContextTokenInfo> securityContextTokenInfoMap = new HashMap<String, SecurityContextTokenInfo>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final BackingStore<StickyKey, HASecurityContextTokenInfo> sctBs;
/*     */ 
/*     */ 
/*     */   
/*     */   public SessionManagerImpl(WSEndpoint endpoint, boolean isSC) {
/* 113 */     if (isSC) {
/* 114 */       BackingStoreFactory bsFactory = HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(HighAvailabilityProvider.StoreType.IN_MEMORY);
/* 115 */       this.sctBs = HighAvailabilityProvider.INSTANCE.createBackingStore(bsFactory, endpoint.getServiceName() + ":" + endpoint.getPortName() + "_SCT_BS", StickyKey.class, HASecurityContextTokenInfo.class);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 121 */       this.sctBs = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SessionManagerImpl(WSEndpoint endpoint, boolean isSC, Properties config) {
/* 127 */     this(endpoint, isSC);
/* 128 */     this; setConfig(config);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Session getSession(String key) {
/* 138 */     Session session = this.sessionMap.get(key);
/* 139 */     if (session == null && HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured() && this.sctBs != null) {
/* 140 */       SecurityContextTokenInfo sctInfo = (SecurityContextTokenInfo)HighAvailabilityProvider.loadFrom(this.sctBs, (Serializable)new StickyKey(key), null);
/* 141 */       session = new Session(this, key, null);
/* 142 */       session.setSecurityInfo(sctInfo);
/* 143 */       this.sessionMap.put(key, session);
/*     */     } 
/* 145 */     return session;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> keys() {
/* 154 */     return this.sessionMap.keySet();
/*     */   }
/*     */   
/*     */   protected Collection<Session> sessions() {
/* 158 */     return this.sessionMap.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminateSession(String key) {
/* 167 */     this.sessionMap.remove(key);
/* 168 */     if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured() && this.sctBs != null) {
/* 169 */       HighAvailabilityProvider.removeFrom(this.sctBs, (Serializable)new StickyKey(key));
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
/*     */   public Session createSession(String key, Class clasz) {
/*     */     Session sess;
/* 185 */     Properties props = getConfig();
/* 186 */     String timeout = (String)props.get("session-timeout");
/* 187 */     int timeOut = 30;
/* 188 */     if (timeout != null) {
/* 189 */       timeOut = Integer.parseInt(timeout);
/*     */     }
/* 191 */     for (Session session : this.sessionMap.values()) {
/* 192 */       SecurityContextTokenInfo securityInfo = session.getSecurityInfo();
/* 193 */       Date expDate = securityInfo.getExpirationTime();
/* 194 */       Calendar expCal = Calendar.getInstance(Locale.getDefault());
/* 195 */       expCal.setTimeInMillis(expDate.getTime());
/* 196 */       if (Calendar.getInstance(Locale.getDefault()).compareTo(expCal) > timeOut * 60 * 1000) {
/* 197 */         terminateSession(session.getSessionKey());
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 202 */       sess = new Session(this, key, clasz.newInstance());
/* 203 */     } catch (InstantiationException e) {
/* 204 */       return null;
/* 205 */     } catch (IllegalAccessException ee) {
/* 206 */       return null;
/*     */     } 
/*     */     
/* 209 */     this.sessionMap.put(key, sess);
/* 210 */     return sess;
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
/*     */   public Session createSession(String key, Object obj) {
/* 225 */     Session session = new Session(this, key, Collections.synchronizedMap(new HashMap<Object, Object>()));
/* 226 */     this.sessionMap.put(key, session);
/*     */     
/* 228 */     return session;
/*     */   }
/*     */   
/*     */   public Session createSession(String key, SecurityContextTokenInfo sctInfo) {
/* 232 */     Session session = new Session(this, key, Collections.synchronizedMap(new HashMap<Object, Object>()));
/* 233 */     session.setSecurityInfo(sctInfo);
/* 234 */     this.sessionMap.put(key, session);
/*     */     
/* 236 */     if (sctInfo != null && HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
/* 237 */       HASecurityContextTokenInfo hasctInfo = new HASecurityContextTokenInfo(sctInfo);
/* 238 */       HaInfo haInfo = HaContext.currentHaInfo();
/* 239 */       if (haInfo != null) {
/* 240 */         HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.sctBs, (Serializable)new StickyKey(key, haInfo.getKey()), (Serializable)hasctInfo, true));
/*     */       } else {
/* 242 */         StickyKey stickyKey = new StickyKey(key);
/* 243 */         String replicaId = HighAvailabilityProvider.saveTo(this.sctBs, (Serializable)stickyKey, (Serializable)hasctInfo, true);
/* 244 */         HaContext.updateHaInfo(new HaInfo(stickyKey.getHashKey(), replicaId, false));
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return session;
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
/*     */   public Session createSession(String key) {
/* 260 */     return createSession(key, Collections.synchronizedMap(new HashMap<Object, Object>()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSession(String key) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuedTokenContext getSecurityContext(String key, boolean checkExpiry) {
/* 280 */     IssuedTokenContext ctx = this.issuedTokenContextMap.get(key);
/* 281 */     if (ctx == null) {
/*     */       
/* 283 */       boolean recovered = false;
/* 284 */       Session session = getSession(key);
/* 285 */       if (session != null) {
/*     */         
/* 287 */         SecurityContextTokenInfo sctInfo = session.getSecurityInfo();
/* 288 */         if (sctInfo != null) {
/* 289 */           ctx = sctInfo.getIssuedTokenContext();
/*     */           
/* 291 */           addSecurityContext(key, ctx);
/* 292 */           recovered = true;
/*     */         } 
/*     */       } 
/*     */       
/* 296 */       if (!recovered) {
/* 297 */         throw new WebServiceException("Could not locate SecureConversation session for Id:" + key);
/*     */       }
/* 299 */     } else if (ctx.getSecurityContextTokenInfo() == null && ctx.getSecurityToken() != null) {
/* 300 */       String sctInfoKey = ((SecurityContextToken)ctx.getSecurityToken()).getIdentifier().toString() + "_" + ((SecurityContextToken)ctx.getSecurityToken()).getInstance();
/*     */ 
/*     */       
/* 303 */       ctx.setSecurityContextTokenInfo(this.securityContextTokenInfoMap.get(sctInfoKey));
/*     */     } 
/*     */     
/* 306 */     if (ctx != null && checkExpiry) {
/*     */       
/* 308 */       Calendar c = new GregorianCalendar();
/* 309 */       long offset = c.get(15);
/* 310 */       if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 311 */         offset += c.getTimeZone().getDSTSavings();
/*     */       }
/* 313 */       long beforeTime = c.getTimeInMillis();
/* 314 */       long currentTime = beforeTime - offset;
/*     */       
/* 316 */       c.setTimeInMillis(currentTime);
/*     */       
/* 318 */       Date currentTimeInDateFormat = c.getTime();
/* 319 */       if (!currentTimeInDateFormat.after(ctx.getCreationTime()) || !currentTimeInDateFormat.before(ctx.getExpirationTime()))
/*     */       {
/* 321 */         throw new WSSecureConversationRuntimeException(new QName("RenewNeeded"), "The provided context token has expired");
/*     */       }
/*     */     } 
/*     */     
/* 325 */     return ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSecurityContext(String key, IssuedTokenContext itctx) {
/* 335 */     this.issuedTokenContextMap.put(key, itctx);
/* 336 */     SecurityContextTokenInfo sctInfo = itctx.getSecurityContextTokenInfo();
/* 337 */     if (sctInfo.getInstance() != null) {
/* 338 */       String sctInfoKey = sctInfo.getIdentifier().toString() + "_" + sctInfo.getInstance();
/*     */       
/* 340 */       this.securityContextTokenInfoMap.put(sctInfoKey, sctInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class HASecurityContextTokenInfo
/*     */     implements SecurityContextTokenInfo {
/* 346 */     String identifier = null;
/* 347 */     String extId = null;
/* 348 */     String instance = null;
/* 349 */     byte[] secret = null;
/* 350 */     Map<String, byte[]> secretMap = (Map)new HashMap<String, byte>();
/* 351 */     Date creationTime = null;
/* 352 */     Date expirationTime = null;
/*     */ 
/*     */     
/*     */     public HASecurityContextTokenInfo() {}
/*     */ 
/*     */     
/*     */     public HASecurityContextTokenInfo(SecurityContextTokenInfo sctInfo) {
/* 359 */       this.identifier = sctInfo.getIdentifier();
/* 360 */       this.extId = sctInfo.getExternalId();
/* 361 */       this.instance = sctInfo.getInstance();
/* 362 */       this.secret = sctInfo.getSecret();
/* 363 */       this.creationTime = sctInfo.getCreationTime();
/* 364 */       this.expirationTime = sctInfo.getExpirationTime();
/* 365 */       Set<String> instKeys = sctInfo.getInstanceKeys();
/* 366 */       for (String instKey : instKeys) {
/* 367 */         this.secretMap.put(instKey, sctInfo.getInstanceSecret(this.instance));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIdentifier() {
/* 373 */       return this.identifier;
/*     */     }
/*     */     
/*     */     public void setIdentifier(String identifier) {
/* 377 */       this.identifier = identifier;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getExternalId() {
/* 384 */       return this.extId;
/*     */     }
/*     */     
/*     */     public void setExternalId(String externalId) {
/* 388 */       this.extId = externalId;
/*     */     }
/*     */     
/*     */     public String getInstance() {
/* 392 */       return this.instance;
/*     */     }
/*     */     
/*     */     public void setInstance(String instance) {
/* 396 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public byte[] getSecret() {
/* 400 */       byte[] newSecret = new byte[this.secret.length];
/* 401 */       System.arraycopy(this.secret, 0, newSecret, 0, this.secret.length);
/* 402 */       return newSecret;
/*     */     }
/*     */     
/*     */     public byte[] getInstanceSecret(String instance) {
/* 406 */       return this.secretMap.get(instance);
/*     */     }
/*     */     
/*     */     public void addInstance(String instance, byte[] key) {
/* 410 */       byte[] newKey = new byte[key.length];
/* 411 */       System.arraycopy(key, 0, newKey, 0, key.length);
/* 412 */       if (instance == null) {
/* 413 */         this.secret = newKey;
/*     */       } else {
/* 415 */         this.secretMap.put(instance, newKey);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Date getCreationTime() {
/* 420 */       return new Date(this.creationTime.getTime());
/*     */     }
/*     */     
/*     */     public void setCreationTime(Date creationTime) {
/* 424 */       this.creationTime = new Date(creationTime.getTime());
/*     */     }
/*     */     
/*     */     public Date getExpirationTime() {
/* 428 */       return new Date(this.expirationTime.getTime());
/*     */     }
/*     */     
/*     */     public void setExpirationTime(Date expirationTime) {
/* 432 */       this.expirationTime = new Date(expirationTime.getTime());
/*     */     }
/*     */     
/*     */     public Set getInstanceKeys() {
/* 436 */       return this.secretMap.keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IssuedTokenContext getIssuedTokenContext() {
/* 441 */       IssuedTokenContext itc = new SessionManagerImpl.HAIssuedTokenContext();
/* 442 */       itc.setCreationTime(getCreationTime());
/* 443 */       itc.setExpirationTime(getExpirationTime());
/* 444 */       itc.setProofKey(getSecret());
/* 445 */       itc.setSecurityContextTokenInfo(this);
/*     */       
/* 447 */       return itc;
/*     */     }
/*     */     
/*     */     public IssuedTokenContext getIssuedTokenContext(SecurityTokenReference reference) {
/* 451 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 456 */       String str = "Identifier=" + this.identifier + " : Secret=" + this.secret + " : ExternalId=" + this.extId + " : Creation Time=" + this.creationTime + " : Expiration Time=" + this.expirationTime;
/*     */ 
/*     */       
/* 459 */       return str;
/*     */     }
/*     */   }
/*     */   
/*     */   static class HAIssuedTokenContext implements IssuedTokenContext {
/* 464 */     X509Certificate x509Certificate = null;
/* 465 */     Token securityToken = null;
/* 466 */     Token associatedProofToken = null;
/* 467 */     Token secTokenReference = null;
/* 468 */     Token unAttachedSecTokenReference = null;
/* 469 */     ArrayList<Object> securityPolicies = new ArrayList();
/* 470 */     Object otherPartyEntropy = null;
/* 471 */     Object selfEntropy = null;
/*     */     URI computedKeyAlgorithm;
/*     */     String sigAlgorithm;
/*     */     String encAlgorithm;
/*     */     String canonicalizationAlgorithm;
/*     */     String signWith;
/*     */     String encryptWith;
/* 478 */     byte[] proofKey = null;
/* 479 */     SecurityContextTokenInfo sctInfo = null;
/* 480 */     Date creationTime = null;
/* 481 */     Date expiryTime = null;
/* 482 */     String username = null;
/* 483 */     String endPointAddress = null;
/*     */     Subject subject;
/*     */     KeyPair proofKeyPair;
/* 486 */     String authType = null;
/* 487 */     String tokenType = null;
/* 488 */     String keyType = null;
/* 489 */     String tokenIssuer = null;
/* 490 */     Token target = null;
/*     */     
/* 492 */     Map<String, Object> otherProps = new HashMap<String, Object>();
/*     */     
/*     */     public X509Certificate getRequestorCertificate() {
/* 495 */       return this.x509Certificate;
/*     */     }
/*     */     
/*     */     public void setRequestorCertificate(X509Certificate cert) {
/* 499 */       this.x509Certificate = cert;
/*     */     }
/*     */     
/*     */     public Subject getRequestorSubject() {
/* 503 */       return this.subject;
/*     */     }
/*     */     
/*     */     public void setRequestorSubject(Subject subject) {
/* 507 */       this.subject = subject;
/*     */     }
/*     */     
/*     */     public String getRequestorUsername() {
/* 511 */       return this.username;
/*     */     }
/*     */     
/*     */     public void setRequestorUsername(String username) {
/* 515 */       this.username = username;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSecurityToken(Token securityToken) {
/* 520 */       this.securityToken = securityToken;
/*     */     }
/*     */     
/*     */     public Token getSecurityToken() {
/* 524 */       return this.securityToken;
/*     */     }
/*     */     
/*     */     public void setAssociatedProofToken(Token associatedProofToken) {
/* 528 */       this.associatedProofToken = associatedProofToken;
/*     */     }
/*     */     
/*     */     public Token getAssociatedProofToken() {
/* 532 */       return this.associatedProofToken;
/*     */     }
/*     */     
/*     */     public Token getAttachedSecurityTokenReference() {
/* 536 */       return this.secTokenReference;
/*     */     }
/*     */     
/*     */     public void setAttachedSecurityTokenReference(Token secTokenReference) {
/* 540 */       this.secTokenReference = secTokenReference;
/*     */     }
/*     */     
/*     */     public Token getUnAttachedSecurityTokenReference() {
/* 544 */       return this.unAttachedSecTokenReference;
/*     */     }
/*     */     
/*     */     public void setUnAttachedSecurityTokenReference(Token secTokenReference) {
/* 548 */       this.unAttachedSecTokenReference = secTokenReference;
/*     */     }
/*     */     
/*     */     public ArrayList<Object> getSecurityPolicy() {
/* 552 */       return this.securityPolicies;
/*     */     }
/*     */     
/*     */     public void setOtherPartyEntropy(Object otherPartyEntropy) {
/* 556 */       this.otherPartyEntropy = otherPartyEntropy;
/*     */     }
/*     */     
/*     */     public Object getOtherPartyEntropy() {
/* 560 */       return this.otherPartyEntropy;
/*     */     }
/*     */     
/*     */     public Key getDecipheredOtherPartyEntropy(Key privKey) throws XWSSecurityException {
/* 564 */       return null;
/*     */     }
/*     */     
/*     */     public void setSelfEntropy(Object selfEntropy) {
/* 568 */       this.selfEntropy = selfEntropy;
/*     */     }
/*     */     
/*     */     public Object getSelfEntropy() {
/* 572 */       return this.selfEntropy;
/*     */     }
/*     */ 
/*     */     
/*     */     public URI getComputedKeyAlgorithmFromProofToken() {
/* 577 */       return this.computedKeyAlgorithm;
/*     */     }
/*     */     
/*     */     public void setComputedKeyAlgorithmFromProofToken(URI computedKeyAlgorithm) {
/* 581 */       this.computedKeyAlgorithm = computedKeyAlgorithm;
/*     */     }
/*     */     
/*     */     public void setProofKey(byte[] key) {
/* 585 */       this.proofKey = key;
/*     */     }
/*     */     
/*     */     public byte[] getProofKey() {
/* 589 */       return this.proofKey;
/*     */     }
/*     */     
/*     */     public void setProofKeyPair(KeyPair keys) {
/* 593 */       this.proofKeyPair = keys;
/*     */     }
/*     */     
/*     */     public KeyPair getProofKeyPair() {
/* 597 */       return this.proofKeyPair;
/*     */     }
/*     */     
/*     */     public void setAuthnContextClass(String authType) {
/* 601 */       this.authType = authType;
/*     */     }
/*     */     
/*     */     public String getAuthnContextClass() {
/* 605 */       return this.authType;
/*     */     }
/*     */     
/*     */     public Date getCreationTime() {
/* 609 */       return this.creationTime;
/*     */     }
/*     */     
/*     */     public Date getExpirationTime() {
/* 613 */       return this.expiryTime;
/*     */     }
/*     */     
/*     */     public void setCreationTime(Date date) {
/* 617 */       this.creationTime = date;
/*     */     }
/*     */     
/*     */     public void setExpirationTime(Date date) {
/* 621 */       this.expiryTime = date;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setEndpointAddress(String endPointAddress) {
/* 628 */       this.endPointAddress = endPointAddress;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getEndpointAddress() {
/* 635 */       return this.endPointAddress;
/*     */     }
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */     
/*     */     public SecurityContextTokenInfo getSecurityContextTokenInfo() {
/* 643 */       return this.sctInfo;
/*     */     }
/*     */     
/*     */     public void setSecurityContextTokenInfo(SecurityContextTokenInfo sctInfo) {
/* 647 */       this.sctInfo = sctInfo;
/*     */     }
/*     */     
/*     */     public Map<String, Object> getOtherProperties() {
/* 651 */       return this.otherProps;
/*     */     }
/*     */     
/*     */     public void setTokenType(String tokenType) {
/* 655 */       this.tokenType = tokenType;
/*     */     }
/*     */     
/*     */     public String getTokenType() {
/* 659 */       return this.tokenType;
/*     */     }
/*     */     
/*     */     public void setKeyType(String keyType) {
/* 663 */       this.keyType = keyType;
/*     */     }
/*     */     
/*     */     public String getKeyType() {
/* 667 */       return this.keyType;
/*     */     }
/*     */     
/*     */     public void setAppliesTo(String appliesTo) {
/* 671 */       this.endPointAddress = appliesTo;
/*     */     }
/*     */     
/*     */     public String getAppliesTo() {
/* 675 */       return this.endPointAddress;
/*     */     }
/*     */     
/*     */     public void setTokenIssuer(String issuer) {
/* 679 */       this.tokenIssuer = issuer;
/*     */     }
/*     */     
/*     */     public String getTokenIssuer() {
/* 683 */       return this.tokenIssuer;
/*     */     }
/*     */     
/*     */     public void setSignatureAlgorithm(String sigAlg) {
/* 687 */       this.sigAlgorithm = sigAlg;
/*     */     }
/*     */     
/*     */     public String getSignatureAlgorithm() {
/* 691 */       return this.sigAlgorithm;
/*     */     }
/*     */     
/*     */     public void setEncryptionAlgorithm(String encAlg) {
/* 695 */       this.encAlgorithm = encAlg;
/*     */     }
/*     */     
/*     */     public String getEncryptionAlgorithm() {
/* 699 */       return this.encAlgorithm;
/*     */     }
/*     */     
/*     */     public void setCanonicalizationAlgorithm(String canonAlg) {
/* 703 */       this.canonicalizationAlgorithm = canonAlg;
/*     */     }
/*     */     
/*     */     public String getCanonicalizationAlgorithm() {
/* 707 */       return this.canonicalizationAlgorithm;
/*     */     }
/*     */     
/*     */     public void setSignWith(String signWithAlgo) {
/* 711 */       this.signWith = signWithAlgo;
/*     */     }
/*     */     
/*     */     public String getSignWith() {
/* 715 */       return this.signWith;
/*     */     }
/*     */     
/*     */     public void setEncryptWith(String encryptWithAlgo) {
/* 719 */       this.encryptWith = encryptWithAlgo;
/*     */     }
/*     */     
/*     */     public String getEncryptWith() {
/* 723 */       return this.encryptWith;
/*     */     }
/*     */     
/*     */     public void setTarget(Token target) {
/* 727 */       this.target = target;
/*     */     }
/*     */     
/*     */     public Token getTarget() {
/* 731 */       return this.target;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\dev\SessionManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */