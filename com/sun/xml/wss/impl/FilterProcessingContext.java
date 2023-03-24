/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterProcessingContext
/*     */   extends ProcessingContextImpl
/*     */ {
/*     */   public static final int ADHOC = 0;
/*     */   public static final int POSTHOC = 1;
/*     */   public static final int DEFAULT = 2;
/*     */   public static final int WSDL_POLICY = 3;
/*  96 */   private byte[] digestValue = null;
/*  97 */   private byte[] canonicalizedData = null;
/*     */ 
/*     */ 
/*     */   
/* 101 */   private int mode = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean primaryPolicyViolation = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean optionalPolicyViolation = false;
/*     */ 
/*     */ 
/*     */   
/* 114 */   private Throwable _PolicyViolation = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enableDynamicPolicyCallback = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   private HashMap tokenCache = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */   
/* 135 */   private HashMap encryptedKeyCache = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */   
/* 139 */   private HashMap insertedX509Cache = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean timestampExported = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   private HashMap elementCache = new HashMap<Object, Object>();
/*     */   
/*     */   private AuthenticationTokenPolicy.X509CertificateBinding x509CertificateBinding;
/*     */   private AuthenticationTokenPolicy.KerberosTokenBinding kerberosTokenBinding;
/*     */   private AuthenticationTokenPolicy.UsernameTokenBinding usernameTokenBinding;
/* 156 */   private WSSPolicy inferredPolicy = null;
/*     */   
/*     */   private SymmetricKeyBinding symmetricKeyBinding;
/* 159 */   private String dataEncAlgo = null;
/* 160 */   private SecretKey currentSecret = null;
/*     */   
/* 162 */   private Node currentRefList = null;
/* 163 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/* 166 */   private HashMap strTransformCache = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterProcessingContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FilterProcessingContext(ProcessingContext context) throws XWSSecurityException {
/* 178 */     copy(this, context);
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
/*     */   public FilterProcessingContext(int filterMode, String messageIdentifier, SecurityPolicy securityPolicy, SOAPMessage message) throws XWSSecurityException {
/* 195 */     this.mode = filterMode;
/* 196 */     setSecurityPolicy(securityPolicy);
/* 197 */     setMessageIdentifier(messageIdentifier);
/* 198 */     setSOAPMessage(message);
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
/*     */   public void setSecurityPolicy(SecurityPolicy policy) throws XWSSecurityException {
/* 212 */     this.primaryPolicyViolation = false;
/* 213 */     this.optionalPolicyViolation = false;
/*     */     
/* 215 */     this._PolicyViolation = null;
/*     */     
/* 217 */     if (!(policy instanceof WSSPolicy) && !PolicyTypeUtil.messagePolicy(policy) && !PolicyTypeUtil.applicationSecurityConfiguration(policy) && !PolicyTypeUtil.dynamicSecurityPolicy(policy)) {
/*     */       
/* 219 */       log.log(Level.SEVERE, "WSS0801.illegal.securitypolicy");
/* 220 */       throw new XWSSecurityException("Illegal SecurityPolicy Type: required one of  WSSPolicy/MessagePolicy/ApplicationSecurityConfiguration");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 225 */     super.setSecurityPolicy(policy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPVE(Throwable exception) {
/* 232 */     this._PolicyViolation = exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getPVE() {
/* 239 */     return this._PolicyViolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(int mode) {
/* 246 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 253 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableDynamicPolicyCallback(boolean enable) {
/* 260 */     this.enableDynamicPolicyCallback = enable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean makeDynamicPolicyCallback() {
/* 267 */     return this.enableDynamicPolicyCallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isPrimaryPolicyViolation(boolean assrt) {
/* 274 */     this.primaryPolicyViolation = assrt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrimaryPolicyViolation() {
/* 281 */     return this.primaryPolicyViolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isOptionalPolicyViolation(boolean assrt) {
/* 288 */     this.optionalPolicyViolation = assrt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptionalPolicyViolation() {
/* 295 */     return this.optionalPolicyViolation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap getTokenCache() {
/* 302 */     return this.tokenCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap getEncryptedKeyCache() {
/* 309 */     return this.encryptedKeyCache;
/*     */   }
/*     */   
/*     */   public HashMap getInsertedX509Cache() {
/* 313 */     return this.insertedX509Cache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void timestampExported(boolean flag) {
/* 320 */     this.timestampExported = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean timestampExported() {
/* 327 */     return this.timestampExported;
/*     */   }
/*     */   
/*     */   public HashMap getElementCache() {
/* 331 */     return this.elementCache;
/*     */   }
/*     */   
/*     */   public HashMap getSTRTransformCache() {
/* 335 */     return this.strTransformCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setX509CertificateBinding(AuthenticationTokenPolicy.X509CertificateBinding x509CertificateBinding) {
/* 340 */     this.x509CertificateBinding = x509CertificateBinding;
/*     */   }
/*     */   
/*     */   public AuthenticationTokenPolicy.X509CertificateBinding getX509CertificateBinding() {
/* 344 */     return this.x509CertificateBinding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUsernameTokenBinding(AuthenticationTokenPolicy.UsernameTokenBinding untBinding) {
/* 349 */     this.usernameTokenBinding = untBinding;
/*     */   }
/*     */   
/*     */   public AuthenticationTokenPolicy.UsernameTokenBinding getusernameTokenBinding() {
/* 353 */     return this.usernameTokenBinding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKerberosTokenBinding(AuthenticationTokenPolicy.KerberosTokenBinding kerberosTokenBinding) {
/* 358 */     this.kerberosTokenBinding = kerberosTokenBinding;
/*     */   }
/*     */   
/*     */   public AuthenticationTokenPolicy.KerberosTokenBinding getKerberosTokenBinding() {
/* 362 */     return this.kerberosTokenBinding;
/*     */   }
/*     */   
/*     */   public void setSymmetricKeyBinding(SymmetricKeyBinding symmetricKeyBinding) {
/* 366 */     this.symmetricKeyBinding = symmetricKeyBinding;
/*     */   }
/*     */   
/*     */   public SymmetricKeyBinding getSymmetricKeyBinding() {
/* 370 */     return this.symmetricKeyBinding;
/*     */   }
/*     */   
/*     */   public void setDataEncryptionAlgorithm(String alg) {
/* 374 */     this.dataEncAlgo = alg;
/*     */   }
/*     */   
/*     */   public String getDataEncryptionAlgorithm() {
/* 378 */     return this.dataEncAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurableSoapMessage getSecurableSoapMessage() {
/* 383 */     return this.secureMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 388 */     this.elementCache.clear();
/* 389 */     this.tokenCache.clear();
/*     */   }
/*     */   
/*     */   public WSSPolicy getInferredPolicy() {
/* 393 */     return this.inferredPolicy;
/*     */   }
/*     */   
/*     */   public void setInferredPolicy(WSSPolicy policy) {
/* 397 */     this.inferredPolicy = policy;
/*     */   }
/*     */   
/*     */   public byte[] getDigestValue() {
/* 401 */     return this.digestValue;
/*     */   }
/*     */   
/*     */   public void setDigestValue(byte[] digestValue) {
/* 405 */     this.digestValue = digestValue;
/*     */   }
/*     */   
/*     */   public byte[] getCanonicalizedData() {
/* 409 */     return this.canonicalizedData;
/*     */   }
/*     */   
/*     */   public void setCanonicalizedData(byte[] canonicalizedData) {
/* 413 */     this.canonicalizedData = canonicalizedData;
/*     */   }
/*     */   
/*     */   public void setCurrentSecret(Key secret) {
/* 417 */     this.currentSecret = (SecretKey)secret;
/*     */   }
/*     */   
/*     */   public SecretKey getCurrentSecret() {
/* 421 */     return this.currentSecret;
/*     */   }
/*     */   
/*     */   public Node getCurrentRefList() {
/* 425 */     return this.currentRefList;
/*     */   }
/*     */   
/*     */   public void setCurrentReferenceList(Node blk) {
/* 429 */     this.currentRefList = blk;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\FilterProcessingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */