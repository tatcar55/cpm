/*     */ package com.sun.xml.ws.policy.parser;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.api.wsdl.parser.XMLEntityResolver;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.streaming.TidyXMLStreamReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyResourceLoader
/*     */ {
/*     */   public static WSDLModel getWsdlModel(URL resourceUrl, boolean isClient) throws IOException, XMLStreamException, SAXException {
/*  83 */     SDDocumentSource doc = SDDocumentSource.create(resourceUrl);
/*  84 */     XMLEntityResolver.Parser parser = new XMLEntityResolver.Parser(doc);
/*  85 */     WSDLModel model = WSDLModel.WSDLParser.parse(parser, new PolicyEntityResolver(), isClient, Container.NONE, PolicyResolverFactory.DEFAULT_POLICY_RESOLVER, new com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PolicyEntityResolver
/*     */     implements XMLEntityResolver
/*     */   {
/*     */     private PolicyEntityResolver() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     private static final Logger LOGGER = Logger.getLogger(PolicyEntityResolver.class);
/* 106 */     private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public XMLEntityResolver.Parser resolveEntity(String publicId, String systemId) throws XMLStreamException, IOException {
/* 124 */       LOGGER.entering(new Object[] { publicId, systemId });
/* 125 */       XMLEntityResolver.Parser parser = null;
/*     */ 
/*     */       
/*     */       try {
/* 129 */         URL systemUrl = new URL(PolicyUtils.Rfc2396.unquote(systemId));
/* 130 */         InputStream input = systemUrl.openStream();
/* 131 */         TidyXMLStreamReader tidyXMLStreamReader = new TidyXMLStreamReader(xmlInputFactory.createXMLStreamReader(systemId, input), input);
/*     */         
/* 133 */         parser = new XMLEntityResolver.Parser(systemUrl, (XMLStreamReader)tidyXMLStreamReader);
/* 134 */         return parser;
/*     */       } finally {
/* 136 */         LOGGER.exiting(parser);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\parser\PolicyResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */