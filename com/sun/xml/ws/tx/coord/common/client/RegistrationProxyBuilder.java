/*     */ package com.sun.xml.ws.tx.coord.common.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.OneWayFeature;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.PendingRequestManager;
/*     */ import com.sun.xml.ws.tx.coord.common.RegistrationIF;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCUtil;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RegistrationProxyBuilder
/*     */ {
/*     */   protected List<WebServiceFeature> features;
/*     */   protected EndpointReference to;
/*     */   protected String txId;
/*     */   protected long timeout;
/*     */   protected String callbackAddress;
/*     */   
/*     */   public RegistrationProxyBuilder feature(WebServiceFeature feature) {
/*  68 */     if (feature == null) return this; 
/*  69 */     if (this.features == null) this.features = new ArrayList<WebServiceFeature>(); 
/*  70 */     this.features.add(feature);
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   public RegistrationProxyBuilder txIdForReference(String txId) {
/*  75 */     this.txId = txId;
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   public RegistrationProxyBuilder to(EndpointReference endpointReference) {
/*  80 */     this.to = endpointReference;
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public RegistrationProxyBuilder timeout(long timeout) {
/*  85 */     this.timeout = timeout;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public RegistrationProxyBuilder callback(String callbackAddress) {
/*  90 */     this.callbackAddress = callbackAddress;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   protected abstract String getDefaultCallbackAddress();
/*     */   
/*     */   protected abstract EndpointReferenceBuilder getEndpointReferenceBuilder();
/*     */   
/*     */   protected WebServiceFeature[] getEnabledFeatures() {
/*  99 */     return this.features.<WebServiceFeature>toArray(new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistrationIF build() {
/* 104 */     if (this.callbackAddress == null)
/* 105 */       this.callbackAddress = getDefaultCallbackAddress(); 
/* 106 */     EndpointReference epr = getEndpointReferenceBuilder().address(this.callbackAddress).referenceParameter(new Element[] { WSCUtil.referenceElementTxId(this.txId), WSCUtil.referenceElementRoutingInfo() }).build();
/*     */     
/* 108 */     WSEndpointReference wsepr = WSEndpointReference.create(epr);
/* 109 */     OneWayFeature oneway = new OneWayFeature(true, wsepr);
/* 110 */     feature((WebServiceFeature)oneway);
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   public abstract class RegistrationProxyF<T extends EndpointReference, K, P, D>
/*     */     implements RegistrationIF<T, K, P> {
/*     */     public BaseRegisterResponseType<T, P> registerOperation(BaseRegisterType<T, K> parameters) {
/*     */       try {
/* 118 */         PendingRequestManager.ResponseBox box = PendingRequestManager.reqisterRequest(RegistrationProxyBuilder.this.txId);
/* 119 */         asyncRegister((K)parameters.getDelegate());
/* 120 */         return box.getResponse(RegistrationProxyBuilder.this.timeout);
/*     */       } finally {
/* 122 */         PendingRequestManager.removeRequest(RegistrationProxyBuilder.this.txId);
/*     */       } 
/*     */     }
/*     */     
/*     */     public abstract D getDelegate();
/*     */     
/*     */     public abstract void asyncRegister(K param1K);
/*     */     
/*     */     public abstract AddressingVersion getAddressingVersion();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\client\RegistrationProxyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */