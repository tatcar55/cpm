/*     */ package com.sun.xml.fastinfoset.stax;
/*     */ 
/*     */ import javax.xml.stream.Location;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventLocation
/*     */   implements Location
/*     */ {
/*  24 */   String _systemId = null;
/*  25 */   String _publicId = null;
/*  26 */   int _column = -1;
/*  27 */   int _line = -1;
/*  28 */   int _charOffset = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Location getNilLocation() {
/*  35 */     return new EventLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/*  43 */     return this._line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/*  51 */     return this._column;
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
/*     */   public int getCharacterOffset() {
/*  63 */     return this._charOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/*  71 */     return this._publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  79 */     return this._systemId;
/*     */   }
/*     */   
/*     */   public void setLineNumber(int line) {
/*  83 */     this._line = line;
/*     */   }
/*     */   public void setColumnNumber(int col) {
/*  86 */     this._column = col;
/*     */   }
/*     */   public void setCharacterOffset(int offset) {
/*  89 */     this._charOffset = offset;
/*     */   }
/*     */   public void setPublicId(String id) {
/*  92 */     this._publicId = id;
/*     */   }
/*     */   public void setSystemId(String id) {
/*  95 */     this._systemId = id;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  99 */     StringBuffer sbuffer = new StringBuffer();
/* 100 */     sbuffer.append("Line number = " + this._line);
/* 101 */     sbuffer.append("\n");
/* 102 */     sbuffer.append("Column number = " + this._column);
/* 103 */     sbuffer.append("\n");
/* 104 */     sbuffer.append("System Id = " + this._systemId);
/* 105 */     sbuffer.append("\n");
/* 106 */     sbuffer.append("Public Id = " + this._publicId);
/* 107 */     sbuffer.append("\n");
/* 108 */     sbuffer.append("CharacterOffset = " + this._charOffset);
/* 109 */     sbuffer.append("\n");
/* 110 */     return sbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\EventLocation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */