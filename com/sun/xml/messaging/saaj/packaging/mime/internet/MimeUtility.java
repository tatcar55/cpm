/*      */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*      */ 
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.ASCIIUtility;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.BEncoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.LineInputStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.QDecoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.QEncoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.QPDecoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.QPEncoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.UUDecoderStream;
/*      */ import com.sun.xml.messaging.saaj.packaging.mime.util.UUEncoderStream;
/*      */ import com.sun.xml.messaging.saaj.util.SAAJUtil;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.Hashtable;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
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
/*      */ public class MimeUtility
/*      */ {
/*      */   public static final int ALL = -1;
/*      */   private static final int BUFFER_SIZE = 1024;
/*      */   private static boolean decodeStrict = true;
/*      */   private static boolean encodeEolStrict = false;
/*      */   private static boolean foldEncodedWords = false;
/*      */   private static boolean foldText = true;
/*      */   private static String defaultJavaCharset;
/*      */   private static String defaultMIMECharset;
/*      */   private static Hashtable mime2java;
/*      */   
/*      */   static {
/*      */     try {
/*  153 */       String s = SAAJUtil.getSystemProperty("mail.mime.decodetext.strict");
/*      */       
/*  155 */       decodeStrict = (s == null || !s.equalsIgnoreCase("false"));
/*  156 */       s = SAAJUtil.getSystemProperty("mail.mime.encodeeol.strict");
/*      */       
/*  158 */       encodeEolStrict = (s != null && s.equalsIgnoreCase("true"));
/*  159 */       s = SAAJUtil.getSystemProperty("mail.mime.foldencodedwords");
/*      */       
/*  161 */       foldEncodedWords = (s != null && s.equalsIgnoreCase("true"));
/*  162 */       s = SAAJUtil.getSystemProperty("mail.mime.foldtext");
/*      */       
/*  164 */       foldText = (s == null || !s.equalsIgnoreCase("false"));
/*  165 */     } catch (SecurityException sex) {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getEncoding(DataSource ds) {
/*  195 */     ContentType cType = null;
/*  196 */     InputStream is = null;
/*  197 */     String encoding = null;
/*      */     
/*      */     try {
/*  200 */       cType = new ContentType(ds.getContentType());
/*  201 */       is = ds.getInputStream();
/*  202 */     } catch (Exception ex) {
/*  203 */       return "base64";
/*      */     } 
/*      */     
/*  206 */     boolean isText = cType.match("text/*");
/*      */     
/*  208 */     int i = checkAscii(is, -1, !isText);
/*  209 */     switch (i) {
/*      */       case 1:
/*  211 */         encoding = "7bit";
/*      */         break;
/*      */       case 2:
/*  214 */         encoding = "quoted-printable";
/*      */         break;
/*      */       default:
/*  217 */         encoding = "base64";
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  223 */       is.close();
/*  224 */     } catch (IOException ioex) {}
/*      */     
/*  226 */     return encoding;
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
/*      */   public static String getEncoding(DataHandler dh) {
/*  243 */     ContentType cType = null;
/*  244 */     String encoding = null;
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
/*  259 */     if (dh.getName() != null) {
/*  260 */       return getEncoding(dh.getDataSource());
/*      */     }
/*      */     try {
/*  263 */       cType = new ContentType(dh.getContentType());
/*  264 */     } catch (Exception ex) {
/*  265 */       return "base64";
/*      */     } 
/*      */     
/*  268 */     if (cType.match("text/*"))
/*      */     
/*  270 */     { AsciiOutputStream aos = new AsciiOutputStream(false, false);
/*      */       try {
/*  272 */         dh.writeTo(aos);
/*  273 */       } catch (IOException ex) {}
/*  274 */       switch (aos.getAscii())
/*      */       { case 1:
/*  276 */           encoding = "7bit";
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
/*  299 */           return encoding;case 2: encoding = "quoted-printable"; return encoding; }  encoding = "base64"; } else { AsciiOutputStream aos = new AsciiOutputStream(true, encodeEolStrict); try { dh.writeTo(aos); } catch (IOException ex) {} if (aos.getAscii() == 1) { encoding = "7bit"; } else { encoding = "base64"; }  }  return encoding;
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
/*      */   public static InputStream decode(InputStream is, String encoding) throws MessagingException {
/*  315 */     if (encoding.equalsIgnoreCase("base64"))
/*  316 */       return (InputStream)new BASE64DecoderStream(is); 
/*  317 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/*  318 */       return (InputStream)new QPDecoderStream(is); 
/*  319 */     if (encoding.equalsIgnoreCase("uuencode") || encoding.equalsIgnoreCase("x-uuencode") || encoding.equalsIgnoreCase("x-uue"))
/*      */     {
/*      */       
/*  322 */       return (InputStream)new UUDecoderStream(is); } 
/*  323 */     if (encoding.equalsIgnoreCase("binary") || encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit"))
/*      */     {
/*      */       
/*  326 */       return is;
/*      */     }
/*  328 */     throw new MessagingException("Unknown encoding: " + encoding);
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
/*      */   public static OutputStream encode(OutputStream os, String encoding) throws MessagingException {
/*  344 */     if (encoding == null)
/*  345 */       return os; 
/*  346 */     if (encoding.equalsIgnoreCase("base64"))
/*  347 */       return (OutputStream)new BASE64EncoderStream(os); 
/*  348 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/*  349 */       return (OutputStream)new QPEncoderStream(os); 
/*  350 */     if (encoding.equalsIgnoreCase("uuencode") || encoding.equalsIgnoreCase("x-uuencode") || encoding.equalsIgnoreCase("x-uue"))
/*      */     {
/*      */       
/*  353 */       return (OutputStream)new UUEncoderStream(os); } 
/*  354 */     if (encoding.equalsIgnoreCase("binary") || encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit"))
/*      */     {
/*      */       
/*  357 */       return os;
/*      */     }
/*  359 */     throw new MessagingException("Unknown encoding: " + encoding);
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
/*      */   public static OutputStream encode(OutputStream os, String encoding, String filename) throws MessagingException {
/*  381 */     if (encoding == null)
/*  382 */       return os; 
/*  383 */     if (encoding.equalsIgnoreCase("base64"))
/*  384 */       return (OutputStream)new BASE64EncoderStream(os); 
/*  385 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/*  386 */       return (OutputStream)new QPEncoderStream(os); 
/*  387 */     if (encoding.equalsIgnoreCase("uuencode") || encoding.equalsIgnoreCase("x-uuencode") || encoding.equalsIgnoreCase("x-uue"))
/*      */     {
/*      */       
/*  390 */       return (OutputStream)new UUEncoderStream(os, filename); } 
/*  391 */     if (encoding.equalsIgnoreCase("binary") || encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit"))
/*      */     {
/*      */       
/*  394 */       return os;
/*      */     }
/*  396 */     throw new MessagingException("Unknown encoding: " + encoding);
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
/*      */   public static String encodeText(String text) throws UnsupportedEncodingException {
/*  437 */     return encodeText(text, null, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeText(String text, String charset, String encoding) throws UnsupportedEncodingException {
/*  468 */     return encodeWord(text, charset, encoding, false);
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
/*      */   public static String decodeText(String etext) throws UnsupportedEncodingException {
/*  510 */     String lwsp = " \t\n\r";
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
/*  522 */     if (etext.indexOf("=?") == -1) {
/*  523 */       return etext;
/*      */     }
/*      */ 
/*      */     
/*  527 */     StringTokenizer st = new StringTokenizer(etext, lwsp, true);
/*  528 */     StringBuffer sb = new StringBuffer();
/*  529 */     StringBuffer wsb = new StringBuffer();
/*  530 */     boolean prevWasEncoded = false;
/*      */     
/*  532 */     while (st.hasMoreTokens()) {
/*      */       
/*  534 */       String str1, s = st.nextToken();
/*      */       char c;
/*  536 */       if ((c = s.charAt(0)) == ' ' || c == '\t' || c == '\r' || c == '\n') {
/*      */         
/*  538 */         wsb.append(c);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       try {
/*  543 */         str1 = decodeWord(s);
/*      */         
/*  545 */         if (!prevWasEncoded && wsb.length() > 0)
/*      */         {
/*      */ 
/*      */           
/*  549 */           sb.append(wsb);
/*      */         }
/*  551 */         prevWasEncoded = true;
/*  552 */       } catch (ParseException pex) {
/*      */         
/*  554 */         str1 = s;
/*      */         
/*  556 */         if (!decodeStrict) {
/*  557 */           str1 = decodeInnerWords(str1);
/*      */         }
/*  559 */         if (wsb.length() > 0)
/*  560 */           sb.append(wsb); 
/*  561 */         prevWasEncoded = false;
/*      */       } 
/*  563 */       sb.append(str1);
/*  564 */       wsb.setLength(0);
/*      */     } 
/*      */     
/*  567 */     return sb.toString();
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
/*      */ 
/*      */   
/*      */   public static String encodeWord(String word) throws UnsupportedEncodingException {
/*  593 */     return encodeWord(word, null, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encodeWord(String word, String charset, String encoding) throws UnsupportedEncodingException {
/*  621 */     return encodeWord(word, charset, encoding, true);
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
/*      */   private static String encodeWord(String string, String charset, String encoding, boolean encodingWord) throws UnsupportedEncodingException {
/*      */     String jcharset;
/*      */     boolean b64;
/*  637 */     int ascii = checkAscii(string);
/*  638 */     if (ascii == 1) {
/*  639 */       return string;
/*      */     }
/*      */ 
/*      */     
/*  643 */     if (charset == null) {
/*  644 */       jcharset = getDefaultJavaCharset();
/*  645 */       charset = getDefaultMIMECharset();
/*      */     } else {
/*  647 */       jcharset = javaCharset(charset);
/*      */     } 
/*      */     
/*  650 */     if (encoding == null) {
/*  651 */       if (ascii != 3) {
/*  652 */         encoding = "Q";
/*      */       } else {
/*  654 */         encoding = "B";
/*      */       } 
/*      */     }
/*      */     
/*  658 */     if (encoding.equalsIgnoreCase("B")) {
/*  659 */       b64 = true;
/*  660 */     } else if (encoding.equalsIgnoreCase("Q")) {
/*  661 */       b64 = false;
/*      */     } else {
/*  663 */       throw new UnsupportedEncodingException("Unknown transfer encoding: " + encoding);
/*      */     } 
/*      */     
/*  666 */     StringBuffer outb = new StringBuffer();
/*  667 */     doEncode(string, b64, jcharset, 68 - charset.length(), "=?" + charset + "?" + encoding + "?", true, encodingWord, outb);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  675 */     return outb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void doEncode(String string, boolean b64, String jcharset, int avail, String prefix, boolean first, boolean encodingWord, StringBuffer buf) throws UnsupportedEncodingException {
/*      */     int len;
/*  685 */     byte[] bytes = string.getBytes(jcharset);
/*      */     
/*  687 */     if (b64) {
/*  688 */       len = BEncoderStream.encodedLength(bytes);
/*      */     } else {
/*  690 */       len = QEncoderStream.encodedLength(bytes, encodingWord);
/*      */     } 
/*      */     int size;
/*  693 */     if (len > avail && (size = string.length()) > 1) {
/*      */ 
/*      */       
/*  696 */       doEncode(string.substring(0, size / 2), b64, jcharset, avail, prefix, first, encodingWord, buf);
/*      */       
/*  698 */       doEncode(string.substring(size / 2, size), b64, jcharset, avail, prefix, false, encodingWord, buf);
/*      */     } else {
/*      */       QEncoderStream qEncoderStream;
/*      */       
/*  702 */       ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
/*      */       
/*  704 */       if (b64) {
/*  705 */         BEncoderStream bEncoderStream = new BEncoderStream(os);
/*      */       } else {
/*  707 */         qEncoderStream = new QEncoderStream(os, encodingWord);
/*      */       } 
/*      */       try {
/*  710 */         qEncoderStream.write(bytes);
/*  711 */         qEncoderStream.close();
/*  712 */       } catch (IOException ioex) {}
/*      */       
/*  714 */       byte[] encodedBytes = os.toByteArray();
/*      */ 
/*      */       
/*  717 */       if (!first)
/*  718 */         if (foldEncodedWords) {
/*  719 */           buf.append("\r\n ");
/*      */         } else {
/*  721 */           buf.append(" ");
/*      */         }  
/*  723 */       buf.append(prefix);
/*  724 */       for (int i = 0; i < encodedBytes.length; i++)
/*  725 */         buf.append((char)encodedBytes[i]); 
/*  726 */       buf.append("?=");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String decodeWord(String eword) throws ParseException, UnsupportedEncodingException {
/*  746 */     if (!eword.startsWith("=?")) {
/*  747 */       throw new ParseException();
/*      */     }
/*      */     
/*  750 */     int start = 2; int pos;
/*  751 */     if ((pos = eword.indexOf('?', start)) == -1)
/*  752 */       throw new ParseException(); 
/*  753 */     String charset = javaCharset(eword.substring(start, pos));
/*      */ 
/*      */     
/*  756 */     start = pos + 1;
/*  757 */     if ((pos = eword.indexOf('?', start)) == -1)
/*  758 */       throw new ParseException(); 
/*  759 */     String encoding = eword.substring(start, pos);
/*      */ 
/*      */     
/*  762 */     start = pos + 1;
/*  763 */     if ((pos = eword.indexOf("?=", start)) == -1)
/*  764 */       throw new ParseException(); 
/*  765 */     String word = eword.substring(start, pos);
/*      */     
/*      */     try {
/*      */       QDecoderStream qDecoderStream;
/*  769 */       ByteArrayInputStream bis = new ByteArrayInputStream(ASCIIUtility.getBytes(word));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  774 */       if (encoding.equalsIgnoreCase("B")) {
/*  775 */         BASE64DecoderStream bASE64DecoderStream = new BASE64DecoderStream(bis);
/*  776 */       } else if (encoding.equalsIgnoreCase("Q")) {
/*  777 */         qDecoderStream = new QDecoderStream(bis);
/*      */       } else {
/*  779 */         throw new UnsupportedEncodingException("unknown encoding: " + encoding);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  787 */       int count = bis.available();
/*  788 */       byte[] bytes = new byte[count];
/*      */       
/*  790 */       count = qDecoderStream.read(bytes, 0, count);
/*      */ 
/*      */ 
/*      */       
/*  794 */       String s = new String(bytes, 0, count, charset);
/*  795 */       if (pos + 2 < eword.length()) {
/*      */         
/*  797 */         String rest = eword.substring(pos + 2);
/*  798 */         if (!decodeStrict)
/*  799 */           rest = decodeInnerWords(rest); 
/*  800 */         s = s + rest;
/*      */       } 
/*  802 */       return s;
/*  803 */     } catch (UnsupportedEncodingException uex) {
/*      */ 
/*      */       
/*  806 */       throw uex;
/*  807 */     } catch (IOException ioex) {
/*      */       
/*  809 */       throw new ParseException();
/*  810 */     } catch (IllegalArgumentException iex) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  818 */       throw new UnsupportedEncodingException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String decodeInnerWords(String word) throws UnsupportedEncodingException {
/*  829 */     int start = 0;
/*  830 */     StringBuffer buf = new StringBuffer(); int i;
/*  831 */     while ((i = word.indexOf("=?", start)) >= 0) {
/*  832 */       buf.append(word.substring(start, i));
/*  833 */       int end = word.indexOf("?=", i);
/*  834 */       if (end < 0)
/*      */         break; 
/*  836 */       String s = word.substring(i, end + 2);
/*      */       try {
/*  838 */         s = decodeWord(s);
/*  839 */       } catch (ParseException pex) {}
/*      */ 
/*      */       
/*  842 */       buf.append(s);
/*  843 */       start = end + 2;
/*      */     } 
/*  845 */     if (start == 0)
/*  846 */       return word; 
/*  847 */     if (start < word.length())
/*  848 */       buf.append(word.substring(start)); 
/*  849 */     return buf.toString();
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
/*      */   public static String quote(String word, String specials) {
/*  869 */     int len = word.length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     boolean needQuoting = false;
/*  876 */     for (int i = 0; i < len; i++) {
/*  877 */       char c = word.charAt(i);
/*  878 */       if (c == '"' || c == '\\' || c == '\r' || c == '\n') {
/*      */         
/*  880 */         StringBuffer sb = new StringBuffer(len + 3);
/*  881 */         sb.append('"');
/*  882 */         sb.append(word.substring(0, i));
/*  883 */         int lastc = 0;
/*  884 */         for (int j = i; j < len; j++) {
/*  885 */           char cc = word.charAt(j);
/*  886 */           if (cc == '"' || cc == '\\' || cc == '\r' || cc == '\n')
/*      */           {
/*  888 */             if (cc != '\n' || lastc != 13)
/*      */             {
/*      */               
/*  891 */               sb.append('\\'); }  } 
/*  892 */           sb.append(cc);
/*  893 */           lastc = cc;
/*      */         } 
/*  895 */         sb.append('"');
/*  896 */         return sb.toString();
/*  897 */       }  if (c < ' ' || c >= '' || specials.indexOf(c) >= 0)
/*      */       {
/*  899 */         needQuoting = true;
/*      */       }
/*      */     } 
/*  902 */     if (needQuoting) {
/*  903 */       StringBuffer sb = new StringBuffer(len + 2);
/*  904 */       sb.append('"').append(word).append('"');
/*  905 */       return sb.toString();
/*      */     } 
/*  907 */     return word;
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
/*      */   static String fold(int used, String s) {
/*  926 */     if (!foldText) {
/*  927 */       return s;
/*      */     }
/*      */     
/*      */     int end;
/*      */     
/*  932 */     for (end = s.length() - 1; end >= 0; end--) {
/*  933 */       char c = s.charAt(end);
/*  934 */       if (c != ' ' && c != '\t')
/*      */         break; 
/*      */     } 
/*  937 */     if (end != s.length() - 1) {
/*  938 */       s = s.substring(0, end + 1);
/*      */     }
/*      */     
/*  941 */     if (used + s.length() <= 76) {
/*  942 */       return s;
/*      */     }
/*      */     
/*  945 */     StringBuffer sb = new StringBuffer(s.length() + 4);
/*  946 */     char lastc = Character.MIN_VALUE;
/*  947 */     while (used + s.length() > 76) {
/*  948 */       int lastspace = -1;
/*  949 */       for (int i = 0; i < s.length() && (
/*  950 */         lastspace == -1 || used + i <= 76); i++) {
/*      */         
/*  952 */         char c = s.charAt(i);
/*  953 */         if ((c == ' ' || c == '\t') && 
/*  954 */           lastc != ' ' && lastc != '\t')
/*  955 */           lastspace = i; 
/*  956 */         lastc = c;
/*      */       } 
/*  958 */       if (lastspace == -1) {
/*      */         
/*  960 */         sb.append(s);
/*  961 */         s = "";
/*  962 */         used = 0;
/*      */         break;
/*      */       } 
/*  965 */       sb.append(s.substring(0, lastspace));
/*  966 */       sb.append("\r\n");
/*  967 */       lastc = s.charAt(lastspace);
/*  968 */       sb.append(lastc);
/*  969 */       s = s.substring(lastspace + 1);
/*  970 */       used = 1;
/*      */     } 
/*  972 */     sb.append(s);
/*  973 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String unfold(String s) {
/*  984 */     if (!foldText) {
/*  985 */       return s;
/*      */     }
/*  987 */     StringBuffer sb = null;
/*      */     int i;
/*  989 */     while ((i = indexOfAny(s, "\r\n")) >= 0) {
/*  990 */       int start = i;
/*  991 */       int l = s.length();
/*  992 */       i++;
/*  993 */       if (i < l && s.charAt(i - 1) == '\r' && s.charAt(i) == '\n')
/*  994 */         i++; 
/*  995 */       if (start == 0 || s.charAt(start - 1) != '\\') {
/*      */         char c;
/*      */ 
/*      */         
/*  999 */         if (i < l && ((c = s.charAt(i)) == ' ' || c == '\t')) {
/* 1000 */           i++;
/* 1001 */           while (i < l && ((c = s.charAt(i)) == ' ' || c == '\t'))
/* 1002 */             i++; 
/* 1003 */           if (sb == null)
/* 1004 */             sb = new StringBuffer(s.length()); 
/* 1005 */           if (start != 0) {
/* 1006 */             sb.append(s.substring(0, start));
/* 1007 */             sb.append(' ');
/*      */           } 
/* 1009 */           s = s.substring(i);
/*      */           
/*      */           continue;
/*      */         } 
/* 1013 */         if (sb == null)
/* 1014 */           sb = new StringBuffer(s.length()); 
/* 1015 */         sb.append(s.substring(0, i));
/* 1016 */         s = s.substring(i);
/*      */         
/*      */         continue;
/*      */       } 
/* 1020 */       if (sb == null)
/* 1021 */         sb = new StringBuffer(s.length()); 
/* 1022 */       sb.append(s.substring(0, start - 1));
/* 1023 */       sb.append(s.substring(start, i));
/* 1024 */       s = s.substring(i);
/*      */     } 
/*      */     
/* 1027 */     if (sb != null) {
/* 1028 */       sb.append(s);
/* 1029 */       return sb.toString();
/*      */     } 
/* 1031 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int indexOfAny(String s, String any) {
/* 1041 */     return indexOfAny(s, any, 0);
/*      */   }
/*      */   
/*      */   private static int indexOfAny(String s, String any, int start) {
/*      */     try {
/* 1046 */       int len = s.length();
/* 1047 */       for (int i = start; i < len; i++) {
/* 1048 */         if (any.indexOf(s.charAt(i)) >= 0)
/* 1049 */           return i; 
/*      */       } 
/* 1051 */       return -1;
/* 1052 */     } catch (StringIndexOutOfBoundsException e) {
/* 1053 */       return -1;
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
/*      */   public static String javaCharset(String charset) {
/* 1065 */     if (mime2java == null || charset == null)
/*      */     {
/* 1067 */       return charset;
/*      */     }
/* 1069 */     String alias = (String)mime2java.get(charset.toLowerCase());
/* 1070 */     return (alias == null) ? charset : alias;
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
/*      */   public static String mimeCharset(String charset) {
/* 1087 */     if (java2mime == null || charset == null)
/*      */     {
/* 1089 */       return charset;
/*      */     }
/* 1091 */     String alias = (String)java2mime.get(charset.toLowerCase());
/* 1092 */     return (alias == null) ? charset : alias;
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
/*      */   public static String getDefaultJavaCharset() {
/* 1109 */     if (defaultJavaCharset == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1114 */       String mimecs = null;
/*      */       
/* 1116 */       mimecs = SAAJUtil.getSystemProperty("mail.mime.charset");
/*      */       
/* 1118 */       if (mimecs != null && mimecs.length() > 0) {
/* 1119 */         defaultJavaCharset = javaCharset(mimecs);
/* 1120 */         return defaultJavaCharset;
/*      */       } 
/*      */       
/*      */       try {
/* 1124 */         defaultJavaCharset = System.getProperty("file.encoding", "8859_1");
/*      */       }
/* 1126 */       catch (SecurityException sex) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1133 */         InputStreamReader reader = new InputStreamReader(new NullInputStream());
/*      */         
/* 1135 */         defaultJavaCharset = reader.getEncoding();
/* 1136 */         if (defaultJavaCharset == null)
/* 1137 */           defaultJavaCharset = "8859_1"; 
/*      */       } 
/*      */     }  class NullInputStream extends InputStream { public int read() {
/*      */         return 0;
/* 1141 */       } }; return defaultJavaCharset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getDefaultMIMECharset() {
/* 1148 */     if (defaultMIMECharset == null) {
/* 1149 */       defaultMIMECharset = SAAJUtil.getSystemProperty("mail.mime.charset");
/*      */     }
/* 1151 */     if (defaultMIMECharset == null)
/* 1152 */       defaultMIMECharset = mimeCharset(getDefaultJavaCharset()); 
/* 1153 */     return defaultMIMECharset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1162 */   private static Hashtable java2mime = new Hashtable<Object, Object>(40); static final int ALL_ASCII = 1; static final int MOSTLY_ASCII = 2; static final int MOSTLY_NONASCII = 3; static {
/* 1163 */     mime2java = new Hashtable<Object, Object>(10);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1168 */       InputStream is = MimeUtility.class.getResourceAsStream("/META-INF/javamail.charset.map");
/*      */ 
/*      */ 
/*      */       
/* 1172 */       if (is != null) {
/* 1173 */         LineInputStream lineInputStream = new LineInputStream(is);
/*      */ 
/*      */         
/* 1176 */         loadMappings(lineInputStream, java2mime);
/*      */ 
/*      */         
/* 1179 */         loadMappings(lineInputStream, mime2java);
/*      */       } 
/* 1181 */     } catch (Exception ex) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1186 */     if (java2mime.isEmpty()) {
/* 1187 */       java2mime.put("8859_1", "ISO-8859-1");
/* 1188 */       java2mime.put("iso8859_1", "ISO-8859-1");
/* 1189 */       java2mime.put("ISO8859-1", "ISO-8859-1");
/*      */       
/* 1191 */       java2mime.put("8859_2", "ISO-8859-2");
/* 1192 */       java2mime.put("iso8859_2", "ISO-8859-2");
/* 1193 */       java2mime.put("ISO8859-2", "ISO-8859-2");
/*      */       
/* 1195 */       java2mime.put("8859_3", "ISO-8859-3");
/* 1196 */       java2mime.put("iso8859_3", "ISO-8859-3");
/* 1197 */       java2mime.put("ISO8859-3", "ISO-8859-3");
/*      */       
/* 1199 */       java2mime.put("8859_4", "ISO-8859-4");
/* 1200 */       java2mime.put("iso8859_4", "ISO-8859-4");
/* 1201 */       java2mime.put("ISO8859-4", "ISO-8859-4");
/*      */       
/* 1203 */       java2mime.put("8859_5", "ISO-8859-5");
/* 1204 */       java2mime.put("iso8859_5", "ISO-8859-5");
/* 1205 */       java2mime.put("ISO8859-5", "ISO-8859-5");
/*      */       
/* 1207 */       java2mime.put("8859_6", "ISO-8859-6");
/* 1208 */       java2mime.put("iso8859_6", "ISO-8859-6");
/* 1209 */       java2mime.put("ISO8859-6", "ISO-8859-6");
/*      */       
/* 1211 */       java2mime.put("8859_7", "ISO-8859-7");
/* 1212 */       java2mime.put("iso8859_7", "ISO-8859-7");
/* 1213 */       java2mime.put("ISO8859-7", "ISO-8859-7");
/*      */       
/* 1215 */       java2mime.put("8859_8", "ISO-8859-8");
/* 1216 */       java2mime.put("iso8859_8", "ISO-8859-8");
/* 1217 */       java2mime.put("ISO8859-8", "ISO-8859-8");
/*      */       
/* 1219 */       java2mime.put("8859_9", "ISO-8859-9");
/* 1220 */       java2mime.put("iso8859_9", "ISO-8859-9");
/* 1221 */       java2mime.put("ISO8859-9", "ISO-8859-9");
/*      */       
/* 1223 */       java2mime.put("SJIS", "Shift_JIS");
/* 1224 */       java2mime.put("MS932", "Shift_JIS");
/* 1225 */       java2mime.put("JIS", "ISO-2022-JP");
/* 1226 */       java2mime.put("ISO2022JP", "ISO-2022-JP");
/* 1227 */       java2mime.put("EUC_JP", "euc-jp");
/* 1228 */       java2mime.put("KOI8_R", "koi8-r");
/* 1229 */       java2mime.put("EUC_CN", "euc-cn");
/* 1230 */       java2mime.put("EUC_TW", "euc-tw");
/* 1231 */       java2mime.put("EUC_KR", "euc-kr");
/*      */     } 
/* 1233 */     if (mime2java.isEmpty()) {
/* 1234 */       mime2java.put("iso-2022-cn", "ISO2022CN");
/* 1235 */       mime2java.put("iso-2022-kr", "ISO2022KR");
/* 1236 */       mime2java.put("utf-8", "UTF8");
/* 1237 */       mime2java.put("utf8", "UTF8");
/* 1238 */       mime2java.put("ja_jp.iso2022-7", "ISO2022JP");
/* 1239 */       mime2java.put("ja_jp.eucjp", "EUCJIS");
/* 1240 */       mime2java.put("euc-kr", "KSC5601");
/* 1241 */       mime2java.put("euckr", "KSC5601");
/* 1242 */       mime2java.put("us-ascii", "ISO-8859-1");
/* 1243 */       mime2java.put("x-us-ascii", "ISO-8859-1");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadMappings(LineInputStream is, Hashtable<String, String> table) {
/*      */     while (true) {
/*      */       String currLine;
/*      */       try {
/* 1252 */         currLine = is.readLine();
/* 1253 */       } catch (IOException ioex) {
/*      */         break;
/*      */       } 
/*      */       
/* 1257 */       if (currLine == null)
/*      */         break; 
/* 1259 */       if (currLine.startsWith("--") && currLine.endsWith("--")) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 1264 */       if (currLine.trim().length() == 0 || currLine.startsWith("#")) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1269 */       StringTokenizer tk = new StringTokenizer(currLine, " \t");
/*      */       try {
/* 1271 */         String key = tk.nextToken();
/* 1272 */         String value = tk.nextToken();
/* 1273 */         table.put(key.toLowerCase(), value);
/* 1274 */       } catch (NoSuchElementException nex) {}
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
/*      */ 
/*      */   
/*      */   static int checkAscii(String s) {
/* 1291 */     int ascii = 0, non_ascii = 0;
/* 1292 */     int l = s.length();
/*      */     
/* 1294 */     for (int i = 0; i < l; i++) {
/* 1295 */       if (nonascii(s.charAt(i))) {
/* 1296 */         non_ascii++;
/*      */       } else {
/* 1298 */         ascii++;
/*      */       } 
/*      */     } 
/* 1301 */     if (non_ascii == 0)
/* 1302 */       return 1; 
/* 1303 */     if (ascii > non_ascii) {
/* 1304 */       return 2;
/*      */     }
/* 1306 */     return 3;
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
/*      */   static int checkAscii(byte[] b) {
/* 1320 */     int ascii = 0, non_ascii = 0;
/*      */     
/* 1322 */     for (int i = 0; i < b.length; i++) {
/*      */ 
/*      */ 
/*      */       
/* 1326 */       if (nonascii(b[i] & 0xFF)) {
/* 1327 */         non_ascii++;
/*      */       } else {
/* 1329 */         ascii++;
/*      */       } 
/*      */     } 
/* 1332 */     if (non_ascii == 0)
/* 1333 */       return 1; 
/* 1334 */     if (ascii > non_ascii) {
/* 1335 */       return 2;
/*      */     }
/* 1337 */     return 3;
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
/*      */   
/*      */   static int checkAscii(InputStream is, int max, boolean breakOnNonAscii) {
/* 1362 */     int ascii = 0, non_ascii = 0;
/*      */     
/* 1364 */     int block = 4096;
/* 1365 */     int linelen = 0;
/* 1366 */     boolean longLine = false, badEOL = false;
/* 1367 */     boolean checkEOL = (encodeEolStrict && breakOnNonAscii);
/* 1368 */     byte[] buf = null;
/* 1369 */     if (max != 0) {
/* 1370 */       block = (max == -1) ? 4096 : Math.min(max, 4096);
/* 1371 */       buf = new byte[block];
/*      */     } 
/* 1373 */     while (max != 0) {
/*      */       int len; try {
/* 1375 */         if ((len = is.read(buf, 0, block)) == -1)
/*      */           break; 
/* 1377 */         int lastb = 0;
/* 1378 */         for (int i = 0; i < len; i++) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1383 */           int b = buf[i] & 0xFF;
/* 1384 */           if (checkEOL && ((lastb == 13 && b != 10) || (lastb != 13 && b == 10)))
/*      */           {
/*      */             
/* 1387 */             badEOL = true; } 
/* 1388 */           if (b == 13 || b == 10) {
/* 1389 */             linelen = 0;
/*      */           } else {
/* 1391 */             linelen++;
/* 1392 */             if (linelen > 998)
/* 1393 */               longLine = true; 
/*      */           } 
/* 1395 */           if (nonascii(b)) {
/* 1396 */             if (breakOnNonAscii) {
/* 1397 */               return 3;
/*      */             }
/* 1399 */             non_ascii++;
/*      */           } else {
/* 1401 */             ascii++;
/* 1402 */           }  lastb = b;
/*      */         } 
/* 1404 */       } catch (IOException ioex) {
/*      */         break;
/*      */       } 
/* 1407 */       if (max != -1) {
/* 1408 */         max -= len;
/*      */       }
/*      */     } 
/* 1411 */     if (max == 0 && breakOnNonAscii)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1418 */       return 3;
/*      */     }
/* 1420 */     if (non_ascii == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1425 */       if (badEOL) {
/* 1426 */         return 3;
/*      */       }
/* 1428 */       if (longLine) {
/* 1429 */         return 2;
/*      */       }
/* 1431 */       return 1;
/*      */     } 
/* 1433 */     if (ascii > non_ascii)
/* 1434 */       return 2; 
/* 1435 */     return 3;
/*      */   }
/*      */   
/*      */   static final boolean nonascii(int b) {
/* 1439 */     return (b >= 127 || (b < 32 && b != 13 && b != 10 && b != 9));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\MimeUtility.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */