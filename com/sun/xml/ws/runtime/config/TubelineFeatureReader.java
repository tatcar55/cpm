/*     */ package com.sun.xml.ws.runtime.config;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.config.metro.dev.FeatureReader;
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
/*     */ public class TubelineFeatureReader
/*     */   implements FeatureReader
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(TubelineFeatureReader.class);
/*  65 */   private static final QName NAME_ATTRIBUTE_NAME = new QName("name");
/*     */ 
/*     */   
/*     */   public TubelineFeature parse(XMLEventReader reader) throws WebServiceException {
/*     */     try {
/*  70 */       StartElement element = reader.nextEvent().asStartElement();
/*  71 */       boolean attributeEnabled = true;
/*  72 */       Iterator<Attribute> iterator = element.getAttributes();
/*  73 */       while (iterator.hasNext()) {
/*  74 */         Attribute nextAttribute = iterator.next();
/*  75 */         QName attributeName = nextAttribute.getName();
/*  76 */         if (ENABLED_ATTRIBUTE_NAME.equals(attributeName)) {
/*  77 */           attributeEnabled = ParserUtil.parseBooleanValue(nextAttribute.getValue()); continue;
/*  78 */         }  if (NAME_ATTRIBUTE_NAME.equals(attributeName)) {
/*     */           continue;
/*     */         }
/*     */         
/*  82 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Unexpected attribute"));
/*     */       } 
/*     */       
/*  85 */       return parseFactories(attributeEnabled, element, reader);
/*  86 */     } catch (XMLStreamException e) {
/*  87 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private TubelineFeature parseFactories(boolean enabled, StartElement element, XMLEventReader reader) throws WebServiceException {
/*  93 */     int elementRead = 0;
/*     */     
/*  95 */     while (reader.hasNext()) {
/*     */       try {
/*  97 */         XMLEvent event = reader.nextEvent();
/*  98 */         switch (event.getEventType()) {
/*     */           case 5:
/*     */             continue;
/*     */           case 4:
/* 102 */             if (event.asCharacters().isWhiteSpace()) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/* 107 */             throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("No character data allowed, was " + event.asCharacters()));
/*     */ 
/*     */           
/*     */           case 1:
/* 111 */             elementRead++;
/*     */             continue;
/*     */           case 2:
/* 114 */             elementRead--;
/* 115 */             if (elementRead < 0) {
/* 116 */               EndElement endElement = event.asEndElement();
/* 117 */               if (!element.getName().equals(endElement.getName()))
/*     */               {
/* 119 */                 throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("End element does not match " + endElement));
/*     */               }
/*     */               break;
/*     */             } 
/*     */             continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 128 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Unexpected event, was " + event));
/*     */       }
/* 130 */       catch (XMLStreamException e) {
/*     */         
/* 132 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 137 */     return new TubelineFeature(enabled);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\config\TubelineFeatureReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */