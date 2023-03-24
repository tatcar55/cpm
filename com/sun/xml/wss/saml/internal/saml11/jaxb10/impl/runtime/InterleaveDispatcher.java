/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.JAXBAssertionError;
/*     */ import com.sun.xml.bind.unmarshaller.Tracer;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
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
/*     */ public abstract class InterleaveDispatcher
/*     */   implements UnmarshallingEventHandler
/*     */ {
/*     */   private final UnmarshallingContext parent;
/*     */   protected final Site[] sites;
/*     */   private boolean isJoining;
/*  45 */   private int nestLevel = 0;
/*     */ 
/*     */   
/*     */   private Site currentSite;
/*     */ 
/*     */   
/*     */   protected InterleaveDispatcher(UnmarshallingContext context, int size) {
/*  52 */     this.parent = context;
/*  53 */     this.sites = new Site[size];
/*  54 */     for (int i = 0; i < size; i++)
/*  55 */       this.sites[i] = new Site(); 
/*     */   }
/*     */   
/*     */   protected void init(UnmarshallingEventHandler[] handlers) {
/*  59 */     for (int i = 0; i < handlers.length; i++) {
/*  60 */       this.sites[i].pushContentHandler(handlers[i], 0);
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
/*     */   public Object owner() {
/*  88 */     if (this.nestLevel > 0) {
/*  89 */       return this.currentSite.getCurrentHandler().owner();
/*     */     }
/*  91 */     throw new JAXBAssertionError();
/*     */   }
/*     */   
/*     */   public void enterElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/*  95 */     if (this.nestLevel++ == 0) {
/*  96 */       int idx = getBranchForElement(uri, local);
/*  97 */       if (idx == -1) {
/*     */         
/*  99 */         joinByEnterElement(null, uri, local, qname, atts);
/*     */         return;
/*     */       } 
/* 102 */       this.currentSite = this.sites[idx];
/*     */     } 
/*     */     
/* 105 */     this.currentSite.getCurrentHandler().enterElement(uri, local, qname, atts);
/*     */   }
/*     */   private void joinByEnterElement(Site source, String uri, String local, String qname, Attributes atts) throws SAXException {
/* 108 */     if (this.isJoining)
/* 109 */       return;  this.isJoining = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     for (int i = 0; i < this.sites.length; i++) {
/* 115 */       if (this.sites[i] != source) {
/* 116 */         this.sites[i].getCurrentHandler().enterElement(uri, local, qname, atts);
/*     */       }
/*     */     } 
/* 119 */     this.parent.popContentHandler();
/* 120 */     this.parent.getCurrentHandler().enterElement(uri, local, qname, atts);
/*     */   }
/*     */ 
/*     */   
/*     */   public void leaveElement(String uri, String local, String qname) throws SAXException {
/* 125 */     if (this.nestLevel == 0) {
/* 126 */       joinByLeaveElement(null, uri, local, qname);
/*     */     } else {
/* 128 */       this.currentSite.getCurrentHandler().leaveElement(uri, local, qname);
/*     */ 
/*     */ 
/*     */       
/* 132 */       this.nestLevel--;
/*     */     } 
/*     */   }
/*     */   private void joinByLeaveElement(Site source, String uri, String local, String qname) throws SAXException {
/* 136 */     if (this.isJoining)
/* 137 */       return;  this.isJoining = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     for (int i = 0; i < this.sites.length; i++) {
/* 143 */       if (this.sites[i] != source) {
/* 144 */         this.sites[i].getCurrentHandler().leaveElement(uri, local, qname);
/*     */       }
/*     */     } 
/* 147 */     this.parent.popContentHandler();
/* 148 */     this.parent.getCurrentHandler().leaveElement(uri, local, qname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void text(String s) throws SAXException {
/* 153 */     if (this.nestLevel == 0) {
/* 154 */       int idx = getBranchForText();
/* 155 */       if (idx == -1) {
/* 156 */         if (s.trim().length() != 0)
/*     */         {
/*     */           
/* 159 */           joinByText(null, s);
/*     */         }
/*     */         return;
/*     */       } 
/* 163 */       this.currentSite = this.sites[idx];
/*     */     } 
/*     */     
/* 166 */     this.currentSite.getCurrentHandler().text(s);
/*     */   }
/*     */   private void joinByText(Site source, String s) throws SAXException {
/* 169 */     if (this.isJoining)
/* 170 */       return;  this.isJoining = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     for (int i = 0; i < this.sites.length; i++) {
/* 176 */       if (this.sites[i] != source) {
/* 177 */         this.sites[i].getCurrentHandler().text(s);
/*     */       }
/*     */     } 
/* 180 */     this.parent.popContentHandler();
/* 181 */     this.parent.getCurrentHandler().text(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enterAttribute(String uri, String local, String qname) throws SAXException {
/* 186 */     if (this.nestLevel++ == 0) {
/* 187 */       int idx = getBranchForAttribute(uri, local);
/* 188 */       if (idx == -1) {
/*     */         
/* 190 */         joinByEnterAttribute(null, uri, local, qname);
/*     */         return;
/*     */       } 
/* 193 */       this.currentSite = this.sites[idx];
/*     */     } 
/*     */     
/* 196 */     this.currentSite.getCurrentHandler().enterAttribute(uri, local, qname);
/*     */   }
/*     */   private void joinByEnterAttribute(Site source, String uri, String local, String qname) throws SAXException {
/* 199 */     if (this.isJoining)
/* 200 */       return;  this.isJoining = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     for (int i = 0; i < this.sites.length; i++) {
/* 206 */       if (this.sites[i] != source) {
/* 207 */         this.sites[i].getCurrentHandler().enterAttribute(uri, local, qname);
/*     */       }
/*     */     } 
/* 210 */     this.parent.popContentHandler();
/* 211 */     this.parent.getCurrentHandler().enterAttribute(uri, local, qname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void leaveAttribute(String uri, String local, String qname) throws SAXException {
/* 216 */     if (this.nestLevel == 0) {
/* 217 */       joinByLeaveAttribute(null, uri, local, qname);
/*     */     } else {
/* 219 */       this.nestLevel--;
/* 220 */       this.currentSite.getCurrentHandler().leaveAttribute(uri, local, qname);
/*     */     } 
/*     */   }
/*     */   private void joinByLeaveAttribute(Site source, String uri, String local, String qname) throws SAXException {
/* 224 */     if (this.isJoining)
/* 225 */       return;  this.isJoining = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     for (int i = 0; i < this.sites.length; i++) {
/* 231 */       if (this.sites[i] != source) {
/* 232 */         this.sites[i].getCurrentHandler().leaveAttribute(uri, local, qname);
/*     */       }
/*     */     } 
/* 235 */     this.parent.popContentHandler();
/* 236 */     this.parent.getCurrentHandler().leaveAttribute(uri, local, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void leaveChild(int nextState) throws SAXException {
/* 242 */     throw new JAXBAssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getBranchForElement(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getBranchForAttribute(String paramString1, String paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getBranchForText();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Site
/*     */     implements UnmarshallingContext, UnmarshallingEventHandler
/*     */   {
/* 280 */     private UnmarshallingEventHandler[] handlers = new UnmarshallingEventHandler[8];
/* 281 */     private int[] mementos = new int[8];
/* 282 */     private int handlerLen = 0; private final InterleaveDispatcher this$0;
/*     */     
/*     */     private Site() {
/* 285 */       pushContentHandler(this, 0);
/*     */     }
/*     */     
/*     */     public void pushContentHandler(UnmarshallingEventHandler handler, int memento) {
/* 289 */       if (this.handlerLen == this.handlers.length) {
/*     */         
/* 291 */         UnmarshallingEventHandler[] h = new UnmarshallingEventHandler[this.handlerLen * 2];
/* 292 */         int[] m = new int[this.handlerLen * 2];
/* 293 */         System.arraycopy(this.handlers, 0, h, 0, this.handlerLen);
/* 294 */         System.arraycopy(this.mementos, 0, m, 0, this.handlerLen);
/* 295 */         this.handlers = h;
/* 296 */         this.mementos = m;
/*     */       } 
/* 298 */       this.handlers[this.handlerLen] = handler;
/* 299 */       this.mementos[this.handlerLen] = memento;
/* 300 */       this.handlerLen++;
/*     */     }
/*     */     
/*     */     public void popContentHandler() throws SAXException {
/* 304 */       this.handlerLen--;
/* 305 */       this.handlers[this.handlerLen] = null;
/* 306 */       getCurrentHandler().leaveChild(this.mementos[this.handlerLen]);
/*     */     }
/*     */     
/*     */     public UnmarshallingEventHandler getCurrentHandler() {
/* 310 */       return this.handlers[this.handlerLen - 1];
/*     */     }
/*     */ 
/*     */     
/*     */     public Object owner() {
/* 315 */       return null;
/*     */     } public void enterElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/* 317 */       InterleaveDispatcher.this.joinByEnterElement(this, uri, local, qname, atts);
/*     */     }
/*     */     public void leaveElement(String uri, String local, String qname) throws SAXException {
/* 320 */       InterleaveDispatcher.this.joinByLeaveElement(this, uri, local, qname);
/*     */     }
/*     */     public void enterAttribute(String uri, String local, String qname) throws SAXException {
/* 323 */       InterleaveDispatcher.this.joinByEnterAttribute(this, uri, local, qname);
/*     */     }
/*     */     public void leaveAttribute(String uri, String local, String qname) throws SAXException {
/* 326 */       InterleaveDispatcher.this.joinByLeaveAttribute(this, uri, local, qname);
/*     */     }
/*     */     public void text(String s) throws SAXException {
/* 329 */       InterleaveDispatcher.this.joinByText(this, s);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveChild(int nextState) throws SAXException {}
/*     */ 
/*     */     
/*     */     public void addPatcher(Runnable job) {
/* 338 */       InterleaveDispatcher.this.parent.addPatcher(job);
/*     */     }
/*     */     
/*     */     public String addToIdTable(String id) {
/* 342 */       return InterleaveDispatcher.this.parent.addToIdTable(id);
/*     */     }
/*     */     
/*     */     public void consumeAttribute(int idx) throws SAXException {
/* 346 */       InterleaveDispatcher.this.parent.consumeAttribute(idx);
/*     */     }
/*     */     
/*     */     public String eatAttribute(int idx) throws SAXException {
/* 350 */       return InterleaveDispatcher.this.parent.eatAttribute(idx);
/*     */     }
/*     */     
/*     */     public int getAttribute(String uri, String name) {
/* 354 */       return InterleaveDispatcher.this.parent.getAttribute(uri, name);
/*     */     }
/*     */     
/*     */     public String getBaseUri() {
/* 358 */       return InterleaveDispatcher.this.parent.getBaseUri();
/*     */     }
/*     */     
/*     */     public GrammarInfo getGrammarInfo() {
/* 362 */       return InterleaveDispatcher.this.parent.getGrammarInfo();
/*     */     }
/*     */     
/*     */     public Locator getLocator() {
/* 366 */       return InterleaveDispatcher.this.parent.getLocator();
/*     */     }
/*     */     
/*     */     public String getNamespaceURI(String prefix) {
/* 370 */       return InterleaveDispatcher.this.parent.getNamespaceURI(prefix);
/*     */     }
/*     */     
/*     */     public Object getObjectFromId(String id) {
/* 374 */       return InterleaveDispatcher.this.parent.getObjectFromId(id);
/*     */     }
/*     */     
/*     */     public String getPrefix(String namespaceURI) {
/* 378 */       return InterleaveDispatcher.this.parent.getPrefix(namespaceURI);
/*     */     }
/*     */     
/*     */     public Iterator getPrefixes(String namespaceURI) {
/* 382 */       return InterleaveDispatcher.this.parent.getPrefixes(namespaceURI);
/*     */     }
/*     */     
/*     */     public Tracer getTracer() {
/* 386 */       return InterleaveDispatcher.this.parent.getTracer();
/*     */     }
/*     */     
/*     */     public Attributes getUnconsumedAttributes() {
/* 390 */       return InterleaveDispatcher.this.parent.getUnconsumedAttributes();
/*     */     }
/*     */     
/*     */     public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException {
/* 394 */       InterleaveDispatcher.this.parent.handleEvent(event, canRecover);
/*     */     }
/*     */     
/*     */     public boolean isNotation(String arg0) {
/* 398 */       return InterleaveDispatcher.this.parent.isNotation(arg0);
/*     */     }
/*     */     
/*     */     public boolean isUnparsedEntity(String arg0) {
/* 402 */       return InterleaveDispatcher.this.parent.isUnparsedEntity(arg0);
/*     */     }
/*     */     
/*     */     public void popAttributes() {
/* 406 */       InterleaveDispatcher.this.parent.popAttributes();
/*     */     }
/*     */     
/*     */     public void pushAttributes(Attributes atts, boolean collectTextFlag) {
/* 410 */       InterleaveDispatcher.this.parent.pushAttributes(atts, collectTextFlag);
/*     */     }
/*     */     
/*     */     public String resolveNamespacePrefix(String prefix) {
/* 414 */       return InterleaveDispatcher.this.parent.resolveNamespacePrefix(prefix);
/*     */     }
/*     */     
/*     */     public String[] getNewlyDeclaredPrefixes() {
/* 418 */       return InterleaveDispatcher.this.parent.getNewlyDeclaredPrefixes();
/*     */     }
/*     */     
/*     */     public String[] getAllDeclaredPrefixes() {
/* 422 */       return InterleaveDispatcher.this.parent.getAllDeclaredPrefixes();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\InterleaveDispatcher.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */