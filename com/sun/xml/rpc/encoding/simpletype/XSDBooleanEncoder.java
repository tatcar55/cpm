/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.encoding.DeserializationException;
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
/*    */ public class XSDBooleanEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 37 */   private static final SimpleTypeEncoder encoder = new XSDBooleanEncoder();
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
/* 52 */     return ((Boolean)obj).toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 58 */     if (str == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     String tmp = EncoderUtils.collapseWhitespace(str);
/*    */ 
/*    */ 
/*    */     
/* 65 */     if (tmp.equals("true")) {
/* 66 */       return Boolean.TRUE;
/*    */     }
/* 68 */     if (tmp.equals("false")) {
/* 69 */       return Boolean.FALSE;
/*    */     }
/*    */     
/* 72 */     if (tmp.equals("1")) {
/* 73 */       return Boolean.TRUE;
/*    */     }
/* 75 */     if (tmp.equals("0")) {
/* 76 */       return Boolean.FALSE;
/*    */     }
/*    */     
/* 79 */     throw new DeserializationException("xsd.invalid.boolean", tmp);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 85 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDBooleanEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */