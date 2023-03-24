/*    */ package com.sun.xml.wss.provider.wsit;
/*    */ 
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.ws.rx.mc.api.McProtocolVersion;
/*    */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*    */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*    */ import com.sun.xml.wss.impl.PolicyResolver;
/*    */ import com.sun.xml.wss.jaxws.impl.PolicyResolverImpl;
/*    */ import com.sun.xml.wss.jaxws.impl.TubeConfiguration;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolicyResolverFactory
/*    */ {
/*    */   public static PolicyResolver createPolicyResolver(List<PolicyAlternativeHolder> alternatives, WSDLBoundOperation cachedOperation, TubeConfiguration tubeConfig, AddressingVersion addVer, boolean isClient, RmProtocolVersion rmVer, McProtocolVersion mcVer) {
/* 68 */     if (alternatives.size() == 1) {
/* 69 */       return (PolicyResolver)new PolicyResolverImpl(((PolicyAlternativeHolder)alternatives.get(0)).getInMessagePolicyMap(), ((PolicyAlternativeHolder)alternatives.get(0)).getInProtocolPM(), cachedOperation, tubeConfig, addVer, isClient, rmVer, mcVer);
/*    */     }
/* 71 */     return new AlternativesBasedPolicyResolver(alternatives, cachedOperation, tubeConfig, addVer, isClient, rmVer, mcVer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PolicyResolver createPolicyResolver(HashMap<WSDLBoundOperation, SecurityPolicyHolder> inMessagePolicyMap, HashMap<String, SecurityPolicyHolder> ip, WSDLBoundOperation cachedOperation, TubeConfiguration tubeConfig, AddressingVersion addVer, boolean isClient, RmProtocolVersion rmVer, McProtocolVersion mcVer) {
/* 80 */     return (PolicyResolver)new PolicyResolverImpl(inMessagePolicyMap, ip, cachedOperation, tubeConfig, addVer, isClient, rmVer, mcVer);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\PolicyResolverFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */