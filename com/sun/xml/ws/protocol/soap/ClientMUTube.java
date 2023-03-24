/*    */ package com.sun.xml.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*    */ import com.sun.xml.ws.client.HandlerConfiguration;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientMUTube
/*    */   extends MUTube
/*    */ {
/*    */   public ClientMUTube(WSBinding binding, Tube next) {
/* 63 */     super(binding, next);
/*    */   }
/*    */   
/*    */   protected ClientMUTube(ClientMUTube that, TubeCloner cloner) {
/* 67 */     super(that, cloner);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public NextAction processResponse(Packet response) {
/* 81 */     if (response.getMessage() == null) {
/* 82 */       return super.processResponse(response);
/*    */     }
/* 84 */     HandlerConfiguration handlerConfig = response.handlerConfig;
/*    */     
/* 86 */     if (handlerConfig == null)
/*    */     {
/*    */       
/* 89 */       handlerConfig = this.binding.getHandlerConfig();
/*    */     }
/* 91 */     Set<QName> misUnderstoodHeaders = getMisUnderstoodHeaders(response.getMessage().getMessageHeaders(), handlerConfig.getRoles(), this.binding.getKnownHeaders());
/* 92 */     if (misUnderstoodHeaders == null || misUnderstoodHeaders.isEmpty()) {
/* 93 */       return super.processResponse(response);
/*    */     }
/* 95 */     throw createMUSOAPFaultException(misUnderstoodHeaders);
/*    */   }
/*    */   
/*    */   public ClientMUTube copy(TubeCloner cloner) {
/* 99 */     return new ClientMUTube(this, cloner);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\protocol\soap\ClientMUTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */