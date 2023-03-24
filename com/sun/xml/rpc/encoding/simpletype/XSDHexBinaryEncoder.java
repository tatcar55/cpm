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
/*    */ public class XSDHexBinaryEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 37 */   private static final SimpleTypeEncoder encoder = new XSDHexBinaryEncoder();
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
/* 52 */     byte[] value = (byte[])obj;
/* 53 */     if (value.length == 0) {
/* 54 */       return "";
/*    */     }
/*    */     
/* 57 */     StringBuffer encodedValue = new StringBuffer(value.length * 2);
/* 58 */     for (int i = 0; i < value.length; i++) {
/* 59 */       encodedValue.append(encodeHex[value[i] >> 4 & 0xF]);
/* 60 */       encodedValue.append(encodeHex[value[i] & 0xF]);
/*    */     } 
/*    */     
/* 63 */     return encodedValue.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 69 */     if (str == null) {
/* 70 */       return null;
/*    */     }
/* 72 */     String encodedValue = EncoderUtils.collapseWhitespace(str);
/* 73 */     int valueLength = encodedValue.length() / 2;
/* 74 */     byte[] value = new byte[valueLength];
/*    */     
/* 76 */     int encodedIdx = 0;
/* 77 */     for (int i = 0; i < valueLength; i++) {
/* 78 */       int nibble1 = decodeHex[encodedValue.charAt(encodedIdx++) - 48];
/* 79 */       int nibble2 = decodeHex[encodedValue.charAt(encodedIdx++) - 48];
/* 80 */       value[i] = (byte)(nibble1 << 4 | nibble2);
/*    */     } 
/*    */     
/* 83 */     return value;
/*    */   }
/*    */   
/* 86 */   private static final char[] encodeHex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 91 */   private static final int[] decodeHex = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15 };
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDHexBinaryEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */