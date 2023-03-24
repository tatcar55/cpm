/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import javax.xml.namespace.NamespaceContext;
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
/*     */ public abstract class BaseCanonicalizer
/*     */ {
/*  84 */   static final byte[] _END_PI = new byte[] { 63, 62 };
/*  85 */   static final byte[] _BEGIN_PI = new byte[] { 60, 63 };
/*  86 */   static final byte[] _END_COMM = new byte[] { 45, 45, 62 };
/*  87 */   static final byte[] _BEGIN_COMM = new byte[] { 60, 33, 45, 45 };
/*  88 */   static final byte[] __XA_ = new byte[] { 38, 35, 120, 65, 59 };
/*  89 */   static final byte[] __X9_ = new byte[] { 38, 35, 120, 57, 59 };
/*  90 */   static final byte[] _QUOT_ = new byte[] { 38, 113, 117, 111, 116, 59 };
/*  91 */   static final byte[] __XD_ = new byte[] { 38, 35, 120, 68, 59 };
/*  92 */   static final byte[] _GT_ = new byte[] { 38, 103, 116, 59 };
/*  93 */   static final byte[] _LT_ = new byte[] { 38, 108, 116, 59 };
/*  94 */   static final byte[] _END_TAG = new byte[] { 60, 47 };
/*  95 */   static final byte[] _AMP_ = new byte[] { 38, 97, 109, 112, 59 };
/*     */   static final String XML = "xml";
/*     */   static final String XMLNS = "xmlns";
/*  98 */   static final byte[] equalsStr = new byte[] { 61, 34 };
/*     */   static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
/*     */   static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
/*     */   static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
/* 102 */   protected ArrayList _attrs = new ArrayList();
/* 103 */   protected ArrayList _nsAttrs = new ArrayList();
/* 104 */   int _attrPos = 0;
/* 105 */   int _attrNSPos = 0;
/* 106 */   protected List _attrResult = null;
/*     */   
/* 108 */   protected List _nsResult = new ArrayList();
/* 109 */   String _defURI = null;
/* 110 */   OutputStream _stream = null;
/* 111 */   boolean[] _ncContextState = new boolean[20];
/* 112 */   StringBuffer _attrName = new StringBuffer();
/* 113 */   int _depth = 0;
/*     */   
/*     */   protected static final int initalCacheSize = 4;
/*     */   boolean _parentNamespacesAdded = false;
/* 117 */   String _elementPrefix = "";
/*     */ 
/*     */   
/*     */   private static boolean debug = false;
/*     */ 
/*     */   
/*     */   public void reset() {
/* 124 */     this._nsResult.clear();
/* 125 */     this._attrResult.clear();
/* 126 */     this._attrPos = 0;
/* 127 */     this._depth = 0;
/* 128 */     this._parentNamespacesAdded = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStream(OutputStream os) {
/* 133 */     this._stream = os;
/*     */   }
/*     */   
/*     */   public OutputStream getOutputStream() {
/* 137 */     return this._stream;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 141 */     if (this._depth >= this._ncContextState.length) {
/* 142 */       boolean[] tmp = new boolean[this._ncContextState.length + 20];
/* 143 */       System.arraycopy(this._ncContextState, 0, tmp, 0, this._ncContextState.length);
/* 144 */       this._ncContextState = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addParentNamespaces(List nsDecls) {
/* 150 */     if (!this._parentNamespacesAdded) {
/*     */       
/* 152 */       this._nsResult.addAll(nsDecls);
/* 153 */       this._parentNamespacesAdded = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected AttributeNS getAttributeNS() {
/* 159 */     if (this._attrNSPos < this._nsAttrs.size()) {
/* 160 */       return this._nsAttrs.get(this._attrNSPos++);
/*     */     }
/* 162 */     for (int i = 0; i < 4; i++) {
/* 163 */       this._nsAttrs.add(new AttributeNS());
/*     */     }
/* 165 */     return this._nsAttrs.get(this._attrNSPos++);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAttributes(Attributes attributes, Iterator<Attribute> itr) throws IOException {
/* 171 */     while (itr.hasNext()) {
/* 172 */       Attribute attr = itr.next();
/* 173 */       int pos = attr.getPosition();
/*     */       
/* 175 */       outputAttrToWriter(attributes.getQName(pos), attributes.getValue(pos), this._stream);
/*     */     } 
/* 177 */     this._attrResult.iterator();
/*     */   }
/*     */   
/*     */   protected void writeAttributesNS(Iterator<AttributeNS> itr) throws IOException {
/* 181 */     while (itr.hasNext()) {
/* 182 */       AttributeNS attr = itr.next();
/* 183 */       String prefix = attr.getPrefix();
/* 184 */       if (prefix.length() != 0) {
/* 185 */         this._attrName.setLength(0);
/* 186 */         this._attrName.append("xmlns:");
/* 187 */         this._attrName.append(prefix);
/* 188 */         prefix = this._attrName.toString();
/*     */       } else {
/* 190 */         prefix = "xmlns";
/*     */       } 
/*     */       
/* 193 */       outputAttrToWriter(prefix, attr.getUri(), this._stream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void outputTextToWriter(char[] text, int start, int length, OutputStream writer) throws IOException {
/* 201 */     byte[] toWrite = null;
/* 202 */     int i = start; while (true) { if (i < start + length) {
/* 203 */         char c = text[i];
/*     */         
/* 205 */         switch (c) {
/*     */           
/*     */           case '&':
/* 208 */             if (Arrays.equals(toWrite, __XD_))
/* 209 */               writer.write(toWrite); 
/* 210 */             toWrite = _AMP_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '<':
/* 215 */             if (Arrays.equals(toWrite, __XD_))
/* 216 */               writer.write(toWrite); 
/* 217 */             toWrite = _LT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '>':
/* 222 */             if (Arrays.equals(toWrite, __XD_))
/* 223 */               writer.write(toWrite); 
/* 224 */             toWrite = _GT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\r':
/* 229 */             if (Arrays.equals(toWrite, __XD_))
/* 230 */               writer.write(toWrite); 
/* 231 */             toWrite = __XD_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\n':
/* 236 */             toWrite = null;
/* 237 */             writeCharToUtf8(c, writer);
/*     */             break;
/*     */           
/*     */           default:
/* 241 */             if (Arrays.equals(toWrite, __XD_)) {
/* 242 */               writer.write(toWrite);
/* 243 */               toWrite = null;
/*     */             } 
/* 245 */             writeCharToUtf8(c, writer); i++;
/*     */             continue;
/*     */         } 
/* 248 */         if (toWrite != null && !Arrays.equals(toWrite, __XD_))
/* 249 */           writer.write(toWrite); 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */       i++; }
/* 254 */      } static final void outputAttrToWriter(String name, String value, OutputStream writer) throws IOException { writer.write(32);
/* 255 */     writeStringToUtf8(name, writer);
/* 256 */     writer.write(equalsStr);
/*     */     
/* 258 */     int length = value.length();
/* 259 */     int i = 0; while (true) { if (i < length)
/* 260 */       { byte[] toWrite; char c = value.charAt(i);
/*     */         
/* 262 */         switch (c) {
/*     */           
/*     */           case '&':
/* 265 */             toWrite = _AMP_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '<':
/* 270 */             toWrite = _LT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 275 */             toWrite = _QUOT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\t':
/* 280 */             toWrite = __X9_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\n':
/* 285 */             toWrite = __XA_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\r':
/* 290 */             toWrite = __XD_;
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 295 */             writeCharToUtf8(c, writer);
/*     */             i++;
/*     */             continue;
/*     */         } 
/* 299 */         writer.write(toWrite); }
/*     */       else { break; }
/*     */        i++; }
/* 302 */      writer.write(34); }
/*     */ 
/*     */   
/*     */   static final void outputAttrToWriter(String prefix, String localName, String value, OutputStream writer) throws IOException {
/* 306 */     writer.write(32);
/* 307 */     if (localName.length() != 0) {
/* 308 */       writeStringToUtf8(prefix, writer);
/* 309 */       writeStringToUtf8(":", writer);
/* 310 */       writeStringToUtf8(localName, writer);
/*     */     } else {
/* 312 */       writeStringToUtf8(prefix, writer);
/*     */     } 
/*     */ 
/*     */     
/* 316 */     writer.write(equalsStr);
/*     */     
/* 318 */     int length = value.length();
/* 319 */     int i = 0; while (true) { if (i < length)
/* 320 */       { byte[] toWrite; char c = value.charAt(i);
/*     */         
/* 322 */         switch (c) {
/*     */           
/*     */           case '&':
/* 325 */             toWrite = _AMP_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '<':
/* 330 */             toWrite = _LT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '"':
/* 335 */             toWrite = _QUOT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\t':
/* 340 */             toWrite = __X9_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\n':
/* 345 */             toWrite = __XA_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\r':
/* 350 */             toWrite = __XD_;
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 355 */             writeCharToUtf8(c, writer);
/*     */             i++;
/*     */             continue;
/*     */         } 
/* 359 */         writer.write(toWrite); }
/*     */       else { break; }
/*     */        i++; }
/* 362 */      writer.write(34);
/*     */   }
/*     */   
/*     */   static final void writeCharToUtf8(char c, OutputStream out) throws IOException {
/*     */     int bias, write;
/* 367 */     if (c <= '') {
/* 368 */       out.write(c);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 373 */     if (c > '߿') {
/* 374 */       char c1 = (char)(c >>> 12);
/* 375 */       write = 224;
/* 376 */       if (c1 > '\000') {
/* 377 */         write |= c1 & 0xF;
/*     */       }
/* 379 */       out.write(write);
/* 380 */       write = 128;
/* 381 */       bias = 63;
/*     */     } else {
/* 383 */       write = 192;
/* 384 */       bias = 31;
/*     */     } 
/* 386 */     char ch = (char)(c >>> 6);
/* 387 */     if (ch > '\000') {
/* 388 */       write |= ch & bias;
/*     */     }
/* 390 */     out.write(write);
/* 391 */     out.write(0x80 | c & 0x3F);
/*     */   }
/*     */ 
/*     */   
/*     */   static final void writeStringToUtf8(String str, OutputStream out) throws IOException {
/* 396 */     int length = str.length();
/* 397 */     int i = 0;
/*     */     
/* 399 */     while (i < length) {
/* 400 */       int bias, write; char c = str.charAt(i++);
/* 401 */       if (c <= '') {
/* 402 */         out.write(c);
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 408 */       if (c > '߿') {
/* 409 */         char c1 = (char)(c >>> 12);
/* 410 */         write = 224;
/* 411 */         if (c1 > '\000') {
/* 412 */           write |= c1 & 0xF;
/*     */         }
/* 414 */         out.write(write);
/* 415 */         write = 128;
/* 416 */         bias = 63;
/*     */       } else {
/* 418 */         write = 192;
/* 419 */         bias = 31;
/*     */       } 
/* 421 */       char ch = (char)(c >>> 6);
/* 422 */       if (ch > '\000') {
/* 423 */         write |= ch & bias;
/*     */       }
/* 425 */       out.write(write);
/* 426 */       out.write(0x80 | c & 0x3F);
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
/*     */   static final void outputPItoWriter(String target, String data, OutputStream writer) throws IOException {
/* 447 */     writer.write(10);
/*     */     
/* 449 */     writer.write(_BEGIN_PI);
/*     */ 
/*     */     
/* 452 */     int length = target.length();
/*     */     int i;
/* 454 */     for (i = 0; i < length; i++) {
/* 455 */       char c = target.charAt(i);
/* 456 */       if (c == '\r') {
/* 457 */         writer.write(__XD_);
/*     */       } else {
/* 459 */         writeCharToUtf8(c, writer);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 465 */     length = data.length();
/*     */     
/* 467 */     if (length > 0) {
/* 468 */       writer.write(32);
/*     */       
/* 470 */       for (i = 0; i < length; i++) {
/* 471 */         char c = data.charAt(i);
/* 472 */         if (c == '\r') {
/* 473 */           writer.write(__XD_);
/*     */         } else {
/* 475 */           writeCharToUtf8(c, writer);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 480 */     writer.write(_END_PI);
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
/*     */   static final void outputCommentToWriter(String data, OutputStream writer) throws IOException {
/* 496 */     writer.write(10);
/*     */     
/* 498 */     writer.write(_BEGIN_COMM);
/*     */ 
/*     */     
/* 501 */     int length = data.length();
/*     */     
/* 503 */     for (int i = 0; i < length; i++) {
/* 504 */       char c = data.charAt(i);
/* 505 */       if (c == '\r') {
/* 506 */         writer.write(__XD_);
/*     */       } else {
/* 508 */         writeCharToUtf8(c, writer);
/*     */       } 
/*     */     } 
/*     */     
/* 512 */     writer.write(_END_COMM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void outputTextToWriter(String text, OutputStream writer) throws IOException {
/* 519 */     byte[] toWrite = null;
/* 520 */     int i = 0; while (true) { if (i < text.length()) {
/* 521 */         char c = text.charAt(i);
/*     */         
/* 523 */         switch (c) {
/*     */           
/*     */           case '&':
/* 526 */             if (Arrays.equals(toWrite, __XD_))
/* 527 */               writer.write(toWrite); 
/* 528 */             toWrite = _AMP_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '<':
/* 533 */             if (Arrays.equals(toWrite, __XD_))
/* 534 */               writer.write(toWrite); 
/* 535 */             toWrite = _LT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '>':
/* 540 */             if (Arrays.equals(toWrite, __XD_))
/* 541 */               writer.write(toWrite); 
/* 542 */             toWrite = _GT_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\r':
/* 547 */             if (Arrays.equals(toWrite, __XD_))
/* 548 */               writer.write(toWrite); 
/* 549 */             toWrite = __XD_;
/*     */             break;
/*     */ 
/*     */           
/*     */           case '\n':
/* 554 */             toWrite = null;
/* 555 */             writeCharToUtf8(c, writer);
/*     */             break;
/*     */           
/*     */           default:
/* 559 */             if (Arrays.equals(toWrite, __XD_)) {
/* 560 */               writer.write(toWrite);
/* 561 */               toWrite = null;
/*     */             } 
/* 563 */             writeCharToUtf8(c, writer); i++;
/*     */             continue;
/*     */         } 
/* 566 */         if (toWrite != null && !Arrays.equals(toWrite, __XD_)) {
/* 567 */           writer.write(toWrite);
/*     */         }
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */       i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean namespaceIsRelative(String namespaceValue) {
/* 580 */     return !namespaceIsAbsolute(namespaceValue);
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
/*     */   public static boolean namespaceIsAbsolute(String namespaceValue) {
/* 594 */     if (namespaceValue.length() == 0) {
/* 595 */       return true;
/*     */     }
/* 597 */     return (namespaceValue.indexOf(':') > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NamespaceContextImpl
/*     */     implements NamespaceContext
/*     */   {
/* 608 */     AttributeNS nsDecl = new AttributeNS();
/* 609 */     HashMap prefixMappings = new HashMap<Object, Object>();
/* 610 */     ArrayList clearDepth = new ArrayList(10);
/*     */     
/*     */     int nsDepth;
/* 613 */     int resizeBy = 10;
/*     */ 
/*     */     
/*     */     public NamespaceContextImpl() {
/* 617 */       for (int i = 0; i < 10; i++) {
/* 618 */         this.clearDepth.add(null);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public AttributeNS getNamespaceDeclaration(String prefix) {
/* 624 */       Stack<AttributeNS> stack = (Stack)this.prefixMappings.get(prefix);
/* 625 */       if (stack == null || stack.empty()) {
/* 626 */         return null;
/*     */       }
/* 628 */       AttributeNS attrNS = stack.peek();
/* 629 */       if (attrNS.isWritten()) {
/* 630 */         if (BaseCanonicalizer.debug) {
/* 631 */           System.out.println("depth " + this.nsDepth + " did not return prefix " + prefix);
/*     */         }
/* 633 */         return null;
/*     */       } 
/* 635 */       if (attrNS != null && !attrNS.isWritten() && !isPrefixRedefined(stack, attrNS)) {
/*     */         
/* 637 */         int tmp = this.nsDepth - 1;
/* 638 */         while (tmp >= 0) {
/* 639 */           BaseCanonicalizer.UsedNSList usedNSList = this.clearDepth.get(tmp);
/* 640 */           if (usedNSList != null && usedNSList.getUsedPrefixList() != null && usedNSList.getUsedPrefixList().contains(prefix)) {
/*     */             
/* 642 */             attrNS = null;
/*     */             break;
/*     */           } 
/* 645 */           tmp--;
/*     */         } 
/*     */       } 
/* 648 */       BaseCanonicalizer.UsedNSList uList = null;
/*     */       
/* 650 */       uList = this.clearDepth.get(this.nsDepth);
/* 651 */       if (uList == null) {
/* 652 */         uList = new BaseCanonicalizer.UsedNSList();
/* 653 */         this.clearDepth.set(this.nsDepth, uList);
/*     */       } 
/* 655 */       if (BaseCanonicalizer.debug) {
/* 656 */         System.out.println("depth " + this.nsDepth + " return prefix " + prefix);
/*     */       }
/* 658 */       uList.getUsedPrefixList().add(prefix);
/* 659 */       return attrNS;
/*     */     }
/*     */ 
/*     */     
/*     */     public void declareNamespace(String prefix, String uri) {
/* 664 */       Stack<Object> nsDecls = (Stack)this.prefixMappings.get(prefix);
/* 665 */       this.nsDecl.setPrefix(prefix);
/* 666 */       this.nsDecl.setUri(uri);
/* 667 */       if (nsDecls == null) {
/* 668 */         nsDecls = new Stack();
/*     */         try {
/* 670 */           nsDecls.add(this.nsDecl.clone());
/* 671 */           this.prefixMappings.put(prefix, nsDecls);
/* 672 */         } catch (CloneNotSupportedException ex) {
/* 673 */           throw new RuntimeException(ex);
/*     */         } 
/* 675 */       } else if (!nsDecls.contains(this.nsDecl)) {
/*     */         try {
/* 677 */           nsDecls.add(this.nsDecl.clone());
/* 678 */         } catch (CloneNotSupportedException ex) {
/* 679 */           throw new RuntimeException(ex);
/*     */         } 
/* 681 */       } else if (nsDecls.contains(this.nsDecl) && "".equals(prefix)) {
/* 682 */         AttributeNS top = (AttributeNS)nsDecls.peek();
/* 683 */         if (!this.nsDecl.equals(top)) {
/*     */           try {
/* 685 */             nsDecls.add(this.nsDecl.clone());
/* 686 */           } catch (CloneNotSupportedException ex) {
/* 687 */             throw new RuntimeException(ex);
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 695 */       BaseCanonicalizer.UsedNSList uList = null;
/* 696 */       uList = this.clearDepth.get(this.nsDepth);
/* 697 */       if (uList == null) {
/* 698 */         uList = new BaseCanonicalizer.UsedNSList();
/* 699 */         this.clearDepth.set(this.nsDepth, uList);
/*     */       } 
/* 701 */       ArrayList<String> prefixList = uList.getPopList();
/* 702 */       prefixList.add(prefix);
/*     */     }
/*     */ 
/*     */     
/*     */     public void push() {
/* 707 */       this.nsDepth++;
/* 708 */       if (BaseCanonicalizer.debug) {
/* 709 */         System.out.println("--------------------Push depth----------------" + this.nsDepth);
/*     */       }
/* 711 */       if (this.nsDepth >= this.clearDepth.size()) {
/* 712 */         this.clearDepth.ensureCapacity(this.clearDepth.size() + this.resizeBy);
/* 713 */         int len = this.clearDepth.size();
/* 714 */         for (int i = len; i < len + this.resizeBy; i++) {
/* 715 */           this.clearDepth.add(null);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void pop() {
/* 721 */       if (this.nsDepth <= 0) {
/*     */         return;
/*     */       }
/* 724 */       BaseCanonicalizer.UsedNSList ul = this.clearDepth.get(this.nsDepth);
/* 725 */       if (BaseCanonicalizer.debug) {
/* 726 */         System.out.println("---------------------pop depth----------------------" + this.nsDepth);
/*     */       }
/* 728 */       this.nsDepth--;
/* 729 */       if (ul == null) {
/*     */         return;
/*     */       }
/* 732 */       ArrayList<String> pList = ul.getPopList();
/* 733 */       for (int i = 0; i < pList.size(); i++) {
/* 734 */         String prefix = pList.get(i);
/* 735 */         Stack stack = (Stack)this.prefixMappings.get(prefix);
/* 736 */         if (BaseCanonicalizer.debug) {
/* 737 */           System.out.println("clear prefix" + prefix);
/*     */         }
/* 739 */         if (!stack.isEmpty()) {
/* 740 */           stack.pop();
/*     */         }
/*     */       } 
/*     */       
/* 744 */       ArrayList<String> rList = ul.getUsedPrefixList();
/* 745 */       for (int j = 0; j < rList.size(); j++) {
/* 746 */         String prefix = rList.get(j);
/* 747 */         if (!pList.contains(prefix)) {
/*     */ 
/*     */           
/* 750 */           Stack<AttributeNS> stack = (Stack)this.prefixMappings.get(prefix);
/* 751 */           if (BaseCanonicalizer.debug) {
/* 752 */             System.out.println("reset written prefix" + prefix);
/*     */           }
/* 754 */           if (!stack.isEmpty()) {
/* 755 */             AttributeNS attrNS = stack.peek();
/* 756 */             attrNS.setWritten(false);
/*     */           } 
/*     */         } 
/* 759 */       }  pList.clear();
/* 760 */       rList.clear();
/*     */     }
/*     */     
/*     */     public void reset() {
/* 764 */       this.nsDepth = 0;
/* 765 */       for (int i = 0; i < this.clearDepth.size(); i++) {
/* 766 */         BaseCanonicalizer.UsedNSList ul = this.clearDepth.get(i);
/* 767 */         if (ul != null)
/*     */         {
/*     */           
/* 770 */           ul.clear(); } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getNamespaceURI(String prefix) {
/* 775 */       Stack<AttributeNS> stack = (Stack)this.prefixMappings.get(prefix);
/* 776 */       if (stack == null || stack.empty()) {
/* 777 */         return "";
/*     */       }
/* 779 */       AttributeNS attrNS = stack.peek();
/* 780 */       return attrNS.getUri();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPrefix(String namespaceURI) {
/* 785 */       Set<String> keys = this.prefixMappings.keySet();
/*     */       
/* 787 */       Iterator<String> itr = keys.iterator();
/* 788 */       while (itr.hasNext()) {
/* 789 */         String key = itr.next();
/* 790 */         Stack<AttributeNS> stack = (Stack)this.prefixMappings.get(key);
/* 791 */         if (stack == null || stack.empty()) {
/*     */           continue;
/*     */         }
/* 794 */         AttributeNS attrNS = stack.peek();
/* 795 */         if (namespaceURI.equals(attrNS.getUri())) {
/* 796 */           return key;
/*     */         }
/*     */       } 
/* 799 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator getPrefixes(String namespaceURI) {
/* 804 */       Set<String> keys = this.prefixMappings.keySet();
/* 805 */       ArrayList<String> list = new ArrayList();
/* 806 */       Iterator<String> itr = keys.iterator();
/* 807 */       while (itr.hasNext()) {
/* 808 */         String key = itr.next();
/* 809 */         Stack<AttributeNS> stack = (Stack)this.prefixMappings.get(key);
/* 810 */         if (stack == null || stack.empty()) {
/*     */           continue;
/*     */         }
/* 813 */         AttributeNS attrNS = stack.peek();
/* 814 */         if (namespaceURI.equals(attrNS.getUri())) {
/* 815 */           list.add(key);
/*     */         }
/*     */       } 
/*     */       
/* 819 */       return list.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isPrefixRedefined(Stack stack, AttributeNS attrNS) {
/* 824 */       Iterator<AttributeNS> it = stack.iterator();
/* 825 */       while (it.hasNext()) {
/* 826 */         AttributeNS aNS = it.next();
/* 827 */         if (!attrNS.getUri().equals(aNS.getUri())) {
/* 828 */           return true;
/*     */         }
/*     */       } 
/* 831 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class UsedNSList {
/* 836 */     ArrayList usedPrefixList = new ArrayList();
/* 837 */     ArrayList popPrefixList = new ArrayList();
/*     */     
/*     */     public ArrayList getPopList() {
/* 840 */       return this.popPrefixList;
/*     */     }
/*     */     
/*     */     public ArrayList getUsedPrefixList() {
/* 844 */       return this.usedPrefixList;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 848 */       this.usedPrefixList.clear();
/* 849 */       this.popPrefixList.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   static class ElementName
/*     */   {
/* 855 */     private UnsyncByteArrayOutputStream utf8Data = new UnsyncByteArrayOutputStream(20);
/*     */ 
/*     */     
/*     */     public UnsyncByteArrayOutputStream getUtf8Data() {
/* 859 */       return this.utf8Data;
/*     */     }
/*     */     
/*     */     public void setUtf8Data(UnsyncByteArrayOutputStream utf8Data) {
/* 863 */       this.utf8Data = utf8Data;
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
/*     */   public static void sort(List<Comparable> list) {
/* 888 */     int size = list.size();
/* 889 */     for (int iterator = 0; iterator < size; iterator++) {
/* 890 */       for (int iterator1 = iterator + 1; iterator1 < size; iterator1++) {
/* 891 */         if (((Comparable)list.get(iterator1)).compareTo(list.get(iterator)) >= 0) {
/* 892 */           swap(list, iterator1, iterator);
/*     */         }
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
/*     */   private static void swap(List<Object> x, int a, int b) {
/* 906 */     Object t = x.get(a);
/* 907 */     x.set(a, x.get(b));
/* 908 */     x.set(b, t);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\BaseCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */