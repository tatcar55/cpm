/*     */ package com.sun.xml.ws.security.secconv;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust10;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust13;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.secconv.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.WSTrustConstants;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import java.io.StringWriter;
/*     */ import java.net.URI;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewWSSCPlugin
/*     */ {
/* 111 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.secconv", "com.sun.xml.ws.security.secconv.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance();
/*     */   
/* 118 */   private WSSCVersion wsscVer = WSSCVersion.WSSC_10;
/* 119 */   private WSTrustVersion wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */   
/*     */   private static final int DEFAULT_KEY_SIZE = 256;
/*     */   private static final String SC_ASSERTION = "SecureConversationAssertion";
/*     */   private static final String FOR_ISSUE = "For Issue";
/*     */   private static final String FOR_CANCEL = "For Cancel";
/*     */   
/*     */   public NewWSSCPlugin(WSSCVersion wsscVer) {
/* 127 */     if (wsscVer instanceof com.sun.xml.ws.security.secconv.impl.wssx.WSSCVersion13) {
/* 128 */       this.wsscVer = wsscVer;
/* 129 */       this.wsTrustVer = WSTrustVersion.WS_TRUST_13;
/* 130 */       this.eleFac = WSTrustElementFactory.newInstance(this.wsTrustVer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSRequest createIssueRequest(PolicyAssertion token) {
/* 139 */     SecureConversationToken scToken = (SecureConversationToken)token;
/* 140 */     AssertionSet assertions = getAssertions(scToken);
/* 141 */     Trust10 trust10 = null;
/* 142 */     Trust13 trust13 = null;
/* 143 */     SymmetricBinding symBinding = null;
/* 144 */     for (PolicyAssertion policyAssertion : assertions) {
/* 145 */       SecurityPolicyVersion spVersion = getSPVersion(policyAssertion);
/* 146 */       if (PolicyUtil.isTrust13(policyAssertion, spVersion)) {
/* 147 */         trust13 = (Trust13)policyAssertion; continue;
/* 148 */       }  if (PolicyUtil.isTrust10(policyAssertion, spVersion)) {
/* 149 */         trust10 = (Trust10)policyAssertion; continue;
/* 150 */       }  if (PolicyUtil.isSymmetricBinding(policyAssertion, spVersion)) {
/* 151 */         symBinding = (SymmetricBinding)policyAssertion;
/*     */       }
/*     */     } 
/*     */     
/* 155 */     int skl = 256;
/* 156 */     boolean reqClientEntropy = false;
/* 157 */     if (symBinding != null) {
/* 158 */       AlgorithmSuite algoSuite = symBinding.getAlgorithmSuite();
/* 159 */       skl = algoSuite.getMinSKLAlgorithm();
/* 160 */       if (skl < 1) {
/* 161 */         skl = 256;
/*     */       }
/* 163 */       if (log.isLoggable(Level.FINE)) {
/* 164 */         log.log(Level.FINE, LogStringsMessages.WSSC_1006_SYM_BIN_KEYSIZE(Integer.valueOf(skl), Integer.valueOf(256)));
/*     */       }
/*     */     } 
/*     */     
/* 168 */     if (trust10 != null) {
/* 169 */       Set trustReqdProps = trust10.getRequiredProperties();
/* 170 */       reqClientEntropy = trustReqdProps.contains("RequireClientEntropy");
/*     */     } 
/*     */     
/* 173 */     if (trust13 != null) {
/* 174 */       Set trustReqdProps = trust13.getRequiredProperties();
/* 175 */       reqClientEntropy = trustReqdProps.contains("RequireClientEntropy");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     BaseSTSRequest rst = null;
/*     */     try {
/* 183 */       rst = createRequestSecurityToken(reqClientEntropy, skl);
/* 184 */     } catch (WSSecureConversationException ex) {
/* 185 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Issue"), (Throwable)ex);
/*     */       
/* 187 */       throw new RuntimeException(LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Issue"), ex);
/* 188 */     } catch (WSTrustException ex) {
/* 189 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0021_PROBLEM_CREATING_RST_TRUST(), (Throwable)ex);
/*     */       
/* 191 */       throw new RuntimeException(LogStringsMessages.WSSC_0021_PROBLEM_CREATING_RST_TRUST(), ex);
/*     */     } 
/*     */     
/* 194 */     return rst;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet createIssuePacket(PolicyAssertion token, BaseSTSRequest rst, WSDLPort wsdlPort, WSBinding binding, JAXBContext jbCxt, String endPointAddress, Packet packet) {
/* 199 */     Packet ret = createSendRequestPacket(token, wsdlPort, binding, jbCxt, rst, this.wsscVer.getSCTRequestAction(), endPointAddress, packet);
/*     */ 
/*     */     
/* 202 */     return ret;
/*     */   }
/*     */   
/*     */   public BaseSTSResponse getRSTR(JAXBContext jbCxt, Packet respPacket) {
/*     */     Unmarshaller unmarshaller;
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/*     */     try {
/* 209 */       unmarshaller = jbCxt.createUnmarshaller();
/* 210 */     } catch (JAXBException ex) {
/* 211 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0016_PROBLEM_MAR_UNMAR(), ex);
/*     */       
/* 213 */       throw new RuntimeException(LogStringsMessages.WSSC_0016_PROBLEM_MAR_UNMAR(), ex);
/*     */     } 
/*     */ 
/*     */     
/* 217 */     Message response = respPacket.getMessage();
/* 218 */     BaseSTSResponse rstr = null;
/* 219 */     if (!response.isFault()) {
/* 220 */       JAXBElement rstrEle = null;
/*     */       try {
/* 222 */         rstrEle = (JAXBElement)response.readPayloadAsJAXB(unmarshaller);
/* 223 */       } catch (JAXBException ex) {
/* 224 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0018_ERR_JAXB_RSTR(), ex);
/*     */         
/* 226 */         throw new RuntimeException(LogStringsMessages.WSSC_0018_ERR_JAXB_RSTR(), ex);
/*     */       } 
/* 228 */       if (this.wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/* 229 */         RequestSecurityTokenResponseCollection requestSecurityTokenResponseCollection = this.eleFac.createRSTRCollectionFrom(rstrEle);
/*     */       } else {
/* 231 */         requestSecurityTokenResponse = this.eleFac.createRSTRFrom(rstrEle);
/*     */       } 
/*     */     } else {
/*     */       try {
/* 235 */         throw new SOAPFaultException(response.readAsSOAPMessage().getSOAPBody().getFault());
/* 236 */       } catch (SOAPException ex) {
/* 237 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0022_PROBLEM_CREATING_FAULT(), ex);
/*     */         
/* 239 */         throw new RuntimeException(LogStringsMessages.WSSC_0022_PROBLEM_CREATING_FAULT(), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuedTokenContext processRSTR(IssuedTokenContext context, BaseSTSRequest rst, BaseSTSResponse rstr, String endPointAddress) {
/*     */     try {
/* 252 */       processRequestSecurityTokenResponse(rst, rstr, context);
/* 253 */     } catch (WSSecureConversationException ex) {
/* 254 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), (Throwable)ex);
/*     */       
/* 256 */       throw new RuntimeException(LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), ex);
/*     */     } 
/* 258 */     context.setEndpointAddress(endPointAddress);
/* 259 */     return context;
/*     */   }
/*     */ 
/*     */   
/*     */   private AssertionSet getAssertions(SecureConversationToken scToken) {
/* 264 */     return scToken.getBootstrapPolicy().getAssertionSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSRequest createCancelRequest(IssuedTokenContext ctx) {
/* 271 */     BaseSTSRequest rst = null;
/*     */     try {
/* 273 */       rst = createRequestSecurityTokenForCancel(ctx);
/* 274 */     } catch (WSSecureConversationException ex) {
/* 275 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Cancel"), (Throwable)ex);
/*     */       
/* 277 */       throw new RuntimeException(LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Cancel"), ex);
/*     */     } 
/* 279 */     return rst;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet createCancelPacket(BaseSTSRequest rst, WSDLPort wsdlPort, WSBinding binding, JAXBContext jbCxt, String endPointAddress) {
/* 284 */     Packet ret = createSendRequestPacket(null, wsdlPort, binding, jbCxt, rst, this.wsscVer.getSCTCancelRequestAction(), endPointAddress, null);
/*     */     
/* 286 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuedTokenContext processCancellation(IssuedTokenContext ctx, WSDLPort wsdlPort, WSBinding binding, Pipe securityPipe, JAXBContext jbCxt, String endPointAddress) {
/* 295 */     BaseSTSRequest rst = null;
/*     */     try {
/* 297 */       rst = createRequestSecurityTokenForCancel(ctx);
/* 298 */     } catch (WSSecureConversationException ex) {
/* 299 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Cancel"), (Throwable)ex);
/*     */       
/* 301 */       throw new RuntimeException(LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Cancel"), ex);
/*     */     } 
/*     */     
/* 304 */     BaseSTSResponse rstr = sendRequest(null, wsdlPort, binding, securityPipe, jbCxt, rst, this.wsscVer.getSCTCancelRequestAction(), endPointAddress, null);
/*     */ 
/*     */     
/*     */     try {
/* 308 */       processRequestSecurityTokenResponse(rst, rstr, ctx);
/* 309 */     } catch (WSSecureConversationException ex) {
/* 310 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), (Throwable)ex);
/*     */       
/* 312 */       throw new RuntimeException(LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), ex);
/*     */     } 
/*     */     
/* 315 */     return ctx;
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
/*     */   private void copyStandardSecurityProperties(Packet packet, Packet requestPacket) {
/* 327 */     Set<String> set = packet.invocationProperties.keySet();
/* 328 */     for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
/* 329 */       String key = it.next();
/* 330 */       requestPacket.invocationProperties.put(key, packet.invocationProperties.get(key));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet createSendRequestPacket(PolicyAssertion issuedToken, WSDLPort wsdlPort, WSBinding binding, JAXBContext jbCxt, BaseSTSRequest rst, String action, String endPointAddress, Packet packet) {
/*     */     Marshaller marshaller;
/*     */     try {
/* 339 */       marshaller = jbCxt.createMarshaller();
/* 340 */     } catch (JAXBException ex) {
/* 341 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0016_PROBLEM_MAR_UNMAR(), ex);
/*     */       
/* 343 */       throw new RuntimeException(LogStringsMessages.WSSC_0016_PROBLEM_MAR_UNMAR(), ex);
/*     */     } 
/*     */     
/* 346 */     Message request = Messages.create(marshaller, this.eleFac.toJAXBElement(rst), binding.getSOAPVersion());
/*     */ 
/*     */     
/* 349 */     if (log.isLoggable(Level.FINE)) {
/* 350 */       log.log(Level.FINE, LogStringsMessages.WSSC_1009_SEND_REQ_MESSAGE(printMessageAsString(request)));
/*     */     }
/*     */     
/* 353 */     Packet reqPacket = new Packet(request);
/* 354 */     if (issuedToken != null) {
/* 355 */       reqPacket.invocationProperties.put("SecureConversationAssertion", issuedToken);
/*     */     }
/* 357 */     if (packet != null) {
/* 358 */       for (WSTrustConstants.STS_PROPERTIES stsProperty : WSTrustConstants.STS_PROPERTIES.values()) {
/* 359 */         reqPacket.invocationProperties.put(stsProperty.toString(), packet.invocationProperties.get(stsProperty.toString()));
/*     */       }
/*     */     }
/*     */     
/* 363 */     reqPacket.setEndPointAddressString(endPointAddress);
/* 364 */     if (log.isLoggable(Level.FINE)) {
/* 365 */       log.log(Level.FINE, LogStringsMessages.WSSC_1008_SET_EP_ADDRESS(endPointAddress));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 371 */       reqPacket = addAddressingHeaders(reqPacket, wsdlPort, binding, action);
/* 372 */     } catch (WSSecureConversationException ex) {
/* 373 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0017_PROBLEM_ADD_ADDRESS_HEADERS(), (Throwable)ex);
/*     */       
/* 375 */       throw new RuntimeException(LogStringsMessages.WSSC_0017_PROBLEM_ADD_ADDRESS_HEADERS(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (packet != null) {
/* 382 */       reqPacket.contentNegotiation = packet.contentNegotiation;
/*     */     }
/* 384 */     copyStandardSecurityProperties(packet, reqPacket);
/* 385 */     return reqPacket;
/*     */   }
/*     */   
/*     */   private BaseSTSResponse sendRequest(PolicyAssertion issuedToken, WSDLPort wsdlPort, WSBinding binding, Pipe securityPipe, JAXBContext jbCxt, BaseSTSRequest rst, String action, String endPointAddress, Packet packet) {
/*     */     Marshaller marshaller;
/*     */     Unmarshaller unmarshaller;
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/*     */     try {
/* 393 */       marshaller = jbCxt.createMarshaller();
/* 394 */       unmarshaller = jbCxt.createUnmarshaller();
/* 395 */     } catch (JAXBException ex) {
/* 396 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0016_PROBLEM_MAR_UNMAR(), ex);
/*     */       
/* 398 */       throw new RuntimeException(LogStringsMessages.WSSC_0016_PROBLEM_MAR_UNMAR(), ex);
/*     */     } 
/*     */     
/* 401 */     Message request = Messages.create(marshaller, this.eleFac.toJAXBElement((RequestSecurityToken)rst), binding.getSOAPVersion());
/*     */ 
/*     */     
/* 404 */     if (log.isLoggable(Level.FINE)) {
/* 405 */       log.log(Level.FINE, LogStringsMessages.WSSC_1009_SEND_REQ_MESSAGE(printMessageAsString(request)));
/*     */     }
/*     */     
/* 408 */     Packet reqPacket = new Packet(request);
/* 409 */     if (issuedToken != null) {
/* 410 */       reqPacket.invocationProperties.put("SecureConversationAssertion", issuedToken);
/*     */     }
/* 412 */     if (packet != null) {
/* 413 */       for (WSTrustConstants.STS_PROPERTIES stsProperty : WSTrustConstants.STS_PROPERTIES.values()) {
/* 414 */         reqPacket.invocationProperties.put(stsProperty.toString(), packet.invocationProperties.get(stsProperty.toString()));
/*     */       }
/*     */     }
/*     */     
/* 418 */     reqPacket.setEndPointAddressString(endPointAddress);
/* 419 */     if (log.isLoggable(Level.FINE)) {
/* 420 */       log.log(Level.FINE, LogStringsMessages.WSSC_1008_SET_EP_ADDRESS(endPointAddress));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 425 */       reqPacket = addAddressingHeaders(reqPacket, wsdlPort, binding, action);
/* 426 */     } catch (WSSecureConversationException ex) {
/* 427 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0017_PROBLEM_ADD_ADDRESS_HEADERS(), (Throwable)ex);
/*     */       
/* 429 */       throw new RuntimeException(LogStringsMessages.WSSC_0017_PROBLEM_ADD_ADDRESS_HEADERS(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 435 */     if (packet != null) {
/* 436 */       reqPacket.contentNegotiation = packet.contentNegotiation;
/*     */     }
/*     */ 
/*     */     
/* 440 */     Packet respPacket = securityPipe.process(reqPacket);
/*     */ 
/*     */     
/* 443 */     Message response = respPacket.getMessage();
/* 444 */     BaseSTSResponse rstr = null;
/* 445 */     if (!response.isFault()) {
/* 446 */       JAXBElement rstrEle = null;
/*     */       try {
/* 448 */         rstrEle = (JAXBElement)response.readPayloadAsJAXB(unmarshaller);
/* 449 */       } catch (JAXBException ex) {
/* 450 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0018_ERR_JAXB_RSTR(), ex);
/*     */         
/* 452 */         throw new RuntimeException(LogStringsMessages.WSSC_0018_ERR_JAXB_RSTR(), ex);
/*     */       } 
/* 454 */       requestSecurityTokenResponse = this.eleFac.createRSTRFrom(rstrEle);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 459 */         throw new SOAPFaultException(response.readAsSOAPMessage().getSOAPBody().getFault());
/* 460 */       } catch (SOAPException ex) {
/* 461 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0022_PROBLEM_CREATING_FAULT(), ex);
/*     */         
/* 463 */         throw new RuntimeException(LogStringsMessages.WSSC_0022_PROBLEM_CREATING_FAULT(), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 467 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   private BaseSTSRequest createRequestSecurityToken(boolean reqClientEntropy, int skl) throws WSSecureConversationException, WSTrustException {
/* 472 */     URI tokenType = URI.create(this.wsscVer.getSCTTokenTypeURI());
/* 473 */     URI requestType = URI.create(this.wsTrustVer.getIssueRequestTypeURI());
/* 474 */     SecureRandom random = new SecureRandom();
/* 475 */     byte[] rawValue = new byte[skl / 8];
/* 476 */     random.nextBytes(rawValue);
/* 477 */     BinarySecret secret = this.eleFac.createBinarySecret(rawValue, this.wsTrustVer.getNonceBinarySecretTypeURI());
/* 478 */     Entropy entropy = reqClientEntropy ? this.eleFac.createEntropy(secret) : null;
/* 479 */     RequestSecurityToken requestSecurityToken = this.eleFac.createRSTForIssue(tokenType, requestType, null, null, null, entropy, null);
/*     */     
/* 481 */     requestSecurityToken.setKeySize(skl);
/* 482 */     requestSecurityToken.setKeyType(URI.create(this.wsTrustVer.getSymmetricKeyTypeURI()));
/* 483 */     requestSecurityToken.setComputedKeyAlgorithm(URI.create(this.wsTrustVer.getCKPSHA1algorithmURI()));
/*     */     
/* 485 */     return (BaseSTSRequest)requestSecurityToken;
/*     */   }
/*     */   
/*     */   private BaseSTSRequest createRequestSecurityTokenForCancel(IssuedTokenContext ctx) throws WSSecureConversationException {
/* 489 */     URI requestType = null;
/* 490 */     requestType = URI.create(this.wsTrustVer.getCancelRequestTypeURI());
/*     */     
/* 492 */     CancelTarget target = this.eleFac.createCancelTarget((SecurityTokenReference)ctx.getUnAttachedSecurityTokenReference());
/* 493 */     return (BaseSTSRequest)this.eleFac.createRSTForCancel(requestType, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processRequestSecurityTokenResponse(BaseSTSRequest rst, BaseSTSResponse rstr, IssuedTokenContext context) throws WSSecureConversationException {
/* 500 */     WSSCClientContract contract = WSSCFactory.newWSSCClientContract();
/* 501 */     if (this.wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/* 502 */       contract.handleRSTRC((RequestSecurityToken)rst, (RequestSecurityTokenResponseCollection)rstr, context);
/*     */     } else {
/* 504 */       contract.handleRSTR((RequestSecurityToken)rst, (RequestSecurityTokenResponse)rstr, context);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String printMessageAsString(Message message) {
/* 509 */     StringWriter writer = new StringWriter();
/* 510 */     XMLOutputFactory factory = XMLOutputFactory.newInstance();
/*     */     try {
/* 512 */       XMLStreamWriter streamWriter = factory.createXMLStreamWriter(writer);
/* 513 */       message.writeTo(streamWriter);
/* 514 */       streamWriter.flush();
/* 515 */       return writer.toString();
/* 516 */     } catch (XMLStreamException ex) {
/* 517 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0025_PROBLEM_PRINTING_MSG(), ex);
/*     */       
/* 519 */       throw new RuntimeException(LogStringsMessages.WSSC_0025_PROBLEM_PRINTING_MSG(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet addAddressingHeaders(Packet packet, WSDLPort wsdlPort, WSBinding binding, String action) throws WSSecureConversationException {
/* 524 */     HeaderList headers = packet.getMessage().getHeaders();
/* 525 */     headers.fillRequestAddressingHeaders(packet, binding.getAddressingVersion(), binding.getSOAPVersion(), false, action);
/*     */     
/* 527 */     return packet;
/*     */   }
/*     */   
/*     */   private SecurityPolicyVersion getSPVersion(PolicyAssertion pa) {
/* 531 */     String nsUri = pa.getName().getNamespaceURI();
/*     */     
/* 533 */     SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */     
/* 535 */     if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(nsUri)) {
/* 536 */       spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */     }
/* 538 */     return spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\NewWSSCPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */