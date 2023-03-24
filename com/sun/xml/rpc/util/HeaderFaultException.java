/*    */ package com.sun.xml.rpc.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeaderFaultException
/*    */   extends Exception
/*    */ {
/* 34 */   private Object obj = null;
/*    */   
/*    */   public HeaderFaultException(String msg, Object obj) {
/* 37 */     super(msg);
/* 38 */     this.obj = obj;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 42 */     return this.obj;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\HeaderFaultException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */