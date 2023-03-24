/*     */ package com.sun.xml.rpc.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.framework.ParseException;
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class Util
/*     */ {
/*     */   public static String getRequiredAttribute(Element element, String name) {
/*  52 */     String result = XmlUtil.getAttributeOrNull(element, name);
/*  53 */     if (result == null) {
/*  54 */       fail("parsing.missingRequiredAttribute", element.getTagName(), name);
/*     */     }
/*     */ 
/*     */     
/*  58 */     return result;
/*     */   }
/*     */   
/*     */   public static void verifyTag(Element element, String tag) {
/*  62 */     if (!element.getLocalName().equals(tag))
/*  63 */       fail("parsing.invalidTag", element.getTagName(), tag); 
/*     */   }
/*     */   
/*     */   public static void verifyTagNS(Element element, String tag, String nsURI) {
/*  67 */     if (!element.getLocalName().equals(tag) || (element.getNamespaceURI() != null && !element.getNamespaceURI().equals(nsURI)))
/*     */     {
/*     */       
/*  70 */       fail("parsing.invalidTagNS", new Object[] { element.getTagName(), element.getNamespaceURI(), tag, nsURI });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void verifyTagNS(Element element, QName name) {
/*  80 */     if (!element.getLocalName().equals(name.getLocalPart()) || (element.getNamespaceURI() != null && !element.getNamespaceURI().equals(name.getNamespaceURI())))
/*     */     {
/*     */       
/*  83 */       fail("parsing.invalidTagNS", new Object[] { element.getTagName(), element.getNamespaceURI(), name.getLocalPart(), name.getNamespaceURI() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void verifyTagNSRootElement(Element element, QName name) {
/*  93 */     if (!element.getLocalName().equals(name.getLocalPart()) || (element.getNamespaceURI() != null && !element.getNamespaceURI().equals(name.getNamespaceURI())))
/*     */     {
/*     */       
/*  96 */       fail("parsing.incorrectRootElement", new Object[] { element.getTagName(), element.getNamespaceURI(), name.getLocalPart(), name.getNamespaceURI() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element nextElementIgnoringCharacterContent(Iterator<Node> iter) {
/* 106 */     while (iter.hasNext()) {
/* 107 */       Node n = iter.next();
/* 108 */       if (n instanceof Text)
/*     */         continue; 
/* 110 */       if (n instanceof org.w3c.dom.Comment)
/*     */         continue; 
/* 112 */       if (!(n instanceof Element))
/* 113 */         fail("parsing.elementExpected"); 
/* 114 */       return (Element)n;
/*     */     } 
/*     */     
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   public static Element nextElement(Iterator<Node> iter) {
/* 121 */     while (iter.hasNext()) {
/* 122 */       Node n = iter.next();
/* 123 */       if (n instanceof Text) {
/* 124 */         Text t = (Text)n;
/* 125 */         if (t.getData().trim().length() == 0)
/*     */           continue; 
/* 127 */         fail("parsing.nonWhitespaceTextFound", t.getData().trim());
/*     */       } 
/* 129 */       if (n instanceof org.w3c.dom.Comment)
/*     */         continue; 
/* 131 */       if (!(n instanceof Element))
/* 132 */         fail("parsing.elementExpected"); 
/* 133 */       return (Element)n;
/*     */     } 
/*     */     
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String processSystemIdWithBase(String baseSystemId, String systemId) {
/*     */     try {
/* 143 */       URL base = null;
/*     */       try {
/* 145 */         base = new URL(baseSystemId);
/* 146 */       } catch (MalformedURLException e) {
/* 147 */         base = (new File(baseSystemId)).toURL();
/*     */       } 
/*     */       
/*     */       try {
/* 151 */         URL url = new URL(base, systemId);
/* 152 */         return url.toString();
/* 153 */       } catch (MalformedURLException e) {
/* 154 */         fail("parsing.invalidURI", systemId);
/*     */       }
/*     */     
/* 157 */     } catch (MalformedURLException e) {
/* 158 */       fail("parsing.invalidURI", baseSystemId);
/*     */     } 
/*     */     
/* 161 */     return null;
/*     */   }
/*     */   
/*     */   public static void fail(String key) {
/* 165 */     throw new ParseException(key);
/*     */   }
/*     */   
/*     */   public static void fail(String key, String arg) {
/* 169 */     throw new ParseException(key, arg);
/*     */   }
/*     */   
/*     */   public static void fail(String key, String arg1, String arg2) {
/* 173 */     throw new ParseException(key, new Object[] { arg1, arg2 });
/*     */   }
/*     */   
/*     */   public static void fail(String key, Object[] args) {
/* 177 */     throw new ParseException(key, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */