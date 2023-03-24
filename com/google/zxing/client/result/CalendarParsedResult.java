/*     */ package com.google.zxing.client.result;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Matcher;
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
/*     */ public final class CalendarParsedResult
/*     */   extends ParsedResult
/*     */ {
/*  36 */   private static final Pattern RFC2445_DURATION = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
/*  37 */   private static final long[] RFC2445_DURATION_FIELD_UNITS = new long[] { 604800000L, 86400000L, 3600000L, 60000L, 1000L };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final Pattern DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
/*     */   
/*     */   private final String summary;
/*     */   
/*     */   private final Date start;
/*     */   
/*     */   private final boolean startAllDay;
/*     */   
/*     */   private final Date end;
/*     */   
/*     */   private final boolean endAllDay;
/*     */   
/*     */   private final String location;
/*     */   
/*     */   private final String organizer;
/*     */   
/*     */   private final String[] attendees;
/*     */   
/*     */   private final String description;
/*     */   
/*     */   private final double latitude;
/*     */   private final double longitude;
/*     */   
/*     */   public CalendarParsedResult(String summary, String startString, String endString, String durationString, String location, String organizer, String[] attendees, String description, double latitude, double longitude) {
/*  69 */     super(ParsedResultType.CALENDAR);
/*  70 */     this.summary = summary;
/*     */     
/*     */     try {
/*  73 */       this.start = parseDate(startString);
/*  74 */     } catch (ParseException pe) {
/*  75 */       throw new IllegalArgumentException(pe.toString());
/*     */     } 
/*     */     
/*  78 */     if (endString == null) {
/*  79 */       long durationMS = parseDurationMS(durationString);
/*  80 */       this.end = (durationMS < 0L) ? null : new Date(this.start.getTime() + durationMS);
/*     */     } else {
/*     */       try {
/*  83 */         this.end = parseDate(endString);
/*  84 */       } catch (ParseException pe) {
/*  85 */         throw new IllegalArgumentException(pe.toString());
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     this.startAllDay = (startString.length() == 8);
/*  90 */     this.endAllDay = (endString != null && endString.length() == 8);
/*     */     
/*  92 */     this.location = location;
/*  93 */     this.organizer = organizer;
/*  94 */     this.attendees = attendees;
/*  95 */     this.description = description;
/*  96 */     this.latitude = latitude;
/*  97 */     this.longitude = longitude;
/*     */   }
/*     */   
/*     */   public String getSummary() {
/* 101 */     return this.summary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getStart() {
/* 108 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStartAllDay() {
/* 115 */     return this.startAllDay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getEnd() {
/* 123 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEndAllDay() {
/* 130 */     return this.endAllDay;
/*     */   }
/*     */   
/*     */   public String getLocation() {
/* 134 */     return this.location;
/*     */   }
/*     */   
/*     */   public String getOrganizer() {
/* 138 */     return this.organizer;
/*     */   }
/*     */   
/*     */   public String[] getAttendees() {
/* 142 */     return this.attendees;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 146 */     return this.description;
/*     */   }
/*     */   
/*     */   public double getLatitude() {
/* 150 */     return this.latitude;
/*     */   }
/*     */   
/*     */   public double getLongitude() {
/* 154 */     return this.longitude;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayResult() {
/* 159 */     StringBuilder result = new StringBuilder(100);
/* 160 */     maybeAppend(this.summary, result);
/* 161 */     maybeAppend(format(this.startAllDay, this.start), result);
/* 162 */     maybeAppend(format(this.endAllDay, this.end), result);
/* 163 */     maybeAppend(this.location, result);
/* 164 */     maybeAppend(this.organizer, result);
/* 165 */     maybeAppend(this.attendees, result);
/* 166 */     maybeAppend(this.description, result);
/* 167 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Date parseDate(String when) throws ParseException {
/*     */     Date date;
/* 178 */     if (!DATE_TIME.matcher(when).matches()) {
/* 179 */       throw new ParseException(when, 0);
/*     */     }
/* 181 */     if (when.length() == 8)
/*     */     {
/* 183 */       return buildDateFormat().parse(when);
/*     */     }
/*     */ 
/*     */     
/* 187 */     if (when.length() == 16 && when.charAt(15) == 'Z') {
/* 188 */       date = buildDateTimeFormat().parse(when.substring(0, 15));
/* 189 */       Calendar calendar = new GregorianCalendar();
/* 190 */       long milliseconds = date.getTime();
/*     */       
/* 192 */       milliseconds += calendar.get(15);
/*     */ 
/*     */       
/* 195 */       calendar.setTime(new Date(milliseconds));
/* 196 */       milliseconds += calendar.get(16);
/* 197 */       date = new Date(milliseconds);
/*     */     } else {
/* 199 */       date = buildDateTimeFormat().parse(when);
/*     */     } 
/* 201 */     return date;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String format(boolean allDay, Date date) {
/* 206 */     if (date == null) {
/* 207 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 211 */     DateFormat format = allDay ? DateFormat.getDateInstance(2) : DateFormat.getDateTimeInstance(2, 2);
/* 212 */     return format.format(date);
/*     */   }
/*     */   
/*     */   private static long parseDurationMS(CharSequence durationString) {
/* 216 */     if (durationString == null) {
/* 217 */       return -1L;
/*     */     }
/* 219 */     Matcher m = RFC2445_DURATION.matcher(durationString);
/* 220 */     if (!m.matches()) {
/* 221 */       return -1L;
/*     */     }
/* 223 */     long durationMS = 0L;
/* 224 */     for (int i = 0; i < RFC2445_DURATION_FIELD_UNITS.length; i++) {
/* 225 */       String fieldValue = m.group(i + 1);
/* 226 */       if (fieldValue != null) {
/* 227 */         durationMS += RFC2445_DURATION_FIELD_UNITS[i] * Integer.parseInt(fieldValue);
/*     */       }
/*     */     } 
/* 230 */     return durationMS;
/*     */   }
/*     */   
/*     */   private static DateFormat buildDateFormat() {
/* 234 */     DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
/*     */ 
/*     */ 
/*     */     
/* 238 */     format.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 239 */     return format;
/*     */   }
/*     */   
/*     */   private static DateFormat buildDateTimeFormat() {
/* 243 */     return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\CalendarParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */