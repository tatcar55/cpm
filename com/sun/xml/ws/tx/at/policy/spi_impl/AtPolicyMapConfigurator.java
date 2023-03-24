/*     */ package com.sun.xml.ws.tx.at.policy.spi_impl;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.policy.AtPolicyCreator;
/*     */ import com.sun.xml.ws.tx.at.policy.EjbTransactionType;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ public class AtPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  72 */   private static final Logger LOGGER = Logger.getLogger(AtPolicyMapConfigurator.class);
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
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/*  85 */     Collection<PolicySubject> subjects = new LinkedList<PolicySubject>();
/*     */     
/*  87 */     Class<?> seiClass = getDeclaringClass(model);
/*  88 */     if (seiClass == null) {
/*  89 */       return subjects;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     EjbTransactionType defaultEjbTxnAttr = EjbTransactionType.NOT_DEFINED;
/*  99 */     Transactional defaultFeature = seiClass.<Transactional>getAnnotation(Transactional.class);
/* 100 */     for (JavaMethod method : model.getJavaMethods()) {
/* 101 */       Transactional effectiveFeature = getEffectiveFeature(method.getSEIMethod(), defaultFeature);
/* 102 */       if (effectiveFeature == null || !effectiveFeature.enabled()) {
/*     */         continue;
/*     */       }
/*     */       
/* 106 */       EjbTransactionType effectiveEjbTxType = defaultEjbTxnAttr.getEffectiveType(method.getSEIMethod());
/*     */       
/* 108 */       String policyId = model.getBoundPortTypeName().getLocalPart() + "_" + method.getOperationName() + "_WSAT_Policy";
/* 109 */       Policy policy = AtPolicyCreator.createPolicy(policyId, (effectiveFeature.version()).namespaceVersion, effectiveFeature.value(), effectiveEjbTxType);
/* 110 */       if (policy != null) {
/*     */         
/* 112 */         WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingOperationSubject(model.getBoundPortTypeName(), new QName(model.getTargetNamespace(), method.getOperationName()));
/*     */         
/* 114 */         PolicySubject generatedWsatPolicySubject = new PolicySubject(wsdlSubject, policy);
/* 115 */         if (LOGGER.isLoggable(Level.FINE)) {
/* 116 */           LOGGER.fine(LocalizationMessages.WSAT_1002_ADD_AT_POLICY_ASSERTION(model.getPortName().toString(), method.getOperationName(), seiClass.getName(), method.getSEIMethod().getName(), effectiveFeature.value().toString(), effectiveEjbTxType.toString(), policy.toString()));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         subjects.add(generatedWsatPolicySubject);
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return subjects;
/*     */   }
/*     */   
/*     */   private Class<?> getDeclaringClass(@Nullable SEIModel model) {
/* 133 */     if (model == null || model.getJavaMethods().isEmpty()) {
/* 134 */       return null;
/*     */     }
/*     */     
/* 137 */     return ((JavaMethod)model.getJavaMethods().iterator().next()).getSEIMethod().getDeclaringClass();
/*     */   }
/*     */   
/*     */   private Transactional getEffectiveFeature(Method method, Transactional defaultFeature) {
/* 141 */     Transactional feature = method.<Transactional>getAnnotation(Transactional.class);
/* 142 */     if (feature != null)
/*     */     {
/*     */       
/* 145 */       return feature;
/*     */     }
/*     */     
/* 148 */     return defaultFeature;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\spi_impl\AtPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */