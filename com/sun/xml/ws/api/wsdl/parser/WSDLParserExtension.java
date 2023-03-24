/*     */ package com.sun.xml.ws.api.wsdl.parser;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSDLParserExtension
/*     */ {
/*     */   public void start(WSDLParserExtensionContext context) {}
/*     */   
/*     */   public void serviceAttributes(WSDLService service, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean serviceElements(WSDLService service, XMLStreamReader reader) {
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portAttributes(WSDLPort port, XMLStreamReader reader) {}
/*     */ 
/*     */   
/*     */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/* 172 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInput(WSDLOperation op, XMLStreamReader reader) {
/* 176 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutput(WSDLOperation op, XMLStreamReader reader) {
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFault(WSDLOperation op, XMLStreamReader reader) {
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingAttributes(WSDLBoundPortType binding, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeElements(WSDLPortType portType, XMLStreamReader reader) {
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeAttributes(WSDLPortType portType, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationElements(WSDLOperation operation, XMLStreamReader reader) {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationAttributes(WSDLOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean messageElements(WSDLMessage msg, XMLStreamReader reader) {
/* 220 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void messageAttributes(WSDLMessage msg, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationInputElements(WSDLInput input, XMLStreamReader reader) {
/* 227 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationInputAttributes(WSDLInput input, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationOutputElements(WSDLOutput output, XMLStreamReader reader) {
/* 234 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationOutputAttributes(WSDLOutput output, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean portTypeOperationFaultElements(WSDLFault fault, XMLStreamReader reader) {
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationFaultAttributes(WSDLFault fault, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationInputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationInputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationOutputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 255 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindingOperationOutputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {}
/*     */   
/*     */   public boolean bindingOperationFaultElements(WSDLBoundFault fault, XMLStreamReader reader) {
/* 262 */     return false;
/*     */   }
/*     */   
/*     */   public void bindingOperationFaultAttributes(WSDLBoundFault fault, XMLStreamReader reader) {}
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {}
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\wsdl\parser\WSDLParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */