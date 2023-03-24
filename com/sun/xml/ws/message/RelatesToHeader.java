/*    */ package com.sun.xml.ws.message;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPHeader;
/*    */ import javax.xml.soap.SOAPHeaderElement;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RelatesToHeader
/*    */   extends StringHeader
/*    */ {
/*    */   protected String type;
/*    */   private final QName typeAttributeName;
/*    */   
/*    */   public RelatesToHeader(QName name, String messageId, String type) {
/* 63 */     super(name, messageId);
/* 64 */     this.type = type;
/* 65 */     this.typeAttributeName = new QName(name.getNamespaceURI(), "type");
/*    */   }
/*    */   
/*    */   public RelatesToHeader(QName name, String mid) {
/* 69 */     super(name, mid);
/* 70 */     this.typeAttributeName = new QName(name.getNamespaceURI(), "type");
/*    */   }
/*    */   
/*    */   public String getType() {
/* 74 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 79 */     w.writeStartElement("", this.name.getLocalPart(), this.name.getNamespaceURI());
/* 80 */     w.writeDefaultNamespace(this.name.getNamespaceURI());
/* 81 */     if (this.type != null)
/* 82 */       w.writeAttribute("type", this.type); 
/* 83 */     w.writeCharacters(this.value);
/* 84 */     w.writeEndElement();
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 89 */     SOAPHeader header = saaj.getSOAPHeader();
/* 90 */     if (header == null)
/* 91 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 92 */     SOAPHeaderElement she = header.addHeaderElement(this.name);
/*    */     
/* 94 */     if (this.type != null)
/* 95 */       she.addAttribute(this.typeAttributeName, this.type); 
/* 96 */     she.addTextNode(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\RelatesToHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */