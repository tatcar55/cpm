/*     */ package com.sun.xml.rpc.sp;
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
/*     */ final class AttributesExImpl
/*     */   implements AttributesEx
/*     */ {
/*     */   private static final int MAX_ATTRS = 10000;
/*     */   int length;
/*     */   String[] data;
/*     */   private String idAttributeName;
/*     */   private static final String SPECIFIED_TRUE = "";
/*     */   
/*     */   public AttributesExImpl() {
/*  50 */     this.length = 0;
/*  51 */     this.data = null;
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
/*     */   public AttributesExImpl(Attributes atts) {
/*  63 */     setAttributes(atts);
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
/*     */   public int getLength() {
/*  77 */     return this.length;
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
/*     */   public String getURI(int index) {
/*  89 */     if (index >= 0 && index < this.length) {
/*  90 */       return this.data[index * 7];
/*     */     }
/*  92 */     return null;
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
/*     */   public String getLocalName(int index) {
/* 105 */     if (index >= 0 && index < this.length) {
/* 106 */       return this.data[index * 7 + 1];
/*     */     }
/* 108 */     return null;
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
/*     */   public String getQName(int index) {
/* 121 */     if (index >= 0 && index < this.length) {
/* 122 */       return this.data[index * 7 + 2];
/*     */     }
/* 124 */     return null;
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
/*     */   public String getType(int index) {
/* 137 */     if (index >= 0 && index < this.length) {
/* 138 */       return this.data[index * 7 + 3];
/*     */     }
/* 140 */     return null;
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
/*     */   public String getValue(int index) {
/* 152 */     if (index >= 0 && index < this.length) {
/* 153 */       return this.data[index * 7 + 4];
/*     */     }
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefault(int index) {
/* 163 */     if (index >= 0 && index < this.length) {
/* 164 */       return this.data[index * 7 + 5];
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpecified(int index) {
/* 175 */     if (index >= 0 && index < this.length) {
/* 176 */       return (this.data[index * 7 + 6] == "");
/*     */     }
/* 178 */     return false;
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
/*     */   public int getIndex(String uri, String localName) {
/* 196 */     int max = this.length * 7;
/* 197 */     for (int i = 0; i < max; i += 7) {
/* 198 */       if (this.data[i].equals(uri) && this.data[i + 1].equals(localName)) {
/* 199 */         return i / 7;
/*     */       }
/*     */     } 
/* 202 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(String qName) {
/* 213 */     int max = this.length * 7;
/* 214 */     for (int i = 0; i < max; i += 7) {
/* 215 */       if (this.data[i + 2].equals(qName)) {
/* 216 */         return i / 7;
/*     */       }
/*     */     } 
/* 219 */     return -1;
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
/*     */   public String getType(String uri, String localName) {
/* 233 */     int max = this.length * 7;
/* 234 */     for (int i = 0; i < max; i += 7) {
/* 235 */       if (this.data[i].equals(uri) && this.data[i + 1].equals(localName)) {
/* 236 */         return this.data[i + 3];
/*     */       }
/*     */     } 
/* 239 */     return null;
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
/*     */   public String getType(String qName) {
/* 251 */     int max = this.length * 7;
/* 252 */     for (int i = 0; i < max; i += 7) {
/* 253 */       if (this.data[i + 2].equals(qName)) {
/* 254 */         return this.data[i + 3];
/*     */       }
/*     */     } 
/* 257 */     return null;
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
/*     */   public String getValue(String uri, String localName) {
/* 271 */     int max = this.length * 7;
/* 272 */     for (int i = 0; i < max; i += 7) {
/* 273 */       if (this.data[i].equals(uri) && this.data[i + 1].equals(localName)) {
/* 274 */         return this.data[i + 4];
/*     */       }
/*     */     } 
/* 277 */     return null;
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
/*     */   public String getValue(String qName) {
/* 289 */     int max = this.length * 7;
/* 290 */     for (int i = 0; i < max; i += 7) {
/* 291 */       if (this.data[i + 2].equals(qName)) {
/* 292 */         return this.data[i + 4];
/*     */       }
/*     */     } 
/* 295 */     return null;
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
/* 314 */     int max = this.length * 7;
/* 315 */     for (int i = 0; i < max; i++) {
/* 316 */       this.data[i] = null;
/*     */     }
/*     */     
/* 319 */     this.length = 0;
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
/*     */   public void setAttributes(Attributes atts) {
/* 331 */     clear();
/* 332 */     this.length = atts.getLength();
/* 333 */     if (this.length > 0) {
/* 334 */       this.data = new String[this.length * 7];
/* 335 */       for (int i = 0; i < this.length; i++) {
/* 336 */         this.data[i * 7] = atts.getURI(i);
/* 337 */         this.data[i * 7 + 1] = atts.getLocalName(i);
/* 338 */         this.data[i * 7 + 2] = atts.getQName(i);
/* 339 */         this.data[i * 7 + 3] = atts.getType(i);
/* 340 */         this.data[i * 7 + 4] = atts.getValue(i);
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
/*     */ 
/*     */   
/*     */   public void addAttribute(String uri, String localName, String qName, String type, String value) {
/* 368 */     if (ensureCapacity(this.length + 1)) {
/* 369 */       this.data[this.length * 7] = uri;
/* 370 */       this.data[this.length * 7 + 1] = localName;
/* 371 */       this.data[this.length * 7 + 2] = qName;
/* 372 */       this.data[this.length * 7 + 3] = type;
/* 373 */       this.data[this.length * 7 + 4] = value;
/* 374 */       this.length++;
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
/*     */   public void addAttribute(String uri, String localName, String qName, String type, String value, String defaultValue, boolean isSpecified) {
/* 389 */     if (ensureCapacity(this.length + 1)) {
/* 390 */       this.data[this.length * 7] = uri;
/* 391 */       this.data[this.length * 7 + 1] = localName;
/* 392 */       this.data[this.length * 7 + 2] = qName;
/* 393 */       this.data[this.length * 7 + 3] = type;
/* 394 */       this.data[this.length * 7 + 4] = value;
/* 395 */       this.data[this.length * 7 + 5] = defaultValue;
/* 396 */       this.data[this.length * 7 + 6] = isSpecified ? "" : null;
/* 397 */       this.length++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(int index, String uri, String localName, String qName, String type, String value) {
/* 429 */     if (index >= 0 && index < this.length) {
/* 430 */       this.data[index * 7] = uri;
/* 431 */       this.data[index * 7 + 1] = localName;
/* 432 */       this.data[index * 7 + 2] = qName;
/* 433 */       this.data[index * 7 + 3] = type;
/* 434 */       this.data[index * 7 + 4] = value;
/*     */     } else {
/* 436 */       badIndex(index);
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
/*     */   public void setAttribute(int index, String uri, String localName, String qName, String type, String value, String defaultValue, boolean isSpecified) {
/* 449 */     if (index >= 0 && index < this.length) {
/* 450 */       this.data[index * 7] = uri;
/* 451 */       this.data[index * 7 + 1] = localName;
/* 452 */       this.data[index * 7 + 2] = qName;
/* 453 */       this.data[index * 7 + 3] = type;
/* 454 */       this.data[index * 7 + 4] = value;
/* 455 */       this.data[index * 7 + 5] = defaultValue;
/* 456 */       this.data[index * 7 + 6] = isSpecified ? "" : null;
/*     */     } else {
/* 458 */       badIndex(index);
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
/*     */   public void removeAttribute(int index) {
/* 471 */     if (index >= 0 && index < this.length) {
/* 472 */       this.data[index * 7] = null;
/* 473 */       this.data[index * 7 + 1] = null;
/* 474 */       this.data[index * 7 + 2] = null;
/* 475 */       this.data[index * 7 + 3] = null;
/* 476 */       this.data[index * 7 + 4] = null;
/* 477 */       this.data[index * 7 + 5] = null;
/* 478 */       this.data[index * 7 + 6] = null;
/* 479 */       if (index < this.length - 1) {
/* 480 */         System.arraycopy(this.data, (index + 1) * 7, this.data, index * 7, (this.length - index - 1) * 7);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 487 */       this.length--;
/*     */     } else {
/* 489 */       badIndex(index);
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
/*     */   public void setURI(int index, String uri) {
/* 504 */     if (index >= 0 && index < this.length) {
/* 505 */       this.data[index * 7] = uri;
/*     */     } else {
/* 507 */       badIndex(index);
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
/*     */   public void setLocalName(int index, String localName) {
/* 522 */     if (index >= 0 && index < this.length) {
/* 523 */       this.data[index * 7 + 1] = localName;
/*     */     } else {
/* 525 */       badIndex(index);
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
/*     */   public void setQName(int index, String qName) {
/* 540 */     if (index >= 0 && index < this.length) {
/* 541 */       this.data[index * 7 + 2] = qName;
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
/*     */   public void setType(int index, String type) {
/* 557 */     if (index >= 0 && index < this.length) {
/* 558 */       this.data[index * 7 + 3] = type;
/*     */     } else {
/* 560 */       badIndex(index);
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
/*     */   public void setValue(int index, String value) {
/* 574 */     if (index >= 0 && index < this.length) {
/* 575 */       this.data[index * 7 + 4] = value;
/*     */     } else {
/* 577 */       badIndex(index);
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
/*     */   public void setDefault(int index, String defaultValue) {
/* 591 */     if (index >= 0 && index < this.length) {
/* 592 */       this.data[index * 7 + 5] = defaultValue;
/*     */     } else {
/* 594 */       badIndex(index);
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
/*     */   public void setSpecified(int index, boolean specified) {
/* 608 */     if (index >= 0 && index < this.length) {
/* 609 */       this.data[index * 7 + 6] = specified ? "" : null;
/*     */     } else {
/* 611 */       badIndex(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdAttributeName() {
/* 619 */     return this.idAttributeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIdAttributeName(String name) {
/* 626 */     this.idAttributeName = name;
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
/*     */   private boolean ensureCapacity(int n) {
/*     */     int max;
/* 640 */     if (n <= 0) {
/* 641 */       return true;
/*     */     }
/* 643 */     if (n > 10000) {
/* 644 */       return false;
/*     */     }
/*     */     
/* 647 */     if (this.data == null || this.data.length == 0)
/* 648 */     { max = 35; }
/* 649 */     else { if (this.data.length >= n * 7) {
/* 650 */         return true;
/*     */       }
/* 652 */       max = this.data.length; }
/*     */     
/* 654 */     while (max < n * 7) {
/* 655 */       max *= 2;
/*     */     }
/*     */     
/* 658 */     String[] newData = new String[max];
/* 659 */     if (this.length > 0) {
/* 660 */       System.arraycopy(this.data, 0, newData, 0, this.length * 7);
/*     */     }
/* 662 */     this.data = newData;
/* 663 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void badIndex(int index) throws ArrayIndexOutOfBoundsException {
/* 673 */     String msg = "Attempt to modify attribute at illegal index: " + index;
/* 674 */     throw new ArrayIndexOutOfBoundsException(msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\AttributesExImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */