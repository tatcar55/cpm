/*     */ package com.sun.xml.rpc.client.http;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RfcDateParser
/*     */ {
/*     */   private static final String debugProp = "hotjava.debug.RfcDateParser";
/*     */   private boolean isGMT = false;
/*  46 */   static final String[] standardFormats = new String[] { "EEEE', 'dd-MMM-yy HH:mm:ss z", "EEEE', 'dd-MMM-yy HH:mm:ss", "EEE', 'dd-MMM-yyyy HH:mm:ss z", "EEE', 'dd MMM yyyy HH:mm:ss z", "EEEE', 'dd MMM yyyy HH:mm:ss z", "EEE', 'dd MMM yyyy hh:mm:ss z", "EEEE', 'dd MMM yyyy hh:mm:ss z", "EEE MMM dd HH:mm:ss z yyyy", "EEE MMM dd HH:mm:ss yyyy", "EEE', 'dd-MMM-yy HH:mm:ss", "EEE', 'dd-MMM-yyyy HH:mm:ss" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   static final String[] gmtStandardFormats = new String[] { "EEEE',' dd-MMM-yy HH:mm:ss 'GMT'", "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'", "EEE',' dd MMM yyyy HH:mm:ss 'GMT'", "EEEE',' dd MMM yyyy HH:mm:ss 'GMT'", "EEE',' dd MMM yyyy hh:mm:ss 'GMT'", "EEEE',' dd MMM yyyy hh:mm:ss 'GMT'", "EEE MMM dd HH:mm:ss 'GMT' yyyy" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String dateString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RfcDateParser(String dateString) {
/*  72 */     this.dateString = dateString.trim();
/*     */     
/*  74 */     if (this.dateString.indexOf("GMT") != -1) {
/*  75 */       this.isGMT = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() {
/*  82 */     int arrayLen = this.isGMT ? gmtStandardFormats.length : standardFormats.length;
/*     */ 
/*     */     
/*  85 */     for (int i = 0; i < arrayLen; i++) {
/*     */       Date d;
/*     */       
/*  88 */       if (this.isGMT) {
/*  89 */         d = tryParsing(gmtStandardFormats[i]);
/*     */       } else {
/*  91 */         d = tryParsing(standardFormats[i]);
/*     */       } 
/*     */       
/*  94 */       if (d != null) {
/*  95 */         return d;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Date tryParsing(String format) {
/* 104 */     SimpleDateFormat df = new SimpleDateFormat(format, Locale.US);
/*     */     
/* 106 */     if (this.isGMT) {
/* 107 */       df.setTimeZone(TimeZone.getTimeZone("GMT"));
/*     */     }
/*     */     
/*     */     try {
/* 111 */       return df.parse(this.dateString);
/* 112 */     } catch (Exception e) {
/* 113 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\http\RfcDateParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */