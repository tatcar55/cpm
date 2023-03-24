/*    */ package com.sun.xml.ws.rx.rm.policy.spi_impl;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.model.SEIModel;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import com.sun.xml.ws.policy.PolicySubject;
/*    */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*    */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import java.util.logging.Level;
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
/*    */ public class RmPolicyMapConfigurator
/*    */   implements PolicyMapConfigurator
/*    */ {
/* 61 */   private static final Logger LOGGER = Logger.getLogger(RmPolicyMapConfigurator.class);
/*    */   
/*    */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/* 64 */     Collection<PolicySubject> subjects = new LinkedList<PolicySubject>();
/*    */     
/*    */     try {
/* 67 */       LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*    */       
/* 69 */       updateReliableMessagingSettings(subjects, wsBinding, model, policyMap);
/*    */       
/* 71 */       return subjects;
/*    */     } finally {
/* 73 */       LOGGER.exiting(subjects);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void updateReliableMessagingSettings(Collection<PolicySubject> subjects, WSBinding wsBinding, SEIModel model, PolicyMap policyMap) throws PolicyException, IllegalArgumentException {
/* 78 */     ReliableMessagingFeature feature = (ReliableMessagingFeature)wsBinding.getFeature(ReliableMessagingFeature.class);
/* 79 */     if (feature == null || !feature.isEnabled()) {
/*    */       return;
/*    */     }
/*    */     
/* 83 */     if (LOGGER.isLoggable(Level.FINEST))
/* 84 */       LOGGER.finest(String.format("Reliable messaging feature enabled on service '%s', port '%s'", new Object[] { model.getServiceQName(), model.getPortName() })); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\spi_impl\RmPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */