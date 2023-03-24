/*     */ package com.sun.xml.ws.security.trust.impl.wssx;
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
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
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
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.DirectReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.KeyIdentifierImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinarySecretType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenResponseCollectionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenResponseType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.ActAsImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.BinarySecretImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.CancelTargetImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.ClaimsImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.EntropyImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.IssuedTokensImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.LifetimeImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.OnBehalfOfImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RenewTargetImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestSecurityTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestSecurityTokenResponseCollectionImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestSecurityTokenResponseImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestedAttachedReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestedProofTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestedSecurityTokenImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestedTokenCancelledImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.RequestedUnattachedReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.SecondaryParametersImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.StatusImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.UseKeyImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.elements.ValidateTargetImpl;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.TrustNamespacePrefixMapper;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class WSTrustElementFactoryImpl
/*     */   extends WSTrustElementFactory
/*     */ {
/* 157 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTForIssue(URI tokenType, URI requestType, URI context, AppliesTo scopes, Claims claims, Entropy entropy, Lifetime lt) throws WSTrustException {
/* 174 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(tokenType, requestType, context, scopes, claims, entropy, lt, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForIssue(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attachedReference, RequestedUnattachedReference unattachedReference, RequestedProofToken proofToken, Entropy entropy, Lifetime lt) throws WSTrustException {
/* 183 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(tokenType, context, token, scopes, attachedReference, unattachedReference, proofToken, entropy, lt, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionForIssue(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attached, RequestedUnattachedReference unattached, RequestedProofToken proofToken, Entropy entropy, Lifetime lt) throws WSTrustException {
/* 191 */     return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(tokenType, context, token, scopes, attached, unattached, proofToken, entropy, lt);
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionForIssue(List<RequestSecurityTokenResponse> rstrs) throws WSTrustException {
/* 196 */     RequestSecurityTokenResponseCollectionImpl rstrc = new RequestSecurityTokenResponseCollectionImpl();
/* 197 */     for (int i = 0; i < rstrs.size(); i++) {
/* 198 */       rstrc.addRequestSecurityTokenResponse(rstrs.get(i));
/*     */     }
/* 200 */     return (RequestSecurityTokenResponseCollection)rstrc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForRenew(URI tokenType, URI context, RequestedSecurityToken token, RequestedAttachedReference attachedReference, RequestedUnattachedReference unattachedRef, RequestedProofToken proofToken, Entropy entropy, Lifetime lifetime) throws WSTrustException {
/* 208 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(tokenType, context, token, null, attachedReference, unattachedRef, proofToken, entropy, lifetime, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuedTokens createIssuedTokens(RequestSecurityTokenResponseCollection issuedTokens) {
/* 217 */     return (IssuedTokens)new IssuedTokensImpl(issuedTokens);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entropy createEntropy(BinarySecret secret) {
/* 224 */     return (Entropy)new EntropyImpl(secret);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entropy createEntropy(EncryptedKey key) {
/* 231 */     return (Entropy)new EntropyImpl(key);
/*     */   }
/*     */   
/*     */   public BinarySecret createBinarySecret(byte[] rawValue, String type) {
/* 235 */     return (BinarySecret)new BinarySecretImpl(rawValue, type);
/*     */   }
/*     */   
/*     */   public BinarySecret createBinarySecret(Element elem) throws WSTrustException {
/* 239 */     return (BinarySecret)new BinarySecretImpl(BinarySecretImpl.fromElement(elem));
/*     */   }
/*     */   
/*     */   public Claims createClaims(Element elem) throws WSTrustException {
/* 243 */     return (Claims)new ClaimsImpl(ClaimsImpl.fromElement(elem));
/*     */   }
/*     */   
/*     */   public Claims createClaims(Claims claims) throws WSTrustException {
/* 247 */     ClaimsImpl newClaims = new ClaimsImpl();
/* 248 */     if (claims != null) {
/* 249 */       newClaims.setDialect(claims.getDialect());
/* 250 */       newClaims.getAny().addAll(claims.getAny());
/* 251 */       newClaims.getOtherAttributes().putAll(claims.getOtherAttributes());
/*     */     } 
/*     */     
/* 254 */     return (Claims)newClaims;
/*     */   }
/*     */   
/*     */   public Claims createClaims() throws WSTrustException {
/* 258 */     return (Claims)new ClaimsImpl();
/*     */   }
/*     */   
/*     */   public Status createStatus(String code, String reason) {
/* 262 */     return (Status)new StatusImpl(code, reason);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Lifetime createLifetime(AttributedDateTime created, AttributedDateTime expires) {
/* 270 */     return (Lifetime)new LifetimeImpl(created, expires);
/*     */   }
/*     */   
/*     */   public OnBehalfOf createOnBehalfOf(Token oboToken) {
/* 274 */     return (OnBehalfOf)new OnBehalfOfImpl(oboToken);
/*     */   }
/*     */   
/*     */   public ActAs createActAs(Token actAsToken) {
/* 278 */     return (ActAs)new ActAsImpl(actAsToken);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityToken createRequestedSecurityToken(Token token) {
/* 285 */     return (RequestedSecurityToken)new RequestedSecurityTokenImpl(token);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityToken createRequestedSecurityToken() {
/* 292 */     return (RequestedSecurityToken)new RequestedSecurityTokenImpl();
/*     */   }
/*     */   
/*     */   public DirectReference createDirectReference(String valueType, String uri) {
/* 296 */     return (DirectReference)new DirectReferenceImpl(valueType, uri);
/*     */   }
/*     */   
/*     */   public KeyIdentifier createKeyIdentifier(String valueType, String encodingType) {
/* 300 */     return (KeyIdentifier)new KeyIdentifierImpl(valueType, encodingType);
/*     */   }
/*     */   
/*     */   public SecurityTokenReference createSecurityTokenReference(Reference ref) {
/* 304 */     return (SecurityTokenReference)new SecurityTokenReferenceImpl(ref);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedAttachedReference createRequestedAttachedReference(SecurityTokenReference str) {
/* 310 */     return (RequestedAttachedReference)new RequestedAttachedReferenceImpl(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedUnattachedReference createRequestedUnattachedReference(SecurityTokenReference str) {
/* 317 */     return (RequestedUnattachedReference)new RequestedUnattachedReferenceImpl(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedProofToken createRequestedProofToken() {
/* 324 */     return (RequestedProofToken)new RequestedProofTokenImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTForRenew(URI tokenType, URI requestType, URI context, RenewTarget target, AllowPostdating apd, Renewing renewingInfo) {
/* 332 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(tokenType, requestType, context, target, apd, renewingInfo);
/*     */   }
/*     */   
/*     */   public RenewTarget createRenewTarget(SecurityTokenReference str) {
/* 336 */     return (RenewTarget)new RenewTargetImpl(str);
/*     */   }
/*     */   
/*     */   public CancelTarget createCancelTarget(SecurityTokenReference str) {
/* 340 */     return (CancelTarget)new CancelTargetImpl(str);
/*     */   }
/*     */   
/*     */   public ValidateTarget createValidateTarget(Token token) {
/* 344 */     return (ValidateTarget)new ValidateTargetImpl(token);
/*     */   }
/*     */   
/*     */   public SecondaryParameters createSecondaryParameters() {
/* 348 */     return (SecondaryParameters)new SecondaryParametersImpl();
/*     */   }
/*     */   
/*     */   public UseKey createUseKey(Token token, String sig) {
/* 352 */     UseKeyImpl useKeyImpl = new UseKeyImpl(token);
/* 353 */     if (sig != null) {
/* 354 */       useKeyImpl.setSignatureID(URI.create(sig));
/*     */     }
/*     */     
/* 357 */     return (UseKey)useKeyImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTForCancel(URI requestType, CancelTarget target) {
/* 364 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(null, requestType, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForCancel() {
/* 371 */     RequestSecurityTokenResponseImpl requestSecurityTokenResponseImpl = new RequestSecurityTokenResponseImpl();
/* 372 */     requestSecurityTokenResponseImpl.setRequestedTokenCancelled((RequestedTokenCancelled)new RequestedTokenCancelledImpl());
/*     */     
/* 374 */     return (RequestSecurityTokenResponse)requestSecurityTokenResponseImpl;
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
/* 386 */     return (RequestSecurityToken)new RequestSecurityTokenImpl(tokenType, requestType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRForValidate(URI tokenType, RequestedSecurityToken token, Status status) {
/* 393 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(tokenType, null, token, null, null, null, null, null, null, status);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRST() {
/* 400 */     return (RequestSecurityToken)new RequestSecurityTokenImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTR() {
/* 407 */     return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl();
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRC(List<RequestSecurityTokenResponse> rstrs) {
/* 411 */     RequestSecurityTokenResponseCollectionImpl requestSecurityTokenResponseCollectionImpl = new RequestSecurityTokenResponseCollectionImpl();
/*     */ 
/*     */     
/* 414 */     for (int i = 0; i < rstrs.size(); i++) {
/* 415 */       requestSecurityTokenResponseCollectionImpl.addRequestSecurityTokenResponse(rstrs.get(i));
/*     */     }
/* 417 */     return (RequestSecurityTokenResponseCollection)requestSecurityTokenResponseCollectionImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTFrom(Source src) {
/*     */     try {
/* 426 */       Unmarshaller u = getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 427 */       JAXBElement<RequestSecurityTokenType> rstType = u.unmarshal(src, RequestSecurityTokenType.class);
/* 428 */       RequestSecurityTokenType type = rstType.getValue();
/* 429 */       return (RequestSecurityToken)new RequestSecurityTokenImpl(type);
/* 430 */     } catch (Exception ex) {
/* 431 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityToken createRSTFrom(Element elem) {
/*     */     try {
/* 440 */       Unmarshaller u = getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 441 */       JAXBElement<RequestSecurityTokenType> rstType = u.unmarshal(elem, RequestSecurityTokenType.class);
/* 442 */       RequestSecurityTokenType type = rstType.getValue();
/* 443 */       return (RequestSecurityToken)new RequestSecurityTokenImpl(type);
/* 444 */     } catch (Exception ex) {
/* 445 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRFrom(Source src) {
/*     */     try {
/* 454 */       Unmarshaller u = getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 455 */       JAXBElement<RequestSecurityTokenResponseType> rstType = u.unmarshal(src, RequestSecurityTokenResponseType.class);
/* 456 */       RequestSecurityTokenResponseType type = rstType.getValue();
/* 457 */       return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(type);
/* 458 */     } catch (Exception ex) {
/* 459 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponse createRSTRFrom(Element elem) {
/*     */     try {
/* 468 */       Unmarshaller u = getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 469 */       JAXBElement<RequestSecurityTokenResponseType> rstType = u.unmarshal(elem, RequestSecurityTokenResponseType.class);
/* 470 */       RequestSecurityTokenResponseType type = rstType.getValue();
/* 471 */       return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(type);
/* 472 */     } catch (Exception ex) {
/* 473 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionFrom(Source src) {
/*     */     try {
/* 482 */       Unmarshaller u = getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 483 */       JAXBElement<RequestSecurityTokenResponseCollectionType> rstrcType = u.unmarshal(src, RequestSecurityTokenResponseCollectionType.class);
/* 484 */       RequestSecurityTokenResponseCollectionType type = rstrcType.getValue();
/* 485 */       return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(type);
/* 486 */     } catch (Exception ex) {
/* 487 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollection createRSTRCollectionFrom(Element elem) {
/* 495 */     checkElement(elem);
/*     */     try {
/* 497 */       Unmarshaller u = getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 498 */       JAXBElement<RequestSecurityTokenResponseCollectionType> rstrcType = u.unmarshal(elem, RequestSecurityTokenResponseCollectionType.class);
/* 499 */       RequestSecurityTokenResponseCollectionType type = rstrcType.getValue();
/* 500 */       return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(type);
/* 501 */     } catch (Exception ex) {
/* 502 */       throw new RuntimeException(ex.getMessage(), ex);
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
/* 521 */       RequestSecurityTokenType type = elem.getValue();
/* 522 */       return (RequestSecurityToken)new RequestSecurityTokenImpl(type);
/* 523 */     } catch (Exception e) {
/* 524 */       throw new RuntimeException("There was a problem while creating RST from JAXBElement", e);
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
/* 542 */       RequestSecurityTokenResponseType type = elem.getValue();
/* 543 */       return (RequestSecurityTokenResponse)new RequestSecurityTokenResponseImpl(type);
/* 544 */     } catch (Exception e) {
/* 545 */       throw new RuntimeException("There was a problem while creating RSTR from JAXBElement", e);
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
/* 562 */       RequestSecurityTokenResponseCollectionType type = elem.getValue();
/* 563 */       return (RequestSecurityTokenResponseCollection)new RequestSecurityTokenResponseCollectionImpl(type);
/* 564 */     } catch (Exception e) {
/* 565 */       throw new RuntimeException("There was a problem while creating RSTRCollection from JAXBElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object createResponseFrom(JAXBElement elem) {
/* 570 */     String local = elem.getName().getLocalPart();
/* 571 */     if (local.equalsIgnoreCase("RequestSecurityTokenResponseType")) {
/* 572 */       return createRSTRFrom(elem);
/*     */     }
/* 574 */     return createRSTRCollectionFrom(elem);
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityTokenReference createSecurityTokenReference(JAXBElement<SecurityTokenReferenceType> elem) {
/*     */     try {
/* 580 */       SecurityTokenReferenceType type = elem.getValue();
/* 581 */       return (SecurityTokenReference)new SecurityTokenReferenceImpl(type);
/* 582 */     } catch (Exception e) {
/* 583 */       throw new RuntimeException("There was a problem while creating STR from JAXBElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SecurityContextToken createSecurityContextToken(URI identifier, String instance, String wsuId) {
/* 588 */     throw new UnsupportedOperationException("this operation is not supported");
/*     */   }
/*     */   
/*     */   public JAXBElement toJAXBElement(BaseSTSRequest request) {
/* 592 */     if (request instanceof RequestSecurityToken) {
/* 593 */       return toJAXBElement((RequestSecurityToken)request);
/*     */     }
/*     */     
/* 596 */     return null;
/*     */   }
/*     */   
/*     */   public JAXBElement toJAXBElement(BaseSTSResponse response) {
/* 600 */     if (response instanceof RequestSecurityTokenResponse) {
/* 601 */       return toJAXBElement((RequestSecurityTokenResponse)response);
/*     */     }
/*     */     
/* 604 */     if (response instanceof RequestSecurityTokenResponseCollection) {
/* 605 */       return toJAXBElement((RequestSecurityTokenResponseCollection)response);
/*     */     }
/*     */     
/* 608 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(SecurityTokenReference str) {
/* 615 */     JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)str);
/*     */     
/* 617 */     return strElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(RequestSecurityToken rst) {
/* 624 */     JAXBElement<RequestSecurityTokenType> rstElement = (new ObjectFactory()).createRequestSecurityToken((RequestSecurityTokenType)rst);
/*     */     
/* 626 */     return rstElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(RequestSecurityTokenResponse rstr) {
/* 633 */     JAXBElement<RequestSecurityTokenResponseType> rstElement = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/*     */     
/* 635 */     return rstElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(Entropy entropy) {
/* 643 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 645 */     return etElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement toJAXBElement(RequestSecurityTokenResponseCollection rstrCollection) {
/* 652 */     JAXBElement<RequestSecurityTokenResponseCollectionType> rstElement = (new ObjectFactory()).createRequestSecurityTokenResponseCollection((RequestSecurityTokenResponseCollectionType)rstrCollection);
/*     */     
/* 654 */     return rstElement;
/*     */   }
/*     */   
/*     */   public Source toSource(BaseSTSRequest request) {
/* 658 */     if (request instanceof RequestSecurityToken) {
/* 659 */       return toSource((RequestSecurityToken)request);
/*     */     }
/*     */     
/* 662 */     return null;
/*     */   }
/*     */   
/*     */   public Source toSource(BaseSTSResponse response) {
/* 666 */     if (response instanceof RequestSecurityTokenResponse) {
/* 667 */       return toSource((RequestSecurityTokenResponse)response);
/*     */     }
/*     */     
/* 670 */     if (response instanceof RequestSecurityTokenResponseCollection) {
/* 671 */       return toSource((RequestSecurityTokenResponseCollection)response);
/*     */     }
/*     */     
/* 674 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toSource(RequestSecurityToken rst) {
/* 684 */     return new DOMSource(toElement(rst));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toSource(RequestSecurityTokenResponse rstr) {
/* 694 */     return new DOMSource(toElement(rstr));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toSource(RequestSecurityTokenResponseCollection rstrCollection) {
/* 704 */     return new DOMSource(toElement(rstrCollection));
/*     */   }
/*     */   
/*     */   public Element toElement(BaseSTSRequest request) {
/* 708 */     if (request instanceof RequestSecurityToken) {
/* 709 */       return toElement((RequestSecurityToken)request);
/*     */     }
/*     */     
/* 712 */     return null;
/*     */   }
/*     */   
/*     */   public Element toElement(BaseSTSResponse response) {
/* 716 */     if (response instanceof RequestSecurityTokenResponse) {
/* 717 */       return toElement((RequestSecurityTokenResponse)response);
/*     */     }
/*     */     
/* 720 */     if (response instanceof RequestSecurityTokenResponseCollection) {
/* 721 */       return toElement((RequestSecurityTokenResponseCollection)response);
/*     */     }
/*     */     
/* 724 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(RequestSecurityToken rst) {
/*     */     try {
/* 735 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 738 */       JAXBElement<RequestSecurityTokenType> rstElement = (new ObjectFactory()).createRequestSecurityToken((RequestSecurityTokenType)rst);
/* 739 */       getMarshaller().marshal(rstElement, doc);
/* 740 */       return doc.getDocumentElement();
/*     */     }
/* 742 */     catch (Exception ex) {
/* 743 */       throw new RuntimeException(ex.getMessage(), ex);
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
/*     */   public Element toElement(RequestSecurityTokenResponse rstr) {
/*     */     try {
/* 757 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 760 */       JAXBElement<RequestSecurityTokenResponseType> rstrElement = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/* 761 */       getMarshaller().marshal(rstrElement, doc);
/* 762 */       return doc.getDocumentElement();
/*     */     }
/* 764 */     catch (Exception ex) {
/* 765 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element toElement(RequestSecurityTokenResponse rstr, Document doc) {
/*     */     try {
/* 772 */       JAXBElement<RequestSecurityTokenResponseType> rstrElement = (new ObjectFactory()).createRequestSecurityTokenResponse((RequestSecurityTokenResponseType)rstr);
/* 773 */       getMarshaller().marshal(rstrElement, doc);
/* 774 */       return doc.getDocumentElement();
/*     */     }
/* 776 */     catch (Exception ex) {
/* 777 */       throw new RuntimeException(ex.getMessage(), ex);
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
/* 789 */       Document doc = WSTrustUtil.newDocument();
/*     */       
/* 791 */       JAXBElement<RequestSecurityTokenResponseCollectionType> rstrElement = (new ObjectFactory()).createRequestSecurityTokenResponseCollection((RequestSecurityTokenResponseCollectionType)rstrCollection);
/*     */       
/* 793 */       getMarshaller().marshal(rstrElement, doc);
/*     */       
/* 795 */       return doc.getDocumentElement();
/* 796 */     } catch (Exception ex) {
/* 797 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element toElement(BinarySecret bs) {
/*     */     try {
/* 803 */       Document doc = WSTrustUtil.newDocument();
/*     */ 
/*     */       
/* 806 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)bs);
/*     */       
/* 808 */       getMarshaller().marshal(bsElement, doc);
/* 809 */       return doc.getDocumentElement();
/* 810 */     } catch (Exception ex) {
/* 811 */       throw new RuntimeException(ex.getMessage(), ex);
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
/* 823 */       if (doc == null) {
/* 824 */         doc = WSTrustUtil.newDocument();
/*     */       }
/*     */ 
/*     */       
/* 828 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)str);
/* 829 */       getMarshaller().marshal(strElement, doc);
/* 830 */       return doc.getDocumentElement();
/*     */     }
/* 832 */     catch (Exception ex) {
/* 833 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(BinarySecret bs, Document doc) {
/*     */     try {
/* 845 */       if (doc == null) {
/* 846 */         doc = WSTrustUtil.newDocument();
/*     */       }
/*     */ 
/*     */       
/* 850 */       JAXBElement<BinarySecretType> bsElement = (new ObjectFactory()).createBinarySecret((BinarySecretType)bs);
/*     */       
/* 852 */       getMarshaller().marshal(bsElement, doc);
/* 853 */       return doc.getDocumentElement();
/*     */     }
/* 855 */     catch (Exception ex) {
/* 856 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Marshaller getMarshaller() {
/*     */     try {
/* 862 */       Marshaller marshaller = getContext(WSTrustVersion.WS_TRUST_13).createMarshaller();
/* 863 */       marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new TrustNamespacePrefixMapper());
/*     */       
/* 865 */       return marshaller;
/* 866 */     } catch (PropertyException e) {
/* 867 */       log.log(Level.SEVERE, LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), e);
/*     */       
/* 869 */       throw new RuntimeException(LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), e);
/*     */     }
/* 871 */     catch (JAXBException jbe) {
/* 872 */       log.log(Level.SEVERE, LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), jbe);
/*     */       
/* 874 */       throw new RuntimeException(LogStringsMessages.WST_0003_ERROR_CREATING_WSTRUSTFACT(), jbe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkElement(Element elem) {
/* 880 */     if (elem != null && elem.getLocalName().equalsIgnoreCase("Fault")) {
/*     */       try {
/* 882 */         QName qname = null;
/* 883 */         Map<String, Object> faultMap = null;
/* 884 */         Set<String> subcodeValues = new LinkedHashSet<String>();
/*     */         
/* 886 */         if (elem.getNamespaceURI().equals("http://schemas.xmlsoap.org/soap/envelope/")) {
/* 887 */           faultMap = getFaultCodeAndReasonForSOAP1_1(elem);
/* 888 */           String codeText = (String)faultMap.get("CodeText");
/* 889 */           String reasonText = (String)faultMap.get("ReasonText");
/* 890 */           codeText = codeText.substring(codeText.indexOf(":") + 1);
/* 891 */           qname = new QName("http://schemas.xmlsoap.org/soap/envelope/", codeText);
/* 892 */           throw new SOAPFaultException(SOAPFactory.newInstance("SOAP 1.1 Protocol").createFault(reasonText, qname));
/* 893 */         }  if (elem.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */         {
/* 895 */           faultMap = getFaultCodeAndReasonForSOAP1_2(elem, "Code", subcodeValues);
/* 896 */           String codeText = (String)faultMap.get("CodeText");
/* 897 */           String reasonText = (String)faultMap.get("ReasonText");
/*     */           
/* 899 */           codeText = codeText.substring(codeText.indexOf(":") + 1);
/* 900 */           qname = new QName("http://www.w3.org/2003/05/soap-envelope", codeText);
/* 901 */           SOAPFault fault = SOAPFactory.newInstance("SOAP 1.2 Protocol").createFault(reasonText, qname);
/*     */ 
/*     */           
/* 904 */           List<String> subcodesList = new ArrayList<String>(subcodeValues);
/* 905 */           Collections.reverse(subcodesList);
/* 906 */           if (!subcodesList.isEmpty()) {
/* 907 */             for (String subCodeValue : subcodesList) {
/* 908 */               subCodeValue = subCodeValue.substring(subCodeValue.indexOf(":") + 1);
/* 909 */               QName subcodeqname = new QName("http://www.w3.org/2003/05/soap-envelope", subCodeValue);
/* 910 */               fault.appendFaultSubcode(subcodeqname);
/*     */             } 
/*     */           }
/* 913 */           throw new SOAPFaultException(fault);
/*     */         }
/*     */       
/* 916 */       } catch (SOAPException se) {
/* 917 */         throw new RuntimeException(se.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private Map getFaultCodeAndReasonForSOAP1_1(Element elem) {
/* 923 */     Map<String, String> faultMap = new HashMap<String, String>(2);
/* 924 */     Node reasonNode = null;
/* 925 */     String reasonText = null;
/* 926 */     String codeText = null;
/* 927 */     NodeList nodes = elem.getChildNodes();
/* 928 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 929 */       Node node = nodes.item(i);
/* 930 */       if (node.getNodeType() != 3)
/*     */       {
/*     */         
/* 933 */         if ("faultcode".equals(node.getLocalName())) {
/* 934 */           codeText = node.getTextContent();
/*     */         }
/* 936 */         else if ("faultstring".equals(node.getLocalName())) {
/* 937 */           reasonText = node.getTextContent();
/*     */         }  } 
/*     */     } 
/* 940 */     faultMap.put("CodeText", codeText);
/* 941 */     faultMap.put("ReasonText", reasonText);
/* 942 */     return faultMap;
/*     */   }
/*     */ 
/*     */   
/*     */   private Map getFaultCodeAndReasonForSOAP1_2(Element elem, String codeString, Set<String> subcodeValues) {
/* 947 */     Map<String, Object> faultMap = new HashMap<String, Object>();
/* 948 */     Node reasonNode = null;
/* 949 */     String reasonText = null;
/* 950 */     Node codeNode = null;
/* 951 */     String codeText = null;
/*     */ 
/*     */     
/* 954 */     NodeList nodes = elem.getChildNodes();
/* 955 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 956 */       Node node = nodes.item(i);
/* 957 */       if (node.getNodeType() != 3)
/*     */       {
/*     */ 
/*     */         
/* 961 */         if (codeString.equals(node.getLocalName())) {
/* 962 */           codeNode = node;
/* 963 */           NodeList subNodes = codeNode.getChildNodes();
/* 964 */           for (int j = 0; j < subNodes.getLength(); j++) {
/* 965 */             Node subNode = subNodes.item(j);
/* 966 */             if (subNode.getNodeType() != 3) {
/*     */ 
/*     */               
/* 969 */               if ("Value".equals(subNode.getLocalName())) {
/* 970 */                 codeText = subNode.getTextContent();
/*     */               }
/* 972 */               if ("Subcode".equals(subNode.getLocalName()))
/* 973 */               { Map subcodeMap = getFaultCodeAndReasonForSOAP1_2((Element)node, "Subcode", subcodeValues);
/*     */                 
/* 975 */                 subcodeValues.add((String)subcodeMap.get("CodeText")); } 
/*     */             } 
/*     */           } 
/* 978 */         } else if ("Reason".equals(node.getLocalName())) {
/* 979 */           reasonNode = node;
/* 980 */           NodeList subNodes = reasonNode.getChildNodes();
/* 981 */           for (int j = 0; j < subNodes.getLength(); j++) {
/* 982 */             Node subNode = subNodes.item(j);
/* 983 */             if (subNode.getNodeType() != 3)
/*     */             {
/*     */               
/* 986 */               if ("Text".equals(subNode.getLocalName()))
/* 987 */                 reasonText = subNode.getTextContent(); 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 993 */     faultMap.put("CodeText", codeText);
/* 994 */     faultMap.put("ReasonText", reasonText);
/* 995 */     return faultMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\WSTrustElementFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */