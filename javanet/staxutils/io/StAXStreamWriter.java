/*     */ package javanet.staxutils.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import javanet.staxutils.helpers.ElementContext;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXStreamWriter
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   private Writer writer;
/*     */   private boolean closed;
/*     */   private NamespaceContext rootContext;
/*     */   private ElementContext elementContext;
/*     */   
/*     */   public StAXStreamWriter(OutputStream stream) {
/*  77 */     this(new OutputStreamWriter(stream));
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
/*     */   public StAXStreamWriter(OutputStream stream, String encoding) throws UnsupportedEncodingException {
/*  92 */     this(new OutputStreamWriter(stream, encoding));
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
/*     */   public StAXStreamWriter(Writer writer) {
/* 104 */     this.writer = writer;
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
/*     */   public StAXStreamWriter(Writer writer, NamespaceContext rootContext) {
/* 117 */     this.writer = writer;
/* 118 */     this.rootContext = rootContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws XMLStreamException {
/* 124 */     if (!this.closed) {
/*     */       
/* 126 */       flush();
/*     */       
/* 128 */       this.closed = true;
/* 129 */       this.writer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() throws XMLStreamException {
/* 138 */     closeElementContext();
/*     */ 
/*     */     
/*     */     try {
/* 142 */       this.writer.flush();
/*     */     }
/* 144 */     catch (IOException e) {
/*     */       
/* 146 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 154 */     return getNamespaceContext().getPrefix(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 161 */     throw new IllegalArgumentException(name + " property not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/*     */     try {
/* 169 */       XMLWriterUtils.writeStartDocument(this.writer);
/*     */     }
/* 171 */     catch (IOException e) {
/*     */       
/* 173 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/*     */     try {
/* 183 */       XMLWriterUtils.writeStartDocument(version, this.writer);
/*     */     }
/* 185 */     catch (IOException e) {
/*     */       
/* 187 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeStartDocument(String encoding, String version) throws XMLStreamException {
/*     */     try {
/* 199 */       XMLWriterUtils.writeStartDocument(version, encoding, this.writer);
/*     */     }
/* 201 */     catch (IOException e) {
/*     */       
/* 203 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeEndDocument() throws XMLStreamException {
/* 212 */     closeElementContext();
/*     */ 
/*     */     
/* 215 */     while (this.elementContext != null)
/*     */     {
/* 217 */       writeEndElement();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeCData(String data) throws XMLStreamException {
/* 225 */     if (data == null)
/*     */     {
/* 227 */       throw new IllegalArgumentException("CDATA argument was null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     closeElementContext();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 237 */       XMLWriterUtils.writeCData(data, this.writer);
/*     */     }
/* 239 */     catch (IOException e) {
/*     */       
/* 241 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 250 */     if (text == null)
/*     */     {
/* 252 */       throw new IllegalArgumentException("Character text argument was null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     closeElementContext();
/*     */ 
/*     */     
/*     */     try {
/* 262 */       XMLWriterUtils.writeCharacters(text, start, len, this.writer);
/*     */     }
/* 264 */     catch (IOException e) {
/*     */       
/* 266 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeCharacters(String text) throws XMLStreamException {
/* 275 */     if (text == null)
/*     */     {
/* 277 */       throw new IllegalArgumentException("Character text argument was null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     closeElementContext();
/*     */ 
/*     */     
/*     */     try {
/* 287 */       XMLWriterUtils.writeCharacters(text, this.writer);
/*     */     }
/* 289 */     catch (IOException e) {
/*     */       
/* 291 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeComment(String data) throws XMLStreamException {
/* 300 */     if (data == null)
/*     */     {
/* 302 */       throw new IllegalArgumentException("Comment data argument was null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 307 */     closeElementContext();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 312 */       XMLWriterUtils.writeComment(data, this.writer);
/*     */     }
/* 314 */     catch (IOException e) {
/*     */       
/* 316 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeDTD(String dtd) throws XMLStreamException {
/* 324 */     if (dtd == null)
/*     */     {
/* 326 */       throw new IllegalArgumentException("dtd argument was null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 332 */       XMLWriterUtils.writeDTD(dtd, this.writer);
/*     */     }
/* 334 */     catch (IOException e) {
/*     */       
/* 336 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeEntityRef(String name) throws XMLStreamException {
/* 346 */     closeElementContext();
/*     */ 
/*     */     
/*     */     try {
/* 350 */       XMLWriterUtils.writeEntityReference(name, this.writer);
/*     */     }
/* 352 */     catch (IOException e) {
/*     */       
/* 354 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 364 */     closeElementContext();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 369 */       XMLWriterUtils.writeProcessingInstruction(target, data, this.writer);
/*     */     }
/* 371 */     catch (IOException e) {
/*     */       
/* 373 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 382 */     writeProcessingInstruction(target, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 388 */     return (NamespaceContext)this.elementContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 395 */     if (this.rootContext == null && this.elementContext == null) {
/*     */       
/* 397 */       this.rootContext = context;
/*     */     }
/*     */     else {
/*     */       
/* 401 */       throw new IllegalStateException("NamespaceContext has already been set or document is already in progress");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setDefaultNamespace(String uri) throws XMLStreamException {
/* 411 */     this.elementContext.putNamespace("", uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 418 */     this.elementContext.putNamespace(prefix, uri);
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
/*     */   public synchronized void writeStartElement(String prefix, String localName, String namespaceURI, boolean isEmpty) throws XMLStreamException {
/* 435 */     if (prefix == null)
/*     */     {
/* 437 */       throw new IllegalArgumentException("prefix may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */     
/* 440 */     if (localName == null)
/*     */     {
/* 442 */       throw new IllegalArgumentException("localName may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */     
/* 445 */     if (namespaceURI == null)
/*     */     {
/* 447 */       throw new IllegalArgumentException("namespaceURI may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     if (this.elementContext != null) {
/*     */       
/* 455 */       closeElementContext();
/*     */ 
/*     */       
/* 458 */       if (this.elementContext == null)
/*     */       {
/* 460 */         throw new XMLStreamException("Writing start tag after close of root element");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     QName name = new QName(namespaceURI, localName, prefix);
/* 469 */     this.elementContext = new ElementContext(name, this.elementContext, isEmpty);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 476 */     writeStartElement(prefix, localName, namespaceURI, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 483 */     writeStartElement("", localName, namespaceURI, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 489 */     writeStartElement("", localName, "", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 496 */     writeStartElement(prefix, localName, namespaceURI, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 503 */     writeStartElement("", localName, namespaceURI, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 509 */     writeStartElement("", localName, "", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeAttribute(QName name, String value) throws XMLStreamException {
/* 516 */     if (this.elementContext == null || this.elementContext.isReadOnly())
/*     */     {
/* 518 */       throw new XMLStreamException(getCurrentPath() + ": attributes must be written directly following a start element.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 524 */     this.elementContext.putAttribute(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 531 */     if (prefix == null)
/*     */     {
/* 533 */       throw new IllegalArgumentException("attribute prefix may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */ 
/*     */     
/* 537 */     if (localName == null)
/*     */     {
/* 539 */       throw new IllegalArgumentException("attribute localName may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */ 
/*     */     
/* 543 */     if (namespaceURI == null)
/*     */     {
/* 545 */       throw new IllegalArgumentException("attribute namespaceURI may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 551 */     writeAttribute(new QName(namespaceURI, localName, prefix), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 558 */     writeAttribute("", namespaceURI, localName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 565 */     writeAttribute("", "", localName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 572 */     writeNamespace("", namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 579 */     if (prefix == null)
/*     */     {
/* 581 */       throw new IllegalArgumentException("Namespace prefix may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */ 
/*     */     
/* 585 */     if (namespaceURI == null)
/*     */     {
/* 587 */       throw new IllegalArgumentException("Namespace URI may not be null @ [" + getCurrentPath() + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 593 */     if (this.elementContext != null && !this.elementContext.isReadOnly()) {
/*     */       
/* 595 */       this.elementContext.putNamespace(prefix, namespaceURI);
/*     */     }
/*     */     else {
/*     */       
/* 599 */       throw new XMLStreamException(getCurrentPath() + ": Namespaces must be written directly following a start tag");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void writeEndElement() throws XMLStreamException {
/* 609 */     closeElementContext();
/*     */     
/* 611 */     if (this.elementContext != null) {
/*     */       
/* 613 */       QName name = this.elementContext.getName();
/*     */       
/*     */       try {
/* 616 */         XMLWriterUtils.writeEndElement(name, this.writer);
/*     */       }
/* 618 */       catch (IOException e) {
/*     */         
/* 620 */         throw new XMLStreamException(getCurrentPath() + ": Error writing end element to stream", e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 626 */       this.elementContext = this.elementContext.getParentContext();
/*     */     }
/*     */     else {
/*     */       
/* 630 */       throw new XMLStreamException("Unmatched END_ELEMENT");
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
/*     */   public synchronized String getCurrentPath() {
/* 643 */     if (this.elementContext == null)
/*     */     {
/* 645 */       return "/";
/*     */     }
/*     */ 
/*     */     
/* 649 */     return this.elementContext.getPath();
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
/*     */   protected void closeElementContext() throws XMLStreamException {
/* 665 */     if (this.elementContext != null && !this.elementContext.isReadOnly()) {
/*     */       
/* 667 */       this.elementContext.setReadOnly();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 672 */         this.writer.write(60);
/* 673 */         XMLWriterUtils.writeQName(this.elementContext.getName(), this.writer);
/*     */         int i, s;
/* 675 */         for (i = 0, s = this.elementContext.attributeCount(); i < s; i++) {
/*     */           
/* 677 */           QName name = this.elementContext.getAttributeName(i);
/* 678 */           String value = this.elementContext.getAttribute(i);
/*     */           
/* 680 */           this.writer.write(32);
/* 681 */           XMLWriterUtils.writeAttribute(name, value, this.writer);
/*     */         } 
/*     */ 
/*     */         
/* 685 */         for (i = 0, s = this.elementContext.namespaceCount(); i < s; i++) {
/*     */           
/* 687 */           String prefix = this.elementContext.getNamespacePrefix(i);
/* 688 */           String uri = this.elementContext.getNamespaceURI(i);
/*     */           
/* 690 */           this.writer.write(32);
/* 691 */           XMLWriterUtils.writeNamespace(prefix, uri, this.writer);
/*     */         } 
/*     */ 
/*     */         
/* 695 */         if (this.elementContext.isEmpty())
/*     */         {
/* 697 */           this.writer.write("/>");
/* 698 */           this.elementContext = this.elementContext.getParentContext();
/*     */         }
/*     */         else
/*     */         {
/* 702 */           this.writer.write(62);
/*     */         }
/*     */       
/*     */       }
/* 706 */       catch (IOException e) {
/*     */         
/* 708 */         throw new XMLStreamException(getCurrentPath() + ": error writing start tag to stream", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\io\StAXStreamWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */