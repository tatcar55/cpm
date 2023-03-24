/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
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
/*     */ public class SAMLToken
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  62 */   private Assertion samlToken = null;
/*  63 */   private JAXBContext jxbContext = null;
/*  64 */   private SOAPVersion soapVersion = null;
/*     */   
/*     */   public SAMLToken(Assertion assertion, JAXBContext jxbContext, SOAPVersion soapVersion) {
/*  67 */     this.samlToken = assertion;
/*  68 */     this.jxbContext = jxbContext;
/*  69 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/*  74 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  78 */     return this.samlToken.getAssertionID();
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  82 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/*  86 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/*  90 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*  94 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/*     */     try {
/* 103 */       Marshaller marshaller = this.jxbContext.createMarshaller();
/* 104 */       if (SOAPVersion.SOAP_11 == this.soapVersion) {
/* 105 */         marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", JAXBUtil.prefixMapper11);
/*     */       } else {
/* 107 */         marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", JAXBUtil.prefixMapper12);
/*     */       } 
/* 109 */       marshaller.setProperty("jaxb.fragment", Boolean.valueOf(true));
/* 110 */       marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.valueOf(false));
/* 111 */       marshaller.marshal(this.samlToken, streamWriter);
/*     */     }
/* 113 */     catch (PropertyException pe) {
/*     */       
/* 115 */       throw new XMLStreamException("Error occurred while setting security marshaller properties", pe);
/* 116 */     } catch (JAXBException je) {
/*     */       
/* 118 */       throw new XMLStreamException("Error occurred while marshalling SAMLAssertion", je);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {}
/*     */   
/*     */   public void writeTo(OutputStream os) {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SAMLToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */