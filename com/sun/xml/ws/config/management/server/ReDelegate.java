/*     */ package com.sun.xml.ws.config.management.server;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.config.management.EndpointCreationAttributes;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.config.management.ManagementMessages;
/*     */ import com.sun.xml.ws.metro.api.config.management.ManagedEndpoint;
/*     */ import com.sun.xml.ws.server.EndpointFactory;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReDelegate
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(ReDelegate.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void recreate(ManagedEndpoint<T> managedEndpoint, WebServiceFeature... features) {
/*     */     try {
/*  77 */       WSEndpoint<T> delegate = recreateEndpoint(managedEndpoint, features);
/*  78 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  79 */         LOGGER.fine(ManagementMessages.WSM_5092_NEW_ENDPOINT_DELEGATE(delegate));
/*     */       }
/*  81 */       managedEndpoint.swapEndpointDelegate(delegate);
/*     */     }
/*  83 */     catch (Throwable e) {
/*  84 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_5091_ENDPOINT_CREATION_FAILED(), e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> WSEndpoint<T> recreateEndpoint(ManagedEndpoint<T> endpoint, WebServiceFeature... features) {
/*  92 */     endpoint.closeManagedObjectManager();
/*     */     
/*  94 */     EndpointCreationAttributes creationAttributes = endpoint.getCreationAttributes();
/*  95 */     BindingImpl bindingImpl = BindingImpl.create(endpoint.getBinding().getBindingId(), features);
/*     */     
/*  97 */     WSEndpoint<T> result = EndpointFactory.createEndpoint(endpoint.getImplementationClass(), creationAttributes.isProcessHandlerAnnotation(), creationAttributes.getInvoker(), endpoint.getServiceName(), endpoint.getPortName(), endpoint.getContainer(), (WSBinding)bindingImpl, null, null, creationAttributes.getEntityResolver(), creationAttributes.isTransportSynchronous());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     result.getComponentRegistry().addAll(endpoint.getComponentRegistry());
/*     */     
/* 110 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\management\server\ReDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */