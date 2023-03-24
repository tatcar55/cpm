/*     */ package com.sun.xml.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSDLOperationFinder
/*     */ {
/*     */   protected final WSDLPort wsdlModel;
/*     */   protected final WSBinding binding;
/*     */   protected final SEIModel seiModel;
/*     */   
/*     */   public WSDLOperationFinder(@NotNull WSDLPort wsdlModel, @NotNull WSBinding binding, @Nullable SEIModel seiModel) {
/*  70 */     this.wsdlModel = wsdlModel;
/*  71 */     this.binding = binding;
/*  72 */     this.seiModel = seiModel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getWSDLOperationQName(Packet request) throws DispatchException {
/*  89 */     WSDLOperationMapping m = getWSDLOperationMapping(request);
/*  90 */     return (m != null) ? m.getOperationName() : null;
/*     */   }
/*     */   
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   protected WSDLOperationMapping wsdlOperationMapping(JavaMethodImpl j) {
/*  98 */     return new WSDLOperationMappingImpl(j.getOperation(), j);
/*     */   }
/*     */   
/*     */   protected WSDLOperationMapping wsdlOperationMapping(WSDLBoundOperation o) {
/* 102 */     return new WSDLOperationMappingImpl(o, null);
/*     */   }
/*     */   
/*     */   static class WSDLOperationMappingImpl implements WSDLOperationMapping {
/*     */     private WSDLBoundOperation wsdlOperation;
/*     */     private JavaMethod javaMethod;
/*     */     private QName operationName;
/*     */     
/*     */     WSDLOperationMappingImpl(WSDLBoundOperation wsdlOperation, JavaMethodImpl javaMethod) {
/* 111 */       this.wsdlOperation = wsdlOperation;
/* 112 */       this.javaMethod = (JavaMethod)javaMethod;
/* 113 */       this.operationName = (javaMethod != null) ? javaMethod.getOperationQName() : wsdlOperation.getName();
/*     */     }
/*     */     
/*     */     public WSDLBoundOperation getWSDLBoundOperation() {
/* 117 */       return this.wsdlOperation;
/*     */     }
/*     */     
/*     */     public JavaMethod getJavaMethod() {
/* 121 */       return this.javaMethod;
/*     */     }
/*     */     
/*     */     public QName getOperationName() {
/* 125 */       return this.operationName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\WSDLOperationFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */