/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.TypeRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.RuntimeUtil;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.ListIterator;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Lister;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.NullSafeAccessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.TextLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ abstract class ArrayElementProperty<BeanT, ListT, ItemT>
/*     */   extends ArrayERProperty<BeanT, ListT, ItemT>
/*     */ {
/*  84 */   private final Map<Class, TagAndType> typeMap = (Map)new HashMap<Class<?>, TagAndType>();
/*     */ 
/*     */ 
/*     */   
/*  88 */   private Map<TypeRef<Type, Class>, JaxBeanInfo> refs = new HashMap<TypeRef<Type, Class>, JaxBeanInfo>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected RuntimeElementPropertyInfo prop;
/*     */ 
/*     */   
/*     */   private final Name nillableTagName;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ArrayElementProperty(JAXBContextImpl grammar, RuntimeElementPropertyInfo prop) {
/* 100 */     super(grammar, (RuntimePropertyInfo)prop, prop.getXmlName(), prop.isCollectionNillable());
/* 101 */     this.prop = prop;
/*     */     
/* 103 */     List<? extends RuntimeTypeRef> types = prop.getTypes();
/*     */     
/* 105 */     Name n = null;
/*     */     
/* 107 */     for (RuntimeTypeRef typeRef : types) {
/* 108 */       Class type = (Class)typeRef.getTarget().getType();
/* 109 */       if (type.isPrimitive()) {
/* 110 */         type = (Class)RuntimeUtil.primitiveToBox.get(type);
/*     */       }
/* 112 */       JaxBeanInfo beanInfo = grammar.getOrCreate((RuntimeTypeInfo)typeRef.getTarget());
/* 113 */       TagAndType tt = new TagAndType(grammar.nameBuilder.createElementName(typeRef.getTagName()), beanInfo);
/*     */ 
/*     */       
/* 116 */       this.typeMap.put(type, tt);
/* 117 */       this.refs.put(typeRef, beanInfo);
/* 118 */       if (typeRef.isNillable() && n == null) {
/* 119 */         n = tt.tagName;
/*     */       }
/*     */     } 
/* 122 */     this.nillableTagName = n;
/*     */   }
/*     */ 
/*     */   
/*     */   public void wrapUp() {
/* 127 */     super.wrapUp();
/* 128 */     this.refs = null;
/* 129 */     this.prop = null;
/*     */   }
/*     */   
/*     */   protected void serializeListBody(BeanT beanT, XMLSerializer w, ListT list) throws IOException, XMLStreamException, SAXException, AccessorException {
/* 133 */     ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */     
/* 135 */     boolean isIdref = itr instanceof Lister.IDREFSIterator;
/*     */     
/* 137 */     while (itr.hasNext()) {
/*     */       try {
/* 139 */         ItemT item = (ItemT)itr.next();
/* 140 */         if (item != null) {
/* 141 */           Class<?> itemType = item.getClass();
/* 142 */           if (isIdref)
/*     */           {
/*     */             
/* 145 */             itemType = ((Lister.IDREFSIterator)itr).last().getClass();
/*     */           }
/*     */           
/* 148 */           TagAndType tt = this.typeMap.get(itemType);
/* 149 */           while (tt == null && itemType != null) {
/*     */             
/* 151 */             itemType = itemType.getSuperclass();
/* 152 */             tt = this.typeMap.get(itemType);
/*     */           } 
/*     */           
/* 155 */           if (tt == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 168 */             w.startElement(((TagAndType)this.typeMap.values().iterator().next()).tagName, null);
/* 169 */             w.childAsXsiType(item, this.fieldName, w.grammar.getBeanInfo(Object.class), false);
/*     */           } else {
/* 171 */             w.startElement(tt.tagName, null);
/* 172 */             serializeItem(tt.beanInfo, item, w);
/*     */           } 
/*     */           
/* 175 */           w.endElement(); continue;
/*     */         } 
/* 177 */         if (this.nillableTagName != null) {
/* 178 */           w.startElement(this.nillableTagName, null);
/* 179 */           w.writeXsiNilTrue();
/* 180 */           w.endElement();
/*     */         }
/*     */       
/* 183 */       } catch (JAXBException e) {
/* 184 */         w.reportError(this.fieldName, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void serializeItem(JaxBeanInfo paramJaxBeanInfo, ItemT paramItemT, XMLSerializer paramXMLSerializer) throws SAXException, AccessorException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createBodyUnmarshaller(UnmarshallerChain chain, QNameMap<ChildLoader> loaders) {
/* 200 */     int offset = chain.allocateOffset();
/* 201 */     Receiver recv = new ArrayERProperty.ReceiverImpl(this, offset);
/*     */     
/* 203 */     for (RuntimeTypeRef typeRef : this.prop.getTypes()) {
/*     */       XsiNilLoader.Array array; DefaultValueLoaderDecorator defaultValueLoaderDecorator;
/* 205 */       Name tagName = chain.context.nameBuilder.createElementName(typeRef.getTagName());
/* 206 */       Loader item = createItemUnmarshaller(chain, typeRef);
/*     */       
/* 208 */       if (typeRef.isNillable() || chain.context.allNillable)
/* 209 */         array = new XsiNilLoader.Array(item); 
/* 210 */       if (typeRef.getDefaultValue() != null) {
/* 211 */         defaultValueLoaderDecorator = new DefaultValueLoaderDecorator((Loader)array, typeRef.getDefaultValue());
/*     */       }
/* 213 */       loaders.put(tagName, new ChildLoader((Loader)defaultValueLoaderDecorator, recv));
/*     */     } 
/*     */   }
/*     */   
/*     */   public final PropertyKind getKind() {
/* 218 */     return PropertyKind.ELEMENT;
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
/*     */   private Loader createItemUnmarshaller(UnmarshallerChain chain, RuntimeTypeRef typeRef) {
/* 235 */     if (PropertyFactory.isLeaf(typeRef.getSource())) {
/* 236 */       Transducer xducer = typeRef.getTransducer();
/* 237 */       return (Loader)new TextLoader(xducer);
/*     */     } 
/* 239 */     return ((JaxBeanInfo)this.refs.get(typeRef)).getLoader(chain.context, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 244 */     if (this.wrapperTagName != null) {
/* 245 */       if (this.wrapperTagName.equals(nsUri, localName))
/* 246 */         return this.acc; 
/*     */     } else {
/* 248 */       for (TagAndType tt : this.typeMap.values()) {
/* 249 */         if (tt.tagName.equals(nsUri, localName))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 254 */           return (Accessor)new NullSafeAccessor(this.acc, this.lister); } 
/*     */       } 
/*     */     } 
/* 257 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ArrayElementProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */