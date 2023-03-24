/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.ParseException;
/*     */ import com.sun.xml.rpc.sp.Parser;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StreamingParser
/*     */ {
/*     */   public static final int START = 0;
/*     */   public static final int END = 1;
/*     */   public static final int ATTR = 2;
/*     */   public static final int CHARS = 3;
/*     */   public static final int IWS = 4;
/*     */   public static final int PI = 5;
/*     */   public static final int AT_END = 6;
/*     */   private static final int DOC_END = -1;
/*     */   private static final int DOC_START = -2;
/*     */   private static final int EMPTY = -3;
/*     */   private static final int EXCEPTION = -4;
/*  63 */   private Parser parser = null;
/*     */   
/*  65 */   private int currentState = -3;
/*  66 */   private String currentName = null;
/*  67 */   private String currentValue = null;
/*  68 */   private String currentURI = null;
/*  69 */   private int currentLine = -1;
/*     */   
/*     */   public StreamingParser(InputStream in) {
/*  72 */     this.parser = new Parser(in, true, true);
/*     */   }
/*     */   
/*     */   public StreamingParser(File file) throws IOException {
/*  76 */     this.parser = new Parser(file, true, true);
/*     */   }
/*     */   
/*     */   public Stream getStream() {
/*  80 */     return new Stream() {
/*     */         public int next(Event event) {
/*  82 */           int state = StreamingParser.this.next();
/*  83 */           event.state = StreamingParser.this.currentState;
/*  84 */           event.name = StreamingParser.this.currentName;
/*  85 */           event.value = StreamingParser.this.currentValue;
/*  86 */           event.uri = StreamingParser.this.currentURI;
/*  87 */           event.line = StreamingParser.this.currentLine;
/*  88 */           return state;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public int next() {
/*  94 */     if (this.currentState == 6) {
/*  95 */       return 6;
/*     */     }
/*     */     try {
/*  98 */       this.currentState = this.parser.parse();
/*  99 */       if (this.currentState == -1)
/* 100 */         this.currentState = 6; 
/* 101 */     } catch (ParseException e) {
/* 102 */       throw new StreamingException(e);
/* 103 */     } catch (IOException e) {
/* 104 */       throw new StreamingException(e);
/*     */     } 
/*     */     
/* 107 */     this.currentName = this.parser.getCurName();
/* 108 */     this.currentValue = this.parser.getCurValue();
/*     */     
/* 110 */     this.currentURI = this.parser.getCurURI();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     this.currentLine = this.parser.getLineNumber();
/*     */     
/* 120 */     return this.currentState;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getState() {
/* 125 */     if (this.currentState == -3)
/* 126 */       throw new IllegalStateException("parser not started"); 
/* 127 */     if (this.currentState < -4)
/* 128 */       throw new InternalError(); 
/* 129 */     return this.currentState;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 133 */     return this.currentName;
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 137 */     return this.currentValue;
/*     */   }
/*     */   
/*     */   public String getURI() {
/* 141 */     return this.currentURI;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\StreamingParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */