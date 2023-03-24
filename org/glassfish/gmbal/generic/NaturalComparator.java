/*    */ package org.glassfish.gmbal.generic;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
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
/*    */ public class NaturalComparator<T>
/*    */   implements Serializable, Comparator<T>
/*    */ {
/*    */   private static final long serialVersionUID = -6702229623606444679L;
/*    */   
/*    */   public int compare(T obj1, T obj2) {
/* 50 */     return ((Comparable<T>)obj1).compareTo(obj2);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\NaturalComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */