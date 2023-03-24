/*      */ package com.sun.xml.wss.jaxws.impl;
/*      */ 
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.ws.api.message.AttachmentSet;
/*      */ import com.sun.xml.ws.api.message.HeaderList;
/*      */ import com.sun.xml.ws.api.message.Message;
/*      */ import com.sun.xml.ws.api.message.Packet;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.ws.api.pipe.Tube;
/*      */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*      */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*      */ import com.sun.xml.ws.api.policy.ModelTranslator;
/*      */ import com.sun.xml.ws.api.policy.ModelUnmarshaller;
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
/*      */ import com.sun.xml.ws.security.impl.policyconv.SCTokenWrapper;
/*      */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*      */ import com.sun.xml.ws.security.impl.policyconv.XWSSPolicyGenerator;
/*      */ import com.sun.xml.ws.security.message.stream.LazyStreamBasedMessage;
/*      */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*      */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityRecipient;
/*      */ import com.sun.xml.ws.security.opt.impl.util.CertificateRetriever;
/*      */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*      */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*      */ import com.sun.xml.ws.security.policy.CallbackHandlerConfiguration;
/*      */ import com.sun.xml.ws.security.policy.CertStoreConfig;
/*      */ import com.sun.xml.ws.security.policy.KerberosConfig;
/*      */ import com.sun.xml.ws.security.policy.KeyStore;
/*      */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*      */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*      */ import com.sun.xml.ws.security.policy.SessionManagerStore;
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
/*      */ import com.sun.xml.wss.impl.NewSecurityRecipient;
/*      */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*      */ import com.sun.xml.wss.impl.WSSAssertion;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.jaxws.impl.logging.LogStringsMessages;
/*      */ import com.sun.xml.wss.provider.wsit.PolicyAlternativeHolder;
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
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.bind.JAXBContext;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.Marshaller;
/*      */ import javax.xml.bind.Unmarshaller;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.SOAPConstants;
/*      */ import javax.xml.soap.SOAPException;
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
/*      */ public abstract class SecurityTubeBase
/*      */   extends AbstractFilterTubeImpl
/*      */ {
/*  153 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.jaxws.impl", "com.sun.xml.wss.jaxws.impl.logging.LogStrings");
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean optimized = true;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean transportOptimization = false;
/*      */ 
/*      */   
/*  164 */   protected Hashtable<String, IssuedTokenContext> issuedTokenContextMap = new Hashtable<String, IssuedTokenContext>();
/*  165 */   protected TubeConfiguration tubeConfig = null;
/*      */   
/*      */   protected static JAXBContext jaxbContext;
/*      */   protected WSSCVersion wsscVer;
/*      */   protected WSTrustVersion wsTrustVer;
/*  170 */   protected RmProtocolVersion rmVer = RmProtocolVersion.WSRM200502;
/*  171 */   protected McProtocolVersion mcVer = McProtocolVersion.WSMC200702;
/*      */   protected boolean disablePayloadBuffer = false;
/*  173 */   protected AlgorithmSuite bindingLevelAlgSuite = null;
/*  174 */   private final QName EPREnabled = new QName("http://schemas.sun.com/2006/03/wss/server", "EnableEPRIdentity");
/*  175 */   private final QName encSCServerCancel = new QName("http://schemas.sun.com/2006/03/wss/server", "EncSCCancel");
/*  176 */   private final QName encSCClientCancel = new QName("http://schemas.sun.com/2006/03/wss/client", "EncSCCancel");
/*  177 */   private final QName optServerSecurity = new QName("http://schemas.sun.com/2006/03/wss/server", "DisableStreamingSecurity");
/*  178 */   private final QName optClientSecurity = new QName("http://schemas.sun.com/2006/03/wss/client", "DisableStreamingSecurity");
/*  179 */   private final QName disableSPBuffering = new QName("http://schemas.sun.com/2006/03/wss/server", "DisablePayloadBuffering");
/*  180 */   private final QName disableCPBuffering = new QName("http://schemas.sun.com/2006/03/wss/client", "DisablePayloadBuffering");
/*      */   protected boolean disableIncPrefix = false;
/*  182 */   private final QName disableIncPrefixServer = new QName("http://schemas.sun.com/2006/03/wss/server", "DisableInclusivePrefixList");
/*  183 */   private final QName disableIncPrefixClient = new QName("http://schemas.sun.com/2006/03/wss/client", "DisableInclusivePrefixList");
/*      */   protected boolean encHeaderContent = false;
/*  185 */   private final QName encHeaderContentServer = new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptHeaderContent");
/*  186 */   private final QName encHeaderContentClient = new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptHeaderContent");
/*  187 */   private final QName bsp10Server = new QName("http://schemas.sun.com/2006/03/wss/server", "BSP10");
/*  188 */   private final QName bsp10Client = new QName("http://schemas.sun.com/2006/03/wss/client", "BSP10");
/*      */   protected boolean bsp10 = false;
/*      */   protected boolean allowMissingTimestamp = false;
/*  191 */   private final QName allowMissingTSServer = new QName("http://schemas.sun.com/2006/03/wss/server", "AllowMissingTimestamp");
/*  192 */   private final QName allowMissingTSClient = new QName("http://schemas.sun.com/2006/03/wss/client", "AllowMissingTimestamp");
/*      */   protected boolean securityMUValue = true;
/*  194 */   private final QName unsetSecurityMUValueServer = new QName("http://schemas.sun.com/2006/03/wss/server", "UnsetSecurityMUValue");
/*  195 */   private final QName unsetSecurityMUValueClient = new QName("http://schemas.sun.com/2006/03/wss/client", "UnsetSecurityMUValue");
/*      */   protected boolean encRMLifecycleMsg = false;
/*  197 */   private final QName encRMLifecycleMsgServer = new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptRMLifecycleMessage");
/*  198 */   private final QName encRMLifecycleMsgClient = new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptRMLifecycleMessage");
/*      */   protected static final ArrayList<String> securityPolicyNamespaces;
/*  200 */   protected static final List<PolicyAssertion> EMPTY_LIST = Collections.emptyList();
/*      */   
/*  202 */   protected SecurityEnvironment secEnv = null;
/*      */   
/*      */   protected static final boolean debug = false;
/*      */   
/*      */   protected boolean isSOAP12 = false;
/*  207 */   protected SOAPVersion soapVersion = null;
/*      */   
/*  209 */   protected SOAPFactory soapFactory = null;
/*  210 */   protected PolicyMap wsPolicyMap = null;
/*      */ 
/*      */   
/*  213 */   protected Policy bpMSP = null;
/*      */   
/*  215 */   protected long timestampTimeOut = 0L;
/*  216 */   protected int iterationsForPDK = 0;
/*      */   protected boolean isEPREnabled = false;
/*      */   protected boolean isCertValidityVerified = false;
/*  219 */   protected List<PolicyAlternativeHolder> policyAlternatives = new ArrayList<PolicyAlternativeHolder>();
/*      */ 
/*      */ 
/*      */   
/*  223 */   protected WSDLBoundOperation cachedOperation = null;
/*  224 */   protected Policy wsitConfig = null;
/*      */   
/*  226 */   protected Marshaller marshaller = null;
/*  227 */   protected Unmarshaller unmarshaller = null;
/*      */   
/*      */   boolean hasIssuedTokens = false;
/*      */   
/*      */   boolean hasSecureConversation = false;
/*      */   
/*      */   boolean hasReliableMessaging = false;
/*      */   
/*      */   boolean hasMakeConnection = false;
/*      */   boolean hasKerberosToken = false;
/*  237 */   AddressingVersion addVer = null;
/*      */   
/*  239 */   protected SecurityPolicyVersion spVersion = null;
/*      */   
/*      */   protected static final String WSDLPORT = "WSDLPort";
/*      */   
/*      */   protected static final String WSENDPOINT = "WSEndpoint";
/*  244 */   protected X509Certificate serverCert = null;
/*      */ 
/*      */   
/*      */   private boolean encryptCancelPayload = false;
/*      */   
/*      */   private Policy cancelMSP;
/*      */   
/*      */   protected boolean isCertValid;
/*      */   
/*      */   private AlgorithmSuite bootStrapAlgoSuite;
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  258 */       securityPolicyNamespaces = new ArrayList<String>();
/*  259 */       securityPolicyNamespaces.add(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri);
/*      */     }
/*  261 */     catch (Exception e) {
/*  262 */       throw new RuntimeException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public SecurityTubeBase(TubeConfiguration config, Tube nextTube) {
/*  267 */     super(nextTube);
/*  268 */     this.tubeConfig = config;
/*  269 */     this.soapVersion = this.tubeConfig.getBinding().getSOAPVersion();
/*      */     
/*  271 */     this.isSOAP12 = (this.soapVersion == SOAPVersion.SOAP_12);
/*  272 */     this.wsPolicyMap = this.tubeConfig.getPolicyMap();
/*  273 */     this.soapFactory = (this.tubeConfig.getBinding().getSOAPVersion()).saajSoapFactory;
/*      */     
/*  275 */     if (this.wsPolicyMap != null) {
/*  276 */       collectPolicies(this.policyAlternatives);
/*      */     }
/*      */     
/*      */     try {
/*  280 */       jaxbContext = WSTrustElementFactory.getContext(this.wsTrustVer);
/*  281 */       this.marshaller = jaxbContext.createMarshaller();
/*  282 */       this.unmarshaller = jaxbContext.createUnmarshaller();
/*  283 */     } catch (JAXBException ex) {
/*  284 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0001_PROBLEM_MAR_UNMAR(), ex);
/*  285 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0001_PROBLEM_MAR_UNMAR(), ex);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  290 */     this.hasReliableMessaging = isReliableMessagingEnabled(this.tubeConfig.getWSDLPort());
/*  291 */     this.hasMakeConnection = isMakeConnectionEnabled(this.tubeConfig.getWSDLPort());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SecurityTubeBase(SecurityTubeBase that, TubeCloner cloner) {
/*  297 */     super(that, cloner);
/*  298 */     this.tubeConfig = that.tubeConfig;
/*  299 */     this.transportOptimization = that.transportOptimization;
/*  300 */     this.optimized = that.optimized;
/*  301 */     this.disableIncPrefix = that.disableIncPrefix;
/*  302 */     this.allowMissingTimestamp = that.allowMissingTimestamp;
/*  303 */     this.securityMUValue = that.securityMUValue;
/*  304 */     this.encHeaderContent = that.encHeaderContent;
/*  305 */     this.issuedTokenContextMap = that.issuedTokenContextMap;
/*  306 */     this.secEnv = that.secEnv;
/*  307 */     this.isSOAP12 = that.isSOAP12;
/*  308 */     this.soapVersion = that.soapVersion;
/*  309 */     this.spVersion = that.spVersion;
/*  310 */     this.soapFactory = that.soapFactory;
/*  311 */     this.addVer = that.addVer;
/*  312 */     this.wsTrustVer = that.wsTrustVer;
/*  313 */     this.wsscVer = that.wsscVer;
/*  314 */     this.rmVer = that.rmVer;
/*  315 */     this.mcVer = that.mcVer;
/*  316 */     this.encRMLifecycleMsg = that.encRMLifecycleMsg;
/*  317 */     this.wsPolicyMap = that.wsPolicyMap;
/*  318 */     this.policyAlternatives = that.policyAlternatives;
/*  319 */     this.bindingLevelAlgSuite = that.bindingLevelAlgSuite;
/*  320 */     this.hasIssuedTokens = that.hasIssuedTokens;
/*  321 */     this.hasKerberosToken = that.hasKerberosToken;
/*  322 */     this.hasSecureConversation = that.hasSecureConversation;
/*  323 */     this.hasReliableMessaging = that.hasReliableMessaging;
/*  324 */     this.hasMakeConnection = that.hasMakeConnection;
/*      */     
/*  326 */     this.timestampTimeOut = that.timestampTimeOut;
/*  327 */     this.iterationsForPDK = that.iterationsForPDK;
/*  328 */     this.serverCert = that.serverCert;
/*  329 */     this.isCertValidityVerified = that.isCertValidityVerified;
/*  330 */     this.isCertValid = that.isCertValid;
/*  331 */     this.cancelMSP = that.cancelMSP;
/*  332 */     this.encryptCancelPayload = that.encryptCancelPayload;
/*      */     try {
/*  334 */       this.marshaller = WSTrustElementFactory.getContext(this.wsTrustVer).createMarshaller();
/*  335 */       this.unmarshaller = WSTrustElementFactory.getContext(this.wsTrustVer).createUnmarshaller();
/*  336 */     } catch (JAXBException ex) {
/*  337 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0001_PROBLEM_MAR_UNMAR(), ex);
/*  338 */       throw new RuntimeException("Problem creating JAXB Marshaller/Unmarshaller", ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected SOAPMessage secureOutboundMessage(SOAPMessage message, ProcessingContext ctx) {
/*      */     try {
/*  344 */       ctx.setSOAPMessage(message);
/*  345 */       SecurityAnnotator.secureMessage(ctx);
/*  346 */       return ctx.getSOAPMessage();
/*  347 */     } catch (WssSoapFaultException soapFaultException) {
/*  348 */       throw getSOAPFaultException(soapFaultException);
/*  349 */     } catch (XWSSecurityException xwse) {
/*  350 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)xwse);
/*      */       
/*  352 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/*  356 */       throw wsfe;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected RuntimeException generateInternalError(PolicyException ex) {
/*      */     SOAPFault fault;
/*      */     try {
/*  363 */       if (this.isSOAP12) {
/*  364 */         fault = this.soapFactory.createFault(ex.getMessage(), SOAPConstants.SOAP_SENDER_FAULT);
/*  365 */         fault.appendFaultSubcode(MessageConstants.WSSE_INTERNAL_SERVER_ERROR);
/*      */       } else {
/*  367 */         fault = this.soapFactory.createFault(ex.getMessage(), MessageConstants.WSSE_INTERNAL_SERVER_ERROR);
/*      */       } 
/*  369 */     } catch (Exception e) {
/*  370 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0002_INTERNAL_SERVER_ERROR(), e);
/*  371 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0002_INTERNAL_SERVER_ERROR(), e);
/*      */     } 
/*  373 */     return new SOAPFaultException(fault);
/*      */   }
/*      */   
/*      */   protected Message secureOutboundMessage(Message message, ProcessingContext ctx) {
/*      */     try {
/*  378 */       JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)ctx;
/*  379 */       context.setSOAPVersion(this.soapVersion);
/*  380 */       context.setAllowMissingTimestamp(this.allowMissingTimestamp);
/*  381 */       context.setMustUnderstandValue(this.securityMUValue);
/*  382 */       context.setWSSAssertion(((MessagePolicy)ctx.getSecurityPolicy()).getWSSAssertion());
/*  383 */       context.setJAXWSMessage(message, this.soapVersion);
/*  384 */       context.isOneWayMessage(message.isOneWay(this.tubeConfig.getWSDLPort()));
/*  385 */       context.setDisableIncPrefix(this.disableIncPrefix);
/*  386 */       context.setEncHeaderContent(this.encHeaderContent);
/*  387 */       context.setBSP(this.bsp10);
/*  388 */       SecurityAnnotator.secureMessage((ProcessingContext)context);
/*  389 */       return context.getJAXWSMessage();
/*  390 */     } catch (XWSSecurityException xwse) {
/*  391 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)xwse);
/*      */       
/*  393 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/*  397 */       throw wsfe;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPMessage verifyInboundMessage(SOAPMessage message, ProcessingContext ctx) throws WssSoapFaultException, XWSSecurityException {
/*      */     try {
/*  404 */       ctx.setSOAPMessage(message);
/*      */ 
/*      */ 
/*      */       
/*  408 */       NewSecurityRecipient.validateMessage(ctx);
/*  409 */       return ctx.getSOAPMessage();
/*  410 */     } catch (WssSoapFaultException soapFaultException) {
/*  411 */       throw getSOAPFaultException(soapFaultException);
/*  412 */     } catch (XWSSecurityException xwse) {
/*      */       
/*  414 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/*  418 */       throw getSOAPFaultException(wsfe);
/*      */     } 
/*      */   }
/*      */   protected Message verifyInboundMessage(Message message, ProcessingContext ctx) throws XWSSecurityException {
/*      */     SecurityRecipient recipient;
/*  423 */     JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)ctx;
/*  424 */     context.setDisablePayloadBuffering(this.disablePayloadBuffer);
/*  425 */     context.setDisableIncPrefix(this.disableIncPrefix);
/*  426 */     if ((MessagePolicy)ctx.getSecurityPolicy() != null) {
/*  427 */       context.setWSSAssertion(((MessagePolicy)ctx.getSecurityPolicy()).getWSSAssertion());
/*      */     }
/*  429 */     context.setAllowMissingTimestamp(this.allowMissingTimestamp);
/*  430 */     context.setMustUnderstandValue(this.securityMUValue);
/*  431 */     context.setEncHeaderContent(this.encHeaderContent);
/*  432 */     context.setBSP(this.bsp10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  442 */     LazyStreamBasedMessage lazyStreamMessage = (LazyStreamBasedMessage)message;
/*  443 */     AttachmentSet attachSet = null;
/*  444 */     if (!LazyStreamBasedMessage.mtomLargeData()) {
/*  445 */       attachSet = lazyStreamMessage.getAttachments();
/*      */     }
/*      */     
/*  448 */     if (attachSet == null || attachSet.isEmpty()) {
/*  449 */       recipient = new SecurityRecipient(lazyStreamMessage.readMessage(), this.soapVersion);
/*      */     } else {
/*      */       
/*  452 */       recipient = new SecurityRecipient(lazyStreamMessage.readMessage(), this.soapVersion, attachSet);
/*      */     } 
/*      */     
/*  455 */     return recipient.validateMessage(context);
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getIssuedTokenPoliciesFromBootstrapPolicy(Token scAssertion) {
/*  460 */     SCTokenWrapper token = (SCTokenWrapper)scAssertion;
/*  461 */     return token.getIssuedTokens();
/*      */   }
/*      */   
/*      */   protected List<PolicyAssertion> getKerberosTokenPoliciesFromBootstrapPolicy(Token scAssertion) {
/*  465 */     SCTokenWrapper token = (SCTokenWrapper)scAssertion;
/*  466 */     return token.getKerberosTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected MessagePolicy getOutgoingXWSSecurityPolicy(Packet packet, boolean isSCMessage) {
/*      */     WSDLBoundOperation operation;
/*  473 */     if (isSCMessage) {
/*  474 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/*  475 */       return getOutgoingXWSBootstrapPolicy(scToken);
/*      */     } 
/*  477 */     Message message = packet.getMessage();
/*      */     
/*  479 */     if (isTrustMessage(packet)) {
/*  480 */       operation = getWSDLOpFromAction(packet, false);
/*      */     } else {
/*  482 */       operation = message.getOperation(this.tubeConfig.getWSDLPort());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  487 */     MessagePolicy mp = null;
/*  488 */     PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  495 */     if (applicableAlternative.getOutMessagePolicyMap() == null)
/*      */     {
/*  497 */       return new MessagePolicy();
/*      */     }
/*  499 */     SecurityPolicyHolder sph = (SecurityPolicyHolder)applicableAlternative.getOutMessagePolicyMap().get(operation);
/*      */     
/*  501 */     if (sph == null) {
/*  502 */       return new MessagePolicy();
/*      */     }
/*  504 */     mp = sph.getMessagePolicy();
/*  505 */     return mp;
/*      */   }
/*      */   
/*      */   protected WSDLBoundOperation getOperation(Message message) {
/*  509 */     if (this.cachedOperation == null) {
/*  510 */       this.cachedOperation = message.getOperation(this.tubeConfig.getWSDLPort());
/*      */     }
/*  512 */     return this.cachedOperation;
/*      */   }
/*      */   
/*      */   protected MessagePolicy getInboundXWSBootstrapPolicy(Token scAssertion) {
/*  516 */     return ((SCTokenWrapper)scAssertion).getMessagePolicy();
/*      */   }
/*      */   
/*      */   protected MessagePolicy getOutgoingXWSBootstrapPolicy(Token scAssertion) {
/*  520 */     return ((SCTokenWrapper)scAssertion).getMessagePolicy();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ProcessingContext initializeInboundProcessingContext(Packet packet) {
/*      */     ProcessingContextImpl ctx;
/*  527 */     if (this.optimized) {
/*  528 */       JAXBFilterProcessingContext jAXBFilterProcessingContext = new JAXBFilterProcessingContext(packet.invocationProperties);
/*  529 */       jAXBFilterProcessingContext.setAddressingVersion(this.addVer);
/*  530 */       jAXBFilterProcessingContext.setSOAPVersion(this.soapVersion);
/*  531 */       jAXBFilterProcessingContext.setSecure(packet.wasTransportSecure);
/*  532 */       jAXBFilterProcessingContext.setBSP(this.bsp10);
/*      */     } else {
/*      */       
/*  535 */       ctx = new ProcessingContextImpl(packet.invocationProperties);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  541 */     ctx.setAddressingEnabled(isAddressingEnabled());
/*  542 */     ctx.setWsscVer(this.wsscVer);
/*      */     
/*  544 */     String action = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  552 */     ctx.setSecurityPolicyVersion(this.spVersion.namespaceUri);
/*      */     
/*  554 */     ctx.setiterationsForPDK(this.iterationsForPDK);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  562 */     ctx.setBootstrapAlgoSuite(getAlgoSuite(this.bootStrapAlgoSuite));
/*  563 */     ctx.setAlgorithmSuite(getAlgoSuite(getBindingAlgorithmSuite(packet)));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  568 */     if (this.serverCert != null) {
/*  569 */       if (!this.isCertValidityVerified) {
/*  570 */         CertificateRetriever cr = new CertificateRetriever();
/*  571 */         this.isCertValid = cr.setServerCertInTheContext(ctx, this.secEnv, this.serverCert);
/*  572 */         cr = null;
/*  573 */         this.isCertValidityVerified = true;
/*      */       }
/*  575 */       else if (this.isCertValid == true) {
/*  576 */         ctx.getExtraneousProperties().put("server-certificate", this.serverCert);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  581 */     ctx.hasIssuedToken(bindingHasIssuedTokenPolicy());
/*  582 */     ctx.setSecurityEnvironment(this.secEnv);
/*  583 */     ctx.isInboundMessage(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     ctx.setWsTrustVer(this.wsTrustVer);
/*      */     
/*  590 */     if (this.tubeConfig.getWSDLPort() != null) {
/*  591 */       ctx.getExtraneousProperties().put("WSDLPort", this.tubeConfig.getWSDLPort());
/*      */     }
/*  593 */     if (this.tubeConfig instanceof ServerTubeConfiguration) {
/*  594 */       ctx.getExtraneousProperties().put("WSEndpoint", ((ServerTubeConfiguration)this.tubeConfig).getEndpoint());
/*      */     }
/*  596 */     return (ProcessingContext)ctx;
/*      */   }
/*      */   
/*      */   protected boolean bindingHasIssuedTokenPolicy() {
/*  600 */     return this.hasIssuedTokens;
/*      */   }
/*      */   
/*      */   protected boolean bindingHasSecureConversationPolicy() {
/*  604 */     return this.hasSecureConversation;
/*      */   }
/*      */   
/*      */   protected boolean bindingHasRMPolicy() {
/*  608 */     return this.hasReliableMessaging;
/*      */   }
/*      */   
/*      */   protected boolean hasKerberosTokenPolicy() {
/*  612 */     return this.hasKerberosToken;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ProcessingContext initializeOutgoingProcessingContext(Packet packet, boolean isSCMessage) {
/*      */     ProcessingContextImpl ctx;
/*  619 */     if (this.optimized) {
/*  620 */       JAXBFilterProcessingContext jAXBFilterProcessingContext = new JAXBFilterProcessingContext(packet.invocationProperties);
/*  621 */       jAXBFilterProcessingContext.setAddressingVersion(this.addVer);
/*  622 */       jAXBFilterProcessingContext.setSOAPVersion(this.soapVersion);
/*  623 */       jAXBFilterProcessingContext.setBSP(this.bsp10);
/*      */     } else {
/*  625 */       ctx = new ProcessingContextImpl(packet.invocationProperties);
/*      */     } 
/*  627 */     if (this.addVer != null) {
/*  628 */       ctx.setAction(getAction(packet));
/*      */     }
/*      */     
/*  631 */     ctx.setSecurityPolicyVersion(this.spVersion.namespaceUri);
/*  632 */     ctx.setTimestampTimeout(this.timestampTimeOut);
/*  633 */     ctx.setiterationsForPDK(this.iterationsForPDK);
/*      */ 
/*      */     
/*  636 */     ctx.setAlgorithmSuite(getAlgoSuite(getBindingAlgorithmSuite(packet)));
/*      */     
/*  638 */     if (this.serverCert != null) {
/*  639 */       if (!this.isCertValidityVerified) {
/*  640 */         CertificateRetriever cr = new CertificateRetriever();
/*  641 */         this.isCertValid = cr.setServerCertInTheContext(ctx, this.secEnv, this.serverCert);
/*  642 */         cr = null;
/*  643 */         this.isCertValidityVerified = true;
/*      */       }
/*  645 */       else if (this.isCertValid == true) {
/*  646 */         ctx.getExtraneousProperties().put("server-certificate", this.serverCert);
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/*  651 */       PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*      */       
/*  653 */       MessagePolicy policy = null;
/*  654 */       if (isRMMessage(packet) || isMakeConnectionMessage(packet)) {
/*  655 */         SecurityPolicyHolder holder = (SecurityPolicyHolder)applicableAlternative.getOutProtocolPM().get("RM");
/*  656 */         policy = holder.getMessagePolicy();
/*  657 */       } else if (isSCCancel(packet)) {
/*  658 */         SecurityPolicyHolder holder = (SecurityPolicyHolder)applicableAlternative.getOutProtocolPM().get("SC-CANCEL");
/*  659 */         policy = holder.getMessagePolicy();
/*  660 */       } else if (isSCRenew(packet)) {
/*  661 */         policy = getOutgoingXWSSecurityPolicy(packet, isSCMessage);
/*  662 */         ctx.isExpired(true);
/*      */       } else {
/*  664 */         policy = getOutgoingXWSSecurityPolicy(packet, isSCMessage);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  669 */       if (policy.getAlgorithmSuite() != null)
/*      */       {
/*  671 */         ctx.setAlgorithmSuite(policy.getAlgorithmSuite());
/*      */       }
/*  673 */       ctx.setWSSAssertion(policy.getWSSAssertion());
/*  674 */       ctx.setSecurityPolicy((SecurityPolicy)policy);
/*  675 */       ctx.setSecurityEnvironment(this.secEnv);
/*  676 */       ctx.isInboundMessage(false);
/*  677 */     } catch (XWSSecurityException e) {
/*  678 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), (Throwable)e);
/*  679 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), e);
/*      */     } 
/*  681 */     return (ProcessingContext)ctx;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPFault getSOAPFault(WssSoapFaultException sfe) {
/*      */     SOAPFault fault;
/*      */     try {
/*  688 */       if (this.isSOAP12) {
/*  689 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/*  690 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*      */       } else {
/*  692 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*      */       } 
/*  694 */     } catch (Exception e) {
/*  695 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0002_INTERNAL_SERVER_ERROR());
/*  696 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0002_INTERNAL_SERVER_ERROR(), e);
/*      */     } 
/*  698 */     return fault;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPFaultException getSOAPFaultException(WssSoapFaultException sfe) {
/*      */     SOAPFault fault;
/*      */     try {
/*  705 */       if (this.isSOAP12) {
/*  706 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/*  707 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*      */       } else {
/*  709 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*      */       } 
/*  711 */     } catch (SOAPException sOAPException) {
/*  712 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0002_INTERNAL_SERVER_ERROR());
/*  713 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0002_INTERNAL_SERVER_ERROR(), sOAPException);
/*      */     } 
/*  715 */     SOAPFaultException e = new SOAPFaultException(fault);
/*  716 */     e.initCause((Throwable)sfe);
/*  717 */     return e;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPFaultException getSOAPFaultException(XWSSecurityException xwse) {
/*      */     QName qname;
/*  723 */     if (xwse.getCause() instanceof com.sun.xml.wss.impl.PolicyViolationException) {
/*  724 */       qname = MessageConstants.WSSE_RECEIVER_POLICY_VIOLATION;
/*      */     } else {
/*  726 */       qname = MessageConstants.WSSE_FAILED_AUTHENTICATION;
/*      */     } 
/*      */     
/*  729 */     WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(qname, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */     
/*  733 */     return getSOAPFaultException(wsfe);
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
/*      */   protected void collectPolicies(List<PolicyAlternativeHolder> alternatives) {
/*      */     try {
/*  750 */       if (this.wsPolicyMap == null) {
/*      */         return;
/*      */       }
/*      */       
/*  754 */       QName serviceName = this.tubeConfig.getWSDLPort().getOwner().getName();
/*  755 */       QName portName = this.tubeConfig.getWSDLPort().getName();
/*      */ 
/*      */       
/*  758 */       PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/*      */ 
/*      */       
/*  761 */       Policy endpointPolicy = this.wsPolicyMap.getEndpointEffectivePolicy(endpointKey);
/*      */ 
/*      */       
/*  764 */       setPolicyCredentials(endpointPolicy);
/*      */ 
/*      */       
/*  767 */       for (WSDLBoundOperation operation : this.tubeConfig.getWSDLPort().getBinding().getBindingOperations()) {
/*  768 */         QName operationName = new QName(operation.getBoundPortType().getName().getNamespaceURI(), operation.getName().getLocalPart());
/*  769 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, operationName);
/*  770 */         Policy operationPolicy = this.wsPolicyMap.getOperationEffectivePolicy(operationKey);
/*  771 */         setPolicyCredentials(operationPolicy);
/*      */       } 
/*      */       
/*  774 */       if (endpointPolicy == null) {
/*  775 */         ArrayList<Policy> policyList = new ArrayList<Policy>();
/*  776 */         PolicyAlternativeHolder ph = new PolicyAlternativeHolder(null, this.spVersion, this.bpMSP);
/*  777 */         alternatives.add(ph);
/*  778 */         collectOperationAndMessageLevelPolicies(this.wsPolicyMap, null, policyList, ph);
/*      */         return;
/*      */       } 
/*  781 */       Iterator<AssertionSet> policiesIter = endpointPolicy.iterator();
/*  782 */       while (policiesIter.hasNext()) {
/*  783 */         ArrayList<Policy> policyList = new ArrayList<Policy>();
/*  784 */         AssertionSet ass = policiesIter.next();
/*  785 */         PolicyAlternativeHolder ph = new PolicyAlternativeHolder(ass, this.spVersion, this.bpMSP);
/*  786 */         alternatives.add(ph);
/*      */         
/*  788 */         Collection<AssertionSet> coll = new ArrayList<AssertionSet>();
/*  789 */         coll.add(ass);
/*  790 */         Policy singleAlternative = Policy.createPolicy(endpointPolicy.getNamespaceVersion(), endpointPolicy.getName(), endpointPolicy.getId(), coll);
/*      */ 
/*      */         
/*  793 */         buildProtocolPolicy(singleAlternative, ph);
/*      */         
/*  795 */         policyList.add(singleAlternative);
/*      */         
/*  797 */         collectOperationAndMessageLevelPolicies(this.wsPolicyMap, singleAlternative, policyList, ph);
/*      */       } 
/*  799 */     } catch (PolicyException pe) {
/*  800 */       throw generateInternalError(pe);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void collectOperationAndMessageLevelPolicies(PolicyMap wsPolicyMap, Policy singleAlternative, ArrayList<Policy> policyList, PolicyAlternativeHolder ph) {
/*  807 */     if (wsPolicyMap == null) {
/*      */       return;
/*      */     }
/*      */     try {
/*  811 */       QName serviceName = this.tubeConfig.getWSDLPort().getOwner().getName();
/*  812 */       QName portName = this.tubeConfig.getWSDLPort().getName();
/*      */       
/*  814 */       PolicyMerger policyMerge = PolicyMerger.getMerger();
/*  815 */       for (WSDLBoundOperation operation : this.tubeConfig.getWSDLPort().getBinding().getBindingOperations()) {
/*  816 */         QName operationName = new QName(operation.getBoundPortType().getName().getNamespaceURI(), operation.getName().getLocalPart());
/*      */ 
/*      */         
/*  819 */         PolicyMapKey messageKey = PolicyMap.createWsdlMessageScopeKey(serviceName, portName, operationName);
/*      */         
/*  821 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, operationName);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  826 */         Policy operationPolicy = wsPolicyMap.getOperationEffectivePolicy(operationKey);
/*  827 */         if (operationPolicy != null) {
/*  828 */           policyList.add(operationPolicy);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  834 */         Policy imPolicy = null;
/*  835 */         imPolicy = wsPolicyMap.getInputMessageEffectivePolicy(messageKey);
/*  836 */         if (imPolicy != null) {
/*  837 */           policyList.add(imPolicy);
/*      */         }
/*      */ 
/*      */         
/*  841 */         Policy imEP = policyMerge.merge(policyList);
/*  842 */         SecurityPolicyHolder outPH = null;
/*  843 */         if (imEP != null) {
/*  844 */           outPH = addOutgoingMP(operation, imEP, ph);
/*      */         }
/*      */         
/*  847 */         if (imPolicy != null) {
/*  848 */           policyList.remove(imPolicy);
/*      */         }
/*      */         
/*  851 */         SecurityPolicyHolder inPH = null;
/*      */         
/*  853 */         Policy omPolicy = null;
/*  854 */         omPolicy = wsPolicyMap.getOutputMessageEffectivePolicy(messageKey);
/*  855 */         if (omPolicy != null) {
/*  856 */           policyList.add(omPolicy);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  861 */         Policy omEP = policyMerge.merge(policyList);
/*  862 */         if (omPolicy != null) {
/*  863 */           policyList.remove(omPolicy);
/*      */         }
/*  865 */         if (omEP != null) {
/*  866 */           inPH = addIncomingMP(operation, omEP, ph);
/*      */         }
/*      */         
/*  869 */         Iterator<WSDLFault> faults = operation.getOperation().getFaults().iterator();
/*  870 */         ArrayList<Policy> faultPL = new ArrayList<Policy>();
/*  871 */         if (singleAlternative != null) {
/*  872 */           faultPL.add(singleAlternative);
/*      */         }
/*  874 */         if (operationPolicy != null) {
/*  875 */           faultPL.add(operationPolicy);
/*      */         }
/*  877 */         while (faults.hasNext()) {
/*  878 */           WSDLFault fault = faults.next();
/*  879 */           PolicyMapKey fKey = null;
/*  880 */           fKey = PolicyMap.createWsdlFaultMessageScopeKey(serviceName, portName, operationName, new QName(operationName.getNamespaceURI(), fault.getName()));
/*      */ 
/*      */           
/*  883 */           Policy fPolicy = wsPolicyMap.getFaultMessageEffectivePolicy(fKey);
/*      */           
/*  885 */           if (fPolicy != null) {
/*  886 */             faultPL.add(fPolicy);
/*      */           }
/*      */ 
/*      */           
/*  890 */           Policy ep = policyMerge.merge(faultPL);
/*  891 */           if (inPH != null) {
/*  892 */             addIncomingFaultPolicy(ep, inPH, fault);
/*      */           }
/*  894 */           if (outPH != null) {
/*  895 */             addOutgoingFaultPolicy(ep, outPH, fault);
/*      */           }
/*  897 */           faultPL.remove(fPolicy);
/*      */         } 
/*  899 */         if (operationPolicy != null) {
/*  900 */           policyList.remove(operationPolicy);
/*      */         }
/*      */       } 
/*  903 */     } catch (PolicyException pe) {
/*  904 */       throw generateInternalError(pe);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getInBoundSCP(Message message) {
/*  910 */     SecurityPolicyHolder sph = null;
/*      */     
/*  912 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  913 */       if (p.getInMessagePolicyMap() == null) {
/*  914 */         return Collections.emptyList();
/*      */       }
/*      */       
/*  917 */       Collection coll = p.getInMessagePolicyMap().values();
/*  918 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  920 */       while (itr.hasNext()) {
/*  921 */         SecurityPolicyHolder ph = itr.next();
/*  922 */         if (ph != null) {
/*  923 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  927 */       if (sph == null) {
/*  928 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  931 */     return sph.getSecureConversationTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getOutBoundSCP(Message message) {
/*  938 */     SecurityPolicyHolder sph = null;
/*      */     
/*  940 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  941 */       if (p.getOutMessagePolicyMap() == null) {
/*  942 */         return Collections.emptyList();
/*      */       }
/*      */       
/*  945 */       Collection coll = p.getOutMessagePolicyMap().values();
/*  946 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  948 */       while (itr.hasNext()) {
/*  949 */         SecurityPolicyHolder ph = itr.next();
/*  950 */         if (ph != null) {
/*  951 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  955 */       if (sph == null) {
/*  956 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  959 */     return sph.getSecureConversationTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getOutBoundKTP(Packet packet, boolean isSCMessage) {
/*  965 */     if (isSCMessage) {
/*  966 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/*  967 */       return ((SCTokenWrapper)scToken).getKerberosTokens();
/*      */     } 
/*  969 */     SecurityPolicyHolder sph = null;
/*      */     
/*  971 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  972 */       if (p.getOutMessagePolicyMap() == null) {
/*  973 */         return Collections.emptyList();
/*      */       }
/*  975 */       Message message = packet.getMessage();
/*      */       
/*  977 */       Collection coll = p.getOutMessagePolicyMap().values();
/*  978 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/*  980 */       while (itr.hasNext()) {
/*  981 */         SecurityPolicyHolder ph = itr.next();
/*  982 */         if (ph != null) {
/*  983 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/*  987 */       if (sph == null) {
/*  988 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/*  991 */     return sph.getKerberosTokens();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getSecureConversationPolicies(Message message, String scope) {
/*  997 */     SecurityPolicyHolder sph = null;
/*      */     
/*  999 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 1000 */       if (p.getOutMessagePolicyMap() == null) {
/* 1001 */         return Collections.emptyList();
/*      */       }
/*      */       
/* 1004 */       Collection coll = p.getOutMessagePolicyMap().values();
/* 1005 */       Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*      */       
/* 1007 */       while (itr.hasNext()) {
/* 1008 */         SecurityPolicyHolder ph = itr.next();
/* 1009 */         if (ph != null) {
/* 1010 */           sph = ph;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1014 */       if (sph == null) {
/* 1015 */         return EMPTY_LIST;
/*      */       }
/*      */     } 
/* 1018 */     return sph.getSecureConversationTokens();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ArrayList<PolicyAssertion> getTokens(Policy policy) {
/* 1023 */     ArrayList<PolicyAssertion> tokenList = new ArrayList<PolicyAssertion>();
/* 1024 */     for (AssertionSet assertionSet : policy) {
/* 1025 */       for (PolicyAssertion assertion : assertionSet) {
/* 1026 */         if (PolicyUtil.isAsymmetricBinding(assertion, this.spVersion)) {
/* 1027 */           AsymmetricBinding sb = (AsymmetricBinding)assertion;
/* 1028 */           Token iToken = sb.getInitiatorToken();
/* 1029 */           if (iToken != null) {
/* 1030 */             addToken(iToken, tokenList);
/*      */           } else {
/* 1032 */             addToken(sb.getInitiatorSignatureToken(), tokenList);
/* 1033 */             addToken(sb.getInitiatorEncryptionToken(), tokenList);
/*      */           } 
/*      */           
/* 1036 */           Token rToken = sb.getRecipientToken();
/* 1037 */           if (rToken != null) {
/* 1038 */             addToken(rToken, tokenList); continue;
/*      */           } 
/* 1040 */           addToken(sb.getRecipientSignatureToken(), tokenList);
/* 1041 */           addToken(sb.getRecipientEncryptionToken(), tokenList); continue;
/*      */         } 
/* 1043 */         if (PolicyUtil.isSymmetricBinding(assertion, this.spVersion)) {
/* 1044 */           SymmetricBinding sb = (SymmetricBinding)assertion;
/* 1045 */           Token token = sb.getProtectionToken();
/* 1046 */           if (token != null) {
/* 1047 */             addToken(token, tokenList); continue;
/*      */           } 
/* 1049 */           addToken(sb.getEncryptionToken(), tokenList);
/* 1050 */           addToken(sb.getSignatureToken(), tokenList); continue;
/*      */         } 
/* 1052 */         if (PolicyUtil.isSupportingTokens(assertion, this.spVersion)) {
/* 1053 */           SupportingTokens st = (SupportingTokens)assertion;
/* 1054 */           Iterator<Token> itr = st.getTokens();
/* 1055 */           while (itr.hasNext()) {
/* 1056 */             addToken(itr.next(), tokenList);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1061 */     return tokenList;
/*      */   }
/*      */ 
/*      */   
/*      */   private void addConfigAssertions(Policy policy, SecurityPolicyHolder sph) {
/* 1066 */     for (AssertionSet assertionSet : policy) {
/* 1067 */       for (PolicyAssertion assertion : assertionSet) {
/* 1068 */         if (PolicyUtil.isConfigPolicyAssertion(assertion)) {
/* 1069 */           sph.addConfigAssertions(assertion);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addToken(Token token, ArrayList<PolicyAssertion> list) {
/* 1076 */     if (token == null) {
/*      */       return;
/*      */     }
/* 1079 */     if (PolicyUtil.isSecureConversationToken((PolicyAssertion)token, this.spVersion) || PolicyUtil.isIssuedToken((PolicyAssertion)token, this.spVersion) || PolicyUtil.isKerberosToken((PolicyAssertion)token, this.spVersion))
/*      */     {
/*      */       
/* 1082 */       list.add((PolicyAssertion)token);
/*      */     }
/*      */   }
/*      */   
/*      */   protected PolicyMapKey getOperationKey(Message message) {
/* 1087 */     WSDLBoundOperation operation = message.getOperation(this.tubeConfig.getWSDLPort());
/* 1088 */     WSDLOperation wsdlOperation = operation.getOperation();
/* 1089 */     QName serviceName = this.tubeConfig.getWSDLPort().getOwner().getName();
/* 1090 */     QName portName = this.tubeConfig.getWSDLPort().getName();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1095 */     PolicyMapKey messageKey = PolicyMap.createWsdlMessageScopeKey(serviceName, portName, wsdlOperation.getName());
/*      */     
/* 1097 */     return messageKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AlgorithmSuite getBindingAlgorithmSuite(Packet packet) {
/* 1106 */     return this.bindingLevelAlgSuite;
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
/* 1126 */     AssertionSet as = policy.getAssertionSet();
/*      */     
/* 1128 */     boolean foundTargets = false;
/* 1129 */     for (PolicyAssertion assertion : as) {
/* 1130 */       if (PolicyUtil.isSignedParts(assertion, this.spVersion) || PolicyUtil.isEncryptParts(assertion, this.spVersion)) {
/* 1131 */         foundTargets = true;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1135 */     return foundTargets;
/*      */   }
/*      */   
/*      */   protected Policy getEffectiveBootstrapPolicy(NestedPolicy bp) throws PolicyException {
/*      */     try {
/* 1140 */       ArrayList<Policy> pl = new ArrayList<Policy>();
/* 1141 */       pl.add(bp);
/* 1142 */       Policy mbp = getMessageBootstrapPolicy();
/* 1143 */       if (mbp != null) {
/* 1144 */         pl.add(mbp);
/*      */       }
/*      */       
/* 1147 */       PolicyMerger pm = PolicyMerger.getMerger();
/* 1148 */       Policy ep = pm.merge(pl);
/* 1149 */       return ep;
/* 1150 */     } catch (Exception e) {
/* 1151 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0007_PROBLEM_GETTING_EFF_BOOT_POLICY(), e);
/* 1152 */       throw new PolicyException(LogStringsMessages.WSSTUBE_0007_PROBLEM_GETTING_EFF_BOOT_POLICY(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Policy getMessageBootstrapPolicy() throws PolicyException, IOException {
/* 1158 */     if (this.bpMSP == null) {
/* 1159 */       String bootstrapMessagePolicy = "boot-msglevel-policy.xml";
/* 1160 */       if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri)) {
/* 1161 */         bootstrapMessagePolicy = "boot-msglevel-policy-sx.xml";
/*      */       }
/* 1163 */       PolicySourceModel model = unmarshalPolicy("com/sun/xml/ws/security/impl/policyconv/" + bootstrapMessagePolicy);
/*      */       
/* 1165 */       this.bpMSP = ModelTranslator.getTranslator().translate(model);
/*      */     } 
/* 1167 */     return this.bpMSP;
/*      */   }
/*      */   
/*      */   private Policy getMessageLevelBSP() throws PolicyException {
/* 1171 */     QName serviceName = this.tubeConfig.getWSDLPort().getOwner().getName();
/* 1172 */     QName portName = this.tubeConfig.getWSDLPort().getName();
/* 1173 */     PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, Constants.bsOperationName);
/*      */     
/* 1175 */     Policy operationLevelEP = this.wsPolicyMap.getOperationEffectivePolicy(operationKey);
/* 1176 */     return operationLevelEP;
/*      */   }
/*      */   
/*      */   protected PolicySourceModel unmarshalPolicy(String resource) throws PolicyException, IOException {
/* 1180 */     InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
/* 1181 */     if (is == null) {
/* 1182 */       return null;
/*      */     }
/* 1184 */     Reader reader = new InputStreamReader(is);
/* 1185 */     PolicySourceModel model = ModelUnmarshaller.getUnmarshaller().unmarshalModel(reader);
/* 1186 */     reader.close();
/* 1187 */     return model;
/*      */   }
/*      */   
/*      */   protected final void cacheOperation(Message msg) {
/* 1191 */     this.cachedOperation = msg.getOperation(this.tubeConfig.getWSDLPort());
/*      */   }
/*      */   
/*      */   protected final void resetCachedOperation() {
/* 1195 */     this.cachedOperation = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isSCMessage(Packet packet) {
/* 1200 */     if (!bindingHasSecureConversationPolicy()) {
/* 1201 */       return false;
/*      */     }
/*      */     
/* 1204 */     if (!isAddressingEnabled()) {
/* 1205 */       return false;
/*      */     }
/*      */     
/* 1208 */     String action = getAction(packet);
/* 1209 */     return (this.wsscVer.getSCTRequestAction().equals(action) || this.wsscVer.getSCTRenewRequestAction().equals(action));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSCCancel(Packet packet) {
/* 1215 */     if (!bindingHasSecureConversationPolicy()) {
/* 1216 */       return false;
/*      */     }
/*      */     
/* 1219 */     if (!isAddressingEnabled()) {
/* 1220 */       return false;
/*      */     }
/*      */     
/* 1223 */     String action = getAction(packet);
/* 1224 */     return (this.wsscVer.getSCTCancelResponseAction().equals(action) || this.wsscVer.getSCTCancelRequestAction().equals(action));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSCRenew(Packet packet) {
/* 1230 */     if (!bindingHasSecureConversationPolicy()) {
/* 1231 */       return false;
/*      */     }
/*      */     
/* 1234 */     if (!isAddressingEnabled()) {
/* 1235 */       return false;
/*      */     }
/*      */     
/* 1238 */     String action = getAction(packet);
/* 1239 */     return (this.wsscVer.getSCTRenewResponseAction().equals(action) || this.wsscVer.getSCTRenewRequestAction().equals(action));
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isAddressingEnabled() {
/* 1244 */     return (this.addVer != null);
/*      */   }
/*      */   
/*      */   protected boolean isTrustMessage(Packet packet) {
/* 1248 */     if (!isAddressingEnabled()) {
/* 1249 */       return false;
/*      */     }
/* 1251 */     String action = getAction(packet);
/*      */ 
/*      */     
/* 1254 */     if (this.wsTrustVer.getIssueRequestAction().equals(action) || this.wsTrustVer.getIssueFinalResoponseAction().equals(action))
/*      */     {
/* 1256 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1260 */     return (this.wsTrustVer.getValidateRequestAction().equals(action) || this.wsTrustVer.getValidateFinalResoponseAction().equals(action));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRMMessage(Packet packet) {
/* 1266 */     if (!isAddressingEnabled()) {
/* 1267 */       return false;
/*      */     }
/* 1269 */     if (!bindingHasRMPolicy()) {
/* 1270 */       return false;
/*      */     }
/*      */     
/* 1273 */     return this.rmVer.isProtocolAction(getAction(packet));
/*      */   }
/*      */   
/*      */   protected boolean isMakeConnectionMessage(Packet packet) {
/* 1277 */     if (!this.hasMakeConnection) {
/* 1278 */       return false;
/*      */     }
/* 1280 */     return this.mcVer.isProtocolAction(getAction(packet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getAction(Packet packet) {
/* 1288 */     HeaderList hl = packet.getMessage().getHeaders();
/*      */     
/* 1290 */     String action = hl.getAction(this.addVer, this.tubeConfig.getBinding().getSOAPVersion());
/* 1291 */     return action;
/*      */   }
/*      */   
/*      */   protected WSDLBoundOperation getWSDLOpFromAction(Packet packet, boolean isIncomming) {
/* 1295 */     String uriValue = getAction(packet);
/* 1296 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 1297 */       Set<WSDLBoundOperation> keys = p.getOutMessagePolicyMap().keySet();
/* 1298 */       for (WSDLBoundOperation wbo : keys) {
/* 1299 */         WSDLOperation wo = wbo.getOperation();
/*      */         
/* 1301 */         String action = getAction(wo, isIncomming);
/* 1302 */         if (action != null && action.equals(uriValue)) {
/* 1303 */           return wbo;
/*      */         }
/*      */       } 
/*      */     } 
/* 1307 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected WSDLBoundOperation getWSDLOpFromAction(Packet packet, boolean isIncomming, boolean isFault) {
/* 1312 */     String uriValue = getAction(packet);
/* 1313 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 1314 */       Set<WSDLBoundOperation> keys = p.getOutMessagePolicyMap().keySet();
/* 1315 */       for (WSDLBoundOperation wbo : keys) {
/* 1316 */         WSDLOperation wo = wbo.getOperation();
/*      */         
/* 1318 */         String action = getAction(wo, isIncomming);
/* 1319 */         if (isFault) {
/* 1320 */           if (action != null)
/* 1321 */             return wbo; 
/*      */           continue;
/*      */         } 
/* 1324 */         if (action != null && action.equals(uriValue)) {
/* 1325 */           return wbo;
/*      */         }
/*      */       } 
/*      */     } 
/* 1329 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void buildProtocolPolicy(Policy endpointPolicy, PolicyAlternativeHolder ph) throws PolicyException {
/* 1334 */     if (endpointPolicy == null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1338 */       RMPolicyResolver rr = new RMPolicyResolver(this.spVersion, this.rmVer, this.mcVer, this.encRMLifecycleMsg);
/* 1339 */       Policy msgLevelPolicy = rr.getOperationLevelPolicy();
/* 1340 */       PolicyMerger merger = PolicyMerger.getMerger();
/* 1341 */       ArrayList<Policy> pList = new ArrayList<Policy>(2);
/* 1342 */       pList.add(endpointPolicy);
/* 1343 */       pList.add(msgLevelPolicy);
/* 1344 */       Policy effectivePolicy = merger.merge(pList);
/* 1345 */       addIncomingProtocolPolicy(effectivePolicy, "RM", ph);
/* 1346 */       addOutgoingProtocolPolicy(effectivePolicy, "RM", ph);
/*      */       
/* 1348 */       pList.remove(msgLevelPolicy);
/* 1349 */       pList.add(getMessageBootstrapPolicy());
/* 1350 */       PolicyMerger pm = PolicyMerger.getMerger();
/*      */       
/* 1352 */       Policy ep = pm.merge(pList);
/* 1353 */       addIncomingProtocolPolicy(ep, "SC", ph);
/* 1354 */       addOutgoingProtocolPolicy(ep, "SC", ph);
/* 1355 */       ArrayList<Policy> pList1 = new ArrayList<Policy>(2);
/* 1356 */       pList1.add(endpointPolicy);
/* 1357 */       pList1.add(getSCCancelPolicy(this.encryptCancelPayload));
/* 1358 */       PolicyMerger pm1 = PolicyMerger.getMerger();
/*      */       
/* 1360 */       Policy ep1 = pm1.merge(pList1);
/* 1361 */       addIncomingProtocolPolicy(ep1, "SC-CANCEL", ph);
/* 1362 */       addOutgoingProtocolPolicy(ep1, "SC-CANCEL", ph);
/* 1363 */     } catch (IOException ie) {
/* 1364 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0008_PROBLEM_BUILDING_PROTOCOL_POLICY(), ie);
/*      */       
/* 1366 */       throw new PolicyException(LogStringsMessages.WSSTUBE_0008_PROBLEM_BUILDING_PROTOCOL_POLICY(), ie);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SecurityPolicyHolder constructPolicyHolder(Policy effectivePolicy, boolean isServer, boolean isIncoming) throws PolicyException {
/* 1373 */     return constructPolicyHolder(effectivePolicy, isServer, isIncoming, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SecurityPolicyHolder constructPolicyHolder(Policy effectivePolicy, boolean isServer, boolean isIncoming, boolean ignoreST) throws PolicyException {
/* 1379 */     XWSSPolicyGenerator xwssPolicyGenerator = new XWSSPolicyGenerator(effectivePolicy, isServer, isIncoming, this.spVersion);
/* 1380 */     xwssPolicyGenerator.process(ignoreST);
/* 1381 */     this.bindingLevelAlgSuite = xwssPolicyGenerator.getBindingLevelAlgSuite();
/* 1382 */     MessagePolicy messagePolicy = xwssPolicyGenerator.getXWSSPolicy();
/*      */     
/* 1384 */     SecurityPolicyHolder sph = new SecurityPolicyHolder();
/* 1385 */     sph.setMessagePolicy(messagePolicy);
/* 1386 */     sph.setBindingLevelAlgSuite(xwssPolicyGenerator.getBindingLevelAlgSuite());
/* 1387 */     sph.isIssuedTokenAsEncryptedSupportingToken(xwssPolicyGenerator.isIssuedTokenAsEncryptedSupportingToken());
/* 1388 */     List<PolicyAssertion> tokenList = getTokens(effectivePolicy);
/* 1389 */     addConfigAssertions(effectivePolicy, sph);
/*      */     
/* 1391 */     for (PolicyAssertion token : tokenList) {
/* 1392 */       if (PolicyUtil.isSecureConversationToken(token, this.spVersion)) {
/* 1393 */         Policy effectiveBP; NestedPolicy bootstrapPolicy = ((SecureConversationToken)token).getBootstrapPolicy();
/*      */         
/* 1395 */         if (hasTargets(bootstrapPolicy)) {
/* 1396 */           NestedPolicy nestedPolicy = bootstrapPolicy;
/*      */         } else {
/* 1398 */           effectiveBP = getEffectiveBootstrapPolicy(bootstrapPolicy);
/*      */         } 
/* 1400 */         xwssPolicyGenerator = new XWSSPolicyGenerator(effectiveBP, isServer, isIncoming, this.spVersion);
/* 1401 */         xwssPolicyGenerator.process(ignoreST);
/* 1402 */         MessagePolicy bmp = xwssPolicyGenerator.getXWSSPolicy();
/* 1403 */         this.bootStrapAlgoSuite = xwssPolicyGenerator.getBindingLevelAlgSuite();
/*      */         
/* 1405 */         if (isServer && isIncoming) {
/* 1406 */           EncryptionPolicy optionalPolicy = new EncryptionPolicy();
/*      */           
/* 1408 */           EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)optionalPolicy.getFeatureBinding();
/* 1409 */           optionalPolicy.newX509CertificateKeyBinding();
/* 1410 */           EncryptionTarget target = new EncryptionTarget();
/* 1411 */           target.setQName(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion"));
/* 1412 */           target.setEnforce(false);
/* 1413 */           fb.addTargetBinding(target);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1422 */         SCTokenWrapper sCTokenWrapper = new SCTokenWrapper(token, bmp);
/* 1423 */         sph.addSecureConversationToken((PolicyAssertion)sCTokenWrapper);
/* 1424 */         this.hasSecureConversation = true;
/*      */ 
/*      */         
/* 1427 */         List<PolicyAssertion> iList = getIssuedTokenPoliciesFromBootstrapPolicy((Token)sCTokenWrapper);
/*      */         
/* 1429 */         if (!iList.isEmpty()) {
/* 1430 */           this.hasIssuedTokens = true;
/*      */         }
/*      */ 
/*      */         
/* 1434 */         List<PolicyAssertion> kList = getKerberosTokenPoliciesFromBootstrapPolicy((Token)sCTokenWrapper);
/*      */         
/* 1436 */         if (!kList.isEmpty())
/* 1437 */           this.hasKerberosToken = true; 
/*      */         continue;
/*      */       } 
/* 1440 */       if (PolicyUtil.isIssuedToken(token, this.spVersion)) {
/* 1441 */         sph.addIssuedToken(token);
/* 1442 */         this.hasIssuedTokens = true; continue;
/* 1443 */       }  if (PolicyUtil.isKerberosToken(token, this.spVersion)) {
/* 1444 */         sph.addKerberosToken(token);
/* 1445 */         this.hasKerberosToken = true;
/*      */       } 
/*      */     } 
/* 1448 */     return sph;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String populateConfigProperties(Set<PolicyAssertion> configAssertions, Properties props) {
/* 1454 */     if (configAssertions == null) {
/* 1455 */       return null;
/*      */     }
/* 1457 */     for (PolicyAssertion as : configAssertions) {
/* 1458 */       if ("KeyStore".equals(as.getName().getLocalPart())) {
/* 1459 */         populateKeystoreProps(props, (KeyStore)as); continue;
/* 1460 */       }  if ("TrustStore".equals(as.getName().getLocalPart())) {
/* 1461 */         populateTruststoreProps(props, (TrustStore)as); continue;
/* 1462 */       }  if ("CallbackHandlerConfiguration".equals(as.getName().getLocalPart())) {
/* 1463 */         String ret = populateCallbackHandlerProps(props, (CallbackHandlerConfiguration)as);
/* 1464 */         if (ret != null)
/* 1465 */           return ret;  continue;
/*      */       } 
/* 1467 */       if ("ValidatorConfiguration".equals(as.getName().getLocalPart())) {
/* 1468 */         populateValidatorProps(props, (ValidatorConfiguration)as); continue;
/* 1469 */       }  if ("CertStore".equals(as.getName().getLocalPart())) {
/* 1470 */         populateCertStoreProps(props, (CertStoreConfig)as); continue;
/* 1471 */       }  if ("KerberosConfig".equals(as.getName().getLocalPart())) {
/* 1472 */         populateKerberosProps(props, (KerberosConfig)as); continue;
/* 1473 */       }  if ("SessionManagerStore".equals(as.getName().getLocalPart())) {
/* 1474 */         populateSessionMgrProps(props, (SessionManagerStore)as);
/*      */       }
/*      */     } 
/* 1477 */     return null;
/*      */   }
/*      */   private void populateSessionMgrProps(Properties props, SessionManagerStore smStore) {
/* 1480 */     if (smStore.getSessionTimeOut() != null) {
/* 1481 */       props.put("session-timeout", smStore.getSessionTimeOut());
/*      */     }
/* 1483 */     if (smStore.getSessionThreshold() != null) {
/* 1484 */       props.put("session-threshold", smStore.getSessionThreshold());
/*      */     }
/*      */   }
/*      */   
/*      */   private void populateKerberosProps(Properties props, KerberosConfig kerbConfig) {
/* 1489 */     if (kerbConfig.getLoginModule() != null) {
/* 1490 */       props.put("krb5.login.module", kerbConfig.getLoginModule());
/*      */     }
/* 1492 */     if (kerbConfig.getServicePrincipal() != null) {
/* 1493 */       props.put("krb5.service.principal", kerbConfig.getServicePrincipal());
/*      */     }
/* 1495 */     if (kerbConfig.getCredentialDelegation() != null) {
/* 1496 */       props.put("krb5.credential.delegation", kerbConfig.getCredentialDelegation());
/*      */     }
/*      */   }
/*      */   
/*      */   private void populateKeystoreProps(Properties props, KeyStore store) {
/* 1501 */     boolean foundLoginModule = false;
/* 1502 */     if (store.getKeyStoreLoginModuleConfigName() != null) {
/* 1503 */       props.put("jaas.loginmodule.for.keystore", store.getKeyStoreLoginModuleConfigName());
/* 1504 */       foundLoginModule = true;
/*      */     } 
/* 1506 */     if (store.getKeyStoreCallbackHandler() != null) {
/* 1507 */       props.put("keystore.callback.handler", store.getKeyStoreCallbackHandler());
/* 1508 */       if (store.getAlias() != null) {
/* 1509 */         props.put("my.alias", store.getAlias());
/*      */       }
/* 1511 */       if (store.getAliasSelectorClassName() != null) {
/* 1512 */         props.put("keystore.certselector", store.getAliasSelectorClassName());
/*      */       }
/*      */       return;
/*      */     } 
/* 1516 */     if (foundLoginModule) {
/*      */       return;
/*      */     }
/*      */     
/* 1520 */     if (store.getLocation() != null) {
/* 1521 */       props.put("keystore.url", store.getLocation());
/*      */     } else {
/*      */       
/* 1524 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0014_KEYSTORE_URL_NULL_CONFIG_ASSERTION());
/*      */       
/* 1526 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0014_KEYSTORE_URL_NULL_CONFIG_ASSERTION());
/*      */     } 
/*      */     
/* 1529 */     if (store.getType() != null) {
/* 1530 */       props.put("keystore.type", store.getType());
/*      */     } else {
/* 1532 */       props.put("keystore.type", "JKS");
/*      */     } 
/*      */     
/* 1535 */     if (store.getPassword() != null) {
/* 1536 */       props.put("keystore.password", new String(store.getPassword()));
/*      */     } else {
/* 1538 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0015_KEYSTORE_PASSWORD_NULL_CONFIG_ASSERTION());
/*      */       
/* 1540 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0015_KEYSTORE_PASSWORD_NULL_CONFIG_ASSERTION());
/*      */     } 
/*      */     
/* 1543 */     if (store.getAlias() != null) {
/* 1544 */       props.put("my.alias", store.getAlias());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1550 */     if (store.getKeyPassword() != null) {
/* 1551 */       props.put("key.password", store.getKeyPassword());
/*      */     }
/*      */     
/* 1554 */     if (store.getAliasSelectorClassName() != null) {
/* 1555 */       props.put("keystore.certselector", store.getAliasSelectorClassName());
/*      */     }
/*      */   }
/*      */   
/*      */   private void populateTruststoreProps(Properties props, TrustStore store) {
/* 1560 */     if (store.getTrustStoreCallbackHandler() != null) {
/* 1561 */       props.put("truststore.callback.handler", store.getTrustStoreCallbackHandler());
/* 1562 */       if (store.getPeerAlias() != null) {
/* 1563 */         props.put("peerentity.alias", store.getPeerAlias());
/*      */       }
/* 1565 */       if (store.getCertSelectorClassName() != null) {
/* 1566 */         props.put("truststore.certselector", store.getCertSelectorClassName());
/*      */       }
/*      */       return;
/*      */     } 
/* 1570 */     if (store.getLocation() != null) {
/* 1571 */       props.put("truststore.url", store.getLocation());
/*      */     } else {
/*      */       
/* 1574 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0016_TRUSTSTORE_URL_NULL_CONFIG_ASSERTION());
/*      */       
/* 1576 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0016_TRUSTSTORE_URL_NULL_CONFIG_ASSERTION());
/*      */     } 
/*      */     
/* 1579 */     if (store.getType() != null) {
/* 1580 */       props.put("truststore.type", store.getType());
/*      */     } else {
/* 1582 */       props.put("truststore.type", "JKS");
/*      */     } 
/*      */     
/* 1585 */     if (store.getPassword() != null) {
/* 1586 */       props.put("truststore.password", new String(store.getPassword()));
/*      */     } else {
/* 1588 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0017_TRUSTSTORE_PASSWORD_NULL_CONFIG_ASSERTION());
/*      */       
/* 1590 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0017_TRUSTSTORE_PASSWORD_NULL_CONFIG_ASSERTION());
/*      */     } 
/*      */     
/* 1593 */     if (store.getPeerAlias() != null) {
/* 1594 */       props.put("peerentity.alias", store.getPeerAlias());
/*      */     }
/*      */     
/* 1597 */     if (store.getSTSAlias() != null) {
/* 1598 */       props.put("sts.alias", store.getSTSAlias());
/*      */     }
/*      */     
/* 1601 */     if (store.getServiceAlias() != null) {
/* 1602 */       props.put("service.alias", store.getServiceAlias());
/*      */     }
/*      */     
/* 1605 */     if (store.getCertSelectorClassName() != null) {
/* 1606 */       props.put("truststore.certselector", store.getCertSelectorClassName());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String populateCallbackHandlerProps(Properties props, CallbackHandlerConfiguration conf) {
/* 1612 */     if (conf.getTimestampTimeout() != null)
/*      */     {
/* 1614 */       this.timestampTimeOut = Long.parseLong(conf.getTimestampTimeout()) * 1000L;
/*      */     }
/* 1616 */     if (conf.getUseXWSSCallbacks() != null) {
/* 1617 */       props.put("user.xwss.callbacks", conf.getUseXWSSCallbacks());
/*      */     }
/* 1619 */     if (conf.getiterationsForPDK() != null) {
/* 1620 */       this.iterationsForPDK = Integer.parseInt(conf.getiterationsForPDK());
/*      */     }
/* 1622 */     Iterator<PolicyAssertion> it = conf.getCallbackHandlers();
/* 1623 */     while (it.hasNext()) {
/* 1624 */       PolicyAssertion p = it.next();
/* 1625 */       CallbackHandler hd = (CallbackHandler)p;
/* 1626 */       String name = hd.getHandlerName();
/* 1627 */       String ret = hd.getHandler();
/* 1628 */       if ("xwssCallbackHandler".equals(name)) {
/* 1629 */         if (ret != null && !"".equals(ret)) {
/* 1630 */           return ret;
/*      */         }
/* 1632 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0018_NULL_OR_EMPTY_XWSS_CALLBACK_HANDLER_CLASSNAME());
/*      */         
/* 1634 */         throw new RuntimeException(LogStringsMessages.WSSTUBE_0018_NULL_OR_EMPTY_XWSS_CALLBACK_HANDLER_CLASSNAME());
/*      */       } 
/* 1636 */       if ("usernameHandler".equals(name)) {
/* 1637 */         if (ret != null && !"".equals(ret)) {
/* 1638 */           props.put("username.callback.handler", ret); continue;
/*      */         } 
/* 1640 */         QName qname = new QName("default");
/* 1641 */         String def = hd.getAttributeValue(qname);
/* 1642 */         if (def != null && !"".equals(def)) {
/* 1643 */           props.put("my.username", def); continue;
/*      */         } 
/* 1645 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0019_NULL_OR_EMPTY_USERNAME_HANDLER_CLASSNAME());
/*      */         
/* 1647 */         throw new RuntimeException(LogStringsMessages.WSSTUBE_0019_NULL_OR_EMPTY_USERNAME_HANDLER_CLASSNAME());
/*      */       } 
/*      */       
/* 1650 */       if ("passwordHandler".equals(name)) {
/* 1651 */         if (ret != null && !"".equals(ret)) {
/* 1652 */           props.put("password.callback.handler", ret); continue;
/*      */         } 
/* 1654 */         QName qname = new QName("default");
/* 1655 */         String def = hd.getAttributeValue(qname);
/* 1656 */         if (def != null && !"".equals(def)) {
/* 1657 */           props.put("my.password", def); continue;
/*      */         } 
/* 1659 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0020_NULL_OR_EMPTY_PASSWORD_HANDLER_CLASSNAME());
/*      */         
/* 1661 */         throw new RuntimeException(LogStringsMessages.WSSTUBE_0020_NULL_OR_EMPTY_PASSWORD_HANDLER_CLASSNAME());
/*      */       } 
/*      */       
/* 1664 */       if ("samlHandler".equals(name)) {
/* 1665 */         if (ret == null || "".equals(ret)) {
/* 1666 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0021_NULL_OR_EMPTY_SAML_HANDLER_CLASSNAME());
/*      */           
/* 1668 */           throw new RuntimeException(LogStringsMessages.WSSTUBE_0021_NULL_OR_EMPTY_SAML_HANDLER_CLASSNAME());
/*      */         } 
/* 1670 */         props.put("saml.callback.handler", ret); continue;
/*      */       } 
/* 1672 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0009_UNSUPPORTED_CALLBACK_TYPE_ENCOUNTERED(name));
/*      */       
/* 1674 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0009_UNSUPPORTED_CALLBACK_TYPE_ENCOUNTERED(name));
/*      */     } 
/*      */     
/* 1677 */     return null;
/*      */   }
/*      */   
/*      */   private void populateValidatorProps(Properties props, ValidatorConfiguration conf) {
/* 1681 */     if (conf.getMaxClockSkew() != null) {
/* 1682 */       props.put("max.clock.skew", conf.getMaxClockSkew());
/*      */     }
/*      */     
/* 1685 */     if (conf.getTimestampFreshnessLimit() != null) {
/* 1686 */       props.put("timestamp.freshness.limit", conf.getTimestampFreshnessLimit());
/*      */     }
/*      */     
/* 1689 */     if (conf.getMaxNonceAge() != null) {
/* 1690 */       props.put("max.nonce.age", conf.getMaxNonceAge());
/*      */     }
/* 1692 */     if (conf.getRevocationEnabled() != null) {
/* 1693 */       props.put("revocation.enabled", conf.getRevocationEnabled());
/*      */     }
/*      */     
/* 1696 */     Iterator<PolicyAssertion> it = conf.getValidators();
/* 1697 */     while (it.hasNext()) {
/* 1698 */       PolicyAssertion p = it.next();
/* 1699 */       Validator v = (Validator)p;
/* 1700 */       String name = v.getValidatorName();
/* 1701 */       String validator = v.getValidator();
/* 1702 */       if (validator == null || "".equals(validator)) {
/* 1703 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0022_NULL_OR_EMPTY_VALIDATOR_CLASSNAME(name));
/*      */         
/* 1705 */         throw new RuntimeException(LogStringsMessages.WSSTUBE_0022_NULL_OR_EMPTY_VALIDATOR_CLASSNAME(name));
/*      */       } 
/*      */       
/* 1708 */       if ("usernameValidator".equals(name)) {
/* 1709 */         props.put("username.validator", validator); continue;
/* 1710 */       }  if ("timestampValidator".equals(name)) {
/* 1711 */         props.put("timestamp.validator", validator); continue;
/* 1712 */       }  if ("certificateValidator".equals(name)) {
/* 1713 */         props.put("certificate.validator", validator); continue;
/* 1714 */       }  if ("samlAssertionValidator".equals(name)) {
/* 1715 */         props.put("saml.validator", validator); continue;
/*      */       } 
/* 1717 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0010_UNKNOWN_VALIDATOR_TYPE_CONFIG(name));
/*      */       
/* 1719 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0010_UNKNOWN_VALIDATOR_TYPE_CONFIG(name));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateCertStoreProps(Properties props, CertStoreConfig certStoreConfig) {
/* 1725 */     if (certStoreConfig.getCallbackHandlerClassName() != null) {
/* 1726 */       props.put("certstore.cbh", certStoreConfig.getCallbackHandlerClassName());
/*      */     }
/* 1728 */     if (certStoreConfig.getCertSelectorClassName() != null) {
/* 1729 */       props.put("certstore.certselector", certStoreConfig.getCertSelectorClassName());
/*      */     }
/* 1731 */     if (certStoreConfig.getCRLSelectorClassName() != null) {
/* 1732 */       props.put("certstore.crlselector", certStoreConfig.getCRLSelectorClassName());
/*      */     }
/*      */   }
/*      */   
/*      */   protected Class loadClass(String classname) throws Exception {
/* 1737 */     if (classname == null) {
/* 1738 */       return null;
/*      */     }
/*      */     
/* 1741 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 1742 */     if (loader != null) {
/*      */       try {
/* 1744 */         Class<?> ret = loader.loadClass(classname);
/* 1745 */         return ret;
/* 1746 */       } catch (ClassNotFoundException e) {}
/*      */     }
/*      */ 
/*      */     
/* 1750 */     loader = getClass().getClassLoader();
/*      */     try {
/* 1752 */       return loader.loadClass(classname);
/*      */     }
/* 1754 */     catch (ClassNotFoundException e) {
/*      */ 
/*      */       
/* 1757 */       log.log(Level.FINE, LogStringsMessages.WSSTUBE_0011_COULD_NOT_FIND_USER_CLASS(), classname);
/*      */       
/* 1759 */       throw new XWSSecurityException("Error : could not find user class :" + classname);
/*      */     } 
/*      */   }
/*      */   protected AlgorithmSuite getAlgoSuite(AlgorithmSuite suite) {
/* 1763 */     if (suite == null) {
/* 1764 */       return null;
/*      */     }
/* 1766 */     AlgorithmSuite als = new AlgorithmSuite(suite.getDigestAlgorithm(), suite.getEncryptionAlgorithm(), suite.getSymmetricKeyAlgorithm(), suite.getAsymmetricKeyAlgorithm());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1771 */     als.setSignatureAlgorithm(suite.getSignatureAlgorithm());
/* 1772 */     return als;
/*      */   }
/*      */   
/*      */   protected WSSAssertion getWssAssertion(WSSAssertion asser) {
/* 1776 */     WSSAssertion assertion = new WSSAssertion(asser.getRequiredProperties(), asser.getType());
/*      */ 
/*      */     
/* 1779 */     return assertion;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isReliableMessagingEnabled(WSDLPort port) {
/* 1784 */     if (port != null && port.getBinding() != null) {
/* 1785 */       boolean enabled = port.getBinding().getFeatures().isEnabled(ReliableMessagingFeature.class);
/* 1786 */       return enabled;
/*      */     } 
/* 1788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isMakeConnectionEnabled(WSDLPort port) {
/* 1792 */     if (port != null && port.getBinding() != null) {
/* 1793 */       boolean enabled = port.getBinding().getFeatures().isEnabled(MakeConnectionSupportedFeature.class);
/* 1794 */       return enabled;
/*      */     } 
/* 1796 */     return false;
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
/*      */   private void setPolicyCredentials(Policy policy) {
/* 1811 */     if (policy != null) {
/* 1812 */       if (policy.contains(AddressingVersion.W3C.policyNsUri) || policy.contains("http://www.w3.org/2007/05/addressing/metadata")) {
/* 1813 */         this.addVer = AddressingVersion.W3C;
/* 1814 */       } else if (policy.contains(AddressingVersion.MEMBER.policyNsUri)) {
/* 1815 */         this.addVer = AddressingVersion.MEMBER;
/*      */       } 
/* 1817 */       if (policy.contains(this.optServerSecurity) || policy.contains(this.optClientSecurity)) {
/* 1818 */         this.optimized = false;
/*      */       }
/* 1820 */       if (policy.contains(this.EPREnabled)) {
/* 1821 */         this.isEPREnabled = true;
/*      */       }
/* 1823 */       if (policy.contains(this.encSCServerCancel) || policy.contains(this.encSCClientCancel)) {
/* 1824 */         this.encryptCancelPayload = true;
/*      */       }
/* 1826 */       if (policy.contains(this.disableCPBuffering) || policy.contains(this.disableSPBuffering)) {
/* 1827 */         this.disablePayloadBuffer = true;
/*      */       }
/* 1829 */       if (policy.contains(this.disableIncPrefixServer) || policy.contains(this.disableIncPrefixClient)) {
/* 1830 */         this.disableIncPrefix = true;
/*      */       }
/* 1832 */       if (policy.contains(this.encHeaderContentServer) || policy.contains(this.encHeaderContentClient)) {
/* 1833 */         this.encHeaderContent = true;
/*      */       }
/* 1835 */       if (policy.contains(this.bsp10Client) || policy.contains(this.bsp10Server)) {
/* 1836 */         this.bsp10 = true;
/*      */       }
/* 1838 */       if (policy.contains(this.allowMissingTSClient) || policy.contains(this.allowMissingTSServer)) {
/* 1839 */         this.allowMissingTimestamp = true;
/*      */       }
/* 1841 */       if (policy.contains(this.unsetSecurityMUValueClient) || policy.contains(this.unsetSecurityMUValueServer)) {
/* 1842 */         this.securityMUValue = false;
/*      */       }
/* 1844 */       if (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri)) {
/* 1845 */         this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/* 1846 */         this.wsscVer = WSSCVersion.WSSC_10;
/* 1847 */         this.wsTrustVer = WSTrustVersion.WS_TRUST_10;
/* 1848 */       } else if (policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)) {
/* 1849 */         this.spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/* 1850 */         this.wsscVer = WSSCVersion.WSSC_13;
/* 1851 */         this.wsTrustVer = WSTrustVersion.WS_TRUST_13;
/* 1852 */       } else if (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri)) {
/* 1853 */         this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200512;
/* 1854 */         this.wsscVer = WSSCVersion.WSSC_10;
/* 1855 */         this.wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*      */       } 
/*      */ 
/*      */       
/* 1859 */       if (policy.contains(RmProtocolVersion.WSRM200702.protocolNamespaceUri) || policy.contains(RmProtocolVersion.WSRM200702.policyNamespaceUri)) {
/*      */         
/* 1861 */         this.rmVer = RmProtocolVersion.WSRM200702;
/* 1862 */       } else if (policy.contains(RmProtocolVersion.WSRM200502.protocolNamespaceUri) || policy.contains(RmProtocolVersion.WSRM200502.policyNamespaceUri)) {
/*      */         
/* 1864 */         this.rmVer = RmProtocolVersion.WSRM200502;
/*      */       } 
/* 1866 */       if (policy.contains(this.encRMLifecycleMsgServer) || policy.contains(this.encRMLifecycleMsgClient)) {
/* 1867 */         this.encRMLifecycleMsg = true;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private Policy getSCCancelPolicy(boolean encryptCancelPayload) throws PolicyException, IOException {
/* 1873 */     if (this.cancelMSP == null) {
/*      */       
/* 1875 */       String scCancelMessagePolicy = encryptCancelPayload ? "enc-sccancel-msglevel-policy.xml" : "sccancel-msglevel-policy.xml";
/* 1876 */       if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri)) {
/* 1877 */         scCancelMessagePolicy = encryptCancelPayload ? "enc-sccancel-msglevel-policy-sx.xml" : "sccancel-msglevel-policy-sx.xml";
/*      */       }
/* 1879 */       PolicySourceModel model = unmarshalPolicy("com/sun/xml/ws/security/impl/policyconv/" + scCancelMessagePolicy);
/*      */       
/* 1881 */       this.cancelMSP = ModelTranslator.getTranslator().translate(model);
/*      */     } 
/* 1883 */     return this.cancelMSP;
/*      */   }
/*      */   
/*      */   protected PolicyAlternativeHolder resolveAlternative(Packet packet, boolean isSCMessage) {
/* 1887 */     if (this.policyAlternatives.size() == 1) {
/* 1888 */       return this.policyAlternatives.get(0);
/*      */     }
/*      */     
/* 1891 */     String alternativeId = (String)packet.invocationProperties.get("policy-alternative-id");
/* 1892 */     if (alternativeId != null) {
/* 1893 */       for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 1894 */         if (alternativeId.equals(p.getId())) {
/* 1895 */           return p;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1900 */     if (!this.policyAlternatives.isEmpty()) {
/* 1901 */       return this.policyAlternatives.get(0);
/*      */     }
/* 1903 */     return null;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\SecurityTubeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */