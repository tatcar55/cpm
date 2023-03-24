/*     */ package com.sun.xml.rpc.client.dii.webservice.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.webservice.WebService;
/*     */ import com.sun.xml.rpc.client.dii.webservice.WebServicesClient;
/*     */ import com.sun.xml.rpc.client.dii.webservice.WebServicesClientException;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderException;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebServicesClientParser
/*     */ {
/*  41 */   private LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.client");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebServicesClient parse(InputStream is) throws WebServicesClientException {
/*     */     try {
/*  50 */       XMLReader reader = XMLReaderFactory.newInstance().createXMLReader(is);
/*     */       
/*  52 */       reader.next();
/*  53 */       return parseWebServicesClient(reader);
/*  54 */     } catch (XMLReaderException e) {
/*  55 */       throw new WebServicesClientException("client.xmlReader", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected WebServicesClient parseWebServicesClient(XMLReader reader) {
/*  60 */     if (!reader.getName().equals(Constants.QNAME_CLIENT)) {
/*  61 */       ParserUtil.failWithFullName("client.invalidElement", reader);
/*     */     }
/*     */     
/*  64 */     WebServicesClient client = new WebServicesClient();
/*  65 */     if (reader.getState() == 1) {
/*  66 */       client.setWebServices(parseWebServices(reader));
/*     */     } else {
/*  68 */       ParserUtil.fail("client.missing.service", reader);
/*     */     } 
/*     */     
/*  71 */     if (reader.nextElementContent() != 5) {
/*  72 */       ParserUtil.fail("client.unexpectedContent", reader);
/*     */     }
/*     */     
/*  75 */     reader.close();
/*  76 */     return client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List parseWebServices(XMLReader reader) {
/*  81 */     List<WebService> webServices = new ArrayList();
/*     */     
/*  83 */     while (reader.nextElementContent() == 1) {
/*     */       
/*  85 */       if (!reader.getName().equals(Constants.QNAME_SERVICE)) {
/*  86 */         ParserUtil.failWithFullName("service.invalidElement", reader);
/*     */       }
/*     */       
/*  89 */       String wsdlLocation = ParserUtil.getAttribute(reader, "wsdlLocation");
/*     */       
/*  91 */       if (wsdlLocation == null) {
/*  92 */         ParserUtil.failWithLocalName("client.invalidwsdlLocation", reader);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  97 */       String model = ParserUtil.getAttribute(reader, "model");
/*     */       
/*  99 */       if (model == null) {
/* 100 */         ParserUtil.failWithLocalName("client.invalidModel", reader);
/*     */       }
/*     */       
/* 103 */       WebService service = new WebService(wsdlLocation, model);
/* 104 */       webServices.add(service);
/*     */       
/* 106 */       if (reader.nextElementContent() != 2) {
/* 107 */         ParserUtil.fail("client.unexpectedContent", reader);
/*     */       }
/*     */     } 
/* 110 */     return webServices;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\webservice\parser\WebServicesClientParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */