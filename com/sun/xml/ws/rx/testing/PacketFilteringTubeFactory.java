/*    */ package com.sun.xml.ws.rx.testing;
/*    */ 
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RmConfiguration;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RmConfigurationFactory;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PacketFilteringTubeFactory
/*    */   implements TubeFactory
/*    */ {
/*    */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 59 */     if (isPacketFilteringEnabled(context.getBinding())) {
/* 60 */       RmConfiguration configuration = RmConfigurationFactory.INSTANCE.createInstance(context);
/*    */       
/* 62 */       return (Tube)new PacketFilteringTube(configuration, context.getTubelineHead(), context);
/*    */     } 
/* 64 */     return context.getTubelineHead();
/*    */   }
/*    */ 
/*    */   
/*    */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 69 */     if (isPacketFilteringEnabled(context.getEndpoint().getBinding())) {
/* 70 */       RmConfiguration configuration = RmConfigurationFactory.INSTANCE.createInstance(context);
/*    */       
/* 72 */       return (Tube)new PacketFilteringTube(configuration, context.getTubelineHead(), context);
/*    */     } 
/* 74 */     return context.getTubelineHead();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isPacketFilteringEnabled(WSBinding binding) {
/* 79 */     PacketFilteringFeature pfFeature = (PacketFilteringFeature)binding.getFeature(PacketFilteringFeature.class);
/* 80 */     return (pfFeature != null && pfFeature.isEnabled() && pfFeature.hasFilters());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\testing\PacketFilteringTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */