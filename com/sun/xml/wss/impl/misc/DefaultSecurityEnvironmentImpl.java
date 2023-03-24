/*      */ package com.sun.xml.wss.impl.misc;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.xml.ws.api.server.WSEndpoint;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosLogin;
/*      */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*      */ import com.sun.xml.wss.NonceManager;
/*      */ import com.sun.xml.wss.ProcessingContext;
/*      */ import com.sun.xml.wss.RealmAuthenticationAdapter;
/*      */ import com.sun.xml.wss.SecurityEnvironment;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.Timestamp;
/*      */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*      */ import com.sun.xml.wss.impl.callback.CertificateValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.DecryptionKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*      */ import com.sun.xml.wss.impl.callback.EncryptionKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.PasswordCallback;
/*      */ import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback;
/*      */ import com.sun.xml.wss.impl.callback.TimestampValidationCallback;
/*      */ import com.sun.xml.wss.impl.callback.UsernameCallback;
/*      */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*      */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.logging.LogStringsMessages;
/*      */ import com.sun.xml.wss.logging.impl.misc.LogStringsMessages;
/*      */ import com.sun.xml.wss.saml.Assertion;
/*      */ import com.sun.xml.wss.util.XWSSUtil;
/*      */ import java.math.BigInteger;
/*      */ import java.security.AccessController;
/*      */ import java.security.Key;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.kerberos.KerberosPrincipal;
/*      */ import javax.security.auth.login.LoginContext;
/*      */ import javax.security.auth.login.LoginException;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import javax.security.auth.x500.X500PrivateCredential;
/*      */ import javax.xml.namespace.QName;
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
/*      */ public class DefaultSecurityEnvironmentImpl
/*      */   implements SecurityEnvironment
/*      */ {
/*      */   private static final long offset;
/*      */   
/*      */   static {
/*  123 */     Calendar c = new GregorianCalendar();
/*  124 */     long calculatedOffset = c.get(15);
/*  125 */     if (c.getTimeZone().inDaylightTime(c.getTime())) {
/*  126 */       calculatedOffset += c.getTimeZone().getDSTSavings();
/*      */     }
/*  128 */     offset = calculatedOffset;
/*      */   }
/*      */ 
/*      */   
/*  132 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*      */ 
/*      */   
/*  135 */   private final SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
/*  136 */   private final SimpleDateFormat calendarFormatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'");
/*  137 */   private CallbackHandler callbackHandler = null;
/*      */   private boolean isDefaultHandler = false;
/*  139 */   private X509Certificate selfCertificate = null;
/*  140 */   private Properties configAssertions = null;
/*      */   
/*  142 */   private long maxNonceAge = 900000L;
/*  143 */   private String mnaProperty = null;
/*      */   private String JAASLoginModuleForKeystore;
/*      */   private Subject loginContextSubjectForKeystore;
/*      */   private String keyStoreCBH;
/*      */   private CallbackHandler keystoreCbHandlerClass;
/*      */   
/*      */   public DefaultSecurityEnvironmentImpl(CallbackHandler cHandler) {
/*  150 */     this.callbackHandler = cHandler;
/*  151 */     if (this.callbackHandler instanceof DefaultCallbackHandler) {
/*  152 */       this.isDefaultHandler = true;
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
/*      */   public DefaultSecurityEnvironmentImpl(CallbackHandler cHandler, Properties confAssertions) {
/*  171 */     this.configAssertions = confAssertions;
/*  172 */     this.callbackHandler = cHandler;
/*  173 */     if (this.callbackHandler instanceof DefaultCallbackHandler) {
/*  174 */       this.isDefaultHandler = true;
/*      */     }
/*      */     
/*  177 */     this.mnaProperty = this.configAssertions.getProperty("max.nonce.age");
/*  178 */     if (this.mnaProperty != null) {
/*      */       try {
/*  180 */         this.maxNonceAge = SecurityUtil.toLong(this.mnaProperty);
/*  181 */       } catch (XWSSecurityException ex) {
/*  182 */         log.log(Level.FINE, " Exception while converting maxNonceAge config property, Setting MaxNonceAge to Default value {0}", Long.valueOf(900000L));
/*  183 */         this.maxNonceAge = 900000L;
/*      */       } 
/*      */     }
/*      */     
/*  187 */     this.JAASLoginModuleForKeystore = this.configAssertions.getProperty("jaas.loginmodule.for.keystore");
/*  188 */     this.keyStoreCBH = this.configAssertions.getProperty("keystore.callback.handler");
/*  189 */     this.loginContextSubjectForKeystore = initJAASKeyStoreLoginModule();
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
/*      */   public X509Certificate getDefaultCertificate(Map context) throws XWSSecurityException {
/*  211 */     X509Certificate cert = getPublicCredentialsFromLCSubject();
/*  212 */     if (cert != null) {
/*  213 */       return cert;
/*      */     }
/*  215 */     X509Certificate defaultCert = null;
/*      */     
/*  217 */     SignatureKeyCallback.DefaultPrivKeyCertRequest defaultPrivKeyCertRequest = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*      */     
/*  219 */     SignatureKeyCallback sigKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)defaultPrivKeyCertRequest);
/*      */     
/*  221 */     if (context != null) {
/*  222 */       ProcessingContext.copy(sigKeyCallback.getRuntimeProperties(), context);
/*      */     }
/*  224 */     Callback[] callbacks = { (Callback)sigKeyCallback };
/*      */     try {
/*  226 */       this.callbackHandler.handle(callbacks);
/*  227 */     } catch (Exception e) {
/*  228 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.DefaultPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.DefaultPrivKeyCertRequest" });
/*      */       
/*  230 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  231 */       throw new XWSSecurityException(e);
/*      */     } 
/*  233 */     defaultCert = defaultPrivKeyCertRequest.getX509Certificate();
/*      */     
/*  235 */     if (defaultCert == null) {
/*  236 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0218_CANNOT_LOCATE_DEFAULT_CERT());
/*  237 */       throw new XWSSecurityException("Unable to locate a default certificate");
/*      */     } 
/*      */     
/*  240 */     return defaultCert;
/*      */   }
/*      */ 
/*      */   
/*      */   public SignatureKeyCallback.PrivKeyCertRequest getDefaultPrivKeyCertRequest(Map context) throws XWSSecurityException {
/*  245 */     SignatureKeyCallback.DefaultPrivKeyCertRequest defaultPrivKeyCertRequest = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*      */     
/*  247 */     SignatureKeyCallback sigKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)defaultPrivKeyCertRequest);
/*      */     
/*  249 */     if (context != null) {
/*  250 */       ProcessingContext.copy(sigKeyCallback.getRuntimeProperties(), context);
/*      */     }
/*  252 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*  253 */     if (cred != null) {
/*  254 */       defaultPrivKeyCertRequest.setX509Certificate(cred.getCertificate());
/*  255 */       defaultPrivKeyCertRequest.setPrivateKey(cred.getPrivateKey());
/*  256 */       return (SignatureKeyCallback.PrivKeyCertRequest)defaultPrivKeyCertRequest;
/*      */     } 
/*      */     
/*  259 */     Callback[] callbacks = { (Callback)sigKeyCallback };
/*      */     try {
/*  261 */       this.callbackHandler.handle(callbacks);
/*  262 */     } catch (Exception e) {
/*  263 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.DefaultPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.DefaultPrivKeyCertRequest" });
/*      */       
/*  265 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  266 */       throw new XWSSecurityException(e);
/*      */     } 
/*  268 */     return (SignatureKeyCallback.PrivKeyCertRequest)defaultPrivKeyCertRequest;
/*      */   }
/*      */ 
/*      */   
/*      */   public SignatureKeyCallback.AliasPrivKeyCertRequest getAliasPrivKeyCertRequest(String certIdentifier) throws XWSSecurityException {
/*  273 */     SignatureKeyCallback.AliasPrivKeyCertRequest request = new SignatureKeyCallback.AliasPrivKeyCertRequest(certIdentifier);
/*      */     
/*  275 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*  276 */     if (cred != null && certIdentifier.equals(cred.getAlias())) {
/*  277 */       request.setX509Certificate(cred.getCertificate());
/*  278 */       request.setPrivateKey(cred.getPrivateKey());
/*  279 */       return request;
/*      */     } 
/*  281 */     SignatureKeyCallback sigCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)request);
/*  282 */     Callback[] callback = { (Callback)sigCallback };
/*      */     try {
/*  284 */       this.callbackHandler.handle(callback);
/*  285 */     } catch (Exception e) {
/*  286 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.AliasPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.AliasPrivKeyCertRequest" });
/*      */       
/*  288 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  289 */       throw new XWSSecurityException(e);
/*      */     } 
/*  291 */     return request;
/*      */   }
/*      */ 
/*      */   
/*      */   public PrivateKey getDefaultPrivateKey(Map context) throws XWSSecurityException {
/*  296 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*  297 */     if (cred != null) {
/*  298 */       return cred.getPrivateKey();
/*      */     }
/*  300 */     PrivateKey defaultPrivKey = null;
/*      */     
/*  302 */     SignatureKeyCallback.DefaultPrivKeyCertRequest defaultPrivKeyCertRequest = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*      */     
/*  304 */     SignatureKeyCallback sigKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)defaultPrivKeyCertRequest);
/*      */     
/*  306 */     if (context != null) {
/*  307 */       ProcessingContext.copy(sigKeyCallback.getRuntimeProperties(), context);
/*      */     }
/*  309 */     Callback[] callbacks = { (Callback)sigKeyCallback };
/*      */     try {
/*  311 */       this.callbackHandler.handle(callbacks);
/*  312 */     } catch (Exception e) {
/*  313 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.DefaultPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.DefaultPrivKeyCertRequest" });
/*      */       
/*  315 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  316 */       throw new XWSSecurityException(e);
/*      */     } 
/*  318 */     defaultPrivKey = defaultPrivKeyCertRequest.getPrivateKey();
/*      */     
/*  320 */     if (defaultPrivKey == null) {
/*  321 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0219_CANNOT_LOCATE_DEFAULT_PRIVKEY());
/*  322 */       throw new XWSSecurityException("Unable to locate a default certificate");
/*      */     } 
/*      */     
/*  325 */     return defaultPrivKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SecretKey getSecretKey(Map context, String alias, boolean encryptMode) throws XWSSecurityException {
/*  331 */     SecretKey symmetricKey = null;
/*      */     
/*  333 */     if (encryptMode) {
/*  334 */       EncryptionKeyCallback.AliasSymmetricKeyRequest aliasSymmetricKeyRequest = new EncryptionKeyCallback.AliasSymmetricKeyRequest(alias);
/*      */       
/*  336 */       EncryptionKeyCallback encKeyCallback = new EncryptionKeyCallback((EncryptionKeyCallback.Request)aliasSymmetricKeyRequest);
/*      */       
/*  338 */       ProcessingContext.copy(encKeyCallback.getRuntimeProperties(), context);
/*      */       
/*  340 */       Callback[] callbacks = { (Callback)encKeyCallback };
/*      */       try {
/*  342 */         this.callbackHandler.handle(callbacks);
/*  343 */       } catch (Exception e) {
/*  344 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("EncryptionKeyCallback.AliasSymmetricKeyRequest"), new Object[] { "EncryptionKeyCallback.AliasSymmetricKeyRequest" });
/*      */         
/*  346 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  347 */         throw new XWSSecurityException(e);
/*      */       } 
/*  349 */       symmetricKey = aliasSymmetricKeyRequest.getSymmetricKey();
/*      */     } else {
/*  351 */       DecryptionKeyCallback.AliasSymmetricKeyRequest aliasSymmetricKeyRequest = new DecryptionKeyCallback.AliasSymmetricKeyRequest(alias);
/*      */       
/*  353 */       DecryptionKeyCallback decryptKeyCallback = new DecryptionKeyCallback((DecryptionKeyCallback.Request)aliasSymmetricKeyRequest);
/*      */       
/*  355 */       ProcessingContext.copy(decryptKeyCallback.getRuntimeProperties(), context);
/*      */       
/*  357 */       Callback[] callbacks = { (Callback)decryptKeyCallback };
/*      */       try {
/*  359 */         this.callbackHandler.handle(callbacks);
/*  360 */       } catch (Exception e) {
/*  361 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("DecryptionKeyCallback.AliasSymmetricKeyRequest"), new Object[] { "DecryptionKeyCallback.AliasSymmetricKeyRequest" });
/*      */         
/*  363 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  364 */         throw new XWSSecurityException(e);
/*      */       } 
/*  366 */       symmetricKey = aliasSymmetricKeyRequest.getSymmetricKey();
/*      */     } 
/*      */     
/*  369 */     if (symmetricKey == null) {
/*  370 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0220_CANNOT_LOCATE_SYMMETRICKEY_FOR_DECRYPT());
/*  371 */       throw new XWSSecurityException("Could not locate the symmetric key for alias '" + alias + "'");
/*      */     } 
/*      */     
/*  374 */     return symmetricKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, String alias, boolean forSigning) throws XWSSecurityException {
/*  380 */     X509Certificate cert = null;
/*      */     
/*  382 */     if (alias == null || ("".equals(alias) && forSigning)) {
/*  383 */       return getDefaultCertificate(context);
/*      */     }
/*  385 */     cert = getPublicCredentialsFromLCSubject();
/*  386 */     if (cert != null) {
/*  387 */       return cert;
/*      */     }
/*  389 */     if (forSigning) {
/*  390 */       SignatureKeyCallback.AliasPrivKeyCertRequest aliasPrivKeyCertRequest = new SignatureKeyCallback.AliasPrivKeyCertRequest(alias);
/*      */       
/*  392 */       SignatureKeyCallback sigKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)aliasPrivKeyCertRequest);
/*      */       
/*  394 */       ProcessingContext.copy(sigKeyCallback.getRuntimeProperties(), context);
/*      */       
/*  396 */       Callback[] callbacks = { (Callback)sigKeyCallback };
/*      */       try {
/*  398 */         this.callbackHandler.handle(callbacks);
/*  399 */       } catch (Exception e) {
/*  400 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.AliasPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.AliasPrivKeyCertRequest" });
/*      */         
/*  402 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  403 */         throw new XWSSecurityException(e);
/*      */       } 
/*  405 */       cert = aliasPrivKeyCertRequest.getX509Certificate();
/*      */     } else {
/*  407 */       EncryptionKeyCallback.AliasX509CertificateRequest aliasX509CertificateRequest = new EncryptionKeyCallback.AliasX509CertificateRequest(alias);
/*      */       
/*  409 */       EncryptionKeyCallback encKeyCallback = new EncryptionKeyCallback((EncryptionKeyCallback.Request)aliasX509CertificateRequest);
/*      */ 
/*      */ 
/*      */       
/*  413 */       ProcessingContext.copy(encKeyCallback.getRuntimeProperties(), context);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  418 */       Callback[] callbacks = { (Callback)encKeyCallback };
/*      */       try {
/*  420 */         this.callbackHandler.handle(callbacks);
/*  421 */       } catch (Exception e) {
/*  422 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("EncryptionKeyCallback.AliasX509CertificateRequest"), new Object[] { "EncryptionKeyCallback.AliasX509CertificateRequest" });
/*      */         
/*  424 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  425 */         throw new XWSSecurityException(e);
/*      */       } 
/*  427 */       cert = aliasX509CertificateRequest.getX509Certificate();
/*      */     } 
/*      */     
/*  430 */     if (cert == null) {
/*  431 */       String val = forSigning ? "Signature" : "Key Encryption";
/*  432 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(val), new Object[] { val });
/*  433 */       throw new XWSSecurityException("Unable to locate certificate for the alias '" + alias + "'");
/*      */     } 
/*      */     
/*  436 */     return cert;
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, PublicKey publicKey, boolean forSign) throws XWSSecurityException {
/*  441 */     X509Certificate cert = getPublicCredentialsFromLCSubject();
/*  442 */     if (cert != null && cert.getPublicKey().equals(publicKey)) {
/*  443 */       return cert;
/*      */     }
/*  445 */     if (!forSign) {
/*  446 */       SignatureVerificationKeyCallback.PublicKeyBasedRequest publicKeyBasedRequest = new SignatureVerificationKeyCallback.PublicKeyBasedRequest(publicKey);
/*      */       
/*  448 */       SignatureVerificationKeyCallback verifyKeyCallback = new SignatureVerificationKeyCallback((SignatureVerificationKeyCallback.Request)publicKeyBasedRequest);
/*      */       
/*  450 */       ProcessingContext.copy(verifyKeyCallback.getRuntimeProperties(), context);
/*      */       
/*  452 */       Callback[] arrayOfCallback = { (Callback)verifyKeyCallback };
/*      */       try {
/*  454 */         this.callbackHandler.handle(arrayOfCallback);
/*  455 */       } catch (UnsupportedCallbackException e1) {
/*      */       
/*  457 */       } catch (Exception e) {
/*  458 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureVerificationKeyCallback.PublicKeyBasedRequest"), new Object[] { "SignatureVerificationKeyCallback.PublicKeyBasedRequest" });
/*      */         
/*  460 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  461 */         throw new XWSSecurityException(e);
/*      */       } 
/*  463 */       return publicKeyBasedRequest.getX509Certificate();
/*      */     } 
/*  465 */     EncryptionKeyCallback.PublicKeyBasedRequest pubKeyReq = new EncryptionKeyCallback.PublicKeyBasedRequest(publicKey);
/*      */     
/*  467 */     EncryptionKeyCallback encCallback = new EncryptionKeyCallback((EncryptionKeyCallback.Request)pubKeyReq);
/*      */     
/*  469 */     ProcessingContext.copy(encCallback.getRuntimeProperties(), context);
/*      */     
/*  471 */     Callback[] callbacks = { (Callback)encCallback };
/*      */     try {
/*  473 */       this.callbackHandler.handle(callbacks);
/*  474 */     } catch (UnsupportedCallbackException e1) {
/*      */     
/*  476 */     } catch (Exception e) {
/*  477 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("EncryptionKeyCallback.PublicKeyBasedRequest"), new Object[] { "EncryptionKeyCallback.PublicKeyBasedRequest" });
/*      */       
/*  479 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  480 */       throw new XWSSecurityException(e);
/*      */     } 
/*  482 */     return pubKeyReq.getX509Certificate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, String alias) throws XWSSecurityException {
/*  489 */     PrivateKey privKey = null;
/*      */     
/*  491 */     if (alias == null) {
/*  492 */       return getDefaultPrivateKey(context);
/*      */     }
/*  494 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*  495 */     if (cred != null && cred.getAlias().equals(alias)) {
/*  496 */       return cred.getPrivateKey();
/*      */     }
/*  498 */     SignatureKeyCallback.AliasPrivKeyCertRequest aliasPrivKeyCertRequest = new SignatureKeyCallback.AliasPrivKeyCertRequest(alias);
/*      */     
/*  500 */     SignatureKeyCallback sigKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)aliasPrivKeyCertRequest);
/*      */     
/*  502 */     ProcessingContext.copy(sigKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  504 */     Callback[] callbacks = { (Callback)sigKeyCallback };
/*      */     try {
/*  506 */       this.callbackHandler.handle(callbacks);
/*  507 */     } catch (Exception e) {
/*  508 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.AliasPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.AliasPrivKeyCertRequest" });
/*      */       
/*  510 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  511 */       throw new XWSSecurityException(e);
/*      */     } 
/*  513 */     privKey = aliasPrivKeyCertRequest.getPrivateKey();
/*      */     
/*  515 */     if (privKey == null) {
/*  516 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0222_CANNOT_LOCATE_PRIVKEY(alias), new Object[] { alias });
/*  517 */       throw new XWSSecurityException("Unable to locate private key for the alias " + alias);
/*      */     } 
/*      */     
/*  520 */     return privKey;
/*      */   }
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/*  525 */     if ("Identifier".equals(valueType)) {
/*  526 */       return getPrivateKey(context, identifier);
/*      */     }
/*      */     
/*  529 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*      */     try {
/*  531 */       if (cred != null && matchesThumbPrint(Base64.decode(identifier), cred.getCertificate())) {
/*  532 */         return cred.getPrivateKey();
/*      */       }
/*  534 */     } catch (Exception ex) {
/*  535 */       log.log(Level.SEVERE, (String)null, ex);
/*  536 */       throw new XWSSecurityException(ex);
/*      */     } 
/*  538 */     PrivateKey privateKey = null;
/*      */     
/*  540 */     DecryptionKeyCallback.ThumbprintBasedRequest thumbprintBasedRequest = new DecryptionKeyCallback.ThumbprintBasedRequest(identifier);
/*      */     
/*  542 */     DecryptionKeyCallback decryptKeyCallback = new DecryptionKeyCallback((DecryptionKeyCallback.Request)thumbprintBasedRequest);
/*      */     
/*  544 */     ProcessingContext.copy(decryptKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  546 */     Callback[] callbacks = { (Callback)decryptKeyCallback };
/*      */     try {
/*  548 */       this.callbackHandler.handle(callbacks);
/*  549 */     } catch (Exception e) {
/*  550 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("DecryptionKeyCallback.ThumbprintBasedRequest"), new Object[] { "DecryptionKeyCallback.ThumbprintBasedRequest" });
/*      */       
/*  552 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  553 */       throw new XWSSecurityException(e);
/*      */     } 
/*  555 */     privateKey = thumbprintBasedRequest.getPrivateKey();
/*      */     
/*  557 */     if (privateKey == null) {
/*      */       
/*  559 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0222_CANNOT_LOCATE_PRIVKEY(identifier), new Object[] { identifier });
/*  560 */       throw new XWSSecurityException("No Matching private key for " + Base64.encode(identifier) + " thumb print identifier found");
/*      */     } 
/*      */     
/*  563 */     return privateKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*  569 */     PrivateKey privateKey = null;
/*  570 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*      */     try {
/*  572 */       if (cred != null && matchesKeyIdentifier(Base64.decode(keyIdentifier), cred.getCertificate())) {
/*  573 */         return cred.getPrivateKey();
/*      */       }
/*  575 */     } catch (Base64DecodingException ex) {
/*  576 */       log.log(Level.SEVERE, (String)null, ex);
/*  577 */       throw new XWSSecurityException(ex);
/*      */     } 
/*  579 */     DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest x509SubjectKeyIdentifierBasedRequest = new DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest(keyIdentifier);
/*      */     
/*  581 */     DecryptionKeyCallback decryptKeyCallback = new DecryptionKeyCallback((DecryptionKeyCallback.Request)x509SubjectKeyIdentifierBasedRequest);
/*      */     
/*  583 */     ProcessingContext.copy(decryptKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  585 */     Callback[] callbacks = { (Callback)decryptKeyCallback };
/*      */     try {
/*  587 */       this.callbackHandler.handle(callbacks);
/*  588 */     } catch (Exception e) {
/*  589 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest"), new Object[] { "DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest" });
/*      */       
/*  591 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  592 */       throw new XWSSecurityException(e);
/*      */     } 
/*  594 */     privateKey = x509SubjectKeyIdentifierBasedRequest.getPrivateKey();
/*      */     
/*  596 */     if (privateKey == null) {
/*      */       
/*  598 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0222_CANNOT_LOCATE_PRIVKEY(keyIdentifier), new Object[] { keyIdentifier });
/*  599 */       throw new XWSSecurityException("No Matching private key for " + Base64.encode(keyIdentifier) + " subject key identifier found");
/*      */     } 
/*      */     
/*  602 */     return privateKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/*  608 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*  609 */     if (cred != null) {
/*  610 */       X509Certificate x509Cert = cred.getCertificate();
/*  611 */       BigInteger serialNo = x509Cert.getSerialNumber();
/*      */ 
/*      */       
/*  614 */       X500Principal currentIssuerPrincipal = x509Cert.getIssuerX500Principal();
/*  615 */       X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  616 */       if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */       {
/*  618 */         return cred.getPrivateKey();
/*      */       }
/*      */     } 
/*      */     
/*  622 */     PrivateKey privateKey = null;
/*      */     
/*  624 */     DecryptionKeyCallback.X509IssuerSerialBasedRequest x509IssuerSerialBasedRequest = new DecryptionKeyCallback.X509IssuerSerialBasedRequest(issuerName, serialNumber);
/*      */     
/*  626 */     DecryptionKeyCallback decryptKeyCallback = new DecryptionKeyCallback((DecryptionKeyCallback.Request)x509IssuerSerialBasedRequest);
/*      */     
/*  628 */     ProcessingContext.copy(decryptKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  630 */     Callback[] callbacks = { (Callback)decryptKeyCallback };
/*      */     try {
/*  632 */       this.callbackHandler.handle(callbacks);
/*  633 */     } catch (Exception e) {
/*  634 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("DecryptionKeyCallback.X509IssuerSerialBasedRequest"), new Object[] { "DecryptionKeyCallback.X509IssuerSerialBasedRequest" });
/*      */       
/*  636 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  637 */       throw new XWSSecurityException(e);
/*      */     } 
/*  639 */     privateKey = x509IssuerSerialBasedRequest.getPrivateKey();
/*      */     
/*  641 */     if (privateKey == null) {
/*      */       
/*  643 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0222_CANNOT_LOCATE_PRIVKEY(serialNumber + ":" + issuerName), new Object[] { serialNumber + ":" + issuerName });
/*      */       
/*  645 */       throw new XWSSecurityException("No Matching private key for serial number " + serialNumber + " and issuer name " + issuerName + " found");
/*      */     } 
/*      */ 
/*      */     
/*  649 */     return privateKey;
/*      */   }
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/*  654 */     return getCertificate(context, identifier, valueType).getPublicKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*  659 */     return getCertificate(context, keyIdentifier).getPublicKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, byte[] identifier, String valueType) throws XWSSecurityException {
/*  664 */     if ("Identifier".equals(valueType)) {
/*  665 */       return getCertificate(context, identifier);
/*      */     }
/*      */ 
/*      */     
/*  669 */     X509Certificate cert = null;
/*  670 */     cert = getPublicCredentialsFromLCSubject();
/*      */     try {
/*  672 */       if (cert != null && matchesThumbPrint(Base64.decode(identifier), cert)) {
/*  673 */         return cert;
/*      */       }
/*  675 */     } catch (Base64DecodingException ex) {
/*  676 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0816_BASE_64_DECODING_ERROR(), ex);
/*  677 */       throw new XWSSecurityException(ex);
/*      */     } 
/*      */     
/*  680 */     SignatureVerificationKeyCallback.ThumbprintBasedRequest thumbprintBasedRequest = new SignatureVerificationKeyCallback.ThumbprintBasedRequest(identifier);
/*      */     
/*  682 */     SignatureVerificationKeyCallback verifyKeyCallback = new SignatureVerificationKeyCallback((SignatureVerificationKeyCallback.Request)thumbprintBasedRequest);
/*      */     
/*  684 */     ProcessingContext.copy(verifyKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  686 */     Callback[] callbacks = { (Callback)verifyKeyCallback };
/*      */     try {
/*  688 */       this.callbackHandler.handle(callbacks);
/*  689 */     } catch (Exception e) {
/*  690 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureVerificationKeyCallback.ThumbprintBasedRequest"), new Object[] { "SignatureVerificationKeyCallback.ThumbprintBasedRequest" });
/*      */       
/*  692 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  693 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  696 */     cert = thumbprintBasedRequest.getX509Certificate();
/*      */     
/*  698 */     if (cert == null) {
/*      */       
/*  700 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(identifier), new Object[] { identifier });
/*  701 */       throw new XWSSecurityException("No Matching public key for " + Base64.encode(identifier) + " thumb print identifier found");
/*      */     } 
/*  703 */     return cert;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, byte[] keyIdentifier) throws XWSSecurityException {
/*  709 */     X509Certificate cert = null;
/*  710 */     cert = getPublicCredentialsFromLCSubject();
/*      */     try {
/*  712 */       if (cert != null && matchesKeyIdentifier(Base64.decode(keyIdentifier), cert)) {
/*  713 */         return cert;
/*      */       }
/*  715 */     } catch (Base64DecodingException ex) {
/*  716 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0816_BASE_64_DECODING_ERROR(), ex);
/*  717 */       throw new XWSSecurityException(ex);
/*      */     } 
/*      */     
/*  720 */     SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest x509SubjectKeyIdentifierBasedRequest = new SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest(keyIdentifier);
/*      */     
/*  722 */     SignatureVerificationKeyCallback verifyKeyCallback = new SignatureVerificationKeyCallback((SignatureVerificationKeyCallback.Request)x509SubjectKeyIdentifierBasedRequest);
/*      */     
/*  724 */     ProcessingContext.copy(verifyKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  726 */     Callback[] callbacks = { (Callback)verifyKeyCallback };
/*      */     try {
/*  728 */       this.callbackHandler.handle(callbacks);
/*  729 */     } catch (Exception e) {
/*  730 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest"), new Object[] { "SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest" });
/*      */       
/*  732 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  733 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  736 */     cert = x509SubjectKeyIdentifierBasedRequest.getX509Certificate();
/*      */     
/*  738 */     if (cert == null) {
/*      */       
/*  740 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(keyIdentifier), new Object[] { keyIdentifier });
/*  741 */       throw new XWSSecurityException("No Matching public key for " + Base64.encode(keyIdentifier) + " subject key identifier found");
/*      */     } 
/*  743 */     return cert;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PublicKey getPublicKey(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/*  749 */     return getCertificate(context, serialNumber, issuerName).getPublicKey();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Certificate getCertificate(Map context, BigInteger serialNumber, String issuerName) throws XWSSecurityException {
/*  755 */     X509Certificate cert = null;
/*  756 */     cert = getPublicCredentialsFromLCSubject();
/*      */     
/*  758 */     if (cert != null) {
/*  759 */       BigInteger serialNo = cert.getSerialNumber();
/*      */       
/*  761 */       X500Principal currentIssuerPrincipal = cert.getIssuerX500Principal();
/*  762 */       X500Principal issuerPrincipal = new X500Principal(issuerName);
/*  763 */       if (serialNo.equals(serialNumber) && currentIssuerPrincipal.equals(issuerPrincipal))
/*      */       {
/*  765 */         return cert;
/*      */       }
/*      */     } 
/*      */     
/*  769 */     SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest x509IssuerSerialBasedRequest = new SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest(issuerName, serialNumber);
/*      */     
/*  771 */     SignatureVerificationKeyCallback verifyKeyCallback = new SignatureVerificationKeyCallback((SignatureVerificationKeyCallback.Request)x509IssuerSerialBasedRequest);
/*      */     
/*  773 */     ProcessingContext.copy(verifyKeyCallback.getRuntimeProperties(), context);
/*      */     
/*  775 */     Callback[] callbacks = { (Callback)verifyKeyCallback };
/*      */     try {
/*  777 */       this.callbackHandler.handle(callbacks);
/*  778 */     } catch (Exception e) {
/*  779 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest"), new Object[] { "SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest" });
/*      */       
/*  781 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/*  782 */       throw new XWSSecurityException(e);
/*      */     } 
/*  784 */     cert = x509IssuerSerialBasedRequest.getX509Certificate();
/*      */     
/*  786 */     if (cert == null) {
/*      */       
/*  788 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0221_CANNOT_LOCATE_CERT(serialNumber + ":" + issuerName), new Object[] { serialNumber + ":" + issuerName });
/*  789 */       throw new XWSSecurityException("No Matching public key for serial number " + serialNumber + " and issuer name " + issuerName + " found");
/*      */     } 
/*      */ 
/*      */     
/*  793 */     return cert;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean validateCertificate(X509Certificate cert, Map context) throws XWSSecurityException {
/*  799 */     CertificateValidationCallback certValCallback = new CertificateValidationCallback(cert, context);
/*  800 */     Callback[] callbacks = { (Callback)certValCallback };
/*      */     try {
/*  802 */       this.callbackHandler.handle(callbacks);
/*  803 */     } catch (Exception e) {
/*  804 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0223_FAILED_CERTIFICATE_VALIDATION());
/*  805 */       throw newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Certificate validation failed", e);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  810 */     if (log.isLoggable(Level.FINE)) {
/*  811 */       log.log(Level.FINE, "Certificate Validation called on certificate {0}", cert.getSubjectDN());
/*      */     }
/*  813 */     return certValCallback.getResult();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, String username, String password) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private X500PrivateCredential getPKCredentialsFromLCSubject() {
/*  823 */     if (this.loginContextSubjectForKeystore != null) {
/*  824 */       Set<X500PrivateCredential> set = this.loginContextSubjectForKeystore.getPrivateCredentials(X500PrivateCredential.class);
/*  825 */       if (set != null) {
/*  826 */         Iterator<X500PrivateCredential> it = set.iterator();
/*  827 */         if (it.hasNext()) {
/*  828 */           X500PrivateCredential cred = it.next();
/*  829 */           return cred;
/*      */         } 
/*      */       } 
/*      */     } 
/*  833 */     return null;
/*      */   }
/*      */   
/*      */   private X509Certificate getPublicCredentialsFromLCSubject() {
/*  837 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/*  838 */     if (cred != null) {
/*  839 */       return cred.getCertificate();
/*      */     }
/*  841 */     return null;
/*      */   }
/*      */   
/*      */   private Subject initJAASKeyStoreLoginModule() {
/*  845 */     if (this.JAASLoginModuleForKeystore == null) {
/*  846 */       return null;
/*      */     }
/*  848 */     LoginContext lc = null;
/*      */     try {
/*  850 */       if (this.keyStoreCBH != null) {
/*  851 */         this.keystoreCbHandlerClass = loadClass(this.keyStoreCBH).newInstance();
/*  852 */         lc = new LoginContext(this.JAASLoginModuleForKeystore, this.keystoreCbHandlerClass);
/*      */       } else {
/*  854 */         lc = new LoginContext(this.JAASLoginModuleForKeystore);
/*      */       } 
/*  856 */       lc.login();
/*  857 */       return lc.getSubject();
/*  858 */     } catch (InstantiationException ex) {
/*  859 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), ex);
/*  860 */       throw new XWSSecurityRuntimeException(ex);
/*  861 */     } catch (IllegalAccessException ex) {
/*  862 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), ex);
/*  863 */       throw new XWSSecurityRuntimeException(ex);
/*  864 */     } catch (XWSSecurityException ex) {
/*  865 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), (Throwable)ex);
/*  866 */       throw new XWSSecurityRuntimeException(ex);
/*  867 */     } catch (LoginException ex) {
/*  868 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0817_KEYSTORE_LOGIN_MODULE_LOGIN_ERROR(), ex);
/*  869 */       throw new XWSSecurityRuntimeException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesKeyIdentifier(byte[] keyIdMatch, X509Certificate x509Cert) throws XWSSecurityException {
/*  877 */     byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/*  878 */     if (keyId == null)
/*      */     {
/*  880 */       return false;
/*      */     }
/*      */     
/*  883 */     if (Arrays.equals(keyIdMatch, keyId)) {
/*  884 */       return true;
/*      */     }
/*  886 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchesThumbPrint(byte[] keyIdMatch, X509Certificate x509Cert) throws XWSSecurityException {
/*  893 */     byte[] keyId = XWSSUtil.getThumbprintIdentifier(x509Cert);
/*  894 */     if (keyId == null)
/*      */     {
/*  896 */       return false;
/*      */     }
/*      */     
/*  899 */     if (Arrays.equals(keyIdMatch, keyId)) {
/*  900 */       return true;
/*      */     }
/*  902 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateUsernameInSubject(final Subject subject, final String username, String password) {
/*  908 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*  911 */             String x500Name = "CN=" + username;
/*      */             
/*  913 */             Principal principal = null;
/*      */             try {
/*  915 */               principal = new X500Principal(x500Name);
/*  916 */               subject.getPrincipals().add(principal);
/*  917 */             } catch (Throwable t) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  923 */             subject.getPublicCredentials().add(username);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  928 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final X509Certificate cert) {
/*  936 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*  939 */             Principal principal = cert.getSubjectX500Principal();
/*  940 */             subject.getPrincipals().add(principal);
/*  941 */             subject.getPublicCredentials().add(cert);
/*  942 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final Assertion assertion) {
/*  950 */     if (this.callbackHandler instanceof DefaultCallbackHandler && (
/*  951 */       (DefaultCallbackHandler)this.callbackHandler).getSAMLValidator() instanceof com.sun.xml.wss.impl.callback.SAMLValidator) {
/*      */       return;
/*      */     }
/*      */     
/*  955 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*  958 */             subject.getPublicCredentials().add(assertion);
/*  959 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, Key secretKey) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, String ek) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Subject getSubject(final Map context) {
/*  990 */     Subject otherPartySubject = (Subject)context.get("javax.security.auth.Subject");
/*      */     
/*  992 */     if (otherPartySubject != null) {
/*  993 */       return otherPartySubject;
/*      */     }
/*  995 */     otherPartySubject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>()
/*      */         {
/*      */           
/*      */           public Subject run()
/*      */           {
/* 1000 */             Subject otherPartySubj = new Subject();
/* 1001 */             context.put("javax.security.auth.Subject", otherPartySubj);
/* 1002 */             return otherPartySubj;
/*      */           }
/*      */         });
/* 1005 */     return otherPartySubject;
/*      */   }
/*      */   
/*      */   public static Subject getSubject(final FilterProcessingContext context) {
/* 1009 */     Subject otherPartySubject = (Subject)context.getExtraneousProperty("javax.security.auth.Subject");
/* 1010 */     if (otherPartySubject != null) {
/* 1011 */       return otherPartySubject;
/*      */     }
/* 1013 */     otherPartySubject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>()
/*      */         {
/*      */           public Subject run()
/*      */           {
/* 1017 */             Subject otherPartySubj = new Subject();
/* 1018 */             context.setExtraneousProperty("javax.security.auth.Subject", otherPartySubj);
/* 1019 */             return otherPartySubj;
/*      */           }
/*      */         });
/* 1022 */     return otherPartySubject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, X509Certificate cert) throws XWSSecurityException {
/* 1028 */     PrivateKey privateKey = null;
/* 1029 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/* 1030 */     if (cred != null) {
/* 1031 */       X509Certificate x509Cert = cred.getCertificate();
/* 1032 */       if (x509Cert.equals(cert)) {
/* 1033 */         return cred.getPrivateKey();
/*      */       }
/*      */     } 
/* 1036 */     DecryptionKeyCallback.X509CertificateBasedRequest x509CertificateBasedRequest = new DecryptionKeyCallback.X509CertificateBasedRequest(cert);
/*      */     
/* 1038 */     DecryptionKeyCallback decryptKeyCallback = new DecryptionKeyCallback((DecryptionKeyCallback.Request)x509CertificateBasedRequest);
/*      */     
/* 1040 */     ProcessingContext.copy(decryptKeyCallback.getRuntimeProperties(), context);
/*      */     
/* 1042 */     Callback[] callbacks = { (Callback)decryptKeyCallback };
/*      */     try {
/* 1044 */       this.callbackHandler.handle(callbacks);
/* 1045 */     } catch (Exception e) {
/* 1046 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("DecryptionKeyCallback.X509CertificateBasedRequest"), new Object[] { "DecryptionKeyCallback.X509CertificateBasedRequest" });
/*      */       
/* 1048 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/* 1049 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1051 */     privateKey = x509CertificateBasedRequest.getPrivateKey();
/*      */     
/* 1053 */     if (privateKey == null) {
/* 1054 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0222_CANNOT_LOCATE_PRIVKEY("given certificate"), new Object[] { "given certificate" });
/* 1055 */       throw new XWSSecurityException("Could not retrieve private Key matching the given certificate");
/*      */     } 
/*      */     
/* 1058 */     return privateKey;
/*      */   }
/*      */ 
/*      */   
/*      */   public PrivateKey getPrivateKey(Map context, PublicKey publicKey, boolean forSign) throws XWSSecurityException {
/* 1063 */     X500PrivateCredential cred = getPKCredentialsFromLCSubject();
/* 1064 */     if (cred != null) {
/* 1065 */       X509Certificate x509Cert = cred.getCertificate();
/* 1066 */       if (x509Cert.getPublicKey().equals(publicKey)) {
/* 1067 */         return cred.getPrivateKey();
/*      */       }
/*      */     } 
/* 1070 */     if (forSign) {
/* 1071 */       SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest publicKeyBasedPrivKeyCertRequest = new SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest(publicKey);
/*      */       
/* 1073 */       SignatureKeyCallback skc = new SignatureKeyCallback((SignatureKeyCallback.Request)publicKeyBasedPrivKeyCertRequest);
/*      */       
/* 1075 */       ProcessingContext.copy(skc.getRuntimeProperties(), context);
/*      */       
/* 1077 */       Callback[] arrayOfCallback = { (Callback)skc };
/*      */       try {
/* 1079 */         this.callbackHandler.handle(arrayOfCallback);
/* 1080 */       } catch (Exception e) {
/* 1081 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest"), new Object[] { "SignatureKeyCallback.PublicKeyBasedPrivKeyCertRequest" });
/*      */         
/* 1083 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/* 1084 */         throw new XWSSecurityException(e);
/*      */       } 
/* 1086 */       return publicKeyBasedPrivKeyCertRequest.getPrivateKey();
/*      */     } 
/* 1088 */     DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest req = new DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest(publicKey);
/*      */     
/* 1090 */     DecryptionKeyCallback dkc = new DecryptionKeyCallback((DecryptionKeyCallback.Request)req);
/*      */     
/* 1092 */     ProcessingContext.copy(dkc.getRuntimeProperties(), context);
/*      */     
/* 1094 */     Callback[] callbacks = { (Callback)dkc };
/*      */     try {
/* 1096 */       this.callbackHandler.handle(callbacks);
/* 1097 */     } catch (Exception e) {
/* 1098 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest"), new Object[] { "DecryptionKeyCallback.PublicKeyBasedPrivKeyRequest" });
/*      */       
/* 1100 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/* 1101 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1103 */     return req.getPrivateKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public Subject getSubject() {
/* 1108 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0224_UNSUPPORTED_ASSOCIATED_SUBJECT());
/* 1109 */     throw new UnsupportedOperationException("This environment does not have an associated Subject");
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
/*      */   public boolean authenticateUser(Map context, String username, String passwordDigest, String nonce, String created) throws XWSSecurityException {
/* 1121 */     PasswordValidationCallback.DigestPasswordRequest request = new PasswordValidationCallback.DigestPasswordRequest(username, passwordDigest, nonce, created);
/*      */ 
/*      */     
/* 1124 */     PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */     
/* 1126 */     ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/* 1127 */     Callback[] callbacks = { (Callback)passwordValidationCallback };
/*      */     
/* 1129 */     boolean result = false;
/*      */     try {
/* 1131 */       this.callbackHandler.handle(callbacks);
/* 1132 */       RealmAuthenticationAdapter adapter = passwordValidationCallback.getRealmAuthenticationAdapter();
/* 1133 */       if (passwordValidationCallback.getValidator() != null) {
/* 1134 */         result = passwordValidationCallback.getResult();
/* 1135 */         if (result == true) {
/* 1136 */           updateUsernameInSubject(getSubject(context), username, null);
/*      */         }
/* 1138 */       } else if (adapter != null) {
/* 1139 */         result = adapter.authenticate(getSubject(context), username, passwordDigest, nonce, created, context);
/*      */       } else {
/* 1141 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0295_PASSWORD_VAL_NOT_CONFIG_USERNAME_VAL());
/* 1142 */         throw new XWSSecurityException("Error: No PasswordValidator Configured for UsernameToken Validation");
/*      */       } 
/* 1144 */     } catch (Exception e) {
/* 1145 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/* 1146 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1148 */     if (log.isLoggable(Level.FINE)) {
/* 1149 */       log.log(Level.FINE, "Username Authentication done for {0}", username);
/*      */     }
/* 1151 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean authenticateUser(Map context, String username, String password) throws XWSSecurityException {
/* 1157 */     PasswordValidationCallback.PlainTextPasswordRequest request = new PasswordValidationCallback.PlainTextPasswordRequest(username, password);
/*      */     
/* 1159 */     PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */     
/* 1161 */     ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/* 1162 */     Callback[] callbacks = { (Callback)passwordValidationCallback };
/* 1163 */     boolean result = false;
/*      */     try {
/* 1165 */       this.callbackHandler.handle(callbacks);
/* 1166 */       RealmAuthenticationAdapter adapter = passwordValidationCallback.getRealmAuthenticationAdapter();
/* 1167 */       if (passwordValidationCallback.getValidator() != null) {
/* 1168 */         result = passwordValidationCallback.getResult();
/* 1169 */         if (result == true) {
/* 1170 */           updateUsernameInSubject(getSubject(context), username, password);
/*      */         }
/* 1172 */       } else if (adapter != null) {
/* 1173 */         result = adapter.authenticate(getSubject(context), username, password, context);
/*      */       } else {
/* 1175 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0295_PASSWORD_VAL_NOT_CONFIG_USERNAME_VAL());
/* 1176 */         throw new XWSSecurityException("Error: No PasswordValidator Configured for UsernameToken Validation");
/*      */       } 
/* 1178 */     } catch (Exception e) {
/* 1179 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/* 1180 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1182 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String authenticateUser(Map context, String username) throws XWSSecurityException {
/* 1188 */     PasswordValidationCallback.DerivedKeyPasswordRequest request = new PasswordValidationCallback.DerivedKeyPasswordRequest(username);
/*      */     
/* 1190 */     PasswordValidationCallback passwordValidationCallback = new PasswordValidationCallback((PasswordValidationCallback.Request)request);
/*      */     
/* 1192 */     ProcessingContext.copy(passwordValidationCallback.getRuntimeProperties(), context);
/* 1193 */     Callback[] callbacks = { (Callback)passwordValidationCallback };
/* 1194 */     boolean result = false;
/*      */     try {
/* 1196 */       this.callbackHandler.handle(callbacks);
/* 1197 */       RealmAuthenticationAdapter adapter = passwordValidationCallback.getRealmAuthenticationAdapter();
/* 1198 */       if (passwordValidationCallback.getValidator() != null) {
/* 1199 */         result = passwordValidationCallback.getResult();
/*      */         
/* 1201 */         if (result == true) {
/* 1202 */           updateUsernameInSubject(getSubject(context), username, null);
/*      */         }
/*      */       } 
/* 1205 */     } catch (Exception e) {
/* 1206 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/* 1207 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1209 */     return request.getPassword();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void defaultValidateCreationTime(String creationTime, long maxClockSkew, long timestampFreshnessLimit) throws XWSSecurityException {
/* 1220 */     Date created = null;
/*      */     try {
/* 1222 */       synchronized (this.calendarFormatter1) {
/* 1223 */         created = this.calendarFormatter1.parse(creationTime);
/*      */       } 
/* 1225 */     } catch (ParseException e) {
/* 1226 */       synchronized (this.calendarFormatter2) {
/*      */         try {
/* 1228 */           created = this.calendarFormatter2.parse(creationTime);
/* 1229 */         } catch (ParseException ex) {
/* 1230 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0226_FAILED_VALIDATING_DEFAULT_CREATION_TIME(), ex);
/* 1231 */           throw new XWSSecurityException(ex);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1238 */     Date current = getFreshnessAndSkewAdjustedDate(maxClockSkew, timestampFreshnessLimit);
/*      */     
/* 1240 */     if (created.before(current)) {
/* 1241 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0227_INVALID_OLDER_CREATION_TIME());
/* 1242 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "The creation time is older than  currenttime - timestamp-freshness-limit - max-clock-skew", null, true);
/*      */     } 
/*      */ 
/*      */     
/* 1246 */     Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, true);
/*      */     
/* 1248 */     if (currentTime.before(created)) {
/* 1249 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0228_INVALID_AHEAD_CREATION_TIME());
/* 1250 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "The creation time is ahead of the current time.", null, true);
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
/*      */   public void validateCreationTime(Map context, String creationTime, long maxClockSkew, long timestampFreshnessLimit) throws XWSSecurityException {
/* 1268 */     TimestampValidationCallback.UTCTimestampRequest request = new TimestampValidationCallback.UTCTimestampRequest(creationTime, null, maxClockSkew, timestampFreshnessLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1275 */     request.isUsernameToken(true);
/* 1276 */     TimestampValidationCallback timestampValidationCallback = new TimestampValidationCallback((TimestampValidationCallback.Request)request);
/*      */     
/* 1278 */     if (!this.isDefaultHandler) {
/* 1279 */       ProcessingContext.copy(timestampValidationCallback.getRuntimeProperties(), context);
/*      */     }
/* 1281 */     Callback[] callbacks = { (Callback)timestampValidationCallback };
/* 1282 */     boolean unSupported = false;
/*      */     try {
/* 1284 */       this.callbackHandler.handle(callbacks);
/* 1285 */     } catch (UnsupportedCallbackException e) {
/* 1286 */       unSupported = true;
/* 1287 */     } catch (Exception e) {
/* 1288 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0226_FAILED_VALIDATING_DEFAULT_CREATION_TIME());
/* 1289 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1292 */     if (unSupported) {
/* 1293 */       defaultValidateCreationTime(creationTime, maxClockSkew, timestampFreshnessLimit);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 1298 */       timestampValidationCallback.getResult();
/* 1299 */     } catch (com.sun.xml.wss.impl.callback.TimestampValidationCallback.TimestampValidationException e) {
/* 1300 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), (Throwable)e);
/* 1301 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean validateSamlIssuer(String issuer) {
/* 1307 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0230_UNSUPPORTED_VALIDATING_SAML_ISSUER());
/* 1308 */     throw new UnsupportedOperationException("SAML Issuer Validation not yet supported");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean validateSamlUser(String user, String domain, String format) {
/* 1314 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0231_UNSUPPORTED_VALIDATING_SAML_USER());
/* 1315 */     throw new UnsupportedOperationException("SAML User Validation not yet supported");
/*      */   }
/*      */   
/*      */   public String getUsername(Map context) throws XWSSecurityException {
/* 1319 */     UsernameCallback usernameCallback = new UsernameCallback();
/*      */     
/* 1321 */     ProcessingContext.copy(usernameCallback.getRuntimeProperties(), context);
/*      */     
/* 1323 */     Callback[] callbacks = { (Callback)usernameCallback };
/*      */     try {
/* 1325 */       this.callbackHandler.handle(callbacks);
/* 1326 */     } catch (Exception e) {
/* 1327 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0216_CALLBACKHANDLER_HANDLE_EXCEPTION("UsernameCallback"), new Object[] { "UsernameCallback" });
/*      */       
/* 1329 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0217_CALLBACKHANDLER_HANDLE_EXCEPTION_LOG(), e);
/* 1330 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1332 */     return usernameCallback.getUsername();
/*      */   }
/*      */   
/*      */   public String getPassword(Map context) throws XWSSecurityException {
/* 1336 */     PasswordCallback passwordCallback = new PasswordCallback();
/*      */     
/* 1338 */     ProcessingContext.copy(passwordCallback.getRuntimeProperties(), context);
/*      */     
/* 1340 */     Callback[] callbacks = { (Callback)passwordCallback };
/*      */     try {
/* 1342 */       this.callbackHandler.handle(callbacks);
/* 1343 */     } catch (Exception e) {
/* 1344 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0225_FAILED_PASSWORD_VALIDATION_CALLBACK(), e);
/* 1345 */       throw new XWSSecurityException(e.getMessage(), e);
/*      */     } 
/* 1347 */     return passwordCallback.getPassword();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void defaultValidateExpirationTime(String expirationTime, long maxClockSkew, long timestampFreshnessLimit) throws XWSSecurityException {
/* 1356 */     if (expirationTime != null) {
/* 1357 */       Date expires = null;
/*      */       try {
/* 1359 */         synchronized (this.calendarFormatter1) {
/* 1360 */           expires = this.calendarFormatter1.parse(expirationTime);
/*      */         } 
/* 1362 */       } catch (ParseException pe) {
/* 1363 */         synchronized (this.calendarFormatter2) {
/*      */           try {
/* 1365 */             expires = this.calendarFormatter2.parse(expirationTime);
/* 1366 */           } catch (ParseException e) {
/* 1367 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0394_ERROR_PARSING_EXPIRATIONTIME());
/* 1368 */             throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e, true);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1374 */       Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, false);
/*      */       
/* 1376 */       if (expires.before(currentTime)) {
/* 1377 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0393_CURRENT_AHEAD_OF_EXPIRES());
/* 1378 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSU_MESSAGE_EXPIRED, "The current time is ahead of the expiration time in Timestamp", null, true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateTimestamp(Map context, Timestamp timestamp, long maxClockSkew, long freshnessLimit) throws XWSSecurityException {
/* 1387 */     validateTimestamp(context, timestamp.getCreated(), timestamp.getExpires(), maxClockSkew, freshnessLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateTimestamp(Map context, String created, String expires, long maxClockSkew, long freshnessLimit) throws XWSSecurityException {
/* 1394 */     if (expiresBeforeCreated(created, expires)) {
/* 1395 */       XWSSecurityException xwsse = new XWSSecurityException("Message expired!");
/* 1396 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0232_EXPIRED_MESSAGE());
/* 1397 */       throw newSOAPFaultException(MessageConstants.WSU_MESSAGE_EXPIRED, "Message expired!", xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1403 */     TimestampValidationCallback.UTCTimestampRequest request = new TimestampValidationCallback.UTCTimestampRequest(created, expires, maxClockSkew, freshnessLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1410 */     TimestampValidationCallback timestampValidationCallback = new TimestampValidationCallback((TimestampValidationCallback.Request)request);
/*      */     
/* 1412 */     if (!this.isDefaultHandler) {
/* 1413 */       ProcessingContext.copy(timestampValidationCallback.getRuntimeProperties(), context);
/*      */     }
/* 1415 */     Callback[] callbacks = { (Callback)timestampValidationCallback };
/* 1416 */     boolean unSupported = false;
/*      */     try {
/* 1418 */       this.callbackHandler.handle(callbacks);
/* 1419 */     } catch (UnsupportedCallbackException e) {
/* 1420 */       unSupported = true;
/* 1421 */     } catch (Exception e) {
/* 1422 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), e);
/* 1423 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1426 */     if (unSupported) {
/*      */       
/* 1428 */       defaultValidateCreationTime(created, maxClockSkew, freshnessLimit);
/* 1429 */       defaultValidateExpirationTime(expires, maxClockSkew, freshnessLimit);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 1434 */       timestampValidationCallback.getResult();
/* 1435 */     } catch (com.sun.xml.wss.impl.callback.TimestampValidationCallback.TimestampValidationException e) {
/* 1436 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0229_FAILED_VALIDATING_TIME_STAMP(), (Throwable)e);
/* 1437 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, e.getMessage(), e, true);
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
/*      */   public static WssSoapFaultException newSOAPFaultException(QName faultCode, String faultstring, Throwable th) {
/* 1449 */     WssSoapFaultException sfe = new WssSoapFaultException(faultCode, faultstring, null, null);
/*      */     
/* 1451 */     sfe.initCause(th);
/* 1452 */     return sfe;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date getGMTDateWithSkewAdjusted(Calendar c, long maxClockSkew, boolean addSkew) {
/* 1461 */     long beforeTime = c.getTimeInMillis();
/* 1462 */     long currentTime = beforeTime - offset;
/*      */     
/* 1464 */     if (addSkew) {
/* 1465 */       currentTime += maxClockSkew;
/*      */     } else {
/* 1467 */       currentTime -= maxClockSkew;
/*      */     } 
/*      */     
/* 1470 */     c.setTimeInMillis(currentTime);
/* 1471 */     return c.getTime();
/*      */   }
/*      */ 
/*      */   
/*      */   private static Date getFreshnessAndSkewAdjustedDate(long maxClockSkew, long timestampFreshnessLimit) {
/* 1476 */     Calendar c = new GregorianCalendar();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1481 */     long beforeTime = c.getTimeInMillis();
/* 1482 */     long currentTime = beforeTime - offset;
/*      */ 
/*      */     
/* 1485 */     long adjustedTime = currentTime - maxClockSkew - timestampFreshnessLimit;
/* 1486 */     c.setTimeInMillis(adjustedTime);
/*      */     
/* 1488 */     return c.getTime();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean expiresBeforeCreated(String creationTime, String expirationTime) throws XWSSecurityException {
/* 1493 */     Date created = null;
/* 1494 */     Date expires = null;
/*      */     try {
/* 1496 */       synchronized (this.calendarFormatter1) {
/* 1497 */         created = this.calendarFormatter1.parse(creationTime);
/* 1498 */         if (expirationTime != null) {
/* 1499 */           expires = this.calendarFormatter1.parse(expirationTime);
/*      */         }
/*      */       } 
/* 1502 */     } catch (ParseException pe) {
/* 1503 */       synchronized (this.calendarFormatter2) {
/*      */         try {
/* 1505 */           created = this.calendarFormatter2.parse(creationTime);
/* 1506 */           if (expirationTime != null) {
/* 1507 */             expires = this.calendarFormatter2.parse(expirationTime);
/*      */           }
/* 1509 */         } catch (ParseException xpe) {
/* 1510 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0233_INVALID_EXPIRE_BEFORE_CREATION(), xpe);
/* 1511 */           throw new XWSSecurityException(xpe.getMessage());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1517 */     if (expires != null && expires.equals(created)) {
/* 1518 */       return true;
/*      */     }
/*      */     
/* 1521 */     if (expires != null && expires.before(created)) {
/* 1522 */       return true;
/*      */     }
/*      */     
/* 1525 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateSAMLAssertion(Map context, Element assertion) throws XWSSecurityException {
/* 1531 */     AuthenticationTokenPolicy authPolicy = new AuthenticationTokenPolicy();
/* 1532 */     AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = (AuthenticationTokenPolicy.SAMLAssertionBinding)authPolicy.newSAMLAssertionFeatureBinding();
/*      */     
/* 1534 */     samlPolicy.setAssertion(assertion);
/*      */     
/* 1536 */     DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)samlPolicy, null);
/*      */ 
/*      */ 
/*      */     
/* 1540 */     ProcessingContext.copy(dynamicCallback.getRuntimeProperties(), context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1546 */       Callback[] callbacks = { (Callback)dynamicCallback };
/* 1547 */       this.callbackHandler.handle(callbacks);
/* 1548 */     } catch (Exception e) {
/* 1549 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0234_FAILED_VALIDATE_SAML_ASSERTION(), e);
/* 1550 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Validation failed for SAML Assertion ", e, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element locateSAMLAssertion(Map context, Element binding, String assertionId, Document ownerDoc) throws XWSSecurityException {
/* 1559 */     AuthenticationTokenPolicy authPolicy = new AuthenticationTokenPolicy();
/* 1560 */     AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = (AuthenticationTokenPolicy.SAMLAssertionBinding)authPolicy.newSAMLAssertionFeatureBinding();
/*      */     
/* 1562 */     samlPolicy.setAuthorityBinding(binding);
/* 1563 */     samlPolicy.setAssertionId(assertionId);
/*      */     
/* 1565 */     DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)samlPolicy, null);
/*      */ 
/*      */ 
/*      */     
/* 1569 */     ProcessingContext.copy(dynamicCallback.getRuntimeProperties(), context);
/*      */     
/*      */     try {
/* 1572 */       Callback[] callbacks = { (Callback)dynamicCallback };
/* 1573 */       this.callbackHandler.handle(callbacks);
/* 1574 */     } catch (Exception e) {
/* 1575 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0235_FAILED_LOCATE_SAML_ASSERTION(), e);
/* 1576 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1578 */     Element assertion = samlPolicy.getAssertion();
/* 1579 */     if (assertion == null) {
/* 1580 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0236_NULL_SAML_ASSERTION());
/* 1581 */       throw new XWSSecurityException("SAML Assertion not set into Policy by CallbackHandler");
/*      */     } 
/*      */     
/* 1584 */     return assertion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthenticationTokenPolicy.SAMLAssertionBinding populateSAMLPolicy(Map fpcontext, AuthenticationTokenPolicy.SAMLAssertionBinding policy, DynamicApplicationContext context) throws XWSSecurityException {
/* 1591 */     DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)policy, (DynamicPolicyContext)context);
/*      */     
/* 1593 */     if (context != null) {
/* 1594 */       ProcessingContext.copy(dynamicCallback.getRuntimeProperties(), fpcontext);
/*      */     }
/*      */     try {
/* 1597 */       Callback[] callbacks = { (Callback)dynamicCallback };
/* 1598 */       this.callbackHandler.handle(callbacks);
/* 1599 */     } catch (Exception e) {
/* 1600 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0237_FAILED_DYNAMIC_POLICY_CALLBACK(), e);
/* 1601 */       throw new XWSSecurityException(e);
/*      */     } 
/* 1603 */     return (AuthenticationTokenPolicy.SAMLAssertionBinding)dynamicCallback.getSecurityPolicy();
/*      */   }
/*      */   
/*      */   public CallbackHandler getCallbackHandler() {
/* 1607 */     return this.callbackHandler;
/*      */   }
/*      */   
/*      */   private void validateSamlVersion(Assertion assertion) {
/* 1611 */     BigInteger major = assertion.getMajorVersion();
/* 1612 */     BigInteger minor = assertion.getMinorVersion();
/*      */     
/* 1614 */     if (major.intValue() != 1) {
/* 1615 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0404_SAML_INVALID_VERSION());
/* 1616 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Major version is not 1 for SAML Assertion:" + assertion.getAssertionID(), new Exception("Major version is not 1 for SAML Assertion"));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1623 */     if (minor.intValue() != 0 && minor.intValue() != 1) {
/* 1624 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0404_SAML_INVALID_VERSION());
/* 1625 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Minor version is not 0/1 for SAML Assertion:" + assertion.getAssertionID(), new Exception("Minor version is not 0/1 for SAML Assertion"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateIssuer(SecurableSoapMessage secMessage, Assertion assertion) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateSamlUser(SecurableSoapMessage secMessage, Assertion assertion) {
/* 1641 */     String user = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateSAMLAssertion(Map context, XMLStreamReader assertion) throws XWSSecurityException {
/* 1648 */     AuthenticationTokenPolicy authPolicy = new AuthenticationTokenPolicy();
/* 1649 */     AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = (AuthenticationTokenPolicy.SAMLAssertionBinding)authPolicy.newSAMLAssertionFeatureBinding();
/*      */     
/* 1651 */     samlPolicy.setAssertion(assertion);
/*      */     
/* 1653 */     DynamicPolicyCallback dynamicCallback = new DynamicPolicyCallback((SecurityPolicy)samlPolicy, null);
/*      */ 
/*      */     
/* 1656 */     ProcessingContext.copy(dynamicCallback.getRuntimeProperties(), context);
/*      */     
/* 1658 */     if (context.get("javax.security.auth.Subject") == null) {
/* 1659 */       dynamicCallback.getRuntimeProperties().put("javax.security.auth.Subject", getSubject(context));
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1664 */       Callback[] callbacks = { (Callback)dynamicCallback };
/* 1665 */       this.callbackHandler.handle(callbacks);
/* 1666 */     } catch (Exception e) {
/* 1667 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0234_FAILED_VALIDATE_SAML_ASSERTION(), e);
/* 1668 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "Validation failed for SAML Assertion ", e, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final XMLStreamReader assertion) {
/* 1675 */     if (this.callbackHandler instanceof DefaultCallbackHandler && (
/* 1676 */       (DefaultCallbackHandler)this.callbackHandler).getSAMLValidator() instanceof com.sun.xml.wss.impl.callback.SAMLValidator) {
/*      */       return;
/*      */     }
/*      */     
/* 1680 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */           public Object run() {
/* 1682 */             subject.getPublicCredentials().add(assertion);
/* 1683 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelfCertificate(X509Certificate cert) {
/* 1692 */     return false;
/*      */   }
/*      */   
/*      */   public void updateOtherPartySubject(Subject subject, Subject bootStrapSubject) {
/* 1696 */     SecurityUtil.copySubject(subject, bootStrapSubject);
/*      */   }
/*      */   
/*      */   public KerberosContext doKerberosLogin() throws XWSSecurityException {
/* 1700 */     String loginModule = this.configAssertions.getProperty("krb5.login.module");
/* 1701 */     String servicePrincipal = this.configAssertions.getProperty("krb5.service.principal");
/* 1702 */     boolean credentialDelegation = Boolean.valueOf(this.configAssertions.getProperty("krb5.credential.delegation")).booleanValue();
/* 1703 */     if (loginModule == null || loginModule.equals("")) {
/* 1704 */       throw new XWSSecurityException("Login Module for Kerberos login is not set or could not be obtained");
/*      */     }
/* 1706 */     if (servicePrincipal == null || servicePrincipal.equals("")) {
/* 1707 */       throw new XWSSecurityException("Kerberos Service Principal is not set or could not be obtained");
/*      */     }
/* 1709 */     return (new KerberosLogin()).login(loginModule, servicePrincipal, credentialDelegation);
/*      */   }
/*      */   
/*      */   public KerberosContext doKerberosLogin(byte[] tokenValue) throws XWSSecurityException {
/* 1713 */     String loginModule = this.configAssertions.getProperty("krb5.login.module");
/* 1714 */     return (new KerberosLogin()).login(loginModule, tokenValue);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateOtherPartySubject(final Subject subject, final GSSName clientCred, final GSSCredential gssCred) {
/* 1720 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */           public Object run() {
/* 1722 */             KerberosPrincipal kerbPrincipal = new KerberosPrincipal(clientCred.toString());
/* 1723 */             subject.getPrincipals().add(kerbPrincipal);
/* 1724 */             subject.getPublicCredentials().add(clientCred);
/* 1725 */             if (gssCred != null) {
/* 1726 */               subject.getPrivateCredentials().add(gssCred);
/*      */             }
/* 1728 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public boolean validateAndCacheNonce(Map context, String nonce, String created, long nonceAge) throws XWSSecurityException {
/* 1734 */     NonceManager nonceMgr = null;
/* 1735 */     if (this.mnaProperty != null) {
/* 1736 */       nonceMgr = NonceManager.getInstance(this.maxNonceAge, (WSEndpoint)context.get("WSEndpoint"));
/*      */     } else {
/* 1738 */       nonceMgr = NonceManager.getInstance(nonceAge, (WSEndpoint)context.get("WSEndpoint"));
/*      */     } 
/*      */     
/* 1741 */     return nonceMgr.validateNonce(nonce, created);
/*      */   }
/*      */ 
/*      */   
/*      */   private Class loadClass(String classname) throws XWSSecurityException {
/* 1746 */     if (classname == null) {
/* 1747 */       return null;
/*      */     }
/* 1749 */     Class<?> ret = null;
/* 1750 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 1751 */     if (loader != null) {
/*      */       try {
/* 1753 */         ret = loader.loadClass(classname);
/* 1754 */         return ret;
/* 1755 */       } catch (ClassNotFoundException e) {
/*      */         
/* 1757 */         if (log.isLoggable(Level.FINE)) {
/* 1758 */           log.log(Level.FINE, "LoadClass: could not load class " + classname, e);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1763 */     loader = getClass().getClassLoader();
/*      */     try {
/* 1765 */       ret = loader.loadClass(classname);
/* 1766 */       return ret;
/* 1767 */     } catch (ClassNotFoundException e) {
/*      */       
/* 1769 */       if (log.isLoggable(Level.FINE)) {
/* 1770 */         log.log(Level.FINE, "LoadClass: could not load class " + classname, e);
/*      */       }
/*      */       
/* 1773 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1521_ERROR_GETTING_USER_CLASS());
/* 1774 */       throw new XWSSecurityException("Could not find User Class " + classname);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\DefaultSecurityEnvironmentImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */