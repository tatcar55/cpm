/*     */ package com.sun.xml.ws.util.xml;
/*     */ 
/*     */ import com.sun.xml.ws.streaming.MtomStreamWriter;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.Base64Data;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderToXMLStreamWriter
/*     */ {
/*     */   private static final int BUF_SIZE = 4096;
/*     */   protected XMLStreamReader in;
/*     */   protected XMLStreamWriter out;
/*     */   private char[] buf;
/*     */   boolean optimizeBase64Data = false;
/*     */   AttachmentMarshaller mtomAttachmentMarshaller;
/*     */   
/*     */   public void bridge(XMLStreamReader in, XMLStreamWriter out) throws XMLStreamException {
/*  88 */     assert in != null && out != null;
/*  89 */     this.in = in;
/*  90 */     this.out = out;
/*     */     
/*  92 */     this.optimizeBase64Data = in instanceof XMLStreamReaderEx;
/*     */     
/*  94 */     if (out instanceof XMLStreamWriterEx && out instanceof MtomStreamWriter) {
/*  95 */       this.mtomAttachmentMarshaller = ((MtomStreamWriter)out).getAttachmentMarshaller();
/*     */     }
/*     */     
/*  98 */     int depth = 0;
/*     */     
/* 100 */     this.buf = new char[4096];
/*     */ 
/*     */     
/* 103 */     int event = in.getEventType();
/* 104 */     if (event == 7)
/*     */     {
/* 106 */       while (!in.isStartElement()) {
/* 107 */         event = in.next();
/* 108 */         if (event == 5) {
/* 109 */           handleComment();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 114 */     if (event != 1) {
/* 115 */       throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 121 */       switch (event) {
/*     */         case 1:
/* 123 */           depth++;
/* 124 */           handleStartElement();
/*     */           break;
/*     */         case 2:
/* 127 */           handleEndElement();
/* 128 */           depth--;
/* 129 */           if (depth == 0)
/*     */             return; 
/*     */           break;
/*     */         case 4:
/* 133 */           handleCharacters();
/*     */           break;
/*     */         case 9:
/* 136 */           handleEntityReference();
/*     */           break;
/*     */         case 3:
/* 139 */           handlePI();
/*     */           break;
/*     */         case 5:
/* 142 */           handleComment();
/*     */           break;
/*     */         case 11:
/* 145 */           handleDTD();
/*     */           break;
/*     */         case 12:
/* 148 */           handleCDATA();
/*     */           break;
/*     */         case 6:
/* 151 */           handleSpace();
/*     */           break;
/*     */         case 8:
/* 154 */           throw new XMLStreamException("Malformed XML at depth=" + depth + ", Reached EOF. Event=" + event);
/*     */         default:
/* 156 */           throw new XMLStreamException("Cannot process event: " + event);
/*     */       } 
/*     */       
/* 159 */       event = in.next();
/* 160 */     } while (depth != 0);
/*     */   }
/*     */   
/*     */   protected void handlePI() throws XMLStreamException {
/* 164 */     this.out.writeProcessingInstruction(this.in.getPITarget(), this.in.getPIData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleCharacters() throws XMLStreamException {
/* 172 */     CharSequence c = null;
/*     */     
/* 174 */     if (this.optimizeBase64Data) {
/* 175 */       c = ((XMLStreamReaderEx)this.in).getPCDATA();
/*     */     }
/*     */     
/* 178 */     if (c != null && c instanceof Base64Data) {
/* 179 */       if (this.mtomAttachmentMarshaller != null) {
/* 180 */         Base64Data b64d = (Base64Data)c;
/* 181 */         ((XMLStreamWriterEx)this.out).writeBinary(b64d.getDataHandler());
/*     */       } else {
/*     */         try {
/* 184 */           ((Base64Data)c).writeTo(this.out);
/* 185 */         } catch (IOException e) {
/* 186 */           throw new XMLStreamException(e);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 190 */       for (int start = 0, read = this.buf.length; read == this.buf.length; start += this.buf.length) {
/* 191 */         read = this.in.getTextCharacters(start, this.buf, 0, this.buf.length);
/* 192 */         this.out.writeCharacters(this.buf, 0, read);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleEndElement() throws XMLStreamException {
/* 198 */     this.out.writeEndElement();
/*     */   }
/*     */   
/*     */   protected void handleStartElement() throws XMLStreamException {
/* 202 */     String nsUri = this.in.getNamespaceURI();
/* 203 */     if (nsUri == null) {
/* 204 */       this.out.writeStartElement(this.in.getLocalName());
/*     */     } else {
/* 206 */       this.out.writeStartElement(fixNull(this.in.getPrefix()), this.in.getLocalName(), nsUri);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     int nsCount = this.in.getNamespaceCount();
/* 214 */     for (int i = 0; i < nsCount; i++) {
/* 215 */       this.out.writeNamespace(this.in.getNamespacePrefix(i), fixNull(this.in.getNamespaceURI(i)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     int attCount = this.in.getAttributeCount();
/* 222 */     for (int j = 0; j < attCount; j++) {
/* 223 */       handleAttribute(j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleAttribute(int i) throws XMLStreamException {
/* 234 */     String nsUri = this.in.getAttributeNamespace(i);
/* 235 */     String prefix = this.in.getAttributePrefix(i);
/* 236 */     if (fixNull(nsUri).equals("http://www.w3.org/2000/xmlns/")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (nsUri == null || prefix == null || prefix.equals("")) {
/* 242 */       this.out.writeAttribute(this.in.getAttributeLocalName(i), this.in.getAttributeValue(i));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 247 */       this.out.writeAttribute(prefix, nsUri, this.in.getAttributeLocalName(i), this.in.getAttributeValue(i));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleDTD() throws XMLStreamException {
/* 257 */     this.out.writeDTD(this.in.getText());
/*     */   }
/*     */   
/*     */   protected void handleComment() throws XMLStreamException {
/* 261 */     this.out.writeComment(this.in.getText());
/*     */   }
/*     */   
/*     */   protected void handleEntityReference() throws XMLStreamException {
/* 265 */     this.out.writeEntityRef(this.in.getText());
/*     */   }
/*     */   
/*     */   protected void handleSpace() throws XMLStreamException {
/* 269 */     handleCharacters();
/*     */   }
/*     */   
/*     */   protected void handleCDATA() throws XMLStreamException {
/* 273 */     this.out.writeCData(this.in.getText());
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 277 */     if (s == null) return ""; 
/* 278 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\XMLStreamReaderToXMLStreamWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */