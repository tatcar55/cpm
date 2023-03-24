/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Printer
/*     */ {
/*     */   public static final int DEFAULT_INCREMENT = 4;
/*     */   private PrintStream ps;
/*     */   private int increment;
/*     */   private char padChar;
/*     */   private int indent;
/*     */   private char[] pad;
/*     */   private StringBuilder bld;
/*     */   private int rightJustificationSize;
/*     */   
/*     */   public Printer(PrintStream ps) {
/*  71 */     this(ps, 4, ' ');
/*     */   }
/*     */   
/*     */   public Printer(PrintStream ps, int increment, char padChar) {
/*  75 */     this.ps = ps;
/*  76 */     this.increment = increment;
/*  77 */     this.padChar = padChar;
/*  78 */     this.indent = 0;
/*  79 */     this.bld = new StringBuilder();
/*  80 */     fill();
/*  81 */     this.rightJustificationSize = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Printer rj(int size) {
/*  88 */     this.rightJustificationSize = size;
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   private Printer rightJustify(String str) {
/*  93 */     if (str.length() < this.rightJustificationSize) {
/*  94 */       for (int ctr = 0; ctr < this.rightJustificationSize - str.length(); ctr++) {
/*  95 */         this.bld.append(' ');
/*     */       }
/*     */     }
/*     */     
/*  99 */     this.rightJustificationSize = 0;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public Printer p(String str) {
/* 104 */     rightJustify(str);
/* 105 */     this.bld.append(str);
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public Printer p(Object... args) {
/* 110 */     for (Object obj : args) {
/* 111 */       p(obj);
/*     */     }
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public Printer p(Object obj) {
/* 117 */     String str = obj.toString();
/* 118 */     rightJustify(str);
/* 119 */     this.bld.append(str);
/* 120 */     return this;
/*     */   }
/*     */   
/*     */   public Printer in() {
/* 124 */     this.indent += this.increment;
/* 125 */     fill();
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public Printer out() {
/* 130 */     if (this.indent < this.increment) {
/* 131 */       throw new IllegalStateException("Cannot undent past start of line");
/*     */     }
/*     */     
/* 134 */     this.indent -= this.increment;
/* 135 */     fill();
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public int indent() {
/* 140 */     return this.indent;
/*     */   }
/*     */   
/*     */   private void fill() {
/* 144 */     this.pad = new char[this.indent];
/* 145 */     for (int ctr = 0; ctr < this.pad.length; ctr++)
/* 146 */       this.pad[ctr] = this.padChar; 
/*     */   }
/*     */   
/*     */   public Printer nl() {
/* 150 */     this.ps.println(flush());
/*     */     
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private String flush() {
/* 157 */     String result = this.bld.toString();
/* 158 */     this.bld = new StringBuilder();
/* 159 */     this.bld.append(this.pad);
/* 160 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isPrintable(char c) {
/* 164 */     if (Character.isJavaIdentifierStart(c))
/*     */     {
/* 166 */       return true;
/*     */     }
/* 168 */     if (Character.isDigit(c)) {
/* 169 */       return true;
/*     */     }
/* 171 */     switch (Character.getType(c)) { case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 24:
/*     */       case 25:
/*     */       case 27:
/* 177 */         return true; }
/*     */     
/* 179 */     return false;
/*     */   }
/*     */   
/*     */   public Printer printBuffer(byte[] buffer) {
/* 183 */     int length = buffer.length;
/*     */     
/* 185 */     for (int i = 0; i < length; i += 16) {
/* 186 */       StringBuffer sbuf = new StringBuffer();
/* 187 */       int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       while (j < 16 && i + j < length) {
/* 193 */         int k = buffer[i + j];
/* 194 */         if (k < 0)
/* 195 */           k = 256 + k; 
/* 196 */         String hex = Integer.toHexString(k);
/* 197 */         if (hex.length() == 1)
/* 198 */           hex = "0" + hex; 
/* 199 */         sbuf.append(hex + " ");
/* 200 */         j++;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       while (j < 16) {
/* 207 */         sbuf.append("   ");
/* 208 */         j++;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 213 */       int x = 0;
/* 214 */       while (x < 16 && x + i < length) {
/* 215 */         char ch = (char)buffer[i + x];
/* 216 */         if (isPrintable(ch)) {
/* 217 */           sbuf.append(ch);
/*     */         } else {
/* 219 */           sbuf.append('.');
/* 220 */         }  x++;
/*     */       } 
/*     */       
/* 223 */       nl().p(sbuf);
/*     */     } 
/*     */     
/* 226 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Printer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */