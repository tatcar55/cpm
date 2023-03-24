/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Calendar;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DatatypeConverter
/*     */ {
/* 112 */   private static DatatypeConverterInterface theConverter = null;
/*     */   
/* 114 */   private static final JAXBPermission SET_DATATYPE_CONVERTER_PERMISSION = new JAXBPermission("setDatatypeConverter");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDatatypeConverter(DatatypeConverterInterface converter) {
/* 143 */     if (converter == null) {
/* 144 */       throw new IllegalArgumentException(Messages.format("DatatypeConverter.ConverterMustNotBeNull"));
/*     */     }
/* 146 */     if (theConverter == null) {
/* 147 */       SecurityManager sm = System.getSecurityManager();
/* 148 */       if (sm != null)
/* 149 */         sm.checkPermission(SET_DATATYPE_CONVERTER_PERMISSION); 
/* 150 */       theConverter = converter;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static synchronized void initConverter() {
/* 155 */     theConverter = new DatatypeConverterImpl();
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
/*     */   public static String parseString(String lexicalXSDString) {
/* 168 */     if (theConverter == null) initConverter(); 
/* 169 */     return theConverter.parseString(lexicalXSDString);
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
/*     */   public static BigInteger parseInteger(String lexicalXSDInteger) {
/* 183 */     if (theConverter == null) initConverter(); 
/* 184 */     return theConverter.parseInteger(lexicalXSDInteger);
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
/*     */   public static int parseInt(String lexicalXSDInt) {
/* 198 */     if (theConverter == null) initConverter(); 
/* 199 */     return theConverter.parseInt(lexicalXSDInt);
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
/*     */   public static long parseLong(String lexicalXSDLong) {
/* 213 */     if (theConverter == null) initConverter(); 
/* 214 */     return theConverter.parseLong(lexicalXSDLong);
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
/*     */   public static short parseShort(String lexicalXSDShort) {
/* 228 */     if (theConverter == null) initConverter(); 
/* 229 */     return theConverter.parseShort(lexicalXSDShort);
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
/*     */   public static BigDecimal parseDecimal(String lexicalXSDDecimal) {
/* 243 */     if (theConverter == null) initConverter(); 
/* 244 */     return theConverter.parseDecimal(lexicalXSDDecimal);
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
/*     */   public static float parseFloat(String lexicalXSDFloat) {
/* 258 */     if (theConverter == null) initConverter(); 
/* 259 */     return theConverter.parseFloat(lexicalXSDFloat);
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
/*     */   public static double parseDouble(String lexicalXSDDouble) {
/* 273 */     if (theConverter == null) initConverter(); 
/* 274 */     return theConverter.parseDouble(lexicalXSDDouble);
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
/*     */   public static boolean parseBoolean(String lexicalXSDBoolean) {
/* 288 */     if (theConverter == null) initConverter(); 
/* 289 */     return theConverter.parseBoolean(lexicalXSDBoolean);
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
/*     */   public static byte parseByte(String lexicalXSDByte) {
/* 303 */     if (theConverter == null) initConverter(); 
/* 304 */     return theConverter.parseByte(lexicalXSDByte);
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
/*     */   public static QName parseQName(String lexicalXSDQName, NamespaceContext nsc) {
/* 326 */     if (theConverter == null) initConverter(); 
/* 327 */     return theConverter.parseQName(lexicalXSDQName, nsc);
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
/*     */   public static Calendar parseDateTime(String lexicalXSDDateTime) {
/* 341 */     if (theConverter == null) initConverter(); 
/* 342 */     return theConverter.parseDateTime(lexicalXSDDateTime);
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
/*     */   public static byte[] parseBase64Binary(String lexicalXSDBase64Binary) {
/* 356 */     if (theConverter == null) initConverter(); 
/* 357 */     return theConverter.parseBase64Binary(lexicalXSDBase64Binary);
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
/*     */   public static byte[] parseHexBinary(String lexicalXSDHexBinary) {
/* 371 */     if (theConverter == null) initConverter(); 
/* 372 */     return theConverter.parseHexBinary(lexicalXSDHexBinary);
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
/*     */   public static long parseUnsignedInt(String lexicalXSDUnsignedInt) {
/* 386 */     if (theConverter == null) initConverter(); 
/* 387 */     return theConverter.parseUnsignedInt(lexicalXSDUnsignedInt);
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
/*     */   public static int parseUnsignedShort(String lexicalXSDUnsignedShort) {
/* 401 */     if (theConverter == null) initConverter(); 
/* 402 */     return theConverter.parseUnsignedShort(lexicalXSDUnsignedShort);
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
/*     */   public static Calendar parseTime(String lexicalXSDTime) {
/* 416 */     if (theConverter == null) initConverter(); 
/* 417 */     return theConverter.parseTime(lexicalXSDTime);
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
/*     */   public static Calendar parseDate(String lexicalXSDDate) {
/* 430 */     if (theConverter == null) initConverter(); 
/* 431 */     return theConverter.parseDate(lexicalXSDDate);
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
/*     */   public static String parseAnySimpleType(String lexicalXSDAnySimpleType) {
/* 446 */     if (theConverter == null) initConverter(); 
/* 447 */     return theConverter.parseAnySimpleType(lexicalXSDAnySimpleType);
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
/*     */   public static String printString(String val) {
/* 461 */     if (theConverter == null) initConverter(); 
/* 462 */     return theConverter.printString(val);
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
/*     */   public static String printInteger(BigInteger val) {
/* 475 */     if (theConverter == null) initConverter(); 
/* 476 */     return theConverter.printInteger(val);
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
/*     */   public static String printInt(int val) {
/* 488 */     if (theConverter == null) initConverter(); 
/* 489 */     return theConverter.printInt(val);
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
/*     */   public static String printLong(long val) {
/* 501 */     if (theConverter == null) initConverter(); 
/* 502 */     return theConverter.printLong(val);
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
/*     */   public static String printShort(short val) {
/* 514 */     if (theConverter == null) initConverter(); 
/* 515 */     return theConverter.printShort(val);
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
/*     */   public static String printDecimal(BigDecimal val) {
/* 528 */     if (theConverter == null) initConverter(); 
/* 529 */     return theConverter.printDecimal(val);
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
/*     */   public static String printFloat(float val) {
/* 541 */     if (theConverter == null) initConverter(); 
/* 542 */     return theConverter.printFloat(val);
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
/*     */   public static String printDouble(double val) {
/* 554 */     if (theConverter == null) initConverter(); 
/* 555 */     return theConverter.printDouble(val);
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
/*     */   public static String printBoolean(boolean val) {
/* 567 */     if (theConverter == null) initConverter(); 
/* 568 */     return theConverter.printBoolean(val);
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
/*     */   public static String printByte(byte val) {
/* 580 */     if (theConverter == null) initConverter(); 
/* 581 */     return theConverter.printByte(val);
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
/*     */   public static String printQName(QName val, NamespaceContext nsc) {
/* 598 */     if (theConverter == null) initConverter(); 
/* 599 */     return theConverter.printQName(val, nsc);
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
/*     */   public static String printDateTime(Calendar val) {
/* 612 */     if (theConverter == null) initConverter(); 
/* 613 */     return theConverter.printDateTime(val);
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
/*     */   public static String printBase64Binary(byte[] val) {
/* 626 */     if (theConverter == null) initConverter(); 
/* 627 */     return theConverter.printBase64Binary(val);
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
/*     */   public static String printHexBinary(byte[] val) {
/* 640 */     if (theConverter == null) initConverter(); 
/* 641 */     return theConverter.printHexBinary(val);
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
/*     */   public static String printUnsignedInt(long val) {
/* 653 */     if (theConverter == null) initConverter(); 
/* 654 */     return theConverter.printUnsignedInt(val);
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
/*     */   public static String printUnsignedShort(int val) {
/* 666 */     if (theConverter == null) initConverter(); 
/* 667 */     return theConverter.printUnsignedShort(val);
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
/*     */   public static String printTime(Calendar val) {
/* 680 */     if (theConverter == null) initConverter(); 
/* 681 */     return theConverter.printTime(val);
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
/*     */   public static String printDate(Calendar val) {
/* 694 */     if (theConverter == null) initConverter(); 
/* 695 */     return theConverter.printDate(val);
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
/*     */   public static String printAnySimpleType(String val) {
/* 707 */     if (theConverter == null) initConverter(); 
/* 708 */     return theConverter.printAnySimpleType(val);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\DatatypeConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */