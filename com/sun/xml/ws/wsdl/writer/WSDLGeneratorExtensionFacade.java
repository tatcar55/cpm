/*     */ package com.sun.xml.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.ws.api.model.CheckedException;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGenExtnContext;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WSDLGeneratorExtensionFacade
/*     */   extends WSDLGeneratorExtension
/*     */ {
/*     */   private final WSDLGeneratorExtension[] extensions;
/*     */   
/*     */   WSDLGeneratorExtensionFacade(WSDLGeneratorExtension... extensions) {
/*  65 */     assert extensions != null;
/*  66 */     this.extensions = extensions;
/*     */   }
/*     */   
/*     */   public void start(WSDLGenExtnContext ctxt) {
/*  70 */     for (WSDLGeneratorExtension e : this.extensions)
/*  71 */       e.start(ctxt); 
/*     */   }
/*     */   
/*     */   public void end(@NotNull WSDLGenExtnContext ctxt) {
/*  75 */     for (WSDLGeneratorExtension e : this.extensions)
/*  76 */       e.end(ctxt); 
/*     */   }
/*     */   
/*     */   public void addDefinitionsExtension(TypedXmlWriter definitions) {
/*  80 */     for (WSDLGeneratorExtension e : this.extensions)
/*  81 */       e.addDefinitionsExtension(definitions); 
/*     */   }
/*     */   
/*     */   public void addServiceExtension(TypedXmlWriter service) {
/*  85 */     for (WSDLGeneratorExtension e : this.extensions)
/*  86 */       e.addServiceExtension(service); 
/*     */   }
/*     */   
/*     */   public void addPortExtension(TypedXmlWriter port) {
/*  90 */     for (WSDLGeneratorExtension e : this.extensions)
/*  91 */       e.addPortExtension(port); 
/*     */   }
/*     */   
/*     */   public void addPortTypeExtension(TypedXmlWriter portType) {
/*  95 */     for (WSDLGeneratorExtension e : this.extensions)
/*  96 */       e.addPortTypeExtension(portType); 
/*     */   }
/*     */   
/*     */   public void addBindingExtension(TypedXmlWriter binding) {
/* 100 */     for (WSDLGeneratorExtension e : this.extensions)
/* 101 */       e.addBindingExtension(binding); 
/*     */   }
/*     */   
/*     */   public void addOperationExtension(TypedXmlWriter operation, JavaMethod method) {
/* 105 */     for (WSDLGeneratorExtension e : this.extensions)
/* 106 */       e.addOperationExtension(operation, method); 
/*     */   }
/*     */   
/*     */   public void addBindingOperationExtension(TypedXmlWriter operation, JavaMethod method) {
/* 110 */     for (WSDLGeneratorExtension e : this.extensions)
/* 111 */       e.addBindingOperationExtension(operation, method); 
/*     */   }
/*     */   
/*     */   public void addInputMessageExtension(TypedXmlWriter message, JavaMethod method) {
/* 115 */     for (WSDLGeneratorExtension e : this.extensions)
/* 116 */       e.addInputMessageExtension(message, method); 
/*     */   }
/*     */   
/*     */   public void addOutputMessageExtension(TypedXmlWriter message, JavaMethod method) {
/* 120 */     for (WSDLGeneratorExtension e : this.extensions)
/* 121 */       e.addOutputMessageExtension(message, method); 
/*     */   }
/*     */   
/*     */   public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/* 125 */     for (WSDLGeneratorExtension e : this.extensions)
/* 126 */       e.addOperationInputExtension(input, method); 
/*     */   }
/*     */   
/*     */   public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/* 130 */     for (WSDLGeneratorExtension e : this.extensions)
/* 131 */       e.addOperationOutputExtension(output, method); 
/*     */   }
/*     */   
/*     */   public void addBindingOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/* 135 */     for (WSDLGeneratorExtension e : this.extensions)
/* 136 */       e.addBindingOperationInputExtension(input, method); 
/*     */   }
/*     */   
/*     */   public void addBindingOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/* 140 */     for (WSDLGeneratorExtension e : this.extensions)
/* 141 */       e.addBindingOperationOutputExtension(output, method); 
/*     */   }
/*     */   
/*     */   public void addBindingOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
/* 145 */     for (WSDLGeneratorExtension e : this.extensions)
/* 146 */       e.addBindingOperationFaultExtension(fault, method, ce); 
/*     */   }
/*     */   
/*     */   public void addFaultMessageExtension(TypedXmlWriter message, JavaMethod method, CheckedException ce) {
/* 150 */     for (WSDLGeneratorExtension e : this.extensions)
/* 151 */       e.addFaultMessageExtension(message, method, ce); 
/*     */   }
/*     */   
/*     */   public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
/* 155 */     for (WSDLGeneratorExtension e : this.extensions)
/* 156 */       e.addOperationFaultExtension(fault, method, ce); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\WSDLGeneratorExtensionFacade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */