/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.api.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.api.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.attachment.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.security.opt.impl.attachment.EncryptedAttachment;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.JAXBDataImpl;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSBData;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.StreamHeaderData;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.SignedMessageHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.EncryptedHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSNSPrefixWrapper;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.logging.impl.crypto.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ETHandler
/*     */ {
/*  92 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  95 */   private WSSElementFactory wsf = null;
/*  96 */   private HashMap props = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public ETHandler(SOAPVersion soapVersion) {
/* 100 */     this.wsf = new WSSElementFactory(soapVersion);
/* 101 */     if (soapVersion == SOAPVersion.SOAP_11) {
/* 102 */       this.props.put("com.sun.xml.bind.namespacePrefixMapper", new WSSNSPrefixWrapper((NamespacePrefixMapper)JAXBUtil.prefixMapper11));
/*     */     } else {
/* 104 */       this.props.put("com.sun.xml.bind.namespacePrefixMapper", new WSSNSPrefixWrapper((NamespacePrefixMapper)JAXBUtil.prefixMapper12));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List buildEDList(EncryptionPolicy policy, Target target, JAXBFilterProcessingContext context, Key key, KeyInfo ki) throws XWSSecurityException {
/* 111 */     SecuredMessage message = context.getSecuredMessage();
/* 112 */     ArrayList<SecurityHeaderElement> edList = new ArrayList();
/* 113 */     if (target.getType() == "qname") {
/* 114 */       QName name = target.getQName();
/* 115 */       if (name == Target.BODY_QNAME) {
/* 116 */         Object obj = message.getBody();
/* 117 */         String dataEncAlg = SecurityUtil.getDataEncryptionAlgo(context);
/* 118 */         if (dataEncAlg.length() == 0 && 
/* 119 */           context.getAlgorithmSuite() != null) {
/* 120 */           dataEncAlg = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */         }
/*     */         
/* 123 */         Data data = null;
/* 124 */         if (obj instanceof SOAPBody) {
/* 125 */           SSBData sSBData = new SSBData((SOAPBody)obj, true, context.getNamespaceContext());
/* 126 */           SecurityHeaderElement ed = (SecurityHeaderElement)this.wsf.createEncryptedData(context.generateID(), (Data)sSBData, dataEncAlg, (KeyInfoType)ki, key, true);
/* 127 */           edList.add(ed);
/* 128 */           SOAPBody sb = (SOAPBody)message.getBody();
/* 129 */           SOAPBody nsb = new SOAPBody((SecurityElement)ed, context.getSOAPVersion());
/* 130 */           nsb.setId(sb.getId());
/* 131 */           message.replaceBody(nsb);
/* 132 */         } else if (obj instanceof SecurityElement) {
/* 133 */           SSEData sSEData = new SSEData((SecurityElement)obj, true, context.getNamespaceContext(), this.props);
/* 134 */           SecurityHeaderElement ed = (SecurityHeaderElement)this.wsf.createEncryptedData(context.generateID(), (Data)sSEData, dataEncAlg, (KeyInfoType)ki, key, true);
/* 135 */           edList.add(ed);
/* 136 */           SOAPBody nsb = new SOAPBody((SecurityElement)ed, context.getSOAPVersion());
/* 137 */           nsb.setId(((SecurityElement)obj).getId());
/* 138 */           message.replaceBody(nsb);
/*     */         } 
/* 140 */         return edList;
/*     */       } 
/*     */ 
/*     */       
/* 144 */       Iterator headers = null;
/* 145 */       if (name.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2004/08/addressing") || name.getNamespaceURI().equals("http://www.w3.org/2005/08/addressing")) {
/*     */         
/* 147 */         if (!"".equals(name.getLocalPart())) {
/* 148 */           headers = message.getHeaders(name.getLocalPart(), null);
/*     */         } else {
/* 150 */           headers = message.getHeaders("http://schemas.xmlsoap.org/ws/2004/08/addressing");
/* 151 */           if (!headers.hasNext()) {
/* 152 */             headers = message.getHeaders("http://www.w3.org/2005/08/addressing");
/*     */           }
/*     */         } 
/* 155 */       } else if (!"".equals(name.getLocalPart())) {
/* 156 */         headers = message.getHeaders(name.getLocalPart(), name.getNamespaceURI());
/*     */       } else {
/* 158 */         headers = message.getHeaders(name.getNamespaceURI());
/*     */       } 
/*     */       
/* 161 */       while (headers.hasNext()) {
/* 162 */         Object header = headers.next();
/* 163 */         SecurityHeaderElement ed = toMessageHeader(policy, target, context, key, header, ki, true);
/* 164 */         edList.add(ed);
/*     */       } 
/*     */       
/* 167 */       if (!edList.isEmpty()) {
/* 168 */         return edList;
/*     */       }
/* 170 */       SecurityHeader sh = context.getSecurityHeader();
/*     */       
/* 172 */       Iterator<SecurityHeaderElement> itr = sh.getHeaders(name.getLocalPart(), name.getNamespaceURI());
/* 173 */       while (itr.hasNext()) {
/* 174 */         SecurityHeaderElement hdr = itr.next();
/* 175 */         if (hdr != null) {
/* 176 */           SecurityHeaderElement ed = toMessageHeader(policy, target, context, key, hdr, ki, false);
/* 177 */           edList.add(ed);
/*     */         } 
/*     */       } 
/* 180 */       return edList;
/* 181 */     }  if (target.getType() == "uri") {
/*     */       
/* 183 */       if ("cid:*".equals(target.getValue())) {
/* 184 */         handleAttachments(context, edList, key, ki, target);
/*     */       } else {
/* 186 */         SecurityHeaderElement se = handleURI(policy, target, context, key, ki);
/* 187 */         edList.add(se);
/*     */       } 
/* 189 */       return edList;
/*     */     } 
/*     */ 
/*     */     
/* 193 */     throw new UnsupportedOperationException("Target Type " + target.getType() + " is not supported by EncryptionProcessor");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityHeaderElement handleURI(EncryptionPolicy policy, Target target, JAXBFilterProcessingContext context, Key key, KeyInfo ki) throws XWSSecurityException {
/* 199 */     String dataEncAlg = SecurityUtil.getDataEncryptionAlgo(context);
/* 200 */     boolean contentOnly = target.getContentOnly();
/* 201 */     Object header = context.getSecurityHeader().getChildElement(target.getValue());
/* 202 */     if (header != null) {
/*     */       
/* 204 */       if (header instanceof SecurityTokenReference) {
/* 205 */         SecurityTokenReference str = (SecurityTokenReference)header;
/* 206 */         Reference reference = str.getReference();
/* 207 */         String refValue = null;
/* 208 */         if ("Identifier".equals(reference.getType())) {
/* 209 */           refValue = ((KeyIdentifier)reference).getReferenceValue();
/* 210 */         } else if ("Direct".equals(reference.getType())) {
/* 211 */           refValue = ((DirectReference)reference).getURI();
/*     */         } 
/* 213 */         if (refValue != null) {
/* 214 */           if (refValue.startsWith("#")) {
/* 215 */             refValue = refValue.substring(1);
/*     */           }
/* 217 */           header = context.getSecurityHeader().getChildElement(refValue);
/*     */         } 
/*     */       } 
/*     */       
/* 221 */       Data data = toData(header, contentOnly, context);
/* 222 */       SecurityHeaderElement ed = (SecurityHeaderElement)this.wsf.createEncryptedData(context.generateID(), data, dataEncAlg, (KeyInfoType)ki, key, target.getContentOnly());
/* 223 */       context.getSecurityHeader().replace((SecurityHeaderElement)header, ed);
/* 224 */       return ed;
/*     */     } 
/* 226 */     header = context.getSecuredMessage().getHeader(target.getValue());
/* 227 */     return toMessageHeader(policy, target, context, key, header, ki, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityHeaderElement toMessageHeader(EncryptionPolicy policy, Target target, JAXBFilterProcessingContext context, Key key, Object header, KeyInfo ki, boolean isEncryptedHeaders) throws XWSSecurityException {
/* 233 */     SecuredMessage message = context.getSecuredMessage();
/* 234 */     String dataEncAlg = SecurityUtil.getDataEncryptionAlgo(context);
/* 235 */     boolean contentOnly = target.getContentOnly();
/*     */     
/* 237 */     boolean encHeaderContent = context.getEncHeaderContent();
/* 238 */     if (encHeaderContent && !"true".equals(context.getExtraneousProperty("EnableWSS11PolicySender"))) {
/* 239 */       contentOnly = true;
/*     */     }
/*     */     
/* 242 */     Data data = toData(header, contentOnly, context);
/* 243 */     SecurityHeaderElement ed = null;
/*     */     
/* 245 */     if (contentOnly) {
/* 246 */       ed = (SecurityHeaderElement)this.wsf.createEncryptedData(context.generateID(), data, dataEncAlg, (KeyInfoType)ki, key, contentOnly);
/* 247 */       if (header instanceof Header)
/* 248 */         throw new XWSSecurityException("Implementation does not support encrypting content which is already encrypted "); 
/* 249 */       if (header instanceof SignedMessageHeader) {
/* 250 */         EncryptedSignedMessageHeader encHdr = new EncryptedSignedMessageHeader((SignedMessageHeader)header, ed);
/* 251 */         message.replaceHeader(header, encHdr);
/*     */       } else {
/* 253 */         Header hdr = new Header((Header)header, ed);
/* 254 */         message.replaceHeader(header, hdr);
/*     */       } 
/*     */     } else {
/*     */       
/* 258 */       if (isEncryptedHeaders && "true".equals(context.getExtraneousProperty("EnableWSS11PolicySender"))) {
/* 259 */         EncryptedHeader encryptedHeader = this.wsf.createEncryptedHeader(context.generateID(), context.generateID(), data, dataEncAlg, (KeyInfoType)ki, key, contentOnly);
/* 260 */         ((NamespaceContextEx)context.getNamespaceContext()).addWSS11NS();
/*     */       } else {
/* 262 */         ed = (SecurityHeaderElement)this.wsf.createEncryptedData(context.generateID(), data, dataEncAlg, (KeyInfoType)ki, key, contentOnly);
/*     */       } 
/* 264 */       if (!message.replaceHeader(header, ed)) {
/* 265 */         context.getSecurityHeader().replace((SecurityHeaderElement)header, ed);
/*     */       }
/*     */     } 
/* 268 */     return ed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Data toData(Object header, boolean contentOnly, JAXBFilterProcessingContext context) throws XWSSecurityException {
/* 273 */     if (header instanceof SecurityElement)
/* 274 */       return (Data)new SSEData((SecurityElement)header, contentOnly, context.getNamespaceContext(), this.props); 
/* 275 */     if (header instanceof com.sun.xml.ws.message.jaxb.JAXBHeader)
/* 276 */       return (Data)new JAXBDataImpl((Header)header, contentOnly, context.getNamespaceContext(), JAXBUtil.getSEIJAXBContext()); 
/* 277 */     if (header instanceof Header) {
/* 278 */       return (Data)new StreamHeaderData((Header)header, contentOnly, context.getNamespaceContext());
/*     */     }
/* 280 */     throw new XWSSecurityException("Unsupported Header type");
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleAttachments(JAXBFilterProcessingContext context, ArrayList<SecurityHeaderElement> edList, Key key, KeyInfo ki, Target target) throws XWSSecurityException {
/* 285 */     SecuredMessage message = context.getSecuredMessage();
/* 286 */     AttachmentSet as = message.getAttachments();
/* 287 */     if (as != null && as.isEmpty()) {
/* 288 */       logger.log(Level.WARNING, LogStringsMessages.WSS_1244_NO_ATTACHMENT_FOUND());
/*     */       return;
/*     */     } 
/* 291 */     String dataEncAlg = SecurityUtil.getDataEncryptionAlgo(context);
/* 292 */     AttachmentSetImpl attachmentSetImpl = new AttachmentSetImpl();
/* 293 */     for (Attachment attachment : as) {
/* 294 */       SecurityHeaderElement ed = (SecurityHeaderElement)this.wsf.createEncryptedData(context.generateID(), attachment, dataEncAlg, (KeyInfoType)ki, key, (EncryptionTarget)target);
/* 295 */       context.getSecurityHeader().add(ed);
/* 296 */       edList.add(ed);
/* 297 */       EncryptedAttachment encryptedAttachment = new EncryptedAttachment(attachment, dataEncAlg, key);
/* 298 */       attachmentSetImpl.add((Attachment)encryptedAttachment);
/*     */     } 
/* 300 */     message.setAttachments((AttachmentSet)attachmentSetImpl);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\ETHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */