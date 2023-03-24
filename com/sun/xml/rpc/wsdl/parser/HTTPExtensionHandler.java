/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.http.HTTPAddress;
/*     */ import com.sun.xml.rpc.wsdl.document.http.HTTPBinding;
/*     */ import com.sun.xml.rpc.wsdl.document.http.HTTPConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.http.HTTPOperation;
/*     */ import com.sun.xml.rpc.wsdl.document.http.HTTPUrlEncoded;
/*     */ import com.sun.xml.rpc.wsdl.document.http.HTTPUrlReplacement;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*     */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*     */ import java.io.IOException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HTTPExtensionHandler
/*     */   extends ExtensionHandlerBase
/*     */ {
/*     */   public String getNamespaceURI() {
/*  56 */     return "http://schemas.xmlsoap.org/wsdl/http/";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleDefinitionsExtension(ParserContext context, Extensible parent, Element e) {
/*  63 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleTypesExtension(ParserContext context, Extensible parent, Element e) {
/*  74 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleBindingExtension(ParserContext context, Extensible parent, Element e) {
/*  85 */     if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_BINDING)) {
/*  86 */       context.push();
/*  87 */       context.registerNamespaces(e);
/*     */       
/*  89 */       HTTPBinding binding = new HTTPBinding();
/*     */       
/*  91 */       String verb = Util.getRequiredAttribute(e, "verb");
/*  92 */       binding.setVerb(verb);
/*     */       
/*  94 */       parent.addExtension((Extension)binding);
/*  95 */       context.pop();
/*  96 */       context.fireDoneParsingEntity(HTTPConstants.QNAME_BINDING, (Entity)binding);
/*  97 */       return true;
/*     */     } 
/*  99 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleOperationExtension(ParserContext context, Extensible parent, Element e) {
/* 111 */     if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_OPERATION)) {
/* 112 */       context.push();
/* 113 */       context.registerNamespaces(e);
/*     */       
/* 115 */       HTTPOperation operation = new HTTPOperation();
/*     */       
/* 117 */       String location = Util.getRequiredAttribute(e, "location");
/*     */       
/* 119 */       operation.setLocation(location);
/*     */       
/* 121 */       parent.addExtension((Extension)operation);
/* 122 */       context.pop();
/* 123 */       context.fireDoneParsingEntity(HTTPConstants.QNAME_OPERATION, (Entity)operation);
/*     */ 
/*     */       
/* 126 */       return true;
/*     */     } 
/* 128 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleInputExtension(ParserContext context, Extensible parent, Element e) {
/* 140 */     if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_URL_ENCODED)) {
/* 141 */       parent.addExtension((Extension)new HTTPUrlEncoded());
/* 142 */       return true;
/* 143 */     }  if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_URL_REPLACEMENT)) {
/*     */       
/* 145 */       parent.addExtension((Extension)new HTTPUrlReplacement());
/* 146 */       return true;
/*     */     } 
/* 148 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleOutputExtension(ParserContext context, Extensible parent, Element e) {
/* 160 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleFaultExtension(ParserContext context, Extensible parent, Element e) {
/* 171 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleServiceExtension(ParserContext context, Extensible parent, Element e) {
/* 182 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handlePortExtension(ParserContext context, Extensible parent, Element e) {
/* 193 */     if (XmlUtil.matchesTagNS(e, HTTPConstants.QNAME_ADDRESS)) {
/* 194 */       context.push();
/* 195 */       context.registerNamespaces(e);
/*     */       
/* 197 */       HTTPAddress address = new HTTPAddress();
/*     */       
/* 199 */       String location = Util.getRequiredAttribute(e, "location");
/*     */       
/* 201 */       address.setLocation(location);
/*     */       
/* 203 */       parent.addExtension((Extension)address);
/* 204 */       context.pop();
/* 205 */       context.fireDoneParsingEntity(HTTPConstants.QNAME_ADDRESS, (Entity)address);
/* 206 */       return true;
/*     */     } 
/* 208 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 212 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMIMEPartExtension(ParserContext context, Extensible parent, Element e) {
/* 220 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doHandleExtension(WriterContext context, Extension extension) throws IOException {
/* 229 */     if (extension instanceof HTTPAddress) {
/* 230 */       HTTPAddress address = (HTTPAddress)extension;
/* 231 */       context.writeStartTag(address.getElementName());
/* 232 */       context.writeAttribute("location", address.getLocation());
/*     */ 
/*     */       
/* 235 */       context.writeEndTag(address.getElementName());
/* 236 */     } else if (extension instanceof HTTPBinding) {
/* 237 */       HTTPBinding binding = (HTTPBinding)extension;
/* 238 */       context.writeStartTag(binding.getElementName());
/* 239 */       context.writeAttribute("verb", binding.getVerb());
/* 240 */       context.writeEndTag(binding.getElementName());
/* 241 */     } else if (extension instanceof HTTPOperation) {
/* 242 */       HTTPOperation operation = (HTTPOperation)extension;
/* 243 */       context.writeStartTag(operation.getElementName());
/* 244 */       context.writeAttribute("location", operation.getLocation());
/*     */ 
/*     */       
/* 247 */       context.writeEndTag(operation.getElementName());
/* 248 */     } else if (extension instanceof HTTPUrlEncoded) {
/* 249 */       context.writeStartTag(extension.getElementName());
/* 250 */       context.writeEndTag(extension.getElementName());
/* 251 */     } else if (extension instanceof HTTPUrlReplacement) {
/* 252 */       context.writeStartTag(extension.getElementName());
/* 253 */       context.writeEndTag(extension.getElementName());
/*     */     } else {
/* 255 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\HTTPExtensionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */