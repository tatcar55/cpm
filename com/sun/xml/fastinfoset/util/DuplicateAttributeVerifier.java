/*     */ package com.sun.xml.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DuplicateAttributeVerifier
/*     */ {
/*     */   public static final int MAP_SIZE = 256;
/*     */   public int _currentIteration;
/*     */   private Entry[] _map;
/*     */   public final Entry _poolHead;
/*     */   public Entry _poolCurrent;
/*     */   private Entry _poolTail;
/*     */   
/*     */   public static class Entry
/*     */   {
/*     */     private int iteration;
/*     */     private int value;
/*     */     private Entry hashNext;
/*     */     private Entry poolNext;
/*     */   }
/*     */   
/*     */   public DuplicateAttributeVerifier() {
/*  46 */     this._poolTail = this._poolHead = new Entry();
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  50 */     this._currentIteration = 0;
/*     */     
/*  52 */     Entry e = this._poolHead;
/*  53 */     while (e != null) {
/*  54 */       e.iteration = 0;
/*  55 */       e = e.poolNext;
/*     */     } 
/*     */     
/*  58 */     reset();
/*     */   }
/*     */   
/*     */   public final void reset() {
/*  62 */     this._poolCurrent = this._poolHead;
/*  63 */     if (this._map == null) {
/*  64 */       this._map = new Entry[256];
/*     */     }
/*     */   }
/*     */   
/*     */   private final void increasePool(int capacity) {
/*  69 */     if (this._map == null) {
/*  70 */       this._map = new Entry[256];
/*  71 */       this._poolCurrent = this._poolHead;
/*     */     } else {
/*  73 */       Entry tail = this._poolTail;
/*  74 */       for (int i = 0; i < capacity; i++) {
/*  75 */         Entry e = new Entry();
/*  76 */         this._poolTail.poolNext = e;
/*  77 */         this._poolTail = e;
/*     */       } 
/*     */       
/*  80 */       this._poolCurrent = tail.poolNext;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void checkForDuplicateAttribute(int hash, int value) throws FastInfosetException {
/*  85 */     if (this._poolCurrent == null) {
/*  86 */       increasePool(16);
/*     */     }
/*     */ 
/*     */     
/*  90 */     Entry newEntry = this._poolCurrent;
/*  91 */     this._poolCurrent = this._poolCurrent.poolNext;
/*     */     
/*  93 */     Entry head = this._map[hash];
/*  94 */     if (head == null || head.iteration < this._currentIteration) {
/*  95 */       newEntry.hashNext = null;
/*  96 */       this._map[hash] = newEntry;
/*  97 */       newEntry.iteration = this._currentIteration;
/*  98 */       newEntry.value = value;
/*     */     } else {
/* 100 */       Entry e = head;
/*     */       do {
/* 102 */         if (e.value == value) {
/* 103 */           reset();
/* 104 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.duplicateAttribute"));
/*     */         } 
/* 106 */       } while ((e = e.hashNext) != null);
/*     */       
/* 108 */       newEntry.hashNext = head;
/* 109 */       this._map[hash] = newEntry;
/* 110 */       newEntry.iteration = this._currentIteration;
/* 111 */       newEntry.value = value;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\DuplicateAttributeVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */