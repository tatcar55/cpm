/*     */ package com.sun.xml.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.server.AsyncProvider;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.spi.db.BindingHelper;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.ServiceMode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProviderEndpointModel<T>
/*     */ {
/*     */   final boolean isAsync;
/*     */   @NotNull
/*     */   final Service.Mode mode;
/*     */   @NotNull
/*     */   final Class datatype;
/*     */   @NotNull
/*     */   final Class implClass;
/*     */   
/*     */   ProviderEndpointModel(Class<T> implementorClass, WSBinding binding) {
/*  90 */     assert implementorClass != null;
/*  91 */     assert binding != null;
/*     */     
/*  93 */     this.implClass = implementorClass;
/*  94 */     this.mode = getServiceMode(implementorClass);
/*  95 */     Class otherClass = (binding instanceof javax.xml.ws.soap.SOAPBinding) ? SOAPMessage.class : DataSource.class;
/*     */     
/*  97 */     this.isAsync = AsyncProvider.class.isAssignableFrom(implementorClass);
/*     */ 
/*     */     
/* 100 */     Class<? extends Object> baseType = this.isAsync ? (Class)AsyncProvider.class : (Class)Provider.class;
/* 101 */     Type baseParam = BindingHelper.getBaseType(implementorClass, baseType);
/* 102 */     if (baseParam == null)
/* 103 */       throw new WebServiceException(ServerMessages.NOT_IMPLEMENT_PROVIDER(implementorClass.getName())); 
/* 104 */     if (!(baseParam instanceof ParameterizedType)) {
/* 105 */       throw new WebServiceException(ServerMessages.PROVIDER_NOT_PARAMETERIZED(implementorClass.getName()));
/*     */     }
/* 107 */     ParameterizedType pt = (ParameterizedType)baseParam;
/* 108 */     Type[] types = pt.getActualTypeArguments();
/* 109 */     if (!(types[0] instanceof Class))
/* 110 */       throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(implementorClass.getName(), types[0])); 
/* 111 */     this.datatype = (Class)types[0];
/*     */     
/* 113 */     if (this.mode == Service.Mode.PAYLOAD && this.datatype != Source.class)
/*     */     {
/*     */       
/* 116 */       throw new IllegalArgumentException("Illeagal combination - Mode.PAYLOAD and Provider<" + otherClass.getName() + ">");
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
/*     */   private static Service.Mode getServiceMode(Class<?> c) {
/* 128 */     ServiceMode mode = c.<ServiceMode>getAnnotation(ServiceMode.class);
/* 129 */     return (mode == null) ? Service.Mode.PAYLOAD : mode.value();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\ProviderEndpointModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */