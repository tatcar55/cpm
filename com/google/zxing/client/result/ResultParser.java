/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ import com.google.zxing.Result;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
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
/*     */ public abstract class ResultParser
/*     */ {
/*  42 */   private static final ResultParser[] PARSERS = new ResultParser[] { new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser() };
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
/*  65 */   private static final Pattern DIGITS = Pattern.compile("\\d+");
/*  66 */   private static final Pattern AMPERSAND = Pattern.compile("&");
/*  67 */   private static final Pattern EQUALS = Pattern.compile("=");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String BYTE_ORDER_MARK = "﻿";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ParsedResult parse(Result paramResult);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String getMassagedText(Result result) {
/*  81 */     String text = result.getText();
/*  82 */     if (text.startsWith("﻿")) {
/*  83 */       text = text.substring(1);
/*     */     }
/*  85 */     return text;
/*     */   }
/*     */   
/*     */   public static ParsedResult parseResult(Result theResult) {
/*  89 */     for (ResultParser parser : PARSERS) {
/*  90 */       ParsedResult result = parser.parse(theResult);
/*  91 */       if (result != null) {
/*  92 */         return result;
/*     */       }
/*     */     } 
/*  95 */     return new TextParsedResult(theResult.getText(), null);
/*     */   }
/*     */   
/*     */   protected static void maybeAppend(String value, StringBuilder result) {
/*  99 */     if (value != null) {
/* 100 */       result.append('\n');
/* 101 */       result.append(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void maybeAppend(String[] value, StringBuilder result) {
/* 106 */     if (value != null) {
/* 107 */       for (String s : value) {
/* 108 */         result.append('\n');
/* 109 */         result.append(s);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected static String[] maybeWrap(String value) {
/* 115 */     (new String[1])[0] = value; return (value == null) ? null : new String[1];
/*     */   }
/*     */   
/*     */   protected static String unescapeBackslash(String escaped) {
/* 119 */     int backslash = escaped.indexOf('\\');
/* 120 */     if (backslash < 0) {
/* 121 */       return escaped;
/*     */     }
/* 123 */     int max = escaped.length();
/* 124 */     StringBuilder unescaped = new StringBuilder(max - 1);
/* 125 */     unescaped.append(escaped.toCharArray(), 0, backslash);
/* 126 */     boolean nextIsEscaped = false;
/* 127 */     for (int i = backslash; i < max; i++) {
/* 128 */       char c = escaped.charAt(i);
/* 129 */       if (nextIsEscaped || c != '\\') {
/* 130 */         unescaped.append(c);
/* 131 */         nextIsEscaped = false;
/*     */       } else {
/* 133 */         nextIsEscaped = true;
/*     */       } 
/*     */     } 
/* 136 */     return unescaped.toString();
/*     */   }
/*     */   
/*     */   protected static int parseHexDigit(char c) {
/* 140 */     if (c >= '0' && c <= '9') {
/* 141 */       return c - 48;
/*     */     }
/* 143 */     if (c >= 'a' && c <= 'f') {
/* 144 */       return 10 + c - 97;
/*     */     }
/* 146 */     if (c >= 'A' && c <= 'F') {
/* 147 */       return 10 + c - 65;
/*     */     }
/* 149 */     return -1;
/*     */   }
/*     */   
/*     */   protected static boolean isStringOfDigits(CharSequence value, int length) {
/* 153 */     return (value != null && length > 0 && length == value.length() && DIGITS.matcher(value).matches());
/*     */   }
/*     */   
/*     */   protected static boolean isSubstringOfDigits(CharSequence value, int offset, int length) {
/* 157 */     if (value == null || length <= 0) {
/* 158 */       return false;
/*     */     }
/* 160 */     int max = offset + length;
/* 161 */     return (value.length() >= max && DIGITS.matcher(value.subSequence(offset, max)).matches());
/*     */   }
/*     */   
/*     */   static Map<String, String> parseNameValuePairs(String uri) {
/* 165 */     int paramStart = uri.indexOf('?');
/* 166 */     if (paramStart < 0) {
/* 167 */       return null;
/*     */     }
/* 169 */     Map<String, String> result = new HashMap<>(3);
/* 170 */     for (String keyValue : AMPERSAND.split(uri.substring(paramStart + 1))) {
/* 171 */       appendKeyValue(keyValue, result);
/*     */     }
/* 173 */     return result;
/*     */   }
/*     */   
/*     */   private static void appendKeyValue(CharSequence keyValue, Map<String, String> result) {
/* 177 */     String[] keyValueTokens = EQUALS.split(keyValue, 2);
/* 178 */     if (keyValueTokens.length == 2) {
/* 179 */       String key = keyValueTokens[0];
/* 180 */       String value = keyValueTokens[1];
/*     */       try {
/* 182 */         value = urlDecode(value);
/* 183 */         result.put(key, value);
/* 184 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String urlDecode(String encoded) {
/*     */     try {
/* 192 */       return URLDecoder.decode(encoded, "UTF-8");
/* 193 */     } catch (UnsupportedEncodingException uee) {
/* 194 */       throw new IllegalStateException(uee);
/*     */     } 
/*     */   }
/*     */   
/*     */   static String[] matchPrefixedField(String prefix, String rawText, char endChar, boolean trim) {
/* 199 */     List<String> matches = null;
/* 200 */     int i = 0;
/* 201 */     int max = rawText.length();
/* 202 */     while (i < max) {
/* 203 */       i = rawText.indexOf(prefix, i);
/* 204 */       if (i < 0) {
/*     */         break;
/*     */       }
/* 207 */       i += prefix.length();
/* 208 */       int start = i;
/* 209 */       boolean more = true;
/* 210 */       while (more) {
/* 211 */         i = rawText.indexOf(endChar, i);
/* 212 */         if (i < 0) {
/*     */           
/* 214 */           i = rawText.length();
/* 215 */           more = false; continue;
/* 216 */         }  if (countPrecedingBackslashes(rawText, i) % 2 != 0) {
/*     */           
/* 218 */           i++;
/*     */           continue;
/*     */         } 
/* 221 */         if (matches == null) {
/* 222 */           matches = new ArrayList<>(3);
/*     */         }
/* 224 */         String element = unescapeBackslash(rawText.substring(start, i));
/* 225 */         if (trim) {
/* 226 */           element = element.trim();
/*     */         }
/* 228 */         if (!element.isEmpty()) {
/* 229 */           matches.add(element);
/*     */         }
/* 231 */         i++;
/* 232 */         more = false;
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     if (matches == null || matches.isEmpty()) {
/* 237 */       return null;
/*     */     }
/* 239 */     return matches.<String>toArray(new String[matches.size()]);
/*     */   }
/*     */   
/*     */   private static int countPrecedingBackslashes(CharSequence s, int pos) {
/* 243 */     int count = 0;
/* 244 */     for (int i = pos - 1; i >= 0 && 
/* 245 */       s.charAt(i) == '\\'; i--) {
/* 246 */       count++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 251 */     return count;
/*     */   }
/*     */   
/*     */   static String matchSinglePrefixedField(String prefix, String rawText, char endChar, boolean trim) {
/* 255 */     String[] matches = matchPrefixedField(prefix, rawText, endChar, trim);
/* 256 */     return (matches == null) ? null : matches[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */