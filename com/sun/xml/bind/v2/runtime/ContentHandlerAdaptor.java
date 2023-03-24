/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.istack.SAXException2;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ContentHandlerAdaptor
/*     */   extends DefaultHandler
/*     */ {
/*  64 */   private final FinalArrayList<String> prefixMap = new FinalArrayList();
/*     */ 
/*     */   
/*     */   private final XMLSerializer serializer;
/*     */   
/*  69 */   private final StringBuffer text = new StringBuffer();
/*     */ 
/*     */   
/*     */   ContentHandlerAdaptor(XMLSerializer _serializer) {
/*  73 */     this.serializer = _serializer;
/*     */   }
/*     */   
/*     */   public void startDocument() {
/*  77 */     this.prefixMap.clear();
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) {
/*  81 */     this.prefixMap.add(prefix);
/*  82 */     this.prefixMap.add(uri);
/*     */   }
/*     */   
/*     */   private boolean containsPrefixMapping(String prefix, String uri) {
/*  86 */     for (int i = 0; i < this.prefixMap.size(); i += 2) {
/*  87 */       if (((String)this.prefixMap.get(i)).equals(prefix) && ((String)this.prefixMap.get(i + 1)).equals(uri))
/*     */       {
/*  89 */         return true; } 
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*     */     try {
/*  97 */       flushText();
/*     */       
/*  99 */       int len = atts.getLength();
/*     */       
/* 101 */       String p = getPrefix(qName);
/*     */ 
/*     */       
/* 104 */       if (containsPrefixMapping(p, namespaceURI)) {
/* 105 */         this.serializer.startElementForce(namespaceURI, localName, p, null);
/*     */       } else {
/* 107 */         this.serializer.startElement(namespaceURI, localName, p, null);
/*     */       } 
/*     */       int i;
/* 110 */       for (i = 0; i < this.prefixMap.size(); i += 2)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 115 */         this.serializer.getNamespaceContext().force((String)this.prefixMap.get(i + 1), (String)this.prefixMap.get(i));
/*     */       }
/*     */ 
/*     */       
/* 119 */       for (i = 0; i < len; i++) {
/* 120 */         String qname = atts.getQName(i);
/* 121 */         if (!qname.startsWith("xmlns") && atts.getURI(i).length() != 0) {
/*     */           
/* 123 */           String prefix = getPrefix(qname);
/*     */           
/* 125 */           this.serializer.getNamespaceContext().declareNamespace(atts.getURI(i), prefix, true);
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       this.serializer.endNamespaceDecls(null);
/*     */       
/* 131 */       for (i = 0; i < len; i++) {
/*     */         
/* 133 */         if (!atts.getQName(i).startsWith("xmlns"))
/*     */         {
/* 135 */           this.serializer.attribute(atts.getURI(i), atts.getLocalName(i), atts.getValue(i)); } 
/*     */       } 
/* 137 */       this.prefixMap.clear();
/* 138 */       this.serializer.endAttributes();
/* 139 */     } catch (IOException e) {
/* 140 */       throw new SAXException2(e);
/* 141 */     } catch (XMLStreamException e) {
/* 142 */       throw new SAXException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getPrefix(String qname) {
/* 147 */     int idx = qname.indexOf(':');
/* 148 */     String prefix = (idx == -1) ? qname : qname.substring(0, idx);
/* 149 */     return prefix;
/*     */   }
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 154 */       flushText();
/* 155 */       this.serializer.endElement();
/* 156 */     } catch (IOException e) {
/* 157 */       throw new SAXException2(e);
/* 158 */     } catch (XMLStreamException e) {
/* 159 */       throw new SAXException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void flushText() throws SAXException, IOException, XMLStreamException {
/* 164 */     if (this.text.length() != 0) {
/* 165 */       this.serializer.text(this.text.toString(), (String)null);
/* 166 */       this.text.setLength(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/* 171 */     this.text.append(ch, start, length);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\ContentHandlerAdaptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */