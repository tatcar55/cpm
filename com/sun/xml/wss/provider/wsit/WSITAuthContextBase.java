/*      */ package com.sun.xml.wss.provider.wsit;
/*      */ 
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.WSBinding;
/*      */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.ws.api.message.HeaderList;
/*      */ import com.sun.xml.ws.api.message.Message;
/*      */ import com.sun.xml.ws.api.message.Packet;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.ws.api.pipe.Pipe;
/*      */ import com.sun.xml.ws.api.pipe.Tube;
/*      */ import com.sun.xml.ws.api.policy.ModelTranslator;
/*      */ import com.sun.xml.ws.api.policy.ModelUnmarshaller;
/*      */ import com.sun.xml.ws.api.server.WSEndpoint;
/*      */ import com.sun.xml.ws.policy.AssertionSet;
/*      */ import com.sun.xml.ws.policy.NestedPolicy;
/*      */ import com.sun.xml.ws.policy.Policy;
/*      */ import com.sun.xml.ws.policy.PolicyAssertion;
/*      */ import com.sun.xml.ws.policy.PolicyException;
/*      */ import com.sun.xml.ws.policy.PolicyMap;
/*      */ import com.sun.xml.ws.policy.PolicyMapKey;
/*      */ import com.sun.xml.ws.policy.PolicyMerger;
/*      */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*      */ import com.sun.xml.ws.rx.mc.api.MakeConnectionSupportedFeature;
/*      */ import com.sun.xml.ws.rx.mc.api.McProtocolVersion;
/*      */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;
/*      */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.impl.policy.CallbackHandler;
/*      */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*      */ import com.sun.xml.ws.security.impl.policy.SessionManagerStore;
/*      */ import com.sun.xml.ws.security.impl.policyconv.SCTokenWrapper;
/*      */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*      */ import com.sun.xml.ws.security.impl.policyconv.XWSSPolicyGenerator;
/*      */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*      */ import com.sun.xml.ws.security.opt.impl.util.CertificateRetriever;
/*      */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*      */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*      */ import com.sun.xml.ws.security.policy.CallbackHandlerConfiguration;
/*      */ import com.sun.xml.ws.security.policy.CertStoreConfig;
/*      */ import com.sun.xml.ws.security.policy.KerberosConfig;
/*      */ import com.sun.xml.ws.security.policy.KeyStore;
/*      */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*      */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*      */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*      */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*      */ import com.sun.xml.ws.security.policy.Token;
/*      */ import com.sun.xml.ws.security.policy.TrustStore;
/*      */ import com.sun.xml.ws.security.policy.Validator;
/*      */ import com.sun.xml.ws.security.policy.ValidatorConfiguration;
/*      */ import com.sun.xml.ws.security.policy.WSSAssertion;
/*      */ import com.sun.xml.ws.security.secconv.WSSCVersion;
/*      */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*      */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*      */ import com.sun.xml.wss.ProcessingContext;
/*      */ import com.sun.xml.wss.SecurityEnvironment;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*      */ import com.sun.xml.wss.impl.WSSAssertion;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.jaxws.impl.ClientTubeConfiguration;
/*      */ import com.sun.xml.wss.jaxws.impl.Constants;
/*      */ import com.sun.xml.wss.jaxws.impl.RMPolicyResolver;
/*      */ import com.sun.xml.wss.jaxws.impl.ServerTubeConfiguration;
/*      */ import com.sun.xml.wss.jaxws.impl.TubeConfiguration;
/*      */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.message.MessageInfo;
/*      */ import javax.xml.bind.JAXBContext;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.Marshaller;
/*      */ import javax.xml.bind.Unmarshaller;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.SOAPConstants;
/*      */ import javax.xml.soap.SOAPFactory;
/*      */ import javax.xml.soap.SOAPFault;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.ws.soap.SOAPFaultException;
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
/*      */ public abstract class WSITAuthContextBase
/*      */ {
/*  171 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  180 */   protected Hashtable<String, IssuedTokenContext> issuedTokenContextMap = new Hashtable<String, IssuedTokenContext>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   private final QName EPREnabled = new QName("http://schemas.sun.com/2006/03/wss/server", "EnableEPRIdentity");
/*  186 */   private final QName encSCServerCancel = new QName("http://schemas.sun.com/2006/03/wss/server", "EncSCCancel");
/*  187 */   private final QName encSCClientCancel = new QName("http://schemas.sun.com/2006/03/wss/client", "EncSCCancel");
/*  188 */   private final QName optServerSecurity = new QName("http://schemas.sun.com/2006/03/wss/server", "DisableStreamingSecurity");
/*  189 */   private final QName optClientSecurity = new QName("http://schemas.sun.com/2006/03/wss/client", "DisableStreamingSecurity");
/*      */   
/*      */   protected boolean disableIncPrefix = false;
/*  192 */   private final QName disableIncPrefixServer = new QName("http://schemas.sun.com/2006/03/wss/server", "DisableInclusivePrefixList");
/*  193 */   private final QName disableIncPrefixClient = new QName("http://schemas.sun.com/2006/03/wss/client", "DisableInclusivePrefixList");
/*      */   
/*      */   protected boolean encRMLifecycleMsg = false;
/*  196 */   private final QName encRMLifecycleMsgServer = new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptRMLifecycleMessage");
/*  197 */   private final QName encRMLifecycleMsgClient = new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptRMLifecycleMessage");
/*      */   
/*      */   protected boolean encHeaderContent = false;
/*  200 */   private final QName encHeaderContentServer = new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptHeaderContent");
/*  201 */   private final QName encHeaderContentClient = new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptHeaderContent");
/*      */   
/*      */   protected boolean allowMissingTimestamp = false;
/*  204 */   private final QName allowMissingTSServer = new QName("http://schemas.sun.com/2006/03/wss/server", "AllowMissingTimestamp");
/*  205 */   private final QName allowMissingTSClient = new QName("http://schemas.sun.com/2006/03/wss/client", "AllowMissingTimestamp");
/*      */   
/*      */   protected boolean securityMUValue = true;
/*  208 */   private final QName unsetSecurityMUValueServer = new QName("http://schemas.sun.com/2006/03/wss/server", "UnsetSecurityMUValue");
/*  209 */   private final QName unsetSecurityMUValueClient = new QName("http://schemas.sun.com/2006/03/wss/client", "UnsetSecurityMUValue");
/*      */   
/*      */   protected static final JAXBContext jaxbContext;
/*      */   
/*  213 */   protected WSSCVersion wsscVer = null;
/*  214 */   protected WSTrustVersion wsTrustVer = null;
/*  215 */   protected RmProtocolVersion rmVer = RmProtocolVersion.WSRM200502;
/*  216 */   protected McProtocolVersion mcVer = McProtocolVersion.WSMC200702;
/*      */   
/*      */   protected static final ArrayList<String> securityPolicyNamespaces;
/*      */   
/*  220 */   protected static final List<PolicyAssertion> EMPTY_LIST = Collections.emptyList();
/*      */ 
/*      */   
/*      */   protected static final boolean debug;
/*      */ 
/*      */   
/*      */   protected Pipe nextPipe;
/*      */ 
/*      */   
/*      */   protected Tube nextTube;
/*      */   
/*      */   protected boolean optimized = true;
/*      */   
/*  233 */   protected TubeConfiguration pipeConfig = null;
/*      */ 
/*      */ 
/*      */   
/*  237 */   protected SecurityEnvironment secEnv = null;
/*      */   
/*      */   protected boolean isSOAP12 = false;
/*      */   
/*  241 */   protected SOAPVersion soapVersion = null;
/*      */   
/*  243 */   protected SOAPFactory soapFactory = null;
/*      */ 
/*      */   
/*  246 */   protected List<PolicyAlternativeHolder> policyAlternatives = new ArrayList<PolicyAlternativeHolder>();
/*      */ 
/*      */   
/*  249 */   protected Policy bpMSP = null;
/*      */ 
/*      */   
/*  252 */   protected Marshaller marshaller = null;
/*  253 */   protected Unmarshaller unmarshaller = null;
/*      */   
/*      */   boolean hasIssuedTokens = false;
/*      */   
/*      */   boolean hasSecureConversation = false;
/*      */   
/*      */   boolean hasKerberosToken = false;
/*      */   
/*  261 */   protected AlgorithmSuite bindingLevelAlgSuite = null;
/*      */   
/*      */   private AlgorithmSuite bootStrapAlgoSuite;
/*      */   
/*      */   boolean hasReliableMessaging = false;
/*      */   boolean hasMakeConnection = false;
/*  267 */   AddressingVersion addVer = null;
/*      */ 
/*      */ 
/*      */   
/*  271 */   protected SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*      */   
/*      */   protected static final String REQ_PACKET = "REQ_PACKET";
/*      */   
/*      */   protected static final String RES_PACKET = "RES_PACKET";
/*      */   protected static final String DEFAULT_JMAC_HANDLER = "com.sun.enterprise.security.jmac.callback.ContainerCallbackHandler";
/*      */   protected static final String WSDLPORT = "WSDLPort";
/*      */   protected static final String WSENDPOINT = "WSEndpoint";
/*  279 */   protected X509Certificate serverCert = null;
/*      */   
/*      */   protected boolean isCertValidityVerified = false;
/*  282 */   protected long timestampTimeOut = 0L;
/*  283 */   protected int iterationsForPDK = 0;
/*      */   
/*      */   protected boolean isEPREnabled = false;
/*      */   
/*      */   private boolean encryptCancelPayload = false;
/*      */   private Policy cancelMSP;
/*      */   protected boolean isCertValid;
/*      */   
/*      */   static {
/*      */     try {
/*  293 */       debug = Boolean.valueOf(System.getProperty("DebugSecurity")).booleanValue();
/*      */ 
/*      */       
/*  296 */       jaxbContext = WSTrustElementFactory.getContext();
/*  297 */       securityPolicyNamespaces = new ArrayList<String>();
/*  298 */       securityPolicyNamespaces.add(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri);
/*      */     }
/*  300 */     catch (Exception e) {
/*  301 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public WSITAuthContextBase(Map<Object, Object> map) {
/*  307 */     this.nextPipe = (Pipe)map.get("NEXT_PIPE");
/*  308 */     this.nextTube = (Tube)map.get("NEXT_TUBE");
/*  309 */     PolicyMap wsPolicyMap = (PolicyMap)map.get("POLICY");
/*  310 */     WSDLPort port = (WSDLPort)map.get("WSDL_MODEL");
/*  311 */     if (this instanceof WSITClientAuthContext) {
/*  312 */       WSBinding binding = (WSBinding)map.get("BINDING");
/*  313 */       this.pipeConfig = (TubeConfiguration)new ClientTubeConfiguration(wsPolicyMap, port, binding);
/*      */     } else {
/*      */       
/*  316 */       WSEndpoint endPoint = (WSEndpoint)map.get("ENDPOINT");
/*  317 */       this.pipeConfig = (TubeConfiguration)new ServerTubeConfiguration(wsPolicyMap, port, endPoint);
/*      */     } 
/*      */ 
/*      */     
/*  321 */     this.soapVersion = this.pipeConfig.getBinding().getSOAPVersion();
/*  322 */     this.isSOAP12 = (this.soapVersion == SOAPVersion.SOAP_12);
/*  323 */     wsPolicyMap = this.pipeConfig.getPolicyMap();
/*  324 */     this.soapFactory = (this.pipeConfig.getBinding().getSOAPVersion()).saajSoapFactory;
/*      */     
/*  326 */     if (wsPolicyMap != null) {
/*  327 */       collectPolicies(wsPolicyMap, this.policyAlternatives);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  332 */       this.marshaller = jaxbContext.createMarshaller();
/*  333 */       this.unmarshaller = jaxbContext.createUnmarshaller();
/*  334 */     } catch (JAXBException ex) {
/*  335 */       throw new RuntimeException(ex);
/*      */     } 
/*      */ 
/*      */     
/*  339 */     this.hasReliableMessaging = isReliableMessagingEnabled(this.pipeConfig.getWSDLPort());
/*  340 */     this.hasMakeConnection = isMakeConnectionEnabled(this.pipeConfig.getWSDLPort());
/*      */ 
/*      */     
/*  343 */     map.put("SOAP_VERSION", this.soapVersion);
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
/*      */ 
/*      */   
/*      */   protected void collectPolicies(PolicyMap wsPolicyMap, List<PolicyAlternativeHolder> alternatives) {
/*      */     try {
/*  361 */       if (wsPolicyMap == null) {
/*      */         return;
/*      */       }
/*      */       
/*  365 */       QName serviceName = this.pipeConfig.getWSDLPort().getOwner().getName();
/*  366 */       QName portName = this.pipeConfig.getWSDLPort().getName();
/*      */ 
/*      */       
/*  369 */       PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/*      */ 
/*      */       
/*  372 */       Policy endpointPolicy = wsPolicyMap.getEndpointEffectivePolicy(endpointKey);
/*      */ 
/*      */       
/*  375 */       setPolicyCredentials(endpointPolicy);
/*      */       
/*  377 */       for (WSDLBoundOperation operation : this.pipeConfig.getWSDLPort().getBinding().getBindingOperations()) {
/*  378 */         QName operationName = new QName(operation.getBoundPortType().getName().getNamespaceURI(), operation.getName().getLocalPart());
/*  379 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, operationName);
/*  380 */         Policy operationPolicy = wsPolicyMap.getOperationEffectivePolicy(operationKey);
/*  381 */         setPolicyCredentials(operationPolicy);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  386 */       if (endpointPolicy == null) {
/*  387 */         ArrayList<Policy> policyList = new ArrayList<Policy>();
/*  388 */         PolicyAlternativeHolder ph = new PolicyAlternativeHolder(null, this.spVersion, this.bpMSP);
/*  389 */         alternatives.add(ph);
/*  390 */         collectOperationAndMessageLevelPolicies(wsPolicyMap, null, policyList, ph);
/*      */         
/*      */         return;
/*      */       } 
/*  394 */       Iterator<AssertionSet> policiesIter = endpointPolicy.iterator();
/*  395 */       while (policiesIter.hasNext()) {
/*  396 */         ArrayList<Policy> policyList = new ArrayList<Policy>();
/*  397 */         AssertionSet ass = policiesIter.next();
/*  398 */         PolicyAlternativeHolder ph = new PolicyAlternativeHolder(ass, this.spVersion, this.bpMSP);
/*  399 */         alternatives.add(ph);
/*      */         
/*  401 */         Collection<AssertionSet> coll = new ArrayList<AssertionSet>();
/*  402 */         coll.add(ass);
/*  403 */         Policy singleAlternative = Policy.createPolicy(endpointPolicy.getNamespaceVersion(), endpointPolicy.getName(), endpointPolicy.getId(), coll);
/*      */ 
/*      */         
/*  406 */         buildProtocolPolicy(singleAlternative, ph);
/*      */         
/*  408 */         policyList.add(singleAlternative);
/*      */         
/*  410 */         collectOperationAndMessageLevelPolicies(wsPolicyMap, singleAlternative, policyList, ph);
/*      */       } 
/*  412 */     } catch (PolicyException pe) {
/*  413 */       throw generateInternalError(pe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void collectOperationAndMessageLevelPolicies(PolicyMap wsPolicyMap, Policy singleAlternative, ArrayList<Policy> policyList, PolicyAlternativeHolder ph) {
/*  420 */     if (wsPolicyMap == null) {
/*      */       return;
/*      */     }
/*      */     try {
/*  424 */       QName serviceName = this.pipeConfig.getWSDLPort().getOwner().getName();
/*  425 */       QName portName = this.pipeConfig.getWSDLPort().getName();
/*      */       
/*  427 */       PolicyMerger policyMerge = PolicyMerger.getMerger();
/*  428 */       for (WSDLBoundOperation operation : this.pipeConfig.getWSDLPort().getBinding().getBindingOperations()) {
/*  429 */         QName operationName = new QName(operation.getBoundPortType().getName().getNamespaceURI(), operation.getName().getLocalPart());
/*      */ 
/*      */         
/*  432 */         PolicyMapKey messageKey = PolicyMap.createWsdlMessageScopeKey(serviceName, portName, operationName);
/*      */         
/*  434 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, operationName);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  439 */         Policy operationPolicy = wsPolicyMap.getOperationEffectivePolicy(operationKey);
/*  440 */         if (operationPolicy != null) {
/*  441 */           policyList.add(operationPolicy);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  447 */         Policy imPolicy = null;
/*  448 */         imPolicy = wsPolicyMap.getInputMessageEffectivePolicy(messageKey);
/*  449 */         if (imPolicy != null) {
/*  450 */           policyList.add(imPolicy);
/*      */         }
/*      */ 
/*      */         
/*  454 */         Policy imEP = policyMerge.merge(policyList);
/*  455 */         SecurityPolicyHolder outPH = null;
/*  456 */         if (imEP != null) {
/*  457 */           outPH = addOutgoingMP(operation, imEP, ph);
/*      */         }
/*      */         
/*  460 */         if (imPolicy != null) {
/*  461 */           policyList.remove(imPolicy);
/*      */         }
/*      */         
/*  464 */         SecurityPolicyHolder inPH = null;
/*      */         
/*  466 */         Policy omPolicy = null;
/*  467 */         omPolicy = wsPolicyMap.getOutputMessageEffectivePolicy(messageKey);
/*  468 */         if (omPolicy != null) {
/*  469 */           policyList.add(omPolicy);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  474 */         Policy omEP = policyMerge.merge(policyList);
/*  475 */         if (omPolicy != null) {
/*  476 */           policyList.remove(omPolicy);
/*      */         }
/*  478 */         if (omEP != null) {
/*  479 */           inPH = addIncomingMP(operation, omEP, ph);
/*      */         }
/*      */         
/*  482 */         Iterator<WSDLFault> faults = operation.getOperation().getFaults().iterator();
/*  483 */         ArrayList<Policy> faultPL = new ArrayList<Policy>();
/*  484 */         if (singleAlternative != null) {
/*  485 */           faultPL.add(singleAlternative);
/*      */         }
/*  487 */         if (operationPolicy != null) {
/*  488 */           faultPL.add(operationPolicy);
/*      */         }
/*  490 */         while (faults.hasNext()) {
/*  491 */           WSDLFault fault = faults.next();
/*  492 */           PolicyMapKey fKey = null;
/*  493 */           fKey = PolicyMap.createWsdlFaultMessageScopeKey(serviceName, portName, operationName, new QName(operationName.getNamespaceURI(), fault.getName()));
/*      */ 
/*      */           
/*  496 */           Policy fPolicy = wsPolicyMap.getFaultMessageEffectivePolicy(fKey);
/*      */           
/*  498 */           if (fPolicy != null) {
/*  499 */             faultPL.add(fPolicy);
/*      */           }
/*      */ 
/*      */           
/*  503 */           Policy ep = policyMerge.merge(faultPL);
/*  504 */           if (inPH != null) {
/*  505 */             addIncomingFaultPolicy(ep, inPH, fault);
/*      */           }
/*  507 */           if (outPH != null) {
/*  508 */             addOutgoingFaultPolicy(ep, outPH, fault);
/*      */           }
/*  510 */           faultPL.remove(fPolicy);
/*      */         } 
/*  512 */         if (operationPolicy != null) {
/*  513 */           policyList.remove(operationPolicy);
/*      */         }
/*      */       } 
/*  516 */     } catch (PolicyException pe) {
/*  517 */       throw generateInternalError(pe);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setPolicyCredentials(Policy policy) {
/*  522 */     if (policy != null) {
/*  523 */       if (policy.contains(AddressingVersion.W3C.policyNsUri) || policy.contains("http://www.w3.org/2007/05/addressing/metadata")) {
/*  524 */         this.addVer = AddressingVersion.W3C;
/*  525 */       } else if (policy.contains(AddressingVersion.MEMBER.policyNsUri)) {
/*  526 */         this.addVer = AddressingVersion.MEMBER;
/*      */       } 
/*  528 */       if (policy.contains(this.optServerSecurity) || policy.contains(this.optClientSecurity)) {
/*  529 */         this.optimized = false;
/*      */       }
/*  531 */       if (policy.contains(this.EPREnabled)) {
/*  532 */         this.isEPREnabled = true;
/*      */       }
/*  534 */       if (policy.contains(this.encSCServerCancel) || policy.contains(this.encSCClientCancel)) {
/*  535 */         this.encryptCancelPayload = true;
/*      */       }
/*  537 */       if (policy.contains(this.disableIncPrefixServer) || policy.contains(this.disableIncPrefixClient)) {
/*  538 */         this.disableIncPrefix = true;
/*      */       }
/*  540 */       if (policy.contains(this.encHeaderContentServer) || policy.contains(this.encHeaderContentClient)) {
/*  541 */         this.encHeaderContent = true;
/*      */       }
/*  543 */       if (policy.contains(this.allowMissingTSClient) || policy.contains(this.allowMissingTSServer)) {
/*  544 */         this.allowMissingTimestamp = true;
/*      */       }
/*  546 */       if (policy.contains(this.unsetSecurityMUValueClient) || policy.contains(this.unsetSecurityMUValueServer)) {
/*  547 */         this.securityMUValue = false;
/*      */       }
/*  549 */       if (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri)) {
/*  550 */         this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*  551 */         this.wsscVer = WSSCVersion.WSSC_10;
/*  552 */         this.wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*  553 */       } else if (policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)) {
/*  554 */         this.spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*  555 */         this.wsscVer = WSSCVersion.WSSC_13;
/*  556 */         this.wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*  557 */       } else if (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri)) {
/*  558 */         this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200512;
/*  559 */         this.wsscVer = WSSCVersion.WSSC_10;
/*  560 */         this.wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*      */       } 
/*  562 */       if (policy.contains(RmProtocolVersion.WSRM200702.protocolNamespaceUri) || policy.contains(RmProtocolVersion.WSRM200702.policyNamespaceUri)) {
/*      */         
/*  564 */         this.rmVer = RmProtocolVersion.WSRM200702;
/*  565 */       } else if (policy.contains(RmProtocolVersion.WSRM200502.protocolNamespaceUri) || policy.contains(RmProtocolVersion.WSRM200502.policyNamespaceUri)) {
/*      */         
/*  567 */         this.rmVer = RmProtocolVersion.WSRM200502;
/*      */       } 
/*      */       
/*  570 */       if (policy.contains(this.encRMLifecycleMsgServer) || policy.contains(this.encRMLifecycleMsgClient))
/*  571 */         this.encRMLifecycleMsg = true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected RuntimeException generateInternalError(PolicyException ex) {
/*  576 */     SOAPFault fault = null;
/*      */     try {
/*  578 */       if (this.isSOAP12) {
/*  579 */         fault = this.soapFactory.createFault(ex.getMessage(), SOAPConstants.SOAP_SENDER_FAULT);
/*  580 */         fault.appendFaultSubcode(MessageConstants.WSSE_INTERNAL_SERVER_ERROR);
/*      */       } else {
/*  582 */         fault = this.soapFactory.createFault(ex.getMessage(), MessageConstants.WSSE_INTERNAL_SERVER_ERROR);
/*      */       } 
/*  584 */     } catch (Exception e) {
/*  585 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0002_INTERNAL_SERVER_ERROR(), e);
/*  586 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0002_INTERNAL_SERVER_ERROR(), e);
/*      */     } 
/*  588 */     return new SOAPFaultException(fault);
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getInBoundSCP(Message message) {
/*  593 */     SecurityPolicyHolder sph = null;
/*  594 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  595 */       if (p.getInMessagePolicyMap() == null) {
/*  596 */         return Collections.emptyList();
/*      */       }
/*      */       
/*  599 */       Collection<SecurityPolicyHolder> coll = p.getInMessagePolicyMap().values();
/*  600 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  602 */       while (itr.hasNext()) {
/*  603 */         SecurityPolicyHolder ph = itr.next();
/*  604 */         if (ph != null) {
/*  605 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  609 */       if (sph == null) {
/*  610 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  613 */     return sph.getSecureConversationTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getOutBoundSCP(Message message) {
/*  619 */     SecurityPolicyHolder sph = null;
/*      */     
/*  621 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  622 */       if (p.getOutMessagePolicyMap() == null) {
/*  623 */         return Collections.emptyList();
/*      */       }
/*      */       
/*  626 */       Collection<SecurityPolicyHolder> coll = p.getOutMessagePolicyMap().values();
/*  627 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  629 */       while (itr.hasNext()) {
/*  630 */         SecurityPolicyHolder ph = itr.next();
/*  631 */         if (ph != null) {
/*  632 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  636 */       if (sph == null) {
/*  637 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  640 */     return sph.getSecureConversationTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getOutBoundKTP(Packet packet, boolean isSCMessage) {
/*  646 */     if (isSCMessage) {
/*  647 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/*  648 */       return ((SCTokenWrapper)scToken).getKerberosTokens();
/*      */     } 
/*  650 */     SecurityPolicyHolder sph = null;
/*      */     
/*  652 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  653 */       if (p.getOutMessagePolicyMap() == null) {
/*  654 */         return Collections.emptyList();
/*      */       }
/*  656 */       Message message = packet.getMessage();
/*      */       
/*  658 */       Collection<SecurityPolicyHolder> coll = p.getOutMessagePolicyMap().values();
/*  659 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  661 */       while (itr.hasNext()) {
/*  662 */         SecurityPolicyHolder ph = itr.next();
/*  663 */         if (ph != null) {
/*  664 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  668 */       if (sph == null) {
/*  669 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  672 */     return sph.getKerberosTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getSecureConversationPolicies(Message message, String scope) {
/*  678 */     SecurityPolicyHolder sph = null;
/*      */     
/*  680 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  681 */       if (p.getOutMessagePolicyMap() == null) {
/*  682 */         return Collections.emptyList();
/*      */       }
/*      */       
/*  685 */       Collection<SecurityPolicyHolder> coll = p.getOutMessagePolicyMap().values();
/*  686 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  688 */       while (itr.hasNext()) {
/*  689 */         SecurityPolicyHolder ph = itr.next();
/*  690 */         if (ph != null) {
/*  691 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  695 */       if (sph == null) {
/*  696 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  699 */     return sph.getSecureConversationTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ArrayList<PolicyAssertion> getTokens(Policy policy) {
/*  705 */     ArrayList<PolicyAssertion> tokenList = new ArrayList<PolicyAssertion>();
/*  706 */     for (AssertionSet assertionSet : policy) {
/*  707 */       for (PolicyAssertion assertion : assertionSet) {
/*  708 */         if (PolicyUtil.isAsymmetricBinding(assertion, this.spVersion)) {
/*  709 */           AsymmetricBinding sb = (AsymmetricBinding)assertion;
/*  710 */           Token iToken = sb.getInitiatorToken();
/*  711 */           if (iToken != null) {
/*  712 */             addToken(iToken, tokenList);
/*      */           } else {
/*  714 */             addToken(sb.getInitiatorSignatureToken(), tokenList);
/*  715 */             addToken(sb.getInitiatorEncryptionToken(), tokenList);
/*      */           } 
/*      */           
/*  718 */           Token rToken = sb.getRecipientToken();
/*  719 */           if (rToken != null) {
/*  720 */             addToken(rToken, tokenList); continue;
/*      */           } 
/*  722 */           addToken(sb.getRecipientSignatureToken(), tokenList);
/*  723 */           addToken(sb.getRecipientEncryptionToken(), tokenList); continue;
/*      */         } 
/*  725 */         if (PolicyUtil.isSymmetricBinding(assertion, this.spVersion)) {
/*  726 */           SymmetricBinding sb = (SymmetricBinding)assertion;
/*  727 */           Token token = sb.getProtectionToken();
/*  728 */           if (token != null) {
/*  729 */             addToken(token, tokenList); continue;
/*      */           } 
/*  731 */           addToken(sb.getEncryptionToken(), tokenList);
/*  732 */           addToken(sb.getSignatureToken(), tokenList); continue;
/*      */         } 
/*  734 */         if (PolicyUtil.isSupportingTokens(assertion, this.spVersion)) {
/*  735 */           SupportingTokens st = (SupportingTokens)assertion;
/*  736 */           Iterator<Token> itr = st.getTokens();
/*  737 */           while (itr.hasNext()) {
/*  738 */             addToken(itr.next(), tokenList);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  743 */     return tokenList;
/*      */   }
/*      */ 
/*      */   
/*      */   private void addConfigAssertions(Policy policy, SecurityPolicyHolder sph) {
/*  748 */     for (AssertionSet assertionSet : policy) {
/*  749 */       for (PolicyAssertion assertion : assertionSet) {
/*  750 */         if (PolicyUtil.isConfigPolicyAssertion(assertion))
/*  751 */           sph.addConfigAssertions(assertion); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addToken(Token token, ArrayList<PolicyAssertion> list) {
/*  757 */     if (token == null) {
/*      */       return;
/*      */     }
/*  760 */     if (PolicyUtil.isSecureConversationToken((PolicyAssertion)token, this.spVersion) || PolicyUtil.isIssuedToken((PolicyAssertion)token, this.spVersion) || PolicyUtil.isKerberosToken((PolicyAssertion)token, this.spVersion))
/*      */     {
/*      */       
/*  763 */       list.add((PolicyAssertion)token);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected PolicyMapKey getOperationKey(Message message) {
/*  769 */     WSDLBoundOperation operation = message.getOperation(this.pipeConfig.getWSDLPort());
/*  770 */     WSDLOperation wsdlOperation = operation.getOperation();
/*  771 */     QName serviceName = this.pipeConfig.getWSDLPort().getOwner().getName();
/*  772 */     QName portName = this.pipeConfig.getWSDLPort().getName();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  777 */     PolicyMapKey messageKey = PolicyMap.createWsdlMessageScopeKey(serviceName, portName, wsdlOperation.getName());
/*      */     
/*  779 */     return messageKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AlgorithmSuite getBindingAlgorithmSuite(Packet packet) {
/*  788 */     return this.bindingLevelAlgSuite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cacheMessage(Packet packet) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasTargets(NestedPolicy policy) {
/*  808 */     AssertionSet as = policy.getAssertionSet();
/*      */     
/*  810 */     boolean foundTargets = false;
/*  811 */     for (PolicyAssertion assertion : as) {
/*  812 */       if (PolicyUtil.isSignedParts(assertion, this.spVersion) || PolicyUtil.isEncryptParts(assertion, this.spVersion)) {
/*  813 */         foundTargets = true;
/*      */         break;
/*      */       } 
/*      */     } 
/*  817 */     return foundTargets;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Policy getEffectiveBootstrapPolicy(NestedPolicy bp) throws PolicyException {
/*      */     try {
/*  823 */       ArrayList<Policy> pl = new ArrayList<Policy>();
/*  824 */       pl.add(bp);
/*  825 */       Policy mbp = getMessageBootstrapPolicy();
/*  826 */       if (mbp != null) {
/*  827 */         pl.add(mbp);
/*      */       }
/*      */       
/*  830 */       PolicyMerger pm = PolicyMerger.getMerger();
/*  831 */       Policy ep = pm.merge(pl);
/*  832 */       return ep;
/*  833 */     } catch (Exception e) {
/*  834 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0007_PROBLEM_GETTING_EFF_BOOT_POLICY(), e);
/*  835 */       throw new PolicyException(LogStringsMessages.WSITPVD_0007_PROBLEM_GETTING_EFF_BOOT_POLICY(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Policy getMessageBootstrapPolicy() throws PolicyException, IOException {
/*  841 */     if (this.bpMSP == null) {
/*  842 */       String bootstrapMessagePolicy = "boot-msglevel-policy.xml";
/*  843 */       if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri)) {
/*  844 */         bootstrapMessagePolicy = "boot-msglevel-policy-sx.xml";
/*      */       }
/*  846 */       PolicySourceModel model = unmarshalPolicy("com/sun/xml/ws/security/impl/policyconv/" + bootstrapMessagePolicy);
/*      */       
/*  848 */       this.bpMSP = ModelTranslator.getTranslator().translate(model);
/*      */     } 
/*  850 */     return this.bpMSP;
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
/*      */   protected PolicySourceModel unmarshalPolicy(String resource) throws PolicyException, IOException {
/*  864 */     InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
/*  865 */     if (is == null) {
/*  866 */       return null;
/*      */     }
/*  868 */     Reader reader = new InputStreamReader(is);
/*  869 */     PolicySourceModel model = ModelUnmarshaller.getUnmarshaller().unmarshalModel(reader);
/*  870 */     reader.close();
/*  871 */     return model;
/*      */   }
/*      */   
/*      */   protected final WSDLBoundOperation cacheOperation(Message msg, Packet packet) {
/*  875 */     WSDLBoundOperation cachedOperation = msg.getOperation(this.pipeConfig.getWSDLPort());
/*  876 */     packet.invocationProperties.put("WSDL_BOUND_OPERATION", cachedOperation);
/*  877 */     return cachedOperation;
/*      */   }
/*      */   
/*      */   protected final void resetCachedOperation(Packet packet) {
/*  881 */     packet.invocationProperties.put("WSDL_BOUND_OPERATION", null);
/*      */   }
/*      */   
/*      */   protected final void cacheOperation(WSDLBoundOperation op, Packet packet) {
/*  885 */     packet.invocationProperties.put("WSDL_BOUND_OPERATION", op);
/*      */   }
/*      */   
/*      */   protected final WSDLBoundOperation cachedOperation(Packet packet) {
/*  889 */     WSDLBoundOperation op = (WSDLBoundOperation)packet.invocationProperties.get("WSDL_BOUND_OPERATION");
/*  890 */     return op;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isSCMessage(Packet packet) {
/*  895 */     if (!bindingHasSecureConversationPolicy()) {
/*  896 */       return false;
/*      */     }
/*      */     
/*  899 */     if (!isAddressingEnabled()) {
/*  900 */       return false;
/*      */     }
/*      */     
/*  903 */     String action = getAction(packet);
/*  904 */     if (Constants.rstSCTURI.equals(action)) {
/*  905 */       return true;
/*      */     }
/*  907 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isSCRenew(Packet packet) {
/*  912 */     if (!bindingHasSecureConversationPolicy()) {
/*  913 */       return false;
/*      */     }
/*      */     
/*  916 */     if (!isAddressingEnabled()) {
/*  917 */       return false;
/*      */     }
/*      */     
/*  920 */     String action = getAction(packet);
/*  921 */     if (this.wsscVer.getSCTRenewResponseAction().equals(action) || this.wsscVer.getSCTRenewRequestAction().equals(action))
/*      */     {
/*  923 */       return true;
/*      */     }
/*  925 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isSCCancel(Packet packet) {
/*  930 */     if (!bindingHasSecureConversationPolicy()) {
/*  931 */       return false;
/*      */     }
/*      */     
/*  934 */     if (!isAddressingEnabled()) {
/*  935 */       return false;
/*      */     }
/*      */     
/*  938 */     String action = getAction(packet);
/*  939 */     if (this.wsscVer.getSCTCancelResponseAction().equals(action) || this.wsscVer.getSCTCancelRequestAction().equals(action))
/*      */     {
/*  941 */       return true;
/*      */     }
/*  943 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isAddressingEnabled() {
/*  947 */     return (this.addVer != null);
/*      */   }
/*      */   
/*      */   protected boolean isTrustMessage(Packet packet) {
/*  951 */     if (!isAddressingEnabled()) {
/*  952 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  956 */     String action = getAction(packet);
/*  957 */     if (this.wsTrustVer.getIssueRequestAction().equals(action) || this.wsTrustVer.getIssueFinalResoponseAction().equals(action))
/*      */     {
/*  959 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  963 */     if (this.wsTrustVer.getValidateRequestAction().equals(action) || this.wsTrustVer.getValidateFinalResoponseAction().equals(action))
/*      */     {
/*  965 */       return true;
/*      */     }
/*      */     
/*  968 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isRMMessage(Packet packet) {
/*  973 */     if (!isAddressingEnabled()) {
/*  974 */       return false;
/*      */     }
/*  976 */     if (!bindingHasRMPolicy()) {
/*  977 */       return false;
/*      */     }
/*      */     
/*  980 */     return this.rmVer.isProtocolAction(getAction(packet));
/*      */   }
/*      */   
/*      */   protected boolean isMakeConnectionMessage(Packet packet) {
/*  984 */     if (!this.hasMakeConnection) {
/*  985 */       return false;
/*      */     }
/*  987 */     return this.mcVer.isProtocolAction(getAction(packet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getAction(Packet packet) {
/*  995 */     HeaderList hl = packet.getMessage().getHeaders();
/*      */     
/*  997 */     String action = hl.getAction(this.addVer, this.pipeConfig.getBinding().getSOAPVersion());
/*  998 */     return action;
/*      */   }
/*      */   
/*      */   protected WSDLBoundOperation getWSDLOpFromAction(Packet packet, boolean isIncomming) {
/* 1002 */     String uriValue = getAction(packet);
/* 1003 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 1004 */       Set<WSDLBoundOperation> keys = p.getOutMessagePolicyMap().keySet();
/* 1005 */       for (WSDLBoundOperation wbo : keys) {
/* 1006 */         WSDLOperation wo = wbo.getOperation();
/*      */         
/* 1008 */         String action = getAction(wo, isIncomming);
/* 1009 */         if (action != null && action.equals(uriValue)) {
/* 1010 */           return wbo;
/*      */         }
/*      */       } 
/*      */     } 
/* 1014 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void buildProtocolPolicy(Policy endpointPolicy, PolicyAlternativeHolder ph) throws PolicyException {
/* 1019 */     if (endpointPolicy == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1023 */       RMPolicyResolver rr = new RMPolicyResolver(this.spVersion, this.rmVer, this.mcVer, this.encRMLifecycleMsg);
/* 1024 */       Policy msgLevelPolicy = rr.getOperationLevelPolicy();
/* 1025 */       PolicyMerger merger = PolicyMerger.getMerger();
/* 1026 */       ArrayList<Policy> pList = new ArrayList<Policy>(2);
/* 1027 */       pList.add(endpointPolicy);
/* 1028 */       pList.add(msgLevelPolicy);
/* 1029 */       Policy effectivePolicy = merger.merge(pList);
/* 1030 */       addIncomingProtocolPolicy(effectivePolicy, "RM", ph);
/* 1031 */       addOutgoingProtocolPolicy(effectivePolicy, "RM", ph);
/*      */       
/* 1033 */       pList.remove(msgLevelPolicy);
/* 1034 */       pList.add(getMessageBootstrapPolicy());
/* 1035 */       PolicyMerger pm = PolicyMerger.getMerger();
/*      */       
/* 1037 */       Policy ep = pm.merge(pList);
/* 1038 */       addIncomingProtocolPolicy(ep, "SC", ph);
/* 1039 */       addOutgoingProtocolPolicy(ep, "SC", ph);
/* 1040 */       ArrayList<Policy> pList1 = new ArrayList<Policy>(2);
/* 1041 */       pList1.add(endpointPolicy);
/* 1042 */       pList1.add(getSCCancelPolicy(this.encryptCancelPayload));
/* 1043 */       PolicyMerger pm1 = PolicyMerger.getMerger();
/*      */       
/* 1045 */       Policy ep1 = pm1.merge(pList1);
/* 1046 */       addIncomingProtocolPolicy(ep1, "SC-CANCEL", ph);
/* 1047 */       addOutgoingProtocolPolicy(ep1, "SC-CANCEL", ph);
/* 1048 */     } catch (IOException ie) {
/* 1049 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0008_PROBLEM_BUILDING_PROTOCOL_POLICY(), ie);
/*      */       
/* 1051 */       throw new PolicyException(LogStringsMessages.WSITPVD_0008_PROBLEM_BUILDING_PROTOCOL_POLICY(), ie);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SecurityPolicyHolder constructPolicyHolder(Policy effectivePolicy, boolean isServer, boolean isIncoming) throws PolicyException {
/* 1058 */     return constructPolicyHolder(effectivePolicy, isServer, isIncoming, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SecurityPolicyHolder constructPolicyHolder(Policy effectivePolicy, boolean isServer, boolean isIncoming, boolean ignoreST) throws PolicyException {
/* 1064 */     XWSSPolicyGenerator xwssPolicyGenerator = new XWSSPolicyGenerator(effectivePolicy, isServer, isIncoming, this.spVersion);
/* 1065 */     xwssPolicyGenerator.process(ignoreST);
/* 1066 */     this.bindingLevelAlgSuite = xwssPolicyGenerator.getBindingLevelAlgSuite();
/* 1067 */     MessagePolicy messagePolicy = xwssPolicyGenerator.getXWSSPolicy();
/*      */     
/* 1069 */     SecurityPolicyHolder sph = new SecurityPolicyHolder();
/* 1070 */     sph.setMessagePolicy(messagePolicy);
/* 1071 */     sph.setBindingLevelAlgSuite(xwssPolicyGenerator.getBindingLevelAlgSuite());
/* 1072 */     sph.isIssuedTokenAsEncryptedSupportingToken(xwssPolicyGenerator.isIssuedTokenAsEncryptedSupportingToken());
/* 1073 */     List<PolicyAssertion> tokenList = getTokens(effectivePolicy);
/* 1074 */     addConfigAssertions(effectivePolicy, sph);
/*      */     
/* 1076 */     for (PolicyAssertion token : tokenList) {
/* 1077 */       if (PolicyUtil.isSecureConversationToken(token, this.spVersion)) {
/* 1078 */         NestedPolicy bootstrapPolicy = ((SecureConversationToken)token).getBootstrapPolicy();
/* 1079 */         Policy effectiveBP = null;
/* 1080 */         if (hasTargets(bootstrapPolicy)) {
/* 1081 */           NestedPolicy nestedPolicy = bootstrapPolicy;
/*      */         } else {
/* 1083 */           effectiveBP = getEffectiveBootstrapPolicy(bootstrapPolicy);
/*      */         } 
/* 1085 */         xwssPolicyGenerator = new XWSSPolicyGenerator(effectiveBP, isServer, isIncoming, this.spVersion);
/* 1086 */         xwssPolicyGenerator.process(ignoreST);
/* 1087 */         MessagePolicy bmp = xwssPolicyGenerator.getXWSSPolicy();
/* 1088 */         this.bootStrapAlgoSuite = xwssPolicyGenerator.getBindingLevelAlgSuite();
/*      */         
/* 1090 */         if (isServer && isIncoming) {
/* 1091 */           EncryptionPolicy optionalPolicy = new EncryptionPolicy();
/*      */           
/* 1093 */           EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)optionalPolicy.getFeatureBinding();
/* 1094 */           optionalPolicy.newX509CertificateKeyBinding();
/* 1095 */           EncryptionTarget target = new EncryptionTarget();
/* 1096 */           target.setQName(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion"));
/* 1097 */           target.setEnforce(false);
/* 1098 */           fb.addTargetBinding(target);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1107 */         SCTokenWrapper sCTokenWrapper = new SCTokenWrapper(token, bmp);
/* 1108 */         sph.addSecureConversationToken((PolicyAssertion)sCTokenWrapper);
/* 1109 */         this.hasSecureConversation = true;
/*      */ 
/*      */         
/* 1112 */         List<PolicyAssertion> iList = getIssuedTokenPoliciesFromBootstrapPolicy((Token)sCTokenWrapper);
/*      */         
/* 1114 */         if (!iList.isEmpty()) {
/* 1115 */           this.hasIssuedTokens = true;
/*      */         }
/*      */ 
/*      */         
/* 1119 */         List<PolicyAssertion> kList = getKerberosTokenPoliciesFromBootstrapPolicy((Token)sCTokenWrapper);
/*      */         
/* 1121 */         if (!kList.isEmpty())
/* 1122 */           this.hasKerberosToken = true; 
/*      */         continue;
/*      */       } 
/* 1125 */       if (PolicyUtil.isIssuedToken(token, this.spVersion)) {
/* 1126 */         sph.addIssuedToken(token);
/* 1127 */         this.hasIssuedTokens = true; continue;
/* 1128 */       }  if (PolicyUtil.isKerberosToken(token, this.spVersion)) {
/* 1129 */         sph.addKerberosToken(token);
/* 1130 */         this.hasKerberosToken = true;
/*      */       } 
/*      */     } 
/* 1133 */     return sph;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getIssuedTokenPoliciesFromBootstrapPolicy(Token scAssertion) {
/* 1138 */     SCTokenWrapper token = (SCTokenWrapper)scAssertion;
/* 1139 */     return token.getIssuedTokens();
/*      */   }
/*      */   
/*      */   protected List<PolicyAssertion> getKerberosTokenPoliciesFromBootstrapPolicy(Token scAssertion) {
/* 1143 */     SCTokenWrapper token = (SCTokenWrapper)scAssertion;
/* 1144 */     return token.getKerberosTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String populateConfigProperties(Set configAssertions, Properties props) {
/* 1150 */     if (configAssertions == null) {
/* 1151 */       return null;
/*      */     }
/* 1153 */     Iterator<PolicyAssertion> it = configAssertions.iterator();
/* 1154 */     while (it.hasNext()) {
/* 1155 */       PolicyAssertion as = it.next();
/* 1156 */       if ("KeyStore".equals(as.getName().getLocalPart())) {
/* 1157 */         populateKeystoreProps(props, (KeyStore)as); continue;
/* 1158 */       }  if ("TrustStore".equals(as.getName().getLocalPart())) {
/* 1159 */         populateTruststoreProps(props, (TrustStore)as); continue;
/* 1160 */       }  if ("CallbackHandlerConfiguration".equals(as.getName().getLocalPart())) {
/* 1161 */         String ret = populateCallbackHandlerProps(props, (CallbackHandlerConfiguration)as);
/* 1162 */         if (ret != null)
/* 1163 */           return ret;  continue;
/*      */       } 
/* 1165 */       if ("ValidatorConfiguration".equals(as.getName().getLocalPart())) {
/* 1166 */         populateValidatorProps(props, (ValidatorConfiguration)as); continue;
/* 1167 */       }  if ("CertStore".equals(as.getName().getLocalPart())) {
/* 1168 */         populateCertStoreProps(props, (CertStoreConfig)as); continue;
/* 1169 */       }  if ("KerberosConfig".equals(as.getName().getLocalPart())) {
/* 1170 */         populateKerberosProps(props, (KerberosConfig)as); continue;
/* 1171 */       }  if ("SessionManagerStore".equals(as.getName().getLocalPart())) {
/* 1172 */         populateSessionMgrProps(props, (SessionManagerStore)as);
/*      */       }
/*      */     } 
/* 1175 */     return null;
/*      */   }
/*      */   
/*      */   private void populateSessionMgrProps(Properties props, SessionManagerStore smStore) {
/* 1179 */     if (smStore.getSessionTimeOut() != null) {
/* 1180 */       props.put("session-timeout", smStore.getSessionTimeOut());
/*      */     }
/* 1182 */     if (smStore.getSessionThreshold() != null) {
/* 1183 */       props.put("session-threshold", smStore.getSessionThreshold());
/*      */     }
/*      */   }
/*      */   
/*      */   private void populateKerberosProps(Properties props, KerberosConfig kerbConfig) {
/* 1188 */     if (kerbConfig.getLoginModule() != null) {
/* 1189 */       props.put("krb5.login.module", kerbConfig.getLoginModule());
/*      */     }
/* 1191 */     if (kerbConfig.getServicePrincipal() != null) {
/* 1192 */       props.put("krb5.service.principal", kerbConfig.getServicePrincipal());
/*      */     }
/* 1194 */     if (kerbConfig.getCredentialDelegation() != null) {
/* 1195 */       props.put("krb5.credential.delegation", kerbConfig.getCredentialDelegation());
/*      */     }
/*      */   }
/*      */   
/*      */   private void populateKeystoreProps(Properties props, KeyStore store) {
/* 1200 */     boolean foundLoginModule = false;
/* 1201 */     if (store.getKeyStoreLoginModuleConfigName() != null) {
/* 1202 */       props.put("jaas.loginmodule.for.keystore", store.getKeyStoreLoginModuleConfigName());
/* 1203 */       foundLoginModule = true;
/*      */     } 
/* 1205 */     if (store.getKeyStoreCallbackHandler() != null) {
/* 1206 */       props.put("keystore.callback.handler", store.getKeyStoreCallbackHandler());
/* 1207 */       if (store.getAlias() != null) {
/* 1208 */         props.put("my.alias", store.getAlias());
/*      */       }
/* 1210 */       if (store.getAliasSelectorClassName() != null) {
/* 1211 */         props.put("keystore.certselector", store.getAliasSelectorClassName());
/*      */       }
/*      */       return;
/*      */     } 
/* 1215 */     if (foundLoginModule) {
/*      */       return;
/*      */     }
/* 1218 */     if (store.getLocation() != null) {
/* 1219 */       props.put("keystore.url", store.getLocation());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1229 */     if (store.getType() != null) {
/* 1230 */       props.put("keystore.type", store.getType());
/*      */     } else {
/* 1232 */       props.put("keystore.type", "JKS");
/*      */     } 
/*      */     
/* 1235 */     if (store.getPassword() != null) {
/* 1236 */       props.put("keystore.password", new String(store.getPassword()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1245 */     if (store.getAlias() != null) {
/* 1246 */       props.put("my.alias", store.getAlias());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1251 */     if (store.getKeyPassword() != null) {
/* 1252 */       props.put("key.password", store.getKeyPassword());
/*      */     }
/*      */     
/* 1255 */     if (store.getAliasSelectorClassName() != null) {
/* 1256 */       props.put("keystore.certselector", store.getAliasSelectorClassName());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected ProcessingContext initializeInboundProcessingContext(Packet packet) {
/* 1262 */     ProcessingContextImpl ctx = null;
/*      */     
/* 1264 */     if (this.optimized) {
/* 1265 */       JAXBFilterProcessingContext jAXBFilterProcessingContext = new JAXBFilterProcessingContext(packet.invocationProperties);
/* 1266 */       jAXBFilterProcessingContext.setAddressingVersion(this.addVer);
/* 1267 */       jAXBFilterProcessingContext.setSOAPVersion(this.soapVersion);
/* 1268 */       jAXBFilterProcessingContext.setSecure(packet.wasTransportSecure);
/* 1269 */       jAXBFilterProcessingContext.setDisableIncPrefix(this.disableIncPrefix);
/* 1270 */       jAXBFilterProcessingContext.setEncHeaderContent(this.encHeaderContent);
/* 1271 */       jAXBFilterProcessingContext.setAllowMissingTimestamp(this.allowMissingTimestamp);
/* 1272 */       jAXBFilterProcessingContext.setMustUnderstandValue(this.securityMUValue);
/*      */     } else {
/* 1274 */       ctx = new ProcessingContextImpl(packet.invocationProperties);
/*      */     } 
/* 1276 */     String action = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1285 */     ctx.setAddressingEnabled(isAddressingEnabled());
/* 1286 */     ctx.setWsscVer(this.wsscVer);
/*      */ 
/*      */     
/* 1289 */     ctx.setSecurityPolicyVersion(this.spVersion.namespaceUri);
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
/* 1300 */     ctx.setBootstrapAlgoSuite(getAlgoSuite(this.bootStrapAlgoSuite));
/* 1301 */     ctx.setAlgorithmSuite(getAlgoSuite(getBindingAlgorithmSuite(packet)));
/*      */ 
/*      */     
/* 1304 */     ctx.hasIssuedToken(bindingHasIssuedTokenPolicy());
/* 1305 */     ctx.setSecurityEnvironment(this.secEnv);
/*      */     
/* 1307 */     if (this.serverCert != null) {
/* 1308 */       if (!this.isCertValidityVerified) {
/* 1309 */         CertificateRetriever cr = new CertificateRetriever();
/* 1310 */         this.isCertValid = cr.setServerCertInTheContext(ctx, this.secEnv, this.serverCert);
/* 1311 */         cr = null;
/* 1312 */         this.isCertValidityVerified = true;
/*      */       }
/* 1314 */       else if (this.isCertValid == true) {
/* 1315 */         ctx.getExtraneousProperties().put("server-certificate", this.serverCert);
/*      */       } 
/*      */     }
/*      */     
/* 1319 */     ctx.isInboundMessage(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1324 */     ctx.setWsTrustVer(this.wsTrustVer);
/*      */     
/* 1326 */     if (this.pipeConfig.getWSDLPort() != null) {
/* 1327 */       ctx.getExtraneousProperties().put("WSDLPort", this.pipeConfig.getWSDLPort());
/*      */     }
/* 1329 */     if (this.pipeConfig instanceof ServerTubeConfiguration) {
/* 1330 */       ctx.getExtraneousProperties().put("WSEndpoint", ((ServerTubeConfiguration)this.pipeConfig).getEndpoint());
/*      */     }
/* 1332 */     return (ProcessingContext)ctx;
/*      */   }
/*      */   
/*      */   private void populateTruststoreProps(Properties props, TrustStore store) {
/* 1336 */     if (store.getLocation() != null) {
/* 1337 */       props.put("truststore.url", store.getLocation());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1347 */     if (store.getType() != null) {
/* 1348 */       props.put("truststore.type", store.getType());
/*      */     } else {
/* 1350 */       props.put("truststore.type", "JKS");
/*      */     } 
/*      */     
/* 1353 */     if (store.getPassword() != null) {
/* 1354 */       props.put("truststore.password", new String(store.getPassword()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1363 */     if (store.getPeerAlias() != null) {
/* 1364 */       props.put("peerentity.alias", store.getPeerAlias());
/*      */     }
/*      */     
/* 1367 */     if (store.getSTSAlias() != null) {
/* 1368 */       props.put("sts.alias", store.getSTSAlias());
/*      */     }
/*      */     
/* 1371 */     if (store.getServiceAlias() != null) {
/* 1372 */       props.put("service.alias", store.getServiceAlias());
/*      */     }
/*      */     
/* 1375 */     if (store.getCertSelectorClassName() != null) {
/* 1376 */       props.put("truststore.certselector", store.getCertSelectorClassName());
/*      */     }
/*      */   }
/*      */   
/*      */   private String populateCallbackHandlerProps(Properties props, CallbackHandlerConfiguration conf) {
/* 1381 */     if (conf.getTimestampTimeout() != null)
/*      */     {
/* 1383 */       this.timestampTimeOut = Long.parseLong(conf.getTimestampTimeout()) * 1000L;
/*      */     }
/* 1385 */     if (conf.getUseXWSSCallbacks() != null) {
/* 1386 */       props.put("user.xwss.callbacks", conf.getUseXWSSCallbacks());
/*      */     }
/* 1388 */     if (conf.getiterationsForPDK() != null) {
/* 1389 */       this.iterationsForPDK = Integer.parseInt(conf.getiterationsForPDK());
/*      */     }
/* 1391 */     Iterator<PolicyAssertion> it = conf.getCallbackHandlers();
/* 1392 */     while (it.hasNext()) {
/* 1393 */       PolicyAssertion p = it.next();
/* 1394 */       CallbackHandler hd = (CallbackHandler)p;
/* 1395 */       String name = hd.getHandlerName();
/* 1396 */       String ret = hd.getHandler();
/* 1397 */       if ("xwssCallbackHandler".equals(name)) {
/* 1398 */         if (ret != null && !"".equals(ret)) {
/* 1399 */           return ret;
/*      */         }
/* 1401 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0018_NULL_OR_EMPTY_XWSS_CALLBACK_HANDLER_CLASSNAME());
/*      */         
/* 1403 */         throw new RuntimeException(LogStringsMessages.WSITPVD_0018_NULL_OR_EMPTY_XWSS_CALLBACK_HANDLER_CLASSNAME());
/*      */       } 
/* 1405 */       if ("jmacCallbackHandler".equals(name)) {
/* 1406 */         if (ret != null && !"".equals(ret)) {
/* 1407 */           props.put("jmac.callbackhandler", ret); continue;
/*      */         } 
/* 1409 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0051_NULL_OR_EMPTY_JMAC_CALLBACK_HANDLER_CLASSNAME());
/*      */         
/* 1411 */         throw new RuntimeException(LogStringsMessages.WSITPVD_0051_NULL_OR_EMPTY_JMAC_CALLBACK_HANDLER_CLASSNAME());
/*      */       } 
/* 1413 */       if ("usernameHandler".equals(name)) {
/* 1414 */         if (ret != null && !"".equals(ret)) {
/* 1415 */           props.put("username.callback.handler", ret); continue;
/*      */         } 
/* 1417 */         QName qname = new QName("default");
/* 1418 */         String def = hd.getAttributeValue(qname);
/* 1419 */         if (def != null && !"".equals(def)) {
/* 1420 */           props.put("my.username", def); continue;
/*      */         } 
/* 1422 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0019_NULL_OR_EMPTY_USERNAME_HANDLER_CLASSNAME());
/*      */         
/* 1424 */         throw new RuntimeException(LogStringsMessages.WSITPVD_0019_NULL_OR_EMPTY_USERNAME_HANDLER_CLASSNAME());
/*      */       } 
/*      */       
/* 1427 */       if ("passwordHandler".equals(name)) {
/* 1428 */         if (ret != null && !"".equals(ret)) {
/* 1429 */           props.put("password.callback.handler", ret); continue;
/*      */         } 
/* 1431 */         QName qname = new QName("default");
/* 1432 */         String def = hd.getAttributeValue(qname);
/* 1433 */         if (def != null && !"".equals(def)) {
/* 1434 */           props.put("my.password", def); continue;
/*      */         } 
/* 1436 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0020_NULL_OR_EMPTY_PASSWORD_HANDLER_CLASSNAME());
/*      */         
/* 1438 */         throw new RuntimeException(LogStringsMessages.WSITPVD_0020_NULL_OR_EMPTY_PASSWORD_HANDLER_CLASSNAME());
/*      */       } 
/*      */       
/* 1441 */       if ("samlHandler".equals(name)) {
/* 1442 */         if (ret == null || "".equals(ret)) {
/* 1443 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0021_NULL_OR_EMPTY_SAML_HANDLER_CLASSNAME());
/*      */           
/* 1445 */           throw new RuntimeException(LogStringsMessages.WSITPVD_0021_NULL_OR_EMPTY_SAML_HANDLER_CLASSNAME());
/*      */         } 
/* 1447 */         props.put("saml.callback.handler", ret); continue;
/*      */       } 
/* 1449 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0009_UNSUPPORTED_CALLBACK_TYPE_ENCOUNTERED(name));
/*      */       
/* 1451 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0009_UNSUPPORTED_CALLBACK_TYPE_ENCOUNTERED(name));
/*      */     } 
/*      */     
/* 1454 */     return null;
/*      */   }
/*      */   
/*      */   private void populateValidatorProps(Properties props, ValidatorConfiguration conf) {
/* 1458 */     if (conf.getMaxClockSkew() != null) {
/* 1459 */       props.put("max.clock.skew", conf.getMaxClockSkew());
/*      */     }
/*      */     
/* 1462 */     if (conf.getTimestampFreshnessLimit() != null) {
/* 1463 */       props.put("timestamp.freshness.limit", conf.getTimestampFreshnessLimit());
/*      */     }
/*      */     
/* 1466 */     if (conf.getMaxNonceAge() != null) {
/* 1467 */       props.put("max.nonce.age", conf.getMaxNonceAge());
/*      */     }
/*      */     
/* 1470 */     if (conf.getRevocationEnabled() != null) {
/* 1471 */       props.put("revocation.enabled", conf.getRevocationEnabled());
/*      */     }
/*      */     
/* 1474 */     Iterator<PolicyAssertion> it = conf.getValidators();
/* 1475 */     while (it.hasNext()) {
/* 1476 */       PolicyAssertion p = it.next();
/* 1477 */       Validator v = (Validator)p;
/* 1478 */       String name = v.getValidatorName();
/* 1479 */       String validator = v.getValidator();
/* 1480 */       if (validator == null || "".equals(validator)) {
/* 1481 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0022_NULL_OR_EMPTY_VALIDATOR_CLASSNAME(name));
/*      */         
/* 1483 */         throw new RuntimeException(LogStringsMessages.WSITPVD_0022_NULL_OR_EMPTY_VALIDATOR_CLASSNAME(name));
/*      */       } 
/* 1485 */       if ("usernameValidator".equals(name)) {
/* 1486 */         props.put("username.validator", validator); continue;
/* 1487 */       }  if ("timestampValidator".equals(name)) {
/* 1488 */         props.put("timestamp.validator", validator); continue;
/* 1489 */       }  if ("certificateValidator".equals(name)) {
/* 1490 */         props.put("certificate.validator", validator); continue;
/* 1491 */       }  if ("samlAssertionValidator".equals(name)) {
/* 1492 */         props.put("saml.validator", validator); continue;
/*      */       } 
/* 1494 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0010_UNKNOWN_VALIDATOR_TYPE_CONFIG(name));
/*      */       
/* 1496 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0010_UNKNOWN_VALIDATOR_TYPE_CONFIG(name));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateCertStoreProps(Properties props, CertStoreConfig certStoreConfig) {
/* 1502 */     if (certStoreConfig.getCallbackHandlerClassName() != null) {
/* 1503 */       props.put("certstore.cbh", certStoreConfig.getCallbackHandlerClassName());
/*      */     }
/* 1505 */     if (certStoreConfig.getCertSelectorClassName() != null) {
/* 1506 */       props.put("certstore.certselector", certStoreConfig.getCertSelectorClassName());
/*      */     }
/* 1508 */     if (certStoreConfig.getCRLSelectorClassName() != null) {
/* 1509 */       props.put("certstore.crlselector", certStoreConfig.getCRLSelectorClassName());
/*      */     }
/*      */   }
/*      */   
/*      */   protected AlgorithmSuite getAlgoSuite(AlgorithmSuite suite) {
/* 1514 */     if (suite == null) {
/* 1515 */       return null;
/*      */     }
/* 1517 */     AlgorithmSuite als = new AlgorithmSuite(suite.getDigestAlgorithm(), suite.getEncryptionAlgorithm(), suite.getSymmetricKeyAlgorithm(), suite.getAsymmetricKeyAlgorithm());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1522 */     als.setSignatureAlgorithm(suite.getSignatureAlgorithm());
/* 1523 */     return als;
/*      */   }
/*      */   
/*      */   protected WSSAssertion getWssAssertion(WSSAssertion asser) {
/* 1527 */     WSSAssertion assertion = new WSSAssertion(asser.getRequiredProperties(), asser.getType());
/*      */ 
/*      */     
/* 1530 */     return assertion;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isReliableMessagingEnabled(WSDLPort port) {
/* 1535 */     if (port != null && port.getBinding() != null) {
/* 1536 */       boolean enabled = port.getBinding().getFeatures().isEnabled(ReliableMessagingFeature.class);
/* 1537 */       return enabled;
/*      */     } 
/* 1539 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isMakeConnectionEnabled(WSDLPort port) {
/* 1543 */     if (port != null && port.getBinding() != null) {
/* 1544 */       boolean enabled = port.getBinding().getFeatures().isEnabled(MakeConnectionSupportedFeature.class);
/* 1545 */       return enabled;
/*      */     } 
/* 1547 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean bindingHasIssuedTokenPolicy() {
/* 1551 */     return this.hasIssuedTokens;
/*      */   }
/*      */   
/*      */   protected boolean bindingHasSecureConversationPolicy() {
/* 1555 */     return this.hasSecureConversation;
/*      */   }
/*      */   
/*      */   protected boolean hasKerberosTokenPolicy() {
/* 1559 */     return this.hasKerberosToken;
/*      */   }
/*      */   
/*      */   protected boolean bindingHasRMPolicy() {
/* 1563 */     return this.hasReliableMessaging;
/*      */   }
/*      */   
/*      */   protected Class loadClass(String classname) throws Exception {
/* 1567 */     if (classname == null) {
/* 1568 */       return null;
/*      */     }
/* 1570 */     Class<?> ret = null;
/* 1571 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 1572 */     if (loader != null) {
/*      */       try {
/* 1574 */         ret = loader.loadClass(classname);
/* 1575 */         return ret;
/* 1576 */       } catch (ClassNotFoundException e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1581 */     loader = getClass().getClassLoader();
/*      */     try {
/* 1583 */       ret = loader.loadClass(classname);
/* 1584 */       return ret;
/* 1585 */     } catch (ClassNotFoundException e) {
/*      */ 
/*      */       
/* 1588 */       log.log(Level.FINE, LogStringsMessages.WSITPVD_0011_COULD_NOT_FIND_USER_CLASS(), classname);
/*      */       
/* 1590 */       throw new XWSSecurityException(LogStringsMessages.WSITPVD_0011_COULD_NOT_FIND_USER_CLASS() + " : " + classname);
/*      */     } 
/*      */   }
/*      */   protected WSDLBoundOperation getOperation(Message message, Packet packet) {
/* 1594 */     WSDLBoundOperation op = cachedOperation(packet);
/* 1595 */     if (op == null) {
/* 1596 */       op = cacheOperation(message, packet);
/*      */     }
/* 1598 */     return op;
/*      */   }
/*      */ 
/*      */   
/*      */   protected ProcessingContext initializeOutgoingProcessingContext(Packet packet, boolean isSCMessage) {
/* 1603 */     ProcessingContextImpl ctx = null;
/* 1604 */     if (this.optimized) {
/* 1605 */       JAXBFilterProcessingContext jAXBFilterProcessingContext = new JAXBFilterProcessingContext(packet.invocationProperties);
/* 1606 */       jAXBFilterProcessingContext.setAddressingVersion(this.addVer);
/* 1607 */       jAXBFilterProcessingContext.setSOAPVersion(this.soapVersion);
/* 1608 */       jAXBFilterProcessingContext.setDisableIncPrefix(this.disableIncPrefix);
/* 1609 */       jAXBFilterProcessingContext.setEncHeaderContent(this.encHeaderContent);
/* 1610 */       jAXBFilterProcessingContext.setAllowMissingTimestamp(this.allowMissingTimestamp);
/* 1611 */       jAXBFilterProcessingContext.setMustUnderstandValue(this.securityMUValue);
/*      */     } else {
/* 1613 */       ctx = new ProcessingContextImpl(packet.invocationProperties);
/*      */     } 
/* 1615 */     if (this.addVer != null) {
/* 1616 */       ctx.setAction(getAction(packet));
/*      */     }
/*      */     
/* 1619 */     ctx.setSecurityPolicyVersion(this.spVersion.namespaceUri);
/*      */     
/* 1621 */     ctx.setTimestampTimeout(this.timestampTimeOut);
/* 1622 */     ctx.setiterationsForPDK(this.iterationsForPDK);
/*      */ 
/*      */     
/* 1625 */     ctx.setAlgorithmSuite(getAlgoSuite(getBindingAlgorithmSuite(packet)));
/*      */     
/* 1627 */     if (this.serverCert != null) {
/* 1628 */       if (!this.isCertValidityVerified) {
/* 1629 */         CertificateRetriever cr = new CertificateRetriever();
/* 1630 */         this.isCertValid = cr.setServerCertInTheContext(ctx, this.secEnv, this.serverCert);
/* 1631 */         cr = null;
/* 1632 */         this.isCertValidityVerified = true;
/*      */       }
/* 1634 */       else if (this.isCertValid == true) {
/* 1635 */         ctx.getExtraneousProperties().put("server-certificate", this.serverCert);
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/* 1640 */       PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*      */       
/* 1642 */       MessagePolicy policy = null;
/* 1643 */       if (isRMMessage(packet) || isMakeConnectionMessage(packet)) {
/* 1644 */         SecurityPolicyHolder holder = applicableAlternative.getOutProtocolPM().get("RM");
/* 1645 */         policy = holder.getMessagePolicy();
/* 1646 */       } else if (isSCCancel(packet)) {
/* 1647 */         SecurityPolicyHolder holder = applicableAlternative.getOutProtocolPM().get("SC-CANCEL");
/* 1648 */         policy = holder.getMessagePolicy();
/* 1649 */       } else if (isSCRenew(packet)) {
/* 1650 */         policy = getOutgoingXWSSecurityPolicy(packet, isSCMessage);
/* 1651 */         ctx.isExpired(true);
/*      */       } else {
/* 1653 */         policy = getOutgoingXWSSecurityPolicy(packet, isSCMessage);
/*      */       } 
/* 1655 */       if (debug) {
/* 1656 */         policy.dumpMessages(true);
/*      */       }
/* 1658 */       if (policy.getAlgorithmSuite() != null)
/*      */       {
/* 1660 */         ctx.setAlgorithmSuite(policy.getAlgorithmSuite());
/*      */       }
/* 1662 */       ctx.setWSSAssertion(policy.getWSSAssertion());
/* 1663 */       ctx.setSecurityPolicy((SecurityPolicy)policy);
/* 1664 */       ctx.setSecurityEnvironment(this.secEnv);
/* 1665 */       ctx.isInboundMessage(false);
/* 1666 */     } catch (XWSSecurityException e) {
/* 1667 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), (Throwable)e);
/* 1668 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), e);
/*      */     } 
/* 1670 */     return (ProcessingContext)ctx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MessagePolicy getOutgoingXWSSecurityPolicy(Packet packet, boolean isSCMessage) {
/* 1677 */     if (isSCMessage) {
/* 1678 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/* 1679 */       return getOutgoingXWSBootstrapPolicy(scToken);
/*      */     } 
/* 1681 */     Message message = packet.getMessage();
/* 1682 */     WSDLBoundOperation operation = null;
/* 1683 */     if (isTrustMessage(packet)) {
/* 1684 */       operation = getWSDLOpFromAction(packet, false);
/*      */     } else {
/* 1686 */       operation = message.getOperation(this.pipeConfig.getWSDLPort());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1691 */     MessagePolicy mp = null;
/* 1692 */     PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1699 */     if (applicableAlternative.getOutMessagePolicyMap() == null)
/*      */     {
/* 1701 */       return new MessagePolicy();
/*      */     }
/* 1703 */     SecurityPolicyHolder sph = applicableAlternative.getOutMessagePolicyMap().get(operation);
/*      */     
/* 1705 */     if (sph == null) {
/* 1706 */       return new MessagePolicy();
/*      */     }
/* 1708 */     mp = sph.getMessagePolicy();
/* 1709 */     return mp;
/*      */   }
/*      */   
/*      */   protected MessagePolicy getOutgoingXWSBootstrapPolicy(Token scAssertion) {
/* 1713 */     return ((SCTokenWrapper)scAssertion).getMessagePolicy();
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPFaultException getSOAPFaultException(WssSoapFaultException sfe) {
/* 1718 */     SOAPFault fault = null;
/*      */     try {
/* 1720 */       if (this.isSOAP12) {
/* 1721 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/* 1722 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*      */       } else {
/* 1724 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*      */       } 
/* 1726 */     } catch (Exception e) {
/* 1727 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0002_INTERNAL_SERVER_ERROR());
/* 1728 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0002_INTERNAL_SERVER_ERROR(), e);
/*      */     } 
/* 1730 */     return new SOAPFaultException(fault);
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPFaultException getSOAPFaultException(XWSSecurityException xwse) {
/* 1735 */     QName qname = null;
/* 1736 */     if (xwse.getCause() instanceof com.sun.xml.wss.impl.PolicyViolationException) {
/* 1737 */       qname = MessageConstants.WSSE_RECEIVER_POLICY_VIOLATION;
/*      */     } else {
/* 1739 */       qname = MessageConstants.WSSE_FAILED_AUTHENTICATION;
/*      */     } 
/*      */     
/* 1742 */     WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(qname, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */     
/* 1746 */     return getSOAPFaultException(wsfe);
/*      */   }
/*      */   
/*      */   protected SOAPMessage secureOutboundMessage(SOAPMessage message, ProcessingContext ctx) {
/*      */     try {
/* 1751 */       ctx.setSOAPMessage(message);
/* 1752 */       SecurityAnnotator.secureMessage(ctx);
/* 1753 */       return ctx.getSOAPMessage();
/* 1754 */     } catch (WssSoapFaultException soapFaultException) {
/* 1755 */       throw getSOAPFaultException(soapFaultException);
/* 1756 */     } catch (XWSSecurityException xwse) {
/* 1757 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)xwse);
/*      */       
/* 1759 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/* 1763 */       throw wsfe;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Message secureOutboundMessage(Message message, ProcessingContext ctx) {
/*      */     try {
/* 1769 */       JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)ctx;
/* 1770 */       context.setSOAPVersion(this.soapVersion);
/* 1771 */       context.setAllowMissingTimestamp(this.allowMissingTimestamp);
/* 1772 */       context.setMustUnderstandValue(this.securityMUValue);
/* 1773 */       context.setWSSAssertion(((MessagePolicy)ctx.getSecurityPolicy()).getWSSAssertion());
/* 1774 */       context.setJAXWSMessage(message, this.soapVersion);
/* 1775 */       context.isOneWayMessage(message.isOneWay(this.pipeConfig.getWSDLPort()));
/* 1776 */       context.setDisableIncPrefix(this.disableIncPrefix);
/* 1777 */       context.setEncHeaderContent(this.encHeaderContent);
/* 1778 */       SecurityAnnotator.secureMessage((ProcessingContext)context);
/* 1779 */       return context.getJAXWSMessage();
/* 1780 */     } catch (XWSSecurityException xwse) {
/* 1781 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)xwse);
/*      */       
/* 1783 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/* 1787 */       throw wsfe;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPFault getSOAPFault(WssSoapFaultException sfe) {
/* 1793 */     SOAPFault fault = null;
/*      */     try {
/* 1795 */       if (this.isSOAP12) {
/* 1796 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/* 1797 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*      */       } else {
/* 1799 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*      */       } 
/* 1801 */     } catch (Exception e) {
/* 1802 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0002_INTERNAL_SERVER_ERROR());
/* 1803 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0002_INTERNAL_SERVER_ERROR(), e);
/*      */     } 
/* 1805 */     return fault;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CallbackHandler loadGFHandler(boolean isClientAuthModule, String jmacHandler) {
/* 1812 */     String classname = "com.sun.enterprise.security.jmac.callback.ContainerCallbackHandler";
/* 1813 */     if (jmacHandler != null) {
/* 1814 */       classname = jmacHandler;
/*      */     }
/* 1816 */     Class<?> ret = null;
/*      */     
/*      */     try {
/* 1819 */       ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*      */       try {
/* 1821 */         if (loader != null) {
/* 1822 */           ret = loader.loadClass(classname);
/*      */         }
/* 1824 */       } catch (ClassNotFoundException e) {}
/*      */ 
/*      */ 
/*      */       
/* 1828 */       if (ret == null) {
/*      */         
/* 1830 */         loader = getClass().getClassLoader();
/* 1831 */         ret = loader.loadClass(classname);
/*      */       } 
/*      */       
/* 1834 */       if (ret != null) {
/* 1835 */         CallbackHandler handler = (CallbackHandler)ret.newInstance();
/* 1836 */         return handler;
/*      */       } 
/* 1838 */     } catch (ClassNotFoundException e) {
/*      */ 
/*      */     
/* 1841 */     } catch (InstantiationException e) {
/*      */     
/* 1843 */     } catch (IllegalAccessException ex) {}
/*      */ 
/*      */     
/* 1846 */     log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0023_COULD_NOT_LOAD_CALLBACK_HANDLER_CLASS(classname));
/*      */     
/* 1848 */     throw new RuntimeException(LogStringsMessages.WSITPVD_0023_COULD_NOT_LOAD_CALLBACK_HANDLER_CLASS(classname));
/*      */   }
/*      */ 
/*      */   
/*      */   protected Packet getRequestPacket(MessageInfo messageInfo) {
/* 1853 */     return (Packet)messageInfo.getMap().get("REQ_PACKET");
/*      */   }
/*      */   
/*      */   protected Packet getResponsePacket(MessageInfo messageInfo) {
/* 1857 */     return (Packet)messageInfo.getMap().get("RES_PACKET");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setRequestPacket(MessageInfo messageInfo, Packet ret) {
/* 1862 */     messageInfo.getMap().put("REQ_PACKET", ret);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setResponsePacket(MessageInfo messageInfo, Packet ret) {
/* 1867 */     messageInfo.getMap().put("RES_PACKET", ret);
/*      */   }
/*      */   
/*      */   private Policy getSCCancelPolicy(boolean encryptCancelPayload) throws PolicyException, IOException {
/* 1871 */     if (this.cancelMSP == null) {
/*      */       
/* 1873 */       String scCancelMessagePolicy = encryptCancelPayload ? "enc-sccancel-msglevel-policy.xml" : "sccancel-msglevel-policy.xml";
/* 1874 */       if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri)) {
/* 1875 */         scCancelMessagePolicy = encryptCancelPayload ? "enc-sccancel-msglevel-policy-sx.xml" : "sccancel-msglevel-policy-sx.xml";
/*      */       }
/* 1877 */       PolicySourceModel model = unmarshalPolicy("com/sun/xml/ws/security/impl/policyconv/" + scCancelMessagePolicy);
/*      */       
/* 1879 */       this.cancelMSP = ModelTranslator.getTranslator().translate(model);
/*      */     } 
/* 1881 */     return this.cancelMSP;
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
/*      */   protected PolicyAlternativeHolder resolveAlternative(Packet packet, boolean isSCMessage) {
/* 1895 */     if (this.policyAlternatives.size() == 1) {
/* 1896 */       return this.policyAlternatives.get(0);
/*      */     }
/*      */     
/* 1899 */     String alternativeId = (String)packet.invocationProperties.get("policy-alternative-id");
/* 1900 */     if (alternativeId != null) {
/* 1901 */       for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 1902 */         if (alternativeId.equals(p.getId())) {
/* 1903 */           return p;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1908 */     if (!this.policyAlternatives.isEmpty()) {
/* 1909 */       return this.policyAlternatives.get(0);
/*      */     }
/* 1911 */     return null;
/*      */   }
/*      */   
/*      */   protected abstract SecurityPolicyHolder addOutgoingMP(WSDLBoundOperation paramWSDLBoundOperation, Policy paramPolicy, PolicyAlternativeHolder paramPolicyAlternativeHolder) throws PolicyException;
/*      */   
/*      */   protected abstract SecurityPolicyHolder addIncomingMP(WSDLBoundOperation paramWSDLBoundOperation, Policy paramPolicy, PolicyAlternativeHolder paramPolicyAlternativeHolder) throws PolicyException;
/*      */   
/*      */   protected abstract void addIncomingFaultPolicy(Policy paramPolicy, SecurityPolicyHolder paramSecurityPolicyHolder, WSDLFault paramWSDLFault) throws PolicyException;
/*      */   
/*      */   protected abstract void addOutgoingFaultPolicy(Policy paramPolicy, SecurityPolicyHolder paramSecurityPolicyHolder, WSDLFault paramWSDLFault) throws PolicyException;
/*      */   
/*      */   protected abstract void addIncomingProtocolPolicy(Policy paramPolicy, String paramString, PolicyAlternativeHolder paramPolicyAlternativeHolder) throws PolicyException;
/*      */   
/*      */   protected abstract void addOutgoingProtocolPolicy(Policy paramPolicy, String paramString, PolicyAlternativeHolder paramPolicyAlternativeHolder) throws PolicyException;
/*      */   
/*      */   protected abstract String getAction(WSDLOperation paramWSDLOperation, boolean paramBoolean);
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITAuthContextBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */