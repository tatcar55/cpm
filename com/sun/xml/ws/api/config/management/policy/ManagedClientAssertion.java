/*     */ package com.sun.xml.ws.api.config.management.policy;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.resources.ManagementMessages;
/*     */ import java.util.Collection;
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
/*     */ public class ManagedClientAssertion
/*     */   extends ManagementAssertion
/*     */ {
/*  63 */   public static final QName MANAGED_CLIENT_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "ManagedClient");
/*     */ 
/*     */   
/*  66 */   private static final Logger LOGGER = Logger.getLogger(ManagedClientAssertion.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ManagedClientAssertion getAssertion(WSPortInfo portInfo) throws WebServiceException {
/*  76 */     if (portInfo == null) {
/*  77 */       return null;
/*     */     }
/*  79 */     LOGGER.entering(new Object[] { portInfo });
/*     */ 
/*     */ 
/*     */     
/*  83 */     PolicyMap policyMap = portInfo.getPolicyMap();
/*  84 */     ManagedClientAssertion assertion = ManagementAssertion.<ManagedClientAssertion>getAssertion(MANAGED_CLIENT_QNAME, policyMap, portInfo.getServiceName(), portInfo.getPortName(), ManagedClientAssertion.class);
/*     */     
/*  86 */     LOGGER.exiting(assertion);
/*  87 */     return assertion;
/*     */   }
/*     */ 
/*     */   
/*     */   public ManagedClientAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters) throws AssertionCreationException {
/*  92 */     super(MANAGED_CLIENT_QNAME, data, assertionParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManagementEnabled() {
/* 101 */     String management = getAttributeValue(MANAGEMENT_ATTRIBUTE_QNAME);
/* 102 */     if (management != null && (
/* 103 */       management.trim().toLowerCase().equals("on") || Boolean.parseBoolean(management))) {
/* 104 */       LOGGER.warning(ManagementMessages.WSM_1006_CLIENT_MANAGEMENT_ENABLED());
/*     */     }
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\config\management\policy\ManagedClientAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */