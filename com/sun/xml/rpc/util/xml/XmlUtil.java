/*     */ package com.sun.xml.rpc.util.xml;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.rpc.util.WSDLParseException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlUtil
/*     */ {
/*     */   public static String getPrefix(String s) {
/*  60 */     int i = s.indexOf(':');
/*  61 */     if (i == -1)
/*  62 */       return null; 
/*  63 */     return s.substring(0, i);
/*     */   }
/*     */   
/*     */   public static String getLocalPart(String s) {
/*  67 */     int i = s.indexOf(':');
/*  68 */     if (i == -1)
/*  69 */       return s; 
/*  70 */     return s.substring(i + 1);
/*     */   }
/*     */   
/*     */   public static String getAttributeOrNull(Element e, String name) {
/*  74 */     Attr a = e.getAttributeNode(name);
/*  75 */     if (a == null)
/*  76 */       return null; 
/*  77 */     return a.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeNSOrNull(Element e, String name, String nsURI) {
/*  84 */     Attr a = e.getAttributeNodeNS(nsURI, name);
/*  85 */     if (a == null)
/*  86 */       return null; 
/*  87 */     return a.getValue();
/*     */   }
/*     */   
/*     */   public static boolean matchesTagNS(Element e, String tag, String nsURI) {
/*     */     try {
/*  92 */       return (e.getLocalName().equals(tag) && e.getNamespaceURI().equals(nsURI));
/*     */     }
/*  94 */     catch (NullPointerException npe) {
/*     */ 
/*     */       
/*  97 */       throw new WSDLParseException("null.namespace.found", e.getLocalName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesTagNS(Element e, QName name) {
/*     */     try {
/* 107 */       return (e.getLocalName().equals(name.getLocalPart()) && e.getNamespaceURI().equals(name.getNamespaceURI()));
/*     */     }
/* 109 */     catch (NullPointerException npe) {
/*     */ 
/*     */       
/* 112 */       throw new WSDLParseException("null.namespace.found", e.getLocalName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator getAllChildren(Element element) {
/* 119 */     return new NodeListIterator(element.getChildNodes());
/*     */   }
/*     */   
/*     */   public static Iterator getAllAttributes(Element element) {
/* 123 */     return new NamedNodeMapIterator(element.getAttributes());
/*     */   }
/*     */   
/*     */   public static List parseTokenList(String tokenList) {
/* 127 */     List<String> result = new ArrayList();
/* 128 */     StringTokenizer tokenizer = new StringTokenizer(tokenList, " ");
/* 129 */     while (tokenizer.hasMoreTokens()) {
/* 130 */       result.add(tokenizer.nextToken());
/*     */     }
/* 132 */     return result;
/*     */   }
/*     */   
/*     */   public static String getTextForNode(Node node) {
/* 136 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 138 */     NodeList children = node.getChildNodes();
/* 139 */     if (children.getLength() == 0) {
/* 140 */       return null;
/*     */     }
/* 142 */     for (int i = 0; i < children.getLength(); i++) {
/* 143 */       Node n = children.item(i);
/*     */       
/* 145 */       if (n instanceof org.w3c.dom.Text) {
/* 146 */         sb.append(n.getNodeValue());
/* 147 */       } else if (n instanceof org.w3c.dom.EntityReference) {
/* 148 */         String s = getTextForNode(n);
/* 149 */         if (s == null) {
/* 150 */           return null;
/*     */         }
/* 152 */         sb.append(s);
/*     */       } else {
/* 154 */         return null;
/*     */       } 
/*     */     } 
/* 157 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static InputStream getUTF8Stream(String s) {
/*     */     try {
/* 162 */       ByteArrayOutputStream bas = new ByteArrayOutputStream();
/* 163 */       Writer w = new OutputStreamWriter(bas, "utf-8");
/* 164 */       w.write(s);
/* 165 */       w.close();
/* 166 */       byte[] ba = bas.toByteArray();
/* 167 */       ByteArrayInputStream bis = new ByteArrayInputStream(ba);
/* 168 */       return bis;
/* 169 */     } catch (IOException e) {
/* 170 */       throw new RuntimeException("should not happen");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ByteInputStream getUTF8ByteInputStream(String s) {
/*     */     try {
/* 176 */       ByteArrayOutputStream bas = new ByteArrayOutputStream();
/* 177 */       Writer w = new OutputStreamWriter(bas, "utf-8");
/* 178 */       w.write(s);
/* 179 */       w.close();
/* 180 */       byte[] ba = bas.toByteArray();
/* 181 */       ByteInputStream bis = new ByteInputStream(ba, ba.length);
/* 182 */       return bis;
/* 183 */     } catch (IOException e) {
/* 184 */       throw new RuntimeException("should not happen");
/*     */     } 
/*     */   }
/*     */   
/* 188 */   static TransformerFactory transformerFactory = null;
/*     */   
/*     */   public static Transformer newTransformer() {
/* 191 */     Transformer t = null;
/*     */     
/* 193 */     if (transformerFactory == null) {
/* 194 */       transformerFactory = TransformerFactory.newInstance();
/*     */     }
/*     */     try {
/* 197 */       t = transformerFactory.newTransformer();
/* 198 */     } catch (TransformerConfigurationException tex) {
/* 199 */       throw new IllegalStateException("Unable to create a JAXP transformer");
/*     */     } 
/* 201 */     return t;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\xml\XmlUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */