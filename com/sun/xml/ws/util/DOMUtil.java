/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
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
/*  78 */     synchronized (DOMUtil.class) {
/*  79 */       if (db == null) {
/*     */         try {
/*  81 */           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  82 */           dbf.setNamespaceAware(true);
/*  83 */           db = dbf.newDocumentBuilder();
/*  84 */         } catch (ParserConfigurationException e) {
/*  85 */           throw new FactoryConfigurationError(e);
/*     */         } 
/*     */       }
/*  88 */       return db.newDocument();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Node createDOMNode(InputStream inputStream) {
/*  94 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  95 */     dbf.setNamespaceAware(true);
/*  96 */     dbf.setValidating(false);
/*     */     try {
/*  98 */       DocumentBuilder builder = dbf.newDocumentBuilder();
/*     */       try {
/* 100 */         return builder.parse(inputStream);
/* 101 */       } catch (SAXException e) {
/* 102 */         e.printStackTrace();
/* 103 */       } catch (IOException e) {
/* 104 */         e.printStackTrace();
/*     */       } 
/* 106 */     } catch (ParserConfigurationException pce) {
/* 107 */       throw new IllegalArgumentException(pce);
/*     */     } 
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serializeNode(Element node, XMLStreamWriter writer) throws XMLStreamException {
/* 119 */     writeTagWithAttributes(node, writer);
/*     */     
/* 121 */     if (node.hasChildNodes()) {
/* 122 */       NodeList children = node.getChildNodes();
/* 123 */       for (int i = 0; i < children.getLength(); i++) {
/* 124 */         Node child = children.item(i);
/* 125 */         switch (child.getNodeType()) {
/*     */           case 7:
/* 127 */             writer.writeProcessingInstruction(child.getNodeValue());
/*     */             break;
/*     */ 
/*     */           
/*     */           case 4:
/* 132 */             writer.writeCData(child.getNodeValue());
/*     */             break;
/*     */           case 8:
/* 135 */             writer.writeComment(child.getNodeValue());
/*     */             break;
/*     */           case 3:
/* 138 */             writer.writeCharacters(child.getNodeValue());
/*     */             break;
/*     */           case 1:
/* 141 */             serializeNode((Element)child, writer);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 147 */     writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public static void writeTagWithAttributes(Element node, XMLStreamWriter writer) throws XMLStreamException {
/* 151 */     String nodePrefix = fixNull(node.getPrefix());
/* 152 */     String nodeNS = fixNull(node.getNamespaceURI());
/*     */     
/* 154 */     String nodeLocalName = (node.getLocalName() == null) ? node.getNodeName() : node.getLocalName();
/*     */ 
/*     */ 
/*     */     
/* 158 */     boolean prefixDecl = isPrefixDeclared(writer, nodeNS, nodePrefix);
/* 159 */     writer.writeStartElement(nodePrefix, nodeLocalName, nodeNS);
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
/* 214 */           writer.writeAttribute(attrPrefix, attrNS, localName, attr.getNodeValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isPrefixDeclared(XMLStreamWriter writer, String nsUri, String prefix) {
/* 221 */     boolean prefixDecl = false;
/* 222 */     NamespaceContext nscontext = writer.getNamespaceContext();
/* 223 */     Iterator prefixItr = nscontext.getPrefixes(nsUri);
/* 224 */     while (prefixItr.hasNext()) {
/* 225 */       if (prefix.equals(prefixItr.next())) {
/* 226 */         prefixDecl = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 230 */     return prefixDecl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getFirstChild(Element e, String nsUri, String local) {
/* 237 */     for (Node n = e.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 238 */       if (n.getNodeType() == 1) {
/* 239 */         Element c = (Element)n;
/* 240 */         if (c.getLocalName().equals(local) && c.getNamespaceURI().equals(nsUri)) {
/* 241 */           return c;
/*     */         }
/*     */       } 
/*     */     } 
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String fixNull(@Nullable String s) {
/* 251 */     if (s == null) {
/* 252 */       return "";
/*     */     }
/* 254 */     return s;
/*     */   }
/*     */ 
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\DOMUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */