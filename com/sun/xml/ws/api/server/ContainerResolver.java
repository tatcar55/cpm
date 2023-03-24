/*    */ package com.sun.xml.ws.api.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ContainerResolver
/*    */ {
/* 62 */   private static final ThreadLocalContainerResolver DEFAULT = new ThreadLocalContainerResolver();
/*    */   
/* 64 */   private static volatile ContainerResolver theResolver = DEFAULT;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setInstance(ContainerResolver resolver) {
/* 73 */     if (resolver == null)
/* 74 */       resolver = DEFAULT; 
/* 75 */     theResolver = resolver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static ContainerResolver getInstance() {
/* 84 */     return theResolver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ThreadLocalContainerResolver getDefault() {
/* 93 */     return DEFAULT;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public abstract Container getContainer();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\ContainerResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */