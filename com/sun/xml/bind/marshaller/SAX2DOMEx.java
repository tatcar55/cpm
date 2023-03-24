/*     */ package com.sun.xml.bind.marshaller;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.util.Which;
/*     */ import com.sun.xml.bind.v2.util.XmlFactory;
/*     */ import java.util.Stack;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ public class SAX2DOMEx
/*     */   implements ContentHandler
/*     */ {
/*  68 */   private Node node = null;
/*     */   private boolean isConsolidate;
/*  70 */   protected final Stack<Node> nodeStack = new Stack<Node>();
/*  71 */   private final FinalArrayList<String> unprocessedNamespaces = new FinalArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Document document;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx(Node node) {
/*  82 */     this(node, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx(Node node, boolean isConsolidate) {
/*  90 */     this.node = node;
/*  91 */     this.isConsolidate = isConsolidate;
/*  92 */     this.nodeStack.push(this.node);
/*     */     
/*  94 */     if (node instanceof Document) {
/*  95 */       this.document = (Document)node;
/*     */     } else {
/*  97 */       this.document = node.getOwnerDocument();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx(DocumentBuilderFactory f) throws ParserConfigurationException {
/* 105 */     f.setValidating(false);
/* 106 */     this.document = f.newDocumentBuilder().newDocument();
/* 107 */     this.node = this.document;
/* 108 */     this.nodeStack.push(this.document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx() throws ParserConfigurationException {
/* 116 */     DocumentBuilderFactory factory = XmlFactory.createDocumentBuilderFactory(false);
/* 117 */     factory.setValidating(false);
/*     */     
/* 119 */     this.document = factory.newDocumentBuilder().newDocument();
/* 120 */     this.node = this.document;
/* 121 */     this.nodeStack.push(this.document);
/*     */   }
/*     */   
/*     */   public final Element getCurrentElement() {
/* 125 */     return (Element)this.nodeStack.peek();
/*     */   }
/*     */   
/*     */   public Node getDOM() {
/* 129 */     return this.node;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() {}
/*     */ 
/*     */   
/*     */   public void endDocument() {}
/*     */   
/*     */   protected void namespace(Element element, String prefix, String uri) {
/*     */     String qname;
/* 140 */     if ("".equals(prefix) || prefix == null) {
/* 141 */       qname = "xmlns";
/*     */     } else {
/* 143 */       qname = "xmlns:" + prefix;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (element.hasAttributeNS("http://www.w3.org/2000/xmlns/", qname))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       element.removeAttributeNS("http://www.w3.org/2000/xmlns/", qname);
/*     */     }
/*     */ 
/*     */     
/* 161 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", qname, uri);
/*     */   }
/*     */   
/*     */   public void startElement(String namespace, String localName, String qName, Attributes attrs) {
/* 165 */     Node parent = this.nodeStack.peek();
/*     */ 
/*     */ 
/*     */     
/* 169 */     Element element = this.document.createElementNS(namespace, qName);
/*     */     
/* 171 */     if (element == null)
/*     */     {
/*     */       
/* 174 */       throw new AssertionError(Messages.format("SAX2DOMEx.DomImplDoesntSupportCreateElementNs", this.document.getClass().getName(), Which.which(this.document.getClass())));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     for (int i = 0; i < this.unprocessedNamespaces.size(); i += 2) {
/* 182 */       String prefix = (String)this.unprocessedNamespaces.get(i);
/* 183 */       String uri = (String)this.unprocessedNamespaces.get(i + 1);
/*     */       
/* 185 */       namespace(element, prefix, uri);
/*     */     } 
/* 187 */     this.unprocessedNamespaces.clear();
/*     */ 
/*     */     
/* 190 */     if (attrs != null) {
/* 191 */       int length = attrs.getLength();
/* 192 */       for (int j = 0; j < length; j++) {
/* 193 */         String namespaceuri = attrs.getURI(j);
/* 194 */         String value = attrs.getValue(j);
/* 195 */         String qname = attrs.getQName(j);
/* 196 */         element.setAttributeNS(namespaceuri, qname, value);
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     parent.appendChild(element);
/*     */     
/* 202 */     this.nodeStack.push(element);
/*     */   }
/*     */   
/*     */   public void endElement(String namespace, String localName, String qName) {
/* 206 */     this.nodeStack.pop();
/*     */   }
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/* 210 */     characters(new String(ch, start, length));
/*     */   }
/*     */   protected Text characters(String s) {
/*     */     Text text;
/* 214 */     Node parent = this.nodeStack.peek();
/* 215 */     Node lastChild = parent.getLastChild();
/*     */     
/* 217 */     if (this.isConsolidate && lastChild != null && lastChild.getNodeType() == 3) {
/* 218 */       text = (Text)lastChild;
/* 219 */       text.appendData(s);
/*     */     } else {
/* 221 */       text = this.document.createTextNode(s);
/* 222 */       parent.appendChild(text);
/*     */     } 
/* 224 */     return text;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) {}
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 231 */     Node parent = this.nodeStack.peek();
/* 232 */     Node n = this.document.createProcessingInstruction(target, data);
/* 233 */     parent.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) {}
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) {
/* 243 */     this.unprocessedNamespaces.add(prefix);
/* 244 */     this.unprocessedNamespaces.add(uri);
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\marshaller\SAX2DOMEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */