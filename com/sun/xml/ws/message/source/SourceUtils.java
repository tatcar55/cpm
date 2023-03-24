/*     */ package com.sun.xml.ws.message.source;
/*     */ 
/*     */ import com.sun.xml.ws.message.RootElementSniffer;
/*     */ import com.sun.xml.ws.streaming.SourceReaderFactory;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SourceUtils
/*     */ {
/*     */   int srcType;
/*     */   private static final int domSource = 1;
/*     */   private static final int streamSource = 2;
/*     */   private static final int saxSource = 4;
/*     */   
/*     */   public SourceUtils(Source src) {
/*  77 */     if (src instanceof javax.xml.transform.stream.StreamSource) {
/*  78 */       this.srcType = 2;
/*  79 */     } else if (src instanceof DOMSource) {
/*  80 */       this.srcType = 1;
/*  81 */     } else if (src instanceof SAXSource) {
/*  82 */       this.srcType = 4;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDOMSource() {
/*  87 */     return ((this.srcType & 0x1) == 1);
/*     */   }
/*     */   
/*     */   public boolean isStreamSource() {
/*  91 */     return ((this.srcType & 0x2) == 2);
/*     */   }
/*     */   
/*     */   public boolean isSaxSource() {
/*  95 */     return ((this.srcType & 0x4) == 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName sniff(Source src) {
/* 105 */     return sniff(src, new RootElementSniffer());
/*     */   }
/*     */   
/*     */   public QName sniff(Source src, RootElementSniffer sniffer) {
/* 109 */     String localName = null;
/* 110 */     String namespaceUri = null;
/*     */     
/* 112 */     if (isDOMSource()) {
/* 113 */       DOMSource domSrc = (DOMSource)src;
/* 114 */       Node n = domSrc.getNode();
/* 115 */       if (n.getNodeType() == 9) {
/* 116 */         n = ((Document)n).getDocumentElement();
/*     */       }
/* 118 */       localName = n.getLocalName();
/* 119 */       namespaceUri = n.getNamespaceURI();
/* 120 */     } else if (isSaxSource()) {
/* 121 */       SAXSource saxSrc = (SAXSource)src;
/* 122 */       SAXResult saxResult = new SAXResult((ContentHandler)sniffer);
/*     */       try {
/* 124 */         Transformer tr = XmlUtil.newTransformer();
/* 125 */         tr.transform(saxSrc, saxResult);
/* 126 */       } catch (TransformerConfigurationException e) {
/* 127 */         throw new WebServiceException(e);
/* 128 */       } catch (TransformerException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 135 */         localName = sniffer.getLocalName();
/* 136 */         namespaceUri = sniffer.getNsUri();
/*     */       } 
/*     */     } 
/* 139 */     return new QName(namespaceUri, localName);
/*     */   }
/*     */   
/*     */   public static void serializeSource(Source src, XMLStreamWriter writer) throws XMLStreamException {
/* 143 */     XMLStreamReader reader = SourceReaderFactory.createSourceReader(src, true);
/*     */     while (true) {
/*     */       String uri, prefix, localName;
/* 146 */       int n, i, state = reader.next();
/* 147 */       switch (state) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 153 */           uri = reader.getNamespaceURI();
/* 154 */           prefix = reader.getPrefix();
/* 155 */           localName = reader.getLocalName();
/*     */           
/* 157 */           if (prefix == null) {
/* 158 */             if (uri == null) {
/* 159 */               writer.writeStartElement(localName);
/*     */             } else {
/* 161 */               writer.writeStartElement(uri, localName);
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 166 */           else if (prefix.length() > 0) {
/*     */ 
/*     */ 
/*     */             
/* 170 */             String writerURI = null;
/* 171 */             if (writer.getNamespaceContext() != null) {
/* 172 */               writerURI = writer.getNamespaceContext().getNamespaceURI(prefix);
/*     */             }
/* 174 */             String writerPrefix = writer.getPrefix(uri);
/* 175 */             if (declarePrefix(prefix, uri, writerPrefix, writerURI)) {
/* 176 */               writer.writeStartElement(prefix, localName, uri);
/* 177 */               writer.setPrefix(prefix, (uri != null) ? uri : "");
/* 178 */               writer.writeNamespace(prefix, uri);
/*     */             } else {
/* 180 */               writer.writeStartElement(prefix, localName, uri);
/*     */             } 
/*     */           } else {
/* 183 */             writer.writeStartElement(prefix, localName, uri);
/*     */           } 
/*     */ 
/*     */           
/* 187 */           n = reader.getNamespaceCount();
/*     */           
/* 189 */           for (i = 0; i < n; i++) {
/* 190 */             String nsPrefix = reader.getNamespacePrefix(i);
/* 191 */             if (nsPrefix == null) {
/* 192 */               nsPrefix = "";
/*     */             }
/*     */             
/* 195 */             String writerURI = null;
/* 196 */             if (writer.getNamespaceContext() != null) {
/* 197 */               writerURI = writer.getNamespaceContext().getNamespaceURI(nsPrefix);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 202 */             String readerURI = reader.getNamespaceURI(i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 211 */             if (writerURI == null || nsPrefix.length() == 0 || prefix.length() == 0 || (!nsPrefix.equals(prefix) && !writerURI.equals(readerURI))) {
/*     */               
/* 213 */               writer.setPrefix(nsPrefix, (readerURI != null) ? readerURI : "");
/* 214 */               writer.writeNamespace(nsPrefix, (readerURI != null) ? readerURI : "");
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 219 */           n = reader.getAttributeCount();
/* 220 */           for (i = 0; i < n; i++) {
/* 221 */             String attrPrefix = reader.getAttributePrefix(i);
/* 222 */             String attrURI = reader.getAttributeNamespace(i);
/*     */             
/* 224 */             writer.writeAttribute((attrPrefix != null) ? attrPrefix : "", (attrURI != null) ? attrURI : "", reader.getAttributeLocalName(i), reader.getAttributeValue(i));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 229 */             setUndeclaredPrefix(attrPrefix, attrURI, writer);
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 233 */           writer.writeEndElement();
/*     */           break;
/*     */         case 4:
/* 236 */           writer.writeCharacters(reader.getText());
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 241 */       if (state == 8) {
/* 242 */         reader.close();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setUndeclaredPrefix(String prefix, String readerURI, XMLStreamWriter writer) throws XMLStreamException {
/* 252 */     String writerURI = null;
/* 253 */     if (writer.getNamespaceContext() != null) {
/* 254 */       writerURI = writer.getNamespaceContext().getNamespaceURI(prefix);
/*     */     }
/*     */     
/* 257 */     if (writerURI == null) {
/* 258 */       writer.setPrefix(prefix, (readerURI != null) ? readerURI : "");
/* 259 */       writer.writeNamespace(prefix, (readerURI != null) ? readerURI : "");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean declarePrefix(String rPrefix, String rUri, String wPrefix, String wUri) {
/* 271 */     if (wUri == null || (wPrefix != null && !rPrefix.equals(wPrefix)) || (rUri != null && !wUri.equals(rUri)))
/*     */     {
/* 273 */       return true;
/*     */     }
/* 275 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\source\SourceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */