/*     */ package com.sun.xml.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SOAPActionBasedOperationFinder
/*     */   extends WSDLOperationFinder
/*     */ {
/*     */   private final Map<String, WSDLOperationMapping> methodHandlers;
/*     */   
/*     */   public SOAPActionBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
/*  71 */     super(wsdlModel, binding, seiModel);
/*  72 */     this.methodHandlers = new HashMap<String, WSDLOperationMapping>();
/*     */ 
/*     */     
/*  75 */     Map<String, Integer> unique = new HashMap<String, Integer>();
/*  76 */     if (seiModel != null) {
/*  77 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  78 */         String soapAction = m.getSOAPAction();
/*  79 */         Integer count = unique.get(soapAction);
/*  80 */         if (count == null) {
/*  81 */           unique.put(soapAction, Integer.valueOf(1)); continue;
/*     */         } 
/*  83 */         unique.put(soapAction, count = Integer.valueOf(count.intValue() + 1));
/*     */       } 
/*     */ 
/*     */       
/*  87 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  88 */         String soapAction = m.getSOAPAction();
/*     */ 
/*     */         
/*  91 */         if (((Integer)unique.get(soapAction)).intValue() == 1) {
/*  92 */           this.methodHandlers.put('"' + soapAction + '"', wsdlOperationMapping(m));
/*     */         }
/*     */       } 
/*     */     } else {
/*  96 */       for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
/*  97 */         this.methodHandlers.put(wsdlOp.getSOAPAction(), wsdlOperationMapping(wsdlOp));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/* 105 */     return (request.soapAction == null) ? null : this.methodHandlers.get(request.soapAction);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\SOAPActionBasedOperationFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */