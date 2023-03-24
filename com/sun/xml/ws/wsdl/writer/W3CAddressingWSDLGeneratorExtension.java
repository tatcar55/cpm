/*     */ package com.sun.xml.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.CheckedException;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGenExtnContext;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.Action;
/*     */ import javax.xml.ws.FaultAction;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CAddressingWSDLGeneratorExtension
/*     */   extends WSDLGeneratorExtension
/*     */ {
/*     */   private boolean enabled;
/*     */   private boolean required = false;
/*     */   
/*     */   public void start(WSDLGenExtnContext ctxt) {
/*  68 */     WSBinding binding = ctxt.getBinding();
/*  69 */     TypedXmlWriter root = ctxt.getRoot();
/*  70 */     this.enabled = binding.isFeatureEnabled(AddressingFeature.class);
/*  71 */     if (!this.enabled)
/*     */       return; 
/*  73 */     AddressingFeature ftr = (AddressingFeature)binding.getFeature(AddressingFeature.class);
/*  74 */     this.required = ftr.isRequired();
/*  75 */     root._namespace(AddressingVersion.W3C.wsdlNsUri, AddressingVersion.W3C.getWsdlPrefix());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/*  80 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/*  83 */     Action a = method.getSEIMethod().<Action>getAnnotation(Action.class);
/*  84 */     if (a != null && !a.input().equals("")) {
/*  85 */       addAttribute(input, a.input());
/*     */     } else {
/*     */       
/*  88 */       String soapAction = method.getBinding().getSOAPAction();
/*     */       
/*  90 */       if (soapAction == null || soapAction.equals("")) {
/*     */         
/*  92 */         String defaultAction = getDefaultAction(method);
/*  93 */         addAttribute(input, defaultAction);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static final String getDefaultAction(JavaMethod method) {
/*  99 */     String tns = method.getOwner().getTargetNamespace();
/* 100 */     String delim = "/";
/*     */     
/*     */     try {
/* 103 */       URI uri = new URI(tns);
/* 104 */       if (uri.getScheme().equalsIgnoreCase("urn"))
/* 105 */         delim = ":"; 
/* 106 */     } catch (URISyntaxException e) {
/* 107 */       LOGGER.warning("TargetNamespace of WebService is not a valid URI");
/*     */     } 
/* 109 */     if (tns.endsWith(delim)) {
/* 110 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/*     */ 
/*     */     
/* 114 */     String name = method.getMEP().isOneWay() ? method.getOperationName() : (method.getOperationName() + "Request");
/*     */     
/* 116 */     return tns + delim + method.getOwner().getPortTypeName().getLocalPart() + delim + name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/* 123 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 126 */     Action a = method.getSEIMethod().<Action>getAnnotation(Action.class);
/* 127 */     if (a != null && !a.output().equals("")) {
/* 128 */       addAttribute(output, a.output());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
/* 134 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 137 */     Action a = method.getSEIMethod().<Action>getAnnotation(Action.class);
/* 138 */     Class[] exs = method.getSEIMethod().getExceptionTypes();
/*     */     
/* 140 */     if (exs == null) {
/*     */       return;
/*     */     }
/* 143 */     if (a != null && a.fault() != null) {
/* 144 */       for (FaultAction fa : a.fault()) {
/* 145 */         if (fa.className().getName().equals(ce.getExceptionClass().getName())) {
/* 146 */           if (fa.value().equals("")) {
/*     */             return;
/*     */           }
/* 149 */           addAttribute(fault, fa.value());
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void addAttribute(TypedXmlWriter writer, String attrValue) {
/* 157 */     writer._attribute(AddressingVersion.W3C.wsdlActionTag, attrValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingExtension(TypedXmlWriter binding) {
/* 162 */     if (!this.enabled)
/*     */       return; 
/* 164 */     binding._element(AddressingVersion.W3C.wsdlExtensionTag, UsingAddressing.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   private static final Logger LOGGER = Logger.getLogger(W3CAddressingWSDLGeneratorExtension.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\W3CAddressingWSDLGeneratorExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */