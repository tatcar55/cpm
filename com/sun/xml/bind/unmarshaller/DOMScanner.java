/*     */ package com.sun.xml.bind.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.LocatorEx;
/*     */ import java.util.Enumeration;
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMScanner
/*     */   implements LocatorEx, InfosetScanner
/*     */ {
/*  80 */   private Node currentNode = null;
/*     */ 
/*     */   
/*  83 */   private final AttributesImpl atts = new AttributesImpl();
/*     */ 
/*     */   
/*  86 */   private ContentHandler receiver = null;
/*     */   
/*  88 */   private Locator locator = (Locator)this;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocator(Locator loc) {
/*  98 */     this.locator = loc;
/*     */   }
/*     */   
/*     */   public void scan(Object node) throws SAXException {
/* 102 */     if (node instanceof Document) {
/* 103 */       scan((Document)node);
/*     */     } else {
/* 105 */       scan((Element)node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void scan(Document doc) throws SAXException {
/* 110 */     scan(doc.getDocumentElement());
/*     */   }
/*     */   
/*     */   public void scan(Element e) throws SAXException {
/* 114 */     setCurrentLocation(e);
/*     */     
/* 116 */     this.receiver.setDocumentLocator(this.locator);
/* 117 */     this.receiver.startDocument();
/*     */     
/* 119 */     NamespaceSupport nss = new NamespaceSupport();
/* 120 */     buildNamespaceSupport(nss, e.getParentNode());
/*     */     
/* 122 */     for (Enumeration<String> enumeration1 = nss.getPrefixes(); enumeration1.hasMoreElements(); ) {
/* 123 */       String prefix = enumeration1.nextElement();
/* 124 */       this.receiver.startPrefixMapping(prefix, nss.getURI(prefix));
/*     */     } 
/*     */     
/* 127 */     visit(e);
/*     */     
/* 129 */     for (Enumeration<String> en = nss.getPrefixes(); en.hasMoreElements(); ) {
/* 130 */       String prefix = en.nextElement();
/* 131 */       this.receiver.endPrefixMapping(prefix);
/*     */     } 
/*     */ 
/*     */     
/* 135 */     setCurrentLocation(e);
/* 136 */     this.receiver.endDocument();
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
/*     */   public void parse(Element e, ContentHandler handler) throws SAXException {
/* 148 */     this.receiver = handler;
/*     */     
/* 150 */     setCurrentLocation(e);
/* 151 */     this.receiver.startDocument();
/*     */     
/* 153 */     this.receiver.setDocumentLocator(this.locator);
/* 154 */     visit(e);
/*     */     
/* 156 */     setCurrentLocation(e);
/* 157 */     this.receiver.endDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseWithContext(Element e, ContentHandler handler) throws SAXException {
/* 168 */     setContentHandler(handler);
/* 169 */     scan(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildNamespaceSupport(NamespaceSupport nss, Node node) {
/* 176 */     if (node == null || node.getNodeType() != 1) {
/*     */       return;
/*     */     }
/* 179 */     buildNamespaceSupport(nss, node.getParentNode());
/*     */     
/* 181 */     nss.pushContext();
/* 182 */     NamedNodeMap atts = node.getAttributes();
/* 183 */     for (int i = 0; i < atts.getLength(); i++) {
/* 184 */       Attr a = (Attr)atts.item(i);
/* 185 */       if ("xmlns".equals(a.getPrefix())) {
/* 186 */         nss.declarePrefix(a.getLocalName(), a.getValue());
/*     */       
/*     */       }
/* 189 */       else if ("xmlns".equals(a.getName())) {
/* 190 */         nss.declarePrefix("", a.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(Element e) throws SAXException {
/* 200 */     setCurrentLocation(e);
/* 201 */     NamedNodeMap attributes = e.getAttributes();
/*     */     
/* 203 */     this.atts.clear();
/* 204 */     int len = (attributes == null) ? 0 : attributes.getLength();
/*     */     
/* 206 */     for (int i = len - 1; i >= 0; i--) {
/* 207 */       Attr a = (Attr)attributes.item(i);
/* 208 */       String name = a.getName();
/*     */       
/* 210 */       if (name.startsWith("xmlns")) {
/* 211 */         if (name.length() == 5) {
/* 212 */           this.receiver.startPrefixMapping("", a.getValue());
/*     */         } else {
/* 214 */           String localName = a.getLocalName();
/* 215 */           if (localName == null)
/*     */           {
/* 217 */             localName = name.substring(6);
/*     */           }
/* 219 */           this.receiver.startPrefixMapping(localName, a.getValue());
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 224 */         String str1 = a.getNamespaceURI();
/* 225 */         if (str1 == null) str1 = "";
/*     */         
/* 227 */         String str2 = a.getLocalName();
/* 228 */         if (str2 == null) str2 = a.getName();
/*     */ 
/*     */         
/* 231 */         this.atts.addAttribute(str1, str2, a.getName(), "CDATA", a.getValue());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     String uri = e.getNamespaceURI();
/* 240 */     if (uri == null) uri = ""; 
/* 241 */     String local = e.getLocalName();
/* 242 */     String qname = e.getTagName();
/* 243 */     if (local == null) local = qname; 
/* 244 */     this.receiver.startElement(uri, local, qname, this.atts);
/*     */ 
/*     */     
/* 247 */     NodeList children = e.getChildNodes();
/* 248 */     int clen = children.getLength(); int j;
/* 249 */     for (j = 0; j < clen; j++) {
/* 250 */       visit(children.item(j));
/*     */     }
/*     */ 
/*     */     
/* 254 */     setCurrentLocation(e);
/* 255 */     this.receiver.endElement(uri, local, qname);
/*     */ 
/*     */     
/* 258 */     for (j = len - 1; j >= 0; j--) {
/* 259 */       Attr a = (Attr)attributes.item(j);
/* 260 */       String name = a.getName();
/* 261 */       if (name.startsWith("xmlns"))
/* 262 */         if (name.length() == 5) {
/* 263 */           this.receiver.endPrefixMapping("");
/*     */         } else {
/* 265 */           this.receiver.endPrefixMapping(a.getLocalName());
/*     */         }  
/*     */     } 
/*     */   } private void visit(Node n) throws SAXException {
/*     */     String value;
/*     */     ProcessingInstruction pi;
/* 271 */     setCurrentLocation(n);
/*     */ 
/*     */     
/* 274 */     switch (n.getNodeType()) {
/*     */       case 3:
/*     */       case 4:
/* 277 */         value = n.getNodeValue();
/* 278 */         this.receiver.characters(value.toCharArray(), 0, value.length());
/*     */         break;
/*     */       case 1:
/* 281 */         visit((Element)n);
/*     */         break;
/*     */       case 5:
/* 284 */         this.receiver.skippedEntity(n.getNodeName());
/*     */         break;
/*     */       case 7:
/* 287 */         pi = (ProcessingInstruction)n;
/* 288 */         this.receiver.processingInstruction(pi.getTarget(), pi.getData());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setCurrentLocation(Node currNode) {
/* 294 */     this.currentNode = currNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getCurrentLocation() {
/* 302 */     return this.currentNode;
/*     */   }
/*     */   
/*     */   public Object getCurrentElement() {
/* 306 */     return this.currentNode;
/*     */   }
/*     */   
/*     */   public LocatorEx getLocator() {
/* 310 */     return this;
/*     */   }
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) {
/* 314 */     this.receiver = handler;
/*     */   }
/*     */   
/*     */   public ContentHandler getContentHandler() {
/* 318 */     return this.receiver;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 323 */     return null; }
/* 324 */   public String getSystemId() { return null; }
/* 325 */   public int getLineNumber() { return -1; } public int getColumnNumber() {
/* 326 */     return -1;
/*     */   }
/*     */   public ValidationEventLocator getLocation() {
/* 329 */     return new ValidationEventLocatorImpl(getCurrentLocation());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bin\\unmarshaller\DOMScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */