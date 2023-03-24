/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.db.glassfish.BridgeWrapper;
/*     */ import com.sun.xml.ws.message.DOMHeader;
/*     */ import com.sun.xml.ws.message.StringHeader;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBHeader;
/*     */ import com.sun.xml.ws.message.saaj.SAAJHeader;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader11;
/*     */ import com.sun.xml.ws.message.stream.StreamHeader12;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Headers
/*     */ {
/*     */   public static Header create(SOAPVersion soapVersion, Marshaller m, Object o) {
/*  98 */     return (Header)new JAXBHeader(BindingContextFactory.getBindingContext(m), o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(JAXBContext context, Object o) {
/* 105 */     return (Header)new JAXBHeader(BindingContextFactory.create(context), o);
/*     */   }
/*     */   
/*     */   public static Header create(BindingContext context, Object o) {
/* 109 */     return (Header)new JAXBHeader(context, o);
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
/*     */   public static Header create(SOAPVersion soapVersion, Marshaller m, QName tagName, Object o) {
/* 124 */     return create(soapVersion, m, new JAXBElement(tagName, o.getClass(), o));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(Bridge bridge, Object jaxbObject) {
/* 132 */     return (Header)new JAXBHeader((XMLBridge)new BridgeWrapper(null, bridge), jaxbObject);
/*     */   }
/*     */   
/*     */   public static Header create(XMLBridge bridge, Object jaxbObject) {
/* 136 */     return (Header)new JAXBHeader(bridge, jaxbObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(SOAPHeaderElement header) {
/* 143 */     return (Header)new SAAJHeader(header);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(Element node) {
/* 150 */     return (Header)new DOMHeader(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(SOAPVersion soapVersion, Element node) {
/* 158 */     return create(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header create(SOAPVersion soapVersion, XMLStreamReader reader) throws XMLStreamException {
/* 169 */     switch (soapVersion) {
/*     */       case SOAP_11:
/* 171 */         return (Header)new StreamHeader11(reader);
/*     */       case SOAP_12:
/* 173 */         return (Header)new StreamHeader12(reader);
/*     */     } 
/* 175 */     throw new AssertionError();
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
/*     */   public static Header create(QName name, String value) {
/* 187 */     return (Header)new StringHeader(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Header createMustUnderstand(@NotNull SOAPVersion soapVersion, @NotNull QName name, @NotNull String value) {
/* 198 */     return (Header)new StringHeader(name, value, soapVersion, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\Headers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */