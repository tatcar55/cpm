/*    */ package com.sun.xml.txw2;
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
/*    */ final class StartDocument
/*    */   extends Content
/*    */ {
/*    */   boolean concludesPendingStartTag() {
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   void accept(ContentVisitor visitor) {
/* 52 */     visitor.onStartDocument();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\StartDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */