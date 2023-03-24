/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
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
/*    */ 
/*    */ public class XSDStringEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */   implements AttachmentEncoder
/*    */ {
/* 40 */   private static final SimpleTypeEncoder encoder = new XSDStringEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 46 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 52 */     return (String)obj;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 58 */     return str;
/*    */   }
/*    */   
/*    */   public DataHandler objectToDataHandler(Object obj) throws Exception {
/* 62 */     DataHandler dataHandler = new DataHandler(obj, "text/plain");
/*    */     
/* 64 */     return dataHandler;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object dataHandlerToObject(DataHandler dataHandler) throws Exception {
/* 69 */     return dataHandler.getContent();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDStringEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */