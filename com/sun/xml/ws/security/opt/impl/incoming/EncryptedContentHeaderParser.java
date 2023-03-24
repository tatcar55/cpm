/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.impl.c14n.StAXAttr;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
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
/*     */ class EncryptedContentHeaderParser
/*     */ {
/*  67 */   XMLStreamReader encContentReader = null;
/*     */   boolean parsed = false;
/*     */   String localName;
/*     */   String uri;
/*     */   String prefix;
/*  72 */   Vector attrList = new Vector();
/*  73 */   Vector attrNSList = new Vector();
/*  74 */   EncryptedData ed = null;
/*     */   
/*  76 */   private HashMap<String, String> parentNS = null;
/*  77 */   private JAXBFilterProcessingContext context = null;
/*     */ 
/*     */   
/*     */   EncryptedContentHeaderParser(XMLStreamReader encContentReader, HashMap<String, String> parentNS, JAXBFilterProcessingContext context) {
/*  81 */     this.encContentReader = encContentReader;
/*  82 */     this.parentNS = parentNS;
/*  83 */     this.context = context;
/*     */   }
/*     */   
/*     */   XMLStreamReader getDecryptedElement(InputStream decryptedIS) throws XMLStreamException, XWSSecurityException {
/*  87 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  88 */     XMLOutputFactory factory = XMLOutputFactory.newInstance();
/*  89 */     XMLStreamWriter writer = factory.createXMLStreamWriter(out);
/*  90 */     writeStartElement(writer);
/*  91 */     writeEndElement(writer);
/*  92 */     writer.flush();
/*  93 */     writer.close();
/*     */     try {
/*  95 */       out.close();
/*  96 */     } catch (IOException ex) {
/*  97 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 100 */     ByteArrayOutputStream tmpOut = new ByteArrayOutputStream();
/* 101 */     String outStr = out.toString();
/* 102 */     int pos = outStr.indexOf('>');
/* 103 */     String startElem = outStr.substring(0, pos + 1);
/* 104 */     String endElem = outStr.substring(pos + 1);
/*     */     try {
/* 106 */       tmpOut.write(startElem.getBytes());
/* 107 */       byte[] buf = new byte[4096];
/*     */       
/* 109 */       for (int len = -1; (len = decryptedIS.read(buf)) != -1;) {
/* 110 */         tmpOut.write(buf, 0, len);
/*     */       }
/* 112 */       tmpOut.write(endElem.getBytes());
/* 113 */     } catch (IOException ex) {
/* 114 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 117 */     InputStream finalContent = new ByteArrayInputStream(tmpOut.toByteArray());
/* 118 */     XMLInputFactory xif = XMLInputFactory.newInstance();
/* 119 */     XMLStreamReader reader = xif.createXMLStreamReader(finalContent);
/* 120 */     return reader;
/*     */   }
/*     */   
/*     */   void writeStartElement(XMLStreamWriter xsw) throws XMLStreamException, XWSSecurityException {
/* 124 */     if (!this.parsed) {
/* 125 */       parse();
/*     */     }
/* 127 */     xsw.writeStartElement(this.prefix, this.localName, this.uri);
/* 128 */     if (this.parentNS.containsKey(this.prefix))
/* 129 */       xsw.writeNamespace(this.prefix, this.uri); 
/*     */     int i;
/* 131 */     for (i = 0; i < this.attrNSList.size(); i++) {
/* 132 */       AttributeNS attrNs = this.attrNSList.get(i);
/* 133 */       xsw.writeNamespace(attrNs.getPrefix(), attrNs.getUri());
/*     */     } 
/* 135 */     for (i = 0; i < this.attrList.size(); i++) {
/* 136 */       StAXAttr attr = this.attrList.get(i);
/* 137 */       if (this.parentNS.containsKey(attr.getPrefix())) {
/* 138 */         xsw.writeNamespace(attr.getPrefix(), this.parentNS.get(attr.getPrefix()));
/*     */       }
/*     */     } 
/* 141 */     for (i = 0; i < this.attrList.size(); i++) {
/* 142 */       StAXAttr attr = this.attrList.get(i);
/* 143 */       xsw.writeAttribute(attr.getPrefix(), attr.getUri(), attr.getLocalName(), attr.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   void writeEndElement(XMLStreamWriter xsw) throws XMLStreamException {
/* 148 */     xsw.writeEndElement();
/*     */   }
/*     */   
/*     */   EncryptedData getEncryptedData() throws XMLStreamException, XWSSecurityException {
/* 152 */     if (!this.parsed) {
/* 153 */       parse();
/*     */     }
/* 155 */     return this.ed;
/*     */   }
/*     */   
/*     */   void parse() throws XMLStreamException, XWSSecurityException {
/* 159 */     this.parsed = true;
/* 160 */     boolean stop = false;
/* 161 */     boolean parentElem = true;
/* 162 */     while (this.encContentReader.hasNext()) {
/* 163 */       int eventType = 1;
/* 164 */       if (!parentElem) {
/* 165 */         eventType = this.encContentReader.next();
/*     */       }
/* 167 */       if (stop) {
/*     */         return;
/*     */       }
/* 170 */       switch (eventType) {
/*     */         case 1:
/* 172 */           if (parentElem) {
/* 173 */             this.localName = this.encContentReader.getLocalName();
/* 174 */             this.uri = this.encContentReader.getNamespaceURI();
/* 175 */             this.prefix = this.encContentReader.getPrefix();
/* 176 */             if (this.prefix == null)
/* 177 */               this.prefix = ""; 
/* 178 */             int count = this.encContentReader.getAttributeCount(); int i;
/* 179 */             for (i = 0; i < count; i++) {
/* 180 */               String localName = this.encContentReader.getAttributeLocalName(i);
/* 181 */               String uri = this.encContentReader.getAttributeNamespace(i);
/* 182 */               String prefix = this.encContentReader.getAttributePrefix(i);
/* 183 */               if (prefix == null)
/* 184 */                 prefix = ""; 
/* 185 */               String value = this.encContentReader.getAttributeValue(i);
/* 186 */               StAXAttr attr = new StAXAttr();
/* 187 */               attr.setLocalName(localName);
/* 188 */               attr.setValue(value);
/* 189 */               attr.setPrefix(prefix);
/* 190 */               attr.setUri(uri);
/* 191 */               this.attrList.add(attr);
/*     */             } 
/*     */             
/* 194 */             count = 0;
/* 195 */             count = this.encContentReader.getNamespaceCount();
/* 196 */             for (i = 0; i < count; i++) {
/* 197 */               String prefix = this.encContentReader.getNamespacePrefix(i);
/* 198 */               if (prefix == null)
/* 199 */                 prefix = ""; 
/* 200 */               String uri = this.encContentReader.getNamespaceURI(i);
/* 201 */               AttributeNS attrNS = new AttributeNS();
/* 202 */               attrNS.setPrefix(prefix);
/* 203 */               attrNS.setUri(uri);
/* 204 */               this.attrNSList.add(attrNS);
/*     */             } 
/* 206 */             parentElem = false; continue;
/*     */           } 
/* 208 */           if (this.encContentReader.getLocalName() == "EncryptedData" && this.encContentReader.getNamespaceURI() == "http://www.w3.org/2001/04/xmlenc#")
/*     */           {
/* 210 */             this.ed = new EncryptedData(this.encContentReader, this.context, this.parentNS);
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 216 */           stop = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\EncryptedContentHeaderParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */