/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.WildcardMode;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.ElementBeanInfoImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.WildcardLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.annotation.DomHandler;
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
/*     */ final class SingleReferenceNodeProperty<BeanT, ValueT>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final Accessor<BeanT, ValueT> acc;
/*  75 */   private final QNameMap<JaxBeanInfo> expectedElements = new QNameMap();
/*     */   
/*     */   private final DomHandler domHandler;
/*     */   private final WildcardMode wcMode;
/*     */   
/*     */   public SingleReferenceNodeProperty(JAXBContextImpl context, RuntimeReferencePropertyInfo prop) {
/*  81 */     super(context, (RuntimePropertyInfo)prop);
/*  82 */     this.acc = prop.getAccessor().optimize(context);
/*     */     
/*  84 */     for (RuntimeElement e : prop.getElements()) {
/*  85 */       this.expectedElements.put(e.getElementName(), context.getOrCreate((RuntimeTypeInfo)e));
/*     */     }
/*     */     
/*  88 */     if (prop.getWildcard() != null) {
/*  89 */       this.domHandler = (DomHandler)ClassFactory.create((Class)prop.getDOMHandler());
/*  90 */       this.wcMode = prop.getWildcard();
/*     */     } else {
/*  92 */       this.domHandler = null;
/*  93 */       this.wcMode = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset(BeanT bean) throws AccessorException {
/*  98 */     this.acc.set(bean, null);
/*     */   }
/*     */   
/*     */   public String getIdValue(BeanT beanT) {
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 106 */     ValueT v = (ValueT)this.acc.get(o);
/* 107 */     if (v != null) {
/*     */       try {
/* 109 */         JaxBeanInfo bi = w.grammar.getBeanInfo(v, true);
/* 110 */         if (bi.jaxbType == Object.class && this.domHandler != null)
/*     */         
/*     */         { 
/* 113 */           w.writeDom(v, this.domHandler, o, this.fieldName); }
/*     */         else
/* 115 */         { bi.serializeRoot(v, w); } 
/* 116 */       } catch (JAXBException e) {
/* 117 */         w.reportError(this.fieldName, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {
/* 124 */     for (QNameMap.Entry<JaxBeanInfo> n : (Iterable<QNameMap.Entry<JaxBeanInfo>>)this.expectedElements.entrySet()) {
/* 125 */       handlers.put(n.nsUri, n.localName, new ChildLoader(((JaxBeanInfo)n.getValue()).getLoader(chain.context, true), (Receiver)this.acc));
/*     */     }
/* 127 */     if (this.domHandler != null) {
/* 128 */       handlers.put(CATCH_ALL, new ChildLoader((Loader)new WildcardLoader(this.domHandler, this.wcMode), (Receiver)this.acc));
/*     */     }
/*     */   }
/*     */   
/*     */   public PropertyKind getKind() {
/* 133 */     return PropertyKind.REFERENCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 138 */     JaxBeanInfo bi = (JaxBeanInfo)this.expectedElements.get(nsUri, localName);
/* 139 */     if (bi != null) {
/* 140 */       if (bi instanceof ElementBeanInfoImpl) {
/* 141 */         final ElementBeanInfoImpl ebi = (ElementBeanInfoImpl)bi;
/*     */         
/* 143 */         return new Accessor<BeanT, Object>(ebi.expectedType) {
/*     */             public Object get(BeanT bean) throws AccessorException {
/* 145 */               ValueT r = (ValueT)SingleReferenceNodeProperty.this.acc.get(bean);
/* 146 */               if (r instanceof JAXBElement) {
/* 147 */                 return ((JAXBElement)r).getValue();
/*     */               }
/*     */               
/* 150 */               return r;
/*     */             }
/*     */             
/*     */             public void set(BeanT bean, Object value) throws AccessorException {
/* 154 */               if (value != null) {
/*     */                 try {
/* 156 */                   value = ebi.createInstanceFromValue(value);
/* 157 */                 } catch (IllegalAccessException e) {
/* 158 */                   throw new AccessorException(e);
/* 159 */                 } catch (InvocationTargetException e) {
/* 160 */                   throw new AccessorException(e);
/* 161 */                 } catch (InstantiationException e) {
/* 162 */                   throw new AccessorException(e);
/*     */                 } 
/*     */               }
/* 165 */               SingleReferenceNodeProperty.this.acc.set(bean, value);
/*     */             }
/*     */           };
/*     */       } 
/*     */       
/* 170 */       return this.acc;
/*     */     } 
/*     */     
/* 173 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\SingleReferenceNodeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */