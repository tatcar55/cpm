/*     */ package com.sun.xml.fastinfoset.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharArray
/*     */   implements CharSequence
/*     */ {
/*     */   public char[] ch;
/*     */   public int start;
/*     */   public int length;
/*     */   protected int _hash;
/*     */   
/*     */   protected CharArray() {}
/*     */   
/*     */   public CharArray(char[] _ch, int _start, int _length, boolean copy) {
/*  31 */     set(_ch, _start, _length, copy);
/*     */   }
/*     */   
/*     */   public final void set(char[] _ch, int _start, int _length, boolean copy) {
/*  35 */     if (copy) {
/*  36 */       this.ch = new char[_length];
/*  37 */       this.start = 0;
/*  38 */       this.length = _length;
/*  39 */       System.arraycopy(_ch, _start, this.ch, 0, _length);
/*     */     } else {
/*  41 */       this.ch = _ch;
/*  42 */       this.start = _start;
/*  43 */       this.length = _length;
/*     */     } 
/*  45 */     this._hash = 0;
/*     */   }
/*     */   
/*     */   public final void cloneArray() {
/*  49 */     char[] _ch = new char[this.length];
/*  50 */     System.arraycopy(this.ch, this.start, _ch, 0, this.length);
/*  51 */     this.ch = _ch;
/*  52 */     this.start = 0;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  56 */     return new String(this.ch, this.start, this.length);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  60 */     if (this._hash == 0)
/*     */     {
/*     */       
/*  63 */       for (int i = this.start; i < this.start + this.length; i++) {
/*  64 */         this._hash = 31 * this._hash + this.ch[i];
/*     */       }
/*     */     }
/*  67 */     return this._hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int hashCode(char[] ch, int start, int length) {
/*  73 */     int hash = 0;
/*  74 */     for (int i = start; i < start + length; i++) {
/*  75 */       hash = 31 * hash + ch[i];
/*     */     }
/*     */     
/*  78 */     return hash;
/*     */   }
/*     */   
/*     */   public final boolean equalsCharArray(CharArray cha) {
/*  82 */     if (this == cha) {
/*  83 */       return true;
/*     */     }
/*     */     
/*  86 */     if (this.length == cha.length) {
/*  87 */       int n = this.length;
/*  88 */       int i = this.start;
/*  89 */       int j = cha.start;
/*  90 */       while (n-- != 0) {
/*  91 */         if (this.ch[i++] != cha.ch[j++])
/*  92 */           return false; 
/*     */       } 
/*  94 */       return true;
/*     */     } 
/*     */     
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public final boolean equalsCharArray(char[] ch, int start, int length) {
/* 101 */     if (this.length == length) {
/* 102 */       int n = this.length;
/* 103 */       int i = this.start;
/* 104 */       int j = start;
/* 105 */       while (n-- != 0) {
/* 106 */         if (this.ch[i++] != ch[j++])
/* 107 */           return false; 
/*     */       } 
/* 109 */       return true;
/*     */     } 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 116 */     if (this == obj) {
/* 117 */       return true;
/*     */     }
/* 119 */     if (obj instanceof CharArray) {
/* 120 */       CharArray cha = (CharArray)obj;
/* 121 */       if (this.length == cha.length) {
/* 122 */         int n = this.length;
/* 123 */         int i = this.start;
/* 124 */         int j = cha.start;
/* 125 */         while (n-- != 0) {
/* 126 */           if (this.ch[i++] != cha.ch[j++])
/* 127 */             return false; 
/*     */         } 
/* 129 */         return true;
/*     */       } 
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int length() {
/* 138 */     return this.length;
/*     */   }
/*     */   
/*     */   public final char charAt(int index) {
/* 142 */     return this.ch[this.start + index];
/*     */   }
/*     */   
/*     */   public final CharSequence subSequence(int start, int end) {
/* 146 */     return new CharArray(this.ch, this.start + start, end - start, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\CharArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */