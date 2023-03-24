/*     */ package com.sun.xml.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.ws.resources.AddressingMessages;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ final class ActionBasedOperationFinder
/*     */   extends WSDLOperationFinder
/*     */ {
/*  80 */   private static final Logger LOGGER = Logger.getLogger(ActionBasedOperationFinder.class.getName());
/*     */   private final Map<ActionBasedOperationSignature, WSDLOperationMapping> uniqueOpSignatureMap;
/*     */   private final Map<String, WSDLOperationMapping> actionMap;
/*     */   @NotNull
/*     */   private final AddressingVersion av;
/*     */   
/*     */   public ActionBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
/*  87 */     super(wsdlModel, binding, seiModel);
/*     */     
/*  89 */     assert binding.getAddressingVersion() != null;
/*  90 */     this.av = binding.getAddressingVersion();
/*  91 */     this.uniqueOpSignatureMap = new HashMap<ActionBasedOperationSignature, WSDLOperationMapping>();
/*  92 */     this.actionMap = new HashMap<String, WSDLOperationMapping>();
/*     */     
/*  94 */     if (seiModel != null) {
/*  95 */       for (JavaMethodImpl m : ((AbstractSEIModelImpl)seiModel).getJavaMethods()) {
/*  96 */         if ((m.getMEP()).isAsync) {
/*     */           continue;
/*     */         }
/*  99 */         String action = m.getInputAction();
/* 100 */         QName payloadName = m.getRequestPayloadName();
/* 101 */         if (payloadName == null) {
/* 102 */           payloadName = PayloadQNameBasedOperationFinder.EMPTY_PAYLOAD;
/*     */         }
/* 104 */         if ((action == null || action.equals("")) && 
/* 105 */           m.getOperation() != null) action = m.getOperation().getOperation().getInput().getAction();
/*     */ 
/*     */         
/* 108 */         if (action != null) {
/* 109 */           ActionBasedOperationSignature opSignature = new ActionBasedOperationSignature(action, payloadName);
/* 110 */           if (this.uniqueOpSignatureMap.get(opSignature) != null) {
/* 111 */             LOGGER.warning(AddressingMessages.NON_UNIQUE_OPERATION_SIGNATURE(this.uniqueOpSignatureMap.get(opSignature), m.getOperationQName(), action, payloadName));
/*     */           }
/*     */           
/* 114 */           this.uniqueOpSignatureMap.put(opSignature, wsdlOperationMapping(m));
/* 115 */           this.actionMap.put(action, wsdlOperationMapping(m));
/*     */         } 
/*     */       } 
/*     */     } else {
/* 119 */       for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
/* 120 */         QName payloadName = wsdlOp.getReqPayloadName();
/* 121 */         if (payloadName == null)
/* 122 */           payloadName = PayloadQNameBasedOperationFinder.EMPTY_PAYLOAD; 
/* 123 */         String action = wsdlOp.getOperation().getInput().getAction();
/* 124 */         ActionBasedOperationSignature opSignature = new ActionBasedOperationSignature(action, payloadName);
/*     */         
/* 126 */         if (this.uniqueOpSignatureMap.get(opSignature) != null) {
/* 127 */           LOGGER.warning(AddressingMessages.NON_UNIQUE_OPERATION_SIGNATURE(this.uniqueOpSignatureMap.get(opSignature), wsdlOp.getName(), action, payloadName));
/*     */         }
/*     */ 
/*     */         
/* 131 */         this.uniqueOpSignatureMap.put(opSignature, wsdlOperationMapping(wsdlOp));
/* 132 */         this.actionMap.put(action, wsdlOperationMapping(wsdlOp));
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
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*     */     QName payloadName;
/* 151 */     MessageHeaders hl = request.getMessage().getMessageHeaders();
/* 152 */     String action = AddressingUtils.getAction(hl, this.av, this.binding.getSOAPVersion());
/*     */     
/* 154 */     if (action == null)
/*     */     {
/* 156 */       return null;
/*     */     }
/* 158 */     Message message = request.getMessage();
/*     */     
/* 160 */     String localPart = message.getPayloadLocalPart();
/* 161 */     if (localPart == null) {
/* 162 */       payloadName = PayloadQNameBasedOperationFinder.EMPTY_PAYLOAD;
/*     */     } else {
/* 164 */       String nsUri = message.getPayloadNamespaceURI();
/* 165 */       if (nsUri == null)
/* 166 */         nsUri = ""; 
/* 167 */       payloadName = new QName(nsUri, localPart);
/*     */     } 
/*     */     
/* 170 */     WSDLOperationMapping opMapping = this.uniqueOpSignatureMap.get(new ActionBasedOperationSignature(action, payloadName));
/* 171 */     if (opMapping != null) {
/* 172 */       return opMapping;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 177 */     opMapping = this.actionMap.get(action);
/* 178 */     if (opMapping != null) {
/* 179 */       return opMapping;
/*     */     }
/*     */     
/* 182 */     Message result = Messages.create(action, this.av, this.binding.getSOAPVersion());
/*     */     
/* 184 */     throw new DispatchException(result);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\ActionBasedOperationFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */