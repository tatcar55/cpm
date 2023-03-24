/*     */ package com.sun.xml.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Injector
/*     */ {
/*  76 */   private static final ReentrantReadWriteLock irwl = new ReentrantReadWriteLock();
/*  77 */   private static final Lock ir = irwl.readLock();
/*  78 */   private static final Lock iw = irwl.writeLock();
/*  79 */   private static final Map<ClassLoader, WeakReference<Injector>> injectors = new WeakHashMap<ClassLoader, WeakReference<Injector>>();
/*     */   
/*  81 */   private static final Logger logger = Util.getClassLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class inject(ClassLoader cl, String className, byte[] image) {
/*  90 */     Injector injector = get(cl);
/*  91 */     if (injector != null) {
/*  92 */       return injector.inject(className, image);
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class find(ClassLoader cl, String className) {
/* 102 */     Injector injector = get(cl);
/* 103 */     if (injector != null) {
/* 104 */       return injector.find(className);
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Injector get(ClassLoader cl) {
/*     */     WeakReference<Injector> wr;
/* 117 */     Injector injector = null;
/*     */     
/* 119 */     ir.lock();
/*     */     try {
/* 121 */       wr = injectors.get(cl);
/*     */     } finally {
/* 123 */       ir.unlock();
/*     */     } 
/* 125 */     if (wr != null) {
/* 126 */       injector = wr.get();
/*     */     }
/* 128 */     if (injector == null) {
/*     */       try {
/* 130 */         wr = new WeakReference<Injector>(injector = new Injector(cl));
/* 131 */         iw.lock();
/*     */         try {
/* 133 */           if (!injectors.containsKey(cl)) {
/* 134 */             injectors.put(cl, wr);
/*     */           }
/*     */         } finally {
/* 137 */           iw.unlock();
/*     */         } 
/* 139 */       } catch (SecurityException e) {
/* 140 */         logger.log(Level.FINE, "Unable to set up a back-door for the injector", e);
/* 141 */         return null;
/*     */       } 
/*     */     }
/* 144 */     return injector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 149 */   private final Map<String, Class> classes = (Map)new HashMap<String, Class<?>>();
/* 150 */   private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
/* 151 */   private final Lock r = this.rwl.readLock();
/* 152 */   private final Lock w = this.rwl.writeLock();
/*     */   
/*     */   private final ClassLoader parent;
/*     */   
/*     */   private final boolean loadable;
/*     */   
/*     */   private static final Method defineClass;
/*     */   
/*     */   private static final Method resolveClass;
/*     */   private static final Method findLoadedClass;
/*     */   
/*     */   static {
/*     */     try {
/* 165 */       defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/* 166 */       resolveClass = ClassLoader.class.getDeclaredMethod("resolveClass", new Class[] { Class.class });
/* 167 */       findLoadedClass = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
/* 168 */     } catch (NoSuchMethodException e) {
/*     */       
/* 170 */       throw new NoSuchMethodError(e.getMessage());
/*     */     } 
/* 172 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           
/*     */           public Void run()
/*     */           {
/* 177 */             Injector.defineClass.setAccessible(true);
/* 178 */             Injector.resolveClass.setAccessible(true);
/* 179 */             Injector.findLoadedClass.setAccessible(true);
/* 180 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private Injector(ClassLoader parent) {
/* 186 */     this.parent = parent;
/* 187 */     assert parent != null;
/*     */     
/* 189 */     boolean loadableCheck = false;
/*     */     
/*     */     try {
/* 192 */       loadableCheck = (parent.loadClass(Accessor.class.getName()) == Accessor.class);
/* 193 */     } catch (ClassNotFoundException e) {}
/*     */ 
/*     */ 
/*     */     
/* 197 */     this.loadable = loadableCheck;
/*     */   }
/*     */ 
/*     */   
/*     */   private Class inject(String className, byte[] image) {
/* 202 */     if (!this.loadable)
/*     */     {
/* 204 */       return null;
/*     */     }
/*     */     
/* 207 */     boolean wlocked = false;
/* 208 */     boolean rlocked = false;
/*     */     
/*     */     try {
/* 211 */       this.r.lock();
/* 212 */       rlocked = true;
/*     */       
/* 214 */       Class c = this.classes.get(className);
/*     */ 
/*     */ 
/*     */       
/* 218 */       this.r.unlock();
/* 219 */       rlocked = false;
/*     */ 
/*     */       
/* 222 */       if (c == null) {
/*     */         
/*     */         try {
/* 225 */           c = (Class)findLoadedClass.invoke(this.parent, new Object[] { className.replace('/', '.') });
/* 226 */         } catch (IllegalArgumentException e) {
/* 227 */           logger.log(Level.FINE, "Unable to find " + className, e);
/* 228 */         } catch (IllegalAccessException e) {
/* 229 */           logger.log(Level.FINE, "Unable to find " + className, e);
/* 230 */         } catch (InvocationTargetException e) {
/* 231 */           Throwable t = e.getTargetException();
/* 232 */           logger.log(Level.FINE, "Unable to find " + className, t);
/*     */         } 
/*     */         
/* 235 */         if (c != null) {
/*     */           
/* 237 */           this.w.lock();
/* 238 */           wlocked = true;
/*     */           
/* 240 */           this.classes.put(className, c);
/*     */           
/* 242 */           this.w.unlock();
/* 243 */           wlocked = false;
/*     */           
/* 245 */           return c;
/*     */         } 
/*     */       } 
/*     */       
/* 249 */       if (c == null) {
/*     */         
/* 251 */         this.r.lock();
/* 252 */         rlocked = true;
/*     */         
/* 254 */         c = this.classes.get(className);
/*     */ 
/*     */ 
/*     */         
/* 258 */         this.r.unlock();
/* 259 */         rlocked = false;
/*     */         
/* 261 */         if (c == null) {
/*     */ 
/*     */           
/*     */           try {
/* 265 */             c = (Class)defineClass.invoke(this.parent, new Object[] { className.replace('/', '.'), image, Integer.valueOf(0), Integer.valueOf(image.length) });
/* 266 */             resolveClass.invoke(this.parent, new Object[] { c });
/* 267 */           } catch (IllegalAccessException e) {
/* 268 */             logger.log(Level.FINE, "Unable to inject " + className, e);
/* 269 */             return null;
/* 270 */           } catch (InvocationTargetException e) {
/* 271 */             Throwable t = e.getTargetException();
/* 272 */             if (t instanceof LinkageError) {
/* 273 */               logger.log(Level.FINE, "duplicate class definition bug occured? Please report this : " + className, t);
/*     */             } else {
/* 275 */               logger.log(Level.FINE, "Unable to inject " + className, t);
/*     */             } 
/* 277 */             return null;
/* 278 */           } catch (SecurityException e) {
/* 279 */             logger.log(Level.FINE, "Unable to inject " + className, e);
/* 280 */             return null;
/* 281 */           } catch (LinkageError e) {
/* 282 */             logger.log(Level.FINE, "Unable to inject " + className, e);
/* 283 */             return null;
/*     */           } 
/*     */           
/* 286 */           this.w.lock();
/* 287 */           wlocked = true;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 292 */           if (!this.classes.containsKey(className)) {
/* 293 */             this.classes.put(className, c);
/*     */           }
/*     */           
/* 296 */           this.w.unlock();
/* 297 */           wlocked = false;
/*     */         } 
/*     */       } 
/* 300 */       return c;
/*     */     } finally {
/* 302 */       if (rlocked) {
/* 303 */         this.r.unlock();
/*     */       }
/* 305 */       if (wlocked) {
/* 306 */         this.w.unlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Class find(String className) {
/* 312 */     this.r.lock();
/*     */     try {
/* 314 */       return this.classes.get(className);
/*     */     } finally {
/* 316 */       this.r.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\Injector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */