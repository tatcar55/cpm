/*    */ package com.sun.xml.ws.message;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Attachment;
/*    */ import com.sun.xml.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.ws.resources.EncodingMessages;
/*    */ import javax.activation.DataHandler;
/*    */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AttachmentUnmarshallerImpl
/*    */   extends AttachmentUnmarshaller
/*    */ {
/*    */   private final AttachmentSet attachments;
/*    */   
/*    */   public AttachmentUnmarshallerImpl(AttachmentSet attachments) {
/* 64 */     this.attachments = attachments;
/*    */   }
/*    */ 
/*    */   
/*    */   public DataHandler getAttachmentAsDataHandler(String cid) {
/* 69 */     Attachment a = this.attachments.get(stripScheme(cid));
/* 70 */     if (a == null)
/* 71 */       throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(cid)); 
/* 72 */     return a.asDataHandler();
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getAttachmentAsByteArray(String cid) {
/* 77 */     Attachment a = this.attachments.get(stripScheme(cid));
/* 78 */     if (a == null)
/* 79 */       throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(cid)); 
/* 80 */     return a.asByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String stripScheme(String cid) {
/* 87 */     if (cid.startsWith("cid:"))
/* 88 */       cid = cid.substring(4); 
/* 89 */     return cid;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\AttachmentUnmarshallerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */