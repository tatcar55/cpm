/*     */ package com.sun.xml.txw2;
/*     */ 
/*     */ import com.sun.xml.txw2.annotation.XmlAttribute;
/*     */ import com.sun.xml.txw2.annotation.XmlCDATA;
/*     */ import com.sun.xml.txw2.annotation.XmlElement;
/*     */ import com.sun.xml.txw2.annotation.XmlNamespace;
/*     */ import com.sun.xml.txw2.annotation.XmlValue;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ContainerElement
/*     */   implements InvocationHandler, TypedXmlWriter
/*     */ {
/*     */   final Document document;
/*     */   StartTag startTag;
/*  70 */   final EndTag endTag = new EndTag();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String nsUri;
/*     */ 
/*     */ 
/*     */   
/*     */   private Content tail;
/*     */ 
/*     */ 
/*     */   
/*     */   private ContainerElement prevOpen;
/*     */ 
/*     */ 
/*     */   
/*     */   private ContainerElement nextOpen;
/*     */ 
/*     */   
/*     */   private final ContainerElement parent;
/*     */ 
/*     */   
/*     */   private ContainerElement lastOpenChild;
/*     */ 
/*     */   
/*     */   private boolean blocked;
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerElement(Document document, ContainerElement parent, String nsUri, String localName) {
/* 100 */     this.parent = parent;
/* 101 */     this.document = document;
/* 102 */     this.nsUri = nsUri;
/* 103 */     this.startTag = new StartTag(this, nsUri, localName);
/* 104 */     this.tail = this.startTag;
/*     */     
/* 106 */     if (isRoot())
/* 107 */       document.setFirstContent(this.startTag); 
/*     */   }
/*     */   
/*     */   private boolean isRoot() {
/* 111 */     return (this.parent == null);
/*     */   }
/*     */   
/*     */   private boolean isCommitted() {
/* 115 */     return (this.tail == null);
/*     */   }
/*     */   
/*     */   public Document getDocument() {
/* 119 */     return this.document;
/*     */   }
/*     */   
/*     */   boolean isBlocked() {
/* 123 */     return (this.blocked && !isCommitted());
/*     */   }
/*     */   
/*     */   public void block() {
/* 127 */     this.blocked = true;
/*     */   }
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 131 */     if (method.getDeclaringClass() == TypedXmlWriter.class || method.getDeclaringClass() == Object.class) {
/*     */       
/*     */       try {
/* 134 */         return method.invoke(this, args);
/* 135 */       } catch (InvocationTargetException e) {
/* 136 */         throw e.getTargetException();
/*     */       } 
/*     */     }
/*     */     
/* 140 */     XmlAttribute xa = method.<XmlAttribute>getAnnotation(XmlAttribute.class);
/* 141 */     XmlValue xv = method.<XmlValue>getAnnotation(XmlValue.class);
/* 142 */     XmlElement xe = method.<XmlElement>getAnnotation(XmlElement.class);
/*     */ 
/*     */     
/* 145 */     if (xa != null) {
/* 146 */       if (xv != null || xe != null) {
/* 147 */         throw new IllegalAnnotationException(method.toString());
/*     */       }
/* 149 */       addAttribute(xa, method, args);
/* 150 */       return proxy;
/*     */     } 
/* 152 */     if (xv != null) {
/* 153 */       if (xe != null) {
/* 154 */         throw new IllegalAnnotationException(method.toString());
/*     */       }
/* 156 */       _pcdata(args);
/* 157 */       return proxy;
/*     */     } 
/*     */     
/* 160 */     return addElement(xe, method, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAttribute(XmlAttribute xa, Method method, Object[] args) {
/* 167 */     assert xa != null;
/*     */     
/* 169 */     checkStartTag();
/*     */     
/* 171 */     String localName = xa.value();
/* 172 */     if (xa.value().length() == 0) {
/* 173 */       localName = method.getName();
/*     */     }
/* 175 */     _attribute(xa.ns(), localName, args);
/*     */   }
/*     */   
/*     */   private void checkStartTag() {
/* 179 */     if (this.startTag == null) {
/* 180 */       throw new IllegalStateException("start tag has already been written");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object addElement(XmlElement e, Method method, Object[] args) {
/* 187 */     Class<?> rt = method.getReturnType();
/*     */ 
/*     */     
/* 190 */     String nsUri = "##default";
/* 191 */     String localName = method.getName();
/*     */     
/* 193 */     if (e != null) {
/*     */       
/* 195 */       if (e.value().length() != 0)
/* 196 */         localName = e.value(); 
/* 197 */       nsUri = e.ns();
/*     */     } 
/*     */     
/* 200 */     if (nsUri.equals("##default")) {
/*     */       
/* 202 */       Class<?> c = method.getDeclaringClass();
/* 203 */       XmlElement ce = c.<XmlElement>getAnnotation(XmlElement.class);
/* 204 */       if (ce != null) {
/* 205 */         nsUri = ce.ns();
/*     */       }
/*     */       
/* 208 */       if (nsUri.equals("##default"))
/*     */       {
/* 210 */         nsUri = getNamespace(c.getPackage());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 215 */     if (rt == void.class) {
/*     */ 
/*     */       
/* 218 */       boolean isCDATA = (method.getAnnotation(XmlCDATA.class) != null);
/*     */       
/* 220 */       StartTag st = new StartTag(this.document, nsUri, localName);
/* 221 */       addChild(st);
/* 222 */       for (Object arg : args) {
/*     */         Text text;
/* 224 */         if (isCDATA) { text = new Cdata(this.document, st, arg); }
/* 225 */         else { text = new Pcdata(this.document, st, arg); }
/* 226 */          addChild(text);
/*     */       } 
/* 228 */       addChild(new EndTag());
/* 229 */       return null;
/*     */     } 
/* 231 */     if (TypedXmlWriter.class.isAssignableFrom(rt))
/*     */     {
/* 233 */       return _element(nsUri, localName, rt);
/*     */     }
/*     */     
/* 236 */     throw new IllegalSignatureException("Illegal return type: " + rt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNamespace(Package pkg) {
/*     */     String nsUri;
/* 243 */     if (pkg == null) return "";
/*     */ 
/*     */     
/* 246 */     XmlNamespace ns = pkg.<XmlNamespace>getAnnotation(XmlNamespace.class);
/* 247 */     if (ns != null) {
/* 248 */       nsUri = ns.value();
/*     */     } else {
/* 250 */       nsUri = "";
/* 251 */     }  return nsUri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addChild(Content child) {
/* 258 */     this.tail.setNext(this.document, child);
/* 259 */     this.tail = child;
/*     */   }
/*     */   
/*     */   public void commit() {
/* 263 */     commit(true);
/*     */   }
/*     */   
/*     */   public void commit(boolean includingAllPredecessors) {
/* 267 */     _commit(includingAllPredecessors);
/* 268 */     this.document.flush();
/*     */   }
/*     */   
/*     */   private void _commit(boolean includingAllPredecessors) {
/* 272 */     if (isCommitted())
/*     */       return; 
/* 274 */     addChild(this.endTag);
/* 275 */     if (isRoot())
/* 276 */       addChild(new EndDocument()); 
/* 277 */     this.tail = null;
/*     */ 
/*     */     
/* 280 */     if (includingAllPredecessors) {
/* 281 */       for (ContainerElement e = this; e != null; e = e.parent) {
/* 282 */         while (e.prevOpen != null) {
/* 283 */           e.prevOpen._commit(false);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 290 */     while (this.lastOpenChild != null) {
/* 291 */       this.lastOpenChild._commit(false);
/*     */     }
/*     */     
/* 294 */     if (this.parent != null) {
/* 295 */       if (this.parent.lastOpenChild == this) {
/* 296 */         assert this.nextOpen == null : "this must be the last one";
/* 297 */         this.parent.lastOpenChild = this.prevOpen;
/*     */       } else {
/* 299 */         assert this.nextOpen.prevOpen == this;
/* 300 */         this.nextOpen.prevOpen = this.prevOpen;
/*     */       } 
/* 302 */       if (this.prevOpen != null) {
/* 303 */         assert this.prevOpen.nextOpen == this;
/* 304 */         this.prevOpen.nextOpen = this.nextOpen;
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     this.nextOpen = null;
/* 309 */     this.prevOpen = null;
/*     */   }
/*     */   
/*     */   public void _attribute(String localName, Object value) {
/* 313 */     _attribute("", localName, value);
/*     */   }
/*     */   
/*     */   public void _attribute(String nsUri, String localName, Object value) {
/* 317 */     checkStartTag();
/* 318 */     this.startTag.addAttribute(nsUri, localName, value);
/*     */   }
/*     */   
/*     */   public void _attribute(QName attributeName, Object value) {
/* 322 */     _attribute(attributeName.getNamespaceURI(), attributeName.getLocalPart(), value);
/*     */   }
/*     */   
/*     */   public void _namespace(String uri) {
/* 326 */     _namespace(uri, false);
/*     */   }
/*     */   
/*     */   public void _namespace(String uri, String prefix) {
/* 330 */     if (prefix == null)
/* 331 */       throw new IllegalArgumentException(); 
/* 332 */     checkStartTag();
/* 333 */     this.startTag.addNamespaceDecl(uri, prefix, false);
/*     */   }
/*     */   
/*     */   public void _namespace(String uri, boolean requirePrefix) {
/* 337 */     checkStartTag();
/* 338 */     this.startTag.addNamespaceDecl(uri, null, requirePrefix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void _pcdata(Object value) {
/* 344 */     addChild(new Pcdata(this.document, this.startTag, value));
/*     */   }
/*     */   
/*     */   public void _cdata(Object value) {
/* 348 */     addChild(new Cdata(this.document, this.startTag, value));
/*     */   }
/*     */   
/*     */   public void _comment(Object value) throws UnsupportedOperationException {
/* 352 */     addChild(new Comment(this.document, this.startTag, value));
/*     */   }
/*     */   
/*     */   public <T extends TypedXmlWriter> T _element(String localName, Class<T> contentModel) {
/* 356 */     return _element(this.nsUri, localName, contentModel);
/*     */   }
/*     */   
/*     */   public <T extends TypedXmlWriter> T _element(QName tagName, Class<T> contentModel) {
/* 360 */     return _element(tagName.getNamespaceURI(), tagName.getLocalPart(), contentModel);
/*     */   }
/*     */   
/*     */   public <T extends TypedXmlWriter> T _element(Class<T> contentModel) {
/* 364 */     return _element(TXW.getTagName(contentModel), contentModel);
/*     */   }
/*     */   
/*     */   public <T extends TypedXmlWriter> T _cast(Class<T> facadeType) {
/* 368 */     return facadeType.cast(Proxy.newProxyInstance(facadeType.getClassLoader(), new Class[] { facadeType }, this));
/*     */   }
/*     */   
/*     */   public <T extends TypedXmlWriter> T _element(String nsUri, String localName, Class<T> contentModel) {
/* 372 */     ContainerElement child = new ContainerElement(this.document, this, nsUri, localName);
/* 373 */     addChild(child.startTag);
/* 374 */     this.tail = child.endTag;
/*     */ 
/*     */     
/* 377 */     if (this.lastOpenChild != null) {
/* 378 */       assert this.lastOpenChild.parent == this;
/*     */       
/* 380 */       assert child.prevOpen == null;
/* 381 */       assert child.nextOpen == null;
/* 382 */       child.prevOpen = this.lastOpenChild;
/* 383 */       assert this.lastOpenChild.nextOpen == null;
/* 384 */       this.lastOpenChild.nextOpen = child;
/*     */     } 
/*     */     
/* 387 */     this.lastOpenChild = child;
/*     */     
/* 389 */     return child._cast(contentModel);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\ContainerElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */