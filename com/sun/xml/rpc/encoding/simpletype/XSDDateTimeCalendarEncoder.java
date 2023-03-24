/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.SimpleTimeZone;
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
/*     */ public class XSDDateTimeCalendarEncoder
/*     */   extends XSDDateTimeDateEncoder
/*     */ {
/*  46 */   private static final SimpleTypeEncoder encoder = new XSDDateTimeCalendarEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance() {
/*  53 */     return encoder;
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
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*     */     String zone;
/*  73 */     if (obj == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     Calendar c = (Calendar)obj;
/*     */ 
/*     */     
/*  79 */     StringBuffer resultBuf = new StringBuffer();
/*  80 */     if (c.get(0) == 0) {
/*  81 */       resultBuf.append('-');
/*     */     }
/*  83 */     synchronized (calendarFormatter) {
/*  84 */       zoneFormatter.setTimeZone(c.getTimeZone());
/*  85 */       zone = zoneFormatter.format(c.getTime());
/*  86 */       calendarFormatter.setTimeZone(c.getTimeZone());
/*  87 */       resultBuf.append(calendarFormatter.format(c.getTime()));
/*     */     } 
/*  89 */     String offsetStr = zone.substring(0, 3) + ":" + zone.substring(3, 5);
/*  90 */     resultBuf.append(offsetStr);
/*  91 */     return resultBuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/*  97 */     if (str == null) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     boolean isNeg = (str.charAt(0) == '-');
/*     */     
/* 103 */     StringBuffer zone = new StringBuffer(10);
/* 104 */     Date date = decodeDateUtil(str, zone);
/* 105 */     String zoneStr = zone.toString();
/* 106 */     Calendar cal = Calendar.getInstance(gmtTimeZone);
/* 107 */     cal.setTime(date);
/*     */     
/* 109 */     if (zoneStr.length() != 0) {
/* 110 */       TimeZone tz = TimeZone.getTimeZone("GMT" + zoneStr);
/*     */       
/* 112 */       if (isNeg) {
/* 113 */         cal.set(0, 0);
/* 114 */         cal.setTime(date);
/*     */       } 
/* 116 */       cal.setTimeZone(tz);
/*     */     
/*     */     }
/* 119 */     else if (isNeg) {
/* 120 */       cal.set(0, 0);
/*     */     } else {
/* 122 */       cal.set(0, 1);
/*     */     } 
/*     */     
/* 125 */     return cal;
/*     */   }
/*     */   
/*     */   private int getDSTSavings(TimeZone tz) {
/* 129 */     if (VersionUtil.isJavaVersionGreaterThan1_3())
/*     */     {
/* 131 */       return tz.getDSTSavings();
/*     */     }
/*     */     
/* 134 */     return ((SimpleTimeZone)tz).getDSTSavings();
/*     */   }
/*     */   
/* 137 */   private static final SimpleDateFormat zoneFormatter = new SimpleDateFormat("Z", locale);
/*     */   
/* 139 */   private static final SimpleDateFormat calendarFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", locale);
/*     */   
/* 141 */   private static final SimpleDateFormat calendarFormatterBC = new SimpleDateFormat("'-'yyyy-MM-dd'T'HH:mm:ss.SSS", locale);
/*     */ 
/*     */   
/*     */   static {
/* 145 */     calendarFormatter.setTimeZone(gmtTimeZone);
/* 146 */     calendarFormatterBC.setTimeZone(gmtTimeZone);
/*     */   }
/*     */   
/*     */   public void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDDateTimeCalendarEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */