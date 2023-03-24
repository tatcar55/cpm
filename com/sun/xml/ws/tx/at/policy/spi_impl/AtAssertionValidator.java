/*    */ package com.sun.xml.ws.tx.at.policy.spi_impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.tx.at.WsatNamespace;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*    */ import com.sun.xml.ws.tx.at.policy.AtAlwaysCapability;
/*    */ import com.sun.xml.ws.tx.at.policy.AtAssertion;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AtAssertionValidator
/*    */   implements PolicyAssertionValidator
/*    */ {
/* 59 */   private static final List<String> SUPPORTED_DOMAINS = Collections.unmodifiableList(WsatNamespace.namespacesList());
/*    */   private static final List<QName> SUPPORTED_ASSERTIONS;
/*    */   
/*    */   static {
/* 63 */     List<QName> tmpList = new ArrayList<QName>(3);
/*    */     
/* 65 */     for (WsatNamespace ns : WsatNamespace.values()) {
/* 66 */       tmpList.add(AtAssertion.nameForNamespace(ns));
/*    */     }
/* 68 */     tmpList.add(AtAlwaysCapability.NAME);
/*    */     
/* 70 */     SUPPORTED_ASSERTIONS = Collections.unmodifiableList(tmpList);
/*    */   }
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 74 */     return SUPPORTED_ASSERTIONS.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 78 */     return SUPPORTED_ASSERTIONS.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */   
/*    */   public String[] declareSupportedDomains() {
/* 82 */     return SUPPORTED_DOMAINS.<String>toArray(new String[SUPPORTED_DOMAINS.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\spi_impl\AtAssertionValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */