/*     */ package com.sun.xml.bind.api.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ class NameUtil
/*     */ {
/*     */   protected static final int UPPER_LETTER = 0;
/*     */   protected static final int LOWER_LETTER = 1;
/*     */   protected static final int OTHER_LETTER = 2;
/*     */   protected static final int DIGIT = 3;
/*     */   protected static final int OTHER = 4;
/*     */   
/*     */   protected boolean isPunct(char c) {
/*  61 */     return (c == '-' || c == '.' || c == ':' || c == '_' || c == '·' || c == '·' || c == '۝' || c == '۞');
/*     */   }
/*     */   
/*     */   protected static boolean isDigit(char c) {
/*  65 */     return ((c >= '0' && c <= '9') || Character.isDigit(c));
/*     */   }
/*     */   
/*     */   protected static boolean isUpper(char c) {
/*  69 */     return ((c >= 'A' && c <= 'Z') || Character.isUpperCase(c));
/*     */   }
/*     */   
/*     */   protected static boolean isLower(char c) {
/*  73 */     return ((c >= 'a' && c <= 'z') || Character.isLowerCase(c));
/*     */   }
/*     */   
/*     */   protected boolean isLetter(char c) {
/*  77 */     return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c));
/*     */   }
/*     */ 
/*     */   
/*     */   private String toLowerCase(String s) {
/*  82 */     return s.toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   private String toUpperCase(char c) {
/*  87 */     return String.valueOf(c).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   private String toUpperCase(String s) {
/*  92 */     return s.toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String capitalize(String s) {
/* 100 */     if (!isLower(s.charAt(0)))
/* 101 */       return s; 
/* 102 */     StringBuilder sb = new StringBuilder(s.length());
/* 103 */     sb.append(toUpperCase(s.charAt(0)));
/* 104 */     sb.append(toLowerCase(s.substring(1)));
/* 105 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextBreak(String s, int start) {
/* 110 */     int n = s.length();
/*     */     
/* 112 */     char c1 = s.charAt(start);
/* 113 */     int t1 = classify(c1);
/*     */     
/* 115 */     for (int i = start + 1; i < n; i++) {
/*     */ 
/*     */       
/* 118 */       int t0 = t1;
/*     */       
/* 120 */       c1 = s.charAt(i);
/* 121 */       t1 = classify(c1);
/*     */       
/* 123 */       switch (actionTable[t0 * 5 + t1]) {
/*     */         case 0:
/* 125 */           if (isPunct(c1)) return i; 
/*     */           break;
/*     */         case 1:
/* 128 */           if (i < n - 1) {
/* 129 */             char c2 = s.charAt(i + 1);
/* 130 */             if (isLower(c2))
/* 131 */               return i; 
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 135 */           return i;
/*     */       } 
/*     */     } 
/* 138 */     return -1;
/*     */   }
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
/* 153 */   private static final byte[] actionTable = new byte[25];
/*     */ 
/*     */   
/*     */   private static final byte ACTION_CHECK_PUNCT = 0;
/*     */ 
/*     */   
/*     */   private static final byte ACTION_CHECK_C2 = 1;
/*     */   
/*     */   private static final byte ACTION_BREAK = 2;
/*     */   
/*     */   private static final byte ACTION_NOBREAK = 3;
/*     */ 
/*     */   
/*     */   private static byte decideAction(int t0, int t1) {
/* 167 */     if (t0 == 4 && t1 == 4) return 0; 
/* 168 */     if (!xor((t0 == 3), (t1 == 3))) return 2; 
/* 169 */     if (t0 == 1 && t1 != 1) return 2; 
/* 170 */     if (!xor((t0 <= 2), (t1 <= 2))) return 2; 
/* 171 */     if (!xor((t0 == 2), (t1 == 2))) return 2;
/*     */     
/* 173 */     if (t0 == 0 && t1 == 0) return 1;
/*     */     
/* 175 */     return 3;
/*     */   }
/*     */   
/*     */   private static boolean xor(boolean x, boolean y) {
/* 179 */     return ((x && y) || (!x && !y));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 184 */     for (int t0 = 0; t0 < 5; t0++) {
/* 185 */       for (int t1 = 0; t1 < 5; t1++) {
/* 186 */         actionTable[t0 * 5 + t1] = decideAction(t0, t1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int classify(char c0) {
/* 193 */     switch (Character.getType(c0)) { case 1:
/* 194 */         return 0;
/* 195 */       case 2: return 1;
/*     */       case 3: case 4:
/*     */       case 5:
/* 198 */         return 2;
/* 199 */       case 9: return 3; }
/* 200 */      return 4;
/*     */   }
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
/*     */   public List<String> toWordList(String s) {
/* 215 */     ArrayList<String> ss = new ArrayList<String>();
/* 216 */     int n = s.length(); int i;
/* 217 */     for (i = 0; i < n; ) {
/*     */ 
/*     */       
/* 220 */       while (i < n && 
/* 221 */         isPunct(s.charAt(i)))
/*     */       {
/* 223 */         i++;
/*     */       }
/* 225 */       if (i >= n) {
/*     */         break;
/*     */       }
/* 228 */       int b = nextBreak(s, i);
/* 229 */       String w = (b == -1) ? s.substring(i) : s.substring(i, b);
/* 230 */       ss.add(escape(capitalize(w)));
/* 231 */       if (b == -1)
/* 232 */         break;  i = b;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     return ss;
/*     */   }
/*     */   
/*     */   protected String toMixedCaseName(List<String> ss, boolean startUpper) {
/* 243 */     StringBuilder sb = new StringBuilder();
/* 244 */     if (!ss.isEmpty()) {
/* 245 */       sb.append(startUpper ? ss.get(0) : toLowerCase(ss.get(0)));
/* 246 */       for (int i = 1; i < ss.size(); i++)
/* 247 */         sb.append(ss.get(i)); 
/*     */     } 
/* 249 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String toMixedCaseVariableName(String[] ss, boolean startUpper, boolean cdrUpper) {
/* 255 */     if (cdrUpper)
/* 256 */       for (int i = 1; i < ss.length; i++)
/* 257 */         ss[i] = capitalize(ss[i]);  
/* 258 */     StringBuilder sb = new StringBuilder();
/* 259 */     if (ss.length > 0) {
/* 260 */       sb.append(startUpper ? ss[0] : toLowerCase(ss[0]));
/* 261 */       for (int i = 1; i < ss.length; i++)
/* 262 */         sb.append(ss[i]); 
/*     */     } 
/* 264 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toConstantName(String s) {
/* 276 */     return toConstantName(toWordList(s));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toConstantName(List<String> ss) {
/* 287 */     StringBuilder sb = new StringBuilder();
/* 288 */     if (!ss.isEmpty()) {
/* 289 */       sb.append(toUpperCase(ss.get(0)));
/* 290 */       for (int i = 1; i < ss.size(); i++) {
/* 291 */         sb.append('_');
/* 292 */         sb.append(toUpperCase(ss.get(i)));
/*     */       } 
/*     */     } 
/* 295 */     return sb.toString();
/*     */   }
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
/*     */   public static void escape(StringBuilder sb, String s, int start) {
/* 314 */     int n = s.length();
/* 315 */     for (int i = start; i < n; i++) {
/* 316 */       char c = s.charAt(i);
/* 317 */       if (Character.isJavaIdentifierPart(c)) {
/* 318 */         sb.append(c);
/*     */       } else {
/* 320 */         sb.append('_');
/* 321 */         if (c <= '\017') { sb.append("000"); }
/* 322 */         else if (c <= 'ÿ') { sb.append("00"); }
/* 323 */         else if (c <= '࿿') { sb.append('0'); }
/* 324 */          sb.append(Integer.toString(c, 16));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String escape(String s) {
/* 334 */     int n = s.length();
/* 335 */     for (int i = 0; i < n; i++) {
/* 336 */       if (!Character.isJavaIdentifierPart(s.charAt(i))) {
/* 337 */         StringBuilder sb = new StringBuilder(s.substring(0, i));
/* 338 */         escape(sb, s, i);
/* 339 */         return sb.toString();
/*     */       } 
/* 341 */     }  return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\api\impl\NameUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */