/*     */ package com.sun.xml.ws.streaming;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.XMLStreamException2;
/*     */ import com.sun.xml.ws.util.xml.DummyLocation;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMStreamReader
/*     */   implements XMLStreamReader, NamespaceContext
/*     */ {
/*     */   protected Node _current;
/*     */   private Node _start;
/*     */   private NamedNodeMap _namedNodeMap;
/*     */   protected String wholeText;
/* 119 */   private final FinalArrayList<Attr> _currentAttributes = new FinalArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   protected Scope[] scopes = new Scope[8];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   protected int depth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class Scope
/*     */   {
/*     */     final Scope parent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     final FinalArrayList<Attr> currentNamespaces = new FinalArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     final FinalArrayList<String> additionalNamespaces = new FinalArrayList();
/*     */     
/*     */     Scope(Scope parent) {
/* 163 */       this.parent = parent;
/*     */     }
/*     */     
/*     */     void reset() {
/* 167 */       this.currentNamespaces.clear();
/* 168 */       this.additionalNamespaces.clear();
/*     */     }
/*     */     
/*     */     int getNamespaceCount() {
/* 172 */       return this.currentNamespaces.size() + this.additionalNamespaces.size() / 2;
/*     */     }
/*     */     
/*     */     String getNamespacePrefix(int index) {
/* 176 */       int sz = this.currentNamespaces.size();
/* 177 */       if (index < sz) {
/* 178 */         Attr attr = (Attr)this.currentNamespaces.get(index);
/* 179 */         String result = attr.getLocalName();
/* 180 */         if (result == null) {
/* 181 */           result = QName.valueOf(attr.getNodeName()).getLocalPart();
/*     */         }
/* 183 */         return result.equals("xmlns") ? null : result;
/*     */       } 
/* 185 */       return (String)this.additionalNamespaces.get((index - sz) * 2);
/*     */     }
/*     */ 
/*     */     
/*     */     String getNamespaceURI(int index) {
/* 190 */       int sz = this.currentNamespaces.size();
/* 191 */       if (index < sz) {
/* 192 */         return ((Attr)this.currentNamespaces.get(index)).getValue();
/*     */       }
/* 194 */       return (String)this.additionalNamespaces.get((index - sz) * 2 + 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getPrefix(String nsUri) {
/* 203 */       for (Scope sp = this; sp != null; sp = sp.parent) {
/* 204 */         int i; for (i = sp.currentNamespaces.size() - 1; i >= 0; i--) {
/* 205 */           String result = DOMStreamReader.getPrefixForAttr((Attr)sp.currentNamespaces.get(i), nsUri);
/* 206 */           if (result != null)
/* 207 */             return result; 
/*     */         } 
/* 209 */         for (i = sp.additionalNamespaces.size() - 2; i >= 0; i -= 2) {
/* 210 */           if (((String)sp.additionalNamespaces.get(i + 1)).equals(nsUri))
/* 211 */             return (String)sp.additionalNamespaces.get(i); 
/*     */         } 
/* 213 */       }  return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getNamespaceURI(@NotNull String prefix) {
/* 223 */       String nsDeclName = (prefix.length() == 0) ? "xmlns" : ("xmlns:" + prefix);
/*     */       
/* 225 */       for (Scope sp = this; sp != null; sp = sp.parent) {
/* 226 */         int i; for (i = sp.currentNamespaces.size() - 1; i >= 0; i--) {
/* 227 */           Attr a = (Attr)sp.currentNamespaces.get(i);
/* 228 */           if (a.getNodeName().equals(nsDeclName))
/* 229 */             return a.getValue(); 
/*     */         } 
/* 231 */         for (i = sp.additionalNamespaces.size() - 2; i >= 0; i -= 2) {
/* 232 */           if (((String)sp.additionalNamespaces.get(i)).equals(prefix))
/* 233 */             return (String)sp.additionalNamespaces.get(i + 1); 
/*     */         } 
/* 235 */       }  return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMStreamReader(Node node) {
/* 244 */     setCurrentNode(node);
/*     */   }
/*     */   
/*     */   public void setCurrentNode(Node node) {
/* 248 */     this.scopes[0] = new Scope(null);
/* 249 */     this.depth = 0;
/*     */     
/* 251 */     this._start = this._current = node;
/* 252 */     this._state = 7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void splitAttributes() {
/* 267 */     this._currentAttributes.clear();
/*     */     
/* 269 */     Scope scope = allocateScope();
/*     */     
/* 271 */     this._namedNodeMap = this._current.getAttributes();
/* 272 */     if (this._namedNodeMap != null) {
/* 273 */       int n = this._namedNodeMap.getLength();
/* 274 */       for (int j = 0; j < n; j++) {
/* 275 */         Attr attr = (Attr)this._namedNodeMap.item(j);
/* 276 */         String attrName = attr.getNodeName();
/* 277 */         if (attrName.startsWith("xmlns:") || attrName.equals("xmlns")) {
/* 278 */           scope.currentNamespaces.add(attr);
/*     */         } else {
/*     */           
/* 281 */           this._currentAttributes.add(attr);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 287 */     ensureNs(this._current);
/* 288 */     for (int i = this._currentAttributes.size() - 1; i >= 0; i--) {
/* 289 */       Attr a = (Attr)this._currentAttributes.get(i);
/* 290 */       if (fixNull(a.getNamespaceURI()).length() > 0) {
/* 291 */         ensureNs(a);
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
/*     */   private void ensureNs(Node n) {
/* 306 */     String prefix = fixNull(n.getPrefix());
/* 307 */     String uri = fixNull(n.getNamespaceURI());
/*     */     
/* 309 */     Scope scope = this.scopes[this.depth];
/*     */     
/* 311 */     String currentUri = scope.getNamespaceURI(prefix);
/*     */     
/* 313 */     if (prefix.length() == 0) {
/* 314 */       currentUri = fixNull(currentUri);
/* 315 */       if (currentUri.equals(uri)) {
/*     */         return;
/*     */       }
/* 318 */     } else if (currentUri != null && currentUri.equals(uri)) {
/*     */       return;
/*     */     } 
/*     */     
/* 322 */     if (prefix.equals("xml") || prefix.equals("xmlns")) {
/*     */       return;
/*     */     }
/*     */     
/* 326 */     scope.additionalNamespaces.add(prefix);
/* 327 */     scope.additionalNamespaces.add(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Scope allocateScope() {
/* 334 */     if (this.scopes.length == ++this.depth) {
/* 335 */       Scope[] newBuf = new Scope[this.scopes.length * 2];
/* 336 */       System.arraycopy(this.scopes, 0, newBuf, 0, this.scopes.length);
/* 337 */       this.scopes = newBuf;
/*     */     } 
/* 339 */     Scope scope = this.scopes[this.depth];
/* 340 */     if (scope == null) {
/* 341 */       scope = this.scopes[this.depth] = new Scope(this.scopes[this.depth - 1]);
/*     */     } else {
/* 343 */       scope.reset();
/*     */     } 
/* 345 */     return scope;
/*     */   }
/*     */   
/*     */   public int getAttributeCount() {
/* 349 */     if (this._state == 1)
/* 350 */       return this._currentAttributes.size(); 
/* 351 */     throw new IllegalStateException("DOMStreamReader: getAttributeCount() called in illegal state");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeLocalName(int index) {
/* 358 */     if (this._state == 1) {
/* 359 */       String localName = ((Attr)this._currentAttributes.get(index)).getLocalName();
/* 360 */       return (localName != null) ? localName : QName.valueOf(((Attr)this._currentAttributes.get(index)).getNodeName()).getLocalPart();
/*     */     } 
/*     */     
/* 363 */     throw new IllegalStateException("DOMStreamReader: getAttributeLocalName() called in illegal state");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getAttributeName(int index) {
/* 370 */     if (this._state == 1) {
/* 371 */       Node attr = (Node)this._currentAttributes.get(index);
/* 372 */       String localName = attr.getLocalName();
/* 373 */       if (localName != null) {
/* 374 */         String prefix = attr.getPrefix();
/* 375 */         String uri = attr.getNamespaceURI();
/* 376 */         return new QName(fixNull(uri), localName, fixNull(prefix));
/*     */       } 
/*     */       
/* 379 */       return QName.valueOf(attr.getNodeName());
/*     */     } 
/*     */     
/* 382 */     throw new IllegalStateException("DOMStreamReader: getAttributeName() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getAttributeNamespace(int index) {
/* 386 */     if (this._state == 1) {
/* 387 */       String uri = ((Attr)this._currentAttributes.get(index)).getNamespaceURI();
/* 388 */       return fixNull(uri);
/*     */     } 
/* 390 */     throw new IllegalStateException("DOMStreamReader: getAttributeNamespace() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getAttributePrefix(int index) {
/* 394 */     if (this._state == 1) {
/* 395 */       String prefix = ((Attr)this._currentAttributes.get(index)).getPrefix();
/* 396 */       return fixNull(prefix);
/*     */     } 
/* 398 */     throw new IllegalStateException("DOMStreamReader: getAttributePrefix() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getAttributeType(int index) {
/* 402 */     if (this._state == 1) {
/* 403 */       return "CDATA";
/*     */     }
/* 405 */     throw new IllegalStateException("DOMStreamReader: getAttributeType() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getAttributeValue(int index) {
/* 409 */     if (this._state == 1) {
/* 410 */       return ((Attr)this._currentAttributes.get(index)).getNodeValue();
/*     */     }
/* 412 */     throw new IllegalStateException("DOMStreamReader: getAttributeValue() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getAttributeValue(String namespaceURI, String localName) {
/* 416 */     if (this._state == 1) {
/* 417 */       if (this._namedNodeMap != null) {
/* 418 */         Node attr = this._namedNodeMap.getNamedItemNS(namespaceURI, localName);
/* 419 */         return (attr != null) ? attr.getNodeValue() : null;
/*     */       } 
/* 421 */       return null;
/*     */     } 
/* 423 */     throw new IllegalStateException("DOMStreamReader: getAttributeValue() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 427 */     return null;
/*     */   }
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 431 */     throw new RuntimeException("DOMStreamReader: getElementText() not implemented");
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 435 */     return null;
/*     */   }
/*     */   
/*     */   public int getEventType() {
/* 439 */     return this._state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalName() {
/* 446 */     if (this._state == 1 || this._state == 2) {
/* 447 */       String localName = this._current.getLocalName();
/* 448 */       return (localName != null) ? localName : QName.valueOf(this._current.getNodeName()).getLocalPart();
/*     */     } 
/*     */     
/* 451 */     if (this._state == 9) {
/* 452 */       return this._current.getNodeName();
/*     */     }
/* 454 */     throw new IllegalStateException("DOMStreamReader: getAttributeValue() called in illegal state");
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 458 */     return DummyLocation.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 465 */     if (this._state == 1 || this._state == 2) {
/* 466 */       String localName = this._current.getLocalName();
/* 467 */       if (localName != null) {
/* 468 */         String prefix = this._current.getPrefix();
/* 469 */         String uri = this._current.getNamespaceURI();
/* 470 */         return new QName(fixNull(uri), localName, fixNull(prefix));
/*     */       } 
/*     */       
/* 473 */       return QName.valueOf(this._current.getNodeName());
/*     */     } 
/*     */     
/* 476 */     throw new IllegalStateException("DOMStreamReader: getName() called in illegal state");
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 480 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Scope getCheckedScope() {
/* 490 */     if (this._state == 1 || this._state == 2) {
/* 491 */       return this.scopes[this.depth];
/*     */     }
/* 493 */     throw new IllegalStateException("DOMStreamReader: neither on START_ELEMENT nor END_ELEMENT");
/*     */   }
/*     */   
/*     */   public int getNamespaceCount() {
/* 497 */     return getCheckedScope().getNamespaceCount();
/*     */   }
/*     */   
/*     */   public String getNamespacePrefix(int index) {
/* 501 */     return getCheckedScope().getNamespacePrefix(index);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(int index) {
/* 505 */     return getCheckedScope().getNamespaceURI(index);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 509 */     if (this._state == 1 || this._state == 2) {
/* 510 */       String uri = this._current.getNamespaceURI();
/* 511 */       return fixNull(uri);
/*     */     } 
/* 513 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 522 */     if (prefix == null) {
/* 523 */       throw new IllegalArgumentException("DOMStreamReader: getNamespaceURI(String) call with a null prefix");
/*     */     }
/* 525 */     if (prefix.equals("xml")) {
/* 526 */       return "http://www.w3.org/XML/1998/namespace";
/*     */     }
/* 528 */     if (prefix.equals("xmlns")) {
/* 529 */       return "http://www.w3.org/2000/xmlns/";
/*     */     }
/*     */ 
/*     */     
/* 533 */     String nsUri = this.scopes[this.depth].getNamespaceURI(prefix);
/* 534 */     if (nsUri != null) return nsUri;
/*     */ 
/*     */     
/* 537 */     Node node = findRootElement();
/* 538 */     String nsDeclName = (prefix.length() == 0) ? "xmlns" : ("xmlns:" + prefix);
/* 539 */     while (node.getNodeType() != 9) {
/*     */       
/* 541 */       NamedNodeMap namedNodeMap = node.getAttributes();
/* 542 */       Attr attr = (Attr)namedNodeMap.getNamedItem(nsDeclName);
/* 543 */       if (attr != null)
/* 544 */         return attr.getValue(); 
/* 545 */       node = node.getParentNode();
/*     */     } 
/* 547 */     return null;
/*     */   }
/*     */   
/*     */   public String getPrefix(String nsUri) {
/* 551 */     if (nsUri == null) {
/* 552 */       throw new IllegalArgumentException("DOMStreamReader: getPrefix(String) call with a null namespace URI");
/*     */     }
/* 554 */     if (nsUri.equals("http://www.w3.org/XML/1998/namespace")) {
/* 555 */       return "xml";
/*     */     }
/* 557 */     if (nsUri.equals("http://www.w3.org/2000/xmlns/")) {
/* 558 */       return "xmlns";
/*     */     }
/*     */ 
/*     */     
/* 562 */     String prefix = this.scopes[this.depth].getPrefix(nsUri);
/* 563 */     if (prefix != null) return prefix;
/*     */ 
/*     */     
/* 566 */     Node node = findRootElement();
/*     */     
/* 568 */     while (node.getNodeType() != 9) {
/*     */       
/* 570 */       NamedNodeMap namedNodeMap = node.getAttributes();
/* 571 */       for (int i = namedNodeMap.getLength() - 1; i >= 0; i--) {
/* 572 */         Attr attr = (Attr)namedNodeMap.item(i);
/* 573 */         prefix = getPrefixForAttr(attr, nsUri);
/* 574 */         if (prefix != null)
/* 575 */           return prefix; 
/*     */       } 
/* 577 */       node = node.getParentNode();
/*     */     } 
/* 579 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Node findRootElement() {
/* 588 */     Node node = this._start;
/*     */     int type;
/* 590 */     while ((type = node.getNodeType()) != 9 && type != 1) {
/* 591 */       node = node.getParentNode();
/*     */     }
/* 593 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPrefixForAttr(Attr attr, String nsUri) {
/* 601 */     String attrName = attr.getNodeName();
/* 602 */     if (!attrName.startsWith("xmlns:") && !attrName.equals("xmlns")) {
/* 603 */       return null;
/*     */     }
/* 605 */     if (attr.getValue().equals(nsUri)) {
/* 606 */       if (attrName.equals("xmlns"))
/* 607 */         return ""; 
/* 608 */       String localName = attr.getLocalName();
/* 609 */       return (localName != null) ? localName : QName.valueOf(attrName).getLocalPart();
/*     */     } 
/*     */ 
/*     */     
/* 613 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getPrefixes(String nsUri) {
/* 619 */     String prefix = getPrefix(nsUri);
/* 620 */     if (prefix == null) return Collections.emptyList().iterator(); 
/* 621 */     return Collections.<String>singletonList(prefix).iterator();
/*     */   }
/*     */   
/*     */   public String getPIData() {
/* 625 */     if (this._state == 3) {
/* 626 */       return ((ProcessingInstruction)this._current).getData();
/*     */     }
/* 628 */     return null;
/*     */   }
/*     */   
/*     */   public String getPITarget() {
/* 632 */     if (this._state == 3) {
/* 633 */       return ((ProcessingInstruction)this._current).getTarget();
/*     */     }
/* 635 */     return null;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 639 */     if (this._state == 1 || this._state == 2) {
/* 640 */       String prefix = this._current.getPrefix();
/* 641 */       return fixNull(prefix);
/*     */     } 
/* 643 */     return null;
/*     */   }
/*     */   
/*     */   public Object getProperty(String str) throws IllegalArgumentException {
/* 647 */     return null;
/*     */   }
/*     */   
/*     */   public String getText() {
/* 651 */     if (this._state == 4)
/* 652 */       return this.wholeText; 
/* 653 */     if (this._state == 12 || this._state == 5 || this._state == 9)
/* 654 */       return this._current.getNodeValue(); 
/* 655 */     throw new IllegalStateException("DOMStreamReader: getTextLength() called in illegal state");
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters() {
/* 659 */     return getText().toCharArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int targetLength) throws XMLStreamException {
/* 664 */     String text = getText();
/* 665 */     int copiedSize = Math.min(targetLength, text.length() - sourceStart);
/* 666 */     text.getChars(sourceStart, sourceStart + copiedSize, target, targetStart);
/*     */     
/* 668 */     return copiedSize;
/*     */   }
/*     */   
/*     */   public int getTextLength() {
/* 672 */     return getText().length();
/*     */   }
/*     */   
/*     */   public int getTextStart() {
/* 676 */     if (this._state == 4 || this._state == 12 || this._state == 5 || this._state == 9) {
/* 677 */       return 0;
/*     */     }
/* 679 */     throw new IllegalStateException("DOMStreamReader: getTextStart() called in illegal state");
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 683 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasName() {
/* 687 */     return (this._state == 1 || this._state == 2);
/*     */   }
/*     */   
/*     */   public boolean hasNext() throws XMLStreamException {
/* 691 */     return (this._state != 8);
/*     */   }
/*     */   
/*     */   public boolean hasText() {
/* 695 */     if (this._state == 4 || this._state == 12 || this._state == 5 || this._state == 9) {
/* 696 */       return (getText().trim().length() > 0);
/*     */     }
/* 698 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAttributeSpecified(int param) {
/* 702 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCharacters() {
/* 706 */     return (this._state == 4);
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/* 710 */     return (this._state == 2);
/*     */   }
/*     */   
/*     */   public boolean isStandalone() {
/* 714 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isStartElement() {
/* 718 */     return (this._state == 1);
/*     */   }
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 722 */     if (this._state == 4 || this._state == 12)
/* 723 */       return (getText().trim().length() == 0); 
/* 724 */     return false;
/*     */   }
/*     */   
/*     */   private static int mapNodeTypeToState(int nodetype) {
/* 728 */     switch (nodetype) {
/*     */       case 4:
/* 730 */         return 12;
/*     */       case 8:
/* 732 */         return 5;
/*     */       case 1:
/* 734 */         return 1;
/*     */       case 6:
/* 736 */         return 15;
/*     */       case 5:
/* 738 */         return 9;
/*     */       case 12:
/* 740 */         return 14;
/*     */       case 7:
/* 742 */         return 3;
/*     */       case 3:
/* 744 */         return 4;
/*     */     } 
/* 746 */     throw new RuntimeException("DOMStreamReader: Unexpected node type");
/*     */   } public int next() throws XMLStreamException {
/*     */     int r;
/*     */     while (true) {
/*     */       Node prev;
/*     */       Text t;
/* 752 */       r = _next();
/* 753 */       switch (r) {
/*     */         
/*     */         case 4:
/* 756 */           prev = this._current.getPreviousSibling();
/* 757 */           if (prev != null && prev.getNodeType() == 3) {
/*     */             continue;
/*     */           }
/* 760 */           t = (Text)this._current;
/* 761 */           this.wholeText = t.getWholeText();
/* 762 */           if (this.wholeText.length() == 0)
/*     */             continue; 
/* 764 */           return 4;
/*     */         case 1:
/* 766 */           splitAttributes();
/* 767 */           return 1;
/*     */       }  break;
/* 769 */     }  return r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _next() throws XMLStreamException {
/*     */     Node child;
/*     */     Node sibling;
/* 777 */     switch (this._state) {
/*     */       case 8:
/* 779 */         throw new IllegalStateException("DOMStreamReader: Calling next() at END_DOCUMENT");
/*     */       
/*     */       case 7:
/* 782 */         if (this._current.getNodeType() == 1) {
/* 783 */           return this._state = 1;
/*     */         }
/*     */         
/* 786 */         child = this._current.getFirstChild();
/* 787 */         if (child == null) {
/* 788 */           return this._state = 8;
/*     */         }
/*     */         
/* 791 */         this._current = child;
/* 792 */         return this._state = mapNodeTypeToState(this._current.getNodeType());
/*     */       
/*     */       case 1:
/* 795 */         child = this._current.getFirstChild();
/* 796 */         if (child == null) {
/* 797 */           return this._state = 2;
/*     */         }
/*     */         
/* 800 */         this._current = child;
/* 801 */         return this._state = mapNodeTypeToState(this._current.getNodeType());
/*     */       
/*     */       case 2:
/* 804 */         this.depth--;
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 9:
/*     */       case 12:
/* 812 */         if (this._current == this._start) {
/* 813 */           return this._state = 8;
/*     */         }
/*     */         
/* 816 */         sibling = this._current.getNextSibling();
/* 817 */         if (sibling == null) {
/* 818 */           this._current = this._current.getParentNode();
/*     */           
/* 820 */           this._state = (this._current == null || this._current.getNodeType() == 9) ? 8 : 2;
/*     */           
/* 822 */           return this._state;
/*     */         } 
/*     */         
/* 825 */         this._current = sibling;
/* 826 */         return this._state = mapNodeTypeToState(this._current.getNodeType());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 832 */     throw new RuntimeException("DOMStreamReader: Unexpected internal state");
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/* 837 */     int eventType = next();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 842 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5)
/*     */     {
/* 844 */       eventType = next();
/*     */     }
/* 846 */     if (eventType != 1 && eventType != 2) {
/* 847 */       throw new XMLStreamException2("DOMStreamReader: Expected start or end tag");
/*     */     }
/* 849 */     return eventType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/* 855 */     if (type != this._state) {
/* 856 */       throw new XMLStreamException2("DOMStreamReader: Required event type not found");
/*     */     }
/* 858 */     if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI())) {
/* 859 */       throw new XMLStreamException2("DOMStreamReader: Required namespaceURI not found");
/*     */     }
/* 861 */     if (localName != null && !localName.equals(getLocalName())) {
/* 862 */       throw new XMLStreamException2("DOMStreamReader: Required localName not found");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean standaloneSet() {
/* 867 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void displayDOM(Node node, OutputStream ostream) {
/*     */     try {
/* 876 */       System.out.println("\n====\n");
/* 877 */       XmlUtil.newTransformer().transform(new DOMSource(node), new StreamResult(ostream));
/*     */       
/* 879 */       System.out.println("\n====\n");
/*     */     }
/* 881 */     catch (Exception e) {
/* 882 */       e.printStackTrace();
/*     */     }  } private static void verifyDOMIntegrity(Node node) { NamedNodeMap attrs;
/*     */     int i;
/*     */     NodeList children;
/*     */     int j;
/* 887 */     switch (node.getNodeType()) {
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 892 */         if (node.getLocalName() == null) {
/* 893 */           System.out.println("WARNING: DOM level 1 node found");
/* 894 */           System.out.println(" -> node.getNodeName() = " + node.getNodeName());
/* 895 */           System.out.println(" -> node.getNamespaceURI() = " + node.getNamespaceURI());
/* 896 */           System.out.println(" -> node.getLocalName() = " + node.getLocalName());
/* 897 */           System.out.println(" -> node.getPrefix() = " + node.getPrefix());
/*     */         } 
/*     */         
/* 900 */         if (node.getNodeType() == 2)
/*     */           return; 
/* 902 */         attrs = node.getAttributes();
/* 903 */         for (i = 0; i < attrs.getLength(); i++) {
/* 904 */           verifyDOMIntegrity(attrs.item(i));
/*     */         }
/*     */       case 9:
/* 907 */         children = node.getChildNodes();
/* 908 */         for (j = 0; j < children.getLength(); j++) {
/* 909 */           verifyDOMIntegrity(children.item(j));
/*     */         }
/*     */         break;
/*     */     }  }
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 916 */     if (s == null) return ""; 
/* 917 */     return s;
/*     */   }
/*     */   
/*     */   public DOMStreamReader() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\streaming\DOMStreamReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */