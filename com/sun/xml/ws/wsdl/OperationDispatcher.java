/*     */ package com.sun.xml.ws.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class OperationDispatcher
/*     */ {
/*     */   private List<WSDLOperationFinder> opFinders;
/*     */   private WSBinding binding;
/*     */   
/*     */   public OperationDispatcher(@NotNull WSDLPort wsdlModel, @NotNull WSBinding binding, @Nullable SEIModel seiModel) {
/*  73 */     this.binding = binding;
/*  74 */     this.opFinders = new ArrayList<WSDLOperationFinder>();
/*  75 */     if (binding.getAddressingVersion() != null) {
/*  76 */       this.opFinders.add(new ActionBasedOperationFinder(wsdlModel, binding, seiModel));
/*     */     }
/*  78 */     this.opFinders.add(new PayloadQNameBasedOperationFinder(wsdlModel, binding, seiModel));
/*  79 */     this.opFinders.add(new SOAPActionBasedOperationFinder(wsdlModel, binding, seiModel));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public QName getWSDLOperationQName(Packet request) throws DispatchException {
/*  90 */     WSDLOperationMapping m = getWSDLOperationMapping(request);
/*  91 */     return (m != null) ? m.getOperationName() : null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
/*  96 */     for (WSDLOperationFinder finder : this.opFinders) {
/*  97 */       WSDLOperationMapping opName = finder.getWSDLOperationMapping(request);
/*  98 */       if (opName != null) {
/*  99 */         return opName;
/*     */       }
/*     */     } 
/* 102 */     String err = MessageFormat.format("Request=[SOAPAction={0},Payload='{'{1}'}'{2}]", new Object[] { request.soapAction, request.getMessage().getPayloadNamespaceURI(), request.getMessage().getPayloadLocalPart() });
/*     */ 
/*     */     
/* 105 */     String faultString = ServerMessages.DISPATCH_CANNOT_FIND_METHOD(err);
/* 106 */     Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(this.binding.getSOAPVersion(), faultString, (this.binding.getSOAPVersion()).faultCodeClient);
/*     */     
/* 108 */     throw new DispatchException(faultMsg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\OperationDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */