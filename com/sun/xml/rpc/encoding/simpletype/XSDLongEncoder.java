/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
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
/*    */ public class XSDLongEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 37 */   private static final SimpleTypeEncoder encoder = new XSDLongEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 43 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 49 */     if (obj == null) {
/* 50 */       return null;
/*    */     }
/* 52 */     return ((Long)obj).toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 58 */     if (str == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     return new Long(EncoderUtils.collapseWhitespace(str));
/*    */   }
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 65 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDLongEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */