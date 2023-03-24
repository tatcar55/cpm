/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSDBoxedBase64BinaryEncoder
/*     */   extends XSDBase64EncoderBase
/*     */ {
/*  37 */   private static final SimpleTypeEncoder encoder = new XSDBoxedBase64BinaryEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance() {
/*  44 */     return encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*  50 */     if (obj == null) {
/*  51 */       return null;
/*     */     }
/*  53 */     Byte[] value = (Byte[])obj;
/*  54 */     if (value.length == 0) {
/*  55 */       return "";
/*     */     }
/*  57 */     int blockCount = value.length / 3;
/*  58 */     int partialBlockLength = value.length % 3;
/*     */     
/*  60 */     if (partialBlockLength != 0) {
/*  61 */       blockCount++;
/*     */     }
/*     */     
/*  64 */     int encodedLength = blockCount * 4;
/*  65 */     StringBuffer encodedValue = new StringBuffer(encodedLength);
/*     */     
/*  67 */     int idx = 0;
/*  68 */     for (int i = 0; i < blockCount; i++) {
/*  69 */       int b1 = value[idx++].byteValue();
/*  70 */       int b2 = (idx < value.length) ? value[idx++].byteValue() : 0;
/*  71 */       int b3 = (idx < value.length) ? value[idx++].byteValue() : 0;
/*     */       
/*  73 */       if (b1 < 0) {
/*  74 */         b1 += 256;
/*     */       }
/*  76 */       if (b2 < 0) {
/*  77 */         b2 += 256;
/*     */       }
/*  79 */       if (b3 < 0) {
/*  80 */         b3 += 256;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  85 */       char encodedChar = encodeBase64[b1 >> 2];
/*  86 */       encodedValue.append(encodedChar);
/*     */       
/*  88 */       encodedChar = encodeBase64[(b1 & 0x3) << 4 | b2 >> 4];
/*  89 */       encodedValue.append(encodedChar);
/*     */       
/*  91 */       encodedChar = encodeBase64[(b2 & 0xF) << 2 | b3 >> 6];
/*  92 */       encodedValue.append(encodedChar);
/*     */       
/*  94 */       encodedChar = encodeBase64[b3 & 0x3F];
/*  95 */       encodedValue.append(encodedChar);
/*     */     } 
/*     */     
/*  98 */     switch (partialBlockLength) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 103 */         encodedValue.setCharAt(encodedLength - 1, '=');
/* 104 */         encodedValue.setCharAt(encodedLength - 2, '=');
/*     */         break;
/*     */       case 2:
/* 107 */         encodedValue.setCharAt(encodedLength - 1, '=');
/*     */         break;
/*     */     } 
/*     */     
/* 111 */     return encodedValue.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 117 */     if (str == null) {
/* 118 */       return null;
/*     */     }
/* 120 */     String uri = "";
/* 121 */     String encodedValue = EncoderUtils.removeWhitespace(str);
/* 122 */     int encodedLength = encodedValue.length();
/* 123 */     if (encodedLength == 0) {
/* 124 */       return new Byte[0];
/*     */     }
/* 126 */     int blockCount = encodedLength / 4;
/* 127 */     int partialBlockLength = 3;
/*     */     
/* 129 */     if (encodedValue.charAt(encodedLength - 1) == '=') {
/* 130 */       partialBlockLength--;
/* 131 */       if (encodedValue.charAt(encodedLength - 2) == '=') {
/* 132 */         partialBlockLength--;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     int valueLength = (blockCount - 1) * 3 + partialBlockLength;
/* 137 */     Byte[] value = new Byte[valueLength];
/*     */     
/* 139 */     int idx = 0;
/* 140 */     int encodedIdx = 0;
/* 141 */     for (int i = 0; i < blockCount; i++) {
/* 142 */       int x1 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 143 */       int x2 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 144 */       int x3 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 145 */       int x4 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/*     */       
/* 147 */       value[idx++] = new Byte((byte)(x1 << 2 | x2 >> 4));
/* 148 */       if (idx < valueLength) {
/* 149 */         value[idx++] = new Byte((byte)((x2 & 0xF) << 4 | x3 >> 2));
/*     */       }
/*     */       
/* 152 */       if (idx < valueLength) {
/* 153 */         value[idx++] = new Byte((byte)((x3 & 0x3) << 6 | x4));
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDBoxedBase64BinaryEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */