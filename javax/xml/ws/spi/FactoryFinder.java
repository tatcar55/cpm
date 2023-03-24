/*     */ package javax.xml.ws.spi;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private static final String OSGI_SERVICE_LOADER_CLASS_NAME = "org.glassfish.hk2.osgiresourcelocator.ServiceLoader";
/*     */   
/*     */   private static Object newInstance(String className, ClassLoader classLoader) {
/*     */     try {
/*  65 */       Class spiClass = safeLoadClass(className, classLoader);
/*  66 */       return spiClass.newInstance();
/*  67 */     } catch (ClassNotFoundException x) {
/*  68 */       throw new WebServiceException("Provider " + className + " not found", x);
/*     */     }
/*  70 */     catch (Exception x) {
/*  71 */       throw new WebServiceException("Provider " + className + " could not be instantiated: " + x, x);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object find(String factoryId, String fallbackClassName) {
/*     */     ClassLoader classLoader;
/*  99 */     if (isOsgi()) {
/* 100 */       return lookupUsingOSGiServiceLoader(factoryId);
/*     */     }
/*     */     
/*     */     try {
/* 104 */       classLoader = Thread.currentThread().getContextClassLoader();
/* 105 */     } catch (Exception x) {
/* 106 */       throw new WebServiceException(x.toString(), x);
/*     */     } 
/*     */     
/* 109 */     String serviceId = "META-INF/services/" + factoryId;
/*     */     
/*     */     try {
/*     */       InputStream is;
/* 113 */       if (classLoader == null) {
/* 114 */         is = ClassLoader.getSystemResourceAsStream(serviceId);
/*     */       } else {
/* 116 */         is = classLoader.getResourceAsStream(serviceId);
/*     */       } 
/*     */       
/* 119 */       if (is != null) {
/* 120 */         BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*     */ 
/*     */         
/* 123 */         String factoryClassName = rd.readLine();
/* 124 */         rd.close();
/*     */         
/* 126 */         if (factoryClassName != null && !"".equals(factoryClassName))
/*     */         {
/* 128 */           return newInstance(factoryClassName, classLoader);
/*     */         }
/*     */       } 
/* 131 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 137 */       String javah = System.getProperty("java.home");
/* 138 */       String configFile = javah + File.separator + "lib" + File.separator + "jaxws.properties";
/*     */       
/* 140 */       File f = new File(configFile);
/* 141 */       if (f.exists()) {
/* 142 */         Properties props = new Properties();
/* 143 */         props.load(new FileInputStream(f));
/* 144 */         String factoryClassName = props.getProperty(factoryId);
/* 145 */         return newInstance(factoryClassName, classLoader);
/*     */       } 
/* 147 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 153 */       String systemProp = System.getProperty(factoryId);
/*     */       
/* 155 */       if (systemProp != null) {
/* 156 */         return newInstance(systemProp, classLoader);
/*     */       }
/* 158 */     } catch (SecurityException se) {}
/*     */ 
/*     */     
/* 161 */     if (fallbackClassName == null) {
/* 162 */       throw new WebServiceException("Provider for " + factoryId + " cannot be found", null);
/*     */     }
/*     */ 
/*     */     
/* 166 */     return newInstance(fallbackClassName, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class safeLoadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
/*     */     try {
/* 176 */       SecurityManager s = System.getSecurityManager();
/* 177 */       if (s != null) {
/* 178 */         int i = className.lastIndexOf('.');
/* 179 */         if (i != -1) {
/* 180 */           s.checkPackageAccess(className.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 184 */       if (classLoader == null) {
/* 185 */         return Class.forName(className);
/*     */       }
/* 187 */       return classLoader.loadClass(className);
/* 188 */     } catch (SecurityException se) {
/*     */       
/* 190 */       if ("com.sun.xml.internal.ws.spi.ProviderImpl".equals(className))
/* 191 */         return Class.forName(className); 
/* 192 */       throw se;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isOsgi() {
/*     */     try {
/* 200 */       Class.forName("org.glassfish.hk2.osgiresourcelocator.ServiceLoader");
/* 201 */       return true;
/* 202 */     } catch (ClassNotFoundException cnfe) {
/*     */       
/* 204 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object lookupUsingOSGiServiceLoader(String factoryId) {
/*     */     try {
/* 210 */       Class<?> serviceClass = Class.forName(factoryId);
/* 211 */       Class[] args = { serviceClass };
/* 212 */       Class<?> target = Class.forName("org.glassfish.hk2.osgiresourcelocator.ServiceLoader");
/* 213 */       Method m = target.getMethod("lookupProviderInstances", new Class[] { Class.class });
/* 214 */       Iterator iter = ((Iterable)m.invoke(null, (Object[])args)).iterator();
/* 215 */       return iter.hasNext() ? iter.next() : null;
/* 216 */     } catch (Exception e) {
/*     */       
/* 218 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\spi\FactoryFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */