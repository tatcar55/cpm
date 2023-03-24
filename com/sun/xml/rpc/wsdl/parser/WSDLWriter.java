/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.document.Binding;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingFault;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingInput;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingOperation;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingOutput;
/*     */ import com.sun.xml.rpc.wsdl.document.Definitions;
/*     */ import com.sun.xml.rpc.wsdl.document.Documentation;
/*     */ import com.sun.xml.rpc.wsdl.document.Fault;
/*     */ import com.sun.xml.rpc.wsdl.document.Import;
/*     */ import com.sun.xml.rpc.wsdl.document.Input;
/*     */ import com.sun.xml.rpc.wsdl.document.Message;
/*     */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*     */ import com.sun.xml.rpc.wsdl.document.Operation;
/*     */ import com.sun.xml.rpc.wsdl.document.Output;
/*     */ import com.sun.xml.rpc.wsdl.document.Port;
/*     */ import com.sun.xml.rpc.wsdl.document.PortType;
/*     */ import com.sun.xml.rpc.wsdl.document.Service;
/*     */ import com.sun.xml.rpc.wsdl.document.Types;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocumentVisitor;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLWriter
/*     */ {
/*     */   private Map _extensionHandlers;
/*     */   
/*     */   public WSDLWriter() throws IOException {
/*  72 */     this._extensionHandlers = new HashMap<Object, Object>();
/*     */ 
/*     */     
/*  75 */     register(new SOAPExtensionHandler());
/*  76 */     register(new HTTPExtensionHandler());
/*  77 */     register(new MIMEExtensionHandler());
/*  78 */     register(new SchemaExtensionHandler());
/*     */   }
/*     */   
/*     */   public void register(ExtensionHandler h) {
/*  82 */     this._extensionHandlers.put(h.getNamespaceURI(), h);
/*  83 */     h.setExtensionHandlers(this._extensionHandlers);
/*     */   }
/*     */   
/*     */   public void unregister(ExtensionHandler h) {
/*  87 */     this._extensionHandlers.put(h.getNamespaceURI(), null);
/*  88 */     h.setExtensionHandlers(null);
/*     */   }
/*     */   
/*     */   public void unregister(String uri) {
/*  92 */     this._extensionHandlers.put(uri, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(final WSDLDocument document, OutputStream os) throws IOException {
/*  97 */     final WriterContext context = new WriterContext(os);
/*     */     try {
/*  99 */       document.accept(new WSDLDocumentVisitor()
/*     */           {
/*     */             public void preVisit(Definitions definitions) throws Exception {
/* 102 */               context.push();
/* 103 */               WSDLWriter.this.initializePrefixes(context, document);
/* 104 */               context.writeStartTag(definitions.getElementName());
/* 105 */               context.writeAttribute("name", definitions.getName());
/*     */ 
/*     */               
/* 108 */               context.writeAttribute("targetNamespace", definitions.getTargetNamespaceURI());
/*     */ 
/*     */               
/* 111 */               context.writeAllPendingNamespaceDeclarations();
/*     */             }
/*     */             
/*     */             public void postVisit(Definitions definitions) throws Exception {
/* 115 */               context.writeEndTag(definitions.getElementName());
/* 116 */               context.pop();
/*     */             }
/*     */             public void visit(Import i) throws Exception {
/* 119 */               context.writeStartTag(i.getElementName());
/* 120 */               context.writeAttribute("namespace", i.getNamespace());
/*     */ 
/*     */               
/* 123 */               context.writeAttribute("location", i.getLocation());
/*     */ 
/*     */               
/* 126 */               context.writeEndTag(i.getElementName());
/*     */             }
/*     */             public void preVisit(Types types) throws Exception {
/* 129 */               context.writeStartTag(types.getElementName());
/*     */             }
/*     */             public void postVisit(Types types) throws Exception {
/* 132 */               context.writeEndTag(types.getElementName());
/*     */             }
/*     */             public void preVisit(Message message) throws Exception {
/* 135 */               context.writeStartTag(message.getElementName());
/* 136 */               context.writeAttribute("name", message.getName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Message message) throws Exception {
/* 141 */               context.writeEndTag(message.getElementName());
/*     */             }
/*     */             public void visit(MessagePart part) throws Exception {
/* 144 */               context.writeStartTag(part.getElementName());
/* 145 */               context.writeAttribute("name", part.getName());
/*     */               
/* 147 */               QName dname = part.getDescriptor();
/* 148 */               Kind dkind = part.getDescriptorKind();
/* 149 */               if (dname != null && dkind != null) {
/* 150 */                 if (dkind.equals(SchemaKinds.XSD_ELEMENT)) {
/* 151 */                   context.writeAttribute("element", dname);
/*     */                 
/*     */                 }
/* 154 */                 else if (dkind.equals(SchemaKinds.XSD_TYPE)) {
/* 155 */                   context.writeAttribute("type", dname);
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/* 160 */               context.writeEndTag(part.getElementName());
/*     */             }
/*     */             public void preVisit(PortType portType) throws Exception {
/* 163 */               context.writeStartTag(portType.getElementName());
/* 164 */               context.writeAttribute("name", portType.getName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(PortType portType) throws Exception {
/* 169 */               context.writeEndTag(portType.getElementName());
/*     */             }
/*     */             public void preVisit(Operation operation) throws Exception {
/* 172 */               context.writeStartTag(operation.getElementName());
/* 173 */               context.writeAttribute("name", operation.getName());
/*     */ 
/*     */ 
/*     */               
/* 177 */               if (operation.getParameterOrder() != null && operation.getParameterOrder().length() > 0)
/*     */               {
/* 179 */                 context.writeAttribute("parameterOrder", operation.getParameterOrder());
/*     */               }
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Operation operation) throws Exception {
/* 185 */               context.writeEndTag(operation.getElementName());
/*     */             }
/*     */             public void preVisit(Input input) throws Exception {
/* 188 */               context.writeStartTag(input.getElementName());
/* 189 */               context.writeAttribute("name", input.getName());
/*     */ 
/*     */               
/* 192 */               context.writeAttribute("message", input.getMessage());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Input input) throws Exception {
/* 197 */               context.writeEndTag(input.getElementName());
/*     */             }
/*     */             public void preVisit(Output output) throws Exception {
/* 200 */               context.writeStartTag(output.getElementName());
/* 201 */               context.writeAttribute("name", output.getName());
/*     */ 
/*     */               
/* 204 */               context.writeAttribute("message", output.getMessage());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Output output) throws Exception {
/* 209 */               context.writeEndTag(output.getElementName());
/*     */             }
/*     */             public void preVisit(Fault fault) throws Exception {
/* 212 */               context.writeStartTag(fault.getElementName());
/* 213 */               context.writeAttribute("name", fault.getName());
/*     */ 
/*     */               
/* 216 */               context.writeAttribute("message", fault.getMessage());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Fault fault) throws Exception {
/* 221 */               context.writeEndTag(fault.getElementName());
/*     */             }
/*     */             public void preVisit(Binding binding) throws Exception {
/* 224 */               context.writeStartTag(binding.getElementName());
/* 225 */               context.writeAttribute("name", binding.getName());
/*     */ 
/*     */               
/* 228 */               context.writeAttribute("type", binding.getPortType());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Binding binding) throws Exception {
/* 233 */               context.writeEndTag(binding.getElementName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void preVisit(BindingOperation operation) throws Exception {
/* 238 */               context.writeStartTag(operation.getElementName());
/* 239 */               context.writeAttribute("name", operation.getName());
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void postVisit(BindingOperation operation) throws Exception {
/* 245 */               context.writeEndTag(operation.getElementName());
/*     */             }
/*     */             public void preVisit(BindingInput input) throws Exception {
/* 248 */               context.writeStartTag(input.getElementName());
/* 249 */               context.writeAttribute("name", input.getName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(BindingInput input) throws Exception {
/* 254 */               context.writeEndTag(input.getElementName());
/*     */             }
/*     */             public void preVisit(BindingOutput output) throws Exception {
/* 257 */               context.writeStartTag(output.getElementName());
/* 258 */               context.writeAttribute("name", output.getName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(BindingOutput output) throws Exception {
/* 263 */               context.writeEndTag(output.getElementName());
/*     */             }
/*     */             public void preVisit(BindingFault fault) throws Exception {
/* 266 */               context.writeStartTag(fault.getElementName());
/* 267 */               context.writeAttribute("name", fault.getName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(BindingFault fault) throws Exception {
/* 272 */               context.writeEndTag(fault.getElementName());
/*     */             }
/*     */             
/*     */             public void preVisit(Service service) throws Exception {
/* 276 */               context.writeStartTag(service.getElementName());
/* 277 */               context.writeAttribute("name", service.getName());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Service service) throws Exception {
/* 282 */               context.writeEndTag(service.getElementName());
/*     */             }
/*     */             public void preVisit(Port port) throws Exception {
/* 285 */               context.writeStartTag(port.getElementName());
/* 286 */               context.writeAttribute("name", port.getName());
/* 287 */               context.writeAttribute("binding", port.getBinding());
/*     */             }
/*     */ 
/*     */             
/*     */             public void postVisit(Port port) throws Exception {
/* 292 */               context.writeEndTag(port.getElementName());
/*     */             }
/*     */             public void preVisit(Extension extension) throws Exception {
/* 295 */               ExtensionHandler h = (ExtensionHandler)WSDLWriter.this._extensionHandlers.get(extension.getElementName().getNamespaceURI());
/*     */ 
/*     */               
/* 298 */               h.doHandleExtension(context, extension);
/*     */             }
/*     */             
/*     */             public void postVisit(Extension extension) throws Exception {}
/*     */             
/*     */             public void visit(Documentation documentation) throws Exception {
/* 304 */               context.writeTag(WSDLConstants.QNAME_DOCUMENTATION, null);
/*     */             }
/*     */           });
/* 307 */       context.flush();
/* 308 */     } catch (Exception e) {
/* 309 */       if (e instanceof IOException)
/* 310 */         throw (IOException)e; 
/* 311 */       if (e instanceof RuntimeException) {
/* 312 */         throw (RuntimeException)e;
/*     */       }
/*     */       
/* 315 */       throw new IllegalStateException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializePrefixes(WriterContext context, WSDLDocument document) throws IOException {
/* 325 */     String tnsURI = document.getDefinitions().getTargetNamespaceURI();
/* 326 */     if (tnsURI != null) {
/* 327 */       context.setTargetNamespaceURI(tnsURI);
/* 328 */       context.declarePrefix("tns", tnsURI);
/*     */     } 
/*     */ 
/*     */     
/* 332 */     context.declarePrefix("", "http://schemas.xmlsoap.org/wsdl/");
/*     */ 
/*     */     
/* 335 */     Set namespaces = document.collectAllNamespaces();
/* 336 */     for (Iterator<String> iter = namespaces.iterator(); iter.hasNext(); ) {
/* 337 */       String nsURI = iter.next();
/* 338 */       if (context.getPrefixFor(nsURI) != null) {
/*     */         continue;
/*     */       }
/* 341 */       String prefix = (String)_commonPrefixes.get(nsURI);
/* 342 */       if (prefix == null)
/*     */       {
/* 344 */         prefix = context.findNewPrefix("ns");
/*     */       }
/* 346 */       context.declarePrefix(prefix, nsURI);
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
/* 357 */   private static Map _commonPrefixes = new HashMap<Object, Object>(); static {
/* 358 */     _commonPrefixes.put("http://schemas.xmlsoap.org/wsdl/", "wsdl");
/* 359 */     _commonPrefixes.put("http://schemas.xmlsoap.org/wsdl/soap/", "soap");
/* 360 */     _commonPrefixes.put("http://schemas.xmlsoap.org/wsdl/http/", "http");
/* 361 */     _commonPrefixes.put("http://schemas.xmlsoap.org/wsdl/mime/", "mime");
/* 362 */     _commonPrefixes.put("http://www.w3.org/2001/XMLSchema", "xsd");
/* 363 */     _commonPrefixes.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
/*     */   }
/*     */   
/*     */   private static final String TARGET_NAMESPACE_PREFIX = "tns";
/*     */   private static final String NEW_NAMESPACE_PREFIX_BASE = "ns";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\WSDLWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */