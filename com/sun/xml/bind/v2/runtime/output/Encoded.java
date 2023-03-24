/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Encoded
/*     */ {
/*     */   public byte[] buf;
/*     */   public int len;
/*     */   
/*     */   public Encoded() {}
/*     */   
/*     */   public Encoded(String text) {
/*  60 */     set(text);
/*     */   }
/*     */   
/*     */   public void ensureSize(int size) {
/*  64 */     if (this.buf == null || this.buf.length < size)
/*  65 */       this.buf = new byte[size]; 
/*     */   }
/*     */   
/*     */   public final void set(String text) {
/*  69 */     int length = text.length();
/*     */     
/*  71 */     ensureSize(length * 3 + 1);
/*     */     
/*  73 */     int ptr = 0;
/*     */     
/*  75 */     for (int i = 0; i < length; i++) {
/*  76 */       char chr = text.charAt(i);
/*  77 */       if (chr > '')
/*  78 */       { if (chr > '߿')
/*  79 */         { if ('?' <= chr && chr <= '?')
/*     */           
/*  81 */           { int uc = ((chr & 0x3FF) << 10 | text.charAt(++i) & 0x3FF) + 65536;
/*     */             
/*  83 */             this.buf[ptr++] = (byte)(0xF0 | uc >> 18);
/*  84 */             this.buf[ptr++] = (byte)(0x80 | uc >> 12 & 0x3F);
/*  85 */             this.buf[ptr++] = (byte)(0x80 | uc >> 6 & 0x3F);
/*  86 */             this.buf[ptr++] = (byte)(128 + (uc & 0x3F)); }
/*     */           else
/*     */           
/*  89 */           { this.buf[ptr++] = (byte)(224 + (chr >> 12));
/*  90 */             this.buf[ptr++] = (byte)(128 + (chr >> 6 & 0x3F));
/*     */ 
/*     */ 
/*     */             
/*  94 */             this.buf[ptr++] = (byte)(128 + (chr & 0x3F)); }  } else { this.buf[ptr++] = (byte)(192 + (chr >> 6)); this.buf[ptr++] = (byte)(128 + (chr & 0x3F)); }
/*     */          }
/*  96 */       else { this.buf[ptr++] = (byte)chr; }
/*     */     
/*     */     } 
/*     */     
/* 100 */     this.len = ptr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setEscape(String text, boolean isAttribute) {
/* 111 */     int length = text.length();
/* 112 */     ensureSize(length * 6 + 1);
/*     */     
/* 114 */     int ptr = 0;
/*     */     
/* 116 */     for (int i = 0; i < length; i++) {
/* 117 */       char chr = text.charAt(i);
/*     */       
/* 119 */       int ptr1 = ptr;
/* 120 */       if (chr > '') {
/* 121 */         if (chr > '߿')
/* 122 */         { if ('?' <= chr && chr <= '?')
/*     */           
/* 124 */           { int uc = ((chr & 0x3FF) << 10 | text.charAt(++i) & 0x3FF) + 65536;
/*     */             
/* 126 */             this.buf[ptr++] = (byte)(0xF0 | uc >> 18);
/* 127 */             this.buf[ptr++] = (byte)(0x80 | uc >> 12 & 0x3F);
/* 128 */             this.buf[ptr++] = (byte)(0x80 | uc >> 6 & 0x3F);
/* 129 */             this.buf[ptr++] = (byte)(128 + (uc & 0x3F)); }
/*     */           else
/*     */           
/* 132 */           { this.buf[ptr1++] = (byte)(224 + (chr >> 12));
/* 133 */             this.buf[ptr1++] = (byte)(128 + (chr >> 6 & 0x3F));
/*     */ 
/*     */ 
/*     */             
/* 137 */             this.buf[ptr1++] = (byte)(128 + (chr & 0x3F)); }  } else { this.buf[ptr1++] = (byte)(192 + (chr >> 6)); this.buf[ptr1++] = (byte)(128 + (chr & 0x3F)); }
/*     */       
/*     */       } else {
/*     */         byte[] ent;
/* 141 */         if ((ent = attributeEntities[chr]) != null)
/*     */         
/*     */         { 
/*     */ 
/*     */           
/* 146 */           if (isAttribute || entities[chr] != null) {
/* 147 */             ptr1 = writeEntity(ent, ptr1);
/*     */           } else {
/* 149 */             this.buf[ptr1++] = (byte)chr;
/*     */           }  }
/* 151 */         else { this.buf[ptr1++] = (byte)chr; }
/*     */         
/* 153 */         ptr = ptr1;
/*     */       } 
/* 155 */     }  this.len = ptr;
/*     */   }
/*     */   
/*     */   private int writeEntity(byte[] entity, int ptr) {
/* 159 */     System.arraycopy(entity, 0, this.buf, ptr, entity.length);
/* 160 */     return ptr + entity.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write(UTF8XmlOutput out) throws IOException {
/* 167 */     out.write(this.buf, 0, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(char b) {
/* 175 */     this.buf[this.len++] = (byte)b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compact() {
/* 183 */     byte[] b = new byte[this.len];
/* 184 */     System.arraycopy(this.buf, 0, b, 0, this.len);
/* 185 */     this.buf = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   private static final byte[][] entities = new byte[128][];
/* 195 */   private static final byte[][] attributeEntities = new byte[128][];
/*     */   
/*     */   static {
/* 198 */     add('&', "&amp;", false);
/* 199 */     add('<', "&lt;", false);
/* 200 */     add('>', "&gt;", false);
/* 201 */     add('"', "&quot;", true);
/* 202 */     add('\t', "&#x9;", true);
/* 203 */     add('\r', "&#xD;", false);
/* 204 */     add('\n', "&#xA;", true);
/*     */   }
/*     */   
/*     */   private static void add(char c, String s, boolean attOnly) {
/* 208 */     byte[] image = UTF8XmlOutput.toBytes(s);
/* 209 */     attributeEntities[c] = image;
/* 210 */     if (!attOnly)
/* 211 */       entities[c] = image; 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\Encoded.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */