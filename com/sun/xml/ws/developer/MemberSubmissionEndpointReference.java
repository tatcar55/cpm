/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "EndpointReference", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */ @XmlType(name = "EndpointReferenceType", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */ public final class MemberSubmissionEndpointReference
/*     */   extends EndpointReference
/*     */   implements MemberSubmissionAddressingConstants
/*     */ {
/*  82 */   private static final JAXBContext msjc = getMSJaxbContext();
/*     */ 
/*     */   
/*     */   @XmlElement(name = "Address", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public Address addr;
/*     */   
/*     */   @XmlElement(name = "ReferenceProperties", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public Elements referenceProperties;
/*     */   
/*     */   @XmlElement(name = "ReferenceParameters", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public Elements referenceParameters;
/*     */   
/*     */   @XmlElement(name = "PortType", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public AttributedQName portTypeName;
/*     */ 
/*     */   
/*     */   public MemberSubmissionEndpointReference(@NotNull Source source) {
/*  99 */     if (source == null) {
/* 100 */       throw new WebServiceException("Source parameter can not be null on constructor");
/*     */     }
/*     */     
/*     */     try {
/* 104 */       Unmarshaller unmarshaller = msjc.createUnmarshaller();
/* 105 */       MemberSubmissionEndpointReference epr = unmarshaller.<MemberSubmissionEndpointReference>unmarshal(source, MemberSubmissionEndpointReference.class).getValue();
/*     */       
/* 107 */       this.addr = epr.addr;
/* 108 */       this.referenceProperties = epr.referenceProperties;
/* 109 */       this.referenceParameters = epr.referenceParameters;
/* 110 */       this.portTypeName = epr.portTypeName;
/* 111 */       this.serviceName = epr.serviceName;
/* 112 */       this.attributes = epr.attributes;
/* 113 */       this.elements = epr.elements;
/* 114 */     } catch (JAXBException e) {
/* 115 */       throw new WebServiceException("Error unmarshalling MemberSubmissionEndpointReference ", e);
/* 116 */     } catch (ClassCastException e) {
/* 117 */       throw new WebServiceException("Source did not contain MemberSubmissionEndpointReference", e);
/*     */     } 
/*     */   } @XmlElement(name = "ServiceName", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public ServiceNameType serviceName; @XmlAnyAttribute
/*     */   public Map<QName, String> attributes; @XmlAnyElement
/*     */   public List<Element> elements; protected static final String MSNS = "http://schemas.xmlsoap.org/ws/2004/08/addressing"; public MemberSubmissionEndpointReference() {} public void writeTo(Result result) {
/*     */     try {
/* 124 */       Marshaller marshaller = msjc.createMarshaller();
/*     */       
/* 126 */       marshaller.marshal(this, result);
/* 127 */     } catch (JAXBException e) {
/* 128 */       throw new WebServiceException("Error marshalling W3CEndpointReference. ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source toWSDLSource() {
/* 138 */     Element wsdlElement = null;
/*     */     
/* 140 */     for (Element elem : this.elements) {
/* 141 */       if (elem.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/") && elem.getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart()))
/*     */       {
/* 143 */         wsdlElement = elem;
/*     */       }
/*     */     } 
/*     */     
/* 147 */     return new DOMSource(wsdlElement);
/*     */   }
/*     */ 
/*     */   
/*     */   private static JAXBContext getMSJaxbContext() {
/*     */     try {
/* 153 */       return JAXBContext.newInstance(new Class[] { MemberSubmissionEndpointReference.class });
/* 154 */     } catch (JAXBException e) {
/* 155 */       throw new WebServiceException("Error creating JAXBContext for MemberSubmissionEndpointReference. ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @XmlType(name = "address", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public static class Address {
/*     */     @XmlValue
/*     */     public String uri;
/*     */     @XmlAnyAttribute
/*     */     public Map<QName, String> attributes;
/*     */   }
/*     */   
/*     */   @XmlType(name = "elements", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
/*     */   public static class Elements {
/*     */     @XmlAnyElement
/*     */     public List<Element> elements;
/*     */   }
/*     */   
/*     */   public static class AttributedQName {
/*     */     @XmlValue
/*     */     public QName name;
/*     */     @XmlAnyAttribute
/*     */     public Map<QName, String> attributes;
/*     */   }
/*     */   
/*     */   public static class ServiceNameType extends AttributedQName {
/*     */     @XmlAttribute(name = "PortName")
/*     */     public String portName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\MemberSubmissionEndpointReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */