/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLModelImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.jaxws.PolicyUtil;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortInfo
/*     */   implements WSPortInfo
/*     */ {
/*     */   @NotNull
/*     */   private final WSServiceDelegate owner;
/*     */   @NotNull
/*     */   public final QName portName;
/*     */   @NotNull
/*     */   public final EndpointAddress targetEndpoint;
/*     */   @NotNull
/*     */   public final BindingID bindingId;
/*     */   @NotNull
/*     */   public final PolicyMap policyMap;
/*     */   @Nullable
/*     */   public final WSDLPort portModel;
/*     */   
/*     */   public PortInfo(WSServiceDelegate owner, EndpointAddress targetEndpoint, QName name, BindingID bindingId) {
/*  89 */     this.owner = owner;
/*  90 */     this.targetEndpoint = targetEndpoint;
/*  91 */     this.portName = name;
/*  92 */     this.bindingId = bindingId;
/*  93 */     this.portModel = getPortModel(owner, name);
/*  94 */     this.policyMap = createPolicyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public PortInfo(@NotNull WSServiceDelegate owner, @NotNull WSDLPort port) {
/*  99 */     this.owner = owner;
/* 100 */     this.targetEndpoint = port.getAddress();
/* 101 */     this.portName = port.getName();
/* 102 */     this.bindingId = port.getBinding().getBindingId();
/* 103 */     this.portModel = port;
/* 104 */     this.policyMap = createPolicyMap();
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 108 */     return this.policyMap;
/*     */   }
/*     */   
/*     */   public PolicyMap createPolicyMap() {
/*     */     PolicyMap map;
/* 113 */     if (this.portModel != null) {
/* 114 */       map = ((WSDLModelImpl)this.portModel.getOwner().getParent()).getPolicyMap();
/*     */     } else {
/* 116 */       map = PolicyResolverFactory.create().resolve(new PolicyResolver.ClientContext(null, this.owner.getContainer()));
/*     */     } 
/*     */     
/* 119 */     if (map == null)
/* 120 */       map = PolicyMap.createPolicyMap(null); 
/* 121 */     return map;
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
/*     */   public BindingImpl createBinding(WebServiceFeature[] webServiceFeatures, Class<?> portInterface) {
/* 134 */     return createBinding(new WebServiceFeatureList(webServiceFeatures), portInterface, null);
/*     */   }
/*     */   
/*     */   public BindingImpl createBinding(WebServiceFeatureList webServiceFeatures, Class<?> portInterface, BindingImpl existingBinding) {
/*     */     Iterable<WebServiceFeature> configFeatures;
/* 139 */     if (existingBinding != null) {
/* 140 */       webServiceFeatures.addAll((Iterable)existingBinding.getFeatures());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (this.portModel != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       WSFeatureList wSFeatureList = this.portModel.getFeatures();
/*     */     } else {
/* 156 */       configFeatures = PolicyUtil.getPortScopedFeatures(this.policyMap, this.owner.getServiceName(), this.portName);
/*     */     } 
/* 158 */     webServiceFeatures.mergeFeatures(configFeatures, false);
/*     */ 
/*     */     
/* 161 */     webServiceFeatures.mergeFeatures(this.owner.serviceInterceptor.preCreateBinding(this, portInterface, (WSFeatureList)webServiceFeatures), false);
/*     */     
/* 163 */     BindingImpl bindingImpl = BindingImpl.create(this.bindingId, webServiceFeatures.toArray());
/* 164 */     this.owner.getHandlerConfigurator().configureHandlers(this, bindingImpl);
/* 165 */     return bindingImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private WSDLPort getPortModel(WSServiceDelegate owner, QName portName) {
/* 171 */     if (owner.getWsdlService() != null) {
/* 172 */       Iterable<WSDLPortImpl> ports = owner.getWsdlService().getPorts();
/* 173 */       for (WSDLPortImpl port : ports) {
/* 174 */         if (port.getName().equals(portName))
/* 175 */           return (WSDLPort)port; 
/*     */       } 
/*     */     } 
/* 178 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getPort() {
/* 187 */     return this.portModel;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSService getOwner() {
/* 192 */     return this.owner;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BindingID getBindingId() {
/* 197 */     return this.bindingId;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EndpointAddress getEndpointAddress() {
/* 202 */     return this.targetEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getServiceName() {
/* 211 */     return this.owner.getServiceName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getPortName() {
/* 219 */     return this.portName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBindingID() {
/* 228 */     return this.bindingId.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\PortInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */