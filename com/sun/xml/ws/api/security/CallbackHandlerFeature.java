/*    */ package com.sun.xml.ws.api.security;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import javax.security.auth.callback.CallbackHandler;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import org.glassfish.gmbal.ManagedAttribute;
/*    */ import org.glassfish.gmbal.ManagedData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ManagedData
/*    */ public final class CallbackHandlerFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   private final CallbackHandler handler;
/*    */   
/*    */   public CallbackHandlerFeature(@NotNull CallbackHandler handler) {
/* 80 */     if (handler == null) throw new IllegalArgumentException(); 
/* 81 */     this.handler = handler;
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 86 */     return CallbackHandlerFeature.class.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ManagedAttribute
/*    */   @NotNull
/*    */   public CallbackHandler getHandler() {
/* 95 */     return this.handler;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\CallbackHandlerFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */