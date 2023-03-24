/*     */ package com.sun.xml.txw2;
/*     */ 
/*     */ import com.sun.xml.txw2.annotation.XmlElement;
/*     */ import com.sun.xml.txw2.annotation.XmlNamespace;
/*     */ import com.sun.xml.txw2.output.TXWSerializer;
/*     */ import com.sun.xml.txw2.output.XmlSerializer;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TXW
/*     */ {
/*     */   static QName getTagName(Class<?> c) {
/*  60 */     String localName = "";
/*  61 */     String nsUri = "##default";
/*     */     
/*  63 */     XmlElement xe = c.<XmlElement>getAnnotation(XmlElement.class);
/*  64 */     if (xe != null) {
/*  65 */       localName = xe.value();
/*  66 */       nsUri = xe.ns();
/*     */     } 
/*     */     
/*  69 */     if (localName.length() == 0) {
/*  70 */       localName = c.getName();
/*  71 */       int idx = localName.lastIndexOf('.');
/*  72 */       if (idx >= 0) {
/*  73 */         localName = localName.substring(idx + 1);
/*     */       }
/*  75 */       localName = Character.toLowerCase(localName.charAt(0)) + localName.substring(1);
/*     */     } 
/*     */     
/*  78 */     if (nsUri.equals("##default")) {
/*  79 */       Package pkg = c.getPackage();
/*  80 */       if (pkg != null) {
/*  81 */         XmlNamespace xn = pkg.<XmlNamespace>getAnnotation(XmlNamespace.class);
/*  82 */         if (xn != null)
/*  83 */           nsUri = xn.value(); 
/*     */       } 
/*     */     } 
/*  86 */     if (nsUri.equals("##default")) {
/*  87 */       nsUri = "";
/*     */     }
/*  89 */     return new QName(nsUri, localName);
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
/*     */   public static <T extends TypedXmlWriter> T create(Class<T> rootElement, XmlSerializer out) {
/* 103 */     if (out instanceof TXWSerializer) {
/* 104 */       TXWSerializer txws = (TXWSerializer)out;
/* 105 */       return txws.txw._element(rootElement);
/*     */     } 
/*     */     
/* 108 */     Document doc = new Document(out);
/* 109 */     QName n = getTagName(rootElement);
/* 110 */     return (new ContainerElement(doc, null, n.getNamespaceURI(), n.getLocalPart()))._cast(rootElement);
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
/*     */   public static <T extends TypedXmlWriter> T create(QName tagName, Class<T> rootElement, XmlSerializer out) {
/* 126 */     if (out instanceof TXWSerializer) {
/* 127 */       TXWSerializer txws = (TXWSerializer)out;
/* 128 */       return txws.txw._element(tagName, rootElement);
/*     */     } 
/* 130 */     return (new ContainerElement(new Document(out), null, tagName.getNamespaceURI(), tagName.getLocalPart()))._cast(rootElement);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\TXW.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */