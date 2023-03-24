/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class IdentityHashSet
/*     */ {
/*     */   private int count;
/*  36 */   private Object[] table = new Object[191];
/*  37 */   private int threshold = 57;
/*     */   private static final float loadFactor = 0.3F;
/*     */   
/*     */   public boolean contains(Object key) {
/*  41 */     Object[] tab = this.table;
/*  42 */     int index = (System.identityHashCode(key) & Integer.MAX_VALUE) % tab.length;
/*     */     
/*     */     while (true) {
/*  45 */       Object e = tab[index];
/*  46 */       if (e == null)
/*  47 */         return false; 
/*  48 */       if (e == key)
/*  49 */         return true; 
/*  50 */       index = (index + 1) % tab.length;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int initialCapacity = 191;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rehash() {
/*  64 */     int oldCapacity = this.table.length;
/*  65 */     Object[] oldMap = this.table;
/*     */     
/*  67 */     int newCapacity = oldCapacity * 2 + 1;
/*  68 */     Object[] newMap = new Object[newCapacity];
/*     */     
/*  70 */     for (int i = oldCapacity; i-- > 0;) {
/*  71 */       if (oldMap[i] != null) {
/*  72 */         int index = (System.identityHashCode(oldMap[i]) & Integer.MAX_VALUE) % newMap.length;
/*  73 */         while (newMap[index] != null)
/*  74 */           index = (index + 1) % newMap.length; 
/*  75 */         newMap[index] = oldMap[i];
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     this.threshold = (int)(newCapacity * 0.3F);
/*     */     
/*  81 */     this.table = newMap;
/*     */   }
/*     */   
/*     */   public boolean add(Object newObj) {
/*  85 */     if (this.count >= this.threshold) {
/*  86 */       rehash();
/*     */     }
/*  88 */     Object[] tab = this.table;
/*  89 */     int index = (System.identityHashCode(newObj) & Integer.MAX_VALUE) % tab.length;
/*     */     
/*     */     Object existing;
/*     */     
/*  93 */     while ((existing = tab[index]) != null) {
/*  94 */       if (existing == newObj) return false; 
/*  95 */       index = (index + 1) % tab.length;
/*     */     } 
/*  97 */     tab[index] = newObj;
/*     */     
/*  99 */     this.count++;
/*     */     
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\IdentityHashSet.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */