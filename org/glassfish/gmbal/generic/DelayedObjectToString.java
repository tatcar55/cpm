/*    */ package org.glassfish.gmbal.generic;
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
/*    */ public class DelayedObjectToString
/*    */ {
/*    */   private Object obj;
/*    */   private ObjectUtility ou;
/*    */   
/*    */   public DelayedObjectToString(Object obj, ObjectUtility ou) {
/* 51 */     this.obj = obj;
/* 52 */     this.ou = ou;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return this.ou.objectToString(this.obj);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\DelayedObjectToString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */