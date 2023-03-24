/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderFactory
/*     */ {
/*     */   static final XMLInputFactory xmlInputFactory;
/*  69 */   static final ThreadLocal fiStreamReader = new ThreadLocal();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   static final ThreadLocal<XMLStreamReader> xmlStreamReader = new ThreadLocal<XMLStreamReader>();
/*     */ 
/*     */   
/*     */   static {
/*  78 */     xmlInputFactory = XMLInputFactory.newInstance();
/*  79 */     xmlInputFactory.setProperty("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
/*     */ 
/*     */     
/*     */     try {
/*  83 */       xmlInputFactory.setProperty("reuse-instance", Boolean.FALSE);
/*  84 */     } catch (IllegalArgumentException e) {}
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
/*     */   public static XMLStreamReader createFreshXMLStreamReader(InputSource source, boolean rejectDTDs) {
/*     */     try {
/* 100 */       synchronized (xmlInputFactory)
/*     */       {
/* 102 */         if (source.getCharacterStream() != null) {
/* 103 */           return xmlInputFactory.createXMLStreamReader(source.getSystemId(), source.getCharacterStream());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 108 */         if (source.getByteStream() != null) {
/* 109 */           return xmlInputFactory.createXMLStreamReader(source.getSystemId(), source.getByteStream());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 114 */         return xmlInputFactory.createXMLStreamReader(source.getSystemId(), (new URL(source.getSystemId())).openStream());
/*     */       }
/*     */     
/* 117 */     } catch (Exception e) {
/* 118 */       throw new WebServiceException("stax.cantCreate", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamReader createFreshXMLStreamReader(String systemId, InputStream stream) {
/*     */     try {
/* 127 */       synchronized (xmlInputFactory)
/*     */       {
/* 129 */         return xmlInputFactory.createXMLStreamReader(systemId, stream);
/*     */       }
/*     */     
/* 132 */     } catch (Exception e) {
/* 133 */       throw new WebServiceException("stax.cantCreate", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamReader createFreshXMLStreamReader(String systemId, Reader reader) {
/*     */     try {
/* 142 */       synchronized (xmlInputFactory)
/*     */       {
/* 144 */         return xmlInputFactory.createXMLStreamReader(systemId, reader);
/*     */       }
/*     */     
/* 147 */     } catch (Exception e) {
/* 148 */       throw new WebServiceException("stax.cantCreate", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static XMLStreamReader createFilteredXMLStreamReader(XMLStreamReader reader, StreamFilter filter) {
/*     */     try {
/* 155 */       synchronized (xmlInputFactory) {
/*     */         
/* 157 */         return xmlInputFactory.createFilteredReader(reader, filter);
/*     */       } 
/* 159 */     } catch (Exception e) {
/* 160 */       throw new WebServiceException("stax.cantCreate", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\XMLStreamReaderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */