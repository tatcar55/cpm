/*     */ package com.sun.xml.ws.security.secconv;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.runtime.dev.Session;
/*     */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust10;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust13;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.secconv.impl.SecurityContextTokenInfoImpl;
/*     */ import com.sun.xml.ws.security.secconv.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.RenewTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.DirectReference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public class WSSCContract
/*     */ {
/* 111 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.secconv", "com.sun.xml.ws.security.secconv.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private long currentTime;
/*     */ 
/*     */   
/* 117 */   private SymmetricBinding symBinding = null;
/*     */   private boolean reqServerEntr = true;
/*     */   private boolean reqClientEntr = false;
/* 120 */   private WSSCVersion wsscVer = WSSCVersion.WSSC_10;
/* 121 */   private WSTrustVersion wsTrustVer = WSTrustVersion.WS_TRUST_10;
/* 122 */   private WSTrustElementFactory wsscEleFac = WSTrustElementFactory.newInstance(WSSCVersion.WSSC_10);
/*     */   
/*     */   private static final int DEFAULT_KEY_SIZE = 128;
/*     */   
/*     */   public static final String LIFETIME = "LifeTime";
/*     */   
/*     */   public static final String SC_CONFIGURATION = "SCConfiguration";
/* 129 */   private long TIMEOUT = 36000000L;
/* 130 */   private static final SimpleDateFormat calendarFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'", Locale.getDefault());
/*     */ 
/*     */ 
/*     */   
/*     */   public WSSCContract() {}
/*     */ 
/*     */   
/*     */   public WSSCContract(WSSCVersion wsscVer) {
/* 138 */     init(wsscVer);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void init(WSSCVersion wsscVer) {
/* 143 */     if (wsscVer instanceof com.sun.xml.ws.security.secconv.impl.wssx.WSSCVersion13) {
/* 144 */       this.wsscVer = wsscVer;
/* 145 */       this.wsTrustVer = WSTrustVersion.WS_TRUST_13;
/* 146 */       this.wsscEleFac = WSTrustElementFactory.newInstance(WSSCVersion.WSSC_13);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse issue(BaseSTSRequest request, IssuedTokenContext context, SecureConversationToken scToken) throws WSSecureConversationException {
/* 154 */     URI tokenType = URI.create(this.wsscVer.getSCTTokenTypeURI());
/* 155 */     URI con = null;
/* 156 */     URI computeKeyAlgo = URI.create(this.wsTrustVer.getCKPSHA1algorithmURI());
/*     */     
/* 158 */     String conStr = ((RequestSecurityToken)request).getContext();
/* 159 */     if (conStr != null) {
/*     */       try {
/* 161 */         con = new URI(conStr);
/* 162 */       } catch (URISyntaxException ex) {
/* 163 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0008_URISYNTAX_EXCEPTION(((RequestSecurityToken)request).getContext()), ex);
/*     */         
/* 165 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0008_URISYNTAX_EXCEPTION(((RequestSecurityToken)request).getContext()), ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 171 */     AppliesTo scopes = ((RequestSecurityToken)request).getAppliesTo();
/*     */     
/* 173 */     RequestedProofToken proofToken = this.wsscEleFac.createRequestedProofToken();
/*     */     
/* 175 */     byte[] clientEntr = null;
/* 176 */     Entropy clientEntropy = ((RequestSecurityToken)request).getEntropy();
/* 177 */     if (clientEntropy != null) {
/* 178 */       BinarySecret clientBS = clientEntropy.getBinarySecret();
/* 179 */       if (clientBS == null) {
/*     */         
/* 181 */         if (log.isLoggable(Level.FINE)) {
/* 182 */           log.log(Level.FINE, LogStringsMessages.WSSC_0009_CLIENT_ENTROPY_VALUE("null"));
/*     */         }
/*     */       } else {
/*     */         
/* 186 */         clientEntr = clientBS.getRawValue();
/* 187 */         if (log.isLoggable(Level.FINE)) {
/* 188 */           log.log(Level.FINE, LogStringsMessages.WSSC_0009_CLIENT_ENTROPY_VALUE(clientEntropy.toString()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     BaseSTSResponse response = createRSTR(computeKeyAlgo, scToken, request, scopes, clientEntr, proofToken, tokenType, clientEntropy, context, con);
/*     */     
/* 195 */     if (log.isLoggable(Level.FINE)) {
/* 196 */       log.log(Level.FINE, LogStringsMessages.WSSC_0014_RSTR_RESPONSE(WSTrustUtil.elemToString(response, this.wsTrustVer)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     updateSubject(context);
/*     */     
/* 203 */     return response;
/*     */   }
/*     */   
/*     */   private void parseAssertion(SecureConversationToken scToken, Entropy clientEntropy) throws WSSecureConversationException, WSSecureConversationException {
/* 207 */     Trust10 trust10 = null;
/* 208 */     Trust13 trust13 = null;
/* 209 */     NestedPolicy wsPolicy = scToken.getBootstrapPolicy();
/* 210 */     AssertionSet assertionSet = wsPolicy.getAssertionSet();
/* 211 */     for (PolicyAssertion policyAssertion : assertionSet) {
/* 212 */       SecurityPolicyVersion spVersion = getSPVersion(policyAssertion);
/* 213 */       if (PolicyUtil.isTrust13(policyAssertion, spVersion)) {
/* 214 */         trust13 = (Trust13)policyAssertion; continue;
/* 215 */       }  if (PolicyUtil.isTrust10(policyAssertion, spVersion)) {
/* 216 */         trust10 = (Trust10)policyAssertion; continue;
/* 217 */       }  if (PolicyUtil.isSymmetricBinding(policyAssertion, spVersion)) {
/* 218 */         this.symBinding = (SymmetricBinding)policyAssertion;
/*     */       }
/*     */     } 
/*     */     
/* 222 */     if (trust10 != null) {
/* 223 */       Set trustReqdProps = trust10.getRequiredProperties();
/* 224 */       this.reqServerEntr = trustReqdProps.contains("RequireServerEntropy");
/* 225 */       this.reqClientEntr = trustReqdProps.contains("RequireClientEntropy");
/*     */     } 
/* 227 */     if (trust13 != null) {
/* 228 */       Set trustReqdProps = trust13.getRequiredProperties();
/* 229 */       this.reqServerEntr = trustReqdProps.contains("RequireServerEntropy");
/* 230 */       this.reqClientEntr = trustReqdProps.contains("RequireClientEntropy");
/*     */     } 
/* 232 */     if (clientEntropy == null) {
/* 233 */       if (this.reqClientEntr) {
/* 234 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0010_CLIENT_ENTROPY_CANNOT_NULL());
/*     */         
/* 236 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0010_CLIENT_ENTROPY_CANNOT_NULL());
/*     */       } 
/* 238 */       this.reqServerEntr = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BaseSTSResponse createRSTR(URI computeKeyAlgo, SecureConversationToken scToken, BaseSTSRequest request, AppliesTo scopes, byte[] clientEntr, RequestedProofToken proofToken, URI tokenType, Entropy clientEntropy, IssuedTokenContext context, URI con) throws WSSecureConversationException, WSSecureConversationException {
/* 245 */     parseAssertion(scToken, clientEntropy);
/*     */     
/* 247 */     int keySize = (int)((RequestSecurityToken)request).getKeySize();
/* 248 */     if (keySize < 1 && this.symBinding != null) {
/* 249 */       AlgorithmSuite algoSuite = this.symBinding.getAlgorithmSuite();
/* 250 */       keySize = algoSuite.getMinSKLAlgorithm();
/*     */     } 
/* 252 */     if (keySize < 1) {
/* 253 */       keySize = 128;
/*     */     }
/* 255 */     if (log.isLoggable(Level.FINE)) {
/* 256 */       log.log(Level.FINE, LogStringsMessages.WSSC_0011_KEY_SIZE_VALUE(Integer.valueOf(keySize), Integer.valueOf(128)));
/*     */     }
/*     */ 
/*     */     
/* 260 */     byte[] secret = WSTrustUtil.generateRandomSecret(keySize / 8);
/* 261 */     String proofTokenType = (clientEntr == null || clientEntr.length == 0) ? this.wsTrustVer.getSymmetricKeyTypeURI() : this.wsTrustVer.getNonceBinarySecretTypeURI();
/*     */     
/* 263 */     Entropy serverEntropy = null;
/* 264 */     if (this.reqServerEntr) {
/* 265 */       BinarySecret serverBS = this.wsscEleFac.createBinarySecret(secret, proofTokenType);
/*     */       
/* 267 */       if (proofTokenType.equals(this.wsTrustVer.getNonceBinarySecretTypeURI())) {
/* 268 */         serverEntropy = this.wsscEleFac.createEntropy(serverBS);
/* 269 */         proofToken.setProofTokenType("ComputedKey");
/* 270 */         proofToken.setComputedKey(computeKeyAlgo);
/*     */ 
/*     */         
/*     */         try {
/* 274 */           secret = SecurityUtil.P_SHA1(clientEntr, secret, keySize / 8);
/* 275 */         } catch (Exception ex) {
/* 276 */           log.log(Level.SEVERE, LogStringsMessages.WSSC_0012_COMPUTE_SECKEY(), ex);
/*     */           
/* 278 */           throw new WSSecureConversationException(LogStringsMessages.WSSC_0012_COMPUTE_SECKEY(), ex);
/*     */         } 
/*     */       } else {
/*     */         
/* 282 */         proofToken.setProofTokenType("BinarySecret");
/* 283 */         proofToken.setBinarySecret(serverBS);
/*     */       } 
/* 285 */     } else if (clientEntropy != null) {
/* 286 */       secret = clientEntr;
/* 287 */       proofToken.setProofTokenType("BinarySecret");
/* 288 */       proofToken.setBinarySecret(clientEntropy.getBinarySecret());
/*     */     } 
/*     */     
/* 291 */     Lifetime lifetime = ((RequestSecurityToken)request).getLifetime();
/*     */     
/* 293 */     if (lifetime != null) {
/* 294 */       long timeout = WSTrustUtil.getLifeSpan(lifetime);
/* 295 */       if (timeout > 0L) {
/* 296 */         setSCTokenTimeout(timeout);
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return createResponse(serverEntropy, con, scopes, secret, proofToken, context, tokenType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BaseSTSResponse createResponse(Entropy serverEntropy, URI con, AppliesTo scopes, byte[] secret, RequestedProofToken proofToken, IssuedTokenContext context, URI tokenType) throws WSSecureConversationException {
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/* 307 */     SecurityContextToken token = WSTrustUtil.createSecurityContextToken(this.wsscEleFac);
/* 308 */     RequestedSecurityToken rst = this.wsscEleFac.createRequestedSecurityToken((Token)token);
/*     */ 
/*     */     
/* 311 */     SecurityTokenReference attachedReference = createSecurityTokenReference(token.getWsuId(), false);
/* 312 */     RequestedAttachedReference rar = this.wsscEleFac.createRequestedAttachedReference(attachedReference);
/* 313 */     SecurityTokenReference unattachedRef = createSecurityTokenReference(token.getIdentifier().toString(), true);
/* 314 */     RequestedUnattachedReference rur = this.wsscEleFac.createRequestedUnattachedReference(unattachedRef);
/*     */ 
/*     */     
/* 317 */     long now = WSTrustUtil.getCurrentTimeWithOffset();
/* 318 */     Lifetime lifetime = WSTrustUtil.createLifetime(now, getSCTokenTimeout(), this.wsTrustVer);
/*     */     
/* 320 */     BaseSTSResponse response = null;
/*     */     try {
/* 322 */       if (this.wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/* 323 */         RequestSecurityTokenResponseCollection requestSecurityTokenResponseCollection = this.wsscEleFac.createRSTRCollectionForIssue(tokenType, con, rst, scopes, rar, rur, proofToken, serverEntropy, lifetime);
/*     */       } else {
/* 325 */         requestSecurityTokenResponse = this.wsscEleFac.createRSTRForIssue(tokenType, con, rst, scopes, rar, rur, proofToken, serverEntropy, lifetime);
/*     */       } 
/* 327 */     } catch (WSTrustException ex) {
/* 328 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), (Throwable)ex);
/*     */       
/* 330 */       throw new WSSecureConversationException(LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), ex);
/*     */     } 
/*     */     
/* 333 */     if (log.isLoggable(Level.FINE)) {
/* 334 */       log.log(Level.FINE, LogStringsMessages.WSSC_1010_CREATING_SESSION(token.getIdentifier()));
/*     */     }
/*     */     
/* 337 */     populateITC(now, secret, token, attachedReference, context, unattachedRef);
/* 338 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateITC(long currentTime, byte[] secret, SecurityContextToken token, SecurityTokenReference attachedReference, IssuedTokenContext context, SecurityTokenReference unattachedRef) {
/* 344 */     context.setSecurityToken((Token)token);
/* 345 */     context.setAttachedSecurityTokenReference((Token)attachedReference);
/* 346 */     context.setUnAttachedSecurityTokenReference((Token)unattachedRef);
/* 347 */     context.setProofKey(secret);
/* 348 */     context.setCreationTime(new Date(currentTime));
/* 349 */     context.setExpirationTime(new Date(currentTime + getSCTokenTimeout()));
/*     */     
/* 351 */     SecurityContextTokenInfoImpl securityContextTokenInfoImpl = new SecurityContextTokenInfoImpl();
/* 352 */     securityContextTokenInfoImpl.setIdentifier(token.getIdentifier().toString());
/* 353 */     securityContextTokenInfoImpl.setExternalId(token.getWsuId());
/* 354 */     securityContextTokenInfoImpl.addInstance(null, secret);
/*     */     
/* 356 */     securityContextTokenInfoImpl.setCreationTime(new Date(currentTime));
/* 357 */     securityContextTokenInfoImpl.setExpirationTime(new Date(currentTime + getSCTokenTimeout()));
/*     */     
/* 359 */     SessionManager sm = (SessionManager)context.getOtherProperties().get("SessionManager");
/* 360 */     sm.createSession(token.getIdentifier().toString(), (SecurityContextTokenInfo)securityContextTokenInfoImpl);
/* 361 */     context.setSecurityContextTokenInfo((SecurityContextTokenInfo)securityContextTokenInfoImpl);
/* 362 */     sm.addSecurityContext(token.getIdentifier().toString(), context);
/*     */   }
/*     */ 
/*     */   
/*     */   private void populateRenewedITC(Session session, byte[] secret, SecurityContextToken token, IssuedTokenContext context, SecurityTokenReference attachedReference) {
/* 367 */     context.setSecurityToken((Token)token);
/*     */     
/* 369 */     context.setAttachedSecurityTokenReference((Token)attachedReference);
/* 370 */     context.setCreationTime(new Date(this.currentTime));
/* 371 */     context.setExpirationTime(new Date(this.currentTime + getSCTokenTimeout()));
/*     */     
/* 373 */     SecurityContextTokenInfo sctInfoForSession = session.getSecurityInfo();
/*     */     
/* 375 */     sctInfoForSession.setExternalId(token.getWsuId());
/* 376 */     sctInfoForSession.setExternalId(token.getInstance());
/* 377 */     sctInfoForSession.setCreationTime(new Date(this.currentTime));
/* 378 */     sctInfoForSession.setExpirationTime(new Date(this.currentTime + getSCTokenTimeout()));
/* 379 */     session.setSecurityInfo(sctInfoForSession);
/*     */     
/* 381 */     SecurityContextTokenInfoImpl securityContextTokenInfoImpl = new SecurityContextTokenInfoImpl();
/*     */     
/* 383 */     securityContextTokenInfoImpl.setIdentifier(token.getIdentifier().toString());
/* 384 */     securityContextTokenInfoImpl.setInstance(token.getInstance());
/* 385 */     securityContextTokenInfoImpl.setExternalId(token.getWsuId());
/* 386 */     securityContextTokenInfoImpl.addInstance(token.getInstance(), secret);
/* 387 */     context.setSecurityContextTokenInfo((SecurityContextTokenInfo)securityContextTokenInfoImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection issueMultiple(RequestSecurityToken request, IssuedTokenContext context) throws WSSecureConversationException {
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse renew(BaseSTSRequest request, IssuedTokenContext context, SecureConversationToken scToken) throws WSSecureConversationException {
/* 401 */     if (scToken.isMustNotSendRenew()) {
/* 402 */       throw new WSSecureConversationException("Service doesn't support Token Renewal, as MustNotSendRenew is enabled in the service policy");
/*     */     }
/*     */     
/* 405 */     URI tokenType = URI.create(this.wsscVer.getSCTTokenTypeURI());
/* 406 */     URI con = null;
/* 407 */     URI computeKeyAlgo = URI.create(this.wsTrustVer.getCKPSHA1algorithmURI());
/* 408 */     RenewTarget renewTgt = ((RequestSecurityToken)request).getRenewTarget();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     String conStr = ((RequestSecurityToken)request).getContext();
/* 416 */     if (conStr != null) {
/*     */       try {
/* 418 */         con = new URI(conStr);
/* 419 */       } catch (URISyntaxException ex) {
/* 420 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0008_URISYNTAX_EXCEPTION(((RequestSecurityToken)request).getContext()), ex);
/*     */         
/* 422 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0008_URISYNTAX_EXCEPTION(((RequestSecurityToken)request).getContext()), ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 429 */     RequestedProofToken proofToken = this.wsscEleFac.createRequestedProofToken();
/*     */ 
/*     */     
/* 432 */     byte[] clientEntr = null;
/* 433 */     Entropy clientEntropy = ((RequestSecurityToken)request).getEntropy();
/* 434 */     if (clientEntropy != null) {
/* 435 */       BinarySecret clientBS = clientEntropy.getBinarySecret();
/* 436 */       if (clientBS == null) {
/*     */         
/* 438 */         if (log.isLoggable(Level.FINE)) {
/* 439 */           log.log(Level.FINE, LogStringsMessages.WSSC_0009_CLIENT_ENTROPY_VALUE("null"));
/*     */         }
/*     */       } else {
/*     */         
/* 443 */         clientEntr = clientBS.getRawValue();
/* 444 */         if (log.isLoggable(Level.FINE)) {
/* 445 */           log.log(Level.FINE, LogStringsMessages.WSSC_0009_CLIENT_ENTROPY_VALUE(clientEntropy.toString()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 450 */     parseAssertion(scToken, clientEntropy);
/*     */     
/* 452 */     int keySize = (int)((RequestSecurityToken)request).getKeySize();
/* 453 */     if (keySize < 1 && this.symBinding != null) {
/* 454 */       AlgorithmSuite algoSuite = this.symBinding.getAlgorithmSuite();
/* 455 */       keySize = algoSuite.getMinSKLAlgorithm();
/*     */     } 
/* 457 */     if (keySize < 1) {
/* 458 */       keySize = 128;
/*     */     }
/* 460 */     if (log.isLoggable(Level.FINE)) {
/* 461 */       log.log(Level.FINE, LogStringsMessages.WSSC_0011_KEY_SIZE_VALUE(Integer.valueOf(keySize), Integer.valueOf(128)));
/*     */     }
/*     */ 
/*     */     
/* 465 */     byte[] secret = WSTrustUtil.generateRandomSecret(keySize / 8);
/* 466 */     String proofTokenType = (clientEntr == null || clientEntr.length == 0) ? this.wsTrustVer.getSymmetricKeyTypeURI() : this.wsTrustVer.getNonceBinarySecretTypeURI();
/*     */     
/* 468 */     Entropy serverEntropy = null;
/* 469 */     if (this.reqServerEntr) {
/* 470 */       BinarySecret serverBS = this.wsscEleFac.createBinarySecret(secret, proofTokenType);
/* 471 */       if (proofTokenType.equals(this.wsTrustVer.getNonceBinarySecretTypeURI())) {
/* 472 */         serverEntropy = this.wsscEleFac.createEntropy(serverBS);
/* 473 */         proofToken.setProofTokenType("ComputedKey");
/* 474 */         proofToken.setComputedKey(computeKeyAlgo);
/*     */ 
/*     */         
/*     */         try {
/* 478 */           secret = SecurityUtil.P_SHA1(clientEntr, secret, keySize / 8);
/* 479 */         } catch (Exception ex) {
/* 480 */           log.log(Level.SEVERE, LogStringsMessages.WSSC_0012_COMPUTE_SECKEY(), ex);
/*     */           
/* 482 */           throw new WSSecureConversationException(LogStringsMessages.WSSC_0012_COMPUTE_SECKEY(), ex);
/*     */         } 
/*     */       } else {
/*     */         
/* 486 */         proofToken.setProofTokenType("BinarySecret");
/* 487 */         proofToken.setBinarySecret(serverBS);
/*     */       } 
/* 489 */     } else if (clientEntropy != null) {
/* 490 */       secret = clientEntr;
/* 491 */       proofToken.setProofTokenType("BinarySecret");
/* 492 */       proofToken.setBinarySecret(clientEntropy.getBinarySecret());
/*     */     } 
/*     */     
/* 495 */     Lifetime lifetime = ((RequestSecurityToken)request).getLifetime();
/*     */     
/* 497 */     if (lifetime != null) {
/*     */       
/* 499 */       long timeout = WSTrustUtil.getLifeSpan(lifetime);
/* 500 */       if (timeout > 0L) {
/* 501 */         setSCTokenTimeout(timeout);
/*     */       }
/*     */     } 
/*     */     
/* 505 */     BaseSTSResponse rstr = createRenewResponse(renewTgt, serverEntropy, con, secret, proofToken, context, tokenType);
/* 506 */     return rstr;
/*     */   }
/*     */   
/*     */   private BaseSTSResponse createRenewResponse(RenewTarget renewTgt, Entropy serverEntropy, URI con, byte[] secret, RequestedProofToken proofToken, IssuedTokenContext context, URI tokenType) throws WSSecureConversationException {
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/* 511 */     SecurityTokenReference str = renewTgt.getSecurityTokenReference();
/* 512 */     SessionManager sm = (SessionManager)context.getOtherProperties().get("SessionManager");
/* 513 */     String id = null;
/* 514 */     Reference ref = str.getReference();
/* 515 */     if (ref.getType().equals("Reference")) {
/* 516 */       id = ((DirectReference)ref).getURIAttr().toString();
/*     */     }
/* 518 */     SecurityContextToken token = WSTrustUtil.createSecurityContextToken(this.wsscEleFac, id);
/* 519 */     RequestedSecurityToken rst = this.wsscEleFac.createRequestedSecurityToken((Token)token);
/*     */     
/* 521 */     SecurityTokenReference attachedReference = createSecurityTokenReferenceForRenew(token.getWsuId(), false, token.getInstance());
/* 522 */     RequestedAttachedReference rar = this.wsscEleFac.createRequestedAttachedReference(attachedReference);
/*     */ 
/*     */     
/* 525 */     IssuedTokenContext ctx = sm.getSecurityContext(id, false);
/*     */     
/* 527 */     if (ctx == null || ctx.getSecurityToken() == null) {
/* 528 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0015_UNKNOWN_CONTEXT(id));
/*     */       
/* 530 */       throw new WSSecureConversationException(LogStringsMessages.WSSC_0015_UNKNOWN_CONTEXT(id));
/*     */     } 
/*     */     
/* 533 */     Lifetime lifetime = createLifetime();
/*     */ 
/*     */     
/* 536 */     if (this.wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/*     */       try {
/* 538 */         RequestSecurityTokenResponse resp = this.wsscEleFac.createRSTRForRenew(tokenType, con, rst, rar, null, proofToken, serverEntropy, lifetime);
/* 539 */         List<RequestSecurityTokenResponse> list = new ArrayList<RequestSecurityTokenResponse>();
/* 540 */         list.add(resp);
/* 541 */         RequestSecurityTokenResponseCollection requestSecurityTokenResponseCollection = ((WSSCElementFactory13)this.wsscEleFac).createRSTRCollectionForIssue(list);
/* 542 */       } catch (WSTrustException ex) {
/* 543 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), (Throwable)ex);
/*     */         
/* 545 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), ex);
/*     */       } 
/*     */     } else {
/*     */       try {
/* 549 */         requestSecurityTokenResponse = this.wsscEleFac.createRSTRForRenew(tokenType, con, rst, rar, null, proofToken, serverEntropy, lifetime);
/* 550 */       } catch (WSTrustException ex) {
/* 551 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), (Throwable)ex);
/*     */         
/* 553 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), ex);
/*     */       } 
/*     */     } 
/* 556 */     if (log.isLoggable(Level.FINE)) {
/* 557 */       log.log(Level.FINE, LogStringsMessages.WSSC_0014_RSTR_RESPONSE(WSTrustUtil.elemToString((BaseSTSResponse)requestSecurityTokenResponse, this.wsTrustVer)));
/*     */     }
/*     */     
/* 560 */     Session session = sm.getSession(token.getIdentifier().toString());
/* 561 */     populateRenewedITC(session, secret, token, ctx, attachedReference);
/* 562 */     sm.addSecurityContext(token.getIdentifier().toString(), ctx);
/* 563 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse cancel(BaseSTSRequest request, IssuedTokenContext context) throws WSSecureConversationException {
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/* 571 */     CancelTarget cancelTgt = ((RequestSecurityToken)request).getCancelTarget();
/* 572 */     SecurityTokenReference str = cancelTgt.getSecurityTokenReference();
/* 573 */     String id = null;
/* 574 */     Reference ref = str.getReference();
/* 575 */     if (ref.getType().equals("Reference")) {
/* 576 */       id = ((DirectReference)ref).getURIAttr().toString();
/*     */     }
/*     */     
/* 579 */     IssuedTokenContext cxt = ((SessionManager)context.getOtherProperties().get("SessionManager")).getSecurityContext(id, true);
/* 580 */     if (cxt == null || cxt.getSecurityContextTokenInfo() == null) {
/* 581 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0015_UNKNOWN_CONTEXT(id));
/*     */       
/* 583 */       throw new WSSecureConversationException(LogStringsMessages.WSSC_0015_UNKNOWN_CONTEXT(id));
/*     */     } 
/*     */ 
/*     */     
/* 587 */     if (this.wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/* 588 */       RequestSecurityTokenResponse resp = this.wsscEleFac.createRSTRForCancel();
/* 589 */       List<RequestSecurityTokenResponse> list = new ArrayList<RequestSecurityTokenResponse>();
/* 590 */       list.add(resp);
/*     */       try {
/* 592 */         RequestSecurityTokenResponseCollection requestSecurityTokenResponseCollection = ((WSSCElementFactory13)this.wsscEleFac).createRSTRCollectionForIssue(list);
/* 593 */       } catch (WSTrustException ex) {
/* 594 */         throw new WSSecureConversationException(ex);
/*     */       } 
/*     */     } else {
/* 597 */       requestSecurityTokenResponse = this.wsscEleFac.createRSTRForCancel();
/*     */     } 
/* 599 */     if (log.isLoggable(Level.FINE)) {
/* 600 */       log.log(Level.FINE, LogStringsMessages.WSSC_0014_RSTR_RESPONSE(WSTrustUtil.elemToString((BaseSTSResponse)requestSecurityTokenResponse, this.wsTrustVer)));
/*     */     }
/*     */     
/* 603 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse validate(RequestSecurityToken request, IssuedTokenContext context) throws WSSecureConversationException {
/* 610 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleUnsolicited(RequestSecurityTokenResponse rstr, IssuedTokenContext context) throws WSSecureConversationException {
/* 621 */     RequestedSecurityToken rqSecToken = rstr.getRequestedSecurityToken();
/* 622 */     Token token = rqSecToken.getToken();
/* 623 */     RequestedProofToken rqProofToken = rstr.getRequestedProofToken();
/* 624 */     String proofTokenType = rqProofToken.getProofTokenType();
/* 625 */     if (proofTokenType.equals("BinarySecret")) {
/* 626 */       BinarySecret binarySecret = rqProofToken.getBinarySecret();
/* 627 */       if (binarySecret.getType().equals(this.wsTrustVer.getSymmetricKeyTypeURI())) {
/* 628 */         byte[] secret = binarySecret.getRawValue();
/* 629 */         context.setProofKey(secret);
/*     */       } 
/* 631 */     } else if (proofTokenType.equals("EncryptedKey")) {
/*     */     
/*     */     } 
/*     */     
/* 635 */     context.setSecurityToken(token);
/* 636 */     long curTime = System.currentTimeMillis();
/* 637 */     Date creationTime = new Date(curTime);
/* 638 */     Date expirationTime = new Date(curTime + getSCTokenTimeout());
/* 639 */     context.setCreationTime(creationTime);
/* 640 */     context.setExpirationTime(expirationTime);
/* 641 */     if (log.isLoggable(Level.FINER)) {
/* 642 */       log.log(Level.FINER, LogStringsMessages.WSSC_1003_SETTING_TIMES(creationTime.toString(), expirationTime.toString()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private SecurityTokenReference createSecurityTokenReference(String id, boolean unattached) {
/* 648 */     String uri = unattached ? id : ("#" + id);
/* 649 */     DirectReference directReference = this.wsscEleFac.createDirectReference(this.wsscVer.getSCTTokenTypeURI(), uri);
/*     */     
/* 651 */     return this.wsscEleFac.createSecurityTokenReference((Reference)directReference);
/*     */   }
/*     */   
/*     */   private SecurityTokenReference createSecurityTokenReferenceForRenew(String id, boolean unattached, String instanceId) {
/* 655 */     String uri = unattached ? id : ("#" + id);
/* 656 */     DirectReference directReference = this.wsscEleFac.createDirectReference(this.wsscVer.getSCTTokenTypeURI(), uri);
/*     */     
/* 658 */     return this.wsscEleFac.createSecurityTokenReference((Reference)directReference);
/*     */   }
/*     */   
/*     */   private Lifetime createLifetime() {
/* 662 */     Calendar cal = new GregorianCalendar();
/* 663 */     int offset = cal.get(15);
/* 664 */     if (cal.getTimeZone().inDaylightTime(cal.getTime())) {
/* 665 */       offset += cal.getTimeZone().getDSTSavings();
/*     */     }
/* 667 */     synchronized (calendarFormatter) {
/* 668 */       calendarFormatter.setTimeZone(cal.getTimeZone());
/*     */ 
/*     */       
/* 671 */       long beforeTime = cal.getTimeInMillis();
/* 672 */       this.currentTime = beforeTime - offset;
/* 673 */       cal.setTimeInMillis(this.currentTime);
/*     */       
/* 675 */       AttributedDateTime created = new AttributedDateTime();
/* 676 */       created.setValue(calendarFormatter.format(cal.getTime()));
/*     */       
/* 678 */       AttributedDateTime expires = new AttributedDateTime();
/* 679 */       cal.setTimeInMillis(this.currentTime + getSCTokenTimeout());
/* 680 */       expires.setValue(calendarFormatter.format(cal.getTime()));
/*     */       
/* 682 */       Lifetime lifetime = this.wsscEleFac.createLifetime(created, expires);
/*     */       
/* 684 */       return lifetime;
/*     */     } 
/*     */   }
/*     */   
/*     */   private SecurityPolicyVersion getSPVersion(PolicyAssertion pa) {
/* 689 */     String nsUri = pa.getName().getNamespaceURI();
/*     */     
/* 691 */     SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */     
/* 693 */     if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(nsUri)) {
/* 694 */       spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */     }
/* 696 */     return spVersion;
/*     */   }
/*     */   
/*     */   public void setWSSCServerConfig(Iterator<PolicyAssertion> wsscConfigIterator) {
/* 700 */     if (wsscConfigIterator != null) {
/* 701 */       while (wsscConfigIterator.hasNext()) {
/* 702 */         PolicyAssertion assertion = wsscConfigIterator.next();
/* 703 */         if (!"SCConfiguration".equals(assertion.getName().getLocalPart())) {
/*     */           continue;
/*     */         }
/* 706 */         Iterator<PolicyAssertion> wsscConfig = assertion.getNestedAssertionsIterator();
/* 707 */         while (wsscConfig.hasNext()) {
/* 708 */           PolicyAssertion serviceSCPolicy = wsscConfig.next();
/* 709 */           if ("LifeTime".equals(serviceSCPolicy.getName().getLocalPart())) {
/* 710 */             setSCTokenTimeout(Integer.parseInt(serviceSCPolicy.getValue()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSCTokenTimeout(long scTokenTimeout) {
/* 719 */     this.TIMEOUT = scTokenTimeout;
/*     */   }
/*     */   private long getSCTokenTimeout() {
/* 722 */     return this.TIMEOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSubject(IssuedTokenContext context) throws WSSecureConversationException {
/* 728 */     Subject subj = context.getRequestorSubject();
/*     */     
/*     */     try {
/* 731 */       if (subj != null) {
/* 732 */         Set<Object> set = subj.getPublicCredentials();
/* 733 */         XMLStreamReader samlReader = null;
/* 734 */         Element samlAssertion = null;
/* 735 */         for (Object obj : set) {
/* 736 */           if (obj instanceof XMLStreamReader) {
/* 737 */             samlReader = (XMLStreamReader)obj;
/*     */             
/* 739 */             samlAssertion = SAMLUtil.createSAMLAssertion(samlReader);
/*     */           } 
/*     */         } 
/* 742 */         if (samlReader != null && samlAssertion != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 747 */           set.remove(samlReader);
/* 748 */           set.add(samlAssertion);
/*     */         } 
/*     */       } 
/* 751 */     } catch (XWSSecurityException ex) {
/* 752 */       throw new WSSecureConversationException(ex.getMessage(), ex);
/* 753 */     } catch (XMLStreamException ex) {
/* 754 */       throw new WSSecureConversationException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */