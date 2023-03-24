/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.msv.datatype.xsd.UnsignedLongType;
/*    */ import com.sun.xml.rpc.encoding.DeserializationException;
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
/*    */ import java.math.BigInteger;
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
/*    */ public class XSDUnsignedLongEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 40 */   private static final SimpleTypeEncoder encoder = new XSDUnsignedLongEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 47 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 53 */     if (obj == null) {
/* 54 */       return null;
/*    */     }
/* 56 */     return ((BigInteger)obj).toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 62 */     if (str == null) {
/* 63 */       return null;
/*    */     }
/* 65 */     Object obj = UnsignedLongType.theInstance._createJavaObject(str, null);
/* 66 */     if (obj != null)
/* 67 */       return obj; 
/* 68 */     throw new DeserializationException("xsd.invalid.unsignedLong", str);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 74 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDUnsignedLongEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */