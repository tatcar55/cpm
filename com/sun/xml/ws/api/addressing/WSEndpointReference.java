/*      */ package com.sun.xml.ws.api.addressing;
/*      */ 
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Nullable;
/*      */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBufferSource;
/*      */ import com.sun.xml.stream.buffer.sax.SAXBufferProcessor;
/*      */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*      */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*      */ import com.sun.xml.ws.addressing.EndpointReferenceUtil;
/*      */ import com.sun.xml.ws.addressing.WSEPRExtension;
/*      */ import com.sun.xml.ws.addressing.model.InvalidAddressingHeaderException;
/*      */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*      */ import com.sun.xml.ws.api.message.Header;
/*      */ import com.sun.xml.ws.api.message.HeaderList;
/*      */ import com.sun.xml.ws.api.message.MessageHeaders;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*      */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*      */ import com.sun.xml.ws.resources.AddressingMessages;
/*      */ import com.sun.xml.ws.resources.ClientMessages;
/*      */ import com.sun.xml.ws.spi.ProviderImpl;
/*      */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*      */ import com.sun.xml.ws.util.DOMUtil;
/*      */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*      */ import com.sun.xml.ws.util.xml.XMLStreamWriterFilter;
/*      */ import com.sun.xml.ws.util.xml.XmlUtil;
/*      */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*      */ import java.io.InputStream;
/*      */ import java.io.StringWriter;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.xml.bind.JAXBContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.sax.SAXSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import javax.xml.ws.Dispatch;
/*      */ import javax.xml.ws.EndpointReference;
/*      */ import javax.xml.ws.Service;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import javax.xml.ws.WebServiceFeature;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.Attributes;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.XMLReader;
/*      */ import org.xml.sax.helpers.XMLFilterImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class WSEndpointReference
/*      */   implements WSDLExtension
/*      */ {
/*      */   private final XMLStreamBuffer infoset;
/*      */   private final AddressingVersion version;
/*      */   @NotNull
/*      */   private Header[] referenceParameters;
/*      */   @NotNull
/*      */   private String address;
/*      */   @NotNull
/*      */   private QName rootElement;
/*      */   
/*      */   public WSEndpointReference(EndpointReference epr, AddressingVersion version) {
/*      */     try {
/*  134 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  135 */       epr.writeTo((Result)new XMLStreamBufferResult(xsb));
/*  136 */       this.infoset = (XMLStreamBuffer)xsb;
/*  137 */       this.version = version;
/*  138 */       this.rootElement = new QName("EndpointReference", version.nsUri);
/*  139 */       parse();
/*  140 */     } catch (XMLStreamException e) {
/*  141 */       throw new WebServiceException(ClientMessages.FAILED_TO_PARSE_EPR(epr), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(EndpointReference epr) {
/*  153 */     this(epr, AddressingVersion.fromSpecClass((Class)epr.getClass()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(XMLStreamBuffer infoset, AddressingVersion version) {
/*      */     try {
/*  161 */       this.infoset = infoset;
/*  162 */       this.version = version;
/*  163 */       this.rootElement = new QName("EndpointReference", version.nsUri);
/*  164 */       parse();
/*  165 */     } catch (XMLStreamException e) {
/*      */       
/*  167 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(InputStream infoset, AddressingVersion version) throws XMLStreamException {
/*  175 */     this(XMLStreamReaderFactory.create(null, infoset, false), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(XMLStreamReader in, AddressingVersion version) throws XMLStreamException {
/*  183 */     this(XMLStreamBuffer.createNewBufferFromXMLStreamReader(in), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(URL address, AddressingVersion version) {
/*  190 */     this(address.toExternalForm(), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(URI address, AddressingVersion version) {
/*  197 */     this(address.toString(), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(String address, AddressingVersion version) {
/*  204 */     this.infoset = createBufferFromAddress(address, version);
/*  205 */     this.version = version;
/*  206 */     this.address = address;
/*  207 */     this.rootElement = new QName("EndpointReference", version.nsUri);
/*  208 */     this.referenceParameters = (Header[])EMPTY_ARRAY;
/*      */   }
/*      */   
/*      */   private static XMLStreamBuffer createBufferFromAddress(String address, AddressingVersion version) {
/*      */     try {
/*  213 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  214 */       StreamWriterBufferCreator w = new StreamWriterBufferCreator(xsb);
/*  215 */       w.writeStartDocument();
/*  216 */       w.writeStartElement(version.getPrefix(), "EndpointReference", version.nsUri);
/*      */       
/*  218 */       w.writeNamespace(version.getPrefix(), version.nsUri);
/*  219 */       w.writeStartElement(version.getPrefix(), version.eprType.address, version.nsUri);
/*  220 */       w.writeCharacters(address);
/*  221 */       w.writeEndElement();
/*  222 */       w.writeEndElement();
/*  223 */       w.writeEndDocument();
/*  224 */       w.close();
/*  225 */       return (XMLStreamBuffer)xsb;
/*  226 */     } catch (XMLStreamException e) {
/*      */       
/*  228 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(@NotNull AddressingVersion version, @NotNull String address, @Nullable QName service, @Nullable QName port, @Nullable QName portType, @Nullable List<Element> metadata, @Nullable String wsdlAddress, @Nullable List<Element> referenceParameters) {
/*  247 */     this(version, address, service, port, portType, metadata, wsdlAddress, null, referenceParameters, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(@NotNull AddressingVersion version, @NotNull String address, @Nullable QName service, @Nullable QName port, @Nullable QName portType, @Nullable List<Element> metadata, @Nullable String wsdlAddress, @Nullable List<Element> referenceParameters, @Nullable Collection<EPRExtension> extns, @Nullable Map<QName, String> attributes) {
/*  266 */     this(createBufferFromData(version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, (String)null, extns, attributes), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSEndpointReference(@NotNull AddressingVersion version, @NotNull String address, @Nullable QName service, @Nullable QName port, @Nullable QName portType, @Nullable List<Element> metadata, @Nullable String wsdlAddress, @Nullable String wsdlTargetNamepsace, @Nullable List<Element> referenceParameters, @Nullable List<Element> elements, @Nullable Map<QName, String> attributes) {
/*  288 */     this(createBufferFromData(version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, wsdlTargetNamepsace, elements, attributes), version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamBuffer createBufferFromData(AddressingVersion version, String address, List<Element> referenceParameters, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace, @Nullable List<Element> elements, @Nullable Map<QName, String> attributes) {
/*  296 */     StreamWriterBufferCreator writer = new StreamWriterBufferCreator();
/*      */     
/*      */     try {
/*  299 */       writer.writeStartDocument();
/*  300 */       writer.writeStartElement(version.getPrefix(), "EndpointReference", version.nsUri);
/*  301 */       writer.writeNamespace(version.getPrefix(), version.nsUri);
/*      */       
/*  303 */       writePartialEPRInfoset(writer, version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, wsdlTargetNamespace, attributes);
/*      */ 
/*      */ 
/*      */       
/*  307 */       if (elements != null) {
/*  308 */         for (Element e : elements) {
/*  309 */           DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*      */         }
/*      */       }
/*      */       
/*  313 */       writer.writeEndElement();
/*  314 */       writer.writeEndDocument();
/*  315 */       writer.flush();
/*      */       
/*  317 */       return (XMLStreamBuffer)writer.getXMLStreamBuffer();
/*  318 */     } catch (XMLStreamException e) {
/*  319 */       throw new WebServiceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamBuffer createBufferFromData(AddressingVersion version, String address, List<Element> referenceParameters, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace, @Nullable Collection<EPRExtension> extns, @Nullable Map<QName, String> attributes) {
/*  326 */     StreamWriterBufferCreator writer = new StreamWriterBufferCreator();
/*      */     
/*      */     try {
/*  329 */       writer.writeStartDocument();
/*  330 */       writer.writeStartElement(version.getPrefix(), "EndpointReference", version.nsUri);
/*  331 */       writer.writeNamespace(version.getPrefix(), version.nsUri);
/*      */       
/*  333 */       writePartialEPRInfoset(writer, version, address, referenceParameters, service, port, portType, metadata, wsdlAddress, wsdlTargetNamespace, attributes);
/*      */ 
/*      */ 
/*      */       
/*  337 */       if (extns != null) {
/*  338 */         for (EPRExtension e : extns) {
/*  339 */           XMLStreamReaderToXMLStreamWriter c = new XMLStreamReaderToXMLStreamWriter();
/*  340 */           XMLStreamReader r = e.readAsXMLStreamReader();
/*  341 */           c.bridge(r, (XMLStreamWriter)writer);
/*  342 */           XMLStreamReaderFactory.recycle(r);
/*      */         } 
/*      */       }
/*      */       
/*  346 */       writer.writeEndElement();
/*  347 */       writer.writeEndDocument();
/*  348 */       writer.flush();
/*      */       
/*  350 */       return (XMLStreamBuffer)writer.getXMLStreamBuffer();
/*  351 */     } catch (XMLStreamException e) {
/*  352 */       throw new WebServiceException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writePartialEPRInfoset(StreamWriterBufferCreator writer, AddressingVersion version, String address, List<Element> referenceParameters, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace, @Nullable Map<QName, String> attributes) throws XMLStreamException {
/*  359 */     if (attributes != null) {
/*  360 */       for (Map.Entry<QName, String> entry : attributes.entrySet()) {
/*  361 */         QName qname = entry.getKey();
/*  362 */         writer.writeAttribute(qname.getPrefix(), qname.getNamespaceURI(), qname.getLocalPart(), entry.getValue());
/*      */       } 
/*      */     }
/*      */     
/*  366 */     writer.writeStartElement(version.getPrefix(), version.eprType.address, version.nsUri);
/*  367 */     writer.writeCharacters(address);
/*  368 */     writer.writeEndElement();
/*      */     
/*  370 */     if (referenceParameters != null && referenceParameters.size() > 0) {
/*  371 */       writer.writeStartElement(version.getPrefix(), version.eprType.referenceParameters, version.nsUri);
/*  372 */       for (Element e : referenceParameters) {
/*  373 */         DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*      */       }
/*  375 */       writer.writeEndElement();
/*      */     } 
/*      */     
/*  378 */     switch (version) {
/*      */       case W3C:
/*  380 */         writeW3CMetaData(writer, service, port, portType, metadata, wsdlAddress, wsdlTargetNamespace);
/*      */         break;
/*      */       
/*      */       case MEMBER:
/*  384 */         writeMSMetaData(writer, service, port, portType, metadata);
/*  385 */         if (wsdlAddress != null) {
/*      */ 
/*      */           
/*  388 */           writer.writeStartElement(MemberSubmissionAddressingConstants.MEX_METADATA.getPrefix(), MemberSubmissionAddressingConstants.MEX_METADATA.getLocalPart(), MemberSubmissionAddressingConstants.MEX_METADATA.getNamespaceURI());
/*      */ 
/*      */           
/*  391 */           writer.writeStartElement(MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getPrefix(), MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getLocalPart(), MemberSubmissionAddressingConstants.MEX_METADATA_SECTION.getNamespaceURI());
/*      */ 
/*      */           
/*  394 */           writer.writeAttribute("Dialect", "http://schemas.xmlsoap.org/wsdl/");
/*      */ 
/*      */           
/*  397 */           writeWsdl(writer, service, wsdlAddress);
/*      */           
/*  399 */           writer.writeEndElement();
/*  400 */           writer.writeEndElement();
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isEmty(QName qname) {
/*  408 */     return (qname == null || qname.toString().trim().length() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeW3CMetaData(StreamWriterBufferCreator writer, QName service, QName port, QName portType, List<Element> metadata, String wsdlAddress, String wsdlTargetNamespace) throws XMLStreamException {
/*  419 */     if (isEmty(service) && isEmty(port) && isEmty(portType) && metadata == null) {
/*      */       return;
/*      */     }
/*      */     
/*  423 */     writer.writeStartElement(AddressingVersion.W3C.getPrefix(), AddressingVersion.W3C.eprType.wsdlMetadata.getLocalPart(), AddressingVersion.W3C.nsUri);
/*      */     
/*  425 */     writer.writeNamespace(AddressingVersion.W3C.getWsdlPrefix(), AddressingVersion.W3C.wsdlNsUri);
/*      */ 
/*      */     
/*  428 */     if (wsdlAddress != null) {
/*  429 */       writeWsdliLocation(writer, service, wsdlAddress, wsdlTargetNamespace);
/*      */     }
/*      */ 
/*      */     
/*  433 */     if (portType != null) {
/*  434 */       writer.writeStartElement("wsam", AddressingVersion.W3C.eprType.portTypeName, "http://www.w3.org/2007/05/addressing/metadata");
/*      */ 
/*      */       
/*  437 */       writer.writeNamespace("wsam", "http://www.w3.org/2007/05/addressing/metadata");
/*      */       
/*  439 */       String portTypePrefix = portType.getPrefix();
/*  440 */       if (portTypePrefix == null || portTypePrefix.equals(""))
/*      */       {
/*  442 */         portTypePrefix = "wsns";
/*      */       }
/*  444 */       writer.writeNamespace(portTypePrefix, portType.getNamespaceURI());
/*  445 */       writer.writeCharacters(portTypePrefix + ":" + portType.getLocalPart());
/*  446 */       writer.writeEndElement();
/*      */     } 
/*  448 */     if (service != null)
/*      */     {
/*  450 */       if (!service.getNamespaceURI().equals("") && !service.getLocalPart().equals("")) {
/*  451 */         writer.writeStartElement("wsam", AddressingVersion.W3C.eprType.serviceName, "http://www.w3.org/2007/05/addressing/metadata");
/*      */ 
/*      */         
/*  454 */         writer.writeNamespace("wsam", "http://www.w3.org/2007/05/addressing/metadata");
/*      */         
/*  456 */         String servicePrefix = service.getPrefix();
/*  457 */         if (servicePrefix == null || servicePrefix.equals(""))
/*      */         {
/*  459 */           servicePrefix = "wsns";
/*      */         }
/*  461 */         writer.writeNamespace(servicePrefix, service.getNamespaceURI());
/*  462 */         if (port != null) {
/*  463 */           writer.writeAttribute(AddressingVersion.W3C.eprType.portName, port.getLocalPart());
/*      */         }
/*  465 */         writer.writeCharacters(servicePrefix + ":" + service.getLocalPart());
/*  466 */         writer.writeEndElement();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  476 */     if (metadata != null) {
/*  477 */       for (Element e : metadata) {
/*  478 */         DOMUtil.serializeNode(e, (XMLStreamWriter)writer);
/*      */       }
/*      */     }
/*  481 */     writer.writeEndElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeWsdliLocation(StreamWriterBufferCreator writer, QName service, String wsdlAddress, String wsdlTargetNamespace) throws XMLStreamException {
/*  493 */     String wsdliLocation = "";
/*  494 */     if (wsdlTargetNamespace != null) {
/*  495 */       wsdliLocation = wsdlTargetNamespace + " ";
/*  496 */     } else if (service != null) {
/*  497 */       wsdliLocation = service.getNamespaceURI() + " ";
/*      */     } else {
/*  499 */       throw new WebServiceException("WSDL target Namespace cannot be resolved");
/*      */     } 
/*  501 */     wsdliLocation = wsdliLocation + wsdlAddress;
/*  502 */     writer.writeNamespace("wsdli", "http://www.w3.org/ns/wsdl-instance");
/*      */     
/*  504 */     writer.writeAttribute("wsdli", "http://www.w3.org/ns/wsdl-instance", "wsdlLocation", wsdliLocation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeMSMetaData(StreamWriterBufferCreator writer, QName service, QName port, QName portType, List<Element> metadata) throws XMLStreamException {
/*  516 */     if (portType != null) {
/*      */       
/*  518 */       writer.writeStartElement(AddressingVersion.MEMBER.getPrefix(), AddressingVersion.MEMBER.eprType.portTypeName, AddressingVersion.MEMBER.nsUri);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  523 */       String portTypePrefix = portType.getPrefix();
/*  524 */       if (portTypePrefix == null || portTypePrefix.equals(""))
/*      */       {
/*  526 */         portTypePrefix = "wsns";
/*      */       }
/*  528 */       writer.writeNamespace(portTypePrefix, portType.getNamespaceURI());
/*  529 */       writer.writeCharacters(portTypePrefix + ":" + portType.getLocalPart());
/*  530 */       writer.writeEndElement();
/*      */     } 
/*      */     
/*  533 */     if (service != null && 
/*  534 */       !service.getNamespaceURI().equals("") && !service.getLocalPart().equals("")) {
/*  535 */       writer.writeStartElement(AddressingVersion.MEMBER.getPrefix(), AddressingVersion.MEMBER.eprType.serviceName, AddressingVersion.MEMBER.nsUri);
/*      */ 
/*      */       
/*  538 */       String servicePrefix = service.getPrefix();
/*  539 */       if (servicePrefix == null || servicePrefix.equals(""))
/*      */       {
/*  541 */         servicePrefix = "wsns";
/*      */       }
/*  543 */       writer.writeNamespace(servicePrefix, service.getNamespaceURI());
/*  544 */       if (port != null) {
/*  545 */         writer.writeAttribute(AddressingVersion.MEMBER.eprType.portName, port.getLocalPart());
/*      */       }
/*      */       
/*  548 */       writer.writeCharacters(servicePrefix + ":" + service.getLocalPart());
/*  549 */       writer.writeEndElement();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void writeWsdl(StreamWriterBufferCreator writer, QName service, String wsdlAddress) throws XMLStreamException {
/*  556 */     writer.writeStartElement("wsdl", WSDLConstants.QNAME_DEFINITIONS.getLocalPart(), "http://schemas.xmlsoap.org/wsdl/");
/*      */ 
/*      */     
/*  559 */     writer.writeNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/");
/*  560 */     writer.writeStartElement("wsdl", WSDLConstants.QNAME_IMPORT.getLocalPart(), "http://schemas.xmlsoap.org/wsdl/");
/*      */ 
/*      */     
/*  563 */     writer.writeAttribute("namespace", service.getNamespaceURI());
/*  564 */     writer.writeAttribute("location", wsdlAddress);
/*  565 */     writer.writeEndElement();
/*  566 */     writer.writeEndElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static WSEndpointReference create(@Nullable EndpointReference epr) {
/*  580 */     if (epr != null) {
/*  581 */       return new WSEndpointReference(epr);
/*      */     }
/*  583 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public WSEndpointReference createWithAddress(@NotNull URI newAddress) {
/*  591 */     return createWithAddress(newAddress.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public WSEndpointReference createWithAddress(@NotNull URL newAddress) {
/*  598 */     return createWithAddress(newAddress.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public WSEndpointReference createWithAddress(@NotNull final String newAddress) {
/*  618 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*  619 */     XMLFilterImpl filter = new XMLFilterImpl() {
/*      */         private boolean inAddress = false;
/*      */         
/*      */         public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/*  623 */           if (localName.equals("Address") && uri.equals(WSEndpointReference.this.version.nsUri)) {
/*  624 */             this.inAddress = true;
/*      */           }
/*  626 */           super.startElement(uri, localName, qName, atts);
/*      */         }
/*      */ 
/*      */         
/*      */         public void characters(char[] ch, int start, int length) throws SAXException {
/*  631 */           if (!this.inAddress) {
/*  632 */             super.characters(ch, start, length);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public void endElement(String uri, String localName, String qName) throws SAXException {
/*  638 */           if (this.inAddress) {
/*  639 */             super.characters(newAddress.toCharArray(), 0, newAddress.length());
/*      */           }
/*  641 */           this.inAddress = false;
/*  642 */           super.endElement(uri, localName, qName);
/*      */         }
/*      */       };
/*  645 */     filter.setContentHandler((ContentHandler)xsb.createFromSAXBufferCreator());
/*      */     try {
/*  647 */       this.infoset.writeTo(filter, false);
/*  648 */     } catch (SAXException e) {
/*  649 */       throw new AssertionError(e);
/*      */     } 
/*      */     
/*  652 */     return new WSEndpointReference((XMLStreamBuffer)xsb, this.version);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public EndpointReference toSpec() {
/*  665 */     return ProviderImpl.INSTANCE.readEndpointReference(asSource("EndpointReference"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public <T extends EndpointReference> T toSpec(Class<T> clazz) {
/*  675 */     return (T)EndpointReferenceUtil.transform(clazz, toSpec());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public <T> T getPort(@NotNull Service jaxwsService, @NotNull Class<T> serviceEndpointInterface, WebServiceFeature... features) {
/*  691 */     return jaxwsService.getPort(toSpec(), serviceEndpointInterface, features);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public <T> Dispatch<T> createDispatch(@NotNull Service jaxwsService, @NotNull Class<T> type, @NotNull Service.Mode mode, WebServiceFeature... features) {
/*  710 */     return jaxwsService.createDispatch(toSpec(), type, mode, features);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Dispatch<Object> createDispatch(@NotNull Service jaxwsService, @NotNull JAXBContext context, @NotNull Service.Mode mode, WebServiceFeature... features) {
/*  729 */     return jaxwsService.createDispatch(toSpec(), context, mode, features);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public AddressingVersion getVersion() {
/*  736 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public String getAddress() {
/*  743 */     return this.address;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnonymous() {
/*  750 */     return this.address.equals(this.version.anonymousUri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNone() {
/*  758 */     return this.address.equals(this.version.noneUri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parse() throws XMLStreamException {
/*  768 */     StreamReaderBufferProcessor xsr = this.infoset.readAsXMLStreamReader();
/*      */ 
/*      */     
/*  771 */     if (xsr.getEventType() == 7) {
/*  772 */       xsr.nextTag();
/*      */     }
/*  774 */     assert xsr.getEventType() == 1;
/*      */     
/*  776 */     String rootLocalName = xsr.getLocalName();
/*  777 */     if (!xsr.getNamespaceURI().equals(this.version.nsUri)) {
/*  778 */       throw new WebServiceException(AddressingMessages.WRONG_ADDRESSING_VERSION(this.version.nsUri, xsr.getNamespaceURI()));
/*      */     }
/*      */ 
/*      */     
/*  782 */     this.rootElement = new QName(xsr.getNamespaceURI(), rootLocalName);
/*      */ 
/*      */     
/*  785 */     List<Header> marks = null;
/*      */     
/*  787 */     while (xsr.nextTag() == 1) {
/*  788 */       String localName = xsr.getLocalName();
/*  789 */       if (this.version.isReferenceParameter(localName)) {
/*      */         XMLStreamBuffer mark;
/*  791 */         while ((mark = xsr.nextTagAndMark()) != null) {
/*  792 */           if (marks == null) {
/*  793 */             marks = new ArrayList<Header>();
/*      */           }
/*      */ 
/*      */           
/*  797 */           marks.add(this.version.createReferenceParameterHeader(mark, xsr.getNamespaceURI(), xsr.getLocalName()));
/*      */           
/*  799 */           XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */         }  continue;
/*      */       } 
/*  802 */       if (localName.equals("Address")) {
/*  803 */         if (this.address != null) {
/*  804 */           throw new InvalidAddressingHeaderException(new QName(this.version.nsUri, rootLocalName), AddressingVersion.fault_duplicateAddressInEpr);
/*      */         }
/*  806 */         this.address = xsr.getElementText().trim(); continue;
/*      */       } 
/*  808 */       XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  814 */     if (marks == null) {
/*  815 */       this.referenceParameters = (Header[])EMPTY_ARRAY;
/*      */     } else {
/*  817 */       this.referenceParameters = marks.<Header>toArray(new Header[marks.size()]);
/*      */     } 
/*      */     
/*  820 */     if (this.address == null) {
/*  821 */       throw new InvalidAddressingHeaderException(new QName(this.version.nsUri, rootLocalName), this.version.fault_missingAddressInEpr);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamReader read(@NotNull final String localName) throws XMLStreamException {
/*  835 */     return (XMLStreamReader)new StreamReaderBufferProcessor(this.infoset)
/*      */       {
/*      */         protected void processElement(String prefix, String uri, String _localName, boolean inScope) {
/*  838 */           if (this._depth == 0) {
/*  839 */             _localName = localName;
/*      */           }
/*  841 */           super.processElement(prefix, uri, _localName, WSEndpointReference.this.isInscope(WSEndpointReference.this.infoset, this._depth));
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private boolean isInscope(XMLStreamBuffer buffer, int depth) {
/*  847 */     return (buffer.getInscopeNamespaces().size() > 0 && depth == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Source asSource(@NotNull String localName) {
/*  859 */     return new SAXSource((XMLReader)new SAXBufferProcessorImpl(localName), new InputSource());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(@NotNull String localName, ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*  874 */     SAXBufferProcessorImpl p = new SAXBufferProcessorImpl(localName);
/*  875 */     p.setContentHandler(contentHandler);
/*  876 */     p.setErrorHandler(errorHandler);
/*  877 */     p.process(this.infoset, fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(@NotNull final String localName, @NotNull XMLStreamWriter w) throws XMLStreamException {
/*  888 */     this.infoset.writeToXMLStreamWriter((XMLStreamWriter)new XMLStreamWriterFilter(w)
/*      */         {
/*      */           private boolean root = true;
/*      */ 
/*      */ 
/*      */           
/*      */           public void writeStartDocument() throws XMLStreamException {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void writeStartDocument(String encoding, String version) throws XMLStreamException {}
/*      */ 
/*      */           
/*      */           public void writeStartDocument(String version) throws XMLStreamException {}
/*      */ 
/*      */           
/*      */           public void writeEndDocument() throws XMLStreamException {}
/*      */ 
/*      */           
/*      */           private String override(String ln) {
/*  908 */             if (this.root) {
/*  909 */               this.root = false;
/*  910 */               return localName;
/*      */             } 
/*  912 */             return ln;
/*      */           }
/*      */ 
/*      */           
/*      */           public void writeStartElement(String localName) throws XMLStreamException {
/*  917 */             super.writeStartElement(override(localName));
/*      */           }
/*      */ 
/*      */           
/*      */           public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/*  922 */             super.writeStartElement(namespaceURI, override(localName));
/*      */           }
/*      */ 
/*      */           
/*      */           public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/*  927 */             super.writeStartElement(prefix, override(localName), namespaceURI);
/*      */           }
/*      */         }true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Header createHeader(QName rootTagName) {
/*  946 */     return (Header)new EPRHeader(rootTagName, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReferenceParametersToList(HeaderList outbound) {
/*  958 */     for (Header header : this.referenceParameters) {
/*  959 */       outbound.add(header);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReferenceParametersToList(MessageHeaders outbound) {
/*  968 */     for (Header header : this.referenceParameters) {
/*  969 */       outbound.add(header);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addReferenceParameters(HeaderList headers) {
/*  977 */     if (headers != null) {
/*  978 */       Header[] hs = new Header[this.referenceParameters.length + headers.size()];
/*  979 */       System.arraycopy(this.referenceParameters, 0, hs, 0, this.referenceParameters.length);
/*  980 */       int i = this.referenceParameters.length;
/*  981 */       for (Header h : headers) {
/*  982 */         hs[i++] = h;
/*      */       }
/*  984 */       this.referenceParameters = hs;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/*  995 */       StringWriter sw = new StringWriter();
/*  996 */       XmlUtil.newTransformer().transform(asSource("EndpointReference"), new StreamResult(sw));
/*  997 */       return sw.toString();
/*  998 */     } catch (TransformerException e) {
/*  999 */       return e.toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getName() {
/* 1009 */     return this.rootElement;
/*      */   }
/*      */ 
/*      */   
/*      */   class SAXBufferProcessorImpl
/*      */     extends SAXBufferProcessor
/*      */   {
/*      */     private final String rootLocalName;
/*      */     private boolean root = true;
/*      */     
/*      */     public SAXBufferProcessorImpl(String rootLocalName) {
/* 1020 */       super(WSEndpointReference.this.infoset, false);
/* 1021 */       this.rootLocalName = rootLocalName;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void processElement(String uri, String localName, String qName, boolean inscope) throws SAXException {
/* 1026 */       if (this.root) {
/* 1027 */         this.root = false;
/*      */         
/* 1029 */         if (qName.equals(localName)) {
/* 1030 */           qName = localName = this.rootLocalName;
/*      */         } else {
/* 1032 */           localName = this.rootLocalName;
/* 1033 */           int idx = qName.indexOf(':');
/* 1034 */           qName = qName.substring(0, idx + 1) + this.rootLocalName;
/*      */         } 
/*      */       } 
/* 1037 */       super.processElement(uri, localName, qName, inscope);
/*      */     }
/*      */   }
/*      */   
/* 1041 */   private static final OutboundReferenceParameterHeader[] EMPTY_ARRAY = new OutboundReferenceParameterHeader[0];
/*      */ 
/*      */   
/*      */   private Map<QName, EPRExtension> rootEprExtensions;
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class EPRExtension
/*      */   {
/*      */     public abstract XMLStreamReader readAsXMLStreamReader() throws XMLStreamException;
/*      */ 
/*      */     
/*      */     public abstract QName getQName();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EPRExtension getEPRExtension(QName extnQName) throws XMLStreamException {
/* 1059 */     if (this.rootEprExtensions == null) {
/* 1060 */       parseEPRExtensions();
/*      */     }
/* 1062 */     return this.rootEprExtensions.get(extnQName);
/*      */   }
/*      */   @NotNull
/*      */   public Collection<EPRExtension> getEPRExtensions() throws XMLStreamException {
/* 1066 */     if (this.rootEprExtensions == null) {
/* 1067 */       parseEPRExtensions();
/*      */     }
/* 1069 */     return this.rootEprExtensions.values();
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseEPRExtensions() throws XMLStreamException {
/* 1074 */     this.rootEprExtensions = new HashMap<QName, EPRExtension>();
/*      */ 
/*      */     
/* 1077 */     StreamReaderBufferProcessor xsr = this.infoset.readAsXMLStreamReader();
/*      */ 
/*      */     
/* 1080 */     if (xsr.getEventType() == 7) {
/* 1081 */       xsr.nextTag();
/*      */     }
/* 1083 */     assert xsr.getEventType() == 1;
/*      */     
/* 1085 */     if (!xsr.getNamespaceURI().equals(this.version.nsUri)) {
/* 1086 */       throw new WebServiceException(AddressingMessages.WRONG_ADDRESSING_VERSION(this.version.nsUri, xsr.getNamespaceURI()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     XMLStreamBuffer mark;
/*      */ 
/*      */     
/* 1094 */     while ((mark = xsr.nextTagAndMark()) != null) {
/* 1095 */       String localName = xsr.getLocalName();
/* 1096 */       String ns = xsr.getNamespaceURI();
/* 1097 */       if (this.version.nsUri.equals(ns)) {
/*      */ 
/*      */         
/* 1100 */         XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr); continue;
/*      */       } 
/* 1102 */       QName qn = new QName(ns, localName);
/* 1103 */       this.rootEprExtensions.put(qn, new WSEPRExtension(mark, qn));
/* 1104 */       XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Metadata getMetaData() {
/* 1117 */     return new Metadata();
/*      */   }
/*      */   
/*      */   public class Metadata { @Nullable
/*      */     private QName serviceName;
/*      */     @Nullable
/*      */     private QName portName;
/*      */     @Nullable
/*      */     private QName portTypeName;
/*      */     @Nullable
/*      */     private Source wsdlSource;
/*      */     @Nullable
/*      */     private String wsdliLocation;
/*      */     
/*      */     @Nullable
/*      */     public QName getServiceName() {
/* 1133 */       return this.serviceName;
/*      */     } @Nullable
/*      */     public QName getPortName() {
/* 1136 */       return this.portName;
/*      */     } @Nullable
/*      */     public QName getPortTypeName() {
/* 1139 */       return this.portTypeName;
/*      */     } @Nullable
/*      */     public Source getWsdlSource() {
/* 1142 */       return this.wsdlSource;
/*      */     } @Nullable
/*      */     public String getWsdliLocation() {
/* 1145 */       return this.wsdliLocation;
/*      */     }
/*      */     
/*      */     private Metadata() {
/*      */       try {
/* 1150 */         parseMetaData();
/* 1151 */       } catch (XMLStreamException e) {
/* 1152 */         throw new WebServiceException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void parseMetaData() throws XMLStreamException {
/* 1160 */       StreamReaderBufferProcessor xsr = WSEndpointReference.this.infoset.readAsXMLStreamReader();
/*      */ 
/*      */       
/* 1163 */       if (xsr.getEventType() == 7) {
/* 1164 */         xsr.nextTag();
/*      */       }
/* 1166 */       assert xsr.getEventType() == 1;
/* 1167 */       String rootElement = xsr.getLocalName();
/* 1168 */       if (!xsr.getNamespaceURI().equals(WSEndpointReference.this.version.nsUri)) {
/* 1169 */         throw new WebServiceException(AddressingMessages.WRONG_ADDRESSING_VERSION(WSEndpointReference.this.version.nsUri, xsr.getNamespaceURI()));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1174 */       if (WSEndpointReference.this.version == AddressingVersion.W3C) {
/*      */         
/*      */         do {
/* 1177 */           if (xsr.getLocalName().equals(WSEndpointReference.this.version.eprType.wsdlMetadata.getLocalPart())) {
/* 1178 */             String wsdlLoc = xsr.getAttributeValue("http://www.w3.org/ns/wsdl-instance", "wsdlLocation");
/* 1179 */             if (wsdlLoc != null) {
/* 1180 */               this.wsdliLocation = wsdlLoc.trim();
/*      */             }
/*      */             XMLStreamBuffer mark;
/* 1183 */             while ((mark = xsr.nextTagAndMark()) != null) {
/* 1184 */               String localName = xsr.getLocalName();
/* 1185 */               String ns = xsr.getNamespaceURI();
/* 1186 */               if (localName.equals(WSEndpointReference.this.version.eprType.serviceName)) {
/* 1187 */                 String portStr = xsr.getAttributeValue(null, WSEndpointReference.this.version.eprType.portName);
/* 1188 */                 if (this.serviceName != null) {
/* 1189 */                   throw new RuntimeException("More than one " + WSEndpointReference.this.version.eprType.serviceName + " element in EPR Metadata");
/*      */                 }
/* 1191 */                 this.serviceName = getElementTextAsQName(xsr);
/* 1192 */                 if (this.serviceName != null && portStr != null)
/* 1193 */                   this.portName = new QName(this.serviceName.getNamespaceURI(), portStr);  continue;
/*      */               } 
/* 1195 */               if (localName.equals(WSEndpointReference.this.version.eprType.portTypeName)) {
/* 1196 */                 if (this.portTypeName != null) {
/* 1197 */                   throw new RuntimeException("More than one " + WSEndpointReference.this.version.eprType.portTypeName + " element in EPR Metadata");
/*      */                 }
/* 1199 */                 this.portTypeName = getElementTextAsQName(xsr); continue;
/* 1200 */               }  if (ns.equals("http://schemas.xmlsoap.org/wsdl/") && localName.equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/*      */                 
/* 1202 */                 this.wsdlSource = (Source)new XMLStreamBufferSource(mark); continue;
/*      */               } 
/* 1204 */               XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */             
/*      */             }
/*      */           
/*      */           }
/* 1209 */           else if (!xsr.getLocalName().equals(rootElement)) {
/* 1210 */             XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */           }
/*      */         
/* 1213 */         } while (XMLStreamReaderUtil.nextElementContent((XMLStreamReader)xsr) == 1);
/*      */         
/* 1215 */         if (this.wsdliLocation != null) {
/* 1216 */           String wsdlLocation = this.wsdliLocation.trim();
/* 1217 */           wsdlLocation = wsdlLocation.substring(this.wsdliLocation.lastIndexOf(" "));
/* 1218 */           this.wsdlSource = new StreamSource(wsdlLocation);
/*      */         } 
/* 1220 */       } else if (WSEndpointReference.this.version == AddressingVersion.MEMBER) {
/*      */         do {
/* 1222 */           String localName = xsr.getLocalName();
/* 1223 */           String ns = xsr.getNamespaceURI();
/*      */           
/* 1225 */           if (localName.equals(WSEndpointReference.this.version.eprType.wsdlMetadata.getLocalPart()) && ns.equals(WSEndpointReference.this.version.eprType.wsdlMetadata.getNamespaceURI())) {
/*      */             
/* 1227 */             while (xsr.nextTag() == 1) {
/*      */               XMLStreamBuffer mark;
/* 1229 */               while ((mark = xsr.nextTagAndMark()) != null) {
/* 1230 */                 localName = xsr.getLocalName();
/* 1231 */                 ns = xsr.getNamespaceURI();
/* 1232 */                 if (ns.equals("http://schemas.xmlsoap.org/wsdl/") && localName.equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart())) {
/*      */                   
/* 1234 */                   this.wsdlSource = (Source)new XMLStreamBufferSource(mark); continue;
/*      */                 } 
/* 1236 */                 XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */               }
/*      */             
/*      */             } 
/* 1240 */           } else if (localName.equals(WSEndpointReference.this.version.eprType.serviceName)) {
/* 1241 */             String portStr = xsr.getAttributeValue(null, WSEndpointReference.this.version.eprType.portName);
/* 1242 */             this.serviceName = getElementTextAsQName(xsr);
/* 1243 */             if (this.serviceName != null && portStr != null) {
/* 1244 */               this.portName = new QName(this.serviceName.getNamespaceURI(), portStr);
/*      */             }
/* 1246 */           } else if (localName.equals(WSEndpointReference.this.version.eprType.portTypeName)) {
/* 1247 */             this.portTypeName = getElementTextAsQName(xsr);
/*      */           
/*      */           }
/* 1250 */           else if (!xsr.getLocalName().equals(rootElement)) {
/* 1251 */             XMLStreamReaderUtil.skipElement((XMLStreamReader)xsr);
/*      */           }
/*      */         
/* 1254 */         } while (XMLStreamReaderUtil.nextElementContent((XMLStreamReader)xsr) == 1);
/*      */       } 
/*      */     }
/*      */     
/*      */     private QName getElementTextAsQName(StreamReaderBufferProcessor xsr) throws XMLStreamException {
/* 1259 */       String text = xsr.getElementText().trim();
/* 1260 */       String prefix = XmlUtil.getPrefix(text);
/* 1261 */       String name = XmlUtil.getLocalPart(text);
/* 1262 */       if (name != null) {
/* 1263 */         if (prefix != null) {
/* 1264 */           String ns = xsr.getNamespaceURI(prefix);
/* 1265 */           if (ns != null) {
/* 1266 */             return new QName(ns, name, prefix);
/*      */           }
/*      */         } else {
/* 1269 */           return new QName(null, name);
/*      */         } 
/*      */       }
/* 1272 */       return null;
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\WSEndpointReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */