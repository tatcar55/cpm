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
/*    */ public class XSDBoxedHexBinaryEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 37 */   private static final SimpleTypeEncoder encoder = new XSDBoxedHexBinaryEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 44 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 50 */     if (obj == null) {
/* 51 */       return null;
/*    */     }
/* 53 */     Byte[] value = (Byte[])obj;
/* 54 */     if (value.length == 0) {
/* 55 */       return "";
/*    */     }
/*    */     
/* 58 */     StringBuffer encodedValue = new StringBuffer(value.length * 2);
/* 59 */     for (int i = 0; i < value.length; i++) {
/* 60 */       encodedValue.append(encodeHex[value[i].byteValue() >> 4 & 0xF]);
/* 61 */       encodedValue.append(encodeHex[value[i].byteValue() & 0xF]);
/*    */     } 
/*    */     
/* 64 */     return encodedValue.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 70 */     if (str == null) {
/* 71 */       return null;
/*    */     }
/* 73 */     String encodedValue = EncoderUtils.collapseWhitespace(str);
/* 74 */     int valueLength = encodedValue.length() / 2;
/* 75 */     Byte[] value = new Byte[valueLength];
/*    */     
/* 77 */     int encodedIdx = 0;
/* 78 */     for (int i = 0; i < valueLength; i++) {
/* 79 */       int nibble1 = decodeHex[encodedValue.charAt(encodedIdx++) - 48];
/* 80 */       int nibble2 = decodeHex[encodedValue.charAt(encodedIdx++) - 48];
/* 81 */       value[i] = new Byte((byte)(nibble1 << 4 | nibble2));
/*    */     } 
/*    */     
/* 84 */     return value;
/*    */   }
/*    */   
/* 87 */   private static final char[] encodeHex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   private static final int[] decodeHex = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15 };
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDBoxedHexBinaryEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */