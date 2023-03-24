/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.handler.HandlerChainsModel;
/*     */ import com.sun.xml.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.ws.util.HandlerAnnotationProcessor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.HandlerResolver;
/*     */ import javax.xml.ws.handler.PortInfo;
/*     */ import javax.xml.ws.soap.SOAPBinding;
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
/*     */ abstract class HandlerConfigurator
/*     */ {
/*     */   abstract void configureHandlers(@NotNull WSPortInfo paramWSPortInfo, @NotNull BindingImpl paramBindingImpl);
/*     */   
/*     */   abstract HandlerResolver getResolver();
/*     */   
/*     */   static final class HandlerResolverImpl
/*     */     extends HandlerConfigurator
/*     */   {
/*     */     @Nullable
/*     */     private final HandlerResolver resolver;
/*     */     
/*     */     public HandlerResolverImpl(HandlerResolver resolver) {
/*  96 */       this.resolver = resolver;
/*     */     }
/*     */ 
/*     */     
/*     */     void configureHandlers(@NotNull WSPortInfo port, @NotNull BindingImpl binding) {
/* 101 */       if (this.resolver != null) {
/* 102 */         binding.setHandlerChain(this.resolver.getHandlerChain((PortInfo)port));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     HandlerResolver getResolver() {
/* 109 */       return this.resolver;
/*     */     }
/*     */   }
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
/*     */   static final class AnnotationConfigurator
/*     */     extends HandlerConfigurator
/*     */   {
/*     */     private final HandlerChainsModel handlerModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     private final Map<WSPortInfo, HandlerAnnotationInfo> chainMap = new HashMap<WSPortInfo, HandlerAnnotationInfo>();
/* 135 */     private static final Logger logger = Logger.getLogger("com.sun.xml.ws.handler");
/*     */ 
/*     */     
/*     */     AnnotationConfigurator(WSServiceDelegate delegate) {
/* 139 */       this.handlerModel = HandlerAnnotationProcessor.buildHandlerChainsModel(delegate.getServiceClass());
/* 140 */       assert this.handlerModel != null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void configureHandlers(WSPortInfo port, BindingImpl binding) {
/* 146 */       HandlerAnnotationInfo chain = this.chainMap.get(port);
/*     */       
/* 148 */       if (chain == null) {
/* 149 */         logGetChain(port);
/*     */         
/* 151 */         chain = this.handlerModel.getHandlersForPortInfo((PortInfo)port);
/* 152 */         this.chainMap.put(port, chain);
/*     */       } 
/*     */       
/* 155 */       if (binding instanceof SOAPBinding) {
/* 156 */         ((SOAPBinding)binding).setRoles(chain.getRoles());
/*     */       }
/*     */       
/* 159 */       logSetChain(port, chain);
/* 160 */       binding.setHandlerChain(chain.getHandlers());
/*     */     }
/*     */     
/*     */     HandlerResolver getResolver() {
/* 164 */       return new HandlerResolver() {
/*     */           public List<Handler> getHandlerChain(PortInfo portInfo) {
/* 166 */             return new ArrayList<Handler>(HandlerConfigurator.AnnotationConfigurator.this.handlerModel.getHandlersForPortInfo(portInfo).getHandlers());
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     private void logSetChain(WSPortInfo info, HandlerAnnotationInfo chain) {
/* 173 */       logger.finer("Setting chain of length " + chain.getHandlers().size() + " for port info");
/*     */       
/* 175 */       logPortInfo(info, Level.FINER);
/*     */     }
/*     */ 
/*     */     
/*     */     private void logGetChain(WSPortInfo info) {
/* 180 */       logger.fine("No handler chain found for port info:");
/* 181 */       logPortInfo(info, Level.FINE);
/* 182 */       logger.fine("Existing handler chains:");
/* 183 */       if (this.chainMap.isEmpty()) {
/* 184 */         logger.fine("none");
/*     */       } else {
/* 186 */         for (WSPortInfo key : this.chainMap.keySet()) {
/* 187 */           logger.fine(((HandlerAnnotationInfo)this.chainMap.get(key)).getHandlers().size() + " handlers for port info ");
/*     */           
/* 189 */           logPortInfo(key, Level.FINE);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void logPortInfo(WSPortInfo info, Level level) {
/* 195 */       logger.log(level, "binding: " + info.getBindingID() + "\nservice: " + info.getServiceName() + "\nport: " + info.getPortName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\HandlerConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */