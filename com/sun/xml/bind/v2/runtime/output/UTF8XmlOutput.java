/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UTF8XmlOutput
/*     */   extends XmlOutputAbstractImpl
/*     */ {
/*     */   protected final OutputStream out;
/*  67 */   private Encoded[] prefixes = new Encoded[8];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int prefixCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Encoded[] localNames;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private final Encoded textBuffer = new Encoded();
/*     */ 
/*     */ 
/*     */   
/*  90 */   protected final byte[] octetBuffer = new byte[1024];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int octetBufferIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeStartTagPending = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private String header;
/*     */ 
/*     */ 
/*     */   
/* 107 */   private CharacterEscapeHandler escapeHandler = null;
/*     */   
/*     */   private final byte[] XMLNS_EQUALS;
/*     */   
/*     */   private final byte[] XMLNS_COLON;
/*     */   
/*     */   private final byte[] EQUALS;
/*     */   
/*     */   private final byte[] CLOSE_TAG;
/*     */   
/*     */   private final byte[] EMPTY_TAG;
/*     */   
/*     */   private final byte[] XML_DECL;
/*     */ 
/*     */   
/*     */   public void setHeader(String header) {
/* 123 */     this.header = header;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/* 128 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*     */     
/* 130 */     this.octetBufferIndex = 0;
/* 131 */     if (!fragment) {
/* 132 */       write(this.XML_DECL);
/*     */     }
/* 134 */     if (this.header != null) {
/* 135 */       this.textBuffer.set(this.header);
/* 136 */       this.textBuffer.write(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/* 142 */     flushBuffer();
/* 143 */     super.endDocument(fragment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void closeStartTag() throws IOException {
/* 150 */     if (this.closeStartTagPending) {
/* 151 */       write(62);
/* 152 */       this.closeStartTagPending = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException {
/* 157 */     closeStartTag();
/* 158 */     int base = pushNsDecls();
/* 159 */     write(60);
/* 160 */     writeName(prefix, localName);
/* 161 */     writeNsDecls(base);
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException {
/* 166 */     closeStartTag();
/* 167 */     int base = pushNsDecls();
/* 168 */     write(60);
/* 169 */     writeName(name);
/* 170 */     writeNsDecls(base);
/*     */   }
/*     */   
/*     */   private int pushNsDecls() {
/* 174 */     int total = this.nsContext.count();
/* 175 */     NamespaceContextImpl.Element ns = this.nsContext.getCurrent();
/*     */     
/* 177 */     if (total > this.prefixes.length) {
/*     */       
/* 179 */       int m = Math.max(total, this.prefixes.length * 2);
/* 180 */       Encoded[] buf = new Encoded[m];
/* 181 */       System.arraycopy(this.prefixes, 0, buf, 0, this.prefixes.length);
/* 182 */       for (int j = this.prefixes.length; j < buf.length; j++)
/* 183 */         buf[j] = new Encoded(); 
/* 184 */       this.prefixes = buf;
/*     */     } 
/*     */     
/* 187 */     int base = Math.min(this.prefixCount, ns.getBase());
/* 188 */     int size = this.nsContext.count();
/* 189 */     for (int i = base; i < size; i++) {
/* 190 */       String p = this.nsContext.getPrefix(i);
/*     */       
/* 192 */       Encoded e = this.prefixes[i];
/*     */       
/* 194 */       if (p.length() == 0) {
/* 195 */         e.buf = EMPTY_BYTE_ARRAY;
/* 196 */         e.len = 0;
/*     */       } else {
/* 198 */         e.set(p);
/* 199 */         e.append(':');
/*     */       } 
/*     */     } 
/* 202 */     this.prefixCount = size;
/* 203 */     return base;
/*     */   }
/*     */   
/*     */   protected void writeNsDecls(int base) throws IOException {
/* 207 */     NamespaceContextImpl.Element ns = this.nsContext.getCurrent();
/* 208 */     int size = this.nsContext.count();
/*     */     
/* 210 */     for (int i = ns.getBase(); i < size; i++) {
/* 211 */       writeNsDecl(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void writeNsDecl(int prefixIndex) throws IOException {
/* 218 */     String p = this.nsContext.getPrefix(prefixIndex);
/*     */     
/* 220 */     if (p.length() == 0) {
/* 221 */       if (this.nsContext.getCurrent().isRootElement() && this.nsContext.getNamespaceURI(prefixIndex).length() == 0) {
/*     */         return;
/*     */       }
/* 224 */       write(this.XMLNS_EQUALS);
/*     */     } else {
/* 226 */       Encoded e = this.prefixes[prefixIndex];
/* 227 */       write(this.XMLNS_COLON);
/* 228 */       write(e.buf, 0, e.len - 1);
/* 229 */       write(this.EQUALS);
/*     */     } 
/* 231 */     doText(this.nsContext.getNamespaceURI(prefixIndex), true);
/* 232 */     write(34);
/*     */   }
/*     */   
/*     */   private void writePrefix(int prefix) throws IOException {
/* 236 */     this.prefixes[prefix].write(this);
/*     */   }
/*     */   
/*     */   private void writeName(Name name) throws IOException {
/* 240 */     writePrefix(this.nsUriIndex2prefixIndex[name.nsUriIndex]);
/* 241 */     this.localNames[name.localNameIndex].write(this);
/*     */   }
/*     */   
/*     */   private void writeName(int prefix, String localName) throws IOException {
/* 245 */     writePrefix(prefix);
/* 246 */     this.textBuffer.set(localName);
/* 247 */     this.textBuffer.write(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException {
/* 252 */     write(32);
/* 253 */     if (name.nsUriIndex == -1) {
/* 254 */       this.localNames[name.localNameIndex].write(this);
/*     */     } else {
/* 256 */       writeName(name);
/* 257 */     }  write(this.EQUALS);
/* 258 */     doText(value, true);
/* 259 */     write(34);
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException {
/* 263 */     write(32);
/* 264 */     if (prefix == -1) {
/* 265 */       this.textBuffer.set(localName);
/* 266 */       this.textBuffer.write(this);
/*     */     } else {
/* 268 */       writeName(prefix, localName);
/* 269 */     }  write(this.EQUALS);
/* 270 */     doText(value, true);
/* 271 */     write(34);
/*     */   }
/*     */   
/*     */   public void endStartTag() throws IOException {
/* 275 */     this.closeStartTagPending = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(Name name) throws IOException {
/* 280 */     if (this.closeStartTagPending) {
/* 281 */       write(this.EMPTY_TAG);
/* 282 */       this.closeStartTagPending = false;
/*     */     } else {
/* 284 */       write(this.CLOSE_TAG);
/* 285 */       writeName(name);
/* 286 */       write(62);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException {
/* 291 */     if (this.closeStartTagPending) {
/* 292 */       write(this.EMPTY_TAG);
/* 293 */       this.closeStartTagPending = false;
/*     */     } else {
/* 295 */       write(this.CLOSE_TAG);
/* 296 */       writeName(prefix, localName);
/* 297 */       write(62);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void text(String value, boolean needSP) throws IOException {
/* 302 */     closeStartTag();
/* 303 */     if (needSP)
/* 304 */       write(32); 
/* 305 */     doText(value, false);
/*     */   }
/*     */   
/*     */   public void text(Pcdata value, boolean needSP) throws IOException {
/* 309 */     closeStartTag();
/* 310 */     if (needSP)
/* 311 */       write(32); 
/* 312 */     value.writeTo(this);
/*     */   }
/*     */   
/*     */   private void doText(String value, boolean isAttribute) throws IOException {
/* 316 */     if (this.escapeHandler != null) {
/* 317 */       StringWriter sw = new StringWriter();
/* 318 */       this.escapeHandler.escape(value.toCharArray(), 0, value.length(), isAttribute, sw);
/* 319 */       this.textBuffer.set(sw.toString());
/*     */     } else {
/* 321 */       this.textBuffer.setEscape(value, isAttribute);
/*     */     } 
/* 323 */     this.textBuffer.write(this);
/*     */   }
/*     */   
/*     */   public final void text(int value) throws IOException {
/* 327 */     closeStartTag();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     boolean minus = (value < 0);
/* 335 */     this.textBuffer.ensureSize(11);
/* 336 */     byte[] buf = this.textBuffer.buf;
/* 337 */     int idx = 11;
/*     */     
/*     */     do {
/* 340 */       int r = value % 10;
/* 341 */       if (r < 0) r = -r; 
/* 342 */       buf[--idx] = (byte)(0x30 | r);
/* 343 */       value /= 10;
/* 344 */     } while (value != 0);
/*     */     
/* 346 */     if (minus) buf[--idx] = 45;
/*     */     
/* 348 */     write(buf, idx, 11 - idx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(byte[] data, int dataLen) throws IOException {
/* 359 */     closeStartTag();
/*     */     
/* 361 */     int start = 0;
/*     */     
/* 363 */     while (dataLen > 0) {
/*     */       
/* 365 */       int batchSize = Math.min((this.octetBuffer.length - this.octetBufferIndex) / 4 * 3, dataLen);
/*     */ 
/*     */       
/* 368 */       this.octetBufferIndex = DatatypeConverterImpl._printBase64Binary(data, start, batchSize, this.octetBuffer, this.octetBufferIndex);
/*     */       
/* 370 */       if (batchSize < dataLen) {
/* 371 */         flushBuffer();
/*     */       }
/* 373 */       start += batchSize;
/* 374 */       dataLen -= batchSize;
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
/*     */   public final void write(int i) throws IOException {
/* 393 */     if (this.octetBufferIndex < this.octetBuffer.length) {
/* 394 */       this.octetBuffer[this.octetBufferIndex++] = (byte)i;
/*     */     } else {
/* 396 */       this.out.write(this.octetBuffer);
/* 397 */       this.octetBufferIndex = 1;
/* 398 */       this.octetBuffer[0] = (byte)i;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void write(byte[] b) throws IOException {
/* 403 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   protected final void write(byte[] b, int start, int length) throws IOException {
/* 407 */     if (this.octetBufferIndex + length < this.octetBuffer.length) {
/* 408 */       System.arraycopy(b, start, this.octetBuffer, this.octetBufferIndex, length);
/* 409 */       this.octetBufferIndex += length;
/*     */     } else {
/* 411 */       this.out.write(this.octetBuffer, 0, this.octetBufferIndex);
/* 412 */       this.out.write(b, start, length);
/* 413 */       this.octetBufferIndex = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void flushBuffer() throws IOException {
/* 418 */     this.out.write(this.octetBuffer, 0, this.octetBufferIndex);
/* 419 */     this.octetBufferIndex = 0;
/*     */   }
/*     */   
/*     */   static byte[] toBytes(String s) {
/* 423 */     byte[] buf = new byte[s.length()];
/* 424 */     for (int i = s.length() - 1; i >= 0; i--)
/* 425 */       buf[i] = (byte)s.charAt(i); 
/* 426 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public UTF8XmlOutput(OutputStream out, Encoded[] localNames, CharacterEscapeHandler escapeHandler) {
/* 431 */     this.XMLNS_EQUALS = (byte[])_XMLNS_EQUALS.clone();
/* 432 */     this.XMLNS_COLON = (byte[])_XMLNS_COLON.clone();
/* 433 */     this.EQUALS = (byte[])_EQUALS.clone();
/* 434 */     this.CLOSE_TAG = (byte[])_CLOSE_TAG.clone();
/* 435 */     this.EMPTY_TAG = (byte[])_EMPTY_TAG.clone();
/* 436 */     this.XML_DECL = (byte[])_XML_DECL.clone(); this.out = out; this.localNames = localNames;
/*     */     for (int i = 0; i < this.prefixes.length; i++)
/*     */       this.prefixes[i] = new Encoded(); 
/* 439 */     this.escapeHandler = escapeHandler; } private static final byte[] _XMLNS_EQUALS = toBytes(" xmlns=\"");
/* 440 */   private static final byte[] _XMLNS_COLON = toBytes(" xmlns:");
/* 441 */   private static final byte[] _EQUALS = toBytes("=\"");
/* 442 */   private static final byte[] _CLOSE_TAG = toBytes("</");
/* 443 */   private static final byte[] _EMPTY_TAG = toBytes("/>");
/* 444 */   private static final byte[] _XML_DECL = toBytes("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
/*     */ 
/*     */   
/* 447 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\UTF8XmlOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */