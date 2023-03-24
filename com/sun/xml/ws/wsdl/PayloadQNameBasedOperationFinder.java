/*     */ package com.sun.xml.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.util.QNameMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PayloadQNameBasedOperationFinder
/*     */   extends WSDLOperationFinder
/*     */ {
/*  75 */   private static final Logger LOGGER = Logger.getLogger(PayloadQNameBasedOperationFinder.class.getName());
/*     */   
/*     */   public static final String EMPTY_PAYLOAD_LOCAL = "";
/*     */   public static final String EMPTY_PAYLOAD_NSURI = "";
/*  79 */   public static final QName EMPTY_PAYLOAD = new QName("", "");
/*     */   
/*  81 */   private final QNameMap<WSDLOperationMapping> methodHandlers = new QNameMap();
/*  82 */   private final QNameMap<List<String>> unique = new QNameMap();
/*     */ 
/*     */   
/*     */   public PayloadQNameBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
/*  86 */     super(wsdlModel, binding, seiModel);
/*  87 */     if (seiModel != null) {
/*     */       
/*  89 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  90 */         if ((m.getMEP()).isAsync)
/*     */           continue; 
/*  92 */         QName name = m.getRequestPayloadName();
/*  93 */         if (name == null)
/*  94 */           name = EMPTY_PAYLOAD; 
/*  95 */         List<String> methods = (List<String>)this.unique.get(name);
/*  96 */         if (methods == null) {
/*  97 */           methods = new ArrayList<String>();
/*  98 */           this.unique.put(name, methods);
/*     */         } 
/* 100 */         methods.add(m.getMethod().getName());
/*     */       } 
/*     */ 
/*     */       
/* 104 */       for (QNameMap.Entry<List<String>> e : (Iterable<QNameMap.Entry<List<String>>>)this.unique.entrySet()) {
/* 105 */         if (((List)e.getValue()).size() > 1) {
/* 106 */           LOGGER.warning(ServerMessages.NON_UNIQUE_DISPATCH_QNAME(e.getValue(), e.createQName()));
/*     */         }
/*     */       } 
/*     */       
/* 110 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/* 111 */         QName name = m.getRequestPayloadName();
/* 112 */         if (name == null) {
/* 113 */           name = EMPTY_PAYLOAD;
/*     */         }
/*     */         
/* 116 */         if (((List)this.unique.get(name)).size() == 1) {
/* 117 */           this.methodHandlers.put(name, wsdlOperationMapping(m));
/*     */         }
/*     */       } 
/*     */     } else {
/* 121 */       for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
/* 122 */         QName name = wsdlOp.getReqPayloadName();
/* 123 */         if (name == null)
/* 124 */           name = EMPTY_PAYLOAD; 
/* 125 */         this.methodHandlers.put(name, wsdlOperationMapping(wsdlOp));
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*     */     String nsUri;
/* 141 */     Message message = request.getMessage();
/* 142 */     String localPart = message.getPayloadLocalPart();
/*     */     
/* 144 */     if (localPart == null) {
/* 145 */       localPart = "";
/* 146 */       nsUri = "";
/*     */     } else {
/* 148 */       nsUri = message.getPayloadNamespaceURI();
/* 149 */       if (nsUri == null)
/* 150 */         nsUri = ""; 
/*     */     } 
/* 152 */     WSDLOperationMapping op = (WSDLOperationMapping)this.methodHandlers.get(nsUri, localPart);
/*     */ 
/*     */     
/* 155 */     if (op == null && !this.unique.containsKey(nsUri, localPart)) {
/* 156 */       String dispatchKey = "{" + nsUri + "}" + localPart;
/* 157 */       String faultString = ServerMessages.DISPATCH_CANNOT_FIND_METHOD(dispatchKey);
/* 158 */       throw new DispatchException(SOAPFaultBuilder.createSOAPFaultMessage(this.binding.getSOAPVersion(), faultString, (this.binding.getSOAPVersion()).faultCodeClient));
/*     */     } 
/*     */     
/* 161 */     return op;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\PayloadQNameBasedOperationFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */