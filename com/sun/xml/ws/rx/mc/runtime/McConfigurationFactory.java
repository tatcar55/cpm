/*    */ package com.sun.xml.ws.rx.mc.runtime;
/*    */ 
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.rx.mc.api.MakeConnectionSupportedFeature;
/*    */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;
/*    */ import com.sun.xml.ws.rx.util.PortUtilities;
/*    */ import org.glassfish.gmbal.ManagedObjectManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum McConfigurationFactory
/*    */ {
/* 58 */   INSTANCE;
/*    */   
/*    */   public McConfiguration createInstance(ServerTubelineAssemblyContext context) {
/* 61 */     String uniqueEndpointId = context.getEndpoint().getServiceName() + "::" + context.getEndpoint().getPortName();
/* 62 */     return createInstance(uniqueEndpointId, context.getWsdlPort(), context.getEndpoint().getBinding(), context.getWrappedContext().getEndpoint().getManagedObjectManager(), HighAvailabilityProvider.INSTANCE);
/*    */   }
/*    */   
/*    */   public McConfiguration createInstance(ClientTubelineAssemblyContext context) {
/* 66 */     return createInstance(context.getAddress().getURI().toString(), context.getWsdlPort(), context.getBinding(), context.getWrappedContext().getBindingProvider().getManagedObjectManager(), HighAvailabilityProvider.INSTANCE);
/*    */   }
/*    */ 
/*    */   
/*    */   private McConfiguration createInstance(String uniqueEndpointId, WSDLPort wsdlPort, WSBinding binding, ManagedObjectManager managedObjectManager, HighAvailabilityProvider haProvider) {
/* 71 */     return new McConfigurationImpl((ReliableMessagingFeature)binding.getFeature(ReliableMessagingFeature.class), (MakeConnectionSupportedFeature)binding.getFeature(MakeConnectionSupportedFeature.class), uniqueEndpointId, binding.getSOAPVersion(), binding.getAddressingVersion(), PortUtilities.checkForRequestResponseOperations(wsdlPort), managedObjectManager, haProvider);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */