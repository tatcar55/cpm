/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.streaming.TidyXMLStreamReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SDDocumentSource
/*     */ {
/*     */   public abstract XMLStreamReader read(XMLInputFactory paramXMLInputFactory) throws IOException, XMLStreamException;
/*     */   
/*     */   public abstract XMLStreamReader read() throws IOException, XMLStreamException;
/*     */   
/*     */   public abstract URL getSystemId();
/*     */   
/*     */   public static SDDocumentSource create(final URL url) {
/* 108 */     return new SDDocumentSource() {
/* 109 */         private final URL systemId = url;
/*     */         
/*     */         public XMLStreamReader read(XMLInputFactory xif) throws IOException, XMLStreamException {
/* 112 */           InputStream is = url.openStream();
/* 113 */           return (XMLStreamReader)new TidyXMLStreamReader(xif.createXMLStreamReader(this.systemId.toExternalForm(), is), is);
/*     */         }
/*     */ 
/*     */         
/*     */         public XMLStreamReader read() throws IOException, XMLStreamException {
/* 118 */           InputStream is = url.openStream();
/* 119 */           return (XMLStreamReader)new TidyXMLStreamReader(XMLStreamReaderFactory.create(this.systemId.toExternalForm(), is, false), is);
/*     */         }
/*     */ 
/*     */         
/*     */         public URL getSystemId() {
/* 124 */           return this.systemId;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SDDocumentSource create(final URL systemId, final XMLStreamBuffer xsb) {
/* 133 */     return new SDDocumentSource() {
/*     */         public XMLStreamReader read(XMLInputFactory xif) throws XMLStreamException {
/* 135 */           return (XMLStreamReader)xsb.readAsXMLStreamReader();
/*     */         }
/*     */         
/*     */         public XMLStreamReader read() throws XMLStreamException {
/* 139 */           return (XMLStreamReader)xsb.readAsXMLStreamReader();
/*     */         }
/*     */         
/*     */         public URL getSystemId() {
/* 143 */           return systemId;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\SDDocumentSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */