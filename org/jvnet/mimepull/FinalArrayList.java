/*    */ package org.jvnet.mimepull;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
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
/*    */ final class FinalArrayList<T>
/*    */   extends ArrayList<T>
/*    */ {
/*    */   public FinalArrayList(int initialCapacity) {
/* 56 */     super(initialCapacity);
/*    */   }
/*    */ 
/*    */   
/*    */   public FinalArrayList() {}
/*    */   
/*    */   public FinalArrayList(Collection<? extends T> ts) {
/* 63 */     super(ts);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\FinalArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */