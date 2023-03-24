/*     */ package com.sun.xml.ws.security.secconv;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.secconv.client.SCTokenConfiguration;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secconv.impl.SecurityContextTokenInfoImpl;
/*     */ import com.sun.xml.ws.security.secconv.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedTokenCancelled;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.net.URI;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
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
/*     */ public class WSSCClientContract
/*     */ {
/*  76 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.secconv", "com.sun.xml.ws.security.secconv.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_KEY_SIZE = 256;
/*     */ 
/*     */   
/*  82 */   private WSSCVersion wsscVer = WSSCVersion.WSSC_10;
/*  83 */   private WSTrustVersion wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleRSTR(RequestSecurityToken rst, RequestSecurityTokenResponse rstr, IssuedTokenContext context) throws WSSecureConversationException {
/*  92 */     if (!context.getSecurityPolicy().isEmpty()) {
/*  93 */       SCTokenConfiguration sctConfig = context.getSecurityPolicy().get(0);
/*  94 */       this.wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/*     */     } 
/*  96 */     if (this.wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/*  97 */       this.wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     }
/*  99 */     if (rst.getRequestType().toString().equals(this.wsTrustVer.getIssueRequestTypeURI())) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       RequestedSecurityToken securityToken = rstr.getRequestedSecurityToken();
/*     */ 
/*     */       
/* 107 */       RequestedAttachedReference attachedRef = rstr.getRequestedAttachedReference();
/* 108 */       RequestedUnattachedReference unattachedRef = rstr.getRequestedUnattachedReference();
/*     */ 
/*     */       
/* 111 */       RequestedProofToken proofToken = rstr.getRequestedProofToken();
/*     */ 
/*     */       
/* 114 */       byte[] key = getKey(rstr, proofToken, rst);
/*     */       
/* 116 */       if (key != null) {
/* 117 */         context.setProofKey(key);
/*     */       }
/*     */ 
/*     */       
/* 121 */       setLifetime(rstr, context);
/*     */       
/* 123 */       if (securityToken == null && proofToken == null) {
/* 124 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0002_NULL_TOKEN());
/*     */         
/* 126 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0002_NULL_TOKEN());
/*     */       } 
/*     */       
/* 129 */       if (securityToken != null) {
/* 130 */         context.setSecurityToken(securityToken.getToken());
/*     */       }
/*     */       
/* 133 */       if (attachedRef != null) {
/* 134 */         context.setAttachedSecurityTokenReference((Token)attachedRef.getSTR());
/*     */       }
/*     */       
/* 137 */       if (unattachedRef != null)
/* 138 */         context.setUnAttachedSecurityTokenReference((Token)unattachedRef.getSTR()); 
/*     */     } 
/* 140 */     if (rst.getRequestType().toString().equals(this.wsTrustVer.getRenewRequestTypeURI())) {
/* 141 */       RequestedSecurityToken securityToken = rstr.getRequestedSecurityToken();
/*     */       
/* 143 */       RequestedProofToken proofToken = rstr.getRequestedProofToken();
/*     */ 
/*     */       
/* 146 */       byte[] key = getKey(rstr, proofToken, rst);
/*     */ 
/*     */       
/* 149 */       setLifetime(rstr, context);
/* 150 */       if (securityToken != null) {
/* 151 */         context.setSecurityToken(securityToken.getToken());
/*     */       }
/* 153 */       SecurityContextTokenInfo sctInfo = null;
/* 154 */       if (context.getSecurityContextTokenInfo() == null) {
/* 155 */         SecurityContextTokenInfoImpl securityContextTokenInfoImpl = new SecurityContextTokenInfoImpl();
/*     */       } else {
/* 157 */         sctInfo = context.getSecurityContextTokenInfo();
/*     */       } 
/* 159 */       sctInfo.setIdentifier(((SecurityContextToken)context.getSecurityToken()).getIdentifier().toString());
/* 160 */       sctInfo.setInstance(((SecurityContextToken)context.getSecurityToken()).getInstance());
/* 161 */       sctInfo.setExternalId(((SecurityContextToken)context.getSecurityToken()).getWsuId());
/* 162 */       if (key != null) {
/* 163 */         sctInfo.addInstance(((SecurityContextToken)context.getSecurityToken()).getInstance(), key);
/*     */       }
/* 165 */       context.setSecurityContextTokenInfo(sctInfo);
/* 166 */     } else if (rst.getRequestType().toString().equals(this.wsTrustVer.getCancelRequestTypeURI())) {
/*     */ 
/*     */ 
/*     */       
/* 170 */       RequestedTokenCancelled cancelled = rstr.getRequestedTokenCancelled();
/* 171 */       if (cancelled != null)
/*     */       {
/* 173 */         context.setProofKey(null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleRSTRC(RequestSecurityToken rst, RequestSecurityTokenResponseCollection rstrc, IssuedTokenContext context) throws WSSecureConversationException {
/* 185 */     List<RequestSecurityTokenResponse> rstrList = rstrc.getRequestSecurityTokenResponses();
/* 186 */     Iterator<RequestSecurityTokenResponse> rstrIterator = rstrList.iterator();
/*     */     
/* 188 */     while (rstrIterator.hasNext()) {
/* 189 */       RequestSecurityTokenResponse rstr = rstrIterator.next();
/* 190 */       handleRSTR(rst, rstr, context);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] getKey(RequestSecurityTokenResponse rstr, RequestedProofToken proofToken, RequestSecurityToken rst) throws UnsupportedOperationException, WSSecureConversationException, WSSecureConversationException, UnsupportedOperationException {
/* 195 */     byte[] key = null;
/* 196 */     if (proofToken != null) {
/* 197 */       String proofTokenType = proofToken.getProofTokenType();
/* 198 */       if ("ComputedKey".equals(proofTokenType))
/* 199 */       { key = computeKey(rstr, proofToken, rst); }
/* 200 */       else { if ("SecurityTokenReference".equals(proofTokenType))
/*     */         {
/* 202 */           throw new UnsupportedOperationException("To Do"); } 
/* 203 */         if ("EncryptedKey".equals(proofTokenType))
/*     */         {
/* 205 */           throw new UnsupportedOperationException("To Do"); } 
/* 206 */         if ("BinarySecret".equals(proofTokenType)) {
/* 207 */           BinarySecret binarySecret = proofToken.getBinarySecret();
/* 208 */           key = binarySecret.getRawValue();
/*     */         } else {
/* 210 */           log.log(Level.SEVERE, LogStringsMessages.WSSC_0003_INVALID_PROOFTOKEN(proofTokenType));
/*     */           
/* 212 */           throw new WSSecureConversationException(LogStringsMessages.WSSC_0003_INVALID_PROOFTOKEN(proofTokenType));
/*     */         }  }
/*     */     
/* 215 */     }  return key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLifetime(RequestSecurityTokenResponse rstr, IssuedTokenContext context) {
/* 221 */     Lifetime lifetime = rstr.getLifetime();
/* 222 */     AttributedDateTime created = lifetime.getCreated();
/* 223 */     AttributedDateTime expires = lifetime.getExpires();
/*     */ 
/*     */     
/* 226 */     if (created != null) {
/* 227 */       context.setCreationTime(WSTrustUtil.parseAttributedDateTime(created));
/*     */     } else {
/*     */       
/* 230 */       context.setCreationTime(new Date());
/*     */     } 
/* 232 */     if (expires != null) {
/* 233 */       context.setExpirationTime(WSTrustUtil.parseAttributedDateTime(expires));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] computeKey(RequestSecurityTokenResponse rstr, RequestedProofToken proofToken, RequestSecurityToken rst) throws WSSecureConversationException, UnsupportedOperationException {
/* 240 */     URI computedKey = proofToken.getComputedKey();
/* 241 */     Entropy clientEntropy = rst.getEntropy();
/* 242 */     Entropy serverEntropy = rstr.getEntropy();
/* 243 */     BinarySecret clientBS = clientEntropy.getBinarySecret();
/* 244 */     BinarySecret serverBS = serverEntropy.getBinarySecret();
/* 245 */     byte[] clientEntr = null;
/* 246 */     byte[] serverEntr = null;
/* 247 */     if (clientBS != null) {
/* 248 */       clientEntr = clientBS.getRawValue();
/*     */     }
/* 250 */     if (serverBS != null) {
/* 251 */       serverEntr = serverBS.getRawValue();
/*     */     }
/* 253 */     byte[] key = null;
/* 254 */     int keySize = (int)rstr.getKeySize();
/* 255 */     if (keySize == 0) {
/* 256 */       keySize = (int)rst.getKeySize();
/*     */     }
/* 258 */     if (keySize == 0) {
/* 259 */       keySize = 256;
/*     */     }
/* 261 */     if (log.isLoggable(Level.FINE)) {
/* 262 */       log.log(Level.FINE, LogStringsMessages.WSSC_0005_COMPUTED_KEYSIZE(Integer.valueOf(keySize), Integer.valueOf(256)));
/*     */     }
/*     */     
/* 265 */     if (computedKey.toString().equals(this.wsTrustVer.getCKPSHA1algorithmURI())) {
/*     */       try {
/* 267 */         key = SecurityUtil.P_SHA1(clientEntr, serverEntr, keySize / 8);
/* 268 */       } catch (Exception ex) {
/* 269 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0006_UNABLETOEXTRACT_KEY(), ex);
/*     */         
/* 271 */         throw new WSSecureConversationException(LogStringsMessages.WSSC_0006_UNABLETOEXTRACT_KEY(), ex);
/*     */       } 
/*     */     } else {
/* 274 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0026_UNSUPPORTED_COMPUTED_KEY(computedKey));
/*     */       
/* 276 */       throw new WSSecureConversationException(LogStringsMessages.WSSC_0026_UNSUPPORTED_COMPUTED_KEY_E(computedKey));
/*     */     } 
/* 278 */     return key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse handleRSTRForNegotiatedExchange(RequestSecurityToken rst, RequestSecurityTokenResponse rstr, IssuedTokenContext context) throws WSSecureConversationException {
/* 287 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForClientInitiatedIssuedTokenContext(AppliesTo scopes, IssuedTokenContext context) throws WSSecureConversationException {
/* 296 */     WSSCElementFactory eleFac = WSSCElementFactory.newInstance();
/*     */     
/* 298 */     byte[] secret = WSTrustUtil.generateRandomSecret(256);
/* 299 */     BinarySecret binarySecret = eleFac.createBinarySecret(secret, this.wsTrustVer.getSymmetricKeyTypeURI());
/*     */     
/* 301 */     RequestedProofToken proofToken = eleFac.createRequestedProofToken();
/* 302 */     proofToken.setProofTokenType("BinarySecret");
/* 303 */     proofToken.setBinarySecret(binarySecret);
/*     */     
/* 305 */     SecurityContextToken token = WSTrustUtil.createSecurityContextToken(eleFac);
/* 306 */     RequestedSecurityToken rst = eleFac.createRequestedSecurityToken((Token)token);
/*     */     
/* 308 */     RequestSecurityTokenResponse rstr = eleFac.createRSTR();
/* 309 */     rstr.setAppliesTo(scopes);
/* 310 */     rstr.setRequestedSecurityToken(rst);
/* 311 */     rstr.setRequestedProofToken(proofToken);
/*     */     
/* 313 */     context.setSecurityToken((Token)token);
/* 314 */     context.setProofKey(secret);
/* 315 */     if (log.isLoggable(Level.FINE)) {
/* 316 */       log.log(Level.FINE, LogStringsMessages.WSSC_0007_CREATED_RSTR(rstr.toString()));
/*     */     }
/*     */     
/* 319 */     return rstr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsChallenge(RequestSecurityTokenResponse rstr) {
/* 328 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getComputedKeyAlgorithmFromProofToken(RequestSecurityTokenResponse rstr) {
/* 335 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCClientContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */