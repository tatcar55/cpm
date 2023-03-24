/*    */ package com.sun.xml.ws.config.metro.parser;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.config.metro.parser.jsr109.WebserviceDescriptionType;
/*    */ import com.sun.xml.ws.config.metro.parser.jsr109.WebservicesType;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBContext;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.JAXBException;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class WsParser
/*    */ {
/* 62 */   private static final Logger LOGGER = Logger.getLogger(WsParser.class);
/*    */ 
/*    */   
/*    */   private static JAXBContext context;
/*    */ 
/*    */ 
/*    */   
/*    */   public WsParser() throws WebServiceException {
/*    */     try {
/* 71 */       if (context == null) {
/* 72 */         context = JAXBContext.newInstance("com.sun.xml.ws.config.metro.parser.jsr109");
/*    */       }
/* 74 */     } catch (JAXBException e) {
/*    */       
/* 76 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to initialize", e));
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<WebserviceDescriptionType> parse(XMLStreamReader reader) throws WebServiceException {
/*    */     try {
/* 82 */       Unmarshaller unmarshaller = context.createUnmarshaller();
/* 83 */       JAXBElement<WebservicesType> elements = unmarshaller.unmarshal(reader, WebservicesType.class);
/* 84 */       WebservicesType root = elements.getValue();
/* 85 */       List<WebserviceDescriptionType> descriptions = root.getWebserviceDescription();
/* 86 */       return descriptions;
/* 87 */     } catch (JAXBException e) {
/*    */       
/* 89 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal webservices.xml", e));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\WsParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */