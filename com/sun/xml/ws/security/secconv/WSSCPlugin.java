/*     */ package com.sun.xml.ws.security.secconv;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Engine;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.security.secconv.client.SCTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policyconv.PolicyID;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyUtil;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.secconv.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.WSTrustConstants;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.RenewTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.jaxws.impl.SecurityClientTube;
/*     */ import com.sun.xml.wss.provider.wsit.WSITClientAuthContext;
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
/*     */ public class WSSCPlugin
/*     */ {
/* 125 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.secconv", "com.sun.xml.ws.security.secconv.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_KEY_SIZE = 256;
/*     */ 
/*     */   
/*     */   private static final String SC_ASSERTION = "SecureConversationAssertion";
/*     */ 
/*     */   
/*     */   private static final String FOR_CANCEL = "For Cancel";
/*     */ 
/*     */   
/* 137 */   private static SignaturePolicy renewSignaturePolicy = null;
/* 138 */   private static PolicyID pid = new PolicyID();
/* 139 */   private static Binding binding = null;
/*     */   
/*     */   private Engine fiberEngine;
/* 142 */   private Packet packet = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(IssuedTokenContext itc) {
/*     */     RequestSecurityToken requestSecurityToken;
/* 224 */     SCTokenConfiguration sctConfig = itc.getSecurityPolicy().get(0);
/* 225 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 226 */     WSTrustVersion wsTrustVer = null;
/* 227 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 228 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 230 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/* 232 */     this.packet = sctConfig.getPacket();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     int skl = 256;
/* 238 */     if (sctConfig.isSymmetricBinding()) {
/* 239 */       skl = sctConfig.getKeySize();
/* 240 */       if (skl < 1) {
/* 241 */         skl = 256;
/*     */       }
/* 243 */       if (log.isLoggable(Level.FINE)) {
/* 244 */         log.log(Level.FINE, LogStringsMessages.WSSC_1006_SYM_BIN_KEYSIZE(Integer.valueOf(skl), Integer.valueOf(256)));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     BaseSTSRequest rst = null;
/*     */     try {
/* 253 */       requestSecurityToken = createRequestSecurityToken(sctConfig, sctConfig.getReqClientEntropy(), skl);
/* 254 */     } catch (WSSecureConversationException ex) {
/* 255 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0024_ERROR_CREATING_RST(""), (Throwable)ex);
/*     */       
/* 257 */       throw new RuntimeException(LogStringsMessages.WSSC_0024_ERROR_CREATING_RST(""), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     BaseSTSResponse rstr = sendRequest(sctConfig, (BaseSTSRequest)requestSecurityToken, itc.getEndpointAddress(), wsscVer.getSCTRequestAction());
/*     */     
/* 266 */     if (log.isLoggable(Level.FINE)) {
/* 267 */       log.log(Level.FINE, LogStringsMessages.WSSC_1012_RECEIVED_SCT_RSTR_ISSUE(WSTrustUtil.elemToString(rstr, wsTrustVer)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 274 */       processRequestSecurityTokenResponse(sctConfig, (BaseSTSRequest)requestSecurityToken, rstr, itc);
/* 275 */     } catch (WSSecureConversationException ex) {
/* 276 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   public void processRenew(IssuedTokenContext itc) {
/*     */     RequestSecurityToken requestSecurityToken;
/* 281 */     SCTokenConfiguration sctConfig = itc.getSecurityPolicy().get(0);
/* 282 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 283 */     WSTrustVersion wsTrustVer = null;
/* 284 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 285 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 287 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/* 289 */     this.packet = sctConfig.getPacket();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     int skl = 256;
/* 295 */     if (sctConfig.isSymmetricBinding()) {
/* 296 */       skl = sctConfig.getKeySize();
/* 297 */       if (skl < 1) {
/* 298 */         skl = 256;
/*     */       }
/* 300 */       if (log.isLoggable(Level.FINE)) {
/* 301 */         log.log(Level.FINE, LogStringsMessages.WSSC_1006_SYM_BIN_KEYSIZE(Integer.valueOf(skl), Integer.valueOf(256)));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 308 */     BaseSTSRequest rst = null;
/*     */     try {
/* 310 */       requestSecurityToken = createRequestSecurityTokenForRenew(itc, sctConfig.getReqClientEntropy(), skl);
/* 311 */     } catch (WSSecureConversationException ex) {
/* 312 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0024_ERROR_CREATING_RST(""), (Throwable)ex);
/*     */       
/* 314 */       throw new RuntimeException(LogStringsMessages.WSSC_0024_ERROR_CREATING_RST(""), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     createRenewSignaturePolicy(sctConfig.getSCToken());
/*     */     
/* 322 */     BaseSTSResponse rstr = sendRequest(sctConfig, (BaseSTSRequest)requestSecurityToken, itc.getEndpointAddress(), wsscVer.getSCTRenewRequestAction());
/*     */     
/* 324 */     if (log.isLoggable(Level.FINE)) {
/* 325 */       log.log(Level.FINE, LogStringsMessages.WSSC_1014_RECEIVED_SCT_RSTR_RENEW(WSTrustUtil.elemToString(rstr, wsTrustVer)));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 330 */       processRequestSecurityTokenResponse(sctConfig, (BaseSTSRequest)requestSecurityToken, rstr, itc);
/* 331 */     } catch (WSSecureConversationException ex) {
/* 332 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private BaseSTSResponse sendRequest(SCTokenConfiguration sctConfig, BaseSTSRequest rst, String endPointAddress, String action) {
/*     */     Marshaller marshaller;
/*     */     Unmarshaller unmarshaller;
/*     */     RequestSecurityTokenResponse requestSecurityTokenResponse;
/* 340 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 341 */     WSTrustVersion wsTrustVer = null;
/* 342 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 343 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 345 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/* 347 */     WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(wsTrustVer);
/* 348 */     JAXBContext jaxbContext = WSTrustElementFactory.getContext(wsTrustVer);
/*     */     try {
/* 350 */       marshaller = jaxbContext.createMarshaller();
/* 351 */       unmarshaller = jaxbContext.createUnmarshaller();
/* 352 */     } catch (JAXBException ex) {
/* 353 */       log.log(Level.SEVERE, "WSSC0016.problem.mar.unmar", ex);
/* 354 */       throw new RuntimeException("Problem creating JAXB Marshaller/Unmarshaller", ex);
/*     */     } 
/*     */     
/* 357 */     Message request = Messages.create(marshaller, eleFac.toJAXBElement(rst), sctConfig.getWSBinding().getSOAPVersion());
/*     */ 
/*     */     
/* 360 */     if (log.isLoggable(Level.FINE)) {
/* 361 */       log.log(Level.FINE, LogStringsMessages.WSSC_1009_SEND_REQ_MESSAGE(printMessageAsString(request)));
/*     */     }
/*     */     
/* 364 */     Packet reqPacket = new Packet(request);
/* 365 */     if (sctConfig.getSCToken() != null) {
/* 366 */       reqPacket.invocationProperties.put("SecureConversationAssertion", sctConfig.getSCToken());
/*     */     }
/* 368 */     if (sctConfig.getPacket() != null) {
/* 369 */       for (WSTrustConstants.STS_PROPERTIES stsProperty : WSTrustConstants.STS_PROPERTIES.values()) {
/* 370 */         reqPacket.invocationProperties.put(stsProperty.toString(), (sctConfig.getPacket()).invocationProperties.get(stsProperty.toString()));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 375 */     reqPacket.endpointAddress = this.packet.endpointAddress;
/* 376 */     if (log.isLoggable(Level.FINE)) {
/* 377 */       log.log(Level.FINE, LogStringsMessages.WSSC_1008_SET_EP_ADDRESS(endPointAddress));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 383 */       reqPacket = addAddressingHeaders(reqPacket, sctConfig.getWSDLPort(), sctConfig.getWSBinding(), action, sctConfig.getAddressingVersion());
/* 384 */     } catch (WSSecureConversationException ex) {
/* 385 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0017_PROBLEM_ADD_ADDRESS_HEADERS(), (Throwable)ex);
/*     */       
/* 387 */       throw new RuntimeException(LogStringsMessages.WSSC_0017_PROBLEM_ADD_ADDRESS_HEADERS(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     if (sctConfig.getPacket() != null) {
/* 394 */       reqPacket.contentNegotiation = (sctConfig.getPacket()).contentNegotiation;
/*     */     }
/*     */     
/* 397 */     copyStandardSecurityProperties(sctConfig.getPacket(), reqPacket);
/*     */ 
/*     */     
/* 400 */     Packet respPacket = null;
/* 401 */     if (sctConfig.getClientTube() != null) {
/* 402 */       reqPacket = ((SecurityClientTube)sctConfig.getClientTube()).processClientRequestPacket(reqPacket);
/* 403 */       Tube tubeline = sctConfig.getNextTube();
/* 404 */       Fiber fiber = getFiberEngine().createFiber();
/* 405 */       respPacket = fiber.runSync(tubeline, reqPacket);
/* 406 */       respPacket = ((SecurityClientTube)sctConfig.getClientTube()).processClientResponsePacket(respPacket);
/*     */     } else {
/* 408 */       WSITClientAuthContext wsitAuthCtx = (WSITClientAuthContext)sctConfig.getOtherOptions().get("WSITClientAuthContext");
/* 409 */       if (wsitAuthCtx != null) {
/*     */         try {
/* 411 */           respPacket = wsitAuthCtx.secureRequest(reqPacket, null, true);
/* 412 */         } catch (XWSSecurityException e) {
/* 413 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 419 */     Message response = respPacket.getMessage();
/* 420 */     BaseSTSResponse rstr = null;
/* 421 */     if (!response.isFault()) {
/* 422 */       JAXBElement rstrEle = null;
/*     */       try {
/* 424 */         rstrEle = (JAXBElement)response.readPayloadAsJAXB(unmarshaller);
/* 425 */       } catch (JAXBException ex) {
/* 426 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0018_ERR_JAXB_RSTR(), ex);
/*     */         
/* 428 */         throw new RuntimeException(LogStringsMessages.WSSC_0018_ERR_JAXB_RSTR(), ex);
/*     */       } 
/* 430 */       if (wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/*     */         
/*     */         try {
/* 433 */           RequestSecurityTokenResponseCollection requestSecurityTokenResponseCollection = eleFac.createRSTRCollectionFrom(rstrEle);
/* 434 */         } catch (Exception e) {
/* 435 */           requestSecurityTokenResponse = eleFac.createRSTRFrom(rstrEle);
/*     */         } 
/*     */       } else {
/*     */         
/* 439 */         requestSecurityTokenResponse = eleFac.createRSTRFrom(rstrEle);
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 445 */         throw new SOAPFaultException(response.readAsSOAPMessage().getSOAPBody().getFault());
/* 446 */       } catch (Exception ex) {
/* 447 */         log.log(Level.SEVERE, LogStringsMessages.WSSC_0022_PROBLEM_CREATING_FAULT(), ex);
/*     */         
/* 449 */         throw new RuntimeException(LogStringsMessages.WSSC_0022_PROBLEM_CREATING_FAULT(), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 453 */     return (BaseSTSResponse)requestSecurityTokenResponse;
/*     */   }
/*     */   
/*     */   private AssertionSet getAssertions(SecureConversationToken scToken) {
/* 457 */     return scToken.getBootstrapPolicy().getAssertionSet();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCancellation(IssuedTokenContext itc) {
/*     */     RequestSecurityToken requestSecurityToken;
/* 490 */     SCTokenConfiguration sctConfig = itc.getSecurityPolicy().get(0);
/* 491 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 492 */     WSTrustVersion wsTrustVer = null;
/* 493 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 494 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 496 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/*     */     
/* 499 */     this.packet = sctConfig.getPacket();
/*     */ 
/*     */ 
/*     */     
/* 503 */     BaseSTSRequest rst = null;
/*     */     try {
/* 505 */       requestSecurityToken = createRequestSecurityTokenForCancel(sctConfig, itc);
/* 506 */     } catch (WSSecureConversationException ex) {
/* 507 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Cancel"), (Throwable)ex);
/*     */       
/* 509 */       throw new RuntimeException(LogStringsMessages.WSSC_0024_ERROR_CREATING_RST("For Cancel"), ex);
/*     */     } 
/*     */     
/* 512 */     BaseSTSResponse rstr = sendRequest(sctConfig, (BaseSTSRequest)requestSecurityToken, itc.getEndpointAddress(), wsscVer.getSCTCancelRequestAction());
/*     */     
/* 514 */     if (log.isLoggable(Level.FINE)) {
/* 515 */       log.log(Level.FINE, LogStringsMessages.WSSC_1016_RECEIVED_SCT_RSTR_CANCEL(WSTrustUtil.elemToString(rstr, wsTrustVer)));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 520 */       processRequestSecurityTokenResponse(sctConfig, (BaseSTSRequest)requestSecurityToken, rstr, itc);
/* 521 */     } catch (WSSecureConversationException ex) {
/* 522 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), (Throwable)ex);
/*     */       
/* 524 */       throw new RuntimeException(LogStringsMessages.WSSC_0020_PROBLEM_CREATING_RSTR(), ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestSecurityToken createRequestSecurityToken(SCTokenConfiguration sctConfig, boolean reqClientEntropy, int skl) throws WSSecureConversationException {
/* 642 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 643 */     WSTrustVersion wsTrustVer = null;
/* 644 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 645 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 647 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/* 649 */     WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(wsTrustVer);
/* 650 */     URI tokenType = URI.create(wsscVer.getSCTTokenTypeURI());
/* 651 */     URI requestType = URI.create(wsTrustVer.getIssueRequestTypeURI());
/* 652 */     SecureRandom random = new SecureRandom();
/* 653 */     byte[] rawValue = new byte[skl / 8];
/* 654 */     random.nextBytes(rawValue);
/* 655 */     BinarySecret secret = eleFac.createBinarySecret(rawValue, wsTrustVer.getNonceBinarySecretTypeURI());
/* 656 */     Entropy entropy = reqClientEntropy ? eleFac.createEntropy(secret) : null;
/* 657 */     Lifetime lifetime = null;
/* 658 */     if (sctConfig.getSCTokenTimeout() > 0L) {
/*     */       
/* 660 */       long currentTime = WSTrustUtil.getCurrentTimeWithOffset();
/* 661 */       lifetime = WSTrustUtil.createLifetime(currentTime, sctConfig.getSCTokenTimeout(), wsTrustVer);
/*     */     } 
/* 663 */     RequestSecurityToken rst = null;
/*     */     try {
/* 665 */       rst = eleFac.createRSTForIssue(tokenType, requestType, null, null, null, entropy, lifetime);
/* 666 */       rst.setKeySize(skl);
/* 667 */       rst.setKeyType(URI.create(wsTrustVer.getSymmetricKeyTypeURI()));
/* 668 */       rst.setComputedKeyAlgorithm(URI.create(wsTrustVer.getCKPSHA1algorithmURI()));
/* 669 */     } catch (WSTrustException ex) {
/* 670 */       throw new WSSecureConversationException(ex);
/*     */     } 
/*     */     
/* 673 */     if (log.isLoggable(Level.FINE)) {
/* 674 */       log.log(Level.FINE, LogStringsMessages.WSSC_1011_CREATED_SCT_RST_ISSUE(WSTrustUtil.elemToString((BaseSTSRequest)rst, wsTrustVer)));
/*     */     }
/*     */     
/* 677 */     return rst;
/*     */   }
/*     */   
/*     */   private RequestSecurityToken createRequestSecurityTokenForRenew(IssuedTokenContext itc, boolean reqClientEntropy, int skl) throws WSSecureConversationException {
/* 681 */     SCTokenConfiguration sctConfig = itc.getSecurityPolicy().get(0);
/* 682 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 683 */     WSTrustVersion wsTrustVer = null;
/* 684 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 685 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 687 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/* 689 */     WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(wsTrustVer);
/* 690 */     URI tokenType = URI.create(wsscVer.getSCTTokenTypeURI());
/* 691 */     URI requestType = URI.create(wsTrustVer.getRenewRequestTypeURI());
/* 692 */     SecureRandom random = new SecureRandom();
/* 693 */     byte[] rawValue = new byte[skl / 8];
/* 694 */     random.nextBytes(rawValue);
/* 695 */     BinarySecret secret = eleFac.createBinarySecret(rawValue, wsTrustVer.getNonceBinarySecretTypeURI());
/* 696 */     Entropy entropy = reqClientEntropy ? eleFac.createEntropy(secret) : null;
/* 697 */     RenewTarget target = eleFac.createRenewTarget((SecurityTokenReference)itc.getUnAttachedSecurityTokenReference());
/*     */     
/* 699 */     RequestSecurityToken rst = null;
/*     */     try {
/* 701 */       rst = eleFac.createRSTForRenew(tokenType, requestType, null, target, null, null);
/* 702 */       rst.setEntropy(entropy);
/* 703 */       rst.setKeySize(skl);
/* 704 */       rst.setKeyType(URI.create(wsTrustVer.getSymmetricKeyTypeURI()));
/* 705 */       rst.setComputedKeyAlgorithm(URI.create(wsTrustVer.getCKPSHA1algorithmURI()));
/* 706 */     } catch (WSTrustException ex) {
/* 707 */       throw new WSSecureConversationException(ex);
/*     */     } 
/* 709 */     Lifetime lifetime = null;
/* 710 */     if (sctConfig.getSCTokenTimeout() > 0L) {
/*     */       
/* 712 */       long currentTime = WSTrustUtil.getCurrentTimeWithOffset();
/* 713 */       lifetime = WSTrustUtil.createLifetime(currentTime, sctConfig.getSCTokenTimeout(), wsTrustVer);
/* 714 */       rst.setLifetime(lifetime);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 719 */     if (log.isLoggable(Level.FINE)) {
/* 720 */       log.log(Level.FINE, LogStringsMessages.WSSC_1013_CREATED_SCT_RST_RENEW(WSTrustUtil.elemToString((BaseSTSRequest)rst, wsTrustVer)));
/*     */     }
/* 722 */     return rst;
/*     */   }
/*     */   
/*     */   private RequestSecurityToken createRequestSecurityTokenForCancel(SCTokenConfiguration sctConfig, IssuedTokenContext ctx) throws WSSecureConversationException {
/* 726 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 727 */     WSTrustVersion wsTrustVer = null;
/* 728 */     if (wsscVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512")) {
/* 729 */       wsTrustVer = WSTrustVersion.WS_TRUST_13;
/*     */     } else {
/* 731 */       wsTrustVer = WSTrustVersion.WS_TRUST_10;
/*     */     } 
/* 733 */     WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(wsTrustVer);
/* 734 */     URI requestType = URI.create(wsTrustVer.getCancelRequestTypeURI());
/*     */     
/* 736 */     CancelTarget target = eleFac.createCancelTarget((SecurityTokenReference)ctx.getUnAttachedSecurityTokenReference());
/* 737 */     RequestSecurityToken rst = eleFac.createRSTForCancel(requestType, target);
/*     */     
/* 739 */     if (log.isLoggable(Level.FINE)) {
/* 740 */       log.log(Level.FINE, LogStringsMessages.WSSC_1015_CREATED_SCT_RST_CANCEL(WSTrustUtil.elemToString((BaseSTSRequest)rst, wsTrustVer)));
/*     */     }
/*     */     
/* 743 */     return rst;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processRequestSecurityTokenResponse(SCTokenConfiguration sctConfig, BaseSTSRequest rst, BaseSTSResponse rstr, IssuedTokenContext context) throws WSSecureConversationException {
/* 772 */     WSSCVersion wsscVer = WSSCVersion.getInstance(sctConfig.getProtocol());
/* 773 */     WSSCClientContract contract = WSSCFactory.newWSSCClientContract();
/* 774 */     if (wsscVer.getNamespaceURI().equals(WSSCVersion.WSSC_13.getNamespaceURI())) {
/*     */       try {
/* 776 */         contract.handleRSTRC((RequestSecurityToken)rst, (RequestSecurityTokenResponseCollection)rstr, context);
/* 777 */       } catch (Exception ex) {
/* 778 */         contract.handleRSTR((RequestSecurityToken)rst, (RequestSecurityTokenResponse)rstr, context);
/*     */       } 
/*     */     } else {
/* 781 */       contract.handleRSTR((RequestSecurityToken)rst, (RequestSecurityTokenResponse)rstr, context);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String printMessageAsString(Message message) {
/* 786 */     StringWriter writer = new StringWriter();
/* 787 */     XMLOutputFactory factory = XMLOutputFactory.newInstance();
/*     */     try {
/* 789 */       XMLStreamWriter streamWriter = factory.createXMLStreamWriter(writer);
/* 790 */       message.writeTo(streamWriter);
/* 791 */       streamWriter.flush();
/* 792 */       return writer.toString();
/* 793 */     } catch (XMLStreamException ex) {
/* 794 */       log.log(Level.SEVERE, LogStringsMessages.WSSC_0025_PROBLEM_PRINTING_MSG(), ex);
/*     */       
/* 796 */       throw new RuntimeException(LogStringsMessages.WSSC_0025_PROBLEM_PRINTING_MSG(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet addAddressingHeaders(Packet packet, WSDLPort wsdlPort, WSBinding binding, String action, AddressingVersion addVer) throws WSSecureConversationException {
/* 801 */     HeaderList list = packet.getMessage().getHeaders();
/* 802 */     list.fillRequestAddressingHeaders(packet, addVer, binding.getSOAPVersion(), false, action);
/*     */     
/* 804 */     return packet;
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
/* 816 */     Set<String> set = packet.invocationProperties.keySet();
/* 817 */     for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
/* 818 */       String key = it.next();
/* 819 */       requestPacket.invocationProperties.put(key, packet.invocationProperties.get(key));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createRenewSignaturePolicy(Token token) {
/* 824 */     renewSignaturePolicy = new SignaturePolicy();
/*     */     
/* 826 */     renewSignaturePolicy.setUUID("_99");
/* 827 */     SecurityPolicyVersion spVersion = token.getSecurityPolicyVersion();
/* 828 */     SecureConversationTokenKeyBinding sct = new SecureConversationTokenKeyBinding();
/* 829 */     SecureConversationToken scToken = (SecureConversationToken)token;
/* 830 */     if (scToken.isRequireDerivedKeys()) {
/* 831 */       DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 832 */       dtKB.setOriginalKeyBinding((WSSPolicy)sct);
/* 833 */       renewSignaturePolicy.setKeyBinding((MLSPolicy)dtKB);
/* 834 */       dtKB.setUUID("_100");
/*     */     } else {
/* 836 */       renewSignaturePolicy.setKeyBinding((MLSPolicy)sct);
/*     */     } 
/* 838 */     if (spVersion == SecurityPolicyVersion.SECURITYPOLICY200507) {
/* 839 */       sct.setIncludeToken(token.getIncludeToken());
/*     */     } else {
/*     */       
/* 842 */       sct.setIncludeToken(SecurityPolicyVersion.SECURITYPOLICY200507.includeTokenAlwaysToRecipient);
/*     */     } 
/* 844 */     sct.setUUID(token.getTokenId());
/*     */     
/* 846 */     AssertionSet assertions = getAssertions(scToken);
/* 847 */     for (PolicyAssertion policyAssertion : assertions) {
/* 848 */       if (PolicyUtil.isBinding(policyAssertion, spVersion)) {
/* 849 */         binding = (Binding)policyAssertion;
/*     */       }
/*     */     } 
/*     */     
/* 853 */     SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)renewSignaturePolicy.getFeatureBinding();
/* 854 */     AlgorithmSuite as = binding.getAlgorithmSuite();
/* 855 */     SecurityPolicyUtil.setCanonicalizationMethod(spFB, as);
/*     */   }
/*     */   
/*     */   public SignaturePolicy getRenewSignaturePolicy() {
/* 859 */     return renewSignaturePolicy;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/* 863 */     return binding.getAlgorithmSuite();
/*     */   }
/*     */   
/*     */   private SecurityPolicyVersion getSPVersion(PolicyAssertion pa) {
/* 867 */     String nsUri = pa.getName().getNamespaceURI();
/*     */     
/* 869 */     SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */     
/* 871 */     if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(nsUri)) {
/* 872 */       spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */     }
/* 874 */     return spVersion;
/*     */   }
/*     */   
/*     */   private Engine getFiberEngine() {
/* 878 */     if (this.fiberEngine == null) {
/* 879 */       this.fiberEngine = (Fiber.current()).owner;
/*     */     }
/* 881 */     return this.fiberEngine;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\secconv\WSSCPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */