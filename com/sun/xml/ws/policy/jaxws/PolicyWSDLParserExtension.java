/*      */ package com.sun.xml.ws.policy.jaxws;
/*      */ 
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLObject;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*      */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*      */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*      */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLModelImpl;
/*      */ import com.sun.xml.ws.policy.PolicyException;
/*      */ import com.sun.xml.ws.policy.PolicyMap;
/*      */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*      */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*      */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*      */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModelContext;
/*      */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*      */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*      */ import com.sun.xml.ws.resources.PolicyMessages;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLInputFactory;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.ws.WebServiceException;
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
/*      */ public final class PolicyWSDLParserExtension
/*      */   extends WSDLParserExtension
/*      */ {
/*      */   enum HandlerType
/*      */   {
/*   90 */     PolicyUri, AnonymousPolicyId;
/*      */   }
/*      */   
/*      */   static final class PolicyRecordHandler {
/*      */     String handler;
/*      */     PolicyWSDLParserExtension.HandlerType type;
/*      */     
/*      */     PolicyRecordHandler(PolicyWSDLParserExtension.HandlerType type, String handler) {
/*   98 */       this.type = type;
/*   99 */       this.handler = handler;
/*      */     }
/*      */     
/*      */     PolicyWSDLParserExtension.HandlerType getType() {
/*  103 */       return this.type;
/*      */     }
/*      */     
/*      */     String getHandler() {
/*  107 */       return this.handler;
/*      */     }
/*      */   }
/*      */   
/*  111 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyWSDLParserExtension.class);
/*      */ 
/*      */   
/*  114 */   private static final StringBuffer AnonymnousPolicyIdPrefix = new StringBuffer("#__anonymousPolicy__ID");
/*      */ 
/*      */   
/*      */   private int anonymousPoliciesCount;
/*      */   
/*  119 */   private final SafePolicyReader policyReader = new SafePolicyReader();
/*      */ 
/*      */   
/*  122 */   private SafePolicyReader.PolicyRecord expandQueueHead = null;
/*      */ 
/*      */   
/*  125 */   private Map<String, SafePolicyReader.PolicyRecord> policyRecordsPassedBy = null;
/*      */   
/*  127 */   private Map<String, PolicySourceModel> anonymousPolicyModels = null;
/*      */ 
/*      */   
/*  130 */   private List<String> unresolvedUris = null;
/*      */ 
/*      */   
/*  133 */   private final LinkedList<String> urisNeeded = new LinkedList<String>();
/*  134 */   private final Map<String, PolicySourceModel> modelsNeeded = new HashMap<String, PolicySourceModel>();
/*      */ 
/*      */   
/*  137 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4ServiceMap = null;
/*  138 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4PortMap = null;
/*  139 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4PortTypeMap = null;
/*  140 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingMap = null;
/*  141 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BoundOperationMap = null;
/*  142 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4OperationMap = null;
/*  143 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4MessageMap = null;
/*  144 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4InputMap = null;
/*  145 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4OutputMap = null;
/*  146 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4FaultMap = null;
/*  147 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingInputOpMap = null;
/*  148 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingOutputOpMap = null;
/*  149 */   private Map<WSDLObject, Collection<PolicyRecordHandler>> handlers4BindingFaultOpMap = null;
/*      */   
/*  151 */   private PolicyMapBuilder policyBuilder = new PolicyMapBuilder();
/*      */   
/*      */   private boolean isPolicyProcessed(String policyUri) {
/*  154 */     return this.modelsNeeded.containsKey(policyUri);
/*      */   }
/*      */   
/*      */   private void addNewPolicyNeeded(String policyUri, PolicySourceModel policyModel) {
/*  158 */     if (!this.modelsNeeded.containsKey(policyUri)) {
/*  159 */       this.modelsNeeded.put(policyUri, policyModel);
/*  160 */       this.urisNeeded.addFirst(policyUri);
/*      */     } 
/*      */   }
/*      */   
/*      */   private Map<String, PolicySourceModel> getPolicyModels() {
/*  165 */     return this.modelsNeeded;
/*      */   }
/*      */   
/*      */   private Map<String, SafePolicyReader.PolicyRecord> getPolicyRecordsPassedBy() {
/*  169 */     if (null == this.policyRecordsPassedBy) {
/*  170 */       this.policyRecordsPassedBy = new HashMap<String, SafePolicyReader.PolicyRecord>();
/*      */     }
/*  172 */     return this.policyRecordsPassedBy;
/*      */   }
/*      */   
/*      */   private Map<String, PolicySourceModel> getAnonymousPolicyModels() {
/*  176 */     if (null == this.anonymousPolicyModels) {
/*  177 */       this.anonymousPolicyModels = new HashMap<String, PolicySourceModel>();
/*      */     }
/*  179 */     return this.anonymousPolicyModels;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4ServiceMap() {
/*  183 */     if (null == this.handlers4ServiceMap) {
/*  184 */       this.handlers4ServiceMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  186 */     return this.handlers4ServiceMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4PortMap() {
/*  190 */     if (null == this.handlers4PortMap) {
/*  191 */       this.handlers4PortMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  193 */     return this.handlers4PortMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4PortTypeMap() {
/*  197 */     if (null == this.handlers4PortTypeMap) {
/*  198 */       this.handlers4PortTypeMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  200 */     return this.handlers4PortTypeMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingMap() {
/*  204 */     if (null == this.handlers4BindingMap) {
/*  205 */       this.handlers4BindingMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  207 */     return this.handlers4BindingMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4OperationMap() {
/*  211 */     if (null == this.handlers4OperationMap) {
/*  212 */       this.handlers4OperationMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  214 */     return this.handlers4OperationMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BoundOperationMap() {
/*  218 */     if (null == this.handlers4BoundOperationMap) {
/*  219 */       this.handlers4BoundOperationMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  221 */     return this.handlers4BoundOperationMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4MessageMap() {
/*  225 */     if (null == this.handlers4MessageMap) {
/*  226 */       this.handlers4MessageMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  228 */     return this.handlers4MessageMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4InputMap() {
/*  232 */     if (null == this.handlers4InputMap) {
/*  233 */       this.handlers4InputMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  235 */     return this.handlers4InputMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4OutputMap() {
/*  239 */     if (null == this.handlers4OutputMap) {
/*  240 */       this.handlers4OutputMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  242 */     return this.handlers4OutputMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4FaultMap() {
/*  246 */     if (null == this.handlers4FaultMap) {
/*  247 */       this.handlers4FaultMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  249 */     return this.handlers4FaultMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingInputOpMap() {
/*  253 */     if (null == this.handlers4BindingInputOpMap) {
/*  254 */       this.handlers4BindingInputOpMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  256 */     return this.handlers4BindingInputOpMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingOutputOpMap() {
/*  260 */     if (null == this.handlers4BindingOutputOpMap) {
/*  261 */       this.handlers4BindingOutputOpMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  263 */     return this.handlers4BindingOutputOpMap;
/*      */   }
/*      */   
/*      */   private Map<WSDLObject, Collection<PolicyRecordHandler>> getHandlers4BindingFaultOpMap() {
/*  267 */     if (null == this.handlers4BindingFaultOpMap) {
/*  268 */       this.handlers4BindingFaultOpMap = new HashMap<WSDLObject, Collection<PolicyRecordHandler>>();
/*      */     }
/*  270 */     return this.handlers4BindingFaultOpMap;
/*      */   }
/*      */   
/*      */   private List<String> getUnresolvedUris(boolean emptyListNeeded) {
/*  274 */     if (null == this.unresolvedUris || emptyListNeeded) {
/*  275 */       this.unresolvedUris = new LinkedList<String>();
/*      */     }
/*  277 */     return this.unresolvedUris;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void policyRecToExpandQueue(SafePolicyReader.PolicyRecord policyRec) {
/*  283 */     if (null == this.expandQueueHead) {
/*  284 */       this.expandQueueHead = policyRec;
/*      */     } else {
/*  286 */       this.expandQueueHead = this.expandQueueHead.insert(policyRec);
/*      */     } 
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
/*      */   private PolicyRecordHandler readSinglePolicy(SafePolicyReader.PolicyRecord policyRec, boolean inner) {
/*  299 */     PolicyRecordHandler handler = null;
/*  300 */     String policyId = policyRec.policyModel.getPolicyId();
/*  301 */     if (policyId == null) {
/*  302 */       policyId = policyRec.policyModel.getPolicyName();
/*      */     }
/*  304 */     if (policyId != null) {
/*  305 */       handler = new PolicyRecordHandler(HandlerType.PolicyUri, policyRec.getUri());
/*  306 */       getPolicyRecordsPassedBy().put(policyRec.getUri(), policyRec);
/*  307 */       policyRecToExpandQueue(policyRec);
/*  308 */     } else if (inner) {
/*  309 */       String anonymousId = AnonymnousPolicyIdPrefix.append(this.anonymousPoliciesCount++).toString();
/*  310 */       handler = new PolicyRecordHandler(HandlerType.AnonymousPolicyId, anonymousId);
/*  311 */       getAnonymousPolicyModels().put(anonymousId, policyRec.policyModel);
/*  312 */       if (null != policyRec.unresolvedURIs) {
/*  313 */         getUnresolvedUris(false).addAll(policyRec.unresolvedURIs);
/*      */       }
/*      */     } 
/*  316 */     return handler;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addHandlerToMap(Map<WSDLObject, Collection<PolicyRecordHandler>> map, WSDLObject key, PolicyRecordHandler handler) {
/*  322 */     if (map.containsKey(key)) {
/*  323 */       ((Collection<PolicyRecordHandler>)map.get(key)).add(handler);
/*      */     } else {
/*  325 */       Collection<PolicyRecordHandler> newSet = new LinkedList<PolicyRecordHandler>();
/*  326 */       newSet.add(handler);
/*  327 */       map.put(key, newSet);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getBaseUrl(String policyUri) {
/*  332 */     if (null == policyUri) {
/*  333 */       return null;
/*      */     }
/*      */     
/*  336 */     int fragmentIdx = policyUri.indexOf('#');
/*  337 */     return (fragmentIdx == -1) ? policyUri : policyUri.substring(0, fragmentIdx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processReferenceUri(String policyUri, WSDLObject element, XMLStreamReader reader, Map<WSDLObject, Collection<PolicyRecordHandler>> map) {
/*  348 */     if (null == policyUri || policyUri.length() == 0) {
/*      */       return;
/*      */     }
/*  351 */     if ('#' != policyUri.charAt(0)) {
/*  352 */       getUnresolvedUris(false).add(policyUri);
/*      */     }
/*      */     
/*  355 */     addHandlerToMap(map, element, new PolicyRecordHandler(HandlerType.PolicyUri, SafePolicyReader.relativeToAbsoluteUrl(policyUri, reader.getLocation().getSystemId())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean processSubelement(WSDLObject element, XMLStreamReader reader, Map<WSDLObject, Collection<PolicyRecordHandler>> map) {
/*  363 */     if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.PolicyReference) {
/*  364 */       processReferenceUri(this.policyReader.readPolicyReferenceElement(reader), element, reader, map);
/*  365 */       return true;
/*  366 */     }  if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.Policy) {
/*  367 */       PolicyRecordHandler handler = readSinglePolicy(this.policyReader.readPolicyElement(reader, (null == reader.getLocation().getSystemId()) ? "" : reader.getLocation().getSystemId()), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  374 */       if (null != handler) {
/*  375 */         addHandlerToMap(map, element, handler);
/*      */       }
/*  377 */       return true;
/*      */     } 
/*  379 */     return false;
/*      */   }
/*      */   
/*      */   private void processAttributes(WSDLObject element, XMLStreamReader reader, Map<WSDLObject, Collection<PolicyRecordHandler>> map) {
/*  383 */     String[] uriArray = getPolicyURIsFromAttr(reader);
/*  384 */     if (null != uriArray) {
/*  385 */       for (String policyUri : uriArray) {
/*  386 */         processReferenceUri(policyUri, element, reader, map);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/*  393 */     LOGGER.entering();
/*  394 */     boolean result = processSubelement((WSDLObject)port, reader, getHandlers4PortMap());
/*  395 */     LOGGER.exiting();
/*  396 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void portAttributes(WSDLPort port, XMLStreamReader reader) {
/*  401 */     LOGGER.entering();
/*  402 */     processAttributes((WSDLObject)port, reader, getHandlers4PortMap());
/*  403 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean serviceElements(WSDLService service, XMLStreamReader reader) {
/*  408 */     LOGGER.entering();
/*  409 */     boolean result = processSubelement((WSDLObject)service, reader, getHandlers4ServiceMap());
/*  410 */     LOGGER.exiting();
/*  411 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void serviceAttributes(WSDLService service, XMLStreamReader reader) {
/*  416 */     LOGGER.entering();
/*  417 */     processAttributes((WSDLObject)service, reader, getHandlers4ServiceMap());
/*  418 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean definitionsElements(XMLStreamReader reader) {
/*  424 */     LOGGER.entering();
/*  425 */     if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.Policy) {
/*  426 */       readSinglePolicy(this.policyReader.readPolicyElement(reader, (null == reader.getLocation().getSystemId()) ? "" : reader.getLocation().getSystemId()), false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  432 */       LOGGER.exiting();
/*  433 */       return true;
/*      */     } 
/*  435 */     LOGGER.exiting();
/*  436 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/*  441 */     LOGGER.entering();
/*  442 */     boolean result = processSubelement((WSDLObject)binding, reader, getHandlers4BindingMap());
/*  443 */     LOGGER.exiting();
/*  444 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindingAttributes(WSDLBoundPortType binding, XMLStreamReader reader) {
/*  449 */     LOGGER.entering();
/*  450 */     processAttributes((WSDLObject)binding, reader, getHandlers4BindingMap());
/*  451 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean portTypeElements(WSDLPortType portType, XMLStreamReader reader) {
/*  456 */     LOGGER.entering();
/*  457 */     boolean result = processSubelement((WSDLObject)portType, reader, getHandlers4PortTypeMap());
/*  458 */     LOGGER.exiting();
/*  459 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void portTypeAttributes(WSDLPortType portType, XMLStreamReader reader) {
/*  464 */     LOGGER.entering();
/*  465 */     processAttributes((WSDLObject)portType, reader, getHandlers4PortTypeMap());
/*  466 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean portTypeOperationElements(WSDLOperation operation, XMLStreamReader reader) {
/*  471 */     LOGGER.entering();
/*  472 */     boolean result = processSubelement((WSDLObject)operation, reader, getHandlers4OperationMap());
/*  473 */     LOGGER.exiting();
/*  474 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void portTypeOperationAttributes(WSDLOperation operation, XMLStreamReader reader) {
/*  479 */     LOGGER.entering();
/*  480 */     processAttributes((WSDLObject)operation, reader, getHandlers4OperationMap());
/*  481 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bindingOperationElements(WSDLBoundOperation boundOperation, XMLStreamReader reader) {
/*  486 */     LOGGER.entering();
/*  487 */     boolean result = processSubelement((WSDLObject)boundOperation, reader, getHandlers4BoundOperationMap());
/*  488 */     LOGGER.exiting();
/*  489 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindingOperationAttributes(WSDLBoundOperation boundOperation, XMLStreamReader reader) {
/*  494 */     LOGGER.entering();
/*  495 */     processAttributes((WSDLObject)boundOperation, reader, getHandlers4BoundOperationMap());
/*  496 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean messageElements(WSDLMessage msg, XMLStreamReader reader) {
/*  501 */     LOGGER.entering();
/*  502 */     boolean result = processSubelement((WSDLObject)msg, reader, getHandlers4MessageMap());
/*  503 */     LOGGER.exiting();
/*  504 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void messageAttributes(WSDLMessage msg, XMLStreamReader reader) {
/*  509 */     LOGGER.entering();
/*  510 */     processAttributes((WSDLObject)msg, reader, getHandlers4MessageMap());
/*  511 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean portTypeOperationInputElements(WSDLInput input, XMLStreamReader reader) {
/*  516 */     LOGGER.entering();
/*  517 */     boolean result = processSubelement((WSDLObject)input, reader, getHandlers4InputMap());
/*  518 */     LOGGER.exiting();
/*  519 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void portTypeOperationInputAttributes(WSDLInput input, XMLStreamReader reader) {
/*  524 */     LOGGER.entering();
/*  525 */     processAttributes((WSDLObject)input, reader, getHandlers4InputMap());
/*  526 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean portTypeOperationOutputElements(WSDLOutput output, XMLStreamReader reader) {
/*  532 */     LOGGER.entering();
/*  533 */     boolean result = processSubelement((WSDLObject)output, reader, getHandlers4OutputMap());
/*  534 */     LOGGER.exiting();
/*  535 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void portTypeOperationOutputAttributes(WSDLOutput output, XMLStreamReader reader) {
/*  540 */     LOGGER.entering();
/*  541 */     processAttributes((WSDLObject)output, reader, getHandlers4OutputMap());
/*  542 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean portTypeOperationFaultElements(WSDLFault fault, XMLStreamReader reader) {
/*  548 */     LOGGER.entering();
/*  549 */     boolean result = processSubelement((WSDLObject)fault, reader, getHandlers4FaultMap());
/*  550 */     LOGGER.exiting();
/*  551 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void portTypeOperationFaultAttributes(WSDLFault fault, XMLStreamReader reader) {
/*  556 */     LOGGER.entering();
/*  557 */     processAttributes((WSDLObject)fault, reader, getHandlers4FaultMap());
/*  558 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bindingOperationInputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/*  563 */     LOGGER.entering();
/*  564 */     boolean result = processSubelement((WSDLObject)operation, reader, getHandlers4BindingInputOpMap());
/*  565 */     LOGGER.exiting();
/*  566 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindingOperationInputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/*  571 */     LOGGER.entering();
/*  572 */     processAttributes((WSDLObject)operation, reader, getHandlers4BindingInputOpMap());
/*  573 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean bindingOperationOutputElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/*  579 */     LOGGER.entering();
/*  580 */     boolean result = processSubelement((WSDLObject)operation, reader, getHandlers4BindingOutputOpMap());
/*  581 */     LOGGER.exiting();
/*  582 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindingOperationOutputAttributes(WSDLBoundOperation operation, XMLStreamReader reader) {
/*  587 */     LOGGER.entering();
/*  588 */     processAttributes((WSDLObject)operation, reader, getHandlers4BindingOutputOpMap());
/*  589 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bindingOperationFaultElements(WSDLBoundFault fault, XMLStreamReader reader) {
/*  594 */     LOGGER.entering();
/*  595 */     boolean result = processSubelement((WSDLObject)fault, reader, getHandlers4BindingFaultOpMap());
/*  596 */     LOGGER.exiting(Boolean.valueOf(result));
/*  597 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void bindingOperationFaultAttributes(WSDLBoundFault fault, XMLStreamReader reader) {
/*  602 */     LOGGER.entering();
/*  603 */     processAttributes((WSDLObject)fault, reader, getHandlers4BindingFaultOpMap());
/*  604 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */   
/*      */   private PolicyMapBuilder getPolicyMapBuilder() {
/*  609 */     if (null == this.policyBuilder) {
/*  610 */       this.policyBuilder = new PolicyMapBuilder();
/*      */     }
/*  612 */     return this.policyBuilder;
/*      */   }
/*      */ 
/*      */   
/*      */   private Collection<String> getPolicyURIs(Collection<PolicyRecordHandler> handlers, PolicySourceModelContext modelContext) throws PolicyException {
/*  617 */     Collection<String> result = new ArrayList<String>(handlers.size());
/*      */     
/*  619 */     for (PolicyRecordHandler handler : handlers) {
/*  620 */       String policyUri = handler.handler;
/*  621 */       if (HandlerType.AnonymousPolicyId == handler.type) {
/*  622 */         PolicySourceModel policyModel = getAnonymousPolicyModels().get(policyUri);
/*  623 */         policyModel.expand(modelContext);
/*  624 */         while (getPolicyModels().containsKey(policyUri)) {
/*  625 */           policyUri = AnonymnousPolicyIdPrefix.append(this.anonymousPoliciesCount++).toString();
/*      */         }
/*  627 */         getPolicyModels().put(policyUri, policyModel);
/*      */       } 
/*  629 */       result.add(policyUri);
/*      */     } 
/*  631 */     return result;
/*      */   }
/*      */   
/*      */   private boolean readExternalFile(String fileUrl) {
/*  635 */     InputStream ios = null;
/*  636 */     XMLStreamReader reader = null;
/*      */     try {
/*  638 */       URL xmlURL = new URL(fileUrl);
/*  639 */       ios = xmlURL.openStream();
/*  640 */       reader = XMLInputFactory.newInstance().createXMLStreamReader(ios);
/*  641 */       while (reader.hasNext()) {
/*  642 */         if (reader.isStartElement() && NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.Policy) {
/*  643 */           readSinglePolicy(this.policyReader.readPolicyElement(reader, fileUrl), false);
/*      */         }
/*  645 */         reader.next();
/*      */       } 
/*  647 */       return true;
/*  648 */     } catch (IOException ioe) {
/*  649 */       return false;
/*  650 */     } catch (XMLStreamException xmlse) {
/*  651 */       return false;
/*      */     } finally {
/*  653 */       PolicyUtils.IO.closeResource(reader);
/*  654 */       PolicyUtils.IO.closeResource(ios);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void finished(WSDLParserExtensionContext context) {
/*  660 */     LOGGER.entering(new Object[] { context });
/*      */     
/*  662 */     if (null != this.expandQueueHead) {
/*  663 */       List<String> externalUris = getUnresolvedUris(false);
/*  664 */       getUnresolvedUris(true);
/*  665 */       LinkedList<String> baseUnresolvedUris = new LinkedList<String>();
/*  666 */       for (SafePolicyReader.PolicyRecord currentRec = this.expandQueueHead; null != currentRec; currentRec = currentRec.next) {
/*  667 */         baseUnresolvedUris.addFirst(currentRec.getUri());
/*      */       }
/*  669 */       getUnresolvedUris(false).addAll(baseUnresolvedUris);
/*  670 */       this.expandQueueHead = null;
/*  671 */       getUnresolvedUris(false).addAll(externalUris);
/*      */     } 
/*      */     
/*  674 */     while (!getUnresolvedUris(false).isEmpty()) {
/*  675 */       List<String> urisToBeSolvedList = getUnresolvedUris(false);
/*  676 */       getUnresolvedUris(true);
/*  677 */       for (String currentUri : urisToBeSolvedList) {
/*  678 */         if (!isPolicyProcessed(currentUri)) {
/*  679 */           SafePolicyReader.PolicyRecord prefetchedRecord = getPolicyRecordsPassedBy().get(currentUri);
/*  680 */           if (null == prefetchedRecord) {
/*  681 */             if (this.policyReader.getUrlsRead().contains(getBaseUrl(currentUri))) {
/*  682 */               LOGGER.logSevereException((Throwable)new PolicyException(PolicyMessages.WSP_1014_CAN_NOT_FIND_POLICY(currentUri))); continue;
/*      */             } 
/*  684 */             if (readExternalFile(getBaseUrl(currentUri))) {
/*  685 */               getUnresolvedUris(false).add(currentUri);
/*      */             }
/*      */             continue;
/*      */           } 
/*  689 */           if (null != prefetchedRecord.unresolvedURIs) {
/*  690 */             getUnresolvedUris(false).addAll(prefetchedRecord.unresolvedURIs);
/*      */           }
/*  692 */           addNewPolicyNeeded(currentUri, prefetchedRecord.policyModel);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  697 */     PolicySourceModelContext modelContext = PolicySourceModelContext.createContext();
/*  698 */     for (String policyUri : this.urisNeeded) {
/*  699 */       PolicySourceModel sourceModel = this.modelsNeeded.get(policyUri);
/*      */       try {
/*  701 */         sourceModel.expand(modelContext);
/*  702 */         modelContext.addModel(new URI(policyUri), sourceModel);
/*  703 */       } catch (URISyntaxException e) {
/*  704 */         LOGGER.logSevereException(e);
/*  705 */       } catch (PolicyException e) {
/*  706 */         LOGGER.logSevereException((Throwable)e);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  716 */       HashSet<BuilderHandlerMessageScope> messageSet = new HashSet<BuilderHandlerMessageScope>();
/*  717 */       for (WSDLService service : context.getWSDLModel().getServices().values()) {
/*  718 */         if (getHandlers4ServiceMap().containsKey(service)) {
/*  719 */           getPolicyMapBuilder().registerHandler(new BuilderHandlerServiceScope(getPolicyURIs(getHandlers4ServiceMap().get(service), modelContext), getPolicyModels(), service, service.getName()));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  727 */         for (WSDLPort port : service.getPorts()) {
/*  728 */           if (getHandlers4PortMap().containsKey(port)) {
/*  729 */             getPolicyMapBuilder().registerHandler(new BuilderHandlerEndpointScope(getPolicyURIs(getHandlers4PortMap().get(port), modelContext), getPolicyModels(), port, port.getOwner().getName(), port.getName()));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  737 */           if (null != port.getBinding()) {
/*      */             
/*  739 */             if (getHandlers4BindingMap().containsKey(port.getBinding()))
/*      */             {
/*  741 */               getPolicyMapBuilder().registerHandler(new BuilderHandlerEndpointScope(getPolicyURIs(getHandlers4BindingMap().get(port.getBinding()), modelContext), getPolicyModels(), port.getBinding(), service.getName(), port.getName()));
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  750 */             if (getHandlers4PortTypeMap().containsKey(port.getBinding().getPortType()))
/*      */             {
/*  752 */               getPolicyMapBuilder().registerHandler(new BuilderHandlerEndpointScope(getPolicyURIs(getHandlers4PortTypeMap().get(port.getBinding().getPortType()), modelContext), getPolicyModels(), port.getBinding().getPortType(), service.getName(), port.getName()));
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  763 */             for (WSDLBoundOperation boundOperation : port.getBinding().getBindingOperations()) {
/*      */               
/*  765 */               WSDLOperation operation = boundOperation.getOperation();
/*  766 */               QName operationName = new QName(boundOperation.getBoundPortType().getName().getNamespaceURI(), boundOperation.getName().getLocalPart());
/*      */               
/*  768 */               if (getHandlers4BoundOperationMap().containsKey(boundOperation))
/*      */               {
/*  770 */                 getPolicyMapBuilder().registerHandler(new BuilderHandlerOperationScope(getPolicyURIs(getHandlers4BoundOperationMap().get(boundOperation), modelContext), getPolicyModels(), boundOperation, service.getName(), port.getName(), operationName));
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  780 */               if (getHandlers4OperationMap().containsKey(operation))
/*      */               {
/*  782 */                 getPolicyMapBuilder().registerHandler(new BuilderHandlerOperationScope(getPolicyURIs(getHandlers4OperationMap().get(operation), modelContext), getPolicyModels(), operation, service.getName(), port.getName(), operationName));
/*      */               }
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
/*  794 */               WSDLInput input = operation.getInput();
/*  795 */               if (null != input) {
/*  796 */                 WSDLMessage inputMsg = input.getMessage();
/*  797 */                 if (inputMsg != null && getHandlers4MessageMap().containsKey(inputMsg)) {
/*  798 */                   messageSet.add(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4MessageMap().get(inputMsg), modelContext), getPolicyModels(), inputMsg, BuilderHandlerMessageScope.Scope.InputMessageScope, service.getName(), port.getName(), operationName, null));
/*      */                 }
/*      */               } 
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
/*  811 */               if (getHandlers4BindingInputOpMap().containsKey(boundOperation))
/*      */               {
/*  813 */                 getPolicyMapBuilder().registerHandler(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4BindingInputOpMap().get(boundOperation), modelContext), getPolicyModels(), boundOperation, BuilderHandlerMessageScope.Scope.InputMessageScope, service.getName(), port.getName(), operationName, null));
/*      */               }
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
/*  825 */               if (null != input && getHandlers4InputMap().containsKey(input))
/*      */               {
/*  827 */                 getPolicyMapBuilder().registerHandler(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4InputMap().get(input), modelContext), getPolicyModels(), input, BuilderHandlerMessageScope.Scope.InputMessageScope, service.getName(), port.getName(), operationName, null));
/*      */               }
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
/*  841 */               WSDLOutput output = operation.getOutput();
/*  842 */               if (null != output) {
/*  843 */                 WSDLMessage outputMsg = output.getMessage();
/*  844 */                 if (outputMsg != null && getHandlers4MessageMap().containsKey(outputMsg)) {
/*  845 */                   messageSet.add(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4MessageMap().get(outputMsg), modelContext), getPolicyModels(), outputMsg, BuilderHandlerMessageScope.Scope.OutputMessageScope, service.getName(), port.getName(), operationName, null));
/*      */                 }
/*      */               } 
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
/*  858 */               if (getHandlers4BindingOutputOpMap().containsKey(boundOperation))
/*      */               {
/*  860 */                 getPolicyMapBuilder().registerHandler(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4BindingOutputOpMap().get(boundOperation), modelContext), getPolicyModels(), boundOperation, BuilderHandlerMessageScope.Scope.OutputMessageScope, service.getName(), port.getName(), operationName, null));
/*      */               }
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
/*  872 */               if (null != output && getHandlers4OutputMap().containsKey(output))
/*      */               {
/*  874 */                 getPolicyMapBuilder().registerHandler(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4OutputMap().get(output), modelContext), getPolicyModels(), output, BuilderHandlerMessageScope.Scope.OutputMessageScope, service.getName(), port.getName(), operationName, null));
/*      */               }
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
/*  888 */               for (WSDLBoundFault boundFault : boundOperation.getFaults()) {
/*  889 */                 WSDLFault fault = boundFault.getFault();
/*      */ 
/*      */ 
/*      */                 
/*  893 */                 if (fault == null) {
/*  894 */                   LOGGER.warning(PolicyMessages.WSP_1021_FAULT_NOT_BOUND(boundFault.getName()));
/*      */                   
/*      */                   continue;
/*      */                 } 
/*  898 */                 WSDLMessage faultMessage = fault.getMessage();
/*  899 */                 QName faultName = new QName(boundOperation.getBoundPortType().getName().getNamespaceURI(), boundFault.getName());
/*      */                 
/*  901 */                 if (faultMessage != null && getHandlers4MessageMap().containsKey(faultMessage)) {
/*  902 */                   messageSet.add(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4MessageMap().get(faultMessage), modelContext), getPolicyModels(), new WSDLBoundFaultContainer(boundFault, boundOperation), BuilderHandlerMessageScope.Scope.FaultMessageScope, service.getName(), port.getName(), operationName, faultName));
/*      */                 }
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
/*  914 */                 if (getHandlers4FaultMap().containsKey(fault)) {
/*  915 */                   messageSet.add(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4FaultMap().get(fault), modelContext), getPolicyModels(), new WSDLBoundFaultContainer(boundFault, boundOperation), BuilderHandlerMessageScope.Scope.FaultMessageScope, service.getName(), port.getName(), operationName, faultName));
/*      */                 }
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
/*  927 */                 if (getHandlers4BindingFaultOpMap().containsKey(boundFault)) {
/*  928 */                   messageSet.add(new BuilderHandlerMessageScope(getPolicyURIs(getHandlers4BindingFaultOpMap().get(boundFault), modelContext), getPolicyModels(), new WSDLBoundFaultContainer(boundFault, boundOperation), BuilderHandlerMessageScope.Scope.FaultMessageScope, service.getName(), port.getName(), operationName, faultName));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
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
/*  948 */       for (BuilderHandlerMessageScope scopeHandler : messageSet) {
/*  949 */         getPolicyMapBuilder().registerHandler(scopeHandler);
/*      */       }
/*  951 */     } catch (PolicyException e) {
/*  952 */       LOGGER.logSevereException((Throwable)e);
/*      */     } 
/*      */ 
/*      */     
/*  956 */     LOGGER.exiting();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postFinished(WSDLParserExtensionContext context) {
/*      */     PolicyMap effectiveMap;
/*  964 */     WSDLModel wsdlModel = context.getWSDLModel();
/*      */     
/*      */     try {
/*  967 */       if (context.isClientSide()) {
/*  968 */         effectiveMap = context.getPolicyResolver().resolve(new PolicyResolver.ClientContext(this.policyBuilder.getPolicyMap(new com.sun.xml.ws.policy.PolicyMapMutator[0]), context.getContainer()));
/*      */       } else {
/*  970 */         effectiveMap = context.getPolicyResolver().resolve(new PolicyResolver.ServerContext(this.policyBuilder.getPolicyMap(new com.sun.xml.ws.policy.PolicyMapMutator[0]), context.getContainer(), null, new com.sun.xml.ws.policy.PolicyMapMutator[0]));
/*  971 */       }  ((WSDLModelImpl)wsdlModel).setPolicyMap(effectiveMap);
/*  972 */     } catch (PolicyException e) {
/*  973 */       LOGGER.logSevereException((Throwable)e);
/*  974 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1007_POLICY_EXCEPTION_WHILE_FINISHING_PARSING_WSDL(), e));
/*      */     } 
/*      */     try {
/*  977 */       PolicyUtil.configureModel(wsdlModel, effectiveMap);
/*  978 */     } catch (PolicyException e) {
/*  979 */       LOGGER.logSevereException((Throwable)e);
/*  980 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1012_FAILED_CONFIGURE_WSDL_MODEL(), e));
/*      */     } 
/*  982 */     LOGGER.exiting();
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
/*      */   private String[] getPolicyURIsFromAttr(XMLStreamReader reader) {
/*  994 */     StringBuilder policyUriBuffer = new StringBuilder();
/*  995 */     for (NamespaceVersion version : NamespaceVersion.values()) {
/*  996 */       String value = reader.getAttributeValue(version.toString(), XmlToken.PolicyUris.toString());
/*  997 */       if (value != null) {
/*  998 */         policyUriBuffer.append(value).append(" ");
/*      */       }
/*      */     } 
/* 1001 */     return (policyUriBuffer.length() > 0) ? policyUriBuffer.toString().split("[\\n ]+") : null;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\PolicyWSDLParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */