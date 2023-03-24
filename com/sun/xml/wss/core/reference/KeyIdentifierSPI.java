/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import java.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyIdentifierSPI
/*     */ {
/*  47 */   public static final String vmVendor = System.getProperty("java.vendor.url");
/*     */   public static final String sunVmVendor = "http://java.sun.com/";
/*     */   public static final String ibmVmVendor = "http://www.ibm.com/";
/*  50 */   public static final boolean isSunVM = "http://java.sun.com/".equals(vmVendor);
/*  51 */   public static final boolean isIBMVM = "http://www.ibm.com/".equals(vmVendor);
/*     */   
/*     */   private static final String sunKeyIdentifierSPIClass = "com.sun.wsit.security.SunKeyIdentifierSPI";
/*     */   
/*     */   private static final String ibmKeyIdentifierSPIClass = "com.sun.wsit.security.IBMKeyIdentifierSPI";
/*     */   
/*     */   private static final String sunKeyIdentifierImplClass = "sun.security.x509.KeyIdentifier";
/*     */   
/*     */   private static final String ibmKeyIdentifierImplClass = "com.ibm.security.x509.KeyIdentifier";
/*     */   protected static final String SUBJECT_KEY_IDENTIFIER_OID = "2.5.29.14";
/*     */   private static final KeyIdentifierSPI instance;
/*     */   
/*     */   static {
/*  64 */     if (isSunVM) {
/*  65 */       instance = loadClass("com.sun.wsit.security.SunKeyIdentifierSPI");
/*  66 */     } else if (isIBMVM) {
/*  67 */       instance = loadClass("com.sun.wsit.security.IBMKeyIdentifierSPI");
/*     */     }
/*  69 */     else if (testClassExist("sun.security.x509.KeyIdentifier")) {
/*  70 */       instance = loadClass("com.sun.wsit.security.SunKeyIdentifierSPI");
/*  71 */     } else if (testClassExist("com.ibm.security.x509.KeyIdentifier")) {
/*  72 */       instance = loadClass("com.sun.wsit.security.IBMKeyIdentifierSPI");
/*     */     } else {
/*  74 */       throw new UnsupportedOperationException("KeyIdentifierSPI Error : No known implementation for VM: " + vmVendor);
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
/*     */   public static KeyIdentifierSPI getInstance() {
/*  88 */     return instance;
/*     */   }
/*     */   
/*     */   private static boolean testClassExist(String className) {
/*     */     try {
/*  93 */       Class<?> spiClass = null;
/*  94 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  95 */       if (classLoader != null) {
/*  96 */         spiClass = classLoader.loadClass(className);
/*     */       }
/*  98 */       if (spiClass == null) {
/*  99 */         spiClass = Class.forName(className);
/*     */       }
/* 101 */       return (spiClass != null);
/* 102 */     } catch (ClassNotFoundException x) {
/* 103 */       return false;
/* 104 */     } catch (Exception x) {
/* 105 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static KeyIdentifierSPI loadClass(String className) {
/*     */     try {
/* 111 */       Class<?> spiClass = null;
/* 112 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 113 */       if (classLoader != null) {
/* 114 */         spiClass = classLoader.loadClass(className);
/*     */       }
/* 116 */       if (spiClass == null) {
/* 117 */         spiClass = Class.forName(className);
/*     */       }
/* 119 */       return (KeyIdentifierSPI)spiClass.newInstance();
/* 120 */     } catch (ClassNotFoundException x) {
/* 121 */       throw new RuntimeException("The KeyIdentifierSPI class: " + className + " specified was not found", x);
/*     */     }
/* 123 */     catch (Exception x) {
/* 124 */       throw new RuntimeException("The KeyIdentifierSPI class: " + className + " could not be instantiated ", x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract byte[] getSubjectKeyIdentifier(X509Certificate paramX509Certificate) throws KeyIdentifierSPIException;
/*     */   
/*     */   protected static final class KeyIdentifierSPIException
/*     */     extends Exception
/*     */   {
/*     */     public KeyIdentifierSPIException(Exception ex) {
/* 135 */       initCause(ex);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\KeyIdentifierSPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */