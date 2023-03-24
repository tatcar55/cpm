/*    */ package com.sun.xml.ws.handler;
/*    */ 
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.message.Messages;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.ProtocolException;
/*    */ import javax.xml.ws.handler.Handler;
/*    */ import javax.xml.ws.http.HTTPException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class XMLHandlerProcessor<C extends MessageUpdatableContext>
/*    */   extends HandlerProcessor<C>
/*    */ {
/*    */   public XMLHandlerProcessor(HandlerTube owner, WSBinding binding, List<? extends Handler> chain) {
/* 68 */     super(owner, binding, chain);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final void insertFaultMessage(C context, ProtocolException exception) {
/* 77 */     if (exception instanceof HTTPException) {
/* 78 */       context.put("javax.xml.ws.http.response.code", Integer.valueOf(((HTTPException)exception).getStatusCode()));
/*    */     }
/* 80 */     if (context != null)
/*    */     {
/* 82 */       context.setPacketMessage(Messages.createEmpty(this.binding.getSOAPVersion()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\XMLHandlerProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */