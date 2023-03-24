/*     */ package javax.xml.ws.wsaddressing;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.XmlValue;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "EndpointReference", namespace = "http://www.w3.org/2005/08/addressing")
/*     */ @XmlType(name = "EndpointReferenceType", namespace = "http://www.w3.org/2005/08/addressing")
/*     */ public final class W3CEndpointReference
/*     */   extends EndpointReference
/*     */ {
/*  87 */   private static final JAXBContext w3cjc = getW3CJaxbContext();
/*     */   
/*     */   @XmlElement(name = "Address", namespace = "http://www.w3.org/2005/08/addressing")
/*     */   private Address address;
/*     */   
/*     */   @XmlElement(name = "ReferenceParameters", namespace = "http://www.w3.org/2005/08/addressing")
/*     */   private Elements referenceParameters;
/*     */   
/*     */   @XmlElement(name = "Metadata", namespace = "http://www.w3.org/2005/08/addressing")
/*     */   private Elements metadata;
/*     */   @XmlAnyAttribute
/*     */   Map<QName, String> attributes;
/*     */   @XmlAnyElement
/*     */   List<Element> elements;
/*     */   protected static final String NS = "http://www.w3.org/2005/08/addressing";
/*     */   
/*     */   protected W3CEndpointReference() {}
/*     */   
/*     */   public W3CEndpointReference(Source source) {
/*     */     try {
/* 107 */       W3CEndpointReference epr = w3cjc.createUnmarshaller().<W3CEndpointReference>unmarshal(source, W3CEndpointReference.class).getValue();
/* 108 */       this.address = epr.address;
/* 109 */       this.metadata = epr.metadata;
/* 110 */       this.referenceParameters = epr.referenceParameters;
/* 111 */       this.elements = epr.elements;
/* 112 */       this.attributes = epr.attributes;
/* 113 */     } catch (JAXBException e) {
/* 114 */       throw new WebServiceException("Error unmarshalling W3CEndpointReference ", e);
/* 115 */     } catch (ClassCastException e) {
/* 116 */       throw new WebServiceException("Source did not contain W3CEndpointReference", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Result result) {
/*     */     try {
/* 125 */       Marshaller marshaller = w3cjc.createMarshaller();
/* 126 */       marshaller.marshal(this, result);
/* 127 */     } catch (JAXBException e) {
/* 128 */       throw new WebServiceException("Error marshalling W3CEndpointReference. ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static JAXBContext getW3CJaxbContext() {
/*     */     try {
/* 134 */       return JAXBContext.newInstance(new Class[] { W3CEndpointReference.class });
/* 135 */     } catch (JAXBException e) {
/* 136 */       throw new WebServiceException("Error creating JAXBContext for W3CEndpointReference. ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @XmlType(name = "address", namespace = "http://www.w3.org/2005/08/addressing")
/*     */   private static class Address {
/*     */     @XmlValue
/*     */     String uri;
/*     */     @XmlAnyAttribute
/*     */     Map<QName, String> attributes;
/*     */   }
/*     */   
/*     */   @XmlType(name = "elements", namespace = "http://www.w3.org/2005/08/addressing")
/*     */   private static class Elements {
/*     */     @XmlAnyElement
/*     */     List<Element> elements;
/*     */     @XmlAnyAttribute
/*     */     Map<QName, String> attributes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\wsaddressing\W3CEndpointReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */