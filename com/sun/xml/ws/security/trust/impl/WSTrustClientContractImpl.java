/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Status;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.trust.WSTrustClientContract;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.net.URI;
/*     */ import java.util.Date;
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
/*     */ public class WSTrustClientContractImpl
/*     */   implements WSTrustClientContract
/*     */ {
/*  91 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleRSTR(BaseSTSRequest request, BaseSTSResponse response, IssuedTokenContext context) throws WSTrustException {
/* 111 */     WSTrustVersion wstVer = WSTrustVersion.getInstance(((STSIssuedTokenConfiguration)context.getSecurityPolicy().get(0)).getProtocol());
/* 112 */     RequestSecurityToken rst = (RequestSecurityToken)request;
/* 113 */     RequestSecurityTokenResponse rstr = null;
/* 114 */     if (response instanceof RequestSecurityTokenResponse) {
/* 115 */       rstr = (RequestSecurityTokenResponse)response;
/* 116 */     } else if (response instanceof RequestSecurityTokenResponseCollection) {
/* 117 */       rstr = ((RequestSecurityTokenResponseCollection)response).getRequestSecurityTokenResponses().get(0);
/*     */     } 
/* 119 */     if (rst.getRequestType().toString().equals(wstVer.getIssueRequestTypeURI())) {
/*     */       
/* 121 */       String appliesTo = null;
/* 122 */       AppliesTo requestAppliesTo = rst.getAppliesTo();
/* 123 */       if (requestAppliesTo != null) {
/* 124 */         appliesTo = WSTrustUtil.getAppliesToURI(requestAppliesTo);
/*     */       }
/*     */ 
/*     */       
/* 128 */       RequestedSecurityToken securityToken = rstr.getRequestedSecurityToken();
/*     */ 
/*     */       
/* 131 */       RequestedAttachedReference attachedRef = rstr.getRequestedAttachedReference();
/* 132 */       RequestedUnattachedReference unattachedRef = rstr.getRequestedUnattachedReference();
/*     */ 
/*     */       
/* 135 */       RequestedProofToken proofToken = rstr.getRequestedProofToken();
/*     */ 
/*     */       
/* 138 */       byte[] key = getKey(wstVer, rstr, proofToken, rst, appliesTo);
/*     */       
/* 140 */       if (key != null) {
/* 141 */         context.setProofKey(key);
/*     */       }
/*     */ 
/*     */       
/* 145 */       setLifetime(rstr, context);
/*     */ 
/*     */ 
/*     */       
/* 149 */       if (securityToken == null && proofToken == null) {
/* 150 */         log.log(Level.SEVERE, LogStringsMessages.WST_0018_TOKENS_NULL(appliesTo));
/*     */         
/* 152 */         throw new WSTrustException(LogStringsMessages.WST_0018_TOKENS_NULL(appliesTo));
/*     */       } 
/*     */ 
/*     */       
/* 156 */       if (securityToken != null) {
/* 157 */         context.setSecurityToken(securityToken.getToken());
/*     */       }
/*     */       
/* 160 */       if (attachedRef != null) {
/* 161 */         context.setAttachedSecurityTokenReference((Token)attachedRef.getSTR());
/*     */       }
/*     */       
/* 164 */       if (unattachedRef != null) {
/* 165 */         context.setUnAttachedSecurityTokenReference((Token)unattachedRef.getSTR());
/*     */       }
/* 167 */     } else if (rst.getRequestType().toString().equals(wstVer.getValidateRequestTypeURI())) {
/* 168 */       Status status = rstr.getStatus();
/* 169 */       context.getOtherProperties().put("status", status);
/* 170 */       RequestedSecurityToken securityToken = rstr.getRequestedSecurityToken();
/* 171 */       if (securityToken != null) {
/* 172 */         context.setSecurityToken(securityToken.getToken());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse handleRSTRForNegotiatedExchange(BaseSTSRequest request, BaseSTSResponse response, IssuedTokenContext context) throws WSTrustException {
/* 183 */     throw new UnsupportedOperationException("Unsupported operation: handleRSTRForNegotiatedExchange");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse createRSTRForClientInitiatedIssuedTokenContext(AppliesTo scopes, IssuedTokenContext context) throws WSTrustException {
/* 192 */     throw new UnsupportedOperationException("Unsupported operation: createRSTRForClientInitiatedIssuedTokenContext");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsChallenge(RequestSecurityTokenResponse rstr) {
/* 201 */     throw new UnsupportedOperationException("Unsupported operation: containsChallenge");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getComputedKeyAlgorithmFromProofToken(RequestSecurityTokenResponse rstr) {
/* 208 */     throw new UnsupportedOperationException("Unsupported operation: getComputedKeyAlgorithmFromProofToken");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLifetime(RequestSecurityTokenResponse rstr, IssuedTokenContext context) {
/* 214 */     Lifetime lifetime = rstr.getLifetime();
/* 215 */     AttributedDateTime created = lifetime.getCreated();
/* 216 */     AttributedDateTime expires = lifetime.getExpires();
/*     */ 
/*     */     
/* 219 */     if (created != null) {
/* 220 */       context.setCreationTime(WSTrustUtil.parseAttributedDateTime(created));
/*     */     } else {
/* 222 */       context.setCreationTime(new Date());
/*     */     } 
/* 224 */     if (expires != null) {
/* 225 */       context.setExpirationTime(WSTrustUtil.parseAttributedDateTime(expires));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getKey(WSTrustVersion wstVer, RequestSecurityTokenResponse rstr, RequestedProofToken proofToken, RequestSecurityToken rst, String appliesTo) throws WSTrustException {
/* 231 */     byte[] key = null;
/* 232 */     if (proofToken != null)
/* 233 */     { String proofTokenType = proofToken.getProofTokenType();
/* 234 */       if ("ComputedKey".equals(proofTokenType))
/* 235 */       { key = computeKey(wstVer, rstr, proofToken, rst); }
/* 236 */       else { if ("SecurityTokenReference".equals(proofTokenType)) {
/*     */           
/* 238 */           log.log(Level.SEVERE, LogStringsMessages.WST_0001_UNSUPPORTED_PROOF_TOKEN_TYPE(proofTokenType, appliesTo));
/*     */           
/* 240 */           throw new WSTrustException(LogStringsMessages.WST_0001_UNSUPPORTED_PROOF_TOKEN_TYPE(proofTokenType, appliesTo));
/* 241 */         }  if ("EncryptedKey".equals(proofTokenType)) {
/*     */           
/* 243 */           log.log(Level.SEVERE, LogStringsMessages.WST_0001_UNSUPPORTED_PROOF_TOKEN_TYPE(proofTokenType, appliesTo));
/*     */           
/* 245 */           throw new WSTrustException(LogStringsMessages.WST_0001_UNSUPPORTED_PROOF_TOKEN_TYPE(proofTokenType, appliesTo));
/* 246 */         }  if ("BinarySecret".equals(proofTokenType)) {
/* 247 */           BinarySecret binarySecret = proofToken.getBinarySecret();
/* 248 */           key = binarySecret.getRawValue();
/*     */         } else {
/* 250 */           log.log(Level.SEVERE, LogStringsMessages.WST_0019_INVALID_PROOF_TOKEN_TYPE(proofTokenType, appliesTo));
/*     */           
/* 252 */           throw new WSTrustException(LogStringsMessages.WST_0019_INVALID_PROOF_TOKEN_TYPE(proofTokenType, appliesTo));
/*     */         }  }
/*     */        }
/* 255 */     else { Entropy clientEntropy = rst.getEntropy();
/* 256 */       if (clientEntropy != null) {
/* 257 */         BinarySecret bs = clientEntropy.getBinarySecret();
/* 258 */         if (bs != null) {
/* 259 */           key = bs.getRawValue();
/*     */         }
/*     */       }  }
/*     */     
/* 263 */     return key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] computeKey(WSTrustVersion wstVer, RequestSecurityTokenResponse rstr, RequestedProofToken proofToken, RequestSecurityToken rst) throws WSTrustException, UnsupportedOperationException {
/* 269 */     URI computedKey = proofToken.getComputedKey();
/* 270 */     Entropy clientEntropy = rst.getEntropy();
/* 271 */     Entropy serverEntropy = rstr.getEntropy();
/* 272 */     BinarySecret clientBinarySecret = clientEntropy.getBinarySecret();
/* 273 */     BinarySecret serverBinarySecret = serverEntropy.getBinarySecret();
/* 274 */     byte[] clientEntropyBytes = null;
/* 275 */     byte[] serverEntropyBytes = null;
/* 276 */     if (clientBinarySecret != null) {
/* 277 */       clientEntropyBytes = clientBinarySecret.getRawValue();
/*     */     }
/* 279 */     if (serverBinarySecret != null) {
/* 280 */       serverEntropyBytes = serverBinarySecret.getRawValue();
/*     */     }
/*     */     
/* 283 */     int keySize = (int)rstr.getKeySize() / 8;
/* 284 */     if (keySize == 0) {
/* 285 */       keySize = (int)rst.getKeySize() / 8;
/* 286 */       if (keySize == 0 && wstVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-trust/200512")) {
/* 287 */         SecondaryParameters secPara = rst.getSecondaryParameters();
/* 288 */         if (secPara != null) {
/* 289 */           keySize = (int)secPara.getKeySize() / 8;
/*     */         }
/*     */       } 
/*     */     } 
/* 293 */     byte[] key = null;
/* 294 */     if (computedKey.toString().equals(wstVer.getCKPSHA1algorithmURI())) {
/*     */       try {
/* 296 */         key = SecurityUtil.P_SHA1(clientEntropyBytes, serverEntropyBytes, keySize);
/* 297 */       } catch (Exception ex) {
/* 298 */         log.log(Level.SEVERE, LogStringsMessages.WST_0037_ERROR_COMPUTING_KEY(), ex);
/*     */         
/* 300 */         throw new WSTrustException(LogStringsMessages.WST_0037_ERROR_COMPUTING_KEY(), ex);
/*     */       } 
/*     */     } else {
/* 303 */       log.log(Level.SEVERE, LogStringsMessages.WST_0026_INVALID_CK_ALGORITHM(computedKey));
/*     */       
/* 305 */       throw new WSTrustException(LogStringsMessages.WST_0026_INVALID_CK_ALGORITHM_E(computedKey));
/*     */     } 
/* 307 */     return key;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\WSTrustClientContractImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */