/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.WSDLResolver;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.ws.Holder;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WSDLGenResolver
/*     */   implements WSDLResolver
/*     */ {
/*     */   private final List<SDDocumentImpl> docs;
/*  69 */   private final List<SDDocumentSource> newDocs = new ArrayList<SDDocumentSource>();
/*     */ 
/*     */   
/*     */   private SDDocumentSource concreteWsdlSource;
/*     */   
/*     */   private SDDocumentImpl abstractWsdl;
/*     */   
/*     */   private SDDocumentImpl concreteWsdl;
/*     */   
/*  78 */   private final Map<String, List<SDDocumentImpl>> nsMapping = new HashMap<String, List<SDDocumentImpl>>();
/*     */   
/*     */   private final QName serviceName;
/*     */   private final QName portTypeName;
/*     */   
/*     */   public WSDLGenResolver(@NotNull List<SDDocumentImpl> docs, QName serviceName, QName portTypeName) {
/*  84 */     this.docs = docs;
/*  85 */     this.serviceName = serviceName;
/*  86 */     this.portTypeName = portTypeName;
/*     */     
/*  88 */     for (SDDocumentImpl doc : docs) {
/*  89 */       if (doc.isWSDL()) {
/*  90 */         SDDocument.WSDL wsdl = (SDDocument.WSDL)doc;
/*  91 */         if (wsdl.hasPortType())
/*  92 */           this.abstractWsdl = doc; 
/*     */       } 
/*  94 */       if (doc.isSchema()) {
/*  95 */         SDDocument.Schema schema = (SDDocument.Schema)doc;
/*  96 */         List<SDDocumentImpl> sysIds = this.nsMapping.get(schema.getTargetNamespace());
/*  97 */         if (sysIds == null) {
/*  98 */           sysIds = new ArrayList<SDDocumentImpl>();
/*  99 */           this.nsMapping.put(schema.getTargetNamespace(), sysIds);
/*     */         } 
/* 101 */         sysIds.add(doc);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result getWSDL(String filename) {
/* 112 */     URL url = createURL(filename);
/* 113 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 114 */     xsb.setSystemId(url.toExternalForm());
/* 115 */     this.concreteWsdlSource = SDDocumentSource.create(url, (XMLStreamBuffer)xsb);
/* 116 */     this.newDocs.add(this.concreteWsdlSource);
/* 117 */     XMLStreamBufferResult r = new XMLStreamBufferResult(xsb);
/* 118 */     r.setSystemId(filename);
/* 119 */     return (Result)r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL createURL(String filename) {
/*     */     try {
/* 131 */       return new URL("file:///" + filename);
/* 132 */     } catch (MalformedURLException e) {
/*     */ 
/*     */       
/* 135 */       throw new WebServiceException(e);
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
/*     */   
/*     */   public Result getAbstractWSDL(Holder<String> filename) {
/* 148 */     if (this.abstractWsdl != null) {
/* 149 */       filename.value = (T)this.abstractWsdl.getURL().toString();
/* 150 */       return null;
/*     */     } 
/* 152 */     URL url = createURL((String)filename.value);
/* 153 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 154 */     xsb.setSystemId(url.toExternalForm());
/* 155 */     SDDocumentSource abstractWsdlSource = SDDocumentSource.create(url, (XMLStreamBuffer)xsb);
/* 156 */     this.newDocs.add(abstractWsdlSource);
/* 157 */     XMLStreamBufferResult r = new XMLStreamBufferResult(xsb);
/* 158 */     r.setSystemId((String)filename.value);
/* 159 */     return (Result)r;
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
/*     */   public Result getSchemaOutput(String namespace, Holder<String> filename) {
/* 171 */     List<SDDocumentImpl> schemas = this.nsMapping.get(namespace);
/* 172 */     if (schemas != null) {
/* 173 */       if (schemas.size() > 1) {
/* 174 */         throw new ServerRtException("server.rt.err", new Object[] { "More than one schema for the target namespace " + namespace });
/*     */       }
/*     */       
/* 177 */       filename.value = (T)((SDDocumentImpl)schemas.get(0)).getURL().toExternalForm();
/* 178 */       return null;
/*     */     } 
/*     */     
/* 181 */     URL url = createURL((String)filename.value);
/* 182 */     MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 183 */     xsb.setSystemId(url.toExternalForm());
/* 184 */     SDDocumentSource sd = SDDocumentSource.create(url, (XMLStreamBuffer)xsb);
/* 185 */     this.newDocs.add(sd);
/*     */     
/* 187 */     XMLStreamBufferResult r = new XMLStreamBufferResult(xsb);
/* 188 */     r.setSystemId((String)filename.value);
/* 189 */     return (Result)r;
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
/*     */   public SDDocumentImpl updateDocs() {
/* 201 */     for (SDDocumentSource doc : this.newDocs) {
/* 202 */       SDDocumentImpl docImpl = SDDocumentImpl.create(doc, this.serviceName, this.portTypeName);
/* 203 */       if (doc == this.concreteWsdlSource) {
/* 204 */         this.concreteWsdl = docImpl;
/*     */       }
/* 206 */       this.docs.add(docImpl);
/*     */     } 
/* 208 */     return this.concreteWsdl;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\WSDLGenResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */