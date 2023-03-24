/*      */ package com.sun.xml.ws.security.opt.impl.incoming;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.Init;
/*      */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*      */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*      */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.message.Attachment;
/*      */ import com.sun.xml.ws.api.message.AttachmentSet;
/*      */ import com.sun.xml.ws.api.message.Header;
/*      */ import com.sun.xml.ws.api.message.HeaderList;
/*      */ import com.sun.xml.ws.api.message.Message;
/*      */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*      */ import com.sun.xml.ws.encoding.TagInfoset;
/*      */ import com.sun.xml.ws.message.stream.StreamMessage;
/*      */ import com.sun.xml.ws.protocol.soap.VersionMismatchException;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*      */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*      */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*      */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*      */ import com.sun.xml.ws.security.opt.impl.attachment.AttachmentImpl;
/*      */ import com.sun.xml.ws.security.opt.impl.attachment.AttachmentSetImpl;
/*      */ import com.sun.xml.ws.security.opt.impl.incoming.processor.SecurityHeaderProcessor;
/*      */ import com.sun.xml.ws.security.opt.impl.incoming.processor.SecurityTokenProcessor;
/*      */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*      */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*      */ import com.sun.xml.ws.security.opt.impl.util.VerifiedMessageXMLStreamReader;
/*      */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*      */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*      */ import com.sun.xml.wss.BasicSecurityProfile;
/*      */ import com.sun.xml.wss.ProcessingContext;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyResolver;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.PolicyUtils;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.Target;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.spi.PolicyVerifier;
/*      */ import com.sun.xml.wss.impl.policy.verifier.PolicyVerifierFactory;
/*      */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.crypto.dsig.Reference;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SecurityRecipient
/*      */ {
/*  116 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings"); private static final int TIMESTAMP_ELEMENT = 1; private static final int USERNAME_TOKEN_ELEMENT = 2; private static final int BINARYSECURITY_TOKEN_ELEMENT = 3; private static final int ENCRYPTED_DATA_ELEMENT = 4; private static final int ENCRYPTED_KEY_ELEMENT = 5; private static final int SIGNATURE_ELEMENT = 6;
/*      */   private static final int REFERENCE_LIST_ELEMENT = 7;
/*      */   private static final int DERIVED_KEY_ELEMENT = 8;
/*      */   private static final int SIGNATURE_CONFIRMATION_ELEMENT = 9;
/*      */   
/*      */   static {
/*  122 */     Init.init();
/*      */   }
/*      */ 
/*      */   
/*      */   private static final int SECURITY_CONTEXT_TOKEN = 10;
/*      */   
/*      */   private static final int SAML_ASSERTION_ELEMENT = 11;
/*      */   
/*      */   private static final int ENCRYPTED_HEADER_ELEMENT = 12;
/*      */   
/*      */   private static final int STR_ELEMENT = 13;
/*      */   
/*      */   private static final String SOAP_ENVELOPE = "Envelope";
/*      */   
/*      */   private static final String SOAP_HEADER = "Header";
/*      */   
/*      */   private static final String SOAP_BODY = "Body";
/*      */   
/*      */   private final String SOAP_NAMESPACE_URI;
/*      */   private final SOAPVersion soapVersion;
/*  142 */   private XMLStreamReader message = null;
/*  143 */   private StreamReaderBufferCreator creator = null;
/*  144 */   private HashMap<String, String> parentNS = new HashMap<String, String>();
/*  145 */   private HashMap<String, String> parentNSOnEnvelope = new HashMap<String, String>();
/*  146 */   private HashMap<String, String> securityHeaderNS = new HashMap<String, String>();
/*  147 */   private HashMap<String, String> bodyENVNS = new HashMap<String, String>();
/*  148 */   private HashMap<String, String> envshNS = null;
/*      */   
/*  150 */   private XMLInputFactory staxIF = null;
/*  151 */   private JAXBFilterProcessingContext context = null;
/*  152 */   private HeaderList headers = null;
/*  153 */   private TagInfoset headerTag = null;
/*  154 */   private TagInfoset envelopeTag = null;
/*  155 */   private ArrayList processedHeaders = new ArrayList(2);
/*  156 */   private SecurityHeaderElement pendingElement = null;
/*  157 */   private ArrayList bufferedHeaders = new ArrayList();
/*  158 */   private SecurityContext securityContext = null;
/*  159 */   private TagInfoset bodyTag = null;
/*  160 */   private String bodyWsuId = "";
/*  161 */   private String payLoadWsuId = "";
/*  162 */   private String payLoadEncId = "";
/*  163 */   private HashMap<String, String> encIds = new HashMap<String, String>();
/*  164 */   private HashMap<String, QName> encQNames = new HashMap<String, QName>();
/*  165 */   private HashMap<String, String> edAlgos = new HashMap<String, String>();
/*      */   
/*  167 */   private BasicSecurityProfile bspContext = new BasicSecurityProfile();
/*      */ 
/*      */ 
/*      */   
/*      */   private String bodyPrologue;
/*      */ 
/*      */ 
/*      */   
/*      */   private String bodyEpilogue;
/*      */ 
/*      */ 
/*      */   
/*      */   public SecurityRecipient(XMLStreamReader reader, SOAPVersion soapVersion) {
/*  180 */     this.message = reader;
/*  181 */     this.soapVersion = soapVersion;
/*  182 */     this.SOAP_NAMESPACE_URI = soapVersion.nsUri;
/*  183 */     this.securityContext = new SecurityContext();
/*      */   }
/*      */   
/*      */   public SecurityRecipient(XMLStreamReader reader, SOAPVersion soapVersion, AttachmentSet attachSet) {
/*  187 */     this(reader, soapVersion);
/*  188 */     this.securityContext.setAttachmentSet(attachSet);
/*      */   }
/*      */   
/*      */   public void setBodyPrologue(String bodyPrologue) {
/*  192 */     this.bodyPrologue = bodyPrologue;
/*      */   }
/*      */   
/*      */   public void setBodyEpilogue(String bodyEpilogue) {
/*  196 */     this.bodyEpilogue = bodyEpilogue;
/*      */   }
/*      */ 
/*      */   
/*      */   public Message validateMessage(JAXBFilterProcessingContext ctx) throws XWSSecurityException {
/*      */     try {
/*  202 */       this.context = ctx;
/*  203 */       this.context.setSecurityContext(this.securityContext);
/*      */ 
/*      */ 
/*      */       
/*  207 */       this.context.setExtraneousProperty("EnableWSS11PolicyReceiver", "true");
/*  208 */       List scList = new ArrayList();
/*  209 */       this.context.setExtraneousProperty("receivedSignValues", scList);
/*      */       
/*  211 */       if (this.message.getEventType() != 1) {
/*  212 */         XMLStreamReaderUtil.nextElementContent(this.message);
/*      */       }
/*  214 */       XMLStreamReaderUtil.verifyReaderState(this.message, 1);
/*  215 */       if ("Envelope".equals(this.message.getLocalName()) && !this.SOAP_NAMESPACE_URI.equals(this.message.getNamespaceURI())) {
/*  216 */         throw new VersionMismatchException(this.soapVersion, new Object[] { this.SOAP_NAMESPACE_URI, this.message.getNamespaceURI() });
/*      */       }
/*  218 */       XMLStreamReaderUtil.verifyTag(this.message, this.SOAP_NAMESPACE_URI, "Envelope");
/*      */       
/*  220 */       this.envelopeTag = new TagInfoset(this.message); int i;
/*  221 */       for (i = 0; i < this.message.getNamespaceCount(); i++) {
/*  222 */         this.parentNS.put(this.message.getNamespacePrefix(i), this.message.getNamespaceURI(i));
/*  223 */         this.parentNSOnEnvelope.put(this.message.getNamespacePrefix(i), this.message.getNamespaceURI(i));
/*      */       } 
/*      */       
/*  226 */       XMLStreamReaderUtil.nextElementContent(this.message);
/*  227 */       XMLStreamReaderUtil.verifyReaderState(this.message, 1);
/*      */ 
/*      */       
/*  230 */       if (this.message.getLocalName().equals("Header") && this.message.getNamespaceURI().equals(this.SOAP_NAMESPACE_URI)) {
/*  231 */         this.headerTag = new TagInfoset(this.message);
/*      */ 
/*      */         
/*  234 */         for (i = 0; i < this.message.getNamespaceCount(); i++) {
/*  235 */           this.parentNS.put(this.message.getNamespacePrefix(i), this.message.getNamespaceURI(i));
/*      */         }
/*      */         
/*  238 */         XMLStreamReaderUtil.nextElementContent(this.message);
/*      */ 
/*      */         
/*  241 */         if (this.message.getEventType() == 1) {
/*  242 */           this.headers = new HeaderList();
/*      */ 
/*      */           
/*  245 */           cacheHeaders(this.message, this.parentNS, ctx);
/*      */         } 
/*      */ 
/*      */         
/*  249 */         XMLStreamReaderUtil.nextElementContent(this.message);
/*      */       } 
/*      */       
/*  252 */       return createMessage();
/*  253 */     } catch (WssSoapFaultException ex) {
/*  254 */       setExceptionMessage(ctx);
/*  255 */       throw ex;
/*  256 */     } catch (WebServiceException te) {
/*  257 */       setExceptionMessage(ctx);
/*  258 */       throw te;
/*  259 */     } catch (XMLStreamException e) {
/*  260 */       setExceptionMessage(ctx);
/*  261 */       throw new WebServiceException(e);
/*  262 */     } catch (XWSSecurityException xe) {
/*  263 */       setExceptionMessage(ctx);
/*  264 */       throw xe;
/*  265 */     } catch (XWSSecurityRuntimeException re) {
/*  266 */       setExceptionMessage(ctx);
/*  267 */       throw re;
/*  268 */     } catch (Exception e) {
/*  269 */       setExceptionMessage(ctx);
/*  270 */       throw new XWSSecurityRuntimeException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void cacheHeaders(XMLStreamReader reader, Map<String, String> namespaces, JAXBFilterProcessingContext ctx) throws XMLStreamException, XWSSecurityException {
/*  277 */     this.creator = new StreamReaderBufferCreator();
/*  278 */     MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
/*  279 */     this.creator.setXMLStreamBuffer(buffer);
/*      */     
/*  281 */     while (reader.getEventType() == 1) {
/*  282 */       Map<String, String> headerBlockNamespaces = namespaces;
/*      */ 
/*      */       
/*  285 */       if ("Security".equals(reader.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(reader.getNamespaceURI())) {
/*      */ 
/*      */         
/*  288 */         if (reader.getNamespaceCount() > 0) {
/*  289 */           headerBlockNamespaces = new HashMap<String, String>(namespaces);
/*  290 */           for (int i = 0; i < reader.getNamespaceCount(); i++) {
/*  291 */             headerBlockNamespaces.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*      */           }
/*      */         } 
/*      */         
/*  295 */         XMLStreamBufferMark xMLStreamBufferMark = new XMLStreamBufferMark(headerBlockNamespaces, (AbstractCreatorProcessor)this.creator);
/*  296 */         handleSecurityHeader();
/*      */       } else {
/*      */         
/*      */         try {
/*  300 */           if (reader.getNamespaceCount() > 0) {
/*  301 */             headerBlockNamespaces = new HashMap<String, String>(namespaces);
/*  302 */             for (int i = 0; i < reader.getNamespaceCount(); i++) {
/*  303 */               headerBlockNamespaces.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*      */             }
/*      */           } 
/*      */           
/*  307 */           XMLStreamBufferMark xMLStreamBufferMark = new XMLStreamBufferMark(headerBlockNamespaces, (AbstractCreatorProcessor)this.creator);
/*  308 */           GenericSecuredHeader gsh = new GenericSecuredHeader(reader, this.soapVersion, this.creator, (HashMap)namespaces, this.staxIF, this.context.getEncHeaderContent());
/*  309 */           this.headers.add((Header)gsh);
/*      */         }
/*  311 */         catch (XMLStreamBufferException ex) {
/*  312 */           throw new XWSSecurityException("Error occurred while reading SOAP Header" + ex);
/*      */         } 
/*      */       } 
/*  315 */       while (reader.isWhiteSpace()) {
/*  316 */         reader.next();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSecurityHeader() throws XWSSecurityException {
/*      */     try {
/*  327 */       if (this.context.getAddressingVersion() != null) {
/*  328 */         String action = this.headers.getAction(this.context.getAddressingVersion(), this.soapVersion);
/*  329 */         updateContext(action, this.context);
/*      */       } 
/*  331 */       if (this.message.getEventType() == 1 && 
/*  332 */         "Security".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(this.message.getNamespaceURI())) {
/*      */         
/*  334 */         for (int i = 0; i < this.message.getNamespaceCount(); i++) {
/*  335 */           this.securityHeaderNS.put(this.message.getNamespacePrefix(i), this.message.getNamespaceURI(i));
/*      */         }
/*  337 */         StreamUtil.moveToNextStartOREndElement(this.message);
/*      */       } 
/*      */ 
/*      */       
/*  341 */       HashMap<String, String> currentParentNS = new HashMap<String, String>();
/*  342 */       currentParentNS.putAll(this.parentNS);
/*  343 */       currentParentNS.putAll(this.securityHeaderNS);
/*  344 */       this.envshNS = currentParentNS;
/*  345 */       int eventType = getSecurityElementType();
/*  346 */       this.securityContext.setSOAPEnvelopeNSDecls(this.parentNSOnEnvelope);
/*  347 */       this.securityContext.setSecurityHdrNSDecls(this.securityHeaderNS);
/*  348 */       this.securityContext.setNonSecurityHeaders(this.headers);
/*  349 */       this.securityContext.setProcessedSecurityHeaders(this.processedHeaders);
/*  350 */       this.securityContext.setBufferedSecurityHeaders(this.bufferedHeaders);
/*  351 */       this.context.setSecurityContext(this.securityContext);
/*  352 */       while (this.message.getEventType() != 8) {
/*  353 */         TimestampHeader timestamp; UsernameTokenHeader ut; String valueType; EncryptedKey ek; EncryptedData ed; ReferenceListHeader refList; Signature sig; DerivedKeyToken dkt; SignatureConfirmation signConfirm; SecurityContextToken sct; SAMLAssertion samlAssertion; SecurityTokenProcessor str; WSSPolicy wSSPolicy1; ArrayList<String> list; WSSPolicy policy; switch (eventType) {
/*      */           case 1:
/*  355 */             if (this.context.isBSP() && this.bspContext.isTimeStampFound()) {
/*  356 */               BasicSecurityProfile.log_bsp_3203();
/*      */             }
/*  358 */             this.bspContext.setTimeStampFound(true);
/*  359 */             timestamp = new TimestampHeader(this.message, this.creator, currentParentNS, this.context);
/*  360 */             wSSPolicy1 = timestamp.getPolicy();
/*  361 */             timestamp.validate((ProcessingContext)this.context);
/*  362 */             this.processedHeaders.add(timestamp);
/*  363 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)timestamp.getPolicy());
/*      */             break;
/*      */           
/*      */           case 2:
/*  367 */             ut = new UsernameTokenHeader(this.message, this.creator, currentParentNS, this.staxIF);
/*  368 */             ut.validate((ProcessingContext)this.context);
/*  369 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(ut);
/*  370 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)ut.getPolicy());
/*  371 */             if (this.context.isTrustMessage() && !this.context.isClient()) {
/*  372 */               IssuedTokenContext ctx = null;
/*  373 */               if (this.context.getTrustContext() == null) {
/*  374 */                 IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*  375 */                 if (this.context.isSecure()) {
/*  376 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
/*      */                 } else {
/*  378 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
/*      */                 } 
/*  380 */                 this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl); break;
/*      */               } 
/*  382 */               ctx = this.context.getTrustContext();
/*  383 */               if (ctx.getAuthnContextClass() != null) {
/*  384 */                 if (this.context.isSecure()) {
/*  385 */                   ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
/*      */                 } else {
/*  387 */                   ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
/*      */                 } 
/*  389 */                 this.context.setTrustContext(ctx);
/*      */               } 
/*      */             } 
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/*  396 */             valueType = this.message.getAttributeValue(null, "ValueType");
/*  397 */             if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(valueType)) {
/*      */               
/*  399 */               KerberosBinarySecurityToken kbst = new KerberosBinarySecurityToken(this.message, this.creator, currentParentNS, this.staxIF);
/*  400 */               WSSPolicy wSSPolicy = kbst.getPolicy();
/*  401 */               kbst.validate((ProcessingContext)this.context);
/*  402 */               this.processedHeaders.add(kbst);
/*  403 */               this.context.getInferredSecurityPolicy().append((SecurityPolicy)kbst.getPolicy());
/*  404 */               if (this.context.isTrustMessage() && !this.context.isClient()) {
/*  405 */                 IssuedTokenContext ctx = null;
/*  406 */                 if (this.context.getTrustContext() == null) {
/*  407 */                   IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*  408 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos");
/*  409 */                   this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl); break;
/*      */                 } 
/*  411 */                 ctx = this.context.getTrustContext();
/*  412 */                 if (ctx.getAuthnContextClass() != null) {
/*  413 */                   ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos");
/*  414 */                   this.context.setTrustContext(ctx);
/*      */                 } 
/*      */               }  break;
/*      */             } 
/*  418 */             if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1".equals(valueType) || valueType == null) {
/*      */ 
/*      */               
/*  421 */               X509BinarySecurityToken bst = new X509BinarySecurityToken(this.message, this.creator, currentParentNS, this.staxIF);
/*  422 */               WSSPolicy wSSPolicy = bst.getPolicy();
/*  423 */               bst.validate((ProcessingContext)this.context);
/*  424 */               this.processedHeaders.add(bst);
/*  425 */               this.context.getInferredSecurityPolicy().append((SecurityPolicy)bst.getPolicy());
/*  426 */               if (this.context.isTrustMessage() && !this.context.isClient()) {
/*  427 */                 IssuedTokenContext ctx = null;
/*  428 */                 if (this.context.getTrustContext() == null) {
/*  429 */                   IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*  430 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:X509");
/*  431 */                   this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl); break;
/*      */                 } 
/*  433 */                 ctx = this.context.getTrustContext();
/*  434 */                 if (ctx.getAuthnContextClass() != null) {
/*  435 */                   ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:X509");
/*  436 */                   this.context.setTrustContext(ctx);
/*      */                 } 
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/*  442 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1616_UNRECOGNIZED_BST_VALUETYPE(valueType));
/*  443 */             throw new XWSSecurityException(LogStringsMessages.WSS_1616_UNRECOGNIZED_BST_VALUETYPE(valueType));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 5:
/*  449 */             ek = new EncryptedKey(this.message, this.context, currentParentNS);
/*  450 */             list = (ArrayList<String>)ek.getPendingReferenceList();
/*  451 */             if (list != null) {
/*  452 */               findAndReplaceED(list, ek);
/*  453 */               if (ek.getPendingReferenceList().size() > 0) {
/*  454 */                 if (this.pendingElement == null) {
/*  455 */                   this.pendingElement = ek;
/*      */                 }
/*  457 */                 addSecurityHeader(ek);
/*      */               } 
/*      */             } else {
/*      */               
/*  461 */               addSecurityHeader(ek);
/*      */             } 
/*  463 */             if (ek.getPolicy() != null) {
/*  464 */               this.context.getInferredSecurityPolicy().append((SecurityPolicy)ek.getPolicy());
/*      */             }
/*      */             break;
/*      */           
/*      */           case 4:
/*  469 */             ed = new EncryptedData(this.message, this.context, currentParentNS);
/*  470 */             handleEncryptedData(ed, currentParentNS);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 12:
/*  475 */             throw new XWSSecurityException("wsse11:EncryptedHeader not allowed inside SecurityHeader");
/*      */ 
/*      */ 
/*      */           
/*      */           case 7:
/*  480 */             refList = new ReferenceListHeader(this.message, this.context);
/*  481 */             if (this.pendingElement == null) {
/*  482 */               this.pendingElement = refList;
/*      */             } else {
/*  484 */               addSecurityHeader(refList);
/*      */             } 
/*      */             
/*  487 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)refList.getPolicy());
/*      */             break;
/*      */           
/*      */           case 6:
/*  491 */             sig = new Signature(this.context, currentParentNS, this.creator);
/*  492 */             sig.process(this.message);
/*  493 */             if (!sig.isValidated()) {
/*  494 */               if (this.pendingElement == null) {
/*  495 */                 this.pendingElement = sig;
/*      */               } else {
/*  497 */                 addSecurityHeader(sig);
/*      */               }
/*      */             
/*  500 */             } else if (!this.processedHeaders.contains(sig)) {
/*  501 */               this.processedHeaders.add(sig);
/*      */             } 
/*      */             
/*  504 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)sig.getPolicy());
/*      */             break;
/*      */           
/*      */           case 8:
/*  508 */             dkt = new DerivedKeyToken(this.message, this.context, currentParentNS);
/*  509 */             this.processedHeaders.add(dkt);
/*      */             break;
/*      */           
/*      */           case 9:
/*  513 */             signConfirm = new SignatureConfirmation(this.message, this.creator, currentParentNS, this.staxIF);
/*  514 */             policy = signConfirm.getPolicy();
/*  515 */             signConfirm.validate((ProcessingContext)this.context);
/*  516 */             this.processedHeaders.add(signConfirm);
/*  517 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)signConfirm.getPolicy());
/*      */             break;
/*      */           
/*      */           case 10:
/*  521 */             sct = new SecurityContextToken(this.message, this.context, currentParentNS);
/*  522 */             this.processedHeaders.add(sct);
/*      */             break;
/*      */           
/*      */           case 11:
/*  526 */             samlAssertion = new SAMLAssertion(this.message, this.context, null, currentParentNS);
/*  527 */             this.processedHeaders.add(samlAssertion);
/*  528 */             if (samlAssertion.isHOK() && 
/*  529 */               !samlAssertion.validateSignature()) {
/*  530 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1614_SAML_SIGNATURE_INVALID());
/*  531 */               throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, LogStringsMessages.WSS_1614_SAML_SIGNATURE_INVALID(), new Exception(LogStringsMessages.WSS_1614_SAML_SIGNATURE_INVALID()));
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  536 */             samlAssertion.validate((ProcessingContext)this.context);
/*  537 */             samlAssertion.getKey();
/*      */ 
/*      */             
/*  540 */             if (this.context.getExtraneousProperty("incoming_saml_assertion") == null && samlAssertion.isHOK()) {
/*  541 */               this.context.getExtraneousProperties().put("incoming_saml_assertion", samlAssertion);
/*      */             }
/*  543 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)samlAssertion.getPolicy());
/*  544 */             if (this.context.isTrustMessage() && !this.context.isClient()) {
/*  545 */               IssuedTokenContext ctx = null;
/*  546 */               if (this.context.getTrustContext() == null) {
/*  547 */                 IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*  548 */                 issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");
/*  549 */                 this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl); break;
/*      */               } 
/*  551 */               ctx = this.context.getTrustContext();
/*  552 */               if (ctx.getAuthnContextClass() != null) {
/*  553 */                 ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");
/*  554 */                 this.context.setTrustContext(ctx);
/*      */               } 
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 13:
/*  565 */             str = new SecurityTokenProcessor(this.context, null);
/*  566 */             str.resolveReference(this.message);
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/*  571 */             if (this.message.getEventType() == 1 && getSecurityElementType() == -1) {
/*      */               
/*  573 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1613_UNRECOGNIZED_SECURITY_ELEMENT(this.message.getLocalName()));
/*  574 */               throw new XWSSecurityException(LogStringsMessages.WSS_1613_UNRECOGNIZED_SECURITY_ELEMENT(this.message.getLocalName()));
/*      */             } 
/*      */             break;
/*      */         } 
/*  578 */         if (StreamUtil._break(this.message, "Security", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) {
/*      */           break;
/*      */         }
/*  581 */         eventType = getSecurityElementType();
/*  582 */         if (eventType == -1 && !StreamUtil.isStartElement(this.message)) {
/*  583 */           if (StreamUtil._break(this.message, "Security", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) {
/*      */             break;
/*      */           }
/*  586 */           this.message.next();
/*      */         } 
/*      */       } 
/*      */       
/*  590 */       this.message.next();
/*  591 */     } catch (XMLStreamException ex) {
/*      */       
/*  593 */       logger.log(Level.FINE, "Error occurred while reading SOAP Headers", ex);
/*  594 */       throw new XWSSecurityException(ex);
/*  595 */     } catch (XMLStreamBufferException ex) {
/*      */       
/*  597 */       logger.log(Level.FINE, "Error occurred while reading SOAP Headers", (Throwable)ex);
/*  598 */       throw new XWSSecurityException(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleEncryptedData(EncryptedData ed, HashMap<String, String> currentParentNS) throws XMLStreamException, XWSSecurityException {
/*  603 */     if (this.pendingElement != null && this.pendingElement instanceof EncryptedKey) {
/*  604 */       EncryptedKey ek = (EncryptedKey)this.pendingElement;
/*  605 */       if (ek.getPendingReferenceList() != null && ek.getPendingReferenceList().contains(ed.getId())) {
/*      */         
/*  607 */         if (ek.getPolicy() != null) {
/*  608 */           ek.getPolicy().setKeyBinding((MLSPolicy)ek.getInferredKB());
/*      */         }
/*      */ 
/*      */         
/*  612 */         if (!ed.hasCipherReference()) {
/*  613 */           XMLStreamReader decryptedData = ed.getDecryptedData(ek.getKey(ed.getEncryptionAlgorithm()));
/*  614 */           SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, currentParentNS, this.staxIF, this.creator);
/*  615 */           if (decryptedData.getEventType() != 1) {
/*  616 */             StreamUtil.moveToNextElement(decryptedData);
/*      */           }
/*  618 */           SecurityHeaderElement she = shp.createHeader(decryptedData);
/*  619 */           this.encIds.put(ed.getId(), she.getId());
/*  620 */           this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*  621 */           ek.getPendingReferenceList().remove(ed.getId());
/*  622 */           if (ek.getPendingReferenceList().isEmpty()) {
/*  623 */             this.pendingElement = null;
/*  624 */             this.bufferedHeaders.remove(ek);
/*  625 */             addSecurityHeader(ek);
/*      */           } 
/*  627 */           checkDecryptedData(she, ek.getPolicy());
/*      */         } else {
/*      */           
/*  630 */           byte[] decryptedMimeData = ed.getDecryptedMimeData(ek.getKey(ed.getEncryptionAlgorithm()));
/*  631 */           AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/*  632 */           this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/*  633 */           ek.getPendingReferenceList().remove(ed.getId());
/*  634 */           if (ek.getPendingReferenceList().isEmpty()) {
/*  635 */             this.pendingElement = null;
/*  636 */             this.bufferedHeaders.remove(ek);
/*  637 */             addSecurityHeader(ek);
/*      */           }
/*      */         
/*      */         } 
/*  641 */       } else if (!lookInBufferedHeaders(ed, currentParentNS)) {
/*  642 */         addSecurityHeader(ed);
/*      */       }
/*      */     
/*  645 */     } else if (this.pendingElement != null && this.pendingElement instanceof ReferenceListHeader) {
/*  646 */       ReferenceListHeader refList = (ReferenceListHeader)this.pendingElement;
/*  647 */       if (refList.getPendingReferenceList().contains(ed.getId())) {
/*      */         
/*  649 */         refList.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/*      */ 
/*      */         
/*  652 */         if (!ed.hasCipherReference()) {
/*  653 */           XMLStreamReader decryptedData = ed.getDecryptedData();
/*  654 */           if (decryptedData.getEventType() != 1) {
/*  655 */             StreamUtil.moveToNextElement(decryptedData);
/*      */           }
/*  657 */           SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, currentParentNS, this.staxIF, this.creator);
/*  658 */           SecurityHeaderElement she = shp.createHeader(decryptedData);
/*  659 */           this.encIds.put(ed.getId(), she.getId());
/*  660 */           this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*  661 */           refList.getPendingReferenceList().remove(ed.getId());
/*  662 */           if (refList.getPendingReferenceList().isEmpty()) {
/*  663 */             this.pendingElement = null;
/*      */           }
/*  665 */           checkDecryptedData(she, refList.getPolicy());
/*      */         } else {
/*      */           
/*  668 */           byte[] decryptedMimeData = ed.getDecryptedMimeData();
/*  669 */           AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/*  670 */           this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/*  671 */           refList.getPendingReferenceList().remove(ed.getId());
/*  672 */           if (refList.getPendingReferenceList().isEmpty()) {
/*  673 */             this.pendingElement = null;
/*      */           }
/*      */         }
/*      */       
/*  677 */       } else if (!lookInBufferedHeaders(ed, currentParentNS)) {
/*  678 */         decryptStandaloneED(ed, currentParentNS, refList.getPolicy());
/*      */       }
/*      */     
/*      */     }
/*  682 */     else if (!lookInBufferedHeaders(ed, currentParentNS)) {
/*  683 */       decryptStandaloneED(ed, currentParentNS, null);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean lookInBufferedHeaders(EncryptedData ed, HashMap<String, String> currentParentNS) throws XWSSecurityException, XMLStreamException {
/*  689 */     if (this.bufferedHeaders.size() > 0) {
/*  690 */       for (int i = 0; i < this.bufferedHeaders.size(); i++) {
/*  691 */         SecurityHeaderElement bufShe = this.bufferedHeaders.get(i);
/*  692 */         if ("EncryptedKey".equals(bufShe.getLocalPart()) && "http://www.w3.org/2001/04/xmlenc#".equals(bufShe.getNamespaceURI())) {
/*      */           
/*  694 */           EncryptedKey ek = (EncryptedKey)bufShe;
/*  695 */           if (ek.getPendingReferenceList() != null && ek.getPendingReferenceList().contains(ed.getId()))
/*      */           {
/*  697 */             if (ek.getPolicy() != null) {
/*  698 */               ek.getPolicy().setKeyBinding((MLSPolicy)ek.getInferredKB());
/*      */             }
/*  700 */             if (!ed.hasCipherReference()) {
/*  701 */               XMLStreamReader decryptedData = ed.getDecryptedData(ek.getKey(ed.getEncryptionAlgorithm()));
/*  702 */               SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, currentParentNS, this.staxIF, this.creator);
/*  703 */               if (decryptedData.getEventType() != 1) {
/*  704 */                 StreamUtil.moveToNextElement(decryptedData);
/*      */               }
/*  706 */               SecurityHeaderElement she = shp.createHeader(decryptedData);
/*  707 */               this.encIds.put(ed.getId(), she.getId());
/*  708 */               this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*      */               
/*  710 */               ek.getPendingReferenceList().remove(ed.getId());
/*  711 */               checkDecryptedData(she, ek.getPolicy());
/*      */             } else {
/*      */               
/*  714 */               byte[] decryptedMimeData = ed.getDecryptedMimeData(ek.getKey(ed.getEncryptionAlgorithm()));
/*  715 */               AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/*  716 */               this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/*  717 */               ek.getPendingReferenceList().remove(ed.getId());
/*      */             } 
/*  719 */             return true;
/*      */           }
/*      */         
/*  722 */         } else if ("ReferenceList".equals(bufShe.getLocalPart()) && "http://www.w3.org/2001/04/xmlenc#".equals(bufShe.getNamespaceURI())) {
/*      */           
/*  724 */           ReferenceListHeader refList = (ReferenceListHeader)bufShe;
/*  725 */           if (refList.getPendingReferenceList().contains(ed.getId())) {
/*      */             
/*  727 */             refList.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/*      */ 
/*      */             
/*  730 */             if (!ed.hasCipherReference()) {
/*  731 */               XMLStreamReader decryptedData = ed.getDecryptedData();
/*  732 */               if (decryptedData.getEventType() != 1) {
/*  733 */                 StreamUtil.moveToNextElement(decryptedData);
/*      */               }
/*  735 */               SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, currentParentNS, this.staxIF, this.creator);
/*  736 */               SecurityHeaderElement she = shp.createHeader(decryptedData);
/*  737 */               this.encIds.put(ed.getId(), she.getId());
/*  738 */               this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*      */               
/*  740 */               refList.getPendingReferenceList().remove(ed.getId());
/*  741 */               checkDecryptedData(she, refList.getPolicy());
/*      */             } else {
/*      */               
/*  744 */               byte[] decryptedMimeData = ed.getDecryptedMimeData();
/*  745 */               AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/*  746 */               this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/*  747 */               refList.getPendingReferenceList().remove(ed.getId());
/*      */             } 
/*  749 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  754 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void decryptStandaloneED(EncryptedData ed, HashMap<String, String> currentParentNS, WSSPolicy policy) throws XMLStreamException, XWSSecurityException {
/*  759 */     if (ed.getKey() == null) {
/*  760 */       this.bufferedHeaders.add(ed);
/*      */       return;
/*      */     } 
/*  763 */     if (!ed.hasCipherReference()) {
/*  764 */       XMLStreamReader decryptedData = ed.getDecryptedData();
/*  765 */       if (decryptedData.getEventType() != 1) {
/*  766 */         StreamUtil.moveToNextElement(decryptedData);
/*      */       }
/*  768 */       SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, currentParentNS, this.staxIF, this.creator);
/*  769 */       SecurityHeaderElement she = shp.createHeader(decryptedData);
/*  770 */       this.encIds.put(ed.getId(), she.getId());
/*  771 */       this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*  772 */       checkDecryptedData(she, policy);
/*  773 */       if (!handleSAMLAssertion(she)) {
/*  774 */         addSecurityHeader(she);
/*      */       }
/*      */     } else {
/*      */       
/*  778 */       byte[] decryptedMimeData = ed.getDecryptedMimeData();
/*  779 */       AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/*  780 */       this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkDecryptedData(SecurityHeaderElement she, WSSPolicy pol) throws XWSSecurityException {
/*  786 */     if ("Signature".equals(she.getLocalPart())) {
/*  787 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)pol)) {
/*  788 */         EncryptionPolicy ep = (EncryptionPolicy)pol;
/*  789 */         EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)ep.getFeatureBinding();
/*      */         
/*  791 */         fb.encryptsSignature(true);
/*      */       } 
/*  793 */       Signature sig = (Signature)she;
/*  794 */       if (!sig.getReferences().isEmpty() && isPending()) {
/*  795 */         if (this.pendingElement == null) {
/*  796 */           this.pendingElement = she;
/*      */         } else {
/*  798 */           this.bufferedHeaders.add(sig);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Message createMessage() throws XWSSecurityException {
/*      */     StreamMessage streamMessage;
/*  807 */     XMLStreamReaderUtil.verifyTag(this.message, this.SOAP_NAMESPACE_URI, "Body");
/*  808 */     this.bodyTag = new TagInfoset(this.message);
/*  809 */     this.bodyENVNS.putAll(this.parentNSOnEnvelope);
/*  810 */     for (int i = 0; i < this.message.getNamespaceCount(); i++) {
/*  811 */       this.bodyENVNS.put(this.message.getNamespacePrefix(i), this.message.getNamespaceURI(i));
/*      */     }
/*  813 */     this.bodyWsuId = StreamUtil.getWsuId(this.message);
/*  814 */     if (this.bodyWsuId == null) {
/*  815 */       this.bodyWsuId = "";
/*      */     }
/*  817 */     XMLStreamReaderUtil.nextElementContent(this.message);
/*  818 */     cachePayLoadId();
/*  819 */     if (this.pendingElement != null) {
/*      */       
/*      */       try {
/*  822 */         if (this.pendingElement instanceof EncryptedKey && StreamUtil.isStartElement(this.message) && isEncryptedData()) {
/*  823 */           XMLStreamReader reader = this.message;
/*  824 */           EncryptedData ed = new EncryptedData(this.message, this.context, this.bodyENVNS);
/*  825 */           this.payLoadWsuId = ed.getId();
/*  826 */           handlePayLoadED(ed);
/*  827 */           this.payLoadEncId = ed.getId();
/*  828 */           XMLStreamReaderUtil.close(reader);
/*  829 */           XMLStreamReaderFactory.recycle(reader);
/*  830 */         } else if (this.pendingElement instanceof Signature) {
/*  831 */           Signature sig = (Signature)this.pendingElement;
/*  832 */           handleSignature(sig);
/*      */           
/*  834 */           this.processedHeaders.add(this.pendingElement);
/*  835 */           this.pendingElement = null;
/*  836 */         } else if (this.pendingElement instanceof ReferenceListHeader) {
/*  837 */           ReferenceListHeader refList = (ReferenceListHeader)this.pendingElement;
/*  838 */           if (refList.getPendingReferenceList().contains(this.payLoadWsuId)) {
/*  839 */             XMLStreamReader reader = this.message;
/*  840 */             EncryptedData ed = new EncryptedData(this.message, this.context, this.bodyENVNS);
/*      */             
/*  842 */             refList.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/*  843 */             this.payLoadWsuId = ed.getId();
/*  844 */             handlePayLoadED(ed);
/*  845 */             refList.getPendingReferenceList().remove(this.payLoadWsuId);
/*  846 */             this.payLoadEncId = ed.getId();
/*  847 */             cachePayLoadId();
/*  848 */             XMLStreamReaderUtil.close(reader);
/*  849 */             XMLStreamReaderFactory.recycle(reader);
/*      */           } 
/*  851 */           if (refList.getPendingReferenceList().size() > 0) {
/*  852 */             findAndReplaceED((ArrayList<String>)refList.getPendingReferenceList(), refList);
/*      */           }
/*  854 */           if (refList.getPendingReferenceList().isEmpty()) {
/*  855 */             this.pendingElement = null;
/*      */           } else {
/*  857 */             String uri = refList.getPendingReferenceList().get(0);
/*  858 */             throw new XWSSecurityException("Reference with ID " + uri + " was not found in the message");
/*      */           }
/*      */         
/*      */         } 
/*  862 */       } catch (XMLStreamException e) {
/*      */         
/*  864 */         throw new WebServiceException(e);
/*  865 */       } catch (XWSSecurityException xse) {
/*  866 */         throw new WebServiceException(xse);
/*      */       } 
/*      */     }
/*      */     
/*  870 */     ArrayList<SecurityHeaderElement> clonedBufferedHeaders = (ArrayList)this.bufferedHeaders.clone();
/*  871 */     if (clonedBufferedHeaders.size() > 0) {
/*  872 */       for (int j = 0; j < clonedBufferedHeaders.size(); j++) {
/*  873 */         SecurityHeaderElement she = clonedBufferedHeaders.get(j);
/*  874 */         processSecurityHeader(she);
/*      */       } 
/*      */     }
/*  877 */     if (this.processedHeaders.size() > 0) {
/*  878 */       for (int j = 0; j < this.processedHeaders.size(); j++) {
/*  879 */         SecurityHeaderElement she = this.processedHeaders.get(j);
/*  880 */         processProcessedHeaders(she);
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/*  885 */       if (this.message == null) {
/*  886 */         this.message = getEmptyBody();
/*      */       }
/*  888 */     } catch (XMLStreamException xse) {
/*  889 */       throw new XWSSecurityException(xse);
/*      */     } 
/*      */     
/*  892 */     Message streamMsg = null;
/*  893 */     AttachmentSet as = this.securityContext.getDecryptedAttachmentSet();
/*  894 */     if (as == null || as.isEmpty()) {
/*  895 */       as = this.securityContext.getAttachmentSet();
/*      */     }
/*  897 */     if (!this.context.getDisablePayloadBuffering() && (!this.context.isSecure() || "Fault".equals(this.message.getLocalName()))) {
/*  898 */       if (logger.isLoggable(Level.FINE)) {
/*  899 */         logger.log(Level.FINE, "Buffering Payload from incomming message");
/*      */       }
/*  901 */       VerifiedMessageXMLStreamReader verifiedReader = new VerifiedMessageXMLStreamReader(this.message, this.bodyENVNS);
/*  902 */       VerifiedStreamMessage verifiedStreamMessage = new VerifiedStreamMessage(this.envelopeTag, this.headerTag, as, this.headers, this.bodyTag, (XMLStreamReader)verifiedReader, this.soapVersion, this.bodyENVNS);
/*      */     } else {
/*  904 */       if (logger.isLoggable(Level.FINE)) {
/*  905 */         logger.log(Level.FINE, "Not Buffering Payload from incomming message");
/*      */       }
/*  907 */       streamMessage = new StreamMessage(this.envelopeTag, this.headerTag, as, this.headers, this.bodyTag, this.message, this.soapVersion);
/*      */     } 
/*  909 */     this.context.setMessage((Message)streamMessage);
/*  910 */     boolean scCancel = false;
/*      */ 
/*      */     
/*  913 */     if (this.context.getAddressingVersion() != null) {
/*  914 */       String action = streamMessage.getHeaders().getAction(this.context.getAddressingVersion(), this.context.getSOAPVersion());
/*      */       
/*  916 */       if ("http://schemas.xmlsoap.org/ws/2004/09/transfer/Get".equals(action)) {
/*  917 */         return (Message)streamMessage;
/*      */       }
/*  919 */       if ("http://schemas.xmlsoap.org/ws/2005/02/trust/RST/SCT/Cancel".equals(action) || "http://schemas.xmlsoap.org/ws/2005/02/trust/RSTR/SCT/Cancel".equals(action))
/*      */       {
/*  921 */         scCancel = true;
/*      */       }
/*      */     } 
/*  924 */     SecurityPolicy msgPolicy = this.context.getSecurityPolicy();
/*      */ 
/*      */     
/*  927 */     if (PolicyUtils.isEmpty(msgPolicy)) {
/*  928 */       PolicyResolver opResolver = (PolicyResolver)this.context.getExtraneousProperty("OperationResolver");
/*      */       
/*  930 */       if (opResolver != null) {
/*  931 */         msgPolicy = opResolver.resolvePolicy((ProcessingContext)this.context);
/*      */       }
/*      */     } 
/*  934 */     if (this.context.isSecure() && this.context.getInferredSecurityPolicy().isEmpty()) {
/*  935 */       if (PolicyUtils.isEmpty(msgPolicy) || this.context.isMissingTimestampAllowed()) {
/*  936 */         return (Message)streamMessage;
/*      */       }
/*  938 */       throw new XWSSecurityException("Security Requirements not met - No Security header in message");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  944 */       MessagePolicy inferredMessagePolicy = this.context.getInferredSecurityPolicy();
/*  945 */       for (int j = 0; j < inferredMessagePolicy.size(); j++) {
/*  946 */         WSSPolicy wssPolicy = (WSSPolicy)inferredMessagePolicy.get(j);
/*  947 */         if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)wssPolicy)) {
/*  948 */           SignaturePolicy.FeatureBinding fb = (SignaturePolicy.FeatureBinding)wssPolicy.getFeatureBinding();
/*  949 */           ArrayList targets = fb.getTargetBindings();
/*      */           
/*  951 */           modifyTargets(targets);
/*  952 */         } else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)wssPolicy)) {
/*  953 */           EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)wssPolicy.getFeatureBinding();
/*  954 */           ArrayList targets = fb.getTargetBindings();
/*      */           
/*  956 */           modifyTargets(targets);
/*      */         }
/*      */       
/*      */       } 
/*  960 */     } catch (Exception ex) {
/*  961 */       throw new XWSSecurityException(ex);
/*      */     } 
/*      */     
/*  964 */     if (scCancel) {
/*  965 */       boolean securedBody = false;
/*  966 */       boolean allHeaders = false;
/*      */       try {
/*  968 */         MessagePolicy mp = this.context.getInferredSecurityPolicy();
/*  969 */         for (int j = 0; j < mp.size(); j++) {
/*  970 */           WSSPolicy wp = (WSSPolicy)mp.get(j);
/*  971 */           if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)wp)) {
/*  972 */             EncryptionPolicy ep = (EncryptionPolicy)wp;
/*  973 */             ArrayList<EncryptionTarget> list = ((EncryptionPolicy.FeatureBinding)ep.getFeatureBinding()).getTargetBindings();
/*  974 */             for (int ei = 0; ei < list.size(); ei++) {
/*  975 */               EncryptionTarget et = list.get(ei);
/*  976 */               if (et.getValue().equals("{http://schemas.xmlsoap.org/soap/envelope/}Body")) {
/*  977 */                 securedBody = true;
/*      */               }
/*      */             } 
/*  980 */           } else if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)wp)) {
/*  981 */             SignaturePolicy sp = (SignaturePolicy)wp;
/*  982 */             ArrayList<SignatureTarget> list = ((SignaturePolicy.FeatureBinding)sp.getFeatureBinding()).getTargetBindings();
/*  983 */             for (int ei = 0; ei < list.size(); ei++) {
/*  984 */               SignatureTarget st = list.get(ei);
/*      */               
/*  986 */               if (st.getValue().equals("{http://schemas.xmlsoap.org/soap/envelope/}Body")) {
/*  987 */                 securedBody = true;
/*      */               }
/*      */             } 
/*  990 */             if (!allHeaders) {
/*  991 */               allHeaders = areHeadersSecured(sp);
/*      */             }
/*      */           } 
/*      */         } 
/*  995 */       } catch (Exception ex) {
/*  996 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */       
/*  999 */       if (!this.context.isSecure() && (!securedBody || !allHeaders)) {
/* 1000 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1602_SCCANCEL_SECURITY_UNCONFIGURED());
/* 1001 */         throw new XWSSecurityException("Security Requirements not met");
/*      */       } 
/* 1003 */       return (Message)streamMessage;
/*      */     } 
/*      */     
/* 1006 */     if ((this.context.getInferredSecurityPolicy() == null || this.context.getInferredSecurityPolicy().isEmpty()) && 
/* 1007 */       this.context.isClient() && "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/SCT/Cancel".equals(this.context.getAction())) {
/* 1008 */       return (Message)streamMessage;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1015 */     PolicyVerifier mpv = PolicyVerifierFactory.createVerifier(msgPolicy, (ProcessingContext)this.context);
/* 1016 */     mpv.verifyPolicy((SecurityPolicy)this.context.getInferredSecurityPolicy(), msgPolicy);
/*      */     
/* 1018 */     return (Message)streamMessage;
/*      */   }
/*      */ 
/*      */   
/*      */   private XMLStreamReader getEmptyBody() throws XMLStreamException {
/* 1023 */     String emptyBody = "<S:Body xmlns:S=\"" + this.soapVersion.nsUri + "\"" + "></S:Body>";
/* 1024 */     InputStream in = new ByteArrayInputStream(emptyBody.getBytes());
/* 1025 */     XMLInputFactory xif = XMLInputFactory.newInstance();
/* 1026 */     XMLStreamReader empBody = xif.createXMLStreamReader(in);
/* 1027 */     empBody.next();
/* 1028 */     empBody.next();
/* 1029 */     return empBody;
/*      */   }
/*      */   
/*      */   private XMLStreamReader getEmptyBodyNoException() {
/*      */     try {
/* 1034 */       return getEmptyBody();
/* 1035 */     } catch (XMLStreamException ex) {
/* 1036 */       logger.log(Level.FINE, "", ex);
/*      */       
/* 1038 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void modifyTargets(ArrayList<Target> targets) {
/* 1044 */     for (int i = 0; i < targets.size(); i++) {
/* 1045 */       Target target = targets.get(i);
/* 1046 */       if ("uri".equals(target.getType())) {
/* 1047 */         findAndReplaceTargets(target);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean handleSAMLAssertion(SecurityHeaderElement she) throws XWSSecurityException {
/* 1054 */     if ("Assertion".equals(she.getLocalPart())) {
/* 1055 */       SAMLAssertion samlAssertion = (SAMLAssertion)she;
/* 1056 */       this.processedHeaders.add(samlAssertion);
/* 1057 */       if (samlAssertion.isHOK()) {
/* 1058 */         samlAssertion.validateSignature();
/*      */       }
/* 1060 */       samlAssertion.validate((ProcessingContext)this.context);
/* 1061 */       samlAssertion.getKey();
/* 1062 */       this.context.getExtraneousProperties().put("incoming_saml_assertion", samlAssertion);
/* 1063 */       if (this.context.isTrustMessage() && !this.context.isClient()) {
/* 1064 */         IssuedTokenContext ctx = null;
/* 1065 */         if (this.context.getTrustContext() == null) {
/* 1066 */           IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 1067 */           issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");
/* 1068 */           this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl);
/*      */         } else {
/* 1070 */           ctx = this.context.getTrustContext();
/* 1071 */           if (ctx.getAuthnContextClass() != null) {
/* 1072 */             ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");
/* 1073 */             this.context.setTrustContext(ctx);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1077 */       return true;
/*      */     } 
/* 1079 */     return false;
/*      */   }
/*      */   
/*      */   private void findAndReplaceTargets(Target target) {
/* 1083 */     String uri = target.getValue();
/* 1084 */     int index = uri.indexOf("#");
/* 1085 */     QName qname = null;
/* 1086 */     if (index >= 0) {
/* 1087 */       uri = uri.substring(index + 1);
/*      */     }
/* 1089 */     if (target instanceof EncryptionTarget) {
/* 1090 */       String temp = this.encIds.get(uri);
/* 1091 */       String edAlgo = this.edAlgos.get(uri);
/* 1092 */       ((EncryptionTarget)target).setDataEncryptionAlgorithm(edAlgo);
/* 1093 */       if (temp != null) {
/* 1094 */         uri = temp;
/*      */       } else {
/* 1096 */         qname = this.encQNames.get(uri);
/*      */       } 
/*      */     } 
/*      */     
/* 1100 */     if (uri.equals(this.bodyWsuId)) {
/* 1101 */       target.setType("qname");
/* 1102 */       target.setValue("SOAP-BODY");
/* 1103 */       target.setContentOnly(false);
/*      */       return;
/*      */     } 
/* 1106 */     if (uri.equals(this.payLoadWsuId) || uri.equals(this.payLoadEncId)) {
/* 1107 */       target.setType("qname");
/* 1108 */       target.setValue("SOAP-BODY");
/* 1109 */       target.setContentOnly(true);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1114 */     if (this.headers != null && this.headers.size() > 0) {
/* 1115 */       Iterator<Header> listItr = this.headers.listIterator();
/* 1116 */       while (listItr.hasNext()) {
/* 1117 */         GenericSecuredHeader header = (GenericSecuredHeader)listItr.next();
/* 1118 */         if (header.hasID(uri)) {
/* 1119 */           qname = new QName(header.getNamespaceURI(), header.getLocalPart());
/* 1120 */           target.setQName(qname);
/* 1121 */           target.setContentOnly(false); return;
/*      */         } 
/* 1123 */         if (qname != null) {
/* 1124 */           target.setQName(qname);
/* 1125 */           target.setContentOnly(false);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     int j;
/* 1131 */     for (j = 0; j < this.processedHeaders.size(); j++) {
/* 1132 */       SecurityHeaderElement header = this.processedHeaders.get(j);
/* 1133 */       if (uri.equals(header.getId())) {
/* 1134 */         qname = new QName(header.getNamespaceURI(), header.getLocalPart());
/* 1135 */         target.setQName(qname);
/* 1136 */         target.setContentOnly(false);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1141 */     for (j = 0; j < this.bufferedHeaders.size(); j++) {
/* 1142 */       SecurityHeaderElement header = this.bufferedHeaders.get(j);
/* 1143 */       if (uri.equals(header.getId())) {
/* 1144 */         qname = new QName(header.getNamespaceURI(), header.getLocalPart());
/* 1145 */         target.setQName(qname);
/* 1146 */         target.setContentOnly(false);
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void processProcessedHeaders(SecurityHeaderElement she) throws XWSSecurityException {
/* 1154 */     if (she instanceof EncryptedData)
/* 1155 */       throw new XWSSecurityException("Error in Processing, EncryptedData inside procesesdHeaders, should never happen"); 
/* 1156 */     if (she instanceof EncryptedKey) {
/* 1157 */       EncryptedKey ek = (EncryptedKey)she;
/* 1158 */       ArrayList<String> list = (ArrayList<String>)ek.getPendingReferenceList();
/* 1159 */       if (list != null && list.size() > 0) {
/* 1160 */         throw new XWSSecurityException("Error in processing, ReferenceList inside EK should have been processed");
/*      */       }
/* 1162 */     } else if (she instanceof ReferenceListHeader) {
/* 1163 */       ReferenceListHeader refList = (ReferenceListHeader)she;
/* 1164 */       if (refList.getPendingReferenceList() != null && refList.getPendingReferenceList().size() > 0) {
/* 1165 */         throw new XWSSecurityException("Error in processing, references in ReferenceList not processed");
/*      */       }
/* 1167 */     } else if (she instanceof Signature) {
/* 1168 */       Signature sig = (Signature)she;
/* 1169 */       if (sig.getReferences() != null && sig.getReferences().size() > 0) {
/* 1170 */         throw new XWSSecurityException("Error in processing, references in Signature not processed");
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isPending() throws XWSSecurityException {
/* 1176 */     for (int i = 0; i < this.bufferedHeaders.size(); i++) {
/* 1177 */       SecurityHeaderElement she = this.bufferedHeaders.get(i);
/* 1178 */       if (isPrimary(she)) {
/* 1179 */         return false;
/*      */       }
/*      */     } 
/* 1182 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isPrimary(SecurityHeaderElement she) {
/* 1187 */     if ("Signature".equals(she.getLocalPart()))
/* 1188 */       return true; 
/* 1189 */     if ("EncryptedKey".equals(she.getLocalPart()))
/* 1190 */       return true; 
/* 1191 */     if ("ReferenceList".equals(she.getLocalPart())) {
/* 1192 */       return true;
/*      */     }
/* 1194 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void processSecurityHeader(SecurityHeaderElement she) throws XWSSecurityException {
/* 1199 */     if (she instanceof Signature) {
/* 1200 */       handleSignature((Signature)she);
/* 1201 */       this.processedHeaders.add(she);
/* 1202 */     } else if (she instanceof EncryptedData) {
/* 1203 */       EncryptedData ed = (EncryptedData)she;
/*      */       
/*      */       try {
/* 1206 */         if (!ed.hasCipherReference()) {
/* 1207 */           XMLStreamReader decryptedData = ed.getDecryptedData();
/* 1208 */           SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, this.envshNS, this.staxIF, this.creator);
/* 1209 */           if (decryptedData.getEventType() != 1) {
/* 1210 */             StreamUtil.moveToNextElement(decryptedData);
/*      */           }
/* 1212 */           SecurityHeaderElement newHeader = shp.createHeader(decryptedData);
/* 1213 */           this.encIds.put(ed.getId(), newHeader.getId());
/* 1214 */           this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/* 1215 */           processSecurityHeader(newHeader);
/* 1216 */           this.processedHeaders.add(newHeader);
/*      */         } else {
/*      */           
/* 1219 */           byte[] decryptedMimeData = ed.getDecryptedMimeData();
/* 1220 */           AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/* 1221 */           this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/*      */         } 
/* 1223 */       } catch (XMLStreamException ex) {
/* 1224 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1615_ERROR_DECRYPTING_ENCRYPTED_DATA(), ex);
/* 1225 */         throw new XWSSecurityException("Error occurred while decrypting EncryptedData with ID " + ed.getId(), ex);
/*      */       } 
/* 1227 */     } else if (she instanceof EncryptedKey) {
/* 1228 */       EncryptedKey ek = (EncryptedKey)she;
/* 1229 */       if (this.pendingElement == null) {
/* 1230 */         this.pendingElement = ek;
/*      */       }
/* 1232 */       addSecurityHeader(ek);
/* 1233 */       ArrayList<String> list = (ArrayList<String>)ek.getPendingReferenceList();
/* 1234 */       if (list != null) {
/* 1235 */         findAndReplaceED(list, ek);
/*      */         
/* 1237 */         if (ek.getPendingReferenceList().size() > 0 && this.payLoadWsuId.length() > 0 && 
/* 1238 */           ek.getPendingReferenceList().contains(this.payLoadWsuId)) {
/*      */ 
/*      */           
/*      */           try {
/* 1242 */             EncryptedData ed = new EncryptedData(this.message, this.context, this.bodyENVNS);
/* 1243 */             this.payLoadWsuId = ed.getId();
/* 1244 */             handlePayLoadED(ed);
/* 1245 */           } catch (XMLStreamException ex) {
/* 1246 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1615_ERROR_DECRYPTING_ENCRYPTED_DATA(), ex);
/* 1247 */             throw new XWSSecurityException("Error occurred while parsing EncryptedData" + ex);
/*      */           } 
/* 1249 */           ek.getPendingReferenceList().remove(this.payLoadWsuId);
/*      */         } 
/*      */         
/* 1252 */         if (!ek.getPendingReferenceList().isEmpty()) {
/* 1253 */           throw new XWSSecurityException("Data  Reference under EncryptedKey with ID " + ek.getId() + " is not found");
/*      */         }
/* 1255 */         this.pendingElement = null;
/* 1256 */         this.bufferedHeaders.remove(ek);
/* 1257 */         addSecurityHeader(ek);
/*      */       }
/*      */     
/* 1260 */     } else if (she instanceof ReferenceListHeader) {
/* 1261 */       ReferenceListHeader refList = (ReferenceListHeader)she;
/* 1262 */       if (refList.getPendingReferenceList().contains(this.payLoadWsuId)) {
/*      */         try {
/* 1264 */           EncryptedData ed = new EncryptedData(this.message, this.context, this.bodyENVNS);
/*      */ 
/*      */           
/* 1267 */           refList.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/*      */           
/* 1269 */           this.payLoadWsuId = ed.getId();
/* 1270 */           handlePayLoadED(ed);
/* 1271 */           refList.getPendingReferenceList().remove(this.payLoadWsuId);
/* 1272 */           cachePayLoadId();
/* 1273 */           this.payLoadEncId = ed.getId();
/* 1274 */         } catch (XMLStreamException ex) {
/* 1275 */           throw new XWSSecurityException("Error occurred while processing EncryptedData", ex);
/*      */         } 
/*      */       }
/* 1278 */       if (refList.getPendingReferenceList().size() > 0) {
/* 1279 */         findAndReplaceED((ArrayList<String>)refList.getPendingReferenceList(), refList);
/*      */       }
/* 1281 */       if (refList.getPendingReferenceList().size() > 0) {
/* 1282 */         String uri = refList.getPendingReferenceList().get(0);
/* 1283 */         throw new XWSSecurityException("Reference with ID " + uri + " was not found in the message");
/*      */       } 
/*      */     } else {
/* 1286 */       throw new XWSSecurityException("Need to support this header, please file a bug." + she);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleSignature(Signature sig) throws XWSSecurityException {
/* 1291 */     Reference bodyRef = null;
/* 1292 */     Reference payLoadRef = null;
/* 1293 */     if (this.bodyWsuId.length() > 0) {
/* 1294 */       bodyRef = sig.removeReferenceWithID("#" + this.bodyWsuId);
/*      */     }
/* 1296 */     if (this.payLoadWsuId.length() > 0) {
/* 1297 */       payLoadRef = sig.removeReferenceWithID("#" + this.payLoadWsuId);
/*      */     }
/* 1299 */     if (bodyRef != null && payLoadRef != null) {
/* 1300 */       throw new XWSSecurityException("Does not support signing of Body and PayLoad together");
/*      */     }
/*      */     
/* 1303 */     boolean validated = false;
/*      */     try {
/* 1305 */       validated = sig.validate();
/* 1306 */     } catch (XWSSecurityException xe) {
/* 1307 */       throw new WebServiceException(xe);
/*      */     } 
/* 1309 */     if (!validated) {
/* 1310 */       ArrayList<Reference> refs = sig.getReferences();
/* 1311 */       if (refs != null && refs.size() > 0) {
/* 1312 */         throw new WebServiceException("Could not find Reference " + ((Reference)refs.get(0)).getURI() + " under Signature with ID" + sig.getId());
/*      */       }
/* 1314 */       throw new XWSSecurityException("Verification of Signature with ID  " + sig.getId() + " failed, possible cause : proper canonicalized" + "signedinfo was not produced.");
/*      */     } 
/*      */ 
/*      */     
/* 1318 */     if (bodyRef != null) {
/* 1319 */       this.message = sig.wrapWithDigester((Reference)bodyRef, this.message, this.bodyPrologue, this.bodyEpilogue, this.bodyTag, this.parentNSOnEnvelope, false);
/* 1320 */     } else if (payLoadRef != null) {
/* 1321 */       this.message = sig.wrapWithDigester((Reference)payLoadRef, this.message, this.bodyPrologue, this.bodyEpilogue, this.bodyTag, this.bodyENVNS, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void handlePayLoadED(EncryptedData ed) throws XWSSecurityException, XMLStreamException {
/* 1327 */     if (this.pendingElement != null && this.pendingElement instanceof EncryptedKey) {
/* 1328 */       EncryptedKey ek = (EncryptedKey)this.pendingElement;
/* 1329 */       if (ek.getPendingReferenceList().contains(ed.getId())) {
/*      */         
/* 1331 */         if (ek.getPolicy() != null) {
/* 1332 */           ek.getPolicy().setKeyBinding((MLSPolicy)ek.getInferredKB());
/*      */         }
/*      */         
/* 1335 */         this.message = ed.getDecryptedData(ek.getKey(ed.getEncryptionAlgorithm()));
/* 1336 */         this.payLoadEncId = this.payLoadWsuId;
/* 1337 */         if (this.message != null && this.message.hasNext()) {
/* 1338 */           this.message.next();
/*      */         }
/* 1340 */         if (this.message == null) {
/* 1341 */           this.message = getEmptyBody();
/*      */         }
/* 1343 */         cachePayLoadId();
/* 1344 */         ek.getPendingReferenceList().remove(ed.getId());
/* 1345 */         findAndReplaceED((ArrayList<String>)ek.getPendingReferenceList(), ek);
/* 1346 */         if (ek.getPendingReferenceList().isEmpty()) {
/* 1347 */           this.pendingElement = null;
/*      */         } else {
/* 1349 */           String uri = ek.getPendingReferenceList().get(0);
/* 1350 */           throw new XWSSecurityException("Could not find Reference " + uri + " under EncryptedKey with ID" + ek.getId());
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1354 */       this.message = ed.getDecryptedData();
/* 1355 */       if (this.message != null && this.message.hasNext()) {
/* 1356 */         this.message.next();
/*      */       }
/* 1358 */       if (this.message == null) {
/* 1359 */         this.message = getEmptyBody();
/*      */       }
/*      */     } 
/* 1362 */     this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*      */   }
/*      */   
/*      */   private void moveToNextElement() throws XMLStreamException {
/* 1366 */     this.message.next();
/* 1367 */     while (this.message.getEventType() != 1) {
/* 1368 */       this.message.next();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isTimeStamp() {
/* 1373 */     if ("Timestamp".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(this.message.getNamespaceURI())) {
/* 1374 */       return true;
/*      */     }
/* 1376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isBST() {
/* 1380 */     if ("BinarySecurityToken".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(this.message.getNamespaceURI())) {
/* 1381 */       return true;
/*      */     }
/* 1383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isUsernameToken() {
/* 1387 */     if ("UsernameToken".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(this.message.getNamespaceURI())) {
/* 1388 */       return true;
/*      */     }
/* 1390 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSignature() {
/* 1394 */     if ("Signature".equals(this.message.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(this.message.getNamespaceURI())) {
/* 1395 */       return true;
/*      */     }
/* 1397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isEncryptedKey() {
/* 1401 */     if ("EncryptedKey".equals(this.message.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(this.message.getNamespaceURI())) {
/* 1402 */       return true;
/*      */     }
/* 1404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isReferenceList() {
/* 1408 */     if ("ReferenceList".equals(this.message.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(this.message.getNamespaceURI())) {
/* 1409 */       return true;
/*      */     }
/* 1411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isEncryptedData() {
/* 1415 */     if ("EncryptedData".equals(this.message.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(this.message.getNamespaceURI())) {
/* 1416 */       return true;
/*      */     }
/* 1418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isDerivedKey() {
/* 1422 */     if ("DerivedKeyToken".equals(this.message.getLocalName()) && ("http://schemas.xmlsoap.org/ws/2005/02/sc".equals(this.message.getNamespaceURI()) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512".equals(this.message.getNamespaceURI())))
/*      */     {
/* 1424 */       return true;
/*      */     }
/* 1426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSignatureConfirmation() {
/* 1430 */     if ("SignatureConfirmation".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(this.message.getNamespaceURI())) {
/* 1431 */       return true;
/*      */     }
/* 1433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSCT() {
/* 1437 */     if ("SecurityContextToken".equals(this.message.getLocalName()) && ("http://schemas.xmlsoap.org/ws/2005/02/sc".equals(this.message.getNamespaceURI()) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512".equals(this.message.getNamespaceURI())))
/*      */     {
/* 1439 */       return true;
/*      */     }
/* 1441 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isEncryptedHeader() {
/* 1445 */     if ("EncryptedHeader".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(this.message.getNamespaceURI())) {
/* 1446 */       return true;
/*      */     }
/* 1448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSTR() {
/* 1452 */     if ("SecurityTokenReference".equals(this.message.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(this.message.getNamespaceURI())) {
/* 1453 */       return true;
/*      */     }
/* 1455 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isSAML() {
/* 1459 */     if ("Assertion".equals(this.message.getLocalName())) {
/* 1460 */       String uri = this.message.getNamespaceURI();
/* 1461 */       if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(uri) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(uri) || "urn:oasis:names:tc:SAML:1.0:assertion".equals(uri)) {
/* 1462 */         return true;
/*      */       }
/*      */     } 
/* 1465 */     return false;
/*      */   }
/*      */   
/*      */   private int getSecurityElementType() {
/* 1469 */     if (this.message.getEventType() == 1) {
/* 1470 */       if (isTimeStamp()) {
/* 1471 */         return 1;
/*      */       }
/*      */       
/* 1474 */       if (isUsernameToken()) {
/* 1475 */         return 2;
/*      */       }
/*      */       
/* 1478 */       if (isBST()) {
/* 1479 */         return 3;
/*      */       }
/*      */       
/* 1482 */       if (isSignature()) {
/* 1483 */         return 6;
/*      */       }
/*      */       
/* 1486 */       if (isEncryptedKey()) {
/* 1487 */         return 5;
/*      */       }
/*      */       
/* 1490 */       if (isEncryptedData()) {
/* 1491 */         return 4;
/*      */       }
/*      */       
/* 1494 */       if (isEncryptedHeader()) {
/* 1495 */         return 12;
/*      */       }
/*      */       
/* 1498 */       if (isReferenceList()) {
/* 1499 */         return 7;
/*      */       }
/*      */       
/* 1502 */       if (isSignatureConfirmation()) {
/* 1503 */         return 9;
/*      */       }
/*      */       
/* 1506 */       if (isDerivedKey()) {
/* 1507 */         return 8;
/*      */       }
/*      */       
/* 1510 */       if (isSCT()) {
/* 1511 */         return 10;
/*      */       }
/*      */       
/* 1514 */       if (isSAML()) {
/* 1515 */         return 11;
/*      */       }
/*      */       
/* 1518 */       if (isSTR()) {
/* 1519 */         return 13;
/*      */       }
/*      */     } 
/* 1522 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void findAndReplaceED(ArrayList<String> edList, SecurityHeaderElement ekOrRlh) throws XWSSecurityException {
/* 1527 */     EncryptedKey ek = null;
/* 1528 */     ReferenceListHeader rlh = null;
/* 1529 */     if (ekOrRlh instanceof EncryptedKey) {
/* 1530 */       ek = (EncryptedKey)ekOrRlh;
/* 1531 */     } else if (ekOrRlh instanceof ReferenceListHeader) {
/* 1532 */       rlh = (ReferenceListHeader)ekOrRlh;
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/* 1537 */     ArrayList<String> refList = (ArrayList<String>)edList.clone();
/*      */     
/* 1539 */     for (int i = 0; i < refList.size(); i++) {
/* 1540 */       String id = refList.get(i);
/* 1541 */       boolean found = false;
/*      */       
/* 1543 */       Iterator<Header> listItr = this.headers.listIterator();
/* 1544 */       while (listItr.hasNext()) {
/* 1545 */         GenericSecuredHeader header = (GenericSecuredHeader)listItr.next();
/* 1546 */         String localPart = (header != null) ? header.getLocalPart() : null;
/* 1547 */         boolean isEncHeader = "EncryptedHeader".equals(localPart);
/* 1548 */         GenericSecuredHeader processedHeader = null;
/* 1549 */         if (isEncHeader) {
/* 1550 */           processedHeader = processEncryptedSOAPHeader(header, ekOrRlh);
/*      */         }
/* 1552 */         if (header.hasID(id) || id.equals(this.context.getEdIdforEh())) {
/* 1553 */           if (processedHeader == null) {
/* 1554 */             processedHeader = processEncryptedSOAPHeader(header, ekOrRlh);
/*      */           }
/* 1556 */           edList.remove(id);
/* 1557 */           this.context.setEdIdforEh(null);
/* 1558 */           int index = this.headers.indexOf(header);
/* 1559 */           this.headers.set(index, processedHeader);
/* 1560 */           found = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1564 */       if (!found) {
/*      */         int j;
/*      */         
/* 1567 */         for (j = 0; j < this.processedHeaders.size(); j++) {
/* 1568 */           SecurityHeaderElement header = this.processedHeaders.get(j);
/* 1569 */           if (id.equals(header.getId()) && 
/* 1570 */             header instanceof EncryptedData) {
/* 1571 */             found = true;
/* 1572 */             throw new XWSSecurityException("EncryptedKey or ReferenceList must appear before EncryptedData element with ID" + header.getId());
/*      */           } 
/*      */         } 
/*      */         
/* 1576 */         if (!found)
/*      */         {
/*      */           
/* 1579 */           for (j = 0; j < this.bufferedHeaders.size(); j++) {
/* 1580 */             SecurityHeaderElement header = this.bufferedHeaders.get(j);
/* 1581 */             if (id.equals(header.getId()) && 
/* 1582 */               header instanceof EncryptedData) {
/* 1583 */               EncryptedData ed = (EncryptedData)header;
/* 1584 */               if (!ed.hasCipherReference()) {
/* 1585 */                 XMLStreamReader decryptedData = null;
/*      */                 try {
/* 1587 */                   if (ek != null) {
/* 1588 */                     if (ek.getPolicy() != null)
/*      */                     {
/* 1590 */                       ek.getPolicy().setKeyBinding((MLSPolicy)ek.getInferredKB());
/*      */                     }
/* 1592 */                     decryptedData = ed.getDecryptedData(ek.getKey(ed.getEncryptionAlgorithm()));
/* 1593 */                   } else if (rlh != null) {
/* 1594 */                     rlh.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/* 1595 */                     decryptedData = ed.getDecryptedData();
/*      */                   } else {
/* 1597 */                     throw new XWSSecurityException("Internal Error: Both EncryptedKey and ReferenceList are set to null");
/*      */                   } 
/*      */ 
/*      */                   
/* 1601 */                   SecurityHeaderProcessor shp = new SecurityHeaderProcessor(this.context, this.envshNS, this.staxIF, this.creator);
/* 1602 */                   if (decryptedData.getEventType() != 1) {
/* 1603 */                     StreamUtil.moveToNextElement(decryptedData);
/*      */                   }
/* 1605 */                   SecurityHeaderElement she = shp.createHeader(decryptedData);
/* 1606 */                   edList.remove(ed.getId());
/* 1607 */                   this.encIds.put(ed.getId(), she.getId());
/* 1608 */                   this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/* 1609 */                   this.bufferedHeaders.set(i, she);
/* 1610 */                 } catch (XMLStreamException ex) {
/* 1611 */                   logger.log(Level.SEVERE, LogStringsMessages.WSS_1615_ERROR_DECRYPTING_ENCRYPTED_DATA(), ex);
/* 1612 */                   throw new XWSSecurityException("Error occurred while decrypting EncryptedData with ID " + ed.getId(), ex);
/*      */                 } 
/*      */               } else {
/*      */                 
/* 1616 */                 byte[] decryptedMimeData = null;
/* 1617 */                 if (ek != null) {
/* 1618 */                   decryptedMimeData = ed.getDecryptedMimeData(ek.getKey(ed.getEncryptionAlgorithm()));
/* 1619 */                 } else if (rlh != null) {
/* 1620 */                   decryptedMimeData = ed.getDecryptedMimeData();
/*      */                 } else {
/* 1622 */                   throw new XWSSecurityException("Internal Error: Both EncryptedKey and ReferenceList are set to null");
/*      */                 } 
/* 1624 */                 AttachmentImpl attachmentImpl = new AttachmentImpl(ed.getAttachmentContentId(), decryptedMimeData, ed.getAttachmentMimeType());
/* 1625 */                 this.securityContext.getDecryptedAttachmentSet().add((Attachment)attachmentImpl);
/* 1626 */                 edList.remove(ed.getId());
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private GenericSecuredHeader processEncryptedSOAPHeader(GenericSecuredHeader header, SecurityHeaderElement ekOrRlh) throws XWSSecurityException {
/* 1637 */     EncryptedKey ek = null;
/* 1638 */     ReferenceListHeader rlh = null;
/* 1639 */     if (ekOrRlh instanceof EncryptedKey) {
/* 1640 */       ek = (EncryptedKey)ekOrRlh;
/* 1641 */     } else if (ekOrRlh instanceof ReferenceListHeader) {
/* 1642 */       rlh = (ReferenceListHeader)ekOrRlh;
/*      */     } 
/*      */     
/*      */     try {
/* 1646 */       XMLStreamReader reader = header.readHeader();
/* 1647 */       if (reader.getEventType() == 7) {
/* 1648 */         reader.next();
/*      */       }
/* 1650 */       if (reader.getEventType() != 1) {
/* 1651 */         StreamUtil.moveToNextElement(reader);
/*      */       }
/* 1653 */       XMLStreamReader decryptedData = null;
/* 1654 */       InputStream decryptedIS = null;
/* 1655 */       EncryptedData ed = null;
/* 1656 */       EncryptedHeader eh = null;
/* 1657 */       boolean encContent = false;
/* 1658 */       EncryptedContentHeaderParser encContentparser = null;
/* 1659 */       if ("EncryptedData".equals(reader.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(reader.getNamespaceURI())) {
/* 1660 */         ed = new EncryptedData(reader, this.context, this.parentNS);
/* 1661 */       } else if ("EncryptedHeader".equals(reader.getLocalName()) && "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(reader.getNamespaceURI())) {
/* 1662 */         eh = new EncryptedHeader(reader, this.context, this.parentNS);
/* 1663 */         ed = eh.getEncryptedData();
/* 1664 */       } else if (this.context.getEncHeaderContent()) {
/*      */         
/* 1666 */         encContent = true;
/*      */         
/* 1668 */         encContentparser = new EncryptedContentHeaderParser(reader, this.parentNS, this.context);
/* 1669 */         ed = encContentparser.getEncryptedData();
/*      */       } else {
/* 1671 */         throw new XWSSecurityException("Wrong Encrypted SOAP Header");
/*      */       } 
/* 1673 */       if (ed != null) {
/* 1674 */         this.context.setEdIdforEh(ed.getId());
/*      */       }
/*      */ 
/*      */       
/* 1678 */       if (!encContent) {
/* 1679 */         if (ek != null) {
/* 1680 */           if (ek.getPolicy() != null) {
/* 1681 */             ek.getPolicy().setKeyBinding((MLSPolicy)ek.getInferredKB());
/*      */           }
/* 1683 */           decryptedData = ed.getDecryptedData(ek.getKey(ed.getEncryptionAlgorithm()));
/* 1684 */         } else if (rlh != null) {
/* 1685 */           rlh.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/* 1686 */           decryptedData = ed.getDecryptedData();
/*      */         } else {
/* 1688 */           throw new XWSSecurityException("Internal Error: Both EncryptedKey and ReferenceList set to null");
/*      */         } 
/*      */ 
/*      */         
/* 1692 */         if (decryptedData.getEventType() == 7) {
/* 1693 */           decryptedData.next();
/*      */         }
/* 1695 */         if (decryptedData.getEventType() != 1) {
/* 1696 */           StreamUtil.moveToNextElement(decryptedData);
/*      */         }
/*      */       }
/* 1699 */       else if (ek != null) {
/* 1700 */         if (ek.getPolicy() != null) {
/* 1701 */           ek.getPolicy().setKeyBinding((MLSPolicy)ek.getInferredKB());
/*      */         }
/* 1703 */         decryptedIS = ed.getCipherInputStream(ek.getKey(ed.getEncryptionAlgorithm()));
/* 1704 */       } else if (rlh != null) {
/* 1705 */         rlh.getPolicy().setKeyBinding((MLSPolicy)ed.getInferredKB());
/* 1706 */         decryptedIS = ed.getCipherInputStream();
/*      */       } 
/*      */ 
/*      */       
/* 1710 */       GenericSecuredHeader gsh = null;
/* 1711 */       if (!encContent) {
/* 1712 */         Map<String, String> headerBlockNamespaces = this.parentNS;
/*      */         
/* 1714 */         if (decryptedData.getNamespaceCount() > 0) {
/* 1715 */           headerBlockNamespaces = new HashMap<String, String>(this.parentNS);
/* 1716 */           for (int k = 0; k < decryptedData.getNamespaceCount(); k++) {
/* 1717 */             headerBlockNamespaces.put(decryptedData.getNamespacePrefix(k), decryptedData.getNamespaceURI(k));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1722 */         gsh = new GenericSecuredHeader(decryptedData, this.soapVersion, this.creator, (HashMap)headerBlockNamespaces, this.staxIF, this.context.getEncHeaderContent());
/*      */       } else {
/*      */         
/* 1725 */         XMLStreamReader decryptedHeader = encContentparser.getDecryptedElement(decryptedIS);
/*      */         
/* 1727 */         if (decryptedHeader.getEventType() == 7) {
/* 1728 */           decryptedHeader.next();
/*      */         }
/* 1730 */         if (decryptedHeader.getEventType() != 1) {
/* 1731 */           StreamUtil.moveToNextElement(decryptedHeader);
/*      */         }
/* 1733 */         Map<String, String> headerBlockNamespaces = this.parentNS;
/*      */         
/* 1735 */         if (decryptedHeader.getNamespaceCount() > 0) {
/* 1736 */           headerBlockNamespaces = new HashMap<String, String>(this.parentNS);
/* 1737 */           for (int k = 0; k < decryptedHeader.getNamespaceCount(); k++) {
/* 1738 */             String prefix = decryptedHeader.getNamespacePrefix(k);
/* 1739 */             if (prefix == null) {
/* 1740 */               prefix = "";
/*      */             }
/* 1742 */             headerBlockNamespaces.put(prefix, decryptedHeader.getNamespaceURI(k));
/*      */           } 
/*      */         } 
/* 1745 */         gsh = new GenericSecuredHeader(decryptedHeader, this.soapVersion, this.creator, (HashMap)headerBlockNamespaces, this.staxIF, this.context.getEncHeaderContent());
/*      */       } 
/* 1747 */       QName gshQName = new QName(gsh.getNamespaceURI(), gsh.getLocalPart());
/* 1748 */       if (eh != null) {
/* 1749 */         this.encQNames.put(eh.getId(), gshQName);
/* 1750 */         this.edAlgos.put(eh.getId(), ed.getEncryptionAlgorithm());
/*      */       } else {
/* 1752 */         this.encQNames.put(ed.getId(), gshQName);
/* 1753 */         this.edAlgos.put(ed.getId(), ed.getEncryptionAlgorithm());
/*      */       } 
/*      */       
/* 1756 */       return gsh;
/* 1757 */     } catch (XMLStreamException ex) {
/* 1758 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1615_ERROR_DECRYPTING_ENCRYPTED_DATA(), ex);
/* 1759 */       throw new XWSSecurityException("Error occurred while decrypting EncryptedData ", ex);
/* 1760 */     } catch (XMLStreamBufferException ex) {
/* 1761 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1615_ERROR_DECRYPTING_ENCRYPTED_DATA(), (Throwable)ex);
/* 1762 */       throw new XWSSecurityException("Error occurred while decrypting EncryptedData", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addSecurityHeader(SecurityHeaderElement sh) {
/* 1768 */     if (this.pendingElement == null || sh instanceof com.sun.xml.ws.security.opt.api.TokenValidator) {
/* 1769 */       this.processedHeaders.add(sh);
/*      */     } else {
/* 1771 */       this.bufferedHeaders.add(sh);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cachePayLoadId() {
/* 1776 */     if (this.message != null && this.message.getEventType() == 1) {
/* 1777 */       this.payLoadWsuId = StreamUtil.getWsuId(this.message);
/* 1778 */       if (this.payLoadWsuId == null) {
/* 1779 */         this.payLoadWsuId = StreamUtil.getId(this.message);
/*      */       }
/* 1781 */       if (this.payLoadWsuId == null) {
/* 1782 */         this.payLoadWsuId = "";
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean areHeadersSecured(SignaturePolicy sp) {
/* 1788 */     ArrayList<SignatureTarget> list = ((SignaturePolicy.FeatureBinding)sp.getFeatureBinding()).getTargetBindings();
/* 1789 */     HeaderList<Header> headerList = this.headers;
/* 1790 */     for (int hl = 0; hl < headerList.size(); hl++) {
/* 1791 */       Header hdr = headerList.get(hl);
/* 1792 */       String localName = hdr.getLocalPart();
/* 1793 */       String uri = hdr.getNamespaceURI();
/* 1794 */       boolean found = false;
/* 1795 */       if ("http://www.w3.org/2005/08/addressing".equals(uri) || "http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(uri)) {
/* 1796 */         for (int i = 0; i < list.size(); i++) {
/* 1797 */           SignatureTarget st = list.get(i);
/* 1798 */           QName value = st.getQName();
/* 1799 */           if (value.getLocalPart().equals(localName) && value.getNamespaceURI().equals(uri)) {
/* 1800 */             found = true;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1804 */         if (!found) {
/* 1805 */           return found;
/*      */         }
/*      */       } 
/*      */     } 
/* 1809 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private XMLStreamReader getEmptyBodyStart() throws XMLStreamException {
/* 1814 */     String emptyBody = "<S:Body xmlns:S=\"" + this.soapVersion.nsUri + "\"" + "></S:Body>";
/* 1815 */     InputStream in = new ByteArrayInputStream(emptyBody.getBytes());
/* 1816 */     XMLInputFactory xif = XMLInputFactory.newInstance();
/* 1817 */     XMLStreamReader empBody = xif.createXMLStreamReader(in);
/* 1818 */     empBody.next();
/* 1819 */     return empBody;
/*      */   }
/*      */   private void setExceptionMessage(JAXBFilterProcessingContext ctx) {
/* 1822 */     XMLStreamReader rdr = null;
/*      */     try {
/* 1824 */       rdr = getEmptyBodyStart();
/* 1825 */     } catch (Exception e) {}
/*      */ 
/*      */     
/* 1828 */     if (this.bodyTag == null) {
/* 1829 */       this.bodyTag = new TagInfoset(rdr);
/*      */     }
/* 1831 */     StreamMessage streamMessage = new StreamMessage(this.envelopeTag, this.headerTag, (AttachmentSet)new AttachmentSetImpl(), this.headers, this.bodyTag, rdr, this.soapVersion);
/* 1832 */     ctx.setPVMessage((Message)streamMessage);
/*      */   }
/*      */   
/*      */   private void updateContext(String action, JAXBFilterProcessingContext ctx) {
/* 1836 */     if (action != null) {
/* 1837 */       ctx.setAction(action);
/*      */       
/* 1839 */       if (isSCRenew(action, ctx)) {
/* 1840 */         ctx.isExpired(true);
/*      */       }
/*      */       
/* 1843 */       if (action != null && (action.contains("/RST/SCT") || action.contains("/RSTR/SCT")) && 
/* 1844 */         ctx.getBootstrapAlgoSuite() != null) {
/* 1845 */         ctx.setAlgorithmSuite(ctx.getBootstrapAlgoSuite());
/*      */       }
/*      */ 
/*      */       
/* 1849 */       if (isTrustMessage(action, ctx)) {
/* 1850 */         ctx.isTrustMessage(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isSCRenew(String action, JAXBFilterProcessingContext ctx) {
/* 1857 */     if (!ctx.isAddressingEnabled()) {
/* 1858 */       return false;
/*      */     }
/* 1860 */     if (ctx.getWsscVer() == null) {
/* 1861 */       return false;
/*      */     }
/* 1863 */     return (ctx.getWsscVer().getSCTRenewResponseAction().equals(action) || ctx.getWsscVer().getSCTRenewRequestAction().equals(action));
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isTrustMessage(String action, JAXBFilterProcessingContext ctx) {
/* 1868 */     if (!ctx.isAddressingEnabled()) {
/* 1869 */       return false;
/*      */     }
/* 1871 */     WSTrustVersion wsTrustVer = ctx.getWsTrustVer();
/* 1872 */     if (wsTrustVer == null) {
/* 1873 */       return false;
/*      */     }
/*      */     
/* 1876 */     if (wsTrustVer.getIssueRequestAction().equals(action) || wsTrustVer.getIssueFinalResoponseAction().equals(action))
/*      */     {
/* 1878 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1882 */     return (wsTrustVer.getValidateRequestAction().equals(action) || wsTrustVer.getValidateFinalResoponseAction().equals(action));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\SecurityRecipient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */