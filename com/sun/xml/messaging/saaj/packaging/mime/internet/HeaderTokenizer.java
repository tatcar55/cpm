/*     */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HeaderTokenizer
/*     */ {
/*     */   private String string;
/*     */   private boolean skipComments;
/*     */   private String delimiters;
/*     */   private int currentPos;
/*     */   private int maxPos;
/*     */   private int nextPos;
/*     */   private int peekPos;
/*     */   public static final String RFC822 = "()<>@,;:\\\"\t .[]";
/*     */   public static final String MIME = "()<>@,;:\\\"\t []/?=";
/*     */   
/*     */   public static class Token
/*     */   {
/*     */     private int type;
/*     */     private String value;
/*     */     public static final int ATOM = -1;
/*     */     public static final int QUOTEDSTRING = -2;
/*     */     public static final int COMMENT = -3;
/*     */     public static final int EOF = -4;
/*     */     
/*     */     public Token(int type, String value) {
/* 102 */       this.type = type;
/* 103 */       this.value = value;
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
/* 123 */       return this.type;
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
/* 135 */       return this.value;
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
/* 158 */   private static final Token EOFToken = new Token(-4, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderTokenizer(String header, String delimiters, boolean skipComments) {
/* 173 */     this.string = (header == null) ? "" : header;
/* 174 */     this.skipComments = skipComments;
/* 175 */     this.delimiters = delimiters;
/* 176 */     this.currentPos = this.nextPos = this.peekPos = 0;
/* 177 */     this.maxPos = this.string.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderTokenizer(String header, String delimiters) {
/* 187 */     this(header, delimiters, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderTokenizer(String header) {
/* 196 */     this(header, "()<>@,;:\\\"\t .[]");
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
/*     */   public Token next() throws ParseException {
/* 211 */     this.currentPos = this.nextPos;
/* 212 */     Token tk = getNext();
/* 213 */     this.nextPos = this.peekPos = this.currentPos;
/* 214 */     return tk;
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
/*     */   public Token peek() throws ParseException {
/* 229 */     this.currentPos = this.peekPos;
/* 230 */     Token tk = getNext();
/* 231 */     this.peekPos = this.currentPos;
/* 232 */     return tk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRemainder() {
/* 242 */     return this.string.substring(this.nextPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Token getNext() throws ParseException {
/* 252 */     if (this.currentPos >= this.maxPos) {
/* 253 */       return EOFToken;
/*     */     }
/*     */     
/* 256 */     if (skipWhiteSpace() == -4) {
/* 257 */       return EOFToken;
/*     */     }
/*     */ 
/*     */     
/* 261 */     boolean filter = false;
/*     */     
/* 263 */     char c = this.string.charAt(this.currentPos);
/*     */ 
/*     */ 
/*     */     
/* 267 */     while (c == '(') {
/*     */ 
/*     */       
/* 270 */       int i = ++this.currentPos, nesting = 1;
/* 271 */       for (; nesting > 0 && this.currentPos < this.maxPos; 
/* 272 */         this.currentPos++) {
/* 273 */         c = this.string.charAt(this.currentPos);
/* 274 */         if (c == '\\') {
/* 275 */           this.currentPos++;
/* 276 */           filter = true;
/* 277 */         } else if (c == '\r') {
/* 278 */           filter = true;
/* 279 */         } else if (c == '(') {
/* 280 */           nesting++;
/* 281 */         } else if (c == ')') {
/* 282 */           nesting--;
/*     */         } 
/* 284 */       }  if (nesting != 0) {
/* 285 */         throw new ParseException("Unbalanced comments");
/*     */       }
/* 287 */       if (!this.skipComments) {
/*     */         String s;
/*     */ 
/*     */         
/* 291 */         if (filter) {
/* 292 */           s = filterToken(this.string, i, this.currentPos - 1);
/*     */         } else {
/* 294 */           s = this.string.substring(i, this.currentPos - 1);
/*     */         } 
/* 296 */         return new Token(-3, s);
/*     */       } 
/*     */ 
/*     */       
/* 300 */       if (skipWhiteSpace() == -4)
/* 301 */         return EOFToken; 
/* 302 */       c = this.string.charAt(this.currentPos);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 307 */     if (c == '"') {
/* 308 */       for (int i = ++this.currentPos; this.currentPos < this.maxPos; this.currentPos++) {
/* 309 */         c = this.string.charAt(this.currentPos);
/* 310 */         if (c == '\\') {
/* 311 */           this.currentPos++;
/* 312 */           filter = true;
/* 313 */         } else if (c == '\r') {
/* 314 */           filter = true;
/* 315 */         } else if (c == '"') {
/* 316 */           String s; this.currentPos++;
/*     */ 
/*     */           
/* 319 */           if (filter) {
/* 320 */             s = filterToken(this.string, i, this.currentPos - 1);
/*     */           } else {
/* 322 */             s = this.string.substring(i, this.currentPos - 1);
/*     */           } 
/* 324 */           return new Token(-2, s);
/*     */         } 
/*     */       } 
/* 327 */       throw new ParseException("Unbalanced quoted string");
/*     */     } 
/*     */ 
/*     */     
/* 331 */     if (c < ' ' || c >= '' || this.delimiters.indexOf(c) >= 0) {
/* 332 */       this.currentPos++;
/* 333 */       char[] ch = new char[1];
/* 334 */       ch[0] = c;
/* 335 */       return new Token(c, new String(ch));
/*     */     } 
/*     */     
/*     */     int start;
/* 339 */     for (start = this.currentPos; this.currentPos < this.maxPos; this.currentPos++) {
/* 340 */       c = this.string.charAt(this.currentPos);
/*     */ 
/*     */       
/* 343 */       if (c < ' ' || c >= '' || c == '(' || c == ' ' || c == '"' || this.delimiters.indexOf(c) >= 0) {
/*     */         break;
/*     */       }
/*     */     } 
/* 347 */     return new Token(-1, this.string.substring(start, this.currentPos));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int skipWhiteSpace() {
/* 353 */     for (; this.currentPos < this.maxPos; this.currentPos++) {
/* 354 */       char c; if ((c = this.string.charAt(this.currentPos)) != ' ' && c != '\t' && c != '\r' && c != '\n')
/*     */       {
/* 356 */         return this.currentPos; } 
/* 357 */     }  return -4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String filterToken(String s, int start, int end) {
/* 364 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 366 */     boolean gotEscape = false;
/* 367 */     boolean gotCR = false;
/*     */     
/* 369 */     for (int i = start; i < end; i++) {
/* 370 */       char c = s.charAt(i);
/* 371 */       if (c == '\n' && gotCR) {
/*     */ 
/*     */         
/* 374 */         gotCR = false;
/*     */       }
/*     */       else {
/*     */         
/* 378 */         gotCR = false;
/* 379 */         if (!gotEscape) {
/*     */           
/* 381 */           if (c == '\\') {
/* 382 */             gotEscape = true;
/* 383 */           } else if (c == '\r') {
/* 384 */             gotCR = true;
/*     */           } else {
/* 386 */             sb.append(c);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 391 */           sb.append(c);
/* 392 */           gotEscape = false;
/*     */         } 
/*     */       } 
/* 395 */     }  return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\HeaderTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */