/*    */ package com.sun.xml.ws.addressing.model;
/*    */ 
/*    */ import com.sun.xml.ws.resources.AddressingMessages;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionNotSupportedException
/*    */   extends WebServiceException
/*    */ {
/*    */   private String action;
/*    */   
/*    */   public ActionNotSupportedException(String action) {
/* 54 */     super(AddressingMessages.ACTION_NOT_SUPPORTED_EXCEPTION(action));
/* 55 */     this.action = action;
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 59 */     return this.action;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\model\ActionNotSupportedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */