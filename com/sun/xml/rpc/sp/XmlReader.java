/*     */ package com.sun.xml.rpc.sp;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharConversionException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class XmlReader
/*     */   extends Reader
/*     */ {
/*     */   private static final int MAXPUSHBACK = 512;
/*     */   private Reader in;
/*     */   private String assignedEncoding;
/*     */   private boolean closed;
/*     */   
/*     */   public static Reader createReader(InputStream in) throws IOException {
/*  96 */     return new XmlReader(in);
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
/*     */   public static Reader createReader(InputStream in, String encoding) throws IOException {
/* 111 */     if (encoding == null)
/* 112 */       return new XmlReader(in); 
/* 113 */     if ("UTF-8".equalsIgnoreCase(encoding) || "UTF8".equalsIgnoreCase(encoding))
/*     */     {
/* 115 */       return new Utf8Reader(in); } 
/* 116 */     if ("US-ASCII".equalsIgnoreCase(encoding) || "ASCII".equalsIgnoreCase(encoding))
/*     */     {
/* 118 */       return new AsciiReader(in); } 
/* 119 */     if ("ISO-8859-1".equalsIgnoreCase(encoding)) {
/* 120 */       return new Iso8859_1Reader(in);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     return new InputStreamReader(in, std2java(encoding));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   private static final Map charsets = new HashMap<Object, Object>(31);
/*     */   
/*     */   static {
/* 140 */     charsets.put("UTF-16", "Unicode");
/* 141 */     charsets.put("ISO-10646-UCS-2", "Unicode");
/*     */ 
/*     */ 
/*     */     
/* 145 */     charsets.put("EBCDIC-CP-US", "cp037");
/* 146 */     charsets.put("EBCDIC-CP-CA", "cp037");
/* 147 */     charsets.put("EBCDIC-CP-NL", "cp037");
/* 148 */     charsets.put("EBCDIC-CP-WT", "cp037");
/*     */     
/* 150 */     charsets.put("EBCDIC-CP-DK", "cp277");
/* 151 */     charsets.put("EBCDIC-CP-NO", "cp277");
/* 152 */     charsets.put("EBCDIC-CP-FI", "cp278");
/* 153 */     charsets.put("EBCDIC-CP-SE", "cp278");
/*     */     
/* 155 */     charsets.put("EBCDIC-CP-IT", "cp280");
/* 156 */     charsets.put("EBCDIC-CP-ES", "cp284");
/* 157 */     charsets.put("EBCDIC-CP-GB", "cp285");
/* 158 */     charsets.put("EBCDIC-CP-FR", "cp297");
/*     */     
/* 160 */     charsets.put("EBCDIC-CP-AR1", "cp420");
/* 161 */     charsets.put("EBCDIC-CP-HE", "cp424");
/* 162 */     charsets.put("EBCDIC-CP-BE", "cp500");
/* 163 */     charsets.put("EBCDIC-CP-CH", "cp500");
/*     */     
/* 165 */     charsets.put("EBCDIC-CP-ROECE", "cp870");
/* 166 */     charsets.put("EBCDIC-CP-YU", "cp870");
/* 167 */     charsets.put("EBCDIC-CP-IS", "cp871");
/* 168 */     charsets.put("EBCDIC-CP-AR2", "cp918");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String std2java(String encoding) {
/* 178 */     String temp = encoding.toUpperCase();
/* 179 */     temp = (String)charsets.get(temp);
/* 180 */     return (temp != null) ? temp : encoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 185 */     return this.assignedEncoding;
/*     */   }
/*     */   
/*     */   private XmlReader(InputStream stream) throws IOException {
/* 189 */     super(stream);
/*     */     
/*     */     PushbackInputStream pb;
/* 192 */     int len = 0;
/*     */     
/* 194 */     if (stream instanceof PushbackInputStream) {
/* 195 */       pb = (PushbackInputStream)stream;
/*     */     } else {
/* 197 */       pb = new PushbackInputStream(stream, 512);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     int peekSize = 4;
/* 204 */     byte[] buf = new byte[4];
/* 205 */     while (len < 4) {
/* 206 */       int bytesRead = pb.read(buf, len, 4 - len);
/* 207 */       if (bytesRead != -1) {
/* 208 */         len += bytesRead;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 213 */     if (len != 4) {
/* 214 */       pb.unread(buf, 0, len);
/*     */     } else {
/* 216 */       if (buf[0] == -17 && buf[1] == -69 && buf[2] == -65) {
/* 217 */         setEncoding(pb, "UTF-8");
/* 218 */         pb.unread(buf, 3, 1);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 223 */       pb.unread(buf, 0, 4);
/*     */       
/* 225 */       switch (buf[0] & 0xFF) {
/*     */         
/*     */         case 0:
/* 228 */           if (buf[1] == 60 && buf[2] == 0 && buf[3] == 63) {
/* 229 */             setEncoding(pb, "UnicodeBig");
/*     */             return;
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 60:
/* 236 */           switch (buf[1] & 0xFF) {
/*     */             default:
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case 0:
/* 245 */               if (buf[2] == 63 && buf[3] == 0) {
/* 246 */                 setEncoding(pb, "UnicodeLittle");
/*     */                 return;
/*     */               } 
/*     */               break;
/*     */             
/*     */             case 63:
/*     */               break;
/*     */           } 
/* 254 */           if (buf[2] != 120 || buf[3] != 109) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 260 */           useEncodingDecl(pb, "UTF8");
/*     */           return;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 76:
/* 267 */           if (buf[1] == 111 && (0xFF & buf[2]) == 167 && (0xFF & buf[3]) == 148) {
/*     */ 
/*     */             
/* 270 */             useEncodingDecl(pb, "CP037");
/*     */             return;
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 254:
/* 278 */           if ((buf[1] & 0xFF) != 255)
/*     */             break; 
/* 280 */           setEncoding(pb, "UTF-16");
/*     */           return;
/*     */ 
/*     */         
/*     */         case 255:
/* 285 */           if ((buf[1] & 0xFF) != 254)
/*     */             break; 
/* 287 */           setEncoding(pb, "UTF-16");
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 300 */     setEncoding(pb, "UTF-8");
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
/*     */   private void useEncodingDecl(PushbackInputStream pb, String encoding) throws IOException {
/* 315 */     byte[] buffer = new byte[512];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     int len = pb.read(buffer, 0, buffer.length);
/* 327 */     pb.unread(buffer, 0, len);
/* 328 */     Reader r = new InputStreamReader(new ByteArrayInputStream(buffer, 4, len), encoding);
/*     */ 
/*     */ 
/*     */     
/*     */     int c;
/*     */ 
/*     */     
/* 335 */     if ((c = r.read()) != 108) {
/* 336 */       setEncoding(pb, "UTF-8");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     StringBuffer buf = new StringBuffer();
/* 350 */     StringBuffer keyBuf = null;
/* 351 */     String key = null;
/* 352 */     boolean sawEq = false;
/* 353 */     char quoteChar = Character.MIN_VALUE;
/* 354 */     boolean sawQuestion = false;
/*     */     int i;
/* 356 */     label81: for (i = 0; i < 507 && (
/* 357 */       c = r.read()) != -1; i++) {
/*     */ 
/*     */ 
/*     */       
/* 361 */       if (c == 32 || c == 9 || c == 10 || c == 13) {
/*     */         continue;
/*     */       }
/*     */       
/* 365 */       if (i == 0) {
/*     */         break;
/*     */       }
/*     */       
/* 369 */       if (c == 63) {
/* 370 */         sawQuestion = true;
/* 371 */       } else if (sawQuestion) {
/* 372 */         if (c == 62)
/*     */           break; 
/* 374 */         sawQuestion = false;
/*     */       } 
/*     */ 
/*     */       
/* 378 */       if (key == null || !sawEq) {
/* 379 */         if (keyBuf == null) {
/* 380 */           if (!Character.isWhitespace((char)c))
/*     */           
/* 382 */           { keyBuf = buf;
/* 383 */             buf.setLength(0);
/* 384 */             buf.append((char)c);
/* 385 */             sawEq = false; } 
/* 386 */         } else if (Character.isWhitespace((char)c)) {
/* 387 */           key = keyBuf.toString();
/* 388 */         } else if (c == 61) {
/* 389 */           if (key == null)
/* 390 */             key = keyBuf.toString(); 
/* 391 */           sawEq = true;
/* 392 */           keyBuf = null;
/* 393 */           quoteChar = Character.MIN_VALUE;
/*     */         } else {
/* 395 */           keyBuf.append((char)c);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 400 */       if (Character.isWhitespace((char)c))
/*     */         continue; 
/* 402 */       if (c == 34 || c == 39) {
/* 403 */         if (quoteChar == '\000') {
/* 404 */           quoteChar = (char)c;
/* 405 */           buf.setLength(0); continue;
/*     */         } 
/* 407 */         if (c == quoteChar) {
/* 408 */           if ("encoding".equals(key)) {
/* 409 */             this.assignedEncoding = buf.toString();
/*     */ 
/*     */             
/* 412 */             for (i = 0; i < this.assignedEncoding.length(); i++) {
/* 413 */               c = this.assignedEncoding.charAt(i);
/* 414 */               if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
/*     */                 continue;
/*     */               }
/* 417 */               if (i == 0)
/*     */                 break label81; 
/* 419 */               if (i > 0) { if (c == 45 || (c >= 48 && c <= 57) || c == 46 || c == 95) {
/*     */                   continue;
/*     */                 }
/*     */                 
/*     */                 break label81; }
/*     */ 
/*     */               
/*     */               break label81;
/*     */             } 
/*     */             
/* 429 */             setEncoding(pb, this.assignedEncoding);
/*     */             
/*     */             return;
/*     */           } 
/* 433 */           key = null;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 438 */       buf.append((char)c);
/*     */       continue;
/*     */     } 
/* 441 */     setEncoding(pb, "UTF-8");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setEncoding(InputStream stream, String encoding) throws IOException {
/* 446 */     this.assignedEncoding = encoding;
/* 447 */     this.in = createReader(stream, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(char[] buf, int off, int len) throws IOException {
/* 456 */     if (this.closed)
/* 457 */       return -1; 
/* 458 */     int val = this.in.read(buf, off, len);
/* 459 */     if (val == -1)
/* 460 */       close(); 
/* 461 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 470 */     if (this.closed)
/* 471 */       throw new IOException("closed"); 
/* 472 */     int val = this.in.read();
/* 473 */     if (val == -1)
/* 474 */       close(); 
/* 475 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 482 */     return (this.in == null) ? false : this.in.markSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int value) throws IOException {
/* 491 */     if (this.in != null) {
/* 492 */       this.in.mark(value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 499 */     if (this.in != null) {
/* 500 */       this.in.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long value) throws IOException {
/* 507 */     return (this.in == null) ? 0L : this.in.skip(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ready() throws IOException {
/* 514 */     return (this.in == null) ? false : this.in.ready();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 521 */     if (this.closed)
/*     */       return; 
/* 523 */     this.in.close();
/* 524 */     this.in = null;
/* 525 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class BaseReader
/*     */     extends Reader
/*     */   {
/*     */     protected InputStream instream;
/*     */     
/*     */     protected byte[] buffer;
/*     */     
/*     */     protected int start;
/*     */     
/*     */     protected int finish;
/*     */ 
/*     */     
/*     */     BaseReader(InputStream stream) {
/* 542 */       super(stream);
/*     */       
/* 544 */       this.instream = stream;
/* 545 */       this.buffer = new byte[2048];
/*     */     }
/*     */     
/*     */     public boolean ready() throws IOException {
/* 549 */       return (this.instream == null || this.finish - this.start > 0 || this.instream.available() != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 556 */       if (this.instream != null) {
/* 557 */         this.instream.close();
/* 558 */         this.start = this.finish = 0;
/* 559 */         this.buffer = null;
/* 560 */         this.instream = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Utf8Reader
/*     */     extends BaseReader
/*     */   {
/*     */     private char nextChar;
/*     */ 
/*     */ 
/*     */     
/*     */     Utf8Reader(InputStream stream) {
/* 576 */       super(stream);
/*     */     }
/*     */     
/*     */     public int read(char[] buf, int offset, int len) throws IOException {
/* 580 */       int i = 0, c = 0;
/*     */       
/* 582 */       if (len <= 0) {
/* 583 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 587 */       if (offset + len > buf.length || offset < 0) {
/* 588 */         throw new ArrayIndexOutOfBoundsException();
/*     */       }
/*     */       
/* 591 */       if (this.nextChar != '\000') {
/* 592 */         buf[offset + i++] = this.nextChar;
/* 593 */         this.nextChar = Character.MIN_VALUE;
/*     */       } 
/*     */       
/* 596 */       while (i < len) {
/*     */         
/* 598 */         if (this.finish <= this.start) {
/* 599 */           if (this.instream == null) {
/* 600 */             c = -1;
/*     */             break;
/*     */           } 
/* 603 */           this.start = 0;
/* 604 */           this.finish = this.instream.read(this.buffer, 0, this.buffer.length);
/* 605 */           if (this.finish <= 0) {
/* 606 */             close();
/* 607 */             c = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 624 */         c = this.buffer[this.start] & 0xFF;
/* 625 */         if ((c & 0x80) == 0) {
/*     */           
/* 627 */           this.start++;
/* 628 */           buf[offset + i++] = (char)c;
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */         
/* 636 */         int off = this.start;
/*     */ 
/*     */         
/*     */         try {
/* 640 */           if ((this.buffer[off] & 0xE0) == 192) {
/* 641 */             c = (this.buffer[off++] & 0x1F) << 6;
/* 642 */             c += this.buffer[off++] & 0x3F;
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 647 */           else if ((this.buffer[off] & 0xF0) == 224) {
/* 648 */             c = (this.buffer[off++] & 0xF) << 12;
/* 649 */             c += (this.buffer[off++] & 0x3F) << 6;
/* 650 */             c += this.buffer[off++] & 0x3F;
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 655 */           else if ((this.buffer[off] & 0xF8) == 240) {
/* 656 */             c = (this.buffer[off++] & 0x7) << 18;
/* 657 */             c += (this.buffer[off++] & 0x3F) << 12;
/* 658 */             c += (this.buffer[off++] & 0x3F) << 6;
/* 659 */             c += this.buffer[off++] & 0x3F;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 664 */             if (c > 1114111) {
/* 665 */               throw new CharConversionException("UTF-8 encoding of character 0x00" + Integer.toHexString(c) + " can't be converted to Unicode.");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 670 */             if (c > 65535)
/*     */             {
/* 672 */               c -= 65536;
/* 673 */               this.nextChar = (char)(56320 + (c & 0x3FF));
/* 674 */               c = 55296 + (c >> 10);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 679 */             throw new CharConversionException("Unconvertible UTF-8 character beginning with 0x" + Integer.toHexString(this.buffer[this.start] & 0xFF));
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 684 */         catch (ArrayIndexOutOfBoundsException e) {
/*     */           
/* 686 */           c = 0;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 695 */         if (off > this.finish) {
/* 696 */           System.arraycopy(this.buffer, this.start, this.buffer, 0, this.finish - this.start);
/* 697 */           this.finish -= this.start;
/* 698 */           this.start = 0;
/* 699 */           off = this.instream.read(this.buffer, this.finish, this.buffer.length - this.finish);
/* 700 */           if (off < 0) {
/* 701 */             close();
/* 702 */             throw new CharConversionException("Partial UTF-8 char");
/*     */           } 
/* 704 */           this.finish += off;
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */         
/* 711 */         this.start++; for (; this.start < off; this.start++) {
/* 712 */           if ((this.buffer[this.start] & 0xC0) != 128) {
/* 713 */             close();
/* 714 */             throw new CharConversionException("Malformed UTF-8 char -- is an XML encoding declaration missing?");
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 723 */         buf[offset + i++] = (char)c;
/* 724 */         if (this.nextChar != '\000' && i < len) {
/* 725 */           buf[offset + i++] = this.nextChar;
/* 726 */           this.nextChar = Character.MIN_VALUE;
/*     */         } 
/*     */       } 
/* 729 */       if (i > 0)
/* 730 */         return i; 
/* 731 */       return (c == -1) ? -1 : 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class AsciiReader
/*     */     extends BaseReader
/*     */   {
/*     */     AsciiReader(InputStream in) {
/* 743 */       super(in);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(char[] buf, int offset, int len) throws IOException {
/* 749 */       if (this.instream == null) {
/* 750 */         return -1;
/*     */       }
/*     */ 
/*     */       
/* 754 */       if (offset + len > buf.length || offset < 0)
/* 755 */         throw new ArrayIndexOutOfBoundsException(); 
/*     */       int i;
/* 757 */       for (i = 0; i < len; i++) {
/* 758 */         if (this.start >= this.finish) {
/* 759 */           this.start = 0;
/* 760 */           this.finish = this.instream.read(this.buffer, 0, this.buffer.length);
/* 761 */           if (this.finish <= 0) {
/* 762 */             if (this.finish <= 0)
/* 763 */               close(); 
/*     */             break;
/*     */           } 
/*     */         } 
/* 767 */         int c = this.buffer[this.start++];
/* 768 */         if ((c & 0x80) != 0) {
/* 769 */           throw new CharConversionException("Illegal ASCII character, 0x" + Integer.toHexString(c & 0xFF));
/*     */         }
/*     */         
/* 772 */         buf[offset + i] = (char)c;
/*     */       } 
/* 774 */       if (i == 0 && this.finish <= 0)
/* 775 */         return -1; 
/* 776 */       return i;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Iso8859_1Reader extends BaseReader {
/*     */     Iso8859_1Reader(InputStream in) {
/* 782 */       super(in);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(char[] buf, int offset, int len) throws IOException {
/* 788 */       if (this.instream == null) {
/* 789 */         return -1;
/*     */       }
/*     */ 
/*     */       
/* 793 */       if (offset + len > buf.length || offset < 0)
/* 794 */         throw new ArrayIndexOutOfBoundsException(); 
/*     */       int i;
/* 796 */       for (i = 0; i < len; i++) {
/* 797 */         if (this.start >= this.finish) {
/* 798 */           this.start = 0;
/* 799 */           this.finish = this.instream.read(this.buffer, 0, this.buffer.length);
/* 800 */           if (this.finish <= 0) {
/* 801 */             if (this.finish <= 0)
/* 802 */               close(); 
/*     */             break;
/*     */           } 
/*     */         } 
/* 806 */         buf[offset + i] = (char)(0xFF & this.buffer[this.start++]);
/*     */       } 
/* 808 */       if (i == 0 && this.finish <= 0)
/* 809 */         return -1; 
/* 810 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\XmlReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */