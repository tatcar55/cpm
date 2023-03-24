/*    */ package com.sun.xml.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*    */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class WSDLParserExtensionContextImpl
/*    */   implements WSDLParserExtensionContext
/*    */ {
/*    */   private final boolean isClientSide;
/*    */   private final WSDLModel wsdlModel;
/*    */   private final Container container;
/*    */   private final PolicyResolver policyResolver;
/*    */   
/*    */   protected WSDLParserExtensionContextImpl(WSDLModel model, boolean isClientSide, Container container, PolicyResolver policyResolver) {
/* 65 */     this.wsdlModel = model;
/* 66 */     this.isClientSide = isClientSide;
/* 67 */     this.container = container;
/* 68 */     this.policyResolver = policyResolver;
/*    */   }
/*    */   
/*    */   public boolean isClientSide() {
/* 72 */     return this.isClientSide;
/*    */   }
/*    */   
/*    */   public WSDLModel getWSDLModel() {
/* 76 */     return this.wsdlModel;
/*    */   }
/*    */   
/*    */   public Container getContainer() {
/* 80 */     return this.container;
/*    */   }
/*    */   
/*    */   public PolicyResolver getPolicyResolver() {
/* 84 */     return this.policyResolver;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\WSDLParserExtensionContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */