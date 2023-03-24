/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLEventFactory;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Characters;
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
/*     */ public class XMLEventWriterOutput
/*     */   extends XmlOutputAbstractImpl
/*     */ {
/*     */   private final XMLEventWriter out;
/*     */   private final XMLEventFactory ef;
/*     */   private final Characters sp;
/*     */   
/*     */   public XMLEventWriterOutput(XMLEventWriter out) {
/*  68 */     this.out = out;
/*  69 */     this.ef = XMLEventFactory.newInstance();
/*  70 */     this.sp = this.ef.createCharacters(" ");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/*  76 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*  77 */     if (!fragment)
/*  78 */       this.out.add(this.ef.createStartDocument()); 
/*     */   }
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/*  82 */     if (!fragment) {
/*  83 */       this.out.add(this.ef.createEndDocument());
/*  84 */       this.out.flush();
/*     */     } 
/*  86 */     super.endDocument(fragment);
/*     */   }
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException, XMLStreamException {
/*  90 */     this.out.add(this.ef.createStartElement(this.nsContext.getPrefix(prefix), this.nsContext.getNamespaceURI(prefix), localName));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/*  97 */     if (nse.count() > 0)
/*  98 */       for (int i = nse.count() - 1; i >= 0; i--) {
/*  99 */         String uri = nse.getNsUri(i);
/* 100 */         if (uri.length() != 0 || nse.getBase() != 1)
/*     */         {
/* 102 */           this.out.add(this.ef.createNamespace(nse.getPrefix(i), uri));
/*     */         }
/*     */       }  
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException, XMLStreamException {
/*     */     Attribute att;
/* 109 */     if (prefix == -1) {
/* 110 */       att = this.ef.createAttribute(localName, value);
/*     */     } else {
/* 112 */       att = this.ef.createAttribute(this.nsContext.getPrefix(prefix), this.nsContext.getNamespaceURI(prefix), localName, value);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 117 */     this.out.add(att);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException, SAXException {}
/*     */ 
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException, SAXException, XMLStreamException {
/* 125 */     this.out.add(this.ef.createEndElement(this.nsContext.getPrefix(prefix), this.nsContext.getNamespaceURI(prefix), localName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 133 */     if (needsSeparatingWhitespace)
/* 134 */       this.out.add(this.sp); 
/* 135 */     this.out.add(this.ef.createCharacters(value));
/*     */   }
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 139 */     text(value.toString(), needsSeparatingWhitespace);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\XMLEventWriterOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */