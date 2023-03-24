/*     */ package com.sun.xml.ws.binding;
/*     */ 
/*     */ import com.oracle.webservices.api.EnvelopeStyleFeature;
/*     */ import com.oracle.webservices.api.message.MessageContextFactory;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.ws.developer.BindingTypeFeature;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BindingImpl
/*     */   implements WSBinding
/*     */ {
/*  85 */   protected static final WebServiceFeature[] EMPTY_FEATURES = new WebServiceFeature[0];
/*     */   
/*     */   private HandlerConfiguration handlerConfig;
/*     */   
/*  89 */   private final Set<QName> addedHeaders = new HashSet<QName>();
/*  90 */   private final Set<QName> knownHeaders = new HashSet<QName>();
/*  91 */   private final Set<QName> unmodKnownHeaders = Collections.unmodifiableSet(this.knownHeaders);
/*     */   
/*     */   private final BindingID bindingId;
/*     */   
/*     */   protected final WebServiceFeatureList features;
/*  96 */   protected final Map<QName, WebServiceFeatureList> operationFeatures = new HashMap<QName, WebServiceFeatureList>();
/*     */   
/*  98 */   protected final Map<QName, WebServiceFeatureList> inputMessageFeatures = new HashMap<QName, WebServiceFeatureList>();
/*     */   
/* 100 */   protected final Map<QName, WebServiceFeatureList> outputMessageFeatures = new HashMap<QName, WebServiceFeatureList>();
/*     */   
/* 102 */   protected final Map<MessageKey, WebServiceFeatureList> faultMessageFeatures = new HashMap<MessageKey, WebServiceFeatureList>();
/*     */   
/* 104 */   protected Service.Mode serviceMode = Service.Mode.PAYLOAD;
/*     */   
/*     */   protected MessageContextFactory messageContextFactory;
/*     */   
/*     */   protected BindingImpl(BindingID bindingId, WebServiceFeature... features) {
/* 109 */     this.bindingId = bindingId;
/* 110 */     this.handlerConfig = new HandlerConfiguration(Collections.emptySet(), Collections.emptyList());
/* 111 */     if (this.handlerConfig.getHandlerKnownHeaders() != null)
/* 112 */       this.knownHeaders.addAll(this.handlerConfig.getHandlerKnownHeaders()); 
/* 113 */     this.features = new WebServiceFeatureList(features);
/* 114 */     this.features.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Handler> getHandlerChain() {
/* 120 */     return this.handlerConfig.getHandlerChain();
/*     */   }
/*     */   
/*     */   public HandlerConfiguration getHandlerConfig() {
/* 124 */     return this.handlerConfig;
/*     */   }
/*     */   
/*     */   protected void setHandlerConfig(HandlerConfiguration handlerConfig) {
/* 128 */     this.handlerConfig = handlerConfig;
/* 129 */     this.knownHeaders.clear();
/* 130 */     this.knownHeaders.addAll(this.addedHeaders);
/* 131 */     if (handlerConfig != null && handlerConfig.getHandlerKnownHeaders() != null)
/* 132 */       this.knownHeaders.addAll(handlerConfig.getHandlerKnownHeaders()); 
/*     */   }
/*     */   
/*     */   public void setMode(@NotNull Service.Mode mode) {
/* 136 */     this.serviceMode = mode;
/*     */   }
/*     */   
/*     */   public Set<QName> getKnownHeaders() {
/* 140 */     return this.unmodKnownHeaders;
/*     */   }
/*     */   
/*     */   public boolean addKnownHeader(QName headerQName) {
/* 144 */     this.addedHeaders.add(headerQName);
/* 145 */     return this.knownHeaders.add(headerQName);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public BindingID getBindingId() {
/* 151 */     return this.bindingId;
/*     */   }
/*     */   
/*     */   public final SOAPVersion getSOAPVersion() {
/* 155 */     return this.bindingId.getSOAPVersion();
/*     */   }
/*     */   
/*     */   public AddressingVersion getAddressingVersion() {
/*     */     AddressingVersion addressingVersion;
/* 160 */     if (this.features.isEnabled((Class)AddressingFeature.class)) {
/* 161 */       addressingVersion = AddressingVersion.W3C;
/* 162 */     } else if (this.features.isEnabled((Class)MemberSubmissionAddressingFeature.class)) {
/* 163 */       addressingVersion = AddressingVersion.MEMBER;
/*     */     } else {
/* 165 */       addressingVersion = null;
/* 166 */     }  return addressingVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final Codec createCodec() {
/* 172 */     return this.bindingId.createEncoder(this);
/*     */   }
/*     */   
/*     */   public static BindingImpl create(@NotNull BindingID bindingId) {
/* 176 */     if (bindingId.equals(BindingID.XML_HTTP)) {
/* 177 */       return new HTTPBindingImpl();
/*     */     }
/* 179 */     return new SOAPBindingImpl(bindingId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BindingImpl create(@NotNull BindingID bindingId, WebServiceFeature[] features) {
/* 184 */     for (WebServiceFeature feature : features) {
/* 185 */       if (feature instanceof BindingTypeFeature) {
/* 186 */         BindingTypeFeature f = (BindingTypeFeature)feature;
/* 187 */         bindingId = BindingID.parse(f.getBindingId());
/*     */       } 
/*     */     } 
/* 190 */     if (bindingId.equals(BindingID.XML_HTTP)) {
/* 191 */       return new HTTPBindingImpl(features);
/*     */     }
/* 193 */     return new SOAPBindingImpl(bindingId, features);
/*     */   }
/*     */   
/*     */   public static WSBinding getDefaultBinding() {
/* 197 */     return new SOAPBindingImpl((BindingID)BindingID.SOAP11_HTTP);
/*     */   }
/*     */   
/*     */   public String getBindingID() {
/* 201 */     return this.bindingId.toString();
/*     */   }
/*     */   @Nullable
/*     */   public <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType) {
/* 205 */     return this.features.get(featureType);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <F extends WebServiceFeature> F getOperationFeature(@NotNull Class<F> featureType, @NotNull QName operationName) {
/* 210 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 211 */     return FeatureListUtil.mergeFeature(featureType, operationFeatureList, this.features);
/*     */   }
/*     */   
/*     */   public boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> feature) {
/* 215 */     return this.features.isEnabled(feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOperationFeatureEnabled(@NotNull Class<? extends WebServiceFeature> featureType, @NotNull QName operationName) {
/* 220 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 221 */     return FeatureListUtil.isFeatureEnabled(featureType, operationFeatureList, this.features);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeatureList getFeatures() {
/* 227 */     if (!isFeatureEnabled((Class)EnvelopeStyleFeature.class)) {
/* 228 */       WebServiceFeature[] f = { (WebServiceFeature)getSOAPVersion().toFeature() };
/* 229 */       this.features.mergeFeatures(f, false);
/*     */     } 
/* 231 */     return this.features;
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceFeatureList getOperationFeatures(@NotNull QName operationName) {
/* 235 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 236 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, this.features });
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceFeatureList getInputMessageFeatures(@NotNull QName operationName) {
/* 240 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 241 */     WebServiceFeatureList messageFeatureList = this.inputMessageFeatures.get(operationName);
/* 242 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, messageFeatureList, this.features });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeatureList getOutputMessageFeatures(@NotNull QName operationName) {
/* 247 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 248 */     WebServiceFeatureList messageFeatureList = this.outputMessageFeatures.get(operationName);
/* 249 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, messageFeatureList, this.features });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeatureList getFaultMessageFeatures(@NotNull QName operationName, @NotNull QName messageName) {
/* 254 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 255 */     WebServiceFeatureList messageFeatureList = this.faultMessageFeatures.get(new MessageKey(operationName, messageName));
/*     */     
/* 257 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, messageFeatureList, this.features });
/*     */   }
/*     */   
/*     */   public void setOperationFeatures(@NotNull QName operationName, WebServiceFeature... newFeatures) {
/* 261 */     if (newFeatures != null) {
/* 262 */       WebServiceFeatureList featureList = this.operationFeatures.get(operationName);
/* 263 */       if (featureList == null) {
/* 264 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 266 */       for (WebServiceFeature f : newFeatures) {
/* 267 */         featureList.add(f);
/*     */       }
/* 269 */       this.operationFeatures.put(operationName, featureList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setInputMessageFeatures(@NotNull QName operationName, WebServiceFeature... newFeatures) {
/* 274 */     if (newFeatures != null) {
/* 275 */       WebServiceFeatureList featureList = this.inputMessageFeatures.get(operationName);
/* 276 */       if (featureList == null) {
/* 277 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 279 */       for (WebServiceFeature f : newFeatures) {
/* 280 */         featureList.add(f);
/*     */       }
/* 282 */       this.inputMessageFeatures.put(operationName, featureList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOutputMessageFeatures(@NotNull QName operationName, WebServiceFeature... newFeatures) {
/* 287 */     if (newFeatures != null) {
/* 288 */       WebServiceFeatureList featureList = this.outputMessageFeatures.get(operationName);
/* 289 */       if (featureList == null) {
/* 290 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 292 */       for (WebServiceFeature f : newFeatures) {
/* 293 */         featureList.add(f);
/*     */       }
/* 295 */       this.outputMessageFeatures.put(operationName, featureList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFaultMessageFeatures(@NotNull QName operationName, @NotNull QName messageName, WebServiceFeature... newFeatures) {
/* 300 */     if (newFeatures != null) {
/* 301 */       MessageKey key = new MessageKey(operationName, messageName);
/* 302 */       WebServiceFeatureList featureList = this.faultMessageFeatures.get(key);
/* 303 */       if (featureList == null) {
/* 304 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 306 */       for (WebServiceFeature f : newFeatures) {
/* 307 */         featureList.add(f);
/*     */       }
/* 309 */       this.faultMessageFeatures.put(key, featureList);
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public synchronized MessageContextFactory getMessageContextFactory() {
/* 314 */     if (this.messageContextFactory == null) {
/* 315 */       this.messageContextFactory = MessageContextFactory.createFactory(getFeatures().toArray());
/*     */     }
/* 317 */     return this.messageContextFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class MessageKey
/*     */   {
/*     */     private final QName operationName;
/*     */     
/*     */     private final QName messageName;
/*     */ 
/*     */     
/*     */     public MessageKey(QName operationName, QName messageName) {
/* 330 */       this.operationName = operationName;
/* 331 */       this.messageName = messageName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 336 */       int hashFirst = (this.operationName != null) ? this.operationName.hashCode() : 0;
/* 337 */       int hashSecond = (this.messageName != null) ? this.messageName.hashCode() : 0;
/*     */       
/* 339 */       return (hashFirst + hashSecond) * hashSecond + hashFirst;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 344 */       if (obj == null) {
/* 345 */         return false;
/*     */       }
/* 347 */       if (getClass() != obj.getClass()) {
/* 348 */         return false;
/*     */       }
/* 350 */       MessageKey other = (MessageKey)obj;
/* 351 */       if (this.operationName != other.operationName && (this.operationName == null || !this.operationName.equals(other.operationName))) {
/* 352 */         return false;
/*     */       }
/* 354 */       if (this.messageName != other.messageName && (this.messageName == null || !this.messageName.equals(other.messageName))) {
/* 355 */         return false;
/*     */       }
/* 357 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 362 */       return "(" + this.operationName + ", " + this.messageName + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\binding\BindingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */