/*    */ package com.sun.xml.rpc.client.dii;
/*    */ 
/*    */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.lang.reflect.Proxy;
/*    */ import java.rmi.Remote;
/*    */ import javax.xml.rpc.ServiceException;
/*    */ import javax.xml.rpc.Stub;
/*    */ import javax.xml.rpc.handler.HandlerRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicProxyBuilder
/*    */ {
/*    */   protected InternalTypeMappingRegistry internalTypeRegistry;
/*    */   protected HandlerRegistry handlerRegistry;
/*    */   protected ServiceInfo configuration;
/*    */   
/*    */   public DynamicProxyBuilder(InternalTypeMappingRegistry internalTypeRegistry, HandlerRegistry handlerRegistry, ServiceInfo configuration) {
/* 53 */     this.internalTypeRegistry = internalTypeRegistry;
/* 54 */     this.handlerRegistry = handlerRegistry;
/* 55 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Remote buildDynamicProxyFor(PortInfo portInfo, Class portInterface) throws ServiceException {
/* 62 */     CallInvocationHandler handler = new CallInvocationHandler(portInterface);
/*    */     
/* 64 */     handler._setProperty("javax.xml.rpc.service.endpoint.address", portInfo.getTargetEndpoint());
/*    */ 
/*    */ 
/*    */     
/* 68 */     Method[] interfaceMethods = portInterface.getMethods();
/* 69 */     for (int i = 0; i < interfaceMethods.length; i++) {
/* 70 */       Method currentMethod = interfaceMethods[i];
/*    */       
/* 72 */       if (Modifier.isPublic(currentMethod.getModifiers())) {
/* 73 */         ConfiguredCall methodCall = new ConfiguredCall(this.internalTypeRegistry, this.handlerRegistry, this.configuration);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 79 */         String methodName = currentMethod.getName();
/*    */         
/* 81 */         methodCall.setPortName(portInfo.getName());
/* 82 */         methodCall.setOperationMethod(currentMethod);
/* 83 */         methodCall.setMethodName(methodName);
/* 84 */         methodCall.setIsProxy(true);
/*    */         
/* 86 */         handler.addCall(currentMethod, methodCall);
/*    */       } 
/*    */     } 
/*    */     
/* 90 */     return (Remote)Proxy.newProxyInstance(portInterface.getClassLoader(), new Class[] { portInterface, Stub.class, Remote.class }, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\DynamicProxyBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */