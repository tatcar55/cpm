/*    */ package com.sun.xml.ws.tx.at.policy.spi_impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.tx.at.WsatNamespace;
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*    */ import com.sun.xml.ws.tx.at.policy.AtAlwaysCapability;
/*    */ import com.sun.xml.ws.tx.at.policy.AtAssertion;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AtAssertionCreator
/*    */   implements PolicyAssertionCreator
/*    */ {
/* 67 */   private static final Map<QName, AssertionInstantiator> instantiationMap = new HashMap<QName, AssertionInstantiator>();
/*    */   static {
/* 69 */     AssertionInstantiator atAssertionInstantiator = new AssertionInstantiator()
/*    */       {
/*    */         public PolicyAssertion instantiate(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 72 */           return (PolicyAssertion)new AtAssertion(data, assertionParameters);
/*    */         }
/*    */       };
/*    */     
/* 76 */     for (WsatNamespace ns : WsatNamespace.values()) {
/* 77 */       instantiationMap.put(AtAssertion.nameForNamespace(ns), atAssertionInstantiator);
/*    */     }
/* 79 */     instantiationMap.put(AtAlwaysCapability.NAME, new AssertionInstantiator()
/*    */         {
/*    */           public PolicyAssertion instantiate(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 82 */             return (PolicyAssertion)new AtAlwaysCapability(data, assertionParameters);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/* 87 */   private static final List<String> SUPPORTED_DOMAINS = Collections.unmodifiableList(WsatNamespace.namespacesList());
/*    */   
/*    */   public String[] getSupportedDomainNamespaceURIs() {
/* 90 */     return SUPPORTED_DOMAINS.<String>toArray(new String[SUPPORTED_DOMAINS.size()]);
/*    */   }
/*    */   
/*    */   public PolicyAssertion createAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative, PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
/* 94 */     AssertionInstantiator instantiator = instantiationMap.get(data.getName());
/* 95 */     if (instantiator != null) {
/* 96 */       return instantiator.instantiate(data, assertionParameters, nestedAlternative);
/*    */     }
/* 98 */     return defaultCreator.createAssertion(data, assertionParameters, nestedAlternative, null);
/*    */   }
/*    */   
/*    */   private static interface AssertionInstantiator {
/*    */     PolicyAssertion instantiate(AssertionData param1AssertionData, Collection<PolicyAssertion> param1Collection, AssertionSet param1AssertionSet);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\spi_impl\AtAssertionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */