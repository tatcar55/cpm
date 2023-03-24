/*     */ package com.sun.xml.ws.api.config.management.policy;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.SimpleAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.resources.ManagementMessages;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ManagementAssertion
/*     */   extends SimpleAssertion
/*     */ {
/*     */   public enum Setting
/*     */   {
/*  71 */     NOT_SET, OFF, ON;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  76 */   protected static final QName MANAGEMENT_ATTRIBUTE_QNAME = new QName("management");
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected static final QName MONITORING_ATTRIBUTE_QNAME = new QName("monitoring");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final QName ID_ATTRIBUTE_QNAME = new QName("id");
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final QName START_ATTRIBUTE_QNAME = new QName("start");
/*     */   
/*  91 */   private static final Logger LOGGER = Logger.getLogger(ManagementAssertion.class);
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
/*     */   protected static <T extends ManagementAssertion> T getAssertion(QName name, PolicyMap policyMap, QName serviceName, QName portName, Class<T> type) throws WebServiceException {
/*     */     try {
/* 110 */       PolicyAssertion assertion = null;
/* 111 */       if (policyMap != null) {
/* 112 */         PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/* 113 */         Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 114 */         if (policy != null) {
/* 115 */           Iterator<AssertionSet> assertionSets = policy.iterator();
/* 116 */           if (assertionSets.hasNext()) {
/* 117 */             AssertionSet assertionSet = assertionSets.next();
/* 118 */             Iterator<PolicyAssertion> assertions = assertionSet.get(name).iterator();
/* 119 */             if (assertions.hasNext()) {
/* 120 */               assertion = assertions.next();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 125 */       return (assertion == null) ? null : (T)assertion.getImplementation(type);
/* 126 */     } catch (PolicyException ex) {
/* 127 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_1001_FAILED_ASSERTION(name), ex));
/*     */     } 
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
/*     */   
/*     */   protected ManagementAssertion(QName name, AssertionData data, Collection<PolicyAssertion> assertionParameters) throws AssertionCreationException {
/* 143 */     super(data, assertionParameters);
/* 144 */     if (!name.equals(data.getName())) {
/* 145 */       throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, ManagementMessages.WSM_1002_EXPECTED_MANAGEMENT_ASSERTION(name)));
/*     */     }
/*     */     
/* 148 */     if (isManagementEnabled() && !data.containsAttribute(ID_ATTRIBUTE_QNAME)) {
/* 149 */       throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, ManagementMessages.WSM_1003_MANAGEMENT_ASSERTION_MISSING_ID(name)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 160 */     return getAttributeValue(ID_ATTRIBUTE_QNAME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStart() {
/* 169 */     return getAttributeValue(START_ATTRIBUTE_QNAME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isManagementEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Setting monitoringAttribute() {
/* 186 */     String monitoring = getAttributeValue(MONITORING_ATTRIBUTE_QNAME);
/* 187 */     Setting result = Setting.NOT_SET;
/* 188 */     if (monitoring != null) {
/* 189 */       if (monitoring.trim().toLowerCase().equals("on") || Boolean.parseBoolean(monitoring)) {
/*     */         
/* 191 */         result = Setting.ON;
/*     */       } else {
/*     */         
/* 194 */         result = Setting.OFF;
/*     */       } 
/*     */     }
/* 197 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\config\management\policy\ManagementAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */