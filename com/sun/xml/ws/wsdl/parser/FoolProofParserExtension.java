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
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FoolProofParserExtension
/*     */   extends DelegatingParserExtension
/*     */ {
/*     */   public FoolProofParserExtension(WSDLParserExtension core) {
/*  67 */     super(core);
/*     */   }
/*     */   
/*     */   private QName pre(XMLStreamReader xsr) {
/*  71 */     return xsr.getName();
/*     */   }
/*     */   
/*     */   private boolean post(QName tagName, XMLStreamReader xsr, boolean result) {
/*  75 */     if (!tagName.equals(xsr.getName()))
/*  76 */       return foundFool(); 
/*  77 */     if (result) {
/*  78 */       if (xsr.getEventType() != 2) {
/*  79 */         foundFool();
/*     */       }
/*  81 */     } else if (xsr.getEventType() != 1) {
/*  82 */       foundFool();
/*     */     } 
/*  84 */     return result;
/*     */   }
/*     */   
/*     */   private boolean foundFool() {
/*  88 */     throw new AssertionError("XMLStreamReader is placed at the wrong place after invoking " + this.core);
/*     */   }
/*     */   
/*     */   public boolean serviceElements(WSDLService service, XMLStreamReader reader) {
/*  92 */     return post(pre(reader), reader, super.serviceElements(service, reader));
/*     */   }
/*     */   
/*     */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/*  96 */     return post(pre(reader), reader, super.portElements(port, reader));
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/* 100 */     return post(pre(reader), reader, super.definitionsElements(reader));
/*     */   }
/*     */   
/*     */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/* 104 */     return post(pre(reader), reader, super.bindingElements(binding, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeElements(WSDLPortType portType, XMLStreamReader reader) {
/* 108 */     return post(pre(reader), reader, super.portTypeElements(portType, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationElements(WSDLOperation operation, XMLStreamReader reader) {
/* 112 */     return post(pre(reader), reader, super.portTypeOperationElements(operation, reader));
/*     */   }
/*     */   
/*     */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 116 */     return post(pre(reader), reader, super.bindingOperationElements(operation, reader));
/*     */   }
/*     */   
/*     */   public boolean messageElements(WSDLMessage msg, XMLStreamReader reader) {
/* 120 */     return post(pre(reader), reader, super.messageElements(msg, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInputElements(WSDLInput input, XMLStreamReader reader) {
/* 124 */     return post(pre(reader), reader, super.portTypeOperationInputElements(input, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutputElements(WSDLOutput output, XMLStreamReader reader) {
/* 128 */     return post(pre(reader), reader, super.portTypeOperationOutputElements(output, reader));
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFaultElements(WSDLFault fault, XMLStreamReader reader) {
/* 132 */     return post(pre(reader), reader, super.portTypeOperationFaultElements(fault, reader));
/*     */   }
/*     */   
/*     */   public boolean bindingOperationInputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 136 */     return super.bindingOperationInputElements(operation, reader);
/*     */   }
/*     */   
/*     */   public boolean bindingOperationOutputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 140 */     return post(pre(reader), reader, super.bindingOperationOutputElements(operation, reader));
/*     */   }
/*     */   
/*     */   public boolean bindingOperationFaultElements(WSDLBoundFault fault, XMLStreamReader reader) {
/* 144 */     return post(pre(reader), reader, super.bindingOperationFaultElements(fault, reader));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\FoolProofParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */