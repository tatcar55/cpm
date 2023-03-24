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
/*     */ public class ConcurrentQueueImpl<V>
/*     */   implements ConcurrentQueue<V>
/*     */ {
/*  52 */   final Entry<V> head = new Entry<V>(null);
/*  53 */   int count = 0;
/*     */   
/*     */   public ConcurrentQueueImpl() {
/*  56 */     this.head.next = this.head;
/*  57 */     this.head.prev = this.head;
/*     */   }
/*     */   
/*     */   private final class Entry<V> {
/*  61 */     Entry<V> next = null;
/*  62 */     Entry<V> prev = null;
/*     */     private ConcurrentQueueImpl<V>.HandleImpl<V> handle;
/*     */     
/*     */     Entry(V value) {
/*  66 */       this.handle = new ConcurrentQueueImpl.HandleImpl<V>(this, value);
/*     */     }
/*     */     
/*     */     ConcurrentQueueImpl<V>.HandleImpl<V> handle() {
/*  70 */       return this.handle;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class HandleImpl<V> implements ConcurrentQueue.Handle<V> {
/*     */     private ConcurrentQueueImpl<V>.Entry<V> entry;
/*     */     private final V value;
/*     */     private boolean valid;
/*     */     
/*     */     HandleImpl(ConcurrentQueueImpl<V>.Entry<V> entry, V value) {
/*  80 */       this.entry = entry;
/*  81 */       this.value = value;
/*  82 */       this.valid = true;
/*     */     }
/*     */     
/*     */     ConcurrentQueueImpl<V>.Entry<V> entry() {
/*  86 */       return this.entry;
/*     */     }
/*     */     
/*     */     public V value() {
/*  90 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove() {
/*  97 */       if (!this.valid) {
/*  98 */         return false;
/*     */       }
/*     */       
/* 101 */       this.valid = false;
/*     */       
/* 103 */       this.entry.next.prev = this.entry.prev;
/* 104 */       this.entry.prev.next = this.entry.next;
/* 105 */       ConcurrentQueueImpl.this.count--;
/*     */       
/* 107 */       this.entry.prev = null;
/* 108 */       this.entry.next = null;
/* 109 */       this.entry.handle = null;
/* 110 */       this.entry = null;
/* 111 */       this.valid = false;
/* 112 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 117 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrentQueue.Handle<V> offer(V arg) {
/* 124 */     if (arg == null) {
/* 125 */       throw new IllegalArgumentException("Argument cannot be null");
/*     */     }
/* 127 */     Entry<V> entry = new Entry<V>(arg);
/*     */     
/* 129 */     entry.next = this.head;
/* 130 */     entry.prev = this.head.prev;
/* 131 */     this.head.prev.next = entry;
/* 132 */     this.head.prev = entry;
/* 133 */     this.count++;
/*     */     
/* 135 */     return entry.handle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V poll() {
/* 142 */     Entry<V> first = null;
/* 143 */     V value = null;
/*     */     
/* 145 */     first = this.head.next;
/* 146 */     if (first == this.head) {
/* 147 */       return null;
/*     */     }
/* 149 */     value = first.handle().value();
/*     */ 
/*     */     
/* 152 */     first.handle().remove();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     first.next = null;
/* 158 */     first.prev = null;
/* 159 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\concurrent\ConcurrentQueueImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */