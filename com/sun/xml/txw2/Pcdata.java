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
/*    */ 
/*    */ 
/*    */ final class Pcdata
/*    */   extends Text
/*    */ {
/*    */   Pcdata(Document document, NamespaceResolver nsResolver, Object obj) {
/* 50 */     super(document, nsResolver, obj);
/*    */   }
/*    */   
/*    */   void accept(ContentVisitor visitor) {
/* 54 */     visitor.onPcdata(this.buffer);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\Pcdata.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */