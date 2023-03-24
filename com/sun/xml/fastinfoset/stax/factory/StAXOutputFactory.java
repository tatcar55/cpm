/*     */ package com.sun.xml.fastinfoset.stax.factory;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.fastinfoset.stax.StAXManager;
/*     */ import com.sun.xml.fastinfoset.stax.events.StAXEventWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXOutputFactory
/*     */   extends XMLOutputFactory
/*     */ {
/*  38 */   private StAXManager _manager = null;
/*     */ 
/*     */   
/*     */   public StAXOutputFactory() {
/*  42 */     this._manager = new StAXManager(2);
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
/*  46 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(result));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Writer writer) throws XMLStreamException {
/*  50 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(writer));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream outputStream) throws XMLStreamException {
/*  54 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(outputStream));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream outputStream, String encoding) throws XMLStreamException {
/*  58 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(outputStream, encoding));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
/*  62 */     if (result instanceof StreamResult) {
/*  63 */       StreamResult streamResult = (StreamResult)result;
/*  64 */       if (streamResult.getWriter() != null)
/*  65 */         return createXMLStreamWriter(streamResult.getWriter()); 
/*  66 */       if (streamResult.getOutputStream() != null)
/*  67 */         return createXMLStreamWriter(streamResult.getOutputStream()); 
/*  68 */       if (streamResult.getSystemId() != null) {
/*     */         try {
/*  70 */           FileWriter writer = new FileWriter(new File(streamResult.getSystemId()));
/*  71 */           return createXMLStreamWriter(writer);
/*  72 */         } catch (IOException ie) {
/*  73 */           throw new XMLStreamException(ie);
/*     */         } 
/*     */       }
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*  80 */         FileWriter writer = new FileWriter(new File(result.getSystemId()));
/*  81 */         return createXMLStreamWriter(writer);
/*  82 */       } catch (IOException ie) {
/*  83 */         throw new XMLStreamException(ie);
/*     */       } 
/*     */     } 
/*  86 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Writer writer) throws XMLStreamException {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream outputStream) throws XMLStreamException {
/*  97 */     return (XMLStreamWriter)new StAXDocumentSerializer(outputStream, new StAXManager(this._manager));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream outputStream, String encoding) throws XMLStreamException {
/* 101 */     StAXDocumentSerializer serializer = new StAXDocumentSerializer(outputStream, new StAXManager(this._manager));
/* 102 */     serializer.setEncoding(encoding);
/* 103 */     return (XMLStreamWriter)serializer;
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 107 */     if (name == null) {
/* 108 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[] { null }));
/*     */     }
/* 110 */     if (this._manager.containsProperty(name))
/* 111 */       return this._manager.getProperty(name); 
/* 112 */     throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[] { name }));
/*     */   }
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/* 116 */     if (name == null) {
/* 117 */       return false;
/*     */     }
/* 119 */     return this._manager.containsProperty(name);
/*     */   }
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/* 123 */     this._manager.setProperty(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\factory\StAXOutputFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */