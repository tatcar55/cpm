/*     */ package com.sun.xml.ws.transport.tcp.wsit;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelCreator;
/*     */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelWSImpl;
/*     */ import com.sun.xml.ws.transport.tcp.util.ConnectionManagementSettings;
/*     */ import com.sun.xml.ws.transport.tcp.util.TCPConstants;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyConnectionManagementSettingsHolder
/*     */   implements ConnectionManagementSettings.ConnectionManagementSettingsHolder
/*     */ {
/*     */   private static final int DEFAULT_VALUE = -1;
/*  73 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp");
/*     */   
/*     */   volatile ConnectionManagementSettings clientSettings;
/*     */   
/*     */   volatile ConnectionManagementSettings serverSettings;
/*     */   
/*  79 */   private static final PolicyConnectionManagementSettingsHolder instance = new PolicyConnectionManagementSettingsHolder();
/*     */ 
/*     */   
/*     */   public static PolicyConnectionManagementSettingsHolder getInstance() {
/*  83 */     return instance;
/*     */   }
/*     */   
/*     */   public ConnectionManagementSettings getClientSettings() {
/*  87 */     return this.clientSettings;
/*     */   }
/*     */   
/*     */   public ConnectionManagementSettings getServerSettings() {
/*  91 */     if (this.serverSettings == null) {
/*  92 */       synchronized (this) {
/*  93 */         if (this.serverSettings == null) {
/*  94 */           WSEndpoint<ServiceChannelWSImpl> endpoint = ServiceChannelCreator.getServiceChannelEndpointInstance();
/*  95 */           this.serverSettings = createSettingsInstance(endpoint.getPort());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 100 */     return this.serverSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   static ConnectionManagementSettings createSettingsInstance(@NotNull WSDLPort port) {
/*     */     try {
/* 107 */       WSDLModel model = port.getBinding().getOwner();
/* 108 */       PolicyMap policyMap = model.getPolicyMap();
/* 109 */       if (policyMap != null) {
/* 110 */         PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(TCPConstants.SERVICE_CHANNEL_WS_NAME, TCPConstants.SERVICE_CHANNEL_WS_PORT_NAME);
/*     */         
/* 112 */         Policy policy = policyMap.getEndpointEffectivePolicy(endpointKey);
/* 113 */         if (policy != null && policy.contains(TCPConstants.TCPTRANSPORT_CONNECTION_MANAGEMENT_ASSERTION)) {
/* 114 */           for (AssertionSet assertionSet : policy) {
/* 115 */             for (PolicyAssertion assertion : assertionSet) {
/* 116 */               if (assertion.getName().equals(TCPConstants.TCPTRANSPORT_CONNECTION_MANAGEMENT_ASSERTION)) {
/* 117 */                 int highWatermark = getAssertionAttrValue(assertion, "HighWatermark");
/* 118 */                 int maxParallelConnections = getAssertionAttrValue(assertion, "MaxParallelConnections");
/* 119 */                 int numberToReclaim = getAssertionAttrValue(assertion, "NumberToReclaim");
/*     */                 
/* 121 */                 if (logger.isLoggable(Level.FINE)) {
/* 122 */                   logger.log(Level.FINE, MessagesMessages.WSTCP_1130_CONNECTION_MNGMNT_SETTINGS_LOADED(Integer.valueOf(highWatermark), Integer.valueOf(maxParallelConnections), Integer.valueOf(numberToReclaim)));
/*     */                 }
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 128 */                 return new ConnectionManagementSettings(highWatermark, maxParallelConnections, numberToReclaim);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/* 134 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 137 */     return new ConnectionManagementSettings(-1, -1, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getAssertionAttrValue(PolicyAssertion assertion, String attrName) {
/* 142 */     String strValue = assertion.getAttributeValue(new QName(attrName));
/* 143 */     if (strValue != null) {
/* 144 */       strValue = strValue.trim();
/* 145 */       return Integer.parseInt(strValue);
/*     */     } 
/*     */     
/* 148 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\wsit\PolicyConnectionManagementSettingsHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */