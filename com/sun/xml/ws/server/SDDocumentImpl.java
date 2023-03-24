/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.util.RuntimeVersion;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.ws.wsdl.SDDocumentResolver;
/*     */ import com.sun.xml.ws.wsdl.parser.ParserUtil;
/*     */ import com.sun.xml.ws.wsdl.parser.WSDLConstants;
/*     */ import com.sun.xml.ws.wsdl.writer.DocumentLocationResolver;
/*     */ import com.sun.xml.ws.wsdl.writer.WSDLPatcher;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class SDDocumentImpl
/*     */   extends SDDocumentSource
/*     */   implements SDDocument
/*     */ {
/*     */   private static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
/*  80 */   private static final QName SCHEMA_INCLUDE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "include");
/*  81 */   private static final QName SCHEMA_IMPORT_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "import");
/*  82 */   private static final QName SCHEMA_REDEFINE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "redefine");
/*  83 */   private static final String VERSION_COMMENT = " Published by JAX-WS RI at http://jax-ws.dev.java.net. RI's version is " + RuntimeVersion.VERSION + ". ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final QName rootName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SDDocumentSource source;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   List<SDDocumentFilter> filters;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   SDDocumentResolver sddocResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final URL url;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<String> imports;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SDDocumentImpl create(SDDocumentSource src, QName serviceName, QName portTypeName) {
/* 120 */     URL systemId = src.getSystemId();
/*     */ 
/*     */     
/*     */     try {
/* 124 */       XMLStreamReader reader = src.read();
/*     */       try {
/* 126 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */         
/* 128 */         QName rootName = reader.getName();
/* 129 */         if (rootName.equals(WSDLConstants.QNAME_SCHEMA)) {
/* 130 */           String tns = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/* 131 */           Set<String> importedDocs = new HashSet<String>();
/* 132 */           while (XMLStreamReaderUtil.nextContent(reader) != 8) {
/* 133 */             if (reader.getEventType() != 1)
/*     */               continue; 
/* 135 */             QName name = reader.getName();
/* 136 */             if (SCHEMA_INCLUDE_QNAME.equals(name) || SCHEMA_IMPORT_QNAME.equals(name) || SCHEMA_REDEFINE_QNAME.equals(name)) {
/*     */               
/* 138 */               String importedDoc = reader.getAttributeValue(null, "schemaLocation");
/* 139 */               if (importedDoc != null) {
/* 140 */                 importedDocs.add((new URL(src.getSystemId(), importedDoc)).toString());
/*     */               }
/*     */             } 
/*     */           } 
/* 144 */           return new SchemaImpl(rootName, systemId, src, tns, importedDocs);
/* 145 */         }  if (rootName.equals(WSDLConstants.QNAME_DEFINITIONS)) {
/* 146 */           String tns = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/*     */           
/* 148 */           boolean hasPortType = false;
/* 149 */           boolean hasService = false;
/* 150 */           Set<String> importedDocs = new HashSet<String>();
/* 151 */           Set<QName> allServices = new HashSet<QName>();
/*     */ 
/*     */           
/* 154 */           while (XMLStreamReaderUtil.nextContent(reader) != 8) {
/* 155 */             if (reader.getEventType() != 1) {
/*     */               continue;
/*     */             }
/* 158 */             QName name = reader.getName();
/* 159 */             if (WSDLConstants.QNAME_PORT_TYPE.equals(name)) {
/* 160 */               String pn = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/* 161 */               if (portTypeName != null && 
/* 162 */                 portTypeName.getLocalPart().equals(pn) && portTypeName.getNamespaceURI().equals(tns))
/* 163 */                 hasPortType = true; 
/*     */               continue;
/*     */             } 
/* 166 */             if (WSDLConstants.QNAME_SERVICE.equals(name)) {
/* 167 */               String sn = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/* 168 */               QName sqn = new QName(tns, sn);
/* 169 */               allServices.add(sqn);
/* 170 */               if (serviceName.equals(sqn))
/* 171 */                 hasService = true;  continue;
/*     */             } 
/* 173 */             if (WSDLConstants.QNAME_IMPORT.equals(name)) {
/* 174 */               String importedDoc = reader.getAttributeValue(null, "location");
/* 175 */               if (importedDoc != null)
/* 176 */                 importedDocs.add((new URL(src.getSystemId(), importedDoc)).toString());  continue;
/*     */             } 
/* 178 */             if (SCHEMA_INCLUDE_QNAME.equals(name) || SCHEMA_IMPORT_QNAME.equals(name) || SCHEMA_REDEFINE_QNAME.equals(name)) {
/*     */               
/* 180 */               String importedDoc = reader.getAttributeValue(null, "schemaLocation");
/* 181 */               if (importedDoc != null) {
/* 182 */                 importedDocs.add((new URL(src.getSystemId(), importedDoc)).toString());
/*     */               }
/*     */             } 
/*     */           } 
/* 186 */           return new WSDLImpl(rootName, systemId, src, tns, hasPortType, hasService, importedDocs, allServices);
/*     */         } 
/*     */         
/* 189 */         return new SDDocumentImpl(rootName, systemId, src);
/*     */       } finally {
/*     */         
/* 192 */         reader.close();
/*     */       } 
/* 194 */     } catch (WebServiceException e) {
/* 195 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { systemId, e });
/* 196 */     } catch (IOException e) {
/* 197 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { systemId, e });
/* 198 */     } catch (XMLStreamException e) {
/* 199 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { systemId, e });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SDDocumentImpl(QName rootName, URL url, SDDocumentSource source) {
/* 204 */     this(rootName, url, source, new HashSet<String>());
/*     */   }
/*     */   
/*     */   protected SDDocumentImpl(QName rootName, URL url, SDDocumentSource source, Set<String> imports) {
/* 208 */     if (url == null) {
/* 209 */       throw new IllegalArgumentException("Cannot construct SDDocument with null URL.");
/*     */     }
/* 211 */     this.rootName = rootName;
/* 212 */     this.source = source;
/* 213 */     this.url = url;
/* 214 */     this.imports = imports;
/*     */   }
/*     */   
/*     */   void setFilters(List<SDDocumentFilter> filters) {
/* 218 */     this.filters = filters;
/*     */   }
/*     */   
/*     */   void setResolver(SDDocumentResolver sddocResolver) {
/* 222 */     this.sddocResolver = sddocResolver;
/*     */   }
/*     */   
/*     */   public QName getRootName() {
/* 226 */     return this.rootName;
/*     */   }
/*     */   
/*     */   public boolean isWSDL() {
/* 230 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSchema() {
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   public URL getURL() {
/* 238 */     return this.url;
/*     */   }
/*     */   
/*     */   public XMLStreamReader read(XMLInputFactory xif) throws IOException, XMLStreamException {
/* 242 */     return this.source.read(xif);
/*     */   }
/*     */   
/*     */   public XMLStreamReader read() throws IOException, XMLStreamException {
/* 246 */     return this.source.read();
/*     */   }
/*     */   
/*     */   public URL getSystemId() {
/* 250 */     return this.url;
/*     */   }
/*     */   
/*     */   public Set<String> getImports() {
/* 254 */     return this.imports;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 258 */     XMLStreamWriter w = null;
/*     */     
/*     */     try {
/* 261 */       w = XMLStreamWriterFactory.create(os, "UTF-8");
/* 262 */       w.writeStartDocument("UTF-8", "1.0");
/* 263 */       (new XMLStreamReaderToXMLStreamWriter()).bridge(this.source.read(), w);
/* 264 */       w.writeEndDocument();
/* 265 */     } catch (XMLStreamException e) {
/* 266 */       IOException ioe = new IOException(e.getMessage());
/* 267 */       ioe.initCause(e);
/* 268 */       throw ioe;
/*     */     } finally {
/*     */       try {
/* 271 */         if (w != null)
/* 272 */           w.close(); 
/* 273 */       } catch (XMLStreamException e) {
/* 274 */         IOException ioe = new IOException(e.getMessage());
/* 275 */         ioe.initCause(e);
/* 276 */         throw ioe;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(PortAddressResolver portAddressResolver, DocumentAddressResolver resolver, OutputStream os) throws IOException {
/* 283 */     XMLStreamWriter w = null;
/*     */     
/*     */     try {
/* 286 */       w = XMLStreamWriterFactory.create(os, "UTF-8");
/* 287 */       w.writeStartDocument("UTF-8", "1.0");
/* 288 */       writeTo(portAddressResolver, resolver, w);
/* 289 */       w.writeEndDocument();
/* 290 */     } catch (XMLStreamException e) {
/* 291 */       IOException ioe = new IOException(e.getMessage());
/* 292 */       ioe.initCause(e);
/* 293 */       throw ioe;
/*     */     } finally {
/*     */       try {
/* 296 */         if (w != null)
/* 297 */           w.close(); 
/* 298 */       } catch (XMLStreamException e) {
/* 299 */         IOException ioe = new IOException(e.getMessage());
/* 300 */         ioe.initCause(e);
/* 301 */         throw ioe;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(PortAddressResolver portAddressResolver, DocumentAddressResolver resolver, XMLStreamWriter out) throws XMLStreamException, IOException {
/* 307 */     if (this.filters != null) {
/* 308 */       for (SDDocumentFilter f : this.filters) {
/* 309 */         out = f.filter(this, out);
/*     */       }
/*     */     }
/*     */     
/* 313 */     XMLStreamReader xsr = this.source.read();
/*     */     try {
/* 315 */       out.writeComment(VERSION_COMMENT);
/* 316 */       (new WSDLPatcher(portAddressResolver, new DocumentLocationResolverImpl(resolver))).bridge(xsr, out);
/*     */     } finally {
/* 318 */       xsr.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SchemaImpl
/*     */     extends SDDocumentImpl
/*     */     implements SDDocument.Schema
/*     */   {
/*     */     private final String targetNamespace;
/*     */ 
/*     */ 
/*     */     
/*     */     public SchemaImpl(QName rootName, URL url, SDDocumentSource source, String targetNamespace, Set<String> imports) {
/* 333 */       super(rootName, url, source, imports);
/* 334 */       this.targetNamespace = targetNamespace;
/*     */     }
/*     */     
/*     */     public String getTargetNamespace() {
/* 338 */       return this.targetNamespace;
/*     */     }
/*     */     
/*     */     public boolean isSchema() {
/* 342 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class WSDLImpl
/*     */     extends SDDocumentImpl
/*     */     implements SDDocument.WSDL {
/*     */     private final String targetNamespace;
/*     */     private final boolean hasPortType;
/*     */     private final boolean hasService;
/*     */     private final Set<QName> allServices;
/*     */     
/*     */     public WSDLImpl(QName rootName, URL url, SDDocumentSource source, String targetNamespace, boolean hasPortType, boolean hasService, Set<String> imports, Set<QName> allServices) {
/* 355 */       super(rootName, url, source, imports);
/* 356 */       this.targetNamespace = targetNamespace;
/* 357 */       this.hasPortType = hasPortType;
/* 358 */       this.hasService = hasService;
/* 359 */       this.allServices = allServices;
/*     */     }
/*     */     
/*     */     public String getTargetNamespace() {
/* 363 */       return this.targetNamespace;
/*     */     }
/*     */     
/*     */     public boolean hasPortType() {
/* 367 */       return this.hasPortType;
/*     */     }
/*     */     
/*     */     public boolean hasService() {
/* 371 */       return this.hasService;
/*     */     }
/*     */     
/*     */     public Set<QName> getAllServices() {
/* 375 */       return this.allServices;
/*     */     }
/*     */     
/*     */     public boolean isWSDL() {
/* 379 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private class DocumentLocationResolverImpl implements DocumentLocationResolver {
/*     */     private DocumentAddressResolver delegate;
/*     */     
/*     */     DocumentLocationResolverImpl(DocumentAddressResolver delegate) {
/* 387 */       this.delegate = delegate;
/*     */     }
/*     */     
/*     */     public String getLocationFor(String namespaceURI, String systemId) {
/* 391 */       if (SDDocumentImpl.this.sddocResolver == null) {
/* 392 */         return systemId;
/*     */       }
/*     */       try {
/* 395 */         URL ref = new URL(SDDocumentImpl.this.getURL(), systemId);
/* 396 */         SDDocument refDoc = SDDocumentImpl.this.sddocResolver.resolve(ref.toExternalForm());
/* 397 */         if (refDoc == null) {
/* 398 */           return systemId;
/*     */         }
/* 400 */         return this.delegate.getRelativeAddressFor(SDDocumentImpl.this, refDoc);
/* 401 */       } catch (MalformedURLException mue) {
/* 402 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\SDDocumentImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */