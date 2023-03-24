/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
/*    */ import java.net.URI;
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
/*    */ 
/*    */ public class XSDAnyURIEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */   implements AttachmentEncoder
/*    */ {
/* 42 */   private static final SimpleTypeEncoder encoder = new XSDAnyURIEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 48 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 54 */     return ((URI)obj).toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 60 */     return new URI(str);
/*    */   }
/*    */   
/*    */   public DataHandler objectToDataHandler(Object obj) throws Exception {
/* 64 */     DataHandler dataHandler = new DataHandler(obj, "text/plain");
/*    */     
/* 66 */     return dataHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object dataHandlerToObject(DataHandler dataHandler) throws Exception {
/* 72 */     return dataHandler.getContent();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDAnyURIEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */