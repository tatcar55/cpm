/*     */ package com.sun.xml.ws.policy.privateutil;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Array;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ServiceFinder<T>
/*     */   implements Iterable<T>
/*     */ {
/* 144 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(ServiceFinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String prefix = "META-INF/services/";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Class<T> serviceClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> ServiceFinder<T> find(Class<T> service, ClassLoader loader) {
/* 177 */     if (null == service) {
/* 178 */       throw (NullPointerException)LOGGER.logSevereException(new NullPointerException(LocalizationMessages.WSP_0032_SERVICE_CAN_NOT_BE_NULL()));
/*     */     }
/* 180 */     return new ServiceFinder<T>(service, loader);
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
/*     */   public static <T> ServiceFinder<T> find(Class<T> service) {
/* 200 */     return find(service, Thread.currentThread().getContextClassLoader());
/*     */   }
/*     */   
/*     */   private ServiceFinder(Class<T> service, ClassLoader loader) {
/* 204 */     this.serviceClass = service;
/* 205 */     this.classLoader = loader;
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
/*     */   public Iterator<T> iterator() {
/* 218 */     return new LazyIterator<T>(this.serviceClass, this.classLoader);
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
/*     */   public T[] toArray() {
/* 231 */     List<T> result = new ArrayList<T>();
/* 232 */     for (T t : this) {
/* 233 */       result.add(t);
/*     */     }
/* 235 */     return result.toArray((T[])Array.newInstance(this.serviceClass, result.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fail(Class service, String msg, Throwable cause) throws ServiceConfigurationError {
/* 240 */     ServiceConfigurationError sce = new ServiceConfigurationError(LocalizationMessages.WSP_0025_SPI_FAIL_SERVICE_MSG(service.getName(), msg));
/*     */     
/* 242 */     if (null != cause) {
/* 243 */       sce.initCause(cause);
/*     */     }
/*     */     
/* 246 */     throw (ServiceConfigurationError)LOGGER.logSevereException(sce);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fail(Class service, URL u, int line, String msg, Throwable cause) throws ServiceConfigurationError {
/* 256 */     fail(service, LocalizationMessages.WSP_0024_SPI_FAIL_SERVICE_URL_LINE_MSG(u, Integer.valueOf(line), msg), cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseLine(Class service, URL u, BufferedReader r, int lc, List<String> names, Set<String> returned) throws IOException, ServiceConfigurationError {
/* 267 */     String ln = r.readLine();
/* 268 */     if (ln == null) {
/* 269 */       return -1;
/*     */     }
/* 271 */     int ci = ln.indexOf('#');
/* 272 */     if (ci >= 0) ln = ln.substring(0, ci); 
/* 273 */     ln = ln.trim();
/* 274 */     int n = ln.length();
/* 275 */     if (n != 0) {
/* 276 */       if (ln.indexOf(' ') >= 0 || ln.indexOf('\t') >= 0)
/* 277 */         fail(service, u, lc, LocalizationMessages.WSP_0067_ILLEGAL_CFG_FILE_SYNTAX(), null); 
/* 278 */       int cp = ln.codePointAt(0);
/* 279 */       if (!Character.isJavaIdentifierStart(cp))
/* 280 */         fail(service, u, lc, LocalizationMessages.WSP_0066_ILLEGAL_PROVIDER_CLASSNAME(ln), null);  int i;
/* 281 */       for (i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
/* 282 */         cp = ln.codePointAt(i);
/* 283 */         if (!Character.isJavaIdentifierPart(cp) && cp != 46)
/* 284 */           fail(service, u, lc, LocalizationMessages.WSP_0066_ILLEGAL_PROVIDER_CLASSNAME(ln), null); 
/*     */       } 
/* 286 */       if (!returned.contains(ln)) {
/* 287 */         names.add(ln);
/* 288 */         returned.add(ln);
/*     */       } 
/*     */     } 
/* 291 */     return lc + 1;
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
/*     */   private static Iterator<String> parse(Class service, URL u, Set<String> returned) throws ServiceConfigurationError {
/* 312 */     InputStream in = null;
/* 313 */     BufferedReader r = null;
/* 314 */     ArrayList<String> names = new ArrayList<String>();
/*     */     try {
/* 316 */       in = u.openStream();
/* 317 */       r = new BufferedReader(new InputStreamReader(in, "utf-8"));
/* 318 */       int lc = 1;
/* 319 */       while ((lc = parseLine(service, u, r, lc, names, returned)) >= 0);
/* 320 */     } catch (IOException x) {
/* 321 */       fail(service, ": " + x, x);
/*     */     } finally {
/*     */       try {
/* 324 */         if (r != null) r.close(); 
/* 325 */         if (in != null) in.close(); 
/* 326 */       } catch (IOException y) {
/* 327 */         fail(service, ": " + y, y);
/*     */       } 
/*     */     } 
/* 330 */     return names.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LazyIterator<T>
/*     */     implements Iterator<T>
/*     */   {
/*     */     Class<T> service;
/*     */     
/*     */     ClassLoader loader;
/* 340 */     Enumeration<URL> configs = null;
/* 341 */     Iterator<String> pending = null;
/* 342 */     Set<String> returned = new TreeSet<String>();
/* 343 */     String nextName = null;
/*     */     
/*     */     private LazyIterator(Class<T> service, ClassLoader loader) {
/* 346 */       this.service = service;
/* 347 */       this.loader = loader;
/*     */     }
/*     */     
/*     */     public boolean hasNext() throws ServiceConfigurationError {
/* 351 */       if (this.nextName != null) {
/* 352 */         return true;
/*     */       }
/* 354 */       if (this.configs == null) {
/*     */         try {
/* 356 */           String fullName = "META-INF/services/" + this.service.getName();
/* 357 */           if (this.loader == null)
/* 358 */           { this.configs = ClassLoader.getSystemResources(fullName); }
/*     */           else
/* 360 */           { this.configs = this.loader.getResources(fullName); } 
/* 361 */         } catch (IOException x) {
/* 362 */           ServiceFinder.fail(this.service, ": " + x, x);
/*     */         } 
/*     */       }
/* 365 */       while (this.pending == null || !this.pending.hasNext()) {
/* 366 */         if (!this.configs.hasMoreElements()) {
/* 367 */           return false;
/*     */         }
/* 369 */         this.pending = ServiceFinder.parse(this.service, this.configs.nextElement(), this.returned);
/*     */       } 
/* 371 */       this.nextName = this.pending.next();
/* 372 */       return true;
/*     */     }
/*     */     
/*     */     public T next() throws ServiceConfigurationError {
/* 376 */       if (!hasNext()) {
/* 377 */         throw new NoSuchElementException();
/*     */       }
/* 379 */       String cn = this.nextName;
/* 380 */       this.nextName = null;
/*     */       try {
/* 382 */         return this.service.cast(Class.forName(cn, true, this.loader).newInstance());
/* 383 */       } catch (ClassNotFoundException x) {
/* 384 */         ServiceFinder.fail(this.service, LocalizationMessages.WSP_0027_SERVICE_PROVIDER_NOT_FOUND(cn), x);
/* 385 */       } catch (Exception x) {
/* 386 */         ServiceFinder.fail(this.service, LocalizationMessages.WSP_0028_SERVICE_PROVIDER_COULD_NOT_BE_INSTANTIATED(cn), x);
/*     */       } 
/* 388 */       return null;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 392 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\privateutil\ServiceFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */