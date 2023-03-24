/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.security.core.ai.IdentityType;
/*     */ import com.sun.xml.security.core.ai.ObjectFactory;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.client.SecondaryIssuedTokenParameters;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.mex.client.MetadataClient;
/*     */ import com.sun.xml.ws.mex.client.PortInfo;
/*     */ import com.sun.xml.ws.mex.client.schema.Metadata;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secext10.BinarySecurityTokenType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.STSIssuedTokenFeature;
/*     */ import com.sun.xml.ws.security.trust.TrustPlugin;
/*     */ import com.sun.xml.ws.security.trust.WSTrustClientContract;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.ActAs;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.elements.ValidateTarget;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.impl.dsig.WSSPolicyConsumerImpl;
/*     */ import com.sun.xml.wss.jaxws.impl.Constants;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.rmi.RemoteException;
/*     */ import java.security.KeyException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.RespectBindingFeature;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.AddressingFeature;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrustPluginImpl
/*     */   implements TrustPlugin
/*     */ {
/* 148 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(IssuedTokenContext itc) throws WSTrustException {
/* 159 */     String signWith = null;
/* 160 */     String encryptWith = null;
/* 161 */     String appliesTo = itc.getEndpointAddress();
/* 162 */     STSIssuedTokenConfiguration stsConfig = itc.getSecurityPolicy().get(0);
/* 163 */     String stsURI = stsConfig.getSTSEndpoint();
/* 164 */     if (stsURI == null) {
/* 165 */       log.log(Level.SEVERE, LogStringsMessages.WST_0029_COULD_NOT_GET_STS_LOCATION(appliesTo));
/*     */       
/* 167 */       throw new WebServiceException(LogStringsMessages.WST_0029_COULD_NOT_GET_STS_LOCATION(appliesTo));
/*     */     } 
/* 169 */     Token oboToken = stsConfig.getOBOToken();
/*     */     
/* 171 */     BaseSTSResponse result = null;
/*     */     try {
/* 173 */       RequestSecurityToken request = createRequest(stsConfig, appliesTo, oboToken);
/*     */       
/* 175 */       result = invokeRST(request, stsConfig);
/*     */       
/* 177 */       WSTrustClientContract contract = WSTrustFactory.createWSTrustClientContract();
/* 178 */       contract.handleRSTR((BaseSTSRequest)request, result, itc);
/* 179 */       KeyPair keyPair = (KeyPair)stsConfig.getOtherOptions().get("UseKey-RSAKeyPair");
/* 180 */       if (keyPair != null) {
/* 181 */         itc.setProofKeyPair(keyPair);
/*     */       }
/*     */       
/* 184 */       encryptWith = stsConfig.getEncryptWith();
/* 185 */       if (encryptWith != null) {
/* 186 */         itc.setEncryptWith(encryptWith);
/*     */       }
/*     */       
/* 189 */       signWith = stsConfig.getSignWith();
/* 190 */       if (signWith != null) {
/* 191 */         itc.setSignWith(signWith);
/*     */       }
/*     */     }
/* 194 */     catch (RemoteException ex) {
/* 195 */       log.log(Level.SEVERE, LogStringsMessages.WST_0016_PROBLEM_IT_CTX(stsURI, appliesTo), ex);
/*     */       
/* 197 */       throw new WSTrustException(LogStringsMessages.WST_0016_PROBLEM_IT_CTX(stsURI, appliesTo), ex);
/* 198 */     } catch (URISyntaxException ex) {
/* 199 */       log.log(Level.SEVERE, LogStringsMessages.WST_0016_PROBLEM_IT_CTX(stsURI, appliesTo), ex);
/*     */       
/* 201 */       throw new WSTrustException(LogStringsMessages.WST_0016_PROBLEM_IT_CTX(stsURI, appliesTo));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processValidate(IssuedTokenContext itc) throws WSTrustException {
/* 206 */     STSIssuedTokenConfiguration stsConfig = itc.getSecurityPolicy().get(0);
/* 207 */     String stsURI = stsConfig.getSTSEndpoint();
/* 208 */     if (stsURI == null) {
/* 209 */       log.log(Level.SEVERE, LogStringsMessages.WST_0029_COULD_NOT_GET_STS_LOCATION(null));
/*     */       
/* 211 */       throw new WebServiceException(LogStringsMessages.WST_0029_COULD_NOT_GET_STS_LOCATION(null));
/*     */     } 
/* 213 */     BaseSTSResponse result = null;
/*     */     try {
/* 215 */       Token token = itc.getTarget();
/* 216 */       RequestSecurityToken request = createRequestForValidatation(stsConfig, token);
/* 217 */       result = invokeRST(request, stsConfig);
/* 218 */       WSTrustClientContract contract = WSTrustFactory.createWSTrustClientContract();
/* 219 */       contract.handleRSTR((BaseSTSRequest)request, result, itc);
/* 220 */     } catch (RemoteException ex) {
/* 221 */       log.log(Level.SEVERE, LogStringsMessages.WST_0016_PROBLEM_IT_CTX(stsURI, null), ex);
/*     */       
/* 223 */       throw new WSTrustException(LogStringsMessages.WST_0016_PROBLEM_IT_CTX(stsURI, null), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private RequestSecurityToken createRequest(STSIssuedTokenConfiguration stsConfig, String appliesTo, Token oboToken) throws URISyntaxException, WSTrustException, NumberFormatException {
/* 228 */     WSTrustVersion wstVer = WSTrustVersion.getInstance(stsConfig.getProtocol());
/* 229 */     WSTrustElementFactory fact = WSTrustElementFactory.newInstance(wstVer);
/* 230 */     URI requestType = URI.create(wstVer.getIssueRequestTypeURI());
/* 231 */     AppliesTo applTo = null;
/* 232 */     if (appliesTo != null) {
/* 233 */       applTo = WSTrustUtil.createAppliesTo(appliesTo);
/* 234 */       if (stsConfig.getOtherOptions().containsKey("Identity")) {
/* 235 */         addServerIdentity(applTo, stsConfig.getOtherOptions().get("Identity"));
/*     */       }
/*     */     } 
/*     */     
/* 239 */     RequestSecurityToken rst = fact.createRSTForIssue(null, requestType, null, applTo, null, null, null);
/*     */ 
/*     */     
/* 242 */     if (oboToken != null) {
/* 243 */       OnBehalfOf obo = fact.createOnBehalfOf(oboToken);
/* 244 */       rst.setOnBehalfOf(obo);
/*     */     } 
/*     */ 
/*     */     
/* 248 */     Token actAsToken = (Token)stsConfig.getOtherOptions().get("ActAs");
/* 249 */     if (actAsToken != null) {
/* 250 */       ActAs actAs = fact.createActAs(actAsToken);
/* 251 */       rst.setActAs(actAs);
/*     */     } 
/*     */ 
/*     */     
/* 255 */     Integer lf = (Integer)stsConfig.getOtherOptions().get("LifeTime");
/* 256 */     if (lf != null) {
/*     */       
/* 258 */       long lfValue = lf.longValue();
/* 259 */       if (lfValue > 0L) {
/* 260 */         long currentTime = WSTrustUtil.getCurrentTimeWithOffset();
/* 261 */         Lifetime lifetime = WSTrustUtil.createLifetime(currentTime, lfValue, wstVer);
/* 262 */         rst.setLifetime(lifetime);
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     String tokenType = null;
/* 267 */     String keyType = null;
/* 268 */     long keySize = -1L;
/* 269 */     String signWith = null;
/* 270 */     String encryptWith = null;
/* 271 */     String signatureAlgorithm = null;
/* 272 */     String encryptionAlgorithm = null;
/* 273 */     String keyWrapAlgorithm = null;
/* 274 */     String canonicalizationAlgorithm = null;
/* 275 */     Claims claims = null;
/* 276 */     if (wstVer.getNamespaceURI().equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 277 */       SecondaryIssuedTokenParameters sitp = stsConfig.getSecondaryIssuedTokenParameters();
/* 278 */       if (sitp != null) {
/* 279 */         SecondaryParameters sp = fact.createSecondaryParameters();
/* 280 */         tokenType = sitp.getTokenType();
/* 281 */         if (tokenType != null) {
/* 282 */           sp.setTokenType(URI.create(tokenType));
/*     */         }
/* 284 */         keyType = sitp.getKeyType();
/* 285 */         if (keyType != null) {
/* 286 */           sp.setKeyType(URI.create(keyType));
/*     */         }
/* 288 */         keySize = sitp.getKeySize();
/* 289 */         if (keySize > 0L) {
/* 290 */           sp.setKeySize(keySize);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 302 */         signatureAlgorithm = sitp.getSignatureAlgorithm();
/* 303 */         if (signatureAlgorithm != null) {
/* 304 */           sp.setSignatureAlgorithm(URI.create(signatureAlgorithm));
/*     */         }
/* 306 */         encryptionAlgorithm = sitp.getEncryptionAlgorithm();
/* 307 */         if (encryptionAlgorithm != null) {
/* 308 */           sp.setEncryptionAlgorithm(URI.create(encryptionAlgorithm));
/*     */         }
/*     */         
/* 311 */         canonicalizationAlgorithm = sitp.getCanonicalizationAlgorithm();
/* 312 */         if (canonicalizationAlgorithm != null) {
/* 313 */           sp.setCanonicalizationAlgorithm(URI.create(canonicalizationAlgorithm));
/*     */         }
/*     */         
/* 316 */         keyWrapAlgorithm = sitp.getKeyWrapAlgorithm();
/* 317 */         if (keyWrapAlgorithm != null) {
/* 318 */           sp.setKeyWrapAlgorithm(URI.create(keyWrapAlgorithm));
/*     */         }
/*     */         
/* 321 */         claims = sitp.getClaims();
/* 322 */         if (claims != null) {
/* 323 */           sp.setClaims(claims);
/*     */         }
/* 325 */         rst.setSecondaryParameters(sp);
/*     */       } 
/*     */     } 
/*     */     
/* 329 */     if (tokenType == null) {
/* 330 */       tokenType = stsConfig.getTokenType();
/* 331 */       if (tokenType != null) {
/* 332 */         rst.setTokenType(URI.create(tokenType));
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if (keyType == null) {
/* 337 */       keyType = stsConfig.getKeyType();
/* 338 */       if (keyType != null) {
/* 339 */         rst.setKeyType(URI.create(keyType));
/*     */       }
/*     */     } 
/*     */     
/* 343 */     if (keySize < 1L) {
/* 344 */       keySize = stsConfig.getKeySize();
/* 345 */       if (keySize > 0L) {
/* 346 */         rst.setKeySize(keySize);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     if (signatureAlgorithm == null) {
/* 367 */       signatureAlgorithm = stsConfig.getSignatureAlgorithm();
/* 368 */       if (signatureAlgorithm != null) {
/* 369 */         rst.setSignatureAlgorithm(URI.create(signatureAlgorithm));
/*     */       }
/*     */     } 
/*     */     
/* 373 */     if (encryptionAlgorithm == null) {
/* 374 */       encryptionAlgorithm = stsConfig.getEncryptionAlgorithm();
/* 375 */       if (encryptionAlgorithm != null) {
/* 376 */         rst.setEncryptionAlgorithm(URI.create(encryptionAlgorithm));
/*     */       }
/*     */     } 
/*     */     
/* 380 */     if (canonicalizationAlgorithm == null) {
/* 381 */       canonicalizationAlgorithm = stsConfig.getCanonicalizationAlgorithm();
/* 382 */       if (canonicalizationAlgorithm != null) {
/* 383 */         rst.setCanonicalizationAlgorithm(URI.create(canonicalizationAlgorithm));
/*     */       }
/*     */     } 
/*     */     
/* 387 */     if (claims == null) {
/* 388 */       claims = stsConfig.getClaims();
/* 389 */       if (claims != null) {
/* 390 */         rst.setClaims(fact.createClaims(claims));
/*     */       }
/*     */     } 
/*     */     
/* 394 */     int len = 32;
/* 395 */     if (keySize > 0L) {
/* 396 */       len = (int)keySize / 8;
/*     */     }
/*     */     
/* 399 */     if (wstVer.getSymmetricKeyTypeURI().equals(keyType)) {
/* 400 */       SecureRandom secRandom = new SecureRandom();
/* 401 */       byte[] nonce = new byte[len];
/* 402 */       secRandom.nextBytes(nonce);
/* 403 */       BinarySecret binarySecret = fact.createBinarySecret(nonce, wstVer.getNonceBinarySecretTypeURI());
/* 404 */       Entropy entropy = fact.createEntropy(binarySecret);
/* 405 */       rst.setEntropy(entropy);
/* 406 */       rst.setComputedKeyAlgorithm(URI.create(wstVer.getCKPSHA1algorithmURI()));
/* 407 */     } else if (wstVer.getPublicKeyTypeURI().equals(keyType) && keySize > 1L) {
/*     */       KeyPairGenerator kpg;
/*     */       
/*     */       try {
/* 411 */         kpg = KeyPairGenerator.getInstance("RSA");
/*     */       
/*     */       }
/* 414 */       catch (NoSuchAlgorithmException ex) {
/* 415 */         throw new WSTrustException("Unable to create key pairs for UseKey", ex);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 420 */       kpg.initialize((int)keySize);
/* 421 */       KeyPair keyPair = kpg.generateKeyPair();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 427 */       KeyInfo keyInfo = createKeyInfo(keyPair.getPublic());
/* 428 */       DocumentBuilderFactory docFactory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 429 */       Document doc = null;
/*     */       try {
/* 431 */         doc = docFactory.newDocumentBuilder().newDocument();
/* 432 */         keyInfo.marshal(new DOMStructure(doc), null);
/* 433 */       } catch (ParserConfigurationException ex) {
/* 434 */         log.log(Level.SEVERE, LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */         
/* 436 */         throw new WSTrustException(LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/* 437 */       } catch (MarshalException ex) {
/* 438 */         log.log(Level.SEVERE, LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */         
/* 440 */         throw new WSTrustException(LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */       } 
/* 442 */       GenericToken genericToken = new GenericToken(doc.getDocumentElement());
/* 443 */       UseKey useKey = fact.createUseKey((Token)genericToken, null);
/* 444 */       rst.setUseKey(useKey);
/*     */ 
/*     */       
/* 447 */       stsConfig.getOtherOptions().put("UseKey-RSAKeyPair", keyPair);
/*     */     } 
/*     */ 
/*     */     
/* 451 */     if (log.isLoggable(Level.FINE)) {
/* 452 */       log.log(Level.FINE, LogStringsMessages.WST_1006_CREATED_RST_ISSUE(WSTrustUtil.elemToString((BaseSTSRequest)rst, wstVer)));
/*     */     }
/*     */ 
/*     */     
/* 456 */     return rst;
/*     */   }
/*     */   
/*     */   private RequestSecurityToken createRequestForValidatation(STSIssuedTokenConfiguration stsConfig, Token token) {
/* 460 */     WSTrustVersion wstVer = WSTrustVersion.getInstance(stsConfig.getProtocol());
/* 461 */     WSTrustElementFactory fact = WSTrustElementFactory.newInstance(wstVer);
/* 462 */     URI requestType = URI.create(wstVer.getValidateRequestTypeURI());
/* 463 */     URI tokenType = URI.create(wstVer.getValidateStatuesTokenType());
/* 464 */     RequestSecurityToken rst = fact.createRSTForValidate(tokenType, requestType);
/* 465 */     if (wstVer.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2005/02/trust")) {
/* 466 */       rst.getAny().add(token.getTokenValue());
/*     */     } else {
/* 468 */       ValidateTarget vt = fact.createValidateTarget(token);
/* 469 */       rst.setValidateTarget(vt);
/*     */     } 
/*     */     
/* 472 */     return rst;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BaseSTSResponse invokeRST(RequestSecurityToken request, STSIssuedTokenConfiguration stsConfig) throws RemoteException, WSTrustException {
/* 478 */     String stsURI = stsConfig.getSTSEndpoint();
/* 479 */     STSIssuedTokenConfiguration rtConfig = (STSIssuedTokenConfiguration)stsConfig.getOtherOptions().get("RunTimeConfig");
/* 480 */     Dispatch<Message> dispatch = null;
/* 481 */     WSTrustVersion wstVer = WSTrustVersion.getInstance(stsConfig.getProtocol());
/* 482 */     WSTrustElementFactory fact = WSTrustElementFactory.newInstance(wstVer);
/* 483 */     if (rtConfig != null) {
/* 484 */       dispatch = (Dispatch<Message>)rtConfig.getOtherOptions().get(stsURI);
/*     */     } else {
/* 486 */       dispatch = (Dispatch<Message>)stsConfig.getOtherOptions().get(stsURI);
/*     */     } 
/*     */     
/* 489 */     if (dispatch == null) {
/* 490 */       URI wsdlLocation = null;
/* 491 */       QName serviceName = null;
/* 492 */       QName portName = null;
/*     */       
/* 494 */       String metadataStr = stsConfig.getSTSMEXAddress();
/* 495 */       if (metadataStr != null) {
/* 496 */         wsdlLocation = URI.create(metadataStr);
/*     */       } else {
/* 498 */         String namespace = stsConfig.getSTSNamespace();
/* 499 */         String wsdlLocationStr = stsConfig.getSTSWSDLLocation();
/* 500 */         if (wsdlLocationStr == null) {
/* 501 */           wsdlLocationStr = stsURI;
/*     */         } else {
/* 503 */           String serviceNameStr = stsConfig.getSTSServiceName();
/* 504 */           if (serviceNameStr != null && namespace != null) {
/* 505 */             serviceName = new QName(namespace, serviceNameStr);
/*     */           }
/*     */           
/* 508 */           String portNameStr = stsConfig.getSTSPortName();
/* 509 */           if (portNameStr != null && namespace != null) {
/* 510 */             portName = new QName(namespace, portNameStr);
/*     */           }
/*     */         } 
/* 513 */         wsdlLocation = URI.create(wsdlLocationStr);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 518 */       if (serviceName == null || portName == null) {
/*     */         
/* 520 */         if (log.isLoggable(Level.FINE)) {
/* 521 */           log.log(Level.FINE, LogStringsMessages.WST_1012_SERVICE_PORTNAME_MEX(serviceName, portName));
/*     */         }
/*     */ 
/*     */         
/* 525 */         QName[] names = doMexRequest(wsdlLocation.toString(), stsURI);
/* 526 */         serviceName = names[0];
/* 527 */         portName = names[1];
/*     */       } 
/*     */       
/* 530 */       Service service = null;
/*     */       
/*     */       try {
/* 533 */         String url = wsdlLocation.toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 542 */         Container container = (Container)stsConfig.getOtherOptions().get("CONTAINER");
/* 543 */         if (container != null) {
/* 544 */           WSService.InitParams initParams = new WSService.InitParams();
/* 545 */           initParams.setContainer(container);
/* 546 */           service = WSService.create(new URL(url), serviceName, initParams);
/*     */         } else {
/* 548 */           service = Service.create(new URL(url), serviceName);
/*     */         } 
/* 550 */       } catch (MalformedURLException ex) {
/* 551 */         log.log(Level.SEVERE, LogStringsMessages.WST_0041_SERVICE_NOT_CREATED(wsdlLocation.toString()), ex);
/*     */         
/* 553 */         throw new WebServiceException(LogStringsMessages.WST_0041_SERVICE_NOT_CREATED(wsdlLocation.toString()), ex);
/*     */       } 
/*     */       
/* 556 */       WebServiceFeature[] wsFeatures = null;
/*     */       
/* 558 */       if (rtConfig != null) {
/* 559 */         wsFeatures = new WebServiceFeature[] { new RespectBindingFeature(), new AddressingFeature(false), (WebServiceFeature)new STSIssuedTokenFeature(rtConfig) };
/*     */       }
/*     */       else {
/*     */         
/* 563 */         wsFeatures = new WebServiceFeature[] { new RespectBindingFeature(), new AddressingFeature(false) };
/*     */       } 
/* 565 */       dispatch = service.createDispatch(portName, Message.class, Service.Mode.MESSAGE, wsFeatures);
/* 566 */       if (rtConfig != null) {
/* 567 */         rtConfig.getOtherOptions().put(stsURI, dispatch);
/*     */       } else {
/* 569 */         stsConfig.getOtherOptions().put(stsURI, dispatch);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 578 */     if (stsURI != null) {
/* 579 */       dispatch.getRequestContext().put("javax.xml.ws.service.endpoint.address", stsURI);
/*     */     }
/* 581 */     dispatch.getRequestContext().put("isTrustMessage", "true");
/* 582 */     dispatch.getRequestContext().put("trustAction", getAction(wstVer, request.getRequestType().toString()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 603 */     setMessageProperties(dispatch.getRequestContext(), stsConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 628 */     Message reqMsg = Messages.createUsingPayload(fact.toSource(request), ((WSBinding)dispatch.getBinding()).getSOAPVersion());
/* 629 */     Message respMsg = dispatch.invoke(reqMsg);
/* 630 */     Source respSrc = respMsg.readPayloadAsSource();
/* 631 */     BaseSTSResponse resp = parseRSTR(respSrc, wstVer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     if (log.isLoggable(Level.FINE)) {
/* 639 */       log.log(Level.FINE, LogStringsMessages.WST_1007_CREATED_RSTR_ISSUE(WSTrustUtil.elemToString(resp, wstVer)));
/*     */     }
/*     */ 
/*     */     
/* 643 */     return resp;
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
/*     */   protected static QName[] doMexRequest(String wsdlLocation, String stsURI) throws WSTrustException {
/* 655 */     QName[] serviceInfo = new QName[2];
/* 656 */     MetadataClient mexClient = new MetadataClient();
/*     */     
/* 658 */     Metadata metadata = mexClient.retrieveMetadata(wsdlLocation);
/*     */ 
/*     */     
/* 661 */     if (metadata != null) {
/* 662 */       List<PortInfo> ports = mexClient.getServiceInformation(metadata);
/*     */ 
/*     */       
/* 665 */       for (PortInfo port : ports) {
/* 666 */         String uri = port.getAddress();
/*     */ 
/*     */ 
/*     */         
/* 670 */         if (URI.create(uri).getPath().equals(URI.create(stsURI).getPath())) {
/* 671 */           serviceInfo[0] = port.getServiceName();
/* 672 */           serviceInfo[1] = port.getPortName();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 678 */       if (serviceInfo[0] == null || serviceInfo[1] == null) {
/* 679 */         log.log(Level.SEVERE, LogStringsMessages.WST_0042_NO_MATCHING_SERVICE_MEX(stsURI));
/*     */         
/* 681 */         throw new WSTrustException(LogStringsMessages.WST_0042_NO_MATCHING_SERVICE_MEX(stsURI));
/*     */       } 
/*     */     } else {
/*     */       
/* 685 */       log.log(Level.SEVERE, LogStringsMessages.WST_0017_SERVICE_PORTNAME_ERROR(wsdlLocation));
/*     */       
/* 687 */       throw new WSTrustException(LogStringsMessages.WST_0017_SERVICE_PORTNAME_ERROR(wsdlLocation));
/*     */     } 
/*     */ 
/*     */     
/* 691 */     return serviceInfo;
/*     */   }
/*     */   
/*     */   private KeyInfo createKeyInfo(PublicKey pubKey) throws WSTrustException {
/* 695 */     KeyInfoFactory kif = WSSPolicyConsumerImpl.getInstance().getKeyInfoFactory();
/* 696 */     KeyValue kv = null;
/*     */     try {
/* 698 */       kv = kif.newKeyValue(pubKey);
/* 699 */     } catch (KeyException ex) {
/* 700 */       throw new WSTrustException("Unable to create key value", ex);
/*     */     } 
/* 702 */     List<KeyValue> kvs = new ArrayList<KeyValue>();
/* 703 */     kvs.add(kv);
/* 704 */     KeyInfo ki = kif.newKeyInfo(kvs);
/* 705 */     return ki;
/*     */   }
/*     */   
/*     */   private String getAction(WSTrustVersion wstVer, String requestType) {
/* 709 */     if (wstVer.getIssueRequestTypeURI().equals(requestType)) {
/* 710 */       return wstVer.getIssueRequestAction();
/*     */     }
/* 712 */     if (wstVer.getValidateRequestTypeURI().equals(requestType)) {
/* 713 */       return wstVer.getValidateRequestAction();
/*     */     }
/* 715 */     if (wstVer.getRenewRequestTypeURI().equals(requestType)) {
/* 716 */       return wstVer.getRenewRequestAction();
/*     */     }
/* 718 */     if (wstVer.getCancelRequestTypeURI().equals(requestType)) {
/* 719 */       return wstVer.getCancelRequestAction();
/*     */     }
/*     */     
/* 722 */     return wstVer.getIssueRequestAction();
/*     */   }
/*     */   private BaseSTSResponse parseRSTR(Source source, WSTrustVersion wstVer) throws WSTrustException {
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/* 726 */     Element ele = null;
/*     */     try {
/* 728 */       DOMResult result = new DOMResult();
/* 729 */       Transformer tf = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newTransformer();
/* 730 */       tf.transform(source, result);
/*     */       
/* 732 */       Node node = result.getNode();
/* 733 */       if (node instanceof Document) {
/* 734 */         ele = ((Document)node).getDocumentElement();
/* 735 */       } else if (node instanceof Element) {
/* 736 */         ele = (Element)node;
/*     */       } 
/* 738 */     } catch (Exception xe) {
/* 739 */       throw new WSTrustException("Error occurred while trying to parse RSTR stream", xe);
/*     */     } 
/*     */     
/* 742 */     RequestedSecurityToken rdst = null;
/* 743 */     WSTrustElementFactory fact = WSTrustElementFactory.newInstance(wstVer);
/* 744 */     NodeList list = ele.getElementsByTagNameNS(ele.getNamespaceURI(), "RequestedSecurityToken");
/* 745 */     if (list.getLength() > 0) {
/* 746 */       Element issuedToken = (Element)list.item(0).getChildNodes().item(0);
/* 747 */       GenericToken token = new GenericToken(issuedToken);
/* 748 */       rdst = fact.createRequestedSecurityToken((Token)token);
/*     */     } 
/*     */     
/* 751 */     if (wstVer.getNamespaceURI().equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 752 */       RequestSecurityTokenResponseCollection requestSecurityTokenResponseCollection = fact.createRSTRCollectionFrom(ele);
/* 753 */       ((RequestSecurityTokenResponse)requestSecurityTokenResponseCollection.getRequestSecurityTokenResponses().get(0)).setRequestedSecurityToken(rdst);
/*     */     } else {
/* 755 */       requestSecurityTokenResponse = fact.createRSTRFrom(ele);
/* 756 */       requestSecurityTokenResponse.setRequestedSecurityToken(rdst);
/*     */     } 
/* 758 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */   
/*     */   private void setMessageProperties(Map<String, Object> context, STSIssuedTokenConfiguration stsConfig) {
/* 762 */     context.putAll(stsConfig.getOtherOptions());
/* 763 */     if (context.containsKey(Constants.SC_ASSERTION)) {
/* 764 */       context.remove(Constants.SC_ASSERTION);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addServerIdentity(AppliesTo aplTo, Object identity) throws WSTrustException {
/* 769 */     if (identity instanceof Element) {
/* 770 */       aplTo.getAny().add(identity);
/* 771 */     } else if (identity instanceof X509Certificate) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 776 */       String id = UUID.randomUUID().toString();
/* 777 */       BinarySecurityTokenType bst = new BinarySecurityTokenType();
/* 778 */       bst.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
/* 779 */       bst.setId(id);
/* 780 */       bst.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/*     */       try {
/* 782 */         bst.setValue(((X509Certificate)identity).getEncoded());
/* 783 */       } catch (CertificateEncodingException ex) {
/* 784 */         throw new WSTrustException(ex.getMessage());
/*     */       } 
/* 786 */       JAXBElement<BinarySecurityTokenType> bstElem = (new ObjectFactory()).createBinarySecurityToken(bst);
/*     */ 
/*     */       
/* 789 */       IdentityType idElem = new IdentityType();
/* 790 */       idElem.getDnsOrSpnOrUpn().add(bstElem);
/* 791 */       aplTo.getAny().add((new ObjectFactory()).createIdentity(idElem));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\TrustPluginImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */