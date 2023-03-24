/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperBridge<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   BindingContext parent;
/*     */   TypeInfo typeInfo;
/*     */   static final String WrapperPrefix = "w";
/*     */   static final String WrapperPrefixColon = "w:";
/*     */   
/*     */   public WrapperBridge(BindingContext p, TypeInfo ti) {
/*  83 */     this.parent = p;
/*  84 */     this.typeInfo = ti;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  89 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/*  94 */     return this.typeInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/*  99 */     WrapperComposite w = (WrapperComposite)object;
/* 100 */     Attributes att = new Attributes() {
/* 101 */         public int getLength() { return 0; }
/* 102 */         public String getURI(int index) { return null; }
/* 103 */         public String getLocalName(int index) { return null; }
/* 104 */         public String getQName(int index) { return null; }
/* 105 */         public String getType(int index) { return null; }
/* 106 */         public String getValue(int index) { return null; }
/* 107 */         public int getIndex(String uri, String localName) { return 0; }
/* 108 */         public int getIndex(String qName) { return 0; }
/* 109 */         public String getType(String uri, String localName) { return null; }
/* 110 */         public String getType(String qName) { return null; }
/* 111 */         public String getValue(String uri, String localName) { return null; }
/* 112 */         public String getValue(String qName) { return null; }
/*     */       };
/*     */     try {
/* 115 */       contentHandler.startPrefixMapping("w", this.typeInfo.tagName.getNamespaceURI());
/* 116 */       contentHandler.startElement(this.typeInfo.tagName.getNamespaceURI(), this.typeInfo.tagName.getLocalPart(), "w:" + this.typeInfo.tagName.getLocalPart(), att);
/* 117 */     } catch (SAXException e) {
/* 118 */       throw new JAXBException(e);
/*     */     } 
/* 120 */     if (w.bridges != null) for (int i = 0; i < w.bridges.length; i++) {
/* 121 */         if (w.bridges[i] instanceof RepeatedElementBridge) {
/* 122 */           RepeatedElementBridge rbridge = (RepeatedElementBridge)w.bridges[i];
/* 123 */           for (Iterator itr = rbridge.collectionHandler().iterator(w.values[i]); itr.hasNext();) {
/* 124 */             rbridge.marshal(itr.next(), contentHandler, am);
/*     */           }
/*     */         } else {
/* 127 */           w.bridges[i].marshal(w.values[i], contentHandler, am);
/*     */         } 
/*     */       }  
/*     */     try {
/* 131 */       contentHandler.endElement(this.typeInfo.tagName.getNamespaceURI(), this.typeInfo.tagName.getLocalPart(), null);
/* 132 */       contentHandler.endPrefixMapping("w");
/* 133 */     } catch (SAXException e) {
/* 134 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/* 141 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 153 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 159 */     WrapperComposite w = (WrapperComposite)object;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 165 */       String prefix = output.getPrefix(this.typeInfo.tagName.getNamespaceURI());
/* 166 */       if (prefix == null) prefix = "w"; 
/* 167 */       output.writeStartElement(prefix, this.typeInfo.tagName.getLocalPart(), this.typeInfo.tagName.getNamespaceURI());
/* 168 */       output.writeNamespace(prefix, this.typeInfo.tagName.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 175 */     catch (XMLStreamException e) {
/* 176 */       e.printStackTrace();
/* 177 */       throw new DatabindingException(e);
/*     */     } 
/* 179 */     if (w.bridges != null) for (int i = 0; i < w.bridges.length; i++) {
/* 180 */         if (w.bridges[i] instanceof RepeatedElementBridge) {
/* 181 */           RepeatedElementBridge rbridge = (RepeatedElementBridge)w.bridges[i];
/* 182 */           for (Iterator itr = rbridge.collectionHandler().iterator(w.values[i]); itr.hasNext();) {
/* 183 */             rbridge.marshal(itr.next(), output, am);
/*     */           }
/*     */         } else {
/* 186 */           w.bridges[i].marshal(w.values[i], output, am);
/*     */         } 
/*     */       }  
/*     */     try {
/* 190 */       output.writeEndElement();
/* 191 */     } catch (XMLStreamException e) {
/* 192 */       throw new DatabindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(InputStream in) throws JAXBException {
/* 199 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 206 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 213 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 220 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 226 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\WrapperBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */