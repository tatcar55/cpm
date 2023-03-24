/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
/*     */ import javax.xml.stream.events.StartDocument;
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
/*     */ public class StartDocumentEvent
/*     */   extends EventBase
/*     */   implements StartDocument
/*     */ {
/*     */   protected String _systemId;
/*  27 */   protected String _encoding = "UTF-8";
/*     */   protected boolean _standalone = true;
/*  29 */   protected String _version = "1.0";
/*     */   private boolean _encodingSet = false;
/*     */   private boolean _standaloneSet = false;
/*     */   
/*     */   public void reset() {
/*  34 */     this._encoding = "UTF-8";
/*  35 */     this._standalone = true;
/*  36 */     this._version = "1.0";
/*  37 */     this._encodingSet = false;
/*  38 */     this._standaloneSet = false;
/*     */   }
/*     */   public StartDocumentEvent() {
/*  41 */     this(null, null);
/*     */   }
/*     */   
/*     */   public StartDocumentEvent(String encoding) {
/*  45 */     this(encoding, null);
/*     */   }
/*     */   
/*     */   public StartDocumentEvent(String encoding, String version) {
/*  49 */     if (encoding != null) {
/*  50 */       this._encoding = encoding;
/*  51 */       this._encodingSet = true;
/*     */     } 
/*  53 */     if (version != null)
/*  54 */       this._version = version; 
/*  55 */     setEventType(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  65 */     return super.getSystemId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/*  73 */     return this._encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean encodingSet() {
/*  80 */     return this._encodingSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStandalone() {
/*  89 */     return this._standalone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean standaloneSet() {
/*  96 */     return this._standaloneSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 104 */     return this._version;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStandalone(boolean standalone) {
/* 109 */     this._standaloneSet = true;
/* 110 */     this._standalone = standalone;
/*     */   }
/*     */   
/*     */   public void setStandalone(String s) {
/* 114 */     this._standaloneSet = true;
/* 115 */     if (s == null) {
/* 116 */       this._standalone = true;
/*     */       return;
/*     */     } 
/* 119 */     if (s.equals("yes")) {
/* 120 */       this._standalone = true;
/*     */     } else {
/* 122 */       this._standalone = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 127 */     this._encoding = encoding;
/* 128 */     this._encodingSet = true;
/*     */   }
/*     */   
/*     */   void setDeclaredEncoding(boolean value) {
/* 132 */     this._encodingSet = value;
/*     */   }
/*     */   
/*     */   public void setVersion(String s) {
/* 136 */     this._version = s;
/*     */   }
/*     */   
/*     */   void clear() {
/* 140 */     this._encoding = "UTF-8";
/* 141 */     this._standalone = true;
/* 142 */     this._version = "1.0";
/* 143 */     this._encodingSet = false;
/* 144 */     this._standaloneSet = false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 148 */     String s = "<?xml version=\"" + this._version + "\"";
/* 149 */     s = s + " encoding='" + this._encoding + "'";
/* 150 */     if (this._standaloneSet)
/* 151 */     { if (this._standalone) {
/* 152 */         s = s + " standalone='yes'?>";
/*     */       } else {
/* 154 */         s = s + " standalone='no'?>";
/*     */       }  }
/* 156 */     else { s = s + "?>"; }
/*     */     
/* 158 */     return s;
/*     */   }
/*     */   
/*     */   public boolean isStartDocument() {
/* 162 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\StartDocumentEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */