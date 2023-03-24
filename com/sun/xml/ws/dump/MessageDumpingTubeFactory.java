/*    */ package com.sun.xml.ws.dump;
/*    */ 
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.TubeFactory;
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
/*    */ public final class MessageDumpingTubeFactory
/*    */   implements TubeFactory
/*    */ {
/*    */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 53 */     MessageDumpingFeature messageDumpingFeature = (MessageDumpingFeature)context.getBinding().getFeature(MessageDumpingFeature.class);
/* 54 */     if (messageDumpingFeature != null) {
/* 55 */       return (Tube)new MessageDumpingTube(context.getTubelineHead(), messageDumpingFeature);
/*    */     }
/*    */     
/* 58 */     return context.getTubelineHead();
/*    */   }
/*    */   
/*    */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 62 */     MessageDumpingFeature messageDumpingFeature = (MessageDumpingFeature)context.getEndpoint().getBinding().getFeature(MessageDumpingFeature.class);
/* 63 */     if (messageDumpingFeature != null) {
/* 64 */       return (Tube)new MessageDumpingTube(context.getTubelineHead(), messageDumpingFeature);
/*    */     }
/*    */     
/* 67 */     return context.getTubelineHead();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\dump\MessageDumpingTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */