/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.v2.runtime.ClassBeanInfoImpl;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.UnmarshallerHandler;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
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
/*     */ public final class SAXConnector
/*     */   implements UnmarshallerHandler
/*     */ {
/*     */   private LocatorEx loc;
/*  66 */   private final StringBuilder buffer = new StringBuilder();
/*     */   private final XmlVisitor next;
/*     */   private final UnmarshallingContext context;
/*     */   private final XmlVisitor.TextPredictor predictor;
/*     */   
/*     */   private static final class TagNameImpl extends TagName {
/*     */     String qname;
/*     */     
/*     */     public String getQname() {
/*  75 */       return this.qname;
/*     */     }
/*     */     
/*     */     private TagNameImpl() {} }
/*  79 */   private final TagNameImpl tagName = new TagNameImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXConnector(XmlVisitor next, LocatorEx externalLocator) {
/*  88 */     this.next = next;
/*  89 */     this.context = next.getContext();
/*  90 */     this.predictor = next.getPredictor();
/*  91 */     this.loc = externalLocator;
/*     */   }
/*     */   
/*     */   public Object getResult() throws JAXBException, IllegalStateException {
/*  95 */     return this.context.getResult();
/*     */   }
/*     */   
/*     */   public UnmarshallingContext getContext() {
/*  99 */     return this.context;
/*     */   }
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 103 */     if (this.loc != null) {
/*     */       return;
/*     */     }
/* 106 */     this.loc = new LocatorExWrapper(locator);
/*     */   }
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 110 */     this.next.startDocument(this.loc, null);
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 114 */     this.next.endDocument();
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 118 */     this.next.startPrefixMapping(prefix, uri);
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/* 122 */     this.next.endPrefixMapping(prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/* 127 */     if (uri == null || uri.length() == 0)
/* 128 */       uri = ""; 
/* 129 */     if (local == null || local.length() == 0)
/* 130 */       local = qname; 
/* 131 */     if (qname == null || qname.length() == 0) {
/* 132 */       qname = local;
/*     */     }
/*     */     
/* 135 */     boolean ignorable = true;
/*     */ 
/*     */     
/*     */     StructureLoader sl;
/*     */     
/* 140 */     if ((sl = this.context.getStructureLoader()) != null) {
/* 141 */       ignorable = ((ClassBeanInfoImpl)sl.getBeanInfo()).hasElementOnlyContentModel();
/*     */     }
/*     */     
/* 144 */     processText(ignorable);
/*     */     
/* 146 */     this.tagName.uri = uri;
/* 147 */     this.tagName.local = local;
/* 148 */     this.tagName.qname = qname;
/* 149 */     this.tagName.atts = atts;
/* 150 */     this.next.startElement(this.tagName);
/*     */   }
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 154 */     processText(false);
/* 155 */     this.tagName.uri = uri;
/* 156 */     this.tagName.local = localName;
/* 157 */     this.tagName.qname = qName;
/* 158 */     this.next.endElement(this.tagName);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void characters(char[] buf, int start, int len) {
/* 163 */     if (this.predictor.expectText())
/* 164 */       this.buffer.append(buf, start, len); 
/*     */   }
/*     */   
/*     */   public final void ignorableWhitespace(char[] buf, int start, int len) {
/* 168 */     characters(buf, start, len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) {}
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) {}
/*     */ 
/*     */   
/*     */   private void processText(boolean ignorable) throws SAXException {
/* 180 */     if (this.predictor.expectText() && (!ignorable || !WhiteSpaceProcessor.isWhiteSpace(this.buffer)))
/* 181 */       this.next.text(this.buffer); 
/* 182 */     this.buffer.setLength(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\SAXConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */