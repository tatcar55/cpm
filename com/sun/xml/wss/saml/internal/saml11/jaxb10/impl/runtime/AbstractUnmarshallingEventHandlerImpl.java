/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.JAXBAssertionError;
/*     */ import com.sun.xml.bind.unmarshaller.Messages;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.bind.Element;
/*     */ import javax.xml.bind.ParseConversionEvent;
/*     */ import javax.xml.bind.helpers.ParseConversionEventImpl;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*     */ import org.xml.sax.Attributes;
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
/*     */ public abstract class AbstractUnmarshallingEventHandlerImpl
/*     */   implements UnmarshallingEventHandler
/*     */ {
/*     */   public final UnmarshallingContext context;
/*     */   private final String stateTextTypes;
/*     */   public int state;
/*     */   
/*     */   public AbstractUnmarshallingEventHandlerImpl(UnmarshallingContext _ctxt, String _stateTextTypes) {
/*  42 */     this.context = _ctxt;
/*  43 */     this.stateTextTypes = _stateTextTypes;
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
/*     */   public void enterElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/*  60 */     unexpectedEnterElement(uri, local, qname, atts);
/*     */   }
/*     */   public void leaveElement(String uri, String local, String qname) throws SAXException {
/*  63 */     unexpectedLeaveElement(uri, local, qname);
/*     */   }
/*     */   public final void text(String text) throws SAXException {
/*  66 */     if (isListState()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  71 */       StringTokenizer tokens = new StringTokenizer(text);
/*  72 */       if (tokens.countTokens() == 1) {
/*  73 */         handleText(text);
/*     */       } else {
/*  75 */         while (tokens.hasMoreTokens())
/*     */         {
/*     */           
/*  78 */           this.context.getCurrentHandler().text(tokens.nextToken());
/*     */         }
/*     */       } 
/*     */     } else {
/*  82 */       handleText(text);
/*     */     } 
/*     */   }
/*     */   protected void handleText(String s) throws SAXException {
/*  86 */     unexpectedText(s);
/*     */   }
/*     */   public void enterAttribute(String uri, String local, String qname) throws SAXException {
/*  89 */     unexpectedEnterAttribute(uri, local, qname);
/*     */   }
/*     */   public void leaveAttribute(String uri, String local, String qname) throws SAXException {
/*  92 */     unexpectedLeaveAttribute(uri, local, qname);
/*     */   }
/*     */   public void leaveChild(int nextState) throws SAXException {
/*  95 */     this.state = nextState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isListState() {
/* 103 */     return (this.stateTextTypes.charAt(this.state) == 'L');
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
/*     */   protected void handleUnexpectedTextException(String text, RuntimeException e) throws SAXException {
/* 121 */     reportError(Messages.format("ContentHandlerEx.UnexpectedText", text), e, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleGenericException(Exception e) throws SAXException {
/* 128 */     reportError(e.getMessage(), e, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void dump() {
/* 133 */     System.err.println("state is :" + this.state);
/*     */   }
/*     */   private void reportError(String msg, boolean canRecover) throws SAXException {
/* 136 */     reportError(msg, null, canRecover);
/*     */   }
/*     */   private void reportError(String msg, Exception nested, boolean canRecover) throws SAXException {
/* 139 */     this.context.handleEvent(new ValidationEventImpl(canRecover ? 1 : 2, msg, new ValidationEventLocatorImpl(this.context.getLocator()), nested), canRecover);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void unexpectedEnterElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/* 147 */     reportError(Messages.format("ContentHandlerEx.UnexpectedEnterElement", uri, local), true);
/*     */     
/* 149 */     this.context.pushContentHandler(new Discarder(this.context), this.state);
/* 150 */     this.context.getCurrentHandler().enterElement(uri, local, qname, atts);
/*     */   }
/*     */   protected final void unexpectedLeaveElement(String uri, String local, String qname) throws SAXException {
/* 153 */     reportError(Messages.format("ContentHandlerEx.UnexpectedLeaveElement", uri, local), false);
/*     */   }
/*     */   protected final void unexpectedEnterAttribute(String uri, String local, String qname) throws SAXException {
/* 156 */     reportError(Messages.format("ContentHandlerEx.UnexpectedEnterAttribute", uri, local), false);
/*     */   }
/*     */   protected final void unexpectedLeaveAttribute(String uri, String local, String qname) throws SAXException {
/* 159 */     reportError(Messages.format("ContentHandlerEx.UnexpectedLeaveAttribute", uri, local), false);
/*     */   }
/*     */   
/*     */   protected final void unexpectedText(String str) throws SAXException {
/* 163 */     str = str.replace('\r', ' ').replace('\n', ' ').replace('\t', ' ').trim();
/*     */     
/* 165 */     reportError(Messages.format("ContentHandlerEx.UnexpectedText", str), true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void unexpectedLeaveChild() throws SAXException {
/* 171 */     dump();
/* 172 */     throw new JAXBAssertionError(Messages.format("ContentHandlerEx.UnexpectedLeaveChild"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleParseConversionException(Exception e) throws SAXException {
/* 180 */     if (e instanceof RuntimeException) {
/* 181 */       throw (RuntimeException)e;
/*     */     }
/*     */     
/* 184 */     ParseConversionEvent pce = new ParseConversionEventImpl(1, e.getMessage(), new ValidationEventLocatorImpl(this.context.getLocator()), e);
/*     */ 
/*     */     
/* 187 */     this.context.handleEvent(pce, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UnmarshallingEventHandler spawnChild(Class clazz, int memento) {
/*     */     UnmarshallableObject child;
/*     */     try {
/* 200 */       child = clazz.newInstance();
/* 201 */     } catch (InstantiationException e) {
/* 202 */       throw new InstantiationError(e.getMessage());
/* 203 */     } catch (IllegalAccessException e) {
/* 204 */       throw new IllegalAccessError(e.getMessage());
/*     */     } 
/*     */     
/* 207 */     UnmarshallingEventHandler handler = child.createUnmarshaller(this.context);
/* 208 */     this.context.pushContentHandler(handler, memento);
/* 209 */     return handler;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object spawnChildFromEnterElement(Class clazz, int memento, String uri, String local, String qname, Attributes atts) throws SAXException {
/* 214 */     UnmarshallingEventHandler ueh = spawnChild(clazz, memento);
/* 215 */     ueh.enterElement(uri, local, qname, atts);
/* 216 */     return ueh.owner();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object spawnChildFromEnterAttribute(Class clazz, int memento, String uri, String local, String qname) throws SAXException {
/* 221 */     UnmarshallingEventHandler ueh = spawnChild(clazz, memento);
/* 222 */     ueh.enterAttribute(uri, local, qname);
/* 223 */     return ueh.owner();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object spawnChildFromText(Class clazz, int memento, String value) throws SAXException {
/* 228 */     UnmarshallingEventHandler ueh = spawnChild(clazz, memento);
/* 229 */     ueh.text(value);
/* 230 */     return ueh.owner();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Object spawnChildFromLeaveElement(Class clazz, int memento, String uri, String local, String qname) throws SAXException {
/* 236 */     UnmarshallingEventHandler ueh = spawnChild(clazz, memento);
/* 237 */     ueh.leaveElement(uri, local, qname);
/* 238 */     return ueh.owner();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object spawnChildFromLeaveAttribute(Class clazz, int memento, String uri, String local, String qname) throws SAXException {
/* 243 */     UnmarshallingEventHandler ueh = spawnChild(clazz, memento);
/* 244 */     ueh.leaveAttribute(uri, local, qname);
/* 245 */     return ueh.owner();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Element spawnWildcard(int memento, String uri, String local, String qname, Attributes atts) throws SAXException {
/* 250 */     UnmarshallingEventHandler ueh = this.context.getGrammarInfo().createUnmarshaller(uri, local, this.context);
/*     */     
/* 252 */     if (ueh != null) {
/* 253 */       this.context.pushContentHandler(ueh, memento);
/* 254 */       ueh.enterElement(uri, local, qname, atts);
/* 255 */       return (Element)ueh.owner();
/*     */     } 
/*     */ 
/*     */     
/* 259 */     this.context.pushContentHandler(new Discarder(this.context), memento);
/* 260 */     this.context.getCurrentHandler().enterElement(uri, local, qname, atts);
/* 261 */     return null;
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
/*     */   protected final void spawnHandlerFromEnterElement(UnmarshallingEventHandler unm, int memento, String uri, String local, String qname, Attributes atts) throws SAXException {
/* 276 */     this.context.pushContentHandler(unm, memento);
/* 277 */     unm.enterElement(uri, local, qname, atts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void spawnHandlerFromEnterAttribute(UnmarshallingEventHandler unm, int memento, String uri, String local, String qname) throws SAXException {
/* 284 */     this.context.pushContentHandler(unm, memento);
/* 285 */     unm.enterAttribute(uri, local, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void spawnHandlerFromFromText(UnmarshallingEventHandler unm, int memento, String value) throws SAXException {
/* 292 */     this.context.pushContentHandler(unm, memento);
/* 293 */     unm.text(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void spawnHandlerFromLeaveElement(UnmarshallingEventHandler unm, int memento, String uri, String local, String qname) throws SAXException {
/* 300 */     this.context.pushContentHandler(unm, memento);
/* 301 */     unm.leaveElement(uri, local, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void spawnHandlerFromLeaveAttribute(UnmarshallingEventHandler unm, int memento, String uri, String local, String qname) throws SAXException {
/* 308 */     this.context.pushContentHandler(unm, memento);
/* 309 */     unm.leaveAttribute(uri, local, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void spawnHandlerFromText(UnmarshallingEventHandler unm, int memento, String text) throws SAXException {
/* 316 */     this.context.pushContentHandler(unm, memento);
/* 317 */     unm.text(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void revertToParentFromEnterElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/* 328 */     this.context.popContentHandler();
/* 329 */     this.context.getCurrentHandler().enterElement(uri, local, qname, atts);
/*     */   }
/*     */   
/*     */   protected final void revertToParentFromLeaveElement(String uri, String local, String qname) throws SAXException {
/* 333 */     this.context.popContentHandler();
/* 334 */     this.context.getCurrentHandler().leaveElement(uri, local, qname);
/*     */   }
/*     */   
/*     */   protected final void revertToParentFromEnterAttribute(String uri, String local, String qname) throws SAXException {
/* 338 */     this.context.popContentHandler();
/* 339 */     this.context.getCurrentHandler().enterAttribute(uri, local, qname);
/*     */   }
/*     */   
/*     */   protected final void revertToParentFromLeaveAttribute(String uri, String local, String qname) throws SAXException {
/* 343 */     this.context.popContentHandler();
/* 344 */     this.context.getCurrentHandler().leaveAttribute(uri, local, qname);
/*     */   }
/*     */   
/*     */   protected final void revertToParentFromText(String value) throws SAXException {
/* 348 */     this.context.popContentHandler();
/* 349 */     this.context.getCurrentHandler().text(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\AbstractUnmarshallingEventHandlerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */