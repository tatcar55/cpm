/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.text.MessageFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndentingWriter
/*     */   extends BufferedWriter
/*     */ {
/*     */   private boolean beginningOfLine = true;
/*  45 */   private int currentIndent = 0;
/*  46 */   private int indentStep = 4;
/*     */   
/*     */   public IndentingWriter(Writer out) {
/*  49 */     super(out);
/*     */   }
/*     */   
/*     */   public IndentingWriter(Writer out, int step) {
/*  53 */     this(out);
/*     */     
/*  55 */     if (this.indentStep < 0) {
/*  56 */       throw new IllegalArgumentException("negative indent step");
/*     */     }
/*  58 */     this.indentStep = step;
/*     */   }
/*     */   
/*     */   public void write(int c) throws IOException {
/*  62 */     checkWrite();
/*  63 */     super.write(c);
/*     */   }
/*     */   
/*     */   public void write(char[] cbuf, int off, int len) throws IOException {
/*  67 */     if (len > 0) {
/*  68 */       checkWrite();
/*     */     }
/*  70 */     super.write(cbuf, off, len);
/*     */   }
/*     */   
/*     */   public void write(String s, int off, int len) throws IOException {
/*  74 */     if (len > 0) {
/*  75 */       checkWrite();
/*     */     }
/*  77 */     super.write(s, off, len);
/*     */   }
/*     */   
/*     */   public void newLine() throws IOException {
/*  81 */     super.newLine();
/*  82 */     this.beginningOfLine = true;
/*     */   }
/*     */   
/*     */   protected void checkWrite() throws IOException {
/*  86 */     if (this.beginningOfLine) {
/*  87 */       this.beginningOfLine = false;
/*  88 */       int i = this.currentIndent;
/*  89 */       while (i > 0) {
/*  90 */         super.write(32);
/*  91 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void indentIn() {
/*  97 */     this.currentIndent += this.indentStep;
/*     */   }
/*     */   
/*     */   protected void indentOut() {
/* 101 */     this.currentIndent -= this.indentStep;
/* 102 */     if (this.currentIndent < 0) {
/* 103 */       this.currentIndent = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void pI() {
/* 108 */     indentIn();
/*     */   }
/*     */   
/*     */   public void pO() {
/* 112 */     indentOut();
/*     */   }
/*     */   
/*     */   public void pI(int levels) {
/* 116 */     for (int i = 0; i < levels; i++) {
/* 117 */       indentIn();
/*     */     }
/*     */   }
/*     */   
/*     */   public void pO(int levels) {
/* 122 */     for (int i = 0; i < levels; i++) {
/* 123 */       indentOut();
/*     */     }
/*     */   }
/*     */   
/*     */   public void p(String s) throws IOException {
/* 128 */     int tabCount = 0;
/* 129 */     for (int i = 0; i < s.length(); i++) {
/* 130 */       if (s.charAt(i) == '\t') {
/* 131 */         tabCount++;
/* 132 */         indentIn();
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     String printStr = s.substring(tabCount);
/* 137 */     boolean canEncode = true;
/*     */ 
/*     */     
/*     */     try {
/* 141 */       if (!canEncode(printStr)) {
/* 142 */         canEncode = false;
/*     */       }
/* 144 */     } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (!canEncode) {
/* 151 */       throw new GeneratorException("generator.indentingwriter.charset.cantencode", printStr);
/*     */     }
/*     */     
/* 154 */     write(printStr);
/*     */     
/* 156 */     while (tabCount-- > 0) {
/* 157 */       indentOut();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canEncode(String s) {
/* 166 */     CharsetEncoder encoder = Charset.forName(System.getProperty("file.encoding")).newEncoder();
/*     */     
/* 168 */     char[] chars = s.toCharArray();
/* 169 */     for (int i = 0; i < chars.length; i++) {
/* 170 */       if (!encoder.canEncode(chars[i])) {
/* 171 */         return false;
/*     */       }
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   public void p(String s1, String s2) throws IOException {
/* 178 */     p(s1);
/* 179 */     p(s2);
/*     */   }
/*     */   
/*     */   public void p(String s1, String s2, String s3) throws IOException {
/* 183 */     p(s1);
/* 184 */     p(s2);
/* 185 */     p(s3);
/*     */   }
/*     */   
/*     */   public void p(String s1, String s2, String s3, String s4) throws IOException {
/* 189 */     p(s1);
/* 190 */     p(s2);
/* 191 */     p(s3);
/* 192 */     p(s4);
/*     */   }
/*     */   
/*     */   public void p(String s1, String s2, String s3, String s4, String s5) throws IOException {
/* 196 */     p(s1);
/* 197 */     p(s2);
/* 198 */     p(s3);
/* 199 */     p(s4);
/* 200 */     p(s5);
/*     */   }
/*     */   
/*     */   public void pln() throws IOException {
/* 204 */     newLine();
/*     */   }
/*     */   
/*     */   public void pln(String s) throws IOException {
/* 208 */     p(s);
/* 209 */     pln();
/*     */   }
/*     */   
/*     */   public void pln(String s1, String s2) throws IOException {
/* 213 */     p(s1, s2);
/* 214 */     pln();
/*     */   }
/*     */   
/*     */   public void pln(String s1, String s2, String s3) throws IOException {
/* 218 */     p(s1, s2, s3);
/* 219 */     pln();
/*     */   }
/*     */   
/*     */   public void pln(String s1, String s2, String s3, String s4) throws IOException {
/* 223 */     p(s1, s2, s3, s4);
/* 224 */     pln();
/*     */   }
/*     */   
/*     */   public void pln(String s1, String s2, String s3, String s4, String s5) throws IOException {
/* 228 */     p(s1, s2, s3, s4, s5);
/* 229 */     pln();
/*     */   }
/*     */   
/*     */   public void plnI(String s) throws IOException {
/* 233 */     p(s);
/* 234 */     pln();
/* 235 */     pI();
/*     */   }
/*     */   
/*     */   public void pO(String s) throws IOException {
/* 239 */     pO();
/* 240 */     p(s);
/*     */   }
/*     */   
/*     */   public void pOln(String s) throws IOException {
/* 244 */     pO(s);
/* 245 */     pln();
/*     */   }
/*     */   
/*     */   public void pOlnI(String s) throws IOException {
/* 249 */     pO(s);
/* 250 */     pln();
/* 251 */     pI();
/*     */   }
/*     */   
/*     */   public void p(Object o) throws IOException {
/* 255 */     write(o.toString());
/*     */   }
/*     */   
/*     */   public void pln(Object o) throws IOException {
/* 259 */     p(o.toString());
/* 260 */     pln();
/*     */   }
/*     */   
/*     */   public void plnI(Object o) throws IOException {
/* 264 */     p(o.toString());
/* 265 */     pln();
/* 266 */     pI();
/*     */   }
/*     */   
/*     */   public void pO(Object o) throws IOException {
/* 270 */     pO();
/* 271 */     p(o.toString());
/*     */   }
/*     */   
/*     */   public void pOln(Object o) throws IOException {
/* 275 */     pO(o.toString());
/* 276 */     pln();
/*     */   }
/*     */   
/*     */   public void pOlnI(Object o) throws IOException {
/* 280 */     pO(o.toString());
/* 281 */     pln();
/* 282 */     pI();
/*     */   }
/*     */   
/*     */   public void pM(String s) throws IOException {
/* 286 */     int i = 0;
/* 287 */     while (i < s.length()) {
/* 288 */       int j = s.indexOf('\n', i);
/* 289 */       if (j == -1) {
/* 290 */         p(s.substring(i));
/*     */         break;
/*     */       } 
/* 293 */       pln(s.substring(i, j));
/* 294 */       i = j + 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void pMln(String s) throws IOException {
/* 300 */     pM(s);
/* 301 */     pln();
/*     */   }
/*     */   
/*     */   public void pMlnI(String s) throws IOException {
/* 305 */     pM(s);
/* 306 */     pln();
/* 307 */     pI();
/*     */   }
/*     */   
/*     */   public void pMO(String s) throws IOException {
/* 311 */     pO();
/* 312 */     pM(s);
/*     */   }
/*     */   
/*     */   public void pMOln(String s) throws IOException {
/* 316 */     pMO(s);
/* 317 */     pln();
/*     */   }
/*     */   
/*     */   public void pF(String pattern, Object[] arguments) throws IOException {
/* 321 */     pM(MessageFormat.format(pattern, arguments));
/*     */   }
/*     */   
/*     */   public void pFln(String pattern, Object[] arguments) throws IOException {
/* 325 */     pF(pattern, arguments);
/* 326 */     pln();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\IndentingWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */