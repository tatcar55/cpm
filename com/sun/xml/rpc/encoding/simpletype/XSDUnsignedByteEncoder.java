/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.msv.datatype.xsd.UnsignedByteType;
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
/*    */ public class XSDUnsignedByteEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 38 */   private static final SimpleTypeEncoder encoder = new XSDUnsignedByteEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 45 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 51 */     if (obj == null) {
/* 52 */       return null;
/*    */     }
/* 54 */     return ((Short)obj).toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 60 */     if (str == null) {
/* 61 */       return null;
/*    */     }
/* 63 */     Object obj = UnsignedByteType.theInstance._createJavaObject(str, null);
/* 64 */     if (obj != null)
/* 65 */       return obj; 
/* 66 */     throw new DeserializationException("xsd.invalid.unsignedShort", str);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 72 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDUnsignedByteEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */