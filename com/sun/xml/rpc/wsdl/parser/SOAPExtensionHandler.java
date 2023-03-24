/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPAddress;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBinding;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPFault;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPHeader;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPHeaderFault;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPOperation;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*     */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
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
/*     */ public class SOAPExtensionHandler
/*     */   extends ExtensionHandlerBase
/*     */ {
/*     */   public String getNamespaceURI() {
/*  61 */     return "http://schemas.xmlsoap.org/wsdl/soap/";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleDefinitionsExtension(ParserContext context, Extensible parent, Element e) {
/*  68 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleTypesExtension(ParserContext context, Extensible parent, Element e) {
/*  79 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleBindingExtension(ParserContext context, Extensible parent, Element e) {
/*  90 */     if (XmlUtil.matchesTagNS(e, SOAPConstants.QNAME_BINDING)) {
/*  91 */       context.push();
/*  92 */       context.registerNamespaces(e);
/*     */       
/*  94 */       SOAPBinding binding = new SOAPBinding();
/*     */ 
/*     */ 
/*     */       
/*  98 */       String transport = Util.getRequiredAttribute(e, "transport");
/*     */       
/* 100 */       binding.setTransport(transport);
/*     */       
/* 102 */       String style = XmlUtil.getAttributeOrNull(e, "style");
/* 103 */       if (style != null) {
/* 104 */         if (style.equals("rpc")) {
/* 105 */           binding.setStyle(SOAPStyle.RPC);
/* 106 */         } else if (style.equals("document")) {
/* 107 */           binding.setStyle(SOAPStyle.DOCUMENT);
/*     */         } else {
/* 109 */           Util.fail("parsing.invalidAttributeValue", "style", style);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 115 */       parent.addExtension((Extension)binding);
/* 116 */       context.pop();
/* 117 */       context.fireDoneParsingEntity(SOAPConstants.QNAME_BINDING, (Entity)binding);
/* 118 */       return true;
/*     */     } 
/* 120 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleOperationExtension(ParserContext context, Extensible parent, Element e) {
/* 132 */     if (XmlUtil.matchesTagNS(e, SOAPConstants.QNAME_OPERATION)) {
/* 133 */       context.push();
/* 134 */       context.registerNamespaces(e);
/*     */       
/* 136 */       SOAPOperation operation = new SOAPOperation();
/*     */       
/* 138 */       String soapAction = XmlUtil.getAttributeOrNull(e, "soapAction");
/*     */       
/* 140 */       if (soapAction != null) {
/* 141 */         operation.setSOAPAction(soapAction);
/*     */       }
/*     */       
/* 144 */       String style = XmlUtil.getAttributeOrNull(e, "style");
/* 145 */       if (style != null) {
/* 146 */         if (style.equals("rpc")) {
/* 147 */           operation.setStyle(SOAPStyle.RPC);
/* 148 */         } else if (style.equals("document")) {
/* 149 */           operation.setStyle(SOAPStyle.DOCUMENT);
/*     */         } else {
/* 151 */           Util.fail("parsing.invalidAttributeValue", "style", style);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 157 */       parent.addExtension((Extension)operation);
/* 158 */       context.pop();
/* 159 */       context.fireDoneParsingEntity(SOAPConstants.QNAME_OPERATION, (Entity)operation);
/*     */ 
/*     */       
/* 162 */       return true;
/*     */     } 
/* 164 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleInputExtension(ParserContext context, Extensible parent, Element e) {
/* 176 */     return handleInputOutputExtension(context, parent, e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleOutputExtension(ParserContext context, Extensible parent, Element e) {
/* 182 */     return handleInputOutputExtension(context, parent, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleMIMEPartExtension(ParserContext context, Extensible parent, Element e) {
/* 189 */     return handleInputOutputExtension(context, parent, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleInputOutputExtension(ParserContext context, Extensible parent, Element e) {
/* 196 */     if (XmlUtil.matchesTagNS(e, SOAPConstants.QNAME_BODY)) {
/* 197 */       context.push();
/* 198 */       context.registerNamespaces(e);
/*     */       
/* 200 */       SOAPBody body = new SOAPBody();
/*     */       
/* 202 */       String use = XmlUtil.getAttributeOrNull(e, "use");
/* 203 */       if (use != null) {
/* 204 */         if (use.equals("literal")) {
/* 205 */           body.setUse(SOAPUse.LITERAL);
/* 206 */         } else if (use.equals("encoded")) {
/* 207 */           body.setUse(SOAPUse.ENCODED);
/*     */         } else {
/* 209 */           Util.fail("parsing.invalidAttributeValue", "use", use);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 216 */       String namespace = XmlUtil.getAttributeOrNull(e, "namespace");
/*     */       
/* 218 */       if (namespace != null) {
/* 219 */         body.setNamespace(namespace);
/*     */       }
/*     */       
/* 222 */       String encodingStyle = XmlUtil.getAttributeOrNull(e, "encodingStyle");
/*     */       
/* 224 */       if (encodingStyle != null) {
/* 225 */         body.setEncodingStyle(encodingStyle);
/*     */       }
/*     */       
/* 228 */       String parts = XmlUtil.getAttributeOrNull(e, "parts");
/* 229 */       if (parts != null) {
/* 230 */         body.setParts(parts);
/*     */       }
/*     */       
/* 233 */       parent.addExtension((Extension)body);
/* 234 */       context.pop();
/* 235 */       context.fireDoneParsingEntity(SOAPConstants.QNAME_BODY, (Entity)body);
/* 236 */       return true;
/* 237 */     }  if (XmlUtil.matchesTagNS(e, SOAPConstants.QNAME_HEADER)) {
/* 238 */       context.push();
/* 239 */       context.registerNamespaces(e);
/*     */       
/* 241 */       SOAPHeader header = new SOAPHeader();
/*     */       
/* 243 */       String use = XmlUtil.getAttributeOrNull(e, "use");
/* 244 */       if (use != null) {
/* 245 */         if (use.equals("literal")) {
/* 246 */           header.setUse(SOAPUse.LITERAL);
/* 247 */         } else if (use.equals("encoded")) {
/* 248 */           header.setUse(SOAPUse.ENCODED);
/*     */         } else {
/* 250 */           Util.fail("parsing.invalidAttributeValue", "use", use);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       String namespace = XmlUtil.getAttributeOrNull(e, "namespace");
/*     */       
/* 259 */       if (namespace != null) {
/* 260 */         header.setNamespace(namespace);
/*     */       }
/*     */       
/* 263 */       String encodingStyle = XmlUtil.getAttributeOrNull(e, "encodingStyle");
/*     */       
/* 265 */       if (encodingStyle != null) {
/* 266 */         header.setEncodingStyle(encodingStyle);
/*     */       }
/*     */       
/* 269 */       String part = XmlUtil.getAttributeOrNull(e, "part");
/* 270 */       if (part != null) {
/* 271 */         header.setPart(part);
/*     */       }
/*     */       
/* 274 */       String messageAttr = XmlUtil.getAttributeOrNull(e, "message");
/*     */       
/* 276 */       if (messageAttr != null) {
/* 277 */         header.setMessage(context.translateQualifiedName(messageAttr));
/*     */       }
/*     */       
/* 280 */       for (Iterator iter = XmlUtil.getAllChildren(e); iter.hasNext(); ) {
/* 281 */         Element e2 = Util.nextElement(iter);
/* 282 */         if (e2 == null) {
/*     */           break;
/*     */         }
/* 285 */         if (XmlUtil.matchesTagNS(e2, SOAPConstants.QNAME_HEADERFAULT)) {
/*     */           
/* 287 */           context.push();
/* 288 */           context.registerNamespaces(e);
/*     */           
/* 290 */           SOAPHeaderFault headerfault = new SOAPHeaderFault();
/*     */           
/* 292 */           String use2 = XmlUtil.getAttributeOrNull(e2, "use");
/*     */           
/* 294 */           if (use2 != null) {
/* 295 */             if (use2.equals("literal")) {
/* 296 */               headerfault.setUse(SOAPUse.LITERAL);
/* 297 */             } else if (use.equals("encoded")) {
/* 298 */               headerfault.setUse(SOAPUse.ENCODED);
/*     */             } else {
/* 300 */               Util.fail("parsing.invalidAttributeValue", "use", use2);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 307 */           String namespace2 = XmlUtil.getAttributeOrNull(e2, "namespace");
/*     */ 
/*     */ 
/*     */           
/* 311 */           if (namespace2 != null) {
/* 312 */             headerfault.setNamespace(namespace2);
/*     */           }
/*     */           
/* 315 */           String encodingStyle2 = XmlUtil.getAttributeOrNull(e2, "encodingStyle");
/*     */ 
/*     */ 
/*     */           
/* 319 */           if (encodingStyle2 != null) {
/* 320 */             headerfault.setEncodingStyle(encodingStyle2);
/*     */           }
/*     */           
/* 323 */           String part2 = XmlUtil.getAttributeOrNull(e2, "part");
/*     */           
/* 325 */           if (part2 != null) {
/* 326 */             headerfault.setPart(part2);
/*     */           }
/*     */           
/* 329 */           String messageAttr2 = XmlUtil.getAttributeOrNull(e2, "message");
/*     */           
/* 331 */           if (messageAttr2 != null) {
/* 332 */             headerfault.setMessage(context.translateQualifiedName(messageAttr2));
/*     */           }
/*     */ 
/*     */           
/* 336 */           header.add(headerfault);
/* 337 */           context.pop(); continue;
/*     */         } 
/* 339 */         Util.fail("parsing.invalidElement", e2.getTagName(), e2.getNamespaceURI());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 346 */       parent.addExtension((Extension)header);
/* 347 */       context.pop();
/* 348 */       context.fireDoneParsingEntity(SOAPConstants.QNAME_HEADER, (Entity)header);
/* 349 */       return true;
/*     */     } 
/* 351 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 355 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleFaultExtension(ParserContext context, Extensible parent, Element e) {
/* 363 */     if (XmlUtil.matchesTagNS(e, SOAPConstants.QNAME_FAULT)) {
/* 364 */       context.push();
/* 365 */       context.registerNamespaces(e);
/*     */       
/* 367 */       SOAPFault fault = new SOAPFault();
/*     */       
/* 369 */       String name = XmlUtil.getAttributeOrNull(e, "name");
/* 370 */       if (name != null) {
/* 371 */         fault.setName(name);
/*     */       }
/*     */       
/* 374 */       String use = XmlUtil.getAttributeOrNull(e, "use");
/* 375 */       if (use != null) {
/* 376 */         if (use.equals("literal")) {
/* 377 */           fault.setUse(SOAPUse.LITERAL);
/* 378 */         } else if (use.equals("encoded")) {
/* 379 */           fault.setUse(SOAPUse.ENCODED);
/*     */         } else {
/* 381 */           Util.fail("parsing.invalidAttributeValue", "use", use);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 388 */       String namespace = XmlUtil.getAttributeOrNull(e, "namespace");
/*     */       
/* 390 */       if (namespace != null) {
/* 391 */         fault.setNamespace(namespace);
/*     */       }
/*     */       
/* 394 */       String encodingStyle = XmlUtil.getAttributeOrNull(e, "encodingStyle");
/*     */       
/* 396 */       if (encodingStyle != null) {
/* 397 */         fault.setEncodingStyle(encodingStyle);
/*     */       }
/*     */       
/* 400 */       parent.addExtension((Extension)fault);
/* 401 */       context.pop();
/* 402 */       context.fireDoneParsingEntity(SOAPConstants.QNAME_FAULT, (Entity)fault);
/* 403 */       return true;
/*     */     } 
/* 405 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 409 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleServiceExtension(ParserContext context, Extensible parent, Element e) {
/* 417 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 421 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handlePortExtension(ParserContext context, Extensible parent, Element e) {
/* 428 */     if (XmlUtil.matchesTagNS(e, SOAPConstants.QNAME_ADDRESS)) {
/* 429 */       context.push();
/* 430 */       context.registerNamespaces(e);
/*     */       
/* 432 */       SOAPAddress address = new SOAPAddress();
/*     */       
/* 434 */       String location = Util.getRequiredAttribute(e, "location");
/*     */       
/* 436 */       address.setLocation(location);
/*     */       
/* 438 */       parent.addExtension((Extension)address);
/* 439 */       context.pop();
/* 440 */       context.fireDoneParsingEntity(SOAPConstants.QNAME_ADDRESS, (Entity)address);
/* 441 */       return true;
/*     */     } 
/* 443 */     Util.fail("parsing.invalidExtensionElement", e.getTagName(), e.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 447 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doHandleExtension(WriterContext context, Extension extension) throws IOException {
/* 455 */     if (extension instanceof SOAPAddress) {
/* 456 */       SOAPAddress address = (SOAPAddress)extension;
/* 457 */       context.writeStartTag(address.getElementName());
/* 458 */       context.writeAttribute("location", address.getLocation());
/*     */ 
/*     */       
/* 461 */       context.writeEndTag(address.getElementName());
/* 462 */     } else if (extension instanceof SOAPBinding) {
/* 463 */       SOAPBinding binding = (SOAPBinding)extension;
/* 464 */       context.writeStartTag(binding.getElementName());
/* 465 */       context.writeAttribute("transport", binding.getTransport());
/*     */ 
/*     */       
/* 468 */       String style = (binding.getStyle() == null) ? null : ((binding.getStyle() == SOAPStyle.DOCUMENT) ? "document" : "rpc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 474 */       context.writeAttribute("style", style);
/* 475 */       context.writeEndTag(binding.getElementName());
/* 476 */     } else if (extension instanceof SOAPBody) {
/* 477 */       SOAPBody body = (SOAPBody)extension;
/* 478 */       context.writeStartTag(body.getElementName());
/* 479 */       context.writeAttribute("encodingStyle", body.getEncodingStyle());
/*     */ 
/*     */       
/* 482 */       context.writeAttribute("parts", body.getParts());
/* 483 */       String use = (body.getUse() == null) ? null : ((body.getUse() == SOAPUse.LITERAL) ? "literal" : "encoded");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 489 */       context.writeAttribute("use", use);
/* 490 */       context.writeAttribute("namespace", body.getNamespace());
/*     */ 
/*     */       
/* 493 */       context.writeEndTag(body.getElementName());
/* 494 */     } else if (extension instanceof SOAPFault) {
/* 495 */       SOAPFault fault = (SOAPFault)extension;
/* 496 */       context.writeStartTag(fault.getElementName());
/* 497 */       context.writeAttribute("name", fault.getName());
/* 498 */       context.writeAttribute("encodingStyle", fault.getEncodingStyle());
/*     */ 
/*     */       
/* 501 */       String use = (fault.getUse() == null) ? null : ((fault.getUse() == SOAPUse.LITERAL) ? "literal" : "encoded");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 507 */       context.writeAttribute("use", use);
/* 508 */       context.writeAttribute("namespace", fault.getNamespace());
/*     */ 
/*     */       
/* 511 */       context.writeEndTag(fault.getElementName());
/* 512 */     } else if (extension instanceof SOAPHeader) {
/* 513 */       SOAPHeader header = (SOAPHeader)extension;
/* 514 */       context.writeStartTag(header.getElementName());
/* 515 */       context.writeAttribute("message", header.getMessage());
/* 516 */       context.writeAttribute("part", header.getPart());
/* 517 */       context.writeAttribute("encodingStyle", header.getEncodingStyle());
/*     */ 
/*     */       
/* 520 */       String use = (header.getUse() == null) ? null : ((header.getUse() == SOAPUse.LITERAL) ? "literal" : "encoded");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 526 */       context.writeAttribute("use", use);
/* 527 */       context.writeAttribute("namespace", header.getNamespace());
/*     */ 
/*     */       
/* 530 */       context.writeEndTag(header.getElementName());
/* 531 */     } else if (extension instanceof SOAPHeaderFault) {
/* 532 */       SOAPHeaderFault headerfault = (SOAPHeaderFault)extension;
/* 533 */       context.writeStartTag(headerfault.getElementName());
/* 534 */       context.writeAttribute("message", headerfault.getMessage());
/*     */ 
/*     */       
/* 537 */       context.writeAttribute("part", headerfault.getPart());
/* 538 */       context.writeAttribute("encodingStyle", headerfault.getEncodingStyle());
/*     */ 
/*     */       
/* 541 */       String use = (headerfault.getUse() == null) ? null : ((headerfault.getUse() == SOAPUse.LITERAL) ? "literal" : "encoded");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 547 */       context.writeAttribute("use", use);
/* 548 */       context.writeAttribute("namespace", headerfault.getNamespace());
/*     */ 
/*     */       
/* 551 */       context.writeEndTag(headerfault.getElementName());
/* 552 */     } else if (extension instanceof SOAPOperation) {
/* 553 */       SOAPOperation operation = (SOAPOperation)extension;
/* 554 */       context.writeStartTag(operation.getElementName());
/* 555 */       context.writeAttribute("soapAction", operation.getSOAPAction());
/*     */ 
/*     */       
/* 558 */       String style = (operation.getStyle() == null) ? null : (operation.isDocument() ? "document" : "rpc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 564 */       context.writeAttribute("style", style);
/* 565 */       context.writeEndTag(operation.getElementName());
/*     */     } else {
/* 567 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\SOAPExtensionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */