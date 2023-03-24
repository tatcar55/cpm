/*    */ package com.sun.xml.ws.rx.mc.policy.spi_impl;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*    */ import com.sun.xml.ws.rx.mc.policy.McAssertionNamespace;
/*    */ import com.sun.xml.ws.rx.mc.policy.wsmc200702.MakeConnectionSupportedAssertion;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public class McAssertionValidator
/*    */   implements PolicyAssertionValidator
/*    */ {
/* 55 */   private static final ArrayList<QName> SERVER_SIDE_ASSERTIONS = new ArrayList<QName>(1);
/* 56 */   private static final ArrayList<QName> CLIENT_SIDE_ASSERTIONS = new ArrayList<QName>(1);
/*    */   
/* 58 */   private static final List<String> SUPPORTED_DOMAINS = Collections.unmodifiableList(McAssertionNamespace.namespacesList());
/*    */   
/*    */   static {
/* 61 */     SERVER_SIDE_ASSERTIONS.add(MakeConnectionSupportedAssertion.NAME);
/*    */ 
/*    */     
/* 64 */     CLIENT_SIDE_ASSERTIONS.addAll(SERVER_SIDE_ASSERTIONS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 71 */     return CLIENT_SIDE_ASSERTIONS.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 75 */     QName assertionName = assertion.getName();
/* 76 */     if (SERVER_SIDE_ASSERTIONS.contains(assertionName))
/* 77 */       return PolicyAssertionValidator.Fitness.SUPPORTED; 
/* 78 */     if (CLIENT_SIDE_ASSERTIONS.contains(assertionName)) {
/* 79 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*    */     }
/* 81 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] declareSupportedDomains() {
/* 86 */     return SUPPORTED_DOMAINS.<String>toArray(new String[SUPPORTED_DOMAINS.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\spi_impl\McAssertionValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */