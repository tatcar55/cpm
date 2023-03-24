/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.JAXBAssertionError;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.marshaller.IdentifiableObject;
/*     */ import com.sun.xml.bind.marshaller.Messages;
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ import com.sun.xml.bind.serializer.AbortSerializationException;
/*     */ import com.sun.xml.bind.serializer.Util;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.helpers.NotIdentifiableEventImpl;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
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
/*     */ public class SAXMarshaller
/*     */   implements XMLSerializer
/*     */ {
/*  47 */   private final AttributesImpl attributes = new AttributesImpl();
/*     */ 
/*     */   
/*     */   private final ContentHandler writer;
/*     */ 
/*     */   
/*     */   private final MarshallerImpl owner;
/*     */ 
/*     */   
/*  56 */   private final Set idReferencedObjects = new HashSet();
/*     */ 
/*     */   
/*  59 */   private final Set objectsWithId = new HashSet();
/*     */   
/*     */   private JAXBObject currentTarget;
/*     */   
/*     */   private final NamespaceContextImpl nsContext;
/*     */   
/*     */   private String[] elementStack;
/*     */   
/*     */   private int elementLen;
/*     */   
/*     */   private final PrefixCallback startPrefixCallback;
/*     */   
/*     */   private final PrefixCallback endPrefixCallback;
/*     */   
/*     */   private final StringBuffer textBuf;
/*     */   private String attNamespaceUri;
/*     */   private String attLocalName;
/*     */   
/*     */   public NamespaceContext2 getNamespaceContext() {
/*  78 */     return this.nsContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXMarshaller(ContentHandler _writer, NamespacePrefixMapper prefixMapper, MarshallerImpl _owner) {
/*  87 */     this.elementStack = new String[16];
/*  88 */     this.elementLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.startPrefixCallback = new PrefixCallback() { private final SAXMarshaller this$0;
/*     */         public void onPrefixMapping(String prefix, String nsUri) throws SAXException {
/* 149 */           SAXMarshaller.this.writer.startPrefixMapping(prefix, nsUri);
/*     */         } }
/*     */       ;
/* 152 */     this.endPrefixCallback = new PrefixCallback() { private final SAXMarshaller this$0;
/*     */         public void onPrefixMapping(String prefix, String nsUri) throws SAXException {
/* 154 */           SAXMarshaller.this.writer.endPrefixMapping(prefix);
/*     */         } }
/*     */       ;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     this.textBuf = new StringBuffer();
/*     */     this.writer = _writer;
/*     */     this.owner = _owner;
/*     */     this.nsContext = new NamespaceContextImpl((prefixMapper != null) ? prefixMapper : defaultNamespacePrefixMapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pushElement(String uri, String local) {
/*     */     if (this.elementStack.length == this.elementLen) {
/*     */       String[] buf = new String[this.elementStack.length * 2];
/*     */       System.arraycopy(this.elementStack, 0, buf, 0, this.elementStack.length);
/*     */       this.elementStack = buf;
/*     */     } 
/*     */     this.elementStack[this.elementLen++] = uri;
/*     */     this.elementStack[this.elementLen++] = local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void popElement() {
/*     */     this.elementLen -= 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCurrentElementUri() {
/*     */     return this.elementStack[this.elementLen - 2];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCurrentElementLocal() {
/*     */     return this.elementStack[this.elementLen - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(String text, String fieldName) throws SAXException {
/* 271 */     if (text == null) {
/* 272 */       reportError(Util.createMissingObjectError(this.currentTarget, fieldName));
/*     */       
/*     */       return;
/*     */     } 
/* 276 */     if (this.textBuf.length() != 0)
/* 277 */       this.textBuf.append(' '); 
/* 278 */     this.textBuf.append(text);
/*     */   }
/*     */   public void startElement(String uri, String local) throws SAXException { boolean isRoot = false; String suggestion = null; if (this.elementLen == 0) { isRoot = true; suggestion = ""; }  writePendingText(); this.nsContext.startElement(); pushElement(uri, local); this.nsContext.declareNamespace(uri, suggestion, false); if (isRoot) { String[] uris = this.nsContext.getNamespacePrefixMapper().getPreDeclaredNamespaceUris(); if (uris != null)
/*     */         for (int i = 0; i < uris.length; i++) { if (uris[i] != null)
/*     */             this.nsContext.declareNamespace(uris[i], null, false);  }   }  }
/*     */   public void endNamespaceDecls() throws SAXException { this.nsContext.endNamespaceDecls(); }
/*     */   public void endAttributes() throws SAXException { String qname, uri = getCurrentElementUri(); String local = getCurrentElementLocal(); String prefix = this.nsContext.getPrefix(uri); _assert((prefix != null)); if (prefix.length() != 0) { qname = prefix + ':' + local; } else { qname = local; }
/*     */      this.nsContext.iterateDeclaredPrefixes(this.startPrefixCallback); this.writer.startElement(uri, local, qname, this.attributes); this.attributes.clear(); this.textBuf.setLength(0); } public void endElement() throws SAXException { String qname; writePendingText(); String uri = getCurrentElementUri(); String local = getCurrentElementLocal(); String prefix = this.nsContext.getPrefix(uri); _assert((prefix != null)); if (prefix.length() != 0) { qname = prefix + ':' + local; }
/*     */     else { qname = local; }
/* 287 */      this.writer.endElement(uri, local, qname); this.nsContext.iterateDeclaredPrefixes(this.endPrefixCallback); popElement(); this.textBuf.setLength(0); this.nsContext.endElement(); } private void writePendingText() throws SAXException { int len = this.textBuf.length();
/*     */     
/* 289 */     if (len != 0) {
/* 290 */       this.writer.characters(this.textBuf.toString().toCharArray(), 0, len);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startAttribute(String uri, String local) {
/* 309 */     this.textBuf.setLength(0);
/*     */ 
/*     */     
/* 312 */     this.attNamespaceUri = uri;
/* 313 */     this.attLocalName = local;
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
/*     */   public void endAttribute() {
/*     */     String qname;
/* 333 */     if (this.attNamespaceUri.length() == 0) {
/*     */       
/* 335 */       qname = this.attLocalName;
/*     */     } else {
/* 337 */       qname = this.nsContext.declareNamespace(this.attNamespaceUri, null, true) + ':' + this.attLocalName;
/*     */     } 
/*     */     
/* 340 */     this.attributes.addAttribute(this.attNamespaceUri, this.attLocalName, qname, "CDATA", this.textBuf.toString());
/*     */   }
/*     */   
/*     */   public String onID(IdentifiableObject owner, String value) throws SAXException {
/* 344 */     this.objectsWithId.add(owner);
/* 345 */     return value;
/*     */   }
/*     */   public String onIDREF(IdentifiableObject obj) throws SAXException {
/* 348 */     this.idReferencedObjects.add(obj);
/* 349 */     String id = obj.____jaxb____getId();
/* 350 */     if (id == null) {
/* 351 */       reportError(new NotIdentifiableEventImpl(1, Messages.format("SAXMarshaller.NotIdentifiable"), new ValidationEventLocatorImpl(obj)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 356 */     return id;
/*     */   }
/*     */ 
/*     */   
/*     */   void reconcileID() throws AbortSerializationException {
/* 361 */     this.idReferencedObjects.removeAll(this.objectsWithId);
/*     */     
/* 363 */     for (Iterator itr = this.idReferencedObjects.iterator(); itr.hasNext(); ) {
/* 364 */       IdentifiableObject o = itr.next();
/* 365 */       reportError(new NotIdentifiableEventImpl(1, Messages.format("SAXMarshaller.DanglingIDREF", o.____jaxb____getId()), new ValidationEventLocatorImpl(o)));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     this.idReferencedObjects.clear();
/* 373 */     this.objectsWithId.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void childAsBody(JAXBObject o, String fieldName) throws SAXException {
/* 379 */     if (o == null) {
/*     */ 
/*     */       
/* 382 */       reportMissingObjectError(fieldName);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 388 */     JAXBObject oldTarget = this.currentTarget;
/* 389 */     this.currentTarget = o;
/*     */     
/* 391 */     this.owner.context.getGrammarInfo().castToXMLSerializable(o).serializeBody(this);
/*     */     
/* 393 */     this.currentTarget = oldTarget;
/*     */   }
/*     */   
/*     */   public void childAsAttributes(JAXBObject o, String fieldName) throws SAXException {
/* 397 */     if (o == null) {
/* 398 */       reportMissingObjectError(fieldName);
/*     */       
/*     */       return;
/*     */     } 
/* 402 */     JAXBObject oldTarget = this.currentTarget;
/* 403 */     this.currentTarget = o;
/*     */     
/* 405 */     this.owner.context.getGrammarInfo().castToXMLSerializable(o).serializeAttributes(this);
/*     */     
/* 407 */     this.currentTarget = oldTarget;
/*     */   }
/*     */   
/*     */   public void childAsURIs(JAXBObject o, String fieldName) throws SAXException {
/* 411 */     if (o == null) {
/* 412 */       reportMissingObjectError(fieldName);
/*     */       
/*     */       return;
/*     */     } 
/* 416 */     JAXBObject oldTarget = this.currentTarget;
/* 417 */     this.currentTarget = o;
/*     */     
/* 419 */     this.owner.context.getGrammarInfo().castToXMLSerializable(o).serializeURIs(this);
/*     */     
/* 421 */     this.currentTarget = oldTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reportError(ValidationEvent ve) throws AbortSerializationException {
/*     */     ValidationEventHandler handler;
/*     */     try {
/* 429 */       handler = this.owner.getEventHandler();
/* 430 */     } catch (JAXBException e) {
/* 431 */       throw new AbortSerializationException(e);
/*     */     } 
/*     */     
/* 434 */     if (!handler.handleEvent(ve)) {
/* 435 */       if (ve.getLinkedException() instanceof Exception) {
/* 436 */         throw new AbortSerializationException((Exception)ve.getLinkedException());
/*     */       }
/* 438 */       throw new AbortSerializationException(ve.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportMissingObjectError(String fieldName) throws SAXException {
/* 444 */     reportError(Util.createMissingObjectError(this.currentTarget, fieldName));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void _assert(boolean b) {
/* 449 */     if (!b) {
/* 450 */       throw new JAXBAssertionError();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 457 */   private static NamespacePrefixMapper defaultNamespacePrefixMapper = new NamespacePrefixMapper() {
/*     */       public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 459 */         if (namespaceUri.equals("http://www.w3.org/2001/XMLSchema-instance"))
/* 460 */           return "xsi"; 
/* 461 */         return suggestion;
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\SAXMarshaller.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */