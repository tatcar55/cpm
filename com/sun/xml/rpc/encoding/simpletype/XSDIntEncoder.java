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
/*    */ public class XSDIntEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 37 */   private static final SimpleTypeEncoder encoder = new XSDIntEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 43 */     return encoder;
/*    */   }
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) {
/* 47 */     if (obj == null) {
/* 48 */       return null;
/*    */     }
/* 50 */     return ((Integer)obj).toString();
/*    */   }
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) {
/* 54 */     if (str == null) {
/* 55 */       return null;
/*    */     }
/* 57 */     return new Integer(EncoderUtils.collapseWhitespace(str));
/*    */   }
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 61 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDIntEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */