/*     */ package com.sun.xml.ws.config.metro.dev;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.config.metro.util.ParserUtil;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SimpleFeatureReader<T extends WebServiceFeature>
/*     */   implements FeatureReader
/*     */ {
/*  66 */   private static final Logger LOGGER = Logger.getLogger(SimpleFeatureReader.class);
/*     */   
/*     */   public T parse(XMLEventReader reader) throws WebServiceException {
/*     */     try {
/*  70 */       StartElement element = reader.nextEvent().asStartElement();
/*  71 */       boolean attributeEnabled = true;
/*  72 */       QName elementName = element.getName();
/*  73 */       Iterator<Attribute> iterator = element.getAttributes();
/*  74 */       while (iterator.hasNext()) {
/*  75 */         Attribute nextAttribute = iterator.next();
/*  76 */         QName attributeName = nextAttribute.getName();
/*  77 */         if (ENABLED_ATTRIBUTE_NAME.equals(attributeName)) {
/*  78 */           attributeEnabled = ParserUtil.parseBooleanValue(nextAttribute.getValue());
/*     */           
/*     */           continue;
/*     */         } 
/*  82 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Unexpected attribute, was " + nextAttribute));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  87 */       while (reader.hasNext()) {
/*     */         try {
/*  89 */           EndElement endElement; XMLEvent event = reader.nextEvent();
/*  90 */           switch (event.getEventType()) {
/*     */             case 5:
/*     */               continue;
/*     */             case 4:
/*  94 */               if (event.asCharacters().isWhiteSpace()) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/*  99 */               throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("No character data allowed, was " + event.asCharacters()));
/*     */             
/*     */             case 2:
/* 102 */               endElement = event.asEndElement();
/* 103 */               if (!elementName.equals(endElement.getName()))
/*     */               {
/* 105 */                 throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Expected end element"));
/*     */               }
/*     */               break;
/*     */           } 
/* 109 */           throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Unexpected event, was " + event));
/*     */         }
/* 111 */         catch (XMLStreamException e) {
/*     */           
/* 113 */           throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */         } 
/*     */       } 
/* 116 */       return createFeature(attributeEnabled);
/* 117 */     } catch (XMLStreamException e) {
/*     */       
/* 119 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract T createFeature(boolean paramBoolean) throws WebServiceException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\dev\SimpleFeatureReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */