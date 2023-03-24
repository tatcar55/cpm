/*     */ package com.sun.xml.wss.jaxws.impl;
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
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class PolicyResolverImpl
/*     */   implements PolicyResolver
/*     */ {
/*  86 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.jaxws.impl", "com.sun.xml.wss.jaxws.impl.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private WSDLBoundOperation cachedOperation = null;
/*  92 */   private HashMap<WSDLBoundOperation, SecurityPolicyHolder> inMessagePolicyMap = null;
/*  93 */   private HashMap<String, SecurityPolicyHolder> inProtocolPM = null;
/*     */   
/*  95 */   private AddressingVersion addVer = null;
/*  96 */   private RmProtocolVersion rmVer = null;
/*  97 */   private McProtocolVersion mcVer = null;
/*  98 */   private TubeConfiguration tubeConfig = null;
/*     */   
/*     */   private boolean isClient = false;
/*     */   private boolean isSCMessage = false;
/* 102 */   private String action = "";
/* 103 */   private WSTrustVersion wstVer = WSTrustVersion.WS_TRUST_10;
/* 104 */   private WSSCVersion wsscVer = WSSCVersion.WSSC_10;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyResolverImpl(HashMap<WSDLBoundOperation, SecurityPolicyHolder> inMessagePolicyMap, HashMap<String, SecurityPolicyHolder> ip, WSDLBoundOperation cachedOperation, TubeConfiguration tubeConfig, AddressingVersion addVer, boolean isClient, RmProtocolVersion rmVer, McProtocolVersion mcVer) {
/* 110 */     this.inMessagePolicyMap = inMessagePolicyMap;
/* 111 */     this.inProtocolPM = ip;
/* 112 */     this.cachedOperation = cachedOperation;
/* 113 */     this.tubeConfig = tubeConfig;
/* 114 */     this.addVer = addVer;
/* 115 */     this.isClient = isClient;
/* 116 */     this.rmVer = rmVer;
/* 117 */     this.mcVer = mcVer;
/*     */   }
/*     */   
/*     */   public SecurityPolicy resolvePolicy(ProcessingContext ctx) {
/* 121 */     Message msg = null;
/* 122 */     SOAPMessage soapMsg = null;
/* 123 */     if (ctx instanceof JAXBFilterProcessingContext) {
/* 124 */       msg = ((JAXBFilterProcessingContext)ctx).getJAXWSMessage();
/*     */     } else {
/* 126 */       soapMsg = ctx.getSOAPMessage();
/* 127 */       msg = Messages.create(soapMsg);
/*     */     } 
/* 129 */     if (((ProcessingContextImpl)ctx).getSecurityPolicyVersion().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)) {
/*     */       
/* 131 */       this.wstVer = WSTrustVersion.WS_TRUST_13;
/* 132 */       this.wsscVer = WSSCVersion.WSSC_13;
/*     */     } 
/*     */     
/* 135 */     SecurityPolicy mp = null;
/*     */     
/* 137 */     this.action = getAction(msg);
/* 138 */     if (isRMMessage() || isMCMessage()) {
/* 139 */       SecurityPolicyHolder holder = this.inProtocolPM.get("RM");
/* 140 */       return (SecurityPolicy)holder.getMessagePolicy();
/*     */     } 
/*     */     
/* 143 */     if (isSCCancel()) {
/* 144 */       SecurityPolicyHolder holder = this.inProtocolPM.get("SC-CANCEL");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 149 */       return (SecurityPolicy)holder.getMessagePolicy();
/*     */     } 
/* 151 */     this.isSCMessage = isSCMessage();
/* 152 */     if (this.isSCMessage) {
/* 153 */       Token scToken = (Token)getInBoundSCP();
/* 154 */       return getInboundXWSBootstrapPolicy(scToken);
/*     */     } 
/*     */     
/* 157 */     if (msg.isFault()) {
/* 158 */       if (soapMsg == null) {
/*     */         try {
/* 160 */           soapMsg = msg.readAsSOAPMessage();
/* 161 */         } catch (SOAPException ex) {}
/*     */       }
/*     */ 
/*     */       
/* 165 */       mp = getInboundFaultPolicy(soapMsg);
/*     */     } else {
/* 167 */       mp = getInboundXWSSecurityPolicy(msg);
/*     */     } 
/*     */     
/* 170 */     if (mp == null) {
/* 171 */       return (SecurityPolicy)new MessagePolicy();
/*     */     }
/* 173 */     return mp;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PolicyAssertion getInBoundSCP() {
/* 178 */     SecurityPolicyHolder sph = null;
/* 179 */     Collection<SecurityPolicyHolder> coll = this.inMessagePolicyMap.values();
/* 180 */     Iterator<SecurityPolicyHolder> itr = coll.iterator();
/*     */     
/* 182 */     while (itr.hasNext()) {
/* 183 */       SecurityPolicyHolder ph = itr.next();
/* 184 */       if (ph != null) {
/* 185 */         sph = ph;
/*     */         break;
/*     */       } 
/*     */     } 
/* 189 */     if (sph == null) {
/* 190 */       return null;
/*     */     }
/* 192 */     List<PolicyAssertion> policies = sph.getSecureConversationTokens();
/* 193 */     if (!policies.isEmpty()) {
/* 194 */       return policies.get(0);
/*     */     }
/* 196 */     return null;
/*     */   }
/*     */   
/*     */   private SecurityPolicy getInboundXWSSecurityPolicy(Message msg) {
/* 200 */     SecurityPolicy mp = null;
/*     */ 
/*     */     
/* 203 */     WSDLBoundOperation operation = null;
/* 204 */     if (this.cachedOperation != null) {
/* 205 */       operation = this.cachedOperation;
/*     */     } else {
/* 207 */       operation = msg.getOperation(this.tubeConfig.getWSDLPort());
/* 208 */       if (operation == null) {
/* 209 */         operation = getWSDLOpFromAction();
/*     */       }
/*     */     } 
/*     */     
/* 213 */     SecurityPolicyHolder sph = this.inMessagePolicyMap.get(operation);
/*     */     
/* 215 */     if (sph == null && (isTrustMessage() || this.isSCMessage)) {
/* 216 */       operation = getWSDLOpFromAction();
/* 217 */       sph = this.inMessagePolicyMap.get(operation);
/*     */     } 
/* 219 */     if (sph == null) {
/* 220 */       return null;
/*     */     }
/*     */     
/* 223 */     return (SecurityPolicy)sph.getMessagePolicy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SecurityPolicy getInboundFaultPolicy(SOAPMessage msg) {
/* 229 */     if (this.cachedOperation != null) {
/* 230 */       WSDLOperation operation = this.cachedOperation.getOperation();
/* 231 */       SecurityPolicyHolder sph = this.inMessagePolicyMap.get(this.cachedOperation);
/*     */       try {
/* 233 */         SOAPBody body = msg.getSOAPBody();
/* 234 */         NodeList nodes = body.getElementsByTagName("detail");
/* 235 */         if (nodes.getLength() == 0) {
/* 236 */           nodes = body.getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Detail");
/*     */         }
/* 238 */         if (nodes.getLength() == 0) {
/* 239 */           nodes = body.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "detail");
/*     */         }
/* 241 */         if (nodes.getLength() == 0) {
/* 242 */           return (SecurityPolicy)sph.getMessagePolicy();
/*     */         }
/* 244 */         if (nodes.getLength() > 0) {
/* 245 */           QName faultDetail; Node node = nodes.item(0);
/* 246 */           Node faultNode = node.getFirstChild();
/* 247 */           while (faultNode != null && faultNode.getNodeType() != 1) {
/* 248 */             faultNode = faultNode.getNextSibling();
/*     */           }
/* 250 */           if (faultNode == null) {
/* 251 */             return (SecurityPolicy)new MessagePolicy();
/*     */           }
/* 253 */           String uri = faultNode.getNamespaceURI();
/*     */           
/* 255 */           if (uri != null && uri.length() > 0) {
/* 256 */             faultDetail = new QName(uri, faultNode.getLocalName());
/*     */           } else {
/* 258 */             faultDetail = new QName(faultNode.getLocalName());
/*     */           } 
/* 260 */           WSDLFault fault = operation.getFault(faultDetail);
/* 261 */           SecurityPolicyHolder faultPolicyHolder = sph.getFaultPolicy(fault);
/* 262 */           return (SecurityPolicy)((faultPolicyHolder == null) ? new MessagePolicy() : faultPolicyHolder.getMessagePolicy());
/*     */         }
/*     */       
/* 265 */       } catch (SOAPException sx) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 270 */     return (SecurityPolicy)new MessagePolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTrustMessage() {
/* 275 */     if (this.wstVer.getIssueRequestAction().equals(this.action) || this.wstVer.getIssueResponseAction().equals(this.action))
/*     */     {
/* 277 */       return true;
/*     */     }
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRMMessage() {
/* 284 */     return this.rmVer.isProtocolAction(this.action);
/*     */   }
/*     */   
/*     */   private boolean isMCMessage() {
/* 288 */     return this.mcVer.isProtocolAction(this.action);
/*     */   }
/*     */   
/*     */   private String getAction(Message msg) {
/* 292 */     if (this.addVer != null) {
/* 293 */       HeaderList hl = msg.getHeaders();
/* 294 */       String retVal = hl.getAction(this.addVer, this.tubeConfig.getBinding().getSOAPVersion());
/* 295 */       return retVal;
/*     */     } 
/* 297 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   private SecurityPolicy getInboundXWSBootstrapPolicy(Token scAssertion) {
/* 302 */     if (scAssertion == null) {
/* 303 */       return null;
/*     */     }
/* 305 */     return (SecurityPolicy)((SCTokenWrapper)scAssertion).getMessagePolicy();
/*     */   }
/*     */   
/*     */   private boolean isSCMessage() {
/* 309 */     if (this.wsscVer.getSCTRequestAction().equals(this.action) || this.wsscVer.getSCTResponseAction().equals(this.action) || this.wsscVer.getSCTRenewRequestAction().equals(this.action) || this.wsscVer.getSCTRenewResponseAction().equals(this.action))
/*     */     {
/*     */ 
/*     */       
/* 313 */       return true;
/*     */     }
/* 315 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSCCancel() {
/* 320 */     if (this.wsscVer.getSCTCancelResponseAction().equals(this.action) || this.wsscVer.getSCTCancelRequestAction().equals(this.action))
/*     */     {
/* 322 */       return true;
/*     */     }
/* 324 */     return false;
/*     */   }
/*     */   
/*     */   private String getAction(WSDLOperation operation) {
/* 328 */     if (!this.isClient) {
/* 329 */       return operation.getInput().getAction();
/*     */     }
/* 331 */     return operation.getOutput().getAction();
/*     */   }
/*     */ 
/*     */   
/*     */   private WSDLBoundOperation getWSDLOpFromAction() {
/* 336 */     Set<WSDLBoundOperation> keys = this.inMessagePolicyMap.keySet();
/* 337 */     for (WSDLBoundOperation wbo : keys) {
/* 338 */       WSDLOperation wo = wbo.getOperation();
/*     */       
/* 340 */       String confAction = getAction(wo);
/* 341 */       if (confAction != null && confAction.equals(this.action)) {
/* 342 */         return wbo;
/*     */       }
/*     */     } 
/* 345 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\PolicyResolverImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */