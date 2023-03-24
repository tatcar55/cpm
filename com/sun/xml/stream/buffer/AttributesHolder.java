/*     */ package com.sun.xml.stream.buffer;
/*     */ 
/*     */ import org.xml.sax.Attributes;
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
/*     */ public final class AttributesHolder
/*     */   implements Attributes
/*     */ {
/*     */   protected static final int DEFAULT_CAPACITY = 8;
/*     */   protected static final int ITEM_SIZE = 8;
/*     */   protected static final int PREFIX = 0;
/*     */   protected static final int URI = 1;
/*     */   protected static final int LOCAL_NAME = 2;
/*     */   protected static final int QNAME = 3;
/*     */   protected static final int TYPE = 4;
/*     */   protected static final int VALUE = 5;
/*     */   protected int _attributeCount;
/*  68 */   protected String[] _strings = new String[64];
/*     */ 
/*     */   
/*     */   public final int getLength() {
/*  72 */     return this._attributeCount;
/*     */   }
/*     */   
/*     */   public final String getPrefix(int index) {
/*  76 */     return (index >= 0 && index < this._attributeCount) ? this._strings[(index << 3) + 0] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getLocalName(int index) {
/*  81 */     return (index >= 0 && index < this._attributeCount) ? this._strings[(index << 3) + 2] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getQName(int index) {
/*  86 */     return (index >= 0 && index < this._attributeCount) ? this._strings[(index << 3) + 3] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getType(int index) {
/*  91 */     return (index >= 0 && index < this._attributeCount) ? this._strings[(index << 3) + 4] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getURI(int index) {
/*  96 */     return (index >= 0 && index < this._attributeCount) ? this._strings[(index << 3) + 1] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getValue(int index) {
/* 101 */     return (index >= 0 && index < this._attributeCount) ? this._strings[(index << 3) + 5] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getIndex(String qName) {
/* 106 */     for (int i = 0; i < this._attributeCount; i++) {
/* 107 */       if (qName.equals(this._strings[(i << 3) + 3])) {
/* 108 */         return i;
/*     */       }
/*     */     } 
/* 111 */     return -1;
/*     */   }
/*     */   
/*     */   public final String getType(String qName) {
/* 115 */     int i = (getIndex(qName) << 3) + 4;
/* 116 */     return (i >= 0) ? this._strings[i] : null;
/*     */   }
/*     */   
/*     */   public final String getValue(String qName) {
/* 120 */     int i = (getIndex(qName) << 3) + 5;
/* 121 */     return (i >= 0) ? this._strings[i] : null;
/*     */   }
/*     */   
/*     */   public final int getIndex(String uri, String localName) {
/* 125 */     for (int i = 0; i < this._attributeCount; i++) {
/* 126 */       if (localName.equals(this._strings[(i << 3) + 2]) && uri.equals(this._strings[(i << 3) + 1]))
/*     */       {
/* 128 */         return i;
/*     */       }
/*     */     } 
/* 131 */     return -1;
/*     */   }
/*     */   
/*     */   public final String getType(String uri, String localName) {
/* 135 */     int i = (getIndex(uri, localName) << 3) + 4;
/* 136 */     return (i >= 0) ? this._strings[i] : null;
/*     */   }
/*     */   
/*     */   public final String getValue(String uri, String localName) {
/* 140 */     int i = (getIndex(uri, localName) << 3) + 5;
/* 141 */     return (i >= 0) ? this._strings[i] : null;
/*     */   }
/*     */   
/*     */   public final void clear() {
/* 145 */     if (this._attributeCount > 0) {
/* 146 */       for (int i = 0; i < this._attributeCount; i++) {
/* 147 */         this._strings[(i << 3) + 5] = null;
/*     */       }
/* 149 */       this._attributeCount = 0;
/*     */     } 
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
/*     */   public final void addAttributeWithQName(String uri, String localName, String qName, String type, String value) {
/* 162 */     int i = this._attributeCount << 3;
/* 163 */     if (i == this._strings.length) {
/* 164 */       resize(i);
/*     */     }
/*     */     
/* 167 */     this._strings[i + 0] = null;
/* 168 */     this._strings[i + 1] = uri;
/* 169 */     this._strings[i + 2] = localName;
/* 170 */     this._strings[i + 3] = qName;
/* 171 */     this._strings[i + 4] = type;
/* 172 */     this._strings[i + 5] = value;
/*     */     
/* 174 */     this._attributeCount++;
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
/*     */   public final void addAttributeWithPrefix(String prefix, String uri, String localName, String type, String value) {
/* 186 */     int i = this._attributeCount << 3;
/* 187 */     if (i == this._strings.length) {
/* 188 */       resize(i);
/*     */     }
/*     */     
/* 191 */     this._strings[i + 0] = prefix;
/* 192 */     this._strings[i + 1] = uri;
/* 193 */     this._strings[i + 2] = localName;
/* 194 */     this._strings[i + 3] = null;
/* 195 */     this._strings[i + 4] = type;
/* 196 */     this._strings[i + 5] = value;
/*     */     
/* 198 */     this._attributeCount++;
/*     */   }
/*     */   
/*     */   private void resize(int length) {
/* 202 */     int newLength = length * 2;
/* 203 */     String[] strings = new String[newLength];
/* 204 */     System.arraycopy(this._strings, 0, strings, 0, length);
/* 205 */     this._strings = strings;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\AttributesHolder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */