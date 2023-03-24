/*     */ package com.sun.xml.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.MarshallerImpl;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
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
/*     */ public class MarshallerBridge
/*     */   extends Bridge
/*     */   implements XMLBridge
/*     */ {
/*     */   protected MarshallerBridge(JAXBContextImpl context) {
/*  71 */     super(context);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, XMLStreamWriter output) throws JAXBException {
/*  75 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  77 */       m.marshal(object, output);
/*     */     } finally {
/*  79 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/*  84 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  86 */       ((MarshallerImpl)m).marshal(object, output, nsContext);
/*     */     } finally {
/*  88 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, Node output) throws JAXBException {
/*  93 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/*  95 */       m.marshal(object, output);
/*     */     } finally {
/*  97 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, ContentHandler contentHandler) throws JAXBException {
/* 102 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/* 104 */       m.marshal(object, contentHandler);
/*     */     } finally {
/* 106 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, Object object, Result result) throws JAXBException {
/* 111 */     m.setProperty("jaxb.fragment", Boolean.valueOf(true));
/*     */     try {
/* 113 */       m.marshal(object, result);
/*     */     } finally {
/* 115 */       m.setProperty("jaxb.fragment", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, XMLStreamReader in) {
/* 120 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, Source in) {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, InputStream in) {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object unmarshal(Unmarshaller u, Node n) {
/* 132 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/* 136 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public TypeReference getTypeReference() {
/* 139 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public BindingContext context() {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public boolean supportOutputStream() {
/* 145 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\glassfish\MarshallerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */