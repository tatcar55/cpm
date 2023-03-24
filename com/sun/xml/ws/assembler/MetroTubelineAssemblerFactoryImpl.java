/*    */ package com.sun.xml.ws.assembler;
/*    */ 
/*    */ import com.sun.xml.ws.api.BindingID;
/*    */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*    */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*    */ import com.sun.xml.ws.api.pipe.TubelineAssembler;
/*    */ import com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
/*    */ import com.sun.xml.ws.api.server.SDDocumentFilter;
/*    */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*    */ import com.sun.xml.ws.runtime.WsdlDocumentFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MetroTubelineAssemblerFactoryImpl
/*    */   extends TubelineAssemblerFactory
/*    */ {
/* 58 */   static final MetroConfigNameImpl METRO_TUBES_CONFIG_NAME = new MetroConfigNameImpl("metro-default.xml", "metro.xml");
/*    */ 
/*    */ 
/*    */   
/*    */   public TubelineAssembler doCreate(BindingID bindingId) {
/* 63 */     return new MetroTubelineAssembler(bindingId, METRO_TUBES_CONFIG_NAME)
/*    */       {
/*    */         protected DefaultServerTubelineAssemblyContext createServerContext(ServerTubeAssemblerContext jaxwsContext)
/*    */         {
/* 67 */           DefaultServerTubelineAssemblyContext context = super.createServerContext(jaxwsContext);
/*    */           
/* 69 */           ServiceDefinition sd = context.getEndpoint().getServiceDefinition();
/* 70 */           if (sd != null) {
/* 71 */             sd.addFilter((SDDocumentFilter)new WsdlDocumentFilter());
/*    */           }
/* 73 */           return context;
/*    */         }
/*    */ 
/*    */         
/*    */         protected MetroClientTubelineAssemblyContextImpl createClientContext(ClientTubeAssemblerContext jaxwsContext) {
/* 78 */           return new MetroClientTubelineAssemblyContextImpl(jaxwsContext);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\MetroTubelineAssemblerFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */