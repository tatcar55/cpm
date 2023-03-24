/*     */ package com.sun.xml.ws.streaming;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.encoding.HasEncoding;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamWriterUtil
/*     */ {
/*     */   @Nullable
/*     */   public static OutputStream getOutputStream(XMLStreamWriter writer) throws XMLStreamException {
/*  73 */     Object obj = null;
/*     */ 
/*     */     
/*  76 */     if (writer instanceof Map) {
/*  77 */       obj = ((Map)writer).get("sjsxp-outputstream");
/*     */     }
/*     */ 
/*     */     
/*  81 */     if (obj == null) {
/*     */       try {
/*  83 */         obj = writer.getProperty("com.ctc.wstx.outputUnderlyingStream");
/*  84 */       } catch (Exception ie) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (obj == null) {
/*     */       try {
/*  93 */         obj = writer.getProperty("http://java.sun.com/xml/stream/properties/outputstream");
/*  94 */       } catch (Exception ie) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (obj != null) {
/* 102 */       writer.writeCharacters("");
/* 103 */       writer.flush();
/* 104 */       return (OutputStream)obj;
/*     */     } 
/* 106 */     return null;
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
/*     */   @Nullable
/*     */   public static String getEncoding(XMLStreamWriter writer) {
/* 122 */     return (writer instanceof HasEncoding) ? ((HasEncoding)writer).getEncoding() : null;
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
/*     */   public static String encodeQName(XMLStreamWriter writer, QName qname, PrefixFactory prefixFactory) {
/*     */     try {
/* 135 */       String namespaceURI = qname.getNamespaceURI();
/* 136 */       String localPart = qname.getLocalPart();
/*     */       
/* 138 */       if (namespaceURI == null || namespaceURI.equals("")) {
/* 139 */         return localPart;
/*     */       }
/*     */       
/* 142 */       String prefix = writer.getPrefix(namespaceURI);
/* 143 */       if (prefix == null) {
/* 144 */         prefix = prefixFactory.getPrefix(namespaceURI);
/* 145 */         writer.writeNamespace(prefix, namespaceURI);
/*     */       } 
/* 147 */       return prefix + ":" + localPart;
/*     */     
/*     */     }
/* 150 */     catch (XMLStreamException e) {
/* 151 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\XMLStreamWriterUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */