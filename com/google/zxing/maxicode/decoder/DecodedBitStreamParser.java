/*     */ package com.google.zxing.maxicode.decoder;
/*     */ 
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
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
/*     */ final class DecodedBitStreamParser
/*     */ {
/*     */   private static final char SHIFTA = '￰';
/*     */   private static final char SHIFTB = '￱';
/*     */   private static final char SHIFTC = '￲';
/*     */   private static final char SHIFTD = '￳';
/*     */   private static final char SHIFTE = '￴';
/*     */   private static final char TWOSHIFTA = '￵';
/*     */   private static final char THREESHIFTA = '￶';
/*     */   private static final char LATCHA = '￷';
/*     */   private static final char LATCHB = '￸';
/*     */   private static final char LOCK = '￹';
/*     */   private static final char ECI = '￺';
/*     */   private static final char NS = '￻';
/*     */   private static final char PAD = '￼';
/*     */   private static final char FS = '\034';
/*     */   private static final char GS = '\035';
/*     */   private static final char RS = '\036';
/*  48 */   private static final NumberFormat NINE_DIGITS = new DecimalFormat("000000000");
/*  49 */   private static final NumberFormat THREE_DIGITS = new DecimalFormat("000");
/*     */   
/*  51 */   private static final String[] SETS = new String[] { "\nABCDEFGHIJKLMNOPQRSTUVWXYZ￺\034\035\036￻ ￼\"#$%&'()*+,-./0123456789:￱￲￳￴￸", "`abcdefghijklmnopqrstuvwxyz￺\034\035\036￻{￼}~;<=>?[\\]^_ ,./:@!|￼￵￶￼￰￲￳￴￷", "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚ￺\034\035\036ÛÜÝÞßª¬±²³µ¹º¼½¾￷ ￹￳￴￸", "àáâãäåæçèéêëìíîïðñòóôõö÷øùú￺\034\035\036￻ûüýþÿ¡¨«¯°´·¸»¿￷ ￲￹￴￸", "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032￺￼￼\033￻\034\035\036\037 ¢£¤¥¦§©­®¶￷ ￲￳￹￸", "\000\001\002\003\004\005\006\007\b\t\n\013\f\r\016\017\020\021\022\023\024\025\026\027\030\031\032\033\034\035\036\037 !\"#$%&'()*+,-./0123456789:;<=>?" };
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
/*     */   static DecoderResult decode(byte[] bytes, int mode) {
/*     */     String postcode, country, service;
/*  64 */     StringBuilder result = new StringBuilder(144);
/*  65 */     switch (mode) {
/*     */       
/*     */       case 2:
/*     */       case 3:
/*  69 */         if (mode == 2) {
/*  70 */           int pc = getPostCode2(bytes);
/*  71 */           NumberFormat df = new DecimalFormat("0000000000".substring(0, getPostCode2Length(bytes)));
/*  72 */           postcode = df.format(pc);
/*     */         } else {
/*  74 */           postcode = getPostCode3(bytes);
/*     */         } 
/*  76 */         country = THREE_DIGITS.format(getCountry(bytes));
/*  77 */         service = THREE_DIGITS.format(getServiceClass(bytes));
/*  78 */         result.append(getMessage(bytes, 10, 84));
/*  79 */         if (result.toString().startsWith("[)>\03601\035")) {
/*  80 */           result.insert(9, postcode + '\035' + country + '\035' + service + '\035'); break;
/*     */         } 
/*  82 */         result.insert(0, postcode + '\035' + country + '\035' + service + '\035');
/*     */         break;
/*     */       
/*     */       case 4:
/*  86 */         result.append(getMessage(bytes, 1, 93));
/*     */         break;
/*     */       case 5:
/*  89 */         result.append(getMessage(bytes, 1, 77));
/*     */         break;
/*     */     } 
/*  92 */     return new DecoderResult(bytes, result.toString(), null, String.valueOf(mode));
/*     */   }
/*     */   
/*     */   private static int getBit(int bit, byte[] bytes) {
/*  96 */     bit--;
/*  97 */     return ((bytes[bit / 6] & 1 << 5 - bit % 6) == 0) ? 0 : 1;
/*     */   }
/*     */   
/*     */   private static int getInt(byte[] bytes, byte[] x) {
/* 101 */     if (x.length == 0) {
/* 102 */       throw new IllegalArgumentException();
/*     */     }
/* 104 */     int val = 0;
/* 105 */     for (int i = 0; i < x.length; i++) {
/* 106 */       val += getBit(x[i], bytes) << x.length - i - 1;
/*     */     }
/* 108 */     return val;
/*     */   }
/*     */   
/*     */   private static int getCountry(byte[] bytes) {
/* 112 */     return getInt(bytes, new byte[] { 53, 54, 43, 44, 45, 46, 47, 48, 37, 38 });
/*     */   }
/*     */   
/*     */   private static int getServiceClass(byte[] bytes) {
/* 116 */     return getInt(bytes, new byte[] { 55, 56, 57, 58, 59, 60, 49, 50, 51, 52 });
/*     */   }
/*     */   
/*     */   private static int getPostCode2Length(byte[] bytes) {
/* 120 */     return getInt(bytes, new byte[] { 39, 40, 41, 42, 31, 32 });
/*     */   }
/*     */   
/*     */   private static int getPostCode2(byte[] bytes) {
/* 124 */     return getInt(bytes, new byte[] { 33, 34, 35, 36, 25, 26, 27, 28, 29, 30, 19, 20, 21, 22, 23, 24, 13, 14, 15, 16, 17, 18, 7, 8, 9, 10, 11, 12, 1, 2 });
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getPostCode3(byte[] bytes) {
/* 129 */     return String.valueOf(new char[] { SETS[0]
/*     */           
/* 131 */           .charAt(getInt(bytes, new byte[] { 39, 40, 41, 42, 31, 32 })), SETS[0]
/* 132 */           .charAt(getInt(bytes, new byte[] { 33, 34, 35, 36, 25, 26 })), SETS[0]
/* 133 */           .charAt(getInt(bytes, new byte[] { 27, 28, 29, 30, 19, 20 })), SETS[0]
/* 134 */           .charAt(getInt(bytes, new byte[] { 21, 22, 23, 24, 13, 14 })), SETS[0]
/* 135 */           .charAt(getInt(bytes, new byte[] { 15, 16, 17, 18, 7, 8 })), SETS[0]
/* 136 */           .charAt(getInt(bytes, new byte[] { 9, 10, 11, 12, 1, 2 })) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getMessage(byte[] bytes, int start, int len) {
/* 142 */     StringBuilder sb = new StringBuilder();
/* 143 */     int shift = -1;
/* 144 */     int set = 0;
/* 145 */     int lastset = 0;
/* 146 */     for (int i = start; i < start + len; i++) {
/* 147 */       int nsval; char c = SETS[set].charAt(bytes[i]);
/* 148 */       switch (c) {
/*     */         case '￷':
/* 150 */           set = 0;
/* 151 */           shift = -1;
/*     */           break;
/*     */         case '￸':
/* 154 */           set = 1;
/* 155 */           shift = -1;
/*     */           break;
/*     */         case '￰':
/*     */         case '￱':
/*     */         case '￲':
/*     */         case '￳':
/*     */         case '￴':
/* 162 */           lastset = set;
/* 163 */           set = c - 65520;
/* 164 */           shift = 1;
/*     */           break;
/*     */         case '￵':
/* 167 */           lastset = set;
/* 168 */           set = 0;
/* 169 */           shift = 2;
/*     */           break;
/*     */         case '￶':
/* 172 */           lastset = set;
/* 173 */           set = 0;
/* 174 */           shift = 3;
/*     */           break;
/*     */         case '￻':
/* 177 */           nsval = (bytes[++i] << 24) + (bytes[++i] << 18) + (bytes[++i] << 12) + (bytes[++i] << 6) + bytes[++i];
/* 178 */           sb.append(NINE_DIGITS.format(nsval));
/*     */           break;
/*     */         case '￹':
/* 181 */           shift = -1;
/*     */           break;
/*     */         default:
/* 184 */           sb.append(c); break;
/*     */       } 
/* 186 */       if (shift-- == 0) {
/* 187 */         set = lastset;
/*     */       }
/*     */     } 
/* 190 */     while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '￼') {
/* 191 */       sb.setLength(sb.length() - 1);
/*     */     }
/* 193 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\maxicode\decoder\DecodedBitStreamParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */