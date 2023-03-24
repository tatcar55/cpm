/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.ValidatorConfiguration;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ public class ValidatorConfiguration
/*     */   extends PolicyAssertion
/*     */   implements ValidatorConfiguration, SecurityAssertionValidator
/*     */ {
/*     */   private boolean populated = false;
/*  60 */   private Iterator<PolicyAssertion> ast = null;
/*  61 */   private static QName cmaxClockSkew = new QName("http://schemas.sun.com/2006/03/wss/client", "maxClockSkew");
/*  62 */   private static QName smaxClockSkew = new QName("http://schemas.sun.com/2006/03/wss/server", "maxClockSkew");
/*  63 */   private static QName ctimestampFreshnessLimit = new QName("http://schemas.sun.com/2006/03/wss/client", "timestampFreshnessLimit");
/*  64 */   private static QName stimestampFreshnessLimit = new QName("http://schemas.sun.com/2006/03/wss/server", "timestampFreshnessLimit");
/*  65 */   private static QName smaxNonceAge = new QName("http://schemas.sun.com/2006/03/wss/server", "maxNonceAge");
/*  66 */   private static QName crevocationEnabled = new QName("http://schemas.sun.com/2006/03/wss/client", "revocationEnabled");
/*  67 */   private static QName srevocationEnabled = new QName("http://schemas.sun.com/2006/03/wss/server", "revocationEnabled");
/*  68 */   private static QName cenforceKeyUsage = new QName("http://schemas.sun.com/2006/03/wss/client", "enforceKeyUsage");
/*  69 */   private static QName senforceKeyUsage = new QName("http://schemas.sun.com/2006/03/wss/server", "enforceKeyUsage");
/*     */   
/*  71 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */ 
/*     */   
/*     */   public ValidatorConfiguration() {}
/*     */   
/*     */   public ValidatorConfiguration(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  77 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   
/*     */   public Iterator<? extends PolicyAssertion> getValidators() {
/*  81 */     populate();
/*  82 */     return this.ast;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  86 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   private void populate() {
/*  90 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/*  94 */     if (!this.populated) {
/*  95 */       this.ast = getNestedAssertionsIterator();
/*  96 */       this.populated = true;
/*     */     } 
/*  98 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public String getMaxClockSkew() {
/* 102 */     if (getAttributes().containsKey(cmaxClockSkew))
/* 103 */       return getAttributeValue(cmaxClockSkew); 
/* 104 */     if (getAttributes().containsKey(smaxClockSkew)) {
/* 105 */       return getAttributeValue(smaxClockSkew);
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public String getTimestampFreshnessLimit() {
/* 111 */     if (getAttributes().containsKey(ctimestampFreshnessLimit))
/* 112 */       return getAttributeValue(ctimestampFreshnessLimit); 
/* 113 */     if (getAttributes().containsKey(stimestampFreshnessLimit)) {
/* 114 */       return getAttributeValue(stimestampFreshnessLimit);
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */   
/*     */   public String getMaxNonceAge() {
/* 120 */     if (getAttributes().containsKey(smaxNonceAge)) {
/* 121 */       return getAttributeValue(smaxNonceAge);
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public String getRevocationEnabled() {
/* 127 */     if (getAttributes().containsKey(crevocationEnabled))
/* 128 */       return getAttributeValue(crevocationEnabled); 
/* 129 */     if (getAttributes().containsKey(srevocationEnabled)) {
/* 130 */       return getAttributeValue(srevocationEnabled);
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   public String getEnforceKeyUsage() {
/* 136 */     if (getAttributes().containsKey(cenforceKeyUsage))
/* 137 */       return getAttributeValue(cenforceKeyUsage); 
/* 138 */     if (getAttributes().containsKey(senforceKeyUsage)) {
/* 139 */       return getAttributeValue(senforceKeyUsage);
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\ValidatorConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */