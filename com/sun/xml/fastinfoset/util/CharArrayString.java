/*    */ package com.sun.xml.fastinfoset.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharArrayString
/*    */   extends CharArray
/*    */ {
/*    */   protected String _s;
/*    */   
/*    */   public CharArrayString(String s) {
/* 24 */     this(s, true);
/*    */   }
/*    */   
/*    */   public CharArrayString(String s, boolean createArray) {
/* 28 */     this._s = s;
/* 29 */     if (createArray) {
/* 30 */       this.ch = this._s.toCharArray();
/* 31 */       this.start = 0;
/* 32 */       this.length = this.ch.length;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     return this._s;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 41 */     return this._s.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 45 */     if (this == obj) {
/* 46 */       return true;
/*    */     }
/* 48 */     if (obj instanceof CharArrayString) {
/* 49 */       CharArrayString chas = (CharArrayString)obj;
/* 50 */       return this._s.equals(chas._s);
/* 51 */     }  if (obj instanceof CharArray) {
/* 52 */       CharArray cha = (CharArray)obj;
/* 53 */       if (this.length == cha.length) {
/* 54 */         int n = this.length;
/* 55 */         int i = this.start;
/* 56 */         int j = cha.start;
/* 57 */         while (n-- != 0) {
/* 58 */           if (this.ch[i++] != cha.ch[j++])
/* 59 */             return false; 
/*    */         } 
/* 61 */         return true;
/*    */       } 
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\CharArrayString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */