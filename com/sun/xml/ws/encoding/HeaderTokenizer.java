/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HeaderTokenizer
/*     */ {
/*     */   private String string;
/*     */   private boolean skipComments;
/*     */   private String delimiters;
/*     */   private int currentPos;
/*     */   private int maxPos;
/*     */   private int nextPos;
/*     */   private int peekPos;
/*     */   private static final String RFC822 = "()<>@,;:\\\"\t .[]";
/*     */   static final String MIME = "()<>@,;:\\\"\t []/?=";
/*     */   
/*     */   static class Token
/*     */   {
/*     */     private int type;
/*     */     private String value;
/*     */     public static final int ATOM = -1;
/*     */     public static final int QUOTEDSTRING = -2;
/*     */     public static final int COMMENT = -3;
/*     */     public static final int EOF = -4;
/*     */     
/*     */     public Token(int type, String value) {
/*  97 */       this.type = type;
/*  98 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getType() {
/* 118 */       return this.type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 130 */       return this.value;
/*     */     }
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
/*     */   
/* 153 */   private static final Token EOFToken = new Token(-4, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HeaderTokenizer(String header, String delimiters, boolean skipComments) {
/* 168 */     this.string = (header == null) ? "" : header;
/* 169 */     this.skipComments = skipComments;
/* 170 */     this.delimiters = delimiters;
/* 171 */     this.currentPos = this.nextPos = this.peekPos = 0;
/* 172 */     this.maxPos = this.string.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HeaderTokenizer(String header, String delimiters) {
/* 182 */     this(header, delimiters, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HeaderTokenizer(String header) {
/* 191 */     this(header, "()<>@,;:\\\"\t .[]");
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
/*     */   Token next() throws WebServiceException {
/* 206 */     this.currentPos = this.nextPos;
/* 207 */     Token tk = getNext();
/* 208 */     this.nextPos = this.peekPos = this.currentPos;
/* 209 */     return tk;
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
/*     */   Token peek() throws WebServiceException {
/* 224 */     this.currentPos = this.peekPos;
/* 225 */     Token tk = getNext();
/* 226 */     this.peekPos = this.currentPos;
/* 227 */     return tk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getRemainder() {
/* 237 */     return this.string.substring(this.nextPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Token getNext() throws WebServiceException {
/* 247 */     if (this.currentPos >= this.maxPos) {
/* 248 */       return EOFToken;
/*     */     }
/*     */     
/* 251 */     if (skipWhiteSpace() == -4) {
/* 252 */       return EOFToken;
/*     */     }
/*     */ 
/*     */     
/* 256 */     boolean filter = false;
/*     */     
/* 258 */     char c = this.string.charAt(this.currentPos);
/*     */ 
/*     */ 
/*     */     
/* 262 */     while (c == '(') {
/*     */ 
/*     */       
/* 265 */       int i = ++this.currentPos, nesting = 1;
/* 266 */       for (; nesting > 0 && this.currentPos < this.maxPos; 
/* 267 */         this.currentPos++) {
/* 268 */         c = this.string.charAt(this.currentPos);
/* 269 */         if (c == '\\') {
/* 270 */           this.currentPos++;
/* 271 */           filter = true;
/* 272 */         } else if (c == '\r') {
/* 273 */           filter = true;
/* 274 */         } else if (c == '(') {
/* 275 */           nesting++;
/* 276 */         } else if (c == ')') {
/* 277 */           nesting--;
/*     */         } 
/* 279 */       }  if (nesting != 0) {
/* 280 */         throw new WebServiceException("Unbalanced comments");
/*     */       }
/* 282 */       if (!this.skipComments) {
/*     */         String s;
/*     */ 
/*     */         
/* 286 */         if (filter) {
/* 287 */           s = filterToken(this.string, i, this.currentPos - 1);
/*     */         } else {
/* 289 */           s = this.string.substring(i, this.currentPos - 1);
/*     */         } 
/* 291 */         return new Token(-3, s);
/*     */       } 
/*     */ 
/*     */       
/* 295 */       if (skipWhiteSpace() == -4)
/* 296 */         return EOFToken; 
/* 297 */       c = this.string.charAt(this.currentPos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 302 */     if (c == '"') {
/* 303 */       for (int i = ++this.currentPos; this.currentPos < this.maxPos; this.currentPos++) {
/* 304 */         c = this.string.charAt(this.currentPos);
/* 305 */         if (c == '\\') {
/* 306 */           this.currentPos++;
/* 307 */           filter = true;
/* 308 */         } else if (c == '\r') {
/* 309 */           filter = true;
/* 310 */         } else if (c == '"') {
/* 311 */           String s; this.currentPos++;
/*     */ 
/*     */           
/* 314 */           if (filter) {
/* 315 */             s = filterToken(this.string, i, this.currentPos - 1);
/*     */           } else {
/* 317 */             s = this.string.substring(i, this.currentPos - 1);
/*     */           } 
/* 319 */           return new Token(-2, s);
/*     */         } 
/*     */       } 
/* 322 */       throw new WebServiceException("Unbalanced quoted string");
/*     */     } 
/*     */ 
/*     */     
/* 326 */     if (c < ' ' || c >= '' || this.delimiters.indexOf(c) >= 0) {
/* 327 */       this.currentPos++;
/* 328 */       char[] ch = new char[1];
/* 329 */       ch[0] = c;
/* 330 */       return new Token(c, new String(ch));
/*     */     } 
/*     */     
/*     */     int start;
/* 334 */     for (start = this.currentPos; this.currentPos < this.maxPos; this.currentPos++) {
/* 335 */       c = this.string.charAt(this.currentPos);
/*     */ 
/*     */       
/* 338 */       if (c < ' ' || c >= '' || c == '(' || c == ' ' || c == '"' || this.delimiters.indexOf(c) >= 0) {
/*     */         break;
/*     */       }
/*     */     } 
/* 342 */     return new Token(-1, this.string.substring(start, this.currentPos));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int skipWhiteSpace() {
/* 348 */     for (; this.currentPos < this.maxPos; this.currentPos++) {
/* 349 */       char c; if ((c = this.string.charAt(this.currentPos)) != ' ' && c != '\t' && c != '\r' && c != '\n')
/*     */       {
/* 351 */         return this.currentPos; } 
/* 352 */     }  return -4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String filterToken(String s, int start, int end) {
/* 359 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 361 */     boolean gotEscape = false;
/* 362 */     boolean gotCR = false;
/*     */     
/* 364 */     for (int i = start; i < end; i++) {
/* 365 */       char c = s.charAt(i);
/* 366 */       if (c == '\n' && gotCR) {
/*     */ 
/*     */         
/* 369 */         gotCR = false;
/*     */       }
/*     */       else {
/*     */         
/* 373 */         gotCR = false;
/* 374 */         if (!gotEscape) {
/*     */           
/* 376 */           if (c == '\\') {
/* 377 */             gotEscape = true;
/* 378 */           } else if (c == '\r') {
/* 379 */             gotCR = true;
/*     */           } else {
/* 381 */             sb.append(c);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 386 */           sb.append(c);
/* 387 */           gotEscape = false;
/*     */         } 
/*     */       } 
/* 390 */     }  return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\HeaderTokenizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */