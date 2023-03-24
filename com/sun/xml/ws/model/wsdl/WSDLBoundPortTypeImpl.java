/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import com.sun.xml.ws.util.QNameMap;
/*     */ import com.sun.xml.ws.util.exception.LocatableWebServiceException;
/*     */ import javax.jws.WebParam;
/*     */ import javax.jws.soap.SOAPBinding;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSDLBoundPortTypeImpl
/*     */   extends AbstractFeaturedObjectImpl
/*     */   implements WSDLBoundPortType
/*     */ {
/*     */   private final QName name;
/*     */   private final QName portTypeName;
/*     */   private WSDLPortTypeImpl portType;
/*     */   private BindingID bindingId;
/*     */   @NotNull
/*     */   private final WSDLModelImpl owner;
/*  71 */   private final QNameMap<WSDLBoundOperationImpl> bindingOperations = new QNameMap();
/*     */ 
/*     */   
/*     */   private QNameMap<WSDLBoundOperationImpl> payloadMap;
/*     */ 
/*     */   
/*     */   private WSDLBoundOperationImpl emptyPayloadOperation;
/*     */ 
/*     */   
/*     */   private SOAPBinding.Style style;
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundPortTypeImpl(XMLStreamReader xsr, @NotNull WSDLModelImpl owner, QName name, QName portTypeName) {
/*  85 */     super(xsr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this.style = SOAPBinding.Style.DOCUMENT; this.owner = owner; this.name = name; this.portTypeName = portTypeName; owner.addBinding(this);
/*     */   }
/* 142 */   public QName getName() { return this.name; } @NotNull public WSDLModelImpl getOwner() { return this.owner; } public WSDLBoundOperationImpl get(QName operationName) { return (WSDLBoundOperationImpl)this.bindingOperations.get(operationName); } public void put(QName opName, WSDLBoundOperationImpl ptOp) { this.bindingOperations.put(opName, ptOp); } public void setStyle(SOAPBinding.Style style) { this.style = style; }
/*     */   public QName getPortTypeName() { return this.portTypeName; }
/*     */   public WSDLPortTypeImpl getPortType() { return this.portType; }
/*     */   public Iterable<WSDLBoundOperationImpl> getBindingOperations() { return this.bindingOperations.values(); }
/* 146 */   public BindingID getBindingId() { return (this.bindingId == null) ? (BindingID)BindingID.SOAP11_HTTP : this.bindingId; } public void setBindingId(BindingID bindingId) { this.bindingId = bindingId; } public SOAPBinding.Style getStyle() { return this.style; }
/*     */ 
/*     */   
/*     */   public boolean isRpcLit() {
/* 150 */     return (SOAPBinding.Style.RPC == this.style);
/*     */   }
/*     */   
/*     */   public boolean isDoclit() {
/* 154 */     return (SOAPBinding.Style.DOCUMENT == this.style);
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
/*     */   public ParameterBinding getBinding(QName operation, String part, WebParam.Mode mode) {
/* 167 */     WSDLBoundOperationImpl op = get(operation);
/* 168 */     if (op == null)
/*     */     {
/* 170 */       return null;
/*     */     }
/* 172 */     if (WebParam.Mode.IN == mode || WebParam.Mode.INOUT == mode) {
/* 173 */       return op.getInputBinding(part);
/*     */     }
/* 175 */     return op.getOutputBinding(part);
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
/*     */   public String getMimeType(QName operation, String part, WebParam.Mode mode) {
/* 187 */     WSDLBoundOperationImpl op = get(operation);
/* 188 */     if (WebParam.Mode.IN == mode) {
/* 189 */       return op.getMimeTypeForInputPart(part);
/*     */     }
/* 191 */     return op.getMimeTypeForOutputPart(part);
/*     */   }
/*     */   
/*     */   public WSDLBoundOperationImpl getOperation(String namespaceUri, String localName) {
/* 195 */     if (namespaceUri == null && localName == null) {
/* 196 */       return this.emptyPayloadOperation;
/*     */     }
/* 198 */     return (WSDLBoundOperationImpl)this.payloadMap.get((namespaceUri == null) ? "" : namespaceUri, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableMTOM() {
/* 203 */     this.features.add(new MTOMFeature());
/*     */   }
/*     */   
/*     */   public boolean isMTOMEnabled() {
/* 207 */     return this.features.isEnabled(MTOMFeature.class);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 211 */     return getBindingId().getSOAPVersion();
/*     */   }
/*     */   
/*     */   void freeze() {
/* 215 */     this.portType = this.owner.getPortType(this.portTypeName);
/* 216 */     if (this.portType == null) {
/* 217 */       throw new LocatableWebServiceException(ClientMessages.UNDEFINED_PORT_TYPE(this.portTypeName), new Locator[] { getLocation() });
/*     */     }
/*     */     
/* 220 */     this.portType.freeze();
/*     */     
/* 222 */     for (WSDLBoundOperationImpl op : this.bindingOperations.values()) {
/* 223 */       op.freeze(this.owner);
/*     */     }
/*     */     
/* 226 */     freezePayloadMap();
/* 227 */     this.owner.finalizeRpcLitBinding(this);
/*     */   }
/*     */   
/*     */   private void freezePayloadMap() {
/* 231 */     if (this.style == SOAPBinding.Style.RPC) {
/* 232 */       this.payloadMap = new QNameMap();
/* 233 */       for (WSDLBoundOperationImpl op : this.bindingOperations.values()) {
/* 234 */         this.payloadMap.put(op.getReqPayloadName(), op);
/*     */       }
/*     */     } else {
/* 237 */       this.payloadMap = new QNameMap();
/*     */       
/* 239 */       for (WSDLBoundOperationImpl op : this.bindingOperations.values()) {
/* 240 */         QName name = op.getReqPayloadName();
/* 241 */         if (name == null) {
/*     */           
/* 243 */           this.emptyPayloadOperation = op;
/*     */           
/*     */           continue;
/*     */         } 
/* 247 */         this.payloadMap.put(name, op);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLBoundPortTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */