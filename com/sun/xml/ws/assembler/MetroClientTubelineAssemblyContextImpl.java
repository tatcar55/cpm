/*    */ package com.sun.xml.ws.assembler;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.EndpointAddress;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.WSService;
/*    */ import com.sun.xml.ws.api.client.WSPortInfo;
/*    */ import com.sun.xml.ws.api.model.SEIModel;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*    */ import com.sun.xml.ws.api.pipe.Codec;
/*    */ import com.sun.xml.ws.api.pipe.Pipe;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import com.sun.xml.ws.assembler.dev.MetroClientTubelineAssemblyContext;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetroClientTubelineAssemblyContextImpl
/*    */   extends DefaultClientTubelineAssemblyContext
/*    */   implements MetroClientTubelineAssemblyContext
/*    */ {
/*    */   private SecureConversationInitiator scInitiator;
/*    */   
/*    */   public MetroClientTubelineAssemblyContextImpl(@NotNull ClientTubeAssemblerContext context) {
/* 60 */     super(context);
/*    */   }
/*    */   
/*    */   public SecureConversationInitiator getScInitiator() {
/* 64 */     return this.scInitiator;
/*    */   }
/*    */   
/*    */   public void setScInitiator(SecureConversationInitiator initiator) {
/* 68 */     this.scInitiator = initiator;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\MetroClientTubelineAssemblyContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */