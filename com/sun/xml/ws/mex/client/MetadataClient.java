/*     */ package com.sun.xml.ws.mex.client;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
/*     */ import com.sun.xml.ws.mex.MetadataConstants;
/*     */ import com.sun.xml.ws.mex.client.schema.Metadata;
/*     */ import com.sun.xml.ws.mex.client.schema.MetadataReference;
/*     */ import com.sun.xml.ws.mex.client.schema.MetadataSection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public class MetadataClient
/*     */ {
/*     */   enum Protocol
/*     */   {
/*  86 */     SOAP_1_2, SOAP_1_1;
/*     */   }
/*  88 */   private final String[] suffixes = new String[] { "", "/mex" };
/*     */   
/*     */   private final MetadataUtil mexUtil;
/*     */   private static final JAXBContext jaxbContext;
/*  92 */   private static final Logger logger = Logger.getLogger(MetadataClient.class.getName());
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  97 */       jaxbContext = JAXBContext.newInstance("com.sun.xml.ws.mex.client.schema");
/*     */     }
/*  99 */     catch (JAXBException jaxbE) {
/* 100 */       throw new AssertionError(jaxbE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetadataClient() {
/* 108 */     this.mexUtil = new MetadataUtil();
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
/*     */   
/*     */   public Metadata retrieveMetadata(@NotNull String address) {
/* 128 */     for (String suffix : this.suffixes) {
/* 129 */       String newAddress = address.concat(suffix);
/* 130 */       for (Protocol p : Protocol.values()) {
/* 131 */         InputStream responseStream = null;
/*     */         try {
/* 133 */           responseStream = this.mexUtil.getMetadata(newAddress, p);
/* 134 */           return createMetadata(responseStream);
/* 135 */         } catch (IOException e) {
/* 136 */           logger.log(MetadataConstants.ERROR_LOG_LEVEL, MessagesMessages.MEX_0006_RETRIEVING_MDATA_FAILURE(p, newAddress));
/*     */ 
/*     */         
/*     */         }
/* 140 */         catch (Exception e) {
/* 141 */           logger.log(Level.WARNING, MessagesMessages.MEX_0008_PARSING_MDATA_FAILURE(p, newAddress));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 148 */     logger.log(MetadataConstants.ERROR_LOG_LEVEL, MessagesMessages.MEX_0007_RETURNING_NULL_MDATA());
/*     */     
/* 150 */     return null;
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
/*     */   public Metadata retrieveMetadata(@NotNull MetadataReference reference) {
/* 163 */     List nodes = reference.getAny();
/* 164 */     for (Object o : nodes) {
/* 165 */       Node node = (Node)o;
/* 166 */       if (node.getLocalName().equals("Address")) {
/* 167 */         String address = node.getFirstChild().getNodeValue();
/* 168 */         return retrieveMetadata(address);
/*     */       } 
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PortInfo> getServiceInformation(@NotNull Metadata data) {
/* 182 */     List<PortInfo> portInfos = new ArrayList<PortInfo>();
/* 183 */     for (MetadataSection section : data.getMetadataSection()) {
/* 184 */       if (section.getDialect().equals("http://schemas.xmlsoap.org/wsdl/")) {
/* 185 */         if (section.getAny() != null) {
/* 186 */           getServiceInformationFromNode(portInfos, section.getAny());
/*     */         }
/* 188 */         if (section.getMetadataReference() != null) {
/* 189 */           Metadata newMetadata = retrieveMetadata(section.getMetadataReference());
/*     */           
/* 191 */           List<PortInfo> newPortInfos = getServiceInformation(newMetadata);
/* 192 */           portInfos.addAll(newPortInfos);
/*     */         } 
/* 194 */         if (section.getLocation() != null) {
/* 195 */           Metadata newMetadata = retrieveMetadata(section.getLocation());
/*     */           
/* 197 */           List<PortInfo> newPortInfos = getServiceInformation(newMetadata);
/* 198 */           portInfos.addAll(newPortInfos);
/*     */         } 
/*     */       } 
/*     */     } 
/* 202 */     return portInfos;
/*     */   }
/*     */ 
/*     */   
/*     */   private void getServiceInformationFromNode(List<PortInfo> portInfos, Object node) {
/* 207 */     Node wsdlNode = (Node)node;
/* 208 */     String namespace = getAttributeValue(wsdlNode, "targetNamespace");
/* 209 */     NodeList nodes = wsdlNode.getChildNodes();
/* 210 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 211 */       Node serviceNode = nodes.item(i);
/* 212 */       if (serviceNode.getLocalName() != null && serviceNode.getLocalName().equals("service")) {
/*     */ 
/*     */         
/* 215 */         Node nameAtt = serviceNode.getAttributes().getNamedItem("name");
/* 216 */         QName serviceName = new QName(namespace, nameAtt.getNodeValue());
/*     */         
/* 218 */         NodeList portNodes = serviceNode.getChildNodes();
/* 219 */         for (int j = 0; j < portNodes.getLength(); j++) {
/* 220 */           Node portNode = portNodes.item(j);
/* 221 */           if (portNode.getLocalName() != null && portNode.getLocalName().equals("port")) {
/*     */ 
/*     */             
/* 224 */             QName portName = new QName(namespace, getAttributeValue(portNode, "name"));
/*     */             
/* 226 */             String address = getPortAddress(portNode);
/* 227 */             portInfos.add(new PortInfo(serviceName, portName, address));
/*     */           } 
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
/*     */   private Metadata createMetadata(InputStream stream) throws XMLStreamException, JAXBException {
/* 244 */     XMLInputFactory factory = XMLInputFactory.newInstance();
/* 245 */     XMLStreamReader reader = factory.createXMLStreamReader(stream);
/*     */     
/* 247 */     int state = 0;
/*     */     do {
/* 249 */       state = reader.next();
/* 250 */     } while (state != 1 || !reader.getLocalName().equals("Metadata"));
/*     */ 
/*     */     
/* 253 */     Unmarshaller uMarhaller = jaxbContext.createUnmarshaller();
/* 254 */     Metadata mData = (Metadata)uMarhaller.unmarshal(reader);
/* 255 */     cleanData(mData);
/* 256 */     return mData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAttributeValue(Node node, String attName) {
/* 263 */     return node.getAttributes().getNamedItem(attName).getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPortAddress(Node portNode) {
/* 271 */     NodeList portDetails = portNode.getChildNodes();
/* 272 */     for (int i = 0; i < portDetails.getLength(); i++) {
/* 273 */       Node addressNode = portDetails.item(i);
/* 274 */       if (addressNode.getLocalName() != null && addressNode.getLocalName().equals("address"))
/*     */       {
/*     */         
/* 277 */         return getAttributeValue(addressNode, "location");
/*     */       }
/*     */     } 
/* 280 */     logger.warning(MessagesMessages.MEX_0009_ADDRESS_NOT_FOUND_FOR_PORT(portNode));
/*     */     
/* 282 */     return null;
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
/*     */   private void cleanData(Metadata mData) {
/* 294 */     for (MetadataSection section : mData.getMetadataSection()) {
/* 295 */       if (section.getDialect().equals("http://schemas.xmlsoap.org/wsdl/") && section.getAny() != null) {
/*     */         
/* 297 */         cleanWSDLNode((Node)section.getAny()); continue;
/*     */       } 
/* 299 */       if (section.getDialect().equals("http://www.w3.org/2001/XMLSchema") && section.getAny() != null)
/*     */       {
/* 301 */         cleanSchemaNode((Node)section.getAny());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanWSDLNode(Node wsdlNode) {
/* 310 */     NodeList nodes = wsdlNode.getChildNodes();
/* 311 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 312 */       Node node = nodes.item(i);
/* 313 */       if (node.getLocalName() != null) {
/* 314 */         if (node.getLocalName().equals("types")) {
/* 315 */           NodeList schemaNodes = node.getChildNodes();
/* 316 */           for (int j = 0; j < schemaNodes.getLength(); j++) {
/* 317 */             Node schemaNode = schemaNodes.item(j);
/* 318 */             if (schemaNode.getLocalName() != null && schemaNode.getLocalName().equals("schema"))
/*     */             {
/*     */               
/* 321 */               cleanSchemaNode(schemaNode);
/*     */             }
/*     */           } 
/* 324 */         } else if (node.getLocalName().equals("import")) {
/* 325 */           cleanImport(node);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanSchemaNode(Node schemaNode) {
/* 332 */     NodeList children = schemaNode.getChildNodes();
/* 333 */     for (int i = 0; i < children.getLength(); i++) {
/* 334 */       Node importNode = children.item(i);
/* 335 */       if (importNode.getLocalName() != null && importNode.getLocalName().equals("import"))
/*     */       {
/* 337 */         cleanImport(importNode);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanImport(Node importNode) {
/* 348 */     NamedNodeMap atts = importNode.getAttributes();
/* 349 */     Node location = atts.getNamedItem("schemaLocation");
/* 350 */     if (location != null && location.getNodeValue().equals("")) {
/*     */       
/* 352 */       atts.removeNamedItem("schemaLocation");
/*     */       return;
/*     */     } 
/* 355 */     location = atts.getNamedItem("location");
/* 356 */     if (location != null && location.getNodeValue().equals(""))
/*     */     {
/* 358 */       atts.removeNamedItem("location");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\client\MetadataClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */