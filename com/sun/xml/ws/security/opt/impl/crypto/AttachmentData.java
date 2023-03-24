/*    */ package com.sun.xml.ws.security.opt.impl.crypto;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Attachment;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import javax.xml.crypto.Data;
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
/*    */ public class AttachmentData
/*    */   implements Data
/*    */ {
/* 55 */   private Attachment attachment = null;
/*    */   
/*    */   public AttachmentData(Attachment attachment) {
/* 58 */     this.attachment = attachment;
/*    */   }
/*    */   
/*    */   public void write(OutputStream os) throws IOException {
/* 62 */     this.attachment.writeTo(os);
/*    */   }
/*    */   
/*    */   public Attachment getAttachment() {
/* 66 */     return this.attachment;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\crypto\AttachmentData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */