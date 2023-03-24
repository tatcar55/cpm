/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.encoding.TagInfoset;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.PolicyBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignatureValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignedInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.DigesterOutputStream;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBValidateContext;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.ExcC14NParameterSpec;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.KeyInfoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.SignedInfoProcessor;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.processor.StreamingPayLoadDigester;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.XMLStreamReaderFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Signature
/*     */   implements SecurityHeaderElement, NamespaceContextInfo, SecurityElementWriter, PolicyBuilder
/*     */ {
/* 120 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   public static final int SIGNEDINFO_EVENT = 1;
/*     */   
/*     */   public static final int SIGNATUREVALUE_EVENT = 2;
/*     */   
/*     */   public static final int KEYINFO_EVENT = 3;
/*     */   
/*     */   public static final int OBJECT_EVENT = 4;
/*     */   public static final String SIGNED_INFO = "SignedInfo";
/*     */   public static final String SIGNATURE_VALUE = "SignatureValue";
/*     */   public static final String KEYINFO = "KeyInfo";
/*     */   public static final String OBJECT = "Object";
/* 133 */   private SignaturePolicy signPolicy = null;
/*     */   
/* 135 */   private HashMap<String, String> currentParentNS = new HashMap<String, String>();
/*     */   private JAXBFilterProcessingContext context;
/* 137 */   private StreamReaderBufferCreator creator = null;
/* 138 */   private SecurityContext securityContext = null;
/* 139 */   private String id = "";
/* 140 */   private SignedInfoProcessor sip = null;
/* 141 */   private XMLStreamWriter canonWriter = null;
/* 142 */   private com.sun.xml.ws.security.opt.crypto.dsig.Signature sig = null;
/* 143 */   private JAXBValidateContext jvc = new JAXBValidateContext();
/* 144 */   private SignedInfo si = null;
/*     */   private boolean validationStatus = false;
/* 146 */   private MutableXMLStreamBuffer buffer = null;
/*     */ 
/*     */   
/*     */   private boolean cacheSignature = false;
/*     */   
/*     */   private boolean storeSigConfirmValue = true;
/*     */ 
/*     */   
/*     */   public Signature(JAXBFilterProcessingContext jpc, Map<String, String> namespaceList, StreamReaderBufferCreator sbc) {
/* 155 */     this.currentParentNS.putAll(namespaceList);
/* 156 */     this.creator = sbc;
/* 157 */     this.securityContext = jpc.getSecurityContext();
/* 158 */     this.context = jpc;
/* 159 */     this.cacheSignature = true;
/* 160 */     this.signPolicy = new SignaturePolicy();
/* 161 */     this.signPolicy.setFeatureBinding((MLSPolicy)new SignaturePolicy.FeatureBinding());
/*     */   }
/*     */ 
/*     */   
/*     */   public Signature(JAXBFilterProcessingContext jpc, Map<String, String> namespaceList, StreamReaderBufferCreator sbc, boolean cacheSig) {
/* 166 */     this.currentParentNS.putAll(namespaceList);
/* 167 */     this.creator = sbc;
/* 168 */     this.securityContext = jpc.getSecurityContext();
/* 169 */     this.context = jpc;
/* 170 */     this.cacheSignature = cacheSig;
/* 171 */     this.signPolicy = new SignaturePolicy();
/* 172 */     this.signPolicy.setFeatureBinding((MLSPolicy)new SignaturePolicy.FeatureBinding());
/*     */   }
/*     */   
/*     */   public void process(XMLStreamReader signature) throws XWSSecurityException {
/*     */     try {
/* 177 */       XMLStreamReader reader = null;
/*     */       
/* 179 */       this.buffer = new MutableXMLStreamBuffer();
/* 180 */       this.buffer.createFromXMLStreamReader(signature);
/* 181 */       StreamReaderBufferProcessor streamReaderBufferProcessor = this.buffer.readAsXMLStreamReader();
/* 182 */       streamReaderBufferProcessor.next();
/* 183 */       byte[] signatureValue = null;
/* 184 */       TagInfoset signatureRoot = new TagInfoset((XMLStreamReader)streamReaderBufferProcessor);
/* 185 */       for (int i = 0; i < streamReaderBufferProcessor.getNamespaceCount(); i++) {
/* 186 */         String prefix = streamReaderBufferProcessor.getNamespacePrefix(i);
/* 187 */         String uri = streamReaderBufferProcessor.getNamespaceURI(i);
/* 188 */         if (prefix == null) {
/* 189 */           prefix = "";
/*     */         }
/* 191 */         this.currentParentNS.put(prefix, uri);
/*     */       } 
/* 193 */       this.context.setCurrentBuffer((XMLStreamBuffer)this.buffer);
/* 194 */       Key key = null;
/* 195 */       this.id = streamReaderBufferProcessor.getAttributeValue(null, "Id");
/* 196 */       if (this.id == null || this.id.length() == 0) {
/* 197 */         this.id = "pvid" + this.context.generateID();
/* 198 */         if (logger.isLoggable(Level.FINEST)) {
/* 199 */           logger.log(Level.FINEST, LogStringsMessages.WSS_1755_MISSINGID_INCOMING_SIGNATURE(this.id));
/*     */         }
/*     */       } 
/*     */       
/* 203 */       this.signPolicy.setUUID(this.id);
/*     */       
/* 205 */       if (StreamUtil.moveToNextElement((XMLStreamReader)streamReaderBufferProcessor)) {
/* 206 */         int refElement = getEventType((XMLStreamReader)streamReaderBufferProcessor);
/* 207 */         while (streamReaderBufferProcessor.getEventType() != 8) {
/* 208 */           List<byte[]> scList; KeyInfoProcessor kip; MLSPolicy inferredKB; switch (refElement) {
/*     */             case 1:
/* 210 */               this.sip = new SignedInfoProcessor(signatureRoot, this.currentParentNS, (XMLStreamReader)streamReaderBufferProcessor, this.context, this.signPolicy, this.buffer);
/* 211 */               this.si = this.sip.process();
/* 212 */               this.canonWriter = this.sip.getCanonicalizer();
/*     */               break;
/*     */             
/*     */             case 2:
/* 216 */               if (this.canonWriter == null) {
/* 217 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1707_ERROR_PROCESSING_SIGNEDINFO(this.id));
/* 218 */                 throw new XWSSecurityException("Elements under Signature are not as per defined schema or error must have occurred while processing SignedInfo for Signature with ID" + this.id);
/*     */               } 
/*     */               
/* 221 */               StreamUtil.writeCurrentEvent((XMLStreamReader)streamReaderBufferProcessor, this.canonWriter);
/*     */               
/* 223 */               if (streamReaderBufferProcessor instanceof XMLStreamReaderEx) {
/* 224 */                 streamReaderBufferProcessor.next();
/* 225 */                 StringBuffer sb = null;
/* 226 */                 while (streamReaderBufferProcessor.getEventType() == 4 && streamReaderBufferProcessor.getEventType() != 2) {
/* 227 */                   CharSequence charSeq = ((XMLStreamReaderEx)streamReaderBufferProcessor).getPCDATA();
/* 228 */                   if (charSeq instanceof Base64Data) {
/* 229 */                     Base64Data bd = (Base64Data)((XMLStreamReaderEx)streamReaderBufferProcessor).getPCDATA();
/* 230 */                     signatureValue = bd.getExact();
/* 231 */                     this.canonWriter.writeCharacters(Base64.encode(signatureValue));
/*     */                   } else {
/* 233 */                     if (sb == null) {
/* 234 */                       sb = new StringBuffer();
/*     */                     }
/* 236 */                     for (int j = 0; j < charSeq.length(); j++) {
/* 237 */                       sb.append(charSeq.charAt(j));
/*     */                     }
/*     */                   } 
/* 240 */                   streamReaderBufferProcessor.next();
/*     */                 } 
/* 242 */                 if (sb != null) {
/* 243 */                   String tmp = sb.toString();
/* 244 */                   this.canonWriter.writeCharacters(tmp);
/*     */                   try {
/* 246 */                     signatureValue = Base64.decode(tmp);
/* 247 */                   } catch (Base64DecodingException dec) {
/* 248 */                     logger.log(Level.SEVERE, LogStringsMessages.WSS_1708_BASE_64_DECODING_ERROR(this.id));
/* 249 */                     throw new XWSSecurityException("Error occurred while decoding signatureValue for Signature with ID" + this.id);
/*     */                   } 
/*     */                 } else {
/* 252 */                   streamReaderBufferProcessor.next();
/*     */                 } 
/*     */               } else {
/* 255 */                 String tmp = StreamUtil.getCV((XMLStreamReader)streamReaderBufferProcessor);
/*     */                 
/* 257 */                 this.canonWriter.writeCharacters(tmp);
/*     */                 try {
/* 259 */                   signatureValue = Base64.decode(tmp);
/* 260 */                 } catch (Base64DecodingException dec) {
/* 261 */                   logger.log(Level.SEVERE, LogStringsMessages.WSS_1708_BASE_64_DECODING_ERROR(this.id), dec);
/* 262 */                   throw new XWSSecurityException("Error occurred while decoding signatureValue for Signature with ID" + this.id);
/*     */                 } 
/*     */               } 
/*     */               
/* 266 */               scList = (ArrayList)this.context.getExtraneousProperty("receivedSignValues");
/* 267 */               if (scList != null && this.storeSigConfirmValue) {
/* 268 */                 scList.add(signatureValue);
/*     */               }
/*     */               break;
/*     */ 
/*     */             
/*     */             case 3:
/* 274 */               if (this.canonWriter == null) {
/* 275 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1707_ERROR_PROCESSING_SIGNEDINFO(this.id));
/* 276 */                 throw new XWSSecurityException("Elements under Signature are not as per defined schema or error must have occurred while processing SignedInfo for Signature with ID" + this.id);
/*     */               } 
/*     */ 
/*     */               
/* 280 */               this.securityContext.setInferredKB(null);
/* 281 */               kip = new KeyInfoProcessor(this.context, KeySelector.Purpose.VERIFY);
/* 282 */               key = kip.getKey((XMLStreamReader)streamReaderBufferProcessor);
/* 283 */               if (key instanceof PublicKey) {
/* 284 */                 X509Certificate cert = null;
/*     */                 try {
/* 286 */                   cert = this.context.getSecurityEnvironment().getCertificate(this.context.getExtraneousProperties(), (PublicKey)key, false);
/* 287 */                 } catch (XWSSecurityException ex) {
/* 288 */                   if (logger.isLoggable(Level.FINE)) {
/* 289 */                     logger.log(Level.FINE, "", (Throwable)ex);
/*     */                   }
/*     */                 } 
/* 292 */                 if (cert != null && !this.context.isSamlSignatureKey()) {
/* 293 */                   this.context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)this.context), cert);
/*     */                 }
/*     */               } 
/* 296 */               inferredKB = this.securityContext.getInferredKB();
/* 297 */               this.signPolicy.setKeyBinding(inferredKB);
/* 298 */               this.securityContext.setInferredKB(null);
/*     */               break;
/*     */             
/*     */             default:
/* 302 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1709_UNRECOGNIZED_SIGNATURE_ELEMENT(streamReaderBufferProcessor.getName()));
/* 303 */               throw new XWSSecurityException("Element name " + streamReaderBufferProcessor.getName() + " is not recognized under Signature");
/*     */           } 
/*     */ 
/*     */           
/* 307 */           if (StreamUtil._break((XMLStreamReader)streamReaderBufferProcessor, "Signature", "http://www.w3.org/2000/09/xmldsig#")) {
/* 308 */             streamReaderBufferProcessor.next();
/*     */             
/*     */             break;
/*     */           } 
/* 312 */           if (!StreamUtil.isStartElement((XMLStreamReader)streamReaderBufferProcessor) && StreamUtil.moveToNextStartOREndElement((XMLStreamReader)streamReaderBufferProcessor) && StreamUtil._break((XMLStreamReader)streamReaderBufferProcessor, "Signature", "http://www.w3.org/2000/09/xmldsig#")) {
/*     */             
/* 314 */             streamReaderBufferProcessor.next();
/*     */             break;
/*     */           } 
/* 317 */           if (streamReaderBufferProcessor.getEventType() != 1) {
/* 318 */             StreamUtil.moveToNextStartOREndElement((XMLStreamReader)streamReaderBufferProcessor);
/* 319 */             boolean isBreak = false;
/* 320 */             while (streamReaderBufferProcessor.getEventType() == 2) {
/* 321 */               if (StreamUtil._break((XMLStreamReader)streamReaderBufferProcessor, "Signature", "http://www.w3.org/2001/04/xmlenc#")) {
/* 322 */                 isBreak = true;
/*     */                 break;
/*     */               } 
/* 325 */               StreamUtil.moveToNextStartOREndElement((XMLStreamReader)streamReaderBufferProcessor);
/*     */             } 
/* 327 */             if (isBreak)
/*     */               break; 
/* 329 */             if (streamReaderBufferProcessor.getEventType() == 8) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 334 */           refElement = getEventType((XMLStreamReader)streamReaderBufferProcessor);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 339 */       this.sig = new com.sun.xml.ws.security.opt.crypto.dsig.Signature();
/* 340 */       SignatureValue sv = new SignatureValue();
/* 341 */       sv.setValue(signatureValue);
/* 342 */       this.sig.setSignatureValue(sv);
/*     */       
/* 344 */       this.jvc.setURIDereferencer(new URIResolver(this.context));
/* 345 */       this.sig.setSignedInfo(this.si);
/* 346 */       this.sig.setVerificationKey(key);
/* 347 */       if (this.sip.getReferenceList().size() == 0) {
/* 348 */         if (!this.sig.validate((XMLValidateContext)this.jvc)) {
/* 349 */           this.validationStatus = true;
/* 350 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1710_SIGNATURE_VERFICATION_FAILED(this.id));
/* 351 */           throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1710_SIGNATURE_VERFICATION_FAILED(this.id), null);
/*     */         } 
/* 353 */         this.validationStatus = true;
/*     */       }
/*     */     
/* 356 */     } catch (XMLStreamException ex) {
/* 357 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), ex);
/* 358 */       throw new XWSSecurityException(ex);
/* 359 */     } catch (XMLSignatureException xse) {
/* 360 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), xse);
/* 361 */       throw new XWSSecurityException(xse);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void process(XMLStreamReader signature, boolean storeSigConfirmValue) throws XWSSecurityException {
/* 366 */     this.storeSigConfirmValue = storeSigConfirmValue;
/* 367 */     process(signature);
/* 368 */     this.storeSigConfirmValue = true;
/*     */   }
/*     */   
/*     */   public boolean validate() throws XWSSecurityException {
/* 372 */     if (isReady()) {
/*     */       try {
/* 374 */         boolean status = this.sig.validate((XMLValidateContext)this.jvc);
/* 375 */         this.validationStatus = true;
/* 376 */         return status;
/* 377 */       } catch (XMLSignatureException ex) {
/* 378 */         this.validationStatus = true;
/* 379 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1713_SIGNATURE_VERIFICATION_EXCEPTION(ex.getMessage()), ex);
/* 380 */         throw new XWSSecurityException(ex);
/*     */       } 
/*     */     }
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference removeReferenceWithID(String id) {
/* 389 */     ArrayList<Reference> refList = this.sip.getReferenceList();
/* 390 */     Reference ref = null;
/* 391 */     for (int i = 0; i < refList.size(); i++) {
/* 392 */       if (((Reference)refList.get(i)).getURI().equals(id)) {
/* 393 */         ref = this.sip.getReferenceList().remove(i);
/*     */         break;
/*     */       } 
/*     */     } 
/* 397 */     return ref;
/*     */   }
/*     */   
/*     */   public ArrayList<Reference> getReferences() {
/* 401 */     return this.sip.getReferenceList();
/*     */   }
/*     */   
/*     */   public boolean isValidated() {
/* 405 */     return this.validationStatus;
/*     */   }
/*     */   
/*     */   public boolean isReady() throws XWSSecurityException {
/* 409 */     if (this.sip.getReferenceList().size() == 0) {
/* 410 */       return true;
/*     */     }
/* 412 */     ArrayList<Reference> refList = (ArrayList<Reference>)this.sip.getReferenceList().clone();
/* 413 */     for (int i = 0; i < refList.size(); i++) {
/* 414 */       Reference ref = refList.get(i);
/* 415 */       if (this.sip.processReference(ref)) {
/* 416 */         this.sip.getReferenceList().remove(ref);
/*     */       }
/*     */     } 
/*     */     
/* 420 */     if (this.sip.getReferenceList().size() == 0) {
/* 421 */       return true;
/*     */     }
/* 423 */     return false;
/*     */   }
/*     */   
/*     */   public boolean verifyReferences() {
/* 427 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean verifySignatureValue() {
/* 431 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) {
/* 435 */     if (reader.getEventType() == 1) {
/* 436 */       if (reader.getLocalName() == "SignedInfo") {
/* 437 */         return 1;
/*     */       }
/* 439 */       if (reader.getLocalName() == "SignatureValue") {
/* 440 */         return 2;
/*     */       }
/* 442 */       if (reader.getLocalName() == "KeyInfo") {
/* 443 */         return 3;
/*     */       }
/* 445 */       if (reader.getLocalName() == "Object") {
/* 446 */         return 4;
/*     */       }
/*     */     } 
/* 449 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean _break(XMLStreamReader reader) throws XMLStreamException {
/* 453 */     if (reader.getEventType() == 2 && 
/* 454 */       reader.getLocalName() == "Signature" && reader.getNamespaceURI() == "http://www.w3.org/2000/09/xmldsig#") {
/* 455 */       reader.next();
/* 456 */       return true;
/*     */     } 
/*     */     
/* 459 */     return false;
/*     */   }
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 463 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 467 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 471 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 475 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 479 */     return "Signature";
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 483 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 487 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 491 */     if (this.buffer != null) {
/* 492 */       this.buffer.writeToXMLStreamWriter(streamWriter);
/*     */     } else {
/* 494 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1712_UNBUFFERED_SIGNATURE_ERROR());
/* 495 */       throw new XMLStreamException("Signature is not buffered , message not as per configured policy");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader wrapWithDigester(Reference ref, XMLStreamReader message, String bodyPrologue, String bodyEpilogue, TagInfoset bodyTag, HashMap<String, String> parentNS, boolean payLoad) throws XWSSecurityException {
/* 502 */     MessageDigest md = null;
/*     */     try {
/* 504 */       String algo = StreamUtil.convertDigestAlgorithm(ref.getDigestMethod().getAlgorithm());
/*     */       
/* 506 */       if (logger.isLoggable(Level.FINE)) {
/* 507 */         logger.log(Level.FINE, "Digest Algorithm is " + ref.getDigestMethod().getAlgorithm());
/* 508 */         logger.log(Level.FINE, "Mapped Digest Algorithm is " + algo);
/*     */       } 
/* 510 */       md = MessageDigest.getInstance(algo);
/*     */     }
/* 512 */     catch (NoSuchAlgorithmException nsae) {
/* 513 */       throw new XWSSecurityException(nsae);
/*     */     } 
/*     */ 
/*     */     
/* 517 */     DigesterOutputStream dos = new DigesterOutputStream(md);
/*     */     
/* 519 */     StAXEXC14nCanonicalizerImpl canonicalizer = new StAXEXC14nCanonicalizerImpl();
/* 520 */     canonicalizer.setBodyPrologue(bodyPrologue);
/* 521 */     canonicalizer.setBodyEpilogue(bodyEpilogue);
/*     */     
/* 523 */     canonicalizer.setStream((OutputStream)dos);
/* 524 */     if (logger.isLoggable(Level.FINEST)) {
/* 525 */       canonicalizer.setStream(new ByteArrayOutputStream());
/*     */     }
/* 527 */     List<Transform> trList = ref.getTransforms();
/* 528 */     if (trList.size() > 1) {
/* 529 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1714_UNSUPPORTED_TRANSFORM_ERROR());
/* 530 */       throw new XWSSecurityException("Only EXC14n Transform is supported");
/*     */     } 
/* 532 */     Transform tr = trList.get(0);
/*     */     
/* 534 */     ExcC14NParameterSpec spec = (ExcC14NParameterSpec)tr.getParameterSpec();
/* 535 */     if (spec != null) {
/* 536 */       canonicalizer.setInclusivePrefixList(spec.getPrefixList());
/*     */     }
/* 538 */     if (parentNS != null && parentNS.size() > 0) {
/* 539 */       Iterator<Map.Entry<String, String>> itr = parentNS.entrySet().iterator();
/*     */       
/* 541 */       while (itr.hasNext()) {
/* 542 */         Map.Entry<String, String> entry = itr.next();
/* 543 */         String prefix = entry.getKey();
/*     */         try {
/* 545 */           String uri = entry.getValue();
/* 546 */           canonicalizer.writeNamespace(prefix, uri);
/* 547 */         } catch (XMLStreamException ex) {
/* 548 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1715_ERROR_CANONICALIZING_BODY(), ex);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     try {
/* 553 */       if (!payLoad) {
/* 554 */         bodyTag.writeStart((XMLStreamWriter)canonicalizer);
/*     */ 
/*     */         
/* 557 */         canonicalizer.setBodyPrologueTime(true);
/*     */       } 
/* 559 */     } catch (XMLStreamException ex) {
/* 560 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1715_ERROR_CANONICALIZING_BODY(), ex);
/* 561 */       throw new XWSSecurityException("Error occurred while canonicalizing BodyTag" + ex);
/*     */     } 
/* 563 */     StreamingPayLoadDigester digester = new StreamingPayLoadDigester(ref, message, canonicalizer, payLoad);
/* 564 */     return XMLStreamReaderFactory.createFilteredXMLStreamReader(message, (StreamFilter)digester);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 568 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public HashMap<String, String> getInscopeNSContext() {
/* 572 */     return this.currentParentNS;
/*     */   }
/*     */   
/*     */   public WSSPolicy getPolicy() {
/* 576 */     return (WSSPolicy)this.signPolicy;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\Signature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */