/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.AttachmentData;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.JAXBDataImpl;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSBData;
/*     */ import com.sun.xml.ws.security.opt.impl.message.SOAPBody;
/*     */ import com.sun.xml.ws.security.opt.impl.message.SecuredMessage;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DSigResolver
/*     */   implements URIDereferencer
/*     */ {
/*  83 */   private static DSigResolver resolver = new DSigResolver();
/*  84 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URIDereferencer getInstance() {
/*  93 */     return resolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
/* 104 */     String uri = null;
/* 105 */     uri = uriRef.getURI();
/* 106 */     if (logger.isLoggable(Level.FINEST)) {
/* 107 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1750_URI_TOBE_DEREFERENCED(uri));
/*     */     }
/* 109 */     return dereferenceURI(uri, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Data dereferenceURI(String url, XMLCryptoContext context) throws URIReferenceException {
/*     */     try {
/* 121 */       String uri = url;
/* 122 */       if (uri.startsWith("#SAML")) {
/* 123 */         AuthenticationTokenPolicy.SAMLAssertionBinding resolvedSAMLBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)context.getProperty("SAML_CLIENT_CACHE");
/*     */         
/* 125 */         if (resolvedSAMLBinding != null) {
/* 126 */           String id = resolvedSAMLBinding.getAssertionId();
/* 127 */           uri = id;
/*     */         } 
/*     */       } 
/* 130 */       if (uri == null || uri.equals("")) {
/* 131 */         logger.log(Level.FINEST, "Empty Reference URI not supported");
/* 132 */         throw new UnsupportedOperationException("Empty Reference URI not supported");
/* 133 */       }  if (uri.charAt(0) == '#')
/* 134 */         return dereferenceFragment(getIdFromFragmentRef(uri), context); 
/* 135 */       if (uri.startsWith("cid:") || uri.startsWith("attachmentRef:"))
/*     */       {
/* 137 */         return dereferenceAttachments(uri, context); } 
/* 138 */       if (uri.startsWith("http")) {
/* 139 */         throw new UnsupportedOperationException("Not supported in optimized path");
/*     */       }
/* 141 */       return dereferenceFragment(uri, context);
/*     */     }
/* 143 */     catch (Exception e) {
/* 144 */       throw new URIReferenceException(e);
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
/*     */   Data dereferenceAttachments(String uri, XMLCryptoContext context) throws URIReferenceException {
/* 156 */     JAXBFilterProcessingContext filterContext = (JAXBFilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 157 */     SecuredMessage secureMsg = filterContext.getSecuredMessage();
/* 158 */     Attachment attachment = secureMsg.getAttachment(uri);
/* 159 */     if (attachment == null) {
/* 160 */       throw new URIReferenceException("Attachment Resource with Identifier  " + uri + " was not found");
/*     */     }
/* 162 */     AttachmentData attachData = new AttachmentData(attachment);
/* 163 */     return (Data)attachData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Data dereferenceFragment(String uri, XMLCryptoContext context) throws XWSSecurityException {
/* 174 */     JAXBFilterProcessingContext filterContext = (JAXBFilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 175 */     HashMap elementCache = filterContext.getElementCache();
/*     */     try {
/* 177 */       if (elementCache.size() > 0) {
/* 178 */         Object obj = elementCache.get(uri);
/* 179 */         if (obj != null && obj instanceof Header) {
/* 180 */           Header reqdHeader = (Header)obj;
/* 181 */           JAXBContext jaxbContext = JAXBUtil.getJAXBContext();
/* 182 */           JAXBElement jb = (JAXBElement)reqdHeader.readAsJAXB(jaxbContext.createUnmarshaller());
/* 183 */           return (Data)new JAXBDataImpl(jb, jaxbContext, filterContext.getNamespaceContext());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 188 */       return getDataById(filterContext, uri);
/* 189 */     } catch (JAXBException jbe) {
/* 190 */       throw new XWSSecurityException(jbe);
/* 191 */     } catch (XMLStreamException sxe) {
/* 192 */       throw new XWSSecurityException(sxe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getIdFromFragmentRef(String ref) {
/* 197 */     char start = ref.charAt(0);
/* 198 */     if (start == '#') {
/* 199 */       return ref.substring(1);
/*     */     }
/* 201 */     return ref;
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
/*     */   private Data getDataById(JAXBFilterProcessingContext context, String uri) throws JAXBException, XMLStreamException, XWSSecurityException {
/* 216 */     SecuredMessage secMessage = context.getSecuredMessage();
/* 217 */     ArrayList headerList = secMessage.getHeaders();
/*     */     
/* 219 */     SecurityHeaderElement reqdHeader = null;
/* 220 */     for (int i = 0; i < headerList.size(); i++) {
/* 221 */       Object header = headerList.get(i);
/* 222 */       if (header instanceof SecurityHeaderElement) {
/*     */         
/* 224 */         SecurityHeaderElement she = (SecurityHeaderElement)header;
/* 225 */         if (uri.equals(she.getId())) {
/* 226 */           reqdHeader = she;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 233 */     if (reqdHeader == null) {
/* 234 */       SecurityHeader secHeader = context.getSecurityHeader();
/* 235 */       SecurityHeaderElement she = secHeader.getChildElement(uri);
/* 236 */       if (she != null && (!"SecurityTokenReference".equals(she.getLocalPart()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(she.getNamespaceURI())))
/*     */       {
/* 238 */         reqdHeader = she;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 243 */     if (reqdHeader != null)
/*     */     {
/* 245 */       return (Data)new JAXBDataImpl((SecurityElement)reqdHeader, context.getNamespaceContext(), false);
/*     */     }
/*     */     try {
/* 248 */       Object body = secMessage.getBody();
/* 249 */       if (body instanceof SecurityElement) {
/* 250 */         SecurityElement se = (SecurityElement)body;
/* 251 */         if (uri.equals(se.getId())) {
/* 252 */           return (Data)new JAXBDataImpl(se, context.getNamespaceContext(), false);
/*     */         }
/* 254 */       } else if (body instanceof SOAPBody) {
/* 255 */         SOAPBody soapBody = (SOAPBody)body;
/* 256 */         if (uri.equals(soapBody.getId())) {
/* 257 */           return (Data)new SSBData(soapBody, false, context.getNamespaceContext());
/*     */         }
/* 259 */         if (uri.equals(soapBody.getBodyContentId())) {
/* 260 */           return (Data)new SSBData(soapBody, true, context.getNamespaceContext());
/*     */         }
/*     */       } 
/* 263 */     } catch (XWSSecurityException ex) {
/* 264 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1704_ERROR_RESOLVING_ID(uri), (Throwable)ex);
/* 265 */       throw new XWSSecurityException(ex);
/*     */     } 
/*     */     
/* 268 */     Data data = null;
/* 269 */     data = (Data)context.getSTRTransformCache().get(uri);
/* 270 */     if (data != null) {
/* 271 */       return data;
/*     */     }
/* 273 */     data = (Data)context.getElementCache().get(uri);
/* 274 */     return data;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\DSigResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */