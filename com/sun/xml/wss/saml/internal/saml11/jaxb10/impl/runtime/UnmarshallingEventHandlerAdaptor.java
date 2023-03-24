/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ public class UnmarshallingEventHandlerAdaptor
/*     */   implements UnmarshallingEventHandler
/*     */ {
/*     */   protected final UnmarshallingContext context;
/*     */   protected final ContentHandler handler;
/*     */   private int depth;
/*     */   
/*     */   public Object owner() {
/*     */     return null;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandlerAdaptor(UnmarshallingContext _ctxt, ContentHandler _handler) throws SAXException {
/*  56 */     this.depth = 0; this.context = _ctxt; this.handler = _handler; try {
/*     */       this.handler.setDocumentLocator(this.context.getLocator());
/*     */       this.handler.startDocument();
/*     */       declarePrefixes(this.context.getAllDeclaredPrefixes());
/*     */     } catch (SAXException e) {
/*     */       error(e);
/*  62 */     }  } public void enterElement(String uri, String local, String qname, Attributes atts) throws SAXException { this.depth++;
/*  63 */     this.context.pushAttributes(atts, true);
/*     */     try {
/*  65 */       declarePrefixes(this.context.getNewlyDeclaredPrefixes());
/*  66 */       this.handler.startElement(uri, local, qname, atts);
/*  67 */     } catch (SAXException e) {
/*  68 */       error(e);
/*     */     }  }
/*     */   
/*     */   public void enterAttribute(String uri, String local, String qname) throws SAXException {}
/*     */   
/*     */   public void leaveAttribute(String uri, String local, String qname) throws SAXException {}
/*     */   
/*     */   public void leaveElement(String uri, String local, String qname) throws SAXException {
/*     */     try {
/*  77 */       this.handler.endElement(uri, local, qname);
/*  78 */       undeclarePrefixes(this.context.getNewlyDeclaredPrefixes());
/*  79 */     } catch (SAXException e) {
/*  80 */       error(e);
/*     */     } 
/*  82 */     this.context.popAttributes();
/*     */     
/*  84 */     this.depth--;
/*  85 */     if (this.depth == 0) {
/*     */       
/*     */       try {
/*  88 */         undeclarePrefixes(this.context.getAllDeclaredPrefixes());
/*  89 */         this.handler.endDocument();
/*  90 */       } catch (SAXException e) {
/*  91 */         error(e);
/*     */       } 
/*  93 */       this.context.popContentHandler();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void declarePrefixes(String[] prefixes) throws SAXException {
/*  98 */     for (int i = prefixes.length - 1; i >= 0; i--) {
/*  99 */       this.handler.startPrefixMapping(prefixes[i], this.context.getNamespaceURI(prefixes[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void undeclarePrefixes(String[] prefixes) throws SAXException {
/* 105 */     for (int i = prefixes.length - 1; i >= 0; i--)
/* 106 */       this.handler.endPrefixMapping(prefixes[i]); 
/*     */   }
/*     */   
/*     */   public void text(String s) throws SAXException {
/*     */     try {
/* 111 */       this.handler.characters(s.toCharArray(), 0, s.length());
/* 112 */     } catch (SAXException e) {
/* 113 */       error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void error(SAXException e) throws SAXException {
/* 118 */     this.context.handleEvent(new ValidationEventImpl(1, e.getMessage(), new ValidationEventLocatorImpl(this.context.getLocator()), e), false);
/*     */   }
/*     */   
/*     */   public void leaveChild(int nextState) throws SAXException {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\UnmarshallingEventHandlerAdaptor.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */