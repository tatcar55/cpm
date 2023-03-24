/*      */ package com.sun.xml.wss.impl;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*      */ import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
/*      */ import com.sun.xml.ws.api.server.WSEndpoint;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*      */ import com.sun.xml.wss.NonceManager;
/*      */ import com.sun.xml.wss.SecurityEnvironment;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.Timestamp;
/*      */ import com.sun.xml.wss.core.reference.KeyIdentifierSPI;
/*      */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*      */ import com.sun.xml.wss.impl.callback.CertificateValidationCallback;
/*      */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*      */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.saml.Assertion;
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.AccessController;
/*      */ import java.security.Key;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.CertPath;
/*      */ import java.security.cert.CertPathBuilder;
/*      */ import java.security.cert.CertPathValidator;
/*      */ import java.security.cert.CertStore;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateEncodingException;
/*      */ import java.security.cert.CertificateExpiredException;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.CertificateNotYetValidException;
/*      */ import java.security.cert.CollectionCertStoreParameters;
/*      */ import java.security.cert.PKIXBuilderParameters;
/*      */ import java.security.cert.X509CertSelector;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.NameCallback;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.message.callback.CertStoreCallback;
/*      */ import javax.security.auth.message.callback.PasswordValidationCallback;
/*      */ import javax.security.auth.message.callback.PrivateKeyCallback;
/*      */ import javax.security.auth.message.callback.SecretKeyCallback;
/*      */ import javax.security.auth.message.callback.TrustStoreCallback;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import javax.security.auth.x500.X500PrivateCredential;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import org.ietf.jgss.GSSCredential;
/*      */ import org.ietf.jgss.GSSName;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WssProviderSecurityEnvironment
/*      */   implements SecurityEnvironment
/*      */ {
/*      */   private Map _securityOptions;
/*      */   private CallbackHandler _handler;
/*  152 */   protected final long MAX_CLOCK_SKEW = 360000L;
/*      */ 
/*      */   
/*  155 */   protected final long TIMESTAMP_FRESHNESS_LIMIT = 300000L;
/*      */   
/*  157 */   private static final SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*      */   
/*  159 */   private static final SimpleDateFormat calendarFormatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
/*      */ 
/*      */ 
/*      */   
/*      */   public WssProviderSecurityEnvironment(CallbackHandler handler, Map options) throws XWSSecurityException {
/*  164 */     this._handler = new PriviledgedHandler(handler);
/*  165 */     this._securityOptions = options;
/*      */     
/*  167 */     if (this._securityOptions != null) {
/*  168 */       String mo_aliases = (String)this._securityOptions.get("ALIASES");
/*  169 */       String mo_keypwds = (String)this._securityOptions.get("PASSWORDS");
/*      */       
/*  171 */       if (mo_aliases != null && mo_keypwds != null) {
/*  172 */         StringTokenizer aliases = new StringTokenizer(mo_aliases, " ");
/*  173 */         StringTokenizer keypwds = new StringTokenizer(mo_keypwds, " ");
/*  174 */         if (aliases.countTokens() != keypwds.countTokens());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, String alias) throws XWSSecurityException {
/*  192 */     PrivateKey privateKey = null;
/*      */     try {
/*  194 */       PrivateKeyCallback.AliasRequest aliasRequest = new PrivateKeyCallback.AliasRequest(alias);
/*      */       
/*  196 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)aliasRequest);
/*  197 */       Callback[] callbacks = { (Callback)pkCallback };
/*  198 */       this._handler.handle(callbacks);
/*  199 */       privateKey = pkCallback.getKey();
/*  200 */     } catch (Exception e) {
/*  201 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  204 */     if (privateKey == null) {
/*  205 */       throw new XWSSecurityException("Unable to locate private key for the alias " + alias);
/*      */     }
/*      */ 
/*      */     
/*  209 */     return privateKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*      */     try {
/*  230 */       Subject subject = getSubject(context);
/*  231 */       if (subject != null) {
/*  232 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  233 */         if (set != null) {
/*  234 */           Iterator<X500PrivateCredential> it = set.iterator();
/*  235 */           while (it.hasNext()) {
/*  236 */             X500PrivateCredential cred = it.next();
/*  237 */             if (matchesKeyIdentifier(Base64.decode(keyIdentifier), cred.getCertificate()))
/*      */             {
/*  239 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  244 */       PrivateKeyCallback.SubjectKeyIDRequest subjectKeyIDRequest = new PrivateKeyCallback.SubjectKeyIDRequest(keyIdentifier);
/*      */       
/*  246 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)subjectKeyIDRequest);
/*  247 */       Callback[] callbacks = { (Callback)pkCallback };
/*  248 */       this._handler.handle(callbacks);
/*      */       
/*  250 */       return pkCallback.getKey();
/*  251 */     } catch (Exception e) {
/*  252 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, X509Certificate cert) throws XWSSecurityException {
/*      */     try {
/*  271 */       Subject subject = getSubject(context);
/*  272 */       if (subject != null) {
/*  273 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  274 */         if (set != null) {
/*  275 */           String issuerName = RFC2253Parser.normalize(cert.getIssuerDN().getName());
/*      */ 
/*      */           
/*  278 */           Iterator<X500PrivateCredential> it = set.iterator();
/*  279 */           while (it.hasNext()) {
/*  280 */             X500PrivateCredential cred = it.next();
/*  281 */             X509Certificate x509Cert = cred.getCertificate();
/*  282 */             BigInteger serialNo = x509Cert.getSerialNumber();
/*  283 */             X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/*  284 */             X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  285 */             if (serialNo.equals(cert.getSerialNumber()) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */             {
/*  287 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  293 */       PrivateKeyCallback.IssuerSerialNumRequest issuerSerialNumRequest = new PrivateKeyCallback.IssuerSerialNumRequest(cert.getIssuerX500Principal(), cert.getSerialNumber());
/*      */       
/*  295 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)issuerSerialNumRequest);
/*  296 */       Callback[] callbacks = { (Callback)pkCallback };
/*  297 */       this._handler.handle(callbacks);
/*      */       
/*  299 */       return pkCallback.getKey();
/*  300 */     } catch (Exception e) {
/*  301 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/*      */     try {
/*  322 */       Subject subject = getSubject(context);
/*  323 */       if (subject != null) {
/*  324 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  325 */         if (set != null) {
/*  326 */           Iterator<X500PrivateCredential> it = set.iterator();
/*  327 */           while (it.hasNext()) {
/*  328 */             X500PrivateCredential cred = it.next();
/*  329 */             X509Certificate x509Cert = cred.getCertificate();
/*  330 */             BigInteger serialNo = x509Cert.getSerialNumber();
/*      */             
/*  332 */             X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/*  333 */             X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  334 */             if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */             {
/*  336 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  342 */       PrivateKeyCallback.IssuerSerialNumRequest issuerSerialNumRequest = new PrivateKeyCallback.IssuerSerialNumRequest(new X500Principal(issuerName), serialNumber);
/*      */       
/*  344 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)issuerSerialNumRequest);
/*  345 */       Callback[] callbacks = { (Callback)pkCallback };
/*  346 */       this._handler.handle(callbacks);
/*      */       
/*  348 */       return pkCallback.getKey();
/*  349 */     } catch (Exception e) {
/*  350 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getDefaultCertificate(Map context) throws XWSSecurityException {
/*  370 */     Subject subject = getSubject(context);
/*  371 */     if (subject != null) {
/*  372 */       Set<X509Certificate> set = subject.getPublicCredentials(X509Certificate.class);
/*  373 */       if (set != null && set.size() == 1) {
/*  374 */         return (X509Certificate)set.toArray()[0];
/*      */       }
/*      */     } 
/*  377 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback(null);
/*  378 */     Callback[] _callbacks = { (Callback)pkCallback };
/*      */     try {
/*  380 */       this._handler.handle(_callbacks);
/*  381 */     } catch (Exception e) {
/*  382 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  385 */     Certificate[] chain = pkCallback.getChain();
/*  386 */     if (chain == null) {
/*  387 */       throw new XWSSecurityException("Empty certificate chain returned by PrivateKeyCallback");
/*      */     }
/*      */     
/*  390 */     return (X509Certificate)chain[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean authenticateUser(Map context, String username, String password) throws XWSSecurityException {
/*  406 */     char[] pwd = (password == null) ? null : password.toCharArray();
/*      */     
/*  408 */     PasswordValidationCallback pvCallback = new PasswordValidationCallback(getRequesterSubject(context), username, pwd);
/*      */     
/*  410 */     Callback[] callbacks = { (Callback)pvCallback };
/*      */     try {
/*  412 */       this._handler.handle(callbacks);
/*  413 */     } catch (Exception e) {
/*  414 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */ 
/*      */     
/*  418 */     if (pwd != null) {
/*  419 */       pvCallback.clearPassword();
/*      */     }
/*  421 */     return pvCallback.getResult();
/*      */   }
/*      */   
/*      */   public String authenticateUser(Map context, String username) throws XWSSecurityException {
/*  425 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean authenticateUser(Map context, String username, String passwordDigest, String nonce, String created) throws XWSSecurityException {
/*  447 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean validateCertificate(X509Certificate cert, Map context) throws XWSSecurityException {
/*      */     PKIXBuilderParameters parameters;
/*      */     try {
/*  462 */       cert.checkValidity();
/*  463 */     } catch (CertificateExpiredException e) {
/*  464 */       throw new XWSSecurityException("X509Certificate Expired", e);
/*  465 */     } catch (CertificateNotYetValidException e) {
/*  466 */       throw new XWSSecurityException("X509Certificate not yet valid", e);
/*      */     } 
/*      */ 
/*      */     
/*  470 */     if (cert.getIssuerX500Principal().equals(cert.getSubjectX500Principal())) {
/*  471 */       if (isTrustedSelfSigned(cert)) {
/*  472 */         return true;
/*      */       }
/*  474 */       throw new XWSSecurityException("Validation of self signed certificate failed");
/*      */     } 
/*      */ 
/*      */     
/*  478 */     X509CertSelector certSelector = new X509CertSelector();
/*  479 */     certSelector.setCertificate(cert);
/*      */ 
/*      */     
/*  482 */     CertPathBuilder builder = null;
/*  483 */     CertPathValidator certValidator = null;
/*  484 */     CertPath certPath = null;
/*  485 */     List<Certificate> certChainList = new ArrayList<Certificate>();
/*  486 */     boolean caFound = false;
/*  487 */     Principal certChainIssuer = null;
/*  488 */     int noOfEntriesInTrustStore = 0;
/*  489 */     boolean isIssuerCertMatched = false;
/*      */     try {
/*  491 */       Callback[] callbacks = null;
/*  492 */       CertStoreCallback csCallback = null;
/*  493 */       TrustStoreCallback tsCallback = null;
/*      */       
/*  495 */       if (tsCallback == null && csCallback == null) {
/*  496 */         csCallback = new CertStoreCallback();
/*  497 */         tsCallback = new TrustStoreCallback();
/*  498 */         callbacks = new Callback[] { (Callback)csCallback, (Callback)tsCallback };
/*  499 */       } else if (csCallback == null) {
/*  500 */         csCallback = new CertStoreCallback();
/*  501 */         callbacks = new Callback[] { (Callback)csCallback };
/*  502 */       } else if (tsCallback == null) {
/*  503 */         tsCallback = new TrustStoreCallback();
/*  504 */         callbacks = new Callback[] { (Callback)tsCallback };
/*      */       } 
/*      */       
/*      */       try {
/*  508 */         this._handler.handle(callbacks);
/*  509 */       } catch (Exception e) {
/*  510 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */       
/*  513 */       parameters = new PKIXBuilderParameters(tsCallback.getTrustStore(), certSelector);
/*  514 */       parameters.setRevocationEnabled(false);
/*  515 */       if (KeyIdentifierSPI.isIBMVM) {
/*      */         
/*  517 */         CertStore cs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(Collections.singleton(cert)));
/*      */         
/*  519 */         parameters.addCertStore(cs);
/*      */       } else {
/*  521 */         parameters.addCertStore(csCallback.getCertStore());
/*      */       } 
/*      */       
/*  524 */       Certificate[] certChain = null;
/*  525 */       String certAlias = tsCallback.getTrustStore().getCertificateAlias(cert);
/*  526 */       if (certAlias != null) {
/*  527 */         certChain = tsCallback.getTrustStore().getCertificateChain(certAlias);
/*      */       }
/*  529 */       if (certChain == null) {
/*  530 */         certChainList.add(cert);
/*  531 */         certChainIssuer = cert.getIssuerX500Principal();
/*  532 */         noOfEntriesInTrustStore = tsCallback.getTrustStore().size();
/*      */       } else {
/*  534 */         certChainList = Arrays.asList(certChain);
/*      */       } 
/*  536 */       while (!caFound && noOfEntriesInTrustStore-- != 0 && certChain == null) {
/*  537 */         Enumeration<String> aliases = tsCallback.getTrustStore().aliases();
/*  538 */         while (aliases.hasMoreElements()) {
/*  539 */           String alias = aliases.nextElement();
/*  540 */           Certificate certificate = tsCallback.getTrustStore().getCertificate(alias);
/*  541 */           if (certificate == null || !"X.509".equals(certificate.getType()) || certChainList.contains(certificate)) {
/*      */             continue;
/*      */           }
/*  544 */           X509Certificate x509Cert = (X509Certificate)certificate;
/*  545 */           if (certChainIssuer.equals(x509Cert.getSubjectX500Principal())) {
/*  546 */             certChainList.add(certificate);
/*  547 */             if (x509Cert.getSubjectX500Principal().equals(x509Cert.getIssuerX500Principal())) {
/*  548 */               caFound = true;
/*      */               break;
/*      */             } 
/*  551 */             certChainIssuer = x509Cert.getIssuerDN();
/*  552 */             if (!isIssuerCertMatched) {
/*  553 */               isIssuerCertMatched = true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  560 */         if (!caFound) {
/*  561 */           if (!isIssuerCertMatched) {
/*      */             break;
/*      */           }
/*  564 */           isIssuerCertMatched = false;
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/*  569 */         CertificateFactory cf = CertificateFactory.getInstance("X.509");
/*  570 */         certPath = cf.generateCertPath(certChainList);
/*  571 */         certValidator = CertPathValidator.getInstance("PKIX");
/*  572 */       } catch (Exception e) {
/*  573 */         throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
/*      */       } 
/*  575 */     } catch (Exception e) {
/*      */       
/*  577 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*      */     try {
/*  581 */       certValidator.validate(certPath, parameters);
/*  582 */     } catch (Exception e) {
/*      */       
/*  584 */       return false;
/*      */     } 
/*      */     
/*  587 */     return true;
/*      */   }
/*      */   
/*      */   private boolean isTrustedSelfSigned(X509Certificate cert) throws XWSSecurityException {
/*      */     try {
/*  592 */       Callback[] callbacks = null;
/*  593 */       CertStoreCallback csCallback = null;
/*  594 */       TrustStoreCallback tsCallback = null;
/*      */       
/*  596 */       if (tsCallback == null && csCallback == null) {
/*  597 */         csCallback = new CertStoreCallback();
/*  598 */         tsCallback = new TrustStoreCallback();
/*  599 */         callbacks = new Callback[] { (Callback)csCallback, (Callback)tsCallback };
/*  600 */       } else if (csCallback == null) {
/*  601 */         csCallback = new CertStoreCallback();
/*  602 */         callbacks = new Callback[] { (Callback)csCallback };
/*  603 */       } else if (tsCallback == null) {
/*  604 */         tsCallback = new TrustStoreCallback();
/*  605 */         callbacks = new Callback[] { (Callback)tsCallback };
/*      */       } 
/*      */       
/*      */       try {
/*  609 */         this._handler.handle(callbacks);
/*  610 */       } catch (Exception e) {
/*  611 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */       
/*  614 */       if (tsCallback.getTrustStore() == null) {
/*  615 */         return false;
/*      */       }
/*      */       
/*  618 */       Enumeration<String> aliases = tsCallback.getTrustStore().aliases();
/*  619 */       while (aliases.hasMoreElements()) {
/*  620 */         String alias = aliases.nextElement();
/*  621 */         Certificate certificate = tsCallback.getTrustStore().getCertificate(alias);
/*  622 */         if (certificate == null || !"X.509".equals(certificate.getType())) {
/*      */           continue;
/*      */         }
/*  625 */         X509Certificate x509Cert = (X509Certificate)certificate;
/*  626 */         if (x509Cert != null && x509Cert.equals(cert)) {
/*  627 */           return true;
/*      */         }
/*      */       } 
/*  630 */       return false;
/*  631 */     } catch (Exception e) {
/*  632 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getMatchingCertificate(Map context, byte[] keyIdMatch) throws XWSSecurityException {
/*  644 */     Subject subject = getSubject(context);
/*  645 */     if (subject != null) {
/*  646 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  647 */       if (set != null) {
/*  648 */         Iterator<X500PrivateCredential> it = set.iterator();
/*  649 */         while (it.hasNext()) {
/*  650 */           X500PrivateCredential cred = it.next();
/*  651 */           X509Certificate cert = cred.getCertificate();
/*  652 */           if (matchesKeyIdentifier(keyIdMatch, cert)) {
/*  653 */             return cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  658 */     PrivateKeyCallback.SubjectKeyIDRequest subjectKeyIDRequest = new PrivateKeyCallback.SubjectKeyIDRequest(keyIdMatch);
/*      */     
/*  660 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)subjectKeyIDRequest);
/*  661 */     TrustStoreCallback tsCallback = new TrustStoreCallback();
/*      */     
/*  663 */     Callback[] callbacks = { (Callback)pkCallback, (Callback)tsCallback };
/*      */     
/*      */     try {
/*  666 */       this._handler.handle(callbacks);
/*  667 */     } catch (Exception e) {
/*  668 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  671 */     Certificate[] chain = pkCallback.getChain();
/*  672 */     if (chain != null) {
/*  673 */       for (int i = 0; i < chain.length; i++) {
/*  674 */         X509Certificate x509Cert = (X509Certificate)chain[i];
/*  675 */         if (matchesKeyIdentifier(keyIdMatch, x509Cert)) {
/*  676 */           return x509Cert;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  682 */     KeyStore trustStore = tsCallback.getTrustStore();
/*  683 */     if (trustStore != null) {
/*  684 */       X509Certificate otherPartyCert = getMatchingCertificate(keyIdMatch, trustStore);
/*  685 */       if (otherPartyCert != null) return otherPartyCert;
/*      */     
/*      */     } 
/*      */     
/*  689 */     throw new XWSSecurityException("No Matching Certificate for :" + Arrays.toString(keyIdMatch) + " found in KeyStore or TrustStore");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getMatchingCertificate(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/*  698 */     Subject subject = getSubject(context);
/*  699 */     if (subject != null) {
/*  700 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  701 */       if (set != null) {
/*  702 */         Iterator<X500PrivateCredential> it = set.iterator();
/*  703 */         while (it.hasNext()) {
/*  704 */           X500PrivateCredential cred = it.next();
/*  705 */           X509Certificate x509Cert = cred.getCertificate();
/*  706 */           BigInteger serialNo = x509Cert.getSerialNumber();
/*      */           
/*  708 */           X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/*  709 */           X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  710 */           if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */           {
/*  712 */             return x509Cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  718 */     PrivateKeyCallback.IssuerSerialNumRequest issuerSerialNumRequest = new PrivateKeyCallback.IssuerSerialNumRequest(new X500Principal(issuerName), serialNumber);
/*      */ 
/*      */     
/*  721 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)issuerSerialNumRequest);
/*  722 */     TrustStoreCallback tsCallback = new TrustStoreCallback();
/*      */     
/*  724 */     Callback[] callbacks = { (Callback)pkCallback, (Callback)tsCallback };
/*      */     
/*      */     try {
/*  727 */       this._handler.handle(callbacks);
/*  728 */     } catch (Exception e) {
/*  729 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  732 */     Certificate[] chain = pkCallback.getChain();
/*  733 */     if (chain != null) {
/*  734 */       for (int i = 0; i < chain.length; i++) {
/*  735 */         X509Certificate x509Cert = (X509Certificate)chain[i];
/*  736 */         if (matchesIssuerSerialAndName(serialNumber, issuerName, x509Cert))
/*      */         {
/*      */ 
/*      */           
/*  740 */           return x509Cert;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  748 */     KeyStore trustStore = tsCallback.getTrustStore();
/*  749 */     if (trustStore != null) {
/*  750 */       X509Certificate otherPartyCert = getMatchingCertificate(serialNumber, issuerName, trustStore);
/*      */ 
/*      */       
/*  753 */       if (otherPartyCert != null) return otherPartyCert;
/*      */     
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  759 */     throw new XWSSecurityException("No Matching Certificate for : found in KeyStore or TrustStore");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getMatchingCertificate(Map context, byte[] keyIdMatch, String valueType) throws XWSSecurityException {
/*  772 */     if ("Identifier".equals(valueType)) {
/*  773 */       return getMatchingCertificate(context, keyIdMatch);
/*      */     }
/*      */     
/*  776 */     Subject subject = getSubject(context);
/*  777 */     if (subject != null) {
/*  778 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  779 */       if (set != null) {
/*  780 */         Iterator<X500PrivateCredential> it = set.iterator();
/*  781 */         while (it.hasNext()) {
/*  782 */           X500PrivateCredential cred = it.next();
/*  783 */           X509Certificate cert = cred.getCertificate();
/*  784 */           if (matchesThumbPrint(keyIdMatch, cert)) {
/*  785 */             return cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  792 */     PrivateKeyCallback.SubjectKeyIDRequest subjectKeyIDRequest = new PrivateKeyCallback.SubjectKeyIDRequest(keyIdMatch);
/*  793 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)subjectKeyIDRequest);
/*  794 */     TrustStoreCallback tsCallback = new TrustStoreCallback();
/*      */     
/*  796 */     Callback[] callbacks = { (Callback)pkCallback, (Callback)tsCallback };
/*      */     
/*      */     try {
/*  799 */       this._handler.handle(callbacks);
/*  800 */     } catch (Exception e) {
/*  801 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  804 */     Certificate[] chain = pkCallback.getChain();
/*  805 */     if (chain != null) {
/*  806 */       for (int i = 0; i < chain.length; i++) {
/*  807 */         X509Certificate x509Cert = (X509Certificate)chain[i];
/*  808 */         if (matchesThumbPrint(keyIdMatch, x509Cert)) {
/*  809 */           return x509Cert;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  815 */     KeyStore trustStore = tsCallback.getTrustStore();
/*  816 */     if (trustStore != null) {
/*  817 */       X509Certificate otherPartyCert = getMatchingCertificate(keyIdMatch, trustStore, valueType);
/*  818 */       if (otherPartyCert != null) return otherPartyCert;
/*      */     
/*      */     } 
/*      */     
/*  822 */     throw new XWSSecurityException("No Matching Certificate for :" + Arrays.toString(keyIdMatch) + " found in KeyStore or TrustStore");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SecretKey getSecretKey(Map context, String alias, boolean encryptMode) throws XWSSecurityException {
/*  833 */     SecretKeyCallback.AliasRequest aliasRequest = new SecretKeyCallback.AliasRequest(alias);
/*  834 */     SecretKeyCallback skCallback = new SecretKeyCallback((SecretKeyCallback.Request)aliasRequest);
/*  835 */     Callback[] callbacks = { (Callback)skCallback };
/*      */     try {
/*  837 */       this._handler.handle(callbacks);
/*  838 */     } catch (Exception e) {
/*  839 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  842 */     return skCallback.getKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, String alias, boolean forSigning) throws XWSSecurityException {
/*  847 */     X509Certificate cert = null;
/*      */     try {
/*  849 */       PrivateKeyCallback pkCallback = null;
/*  850 */       if (forSigning) {
/*      */         try {
/*  852 */           Subject subject = getSubject(context);
/*  853 */           if (subject != null) {
/*  854 */             Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  855 */             if (set != null) {
/*  856 */               Iterator<X500PrivateCredential> it = set.iterator();
/*  857 */               while (it.hasNext()) {
/*  858 */                 X500PrivateCredential cred = it.next();
/*  859 */                 if (cred.getAlias().equals(alias)) {
/*  860 */                   return cred.getCertificate();
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*  865 */           PrivateKeyCallback.AliasRequest aliasRequest = new PrivateKeyCallback.AliasRequest(alias);
/*  866 */           pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)aliasRequest);
/*  867 */           Callback[] callbacks = { (Callback)pkCallback };
/*  868 */           this._handler.handle(callbacks);
/*  869 */         } catch (Exception e) {
/*  870 */           throw new XWSSecurityException(e);
/*      */         } 
/*      */         
/*  873 */         Certificate[] chain = pkCallback.getChain();
/*  874 */         if (chain != null) {
/*  875 */           cert = (X509Certificate)chain[0];
/*      */         }
/*      */       } else {
/*      */         
/*  879 */         TrustStoreCallback tsCallback = new TrustStoreCallback();
/*  880 */         Callback[] _callbacks = { (Callback)tsCallback };
/*  881 */         this._handler.handle(_callbacks);
/*      */ 
/*      */         
/*  884 */         cert = getDynamicCertificate(context, tsCallback.getTrustStore());
/*      */ 
/*      */         
/*  887 */         if (cert == null && 
/*  888 */           tsCallback.getTrustStore() != null) {
/*  889 */           cert = (X509Certificate)tsCallback.getTrustStore().getCertificate(alias);
/*      */         }
/*      */       }
/*      */     
/*  893 */     } catch (Exception e) {
/*  894 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  897 */     if (cert == null) {
/*  898 */       throw new XWSSecurityException("Unable to locate certificate for the alias '" + alias + "'");
/*      */     }
/*      */ 
/*      */     
/*  902 */     return cert;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesKeyIdentifier(byte[] keyIdMatch, X509Certificate x509Cert) throws XWSSecurityException {
/*  909 */     byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/*  910 */     if (keyId == null)
/*      */     {
/*  912 */       return false;
/*      */     }
/*      */     
/*  915 */     if (Arrays.equals(keyIdMatch, keyId)) {
/*  916 */       return true;
/*      */     }
/*  918 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] getThumbprintIdentifier(X509Certificate cert) throws XWSSecurityException {
/*  924 */     byte[] thumbPrintIdentifier = null;
/*      */     
/*      */     try {
/*  927 */       thumbPrintIdentifier = MessageDigest.getInstance("SHA-1").digest(cert.getEncoded());
/*  928 */     } catch (NoSuchAlgorithmException ex) {
/*  929 */       throw new XWSSecurityException("Digest algorithm SHA-1 not found");
/*  930 */     } catch (CertificateEncodingException ex) {
/*  931 */       throw new XWSSecurityException("Error while getting certificate's raw content");
/*      */     } 
/*  933 */     return thumbPrintIdentifier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesThumbPrint(byte[] keyIdMatch, X509Certificate x509Cert) throws XWSSecurityException {
/*  940 */     byte[] keyId = getThumbprintIdentifier(x509Cert);
/*  941 */     if (keyId == null)
/*      */     {
/*  943 */       return false;
/*      */     }
/*      */     
/*  946 */     if (Arrays.equals(keyIdMatch, keyId)) {
/*  947 */       return true;
/*      */     }
/*  949 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(byte[] keyIdMatch, KeyStore kStore) throws XWSSecurityException {
/*  957 */     if (kStore == null) {
/*  958 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  962 */       Enumeration<String> enum1 = kStore.aliases();
/*  963 */       while (enum1.hasMoreElements()) {
/*  964 */         String alias = enum1.nextElement();
/*      */         
/*  966 */         Certificate cert = kStore.getCertificate(alias);
/*  967 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/*      */         
/*  971 */         X509Certificate x509Cert = (X509Certificate)cert;
/*  972 */         byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/*  973 */         if (keyId == null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  978 */         if (Arrays.equals(keyIdMatch, keyId)) {
/*  979 */           return x509Cert;
/*      */         }
/*      */       } 
/*  982 */     } catch (KeyStoreException kEx) {
/*  983 */       throw new XWSSecurityException(kEx);
/*      */     } 
/*  985 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(byte[] keyIdMatch, KeyStore kStore, String valueType) throws XWSSecurityException {
/*  994 */     if ("Identifier".equals(valueType)) {
/*  995 */       return getMatchingCertificate(keyIdMatch, kStore);
/*      */     }
/*      */ 
/*      */     
/*  999 */     if (kStore == null) {
/* 1000 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1004 */       Enumeration<String> enum1 = kStore.aliases();
/* 1005 */       while (enum1.hasMoreElements()) {
/* 1006 */         String alias = enum1.nextElement();
/*      */         
/* 1008 */         Certificate cert = kStore.getCertificate(alias);
/* 1009 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1013 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1014 */         byte[] keyId = getThumbprintIdentifier(x509Cert);
/*      */         
/* 1016 */         if (Arrays.equals(keyIdMatch, keyId)) {
/* 1017 */           return x509Cert;
/*      */         }
/*      */       } 
/* 1020 */     } catch (KeyStoreException kEx) {
/* 1021 */       throw new XWSSecurityException(kEx);
/*      */     } 
/* 1023 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesIssuerSerialAndName(BigInteger serialNumberMatch, String issuerNameMatch, X509Certificate x509Cert) {
/* 1031 */     BigInteger serialNumber = x509Cert.getSerialNumber();
/*      */     
/* 1033 */     X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1034 */     X500Principal issuerPrincipal = new X500Principal(issuerNameMatch);
/*      */     
/* 1036 */     if (serialNumber.equals(serialNumberMatch) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */     {
/* 1038 */       return true;
/*      */     }
/*      */     
/* 1041 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(BigInteger serialNumber, String issuerName, KeyStore kStore) throws XWSSecurityException {
/* 1050 */     if (kStore == null) {
/* 1051 */       return null;
/*      */     }
/*      */     try {
/* 1054 */       Enumeration<String> enum1 = kStore.aliases();
/* 1055 */       while (enum1.hasMoreElements()) {
/* 1056 */         String alias = enum1.nextElement();
/*      */         
/* 1058 */         Certificate cert = kStore.getCertificate(alias);
/* 1059 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1063 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1064 */         BigInteger serialNo = x509Cert.getSerialNumber();
/*      */         
/* 1066 */         X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1067 */         X500Principal issuerPrincipal = new X500Principal(issuerName);
/* 1068 */         if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */         {
/* 1070 */           return x509Cert;
/*      */         }
/*      */       } 
/* 1073 */     } catch (KeyStoreException kEx) {
/* 1074 */       throw new XWSSecurityException(kEx);
/*      */     } 
/* 1076 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final String username, final String password) {
/* 1083 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 1086 */             String x500Name = "CN=" + username;
/* 1087 */             Principal principal = new X500Principal(x500Name);
/* 1088 */             subject.getPrincipals().add(principal);
/* 1089 */             subject.getPrivateCredentials().add(password);
/* 1090 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final X509Certificate cert) {
/* 1099 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 1102 */             Principal principal = cert.getSubjectX500Principal();
/* 1103 */             subject.getPrincipals().add(principal);
/* 1104 */             subject.getPublicCredentials().add(cert);
/* 1105 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, Assertion assertion) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/* 1121 */     return getMatchingCertificate(context, serialNumber, issuerName).getPublicKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(String keyIdentifier) throws XWSSecurityException {
/*      */     try {
/* 1127 */       return getMatchingCertificate((Map)null, getDecodedBase64EncodedData(keyIdentifier)).getPublicKey();
/*      */     
/*      */     }
/* 1130 */     catch (Exception e) {
/* 1131 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*      */     try {
/* 1138 */       return getMatchingCertificate(context, keyIdentifier).getPublicKey();
/* 1139 */     } catch (Exception e) {
/* 1140 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/* 1146 */     return getMatchingCertificate(context, identifier, valueType).getPublicKey();
/*      */   }
/*      */ 
/*      */   
/*      */   private byte[] getDecodedBase64EncodedData(String encodedData) throws XWSSecurityException {
/*      */     try {
/* 1152 */       return Base64.decode(encodedData);
/* 1153 */     } catch (Base64DecodingException e) {
/* 1154 */       throw new SecurityHeaderException("Unable to decode Base64 encoded data", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/* 1165 */     return getMatchingCertificate(context, serialNumber, issuerName);
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(String keyIdentifier) throws XWSSecurityException {
/*      */     try {
/* 1171 */       byte[] decoded = getDecodedBase64EncodedData(keyIdentifier);
/* 1172 */       return getMatchingCertificate((Map)null, decoded);
/* 1173 */     } catch (Exception e) {
/* 1174 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, PublicKey publicKey, boolean forSign) {
/* 1179 */     return null;
/*      */   }
/*      */   
/*      */   public X509Certificate getCertificate(Map context, byte[] ski) {
/* 1183 */     return null;
/*      */   }
/*      */   
/*      */   public X509Certificate getCertificate(Map context, PublicKey publicKey, boolean forSign) throws XWSSecurityException {
/* 1187 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/* 1193 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean validateSamlIssuer(String issuer) {
/* 1198 */     return true;
/*      */   }
/*      */   
/*      */   public boolean validateSamlUser(String user, String domain, String format) {
/* 1202 */     return true;
/*      */   }
/*      */   
/*      */   public void setSubject(Subject subject, Map<String, Subject> context) {
/* 1206 */     context.put("javax.security.auth.Subject.self", subject);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRequesterSubject(Subject subject, Map<String, Subject> context) {
/* 1211 */     context.put("javax.security.auth.Subject", subject);
/*      */   }
/*      */   
/*      */   public Subject getSubject() {
/* 1215 */     return null;
/*      */   }
/*      */   
/*      */   public Subject getSubject(Map context) {
/* 1219 */     return (Subject)context.get("javax.security.auth.Subject.self");
/*      */   }
/*      */   
/*      */   public Subject getRequesterSubject(Map context) {
/* 1223 */     return (Subject)context.get("javax.security.auth.Subject");
/*      */   }
/*      */   
/*      */   private Date getGMTDateWithSkewAdjusted(Calendar c, boolean addSkew) {
/* 1227 */     long offset = c.get(15);
/* 1228 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 1229 */       offset += c.getTimeZone().getDSTSavings();
/*      */     }
/* 1231 */     long beforeTime = c.getTimeInMillis();
/* 1232 */     long currentTime = beforeTime - offset;
/*      */     
/* 1234 */     if (addSkew) {
/* 1235 */       currentTime += 360000L;
/*      */     } else {
/* 1237 */       currentTime -= 360000L;
/*      */     } 
/* 1239 */     c.setTimeInMillis(currentTime);
/* 1240 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUsername(Map context) throws XWSSecurityException {
/* 1248 */     NameCallback nameCallback = new NameCallback("Username: ");
/*      */     try {
/* 1250 */       Callback[] cbs = { nameCallback };
/* 1251 */       this._handler.handle(cbs);
/* 1252 */     } catch (Exception e) {
/* 1253 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1256 */     return nameCallback.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPassword(Map context) throws XWSSecurityException {
/* 1264 */     PasswordCallback pwdCallback = new PasswordCallback("Password: ", false);
/*      */     try {
/* 1266 */       Callback[] cbs = { pwdCallback };
/* 1267 */       this._handler.handle(cbs);
/* 1268 */     } catch (Exception e) {
/* 1269 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1272 */     if (pwdCallback.getPassword() == null) {
/* 1273 */       return null;
/*      */     }
/* 1275 */     return new String(pwdCallback.getPassword());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean validateAndCacheNonce(Map context, String nonce, String created, long maxNonceAge) throws XWSSecurityException {
/* 1280 */     NonceManager nonceMgr = null;
/* 1281 */     nonceMgr = NonceManager.getInstance(maxNonceAge, (WSEndpoint)context.get("WSEndpoint"));
/* 1282 */     return nonceMgr.validateNonce(nonce, created);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateTimestamp(Map context, String created, String expires, long maxClockSkew, long freshnessLimit) throws XWSSecurityException {
/* 1288 */     if (expiresBeforeCreated(created, expires)) {
/* 1289 */       XWSSecurityException xwsse = new XWSSecurityException("Message expired!");
/* 1290 */       throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSU_MESSAGE_EXPIRED, "Message expired!", xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1295 */     validateCreationTime(context, created, maxClockSkew, freshnessLimit);
/* 1296 */     validateExpirationTime(expires, maxClockSkew, freshnessLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateTimestamp(Map context, Timestamp timestamp, long maxClockSkew, long freshnessLimit) throws XWSSecurityException {
/* 1302 */     validateTimestamp(context, timestamp.getCreated(), timestamp.getExpires(), maxClockSkew, freshnessLimit);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean expiresBeforeCreated(String creationTime, String expirationTime) throws XWSSecurityException {
/* 1307 */     Date created = null;
/* 1308 */     Date expires = null;
/*      */     try {
/*      */       try {
/* 1311 */         synchronized (calendarFormatter1) {
/* 1312 */           created = calendarFormatter1.parse(creationTime);
/*      */         } 
/* 1314 */         if (expirationTime != null) {
/* 1315 */           synchronized (calendarFormatter1) {
/* 1316 */             expires = calendarFormatter1.parse(expirationTime);
/*      */           }
/*      */         
/*      */         }
/* 1320 */       } catch (ParseException pe) {
/* 1321 */         synchronized (calendarFormatter2) {
/* 1322 */           created = calendarFormatter2.parse(creationTime);
/*      */         } 
/* 1324 */         if (expirationTime != null) {
/* 1325 */           synchronized (calendarFormatter2) {
/* 1326 */             expires = calendarFormatter2.parse(expirationTime);
/*      */           } 
/*      */         }
/*      */       } 
/* 1330 */     } catch (ParseException pe) {
/* 1331 */       throw new XWSSecurityException(pe.getMessage());
/*      */     } 
/*      */     
/* 1334 */     if (expires != null && expires.before(created)) {
/* 1335 */       return true;
/*      */     }
/* 1337 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateCreationTime(Map context, String creationTime, long maxClockSkew, long timestampFreshnessLimit) throws XWSSecurityException {
/*      */     Date date1;
/*      */     try {
/* 1348 */       synchronized (calendarFormatter1) {
/* 1349 */         date1 = calendarFormatter1.parse(creationTime);
/*      */       } 
/* 1351 */     } catch (ParseException pe) {
/*      */       try {
/* 1353 */         synchronized (calendarFormatter2) {
/* 1354 */           date1 = calendarFormatter2.parse(creationTime);
/*      */         } 
/* 1356 */       } catch (ParseException pe1) {
/* 1357 */         throw new XWSSecurityException("Exception while parsing Creation Time :" + pe1.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1362 */     Date current = null;
/*      */     try {
/* 1364 */       current = getFreshnessAndSkewAdjustedDate(maxClockSkew, timestampFreshnessLimit);
/* 1365 */     } catch (ParseException pe) {
/* 1366 */       throw new XWSSecurityException(pe.getMessage());
/*      */     } 
/*      */     
/* 1369 */     if (date1.before(current)) {
/* 1370 */       XWSSecurityException xwsse = new XWSSecurityException("Creation Time is older than configured Timestamp Freshness Interval!");
/*      */       
/* 1372 */       throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Creation Time is older than configured Timestamp Freshness Interval!", xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1378 */     Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, true);
/*      */     
/* 1380 */     if (currentTime.before(date1)) {
/* 1381 */       XWSSecurityException xwsse = new XWSSecurityException("Creation Time ahead of Current Time!");
/* 1382 */       throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Creation Time ahead of Current Time!", xwsse);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateExpirationTime(String expirationTime, long maxClockSkew, long timestampFreshnessLimit) throws XWSSecurityException {
/* 1393 */     if (expirationTime != null) {
/*      */       Date date1;
/*      */       try {
/* 1396 */         synchronized (calendarFormatter1) {
/* 1397 */           date1 = calendarFormatter1.parse(expirationTime);
/*      */         } 
/* 1399 */       } catch (ParseException pe) {
/*      */         try {
/* 1401 */           synchronized (calendarFormatter2) {
/* 1402 */             date1 = calendarFormatter2.parse(expirationTime);
/*      */           } 
/* 1404 */         } catch (ParseException pe1) {
/* 1405 */           throw new XWSSecurityException("Exception while parsing Expiration Time :" + pe1.getMessage());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1410 */       Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, false);
/*      */       
/* 1412 */       if (date1.before(currentTime)) {
/* 1413 */         XWSSecurityException xwsse = new XWSSecurityException("Message Expired!");
/* 1414 */         throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSU_MESSAGE_EXPIRED, "Message Expired!", xwsse);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallbackHandler getCallbackHandler() throws XWSSecurityException {
/* 1424 */     return this._handler;
/*      */   }
/*      */   
/*      */   public void validateSAMLAssertion(Map context, Element assertion) throws XWSSecurityException {
/* 1428 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Element locateSAMLAssertion(Map context, Element binding, String assertionId, Document ownerDoc) throws XWSSecurityException {
/* 1434 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthenticationTokenPolicy.SAMLAssertionBinding populateSAMLPolicy(Map fpcontext, AuthenticationTokenPolicy.SAMLAssertionBinding policy, DynamicApplicationContext context) throws XWSSecurityException {
/* 1440 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date getGMTDateWithSkewAdjusted(Calendar c, long maxClockSkew, boolean addSkew) {
/* 1446 */     long offset = c.get(15);
/* 1447 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 1448 */       offset += c.getTimeZone().getDSTSavings();
/*      */     }
/* 1450 */     long beforeTime = c.getTimeInMillis();
/* 1451 */     long currentTime = beforeTime - offset;
/*      */     
/* 1453 */     if (addSkew) {
/* 1454 */       currentTime += maxClockSkew;
/*      */     } else {
/* 1456 */       currentTime -= maxClockSkew;
/*      */     } 
/* 1458 */     c.setTimeInMillis(currentTime);
/* 1459 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date getFreshnessAndSkewAdjustedDate(long maxClockSkew, long timestampFreshnessLimit) throws ParseException {
/* 1465 */     Calendar c = new GregorianCalendar();
/* 1466 */     long offset = c.get(15);
/* 1467 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 1468 */       offset += c.getTimeZone().getDSTSavings();
/*      */     }
/* 1470 */     long beforeTime = c.getTimeInMillis();
/* 1471 */     long currentTime = beforeTime - offset;
/*      */ 
/*      */     
/* 1474 */     long adjustedTime = currentTime - maxClockSkew - timestampFreshnessLimit;
/* 1475 */     c.setTimeInMillis(adjustedTime);
/*      */     
/* 1477 */     return c.getTime();
/*      */   }
/*      */ 
/*      */   
/*      */   private X509Certificate getDynamicCertificate(Map context, KeyStore trustStore) {
/* 1482 */     X509Certificate cert = null;
/*      */     
/* 1484 */     Subject requesterSubject = getRequesterSubject(context);
/* 1485 */     String keyId = (String)context.get("requester.keyid");
/* 1486 */     String issuerName = (String)context.get("requester.issuername");
/* 1487 */     BigInteger issuerSerial = (BigInteger)context.get("requester.serial");
/*      */     
/* 1489 */     if (keyId != null) {
/*      */       try {
/* 1491 */         cert = getMatchingCertificate(keyId.getBytes(), trustStore);
/* 1492 */         if (cert != null)
/* 1493 */           return cert; 
/* 1494 */       } catch (XWSSecurityException e) {}
/* 1495 */     } else if (issuerName != null && issuerSerial != null) {
/*      */       try {
/* 1497 */         cert = getMatchingCertificate(issuerSerial, issuerName, trustStore);
/* 1498 */         if (cert != null)
/* 1499 */           return cert; 
/* 1500 */       } catch (XWSSecurityException e) {}
/* 1501 */     } else if (requesterSubject != null) {
/* 1502 */       Set<Object> publicCredentials = requesterSubject.getPublicCredentials();
/* 1503 */       for (Iterator it = publicCredentials.iterator(); it.hasNext(); ) {
/* 1504 */         Object cred = it.next();
/* 1505 */         if (cred instanceof X509Certificate) {
/* 1506 */           cert = (X509Certificate)cred;
/*      */         }
/*      */       } 
/* 1509 */       if (cert != null) {
/* 1510 */         return cert;
/*      */       }
/*      */     } 
/* 1513 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subj, String encryptedKey) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, Key secretKey) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, byte[] keyIdentifier, String valueType) throws XWSSecurityException {
/* 1527 */     if ("Identifier".equals(valueType)) {
/* 1528 */       return getPrivateKey(context, keyIdentifier);
/*      */     }
/*      */     
/*      */     try {
/* 1532 */       Subject subject = getSubject(context);
/* 1533 */       if (subject != null) {
/* 1534 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 1535 */         if (set != null) {
/* 1536 */           Iterator<X500PrivateCredential> it = set.iterator();
/* 1537 */           while (it.hasNext()) {
/* 1538 */             X500PrivateCredential cred = it.next();
/* 1539 */             if (matchesThumbPrint(Base64.decode(keyIdentifier), cred.getCertificate()))
/*      */             {
/* 1541 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1549 */       PrivateKeyCallback.SubjectKeyIDRequest subjectKeyIDRequest = new PrivateKeyCallback.SubjectKeyIDRequest(keyIdentifier);
/*      */ 
/*      */       
/* 1552 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)subjectKeyIDRequest);
/* 1553 */       Callback[] callbacks = { (Callback)pkCallback };
/* 1554 */       this._handler.handle(callbacks);
/*      */       
/* 1556 */       return pkCallback.getKey();
/* 1557 */     } catch (Exception e) {
/* 1558 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void validateSAMLAssertion(Map context, XMLStreamReader assertion) throws XWSSecurityException {
/* 1564 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, XMLStreamReader assertion) {}
/*      */ 
/*      */   
/*      */   public boolean isSelfCertificate(X509Certificate cert) {
/* 1573 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, Subject bootStrapSubject) {
/* 1577 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */   
/*      */   public KerberosContext doKerberosLogin() throws XWSSecurityException {
/* 1581 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */   
/*      */   public KerberosContext doKerberosLogin(byte[] tokenValue) throws XWSSecurityException {
/* 1585 */     throw new UnsupportedOperationException("Not supported");
/*      */   }
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, GSSName clientCred, GSSCredential gssCred) {
/* 1589 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   
/*      */   class PriviledgedHandler
/*      */     implements CallbackHandler {
/* 1594 */     CallbackHandler delegate = null;
/*      */     
/*      */     public PriviledgedHandler(CallbackHandler handler) {
/* 1597 */       this.delegate = handler;
/*      */     }
/*      */     
/*      */     public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
/* 1601 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*      */               try {
/* 1605 */                 WssProviderSecurityEnvironment.PriviledgedHandler.this.delegate.handle(callbacks);
/* 1606 */                 return null;
/* 1607 */               } catch (Exception ex) {
/* 1608 */                 throw new XWSSecurityRuntimeException(ex);
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\WssProviderSecurityEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */