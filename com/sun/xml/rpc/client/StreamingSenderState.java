/*    */ package com.sun.xml.rpc.client;
/*    */ 
/*    */ import com.sun.xml.rpc.client.dii.BasicCall;
/*    */ import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
/*    */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*    */ import javax.xml.rpc.handler.HandlerChain;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamingSenderState
/*    */ {
/*    */   private SOAPMessageContext _context;
/*    */   private InternalSOAPMessage _request;
/*    */   private InternalSOAPMessage _response;
/*    */   private HandlerChain _handlerChain;
/*    */   private BasicCall _call;
/*    */   
/*    */   public StreamingSenderState(SOAPMessageContext context, HandlerChain handlerChain, boolean useFastInfoset, boolean acceptFastInfoset) {
/* 46 */     this._context = context;
/* 47 */     this._context.setMessage(this._context.createMessage(useFastInfoset, acceptFastInfoset));
/* 48 */     this._handlerChain = handlerChain;
/*    */   }
/*    */   
/*    */   public SOAPMessageContext getMessageContext() {
/* 52 */     return this._context;
/*    */   }
/*    */   
/*    */   public boolean isFailure() {
/* 56 */     return this._context.isFailure();
/*    */   }
/*    */   
/*    */   public InternalSOAPMessage getRequest() {
/* 60 */     if (this._request == null) {
/* 61 */       this._request = new InternalSOAPMessage(this._context.getMessage());
/*    */     }
/*    */     
/* 64 */     return this._request;
/*    */   }
/*    */   
/*    */   public InternalSOAPMessage getResponse() {
/* 68 */     if (this._response == null) {
/* 69 */       this._response = new InternalSOAPMessage(this._context.getMessage());
/* 70 */       this._response.setOperationCode(getRequest().getOperationCode());
/*    */     } 
/*    */     
/* 73 */     return this._response;
/*    */   }
/*    */   
/*    */   public HandlerChain getHandlerChain() {
/* 77 */     return this._handlerChain;
/*    */   }
/*    */   
/*    */   public BasicCall getCall() {
/* 81 */     return this._call;
/*    */   }
/*    */   
/*    */   public void setCall(BasicCall call) {
/* 85 */     this._call = call;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\StreamingSenderState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */