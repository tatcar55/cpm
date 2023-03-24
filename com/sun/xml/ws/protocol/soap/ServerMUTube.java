/*    */ package com.sun.xml.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.pipe.NextAction;
/*    */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
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
/*    */ public class ServerMUTube
/*    */   extends MUTube
/*    */ {
/*    */   private ServerTubeAssemblerContext tubeContext;
/*    */   private final Set<String> roles;
/*    */   private final Set<QName> handlerKnownHeaders;
/*    */   
/*    */   public ServerMUTube(ServerTubeAssemblerContext tubeContext, Tube next) {
/* 60 */     super(tubeContext.getEndpoint().getBinding(), next);
/*    */     
/* 62 */     this.tubeContext = tubeContext;
/*    */ 
/*    */     
/* 65 */     HandlerConfiguration handlerConfig = this.binding.getHandlerConfig();
/* 66 */     this.roles = handlerConfig.getRoles();
/* 67 */     this.handlerKnownHeaders = this.binding.getKnownHeaders();
/*    */   }
/*    */   
/*    */   protected ServerMUTube(ServerMUTube that, TubeCloner cloner) {
/* 71 */     super(that, cloner);
/* 72 */     this.tubeContext = that.tubeContext;
/* 73 */     this.roles = that.roles;
/* 74 */     this.handlerKnownHeaders = that.handlerKnownHeaders;
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
/*    */   public NextAction processRequest(Packet request) {
/* 87 */     Set<QName> misUnderstoodHeaders = getMisUnderstoodHeaders(request.getMessage().getMessageHeaders(), this.roles, this.handlerKnownHeaders);
/* 88 */     if (misUnderstoodHeaders == null || misUnderstoodHeaders.isEmpty()) {
/* 89 */       return doInvoke(this.next, request);
/*    */     }
/* 91 */     return doReturnWith(request.createServerResponse(createMUSOAPFaultMessage(misUnderstoodHeaders), this.tubeContext.getWsdlModel(), this.tubeContext.getSEIModel(), this.tubeContext.getEndpoint().getBinding()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ServerMUTube copy(TubeCloner cloner) {
/* 96 */     return new ServerMUTube(this, cloner);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\protocol\soap\ServerMUTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */