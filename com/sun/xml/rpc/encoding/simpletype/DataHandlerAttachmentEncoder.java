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
/*    */ public class DataHandlerAttachmentEncoder
/*    */   implements AttachmentEncoder
/*    */ {
/* 36 */   private static final AttachmentEncoder encoder = new DataHandlerAttachmentEncoder();
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
/* 47 */     return (DataHandler)obj;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object dataHandlerToObject(DataHandler dataHandler) throws Exception {
/* 52 */     return dataHandler;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\DataHandlerAttachmentEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */