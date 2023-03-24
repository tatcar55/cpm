/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.SignedMessageHeader;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.impl.c14n.StAXAttr;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedSignedMessageHeader
/*     */   extends SignedMessageHeader
/*     */ {
/*  70 */   private SecurityHeaderElement encHeader = null;
/*     */   private boolean parsed = false;
/*     */   private String localName;
/*     */   private String uri;
/*     */   private String prefix;
/*  75 */   private Vector attrList = new Vector();
/*  76 */   private Vector attrNSList = new Vector();
/*     */   
/*  78 */   private MutableXMLStreamBuffer buffer = null;
/*     */ 
/*     */   
/*     */   public EncryptedSignedMessageHeader(SignedMessageHeader hdr, SecurityHeaderElement she) {
/*  82 */     super((SecurityHeaderElement)hdr);
/*  83 */     this.encHeader = she;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*  91 */     if (this.buffer == null) {
/*  92 */       this.buffer = new MutableXMLStreamBuffer();
/*  93 */       XMLStreamWriter writer = this.buffer.createFromXMLStreamWriter();
/*  94 */       super.writeTo(writer);
/*     */     } 
/*  96 */     return (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 103 */     if (!this.parsed) {
/* 104 */       parse();
/*     */     }
/* 106 */     writeStartElement(streamWriter);
/* 107 */     ((SecurityElementWriter)this.encHeader).writeTo(streamWriter);
/* 108 */     writeEndElement(streamWriter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap props) throws XMLStreamException {
/* 115 */     if (!this.parsed) {
/* 116 */       parse();
/*     */     }
/* 118 */     writeStartElement(streamWriter);
/* 119 */     ((SecurityElementWriter)this.encHeader).writeTo(streamWriter, props);
/* 120 */     writeEndElement(streamWriter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void parse() throws XMLStreamException {
/* 125 */     XMLStreamReader reader = readHeader();
/* 126 */     this.parsed = true;
/* 127 */     boolean stop = false;
/* 128 */     while (reader.hasNext()) {
/* 129 */       int count, i, eventType = reader.next();
/* 130 */       if (stop) {
/*     */         return;
/*     */       }
/* 133 */       switch (eventType) {
/*     */         case 1:
/* 135 */           this.localName = reader.getLocalName();
/* 136 */           this.uri = reader.getNamespaceURI();
/* 137 */           this.prefix = reader.getPrefix();
/* 138 */           if (this.prefix == null)
/* 139 */             this.prefix = ""; 
/* 140 */           count = reader.getAttributeCount();
/* 141 */           for (i = 0; i < count; i++) {
/* 142 */             String localName = reader.getAttributeLocalName(i);
/* 143 */             String uri = reader.getAttributeNamespace(i);
/* 144 */             String prefix = reader.getAttributePrefix(i);
/* 145 */             if (prefix == null)
/* 146 */               prefix = ""; 
/* 147 */             String value = reader.getAttributeValue(i);
/* 148 */             StAXAttr attr = new StAXAttr();
/* 149 */             attr.setLocalName(localName);
/* 150 */             attr.setValue(value);
/* 151 */             attr.setPrefix(prefix);
/* 152 */             attr.setUri(uri);
/* 153 */             this.attrList.add(attr);
/*     */           } 
/*     */           
/* 156 */           count = 0;
/* 157 */           count = reader.getNamespaceCount();
/* 158 */           for (i = 0; i < count; i++) {
/* 159 */             String prefix = reader.getNamespacePrefix(i);
/* 160 */             if (prefix == null)
/* 161 */               prefix = ""; 
/* 162 */             String uri = reader.getNamespaceURI(i);
/* 163 */             AttributeNS attrNS = new AttributeNS();
/* 164 */             attrNS.setPrefix(prefix);
/* 165 */             attrNS.setUri(uri);
/* 166 */             this.attrNSList.add(attrNS);
/*     */           } 
/* 168 */           stop = true;
/*     */ 
/*     */         
/*     */         case 2:
/* 172 */           stop = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEndElement(XMLStreamWriter xsw) throws XMLStreamException {
/* 181 */     xsw.writeEndElement();
/*     */   }
/*     */   
/*     */   private void writeStartElement(XMLStreamWriter xsw) throws XMLStreamException {
/* 185 */     xsw.writeStartElement(this.prefix, this.localName, this.uri); int i;
/* 186 */     for (i = 0; i < this.attrNSList.size(); i++) {
/* 187 */       AttributeNS attrNs = this.attrNSList.get(i);
/* 188 */       xsw.writeNamespace(attrNs.getPrefix(), attrNs.getUri());
/*     */     } 
/* 190 */     for (i = 0; i < this.attrList.size(); i++) {
/* 191 */       StAXAttr attr = this.attrList.get(i);
/* 192 */       xsw.writeAttribute(attr.getPrefix(), attr.getUri(), attr.getLocalName(), attr.getValue());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\EncryptedSignedMessageHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */