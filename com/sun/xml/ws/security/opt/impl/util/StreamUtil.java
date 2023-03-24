/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ import org.jvnet.staxex.XMLStreamWriterEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamUtil
/*     */ {
/*     */   public static boolean moveToNextElement(XMLStreamReader reader) throws XMLStreamException {
/*  61 */     if (reader.hasNext()) {
/*  62 */       reader.next();
/*  63 */       while (reader.getEventType() != 1) {
/*  64 */         if (reader.hasNext()) {
/*  65 */           reader.next(); continue;
/*     */         } 
/*  67 */         return false;
/*     */       } 
/*     */       
/*  70 */       return true;
/*     */     } 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean moveToNextStartOREndElement(XMLStreamReader reader) throws XMLStreamException {
/*  77 */     if (reader.hasNext()) {
/*  78 */       reader.next();
/*  79 */       while (move(reader)) {
/*  80 */         if (reader.hasNext()) {
/*  81 */           reader.next(); continue;
/*     */         } 
/*  83 */         return false;
/*     */       } 
/*     */       
/*  86 */       return true;
/*     */     } 
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean moveToNextStartOREndElement(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
/*  93 */     if (writer == null) {
/*  94 */       return moveToNextStartOREndElement(reader);
/*     */     }
/*  96 */     if (reader.hasNext()) {
/*  97 */       reader.next();
/*  98 */       writeCurrentEvent(reader, writer);
/*  99 */       while (move(reader)) {
/* 100 */         if (reader.hasNext()) {
/* 101 */           reader.next();
/* 102 */           writeCurrentEvent(reader, writer); continue;
/*     */         } 
/* 104 */         return false;
/*     */       } 
/*     */       
/* 107 */       return true;
/*     */     } 
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStartElement(XMLStreamReader reader) {
/* 115 */     if (reader.getEventType() == 1) {
/* 116 */       return true;
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean _break(XMLStreamReader reader, String localName, String uri) throws XMLStreamException {
/* 123 */     if (reader.getEventType() == 2 && 
/* 124 */       reader.getLocalName() == localName && (reader.getNamespaceURI() == uri || reader.getNamespaceURI() == "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512"))
/*     */     {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean move(XMLStreamReader reader) {
/* 134 */     if (reader.getEventType() == 1 || reader.getEventType() == 2)
/*     */     {
/* 136 */       return false;
/*     */     }
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeStartElement(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
/* 143 */     String pref = reader.getPrefix();
/* 144 */     if (pref == null) {
/* 145 */       pref = "";
/*     */     }
/* 147 */     writer.writeStartElement(pref, reader.getLocalName(), reader.getNamespaceURI());
/*     */     
/* 149 */     int nsCount = reader.getNamespaceCount();
/*     */     
/* 151 */     for (int i = 0; i < nsCount; i++) {
/* 152 */       String prefix = reader.getNamespacePrefix(i);
/* 153 */       if (prefix == null) prefix = ""; 
/* 154 */       writer.writeNamespace(prefix, reader.getNamespaceURI(i));
/*     */     } 
/* 156 */     int atCount = reader.getAttributeCount();
/* 157 */     for (int j = 0; j < atCount; j++) {
/* 158 */       if (reader.getAttributePrefix(j) == "" || reader.getAttributePrefix(j) == null) {
/* 159 */         writer.writeAttribute(reader.getAttributeLocalName(j), reader.getAttributeValue(j));
/*     */       } else {
/* 161 */         writer.writeAttribute(reader.getAttributePrefix(j), reader.getAttributeNamespace(j), reader.getAttributeLocalName(j), reader.getAttributeValue(j));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeCurrentEvent(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
/*     */     char[] buf;
/* 168 */     int actual, sourceStart, event = reader.getEventType();
/* 169 */     switch (event) {
/*     */       
/*     */       case 12:
/* 172 */         writer.writeCData(reader.getText());
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 177 */         buf = new char[2048];
/* 178 */         actual = 0;
/* 179 */         sourceStart = 0;
/*     */         do {
/* 181 */           actual = reader.getTextCharacters(sourceStart, buf, 0, 2048);
/* 182 */           if (actual <= 0)
/* 183 */             continue;  writer.writeCharacters(buf, 0, actual);
/* 184 */           sourceStart += actual;
/*     */         }
/* 186 */         while (actual == 2048);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 192 */         writer.writeComment(reader.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 202 */         writer.writeEndElement();
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 221 */         writer.writeCharacters(reader.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 229 */         writeStartElement(reader, writer);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCurrentEvent(XMLStreamReaderEx reader, XMLStreamWriterEx writer) throws XMLStreamException {
/* 237 */     int event = reader.getEventType();
/* 238 */     switch (event) {
/*     */       
/*     */       case 12:
/* 241 */         writer.writeCData(reader.getText());
/*     */         break;
/*     */       
/*     */       case 4:
/* 245 */         writer.writeCharacters(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
/*     */         break;
/*     */       
/*     */       case 5:
/* 249 */         writer.writeComment(reader.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 259 */         writer.writeEndElement();
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 278 */         writer.writeCharacters(reader.getText());
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 286 */         writeStartElement((XMLStreamReader)reader, (XMLStreamWriter)writer);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getWsuId(XMLStreamReader reader) {
/* 293 */     return reader.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*     */   }
/*     */   
/*     */   public static String getId(XMLStreamReader reader) {
/* 297 */     return reader.getAttributeValue(null, "Id");
/*     */   }
/*     */   
/*     */   public static String getCV(XMLStreamReader reader) throws XMLStreamException {
/* 301 */     StringBuffer content = new StringBuffer();
/* 302 */     int eventType = reader.getEventType();
/* 303 */     while (eventType != 2) {
/* 304 */       if (eventType == 4 || eventType == 12 || eventType == 6 || eventType == 9) {
/*     */ 
/*     */ 
/*     */         
/* 308 */         content.append(reader.getText());
/* 309 */       } else if (eventType == 3 || eventType == 5) {
/*     */       
/*     */       } 
/*     */       
/* 313 */       eventType = reader.next();
/*     */     } 
/* 315 */     return content.toString();
/*     */   }
/*     */   
/*     */   public static String getCV(XMLStreamReaderEx reader) throws XMLStreamException {
/* 319 */     StringBuffer sb = new StringBuffer();
/* 320 */     while (reader.getEventType() == 4 && reader.getEventType() != 2) {
/* 321 */       CharSequence charSeq = reader.getPCDATA();
/* 322 */       for (int i = 0; i < charSeq.length(); i++) {
/* 323 */         sb.append(charSeq.charAt(i));
/*     */       }
/* 325 */       reader.next();
/*     */     } 
/* 327 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String convertDigestAlgorithm(String algo) {
/* 331 */     if ("http://www.w3.org/2000/09/xmldsig#sha1".equals(algo)) {
/* 332 */       return "SHA-1";
/*     */     }
/* 334 */     if ("http://www.w3.org/2001/04/xmlenc#sha256".equals(algo)) {
/* 335 */       return "SHA-256";
/*     */     }
/*     */     
/* 338 */     if ("http://www.w3.org/2001/04/xmlenc#sha512".equals(algo)) {
/* 339 */       return "SHA-512";
/*     */     }
/*     */     
/* 342 */     return "SHA-1";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\StreamUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */