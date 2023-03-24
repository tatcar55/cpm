/*    */ package com.sun.xml.ws.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.server.AbstractInstanceResolver;
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SingletonResolver<T>
/*    */   extends AbstractInstanceResolver<T>
/*    */ {
/*    */   @NotNull
/*    */   private final T singleton;
/*    */   
/*    */   public SingletonResolver(@NotNull T singleton) {
/* 62 */     this.singleton = singleton;
/*    */   }
/*    */   @NotNull
/*    */   public T resolve(Packet request) {
/* 66 */     return this.singleton;
/*    */   }
/*    */   
/*    */   public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
/* 70 */     getResourceInjector(endpoint).inject(wsc, this.singleton);
/*    */     
/* 72 */     invokeMethod(findAnnotatedMethod(this.singleton.getClass(), PostConstruct.class), this.singleton, new Object[0]);
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 76 */     invokeMethod(findAnnotatedMethod(this.singleton.getClass(), PreDestroy.class), this.singleton, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\SingletonResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */