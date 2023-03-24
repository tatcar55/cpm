/*     */ package com.sun.xml.wss.util;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.KeyStore;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XWSSUtil
/*     */ {
/*  91 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static X509Certificate getCertificateFromTrustStore(byte[] ski, KeyStore trustStore) throws IOException {
/*     */     try {
/* 105 */       Enumeration<String> aliases = trustStore.aliases();
/* 106 */       while (aliases.hasMoreElements()) {
/* 107 */         String alias = aliases.nextElement();
/* 108 */         Certificate cert = trustStore.getCertificate(alias);
/* 109 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*     */           continue;
/*     */         }
/* 112 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 113 */         byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/* 114 */         if (keyId == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 118 */         if (Arrays.equals(ski, keyId)) {
/* 119 */           return x509Cert;
/*     */         }
/*     */       } 
/* 122 */     } catch (Exception e) {
/* 123 */       throw new IOException(e.getMessage());
/*     */     } 
/* 125 */     return null;
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
/*     */   public static X509Certificate getCertificateFromTrustStore(String issuerName, BigInteger serialNumber, KeyStore trustStore) throws IOException {
/*     */     try {
/* 143 */       Enumeration<String> aliases = trustStore.aliases();
/* 144 */       while (aliases.hasMoreElements()) {
/* 145 */         String alias = aliases.nextElement();
/* 146 */         Certificate cert = trustStore.getCertificate(alias);
/* 147 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*     */           continue;
/*     */         }
/* 150 */         X509Certificate x509Cert = (X509Certificate)cert;
/*     */ 
/*     */         
/* 153 */         X500Principal thisIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 154 */         X500Principal issuerPrincipal = new X500Principal(issuerName);
/*     */         
/* 156 */         BigInteger thisSerialNumber = x509Cert.getSerialNumber();
/*     */         
/* 158 */         if (thisIssuerPrincipal.equals(issuerPrincipal) && thisSerialNumber.equals(serialNumber))
/*     */         {
/* 160 */           return x509Cert;
/*     */         }
/*     */       } 
/* 163 */     } catch (Exception e) {
/* 164 */       throw new IOException(e.getMessage());
/*     */     } 
/* 166 */     return null;
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
/*     */   public static PrivateKey getPrivateKey(byte[] ski, KeyStore keyStore, String keyStorePassword) throws IOException {
/*     */     try {
/* 180 */       Enumeration<String> aliases = keyStore.aliases();
/* 181 */       while (aliases.hasMoreElements()) {
/* 182 */         String alias = aliases.nextElement();
/* 183 */         if (!keyStore.isKeyEntry(alias))
/*     */           continue; 
/* 185 */         Certificate cert = keyStore.getCertificate(alias);
/* 186 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*     */           continue;
/*     */         }
/* 189 */         X509Certificate x509Cert = (X509Certificate)cert;
/* 190 */         byte[] keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(x509Cert);
/* 191 */         if (keyId == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 195 */         if (Arrays.equals(ski, keyId))
/*     */         {
/* 197 */           return (PrivateKey)keyStore.getKey(alias, keyStorePassword.toCharArray());
/*     */         }
/*     */       } 
/* 200 */     } catch (Exception e) {
/* 201 */       throw new IOException(e.getMessage());
/*     */     } 
/* 203 */     return null;
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
/*     */   
/*     */   public static PrivateKey getPrivateKey(String issuerName, BigInteger serialNumber, KeyStore keyStore, String keyStorePassword) throws IOException {
/*     */     try {
/* 222 */       Enumeration<String> aliases = keyStore.aliases();
/* 223 */       while (aliases.hasMoreElements()) {
/* 224 */         String alias = aliases.nextElement();
/* 225 */         if (!keyStore.isKeyEntry(alias))
/*     */           continue; 
/* 227 */         Certificate cert = keyStore.getCertificate(alias);
/* 228 */         if (cert == null || !"X.509".equals(cert.getType())) {
/*     */           continue;
/*     */         }
/* 231 */         X509Certificate x509Cert = (X509Certificate)cert;
/*     */         
/* 233 */         X500Principal thisIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 234 */         X500Principal issuerPrincipal = new X500Principal(issuerName);
/*     */         
/* 236 */         BigInteger thisSerialNumber = x509Cert.getSerialNumber();
/*     */         
/* 238 */         if (thisIssuerPrincipal.equals(issuerPrincipal) && thisSerialNumber.equals(serialNumber))
/*     */         {
/* 240 */           return (PrivateKey)keyStore.getKey(alias, keyStorePassword.toCharArray());
/*     */         }
/*     */       } 
/* 243 */     } catch (Exception e) {
/* 244 */       throw new IOException(e.getMessage());
/*     */     } 
/* 246 */     return null;
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
/*     */   public static PrivateKey getPrivateKey(X509Certificate certificate, KeyStore keyStore, String keyStorePassword) throws IOException {
/*     */     try {
/* 262 */       Enumeration<String> aliases = keyStore.aliases();
/* 263 */       while (aliases.hasMoreElements()) {
/* 264 */         String alias = aliases.nextElement();
/* 265 */         if (!keyStore.isKeyEntry(alias))
/*     */           continue; 
/* 267 */         Certificate cert = keyStore.getCertificate(alias);
/* 268 */         if (cert != null && cert.equals(certificate))
/* 269 */           return (PrivateKey)keyStore.getKey(alias, keyStorePassword.toCharArray()); 
/*     */       } 
/* 271 */     } catch (Exception e) {
/* 272 */       throw new IOException(e.getMessage());
/*     */     } 
/* 274 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey generateSymmetricKey(String algorithm) throws XWSSecurityException {
/* 284 */     return SecurityUtil.generateSymmetricKey(algorithm);
/*     */   }
/*     */   
/*     */   public static X509Certificate matchesProgrammaticInfo(Object obj, byte[] keyIdentifier, String valueType) {
/* 288 */     if (obj == null) {
/* 289 */       return null;
/*     */     }
/* 291 */     if (obj instanceof X509Certificate) {
/*     */       try {
/* 293 */         X509Certificate cert = (X509Certificate)obj;
/* 294 */         byte[] keyId = null;
/* 295 */         if ("Identifier".equals(valueType)) {
/* 296 */           keyId = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 297 */         } else if ("Thumbprint".equals(valueType)) {
/* 298 */           keyId = getThumbprintIdentifier(cert);
/*     */         } 
/* 300 */         if (keyId != null && 
/* 301 */           Arrays.equals(keyIdentifier, keyId)) {
/* 302 */           return cert;
/*     */         }
/*     */       }
/* 305 */       catch (XWSSecurityException ex) {
/* 306 */         log.log(Level.SEVERE, (String)null, (Throwable)ex);
/* 307 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */     }
/* 310 */     return null;
/*     */   }
/*     */   
/*     */   public static X509Certificate matchesProgrammaticInfo(Object obj, PublicKey publicKey) {
/* 314 */     if (obj == null) {
/* 315 */       return null;
/*     */     }
/* 317 */     if (obj instanceof X509Certificate) {
/* 318 */       X509Certificate cert = (X509Certificate)obj;
/* 319 */       if (cert.getPublicKey().equals(publicKey)) {
/* 320 */         return cert;
/*     */       }
/*     */     } 
/* 323 */     return null;
/*     */   }
/*     */   
/*     */   public static X509Certificate matchesProgrammaticInfo(Object obj, BigInteger serialNumber, String issuerName) {
/* 327 */     if (obj == null) {
/* 328 */       return null;
/*     */     }
/* 330 */     if (obj instanceof X509Certificate) {
/* 331 */       X509Certificate cert = (X509Certificate)obj;
/* 332 */       if (cert.getSerialNumber().equals(serialNumber)) {
/*     */         
/* 334 */         X500Principal thisIssuerPrincipal = cert.getIssuerX500Principal();
/* 335 */         X500Principal issuerPrincipal = new X500Principal(issuerName);
/* 336 */         if (thisIssuerPrincipal.equals(issuerPrincipal)) {
/* 337 */           return cert;
/*     */         }
/*     */       } 
/*     */     } 
/* 341 */     return null;
/*     */   }
/*     */   
/*     */   public static PrivateKey getProgrammaticPrivateKey(Map context) {
/* 345 */     if (context == null) {
/* 346 */       return null;
/*     */     }
/* 348 */     Object obj = context.get("privatekey");
/* 349 */     if (obj instanceof PrivateKey) {
/* 350 */       return (PrivateKey)obj;
/*     */     }
/* 352 */     if (obj != null) {
/* 353 */       log.log(Level.SEVERE, "value of PRIVATEKEY_PROPERTY is not a PrivateKey");
/* 354 */       throw new XWSSecurityRuntimeException("value of PRIVATEKEY_PROPERTY is not a PrivateKey");
/*     */     } 
/*     */     
/* 357 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] getThumbprintIdentifier(X509Certificate cert) throws XWSSecurityException {
/* 362 */     byte[] thumbPrintIdentifier = null;
/*     */     
/*     */     try {
/* 365 */       thumbPrintIdentifier = MessageDigest.getInstance("SHA-1").digest(cert.getEncoded());
/* 366 */     } catch (NoSuchAlgorithmException ex) {
/* 367 */       log.log(Level.SEVERE, "WSS0708.no.digest.algorithm");
/* 368 */       throw new XWSSecurityException("Digest algorithm SHA-1 not found");
/* 369 */     } catch (CertificateEncodingException ex) {
/* 370 */       log.log(Level.SEVERE, "WSS0709.error.getting.rawContent");
/* 371 */       throw new XWSSecurityException("Error while getting certificate's raw content");
/*     */     } 
/* 373 */     return thumbPrintIdentifier;
/*     */   }
/*     */   
/*     */   public static CertSelector getCertSelector(Class<?> certSelectorClass, Map context) {
/* 377 */     CertSelector selector = null;
/* 378 */     if (certSelectorClass != null) {
/* 379 */       Constructor<?> ctor = null;
/*     */       try {
/* 381 */         ctor = certSelectorClass.getConstructor(new Class[] { Map.class });
/* 382 */       } catch (SecurityException ex) {
/*     */       
/* 384 */       } catch (NoSuchMethodException ex) {}
/*     */ 
/*     */       
/* 387 */       if (ctor != null) {
/*     */         try {
/* 389 */           selector = (CertSelector)ctor.newInstance(new Object[] { context });
/* 390 */           return selector;
/* 391 */         } catch (IllegalArgumentException ex) {
/* 392 */           log.log(Level.SEVERE, "WSS0812.exception.instantiating.certselector", ex);
/* 393 */           throw new RuntimeException(ex);
/* 394 */         } catch (InstantiationException ex) {
/* 395 */           log.log(Level.SEVERE, "WSS0812.exception.instantiating.certselector", ex);
/* 396 */           throw new RuntimeException(ex);
/* 397 */         } catch (InvocationTargetException ex) {
/* 398 */           log.log(Level.SEVERE, "WSS0812.exception.instantiating.certselector", ex);
/* 399 */           throw new RuntimeException(ex);
/* 400 */         } catch (IllegalAccessException ex) {
/* 401 */           log.log(Level.SEVERE, "WSS0812.exception.instantiating.certselector", ex);
/* 402 */           throw new RuntimeException(ex);
/*     */         } 
/*     */       }
/*     */       try {
/* 406 */         selector = (CertSelector)certSelectorClass.newInstance();
/* 407 */         return selector;
/* 408 */       } catch (InstantiationException ex) {
/* 409 */         log.log(Level.SEVERE, "WSS0812.exception.instantiating.certselector", ex);
/* 410 */         throw new RuntimeException(ex);
/* 411 */       } catch (IllegalAccessException ex) {
/* 412 */         log.log(Level.SEVERE, "WSS0812.exception.instantiating.certselector", ex);
/* 413 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     } 
/*     */     
/* 417 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\\util\XWSSUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */