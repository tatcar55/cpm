/*     */ package com.sun.xml.ws.rx.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimestampedCollection<K, V>
/*     */ {
/*     */   public static <K, V> TimestampedCollection<K, V> newInstance() {
/*  69 */     return new TimestampedCollection<K, V>();
/*     */   }
/*     */   
/*     */   private static class TimestampedRegistration<K, V>
/*     */     implements Comparable<TimestampedRegistration<K, V>> {
/*     */     private final long timestamp;
/*     */     private final K key;
/*     */     @NotNull
/*     */     private final V value;
/*     */     
/*     */     public TimestampedRegistration(long timestamp, K key, @NotNull V value) {
/*  80 */       this.timestamp = timestamp;
/*  81 */       this.key = key;
/*  82 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int compareTo(TimestampedRegistration<K, V> other) {
/*  86 */       return (this.timestamp < other.timestamp) ? -1 : ((this.timestamp == other.timestamp) ? 0 : 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  92 */   private final PriorityQueue<TimestampedRegistration<K, V>> timestampedPriorityQueue = new PriorityQueue<TimestampedRegistration<K, V>>();
/*     */ 
/*     */ 
/*     */   
/*  96 */   private final Map<K, TimestampedRegistration<K, V>> correlationMap = new HashMap<K, TimestampedRegistration<K, V>>();
/*     */ 
/*     */ 
/*     */   
/* 100 */   private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V register(@NotNull K correlationId, @NotNull V subject) {
/*     */     try {
/* 122 */       TimestampedRegistration<K, V> tr = new TimestampedRegistration<K, V>(System.currentTimeMillis(), correlationId, subject);
/* 123 */       this.rwLock.writeLock().lock();
/*     */       
/* 125 */       this.timestampedPriorityQueue.offer(tr);
/* 126 */       TimestampedRegistration<K, V> oldTr = this.correlationMap.put(tr.key, tr);
/* 127 */       if (oldTr != null) {
/* 128 */         removeFromQueue(oldTr);
/* 129 */         return oldTr.value;
/*     */       } 
/*     */       
/* 132 */       return null;
/*     */     } finally {
/* 134 */       this.rwLock.writeLock().unlock();
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
/*     */   public boolean register(@NotNull V subject) {
/* 150 */     return register(System.currentTimeMillis(), subject);
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
/*     */   public boolean register(long timestamp, @NotNull V subject) {
/*     */     try {
/* 168 */       TimestampedRegistration<K, V> tr = new TimestampedRegistration<K, V>(timestamp, null, subject);
/* 169 */       this.rwLock.writeLock().lock();
/*     */       
/* 171 */       return this.timestampedPriorityQueue.offer(tr);
/*     */     } finally {
/*     */       
/* 174 */       this.rwLock.writeLock().unlock();
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
/*     */   public V remove(@NotNull K correlationId) {
/*     */     try {
/* 190 */       this.rwLock.writeLock().lock();
/* 191 */       TimestampedRegistration<K, V> tr = this.correlationMap.remove(correlationId);
/* 192 */       if (tr == null) {
/* 193 */         return null;
/*     */       }
/* 195 */       removeFromQueue(tr);
/*     */       
/* 197 */       return tr.value;
/*     */     } finally {
/* 199 */       this.rwLock.writeLock().unlock();
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
/*     */   public V removeOldest() {
/*     */     try {
/* 217 */       this.rwLock.writeLock().lock();
/* 218 */       TimestampedRegistration<K, V> tr = this.timestampedPriorityQueue.poll();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       this.rwLock.writeLock().unlock();
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
/*     */   public List<V> removeAll() {
/*     */     try {
/* 243 */       this.rwLock.writeLock().lock();
/* 244 */       if (this.timestampedPriorityQueue.isEmpty()) {
/* 245 */         return (List)Collections.emptyList();
/*     */       }
/*     */       
/* 248 */       List<V> values = new ArrayList<V>(this.timestampedPriorityQueue.size());
/*     */       
/* 250 */       while (!this.timestampedPriorityQueue.isEmpty()) {
/* 251 */         values.add(removeOldest());
/*     */       }
/*     */       
/* 254 */       return values;
/*     */     } finally {
/* 256 */       this.rwLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*     */     try {
/* 267 */       this.rwLock.readLock().lock();
/* 268 */       return this.timestampedPriorityQueue.isEmpty();
/*     */     } finally {
/* 270 */       this.rwLock.readLock().unlock();
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
/*     */   public int size() {
/*     */     try {
/* 283 */       this.rwLock.readLock().lock();
/* 284 */       return this.timestampedPriorityQueue.size();
/*     */     } finally {
/* 286 */       this.rwLock.readLock().unlock();
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
/*     */   public long getOldestRegistrationTimestamp() {
/*     */     try {
/* 301 */       this.rwLock.readLock().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 310 */       this.rwLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromQueue(TimestampedRegistration<K, V> tr) {
/* 315 */     Iterator<TimestampedRegistration<K, V>> it = this.timestampedPriorityQueue.iterator();
/* 316 */     while (it.hasNext()) {
/* 317 */       if (it.next() == tr) {
/*     */         
/* 319 */         it.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\TimestampedCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */