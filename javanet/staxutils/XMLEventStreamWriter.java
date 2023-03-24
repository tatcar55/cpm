/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.stream.events.EndElement;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLEventStreamWriter
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   private XMLEventWriter out;
/*     */   private XMLEventFactory factory;
/*     */   private static final String DEFAULT_ENCODING = "UTF-8";
/*     */   private int depth;
/*     */   private EndElement[] stack;
/*     */   
/*     */   public XMLEventStreamWriter(XMLEventWriter out) {
/*  56 */     this(out, XMLEventFactory.newInstance());
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
/*     */   public XMLEventStreamWriter(XMLEventWriter out, XMLEventFactory factory) {
/*  71 */     this.depth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.stack = new EndElement[] { null, null, null, null };
/*     */     this.out = out;
/*     */     this.factory = factory; } private void write(StartElement start) throws XMLStreamException {
/*  80 */     if (this.stack.length <= this.depth) {
/*     */       
/*  82 */       EndElement[] newStack = new EndElement[this.stack.length * 2];
/*  83 */       System.arraycopy(this.stack, 0, newStack, 0, this.stack.length);
/*  84 */       this.stack = newStack;
/*     */     } 
/*  86 */     this.out.add(start);
/*     */     
/*  88 */     this.stack[this.depth++] = this.factory.createEndElement(start.getName(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(Namespace space) throws XMLStreamException {
/*  94 */     Collection spaces = new ArrayList();
/*  95 */     EndElement oldEnd = this.stack[this.depth - 1];
/*  96 */     Iterator oldSpaces = oldEnd.getNamespaces();
/*  97 */     if (oldSpaces != null) {
/*  98 */       while (oldSpaces.hasNext()) {
/*  99 */         spaces.add(oldSpaces.next());
/*     */       }
/*     */     }
/* 102 */     spaces.add(space);
/* 103 */     EndElement end = this.factory.createEndElement(oldEnd.getName(), spaces.iterator());
/*     */     
/* 105 */     this.out.add(space);
/*     */     
/* 107 */     this.stack[this.depth - 1] = end;
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 111 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 115 */     this.out.setNamespaceContext(context);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 119 */     return this.out.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 123 */     this.out.setDefaultNamespace(uri);
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 127 */     return this.out.getPrefix(uri);
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 131 */     this.out.setPrefix(prefix, uri);
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 135 */     this.out.add(this.factory.createStartDocument("UTF-8"));
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 139 */     writeStartDocument("UTF-8", version);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 143 */     this.out.add(this.factory.createStartDocument(encoding, version));
/*     */   }
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {
/* 147 */     this.out.add(this.factory.createDTD(dtd));
/*     */   }
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/* 151 */     this.out.add(this.factory.createComment(data));
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 155 */     writeProcessingInstruction(target, "");
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 159 */     this.out.add(this.factory.createProcessingInstruction(target, data));
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 163 */     writeStartElement(localName);
/* 164 */     writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 168 */     writeStartElement(namespaceURI, localName);
/* 169 */     writeEndElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 174 */     writeStartElement(prefix, localName, namespaceURI);
/* 175 */     writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 179 */     write(this.factory.createStartElement(new QName(localName), (Iterator)null, (Iterator)null));
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 183 */     write(this.factory.createStartElement(new QName(namespaceURI, localName), (Iterator)null, (Iterator)null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 188 */     write(this.factory.createStartElement(new QName(namespaceURI, localName, prefix), (Iterator)null, (Iterator)null));
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 192 */     this.out.add(this.factory.createAttribute(localName, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 197 */     this.out.add(this.factory.createAttribute(new QName(namespaceURI, localName), value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 202 */     this.out.add(this.factory.createAttribute(prefix, namespaceURI, localName, value));
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 206 */     write(this.factory.createNamespace(namespaceURI));
/*     */   }
/*     */   
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 210 */     write(this.factory.createNamespace(prefix, namespaceURI));
/*     */   }
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/* 214 */     this.out.add(this.factory.createCharacters(text));
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 218 */     writeCharacters(new String(text, start, len));
/*     */   }
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 222 */     this.out.add(this.factory.createCData(data));
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 226 */     this.out.add(this.factory.createEntityReference(name, null));
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 230 */     if (this.depth <= 0) {
/*     */       
/* 232 */       this.out.add(this.factory.createEndElement(new QName("unknown"), null));
/*     */     } else {
/* 234 */       this.out.add(this.stack[this.depth - 1]);
/*     */       
/* 236 */       this.depth--;
/* 237 */       this.stack[this.depth] = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/*     */     try {
/* 243 */       while (this.depth > 0) {
/* 244 */         writeEndElement();
/*     */       }
/* 246 */     } catch (Exception ignored) {}
/*     */     
/* 248 */     this.out.add(this.factory.createEndDocument());
/*     */     
/* 250 */     this.depth = 0;
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/* 254 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 258 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLEventStreamWriter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */