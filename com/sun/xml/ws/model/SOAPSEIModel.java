/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.jws.WebParam;
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
/*     */ public class SOAPSEIModel
/*     */   extends AbstractSEIModelImpl
/*     */ {
/*     */   public SOAPSEIModel(WebServiceFeatureList features) {
/*  60 */     super(features);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateMaps() {
/*  65 */     int emptyBodyCount = 0;
/*  66 */     for (JavaMethodImpl jm : getJavaMethods()) {
/*  67 */       put(jm.getMethod(), jm);
/*  68 */       boolean bodyFound = false;
/*  69 */       for (ParameterImpl p : jm.getRequestParameters()) {
/*  70 */         ParameterBinding binding = p.getBinding();
/*  71 */         if (binding.isBody()) {
/*  72 */           put(p.getName(), jm);
/*  73 */           bodyFound = true;
/*     */         } 
/*     */       } 
/*  76 */       if (!bodyFound) {
/*  77 */         put(this.emptyBodyName, jm);
/*     */         
/*  79 */         emptyBodyCount++;
/*     */       } 
/*     */     } 
/*  82 */     if (emptyBodyCount > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<QName> getKnownHeaders() {
/*  89 */     Set<QName> headers = new HashSet<QName>();
/*  90 */     for (JavaMethodImpl method : getJavaMethods()) {
/*     */       
/*  92 */       Iterator<ParameterImpl> params = method.getRequestParameters().iterator();
/*  93 */       fillHeaders(params, headers, WebParam.Mode.IN);
/*     */ 
/*     */       
/*  96 */       params = method.getResponseParameters().iterator();
/*  97 */       fillHeaders(params, headers, WebParam.Mode.OUT);
/*     */     } 
/*  99 */     return headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillHeaders(Iterator<ParameterImpl> params, Set<QName> headers, WebParam.Mode mode) {
/* 107 */     while (params.hasNext()) {
/* 108 */       ParameterImpl param = params.next();
/* 109 */       ParameterBinding binding = (mode == WebParam.Mode.IN) ? param.getInBinding() : param.getOutBinding();
/* 110 */       QName name = param.getName();
/* 111 */       if (binding.isHeader() && !headers.contains(name))
/* 112 */         headers.add(name); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\SOAPSEIModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */