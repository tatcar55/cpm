/*     */ package com.sun.xml.wss.util;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
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
/*     */ public class DateUtils
/*     */ {
/*     */   private static final String UTC_DATE_Z_FORMAT = "{0}-{1}-{2}T{3}:{4}:{5}Z";
/*     */   private static final String UTC_DATE_FORMAT = "{0}-{1}-{2}T{3}:{4}:{5}";
/*  57 */   private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String dateToString(Date date) {
/*  65 */     return dateToString(date, "{0}-{1}-{2}T{3}:{4}:{5}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toUTCDateFormat(Date date) {
/*  75 */     return dateToString(date, "{0}-{1}-{2}T{3}:{4}:{5}Z");
/*     */   }
/*     */ 
/*     */   
/*     */   private static String dateToString(Date date, String format) {
/*  80 */     GregorianCalendar cal = new GregorianCalendar(UTC_TIME_ZONE);
/*  81 */     cal.setTime(date);
/*  82 */     String[] params = new String[6];
/*     */     
/*  84 */     params[0] = formatInteger(cal.get(1), 4);
/*  85 */     params[1] = formatInteger(cal.get(2) + 1, 2);
/*  86 */     params[2] = formatInteger(cal.get(5), 2);
/*  87 */     params[3] = formatInteger(cal.get(11), 2);
/*  88 */     params[4] = formatInteger(cal.get(12), 2);
/*  89 */     params[5] = formatInteger(cal.get(13), 2);
/*  90 */     return MessageFormat.format(format, (Object[])params);
/*     */   }
/*     */   
/*     */   private static String formatInteger(int value, int length) {
/*  94 */     String val = Integer.toString(value);
/*  95 */     int diff = length - val.length();
/*     */     
/*  97 */     for (int i = 0; i < diff; i++) {
/*  98 */       val = "0" + val;
/*     */     }
/*     */     
/* 101 */     return val;
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
/*     */   public static Date stringToDate(String strDate) throws ParseException {
/* 148 */     int[] diffTime = null;
/* 149 */     boolean plusTime = true;
/*     */ 
/*     */     
/* 152 */     int idxT = strDate.indexOf('T');
/* 153 */     if (idxT == -1) {
/* 154 */       throw new ParseException("Invalid Date Format", 0);
/*     */     }
/*     */     
/* 157 */     int idxDiffUTC = strDate.indexOf('-', idxT);
/* 158 */     if (idxDiffUTC == -1) {
/* 159 */       idxDiffUTC = strDate.indexOf('+', idxT);
/* 160 */       plusTime = false;
/*     */     } 
/*     */     
/* 163 */     if (idxDiffUTC != -1) {
/* 164 */       diffTime = getDiffTime(strDate, idxDiffUTC);
/* 165 */       strDate = strDate.substring(0, idxDiffUTC);
/*     */     } 
/*     */     
/* 168 */     int idxMilliSec = strDate.indexOf('.');
/* 169 */     if (idxMilliSec != -1) {
/* 170 */       strDate = strDate.substring(0, idxMilliSec);
/*     */     } else {
/*     */       
/* 173 */       char lastChar = strDate.charAt(strDate.length() - 1);
/* 174 */       if (lastChar == 'z' || lastChar == 'Z') {
/* 175 */         strDate = strDate.substring(0, strDate.length() - 1);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return createDate(strDate, diffTime, plusTime);
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
/*     */   private static int[] getDiffTime(String strDate, int idx) throws ParseException {
/* 196 */     String strDiff = strDate.substring(idx + 1, strDate.length() - 1);
/* 197 */     int[] diffArray = new int[2];
/* 198 */     int colonIdx = strDiff.indexOf(':');
/*     */     
/* 200 */     if (colonIdx == -1) {
/* 201 */       throw new ParseException("Invalid Date Format", 0);
/*     */     }
/*     */     
/*     */     try {
/* 205 */       diffArray[0] = Integer.parseInt(strDiff.substring(0, colonIdx));
/* 206 */       diffArray[1] = Integer.parseInt(strDiff.substring(colonIdx + 1));
/* 207 */     } catch (NumberFormatException nfe) {
/* 208 */       throw new ParseException("Invalid Date Format", 0);
/*     */     } 
/*     */     
/* 211 */     return diffArray;
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
/*     */   private static Date createDate(String strDate, int[] timeDiff, boolean plusDiff) throws ParseException {
/*     */     try {
/* 231 */       int year = Integer.parseInt(strDate.substring(0, 4));
/* 232 */       if (strDate.charAt(4) != '-') {
/* 233 */         throw new ParseException("Invalid Date Format", 0);
/*     */       }
/*     */       
/* 236 */       int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
/* 237 */       if (strDate.charAt(7) != '-') {
/* 238 */         throw new ParseException("Invalid Date Format", 0);
/*     */       }
/*     */       
/* 241 */       int day = Integer.parseInt(strDate.substring(8, 10));
/* 242 */       if (strDate.charAt(10) != 'T') {
/* 243 */         throw new ParseException("Invalid Date Format", 0);
/*     */       }
/*     */       
/* 246 */       int hour = Integer.parseInt(strDate.substring(11, 13));
/* 247 */       if (strDate.charAt(13) != ':') {
/* 248 */         throw new ParseException("Invalid Date Format", 0);
/*     */       }
/*     */       
/* 251 */       int minute = Integer.parseInt(strDate.substring(14, 16));
/* 252 */       int second = 0;
/*     */       
/* 254 */       if (strDate.length() > 17) {
/* 255 */         if (strDate.charAt(16) != ':') {
/* 256 */           throw new ParseException("Invalid Date Format", 0);
/*     */         }
/*     */         
/* 259 */         second = Integer.parseInt(strDate.substring(17, 19));
/*     */       } 
/*     */       
/* 262 */       GregorianCalendar cal = new GregorianCalendar(year, month, day, hour, minute, second);
/*     */       
/* 264 */       cal.setTimeZone(UTC_TIME_ZONE);
/*     */       
/* 266 */       if (timeDiff != null) {
/* 267 */         int hourDiff = plusDiff ? timeDiff[0] : (-1 * timeDiff[0]);
/* 268 */         int minuteDiff = plusDiff ? timeDiff[1] : (-1 * timeDiff[1]);
/* 269 */         cal.add(10, hourDiff);
/* 270 */         cal.add(12, minuteDiff);
/*     */       } 
/*     */       
/* 273 */       return cal.getTime();
/* 274 */     } catch (NumberFormatException nfe) {
/* 275 */       throw new ParseException("Invalid Date Format", 0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\\util\DateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */