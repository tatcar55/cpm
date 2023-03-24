/*     */ package com.sun.xml.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.MarshallerImpl;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
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
/*     */ final class MarshallerBridge
/*     */   extends Bridge
/*     */ {
/*     */   public MarshallerBridge(JAXBRIContext context) {
/*  69 */     super((JAXBContextImpl)context);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, XMLStreamWriter output) throws JAXBException {
/*  73 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  75 */       m.marshal(object, output);
/*     */     } finally {
/*  77 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/*  82 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  84 */       ((MarshallerImpl)m).marshal(object, output, nsContext);
/*     */     } finally {
/*  86 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, Node output) throws JAXBException {
/*  91 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  93 */       m.marshal(object, output);
/*     */     } finally {
/*  95 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, ContentHandler contentHandler) throws JAXBException {
/* 100 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/* 102 */       m.marshal(object, contentHandler);
/*     */     } finally {
/* 104 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, Result result) throws JAXBException {
/* 109 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/* 111 */       m.marshal(object, result);
/*     */     } finally {
/* 113 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, XMLStreamReader in) {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, Source in) {
/* 122 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, InputStream in) {
/* 126 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, Node n) {
/* 130 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public TypeReference getTypeReference() {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\jaxb\MarshallerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */