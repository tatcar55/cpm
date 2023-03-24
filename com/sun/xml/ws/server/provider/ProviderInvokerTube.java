/*    */ package com.sun.xml.ws.server.provider;
/*    */ 
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import com.sun.xml.ws.api.server.Invoker;
/*    */ import com.sun.xml.ws.api.server.ProviderInvokerTubeFactory;
/*    */ import com.sun.xml.ws.binding.SOAPBindingImpl;
/*    */ import com.sun.xml.ws.server.InvokerTube;
/*    */ import javax.xml.ws.Provider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ProviderInvokerTube<T>
/*    */   extends InvokerTube<Provider<T>>
/*    */ {
/*    */   protected ProviderArgumentsBuilder<T> argsBuilder;
/*    */   
/*    */   ProviderInvokerTube(Invoker invoker, ProviderArgumentsBuilder<T> argsBuilder) {
/* 64 */     super(invoker);
/* 65 */     this.argsBuilder = argsBuilder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> ProviderInvokerTube<T> create(Class<T> implType, WSBinding binding, Invoker invoker, Container container) {
/* 71 */     ProviderEndpointModel<T> model = new ProviderEndpointModel<T>(implType, binding);
/* 72 */     ProviderArgumentsBuilder<?> argsBuilder = ProviderArgumentsBuilder.create(model, binding);
/* 73 */     if (binding instanceof SOAPBindingImpl)
/*    */     {
/* 75 */       ((SOAPBindingImpl)binding).setMode(model.mode);
/*    */     }
/*    */     
/* 78 */     return ProviderInvokerTubeFactory.create(null, container, implType, invoker, argsBuilder, model.isAsync);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\ProviderInvokerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */