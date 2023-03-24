/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.Location;
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
/*     */ abstract class StAXConnector
/*     */ {
/*     */   protected final XmlVisitor visitor;
/*     */   protected final UnmarshallingContext context;
/*     */   protected final XmlVisitor.TextPredictor predictor;
/*     */   
/*     */   private final class TagNameImpl
/*     */     extends TagName
/*     */   {
/*     */     private TagNameImpl() {}
/*     */     
/*     */     public String getQname() {
/*  66 */       return StAXConnector.this.getCurrentQName();
/*     */     }
/*     */   }
/*     */   
/*  70 */   protected final TagName tagName = new TagNameImpl();
/*     */   
/*     */   protected StAXConnector(XmlVisitor visitor) {
/*  73 */     this.visitor = visitor;
/*  74 */     this.context = visitor.getContext();
/*  75 */     this.predictor = visitor.getPredictor();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void bridge() throws XMLStreamException;
/*     */ 
/*     */   
/*     */   protected abstract Location getCurrentLocation();
/*     */ 
/*     */   
/*     */   protected abstract String getCurrentQName();
/*     */ 
/*     */   
/*     */   protected final void handleStartDocument(NamespaceContext nsc) throws SAXException {
/*  90 */     this.visitor.startDocument(new LocatorEx() {
/*     */           public ValidationEventLocator getLocation() {
/*  92 */             return new ValidationEventLocatorImpl(this);
/*     */           }
/*     */           public int getColumnNumber() {
/*  95 */             return StAXConnector.this.getCurrentLocation().getColumnNumber();
/*     */           }
/*     */           public int getLineNumber() {
/*  98 */             return StAXConnector.this.getCurrentLocation().getLineNumber();
/*     */           }
/*     */           public String getPublicId() {
/* 101 */             return StAXConnector.this.getCurrentLocation().getPublicId();
/*     */           }
/*     */           public String getSystemId() {
/* 104 */             return StAXConnector.this.getCurrentLocation().getSystemId();
/*     */           }
/*     */         },  nsc);
/*     */   }
/*     */   
/*     */   protected final void handleEndDocument() throws SAXException {
/* 110 */     this.visitor.endDocument();
/*     */   }
/*     */   
/*     */   protected static String fixNull(String s) {
/* 114 */     if (s == null) return ""; 
/* 115 */     return s;
/*     */   }
/*     */   
/*     */   protected final String getQName(String prefix, String localName) {
/* 119 */     if (prefix == null || prefix.length() == 0) {
/* 120 */       return localName;
/*     */     }
/* 122 */     return prefix + ':' + localName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\StAXConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */