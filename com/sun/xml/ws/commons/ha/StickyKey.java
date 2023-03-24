/*    */ package com.sun.xml.ws.commons.ha;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.glassfish.ha.store.api.HashableKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StickyKey
/*    */   implements HashableKey, Serializable
/*    */ {
/*    */   final Serializable key;
/*    */   private final String hashKey;
/*    */   
/*    */   public StickyKey(Serializable key, String hashKey) {
/* 56 */     this.key = key;
/* 57 */     this.hashKey = hashKey;
/*    */   }
/*    */   
/*    */   public StickyKey(Serializable key) {
/* 61 */     this.key = key;
/* 62 */     this.hashKey = "HASHABLE_KEY_" + String.valueOf(key.hashCode());
/*    */   }
/*    */   
/*    */   public String getHashKey() {
/* 66 */     return this.hashKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 71 */     if (that == null) {
/* 72 */       return false;
/*    */     }
/* 74 */     if (getClass() != that.getClass()) {
/* 75 */       return false;
/*    */     }
/* 77 */     return this.key.equals(((StickyKey)that).key);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 82 */     return this.key.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return "StickyKey{key=" + this.key + ", hashKey=" + this.hashKey + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\ha\StickyKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */