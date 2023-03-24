/*      */ package com.sun.xml.wss.impl.misc;
/*      */ 
/*      */ import com.sun.xml.wss.AliasSelector;
/*      */ import com.sun.xml.wss.RealmAuthenticationAdapter;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*      */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*      */ import com.sun.xml.wss.impl.callback.CertStoreCallback;
/*      */ import com.sun.xml.wss.impl.callback.CertificateValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.DecryptionKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*      */ import com.sun.xml.wss.impl.callback.EncryptionKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.KeyStoreCallback;
/*      */ import com.sun.xml.wss.impl.callback.PasswordCallback;
/*      */ import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.PrivateKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.SAMLAssertionValidator;
/*      */ import com.sun.xml.wss.impl.callback.SAMLCallback;
/*      */ import com.sun.xml.wss.impl.callback.SAMLValidator;
/*      */ import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.TimestampValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.UsernameCallback;
/*      */ import com.sun.xml.wss.impl.callback.ValidatorExtension;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.PrivateKeyBinding;
/*      */ import com.sun.xml.wss.logging.impl.misc.LogStringsMessages;
/*      */ import com.sun.xml.wss.util.XWSSUtil;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PublicKey;
/*      */ import java.security.UnrecoverableKeyException;
/*      */ import java.security.cert.CertPath;
/*      */ import java.security.cert.CertPathValidator;
/*      */ import java.security.cert.CertSelector;
/*      */ import java.security.cert.CertStore;
/*      */ import java.security.cert.CertStoreException;
/*      */ import java.security.cert.Certificate;
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
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TimeZone;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.NameCallback;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.x500.X500Principal;
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
/*      */ public class DefaultCallbackHandler
/*      */   implements CallbackHandler
/*      */ {
/*      */   public static final String KEYSTORE_URL = "keystore.url";
/*      */   public static final String KEYSTORE_TYPE = "keystore.type";
/*      */   public static final String KEYSTORE_PASSWORD = "keystore.password";
/*      */   public static final String KEY_PASSWORD = "key.password";
/*      */   public static final String MY_ALIAS = "my.alias";
/*      */   public static final String MY_USERNAME = "my.username";
/*      */   public static final String MY_PASSWORD = "my.password";
/*      */   public static final String MY_ITERATIONS = "my.iterations";
/*      */   public static final String TRUSTSTORE_URL = "truststore.url";
/*      */   public static final String TRUSTSTORE_TYPE = "truststore.type";
/*      */   public static final String TRUSTSTORE_PASSWORD = "truststore.password";
/*      */   public static final String PEER_ENTITY_ALIAS = "peerentity.alias";
/*      */   public static final String STS_ALIAS = "sts.alias";
/*      */   public static final String SERVICE_ALIAS = "service.alias";
/*      */   public static final String USERNAME_CBH = "username.callback.handler";
/*      */   public static final String PASSWORD_CBH = "password.callback.handler";
/*      */   public static final String SAML_CBH = "saml.callback.handler";
/*      */   public static final String KEYSTORE_CBH = "keystore.callback.handler";
/*      */   public static final String TRUSTSTORE_CBH = "truststore.callback.handler";
/*      */   public static final String USERNAME_VALIDATOR = "username.validator";
/*      */   public static final String SAML_VALIDATOR = "saml.validator";
/*      */   public static final String TIMESTAMP_VALIDATOR = "timestamp.validator";
/*      */   public static final String CERTIFICATE_VALIDATOR = "certificate.validator";
/*      */   public static final String MAX_CLOCK_SKEW_PROPERTY = "max.clock.skew";
/*      */   public static final String MAX_NONCE_AGE_PROPERTY = "max.nonce.age";
/*      */   public static final String TIMESTAMP_FRESHNESS_LIMIT_PROPERTY = "timestamp.freshness.limit";
/*      */   public static final String REVOCATION_ENABLED = "revocation.enabled";
/*      */   public static final String CERTSTORE_CBH = "certstore.cbh";
/*      */   public static final String CERTSTORE_CERTSELECTOR = "certstore.certselector";
/*      */   public static final String CERTSTORE_CRLSELECTOR = "certstore.crlselector";
/*      */   public static final String KEYSTORE_CERTSELECTOR = "keystore.certselector";
/*      */   public static final String TRUSTSTORE_CERTSELECTOR = "truststore.certselector";
/*      */   public static final String JMAC_CALLBACK_HANDLER = "jmac.callbackhandler";
/*      */   public static final String KRB5_LOGIN_MODULE = "krb5.login.module";
/*      */   public static final String KRB5_SERVICE_PRINCIPAL = "krb5.service.principal";
/*      */   public static final String KRB5_CREDENTIAL_DELEGATION = "krb5.credential.delegation";
/*      */   public static final String USE_XWSS_CALLBACKS = "user.xwss.callbacks";
/*      */   public static final String JAAS_KEYSTORE_LOGIN_MODULE = "jaas.loginmodule.for.keystore";
/*      */   private String keyStoreURL;
/*      */   private String keyStorePassword;
/*      */   private String keyStoreType;
/*      */   private String myAlias;
/*      */   private String keyPwd;
/*  186 */   private char[] keyPassword = null;
/*      */   
/*      */   private String trustStoreURL;
/*      */   
/*      */   private String trustStorePassword;
/*      */   
/*      */   private String trustStoreType;
/*      */   private String peerEntityAlias;
/*      */   private String certStoreCBHClassName;
/*      */   private String certSelectorClassName;
/*      */   private String crlSelectorClassName;
/*      */   private String keystoreCertSelectorClassName;
/*      */   private String truststoreCertSelectorClassName;
/*      */   private String myUsername;
/*      */   private String myPassword;
/*      */   private KeyStore keyStore;
/*      */   private KeyStore trustStore;
/*      */   private Class usernameCbHandler;
/*      */   private Class passwordCbHandler;
/*      */   private Class samlCbHandler;
/*      */   private Class keystoreCbHandler;
/*      */   private Class truststoreCbHandler;
/*      */   private Class certstoreCbHandler;
/*      */   private Class certSelectorClass;
/*      */   private Class crlSelectorClass;
/*      */   private Class usernameValidator;
/*      */   private Class timestampValidator;
/*      */   private Class samlValidator;
/*      */   private Class certificateValidator;
/*      */   protected long maxClockSkewG;
/*      */   protected long timestampFreshnessLimitG;
/*      */   protected long maxNonceAge;
/*      */   protected String revocationEnabledAttr;
/*      */   protected boolean revocationEnabled = false;
/*  220 */   protected String mcs = null;
/*  221 */   protected String tfl = null;
/*  222 */   protected String mna = null;
/*  223 */   private static Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.misc", "com.sun.xml.wss.logging.impl.misc.LogStrings");
/*  224 */   private static final String fileSeparator = System.getProperty("file.separator");
/*  225 */   private static final UnsupportedCallbackException unsupported = new UnsupportedCallbackException(null, "Unsupported Callback Type Encountered");
/*      */   
/*  227 */   private static final URI ISSUE_REQUEST_URI = URI.create("http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue");
/*      */   
/*      */   private CallbackHandler usernameHandler;
/*      */   private CallbackHandler passwordHandler;
/*      */   private CallbackHandler samlHandler;
/*      */   private CallbackHandler certstoreHandler;
/*      */   private CallbackHandler keystoreHandler;
/*      */   private CallbackHandler truststoreHandler;
/*      */   private PasswordValidationCallback.PasswordValidator pwValidator;
/*      */   private TimestampValidationCallback.TimestampValidator tsValidator;
/*      */   private CertificateValidationCallback.CertificateValidator certValidator;
/*      */   private SAMLAssertionValidator sValidator;
/*      */   private CertificateValidationCallback.CertificateValidator defaultCertValidator;
/*      */   private TimestampValidationCallback.TimestampValidator defaultTSValidator;
/*  241 */   private RealmAuthenticationAdapter usernameAuthenticator = null;
/*  242 */   private RealmAuthenticationAdapter defRealmAuthenticator = null;
/*  243 */   private CertStore certStore = null;
/*      */   
/*      */   private Class keystoreCertSelectorClass;
/*      */   private Class truststoreCertSelectorClass;
/*      */   private String useXWSSCallbacksStr;
/*      */   private boolean useXWSSCallbacks;
/*      */   
/*      */   public DefaultCallbackHandler(String clientOrServer, Properties assertions) throws XWSSecurityException {
/*  251 */     Properties properties = null;
/*  252 */     if (assertions != null && !assertions.isEmpty()) {
/*  253 */       properties = assertions;
/*      */     } else {
/*      */       
/*  256 */       properties = new Properties();
/*  257 */       String resource = clientOrServer + "-security-env.properties";
/*  258 */       InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
/*  259 */       if (in != null) {
/*      */         try {
/*  261 */           properties.load(in);
/*  262 */         } catch (IOException ex) {
/*  263 */           throw new XWSSecurityException(ex);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  271 */     this.keyStoreURL = properties.getProperty("keystore.url");
/*  272 */     this.keyStoreURL = resolveHome(this.keyStoreURL);
/*  273 */     this.keyStoreType = properties.getProperty("keystore.type");
/*  274 */     this.keyStorePassword = properties.getProperty("keystore.password");
/*  275 */     this.keyPwd = properties.getProperty("key.password");
/*  276 */     this.myAlias = properties.getProperty("my.alias");
/*  277 */     this.myUsername = properties.getProperty("my.username");
/*  278 */     this.myPassword = properties.getProperty("my.password");
/*      */ 
/*      */     
/*  281 */     this.trustStoreURL = properties.getProperty("truststore.url");
/*  282 */     this.trustStoreURL = resolveHome(this.trustStoreURL);
/*  283 */     this.keyStoreType = properties.getProperty("keystore.type");
/*  284 */     this.trustStoreType = properties.getProperty("truststore.type");
/*  285 */     this.trustStorePassword = properties.getProperty("truststore.password");
/*  286 */     this.peerEntityAlias = properties.getProperty("peerentity.alias");
/*      */ 
/*      */ 
/*      */     
/*  290 */     this.certStoreCBHClassName = properties.getProperty("certstore.cbh");
/*  291 */     this.certSelectorClassName = properties.getProperty("certstore.certselector");
/*  292 */     this.crlSelectorClassName = properties.getProperty("certstore.crlselector");
/*      */     
/*  294 */     this.keystoreCertSelectorClassName = properties.getProperty("keystore.certselector");
/*  295 */     this.truststoreCertSelectorClassName = properties.getProperty("truststore.certselector");
/*      */     
/*  297 */     String uCBH = properties.getProperty("username.callback.handler");
/*  298 */     String pCBH = properties.getProperty("password.callback.handler");
/*  299 */     String sCBH = properties.getProperty("saml.callback.handler");
/*  300 */     String keystoreCBH = properties.getProperty("keystore.callback.handler");
/*  301 */     String truststoreCBH = properties.getProperty("truststore.callback.handler");
/*  302 */     String uV = properties.getProperty("username.validator");
/*  303 */     String sV = properties.getProperty("saml.validator");
/*  304 */     String tV = properties.getProperty("timestamp.validator");
/*  305 */     String cV = properties.getProperty("certificate.validator");
/*      */     
/*  307 */     this.usernameCbHandler = loadClass(uCBH);
/*  308 */     this.passwordCbHandler = loadClass(pCBH);
/*  309 */     this.samlCbHandler = loadClass(sCBH);
/*  310 */     this.keystoreCbHandler = loadClass(keystoreCBH);
/*  311 */     this.truststoreCbHandler = loadClass(truststoreCBH);
/*      */     
/*  313 */     this.usernameValidator = loadClass(uV);
/*  314 */     this.samlValidator = loadClass(sV);
/*  315 */     this.timestampValidator = loadClass(tV);
/*  316 */     this.certificateValidator = loadClass(cV);
/*      */     
/*  318 */     this.keystoreCertSelectorClass = loadClass(this.keystoreCertSelectorClassName);
/*  319 */     this.truststoreCertSelectorClass = loadClass(this.truststoreCertSelectorClassName);
/*      */     
/*  321 */     this.certstoreCbHandler = loadClass(this.certStoreCBHClassName);
/*  322 */     this.certSelectorClass = loadClass(this.certSelectorClassName);
/*  323 */     this.crlSelectorClass = loadClass(this.crlSelectorClassName);
/*      */ 
/*      */     
/*  326 */     this.mcs = properties.getProperty("max.clock.skew");
/*  327 */     this.tfl = properties.getProperty("timestamp.freshness.limit");
/*  328 */     this.mna = properties.getProperty("max.nonce.age");
/*  329 */     this.revocationEnabledAttr = properties.getProperty("revocation.enabled");
/*  330 */     if (this.revocationEnabledAttr != null) {
/*  331 */       this.revocationEnabled = Boolean.parseBoolean(this.revocationEnabledAttr);
/*      */     }
/*      */     
/*  334 */     this.useXWSSCallbacksStr = properties.getProperty("user.xwss.callbacks");
/*  335 */     if (this.useXWSSCallbacksStr != null) {
/*  336 */       this.useXWSSCallbacks = Boolean.parseBoolean(this.useXWSSCallbacksStr);
/*      */     }
/*  338 */     this.maxClockSkewG = toLong(this.mcs);
/*  339 */     this.timestampFreshnessLimitG = toLong(this.tfl);
/*  340 */     this.maxNonceAge = toLong(this.mna);
/*      */     
/*  342 */     initTrustStore();
/*  343 */     initKeyStore();
/*  344 */     initNewInstances();
/*      */     
/*  346 */     this.defaultCertValidator = new X509CertificateValidatorImpl();
/*  347 */     this.defaultTSValidator = new DefaultTimestampValidator();
/*      */   }
/*      */ 
/*      */   
/*      */   public DefaultCallbackHandler(String clientOrServer, Properties assertions, RealmAuthenticationAdapter adapter) throws Exception {
/*  352 */     this(clientOrServer, assertions);
/*  353 */     this.usernameAuthenticator = adapter;
/*  354 */     if (adapter == null) {
/*  355 */       this.defRealmAuthenticator = RealmAuthenticationAdapter.newInstance(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleUsernameCallback(UsernameCallback cb) throws IOException, UnsupportedCallbackException {
/*  366 */     if (this.myUsername != null) {
/*  367 */       cb.setUsername(this.myUsername);
/*      */     } else {
/*  369 */       String username = (String)cb.getRuntimeProperties().get("username");
/*  370 */       if (username == null)
/*      */       {
/*  372 */         username = (String)cb.getRuntimeProperties().get("javax.xml.ws.security.auth.username");
/*      */       }
/*  374 */       if (username != null) {
/*  375 */         cb.setUsername(username);
/*  376 */       } else if (this.usernameHandler != null) {
/*  377 */         Callback[] cbs = null;
/*  378 */         if (this.useXWSSCallbacks) {
/*  379 */           cbs = new Callback[] { (Callback)cb };
/*  380 */           this.usernameHandler.handle(cbs);
/*      */         } else {
/*  382 */           NameCallback nc = new NameCallback("Username=");
/*  383 */           cbs = new Callback[] { nc };
/*  384 */           this.usernameHandler.handle(cbs);
/*  385 */           cb.setUsername(((NameCallback)cbs[0]).getName());
/*      */         } 
/*      */       } else {
/*      */         
/*  389 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1500_INVALID_USERNAME_HANDLER());
/*  390 */         throw new UnsupportedCallbackException(null, "Username Handler Not Configured");
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
/*      */   private void handlePasswordCallback(PasswordCallback cb) throws IOException, UnsupportedCallbackException {
/*  402 */     if (this.myPassword != null) {
/*  403 */       cb.setPassword(this.myPassword);
/*      */     } else {
/*  405 */       String password = (String)cb.getRuntimeProperties().get("password");
/*  406 */       if (password == null)
/*      */       {
/*  408 */         password = (String)cb.getRuntimeProperties().get("javax.xml.ws.security.auth.password");
/*      */       }
/*  410 */       if (password != null) {
/*  411 */         cb.setPassword(password);
/*  412 */       } else if (this.passwordHandler != null) {
/*  413 */         Callback[] cbs = null;
/*  414 */         if (this.useXWSSCallbacks) {
/*  415 */           cbs = new Callback[] { (Callback)cb };
/*  416 */           this.passwordHandler.handle(cbs);
/*      */         } else {
/*  418 */           PasswordCallback pc = new PasswordCallback("Password=", false);
/*  419 */           cbs = new Callback[] { pc };
/*  420 */           this.passwordHandler.handle(cbs);
/*  421 */           char[] pass = ((PasswordCallback)cbs[0]).getPassword();
/*  422 */           cb.setPassword(new String(pass));
/*      */         } 
/*      */       } else {
/*      */         
/*  426 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1525_INVALID_PASSWORD_HANDLER());
/*  427 */         throw new UnsupportedCallbackException(null, "Password Handler Not Configured");
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
/*      */   private void handlePasswordValidation(PasswordValidationCallback cb) throws IOException, UnsupportedCallbackException {
/*  439 */     if (cb.getRequest() instanceof PasswordValidationCallback.PlainTextPasswordRequest) {
/*  440 */       if (this.pwValidator != null) {
/*  441 */         cb.setValidator(this.pwValidator);
/*      */       }
/*  443 */       else if (this.usernameAuthenticator != null) {
/*  444 */         cb.setRealmAuthentcationAdapter(this.usernameAuthenticator);
/*      */       } else {
/*  446 */         cb.setRealmAuthentcationAdapter(this.defRealmAuthenticator);
/*      */       }
/*      */     
/*  449 */     } else if (cb.getRequest() instanceof PasswordValidationCallback.DigestPasswordRequest) {
/*  450 */       PasswordValidationCallback.DigestPasswordRequest request = (PasswordValidationCallback.DigestPasswordRequest)cb.getRequest();
/*      */       
/*  452 */       if (this.pwValidator != null && this.pwValidator instanceof PasswordValidationCallback.WsitDigestPasswordValidator) {
/*  453 */         ((PasswordValidationCallback.WsitDigestPasswordValidator)this.pwValidator).setPassword((PasswordValidationCallback.Request)request);
/*  454 */         cb.setValidator(this.pwValidator);
/*      */       } 
/*  456 */     } else if (cb.getRequest() instanceof PasswordValidationCallback.DerivedKeyPasswordRequest) {
/*  457 */       PasswordValidationCallback.DerivedKeyPasswordRequest request = (PasswordValidationCallback.DerivedKeyPasswordRequest)cb.getRequest();
/*      */       
/*  459 */       if (this.pwValidator != null && this.pwValidator instanceof PasswordValidationCallback.DerivedKeyPasswordValidator) {
/*  460 */         ((PasswordValidationCallback.DerivedKeyPasswordValidator)this.pwValidator).setPassword((PasswordValidationCallback.Request)request);
/*  461 */         cb.setValidator(this.pwValidator);
/*      */       } 
/*      */     } else {
/*  464 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1503_UNSUPPORTED_REQUESTTYPE());
/*  465 */       throw new UnsupportedCallbackException(null, "Unsupported Request Type for Password Validation");
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleTimestampValidation(TimestampValidationCallback cb) throws IOException, UnsupportedCallbackException {
/*  470 */     if (this.tsValidator != null) {
/*  471 */       cb.setValidator(this.tsValidator);
/*      */     } else {
/*      */       
/*  474 */       cb.setValidator(this.defaultTSValidator);
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
/*      */   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
/*  486 */     for (int i = 0; i < callbacks.length; i++) {
/*      */       
/*  488 */       if (callbacks[i] instanceof UsernameCallback) {
/*  489 */         UsernameCallback cb = (UsernameCallback)callbacks[i];
/*  490 */         handleUsernameCallback(cb);
/*      */       }
/*  492 */       else if (callbacks[i] instanceof PasswordCallback) {
/*  493 */         PasswordCallback cb = (PasswordCallback)callbacks[i];
/*  494 */         handlePasswordCallback(cb);
/*      */       }
/*  496 */       else if (callbacks[i] instanceof PasswordValidationCallback) {
/*  497 */         PasswordValidationCallback cb = (PasswordValidationCallback)callbacks[i];
/*  498 */         handlePasswordValidation(cb);
/*      */       }
/*  500 */       else if (callbacks[i] instanceof TimestampValidationCallback) {
/*  501 */         TimestampValidationCallback cb = (TimestampValidationCallback)callbacks[i];
/*  502 */         handleTimestampValidation(cb);
/*      */       }
/*  504 */       else if (callbacks[i] instanceof SignatureVerificationKeyCallback) {
/*      */         
/*  506 */         SignatureVerificationKeyCallback cb = (SignatureVerificationKeyCallback)callbacks[i];
/*      */         
/*  508 */         if (cb.getRequest() instanceof SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest) {
/*      */           
/*  510 */           SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest request = (SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest)cb.getRequest();
/*      */           
/*  512 */           X509Certificate cert = getCertificateFromTrustStore(request.getSubjectKeyIdentifier(), cb.getRuntimeProperties());
/*      */ 
/*      */           
/*  515 */           request.setX509Certificate(cert);
/*      */         }
/*  517 */         else if (cb.getRequest() instanceof SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest) {
/*      */           
/*  519 */           SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest request = (SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest)cb.getRequest();
/*      */           
/*  521 */           X509Certificate cert = getCertificateFromTrustStore(request.getIssuerName(), request.getSerialNumber(), cb.getRuntimeProperties());
/*      */ 
/*      */ 
/*      */           
/*  525 */           request.setX509Certificate(cert);
/*      */         }
/*  527 */         else if (cb.getRequest() instanceof SignatureVerificationKeyCallback.ThumbprintBasedRequest) {
/*  528 */           SignatureVerificationKeyCallback.ThumbprintBasedRequest request = (SignatureVerificationKeyCallback.ThumbprintBasedRequest)cb.getRequest();
/*      */           
/*  530 */           X509Certificate cert = getCertificateFromTrustStoreForThumbprint(request.getThumbprintIdentifier(), cb.getRuntimeProperties());
/*      */ 
/*      */           
/*  533 */           request.setX509Certificate(cert);
/*      */         }
/*  535 */         else if (cb.getRequest() instanceof SignatureVerificationKeyCallback.PublicKeyBasedRequest) {
/*  536 */           SignatureVerificationKeyCallback.PublicKeyBasedRequest request = (SignatureVerificationKeyCallback.PublicKeyBasedRequest)cb.getRequest();
/*      */           
/*  538 */           X509Certificate cert = getCertificateFromTrustStoreForSAML(request.getPublicKey(), cb.getRuntimeProperties());
/*      */           
/*  540 */           request.setX509Certificate(cert);
/*      */         } else {
/*  542 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1504_UNSUPPORTED_CALLBACK_TYPE());
/*  543 */           throw unsupported;
/*      */         }
/*      */       
/*  546 */       } else if (callbacks[i] instanceof SignatureKeyCallback) {
/*  547 */         SignatureKeyCallback cb = (SignatureKeyCallback)callbacks[i];
/*      */         
/*  549 */         if (cb.getRequest() instanceof SignatureKeyCallback.DefaultPrivKeyCertRequest) {
/*      */           
/*  551 */           SignatureKeyCallback.DefaultPrivKeyCertRequest request = (SignatureKeyCallback.DefaultPrivKeyCertRequest)cb.getRequest();
/*      */           
/*  553 */           getDefaultPrivKeyCert(request, cb.getRuntimeProperties());
/*      */         }
/*  555 */         else if (cb.getRequest() instanceof SignatureKeyCallback.AliasPrivKeyCertRequest) {
/*  556 */           SignatureKeyCallback.AliasPrivKeyCertRequest request = (SignatureKeyCallback.AliasPrivKeyCertRequest)cb.getRequest();
/*      */           
/*  558 */           String alias = request.getAlias();
/*      */           try {
/*  560 */             X509Certificate cert = (X509Certificate)this.keyStore.getCertificate(alias);
/*      */             
/*  562 */             request.setX509Certificate(cert);
/*      */             
/*  564 */             PrivateKey privKey = getPrivateKey(cb.getRuntimeProperties(), alias);
/*      */ 
/*      */             
/*  567 */             request.setPrivateKey(privKey);
/*  568 */           } catch (Exception e) {
/*  569 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/*  570 */             throw new RuntimeException(e);
/*      */           } 
/*      */         } else {
/*      */           
/*  574 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1504_UNSUPPORTED_CALLBACK_TYPE());
/*  575 */           throw unsupported;
/*      */         }
/*      */       
/*  578 */       } else if (callbacks[i] instanceof DecryptionKeyCallback) {
/*  579 */         DecryptionKeyCallback cb = (DecryptionKeyCallback)callbacks[i];
/*      */         
/*  581 */         if (cb.getRequest() instanceof DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest) {
/*  582 */           DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest request = (DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest)cb.getRequest();
/*      */           
/*  584 */           byte[] ski = request.getSubjectKeyIdentifier();
/*  585 */           PrivateKey privKey = getPrivateKey(ski, cb.getRuntimeProperties());
/*  586 */           request.setPrivateKey(privKey);
/*      */         }
/*  588 */         else if (cb.getRequest() instanceof DecryptionKeyCallback.X509IssuerSerialBasedRequest) {
/*  589 */           DecryptionKeyCallback.X509IssuerSerialBasedRequest request = (DecryptionKeyCallback.X509IssuerSerialBasedRequest)cb.getRequest();
/*      */           
/*  591 */           String issuerName = request.getIssuerName();
/*  592 */           BigInteger serialNumber = request.getSerialNumber();
/*  593 */           PrivateKey privKey = getPrivateKey(issuerName, serialNumber, cb.getRuntimeProperties());
/*  594 */           request.setPrivateKey(privKey);
/*      */         }
/*  596 */         else if (cb.getRequest() instanceof DecryptionKeyCallback.X509CertificateBasedRequest) {
/*  597 */           DecryptionKeyCallback.X509CertificateBasedRequest request = (DecryptionKeyCallback.X509CertificateBasedRequest)cb.getRequest();
/*      */           
/*  599 */           X509Certificate cert = request.getX509Certificate();
/*  600 */           PrivateKey privKey = getPrivateKey(cert, cb.getRuntimeProperties());
/*  601 */           request.setPrivateKey(privKey);
/*      */         }
/*  603 */         else if (cb.getRequest() instanceof DecryptionKeyCallback.ThumbprintBasedRequest) {
/*  604 */           DecryptionKeyCallback.ThumbprintBasedRequest request = (DecryptionKeyCallback.ThumbprintBasedRequest)cb.getRequest();
/*      */           
/*  606 */           byte[] ski = request.getThumbprintIdentifier();
/*  607 */           PrivateKey privKey = getPrivateKeyForThumbprint(ski, cb.getRuntimeProperties());
/*  608 */           request.setPrivateKey(privKey);
/*  609 */         } else if (cb.getRequest() instanceof DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest) {
/*  610 */           DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest request = (DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest)cb.getRequest();
/*      */ 
/*      */           
/*  613 */           PrivateKey privKey = getPrivateKeyFromKeyStore(request.getPublicKey(), cb.getRuntimeProperties());
/*  614 */           request.setPrivateKey(privKey);
/*      */         } else {
/*  616 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1504_UNSUPPORTED_CALLBACK_TYPE());
/*  617 */           throw unsupported;
/*      */         }
/*      */       
/*  620 */       } else if (callbacks[i] instanceof EncryptionKeyCallback) {
/*  621 */         EncryptionKeyCallback cb = (EncryptionKeyCallback)callbacks[i];
/*      */         
/*  623 */         if (cb.getRequest() instanceof EncryptionKeyCallback.AliasX509CertificateRequest) {
/*  624 */           EncryptionKeyCallback.AliasX509CertificateRequest request = (EncryptionKeyCallback.AliasX509CertificateRequest)cb.getRequest();
/*      */ 
/*      */           
/*  627 */           String alias = request.getAlias();
/*  628 */           if ("".equals(alias) || alias == null) {
/*  629 */             getDefaultCertificateFromTrustStore(cb.getRuntimeProperties(), request);
/*      */           } else {
/*      */             try {
/*  632 */               KeyStore tStore = getTrustStore(cb.getRuntimeProperties());
/*  633 */               if (tStore != null) {
/*  634 */                 X509Certificate cert = (X509Certificate)tStore.getCertificate(alias);
/*      */                 
/*  636 */                 request.setX509Certificate(cert);
/*      */               } 
/*  638 */             } catch (Exception e) {
/*  639 */               log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), e);
/*  640 */               throw new RuntimeException(e);
/*      */             }
/*      */           
/*      */           } 
/*  644 */         } else if (cb.getRequest() instanceof EncryptionKeyCallback.PublicKeyBasedRequest) {
/*  645 */           EncryptionKeyCallback.PublicKeyBasedRequest request = (EncryptionKeyCallback.PublicKeyBasedRequest)cb.getRequest();
/*      */           
/*      */           try {
/*  648 */             X509Certificate cert = getCertificateFromTrustStoreForSAML(request.getPublicKey(), cb.getRuntimeProperties());
/*      */             
/*  650 */             request.setX509Certificate(cert);
/*  651 */           } catch (Exception e) {
/*  652 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), e);
/*  653 */             throw new RuntimeException(e);
/*      */           } 
/*  655 */         } else if (cb.getRequest() instanceof EncryptionKeyCallback.AliasSymmetricKeyRequest) {
/*  656 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1504_UNSUPPORTED_CALLBACK_TYPE());
/*  657 */           throw unsupported;
/*      */         }
/*      */       
/*  660 */       } else if (callbacks[i] instanceof CertificateValidationCallback) {
/*  661 */         CertificateValidationCallback cb = (CertificateValidationCallback)callbacks[i];
/*  662 */         getTrustStore(cb.getRuntimeProperties());
/*  663 */         cb.setValidator(this.certValidator);
/*      */       
/*      */       }
/*  666 */       else if (callbacks[i] instanceof DynamicPolicyCallback) {
/*  667 */         DynamicPolicyCallback dp = (DynamicPolicyCallback)callbacks[i];
/*  668 */         SecurityPolicy policy = dp.getSecurityPolicy();
/*  669 */         if (policy instanceof AuthenticationTokenPolicy.SAMLAssertionBinding) {
/*  670 */           AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)((AuthenticationTokenPolicy.SAMLAssertionBinding)policy).clone();
/*      */ 
/*      */           
/*  673 */           if (samlBinding.getAssertion() == null && samlBinding.getAuthorityBinding() == null && samlBinding.getAssertionReader() == null) {
/*  674 */             populateAssertion(samlBinding, dp);
/*  675 */           } else if (samlBinding.getAssertion() != null || samlBinding.getAssertionReader() != null) {
/*  676 */             Subject subj = (Subject)dp.getRuntimeProperties().get("javax.security.auth.Subject");
/*      */             
/*  678 */             validateSAMLAssertion(samlBinding, subj, dp.getRuntimeProperties());
/*  679 */           } else if (samlBinding.getAuthorityBinding() != null && samlBinding.getAssertionId() != null) {
/*  680 */             locateSAMLAssertion(samlBinding, dp.getRuntimeProperties());
/*      */           } else {
/*  682 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1506_INVALID_SAML_POLICY());
/*  683 */             throw new UnsupportedCallbackException(null, "SAML Assertion not present in the Policy");
/*      */           } 
/*      */         } 
/*      */       } else {
/*  687 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1504_UNSUPPORTED_CALLBACK_TYPE());
/*  688 */         throw unsupported;
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
/*      */   private boolean isMyCert(X509Certificate certificate, Map runtimeProps) {
/*      */     try {
/*  701 */       SignatureKeyCallback.DefaultPrivKeyCertRequest request = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*      */       
/*  703 */       getDefaultPrivKeyCert(request, runtimeProps);
/*  704 */       X509Certificate cert = request.getX509Certificate();
/*  705 */       if (cert != null && cert.equals(certificate)) {
/*  706 */         return true;
/*      */       }
/*  708 */     } catch (IOException ex) {}
/*      */ 
/*      */     
/*  711 */     return false;
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
/*      */   private void populateAssertion(AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, DynamicPolicyCallback dp) throws IOException, UnsupportedCallbackException {
/*  724 */     if ("SV".equals(samlBinding.getAssertionType())) {
/*      */       
/*  726 */       if (this.samlHandler != null) {
/*      */         
/*  728 */         SAMLCallback sc = new SAMLCallback();
/*  729 */         SecurityUtil.copy(sc.getRuntimeProperties(), dp.getRuntimeProperties());
/*  730 */         sc.setConfirmationMethod("SV-Assertion");
/*  731 */         sc.setSAMLVersion(samlBinding.getSAMLVersion());
/*  732 */         Callback[] cbs = { (Callback)sc };
/*  733 */         this.samlHandler.handle(cbs);
/*  734 */         samlBinding.setAssertion(sc.getAssertionElement());
/*  735 */         samlBinding.setAssertion(sc.getAssertionReader());
/*  736 */         samlBinding.setAuthorityBinding(sc.getAuthorityBindingElement());
/*  737 */         dp.setSecurityPolicy((SecurityPolicy)samlBinding);
/*  738 */         samlBinding.setAssertionId(sc.getAssertionId());
/*  739 */         samlBinding.setSAMLVersion(sc.getSAMLVersion());
/*      */       } else {
/*  741 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1507_NO_SAML_CALLBACK_HANDLER());
/*  742 */         throw new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  747 */     else if (this.samlHandler != null) {
/*      */       
/*  749 */       SAMLCallback sc = new SAMLCallback();
/*  750 */       SecurityUtil.copy(sc.getRuntimeProperties(), dp.getRuntimeProperties());
/*  751 */       sc.setConfirmationMethod("HOK-Assertion");
/*  752 */       sc.setSAMLVersion(samlBinding.getSAMLVersion());
/*  753 */       Callback[] cbs = { (Callback)sc };
/*  754 */       this.samlHandler.handle(cbs);
/*  755 */       samlBinding.setAssertion(sc.getAssertionElement());
/*  756 */       samlBinding.setAssertion(sc.getAssertionReader());
/*  757 */       samlBinding.setAuthorityBinding(sc.getAuthorityBindingElement());
/*  758 */       samlBinding.setAssertionId(sc.getAssertionId());
/*  759 */       samlBinding.setSAMLVersion(sc.getSAMLVersion());
/*  760 */       dp.setSecurityPolicy((SecurityPolicy)samlBinding);
/*  761 */       PrivateKeyBinding pkBinding = (PrivateKeyBinding)samlBinding.newPrivateKeyBinding();
/*      */       
/*  763 */       SignatureKeyCallback.DefaultPrivKeyCertRequest request = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*      */       
/*  765 */       getDefaultPrivKeyCert(request, dp.getRuntimeProperties());
/*  766 */       pkBinding.setPrivateKey(request.getPrivateKey());
/*      */     } else {
/*      */       
/*  769 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1507_NO_SAML_CALLBACK_HANDLER());
/*  770 */       throw new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion");
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
/*      */   private void validateSAMLAssertion(AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, Subject subj, Map props) throws IOException, UnsupportedCallbackException {
/*  786 */     if (this.sValidator != null) {
/*      */       try {
/*  788 */         if (this.sValidator instanceof ValidatorExtension) {
/*  789 */           ((ValidatorExtension)this.sValidator).setRuntimeProperties(props);
/*      */         }
/*  791 */         if (samlBinding.getAssertion() != null) {
/*  792 */           if (this.sValidator instanceof SAMLValidator) {
/*  793 */             ((SAMLValidator)this.sValidator).validate(samlBinding.getAssertion(), props, subj);
/*      */           } else {
/*  795 */             this.sValidator.validate(samlBinding.getAssertion());
/*      */           } 
/*  797 */         } else if (samlBinding.getAssertionReader() != null) {
/*  798 */           if (this.sValidator instanceof SAMLValidator) {
/*  799 */             ((SAMLValidator)this.sValidator).validate(samlBinding.getAssertionReader(), props, subj);
/*      */           } else {
/*  801 */             this.sValidator.validate(samlBinding.getAssertionReader());
/*      */           } 
/*      */         } 
/*  804 */       } catch (com.sun.xml.wss.impl.callback.SAMLAssertionValidator.SAMLValidationException e) {
/*  805 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1508_FAILED_VALIDATE_SAML_ASSERTION(), (Throwable)e);
/*  806 */         throw new RuntimeException(e);
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
/*      */   private void locateSAMLAssertion(AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, Map context) throws IOException, UnsupportedCallbackException {
/*  821 */     Element binding = samlBinding.getAuthorityBinding();
/*  822 */     String assertionId = samlBinding.getAssertionId();
/*      */ 
/*      */     
/*  825 */     if ("SV".equals(samlBinding.getAssertionType())) {
/*  826 */       if (this.samlHandler != null) {
/*  827 */         SAMLCallback sc = new SAMLCallback();
/*  828 */         sc.setConfirmationMethod("SV-Assertion");
/*  829 */         sc.setSAMLVersion(samlBinding.getSAMLVersion());
/*  830 */         sc.setAssertionId(assertionId);
/*  831 */         sc.setAuthorityBindingElement(binding);
/*  832 */         Callback[] cbs = { (Callback)sc };
/*  833 */         this.samlHandler.handle(cbs);
/*  834 */         samlBinding.setAssertion(sc.getAssertionElement());
/*  835 */         samlBinding.setAssertion(sc.getAssertionReader());
/*  836 */         samlBinding.setSAMLVersion(sc.getSAMLVersion());
/*      */       } else {
/*  838 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1507_NO_SAML_CALLBACK_HANDLER());
/*  839 */         throw new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  844 */     else if (this.samlHandler != null) {
/*  845 */       SAMLCallback sc = new SAMLCallback();
/*  846 */       sc.setConfirmationMethod("HOK-Assertion");
/*  847 */       sc.setSAMLVersion(samlBinding.getSAMLVersion());
/*  848 */       sc.setAssertionId(assertionId);
/*  849 */       sc.setAuthorityBindingElement(binding);
/*  850 */       Callback[] cbs = { (Callback)sc };
/*  851 */       this.samlHandler.handle(cbs);
/*  852 */       samlBinding.setAssertion(sc.getAssertionElement());
/*  853 */       samlBinding.setAssertion(sc.getAssertionReader());
/*  854 */       samlBinding.setSAMLVersion(sc.getSAMLVersion());
/*  855 */       PrivateKeyBinding pkBinding = (PrivateKeyBinding)samlBinding.newPrivateKeyBinding();
/*      */       
/*  857 */       SignatureKeyCallback.DefaultPrivKeyCertRequest request = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*      */       
/*  859 */       getDefaultPrivKeyCert(request, context);
/*  860 */       pkBinding.setPrivateKey(request.getPrivateKey());
/*      */     } else {
/*      */       
/*  863 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1507_NO_SAML_CALLBACK_HANDLER());
/*  864 */       throw new UnsupportedCallbackException(null, "A Required SAML Callback Handler was not specified in configuration : Cannot Populate SAML Assertion");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initTrustStore() throws XWSSecurityException {
/*      */     try {
/*  876 */       if (this.trustStoreURL == null) {
/*  877 */         if (log.isLoggable(Level.FINE)) {
/*  878 */           log.log(Level.FINE, "Got NULL for TrustStore URL");
/*      */         }
/*      */         return;
/*      */       } 
/*  882 */       if (this.trustStorePassword == null && 
/*  883 */         log.isLoggable(Level.FINE)) {
/*  884 */         log.log(Level.FINE, "Got NULL for TrustStore Password");
/*      */       }
/*      */ 
/*      */       
/*  888 */       char[] trustStorePasswordChars = null;
/*      */       
/*  890 */       Class<CallbackHandler> cbh = loadClassSilent(this.trustStorePassword);
/*  891 */       if (cbh != null) {
/*  892 */         CallbackHandler hdlr = cbh.newInstance();
/*  893 */         PasswordCallback pc = new PasswordCallback("TrustStorePassword", false);
/*      */         
/*  895 */         Callback[] cbs = { pc };
/*  896 */         hdlr.handle(cbs);
/*  897 */         trustStorePasswordChars = ((PasswordCallback)cbs[0]).getPassword();
/*      */       } else {
/*      */         
/*  900 */         trustStorePasswordChars = this.trustStorePassword.toCharArray();
/*      */       } 
/*      */       
/*  903 */       this.trustStore = KeyStore.getInstance(this.trustStoreType);
/*  904 */       InputStream is = null;
/*  905 */       URL tURL = SecurityUtil.loadFromClasspath("META-INF/" + this.trustStoreURL);
/*      */       
/*      */       try {
/*  908 */         if (tURL != null) {
/*  909 */           is = tURL.openStream();
/*      */         } else {
/*  911 */           is = new FileInputStream(this.trustStoreURL);
/*      */         } 
/*  913 */         this.trustStore.load(is, trustStorePasswordChars);
/*      */       } finally {
/*  915 */         if (is != null) {
/*  916 */           is.close();
/*      */         }
/*      */       } 
/*  919 */     } catch (Exception e) {
/*  920 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1509_FAILED_INIT_TRUSTSTORE(), e);
/*  921 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initKeyStore() throws XWSSecurityException {
/*      */     try {
/*  931 */       if (this.keyStoreURL == null) {
/*  932 */         if (log.isLoggable(Level.FINE)) {
/*  933 */           log.log(Level.FINE, "Got NULL for KeyStore URL");
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  938 */       if (this.keyStorePassword == null) {
/*  939 */         if (log.isLoggable(Level.FINE)) {
/*  940 */           log.log(Level.FINE, "Got NULL for KeyStore PASSWORD");
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  945 */       char[] keyStorePasswordChars = null;
/*      */       
/*  947 */       Class<CallbackHandler> cbh = loadClassSilent(this.keyStorePassword);
/*  948 */       if (cbh != null) {
/*  949 */         CallbackHandler hdlr = cbh.newInstance();
/*  950 */         PasswordCallback pc = new PasswordCallback("KeyStorePassword", false);
/*      */         
/*  952 */         Callback[] cbs = { pc };
/*  953 */         hdlr.handle(cbs);
/*  954 */         keyStorePasswordChars = ((PasswordCallback)cbs[0]).getPassword();
/*      */       } else {
/*      */         
/*  957 */         keyStorePasswordChars = this.keyStorePassword.toCharArray();
/*      */       } 
/*      */ 
/*      */       
/*  961 */       if (this.keyPwd == null) {
/*  962 */         this.keyPassword = keyStorePasswordChars;
/*      */       } else {
/*  964 */         initKeyPassword();
/*      */       } 
/*      */       
/*  967 */       this.keyStore = KeyStore.getInstance(this.keyStoreType);
/*  968 */       InputStream is = null;
/*  969 */       URL kURL = SecurityUtil.loadFromClasspath("META-INF/" + this.keyStoreURL);
/*      */       try {
/*  971 */         if (kURL != null) {
/*  972 */           is = kURL.openStream();
/*      */         } else {
/*  974 */           is = new FileInputStream(this.keyStoreURL);
/*      */         } 
/*  976 */         this.keyStore.load(is, keyStorePasswordChars);
/*      */       } finally {
/*  978 */         if (is != null) {
/*  979 */           is.close();
/*      */         }
/*      */       } 
/*  982 */     } catch (Exception e) {
/*  983 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1510_FAILED_INIT_KEYSTORE(), e);
/*  984 */       throw new RuntimeException(e);
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
/*      */   private X509Certificate getCertificateFromTrustStore(byte[] ski, Map runtimeProps) throws IOException {
/*      */     try {
/*  999 */       if (runtimeProps != null) {
/* 1000 */         Object obj = runtimeProps.get("server-certificate");
/* 1001 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, ski, "Identifier");
/* 1002 */         if (cert != null) {
/* 1003 */           return cert;
/*      */         }
/*      */       } 
/* 1006 */       if (getTrustStore(runtimeProps) == null && getCertStore(runtimeProps) == null) {
/* 1007 */         return null;
/*      */       }
/* 1009 */       if (this.trustStore != null) {
/* 1010 */         Enumeration<String> aliases = this.trustStore.aliases();
/* 1011 */         while (aliases.hasMoreElements()) {
/* 1012 */           String alias = aliases.nextElement();
/* 1013 */           Certificate cert = this.trustStore.getCertificate(alias);
/* 1014 */           if (cert == null || !"X.509".equals(cert.getType())) {
/*      */             continue;
/*      */           }
/* 1017 */           X509Certificate x509Cert = (X509Certificate)cert;
/* 1018 */           byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/* 1019 */           if (keyId == null) {
/*      */             continue;
/*      */           }
/*      */           
/* 1023 */           if (Arrays.equals(ski, keyId)) {
/* 1024 */             return x509Cert;
/*      */           }
/*      */         } 
/*      */       } 
/* 1028 */     } catch (Exception e) {
/* 1029 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), e);
/* 1030 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1033 */     if (this.certStore != null) {
/* 1034 */       CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1041 */       if (selector == null) {
/* 1042 */         selector = new KeyIdentifierCertSelector(ski);
/*      */       }
/* 1044 */       Collection<? extends Certificate> certs = null;
/*      */       try {
/* 1046 */         certs = this.certStore.getCertificates(selector);
/* 1047 */       } catch (CertStoreException ex) {
/* 1048 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1530_EXCEPTION_IN_CERTSTORE_LOOKUP(), ex);
/* 1049 */         throw new RuntimeException(ex);
/*      */       } 
/* 1051 */       if (certs.size() > 0) {
/* 1052 */         return certs.iterator().next();
/*      */       }
/*      */     } 
/* 1055 */     return null;
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
/*      */   private X509Certificate getCertificateFromTrustStore(String issuerName, BigInteger serialNumber, Map runtimeProps) throws IOException {
/*      */     try {
/* 1072 */       if (runtimeProps != null) {
/* 1073 */         Object obj = runtimeProps.get("server-certificate");
/* 1074 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, serialNumber, issuerName);
/* 1075 */         if (cert != null) {
/* 1076 */           return cert;
/*      */         }
/*      */       } 
/*      */       
/* 1080 */       if (getTrustStore(runtimeProps) == null && getCertStore(runtimeProps) == null) {
/* 1081 */         return null;
/*      */       }
/*      */       
/* 1084 */       if (this.certStore != null) {
/* 1085 */         CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1093 */         if (selector == null) {
/* 1094 */           selector = new IssuerNameAndSerialCertSelector(serialNumber, issuerName);
/*      */         }
/* 1096 */         Collection<? extends Certificate> certs = null;
/*      */         try {
/* 1098 */           certs = this.certStore.getCertificates(selector);
/* 1099 */         } catch (CertStoreException ex) {
/* 1100 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1530_EXCEPTION_IN_CERTSTORE_LOOKUP(), ex);
/* 1101 */           throw new RuntimeException(ex);
/*      */         } 
/* 1103 */         if (certs.size() > 0) {
/* 1104 */           return certs.iterator().next();
/*      */         }
/*      */       } 
/* 1107 */       if (this.trustStore != null) {
/* 1108 */         Enumeration<String> aliases = this.trustStore.aliases();
/* 1109 */         while (aliases.hasMoreElements()) {
/* 1110 */           String alias = aliases.nextElement();
/* 1111 */           Certificate cert = this.trustStore.getCertificate(alias);
/* 1112 */           if (cert == null || !"X.509".equals(cert.getType())) {
/*      */             continue;
/*      */           }
/* 1115 */           X509Certificate x509Cert = (X509Certificate)cert;
/*      */           
/* 1117 */           X500Principal thisIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1118 */           X500Principal issuerPrincipal = new X500Principal(issuerName);
/*      */           
/* 1120 */           BigInteger thisSerialNumber = x509Cert.getSerialNumber();
/* 1121 */           if (thisIssuerPrincipal.equals(issuerPrincipal) && thisSerialNumber.equals(serialNumber))
/*      */           {
/* 1123 */             return x509Cert;
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/* 1128 */     } catch (Exception e) {
/* 1129 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), e);
/* 1130 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1133 */     return null;
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
/*      */   public PrivateKey getPrivateKey(byte[] ski, Map runtimeProps) throws IOException {
/*      */     try {
/* 1146 */       if (runtimeProps != null) {
/* 1147 */         Object obj = runtimeProps.get("certificate");
/* 1148 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, ski, "Identifier");
/* 1149 */         if (cert != null) {
/* 1150 */           return XWSSUtil.getProgrammaticPrivateKey(runtimeProps);
/*      */         }
/*      */       } 
/* 1153 */       if (getKeyStore(runtimeProps) == null) {
/* 1154 */         return null;
/*      */       }
/* 1156 */       Enumeration<String> aliases = this.keyStore.aliases();
/* 1157 */       while (aliases.hasMoreElements()) {
/* 1158 */         String alias = aliases.nextElement();
/* 1159 */         if (!this.keyStore.isKeyEntry(alias)) {
/*      */           continue;
/*      */         }
/* 1162 */         Certificate cert = this.keyStore.getCertificate(alias);
/* 1163 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/* 1166 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1167 */         byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/* 1168 */         if (keyId == null) {
/*      */           continue;
/*      */         }
/*      */         
/* 1172 */         if (Arrays.equals(ski, keyId))
/*      */         {
/*      */           
/* 1175 */           return getPrivateKey(runtimeProps, alias);
/*      */         }
/*      */       } 
/* 1178 */     } catch (Exception e) {
/* 1179 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/* 1180 */       throw new RuntimeException(e);
/*      */     } 
/* 1182 */     return null;
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
/*      */   public PrivateKey getPrivateKey(String issuerName, BigInteger serialNumber, Map runtimeProps) throws IOException {
/*      */     try {
/* 1199 */       if (runtimeProps != null) {
/* 1200 */         Object obj = runtimeProps.get("certificate");
/* 1201 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, serialNumber, issuerName);
/* 1202 */         if (cert != null) {
/* 1203 */           return XWSSUtil.getProgrammaticPrivateKey(runtimeProps);
/*      */         }
/*      */       } 
/* 1206 */       if (getKeyStore(runtimeProps) == null) {
/* 1207 */         return null;
/*      */       }
/* 1209 */       Enumeration<String> aliases = this.keyStore.aliases();
/* 1210 */       while (aliases.hasMoreElements()) {
/* 1211 */         String alias = aliases.nextElement();
/* 1212 */         if (!this.keyStore.isKeyEntry(alias)) {
/*      */           continue;
/*      */         }
/* 1215 */         Certificate cert = this.keyStore.getCertificate(alias);
/* 1216 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/* 1219 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1220 */         X500Principal thisIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 1221 */         X500Principal issuerPrincipal = new X500Principal(issuerName);
/* 1222 */         BigInteger thisSerialNumber = x509Cert.getSerialNumber();
/* 1223 */         if (thisIssuerPrincipal.equals(issuerPrincipal) && thisSerialNumber.equals(serialNumber))
/*      */         {
/*      */           
/* 1226 */           return getPrivateKey(runtimeProps, alias);
/*      */         }
/*      */       } 
/* 1229 */     } catch (Exception e) {
/* 1230 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/* 1231 */       throw new RuntimeException(e);
/*      */     } 
/* 1233 */     return null;
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
/*      */   public PrivateKey getPrivateKey(X509Certificate certificate, Map runtimeProps) throws IOException {
/*      */     try {
/* 1247 */       if (runtimeProps != null) {
/* 1248 */         Object obj = runtimeProps.get("certificate");
/* 1249 */         if (obj != null && obj.equals(certificate)) {
/* 1250 */           return XWSSUtil.getProgrammaticPrivateKey(runtimeProps);
/*      */         }
/*      */       } 
/* 1253 */       if (getKeyStore(runtimeProps) == null) {
/* 1254 */         return null;
/*      */       }
/* 1256 */       Enumeration<String> aliases = this.keyStore.aliases();
/* 1257 */       while (aliases.hasMoreElements()) {
/* 1258 */         String alias = aliases.nextElement();
/* 1259 */         if (!this.keyStore.isKeyEntry(alias)) {
/*      */           continue;
/*      */         }
/* 1262 */         Certificate cert = this.keyStore.getCertificate(alias);
/* 1263 */         if (cert != null && cert.equals(certificate))
/*      */         {
/* 1265 */           return getPrivateKey(runtimeProps, alias);
/*      */         }
/*      */       } 
/* 1268 */     } catch (Exception e) {
/* 1269 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/* 1270 */       throw new RuntimeException(e);
/*      */     } 
/* 1272 */     return null;
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
/*      */   private void getDefaultCertificateFromTrustStore(Map context, EncryptionKeyCallback.AliasX509CertificateRequest req) throws IOException {
/* 1284 */     String currentAlias = null;
/*      */     
/* 1286 */     Object obj = context.get("server-certificate");
/* 1287 */     if (obj instanceof X509Certificate) {
/* 1288 */       req.setX509Certificate((X509Certificate)obj);
/*      */       return;
/*      */     } 
/* 1291 */     if (this.peerEntityAlias != null) {
/* 1292 */       currentAlias = this.peerEntityAlias;
/*      */     } else {
/*      */       
/* 1295 */       getCertStore(context);
/* 1296 */       if (this.certStore != null) {
/* 1297 */         CertSelector selector = null;
/* 1298 */         if (this.certSelectorClass != null) {
/* 1299 */           selector = XWSSUtil.getCertSelector(this.certSelectorClass, context);
/*      */         }
/* 1301 */         if (selector != null) {
/* 1302 */           Collection<? extends Certificate> certs = null;
/*      */           try {
/* 1304 */             certs = this.certStore.getCertificates(selector);
/* 1305 */           } catch (CertStoreException ex) {
/* 1306 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), ex);
/* 1307 */             throw new RuntimeException(ex);
/*      */           } 
/* 1309 */           if (certs.size() > 0) {
/* 1310 */             req.setX509Certificate(certs.iterator().next());
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1316 */       if (getTrustStore(context) != null) {
/* 1317 */         if (this.truststoreCertSelectorClass != null) {
/* 1318 */           CertSelector selector = XWSSUtil.getCertSelector(this.truststoreCertSelectorClass, context);
/* 1319 */           if (selector != null) {
/* 1320 */             Enumeration<String> aliases = null;
/*      */             try {
/* 1322 */               aliases = this.trustStore.aliases();
/* 1323 */             } catch (KeyStoreException ex) {
/* 1324 */               log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), ex);
/* 1325 */               throw new RuntimeException(ex);
/*      */             } 
/* 1327 */             while (aliases.hasMoreElements()) {
/* 1328 */               String currAlias = aliases.nextElement();
/* 1329 */               Certificate thisCertificate = null;
/*      */               try {
/* 1331 */                 thisCertificate = this.trustStore.getCertificate(currAlias);
/* 1332 */               } catch (KeyStoreException ex) {
/* 1333 */                 log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), ex);
/* 1334 */                 throw new RuntimeException(ex);
/*      */               } 
/* 1336 */               if (thisCertificate instanceof X509Certificate && selector.match(thisCertificate)) {
/* 1337 */                 req.setX509Certificate((X509Certificate)thisCertificate);
/*      */ 
/*      */ 
/*      */                 
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1346 */           X509Certificate cert = getDynamicCertificate(context);
/*      */           
/* 1348 */           if (cert != null) {
/* 1349 */             req.setX509Certificate(cert);
/*      */             
/*      */             return;
/*      */           } 
/* 1353 */           Enumeration<String> aliases = null;
/*      */           try {
/* 1355 */             aliases = this.trustStore.aliases();
/* 1356 */           } catch (KeyStoreException ex) {
/* 1357 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), ex);
/* 1358 */             throw new RuntimeException(ex);
/*      */           } 
/* 1360 */           while (aliases.hasMoreElements()) {
/* 1361 */             currentAlias = aliases.nextElement();
/* 1362 */             if (!"certificate-authority".equals(currentAlias) && !"root".equals(currentAlias)) {
/* 1363 */               log.log(Level.WARNING, "truststore peeralias not found,picking up the arbitrary certificate ");
/*      */               break;
/*      */             } 
/* 1366 */             currentAlias = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1373 */     if (getTrustStore(context) != null && currentAlias != null) {
/* 1374 */       X509Certificate thisCertificate = null;
/*      */       try {
/* 1376 */         thisCertificate = (X509Certificate)this.trustStore.getCertificate(currentAlias);
/* 1377 */       } catch (KeyStoreException ex) {
/* 1378 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), ex);
/* 1379 */         throw new RuntimeException(ex);
/*      */       } 
/* 1381 */       req.setX509Certificate(thisCertificate);
/*      */       return;
/*      */     } 
/* 1384 */     log.log(Level.SEVERE, LogStringsMessages.WSS_1511_FAILED_LOCATE_PEER_CERTIFICATE());
/*      */     
/* 1386 */     throw new RuntimeException("An Error occurred while locating PEER Entity certificate in TrustStore");
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
/*      */   private void getDefaultPrivKeyCert(SignatureKeyCallback.DefaultPrivKeyCertRequest request, Map context) throws IOException {
/* 1400 */     Object obj = context.get("certificate");
/* 1401 */     boolean foundCert = false;
/* 1402 */     if (obj instanceof X509Certificate) {
/* 1403 */       request.setX509Certificate((X509Certificate)obj);
/* 1404 */       foundCert = true;
/*      */     } 
/* 1406 */     obj = context.get("privatekey");
/* 1407 */     if (obj instanceof PrivateKey) {
/* 1408 */       request.setPrivateKey((PrivateKey)obj);
/* 1409 */       if (foundCert) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1414 */     getKeyStore(context);
/* 1415 */     String uniqueAlias = null;
/*      */     try {
/* 1417 */       if (this.myAlias != null) {
/* 1418 */         uniqueAlias = this.myAlias;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1424 */         if (this.keystoreCertSelectorClass != null) {
/* 1425 */           AliasSelector selector = null;
/*      */           try {
/* 1427 */             selector = this.keystoreCertSelectorClass.newInstance();
/* 1428 */           } catch (IllegalAccessException ex) {
/* 1429 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1532_EXCEPTION_INSTANTIATING_ALIASSELECTOR(), ex);
/* 1430 */             throw new RuntimeException(ex);
/* 1431 */           } catch (InstantiationException ex) {
/* 1432 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1532_EXCEPTION_INSTANTIATING_ALIASSELECTOR(), ex);
/* 1433 */             throw new RuntimeException(ex);
/*      */           } 
/* 1435 */           uniqueAlias = selector.select(context);
/*      */         } 
/*      */         
/* 1438 */         if (uniqueAlias == null) {
/* 1439 */           Enumeration<String> aliases = this.keyStore.aliases();
/* 1440 */           while (aliases.hasMoreElements()) {
/* 1441 */             String currentAlias = aliases.nextElement();
/* 1442 */             if (this.keyStore.isKeyEntry(currentAlias)) {
/* 1443 */               Certificate thisCertificate = this.keyStore.getCertificate(currentAlias);
/* 1444 */               if (thisCertificate != null && 
/* 1445 */                 thisCertificate instanceof X509Certificate) {
/* 1446 */                 if (uniqueAlias == null) {
/* 1447 */                   uniqueAlias = currentAlias;
/*      */                   continue;
/*      */                 } 
/* 1450 */                 uniqueAlias = null;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1459 */       if (uniqueAlias != null)
/*      */       {
/* 1461 */         request.setX509Certificate((X509Certificate)this.keyStore.getCertificate(uniqueAlias));
/*      */         
/* 1463 */         request.setPrivateKey(getPrivateKey(context, uniqueAlias));
/*      */       
/*      */       }
/*      */       else
/*      */       {
/* 1468 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1512_FAILED_LOCATE_CERTIFICATE_PRIVATEKEY());
/* 1469 */         throw new RuntimeException("An Error occurred while locating default certificate and privateKey in KeyStore");
/*      */       }
/*      */     
/* 1472 */     } catch (Exception e) {
/* 1473 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/* 1474 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class DefaultTimestampValidator
/*      */     implements TimestampValidationCallback.TimestampValidator
/*      */   {
/*      */     private DefaultTimestampValidator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void validate(TimestampValidationCallback.Request request) throws TimestampValidationCallback.TimestampValidationException {
/* 1488 */       TimestampValidationCallback.UTCTimestampRequest utcTimestampRequest = (TimestampValidationCallback.UTCTimestampRequest)request;
/*      */ 
/*      */ 
/*      */       
/* 1492 */       TimeZone utc = TimeZone.getTimeZone("UTC");
/*      */ 
/*      */ 
/*      */       
/* 1496 */       SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*      */       
/* 1498 */       calendarFormatter1.setTimeZone(utc);
/* 1499 */       SimpleDateFormat calendarFormatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
/* 1500 */       calendarFormatter2.setTimeZone(utc);
/*      */       
/* 1502 */       Date created = null;
/* 1503 */       Date expired = null;
/*      */ 
/*      */       
/*      */       try {
/* 1507 */         created = calendarFormatter1.parse(utcTimestampRequest.getCreated());
/* 1508 */         if (utcTimestampRequest.getExpired() != null) {
/* 1509 */           expired = calendarFormatter1.parse(utcTimestampRequest.getExpired());
/*      */         }
/* 1511 */       } catch (ParseException pe) {
/*      */         try {
/* 1513 */           created = calendarFormatter2.parse(utcTimestampRequest.getCreated());
/* 1514 */           if (utcTimestampRequest.getExpired() != null) {
/* 1515 */             expired = calendarFormatter2.parse(utcTimestampRequest.getExpired());
/*      */           }
/* 1517 */         } catch (ParseException ipe) {
/* 1518 */           DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1513_EXCEPTION_VALIDATE_TIMESTAMP(), ipe);
/* 1519 */           throw new TimestampValidationCallback.TimestampValidationException(ipe.getMessage());
/*      */         } 
/*      */       } 
/*      */       
/* 1523 */       long maxClockSkewLocal = utcTimestampRequest.getMaxClockSkew();
/* 1524 */       if (DefaultCallbackHandler.this.mcs != null && DefaultCallbackHandler.this.maxClockSkewG >= 0L) {
/* 1525 */         maxClockSkewLocal = DefaultCallbackHandler.this.maxClockSkewG;
/*      */       }
/* 1527 */       long tsfLocal = utcTimestampRequest.getTimestampFreshnessLimit();
/* 1528 */       if (DefaultCallbackHandler.this.tfl != null && DefaultCallbackHandler.this.timestampFreshnessLimitG > 0L) {
/* 1529 */         tsfLocal = DefaultCallbackHandler.this.timestampFreshnessLimitG;
/*      */       }
/*      */ 
/*      */       
/* 1533 */       DefaultCallbackHandler.this.validateCreationTime(created, maxClockSkewLocal, tsfLocal);
/*      */ 
/*      */       
/* 1536 */       if (expired != null) {
/* 1537 */         DefaultCallbackHandler.this.validateExpirationTime(expired, maxClockSkewLocal, tsfLocal);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateExpirationTime(Date expires, long maxClockSkew, long timestampFreshnessLimit) throws TimestampValidationCallback.TimestampValidationException {
/* 1547 */     Date current = getCurrentDateTimeAdjustedBy(-1L * maxClockSkew);
/*      */     
/* 1549 */     if (expires.before(current)) {
/* 1550 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1514_ERROR_AHEAD_CURRENT_TIME());
/* 1551 */       throw new TimestampValidationCallback.TimestampValidationException("The current time is ahead of the expiration time in Timestamp");
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
/*      */   public void validateCreationTime(Date created, long maxClockSkew, long timestampFreshnessLimit) throws TimestampValidationCallback.TimestampValidationException {
/* 1563 */     Date current = getCurrentDateTimeAdjustedBy(-1L * (maxClockSkew + timestampFreshnessLimit));
/*      */ 
/*      */     
/* 1566 */     if (created.before(current)) {
/* 1567 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1515_ERROR_CURRENT_TIME());
/* 1568 */       log.log(Level.SEVERE, "Creation time:" + created);
/* 1569 */       log.log(Level.SEVERE, "Current time:" + current);
/* 1570 */       throw new TimestampValidationCallback.TimestampValidationException("The creation time is older than  currenttime - timestamp-freshness-limit - max-clock-skew");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1575 */     current = getCurrentDateTimeAdjustedBy(maxClockSkew);
/*      */     
/* 1577 */     if (current.before(created)) {
/* 1578 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1516_ERROR_CREATION_AHEAD_CURRENT_TIME());
/* 1579 */       throw new TimestampValidationCallback.TimestampValidationException("The creation time is ahead of the current time.");
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
/*      */   private Date getCurrentDateTimeAdjustedBy(long adjustment) {
/* 1591 */     Calendar now = new GregorianCalendar();
/* 1592 */     now.setTimeInMillis(now.getTimeInMillis() + adjustment);
/*      */     
/* 1594 */     return now.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class X509CertificateValidatorImpl
/*      */     implements CertificateValidationCallback.CertificateValidator, ValidatorExtension
/*      */   {
/* 1603 */     private Map runtimeProps = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean validate(X509Certificate certificate) throws CertificateValidationCallback.CertificateValidationException {
/*      */       PKIXBuilderParameters parameters;
/*      */       try {
/* 1612 */         certificate.checkValidity();
/* 1613 */       } catch (CertificateExpiredException e) {
/* 1614 */         DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1517_X_509_EXPIRED(), e);
/* 1615 */         throw new CertificateValidationCallback.CertificateValidationException("X509Certificate Expired", e);
/* 1616 */       } catch (CertificateNotYetValidException e) {
/* 1617 */         DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1517_X_509_EXPIRED(), e);
/* 1618 */         throw new CertificateValidationCallback.CertificateValidationException("X509Certificate Not Yet Valid", e);
/*      */       } 
/*      */ 
/*      */       
/* 1622 */       if (certificate.getIssuerX500Principal().equals(certificate.getSubjectX500Principal())) {
/* 1623 */         if (isTrustedSelfSigned(certificate, DefaultCallbackHandler.this.getTrustStore(this.runtimeProps))) {
/* 1624 */           return true;
/*      */         }
/* 1626 */         DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1533_X_509_SELF_SIGNED_CERTIFICATE_NOT_VALID());
/* 1627 */         throw new CertificateValidationCallback.CertificateValidationException("Validation of self signed certificate failed");
/*      */       } 
/*      */ 
/*      */       
/* 1631 */       X509CertSelector certSelector = new X509CertSelector();
/* 1632 */       certSelector.setCertificate(certificate);
/*      */ 
/*      */       
/* 1635 */       CertPathValidator certValidator = null;
/* 1636 */       CertPath certPath = null;
/* 1637 */       List<Certificate> certChainList = new ArrayList<Certificate>();
/* 1638 */       boolean caFound = false;
/* 1639 */       Principal certChainIssuer = null;
/* 1640 */       int noOfEntriesInTrustStore = 0;
/* 1641 */       boolean isIssuerCertMatched = false;
/*      */       
/*      */       try {
/* 1644 */         KeyStore tStore = DefaultCallbackHandler.this.getTrustStore(this.runtimeProps);
/* 1645 */         CertStore cStore = DefaultCallbackHandler.this.getCertStore(this.runtimeProps);
/* 1646 */         parameters = new PKIXBuilderParameters(tStore, certSelector);
/* 1647 */         parameters.setRevocationEnabled(DefaultCallbackHandler.this.revocationEnabled);
/* 1648 */         if (cStore != null) {
/* 1649 */           parameters.addCertStore(cStore);
/*      */         }
/*      */         else {
/*      */           
/* 1653 */           CertStore cs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(Collections.singleton(certificate)));
/*      */           
/* 1655 */           parameters.addCertStore(cs);
/*      */         } 
/*      */         
/* 1658 */         Certificate[] certChain = null;
/* 1659 */         String certAlias = tStore.getCertificateAlias(certificate);
/* 1660 */         if (certAlias != null) {
/* 1661 */           certChain = tStore.getCertificateChain(certAlias);
/*      */         }
/* 1663 */         if (certChain == null) {
/* 1664 */           certChainList.add(certificate);
/* 1665 */           certChainIssuer = certificate.getIssuerX500Principal();
/* 1666 */           noOfEntriesInTrustStore = tStore.size();
/*      */         } else {
/* 1668 */           certChainList = Arrays.asList(certChain);
/*      */         } 
/* 1670 */         while (!caFound && noOfEntriesInTrustStore-- != 0 && certChain == null) {
/* 1671 */           Enumeration<String> aliases = tStore.aliases();
/* 1672 */           while (aliases.hasMoreElements()) {
/* 1673 */             String alias = aliases.nextElement();
/* 1674 */             Certificate cert = tStore.getCertificate(alias);
/* 1675 */             if (cert == null || !"X.509".equals(cert.getType()) || certChainList.contains(cert)) {
/*      */               continue;
/*      */             }
/* 1678 */             X509Certificate x509Cert = (X509Certificate)cert;
/* 1679 */             if (certChainIssuer.equals(x509Cert.getSubjectX500Principal())) {
/* 1680 */               certChainList.add(cert);
/* 1681 */               if (x509Cert.getSubjectX500Principal().equals(x509Cert.getIssuerX500Principal())) {
/* 1682 */                 caFound = true;
/*      */                 break;
/*      */               } 
/* 1685 */               certChainIssuer = x509Cert.getIssuerDN();
/* 1686 */               if (!isIssuerCertMatched) {
/* 1687 */                 isIssuerCertMatched = true;
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1694 */           if (!caFound) {
/* 1695 */             if (!isIssuerCertMatched) {
/*      */               break;
/*      */             }
/* 1698 */             isIssuerCertMatched = false;
/*      */           } 
/*      */         } 
/*      */         
/* 1702 */         CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 1703 */         certPath = cf.generateCertPath(certChainList);
/* 1704 */         certValidator = CertPathValidator.getInstance("PKIX");
/*      */       }
/* 1706 */       catch (Exception e) {
/* 1707 */         DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1518_FAILEDTO_VALIDATE_CERTIFICATE(), e);
/* 1708 */         throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
/*      */       } 
/*      */       
/*      */       try {
/* 1712 */         certValidator.validate(certPath, parameters);
/* 1713 */       } catch (Exception e) {
/* 1714 */         DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1518_FAILEDTO_VALIDATE_CERTIFICATE(), e);
/* 1715 */         throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
/*      */       } 
/* 1717 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean isTrustedSelfSigned(X509Certificate cert, KeyStore trustStore) throws CertificateValidationCallback.CertificateValidationException {
/* 1722 */       if (trustStore == null) {
/* 1723 */         DefaultCallbackHandler.log.log(Level.WARNING, LogStringsMessages.WSS_1541_TRUSTSTORE_NOT_FOUND_CHECK_CONFIG());
/* 1724 */         return false;
/*      */       } 
/*      */       try {
/* 1727 */         Enumeration<String> aliases = trustStore.aliases();
/* 1728 */         while (aliases.hasMoreElements()) {
/* 1729 */           String alias = aliases.nextElement();
/* 1730 */           Certificate certificate = trustStore.getCertificate(alias);
/* 1731 */           if (certificate == null || !"X.509".equals(certificate.getType())) {
/*      */             continue;
/*      */           }
/* 1734 */           X509Certificate x509Cert = (X509Certificate)certificate;
/* 1735 */           if (x509Cert != null && x509Cert.equals(cert)) {
/* 1736 */             return true;
/*      */           }
/*      */         } 
/* 1739 */         return false;
/* 1740 */       } catch (Exception e) {
/* 1741 */         DefaultCallbackHandler.log.log(Level.SEVERE, LogStringsMessages.WSS_1518_FAILEDTO_VALIDATE_CERTIFICATE(), e);
/* 1742 */         throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setRuntimeProperties(Map props) {
/* 1747 */       this.runtimeProps = props;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getCertificateFromTrustStoreForThumbprint(byte[] ski, Map runtimeProps) throws IOException {
/*      */     try {
/* 1786 */       if (runtimeProps != null) {
/* 1787 */         Object obj = runtimeProps.get("server-certificate");
/* 1788 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, ski, "Thumbprint");
/* 1789 */         if (cert != null) {
/* 1790 */           return cert;
/*      */         }
/*      */       } 
/* 1793 */       if (getTrustStore(runtimeProps) == null && getCertStore(runtimeProps) == null) {
/* 1794 */         return null;
/*      */       }
/* 1796 */       if (this.trustStore != null) {
/* 1797 */         Enumeration<String> aliases = this.trustStore.aliases();
/* 1798 */         while (aliases.hasMoreElements()) {
/* 1799 */           String alias = aliases.nextElement();
/* 1800 */           Certificate cert = this.trustStore.getCertificate(alias);
/* 1801 */           if (cert == null || !"X.509".equals(cert.getType())) {
/*      */             continue;
/*      */           }
/* 1804 */           X509Certificate x509Cert = (X509Certificate)cert;
/* 1805 */           byte[] keyId = XWSSUtil.getThumbprintIdentifier(x509Cert);
/* 1806 */           if (keyId == null) {
/*      */             continue;
/*      */           }
/*      */           
/* 1810 */           if (Arrays.equals(ski, keyId)) {
/* 1811 */             return x509Cert;
/*      */           }
/*      */         } 
/*      */       } 
/* 1815 */     } catch (Exception e) {
/* 1816 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), e);
/* 1817 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1820 */     if (this.certStore != null) {
/* 1821 */       CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1828 */       if (selector == null) {
/* 1829 */         selector = new DigestCertSelector(ski, "SHA-1");
/*      */       }
/* 1831 */       Collection<? extends Certificate> certs = null;
/*      */       try {
/* 1833 */         certs = this.certStore.getCertificates(selector);
/* 1834 */       } catch (CertStoreException ex) {
/* 1835 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1530_EXCEPTION_IN_CERTSTORE_LOOKUP(), ex);
/* 1836 */         throw new RuntimeException(ex);
/*      */       } 
/* 1838 */       if (certs.size() > 0) {
/* 1839 */         return certs.iterator().next();
/*      */       }
/*      */     } 
/*      */     
/* 1843 */     return null;
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
/*      */   public PrivateKey getPrivateKeyForThumbprint(byte[] ski, Map runtimeProps) throws IOException {
/*      */     try {
/* 1856 */       if (runtimeProps != null) {
/* 1857 */         Object obj = runtimeProps.get("certificate");
/* 1858 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, ski, "Thumbprint");
/* 1859 */         if (cert != null) {
/* 1860 */           return XWSSUtil.getProgrammaticPrivateKey(runtimeProps);
/*      */         }
/*      */       } 
/* 1863 */       if (getKeyStore(runtimeProps) == null) {
/* 1864 */         return null;
/*      */       }
/* 1866 */       Enumeration<String> aliases = this.keyStore.aliases();
/* 1867 */       while (aliases.hasMoreElements()) {
/* 1868 */         String alias = aliases.nextElement();
/* 1869 */         if (!this.keyStore.isKeyEntry(alias)) {
/*      */           continue;
/*      */         }
/* 1872 */         Certificate cert = this.keyStore.getCertificate(alias);
/* 1873 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*      */           continue;
/*      */         }
/* 1876 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 1877 */         byte[] keyId = XWSSUtil.getThumbprintIdentifier(x509Cert);
/* 1878 */         if (keyId == null) {
/*      */           continue;
/*      */         }
/*      */         
/* 1882 */         if (Arrays.equals(ski, keyId))
/*      */         {
/*      */           
/* 1885 */           return getPrivateKey(runtimeProps, alias);
/*      */         }
/*      */       } 
/* 1888 */     } catch (Exception e) {
/* 1889 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/* 1890 */       throw new RuntimeException(e);
/*      */     } 
/* 1892 */     return null;
/*      */   }
/*      */   
/*      */   private Class loadClassSilent(String classname) {
/* 1896 */     if (classname == null) {
/* 1897 */       return null;
/*      */     }
/* 1899 */     Class<?> ret = null;
/* 1900 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 1901 */     if (loader != null) {
/*      */       try {
/* 1903 */         ret = loader.loadClass(classname);
/* 1904 */         return ret;
/* 1905 */       } catch (ClassNotFoundException e) {
/*      */         
/* 1907 */         if (log.isLoggable(Level.FINE)) {
/* 1908 */           log.log(Level.FINE, "LoadClassSilent: could not load class " + classname, e);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1913 */     loader = getClass().getClassLoader();
/* 1914 */     if (loader != null) {
/*      */       try {
/* 1916 */         ret = loader.loadClass(classname);
/* 1917 */         return ret;
/* 1918 */       } catch (ClassNotFoundException e) {
/*      */         
/* 1920 */         if (log.isLoggable(Level.FINE)) {
/* 1921 */           log.log(Level.FINE, "LoadClassSilent: could not load class " + classname, e);
/*      */         }
/*      */       } 
/*      */     }
/* 1925 */     return null;
/*      */   }
/*      */   
/*      */   private Class loadClass(String classname) throws XWSSecurityException {
/* 1929 */     if (classname == null) {
/* 1930 */       return null;
/*      */     }
/* 1932 */     Class<?> ret = null;
/* 1933 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 1934 */     if (loader != null) {
/*      */       try {
/* 1936 */         ret = loader.loadClass(classname);
/* 1937 */         return ret;
/* 1938 */       } catch (ClassNotFoundException e) {
/*      */         
/* 1940 */         if (log.isLoggable(Level.FINE)) {
/* 1941 */           log.log(Level.FINE, "LoadClass: could not load class " + classname, e);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1946 */     loader = getClass().getClassLoader();
/*      */     try {
/* 1948 */       ret = loader.loadClass(classname);
/* 1949 */       return ret;
/* 1950 */     } catch (ClassNotFoundException e) {
/*      */       
/* 1952 */       if (log.isLoggable(Level.FINE)) {
/* 1953 */         log.log(Level.FINE, "LoadClass: could not load class " + classname, e);
/*      */       }
/*      */       
/* 1956 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1521_ERROR_GETTING_USER_CLASS());
/* 1957 */       throw new XWSSecurityException("Could not find User Class " + classname);
/*      */     } 
/*      */   }
/*      */   private long toLong(String lng) throws XWSSecurityException {
/* 1961 */     if (lng == null) {
/* 1962 */       return 0L;
/*      */     }
/* 1964 */     Long ret = Long.valueOf(0L);
/* 1965 */     ret = Long.valueOf(lng);
/*      */     try {
/* 1967 */       ret = Long.valueOf(lng);
/* 1968 */     } catch (Exception e) {
/* 1969 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1522_ERROR_GETTING_LONG_VALUE(), e);
/* 1970 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1972 */     return ret.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initNewInstances() throws XWSSecurityException {
/*      */     try {
/* 1979 */       if (this.usernameCbHandler != null) {
/* 1980 */         this.usernameHandler = this.usernameCbHandler.newInstance();
/*      */       }
/* 1982 */       else if (log.isLoggable(Level.FINE)) {
/* 1983 */         log.log(Level.FINE, "Got NULL for Username Callback Handler");
/*      */       } 
/*      */       
/* 1986 */       if (this.passwordCbHandler != null) {
/* 1987 */         this.passwordHandler = this.passwordCbHandler.newInstance();
/*      */       }
/* 1989 */       else if (log.isLoggable(Level.FINE)) {
/* 1990 */         log.log(Level.FINE, "Got NULL for Password Callback Handler");
/*      */       } 
/*      */ 
/*      */       
/* 1994 */       if (this.samlCbHandler != null) {
/* 1995 */         this.samlHandler = this.samlCbHandler.newInstance();
/*      */       }
/*      */       
/* 1998 */       if (this.usernameValidator != null) {
/* 1999 */         this.pwValidator = this.usernameValidator.newInstance();
/*      */       }
/*      */       
/* 2002 */       if (this.timestampValidator != null) {
/* 2003 */         this.tsValidator = this.timestampValidator.newInstance();
/*      */       }
/*      */       
/* 2006 */       if (this.samlValidator != null) {
/* 2007 */         this.sValidator = this.samlValidator.newInstance();
/*      */       }
/*      */       
/* 2010 */       if (this.certificateValidator != null) {
/* 2011 */         this.certValidator = this.certificateValidator.newInstance();
/*      */       } else {
/*      */         
/* 2014 */         this.certValidator = new X509CertificateValidatorImpl();
/*      */       } 
/*      */       
/* 2017 */       if (this.certstoreCbHandler != null) {
/* 2018 */         this.certstoreHandler = this.certstoreCbHandler.newInstance();
/*      */       }
/*      */       
/* 2021 */       if (this.keystoreCbHandler != null) {
/* 2022 */         this.keystoreHandler = this.keystoreCbHandler.newInstance();
/*      */       }
/* 2024 */       if (this.truststoreCbHandler != null) {
/* 2025 */         this.truststoreHandler = this.truststoreCbHandler.newInstance();
/*      */       
/*      */       }
/*      */     }
/* 2029 */     catch (Exception e) {
/* 2030 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1523_ERROR_GETTING_NEW_INSTANCE_CALLBACK_HANDLER(), e);
/* 2031 */       throw new XWSSecurityException(e);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getCertificateFromTrustStoreForSAML(PublicKey pk, Map runtimeProps) throws IOException {
/*      */     try {
/* 2061 */       if (runtimeProps != null) {
/* 2062 */         Object obj = runtimeProps.get("server-certificate");
/* 2063 */         X509Certificate cert = XWSSUtil.matchesProgrammaticInfo(obj, pk);
/* 2064 */         if (cert != null) {
/* 2065 */           return cert;
/*      */         }
/*      */       } 
/* 2068 */       if (getTrustStore(runtimeProps) == null && getCertStore(runtimeProps) == null) {
/* 2069 */         return null;
/*      */       }
/* 2071 */       if (this.trustStore != null) {
/* 2072 */         Enumeration<String> aliases = this.trustStore.aliases();
/* 2073 */         while (aliases.hasMoreElements()) {
/* 2074 */           String alias = aliases.nextElement();
/* 2075 */           Certificate cert = this.trustStore.getCertificate(alias);
/* 2076 */           if (cert == null || !"X.509".equals(cert.getType())) {
/*      */             continue;
/*      */           }
/* 2079 */           X509Certificate x509Cert = (X509Certificate)cert;
/* 2080 */           if (x509Cert.getPublicKey().equals(pk)) {
/* 2081 */             return x509Cert;
/*      */           }
/*      */         } 
/*      */       } 
/* 2085 */     } catch (Exception e) {
/* 2086 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1526_FAILEDTO_GETCERTIFICATE(), e);
/* 2087 */       throw new RuntimeException(e);
/*      */     } 
/* 2089 */     if (this.certStore != null) {
/* 2090 */       CertSelector selector = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2097 */       if (selector == null) {
/* 2098 */         selector = new PublicKeyCertSelector(pk);
/*      */       }
/* 2100 */       Collection<? extends Certificate> certs = null;
/*      */       try {
/* 2102 */         certs = this.certStore.getCertificates(selector);
/* 2103 */       } catch (CertStoreException ex) {
/* 2104 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1530_EXCEPTION_IN_CERTSTORE_LOOKUP(), ex);
/* 2105 */         throw new RuntimeException(ex);
/*      */       } 
/* 2107 */       if (certs.size() > 0) {
/* 2108 */         return certs.iterator().next();
/*      */       }
/*      */     } 
/*      */     
/* 2112 */     return null;
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
/*      */   private PrivateKey getPrivateKeyFromKeyStore(PublicKey pk, Map runtimeProps) throws IOException {
/*      */     try {
/* 2125 */       Enumeration<String> aliases = this.keyStore.aliases();
/* 2126 */       while (aliases.hasMoreElements()) {
/* 2127 */         String alias = aliases.nextElement();
/* 2128 */         if (!this.keyStore.isKeyEntry(alias)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 2133 */         Certificate cert = this.keyStore.getCertificate(alias);
/* 2134 */         if (pk.equals(cert.getPublicKey())) {
/* 2135 */           PrivateKey key = getPrivateKey(runtimeProps, alias);
/* 2136 */           return key;
/*      */         }
/*      */       
/*      */       } 
/* 2140 */     } catch (Exception e) {
/* 2141 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1505_FAILEDTO_GETKEY(), e);
/* 2142 */       throw new RuntimeException(e);
/*      */     } 
/* 2144 */     return null;
/*      */   }
/*      */   
/*      */   private String resolveHome(String url) {
/* 2148 */     if (url == null) {
/* 2149 */       return null;
/*      */     }
/* 2151 */     if (url.startsWith("$WSIT_HOME")) {
/* 2152 */       String wsitHome = System.getProperty("WSIT_HOME");
/* 2153 */       if (wsitHome != null) {
/* 2154 */         String ret = url.replace("$WSIT_HOME", wsitHome);
/* 2155 */         return ret;
/*      */       } 
/* 2157 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1524_UNABLETO_RESOLVE_URI_WSIT_HOME_NOTSET());
/* 2158 */       throw new RuntimeException("The following config URL: " + url + " in the WSDL could not be resolved because System Property WSIT_HOME was not set");
/*      */     } 
/*      */     
/* 2161 */     return url;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initKeyPassword() {
/*      */     try {
/* 2169 */       Class<CallbackHandler> cbh = loadClassSilent(this.keyPwd);
/* 2170 */       if (cbh != null) {
/* 2171 */         CallbackHandler hdlr = cbh.newInstance();
/* 2172 */         PasswordCallback pc = new PasswordCallback("KeyPassword", false);
/*      */         
/* 2174 */         Callback[] cbs = { pc };
/* 2175 */         hdlr.handle(cbs);
/* 2176 */         this.keyPassword = ((PasswordCallback)cbs[0]).getPassword();
/*      */       } else {
/*      */         
/* 2179 */         this.keyPassword = this.keyPwd.toCharArray();
/*      */       } 
/* 2181 */     } catch (InstantiationException ex) {
/* 2182 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1528_FAILED_INITIALIZE_KEY_PASSWORD(), ex);
/* 2183 */       throw new RuntimeException(ex);
/* 2184 */     } catch (IOException e) {
/* 2185 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1528_FAILED_INITIALIZE_KEY_PASSWORD(), e);
/* 2186 */       throw new RuntimeException(e);
/* 2187 */     } catch (IllegalAccessException ie) {
/* 2188 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1528_FAILED_INITIALIZE_KEY_PASSWORD(), ie);
/* 2189 */       throw new RuntimeException(ie);
/* 2190 */     } catch (UnsupportedCallbackException ue) {
/* 2191 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1528_FAILED_INITIALIZE_KEY_PASSWORD(), ue);
/* 2192 */       throw new RuntimeException(ue);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X509Certificate getDynamicCertificate(Map context) {
/* 2203 */     X509Certificate cert = null;
/* 2204 */     X509Certificate self = null;
/* 2205 */     Subject requesterSubject = getRequesterSubject(context);
/* 2206 */     if (requesterSubject != null) {
/* 2207 */       Set<Object> publicCredentials = requesterSubject.getPublicCredentials();
/* 2208 */       for (Iterator it = publicCredentials.iterator(); it.hasNext(); ) {
/* 2209 */         Object cred = it.next();
/* 2210 */         if (cred instanceof X509Certificate) {
/* 2211 */           X509Certificate certificate = (X509Certificate)cred;
/* 2212 */           if (!isMyCert(certificate, context)) {
/* 2213 */             cert = certificate;
/*      */             break;
/*      */           } 
/* 2216 */           self = certificate;
/*      */         } 
/*      */       } 
/*      */       
/* 2220 */       if (cert != null)
/* 2221 */         return cert; 
/* 2222 */       if (self != null)
/*      */       {
/* 2224 */         return self;
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
/* 2244 */     if (log.isLoggable(Level.FINE)) {
/* 2245 */       log.log(Level.FINE, "Could not locate Incoming Client Certificate in Caller Subject");
/*      */     }
/*      */     
/* 2248 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Subject getRequesterSubject(final Map context) {
/* 2253 */     Subject otherPartySubject = (Subject)context.get("javax.security.auth.Subject");
/* 2254 */     if (otherPartySubject != null) {
/* 2255 */       return otherPartySubject;
/*      */     }
/* 2257 */     otherPartySubject = AccessController.<Subject>doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           
/*      */           public Object run()
/*      */           {
/* 2262 */             Subject otherPartySubj = new Subject();
/* 2263 */             context.put("javax.security.auth.Subject", otherPartySubj);
/* 2264 */             return otherPartySubj;
/*      */           }
/*      */         });
/* 2267 */     return otherPartySubject;
/*      */   }
/*      */   
/*      */   private KeyStore getKeyStore(Map runtimeProps) {
/*      */     try {
/* 2272 */       if (this.keyStore != null) {
/* 2273 */         return this.keyStore;
/*      */       }
/* 2275 */       return getKeyStoreUsingCallback(runtimeProps);
/*      */     } finally {
/* 2277 */       if (this.keyStore == null) {
/* 2278 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1540_KEYSTORE_NOT_FOUND_CHECK_CONFIG());
/* 2279 */         throw new XWSSecurityRuntimeException("Could not locate KeyStore, check keystore assertion in WSIT configuration");
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private synchronized KeyStore getKeyStoreUsingCallback(Map runtimeProps) {
/* 2285 */     if (this.keyStore == null && this.keystoreHandler != null) {
/*      */       try {
/* 2287 */         KeyStoreCallback cb = new KeyStoreCallback();
/* 2288 */         SecurityUtil.copy(cb.getRuntimeProperties(), runtimeProps);
/* 2289 */         Callback[] cbs = { (Callback)cb };
/* 2290 */         this.keystoreHandler.handle(cbs);
/* 2291 */         this.keyStore = cb.getKeystore();
/* 2292 */         if (this.keyStore == null) {
/* 2293 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1534_NO_KEYSTORE_SET_IN_KEYSTORECALLBACK_CALLBACKHANDLER());
/* 2294 */           throw new XWSSecurityRuntimeException("No KeyStore set in KeyStorCallback  by CallbackHandler");
/*      */         } 
/* 2296 */       } catch (IOException ex) {
/* 2297 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1535_ERROR_KEYSTORE_USING_CALLBACK(), ex);
/* 2298 */         throw new XWSSecurityRuntimeException(ex);
/* 2299 */       } catch (UnsupportedCallbackException ex) {
/* 2300 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1535_ERROR_KEYSTORE_USING_CALLBACK(), ex);
/* 2301 */         throw new XWSSecurityRuntimeException(ex);
/*      */       } 
/*      */     }
/* 2304 */     return this.keyStore;
/*      */   }
/*      */   
/*      */   private KeyStore getTrustStore(Map runtimeProps) {
/* 2308 */     if (this.trustStore != null) {
/* 2309 */       return this.trustStore;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2315 */     return getTrustStoreUsingCallback(runtimeProps);
/*      */   }
/*      */   
/*      */   private CertStore getCertStore(Map runtimeProps) {
/* 2319 */     if (this.certStore != null) {
/* 2320 */       return this.certStore;
/*      */     }
/* 2322 */     return getCertStoreUsingCallback(runtimeProps);
/*      */   }
/*      */   
/*      */   private synchronized CertStore getCertStoreUsingCallback(Map runtimeProps) {
/* 2326 */     if (this.certstoreHandler != null) {
/*      */       
/* 2328 */       CertStoreCallback cb = new CertStoreCallback();
/* 2329 */       SecurityUtil.copy(cb.getRuntimeProperties(), runtimeProps);
/* 2330 */       Callback[] callbacks = { (Callback)cb };
/*      */       try {
/* 2332 */         this.certstoreHandler.handle(callbacks);
/* 2333 */         this.certStore = cb.getCertStore();
/* 2334 */       } catch (UnsupportedCallbackException ex) {
/* 2335 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1529_EXCEPTION_IN_CERTSTORE_CALLBACK(), ex);
/* 2336 */         throw new XWSSecurityRuntimeException(ex);
/* 2337 */       } catch (IOException ex) {
/* 2338 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1529_EXCEPTION_IN_CERTSTORE_CALLBACK(), ex);
/* 2339 */         throw new XWSSecurityRuntimeException(ex);
/*      */       } 
/*      */     } 
/* 2342 */     return this.certStore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized KeyStore getTrustStoreUsingCallback(Map runtimeProps) {
/* 2352 */     if (this.trustStore == null && this.truststoreHandler != null) {
/*      */       try {
/* 2354 */         KeyStoreCallback cb = new KeyStoreCallback();
/* 2355 */         SecurityUtil.copy(cb.getRuntimeProperties(), runtimeProps);
/* 2356 */         Callback[] cbs = { (Callback)cb };
/* 2357 */         this.truststoreHandler.handle(cbs);
/* 2358 */         this.trustStore = cb.getKeystore();
/* 2359 */         if (this.trustStore == null) {
/* 2360 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1536_NO_TRUSTSTORE_SET_IN_TRUSTSTORECALLBACK());
/* 2361 */           throw new XWSSecurityRuntimeException("No TrustStore set in KeyStorCallback  by CallbackHandler");
/*      */         } 
/* 2363 */       } catch (IOException ex) {
/* 2364 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1537_ERROR_TRUSTSTORE_USING_CALLBACK(), ex);
/* 2365 */         throw new XWSSecurityRuntimeException(ex);
/* 2366 */       } catch (UnsupportedCallbackException ex) {
/* 2367 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1537_ERROR_TRUSTSTORE_USING_CALLBACK(), ex);
/* 2368 */         throw new XWSSecurityRuntimeException(ex);
/*      */       } 
/*      */     }
/* 2371 */     return this.trustStore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PrivateKey getPrivateKey(Map runtimeProps, String alias) {
/* 2381 */     PrivateKey privKey = null;
/* 2382 */     if (this.keystoreHandler != null) {
/*      */       try {
/* 2384 */         PrivateKeyCallback cb = new PrivateKeyCallback();
/* 2385 */         cb.setKeystore(this.keyStore);
/* 2386 */         cb.setAlias(alias);
/* 2387 */         SecurityUtil.copy(cb.getRuntimeProperties(), runtimeProps);
/* 2388 */         Callback[] cbs = { (Callback)cb };
/* 2389 */         this.keystoreHandler.handle(cbs);
/* 2390 */         privKey = cb.getKey();
/* 2391 */       } catch (IOException ex) {
/* 2392 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1538_ERROR_GETTING_PRIVATE_KEY(), ex);
/* 2393 */         throw new XWSSecurityRuntimeException(ex);
/* 2394 */       } catch (UnsupportedCallbackException ex) {
/* 2395 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1538_ERROR_GETTING_PRIVATE_KEY(), ex);
/* 2396 */         throw new XWSSecurityRuntimeException(ex);
/*      */       } 
/*      */     } else {
/*      */       
/*      */       try {
/* 2401 */         privKey = (PrivateKey)this.keyStore.getKey(alias, this.keyPassword);
/* 2402 */       } catch (KeyStoreException ex) {
/* 2403 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1538_ERROR_GETTING_PRIVATE_KEY(), ex);
/* 2404 */         throw new XWSSecurityRuntimeException(ex);
/* 2405 */       } catch (NoSuchAlgorithmException ex) {
/* 2406 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1538_ERROR_GETTING_PRIVATE_KEY(), ex);
/* 2407 */         throw new XWSSecurityRuntimeException(ex);
/* 2408 */       } catch (UnrecoverableKeyException ex) {
/* 2409 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1538_ERROR_GETTING_PRIVATE_KEY(), ex);
/* 2410 */         throw new XWSSecurityRuntimeException(ex);
/*      */       } 
/*      */     } 
/* 2413 */     if (privKey == null) {
/* 2414 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1539_PRIVATE_KEY_NULL_ERROR());
/* 2415 */       throw new XWSSecurityRuntimeException("PrivateKey returned by PrivateKeyCallback was Null");
/*      */     } 
/* 2417 */     return privKey;
/*      */   }
/*      */   
/*      */   public SAMLAssertionValidator getSAMLValidator() {
/* 2421 */     return this.sValidator;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\DefaultCallbackHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */