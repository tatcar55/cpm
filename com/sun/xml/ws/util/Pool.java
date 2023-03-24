/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Pool<T>
/*     */ {
/*     */   private volatile WeakReference<ConcurrentLinkedQueue<T>> queue;
/*     */   
/*     */   public final T take() {
/*  80 */     T t = getQueue().poll();
/*  81 */     if (t == null)
/*  82 */       return create(); 
/*  83 */     return t;
/*     */   }
/*     */   
/*     */   private ConcurrentLinkedQueue<T> getQueue() {
/*  87 */     WeakReference<ConcurrentLinkedQueue<T>> q = this.queue;
/*  88 */     if (q != null) {
/*  89 */       ConcurrentLinkedQueue<T> concurrentLinkedQueue = q.get();
/*  90 */       if (concurrentLinkedQueue != null) {
/*  91 */         return concurrentLinkedQueue;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     ConcurrentLinkedQueue<T> d = new ConcurrentLinkedQueue<T>();
/*  96 */     this.queue = new WeakReference<ConcurrentLinkedQueue<T>>(d);
/*     */     
/*  98 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void recycle(T t) {
/* 105 */     getQueue().offer(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract T create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Marshaller
/*     */     extends Pool<javax.xml.bind.Marshaller>
/*     */   {
/*     */     private final JAXBContext context;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Marshaller(JAXBContext context) {
/* 129 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     protected javax.xml.bind.Marshaller create() {
/*     */       try {
/* 135 */         return this.context.createMarshaller();
/* 136 */       } catch (JAXBException e) {
/*     */         
/* 138 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Unmarshaller
/*     */     extends Pool<javax.xml.bind.Unmarshaller>
/*     */   {
/*     */     private final JAXBContext context;
/*     */     
/*     */     public Unmarshaller(JAXBContext context) {
/* 150 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     protected javax.xml.bind.Unmarshaller create() {
/*     */       try {
/* 156 */         return this.context.createUnmarshaller();
/* 157 */       } catch (JAXBException e) {
/*     */         
/* 159 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class TubePool
/*     */     extends Pool<Tube>
/*     */   {
/*     */     private final Tube master;
/*     */     
/*     */     public TubePool(Tube master) {
/* 171 */       this.master = master;
/* 172 */       recycle(master);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Tube create() {
/* 177 */       return TubeCloner.clone(this.master);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public final Tube takeMaster() {
/* 188 */       return this.master;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\Pool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */