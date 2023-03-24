/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.rx.mc.api.McProtocolVersion;
/*     */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SCTokenWrapper;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.secconv.WSSCVersion;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.impl.PolicyResolver;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.policy.PolicyAlternatives;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.jaxws.impl.TubeConfiguration;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AlternativesBasedPolicyResolver
/*     */   implements PolicyResolver
/*     */ {
/*  94 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private WSDLBoundOperation cachedOperation = null;
/*     */   
/* 101 */   private AddressingVersion addVer = null;
/* 102 */   private RmProtocolVersion rmVer = null;
/* 103 */   private McProtocolVersion mcVer = null;
/* 104 */   private TubeConfiguration tubeConfig = null;
/*     */   
/*     */   private boolean isClient = false;
/*     */   private boolean isSCMessage = false;
/* 108 */   private String action = "";
/* 109 */   private WSTrustVersion wstVer = WSTrustVersion.WS_TRUST_10;
/* 110 */   private WSSCVersion wsscVer = WSSCVersion.WSSC_10;
/* 111 */   private List<PolicyAlternativeHolder> policyAlternatives = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlternativesBasedPolicyResolver(List<PolicyAlternativeHolder> alternatives, WSDLBoundOperation cachedOperation, TubeConfiguration tubeConfig, AddressingVersion addVer, boolean client, RmProtocolVersion rmVer, McProtocolVersion mcVer) {
/* 120 */     this.policyAlternatives = alternatives;
/* 121 */     this.cachedOperation = cachedOperation;
/* 122 */     this.tubeConfig = tubeConfig;
/* 123 */     this.addVer = addVer;
/* 124 */     this.isClient = client;
/* 125 */     this.rmVer = rmVer;
/* 126 */     this.mcVer = mcVer;
/*     */   }
/*     */   
/*     */   public SecurityPolicy resolvePolicy(ProcessingContext ctx) {
/* 130 */     Message msg = null;
/* 131 */     SOAPMessage soapMsg = null;
/* 132 */     if (ctx instanceof JAXBFilterProcessingContext) {
/* 133 */       msg = ((JAXBFilterProcessingContext)ctx).getJAXWSMessage();
/*     */     } else {
/* 135 */       soapMsg = ctx.getSOAPMessage();
/* 136 */       msg = Messages.create(soapMsg);
/*     */     } 
/* 138 */     if (((ProcessingContextImpl)ctx).getSecurityPolicyVersion().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)) {
/*     */       
/* 140 */       this.wstVer = WSTrustVersion.WS_TRUST_13;
/* 141 */       this.wsscVer = WSSCVersion.WSSC_13;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     this.action = getAction(msg);
/* 147 */     if (isRMMessage() || isMCMessage()) {
/* 148 */       return getProtocolPolicy("RM");
/*     */     }
/*     */     
/* 151 */     if (isSCCancel()) {
/* 152 */       return getProtocolPolicy("SC-CANCEL");
/*     */     }
/* 154 */     SecurityPolicy mp = null;
/* 155 */     this.isSCMessage = isSCMessage();
/* 156 */     if (this.isSCMessage) {
/* 157 */       Token scToken = (Token)getInBoundSCP();
/* 158 */       return getInboundXWSBootstrapPolicy(scToken);
/*     */     } 
/*     */     
/* 161 */     if (msg.isFault()) {
/* 162 */       if (soapMsg == null) {
/*     */         try {
/* 164 */           soapMsg = msg.readAsSOAPMessage();
/* 165 */         } catch (SOAPException ex) {}
/*     */       }
/*     */ 
/*     */       
/* 169 */       mp = getInboundFaultPolicy(soapMsg);
/*     */     } else {
/* 171 */       mp = getInboundXWSSecurityPolicy(msg);
/*     */     } 
/*     */     
/* 174 */     if (mp == null) {
/* 175 */       return (SecurityPolicy)new MessagePolicy();
/*     */     }
/* 177 */     return mp;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PolicyAssertion getInBoundSCP() {
/* 182 */     SecurityPolicyHolder sph = null;
/* 183 */     Collection coll = new ArrayList();
/* 184 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 185 */       coll.addAll(p.getInMessagePolicyMap().values());
/*     */     }
/*     */     
/* 188 */     Iterator<SecurityPolicyHolder> itr = coll.iterator();
/* 189 */     while (itr.hasNext()) {
/* 190 */       SecurityPolicyHolder ph = itr.next();
/* 191 */       if (ph != null) {
/* 192 */         sph = ph;
/*     */         break;
/*     */       } 
/*     */     } 
/* 196 */     if (sph == null) {
/* 197 */       return null;
/*     */     }
/* 199 */     List<PolicyAssertion> policies = sph.getSecureConversationTokens();
/* 200 */     if (!policies.isEmpty()) {
/* 201 */       return policies.get(0);
/*     */     }
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private SecurityPolicy getInboundXWSSecurityPolicy(Message msg) {
/* 208 */     SecurityPolicy mp = null;
/*     */ 
/*     */     
/* 211 */     WSDLBoundOperation operation = null;
/* 212 */     if (this.cachedOperation != null) {
/* 213 */       operation = this.cachedOperation;
/*     */     } else {
/* 215 */       operation = msg.getOperation(this.tubeConfig.getWSDLPort());
/* 216 */       if (operation == null) {
/* 217 */         operation = getWSDLOpFromAction();
/*     */       }
/*     */     } 
/*     */     
/* 221 */     List<MessagePolicy> mps = new ArrayList<MessagePolicy>();
/* 222 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 223 */       SecurityPolicyHolder sph = p.getInMessagePolicyMap().get(operation);
/*     */       
/* 225 */       if (sph == null && (isTrustMessage() || this.isSCMessage)) {
/* 226 */         operation = getWSDLOpFromAction();
/* 227 */         sph = p.getInMessagePolicyMap().get(operation);
/*     */       } 
/* 229 */       if (sph != null) {
/* 230 */         mps.add(cloneWithId(sph.getMessagePolicy(), p.getId()));
/*     */       }
/*     */     } 
/* 233 */     return (SecurityPolicy)new PolicyAlternatives(mps);
/*     */   }
/*     */ 
/*     */   
/*     */   private SecurityPolicy getInboundFaultPolicy(SOAPMessage msg) {
/* 238 */     if (this.cachedOperation != null) {
/* 239 */       List<MessagePolicy> mps = new ArrayList<MessagePolicy>();
/* 240 */       for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 241 */         WSDLOperation operation = this.cachedOperation.getOperation();
/*     */         try {
/* 243 */           SOAPBody body = msg.getSOAPBody();
/* 244 */           NodeList nodes = body.getElementsByTagName("detail");
/* 245 */           if (nodes.getLength() == 0) {
/* 246 */             nodes = body.getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Detail");
/*     */           }
/* 248 */           if (nodes.getLength() > 0) {
/* 249 */             QName faultDetail; Node node = nodes.item(0);
/* 250 */             Node faultNode = node.getFirstChild();
/* 251 */             while (faultNode != null && faultNode.getNodeType() != 1) {
/* 252 */               faultNode = faultNode.getNextSibling();
/*     */             }
/* 254 */             if (faultNode == null) {
/* 255 */               return (SecurityPolicy)new MessagePolicy();
/*     */             }
/* 257 */             String uri = faultNode.getNamespaceURI();
/*     */             
/* 259 */             if (uri != null && uri.length() > 0) {
/* 260 */               faultDetail = new QName(uri, faultNode.getLocalName());
/*     */             } else {
/* 262 */               faultDetail = new QName(faultNode.getLocalName());
/*     */             } 
/* 264 */             WSDLFault fault = operation.getFault(faultDetail);
/* 265 */             SecurityPolicyHolder sph = p.getInMessagePolicyMap().get(this.cachedOperation);
/* 266 */             SecurityPolicyHolder faultPolicyHolder = sph.getFaultPolicy(fault);
/* 267 */             if (faultPolicyHolder != null) {
/* 268 */               mps.add(cloneWithId(faultPolicyHolder.getMessagePolicy(), p.getId()));
/*     */             }
/*     */           } 
/* 271 */         } catch (SOAPException sx) {
/*     */           
/* 273 */           log.log(Level.WARNING, LogStringsMessages.WSITPVD_0065_ERROR_RESOLVING_ALTERNATIVES(), sx);
/*     */         } 
/*     */       } 
/* 276 */       return (SecurityPolicy)new PolicyAlternatives(mps);
/*     */     } 
/* 278 */     return (SecurityPolicy)new MessagePolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTrustMessage() {
/* 283 */     if (this.wstVer.getIssueRequestAction().equals(this.action) || this.wstVer.getIssueResponseAction().equals(this.action))
/*     */     {
/* 285 */       return true;
/*     */     }
/* 287 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRMMessage() {
/* 292 */     return this.rmVer.isProtocolAction(this.action);
/*     */   }
/*     */   
/*     */   private boolean isMCMessage() {
/* 296 */     return this.mcVer.isProtocolAction(this.action);
/*     */   }
/*     */   
/*     */   private String getAction(Message msg) {
/* 300 */     if (this.addVer != null) {
/* 301 */       HeaderList hl = msg.getHeaders();
/* 302 */       String retVal = hl.getAction(this.addVer, this.tubeConfig.getBinding().getSOAPVersion());
/* 303 */       return retVal;
/*     */     } 
/* 305 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   private SecurityPolicy getInboundXWSBootstrapPolicy(Token scAssertion) {
/* 310 */     if (scAssertion == null) {
/* 311 */       return null;
/*     */     }
/* 313 */     return (SecurityPolicy)((SCTokenWrapper)scAssertion).getMessagePolicy();
/*     */   }
/*     */   
/*     */   private boolean isSCMessage() {
/* 317 */     if (this.wsscVer.getSCTRequestAction().equals(this.action) || this.wsscVer.getSCTResponseAction().equals(this.action) || this.wsscVer.getSCTRenewRequestAction().equals(this.action) || this.wsscVer.getSCTRenewResponseAction().equals(this.action))
/*     */     {
/*     */ 
/*     */       
/* 321 */       return true;
/*     */     }
/* 323 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSCCancel() {
/* 328 */     if (this.wsscVer.getSCTCancelResponseAction().equals(this.action) || this.wsscVer.getSCTCancelRequestAction().equals(this.action))
/*     */     {
/* 330 */       return true;
/*     */     }
/* 332 */     return false;
/*     */   }
/*     */   
/*     */   private String getAction(WSDLOperation operation) {
/* 336 */     if (!this.isClient) {
/* 337 */       return operation.getInput().getAction();
/*     */     }
/* 339 */     return operation.getOutput().getAction();
/*     */   }
/*     */ 
/*     */   
/*     */   private WSDLBoundOperation getWSDLOpFromAction() {
/* 344 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 345 */       Set<WSDLBoundOperation> keys = p.getInMessagePolicyMap().keySet();
/* 346 */       for (WSDLBoundOperation wbo : keys) {
/* 347 */         WSDLOperation wo = wbo.getOperation();
/*     */         
/* 349 */         String confAction = getAction(wo);
/* 350 */         if (confAction != null && confAction.equals(this.action)) {
/* 351 */           return wbo;
/*     */         }
/*     */       } 
/*     */     } 
/* 355 */     return null;
/*     */   }
/*     */   
/*     */   private SecurityPolicy getProtocolPolicy(String protocol) {
/* 359 */     List<MessagePolicy> mps = new ArrayList<MessagePolicy>();
/* 360 */     for (PolicyAlternativeHolder policyAlternativeHolder : this.policyAlternatives) {
/* 361 */       SecurityPolicyHolder sph = policyAlternativeHolder.getInProtocolPM().get(protocol);
/* 362 */       if (sph != null) {
/* 363 */         mps.add(cloneWithId(sph.getMessagePolicy(), policyAlternativeHolder.getId()));
/*     */       }
/*     */     } 
/* 366 */     PolicyAlternatives p = new PolicyAlternatives(mps);
/* 367 */     return (SecurityPolicy)p;
/*     */   }
/*     */   
/*     */   private MessagePolicy cloneWithId(MessagePolicy toClone, String id) {
/* 371 */     if (toClone == null) {
/* 372 */       return null;
/*     */     }
/*     */     try {
/* 375 */       MessagePolicy copy = new MessagePolicy();
/* 376 */       copy.setPolicyAlternativeId(id);
/* 377 */       Iterator<SecurityPolicy> it = toClone.iterator();
/* 378 */       while (it.hasNext()) {
/* 379 */         copy.append(it.next());
/*     */       }
/* 381 */       return copy;
/* 382 */     } catch (Exception ex) {
/* 383 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\AlternativesBasedPolicyResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */