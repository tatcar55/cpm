/*     */ package com.sun.xml.rpc.server;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamingHandlerState
/*     */ {
/*     */   private SOAPMessageContext _context;
/*     */   private InternalSOAPMessage _request;
/*     */   private InternalSOAPMessage _response;
/*     */   public static final int CALL_NO_HANDLERS = -1;
/*     */   public static final int CALL_FAULT_HANDLERS = 0;
/*     */   public static final int CALL_RESPONSE_HANDLERS = 1;
/*     */   int handlerFlag;
/*     */   
/*     */   public StreamingHandlerState(SOAPMessageContext context) {
/*  99 */     this.handlerFlag = 1; this._context = context; this._request = new InternalSOAPMessage(this._context.getMessage()); this._response = null;
/*     */   }
/*     */   public boolean isFastInfoset() { return this._context.isFastInfoset(); }
/* 102 */   public boolean acceptFastInfoset() { return this._context.acceptFastInfoset(); } public SOAPMessageContext getMessageContext() { return this._context; } public boolean isFailure() { if (this._response == null) return false;  return this._response.isFailure(); } public void setHandlerFlag(int handlerFlag) { this.handlerFlag = handlerFlag; }
/*     */   public InternalSOAPMessage getRequest() { return this._request; }
/*     */   public InternalSOAPMessage getResponse() { if (this._response == null) this._response = new InternalSOAPMessage(this._context.createMessage(this._context.acceptFastInfoset(), this._context.acceptFastInfoset()));  return this._response; }
/*     */   public void setResponse(InternalSOAPMessage msg) { this._response = msg; }
/* 106 */   public InternalSOAPMessage resetResponse() { this._response = null; return getResponse(); } public int getHandlerFlag() { return this.handlerFlag; }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\StreamingHandlerState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */