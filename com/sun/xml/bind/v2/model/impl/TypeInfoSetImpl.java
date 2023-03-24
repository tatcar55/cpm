/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.core.BuiltinLeafInfo;
/*     */ import com.sun.xml.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.bind.v2.model.core.LeafInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.Ref;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.RuntimeUtil;
/*     */ import com.sun.xml.bind.v2.util.FlattenIterator;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.annotation.XmlNs;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TypeInfoSetImpl<T, C, F, M>
/*     */   implements TypeInfoSet<T, C, F, M>
/*     */ {
/*     */   @XmlTransient
/*     */   public final Navigator<T, C, F, M> nav;
/*     */   @XmlTransient
/*     */   public final AnnotationReader<T, C, F, M> reader;
/*  96 */   private final Map<T, BuiltinLeafInfo<T, C>> builtins = new LinkedHashMap<T, BuiltinLeafInfo<T, C>>();
/*     */ 
/*     */ 
/*     */   
/* 100 */   private final Map<C, EnumLeafInfoImpl<T, C, F, M>> enums = new LinkedHashMap<C, EnumLeafInfoImpl<T, C, F, M>>();
/*     */ 
/*     */ 
/*     */   
/* 104 */   private final Map<T, ArrayInfoImpl<T, C, F, M>> arrays = new LinkedHashMap<T, ArrayInfoImpl<T, C, F, M>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlJavaTypeAdapter(RuntimeUtil.ToStringAdapter.class)
/* 115 */   private final Map<C, ClassInfoImpl<T, C, F, M>> beans = new LinkedHashMap<C, ClassInfoImpl<T, C, F, M>>();
/*     */ 
/*     */   
/*     */   @XmlTransient
/* 119 */   private final Map<C, ClassInfoImpl<T, C, F, M>> beansView = Collections.unmodifiableMap(this.beans);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private final Map<C, Map<QName, ElementInfoImpl<T, C, F, M>>> elementMappings = new LinkedHashMap<C, Map<QName, ElementInfoImpl<T, C, F, M>>>();
/*     */ 
/*     */   
/* 129 */   private final Iterable<? extends ElementInfoImpl<T, C, F, M>> allElements = (Iterable)new Iterable<ElementInfoImpl<ElementInfoImpl<T, C, F, M>, C, F, M>>()
/*     */     {
/*     */       public Iterator<ElementInfoImpl<T, C, F, M>> iterator() {
/* 132 */         return (Iterator<ElementInfoImpl<T, C, F, M>>)new FlattenIterator(TypeInfoSetImpl.this.elementMappings.values());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final NonElement<T, C> anyType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Map<String, String>> xmlNsCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeInfoSetImpl(Navigator<T, C, F, M> nav, AnnotationReader<T, C, F, M> reader, Map<T, ? extends BuiltinLeafInfoImpl<T, C>> leaves) {
/* 154 */     this.nav = nav;
/* 155 */     this.reader = reader;
/* 156 */     this.builtins.putAll((Map)leaves);
/*     */     
/* 158 */     this.anyType = createAnyType();
/*     */ 
/*     */     
/* 161 */     for (Map.Entry<Class<?>, Class<?>> e : (Iterable<Map.Entry<Class<?>, Class<?>>>)RuntimeUtil.primitiveToBox.entrySet()) {
/* 162 */       this.builtins.put((T)nav.getPrimitive(e.getKey()), leaves.get(nav.ref(e.getValue())));
/*     */     }
/*     */ 
/*     */     
/* 166 */     this.elementMappings.put(null, new LinkedHashMap<QName, ElementInfoImpl<T, C, F, M>>());
/*     */   }
/*     */   
/*     */   protected NonElement<T, C> createAnyType() {
/* 170 */     return new AnyTypeImpl<T, C>(this.nav);
/*     */   }
/*     */   
/*     */   public Navigator<T, C, F, M> getNavigator() {
/* 174 */     return this.nav;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ClassInfoImpl<T, C, F, M> ci) {
/* 181 */     this.beans.put(ci.getClazz(), ci);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(EnumLeafInfoImpl<T, C, F, M> li) {
/* 188 */     this.enums.put(li.clazz, li);
/*     */   }
/*     */   
/*     */   public void add(ArrayInfoImpl<T, C, F, M> ai) {
/* 192 */     this.arrays.put(ai.getType(), ai);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(T type) {
/* 203 */     type = (T)this.nav.erasure(type);
/*     */     
/* 205 */     LeafInfo<T, C> l = (LeafInfo<T, C>)this.builtins.get(type);
/* 206 */     if (l != null) return (NonElement<T, C>)l;
/*     */     
/* 208 */     if (this.nav.isArray(type)) {
/* 209 */       return (NonElement<T, C>)this.arrays.get(type);
/*     */     }
/*     */     
/* 212 */     C d = (C)this.nav.asDecl(type);
/* 213 */     if (d == null) return null; 
/* 214 */     return getClassInfo(d);
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getAnyTypeInfo() {
/* 218 */     return this.anyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(Ref<T, C> ref) {
/* 226 */     assert !ref.valueList;
/* 227 */     C c = (C)this.nav.asDecl(ref.type);
/* 228 */     if (c != null && this.reader.getClassAnnotation(XmlRegistry.class, c, null) != null) {
/* 229 */       return null;
/*     */     }
/* 231 */     return getTypeInfo((T)ref.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<C, ? extends ClassInfoImpl<T, C, F, M>> beans() {
/* 238 */     return this.beansView;
/*     */   }
/*     */   
/*     */   public Map<T, ? extends BuiltinLeafInfo<T, C>> builtins() {
/* 242 */     return this.builtins;
/*     */   }
/*     */   
/*     */   public Map<C, ? extends EnumLeafInfoImpl<T, C, F, M>> enums() {
/* 246 */     return this.enums;
/*     */   }
/*     */   
/*     */   public Map<? extends T, ? extends ArrayInfoImpl<T, C, F, M>> arrays() {
/* 250 */     return this.arrays;
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
/*     */   public NonElement<T, C> getClassInfo(C type) {
/* 265 */     LeafInfo<T, C> l = (LeafInfo<T, C>)this.builtins.get(this.nav.use(type));
/* 266 */     if (l != null) return (NonElement<T, C>)l;
/*     */     
/* 268 */     l = (LeafInfo<T, C>)this.enums.get(type);
/* 269 */     if (l != null) return (NonElement<T, C>)l;
/*     */     
/* 271 */     if (this.nav.asDecl(Object.class).equals(type)) {
/* 272 */       return this.anyType;
/*     */     }
/* 274 */     return (NonElement<T, C>)this.beans.get(type);
/*     */   }
/*     */   
/*     */   public ElementInfoImpl<T, C, F, M> getElementInfo(C scope, QName name) {
/* 278 */     while (scope != null) {
/* 279 */       Map<QName, ElementInfoImpl<T, C, F, M>> m = this.elementMappings.get(scope);
/* 280 */       if (m != null) {
/* 281 */         ElementInfoImpl<T, C, F, M> r = m.get(name);
/* 282 */         if (r != null) return r; 
/*     */       } 
/* 284 */       scope = (C)this.nav.getSuperClass(scope);
/*     */     } 
/* 286 */     return (ElementInfoImpl<T, C, F, M>)((Map)this.elementMappings.get(null)).get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(ElementInfoImpl<T, C, F, M> ei, ModelBuilder<T, C, F, M> builder) {
/* 294 */     C scope = null;
/* 295 */     if (ei.getScope() != null) {
/* 296 */       scope = (C)ei.getScope().getClazz();
/*     */     }
/* 298 */     Map<QName, ElementInfoImpl<T, C, F, M>> m = this.elementMappings.get(scope);
/* 299 */     if (m == null) {
/* 300 */       this.elementMappings.put(scope, m = new LinkedHashMap<QName, ElementInfoImpl<T, C, F, M>>());
/*     */     }
/* 302 */     ElementInfoImpl<T, C, F, M> existing = m.put(ei.getElementName(), ei);
/*     */     
/* 304 */     if (existing != null) {
/* 305 */       QName en = ei.getElementName();
/* 306 */       builder.reportError(new IllegalAnnotationException(Messages.CONFLICTING_XML_ELEMENT_MAPPING.format(new Object[] { en.getNamespaceURI(), en.getLocalPart() }, ), ei, existing));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<QName, ? extends ElementInfoImpl<T, C, F, M>> getElementMappings(C scope) {
/* 314 */     return this.elementMappings.get(scope);
/*     */   }
/*     */   
/*     */   public Iterable<? extends ElementInfoImpl<T, C, F, M>> getAllElements() {
/* 318 */     return this.allElements;
/*     */   }
/*     */   
/*     */   public Map<String, String> getXmlNs(String namespaceUri) {
/* 322 */     if (this.xmlNsCache == null) {
/* 323 */       this.xmlNsCache = new HashMap<String, Map<String, String>>();
/*     */       
/* 325 */       for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 326 */         XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 327 */         if (xs == null) {
/*     */           continue;
/*     */         }
/* 330 */         String uri = xs.namespace();
/* 331 */         Map<String, String> m = this.xmlNsCache.get(uri);
/* 332 */         if (m == null) {
/* 333 */           this.xmlNsCache.put(uri, m = new HashMap<String, String>());
/*     */         }
/* 335 */         for (XmlNs xns : xs.xmlns()) {
/* 336 */           m.put(xns.prefix(), xns.namespaceURI());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     Map<String, String> r = this.xmlNsCache.get(namespaceUri);
/* 342 */     if (r != null) return r; 
/* 343 */     return Collections.emptyMap();
/*     */   }
/*     */   
/*     */   public Map<String, String> getSchemaLocations() {
/* 347 */     Map<String, String> r = new HashMap<String, String>();
/* 348 */     for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 349 */       XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 350 */       if (xs == null) {
/*     */         continue;
/*     */       }
/* 353 */       String loc = xs.location();
/* 354 */       if (loc.equals("##generate")) {
/*     */         continue;
/*     */       }
/* 357 */       r.put(xs.namespace(), loc);
/*     */     } 
/* 359 */     return r;
/*     */   }
/*     */   
/*     */   public final XmlNsForm getElementFormDefault(String nsUri) {
/* 363 */     for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 364 */       XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 365 */       if (xs == null) {
/*     */         continue;
/*     */       }
/* 368 */       if (!xs.namespace().equals(nsUri)) {
/*     */         continue;
/*     */       }
/* 371 */       XmlNsForm xnf = xs.elementFormDefault();
/* 372 */       if (xnf != XmlNsForm.UNSET)
/* 373 */         return xnf; 
/*     */     } 
/* 375 */     return XmlNsForm.UNSET;
/*     */   }
/*     */   
/*     */   public final XmlNsForm getAttributeFormDefault(String nsUri) {
/* 379 */     for (ClassInfoImpl<T, C, F, M> ci : beans().values()) {
/* 380 */       XmlSchema xs = (XmlSchema)this.reader.getPackageAnnotation(XmlSchema.class, ci.getClazz(), null);
/* 381 */       if (xs == null) {
/*     */         continue;
/*     */       }
/* 384 */       if (!xs.namespace().equals(nsUri)) {
/*     */         continue;
/*     */       }
/* 387 */       XmlNsForm xnf = xs.attributeFormDefault();
/* 388 */       if (xnf != XmlNsForm.UNSET)
/* 389 */         return xnf; 
/*     */     } 
/* 391 */     return XmlNsForm.UNSET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(Result out) throws JAXBException {
/* 402 */     JAXBContext context = JAXBContext.newInstance(new Class[] { getClass() });
/* 403 */     Marshaller m = context.createMarshaller();
/* 404 */     m.marshal(this, out);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\TypeInfoSetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */