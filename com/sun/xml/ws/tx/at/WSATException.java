/*    */ package com.sun.xml.ws.tx.at;
/*    */ 
/*    */ import javax.transaction.xa.XAException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSATException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 8473938065230309413L;
/*    */   public int errorCode;
/*    */   
/*    */   public WSATException(String s) {
/* 55 */     super(s);
/*    */   }
/*    */   
/*    */   public WSATException(Exception ex) {
/* 59 */     super(ex);
/*    */   }
/*    */   
/*    */   public WSATException(String s, XAException xae) {
/* 63 */     super(s, xae);
/* 64 */     this.errorCode = xae.errorCode;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\WSATException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */