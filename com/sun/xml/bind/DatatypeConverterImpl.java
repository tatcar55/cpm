/*      */ package com.sun.xml.bind;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.TimeZone;
/*      */ import javax.xml.bind.DatatypeConverterInterface;
/*      */ import javax.xml.datatype.DatatypeConfigurationException;
/*      */ import javax.xml.datatype.DatatypeFactory;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamWriter;
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
/*      */ @Deprecated
/*      */ public final class DatatypeConverterImpl
/*      */   implements DatatypeConverterInterface
/*      */ {
/*      */   @Deprecated
/*   78 */   public static final DatatypeConverterInterface theInstance = new DatatypeConverterImpl();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger _parseInteger(CharSequence s) {
/*   85 */     return new BigInteger(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString());
/*      */   }
/*      */   
/*      */   public static String _printInteger(BigInteger val) {
/*   89 */     return val.toString();
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
/*  103 */     int len = s.length();
/*  104 */     int sign = 1;
/*      */     
/*  106 */     int r = 0;
/*      */     
/*  108 */     for (int i = 0; i < len; i++) {
/*  109 */       char ch = s.charAt(i);
/*  110 */       if (!WhiteSpaceProcessor.isWhiteSpace(ch))
/*      */       {
/*  112 */         if ('0' <= ch && ch <= '9') {
/*  113 */           r = r * 10 + ch - 48;
/*  114 */         } else if (ch == '-') {
/*  115 */           sign = -1;
/*  116 */         } else if (ch != '+') {
/*      */ 
/*      */           
/*  119 */           throw new NumberFormatException("Not a number: " + s);
/*      */         } 
/*      */       }
/*      */     } 
/*  123 */     return r * sign;
/*      */   }
/*      */   
/*      */   public static long _parseLong(CharSequence s) {
/*  127 */     return Long.valueOf(removeOptionalPlus(WhiteSpaceProcessor.trim(s)).toString()).longValue();
/*      */   }
/*      */   
/*      */   public static short _parseShort(CharSequence s) {
/*  131 */     return (short)_parseInt(s);
/*      */   }
/*      */   
/*      */   public static String _printShort(short val) {
/*  135 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public static BigDecimal _parseDecimal(CharSequence content) {
/*  139 */     content = WhiteSpaceProcessor.trim(content);
/*      */     
/*  141 */     if (content.length() <= 0) {
/*  142 */       return null;
/*      */     }
/*      */     
/*  145 */     return new BigDecimal(content.toString());
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
/*      */   public static float _parseFloat(CharSequence _val) {
/*  161 */     String s = WhiteSpaceProcessor.trim(_val).toString();
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
/*  177 */     if (s.equals("NaN")) {
/*  178 */       return Float.NaN;
/*      */     }
/*  180 */     if (s.equals("INF")) {
/*  181 */       return Float.POSITIVE_INFINITY;
/*      */     }
/*  183 */     if (s.equals("-INF")) {
/*  184 */       return Float.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  187 */     if (s.length() == 0 || !isDigitOrPeriodOrSign(s.charAt(0)) || !isDigitOrPeriodOrSign(s.charAt(s.length() - 1)))
/*      */     {
/*      */       
/*  190 */       throw new NumberFormatException();
/*      */     }
/*      */ 
/*      */     
/*  194 */     return Float.parseFloat(s);
/*      */   }
/*      */   
/*      */   public static String _printFloat(float v) {
/*  198 */     if (Float.isNaN(v)) {
/*  199 */       return "NaN";
/*      */     }
/*  201 */     if (v == Float.POSITIVE_INFINITY) {
/*  202 */       return "INF";
/*      */     }
/*  204 */     if (v == Float.NEGATIVE_INFINITY) {
/*  205 */       return "-INF";
/*      */     }
/*  207 */     return String.valueOf(v);
/*      */   }
/*      */   
/*      */   public static double _parseDouble(CharSequence _val) {
/*  211 */     String val = WhiteSpaceProcessor.trim(_val).toString();
/*      */     
/*  213 */     if (val.equals("NaN")) {
/*  214 */       return Double.NaN;
/*      */     }
/*  216 */     if (val.equals("INF")) {
/*  217 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*  219 */     if (val.equals("-INF")) {
/*  220 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*  223 */     if (val.length() == 0 || !isDigitOrPeriodOrSign(val.charAt(0)) || !isDigitOrPeriodOrSign(val.charAt(val.length() - 1)))
/*      */     {
/*      */       
/*  226 */       throw new NumberFormatException(val);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  231 */     return Double.parseDouble(val);
/*      */   } public static Boolean _parseBoolean(CharSequence literal) {
/*      */     char ch;
/*      */     String strTrue, strFalse;
/*  235 */     if (literal == null) {
/*  236 */       return null;
/*      */     }
/*      */     
/*  239 */     int i = 0;
/*  240 */     int len = literal.length();
/*      */     
/*  242 */     boolean value = false;
/*      */     
/*  244 */     if (literal.length() <= 0) {
/*  245 */       return null;
/*      */     }
/*      */     
/*      */     do {
/*  249 */       ch = literal.charAt(i++);
/*  250 */     } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     
/*  252 */     int strIndex = 0;
/*      */     
/*  254 */     switch (ch) {
/*      */       case '1':
/*  256 */         value = true;
/*      */         break;
/*      */       case '0':
/*  259 */         value = false;
/*      */         break;
/*      */       case 't':
/*  262 */         strTrue = "rue";
/*      */         do {
/*  264 */           ch = literal.charAt(i++);
/*  265 */         } while (strTrue.charAt(strIndex++) == ch && i < len && strIndex < 3);
/*      */         
/*  267 */         if (strIndex == 3) {
/*  268 */           value = true; break;
/*      */         } 
/*  270 */         return Boolean.valueOf(false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 'f':
/*  276 */         strFalse = "alse";
/*      */         do {
/*  278 */           ch = literal.charAt(i++);
/*  279 */         } while (strFalse.charAt(strIndex++) == ch && i < len && strIndex < 4);
/*      */ 
/*      */         
/*  282 */         if (strIndex == 4) {
/*  283 */           value = false; break;
/*      */         } 
/*  285 */         return Boolean.valueOf(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  292 */     if (i < len) {
/*      */       do {
/*  294 */         ch = literal.charAt(i++);
/*  295 */       } while (WhiteSpaceProcessor.isWhiteSpace(ch) && i < len);
/*      */     }
/*      */     
/*  298 */     if (i == len) {
/*  299 */       return Boolean.valueOf(value);
/*      */     }
/*  301 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String _printBoolean(boolean val) {
/*  307 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   public static byte _parseByte(CharSequence literal) {
/*  311 */     return (byte)_parseInt(literal);
/*      */   }
/*      */   
/*      */   public static String _printByte(byte val) {
/*  315 */     return String.valueOf(val);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName _parseQName(CharSequence text, NamespaceContext nsc) {
/*      */     String uri, localPart, prefix;
/*  322 */     int length = text.length();
/*      */ 
/*      */     
/*  325 */     int start = 0;
/*  326 */     while (start < length && WhiteSpaceProcessor.isWhiteSpace(text.charAt(start))) {
/*  327 */       start++;
/*      */     }
/*      */     
/*  330 */     int end = length;
/*  331 */     while (end > start && WhiteSpaceProcessor.isWhiteSpace(text.charAt(end - 1))) {
/*  332 */       end--;
/*      */     }
/*      */     
/*  335 */     if (end == start) {
/*  336 */       throw new IllegalArgumentException("input is empty");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  345 */     int idx = start + 1;
/*  346 */     while (idx < end && text.charAt(idx) != ':') {
/*  347 */       idx++;
/*      */     }
/*      */     
/*  350 */     if (idx == end) {
/*  351 */       uri = nsc.getNamespaceURI("");
/*  352 */       localPart = text.subSequence(start, end).toString();
/*  353 */       prefix = "";
/*      */     } else {
/*      */       
/*  356 */       prefix = text.subSequence(start, idx).toString();
/*  357 */       localPart = text.subSequence(idx + 1, end).toString();
/*  358 */       uri = nsc.getNamespaceURI(prefix);
/*      */ 
/*      */       
/*  361 */       if (uri == null || uri.length() == 0)
/*      */       {
/*      */         
/*  364 */         throw new IllegalArgumentException("prefix " + prefix + " is not bound to a namespace");
/*      */       }
/*      */     } 
/*      */     
/*  368 */     return new QName(uri, localPart, prefix);
/*      */   }
/*      */   
/*      */   public static GregorianCalendar _parseDateTime(CharSequence s) {
/*  372 */     String val = WhiteSpaceProcessor.trim(s).toString();
/*  373 */     return datatypeFactory.newXMLGregorianCalendar(val).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   public static String _printDateTime(Calendar val) {
/*  377 */     return CalendarFormatter.doFormat("%Y-%M-%DT%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   public static String _printDate(Calendar val) {
/*  381 */     return CalendarFormatter.doFormat("%Y-%M-%D" + "%z", val);
/*      */   }
/*      */   
/*      */   public static String _printInt(int val) {
/*  385 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public static String _printLong(long val) {
/*  389 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   public static String _printDecimal(BigDecimal val) {
/*  393 */     return val.toPlainString();
/*      */   }
/*      */   
/*      */   public static String _printDouble(double v) {
/*  397 */     if (Double.isNaN(v)) {
/*  398 */       return "NaN";
/*      */     }
/*  400 */     if (v == Double.POSITIVE_INFINITY) {
/*  401 */       return "INF";
/*      */     }
/*  403 */     if (v == Double.NEGATIVE_INFINITY) {
/*  404 */       return "-INF";
/*      */     }
/*  406 */     return String.valueOf(v);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String _printQName(QName val, NamespaceContext nsc) {
/*  412 */     String qname, prefix = nsc.getPrefix(val.getNamespaceURI());
/*  413 */     String localPart = val.getLocalPart();
/*      */     
/*  415 */     if (prefix == null || prefix.length() == 0) {
/*  416 */       qname = localPart;
/*      */     } else {
/*  418 */       qname = prefix + ':' + localPart;
/*      */     } 
/*      */     
/*  421 */     return qname;
/*      */   }
/*      */ 
/*      */   
/*  425 */   private static final byte[] decodeMap = initDecodeMap();
/*      */   private static final byte PADDING = 127;
/*      */   
/*      */   private static byte[] initDecodeMap() {
/*  429 */     byte[] map = new byte[128];
/*      */     int i;
/*  431 */     for (i = 0; i < 128; i++) {
/*  432 */       map[i] = -1;
/*      */     }
/*      */     
/*  435 */     for (i = 65; i <= 90; i++) {
/*  436 */       map[i] = (byte)(i - 65);
/*      */     }
/*  438 */     for (i = 97; i <= 122; i++) {
/*  439 */       map[i] = (byte)(i - 97 + 26);
/*      */     }
/*  441 */     for (i = 48; i <= 57; i++) {
/*  442 */       map[i] = (byte)(i - 48 + 52);
/*      */     }
/*  444 */     map[43] = 62;
/*  445 */     map[47] = 63;
/*  446 */     map[61] = Byte.MAX_VALUE;
/*      */     
/*  448 */     return map;
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
/*  472 */     int len = text.length();
/*      */ 
/*      */     
/*  475 */     int j = len - 1;
/*  476 */     while (j >= 0) {
/*  477 */       byte code = decodeMap[text.charAt(j)];
/*  478 */       if (code == Byte.MAX_VALUE) {
/*      */         j--; continue;
/*      */       } 
/*  481 */       if (code == -1)
/*      */       {
/*  483 */         return text.length() / 4 * 3;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  488 */     j++;
/*  489 */     int padSize = len - j;
/*  490 */     if (padSize > 2)
/*      */     {
/*  492 */       return text.length() / 4 * 3;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  497 */     return text.length() / 4 * 3 - padSize;
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
/*  510 */     int buflen = guessLength(text);
/*  511 */     byte[] out = new byte[buflen];
/*  512 */     int o = 0;
/*      */     
/*  514 */     int len = text.length();
/*      */ 
/*      */     
/*  517 */     byte[] quadruplet = new byte[4];
/*  518 */     int q = 0;
/*      */ 
/*      */     
/*  521 */     for (int i = 0; i < len; i++) {
/*  522 */       char ch = text.charAt(i);
/*  523 */       byte v = decodeMap[ch];
/*      */       
/*  525 */       if (v != -1) {
/*  526 */         quadruplet[q++] = v;
/*      */       }
/*      */       
/*  529 */       if (q == 4) {
/*      */         
/*  531 */         out[o++] = (byte)(quadruplet[0] << 2 | quadruplet[1] >> 4);
/*  532 */         if (quadruplet[2] != Byte.MAX_VALUE) {
/*  533 */           out[o++] = (byte)(quadruplet[1] << 4 | quadruplet[2] >> 2);
/*      */         }
/*  535 */         if (quadruplet[3] != Byte.MAX_VALUE) {
/*  536 */           out[o++] = (byte)(quadruplet[2] << 6 | quadruplet[3]);
/*      */         }
/*  538 */         q = 0;
/*      */       } 
/*      */     } 
/*      */     
/*  542 */     if (buflen == o)
/*      */     {
/*  544 */       return out;
/*      */     }
/*      */ 
/*      */     
/*  548 */     byte[] nb = new byte[o];
/*  549 */     System.arraycopy(out, 0, nb, 0, o);
/*  550 */     return nb;
/*      */   }
/*  552 */   private static final char[] encodeMap = initEncodeMap(); private static final DatatypeFactory datatypeFactory;
/*      */   
/*      */   private static char[] initEncodeMap() {
/*  555 */     char[] map = new char[64];
/*      */     int i;
/*  557 */     for (i = 0; i < 26; i++) {
/*  558 */       map[i] = (char)(65 + i);
/*      */     }
/*  560 */     for (i = 26; i < 52; i++) {
/*  561 */       map[i] = (char)(97 + i - 26);
/*      */     }
/*  563 */     for (i = 52; i < 62; i++) {
/*  564 */       map[i] = (char)(48 + i - 52);
/*      */     }
/*  566 */     map[62] = '+';
/*  567 */     map[63] = '/';
/*      */     
/*  569 */     return map;
/*      */   }
/*      */   
/*      */   public static char encode(int i) {
/*  573 */     return encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static byte encodeByte(int i) {
/*  577 */     return (byte)encodeMap[i & 0x3F];
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input) {
/*  581 */     return _printBase64Binary(input, 0, input.length);
/*      */   }
/*      */   
/*      */   public static String _printBase64Binary(byte[] input, int offset, int len) {
/*  585 */     char[] buf = new char[(len + 2) / 3 * 4];
/*  586 */     int ptr = _printBase64Binary(input, offset, len, buf, 0);
/*  587 */     assert ptr == buf.length;
/*  588 */     return new String(buf);
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
/*  602 */     int remaining = len;
/*      */     int i;
/*  604 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  605 */       buf[ptr++] = encode(input[i] >> 2);
/*  606 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  609 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  612 */       buf[ptr++] = encode(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  615 */     if (remaining == 1) {
/*  616 */       buf[ptr++] = encode(input[i] >> 2);
/*  617 */       buf[ptr++] = encode((input[i] & 0x3) << 4);
/*  618 */       buf[ptr++] = '=';
/*  619 */       buf[ptr++] = '=';
/*      */     } 
/*      */     
/*  622 */     if (remaining == 2) {
/*  623 */       buf[ptr++] = encode(input[i] >> 2);
/*  624 */       buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */       
/*  626 */       buf[ptr++] = encode((input[i + 1] & 0xF) << 2);
/*  627 */       buf[ptr++] = '=';
/*      */     } 
/*  629 */     return ptr;
/*      */   }
/*      */   
/*      */   public static void _printBase64Binary(byte[] input, int offset, int len, XMLStreamWriter output) throws XMLStreamException {
/*  633 */     int remaining = len;
/*      */     
/*  635 */     char[] buf = new char[4];
/*      */     int i;
/*  637 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  638 */       buf[0] = encode(input[i] >> 2);
/*  639 */       buf[1] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  642 */       buf[2] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  645 */       buf[3] = encode(input[i + 2] & 0x3F);
/*  646 */       output.writeCharacters(buf, 0, 4);
/*      */     } 
/*      */     
/*  649 */     if (remaining == 1) {
/*  650 */       buf[0] = encode(input[i] >> 2);
/*  651 */       buf[1] = encode((input[i] & 0x3) << 4);
/*  652 */       buf[2] = '=';
/*  653 */       buf[3] = '=';
/*  654 */       output.writeCharacters(buf, 0, 4);
/*      */     } 
/*      */     
/*  657 */     if (remaining == 2) {
/*  658 */       buf[0] = encode(input[i] >> 2);
/*  659 */       buf[1] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */       
/*  661 */       buf[2] = encode((input[i + 1] & 0xF) << 2);
/*  662 */       buf[3] = '=';
/*  663 */       output.writeCharacters(buf, 0, 4);
/*      */     } 
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
/*  678 */     byte[] buf = out;
/*  679 */     int remaining = len;
/*      */     int i;
/*  681 */     for (i = offset; remaining >= 3; remaining -= 3, i += 3) {
/*  682 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  683 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  686 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*      */ 
/*      */       
/*  689 */       buf[ptr++] = encodeByte(input[i + 2] & 0x3F);
/*      */     } 
/*      */     
/*  692 */     if (remaining == 1) {
/*  693 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  694 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4);
/*  695 */       buf[ptr++] = 61;
/*  696 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  699 */     if (remaining == 2) {
/*  700 */       buf[ptr++] = encodeByte(input[i] >> 2);
/*  701 */       buf[ptr++] = encodeByte((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*      */ 
/*      */       
/*  704 */       buf[ptr++] = encodeByte((input[i + 1] & 0xF) << 2);
/*  705 */       buf[ptr++] = 61;
/*      */     } 
/*      */     
/*  708 */     return ptr;
/*      */   }
/*      */   
/*      */   private static CharSequence removeOptionalPlus(CharSequence s) {
/*  712 */     int len = s.length();
/*      */     
/*  714 */     if (len <= 1 || s.charAt(0) != '+') {
/*  715 */       return s;
/*      */     }
/*      */     
/*  718 */     s = s.subSequence(1, len);
/*  719 */     char ch = s.charAt(0);
/*  720 */     if ('0' <= ch && ch <= '9') {
/*  721 */       return s;
/*      */     }
/*  723 */     if ('.' == ch) {
/*  724 */       return s;
/*      */     }
/*      */     
/*  727 */     throw new NumberFormatException();
/*      */   }
/*      */   
/*      */   private static boolean isDigitOrPeriodOrSign(char ch) {
/*  731 */     if ('0' <= ch && ch <= '9') {
/*  732 */       return true;
/*      */     }
/*  734 */     if (ch == '+' || ch == '-' || ch == '.') {
/*  735 */       return true;
/*      */     }
/*  737 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  743 */       datatypeFactory = DatatypeFactory.newInstance();
/*  744 */     } catch (DatatypeConfigurationException e) {
/*  745 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final class CalendarFormatter
/*      */   {
/*      */     public static String doFormat(String format, Calendar cal) throws IllegalArgumentException {
/*  752 */       int fidx = 0;
/*  753 */       int flen = format.length();
/*  754 */       StringBuilder buf = new StringBuilder();
/*      */       
/*  756 */       while (fidx < flen) {
/*  757 */         char fch = format.charAt(fidx++);
/*      */         
/*  759 */         if (fch != '%') {
/*  760 */           buf.append(fch);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  765 */         switch (format.charAt(fidx++)) {
/*      */           case 'Y':
/*  767 */             formatYear(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'M':
/*  771 */             formatMonth(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'D':
/*  775 */             formatDays(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'h':
/*  779 */             formatHours(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'm':
/*  783 */             formatMinutes(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 's':
/*  787 */             formatSeconds(cal, buf);
/*      */             continue;
/*      */           
/*      */           case 'z':
/*  791 */             formatTimeZone(cal, buf);
/*      */             continue;
/*      */         } 
/*      */ 
/*      */         
/*  796 */         throw new InternalError();
/*      */       } 
/*      */ 
/*      */       
/*  800 */       return buf.toString();
/*      */     }
/*      */     private static void formatYear(Calendar cal, StringBuilder buf) {
/*      */       String s;
/*  804 */       int year = cal.get(1);
/*      */ 
/*      */       
/*  807 */       if (year <= 0) {
/*      */         
/*  809 */         s = Integer.toString(1 - year);
/*      */       } else {
/*      */         
/*  812 */         s = Integer.toString(year);
/*      */       } 
/*      */       
/*  815 */       while (s.length() < 4) {
/*  816 */         s = '0' + s;
/*      */       }
/*  818 */       if (year <= 0) {
/*  819 */         s = '-' + s;
/*      */       }
/*      */       
/*  822 */       buf.append(s);
/*      */     }
/*      */     
/*      */     private static void formatMonth(Calendar cal, StringBuilder buf) {
/*  826 */       formatTwoDigits(cal.get(2) + 1, buf);
/*      */     }
/*      */     
/*      */     private static void formatDays(Calendar cal, StringBuilder buf) {
/*  830 */       formatTwoDigits(cal.get(5), buf);
/*      */     }
/*      */     
/*      */     private static void formatHours(Calendar cal, StringBuilder buf) {
/*  834 */       formatTwoDigits(cal.get(11), buf);
/*      */     }
/*      */     
/*      */     private static void formatMinutes(Calendar cal, StringBuilder buf) {
/*  838 */       formatTwoDigits(cal.get(12), buf);
/*      */     }
/*      */     
/*      */     private static void formatSeconds(Calendar cal, StringBuilder buf) {
/*  842 */       formatTwoDigits(cal.get(13), buf);
/*  843 */       if (cal.isSet(14)) {
/*  844 */         int n = cal.get(14);
/*  845 */         if (n != 0) {
/*  846 */           String ms = Integer.toString(n);
/*  847 */           while (ms.length() < 3) {
/*  848 */             ms = '0' + ms;
/*      */           }
/*  850 */           buf.append('.');
/*  851 */           buf.append(ms);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private static void formatTimeZone(Calendar cal, StringBuilder buf) {
/*  858 */       TimeZone tz = cal.getTimeZone();
/*      */       
/*  860 */       if (tz == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  865 */       int offset = tz.getOffset(cal.getTime().getTime());
/*      */       
/*  867 */       if (offset == 0) {
/*  868 */         buf.append('Z');
/*      */         
/*      */         return;
/*      */       } 
/*  872 */       if (offset >= 0) {
/*  873 */         buf.append('+');
/*      */       } else {
/*  875 */         buf.append('-');
/*  876 */         offset *= -1;
/*      */       } 
/*      */       
/*  879 */       offset /= 60000;
/*      */       
/*  881 */       formatTwoDigits(offset / 60, buf);
/*  882 */       buf.append(':');
/*  883 */       formatTwoDigits(offset % 60, buf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void formatTwoDigits(int n, StringBuilder buf) {
/*  889 */       if (n < 10) {
/*  890 */         buf.append('0');
/*      */       }
/*  892 */       buf.append(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String parseString(String lexicalXSDString) {
/*  900 */     return lexicalXSDString;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public BigInteger parseInteger(String lexicalXSDInteger) {
/*  905 */     return _parseInteger(lexicalXSDInteger);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printInteger(BigInteger val) {
/*  910 */     return _printInteger(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public int parseInt(String s) {
/*  915 */     return _parseInt(s);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public long parseLong(String lexicalXSLong) {
/*  920 */     return _parseLong(lexicalXSLong);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public short parseShort(String lexicalXSDShort) {
/*  925 */     return _parseShort(lexicalXSDShort);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printShort(short val) {
/*  930 */     return _printShort(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public BigDecimal parseDecimal(String content) {
/*  935 */     return _parseDecimal(content);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public float parseFloat(String lexicalXSDFloat) {
/*  940 */     return _parseFloat(lexicalXSDFloat);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printFloat(float v) {
/*  945 */     return _printFloat(v);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public double parseDouble(String lexicalXSDDouble) {
/*  950 */     return _parseDouble(lexicalXSDDouble);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public boolean parseBoolean(String lexicalXSDBoolean) {
/*  955 */     Boolean b = _parseBoolean(lexicalXSDBoolean);
/*  956 */     return (b == null) ? false : b.booleanValue();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printBoolean(boolean val) {
/*  961 */     return val ? "true" : "false";
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public byte parseByte(String lexicalXSDByte) {
/*  966 */     return _parseByte(lexicalXSDByte);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printByte(byte val) {
/*  971 */     return _printByte(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public QName parseQName(String lexicalXSDQName, NamespaceContext nsc) {
/*  976 */     return _parseQName(lexicalXSDQName, nsc);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Calendar parseDateTime(String lexicalXSDDateTime) {
/*  981 */     return _parseDateTime(lexicalXSDDateTime);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDateTime(Calendar val) {
/*  986 */     return _printDateTime(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public byte[] parseBase64Binary(String lexicalXSDBase64Binary) {
/*  991 */     return _parseBase64Binary(lexicalXSDBase64Binary);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public byte[] parseHexBinary(String s) {
/*  996 */     int len = s.length();
/*      */ 
/*      */     
/*  999 */     if (len % 2 != 0) {
/* 1000 */       throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);
/*      */     }
/*      */     
/* 1003 */     byte[] out = new byte[len / 2];
/*      */     
/* 1005 */     for (int i = 0; i < len; i += 2) {
/* 1006 */       int h = hexToBin(s.charAt(i));
/* 1007 */       int l = hexToBin(s.charAt(i + 1));
/* 1008 */       if (h == -1 || l == -1) {
/* 1009 */         throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);
/*      */       }
/*      */       
/* 1012 */       out[i / 2] = (byte)(h * 16 + l);
/*      */     } 
/*      */     
/* 1015 */     return out;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   private static int hexToBin(char ch) {
/* 1020 */     if ('0' <= ch && ch <= '9') {
/* 1021 */       return ch - 48;
/*      */     }
/* 1023 */     if ('A' <= ch && ch <= 'F') {
/* 1024 */       return ch - 65 + 10;
/*      */     }
/* 1026 */     if ('a' <= ch && ch <= 'f') {
/* 1027 */       return ch - 97 + 10;
/*      */     }
/* 1029 */     return -1;
/*      */   }
/*      */   
/*      */   @Deprecated
/* 1033 */   private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
/*      */   
/*      */   @Deprecated
/*      */   public String printHexBinary(byte[] data) {
/* 1037 */     StringBuilder r = new StringBuilder(data.length * 2);
/* 1038 */     for (byte b : data) {
/* 1039 */       r.append(hexCode[b >> 4 & 0xF]);
/* 1040 */       r.append(hexCode[b & 0xF]);
/*      */     } 
/* 1042 */     return r.toString();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public long parseUnsignedInt(String lexicalXSDUnsignedInt) {
/* 1047 */     return _parseLong(lexicalXSDUnsignedInt);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printUnsignedInt(long val) {
/* 1052 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public int parseUnsignedShort(String lexicalXSDUnsignedShort) {
/* 1057 */     return _parseInt(lexicalXSDUnsignedShort);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Calendar parseTime(String lexicalXSDTime) {
/* 1062 */     return datatypeFactory.newXMLGregorianCalendar(lexicalXSDTime).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printTime(Calendar val) {
/* 1067 */     return CalendarFormatter.doFormat("%h:%m:%s%z", val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public Calendar parseDate(String lexicalXSDDate) {
/* 1072 */     return datatypeFactory.newXMLGregorianCalendar(lexicalXSDDate).toGregorianCalendar();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDate(Calendar val) {
/* 1077 */     return _printDate(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String parseAnySimpleType(String lexicalXSDAnySimpleType) {
/* 1082 */     return lexicalXSDAnySimpleType;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printString(String val) {
/* 1087 */     return val;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printInt(int val) {
/* 1092 */     return _printInt(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printLong(long val) {
/* 1097 */     return _printLong(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDecimal(BigDecimal val) {
/* 1102 */     return _printDecimal(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printDouble(double v) {
/* 1107 */     return _printDouble(v);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printQName(QName val, NamespaceContext nsc) {
/* 1112 */     return _printQName(val, nsc);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printBase64Binary(byte[] val) {
/* 1117 */     return _printBase64Binary(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printUnsignedShort(int val) {
/* 1122 */     return String.valueOf(val);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public String printAnySimpleType(String val) {
/* 1127 */     return val;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\DatatypeConverterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */