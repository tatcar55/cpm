/*    */ package com.sun.xml.ws.client.dispatch;
/*    */ 
/*    */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*    */ import com.sun.xml.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.binding.BindingImpl;
/*    */ import com.sun.xml.ws.client.WSServiceDelegate;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageDispatch
/*    */   extends DispatchImpl<Message>
/*    */ {
/*    */   @Deprecated
/*    */   public MessageDispatch(QName port, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 65 */     super(port, Service.Mode.MESSAGE, service, pipe, binding, epr);
/*    */   }
/*    */   
/*    */   public MessageDispatch(WSPortInfo portInfo, BindingImpl binding, WSEndpointReference epr) {
/* 69 */     super(portInfo, Service.Mode.MESSAGE, binding, epr, true);
/*    */   }
/*    */ 
/*    */   
/*    */   Message toReturnValue(Packet response) {
/* 74 */     return response.getMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   Packet createPacket(Message msg) {
/* 79 */     return new Packet(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\MessageDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */