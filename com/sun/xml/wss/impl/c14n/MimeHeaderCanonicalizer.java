/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.mail.internet.MimeUtility;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MimeHeaderCanonicalizer
/*     */   extends Canonicalizer
/*     */ {
/*     */   private static final String _WS = " ";
/*     */   private static final String _SC = ";";
/*     */   private static final String _BS = "\\";
/*     */   private static final String _FS = "/";
/*     */   private static final String _EQ = "=";
/*     */   private static final String _CL = ":";
/*     */   private static final String _OC = "(";
/*     */   private static final String _CC = ")";
/*     */   private static final String _QT = "\"";
/*     */   private static final String _SQ = "'";
/*     */   private static final String _HT = "\t";
/*     */   private static final String _AX = "*";
/*     */   private static final String _PC = "%";
/*     */   private static final String _CRLF = "\r\n";
/*     */   
/*     */   public byte[] canonicalize(byte[] input) throws XWSSecurityException {
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream canonicalize(InputStream input, OutputStream outputStream) throws TransformException {
/*  72 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] _canonicalize(Iterator mimeHeaders) throws XWSSecurityException {
/*  77 */     String _mh = "";
/*  78 */     List mimeHeaderList = new ArrayList();
/*  79 */     while (mimeHeaders.hasNext()) {
/*  80 */       mimeHeaderList.add(mimeHeaders.next());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     String cDescription = getMatchingHeader(mimeHeaderList, "Content-Description");
/*  88 */     if (cDescription != null) {
/*  89 */       _mh = _mh + "Content-Description:";
/*  90 */       _mh = _mh + uncomment(rfc2047decode(unfold(cDescription)));
/*  91 */       _mh = _mh + "\r\n";
/*     */     } 
/*     */ 
/*     */     
/*  95 */     String cDisposition = getMatchingHeader(mimeHeaderList, "Content-Disposition");
/*  96 */     if (cDisposition != null) {
/*  97 */       _mh = _mh + "Content-Disposition:";
/*  98 */       _mh = _mh + canonicalizeHeaderLine(uncomment(unfold(cDisposition)), true);
/*  99 */       _mh = _mh + "\r\n";
/*     */     } 
/*     */ 
/*     */     
/* 103 */     String cId = getMatchingHeader(mimeHeaderList, "Content-ID");
/* 104 */     if (cId != null) {
/* 105 */       _mh = _mh + "Content-ID:";
/* 106 */       _mh = _mh + unfoldWS(uncomment(unfold(cId))).trim();
/* 107 */       _mh = _mh + "\r\n";
/*     */     } 
/*     */ 
/*     */     
/* 111 */     String cLocation = getMatchingHeader(mimeHeaderList, "Content-Location");
/* 112 */     if (cLocation != null) {
/* 113 */       _mh = _mh + "Content-Location:";
/* 114 */       _mh = _mh + unfoldWS(uncomment(unfold(cLocation))).trim();
/* 115 */       _mh = _mh + "\r\n";
/*     */     } 
/*     */ 
/*     */     
/* 119 */     String cType = getMatchingHeader(mimeHeaderList, "Content-Type");
/* 120 */     cType = (cType == null) ? "text/plain; charset=us-ascii" : cType;
/*     */     
/* 122 */     _mh = _mh + "Content-Type:";
/* 123 */     _mh = _mh + canonicalizeHeaderLine(uncomment(unfold(cType)), true);
/* 124 */     _mh = _mh + "\r\n";
/*     */     
/* 126 */     _mh = _mh + "\r\n";
/*     */     
/* 128 */     byte[] b = null;
/*     */     try {
/* 130 */       b = _mh.getBytes("UTF-8");
/* 131 */     } catch (Exception e) {
/*     */       
/* 133 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 136 */     return b;
/*     */   }
/*     */   
/*     */   private String getMatchingHeader(List<MimeHeader> mimeHeaders, String key) throws XWSSecurityException {
/* 140 */     String header_line = null;
/*     */     try {
/* 142 */       for (int i = 0; i < mimeHeaders.size(); i++) {
/* 143 */         MimeHeader mhr = mimeHeaders.get(i);
/*     */         
/* 145 */         if (mhr.getName().equalsIgnoreCase(key)) {
/* 146 */           header_line = mhr.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 157 */     } catch (Exception npe) {
/*     */       
/* 159 */       throw new XWSSecurityException("Failed to locate MIME Header, " + key);
/*     */     } 
/* 161 */     return header_line;
/*     */   }
/*     */   
/*     */   private String unfold(String input) {
/* 165 */     if (input.charAt(0) == "\"".charAt(0) || input.charAt(input.length() - 1) == "\"".charAt(0)) return input;
/*     */ 
/*     */     
/* 168 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 170 */     for (int i = 0; i < input.length(); i++) {
/* 171 */       char c = input.charAt(i);
/* 172 */       if (c == "\r\n".charAt(0) && i != input.length() - 1 && 
/* 173 */         input.charAt(i + 1) == "\r\n".charAt(1)) {
/* 174 */         i++;
/*     */       } else {
/*     */         
/* 177 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 180 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String rfc2047decode(String input) throws XWSSecurityException {
/* 184 */     if (input.charAt(0) == "\"".charAt(0) || input.charAt(input.length() - 1) == "\"".charAt(0)) return input;
/*     */     
/* 186 */     String decodedText = null;
/*     */     try {
/* 188 */       decodedText = MimeUtility.decodeText(input);
/* 189 */     } catch (Exception e) {
/*     */       
/* 191 */       throw new XWSSecurityException(e);
/*     */     } 
/* 193 */     return decodedText;
/*     */   }
/*     */   
/*     */   private String unfoldWS(String input) {
/* 197 */     if (input.charAt(0) == "\"".charAt(0) || input.charAt(input.length() - 1) == "\"".charAt(0)) return input;
/*     */     
/* 199 */     StringBuffer sb = new StringBuffer();
/* 200 */     for (int i = 0; i < input.length(); i++) {
/* 201 */       if (input.charAt(i) == " ".charAt(0) || input.charAt(i) == "\t".charAt(0)) {
/* 202 */         sb.append(" ".charAt(0));
/* 203 */         for (; ++i != input.length() - 1; i++) {
/* 204 */           if (input.charAt(i) != " ".charAt(0) && input.charAt(i) != "\t".charAt(0)) {
/* 205 */             sb.append(input.charAt(i)); break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 209 */         sb.append(input.charAt(i));
/*     */       } 
/*     */     } 
/* 212 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String uncomment(String input) {
/* 216 */     if (input.charAt(0) == "\"".charAt(0) || input.charAt(input.length() - 1) == "\"".charAt(0)) return input;
/*     */     
/* 218 */     for (int oc = 0; oc < input.length() && (
/* 219 */       oc = input.indexOf("(")) != -1; oc++) {
/*     */ 
/*     */ 
/*     */       
/* 223 */       int offset = input.substring(oc).indexOf(")");
/* 224 */       if (offset == -1) {
/*     */         break;
/*     */       }
/*     */       
/* 228 */       int cc = oc + offset;
/*     */       
/* 230 */       String fs = (oc != 0) ? input.substring(0, oc) : "";
/* 231 */       String bs = input.substring(cc + 1);
/*     */       
/* 233 */       if (offset == 1) {
/*     */         
/* 235 */         input = fs + " " + bs;
/*     */       
/*     */       }
/* 238 */       else if (input.substring(oc + 1, cc).indexOf("(") == -1) {
/*     */         
/* 240 */         input = fs + " " + bs;
/*     */       } else {
/*     */         
/* 243 */         input = fs + "(" + uncomment(input.substring(oc + 1, cc + 1)) + bs;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 249 */     return input;
/*     */   }
/*     */   
/*     */   private String canonicalizeHeaderLine(String input, boolean applyStep10SwaDraft13) throws XWSSecurityException {
/* 253 */     int _sc = input.indexOf(";");
/* 254 */     if (_sc <= 0 || _sc == input.length() - 1) return input;
/*     */ 
/*     */     
/* 257 */     String _fs = input.substring(0, _sc).toLowerCase();
/* 258 */     if (applyStep10SwaDraft13) _fs = quote(_fs, false);
/*     */ 
/*     */     
/* 261 */     String size = null;
/* 262 */     String type = null;
/* 263 */     String charset = null;
/* 264 */     String padding = null;
/* 265 */     String filename = null;
/* 266 */     String read_date = null;
/* 267 */     String creation_date = null;
/* 268 */     String modification_date = null;
/*     */     
/* 270 */     String decoded = null;
/*     */     
/*     */     try {
/* 273 */       decoded = rfc2184decode(input.substring(_sc + 1));
/* 274 */     } catch (Exception e) {
/*     */       
/* 276 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 279 */     StringTokenizer strnzr = new StringTokenizer(decoded, ";");
/* 280 */     while (strnzr.hasMoreElements()) {
/* 281 */       String param = strnzr.nextToken();
/*     */       
/* 283 */       String pname = param.substring(0, param.indexOf("="));
/* 284 */       String value = param.substring(param.indexOf("=") + 1);
/*     */       
/* 286 */       if (pname.equalsIgnoreCase("type")) {
/* 287 */         type = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 289 */       if (pname.equalsIgnoreCase("padding")) {
/* 290 */         padding = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 292 */       if (pname.equalsIgnoreCase("charset")) {
/* 293 */         charset = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 295 */       if (pname.equalsIgnoreCase("filename")) {
/* 296 */         filename = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 298 */       if (pname.equalsIgnoreCase("creation-date")) {
/* 299 */         creation_date = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 301 */       if (pname.equalsIgnoreCase("modification-date")) {
/* 302 */         modification_date = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 304 */       if (pname.equalsIgnoreCase("read-date")) {
/* 305 */         read_date = quote(value.toLowerCase(), true); continue;
/*     */       } 
/* 307 */       if (pname.equalsIgnoreCase("size")) {
/* 308 */         size = quote(value.toLowerCase(), true);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 313 */     if (charset != null) {
/* 314 */       _fs = _fs + ";charset=" + charset;
/*     */     }
/* 316 */     if (creation_date != null) {
/* 317 */       _fs = _fs + ";creation-date=" + creation_date;
/*     */     }
/* 319 */     if (filename != null) {
/* 320 */       _fs = _fs + ";filename=" + filename;
/*     */     }
/* 322 */     if (modification_date != null) {
/* 323 */       _fs = _fs + ";modification-date=" + modification_date;
/*     */     }
/* 325 */     if (padding != null) {
/* 326 */       _fs = _fs + ";padding=" + padding;
/*     */     }
/* 328 */     if (read_date != null) {
/* 329 */       _fs = _fs + ";read-date=" + read_date;
/*     */     }
/* 331 */     if (size != null) {
/* 332 */       _fs = _fs + ";size=" + size;
/*     */     }
/* 334 */     if (type != null) {
/* 335 */       _fs = _fs + ";type=" + type;
/*     */     }
/* 337 */     return _fs;
/*     */   }
/*     */   
/*     */   private Vector makeParameterVector(String input) {
/* 341 */     Vector<Object> v = new Vector();
/*     */     
/* 343 */     StringTokenizer nzr = new StringTokenizer(input, ";");
/* 344 */     while (nzr.hasMoreTokens()) {
/* 345 */       v.add(nzr.nextToken());
/*     */     }
/*     */     
/* 348 */     return v;
/*     */   }
/*     */   
/*     */   private String _rfc2184decode(String input) throws Exception {
/* 352 */     StringTokenizer nzr = new StringTokenizer(input, "'");
/*     */     
/* 354 */     if (nzr.countTokens() != 3)
/*     */     {
/* 356 */       throw new XWSSecurityException("Malformed RFC2184 encoded parameter");
/*     */     }
/*     */     
/* 359 */     String charset = nzr.nextToken();
/* 360 */     String language = nzr.nextToken();
/*     */ 
/*     */     
/* 363 */     for (int i = 0; i < input.length(); i++) {
/* 364 */       if (input.charAt(i) == "%".charAt(0)) {
/* 365 */         input = input.substring(0, i) + _decodeHexadecimal(input.substring(i + 1, i + 3), charset, language) + input.substring(i + 3);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 371 */     return input;
/*     */   }
/*     */ 
/*     */   
/*     */   private String _decodeHexadecimal(String input, String charset, String language) throws Exception {
/* 376 */     byte b = Byte.decode("0x" + input.toUpperCase()).byteValue();
/* 377 */     return new String(new byte[] { b }, MimeUtility.javaCharset(charset));
/*     */   }
/*     */   
/*     */   private String rfc2184decode(String input) throws Exception {
/* 381 */     int index = -1;
/* 382 */     String pname = "";
/* 383 */     String value = "";
/* 384 */     String decoded = "";
/*     */     
/* 386 */     Vector<String> v = makeParameterVector(input);
/* 387 */     for (int i = 0; i < v.size(); i++) {
/* 388 */       String token = v.elementAt(i);
/*     */       
/* 390 */       int idx = token.indexOf("=");
/* 391 */       String pn = token.substring(0, idx).trim();
/* 392 */       String pv = token.substring(idx + 1).trim();
/*     */       
/* 394 */       if (pn.endsWith("*")) {
/*     */         
/* 396 */         pn = pn.substring(0, pn.length() - 1);
/* 397 */         pv = _rfc2184decode(pv);
/*     */         
/* 399 */         token = pn + "=" + pv;
/*     */         
/* 401 */         v.setElementAt(token, i);
/*     */         
/* 403 */         i--;
/*     */       } else {
/*     */         
/* 406 */         int ix = pn.indexOf("*");
/*     */         
/* 408 */         if (ix == -1) {
/*     */           
/* 410 */           if (!pname.equals("")) {
/* 411 */             decoded = decoded + ";" + pname + "=" + pv;
/*     */           }
/*     */           
/* 414 */           decoded = decoded + ";" + pn + "=" + pv;
/*     */ 
/*     */           
/* 417 */           pname = "";
/* 418 */           value = "";
/* 419 */           index = -1;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 424 */           String pn_i = pn.substring(0, ix).trim();
/* 425 */           int curr = (new Integer(pn.substring(ix + 1).trim())).intValue();
/*     */           
/* 427 */           if (pn_i.equalsIgnoreCase(pname)) {
/* 428 */             if (curr != index + 1)
/*     */             {
/* 430 */               throw new XWSSecurityException("Malformed RFC2184 encoded parameter");
/*     */             }
/*     */             
/* 433 */             value = value + concatenate2184decoded(value, pv);
/* 434 */             index++;
/*     */           
/*     */           }
/* 437 */           else if (curr == 0) {
/*     */             
/* 439 */             if (!pname.equals("")) {
/* 440 */               decoded = decoded + ";" + pname + "=" + value;
/*     */             }
/*     */             
/* 443 */             pname = pn_i;
/* 444 */             value = pv;
/* 445 */             index++;
/*     */           } else {
/*     */             
/* 448 */             throw new XWSSecurityException("Malformed RFC2184 encoded parameter");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 456 */     if (!pname.equals("")) {
/* 457 */       decoded = decoded + ";" + pname + "=" + value;
/*     */     }
/* 459 */     return decoded;
/*     */   }
/*     */   
/*     */   private String concatenate2184decoded(String v0, String v1) throws XWSSecurityException {
/* 463 */     boolean v0Quoted = (v0.charAt(0) == "\"".charAt(0) && v0.charAt(v0.length() - 1) == "\"".charAt(0));
/* 464 */     boolean v1Quoted = (v1.charAt(0) == "\"".charAt(0) && v1.charAt(0) == "\"".charAt(0));
/*     */     
/* 466 */     if (v0Quoted != v1Quoted)
/*     */     {
/* 468 */       throw new XWSSecurityException("Malformed RFC2184 encoded parameter");
/*     */     }
/*     */     
/* 471 */     String value = null;
/*     */     
/* 473 */     if (v0Quoted) {
/* 474 */       value = v0.substring(0, v0.length() - 1) + v1.substring(1);
/*     */     } else {
/* 476 */       value = v0 + v1;
/*     */     } 
/*     */     
/* 479 */     return value;
/*     */   }
/*     */   
/*     */   private String quote(String input, boolean force) {
/* 483 */     if (input.charAt(0) == "\"".charAt(0) || input.charAt(input.length() - 1) == "\"".charAt(0)) {
/* 484 */       input = "\"" + unquoteInner(input.substring(1, input.length() - 1)) + "\"";
/*     */     }
/* 486 */     else if (force) {
/* 487 */       input = "\"" + quoteInner(unfoldWS(input).trim()) + "\"";
/*     */     } else {
/* 489 */       input = unfoldWS(input).trim();
/*     */     } 
/* 491 */     return input;
/*     */   }
/*     */   
/*     */   private String unquoteInner(String input) {
/* 495 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 497 */     for (int i = 0; i < input.length(); i++) {
/* 498 */       char c = input.charAt(i);
/*     */       
/* 500 */       if (c == "\\".charAt(0)) {
/* 501 */         if (i == input.length() - 1) {
/* 502 */           sb.append(c);
/* 503 */           sb.append(c);
/*     */           
/*     */           break;
/*     */         } 
/* 507 */         i++;
/* 508 */         char d = input.charAt(i);
/* 509 */         if (d == "\"".charAt(0) || d == "\\".charAt(0)) {
/* 510 */           sb.append(c);
/* 511 */           sb.append(d);
/*     */         } else {
/*     */           
/* 514 */           sb.append(d);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 519 */         if (c == "\"".charAt(0)) {
/* 520 */           sb.append("\\".charAt(0));
/* 521 */           sb.append(c);
/*     */         } 
/*     */         
/* 524 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 527 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String quoteInner(String input) {
/* 531 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 533 */     for (int i = 0; i < input.length(); i++) {
/* 534 */       char c = input.charAt(i);
/*     */       
/* 536 */       if (c == "\\".charAt(0) || c == "\"".charAt(0)) sb.append("\\".charAt(0));
/*     */       
/* 538 */       sb.append(c);
/*     */     } 
/*     */     
/* 541 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\MimeHeaderCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */