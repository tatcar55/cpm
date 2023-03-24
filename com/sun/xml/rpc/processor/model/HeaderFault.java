/*    */ package com.sun.xml.rpc.processor.model;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeaderFault
/*    */   extends Fault
/*    */ {
/*    */   private QName _message;
/*    */   private String _part;
/*    */   
/*    */   public HeaderFault() {}
/*    */   
/*    */   public HeaderFault(String name) {
/* 36 */     super(name);
/*    */   }
/*    */   
/*    */   public QName getMessage() {
/* 40 */     return this._message;
/*    */   }
/*    */   
/*    */   public void setMessage(QName message) {
/* 44 */     this._message = message;
/*    */   }
/*    */   
/*    */   public String getPart() {
/* 48 */     return this._part;
/*    */   }
/*    */   
/*    */   public void setPart(String part) {
/* 52 */     this._part = part;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\HeaderFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */