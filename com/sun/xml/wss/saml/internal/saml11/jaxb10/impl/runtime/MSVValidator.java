/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.msv.grammar.IDContextProvider2;
/*     */ import com.sun.msv.util.LightStack;
/*     */ import com.sun.msv.util.StartTagInfo;
/*     */ import com.sun.msv.util.StringRef;
/*     */ import com.sun.msv.verifier.Acceptor;
/*     */ import com.sun.msv.verifier.regexp.StringToken;
/*     */ import com.sun.xml.bind.JAXBAssertionError;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.marshaller.IdentifiableObject;
/*     */ import com.sun.xml.bind.serializer.AbortSerializationException;
/*     */ import com.sun.xml.bind.serializer.Util;
/*     */ import com.sun.xml.bind.validator.Messages;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import org.relaxng.datatype.Datatype;
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
/*     */ public class MSVValidator
/*     */   implements XMLSerializer, IDContextProvider2
/*     */ {
/*     */   private Acceptor acceptor;
/*     */   private final ValidationContext context;
/*     */   private final ValidatableObject target;
/*     */   final DefaultJAXBContextImpl jaxbContext;
/*  55 */   private final LightStack stack = new LightStack(); private StringBuffer buf; private String attNamespaceUri; private String attLocalName;
/*     */   
/*     */   public NamespaceContext2 getNamespaceContext() {
/*  58 */     return this.context.getNamespaceContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean insideAttribute;
/*     */ 
/*     */ 
/*     */   
/*     */   private String currentElementUri;
/*     */ 
/*     */   
/*     */   private String currentElementLocalName;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validate(DefaultJAXBContextImpl jaxbCtx, ValidationContext context, ValidatableObject vo) throws SAXException {
/*     */     try {
/*  77 */       (new MSVValidator(jaxbCtx, context, vo))._validate();
/*  78 */     } catch (RuntimeException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  84 */       context.reportEvent(vo, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void _validate() throws SAXException {
/*  90 */     this.context.getNamespaceContext().startElement();
/*     */ 
/*     */     
/*  93 */     this.target.serializeURIs(this);
/*     */     
/*  95 */     endNamespaceDecls();
/*     */     
/*  97 */     this.target.serializeAttributes(this);
/*     */     
/*  99 */     endAttributes();
/*     */ 
/*     */     
/* 102 */     this.target.serializeBody(this);
/* 103 */     writePendingText();
/*     */     
/* 105 */     this.context.getNamespaceContext().endElement();
/*     */     
/* 107 */     if (!this.acceptor.isAcceptState(null)) {
/*     */ 
/*     */       
/* 110 */       StringRef ref = new StringRef();
/* 111 */       this.acceptor.isAcceptState(ref);
/* 112 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endNamespaceDecls() throws SAXException {
/* 117 */     this.context.getNamespaceContext().endNamespaceDecls();
/*     */   }
/*     */   
/*     */   public void endAttributes() throws SAXException {
/* 121 */     if (!this.acceptor.onEndAttributes(null, null)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       StringRef ref = new StringRef();
/* 127 */       StartTagInfo sti = new StartTagInfo(this.currentElementUri, this.currentElementLocalName, this.currentElementLocalName, emptyAttributes, this);
/*     */ 
/*     */       
/* 130 */       this.acceptor.onEndAttributes(sti, ref);
/* 131 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */   }
/*     */   
/*     */   private MSVValidator(DefaultJAXBContextImpl _jaxbCtx, ValidationContext _ctxt, ValidatableObject vo) {
/* 136 */     this.buf = new StringBuffer(); this.jaxbContext = _jaxbCtx;
/*     */     this.acceptor = vo.createRawValidator().createAcceptor();
/*     */     this.context = _ctxt;
/* 139 */     this.target = vo; } public final void text(String text, String fieldName) throws SAXException { if (text == null) {
/* 140 */       reportMissingObjectError(fieldName);
/*     */       
/*     */       return;
/*     */     } 
/* 144 */     if (this.buf.length() != 0)
/* 145 */       this.buf.append(' '); 
/* 146 */     this.buf.append(text); }
/*     */ 
/*     */   
/*     */   public void reportMissingObjectError(String fieldName) throws SAXException {
/* 150 */     reportError(Util.createMissingObjectError(this.target, fieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startAttribute(String uri, String local) {
/* 161 */     this.attNamespaceUri = uri;
/* 162 */     this.attLocalName = local;
/* 163 */     this.insideAttribute = true;
/*     */   }
/*     */   
/*     */   public void endAttribute() throws SAXException {
/* 167 */     this.insideAttribute = false;
/* 168 */     if (!this.acceptor.onAttribute2(this.attNamespaceUri, this.attLocalName, this.attLocalName, this.buf.toString(), this, null, null)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       StringRef ref = new StringRef();
/* 177 */       this.acceptor.onAttribute2(this.attNamespaceUri, this.attLocalName, this.attLocalName, this.buf.toString(), this, ref, null);
/*     */ 
/*     */       
/* 180 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */     
/* 183 */     this.buf = new StringBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   private void writePendingText() throws SAXException {
/* 188 */     if (!this.acceptor.onText2(this.buf.toString(), this, null, null)) {
/*     */ 
/*     */       
/* 191 */       StringRef ref = new StringRef();
/* 192 */       this.acceptor.onText2(this.buf.toString(), this, ref, null);
/* 193 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */     
/* 196 */     if (this.buf.length() > 1024) {
/* 197 */       this.buf = new StringBuffer();
/*     */     } else {
/* 199 */       this.buf.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String local) throws SAXException {
/* 206 */     writePendingText();
/*     */     
/* 208 */     this.context.getNamespaceContext().startElement();
/*     */     
/* 210 */     this.stack.push(this.acceptor);
/*     */     
/* 212 */     StartTagInfo sti = new StartTagInfo(uri, local, local, emptyAttributes, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     Acceptor child = this.acceptor.createChildAcceptor(sti, null);
/* 219 */     if (child == null) {
/*     */ 
/*     */       
/* 222 */       StringRef ref = new StringRef();
/* 223 */       child = this.acceptor.createChildAcceptor(sti, ref);
/* 224 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */     
/* 227 */     this.currentElementUri = uri;
/* 228 */     this.currentElementLocalName = local;
/*     */     
/* 230 */     this.acceptor = child;
/*     */   }
/*     */   
/*     */   public void endElement() throws SAXException {
/* 234 */     writePendingText();
/*     */     
/* 236 */     if (!this.acceptor.isAcceptState(null)) {
/*     */ 
/*     */       
/* 239 */       StringRef ref = new StringRef();
/* 240 */       this.acceptor.isAcceptState(ref);
/* 241 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */ 
/*     */     
/* 245 */     Acceptor child = this.acceptor;
/* 246 */     this.acceptor = (Acceptor)this.stack.pop();
/* 247 */     if (!this.acceptor.stepForward(child, null)) {
/*     */ 
/*     */       
/* 250 */       StringRef ref = new StringRef();
/* 251 */       this.acceptor.stepForward(child, ref);
/*     */       
/* 253 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */     
/* 256 */     this.context.getNamespaceContext().endElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void childAsAttributes(JAXBObject o, String fieldName) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void childAsURIs(JAXBObject o, String fieldName) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   private static final AttributesImpl emptyAttributes = new AttributesImpl();
/*     */ 
/*     */   
/*     */   public static final String DUMMY_ELEMENT_NS = "http://java.sun.com/jaxb/xjc/dummy-elements";
/*     */ 
/*     */ 
/*     */   
/*     */   public void childAsBody(JAXBObject o, String fieldName) throws SAXException {
/* 284 */     ValidatableObject vo = this.jaxbContext.getGrammarInfo().castToValidatableObject(o);
/*     */     
/* 286 */     if (vo == null) {
/* 287 */       reportMissingObjectError(fieldName);
/*     */       
/*     */       return;
/*     */     } 
/* 291 */     if (this.insideAttribute) { childAsAttributeBody(vo, fieldName); }
/* 292 */     else { childAsElementBody(o, vo); }
/*     */   
/*     */   }
/*     */   private void childAsElementBody(Object o, ValidatableObject vo) throws SAXException {
/* 296 */     String intfName = vo.getPrimaryInterface().getName();
/* 297 */     intfName = intfName.replace('$', '.');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     StartTagInfo sti = new StartTagInfo("http://java.sun.com/jaxb/xjc/dummy-elements", intfName, intfName, emptyAttributes, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     Acceptor child = this.acceptor.createChildAcceptor(sti, null);
/* 332 */     if (child == null) {
/*     */       
/* 334 */       StringRef ref = new StringRef();
/* 335 */       child = this.acceptor.createChildAcceptor(sti, ref);
/* 336 */       this.context.reportEvent(this.target, ref.str);
/*     */     } 
/*     */     
/* 339 */     if (o instanceof RIElement) {
/* 340 */       RIElement rie = (RIElement)o;
/* 341 */       if (!child.onAttribute2(rie.____jaxb_ri____getNamespaceURI(), rie.____jaxb_ri____getLocalName(), rie.____jaxb_ri____getLocalName(), "", null, null, null))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 349 */         this.context.reportEvent(this.target, Messages.format("MSVValidator.IncorrectChildForWildcard", rie.____jaxb_ri____getNamespaceURI(), rie.____jaxb_ri____getLocalName()));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 355 */     child.onEndAttributes(sti, null);
/*     */ 
/*     */     
/* 358 */     if (!this.acceptor.stepForward(child, null))
/*     */     {
/*     */       
/* 361 */       throw new JAXBAssertionError();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 366 */     this.context.validate(vo);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void childAsAttributeBody(ValidatableObject vo, String fieldName) throws SAXException {
/* 410 */     text("\000" + vo.getPrimaryInterface().getName(), fieldName);
/*     */ 
/*     */     
/* 413 */     this.context.validate(vo);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reportError(ValidationEvent e) throws AbortSerializationException {
/* 418 */     this.context.reportEvent(this.target, e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String onID(IdentifiableObject owner, String value) throws SAXException {
/* 427 */     return this.context.onID(this.target, value);
/*     */   }
/*     */   public String onIDREF(IdentifiableObject value) throws SAXException {
/* 430 */     return this.context.onIDREF(this.target, value.____jaxb____getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseUri() {
/* 439 */     return null;
/*     */   }
/*     */   public boolean isUnparsedEntity(String entityName) {
/* 442 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isNotation(String notation) {
/* 446 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onID(Datatype dt, StringToken s) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String resolveNamespacePrefix(String prefix) {
/* 457 */     return this.context.getNamespaceContext().getNamespaceURI(prefix);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\MSVValidator.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */