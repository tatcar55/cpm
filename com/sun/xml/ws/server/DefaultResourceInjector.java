/*    */ package com.sun.xml.ws.server;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.server.ResourceInjector;
/*    */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*    */ import com.sun.xml.ws.util.InjectionPlan;
/*    */ import javax.xml.ws.WebServiceContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DefaultResourceInjector
/*    */   extends ResourceInjector
/*    */ {
/*    */   public void inject(@NotNull WSWebServiceContext context, @NotNull Object instance) {
/* 58 */     InjectionPlan.buildInjectionPlan(instance.getClass(), WebServiceContext.class, false).inject(instance, context);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\DefaultResourceInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */