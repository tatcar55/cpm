/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ public final class WSDLBoundOperationImpl extends AbstractExtensibleImpl implements WSDLBoundOperation {
/*     */   private final QName name;
/*     */   private final Map<String, ParameterBinding> inputParts;
/*     */   private final Map<String, ParameterBinding> outputParts;
/*     */   private final Map<String, ParameterBinding> faultParts;
/*     */   private final Map<String, String> inputMimeTypes;
/*     */   private final Map<String, String> outputMimeTypes;
/*     */   private final Map<String, String> faultMimeTypes;
/*     */   private boolean explicitInputSOAPBodyParts = false;
/*     */   private boolean explicitOutputSOAPBodyParts = false;
/*     */   private boolean explicitFaultSOAPBodyParts = false;
/*     */   private Boolean emptyInputBody;
/*     */   private Boolean emptyOutputBody;
/*     */   private Boolean emptyFaultBody;
/*     */   private final Map<String, WSDLPartImpl> inParts;
/*     */   private final Map<String, WSDLPartImpl> outParts;
/*     */   private final Map<String, WSDLPartImpl> fltParts;
/*     */   private final List<WSDLBoundFaultImpl> wsdlBoundFaults;
/*     */   private WSDLOperationImpl operation;
/*     */   private String soapAction;
/*     */   private WSDLBoundOperation.ANONYMOUS anonymous;
/*     */   private final WSDLBoundPortTypeImpl owner;
/*     */   private SOAPBinding.Style style;
/*     */   private String reqNamespace;
/*     */   private String respNamespace;
/*     */   private QName requestPayloadName;
/*     */   private QName responsePayloadName;
/*     */   private boolean emptyRequestPayload;
/*     */   private boolean emptyResponsePayload;
/*     */   private Map<QName, WSDLMessageImpl> messages;
/*     */   
/*     */   public QName getName() {
/*     */     return this.name;
/*     */   }
/*     */   
/*     */   public String getSOAPAction() {
/*     */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public void setSoapAction(String soapAction) {
/*     */     this.soapAction = (soapAction != null) ? soapAction : "";
/*     */   }
/*     */   
/*     */   public WSDLPartImpl getPart(String partName, WebParam.Mode mode) {
/*     */     if (mode == WebParam.Mode.IN)
/*     */       return this.inParts.get(partName); 
/*     */     if (mode == WebParam.Mode.OUT)
/*     */       return this.outParts.get(partName); 
/*     */     return null;
/*     */   }
/*     */   
/*     */   public void addPart(WSDLPartImpl part, WebParam.Mode mode) {
/*     */     if (mode == WebParam.Mode.IN) {
/*     */       this.inParts.put(part.getName(), part);
/*     */     } else if (mode == WebParam.Mode.OUT) {
/*     */       this.outParts.put(part.getName(), part);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, ParameterBinding> getInputParts() {
/*     */     return this.inputParts;
/*     */   }
/*     */   
/*     */   public Map<String, ParameterBinding> getOutputParts() {
/*     */     return this.outputParts;
/*     */   }
/*     */   
/*     */   public Map<String, ParameterBinding> getFaultParts() {
/*     */     return this.faultParts;
/*     */   }
/*     */   
/*     */   public Map<String, WSDLPart> getInParts() {
/*     */     return Collections.unmodifiableMap((Map)this.inParts);
/*     */   }
/*     */   
/*     */   public Map<String, WSDLPart> getOutParts() {
/*     */     return Collections.unmodifiableMap((Map)this.outParts);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<WSDLBoundFaultImpl> getFaults() {
/*     */     return this.wsdlBoundFaults;
/*     */   }
/*     */   
/*     */   public void addFault(@NotNull WSDLBoundFaultImpl fault) {
/*     */     this.wsdlBoundFaults.add(fault);
/*     */   }
/*     */   
/*     */   public Map<String, String> getInputMimeTypes() {
/*     */     return this.inputMimeTypes;
/*     */   }
/*     */   
/*  94 */   public WSDLBoundOperationImpl(XMLStreamReader xsr, WSDLBoundPortTypeImpl owner, QName name) { super(xsr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     this.style = SOAPBinding.Style.DOCUMENT; this.name = name; this.inputParts = new HashMap<String, ParameterBinding>(); this.outputParts = new HashMap<String, ParameterBinding>(); this.faultParts = new HashMap<String, ParameterBinding>(); this.inputMimeTypes = new HashMap<String, String>(); this.outputMimeTypes = new HashMap<String, String>(); this.faultMimeTypes = new HashMap<String, String>(); this.inParts = new HashMap<String, WSDLPartImpl>(); this.outParts = new HashMap<String, WSDLPartImpl>(); this.fltParts = new HashMap<String, WSDLPartImpl>(); this.wsdlBoundFaults = new ArrayList<WSDLBoundFaultImpl>(); this.owner = owner; }
/*     */   public Map<String, String> getOutputMimeTypes() { return this.outputMimeTypes; }
/* 332 */   public Map<String, String> getFaultMimeTypes() { return this.faultMimeTypes; } public ParameterBinding getInputBinding(String part) { if (this.emptyInputBody == null) if (this.inputParts.get(" ") != null) { this.emptyInputBody = Boolean.valueOf(true); } else { this.emptyInputBody = Boolean.valueOf(false); }   ParameterBinding block = this.inputParts.get(part); if (block == null) { if (this.explicitInputSOAPBodyParts || this.emptyInputBody.booleanValue()) return ParameterBinding.UNBOUND;  return ParameterBinding.BODY; }  return block; } public ParameterBinding getOutputBinding(String part) { if (this.emptyOutputBody == null) if (this.outputParts.get(" ") != null) { this.emptyOutputBody = Boolean.valueOf(true); } else { this.emptyOutputBody = Boolean.valueOf(false); }   ParameterBinding block = this.outputParts.get(part); if (block == null) { if (this.explicitOutputSOAPBodyParts || this.emptyOutputBody.booleanValue()) return ParameterBinding.UNBOUND;  return ParameterBinding.BODY; }  return block; } public ParameterBinding getFaultBinding(String part) { if (this.emptyFaultBody == null) if (this.faultParts.get(" ") != null) { this.emptyFaultBody = Boolean.valueOf(true); } else { this.emptyFaultBody = Boolean.valueOf(false); }   ParameterBinding block = this.faultParts.get(part); if (block == null) { if (this.explicitFaultSOAPBodyParts || this.emptyFaultBody.booleanValue()) return ParameterBinding.UNBOUND;  return ParameterBinding.BODY; }  return block; } public String getMimeTypeForInputPart(String part) { return this.inputMimeTypes.get(part); } public void setStyle(SOAPBinding.Style style) { this.style = style; }
/*     */   public String getMimeTypeForOutputPart(String part) { return this.outputMimeTypes.get(part); }
/*     */   public String getMimeTypeForFaultPart(String part) { return this.faultMimeTypes.get(part); }
/*     */   public WSDLOperationImpl getOperation() { return this.operation; }
/* 336 */   public WSDLBoundPortType getBoundPortType() { return this.owner; } public void setInputExplicitBodyParts(boolean b) { this.explicitInputSOAPBodyParts = b; } public void setOutputExplicitBodyParts(boolean b) { this.explicitOutputSOAPBodyParts = b; } public void setFaultExplicitBodyParts(boolean b) { this.explicitFaultSOAPBodyParts = b; } @Nullable public QName getReqPayloadName() { if (this.emptyRequestPayload) {
/* 337 */       return null;
/*     */     }
/* 339 */     if (this.requestPayloadName != null) {
/* 340 */       return this.requestPayloadName;
/*     */     }
/* 342 */     if (this.style.equals(SOAPBinding.Style.RPC)) {
/* 343 */       String ns = (getRequestNamespace() != null) ? getRequestNamespace() : this.name.getNamespaceURI();
/* 344 */       this.requestPayloadName = new QName(ns, this.name.getLocalPart());
/* 345 */       return this.requestPayloadName;
/*     */     } 
/* 347 */     QName inMsgName = this.operation.getInput().getMessage().getName();
/* 348 */     WSDLMessageImpl message = this.messages.get(inMsgName);
/* 349 */     for (WSDLPartImpl part : message.parts()) {
/* 350 */       ParameterBinding binding = getInputBinding(part.getName());
/* 351 */       if (binding.isBody()) {
/* 352 */         this.requestPayloadName = part.getDescriptor().name();
/* 353 */         return this.requestPayloadName;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 358 */     this.emptyRequestPayload = true;
/*     */ 
/*     */     
/* 361 */     return null; }
/*     */   
/*     */   @Nullable
/*     */   public QName getResPayloadName() {
/* 365 */     if (this.emptyResponsePayload) {
/* 366 */       return null;
/*     */     }
/* 368 */     if (this.responsePayloadName != null) {
/* 369 */       return this.responsePayloadName;
/*     */     }
/* 371 */     if (this.style.equals(SOAPBinding.Style.RPC)) {
/* 372 */       String ns = (getResponseNamespace() != null) ? getResponseNamespace() : this.name.getNamespaceURI();
/* 373 */       this.responsePayloadName = new QName(ns, this.name.getLocalPart() + "Response");
/* 374 */       return this.responsePayloadName;
/*     */     } 
/* 376 */     QName outMsgName = this.operation.getOutput().getMessage().getName();
/* 377 */     WSDLMessageImpl message = this.messages.get(outMsgName);
/* 378 */     for (WSDLPartImpl part : message.parts()) {
/* 379 */       ParameterBinding binding = getOutputBinding(part.getName());
/* 380 */       if (binding.isBody()) {
/* 381 */         this.responsePayloadName = part.getDescriptor().name();
/* 382 */         return this.responsePayloadName;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 387 */     this.emptyResponsePayload = true;
/*     */ 
/*     */     
/* 390 */     return null;
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
/*     */   public String getRequestNamespace() {
/* 404 */     return (this.reqNamespace != null) ? this.reqNamespace : this.name.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public void setRequestNamespace(String ns) {
/* 408 */     this.reqNamespace = ns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResponseNamespace() {
/* 419 */     return (this.respNamespace != null) ? this.respNamespace : this.name.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public void setResponseNamespace(String ns) {
/* 423 */     this.respNamespace = ns;
/*     */   }
/*     */   
/*     */   WSDLBoundPortTypeImpl getOwner() {
/* 427 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void freeze(WSDLModelImpl parent) {
/* 437 */     this.messages = parent.getMessages();
/* 438 */     this.operation = this.owner.getPortType().get(this.name.getLocalPart());
/* 439 */     for (WSDLBoundFaultImpl bf : this.wsdlBoundFaults) {
/* 440 */       bf.freeze(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAnonymous(WSDLBoundOperation.ANONYMOUS anonymous) {
/* 445 */     this.anonymous = anonymous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLBoundOperation.ANONYMOUS getAnonymous() {
/* 452 */     return this.anonymous;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLBoundOperationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */