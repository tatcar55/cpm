/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
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
/*     */ public class XSDDateEncoder
/*     */   extends SimpleTypeEncoderBase
/*     */ {
/*  45 */   private static final SimpleTypeEncoder encoder = new XSDDateEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance() {
/*  51 */     return encoder;
/*     */   }
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*  56 */     if (obj == null) {
/*  57 */       return null;
/*     */     }
/*  59 */     Calendar cal = (Calendar)obj;
/*  60 */     boolean isBC = (cal.get(0) == 0);
/*  61 */     StringBuffer buf = new StringBuffer();
/*  62 */     if (isBC) {
/*  63 */       cal.set(0, 1);
/*  64 */       buf.append("-");
/*     */     } 
/*  66 */     synchronized (dateFormatter) {
/*  67 */       buf.append(dateFormatter.format(((Calendar)obj).getTime()));
/*     */     } 
/*  69 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/*  74 */     if (str == null) {
/*  75 */       return null;
/*     */     }
/*  77 */     Calendar cal = Calendar.getInstance(gmtTimeZone);
/*  78 */     boolean isNeg = false;
/*     */     
/*  80 */     str = EncoderUtils.collapseWhitespace(str);
/*  81 */     if (str.charAt(0) == '+') {
/*  82 */       str = str.substring(1);
/*     */     }
/*  84 */     if (str.charAt(0) == '-') {
/*  85 */       str = str.substring(1);
/*  86 */       isNeg = true;
/*     */     } 
/*  88 */     validateDateStr(str);
/*  89 */     synchronized (dateFormatter) {
/*  90 */       cal.setTime(dateFormatter.parse(str));
/*     */     } 
/*  92 */     if (isNeg) {
/*  93 */       cal.set(0, 0);
/*     */     }
/*  95 */     return cal;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateDateStr(String dateStr) throws Exception {
/* 100 */     if (dateStr.length() < 10)
/* 101 */       throw new DeserializationException("xsd.invalid.date", dateStr); 
/*     */   }
/*     */   
/* 104 */   protected static final Locale locale = new Locale("en_US");
/* 105 */   protected static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", locale);
/*     */   
/* 107 */   protected static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
/*     */   static {
/* 109 */     dateFormatter.setTimeZone(gmtTimeZone);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDDateEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */