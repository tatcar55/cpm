/*      */ package com.sun.xml.wss.impl.misc;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*      */ import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
/*      */ import com.sun.xml.ws.api.ResourceLoader;
/*      */ import com.sun.xml.ws.api.server.Container;
/*      */ import com.sun.xml.ws.api.server.WSEndpoint;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosLogin;
/*      */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*      */ import com.sun.xml.wss.AliasSelector;
/*      */ import com.sun.xml.wss.NonceManager;
/*      */ import com.sun.xml.wss.ProcessingContext;
/*      */ import com.sun.xml.wss.RealmAuthenticationAdapter;
/*      */ import com.sun.xml.wss.SecurityEnvironment;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.Timestamp;
/*      */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.SecurityHeaderException;
/*      */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*      */ import com.sun.xml.wss.impl.callback.CertificateValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.RuntimeProperties;
/*      */ import com.sun.xml.wss.impl.callback.SAMLAssertionValidator;
/*      */ import com.sun.xml.wss.impl.callback.SAMLCallback;
/*      */ import com.sun.xml.wss.impl.callback.SAMLValidator;
/*      */ import com.sun.xml.wss.impl.callback.TimestampValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.ValidatorExtension;
/*      */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.PrivateKeyBinding;
/*      */ import com.sun.xml.wss.logging.LogStringsMessages;
/*      */ import com.sun.xml.wss.logging.impl.misc.LogStringsMessages;
/*      */ import com.sun.xml.wss.saml.Assertion;
/*      */ import com.sun.xml.wss.util.XWSSUtil;
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.security.AccessController;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.CertPath;
/*      */ import java.security.cert.CertPathValidator;
/*      */ import java.security.cert.CertSelector;
/*      */ import java.security.cert.CertStore;
/*      */ import java.security.cert.CertStoreException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateExpiredException;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.CertificateNotYetValidException;
/*      */ import java.security.cert.PKIXBuilderParameters;
/*      */ import java.security.cert.X509CertSelector;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Timer;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.NameCallback;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.kerberos.KerberosPrincipal;
/*      */ import javax.security.auth.login.LoginContext;
/*      */ import javax.security.auth.login.LoginException;
/*      */ import javax.security.auth.message.callback.CallerPrincipalCallback;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSITProviderSecurityEnvironment
/*      */   implements SecurityEnvironment
/*      */ {
/*      */   private Map _securityOptions;
/*      */   private CallbackHandler _handler;
/*  186 */   protected final long MAX_CLOCK_SKEW = 300000L;
/*      */ 
/*      */   
/*  189 */   protected final long TIMESTAMP_FRESHNESS_LIMIT = 300000L;
/*      */ 
/*      */   
/*  192 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*      */   
/*      */   public static final String USERNAME_CBH = "username.callback.handler";
/*      */   
/*      */   public static final String PASSWORD_CBH = "password.callback.handler";
/*  197 */   private static final SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*      */   
/*  199 */   private static final SimpleDateFormat calendarFormatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
/*      */ 
/*      */ 
/*      */   
/*  203 */   NonceCache nonceCache = null;
/*      */   
/*      */   static final boolean USE_DAEMON_THREAD = true;
/*      */   
/*  207 */   static final Timer nonceCleanupTimer = new Timer(true);
/*      */   
/*      */   private String myAlias;
/*      */   private String keyPwd;
/*      */   private String peerEntityAlias;
/*      */   private String myUsername;
/*      */   private String myPassword;
/*      */   private String samlCBH;
/*      */   private String sV;
/*      */   private Class samlCbHandler;
/*  217 */   private CallbackHandler samlHandler = null;
/*      */   private Class samlValidator;
/*      */   private SAMLAssertionValidator sValidator;
/*  220 */   private String krbLoginModule = null;
/*  221 */   private String krbServicePrincipal = null;
/*      */   
/*      */   private boolean krbCredentialDelegation = false;
/*      */   private Class usernameCbHandler;
/*      */   private Class passwordCbHandler;
/*      */   private String mcs;
/*      */   private String tfl;
/*      */   private String mna;
/*      */   protected long maxClockSkewG;
/*      */   protected long timestampFreshnessLimitG;
/*  231 */   protected long maxNonceAge = 900000L;
/*      */   
/*      */   private boolean isAppClient = true;
/*      */   
/*  235 */   private X509Certificate selfCertificate = null;
/*      */   
/*      */   private String certSelectorClassName;
/*      */   
/*      */   private String crlSelectorClassName;
/*      */   
/*      */   private Class certSelectorClass;
/*      */   
/*      */   private Class crlSelectorClass;
/*      */   
/*      */   protected String revocationEnabledAttr;
/*      */   protected boolean revocationEnabled = false;
/*      */   private String keystoreCertSelectorClassName;
/*      */   private String truststoreCertSelectorClassName;
/*      */   private Class keystoreCertSelectorClass;
/*      */   private Class truststoreCertSelectorClass;
/*  251 */   private Container container = null;
/*      */   
/*      */   private String useXWSSCallbacksStr;
/*      */   
/*      */   private boolean useXWSSCallbacks = false;
/*      */   
/*      */   private CertificateValidationCallback.CertificateValidator certValidator;
/*      */   private Class certificateValidator;
/*      */   private Class usernameValidator;
/*      */   private Class timestampValidator;
/*      */   private PasswordValidationCallback.PasswordValidator pwValidator;
/*      */   private TimestampValidationCallback.TimestampValidator tsValidator;
/*      */   private String jaasLoginModuleForKeystore;
/*      */   private Subject loginContextSubjectForKeystore;
/*      */   private String keyStoreCBH;
/*      */   private CallbackHandler keystoreCbHandlerClass;
/*      */   private CallbackHandler usernameHandler;
/*      */   private CallbackHandler passwordHandler;
/*      */   
/*      */   public WSITProviderSecurityEnvironment(CallbackHandler handler, Map options, Properties configAssertions) throws XWSSecurityException {
/*  271 */     this._handler = new PriviledgedHandler(handler);
/*  272 */     this._securityOptions = options;
/*      */     
/*  274 */     if (this._securityOptions != null) {
/*  275 */       String mo_aliases = (String)this._securityOptions.get("ALIASES");
/*  276 */       String mo_keypwds = (String)this._securityOptions.get("PASSWORDS");
/*      */       
/*  278 */       if (mo_aliases != null && mo_keypwds != null) {
/*  279 */         StringTokenizer aliases = new StringTokenizer(mo_aliases, " ");
/*  280 */         StringTokenizer keypwds = new StringTokenizer(mo_keypwds, " ");
/*  281 */         if (aliases.countTokens() != keypwds.countTokens());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  289 */       this.container = (Container)this._securityOptions.get("CONTAINER");
/*      */     } 
/*      */ 
/*      */     
/*  293 */     this.myAlias = configAssertions.getProperty("my.alias");
/*  294 */     this.keyPwd = configAssertions.getProperty("key.password");
/*  295 */     this.peerEntityAlias = configAssertions.getProperty("peerentity.alias");
/*      */     
/*  297 */     this.krbLoginModule = configAssertions.getProperty("krb5.login.module");
/*  298 */     this.krbServicePrincipal = configAssertions.getProperty("krb5.service.principal");
/*  299 */     this.krbCredentialDelegation = Boolean.valueOf(configAssertions.getProperty("krb5.credential.delegation")).booleanValue();
/*      */     
/*  301 */     String uCBH = configAssertions.getProperty("username.callback.handler");
/*  302 */     String pCBH = configAssertions.getProperty("password.callback.handler");
/*      */     
/*  304 */     this.myUsername = configAssertions.getProperty("my.username");
/*  305 */     this.myPassword = configAssertions.getProperty("my.password");
/*  306 */     this.samlCBH = configAssertions.getProperty("saml.callback.handler");
/*  307 */     if (this.samlCBH != null) {
/*  308 */       this.samlCbHandler = loadClass(this.samlCBH);
/*      */     }
/*  310 */     if (this.samlCbHandler != null) {
/*      */       try {
/*  312 */         this.samlHandler = this.samlCbHandler.newInstance();
/*  313 */       } catch (InstantiationException ex) {
/*  314 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0715_EXCEPTION_CREATING_NEWINSTANCE(), ex);
/*  315 */         throw new XWSSecurityException(ex);
/*  316 */       } catch (IllegalAccessException ex) {
/*  317 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0715_EXCEPTION_CREATING_NEWINSTANCE(), ex);
/*  318 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */     }
/*      */     
/*  322 */     this.sV = configAssertions.getProperty("saml.validator");
/*  323 */     if (this.sV != null) {
/*  324 */       this.samlValidator = loadClass(this.sV);
/*      */     }
/*      */     
/*  327 */     if (this.samlValidator != null) {
/*      */       try {
/*  329 */         this.sValidator = this.samlValidator.newInstance();
/*  330 */       } catch (InstantiationException ex) {
/*  331 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0715_EXCEPTION_CREATING_NEWINSTANCE(), ex);
/*  332 */         throw new XWSSecurityException(ex);
/*  333 */       } catch (IllegalAccessException ex) {
/*  334 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0715_EXCEPTION_CREATING_NEWINSTANCE(), ex);
/*  335 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */     }
/*      */     
/*  339 */     this.mcs = configAssertions.getProperty("max.clock.skew");
/*  340 */     this.tfl = configAssertions.getProperty("timestamp.freshness.limit");
/*  341 */     this.mna = configAssertions.getProperty("max.nonce.age");
/*      */     
/*  343 */     this.revocationEnabledAttr = configAssertions.getProperty("revocation.enabled");
/*  344 */     if (this.revocationEnabledAttr != null) {
/*  345 */       this.revocationEnabled = Boolean.parseBoolean(this.revocationEnabledAttr);
/*      */     }
/*  347 */     this.maxClockSkewG = SecurityUtil.toLong(this.mcs);
/*  348 */     this.timestampFreshnessLimitG = SecurityUtil.toLong(this.tfl);
/*  349 */     if (this.mna != null) {
/*  350 */       this.maxNonceAge = SecurityUtil.toLong(this.mna);
/*      */     }
/*      */     
/*  353 */     this.useXWSSCallbacksStr = configAssertions.getProperty("user.xwss.callbacks");
/*  354 */     if (this.useXWSSCallbacksStr != null) {
/*  355 */       this.useXWSSCallbacks = Boolean.parseBoolean(this.useXWSSCallbacksStr);
/*      */     }
/*  357 */     String cV = configAssertions.getProperty("certificate.validator");
/*  358 */     this.certificateValidator = loadClass(cV);
/*  359 */     String uV = configAssertions.getProperty("username.validator");
/*  360 */     String tV = configAssertions.getProperty("timestamp.validator");
/*  361 */     this.usernameValidator = loadClass(uV);
/*  362 */     this.timestampValidator = loadClass(tV);
/*  363 */     this.usernameCbHandler = loadClass(uCBH);
/*  364 */     this.passwordCbHandler = loadClass(pCBH);
/*      */     
/*      */     try {
/*  367 */       if (this.certificateValidator != null) {
/*  368 */         this.certValidator = this.certificateValidator.newInstance();
/*      */       }
/*  370 */       if (this.usernameValidator != null) {
/*  371 */         this.pwValidator = this.usernameValidator.newInstance();
/*      */       }
/*  373 */       if (this.timestampValidator != null) {
/*  374 */         this.tsValidator = this.timestampValidator.newInstance();
/*      */       }
/*  376 */     } catch (Exception e) {
/*  377 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1523_ERROR_GETTING_NEW_INSTANCE_CALLBACK_HANDLER(), e);
/*  378 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */ 
/*      */     
/*  382 */     NameCallback nameCallback = new NameCallback("Username: ");
/*      */     try {
/*  384 */       Callback[] cbs = { nameCallback };
/*  385 */       this._handler.handle(cbs);
/*  386 */     } catch (UnsupportedCallbackException e) {
/*  387 */       this.isAppClient = false;
/*  388 */     } catch (Exception e) {
/*  389 */       this.isAppClient = false;
/*      */     } 
/*      */     
/*  392 */     this.certSelectorClassName = configAssertions.getProperty("certstore.certselector");
/*  393 */     this.crlSelectorClassName = configAssertions.getProperty("certstore.crlselector");
/*  394 */     this.certSelectorClass = loadClass(this.certSelectorClassName);
/*  395 */     this.crlSelectorClass = loadClass(this.crlSelectorClassName);
/*      */     
/*  397 */     this.keystoreCertSelectorClassName = configAssertions.getProperty("keystore.certselector");
/*  398 */     this.truststoreCertSelectorClassName = configAssertions.getProperty("truststore.certselector");
/*  399 */     this.keystoreCertSelectorClass = loadClass(this.keystoreCertSelectorClassName);
/*  400 */     this.truststoreCertSelectorClass = loadClass(this.truststoreCertSelectorClassName);
/*      */     
/*  402 */     this.jaasLoginModuleForKeystore = configAssertions.getProperty("jaas.loginmodule.for.keystore");
/*  403 */     this.keyStoreCBH = configAssertions.getProperty("keystore.callback.handler");
/*  404 */     this.loginContextSubjectForKeystore = initJAASKeyStoreLoginModule();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, String alias) throws XWSSecurityException {
/*  430 */     if (alias == null && context != null) {
/*  431 */       Object obj = context.get("privatekey");
/*  432 */       PrivateKey key = null;
/*  433 */       if (obj instanceof PrivateKey) {
/*  434 */         key = (PrivateKey)obj;
/*  435 */         return key;
/*      */       } 
/*      */     } 
/*  438 */     PrivateKey privateKey = null;
/*      */     try {
/*  440 */       PrivateKeyCallback.AliasRequest aliasRequest = new PrivateKeyCallback.AliasRequest(alias);
/*      */       
/*  442 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)aliasRequest);
/*  443 */       Callback[] callbacks = null;
/*  444 */       if (this.useXWSSCallbacks) {
/*  445 */         RuntimeProperties props = new RuntimeProperties(context);
/*  446 */         callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */       } else {
/*  448 */         callbacks = new Callback[] { (Callback)pkCallback };
/*      */       } 
/*  450 */       this._handler.handle(callbacks);
/*  451 */       privateKey = pkCallback.getKey();
/*  452 */     } catch (Exception e) {
/*  453 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.AliasRequest"), new Object[] { "PrivateKeyCallback.AliasRequest" });
/*      */       
/*  455 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  456 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  459 */     if (privateKey == null) {
/*  460 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0222_CANNOT_LOCATE_PRIVKEY(alias), new Object[] { alias });
/*  461 */       throw new XWSSecurityException("Unable to locate private key for the alias: " + alias);
/*      */     } 
/*      */ 
/*      */     
/*  465 */     return privateKey;
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
/*      */   public PrivateKey getPrivateKey(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*  483 */     if (context != null) {
/*  484 */       Object obj = context.get("privatekey");
/*  485 */       PrivateKey key = null;
/*  486 */       if (obj instanceof PrivateKey) {
/*  487 */         key = (PrivateKey)obj;
/*  488 */         Object cert = context.get("certificate");
/*  489 */         if (XWSSUtil.matchesProgrammaticInfo(cert, keyIdentifier, "Identifier") != null) {
/*  490 */           return key;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  499 */       Subject subject = getSubject(context);
/*  500 */       if (subject != null) {
/*  501 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  502 */         if (set != null) {
/*  503 */           Iterator<X500PrivateCredential> it = set.iterator();
/*  504 */           while (it.hasNext()) {
/*  505 */             X500PrivateCredential cred = it.next();
/*  506 */             if (matchesKeyIdentifier(Base64.decode(keyIdentifier), cred.getCertificate()))
/*      */             {
/*  508 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  513 */       PrivateKeyCallback.SubjectKeyIDRequest subjectKeyIDRequest = new PrivateKeyCallback.SubjectKeyIDRequest(keyIdentifier);
/*      */       
/*  515 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)subjectKeyIDRequest);
/*  516 */       Callback[] callbacks = null;
/*  517 */       if (this.useXWSSCallbacks) {
/*  518 */         RuntimeProperties props = new RuntimeProperties(context);
/*  519 */         callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */       } else {
/*  521 */         callbacks = new Callback[] { (Callback)pkCallback };
/*      */       } 
/*  523 */       this._handler.handle(callbacks);
/*      */       
/*  525 */       return pkCallback.getKey();
/*  526 */     } catch (Exception e) {
/*  527 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.SubjectKeyIDRequest"), new Object[] { "PrivateKeyCallback.SubjectKeyIDRequest" });
/*      */       
/*  529 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  530 */       throw new XWSSecurityException(e);
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
/*  549 */       if (context != null) {
/*  550 */         Object obj = context.get("certificate");
/*  551 */         if (cert != null && cert.equals(obj)) {
/*  552 */           Object key = context.get("privatekey");
/*  553 */           if (key != null && key instanceof PrivateKey) {
/*  554 */             return (PrivateKey)key;
/*      */           }
/*      */         } 
/*      */       } 
/*  558 */       Subject subject = getSubject(context);
/*  559 */       if (subject != null) {
/*  560 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  561 */         if (set != null) {
/*  562 */           String issuerName = RFC2253Parser.normalize(cert.getIssuerDN().getName());
/*      */           
/*  564 */           Iterator<X500PrivateCredential> it = set.iterator();
/*  565 */           while (it.hasNext()) {
/*  566 */             X500PrivateCredential cred = it.next();
/*  567 */             X509Certificate x509Cert = cred.getCertificate();
/*  568 */             BigInteger serialNo = x509Cert.getSerialNumber();
/*  569 */             X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/*  570 */             X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  571 */             if (serialNo.equals(cert.getSerialNumber()) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */             {
/*  573 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  579 */       PrivateKeyCallback.IssuerSerialNumRequest issuerSerialNumRequest = new PrivateKeyCallback.IssuerSerialNumRequest(cert.getIssuerX500Principal(), cert.getSerialNumber());
/*      */ 
/*      */       
/*  582 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)issuerSerialNumRequest);
/*  583 */       Callback[] callbacks = null;
/*  584 */       if (this.useXWSSCallbacks) {
/*  585 */         RuntimeProperties props = new RuntimeProperties(context);
/*  586 */         callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */       } else {
/*  588 */         callbacks = new Callback[] { (Callback)pkCallback };
/*      */       } 
/*  590 */       this._handler.handle(callbacks);
/*      */       
/*  592 */       return pkCallback.getKey();
/*  593 */     } catch (Exception e) {
/*  594 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.IssuerSerialNumRequest"), new Object[] { "PrivateKeyCallback.IssuerSerialNumRequest" });
/*      */       
/*  596 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  597 */       throw new XWSSecurityException(e);
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
/*  618 */       if (context != null) {
/*  619 */         Object obj = context.get("certificate");
/*  620 */         if (XWSSUtil.matchesProgrammaticInfo(obj, serialNumber, issuerName) != null) {
/*  621 */           PrivateKey key = XWSSUtil.getProgrammaticPrivateKey(context);
/*  622 */           if (key != null) {
/*  623 */             return key;
/*      */           }
/*      */         } 
/*      */       } 
/*  627 */       Subject subject = getSubject(context);
/*  628 */       if (subject != null) {
/*  629 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/*  630 */         if (set != null) {
/*  631 */           Iterator<X500PrivateCredential> it = set.iterator();
/*  632 */           while (it.hasNext()) {
/*  633 */             X500PrivateCredential cred = it.next();
/*  634 */             X509Certificate x509Cert = cred.getCertificate();
/*  635 */             BigInteger serialNo = x509Cert.getSerialNumber();
/*      */             
/*  637 */             X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/*  638 */             X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  639 */             if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */             {
/*  641 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  647 */       PrivateKeyCallback.IssuerSerialNumRequest issuerSerialNumRequest = new PrivateKeyCallback.IssuerSerialNumRequest(new X500Principal(issuerName), serialNumber);
/*      */ 
/*      */       
/*  650 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)issuerSerialNumRequest);
/*  651 */       Callback[] callbacks = null;
/*  652 */       if (this.useXWSSCallbacks) {
/*  653 */         RuntimeProperties props = new RuntimeProperties(context);
/*  654 */         callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */       } else {
/*  656 */         callbacks = new Callback[] { (Callback)pkCallback };
/*      */       } 
/*  658 */       this._handler.handle(callbacks);
/*      */       
/*  660 */       return pkCallback.getKey();
/*  661 */     } catch (Exception e) {
/*  662 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.IssuerSerialNumRequest"), new Object[] { "PrivateKeyCallback.IssuerSerialNumRequest" });
/*      */       
/*  664 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  665 */       throw new XWSSecurityException(e);
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
/*  685 */     if (context != null) {
/*  686 */       Object obj = context.get("certificate");
/*  687 */       if (obj != null && obj instanceof X509Certificate) {
/*  688 */         return (X509Certificate)obj;
/*      */       }
/*      */     } 
/*  691 */     Subject subject = getSubject(context);
/*  692 */     if (subject != null) {
/*  693 */       Set<X509Certificate> set = subject.getPublicCredentials(X509Certificate.class);
/*  694 */       if (set != null && set.size() == 1) {
/*  695 */         return (X509Certificate)set.toArray()[0];
/*      */       }
/*      */     } 
/*  698 */     if (this.myAlias != null || this.keystoreCertSelectorClass != null) {
/*  699 */       return getCertificate(context, this.myAlias, true);
/*      */     }
/*      */     
/*  702 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback(null);
/*  703 */     Callback[] _callbacks = null;
/*  704 */     if (this.useXWSSCallbacks) {
/*  705 */       RuntimeProperties props = new RuntimeProperties(context);
/*  706 */       _callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */     } else {
/*      */       
/*  709 */       _callbacks = new Callback[] { (Callback)pkCallback };
/*      */     } 
/*      */     try {
/*  712 */       this._handler.handle(_callbacks);
/*  713 */     } catch (Exception e) {
/*  714 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback with null argument"), new Object[] { "PrivateKeyCallback with null argument" });
/*      */       
/*  716 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  717 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  720 */     Certificate[] chain = pkCallback.getChain();
/*  721 */     if (chain == null) {
/*  722 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0296_NULL_CHAIN_CERT());
/*  723 */       throw new XWSSecurityException("Empty certificate chain returned by PrivateKeyCallback");
/*      */     } 
/*      */     
/*  726 */     return (X509Certificate)chain[0];
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
/*      */   public boolean authenticateUser(Map context, String username, String password) throws XWSSecurityException {
/*  739 */     if (this.pwValidator != null) {
/*  740 */       PasswordValidationCallback.PlainTextPasswordRequest request = new PasswordValidationCallback.PlainTextPasswordRequest(username, password);
/*      */       
/*  742 */       PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */       
/*  744 */       ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/*  745 */       passwordValidationCallback.setValidator(this.pwValidator);
/*  746 */       return passwordValidationCallback.getResult();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  752 */     char[] pwd = (password == null) ? null : password.toCharArray();
/*  753 */     PasswordValidationCallback pvCallback = new PasswordValidationCallback(getRequesterSubject(context), username, pwd);
/*      */     
/*  755 */     Callback[] callbacks = null;
/*  756 */     if (this.useXWSSCallbacks) {
/*  757 */       RuntimeProperties xwsscb = new RuntimeProperties(context);
/*  758 */       callbacks = new Callback[] { (Callback)xwsscb, (Callback)pvCallback };
/*      */     } else {
/*  760 */       callbacks = new Callback[] { (Callback)pvCallback };
/*      */     } 
/*      */     try {
/*  763 */       this._handler.handle(callbacks);
/*  764 */     } catch (Exception e) {
/*  765 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("Authenticating User against list of Known username-password pairs"), new Object[] { "Authenticating User against list of Known username-password pairs" });
/*      */       
/*  767 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */ 
/*      */     
/*  771 */     if (pwd != null) {
/*  772 */       pvCallback.clearPassword();
/*      */     }
/*  774 */     if (log.isLoggable(Level.FINE)) {
/*  775 */       log.log(Level.FINE, "Username Authentication done for " + username);
/*      */     }
/*      */     
/*  778 */     return pvCallback.getResult();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String authenticateUser(Map context, String username) throws XWSSecurityException {
/*  788 */     String password = null;
/*  789 */     if (this.pwValidator != null) {
/*  790 */       PasswordValidationCallback.DerivedKeyPasswordRequest request = new PasswordValidationCallback.DerivedKeyPasswordRequest(username);
/*      */       
/*  792 */       PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */       
/*  794 */       ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/*  795 */       if (this.pwValidator != null && this.pwValidator instanceof PasswordValidationCallback.DerivedKeyPasswordValidator) {
/*  796 */         ((PasswordValidationCallback.DerivedKeyPasswordValidator)this.pwValidator).setPassword((PasswordValidationCallback.Request)request);
/*  797 */         passwordValidationCallback.setValidator(this.pwValidator);
/*      */       } 
/*  799 */       passwordValidationCallback.getResult();
/*  800 */       password = request.getPassword();
/*      */     } 
/*  802 */     return password;
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
/*      */   public boolean authenticateUser(Map context, String username, String passwordDigest, String nonce, String created) throws XWSSecurityException {
/*  822 */     boolean result = false;
/*  823 */     if (this.pwValidator != null) {
/*  824 */       PasswordValidationCallback.DigestPasswordRequest request = new PasswordValidationCallback.DigestPasswordRequest(username, passwordDigest, nonce, created);
/*      */       
/*  826 */       PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */       
/*  828 */       ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/*  829 */       if (this.pwValidator != null && this.pwValidator instanceof PasswordValidationCallback.WsitDigestPasswordValidator) {
/*  830 */         ((PasswordValidationCallback.WsitDigestPasswordValidator)this.pwValidator).setPassword((PasswordValidationCallback.Request)request);
/*  831 */         passwordValidationCallback.setValidator(this.pwValidator);
/*      */       } 
/*  833 */       return passwordValidationCallback.getResult();
/*      */     } 
/*      */     
/*  836 */     if (this.useXWSSCallbacks) {
/*  837 */       PasswordValidationCallback.DigestPasswordRequest request = new PasswordValidationCallback.DigestPasswordRequest(username, passwordDigest, nonce, created);
/*      */ 
/*      */       
/*  840 */       PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */       
/*  842 */       ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/*  843 */       Callback[] callbacks = { (Callback)passwordValidationCallback };
/*      */       
/*      */       try {
/*  846 */         this._handler.handle(callbacks);
/*  847 */         if (passwordValidationCallback.getValidator() != null) {
/*  848 */           result = passwordValidationCallback.getResult();
/*  849 */           if (result == true) {
/*  850 */             CallerPrincipalCallback pvCallback = new CallerPrincipalCallback(getSubject(context), username);
/*  851 */             callbacks = new Callback[] { (Callback)pvCallback };
/*      */             try {
/*  853 */               this._handler.handle(callbacks);
/*  854 */             } catch (Exception e) {
/*  855 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("CallerPrincipalCallback"), new Object[] { "CallerPrincipalCallback" });
/*      */               
/*  857 */               throw new XWSSecurityRuntimeException(e);
/*      */             } 
/*      */           } 
/*  860 */           return result;
/*      */         } 
/*  862 */       } catch (UnsupportedCallbackException ex) {
/*      */         
/*  864 */         if (log.isLoggable(Level.FINE)) {
/*  865 */           log.log(Level.FINE, "The Supplied JMAC CallbackHandler does not support com.sun.xml.wss.impl.callback.PasswordValidationCallback.DigestPasswordRequest");
/*      */         }
/*  867 */       } catch (Exception e) {
/*  868 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/*  869 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/*  874 */       RealmAuthenticationAdapter adapter = RealmAuthenticationAdapter.newInstance(null);
/*  875 */       if (adapter != null) {
/*  876 */         result = adapter.authenticate(getSubject(context), username, passwordDigest, nonce, created, context);
/*      */       } else {
/*  878 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0295_PASSWORD_VAL_NOT_CONFIG_USERNAME_VAL());
/*  879 */         throw new XWSSecurityException("Error: No PasswordValidator Configured for UsernameToken Validation");
/*      */       } 
/*  881 */     } catch (Exception e) {
/*  882 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/*  883 */       throw new XWSSecurityException(e);
/*      */     } 
/*  885 */     if (log.isLoggable(Level.FINE)) {
/*  886 */       log.log(Level.FINE, "Username Authentication done for " + username);
/*      */     }
/*  888 */     return result;
/*      */   }
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
/*  900 */     if (this.certValidator != null) {
/*  901 */       CertificateValidationCallback certValCallback = new CertificateValidationCallback(cert, context);
/*  902 */       certValCallback.setValidator(this.certValidator);
/*  903 */       certValCallback.setRevocationEnabled(this.revocationEnabled);
/*  904 */       return certValCallback.getResult();
/*      */     } 
/*  906 */     if (this.useXWSSCallbacks) {
/*  907 */       CertificateValidationCallback certValCallback = new CertificateValidationCallback(cert, context);
/*  908 */       certValCallback.setRevocationEnabled(this.revocationEnabled);
/*  909 */       Callback[] callbacks = { (Callback)certValCallback };
/*      */       try {
/*  911 */         this._handler.handle(callbacks);
/*  912 */       } catch (Exception e) {
/*  913 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0223_FAILED_CERTIFICATE_VALIDATION());
/*  914 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Certificate validation failed", e, true);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  919 */       if (log.isLoggable(Level.FINE)) {
/*  920 */         log.log(Level.FINE, "Certificate Validation called on certificate " + cert.getSubjectDN());
/*      */       }
/*  922 */       return certValCallback.getResult();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  929 */       cert.checkValidity();
/*  930 */     } catch (CertificateExpiredException e) {
/*  931 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0298_X_509_EXPIRED(), e);
/*  932 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "X509Certificate Expired", e, true);
/*      */     }
/*  934 */     catch (CertificateNotYetValidException e) {
/*  935 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0299_X_509_NOT_VALID(), e);
/*  936 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "X509Certificate not yet valid", e, true);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  941 */     if (cert.getIssuerX500Principal().equals(cert.getSubjectX500Principal())) {
/*  942 */       if (isTrustedSelfSigned(cert)) {
/*  943 */         return true;
/*      */       }
/*  945 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1533_X_509_SELF_SIGNED_CERTIFICATE_NOT_VALID());
/*  946 */       throw new XWSSecurityException("Validation of self signed certificate failed");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  951 */     X509CertSelector certSelector = new X509CertSelector();
/*  952 */     certSelector.setCertificate(cert);
/*      */     
/*  954 */     CertPathValidator certPathValidator = null;
/*  955 */     CertPath certPath = null;
/*  956 */     List<Certificate> certChainList = new ArrayList<Certificate>();
/*  957 */     boolean caFound = false;
/*  958 */     Principal certChainIssuer = null;
/*  959 */     int noOfEntriesInTrustStore = 0;
/*  960 */     boolean isIssuerCertMatched = false;
/*      */     
/*      */     try {
/*  963 */       Callback[] callbacks = null;
/*  964 */       CertStoreCallback csCallback = null;
/*  965 */       TrustStoreCallback tsCallback = null;
/*      */       
/*  967 */       if (tsCallback == null && csCallback == null) {
/*  968 */         csCallback = new CertStoreCallback();
/*  969 */         tsCallback = new TrustStoreCallback();
/*  970 */         callbacks = new Callback[] { (Callback)csCallback, (Callback)tsCallback };
/*  971 */       } else if (csCallback == null) {
/*  972 */         csCallback = new CertStoreCallback();
/*  973 */         callbacks = new Callback[] { (Callback)csCallback };
/*  974 */       } else if (tsCallback == null) {
/*  975 */         tsCallback = new TrustStoreCallback();
/*  976 */         callbacks = new Callback[] { (Callback)tsCallback };
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  981 */         this._handler.handle(callbacks);
/*  982 */       } catch (Exception e) {
/*  983 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("Validate an X509Certificate"), new Object[] { "Validate an X509Certificate" });
/*      */         
/*  985 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */       
/*  988 */       Certificate[] certChain = null;
/*  989 */       String certAlias = tsCallback.getTrustStore().getCertificateAlias(cert);
/*  990 */       if (certAlias != null) {
/*  991 */         certChain = tsCallback.getTrustStore().getCertificateChain(certAlias);
/*      */       }
/*  993 */       if (certChain == null) {
/*  994 */         certChainList.add(cert);
/*  995 */         certChainIssuer = cert.getIssuerX500Principal();
/*  996 */         noOfEntriesInTrustStore = tsCallback.getTrustStore().size();
/*      */       } else {
/*  998 */         certChainList = Arrays.asList(certChain);
/*      */       } 
/* 1000 */       while (!caFound && noOfEntriesInTrustStore-- != 0 && certChain == null) {
/* 1001 */         Enumeration<String> aliases = tsCallback.getTrustStore().aliases();
/* 1002 */         while (aliases.hasMoreElements()) {
/* 1003 */           String alias = aliases.nextElement();
/* 1004 */           Certificate certificate = tsCallback.getTrustStore().getCertificate(alias);
/* 1005 */           if (certificate == null || !"X.509".equals(certificate.getType()) || certChainList.contains(certificate)) {
/*      */             continue;
/*      */           }
/* 1008 */           X509Certificate x509Cert = (X509Certificate)certificate;
/* 1009 */           if (certChainIssuer.equals(x509Cert.getSubjectX500Principal())) {
/* 1010 */             certChainList.add(certificate);
/* 1011 */             if (x509Cert.getSubjectX500Principal().equals(x509Cert.getIssuerX500Principal())) {
/* 1012 */               caFound = true;
/*      */               break;
/*      */             } 
/* 1015 */             certChainIssuer = x509Cert.getIssuerDN();
/* 1016 */             if (!isIssuerCertMatched) {
/* 1017 */               isIssuerCertMatched = true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1024 */         if (!caFound) {
/* 1025 */           if (!isIssuerCertMatched) {
/*      */             break;
/*      */           }
/* 1028 */           isIssuerCertMatched = false;
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/* 1033 */         CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 1034 */         certPath = cf.generateCertPath(certChainList);
/* 1035 */         certPathValidator = CertPathValidator.getInstance("PKIX");
/* 1036 */       } catch (Exception e) {
/* 1037 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1518_FAILEDTO_VALIDATE_CERTIFICATE(), e);
/* 1038 */         throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
/*      */       } 
/*      */       
/* 1041 */       parameters = new PKIXBuilderParameters(tsCallback.getTrustStore(), certSelector);
/* 1042 */       parameters.setRevocationEnabled(this.revocationEnabled);
/* 1043 */       parameters.addCertStore(csCallback.getCertStore());
/*      */     }
/* 1045 */     catch (Exception e) {
/*      */       
/* 1047 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0223_FAILED_CERTIFICATE_VALIDATION(), e);
/* 1048 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1053 */       certPathValidator.validate(certPath, parameters);
/* 1054 */     } catch (Exception e) {
/* 1055 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0223_FAILED_CERTIFICATE_VALIDATION(), e);
/* 1056 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*      */     } 
/*      */ 
/*      */     
/* 1060 */     if (log.isLoggable(Level.FINE)) {
/* 1061 */       log.log(Level.FINE, "Certificate Validation called on certificate " + cert.getSubjectDN());
/*      */     }
/*      */     
/* 1064 */     return true;
/*      */   }
/*      */   
/*      */   private Subject initJAASKeyStoreLoginModule() {
/* 1068 */     if (this.jaasLoginModuleForKeystore == null) {
/* 1069 */       return null;
/*      */     }
/* 1071 */     LoginContext lc = null;
/*      */     try {
/* 1073 */       if (this.keyStoreCBH != null) {
/* 1074 */         this.keystoreCbHandlerClass = loadClass(this.keyStoreCBH).newInstance();
/* 1075 */         lc = new LoginContext(this.jaasLoginModuleForKeystore, this.keystoreCbHandlerClass);
/*      */       } else {
/* 1077 */         lc = new LoginContext(this.jaasLoginModuleForKeystore);
/*      */       } 
/* 1079 */       lc.login();
/* 1080 */       return lc.getSubject();
/* 1081 */     } catch (InstantiationException ex) {
/* 1082 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), ex);
/* 1083 */       throw new XWSSecurityRuntimeException(ex);
/* 1084 */     } catch (IllegalAccessException ex) {
/* 1085 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), ex);
/* 1086 */       throw new XWSSecurityRuntimeException(ex);
/* 1087 */     } catch (XWSSecurityException ex) {
/* 1088 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), (Throwable)ex);
/* 1089 */       throw new XWSSecurityRuntimeException(ex);
/* 1090 */     } catch (LoginException ex) {
/* 1091 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), ex);
/* 1092 */       throw new XWSSecurityRuntimeException(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isTrustedSelfSigned(X509Certificate cert) throws XWSSecurityException {
/*      */     try {
/* 1098 */       Callback[] callbacks = null;
/* 1099 */       CertStoreCallback csCallback = null;
/* 1100 */       TrustStoreCallback tsCallback = null;
/*      */       
/* 1102 */       if (tsCallback == null && csCallback == null) {
/* 1103 */         csCallback = new CertStoreCallback();
/* 1104 */         tsCallback = new TrustStoreCallback();
/* 1105 */         callbacks = new Callback[] { (Callback)csCallback, (Callback)tsCallback };
/* 1106 */       } else if (csCallback == null) {
/* 1107 */         csCallback = new CertStoreCallback();
/* 1108 */         callbacks = new Callback[] { (Callback)csCallback };
/* 1109 */       } else if (tsCallback == null) {
/* 1110 */         tsCallback = new TrustStoreCallback();
/* 1111 */         callbacks = new Callback[] { (Callback)tsCallback };
/*      */       } 
/*      */       
/*      */       try {
/* 1115 */         this._handler.handle(callbacks);
/* 1116 */       } catch (Exception e) {
/* 1117 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("Validate an X509Certificate"), new Object[] { "Validate an X509Certificate" });
/*      */         
/* 1119 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */       
/* 1122 */       if (tsCallback.getTrustStore() == null) {
/* 1123 */         return false;
/*      */       }
/*      */       
/* 1126 */       Enumeration<String> aliases = tsCallback.getTrustStore().aliases();
/* 1127 */       while (aliases.hasMoreElements()) {
/* 1128 */         String alias = aliases.nextElement();
/* 1129 */         Certificate certificate = tsCallback.getTrustStore().getCertificate(alias);
/* 1130 */         if (certificate == null || !"X.509".equals(certificate.getType())) {
/*      */           continue;
/*      */         }
/* 1133 */         X509Certificate x509Cert = (X509Certificate)certificate;
/* 1134 */         if (x509Cert != null && x509Cert.equals(cert)) {
/* 1135 */           return true;
/*      */         }
/*      */       } 
/* 1138 */       return false;
/* 1139 */     } catch (Exception e) {
/* 1140 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0223_FAILED_CERTIFICATE_VALIDATION(), e);
/* 1141 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
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
/*      */   public X509Certificate getMatchingCertificate(Map context, byte[] keyIdMatch) throws XWSSecurityException {
/* 1154 */     Subject subject = getSubject(context);
/* 1155 */     if (subject != null) {
/* 1156 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 1157 */       if (set != null) {
/* 1158 */         Iterator<X500PrivateCredential> it = set.iterator();
/*      */ 
/*      */         
/* 1161 */         while (it.hasNext()) {
/* 1162 */           X500PrivateCredential cred = it.next();
/* 1163 */           X509Certificate cert = cred.getCertificate();
/* 1164 */           if (matchesKeyIdentifier(keyIdMatch, cert)) {
/* 1165 */             return cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1170 */     PrivateKeyCallback.SubjectKeyIDRequest subjectKeyIDRequest = new PrivateKeyCallback.SubjectKeyIDRequest(keyIdMatch);
/*      */     
/* 1172 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)subjectKeyIDRequest);
/* 1173 */     CertStoreCallback csCallback = new CertStoreCallback();
/* 1174 */     TrustStoreCallback tsCallback = new TrustStoreCallback();
/*      */     
/* 1176 */     Callback[] callbacks = null;
/* 1177 */     if (this.useXWSSCallbacks) {
/* 1178 */       RuntimeProperties props = new RuntimeProperties(context);
/* 1179 */       callbacks = new Callback[] { (Callback)props, (Callback)pkCallback, (Callback)tsCallback, (Callback)csCallback };
/*      */     } else {
/* 1181 */       callbacks = new Callback[] { (Callback)pkCallback, (Callback)tsCallback, (Callback)csCallback };
/*      */     } 
/*      */     try {
/* 1184 */       this._handler.handle(callbacks);
/* 1185 */     } catch (Exception e) {
/* 1186 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.SubjectKeyIDRequest"), new Object[] { "PrivateKeyCallback.SubjectKeyIDRequest" });
/*      */       
/* 1188 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1191 */     Certificate[] chain = pkCallback.getChain();
/* 1192 */     if (chain != null) {
/* 1193 */       if (chain.length == 1) {
/* 1194 */         return (X509Certificate)chain[0];
/*      */       }
/* 1196 */       for (int i = 0; i < chain.length; i++) {
/* 1197 */         X509Certificate x509Cert = (X509Certificate)chain[i];
/* 1198 */         if (matchesKeyIdentifier(keyIdMatch, x509Cert)) {
/* 1199 */           return x509Cert;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1204 */     CertStore certStore = csCallback.getCertStore();
/* 1205 */     if (certStore != null) {
/* 1206 */       CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1213 */       if (selector == null) {
/* 1214 */         selector = new KeyIdentifierCertSelector(keyIdMatch);
/*      */       }
/*      */       try {
/* 1217 */         Collection<? extends Certificate> certs = certStore.getCertificates(selector);
/* 1218 */         if (!certs.isEmpty()) {
/* 1219 */           Iterator<? extends Certificate> it = certs.iterator();
/* 1220 */           return (X509Certificate)it.next();
/*      */         } 
/* 1222 */       } catch (CertStoreException ex) {
/*      */         
/* 1224 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0713_ERROR_IN_CERTSTORE_LOOKUP(), ex);
/* 1225 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */     } 
/*      */     
/* 1229 */     KeyStore trustStore = tsCallback.getTrustStore();
/* 1230 */     if (trustStore != null) {
/* 1231 */       X509Certificate otherPartyCert = getMatchingCertificate(keyIdMatch, trustStore);
/* 1232 */       if (otherPartyCert != null) {
/* 1233 */         return otherPartyCert;
/*      */       }
/*      */     } 
/*      */     
/* 1237 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(keyIdMatch), new Object[] { keyIdMatch });
/*      */     
/* 1239 */     throw new XWSSecurityException("No Matching Certificate for :" + new String(keyIdMatch) + " found in KeyStore or TrustStore");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getMatchingCertificate(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/* 1248 */     Subject subject = getSubject(context);
/* 1249 */     if (subject != null) {
/* 1250 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 1251 */       if (set != null) {
/* 1252 */         Iterator<X500PrivateCredential> it = set.iterator();
/* 1253 */         while (it.hasNext()) {
/* 1254 */           X500PrivateCredential cred = it.next();
/* 1255 */           X509Certificate x509Cert = cred.getCertificate();
/* 1256 */           BigInteger serialNo = x509Cert.getSerialNumber();
/*      */           
/* 1258 */           X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1259 */           X500Principal issuerPrincipal = new X500Principal(issuerName);
/* 1260 */           if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */           {
/* 1262 */             return x509Cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1268 */     PrivateKeyCallback.IssuerSerialNumRequest issuerSerialNumRequest = new PrivateKeyCallback.IssuerSerialNumRequest(new X500Principal(issuerName), serialNumber);
/*      */ 
/*      */     
/* 1271 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)issuerSerialNumRequest);
/* 1272 */     TrustStoreCallback tsCallback = new TrustStoreCallback();
/* 1273 */     CertStoreCallback csCallback = new CertStoreCallback();
/*      */     
/* 1275 */     Callback[] callbacks = null;
/* 1276 */     if (this.useXWSSCallbacks) {
/* 1277 */       RuntimeProperties props = new RuntimeProperties(context);
/* 1278 */       callbacks = new Callback[] { (Callback)props, (Callback)pkCallback, (Callback)tsCallback, (Callback)csCallback };
/*      */     } else {
/* 1280 */       callbacks = new Callback[] { (Callback)pkCallback, (Callback)tsCallback, (Callback)csCallback };
/*      */     } 
/*      */     
/*      */     try {
/* 1284 */       this._handler.handle(callbacks);
/* 1285 */     } catch (Exception e) {
/* 1286 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.IssuerSerialNumRequest"), new Object[] { "PrivateKeyCallback.IssuerSerialNumRequest" });
/*      */       
/* 1288 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1291 */     Certificate[] chain = pkCallback.getChain();
/* 1292 */     if (chain != null) {
/* 1293 */       if (chain.length == 1) {
/* 1294 */         return (X509Certificate)chain[0];
/*      */       }
/* 1296 */       for (int i = 0; i < chain.length; i++) {
/* 1297 */         X509Certificate x509Cert = (X509Certificate)chain[i];
/* 1298 */         if (matchesIssuerSerialAndName(serialNumber, issuerName, x509Cert))
/*      */         {
/*      */ 
/*      */           
/* 1302 */           return x509Cert;
/*      */         }
/*      */       } 
/* 1305 */     } else if (log.isLoggable(Level.FINE)) {
/* 1306 */       log.log(Level.FINE, LogStringsMessages.WSS_0296_NULL_CHAIN_CERT());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1311 */     CertStore certStore = csCallback.getCertStore();
/* 1312 */     if (certStore != null) {
/* 1313 */       CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1321 */       if (selector == null) {
/* 1322 */         selector = new IssuerNameAndSerialCertSelector(serialNumber, issuerName);
/*      */       }
/*      */       try {
/* 1325 */         Collection<? extends Certificate> certs = certStore.getCertificates(selector);
/* 1326 */         if (!certs.isEmpty()) {
/* 1327 */           Iterator<? extends Certificate> it = certs.iterator();
/* 1328 */           return (X509Certificate)it.next();
/*      */         } 
/* 1330 */       } catch (CertStoreException ex) {
/*      */         
/* 1332 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0713_ERROR_IN_CERTSTORE_LOOKUP(), ex);
/* 1333 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1338 */     KeyStore trustStore = tsCallback.getTrustStore();
/* 1339 */     if (trustStore != null) {
/* 1340 */       X509Certificate otherPartyCert = getMatchingCertificate(serialNumber, issuerName, trustStore);
/*      */ 
/*      */       
/* 1343 */       if (otherPartyCert != null) {
/* 1344 */         return otherPartyCert;
/*      */       }
/*      */     } else {
/* 1347 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0707_NULL_TRUSTSTORE());
/*      */     } 
/*      */ 
/*      */     
/* 1351 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(issuerName + " : " + serialNumber), new Object[] { issuerName + " : " + serialNumber });
/*      */     
/* 1353 */     throw new XWSSecurityException("No Matching Certificate for :" + issuerName + " : " + serialNumber + " found in KeyStore or TrustStore");
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
/* 1366 */     X509Certificate certificate = XWSSUtil.matchesProgrammaticInfo(context, keyIdMatch, valueType);
/* 1367 */     if (certificate != null) {
/* 1368 */       return certificate;
/*      */     }
/* 1370 */     if ("Identifier".equals(valueType)) {
/* 1371 */       return getMatchingCertificate(context, keyIdMatch);
/*      */     }
/*      */     
/* 1374 */     if (!"Thumbprint".equals(valueType)) {
/* 1375 */       throw new XWSSecurityException("Internal Error : Unsupported Valuetype :" + valueType + " passed to getMatchingCertificate()");
/*      */     }
/*      */ 
/*      */     
/* 1379 */     Subject subject = getSubject(context);
/* 1380 */     if (subject != null) {
/* 1381 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 1382 */       if (set != null) {
/* 1383 */         Iterator<X500PrivateCredential> it = set.iterator();
/* 1384 */         while (it.hasNext()) {
/* 1385 */           X500PrivateCredential cred = it.next();
/* 1386 */           X509Certificate cert = cred.getCertificate();
/* 1387 */           if (matchesThumbPrint(keyIdMatch, cert)) {
/* 1388 */             return cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1393 */     PrivateKeyCallback.DigestRequest digestRequest = new PrivateKeyCallback.DigestRequest(keyIdMatch, "SHA-1");
/* 1394 */     PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)digestRequest);
/* 1395 */     TrustStoreCallback tsCallback = new TrustStoreCallback();
/* 1396 */     CertStoreCallback csCallback = new CertStoreCallback();
/*      */     
/* 1398 */     Callback[] callbacks = null;
/* 1399 */     if (this.useXWSSCallbacks) {
/* 1400 */       RuntimeProperties props = new RuntimeProperties(context);
/* 1401 */       callbacks = new Callback[] { (Callback)props, (Callback)pkCallback, (Callback)tsCallback, (Callback)csCallback };
/*      */     } else {
/* 1403 */       callbacks = new Callback[] { (Callback)pkCallback, (Callback)tsCallback, (Callback)csCallback };
/*      */     } 
/*      */     try {
/* 1406 */       this._handler.handle(callbacks);
/* 1407 */     } catch (Exception e) {
/* 1408 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.SubjectKeyIDRequest"), new Object[] { "PrivateKeyCallback.SubjectKeyIDRequest" });
/*      */       
/* 1410 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1413 */     Certificate[] chain = pkCallback.getChain();
/* 1414 */     if (chain != null) {
/* 1415 */       if (chain.length == 1) {
/* 1416 */         return (X509Certificate)chain[0];
/*      */       }
/* 1418 */       for (int i = 0; i < chain.length; i++) {
/* 1419 */         X509Certificate x509Cert = (X509Certificate)chain[i];
/*      */         
/* 1421 */         if (matchesThumbPrint(keyIdMatch, x509Cert)) {
/* 1422 */           return x509Cert;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1428 */     CertStore certStore = csCallback.getCertStore();
/* 1429 */     if (certStore != null) {
/* 1430 */       CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1437 */       if (selector == null) {
/* 1438 */         selector = new DigestCertSelector(keyIdMatch, "SHA-1");
/*      */       }
/*      */       try {
/* 1441 */         Collection<? extends Certificate> certs = certStore.getCertificates(selector);
/* 1442 */         if (!certs.isEmpty()) {
/* 1443 */           Iterator<? extends Certificate> it = certs.iterator();
/* 1444 */           return (X509Certificate)it.next();
/*      */         } 
/* 1446 */       } catch (CertStoreException ex) {
/*      */         
/* 1448 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0713_ERROR_IN_CERTSTORE_LOOKUP(), ex);
/* 1449 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1454 */     KeyStore trustStore = tsCallback.getTrustStore();
/* 1455 */     if (trustStore != null) {
/* 1456 */       X509Certificate otherPartyCert = getMatchingCertificate(keyIdMatch, trustStore, valueType);
/* 1457 */       if (otherPartyCert != null) return otherPartyCert;
/*      */     
/*      */     } 
/*      */     
/* 1461 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(keyIdMatch), new Object[] { keyIdMatch });
/*      */     
/* 1463 */     throw new XWSSecurityException("No Matching Certificate for :" + new String(keyIdMatch) + " found in KeyStore or TrustStore");
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
/* 1474 */     SecretKeyCallback.AliasRequest aliasRequest = new SecretKeyCallback.AliasRequest(alias);
/* 1475 */     SecretKeyCallback skCallback = new SecretKeyCallback((SecretKeyCallback.Request)aliasRequest);
/* 1476 */     Callback[] callbacks = null;
/* 1477 */     if (this.useXWSSCallbacks) {
/* 1478 */       RuntimeProperties props = new RuntimeProperties(context);
/* 1479 */       callbacks = new Callback[] { (Callback)props, (Callback)skCallback };
/*      */     } else {
/* 1481 */       callbacks = new Callback[] { (Callback)skCallback };
/*      */     } 
/*      */     try {
/* 1484 */       this._handler.handle(callbacks);
/* 1485 */     } catch (Exception e) {
/* 1486 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SecretKeyCallback.AliasRequest"), new Object[] { "SecretKeyCallback.AliasRequest" });
/*      */       
/* 1488 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1491 */     return skCallback.getKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, String alias, boolean forSigning) throws XWSSecurityException {
/* 1496 */     String actualAlias = alias;
/* 1497 */     X509Certificate cert = null;
/* 1498 */     if (alias == null || "".equals(alias)) {
/* 1499 */       if (forSigning) {
/*      */         
/* 1501 */         if (context != null) {
/* 1502 */           Object obj = context.get("certificate");
/* 1503 */           if (obj instanceof X509Certificate) {
/* 1504 */             return (X509Certificate)obj;
/*      */           }
/* 1506 */           if (obj != null) {
/* 1507 */             throw new RuntimeException("CERTIFICATE_PROPERTY does not seem to be set to a valid X509Ceritificate");
/*      */           }
/*      */         } 
/*      */         
/* 1511 */         if (this.myAlias != null) {
/* 1512 */           actualAlias = this.myAlias;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1518 */         else if (this.keystoreCertSelectorClass != null) {
/* 1519 */           AliasSelector selector = null;
/*      */           try {
/* 1521 */             selector = this.keystoreCertSelectorClass.newInstance();
/*      */           }
/* 1523 */           catch (IllegalAccessException ex) {
/* 1524 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0811_EXCEPTION_INSTANTIATING_ALIASSELECTOR(), ex);
/* 1525 */             throw new RuntimeException(ex);
/* 1526 */           } catch (InstantiationException ex) {
/* 1527 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0811_EXCEPTION_INSTANTIATING_ALIASSELECTOR(), ex);
/* 1528 */             throw new RuntimeException(ex);
/*      */           } 
/* 1530 */           actualAlias = selector.select(context);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1535 */         if (context != null) {
/* 1536 */           Object obj = context.get("server-certificate");
/* 1537 */           if (obj instanceof X509Certificate) {
/* 1538 */             return (X509Certificate)obj;
/*      */           }
/*      */         } 
/* 1541 */         if (this.peerEntityAlias != null) {
/* 1542 */           actualAlias = this.peerEntityAlias;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1547 */     PrivateKeyCallback pkCallback = null;
/* 1548 */     if (forSigning) {
/*      */       try {
/* 1550 */         Subject subject = getSubject(context);
/* 1551 */         if (subject != null) {
/* 1552 */           Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 1553 */           if (set != null) {
/* 1554 */             Iterator<X500PrivateCredential> it = set.iterator();
/* 1555 */             while (it.hasNext()) {
/* 1556 */               X500PrivateCredential cred = it.next();
/* 1557 */               if (cred.getAlias().equals(actualAlias)) {
/* 1558 */                 return cred.getCertificate();
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1563 */         PrivateKeyCallback.AliasRequest aliasRequest = new PrivateKeyCallback.AliasRequest(actualAlias);
/* 1564 */         pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)aliasRequest);
/*      */         
/* 1566 */         Callback[] callbacks = null;
/* 1567 */         if (this.useXWSSCallbacks) {
/* 1568 */           RuntimeProperties props = new RuntimeProperties(context);
/* 1569 */           callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */         } else {
/* 1571 */           callbacks = new Callback[] { (Callback)pkCallback };
/*      */         } 
/* 1573 */         this._handler.handle(callbacks);
/* 1574 */       } catch (Exception e) {
/* 1575 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1576 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */       
/* 1579 */       Certificate[] chain = pkCallback.getChain();
/* 1580 */       if (chain != null) {
/* 1581 */         cert = (X509Certificate)chain[0];
/*      */       }
/* 1583 */       else if (log.isLoggable(Level.FINE)) {
/* 1584 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0296_NULL_CHAIN_CERT());
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1589 */     else if (actualAlias != null && !"".equals(actualAlias)) {
/* 1590 */       TrustStoreCallback tsCallback = new TrustStoreCallback();
/* 1591 */       Callback[] _callbacks = null;
/* 1592 */       if (this.useXWSSCallbacks) {
/* 1593 */         RuntimeProperties props = new RuntimeProperties(context);
/* 1594 */         _callbacks = new Callback[] { (Callback)props, (Callback)tsCallback };
/*      */       } else {
/* 1596 */         _callbacks = new Callback[] { (Callback)tsCallback };
/*      */       } 
/*      */       try {
/* 1599 */         this._handler.handle(_callbacks);
/* 1600 */       } catch (IOException ex) {
/* 1601 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1602 */         throw new XWSSecurityException(ex);
/* 1603 */       } catch (UnsupportedCallbackException ex) {
/* 1604 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1605 */         throw new XWSSecurityException(ex);
/*      */       } 
/* 1607 */       if (tsCallback.getTrustStore() != null) {
/*      */         try {
/* 1609 */           cert = (X509Certificate)tsCallback.getTrustStore().getCertificate(actualAlias);
/* 1610 */         } catch (KeyStoreException ex) {
/* 1611 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1612 */           throw new XWSSecurityException(ex);
/*      */         }
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1619 */       if (this.certSelectorClass != null) {
/* 1620 */         CertStoreCallback csCallback = new CertStoreCallback();
/*      */         
/* 1622 */         Callback[] _callbacks = null;
/* 1623 */         if (this.useXWSSCallbacks) {
/* 1624 */           RuntimeProperties props = new RuntimeProperties(context);
/* 1625 */           _callbacks = new Callback[] { (Callback)props, (Callback)csCallback };
/*      */         } else {
/* 1627 */           _callbacks = new Callback[] { (Callback)csCallback };
/*      */         } 
/*      */         try {
/* 1630 */           this._handler.handle(_callbacks);
/* 1631 */         } catch (IOException ex) {
/* 1632 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1633 */           throw new XWSSecurityException(ex);
/* 1634 */         } catch (UnsupportedCallbackException ex) {
/* 1635 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1636 */           throw new XWSSecurityException(ex);
/*      */         } 
/*      */         
/* 1639 */         if (csCallback.getCertStore() != null) {
/* 1640 */           CertSelector selector = XWSSUtil.getCertSelector(this.certSelectorClass, context);
/* 1641 */           if (selector != null) {
/* 1642 */             Collection<? extends Certificate> certs = null;
/*      */             try {
/* 1644 */               certs = csCallback.getCertStore().getCertificates(selector);
/* 1645 */             } catch (CertStoreException ex) {
/* 1646 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0813_FAILEDTO_GETCERTIFICATE(), ex);
/* 1647 */               throw new RuntimeException(ex);
/*      */             } 
/* 1649 */             if (certs.size() > 0) {
/* 1650 */               cert = certs.iterator().next();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1656 */       if (cert == null && this.truststoreCertSelectorClass != null) {
/*      */         
/* 1658 */         TrustStoreCallback tsCallback = new TrustStoreCallback();
/* 1659 */         Callback[] _callbacks = null;
/* 1660 */         if (this.useXWSSCallbacks) {
/* 1661 */           RuntimeProperties props = new RuntimeProperties(context);
/* 1662 */           _callbacks = new Callback[] { (Callback)props, (Callback)tsCallback };
/*      */         } else {
/* 1664 */           _callbacks = new Callback[] { (Callback)tsCallback };
/*      */         } 
/*      */         try {
/* 1667 */           this._handler.handle(_callbacks);
/* 1668 */         } catch (IOException ex) {
/* 1669 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1670 */           throw new XWSSecurityException(ex);
/* 1671 */         } catch (UnsupportedCallbackException ex) {
/* 1672 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(alias), new Object[] { alias });
/* 1673 */           throw new XWSSecurityException(ex);
/*      */         } 
/*      */         
/* 1676 */         KeyStore trustStore = tsCallback.getTrustStore();
/*      */         
/* 1678 */         if (trustStore != null && 
/* 1679 */           this.truststoreCertSelectorClass != null) {
/* 1680 */           CertSelector selector = XWSSUtil.getCertSelector(this.truststoreCertSelectorClass, context);
/* 1681 */           if (selector != null) {
/* 1682 */             Enumeration<String> aliases = null;
/*      */             try {
/* 1684 */               aliases = trustStore.aliases();
/* 1685 */             } catch (KeyStoreException ex) {
/* 1686 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0813_FAILEDTO_GETCERTIFICATE(), ex);
/* 1687 */               throw new RuntimeException(ex);
/*      */             } 
/* 1689 */             while (aliases.hasMoreElements()) {
/* 1690 */               String currAlias = aliases.nextElement();
/* 1691 */               Certificate thisCertificate = null;
/*      */               try {
/* 1693 */                 thisCertificate = trustStore.getCertificate(currAlias);
/* 1694 */               } catch (KeyStoreException ex) {
/* 1695 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_0813_FAILEDTO_GETCERTIFICATE(), ex);
/* 1696 */                 throw new RuntimeException(ex);
/*      */               } 
/* 1698 */               if (thisCertificate instanceof X509Certificate && selector.match(thisCertificate))
/*      */               {
/* 1700 */                 return (X509Certificate)thisCertificate;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1707 */       if (cert == null) {
/* 1708 */         cert = getDynamicCertificate(context);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1713 */     if (cert == null) {
/* 1714 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(actualAlias));
/* 1715 */       throw new XWSSecurityException("Unable to locate certificate for the alias '" + actualAlias + "'");
/*      */     } 
/*      */ 
/*      */     
/* 1719 */     return cert;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isMyCert(X509Certificate certificate, Map context) {
/*      */     try {
/* 1725 */       X509Certificate cert = getDefaultCertificate(context);
/* 1726 */       if (cert != null && cert.equals(certificate)) {
/* 1727 */         return true;
/*      */       }
/* 1729 */     } catch (XWSSecurityException ex) {}
/*      */ 
/*      */     
/* 1732 */     return false;
/*      */   }
/*      */   
/*      */   private Class loadUsingResourceLoader(String classname) {
/* 1736 */     Class<?> ret = null;
/* 1737 */     if (log.isLoggable(Level.FINE)) {
/* 1738 */       log.log(Level.FINE, "Entered loadUsingResourceLoader to load class.." + classname);
/*      */     }
/* 1740 */     if (this.container != null) {
/*      */       
/* 1742 */       ResourceLoader loader = (ResourceLoader)this.container.getSPI(ResourceLoader.class);
/* 1743 */       if (loader != null) {
/* 1744 */         if (log.isLoggable(Level.FINE)) {
/* 1745 */           log.log(Level.FINE, "Obtained Non null ResourceLoader instance....");
/*      */         }
/*      */         
/*      */         try {
/* 1749 */           URL classpathUrl = loader.getResource(classname);
/*      */           
/* 1751 */           ClassLoader parent = getClass().getClassLoader();
/* 1752 */           URLClassLoader classloader = URLClassLoader.newInstance(new URL[] { classpathUrl }, parent);
/* 1753 */           ret = classloader.loadClass(classname);
/* 1754 */           return ret;
/* 1755 */         } catch (ClassNotFoundException ex) {
/* 1756 */           if (log.isLoggable(Level.FINE)) {
/* 1757 */             log.log(Level.FINE, "Failed load class using ResourceLoader instance....", ex);
/*      */           }
/* 1759 */         } catch (MalformedURLException e) {
/* 1760 */           if (log.isLoggable(Level.FINE)) {
/* 1761 */             log.log(Level.FINE, "Failed load class using ResourceLoader instance....", e);
/*      */           }
/*      */         }
/*      */       
/* 1765 */       } else if (log.isLoggable(Level.FINE)) {
/* 1766 */         log.log(Level.FINE, "Failed to obtain ResourceLoader instance....");
/*      */       }
/*      */     
/*      */     }
/* 1770 */     else if (log.isLoggable(Level.FINE)) {
/* 1771 */       log.log(Level.FINE, "Failed to obtain \"Container\" for getting ResourceLoader SPI ....");
/*      */     } 
/*      */     
/* 1774 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesKeyIdentifier(byte[] keyIdMatch, X509Certificate x509Cert) throws XWSSecurityException {
/* 1781 */     byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/* 1782 */     if (keyId == null)
/*      */     {
/* 1784 */       return false;
/*      */     }
/*      */     
/* 1787 */     if (Arrays.equals(keyIdMatch, keyId)) {
/* 1788 */       return true;
/*      */     }
/* 1790 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesThumbPrint(byte[] keyIdMatch, X509Certificate x509Cert) throws XWSSecurityException {
/* 1798 */     byte[] keyId = XWSSUtil.getThumbprintIdentifier(x509Cert);
/* 1799 */     if (keyId == null)
/*      */     {
/* 1801 */       return false;
/*      */     }
/*      */     
/* 1804 */     if (Arrays.equals(keyIdMatch, keyId)) {
/* 1805 */       return true;
/*      */     }
/* 1807 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(byte[] keyIdMatch, KeyStore kStore) throws XWSSecurityException {
/* 1815 */     if (kStore == null) {
/* 1816 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1820 */       Enumeration<String> enum1 = kStore.aliases();
/* 1821 */       while (enum1.hasMoreElements()) {
/* 1822 */         String alias = enum1.nextElement();
/*      */         
/* 1824 */         Certificate cert = kStore.getCertificate(alias);
/* 1825 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1829 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1830 */         byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/* 1831 */         if (keyId == null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1836 */         if (Arrays.equals(keyIdMatch, keyId)) {
/* 1837 */           return x509Cert;
/*      */         }
/*      */       } 
/* 1840 */     } catch (KeyStoreException kEx) {
/* 1841 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(keyIdMatch), new Object[] { keyIdMatch });
/*      */       
/* 1843 */       throw new XWSSecurityException("No Matching Certificate for :" + new String(keyIdMatch) + " found in KeyStore.", kEx);
/*      */     } 
/*      */ 
/*      */     
/* 1847 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(byte[] keyIdMatch, KeyStore kStore, String valueType) throws XWSSecurityException {
/* 1856 */     if ("Identifier".equals(valueType)) {
/* 1857 */       return getMatchingCertificate(keyIdMatch, kStore);
/*      */     }
/* 1859 */     if (!"Thumbprint".equals(valueType)) {
/* 1860 */       throw new XWSSecurityException("Internal Error : Unsupported Valuetype :" + valueType + " passed to getMatchingCertificate()");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1865 */     if (kStore == null) {
/* 1866 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1870 */       Enumeration<String> enum1 = kStore.aliases();
/* 1871 */       while (enum1.hasMoreElements()) {
/* 1872 */         String alias = enum1.nextElement();
/*      */         
/* 1874 */         Certificate cert = kStore.getCertificate(alias);
/* 1875 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1879 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1880 */         byte[] keyId = XWSSUtil.getThumbprintIdentifier(x509Cert);
/*      */         
/* 1882 */         if (Arrays.equals(keyIdMatch, keyId)) {
/* 1883 */           return x509Cert;
/*      */         }
/*      */       } 
/* 1886 */     } catch (KeyStoreException kEx) {
/* 1887 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(keyIdMatch), new Object[] { keyIdMatch });
/*      */       
/* 1889 */       throw new XWSSecurityException("No Matching Certificate for :" + new String(keyIdMatch) + " found in KeyStore.", kEx);
/*      */     } 
/*      */ 
/*      */     
/* 1893 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesIssuerSerialAndName(BigInteger serialNumberMatch, String issuerNameMatch, X509Certificate x509Cert) {
/* 1901 */     BigInteger serialNumber = x509Cert.getSerialNumber();
/*      */     
/* 1903 */     X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1904 */     X500Principal issuerPrincipal = new X500Principal(issuerNameMatch);
/*      */     
/* 1906 */     if (serialNumber.equals(serialNumberMatch) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */     {
/* 1908 */       return true;
/*      */     }
/* 1910 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(BigInteger serialNumber, String issuerName, KeyStore kStore) throws XWSSecurityException {
/* 1919 */     if (kStore == null) {
/* 1920 */       return null;
/*      */     }
/*      */     try {
/* 1923 */       Enumeration<String> enum1 = kStore.aliases();
/* 1924 */       while (enum1.hasMoreElements()) {
/* 1925 */         String alias = enum1.nextElement();
/*      */         
/* 1927 */         Certificate cert = kStore.getCertificate(alias);
/* 1928 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1932 */         X509Certificate x509Cert = (X509Certificate)cert;
/*      */         
/* 1934 */         X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1935 */         X500Principal issuerPrincipal = new X500Principal(issuerName);
/*      */         
/* 1937 */         BigInteger thisSerialNumber = x509Cert.getSerialNumber();
/*      */ 
/*      */         
/* 1940 */         if (thisSerialNumber.equals(serialNumber) && currentIssuerPrincipal.equals(issuerName))
/*      */         {
/* 1942 */           return x509Cert;
/*      */         }
/*      */       } 
/* 1945 */     } catch (KeyStoreException kEx) {
/* 1946 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(issuerName + " : " + serialNumber), new Object[] { issuerName + " : " + serialNumber });
/*      */       
/* 1948 */       throw new XWSSecurityException("No Matching Certificate for :" + issuerName + " : " + serialNumber + " found in KeyStore.", kEx);
/*      */     } 
/*      */ 
/*      */     
/* 1952 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getMatchingCertificate(PublicKey publicKey, KeyStore kStore) throws XWSSecurityException {
/* 1960 */     if (kStore == null) {
/* 1961 */       return null;
/*      */     }
/*      */     try {
/* 1964 */       Enumeration<String> enum1 = kStore.aliases();
/* 1965 */       while (enum1.hasMoreElements()) {
/* 1966 */         String alias = enum1.nextElement();
/*      */         
/* 1968 */         Certificate cert = kStore.getCertificate(alias);
/* 1969 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/* 1972 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1973 */         if (x509Cert.getPublicKey().equals(publicKey))
/* 1974 */           return x509Cert; 
/*      */       } 
/* 1976 */     } catch (KeyStoreException kEx) {
/* 1977 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(publicKey), new Object[] { publicKey });
/*      */       
/* 1979 */       throw new XWSSecurityException("No Matching Certificate for :" + publicKey + " found in KeyStore.", kEx);
/*      */     } 
/*      */ 
/*      */     
/* 1983 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, String username, String password) {
/* 1991 */     CallerPrincipalCallback pvCallback = new CallerPrincipalCallback(subject, username);
/* 1992 */     Callback[] callbacks = { (Callback)pvCallback };
/*      */     try {
/* 1994 */       this._handler.handle(callbacks);
/* 1995 */     } catch (Exception e) {
/* 1996 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("CallerPrincipalCallback"), new Object[] { "CallerPrincipalCallback" });
/*      */       
/* 1998 */       throw new XWSSecurityRuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final X509Certificate cert) {
/* 2006 */     Principal principal = cert.getSubjectX500Principal();
/* 2007 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 2010 */             subject.getPublicCredentials().add(cert);
/* 2011 */             return null;
/*      */           }
/*      */         });
/*      */     
/* 2015 */     CallerPrincipalCallback pvCallback = new CallerPrincipalCallback(subject, principal);
/* 2016 */     Callback[] callbacks = { (Callback)pvCallback };
/*      */     try {
/* 2018 */       this._handler.handle(callbacks);
/* 2019 */     } catch (Exception e) {
/* 2020 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("CallerPrincipalCallback"), new Object[] { "CallerPrincipalCallback" });
/*      */       
/* 2022 */       throw new XWSSecurityRuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final Assertion assertion) {
/* 2029 */     if (this.sValidator instanceof SAMLValidator) {
/*      */       return;
/*      */     }
/*      */     
/* 2033 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 2036 */             subject.getPublicCredentials().add(assertion);
/* 2037 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/* 2045 */     return getCertificate(context, serialNumber, issuerName).getPublicKey();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(String keyIdentifier) throws XWSSecurityException {
/*      */     try {
/* 2052 */       return getMatchingCertificate((Map)null, getDecodedBase64EncodedData(keyIdentifier)).getPublicKey();
/*      */     
/*      */     }
/* 2055 */     catch (Exception e) {
/* 2056 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(keyIdentifier), new Object[] { keyIdentifier });
/*      */       
/* 2058 */       throw new XWSSecurityException("No Matching Certificate for :" + keyIdentifier + " found in KeyStore ");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*      */     try {
/* 2066 */       return getCertificate(context, keyIdentifier).getPublicKey();
/* 2067 */     } catch (Exception e) {
/* 2068 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0706_NO_MATCHING_CERT(keyIdentifier), new Object[] { keyIdentifier });
/*      */       
/* 2070 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/* 2076 */     return getCertificate(context, identifier, valueType).getPublicKey();
/*      */   }
/*      */ 
/*      */   
/*      */   private byte[] getDecodedBase64EncodedData(String encodedData) throws XWSSecurityException {
/*      */     try {
/* 2082 */       return Base64.decode(encodedData);
/* 2083 */     } catch (Base64DecodingException e) {
/* 2084 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0144_UNABLETO_DECODE_BASE_64_DATA(e.getMessage()), e);
/* 2085 */       throw new SecurityHeaderException("Unable to decode Base64 encoded data", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/* 2095 */     if (context != null) {
/* 2096 */       Object obj = context.get("server-certificate");
/* 2097 */       X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, serialNumber, issuerName);
/* 2098 */       if (cert != null) {
/* 2099 */         return cert;
/*      */       }
/*      */     } 
/* 2102 */     return getMatchingCertificate(context, serialNumber, issuerName);
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(String keyIdentifier) throws XWSSecurityException {
/*      */     try {
/* 2108 */       byte[] decoded = getDecodedBase64EncodedData(keyIdentifier);
/* 2109 */       return getMatchingCertificate((Map)null, decoded);
/* 2110 */     } catch (Exception e) {
/* 2111 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0711_ERROR_MATCH_CERT_FOR_DECODED_STRING(), e);
/* 2112 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, PublicKey publicKey, boolean forSign) {
/* 2119 */     if (XWSSUtil.matchesProgrammaticInfo(context, publicKey) != null) {
/* 2120 */       PrivateKey key = XWSSUtil.getProgrammaticPrivateKey(context);
/* 2121 */       if (key != null) {
/* 2122 */         return key;
/*      */       }
/*      */     } 
/* 2125 */     PublicKey myAliasKey = null;
/*      */     try {
/* 2127 */       myAliasKey = getCertificate(context, this.myAlias, true).getPublicKey();
/* 2128 */       if (myAliasKey.equals(publicKey)) {
/* 2129 */         PrivateKey ret = getPrivateKey(context, this.myAlias);
/* 2130 */         return ret;
/*      */       } 
/* 2132 */     } catch (XWSSecurityException ex) {
/*      */       
/* 2134 */       throw new XWSSecurityRuntimeException(ex);
/*      */     } 
/*      */     
/* 2137 */     throw new XWSSecurityRuntimeException("Could not locate Matching Private Key for: " + publicKey);
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, byte[] ski) {
/* 2142 */     X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(context, ski, "Identifier");
/* 2143 */     if (cert != null) {
/* 2144 */       return cert;
/*      */     }
/*      */     try {
/* 2147 */       return getMatchingCertificate(context, ski);
/* 2148 */     } catch (XWSSecurityException ex) {
/* 2149 */       throw new RuntimeException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, PublicKey publicKey, boolean forSign) throws XWSSecurityException {
/* 2156 */     if (context != null) {
/* 2157 */       Object obj = context.get("server-certificate");
/* 2158 */       X509Certificate certificate = XWSSUtil.matchesProgrammaticInfo(obj, publicKey);
/* 2159 */       if (certificate != null) {
/* 2160 */         return certificate;
/*      */       }
/*      */     } 
/* 2163 */     Subject subject = getSubject(context);
/* 2164 */     if (subject != null) {
/* 2165 */       Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 2166 */       if (set != null) {
/* 2167 */         Iterator<X500PrivateCredential> it = set.iterator();
/* 2168 */         while (it.hasNext()) {
/* 2169 */           X500PrivateCredential cred = it.next();
/* 2170 */           X509Certificate cert = cred.getCertificate();
/* 2171 */           if (cert.getPublicKey().equals(publicKey)) {
/* 2172 */             return cert;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 2177 */     if (!forSign) {
/* 2178 */       CertStoreCallback csCallback = new CertStoreCallback();
/* 2179 */       TrustStoreCallback tsCallback = new TrustStoreCallback();
/*      */       
/* 2181 */       Callback[] callbacks = null;
/* 2182 */       if (this.useXWSSCallbacks) {
/* 2183 */         RuntimeProperties props = new RuntimeProperties(context);
/* 2184 */         callbacks = new Callback[] { (Callback)props, (Callback)csCallback, (Callback)tsCallback };
/*      */       } else {
/* 2186 */         callbacks = new Callback[] { (Callback)csCallback, (Callback)tsCallback };
/*      */       } 
/*      */       
/*      */       try {
/* 2190 */         this._handler.handle(callbacks);
/* 2191 */       } catch (Exception e) {
/* 2192 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("CertStoreCallback"), new Object[] { "CertStoreCallback" });
/*      */         
/* 2194 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */       
/* 2197 */       CertStore certStore = csCallback.getCertStore();
/* 2198 */       if (certStore != null) {
/* 2199 */         CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2206 */         if (selector == null) {
/* 2207 */           selector = new PublicKeyCertSelector(publicKey);
/*      */         }
/*      */         try {
/* 2210 */           Collection<? extends Certificate> certs = certStore.getCertificates(selector);
/* 2211 */           if (!certs.isEmpty()) {
/* 2212 */             Iterator<? extends Certificate> it = certs.iterator();
/* 2213 */             return (X509Certificate)it.next();
/*      */           } 
/* 2215 */         } catch (CertStoreException ex) {
/* 2216 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0713_ERROR_IN_CERTSTORE_LOOKUP(), ex);
/* 2217 */           throw new XWSSecurityException(ex);
/*      */         } 
/*      */       } 
/*      */       
/* 2221 */       KeyStore trustStore = tsCallback.getTrustStore();
/* 2222 */       if (trustStore != null) {
/* 2223 */         X509Certificate otherPartyCert = getMatchingCertificate(publicKey, trustStore);
/* 2224 */         if (otherPartyCert != null) {
/* 2225 */           return otherPartyCert;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2233 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION(publicKey), new Object[] { publicKey });
/*      */     
/* 2235 */     throw new XWSSecurityException("No Matching Certificate for :" + publicKey + " found in KeyStore or TrustStore");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/* 2243 */     if (context != null) {
/* 2244 */       Object obj = context.get("server-certificate");
/* 2245 */       X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, identifier, valueType);
/* 2246 */       if (cert != null) {
/* 2247 */         return cert;
/*      */       }
/*      */     } 
/* 2250 */     if ("Identifier".equals(valueType)) {
/* 2251 */       return getMatchingCertificate(context, identifier);
/*      */     }
/*      */     
/* 2254 */     return getMatchingCertificate(context, identifier, valueType);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean validateSamlIssuer(String issuer) {
/* 2259 */     return true;
/*      */   }
/*      */   
/*      */   public boolean validateSamlUser(String user, String domain, String format) {
/* 2263 */     return true;
/*      */   }
/*      */   
/*      */   public void setSubject(Subject subject, Map<String, Subject> context) {
/* 2267 */     context.put("javax.security.auth.Subject.self", subject);
/*      */   }
/*      */   
/*      */   public void setRequesterSubject(Subject subject, Map<String, Subject> context) {
/* 2271 */     context.put("javax.security.auth.Subject", subject);
/*      */   }
/*      */   
/*      */   public Subject getSubject() {
/* 2275 */     return null;
/*      */   }
/*      */   
/*      */   public Subject getSubject(Map context) {
/* 2279 */     if (context == null) {
/* 2280 */       return null;
/*      */     }
/* 2282 */     if (this.loginContextSubjectForKeystore != null) {
/* 2283 */       return this.loginContextSubjectForKeystore;
/*      */     }
/* 2285 */     return (Subject)context.get("javax.security.auth.Subject.self");
/*      */   }
/*      */   
/*      */   public Subject getRequesterSubject(final Map context) {
/* 2289 */     if (context == null) {
/* 2290 */       return null;
/*      */     }
/* 2292 */     Subject otherPartySubject = (Subject)context.get("javax.security.auth.Subject");
/* 2293 */     if (otherPartySubject != null) {
/* 2294 */       return otherPartySubject;
/*      */     }
/* 2296 */     otherPartySubject = AccessController.<Subject>doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run()
/*      */           {
/* 2300 */             Subject otherPartySubj = new Subject();
/* 2301 */             context.put("javax.security.auth.Subject", otherPartySubj);
/* 2302 */             return otherPartySubj;
/*      */           }
/*      */         });
/*      */     
/* 2306 */     return otherPartySubject;
/*      */   }
/*      */   
/*      */   private Date getGMTDateWithSkewAdjusted(Calendar c, boolean addSkew) {
/* 2310 */     long offset = c.get(15);
/* 2311 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 2312 */       offset += c.getTimeZone().getDSTSavings();
/*      */     }
/* 2314 */     long beforeTime = c.getTimeInMillis();
/* 2315 */     long currentTime = beforeTime - offset;
/*      */     
/* 2317 */     if (addSkew) {
/* 2318 */       currentTime += 300000L;
/*      */     } else {
/* 2320 */       currentTime -= 300000L;
/*      */     } 
/* 2322 */     c.setTimeInMillis(currentTime);
/* 2323 */     return c.getTime();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getUsername(Map context) throws XWSSecurityException {
/* 2328 */     if (context == null) {
/* 2329 */       return null;
/*      */     }
/* 2331 */     if (this.myUsername != null) {
/* 2332 */       return this.myUsername;
/*      */     }
/* 2334 */     String username = (String)context.get("username");
/* 2335 */     if (username == null)
/*      */     {
/* 2337 */       username = (String)context.get("javax.xml.ws.security.auth.username");
/*      */     }
/* 2339 */     if (username != null) {
/* 2340 */       return username;
/*      */     }
/*      */ 
/*      */     
/* 2344 */     NameCallback nameCallback = new NameCallback("Username: ");
/*      */     try {
/* 2346 */       Callback[] cbs = null;
/* 2347 */       if (this.useXWSSCallbacks) {
/* 2348 */         RuntimeProperties props = new RuntimeProperties(context);
/* 2349 */         cbs = new Callback[] { (Callback)props, nameCallback };
/*      */       } else {
/*      */         
/* 2352 */         cbs = new Callback[] { nameCallback };
/*      */       } 
/*      */       
/* 2355 */       if (this.usernameCbHandler != null) {
/* 2356 */         this.usernameHandler = this.usernameCbHandler.newInstance();
/* 2357 */         this.usernameHandler.handle(cbs);
/* 2358 */         nameCallback.setName(((NameCallback)cbs[0]).getName());
/*      */       } else {
/* 2360 */         if (log.isLoggable(Level.FINE)) {
/* 2361 */           log.log(Level.FINE, "Got NULL for Username Callback Handler");
/*      */         }
/* 2363 */         if (!this.isAppClient)
/*      */         {
/*      */           
/* 2366 */           return null;
/*      */         }
/* 2368 */         this._handler.handle(cbs);
/*      */       } 
/* 2370 */     } catch (Exception e) {
/* 2371 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("NameCallback"), new Object[] { "NameCallback" });
/*      */       
/* 2373 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 2376 */     return nameCallback.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPassword(Map context) throws XWSSecurityException {
/* 2383 */     if (this.myPassword != null) {
/* 2384 */       if (this.myPassword.startsWith("$")) {
/* 2385 */         String alias = this.myPassword.substring(1);
/* 2386 */         SecretKeyCallback.AliasRequest ar = new SecretKeyCallback.AliasRequest(alias);
/*      */         
/* 2388 */         SecretKeyCallback skcb = new SecretKeyCallback((SecretKeyCallback.Request)ar);
/* 2389 */         Callback[] callbacks = null;
/* 2390 */         if (this.useXWSSCallbacks) {
/* 2391 */           RuntimeProperties props = new RuntimeProperties(context);
/* 2392 */           callbacks = new Callback[] { (Callback)props, (Callback)skcb };
/*      */         } else {
/* 2394 */           callbacks = new Callback[] { (Callback)skcb };
/*      */         } 
/*      */         try {
/* 2397 */           this._handler.handle(callbacks);
/* 2398 */           SecretKey key = skcb.getKey();
/* 2399 */           byte[] arrayOfByte = key.getEncoded();
/* 2400 */           return new String(arrayOfByte);
/* 2401 */         } catch (Exception ex) {
/* 2402 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SecretKeyCallback.AliasRequest"), new Object[] { "SecretKeyCallback.AliasRequest" });
/*      */           
/* 2404 */           throw new XWSSecurityException(ex);
/*      */         } 
/*      */       } 
/* 2407 */       return this.myPassword;
/*      */     } 
/*      */     
/* 2410 */     String password = (String)context.get("password");
/* 2411 */     if (password == null)
/*      */     {
/* 2413 */       password = (String)context.get("javax.xml.ws.security.auth.password");
/*      */     }
/* 2415 */     if (password != null) {
/* 2416 */       return password;
/*      */     }
/*      */     
/* 2419 */     PasswordCallback pwdCallback = new PasswordCallback("Password: ", false);
/* 2420 */     Callback[] cbs = null;
/* 2421 */     if (this.useXWSSCallbacks) {
/* 2422 */       RuntimeProperties props = new RuntimeProperties(context);
/* 2423 */       cbs = new Callback[] { (Callback)props, pwdCallback };
/*      */     } else {
/* 2425 */       cbs = new Callback[] { pwdCallback };
/*      */     } 
/*      */     try {
/* 2428 */       if (this.passwordCbHandler != null) {
/* 2429 */         this.passwordHandler = this.passwordCbHandler.newInstance();
/* 2430 */         this.passwordHandler.handle(cbs);
/* 2431 */         char[] pass = ((PasswordCallback)cbs[0]).getPassword();
/* 2432 */         pwdCallback.setPassword(pass);
/*      */       } else {
/* 2434 */         if (!this.isAppClient)
/*      */         {
/*      */           
/* 2437 */           return null;
/*      */         }
/* 2439 */         if (this.useXWSSCallbacks) {
/* 2440 */           RuntimeProperties props = new RuntimeProperties(context);
/* 2441 */           cbs = new Callback[] { (Callback)props, pwdCallback };
/*      */         } else {
/* 2443 */           cbs = new Callback[] { pwdCallback };
/*      */         } 
/* 2445 */         this._handler.handle(cbs);
/*      */       } 
/* 2447 */     } catch (Exception e) {
/* 2448 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/* 2449 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 2452 */     if (pwdCallback.getPassword() == null) {
/* 2453 */       return null;
/*      */     }
/*      */     
/* 2456 */     return new String(pwdCallback.getPassword());
/*      */   }
/*      */   
/*      */   public boolean validateAndCacheNonce(Map context, String nonce, String created, long nonceAge) throws XWSSecurityException {
/* 2460 */     NonceManager nonceMgr = null;
/* 2461 */     if (this.mna != null) {
/* 2462 */       nonceMgr = NonceManager.getInstance(this.maxNonceAge, (WSEndpoint)context.get("WSEndpoint"));
/*      */     } else {
/* 2464 */       nonceMgr = NonceManager.getInstance(nonceAge, (WSEndpoint)context.get("WSEndpoint"));
/*      */     } 
/* 2466 */     return nonceMgr.validateNonce(nonce, created);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateTimestamp(Map context, String created, String expires, long maxClockSkew, long freshnessLimit) throws XWSSecurityException {
/* 2475 */     if (this.tsValidator != null) {
/* 2476 */       TimestampValidationCallback.UTCTimestampRequest request = new TimestampValidationCallback.UTCTimestampRequest(created, expires, maxClockSkew, freshnessLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2483 */       TimestampValidationCallback timestampValidationCallback = new TimestampValidationCallback((TimestampValidationCallback.Request)request);
/*      */       
/* 2485 */       ProcessingContext.copy(timestampValidationCallback.getRuntimeProperties(), context);
/* 2486 */       timestampValidationCallback.setValidator(this.tsValidator);
/*      */       try {
/* 2488 */         timestampValidationCallback.getResult();
/*      */         return;
/* 2490 */       } catch (com.sun.xml.wss.impl.callback.TimestampValidationCallback.TimestampValidationException e) {
/* 2491 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), (Throwable)e);
/* 2492 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*      */       } 
/*      */     } 
/*      */     
/* 2496 */     if (this.useXWSSCallbacks) {
/* 2497 */       TimestampValidationCallback.UTCTimestampRequest request = new TimestampValidationCallback.UTCTimestampRequest(created, expires, maxClockSkew, freshnessLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2504 */       TimestampValidationCallback timestampValidationCallback = new TimestampValidationCallback((TimestampValidationCallback.Request)request);
/*      */ 
/*      */       
/* 2507 */       ProcessingContext.copy(timestampValidationCallback.getRuntimeProperties(), context);
/* 2508 */       Callback[] callbacks = { (Callback)timestampValidationCallback };
/*      */       try {
/* 2510 */         this._handler.handle(callbacks);
/*      */         return;
/* 2512 */       } catch (UnsupportedCallbackException e) {
/*      */       
/* 2514 */       } catch (Exception e) {
/* 2515 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), e);
/* 2516 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*      */       } 
/*      */     } 
/*      */     
/* 2520 */     if (expiresBeforeCreated(created, expires)) {
/* 2521 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0232_EXPIRED_MESSAGE());
/* 2522 */       XWSSecurityException xwsse = new XWSSecurityException("Message expired!");
/* 2523 */       throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSU_MESSAGE_EXPIRED, "Message expired!", xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2529 */     validateCreationTime(context, created, maxClockSkew, freshnessLimit);
/* 2530 */     validateExpirationTime(expires, maxClockSkew, freshnessLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateTimestamp(Map context, Timestamp timestamp, long maxClockSkew, long freshnessLimit) throws XWSSecurityException {
/* 2536 */     validateTimestamp(context, timestamp.getCreated(), timestamp.getExpires(), maxClockSkew, freshnessLimit);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean expiresBeforeCreated(String creationTime, String expirationTime) throws XWSSecurityException {
/* 2541 */     Date created = null;
/* 2542 */     Date expires = null;
/*      */     try {
/*      */       try {
/* 2545 */         synchronized (calendarFormatter1) {
/* 2546 */           created = calendarFormatter1.parse(creationTime);
/*      */         } 
/* 2548 */         if (expirationTime != null) {
/* 2549 */           synchronized (calendarFormatter1) {
/* 2550 */             expires = calendarFormatter1.parse(expirationTime);
/*      */           }
/*      */         
/*      */         }
/* 2554 */       } catch (ParseException pe) {
/* 2555 */         synchronized (calendarFormatter2) {
/* 2556 */           created = calendarFormatter2.parse(creationTime);
/*      */         } 
/* 2558 */         if (expirationTime != null) {
/* 2559 */           synchronized (calendarFormatter2) {
/* 2560 */             expires = calendarFormatter2.parse(expirationTime);
/*      */           } 
/*      */         }
/*      */       } 
/* 2564 */     } catch (ParseException pe) {
/* 2565 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0394_ERROR_PARSING_EXPIRATIONTIME());
/* 2566 */       throw new XWSSecurityException(pe.getMessage());
/*      */     } 
/*      */     
/* 2569 */     if (expires != null && expires.before(created)) {
/* 2570 */       return true;
/*      */     }
/* 2572 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateCreationTime(Map context, String creationTime, long maxClockSkew, long timestampFreshnessLimit) throws XWSSecurityException {
/*      */     Date date1;
/* 2582 */     if (this.tsValidator != null) {
/* 2583 */       TimestampValidationCallback.UTCTimestampRequest request = new TimestampValidationCallback.UTCTimestampRequest(creationTime, null, maxClockSkew, timestampFreshnessLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2590 */       request.isUsernameToken(true);
/* 2591 */       TimestampValidationCallback timestampValidationCallback = new TimestampValidationCallback((TimestampValidationCallback.Request)request);
/*      */ 
/*      */       
/* 2594 */       ProcessingContext.copy(timestampValidationCallback.getRuntimeProperties(), context);
/* 2595 */       timestampValidationCallback.setValidator(this.tsValidator);
/*      */       try {
/* 2597 */         timestampValidationCallback.getResult();
/*      */         return;
/* 2599 */       } catch (com.sun.xml.wss.impl.callback.TimestampValidationCallback.TimestampValidationException e) {
/* 2600 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), (Throwable)e);
/* 2601 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*      */       } 
/*      */     } 
/*      */     
/* 2605 */     if (this.useXWSSCallbacks) {
/* 2606 */       TimestampValidationCallback.UTCTimestampRequest request = new TimestampValidationCallback.UTCTimestampRequest(creationTime, null, maxClockSkew, timestampFreshnessLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2613 */       TimestampValidationCallback timestampValidationCallback = new TimestampValidationCallback((TimestampValidationCallback.Request)request);
/*      */ 
/*      */       
/* 2616 */       ProcessingContext.copy(timestampValidationCallback.getRuntimeProperties(), context);
/* 2617 */       Callback[] callbacks = { (Callback)timestampValidationCallback };
/*      */       try {
/* 2619 */         this._handler.handle(callbacks);
/*      */         return;
/* 2621 */       } catch (UnsupportedCallbackException e) {
/*      */       
/* 2623 */       } catch (Exception e) {
/* 2624 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), e);
/* 2625 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2630 */     long maxClockSkewActual = maxClockSkew;
/* 2631 */     long freshnessLimitActual = timestampFreshnessLimit;
/*      */     
/* 2633 */     if (this.mcs != null && this.maxClockSkewG >= 0L) {
/* 2634 */       maxClockSkewActual = this.maxClockSkewG;
/*      */     }
/* 2636 */     if (this.tfl != null && this.timestampFreshnessLimitG > 0L) {
/* 2637 */       freshnessLimitActual = this.timestampFreshnessLimitG;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2642 */       synchronized (calendarFormatter1) {
/* 2643 */         date1 = calendarFormatter1.parse(creationTime);
/*      */       } 
/* 2645 */     } catch (ParseException pe) {
/*      */       try {
/* 2647 */         synchronized (calendarFormatter2) {
/* 2648 */           date1 = calendarFormatter2.parse(creationTime);
/*      */         } 
/* 2650 */       } catch (ParseException pe1) {
/* 2651 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0226_FAILED_VALIDATING_DEFAULT_CREATION_TIME(), pe1);
/* 2652 */         throw new XWSSecurityException("Exception while parsing Creation Time :" + pe1.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2657 */     Date current = null;
/*      */     try {
/* 2659 */       current = getFreshnessAndSkewAdjustedDate(maxClockSkewActual, freshnessLimitActual);
/*      */     }
/* 2661 */     catch (ParseException pe) {
/* 2662 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0712_ERROR_ADJUST_SKEW_FRESHNESS_TIME(), pe);
/* 2663 */       throw new XWSSecurityException(pe.getMessage());
/*      */     } 
/*      */     
/* 2666 */     if (date1.before(current)) {
/* 2667 */       XWSSecurityException xwsse = new XWSSecurityException("Creation Time is older than configured Timestamp Freshness Interval!");
/*      */       
/* 2669 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Creation Time is older than configured Timestamp Freshness Interval!", xwsse, true);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2675 */     Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkewActual, true);
/*      */     
/* 2677 */     if (currentTime.before(date1)) {
/* 2678 */       XWSSecurityException xwsse = new XWSSecurityException("Creation Time ahead of Current Time!");
/* 2679 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Creation Time ahead of Current Time!", xwsse, true);
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
/* 2690 */     long maxClockSkewActual = maxClockSkew;
/* 2691 */     long freshnessLimitActual = timestampFreshnessLimit;
/*      */     
/* 2693 */     if (this.mcs != null && this.maxClockSkewG >= 0L) {
/* 2694 */       maxClockSkewActual = this.maxClockSkewG;
/*      */     }
/* 2696 */     if (this.tfl != null && this.timestampFreshnessLimitG > 0L) {
/* 2697 */       freshnessLimitActual = this.timestampFreshnessLimitG;
/*      */     }
/*      */     
/* 2700 */     if (expirationTime != null) {
/*      */       Date date1;
/*      */       try {
/* 2703 */         synchronized (calendarFormatter1) {
/* 2704 */           date1 = calendarFormatter1.parse(expirationTime);
/*      */         } 
/* 2706 */       } catch (ParseException pe) {
/*      */         try {
/* 2708 */           synchronized (calendarFormatter2) {
/* 2709 */             date1 = calendarFormatter2.parse(expirationTime);
/*      */           } 
/* 2711 */         } catch (ParseException pe1) {
/* 2712 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0394_ERROR_PARSING_EXPIRATIONTIME());
/* 2713 */           throw new XWSSecurityException("Exception while parsing Expiration Time :" + pe1.getMessage());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2718 */       Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkewActual, false);
/*      */       
/* 2720 */       if (date1.before(currentTime)) {
/* 2721 */         XWSSecurityException xwsse = new XWSSecurityException("Message Expired!");
/* 2722 */         throw DefaultSecurityEnvironmentImpl.newSOAPFaultException(MessageConstants.WSU_MESSAGE_EXPIRED, "Message Expired!", xwsse);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CallbackHandler getCallbackHandler() throws XWSSecurityException {
/* 2732 */     return this._handler;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateSAMLAssertion(Map context, Element assertion) throws XWSSecurityException {
/* 2738 */     if (this.sValidator != null) {
/*      */       try {
/* 2740 */         if (this.sValidator instanceof ValidatorExtension) {
/* 2741 */           ((ValidatorExtension)this.sValidator).setRuntimeProperties(context);
/*      */         }
/* 2743 */         if (this.sValidator instanceof SAMLValidator) {
/* 2744 */           ((SAMLValidator)this.sValidator).validate(assertion, context, getRequesterSubject(context));
/*      */         } else {
/* 2746 */           this.sValidator.validate(assertion);
/*      */         } 
/* 2748 */       } catch (com.sun.xml.wss.impl.callback.SAMLAssertionValidator.SAMLValidationException e) {
/* 2749 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0716_FAILED_VALIDATE_SAML_ASSERTION(), (Throwable)e);
/* 2750 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Validation failed for SAML Assertion ", e, true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element locateSAMLAssertion(Map context, Element binding, String assertionId, Document ownerDoc) throws XWSSecurityException {
/* 2760 */     if (this.samlHandler != null) {
/* 2761 */       SAMLCallback sc = new SAMLCallback();
/* 2762 */       sc.setAssertionId(assertionId);
/* 2763 */       sc.setAuthorityBindingElement(binding);
/* 2764 */       Callback[] cbs = { (Callback)sc };
/*      */       try {
/* 2766 */         this.samlHandler.handle(cbs);
/* 2767 */       } catch (UnsupportedCallbackException ex) {
/* 2768 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0718_EXCEPTION_INVOKING_SAML_HANDLER(), ex);
/* 2769 */         throw new XWSSecurityException(ex);
/* 2770 */       } catch (IOException ex) {
/* 2771 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0718_EXCEPTION_INVOKING_SAML_HANDLER(), ex);
/* 2772 */         throw new XWSSecurityException(ex);
/*      */       } 
/* 2774 */       return sc.getAssertionElement();
/*      */     } 
/*      */     
/* 2777 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0717_NO_SAML_CALLBACK_HANDLER());
/* 2778 */     throw new XWSSecurityException(new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthenticationTokenPolicy.SAMLAssertionBinding populateSAMLPolicy(Map fpcontext, AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, DynamicApplicationContext context) throws XWSSecurityException {
/* 2788 */     AuthenticationTokenPolicy.SAMLAssertionBinding ret = (AuthenticationTokenPolicy.SAMLAssertionBinding)samlBinding.clone();
/*      */     
/* 2790 */     if ("SV".equals(samlBinding.getAssertionType())) {
/*      */       
/* 2792 */       if (this.samlHandler != null) {
/* 2793 */         SAMLCallback sc = new SAMLCallback();
/* 2794 */         SecurityUtil.copy(sc.getRuntimeProperties(), fpcontext);
/* 2795 */         sc.setConfirmationMethod("SV-Assertion");
/* 2796 */         sc.setSAMLVersion(samlBinding.getSAMLVersion());
/* 2797 */         Callback[] cbs = { (Callback)sc };
/*      */         try {
/* 2799 */           this.samlHandler.handle(cbs);
/* 2800 */         } catch (UnsupportedCallbackException ex) {
/* 2801 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0718_EXCEPTION_INVOKING_SAML_HANDLER(), ex);
/* 2802 */           throw new XWSSecurityException(ex);
/* 2803 */         } catch (IOException ex) {
/* 2804 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0718_EXCEPTION_INVOKING_SAML_HANDLER(), ex);
/* 2805 */           throw new XWSSecurityException(ex);
/*      */         } 
/* 2807 */         ret.setAssertion(sc.getAssertionElement());
/* 2808 */         ret.setAssertion(sc.getAssertionReader());
/* 2809 */         ret.setAuthorityBinding(sc.getAuthorityBindingElement());
/* 2810 */         ret.setSAMLVersion(sc.getSAMLVersion());
/*      */       } else {
/*      */         
/* 2813 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0717_NO_SAML_CALLBACK_HANDLER());
/* 2814 */         throw new XWSSecurityException(new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion"));
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 2820 */     else if (this.samlHandler != null) {
/*      */       
/* 2822 */       SAMLCallback sc = new SAMLCallback();
/* 2823 */       SecurityUtil.copy(sc.getRuntimeProperties(), fpcontext);
/* 2824 */       sc.setConfirmationMethod("HOK-Assertion");
/* 2825 */       sc.setSAMLVersion(samlBinding.getSAMLVersion());
/* 2826 */       Callback[] cbs = { (Callback)sc };
/*      */       try {
/* 2828 */         this.samlHandler.handle(cbs);
/* 2829 */       } catch (IOException ex) {
/* 2830 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0718_EXCEPTION_INVOKING_SAML_HANDLER(), ex);
/* 2831 */         throw new XWSSecurityException(ex);
/* 2832 */       } catch (UnsupportedCallbackException ex) {
/* 2833 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0718_EXCEPTION_INVOKING_SAML_HANDLER(), ex);
/* 2834 */         throw new XWSSecurityException(ex);
/*      */       } 
/* 2836 */       ret.setAssertion(sc.getAssertionElement());
/* 2837 */       ret.setAuthorityBinding(sc.getAuthorityBindingElement());
/* 2838 */       ret.setAssertion(sc.getAssertionReader());
/* 2839 */       PrivateKeyBinding pkBinding = (PrivateKeyBinding)ret.newPrivateKeyBinding();
/* 2840 */       PrivateKey key = getPrivateKey(fpcontext, this.myAlias);
/* 2841 */       pkBinding.setPrivateKey(key);
/* 2842 */       ret.setAssertionId(sc.getAssertionId());
/* 2843 */       ret.setSAMLVersion(sc.getSAMLVersion());
/*      */     } else {
/* 2845 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0717_NO_SAML_CALLBACK_HANDLER());
/* 2846 */       throw new XWSSecurityException(new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2851 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date getGMTDateWithSkewAdjusted(Calendar c, long maxClockSkew, boolean addSkew) {
/* 2857 */     long offset = c.get(15);
/* 2858 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 2859 */       offset += c.getTimeZone().getDSTSavings();
/*      */     }
/* 2861 */     long beforeTime = c.getTimeInMillis();
/* 2862 */     long currentTime = beforeTime - offset;
/*      */     
/* 2864 */     if (addSkew) {
/* 2865 */       currentTime += maxClockSkew;
/*      */     } else {
/* 2867 */       currentTime -= maxClockSkew;
/*      */     } 
/* 2869 */     c.setTimeInMillis(currentTime);
/* 2870 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date getFreshnessAndSkewAdjustedDate(long maxClockSkew, long timestampFreshnessLimit) throws ParseException {
/* 2876 */     Calendar c = new GregorianCalendar();
/* 2877 */     long offset = c.get(15);
/* 2878 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/* 2879 */       offset += c.getTimeZone().getDSTSavings();
/*      */     }
/* 2881 */     long beforeTime = c.getTimeInMillis();
/* 2882 */     long currentTime = beforeTime - offset;
/*      */ 
/*      */     
/* 2885 */     long adjustedTime = currentTime - maxClockSkew - timestampFreshnessLimit;
/* 2886 */     c.setTimeInMillis(adjustedTime);
/*      */     
/* 2888 */     return c.getTime();
/*      */   }
/*      */ 
/*      */   
/*      */   private X509Certificate getDynamicCertificate(Map context) {
/* 2893 */     X509Certificate cert = null;
/* 2894 */     X509Certificate self = null;
/* 2895 */     Subject requesterSubject = getRequesterSubject(context);
/* 2896 */     if (requesterSubject != null) {
/* 2897 */       Set<Object> publicCredentials = requesterSubject.getPublicCredentials();
/* 2898 */       for (Iterator it = publicCredentials.iterator(); it.hasNext(); ) {
/* 2899 */         Object cred = it.next();
/* 2900 */         if (cred instanceof X509Certificate) {
/* 2901 */           X509Certificate certificate = (X509Certificate)cred;
/* 2902 */           if (!isMyCert(certificate, context)) {
/* 2903 */             cert = certificate;
/*      */             break;
/*      */           } 
/* 2906 */           self = certificate;
/*      */         } 
/*      */       } 
/*      */       
/* 2910 */       if (cert != null)
/* 2911 */         return cert; 
/* 2912 */       if (self != null)
/*      */       {
/* 2914 */         return self;
/*      */       }
/*      */     } 
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
/* 2936 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, byte[] keyIdentifier, String valueType) throws XWSSecurityException {
/* 2942 */     X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(context, keyIdentifier, valueType);
/* 2943 */     if (cert != null) {
/* 2944 */       PrivateKey key = XWSSUtil.getProgrammaticPrivateKey(context);
/* 2945 */       if (key != null) {
/* 2946 */         return key;
/*      */       }
/*      */     } 
/* 2949 */     if ("Identifier".equals(valueType)) {
/* 2950 */       return getPrivateKey(context, keyIdentifier);
/*      */     }
/* 2952 */     if (!"Thumbprint".equals(valueType)) {
/* 2953 */       throw new XWSSecurityException("Internal Error : Unsupported Valuetype :" + valueType + " passed to getPrivateKey()");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2959 */       Subject subject = getSubject(context);
/* 2960 */       if (subject != null) {
/* 2961 */         Set<X500PrivateCredential> set = subject.getPrivateCredentials(X500PrivateCredential.class);
/* 2962 */         if (set != null) {
/* 2963 */           Iterator<X500PrivateCredential> it = set.iterator();
/* 2964 */           while (it.hasNext()) {
/* 2965 */             X500PrivateCredential cred = it.next();
/* 2966 */             if (matchesThumbPrint(Base64.decode(keyIdentifier), cred.getCertificate()))
/*      */             {
/* 2968 */               return cred.getPrivateKey();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 2973 */       PrivateKeyCallback.DigestRequest digestRequest = new PrivateKeyCallback.DigestRequest(keyIdentifier, "SHA-1");
/*      */       
/* 2975 */       PrivateKeyCallback pkCallback = new PrivateKeyCallback((PrivateKeyCallback.Request)digestRequest);
/* 2976 */       Callback[] callbacks = null;
/* 2977 */       if (this.useXWSSCallbacks) {
/* 2978 */         RuntimeProperties props = new RuntimeProperties(context);
/* 2979 */         callbacks = new Callback[] { (Callback)props, (Callback)pkCallback };
/*      */       } else {
/* 2981 */         callbacks = new Callback[] { (Callback)pkCallback };
/*      */       } 
/* 2983 */       this._handler.handle(callbacks);
/*      */       
/* 2985 */       return pkCallback.getKey();
/* 2986 */     } catch (Exception e) {
/* 2987 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("PrivateKeyCallback.SubjectKeyIDRequest"), new Object[] { "PrivateKeyCallback.SubjectKeyIDRequest" });
/*      */       
/* 2989 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void validateSAMLAssertion(Map context, XMLStreamReader assertion) throws XWSSecurityException {
/* 2995 */     if (this.sValidator != null) {
/*      */       try {
/* 2997 */         if (this.sValidator instanceof ValidatorExtension) {
/* 2998 */           ((ValidatorExtension)this.sValidator).setRuntimeProperties(context);
/*      */         }
/* 3000 */         if (this.sValidator instanceof SAMLValidator) {
/* 3001 */           ((SAMLValidator)this.sValidator).validate(assertion, context, getRequesterSubject(context));
/*      */         } else {
/* 3003 */           this.sValidator.validate(assertion);
/*      */         }
/*      */       
/* 3006 */       } catch (com.sun.xml.wss.impl.callback.SAMLAssertionValidator.SAMLValidationException e) {
/* 3007 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0716_FAILED_VALIDATE_SAML_ASSERTION(), (Throwable)e);
/* 3008 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final XMLStreamReader assertion) {
/* 3014 */     if (this.sValidator instanceof SAMLValidator) {
/*      */       return;
/*      */     }
/*      */     
/* 3018 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run()
/*      */           {
/* 3022 */             subject.getPublicCredentials().add(assertion);
/* 3023 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSelfCertificate(X509Certificate cert) {
/* 3030 */     return false;
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
/*      */   private Class loadClass(String classname) throws XWSSecurityException {
/* 3079 */     if (classname == null) {
/* 3080 */       return null;
/*      */     }
/* 3082 */     Class<?> ret = null;
/* 3083 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 3084 */     if (loader != null) {
/*      */       try {
/* 3086 */         ret = loader.loadClass(classname);
/* 3087 */         return ret;
/* 3088 */       } catch (ClassNotFoundException e) {
/* 3089 */         if (log.isLoggable(Level.FINE)) {
/* 3090 */           log.log(Level.FINE, "Failed to load class Thread Context ClassLoader..." + classname);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 3095 */     loader = getClass().getClassLoader();
/*      */     try {
/* 3097 */       ret = loader.loadClass(classname);
/* 3098 */       return ret;
/* 3099 */     } catch (ClassNotFoundException e) {
/* 3100 */       if (log.isLoggable(Level.FINE)) {
/* 3101 */         log.log(Level.FINE, "Failed to load class using this.getClass().getClassLoader()..." + classname);
/*      */       }
/*      */       
/* 3104 */       if (log.isLoggable(Level.FINE)) {
/* 3105 */         log.log(Level.FINE, "Calling loadUsingResourceLoader to load class.." + classname);
/*      */       }
/* 3107 */       if (ret == null) {
/* 3108 */         ret = loadUsingResourceLoader(classname);
/* 3109 */         if (ret != null) {
/* 3110 */           return ret;
/*      */         }
/*      */       } 
/*      */       
/* 3114 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0714_ERROR_GETTING_USER_CLASS(classname), new Object[] { classname });
/* 3115 */       throw new XWSSecurityException("Could not find User Class " + classname);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, Subject bootStrapSubject) {
/* 3121 */     SecurityUtil.copySubject(subject, bootStrapSubject);
/*      */   }
/*      */   
/*      */   public KerberosContext doKerberosLogin() throws XWSSecurityException {
/* 3125 */     if (this.krbLoginModule == null || this.krbLoginModule.equals("")) {
/* 3126 */       throw new XWSSecurityException("Login Module for Kerberos login is not set or could not be obtained");
/*      */     }
/* 3128 */     if (this.krbServicePrincipal == null || this.krbServicePrincipal.equals("")) {
/* 3129 */       throw new XWSSecurityException("Kerberos Service Principal is not set or could not be obtained");
/*      */     }
/* 3131 */     return (new KerberosLogin()).login(this.krbLoginModule, this.krbServicePrincipal, this.krbCredentialDelegation);
/*      */   }
/*      */   
/*      */   public KerberosContext doKerberosLogin(byte[] tokenValue) throws XWSSecurityException {
/* 3135 */     return (new KerberosLogin()).login(this.krbLoginModule, tokenValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final GSSName clientCred, final GSSCredential gssCred) {
/*      */     try {
/* 3143 */       final KerberosPrincipal kerbPrincipal = new KerberosPrincipal(clientCred.toString());
/* 3144 */       CallerPrincipalCallback pvCallback = new CallerPrincipalCallback(subject, kerbPrincipal.getName());
/* 3145 */       Callback[] callbacks = { (Callback)pvCallback };
/* 3146 */       this._handler.handle(callbacks);
/*      */ 
/*      */       
/* 3149 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/* 3152 */               subject.getPrincipals().add(kerbPrincipal);
/* 3153 */               subject.getPublicCredentials().add(clientCred);
/* 3154 */               if (gssCred != null) {
/* 3155 */                 subject.getPrivateCredentials().add(gssCred);
/*      */               }
/* 3157 */               return null;
/*      */             }
/*      */           });
/*      */     }
/* 3161 */     catch (Exception e) {
/* 3162 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("CallerPrincipalCallback"), new Object[] { "CallerPrincipalCallback" });
/*      */       
/* 3164 */       throw new XWSSecurityRuntimeException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   class PriviledgedHandler
/*      */     implements CallbackHandler {
/* 3170 */     CallbackHandler delegate = null;
/*      */     
/*      */     public PriviledgedHandler(CallbackHandler handler) {
/* 3173 */       this.delegate = handler;
/*      */     }
/*      */     
/*      */     public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
/* 3177 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*      */               try {
/* 3181 */                 WSITProviderSecurityEnvironment.PriviledgedHandler.this.delegate.handle(callbacks);
/* 3182 */                 return null;
/* 3183 */               } catch (Exception ex) {
/* 3184 */                 throw new XWSSecurityRuntimeException(ex);
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\WSITProviderSecurityEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */