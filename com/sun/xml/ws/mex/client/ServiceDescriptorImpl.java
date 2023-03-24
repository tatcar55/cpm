/*     */ package com.sun.xml.ws.mex.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.wsdl.parser.ServiceDescriptor;
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
/*     */ import com.sun.xml.ws.mex.client.schema.Metadata;
/*     */ import com.sun.xml.ws.mex.client.schema.MetadataReference;
/*     */ import com.sun.xml.ws.mex.client.schema.MetadataSection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceDescriptorImpl
/*     */   extends ServiceDescriptor
/*     */ {
/*     */   private final List<Source> wsdls;
/*     */   private final List<Source> schemas;
/*     */   private final List<Node> importsToPatch;
/*     */   private final Map<String, String> nsToSysIdMap;
/*     */   private static final String LOCATION = "location";
/*     */   private static final String NAMESPACE = "namespace";
/* 103 */   private static final Logger logger = Logger.getLogger(ServiceDescriptorImpl.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceDescriptorImpl(Metadata mData) {
/* 111 */     this.wsdls = new ArrayList<Source>();
/* 112 */     this.schemas = new ArrayList<Source>();
/* 113 */     this.importsToPatch = new ArrayList<Node>();
/* 114 */     this.nsToSysIdMap = new HashMap<String, String>();
/* 115 */     populateLists(mData);
/* 116 */     if (!this.importsToPatch.isEmpty()) {
/* 117 */       patchImports();
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
/*     */   private void populateLists(Metadata mData) {
/* 129 */     for (MetadataSection section : mData.getMetadataSection()) {
/* 130 */       if (section.getMetadataReference() != null) {
/* 131 */         handleReference(section); continue;
/* 132 */       }  if (section.getLocation() != null) {
/* 133 */         handleLocation(section); continue;
/*     */       } 
/* 135 */       handleXml(section);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleXml(MetadataSection section) {
/* 145 */     String dialect = section.getDialect();
/* 146 */     String identifier = section.getIdentifier();
/* 147 */     if (dialect.equals("http://schemas.xmlsoap.org/wsdl/")) {
/* 148 */       this.wsdls.add(createSource(section, identifier));
/* 149 */     } else if (dialect.equals("http://www.w3.org/2001/XMLSchema")) {
/* 150 */       this.schemas.add(createSource(section, identifier));
/*     */     } else {
/* 152 */       logger.warning(MessagesMessages.MEX_0002_UNKNOWN_DIALECT_WITH_ID(dialect, identifier));
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
/*     */   private void handleReference(MetadataSection section) {
/* 164 */     MetadataReference ref = section.getMetadataReference();
/* 165 */     populateLists((new MetadataClient()).retrieveMetadata(ref));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleLocation(MetadataSection section) {
/* 173 */     String location = section.getLocation();
/* 174 */     String dialect = section.getDialect();
/* 175 */     String identifier = section.getIdentifier();
/* 176 */     if (dialect.equals("http://schemas.xmlsoap.org/wsdl/")) {
/* 177 */       this.wsdls.add(getSourceFromLocation(location, identifier));
/* 178 */     } else if (dialect.equals("http://www.w3.org/2001/XMLSchema")) {
/* 179 */       this.schemas.add(getSourceFromLocation(location, identifier));
/*     */     } else {
/* 181 */       logger.warning(MessagesMessages.MEX_0002_UNKNOWN_DIALECT_WITH_ID(dialect, identifier));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Source> getWSDLs() {
/* 188 */     return this.wsdls;
/*     */   }
/*     */   
/*     */   public List<Source> getSchemas() {
/* 192 */     return this.schemas;
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
/*     */   private Source createSource(MetadataSection section, String identifier) {
/* 204 */     Node node = (Node)section.getAny();
/* 205 */     String sysId = identifier;
/* 206 */     if (section.getDialect().equals("http://schemas.xmlsoap.org/wsdl/")) {
/* 207 */       String targetNamespace = getNamespaceFromNode(node);
/* 208 */       if (sysId == null) {
/* 209 */         sysId = targetNamespace;
/*     */       }
/* 211 */       this.nsToSysIdMap.put(targetNamespace, sysId);
/* 212 */       checkWsdlImports(node);
/*     */     }
/* 214 */     else if (sysId == null) {
/* 215 */       sysId = getNamespaceFromNode(node);
/*     */     } 
/*     */     
/* 218 */     Source source = new DOMSource(node);
/* 219 */     source.setSystemId(sysId);
/* 220 */     return source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Source getSourceFromLocation(String address, String identifier) {
/*     */     try {
/* 232 */       HttpPoster poster = new HttpPoster();
/* 233 */       InputStream response = poster.makeGetCall(address);
/* 234 */       if (identifier != null) {
/* 235 */         StreamSource source = new StreamSource(response);
/* 236 */         source.setSystemId(identifier);
/* 237 */         return source;
/*     */       } 
/* 239 */       return parseAndConvertStream(address, response);
/* 240 */     } catch (IOException ioe) {
/* 241 */       String exceptionMessage = MessagesMessages.MEX_0014_RETRIEVAL_FROM_ADDRESS_FAILURE(address);
/*     */ 
/*     */       
/* 244 */       logger.log(Level.SEVERE, exceptionMessage, ioe);
/* 245 */       throw new WebServiceException(exceptionMessage, ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNamespaceFromNode(Node node) {
/* 255 */     Node namespace = node.getAttributes().getNamedItem("targetNamespace");
/* 256 */     if (namespace == null) {
/*     */       
/* 258 */       logger.warning(MessagesMessages.MEX_0003_UNKNOWN_WSDL_NAMESPACE(node.getNodeName()));
/*     */ 
/*     */       
/* 261 */       return null;
/*     */     } 
/* 263 */     return namespace.getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkWsdlImports(Node wsdl) {
/* 272 */     NodeList kids = wsdl.getChildNodes();
/* 273 */     for (int i = 0; i < kids.getLength(); i++) {
/* 274 */       Node importNode = kids.item(i);
/* 275 */       if (importNode.getLocalName() != null && importNode.getLocalName().equals("import")) {
/*     */ 
/*     */         
/* 278 */         Node location = importNode.getAttributes().getNamedItem("location");
/*     */         
/* 280 */         if (location == null) {
/* 281 */           this.importsToPatch.add(importNode);
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
/*     */   private Source parseAndConvertStream(String address, InputStream stream) {
/*     */     try {
/* 302 */       Transformer xFormer = TransformerFactory.newInstance().newTransformer();
/*     */       
/* 304 */       Source source = new StreamSource(stream);
/* 305 */       DOMResult result = new DOMResult();
/* 306 */       xFormer.transform(source, result);
/* 307 */       Node wsdlDoc = result.getNode();
/* 308 */       source = new DOMSource(wsdlDoc);
/* 309 */       source.setSystemId(getNamespaceFromNode(wsdlDoc.getFirstChild()));
/* 310 */       return source;
/* 311 */     } catch (TransformerException te) {
/* 312 */       String exceptionMessage = MessagesMessages.MEX_0004_TRANSFORMING_FAILURE(address);
/*     */       
/* 314 */       logger.log(Level.SEVERE, exceptionMessage, te);
/* 315 */       throw new WebServiceException(exceptionMessage, te);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void patchImports() throws DOMException {
/* 325 */     for (Node importNode : this.importsToPatch) {
/* 326 */       NamedNodeMap atts = importNode.getAttributes();
/* 327 */       String targetNamespace = atts.getNamedItem("namespace").getNodeValue();
/*     */       
/* 329 */       String sysId = this.nsToSysIdMap.get(targetNamespace);
/* 330 */       if (sysId == null) {
/* 331 */         logger.warning(MessagesMessages.MEX_0005_WSDL_NOT_FOUND_WITH_NAMESPACE(targetNamespace));
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 336 */       Attr locationAtt = importNode.getOwnerDocument().createAttribute("location");
/*     */       
/* 338 */       locationAtt.setValue(sysId);
/* 339 */       atts.setNamedItem(locationAtt);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\client\ServiceDescriptorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */