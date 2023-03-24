/*     */ package com.sun.xml.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DelegatingParserExtension
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   protected final WSDLParserExtension core;
/*     */   
/*     */   public DelegatingParserExtension(WSDLParserExtension core) {
/*  59 */     this.core = core;
/*     */   }
/*     */   
/*     */   public void start(WSDLParserExtensionContext context) {
/*  63 */     this.core.start(context);
/*     */   }
/*     */   
/*     */   public void serviceAttributes(WSDLService service, XMLStreamReader reader) {
/*  67 */     this.core.serviceAttributes(service, reader);
/*     */   }
/*     */   
/*     */   public boolean serviceElements(WSDLService service, XMLStreamReader reader) {
/*  71 */     return this.core.serviceElements(service, reader);
/*     */   }
/*     */   
/*     */   public void portAttributes(WSDLPort port, XMLStreamReader reader) {
/*  75 */     this.core.portAttributes(port, reader);
/*     */   }
/*     */   
/*     */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/*  79 */     return this.core.portElements(port, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInput(WSDLOperation op, XMLStreamReader reader) {
/*  83 */     return this.core.portTypeOperationInput(op, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutput(WSDLOperation op, XMLStreamReader reader) {
/*  87 */     return this.core.portTypeOperationOutput(op, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFault(WSDLOperation op, XMLStreamReader reader) {
/*  91 */     return this.core.portTypeOperationFault(op, reader);
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/*  95 */     return this.core.definitionsElements(reader);
/*     */   }
/*     */   
/*     */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/*  99 */     return this.core.bindingElements(binding, reader);
/*     */   }
/*     */   
/*     */   public void bindingAttributes(WSDLBoundPortType binding, XMLStreamReader reader) {
/* 103 */     this.core.bindingAttributes(binding, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeElements(WSDLPortType portType, XMLStreamReader reader) {
/* 107 */     return this.core.portTypeElements(portType, reader);
/*     */   }
/*     */   
/*     */   public void portTypeAttributes(WSDLPortType portType, XMLStreamReader reader) {
/* 111 */     this.core.portTypeAttributes(portType, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationElements(WSDLOperation operation, XMLStreamReader reader) {
/* 115 */     return this.core.portTypeOperationElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationAttributes(WSDLOperation operation, XMLStreamReader reader) {
/* 119 */     this.core.portTypeOperationAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 123 */     return this.core.bindingOperationElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 127 */     this.core.bindingOperationAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean messageElements(WSDLMessage msg, XMLStreamReader reader) {
/* 131 */     return this.core.messageElements(msg, reader);
/*     */   }
/*     */   
/*     */   public void messageAttributes(WSDLMessage msg, XMLStreamReader reader) {
/* 135 */     this.core.messageAttributes(msg, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInputElements(WSDLInput input, XMLStreamReader reader) {
/* 139 */     return this.core.portTypeOperationInputElements(input, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationInputAttributes(WSDLInput input, XMLStreamReader reader) {
/* 143 */     this.core.portTypeOperationInputAttributes(input, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutputElements(WSDLOutput output, XMLStreamReader reader) {
/* 147 */     return this.core.portTypeOperationOutputElements(output, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationOutputAttributes(WSDLOutput output, XMLStreamReader reader) {
/* 151 */     this.core.portTypeOperationOutputAttributes(output, reader);
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFaultElements(WSDLFault fault, XMLStreamReader reader) {
/* 155 */     return this.core.portTypeOperationFaultElements(fault, reader);
/*     */   }
/*     */   
/*     */   public void portTypeOperationFaultAttributes(WSDLFault fault, XMLStreamReader reader) {
/* 159 */     this.core.portTypeOperationFaultAttributes(fault, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationInputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 163 */     return this.core.bindingOperationInputElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationInputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 167 */     this.core.bindingOperationInputAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationOutputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 171 */     return this.core.bindingOperationOutputElements(operation, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationOutputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 175 */     this.core.bindingOperationOutputAttributes(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationFaultElements(WSDLBoundFault fault, XMLStreamReader reader) {
/* 179 */     return this.core.bindingOperationFaultElements(fault, reader);
/*     */   }
/*     */   
/*     */   public void bindingOperationFaultAttributes(WSDLBoundFault fault, XMLStreamReader reader) {
/* 183 */     this.core.bindingOperationFaultAttributes(fault, reader);
/*     */   }
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 187 */     this.core.finished(context);
/*     */   }
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {
/* 191 */     this.core.postFinished(context);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\DelegatingParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */