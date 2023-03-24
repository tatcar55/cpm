/*    */ package com.sun.xml.wss.jaxws.impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ClientTubeConfiguration
/*    */   extends TubeConfiguration
/*    */ {
/*    */   private final WSBinding binding;
/*    */   
/*    */   public ClientTubeConfiguration(PolicyMap policy, WSDLPort wsdlPort, WSBinding binding) {
/* 56 */     super(policy, wsdlPort);
/* 57 */     this.binding = binding;
/*    */   }
/*    */   
/*    */   public WSBinding getBinding() {
/* 61 */     return this.binding;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\ClientTubeConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */