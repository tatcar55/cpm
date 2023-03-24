/*    */ package com.sun.xml.ws.rx.mc.policy.spi_impl;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*    */ import com.sun.xml.ws.rx.mc.policy.McAssertionNamespace;
/*    */ import com.sun.xml.ws.rx.mc.policy.wsmc200702.MakeConnectionSupportedAssertion;
/*    */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
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
/*    */ public final class McAssertionCreator
/*    */   implements PolicyAssertionCreator
/*    */ {
/* 64 */   private static final Map<QName, AssertionInstantiator> instantiationMap = new HashMap<QName, AssertionInstantiator>();
/*    */   static {
/* 66 */     instantiationMap.put(MakeConnectionSupportedAssertion.NAME, MakeConnectionSupportedAssertion.getInstantiator());
/*    */   }
/*    */   
/* 69 */   private static final List<String> SUPPORTED_DOMAINS = Collections.unmodifiableList(McAssertionNamespace.namespacesList());
/*    */   
/*    */   public String[] getSupportedDomainNamespaceURIs() {
/* 72 */     return SUPPORTED_DOMAINS.<String>toArray(new String[SUPPORTED_DOMAINS.size()]);
/*    */   }
/*    */   
/*    */   public PolicyAssertion createAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative, PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
/* 76 */     AssertionInstantiator instantiator = instantiationMap.get(data.getName());
/* 77 */     if (instantiator != null) {
/* 78 */       return instantiator.newInstance(data, assertionParameters, nestedAlternative);
/*    */     }
/* 80 */     return defaultCreator.createAssertion(data, assertionParameters, nestedAlternative, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\spi_impl\McAssertionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */