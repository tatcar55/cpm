/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TagInfoset
/*     */ {
/*     */   @NotNull
/*     */   public final String[] ns;
/*     */   @NotNull
/*     */   public final AttributesImpl atts;
/*     */   @Nullable
/*     */   public final String prefix;
/*     */   @Nullable
/*     */   public final String nsUri;
/*     */   @NotNull
/*     */   public final String localName;
/*     */   @Nullable
/*     */   private String qname;
/*     */   
/*     */   public TagInfoset(String nsUri, String localName, String prefix, AttributesImpl atts, String... ns) {
/* 108 */     this.nsUri = nsUri;
/* 109 */     this.prefix = prefix;
/* 110 */     this.localName = localName;
/* 111 */     this.atts = atts;
/* 112 */     this.ns = ns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagInfoset(XMLStreamReader reader) {
/* 120 */     this.prefix = reader.getPrefix();
/* 121 */     this.nsUri = reader.getNamespaceURI();
/* 122 */     this.localName = reader.getLocalName();
/*     */     
/* 124 */     int nsc = reader.getNamespaceCount();
/* 125 */     if (nsc > 0) {
/* 126 */       this.ns = new String[nsc * 2];
/* 127 */       for (int i = 0; i < nsc; i++) {
/* 128 */         this.ns[i * 2] = fixNull(reader.getNamespacePrefix(i));
/* 129 */         this.ns[i * 2 + 1] = fixNull(reader.getNamespaceURI(i));
/*     */       } 
/*     */     } else {
/* 132 */       this.ns = EMPTY_ARRAY;
/*     */     } 
/*     */     
/* 135 */     int ac = reader.getAttributeCount();
/* 136 */     if (ac > 0) {
/* 137 */       this.atts = new AttributesImpl();
/* 138 */       StringBuilder sb = new StringBuilder();
/* 139 */       for (int i = 0; i < ac; i++) {
/* 140 */         String qname; sb.setLength(0);
/* 141 */         String prefix = reader.getAttributePrefix(i);
/* 142 */         String localName = reader.getAttributeLocalName(i);
/*     */ 
/*     */         
/* 145 */         if (prefix != null && prefix.length() != 0) {
/* 146 */           sb.append(prefix);
/* 147 */           sb.append(":");
/* 148 */           sb.append(localName);
/* 149 */           qname = sb.toString();
/*     */         } else {
/* 151 */           qname = localName;
/*     */         } 
/*     */         
/* 154 */         this.atts.addAttribute(fixNull(reader.getAttributeNamespace(i)), localName, qname, reader.getAttributeType(i), reader.getAttributeValue(i));
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 162 */       this.atts = EMPTY_ATTRIBUTES;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStart(ContentHandler contentHandler) throws SAXException {
/* 170 */     for (int i = 0; i < this.ns.length; i += 2)
/* 171 */       contentHandler.startPrefixMapping(fixNull(this.ns[i]), fixNull(this.ns[i + 1])); 
/* 172 */     contentHandler.startElement(fixNull(this.nsUri), this.localName, getQName(), this.atts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEnd(ContentHandler contentHandler) throws SAXException {
/* 179 */     contentHandler.endElement(fixNull(this.nsUri), this.localName, getQName());
/* 180 */     for (int i = this.ns.length - 2; i >= 0; i -= 2) {
/* 181 */       contentHandler.endPrefixMapping(fixNull(this.ns[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStart(XMLStreamWriter w) throws XMLStreamException {
/* 190 */     if (this.prefix == null) {
/* 191 */       if (this.nsUri == null) {
/* 192 */         w.writeStartElement(this.localName);
/*     */       }
/*     */       else {
/*     */         
/* 196 */         w.writeStartElement("", this.localName, this.nsUri);
/*     */       } 
/*     */     } else {
/* 199 */       w.writeStartElement(this.prefix, this.localName, this.nsUri);
/*     */     } 
/*     */     int i;
/* 202 */     for (i = 0; i < this.ns.length; i += 2) {
/* 203 */       w.writeNamespace(this.ns[i], this.ns[i + 1]);
/*     */     }
/*     */     
/* 206 */     for (i = 0; i < this.atts.getLength(); i++) {
/* 207 */       String nsUri = this.atts.getURI(i);
/* 208 */       if (nsUri == null || nsUri.length() == 0) {
/* 209 */         w.writeAttribute(this.atts.getLocalName(i), this.atts.getValue(i));
/*     */       } else {
/* 211 */         String rawName = this.atts.getQName(i);
/* 212 */         String prefix = rawName.substring(0, rawName.indexOf(':'));
/* 213 */         w.writeAttribute(prefix, nsUri, this.atts.getLocalName(i), this.atts.getValue(i));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getQName() {
/* 219 */     if (this.qname != null) return this.qname;
/*     */     
/* 221 */     StringBuilder sb = new StringBuilder();
/* 222 */     if (this.prefix != null) {
/* 223 */       sb.append(this.prefix);
/* 224 */       sb.append(':');
/* 225 */       sb.append(this.localName);
/* 226 */       this.qname = sb.toString();
/*     */     } else {
/* 228 */       this.qname = this.localName;
/*     */     } 
/* 230 */     return this.qname;
/*     */   }
/*     */   private static String fixNull(String s) {
/* 233 */     if (s == null) return ""; 
/* 234 */     return s;
/*     */   }
/*     */   
/* 237 */   private static final String[] EMPTY_ARRAY = new String[0];
/* 238 */   private static final AttributesImpl EMPTY_ATTRIBUTES = new AttributesImpl();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\TagInfoset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */