/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferSource;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndpointReferenceUtil
/*     */ {
/*     */   public static <T extends EndpointReference> T transform(Class<T> clazz, @NotNull EndpointReference epr) {
/*  75 */     assert epr != null;
/*  76 */     if (clazz.isAssignableFrom(W3CEndpointReference.class)) {
/*  77 */       if (epr instanceof W3CEndpointReference)
/*  78 */         return (T)epr; 
/*  79 */       if (epr instanceof MemberSubmissionEndpointReference) {
/*  80 */         return (T)toW3CEpr((MemberSubmissionEndpointReference)epr);
/*     */       }
/*  82 */     } else if (clazz.isAssignableFrom(MemberSubmissionEndpointReference.class)) {
/*  83 */       if (epr instanceof W3CEndpointReference)
/*  84 */         return (T)toMSEpr((W3CEndpointReference)epr); 
/*  85 */       if (epr instanceof MemberSubmissionEndpointReference) {
/*  86 */         return (T)epr;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  91 */     throw new WebServiceException("Unknwon EndpointReference: " + epr.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   private static W3CEndpointReference toW3CEpr(MemberSubmissionEndpointReference msEpr) {
/*  96 */     StreamWriterBufferCreator writer = new StreamWriterBufferCreator();
/*  97 */     w3cMetadataWritten = false;
/*     */     try {
/*  99 */       writer.writeStartDocument();
/* 100 */       writer.writeStartElement(AddressingVersion.W3C.getPrefix(), "EndpointReference", AddressingVersion.W3C.nsUri);
/*     */       
/* 102 */       writer.writeNamespace(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.nsUri);
/*     */ 
/*     */       
/* 105 */       writer.writeStartElement(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.eprType.address, AddressingVersion.W3C.nsUri);
/*     */ 
/*     */       
/* 108 */       writer.writeCharacters(msEpr.addr.uri);
/* 109 */       writer.writeEndElement();
/*     */       
/* 111 */       if ((msEpr.referenceProperties != null && msEpr.referenceProperties.elements.size() > 0) || (msEpr.referenceParameters != null && msEpr.referenceParameters.elements.size() > 0)) {
/*     */ 
/*     */         
/* 114 */         writer.writeStartElement(AddressingVersion.W3C.getPrefix(), "ReferenceParameters", AddressingVersion.W3C.nsUri);
/*     */ 
/*     */         
/* 117 */         if (msEpr.referenceProperties != null) {
/* 118 */           for (Element e : msEpr.referenceProperties.elements) {
/* 119 */             DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*     */           }
/*     */         }
/*     */         
/* 123 */         if (msEpr.referenceParameters != null) {
/* 124 */           for (Element e : msEpr.referenceParameters.elements) {
/* 125 */             DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*     */           }
/*     */         }
/* 128 */         writer.writeEndElement();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       Element wsdlElement = null;
/*     */       
/* 174 */       if (msEpr.elements != null && msEpr.elements.size() > 0) {
/* 175 */         for (Element e : msEpr.elements) {
/* 176 */           if (e.getNamespaceURI().equals(MemberSubmissionAddressingConstants.MEX_METADATA.getNamespaceURI()) && e.getLocalName().equals(MemberSubmissionAddressingConstants.MEX_METADATA.getLocalPart())) {
/*     */             
/* 178 */             NodeList nl = e.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", WSDLConstants.QNAME_DEFINITIONS.getLocalPart());
/*     */             
/* 180 */             if (nl != null) {
/* 181 */               wsdlElement = (Element)nl.item(0);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 187 */       if (wsdlElement != null) {
/* 188 */         DOMUtil.serializeNode(wsdlElement, (XMLStreamWriter)writer);
/*     */       }
/*     */       
/* 191 */       if (w3cMetadataWritten) {
/* 192 */         writer.writeEndElement();
/*     */       }
/*     */ 
/*     */       
/* 196 */       if (msEpr.elements != null && msEpr.elements.size() > 0) {
/* 197 */         for (Element e : msEpr.elements) {
/* 198 */           if (!e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/") || e.getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart()));
/*     */ 
/*     */ 
/*     */           
/* 202 */           DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       writer.writeEndElement();
/* 210 */       writer.writeEndDocument();
/* 211 */       writer.flush();
/* 212 */     } catch (XMLStreamException e) {
/* 213 */       throw new WebServiceException(e);
/*     */     } 
/* 215 */     return new W3CEndpointReference((Source)new XMLStreamBufferSource((XMLStreamBuffer)writer.getXMLStreamBuffer()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean w3cMetadataWritten = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MemberSubmissionEndpointReference toMSEpr(W3CEndpointReference w3cEpr) {
/* 228 */     DOMResult result = new DOMResult();
/* 229 */     w3cEpr.writeTo(result);
/* 230 */     Node eprNode = result.getNode();
/* 231 */     Element e = DOMUtil.getFirstElementChild(eprNode);
/* 232 */     if (e == null) {
/* 233 */       return null;
/*     */     }
/*     */     
/* 236 */     MemberSubmissionEndpointReference msEpr = new MemberSubmissionEndpointReference();
/*     */     
/* 238 */     NodeList nodes = e.getChildNodes();
/* 239 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 240 */       if (nodes.item(i).getNodeType() == 1) {
/* 241 */         Element child = (Element)nodes.item(i);
/* 242 */         if (child.getNamespaceURI().equals(AddressingVersion.W3C.nsUri) && child.getLocalName().equals(AddressingVersion.W3C.eprType.address)) {
/*     */           
/* 244 */           if (msEpr.addr == null) {
/* 245 */             msEpr.addr = new MemberSubmissionEndpointReference.Address();
/*     */           }
/* 247 */           msEpr.addr.uri = XmlUtil.getTextForNode(child);
/*     */         }
/* 249 */         else if (child.getNamespaceURI().equals(AddressingVersion.W3C.nsUri) && child.getLocalName().equals("ReferenceParameters")) {
/*     */           
/* 251 */           NodeList refParams = child.getChildNodes();
/* 252 */           for (int j = 0; j < refParams.getLength(); j++) {
/* 253 */             if (refParams.item(j).getNodeType() == 1) {
/* 254 */               if (msEpr.referenceParameters == null) {
/* 255 */                 msEpr.referenceParameters = new MemberSubmissionEndpointReference.Elements();
/* 256 */                 msEpr.referenceParameters.elements = new ArrayList();
/*     */               } 
/* 258 */               msEpr.referenceParameters.elements.add((Element)refParams.item(j));
/*     */             } 
/*     */           } 
/* 261 */         } else if (child.getNamespaceURI().equals(AddressingVersion.W3C.nsUri) && child.getLocalName().equals(AddressingVersion.W3C.eprType.wsdlMetadata.getLocalPart())) {
/*     */           
/* 263 */           NodeList metadata = child.getChildNodes();
/* 264 */           String wsdlLocation = child.getAttributeNS("http://www.w3.org/ns/wsdl-instance", "wsdlLocation");
/*     */           
/* 266 */           Element wsdlDefinitions = null;
/* 267 */           for (int j = 0; j < metadata.getLength(); j++) {
/* 268 */             Node node = metadata.item(j);
/* 269 */             if (node.getNodeType() == 1) {
/*     */ 
/*     */ 
/*     */               
/* 273 */               Element elm = (Element)node;
/* 274 */               if ((elm.getNamespaceURI().equals(AddressingVersion.W3C.wsdlNsUri) || elm.getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata")) && elm.getLocalName().equals(AddressingVersion.W3C.eprType.serviceName)) {
/*     */ 
/*     */                 
/* 277 */                 msEpr.serviceName = new MemberSubmissionEndpointReference.ServiceNameType();
/* 278 */                 msEpr.serviceName.portName = elm.getAttribute(AddressingVersion.W3C.eprType.portName);
/*     */                 
/* 280 */                 String service = elm.getTextContent();
/* 281 */                 String prefix = XmlUtil.getPrefix(service);
/* 282 */                 String name = XmlUtil.getLocalPart(service);
/*     */ 
/*     */                 
/* 285 */                 if (name != null)
/*     */                 
/*     */                 { 
/*     */                   
/* 289 */                   if (prefix != null) {
/* 290 */                     String ns = elm.lookupNamespaceURI(prefix);
/* 291 */                     if (ns != null) {
/* 292 */                       msEpr.serviceName.name = new QName(ns, name, prefix);
/*     */                     }
/*     */                   } else {
/* 295 */                     msEpr.serviceName.name = new QName(null, name);
/*     */                   } 
/* 297 */                   msEpr.serviceName.attributes = getAttributes(elm); } 
/* 298 */               } else if ((elm.getNamespaceURI().equals(AddressingVersion.W3C.wsdlNsUri) || elm.getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata")) && elm.getLocalName().equals(AddressingVersion.W3C.eprType.portTypeName)) {
/*     */ 
/*     */                 
/* 301 */                 msEpr.portTypeName = new MemberSubmissionEndpointReference.AttributedQName();
/*     */                 
/* 303 */                 String portType = elm.getTextContent();
/* 304 */                 String prefix = XmlUtil.getPrefix(portType);
/* 305 */                 String name = XmlUtil.getLocalPart(portType);
/*     */ 
/*     */                 
/* 308 */                 if (name != null)
/*     */                 
/*     */                 { 
/*     */                   
/* 312 */                   if (prefix != null) {
/* 313 */                     String ns = elm.lookupNamespaceURI(prefix);
/* 314 */                     if (ns != null) {
/* 315 */                       msEpr.portTypeName.name = new QName(ns, name, prefix);
/*     */                     }
/*     */                   } else {
/* 318 */                     msEpr.portTypeName.name = new QName(null, name);
/*     */                   } 
/* 320 */                   msEpr.portTypeName.attributes = getAttributes(elm); } 
/* 321 */               } else if (elm.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/") && elm.getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/*     */                 
/* 323 */                 wsdlDefinitions = elm;
/*     */               }
/*     */               else {
/*     */                 
/* 327 */                 if (msEpr.elements == null) {
/* 328 */                   msEpr.elements = new ArrayList();
/*     */                 }
/* 330 */                 msEpr.elements.add(elm);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 335 */           Document doc = DOMUtil.createDom();
/* 336 */           Element mexEl = doc.createElementNS(MemberSubmissionAddressingConstants.MEX_METADATA.getNamespaceURI(), MemberSubmissionAddressingConstants.MEX_METADATA.getPrefix() + ":" + MemberSubmissionAddressingConstants.MEX_METADATA.getLocalPart());
/*     */ 
/*     */           
/* 339 */           Element metadataEl = doc.createElementNS(MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getNamespaceURI(), MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getPrefix() + ":" + MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getLocalPart());
/*     */ 
/*     */           
/* 342 */           metadataEl.setAttribute("Dialect", "http://schemas.xmlsoap.org/wsdl/");
/*     */           
/* 344 */           if (wsdlDefinitions == null && wsdlLocation != null && !wsdlLocation.equals("")) {
/* 345 */             wsdlLocation = wsdlLocation.trim();
/* 346 */             String wsdlTns = wsdlLocation.substring(0, wsdlLocation.indexOf(' '));
/* 347 */             wsdlLocation = wsdlLocation.substring(wsdlLocation.indexOf(' ') + 1);
/* 348 */             Element wsdlEl = doc.createElementNS("http://schemas.xmlsoap.org/wsdl/", "wsdl:" + WSDLConstants.QNAME_DEFINITIONS.getLocalPart());
/*     */ 
/*     */             
/* 351 */             Element wsdlImportEl = doc.createElementNS("http://schemas.xmlsoap.org/wsdl/", "wsdl:" + WSDLConstants.QNAME_IMPORT.getLocalPart());
/*     */ 
/*     */             
/* 354 */             wsdlImportEl.setAttribute("namespace", wsdlTns);
/* 355 */             wsdlImportEl.setAttribute("location", wsdlLocation);
/* 356 */             wsdlEl.appendChild(wsdlImportEl);
/* 357 */             metadataEl.appendChild(wsdlEl);
/* 358 */           } else if (wsdlDefinitions != null) {
/* 359 */             metadataEl.appendChild(wsdlDefinitions);
/*     */           } 
/* 361 */           mexEl.appendChild(metadataEl);
/*     */           
/* 363 */           if (msEpr.elements == null) {
/* 364 */             msEpr.elements = new ArrayList();
/*     */           }
/* 366 */           msEpr.elements.add(mexEl);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 371 */           if (msEpr.elements == null) {
/* 372 */             msEpr.elements = new ArrayList();
/*     */           }
/* 374 */           msEpr.elements.add(child);
/*     */         }
/*     */       
/* 377 */       } else if (nodes.item(i).getNodeType() == 2) {
/* 378 */         Node n = nodes.item(i);
/* 379 */         if (msEpr.attributes == null) {
/* 380 */           msEpr.attributes = new HashMap<Object, Object>();
/* 381 */           String prefix = fixNull(n.getPrefix());
/* 382 */           String ns = fixNull(n.getNamespaceURI());
/* 383 */           String localName = n.getLocalName();
/* 384 */           msEpr.attributes.put(new QName(ns, localName, prefix), n.getNodeValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 389 */     return msEpr;
/*     */   }
/*     */   
/*     */   private static Map<QName, String> getAttributes(Node node) {
/* 393 */     Map<QName, String> attribs = null;
/*     */     
/* 395 */     NamedNodeMap nm = node.getAttributes();
/* 396 */     for (int i = 0; i < nm.getLength(); i++) {
/* 397 */       if (attribs == null) {
/* 398 */         attribs = new HashMap<QName, String>();
/*     */       }
/* 400 */       Node n = nm.item(i);
/* 401 */       String prefix = fixNull(n.getPrefix());
/* 402 */       String ns = fixNull(n.getNamespaceURI());
/* 403 */       String localName = n.getLocalName();
/* 404 */       if (!prefix.equals("xmlns") && (prefix.length() != 0 || !localName.equals("xmlns")))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 409 */         if (!localName.equals(AddressingVersion.W3C.eprType.portName))
/* 410 */           attribs.put(new QName(ns, localName, prefix), n.getNodeValue()); 
/*     */       }
/*     */     } 
/* 413 */     return attribs;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String fixNull(@Nullable String s) {
/* 419 */     if (s == null) {
/* 420 */       return "";
/*     */     }
/* 422 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\EndpointReferenceUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */