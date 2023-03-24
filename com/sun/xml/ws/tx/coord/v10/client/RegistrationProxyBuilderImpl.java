/*     */ package com.sun.xml.ws.tx.coord.v10.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.RegistrationIF;
/*     */ import com.sun.xml.ws.tx.coord.common.client.RegistrationProxyBuilder;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegisterType;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegistrationCoordinatorPortType;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import javax.xml.ws.EndpointReference;
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
/*     */ public class RegistrationProxyBuilderImpl
/*     */   extends RegistrationProxyBuilder
/*     */ {
/*     */   public RegistrationProxyBuilderImpl() {
/*  64 */     feature((WebServiceFeature)new MemberSubmissionAddressingFeature());
/*     */   }
/*     */   
/*     */   protected String getDefaultCallbackAddress() {
/*  68 */     return WSATHelper.V10.getRegistrationRequesterAddress();
/*     */   }
/*     */   
/*     */   protected EndpointReferenceBuilder getEndpointReferenceBuilder() {
/*  72 */     return EndpointReferenceBuilder.MemberSubmission();
/*     */   }
/*     */   
/*     */   public RegistrationIF<MemberSubmissionEndpointReference, RegisterType, RegisterResponseType> build() {
/*  76 */     super.build();
/*  77 */     return (RegistrationIF<MemberSubmissionEndpointReference, RegisterType, RegisterResponseType>)new RegistrationProxyImpl();
/*     */   }
/*     */   
/*  80 */   private static final RegistrationServiceV10 service = new RegistrationServiceV10();
/*     */   
/*     */   class RegistrationProxyImpl extends RegistrationProxyBuilder.RegistrationProxyF<MemberSubmissionEndpointReference, RegisterType, RegisterResponseType, RegistrationCoordinatorPortType> {
/*     */     private RegistrationCoordinatorPortType port;
/*     */     
/*     */     RegistrationProxyImpl() {
/*  86 */       super(RegistrationProxyBuilderImpl.this);
/*  87 */       this.port = RegistrationProxyBuilderImpl.service.getRegistrationCoordinatorPortTypePort(RegistrationProxyBuilderImpl.this.to, RegistrationProxyBuilderImpl.this.getEnabledFeatures());
/*     */     }
/*     */     
/*     */     public RegistrationCoordinatorPortType getDelegate() {
/*  91 */       return this.port;
/*     */     }
/*     */     
/*     */     public void asyncRegister(RegisterType parameters) {
/*  95 */       this.port.registerOperation(parameters);
/*  96 */       closePort();
/*     */     }
/*     */     
/*     */     private void closePort() {
/*     */       try {
/* 101 */         ((Closeable)this.port).close();
/* 102 */       } catch (IOException e) {
/* 103 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public AddressingVersion getAddressingVersion() {
/* 108 */       return AddressingVersion.MEMBER;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\client\RegistrationProxyBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */