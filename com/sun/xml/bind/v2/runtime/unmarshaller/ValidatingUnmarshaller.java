/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.v2.util.FatalAdapter;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.ValidatorHandler;
/*     */ import org.xml.sax.ErrorHandler;
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
/*     */ final class ValidatingUnmarshaller
/*     */   implements XmlVisitor, XmlVisitor.TextPredictor
/*     */ {
/*     */   private final XmlVisitor next;
/*     */   private final ValidatorHandler validator;
/*  60 */   private NamespaceContext nsContext = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final XmlVisitor.TextPredictor predictor;
/*     */ 
/*     */   
/*  67 */   private char[] buf = new char[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidatingUnmarshaller(Schema schema, XmlVisitor next) {
/*  73 */     this.validator = schema.newValidatorHandler();
/*  74 */     this.next = next;
/*  75 */     this.predictor = next.getPredictor();
/*     */ 
/*     */     
/*  78 */     this.validator.setErrorHandler((ErrorHandler)new FatalAdapter(getContext()));
/*     */   }
/*     */   
/*     */   public void startDocument(LocatorEx locator, NamespaceContext nsContext) throws SAXException {
/*  82 */     this.nsContext = nsContext;
/*  83 */     this.validator.setDocumentLocator(locator);
/*  84 */     this.validator.startDocument();
/*  85 */     this.next.startDocument(locator, nsContext);
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*  89 */     this.nsContext = null;
/*  90 */     this.validator.endDocument();
/*  91 */     this.next.endDocument();
/*     */   }
/*     */   
/*     */   public void startElement(TagName tagName) throws SAXException {
/*  95 */     if (this.nsContext != null) {
/*  96 */       String tagNamePrefix = tagName.getPrefix().intern();
/*  97 */       if (tagNamePrefix != "") {
/*  98 */         this.validator.startPrefixMapping(tagNamePrefix, this.nsContext.getNamespaceURI(tagNamePrefix));
/*     */       }
/*     */     } 
/* 101 */     this.validator.startElement(tagName.uri, tagName.local, tagName.getQname(), tagName.atts);
/* 102 */     this.next.startElement(tagName);
/*     */   }
/*     */   
/*     */   public void endElement(TagName tagName) throws SAXException {
/* 106 */     this.validator.endElement(tagName.uri, tagName.local, tagName.getQname());
/* 107 */     this.next.endElement(tagName);
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String nsUri) throws SAXException {
/* 111 */     this.validator.startPrefixMapping(prefix, nsUri);
/* 112 */     this.next.startPrefixMapping(prefix, nsUri);
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/* 116 */     this.validator.endPrefixMapping(prefix);
/* 117 */     this.next.endPrefixMapping(prefix);
/*     */   }
/*     */   
/*     */   public void text(CharSequence pcdata) throws SAXException {
/* 121 */     int len = pcdata.length();
/* 122 */     if (this.buf.length < len) {
/* 123 */       this.buf = new char[len];
/*     */     }
/* 125 */     for (int i = 0; i < len; i++) {
/* 126 */       this.buf[i] = pcdata.charAt(i);
/*     */     }
/* 128 */     this.validator.characters(this.buf, 0, len);
/* 129 */     if (this.predictor.expectText())
/* 130 */       this.next.text(pcdata); 
/*     */   }
/*     */   
/*     */   public UnmarshallingContext getContext() {
/* 134 */     return this.next.getContext();
/*     */   }
/*     */   
/*     */   public XmlVisitor.TextPredictor getPredictor() {
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean expectText() {
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\ValidatingUnmarshaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */