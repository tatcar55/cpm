/*     */ package com.sun.xml.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAX2SAXReader
/*     */ {
/*     */   ContentHandler _handler;
/*     */   LexicalHandler _lexicalHandler;
/*     */   XMLStreamReader _reader;
/*     */   
/*     */   public StAX2SAXReader(XMLStreamReader reader, ContentHandler handler) {
/*  48 */     this._handler = handler;
/*  49 */     this._reader = reader;
/*     */   }
/*     */   
/*     */   public StAX2SAXReader(XMLStreamReader reader) {
/*  53 */     this._reader = reader;
/*     */   }
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) {
/*  57 */     this._handler = handler;
/*     */   }
/*     */   
/*     */   public void setLexicalHandler(LexicalHandler lexicalHandler) {
/*  61 */     this._lexicalHandler = lexicalHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void adapt() throws XMLStreamException, SAXException {
/*  67 */     AttributesImpl attrs = new AttributesImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     this._handler.startDocument();
/*     */ 
/*     */     
/*     */     try {
/*  76 */       while (this._reader.hasNext()) {
/*  77 */         QName qname; String prefix, localPart; int nsc, nat, i, event = this._reader.next();
/*     */ 
/*     */         
/*  80 */         switch (event) {
/*     */           
/*     */           case 1:
/*  83 */             nsc = this._reader.getNamespaceCount();
/*  84 */             for (i = 0; i < nsc; i++) {
/*  85 */               this._handler.startPrefixMapping(this._reader.getNamespacePrefix(i), this._reader.getNamespaceURI(i));
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*  90 */             attrs.clear();
/*  91 */             nat = this._reader.getAttributeCount();
/*  92 */             for (i = 0; i < nat; i++) {
/*  93 */               QName q = this._reader.getAttributeName(i);
/*  94 */               String qName = this._reader.getAttributePrefix(i);
/*  95 */               if (qName == null || qName == "") {
/*  96 */                 qName = q.getLocalPart();
/*     */               } else {
/*  98 */                 qName = qName + ":" + q.getLocalPart();
/*     */               } 
/* 100 */               attrs.addAttribute(this._reader.getAttributeNamespace(i), q.getLocalPart(), qName, this._reader.getAttributeType(i), this._reader.getAttributeValue(i));
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 108 */             qname = this._reader.getName();
/* 109 */             prefix = qname.getPrefix();
/* 110 */             localPart = qname.getLocalPart();
/*     */             
/* 112 */             this._handler.startElement(this._reader.getNamespaceURI(), localPart, (prefix.length() > 0) ? (prefix + ":" + localPart) : localPart, attrs);
/*     */             continue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 2:
/* 121 */             qname = this._reader.getName();
/* 122 */             prefix = qname.getPrefix();
/* 123 */             localPart = qname.getLocalPart();
/*     */             
/* 125 */             this._handler.endElement(this._reader.getNamespaceURI(), localPart, (prefix.length() > 0) ? (prefix + ":" + localPart) : localPart);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 131 */             nsc = this._reader.getNamespaceCount();
/* 132 */             for (i = 0; i < nsc; i++) {
/* 133 */               this._handler.endPrefixMapping(this._reader.getNamespacePrefix(i));
/*     */             }
/*     */             continue;
/*     */           
/*     */           case 4:
/* 138 */             this._handler.characters(this._reader.getTextCharacters(), this._reader.getTextStart(), this._reader.getTextLength());
/*     */             continue;
/*     */           case 5:
/* 141 */             this._lexicalHandler.comment(this._reader.getTextCharacters(), this._reader.getTextStart(), this._reader.getTextLength());
/*     */             continue;
/*     */           case 3:
/* 144 */             this._handler.processingInstruction(this._reader.getPITarget(), this._reader.getPIData());
/*     */             continue;
/*     */           case 8:
/*     */             continue;
/*     */         } 
/* 149 */         throw new RuntimeException(CommonResourceBundle.getInstance().getString("message.StAX2SAXReader", new Object[] { Integer.valueOf(event) }));
/*     */       }
/*     */     
/*     */     }
/* 153 */     catch (XMLStreamException e) {
/* 154 */       this._handler.endDocument();
/* 155 */       throw e;
/*     */     } 
/*     */     
/* 158 */     this._handler.endDocument();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\StAX2SAXReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */