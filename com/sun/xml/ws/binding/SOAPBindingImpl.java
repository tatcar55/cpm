/*     */ package com.sun.xml.ws.binding;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import javax.xml.ws.soap.SOAPBinding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SOAPBindingImpl
/*     */   extends BindingImpl
/*     */   implements SOAPBinding
/*     */ {
/*     */   public static final String X_SOAP12HTTP_BINDING = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/";
/*     */   private static final String ROLE_NONE = "http://www.w3.org/2003/05/soap-envelope/role/none";
/*     */   protected final SOAPVersion soapVersion;
/*  73 */   private Set<QName> portKnownHeaders = Collections.emptySet();
/*  74 */   private Set<QName> bindingUnderstoodHeaders = new HashSet<QName>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SOAPBindingImpl(BindingID bindingId) {
/*  82 */     this(bindingId, EMPTY_FEATURES);
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
/*     */   SOAPBindingImpl(BindingID bindingId, WebServiceFeature... features) {
/*  95 */     super(bindingId, features);
/*  96 */     this.soapVersion = bindingId.getSOAPVersion();
/*     */     
/*  98 */     setRoles(new HashSet<String>());
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.features.addAll((Iterable<WebServiceFeature>)bindingId.createBuiltinFeatureList());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortKnownHeaders(@NotNull Set<QName> headers) {
/* 113 */     this.portKnownHeaders = headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean understandsHeader(QName header) {
/* 122 */     return (this.serviceMode == Service.Mode.MESSAGE || this.portKnownHeaders.contains(header) || this.bindingUnderstoodHeaders.contains(header));
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
/*     */   public void setHandlerChain(List<Handler> chain) {
/* 134 */     setHandlerConfig(new HandlerConfiguration(getHandlerConfig().getRoles(), chain));
/*     */   }
/*     */   
/*     */   protected void addRequiredRoles(Set<String> roles) {
/* 138 */     roles.addAll(this.soapVersion.requiredRoles);
/*     */   }
/*     */   
/*     */   public Set<String> getRoles() {
/* 142 */     return getHandlerConfig().getRoles();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoles(Set<String> roles) {
/* 151 */     if (roles == null) {
/* 152 */       roles = new HashSet<String>();
/*     */     }
/* 154 */     if (roles.contains("http://www.w3.org/2003/05/soap-envelope/role/none")) {
/* 155 */       throw new WebServiceException(ClientMessages.INVALID_SOAP_ROLE_NONE());
/*     */     }
/* 157 */     addRequiredRoles(roles);
/* 158 */     setHandlerConfig(new HandlerConfiguration(roles, getHandlerConfig()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMTOMEnabled() {
/* 166 */     return isFeatureEnabled((Class)MTOMFeature.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMTOMEnabled(boolean b) {
/* 173 */     this.features.setMTOMEnabled(b);
/*     */   }
/*     */   
/*     */   public SOAPFactory getSOAPFactory() {
/* 177 */     return this.soapVersion.getSOAPFactory();
/*     */   }
/*     */   
/*     */   public MessageFactory getMessageFactory() {
/* 181 */     return this.soapVersion.getMessageFactory();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\binding\SOAPBindingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */