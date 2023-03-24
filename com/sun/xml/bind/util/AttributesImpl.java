/*     */ package com.sun.xml.bind.util;
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
/*     */ public class AttributesImpl
/*     */   implements Attributes
/*     */ {
/*     */   int length;
/*     */   String[] data;
/*     */   
/*     */   public AttributesImpl() {
/*  99 */     this.length = 0;
/* 100 */     this.data = null;
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
/*     */   public AttributesImpl(Attributes atts) {
/* 114 */     setAttributes(atts);
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
/*     */   public int getLength() {
/* 132 */     return this.length;
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
/*     */   public String getURI(int index) {
/* 146 */     if (index >= 0 && index < this.length) {
/* 147 */       return this.data[index * 5];
/*     */     }
/* 149 */     return null;
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
/*     */   public String getLocalName(int index) {
/* 164 */     if (index >= 0 && index < this.length) {
/* 165 */       return this.data[index * 5 + 1];
/*     */     }
/* 167 */     return null;
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
/*     */   public String getQName(int index) {
/* 182 */     if (index >= 0 && index < this.length) {
/* 183 */       return this.data[index * 5 + 2];
/*     */     }
/* 185 */     return null;
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
/*     */   public String getType(int index) {
/* 200 */     if (index >= 0 && index < this.length) {
/* 201 */       return this.data[index * 5 + 3];
/*     */     }
/* 203 */     return null;
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
/*     */   public String getValue(int index) {
/* 217 */     if (index >= 0 && index < this.length) {
/* 218 */       return this.data[index * 5 + 4];
/*     */     }
/* 220 */     return null;
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
/*     */   public int getIndex(String uri, String localName) {
/* 240 */     int max = this.length * 5;
/* 241 */     for (int i = 0; i < max; i += 5) {
/* 242 */       if (this.data[i].equals(uri) && this.data[i + 1].equals(localName)) {
/* 243 */         return i / 5;
/*     */       }
/*     */     } 
/* 246 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexFast(String uri, String localName) {
/* 253 */     for (int i = (this.length - 1) * 5; i >= 0; i -= 5) {
/*     */       
/* 255 */       if (this.data[i + 1] == localName && this.data[i] == uri) {
/* 256 */         return i / 5;
/*     */       }
/*     */     } 
/* 259 */     return -1;
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
/*     */   public int getIndex(String qName) {
/* 272 */     int max = this.length * 5;
/* 273 */     for (int i = 0; i < max; i += 5) {
/* 274 */       if (this.data[i + 2].equals(qName)) {
/* 275 */         return i / 5;
/*     */       }
/*     */     } 
/* 278 */     return -1;
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
/*     */   public String getType(String uri, String localName) {
/* 294 */     int max = this.length * 5;
/* 295 */     for (int i = 0; i < max; i += 5) {
/* 296 */       if (this.data[i].equals(uri) && this.data[i + 1].equals(localName)) {
/* 297 */         return this.data[i + 3];
/*     */       }
/*     */     } 
/* 300 */     return null;
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
/*     */   public String getType(String qName) {
/* 314 */     int max = this.length * 5;
/* 315 */     for (int i = 0; i < max; i += 5) {
/* 316 */       if (this.data[i + 2].equals(qName)) {
/* 317 */         return this.data[i + 3];
/*     */       }
/*     */     } 
/* 320 */     return null;
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
/*     */   public String getValue(String uri, String localName) {
/* 336 */     int max = this.length * 5;
/* 337 */     for (int i = 0; i < max; i += 5) {
/* 338 */       if (this.data[i].equals(uri) && this.data[i + 1].equals(localName)) {
/* 339 */         return this.data[i + 4];
/*     */       }
/*     */     } 
/* 342 */     return null;
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
/*     */   public String getValue(String qName) {
/* 356 */     int max = this.length * 5;
/* 357 */     for (int i = 0; i < max; i += 5) {
/* 358 */       if (this.data[i + 2].equals(qName)) {
/* 359 */         return this.data[i + 4];
/*     */       }
/*     */     } 
/* 362 */     return null;
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
/*     */   public void clear() {
/* 381 */     if (this.data != null)
/* 382 */       for (int i = 0; i < this.length * 5; i++) {
/* 383 */         this.data[i] = null;
/*     */       } 
/* 385 */     this.length = 0;
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
/*     */   public void setAttributes(Attributes atts) {
/* 399 */     clear();
/* 400 */     this.length = atts.getLength();
/* 401 */     if (this.length > 0) {
/* 402 */       this.data = new String[this.length * 5];
/* 403 */       for (int i = 0; i < this.length; i++) {
/* 404 */         this.data[i * 5] = atts.getURI(i);
/* 405 */         this.data[i * 5 + 1] = atts.getLocalName(i);
/* 406 */         this.data[i * 5 + 2] = atts.getQName(i);
/* 407 */         this.data[i * 5 + 3] = atts.getType(i);
/* 408 */         this.data[i * 5 + 4] = atts.getValue(i);
/*     */       } 
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
/*     */   public void addAttribute(String uri, String localName, String qName, String type, String value) {
/* 434 */     ensureCapacity(this.length + 1);
/* 435 */     this.data[this.length * 5] = uri;
/* 436 */     this.data[this.length * 5 + 1] = localName;
/* 437 */     this.data[this.length * 5 + 2] = qName;
/* 438 */     this.data[this.length * 5 + 3] = type;
/* 439 */     this.data[this.length * 5 + 4] = value;
/* 440 */     this.length++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(int index, String uri, String localName, String qName, String type, String value) {
/* 468 */     if (index >= 0 && index < this.length) {
/* 469 */       this.data[index * 5] = uri;
/* 470 */       this.data[index * 5 + 1] = localName;
/* 471 */       this.data[index * 5 + 2] = qName;
/* 472 */       this.data[index * 5 + 3] = type;
/* 473 */       this.data[index * 5 + 4] = value;
/*     */     } else {
/* 475 */       badIndex(index);
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
/*     */ 
/*     */   
/*     */   public void removeAttribute(int index) {
/* 490 */     if (index >= 0 && index < this.length) {
/* 491 */       if (index < this.length - 1) {
/* 492 */         System.arraycopy(this.data, (index + 1) * 5, this.data, index * 5, (this.length - index - 1) * 5);
/*     */       }
/*     */       
/* 495 */       index = (this.length - 1) * 5;
/* 496 */       this.data[index++] = null;
/* 497 */       this.data[index++] = null;
/* 498 */       this.data[index++] = null;
/* 499 */       this.data[index++] = null;
/* 500 */       this.data[index] = null;
/* 501 */       this.length--;
/*     */     } else {
/* 503 */       badIndex(index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURI(int index, String uri) {
/* 520 */     if (index >= 0 && index < this.length) {
/* 521 */       this.data[index * 5] = uri;
/*     */     } else {
/* 523 */       badIndex(index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalName(int index, String localName) {
/* 540 */     if (index >= 0 && index < this.length) {
/* 541 */       this.data[index * 5 + 1] = localName;
/*     */     } else {
/* 543 */       badIndex(index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQName(int index, String qName) {
/* 560 */     if (index >= 0 && index < this.length) {
/* 561 */       this.data[index * 5 + 2] = qName;
/*     */     } else {
/* 563 */       badIndex(index);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(int index, String type) {
/* 579 */     if (index >= 0 && index < this.length) {
/* 580 */       this.data[index * 5 + 3] = type;
/*     */     } else {
/* 582 */       badIndex(index);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int index, String value) {
/* 598 */     if (index >= 0 && index < this.length) {
/* 599 */       this.data[index * 5 + 4] = value;
/*     */     } else {
/* 601 */       badIndex(index);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureCapacity(int n) {
/*     */     int max;
/* 619 */     if (n <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 623 */     if (this.data == null || this.data.length == 0) {
/* 624 */       max = 25;
/*     */     } else {
/* 626 */       if (this.data.length >= n * 5) {
/*     */         return;
/*     */       }
/*     */       
/* 630 */       max = this.data.length;
/*     */     } 
/* 632 */     while (max < n * 5) {
/* 633 */       max *= 2;
/*     */     }
/*     */     
/* 636 */     String[] newData = new String[max];
/* 637 */     if (this.length > 0) {
/* 638 */       System.arraycopy(this.data, 0, newData, 0, this.length * 5);
/*     */     }
/* 640 */     this.data = newData;
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
/*     */   private void badIndex(int index) throws ArrayIndexOutOfBoundsException {
/* 653 */     String msg = "Attempt to modify attribute at illegal index: " + index;
/*     */     
/* 655 */     throw new ArrayIndexOutOfBoundsException(msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bin\\util\AttributesImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */