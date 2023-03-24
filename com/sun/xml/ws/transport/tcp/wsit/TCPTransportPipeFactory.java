/*     */ package com.sun.xml.ws.transport.tcp.wsit;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.transport.tcp.client.ServiceChannelTransportPipe;
/*     */ import com.sun.xml.ws.transport.tcp.client.TCPTransportPipe;
/*     */ import com.sun.xml.ws.transport.tcp.client.TCPTransportPipeFactory;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.stubs.ServiceChannelWSImplService;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPTransportPipeFactory
/*     */   extends TCPTransportPipeFactory
/*     */ {
/*  65 */   private static final QName serviceChannelServiceName = (new ServiceChannelWSImplService()).getServiceName();
/*     */ 
/*     */   
/*     */   public Tube doCreate(ClientTubeAssemblerContext context) {
/*  69 */     return doCreate(context, true);
/*     */   }
/*     */   
/*     */   public static Tube doCreate(@NotNull ClientTubeAssemblerContext context, boolean checkSchema) {
/*  73 */     if (checkSchema && !"vnd.sun.ws.tcp".equalsIgnoreCase(context.getAddress().getURI().getScheme())) {
/*  74 */       return null;
/*     */     }
/*     */     
/*  77 */     initializeConnectionManagement(context.getWsdlModel());
/*  78 */     if (context.getService().getServiceName().equals(serviceChannelServiceName)) {
/*  79 */       return (Tube)new ServiceChannelTransportPipe(context);
/*     */     }
/*     */     
/*  82 */     return (Tube)new TCPTransportPipe(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initializeConnectionManagement(WSDLPort port) {
/*  90 */     PolicyConnectionManagementSettingsHolder instance = PolicyConnectionManagementSettingsHolder.getInstance();
/*     */ 
/*     */     
/*  93 */     if (instance.clientSettings == null) {
/*  94 */       synchronized (instance) {
/*  95 */         if (instance.clientSettings == null) {
/*  96 */           instance.clientSettings = PolicyConnectionManagementSettingsHolder.createSettingsInstance(port);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int retrieveCustomTCPPort(WSDLPort port) {
/*     */     try {
/* 105 */       WSDLModel model = port.getBinding().getOwner();
/* 106 */       PolicyMap policyMap = model.getPolicyMap();
/* 107 */       if (policyMap != null) {
/* 108 */         PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(port.getOwner().getName(), port.getName());
/* 109 */         Policy policy = policyMap.getEndpointEffectivePolicy(endpointKey);
/*     */         
/* 111 */         if (policy != null && policy.contains(TCPConstants.TCPTRANSPORT_POLICY_ASSERTION))
/*     */         {
/*     */           
/* 114 */           for (AssertionSet assertionSet : policy) {
/* 115 */             for (PolicyAssertion assertion : assertionSet) {
/* 116 */               if (assertion.getName().equals(TCPConstants.TCPTRANSPORT_POLICY_ASSERTION)) {
/* 117 */                 String value = assertion.getAttributeValue(new QName("port"));
/* 118 */                 if (value == null) {
/* 119 */                   return -1;
/*     */                 }
/* 121 */                 value = value.trim();
/*     */                 
/*     */                 try {
/* 124 */                   return Integer.parseInt(value);
/* 125 */                 } catch (NumberFormatException e) {
/*     */ 
/*     */                   
/* 128 */                   return -1;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 135 */       return -1;
/* 136 */     } catch (PolicyException e) {
/* 137 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\wsit\TCPTransportPipeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */