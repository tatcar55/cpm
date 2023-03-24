/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.org.apache.xerces.util.XMLChar;
/*     */ import javax.xml.stream.events.Characters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharactersEvent
/*     */   extends EventBase
/*     */   implements Characters
/*     */ {
/*     */   private String _text;
/*     */   private boolean isCData = false;
/*     */   private boolean isSpace = false;
/*     */   private boolean isIgnorable = false;
/*     */   private boolean needtoCheck = true;
/*     */   
/*     */   public CharactersEvent() {
/*  32 */     super(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharactersEvent(String data) {
/*  39 */     super(4);
/*  40 */     this._text = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharactersEvent(String data, boolean isCData) {
/*  49 */     super(4);
/*  50 */     this._text = data;
/*  51 */     this.isCData = isCData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getData() {
/*  58 */     return this._text;
/*     */   }
/*     */   
/*     */   public void setData(String data) {
/*  62 */     this._text = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCData() {
/*  70 */     return this.isCData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     if (this.isCData) {
/*  79 */       return "<![CDATA[" + this._text + "]]>";
/*     */     }
/*  81 */     return this._text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnorableWhiteSpace() {
/*  91 */     return this.isIgnorable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 102 */     if (this.needtoCheck) {
/* 103 */       checkWhiteSpace();
/* 104 */       this.needtoCheck = false;
/*     */     } 
/* 106 */     return this.isSpace;
/*     */   }
/*     */   
/*     */   public void setSpace(boolean isSpace) {
/* 110 */     this.isSpace = isSpace;
/* 111 */     this.needtoCheck = false;
/*     */   }
/*     */   public void setIgnorable(boolean isIgnorable) {
/* 114 */     this.isIgnorable = isIgnorable;
/* 115 */     setEventType(6);
/*     */   }
/*     */   
/*     */   private void checkWhiteSpace() {
/* 119 */     if (!Util.isEmptyString(this._text)) {
/* 120 */       this.isSpace = true;
/* 121 */       for (int i = 0; i < this._text.length(); i++) {
/* 122 */         if (!XMLChar.isSpace(this._text.charAt(i))) {
/* 123 */           this.isSpace = false;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\CharactersEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */