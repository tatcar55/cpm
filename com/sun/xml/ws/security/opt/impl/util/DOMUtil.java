/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.FactoryConfigurationError;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class DOMUtil
/*     */ {
/*     */   private static DocumentBuilder db;
/*     */   
/*     */   public static Document createDom() {
/*  79 */     synchronized (DOMUtil.class) {
/*  80 */       if (db == null) {
/*     */         try {
/*  82 */           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  83 */           dbf.setNamespaceAware(true);
/*  84 */           db = dbf.newDocumentBuilder();
/*  85 */         } catch (ParserConfigurationException e) {
/*  86 */           throw new FactoryConfigurationError(e);
/*     */         } 
/*     */       }
/*  89 */       return db.newDocument();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Node createDOMNode(InputStream inputStream) {
/*  95 */     DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*  96 */     dbf.setNamespaceAware(true);
/*  97 */     dbf.setValidating(false);
/*     */     try {
/*  99 */       DocumentBuilder builder = dbf.newDocumentBuilder();
/*     */       try {
/* 101 */         return builder.parse(inputStream);
/* 102 */       } catch (SAXException e) {
/* 103 */         e.printStackTrace();
/* 104 */       } catch (IOException e) {
/* 105 */         e.printStackTrace();
/*     */       } 
/* 107 */     } catch (ParserConfigurationException pce) {
/* 108 */       IllegalArgumentException iae = new IllegalArgumentException(pce.getMessage());
/* 109 */       iae.initCause(pce);
/* 110 */       throw iae;
/*     */     } 
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serializeNode(Element node, XMLStreamWriter writer) throws XMLStreamException {
/* 122 */     writeTagWithAttributes(node, writer);
/*     */     
/* 124 */     if (node.hasChildNodes()) {
/* 125 */       NodeList children = node.getChildNodes();
/* 126 */       for (int i = 0; i < children.getLength(); i++) {
/* 127 */         Node child = children.item(i);
/* 128 */         switch (child.getNodeType()) {
/*     */           case 7:
/* 130 */             writer.writeProcessingInstruction(child.getNodeValue());
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/* 135 */             writer.writeCData(child.getNodeValue());
/*     */             break;
/*     */           case 8:
/* 138 */             writer.writeComment(child.getNodeValue());
/*     */             break;
/*     */           case 3:
/* 141 */             writer.writeCharacters(child.getNodeValue());
/*     */             break;
/*     */           case 1:
/* 144 */             serializeNode((Element)child, writer);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 149 */     writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public static void writeTagWithAttributes(Element node, XMLStreamWriter writer) throws XMLStreamException {
/* 153 */     String nodePrefix = fixNull(node.getPrefix());
/* 154 */     String nodeNS = fixNull(node.getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/* 158 */     boolean prefixDecl = isPrefixDeclared(writer, nodeNS, nodePrefix);
/* 159 */     writer.writeStartElement(nodePrefix, node.getLocalName(), nodeNS);
/*     */     
/* 161 */     if (node.hasAttributes()) {
/* 162 */       NamedNodeMap attrs = node.getAttributes();
/* 163 */       int numOfAttributes = attrs.getLength();
/*     */ 
/*     */ 
/*     */       
/* 167 */       for (int i = 0; i < numOfAttributes; i++) {
/* 168 */         Node attr = attrs.item(i);
/* 169 */         String nsUri = fixNull(attr.getNamespaceURI());
/* 170 */         if (nsUri.equals("http://www.w3.org/2000/xmlns/")) {
/*     */           
/* 172 */           String local = attr.getLocalName().equals("xmlns") ? "" : attr.getLocalName();
/* 173 */           if (local.equals(nodePrefix) && attr.getNodeValue().equals(nodeNS)) {
/* 174 */             prefixDecl = true;
/*     */           }
/* 176 */           if (local.equals("")) {
/* 177 */             writer.writeDefaultNamespace(attr.getNodeValue());
/*     */           } else {
/*     */             
/* 180 */             writer.setPrefix(attr.getLocalName(), attr.getNodeValue());
/* 181 */             writer.writeNamespace(attr.getLocalName(), attr.getNodeValue());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     if (!prefixDecl) {
/* 188 */       writer.writeNamespace(nodePrefix, nodeNS);
/*     */     }
/*     */ 
/*     */     
/* 192 */     if (node.hasAttributes()) {
/* 193 */       NamedNodeMap attrs = node.getAttributes();
/* 194 */       int numOfAttributes = attrs.getLength();
/*     */       
/* 196 */       for (int i = 0; i < numOfAttributes; i++) {
/* 197 */         Node attr = attrs.item(i);
/* 198 */         String attrPrefix = fixNull(attr.getPrefix());
/* 199 */         String attrNS = fixNull(attr.getNamespaceURI());
/* 200 */         if (!attrNS.equals("http://www.w3.org/2000/xmlns/")) {
/* 201 */           String localName = attr.getLocalName();
/* 202 */           if (localName == null)
/*     */           {
/*     */             
/* 205 */             localName = attr.getNodeName();
/*     */           }
/* 207 */           boolean attrPrefixDecl = isPrefixDeclared(writer, attrNS, attrPrefix);
/* 208 */           if (!attrPrefix.equals("") && !attrPrefixDecl) {
/*     */ 
/*     */             
/* 211 */             writer.setPrefix(attr.getLocalName(), attr.getNodeValue());
/* 212 */             writer.writeNamespace(attrPrefix, attrNS);
/*     */           } 
/* 214 */           if (attr.getNamespaceURI() != null) {
/* 215 */             writer.writeAttribute(attrPrefix, attrNS, localName, attr.getNodeValue());
/*     */           } else {
/* 217 */             writer.writeAttribute(localName, attr.getNodeValue());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isPrefixDeclared(XMLStreamWriter writer, String nsUri, String prefix) {
/* 225 */     boolean prefixDecl = false;
/* 226 */     NamespaceContext nscontext = writer.getNamespaceContext();
/* 227 */     Iterator prefixItr = nscontext.getPrefixes(nsUri);
/* 228 */     while (prefixItr.hasNext()) {
/* 229 */       if (prefix.equals(prefixItr.next())) {
/* 230 */         prefixDecl = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 234 */     return prefixDecl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChild(Element e, String nsUri, String local) {
/* 241 */     for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 242 */       if (n.getNodeType() == 1) {
/* 243 */         Element c = (Element)n;
/* 244 */         if (c.getLocalName().equals(local) && c.getNamespaceURI().equals(nsUri))
/* 245 */           return c; 
/*     */       } 
/*     */     } 
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String fixNull(@Nullable String s) {
/* 254 */     if (s == null) return ""; 
/* 255 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Element getFirstElementChild(Node parent) {
/* 264 */     for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 265 */       if (n.getNodeType() == 1) {
/* 266 */         return (Element)n;
/*     */       }
/*     */     } 
/* 269 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static List<Element> getChildElements(Node parent) {
/* 274 */     List<Element> elements = new ArrayList<Element>();
/* 275 */     for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 276 */       if (n.getNodeType() == 1) {
/* 277 */         elements.add((Element)n);
/*     */       }
/*     */     } 
/* 280 */     return elements;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\DOMUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */