/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.Envelope;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import com.sun.xml.messaging.saaj.util.FastInfosetReflection;
/*     */ import com.sun.xml.messaging.saaj.util.transform.EfficientStreamingTransformer;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EnvelopeImpl
/*     */   extends ElementImpl
/*     */   implements Envelope
/*     */ {
/*     */   protected HeaderImpl header;
/*     */   protected BodyImpl body;
/*  73 */   String omitXmlDecl = "yes";
/*  74 */   String charset = "utf-8";
/*  75 */   String xmlDecl = null;
/*     */   
/*     */   protected EnvelopeImpl(SOAPDocumentImpl ownerDoc, Name name) {
/*  78 */     super(ownerDoc, name);
/*     */   }
/*     */   
/*     */   protected EnvelopeImpl(SOAPDocumentImpl ownerDoc, QName name) {
/*  82 */     super(ownerDoc, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnvelopeImpl(SOAPDocumentImpl ownerDoc, NameImpl name, boolean createHeader, boolean createBody) throws SOAPException {
/*  91 */     this(ownerDoc, (Name)name);
/*     */     
/*  93 */     ensureNamespaceIsDeclared(getElementQName().getPrefix(), getElementQName().getNamespaceURI());
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (createHeader) {
/*  98 */       addHeader();
/*     */     }
/* 100 */     if (createBody) {
/* 101 */       addBody();
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract NameImpl getHeaderName(String paramString);
/*     */   
/*     */   public SOAPHeader addHeader() throws SOAPException {
/* 108 */     return addHeader((String)null);
/*     */   }
/*     */   protected abstract NameImpl getBodyName(String paramString);
/*     */   
/*     */   public SOAPHeader addHeader(String prefix) throws SOAPException {
/* 113 */     if (prefix == null || prefix.equals("")) {
/* 114 */       prefix = getPrefix();
/*     */     }
/*     */     
/* 117 */     NameImpl headerName = getHeaderName(prefix);
/* 118 */     NameImpl bodyName = getBodyName(prefix);
/*     */     
/* 120 */     HeaderImpl header = null;
/* 121 */     SOAPElement firstChild = null;
/*     */     
/* 123 */     Iterator<SOAPElement> eachChild = getChildElementNodes();
/* 124 */     if (eachChild.hasNext()) {
/* 125 */       firstChild = eachChild.next();
/* 126 */       if (firstChild.getElementName().equals(headerName)) {
/* 127 */         log.severe("SAAJ0120.impl.header.already.exists");
/* 128 */         throw new SOAPExceptionImpl("Can't add a header when one is already present.");
/* 129 */       }  if (!firstChild.getElementName().equals(bodyName)) {
/* 130 */         log.severe("SAAJ0121.impl.invalid.first.child.of.envelope");
/* 131 */         throw new SOAPExceptionImpl("First child of Envelope must be either a Header or Body");
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     header = (HeaderImpl)createElement((Name)headerName);
/* 136 */     insertBefore(header, firstChild);
/* 137 */     header.ensureNamespaceIsDeclared(headerName.getPrefix(), headerName.getURI());
/*     */     
/* 139 */     return header;
/*     */   }
/*     */   
/*     */   protected void lookForHeader() throws SOAPException {
/* 143 */     NameImpl headerName = getHeaderName((String)null);
/*     */     
/* 145 */     HeaderImpl hdr = (HeaderImpl)findChild(headerName);
/* 146 */     this.header = hdr;
/*     */   }
/*     */   
/*     */   public SOAPHeader getHeader() throws SOAPException {
/* 150 */     lookForHeader();
/* 151 */     return this.header;
/*     */   }
/*     */   
/*     */   protected void lookForBody() throws SOAPException {
/* 155 */     NameImpl bodyName = getBodyName((String)null);
/*     */     
/* 157 */     BodyImpl bodyChildElement = (BodyImpl)findChild(bodyName);
/* 158 */     this.body = bodyChildElement;
/*     */   }
/*     */   
/*     */   public SOAPBody addBody() throws SOAPException {
/* 162 */     return addBody((String)null);
/*     */   }
/*     */   
/*     */   public SOAPBody addBody(String prefix) throws SOAPException {
/* 166 */     lookForBody();
/*     */     
/* 168 */     if (prefix == null || prefix.equals("")) {
/* 169 */       prefix = getPrefix();
/*     */     }
/*     */     
/* 172 */     if (this.body == null) {
/* 173 */       NameImpl bodyName = getBodyName(prefix);
/* 174 */       this.body = (BodyImpl)createElement((Name)bodyName);
/* 175 */       insertBefore(this.body, null);
/* 176 */       this.body.ensureNamespaceIsDeclared(bodyName.getPrefix(), bodyName.getURI());
/*     */     } else {
/* 178 */       log.severe("SAAJ0122.impl.body.already.exists");
/* 179 */       throw new SOAPExceptionImpl("Can't add a body when one is already present.");
/*     */     } 
/*     */     
/* 182 */     return this.body;
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/* 186 */     if (getBodyName((String)null).equals(name)) {
/* 187 */       return addBody(name.getPrefix());
/*     */     }
/* 189 */     if (getHeaderName((String)null).equals(name)) {
/* 190 */       return addHeader(name.getPrefix());
/*     */     }
/*     */     
/* 193 */     return super.addElement(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/* 197 */     if (getBodyName((String)null).equals(NameImpl.convertToName(name))) {
/* 198 */       return addBody(name.getPrefix());
/*     */     }
/* 200 */     if (getHeaderName((String)null).equals(NameImpl.convertToName(name))) {
/* 201 */       return addHeader(name.getPrefix());
/*     */     }
/*     */     
/* 204 */     return super.addElement(name);
/*     */   }
/*     */   
/*     */   public SOAPBody getBody() throws SOAPException {
/* 208 */     lookForBody();
/* 209 */     return this.body;
/*     */   }
/*     */   
/*     */   public Source getContent() {
/* 213 */     return new DOMSource(getOwnerDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Name createName(String localName, String prefix, String uri) throws SOAPException {
/* 223 */     if ("xmlns".equals(prefix)) {
/* 224 */       log.severe("SAAJ0123.impl.no.reserved.xmlns");
/* 225 */       throw new SOAPExceptionImpl("Cannot declare reserved xmlns prefix");
/*     */     } 
/*     */     
/* 228 */     if (prefix == null && "xmlns".equals(localName)) {
/* 229 */       log.severe("SAAJ0124.impl.qualified.name.cannot.be.xmlns");
/* 230 */       throw new SOAPExceptionImpl("Qualified name cannot be xmlns");
/*     */     } 
/*     */     
/* 233 */     return (Name)NameImpl.create(localName, prefix, uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public Name createName(String localName, String prefix) throws SOAPException {
/* 238 */     String namespace = getNamespaceURI(prefix);
/* 239 */     if (namespace == null) {
/* 240 */       log.log(Level.SEVERE, "SAAJ0126.impl.cannot.locate.ns", (Object[])new String[] { prefix });
/*     */ 
/*     */ 
/*     */       
/* 244 */       throw new SOAPExceptionImpl("Unable to locate namespace for prefix " + prefix);
/*     */     } 
/*     */     
/* 247 */     return (Name)NameImpl.create(localName, prefix, namespace);
/*     */   }
/*     */   
/*     */   public Name createName(String localName) throws SOAPException {
/* 251 */     return (Name)NameImpl.createFromUnqualifiedName(localName);
/*     */   }
/*     */   
/*     */   public void setOmitXmlDecl(String value) {
/* 255 */     this.omitXmlDecl = value;
/*     */   }
/*     */   
/*     */   public void setXmlDecl(String value) {
/* 259 */     this.xmlDecl = value;
/*     */   }
/*     */   
/*     */   private String getOmitXmlDecl() {
/* 263 */     return this.omitXmlDecl;
/*     */   }
/*     */   
/*     */   public void setCharsetEncoding(String value) {
/* 267 */     this.charset = value;
/*     */   }
/*     */   
/*     */   public void output(OutputStream out) throws IOException {
/*     */     try {
/* 272 */       Transformer transformer = EfficientStreamingTransformer.newTransformer();
/*     */ 
/*     */       
/* 275 */       transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */ 
/*     */ 
/*     */       
/* 279 */       transformer.setOutputProperty("encoding", this.charset);
/*     */ 
/*     */ 
/*     */       
/* 283 */       if (this.omitXmlDecl.equals("no") && this.xmlDecl == null) {
/* 284 */         this.xmlDecl = "<?xml version=\"" + getOwnerDocument().getXmlVersion() + "\" encoding=\"" + this.charset + "\" ?>";
/*     */       }
/*     */ 
/*     */       
/* 288 */       StreamResult result = new StreamResult(out);
/* 289 */       if (this.xmlDecl != null) {
/* 290 */         OutputStreamWriter writer = new OutputStreamWriter(out, this.charset);
/* 291 */         writer.write(this.xmlDecl);
/* 292 */         writer.flush();
/* 293 */         result = new StreamResult(writer);
/*     */       } 
/*     */       
/* 296 */       if (log.isLoggable(Level.FINE)) {
/* 297 */         log.log(Level.FINE, "SAAJ0190.impl.set.xml.declaration", (Object[])new String[] { this.omitXmlDecl });
/*     */         
/* 299 */         log.log(Level.FINE, "SAAJ0191.impl.set.encoding", (Object[])new String[] { this.charset });
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 304 */       transformer.transform(getContent(), result);
/* 305 */     } catch (Exception ex) {
/* 306 */       throw new IOException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void output(OutputStream out, boolean isFastInfoset) throws IOException {
/* 316 */     if (!isFastInfoset) {
/* 317 */       output(out);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 322 */         Source source = getContent();
/* 323 */         Transformer transformer = EfficientStreamingTransformer.newTransformer();
/* 324 */         transformer.transform(getContent(), FastInfosetReflection.FastInfosetResult_new(out));
/*     */       
/*     */       }
/* 327 */       catch (Exception ex) {
/* 328 */         throw new IOException(ex.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/* 367 */     log.log(Level.SEVERE, "SAAJ0146.impl.invalid.name.change.requested", new Object[] { this.elementQName.getLocalPart(), newName.getLocalPart() });
/*     */ 
/*     */ 
/*     */     
/* 371 */     throw new SOAPException("Cannot change name for " + this.elementQName.getLocalPart() + " to " + newName.getLocalPart());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\EnvelopeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */