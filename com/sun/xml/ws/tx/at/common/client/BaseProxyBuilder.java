/*     */ package com.sun.xml.ws.tx.at.common.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.OneWayFeature;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseProxyBuilder<T, B extends BaseProxyBuilder<T, B>>
/*     */ {
/*     */   protected WSATVersion<T> version;
/*     */   protected EndpointReference to;
/*     */   protected EndpointReference replyTo;
/*     */   protected List<WebServiceFeature> features;
/*     */   
/*     */   protected BaseProxyBuilder(WSATVersion<T> version) {
/*  65 */     this.version = version;
/*  66 */     feature(version.newAddressingFeature());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void feature(WebServiceFeature feature) {
/*  74 */     if (feature == null)
/*  75 */       return;  if (this.features == null) this.features = new ArrayList<WebServiceFeature>(); 
/*  76 */     this.features.add(feature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B to(EndpointReference to) {
/*  85 */     this.to = to;
/*  86 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B replyTo(EndpointReference replyTo) {
/*  95 */     this.replyTo = replyTo;
/*  96 */     if (replyTo != null)
/*  97 */       feature((WebServiceFeature)new OneWayFeature(true, WSEndpointReference.create(replyTo))); 
/*  98 */     return (B)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B txIdForReference(String txId, String bqual) {
/* 108 */     EndpointReference endpointReference = this.version.newEndpointReferenceBuilder().address(getDefaultCallbackAddress()).referenceParameter(new Element[] { WSCUtil.referenceElementTxId(txId), WSCUtil.referenceElementBranchQual(bqual), WSCUtil.referenceElementRoutingInfo() }).build();
/*     */ 
/*     */ 
/*     */     
/* 112 */     replyTo(endpointReference);
/* 113 */     return (B)this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebServiceFeature[] getEnabledFeatures() {
/* 118 */     return this.features.<WebServiceFeature>toArray(new WebServiceFeature[0]);
/*     */   }
/*     */   
/*     */   protected abstract String getDefaultCallbackAddress();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\client\BaseProxyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */