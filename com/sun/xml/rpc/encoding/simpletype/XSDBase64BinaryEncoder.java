/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.FastInfosetWriter;
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
/*     */ 
/*     */ 
/*     */ public class XSDBase64BinaryEncoder
/*     */   extends XSDBase64EncoderBase
/*     */ {
/*  40 */   private static final SimpleTypeEncoder encoder = new XSDBase64BinaryEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance() {
/*  47 */     return encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*  53 */     if (obj == null) {
/*  54 */       return null;
/*     */     }
/*  56 */     byte[] value = (byte[])obj;
/*  57 */     if (value.length == 0) {
/*  58 */       return "";
/*     */     }
/*     */     
/*  61 */     int blockCount = value.length / 3;
/*  62 */     int partialBlockLength = value.length % 3;
/*     */     
/*  64 */     if (partialBlockLength != 0) {
/*  65 */       blockCount++;
/*     */     }
/*     */     
/*  68 */     int encodedLength = blockCount * 4;
/*  69 */     StringBuffer encodedValue = new StringBuffer(encodedLength);
/*     */     
/*  71 */     int idx = 0;
/*  72 */     for (int i = 0; i < blockCount; i++) {
/*  73 */       int b1 = value[idx++];
/*  74 */       int b2 = (idx < value.length) ? value[idx++] : 0;
/*  75 */       int b3 = (idx < value.length) ? value[idx++] : 0;
/*     */       
/*  77 */       if (b1 < 0) {
/*  78 */         b1 += 256;
/*     */       }
/*  80 */       if (b2 < 0) {
/*  81 */         b2 += 256;
/*     */       }
/*  83 */       if (b3 < 0) {
/*  84 */         b3 += 256;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  89 */       char encodedChar = encodeBase64[b1 >> 2];
/*  90 */       encodedValue.append(encodedChar);
/*     */       
/*  92 */       encodedChar = encodeBase64[(b1 & 0x3) << 4 | b2 >> 4];
/*  93 */       encodedValue.append(encodedChar);
/*     */       
/*  95 */       encodedChar = encodeBase64[(b2 & 0xF) << 2 | b3 >> 6];
/*  96 */       encodedValue.append(encodedChar);
/*     */       
/*  98 */       encodedChar = encodeBase64[b3 & 0x3F];
/*  99 */       encodedValue.append(encodedChar);
/*     */     } 
/*     */     
/* 102 */     switch (partialBlockLength) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 107 */         encodedValue.setCharAt(encodedLength - 1, '=');
/* 108 */         encodedValue.setCharAt(encodedLength - 2, '=');
/*     */         break;
/*     */       case 2:
/* 111 */         encodedValue.setCharAt(encodedLength - 1, '=');
/*     */         break;
/*     */     } 
/*     */     
/* 115 */     return encodedValue.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 121 */     if (str == null) {
/* 122 */       return null;
/*     */     }
/*     */     
/* 125 */     String uri = "";
/* 126 */     String encodedValue = EncoderUtils.removeWhitespace(str);
/* 127 */     int encodedLength = encodedValue.length();
/* 128 */     if (encodedLength == 0) {
/* 129 */       return new byte[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     int blockCount = encodedLength / 4;
/* 137 */     int partialBlockLength = 3;
/*     */     
/* 139 */     if (encodedValue.charAt(encodedLength - 1) == '=') {
/* 140 */       partialBlockLength--;
/* 141 */       if (encodedValue.charAt(encodedLength - 2) == '=') {
/* 142 */         partialBlockLength--;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     int valueLength = (blockCount - 1) * 3 + partialBlockLength;
/* 147 */     byte[] value = new byte[valueLength];
/*     */     
/* 149 */     int idx = 0;
/* 150 */     int encodedIdx = 0;
/* 151 */     for (int i = 0; i < blockCount; i++) {
/* 152 */       int x1 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 153 */       int x2 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 154 */       int x3 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/* 155 */       int x4 = decodeBase64[encodedValue.charAt(encodedIdx++) - 43];
/*     */       
/* 157 */       value[idx++] = (byte)(x1 << 2 | x2 >> 4);
/* 158 */       if (idx < valueLength) {
/* 159 */         value[idx++] = (byte)((x2 & 0xF) << 4 | x3 >> 2);
/*     */       }
/* 161 */       if (idx < valueLength) {
/* 162 */         value[idx++] = (byte)((x3 & 0x3) << 6 | x4);
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return value;
/*     */   }
/*     */   
/*     */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 170 */     if (obj == null) {
/*     */       return;
/*     */     }
/* 173 */     byte[] value = (byte[])obj;
/* 174 */     if (value.length == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 179 */     if (writer instanceof FastInfosetWriter) {
/* 180 */       ((FastInfosetWriter)writer).writeBytes(value, 0, value.length);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (writer instanceof com.sun.xml.rpc.streaming.XmlTreeWriter) {
/* 190 */       writer.writeCharsUnquoted(objectToString(obj, (XMLWriter)null));
/*     */       
/*     */       return;
/*     */     } 
/* 194 */     int blockCount = value.length / 3;
/* 195 */     int partialBlockLength = value.length % 3;
/*     */     
/* 197 */     if (partialBlockLength != 0) {
/* 198 */       blockCount++;
/*     */     }
/*     */     
/* 201 */     int encodedLength = blockCount * 4;
/* 202 */     char[] encodedValue = new char[4];
/*     */     
/* 204 */     int idx = 0;
/* 205 */     for (int i = 0; i < blockCount; i++) {
/* 206 */       int b1 = value[idx++];
/* 207 */       int b2 = (idx < value.length) ? value[idx++] : 0;
/* 208 */       int b3 = (idx < value.length) ? value[idx++] : 0;
/*     */       
/* 210 */       if (b1 < 0) {
/* 211 */         b1 += 256;
/*     */       }
/* 213 */       if (b2 < 0) {
/* 214 */         b2 += 256;
/*     */       }
/* 216 */       if (b3 < 0) {
/* 217 */         b3 += 256;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 222 */       char encodedChar = encodeBase64[b1 >> 2];
/* 223 */       encodedValue[0] = encodedChar;
/*     */       
/* 225 */       encodedChar = encodeBase64[(b1 & 0x3) << 4 | b2 >> 4];
/* 226 */       encodedValue[1] = encodedChar;
/*     */       
/* 228 */       encodedChar = encodeBase64[(b2 & 0xF) << 2 | b3 >> 6];
/* 229 */       encodedValue[2] = encodedChar;
/*     */       
/* 231 */       encodedChar = encodeBase64[b3 & 0x3F];
/* 232 */       encodedValue[3] = encodedChar;
/*     */       
/* 234 */       if (i == blockCount - 1) {
/* 235 */         switch (partialBlockLength) {
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/* 240 */             encodedValue[2] = '=';
/* 241 */             encodedValue[3] = '=';
/*     */             break;
/*     */           case 2:
/* 244 */             encodedValue[3] = '=';
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/* 249 */       writer.writeCharsUnquoted(encodedValue, 0, 4);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDBase64BinaryEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */