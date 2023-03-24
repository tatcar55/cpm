/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.encoding.TagInfoset;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.CanonicalizationMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.DigestMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignatureMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignedInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transform;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transforms;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBSignatureFactory;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBStructure;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBValidateContext;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.ExcC14NParameterSpec;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.GenericSecuredHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.URIResolver;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.TransformationParametersType;
/*     */ import com.sun.xml.wss.BasicSecurityProfile;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
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
/*     */ public class SignedInfoProcessor
/*     */ {
/* 114 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   public static final int CANONICALIZATION_METHOD_EVENT = 1;
/*     */   
/*     */   public static final int SIGNATURE_METHOD_EVENT = 2;
/*     */   
/*     */   public static final int REFERENCE_EVENT = 3;
/*     */   public static final int DIGEST_METHOD_EVENT = 4;
/*     */   public static final int DIGEST_VALUE_EVENT = 5;
/*     */   public static final int TRANSFORM_EVENT = 6;
/*     */   public static final int TRANSFORMS_EVENT = 7;
/* 125 */   StAXEXC14nCanonicalizerImpl exc14nFinal = null;
/*     */   public static final String CANONICALIZATION_METHOD = "CanonicalizationMethod";
/*     */   public static final String SIGNATURE_METHOD = "SignatureMethod";
/*     */   public static final String REFERENCE = "Reference";
/*     */   public static final String INCLUSIVENAMESPACES = "InclusiveNamespaces";
/*     */   public static final String EXC14N_NS = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   public static final String TRANSFORMS = "Transforms";
/*     */   public static final String TRANSFORM = "Transform";
/*     */   public static final String DIGEST_METHOD = "DigestMethod";
/*     */   public static final String DIGEST_VALUE = "DigestValue";
/* 135 */   private String canonAlgo = "";
/*     */   
/* 137 */   private TagInfoset signatureRoot = null;
/*     */   
/* 139 */   private String signatureMethod = "";
/* 140 */   private HashMap<String, String> currentNSDecls = new HashMap<String, String>();
/* 141 */   private UnsyncByteArrayOutputStream canonInfo = new UnsyncByteArrayOutputStream();
/* 142 */   private XMLStreamReader reader = null;
/* 143 */   private SecurityContext securityContext = null;
/* 144 */   private ArrayList<Reference> refList = null;
/* 145 */   private URIResolver resolver = null;
/* 146 */   private SignaturePolicy.FeatureBinding fb = null;
/* 147 */   private JAXBFilterProcessingContext pc = null;
/* 148 */   private JAXBSignatureFactory signatureFactory = null;
/* 149 */   MutableXMLStreamBuffer siBuffer = null;
/*     */ 
/*     */   
/*     */   public SignedInfoProcessor(TagInfoset signature, HashMap<String, String> parentNSDecls, XMLStreamReader reader, JAXBFilterProcessingContext pc, SignaturePolicy signPolicy, MutableXMLStreamBuffer buffer) {
/* 153 */     this.signatureRoot = signature;
/* 154 */     this.siBuffer = buffer;
/* 155 */     this.currentNSDecls.putAll(parentNSDecls);
/* 156 */     this.reader = reader;
/* 157 */     this.pc = pc;
/* 158 */     this.securityContext = pc.getSecurityContext();
/* 159 */     this.resolver = new URIResolver(pc);
/* 160 */     this.signatureFactory = JAXBSignatureFactory.newInstance();
/* 161 */     this.fb = (SignaturePolicy.FeatureBinding)signPolicy.getFeatureBinding();
/*     */   }
/*     */   
/*     */   public XMLStreamWriter getCanonicalizer() {
/* 165 */     return (XMLStreamWriter)this.exc14nFinal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo process() throws XWSSecurityException {
/*     */     try {
/* 174 */       for (int i = 0; i < this.reader.getNamespaceCount(); i++) {
/* 175 */         this.currentNSDecls.put(this.reader.getNamespacePrefix(i), this.reader.getNamespaceURI(i));
/*     */       }
/* 177 */       boolean referencesFound = false;
/* 178 */       if (StreamUtil.moveToNextElement(this.reader)) {
/* 179 */         int refElement = getEventType(this.reader);
/* 180 */         while (this.reader.getEventType() != 8) {
/* 181 */           switch (refElement) {
/*     */             case 1:
/* 183 */               readCanonicalizationMethod(this.reader);
/* 184 */               this.fb.setCanonicalizationAlgorithm(this.canonAlgo);
/*     */               break;
/*     */             
/*     */             case 2:
/* 188 */               this.signatureMethod = this.reader.getAttributeValue(null, "Algorithm");
/*     */               break;
/*     */             
/*     */             case 3:
/* 192 */               referencesFound = true;
/* 193 */               processReferences(this.reader);
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 200 */           if (StreamUtil._break(this.reader, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#")) {
/* 201 */             if (this.reader.hasNext())
/* 202 */               this.reader.next(); 
/*     */             break;
/*     */           } 
/* 205 */           if (this.reader.getEventType() == 8) {
/*     */             break;
/*     */           }
/* 208 */           this.reader.next();
/*     */           
/* 210 */           if (StreamUtil._break(this.reader, "SignedInfo", "http://www.w3.org/2000/09/xmldsig#")) {
/* 211 */             if (this.reader.hasNext())
/* 212 */               this.reader.next(); 
/*     */             break;
/*     */           } 
/* 215 */           refElement = getEventType(this.reader);
/*     */         } 
/*     */       } 
/*     */       
/* 219 */       if (!referencesFound) {
/* 220 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1725_REFERENCE_ELEMENT_NOTFOUND());
/* 221 */         throw new XWSSecurityException(LogStringsMessages.WSS_1725_REFERENCE_ELEMENT_NOTFOUND());
/*     */       } 
/* 223 */       SignedInfo si = new SignedInfo();
/* 224 */       SignatureMethod sm = new SignatureMethod();
/* 225 */       sm.setAlgorithm(this.signatureMethod);
/* 226 */       si.setSignatureMethod(sm);
/* 227 */       si.setReference(getReferenceList());
/* 228 */       StreamReaderBufferProcessor streamReaderBufferProcessor = this.siBuffer.readAsXMLStreamReader();
/* 229 */       while (streamReaderBufferProcessor.hasNext() && (
/* 230 */         streamReaderBufferProcessor.getEventType() != 1 || 
/* 231 */         streamReaderBufferProcessor.getLocalName() != "SignedInfo".intern()))
/*     */       {
/*     */ 
/*     */         
/* 235 */         streamReaderBufferProcessor.next();
/*     */       }
/* 237 */       int counter = 1;
/* 238 */       while (streamReaderBufferProcessor.hasNext()) {
/* 239 */         StreamUtil.writeCurrentEvent((XMLStreamReader)streamReaderBufferProcessor, (XMLStreamWriter)this.exc14nFinal);
/* 240 */         if (counter == 0) {
/*     */           break;
/*     */         }
/* 243 */         streamReaderBufferProcessor.next();
/* 244 */         if (streamReaderBufferProcessor.getEventType() == 2) {
/* 245 */           counter--; continue;
/* 246 */         }  if (streamReaderBufferProcessor.getEventType() == 1) {
/* 247 */           counter++;
/*     */         }
/*     */       } 
/* 250 */       si.setCanonicalizedSI(this.canonInfo.toByteArray());
/* 251 */       if (logger.isLoggable(Level.FINEST)) {
/* 252 */         logger.log(Level.FINEST, "Canonicalized Signed Info:" + new String(this.canonInfo.toByteArray()));
/*     */       }
/* 254 */       return si;
/* 255 */     } catch (XMLStreamException ex) {
/* 256 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), ex);
/* 257 */       throw new XWSSecurityException(LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readCanonicalizationMethod(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 268 */       this.canonAlgo = reader.getAttributeValue(null, "Algorithm");
/* 269 */       if (this.canonAlgo != null && this.canonAlgo.length() == 0) {
/* 270 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1718_MISSING_CANON_ALGORITHM());
/* 271 */         throw new XWSSecurityException(LogStringsMessages.WSS_1718_MISSING_CANON_ALGORITHM());
/*     */       } 
/* 273 */       String[] prefixList = null;
/* 274 */       if (reader.hasNext()) {
/* 275 */         reader.next();
/* 276 */         if (reader.getEventType() == 1 && 
/* 277 */           reader.getLocalName() == "InclusiveNamespaces" && reader.getNamespaceURI() == "http://www.w3.org/2001/10/xml-exc-c14n#") {
/* 278 */           String pl = reader.getAttributeValue(null, "PrefixList");
/* 279 */           if (pl != null && pl.length() > 0) {
/* 280 */             prefixList = pl.split(" ");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 286 */       if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(this.canonAlgo)) {
/* 287 */         this.exc14nFinal = new StAXEXC14nCanonicalizerImpl();
/* 288 */         if (prefixList != null && prefixList.length > 0) {
/* 289 */           ArrayList<String> al = new ArrayList<String>(prefixList.length);
/* 290 */           for (int i = 0; i < prefixList.length; i++) {
/* 291 */             al.add(prefixList[i]);
/*     */           }
/* 293 */           this.exc14nFinal.setInclusivePrefixList(al);
/*     */         } 
/* 295 */         Iterator<String> itr = this.currentNSDecls.keySet().iterator();
/* 296 */         this.exc14nFinal.setStream((OutputStream)this.canonInfo);
/*     */         
/* 298 */         while (itr.hasNext()) {
/* 299 */           String prefix = itr.next();
/* 300 */           String uri = this.currentNSDecls.get(prefix);
/* 301 */           this.exc14nFinal.writeNamespace(prefix, uri);
/*     */         } 
/*     */       } 
/* 304 */     } catch (XMLStreamException xse) {
/* 305 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), xse);
/* 306 */       throw new XWSSecurityException(LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), xse);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processReferences(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 317 */       String dm = "";
/* 318 */       String uri = reader.getAttributeValue(null, "URI");
/* 319 */       String digestValue = "";
/* 320 */       Base64Data bd = null;
/* 321 */       ArrayList<Transform> tList = null;
/* 322 */       while (reader.hasNext() && !StreamUtil._break(reader, "Reference", "http://www.w3.org/2000/09/xmldsig#")) {
/* 323 */         reader.next();
/* 324 */         int referenceEvent = getReferenceEventType(reader);
/* 325 */         switch (referenceEvent) {
/*     */           case 7:
/* 327 */             tList = processTransforms(reader, uri);
/*     */ 
/*     */           
/*     */           case 4:
/* 331 */             dm = reader.getAttributeValue(null, "Algorithm");
/*     */ 
/*     */           
/*     */           case 5:
/* 335 */             if (reader instanceof XMLStreamReaderEx) {
/* 336 */               reader.next();
/* 337 */               CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 338 */               String dv = null;
/* 339 */               if (charSeq instanceof Base64Data) {
/* 340 */                 bd = (Base64Data)charSeq;
/* 341 */                 dv = Base64.encode(bd.getExact());
/*     */               } else {
/* 343 */                 dv = StreamUtil.getCV(reader);
/*     */               } 
/* 345 */               digestValue = dv;
/*     */               continue;
/*     */             } 
/* 348 */             digestValue = StreamUtil.getCV(reader);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 358 */       Reference reference = new Reference();
/* 359 */       DigestMethod digestMethod = new DigestMethod();
/* 360 */       digestMethod.setAlgorithm(dm);
/* 361 */       reference.setDigestMethod(digestMethod);
/* 362 */       if (bd != null) {
/* 363 */         reference.setDigestValue(bd.getExact());
/*     */       } else {
/*     */         try {
/* 366 */           reference.setDigestValue(Base64.decode(digestValue));
/* 367 */         } catch (Base64DecodingException dec) {
/* 368 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1719_ERROR_DIGESTVAL_REFERENCE(uri), dec);
/* 369 */           throw new XWSSecurityException(LogStringsMessages.WSS_1719_ERROR_DIGESTVAL_REFERENCE(uri));
/*     */         } 
/*     */       } 
/*     */       
/* 373 */       reference.setURI(uri);
/* 374 */       Transforms transforms = new Transforms();
/* 375 */       transforms.setTransform(tList);
/* 376 */       reference.setTransforms(transforms);
/*     */ 
/*     */       
/* 379 */       Target target = new Target("uri", uri);
/* 380 */       SignatureTarget signTarget = new SignatureTarget(target);
/* 381 */       signTarget.setDigestAlgorithm(dm);
/* 382 */       for (int i = 0; tList != null && i < tList.size(); i++) {
/* 383 */         Transform tr = tList.get(i);
/* 384 */         SignatureTarget.Transform sTr = new SignatureTarget.Transform(tr.getAlgorithm());
/* 385 */         signTarget.addTransform(sTr);
/*     */       } 
/*     */       
/* 388 */       this.fb.addTargetBinding(signTarget);
/*     */       
/* 390 */       if (!processReference(reference)) {
/* 391 */         ArrayList<Reference> refCache = getReferenceList();
/* 392 */         refCache.add(reference);
/*     */       } 
/* 394 */     } catch (XMLStreamException xe) {
/* 395 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), xe);
/* 396 */       throw new XWSSecurityException(LogStringsMessages.WSS_1711_ERROR_VERIFYING_SIGNATURE(), xe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processReference(Reference reference) throws XWSSecurityException {
/* 406 */     final String uri = reference.getURI();
/* 407 */     URIReference ref = new URIReference() {
/*     */         public String getType() {
/* 409 */           return "";
/*     */         }
/*     */         public String getURI() {
/* 412 */           return uri;
/*     */         }
/*     */       };
/* 415 */     Data data = null;
/*     */     try {
/* 417 */       data = this.resolver.dereference(ref, null);
/* 418 */     } catch (URIReferenceException ure) {
/* 419 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1720_ERROR_URI_DEREF(uri), ure);
/* 420 */       throw new XWSSecurityException(LogStringsMessages.WSS_1720_ERROR_URI_DEREF(uri), ure);
/*     */     } 
/* 422 */     if (data != null) {
/* 423 */       JAXBValidateContext jvc = new JAXBValidateContext();
/* 424 */       jvc.setURIDereferencer((URIDereferencer)this.resolver);
/* 425 */       jvc.put("http://wss.sun.com#processingContext", this.pc);
/*     */       try {
/* 427 */         if (!reference.validate((XMLValidateContext)jvc)) {
/* 428 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1721_REFERENCE_VALIDATION_FAILED(uri));
/* 429 */           throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1721_REFERENCE_VALIDATION_FAILED(uri), null);
/*     */         } 
/* 431 */         return true;
/* 432 */       } catch (XMLSignatureException xse) {
/* 433 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1722_ERROR_REFERENCE_VALIDATION(uri), xse);
/* 434 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1722_ERROR_REFERENCE_VALIDATION(uri), xse);
/*     */       } 
/*     */     } 
/* 437 */     return false;
/*     */   }
/*     */   
/*     */   public ArrayList<Reference> getReferenceList() {
/* 441 */     if (this.refList == null) {
/* 442 */       this.refList = new ArrayList<Reference>(1);
/*     */     }
/* 444 */     return this.refList;
/*     */   }
/*     */   
/*     */   public int getReferenceEventType(XMLStreamReader reader) {
/* 448 */     if (reader.getEventType() == 1) {
/* 449 */       if (reader.getLocalName() == "Transforms") {
/* 450 */         return 7;
/*     */       }
/* 452 */       if (reader.getLocalName() == "DigestMethod") {
/* 453 */         return 4;
/*     */       }
/* 455 */       if (reader.getLocalName() == "DigestValue") {
/* 456 */         return 5;
/*     */       }
/*     */     } 
/* 459 */     return -1;
/*     */   }
/*     */   
/*     */   private int getEventType(XMLStreamReader reader) {
/* 463 */     if (reader.getEventType() == 1) {
/* 464 */       if (reader.getLocalName() == "CanonicalizationMethod") {
/* 465 */         return 1;
/*     */       }
/* 467 */       if (reader.getLocalName() == "SignatureMethod") {
/* 468 */         return 2;
/*     */       }
/* 470 */       if (reader.getLocalName() == "Reference") {
/* 471 */         return 3;
/*     */       }
/*     */     } 
/* 474 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList processTransforms(XMLStreamReader reader, String uri) throws XWSSecurityException {
/*     */     try {
/* 486 */       ArrayList<Transform> trList = new ArrayList(1);
/* 487 */       while (reader.hasNext()) {
/* 488 */         if (StreamUtil.isStartElement(reader) && reader.getLocalName() == "Transform") {
/* 489 */           trList.add(processTransform(reader, uri));
/*     */         }
/* 491 */         reader.next();
/* 492 */         if (StreamUtil._break(reader, "Transforms", "http://www.w3.org/2000/09/xmldsig#")) {
/*     */           break;
/*     */         }
/*     */       } 
/* 496 */       return trList;
/* 497 */     } catch (XMLStreamException xse) {
/* 498 */       throw new XWSSecurityException(xse);
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
/*     */   private Transform processTransform(XMLStreamReader reader, String uri) throws XWSSecurityException {
/*     */     try {
/* 511 */       ExcC14NParameterSpec exc14nSpec = null;
/* 512 */       String value = reader.getAttributeValue(null, "Algorithm");
/* 513 */       if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(value)) {
/* 514 */         exc14nSpec = readEXC14nTransform(reader);
/* 515 */         return (Transform)this.signatureFactory.newTransform(value, (TransformParameterSpec)exc14nSpec);
/* 516 */       }  if ("http://www.w3.org/2000/09/xmldsig#enveloped-signature".equals(value)) {
/* 517 */         if (this.pc.isBSP()) {
/* 518 */           BasicSecurityProfile.log_bsp_3104();
/*     */         }
/* 520 */         return (Transform)this.signatureFactory.newTransform(value, (TransformParameterSpec)exc14nSpec);
/* 521 */       }  if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(value))
/* 522 */       { StreamUtil.moveToNextStartOREndElement(reader);
/* 523 */         if (reader.getLocalName() == "TransformationParameters") {
/* 524 */           Transform tr = (Transform)this.signatureFactory.newTransform(value, readSTRTransform(reader));
/* 525 */           String id = "";
/* 526 */           int index = uri.indexOf("#");
/* 527 */           if (index >= 0) {
/* 528 */             id = uri.substring(index + 1);
/*     */           } else {
/* 530 */             id = uri;
/*     */           } 
/* 532 */           tr.setReferenceId(id);
/* 533 */           return tr;
/*     */         }  }
/* 535 */       else { if ("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(value))
/* 536 */           return (Transform)this.signatureFactory.newTransform(value, (TransformParameterSpec)exc14nSpec); 
/* 537 */         if ("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(value)) {
/* 538 */           return (Transform)this.signatureFactory.newTransform(value, (TransformParameterSpec)exc14nSpec);
/*     */         }
/* 540 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1723_UNSUPPORTED_TRANSFORM_ELEMENT(value));
/* 541 */         SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_ALGORITHM, LogStringsMessages.WSS_1723_UNSUPPORTED_TRANSFORM_ELEMENT(value), null); }
/*     */       
/* 543 */       return null;
/* 544 */     } catch (Exception xe) {
/* 545 */       throw new XWSSecurityException("Transform error", xe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getMessagePart(String id) {
/* 555 */     HeaderList headers = this.securityContext.getNonSecurityHeaders();
/* 556 */     if (headers != null && headers.size() > 0) {
/* 557 */       Iterator<Header> listItr = headers.listIterator();
/* 558 */       boolean found = false;
/* 559 */       while (listItr.hasNext()) {
/* 560 */         GenericSecuredHeader header = (GenericSecuredHeader)listItr.next();
/* 561 */         if (header.hasID(id)) {
/* 562 */           return header;
/*     */         }
/*     */       } 
/*     */     } 
/* 566 */     ArrayList<SecurityHeaderElement> pshList = this.securityContext.getProcessedSecurityHeaders();
/* 567 */     for (int j = 0; j < pshList.size(); j++) {
/* 568 */       SecurityHeaderElement header = pshList.get(j);
/* 569 */       if (id.equals(header.getId())) {
/* 570 */         return header;
/*     */       }
/*     */     } 
/* 573 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLStructure readSTRTransform(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/* 584 */       TransformationParametersType tp = (new ObjectFactory()).createTransformationParametersType();
/*     */       
/* 586 */       CanonicalizationMethod cm = new CanonicalizationMethod();
/*     */       
/* 588 */       tp.getAny().add(cm);
/* 589 */       JAXBElement<TransformationParametersType> tpElement = (new ObjectFactory()).createTransformationParameters(tp);
/*     */       
/* 591 */       JAXBStructure jAXBStructure = new JAXBStructure(tpElement);
/* 592 */       reader.next();
/* 593 */       if (StreamUtil.isStartElement(reader) && reader.getLocalName() == "CanonicalizationMethod") {
/* 594 */         String value = reader.getAttributeValue(null, "Algorithm");
/* 595 */         cm.setAlgorithm(value);
/* 596 */         StreamUtil.moveToNextStartOREndElement(reader);
/*     */       } 
/* 598 */       return (XMLStructure)jAXBStructure;
/* 599 */     } catch (Exception ex) {
/* 600 */       throw new XWSSecurityException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExcC14NParameterSpec readEXC14nTransform(XMLStreamReader reader) throws XMLStreamException {
/* 611 */     String prefixList = "";
/* 612 */     ExcC14NParameterSpec exc14nSpec = null;
/* 613 */     if (reader.hasNext()) {
/* 614 */       reader.next();
/* 615 */       if (StreamUtil.isStartElement(reader) && reader.getLocalName() == "InclusiveNamespaces") {
/* 616 */         prefixList = reader.getAttributeValue(null, "PrefixList");
/* 617 */         String[] pl = null;
/* 618 */         if (prefixList != null && prefixList.length() > 0) {
/* 619 */           pl = prefixList.split(" ");
/*     */         }
/* 621 */         if (pl != null && pl.length > 0) {
/* 622 */           ArrayList<String> prefixs = new ArrayList();
/* 623 */           for (int i = 0; i < pl.length; i++) {
/* 624 */             prefixs.add(pl[i]);
/*     */           }
/* 626 */           exc14nSpec = new ExcC14NParameterSpec(prefixs);
/*     */         } 
/* 628 */         if (reader.hasNext()) {
/* 629 */           reader.next();
/*     */         }
/*     */       } 
/*     */     } 
/* 633 */     return exc14nSpec;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\SignedInfoProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */