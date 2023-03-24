/*      */ package com.sun.xml.wss.impl.transform;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
/*      */ import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
/*      */ import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
/*      */ import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
/*      */ import com.sun.xml.wss.WSITXMLFactory;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeSet;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.crypto.Data;
/*      */ import javax.xml.crypto.NodeSetData;
/*      */ import javax.xml.crypto.URIDereferencer;
/*      */ import javax.xml.crypto.URIReferenceException;
/*      */ import javax.xml.crypto.XMLCryptoContext;
/*      */ import javax.xml.crypto.dom.DOMURIReference;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Comment;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.ProcessingInstruction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Canonicalizer20010315ExclOmitComments
/*      */ {
/*  118 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*      */   
/*  120 */   private static final byte[] _END_PI = new byte[] { 63, 62 };
/*  121 */   private static final byte[] _BEGIN_PI = new byte[] { 60, 63 };
/*  122 */   private static final byte[] _END_COMM = new byte[] { 45, 45, 62 };
/*  123 */   private static final byte[] _BEGIN_COMM = new byte[] { 60, 33, 45, 45 };
/*  124 */   private static final byte[] __XA_ = new byte[] { 38, 35, 120, 65, 59 };
/*  125 */   private static final byte[] __X9_ = new byte[] { 38, 35, 120, 57, 59 };
/*  126 */   private static final byte[] _QUOT_ = new byte[] { 38, 113, 117, 111, 116, 59 };
/*  127 */   private static final byte[] __XD_ = new byte[] { 38, 35, 120, 68, 59 };
/*  128 */   private static final byte[] _GT_ = new byte[] { 38, 103, 116, 59 };
/*  129 */   private static final byte[] _LT_ = new byte[] { 38, 108, 116, 59 };
/*  130 */   private static final byte[] _END_TAG = new byte[] { 60, 47 };
/*  131 */   private static final byte[] _AMP_ = new byte[] { 38, 97, 109, 112, 59 };
/*  132 */   static final AttrCompare COMPARE = new AttrCompare();
/*      */   static final String XML = "xml";
/*      */   static final String XMLNS = "xmlns";
/*  135 */   static final byte[] equalsStr = new byte[] { 61, 34 };
/*      */   
/*      */   static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
/*      */   static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
/*      */   static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
/*      */   protected static final Attr nullNode;
/*      */   
/*      */   static {
/*      */     try {
/*  144 */       nullNode = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newDocumentBuilder().newDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/*      */       
/*  146 */       nullNode.setValue("");
/*  147 */     } catch (Exception e) {
/*  148 */       throw new RuntimeException("Unable to create nullNode" + e);
/*      */     } 
/*      */   }
/*  151 */   XMLCryptoContext cryptoContext = null;
/*      */   boolean _includeComments;
/*      */   boolean reset = false;
/*  154 */   Set _xpathNodeSet = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   Node _excludeNode = null;
/*      */   
/*  161 */   OutputStream _writer = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  166 */   TreeSet _inclusiveNSSet = null;
/*  167 */   Set tokenSet = new HashSet();
/*      */ 
/*      */   
/*      */   static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
/*      */ 
/*      */   
/*      */   public Canonicalizer20010315ExclOmitComments() {
/*  174 */     this._includeComments = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void engineCanonicalizeXPathNodeSet(Set xpathNodeSet, String inclusiveNamespaces, OutputStream stream, XMLCryptoContext context) throws CanonicalizationException, URIReferenceException {
/*      */     try {
/*  188 */       this._writer = stream;
/*  189 */       this._inclusiveNSSet = (TreeSet)InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
/*      */       
/*  191 */       if (xpathNodeSet.size() == 0) {
/*      */         return;
/*      */       }
/*      */       
/*  195 */       this._xpathNodeSet = xpathNodeSet;
/*  196 */       this.cryptoContext = context;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  217 */       this._inclusiveNSSet = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Iterator handleAttributes(Element E, NameSpaceSymbTable ns, boolean isOutputElement) throws CanonicalizationException {
/*  232 */     SortedSet<Attr> result = new TreeSet(COMPARE);
/*  233 */     NamedNodeMap attrs = null;
/*  234 */     int attrsLength = 0;
/*  235 */     if (E.hasAttributes()) {
/*  236 */       attrs = E.getAttributes();
/*  237 */       attrsLength = attrs.getLength();
/*      */     } 
/*      */ 
/*      */     
/*  241 */     Set<String> visiblyUtilized = null;
/*      */ 
/*      */     
/*  244 */     if (isOutputElement) {
/*  245 */       visiblyUtilized = (Set)this._inclusiveNSSet.clone();
/*      */     }
/*      */     
/*  248 */     for (int i = 0; i < attrsLength; i++) {
/*  249 */       Attr N = (Attr)attrs.item(i);
/*  250 */       String NName = N.getLocalName();
/*  251 */       String NNodeValue = N.getNodeValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  257 */       if (!"http://www.w3.org/2000/xmlns/".equals(N.getNamespaceURI())) {
/*      */         
/*  259 */         if (isOutputElement)
/*      */         {
/*  261 */           String prefix = N.getPrefix();
/*  262 */           if (prefix != null && !prefix.equals("xml") && !prefix.equals("xmlns")) {
/*  263 */             visiblyUtilized.add(prefix);
/*      */           }
/*      */           
/*  266 */           result.add(N);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*  272 */       else if (ns.addMapping(NName, NNodeValue, N)) {
/*      */         
/*  274 */         if (C14nHelper.namespaceIsRelative(NNodeValue)) {
/*  275 */           Object[] exArgs = { E.getTagName(), NName, N.getNodeValue() };
/*      */           
/*  277 */           throw new CanonicalizationException("c14n.Canonicalizer.RelativeNamespace", exArgs);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  283 */     if (isOutputElement) {
/*      */       
/*  285 */       Attr xmlns = E.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/*  286 */       if (xmlns != null && !this._xpathNodeSet.contains(xmlns))
/*      */       {
/*      */         
/*  289 */         ns.addMapping("xmlns", "", nullNode);
/*      */       }
/*      */       
/*  292 */       if (E.getNamespaceURI() != null) {
/*  293 */         String prefix = E.getPrefix();
/*  294 */         if (prefix == null || prefix.length() == 0) {
/*  295 */           visiblyUtilized.add("xmlns");
/*      */         } else {
/*  297 */           visiblyUtilized.add(prefix);
/*      */         } 
/*      */       } else {
/*  300 */         visiblyUtilized.add("xmlns");
/*      */       } 
/*      */ 
/*      */       
/*  304 */       Iterator<String> it = visiblyUtilized.iterator();
/*  305 */       while (it.hasNext()) {
/*  306 */         String s = it.next();
/*  307 */         Attr key = ns.getMapping(s);
/*  308 */         if (key == null) {
/*      */           continue;
/*      */         }
/*  311 */         result.add(key);
/*      */       } 
/*      */     } else {
/*  314 */       Iterator<String> it = this._inclusiveNSSet.iterator();
/*  315 */       while (it.hasNext()) {
/*  316 */         String s = it.next();
/*  317 */         Attr key = ns.getMappingWithoutRendered(s);
/*  318 */         if (key == null) {
/*      */           continue;
/*      */         }
/*  321 */         result.add(key);
/*      */       } 
/*      */     } 
/*      */     
/*  325 */     return result.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int getPositionRelativeToDocumentElement(Node currentNode) {
/*  342 */     if (currentNode == null || currentNode.getParentNode().getNodeType() != 9)
/*      */     {
/*  344 */       return 0;
/*      */     }
/*  346 */     Element documentElement = currentNode.getOwnerDocument().getDocumentElement();
/*  347 */     if (documentElement == null || documentElement == currentNode) {
/*  348 */       return 0;
/*      */     }
/*      */     
/*  351 */     for (Node x = currentNode; x != null; x = x.getNextSibling()) {
/*  352 */       if (x == documentElement) {
/*  353 */         return -1;
/*      */       }
/*      */     } 
/*      */     
/*  357 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void canonicalizeXPathNodeSet(Node currentNode, NameSpaceSymbTable ns) throws CanonicalizationException, IOException, URIReferenceException {
/*      */     Node currentChild;
/*  372 */     boolean currentNodeIsVisible = this._xpathNodeSet.contains(currentNode);
/*      */     
/*  374 */     switch (currentNode.getNodeType()) {
/*      */       default:
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 6:
/*      */       case 11:
/*      */       case 12:
/*  384 */         throw new CanonicalizationException("empty");
/*      */       case 9:
/*  386 */         currentChild = currentNode.getFirstChild();
/*  387 */         for (; currentChild != null; 
/*  388 */           currentChild = currentChild.getNextSibling()) {
/*  389 */           canonicalizeXPathNodeSet(currentChild, ns);
/*      */         }
/*      */ 
/*      */       
/*      */       case 8:
/*  394 */         if (currentNodeIsVisible && this._includeComments) {
/*  395 */           outputCommentToWriter((Comment)currentNode, this._writer);
/*      */         }
/*      */ 
/*      */       
/*      */       case 7:
/*  400 */         if (currentNodeIsVisible) {
/*  401 */           outputPItoWriter((ProcessingInstruction)currentNode, this._writer);
/*      */         }
/*      */ 
/*      */       
/*      */       case 3:
/*  406 */         if (currentNodeIsVisible && currentNode.getParentNode().getLocalName().equals("BinarySecurityToken") && currentNode.getParentNode().getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"))
/*      */         {
/*  408 */           outputTextToWriter(currentNode.getNodeValue(), this._writer, true);
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  414 */         if (currentNodeIsVisible) {
/*  415 */           outputTextToWriter(currentNode.getNodeValue(), this._writer);
/*      */           
/*  417 */           Node nextSibling = currentNode.getNextSibling();
/*      */           
/*  419 */           for (; nextSibling != null && (nextSibling.getNodeType() == 3 || nextSibling.getNodeType() == 4); 
/*      */ 
/*      */             
/*  422 */             nextSibling = nextSibling.getNextSibling())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  429 */             outputTextToWriter(nextSibling.getNodeValue(), this._writer);
/*      */           }
/*      */         } 
/*      */       case 1:
/*      */         break;
/*      */     } 
/*  435 */     String localName = currentNode.getLocalName();
/*  436 */     Element currentElement = null;
/*  437 */     if (currentNodeIsVisible && localName != null && localName.equals("SecurityTokenReference")) {
/*  438 */       String namespaceURI = currentNode.getNamespaceURI();
/*  439 */       currentElement = (Element)currentNode;
/*  440 */       if (namespaceURI != null && namespaceURI.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) {
/*  441 */         currentElement = (Element)deReference(currentNode, this.cryptoContext);
/*  442 */         STRTransformImpl.toNodeSet(currentElement, this.tokenSet);
/*  443 */         removeNodes(currentNode, this._xpathNodeSet);
/*      */         
/*  445 */         printBinaryToken(currentElement, ns);
/*      */         
/*  447 */         this.tokenSet.clear();
/*      */       } 
/*      */     } 
/*      */     
/*  451 */     currentElement = (Element)currentNode;
/*      */ 
/*      */     
/*  454 */     OutputStream writer = this._writer;
/*  455 */     String tagName = currentElement.getTagName();
/*  456 */     if (currentNodeIsVisible) {
/*      */       
/*  458 */       ns.outputNodePush();
/*  459 */       writer.write(60);
/*  460 */       writeStringToUtf8(tagName, writer);
/*      */     } else {
/*      */       
/*  463 */       ns.push();
/*      */     } 
/*      */ 
/*      */     
/*  467 */     Iterator<Attr> attrs = handleAttributes(currentElement, ns, currentNodeIsVisible);
/*  468 */     while (attrs.hasNext()) {
/*  469 */       Attr attr = attrs.next();
/*  470 */       outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer);
/*      */     } 
/*      */     
/*  473 */     if (currentNodeIsVisible) {
/*  474 */       writer.write(62);
/*      */     }
/*      */ 
/*      */     
/*  478 */     Node node1 = currentNode.getFirstChild();
/*  479 */     for (; node1 != null; 
/*  480 */       node1 = node1.getNextSibling()) {
/*  481 */       canonicalizeXPathNodeSet(node1, ns);
/*      */     }
/*      */     
/*  484 */     if (currentNodeIsVisible) {
/*  485 */       writer.write(_END_TAG);
/*  486 */       writeStringToUtf8(tagName, writer);
/*      */       
/*  488 */       writer.write(62);
/*  489 */       ns.outputNodePop();
/*      */     } 
/*  491 */     ns.pop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void getParentNameSpaces(Element el, NameSpaceSymbTable ns) {
/*  504 */     List<Element> parents = new ArrayList();
/*  505 */     Node n1 = el.getParentNode();
/*  506 */     if (!(n1 instanceof Element)) {
/*      */       return;
/*      */     }
/*      */     
/*  510 */     Element parent = (Element)el.getParentNode();
/*  511 */     while (parent != null) {
/*  512 */       parents.add(parent);
/*  513 */       Node n = parent.getParentNode();
/*  514 */       if (!(n instanceof Element)) {
/*      */         break;
/*      */       }
/*  517 */       parent = (Element)n;
/*      */     } 
/*      */     
/*  520 */     ListIterator<Element> it = parents.listIterator(parents.size());
/*  521 */     while (it.hasPrevious()) {
/*  522 */       Element ele = it.previous();
/*  523 */       if (!ele.hasAttributes()) {
/*      */         continue;
/*      */       }
/*  526 */       NamedNodeMap attrs = ele.getAttributes();
/*  527 */       int attrsLength = attrs.getLength();
/*  528 */       for (int i = 0; i < attrsLength; i++) {
/*  529 */         Attr N = (Attr)attrs.item(i);
/*  530 */         if ("http://www.w3.org/2000/xmlns/".equals(N.getNamespaceURI())) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  535 */           String NName = N.getLocalName();
/*  536 */           String NValue = N.getNodeValue();
/*  537 */           if (!"xml".equals(NName) || !"http://www.w3.org/XML/1998/namespace".equals(NValue))
/*      */           {
/*      */ 
/*      */             
/*  541 */             ns.addMapping(NName, NValue, N); } 
/*      */         } 
/*      */       } 
/*      */     }  Attr nsprefix;
/*  545 */     if ((nsprefix = ns.getMappingWithoutRendered("xmlns")) != null && "".equals(nsprefix.getValue()))
/*      */     {
/*  547 */       ns.addMappingAndRender("xmlns", "", nullNode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void outputAttrToWriter(String name, String value, OutputStream writer) throws IOException {
/*  570 */     writer.write(32);
/*  571 */     writeStringToUtf8(name, writer);
/*  572 */     writer.write(equalsStr);
/*      */     
/*  574 */     int length = value.length();
/*  575 */     int i = 0; while (true) { if (i < length)
/*  576 */       { byte[] toWrite; char c = value.charAt(i);
/*      */         
/*  578 */         switch (c) {
/*      */           
/*      */           case '&':
/*  581 */             toWrite = _AMP_;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '<':
/*  586 */             toWrite = _LT_;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/*  591 */             toWrite = _QUOT_;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\t':
/*  596 */             toWrite = __X9_;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\n':
/*  601 */             toWrite = __XA_;
/*      */             break;
/*      */ 
/*      */           
/*      */           case '\r':
/*  606 */             toWrite = __XD_;
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/*  611 */             writeCharToUtf8(c, writer);
/*      */             i++;
/*      */             continue;
/*      */         } 
/*  615 */         writer.write(toWrite); }
/*      */       else { break; }
/*      */        i++; }
/*  618 */      writer.write(34);
/*      */   }
/*      */ 
/*      */   
/*      */   static final void writeCharToUtf8(char c, OutputStream out) throws IOException {
/*  623 */     if (c <= '') {
/*  624 */       out.write(c); return;
/*      */     } 
/*  626 */     if (c > '߿') {
/*  627 */       char c1 = (char)(c >>> 12);
/*  628 */       if (c1 > '\000') {
/*  629 */         out.write(0xE0 | c1 & 0xF);
/*      */       } else {
/*  631 */         out.write(224);
/*      */       } 
/*  633 */       out.write(0x80 | c >>> 6 & 0x3F);
/*  634 */       out.write(0x80 | c & 0x3F);
/*      */       
/*      */       return;
/*      */     } 
/*  638 */     char ch = (char)(c >>> 6);
/*  639 */     if (ch > '\000') {
/*  640 */       out.write(0xC0 | ch & 0x1F);
/*      */     } else {
/*  642 */       out.write(192);
/*      */     } 
/*  644 */     out.write(0x80 | c & 0x3F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void writeStringToUtf8(String str, OutputStream out) throws IOException {
/*  651 */     int length = str.length();
/*  652 */     int i = 0;
/*      */     
/*  654 */     while (i < length) {
/*  655 */       char c = str.charAt(i++);
/*  656 */       if (c <= '') {
/*  657 */         out.write(c); continue;
/*      */       } 
/*  659 */       if (c > '߿') {
/*  660 */         char c1 = (char)(c >>> 12);
/*  661 */         if (c1 > '\000') {
/*  662 */           out.write(0xE0 | c1 & 0xF);
/*      */         } else {
/*  664 */           out.write(224);
/*      */         } 
/*  666 */         out.write(0x80 | c >>> 6 & 0x3F);
/*  667 */         out.write(0x80 | c & 0x3F);
/*      */         continue;
/*      */       } 
/*  670 */       char ch = (char)(c >>> 6);
/*  671 */       if (ch > '\000') {
/*  672 */         out.write(0xC0 | ch & 0x1F);
/*      */       } else {
/*  674 */         out.write(192);
/*      */       } 
/*  676 */       out.write(0x80 | c & 0x3F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void outputPItoWriter(ProcessingInstruction currentPI, OutputStream writer) throws IOException {
/*  690 */     int position = getPositionRelativeToDocumentElement(currentPI);
/*      */     
/*  692 */     if (position == 1) {
/*  693 */       writer.write(10);
/*      */     }
/*  695 */     writer.write(_BEGIN_PI);
/*      */     
/*  697 */     String target = currentPI.getTarget();
/*  698 */     int length = target.length();
/*      */     
/*  700 */     for (int i = 0; i < length; i++) {
/*  701 */       char c = target.charAt(i);
/*  702 */       if (c == '\r') {
/*  703 */         writer.write(__XD_);
/*      */       } else {
/*  705 */         writeCharToUtf8(c, writer);
/*      */       } 
/*      */     } 
/*      */     
/*  709 */     String data = currentPI.getData();
/*      */     
/*  711 */     length = data.length();
/*      */     
/*  713 */     if (length > 0) {
/*  714 */       writer.write(32);
/*      */       
/*  716 */       for (int j = 0; j < length; j++) {
/*  717 */         char c = data.charAt(j);
/*  718 */         if (c == '\r') {
/*  719 */           writer.write(__XD_);
/*      */         } else {
/*  721 */           writeCharToUtf8(c, writer);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  726 */     writer.write(_END_PI);
/*  727 */     if (position == -1) {
/*  728 */       writer.write(10);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void outputCommentToWriter(Comment currentComment, OutputStream writer) throws IOException {
/*  740 */     int position = getPositionRelativeToDocumentElement(currentComment);
/*  741 */     if (position == 1) {
/*  742 */       writer.write(10);
/*      */     }
/*  744 */     writer.write(_BEGIN_COMM);
/*      */     
/*  746 */     String data = currentComment.getData();
/*  747 */     int length = data.length();
/*      */     
/*  749 */     for (int i = 0; i < length; i++) {
/*  750 */       char c = data.charAt(i);
/*  751 */       if (c == '\r') {
/*  752 */         writer.write(__XD_);
/*      */       } else {
/*  754 */         writeCharToUtf8(c, writer);
/*      */       } 
/*      */     } 
/*      */     
/*  758 */     writer.write(_END_COMM);
/*  759 */     if (position == -1) {
/*  760 */       writer.write(10);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void outputTextToWriter(String text, OutputStream writer) throws IOException {
/*  772 */     outputTextToWriter(text, writer, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void outputTextToWriter(String text, OutputStream writer, boolean skipWhiteSpace) throws IOException {
/*  784 */     int length = text.length();
/*      */     
/*  786 */     for (int i = 0; i < length; i++) {
/*  787 */       char c = text.charAt(i);
/*      */       
/*  789 */       switch (c) {
/*      */         
/*      */         case '&':
/*  792 */           writer.write(_AMP_);
/*      */           break;
/*      */         
/*      */         case '<':
/*  796 */           writer.write(_LT_);
/*      */           break;
/*      */         
/*      */         case '>':
/*  800 */           writer.write(_GT_);
/*      */           break;
/*      */         
/*      */         case '\r':
/*  804 */           if (!skipWhiteSpace) {
/*  805 */             writer.write(__XD_);
/*      */           }
/*      */           break;
/*      */         case '\n':
/*  809 */           if (!skipWhiteSpace) {
/*  810 */             writeCharToUtf8(c, writer);
/*      */           }
/*      */           break;
/*      */         case '\t':
/*  814 */           if (!skipWhiteSpace) {
/*  815 */             writeCharToUtf8(c, writer);
/*      */           }
/*      */           break;
/*      */         case ' ':
/*  819 */           if (!skipWhiteSpace) {
/*  820 */             writeCharToUtf8(c, writer);
/*      */           }
/*      */           break;
/*      */         default:
/*  824 */           writeCharToUtf8(c, writer);
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean is_includeComments() {
/*  835 */     return this._includeComments;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set_includeComments(boolean comments) {
/*  841 */     this._includeComments = comments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWriter(OutputStream _writer) {
/*  848 */     this._writer = _writer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Node deReference(final Node node, XMLCryptoContext context) throws URIReferenceException {
/*  855 */     URIDereferencer dereferencer = context.getURIDereferencer();
/*      */ 
/*      */     
/*  858 */     DOMURIReference domReference = new DOMURIReference() {
/*      */         public Node getHere() {
/*  860 */           return node;
/*      */         }
/*      */         public String getURI() {
/*  863 */           return null;
/*      */         }
/*      */         public String getType() {
/*  866 */           return null;
/*      */         }
/*      */       };
/*  869 */     Data data = dereferencer.dereference(domReference, context);
/*      */     
/*  871 */     Iterator<Node> nodeIterator = ((NodeSetData)data).iterator();
/*  872 */     if (nodeIterator.hasNext()) {
/*  873 */       return nodeIterator.next();
/*      */     }
/*  875 */     throw new URIReferenceException("URI " + ((Element)node).getAttribute("URI") + "not found");
/*      */   }
/*      */   
/*      */   void removeNodes(Node rootNode, Set result) {
/*      */     try {
/*      */       Node r;
/*  881 */       switch (rootNode.getNodeType()) {
/*      */         case 1:
/*  883 */           result.remove(rootNode);
/*      */         
/*      */         case 9:
/*  886 */           for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/*  887 */             removeNodes(r, result);
/*      */           }
/*      */           return;
/*      */       } 
/*  891 */       result.remove(rootNode);
/*      */     }
/*  893 */     catch (Exception ex) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void printBinaryToken(Node currentNode, NameSpaceSymbTable ns) throws CanonicalizationException, IOException, URIReferenceException {
/*      */     Node currentChild;
/*  902 */     if (currentNode == null)
/*      */       return; 
/*  904 */     boolean currentNodeIsVisible = this._xpathNodeSet.contains(currentNode);
/*  905 */     if (!currentNodeIsVisible) {
/*  906 */       currentNodeIsVisible = this.tokenSet.contains(currentNode);
/*      */     }
/*  908 */     switch (currentNode.getNodeType()) {
/*      */       default:
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*      */       case 6:
/*      */       case 11:
/*      */       case 12:
/*  918 */         throw new CanonicalizationException("empty");
/*      */       case 9:
/*  920 */         currentChild = currentNode.getFirstChild();
/*  921 */         for (; currentChild != null; 
/*  922 */           currentChild = currentChild.getNextSibling()) {
/*  923 */           printBinaryToken(currentChild, ns);
/*      */         }
/*      */ 
/*      */       
/*      */       case 8:
/*  928 */         if (currentNodeIsVisible && this._includeComments) {
/*  929 */           outputCommentToWriter((Comment)currentNode, this._writer);
/*      */         }
/*      */ 
/*      */       
/*      */       case 7:
/*  934 */         if (currentNodeIsVisible) {
/*  935 */           outputPItoWriter((ProcessingInstruction)currentNode, this._writer);
/*      */         }
/*      */ 
/*      */       
/*      */       case 3:
/*  940 */         if (currentNodeIsVisible && currentNode.getParentNode().getLocalName().equals("BinarySecurityToken") && currentNode.getParentNode().getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) {
/*      */           
/*  942 */           NamedNodeMap nmap = currentNode.getParentNode().getAttributes();
/*  943 */           Node node = nmap.getNamedItemNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*  944 */           if (node == null) {
/*      */             
/*  946 */             outputTextToWriter(currentNode.getNodeValue(), this._writer, true);
/*      */           } else {
/*  948 */             outputTextToWriter(currentNode.getNodeValue(), this._writer, false);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  955 */         if (currentNodeIsVisible) {
/*  956 */           outputTextToWriter(currentNode.getNodeValue(), this._writer);
/*      */           
/*  958 */           Node nextSibling = currentNode.getNextSibling();
/*      */           
/*  960 */           for (; nextSibling != null && (nextSibling.getNodeType() == 3 || nextSibling.getNodeType() == 4); 
/*      */ 
/*      */             
/*  963 */             nextSibling = nextSibling.getNextSibling())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  970 */             outputTextToWriter(nextSibling.getNodeValue(), this._writer);
/*      */           }
/*      */         } 
/*      */       case 1:
/*      */         break;
/*      */     } 
/*  976 */     String localName = currentNode.getLocalName();
/*  977 */     Element currentElement = null;
/*  978 */     currentElement = (Element)currentNode;
/*      */     
/*  980 */     OutputStream writer = this._writer;
/*  981 */     String tagName = currentElement.getTagName();
/*  982 */     if (currentNodeIsVisible) {
/*      */       
/*  984 */       ns.outputNodePush();
/*  985 */       writer.write(60);
/*  986 */       writeStringToUtf8(tagName, writer);
/*      */     } else {
/*      */       
/*  989 */       ns.push();
/*      */     } 
/*      */ 
/*      */     
/*  993 */     Attr defNS = currentElement.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/*  994 */     Iterator<Attr> attrs = handleAttributes(currentElement, ns, currentNodeIsVisible);
/*  995 */     if (defNS != null)
/*  996 */       outputAttrToWriter(defNS.getNodeName(), defNS.getNodeValue(), writer); 
/*  997 */     while (attrs.hasNext()) {
/*  998 */       Attr attr = attrs.next();
/*  999 */       outputAttrToWriter(attr.getNodeName(), attr.getNodeValue(), writer);
/*      */     } 
/*      */     
/* 1002 */     if (currentNodeIsVisible) {
/* 1003 */       writer.write(62);
/*      */     }
/*      */ 
/*      */     
/* 1007 */     Node node1 = currentNode.getFirstChild();
/* 1008 */     for (; node1 != null; 
/* 1009 */       node1 = node1.getNextSibling()) {
/* 1010 */       printBinaryToken(node1, ns);
/*      */     }
/*      */     
/* 1013 */     if (currentNodeIsVisible) {
/* 1014 */       writer.write(_END_TAG);
/* 1015 */       writeStringToUtf8(tagName, writer);
/*      */       
/* 1017 */       writer.write(62);
/* 1018 */       ns.outputNodePop();
/*      */     } 
/* 1020 */     ns.pop();
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\Canonicalizer20010315ExclOmitComments.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */