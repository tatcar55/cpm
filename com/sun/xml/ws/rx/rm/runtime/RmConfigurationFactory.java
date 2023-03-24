/*    */ package com.sun.xml.ws.rx.rm.runtime;
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
/*    */ 
/*    */ public enum RmConfigurationFactory
/*    */ {
/* 59 */   INSTANCE;
/*    */   
/*    */   public RmConfiguration createInstance(ServerTubelineAssemblyContext context) {
/* 62 */     return createInstance(context.getWsdlPort(), context.getEndpoint().getBinding(), context.getWrappedContext().getEndpoint().getManagedObjectManager());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RmConfiguration createInstance(ClientTubelineAssemblyContext context) {
/* 69 */     return createInstance(context.getWsdlPort(), context.getBinding(), context.getWrappedContext().getBindingProvider().getManagedObjectManager());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private RmConfiguration createInstance(WSDLPort wsdlPort, WSBinding binding, ManagedObjectManager managedObjectManager) {
/* 77 */     return new RmConfigurationImpl((ReliableMessagingFeature)binding.getFeature(ReliableMessagingFeature.class), (MakeConnectionSupportedFeature)binding.getFeature(MakeConnectionSupportedFeature.class), binding.getSOAPVersion(), binding.getAddressingVersion(), PortUtilities.checkForRequestResponseOperations(wsdlPort), managedObjectManager, HighAvailabilityProvider.INSTANCE);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RmConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */