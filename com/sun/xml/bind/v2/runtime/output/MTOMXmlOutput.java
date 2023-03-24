/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MTOMXmlOutput
/*     */   extends XmlOutputAbstractImpl
/*     */ {
/*     */   private final XmlOutput next;
/*     */   private String nsUri;
/*     */   private String localName;
/*     */   
/*     */   public MTOMXmlOutput(XmlOutput next) {
/*  71 */     this.next = next;
/*     */   }
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/*  75 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*  76 */     this.next.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*     */   }
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/*  80 */     this.next.endDocument(fragment);
/*  81 */     super.endDocument(fragment);
/*     */   }
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException, XMLStreamException {
/*  85 */     this.next.beginStartTag(name);
/*  86 */     this.nsUri = name.nsUri;
/*  87 */     this.localName = name.localName;
/*     */   }
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException, XMLStreamException {
/*  91 */     this.next.beginStartTag(prefix, localName);
/*  92 */     this.nsUri = this.nsContext.getNamespaceURI(prefix);
/*  93 */     this.localName = localName;
/*     */   }
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException, XMLStreamException {
/*  97 */     this.next.attribute(name, value);
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException, XMLStreamException {
/* 101 */     this.next.attribute(prefix, localName, value);
/*     */   }
/*     */   
/*     */   public void endStartTag() throws IOException, SAXException {
/* 105 */     this.next.endStartTag();
/*     */   }
/*     */   
/*     */   public void endTag(Name name) throws IOException, SAXException, XMLStreamException {
/* 109 */     this.next.endTag(name);
/*     */   }
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException, SAXException, XMLStreamException {
/* 113 */     this.next.endTag(prefix, localName);
/*     */   }
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 117 */     this.next.text(value, needsSeparatingWhitespace);
/*     */   }
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 121 */     if (value instanceof Base64Data && !this.serializer.getInlineBinaryFlag()) {
/* 122 */       String cid; Base64Data b64d = (Base64Data)value;
/*     */       
/* 124 */       if (b64d.hasData()) {
/* 125 */         cid = this.serializer.attachmentMarshaller.addMtomAttachment(b64d.get(), 0, b64d.getDataLen(), b64d.getMimeType(), this.nsUri, this.localName);
/*     */       } else {
/*     */         
/* 128 */         cid = this.serializer.attachmentMarshaller.addMtomAttachment(b64d.getDataHandler(), this.nsUri, this.localName);
/*     */       } 
/*     */       
/* 131 */       if (cid != null) {
/* 132 */         this.nsContext.getCurrent().push();
/* 133 */         int prefix = this.nsContext.declareNsUri("http://www.w3.org/2004/08/xop/include", "xop", false);
/* 134 */         beginStartTag(prefix, "Include");
/* 135 */         attribute(-1, "href", cid);
/* 136 */         endStartTag();
/* 137 */         endTag(prefix, "Include");
/* 138 */         this.nsContext.getCurrent().pop();
/*     */         return;
/*     */       } 
/*     */     } 
/* 142 */     this.next.text(value, needsSeparatingWhitespace);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\MTOMXmlOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */