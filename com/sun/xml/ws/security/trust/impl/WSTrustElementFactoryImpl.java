/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.Status;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.EncryptedKey;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.ActAs;
/*     */ import com.sun.xml.ws.security.trust.elements.AllowPostdating;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.IssuedTokens;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.RenewTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Renewing;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedTokenCancelled;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.elements.ValidateTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.str.DirectReference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenResponseCollectionType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenResponseType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.BinarySecretImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.CancelTargetImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.ClaimsImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.EntropyImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.IssuedTokensImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.LifetimeImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.OnBehalfOfImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RenewTargetImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestSecurityTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestSecurityTokenResponseCollectionImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestSecurityTokenResponseImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestedAttachedReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestedProofTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestedSecurityTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestedTokenCancelledImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.RequestedUnattachedReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.StatusImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.UseKeyImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.DirectReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.KeyIdentifierImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.TrustNamespacePrefixMapper;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBSource;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class WSTrustElementFactoryImpl
/*     */   extends WSTrustElementFactory
/*     */ {
/* 145 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String RST = "RST";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String RSTRCollection = "RSTRCollection";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTForIssue(URI tokenType, URI requestType, URI context, AppliesTo scopes, Claims claims, Entropy entropy, Lifetime lifetime) throws WSTrustException {
/* 166 */     if (tokenType == null && scopes == null) {
/* 167 */       log.log(Level.WARNING, LogStringsMessages.WST_1003_TOKENTYPE_APPLIESTO_NULL());
/*     */     }
/*     */     
/* 170 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(tokenType, requestType, context, scopes, claims, entropy, lifetime, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForIssue(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attachedReference, RequestedUnattachedReference unattachedRef, RequestedProofToken proofToken, Entropy entropy, Lifetime lifetime) throws WSTrustException {
/* 178 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(tokenType, context, token, scopes, attachedReference, unattachedRef, proofToken, entropy, lifetime, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionForIssue(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attached, RequestedUnattachedReference unattached, RequestedProofToken proofToken, Entropy entropy, Lifetime lifetime) throws WSTrustException {
/* 188 */     return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(tokenType, context, token, scopes, attached, unattached, proofToken, entropy, lifetime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForRenew(URI tokenType, URI context, RequestedSecurityToken token, RequestedAttachedReference attachedReference, RequestedUnattachedReference unattachedRef, RequestedProofToken proofToken, Entropy entropy, Lifetime lifetime) throws WSTrustException {
/* 198 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(tokenType, context, token, null, attachedReference, unattachedRef, proofToken, entropy, lifetime, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuedTokens createIssuedTokens(RequestSecurityTokenResponseCollection issuedTokens) {
/* 207 */     return (IssuedTokens)new IssuedTokensImpl(issuedTokens);
/*     */   }
/*     */   
/*     */   public OnBehalfOf createOnBehalfOf(Token oboToken) {
/* 211 */     return (OnBehalfOf)new OnBehalfOfImpl(oboToken);
/*     */   }
/*     */   
/*     */   public ValidateTarget createValidateTarget(Token token) {
/* 215 */     throw new UnsupportedOperationException("Unsupported operation: ValidateTarget");
/*     */   }
/*     */   
/*     */   public ActAs createActAs(Token token) {
/* 219 */     throw new UnsupportedOperationException("Unsupported operation: createActAs");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entropy createEntropy(BinarySecret secret) {
/* 226 */     return (Entropy)new EntropyImpl(secret);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entropy createEntropy(EncryptedKey key) {
/* 233 */     return (Entropy)new EntropyImpl(key);
/*     */   }
/*     */   
/*     */   public BinarySecret createBinarySecret(byte[] rawValue, String type) {
/* 237 */     return (BinarySecret)new BinarySecretImpl(rawValue, type);
/*     */   }
/*     */   
/*     */   public BinarySecret createBinarySecret(Element elem) throws WSTrustException {
/* 241 */     return (BinarySecret)new BinarySecretImpl(BinarySecretImpl.fromElement(elem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Lifetime createLifetime(AttributedDateTime created, AttributedDateTime expires) {
/* 248 */     return (Lifetime)new LifetimeImpl(created, expires);
/*     */   }
/*     */   
/*     */   public Claims createClaims(Element elem) throws WSTrustException {
/* 252 */     return (Claims)new ClaimsImpl(ClaimsImpl.fromElement(elem));
/*     */   }
/*     */   
/*     */   public Claims createClaims(Claims claims) throws WSTrustException {
/* 256 */     ClaimsImpl newClaims = new ClaimsImpl();
/* 257 */     if (claims != null) {
/* 258 */       newClaims.setDialect(claims.getDialect());
/* 259 */       newClaims.getAny().addAll(claims.getAny());
/* 260 */       newClaims.getOtherAttributes().putAll(claims.getOtherAttributes());
/*     */     } 
/*     */     
/* 263 */     return (Claims)newClaims;
/*     */   }
/*     */   
/*     */   public Claims createClaims() throws WSTrustException {
/* 267 */     return (Claims)new ClaimsImpl();
/*     */   }
/*     */   
/*     */   public Status createStatus(String code, String reason) {
/* 271 */     return (Status)new StatusImpl(code, reason);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityToken createRequestedSecurityToken(Token token) {
/* 278 */     return (RequestedSecurityToken)new RequestedSecurityTokenImpl(token);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityToken createRequestedSecurityToken() {
/* 285 */     return (RequestedSecurityToken)new RequestedSecurityTokenImpl();
/*     */   }
/*     */   
/*     */   public DirectReference createDirectReference(String valueType, String uri) {
/* 289 */     return (DirectReference)new DirectReferenceImpl(valueType, uri);
/*     */   }
/*     */   
/*     */   public DirectReference createDirectReference(String valueType, String uri, String instanceId) {
/* 293 */     return (DirectReference)new DirectReferenceImpl(valueType, uri, instanceId);
/*     */   }
/*     */   
/*     */   public KeyIdentifier createKeyIdentifier(String valueType, String encodingType) {
/* 297 */     return (KeyIdentifier)new KeyIdentifierImpl(valueType, encodingType);
/*     */   }
/*     */   
/*     */   public SecurityTokenReference createSecurityTokenReference(Reference ref) {
/* 301 */     return (SecurityTokenReference)new SecurityTokenReferenceImpl(ref);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedAttachedReference createRequestedAttachedReference(SecurityTokenReference str) {
/* 308 */     return (RequestedAttachedReference)new RequestedAttachedReferenceImpl(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedUnattachedReference createRequestedUnattachedReference(SecurityTokenReference str) {
/* 315 */     return (RequestedUnattachedReference)new RequestedUnattachedReferenceImpl(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedProofToken createRequestedProofToken() {
/* 322 */     return (RequestedProofToken)new RequestedProofTokenImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecondaryParameters createSecondaryParameters() {
/* 329 */     throw new UnsupportedOperationException("Unsupported operation: createSecondaryParameters");
/*     */   }
/*     */   
/*     */   public UseKey createUseKey(Token token, String sig) {
/* 333 */     UseKeyImpl useKeyImpl = new UseKeyImpl(token);
/* 334 */     if (sig != null) {
/* 335 */       useKeyImpl.setSignatureID(URI.create(sig));
/*     */     }
/*     */     
/* 338 */     return (UseKey)useKeyImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTForRenew(URI tokenType, URI requestType, URI context, RenewTarget target, AllowPostdating apd, Renewing renewingInfo) {
/* 345 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(tokenType, requestType, context, target, apd, renewingInfo);
/*     */   }
/*     */   
/*     */   public RenewTarget createRenewTarget(SecurityTokenReference str) {
/* 349 */     return (RenewTarget)new RenewTargetImpl(str);
/*     */   }
/*     */   
/*     */   public CancelTarget createCancelTarget(SecurityTokenReference str) {
/* 353 */     return (CancelTarget)new CancelTargetImpl(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTForCancel(URI requestType, CancelTarget target) {
/* 360 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(null, requestType, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForCancel() {
/* 367 */     RequestSecurityTokenResponseImpl requestSecurityTokenResponseImpl = new RequestSecurityTokenResponseImpl();
/* 368 */     requestSecurityTokenResponseImpl.setRequestedTokenCancelled((RequestedTokenCancelled)new RequestedTokenCancelledImpl());
/* 369 */     if (log.isLoggable(Level.FINE)) {
/* 370 */       log.log(Level.FINE, LogStringsMessages.WST_1008_CREATED_RSTR_CANCEL(requestSecurityTokenResponseImpl.toString()));
/*     */     }
/*     */     
/* 373 */     return (RequestSecurityTokenResponse)requestSecurityTokenResponseImpl;
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
/*     */   public RequestSecurityToken createRSTForValidate(URI tokenType, URI requestType) {
/* 385 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(tokenType, requestType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForValidate(URI tokenType, RequestedSecurityToken token, Status status) {
/* 392 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(tokenType, null, token, null, null, null, null, null, null, status);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRST() {
/* 399 */     return (RequestSecurityToken)new RequestSecurityTokenImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTR() {
/* 406 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl();
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRC(List<RequestSecurityTokenResponse> rstrs) {
/* 410 */     RequestSecurityTokenResponseCollectionImpl requestSecurityTokenResponseCollectionImpl = new RequestSecurityTokenResponseCollectionImpl();
/* 411 */     requestSecurityTokenResponseCollectionImpl.getRequestSecurityTokenResponses().addAll(rstrs);
/*     */     
/* 413 */     return (RequestSecurityTokenResponseCollection)requestSecurityTokenResponseCollectionImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTFrom(Source src) {
/*     */     try {
/* 422 */       Unmarshaller unmarshaller = getContext().createUnmarshaller();
/* 423 */       JAXBElement<RequestSecurityTokenType> rstType = unmarshaller.unmarshal(src, RequestSecurityTokenType.class);
/* 424 */       RequestSecurityTokenType type = rstType.getValue();
/* 425 */       return (RequestSecurityToken)new RequestSecurityTokenImpl(type);
/* 426 */     } catch (Exception ex) {
/* 427 */       log.log(Level.SEVERE, LogStringsMessages.WST_0006_FAIL_RST_SOURCE(src.toString()), ex);
/*     */       
/* 429 */       throw new RuntimeException(LogStringsMessages.WST_0006_FAIL_RST_SOURCE(src.toString()), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTFrom(Element elem) {
/*     */     try {
/* 438 */       Unmarshaller unmarshaller = getContext().createUnmarshaller();
/* 439 */       JAXBElement<RequestSecurityTokenType> rstType = unmarshaller.unmarshal(elem, RequestSecurityTokenType.class);
/* 440 */       RequestSecurityTokenType type = rstType.getValue();
/* 441 */       return (RequestSecurityToken)new RequestSecurityTokenImpl(type);
/* 442 */     } catch (Exception ex) {
/* 443 */       log.log(Level.SEVERE, LogStringsMessages.WST_0007_FAIL_RST_ELEM(elem.toString()), ex);
/*     */       
/* 445 */       throw new RuntimeException(LogStringsMessages.WST_0007_FAIL_RST_ELEM(elem.toString()), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRFrom(Source src) {
/*     */     try {
/* 454 */       Unmarshaller unmarshaller = getContext().createUnmarshaller();
/* 455 */       JAXBElement<RequestSecurityTokenResponseType> rstType = unmarshaller.unmarshal(src, RequestSecurityTokenResponseType.class);
/* 456 */       RequestSecurityTokenResponseType type = rstType.getValue();
/* 457 */       return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(type);
/* 458 */     } catch (Exception ex) {
/* 459 */       log.log(Level.SEVERE, LogStringsMessages.WST_0008_FAIL_RSTR_SOURCE(src.toString()), ex);
/*     */       
/* 461 */       throw new RuntimeException(LogStringsMessages.WST_0008_FAIL_RSTR_SOURCE(src.toString()), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRFrom(Element elem) {
/*     */     try {
/* 470 */       Unmarshaller unmarshaller = getContext().createUnmarshaller();
/* 471 */       JAXBElement<RequestSecurityTokenResponseType> rstType = unmarshaller.unmarshal(elem, RequestSecurityTokenResponseType.class);
/* 472 */       RequestSecurityTokenResponseType type = rstType.getValue();
/* 473 */       return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(type);
/* 474 */     } catch (Exception ex) {
/* 475 */       log.log(Level.SEVERE, LogStringsMessages.WST_0008_FAIL_RSTR_SOURCE(elem.toString()), ex);
/*     */       
/* 477 */       throw new RuntimeException(LogStringsMessages.WST_0008_FAIL_RSTR_SOURCE(elem.toString()), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionFrom(Source src) {
/*     */     try {
/* 486 */       Unmarshaller u = getContext().createUnmarshaller();
/* 487 */       JAXBElement<RequestSecurityTokenResponseCollectionType> rstrcType = u.unmarshal(src, RequestSecurityTokenResponseCollectionType.class);
/* 488 */       RequestSecurityTokenResponseCollectionType type = rstrcType.getValue();
/* 489 */       return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(type);
/* 490 */     } catch (Exception ex) {
/* 491 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionFrom(Element elem) {
/*     */     try {
/* 500 */       Unmarshaller u = getContext().createUnmarshaller();
/* 501 */       JAXBElement<RequestSecurityTokenResponseCollectionType> rstrcType = u.unmarshal(elem, RequestSecurityTokenResponseCollectionType.class);
/* 502 */       RequestSecurityTokenResponseCollectionType type = rstrcType.getValue();
/* 503 */       return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(type);
/* 504 */     } catch (Exception ex) {
/* 505 */       throw new RuntimeException(ex.getMessage(), ex);
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
/*     */   public RequestSecurityToken createRSTFrom(JAXBElement<RequestSecurityTokenType> elem) {
/*     */     try {
/* 524 */       RequestSecurityTokenType type = elem.getValue();
/* 525 */       return (RequestSecurityToken)new RequestSecurityTokenImpl(type);
/* 526 */     } catch (Exception e) {
/* 527 */       log.log(Level.SEVERE, LogStringsMessages.WST_0010_FAILED_CREATION_FROM_JAXBELE("RST"), e);
/*     */       
/* 529 */       throw new RuntimeException(LogStringsMessages.WST_0010_FAILED_CREATION_FROM_JAXBELE("RST"), e);
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
/*     */   public RequestSecurityTokenResponse createRSTRFrom(JAXBElement<RequestSecurityTokenResponseType> elem) {
/*     */     try {
/* 547 */       RequestSecurityTokenResponseType type = elem.getValue();
/* 548 */       return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(type);
/* 549 */     } catch (Exception e) {
/* 550 */       log.log(Level.SEVERE, LogStringsMessages.WST_0009_FAIL_RSTR_ELEM(elem.toString()), e);
/*     */       
/* 552 */       throw new RuntimeException(LogStringsMessages.WST_0009_FAIL_RSTR_ELEM(elem.toString()), e);
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
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionFrom(JAXBElement<RequestSecurityTokenResponseCollectionType> elem) {
/*     */     try {
/* 569 */       RequestSecurityTokenResponseCollectionType type = elem.getValue();
/* 570 */       return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(type);
/* 571 */     } catch (Exception e) {
/* 572 */       log.log(Level.SEVERE, LogStringsMessages.WST_0010_FAILED_CREATION_FROM_JAXBELE("RSTRCollection"), e);
/*     */       
/* 574 */       throw new RuntimeException(LogStringsMessages.WST_0010_FAILED_CREATION_FROM_JAXBELE("RSTRCollection"), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SecurityTokenReference createSecurityTokenReference(JAXBElement<SecurityTokenReferenceType> elem) {
/*     */     try {
/* 580 */       SecurityTokenReferenceType type = elem.getValue();
/* 581 */       return (SecurityTokenReference)new SecurityTokenReferenceImpl(type);
/* 582 */     } catch (Exception e) {
/* 583 */       log.log(Level.SEVERE, LogStringsMessages.WST_0010_FAILED_CREATION_FROM_JAXBELE("STR"), e);
/*     */       
/* 585 */       throw new RuntimeException(LogStringsMessages.WST_0010_FAILED_CREATION_FROM_JAXBELE("STR"), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SecurityContextToken createSecurityContextToken(URI identifier, String instance, String wsuId) {
/* 590 */     throw new UnsupportedOperationException("this operation is not supported");
/*     */   }
/*     */   
/*     */   public JAXBElement toJAXBElement(BaseSTSRequest request) {
/* 594 */     if (request instanceof RequestSecurityToken) {
/* 595 */       return toJAXBElement((RequestSecurityToken)request);
/*     */     }
/*     */     
/* 598 */     return null;
/*     */   }
/*     */   
/*     */   public JAXBElement toJAXBElement(BaseSTSResponse response) {
/* 602 */     if (response instanceof RequestSecurityTokenResponse) {
/* 603 */       return toJAXBElement((RequestSecurityTokenResponse)response);
/*     */     }
/*     */     
/* 606 */     if (response instanceof RequestSecurityTokenResponseCollection) {
/* 607 */       return toJAXBElement((RequestSecurityTokenResponseCollection)response);
/*     */     }
/*     */     
/* 610 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(SecurityTokenReference str) {
/* 617 */     JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)str);
/*     */     
/* 619 */     return strElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(RequestSecurityToken rst) {
/* 626 */     JAXBElement<RequestSecurityTokenType> rstElement = (new ObjectFactory()).createRequestSecurityToken((RequestSecurityTokenType)rst);
/*     */     
/* 628 */     return rstElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(RequestSecurityTokenResponse rstr) {
/* 635 */     JAXBElement<RequestSecurityTokenResponseType> rstElement = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/*     */     
/* 637 */     return rstElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(Entropy entropy) {
/* 644 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 646 */     return etElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(RequestSecurityTokenResponseCollection rstrCollection) {
/* 653 */     JAXBElement<RequestSecurityTokenResponseCollectionType> rstElement = (new ObjectFactory()).createRequestSecurityTokenResponseCollection((RequestSecurityTokenResponseCollectionType)rstrCollection);
/*     */     
/* 655 */     return rstElement;
/*     */   }
/*     */   
/*     */   public Source toSource(BaseSTSRequest request) {
/* 659 */     if (request instanceof RequestSecurityToken) {
/* 660 */       return toSource((RequestSecurityToken)request);
/*     */     }
/*     */     
/* 663 */     return null;
/*     */   }
/*     */   
/*     */   public Source toSource(BaseSTSResponse response) {
/* 667 */     if (response instanceof RequestSecurityTokenResponse) {
/* 668 */       return toSource((RequestSecurityTokenResponse)response);
/*     */     }
/*     */     
/* 671 */     if (response instanceof RequestSecurityTokenResponseCollection) {
/* 672 */       return toSource((RequestSecurityTokenResponseCollection)response);
/*     */     }
/*     */     
/* 675 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toSource(RequestSecurityToken rst) {
/*     */     try {
/* 685 */       return new JAXBSource(getMarshaller(), toJAXBElement(rst));
/* 686 */     } catch (JAXBException ex) {
/* 687 */       log.log(Level.SEVERE, LogStringsMessages.WST_0002_FAIL_MARSHAL_TOSOURCE("RST"), ex);
/*     */       
/* 689 */       throw new RuntimeException(LogStringsMessages.WST_0002_FAIL_MARSHAL_TOSOURCE("RST"), ex);
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
/*     */   public Source toSource(RequestSecurityTokenResponse rstr) {
/*     */     try {
/* 702 */       return new JAXBSource(getMarshaller(), toJAXBElement(rstr));
/* 703 */     } catch (JAXBException ex) {
/* 704 */       log.log(Level.SEVERE, LogStringsMessages.WST_0002_FAIL_MARSHAL_TOSOURCE("RSTR"), ex);
/*     */       
/* 706 */       throw new RuntimeException(LogStringsMessages.WST_0002_FAIL_MARSHAL_TOSOURCE("RSTR"), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toSource(RequestSecurityTokenResponseCollection rstrCollection) {
/*     */     try {
/* 718 */       return new JAXBSource(getMarshaller(), toJAXBElement(rstrCollection));
/* 719 */     } catch (JAXBException ex) {
/* 720 */       log.log(Level.SEVERE, LogStringsMessages.WST_0002_FAIL_MARSHAL_TOSOURCE("RSTRCollection"), ex);
/*     */       
/* 722 */       throw new RuntimeException(LogStringsMessages.WST_0002_FAIL_MARSHAL_TOSOURCE("RSTRCollection"), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element toElement(BaseSTSRequest request) {
/* 727 */     if (request instanceof RequestSecurityToken) {
/* 728 */       return toElement((RequestSecurityToken)request);
/*     */     }
/*     */     
/* 731 */     return null;
/*     */   }
/*     */   
/*     */   public Element toElement(BaseSTSResponse response) {
/* 735 */     if (response instanceof RequestSecurityTokenResponse) {
/* 736 */       return toElement((RequestSecurityTokenResponse)response);
/*     */     }
/*     */     
/* 739 */     if (response instanceof RequestSecurityTokenResponseCollection) {
/* 740 */       return toElement((RequestSecurityTokenResponseCollection)response);
/*     */     }
/*     */     
/* 743 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(RequestSecurityToken rst) {
/*     */     try {
/* 755 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 758 */       JAXBElement<RequestSecurityTokenType> rstElement = (new ObjectFactory()).createRequestSecurityToken((RequestSecurityTokenType)rst);
/* 759 */       getMarshaller().marshal(rstElement, doc);
/* 760 */       return doc.getDocumentElement();
/* 761 */     } catch (JAXBException e) {
/* 762 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), e);
/*     */       
/* 764 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(RequestSecurityTokenResponse rstr) {
/*     */     try {
/* 776 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 779 */       JAXBElement<RequestSecurityTokenResponseType> rstrElement = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/* 780 */       getMarshaller().marshal(rstrElement, doc);
/* 781 */       return doc.getDocumentElement();
/*     */     }
/* 783 */     catch (Exception ex) {
/* 784 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */       
/* 786 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element toElement(RequestSecurityTokenResponse rstr, Document doc) {
/*     */     try {
/* 792 */       JAXBElement<RequestSecurityTokenResponseType> rstrElement = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/* 793 */       getMarshaller().marshal(rstrElement, doc);
/* 794 */       return doc.getDocumentElement();
/*     */     }
/* 796 */     catch (JAXBException ex) {
/* 797 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */       
/* 799 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(RequestSecurityTokenResponseCollection rstrCollection) {
/*     */     try {
/* 811 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 814 */       JAXBElement<RequestSecurityTokenResponseCollectionType> rstElement = (new ObjectFactory()).createRequestSecurityTokenResponseCollection((RequestSecurityTokenResponseCollectionType)rstrCollection);
/*     */       
/* 816 */       getMarshaller().marshal(rstElement, doc);
/* 817 */       return doc.getDocumentElement();
/* 818 */     } catch (JAXBException ex) {
/* 819 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */       
/* 821 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element toElement(BinarySecret secret) {
/*     */     try {
/* 827 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 830 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)secret);
/*     */       
/* 832 */       getMarshaller().marshal(bsElement, doc);
/* 833 */       return doc.getDocumentElement();
/* 834 */     } catch (JAXBException ex) {
/* 835 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */       
/* 837 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(SecurityTokenReference str, Document doc) {
/*     */     try {
/* 849 */       if (doc == null) {
/* 850 */         doc = WSTrustUtil.newDocument();
/*     */       }
/*     */ 
/*     */       
/* 854 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)str);
/* 855 */       getMarshaller().marshal(strElement, doc);
/* 856 */       return doc.getDocumentElement();
/* 857 */     } catch (JAXBException ex) {
/* 858 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */       
/* 860 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(BinarySecret secret, Document doc) {
/*     */     try {
/* 872 */       if (doc == null) {
/* 873 */         doc = WSTrustUtil.newDocument();
/*     */       }
/*     */ 
/*     */       
/* 877 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)secret);
/*     */       
/* 879 */       getMarshaller().marshal(bsElement, doc);
/* 880 */       return doc.getDocumentElement();
/*     */     }
/* 882 */     catch (JAXBException ex) {
/* 883 */       log.log(Level.SEVERE, LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */       
/* 885 */       throw new RuntimeException(LogStringsMessages.WST_0012_JAXB_EX_TO_ELEMENT(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Marshaller getMarshaller() {
/*     */     try {
/* 891 */       Marshaller marshaller = getContext().createMarshaller();
/* 892 */       marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new TrustNamespacePrefixMapper());
/*     */       
/* 894 */       return marshaller;
/* 895 */     } catch (PropertyException e) {
/* 896 */       log.log(Level.SEVERE, LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), e);
/*     */       
/* 898 */       throw new RuntimeException(LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), e);
/*     */     }
/* 900 */     catch (JAXBException jbe) {
/* 901 */       log.log(Level.SEVERE, LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), jbe);
/*     */       
/* 903 */       throw new RuntimeException(LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\WSTrustElementFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */