/*     */ package javax.xml.soap;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FactoryFinder
/*     */ {
/*     */   private static Object newInstance(String className, ClassLoader classLoader, String defaultFactoryClass) throws SOAPException {
/*     */     try {
/*  44 */       Class spiClass = safeLoadClass(className, classLoader, defaultFactoryClass);
/*  45 */       return spiClass.newInstance();
/*  46 */     } catch (ClassNotFoundException x) {
/*  47 */       throw new SOAPException("Provider " + className + " not found", x);
/*     */     }
/*  49 */     catch (Exception x) {
/*  50 */       throw new SOAPException("Provider " + className + " could not be instantiated: " + x, x);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object find(String factoryId) throws SOAPException {
/*  72 */     return find(factoryId, null, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object find(String factoryId, String fallbackClassName) throws SOAPException {
/*  98 */     return find(factoryId, fallbackClassName, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object find(String factoryId, String defaultClassName, boolean tryFallback) throws SOAPException {
/*     */     ClassLoader classLoader;
/*     */     try {
/* 129 */       classLoader = Thread.currentThread().getContextClassLoader();
/* 130 */     } catch (Exception x) {
/* 131 */       throw new SOAPException(x.toString(), x);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 136 */       String systemProp = System.getProperty(factoryId);
/*     */       
/* 138 */       if (systemProp != null) {
/* 139 */         return newInstance(systemProp, classLoader, defaultClassName);
/*     */       }
/* 141 */     } catch (SecurityException se) {}
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 146 */       String javah = System.getProperty("java.home");
/* 147 */       String configFile = javah + File.separator + "lib" + File.separator + "jaxm.properties";
/*     */       
/* 149 */       File f = new File(configFile);
/* 150 */       if (f.exists()) {
/* 151 */         Properties props = new Properties();
/* 152 */         props.load(new FileInputStream(f));
/* 153 */         String factoryClassName = props.getProperty(factoryId);
/* 154 */         return newInstance(factoryClassName, classLoader, defaultClassName);
/*     */       } 
/* 156 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 159 */     String serviceId = "META-INF/services/" + factoryId;
/*     */     
/*     */     try {
/* 162 */       InputStream is = null;
/* 163 */       if (classLoader == null) {
/* 164 */         is = ClassLoader.getSystemResourceAsStream(serviceId);
/*     */       } else {
/* 166 */         is = classLoader.getResourceAsStream(serviceId);
/*     */       } 
/*     */       
/* 169 */       if (is != null) {
/* 170 */         BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*     */ 
/*     */         
/* 173 */         String factoryClassName = rd.readLine();
/* 174 */         rd.close();
/*     */         
/* 176 */         if (factoryClassName != null && !"".equals(factoryClassName))
/*     */         {
/* 178 */           return newInstance(factoryClassName, classLoader, defaultClassName);
/*     */         }
/*     */       } 
/* 181 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/* 185 */     if (!tryFallback) {
/* 186 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 190 */     if (defaultClassName == null) {
/* 191 */       throw new SOAPException("Provider for " + factoryId + " cannot be found", null);
/*     */     }
/*     */     
/* 194 */     return newInstance(defaultClassName, classLoader, defaultClassName);
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
/*     */   private static Class safeLoadClass(String className, ClassLoader classLoader, String defaultFactoryClass) throws ClassNotFoundException {
/*     */     try {
/* 208 */       SecurityManager s = System.getSecurityManager();
/* 209 */       if (s != null) {
/* 210 */         int i = className.lastIndexOf('.');
/* 211 */         if (i != -1) {
/* 212 */           s.checkPackageAccess(className.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 216 */       if (classLoader == null) {
/* 217 */         return Class.forName(className);
/*     */       }
/* 219 */       return classLoader.loadClass(className);
/* 220 */     } catch (SecurityException se) {
/*     */ 
/*     */ 
/*     */       
/* 224 */       if (className.equals(defaultFactoryClass))
/* 225 */         return Class.forName(className); 
/* 226 */       throw se;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\FactoryFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */