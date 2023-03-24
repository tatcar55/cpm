/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ import com.google.zxing.Result;
/*     */ import java.util.List;
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
/*     */ public final class VEventResultParser
/*     */   extends ResultParser
/*     */ {
/*     */   public CalendarParsedResult parse(Result result) {
/*     */     double latitude, longitude;
/*  33 */     String rawText = getMassagedText(result);
/*  34 */     int vEventStart = rawText.indexOf("BEGIN:VEVENT");
/*  35 */     if (vEventStart < 0) {
/*  36 */       return null;
/*     */     }
/*     */     
/*  39 */     String summary = matchSingleVCardPrefixedField("SUMMARY", rawText, true);
/*  40 */     String start = matchSingleVCardPrefixedField("DTSTART", rawText, true);
/*  41 */     if (start == null) {
/*  42 */       return null;
/*     */     }
/*  44 */     String end = matchSingleVCardPrefixedField("DTEND", rawText, true);
/*  45 */     String duration = matchSingleVCardPrefixedField("DURATION", rawText, true);
/*  46 */     String location = matchSingleVCardPrefixedField("LOCATION", rawText, true);
/*  47 */     String organizer = stripMailto(matchSingleVCardPrefixedField("ORGANIZER", rawText, true));
/*     */     
/*  49 */     String[] attendees = matchVCardPrefixedField("ATTENDEE", rawText, true);
/*  50 */     if (attendees != null) {
/*  51 */       for (int i = 0; i < attendees.length; i++) {
/*  52 */         attendees[i] = stripMailto(attendees[i]);
/*     */       }
/*     */     }
/*  55 */     String description = matchSingleVCardPrefixedField("DESCRIPTION", rawText, true);
/*     */     
/*  57 */     String geoString = matchSingleVCardPrefixedField("GEO", rawText, true);
/*     */ 
/*     */     
/*  60 */     if (geoString == null) {
/*  61 */       latitude = Double.NaN;
/*  62 */       longitude = Double.NaN;
/*     */     } else {
/*  64 */       int semicolon = geoString.indexOf(';');
/*  65 */       if (semicolon < 0) {
/*  66 */         return null;
/*     */       }
/*     */       try {
/*  69 */         latitude = Double.parseDouble(geoString.substring(0, semicolon));
/*  70 */         longitude = Double.parseDouble(geoString.substring(semicolon + 1));
/*  71 */       } catch (NumberFormatException ignored) {
/*  72 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  77 */       return new CalendarParsedResult(summary, start, end, duration, location, organizer, attendees, description, latitude, longitude);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  87 */     catch (IllegalArgumentException ignored) {
/*  88 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String matchSingleVCardPrefixedField(CharSequence prefix, String rawText, boolean trim) {
/*  95 */     List<String> values = VCardResultParser.matchSingleVCardPrefixedField(prefix, rawText, trim, false);
/*  96 */     return (values == null || values.isEmpty()) ? null : values.get(0);
/*     */   }
/*     */   
/*     */   private static String[] matchVCardPrefixedField(CharSequence prefix, String rawText, boolean trim) {
/* 100 */     List<List<String>> values = VCardResultParser.matchVCardPrefixedField(prefix, rawText, trim, false);
/* 101 */     if (values == null || values.isEmpty()) {
/* 102 */       return null;
/*     */     }
/* 104 */     int size = values.size();
/* 105 */     String[] result = new String[size];
/* 106 */     for (int i = 0; i < size; i++) {
/* 107 */       result[i] = ((List<String>)values.get(i)).get(0);
/*     */     }
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   private static String stripMailto(String s) {
/* 113 */     if (s != null && (s.startsWith("mailto:") || s.startsWith("MAILTO:"))) {
/* 114 */       s = s.substring(7);
/*     */     }
/* 116 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\VEventResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */