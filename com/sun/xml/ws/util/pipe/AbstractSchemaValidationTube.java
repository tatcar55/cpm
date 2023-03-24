/*     */ package com.sun.xml.ws.util.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.developer.SchemaValidationFeature;
/*     */ import com.sun.xml.ws.developer.ValidationErrorHandler;
/*     */ import com.sun.xml.ws.server.SDDocumentImpl;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.ws.wsdl.SDDocumentResolver;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.validation.Validator;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ls.LSInput;
/*     */ import org.w3c.dom.ls.LSResourceResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSchemaValidationTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  97 */   private static final Logger LOGGER = Logger.getLogger(AbstractSchemaValidationTube.class.getName());
/*     */   
/*     */   protected final WSBinding binding;
/*     */   protected final SchemaValidationFeature feature;
/* 101 */   protected final DocumentAddressResolver resolver = new ValidationDocumentAddressResolver();
/*     */   protected final SchemaFactory sf;
/*     */   
/*     */   public AbstractSchemaValidationTube(WSBinding binding, Tube next) {
/* 105 */     super(next);
/* 106 */     this.binding = binding;
/* 107 */     this.feature = (SchemaValidationFeature)binding.getFeature(SchemaValidationFeature.class);
/* 108 */     this.sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/*     */   }
/*     */   
/*     */   protected AbstractSchemaValidationTube(AbstractSchemaValidationTube that, TubeCloner cloner) {
/* 112 */     super(that, cloner);
/* 113 */     this.binding = that.binding;
/* 114 */     this.feature = that.feature;
/* 115 */     this.sf = that.sf;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ValidationDocumentAddressResolver
/*     */     implements DocumentAddressResolver
/*     */   {
/*     */     private ValidationDocumentAddressResolver() {}
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getRelativeAddressFor(@NotNull SDDocument current, @NotNull SDDocument referenced) {
/* 127 */       AbstractSchemaValidationTube.LOGGER.log(Level.FINE, "Current = {0} resolved relative={1}", new Object[] { current.getURL(), referenced.getURL() });
/* 128 */       return referenced.getURL().toExternalForm();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Document createDOM(SDDocument doc) {
/* 134 */     ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */     try {
/* 136 */       doc.writeTo(null, this.resolver, (OutputStream)bab);
/* 137 */     } catch (IOException ioe) {
/* 138 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */ 
/*     */     
/* 142 */     Transformer trans = XmlUtil.newTransformer();
/* 143 */     Source source = new StreamSource(bab.newInputStream(), null);
/* 144 */     DOMResult result = new DOMResult();
/*     */     try {
/* 146 */       trans.transform(source, result);
/* 147 */     } catch (TransformerException te) {
/* 148 */       throw new WebServiceException(te);
/*     */     } 
/* 150 */     return (Document)result.getNode();
/*     */   }
/*     */   
/*     */   protected class MetadataResolverImpl
/*     */     implements SDDocumentResolver, LSResourceResolver
/*     */   {
/* 156 */     final Map<String, SDDocument> docs = new HashMap<String, SDDocument>();
/*     */ 
/*     */     
/* 159 */     final Map<String, SDDocument> nsMapping = new HashMap<String, SDDocument>();
/*     */ 
/*     */     
/*     */     public MetadataResolverImpl() {}
/*     */     
/*     */     public MetadataResolverImpl(Iterable<SDDocument> it) {
/* 165 */       for (SDDocument doc : it) {
/* 166 */         if (doc.isSchema()) {
/* 167 */           this.docs.put(doc.getURL().toExternalForm(), doc);
/* 168 */           this.nsMapping.put(((SDDocument.Schema)doc).getTargetNamespace(), doc);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     void addSchema(Source schema) {
/* 174 */       assert schema.getSystemId() != null;
/*     */       
/* 176 */       String systemId = schema.getSystemId();
/*     */       try {
/* 178 */         XMLStreamBufferResult xsbr = (XMLStreamBufferResult)XmlUtil.identityTransform(schema, (Result)new XMLStreamBufferResult());
/* 179 */         SDDocumentSource sds = SDDocumentSource.create(new URL(systemId), (XMLStreamBuffer)xsbr.getXMLStreamBuffer());
/* 180 */         SDDocumentImpl sDDocumentImpl = SDDocumentImpl.create(sds, new QName(""), new QName(""));
/* 181 */         this.docs.put(systemId, sDDocumentImpl);
/* 182 */         this.nsMapping.put(((SDDocument.Schema)sDDocumentImpl).getTargetNamespace(), sDDocumentImpl);
/* 183 */       } catch (Exception ex) {
/* 184 */         AbstractSchemaValidationTube.LOGGER.log(Level.WARNING, "Exception in adding schemas to resolver", ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     void addSchemas(Collection<? extends Source> schemas) {
/* 189 */       for (Source src : schemas) {
/* 190 */         addSchema(src);
/*     */       }
/*     */     }
/*     */     
/*     */     public SDDocument resolve(String systemId) {
/*     */       SDDocumentImpl sDDocumentImpl;
/* 196 */       SDDocument sdi = this.docs.get(systemId);
/* 197 */       if (sdi == null) {
/*     */         SDDocumentSource sds;
/*     */         try {
/* 200 */           sds = SDDocumentSource.create(new URL(systemId));
/* 201 */         } catch (MalformedURLException e) {
/* 202 */           throw new WebServiceException(e);
/*     */         } 
/* 204 */         sDDocumentImpl = SDDocumentImpl.create(sds, new QName(""), new QName(""));
/* 205 */         this.docs.put(systemId, sDDocumentImpl);
/*     */       } 
/* 207 */       return (SDDocument)sDDocumentImpl;
/*     */     }
/*     */ 
/*     */     
/*     */     public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
/* 212 */       if (AbstractSchemaValidationTube.LOGGER.isLoggable(Level.FINE)) {
/* 213 */         AbstractSchemaValidationTube.LOGGER.log(Level.FINE, "type={0} namespaceURI={1} publicId={2} systemId={3} baseURI={4}", new Object[] { type, namespaceURI, publicId, systemId, baseURI });
/*     */       }
/*     */       try {
/*     */         final SDDocument doc;
/* 217 */         if (systemId == null) {
/* 218 */           doc = this.nsMapping.get(namespaceURI);
/*     */         } else {
/* 220 */           URI rel = (baseURI != null) ? (new URI(baseURI)).resolve(systemId) : new URI(systemId);
/*     */ 
/*     */           
/* 223 */           doc = this.docs.get(rel.toString());
/*     */         } 
/* 225 */         if (doc != null) {
/* 226 */           return new LSInput()
/*     */             {
/*     */               public Reader getCharacterStream()
/*     */               {
/* 230 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setCharacterStream(Reader characterStream) {
/* 235 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public InputStream getByteStream() {
/* 240 */                 ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */                 try {
/* 242 */                   doc.writeTo(null, AbstractSchemaValidationTube.this.resolver, (OutputStream)bab);
/* 243 */                 } catch (IOException ioe) {
/* 244 */                   throw new WebServiceException(ioe);
/*     */                 } 
/* 246 */                 return bab.newInputStream();
/*     */               }
/*     */ 
/*     */               
/*     */               public void setByteStream(InputStream byteStream) {
/* 251 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getStringData() {
/* 256 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setStringData(String stringData) {
/* 261 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getSystemId() {
/* 266 */                 return doc.getURL().toExternalForm();
/*     */               }
/*     */ 
/*     */               
/*     */               public void setSystemId(String systemId) {
/* 271 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getPublicId() {
/* 276 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setPublicId(String publicId) {
/* 281 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getBaseURI() {
/* 286 */                 return doc.getURL().toExternalForm();
/*     */               }
/*     */ 
/*     */               
/*     */               public void setBaseURI(String baseURI) {
/* 291 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public String getEncoding() {
/* 296 */                 return null;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setEncoding(String encoding) {
/* 301 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean getCertifiedText() {
/* 306 */                 return false;
/*     */               }
/*     */ 
/*     */               
/*     */               public void setCertifiedText(boolean certifiedText) {
/* 311 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/* 315 */       } catch (Exception e) {
/* 316 */         AbstractSchemaValidationTube.LOGGER.log(Level.WARNING, "Exception in LSResourceResolver impl", e);
/*     */       } 
/* 318 */       if (AbstractSchemaValidationTube.LOGGER.isLoggable(Level.FINE)) {
/* 319 */         AbstractSchemaValidationTube.LOGGER.log(Level.FINE, "Don''t know about systemId={0} baseURI={1}", new Object[] { systemId, baseURI });
/*     */       }
/* 321 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMultiSchemaForTns(String tns, String systemId, Map<String, List<String>> schemas) {
/* 327 */     List<String> docIdList = schemas.get(tns);
/* 328 */     if (docIdList == null) {
/* 329 */       docIdList = new ArrayList<String>();
/* 330 */       schemas.put(tns, docIdList);
/*     */     } 
/* 332 */     docIdList.add(systemId);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Source[] getSchemaSources(Iterable<SDDocument> docs, MetadataResolverImpl mdresolver) {
/* 351 */     Map<String, DOMSource> inlinedSchemas = new HashMap<String, DOMSource>();
/*     */ 
/*     */ 
/*     */     
/* 355 */     Map<String, List<String>> multiSchemaForTns = new HashMap<String, List<String>>();
/*     */     
/* 357 */     for (SDDocument sdoc : docs) {
/* 358 */       if (sdoc.isWSDL()) {
/* 359 */         Document dom = createDOM(sdoc);
/*     */         
/* 361 */         addSchemaFragmentSource(dom, sdoc.getURL().toExternalForm(), inlinedSchemas); continue;
/* 362 */       }  if (sdoc.isSchema()) {
/* 363 */         updateMultiSchemaForTns(((SDDocument.Schema)sdoc).getTargetNamespace(), sdoc.getURL().toExternalForm(), multiSchemaForTns);
/*     */       }
/*     */     } 
/* 366 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 367 */       LOGGER.log(Level.FINE, "WSDL inlined schema fragment documents(these are used to create a pseudo schema) = {0}", inlinedSchemas.keySet());
/*     */     }
/* 369 */     for (DOMSource src : inlinedSchemas.values()) {
/* 370 */       String tns = getTargetNamespace(src);
/* 371 */       updateMultiSchemaForTns(tns, src.getSystemId(), multiSchemaForTns);
/*     */     } 
/*     */     
/* 374 */     if (multiSchemaForTns.isEmpty())
/* 375 */       return new Source[0]; 
/* 376 */     if (multiSchemaForTns.size() == 1 && ((List)multiSchemaForTns.values().iterator().next()).size() == 1) {
/*     */       
/* 378 */       String systemId = ((List<String>)multiSchemaForTns.values().iterator().next()).get(0);
/* 379 */       return new Source[] { inlinedSchemas.get(systemId) };
/*     */     } 
/*     */ 
/*     */     
/* 383 */     mdresolver.addSchemas(inlinedSchemas.values());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     Map<String, String> oneSchemaForTns = new HashMap<String, String>();
/* 389 */     int i = 0;
/* 390 */     for (Map.Entry<String, List<String>> e : multiSchemaForTns.entrySet()) {
/*     */       String systemId;
/* 392 */       List<String> sameTnsSchemas = e.getValue();
/* 393 */       if (sameTnsSchemas.size() > 1) {
/*     */ 
/*     */         
/* 396 */         systemId = "file:x-jax-ws-include-" + i++;
/* 397 */         Source src = createSameTnsPseudoSchema(e.getKey(), sameTnsSchemas, systemId);
/* 398 */         mdresolver.addSchema(src);
/*     */       } else {
/* 400 */         systemId = sameTnsSchemas.get(0);
/*     */       } 
/* 402 */       oneSchemaForTns.put(e.getKey(), systemId);
/*     */     } 
/*     */ 
/*     */     
/* 406 */     Source pseudoSchema = createMasterPseudoSchema(oneSchemaForTns);
/* 407 */     return new Source[] { pseudoSchema };
/*     */   }
/*     */   @Nullable
/*     */   private void addSchemaFragmentSource(Document doc, String systemId, Map<String, DOMSource> map) {
/* 411 */     Element e = doc.getDocumentElement();
/* 412 */     assert e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/");
/* 413 */     assert e.getLocalName().equals("definitions");
/*     */     
/* 415 */     NodeList typesList = e.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "types");
/* 416 */     for (int i = 0; i < typesList.getLength(); i++) {
/* 417 */       NodeList schemaList = ((Element)typesList.item(i)).getElementsByTagNameNS("http://www.w3.org/2001/XMLSchema", "schema");
/* 418 */       for (int j = 0; j < schemaList.getLength(); j++) {
/* 419 */         Element elem = (Element)schemaList.item(j);
/* 420 */         NamespaceSupport nss = new NamespaceSupport();
/*     */ 
/*     */         
/* 423 */         buildNamespaceSupport(nss, elem);
/* 424 */         patchDOMFragment(nss, elem);
/* 425 */         String docId = systemId + "#schema" + j;
/* 426 */         map.put(docId, new DOMSource(elem, docId));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildNamespaceSupport(NamespaceSupport nss, Node node) {
/* 436 */     if (node == null || node.getNodeType() != 1) {
/*     */       return;
/*     */     }
/*     */     
/* 440 */     buildNamespaceSupport(nss, node.getParentNode());
/*     */     
/* 442 */     nss.pushContext();
/* 443 */     NamedNodeMap atts = node.getAttributes();
/* 444 */     for (int i = 0; i < atts.getLength(); i++) {
/* 445 */       Attr a = (Attr)atts.item(i);
/* 446 */       if ("xmlns".equals(a.getPrefix())) {
/* 447 */         nss.declarePrefix(a.getLocalName(), a.getValue());
/*     */       
/*     */       }
/* 450 */       else if ("xmlns".equals(a.getName())) {
/* 451 */         nss.declarePrefix("", a.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private void patchDOMFragment(NamespaceSupport nss, Element elem) {
/* 464 */     NamedNodeMap atts = elem.getAttributes();
/* 465 */     for (Enumeration<String> en = nss.getPrefixes(); en.hasMoreElements(); ) {
/* 466 */       String prefix = en.nextElement();
/*     */       
/* 468 */       for (int i = 0; i < atts.getLength(); i++) {
/* 469 */         Attr a = (Attr)atts.item(i);
/* 470 */         if (!"xmlns".equals(a.getPrefix()) || !a.getLocalName().equals(prefix)) {
/* 471 */           if (LOGGER.isLoggable(Level.FINE)) {
/* 472 */             LOGGER.log(Level.FINE, "Patching with xmlns:{0}={1}", new Object[] { prefix, nss.getURI(prefix) });
/*     */           }
/* 474 */           elem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, nss.getURI(prefix));
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Source createSameTnsPseudoSchema(String tns, Collection<String> docs, String pseudoSystemId) {
/* 496 */     assert docs.size() > 1;
/*     */     
/* 498 */     final StringBuilder sb = new StringBuilder("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema'");
/* 499 */     if (!tns.equals("")) {
/* 500 */       sb.append(" targetNamespace='").append(tns).append("'");
/*     */     }
/* 502 */     sb.append(">\n");
/* 503 */     for (String systemId : docs) {
/* 504 */       sb.append("<xsd:include schemaLocation='").append(systemId).append("'/>\n");
/*     */     }
/* 506 */     sb.append("</xsd:schema>\n");
/* 507 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 508 */       LOGGER.log(Level.FINE, "Pseudo Schema for the same tns={0}is {1}", new Object[] { tns, sb });
/*     */     }
/*     */ 
/*     */     
/* 512 */     return new StreamSource(pseudoSystemId)
/*     */       {
/*     */         public Reader getReader() {
/* 515 */           return new StringReader(sb.toString());
/*     */         }
/*     */       };
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
/*     */   private Source createMasterPseudoSchema(Map<String, String> docs) {
/* 532 */     final StringBuilder sb = new StringBuilder("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' targetNamespace='urn:x-jax-ws-master'>\n");
/* 533 */     for (Map.Entry<String, String> e : docs.entrySet()) {
/* 534 */       String systemId = e.getValue();
/* 535 */       String ns = e.getKey();
/* 536 */       sb.append("<xsd:import schemaLocation='").append(systemId).append("'");
/* 537 */       if (!ns.equals("")) {
/* 538 */         sb.append(" namespace='").append(ns).append("'");
/*     */       }
/* 540 */       sb.append("/>\n");
/*     */     } 
/* 542 */     sb.append("</xsd:schema>");
/* 543 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 544 */       LOGGER.log(Level.FINE, "Master Pseudo Schema = {0}", sb);
/*     */     }
/*     */ 
/*     */     
/* 548 */     return new StreamSource("file:x-jax-ws-master-doc")
/*     */       {
/*     */         public Reader getReader() {
/* 551 */           return new StringReader(sb.toString());
/*     */         }
/*     */       };
/*     */   }
/*     */   protected void doProcess(Packet packet) throws SAXException {
/*     */     ValidationErrorHandler handler;
/* 557 */     getValidator().reset();
/* 558 */     Class<? extends ValidationErrorHandler> handlerClass = this.feature.getErrorHandler();
/*     */     
/*     */     try {
/* 561 */       handler = handlerClass.newInstance();
/* 562 */     } catch (Exception e) {
/* 563 */       throw new WebServiceException(e);
/*     */     } 
/* 565 */     handler.setPacket(packet);
/* 566 */     getValidator().setErrorHandler((ErrorHandler)handler);
/* 567 */     Message msg = packet.getMessage().copy();
/* 568 */     Source source = msg.readPayloadAsSource();
/*     */ 
/*     */     
/*     */     try {
/* 572 */       getValidator().validate(source);
/* 573 */     } catch (IOException e) {
/* 574 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getTargetNamespace(DOMSource src) {
/* 579 */     Element elem = (Element)src.getNode();
/* 580 */     return elem.getAttribute("targetNamespace");
/*     */   }
/*     */   
/*     */   protected abstract Validator getValidator();
/*     */   
/*     */   protected abstract boolean isNoValidation();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\pipe\AbstractSchemaValidationTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */