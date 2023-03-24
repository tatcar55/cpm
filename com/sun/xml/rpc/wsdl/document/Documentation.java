/*    */ package com.sun.xml.rpc.wsdl.document;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Documentation
/*    */ {
/*    */   private String content;
/*    */   
/*    */   public Documentation(String s) {
/* 37 */     this.content = s;
/*    */   }
/*    */   
/*    */   public String getContent() {
/* 41 */     return this.content;
/*    */   }
/*    */   
/*    */   public void setContent(String s) {
/* 45 */     this.content = s;
/*    */   }
/*    */   
/*    */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 49 */     visitor.visit(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Documentation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */