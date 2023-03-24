/*      */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*      */ 
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Nullable;
/*      */ import com.sun.istack.SAXParseException2;
/*      */ import com.sun.xml.bind.IDResolver;
/*      */ import com.sun.xml.bind.api.AccessorException;
/*      */ import com.sun.xml.bind.api.ClassResolver;
/*      */ import com.sun.xml.bind.unmarshaller.InfosetScanner;
/*      */ import com.sun.xml.bind.v2.ClassFactory;
/*      */ import com.sun.xml.bind.v2.runtime.AssociationMap;
/*      */ import com.sun.xml.bind.v2.runtime.Coordinator;
/*      */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*      */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.Callable;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.bind.UnmarshalException;
/*      */ import javax.xml.bind.ValidationEvent;
/*      */ import javax.xml.bind.ValidationEventHandler;
/*      */ import javax.xml.bind.ValidationEventLocator;
/*      */ import javax.xml.bind.helpers.ValidationEventImpl;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.helpers.LocatorImpl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class UnmarshallingContext
/*      */   extends Coordinator
/*      */   implements NamespaceContext, ValidationEventHandler, ErrorHandler, XmlVisitor, XmlVisitor.TextPredictor
/*      */ {
/*      */   private final State root;
/*      */   private State current;
/*      */   private static final LocatorEx DUMMY_INSTANCE;
/*      */   
/*      */   static {
/*  107 */     LocatorImpl loc = new LocatorImpl();
/*  108 */     loc.setPublicId(null);
/*  109 */     loc.setSystemId(null);
/*  110 */     loc.setLineNumber(-1);
/*  111 */     loc.setColumnNumber(-1);
/*  112 */     DUMMY_INSTANCE = new LocatorExWrapper(loc);
/*      */   }
/*      */   @NotNull
/*  115 */   private LocatorEx locator = DUMMY_INSTANCE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object result;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JaxBeanInfo expectedType;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IDResolver idResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isUnmarshalInProgress = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean aborted = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final UnmarshallerImpl parent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final AssociationMap assoc;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isInplaceMode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InfosetScanner scanner;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object currentElement;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NamespaceContext environmentNamespaceContext;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ClassResolver classResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ClassLoader classLoader;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<Class, Factory> factories;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Patcher[] patchers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int patchersLen;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] nsBind;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int nsLen;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Scope[] scopes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int scopeTop;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final class State
/*      */   {
/*      */     public Loader loader;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Receiver receiver;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Intercepter intercepter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object target;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object backup;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int numNsDecl;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String elementDefaultValue;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public State prev;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private State next;
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nil = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UnmarshallingContext getContext() {
/*  275 */       return UnmarshallingContext.this;
/*      */     }
/*      */     
/*      */     private State(State prev) {
/*  279 */       this.prev = prev;
/*  280 */       if (prev != null)
/*  281 */         prev.next = this; 
/*      */     }
/*      */     
/*      */     private void push() {
/*  285 */       if (this.next == null)
/*  286 */         UnmarshallingContext.this.allocateMoreStates(); 
/*  287 */       State n = this.next;
/*  288 */       n.numNsDecl = UnmarshallingContext.this.nsLen;
/*  289 */       UnmarshallingContext.this.current = n;
/*      */     }
/*      */     
/*      */     private void pop() {
/*  293 */       assert this.prev != null;
/*  294 */       this.loader = null;
/*  295 */       this.nil = false;
/*  296 */       this.receiver = null;
/*  297 */       this.intercepter = null;
/*  298 */       this.elementDefaultValue = null;
/*  299 */       this.target = null;
/*  300 */       UnmarshallingContext.this.current = this.prev;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Factory
/*      */   {
/*      */     private final Object factorInstance;
/*      */     
/*      */     private final Method method;
/*      */     
/*      */     public Factory(Object factorInstance, Method method) {
/*  312 */       this.factorInstance = factorInstance;
/*  313 */       this.method = method;
/*      */     }
/*      */     
/*      */     public Object createInstance() throws SAXException {
/*      */       try {
/*  318 */         return this.method.invoke(this.factorInstance, new Object[0]);
/*  319 */       } catch (IllegalAccessException e) {
/*  320 */         UnmarshallingContext.getInstance().handleError(e, false);
/*  321 */       } catch (InvocationTargetException e) {
/*  322 */         UnmarshallingContext.getInstance().handleError(e, false);
/*      */       } 
/*  324 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset(InfosetScanner scanner, boolean isInplaceMode, JaxBeanInfo expectedType, IDResolver idResolver) {
/*  344 */     this.scanner = scanner;
/*  345 */     this.isInplaceMode = isInplaceMode;
/*  346 */     this.expectedType = expectedType;
/*  347 */     this.idResolver = idResolver;
/*      */   }
/*      */   
/*      */   public JAXBContextImpl getJAXBContext() {
/*  351 */     return this.parent.context;
/*      */   }
/*      */   
/*      */   public State getCurrentState() {
/*  355 */     return this.current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Loader selectRootLoader(State state, TagName tag) throws SAXException {
/*      */     try {
/*  367 */       Loader l = getJAXBContext().selectRootLoader(state, tag);
/*  368 */       if (l != null) return l;
/*      */       
/*  370 */       if (this.classResolver != null) {
/*  371 */         Class<?> clazz = this.classResolver.resolveElementName(tag.uri, tag.local);
/*  372 */         if (clazz != null) {
/*  373 */           JAXBContextImpl enhanced = getJAXBContext().createAugmented(clazz);
/*  374 */           JaxBeanInfo<?> bi = enhanced.getBeanInfo(clazz);
/*  375 */           return bi.getLoader(enhanced, true);
/*      */         } 
/*      */       } 
/*  378 */     } catch (RuntimeException e) {
/*  379 */       throw e;
/*  380 */     } catch (Exception e) {
/*  381 */       handleError(e);
/*      */     } 
/*      */     
/*  384 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocateMoreStates() {
/*  396 */     assert this.current.next == null;
/*      */     
/*  398 */     State s = this.current;
/*  399 */     for (int i = 0; i < 8; i++)
/*  400 */       s = new State(s); 
/*      */   }
/*      */   public void setFactories(Object factoryInstances) { this.factories.clear(); if (factoryInstances == null) return;  if (factoryInstances instanceof Object[]) { for (Object factory : (Object[])factoryInstances) addFactory(factory);  } else { addFactory(factoryInstances); }  }
/*      */   private void addFactory(Object factory) { for (Method m : factory.getClass().getMethods()) { if (m.getName().startsWith("create")) if ((m.getParameterTypes()).length <= 0) { Class<?> type = m.getReturnType(); this.factories.put(type, new Factory(factory, m)); }   }  }
/*  404 */   public void startDocument(LocatorEx locator, NamespaceContext nsContext) throws SAXException { if (locator != null) this.locator = locator;  this.environmentNamespaceContext = nsContext; this.result = null; this.current = this.root; this.patchersLen = 0; this.aborted = false; this.isUnmarshalInProgress = true; this.nsLen = 0; setThreadAffinity(); if (this.expectedType != null) { this.root.loader = EXPECTED_TYPE_ROOT_LOADER; } else { this.root.loader = DEFAULT_ROOT_LOADER; }  this.idResolver.startDocument(this); } public void startElement(TagName tagName) throws SAXException { pushCoordinator(); try { _startElement(tagName); } finally { popCoordinator(); }  } private void _startElement(TagName tagName) throws SAXException { if (this.assoc != null) this.currentElement = this.scanner.getCurrentElement();  Loader h = this.current.loader; this.current.push(); h.childElement(this.current, tagName); assert this.current.loader != null; this.current.loader.startElement(this.current, tagName); } public void clearStates() { State last = this.current;
/*  405 */     for (; last.next != null; last = last.next);
/*  406 */     while (last.prev != null) {
/*  407 */       last.loader = null;
/*  408 */       last.nil = false;
/*  409 */       last.receiver = null;
/*  410 */       last.intercepter = null;
/*  411 */       last.elementDefaultValue = null;
/*  412 */       last.target = null;
/*  413 */       last = last.prev;
/*  414 */       last.next.prev = null;
/*  415 */       last.next = null;
/*      */     } 
/*  417 */     this.current = last; }
/*      */   public void text(CharSequence pcdata) throws SAXException { State cur = this.current; pushCoordinator(); try { if (cur.elementDefaultValue != null && pcdata.length() == 0) pcdata = cur.elementDefaultValue;  cur.loader.text(cur, pcdata); } finally { popCoordinator(); }  }
/*      */   public final void endElement(TagName tagName) throws SAXException { pushCoordinator(); try { State child = this.current; child.loader.leaveElement(child, tagName); Object target = child.target; Receiver recv = child.receiver; Intercepter intercepter = child.intercepter; child.pop(); if (intercepter != null)
/*      */         target = intercepter.intercept(this.current, target);  if (recv != null)
/*      */         recv.receive(this.current, target);  } finally { popCoordinator(); }  }
/*      */   public void endDocument() throws SAXException { runPatchers(); this.idResolver.endDocument(); this.isUnmarshalInProgress = false; this.currentElement = null; this.locator = DUMMY_INSTANCE; this.environmentNamespaceContext = null; assert this.root == this.current; resetThreadAffinity(); }
/*  423 */   @Deprecated public boolean expectText() { return this.current.loader.expectText; } public UnmarshallingContext(UnmarshallerImpl _parent, AssociationMap assoc) { this.factories = (Map)new HashMap<Class<?>, Factory>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     this.patchers = null;
/*  732 */     this.patchersLen = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  816 */     this.nsBind = new String[16];
/*  817 */     this.nsLen = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  953 */     this.scopes = new Scope[16];
/*      */ 
/*      */ 
/*      */     
/*  957 */     this.scopeTop = 0;
/*      */ 
/*      */     
/*  960 */     for (int i = 0; i < this.scopes.length; i++)
/*  961 */       this.scopes[i] = new Scope<Object, Object, Object, Object>(this);  this.parent = _parent; this.assoc = assoc; this.root = this.current = new State(null); allocateMoreStates(); } @Deprecated public XmlVisitor.TextPredictor getPredictor() { return this; }
/*      */   public UnmarshallingContext getContext() { return this; }
/*      */   public Object getResult() throws UnmarshalException { if (this.isUnmarshalInProgress)
/*      */       throw new IllegalStateException();  if (!this.aborted)
/*      */       return this.result;  throw new UnmarshalException((String)null); }
/*      */   void clearResult() { if (this.isUnmarshalInProgress)
/*      */       throw new IllegalStateException();  this.result = null; }
/*      */   public Object createInstance(Class<?> clazz) throws SAXException { if (!this.factories.isEmpty()) { Factory factory = this.factories.get(clazz); if (factory != null)
/*      */         return factory.createInstance();  }  return ClassFactory.create(clazz); }
/*      */   public Object createInstance(JaxBeanInfo beanInfo) throws SAXException { if (!this.factories.isEmpty()) { Factory factory = this.factories.get(beanInfo.jaxbType); if (factory != null)
/*      */         return factory.createInstance();  }  try { return beanInfo.createInstance(this); } catch (IllegalAccessException e) { Loader.reportError("Unable to create an instance of " + beanInfo.jaxbType.getName(), e, false); } catch (InvocationTargetException e) { Loader.reportError("Unable to create an instance of " + beanInfo.jaxbType.getName(), e, false); } catch (InstantiationException e) { Loader.reportError("Unable to create an instance of " + beanInfo.jaxbType.getName(), e, false); }  return null; }
/*      */   public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException { ValidationEventHandler eventHandler = this.parent.getEventHandler(); boolean recover = eventHandler.handleEvent(event); if (!recover)
/*      */       this.aborted = true;  if (!canRecover || !recover)
/*      */       throw new SAXParseException2(event.getMessage(), this.locator, new UnmarshalException(event.getMessage(), event.getLinkedException()));  }
/*      */   public boolean handleEvent(ValidationEvent event) { try { boolean recover = this.parent.getEventHandler().handleEvent(event); if (!recover)
/*      */         this.aborted = true;  return recover; }
/*      */     catch (RuntimeException re) { return false; }
/*      */      }
/*      */   public void handleError(Exception e) throws SAXException { handleError(e, true); }
/*  980 */   public void startScope(int frameSize) { this.scopeTop += frameSize;
/*      */ 
/*      */     
/*  983 */     if (this.scopeTop >= this.scopes.length) {
/*  984 */       Scope[] s = new Scope[Math.max(this.scopeTop + 1, this.scopes.length * 2)];
/*  985 */       System.arraycopy(this.scopes, 0, s, 0, this.scopes.length);
/*  986 */       for (int i = this.scopes.length; i < s.length; i++)
/*  987 */         s[i] = new Scope<Object, Object, Object, Object>(this); 
/*  988 */       this.scopes = s;
/*      */     }  } public void handleError(Exception e, boolean canRecover) throws SAXException { handleEvent(new ValidationEventImpl(1, e.getMessage(), this.locator.getLocation(), e), canRecover); }
/*      */   public void handleError(String msg) { handleEvent(new ValidationEventImpl(1, msg, this.locator.getLocation())); }
/*      */   protected ValidationEventLocator getLocation() { return this.locator.getLocation(); }
/*      */   public LocatorEx getLocator() { return this.locator; }
/*      */   public void errorUnresolvedIDREF(Object bean, String idref, LocatorEx loc) throws SAXException { handleEvent(new ValidationEventImpl(1, Messages.UNRESOLVED_IDREF.format(new Object[] { idref }, ), loc.getLocation()), true); }
/*      */   public void addPatcher(Patcher job) { if (this.patchers == null)
/*      */       this.patchers = new Patcher[32];  if (this.patchers.length == this.patchersLen) { Patcher[] buf = new Patcher[this.patchersLen * 2]; System.arraycopy(this.patchers, 0, buf, 0, this.patchersLen); this.patchers = buf; }
/*      */      this.patchers[this.patchersLen++] = job; }
/*      */   private void runPatchers() throws SAXException { if (this.patchers != null)
/*      */       for (int i = 0; i < this.patchersLen; i++) { this.patchers[i].run(); this.patchers[i] = null; }
/*      */         }
/*      */   public String addToIdTable(String id) throws SAXException { Object o = this.current.target; if (o == null)
/*      */       o = this.current.prev.target;  this.idResolver.bind(id, o); return id; }
/*      */   public Callable getObjectFromId(String id, Class targetType) throws SAXException { return this.idResolver.resolve(id, targetType); }
/*      */   public void endScope(int frameSize) throws SAXException { 
/* 1004 */     try { for (; frameSize > 0; frameSize--, this.scopeTop--)
/* 1005 */         this.scopes[this.scopeTop].finish();  }
/* 1006 */     catch (AccessorException e)
/* 1007 */     { handleError((Exception)e);
/*      */ 
/*      */ 
/*      */       
/* 1011 */       for (; frameSize > 0; frameSize--)
/* 1012 */         this.scopes[this.scopeTop--] = new Scope<Object, Object, Object, Object>(this);  }  } public void startPrefixMapping(String prefix, String uri) { if (this.nsBind.length == this.nsLen) { String[] n = new String[this.nsLen * 2]; System.arraycopy(this.nsBind, 0, n, 0, this.nsLen); this.nsBind = n; }  this.nsBind[this.nsLen++] = prefix; this.nsBind[this.nsLen++] = uri; }
/*      */   public void endPrefixMapping(String prefix) { this.nsLen -= 2; }
/*      */   private String resolveNamespacePrefix(String prefix) { if (prefix.equals("xml")) return "http://www.w3.org/XML/1998/namespace";  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (prefix.equals(this.nsBind[i])) return this.nsBind[i + 1];  }  if (this.environmentNamespaceContext != null) return this.environmentNamespaceContext.getNamespaceURI(prefix.intern());  if (prefix.equals("")) return "";  return null; }
/*      */   public String[] getNewlyDeclaredPrefixes() { return getPrefixList(this.current.prev.numNsDecl); }
/*      */   public String[] getAllDeclaredPrefixes() { return getPrefixList(0); }
/*      */   private String[] getPrefixList(int startIndex) { int size = (this.current.numNsDecl - startIndex) / 2; String[] r = new String[size]; for (int i = 0; i < r.length; i++) r[i] = this.nsBind[startIndex + i * 2];  return r; }
/*      */   public Iterator<String> getPrefixes(String uri) { return Collections.<String>unmodifiableList(getAllPrefixesInList(uri)).iterator(); }
/*      */   private List<String> getAllPrefixesInList(String uri) { List<String> a = new ArrayList<String>(); if (uri == null) throw new IllegalArgumentException();  if (uri.equals("http://www.w3.org/XML/1998/namespace")) { a.add("xml"); return a; }  if (uri.equals("http://www.w3.org/2000/xmlns/")) { a.add("xmlns"); return a; }  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (uri.equals(this.nsBind[i + 1]) && getNamespaceURI(this.nsBind[i]).equals(this.nsBind[i + 1])) a.add(this.nsBind[i]);  }  return a; }
/*      */   public String getPrefix(String uri) { if (uri == null) throw new IllegalArgumentException();  if (uri.equals("http://www.w3.org/XML/1998/namespace")) return "xml";  if (uri.equals("http://www.w3.org/2000/xmlns/")) return "xmlns";  for (int i = this.nsLen - 2; i >= 0; i -= 2) { if (uri.equals(this.nsBind[i + 1]) && getNamespaceURI(this.nsBind[i]).equals(this.nsBind[i + 1]))
/*      */         return this.nsBind[i];  }  if (this.environmentNamespaceContext != null)
/*      */       return this.environmentNamespaceContext.getPrefix(uri);  return null; }
/*      */   public String getNamespaceURI(String prefix) { if (prefix == null)
/*      */       throw new IllegalArgumentException();  if (prefix.equals("xmlns"))
/*      */       return "http://www.w3.org/2000/xmlns/";  return resolveNamespacePrefix(prefix); }
/* 1026 */   public Scope getScope(int offset) { return this.scopes[this.scopeTop - offset]; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1037 */   private static final Loader DEFAULT_ROOT_LOADER = new DefaultRootLoader();
/* 1038 */   private static final Loader EXPECTED_TYPE_ROOT_LOADER = new ExpectedTypeRootLoader();
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DefaultRootLoader
/*      */     extends Loader
/*      */     implements Receiver
/*      */   {
/*      */     private DefaultRootLoader() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 1051 */       Loader loader = state.getContext().selectRootLoader(state, ea);
/* 1052 */       if (loader != null) {
/* 1053 */         state.loader = loader;
/* 1054 */         state.receiver = this;
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1060 */       JaxBeanInfo beanInfo = XsiTypeLoader.parseXsiType(state, ea, null);
/* 1061 */       if (beanInfo == null) {
/*      */         
/* 1063 */         reportUnexpectedChildElement(ea, false);
/*      */         
/*      */         return;
/*      */       } 
/* 1067 */       state.loader = beanInfo.getLoader(null, false);
/* 1068 */       state.prev.backup = new JAXBElement(ea.createQName(), Object.class, null);
/* 1069 */       state.receiver = this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Collection<QName> getExpectedChildElements() {
/* 1074 */       return UnmarshallingContext.getInstance().getJAXBContext().getValidRootNames();
/*      */     }
/*      */     
/*      */     public void receive(UnmarshallingContext.State state, Object o) {
/* 1078 */       if (state.backup != null) {
/* 1079 */         ((JAXBElement<Object>)state.backup).setValue(o);
/* 1080 */         o = state.backup;
/*      */       } 
/* 1082 */       if (state.nil) {
/* 1083 */         ((JAXBElement)o).setNil(true);
/*      */       }
/* 1085 */       (state.getContext()).result = o;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class ExpectedTypeRootLoader
/*      */     extends Loader
/*      */     implements Receiver
/*      */   {
/*      */     private ExpectedTypeRootLoader() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void childElement(UnmarshallingContext.State state, TagName ea) {
/* 1100 */       UnmarshallingContext context = state.getContext();
/*      */ 
/*      */       
/* 1103 */       QName qn = new QName(ea.uri, ea.local);
/* 1104 */       state.prev.target = new JAXBElement(qn, context.expectedType.jaxbType, null, null);
/* 1105 */       state.receiver = this;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1110 */       state.loader = new XsiNilLoader(context.expectedType.getLoader(null, true));
/*      */     }
/*      */     
/*      */     public void receive(UnmarshallingContext.State state, Object o) {
/* 1114 */       JAXBElement<Object> e = (JAXBElement)state.target;
/* 1115 */       e.setValue(o);
/* 1116 */       state.getContext().recordOuterPeer(e);
/* 1117 */       (state.getContext()).result = e;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recordInnerPeer(Object innerPeer) {
/* 1132 */     if (this.assoc != null) {
/* 1133 */       this.assoc.addInner(this.currentElement, innerPeer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getInnerPeer() {
/* 1144 */     if (this.assoc != null && this.isInplaceMode) {
/* 1145 */       return this.assoc.getInnerPeer(this.currentElement);
/*      */     }
/* 1147 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void recordOuterPeer(Object outerPeer) {
/* 1158 */     if (this.assoc != null) {
/* 1159 */       this.assoc.addOuter(this.currentElement, outerPeer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getOuterPeer() {
/* 1170 */     if (this.assoc != null && this.isInplaceMode) {
/* 1171 */       return this.assoc.getOuterPeer(this.currentElement);
/*      */     }
/* 1173 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getXMIMEContentType() {
/* 1192 */     Object t = this.current.target;
/* 1193 */     if (t == null) return null; 
/* 1194 */     return getJAXBContext().getXMIMEContentType(t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UnmarshallingContext getInstance() {
/* 1202 */     return (UnmarshallingContext)Coordinator._getInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<QName> getCurrentExpectedElements() {
/* 1212 */     pushCoordinator();
/*      */     try {
/* 1214 */       State s = getCurrentState();
/* 1215 */       Loader l = s.loader;
/* 1216 */       return (l != null) ? l.getExpectedChildElements() : null;
/*      */     } finally {
/* 1218 */       popCoordinator();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<QName> getCurrentExpectedAttributes() {
/* 1229 */     pushCoordinator();
/*      */     try {
/* 1231 */       State s = getCurrentState();
/* 1232 */       Loader l = s.loader;
/* 1233 */       return (l != null) ? l.getExpectedAttributes() : null;
/*      */     } finally {
/* 1235 */       popCoordinator();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StructureLoader getStructureLoader() {
/* 1245 */     if (this.current.loader instanceof StructureLoader) {
/* 1246 */       return (StructureLoader)this.current.loader;
/*      */     }
/* 1248 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\UnmarshallingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */