/*     */ package com.sun.xml.rpc.sp;
/*     */ 
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
/*     */ public final class StreamingParserImpl
/*     */   extends StreamingParser
/*     */ {
/*  42 */   private Parser parser = null;
/*     */   
/*     */   private static final int DOC_END = -1;
/*     */   
/*     */   private static final int DOC_START = -2;
/*     */   private static final int EMPTY = -3;
/*     */   private static final int EXCEPTION = -4;
/*  49 */   private int cur = -3;
/*  50 */   private String curName = null;
/*  51 */   private String curValue = null;
/*  52 */   private String curURI = null;
/*     */   
/*     */   private boolean validating;
/*     */   
/*     */   private boolean coalescing;
/*     */   private boolean namespaceAware;
/*  58 */   private int curLine = -1;
/*  59 */   private int curCol = -1;
/*     */ 
/*     */   
/*  62 */   private String publicId = null;
/*  63 */   private String systemId = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private StreamingParserImpl(StreamingParserFactory pf) {
/*  68 */     this.validating = pf.isValidating();
/*  69 */     this.coalescing = pf.isCoalescing();
/*  70 */     this.namespaceAware = pf.isNamespaceAware();
/*     */   }
/*     */   
/*     */   StreamingParserImpl(StreamingParserFactory pf, InputStream in) {
/*  74 */     this(pf);
/*  75 */     this.parser = new Parser(in, this.coalescing, this.namespaceAware);
/*     */   }
/*     */ 
/*     */   
/*     */   StreamingParserImpl(StreamingParserFactory pf, File file) throws IOException {
/*  80 */     this(pf);
/*  81 */     this.parser = new Parser(file, this.coalescing, this.namespaceAware);
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
/*     */   public int parse() throws ParseException, IOException {
/* 101 */     if (this.cur == -1) {
/* 102 */       return -1;
/*     */     }
/* 104 */     this.cur = this.parser.parse();
/* 105 */     this.curName = this.parser.getCurName();
/* 106 */     this.curValue = this.parser.getCurValue();
/* 107 */     this.curURI = this.parser.getCurURI();
/* 108 */     this.curLine = this.parser.getLineNumber();
/* 109 */     this.curCol = this.parser.getColumnNumber();
/* 110 */     return this.cur;
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
/*     */   public int state() {
/* 127 */     if (this.cur == -3)
/* 128 */       throw new IllegalStateException("Parser not started"); 
/* 129 */     if (this.cur < -1)
/* 130 */       throw new InternalError(); 
/* 131 */     return this.cur;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/* 141 */     if (this.curName == null)
/* 142 */       throw new IllegalStateException("Name not defined in this state"); 
/* 143 */     return this.curName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String value() {
/* 153 */     if (this.curValue == null)
/* 154 */       throw new IllegalStateException("Value not defined in this state"); 
/* 155 */     return this.curValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String uriString() {
/* 165 */     if (!this.namespaceAware)
/* 166 */       return null; 
/* 167 */     if (this.curURI == null) {
/* 168 */       throw new IllegalStateException("Value not defined in this state");
/*     */     }
/* 170 */     return this.curURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int line() {
/* 178 */     return this.curLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int column() {
/* 186 */     return this.curCol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String publicId() {
/* 194 */     return this.publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String systemId() {
/* 202 */     return this.systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidating() {
/* 212 */     return this.validating;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCoalescing() {
/* 222 */     return this.coalescing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNamespaceAware() {
/* 232 */     return this.namespaceAware;
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
/*     */   public String describe(boolean articleNeeded) {
/* 246 */     return describe(this.cur, this.curName, this.curValue, articleNeeded);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\StreamingParserImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */