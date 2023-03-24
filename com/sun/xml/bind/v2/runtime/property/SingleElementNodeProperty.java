/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ final class SingleElementNodeProperty<BeanT, ValueT>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final Accessor<BeanT, ValueT> acc;
/*     */   private final boolean nillable;
/*     */   private final QName[] acceptedElements;
/*  83 */   private final Map<Class, TagAndType> typeNames = (Map)new HashMap<Class<?>, TagAndType>();
/*     */ 
/*     */   
/*     */   private RuntimeElementPropertyInfo prop;
/*     */ 
/*     */   
/*     */   private final Name nullTagName;
/*     */ 
/*     */   
/*     */   public SingleElementNodeProperty(JAXBContextImpl context, RuntimeElementPropertyInfo prop) {
/*  93 */     super(context, (RuntimePropertyInfo)prop);
/*  94 */     this.acc = prop.getAccessor().optimize(context);
/*  95 */     this.prop = prop;
/*     */     
/*  97 */     QName nt = null;
/*  98 */     boolean nil = false;
/*     */     
/* 100 */     this.acceptedElements = new QName[prop.getTypes().size()];
/* 101 */     for (int i = 0; i < this.acceptedElements.length; i++) {
/* 102 */       this.acceptedElements[i] = ((RuntimeTypeRef)prop.getTypes().get(i)).getTagName();
/*     */     }
/* 104 */     for (RuntimeTypeRef e : prop.getTypes()) {
/* 105 */       JaxBeanInfo beanInfo = context.getOrCreate((RuntimeTypeInfo)e.getTarget());
/* 106 */       if (nt == null) nt = e.getTagName(); 
/* 107 */       this.typeNames.put(beanInfo.jaxbType, new TagAndType(context.nameBuilder.createElementName(e.getTagName()), beanInfo));
/*     */       
/* 109 */       nil |= e.isNillable();
/*     */     } 
/*     */     
/* 112 */     this.nullTagName = context.nameBuilder.createElementName(nt);
/*     */     
/* 114 */     this.nillable = nil;
/*     */   }
/*     */ 
/*     */   
/*     */   public void wrapUp() {
/* 119 */     super.wrapUp();
/* 120 */     this.prop = null;
/*     */   }
/*     */   
/*     */   public void reset(BeanT bean) throws AccessorException {
/* 124 */     this.acc.set(bean, null);
/*     */   }
/*     */   
/*     */   public String getIdValue(BeanT beanT) {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 133 */     ValueT v = (ValueT)this.acc.get(o);
/* 134 */     if (v != null) {
/* 135 */       Class<?> vtype = v.getClass();
/* 136 */       TagAndType tt = this.typeNames.get(vtype);
/*     */       
/* 138 */       if (tt == null) {
/* 139 */         for (Map.Entry<Class<?>, TagAndType> e : this.typeNames.entrySet()) {
/* 140 */           if (((Class)e.getKey()).isAssignableFrom(vtype)) {
/* 141 */             tt = e.getValue();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 147 */       boolean addNilDecl = (o instanceof JAXBElement && ((JAXBElement)o).isNil());
/* 148 */       if (tt == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 154 */         w.startElement(((TagAndType)this.typeNames.values().iterator().next()).tagName, null);
/* 155 */         w.childAsXsiType(v, this.fieldName, w.grammar.getBeanInfo(Object.class), (addNilDecl && this.nillable));
/*     */       } else {
/* 157 */         w.startElement(tt.tagName, null);
/* 158 */         w.childAsXsiType(v, this.fieldName, tt.beanInfo, (addNilDecl && this.nillable));
/*     */       } 
/* 160 */       w.endElement();
/* 161 */     } else if (this.nillable) {
/* 162 */       w.startElement(this.nullTagName, null);
/* 163 */       w.writeXsiNilTrue();
/* 164 */       w.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {
/* 169 */     JAXBContextImpl context = chain.context;
/*     */     
/* 171 */     for (RuntimeTypeRef runtimeTypeRef : this.prop.getTypes()) {
/* 172 */       DefaultValueLoaderDecorator defaultValueLoaderDecorator; XsiNilLoader.Single single; JaxBeanInfo bi = context.getOrCreate((RuntimeTypeInfo)runtimeTypeRef.getTarget());
/*     */ 
/*     */       
/* 175 */       Loader l = bi.getLoader(context, !Modifier.isFinal(bi.jaxbType.getModifiers()));
/* 176 */       if (runtimeTypeRef.getDefaultValue() != null)
/* 177 */         defaultValueLoaderDecorator = new DefaultValueLoaderDecorator(l, runtimeTypeRef.getDefaultValue()); 
/* 178 */       if (this.nillable || chain.context.allNillable)
/* 179 */         single = new XsiNilLoader.Single((Loader)defaultValueLoaderDecorator, this.acc); 
/* 180 */       handlers.put(runtimeTypeRef.getTagName(), new ChildLoader((Loader)single, (Receiver)this.acc));
/*     */     } 
/*     */   }
/*     */   
/*     */   public PropertyKind getKind() {
/* 185 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 190 */     for (QName n : this.acceptedElements) {
/* 191 */       if (n.getNamespaceURI().equals(nsUri) && n.getLocalPart().equals(localName))
/* 192 */         return this.acc; 
/*     */     } 
/* 194 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\SingleElementNodeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */