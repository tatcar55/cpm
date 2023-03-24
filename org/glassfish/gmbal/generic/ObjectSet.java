/*    */ package org.glassfish.gmbal.generic;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectSet
/*    */ {
/* 56 */   private IdentityHashMap map = new IdentityHashMap<Object, Object>();
/* 57 */   private static Object VALUE = new Object();
/*    */   
/*    */   public boolean contains(Object obj) {
/* 60 */     return (this.map.get(obj) == VALUE);
/*    */   }
/*    */   
/*    */   public void add(Object obj) {
/* 64 */     this.map.put(obj, VALUE);
/*    */   }
/*    */   
/*    */   public void remove(Object obj) {
/* 68 */     this.map.remove(obj);
/*    */   }
/*    */   
/*    */   public Iterator iterator() {
/* 72 */     return this.map.keySet().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\ObjectSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */