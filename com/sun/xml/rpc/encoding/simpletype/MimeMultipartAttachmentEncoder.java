/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import javax.activation.DataHandler;
/*    */ import javax.mail.internet.MimeMultipart;
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
/*    */ public class MimeMultipartAttachmentEncoder
/*    */   implements AttachmentEncoder
/*    */ {
/* 37 */   private static final AttachmentEncoder encoder = new MimeMultipartAttachmentEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AttachmentEncoder getInstance() {
/* 44 */     return encoder;
/*    */   }
/*    */   
/*    */   public DataHandler objectToDataHandler(Object obj) throws Exception {
/* 48 */     String contentType = ((MimeMultipart)obj).getContentType();
/* 49 */     DataHandler dataHandler = new DataHandler(obj, contentType);
/*    */     
/* 51 */     return dataHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object dataHandlerToObject(DataHandler dataHandler) throws Exception {
/* 57 */     return dataHandler.getContent();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\MimeMultipartAttachmentEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */