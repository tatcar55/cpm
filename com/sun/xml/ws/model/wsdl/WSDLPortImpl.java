/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import com.sun.xml.ws.util.exception.LocatableWebServiceException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSDLPortImpl
/*     */   extends AbstractFeaturedObjectImpl
/*     */   implements WSDLPort
/*     */ {
/*     */   private final QName name;
/*     */   private EndpointAddress address;
/*     */   private final QName bindingName;
/*     */   private final WSDLServiceImpl owner;
/*     */   private WSEndpointReference epr;
/*     */   private WSDLBoundPortTypeImpl boundPortType;
/*     */   
/*     */   public WSDLPortImpl(XMLStreamReader xsr, WSDLServiceImpl owner, QName name, QName binding) {
/*  75 */     super(xsr);
/*  76 */     this.owner = owner;
/*  77 */     this.name = name;
/*  78 */     this.bindingName = binding;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  82 */     return this.name;
/*     */   }
/*     */   
/*     */   public QName getBindingName() {
/*  86 */     return this.bindingName;
/*     */   }
/*     */   
/*     */   public EndpointAddress getAddress() {
/*  90 */     return this.address;
/*     */   }
/*     */   
/*     */   public WSDLServiceImpl getOwner() {
/*  94 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddress(EndpointAddress address) {
/* 101 */     assert address != null;
/* 102 */     this.address = address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEPR(@NotNull WSEndpointReference epr) {
/* 109 */     assert epr != null;
/* 110 */     addExtension((WSDLExtension)epr);
/* 111 */     this.epr = epr;
/*     */   }
/*     */   @Nullable
/*     */   public WSEndpointReference getEPR() {
/* 115 */     return this.epr;
/*     */   }
/*     */   public WSDLBoundPortTypeImpl getBinding() {
/* 118 */     return this.boundPortType;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 122 */     return this.boundPortType.getSOAPVersion();
/*     */   }
/*     */   
/*     */   void freeze(WSDLModelImpl root) {
/* 126 */     this.boundPortType = root.getBinding(this.bindingName);
/* 127 */     if (this.boundPortType == null) {
/* 128 */       throw new LocatableWebServiceException(ClientMessages.UNDEFINED_BINDING(this.bindingName), new Locator[] { getLocation() });
/*     */     }
/*     */     
/* 131 */     if (this.features == null)
/* 132 */       this.features = new WebServiceFeatureList(); 
/* 133 */     this.features.setParentFeaturedObject(this.boundPortType);
/* 134 */     this.notUnderstoodExtensions.addAll(this.boundPortType.notUnderstoodExtensions);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLPortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */