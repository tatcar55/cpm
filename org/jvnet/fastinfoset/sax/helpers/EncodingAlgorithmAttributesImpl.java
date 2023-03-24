/*     */ package org.jvnet.fastinfoset.sax.helpers;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
/*     */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*     */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
/*     */ import org.jvnet.fastinfoset.sax.EncodingAlgorithmAttributes;
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
/*     */ public class EncodingAlgorithmAttributesImpl
/*     */   implements EncodingAlgorithmAttributes
/*     */ {
/*     */   private static final int DEFAULT_CAPACITY = 8;
/*     */   private static final int URI_OFFSET = 0;
/*     */   private static final int LOCALNAME_OFFSET = 1;
/*     */   private static final int QNAME_OFFSET = 2;
/*     */   private static final int TYPE_OFFSET = 3;
/*     */   private static final int VALUE_OFFSET = 4;
/*     */   private static final int ALGORITHMURI_OFFSET = 5;
/*     */   private static final int SIZE = 6;
/*     */   private Map _registeredEncodingAlgorithms;
/*     */   private int _length;
/*     */   private String[] _data;
/*     */   private int[] _algorithmIds;
/*     */   private Object[] _algorithmData;
/*     */   private String[] _alphabets;
/*     */   private boolean[] _toIndex;
/*     */   
/*     */   public EncodingAlgorithmAttributesImpl() {
/*  79 */     this(null, null);
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
/*     */   public EncodingAlgorithmAttributesImpl(Attributes attributes) {
/*  91 */     this(null, attributes);
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
/*     */   public EncodingAlgorithmAttributesImpl(Map registeredEncodingAlgorithms, Attributes attributes) {
/* 106 */     this._data = new String[48];
/* 107 */     this._algorithmIds = new int[8];
/* 108 */     this._algorithmData = new Object[8];
/* 109 */     this._alphabets = new String[8];
/* 110 */     this._toIndex = new boolean[8];
/*     */     
/* 112 */     this._registeredEncodingAlgorithms = registeredEncodingAlgorithms;
/*     */     
/* 114 */     if (attributes != null) {
/* 115 */       if (attributes instanceof EncodingAlgorithmAttributes) {
/* 116 */         setAttributes((EncodingAlgorithmAttributes)attributes);
/*     */       } else {
/* 118 */         setAttributes(attributes);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clear() {
/* 128 */     for (int i = 0; i < this._length; i++) {
/* 129 */       this._data[i * 6 + 4] = null;
/* 130 */       this._algorithmData[i] = null;
/*     */     } 
/* 132 */     this._length = 0;
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
/*     */   public void addAttribute(String URI, String localName, String qName, String type, String value) {
/* 154 */     if (this._length >= this._algorithmData.length) {
/* 155 */       resize();
/*     */     }
/*     */     
/* 158 */     int i = this._length * 6;
/* 159 */     this._data[i++] = replaceNull(URI);
/* 160 */     this._data[i++] = replaceNull(localName);
/* 161 */     this._data[i++] = replaceNull(qName);
/* 162 */     this._data[i++] = replaceNull(type);
/* 163 */     this._data[i++] = replaceNull(value);
/* 164 */     this._toIndex[this._length] = false;
/* 165 */     this._alphabets[this._length] = null;
/*     */     
/* 167 */     this._length++;
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
/*     */   public void addAttribute(String URI, String localName, String qName, String type, String value, boolean index, String alphabet) {
/* 192 */     if (this._length >= this._algorithmData.length) {
/* 193 */       resize();
/*     */     }
/*     */     
/* 196 */     int i = this._length * 6;
/* 197 */     this._data[i++] = replaceNull(URI);
/* 198 */     this._data[i++] = replaceNull(localName);
/* 199 */     this._data[i++] = replaceNull(qName);
/* 200 */     this._data[i++] = replaceNull(type);
/* 201 */     this._data[i++] = replaceNull(value);
/* 202 */     this._toIndex[this._length] = index;
/* 203 */     this._alphabets[this._length] = alphabet;
/*     */     
/* 205 */     this._length++;
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
/*     */   public void addAttributeWithBuiltInAlgorithmData(String URI, String localName, String qName, int builtInAlgorithmID, Object algorithmData) {
/* 227 */     if (this._length >= this._algorithmData.length) {
/* 228 */       resize();
/*     */     }
/*     */     
/* 231 */     int i = this._length * 6;
/* 232 */     this._data[i++] = replaceNull(URI);
/* 233 */     this._data[i++] = replaceNull(localName);
/* 234 */     this._data[i++] = replaceNull(qName);
/* 235 */     this._data[i++] = "CDATA";
/* 236 */     this._data[i++] = "";
/* 237 */     this._data[i++] = null;
/* 238 */     this._algorithmIds[this._length] = builtInAlgorithmID;
/* 239 */     this._algorithmData[this._length] = algorithmData;
/* 240 */     this._toIndex[this._length] = false;
/* 241 */     this._alphabets[this._length] = null;
/*     */     
/* 243 */     this._length++;
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
/*     */   public void addAttributeWithAlgorithmData(String URI, String localName, String qName, String algorithmURI, int algorithmID, Object algorithmData) {
/* 266 */     if (this._length >= this._algorithmData.length) {
/* 267 */       resize();
/*     */     }
/*     */     
/* 270 */     int i = this._length * 6;
/* 271 */     this._data[i++] = replaceNull(URI);
/* 272 */     this._data[i++] = replaceNull(localName);
/* 273 */     this._data[i++] = replaceNull(qName);
/* 274 */     this._data[i++] = "CDATA";
/* 275 */     this._data[i++] = "";
/* 276 */     this._data[i++] = algorithmURI;
/* 277 */     this._algorithmIds[this._length] = algorithmID;
/* 278 */     this._algorithmData[this._length] = algorithmData;
/* 279 */     this._toIndex[this._length] = false;
/* 280 */     this._alphabets[this._length] = null;
/*     */     
/* 282 */     this._length++;
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
/*     */   public void replaceWithAttributeAlgorithmData(int index, String algorithmURI, int algorithmID, Object algorithmData) {
/* 299 */     if (index < 0 || index >= this._length)
/*     */       return; 
/* 301 */     int i = index * 6;
/* 302 */     this._data[i + 4] = null;
/* 303 */     this._data[i + 5] = algorithmURI;
/* 304 */     this._algorithmIds[index] = algorithmID;
/* 305 */     this._algorithmData[index] = algorithmData;
/* 306 */     this._toIndex[index] = false;
/* 307 */     this._alphabets[index] = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributes(Attributes atts) {
/* 316 */     this._length = atts.getLength();
/* 317 */     if (this._length > 0) {
/*     */       
/* 319 */       if (this._length >= this._algorithmData.length) {
/* 320 */         resizeNoCopy();
/*     */       }
/*     */       
/* 323 */       int index = 0;
/* 324 */       for (int i = 0; i < this._length; i++) {
/* 325 */         this._data[index++] = atts.getURI(i);
/* 326 */         this._data[index++] = atts.getLocalName(i);
/* 327 */         this._data[index++] = atts.getQName(i);
/* 328 */         this._data[index++] = atts.getType(i);
/* 329 */         this._data[index++] = atts.getValue(i);
/* 330 */         index++;
/* 331 */         this._toIndex[i] = false;
/* 332 */         this._alphabets[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributes(EncodingAlgorithmAttributes atts) {
/* 343 */     this._length = atts.getLength();
/* 344 */     if (this._length > 0) {
/*     */       
/* 346 */       if (this._length >= this._algorithmData.length) {
/* 347 */         resizeNoCopy();
/*     */       }
/*     */       
/* 350 */       int index = 0;
/* 351 */       for (int i = 0; i < this._length; i++) {
/* 352 */         this._data[index++] = atts.getURI(i);
/* 353 */         this._data[index++] = atts.getLocalName(i);
/* 354 */         this._data[index++] = atts.getQName(i);
/* 355 */         this._data[index++] = atts.getType(i);
/* 356 */         this._data[index++] = atts.getValue(i);
/* 357 */         this._data[index++] = atts.getAlgorithmURI(i);
/* 358 */         this._algorithmIds[i] = atts.getAlgorithmIndex(i);
/* 359 */         this._algorithmData[i] = atts.getAlgorithmData(i);
/* 360 */         this._toIndex[i] = false;
/* 361 */         this._alphabets[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getLength() {
/* 369 */     return this._length;
/*     */   }
/*     */   
/*     */   public final String getLocalName(int index) {
/* 373 */     if (index >= 0 && index < this._length) {
/* 374 */       return this._data[index * 6 + 1];
/*     */     }
/* 376 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getQName(int index) {
/* 381 */     if (index >= 0 && index < this._length) {
/* 382 */       return this._data[index * 6 + 2];
/*     */     }
/* 384 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getType(int index) {
/* 389 */     if (index >= 0 && index < this._length) {
/* 390 */       return this._data[index * 6 + 3];
/*     */     }
/* 392 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getURI(int index) {
/* 397 */     if (index >= 0 && index < this._length) {
/* 398 */       return this._data[index * 6 + 0];
/*     */     }
/* 400 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getValue(int index) {
/* 405 */     if (index >= 0 && index < this._length) {
/* 406 */       String value = this._data[index * 6 + 4];
/* 407 */       if (value != null) return value; 
/*     */     } else {
/* 409 */       return null;
/*     */     } 
/*     */     
/* 412 */     if (this._algorithmData[index] == null || this._registeredEncodingAlgorithms == null) {
/* 413 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 417 */       this._data[index * 6 + 4] = convertEncodingAlgorithmDataToString(this._algorithmIds[index], this._data[index * 6 + 5], this._algorithmData[index]).toString(); return convertEncodingAlgorithmDataToString(this._algorithmIds[index], this._data[index * 6 + 5], this._algorithmData[index]).toString();
/*     */ 
/*     */     
/*     */     }
/* 421 */     catch (IOException e) {
/* 422 */       return null;
/* 423 */     } catch (FastInfosetException e) {
/* 424 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final int getIndex(String qName) {
/* 429 */     for (int index = 0; index < this._length; index++) {
/* 430 */       if (qName.equals(this._data[index * 6 + 2])) {
/* 431 */         return index;
/*     */       }
/*     */     } 
/* 434 */     return -1;
/*     */   }
/*     */   
/*     */   public final String getType(String qName) {
/* 438 */     int index = getIndex(qName);
/* 439 */     if (index >= 0) {
/* 440 */       return this._data[index * 6 + 3];
/*     */     }
/* 442 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getValue(String qName) {
/* 447 */     int index = getIndex(qName);
/* 448 */     if (index >= 0) {
/* 449 */       return getValue(index);
/*     */     }
/* 451 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getIndex(String uri, String localName) {
/* 456 */     for (int index = 0; index < this._length; index++) {
/* 457 */       if (localName.equals(this._data[index * 6 + 1]) && uri.equals(this._data[index * 6 + 0]))
/*     */       {
/* 459 */         return index;
/*     */       }
/*     */     } 
/* 462 */     return -1;
/*     */   }
/*     */   
/*     */   public final String getType(String uri, String localName) {
/* 466 */     int index = getIndex(uri, localName);
/* 467 */     if (index >= 0) {
/* 468 */       return this._data[index * 6 + 3];
/*     */     }
/* 470 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getValue(String uri, String localName) {
/* 475 */     int index = getIndex(uri, localName);
/* 476 */     if (index >= 0) {
/* 477 */       return getValue(index);
/*     */     }
/* 479 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAlgorithmURI(int index) {
/* 486 */     if (index >= 0 && index < this._length) {
/* 487 */       return this._data[index * 6 + 5];
/*     */     }
/* 489 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getAlgorithmIndex(int index) {
/* 494 */     if (index >= 0 && index < this._length) {
/* 495 */       return this._algorithmIds[index];
/*     */     }
/* 497 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object getAlgorithmData(int index) {
/* 502 */     if (index >= 0 && index < this._length) {
/* 503 */       return this._algorithmData[index];
/*     */     }
/* 505 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAlpababet(int index) {
/* 512 */     if (index >= 0 && index < this._length) {
/* 513 */       return this._alphabets[index];
/*     */     }
/* 515 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean getToIndex(int index) {
/* 520 */     if (index >= 0 && index < this._length) {
/* 521 */       return this._toIndex[index];
/*     */     }
/* 523 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String replaceNull(String s) {
/* 530 */     return (s != null) ? s : "";
/*     */   }
/*     */   
/*     */   private final void resizeNoCopy() {
/* 534 */     int newLength = this._length * 3 / 2 + 1;
/*     */     
/* 536 */     this._data = new String[newLength * 6];
/* 537 */     this._algorithmIds = new int[newLength];
/* 538 */     this._algorithmData = new Object[newLength];
/*     */   }
/*     */   
/*     */   private final void resize() {
/* 542 */     int newLength = this._length * 3 / 2 + 1;
/*     */     
/* 544 */     String[] data = new String[newLength * 6];
/* 545 */     int[] algorithmIds = new int[newLength];
/* 546 */     Object[] algorithmData = new Object[newLength];
/* 547 */     String[] alphabets = new String[newLength];
/* 548 */     boolean[] toIndex = new boolean[newLength];
/*     */     
/* 550 */     System.arraycopy(this._data, 0, data, 0, this._length * 6);
/* 551 */     System.arraycopy(this._algorithmIds, 0, algorithmIds, 0, this._length);
/* 552 */     System.arraycopy(this._algorithmData, 0, algorithmData, 0, this._length);
/* 553 */     System.arraycopy(this._alphabets, 0, alphabets, 0, this._length);
/* 554 */     System.arraycopy(this._toIndex, 0, toIndex, 0, this._length);
/*     */     
/* 556 */     this._data = data;
/* 557 */     this._algorithmIds = algorithmIds;
/* 558 */     this._algorithmData = algorithmData;
/* 559 */     this._alphabets = alphabets;
/* 560 */     this._toIndex = toIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   private final StringBuffer convertEncodingAlgorithmDataToString(int identifier, String URI, Object data) throws FastInfosetException, IOException {
/* 565 */     EncodingAlgorithm ea = null;
/* 566 */     if (identifier < 9)
/* 567 */     { BuiltInEncodingAlgorithm builtInEncodingAlgorithm = BuiltInEncodingAlgorithmFactory.getAlgorithm(identifier); }
/* 568 */     else { if (identifier == 9) {
/* 569 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/*     */       }
/* 571 */       if (identifier >= 32) {
/* 572 */         if (URI == null) {
/* 573 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent") + identifier);
/*     */         }
/*     */ 
/*     */         
/* 577 */         ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 578 */         if (ea == null) {
/* 579 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.algorithmNotRegistered") + URI);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 586 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*     */       }  }
/*     */ 
/*     */     
/* 590 */     StringBuffer sb = new StringBuffer();
/* 591 */     ea.convertToCharacters(data, sb);
/* 592 */     return sb;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\helpers\EncodingAlgorithmAttributesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */