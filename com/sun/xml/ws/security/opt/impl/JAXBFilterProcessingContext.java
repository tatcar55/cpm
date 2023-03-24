/*     */ package com.sun.xml.ws.security.opt.impl;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityContext;
/*     */ import com.sun.xml.ws.security.opt.impl.message.MessageWrapper;
/*     */ import com.sun.xml.ws.security.opt.impl.message.SecuredMessage;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.wss.BasicSecurityProfile;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageLayout;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.security.Key;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBFilterProcessingContext
/*     */   extends FilterProcessingContext
/*     */ {
/*  85 */   protected static final Logger logger = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */   
/*  87 */   private int uid = 5001;
/*     */   private boolean isBSP = false;
/*  89 */   private Message pvMsg = null;
/*     */ 
/*     */ 
/*     */   
/*  93 */   SecuredMessage securedMessage = null;
/*     */   
/*  95 */   SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/*  97 */   SecurityHeader secHeader = null;
/*     */   
/*  99 */   private SecurityContext securityContext = null;
/*     */   
/* 101 */   private Message message = null;
/*     */   private boolean isOneWay = false;
/*     */   private boolean isSAMLEK = false;
/* 104 */   private AddressingVersion addrVer = null;
/* 105 */   private NamespaceContextEx nsc = null;
/* 106 */   private XMLStreamBuffer xmlBuffer = null;
/*     */   private boolean isSSL = false;
/* 108 */   private HashMap<String, Key> currentSecretMap = new HashMap<String, Key>();
/*     */   private boolean disablePayloadBuffering = false;
/*     */   private boolean disbaleIncPrefix = false;
/*     */   private boolean encHeaderContent = false;
/*     */   private boolean allowMissingTimestamp = false;
/*     */   private boolean mustUnderstandValue = true;
/* 114 */   private BasicSecurityProfile bspContext = null;
/* 115 */   private String edId = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBFilterProcessingContext() {
/* 120 */     this.bspContext = new BasicSecurityProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBFilterProcessingContext(ProcessingContext context) throws XWSSecurityException {
/* 126 */     this.bspContext = new BasicSecurityProfile();
/*     */   }
/*     */ 
/*     */   
/*     */   public JAXBFilterProcessingContext(Map invocationProps) {
/* 131 */     this.properties = invocationProps;
/* 132 */     this.bspContext = new BasicSecurityProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBFilterProcessingContext(String messageIdentifier, SecurityPolicy securityPolicy, Message message, SOAPVersion soapVersion) throws XWSSecurityException {
/* 141 */     setSecurityPolicy(securityPolicy);
/* 142 */     setMessageIdentifier(messageIdentifier);
/* 143 */     this.securedMessage = new SecuredMessage(message, getSecurityHeader(), soapVersion);
/* 144 */     this.soapVersion = soapVersion;
/* 145 */     this.securedMessage.setRootElements(getNamespaceContext());
/* 146 */     this.bspContext = new BasicSecurityProfile();
/*     */   }
/*     */   
/*     */   public SecuredMessage getSecuredMessage() {
/* 150 */     return this.securedMessage;
/*     */   }
/*     */   
/*     */   public void isOneWayMessage(boolean value) {
/* 154 */     this.isOneWay = value;
/*     */   }
/*     */   
/*     */   public void setEdIdforEh(String id) {
/* 158 */     this.edId = id;
/*     */   }
/*     */   public String getEdIdforEh() {
/* 161 */     return this.edId;
/*     */   }
/*     */   public void setJAXWSMessage(Message jaxWsMessage, SOAPVersion soapVersion) {
/* 164 */     QName secQName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse");
/*     */     
/* 166 */     this.secHeader = (SecurityHeader)jaxWsMessage.getHeaders().get(secQName, false);
/* 167 */     this.securedMessage = new SecuredMessage(jaxWsMessage, getSecurityHeader(), soapVersion);
/* 168 */     this.soapVersion = soapVersion;
/* 169 */     this.securedMessage.setRootElements(getNamespaceContext());
/*     */   }
/*     */   
/*     */   public void setMessage(Message message) {
/* 173 */     this.message = message;
/*     */   }
/*     */   
/*     */   public Message getJAXWSMessage() {
/* 177 */     if (this.message != null) {
/* 178 */       return this.message;
/*     */     }
/* 180 */     return (Message)new MessageWrapper(this.securedMessage, this.isOneWay);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 184 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public void setSOAPVersion(SOAPVersion sv) {
/* 188 */     this.soapVersion = sv;
/*     */   }
/*     */   
/*     */   public boolean isSOAP12() {
/* 192 */     return (this.soapVersion == SOAPVersion.SOAP_12);
/*     */   }
/*     */   
/*     */   public SecurityHeader getSecurityHeader() {
/* 196 */     if (this.secHeader == null) {
/* 197 */       this.secHeader = (new WSSElementFactory(this.soapVersion)).createSecurityHeader(getLayout(), this.soapVersion.nsUri, this.mustUnderstandValue);
/*     */     }
/* 199 */     return this.secHeader;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SecurableSoapMessage getSecureMessage() {
/* 204 */     return null;
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
/*     */   protected void setSecureMessage(SecurableSoapMessage msg) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSOAPMessage(SOAPMessage message) throws XWSSecurityException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage getSOAPMessage() {
/* 234 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(ProcessingContext ctxx1, ProcessingContext ctxx2) throws XWSSecurityException {
/* 240 */     if (ctxx2 instanceof JAXBFilterProcessingContext) {
/* 241 */       JAXBFilterProcessingContext ctx1 = (JAXBFilterProcessingContext)ctxx1;
/* 242 */       JAXBFilterProcessingContext ctx2 = (JAXBFilterProcessingContext)ctxx2;
/* 243 */       super.copy((ProcessingContext)ctx1, (ProcessingContext)ctx2);
/* 244 */       ctx1.setJAXWSMessage(ctx2.getJAXWSMessage(), ctx2.soapVersion);
/*     */     } else {
/* 246 */       super.copy(ctxx1, ctxx2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized String generateID() {
/* 251 */     this.uid++;
/* 252 */     return "_" + Integer.toString(this.uid);
/*     */   }
/*     */   
/*     */   public SecurityContext getSecurityContext() {
/* 256 */     return this.securityContext;
/*     */   }
/*     */   
/*     */   public void setSecurityContext(SecurityContext securityContext) {
/* 260 */     this.securityContext = securityContext;
/*     */   }
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/* 264 */     if (this.nsc == null) {
/* 265 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 266 */         this.nsc = (NamespaceContextEx)new NamespaceContextEx(false);
/*     */       } else {
/* 268 */         this.nsc = (NamespaceContextEx)new NamespaceContextEx(true);
/*     */       } 
/* 270 */       if (getWSSAssertion() != null) {
/* 271 */         if (getWSSAssertion().getType().equals("1.1")) {
/* 272 */           ((NamespaceContextEx)this.nsc).addWSS11NS();
/* 273 */           ((NamespaceContextEx)this.nsc).addWSSNS();
/*     */         } else {
/* 275 */           ((NamespaceContextEx)this.nsc).addWSSNS();
/*     */         } 
/*     */       } else {
/* 278 */         ((NamespaceContextEx)this.nsc).addWSSNS();
/*     */       } 
/*     */       
/* 281 */       ((NamespaceContextEx)this.nsc).addXSDNS();
/*     */     } 
/* 283 */     return this.nsc;
/*     */   }
/*     */   
/*     */   public boolean isSAMLEK() {
/* 287 */     return this.isSAMLEK;
/*     */   }
/*     */   
/*     */   public void isSAMLEK(boolean isSAMLEK) {
/* 291 */     this.isSAMLEK = isSAMLEK;
/*     */   }
/*     */   
/*     */   public AddressingVersion getAddressingVersion() {
/* 295 */     return this.addrVer;
/*     */   }
/*     */   
/*     */   public void setAddressingVersion(AddressingVersion addrVer) {
/* 299 */     this.addrVer = addrVer;
/*     */   }
/*     */   
/*     */   public void setCurrentBuffer(XMLStreamBuffer buffer) {
/* 303 */     this.xmlBuffer = buffer;
/*     */   }
/*     */   
/*     */   public XMLStreamBuffer getCurrentBuffer() {
/* 307 */     return this.xmlBuffer;
/*     */   }
/*     */   
/*     */   public void setSecure(boolean value) {
/* 311 */     this.isSSL = value;
/*     */   }
/*     */   
/*     */   public boolean isSecure() {
/* 315 */     return this.isSSL;
/*     */   }
/*     */   
/*     */   public int getLayout() {
/* 319 */     MessagePolicy mp = (MessagePolicy)getSecurityPolicy();
/* 320 */     if (mp != null) {
/* 321 */       if (MessageLayout.Strict == mp.getLayout())
/* 322 */         return 1; 
/* 323 */       if (MessageLayout.Lax == mp.getLayout())
/* 324 */         return 0; 
/* 325 */       if (MessageLayout.LaxTsFirst == mp.getLayout())
/* 326 */         return 2; 
/* 327 */       if (MessageLayout.LaxTsLast == mp.getLayout()) {
/* 328 */         return 3;
/*     */       }
/*     */     } 
/* 331 */     return 1;
/*     */   }
/*     */   
/*     */   public void addToCurrentSecretMap(String ekId, Key key) {
/* 335 */     this.currentSecretMap.put(ekId, key);
/*     */   }
/*     */   
/*     */   public Key getCurrentSecretFromMap(String ekId) {
/* 339 */     return this.currentSecretMap.get(ekId);
/*     */   }
/*     */   
/*     */   public boolean getDisablePayloadBuffering() {
/* 343 */     return this.disablePayloadBuffering;
/*     */   }
/*     */   
/*     */   public void setDisablePayloadBuffering(boolean value) {
/* 347 */     this.disablePayloadBuffering = value;
/*     */   }
/*     */   
/*     */   public boolean getDisableIncPrefix() {
/* 351 */     return this.disbaleIncPrefix;
/*     */   }
/*     */   
/*     */   public void setDisableIncPrefix(boolean disableIncPrefix) {
/* 355 */     this.disbaleIncPrefix = disableIncPrefix;
/*     */   }
/*     */   
/*     */   public boolean getEncHeaderContent() {
/* 359 */     return this.encHeaderContent;
/*     */   }
/*     */   
/*     */   public void setEncHeaderContent(boolean encHeaderContent) {
/* 363 */     this.encHeaderContent = encHeaderContent;
/*     */   }
/*     */   
/*     */   public void setBSP(boolean value) {
/* 367 */     this.isBSP = value;
/*     */   }
/*     */   
/*     */   public boolean isBSP() {
/* 371 */     return this.isBSP;
/*     */   }
/*     */   
/*     */   public BasicSecurityProfile getBSPContext() {
/* 375 */     return this.bspContext;
/*     */   }
/*     */   
/*     */   public Message getPVMessage() {
/* 379 */     return this.pvMsg;
/*     */   }
/*     */   
/*     */   public void setPVMessage(Message msg) {
/* 383 */     this.pvMsg = msg;
/*     */   }
/*     */   
/*     */   public boolean isMissingTimestampAllowed() {
/* 387 */     return this.allowMissingTimestamp;
/*     */   }
/*     */   
/*     */   public void setAllowMissingTimestamp(boolean allowMissingTimestamp) {
/* 391 */     this.allowMissingTimestamp = allowMissingTimestamp;
/*     */   }
/*     */   
/*     */   public boolean getMustUnderstandValue() {
/* 395 */     return this.mustUnderstandValue;
/*     */   }
/*     */   
/*     */   public void setMustUnderstandValue(boolean muValue) {
/* 399 */     this.mustUnderstandValue = muValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\JAXBFilterProcessingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */