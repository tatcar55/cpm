/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
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
/*     */ public class XSDDateTimeDateEncoder
/*     */   extends SimpleTypeEncoderBase
/*     */ {
/*  45 */   private static final SimpleTypeEncoder encoder = new XSDDateTimeDateEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance() {
/*  52 */     return encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*  58 */     if (obj == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     Calendar calendar = Calendar.getInstance(gmtTimeZone);
/*  62 */     calendar.setTime((Date)obj);
/*  63 */     boolean isBC = (calendar.get(0) == 0);
/*  64 */     StringBuffer buf = new StringBuffer();
/*  65 */     if (isBC) {
/*  66 */       calendar.set(0, 1);
/*  67 */       buf.append("-");
/*     */     } 
/*  69 */     synchronized (dateFormatter) {
/*  70 */       buf.append(dateFormatter.format(calendar.getTime()));
/*     */     } 
/*     */     
/*  73 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/*  79 */     if (str == null) {
/*  80 */       return null;
/*     */     }
/*  82 */     return decodeDateUtil(str, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateDateStr(String dateStr) throws Exception {
/*  87 */     if (dateStr.length() < 19)
/*  88 */       throw new DeserializationException("xsd.invalid.date", dateStr); 
/*     */   }
/*     */   
/*     */   protected static String getDateFormatPattern(String xsdDateTime) {
/*  92 */     String formatPattern = "yyyy";
/*  93 */     int idx = xsdDateTime.indexOf('-', 4); int i;
/*  94 */     for (i = 4; i < idx; i++)
/*  95 */       formatPattern = formatPattern + "y"; 
/*  96 */     formatPattern = formatPattern + "-MM-dd'T'HH:mm:ss";
/*  97 */     idx = xsdDateTime.indexOf('.');
/*  98 */     for (i = idx; i < xsdDateTime.length() - 1 && i < idx + 3 && 
/*  99 */       Character.isDigit(xsdDateTime.charAt(i + 1)); i++) {
/* 100 */       if (i == idx) {
/* 101 */         formatPattern = formatPattern + ".";
/*     */       }
/* 103 */       formatPattern = formatPattern + "S";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return formatPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Date decodeDateUtil(String str, StringBuffer zone) throws Exception {
/* 115 */     if (str == null) {
/* 116 */       return null;
/*     */     }
/* 118 */     str = EncoderUtils.collapseWhitespace(str);
/* 119 */     Calendar cal = Calendar.getInstance();
/* 120 */     boolean isNeg = false;
/*     */     
/* 122 */     str = EncoderUtils.collapseWhitespace(str);
/* 123 */     if (str.charAt(0) == '+') {
/* 124 */       str = str.substring(1);
/*     */     }
/* 126 */     if (str.charAt(0) == '-') {
/* 127 */       str = str.substring(1);
/* 128 */       isNeg = true;
/*     */     } 
/* 130 */     validateDateStr(str);
/* 131 */     StringBuffer strBuf = new StringBuffer(30);
/* 132 */     int dateLen = getDateFormatPattern(str, strBuf);
/* 133 */     String pattern = strBuf.toString();
/* 134 */     SimpleDateFormat df = new SimpleDateFormat(pattern);
/* 135 */     df.setTimeZone(gmtTimeZone);
/* 136 */     String tmp = str.substring(0, dateLen);
/* 137 */     Date date = df.parse(str.substring(0, dateLen));
/*     */ 
/*     */     
/* 140 */     if (dateLen < str.length()) {
/* 141 */       int start = dateLen;
/* 142 */       if (Character.isDigit(str.charAt(start))) {
/* 143 */         int end = start;
/*     */ 
/*     */ 
/*     */         
/* 147 */         while (end < str.length() && Character.isDigit(str.charAt(end))) {
/* 148 */           end++;
/*     */         }
/* 150 */         String tmp2 = str.substring(start, start + 1);
/* 151 */         int fractmilli = Integer.parseInt(str.substring(start, start + 1));
/*     */         
/* 153 */         if (fractmilli >= 5)
/* 154 */           date.setTime(date.getTime() + 1L); 
/* 155 */         start = end;
/*     */       } 
/*     */       
/* 158 */       if (start < str.length()) {
/* 159 */         if (str.charAt(start) != 'Z') {
/* 160 */           Date tzOffset; if (zone != null)
/* 161 */             zone.append(str.substring(start)); 
/* 162 */           tmp = str.substring(start + 1);
/*     */           
/* 164 */           synchronized (timeZoneFormatter) {
/* 165 */             tzOffset = timeZoneFormatter.parse(str.substring(start + 1));
/*     */           } 
/*     */           
/* 168 */           long millis = (str.charAt(start) == '+') ? -tzOffset.getTime() : tzOffset.getTime();
/*     */ 
/*     */ 
/*     */           
/* 172 */           date.setTime(date.getTime() + millis);
/* 173 */         } else if (str.charAt(start) == 'Z') {
/* 174 */           cal.setTimeZone(gmtTimeZone);
/*     */         } 
/*     */       }
/*     */     } 
/* 178 */     if (isNeg) {
/* 179 */       cal.setTime(date);
/* 180 */       cal.set(0, 0);
/* 181 */       date = cal.getTime();
/*     */     } 
/* 183 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getDateFormatPattern(String dateStr, StringBuffer strBuf) {
/* 190 */     String formatPattern = "yyyy";
/* 191 */     strBuf.append(formatPattern);
/* 192 */     int idx = dateStr.indexOf('-', 4); int i;
/* 193 */     for (i = 4; i < idx; i++)
/* 194 */       strBuf.append('y'); 
/* 195 */     strBuf.append("-MM-dd'T'HH:mm:ss");
/* 196 */     idx = dateStr.indexOf('.');
/* 197 */     i = idx;
/* 198 */     while (idx > 0 && i < dateStr.length() - 1 && i < idx + 3) {
/*     */       
/* 200 */       if (Character.isDigit(dateStr.charAt(i + 1))) {
/* 201 */         if (i == idx) {
/* 202 */           strBuf.append('.');
/*     */         }
/* 204 */         strBuf.append('S');
/*     */         
/*     */         i++;
/*     */       } 
/*     */     } 
/* 209 */     return strBuf.length() - 2;
/*     */   }
/*     */   
/* 212 */   protected static final Locale locale = new Locale("en_US");
/* 213 */   protected static final SimpleDateFormat timeZoneFormatter = new SimpleDateFormat("HH:mm", locale);
/*     */ 
/*     */   
/* 216 */   protected static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
/*     */   
/* 218 */   protected static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale);
/*     */ 
/*     */   
/*     */   static {
/* 222 */     dateFormatter.setTimeZone(gmtTimeZone);
/* 223 */     timeZoneFormatter.setTimeZone(gmtTimeZone);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDDateTimeDateEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */