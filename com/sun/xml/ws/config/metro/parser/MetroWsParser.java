/*     */ package com.sun.xml.ws.config.metro.parser;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.config.metro.dev.ElementFeatureMapping;
/*     */ import com.sun.xml.ws.config.metro.dev.FeatureReader;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
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
/*     */ class MetroWsParser
/*     */ {
/*  75 */   private static final Logger LOGGER = Logger.getLogger(MetroWsParser.class);
/*     */   
/*  77 */   private static final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
/*     */   
/*     */   private static final String CONFIG_NAMESPACE = "http://metro.dev.java.net/xmlns/metro-webservices";
/*  80 */   private static final QName CONFIG_ROOT_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "metro-webservices");
/*  81 */   private static final QName PORT_COMPONENT_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "port-component");
/*  82 */   private static final QName PORT_COMPONENT_REF_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "port-component-ref");
/*  83 */   private static final QName OPERATION_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "operation");
/*  84 */   private static final QName INPUT_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "input");
/*  85 */   private static final QName OUTPUT_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "output");
/*  86 */   private static final QName FAULT_ELEMENT = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "fault");
/*  87 */   private static final QName NAME_ATTRIBUTE = new QName("name");
/*  88 */   private static final QName WSDL_NAME_ATTRIBUTE = new QName("wsdl-name");
/*     */   
/*  90 */   private static final QName TCP_TRANSPORT_ELEMENT_NAME = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "tcp-transport");
/*  91 */   private static final QName TUBELINE_ELEMENT_NAME = new QName("http://metro.dev.java.net/xmlns/metro-webservices", "tubeline");
/*     */   
/*  93 */   private static final Map<QName, FeatureReader<?>> nameToReader = new HashMap<QName, FeatureReader<?>>();
/*     */   
/*     */   static {
/*     */     try {
/*  97 */       nameToReader.put(NamespaceVersion.v1_5.asQName(XmlToken.Policy), instantiateFeatureReader("com.sun.xml.ws.policy.config.PolicyFeatureReader"));
/*     */       
/*  99 */       nameToReader.put(TCP_TRANSPORT_ELEMENT_NAME, instantiateFeatureReader("com.sun.xml.ws.transport.tcp.dev.TcpTransportFeatureReader"));
/*     */       
/* 101 */       nameToReader.put(TUBELINE_ELEMENT_NAME, instantiateFeatureReader("com.sun.xml.ws.runtime.config.TubelineFeatureReader"));
/*     */ 
/*     */       
/* 104 */       ElementFeatureMapping[] elementFeatureMappings = (ElementFeatureMapping[])PolicyUtils.ServiceProvider.load(ElementFeatureMapping.class);
/* 105 */       if (elementFeatureMappings != null) {
/* 106 */         for (int i = 0; i < elementFeatureMappings.length; i++) {
/* 107 */           ElementFeatureMapping elementFeatureMapping = elementFeatureMappings[i];
/* 108 */           QName elementName = elementFeatureMapping.getElementName();
/* 109 */           if (nameToReader.containsKey(elementName))
/*     */           {
/* 111 */             throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("duplicate registration of reader ... for element ..."));
/*     */           }
/* 113 */           nameToReader.put(elementName, elementFeatureMapping.getFeatureReader());
/*     */         } 
/*     */       }
/* 116 */     } catch (ClassNotFoundException ex) {
/*     */       
/* 118 */       LOGGER.logSevereException(new WebServiceException("Failed to initialize feature readers", ex));
/* 119 */     } catch (InstantiationException ex) {
/*     */       
/* 121 */       LOGGER.logSevereException(new WebServiceException("Failed to initialize feature readers", ex));
/* 122 */     } catch (IllegalAccessException ex) {
/*     */       
/* 124 */       LOGGER.logSevereException(new WebServiceException("Failed to initialize feature readers", ex));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static FeatureReader instantiateFeatureReader(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
/* 130 */     return Class.forName(className).<FeatureReader>asSubclass(FeatureReader.class).newInstance();
/*     */   }
/*     */   
/*     */   public MetroWsParser() throws WebServiceException {
/* 134 */     if (nameToReader == null || nameToReader.isEmpty())
/*     */     {
/* 136 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to initialize feature readers"));
/*     */     }
/*     */   }
/*     */   
/*     */   public List<ParsedElement> unmarshal(XMLStreamReader reader) throws WebServiceException {
/*     */     try {
/* 142 */       XMLEventReader eventReader = inputFactory.createXMLEventReader(reader);
/* 143 */       return unmarshal(eventReader);
/* 144 */     } catch (XMLStreamException e) {
/*     */       
/* 146 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(e));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List<ParsedElement> unmarshal(XMLEventReader reader) throws WebServiceException {
/* 151 */     List<ParsedElement> configElements = new LinkedList<ParsedElement>();
/*     */     
/* 153 */     while (reader.hasNext()) {
/*     */       try {
/* 155 */         XMLEvent event = reader.nextEvent();
/* 156 */         switch (event.getEventType()) {
/*     */           case 5:
/*     */           case 7:
/*     */             continue;
/*     */           case 4:
/* 161 */             processCharacters(event.asCharacters(), null);
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 1:
/* 166 */             if (CONFIG_ROOT_ELEMENT.equals(event.asStartElement().getName())) {
/* 167 */               unmarshalComponents(configElements, reader);
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/* 172 */             throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("metro-webservice element expected, instead got " + event));
/*     */         } 
/*     */         
/* 175 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("metro-webservice element expected, instead got " + event));
/*     */       }
/* 177 */       catch (XMLStreamException e) {
/*     */         
/* 179 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
/*     */       } 
/*     */     } 
/* 182 */     return configElements;
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalComponents(final List<ParsedElement> configElements, XMLEventReader reader) throws WebServiceException {
/* 187 */     unmarshal(configElements, CONFIG_ROOT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/*     */             try {
/* 190 */               StartElement element = reader.peek().asStartElement();
/* 191 */               if (MetroWsParser.PORT_COMPONENT_ELEMENT.equals(element.getName())) {
/* 192 */                 Attribute nameAttribute = element.getAttributeByName(MetroWsParser.NAME_ATTRIBUTE);
/* 193 */                 if (nameAttribute != null) {
/* 194 */                   reader.next();
/* 195 */                   MetroWsParser.this.unmarshalPortComponent(configElements, nameAttribute.getValue(), reader);
/*     */                 }
/*     */                 else {
/*     */                   
/* 199 */                   throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Expected name attribute"));
/*     */                 }
/*     */               
/* 202 */               } else if (MetroWsParser.PORT_COMPONENT_REF_ELEMENT.equals(element.getName())) {
/* 203 */                 Attribute nameAttribute = element.getAttributeByName(MetroWsParser.NAME_ATTRIBUTE);
/* 204 */                 if (nameAttribute != null) {
/* 205 */                   reader.next();
/* 206 */                   MetroWsParser.this.unmarshalPortComponentRef(configElements, nameAttribute.getValue(), reader);
/*     */                 }
/*     */                 else {
/*     */                   
/* 210 */                   throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Expected name attribute"));
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 215 */                 throw new WebServiceException("Expected component element, got " + element);
/*     */               } 
/* 217 */             } catch (XMLStreamException e) {
/* 218 */               throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Failed to unmarshal", e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponent(final List<ParsedElement> configElements, final String componentName, XMLEventReader reader) throws WebServiceException {
/* 226 */     unmarshal(configElements, PORT_COMPONENT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/*     */             try {
/* 229 */               StartElement element = reader.peek().asStartElement();
/* 230 */               if (MetroWsParser.OPERATION_ELEMENT.equals(element.getName())) {
/* 231 */                 Attribute nameAttribute = element.getAttributeByName(MetroWsParser.WSDL_NAME_ATTRIBUTE);
/* 232 */                 if (nameAttribute != null) {
/* 233 */                   reader.next();
/* 234 */                   MetroWsParser.this.unmarshalPortComponentOperation(configElements, componentName, nameAttribute.getValue(), reader);
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 239 */                   throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Expected wsdl-name attribute"));
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 244 */                 WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 245 */                 configElements.add(ParsedElement.createPortComponentElement(componentName, feature));
/*     */               } 
/* 247 */             } catch (XMLStreamException e) {
/* 248 */               throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Failed to unmarshal", e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentRef(final List<ParsedElement> configElements, final String componentName, XMLEventReader reader) throws WebServiceException {
/* 256 */     unmarshal(configElements, PORT_COMPONENT_REF_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/*     */             try {
/* 259 */               StartElement element = reader.peek().asStartElement();
/* 260 */               if (MetroWsParser.OPERATION_ELEMENT.equals(element.getName())) {
/* 261 */                 Attribute nameAttribute = element.getAttributeByName(MetroWsParser.WSDL_NAME_ATTRIBUTE);
/* 262 */                 if (nameAttribute != null) {
/* 263 */                   reader.next();
/* 264 */                   MetroWsParser.this.unmarshalPortComponentRefOperation(configElements, componentName, nameAttribute.getValue(), reader);
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 269 */                   throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Expected wsdl-name attribute"));
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 274 */                 WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 275 */                 configElements.add(ParsedElement.createPortComponentRefElement(componentName, feature));
/*     */               } 
/* 277 */             } catch (XMLStreamException e) {
/* 278 */               throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Failed to unmarshal", e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentOperation(final List<ParsedElement> configElements, final String componentName, final String operationName, XMLEventReader reader) throws WebServiceException {
/* 286 */     unmarshal(configElements, OPERATION_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/*     */             try {
/* 289 */               StartElement element = reader.peek().asStartElement();
/* 290 */               QName childName = element.getName();
/* 291 */               if (MetroWsParser.INPUT_ELEMENT.equals(childName)) {
/* 292 */                 reader.next();
/* 293 */                 MetroWsParser.this.unmarshalPortComponentInput(configElements, componentName, operationName, reader);
/*     */               }
/* 295 */               else if (MetroWsParser.OUTPUT_ELEMENT.equals(childName)) {
/* 296 */                 reader.next();
/* 297 */                 MetroWsParser.this.unmarshalPortComponentOutput(configElements, componentName, operationName, reader);
/*     */               }
/* 299 */               else if (MetroWsParser.FAULT_ELEMENT.equals(childName)) {
/* 300 */                 Attribute nameAttribute = element.getAttributeByName(MetroWsParser.WSDL_NAME_ATTRIBUTE);
/* 301 */                 if (nameAttribute != null) {
/* 302 */                   reader.next();
/* 303 */                   MetroWsParser.this.unmarshalPortComponentFault(configElements, componentName, operationName, nameAttribute.getValue(), reader);
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 308 */                   throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Expected wsdl-name attribute"));
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 313 */                 WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 314 */                 configElements.add(ParsedElement.createPortComponentOperationElement(componentName, operationName, feature));
/*     */               }
/*     */             
/* 317 */             } catch (XMLStreamException e) {
/* 318 */               throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Failed to unmarshal", e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentRefOperation(final List<ParsedElement> configElements, final String componentName, final String operationName, XMLEventReader reader) throws WebServiceException {
/* 326 */     unmarshal(configElements, OPERATION_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/*     */             try {
/* 329 */               StartElement element = reader.peek().asStartElement();
/* 330 */               QName childName = element.getName();
/* 331 */               if (MetroWsParser.INPUT_ELEMENT.equals(childName)) {
/* 332 */                 reader.next();
/* 333 */                 MetroWsParser.this.unmarshalPortComponentRefInput(configElements, componentName, operationName, reader);
/*     */               }
/* 335 */               else if (MetroWsParser.OUTPUT_ELEMENT.equals(childName)) {
/* 336 */                 reader.next();
/* 337 */                 MetroWsParser.this.unmarshalPortComponentRefOutput(configElements, componentName, operationName, reader);
/*     */               }
/* 339 */               else if (MetroWsParser.FAULT_ELEMENT.equals(childName)) {
/* 340 */                 Attribute nameAttribute = element.getAttributeByName(MetroWsParser.WSDL_NAME_ATTRIBUTE);
/* 341 */                 if (nameAttribute != null) {
/* 342 */                   reader.next();
/* 343 */                   MetroWsParser.this.unmarshalPortComponentRefFault(configElements, componentName, operationName, nameAttribute.getValue(), reader);
/*     */                 
/*     */                 }
/*     */                 else {
/*     */                   
/* 348 */                   throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Expected wsdl-name attribute"));
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 353 */                 WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 354 */                 configElements.add(ParsedElement.createPortComponentRefOperationElement(componentName, operationName, feature));
/*     */               }
/*     */             
/* 357 */             } catch (XMLStreamException e) {
/* 358 */               throw (WebServiceException)MetroWsParser.LOGGER.logSevereException(new WebServiceException("Failed to unmarshal", e));
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentInput(final List<ParsedElement> configElements, final String componentName, final String operationName, XMLEventReader reader) throws WebServiceException {
/* 366 */     unmarshal(configElements, INPUT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/* 368 */             WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 369 */             configElements.add(ParsedElement.createPortComponentInputElement(componentName, operationName, feature));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentOutput(final List<ParsedElement> configElements, final String componentName, final String operationName, XMLEventReader reader) throws WebServiceException {
/* 377 */     unmarshal(configElements, OUTPUT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/* 379 */             WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 380 */             configElements.add(ParsedElement.createPortComponentOutputElement(componentName, operationName, feature));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentFault(final List<ParsedElement> configElements, final String componentName, final String operationName, final String faultName, XMLEventReader reader) throws WebServiceException {
/* 388 */     unmarshal(configElements, FAULT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/* 390 */             WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 391 */             configElements.add(ParsedElement.createPortComponentFaultElement(componentName, operationName, faultName, feature));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentRefInput(final List<ParsedElement> configElements, final String componentName, final String operationName, XMLEventReader reader) throws WebServiceException {
/* 399 */     unmarshal(configElements, INPUT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/* 401 */             WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 402 */             configElements.add(ParsedElement.createPortComponentRefInputElement(componentName, operationName, feature));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentRefOutput(final List<ParsedElement> configElements, final String componentName, final String operationName, XMLEventReader reader) throws WebServiceException {
/* 410 */     unmarshal(configElements, OUTPUT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/* 412 */             WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 413 */             configElements.add(ParsedElement.createPortComponentRefOutputElement(componentName, operationName, feature));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshalPortComponentRefFault(final List<ParsedElement> configElements, final String componentName, final String operationName, final String faultName, XMLEventReader reader) throws WebServiceException {
/* 421 */     unmarshal(configElements, FAULT_ELEMENT, reader, new ElementParser() {
/*     */           public void parse(XMLEventReader reader) {
/* 423 */             WebServiceFeature feature = MetroWsParser.this.parseElement(reader);
/* 424 */             configElements.add(ParsedElement.createPortComponentRefFaultElement(componentName, operationName, faultName, feature));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void unmarshal(List<ParsedElement> configElements, QName endTag, XMLEventReader reader, ElementParser parser) throws WebServiceException {
/* 433 */     while (reader.hasNext()) {
/*     */       try {
/* 435 */         XMLEvent xmlParserEvent = reader.peek();
/* 436 */         switch (xmlParserEvent.getEventType()) {
/*     */           case 5:
/* 438 */             reader.next();
/*     */             continue;
/*     */           case 4:
/* 441 */             processCharacters(reader.nextEvent().asCharacters(), null);
/*     */             continue;
/*     */           case 2:
/* 444 */             checkEndTagName(endTag, reader.nextEvent().asEndElement());
/*     */             break;
/*     */           case 1:
/* 447 */             parser.parse(reader);
/*     */             continue;
/*     */         } 
/*     */         
/* 451 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("expected XML element"));
/*     */       }
/* 453 */       catch (XMLStreamException e) {
/* 454 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("unmarshalling failed", e));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private WebServiceFeature parseElement(XMLEventReader reader) throws WebServiceException {
/*     */     try {
/* 462 */       StartElement element = reader.peek().asStartElement();
/* 463 */       QName elementName = element.getName();
/* 464 */       FeatureReader featureReader = nameToReader.get(elementName);
/* 465 */       if (featureReader != null) {
/* 466 */         return featureReader.parse(reader);
/*     */       }
/*     */ 
/*     */       
/* 470 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("unknown element " + element));
/*     */     }
/* 472 */     catch (XMLStreamException e) {
/*     */       
/* 474 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("failed to parse", e));
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
/*     */   private void checkEndTagName(QName expected, EndElement element) throws WebServiceException {
/* 487 */     QName actual = element.getName();
/* 488 */     if (!expected.equals(actual))
/*     */     {
/* 490 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("end tag does not match start tag"));
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
/*     */   private StringBuilder processCharacters(Characters characters, StringBuilder currentValueBuffer) throws WebServiceException {
/* 505 */     if (characters.isWhiteSpace()) {
/* 506 */       return currentValueBuffer;
/*     */     }
/*     */ 
/*     */     
/* 510 */     throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("No character data allowed"));
/*     */   }
/*     */   
/*     */   private static interface ElementParser {
/*     */     void parse(XMLEventReader param1XMLEventReader) throws WebServiceException;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\MetroWsParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */