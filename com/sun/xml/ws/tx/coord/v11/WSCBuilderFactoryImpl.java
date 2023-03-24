/*    */ package com.sun.xml.ws.tx.coord.v11;
/*    */ 
/*    */ import com.sun.xml.ws.tx.coord.common.WSATCoordinationContextBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.WSCBuilderFactory;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationMessageBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationProxyBuilder;
/*    */ import com.sun.xml.ws.tx.coord.v11.client.RegistrationMessageBuilderImpl;
/*    */ import com.sun.xml.ws.tx.coord.v11.client.RegistrationProxyBuilderImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSCBuilderFactoryImpl
/*    */   extends WSCBuilderFactory
/*    */ {
/*    */   public WSATCoordinationContextBuilder newWSATCoordinationContextBuilder() {
/* 53 */     return new WSATCoordinationContextBuilderImpl();
/*    */   }
/*    */   
/*    */   public RegistrationProxyBuilder newRegistrationProxyBuilder() {
/* 57 */     return (RegistrationProxyBuilder)new RegistrationProxyBuilderImpl();
/*    */   }
/*    */   
/*    */   public RegistrationMessageBuilder newWSATRegistrationRequestBuilder() {
/* 61 */     return (RegistrationMessageBuilder)new RegistrationMessageBuilderImpl();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\WSCBuilderFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */