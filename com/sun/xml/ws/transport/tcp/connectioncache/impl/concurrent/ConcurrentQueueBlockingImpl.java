/*     */ package com.sun.xml.ws.transport.tcp.connectioncache.impl.concurrent;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent.ConcurrentQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentQueueBlockingImpl<V>
/*     */   implements ConcurrentQueue<V>
/*     */ {
/*  65 */   final Entry<V> head = new Entry<V>(null);
/*  66 */   final Object lock = new Object();
/*  67 */   int count = 0;
/*     */   
/*     */   private final class Entry<V> {
/*  70 */     Entry<V> next = null;
/*  71 */     Entry<V> prev = null;
/*     */     private ConcurrentQueueBlockingImpl<V>.HandleImpl<V> handle;
/*     */     
/*     */     Entry(V value) {
/*  75 */       this.handle = new ConcurrentQueueBlockingImpl.HandleImpl<V>(this, value);
/*     */     }
/*     */     
/*     */     ConcurrentQueueBlockingImpl<V>.HandleImpl<V> handle() {
/*  79 */       return this.handle;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class HandleImpl<V> implements ConcurrentQueue.Handle<V> {
/*     */     private ConcurrentQueueBlockingImpl<V>.Entry<V> entry;
/*     */     private final V value;
/*     */     private boolean valid;
/*     */     
/*     */     HandleImpl(ConcurrentQueueBlockingImpl<V>.Entry<V> entry, V value) {
/*  89 */       this.entry = entry;
/*  90 */       this.value = value;
/*  91 */       this.valid = true;
/*     */     }
/*     */     
/*     */     ConcurrentQueueBlockingImpl<V>.Entry<V> entry() {
/*  95 */       return this.entry;
/*     */     }
/*     */     
/*     */     public V value() {
/*  99 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove() {
/* 106 */       synchronized (ConcurrentQueueBlockingImpl.this.lock) {
/* 107 */         if (!this.valid) {
/* 108 */           return false;
/*     */         }
/*     */         
/* 111 */         this.valid = false;
/*     */         
/* 113 */         this.entry.next.prev = this.entry.prev;
/* 114 */         this.entry.prev.next = this.entry.next;
/* 115 */         ConcurrentQueueBlockingImpl.this.count--;
/*     */       } 
/*     */       
/* 118 */       this.entry.prev = null;
/* 119 */       this.entry.next = null;
/* 120 */       this.entry.handle = null;
/* 121 */       this.entry = null;
/* 122 */       this.valid = false;
/* 123 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 128 */     synchronized (this.lock) {
/* 129 */       return this.count;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrentQueue.Handle<V> offer(V arg) {
/* 137 */     if (arg == null) {
/* 138 */       throw new IllegalArgumentException("Argument cannot be null");
/*     */     }
/* 140 */     Entry<V> entry = new Entry<V>(arg);
/*     */     
/* 142 */     synchronized (this.lock) {
/* 143 */       entry.next = this.head;
/* 144 */       entry.prev = this.head.prev;
/* 145 */       this.head.prev.next = entry;
/* 146 */       this.head.prev = entry;
/* 147 */       this.count++;
/*     */     } 
/*     */     
/* 150 */     return entry.handle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V poll() {
/* 157 */     Entry<V> first = null;
/*     */     
/* 159 */     synchronized (this.lock) {
/* 160 */       first = this.head.next;
/* 161 */       if (first == this.head) {
/* 162 */         return null;
/*     */       }
/*     */       
/* 165 */       first.handle().remove();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     first.next = null;
/* 172 */     first.prev = null;
/* 173 */     V value = first.handle().value();
/* 174 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\concurrent\ConcurrentQueueBlockingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */