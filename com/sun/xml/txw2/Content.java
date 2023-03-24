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
/*    */ 
/*    */ 
/*    */ abstract class Content
/*    */ {
/*    */   private Content next;
/*    */   
/*    */   final Content getNext() {
/* 53 */     return this.next;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final void setNext(Document doc, Content next) {
/* 65 */     assert next != null;
/* 66 */     assert this.next == null : "next of " + this + " is already set to " + this.next;
/* 67 */     this.next = next;
/* 68 */     doc.run();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isReadyToCommit() {
/* 75 */     return true;
/*    */   }
/*    */   
/*    */   abstract boolean concludesPendingStartTag();
/*    */   
/*    */   abstract void accept(ContentVisitor paramContentVisitor);
/*    */   
/*    */   public void written() {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\Content.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */