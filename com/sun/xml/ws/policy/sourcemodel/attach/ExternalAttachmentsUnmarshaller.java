/*     */ package com.sun.xml.ws.policy.sourcemodel.attach;
/*     */ 
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelTranslator;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelUnmarshaller;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import java.io.Reader;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Characters;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ import javax.xml.stream.events.XMLEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExternalAttachmentsUnmarshaller
/*     */ {
/*  76 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(ExternalAttachmentsUnmarshaller.class);
/*     */   
/*     */   public static final URI BINDING_ID;
/*     */   public static final URI BINDING_OPERATION_ID;
/*     */   public static final URI BINDING_OPERATION_INPUT_ID;
/*     */   public static final URI BINDING_OPERATION_OUTPUT_ID;
/*     */   public static final URI BINDING_OPERATION_FAULT_ID;
/*     */   
/*     */   static {
/*     */     try {
/*  86 */       BINDING_ID = new URI("urn:uuid:c9bef600-0d7a-11de-abc1-0002a5d5c51b");
/*  87 */       BINDING_OPERATION_ID = new URI("urn:uuid:62e66b60-0d7b-11de-a1a2-0002a5d5c51b");
/*  88 */       BINDING_OPERATION_INPUT_ID = new URI("urn:uuid:730d8d20-0d7b-11de-84e9-0002a5d5c51b");
/*  89 */       BINDING_OPERATION_OUTPUT_ID = new URI("urn:uuid:85b0f980-0d7b-11de-8e9d-0002a5d5c51b");
/*  90 */       BINDING_OPERATION_FAULT_ID = new URI("urn:uuid:917cb060-0d7b-11de-9e80-0002a5d5c51b");
/*  91 */     } catch (URISyntaxException e) {
/*  92 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0094_INVALID_URN()), e);
/*     */     } 
/*     */   }
/*     */   
/*  96 */   private static final QName POLICY_ATTACHMENT = new QName("http://www.w3.org/ns/ws-policy", "PolicyAttachment");
/*  97 */   private static final QName APPLIES_TO = new QName("http://www.w3.org/ns/ws-policy", "AppliesTo");
/*  98 */   private static final QName POLICY = new QName("http://www.w3.org/ns/ws-policy", "Policy");
/*  99 */   private static final QName URI = new QName("http://www.w3.org/ns/ws-policy", "URI");
/* 100 */   private static final QName POLICIES = new QName("http://java.sun.com/xml/ns/metro/management", "Policies");
/* 101 */   private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();
/* 102 */   private static final PolicyModelUnmarshaller POLICY_UNMARSHALLER = PolicyModelUnmarshaller.getXmlUnmarshaller();
/*     */   
/* 104 */   private final Map<URI, Policy> map = new HashMap<URI, Policy>();
/* 105 */   private URI currentUri = null;
/* 106 */   private Policy currentPolicy = null;
/*     */   
/*     */   public static Map<URI, Policy> unmarshal(Reader source) throws PolicyException {
/* 109 */     LOGGER.entering(new Object[] { source });
/*     */     try {
/* 111 */       XMLEventReader reader = XML_INPUT_FACTORY.createXMLEventReader(source);
/* 112 */       ExternalAttachmentsUnmarshaller instance = new ExternalAttachmentsUnmarshaller();
/* 113 */       Map<URI, Policy> map = instance.unmarshal(reader, null);
/* 114 */       LOGGER.exiting(map);
/* 115 */       return Collections.unmodifiableMap(map);
/* 116 */     } catch (XMLStreamException ex) {
/* 117 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0086_FAILED_CREATE_READER(source)), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Map<URI, Policy> unmarshal(XMLEventReader reader, StartElement parentElement) throws PolicyException {
/* 122 */     XMLEvent event = null;
/* 123 */     while (reader.hasNext()) {
/*     */       try {
/* 125 */         StartElement element; event = reader.peek();
/* 126 */         switch (event.getEventType()) {
/*     */           case 5:
/*     */           case 7:
/* 129 */             reader.nextEvent();
/*     */             continue;
/*     */           
/*     */           case 4:
/* 133 */             processCharacters(event.asCharacters(), parentElement, this.map);
/* 134 */             reader.nextEvent();
/*     */             continue;
/*     */           
/*     */           case 2:
/* 138 */             processEndTag(event.asEndElement(), parentElement);
/* 139 */             reader.nextEvent();
/* 140 */             return this.map;
/*     */           
/*     */           case 1:
/* 143 */             element = event.asStartElement();
/* 144 */             processStartTag(element, parentElement, reader, this.map);
/*     */             continue;
/*     */           
/*     */           case 8:
/* 148 */             return this.map;
/*     */         } 
/*     */         
/* 151 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0087_UNKNOWN_EVENT(event)));
/*     */       }
/* 153 */       catch (XMLStreamException e) {
/* 154 */         Location location = (event == null) ? null : event.getLocation();
/* 155 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0088_FAILED_PARSE(location)), e);
/*     */       } 
/*     */     } 
/* 158 */     return this.map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processStartTag(StartElement element, StartElement parent, XMLEventReader reader, Map<URI, Policy> map) throws PolicyException {
/*     */     try {
/* 165 */       QName name = element.getName();
/* 166 */       if (parent == null) {
/* 167 */         if (!name.equals(POLICIES)) {
/* 168 */           throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0089_EXPECTED_ELEMENT("<Policies>", name, element.getLocation())));
/*     */         }
/*     */       } else {
/* 171 */         QName parentName = parent.getName();
/* 172 */         if (parentName.equals(POLICIES)) {
/* 173 */           if (!name.equals(POLICY_ATTACHMENT)) {
/* 174 */             throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0089_EXPECTED_ELEMENT("<PolicyAttachment>", name, element.getLocation())));
/*     */           }
/* 176 */         } else if (parentName.equals(POLICY_ATTACHMENT)) {
/* 177 */           if (name.equals(POLICY)) {
/* 178 */             readPolicy(reader); return;
/*     */           } 
/* 180 */           if (!name.equals(APPLIES_TO)) {
/* 181 */             throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0089_EXPECTED_ELEMENT("<AppliesTo> or <Policy>", name, element.getLocation())));
/*     */           }
/* 183 */         } else if (parentName.equals(APPLIES_TO)) {
/* 184 */           if (!name.equals(URI)) {
/* 185 */             throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0089_EXPECTED_ELEMENT("<URI>", name, element.getLocation())));
/*     */           }
/*     */         } else {
/* 188 */           throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0090_UNEXPECTED_ELEMENT(name, element.getLocation())));
/*     */         } 
/*     */       } 
/* 191 */       reader.nextEvent();
/* 192 */       unmarshal(reader, element);
/* 193 */     } catch (XMLStreamException e) {
/* 194 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0088_FAILED_PARSE(element.getLocation()), e));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readPolicy(XMLEventReader reader) throws PolicyException {
/* 199 */     PolicySourceModel policyModel = POLICY_UNMARSHALLER.unmarshalModel(reader);
/* 200 */     PolicyModelTranslator translator = PolicyModelTranslator.getTranslator();
/* 201 */     Policy policy = translator.translate(policyModel);
/* 202 */     if (this.currentUri != null) {
/* 203 */       this.map.put(this.currentUri, policy);
/* 204 */       this.currentUri = null;
/* 205 */       this.currentPolicy = null;
/*     */     } else {
/*     */       
/* 208 */       this.currentPolicy = policy;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processEndTag(EndElement element, StartElement startElement) throws PolicyException {
/* 213 */     checkEndTagName(startElement.getName(), element);
/*     */   }
/*     */   
/*     */   private void checkEndTagName(QName expectedName, EndElement element) throws PolicyException {
/* 217 */     QName actualName = element.getName();
/* 218 */     if (!expectedName.equals(actualName)) {
/* 219 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0091_END_ELEMENT_NO_MATCH(expectedName, element, element.getLocation())));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processCharacters(Characters chars, StartElement currentElement, Map<URI, Policy> map) throws PolicyException {
/* 226 */     if (chars.isWhiteSpace()) {
/*     */       return;
/*     */     }
/*     */     
/* 230 */     String data = chars.getData();
/* 231 */     if (currentElement != null && URI.equals(currentElement.getName())) {
/* 232 */       processUri(chars, map);
/*     */       return;
/*     */     } 
/* 235 */     throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0092_CHARACTER_DATA_UNEXPECTED(currentElement, data, chars.getLocation())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processUri(Characters chars, Map<URI, Policy> map) throws PolicyException {
/* 242 */     String data = chars.getData().trim();
/*     */     try {
/* 244 */       URI uri = new URI(data);
/* 245 */       if (this.currentPolicy != null) {
/* 246 */         map.put(uri, this.currentPolicy);
/* 247 */         this.currentUri = null;
/* 248 */         this.currentPolicy = null;
/*     */       } else {
/* 250 */         this.currentUri = uri;
/*     */       } 
/* 252 */     } catch (URISyntaxException e) {
/* 253 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0093_INVALID_URI(data, chars.getLocation())), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\attach\ExternalAttachmentsUnmarshaller.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */