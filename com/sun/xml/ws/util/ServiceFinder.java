/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.ComponentEx;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Array;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceFinder<T>
/*     */   implements Iterable<T>
/*     */ {
/*     */   private static final String prefix = "META-INF/services/";
/* 157 */   private static WeakHashMap<ClassLoader, ConcurrentHashMap<String, ServiceName[]>> serviceNameCache = new WeakHashMap<ClassLoader, ConcurrentHashMap<String, ServiceName[]>>();
/*     */   private final Class<T> serviceClass;
/*     */   @Nullable
/*     */   private final ClassLoader classLoader;
/*     */   @Nullable
/*     */   private final ComponentEx component;
/*     */   
/*     */   private static class ServiceName {
/*     */     final String className;
/*     */     
/*     */     public ServiceName(String className, URL config) {
/* 168 */       this.className = className;
/* 169 */       this.config = config;
/*     */     }
/*     */     final URL config; }
/*     */   
/*     */   public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @Nullable ClassLoader loader, Component component) {
/* 174 */     return new ServiceFinder<T>(service, loader, component);
/*     */   }
/*     */   
/*     */   public static <T> ServiceFinder<T> find(@NotNull Class<T> service, Component component) {
/* 178 */     return find(service, Thread.currentThread().getContextClassLoader(), component);
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
/*     */   public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @Nullable ClassLoader loader) {
/* 207 */     return find(service, loader, (Component)ContainerResolver.getInstance().getContainer());
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
/* 227 */     return find(service, Thread.currentThread().getContextClassLoader());
/*     */   }
/*     */   
/*     */   private ServiceFinder(Class<T> service, ClassLoader loader, Component component) {
/* 231 */     this.serviceClass = service;
/* 232 */     this.classLoader = loader;
/* 233 */     this.component = getComponentEx(component);
/*     */   }
/*     */   
/*     */   private static ServiceName[] serviceClassNames(Class serviceClass, ClassLoader classLoader) {
/* 237 */     ArrayList<ServiceName> l = new ArrayList<ServiceName>();
/* 238 */     for (Iterator<ServiceName> it = new ServiceNameIterator(serviceClass, classLoader); it.hasNext(); l.add(it.next()));
/* 239 */     return l.<ServiceName>toArray(new ServiceName[l.size()]);
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
/*     */   public Iterator<T> iterator() {
/* 253 */     Iterator<T> it = new LazyIterator<T>(this.serviceClass, this.classLoader);
/* 254 */     return (this.component != null) ? new CompositeIterator<T>((Iterator<T>[])new Iterator[] { this.component.getIterableSPI(this.serviceClass).iterator(), it }) : it;
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
/*     */   public T[] toArray() {
/* 269 */     List<T> result = new ArrayList<T>();
/* 270 */     for (T t : this) {
/* 271 */       result.add(t);
/*     */     }
/* 273 */     return result.toArray((T[])Array.newInstance(this.serviceClass, result.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fail(Class service, String msg, Throwable cause) throws ServiceConfigurationError {
/* 278 */     ServiceConfigurationError sce = new ServiceConfigurationError(service.getName() + ": " + msg);
/*     */     
/* 280 */     sce.initCause(cause);
/* 281 */     throw sce;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fail(Class service, String msg) throws ServiceConfigurationError {
/* 286 */     throw new ServiceConfigurationError(service.getName() + ": " + msg);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fail(Class service, URL u, int line, String msg) throws ServiceConfigurationError {
/* 291 */     fail(service, u + ":" + line + ": " + msg);
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
/* 302 */     String ln = r.readLine();
/* 303 */     if (ln == null) {
/* 304 */       return -1;
/*     */     }
/* 306 */     int ci = ln.indexOf('#');
/* 307 */     if (ci >= 0) ln = ln.substring(0, ci); 
/* 308 */     ln = ln.trim();
/* 309 */     int n = ln.length();
/* 310 */     if (n != 0) {
/* 311 */       if (ln.indexOf(' ') >= 0 || ln.indexOf('\t') >= 0)
/* 312 */         fail(service, u, lc, "Illegal configuration-file syntax"); 
/* 313 */       int cp = ln.codePointAt(0);
/* 314 */       if (!Character.isJavaIdentifierStart(cp))
/* 315 */         fail(service, u, lc, "Illegal provider-class name: " + ln);  int i;
/* 316 */       for (i = Character.charCount(cp); i < n; i += Character.charCount(cp)) {
/* 317 */         cp = ln.codePointAt(i);
/* 318 */         if (!Character.isJavaIdentifierPart(cp) && cp != 46)
/* 319 */           fail(service, u, lc, "Illegal provider-class name: " + ln); 
/*     */       } 
/* 321 */       if (!returned.contains(ln)) {
/* 322 */         names.add(ln);
/* 323 */         returned.add(ln);
/*     */       } 
/*     */     } 
/* 326 */     return lc + 1;
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
/* 347 */     InputStream in = null;
/* 348 */     BufferedReader r = null;
/* 349 */     ArrayList<String> names = new ArrayList<String>();
/*     */     try {
/* 351 */       in = u.openStream();
/* 352 */       r = new BufferedReader(new InputStreamReader(in, "utf-8"));
/* 353 */       int lc = 1;
/* 354 */       while ((lc = parseLine(service, u, r, lc, names, returned)) >= 0);
/* 355 */     } catch (IOException x) {
/* 356 */       fail(service, ": " + x);
/*     */     } finally {
/*     */       try {
/* 359 */         if (r != null) r.close(); 
/* 360 */         if (in != null) in.close(); 
/* 361 */       } catch (IOException y) {
/* 362 */         fail(service, ": " + y);
/*     */       } 
/*     */     } 
/* 365 */     return names.iterator();
/*     */   }
/*     */   
/*     */   private static ComponentEx getComponentEx(Component component) {
/* 369 */     if (component instanceof ComponentEx) {
/* 370 */       return (ComponentEx)component;
/*     */     }
/* 372 */     return (component != null) ? new ComponentExWrapper(component) : null;
/*     */   }
/*     */   
/*     */   private static class ComponentExWrapper implements ComponentEx {
/*     */     private final Component component;
/*     */     
/*     */     public ComponentExWrapper(Component component) {
/* 379 */       this.component = component;
/*     */     }
/*     */     
/*     */     public <S> S getSPI(Class<S> spiType) {
/* 383 */       return (S)this.component.getSPI(spiType);
/*     */     }
/*     */     
/*     */     public <S> Iterable<S> getIterableSPI(Class<S> spiType) {
/* 387 */       S item = getSPI(spiType);
/* 388 */       if (item != null) {
/* 389 */         Collection<S> c = Collections.singletonList(item);
/* 390 */         return c;
/*     */       } 
/* 392 */       return Collections.emptySet();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CompositeIterator<T> implements Iterator<T> {
/*     */     private final Iterator<Iterator<T>> it;
/* 398 */     private Iterator<T> current = null;
/*     */     
/*     */     public CompositeIterator(Iterator<T>... iterators) {
/* 401 */       this.it = Arrays.<Iterator<T>>asList(iterators).iterator();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 405 */       if (this.current != null && this.current.hasNext()) {
/* 406 */         return true;
/*     */       }
/* 408 */       while (this.it.hasNext()) {
/* 409 */         this.current = this.it.next();
/* 410 */         if (this.current.hasNext()) {
/* 411 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 415 */       return false;
/*     */     }
/*     */     
/*     */     public T next() {
/* 419 */       if (!hasNext()) {
/* 420 */         throw new NoSuchElementException();
/*     */       }
/* 422 */       return this.current.next();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 426 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ServiceNameIterator
/*     */     implements Iterator<ServiceName>
/*     */   {
/*     */     Class service;
/*     */     @Nullable
/*     */     ClassLoader loader;
/* 436 */     Enumeration<URL> configs = null;
/* 437 */     Iterator<String> pending = null;
/* 438 */     Set<String> returned = new TreeSet<String>();
/* 439 */     String nextName = null;
/* 440 */     URL currentConfig = null;
/*     */     
/*     */     private ServiceNameIterator(Class service, ClassLoader loader) {
/* 443 */       this.service = service;
/* 444 */       this.loader = loader;
/*     */     }
/*     */     
/*     */     public boolean hasNext() throws ServiceConfigurationError {
/* 448 */       if (this.nextName != null) {
/* 449 */         return true;
/*     */       }
/* 451 */       if (this.configs == null) {
/*     */         try {
/* 453 */           String fullName = "META-INF/services/" + this.service.getName();
/* 454 */           if (this.loader == null)
/* 455 */           { this.configs = ClassLoader.getSystemResources(fullName); }
/*     */           else
/* 457 */           { this.configs = this.loader.getResources(fullName); } 
/* 458 */         } catch (IOException x) {
/* 459 */           ServiceFinder.fail(this.service, ": " + x);
/*     */         } 
/*     */       }
/* 462 */       while (this.pending == null || !this.pending.hasNext()) {
/* 463 */         if (!this.configs.hasMoreElements()) {
/* 464 */           return false;
/*     */         }
/* 466 */         this.currentConfig = this.configs.nextElement();
/* 467 */         this.pending = ServiceFinder.parse(this.service, this.currentConfig, this.returned);
/*     */       } 
/* 469 */       this.nextName = this.pending.next();
/* 470 */       return true;
/*     */     }
/*     */     
/*     */     public ServiceFinder.ServiceName next() throws ServiceConfigurationError {
/* 474 */       if (!hasNext()) {
/* 475 */         throw new NoSuchElementException();
/*     */       }
/* 477 */       String cn = this.nextName;
/* 478 */       this.nextName = null;
/* 479 */       return new ServiceFinder.ServiceName(cn, this.currentConfig);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 483 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private static class LazyIterator<T> implements Iterator<T> {
/*     */     Class<T> service;
/*     */     @Nullable
/*     */     ClassLoader loader;
/*     */     ServiceFinder.ServiceName[] names;
/*     */     int index;
/*     */     
/*     */     private LazyIterator(Class<T> service, ClassLoader loader) {
/* 494 */       this.service = service;
/* 495 */       this.loader = loader;
/* 496 */       this.names = null;
/* 497 */       this.index = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 502 */       if (this.names == null) {
/* 503 */         ConcurrentHashMap<String, ServiceFinder.ServiceName[]> nameMap = null;
/* 504 */         synchronized (ServiceFinder.serviceNameCache) { nameMap = (ConcurrentHashMap<String, ServiceFinder.ServiceName[]>)ServiceFinder.serviceNameCache.get(this.loader); }
/* 505 */          this.names = (nameMap != null) ? nameMap.get(this.service.getName()) : null;
/* 506 */         if (this.names == null) {
/* 507 */           this.names = ServiceFinder.serviceClassNames(this.service, this.loader);
/* 508 */           if (nameMap == null) nameMap = (ConcurrentHashMap)new ConcurrentHashMap<String, ServiceFinder.ServiceName>(); 
/* 509 */           nameMap.put(this.service.getName(), this.names);
/* 510 */           synchronized (ServiceFinder.serviceNameCache) { ServiceFinder.serviceNameCache.put(this.loader, nameMap); }
/*     */         
/*     */         } 
/* 513 */       }  return (this.index < this.names.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public T next() {
/* 518 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 519 */       ServiceFinder.ServiceName sn = this.names[this.index++];
/* 520 */       String cn = sn.className;
/* 521 */       URL currentConfig = sn.config;
/*     */       try {
/* 523 */         return this.service.cast(Class.forName(cn, true, this.loader).newInstance());
/* 524 */       } catch (ClassNotFoundException x) {
/* 525 */         ServiceFinder.fail(this.service, "Provider " + cn + " is specified in " + currentConfig + " but not found");
/* 526 */       } catch (Exception x) {
/* 527 */         ServiceFinder.fail(this.service, "Provider " + cn + " is specified in " + currentConfig + "but could not be instantiated: " + x, x);
/*     */       } 
/* 529 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 534 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\ServiceFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */