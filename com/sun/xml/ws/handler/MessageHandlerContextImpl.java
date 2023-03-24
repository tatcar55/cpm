/*    */ package com.sun.xml.ws.handler;
/*    */ 
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.handler.MessageHandlerContext;
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.model.SEIModel;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageHandlerContextImpl
/*    */   extends MessageUpdatableContext
/*    */   implements MessageHandlerContext
/*    */ {
/*    */   @Nullable
/*    */   private SEIModel seiModel;
/*    */   private Set<String> roles;
/*    */   private WSBinding binding;
/*    */   @Nullable
/*    */   private WSDLPort wsdlModel;
/*    */   
/*    */   public MessageHandlerContextImpl(@Nullable SEIModel seiModel, WSBinding binding, @Nullable WSDLPort wsdlModel, Packet packet, Set<String> roles) {
/* 63 */     super(packet);
/* 64 */     this.seiModel = seiModel;
/* 65 */     this.binding = binding;
/* 66 */     this.wsdlModel = wsdlModel;
/* 67 */     this.roles = roles;
/*    */   }
/*    */   public Message getMessage() {
/* 70 */     return this.packet.getMessage();
/*    */   }
/*    */   
/*    */   public void setMessage(Message message) {
/* 74 */     this.packet.setMessage(message);
/*    */   }
/*    */   
/*    */   public Set<String> getRoles() {
/* 78 */     return this.roles;
/*    */   }
/*    */   
/*    */   public WSBinding getWSBinding() {
/* 82 */     return this.binding;
/*    */   }
/*    */   @Nullable
/*    */   public SEIModel getSEIModel() {
/* 86 */     return this.seiModel;
/*    */   }
/*    */   @Nullable
/*    */   public WSDLPort getPort() {
/* 90 */     return this.wsdlModel;
/*    */   }
/*    */ 
/*    */   
/*    */   void updateMessage() {}
/*    */ 
/*    */   
/*    */   void setPacketMessage(Message newMessage) {
/* 98 */     setMessage(newMessage);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\MessageHandlerContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */