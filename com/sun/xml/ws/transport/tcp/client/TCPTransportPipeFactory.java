/*    */ package com.sun.xml.ws.transport.tcp.client;
/*    */ 
/*    */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*    */ import com.sun.xml.ws.api.pipe.TransportTubeFactory;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.transport.tcp.servicechannel.stubs.ServiceChannelWSImplService;
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
/*    */ public class TCPTransportPipeFactory
/*    */   extends TransportTubeFactory
/*    */ {
/* 54 */   private static final QName serviceChannelServiceName = (new ServiceChannelWSImplService()).getServiceName();
/*    */ 
/*    */   
/*    */   public Tube doCreate(ClientTubeAssemblerContext context) {
/* 58 */     if (!"vnd.sun.ws.tcp".equalsIgnoreCase(context.getAddress().getURI().getScheme())) {
/* 59 */       return null;
/*    */     }
/*    */     
/* 62 */     if (context.getService().getServiceName().equals(serviceChannelServiceName)) {
/* 63 */       return (Tube)new ServiceChannelTransportPipe(context);
/*    */     }
/*    */     
/* 66 */     return (Tube)new TCPTransportPipe(context);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\client\TCPTransportPipeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */