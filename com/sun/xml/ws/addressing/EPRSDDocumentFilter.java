/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.ws.api.server.Module;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.server.WSEndpointImpl;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamWriterFilter;
/*     */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EPRSDDocumentFilter
/*     */   implements SDDocumentFilter
/*     */ {
/*     */   private final WSEndpointImpl<?> endpoint;
/*     */   List<BoundEndpoint> beList;
/*     */   
/*     */   public EPRSDDocumentFilter(@NotNull WSEndpointImpl<?> endpoint) {
/*  75 */     this.endpoint = endpoint;
/*     */   }
/*     */   @Nullable
/*     */   private WSEndpointImpl<?> getEndpoint(String serviceName, String portName) {
/*  79 */     if (serviceName == null || portName == null)
/*  80 */       return null; 
/*  81 */     if (this.endpoint.getServiceName().getLocalPart().equals(serviceName) && this.endpoint.getPortName().getLocalPart().equals(portName)) {
/*  82 */       return this.endpoint;
/*     */     }
/*  84 */     if (this.beList == null) {
/*     */       
/*  86 */       Module module = (Module)this.endpoint.getContainer().getSPI(Module.class);
/*  87 */       if (module != null) {
/*  88 */         this.beList = module.getBoundEndpoints();
/*     */       } else {
/*  90 */         this.beList = Collections.emptyList();
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     for (BoundEndpoint be : this.beList) {
/*  95 */       WSEndpoint wse = be.getEndpoint();
/*  96 */       if (wse.getServiceName().getLocalPart().equals(serviceName) && wse.getPortName().getLocalPart().equals(portName)) {
/*  97 */         return (WSEndpointImpl)wse;
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamWriter filter(SDDocument doc, XMLStreamWriter w) throws XMLStreamException, IOException {
/* 106 */     if (!doc.isWSDL()) {
/* 107 */       return w;
/*     */     }
/*     */     
/* 110 */     return (XMLStreamWriter)new XMLStreamWriterFilter(w)
/*     */       {
/*     */         private boolean eprExtnFilterON = false;
/*     */         private boolean portHasEPR = false;
/* 114 */         private int eprDepth = -1;
/*     */         
/* 116 */         private String serviceName = null;
/*     */         private boolean onService = false;
/* 118 */         private int serviceDepth = -1;
/*     */         
/* 120 */         private String portName = null;
/*     */         private boolean onPort = false;
/* 122 */         private int portDepth = -1;
/*     */         
/*     */         private String portAddress;
/*     */         private boolean onPortAddress = false;
/*     */         
/*     */         private void handleStartElement(String localName, String namespaceURI) throws XMLStreamException {
/* 128 */           resetOnElementFlags();
/* 129 */           if (this.serviceDepth >= 0) {
/* 130 */             this.serviceDepth++;
/*     */           }
/* 132 */           if (this.portDepth >= 0) {
/* 133 */             this.portDepth++;
/*     */           }
/* 135 */           if (this.eprDepth >= 0) {
/* 136 */             this.eprDepth++;
/*     */           }
/*     */           
/* 139 */           if (namespaceURI.equals(WSDLConstants.QNAME_SERVICE.getNamespaceURI()) && localName.equals(WSDLConstants.QNAME_SERVICE.getLocalPart())) {
/* 140 */             this.onService = true;
/* 141 */             this.serviceDepth = 0;
/* 142 */           } else if (namespaceURI.equals(WSDLConstants.QNAME_PORT.getNamespaceURI()) && localName.equals(WSDLConstants.QNAME_PORT.getLocalPart())) {
/* 143 */             if (this.serviceDepth >= 1) {
/* 144 */               this.onPort = true;
/* 145 */               this.portDepth = 0;
/*     */             } 
/* 147 */           } else if (namespaceURI.equals("http://www.w3.org/2005/08/addressing") && localName.equals("EndpointReference")) {
/* 148 */             if (this.serviceDepth >= 1 && this.portDepth >= 1) {
/* 149 */               this.portHasEPR = true;
/* 150 */               this.eprDepth = 0;
/*     */             } 
/* 152 */           } else if ((namespaceURI.equals(WSDLConstants.NS_SOAP_BINDING_ADDRESS.getNamespaceURI()) || namespaceURI.equals(WSDLConstants.NS_SOAP12_BINDING_ADDRESS.getNamespaceURI())) && localName.equals("address") && this.portDepth == 1) {
/*     */             
/* 154 */             this.onPortAddress = true;
/*     */           } 
/* 156 */           WSEndpointImpl wSEndpointImpl = EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName);
/*     */ 
/*     */           
/* 159 */           if (wSEndpointImpl != null && 
/* 160 */             this.eprDepth == 1 && !namespaceURI.equals("http://www.w3.org/2005/08/addressing"))
/*     */           {
/* 162 */             this.eprExtnFilterON = true;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         private void resetOnElementFlags() {
/* 175 */           if (this.onService) {
/* 176 */             this.onService = false;
/*     */           }
/* 178 */           if (this.onPort) {
/* 179 */             this.onPort = false;
/*     */           }
/* 181 */           if (this.onPortAddress) {
/* 182 */             this.onPortAddress = false;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         private void writeEPRExtensions(Collection<WSEndpointReference.EPRExtension> eprExtns) throws XMLStreamException {
/* 189 */           if (eprExtns != null) {
/* 190 */             for (WSEndpointReference.EPRExtension e : eprExtns) {
/* 191 */               XMLStreamReaderToXMLStreamWriter c = new XMLStreamReaderToXMLStreamWriter();
/* 192 */               XMLStreamReader r = e.readAsXMLStreamReader();
/* 193 */               c.bridge(r, this.writer);
/* 194 */               XMLStreamReaderFactory.recycle(r);
/*     */             } 
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 201 */           handleStartElement(localName, namespaceURI);
/* 202 */           if (!this.eprExtnFilterON) {
/* 203 */             super.writeStartElement(prefix, localName, namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 209 */           handleStartElement(localName, namespaceURI);
/* 210 */           if (!this.eprExtnFilterON) {
/* 211 */             super.writeStartElement(namespaceURI, localName);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeStartElement(String localName) throws XMLStreamException {
/* 217 */           if (!this.eprExtnFilterON) {
/* 218 */             super.writeStartElement(localName);
/*     */           }
/*     */         }
/*     */         
/*     */         private void handleEndElement() throws XMLStreamException {
/* 223 */           resetOnElementFlags();
/*     */           
/* 225 */           if (this.portDepth == 0)
/*     */           {
/* 227 */             if (!this.portHasEPR && EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName) != null) {
/*     */ 
/*     */               
/* 230 */               this.writer.writeStartElement(AddressingVersion.W3C.getPrefix(), "EndpointReference", AddressingVersion.W3C.nsUri);
/* 231 */               this.writer.writeNamespace(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.nsUri);
/* 232 */               this.writer.writeStartElement(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.eprType.address, AddressingVersion.W3C.nsUri);
/* 233 */               this.writer.writeCharacters(this.portAddress);
/* 234 */               this.writer.writeEndElement();
/* 235 */               writeEPRExtensions(EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName).getEndpointReferenceExtensions());
/* 236 */               this.writer.writeEndElement();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/* 241 */           if (this.eprDepth == 0) {
/* 242 */             if (this.portHasEPR && EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName) != null) {
/* 243 */               writeEPRExtensions(EPRSDDocumentFilter.this.getEndpoint(this.serviceName, this.portName).getEndpointReferenceExtensions());
/*     */             }
/* 245 */             this.eprExtnFilterON = false;
/*     */           } 
/*     */           
/* 248 */           if (this.serviceDepth >= 0) {
/* 249 */             this.serviceDepth--;
/*     */           }
/* 251 */           if (this.portDepth >= 0) {
/* 252 */             this.portDepth--;
/*     */           }
/* 254 */           if (this.eprDepth >= 0) {
/* 255 */             this.eprDepth--;
/*     */           }
/*     */           
/* 258 */           if (this.serviceDepth == -1) {
/* 259 */             this.serviceName = null;
/*     */           }
/* 261 */           if (this.portDepth == -1) {
/* 262 */             this.portHasEPR = false;
/* 263 */             this.portAddress = null;
/* 264 */             this.portName = null;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEndElement() throws XMLStreamException {
/* 270 */           handleEndElement();
/* 271 */           if (!this.eprExtnFilterON) {
/* 272 */             super.writeEndElement();
/*     */           }
/*     */         }
/*     */         
/*     */         private void handleAttribute(String localName, String value) {
/* 277 */           if (localName.equals("name")) {
/* 278 */             if (this.onService) {
/* 279 */               this.serviceName = value;
/* 280 */               this.onService = false;
/* 281 */             } else if (this.onPort) {
/* 282 */               this.portName = value;
/* 283 */               this.onPort = false;
/*     */             } 
/*     */           }
/* 286 */           if (localName.equals("location") && this.onPortAddress) {
/* 287 */             this.portAddress = value;
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 295 */           handleAttribute(localName, value);
/* 296 */           if (!this.eprExtnFilterON) {
/* 297 */             super.writeAttribute(prefix, namespaceURI, localName, value);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 303 */           handleAttribute(localName, value);
/* 304 */           if (!this.eprExtnFilterON) {
/* 305 */             super.writeAttribute(namespaceURI, localName, value);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 311 */           handleAttribute(localName, value);
/* 312 */           if (!this.eprExtnFilterON) {
/* 313 */             super.writeAttribute(localName, value);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 320 */           if (!this.eprExtnFilterON) {
/* 321 */             super.writeEmptyElement(namespaceURI, localName);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 327 */           if (!this.eprExtnFilterON) {
/* 328 */             super.writeNamespace(prefix, namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 334 */           if (!this.eprExtnFilterON) {
/* 335 */             super.setNamespaceContext(context);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 341 */           if (!this.eprExtnFilterON) {
/* 342 */             super.setDefaultNamespace(uri);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 348 */           if (!this.eprExtnFilterON) {
/* 349 */             super.setPrefix(prefix, uri);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 355 */           if (!this.eprExtnFilterON) {
/* 356 */             super.writeProcessingInstruction(target, data);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 362 */           if (!this.eprExtnFilterON) {
/* 363 */             super.writeEmptyElement(prefix, localName, namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeCData(String data) throws XMLStreamException {
/* 369 */           if (!this.eprExtnFilterON) {
/* 370 */             super.writeCData(data);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeCharacters(String text) throws XMLStreamException {
/* 376 */           if (!this.eprExtnFilterON) {
/* 377 */             super.writeCharacters(text);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeComment(String data) throws XMLStreamException {
/* 383 */           if (!this.eprExtnFilterON) {
/* 384 */             super.writeComment(data);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeDTD(String dtd) throws XMLStreamException {
/* 390 */           if (!this.eprExtnFilterON) {
/* 391 */             super.writeDTD(dtd);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 397 */           if (!this.eprExtnFilterON) {
/* 398 */             super.writeDefaultNamespace(namespaceURI);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEmptyElement(String localName) throws XMLStreamException {
/* 404 */           if (!this.eprExtnFilterON) {
/* 405 */             super.writeEmptyElement(localName);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeEntityRef(String name) throws XMLStreamException {
/* 411 */           if (!this.eprExtnFilterON) {
/* 412 */             super.writeEntityRef(name);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 418 */           if (!this.eprExtnFilterON) {
/* 419 */             super.writeProcessingInstruction(target);
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 426 */           if (!this.eprExtnFilterON)
/* 427 */             super.writeCharacters(text, start, len); 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\EPRSDDocumentFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */