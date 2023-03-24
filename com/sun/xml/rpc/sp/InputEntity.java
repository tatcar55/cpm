/*      */ package com.sun.xml.rpc.sp;
/*      */ 
/*      */ import java.io.CharConversionException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URL;
/*      */ import java.util.Locale;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.Locator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class InputEntity
/*      */   implements Locator
/*      */ {
/*      */   private int start;
/*      */   private int finish;
/*      */   private char[] buf;
/*   59 */   private int lineNumber = 1;
/*      */ 
/*      */   
/*      */   private boolean returnedFirstHalf = false;
/*      */ 
/*      */   
/*      */   private boolean maybeInCRLF = false;
/*      */ 
/*      */   
/*      */   private String name;
/*      */ 
/*      */   
/*      */   private InputEntity next;
/*      */   
/*      */   private InputSource input;
/*      */   
/*      */   private Reader reader;
/*      */   
/*      */   private boolean isClosed;
/*      */   
/*      */   private Locale locale;
/*      */   
/*      */   private StringBuffer rememberedText;
/*      */   
/*      */   private int startRemember;
/*      */   
/*      */   private boolean isPE;
/*      */   
/*      */   private static final int BUFSIZ = 2049;
/*      */   
/*   89 */   private static final char[] newline = new char[] { '\n' };
/*      */ 
/*      */   
/*   92 */   private char[] cdataBuf = null; private int end;
/*      */   
/*      */   public static InputEntity getInputEntity(Locale l) {
/*   95 */     InputEntity retval = new InputEntity();
/*   96 */     retval.locale = l;
/*   97 */     return retval;
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
/*      */   public boolean isInternal() {
/*  111 */     return (this.reader == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDocument() {
/*  118 */     return (this.next == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isParameterEntity() {
/*  126 */     return this.isPE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  133 */     return this.name;
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
/*      */   public void init(InputSource in, String name, InputEntity stack, boolean isPE) throws ParseException, IOException {
/*  146 */     this.input = in;
/*  147 */     this.isPE = isPE;
/*  148 */     this.reader = in.getCharacterStream();
/*      */     
/*  150 */     if (this.reader == null) {
/*  151 */       InputStream bytes = in.getByteStream();
/*      */       
/*  153 */       if (bytes == null) {
/*  154 */         this.reader = XmlReader.createReader((new URL(in.getSystemId())).openStream());
/*      */       
/*      */       }
/*  157 */       else if (in.getEncoding() != null) {
/*  158 */         this.reader = XmlReader.createReader(in.getByteStream(), in.getEncoding());
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  164 */         this.reader = XmlReader.createReader(in.getByteStream());
/*      */       } 
/*      */     } 
/*  167 */     this.next = stack;
/*  168 */     this.buf = new char[2049];
/*  169 */     this.name = name;
/*  170 */     checkRecursion(stack);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init(char[] b, String name, InputEntity stack, boolean isPE) throws ParseException {
/*  178 */     this.next = stack;
/*  179 */     this.buf = b;
/*  180 */     this.finish = b.length;
/*  181 */     this.name = name;
/*  182 */     this.isPE = isPE;
/*  183 */     checkRecursion(stack);
/*      */   }
/*      */   
/*      */   private void checkRecursion(InputEntity stack) throws ParseException {
/*  187 */     if (stack == null)
/*      */       return; 
/*  189 */     for (stack = stack.next; stack != null; stack = stack.next) {
/*  190 */       if (stack.name != null && stack.name.equals(this.name)) {
/*  191 */         fatal("P-069", new Object[] { this.name });
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public InputEntity pop() throws ParseException, IOException {
/*  197 */     close();
/*  198 */     return this.next;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEOF() throws ParseException, IOException {
/*  205 */     if (this.start >= this.finish) {
/*  206 */       fillbuf();
/*  207 */       return (this.start >= this.finish);
/*      */     } 
/*  209 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/*  218 */     if (this.reader == null)
/*  219 */       return null; 
/*  220 */     if (this.reader instanceof XmlReader) {
/*  221 */       return ((XmlReader)this.reader).getEncoding();
/*      */     }
/*      */ 
/*      */     
/*  225 */     if (this.reader instanceof InputStreamReader)
/*  226 */       return ((InputStreamReader)this.reader).getEncoding(); 
/*  227 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getNameChar() throws ParseException, IOException {
/*  236 */     if (this.finish <= this.start)
/*  237 */       fillbuf(); 
/*  238 */     if (this.finish > this.start) {
/*  239 */       char c = this.buf[this.start++];
/*  240 */       if (XmlChars.isNameChar(c))
/*  241 */         return c; 
/*  242 */       this.start--;
/*      */     } 
/*  244 */     return Character.MIN_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getc() throws ParseException, IOException {
/*  253 */     if (this.finish <= this.start)
/*  254 */       fillbuf(); 
/*  255 */     if (this.finish > this.start) {
/*  256 */       char c = this.buf[this.start++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  262 */       if (this.returnedFirstHalf) {
/*  263 */         if (c >= '?' && c <= '?') {
/*  264 */           this.returnedFirstHalf = false;
/*  265 */           return c;
/*      */         } 
/*  267 */         fatal("P-070", new Object[] { Integer.toHexString(c) });
/*      */       } 
/*  269 */       if ((c >= ' ' && c <= '퟿') || c == '\t' || (c >= '' && c <= '�'))
/*      */       {
/*      */         
/*  272 */         return c;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  278 */       if (c == '\r' && !isInternal()) {
/*  279 */         this.maybeInCRLF = true;
/*  280 */         c = getc();
/*  281 */         if (c != '\n')
/*  282 */           ungetc(); 
/*  283 */         this.maybeInCRLF = false;
/*      */         
/*  285 */         this.lineNumber++;
/*  286 */         return '\n';
/*      */       } 
/*  288 */       if (c == '\n' || c == '\r') {
/*  289 */         if (!isInternal() && !this.maybeInCRLF)
/*  290 */           this.lineNumber++; 
/*  291 */         return c;
/*      */       } 
/*      */ 
/*      */       
/*  295 */       if (c >= '?' && c < '?') {
/*  296 */         this.returnedFirstHalf = true;
/*  297 */         return c;
/*      */       } 
/*      */       
/*  300 */       fatal("P-071", new Object[] { Integer.toHexString(c) });
/*      */     } 
/*  302 */     throw new EndOfInputException();
/*      */   }
/*      */   
/*      */   public boolean peekc(char c) throws ParseException, IOException {
/*  306 */     if (this.finish <= this.start)
/*  307 */       fillbuf(); 
/*  308 */     if (this.finish > this.start) {
/*  309 */       if (this.buf[this.start] == c) {
/*  310 */         this.start++;
/*  311 */         return true;
/*      */       } 
/*  313 */       return false;
/*      */     } 
/*  315 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ungetc() {
/*  322 */     if (this.start == 0)
/*  323 */       throw new InternalError("ungetc"); 
/*  324 */     this.start--;
/*      */     
/*  326 */     if (this.buf[this.start] == '\n' || this.buf[this.start] == '\r') {
/*  327 */       if (!isInternal())
/*  328 */         this.lineNumber--; 
/*  329 */     } else if (this.returnedFirstHalf) {
/*  330 */       this.returnedFirstHalf = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean maybeWhitespace() throws ParseException, IOException {
/*  338 */     boolean isSpace = false;
/*  339 */     boolean sawCR = false;
/*      */ 
/*      */     
/*      */     while (true) {
/*  343 */       if (this.finish <= this.start)
/*  344 */         fillbuf(); 
/*  345 */       if (this.finish <= this.start) {
/*  346 */         return isSpace;
/*      */       }
/*  348 */       char c = this.buf[this.start++];
/*  349 */       if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
/*  350 */         isSpace = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  355 */         if ((c == '\n' || c == '\r') && !isInternal()) {
/*  356 */           if (c != '\n' || !sawCR) {
/*  357 */             this.lineNumber++;
/*  358 */             sawCR = false;
/*      */           } 
/*  360 */           if (c == '\r')
/*  361 */             sawCR = true; 
/*      */         }  continue;
/*      */       }  break;
/*  364 */     }  this.start--;
/*  365 */     return isSpace;
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
/*      */   private InputEntity() {
/*  377 */     this.end = -1;
/*      */   }
/*      */   
/*      */   String getParsedContent(boolean coalescing) throws ParseException, IOException {
/*  381 */     if (!coalescing) {
/*      */ 
/*      */       
/*  384 */       int i = this.start;
/*  385 */       if (parsedContent()) {
/*  386 */         if (this.end == -1)
/*  387 */           this.end = this.start; 
/*  388 */         return new String(this.buf, i, this.start - i);
/*      */       } 
/*  390 */       return null;
/*      */     } 
/*      */     
/*  393 */     int s = this.start;
/*  394 */     StringBuffer content = null;
/*  395 */     while (parsedContent()) {
/*      */       
/*  397 */       if (content == null) {
/*  398 */         content = new StringBuffer();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  404 */       if (this.end == -1) {
/*  405 */         this.end = this.start;
/*      */       }
/*  407 */       if (this.start < s)
/*  408 */         s = 0; 
/*  409 */       content.append(this.buf, s, this.end - s);
/*  410 */       this.end = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  415 */       if (!coalescing || isEOF()) {
/*      */         break;
/*      */       }
/*  418 */       s = this.start;
/*      */     } 
/*  420 */     return (content == null) ? null : content.toString();
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
/*      */   public boolean parsedContent() throws ParseException, IOException {
/*      */     int first;
/*      */     int last;
/*      */     boolean sawContent;
/*  444 */     for (first = last = this.start, sawContent = false;; last++) {
/*      */ 
/*      */       
/*  447 */       if (last >= this.finish) {
/*  448 */         if (last > first) {
/*  449 */           sawContent = true;
/*  450 */           this.start = last;
/*  451 */           return sawContent;
/*      */         } 
/*  453 */         if (isEOF()) {
/*  454 */           return sawContent;
/*      */         }
/*  456 */         first = this.start;
/*  457 */         last = first - 1;
/*      */       }
/*      */       else {
/*      */         
/*  461 */         char c = this.buf[last];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  472 */         if ((c <= ']' || c > '퟿') && (c >= '&' || c < ' ') && (c <= '<' || c >= ']') && (c <= '&' || c >= '<') && c != '\t' && (c < '' || c > '�'))
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  481 */           if (c == '<' || c == '&') {
/*      */             break;
/*      */           }
/*      */           
/*  485 */           if (c == '\n')
/*  486 */           { if (!isInternal()) {
/*  487 */               this.lineNumber++;
/*      */ 
/*      */             
/*      */             }
/*      */             
/*      */              }
/*      */           
/*  494 */           else if (c == '\r')
/*  495 */           { if (!isInternal())
/*      */             {
/*  497 */               sawContent = true;
/*  498 */               this.lineNumber++;
/*  499 */               if (this.finish > last + 1) {
/*  500 */                 if (this.buf[last + 1] == '\n') {
/*  501 */                   last++;
/*  502 */                   this.buf[last - 1] = '\n';
/*  503 */                   this.end = last;
/*      */                 } else {
/*  505 */                   this.buf[last] = '\n';
/*      */                 } 
/*      */               } else {
/*      */                 
/*  509 */                 this.buf[last] = '\n';
/*      */               } 
/*  511 */               first = this.start = last + 1;
/*      */               
/*  513 */               return sawContent;
/*      */             }
/*      */              }
/*      */           
/*  517 */           else if (c == ']')
/*  518 */           { switch (this.finish - last) {
/*      */ 
/*      */               
/*      */               case 2:
/*  522 */                 if (this.buf[last + 1] != ']') {
/*      */                   break;
/*      */                 }
/*      */               
/*      */               case 1:
/*  527 */                 if (this.reader == null || this.isClosed)
/*      */                   break; 
/*  529 */                 if (last == first)
/*  530 */                   throw new InternalError("fillbuf"); 
/*  531 */                 last--;
/*  532 */                 if (last > first) {
/*  533 */                   sawContent = true;
/*  534 */                   this.start = last;
/*  535 */                   return sawContent;
/*      */                 } 
/*  537 */                 fillbuf();
/*  538 */                 first = last = this.start;
/*      */                 break;
/*      */ 
/*      */ 
/*      */               
/*      */               default:
/*  544 */                 if (this.buf[last + 1] == ']' && this.buf[last + 2] == '>') {
/*  545 */                   fatal("P-072", null);
/*      */                 }
/*      */                 break;
/*      */             } 
/*      */             
/*      */              }
/*  551 */           else if (c >= '?' && c <= '?')
/*  552 */           { if (last + 1 >= this.finish) {
/*  553 */               if (last > first) {
/*  554 */                 sawContent = true;
/*  555 */                 this.end = last;
/*  556 */                 this.start = last + 1;
/*  557 */                 return sawContent;
/*      */               } 
/*  559 */               if (isEOF()) {
/*  560 */                 fatal("P-081", new Object[] { Integer.toHexString(c) });
/*      */               }
/*  562 */               first = this.start;
/*  563 */               last = first;
/*      */             
/*      */             }
/*  566 */             else if (checkSurrogatePair(last)) {
/*  567 */               last++;
/*      */             } else {
/*  569 */               last--;
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             }  }
/*      */           else
/*  576 */           { fatal("P-071", new Object[] { Integer.toHexString(c) }); }  } 
/*      */       } 
/*  578 */     }  if (last == first)
/*  579 */       return sawContent; 
/*  580 */     this.start = last;
/*  581 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getUnparsedContent(boolean ignorableWhitespace, String whitespaceInvalidMessage) throws ParseException, IOException {
/*  591 */     int s = this.start;
/*  592 */     String ret = null;
/*      */     
/*  594 */     if (!unparsedContent(ignorableWhitespace, whitespaceInvalidMessage)) {
/*  595 */       return null;
/*      */     }
/*  597 */     return new String(this.cdataBuf);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean unparsedContent(boolean ignorableWhitespace, String whitespaceInvalidMessage) throws ParseException, IOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: ldc '![CDATA['
/*      */     //   3: aconst_null
/*      */     //   4: invokevirtual peek : (Ljava/lang/String;[C)Z
/*      */     //   7: ifne -> 12
/*      */     //   10: iconst_0
/*      */     //   11: ireturn
/*      */     //   12: aconst_null
/*      */     //   13: astore #4
/*      */     //   15: iconst_0
/*      */     //   16: istore #5
/*      */     //   18: iconst_0
/*      */     //   19: istore #6
/*      */     //   21: aload_0
/*      */     //   22: getfield start : I
/*      */     //   25: istore #8
/*      */     //   27: iload_1
/*      */     //   28: istore #9
/*      */     //   30: aload_0
/*      */     //   31: getfield start : I
/*      */     //   34: istore_3
/*      */     //   35: iload_3
/*      */     //   36: aload_0
/*      */     //   37: getfield finish : I
/*      */     //   40: if_icmpge -> 310
/*      */     //   43: aload_0
/*      */     //   44: getfield buf : [C
/*      */     //   47: iload_3
/*      */     //   48: caload
/*      */     //   49: istore #7
/*      */     //   51: iload #7
/*      */     //   53: invokestatic isChar : (I)Z
/*      */     //   56: ifne -> 118
/*      */     //   59: iconst_0
/*      */     //   60: istore #9
/*      */     //   62: iload #7
/*      */     //   64: ldc 55296
/*      */     //   66: if_icmplt -> 96
/*      */     //   69: iload #7
/*      */     //   71: ldc 57343
/*      */     //   73: if_icmpgt -> 96
/*      */     //   76: aload_0
/*      */     //   77: iload_3
/*      */     //   78: invokespecial checkSurrogatePair : (I)Z
/*      */     //   81: ifeq -> 90
/*      */     //   84: iinc #3, 1
/*      */     //   87: goto -> 304
/*      */     //   90: iinc #3, -1
/*      */     //   93: goto -> 310
/*      */     //   96: aload_0
/*      */     //   97: ldc 'P-071'
/*      */     //   99: iconst_1
/*      */     //   100: anewarray java/lang/Object
/*      */     //   103: dup
/*      */     //   104: iconst_0
/*      */     //   105: aload_0
/*      */     //   106: getfield buf : [C
/*      */     //   109: iload_3
/*      */     //   110: caload
/*      */     //   111: invokestatic toHexString : (I)Ljava/lang/String;
/*      */     //   114: aastore
/*      */     //   115: invokespecial fatal : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   118: iload #7
/*      */     //   120: bipush #10
/*      */     //   122: if_icmpne -> 145
/*      */     //   125: aload_0
/*      */     //   126: invokevirtual isInternal : ()Z
/*      */     //   129: ifne -> 304
/*      */     //   132: aload_0
/*      */     //   133: dup
/*      */     //   134: getfield lineNumber : I
/*      */     //   137: iconst_1
/*      */     //   138: iadd
/*      */     //   139: putfield lineNumber : I
/*      */     //   142: goto -> 304
/*      */     //   145: iload #7
/*      */     //   147: bipush #13
/*      */     //   149: if_icmpne -> 232
/*      */     //   152: aload_0
/*      */     //   153: invokevirtual isInternal : ()Z
/*      */     //   156: ifeq -> 162
/*      */     //   159: goto -> 304
/*      */     //   162: iload #9
/*      */     //   164: ifeq -> 186
/*      */     //   167: aload_2
/*      */     //   168: ifnull -> 186
/*      */     //   171: aload_0
/*      */     //   172: getstatic com/sun/xml/rpc/sp/Parser.messages : Lcom/sun/xml/rpc/sp/Parser$Catalog;
/*      */     //   175: aload_0
/*      */     //   176: getfield locale : Ljava/util/Locale;
/*      */     //   179: aload_2
/*      */     //   180: invokevirtual getMessage : (Ljava/util/Locale;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   183: invokespecial fatal : (Ljava/lang/String;)V
/*      */     //   186: aload_0
/*      */     //   187: dup
/*      */     //   188: getfield lineNumber : I
/*      */     //   191: iconst_1
/*      */     //   192: iadd
/*      */     //   193: putfield lineNumber : I
/*      */     //   196: aload_0
/*      */     //   197: getfield finish : I
/*      */     //   200: iload_3
/*      */     //   201: iconst_1
/*      */     //   202: iadd
/*      */     //   203: if_icmple -> 222
/*      */     //   206: aload_0
/*      */     //   207: getfield buf : [C
/*      */     //   210: iload_3
/*      */     //   211: iconst_1
/*      */     //   212: iadd
/*      */     //   213: caload
/*      */     //   214: bipush #10
/*      */     //   216: if_icmpne -> 222
/*      */     //   219: iinc #3, 1
/*      */     //   222: aload_0
/*      */     //   223: iload_3
/*      */     //   224: iconst_1
/*      */     //   225: iadd
/*      */     //   226: putfield start : I
/*      */     //   229: goto -> 304
/*      */     //   232: iload #7
/*      */     //   234: bipush #93
/*      */     //   236: if_icmpeq -> 259
/*      */     //   239: iload #7
/*      */     //   241: bipush #32
/*      */     //   243: if_icmpeq -> 304
/*      */     //   246: iload #7
/*      */     //   248: bipush #9
/*      */     //   250: if_icmpeq -> 304
/*      */     //   253: iconst_0
/*      */     //   254: istore #9
/*      */     //   256: goto -> 304
/*      */     //   259: iload_3
/*      */     //   260: iconst_2
/*      */     //   261: iadd
/*      */     //   262: aload_0
/*      */     //   263: getfield finish : I
/*      */     //   266: if_icmpge -> 310
/*      */     //   269: aload_0
/*      */     //   270: getfield buf : [C
/*      */     //   273: iload_3
/*      */     //   274: iconst_1
/*      */     //   275: iadd
/*      */     //   276: caload
/*      */     //   277: bipush #93
/*      */     //   279: if_icmpne -> 301
/*      */     //   282: aload_0
/*      */     //   283: getfield buf : [C
/*      */     //   286: iload_3
/*      */     //   287: iconst_2
/*      */     //   288: iadd
/*      */     //   289: caload
/*      */     //   290: bipush #62
/*      */     //   292: if_icmpne -> 301
/*      */     //   295: iconst_1
/*      */     //   296: istore #6
/*      */     //   298: goto -> 310
/*      */     //   301: iconst_0
/*      */     //   302: istore #9
/*      */     //   304: iinc #3, 1
/*      */     //   307: goto -> 35
/*      */     //   310: iload #9
/*      */     //   312: ifeq -> 334
/*      */     //   315: aload_2
/*      */     //   316: ifnull -> 334
/*      */     //   319: aload_0
/*      */     //   320: getstatic com/sun/xml/rpc/sp/Parser.messages : Lcom/sun/xml/rpc/sp/Parser$Catalog;
/*      */     //   323: aload_0
/*      */     //   324: getfield locale : Ljava/util/Locale;
/*      */     //   327: aload_2
/*      */     //   328: invokevirtual getMessage : (Ljava/util/Locale;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   331: invokespecial fatal : (Ljava/lang/String;)V
/*      */     //   334: iload #6
/*      */     //   336: ifeq -> 414
/*      */     //   339: aload_0
/*      */     //   340: getfield cdataBuf : [C
/*      */     //   343: ifnull -> 373
/*      */     //   346: iload #5
/*      */     //   348: iload_3
/*      */     //   349: iadd
/*      */     //   350: iload #8
/*      */     //   352: isub
/*      */     //   353: newarray char
/*      */     //   355: astore #4
/*      */     //   357: aload_0
/*      */     //   358: getfield cdataBuf : [C
/*      */     //   361: iconst_0
/*      */     //   362: aload #4
/*      */     //   364: iconst_0
/*      */     //   365: iload #5
/*      */     //   367: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   370: goto -> 381
/*      */     //   373: iload_3
/*      */     //   374: iload #8
/*      */     //   376: isub
/*      */     //   377: newarray char
/*      */     //   379: astore #4
/*      */     //   381: aload_0
/*      */     //   382: getfield buf : [C
/*      */     //   385: iload #8
/*      */     //   387: aload #4
/*      */     //   389: iload #5
/*      */     //   391: iload_3
/*      */     //   392: iload #8
/*      */     //   394: isub
/*      */     //   395: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   398: aload_0
/*      */     //   399: aload #4
/*      */     //   401: putfield cdataBuf : [C
/*      */     //   404: aload_0
/*      */     //   405: iload_3
/*      */     //   406: iconst_3
/*      */     //   407: iadd
/*      */     //   408: putfield start : I
/*      */     //   411: goto -> 518
/*      */     //   414: aload_0
/*      */     //   415: getfield cdataBuf : [C
/*      */     //   418: ifnull -> 453
/*      */     //   421: aload_0
/*      */     //   422: getfield cdataBuf : [C
/*      */     //   425: arraylength
/*      */     //   426: sipush #2049
/*      */     //   429: iadd
/*      */     //   430: newarray char
/*      */     //   432: astore #4
/*      */     //   434: aload_0
/*      */     //   435: getfield cdataBuf : [C
/*      */     //   438: iconst_0
/*      */     //   439: aload #4
/*      */     //   441: iconst_0
/*      */     //   442: aload_0
/*      */     //   443: getfield cdataBuf : [C
/*      */     //   446: arraylength
/*      */     //   447: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   450: goto -> 460
/*      */     //   453: sipush #2049
/*      */     //   456: newarray char
/*      */     //   458: astore #4
/*      */     //   460: aload_0
/*      */     //   461: getfield buf : [C
/*      */     //   464: iload #8
/*      */     //   466: aload #4
/*      */     //   468: iload #5
/*      */     //   470: iload_3
/*      */     //   471: iload #8
/*      */     //   473: isub
/*      */     //   474: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*      */     //   477: aload_0
/*      */     //   478: aload #4
/*      */     //   480: putfield cdataBuf : [C
/*      */     //   483: iload #5
/*      */     //   485: iload_3
/*      */     //   486: iload #8
/*      */     //   488: isub
/*      */     //   489: iadd
/*      */     //   490: istore #5
/*      */     //   492: aload_0
/*      */     //   493: iload_3
/*      */     //   494: putfield start : I
/*      */     //   497: aload_0
/*      */     //   498: invokespecial fillbuf : ()V
/*      */     //   501: aload_0
/*      */     //   502: invokevirtual isEOF : ()Z
/*      */     //   505: ifeq -> 515
/*      */     //   508: aload_0
/*      */     //   509: ldc 'P-073'
/*      */     //   511: aconst_null
/*      */     //   512: invokespecial fatal : (Ljava/lang/String;[Ljava/lang/Object;)V
/*      */     //   515: goto -> 18
/*      */     //   518: iconst_1
/*      */     //   519: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #626	-> 0
/*      */     //   #627	-> 10
/*      */     //   #632	-> 12
/*      */     //   #633	-> 15
/*      */     //   #636	-> 18
/*      */     //   #638	-> 21
/*      */     //   #642	-> 27
/*      */     //   #644	-> 30
/*      */     //   #645	-> 43
/*      */     //   #650	-> 51
/*      */     //   #651	-> 59
/*      */     //   #652	-> 62
/*      */     //   #653	-> 76
/*      */     //   #654	-> 84
/*      */     //   #655	-> 87
/*      */     //   #657	-> 90
/*      */     //   #658	-> 93
/*      */     //   #661	-> 96
/*      */     //   #665	-> 118
/*      */     //   #666	-> 125
/*      */     //   #667	-> 132
/*      */     //   #670	-> 145
/*      */     //   #672	-> 152
/*      */     //   #673	-> 159
/*      */     //   #675	-> 162
/*      */     //   #676	-> 167
/*      */     //   #677	-> 171
/*      */     //   #682	-> 186
/*      */     //   #683	-> 196
/*      */     //   #684	-> 206
/*      */     //   #685	-> 219
/*      */     //   #689	-> 222
/*      */     //   #690	-> 229
/*      */     //   #692	-> 232
/*      */     //   #693	-> 239
/*      */     //   #694	-> 253
/*      */     //   #697	-> 259
/*      */     //   #698	-> 269
/*      */     //   #699	-> 295
/*      */     //   #700	-> 298
/*      */     //   #702	-> 301
/*      */     //   #644	-> 304
/*      */     //   #709	-> 310
/*      */     //   #710	-> 315
/*      */     //   #711	-> 319
/*      */     //   #716	-> 334
/*      */     //   #718	-> 339
/*      */     //   #719	-> 346
/*      */     //   #720	-> 357
/*      */     //   #721	-> 370
/*      */     //   #722	-> 373
/*      */     //   #724	-> 381
/*      */     //   #725	-> 398
/*      */     //   #727	-> 404
/*      */     //   #728	-> 411
/*      */     //   #733	-> 414
/*      */     //   #734	-> 421
/*      */     //   #735	-> 434
/*      */     //   #736	-> 450
/*      */     //   #737	-> 453
/*      */     //   #740	-> 460
/*      */     //   #741	-> 477
/*      */     //   #742	-> 483
/*      */     //   #744	-> 492
/*      */     //   #745	-> 497
/*      */     //   #746	-> 501
/*      */     //   #747	-> 508
/*      */     //   #748	-> 515
/*      */     //   #749	-> 518
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   51	259	7	c	C
/*      */     //   21	494	6	done	Z
/*      */     //   27	488	8	s	I
/*      */     //   30	485	9	white	Z
/*      */     //   0	520	0	this	Lcom/sun/xml/rpc/sp/InputEntity;
/*      */     //   0	520	1	ignorableWhitespace	Z
/*      */     //   0	520	2	whitespaceInvalidMessage	Ljava/lang/String;
/*      */     //   35	485	3	last	I
/*      */     //   15	505	4	tempBuf	[C
/*      */     //   18	502	5	cdataLast	I
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkSurrogatePair(int offset) throws ParseException {
/*  754 */     if (offset + 1 >= this.finish) {
/*  755 */       return false;
/*      */     }
/*  757 */     char c1 = this.buf[offset++];
/*  758 */     char c2 = this.buf[offset];
/*      */     
/*  760 */     if (c1 >= '?' && c1 < '?' && c2 >= '?' && c2 <= '?')
/*  761 */       return true; 
/*  762 */     fatal("P-074", new Object[] { Integer.toHexString(c1 & Character.MAX_VALUE), Integer.toHexString(c2 & Character.MAX_VALUE) });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  767 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ignorableWhitespace() throws ParseException, IOException {
/*  778 */     boolean isSpace = false;
/*      */ 
/*      */ 
/*      */     
/*  782 */     int first = this.start; while (true) {
/*  783 */       if (this.finish <= this.start) {
/*  784 */         fillbuf();
/*  785 */         first = this.start;
/*      */       } 
/*  787 */       if (this.finish <= this.start) {
/*  788 */         return isSpace;
/*      */       }
/*  790 */       char c = this.buf[this.start++];
/*  791 */       switch (c) {
/*      */         case '\n':
/*  793 */           if (!isInternal()) {
/*  794 */             this.lineNumber++;
/*      */           }
/*      */         
/*      */         case '\t':
/*      */         case ' ':
/*  799 */           isSpace = true;
/*      */           continue;
/*      */         
/*      */         case '\r':
/*  803 */           isSpace = true;
/*  804 */           if (!isInternal())
/*  805 */             this.lineNumber++; 
/*  806 */           if (this.start < this.finish && this.buf[this.start] == '\n')
/*  807 */             this.start++; 
/*  808 */           first = this.start; continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  812 */     ungetc();
/*  813 */     return isSpace;
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
/*      */   public boolean peek(String next, char[] chars) throws ParseException, IOException {
/*      */     int len, i;
/*  830 */     if (chars != null) {
/*  831 */       len = chars.length;
/*      */     } else {
/*  833 */       len = next.length();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  838 */     if (this.finish <= this.start || this.finish - this.start < len) {
/*  839 */       fillbuf();
/*      */     }
/*      */     
/*  842 */     if (this.finish <= this.start) {
/*  843 */       return false;
/*      */     }
/*      */     
/*  846 */     if (chars != null) {
/*  847 */       for (i = 0; i < len && this.start + i < this.finish; i++) {
/*  848 */         if (this.buf[this.start + i] != chars[i])
/*  849 */           return false; 
/*      */       } 
/*      */     } else {
/*  852 */       for (i = 0; i < len && this.start + i < this.finish; i++) {
/*  853 */         if (this.buf[this.start + i] != next.charAt(i)) {
/*  854 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  860 */     if (i < len) {
/*  861 */       if (this.reader == null || this.isClosed) {
/*  862 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  871 */       if (len > this.buf.length) {
/*  872 */         fatal("P-077", new Object[] { new Integer(this.buf.length) });
/*      */       }
/*  874 */       fillbuf();
/*  875 */       return peek(next, chars);
/*      */     } 
/*      */     
/*  878 */     this.start += len;
/*  879 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startRemembering() {
/*  889 */     if (this.startRemember != 0)
/*  890 */       throw new InternalError(); 
/*  891 */     this.startRemember = this.start;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String rememberText() {
/*      */     String retval;
/*  899 */     if (this.rememberedText != null) {
/*  900 */       this.rememberedText.append(this.buf, this.startRemember, this.start - this.startRemember);
/*  901 */       retval = this.rememberedText.toString();
/*      */     } else {
/*  903 */       retval = new String(this.buf, this.startRemember, this.start - this.startRemember);
/*      */     } 
/*  905 */     this.startRemember = 0;
/*  906 */     this.rememberedText = null;
/*  907 */     return retval;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Locator getLocator() {
/*  913 */     InputEntity current = this;
/*      */ 
/*      */ 
/*      */     
/*  917 */     while (current != null && current.input == null)
/*  918 */       current = current.next; 
/*  919 */     return (current == null) ? this : current;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPublicId() {
/*  924 */     Locator where = getLocator();
/*  925 */     if (where == this)
/*  926 */       return this.input.getPublicId(); 
/*  927 */     return where.getPublicId();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getSystemId() {
/*  932 */     Locator where = getLocator();
/*  933 */     if (where == this)
/*  934 */       return this.input.getSystemId(); 
/*  935 */     return where.getSystemId();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLineNumber() {
/*  940 */     Locator where = getLocator();
/*  941 */     if (where == this)
/*  942 */       return this.lineNumber; 
/*  943 */     return where.getLineNumber();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColumnNumber() {
/*  948 */     return -1;
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
/*      */   private void fillbuf() throws ParseException, IOException {
/*  966 */     if (this.reader == null || this.isClosed) {
/*      */       return;
/*      */     }
/*      */     
/*  970 */     if (this.startRemember != 0) {
/*  971 */       if (this.rememberedText == null)
/*  972 */         this.rememberedText = new StringBuffer(this.buf.length); 
/*  973 */       this.rememberedText.append(this.buf, this.startRemember, this.start - this.startRemember);
/*      */     } 
/*      */     
/*  976 */     boolean extra = (this.finish > 0 && this.start > 0);
/*      */ 
/*      */     
/*  979 */     if (extra)
/*  980 */       this.start--; 
/*  981 */     int len = this.finish - this.start;
/*      */     
/*  983 */     System.arraycopy(this.buf, this.start, this.buf, 0, len);
/*  984 */     this.start = 0;
/*  985 */     this.finish = len;
/*      */     
/*      */     try {
/*  988 */       len = this.buf.length - len;
/*  989 */       len = this.reader.read(this.buf, this.finish, len);
/*  990 */     } catch (UnsupportedEncodingException e) {
/*  991 */       fatal("P-075", new Object[] { e.getMessage() });
/*  992 */     } catch (CharConversionException e) {
/*  993 */       fatal("P-076", new Object[] { e.getMessage() });
/*      */     } 
/*  995 */     if (len >= 0) {
/*  996 */       this.finish += len;
/*      */     } else {
/*  998 */       close();
/*  999 */     }  if (extra) {
/* 1000 */       this.start++;
/*      */     }
/* 1002 */     if (this.startRemember != 0)
/*      */     {
/* 1004 */       this.startRemember = 1; } 
/*      */   }
/*      */   
/*      */   public void close() {
/*      */     try {
/* 1009 */       if (this.reader != null && !this.isClosed)
/* 1010 */         this.reader.close(); 
/* 1011 */       this.isClosed = true;
/* 1012 */     } catch (IOException e) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fatal(String message) throws ParseException {
/* 1018 */     ParseException x = new ParseException(message, getPublicId(), getSystemId(), getLineNumber(), getColumnNumber());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1027 */     close();
/* 1028 */     throw x;
/*      */   }
/*      */ 
/*      */   
/*      */   private void fatal(String messageId, Object[] params) throws ParseException {
/* 1033 */     fatal(Parser.messages.getMessage(this.locale, messageId, params));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\InputEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */