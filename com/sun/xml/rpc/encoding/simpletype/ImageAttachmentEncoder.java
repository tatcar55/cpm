/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import javax.activation.DataHandler;
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
/*    */ public class ImageAttachmentEncoder
/*    */   implements AttachmentEncoder
/*    */ {
/* 36 */   private static final AttachmentEncoder encoder = new ImageAttachmentEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AttachmentEncoder getInstance() {
/* 43 */     return encoder;
/*    */   }
/*    */   
/*    */   public DataHandler objectToDataHandler(Object obj) throws Exception {
/* 47 */     DataHandler dataHandler = new DataHandler(obj, "image/jpeg");
/*    */     
/* 49 */     return dataHandler;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object dataHandlerToObject(DataHandler dataHandler) throws Exception {
/* 54 */     return dataHandler.getContent();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\ImageAttachmentEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */