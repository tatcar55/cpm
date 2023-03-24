/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.CallbackHandlerConfiguration;
/*    */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
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
/*    */ public class CallbackHandlerConfiguration
/*    */   extends PolicyAssertion
/*    */   implements CallbackHandlerConfiguration, SecurityAssertionValidator
/*    */ {
/* 57 */   private static final QName timestampTimeout = new QName("timestampTimeout");
/*    */   private boolean populated = false;
/* 59 */   private static final QName useXWSSCallbacks = new QName("useXWSSCallbacks");
/* 60 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/* 61 */   private static final QName iterationsForPDK = new QName("iterationsForPDK");
/*    */ 
/*    */   
/*    */   public CallbackHandlerConfiguration() {}
/*    */   
/*    */   public CallbackHandlerConfiguration(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 67 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   
/*    */   public Iterator<? extends PolicyAssertion> getCallbackHandlers() {
/* 71 */     return getParametersIterator();
/*    */   }
/*    */   
/*    */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 75 */     return populate(isServer);
/*    */   }
/*    */   
/*    */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 79 */     if (!this.populated) {
/* 80 */       this.populated = true;
/*    */     }
/* 82 */     return this.fitness;
/*    */   }
/*    */   
/*    */   public String getTimestampTimeout() {
/* 86 */     if (getAttributes().containsKey(timestampTimeout)) {
/* 87 */       return getAttributeValue(timestampTimeout);
/*    */     }
/* 89 */     return null;
/*    */   }
/*    */   
/*    */   public String getiterationsForPDK() {
/* 93 */     if (getAttributes().containsKey(iterationsForPDK)) {
/* 94 */       return getAttributeValue(iterationsForPDK);
/*    */     }
/* 96 */     return "0";
/*    */   }
/*    */   public String getUseXWSSCallbacks() {
/* 99 */     return getAttributeValue(useXWSSCallbacks);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\CallbackHandlerConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */