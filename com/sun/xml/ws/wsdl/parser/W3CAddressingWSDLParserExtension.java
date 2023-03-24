/*     */ package com.sun.xml.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFeaturedObject;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLBoundOperationImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLBoundPortTypeImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLFaultImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLInputImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLOperationImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLOutputImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortTypeImpl;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class W3CAddressingWSDLParserExtension
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   protected static final String COLON_DELIMITER = ":";
/*     */   protected static final String SLASH_DELIMITER = "/";
/*     */   
/*     */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/*  66 */     return addressibleElement(reader, (WSDLFeaturedObject)binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/*  71 */     return addressibleElement(reader, (WSDLFeaturedObject)port);
/*     */   }
/*     */   
/*     */   private boolean addressibleElement(XMLStreamReader reader, WSDLFeaturedObject binding) {
/*  75 */     QName ua = reader.getName();
/*  76 */     if (ua.equals(AddressingVersion.W3C.wsdlExtensionTag)) {
/*  77 */       String required = reader.getAttributeValue("http://schemas.xmlsoap.org/wsdl/", "required");
/*  78 */       binding.addFeature(new AddressingFeature(true, Boolean.parseBoolean(required)));
/*  79 */       XMLStreamReaderUtil.skipElement(reader);
/*  80 */       return true;
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/*  88 */     WSDLBoundOperationImpl impl = (WSDLBoundOperationImpl)operation;
/*     */     
/*  90 */     QName anon = reader.getName();
/*  91 */     if (anon.equals(AddressingVersion.W3C.wsdlAnonymousTag)) {
/*     */       try {
/*  93 */         String value = reader.getElementText();
/*  94 */         if (value == null || value.trim().equals("")) {
/*  95 */           throw new WebServiceException("Null values not permitted in wsaw:Anonymous.");
/*     */         }
/*     */         
/*  98 */         if (value.equals("optional")) {
/*  99 */           impl.setAnonymous(WSDLBoundOperation.ANONYMOUS.optional);
/* 100 */         } else if (value.equals("required")) {
/* 101 */           impl.setAnonymous(WSDLBoundOperation.ANONYMOUS.required);
/* 102 */         } else if (value.equals("prohibited")) {
/* 103 */           impl.setAnonymous(WSDLBoundOperation.ANONYMOUS.prohibited);
/*     */         } else {
/* 105 */           throw new WebServiceException("wsaw:Anonymous value \"" + value + "\" not understood.");
/*     */         }
/*     */       
/*     */       }
/* 109 */       catch (XMLStreamException e) {
/* 110 */         throw new WebServiceException(e);
/*     */       } 
/*     */       
/* 113 */       return true;
/*     */     } 
/*     */     
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public void portTypeOperationInputAttributes(WSDLInput input, XMLStreamReader reader) {
/* 120 */     String action = ParserUtil.getAttribute(reader, getWsdlActionTag());
/* 121 */     if (action != null) {
/* 122 */       ((WSDLInputImpl)input).setAction(action);
/* 123 */       ((WSDLInputImpl)input).setDefaultAction(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationOutputAttributes(WSDLOutput output, XMLStreamReader reader) {
/* 129 */     String action = ParserUtil.getAttribute(reader, getWsdlActionTag());
/* 130 */     if (action != null) {
/* 131 */       ((WSDLOutputImpl)output).setAction(action);
/* 132 */       ((WSDLOutputImpl)output).setDefaultAction(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationFaultAttributes(WSDLFault fault, XMLStreamReader reader) {
/* 138 */     String action = ParserUtil.getAttribute(reader, getWsdlActionTag());
/* 139 */     if (action != null) {
/* 140 */       ((WSDLFaultImpl)fault).setAction(action);
/* 141 */       ((WSDLFaultImpl)fault).setDefaultAction(false);
/*     */     } 
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
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 157 */     WSDLModel model = context.getWSDLModel();
/* 158 */     for (WSDLService service : model.getServices().values()) {
/* 159 */       for (WSDLPort wp : service.getPorts()) {
/* 160 */         WSDLPortImpl port = (WSDLPortImpl)wp;
/* 161 */         WSDLBoundPortTypeImpl binding = port.getBinding();
/*     */ 
/*     */         
/* 164 */         populateActions(binding);
/*     */ 
/*     */         
/* 167 */         patchAnonymousDefault(binding);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getNamespaceURI() {
/* 173 */     return AddressingVersion.W3C.wsdlNsUri;
/*     */   }
/*     */   
/*     */   protected QName getWsdlActionTag() {
/* 177 */     return AddressingVersion.W3C.wsdlActionTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateActions(WSDLBoundPortTypeImpl binding) {
/* 185 */     WSDLPortTypeImpl porttype = binding.getPortType();
/* 186 */     for (WSDLOperationImpl o : porttype.getOperations()) {
/*     */ 
/*     */       
/* 189 */       WSDLBoundOperationImpl wboi = binding.get(o.getName());
/*     */       
/* 191 */       if (wboi == null) {
/*     */         
/* 193 */         o.getInput().setAction(defaultInputAction((WSDLOperation)o));
/*     */         continue;
/*     */       } 
/* 196 */       String soapAction = wboi.getSOAPAction();
/* 197 */       if (o.getInput().getAction() == null || o.getInput().getAction().equals(""))
/*     */       {
/*     */         
/* 200 */         if (soapAction != null && !soapAction.equals("")) {
/*     */           
/* 202 */           o.getInput().setAction(soapAction);
/*     */         } else {
/*     */           
/* 205 */           o.getInput().setAction(defaultInputAction((WSDLOperation)o));
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 210 */       if (o.getOutput() == null) {
/*     */         continue;
/*     */       }
/* 213 */       if (o.getOutput().getAction() == null || o.getOutput().getAction().equals("")) {
/* 214 */         o.getOutput().setAction(defaultOutputAction((WSDLOperation)o));
/*     */       }
/*     */       
/* 217 */       if (o.getFaults() == null || !o.getFaults().iterator().hasNext()) {
/*     */         continue;
/*     */       }
/* 220 */       for (WSDLFaultImpl wSDLFaultImpl : o.getFaults()) {
/* 221 */         if (wSDLFaultImpl.getAction() == null || wSDLFaultImpl.getAction().equals("")) {
/* 222 */           wSDLFaultImpl.setAction(defaultFaultAction(wSDLFaultImpl.getName(), (WSDLOperation)o));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void patchAnonymousDefault(WSDLBoundPortTypeImpl binding) {
/* 235 */     for (WSDLBoundOperationImpl wbo : binding.getBindingOperations()) {
/* 236 */       if (wbo.getAnonymous() == null)
/* 237 */         wbo.setAnonymous(WSDLBoundOperation.ANONYMOUS.optional); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String defaultInputAction(WSDLOperation o) {
/* 242 */     return buildAction(o.getInput().getName(), o, false);
/*     */   }
/*     */   
/*     */   private String defaultOutputAction(WSDLOperation o) {
/* 246 */     return buildAction(o.getOutput().getName(), o, false);
/*     */   }
/*     */   
/*     */   private String defaultFaultAction(String name, WSDLOperation o) {
/* 250 */     return buildAction(name, o, true);
/*     */   }
/*     */   
/*     */   protected static final String buildAction(String name, WSDLOperation o, boolean isFault) {
/* 254 */     String tns = o.getName().getNamespaceURI();
/*     */     
/* 256 */     String delim = "/";
/*     */ 
/*     */     
/* 259 */     if (!tns.startsWith("http")) {
/* 260 */       delim = ":";
/*     */     }
/* 262 */     if (tns.endsWith(delim)) {
/* 263 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/* 265 */     if (o.getPortTypeName() == null) {
/* 266 */       throw new WebServiceException("\"" + o.getName() + "\" operation's owning portType name is null.");
/*     */     }
/* 268 */     return tns + delim + o.getPortTypeName().getLocalPart() + delim + (isFault ? (o.getName().getLocalPart() + delim + "Fault" + delim) : "") + name;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\W3CAddressingWSDLParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */