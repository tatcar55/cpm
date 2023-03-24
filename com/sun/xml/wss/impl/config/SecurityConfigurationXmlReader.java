/*      */ package com.sun.xml.wss.impl.config;
/*      */ 
/*      */ import com.sun.xml.wss.WSITXMLFactory;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.XMLUtil;
/*      */ import com.sun.xml.wss.impl.configuration.StaticApplicationContext;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DynamicSecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.Parameter;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.Target;
/*      */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Random;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.XPathType;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SecurityConfigurationXmlReader
/*      */   implements ConfigurationConstants
/*      */ {
/*  116 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*      */ 
/*      */ 
/*      */   
/*  120 */   static Random rnd = new Random();
/*      */   private static Document parseXmlString(String sourceXml) throws Exception {
/*  122 */     return parseXmlStream(new ByteArrayInputStream(sourceXml.getBytes()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateConfiguration(Element element) throws Exception {
/*  131 */     NodeList timestamps = element.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "Timestamp");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  136 */     if (timestamps.getLength() > 1)
/*      */     {
/*  138 */       throw new IllegalStateException("More than one xwss:Timestamp element in security configuration file");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  144 */     NodeList requireTimestamps = element.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "RequireTimestamp");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  149 */     if (requireTimestamps.getLength() > 1)
/*      */     {
/*  151 */       throw new IllegalStateException("More than one xwss:RequireTimestamp element in security configuration file");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  157 */     NodeList usernamePasswords = element.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "UsernameToken");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  162 */     if (usernamePasswords.getLength() > 1)
/*      */     {
/*  164 */       throw new IllegalStateException("More than one xwss:UsernameToken element in security configuration file");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  170 */     NodeList requireUsernamePasswords = element.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "RequireUsernameToken");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  175 */     if (requireUsernamePasswords.getLength() > 1)
/*      */     {
/*  177 */       throw new IllegalStateException("More than one xwss:RequireUsernameToken element in security configuration file");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  183 */     NodeList optionalTargets = element.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "OptionalTargets");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     if (optionalTargets.getLength() > 1)
/*      */     {
/*  190 */       throw new IllegalStateException("More than one xwss:OptionalTargets element in security configuration file");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  196 */     NodeList samlAssertions = element.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "SAMLAssertion");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  201 */     if (samlAssertions.getLength() > 1)
/*      */     {
/*  203 */       throw new IllegalStateException("More than one xwss:SAMLAssertion element in security configuration file");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  208 */     checkIdUniqueness(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ApplicationSecurityConfiguration readApplicationSecurityConfigurationString(String sourceXml) throws Exception {
/*  221 */     return (ApplicationSecurityConfiguration)createSecurityConfiguration(parseXmlString(sourceXml).getDocumentElement());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Document parseXmlStream(InputStream xmlStream) throws Exception {
/*  229 */     return parseXmlStream(xmlStream, (PrintStream)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Document parseXmlStream(InputStream xmlStream, PrintStream out) throws Exception {
/*  236 */     DocumentBuilderFactory factory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*      */     
/*  238 */     factory.setAttribute("http://apache.org/xml/features/validation/dynamic", Boolean.FALSE);
/*      */ 
/*      */     
/*  241 */     factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
/*      */ 
/*      */     
/*  244 */     InputStream is = SecurityConfigurationXmlReader.class.getResourceAsStream("xwssconfig.xsd");
/*      */ 
/*      */     
/*  247 */     factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", is);
/*      */     
/*  249 */     factory.setValidating(true);
/*  250 */     factory.setIgnoringComments(true);
/*  251 */     factory.setNamespaceAware(true);
/*  252 */     DocumentBuilder builder = factory.newDocumentBuilder();
/*  253 */     builder.setErrorHandler(new ErrorHandler(out));
/*  254 */     Document configurationDocument = builder.parse(xmlStream);
/*      */ 
/*      */     
/*  257 */     NodeList nodeList = configurationDocument.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "SecurityConfiguration");
/*      */     
/*  259 */     for (int i = 0; i < nodeList.getLength(); i++) {
/*  260 */       validateConfiguration((Element)nodeList.item(i));
/*      */     }
/*  262 */     return configurationDocument;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void validate(InputStream xmlStream, PrintStream out) throws Exception {
/*  274 */     parseXmlStream(xmlStream, out);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DeclarativeSecurityConfiguration createDeclarativeConfiguration(InputStream xmlStream) throws Exception {
/*  287 */     return readContainerForBaseConfigurationData(parseXmlStream(xmlStream).getDocumentElement(), new DeclarativeSecurityConfiguration());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ApplicationSecurityConfiguration createApplicationSecurityConfiguration(InputStream xmlStream) throws Exception {
/*  302 */     ApplicationSecurityConfiguration config = (ApplicationSecurityConfiguration)createSecurityConfiguration(parseXmlStream(xmlStream).getDocumentElement());
/*      */ 
/*      */     
/*  305 */     config.init();
/*  306 */     return config;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static DeclarativeSecurityConfiguration createDeclarativeConfiguration(Element configData) throws Exception {
/*  312 */     DeclarativeSecurityConfiguration declarations = new DeclarativeSecurityConfiguration();
/*      */     
/*  314 */     readContainerForBaseConfigurationData(configData, declarations);
/*  315 */     return declarations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SecurityPolicy createSecurityConfiguration(Element configData) throws Exception {
/*  322 */     QName qname = getQName(configData);
/*      */     
/*  324 */     if (JAXRPC_SECURITY_ELEMENT_QNAME.equals(qname)) {
/*  325 */       ApplicationSecurityConfiguration declarations = new ApplicationSecurityConfiguration();
/*      */ 
/*      */       
/*  328 */       String secEnvHandler = getSecurityEnvironmentHandler(configData);
/*  329 */       if (secEnvHandler != null) {
/*  330 */         declarations.setSecurityEnvironmentHandler(secEnvHandler);
/*      */       }
/*  332 */       if (!configHasSingleService(configData)) {
/*  333 */         throw new IllegalStateException("Single <xwss:Service> element expected under <xwss:JAXRPCSecurity> element");
/*      */       }
/*      */ 
/*      */       
/*  337 */       String optimize = configData.getAttribute("optimize");
/*      */       
/*  339 */       boolean opt = Boolean.valueOf(optimize).booleanValue();
/*      */       
/*  341 */       declarations.isOptimized(opt);
/*      */       
/*  343 */       String retainSecHeader = configData.getAttribute("retainSecurityHeader");
/*  344 */       declarations.retainSecurityHeader(Boolean.valueOf(retainSecHeader).booleanValue());
/*      */       
/*  346 */       String resetMU = configData.getAttribute("resetMustUnderstand");
/*  347 */       declarations.resetMustUnderstand(Boolean.valueOf(resetMU).booleanValue());
/*      */       
/*  349 */       Element previousDefinitionElement = null;
/*  350 */       Element eachDefinitionElement = getFirstChildElement(configData);
/*  351 */       int secEnvTagCount = 0;
/*  352 */       HashMap<Object, Object> serviceNameMap = new HashMap<Object, Object>();
/*      */ 
/*      */       
/*  355 */       while (eachDefinitionElement != null) {
/*  356 */         QName qName = getQName(eachDefinitionElement);
/*      */         
/*  358 */         if (SERVICE_ELEMENT_QNAME.equals(qName)) {
/*  359 */           StaticApplicationContext jaxrpcSContext = new StaticApplicationContext();
/*      */ 
/*      */           
/*  362 */           String name = eachDefinitionElement.getAttribute("name");
/*  363 */           if (serviceNameMap.containsKey(name))
/*      */           {
/*  365 */             throw new IllegalStateException("Service Name " + name + " Already in use for another Service");
/*      */           }
/*      */ 
/*      */           
/*  369 */           serviceNameMap.put(name, null);
/*      */ 
/*      */ 
/*      */           
/*  373 */           readApplicationSecurityConfiguration(eachDefinitionElement, (SecurityPolicy)declarations, (SecurityPolicy)null, jaxrpcSContext);
/*      */         
/*      */         }
/*  376 */         else if (SECURITY_ENVIRONMENT_HANDLER_ELEMENT_QNAME.equals(qName)) {
/*      */ 
/*      */           
/*  379 */           if (secEnvTagCount == 1)
/*      */           {
/*  381 */             throw new IllegalStateException("More than one xwss:SecurityEnvironmentHandler element in security configuration file");
/*      */           }
/*      */ 
/*      */           
/*  385 */           secEnvTagCount++;
/*      */         } else {
/*      */           
/*  388 */           log.log(Level.SEVERE, "WSS0413.illegal.configuration.element", eachDefinitionElement.getTagName());
/*      */ 
/*      */           
/*  391 */           throw new IllegalStateException(eachDefinitionElement.getTagName() + " is not a recognized definition type");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  396 */         previousDefinitionElement = eachDefinitionElement;
/*  397 */         eachDefinitionElement = getNextElement(eachDefinitionElement);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  402 */       QName definitionType = getQName(previousDefinitionElement);
/*  403 */       if (!SECURITY_ENVIRONMENT_HANDLER_ELEMENT_QNAME.equals(definitionType))
/*      */       {
/*  405 */         throw new IllegalStateException("The SecurityEnvironmentHandler must appear as the last Element inside a <xwss:JAXRPCSecurity>");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  411 */       declarations.singleServiceNoPorts(configHasSingleServiceAndNoPorts(configData));
/*      */       
/*  413 */       declarations.hasOperationPolicies(configHasOperations(configData));
/*      */ 
/*      */       
/*  416 */       return (SecurityPolicy)declarations;
/*      */     } 
/*  418 */     if (DECLARATIVE_CONFIGURATION_ELEMENT_QNAME.equals(qname)) {
/*  419 */       if (dynamicPolicy(configData)) {
/*  420 */         return (SecurityPolicy)new DynamicSecurityPolicy();
/*      */       }
/*      */       
/*  423 */       DeclarativeSecurityConfiguration declarations = new DeclarativeSecurityConfiguration();
/*      */       
/*  425 */       readContainerForBaseConfigurationData(configData, declarations);
/*      */       
/*  427 */       return declarations;
/*      */     } 
/*  429 */     log.log(Level.SEVERE, "WSS0413.illegal.configuration.element", configData.getTagName());
/*      */ 
/*      */     
/*  432 */     throw new IllegalStateException(configData.getTagName() + " is not a recognized definition type");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readApplicationSecurityConfiguration(Element configData, SecurityPolicy declarations, SecurityPolicy subDeclarations, StaticApplicationContext iContext) throws Exception {
/*  447 */     QName qname = getQName(configData);
/*      */     
/*  449 */     if (SERVICE_ELEMENT_QNAME.equals(qname)) {
/*      */       
/*  451 */       String id = getIdAttribute(configData);
/*  452 */       String name = configData.getAttribute("name");
/*  453 */       String useCache = configData.getAttribute("useCache");
/*      */       
/*  455 */       boolean isBSP = getBSPAttribute(configData, (ApplicationSecurityConfiguration)null);
/*      */       
/*  457 */       iContext.isService(true);
/*  458 */       iContext.setUUID(id);
/*  459 */       iContext.setServiceIdentifier(name);
/*      */ 
/*      */       
/*  462 */       if (!"".equals(name)) {
/*  463 */         iContext.setApplicationContextRoot(name);
/*  464 */       } else if (!"".equals(id)) {
/*  465 */         iContext.setApplicationContextRoot(id);
/*      */       } else {
/*  467 */         iContext.setApplicationContextRoot(generateUUID());
/*      */       } 
/*      */ 
/*      */       
/*  471 */       ApplicationSecurityConfiguration innerDeclarations = new ApplicationSecurityConfiguration();
/*      */ 
/*      */       
/*  474 */       innerDeclarations.isBSP(isBSP);
/*  475 */       innerDeclarations.useCache(parseBoolean("useCache", useCache));
/*      */ 
/*      */       
/*  478 */       ((ApplicationSecurityConfiguration)declarations).setSecurityPolicy((StaticPolicyContext)iContext, (SecurityPolicy)innerDeclarations);
/*      */ 
/*      */       
/*  481 */       String secEnvHandler = getSecurityEnvironmentHandler(configData);
/*  482 */       if (secEnvHandler != null) {
/*  483 */         innerDeclarations.setSecurityEnvironmentHandler(secEnvHandler);
/*  484 */       } else if (((ApplicationSecurityConfiguration)declarations).getSecurityEnvironmentHandler() != null) {
/*      */         
/*  486 */         innerDeclarations.setSecurityEnvironmentHandler(((ApplicationSecurityConfiguration)declarations).getSecurityEnvironmentHandler());
/*      */       }
/*      */       else {
/*      */         
/*  490 */         throw new IllegalStateException("Missing <xwss:SecurityEnvironmentHandler> element for " + qname.getLocalPart());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  495 */       NodeList nl = configData.getChildNodes();
/*  496 */       for (int i = 0; i < nl.getLength(); i++)
/*      */       {
/*  498 */         Node child = nl.item(i);
/*  499 */         if (child instanceof Element) {
/*  500 */           readApplicationSecurityConfiguration((Element)child, declarations, (SecurityPolicy)innerDeclarations, iContext);
/*      */         
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  506 */     else if (PORT_ELEMENT_QNAME.equals(qname)) {
/*      */       
/*  508 */       if (subDeclarations == null) {
/*  509 */         throw new Exception("Unexpected <xwss:Port> element without a parent <xwss:Service> encountered");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  514 */       String port = configData.getAttribute("name");
/*      */       
/*  516 */       StaticApplicationContext jContext = new StaticApplicationContext();
/*  517 */       jContext.copy(iContext);
/*  518 */       jContext.isPort(true);
/*  519 */       jContext.isService(false);
/*  520 */       jContext.setPortIdentifier(port);
/*      */       
/*  522 */       ApplicationSecurityConfiguration innerDeclarations = new ApplicationSecurityConfiguration();
/*      */ 
/*      */       
/*  525 */       boolean isBSP = getBSPAttribute(configData, (ApplicationSecurityConfiguration)subDeclarations);
/*      */ 
/*      */       
/*  528 */       innerDeclarations.isBSP(isBSP);
/*  529 */       innerDeclarations.setSecurityEnvironmentHandler(((ApplicationSecurityConfiguration)subDeclarations).getSecurityEnvironmentHandler());
/*      */ 
/*      */ 
/*      */       
/*  533 */       ((ApplicationSecurityConfiguration)declarations).setSecurityPolicy((StaticPolicyContext)jContext, (SecurityPolicy)innerDeclarations);
/*      */       
/*  535 */       ((ApplicationSecurityConfiguration)subDeclarations).setSecurityPolicy((StaticPolicyContext)jContext, (SecurityPolicy)innerDeclarations);
/*      */ 
/*      */       
/*  538 */       NodeList nl = configData.getChildNodes();
/*  539 */       for (int i = 0; i < nl.getLength(); i++)
/*      */       {
/*  541 */         Node child = nl.item(i);
/*  542 */         if (child instanceof Element) {
/*  543 */           readApplicationSecurityConfiguration((Element)child, declarations, (SecurityPolicy)innerDeclarations, jContext);
/*      */         
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  549 */     else if (OPERATION_ELEMENT_QNAME.equals(qname)) {
/*      */       
/*  551 */       String operation = configData.getAttribute("name");
/*      */       
/*  553 */       StaticApplicationContext kContext = new StaticApplicationContext();
/*      */       
/*  555 */       kContext.copy(iContext);
/*  556 */       kContext.isOperation(true);
/*  557 */       kContext.isPort(false);
/*  558 */       kContext.setOperationIdentifier(operation);
/*      */ 
/*      */       
/*  561 */       ApplicationSecurityConfiguration innerDeclarations = new ApplicationSecurityConfiguration();
/*      */ 
/*      */       
/*  564 */       ((ApplicationSecurityConfiguration)declarations).setSecurityPolicy((StaticPolicyContext)kContext, (SecurityPolicy)innerDeclarations);
/*      */       
/*  566 */       ((ApplicationSecurityConfiguration)subDeclarations).setSecurityPolicy((StaticPolicyContext)kContext, (SecurityPolicy)innerDeclarations);
/*      */ 
/*      */       
/*  569 */       boolean isBSP = getBSPAttribute(configData, (ApplicationSecurityConfiguration)subDeclarations);
/*      */       
/*  571 */       innerDeclarations.isBSP(isBSP);
/*  572 */       innerDeclarations.setSecurityEnvironmentHandler(((ApplicationSecurityConfiguration)subDeclarations).getSecurityEnvironmentHandler());
/*      */ 
/*      */ 
/*      */       
/*  576 */       NodeList nl = configData.getChildNodes();
/*  577 */       for (int i = 0; i < nl.getLength(); i++)
/*      */       {
/*  579 */         Node child = nl.item(i);
/*  580 */         if (child instanceof Element) {
/*  581 */           readApplicationSecurityConfiguration((Element)child, declarations, (SecurityPolicy)innerDeclarations, kContext);
/*      */         
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  587 */     else if (DECLARATIVE_CONFIGURATION_ELEMENT_QNAME.equals(qname)) {
/*      */       
/*  589 */       if (dynamicPolicy(configData)) {
/*  590 */         ((ApplicationSecurityConfiguration)subDeclarations).setSecurityPolicy((StaticPolicyContext)iContext, (SecurityPolicy)new DynamicSecurityPolicy());
/*      */       }
/*      */ 
/*      */       
/*  594 */       DeclarativeSecurityConfiguration innerDeclarations = new DeclarativeSecurityConfiguration();
/*      */ 
/*      */       
/*  597 */       boolean isBSP = getBSPAttribute(configData, (ApplicationSecurityConfiguration)subDeclarations);
/*      */       
/*  599 */       innerDeclarations.isBSP(isBSP);
/*      */       
/*  601 */       ((ApplicationSecurityConfiguration)subDeclarations).setSecurityPolicy((StaticPolicyContext)iContext, innerDeclarations);
/*      */ 
/*      */       
/*  604 */       readContainerForBaseConfigurationData(configData, innerDeclarations, ((ApplicationSecurityConfiguration)subDeclarations).getSecurityEnvironmentHandler());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  609 */     else if (SECURITY_ENVIRONMENT_HANDLER_ELEMENT_QNAME.equals(qname)) {
/*      */ 
/*      */ 
/*      */       
/*  613 */       if (!iContext.isService())
/*      */       {
/*  615 */         throw new IllegalStateException("An <xwss:SecurityEnvironmentHandler> can only appearunder a <xwss:Service>/<xwss:JAXRPCSecurity> element");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DeclarativeSecurityConfiguration readContainerForBaseConfigurationData(Element configData, DeclarativeSecurityConfiguration declarations) throws Exception {
/*  627 */     return readContainerForBaseConfigurationData(configData, declarations, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DeclarativeSecurityConfiguration readContainerForBaseConfigurationData(Element configData, DeclarativeSecurityConfiguration declarations, String securityHandlerClass) throws Exception {
/*  637 */     QName qname = getQName(configData);
/*      */     
/*  639 */     if (DECLARATIVE_CONFIGURATION_ELEMENT_QNAME.equals(qname)) {
/*      */       
/*  641 */       NamedNodeMap configurationAttributes = configData.getAttributes();
/*  642 */       int attributeCount = configurationAttributes.getLength();
/*  643 */       String attributeName = null;
/*      */       
/*  645 */       for (int index = 0; index < attributeCount; index++) {
/*  646 */         Attr configurationAttribute = (Attr)configurationAttributes.item(index);
/*      */         
/*  648 */         attributeName = configurationAttribute.getName();
/*      */         
/*  650 */         if ("dumpMessages".equalsIgnoreCase(attributeName)) {
/*      */           
/*  652 */           declarations.setDumpMessages(parseBoolean("dumpMessages", configurationAttribute.getValue()));
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  657 */         else if (!"http://www.w3.org/2000/xmlns/".equals(configurationAttribute.getNamespaceURI())) {
/*      */ 
/*      */           
/*  660 */           if ("enableDynamicPolicy".equalsIgnoreCase(attributeName)) {
/*      */             
/*  662 */             declarations.enableDynamicPolicy(parseBoolean("enableDynamicPolicy", configurationAttribute.getValue()));
/*      */ 
/*      */           
/*      */           }
/*  666 */           else if ("enableWSS11Policy".equalsIgnoreCase(attributeName)) {
/*      */             
/*  668 */             boolean wss11Enabled = parseBoolean("enableWSS11Policy", configurationAttribute.getValue());
/*      */             
/*  670 */             declarations.senderSettings().enableWSS11Policy(wss11Enabled);
/*  671 */             declarations.receiverSettings().enableWSS11Policy(wss11Enabled);
/*  672 */           } else if ("retainSecurityHeader".equalsIgnoreCase(attributeName)) {
/*  673 */             String retainSecHeader = configurationAttribute.getValue();
/*  674 */             declarations.retainSecurityHeader(Boolean.valueOf(retainSecHeader).booleanValue());
/*  675 */           } else if ("resetMustUnderstand".equalsIgnoreCase(attributeName)) {
/*  676 */             String resetMU = configurationAttribute.getValue();
/*  677 */             declarations.resetMustUnderstand(Boolean.valueOf(resetMU).booleanValue());
/*      */           } else {
/*  679 */             log.log(Level.SEVERE, "WSS0412.illegal.attribute.name", new Object[] { attributeName, configData.getTagName() });
/*      */ 
/*      */ 
/*      */             
/*  683 */             throw new IllegalStateException(attributeName + " is not a recognized attribute of SecurityConfiguration");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  688 */       readBaseConfigurationData(configData, declarations, securityHandlerClass);
/*      */     } else {
/*  690 */       log.log(Level.SEVERE, "WSS0413.illegal.configuration.element", configData.getTagName());
/*      */ 
/*      */       
/*  693 */       throw new IllegalStateException(configData.getTagName() + " is not a recognized definition type");
/*      */     } 
/*      */     
/*  696 */     return declarations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readBaseConfigurationData(Element configData, DeclarativeSecurityConfiguration declarations, String securityHandlerClass) throws PolicyGenerationException, XWSSecurityException {
/*  705 */     Element eachDefinitionElement = getFirstChildElement(configData);
/*  706 */     boolean timestampFound = false;
/*      */     
/*  708 */     boolean senderEnableDynamicPolicy = declarations.senderSettings().enableDynamicPolicy();
/*  709 */     boolean receiverEnableDynamicPolicy = declarations.receiverSettings().enableDynamicPolicy();
/*  710 */     boolean receiverBSPFlag = declarations.receiverSettings().isBSP();
/*      */ 
/*      */     
/*  713 */     boolean senderBSPFlag = declarations.senderSettings().isBSP();
/*      */     
/*  715 */     while (eachDefinitionElement != null) {
/*  716 */       QName definitionType = getQName(eachDefinitionElement);
/*      */       
/*  718 */       if (TIMESTAMP_ELEMENT_QNAME.equals(definitionType)) {
/*      */         
/*  720 */         if (!timestampFound) {
/*  721 */           TimestampPolicy timestampPolicy = new TimestampPolicy();
/*  722 */           readTimestampSettings(timestampPolicy, eachDefinitionElement);
/*  723 */           applyDefaults(timestampPolicy, senderEnableDynamicPolicy);
/*      */           
/*  725 */           declarations.senderSettings().append((SecurityPolicy)timestampPolicy);
/*  726 */           timestampFound = true;
/*      */         } else {
/*  728 */           log.log(Level.SEVERE, "WSS0516.duplicate.configuration.element", new Object[] { definitionType, configData.getLocalName() });
/*      */ 
/*      */ 
/*      */           
/*  732 */           throw new IllegalStateException("Duplicate Timestamp element");
/*      */         }
/*      */       
/*      */       }
/*  736 */       else if (ENCRYPT_OPERATION_ELEMENT_QNAME.equals(definitionType)) {
/*      */         
/*  738 */         EncryptionPolicy encryptionPolicy = new EncryptionPolicy();
/*  739 */         readEncryptionSettings(encryptionPolicy, eachDefinitionElement);
/*  740 */         applyDefaults(encryptionPolicy, senderEnableDynamicPolicy);
/*  741 */         declarations.senderSettings().append((SecurityPolicy)encryptionPolicy);
/*      */       }
/*  743 */       else if (SIGN_OPERATION_ELEMENT_QNAME.equals(definitionType)) {
/*  744 */         SignaturePolicy signaturePolicy = new SignaturePolicy();
/*  745 */         readSigningSettings(signaturePolicy, eachDefinitionElement, senderEnableDynamicPolicy);
/*      */ 
/*      */ 
/*      */         
/*  749 */         SignaturePolicy.FeatureBinding fb = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/*      */         
/*  751 */         if (fb != null) {
/*  752 */           fb.isBSP(senderBSPFlag);
/*      */         }
/*      */ 
/*      */         
/*  756 */         String includeTimeStamp = eachDefinitionElement.getAttribute("includeTimestamp");
/*      */         
/*  758 */         boolean timeStamp = getBooleanValue(includeTimeStamp);
/*      */         
/*  760 */         if (timeStamp && !hasTimestampSiblingPolicy(eachDefinitionElement)) {
/*      */           
/*  762 */           TimestampPolicy t = new TimestampPolicy();
/*  763 */           t.setMaxClockSkew(300000L);
/*  764 */           t.setTimestampFreshness(300000L);
/*  765 */           applyDefaults(t, senderEnableDynamicPolicy);
/*  766 */           declarations.senderSettings().append((SecurityPolicy)t);
/*      */         } 
/*      */         
/*  769 */         declarations.senderSettings().append((SecurityPolicy)signaturePolicy);
/*      */       }
/*  771 */       else if (USERNAME_PASSWORD_AUTHENTICATION_ELEMENT_QNAME.equals(definitionType)) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  776 */           AuthenticationTokenPolicy utBinding = new AuthenticationTokenPolicy();
/*      */ 
/*      */           
/*  779 */           AuthenticationTokenPolicy.UsernameTokenBinding featureBinding = (AuthenticationTokenPolicy.UsernameTokenBinding)utBinding.newUsernameTokenFeatureBinding();
/*      */ 
/*      */           
/*  782 */           featureBinding.newTimestampFeatureBinding();
/*  783 */           readUsernamePasswordSettings(featureBinding, eachDefinitionElement);
/*  784 */           applyDefaults(featureBinding, senderEnableDynamicPolicy);
/*  785 */           declarations.senderSettings().append((SecurityPolicy)utBinding);
/*  786 */         } catch (PolicyGenerationException pge) {
/*      */           
/*  788 */           throw new IllegalStateException(pge.getMessage());
/*      */         }
/*      */       
/*  791 */       } else if (SAML_ELEMENT_QNAME.equals(definitionType)) {
/*      */         
/*      */         try {
/*  794 */           AuthenticationTokenPolicy samlBinding = new AuthenticationTokenPolicy();
/*      */ 
/*      */           
/*  797 */           AuthenticationTokenPolicy.SAMLAssertionBinding featureBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)samlBinding.newSAMLAssertionFeatureBinding();
/*      */ 
/*      */           
/*  800 */           readSAMLTokenSettings(featureBinding, eachDefinitionElement);
/*      */           
/*  802 */           applyDefaults(featureBinding, senderEnableDynamicPolicy);
/*  803 */           declarations.senderSettings().append((SecurityPolicy)samlBinding);
/*  804 */         } catch (PolicyGenerationException pge) {
/*      */           
/*  806 */           throw new IllegalStateException(pge.getMessage());
/*      */         } 
/*  808 */       } else if (SIGNATURE_REQUIREMENT_ELEMENT_QNAME.equals(definitionType)) {
/*      */         
/*  810 */         SignaturePolicy signaturePolicy = new SignaturePolicy();
/*  811 */         readVerifySettings(signaturePolicy, eachDefinitionElement, receiverBSPFlag, receiverEnableDynamicPolicy);
/*  812 */         declarations.receiverSettings().append((SecurityPolicy)signaturePolicy);
/*      */         
/*  814 */         String requireTimeStamp = eachDefinitionElement.getAttribute("requireTimestamp");
/*      */         
/*  816 */         boolean timeStamp = getBooleanValue(requireTimeStamp);
/*      */         
/*  818 */         if (timeStamp && !hasTimestampSiblingPolicy(eachDefinitionElement))
/*      */         {
/*  820 */           TimestampPolicy t = new TimestampPolicy();
/*      */ 
/*      */           
/*  823 */           applyReceiverDefaults(t, receiverBSPFlag, securityHandlerClass, receiverEnableDynamicPolicy);
/*  824 */           declarations.receiverSettings().append((SecurityPolicy)t);
/*      */         }
/*      */       
/*  827 */       } else if (ENCRYPTION_REQUIREMENT_ELEMENT_QNAME.equals(definitionType)) {
/*      */         
/*  829 */         EncryptionPolicy encryptionPolicy = new EncryptionPolicy();
/*  830 */         readDecryptionSettings(encryptionPolicy, eachDefinitionElement);
/*  831 */         applyReceiverDefaults(encryptionPolicy, receiverBSPFlag, receiverEnableDynamicPolicy);
/*  832 */         declarations.receiverSettings().append((SecurityPolicy)encryptionPolicy);
/*      */       }
/*  834 */       else if (USERNAMETOKEN_REQUIREMENT_ELEMENT_QNAME.equals(definitionType)) {
/*      */         
/*      */         try {
/*  837 */           AuthenticationTokenPolicy utBinding = new AuthenticationTokenPolicy();
/*      */ 
/*      */           
/*  840 */           AuthenticationTokenPolicy.UsernameTokenBinding featureBinding = (AuthenticationTokenPolicy.UsernameTokenBinding)utBinding.newUsernameTokenFeatureBinding();
/*      */ 
/*      */           
/*  843 */           featureBinding.newTimestampFeatureBinding();
/*  844 */           readUsernamePasswordRequirementSettings(featureBinding, eachDefinitionElement);
/*      */           
/*  846 */           applyReceiverDefaults(featureBinding, receiverBSPFlag, securityHandlerClass, receiverEnableDynamicPolicy);
/*  847 */           declarations.receiverSettings().append((SecurityPolicy)utBinding);
/*      */ 
/*      */         
/*      */         }
/*  851 */         catch (PolicyGenerationException pge) {
/*      */           
/*  853 */           throw new IllegalStateException(pge.getMessage());
/*      */         } 
/*  855 */       } else if (TIMESTAMP_REQUIREMENT_ELEMENT_QNAME.equals(definitionType)) {
/*      */ 
/*      */         
/*  858 */         TimestampPolicy timestampPolicy = new TimestampPolicy();
/*  859 */         readTimestampRequirementSettings(timestampPolicy, eachDefinitionElement);
/*      */         
/*  861 */         applyReceiverDefaults(timestampPolicy, receiverBSPFlag, securityHandlerClass, receiverEnableDynamicPolicy);
/*  862 */         declarations.receiverSettings().append((SecurityPolicy)timestampPolicy);
/*      */       }
/*  864 */       else if (SAML_REQUIREMENT_ELEMENT_QNAME.equals(definitionType)) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  869 */           AuthenticationTokenPolicy samlBinding = new AuthenticationTokenPolicy();
/*      */ 
/*      */           
/*  872 */           AuthenticationTokenPolicy.SAMLAssertionBinding featureBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)samlBinding.newSAMLAssertionFeatureBinding();
/*      */ 
/*      */           
/*  875 */           readRequireSAMLTokenSettings(featureBinding, eachDefinitionElement);
/*      */           
/*  877 */           applyReceiverDefaults(featureBinding, receiverBSPFlag, receiverEnableDynamicPolicy);
/*  878 */           declarations.receiverSettings().append((SecurityPolicy)samlBinding);
/*  879 */         } catch (PolicyGenerationException pge) {
/*      */           
/*  881 */           throw new IllegalStateException(pge.getMessage());
/*      */         }
/*      */       
/*  884 */       } else if (OPTIONAL_TARGETS_ELEMENT_QNAME.equals(definitionType)) {
/*  885 */         readOptionalTargetSettings(declarations.receiverSettings(), eachDefinitionElement);
/*      */       } else {
/*      */         
/*  888 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", definitionType.toString());
/*      */ 
/*      */         
/*  891 */         throw new IllegalStateException(definitionType + " is not a recognized definition type");
/*      */       } 
/*      */ 
/*      */       
/*  895 */       eachDefinitionElement = getNextElement(eachDefinitionElement);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readVerifySettings(SignaturePolicy signaturePolicy, Element signingSettings, boolean bsp, boolean dp) {
/*  902 */     readVerifySettings(signaturePolicy, signingSettings);
/*  903 */     applyReceiverDefaults(signaturePolicy, bsp, dp);
/*  904 */     String includeTimeStamp = signingSettings.getAttribute("requireTimestamp");
/*  905 */     boolean timeStamp = getBooleanValue(includeTimeStamp);
/*  906 */     if (timeStamp) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  912 */       SignatureTarget st = new SignatureTarget();
/*  913 */       st.setType("qname");
/*  914 */       st.setValue("{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp");
/*  915 */       st.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/*  916 */       ((SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding()).addTargetBinding(st);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  921 */     signaturePolicy.isBSP(bsp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readVerifySettings(SignaturePolicy signaturePolicy, Element signingSettings) {
/*  926 */     readSigningSettings(signaturePolicy, signingSettings);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readSigningSettings(SignaturePolicy signaturePolicy, Element signingSettings, boolean enableDynamicPolicy) {
/*  931 */     readSigningSettings(signaturePolicy, signingSettings);
/*  932 */     applyDefaults(signaturePolicy, enableDynamicPolicy);
/*      */     
/*  934 */     String includeTimeStamp = signingSettings.getAttribute("includeTimestamp");
/*  935 */     boolean timeStamp = getBooleanValue(includeTimeStamp);
/*      */     
/*  937 */     if (timeStamp) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  943 */       SignatureTarget st = new SignatureTarget();
/*  944 */       st.setType("qname");
/*  945 */       st.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/*  946 */       st.setValue("{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp");
/*  947 */       ((SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding()).addTargetBinding(st);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasTimestampSiblingPolicy(Element signElement) {
/*  955 */     if ("Sign".equals(signElement.getLocalName())) {
/*  956 */       Element signParent = (Element)signElement.getParentNode();
/*  957 */       NodeList timeStampNodes = signParent.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "Timestamp");
/*  958 */       if (timeStampNodes.getLength() > 0) {
/*  959 */         return true;
/*      */       }
/*      */     } else {
/*  962 */       Element signParent = (Element)signElement.getParentNode();
/*  963 */       NodeList timeStampNodes = signParent.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "RequireTimestamp");
/*  964 */       if (timeStampNodes.getLength() > 0) {
/*  965 */         return true;
/*      */       }
/*  967 */       NamedNodeMap requireTimestampAttrNode = null;
/*  968 */       Node requireSignatureNode = signElement.getPreviousSibling();
/*  969 */       while (requireSignatureNode != null) {
/*  970 */         if ("RequireSignature".equals(requireSignatureNode.getLocalName())) {
/*  971 */           requireTimestampAttrNode = requireSignatureNode.getAttributes();
/*      */ 
/*      */           
/*  974 */           if ("true".equalsIgnoreCase(requireTimestampAttrNode.getNamedItem("requireTimestamp").getLocalName()))
/*      */           {
/*      */             
/*  977 */             return true;
/*      */           }
/*      */         } 
/*  980 */         requireSignatureNode = requireSignatureNode.getPreviousSibling();
/*      */       } 
/*      */     } 
/*      */     
/*  984 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readSigningSettings(SignaturePolicy signaturePolicy, Element signingSettings) {
/*  992 */     String id = getIdAttribute(signingSettings);
/*  993 */     signaturePolicy.setUUID(id);
/*      */     
/*  995 */     NamedNodeMap signingAttributes = signingSettings.getAttributes();
/*  996 */     int attributeCount = signingAttributes.getLength();
/*  997 */     String attributeName = null;
/*      */     
/*  999 */     for (int index = 0; index < attributeCount; index++) {
/* 1000 */       Attr signingAttribute = (Attr)signingAttributes.item(index);
/* 1001 */       attributeName = signingAttribute.getName();
/* 1002 */       if (!"id".equalsIgnoreCase(attributeName))
/*      */       {
/* 1004 */         if (!"includeTimestamp".equalsIgnoreCase(attributeName) || !"Sign".equals(signingSettings.getLocalName()))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1009 */           if (!"requireTimestamp".equalsIgnoreCase(attributeName) || !"RequireSignature".equals(signingSettings.getLocalName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1015 */             log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, signingSettings.getTagName() });
/*      */ 
/*      */ 
/*      */             
/* 1019 */             throw new IllegalStateException(attributeName + " is not a recognized attribute of " + signingSettings.getTagName());
/*      */           } 
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1025 */     Element eachSubElement = getFirstChildElement(signingSettings);
/* 1026 */     int keyBearingTokensSeen = 0;
/*      */     
/* 1028 */     while (eachSubElement != null) {
/* 1029 */       QName subElementQName = getQName(eachSubElement);
/*      */       
/* 1031 */       if (TARGET_QNAME.equals(subElementQName)) {
/* 1032 */         SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/*      */ 
/*      */         
/* 1035 */         featureBinding.addTargetBinding(readTargetSettings(eachSubElement, true));
/*      */       }
/* 1037 */       else if (X509TOKEN_ELEMENT_QNAME.equals(subElementQName)) {
/* 1038 */         if (keyBearingTokensSeen > 0) {
/* 1039 */           log.log(Level.SEVERE, "WSS0520.illegal.configuration.state");
/*      */           
/* 1041 */           throw new IllegalStateException("Atmost one of X509token/SymmetricKey/SAMLAssertion  key bindings can be configured for an Sign/RequireSignature operation");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1046 */         keyBearingTokensSeen++;
/* 1047 */         readX509TokenSettings((AuthenticationTokenPolicy.X509CertificateBinding)signaturePolicy.newX509CertificateKeyBinding(), eachSubElement);
/*      */       
/*      */       }
/* 1050 */       else if (SYMMETRIC_KEY_ELEMENT_QNAME.equals(subElementQName)) {
/* 1051 */         if (keyBearingTokensSeen > 0) {
/* 1052 */           log.log(Level.SEVERE, "WSS0520.illegal.configuration.state");
/*      */           
/* 1054 */           throw new IllegalStateException("Atmost one of X509token/SymmetricKey/SAMLAssertion  key bindings can be configured for an Sign/RequireSignature operation");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1059 */         keyBearingTokensSeen++;
/* 1060 */         readSymmetricKeySettings((SymmetricKeyBinding)signaturePolicy.newSymmetricKeyBinding(), eachSubElement);
/*      */       
/*      */       }
/* 1063 */       else if (SAML_ELEMENT_QNAME.equals(subElementQName)) {
/* 1064 */         if (keyBearingTokensSeen > 0) {
/* 1065 */           log.log(Level.SEVERE, "WSS0520.illegal.configuration.state");
/*      */           
/* 1067 */           throw new IllegalStateException("Atmost one of X509token/SymmetricKey/SAMLAssertion  key bindings can be configured for an Sign/RequireSignature operation");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1072 */         keyBearingTokensSeen++;
/* 1073 */         readSAMLTokenSettings((AuthenticationTokenPolicy.SAMLAssertionBinding)signaturePolicy.newSAMLAssertionKeyBinding(), eachSubElement);
/*      */ 
/*      */       
/*      */       }
/* 1077 */       else if (SIGNATURE_TARGET_ELEMENT_QNAME.equals(subElementQName)) {
/* 1078 */         SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/*      */ 
/*      */         
/* 1081 */         featureBinding.addTargetBinding(readSignatureTargetSettings(eachSubElement));
/*      */       }
/* 1083 */       else if (CANONICALIZATION_METHOD_ELEMENT_QNAME.equals(subElementQName)) {
/* 1084 */         readCanonMethodSettings(signaturePolicy, eachSubElement);
/* 1085 */       } else if (SIGNATURE_METHOD_ELEMENT_QNAME.equals(subElementQName)) {
/* 1086 */         readSigMethodSettings(signaturePolicy, eachSubElement);
/*      */       } else {
/* 1088 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", subElementQName.toString());
/*      */ 
/*      */         
/* 1091 */         throw new IllegalStateException(subElementQName + " is not a recognized sub-element of Sign/RequireSignature");
/*      */       } 
/*      */       
/* 1094 */       eachSubElement = getNextElement(eachSubElement);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readSymmetricKeySettings(SymmetricKeyBinding keyBinding, Element symmKeyElement) {
/* 1104 */     NamedNodeMap symmKeyAttributes = symmKeyElement.getAttributes();
/* 1105 */     int attributeCount = symmKeyAttributes.getLength();
/* 1106 */     String attributeName = null;
/*      */     
/* 1108 */     if (attributeCount == 0) {
/* 1109 */       throw new IllegalStateException("A SymmetricKey must specify keyAlias, certAlias or useReceivedSecret as an attribute");
/*      */     }
/*      */ 
/*      */     
/* 1113 */     for (int index = 0; index < attributeCount; index++) {
/* 1114 */       Attr symmKeyAttribute = (Attr)symmKeyAttributes.item(index);
/* 1115 */       attributeName = symmKeyAttribute.getName();
/*      */       
/* 1117 */       if ("keyAlias".equalsIgnoreCase(attributeName)) {
/* 1118 */         keyBinding.setKeyIdentifier(symmKeyAttribute.getValue());
/* 1119 */       } else if ("certAlias".equalsIgnoreCase(attributeName)) {
/* 1120 */         keyBinding.setCertAlias(symmKeyAttribute.getValue());
/* 1121 */       } else if ("useReceivedSecret".equalsIgnoreCase(attributeName)) {
/*      */         try {
/* 1123 */           keyBinding.setUseReceivedSecret(parseBoolean(attributeName, symmKeyAttribute.getValue()));
/* 1124 */         } catch (Exception e) {
/* 1125 */           e.printStackTrace();
/*      */         } 
/*      */       } else {
/* 1128 */         log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:SymmetricKey" });
/*      */ 
/*      */ 
/*      */         
/* 1132 */         throw new IllegalStateException(attributeName + " is not a recognized attribute of SymmetricKey");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readX509TokenSettings(AuthenticationTokenPolicy.X509CertificateBinding keyBinding, Element token) {
/* 1144 */     keyBinding.newPrivateKeyBinding();
/* 1145 */     String id = getIdAttribute(token);
/* 1146 */     keyBinding.setUUID(id);
/*      */     
/* 1148 */     NamedNodeMap tokenAttributes = token.getAttributes();
/* 1149 */     int attributeCount = tokenAttributes.getLength();
/* 1150 */     String attributeName = null;
/* 1151 */     for (int index = 0; index < attributeCount; index++) {
/* 1152 */       Attr tokenAttribute = (Attr)tokenAttributes.item(index);
/* 1153 */       attributeName = tokenAttribute.getName();
/*      */       
/* 1155 */       if (!"id".equalsIgnoreCase(attributeName))
/*      */       {
/* 1157 */         if ("keyReferenceType".equalsIgnoreCase(attributeName)) {
/* 1158 */           String keyReferenceStrategy = tokenAttribute.getValue();
/* 1159 */           keyBinding.setReferenceType(keyReferenceStrategy);
/*      */         }
/* 1161 */         else if ("certificateAlias".equalsIgnoreCase(attributeName)) {
/* 1162 */           String certificateAlias = tokenAttribute.getValue();
/* 1163 */           keyBinding.setCertificateIdentifier(certificateAlias);
/* 1164 */         } else if ("EncodingType".equalsIgnoreCase(attributeName)) {
/* 1165 */           String encodingType = tokenAttribute.getValue();
/* 1166 */           keyBinding.setEncodingType(encodingType);
/* 1167 */         } else if ("ValueType".equalsIgnoreCase(attributeName)) {
/* 1168 */           String valueType = tokenAttribute.getValue();
/* 1169 */           keyBinding.setValueType(valueType);
/* 1170 */         } else if ("strId".equalsIgnoreCase(attributeName)) {
/* 1171 */           String strid = tokenAttribute.getValue();
/* 1172 */           keyBinding.setSTRID(strid);
/*      */         } else {
/* 1174 */           log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:X509Token" });
/*      */ 
/*      */ 
/*      */           
/* 1178 */           throw new IllegalStateException(attributeName + " is not a recognized attribute of X509Token");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readOptionalTargetSettings(MessagePolicy requirements, Element optionalTargetSettings) throws XWSSecurityException {
/* 1191 */     ArrayList<Target> targets = new ArrayList();
/* 1192 */     Element eachSubElement = getFirstChildElement(optionalTargetSettings);
/* 1193 */     while (eachSubElement != null) {
/* 1194 */       QName subElementQName = getQName(eachSubElement);
/*      */       
/* 1196 */       if (TARGET_QNAME.equals(subElementQName)) {
/* 1197 */         Target t = new Target();
/* 1198 */         t.setEnforce(false);
/* 1199 */         Target t1 = readTargetSettings(eachSubElement, t);
/* 1200 */         targets.add(t1);
/*      */       } else {
/* 1202 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", subElementQName.toString());
/*      */ 
/*      */         
/* 1205 */         throw new IllegalStateException(subElementQName + " is not a recognized sub-element of OptionalTargets");
/*      */       } 
/*      */       
/* 1208 */       eachSubElement = getNextElement(eachSubElement);
/*      */     } 
/*      */     
/* 1211 */     requirements.addOptionalTargets(targets);
/*      */     
/* 1213 */     requirements.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readDecryptionSettings(EncryptionPolicy encryptionPolicy, Element encryptionSettings) {
/* 1219 */     readEncryptionSettings(encryptionPolicy, encryptionSettings);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readEncryptionSettings(EncryptionPolicy encryptionPolicy, Element encryptionSettings) {
/* 1229 */     String id = getIdAttribute(encryptionSettings);
/* 1230 */     encryptionPolicy.setUUID(id);
/*      */ 
/*      */     
/* 1233 */     NamedNodeMap encryptAttributes = encryptionSettings.getAttributes();
/*      */     
/* 1235 */     int attributeCount = encryptAttributes.getLength();
/*      */     
/* 1237 */     String attributeName = null;
/*      */     
/* 1239 */     for (int index = 0; index < attributeCount; ) {
/* 1240 */       Attr encAttr = (Attr)encryptAttributes.item(index);
/*      */       
/* 1242 */       attributeName = encAttr.getName();
/*      */       
/* 1244 */       if ("id".equalsIgnoreCase(attributeName)) {
/*      */         index++; continue;
/*      */       } 
/* 1247 */       log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, encryptionSettings.getTagName() });
/*      */ 
/*      */ 
/*      */       
/* 1251 */       throw new IllegalStateException(attributeName + " is not a recognized attribute of " + encryptionSettings.getTagName());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1258 */     int keyBearingTokensSeen = 0;
/*      */     
/* 1260 */     Element eachSubElement = getFirstChildElement(encryptionSettings);
/* 1261 */     while (eachSubElement != null) {
/* 1262 */       QName subElementQName = getQName(eachSubElement);
/*      */       
/* 1264 */       if (TARGET_QNAME.equals(subElementQName)) {
/* 1265 */         EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)encryptionPolicy.getFeatureBinding();
/*      */ 
/*      */         
/* 1268 */         featureBinding.addTargetBinding(readTargetSettings(eachSubElement, false));
/*      */       }
/* 1270 */       else if (X509TOKEN_ELEMENT_QNAME.equals(subElementQName)) {
/* 1271 */         if (keyBearingTokensSeen > 0) {
/* 1272 */           log.log(Level.SEVERE, "WSS0520.illegal.configuration.state");
/*      */           
/* 1274 */           throw new IllegalStateException("Atmost one of X509token/SymmetricKey/SAMLAssertion  key bindings can be configured for an Encrypt/RequireEncryption operation");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1279 */         keyBearingTokensSeen++;
/* 1280 */         readX509TokenSettings((AuthenticationTokenPolicy.X509CertificateBinding)encryptionPolicy.newX509CertificateKeyBinding(), eachSubElement);
/*      */       
/*      */       }
/* 1283 */       else if (SYMMETRIC_KEY_ELEMENT_QNAME.equals(subElementQName)) {
/* 1284 */         if (keyBearingTokensSeen > 0) {
/* 1285 */           log.log(Level.SEVERE, "WSS0520.illegal.configuration.state");
/*      */           
/* 1287 */           throw new IllegalStateException("Atmost one of X509token/SymmetricKey/SAMLAssertion  key bindings can be configured for an Encrypt/RequireEncryption operation");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1292 */         keyBearingTokensSeen++;
/* 1293 */         readSymmetricKeySettings((SymmetricKeyBinding)encryptionPolicy.newSymmetricKeyBinding(), eachSubElement);
/*      */       
/*      */       }
/* 1296 */       else if (SAML_ELEMENT_QNAME.equals(subElementQName)) {
/* 1297 */         if (keyBearingTokensSeen > 0) {
/* 1298 */           log.log(Level.SEVERE, "WSS0520.illegal.configuration.state");
/*      */           
/* 1300 */           throw new IllegalStateException("Atmost one of X509token/SymmetricKey/SAMLAssertion  key bindings can be configured for an Encrypt/RequireEncryption operation");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1305 */         keyBearingTokensSeen++;
/* 1306 */         readSAMLTokenSettings((AuthenticationTokenPolicy.SAMLAssertionBinding)encryptionPolicy.newSAMLAssertionKeyBinding(), eachSubElement);
/*      */ 
/*      */       
/*      */       }
/* 1310 */       else if (ENCRYPTION_TARGET_ELEMENT_QNAME.equals(subElementQName)) {
/* 1311 */         EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)encryptionPolicy.getFeatureBinding();
/*      */ 
/*      */         
/* 1314 */         featureBinding.addTargetBinding(readEncryptionTargetSettings(eachSubElement));
/*      */       }
/* 1316 */       else if (KEY_ENCRYPTION_METHOD_ELEMENT_QNAME.equals(subElementQName)) {
/* 1317 */         readKeyEncMethodSettings(encryptionPolicy, eachSubElement);
/* 1318 */       } else if (DATA_ENCRYPTION_METHOD_ELEMENT_QNAME.equals(subElementQName)) {
/* 1319 */         readDataEncMethodSettings(encryptionPolicy, eachSubElement);
/*      */       } else {
/* 1321 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", subElementQName.toString());
/*      */ 
/*      */         
/* 1324 */         throw new IllegalStateException(subElementQName + " is not a recognized sub-element of Encrypt/RequireEncryption");
/*      */       } 
/*      */       
/* 1327 */       eachSubElement = getNextElement(eachSubElement);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readKeyEncMethodSettings(EncryptionPolicy encryptionPolicy, Element keyEncSettings) {
/* 1334 */     String algorithm = keyEncSettings.getAttribute("algorithm");
/*      */     
/* 1336 */     if ("".equals(algorithm)) {
/* 1337 */       throw new IllegalArgumentException("Empty/Missing algorithm attribute on " + keyEncSettings.getTagName());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1342 */     checkCompatibility(algorithm, keyEncSettings);
/* 1343 */     MLSPolicy mLSPolicy = encryptionPolicy.getKeyBinding();
/* 1344 */     if (mLSPolicy == null) {
/* 1345 */       mLSPolicy = encryptionPolicy.newX509CertificateKeyBinding();
/*      */       
/* 1347 */       ((AuthenticationTokenPolicy.X509CertificateBinding)mLSPolicy).setReferenceType("Direct");
/*      */     } 
/*      */     
/* 1350 */     setKeyAlgorithm((SecurityPolicy)mLSPolicy, algorithm);
/*      */   }
/*      */   
/*      */   private static void checkCompatibility(String keyEncAlgo, Element keyEncSettings) {
/* 1354 */     if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(keyEncAlgo) || "http://www.w3.org/2001/04/xmlenc#rsa-1_5".equals(keyEncAlgo)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1361 */       if (hasSymmetricKeySibling(keyEncSettings))
/*      */       {
/* 1363 */         throw new IllegalStateException("Invalid SymmetricKey association specified for KeyEncryptionMethod " + keyEncAlgo + ", required X509Token/SAML key association");
/*      */       
/*      */       }
/*      */     }
/* 1367 */     else if ("http://www.w3.org/2001/04/xmlenc#kw-tripledes".equals(keyEncAlgo) || keyEncAlgo.startsWith("http://www.w3.org/2001/04/xmlenc#kw-aes")) {
/*      */ 
/*      */       
/* 1370 */       if (!hasSymmetricKeySibling(keyEncSettings))
/*      */       {
/* 1372 */         throw new IllegalStateException("Missing SymmetricKey association  for KeyEncryptionMethod " + keyEncAlgo);
/*      */       }
/*      */ 
/*      */       
/* 1376 */       if (hasX509Sibling(keyEncSettings))
/*      */       {
/* 1378 */         throw new IllegalStateException("Invalid X509Token/SAML key association specified for KeyEncryptionMethod " + keyEncAlgo + ",  required SymmetricKey association");
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1383 */       throw new IllegalArgumentException("Invalid/Unsupported Algorithm " + keyEncAlgo + " specified for " + "KeyEncryptionMethod");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasX509Sibling(Element keyEncSettings) {
/* 1390 */     Element parent = (Element)keyEncSettings.getParentNode();
/* 1391 */     NodeList x509Nodes = parent.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "X509Token");
/* 1392 */     if (x509Nodes.getLength() > 0) {
/* 1393 */       return true;
/*      */     }
/* 1395 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean hasSymmetricKeySibling(Element keyEncSettings) {
/* 1399 */     Element parent = (Element)keyEncSettings.getParentNode();
/* 1400 */     NodeList symKeyNodes = parent.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "SymmetricKey");
/* 1401 */     if (symKeyNodes.getLength() > 0) {
/* 1402 */       return true;
/*      */     }
/* 1404 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setDefaultKeyAlgorithm(SecurityPolicy keyBinding, String algorithm) {
/* 1409 */     if (PolicyTypeUtil.samlTokenPolicy(keyBinding)) {
/* 1410 */       AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)keyBinding;
/*      */       
/* 1412 */       if ("".equals(samlBinding.getKeyAlgorithm()))
/* 1413 */         samlBinding.setKeyAlgorithm(algorithm); 
/* 1414 */     } else if (PolicyTypeUtil.x509CertificateBinding(keyBinding)) {
/* 1415 */       AuthenticationTokenPolicy.X509CertificateBinding x509Binding = (AuthenticationTokenPolicy.X509CertificateBinding)keyBinding;
/*      */       
/* 1417 */       if ("".equals(x509Binding.getKeyAlgorithm()))
/* 1418 */         x509Binding.setKeyAlgorithm(algorithm); 
/* 1419 */     } else if (PolicyTypeUtil.symmetricKeyBinding(keyBinding)) {
/* 1420 */       SymmetricKeyBinding symBinding = (SymmetricKeyBinding)keyBinding;
/* 1421 */       if ("".equals(symBinding.getKeyAlgorithm()))
/* 1422 */         symBinding.setKeyAlgorithm(algorithm); 
/*      */     } else {
/* 1424 */       throw new IllegalArgumentException("Unknown Key Type " + keyBinding.getClass().getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setKeyAlgorithm(SecurityPolicy keyBinding, String algorithm) {
/* 1430 */     if (PolicyTypeUtil.samlTokenPolicy(keyBinding)) {
/* 1431 */       AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)keyBinding;
/*      */       
/* 1433 */       samlBinding.setKeyAlgorithm(algorithm);
/* 1434 */     } else if (PolicyTypeUtil.x509CertificateBinding(keyBinding)) {
/* 1435 */       AuthenticationTokenPolicy.X509CertificateBinding x509Binding = (AuthenticationTokenPolicy.X509CertificateBinding)keyBinding;
/*      */       
/* 1437 */       x509Binding.setKeyAlgorithm(algorithm);
/* 1438 */       if ("http://www.w3.org/2000/09/xmldsig#hmac-sha1".equals(algorithm)) {
/* 1439 */         String certAlias = x509Binding.getCertificateIdentifier();
/* 1440 */         if (certAlias == null || certAlias.equals("")) {
/* 1441 */           throw new IllegalArgumentException("The certificate Alias should be set when algorithm is:" + algorithm);
/*      */         }
/*      */       } 
/* 1444 */     } else if (PolicyTypeUtil.symmetricKeyBinding(keyBinding)) {
/* 1445 */       SymmetricKeyBinding symBinding = (SymmetricKeyBinding)keyBinding;
/* 1446 */       symBinding.setKeyAlgorithm(algorithm);
/*      */     } else {
/* 1448 */       throw new IllegalArgumentException("Unknown Key Type " + keyBinding.getClass().getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readDataEncMethodSettings(EncryptionPolicy encryptionPolicy, Element dataEncSettings) {
/* 1454 */     String algorithm = dataEncSettings.getAttribute("algorithm");
/*      */     
/* 1456 */     if ("".equals(algorithm)) {
/* 1457 */       throw new IllegalArgumentException("Empty/Missing algorithm attribute on " + dataEncSettings.getTagName());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1462 */     EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)encryptionPolicy.getFeatureBinding();
/*      */ 
/*      */     
/* 1465 */     featureBinding.setDataEncryptionAlgorithm(algorithm);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readCanonMethodSettings(SignaturePolicy signaturePolicy, Element canonSettings) {
/* 1470 */     String algorithm = canonSettings.getAttribute("algorithm");
/*      */     
/* 1472 */     boolean disableInclusivePrefix = false;
/*      */     try {
/* 1474 */       disableInclusivePrefix = parseBoolean("disableInclusivePrefix", canonSettings.getAttribute("disableInclusivePrefix"));
/* 1475 */     } catch (Exception e) {
/* 1476 */       e.printStackTrace();
/*      */     } 
/* 1478 */     if ("".equals(algorithm)) {
/* 1479 */       throw new IllegalArgumentException("Empty/Missing algorithm attribute on " + canonSettings.getTagName());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1484 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/*      */ 
/*      */     
/* 1487 */     featureBinding.setCanonicalizationAlgorithm(algorithm);
/* 1488 */     featureBinding.setDisbaleInclusivePrefix(disableInclusivePrefix);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readSigMethodSettings(SignaturePolicy signaturePolicy, Element sigMethodSettings) {
/* 1493 */     String algorithm = sigMethodSettings.getAttribute("algorithm");
/*      */     
/* 1495 */     if ("".equals(algorithm)) {
/* 1496 */       throw new IllegalArgumentException("Empty/Missing algorithm attribute on " + sigMethodSettings.getTagName());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1502 */     MLSPolicy mLSPolicy = signaturePolicy.getKeyBinding();
/* 1503 */     if (mLSPolicy == null) {
/* 1504 */       mLSPolicy = signaturePolicy.newX509CertificateKeyBinding();
/*      */       
/* 1506 */       ((AuthenticationTokenPolicy.X509CertificateBinding)mLSPolicy).setReferenceType("Direct");
/*      */     } 
/*      */     
/* 1509 */     setKeyAlgorithm((SecurityPolicy)mLSPolicy, algorithm);
/*      */   }
/*      */   
/*      */   private static QName getQName(Node element) {
/* 1513 */     return new QName(element.getNamespaceURI(), element.getLocalName());
/*      */   }
/*      */   
/*      */   private static Element getFirstChildElement(Node node) {
/* 1517 */     Node nextSibling = node.getFirstChild();
/* 1518 */     while (nextSibling != null && 
/* 1519 */       !(nextSibling instanceof Element))
/*      */     {
/*      */       
/* 1522 */       nextSibling = nextSibling.getNextSibling();
/*      */     }
/* 1524 */     return (Element)nextSibling;
/*      */   }
/*      */   
/*      */   private static Element getNextElement(Node node) {
/* 1528 */     Node nextSibling = node;
/* 1529 */     while (nextSibling != null) {
/* 1530 */       nextSibling = nextSibling.getNextSibling();
/* 1531 */       if (nextSibling instanceof Element) {
/*      */         break;
/*      */       }
/*      */     } 
/* 1535 */     return (Element)nextSibling;
/*      */   }
/*      */   
/*      */   private static class ErrorHandler extends DefaultHandler {
/*      */     PrintStream out;
/*      */     
/*      */     public ErrorHandler(PrintStream out) {
/* 1542 */       this.out = out;
/*      */     }
/*      */     
/*      */     public void error(SAXParseException e) throws SAXException {
/* 1546 */       if (this.out != null)
/* 1547 */         this.out.println(e); 
/* 1548 */       throw e;
/*      */     }
/*      */     
/*      */     public void warning(SAXParseException e) throws SAXException {
/* 1552 */       if (this.out != null) {
/* 1553 */         this.out.println(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void fatalError(SAXParseException e) throws SAXException {
/* 1559 */       if (this.out != null)
/* 1560 */         this.out.println(e); 
/* 1561 */       throw e;
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean parseBoolean(String attr, String value) throws Exception {
/* 1566 */     if ("1".equals(value) || "true".equalsIgnoreCase(value))
/* 1567 */       return true; 
/* 1568 */     if ("0".equals(value) || "false".equalsIgnoreCase(value)) {
/* 1569 */       return false;
/*      */     }
/* 1571 */     log.log(Level.SEVERE, "WSS0511.illegal.boolean.value", value);
/* 1572 */     throw new Exception("Boolean attribute " + attr + " has value other than 'true' or 'false'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long parseLong(String str) {
/* 1580 */     if (!"".equals(str)) {
/* 1581 */       String ret = str;
/* 1582 */       int idx = str.indexOf(".");
/* 1583 */       if (idx > 0) {
/* 1584 */         ret = str.substring(0, idx);
/*      */       }
/* 1586 */       return Long.parseLong(ret);
/*      */     } 
/* 1588 */     return 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void readTimestampSettings(TimestampPolicy policy, Element timestampSettings) {
/* 1593 */     String id = getIdAttribute(timestampSettings);
/* 1594 */     policy.setUUID(id);
/* 1595 */     String timeout = timestampSettings.getAttribute("timeout");
/* 1596 */     policy.setTimeout(parseLong(timeout) * 1000L);
/*      */ 
/*      */     
/* 1599 */     Element someElement = getFirstChildElement(timestampSettings);
/* 1600 */     if (someElement != null) {
/* 1601 */       log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", getQName(someElement));
/*      */ 
/*      */       
/* 1604 */       throw new IllegalStateException(getQName(someElement) + " is not a recognized sub-element of Timestamp");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readTimestampRequirementSettings(TimestampPolicy policy, Element timestampSettings) {
/* 1611 */     String id = getIdAttribute(timestampSettings);
/* 1612 */     policy.setUUID(id);
/*      */     
/* 1614 */     String maxClockSkew = timestampSettings.getAttribute("maxClockSkew");
/* 1615 */     String timestampFreshness = timestampSettings.getAttribute("timestampFreshnessLimit");
/*      */ 
/*      */     
/* 1618 */     policy.setMaxClockSkew(parseLong(maxClockSkew) * 1000L);
/* 1619 */     policy.setTimestampFreshness(parseLong(timestampFreshness) * 1000L);
/*      */     
/* 1621 */     Element someElement = getFirstChildElement(timestampSettings);
/* 1622 */     if (someElement != null) {
/* 1623 */       log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", getQName(someElement));
/*      */ 
/*      */       
/* 1626 */       throw new IllegalStateException(getQName(someElement) + " is not a recognized sub-element of RequireTimestamp");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readUsernamePasswordSettings(AuthenticationTokenPolicy.UsernameTokenBinding utBinding, Element usernamePasswordSettings) {
/* 1637 */     String id = getIdAttribute(usernamePasswordSettings);
/* 1638 */     utBinding.setUUID(id);
/*      */     
/* 1640 */     NamedNodeMap usernameAttributes = usernamePasswordSettings.getAttributes();
/*      */     
/* 1642 */     int attributeCount = usernameAttributes.getLength();
/*      */     
/* 1644 */     String attributeName = null;
/*      */     
/* 1646 */     for (int index = 0; index < attributeCount; index++) {
/* 1647 */       Attr usernamePasswordAttribute = (Attr)usernameAttributes.item(index);
/*      */ 
/*      */       
/* 1650 */       attributeName = usernamePasswordAttribute.getName();
/* 1651 */       if ("id".equalsIgnoreCase(attributeName)) {
/* 1652 */         utBinding.setUUID(usernamePasswordAttribute.getValue());
/* 1653 */       } else if ("name".equalsIgnoreCase(attributeName)) {
/* 1654 */         utBinding.setUsername(usernamePasswordAttribute.getValue());
/* 1655 */       } else if ("password".equalsIgnoreCase(attributeName)) {
/* 1656 */         utBinding.setPassword(usernamePasswordAttribute.getValue());
/* 1657 */       } else if ("useNonce".equalsIgnoreCase(attributeName)) {
/* 1658 */         utBinding.setUseNonce(getBooleanValue(usernamePasswordAttribute.getValue()));
/*      */       }
/* 1660 */       else if ("digestPassword".equalsIgnoreCase(attributeName)) {
/*      */         
/* 1662 */         utBinding.setDigestOn(getBooleanValue(usernamePasswordAttribute.getValue()));
/*      */       } else {
/*      */         
/* 1665 */         log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, usernamePasswordSettings.getTagName() });
/*      */ 
/*      */ 
/*      */         
/* 1669 */         throw new IllegalStateException(attributeName + " is not a recognized attribute of UsernameToken");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1674 */     if (utBinding.getDigestOn() && !utBinding.getUseNonce()) {
/* 1675 */       throw new IllegalStateException("useNonce attribute must be true if digestPassword is true");
/*      */     }
/*      */     
/* 1678 */     Element someElement = getFirstChildElement(usernamePasswordSettings);
/* 1679 */     if (someElement != null) {
/* 1680 */       log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", getQName(someElement));
/*      */ 
/*      */       
/* 1683 */       throw new IllegalStateException(getQName(someElement) + " is not a recognized sub-element of UsernameToken");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readUsernamePasswordRequirementSettings(AuthenticationTokenPolicy.UsernameTokenBinding utBinding, Element authenticateUserSettings) {
/* 1694 */     String id = getIdAttribute(authenticateUserSettings);
/* 1695 */     utBinding.setUUID(id);
/*      */ 
/*      */     
/* 1698 */     TimestampPolicy tPolicy = null;
/*      */     try {
/* 1700 */       tPolicy = (TimestampPolicy)utBinding.newTimestampFeatureBinding();
/* 1701 */     } catch (Exception e) {
/*      */       
/* 1703 */       throw new IllegalStateException(e.getMessage());
/*      */     } 
/*      */     
/* 1706 */     NamedNodeMap authenticateUserAttributes = authenticateUserSettings.getAttributes();
/* 1707 */     int attributeCount = authenticateUserAttributes.getLength();
/* 1708 */     String attributeName = null;
/*      */     
/* 1710 */     for (int index = 0; index < attributeCount; index++) {
/* 1711 */       Attr authenticateUserAttribute = (Attr)authenticateUserAttributes.item(index);
/* 1712 */       attributeName = authenticateUserAttribute.getName();
/*      */       
/* 1714 */       if (!"id".equalsIgnoreCase(attributeName))
/*      */       {
/* 1716 */         if ("nonceRequired".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1718 */           utBinding.setUseNonce(getBooleanValue(authenticateUserAttribute.getValue()));
/*      */         }
/* 1720 */         else if ("passwordDigestRequired".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1722 */           utBinding.setDigestOn(getBooleanValue(authenticateUserAttribute.getValue()));
/*      */         }
/* 1724 */         else if ("maxClockSkew".equalsIgnoreCase(attributeName)) {
/* 1725 */           tPolicy.setMaxClockSkew(parseLong(authenticateUserAttribute.getValue()) * 1000L);
/* 1726 */         } else if ("timestampFreshnessLimit".equalsIgnoreCase(attributeName)) {
/* 1727 */           tPolicy.setTimestampFreshness(parseLong(authenticateUserAttribute.getValue()) * 1000L);
/* 1728 */         } else if ("maxNonceAge".equalsIgnoreCase(attributeName)) {
/* 1729 */           utBinding.setMaxNonceAge(parseLong(authenticateUserAttribute.getValue()) * 1000L);
/*      */         } else {
/* 1731 */           log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:RequireUsernameToken" });
/*      */ 
/*      */ 
/*      */           
/* 1735 */           throw new IllegalStateException(attributeName + " is not a recognized attribute of RequireUsernameToken");
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1740 */     Element someElement = getFirstChildElement(authenticateUserSettings);
/* 1741 */     if (someElement != null) {
/* 1742 */       log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", getQName(someElement));
/*      */ 
/*      */       
/* 1745 */       throw new IllegalStateException(getQName(someElement) + " is not a recognized sub-element of RequireUsernameToken");
/*      */     } 
/*      */ 
/*      */     
/* 1749 */     if (utBinding.getDigestOn() && !utBinding.getUseNonce()) {
/* 1750 */       throw new IllegalStateException("nonceRequired attribute must be true if passwordDigestRequired is true");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readSAMLTokenSettings(AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, Element samlTokenSettings) {
/* 1758 */     String id = getIdAttribute(samlTokenSettings);
/* 1759 */     samlBinding.setUUID(id);
/*      */     
/* 1761 */     String type = samlTokenSettings.getAttribute("type");
/* 1762 */     validateSAMLType(type, samlTokenSettings);
/*      */     
/* 1764 */     NamedNodeMap samlAttributes = samlTokenSettings.getAttributes();
/* 1765 */     int attributeCount = samlAttributes.getLength();
/* 1766 */     String attributeName = null;
/*      */     
/* 1768 */     for (int index = 0; index < attributeCount; index++) {
/* 1769 */       Attr samlAttribute = (Attr)samlAttributes.item(index);
/*      */       
/* 1771 */       attributeName = samlAttribute.getName();
/*      */       
/* 1773 */       if (!"id".equalsIgnoreCase(attributeName))
/*      */       {
/* 1775 */         if ("type".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1777 */           samlBinding.setAssertionType(samlAttribute.getValue());
/* 1778 */         } else if ("authorityId".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1780 */           samlBinding.setAuthorityIdentifier(samlAttribute.getValue());
/* 1781 */         } else if ("keyIdentifier".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1783 */           samlBinding.setKeyIdentifier(samlAttribute.getValue());
/* 1784 */         } else if ("keyReferenceType".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1786 */           String attributeValue = samlAttribute.getValue();
/* 1787 */           validateSAMLKeyReferenceType(attributeValue);
/* 1788 */           samlBinding.setReferenceType(attributeValue);
/* 1789 */         } else if ("strId".equalsIgnoreCase(attributeName)) {
/* 1790 */           String strid = samlAttribute.getValue();
/* 1791 */           samlBinding.setSTRID(strid);
/*      */         } else {
/* 1793 */           log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:SAMLAssertion" });
/*      */ 
/*      */ 
/*      */           
/* 1797 */           throw new IllegalStateException(attributeName + " is not a recognized attribute of SAMLAssertion");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readRequireSAMLTokenSettings(AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding, Element samlTokenSettings) {
/* 1808 */     String id = getIdAttribute(samlTokenSettings);
/* 1809 */     samlBinding.setUUID(id);
/*      */     
/* 1811 */     String type = samlTokenSettings.getAttribute("type");
/* 1812 */     validateRequireSAMLType(type, samlTokenSettings);
/*      */     
/* 1814 */     NamedNodeMap samlAttributes = samlTokenSettings.getAttributes();
/* 1815 */     int attributeCount = samlAttributes.getLength();
/* 1816 */     String attributeName = null;
/*      */     
/* 1818 */     for (int index = 0; index < attributeCount; index++) {
/* 1819 */       Attr samlAttribute = (Attr)samlAttributes.item(index);
/*      */       
/* 1821 */       attributeName = samlAttribute.getName();
/*      */       
/* 1823 */       if (!"id".equalsIgnoreCase(attributeName))
/*      */       {
/* 1825 */         if ("type".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1827 */           samlBinding.setAssertionType(samlAttribute.getValue());
/* 1828 */         } else if ("authorityId".equalsIgnoreCase(attributeName)) {
/* 1829 */           samlBinding.setAuthorityIdentifier(samlAttribute.getValue());
/* 1830 */         } else if ("keyReferenceType".equalsIgnoreCase(attributeName)) {
/*      */           
/* 1832 */           String attributeValue = samlAttribute.getValue();
/* 1833 */           validateSAMLKeyReferenceType(attributeValue);
/* 1834 */           samlBinding.setReferenceType(attributeValue);
/* 1835 */         } else if ("strId".equalsIgnoreCase(attributeName)) {
/* 1836 */           String strid = samlAttribute.getValue();
/* 1837 */           samlBinding.setSTRID(strid);
/*      */         } else {
/* 1839 */           log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:RequireSAMLAssertion" });
/*      */ 
/*      */ 
/*      */           
/* 1843 */           throw new IllegalStateException(attributeName + " is not a recognized attribute of RequireSAMLAssertion");
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EncryptionTarget readEncryptionTargetSettings(Element targetSettings) {
/* 1854 */     EncryptionTarget target = new EncryptionTarget();
/*      */ 
/*      */     
/* 1857 */     NamedNodeMap targetAttributes = targetSettings.getAttributes();
/* 1858 */     int attributeCount = targetAttributes.getLength();
/* 1859 */     String attributeName = null;
/* 1860 */     for (int index = 0; index < attributeCount; index++) {
/* 1861 */       Attr targetAttribute = (Attr)targetAttributes.item(index);
/* 1862 */       attributeName = targetAttribute.getName();
/*      */       
/* 1864 */       if ("type".equalsIgnoreCase(attributeName)) {
/* 1865 */         String targetType = targetAttribute.getValue();
/*      */         
/* 1867 */         if ("qname".equalsIgnoreCase(targetType)) {
/* 1868 */           target.setType("qname");
/* 1869 */         } else if ("xpath".equalsIgnoreCase(targetType)) {
/* 1870 */           target.setType("xpath");
/* 1871 */         } else if ("uri".equalsIgnoreCase(targetType)) {
/* 1872 */           target.setType("uri");
/*      */         } else {
/* 1874 */           log.log(Level.SEVERE, "WSS0519.illegal.attribute.value", "xwss:Target@Type");
/*      */ 
/*      */           
/* 1877 */           throw new IllegalStateException(targetType + " is not a recognized type of Target");
/*      */         }
/*      */       
/* 1880 */       } else if ("contentOnly".equalsIgnoreCase(attributeName)) {
/*      */ 
/*      */         
/* 1883 */         String contentOnly = targetAttribute.getValue();
/* 1884 */         target.setContentOnly(getBooleanValue(contentOnly));
/*      */       }
/* 1886 */       else if ("enforce".equalsIgnoreCase(attributeName)) {
/*      */         
/* 1888 */         String enforce_S = targetAttribute.getValue();
/* 1889 */         boolean enforce = Boolean.valueOf(enforce_S).booleanValue();
/* 1890 */         target.setEnforce(enforce);
/* 1891 */       } else if (!"value".equalsIgnoreCase(attributeName)) {
/*      */ 
/*      */ 
/*      */         
/* 1895 */         log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:Target" });
/*      */ 
/*      */ 
/*      */         
/* 1899 */         throw new IllegalStateException(attributeName + " is not a recognized attribute of Target");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1906 */     String targetValue = targetSettings.getAttribute("value");
/* 1907 */     if (targetValue == null)
/*      */     {
/*      */       
/* 1910 */       throw new IllegalStateException("value attribute of the EncryptionTarget element missing/empty");
/*      */     }
/*      */ 
/*      */     
/* 1914 */     if (targetValue.startsWith("#")) {
/* 1915 */       targetValue = targetValue.substring(1);
/*      */     }
/*      */     
/* 1918 */     target.setValue(targetValue);
/*      */ 
/*      */     
/* 1921 */     Element eachDefinitionElement = getFirstChildElement(targetSettings);
/* 1922 */     while (eachDefinitionElement != null) {
/* 1923 */       QName definitionType = getQName(eachDefinitionElement);
/* 1924 */       if (TRANSFORM_ELEMENT_QNAME.equals(definitionType)) {
/* 1925 */         EncryptionTarget.Transform transform = readEncTransform(eachDefinitionElement);
/*      */         
/* 1927 */         target.addCipherReferenceTransform(transform);
/*      */       } else {
/* 1929 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", definitionType.toString());
/*      */ 
/*      */ 
/*      */         
/* 1933 */         throw new IllegalStateException(definitionType + " is not a recognized sub-element of EncryptionTarget");
/*      */       } 
/*      */       
/* 1936 */       eachDefinitionElement = getNextElement(eachDefinitionElement);
/*      */     } 
/*      */     
/* 1939 */     return target;
/*      */   }
/*      */   
/*      */   private static Target readTargetSettings(Element targetSettings, boolean signature) {
/* 1943 */     if (signature) {
/* 1944 */       SignatureTarget signatureTarget = new SignatureTarget();
/* 1945 */       signatureTarget.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/* 1946 */       return readTargetSettings(targetSettings, (Target)signatureTarget);
/*      */     } 
/* 1948 */     EncryptionTarget target = new EncryptionTarget();
/* 1949 */     return readTargetSettings(targetSettings, (Target)target);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Target readTargetSettings(Element targetSettings, Target target) {
/* 1958 */     NamedNodeMap targetAttributes = targetSettings.getAttributes();
/* 1959 */     int attributeCount = targetAttributes.getLength();
/* 1960 */     String attributeName = null;
/* 1961 */     for (int index = 0; index < attributeCount; index++) {
/* 1962 */       Attr targetAttribute = (Attr)targetAttributes.item(index);
/* 1963 */       attributeName = targetAttribute.getName();
/*      */       
/* 1965 */       if ("type".equalsIgnoreCase(attributeName)) {
/* 1966 */         String targetType = targetAttribute.getValue();
/*      */         
/* 1968 */         if ("qname".equalsIgnoreCase(targetType)) {
/* 1969 */           target.setType("qname");
/* 1970 */         } else if ("xpath".equalsIgnoreCase(targetType)) {
/* 1971 */           target.setType("xpath");
/* 1972 */         } else if ("uri".equalsIgnoreCase(targetType)) {
/* 1973 */           target.setType("uri");
/*      */         } else {
/* 1975 */           log.log(Level.SEVERE, "WSS0519.illegal.attribute.value", "xwss:Target@Type");
/*      */ 
/*      */           
/* 1978 */           throw new IllegalStateException(targetType + " is not a recognized type of Target");
/*      */         }
/*      */       
/* 1981 */       } else if ("contentOnly".equalsIgnoreCase(attributeName)) {
/*      */         
/* 1983 */         if (targetAttribute.getSpecified()) {
/* 1984 */           validateTargetContentOnly(targetSettings);
/*      */         }
/* 1986 */         String contentOnly = targetAttribute.getValue();
/* 1987 */         target.setContentOnly(getBooleanValue(contentOnly));
/* 1988 */       } else if ("enforce".equalsIgnoreCase(attributeName)) {
/*      */         
/* 1990 */         String enforce_S = targetAttribute.getValue();
/* 1991 */         boolean enforce = getBooleanValue(enforce_S);
/* 1992 */         Node parent = targetSettings.getParentNode();
/* 1993 */         if ("OptionalTargets".equals(parent.getLocalName())) {
/* 1994 */           if (targetAttribute.getSpecified() && enforce) {
/* 1995 */             log.warning("WSS0760.warning.optionaltarget.enforce.ignored");
/*      */           }
/*      */         } else {
/* 1998 */           target.setEnforce(enforce);
/*      */         } 
/*      */       } else {
/* 2001 */         log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:Target" });
/*      */ 
/*      */ 
/*      */         
/* 2005 */         throw new IllegalStateException(attributeName + " is not a recognized attribute of Target");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2011 */     String targetValue = XMLUtil.getFullTextFromChildren(targetSettings);
/*      */     
/* 2013 */     if (targetValue == null || targetValue.equals(""))
/*      */     {
/* 2015 */       throw new IllegalStateException("Value of the Target element is required to be specified");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2020 */     if (targetValue.startsWith("#")) {
/* 2021 */       targetValue = targetValue.substring(1);
/*      */     }
/*      */     
/* 2024 */     target.setValue(targetValue);
/*      */     
/* 2026 */     return target;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SignatureTarget readSignatureTargetSettings(Element targetSettings) {
/* 2033 */     SignatureTarget target = new SignatureTarget();
/*      */ 
/*      */     
/* 2036 */     NamedNodeMap targetAttributes = targetSettings.getAttributes();
/* 2037 */     int attributeCount = targetAttributes.getLength();
/* 2038 */     String attributeName = null;
/* 2039 */     for (int index = 0; index < attributeCount; index++) {
/* 2040 */       Attr targetAttribute = (Attr)targetAttributes.item(index);
/* 2041 */       attributeName = targetAttribute.getName();
/*      */       
/* 2043 */       if ("type".equalsIgnoreCase(attributeName)) {
/* 2044 */         String targetType = targetAttribute.getValue();
/*      */         
/* 2046 */         if ("qname".equalsIgnoreCase(targetType)) {
/* 2047 */           target.setType("qname");
/* 2048 */         } else if ("xpath".equalsIgnoreCase(targetType)) {
/* 2049 */           target.setType("xpath");
/* 2050 */         } else if ("uri".equalsIgnoreCase(targetType)) {
/* 2051 */           target.setType("uri");
/*      */         } else {
/* 2053 */           log.log(Level.SEVERE, "WSS0519.illegal.attribute.value", "xwss:Target@Type");
/*      */ 
/*      */           
/* 2056 */           throw new IllegalStateException(targetType + " is not a recognized type of Target");
/*      */         }
/*      */       
/* 2059 */       } else if ("contentOnly".equalsIgnoreCase(attributeName)) {
/*      */         
/* 2061 */         if (targetAttribute.getSpecified()) {
/* 2062 */           throw new IllegalStateException("invalid contentOnly attribute in a xwss:SignatureTarget");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 2082 */       else if ("enforce".equalsIgnoreCase(attributeName)) {
/*      */         
/* 2084 */         String enforce_S = targetAttribute.getValue();
/* 2085 */         boolean enforce = getBooleanValue(enforce_S);
/* 2086 */         target.setEnforce(enforce);
/* 2087 */       } else if (!"value".equalsIgnoreCase(attributeName)) {
/*      */ 
/*      */         
/* 2090 */         log.log(Level.SEVERE, "WSS0512.illegal.attribute.name", new Object[] { attributeName, "xwss:Target" });
/*      */ 
/*      */ 
/*      */         
/* 2094 */         throw new IllegalStateException(attributeName + " is not a recognized attribute of Target");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2099 */     String targetValue = targetSettings.getAttribute("value");
/* 2100 */     if (targetValue == null)
/*      */     {
/*      */       
/* 2103 */       throw new IllegalStateException("value attribute of the SignatureTarget element missing/empty");
/*      */     }
/*      */     
/* 2106 */     target.setValue(targetValue);
/*      */ 
/*      */     
/* 2109 */     boolean attachmentTxSeen = false;
/* 2110 */     Element eachDefinitionElement = getFirstChildElement(targetSettings);
/* 2111 */     while (eachDefinitionElement != null) {
/* 2112 */       QName definitionType = getQName(eachDefinitionElement);
/* 2113 */       if (DIGEST_METHOD_ELEMENT_QNAME.equals(definitionType)) {
/* 2114 */         String algorithm = readDigestMethod(eachDefinitionElement);
/* 2115 */         target.setDigestAlgorithm(algorithm);
/* 2116 */       } else if (TRANSFORM_ELEMENT_QNAME.equals(definitionType)) {
/* 2117 */         SignatureTarget.Transform transform = readSigTransform(eachDefinitionElement);
/*      */         
/* 2119 */         if (transform.getTransform().equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform") || transform.getTransform().equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete-Transform"))
/*      */         {
/*      */ 
/*      */           
/* 2123 */           attachmentTxSeen = true;
/*      */         }
/* 2125 */         target.addTransform(transform);
/*      */       } else {
/* 2127 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", definitionType.toString());
/*      */ 
/*      */ 
/*      */         
/* 2131 */         throw new IllegalStateException(definitionType + " is not a recognized sub-element of SignatureTarget");
/*      */       } 
/*      */       
/* 2134 */       eachDefinitionElement = getNextElement(eachDefinitionElement);
/*      */     } 
/* 2136 */     if ("".equals(target.getDigestAlgorithm())) {
/* 2137 */       target.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/*      */     }
/* 2139 */     if (target.getValue().startsWith("cid") || target.getValue().startsWith("CID") || target.getValue().startsWith("attachmentRef:"))
/*      */     {
/*      */       
/* 2142 */       if (!attachmentTxSeen) {
/* 2143 */         throw new IllegalStateException("Missing Transform specification for Attachment Target " + target.getValue());
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 2148 */     return target;
/*      */   }
/*      */   
/*      */   private static String readDigestMethod(Element digestMethod) {
/* 2152 */     String algorithm = digestMethod.getAttribute("algorithm");
/* 2153 */     if ("".equals(algorithm)) {
/* 2154 */       throw new IllegalArgumentException("Empty/missing algorithm attribute on SignatureTarget");
/*      */     }
/*      */     
/* 2157 */     return algorithm;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static SignatureTarget.Transform readSigTransform(Element transform) {
/* 2163 */     String algorithm = transform.getAttribute("algorithm");
/* 2164 */     boolean disableInclusivePrefix = false;
/*      */     try {
/* 2166 */       disableInclusivePrefix = parseBoolean("disableInclusivePrefix", transform.getAttribute("disableInclusivePrefix"));
/* 2167 */     } catch (Exception e) {
/* 2168 */       e.printStackTrace();
/*      */     } 
/* 2170 */     if ("".equals(algorithm))
/*      */     {
/* 2172 */       throw new IllegalStateException(" Empty/Missing algorithm attribute on xwss:Transform element");
/*      */     }
/*      */ 
/*      */     
/* 2176 */     Element eachDefinitionElement = getFirstChildElement(transform);
/*      */     
/* 2178 */     SignatureTarget.Transform trans = new SignatureTarget.Transform();
/* 2179 */     trans.setTransform(algorithm);
/* 2180 */     trans.setDisbaleInclusivePrefix(disableInclusivePrefix);
/*      */     
/* 2182 */     if (algorithm.equals("http://www.w3.org/TR/1999/REC-xpath-19991116")) {
/* 2183 */       fillXPATHTransformParams(eachDefinitionElement, trans);
/* 2184 */     } else if (algorithm.equals("http://www.w3.org/2002/06/xmldsig-filter2")) {
/* 2185 */       fillXPATH2TransformParams(eachDefinitionElement, trans);
/* 2186 */     } else if (algorithm.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform")) {
/* 2187 */       fillSTRTransformParams(eachDefinitionElement, trans);
/*      */     }
/* 2189 */     else if (log.getLevel() == Level.FINE) {
/* 2190 */       log.log(Level.FINE, "Algorithm Parameters not supportedfor transform", algorithm);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2195 */     return trans;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void fillXPATHTransformParams(Element algoElement, SignatureTarget.Transform transform) {
/* 2200 */     QName definitionType = getQName(algoElement);
/* 2201 */     if (ALGORITHM_PARAMETER_ELEMENT_QNAME.equals(definitionType)) {
/* 2202 */       String name = algoElement.getAttribute("name");
/* 2203 */       String value = algoElement.getAttribute("value");
/*      */       
/* 2205 */       if (name.equals("XPATH")) {
/* 2206 */         transform.setAlgorithmParameters(new XPathFilterParameterSpec(value));
/*      */       } else {
/* 2208 */         throw new IllegalStateException("XPATH Transform must have XPATH attribute name and an XPATH Expression as value");
/*      */       } 
/*      */     } else {
/*      */       
/* 2212 */       log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", definitionType.toString());
/*      */ 
/*      */ 
/*      */       
/* 2216 */       throw new IllegalStateException(definitionType + " is not a recognized sub-element of Transform");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void fillXPATH2TransformParams(Element algoElement, SignatureTarget.Transform transform) {
/* 2224 */     ArrayList<XPathType> xpathTypeList = new ArrayList();
/* 2225 */     while (algoElement != null) {
/* 2226 */       QName definitionType = getQName(algoElement);
/* 2227 */       if (ALGORITHM_PARAMETER_ELEMENT_QNAME.equals(definitionType)) {
/*      */ 
/*      */         
/* 2230 */         String name = algoElement.getAttribute("name");
/* 2231 */         String value = algoElement.getAttribute("value");
/* 2232 */         if (name.equalsIgnoreCase("UNION")) {
/* 2233 */           xpathTypeList.add(new XPathType(value, XPathType.Filter.UNION));
/* 2234 */         } else if (name.equalsIgnoreCase("INTERSECT")) {
/* 2235 */           xpathTypeList.add(new XPathType(value, XPathType.Filter.INTERSECT));
/* 2236 */         } else if (name.equalsIgnoreCase("SUBTRACT")) {
/* 2237 */           xpathTypeList.add(new XPathType(value, XPathType.Filter.SUBTRACT));
/*      */         } else {
/* 2239 */           throw new IllegalStateException("XPATH2 Transform AlgorithmParameter name attribute should be one of UNION,INTERSECT,SUBTRACT");
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2244 */         log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", definitionType.toString());
/*      */ 
/*      */ 
/*      */         
/* 2248 */         throw new IllegalStateException(definitionType + " is not a recognized sub-element of Transform");
/*      */       } 
/*      */       
/* 2251 */       algoElement = getNextElement(algoElement);
/*      */     } 
/*      */     
/* 2254 */     transform.setAlgorithmParameters(new XPathFilter2ParameterSpec(xpathTypeList));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void fillSTRTransformParams(Element algoElement, SignatureTarget.Transform transform) {
/* 2259 */     QName definitionType = getQName(algoElement);
/* 2260 */     if (ALGORITHM_PARAMETER_ELEMENT_QNAME.equals(definitionType)) {
/* 2261 */       String name = algoElement.getAttribute("name");
/* 2262 */       String value = algoElement.getAttribute("value");
/* 2263 */       transform.setAlgorithmParameters((AlgorithmParameterSpec)new Parameter(name, value));
/*      */     } else {
/* 2265 */       log.log(Level.SEVERE, "WSS0513.illegal.configuration.element", definitionType.toString());
/*      */ 
/*      */ 
/*      */       
/* 2269 */       throw new IllegalStateException(definitionType + " is not a recognized sub-element of Transform");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EncryptionTarget.Transform readEncTransform(Element transform) {
/* 2278 */     String algorithm = transform.getAttribute("algorithm");
/* 2279 */     if ("".equals(algorithm))
/*      */     {
/* 2281 */       throw new IllegalStateException(" Empty/Missing algorithm attribute on xwss:Transform element");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2302 */     EncryptionTarget.Transform trans = new EncryptionTarget.Transform();
/* 2303 */     trans.setTransform(algorithm);
/*      */     
/* 2305 */     return trans;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateContentOnly(Element target) {
/* 2311 */     Node parent = target.getParentNode();
/* 2312 */     String parentName = parent.getLocalName();
/*      */     
/* 2314 */     if ("RequireSignature".equalsIgnoreCase(parentName) || "Sign".equalsIgnoreCase(parentName)) {
/*      */       
/* 2316 */       String targetValue = target.getAttribute("value");
/* 2317 */       String targetType = target.getAttribute("type");
/* 2318 */       if ("uri".equalsIgnoreCase(targetType)) {
/* 2319 */         if (!targetValue.startsWith("cid") && !targetValue.startsWith("CID"))
/*      */         {
/* 2321 */           throw new IllegalStateException("invalid contentOnly attribute on a non-attachment SignatureTarget");
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 2326 */         throw new IllegalStateException("invalid contentOnly attribute in a SignatureTarget");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2331 */     if (!"Encrypt".equalsIgnoreCase(parentName) && !"RequireEncryption".equalsIgnoreCase(parentName))
/*      */     {
/*      */       
/* 2334 */       throw new IllegalStateException("contentOnly attribute not allowed on Targets under element " + parentName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateSAMLKeyReferenceType(String typeName) {
/* 2341 */     if (!"Identifier".equalsIgnoreCase(typeName) && !"Embedded".equalsIgnoreCase(typeName))
/*      */     {
/* 2343 */       throw new IllegalStateException("Reference Type " + typeName + " not allowed for SAMLAssertion References");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateRequireSAMLType(String type, Element samlTokenSettings) {
/* 2351 */     if (!"SV".equals(type)) {
/* 2352 */       throw new IllegalStateException("Allowed Assertion Types for <xwss:RequireSAMLAssertion> is SV only");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2357 */     Node parent = samlTokenSettings.getParentNode();
/* 2358 */     if (parent == null)
/*      */     {
/* 2360 */       throw new IllegalStateException("<xwss:RequireSAMLAssertion> cannot occur at this position");
/*      */     }
/*      */ 
/*      */     
/* 2364 */     String parentName = parent.getLocalName();
/* 2365 */     if (!"SecurityConfiguration".equals(parentName)) {
/* 2366 */       throw new IllegalStateException("<xwss:RequireSAMLAssertion> of Type=SV cannot occur as child of " + parentName);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void validateSAMLType(String type, Element samlTokenSettings) {
/* 2372 */     if (!"SV".equals(type) && !"HOK".equals(type)) {
/* 2373 */       throw new IllegalStateException(type + " not a valid SAML Assertion Type, require one of HOK|SV");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2378 */     if ("SV".equals(type)) {
/*      */       
/* 2380 */       Node parent = samlTokenSettings.getParentNode();
/* 2381 */       if (parent == null)
/*      */       {
/* 2383 */         throw new IllegalStateException("SAML Assertion cannot occur at this position");
/*      */       }
/*      */ 
/*      */       
/* 2387 */       String parentName = parent.getLocalName();
/* 2388 */       if (!"SecurityConfiguration".equals(parentName)) {
/* 2389 */         throw new IllegalStateException("SAML Assertion of Type=SV cannot occur as child of " + parentName);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean dynamicPolicy(Element configData) {
/* 2396 */     String dynamicFlag = configData.getAttribute("enableDynamicPolicy");
/* 2397 */     NodeList nl = configData.getElementsByTagName("*");
/*      */     
/* 2399 */     if ("".equals(dynamicFlag) || "false".equals(dynamicFlag) || "0".equals(dynamicFlag))
/*      */     {
/* 2401 */       return false;
/*      */     }
/* 2403 */     if (("true".equals(dynamicFlag) || "1".equals(dynamicFlag)) && 
/* 2404 */       nl.getLength() == 0) {
/* 2405 */       return true;
/*      */     }
/*      */     
/* 2408 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean getBSPAttribute(Element configData, ApplicationSecurityConfiguration parent) {
/* 2412 */     String conformanceValue = configData.getAttribute("conformance");
/* 2413 */     if ("bsp".equals(conformanceValue))
/* 2414 */       return true; 
/* 2415 */     if ("".equals(conformanceValue) && parent != null) {
/* 2416 */       return parent.isBSP();
/*      */     }
/* 2418 */     return false;
/*      */   }
/*      */   
/*      */   private static String getIdAttribute(Element configData) {
/* 2422 */     String id = configData.getAttribute("id");
/*      */     
/* 2424 */     if (id.startsWith("#")) {
/* 2425 */       throw new IllegalArgumentException("Illegal id attribute " + id + ", id attributes on policy elements cannot begin with a '#' character");
/*      */     }
/*      */ 
/*      */     
/* 2429 */     if ("".equals(id)) {
/* 2430 */       id = generateUUID();
/*      */     }
/* 2432 */     return id;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String generateUUID() {
/* 2438 */     int intRandom = rnd.nextInt();
/* 2439 */     String id = "XWSSGID-" + String.valueOf(System.currentTimeMillis()) + String.valueOf(intRandom);
/* 2440 */     return id;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void validateTargetContentOnly(Element target) {
/* 2445 */     Node parent = target.getParentNode();
/* 2446 */     String parentName = parent.getLocalName();
/* 2447 */     if (!"Encrypt".equalsIgnoreCase(parentName) && !"RequireEncryption".equalsIgnoreCase(parentName))
/*      */     {
/*      */       
/* 2450 */       throw new IllegalStateException("contentOnly attribute not allowed on Targets under element " + parentName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getSecurityEnvironmentHandler(Element element) {
/* 2458 */     int secEnvCount = 0;
/* 2459 */     Element eachDefinitionElement = getFirstChildElement(element);
/* 2460 */     String handlerClsName = null;
/*      */     
/* 2462 */     while (eachDefinitionElement != null) {
/* 2463 */       QName definitionType = getQName(eachDefinitionElement);
/* 2464 */       if (SECURITY_ENVIRONMENT_HANDLER_ELEMENT_QNAME.equals(definitionType)) {
/* 2465 */         if (secEnvCount > 0)
/*      */         {
/* 2467 */           throw new IllegalStateException("More than one <xwss:SecurityEnvironmentHandler> element under " + element.getTagName());
/*      */         }
/*      */ 
/*      */         
/* 2471 */         secEnvCount++;
/*      */         
/* 2473 */         handlerClsName = XMLUtil.getFullTextFromChildren(eachDefinitionElement);
/*      */ 
/*      */         
/* 2476 */         if (handlerClsName == null || handlerClsName.equals(""))
/*      */         {
/* 2478 */           throw new IllegalStateException("A Handler class name has to be specified in security configuration file");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2483 */       eachDefinitionElement = getNextElement(eachDefinitionElement);
/*      */     } 
/* 2485 */     return handlerClsName;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readAlgorithmProperties(HashMap<String, String> props, Element eachDefinitionElement) {
/* 2491 */     String name = eachDefinitionElement.getAttribute("name");
/* 2492 */     String value = eachDefinitionElement.getAttribute("value");
/* 2493 */     props.put(name, value);
/*      */   }
/*      */   
/*      */   private static boolean getBooleanValue(String valueString) {
/* 2497 */     if ("0".equals(valueString) || "false".equalsIgnoreCase(valueString)) {
/* 2498 */       return false;
/*      */     }
/* 2500 */     if ("1".equals(valueString) || "true".equalsIgnoreCase(valueString)) {
/* 2501 */       return true;
/*      */     }
/* 2503 */     log.log(Level.SEVERE, "WSS0511.illegal.boolean.value", valueString);
/* 2504 */     throw new IllegalArgumentException(valueString + " is not a valid boolean value");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void applyDefaults(TimestampPolicy policy, boolean dp) {
/* 2509 */     if (policy.getTimeout() == 0L) {
/* 2510 */       policy.setTimeout(5000L);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyDefaults(EncryptionPolicy policy, boolean dp) {
/* 2517 */     EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*      */     
/* 2519 */     boolean targetsEmpty = (featureBinding.getTargetBindings().size() == 0);
/* 2520 */     if (!dp && targetsEmpty) {
/*      */ 
/*      */       
/* 2523 */       EncryptionTarget encryptionTarget = new EncryptionTarget();
/* 2524 */       featureBinding.addTargetBinding((Target)encryptionTarget);
/*      */     } 
/* 2526 */     if (policy.getKeyBinding() == null) {
/* 2527 */       AuthenticationTokenPolicy.X509CertificateBinding x509Policy = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/*      */       
/* 2529 */       x509Policy.setReferenceType("Direct");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyDefaults(SignaturePolicy policy, boolean dp) {
/* 2537 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)policy.getFeatureBinding();
/*      */ 
/*      */     
/* 2540 */     boolean targetsEmpty = (featureBinding.getTargetBindings().size() == 0);
/*      */ 
/*      */ 
/*      */     
/* 2544 */     if (!dp && targetsEmpty) {
/*      */       
/* 2546 */       SignatureTarget t = new SignatureTarget();
/* 2547 */       t.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/* 2548 */       featureBinding.addTargetBinding(t);
/*      */     } 
/* 2550 */     if (policy.getKeyBinding() == null) {
/* 2551 */       AuthenticationTokenPolicy.X509CertificateBinding x509Binding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/*      */       
/* 2553 */       x509Binding.newPrivateKeyBinding();
/* 2554 */       x509Binding.setReferenceType("Direct");
/*      */     } 
/*      */     
/* 2557 */     if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)policy.getKeyBinding())) {
/* 2558 */       setDefaultKeyAlgorithm((SecurityPolicy)policy.getKeyBinding(), "http://www.w3.org/2000/09/xmldsig#hmac-sha1");
/*      */     } else {
/* 2560 */       setDefaultKeyAlgorithm((SecurityPolicy)policy.getKeyBinding(), "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
/*      */     } 
/* 2562 */     if ("".equals(featureBinding.getCanonicalizationAlgorithm())) {
/* 2563 */       featureBinding.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyDefaults(AuthenticationTokenPolicy.UsernameTokenBinding policy, boolean dp) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyDefaults(AuthenticationTokenPolicy.SAMLAssertionBinding policy, boolean dp) {
/* 2578 */     if ("".equals(policy.getReferenceType())) {
/* 2579 */       policy.setReferenceType("Identifier");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyReceiverDefaults(SignaturePolicy policy, boolean bsp, boolean dp) {
/* 2586 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)policy.getFeatureBinding();
/*      */     
/* 2588 */     boolean targetsEmpty = (featureBinding.getTargetBindings().size() == 0);
/* 2589 */     if (!dp && targetsEmpty) {
/*      */       
/* 2591 */       SignatureTarget t = new SignatureTarget();
/*      */       
/* 2593 */       t.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/* 2594 */       featureBinding.addTargetBinding(t);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2605 */     policy.isBSP(bsp);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyReceiverDefaults(EncryptionPolicy policy, boolean bsp, boolean dp) {
/* 2611 */     EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/*      */     
/* 2613 */     boolean targetsEmpty = (featureBinding.getTargetBindings().size() == 0);
/* 2614 */     if (!dp && targetsEmpty) {
/*      */ 
/*      */       
/* 2617 */       EncryptionTarget encryptionTarget = new EncryptionTarget();
/* 2618 */       featureBinding.addTargetBinding((Target)encryptionTarget);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2632 */     policy.isBSP(bsp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyReceiverDefaults(AuthenticationTokenPolicy.UsernameTokenBinding policy, boolean bsp, String securityHandlerClass, boolean dp) throws PolicyGenerationException {
/* 2642 */     policy.isBSP(bsp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyReceiverDefaults(TimestampPolicy timestampPolicy, boolean bsp, String securityHandlerClass, boolean dp) {
/* 2649 */     if (timestampPolicy.getMaxClockSkew() == 0L) {
/* 2650 */       timestampPolicy.setMaxClockSkew(300000L);
/*      */     }
/*      */     
/* 2653 */     if (timestampPolicy.getTimestampFreshness() == 0L) {
/* 2654 */       timestampPolicy.setTimestampFreshness(300000L);
/*      */     }
/*      */     
/* 2657 */     timestampPolicy.isBSP(bsp);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void applyReceiverDefaults(AuthenticationTokenPolicy.SAMLAssertionBinding policy, boolean bsp, boolean dp) {
/* 2663 */     if ("".equals(policy.getReferenceType())) {
/* 2664 */       policy.setReferenceType("Identifier");
/*      */     }
/* 2666 */     policy.isBSP(bsp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean configHasSingleService(Element config) {
/* 2671 */     NodeList services = config.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "Service");
/*      */ 
/*      */     
/* 2674 */     if (services.getLength() > 1 || services.getLength() == 0) {
/* 2675 */       return false;
/*      */     }
/* 2677 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean configHasSingleServiceAndNoPorts(Element config) {
/* 2682 */     NodeList services = config.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "Service");
/*      */ 
/*      */     
/* 2685 */     if (services.getLength() > 1 || services.getLength() == 0) {
/* 2686 */       return false;
/*      */     }
/*      */     
/* 2689 */     NodeList ports = config.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "Port");
/*      */ 
/*      */ 
/*      */     
/* 2693 */     if (ports.getLength() == 0) {
/* 2694 */       return true;
/*      */     }
/* 2696 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean configHasOperations(Element config) {
/* 2700 */     NodeList ops = config.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "Operation");
/*      */ 
/*      */     
/* 2703 */     if (ops.getLength() > 0) {
/* 2704 */       return true;
/*      */     }
/* 2706 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkIdUniqueness(Element elem) {
/* 2711 */     NodeList nl = elem.getElementsByTagNameNS("http://java.sun.com/xml/ns/xwss/config", "*");
/* 2712 */     int len = nl.getLength();
/* 2713 */     HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 2714 */     for (int i = 0; i < len; i++) {
/* 2715 */       Element subElem = (Element)nl.item(i);
/* 2716 */       String idAttr = subElem.getAttribute("id");
/* 2717 */       if (!"".equals(idAttr)) {
/* 2718 */         if (map.containsKey(idAttr)) {
/* 2719 */           throw new IllegalArgumentException("id attribute value '" + idAttr + "' not unique");
/*      */         }
/* 2721 */         map.put(idAttr, idAttr);
/*      */       } 
/*      */ 
/*      */       
/* 2725 */       idAttr = subElem.getAttribute("strId");
/* 2726 */       if (!"".equals(idAttr)) {
/* 2727 */         if (map.containsKey(idAttr)) {
/* 2728 */           throw new IllegalArgumentException("strId/id attribute value '" + idAttr + "' not unique");
/*      */         }
/* 2730 */         map.put(idAttr, idAttr);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\config\SecurityConfigurationXmlReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */