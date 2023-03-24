/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.property.Property;
/*     */ import com.sun.xml.bind.v2.runtime.property.PropertyFactory;
/*     */ import com.sun.xml.bind.v2.runtime.property.UnmarshallerChain;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Discarder;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Intercepter;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.TagName;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ElementBeanInfoImpl
/*     */   extends JaxBeanInfo<JAXBElement>
/*     */ {
/*     */   private Loader loader;
/*     */   private final Property property;
/*     */   private final QName tagName;
/*     */   public final Class expectedType;
/*     */   private final Class scope;
/*     */   private final Constructor<? extends JAXBElement> constructor;
/*     */   
/*     */   ElementBeanInfoImpl(JAXBContextImpl grammar, RuntimeElementInfo rei) {
/*  94 */     super(grammar, (RuntimeTypeInfo)rei, rei.getType(), true, false, true);
/*     */     
/*  96 */     this.property = PropertyFactory.create(grammar, (RuntimePropertyInfo)rei.getProperty());
/*     */     
/*  98 */     this.tagName = rei.getElementName();
/*  99 */     this.expectedType = Navigator.REFLECTION.erasure((Type)rei.getContentInMemoryType());
/* 100 */     this.scope = (rei.getScope() == null) ? JAXBElement.GlobalScope.class : (Class)rei.getScope().getClazz();
/*     */     
/* 102 */     Class<JAXBElement> type = Navigator.REFLECTION.erasure(rei.getType());
/* 103 */     if (type == JAXBElement.class) {
/* 104 */       this.constructor = null;
/*     */     } else {
/*     */       try {
/* 107 */         this.constructor = type.getConstructor(new Class[] { this.expectedType });
/* 108 */       } catch (NoSuchMethodException e) {
/* 109 */         NoSuchMethodError x = new NoSuchMethodError("Failed to find the constructor for " + type + " with " + this.expectedType);
/* 110 */         x.initCause(e);
/* 111 */         throw x;
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
/*     */   protected ElementBeanInfoImpl(final JAXBContextImpl grammar) {
/* 125 */     super(grammar, null, JAXBElement.class, true, false, true);
/* 126 */     this.tagName = null;
/* 127 */     this.expectedType = null;
/* 128 */     this.scope = null;
/* 129 */     this.constructor = null;
/*     */     
/* 131 */     this.property = new Property<JAXBElement>() {
/*     */         public void reset(JAXBElement o) {
/* 133 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public void serializeBody(JAXBElement<?> e, XMLSerializer target, Object outerPeer) throws SAXException, IOException, XMLStreamException {
/* 137 */           Class scope = e.getScope();
/* 138 */           if (e.isGlobalScope()) scope = null; 
/* 139 */           QName n = e.getName();
/* 140 */           ElementBeanInfoImpl bi = grammar.getElement(scope, n);
/* 141 */           if (bi == null) {
/*     */             JaxBeanInfo<?> tbi;
/*     */             
/*     */             try {
/* 145 */               tbi = grammar.getBeanInfo(e.getDeclaredType(), true);
/* 146 */             } catch (JAXBException x) {
/*     */               
/* 148 */               target.reportError(null, x);
/*     */               return;
/*     */             } 
/* 151 */             Object value = e.getValue();
/* 152 */             target.startElement(n.getNamespaceURI(), n.getLocalPart(), n.getPrefix(), null);
/* 153 */             if (value == null) {
/* 154 */               target.writeXsiNilTrue();
/*     */             } else {
/* 156 */               target.childAsXsiType(value, "value", tbi, false);
/*     */             } 
/* 158 */             target.endElement();
/*     */           } else {
/*     */             try {
/* 161 */               bi.property.serializeBody(e, target, e);
/* 162 */             } catch (AccessorException x) {
/* 163 */               target.reportError(null, (Throwable)x);
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void serializeURIs(JAXBElement o, XMLSerializer target) {}
/*     */         
/*     */         public boolean hasSerializeURIAction() {
/* 172 */           return false;
/*     */         }
/*     */         
/*     */         public String getIdValue(JAXBElement o) {
/* 176 */           return null;
/*     */         }
/*     */         
/*     */         public PropertyKind getKind() {
/* 180 */           return PropertyKind.ELEMENT;
/*     */         }
/*     */ 
/*     */         
/*     */         public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {}
/*     */         
/*     */         public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 187 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void wrapUp() {}
/*     */         
/*     */         public RuntimePropertyInfo getInfo() {
/* 194 */           return ElementBeanInfoImpl.this.property.getInfo();
/*     */         }
/*     */         
/*     */         public boolean isHiddenByOverride() {
/* 198 */           return false;
/*     */         }
/*     */         
/*     */         public void setHiddenByOverride(boolean hidden) {
/* 202 */           throw new UnsupportedOperationException("Not supported on jaxbelements.");
/*     */         }
/*     */         
/*     */         public String getFieldName() {
/* 206 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class IntercepterLoader
/*     */     extends Loader
/*     */     implements Intercepter
/*     */   {
/*     */     private final Loader core;
/*     */ 
/*     */ 
/*     */     
/*     */     public IntercepterLoader(Loader core) {
/* 222 */       this.core = core;
/*     */     }
/*     */ 
/*     */     
/*     */     public final void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 227 */       state.loader = this.core;
/* 228 */       state.intercepter = this;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 233 */       UnmarshallingContext context = state.getContext();
/*     */ 
/*     */       
/* 236 */       Object child = context.getOuterPeer();
/*     */       
/* 238 */       if (child != null && ElementBeanInfoImpl.this.jaxbType != child.getClass()) {
/* 239 */         child = null;
/*     */       }
/* 241 */       if (child != null) {
/* 242 */         ElementBeanInfoImpl.this.reset((JAXBElement)child, context);
/*     */       }
/* 244 */       if (child == null) {
/* 245 */         child = context.createInstance(ElementBeanInfoImpl.this);
/*     */       }
/* 247 */       fireBeforeUnmarshal(ElementBeanInfoImpl.this, child, state);
/*     */       
/* 249 */       context.recordOuterPeer(child);
/* 250 */       UnmarshallingContext.State p = state.prev;
/* 251 */       p.backup = p.target;
/* 252 */       p.target = child;
/*     */       
/* 254 */       this.core.startElement(state, ea);
/*     */     }
/*     */     
/*     */     public Object intercept(UnmarshallingContext.State state, Object o) throws SAXException {
/* 258 */       JAXBElement<Object> e = (JAXBElement)state.target;
/* 259 */       state.target = state.backup;
/* 260 */       state.backup = null;
/*     */       
/* 262 */       if (state.nil) {
/* 263 */         e.setNil(true);
/* 264 */         state.nil = false;
/*     */       } 
/*     */       
/* 267 */       if (o != null)
/*     */       {
/*     */         
/* 270 */         e.setValue(o);
/*     */       }
/* 272 */       fireAfterUnmarshal(ElementBeanInfoImpl.this, e, state);
/*     */       
/* 274 */       return e;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getElementNamespaceURI(JAXBElement e) {
/* 279 */     return e.getName().getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getElementLocalName(JAXBElement e) {
/* 283 */     return e.getName().getLocalPart();
/*     */   }
/*     */   
/*     */   public Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 287 */     if (this.loader == null) {
/*     */       
/* 289 */       UnmarshallerChain c = new UnmarshallerChain(context);
/* 290 */       QNameMap<ChildLoader> result = new QNameMap();
/* 291 */       this.property.buildChildElementUnmarshallers(c, result);
/* 292 */       if (result.size() == 1) {
/*     */         
/* 294 */         this.loader = new IntercepterLoader(((ChildLoader)result.getOne().getValue()).loader);
/*     */       } else {
/*     */         
/* 297 */         this.loader = Discarder.INSTANCE;
/*     */       } 
/* 299 */     }  return this.loader;
/*     */   }
/*     */   
/*     */   public final JAXBElement createInstance(UnmarshallingContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException {
/* 303 */     return createInstanceFromValue(null);
/*     */   }
/*     */   
/*     */   public final JAXBElement createInstanceFromValue(Object o) throws IllegalAccessException, InvocationTargetException, InstantiationException {
/* 307 */     if (this.constructor == null) {
/* 308 */       return new JAXBElement(this.tagName, this.expectedType, this.scope, o);
/*     */     }
/* 310 */     return this.constructor.newInstance(new Object[] { o });
/*     */   }
/*     */   
/*     */   public boolean reset(JAXBElement e, UnmarshallingContext context) {
/* 314 */     e.setValue(null);
/* 315 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId(JAXBElement e, XMLSerializer target) {
/* 324 */     Object o = e.getValue();
/* 325 */     if (o instanceof String) {
/* 326 */       return (String)o;
/*     */     }
/* 328 */     return null;
/*     */   }
/*     */   
/*     */   public void serializeBody(JAXBElement element, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/*     */     try {
/* 333 */       this.property.serializeBody(element, target, null);
/* 334 */     } catch (AccessorException x) {
/* 335 */       target.reportError(null, (Throwable)x);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeRoot(JAXBElement e, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 340 */     serializeBody(e, target);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(JAXBElement e, XMLSerializer target) {}
/*     */ 
/*     */   
/*     */   public void serializeURIs(JAXBElement e, XMLSerializer target) {}
/*     */ 
/*     */   
/*     */   public final Transducer<JAXBElement> getTransducer() {
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void wrapUp() {
/* 357 */     super.wrapUp();
/* 358 */     this.property.wrapUp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void link(JAXBContextImpl grammar) {
/* 363 */     super.link(grammar);
/* 364 */     getLoader(grammar, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\ElementBeanInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */