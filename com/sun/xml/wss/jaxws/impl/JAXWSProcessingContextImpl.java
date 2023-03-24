/*    */ package com.sun.xml.wss.jaxws.impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JAXWSProcessingContextImpl
/*    */   extends FilterProcessingContext
/*    */   implements JAXWSProcessingContext
/*    */ {
/*    */   private Message _message;
/*    */   
/*    */   public void setMessage(Message message) {
/* 68 */     this._message = message;
/*    */   }
/*    */   
/*    */   public Message getMessage() {
/* 72 */     return this._message;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\JAXWSProcessingContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */