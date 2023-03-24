/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
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
/*     */ 
/*     */ final class MTOMDecorator
/*     */   implements XmlVisitor
/*     */ {
/*     */   private final XmlVisitor next;
/*     */   private final AttachmentUnmarshaller au;
/*     */   private UnmarshallerImpl parent;
/*  65 */   private final Base64Data base64data = new Base64Data();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inXopInclude;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean followXop;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MTOMDecorator(UnmarshallerImpl parent, XmlVisitor next, AttachmentUnmarshaller au) {
/*  83 */     this.parent = parent;
/*  84 */     this.next = next;
/*  85 */     this.au = au;
/*     */   }
/*     */   
/*     */   public void startDocument(LocatorEx loc, NamespaceContext nsContext) throws SAXException {
/*  89 */     this.next.startDocument(loc, nsContext);
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*  93 */     this.next.endDocument();
/*     */   }
/*     */   
/*     */   public void startElement(TagName tagName) throws SAXException {
/*  97 */     if (tagName.local.equals("Include") && tagName.uri.equals("http://www.w3.org/2004/08/xop/include")) {
/*     */       
/*  99 */       String href = tagName.atts.getValue("href");
/* 100 */       DataHandler attachment = this.au.getAttachmentAsDataHandler(href);
/* 101 */       if (attachment == null)
/*     */       {
/* 103 */         this.parent.getEventHandler().handleEvent(null);
/*     */       }
/*     */       
/* 106 */       this.base64data.set(attachment);
/* 107 */       this.next.text((CharSequence)this.base64data);
/* 108 */       this.inXopInclude = true;
/* 109 */       this.followXop = true;
/*     */     } else {
/* 111 */       this.next.startElement(tagName);
/*     */     } 
/*     */   }
/*     */   public void endElement(TagName tagName) throws SAXException {
/* 115 */     if (this.inXopInclude) {
/*     */       
/* 117 */       this.inXopInclude = false;
/* 118 */       this.followXop = true;
/*     */       return;
/*     */     } 
/* 121 */     this.next.endElement(tagName);
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String nsUri) throws SAXException {
/* 125 */     this.next.startPrefixMapping(prefix, nsUri);
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/* 129 */     this.next.endPrefixMapping(prefix);
/*     */   }
/*     */   
/*     */   public void text(CharSequence pcdata) throws SAXException {
/* 133 */     if (!this.followXop) {
/* 134 */       this.next.text(pcdata);
/*     */     } else {
/* 136 */       this.followXop = false;
/*     */     } 
/*     */   }
/*     */   public UnmarshallingContext getContext() {
/* 140 */     return this.next.getContext();
/*     */   }
/*     */   
/*     */   public XmlVisitor.TextPredictor getPredictor() {
/* 144 */     return this.next.getPredictor();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\MTOMDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */