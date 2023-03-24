/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.ConsoleHandler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContextFinder
/*     */ {
/*  75 */   private static final Logger logger = Logger.getLogger("javax.xml.bind"); static {
/*     */     try {
/*  77 */       if (AccessController.doPrivileged(new GetPropertyAction("jaxb.debug")) != null)
/*     */       {
/*     */         
/*  80 */         logger.setUseParentHandlers(false);
/*  81 */         logger.setLevel(Level.ALL);
/*  82 */         ConsoleHandler handler = new ConsoleHandler();
/*  83 */         handler.setLevel(Level.ALL);
/*  84 */         logger.addHandler(handler);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*  90 */     catch (Throwable t) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PLATFORM_DEFAULT_FACTORY_CLASS = "com.sun.xml.internal.bind.v2.ContextFactory";
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleInvocationTargetException(InvocationTargetException x) throws JAXBException {
/* 101 */     Throwable t = x.getTargetException();
/* 102 */     if (t != null) {
/* 103 */       if (t instanceof JAXBException)
/*     */       {
/* 105 */         throw (JAXBException)t; } 
/* 106 */       if (t instanceof RuntimeException)
/*     */       {
/* 108 */         throw (RuntimeException)t; } 
/* 109 */       if (t instanceof Error) {
/* 110 */         throw (Error)t;
/*     */       }
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
/*     */   private static JAXBException handleClassCastException(Class originalType, Class targetType) {
/* 127 */     URL targetTypeURL = which(targetType);
/*     */     
/* 129 */     return new JAXBException(Messages.format("JAXBContext.IllegalCast", getClassClassLoader(originalType).getResource("javax/xml/bind/JAXBContext.class"), targetTypeURL));
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
/*     */   static JAXBContext newInstance(String contextPath, String className, ClassLoader classLoader, Map properties) throws JAXBException {
/*     */     try {
/* 145 */       Class spFactory = safeLoadClass(className, classLoader);
/* 146 */       return newInstance(contextPath, spFactory, classLoader, properties);
/* 147 */     } catch (ClassNotFoundException x) {
/* 148 */       throw new JAXBException(Messages.format("ContextFinder.ProviderNotFound", className), x);
/*     */     
/*     */     }
/* 151 */     catch (RuntimeException x) {
/*     */ 
/*     */       
/* 154 */       throw x;
/* 155 */     } catch (Exception x) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       throw new JAXBException(Messages.format("ContextFinder.CouldNotInstantiate", className, x), x);
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
/*     */   static JAXBContext newInstance(String contextPath, Class spFactory, ClassLoader classLoader, Map properties) throws JAXBException {
/*     */     try {
/* 179 */       Object context = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 184 */         Method m = spFactory.getMethod("createContext", new Class[] { String.class, ClassLoader.class, Map.class });
/*     */         
/* 186 */         context = m.invoke(null, new Object[] { contextPath, classLoader, properties });
/* 187 */       } catch (NoSuchMethodException e) {}
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (context == null) {
/*     */ 
/*     */         
/* 194 */         Method m = spFactory.getMethod("createContext", new Class[] { String.class, ClassLoader.class });
/*     */         
/* 196 */         context = m.invoke(null, new Object[] { contextPath, classLoader });
/*     */       } 
/*     */       
/* 199 */       if (!(context instanceof JAXBContext))
/*     */       {
/* 201 */         throw handleClassCastException(context.getClass(), JAXBContext.class);
/*     */       }
/* 203 */       return (JAXBContext)context;
/* 204 */     } catch (InvocationTargetException x) {
/* 205 */       handleInvocationTargetException(x);
/*     */ 
/*     */       
/* 208 */       Throwable e = x;
/* 209 */       if (x.getTargetException() != null) {
/* 210 */         e = x.getTargetException();
/*     */       }
/* 212 */       throw new JAXBException(Messages.format("ContextFinder.CouldNotInstantiate", spFactory, e), e);
/* 213 */     } catch (RuntimeException x) {
/*     */ 
/*     */       
/* 216 */       throw x;
/* 217 */     } catch (Exception x) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       throw new JAXBException(Messages.format("ContextFinder.CouldNotInstantiate", spFactory, x), x);
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
/*     */   static JAXBContext newInstance(Class[] classes, Map properties, String className) throws JAXBException {
/*     */     Class spi;
/* 236 */     ClassLoader cl = getContextClassLoader();
/*     */     
/*     */     try {
/* 239 */       spi = safeLoadClass(className, cl);
/* 240 */     } catch (ClassNotFoundException e) {
/* 241 */       throw new JAXBException(e);
/*     */     } 
/*     */     
/* 244 */     if (logger.isLoggable(Level.FINE))
/*     */     {
/* 246 */       logger.log(Level.FINE, "loaded {0} from {1}", new Object[] { className, which(spi) });
/*     */     }
/*     */     
/* 249 */     return newInstance(classes, properties, spi);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static JAXBContext newInstance(Class[] classes, Map properties, Class spFactory) throws JAXBException {
/*     */     Method m;
/*     */     try {
/* 257 */       m = spFactory.getMethod("createContext", new Class[] { Class[].class, Map.class });
/* 258 */     } catch (NoSuchMethodException e) {
/* 259 */       throw new JAXBException(e);
/*     */     } 
/*     */     try {
/* 262 */       Object context = m.invoke(null, new Object[] { classes, properties });
/* 263 */       if (!(context instanceof JAXBContext))
/*     */       {
/* 265 */         throw handleClassCastException(context.getClass(), JAXBContext.class);
/*     */       }
/* 267 */       return (JAXBContext)context;
/* 268 */     } catch (IllegalAccessException e) {
/* 269 */       throw new JAXBException(e);
/* 270 */     } catch (InvocationTargetException e) {
/* 271 */       handleInvocationTargetException(e);
/*     */       
/* 273 */       Throwable x = e;
/* 274 */       if (e.getTargetException() != null) {
/* 275 */         x = e.getTargetException();
/*     */       }
/* 277 */       throw new JAXBException(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JAXBContext find(String factoryId, String contextPath, ClassLoader classLoader, Map properties) throws JAXBException {
/* 285 */     String jaxbContextFQCN = JAXBContext.class.getName();
/*     */ 
/*     */ 
/*     */     
/* 289 */     StringTokenizer packages = new StringTokenizer(contextPath, ":");
/*     */ 
/*     */     
/* 292 */     if (!packages.hasMoreTokens())
/*     */     {
/* 294 */       throw new JAXBException(Messages.format("ContextFinder.NoPackageInContextPath"));
/*     */     }
/*     */     
/* 297 */     logger.fine("Searching jaxb.properties");
/*     */     
/* 299 */     while (packages.hasMoreTokens()) {
/* 300 */       String packageName = packages.nextToken(":").replace('.', '/');
/*     */       
/* 302 */       StringBuilder propFileName = (new StringBuilder()).append(packageName).append("/jaxb.properties");
/*     */       
/* 304 */       Properties props = loadJAXBProperties(classLoader, propFileName.toString());
/* 305 */       if (props != null) {
/* 306 */         if (props.containsKey(factoryId)) {
/* 307 */           String str = props.getProperty(factoryId);
/* 308 */           return newInstance(contextPath, str, classLoader, properties);
/*     */         } 
/* 310 */         throw new JAXBException(Messages.format("ContextFinder.MissingProperty", packageName, factoryId));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 315 */     logger.fine("Searching the system property");
/*     */ 
/*     */     
/* 318 */     String factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction("javax.xml.bind.context.factory"));
/* 319 */     if (factoryClassName != null) {
/* 320 */       return newInstance(contextPath, factoryClassName, classLoader, properties);
/*     */     }
/* 322 */     factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction(jaxbContextFQCN));
/* 323 */     if (factoryClassName != null) {
/* 324 */       return newInstance(contextPath, factoryClassName, classLoader, properties);
/*     */     }
/*     */ 
/*     */     
/* 328 */     if (getContextClassLoader() == classLoader) {
/* 329 */       Class factory = lookupUsingOSGiServiceLoader("javax.xml.bind.JAXBContext");
/* 330 */       if (factory != null) {
/* 331 */         logger.fine("OSGi environment detected");
/* 332 */         return newInstance(contextPath, factory, classLoader, properties);
/*     */       } 
/*     */     } 
/*     */     
/* 336 */     logger.fine("Searching META-INF/services");
/*     */ 
/*     */     
/*     */     try {
/* 340 */       StringBuilder resource = (new StringBuilder()).append("META-INF/services/").append(jaxbContextFQCN);
/* 341 */       InputStream resourceStream = classLoader.getResourceAsStream(resource.toString());
/*     */ 
/*     */       
/* 344 */       if (resourceStream != null) {
/* 345 */         BufferedReader r = new BufferedReader(new InputStreamReader(resourceStream, "UTF-8"));
/* 346 */         factoryClassName = r.readLine().trim();
/* 347 */         r.close();
/* 348 */         return newInstance(contextPath, factoryClassName, classLoader, properties);
/*     */       } 
/* 350 */       logger.log(Level.FINE, "Unable to load:{0}", resource.toString());
/*     */     }
/* 352 */     catch (UnsupportedEncodingException e) {
/*     */       
/* 354 */       throw new JAXBException(e);
/* 355 */     } catch (IOException e) {
/* 356 */       throw new JAXBException(e);
/*     */     } 
/*     */ 
/*     */     
/* 360 */     logger.fine("Trying to create the platform default provider");
/* 361 */     return newInstance(contextPath, "com.sun.xml.internal.bind.v2.ContextFactory", classLoader, properties);
/*     */   }
/*     */ 
/*     */   
/*     */   static JAXBContext find(Class[] classes, Map properties) throws JAXBException {
/* 366 */     String jaxbContextFQCN = JAXBContext.class.getName();
/*     */ 
/*     */ 
/*     */     
/* 370 */     for (Class c : classes) {
/*     */       
/* 372 */       ClassLoader classLoader = getClassClassLoader(c);
/* 373 */       Package pkg = c.getPackage();
/* 374 */       if (pkg != null) {
/*     */         
/* 376 */         String packageName = pkg.getName().replace('.', '/');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 385 */         String resourceName = packageName + "/jaxb.properties";
/* 386 */         logger.log(Level.FINE, "Trying to locate {0}", resourceName);
/* 387 */         Properties props = loadJAXBProperties(classLoader, resourceName);
/* 388 */         if (props == null) {
/* 389 */           logger.fine("  not found");
/*     */         } else {
/* 391 */           logger.fine("  found");
/* 392 */           if (props.containsKey("javax.xml.bind.context.factory")) {
/*     */             
/* 394 */             String str = props.getProperty("javax.xml.bind.context.factory").trim();
/* 395 */             return newInstance(classes, properties, str);
/*     */           } 
/* 397 */           throw new JAXBException(Messages.format("ContextFinder.MissingProperty", packageName, "javax.xml.bind.context.factory"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 403 */     logger.log(Level.FINE, "Checking system property {0}", "javax.xml.bind.context.factory");
/* 404 */     String factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction("javax.xml.bind.context.factory"));
/* 405 */     if (factoryClassName != null) {
/* 406 */       logger.log(Level.FINE, "  found {0}", factoryClassName);
/* 407 */       return newInstance(classes, properties, factoryClassName);
/*     */     } 
/* 409 */     logger.fine("  not found");
/* 410 */     logger.log(Level.FINE, "Checking system property {0}", jaxbContextFQCN);
/* 411 */     factoryClassName = AccessController.<String>doPrivileged(new GetPropertyAction(jaxbContextFQCN));
/* 412 */     if (factoryClassName != null) {
/* 413 */       logger.log(Level.FINE, "  found {0}", factoryClassName);
/* 414 */       return newInstance(classes, properties, factoryClassName);
/*     */     } 
/* 416 */     logger.fine("  not found");
/*     */ 
/*     */ 
/*     */     
/* 420 */     Class factory = lookupUsingOSGiServiceLoader("javax.xml.bind.JAXBContext");
/* 421 */     if (factory != null) {
/* 422 */       logger.fine("OSGi environment detected");
/* 423 */       return newInstance(classes, properties, factory);
/*     */     } 
/*     */ 
/*     */     
/* 427 */     logger.fine("Checking META-INF/services");
/*     */     try {
/*     */       URL resourceURL;
/* 430 */       String resource = "META-INF/services/" + jaxbContextFQCN;
/* 431 */       ClassLoader classLoader = getContextClassLoader();
/*     */       
/* 433 */       if (classLoader == null) {
/* 434 */         resourceURL = ClassLoader.getSystemResource(resource);
/*     */       } else {
/* 436 */         resourceURL = classLoader.getResource(resource);
/*     */       } 
/* 438 */       if (resourceURL != null) {
/* 439 */         logger.log(Level.FINE, "Reading {0}", resourceURL);
/* 440 */         BufferedReader r = new BufferedReader(new InputStreamReader(resourceURL.openStream(), "UTF-8"));
/* 441 */         factoryClassName = r.readLine().trim();
/* 442 */         return newInstance(classes, properties, factoryClassName);
/*     */       } 
/* 444 */       logger.log(Level.FINE, "Unable to find: {0}", resource);
/*     */     }
/* 446 */     catch (UnsupportedEncodingException e) {
/*     */       
/* 448 */       throw new JAXBException(e);
/* 449 */     } catch (IOException e) {
/* 450 */       throw new JAXBException(e);
/*     */     } 
/*     */ 
/*     */     
/* 454 */     logger.fine("Trying to create the platform default provider");
/* 455 */     return newInstance(classes, properties, "com.sun.xml.internal.bind.v2.ContextFactory");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class lookupUsingOSGiServiceLoader(String factoryId) {
/*     */     try {
/* 461 */       Class<?> serviceClass = Class.forName(factoryId);
/* 462 */       Class<?> target = Class.forName("org.glassfish.hk2.osgiresourcelocator.ServiceLoader");
/* 463 */       Method m = target.getMethod("lookupProviderClasses", new Class[] { Class.class });
/* 464 */       Iterator<Class<?>> iter = ((Iterable)m.invoke(null, new Object[] { serviceClass })).iterator();
/* 465 */       return iter.hasNext() ? iter.next() : null;
/* 466 */     } catch (Exception e) {
/* 467 */       logger.log(Level.FINE, "Unable to find from OSGi: {0}", factoryId);
/* 468 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties loadJAXBProperties(ClassLoader classLoader, String propFileName) throws JAXBException {
/* 476 */     Properties props = null;
/*     */     
/*     */     try {
/*     */       URL url;
/* 480 */       if (classLoader == null) {
/* 481 */         url = ClassLoader.getSystemResource(propFileName);
/*     */       } else {
/* 483 */         url = classLoader.getResource(propFileName);
/*     */       } 
/* 485 */       if (url != null) {
/* 486 */         logger.log(Level.FINE, "loading props from {0}", url);
/* 487 */         props = new Properties();
/* 488 */         InputStream is = url.openStream();
/* 489 */         props.load(is);
/* 490 */         is.close();
/*     */       } 
/* 492 */     } catch (IOException ioe) {
/* 493 */       logger.log(Level.FINE, "Unable to load " + propFileName, ioe);
/* 494 */       throw new JAXBException(ioe.toString(), ioe);
/*     */     } 
/*     */     
/* 497 */     return props;
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
/*     */   static URL which(Class clazz, ClassLoader loader) {
/* 515 */     String classnameAsResource = clazz.getName().replace('.', '/') + ".class";
/*     */     
/* 517 */     if (loader == null) {
/* 518 */       loader = getSystemClassLoader();
/*     */     }
/*     */     
/* 521 */     return loader.getResource(classnameAsResource);
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
/*     */   static URL which(Class clazz) {
/* 537 */     return which(clazz, getClassClassLoader(clazz));
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
/*     */   private static Class safeLoadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
/* 559 */     logger.log(Level.FINE, "Trying to load {0}", className);
/*     */     
/*     */     try {
/* 562 */       SecurityManager s = System.getSecurityManager();
/* 563 */       if (s != null) {
/* 564 */         int i = className.lastIndexOf('.');
/* 565 */         if (i != -1) {
/* 566 */           s.checkPackageAccess(className.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 570 */       if (classLoader == null) {
/* 571 */         return Class.forName(className);
/*     */       }
/* 573 */       return classLoader.loadClass(className);
/*     */     }
/* 575 */     catch (SecurityException se) {
/*     */       
/* 577 */       if ("com.sun.xml.internal.bind.v2.ContextFactory".equals(className)) {
/* 578 */         return Class.forName(className);
/*     */       }
/* 580 */       throw se;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ClassLoader getContextClassLoader() {
/* 585 */     if (System.getSecurityManager() == null) {
/* 586 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 588 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public Object run() {
/* 591 */             return Thread.currentThread().getContextClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static ClassLoader getClassClassLoader(final Class c) {
/* 598 */     if (System.getSecurityManager() == null) {
/* 599 */       return c.getClassLoader();
/*     */     }
/* 601 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public Object run() {
/* 604 */             return c.getClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static ClassLoader getSystemClassLoader() {
/* 611 */     if (System.getSecurityManager() == null) {
/* 612 */       return ClassLoader.getSystemClassLoader();
/*     */     }
/* 614 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public Object run() {
/* 617 */             return ClassLoader.getSystemClassLoader();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\ContextFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */