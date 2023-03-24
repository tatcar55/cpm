/*     */ package com.sun.xml.ws.tx.at.runtime;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.tx.at.TransactionalFeature;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.tx.at.internal.WSATGatewayRM;
/*     */ import com.sun.xml.ws.tx.at.tube.WSATClientTube;
/*     */ import com.sun.xml.ws.tx.at.tube.WSATServerTube;
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
/*     */ public final class AtTubeFactory
/*     */   implements TubeFactory
/*     */ {
/*     */   private static final String WSAT_SOAP_NSURI = "http://schemas.xmlsoap.org/ws/2004/10/wsat";
/*  65 */   private static final QName AT_ALWAYS_CAPABILITY = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "ATAlwaysCapability");
/*  66 */   private static final QName AT_ASSERTION = new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "ATAssertion");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createTube(ClientTubelineAssemblyContext context) {
/*  75 */     TransactionalFeature feature = (TransactionalFeature)context.getBinding().getFeature(TransactionalFeature.class);
/*  76 */     if (isWSATPolicyEnabled(context.getPolicyMap(), context.getWsdlPort(), false) || (feature != null && feature.isEnabled())) {
/*     */       
/*  78 */       WSATGatewayRM.create();
/*  79 */       return (Tube)new WSATClientTube(context.getTubelineHead(), context, feature);
/*     */     } 
/*  81 */     return context.getTubelineHead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createTube(ServerTubelineAssemblyContext context) {
/*  92 */     TransactionalFeature feature = (TransactionalFeature)context.getEndpoint().getBinding().getFeature(TransactionalFeature.class);
/*  93 */     if (isWSATPolicyEnabled(context.getPolicyMap(), context.getWsdlPort(), true) || (feature != null && feature.isEnabled())) {
/*     */       
/*  95 */       WSATGatewayRM.create();
/*  96 */       return (Tube)new WSATServerTube(context.getTubelineHead(), context, feature);
/*     */     } 
/*  98 */     return context.getTubelineHead();
/*     */   }
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
/*     */   private boolean isWSATPolicyEnabled(PolicyMap policyMap, WSDLPort wsdlPort, boolean isServerSide) {
/* 112 */     if (policyMap == null || wsdlPort == null)
/*     */     {
/* 114 */       return false;
/*     */     }
/*     */     try {
/* 117 */       PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(wsdlPort.getOwner().getName(), wsdlPort.getName());
/* 118 */       Policy policy = policyMap.getEndpointEffectivePolicy(endpointKey);
/* 119 */       for (WSDLBoundOperation wbo : wsdlPort.getBinding().getBindingOperations()) {
/* 120 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(wsdlPort.getOwner().getName(), wsdlPort.getName(), wbo.getName());
/* 121 */         policy = policyMap.getOperationEffectivePolicy(operationKey);
/* 122 */         if (policy != null) {
/*     */           
/* 124 */           if (isServerSide && policy.contains(AT_ALWAYS_CAPABILITY)) {
/* 125 */             return true;
/*     */           }
/*     */           
/* 128 */           if (policy.contains(AT_ASSERTION)) {
/* 129 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 133 */     } catch (PolicyException e) {
/* 134 */       throw new WebServiceException(e);
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\runtime\AtTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */