/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.security.opt.api.NamespaceContextInfo;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.AttachmentData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URIResolver
/*     */   implements URIDereferencer
/*     */ {
/*     */   private SecurityContext securityContext;
/*  65 */   private JAXBFilterProcessingContext pc = null;
/*     */   
/*     */   public URIResolver(JAXBFilterProcessingContext pc) {
/*  68 */     this.pc = pc;
/*  69 */     this.securityContext = pc.getSecurityContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public Data dereference(URIReference uRIReference, XMLCryptoContext xMLCryptoContext) throws URIReferenceException {
/*  74 */     HeaderList headers = this.securityContext.getNonSecurityHeaders();
/*  75 */     String tmpId = uRIReference.getURI();
/*     */     
/*  77 */     if (tmpId.startsWith("cid:")) {
/*  78 */       return dereferenceAttachments(tmpId.substring(4));
/*     */     }
/*     */     
/*  81 */     String id = "";
/*  82 */     int index = tmpId.indexOf("#");
/*  83 */     if (index >= 0) {
/*  84 */       id = tmpId.substring(index + 1);
/*     */     } else {
/*  86 */       id = tmpId;
/*     */     } 
/*  88 */     if (headers != null && headers.size() > 0) {
/*  89 */       Iterator<Header> listItr = headers.listIterator();
/*  90 */       boolean found = false;
/*  91 */       while (listItr.hasNext()) {
/*  92 */         GenericSecuredHeader header = (GenericSecuredHeader)listItr.next();
/*  93 */         if (header.hasID(id) && !header.hasEncData()) {
/*  94 */           return (Data)new StreamWriterData(header, header.getInscopeNSContext());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     ArrayList<SecurityHeaderElement> pshList = this.securityContext.getProcessedSecurityHeaders();
/* 100 */     for (int j = 0; j < pshList.size(); j++) {
/* 101 */       SecurityHeaderElement header = pshList.get(j);
/* 102 */       if (id.equals(header.getId())) {
/* 103 */         if (header instanceof NamespaceContextInfo) {
/* 104 */           return (Data)new StreamWriterData(header, ((NamespaceContextInfo)header).getInscopeNSContext());
/*     */         }
/* 106 */         throw new URIReferenceException("Cannot derefernce this MessagePart and use if for any crypto operation as the message part is not cached");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     ArrayList<SecurityHeaderElement> bufList = this.securityContext.getBufferedSecurityHeaders();
/* 115 */     for (int i = 0; i < bufList.size(); i++) {
/* 116 */       SecurityHeaderElement header = bufList.get(i);
/* 117 */       if (id.equals(header.getId())) {
/* 118 */         if (header instanceof NamespaceContextInfo) {
/* 119 */           return (Data)new StreamWriterData(header, ((NamespaceContextInfo)header).getInscopeNSContext());
/*     */         }
/* 121 */         throw new URIReferenceException("Cannot derefernce this MessagePart and use if for any crypto operation as the message part is not cached");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     Data data = null;
/* 128 */     data = (Data)this.pc.getSTRTransformCache().get(id);
/* 129 */     if (data != null)
/* 130 */       return data; 
/* 131 */     data = (Data)this.pc.getElementCache().get(id);
/* 132 */     return data;
/*     */   }
/*     */   
/*     */   private Data dereferenceAttachments(String cidRef) throws URIReferenceException {
/* 136 */     AttachmentSet attachments = this.securityContext.getDecryptedAttachmentSet();
/* 137 */     if (attachments == null || attachments.isEmpty()) {
/* 138 */       attachments = this.securityContext.getAttachmentSet();
/*     */     }
/* 140 */     if (attachments == null || attachments.isEmpty()) {
/* 141 */       throw new URIReferenceException("Attachment Resource with Identifier  " + cidRef + " was not found");
/*     */     }
/* 143 */     Attachment attachment = attachments.get(cidRef);
/* 144 */     if (attachment == null) {
/* 145 */       throw new URIReferenceException("Attachment Resource with Identifier  " + cidRef + " was not found");
/*     */     }
/* 147 */     AttachmentData attachData = new AttachmentData(attachment);
/* 148 */     return (Data)attachData;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\URIResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */