/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.addressing.policy.AddressingFeatureConfigurator;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.encoding.policy.FastInfosetFeatureConfigurator;
/*     */ import com.sun.xml.ws.encoding.policy.MtomFeatureConfigurator;
/*     */ import com.sun.xml.ws.encoding.policy.SelectOptimalEncodingFeatureConfigurator;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public class PolicyUtil
/*     */ {
/*  71 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtil.class);
/*  72 */   private static final Collection<PolicyFeatureConfigurator> CONFIGURATORS = new LinkedList<PolicyFeatureConfigurator>();
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  77 */     CONFIGURATORS.add(new AddressingFeatureConfigurator());
/*  78 */     CONFIGURATORS.add(new MtomFeatureConfigurator());
/*  79 */     CONFIGURATORS.add(new FastInfosetFeatureConfigurator());
/*  80 */     CONFIGURATORS.add(new SelectOptimalEncodingFeatureConfigurator());
/*     */ 
/*     */     
/*  83 */     addServiceProviders(CONFIGURATORS, PolicyFeatureConfigurator.class);
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
/*     */   public static <T> void addServiceProviders(Collection<T> providers, Class<T> service) {
/*  95 */     Iterator<T> foundProviders = ServiceFinder.find(service).iterator();
/*  96 */     while (foundProviders.hasNext()) {
/*  97 */       providers.add(foundProviders.next());
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
/*     */   public static void configureModel(WSDLModel model, PolicyMap policyMap) throws PolicyException {
/* 111 */     LOGGER.entering(new Object[] { model, policyMap });
/* 112 */     for (WSDLService service : model.getServices().values()) {
/* 113 */       for (WSDLPort port : service.getPorts()) {
/* 114 */         Collection<WebServiceFeature> features = getPortScopedFeatures(policyMap, service.getName(), port.getName());
/* 115 */         for (WebServiceFeature feature : features) {
/* 116 */           port.addFeature(feature);
/* 117 */           port.getBinding().addFeature(feature);
/*     */         } 
/*     */       } 
/*     */     } 
/* 121 */     LOGGER.exiting();
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
/*     */   public static Collection<WebServiceFeature> getPortScopedFeatures(PolicyMap policyMap, QName serviceName, QName portName) {
/* 134 */     LOGGER.entering(new Object[] { policyMap, serviceName, portName });
/* 135 */     Collection<WebServiceFeature> features = new ArrayList<WebServiceFeature>();
/*     */     try {
/* 137 */       PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/* 138 */       for (PolicyFeatureConfigurator configurator : CONFIGURATORS) {
/* 139 */         Collection<WebServiceFeature> additionalFeatures = configurator.getFeatures(key, policyMap);
/* 140 */         if (additionalFeatures != null) {
/* 141 */           features.addAll(additionalFeatures);
/*     */         }
/*     */       } 
/* 144 */     } catch (PolicyException e) {
/* 145 */       throw new WebServiceException(e);
/*     */     } 
/* 147 */     LOGGER.exiting(features);
/* 148 */     return features;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\PolicyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */