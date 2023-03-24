/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.xml.security.core.xenc.ReferenceList;
/*     */ import com.sun.xml.security.core.xenc.ReferenceType;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.util.DOMUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class GSHeaderElement
/*     */   implements SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*  67 */   JAXBElement element = null;
/*  68 */   Object obj = null;
/*  69 */   private String id = "";
/*  70 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  71 */   private Element domElement = null;
/*     */   
/*     */   public GSHeaderElement(JAXBElement el, SOAPVersion sv) {
/*  74 */     this.element = el;
/*  75 */     this.soapVersion = sv;
/*     */   }
/*     */   private XMLStreamBuffer buffer;
/*     */   public GSHeaderElement(Object obj, SOAPVersion sv) {
/*  79 */     this.obj = obj;
/*  80 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public GSHeaderElement(Element obj, SOAPVersion sv) {
/*  84 */     this.domElement = obj;
/*  85 */     this.soapVersion = sv;
/*  86 */     if (this.domElement.getLocalName().equals("Assertion")) {
/*  87 */       this.id = this.domElement.getAttribute("AssertionID");
/*  88 */       if (this.id == null || this.id.equals(""))
/*  89 */         this.id = this.domElement.getAttribute("ID"); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public GSHeaderElement(Element obj) {
/*  94 */     this.domElement = obj;
/*  95 */     if (this.domElement.getLocalName() == "Assertion") {
/*  96 */       this.id = this.domElement.getAttribute("AssertionID");
/*  97 */       if (this.id == null || this.id.equals(""))
/*  98 */         this.id = this.domElement.getAttribute("ID"); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public GSHeaderElement(XMLStreamBuffer buffer) {
/* 103 */     this.buffer = buffer;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 107 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 111 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 116 */     if (this.element != null) {
/* 117 */       return this.element.getName().getNamespaceURI();
/*     */     }
/* 119 */     if (this.domElement != null) {
/* 120 */       return this.domElement.getNamespaceURI();
/*     */     }
/*     */     
/* 123 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 128 */     if (this.element != null) {
/* 129 */       return this.element.getName().getLocalPart();
/*     */     }
/*     */     
/* 132 */     if (this.domElement != null) {
/* 133 */       return this.domElement.getLocalName();
/*     */     }
/*     */     
/* 136 */     if (this.obj != null && 
/* 137 */       this.obj instanceof ReferenceList) {
/* 138 */       return "ReferenceList";
/*     */     }
/*     */     
/* 141 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 147 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/*     */     try {
/* 154 */       Marshaller writer = getMarshaller();
/* 155 */       if (this.buffer != null) {
/* 156 */         this.buffer.writeToXMLStreamWriter(streamWriter);
/* 157 */       } else if (this.element != null) {
/* 158 */         writer.marshal(this.element, streamWriter);
/* 159 */       } else if (this.domElement != null) {
/* 160 */         DOMUtil.serializeNode(this.domElement, streamWriter);
/*     */       } else {
/* 162 */         writer.marshal(this.obj, streamWriter);
/*     */       } 
/* 164 */     } catch (JAXBException ex) {
/* 165 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 170 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] canonicalize(String algorithm, List<AttributeNS> namespaceDecls) {
/* 175 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isCanonicalized() {
/* 179 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/*     */     try {
/* 184 */       Marshaller writer = getMarshaller();
/* 185 */       if (this.element != null) {
/* 186 */         writer.marshal(this.element, os);
/*     */       } else {
/* 188 */         writer.marshal(this.obj, os);
/*     */       } 
/* 190 */     } catch (JAXBException ex) {
/* 191 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 196 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 200 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 204 */     String tmpId = "#" + id;
/* 205 */     if (this.element != null && 
/* 206 */       this.element.getName().getLocalPart() == "ReferenceList") {
/* 207 */       ReferenceList list = this.element.getValue();
/* 208 */       List<JAXBElement<ReferenceType>> listElems = list.getDataReferenceOrKeyReference();
/* 209 */       for (int i = 0; i < listElems.size(); i++) {
/* 210 */         JAXBElement<ReferenceType> ref = listElems.get(i);
/* 211 */         ReferenceType rt = ref.getValue();
/* 212 */         if (rt.getURI().equals(tmpId)) {
/* 213 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     if (this.obj != null && 
/* 219 */       this.obj instanceof ReferenceList) {
/* 220 */       ReferenceList rl = (ReferenceList)this.obj;
/* 221 */       List<JAXBElement<ReferenceType>> listElems = rl.getDataReferenceOrKeyReference();
/* 222 */       for (int i = 0; i < listElems.size(); i++) {
/* 223 */         JAXBElement<ReferenceType> ref = listElems.get(i);
/* 224 */         ReferenceType rt = ref.getValue();
/* 225 */         if (rt.getURI().equals(tmpId)) {
/* 226 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 235 */     writeTo(streamWriter);
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 239 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\GSHeaderElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */