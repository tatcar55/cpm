/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.model.WrapperParameter;
/*     */ import java.util.List;
/*     */ import javax.jws.soap.SOAPBinding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StubAsyncHandler
/*     */   extends StubHandler
/*     */ {
/*     */   private final Class asyncBeanClass;
/*     */   
/*     */   public StubAsyncHandler(JavaMethodImpl jm, JavaMethodImpl sync, MessageContextFactory mcf) {
/*  57 */     super(sync, mcf);
/*     */     
/*  59 */     List<ParameterImpl> rp = sync.getResponseParameters();
/*  60 */     int size = 0;
/*  61 */     for (ParameterImpl param : rp) {
/*  62 */       if (param.isWrapperStyle()) {
/*  63 */         WrapperParameter wrapParam = (WrapperParameter)param;
/*  64 */         size += wrapParam.getWrapperChildren().size();
/*  65 */         if (sync.getBinding().getStyle() == SOAPBinding.Style.DOCUMENT)
/*     */         {
/*     */           
/*  68 */           size += 2; } 
/*     */         continue;
/*     */       } 
/*  71 */       size++;
/*     */     } 
/*     */ 
/*     */     
/*  75 */     Class tempWrap = null;
/*  76 */     if (size > 1) {
/*  77 */       rp = jm.getResponseParameters();
/*  78 */       for (ParameterImpl param : rp) {
/*  79 */         if (param.isWrapperStyle()) {
/*  80 */           WrapperParameter wrapParam = (WrapperParameter)param;
/*  81 */           if (sync.getBinding().getStyle() == SOAPBinding.Style.DOCUMENT) {
/*     */             
/*  83 */             tempWrap = (Class)(wrapParam.getTypeInfo()).type;
/*     */             break;
/*     */           } 
/*  86 */           for (ParameterImpl p : wrapParam.getWrapperChildren()) {
/*  87 */             if (p.getIndex() == -1) {
/*  88 */               tempWrap = (Class)(p.getTypeInfo()).type;
/*     */               break;
/*     */             } 
/*     */           } 
/*  92 */           if (tempWrap != null)
/*     */             break; 
/*     */           continue;
/*     */         } 
/*  96 */         if (param.getIndex() == -1) {
/*  97 */           tempWrap = (Class)(param.getTypeInfo()).type;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 103 */     this.asyncBeanClass = tempWrap;
/*     */     
/* 105 */     switch (size) {
/*     */       case 0:
/* 107 */         this.responseBuilder = buildResponseBuilder(sync, ValueSetterFactory.NONE);
/*     */         return;
/*     */       case 1:
/* 110 */         this.responseBuilder = buildResponseBuilder(sync, ValueSetterFactory.SINGLE);
/*     */         return;
/*     */     } 
/* 113 */     this.responseBuilder = buildResponseBuilder(sync, new ValueSetterFactory.AsyncBeanValueSetterFactory(this.asyncBeanClass));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initArgs(Object[] args) throws Exception {
/* 119 */     if (this.asyncBeanClass != null) {
/* 120 */       args[0] = this.asyncBeanClass.newInstance();
/*     */     }
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 125 */     return ValueGetterFactory.ASYNC;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\StubAsyncHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */