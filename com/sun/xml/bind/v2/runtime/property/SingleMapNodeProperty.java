/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.nav.ReflectionNavigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeMapPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.TagName;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SingleMapNodeProperty<BeanT, ValueT extends Map>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final Accessor<BeanT, ValueT> acc;
/*     */   private final Name tagName;
/*     */   private final Name entryTag;
/*     */   private final Name keyTag;
/*     */   private final Name valueTag;
/*     */   private final boolean nillable;
/*     */   private JaxBeanInfo keyBeanInfo;
/*     */   private JaxBeanInfo valueBeanInfo;
/*     */   private final Class<? extends ValueT> mapImplClass;
/*     */   
/*     */   public SingleMapNodeProperty(JAXBContextImpl context, RuntimeMapPropertyInfo prop) {
/* 105 */     super(context, (RuntimePropertyInfo)prop);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     this.itemsLoader = new Loader(false)
/*     */       {
/* 158 */         private ThreadLocal<BeanT> target = new ThreadLocal<BeanT>();
/* 159 */         private ThreadLocal<ValueT> map = new ThreadLocal<ValueT>();
/* 160 */         private int depthCounter = 0;
/*     */ 
/*     */ 
/*     */         
/*     */         public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*     */           try {
/* 166 */             this.target.set((BeanT)state.prev.target);
/* 167 */             this.map.set((ValueT)SingleMapNodeProperty.this.acc.get(this.target.get()));
/* 168 */             this.depthCounter++;
/* 169 */             if (this.map.get() == null) {
/* 170 */               this.map.set((ValueT)ClassFactory.create(SingleMapNodeProperty.this.mapImplClass));
/*     */             }
/* 172 */             ((Map)this.map.get()).clear();
/* 173 */             state.target = this.map.get();
/* 174 */           } catch (AccessorException e) {
/*     */             
/* 176 */             handleGenericException((Exception)e, true);
/* 177 */             state.target = new HashMap<Object, Object>();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 183 */           super.leaveElement(state, ea);
/*     */           try {
/* 185 */             SingleMapNodeProperty.this.acc.set(this.target.get(), this.map.get());
/* 186 */             if (--this.depthCounter == 0) {
/* 187 */               this.target.remove();
/* 188 */               this.map.remove();
/*     */             } 
/* 190 */           } catch (AccessorException ex) {
/* 191 */             handleGenericException((Exception)ex, true);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 197 */           if (ea.matches(SingleMapNodeProperty.this.entryTag)) {
/* 198 */             state.loader = SingleMapNodeProperty.this.entryLoader;
/*     */           } else {
/* 200 */             super.childElement(state, ea);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public Collection<QName> getExpectedChildElements() {
/* 206 */           return Collections.singleton(SingleMapNodeProperty.this.entryTag.toQName());
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     this.entryLoader = new Loader(false)
/*     */       {
/*     */         public void startElement(UnmarshallingContext.State state, TagName ea) {
/* 218 */           state.target = new Object[2];
/*     */         }
/*     */ 
/*     */         
/*     */         public void leaveElement(UnmarshallingContext.State state, TagName ea) {
/* 223 */           Object[] keyValue = (Object[])state.target;
/* 224 */           Map<Object, Object> map = (Map)state.prev.target;
/* 225 */           map.put(keyValue[0], keyValue[1]);
/*     */         }
/*     */ 
/*     */         
/*     */         public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 230 */           if (ea.matches(SingleMapNodeProperty.this.keyTag)) {
/* 231 */             state.loader = SingleMapNodeProperty.this.keyLoader;
/* 232 */             state.receiver = SingleMapNodeProperty.keyReceiver;
/*     */             return;
/*     */           } 
/* 235 */           if (ea.matches(SingleMapNodeProperty.this.valueTag)) {
/* 236 */             state.loader = SingleMapNodeProperty.this.valueLoader;
/* 237 */             state.receiver = SingleMapNodeProperty.valueReceiver;
/*     */             return;
/*     */           } 
/* 240 */           super.childElement(state, ea);
/*     */         }
/*     */         
/*     */         public Collection<QName> getExpectedChildElements()
/*     */         {
/* 245 */           return Arrays.asList(new QName[] { SingleMapNodeProperty.access$400(this.this$0).toQName(), SingleMapNodeProperty.access$700(this.this$0).toQName() }); }
/*     */       }; this.acc = prop.getAccessor().optimize(context); this.tagName = context.nameBuilder.createElementName(prop.getXmlName()); this.entryTag = context.nameBuilder.createElementName("", "entry"); this.keyTag = context.nameBuilder.createElementName("", "key"); this.valueTag = context.nameBuilder.createElementName("", "value"); this.nillable = prop.isCollectionNillable(); this.keyBeanInfo = context.getOrCreate((RuntimeTypeInfo)prop.getKeyType()); this.valueBeanInfo = context.getOrCreate((RuntimeTypeInfo)prop.getValueType()); Class<ValueT> sig = ReflectionNavigator.REFLECTION.erasure(prop.getRawType());
/*     */     this.mapImplClass = ClassFactory.inferImplClass(sig, knownImplClasses);
/*     */   } private static final Class[] knownImplClasses = new Class[] { HashMap.class, TreeMap.class, LinkedHashMap.class }; private Loader keyLoader; private Loader valueLoader; private final Loader itemsLoader; private final Loader entryLoader; public void reset(BeanT bean) throws AccessorException { this.acc.set(bean, null); } public String getIdValue(BeanT bean) { return null; } public PropertyKind getKind() { return PropertyKind.MAP; } public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) { this.keyLoader = this.keyBeanInfo.getLoader(chain.context, true);
/*     */     this.valueLoader = this.valueBeanInfo.getLoader(chain.context, true);
/*     */     handlers.put(this.tagName, new ChildLoader(this.itemsLoader, null)); } private static final class ReceiverImpl implements Receiver
/*     */   {
/* 252 */     public ReceiverImpl(int index) { this.index = index; }
/*     */      private final int index;
/*     */     public void receive(UnmarshallingContext.State state, Object o) {
/* 255 */       ((Object[])state.target)[this.index] = o;
/*     */     }
/*     */   }
/*     */   
/* 259 */   private static final Receiver keyReceiver = new ReceiverImpl(0);
/* 260 */   private static final Receiver valueReceiver = new ReceiverImpl(1);
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 264 */     Map map = (Map)this.acc.get(o);
/* 265 */     if (map != null) {
/* 266 */       bareStartTag(w, this.tagName, map);
/* 267 */       for (Map.Entry e : map.entrySet()) {
/* 268 */         bareStartTag(w, this.entryTag, null);
/*     */         
/* 270 */         Object key = e.getKey();
/* 271 */         if (key != null) {
/* 272 */           w.startElement(this.keyTag, key);
/* 273 */           w.childAsXsiType(key, this.fieldName, this.keyBeanInfo, false);
/* 274 */           w.endElement();
/*     */         } 
/*     */         
/* 277 */         Object value = e.getValue();
/* 278 */         if (value != null) {
/* 279 */           w.startElement(this.valueTag, value);
/* 280 */           w.childAsXsiType(value, this.fieldName, this.valueBeanInfo, false);
/* 281 */           w.endElement();
/*     */         } 
/*     */         
/* 284 */         w.endElement();
/*     */       } 
/* 286 */       w.endElement();
/*     */     }
/* 288 */     else if (this.nillable) {
/* 289 */       w.startElement(this.tagName, null);
/* 290 */       w.writeXsiNilTrue();
/* 291 */       w.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void bareStartTag(XMLSerializer w, Name tagName, Object peer) throws IOException, XMLStreamException, SAXException {
/* 296 */     w.startElement(tagName, peer);
/* 297 */     w.endNamespaceDecls(peer);
/* 298 */     w.endAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 303 */     if (this.tagName.equals(nsUri, localName))
/* 304 */       return this.acc; 
/* 305 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\SingleMapNodeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */