/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.JAXBAssertionError;
/*     */ import com.sun.xml.bind.unmarshaller.Messages;
/*     */ import com.sun.xml.bind.unmarshaller.Tracer;
/*     */ import com.sun.xml.bind.util.AttributesImpl;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.UnmarshalException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXUnmarshallerHandlerImpl
/*     */   implements SAXUnmarshallerHandler, UnmarshallingContext
/*     */ {
/*     */   private boolean isUnmarshalInProgress = true;
/*     */   private final GrammarInfo grammarInfo;
/*     */   private Object result;
/*     */   private UnmarshallingEventHandler[] handlers;
/*     */   private int[] mementos;
/*     */   private int handlerLen;
/*     */   private StringBuffer buffer;
/*     */   private String[] nsBind;
/*     */   private int nsLen;
/*     */   private int[] idxStack;
/*     */   private AttributesImpl[] attStack;
/*     */   private int elementDepth;
/*     */   private int stackTop;
/*     */   private boolean[] collectText;
/*     */   private Runnable[] patchers;
/*     */   private int patchersLen;
/*     */   private Hashtable idmap;
/*     */   private Locator locator;
/*     */   
/*     */   public GrammarInfo getGrammarInfo() {
/*  64 */     return this.grammarInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean shouldCollectText() {
/*  70 */     return this.collectText[this.stackTop];
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*  75 */     this.result = null;
/*  76 */     this.handlerLen = 0;
/*  77 */     this.patchers = null;
/*  78 */     this.patchersLen = 0;
/*  79 */     this.aborted = false;
/*  80 */     this.isUnmarshalInProgress = true;
/*     */     
/*  82 */     this.stackTop = 0;
/*  83 */     this.elementDepth = 1;
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*  87 */     runPatchers();
/*  88 */     this.isUnmarshalInProgress = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/*  95 */     if (uri == null)
/*  96 */       uri = ""; 
/*  97 */     if (local == null || local.length() == 0)
/*  98 */       local = qname; 
/*  99 */     if (qname == null || qname.length() == 0) {
/* 100 */       qname = local;
/*     */     }
/* 102 */     if (this.result == null) {
/*     */ 
/*     */       
/* 105 */       UnmarshallingEventHandler unmarshaller = this.grammarInfo.createUnmarshaller(uri, local, this);
/*     */       
/* 107 */       if (unmarshaller == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 114 */         throw new SAXParseException(Messages.format("SAXUnmarshallerHandlerImpl.UnexpectedRootElement2", uri, local, computeExpectedRootElements()), getLocator());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 119 */       this.result = unmarshaller.owner();
/*     */       
/* 121 */       pushContentHandler(unmarshaller, 0);
/*     */     } 
/*     */     
/* 124 */     processText(true);
/*     */     
/* 126 */     getCurrentHandler().enterElement(uri, local, qname, atts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void endElement(String uri, String local, String qname) throws SAXException {
/* 133 */     if (uri == null)
/* 134 */       uri = ""; 
/* 135 */     if (local == null || local.length() == 0)
/* 136 */       local = qname; 
/* 137 */     if (qname == null || qname.length() == 0) {
/* 138 */       qname = local;
/*     */     }
/* 140 */     processText(false);
/* 141 */     getCurrentHandler().leaveElement(uri, local, qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getResult() throws UnmarshalException {
/* 151 */     if (this.isUnmarshalInProgress) {
/* 152 */       throw new IllegalStateException();
/*     */     }
/* 154 */     if (!this.aborted) return this.result;
/*     */ 
/*     */     
/* 157 */     throw new UnmarshalException((String)null);
/*     */   } public void pushContentHandler(UnmarshallingEventHandler handler, int memento) { if (this.handlerLen == this.handlers.length) { UnmarshallingEventHandler[] h = new UnmarshallingEventHandler[this.handlerLen * 2]; int[] m = new int[this.handlerLen * 2]; System.arraycopy(this.handlers, 0, h, 0, this.handlerLen); System.arraycopy(this.mementos, 0, m, 0, this.handlerLen); this.handlers = h; this.mementos = m; }  this.handlers[this.handlerLen] = handler; this.mementos[this.handlerLen] = memento; this.handlerLen++; } public void popContentHandler() throws SAXException { this.handlerLen--; this.handlers[this.handlerLen] = null; getCurrentHandler().leaveChild(this.mementos[this.handlerLen]); }
/*     */   public UnmarshallingEventHandler getCurrentHandler() { return this.handlers[this.handlerLen - 1]; }
/*     */   protected void consumeText(String str, boolean ignorable) throws SAXException { if (ignorable && str.trim().length() == 0) return;  getCurrentHandler().text(str); }
/*     */   private void processText(boolean ignorable) throws SAXException { if (shouldCollectText()) consumeText(this.buffer.toString(), ignorable);  if (this.buffer.length() < 1024) { this.buffer.setLength(0); } else { this.buffer = new StringBuffer(); }  }
/*     */   public final void characters(char[] buf, int start, int len) { if (shouldCollectText())
/*     */       this.buffer.append(buf, start, len);  }
/*     */   public final void ignorableWhitespace(char[] buf, int start, int len) { characters(buf, start, len); }
/*     */   public void startPrefixMapping(String prefix, String uri) { if (this.nsBind.length == this.nsLen) { String[] n = new String[this.nsLen * 2]; System.arraycopy(this.nsBind, 0, n, 0, this.nsLen); this.nsBind = n; }  this.nsBind[this.nsLen++] = prefix; this.nsBind[this.nsLen++] = uri; }
/*     */   public void endPrefixMapping(String prefix) { this.nsLen -= 2; }
/* 167 */   public SAXUnmarshallerHandlerImpl(UnmarshallerImpl _parent, GrammarInfo _gi) { this.handlers = new UnmarshallingEventHandler[16];
/* 168 */     this.mementos = new int[16];
/* 169 */     this.handlerLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.buffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     this.nsBind = new String[16];
/* 241 */     this.nsLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     this.idxStack = new int[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     this.attStack = new AttributesImpl[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     this.collectText = new boolean[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 497 */     this.patchers = null;
/* 498 */     this.patchersLen = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     this.idmap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 551 */     this.locator = DUMMY_LOCATOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 562 */     this.aborted = false; this.parent = _parent; this.grammarInfo = _gi; startPrefixMapping("", ""); } public String resolveNamespacePrefix(String prefix) { if (prefix.equals("xml")) return "http://www.w3.org/XML/1998/namespace";  for (int i = this.idxStack[this.stackTop] - 2; i >= 0; i -= 2) { if (prefix.equals(this.nsBind[i])) return this.nsBind[i + 1];  }  return null; } public String[] getNewlyDeclaredPrefixes() { return getPrefixList(this.idxStack[this.stackTop - 1]); } public String[] getAllDeclaredPrefixes() { return getPrefixList(2); } private String[] getPrefixList(int startIndex) { int size = (this.idxStack[this.stackTop] - startIndex) / 2; String[] r = new String[size]; for (int i = 0; i < r.length; i++) r[i] = this.nsBind[startIndex + i * 2];  return r; }
/*     */   public Iterator getPrefixes(String uri) { return Collections.unmodifiableList(getAllPrefixesInList(uri)).iterator(); }
/*     */   private List getAllPrefixesInList(String uri) { List a = new ArrayList(); if (uri.equals("http://www.w3.org/XML/1998/namespace")) { a.add("xml"); return a; }  if (uri.equals("http://www.w3.org/2000/xmlns/")) { a.add("xmlns"); return a; }  if (uri == null) throw new IllegalArgumentException();  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (uri.equals(this.nsBind[i + 1]) && getNamespaceURI(this.nsBind[i]).equals(this.nsBind[i + 1])) a.add(this.nsBind[i]);  }  return a; }
/*     */   public String getPrefix(String uri) { if (uri.equals("http://www.w3.org/XML/1998/namespace")) return "xml";  if (uri.equals("http://www.w3.org/2000/xmlns/")) return "xmlns";  if (uri == null) throw new IllegalArgumentException();  for (int i = this.idxStack[this.stackTop] - 2; i >= 0; i -= 2) { if (uri.equals(this.nsBind[i + 1]) && getNamespaceURI(this.nsBind[i]).equals(this.nsBind[i + 1])) return this.nsBind[i];  }  return null; }
/*     */   public String getNamespaceURI(String prefix) { if (prefix == null) throw new IllegalArgumentException();  if (prefix.equals("xmlns")) return "http://www.w3.org/2000/xmlns/";  return resolveNamespacePrefix(prefix); }
/* 567 */   public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException { ValidationEventHandler eventHandler; try { eventHandler = this.parent.getEventHandler(); }
/* 568 */     catch (JAXBException e)
/*     */     
/* 570 */     { throw new JAXBAssertionError(); }
/*     */ 
/*     */     
/* 573 */     boolean recover = eventHandler.handleEvent(event);
/*     */ 
/*     */ 
/*     */     
/* 577 */     if (!recover) this.aborted = true;
/*     */     
/* 579 */     if (!canRecover || !recover)
/* 580 */       throw new SAXException(new UnmarshalException(event.getMessage(), event.getLinkedException()));  }
/*     */   public void pushAttributes(Attributes atts, boolean collectTextFlag) { if (this.attStack.length == this.elementDepth) { AttributesImpl[] buf1 = new AttributesImpl[this.attStack.length * 2]; System.arraycopy(this.attStack, 0, buf1, 0, this.attStack.length); this.attStack = buf1; int[] buf2 = new int[this.idxStack.length * 2]; System.arraycopy(this.idxStack, 0, buf2, 0, this.idxStack.length); this.idxStack = buf2; boolean[] buf3 = new boolean[this.collectText.length * 2]; System.arraycopy(this.collectText, 0, buf3, 0, this.collectText.length); this.collectText = buf3; }  this.elementDepth++; this.stackTop++; AttributesImpl a = this.attStack[this.stackTop]; if (a == null) { this.attStack[this.stackTop] = a = new AttributesImpl(); } else { a.clear(); }  for (int i = 0; i < atts.getLength(); i++) { String auri = atts.getURI(i); String alocal = atts.getLocalName(i); String avalue = atts.getValue(i); String aqname = atts.getQName(i); if (auri == null) auri = "";  if (alocal == null || alocal.length() == 0) alocal = aqname;  if (aqname == null || aqname.length() == 0) aqname = alocal;  if (auri == "http://www.w3.org/2001/XMLSchema-instance" && alocal == "nil") { String v = avalue.trim(); if (v.equals("false") || v.equals("0")) continue;  }  a.addAttribute(auri, alocal, aqname, atts.getType(i), avalue); continue; }  this.idxStack[this.stackTop] = this.nsLen; this.collectText[this.stackTop] = collectTextFlag; }
/*     */   public void popAttributes() { this.stackTop--; this.elementDepth--; }
/*     */   public Attributes getUnconsumedAttributes() { return (Attributes)this.attStack[this.stackTop]; }
/*     */   public int getAttribute(String uri, String local) { return this.attStack[this.stackTop].getIndexFast(uri, local); }
/*     */   public void consumeAttribute(int idx) throws SAXException { AttributesImpl a = this.attStack[this.stackTop]; String uri = a.getURI(idx); String local = a.getLocalName(idx); String qname = a.getQName(idx); String value = a.getValue(idx); a.removeAttribute(idx); getCurrentHandler().enterAttribute(uri, local, qname); consumeText(value, false); getCurrentHandler().leaveAttribute(uri, local, qname); }
/*     */   public String eatAttribute(int idx) throws SAXException { AttributesImpl a = this.attStack[this.stackTop]; String value = a.getValue(idx); a.removeAttribute(idx); return value; }
/*     */   public void addPatcher(Runnable job) { if (this.patchers == null) this.patchers = new Runnable[32];  if (this.patchers.length == this.patchersLen) { Runnable[] buf = new Runnable[this.patchersLen * 2]; System.arraycopy(this.patchers, 0, buf, 0, this.patchersLen); this.patchers = buf; }  this.patchers[this.patchersLen++] = job; }
/*     */   private void runPatchers() { if (this.patchers != null) for (int i = 0; i < this.patchersLen; i++) this.patchers[i].run();   }
/*     */   public String addToIdTable(String id) { if (this.idmap == null)
/* 590 */       this.idmap = new Hashtable();  this.idmap.put(id, getCurrentHandler().owner()); return id; } public String getBaseUri() { return null; }
/* 591 */   public Object getObjectFromId(String id) { if (this.idmap == null) return null;  return this.idmap.get(id); } public void skippedEntity(String name) {} public void processingInstruction(String target, String data) {} public void setDocumentLocator(Locator loc) { this.locator = loc; } public Locator getLocator() { return this.locator; } private static final Locator DUMMY_LOCATOR = new LocatorImpl(); private final UnmarshallerImpl parent; private boolean aborted; private Tracer tracer; public boolean isUnparsedEntity(String s) { return true; } public boolean isNotation(String s) {
/* 592 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTracer(Tracer t) {
/* 602 */     this.tracer = t;
/*     */   }
/*     */   public Tracer getTracer() {
/* 605 */     if (this.tracer == null)
/* 606 */       this.tracer = (Tracer)new Tracer.Standard(); 
/* 607 */     return this.tracer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String computeExpectedRootElements() {
/* 614 */     String r = "";
/*     */     
/* 616 */     String[] probePoints = this.grammarInfo.getProbePoints();
/* 617 */     for (int i = 0; i < probePoints.length; i += 2) {
/* 618 */       if (this.grammarInfo.recognize(probePoints[i], probePoints[i + 1])) {
/* 619 */         if (r.length() != 0) r = r + ','; 
/* 620 */         r = r + "<{" + probePoints[i] + "}" + probePoints[i + 1] + ">";
/*     */       } 
/*     */     } 
/*     */     
/* 624 */     return r;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\SAXUnmarshallerHandlerImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */