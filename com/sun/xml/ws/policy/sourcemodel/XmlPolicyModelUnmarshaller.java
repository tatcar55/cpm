/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyConstants;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import java.io.Reader;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventReader;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
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
/*     */ public class XmlPolicyModelUnmarshaller
/*     */   extends PolicyModelUnmarshaller
/*     */ {
/*  75 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(XmlPolicyModelUnmarshaller.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicySourceModel unmarshalModel(Object storage) throws PolicyException {
/*  88 */     XMLEventReader reader = createXMLEventReader(storage);
/*  89 */     PolicySourceModel model = null;
/*     */ 
/*     */     
/*  92 */     while (reader.hasNext()) {
/*     */       try {
/*  94 */         XMLEvent event = reader.peek();
/*  95 */         switch (event.getEventType()) {
/*     */           case 5:
/*     */           case 7:
/*  98 */             reader.nextEvent();
/*     */             continue;
/*     */           case 4:
/* 101 */             processCharacters(ModelNode.Type.POLICY, event.asCharacters(), null);
/*     */ 
/*     */             
/* 104 */             reader.nextEvent();
/*     */             continue;
/*     */           case 1:
/* 107 */             if (NamespaceVersion.resolveAsToken(event.asStartElement().getName()) == XmlToken.Policy) {
/* 108 */               StartElement rootElement = reader.nextEvent().asStartElement();
/*     */               
/* 110 */               model = initializeNewModel(rootElement);
/* 111 */               unmarshalNodeContent(model.getNamespaceVersion(), model.getRootNode(), rootElement.getName(), reader);
/*     */               
/*     */               break;
/*     */             } 
/* 115 */             throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0048_POLICY_ELEMENT_EXPECTED_FIRST()));
/*     */         } 
/*     */         
/* 118 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0048_POLICY_ELEMENT_EXPECTED_FIRST()));
/*     */       }
/* 120 */       catch (XMLStreamException e) {
/* 121 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0068_FAILED_TO_UNMARSHALL_POLICY_EXPRESSION(), e));
/*     */       } 
/*     */     } 
/* 124 */     return model;
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
/*     */   protected PolicySourceModel createSourceModel(NamespaceVersion nsVersion, String id, String name) {
/* 136 */     return PolicySourceModel.createPolicySourceModel(nsVersion, id, name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicySourceModel initializeNewModel(StartElement element) throws PolicyException, XMLStreamException {
/* 142 */     NamespaceVersion nsVersion = NamespaceVersion.resolveVersion(element.getName().getNamespaceURI());
/*     */     
/* 144 */     Attribute policyName = getAttributeByName(element, nsVersion.asQName(XmlToken.Name));
/* 145 */     Attribute xmlId = getAttributeByName(element, PolicyConstants.XML_ID);
/* 146 */     Attribute policyId = getAttributeByName(element, PolicyConstants.WSU_ID);
/*     */     
/* 148 */     if (policyId == null) {
/* 149 */       policyId = xmlId;
/* 150 */     } else if (xmlId != null) {
/* 151 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0058_MULTIPLE_POLICY_IDS_NOT_ALLOWED()));
/*     */     } 
/*     */     
/* 154 */     PolicySourceModel model = createSourceModel(nsVersion, (policyId == null) ? null : policyId.getValue(), (policyName == null) ? null : policyName.getValue());
/*     */ 
/*     */ 
/*     */     
/* 158 */     return model;
/*     */   }
/*     */   
/*     */   private ModelNode addNewChildNode(NamespaceVersion nsVersion, ModelNode parentNode, StartElement childElement) throws PolicyException {
/*     */     ModelNode childNode;
/* 163 */     QName childElementName = childElement.getName();
/* 164 */     if (parentNode.getType() == ModelNode.Type.ASSERTION_PARAMETER_NODE)
/* 165 */     { childNode = parentNode.createChildAssertionParameterNode(); }
/*     */     else
/* 167 */     { Attribute uri; XmlToken token = NamespaceVersion.resolveAsToken(childElementName);
/*     */       
/* 169 */       switch (token)
/*     */       { case Policy:
/* 171 */           childNode = parentNode.createChildPolicyNode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 213 */           return childNode;case All: childNode = parentNode.createChildAllNode(); return childNode;case ExactlyOne: childNode = parentNode.createChildExactlyOneNode(); return childNode;case PolicyReference: uri = getAttributeByName(childElement, nsVersion.asQName(XmlToken.Uri)); if (uri == null) throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0040_POLICY_REFERENCE_URI_ATTR_NOT_FOUND()));  try { PolicyReferenceData refData; URI reference = new URI(uri.getValue()); Attribute digest = getAttributeByName(childElement, nsVersion.asQName(XmlToken.Digest)); if (digest == null) { refData = new PolicyReferenceData(reference); } else { Attribute digestAlgorithm = getAttributeByName(childElement, nsVersion.asQName(XmlToken.DigestAlgorithm)); URI algorithmRef = null; if (digestAlgorithm != null) algorithmRef = new URI(digestAlgorithm.getValue());  refData = new PolicyReferenceData(reference, digest.getValue(), algorithmRef); }  childNode = parentNode.createChildPolicyReferenceNode(refData); } catch (URISyntaxException e) { throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0012_UNABLE_TO_UNMARSHALL_POLICY_MALFORMED_URI(), e)); }  return childNode; }  if (parentNode.isDomainSpecific()) { childNode = parentNode.createChildAssertionParameterNode(); } else { childNode = parentNode.createChildAssertionNode(); }  }  return childNode;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseAssertionData(NamespaceVersion nsVersion, String value, ModelNode childNode, StartElement childElement) throws IllegalArgumentException, PolicyException {
/* 218 */     Map<QName, String> attributeMap = new HashMap<QName, String>();
/* 219 */     boolean optional = false;
/* 220 */     boolean ignorable = false;
/*     */     
/* 222 */     Iterator<Attribute> iterator = childElement.getAttributes();
/* 223 */     while (iterator.hasNext()) {
/* 224 */       Attribute nextAttribute = iterator.next();
/* 225 */       QName name = nextAttribute.getName();
/* 226 */       if (attributeMap.containsKey(name)) {
/* 227 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0059_MULTIPLE_ATTRS_WITH_SAME_NAME_DETECTED_FOR_ASSERTION(nextAttribute.getName(), childElement.getName())));
/*     */       }
/* 229 */       if (nsVersion.asQName(XmlToken.Optional).equals(name)) {
/* 230 */         optional = parseBooleanValue(nextAttribute.getValue()); continue;
/* 231 */       }  if (nsVersion.asQName(XmlToken.Ignorable).equals(name)) {
/* 232 */         ignorable = parseBooleanValue(nextAttribute.getValue()); continue;
/*     */       } 
/* 234 */       attributeMap.put(name, nextAttribute.getValue());
/*     */     } 
/*     */ 
/*     */     
/* 238 */     AssertionData nodeData = new AssertionData(childElement.getName(), value, attributeMap, childNode.getType(), optional, ignorable);
/*     */ 
/*     */     
/* 241 */     if (nodeData.containsAttribute(PolicyConstants.VISIBILITY_ATTRIBUTE)) {
/* 242 */       String visibilityValue = nodeData.getAttributeValue(PolicyConstants.VISIBILITY_ATTRIBUTE);
/* 243 */       if (!"private".equals(visibilityValue)) {
/* 244 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0004_UNEXPECTED_VISIBILITY_ATTR_VALUE(visibilityValue)));
/*     */       }
/*     */     } 
/*     */     
/* 248 */     childNode.setOrReplaceNodeData(nodeData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Attribute getAttributeByName(StartElement element, QName attributeName) {
/* 254 */     Attribute attribute = element.getAttributeByName(attributeName);
/*     */ 
/*     */     
/* 257 */     if (attribute == null) {
/* 258 */       String localAttributeName = attributeName.getLocalPart();
/* 259 */       Iterator<Attribute> iterator = element.getAttributes();
/* 260 */       while (iterator.hasNext()) {
/* 261 */         Attribute nextAttribute = iterator.next();
/* 262 */         QName aName = nextAttribute.getName();
/* 263 */         boolean attributeFoundByWorkaround = (aName.equals(attributeName) || (aName.getLocalPart().equals(localAttributeName) && (aName.getPrefix() == null || "".equals(aName.getPrefix()))));
/* 264 */         if (attributeFoundByWorkaround) {
/* 265 */           attribute = nextAttribute;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 272 */     return attribute;
/*     */   }
/*     */   
/*     */   private String unmarshalNodeContent(NamespaceVersion nsVersion, ModelNode node, QName nodeElementName, XMLEventReader reader) throws PolicyException {
/* 276 */     StringBuilder valueBuffer = null;
/*     */ 
/*     */     
/* 279 */     while (reader.hasNext()) {
/*     */       try {
/* 281 */         StartElement childElement; ModelNode childNode; String value; XMLEvent xmlParserEvent = reader.nextEvent();
/* 282 */         switch (xmlParserEvent.getEventType()) {
/*     */           case 5:
/*     */             continue;
/*     */           case 4:
/* 286 */             valueBuffer = processCharacters(node.getType(), xmlParserEvent.asCharacters(), valueBuffer);
/*     */             continue;
/*     */           case 2:
/* 289 */             checkEndTagName(nodeElementName, xmlParserEvent.asEndElement());
/*     */             break;
/*     */           case 1:
/* 292 */             childElement = xmlParserEvent.asStartElement();
/*     */             
/* 294 */             childNode = addNewChildNode(nsVersion, node, childElement);
/* 295 */             value = unmarshalNodeContent(nsVersion, childNode, childElement.getName(), reader);
/*     */             
/* 297 */             if (childNode.isDomainSpecific()) {
/* 298 */               parseAssertionData(nsVersion, value, childNode, childElement);
/*     */             }
/*     */             continue;
/*     */         } 
/* 302 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0011_UNABLE_TO_UNMARSHALL_POLICY_XML_ELEM_EXPECTED()));
/*     */       }
/* 304 */       catch (XMLStreamException e) {
/* 305 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0068_FAILED_TO_UNMARSHALL_POLICY_EXPRESSION(), e));
/*     */       } 
/*     */     } 
/*     */     
/* 309 */     return (valueBuffer == null) ? null : valueBuffer.toString().trim();
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
/*     */   private XMLEventReader createXMLEventReader(Object storage) throws PolicyException {
/* 322 */     if (storage instanceof XMLEventReader) {
/* 323 */       return (XMLEventReader)storage;
/*     */     }
/* 325 */     if (!(storage instanceof Reader)) {
/* 326 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0022_STORAGE_TYPE_NOT_SUPPORTED(storage.getClass().getName())));
/*     */     }
/*     */     
/*     */     try {
/* 330 */       return XMLInputFactory.newInstance().createXMLEventReader((Reader)storage);
/* 331 */     } catch (XMLStreamException e) {
/* 332 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0014_UNABLE_TO_INSTANTIATE_READER_FOR_STORAGE(), e));
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
/*     */   private void checkEndTagName(QName expected, EndElement element) throws PolicyException {
/* 346 */     QName actual = element.getName();
/* 347 */     if (!expected.equals(actual)) {
/* 348 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0003_UNMARSHALLING_FAILED_END_TAG_DOES_NOT_MATCH(expected, actual)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder processCharacters(ModelNode.Type currentNodeType, Characters characters, StringBuilder currentValueBuffer) throws PolicyException {
/* 356 */     if (characters.isWhiteSpace()) {
/* 357 */       return currentValueBuffer;
/*     */     }
/* 359 */     StringBuilder buffer = (currentValueBuffer == null) ? new StringBuilder() : currentValueBuffer;
/* 360 */     String data = characters.getData();
/* 361 */     if (currentNodeType == ModelNode.Type.ASSERTION || currentNodeType == ModelNode.Type.ASSERTION_PARAMETER_NODE) {
/* 362 */       return buffer.append(data);
/*     */     }
/* 364 */     throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0009_UNEXPECTED_CDATA_ON_SOURCE_MODEL_NODE(currentNodeType, data)));
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
/*     */   private boolean parseBooleanValue(String value) throws PolicyException {
/* 380 */     if ("true".equals(value) || "1".equals(value)) {
/* 381 */       return true;
/*     */     }
/* 383 */     if ("false".equals(value) || "0".equals(value)) {
/* 384 */       return false;
/*     */     }
/* 386 */     throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0095_INVALID_BOOLEAN_VALUE(value)));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\XmlPolicyModelUnmarshaller.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */