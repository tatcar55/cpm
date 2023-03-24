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
/*     */ public class ConcurrentQueueNonBlockingImpl<V>
/*     */   implements ConcurrentQueue<V>
/*     */ {
/*  64 */   final Entry<V> head = new Entry<V>(null);
/*  65 */   final Object lock = new Object();
/*  66 */   int count = 0;
/*     */   
/*     */   private final class Entry<V> {
/*  69 */     Entry<V> next = null;
/*  70 */     Entry<V> prev = null;
/*     */     private ConcurrentQueueNonBlockingImpl<V>.HandleImpl<V> handle;
/*     */     
/*     */     Entry(V value) {
/*  74 */       this.handle = new ConcurrentQueueNonBlockingImpl.HandleImpl<V>(this, value);
/*     */     }
/*     */     
/*     */     ConcurrentQueueNonBlockingImpl<V>.HandleImpl<V> handle() {
/*  78 */       return this.handle;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class HandleImpl<V> implements ConcurrentQueue.Handle<V> {
/*     */     private ConcurrentQueueNonBlockingImpl<V>.Entry<V> entry;
/*     */     private final V value;
/*     */     private boolean valid;
/*     */     
/*     */     HandleImpl(ConcurrentQueueNonBlockingImpl<V>.Entry<V> entry, V value) {
/*  88 */       this.entry = entry;
/*  89 */       this.value = value;
/*  90 */       this.valid = true;
/*     */     }
/*     */     
/*     */     ConcurrentQueueNonBlockingImpl<V>.Entry<V> entry() {
/*  94 */       return this.entry;
/*     */     }
/*     */     
/*     */     public V value() {
/*  98 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove() {
/* 105 */       synchronized (ConcurrentQueueNonBlockingImpl.this.lock) {
/* 106 */         if (!this.valid) {
/* 107 */           return false;
/*     */         }
/*     */         
/* 110 */         this.valid = false;
/*     */         
/* 112 */         this.entry.next.prev = this.entry.prev;
/* 113 */         this.entry.prev.next = this.entry.next;
/* 114 */         ConcurrentQueueNonBlockingImpl.this.count--;
/*     */       } 
/*     */       
/* 117 */       this.entry.prev = null;
/* 118 */       this.entry.next = null;
/* 119 */       this.entry.handle = null;
/* 120 */       this.entry = null;
/* 121 */       this.valid = false;
/* 122 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 127 */     synchronized (this.lock) {
/* 128 */       return this.count;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrentQueue.Handle<V> offer(V arg) {
/* 136 */     if (arg == null) {
/* 137 */       throw new IllegalArgumentException("Argument cannot be null");
/*     */     }
/* 139 */     Entry<V> entry = new Entry<V>(arg);
/*     */     
/* 141 */     synchronized (this.lock) {
/* 142 */       entry.next = this.head;
/* 143 */       entry.prev = this.head.prev;
/* 144 */       this.head.prev.next = entry;
/* 145 */       this.head.prev = entry;
/* 146 */       this.count++;
/*     */     } 
/*     */     
/* 149 */     return entry.handle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V poll() {
/* 156 */     Entry<V> first = null;
/*     */     
/* 158 */     synchronized (this.lock) {
/* 159 */       first = this.head.next;
/* 160 */       if (first == this.head) {
/* 161 */         return null;
/*     */       }
/*     */       
/* 164 */       first.handle().remove();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     first.next = null;
/* 171 */     first.prev = null;
/* 172 */     V value = first.handle().value();
/* 173 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\concurrent\ConcurrentQueueNonBlockingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */