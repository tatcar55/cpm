/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.impl.attachment.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecuredMessage
/*     */ {
/*     */   ArrayList headers;
/*  71 */   SOAPBody body = null;
/*     */   boolean isOneWay = false;
/*  73 */   SecurityElement securedBody = null;
/*  74 */   AttachmentSet attachments = null;
/*  75 */   private NamespaceContextEx context = null;
/*  76 */   SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*  77 */   private SecurityHeader sh = null;
/*     */ 
/*     */   
/*     */   public SecuredMessage(Message msg, SecurityHeader sh, SOAPVersion soapVersion) {
/*  81 */     this(msg, sh);
/*  82 */     this.body = new SOAPBody(msg, soapVersion);
/*  83 */     this.soapVersion = soapVersion;
/*  84 */     boolean isSOAP12 = (soapVersion == SOAPVersion.SOAP_12);
/*     */   }
/*     */ 
/*     */   
/*     */   public SecuredMessage(Message msg, SecurityHeader sh) {
/*  89 */     HeaderList hl = msg.getHeaders();
/*  90 */     this.headers = new ArrayList((Collection<?>)hl);
/*     */     
/*  92 */     this.body = new SOAPBody(msg);
/*  93 */     this.attachments = msg.getAttachments();
/*  94 */     this.sh = sh;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/*  98 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public ArrayList getHeaders() {
/* 102 */     return this.headers;
/*     */   }
/*     */   
/*     */   public void setRootElements(NamespaceContextEx ne) {
/* 106 */     this.context = ne;
/*     */   }
/*     */   
/*     */   public boolean isOneWay() {
/* 110 */     return this.isOneWay;
/*     */   }
/*     */   
/*     */   public Iterator getHeaders(final String localName, final String uri) {
/* 114 */     return new Iterator()
/*     */       {
/* 116 */         int idx = 0;
/*     */         Object next;
/*     */         
/*     */         public boolean hasNext() {
/* 120 */           if (this.next == null) {
/* 121 */             fetch();
/*     */           }
/* 123 */           return (this.next != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 127 */           if (this.next == null) {
/* 128 */             fetch();
/* 129 */             if (this.next == null) {
/* 130 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 134 */           Object r = this.next;
/* 135 */           this.next = null;
/* 136 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 140 */           while (this.idx < SecuredMessage.this.headers.size()) {
/* 141 */             Object obj = SecuredMessage.this.headers.get(this.idx++);
/* 142 */             if (obj instanceof Header) {
/* 143 */               Header hd = (Header)obj;
/* 144 */               if ((uri == null && localName.equals(hd.getLocalPart())) || (localName.equals(hd.getLocalPart()) && uri.equals(hd.getNamespaceURI()))) {
/*     */                 
/* 146 */                 this.next = hd; break;
/*     */               }  continue;
/*     */             } 
/* 149 */             if (obj instanceof SecurityElement) {
/* 150 */               SecurityElement she = (SecurityElement)obj;
/* 151 */               if ((uri == null && localName.equals(she.getLocalPart())) || (localName.equals(she.getLocalPart()) && uri.equals(she.getNamespaceURI()))) {
/*     */                 
/* 153 */                 this.next = she;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         public void remove() {
/* 161 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getHeaders(final String uri) {
/* 168 */     return new Iterator()
/*     */       {
/* 170 */         int idx = 0;
/*     */         Object next;
/*     */         
/*     */         public boolean hasNext() {
/* 174 */           if (this.next == null) {
/* 175 */             fetch();
/*     */           }
/* 177 */           return (this.next != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 181 */           if (this.next == null) {
/* 182 */             fetch();
/* 183 */             if (this.next == null) {
/* 184 */               throw new NoSuchElementException();
/*     */             }
/*     */           } 
/*     */           
/* 188 */           Object r = this.next;
/* 189 */           this.next = null;
/* 190 */           return r;
/*     */         }
/*     */         
/*     */         private void fetch() {
/* 194 */           while (this.idx < SecuredMessage.this.headers.size()) {
/* 195 */             Object obj = SecuredMessage.this.headers.get(this.idx++);
/* 196 */             if (obj instanceof Header) {
/* 197 */               Header hd = (Header)obj;
/* 198 */               if (uri.equals(hd.getNamespaceURI())) {
/* 199 */                 this.next = hd; break;
/*     */               }  continue;
/*     */             } 
/* 202 */             if (obj instanceof SecurityElement) {
/* 203 */               SecurityElement she = (SecurityElement)obj;
/* 204 */               if (uri.equals(she.getNamespaceURI())) {
/* 205 */                 this.next = she;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         public void remove() {
/* 213 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean replaceHeader(Object header1, Object header2) {
/* 219 */     boolean replaced = false;
/* 220 */     for (int i = 0; i < this.headers.size(); i++) {
/* 221 */       Object obj = this.headers.get(i);
/* 222 */       if (obj == header1 && obj.equals(header1)) {
/* 223 */         this.headers.set(i, header2);
/* 224 */         replaced = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 228 */     return replaced;
/*     */   }
/*     */   
/*     */   public Object getHeader(String id) {
/* 232 */     Object hdr = null;
/* 233 */     for (int i = 0; i < this.headers.size(); i++) {
/* 234 */       Object obj = this.headers.get(i);
/* 235 */       if (obj instanceof Header) {
/* 236 */         Header hd = (Header)obj;
/* 237 */         String wsuId = hd.getAttribute("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Id");
/* 238 */         if (id.equals(wsuId)) {
/* 239 */           hdr = hd;
/*     */           break;
/*     */         } 
/* 242 */       } else if (obj instanceof SecurityElement) {
/* 243 */         SecurityElement she = (SecurityElement)obj;
/* 244 */         if (id.equals(she.getId())) {
/* 245 */           hdr = she;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 250 */     return hdr;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 254 */     if (this.body != null) {
/* 255 */       return this.body.getPayloadNamespaceURI();
/*     */     }
/* 257 */     if (this.securedBody != null) {
/* 258 */       return this.securedBody.getNamespaceURI();
/*     */     }
/* 260 */     return null;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 264 */     if (this.body != null) {
/* 265 */       return this.body.getPayloadLocalPart();
/*     */     }
/* 267 */     if (this.securedBody != null) {
/* 268 */       return this.securedBody.getLocalPart();
/*     */     }
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 274 */     if (this.body != null) {
/* 275 */       return this.body.read();
/*     */     }
/*     */     
/* 278 */     if (this.securedBody != null) {
/* 279 */       return this.securedBody.readHeader();
/*     */     }
/* 281 */     throw new XMLStreamException("No Payload found");
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 285 */     if (this.body != null) {
/* 286 */       this.body.writeTo(sw);
/* 287 */     } else if (this.securedBody != null) {
/* 288 */       ((SecurityElementWriter)this.securedBody).writeTo(sw);
/*     */     } else {
/* 290 */       throw new XMLStreamException("No Payload found");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getBody() throws XWSSecurityException {
/* 296 */     if (this.body != null)
/* 297 */       return this.body; 
/* 298 */     if (this.securedBody != null) {
/* 299 */       return this.securedBody;
/*     */     }
/* 301 */     throw new XWSSecurityException("No body present in message");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceBody(SecurityElement she) {
/* 307 */     this.securedBody = she;
/* 308 */     this.body = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceBody(SOAPBody sb) {
/* 313 */     this.body = sb;
/* 314 */     this.securedBody = null;
/*     */   }
/*     */   
/*     */   public AttachmentSet getAttachments() {
/* 318 */     if (this.attachments == null) {
/* 319 */       this.attachments = (AttachmentSet)new AttachmentSetImpl();
/*     */     }
/* 321 */     return this.attachments;
/*     */   }
/*     */   
/*     */   public void setAttachments(AttachmentSet as) {
/* 325 */     this.attachments = as;
/*     */   }
/*     */   
/*     */   public Attachment getAttachment(String uri) {
/* 329 */     Attachment attachment = null;
/*     */     
/* 331 */     if (this.attachments != null && uri.startsWith("cid:")) {
/* 332 */       uri = uri.substring("cid:".length());
/* 333 */       attachment = this.attachments.get(uri);
/*     */     } 
/*     */     
/* 336 */     return attachment;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 340 */     sw.writeStartDocument();
/* 341 */     sw.writeStartElement("S", "Envelope", this.soapVersion.nsUri);
/* 342 */     Iterator<NamespaceContextEx.Binding> itr = this.context.iterator();
/*     */     
/* 344 */     while (itr.hasNext()) {
/* 345 */       NamespaceContextEx.Binding binding = itr.next();
/* 346 */       sw.writeNamespace(binding.getPrefix(), binding.getNamespaceURI());
/*     */     } 
/*     */     
/* 349 */     sw.writeStartElement("S", "Header", this.soapVersion.nsUri);
/* 350 */     for (int i = 0; i < this.headers.size(); i++) {
/* 351 */       Object hdr = this.headers.get(i);
/* 352 */       if (hdr instanceof Header) {
/* 353 */         ((Header)hdr).writeTo(sw);
/*     */       } else {
/* 355 */         ((SecurityElementWriter)hdr).writeTo(sw);
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     this.sh.writeTo(sw);
/* 360 */     sw.writeEndElement();
/* 361 */     if (this.securedBody != null) {
/* 362 */       ((SecurityElementWriter)this.securedBody).writeTo(sw);
/* 363 */     } else if (this.body != null) {
/* 364 */       this.body.writeTo(sw);
/*     */     } 
/*     */     
/* 367 */     sw.writeEndDocument();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\SecuredMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */