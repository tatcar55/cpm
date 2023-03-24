/*      */ package javax.xml.bind;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.TimeZone;
/*      */ import javax.xml.datatype.DatatypeConfigurationException;
/*      */ import javax.xml.datatype.DatatypeFactory;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class DatatypeConverterImpl
/*      */   implements DatatypeConverterInterface
/*      */ {
/*   73 */   public static final DatatypeConverterInterface theInstance = new DatatypeConverterImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String parseString(String lexicalXSDString) {
/*   79 */     return lexicalXSDString;
/*      */   }
/*      */   
/*      */   public BigInteger parseInteger(String lexicalXSDInteger) {
/*   83 */     return _parseInteger(lexicalXSDInteger);
/*      */   }
/*      */   
/*      */   public static BigInteger _parseInteger(CharSequence s) {
/*   87 */     return new BigInteger(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString());
/*      */   }
/*      */   
/*      */   public String printInteger(BigInteger val) {
/*   91 */     return _printInteger(val);
/*      */   }
/*      */   
/*      */   public static String _printInteger(BigInteger val) {
/*   95 */     return val.toString();
/*      */   }
/*      */   
/*      */   public int parseInt(String s) {
/*   99 */     return _parseInt(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int _parseInt(CharSequence s) {
/*  113 */     int len = s.length();
/*  114 */     int sign = 1;
/*      */     
/*  116 */     int r = 0;
/*      */     
/*  118 */     for (int i = 0; i < len; i++) {
/*  119 */       char ch = s.charAt(i);
/*  120 */       if (!WhiteSpaceProcessor.isWhiteSpace(ch))
/*      */       {
/*  122 */         if ('0' <= ch && ch <= '9') {
/*  123 */           r = r * 10 + ch - 48;
/*  124 */         } else if (ch == '-') {
/*  125 */           sign = -1;
/*  126 */         } else if (ch != '+') {
/*      */ 
/*      */           
/*  129 */           throw new NumberFormatException("Not a number: " + s);
/*      */         } 
/*      */       }
/*      */     } 
/*  133 */     return r * sign;
/*      */   }
/*      */   
/*      */   public long parseLong(String lexicalXSLong) {
/*  137 */     return _parseLong(lexicalXSLong);
/*      */   }
/*      */   
/*      */   public static long _parseLong(CharSequence s) {
/*  141 */     return Long.valueOf(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString()).longValue();
/*      */   }
/*      */   
/*      */   public short parseShort(String lexicalXSDShort) {
/*  145 */     return _parseShort(lexicalXSDShort);
/*      */   }
/*      */   
/*      */   public static short _parseShort(CharSequence s) {
/*  149 */     return (short)_parseInt(s);
/*      */   }
/*      */   
/*      */   public String printShort(short val) {
/*  153 */     return _printShort(val);
/*      */   }
/*      */   
/*      */   public static String _printShort(short val) {
/*  157 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public BigDecimal parseDecimal(String content) {
/*  161 */     return _parseDecimal(content);
/*      */   }
/*      */   
/*      */   public static BigDecimal _parseDecimal(CharSequence content) {
/*  165 */     content = WhiteSpaceProcessor.trim(content);
/*      */     
/*  167 */     if (content.length() <= 0) {
/*  168 */       return null;
/*      */     }
/*      */     
/*  171 */     return new BigDecimal(content.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float parseFloat(String lexicalXSDFloat) {
/*  187 */     return _parseFloat(lexicalXSDFloat);
/*      */   }
/*      */   
/*      */   public static float _parseFloat(CharSequence _val) {
/*  191 */     String s = WhiteSpaceProcessor.trim(_val).toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  207 */     if (s.equals("NaN")) {
/*  208 */       return Float.NaN;
/*      */     }
/*  210 */     if (s.equals("INF")) {
/*  211 */       return Float.POSITIVE_INFINITY;
/*      */     }
/*  213 */     if (s.equals("-INF")) {
/*  214 */       return Float.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  217 */     if (s.length() == 0 || !isDigitOrPeriodOrSign(s.charAt(0)) || !isDigitOrPeriodOrSign(s.charAt(s.length() - 1)))
/*      */     {
/*      */       
/*  220 */       throw new NumberFormatException();
/*      */     }
/*      */ 
/*      */     
/*  224 */     return Float.parseFloat(s);
/*      */   }
/*      */   
/*      */   public String printFloat(float v) {
/*  228 */     return _printFloat(v);
/*      */   }
/*      */   
/*      */   public static String _printFloat(float v) {
/*  232 */     if (Float.isNaN(v)) {
/*  233 */       return "NaN";
/*      */     }
/*  235 */     if (v == Float.POSITIVE_INFINITY) {
/*  236 */       return "INF";
/*      */     }
/*  238 */     if (v == Float.NEGATIVE_INFINITY) {
/*  239 */       return "-INF";
/*      */     }
/*  241 */     return String.valueOf(v);
/*      */   }
/*      */   
/*      */   public double parseDouble(String lexicalXSDDouble) {
/*  245 */     return _parseDouble(lexicalXSDDouble);
/*      */   }
/*      */   
/*      */   public static double _parseDouble(CharSequence _val) {
/*  249 */     String val = WhiteSpaceProcessor.trim(_val).toString();
/*      */     
/*  251 */     if (val.equals("NaN")) {
/*  252 */       return Double.NaN;
/*      */     }
/*  254 */     if (val.equals("INF")) {
/*  255 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*  257 */     if (val.equals("-INF")) {
/*  258 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  261 */     if (val.length() == 0 || !isDigitOrPeriodOrSign(val.charAt(0)) || !isDigitOrPeriodOrSign(val.charAt(val.length() - 1)))
/*      */     {
/*      */       
/*  264 */       throw new NumberFormatException(val);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  269 */     return Double.parseDouble(val);
/*      */   }
/*      */   
/*      */   public boolean parseBoolean(String lexicalXSDBoolean) {
/*  273 */     Boolean b = _parseBoolean(lexicalXSDBoolean);
/*  274 */     return (b == null) ? false : b.booleanValue();
/*      */   } public static Boolean _parseBoolean(CharSequence literal) {
/*      */     char ch;
/*      */     String strTrue, strFalse;
/*  278 */     if (literal == null) {
/*  279 */       return null;
/*      */     }
/*      */     
/*  282 */     int i = 0;
/*  283 */     int len = literal.length();
/*      */     
/*  285 */     boolean value = false;
/*      */     
/*  287 */     if (literal.length() <= 0) {
/*  288 */       return null;
/*      */     }
/*      */     
/*      */     do {
/*  292 */       ch = literal.charAt(i++);
/*  293 */     } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     
/*  295 */     int strIndex = 0;
/*      */     
/*  297 */     switch (ch) {
/*      */       case '1':
/*  299 */         value = true;
/*      */         break;
/*      */       case '0':
/*  302 */         value = false;
/*      */         break;
/*      */       case 't':
/*  305 */         strTrue = "rue";
/*      */         do {
/*  307 */           ch = literal.charAt(i++);
/*  308 */         } while (strTrue.charAt(strIndex++) == ch && i < len && strIndex < 3);
/*      */         
/*  310 */         if (strIndex == 3) {
/*  311 */           value = true; break;
/*      */         } 
/*  313 */         return Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 'f':
/*  319 */         strFalse = "alse";
/*      */         do {
/*  321 */           ch = literal.charAt(i++);
/*  322 */         } while (strFalse.charAt(strIndex++) == ch && i < len && strIndex < 4);
/*      */ 
/*      */         
/*  325 */         if (strIndex == 4) {
/*  326 */           value = false; break;
/*      */         } 
/*  328 */         return Boolean.valueOf(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  335 */     if (i < len) {
/*      */       do {
/*  337 */         ch = literal.charAt(i++);
/*  338 */       } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     }
/*      */     
/*  341 */     if (i == len) {
/*  342 */       return Boolean.valueOf(value);
/*      */     }
/*  344 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String printBoolean(boolean val) {
/*  350 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   public static String _printBoolean(boolean val) {
/*  354 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   public byte parseByte(String lexicalXSDByte) {
/*  358 */     return _parseByte(lexicalXSDByte);
/*      */   }
/*      */   
/*      */   public static byte _parseByte(CharSequence literal) {
/*  362 */     return (byte)_parseInt(literal);
/*      */   }
/*      */   
/*      */   public String printByte(byte val) {
/*  366 */     return _printByte(val);
/*      */   }
/*      */   
/*      */   public static String _printByte(byte val) {
/*  370 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public QName parseQName(String lexicalXSDQName, NamespaceContext nsc) {
/*  374 */     return _parseQName(lexicalXSDQName, nsc);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName _parseQName(CharSequence text, NamespaceContext nsc) {
/*      */     String uri, localPart, prefix;
/*  381 */     int length = text.length();
/*      */ 
/*      */     
/*  384 */     int start = 0;
/*  385 */     while (start < length && WhiteSpaceProcessor.isWhiteSpace(text.charAt(start))) {
/*  386 */       start++;
/*      */     }
/*      */     
/*  389 */     int end = length;
/*  390 */     while (end > start && WhiteSpaceProcessor.isWhiteSpace(text.charAt(end - 1))) {
/*  391 */       end--;
/*      */     }
/*      */     
/*  394 */     if (end == start) {
/*  395 */       throw new IllegalArgumentException("input is empty");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  404 */     int idx = start + 1;
/*  405 */     while (idx < end && text.charAt(idx) != ':') {
/*  406 */       idx++;
/*      */     }
/*      */     
/*  409 */     if (idx == end) {
/*  410 */       uri = nsc.getNamespaceURI("");
/*  411 */       localPart = text.subSequence(start, end).toString();
/*  412 */       prefix = "";
/*      */     } else {
/*      */       
/*  415 */       prefix = text.subSequence(start, idx).toString();
/*  416 */       localPart = text.subSequence(idx + 1, end).toString();
/*  417 */       uri = nsc.getNamespaceURI(prefix);
/*      */ 
/*      */       
/*  420 */       if (uri == null || uri.length() == 0)
/*      */       {
/*      */         
/*  423 */         throw new IllegalArgumentException("prefix " + prefix + " is not bound to a namespace");
/*      */       }
/*      */     } 
/*      */     
/*  427 */     return new QName(uri, localPart, prefix);
/*      */   }
/*      */   
/*      */   public Calendar parseDateTime(String lexicalXSDDateTime) {
/*  431 */     return _parseDateTime(lexicalXSDDateTime);
/*      */   }
/*      */   
/*      */   public static GregorianCalendar _parseDateTime(CharSequence s) {
/*  435 */     String val = WhiteSpaceProcessor.trim(s).toString();
/*  436 */     return datatypeFactory.newXMLGregorianCalendar(val).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public String printDateTime(Calendar val) {
/*  440 */     return _printDateTime(val);
/*      */   }
/*      */   
/*      */   public static String _printDateTime(Calendar val) {
/*  444 */     return CalendarFormatter.doFormat("%Y-%M-%DT%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   public byte[] parseBase64Binary(String lexicalXSDBase64Binary) {
/*  448 */     return _parseBase64Binary(lexicalXSDBase64Binary);
/*      */   }
/*      */   
/*      */   public byte[] parseHexBinary(String s) {
/*  452 */     int len = s.length();
/*      */ 
/*      */     
/*  455 */     if (len % 2 != 0) {
/*  456 */       throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
/*      */     }
/*      */     
/*  459 */     byte[] out = new byte[len / 2];
/*      */     
/*  461 */     for (int i = 0; i < len; i += 2) {
/*  462 */       int h = hexToBin(s.charAt(i));
/*  463 */       int l = hexToBin(s.charAt(i + 1));
/*  464 */       if (h == -1 || l == -1) {
/*  465 */         throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
/*      */       }
/*      */       
/*  468 */       out[i / 2] = (byte)(h * 16 + l);
/*      */     } 
/*      */     
/*  471 */     return out;
/*      */   }
/*      */   
/*      */   private static int hexToBin(char ch) {
/*  475 */     if ('0' <= ch && ch <= '9') {
/*  476 */       return ch - 48;
/*      */     }
/*  478 */     if ('A' <= ch && ch <= 'F') {
/*  479 */       return ch - 65 + 10;
/*      */     }
/*  481 */     if ('a' <= ch && ch <= 'f') {
/*  482 */       return ch - 97 + 10;
/*      */     }
/*  484 */     return -1;
/*      */   }
/*  486 */   private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
/*      */   
/*      */   public String printHexBinary(byte[] data) {
/*  489 */     StringBuilder r = new StringBuilder(data.length * 2);
/*  490 */     for (byte b : data) {
/*  491 */       r.append(hexCode[b >> 4 & 0xF]);
/*  492 */       r.append(hexCode[b & 0xF]);
/*      */     } 
/*  494 */     return r.toString();
/*      */   }
/*      */   
/*      */   public long parseUnsignedInt(String lexicalXSDUnsignedInt) {
/*  498 */     return _parseLong(lexicalXSDUnsignedInt);
/*      */   }
/*      */   
/*      */   public String printUnsignedInt(long val) {
/*  502 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   public int parseUnsignedShort(String lexicalXSDUnsignedShort) {
/*  506 */     return _parseInt(lexicalXSDUnsignedShort);
/*      */   }
/*      */   
/*      */   public Calendar parseTime(String lexicalXSDTime) {
/*  510 */     return datatypeFactory.newXMLGregorianCalendar(lexicalXSDTime).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public String printTime(Calendar val) {
/*  514 */     return CalendarFormatter.doFormat("%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   public Calendar parseDate(String lexicalXSDDate) {
/*  518 */     return datatypeFactory.newXMLGregorianCalendar(lexicalXSDDate).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public String printDate(Calendar val) {
/*  522 */     return _printDate(val);
/*      */   }
/*      */   
/*      */   public static String _printDate(Calendar val) {
/*  526 */     return CalendarFormatter.doFormat("%Y-%M-%D" + "%z", val);
/*      */   }
/*      */   
/*      */   public String parseAnySimpleType(String lexicalXSDAnySimpleType) {
/*  530 */     return lexicalXSDAnySimpleType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String printString(String val) {
/*  536 */     return val;
/*      */   }
/*      */   
/*      */   public String printInt(int val) {
/*  540 */     return _printInt(val);
/*      */   }
/*      */   
/*      */   public static String _printInt(int val) {
/*  544 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public String printLong(long val) {
/*  548 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   public static String _printLong(long val) {
/*  552 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public String printDecimal(BigDecimal val) {
/*  556 */     return _printDecimal(val);
/*      */   }
/*      */   
/*      */   public static String _printDecimal(BigDecimal val) {
/*  560 */     return val.toPlainString();
/*      */   }
/*      */   
/*      */   public String printDouble(double v) {
/*  564 */     return _printDouble(v);
/*      */   }
/*      */   
/*      */   public static String _printDouble(double v) {
/*  568 */     if (Double.isNaN(v)) {
/*  569 */       return "NaN";
/*      */     }
/*  571 */     if (v == Double.POSITIVE_INFINITY) {
/*  572 */       return "INF";
/*      */     }
/*  574 */     if (v == Double.NEGATIVE_INFINITY) {
/*  575 */       return "-INF";
/*      */     }
/*  577 */     return String.valueOf(v);
/*      */   }
/*      */   
/*      */   public String printQName(QName val, NamespaceContext nsc) {
/*  581 */     return _printQName(val, nsc);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String _printQName(QName val, NamespaceContext nsc) {
/*  587 */     String qname, prefix = nsc.getPrefix(val.getNamespaceURI());
/*  588 */     String localPart = val.getLocalPart();
/*      */     
/*  590 */     if (prefix == null || prefix.length() == 0) {
/*  591 */       qname = localPart;
/*      */     } else {
/*  593 */       qname = prefix + ':' + localPart;
/*      */     } 
/*      */     
/*  596 */     return qname;
/*      */   }
/*      */   
/*      */   public String printBase64Binary(byte[] val) {
/*  600 */     return _printBase64Binary(val);
/*      */   }
/*      */   
/*      */   public String printUnsignedShort(int val) {
/*  604 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public String printAnySimpleType(String val) {
/*  608 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String installHook(String s) {
/*  617 */     DatatypeConverter.setDatatypeConverter(theInstance);
/*  618 */     return s;
/*      */   }
/*      */   
/*  621 */   private static final byte[] decodeMap = initDecodeMap();
/*      */   private static final byte PADDING = 127;
/*      */   
/*      */   private static byte[] initDecodeMap() {
/*  625 */     byte[] map = new byte[128];
/*      */     int i;
/*  627 */     for (i = 0; i < 128; i++) {
/*  628 */       map[i] = -1;
/*      */     }
/*      */     
/*  631 */     for (i = 65; i <= 90; i++) {
/*  632 */       map[i] = (byte)(i - 65);
/*      */     }
/*  634 */     for (i = 97; i <= 122; i++) {
/*  635 */       map[i] = (byte)(i - 97 + 26);
/*      */     }
/*  637 */     for (i = 48; i <= 57; i++) {
/*  638 */       map[i] = (byte)(i - 48 + 52);
/*      */     }
/*  640 */     map[43] = 62;
/*  641 */     map[47] = 63;
/*  642 */     map[61] = Byte.MAX_VALUE;
/*      */     
/*  644 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int guessLength(String text) {
/*  668 */     int len = text.length();
/*      */ 
/*      */     
/*  671 */     int j = len - 1;
/*  672 */     while (j >= 0) {
/*  673 */       byte code = decodeMap[text.charAt(j)];
/*  674 */       if (code == Byte.MAX_VALUE) {
/*      */         j--; continue;
/*      */       } 
/*  677 */       if (code == -1)
/*      */       {
/*  679 */         return text.length() / 4 * 3;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  684 */     j++;
/*  685 */     int padSize = len - j;
/*  686 */     if (padSize > 2)
/*      */     {
/*  688 */       return text.length() / 4 * 3;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  693 */     return text.length() / 4 * 3 - padSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] _parseBase64Binary(String text) {
/*  706 */     int buflen = guessLength(text);
/*  707 */     byte[] out = new byte[buflen];
/*  708 */     int o = 0;
/*      */     
/*  710 */     int len = text.length();
/*      */ 
/*      */     
/*  713 */     byte[] quadruplet = new byte[4];
/*  714 */     int q = 0;
/*      */ 
/*      */     
/*  717 */     for (int i = 0; i < len; i++) {
/*  718 */       char ch = text.charAt(i);
/*  719 */       byte v = decodeMap[ch];
/*      */       
/*  721 */       if (v != -1) {
/*  722 */         quadruplet[q++] = v;
/*      */       }
/*      */       
/*  725 */       if (q == 4) {
/*      */         
/*  727 */         out[o++] = (byte)(quadruplet[0] << 2 | quadruplet[1] >> 4);
/*  728 */         if (quadruplet[2] != Byte.MAX_VALUE) {
/*  729 */           out[o++] = (byte)(quadruplet[1] << 4 | quadruplet[2] >> 2);
/*      */         }
/*  731 */         if (quadruplet[3] != Byte.MAX_VALUE) {
/*  732 */           out[o++] = (byte)(quadruplet[2] << 6 | quadruplet[3]);
/*      */         }
/*  734 */         q = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  738 */     if (buflen == o)
/*      */     {
/*  740 */       return out;
/*      */     }
/*      */ 
/*      */     
/*  744 */     byte[] nb = new byte[o];
/*  745 */     System.arraycopy(out, 0, nb, 0, o);
/*  746 */     return nb;
/*      */   }
/*  748 */   private static final char[] encodeMap = initEncodeMap(); private static final DatatypeFactory datatypeFactory;
/*      */   
/*      */   private static char[] initEncodeMap() {
/*  751 */     char[] map = new char[64];
/*      */     int i;
/*  753 */     for (i = 0; i < 26; i++) {
/*  754 */       map[i] = (char)(65 + i);
/*      */     }
/*  756 */     for (i = 26; i < 52; i++) {
/*  757 */       map[i] = (char)(97 + i - 26);
/*      */     }
/*  759 */     for (i = 52; i < 62; i++) {
/*  760 */       map[i] = (char)(48 + i - 52);
/*      */     }
/*  762 */     map[62] = '+';
/*  763 */     map[63] = '/';
/*      */     
/*  765 */     return map;
/*      */   }
/*      */   
/*      */   public static char encode(int i) {
/*  769 */     return encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static byte encodeByte(int i) {
/*  773 */     return (byte)encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input) {
/*  777 */     return _printBase64Binary(input, 0, input.length);
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input, int offset, int len) {
/*  781 */     char[] buf = new char[(len + 2) / 3 * 4];
/*  782 */     int ptr = _printBase64Binary(input, offset, len, buf, 0);
/*  783 */     assert ptr == buf.length;
/*  784 */     return new String(buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int _printBase64Binary(byte[] input, int offset, int len, char[] buf, int ptr) {
/*  798 */     int remaining = len;
/*      */     int i;
/*  800 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  801 */       buf[ptr++] = encode(input[i] >> 2);
/*  802 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  805 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  808 */       buf[ptr++] = encode(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  811 */     if (remaining == 1) {
/*  812 */       buf[ptr++] = encode(input[i] >> 2);
/*  813 */       buf[ptr++] = encode((input[i] & 0x3) << 4);
/*  814 */       buf[ptr++] = '=';
/*  815 */       buf[ptr++] = '=';
/*      */     } 
/*      */     
/*  818 */     if (remaining == 2) {
/*  819 */       buf[ptr++] = encode(input[i] >> 2);
/*  820 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */       
/*  822 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2);
/*  823 */       buf[ptr++] = '=';
/*      */     } 
/*  825 */     return ptr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int _printBase64Binary(byte[] input, int offset, int len, byte[] out, int ptr) {
/*  839 */     byte[] buf = out;
/*  840 */     int remaining = len;
/*      */     int i;
/*  842 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  843 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  844 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  847 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  850 */       buf[ptr++] = encodeByte(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  853 */     if (remaining == 1) {
/*  854 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  855 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4);
/*  856 */       buf[ptr++] = 61;
/*  857 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  860 */     if (remaining == 2) {
/*  861 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  862 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  865 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2);
/*  866 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  869 */     return ptr;
/*      */   }
/*      */   
/*      */   private static CharSequence removeOptionalPlus(CharSequence s) {
/*  873 */     int len = s.length();
/*      */     
/*  875 */     if (len <= 1 || s.charAt(0) != '+') {
/*  876 */       return s;
/*      */     }
/*      */     
/*  879 */     s = s.subSequence(1, len);
/*  880 */     char ch = s.charAt(0);
/*  881 */     if ('0' <= ch && ch <= '9') {
/*  882 */       return s;
/*      */     }
/*  884 */     if ('.' == ch) {
/*  885 */       return s;
/*      */     }
/*      */     
/*  888 */     throw new NumberFormatException();
/*      */   }
/*      */   
/*      */   private static boolean isDigitOrPeriodOrSign(char ch) {
/*  892 */     if ('0' <= ch && ch <= '9') {
/*  893 */       return true;
/*      */     }
/*  895 */     if (ch == '+' || ch == '-' || ch == '.') {
/*  896 */       return true;
/*      */     }
/*  898 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  904 */       datatypeFactory = DatatypeFactory.newInstance();
/*  905 */     } catch (DatatypeConfigurationException e) {
/*  906 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final class CalendarFormatter
/*      */   {
/*      */     public static String doFormat(String format, Calendar cal) throws IllegalArgumentException {
/*  913 */       int fidx = 0;
/*  914 */       int flen = format.length();
/*  915 */       StringBuilder buf = new StringBuilder();
/*      */       
/*  917 */       while (fidx < flen) {
/*  918 */         char fch = format.charAt(fidx++);
/*      */         
/*  920 */         if (fch != '%') {
/*  921 */           buf.append(fch);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  926 */         switch (format.charAt(fidx++)) {
/*      */           case 'Y':
/*  928 */             formatYear(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'M':
/*  932 */             formatMonth(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'D':
/*  936 */             formatDays(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'h':
/*  940 */             formatHours(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'm':
/*  944 */             formatMinutes(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 's':
/*  948 */             formatSeconds(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'z':
/*  952 */             formatTimeZone(cal, buf);
/*      */             continue;
/*      */         } 
/*      */ 
/*      */         
/*  957 */         throw new InternalError();
/*      */       } 
/*      */ 
/*      */       
/*  961 */       return buf.toString();
/*      */     }
/*      */     private static void formatYear(Calendar cal, StringBuilder buf) {
/*      */       String s;
/*  965 */       int year = cal.get(1);
/*      */ 
/*      */       
/*  968 */       if (year <= 0) {
/*      */         
/*  970 */         s = Integer.toString(1 - year);
/*      */       } else {
/*      */         
/*  973 */         s = Integer.toString(year);
/*      */       } 
/*      */       
/*  976 */       while (s.length() < 4) {
/*  977 */         s = '0' + s;
/*      */       }
/*  979 */       if (year <= 0) {
/*  980 */         s = '-' + s;
/*      */       }
/*      */       
/*  983 */       buf.append(s);
/*      */     }
/*      */     
/*      */     private static void formatMonth(Calendar cal, StringBuilder buf) {
/*  987 */       formatTwoDigits(cal.get(2) + 1, buf);
/*      */     }
/*      */     
/*      */     private static void formatDays(Calendar cal, StringBuilder buf) {
/*  991 */       formatTwoDigits(cal.get(5), buf);
/*      */     }
/*      */     
/*      */     private static void formatHours(Calendar cal, StringBuilder buf) {
/*  995 */       formatTwoDigits(cal.get(11), buf);
/*      */     }
/*      */     
/*      */     private static void formatMinutes(Calendar cal, StringBuilder buf) {
/*  999 */       formatTwoDigits(cal.get(12), buf);
/*      */     }
/*      */     
/*      */     private static void formatSeconds(Calendar cal, StringBuilder buf) {
/* 1003 */       formatTwoDigits(cal.get(13), buf);
/* 1004 */       if (cal.isSet(14)) {
/* 1005 */         int n = cal.get(14);
/* 1006 */         if (n != 0) {
/* 1007 */           String ms = Integer.toString(n);
/* 1008 */           while (ms.length() < 3) {
/* 1009 */             ms = '0' + ms;
/*      */           }
/* 1011 */           buf.append('.');
/* 1012 */           buf.append(ms);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static void formatTimeZone(Calendar cal, StringBuilder buf) {
/* 1019 */       TimeZone tz = cal.getTimeZone();
/*      */       
/* 1021 */       if (tz == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1026 */       int offset = tz.getOffset(cal.getTime().getTime());
/*      */       
/* 1028 */       if (offset == 0) {
/* 1029 */         buf.append('Z');
/*      */         
/*      */         return;
/*      */       } 
/* 1033 */       if (offset >= 0) {
/* 1034 */         buf.append('+');
/*      */       } else {
/* 1036 */         buf.append('-');
/* 1037 */         offset *= -1;
/*      */       } 
/*      */       
/* 1040 */       offset /= 60000;
/*      */       
/* 1042 */       formatTwoDigits(offset / 60, buf);
/* 1043 */       buf.append(':');
/* 1044 */       formatTwoDigits(offset % 60, buf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void formatTwoDigits(int n, StringBuilder buf) {
/* 1050 */       if (n < 10) {
/* 1051 */         buf.append('0');
/*      */       }
/* 1053 */       buf.append(n);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\DatatypeConverterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */