/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import org.xml.sax.Attributes;
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
/*     */ public final class InterningXmlVisitor
/*     */   implements XmlVisitor
/*     */ {
/*     */   private final XmlVisitor next;
/*  56 */   private final AttributesImpl attributes = new AttributesImpl();
/*     */   
/*     */   public InterningXmlVisitor(XmlVisitor next) {
/*  59 */     this.next = next;
/*     */   }
/*     */   
/*     */   public void startDocument(LocatorEx locator, NamespaceContext nsContext) throws SAXException {
/*  63 */     this.next.startDocument(locator, nsContext);
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*  67 */     this.next.endDocument();
/*     */   }
/*     */   
/*     */   public void startElement(TagName tagName) throws SAXException {
/*  71 */     this.attributes.setAttributes(tagName.atts);
/*  72 */     tagName.atts = this.attributes;
/*  73 */     tagName.uri = intern(tagName.uri);
/*  74 */     tagName.local = intern(tagName.local);
/*  75 */     this.next.startElement(tagName);
/*     */   }
/*     */   
/*     */   public void endElement(TagName tagName) throws SAXException {
/*  79 */     tagName.uri = intern(tagName.uri);
/*  80 */     tagName.local = intern(tagName.local);
/*  81 */     this.next.endElement(tagName);
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String nsUri) throws SAXException {
/*  85 */     this.next.startPrefixMapping(intern(prefix), intern(nsUri));
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/*  89 */     this.next.endPrefixMapping(intern(prefix));
/*     */   }
/*     */   
/*     */   public void text(CharSequence pcdata) throws SAXException {
/*  93 */     this.next.text(pcdata);
/*     */   }
/*     */   
/*     */   public UnmarshallingContext getContext() {
/*  97 */     return this.next.getContext();
/*     */   }
/*     */   
/*     */   public XmlVisitor.TextPredictor getPredictor() {
/* 101 */     return this.next.getPredictor();
/*     */   }
/*     */   
/*     */   private static class AttributesImpl implements Attributes {
/*     */     private Attributes core;
/*     */     
/*     */     void setAttributes(Attributes att) {
/* 108 */       this.core = att;
/*     */     }
/*     */     private AttributesImpl() {}
/*     */     public int getIndex(String qName) {
/* 112 */       return this.core.getIndex(qName);
/*     */     }
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 116 */       return this.core.getIndex(uri, localName);
/*     */     }
/*     */     
/*     */     public int getLength() {
/* 120 */       return this.core.getLength();
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 124 */       return InterningXmlVisitor.intern(this.core.getLocalName(index));
/*     */     }
/*     */     
/*     */     public String getQName(int index) {
/* 128 */       return InterningXmlVisitor.intern(this.core.getQName(index));
/*     */     }
/*     */     
/*     */     public String getType(int index) {
/* 132 */       return InterningXmlVisitor.intern(this.core.getType(index));
/*     */     }
/*     */     
/*     */     public String getType(String qName) {
/* 136 */       return InterningXmlVisitor.intern(this.core.getType(qName));
/*     */     }
/*     */     
/*     */     public String getType(String uri, String localName) {
/* 140 */       return InterningXmlVisitor.intern(this.core.getType(uri, localName));
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 144 */       return InterningXmlVisitor.intern(this.core.getURI(index));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getValue(int index) {
/* 153 */       return this.core.getValue(index);
/*     */     }
/*     */     
/*     */     public String getValue(String qName) {
/* 157 */       return this.core.getValue(qName);
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 161 */       return this.core.getValue(uri, localName);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String intern(String s) {
/* 166 */     if (s == null) return null; 
/* 167 */     return s.intern();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\InterningXmlVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */