/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLReporter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StAXContentHandler
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler
/*     */ {
/*     */   protected boolean isCDATA;
/*     */   protected StringBuffer CDATABuffer;
/*     */   protected SimpleNamespaceContext namespaces;
/*     */   protected Locator docLocator;
/*     */   protected XMLReporter reporter;
/*     */   
/*     */   public StAXContentHandler() {}
/*     */   
/*     */   public StAXContentHandler(XMLReporter reporter) {
/*  89 */     this.reporter = reporter;
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
/*     */   public void setXMLReporter(XMLReporter reporter) {
/* 101 */     this.reporter = reporter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 107 */     this.docLocator = locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Location getCurrentLocation() {
/* 118 */     if (this.docLocator != null)
/*     */     {
/* 120 */       return new SAXLocation(this.docLocator);
/*     */     }
/*     */ 
/*     */     
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(SAXParseException e) throws SAXException {
/* 132 */     reportException("ERROR", e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fatalError(SAXParseException e) throws SAXException {
/* 138 */     reportException("FATAL", e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void warning(SAXParseException e) throws SAXException {
/* 144 */     reportException("WARNING", e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 150 */     this.namespaces = new SimpleNamespaceContext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 156 */     this.namespaces = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 163 */     this.namespaces = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 170 */     this.namespaces = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 177 */     if (prefix == null) {
/*     */       
/* 179 */       prefix = "";
/*     */     }
/* 181 */     else if (prefix.equals("xml")) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (this.namespaces == null)
/*     */     {
/* 189 */       this.namespaces = new SimpleNamespaceContext();
/*     */     }
/*     */     
/* 192 */     this.namespaces.setPrefix(prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 202 */     this.isCDATA = true;
/* 203 */     if (this.CDATABuffer == null) {
/*     */       
/* 205 */       this.CDATABuffer = new StringBuffer();
/*     */     }
/*     */     else {
/*     */       
/* 209 */       this.CDATABuffer.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 218 */     if (this.isCDATA)
/*     */     {
/* 220 */       this.CDATABuffer.append(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 228 */     this.isCDATA = false;
/* 229 */     this.CDATABuffer.setLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void reportException(String type, SAXException e) throws SAXException {
/* 261 */     if (this.reporter != null) {
/*     */       
/*     */       try {
/*     */         
/* 265 */         this.reporter.report(e.getMessage(), type, e, getCurrentLocation());
/*     */       }
/* 267 */       catch (XMLStreamException e1) {
/*     */         
/* 269 */         throw new SAXException(e1);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void parseQName(String qName, String[] results) {
/*     */     String prefix, local;
/* 289 */     int idx = qName.indexOf(':');
/* 290 */     if (idx >= 0) {
/*     */       
/* 292 */       prefix = qName.substring(0, idx);
/* 293 */       local = qName.substring(idx + 1);
/*     */     }
/*     */     else {
/*     */       
/* 297 */       prefix = "";
/* 298 */       local = qName;
/*     */     } 
/*     */ 
/*     */     
/* 302 */     results[0] = prefix;
/* 303 */     results[1] = local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SAXLocation
/*     */     implements Location
/*     */   {
/*     */     private int lineNumber;
/*     */ 
/*     */     
/*     */     private int columnNumber;
/*     */ 
/*     */     
/*     */     private String publicId;
/*     */ 
/*     */     
/*     */     private String systemId;
/*     */ 
/*     */ 
/*     */     
/*     */     private SAXLocation(Locator locator) {
/* 326 */       this.lineNumber = locator.getLineNumber();
/* 327 */       this.columnNumber = locator.getColumnNumber();
/* 328 */       this.publicId = locator.getPublicId();
/* 329 */       this.systemId = locator.getSystemId();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getLineNumber() {
/* 335 */       return this.lineNumber;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getColumnNumber() {
/* 341 */       return this.columnNumber;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCharacterOffset() {
/* 347 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPublicId() {
/* 353 */       return this.publicId;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getSystemId() {
/* 359 */       return this.systemId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\StAXContentHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */