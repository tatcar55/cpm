/*     */ package com.ctc.wstx.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymbolTable
/*     */ {
/*     */   protected static final int DEFAULT_TABLE_SIZE = 128;
/*     */   protected static final float DEFAULT_FILL_FACTOR = 0.75F;
/*     */   protected static final String EMPTY_STRING = "";
/*     */   protected boolean mInternStrings;
/*     */   protected String[] mSymbols;
/*     */   protected Bucket[] mBuckets;
/*     */   protected int mSize;
/*     */   protected int mSizeThreshold;
/*     */   protected int mIndexMask;
/*     */   protected int mThisVersion;
/*     */   protected boolean mDirty;
/*     */   
/*     */   public SymbolTable() {
/* 161 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolTable(boolean internStrings) {
/* 168 */     this(internStrings, 128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolTable(boolean internStrings, int initialSize) {
/* 175 */     this(internStrings, initialSize, 0.75F);
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
/*     */   public SymbolTable(boolean internStrings, int initialSize, float fillFactor) {
/* 191 */     this.mInternStrings = internStrings;
/*     */     
/* 193 */     this.mThisVersion = 1;
/*     */     
/* 195 */     this.mDirty = true;
/*     */ 
/*     */     
/* 198 */     if (initialSize < 1) {
/* 199 */       throw new IllegalArgumentException("Can not use negative/zero initial size: " + initialSize);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     int currSize = 4;
/* 206 */     while (currSize < initialSize) {
/* 207 */       currSize += currSize;
/*     */     }
/* 209 */     initialSize = currSize;
/*     */ 
/*     */     
/* 212 */     this.mSymbols = new String[initialSize];
/* 213 */     this.mBuckets = new Bucket[initialSize >> 1];
/*     */     
/* 215 */     this.mIndexMask = initialSize - 1;
/* 216 */     this.mSize = 0;
/*     */ 
/*     */     
/* 219 */     if (fillFactor < 0.01F) {
/* 220 */       throw new IllegalArgumentException("Fill factor can not be lower than 0.01.");
/*     */     }
/* 222 */     if (fillFactor > 10.0F) {
/* 223 */       throw new IllegalArgumentException("Fill factor can not be higher than 10.0.");
/*     */     }
/* 225 */     this.mSizeThreshold = (int)((initialSize * fillFactor) + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SymbolTable(boolean internStrings, String[] symbols, Bucket[] buckets, int size, int sizeThreshold, int indexMask, int version) {
/* 235 */     this.mInternStrings = internStrings;
/* 236 */     this.mSymbols = symbols;
/* 237 */     this.mBuckets = buckets;
/* 238 */     this.mSize = size;
/* 239 */     this.mSizeThreshold = sizeThreshold;
/* 240 */     this.mIndexMask = indexMask;
/* 241 */     this.mThisVersion = version;
/*     */ 
/*     */     
/* 244 */     this.mDirty = false;
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
/*     */   public synchronized SymbolTable makeChild() {
/* 260 */     return new SymbolTable(this.mInternStrings, this.mSymbols, this.mBuckets, this.mSize, this.mSizeThreshold, this.mIndexMask, this.mThisVersion + 1);
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
/*     */   public synchronized void mergeChild(SymbolTable child) {
/* 275 */     if (child.size() <= size()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 280 */     this.mSymbols = child.mSymbols;
/* 281 */     this.mBuckets = child.mBuckets;
/* 282 */     this.mSize = child.mSize;
/* 283 */     this.mSizeThreshold = child.mSizeThreshold;
/* 284 */     this.mIndexMask = child.mIndexMask;
/* 285 */     this.mThisVersion++;
/*     */ 
/*     */ 
/*     */     
/* 289 */     this.mDirty = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     child.mDirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInternStrings(boolean state) {
/* 307 */     this.mInternStrings = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 316 */     return this.mSize;
/*     */   } public int version() {
/* 318 */     return this.mThisVersion;
/*     */   } public boolean isDirty() {
/* 320 */     return this.mDirty;
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
/*     */   public boolean isDirectChildOf(SymbolTable t) {
/* 333 */     if (this.mThisVersion == t.mThisVersion + 1) {
/* 334 */       return true;
/*     */     }
/* 336 */     return false;
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
/*     */   public String findSymbol(char[] buffer, int start, int len, int hash) {
/* 361 */     if (len < 1) {
/* 362 */       return "";
/*     */     }
/*     */     
/* 365 */     hash &= this.mIndexMask;
/*     */     
/* 367 */     String sym = this.mSymbols[hash];
/*     */ 
/*     */     
/* 370 */     if (sym != null) {
/*     */       
/* 372 */       if (sym.length() == len) {
/* 373 */         int i = 0;
/*     */         
/* 375 */         while (sym.charAt(i) == buffer[start + i]) {
/*     */ 
/*     */           
/* 378 */           if (++i >= len)
/*     */             break; 
/* 380 */         }  if (i == len) {
/* 381 */           return sym;
/*     */         }
/*     */       } 
/*     */       
/* 385 */       Bucket b = this.mBuckets[hash >> 1];
/* 386 */       if (b != null) {
/* 387 */         sym = b.find(buffer, start, len);
/* 388 */         if (sym != null) {
/* 389 */           return sym;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 395 */     if (this.mSize >= this.mSizeThreshold) {
/* 396 */       rehash();
/*     */ 
/*     */ 
/*     */       
/* 400 */       hash = calcHash(buffer, start, len) & this.mIndexMask;
/* 401 */     } else if (!this.mDirty) {
/*     */       
/* 403 */       copyArrays();
/* 404 */       this.mDirty = true;
/*     */     } 
/* 406 */     this.mSize++;
/*     */     
/* 408 */     String newSymbol = new String(buffer, start, len);
/* 409 */     if (this.mInternStrings) {
/* 410 */       newSymbol = newSymbol.intern();
/*     */     }
/*     */     
/* 413 */     if (this.mSymbols[hash] == null) {
/* 414 */       this.mSymbols[hash] = newSymbol;
/*     */     } else {
/* 416 */       int bix = hash >> 1;
/* 417 */       this.mBuckets[bix] = new Bucket(newSymbol, this.mBuckets[bix]);
/*     */     } 
/*     */     
/* 420 */     return newSymbol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findSymbolIfExists(char[] buffer, int start, int len, int hash) {
/* 430 */     if (len < 1) {
/* 431 */       return "";
/*     */     }
/* 433 */     hash &= this.mIndexMask;
/*     */     
/* 435 */     String sym = this.mSymbols[hash];
/*     */     
/* 437 */     if (sym != null) {
/*     */       
/* 439 */       if (sym.length() == len) {
/* 440 */         int i = 0;
/*     */         
/* 442 */         while (sym.charAt(i) == buffer[start + i]) {
/*     */ 
/*     */           
/* 445 */           if (++i >= len)
/*     */             break; 
/* 447 */         }  if (i == len) {
/* 448 */           return sym;
/*     */         }
/*     */       } 
/*     */       
/* 452 */       Bucket b = this.mBuckets[hash >> 1];
/* 453 */       if (b != null) {
/* 454 */         sym = b.find(buffer, start, len);
/* 455 */         if (sym != null) {
/* 456 */           return sym;
/*     */         }
/*     */       } 
/*     */     } 
/* 460 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findSymbol(String str) {
/* 470 */     int len = str.length();
/*     */     
/* 472 */     if (len < 1) {
/* 473 */       return "";
/*     */     }
/*     */     
/* 476 */     int index = calcHash(str) & this.mIndexMask;
/* 477 */     String sym = this.mSymbols[index];
/*     */ 
/*     */     
/* 480 */     if (sym != null) {
/*     */       
/* 482 */       if (sym.length() == len) {
/* 483 */         int i = 0;
/* 484 */         for (; i < len && 
/* 485 */           sym.charAt(i) == str.charAt(i); i++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 490 */         if (i == len) {
/* 491 */           return sym;
/*     */         }
/*     */       } 
/*     */       
/* 495 */       Bucket b = this.mBuckets[index >> 1];
/* 496 */       if (b != null) {
/* 497 */         sym = b.find(str);
/* 498 */         if (sym != null) {
/* 499 */           return sym;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 505 */     if (this.mSize >= this.mSizeThreshold) {
/* 506 */       rehash();
/*     */ 
/*     */ 
/*     */       
/* 510 */       index = calcHash(str) & this.mIndexMask;
/* 511 */     } else if (!this.mDirty) {
/*     */       
/* 513 */       copyArrays();
/* 514 */       this.mDirty = true;
/*     */     } 
/* 516 */     this.mSize++;
/*     */     
/* 518 */     if (this.mInternStrings) {
/* 519 */       str = str.intern();
/*     */     }
/*     */     
/* 522 */     if (this.mSymbols[index] == null) {
/* 523 */       this.mSymbols[index] = str;
/*     */     } else {
/* 525 */       int bix = index >> 1;
/* 526 */       this.mBuckets[bix] = new Bucket(str, this.mBuckets[bix]);
/*     */     } 
/*     */     
/* 529 */     return str;
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
/*     */   public static int calcHash(char[] buffer, int start, int len) {
/* 542 */     int hash = buffer[0];
/* 543 */     for (int i = 1; i < len; i++) {
/* 544 */       hash = hash * 31 + buffer[i];
/*     */     }
/* 546 */     return hash;
/*     */   }
/*     */   
/*     */   public static int calcHash(String key) {
/* 550 */     int hash = key.charAt(0);
/* 551 */     for (int i = 1, len = key.length(); i < len; i++) {
/* 552 */       hash = hash * 31 + key.charAt(i);
/*     */     }
/*     */     
/* 555 */     return hash;
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
/*     */   private void copyArrays() {
/* 569 */     String[] oldSyms = this.mSymbols;
/* 570 */     int size = oldSyms.length;
/* 571 */     this.mSymbols = new String[size];
/* 572 */     System.arraycopy(oldSyms, 0, this.mSymbols, 0, size);
/* 573 */     Bucket[] oldBuckets = this.mBuckets;
/* 574 */     size = oldBuckets.length;
/* 575 */     this.mBuckets = new Bucket[size];
/* 576 */     System.arraycopy(oldBuckets, 0, this.mBuckets, 0, size);
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
/*     */   private void rehash() {
/* 588 */     int size = this.mSymbols.length;
/* 589 */     int newSize = size + size;
/* 590 */     String[] oldSyms = this.mSymbols;
/* 591 */     Bucket[] oldBuckets = this.mBuckets;
/* 592 */     this.mSymbols = new String[newSize];
/* 593 */     this.mBuckets = new Bucket[newSize >> 1];
/*     */     
/* 595 */     this.mIndexMask = newSize - 1;
/* 596 */     this.mSizeThreshold += this.mSizeThreshold;
/*     */     
/* 598 */     int count = 0;
/*     */ 
/*     */     
/*     */     int i;
/*     */     
/* 603 */     for (i = 0; i < size; i++) {
/* 604 */       String symbol = oldSyms[i];
/* 605 */       if (symbol != null) {
/* 606 */         count++;
/* 607 */         int index = calcHash(symbol) & this.mIndexMask;
/* 608 */         if (this.mSymbols[index] == null) {
/* 609 */           this.mSymbols[index] = symbol;
/*     */         } else {
/* 611 */           int bix = index >> 1;
/* 612 */           this.mBuckets[bix] = new Bucket(symbol, this.mBuckets[bix]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 617 */     size >>= 1;
/* 618 */     for (i = 0; i < size; i++) {
/* 619 */       Bucket b = oldBuckets[i];
/* 620 */       while (b != null) {
/* 621 */         count++;
/* 622 */         String symbol = b.getSymbol();
/* 623 */         int index = calcHash(symbol) & this.mIndexMask;
/* 624 */         if (this.mSymbols[index] == null) {
/* 625 */           this.mSymbols[index] = symbol;
/*     */         } else {
/* 627 */           int bix = index >> 1;
/* 628 */           this.mBuckets[bix] = new Bucket(symbol, this.mBuckets[bix]);
/*     */         } 
/* 630 */         b = b.getNext();
/*     */       } 
/*     */     } 
/*     */     
/* 634 */     if (count != this.mSize) {
/* 635 */       throw new IllegalStateException("Internal error on SymbolTable.rehash(): had " + this.mSize + " entries; now have " + count + ".");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calcAvgSeek() {
/* 646 */     int count = 0;
/*     */     int i, len;
/* 648 */     for (i = 0, len = this.mSymbols.length; i < len; i++) {
/* 649 */       if (this.mSymbols[i] != null) {
/* 650 */         count++;
/*     */       }
/*     */     } 
/*     */     
/* 654 */     for (i = 0, len = this.mBuckets.length; i < len; i++) {
/* 655 */       Bucket b = this.mBuckets[i];
/* 656 */       int cost = 2;
/* 657 */       while (b != null) {
/* 658 */         count += cost;
/* 659 */         cost++;
/* 660 */         b = b.getNext();
/*     */       } 
/*     */     } 
/*     */     
/* 664 */     return count / this.mSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Bucket
/*     */   {
/*     */     private final String mSymbol;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Bucket mNext;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Bucket(String symbol, Bucket next) {
/* 682 */       this.mSymbol = symbol;
/* 683 */       this.mNext = next;
/*     */     }
/*     */     
/* 686 */     public String getSymbol() { return this.mSymbol; } public Bucket getNext() {
/* 687 */       return this.mNext;
/*     */     }
/*     */     public String find(char[] buf, int start, int len) {
/* 690 */       String sym = this.mSymbol;
/* 691 */       Bucket b = this.mNext;
/*     */       
/*     */       while (true) {
/* 694 */         if (sym.length() == len) {
/* 695 */           int i = 0;
/*     */           
/* 697 */           while (sym.charAt(i) == buf[start + i])
/*     */           
/*     */           { 
/* 700 */             if (++i >= len)
/* 701 */               break;  }  if (i == len) {
/* 702 */             return sym;
/*     */           }
/*     */         } 
/* 705 */         if (b == null) {
/*     */           break;
/*     */         }
/* 708 */         sym = b.getSymbol();
/* 709 */         b = b.getNext();
/*     */       } 
/* 711 */       return null;
/*     */     }
/*     */     
/*     */     public String find(String str) {
/* 715 */       String sym = this.mSymbol;
/* 716 */       Bucket b = this.mNext;
/*     */       
/*     */       while (true) {
/* 719 */         if (sym.equals(str)) {
/* 720 */           return sym;
/*     */         }
/* 722 */         if (b == null) {
/*     */           break;
/*     */         }
/* 725 */         sym = b.getSymbol();
/* 726 */         b = b.getNext();
/*     */       } 
/* 728 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wst\\util\SymbolTable.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */