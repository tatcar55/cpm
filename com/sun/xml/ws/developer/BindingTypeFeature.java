/*    */ package com.sun.xml.ws.developer;
/*    */ 
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
/*    */ @ManagedData
/*    */ public final class BindingTypeFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "http://jax-ws.dev.java.net/features/binding";
/*    */   private final String bindingId;
/*    */   
/*    */   public BindingTypeFeature(String bindingId) {
/* 64 */     this.bindingId = bindingId;
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 69 */     return "http://jax-ws.dev.java.net/features/binding";
/*    */   }
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getBindingId() {
/* 74 */     return this.bindingId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\BindingTypeFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */