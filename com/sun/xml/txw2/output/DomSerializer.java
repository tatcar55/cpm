/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import com.sun.xml.txw2.TxwException;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import org.w3c.dom.Document;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DomSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final SaxSerializer serializer;
/*     */   
/*     */   public DomSerializer(Node node) {
/*  73 */     Dom2SaxAdapter adapter = new Dom2SaxAdapter(node);
/*  74 */     this.serializer = new SaxSerializer(adapter, adapter, false);
/*     */   }
/*     */   
/*     */   public DomSerializer(DOMResult domResult) {
/*  78 */     Node node = domResult.getNode();
/*     */     
/*  80 */     if (node == null) {
/*     */       try {
/*  82 */         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/*  83 */         dbf.setNamespaceAware(true);
/*  84 */         DocumentBuilder db = dbf.newDocumentBuilder();
/*  85 */         Document doc = db.newDocument();
/*  86 */         domResult.setNode(doc);
/*  87 */         this.serializer = new SaxSerializer(new Dom2SaxAdapter(doc), null, false);
/*  88 */       } catch (ParserConfigurationException pce) {
/*  89 */         throw new TxwException(pce);
/*     */       } 
/*     */     } else {
/*  92 */       this.serializer = new SaxSerializer(new Dom2SaxAdapter(node), null, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() {
/*  98 */     this.serializer.startDocument();
/*     */   }
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/* 102 */     this.serializer.beginStartTag(uri, localName, prefix);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/* 106 */     this.serializer.writeAttribute(uri, localName, prefix, value);
/*     */   }
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/* 110 */     this.serializer.writeXmlns(prefix, uri);
/*     */   }
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {
/* 114 */     this.serializer.endStartTag(uri, localName, prefix);
/*     */   }
/*     */   
/*     */   public void endTag() {
/* 118 */     this.serializer.endTag();
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/* 122 */     this.serializer.text(text);
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/* 126 */     this.serializer.cdata(text);
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/* 130 */     this.serializer.comment(comment);
/*     */   }
/*     */   
/*     */   public void endDocument() {
/* 134 */     this.serializer.endDocument();
/*     */   }
/*     */   
/*     */   public void flush() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\DomSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */