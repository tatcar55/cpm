/*     */ package com.sun.xml.ws.security.trust.impl.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.client.SecondaryIssuedTokenParameters;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.addressing.policy.Address;
/*     */ import com.sun.xml.ws.security.policy.IssuedToken;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.RequestSecurityTokenTemplate;
/*     */ import com.sun.xml.ws.security.secext10.AttributedString;
/*     */ import com.sun.xml.ws.security.secext10.BinarySecurityTokenType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.UsernameTokenType;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import java.net.URI;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class DefaultSTSIssuedTokenConfiguration
/*     */   extends STSIssuedTokenConfiguration
/*     */ {
/*     */   private static final String PRE_CONFIGURED_STS = "PreconfiguredSTS";
/*     */   private static final String NAMESPACE = "namespace";
/*     */   private static final String CONFIG_NAMESPACE = "";
/*     */   private static final String ENDPOINT = "endPoint";
/*     */   private static final String METADATA = "metadata";
/*     */   private static final String WSDL_LOCATION = "wsdlLocation";
/*     */   private static final String SERVICE_NAME = "serviceName";
/*     */   private static final String PORT_NAME = "portName";
/*     */   private static final String REQUEST_SECURITY_TOKEN_TEMPLATE = "RequestSecurityTokenTemplate";
/*     */   private static final String CLAIMS = "Claims";
/*     */   private static final String DIALECT = "Dialect";
/*     */   private static final String IDENTITY = "Identity";
/*     */   private static final String WST_VERSION = "wstVersion";
/*  88 */   private String tokenType = null;
/*     */   
/*  90 */   private String keyType = null;
/*     */   
/*  92 */   private long keySize = -1L;
/*     */   
/*  94 */   private String signatureAlg = null;
/*     */   
/*  96 */   private String encAlg = null;
/*     */   
/*  98 */   private String canAlg = null;
/*     */   
/* 100 */   private String keyWrapAlg = null;
/*     */   
/* 102 */   private Token oboToken = null;
/*     */   
/* 104 */   private String signWith = null;
/*     */   
/* 106 */   private String encryptWith = null;
/*     */   
/* 108 */   private Claims claims = null;
/*     */ 
/*     */   
/*     */   public DefaultSTSIssuedTokenConfiguration() {}
/*     */ 
/*     */   
/*     */   public DefaultSTSIssuedTokenConfiguration(String protocol, IssuedToken issuedToken, PolicyAssertion localToken) {
/* 115 */     if (protocol != null) {
/* 116 */       this.protocol = protocol;
/*     */     }
/* 118 */     parseAssertions(issuedToken, localToken);
/*     */   }
/*     */   public DefaultSTSIssuedTokenConfiguration(String stsEndpoint, String stsMEXAddress) {
/* 121 */     super(stsEndpoint, stsMEXAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSTSIssuedTokenConfiguration(String stsEndpoint, String stsWSDLLocation, String stsServiceName, String stsPortName, String stsNamespace) {
/* 126 */     super(stsEndpoint, stsWSDLLocation, stsServiceName, stsPortName, stsNamespace);
/*     */   }
/*     */   
/*     */   public DefaultSTSIssuedTokenConfiguration(String protocol, String stsEndpoint, String stsMEXAddress) {
/* 130 */     super(protocol, stsEndpoint, stsMEXAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSTSIssuedTokenConfiguration(String protocol, String stsEndpoint, String stsWSDLLocation, String stsServiceName, String stsPortName, String stsNamespace) {
/* 135 */     super(protocol, stsEndpoint, stsWSDLLocation, stsServiceName, stsPortName, stsNamespace);
/*     */   }
/*     */   
/*     */   public void setProtocol(String protocol) {
/* 139 */     this.protocol = protocol;
/*     */   }
/*     */   
/*     */   public void setSTSInfo(String stsEndpoint, String stsMEXAddress) {
/* 143 */     this.stsEndpoint = stsEndpoint;
/* 144 */     this.stsMEXAddress = stsMEXAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSTSInfo(String protocol, String stsEndpoint, String stsWSDLLocation, String stsServiceName, String stsPortName, String stsNamespace) {
/* 149 */     this.protocol = protocol;
/* 150 */     this.stsEndpoint = stsEndpoint;
/* 151 */     this.stsWSDLLocation = stsWSDLLocation;
/* 152 */     this.stsServiceName = stsServiceName;
/* 153 */     this.stsPortName = stsPortName;
/* 154 */     this.stsNamespace = stsNamespace;
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 158 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public void setKeyType(String keyType) {
/* 162 */     this.keyType = keyType;
/*     */   }
/*     */   
/*     */   public void setKeySize(long keySize) {
/* 166 */     this.keySize = keySize;
/*     */   }
/*     */   
/*     */   public void setSignWith(String signWithAlg) {
/* 170 */     this.signWith = signWithAlg;
/*     */   }
/*     */   
/*     */   public void setEncryptWith(String encWithAlg) {
/* 174 */     this.encryptWith = encWithAlg;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(String sigAlg) {
/* 178 */     this.signatureAlg = sigAlg;
/*     */   }
/*     */   
/*     */   public void setEncryptionAlgorithm(String encAlg) {
/* 182 */     this.encAlg = encAlg;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationAlgorithm(String canAlg) {
/* 186 */     this.canAlg = canAlg;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(String keyWrapAlg) {
/* 190 */     this.keyWrapAlg = keyWrapAlg;
/*     */   }
/*     */   
/*     */   public void setClaims(Claims claims) {
/* 194 */     this.claims = claims;
/*     */   }
/*     */   
/*     */   public void setOBOToken(Token token) {
/* 198 */     this.oboToken = token;
/*     */   }
/*     */   
/*     */   public void setOBOToken(String username, String password) {
/* 202 */     this.oboToken = createUsernameToken(username, password);
/*     */   }
/*     */   
/*     */   public void setOBOToken(X509Certificate cert) {
/* 206 */     this.oboToken = createBinaryTokenForCertificate(cert);
/*     */   }
/*     */   
/*     */   public void setActAsToken(String username, String password) {
/* 210 */     getOtherOptions().put("ActAs", createUsernameToken(username, password));
/*     */   }
/*     */   
/*     */   public void setActAsToken(X509Certificate cert) {
/* 214 */     getOtherOptions().put("ActAs", createBinaryTokenForCertificate(cert));
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 218 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public String getKeyType() {
/* 222 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 226 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public String getSignatureAlgorithm() {
/* 230 */     return this.signatureAlg;
/*     */   }
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 234 */     return this.encAlg;
/*     */   }
/*     */   
/*     */   public String getCanonicalizationAlgorithm() {
/* 238 */     return this.canAlg;
/*     */   }
/*     */   
/*     */   public String getKeyWrapAlgorithm() {
/* 242 */     return this.keyWrapAlg;
/*     */   }
/*     */   
/*     */   public String getSignWith() {
/* 246 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public String getEncryptWith() {
/* 250 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 254 */     return this.claims;
/*     */   }
/*     */   
/*     */   public Token getOBOToken() {
/* 258 */     return this.oboToken;
/*     */   }
/*     */   
/*     */   public void setSecondaryIssuedTokenParameters(SecondaryIssuedTokenParameters sisPara) {
/* 262 */     this.sisPara = sisPara;
/*     */   }
/*     */   
/*     */   private void parseAssertions(IssuedToken issuedToken, PolicyAssertion localToken) {
/* 266 */     Issuer issuer = issuedToken.getIssuer();
/* 267 */     URI stsURI = null;
/* 268 */     if (issuer != null) {
/* 269 */       stsURI = issuedToken.getIssuer().getAddress().getURI();
/* 270 */       if (issuer.getIdentity() != null) {
/* 271 */         getOtherOptions().put("Identity", issuer.getIdentity());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 276 */     if (stsURI != null) {
/* 277 */       this.stsEndpoint = stsURI.toString();
/* 278 */       Address metadataIssuerAddress = issuer.getMetadataAddress();
/* 279 */       if (metadataIssuerAddress != null) {
/* 280 */         URI metadataIssuerAddressURI = metadataIssuerAddress.getURI();
/* 281 */         if (metadataIssuerAddressURI != null) {
/* 282 */           this.stsMEXAddress = metadataIssuerAddressURI.toString();
/*     */         }
/*     */       } 
/*     */       
/* 286 */       if (this.stsMEXAddress == null) {
/* 287 */         this.stsMEXAddress = this.stsEndpoint + "/mex";
/*     */       }
/*     */     } 
/*     */     
/* 291 */     String stsProtocol = null;
/* 292 */     if (localToken != null)
/*     */     {
/* 294 */       if ("PreconfiguredSTS".equals(localToken.getName().getLocalPart())) {
/* 295 */         Map<QName, String> attrs = localToken.getAttributes();
/* 296 */         stsProtocol = trim(attrs.get(new QName("", "wstVersion")));
/* 297 */         if (stsURI == null) {
/* 298 */           this.stsNamespace = trim(attrs.get(new QName("", "namespace")));
/* 299 */           this.stsEndpoint = trim(attrs.get(new QName("", "endPoint")));
/* 300 */           if (this.stsEndpoint == null) {
/* 301 */             this.stsEndpoint = trim(attrs.get(new QName("", "endPoint".toLowerCase())));
/*     */           }
/* 303 */           this.stsMEXAddress = trim(attrs.get(new QName("", "metadata")));
/*     */           
/* 305 */           if (this.stsMEXAddress == null) {
/* 306 */             this.stsWSDLLocation = trim(attrs.get(new QName("", "wsdlLocation")));
/* 307 */             this.stsServiceName = trim(attrs.get(new QName("", "serviceName")));
/* 308 */             this.stsPortName = trim(attrs.get(new QName("", "portName")));
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 313 */         String shareToken = attrs.get(new QName("", "shareToken"));
/* 314 */         if ("true".equals(shareToken)) {
/* 315 */           getOtherOptions().put("shareToken", shareToken);
/*     */         }
/*     */ 
/*     */         
/* 319 */         String renewExpiredToken = attrs.get(new QName("", "renewExpiredToken"));
/* 320 */         if ("true".equals(renewExpiredToken)) {
/* 321 */           getOtherOptions().put("renewExpiredToken", renewExpiredToken);
/*     */         }
/*     */ 
/*     */         
/* 325 */         String maxClockSkew = attrs.get(new QName("", "MaxClockSkew"));
/* 326 */         if (maxClockSkew != null) {
/* 327 */           getOtherOptions().put("MaxClockSkew", maxClockSkew);
/*     */         }
/*     */ 
/*     */         
/* 331 */         if (localToken.hasParameters()) {
/* 332 */           Iterator<PolicyAssertion> pas = localToken.getParametersIterator();
/* 333 */           while (pas.hasNext()) {
/* 334 */             PolicyAssertion pa = pas.next();
/* 335 */             if ("LifeTime".equals(pa.getName().getLocalPart())) {
/* 336 */               getOtherOptions().put("LifeTime", Integer.valueOf(Integer.parseInt(pa.getValue())));
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 343 */     if (stsProtocol == null) {
/* 344 */       stsProtocol = this.protocol;
/*     */     }
/* 346 */     RequestSecurityTokenTemplate rstt = issuedToken.getRequestSecurityTokenTemplate();
/* 347 */     if (rstt != null) {
/* 348 */       Claims claims = null;
/* 349 */       if (this.protocol.equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 350 */         if (issuedToken.getClaims() != null) {
/* 351 */           claims = getClaims(issuedToken, stsProtocol);
/*     */         }
/*     */       }
/* 354 */       else if (rstt.getClaims() != null) {
/* 355 */         claims = getClaims(issuedToken, stsProtocol);
/*     */       } 
/*     */ 
/*     */       
/* 359 */       if (!this.protocol.equals(stsProtocol)) {
/*     */         
/* 361 */         copy(rstt, stsProtocol, this.protocol);
/* 362 */         setClaims(claims);
/* 363 */         this.protocol = stsProtocol;
/* 364 */       } else if (this.protocol.equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 365 */         SecondaryIssuedTokenParametersImpl sitp = new SecondaryIssuedTokenParametersImpl();
/* 366 */         copy(rstt, sitp);
/* 367 */         sitp.setClaims(claims);
/* 368 */         this.sisPara = sitp;
/*     */       } else {
/* 370 */         copy(rstt);
/* 371 */         setClaims(claims);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Claims getClaims(IssuedToken issuedToken, String stsWstProtocol) {
/* 377 */     Claims cs = null;
/*     */     
/*     */     try {
/* 380 */       if (this.protocol.equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 381 */         Element claimsEle = issuedToken.getClaims().getClaimsAsElement();
/* 382 */         cs = WSTrustElementFactory.newInstance(WSTrustVersion.WS_TRUST_13.getNamespaceURI()).createClaims(claimsEle);
/*     */       } else {
/* 384 */         RequestSecurityTokenTemplate rstt = issuedToken.getRequestSecurityTokenTemplate();
/* 385 */         Element claimsEle = rstt.getClaims().getClaimsAsElement();
/* 386 */         cs = WSTrustElementFactory.newInstance(WSTrustVersion.WS_TRUST_10.getNamespaceURI()).createClaims(claimsEle);
/*     */       } 
/* 388 */       cs = WSTrustElementFactory.newInstance(WSTrustVersion.getInstance(stsWstProtocol)).createClaims(cs);
/* 389 */     } catch (Exception e) {
/* 390 */       throw new WebServiceException(e);
/*     */     } 
/* 392 */     return cs;
/*     */   }
/*     */   
/*     */   private void copy(RequestSecurityTokenTemplate rstt) {
/* 396 */     setTokenType(trim(rstt.getTokenType()));
/* 397 */     setKeyType(trim(rstt.getKeyType()));
/* 398 */     setKeySize(rstt.getKeySize());
/* 399 */     setSignWith(trim(rstt.getSignWith()));
/* 400 */     setEncryptWith(trim(rstt.getEncryptWith()));
/* 401 */     setSignatureAlgorithm(trim(rstt.getSignatureAlgorithm()));
/* 402 */     setEncryptionAlgorithm(trim(rstt.getEncryptionAlgorithm()));
/* 403 */     setCanonicalizationAlgorithm(trim(rstt.getCanonicalizationAlgorithm()));
/*     */   }
/*     */   
/*     */   private void copy(RequestSecurityTokenTemplate rstt, SecondaryIssuedTokenParametersImpl sitp) {
/* 407 */     sitp.setTokenType(trim(rstt.getTokenType()));
/* 408 */     sitp.setKeyType(trim(rstt.getKeyType()));
/* 409 */     sitp.setKeySize(rstt.getKeySize());
/* 410 */     sitp.setSignWith(trim(rstt.getSignWith()));
/* 411 */     sitp.setEncryptWith(trim(rstt.getEncryptWith()));
/* 412 */     sitp.setSignatureAlgorithm(trim(rstt.getSignatureAlgorithm()));
/* 413 */     sitp.setEncryptionAlgorithm(trim(rstt.getEncryptionAlgorithm()));
/* 414 */     sitp.setCanonicalizationAlgorithm(trim(rstt.getCanonicalizationAlgorithm()));
/* 415 */     sitp.setKeyWrapAlgorithm(trim(rstt.getKeyWrapAlgorithm()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void copy(RequestSecurityTokenTemplate rstt, String stsWstProtocol, String serviceWstProtocol) {
/* 420 */     WSTrustVersion stsWstVer = WSTrustVersion.getInstance(stsWstProtocol);
/* 421 */     WSTrustVersion serviceWstVer = WSTrustVersion.getInstance(serviceWstProtocol);
/* 422 */     String rsttKeyType = trim(rstt.getKeyType());
/* 423 */     if (serviceWstVer.getPublicKeyTypeURI().equals(rsttKeyType)) {
/* 424 */       setKeyType(stsWstVer.getPublicKeyTypeURI());
/* 425 */     } else if (serviceWstVer.getSymmetricKeyTypeURI().equals(rsttKeyType)) {
/* 426 */       setKeyType(stsWstVer.getSymmetricKeyTypeURI());
/* 427 */     } else if (serviceWstVer.getBearerKeyTypeURI().equals(rsttKeyType)) {
/* 428 */       setKeyType(stsWstVer.getBearerKeyTypeURI());
/*     */     } 
/* 430 */     setTokenType(trim(rstt.getTokenType()));
/* 431 */     setKeySize(rstt.getKeySize());
/* 432 */     setSignWith(trim(rstt.getSignWith()));
/* 433 */     setEncryptWith(trim(rstt.getEncryptWith()));
/* 434 */     setSignatureAlgorithm(trim(rstt.getSignatureAlgorithm()));
/* 435 */     setEncryptionAlgorithm(trim(rstt.getEncryptionAlgorithm()));
/* 436 */     setCanonicalizationAlgorithm(trim(rstt.getCanonicalizationAlgorithm()));
/*     */   }
/*     */   
/*     */   public void copy(STSIssuedTokenConfiguration config) {
/* 440 */     if (config.getProtocol() != null) {
/* 441 */       this.protocol = config.getProtocol();
/*     */     }
/*     */     
/* 444 */     if (this.stsEndpoint == null && config.getSTSEndpoint() != null) {
/* 445 */       this.stsEndpoint = config.getSTSEndpoint();
/* 446 */       if (config.getSTSMEXAddress() != null) {
/* 447 */         this.stsMEXAddress = config.getSTSMEXAddress();
/* 448 */       } else if (config.getSTSWSDLLocation() != null) {
/* 449 */         this.stsWSDLLocation = config.getSTSWSDLLocation();
/* 450 */         this.stsServiceName = config.getSTSServiceName();
/* 451 */         this.stsPortName = config.getSTSPortName();
/* 452 */         this.stsNamespace = config.getSTSNamespace();
/*     */       } 
/*     */     } 
/*     */     
/* 456 */     if (this.tokenType == null && config.getTokenType() != null) {
/* 457 */       this.tokenType = config.getTokenType();
/*     */     }
/*     */     
/* 460 */     if (this.keyType == null && config.getKeyType() != null) {
/* 461 */       this.keyType = config.getKeyType();
/*     */     }
/*     */     
/* 464 */     if (this.keySize < 1L && config.getKeySize() > 0L) {
/* 465 */       this.keySize = config.getKeySize();
/*     */     }
/*     */     
/* 468 */     if (this.signatureAlg == null && config.getSignatureAlgorithm() != null) {
/* 469 */       this.signatureAlg = config.getSignatureAlgorithm();
/*     */     }
/*     */     
/* 472 */     if (this.encAlg == null && config.getEncryptionAlgorithm() != null) {
/* 473 */       this.encAlg = config.getEncryptionAlgorithm();
/*     */     }
/*     */     
/* 476 */     if (config.getCanonicalizationAlgorithm() != null) {
/* 477 */       this.canAlg = config.getCanonicalizationAlgorithm();
/*     */     }
/*     */     
/* 480 */     if (this.keyWrapAlg == null && config.getKeyWrapAlgorithm() != null) {
/* 481 */       this.keyWrapAlg = config.getKeyWrapAlgorithm();
/*     */     }
/*     */     
/* 484 */     if (this.signWith == null && config.getSignWith() != null) {
/* 485 */       this.signWith = config.getSignWith();
/*     */     }
/* 487 */     if (this.encryptWith == null && config.getEncryptWith() != null) {
/* 488 */       this.encryptWith = config.getEncryptWith();
/*     */     }
/*     */     
/* 491 */     if (config.getOBOToken() != null) {
/* 492 */       this.oboToken = config.getOBOToken();
/*     */     }
/*     */     
/* 495 */     if (this.claims == null && config.getClaims() != null) {
/* 496 */       this.claims = config.getClaims();
/*     */     }
/*     */     
/* 499 */     getOtherOptions().putAll(config.getOtherOptions());
/* 500 */     if (config.getOtherOptions().containsKey("IssuedToken")) {
/* 501 */       getOtherOptions().remove("IssuedToken");
/*     */     }
/*     */   }
/*     */   
/*     */   private Token createUsernameToken(String username, String password) {
/* 506 */     ObjectFactory fact = new ObjectFactory();
/* 507 */     UsernameTokenType ut = fact.createUsernameTokenType();
/* 508 */     AttributedString un = fact.createAttributedString();
/* 509 */     un.setValue(username);
/* 510 */     AttributedString pwd = fact.createAttributedString();
/* 511 */     pwd.setValue(password);
/* 512 */     ut.setUsername(un);
/* 513 */     ut.setPassword(pwd);
/*     */     
/* 515 */     return (Token)new GenericToken(fact.createUsernameToken(ut));
/*     */   }
/*     */   
/*     */   private Token createBinaryTokenForCertificate(X509Certificate cert) {
/* 519 */     ObjectFactory fact = new ObjectFactory();
/* 520 */     BinarySecurityTokenType bst = fact.createBinarySecurityTokenType();
/* 521 */     bst.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
/* 522 */     bst.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/*     */     try {
/* 524 */       bst.setValue(cert.getEncoded());
/* 525 */     } catch (CertificateEncodingException ex) {
/* 526 */       throw new RuntimeException(ex);
/*     */     } 
/*     */     
/* 529 */     return (Token)new GenericToken(fact.createBinarySecurityToken(bst));
/*     */   }
/*     */   
/*     */   private String trim(String uriStr) {
/* 533 */     if (uriStr != null) {
/* 534 */       return uriStr.trim();
/*     */     }
/*     */     
/* 537 */     return uriStr;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\client\DefaultSTSIssuedTokenConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */