/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JaxRpcMappingNode
/*     */ {
/*     */   private static final String QNAME_SEPARATOR = ":";
/*  35 */   private static String DEBUG = System.getProperty("com.sun.xml.rpc.j2ee.debug");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Document getOwnerDocument(Node node) {
/*  45 */     if (node instanceof Document) {
/*  46 */       return (Document)node;
/*     */     }
/*  48 */     return node.getOwnerDocument();
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
/*     */   public static Element appendChild(Node parent, String elementName) {
/*  60 */     Element child = getOwnerDocument(parent).createElementNS("http://java.sun.com/xml/ns/j2ee", elementName);
/*     */ 
/*     */ 
/*     */     
/*  64 */     parent.appendChild(child);
/*  65 */     return child;
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
/*     */   public static Node appendTextChild(Node parent, String elementName, String text) {
/*  82 */     if (text == null || text.length() == 0) {
/*  83 */       return null;
/*     */     }
/*  85 */     Node child = appendChild(parent, elementName);
/*  86 */     child.appendChild(getOwnerDocument(child).createTextNode(text));
/*  87 */     return child;
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
/*     */   public static Node appendTextChild(Node parent, String elementName, int value) {
/* 103 */     return appendTextChild(parent, elementName, String.valueOf(value));
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
/*     */   public static Node forceAppendTextChild(Node parent, String elementName, String text) {
/* 120 */     Node child = appendChild(parent, elementName);
/* 121 */     if (text != null && text.length() != 0) {
/* 122 */       child.appendChild(getOwnerDocument(child).createTextNode(text));
/*     */     }
/* 124 */     return child;
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
/*     */   public static void setAttribute(Element parent, String elementName, String text) {
/* 141 */     if (text == null || text.length() == 0)
/*     */       return; 
/* 143 */     parent.setAttributeNS("http://java.sun.com/xml/ns/j2ee", elementName, text);
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
/*     */   public static void setAttributeNS(Element element, String prefix, String namespaceURI) {
/* 160 */     String nsPrefix = prefix.equals("") ? "xmlns" : ("xmlns:" + prefix);
/*     */ 
/*     */     
/* 163 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", nsPrefix, namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void debug(String className, String msg) {
/* 171 */     if (DEBUG != null)
/* 172 */       System.out.println("[" + className + "] --> " + msg); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\JaxRpcMappingNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */