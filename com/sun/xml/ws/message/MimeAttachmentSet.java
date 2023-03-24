/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.encoding.MimeMultipartParser;
/*     */ import com.sun.xml.ws.resources.EncodingMessages;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MimeAttachmentSet
/*     */   implements AttachmentSet
/*     */ {
/*     */   private final MimeMultipartParser mpp;
/*  62 */   private Map<String, Attachment> atts = new HashMap<String, Attachment>();
/*     */ 
/*     */   
/*     */   public MimeAttachmentSet(MimeMultipartParser mpp) {
/*  66 */     this.mpp = mpp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Attachment get(String contentId) {
/*  76 */     Attachment att = this.atts.get(contentId);
/*  77 */     if (att != null) {
/*  78 */       return att;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  84 */       att = this.mpp.getAttachmentPart(contentId);
/*  85 */       if (att != null) {
/*  86 */         this.atts.put(contentId, att);
/*     */       }
/*  88 */     } catch (IOException e) {
/*  89 */       throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(contentId), e);
/*     */     } 
/*  91 */     return att;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  99 */     return (this.atts.size() <= 0 && this.mpp.getAttachmentParts().isEmpty());
/*     */   }
/*     */   
/*     */   public void add(Attachment att) {
/* 103 */     this.atts.put(att.getContentId(), att);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Attachment> iterator() {
/* 114 */     Map<String, Attachment> attachments = this.mpp.getAttachmentParts();
/* 115 */     for (Map.Entry<String, Attachment> att : attachments.entrySet()) {
/* 116 */       if (this.atts.get(att.getKey()) == null) {
/* 117 */         this.atts.put(att.getKey(), att.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return this.atts.values().iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\MimeAttachmentSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */