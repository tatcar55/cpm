/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
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
/*     */ public final class ForkXmlOutput
/*     */   extends XmlOutputAbstractImpl
/*     */ {
/*     */   private final XmlOutput lhs;
/*     */   private final XmlOutput rhs;
/*     */   
/*     */   public ForkXmlOutput(XmlOutput lhs, XmlOutput rhs) {
/*  61 */     this.lhs = lhs;
/*  62 */     this.rhs = rhs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/*  67 */     this.lhs.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*  68 */     this.rhs.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/*  73 */     this.lhs.endDocument(fragment);
/*  74 */     this.rhs.endDocument(fragment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException, XMLStreamException {
/*  79 */     this.lhs.beginStartTag(name);
/*  80 */     this.rhs.beginStartTag(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException, XMLStreamException {
/*  85 */     this.lhs.attribute(name, value);
/*  86 */     this.rhs.attribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(Name name) throws IOException, SAXException, XMLStreamException {
/*  91 */     this.lhs.endTag(name);
/*  92 */     this.rhs.endTag(name);
/*     */   }
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException, XMLStreamException {
/*  96 */     this.lhs.beginStartTag(prefix, localName);
/*  97 */     this.rhs.beginStartTag(prefix, localName);
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException, XMLStreamException {
/* 101 */     this.lhs.attribute(prefix, localName, value);
/* 102 */     this.rhs.attribute(prefix, localName, value);
/*     */   }
/*     */   
/*     */   public void endStartTag() throws IOException, SAXException {
/* 106 */     this.lhs.endStartTag();
/* 107 */     this.rhs.endStartTag();
/*     */   }
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException, SAXException, XMLStreamException {
/* 111 */     this.lhs.endTag(prefix, localName);
/* 112 */     this.rhs.endTag(prefix, localName);
/*     */   }
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 116 */     this.lhs.text(value, needsSeparatingWhitespace);
/* 117 */     this.rhs.text(value, needsSeparatingWhitespace);
/*     */   }
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 121 */     this.lhs.text(value, needsSeparatingWhitespace);
/* 122 */     this.rhs.text(value, needsSeparatingWhitespace);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\ForkXmlOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */