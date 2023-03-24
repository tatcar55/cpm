/*      */ package com.sun.xml.bind.v2.runtime;
/*      */ 
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Pool;
/*      */ import com.sun.xml.bind.api.AccessorException;
/*      */ import com.sun.xml.bind.api.Bridge;
/*      */ import com.sun.xml.bind.api.BridgeContext;
/*      */ import com.sun.xml.bind.api.CompositeStructure;
/*      */ import com.sun.xml.bind.api.ErrorListener;
/*      */ import com.sun.xml.bind.api.JAXBRIContext;
/*      */ import com.sun.xml.bind.api.RawAccessor;
/*      */ import com.sun.xml.bind.api.TypeReference;
/*      */ import com.sun.xml.bind.unmarshaller.DOMScanner;
/*      */ import com.sun.xml.bind.unmarshaller.InfosetScanner;
/*      */ import com.sun.xml.bind.util.Which;
/*      */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*      */ import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;
/*      */ import com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
/*      */ import com.sun.xml.bind.v2.model.core.Adapter;
/*      */ import com.sun.xml.bind.v2.model.core.NonElement;
/*      */ import com.sun.xml.bind.v2.model.core.Ref;
/*      */ import com.sun.xml.bind.v2.model.core.TypeInfoSet;
/*      */ import com.sun.xml.bind.v2.model.impl.RuntimeBuiltinLeafInfoImpl;
/*      */ import com.sun.xml.bind.v2.model.impl.RuntimeModelBuilder;
/*      */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*      */ import com.sun.xml.bind.v2.model.nav.ReflectionNavigator;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeArrayInfo;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeClassInfo;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementInfo;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeEnumLeafInfo;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeLeafInfo;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*      */ import com.sun.xml.bind.v2.runtime.output.Encoded;
/*      */ import com.sun.xml.bind.v2.runtime.property.AttributeProperty;
/*      */ import com.sun.xml.bind.v2.runtime.property.Property;
/*      */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.TagName;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*      */ import com.sun.xml.bind.v2.schemagen.XmlSchemaGenerator;
/*      */ import com.sun.xml.bind.v2.util.EditDistance;
/*      */ import com.sun.xml.bind.v2.util.QNameMap;
/*      */ import com.sun.xml.bind.v2.util.XmlFactory;
/*      */ import com.sun.xml.txw2.output.ResultFactory;
/*      */ import java.io.IOException;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import javax.xml.bind.Binder;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.JAXBIntrospector;
/*      */ import javax.xml.bind.Marshaller;
/*      */ import javax.xml.bind.SchemaOutputResolver;
/*      */ import javax.xml.bind.Unmarshaller;
/*      */ import javax.xml.bind.Validator;
/*      */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*      */ import javax.xml.bind.annotation.XmlList;
/*      */ import javax.xml.bind.annotation.XmlNs;
/*      */ import javax.xml.bind.annotation.XmlSchema;
/*      */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*      */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.FactoryConfigurationError;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import javax.xml.transform.sax.SAXTransformerFactory;
/*      */ import javax.xml.transform.sax.TransformerHandler;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class JAXBContextImpl
/*      */   extends JAXBRIContext
/*      */ {
/*      */   private final Map<TypeReference, Bridge> bridges;
/*      */   private static volatile SAXTransformerFactory tf;
/*      */   private static DocumentBuilder db;
/*      */   private final QNameMap<JaxBeanInfo> rootMap;
/*      */   private final HashMap<QName, JaxBeanInfo> typeMap;
/*      */   private final Map<Class, JaxBeanInfo> beanInfoMap;
/*      */   protected Map<RuntimeTypeInfo, JaxBeanInfo> beanInfos;
/*      */   private final Map<Class, Map<QName, ElementBeanInfoImpl>> elements;
/*      */   public final Pool<Marshaller> marshallerPool;
/*      */   public final Pool<Unmarshaller> unmarshallerPool;
/*      */   public NameBuilder nameBuilder;
/*      */   public final NameList nameList;
/*      */   private final String defaultNsUri;
/*      */   private final Class[] classes;
/*      */   protected final boolean c14nSupport;
/*      */   public final boolean xmlAccessorFactorySupport;
/*      */   public final boolean allNillable;
/*      */   public final boolean retainPropertyInfo;
/*      */   public final boolean supressAccessorWarnings;
/*      */   public final boolean improvedXsiTypeHandling;
/*      */   public final boolean disableSecurityProcessing;
/*      */   private WeakReference<RuntimeTypeInfoSet> typeInfoSetCache;
/*      */   @NotNull
/*      */   private RuntimeAnnotationReader annotationReader;
/*      */   private boolean hasSwaRef;
/*      */   @NotNull
/*      */   private final Map<Class, Class> subclassReplacements;
/*      */   public final boolean fastBoot;
/*      */   private Set<XmlNs> xmlNsSet;
/*      */   private Encoded[] utf8nameTable;
/*      */   
/*      */   public Set<XmlNs> getXmlNsSet() {
/*  276 */     return this.xmlNsSet;
/*      */   } private JAXBContextImpl(JAXBContextBuilder builder) throws JAXBException { boolean bool; this.bridges = new LinkedHashMap<TypeReference, Bridge>(); this.rootMap = new QNameMap(); this.typeMap = new HashMap<QName, JaxBeanInfo>(); this.beanInfoMap = (Map)new LinkedHashMap<Class<?>, JaxBeanInfo>(); this.beanInfos = new LinkedHashMap<RuntimeTypeInfo, JaxBeanInfo>(); this.elements = (Map)new LinkedHashMap<Class<?>, Map<QName, ElementBeanInfoImpl>>(); this.marshallerPool = (Pool<Marshaller>)new Pool.Impl<Marshaller>() { @NotNull protected Marshaller create() { return JAXBContextImpl.this.createMarshaller(); } }
/*      */       ; this.unmarshallerPool = (Pool<Unmarshaller>)new Pool.Impl<Unmarshaller>() { @NotNull protected Unmarshaller create() { return (Unmarshaller)JAXBContextImpl.this.createUnmarshaller(); } };
/*      */     this.nameBuilder = new NameBuilder();
/*      */     this.xmlNsSet = null;
/*  281 */     this.defaultNsUri = builder.defaultNsUri;
/*  282 */     this.retainPropertyInfo = builder.retainPropertyInfo;
/*  283 */     this.annotationReader = builder.annotationReader;
/*  284 */     this.subclassReplacements = builder.subclassReplacements;
/*  285 */     this.c14nSupport = builder.c14nSupport;
/*  286 */     this.classes = builder.classes;
/*  287 */     this.xmlAccessorFactorySupport = builder.xmlAccessorFactorySupport;
/*  288 */     this.allNillable = builder.allNillable;
/*  289 */     this.supressAccessorWarnings = builder.supressAccessorWarnings;
/*  290 */     this.improvedXsiTypeHandling = builder.improvedXsiTypeHandling;
/*  291 */     this.disableSecurityProcessing = builder.disableSecurityProcessing;
/*      */     
/*  293 */     Collection<TypeReference> typeRefs = builder.typeRefs;
/*      */ 
/*      */     
/*      */     try {
/*  297 */       bool = Boolean.getBoolean(JAXBContextImpl.class.getName() + ".fastBoot");
/*  298 */     } catch (SecurityException e) {
/*  299 */       bool = false;
/*      */     } 
/*  301 */     this.fastBoot = bool;
/*      */     
/*  303 */     RuntimeTypeInfoSet typeSet = getTypeInfoSet();
/*      */ 
/*      */     
/*  306 */     this.elements.put(null, new LinkedHashMap<QName, ElementBeanInfoImpl>());
/*      */ 
/*      */     
/*  309 */     for (RuntimeBuiltinLeafInfoImpl runtimeBuiltinLeafInfoImpl : RuntimeBuiltinLeafInfoImpl.builtinBeanInfos) {
/*  310 */       LeafBeanInfoImpl<?> bi = new LeafBeanInfoImpl(this, (RuntimeLeafInfo)runtimeBuiltinLeafInfoImpl);
/*  311 */       this.beanInfoMap.put(runtimeBuiltinLeafInfoImpl.getClazz(), bi);
/*  312 */       for (QName t : bi.getTypeNames()) {
/*  313 */         this.typeMap.put(t, bi);
/*      */       }
/*      */     } 
/*  316 */     for (RuntimeEnumLeafInfo e : typeSet.enums().values()) {
/*  317 */       JaxBeanInfo<?> bi = getOrCreate(e);
/*  318 */       for (QName qn : bi.getTypeNames())
/*  319 */         this.typeMap.put(qn, bi); 
/*  320 */       if (e.isElement()) {
/*  321 */         this.rootMap.put(e.getElementName(), bi);
/*      */       }
/*      */     } 
/*  324 */     for (RuntimeArrayInfo a : typeSet.arrays().values()) {
/*  325 */       JaxBeanInfo<?> ai = getOrCreate(a);
/*  326 */       for (QName qn : ai.getTypeNames()) {
/*  327 */         this.typeMap.put(qn, ai);
/*      */       }
/*      */     } 
/*  330 */     for (Map.Entry<Class<?>, ? extends RuntimeClassInfo> e : (Iterable<Map.Entry<Class<?>, ? extends RuntimeClassInfo>>)typeSet.beans().entrySet()) {
/*  331 */       ClassBeanInfoImpl<?> bi = getOrCreate(e.getValue());
/*      */       
/*  333 */       XmlSchema xs = (XmlSchema)this.annotationReader.getPackageAnnotation(XmlSchema.class, e.getKey(), null);
/*  334 */       if (xs != null && 
/*  335 */         xs.xmlns() != null && (xs.xmlns()).length > 0) {
/*  336 */         if (this.xmlNsSet == null)
/*  337 */           this.xmlNsSet = new HashSet<XmlNs>(); 
/*  338 */         this.xmlNsSet.addAll(Arrays.asList(xs.xmlns()));
/*      */       } 
/*      */ 
/*      */       
/*  342 */       if (bi.isElement()) {
/*  343 */         this.rootMap.put(((RuntimeClassInfo)e.getValue()).getElementName(), bi);
/*      */       }
/*  345 */       for (QName qn : bi.getTypeNames()) {
/*  346 */         this.typeMap.put(qn, bi);
/*      */       }
/*      */     } 
/*      */     
/*  350 */     for (RuntimeElementInfo n : typeSet.getAllElements()) {
/*  351 */       ElementBeanInfoImpl bi = getOrCreate(n);
/*  352 */       if (n.getScope() == null) {
/*  353 */         this.rootMap.put(n.getElementName(), bi);
/*      */       }
/*  355 */       RuntimeClassInfo scope = n.getScope();
/*  356 */       Class scopeClazz = (scope == null) ? null : (Class)scope.getClazz();
/*  357 */       Map<QName, ElementBeanInfoImpl> m = this.elements.get(scopeClazz);
/*  358 */       if (m == null) {
/*  359 */         m = new LinkedHashMap<QName, ElementBeanInfoImpl>();
/*  360 */         this.elements.put(scopeClazz, m);
/*      */       } 
/*  362 */       m.put(n.getElementName(), bi);
/*      */     } 
/*      */ 
/*      */     
/*  366 */     this.beanInfoMap.put(JAXBElement.class, new ElementBeanInfoImpl(this));
/*      */     
/*  368 */     this.beanInfoMap.put(CompositeStructure.class, new CompositeStructureBeanInfo(this));
/*      */     
/*  370 */     getOrCreate((RuntimeTypeInfo)typeSet.getAnyTypeInfo());
/*      */ 
/*      */     
/*  373 */     for (JaxBeanInfo bi : this.beanInfos.values()) {
/*  374 */       bi.link(this);
/*      */     }
/*      */     
/*  377 */     for (Map.Entry<Class<?>, Class<?>> e : RuntimeUtil.primitiveToBox.entrySet()) {
/*  378 */       this.beanInfoMap.put(e.getKey(), this.beanInfoMap.get(e.getValue()));
/*      */     }
/*      */     
/*  381 */     ReflectionNavigator nav = typeSet.getNavigator();
/*      */     
/*  383 */     for (TypeReference tr : typeRefs) {
/*  384 */       InternalBridge<?> bridge; XmlJavaTypeAdapter xjta = (XmlJavaTypeAdapter)tr.get(XmlJavaTypeAdapter.class);
/*  385 */       Adapter<Type, Class<?>> a = null;
/*  386 */       XmlList xl = (XmlList)tr.get(XmlList.class);
/*      */ 
/*      */       
/*  389 */       Class<?> erasedType = nav.erasure(tr.type);
/*      */       
/*  391 */       if (xjta != null) {
/*  392 */         a = new Adapter(xjta.value(), (Navigator)nav);
/*      */       }
/*  394 */       if (tr.get(XmlAttachmentRef.class) != null) {
/*  395 */         a = new Adapter(SwaRefAdapter.class, (Navigator)nav);
/*  396 */         this.hasSwaRef = true;
/*      */       } 
/*      */       
/*  399 */       if (a != null) {
/*  400 */         erasedType = nav.erasure((Type)a.defaultType);
/*      */       }
/*      */       
/*  403 */       Name name = this.nameBuilder.createElementName(tr.tagName);
/*      */ 
/*      */       
/*  406 */       if (xl == null) {
/*  407 */         bridge = new BridgeImpl(this, name, getBeanInfo(erasedType, true), tr);
/*      */       } else {
/*  409 */         bridge = new BridgeImpl(this, name, new ValueListBeanInfoImpl(this, erasedType), tr);
/*      */       } 
/*  411 */       if (a != null) {
/*  412 */         bridge = new BridgeAdapter<Object, Object>(bridge, (Class<? extends XmlAdapter<?, ?>>)a.adapterType);
/*      */       }
/*  414 */       this.bridges.put(tr, bridge);
/*      */     } 
/*      */     
/*  417 */     this.nameList = this.nameBuilder.conclude();
/*      */     
/*  419 */     for (JaxBeanInfo bi : this.beanInfos.values()) {
/*  420 */       bi.wrapUp();
/*      */     }
/*      */     
/*  423 */     this.nameBuilder = null;
/*  424 */     this.beanInfos = null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSwaRef() {
/*  431 */     return this.hasSwaRef;
/*      */   }
/*      */   
/*      */   public RuntimeTypeInfoSet getRuntimeTypeInfoSet() {
/*      */     try {
/*  436 */       return getTypeInfoSet();
/*  437 */     } catch (IllegalAnnotationsException e) {
/*      */       
/*  439 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RuntimeTypeInfoSet getTypeInfoSet() throws IllegalAnnotationsException {
/*  449 */     if (this.typeInfoSetCache != null) {
/*  450 */       RuntimeTypeInfoSet runtimeTypeInfoSet = this.typeInfoSetCache.get();
/*  451 */       if (runtimeTypeInfoSet != null) {
/*  452 */         return runtimeTypeInfoSet;
/*      */       }
/*      */     } 
/*  455 */     RuntimeModelBuilder builder = new RuntimeModelBuilder(this, this.annotationReader, this.subclassReplacements, this.defaultNsUri);
/*      */     
/*  457 */     IllegalAnnotationsException.Builder errorHandler = new IllegalAnnotationsException.Builder();
/*  458 */     builder.setErrorHandler(errorHandler);
/*      */     
/*  460 */     for (Class<CompositeStructure> c : this.classes) {
/*  461 */       if (c != CompositeStructure.class)
/*      */       {
/*      */ 
/*      */         
/*  465 */         builder.getTypeInfo(new Ref(c));
/*      */       }
/*      */     } 
/*  468 */     this.hasSwaRef |= builder.hasSwaRef;
/*  469 */     RuntimeTypeInfoSet r = builder.link();
/*      */     
/*  471 */     errorHandler.check();
/*  472 */     assert r != null : "if no error was reported, the link must be a success";
/*      */     
/*  474 */     this.typeInfoSetCache = new WeakReference<RuntimeTypeInfoSet>(r);
/*      */     
/*  476 */     return r;
/*      */   }
/*      */ 
/*      */   
/*      */   public ElementBeanInfoImpl getElement(Class scope, QName name) {
/*  481 */     Map<QName, ElementBeanInfoImpl> m = this.elements.get(scope);
/*  482 */     if (m != null) {
/*  483 */       ElementBeanInfoImpl bi = m.get(name);
/*  484 */       if (bi != null)
/*  485 */         return bi; 
/*      */     } 
/*  487 */     m = this.elements.get(null);
/*  488 */     return m.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ElementBeanInfoImpl getOrCreate(RuntimeElementInfo rei) {
/*  496 */     JaxBeanInfo bi = this.beanInfos.get(rei);
/*  497 */     if (bi != null) return (ElementBeanInfoImpl)bi;
/*      */ 
/*      */     
/*  500 */     return new ElementBeanInfoImpl(this, rei);
/*      */   }
/*      */   
/*      */   protected JaxBeanInfo getOrCreate(RuntimeEnumLeafInfo eli) {
/*  504 */     JaxBeanInfo bi = this.beanInfos.get(eli);
/*  505 */     if (bi != null) return bi; 
/*  506 */     bi = new LeafBeanInfoImpl(this, (RuntimeLeafInfo)eli);
/*  507 */     this.beanInfoMap.put(bi.jaxbType, bi);
/*  508 */     return bi;
/*      */   }
/*      */   
/*      */   protected ClassBeanInfoImpl getOrCreate(RuntimeClassInfo ci) {
/*  512 */     ClassBeanInfoImpl bi = (ClassBeanInfoImpl)this.beanInfos.get(ci);
/*  513 */     if (bi != null) return bi; 
/*  514 */     bi = new ClassBeanInfoImpl(this, ci);
/*  515 */     this.beanInfoMap.put(bi.jaxbType, bi);
/*  516 */     return bi;
/*      */   }
/*      */   
/*      */   protected JaxBeanInfo getOrCreate(RuntimeArrayInfo ai) {
/*  520 */     JaxBeanInfo abi = this.beanInfos.get(ai);
/*  521 */     if (abi != null) return abi;
/*      */     
/*  523 */     abi = new ArrayBeanInfoImpl(this, ai);
/*      */     
/*  525 */     this.beanInfoMap.put(ai.getType(), abi);
/*  526 */     return abi;
/*      */   }
/*      */   
/*      */   public JaxBeanInfo getOrCreate(RuntimeTypeInfo e) {
/*  530 */     if (e instanceof RuntimeElementInfo)
/*  531 */       return getOrCreate((RuntimeElementInfo)e); 
/*  532 */     if (e instanceof RuntimeClassInfo)
/*  533 */       return getOrCreate((RuntimeClassInfo)e); 
/*  534 */     if (e instanceof RuntimeLeafInfo) {
/*  535 */       JaxBeanInfo bi = this.beanInfos.get(e);
/*  536 */       assert bi != null;
/*  537 */       return bi;
/*      */     } 
/*  539 */     if (e instanceof RuntimeArrayInfo)
/*  540 */       return getOrCreate((RuntimeArrayInfo)e); 
/*  541 */     if (e.getType() == Object.class) {
/*      */       
/*  543 */       JaxBeanInfo bi = this.beanInfoMap.get(Object.class);
/*  544 */       if (bi == null) {
/*  545 */         bi = new AnyTypeBeanInfo(this, e);
/*  546 */         this.beanInfoMap.put(Object.class, bi);
/*      */       } 
/*  548 */       return bi;
/*      */     } 
/*      */     
/*  551 */     throw new IllegalArgumentException();
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
/*      */   public final JaxBeanInfo getBeanInfo(Object o) {
/*  566 */     for (Class<?> c = o.getClass(); c != Object.class; c = c.getSuperclass()) {
/*  567 */       JaxBeanInfo bi = this.beanInfoMap.get(c);
/*  568 */       if (bi != null) return bi; 
/*      */     } 
/*  570 */     if (o instanceof org.w3c.dom.Element)
/*  571 */       return this.beanInfoMap.get(Object.class); 
/*  572 */     for (Class<?> clazz : o.getClass().getInterfaces()) {
/*  573 */       JaxBeanInfo bi = this.beanInfoMap.get(clazz);
/*  574 */       if (bi != null) return bi; 
/*      */     } 
/*  576 */     return null;
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
/*      */   public final JaxBeanInfo getBeanInfo(Object o, boolean fatal) throws JAXBException {
/*  588 */     JaxBeanInfo bi = getBeanInfo(o);
/*  589 */     if (bi != null) return bi; 
/*  590 */     if (fatal) {
/*  591 */       if (o instanceof Document)
/*  592 */         throw new JAXBException(Messages.ELEMENT_NEEDED_BUT_FOUND_DOCUMENT.format(new Object[] { o.getClass() })); 
/*  593 */       throw new JAXBException(Messages.UNKNOWN_CLASS.format(new Object[] { o.getClass() }));
/*      */     } 
/*  595 */     return null;
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
/*      */   public final <T> JaxBeanInfo<T> getBeanInfo(Class<T> clazz) {
/*  609 */     return this.beanInfoMap.get(clazz);
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
/*      */   public final <T> JaxBeanInfo<T> getBeanInfo(Class<T> clazz, boolean fatal) throws JAXBException {
/*  621 */     JaxBeanInfo<T> bi = getBeanInfo(clazz);
/*  622 */     if (bi != null) return bi; 
/*  623 */     if (fatal)
/*  624 */       throw new JAXBException(clazz.getName() + " is not known to this context"); 
/*  625 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Loader selectRootLoader(UnmarshallingContext.State state, TagName tag) {
/*  636 */     JaxBeanInfo beanInfo = (JaxBeanInfo)this.rootMap.get(tag.uri, tag.local);
/*  637 */     if (beanInfo == null) {
/*  638 */       return null;
/*      */     }
/*  640 */     return beanInfo.getLoader(this, true);
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
/*      */   public JaxBeanInfo getGlobalType(QName name) {
/*  652 */     return this.typeMap.get(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNearestTypeName(QName name) {
/*  663 */     String[] all = new String[this.typeMap.size()];
/*  664 */     int i = 0;
/*  665 */     for (QName qn : this.typeMap.keySet()) {
/*  666 */       if (qn.getLocalPart().equals(name.getLocalPart()))
/*  667 */         return qn.toString(); 
/*  668 */       all[i++] = qn.toString();
/*      */     } 
/*      */     
/*  671 */     String nearest = EditDistance.findNearest(name.toString(), all);
/*      */     
/*  673 */     if (EditDistance.editDistance(nearest, name.toString()) > 10) {
/*  674 */       return null;
/*      */     }
/*  676 */     return nearest;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<QName> getValidRootNames() {
/*  684 */     Set<QName> r = new TreeSet<QName>(QNAME_COMPARATOR);
/*  685 */     for (QNameMap.Entry e : this.rootMap.entrySet()) {
/*  686 */       r.add(e.createQName());
/*      */     }
/*  688 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Encoded[] getUTF8NameTable() {
/*  697 */     if (this.utf8nameTable == null) {
/*  698 */       Encoded[] x = new Encoded[this.nameList.localNames.length];
/*  699 */       for (int i = 0; i < x.length; i++) {
/*  700 */         Encoded e = new Encoded(this.nameList.localNames[i]);
/*  701 */         e.compact();
/*  702 */         x[i] = e;
/*      */       } 
/*  704 */       this.utf8nameTable = x;
/*      */     } 
/*  706 */     return this.utf8nameTable;
/*      */   }
/*      */   
/*      */   public int getNumberOfLocalNames() {
/*  710 */     return this.nameList.localNames.length;
/*      */   }
/*      */   
/*      */   public int getNumberOfElementNames() {
/*  714 */     return this.nameList.numberOfElementNames;
/*      */   }
/*      */   
/*      */   public int getNumberOfAttributeNames() {
/*  718 */     return this.nameList.numberOfAttributeNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Transformer createTransformer(boolean disableSecureProcessing) {
/*      */     try {
/*  726 */       if (tf == null) {
/*  727 */         synchronized (JAXBContextImpl.class) {
/*  728 */           if (tf == null) {
/*  729 */             tf = (SAXTransformerFactory)XmlFactory.createTransformerFactory(disableSecureProcessing);
/*      */           }
/*      */         } 
/*      */       }
/*  733 */       return tf.newTransformer();
/*  734 */     } catch (TransformerConfigurationException e) {
/*  735 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TransformerHandler createTransformerHandler(boolean disableSecureProcessing) {
/*      */     try {
/*  744 */       if (tf == null) {
/*  745 */         synchronized (JAXBContextImpl.class) {
/*  746 */           if (tf == null) {
/*  747 */             tf = (SAXTransformerFactory)XmlFactory.createTransformerFactory(disableSecureProcessing);
/*      */           }
/*      */         } 
/*      */       }
/*  751 */       return tf.newTransformerHandler();
/*  752 */     } catch (TransformerConfigurationException e) {
/*  753 */       throw new Error(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Document createDom(boolean disableSecurityProcessing) {
/*  761 */     synchronized (JAXBContextImpl.class) {
/*  762 */       if (db == null) {
/*      */         try {
/*  764 */           DocumentBuilderFactory dbf = XmlFactory.createDocumentBuilderFactory(disableSecurityProcessing);
/*  765 */           db = dbf.newDocumentBuilder();
/*  766 */         } catch (ParserConfigurationException e) {
/*      */           
/*  768 */           throw new FactoryConfigurationError(e);
/*      */         } 
/*      */       }
/*  771 */       return db.newDocument();
/*      */     } 
/*      */   }
/*      */   
/*      */   public MarshallerImpl createMarshaller() {
/*  776 */     return new MarshallerImpl(this, null);
/*      */   }
/*      */   
/*      */   public UnmarshallerImpl createUnmarshaller() {
/*  780 */     return new UnmarshallerImpl(this, null);
/*      */   }
/*      */   
/*      */   public Validator createValidator() {
/*  784 */     throw new UnsupportedOperationException(Messages.NOT_IMPLEMENTED_IN_2_0.format(new Object[0]));
/*      */   }
/*      */ 
/*      */   
/*      */   public JAXBIntrospector createJAXBIntrospector() {
/*  789 */     return new JAXBIntrospector() {
/*      */         public boolean isElement(Object object) {
/*  791 */           return (getElementName(object) != null);
/*      */         }
/*      */         
/*      */         public QName getElementName(Object jaxbElement) {
/*      */           try {
/*  796 */             return JAXBContextImpl.this.getElementName(jaxbElement);
/*  797 */           } catch (JAXBException e) {
/*  798 */             return null;
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private NonElement<Type, Class> getXmlType(RuntimeTypeInfoSet tis, TypeReference tr) {
/*  805 */     if (tr == null) {
/*  806 */       throw new IllegalArgumentException();
/*      */     }
/*  808 */     XmlJavaTypeAdapter xjta = (XmlJavaTypeAdapter)tr.get(XmlJavaTypeAdapter.class);
/*  809 */     XmlList xl = (XmlList)tr.get(XmlList.class);
/*      */     
/*  811 */     Ref<Type, Class<?>> ref = new Ref((AnnotationReader)this.annotationReader, (Navigator)tis.getNavigator(), tr.type, xjta, xl);
/*      */     
/*  813 */     return tis.getTypeInfo(ref);
/*      */   }
/*      */ 
/*      */   
/*      */   public void generateEpisode(Result output) {
/*  818 */     if (output == null)
/*  819 */       throw new IllegalArgumentException(); 
/*  820 */     createSchemaGenerator().writeEpisodeFile(ResultFactory.createSerializer(output));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateSchema(SchemaOutputResolver outputResolver) throws IOException {
/*  826 */     if (outputResolver == null) {
/*  827 */       throw new IOException(Messages.NULL_OUTPUT_RESOLVER.format(new Object[0]));
/*      */     }
/*  829 */     final SAXParseException[] e = new SAXParseException[1];
/*  830 */     final SAXParseException[] w = new SAXParseException[1];
/*      */     
/*  832 */     createSchemaGenerator().write(outputResolver, new ErrorListener() {
/*      */           public void error(SAXParseException exception) {
/*  834 */             e[0] = exception;
/*      */           }
/*      */           
/*      */           public void fatalError(SAXParseException exception) {
/*  838 */             e[0] = exception;
/*      */           }
/*      */           
/*      */           public void warning(SAXParseException exception) {
/*  842 */             w[0] = exception;
/*      */           }
/*      */ 
/*      */           
/*      */           public void info(SAXParseException exception) {}
/*      */         });
/*  848 */     if (e[0] != null) {
/*  849 */       IOException x = new IOException(Messages.FAILED_TO_GENERATE_SCHEMA.format(new Object[0]));
/*  850 */       x.initCause(e[0]);
/*  851 */       throw x;
/*      */     } 
/*  853 */     if (w[0] != null) {
/*  854 */       IOException x = new IOException(Messages.ERROR_PROCESSING_SCHEMA.format(new Object[0]));
/*  855 */       x.initCause(w[0]);
/*  856 */       throw x;
/*      */     } 
/*      */   }
/*      */   
/*      */   private XmlSchemaGenerator<Type, Class, Field, Method> createSchemaGenerator() {
/*      */     RuntimeTypeInfoSet tis;
/*      */     try {
/*  863 */       tis = getTypeInfoSet();
/*  864 */     } catch (IllegalAnnotationsException e) {
/*      */       
/*  866 */       throw new AssertionError(e);
/*      */     } 
/*      */     
/*  869 */     XmlSchemaGenerator<Type, Class<?>, Field, Method> xsdgen = new XmlSchemaGenerator((Navigator)tis.getNavigator(), (TypeInfoSet)tis);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     Set<QName> rootTagNames = new HashSet<QName>();
/*  876 */     for (RuntimeElementInfo ei : tis.getAllElements()) {
/*  877 */       rootTagNames.add(ei.getElementName());
/*      */     }
/*  879 */     for (RuntimeClassInfo ci : tis.beans().values()) {
/*  880 */       if (ci.isElement()) {
/*  881 */         rootTagNames.add(ci.asElement().getElementName());
/*      */       }
/*      */     } 
/*  884 */     for (TypeReference tr : this.bridges.keySet()) {
/*  885 */       if (rootTagNames.contains(tr.tagName)) {
/*      */         continue;
/*      */       }
/*  888 */       if (tr.type == void.class || tr.type == Void.class) {
/*  889 */         xsdgen.add(tr.tagName, false, null); continue;
/*      */       } 
/*  891 */       if (tr.type == CompositeStructure.class) {
/*      */         continue;
/*      */       }
/*  894 */       NonElement<Type, Class<?>> typeInfo = getXmlType(tis, tr);
/*  895 */       xsdgen.add(tr.tagName, !Navigator.REFLECTION.isPrimitive(tr.type), typeInfo);
/*      */     } 
/*      */     
/*  898 */     return xsdgen;
/*      */   }
/*      */   
/*      */   public QName getTypeName(TypeReference tr) {
/*      */     try {
/*  903 */       NonElement<Type, Class<?>> xt = getXmlType(getTypeInfoSet(), tr);
/*  904 */       if (xt == null) throw new IllegalArgumentException(); 
/*  905 */       return xt.getTypeName();
/*  906 */     } catch (IllegalAnnotationsException e) {
/*      */       
/*  908 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SchemaOutputResolver createTestResolver() {
/*  916 */     return new SchemaOutputResolver() {
/*      */         public Result createOutput(String namespaceUri, String suggestedFileName) {
/*  918 */           SAXResult r = new SAXResult(new DefaultHandler());
/*  919 */           r.setSystemId(suggestedFileName);
/*  920 */           return r;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> Binder<T> createBinder(Class<T> domType) {
/*  927 */     if (domType == Node.class) {
/*  928 */       return (Binder)createBinder();
/*      */     }
/*  930 */     return super.createBinder(domType);
/*      */   }
/*      */ 
/*      */   
/*      */   public Binder<Node> createBinder() {
/*  935 */     return new BinderImpl<Node>(this, (InfosetScanner<Node>)new DOMScanner());
/*      */   }
/*      */   
/*      */   public QName getElementName(Object o) throws JAXBException {
/*  939 */     JaxBeanInfo<Object> bi = getBeanInfo(o, true);
/*  940 */     if (!bi.isElement())
/*  941 */       return null; 
/*  942 */     return new QName(bi.getElementNamespaceURI(o), bi.getElementLocalName(o));
/*      */   }
/*      */   
/*      */   public QName getElementName(Class<?> o) throws JAXBException {
/*  946 */     JaxBeanInfo<?> bi = getBeanInfo(o, true);
/*  947 */     if (!bi.isElement())
/*  948 */       return null; 
/*  949 */     return new QName(bi.getElementNamespaceURI(o), bi.getElementLocalName(o));
/*      */   }
/*      */   
/*      */   public Bridge createBridge(TypeReference ref) {
/*  953 */     return this.bridges.get(ref);
/*      */   }
/*      */   @NotNull
/*      */   public BridgeContext createBridgeContext() {
/*  957 */     return new BridgeContextImpl(this);
/*      */   }
/*      */   
/*      */   public RawAccessor getElementPropertyAccessor(Class<?> wrapperBean, String nsUri, String localName) throws JAXBException {
/*  961 */     JaxBeanInfo<?> bi = getBeanInfo(wrapperBean, true);
/*  962 */     if (!(bi instanceof ClassBeanInfoImpl)) {
/*  963 */       throw new JAXBException(wrapperBean + " is not a bean");
/*      */     }
/*  965 */     for (ClassBeanInfoImpl cb = (ClassBeanInfoImpl)bi; cb != null; cb = cb.superClazz) {
/*  966 */       for (Property p : cb.properties) {
/*  967 */         final Accessor acc = p.getElementPropertyAccessor(nsUri, localName);
/*  968 */         if (acc != null)
/*  969 */           return new RawAccessor()
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*      */               public Object get(Object bean) throws AccessorException
/*      */               {
/*  976 */                 return acc.getUnadapted(bean);
/*      */               }
/*      */               
/*      */               public void set(Object bean, Object value) throws AccessorException {
/*  980 */                 acc.setUnadapted(bean, value);
/*      */               }
/*      */             }; 
/*      */       } 
/*      */     } 
/*  985 */     throw new JAXBException(new QName(nsUri, localName) + " is not a valid property on " + wrapperBean);
/*      */   }
/*      */   
/*      */   public List<String> getKnownNamespaceURIs() {
/*  989 */     return Arrays.asList(this.nameList.namespaceURIs);
/*      */   }
/*      */   
/*      */   public String getBuildId() {
/*  993 */     Package pkg = getClass().getPackage();
/*  994 */     if (pkg == null) return null; 
/*  995 */     return pkg.getImplementationVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1000 */     StringBuilder buf = new StringBuilder(Which.which(getClass()) + " Build-Id: " + getBuildId());
/* 1001 */     buf.append("\nClasses known to this context:\n");
/*      */     
/* 1003 */     Set<String> names = new TreeSet<String>();
/*      */     
/* 1005 */     for (Class key : this.beanInfoMap.keySet()) {
/* 1006 */       names.add(key.getName());
/*      */     }
/* 1008 */     for (String name : names) {
/* 1009 */       buf.append("  ").append(name).append('\n');
/*      */     }
/* 1011 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getXMIMEContentType(Object o) {
/* 1019 */     JaxBeanInfo bi = getBeanInfo(o);
/* 1020 */     if (!(bi instanceof ClassBeanInfoImpl)) {
/* 1021 */       return null;
/*      */     }
/* 1023 */     ClassBeanInfoImpl cb = (ClassBeanInfoImpl)bi;
/* 1024 */     for (Property p : cb.properties) {
/* 1025 */       if (p instanceof AttributeProperty) {
/* 1026 */         AttributeProperty ap = (AttributeProperty)p;
/* 1027 */         if (ap.attName.equals("http://www.w3.org/2005/05/xmlmime", "contentType"))
/*      */           try {
/* 1029 */             return (String)ap.xacc.print(o);
/* 1030 */           } catch (AccessorException e) {
/* 1031 */             return null;
/* 1032 */           } catch (SAXException e) {
/* 1033 */             return null;
/* 1034 */           } catch (ClassCastException e) {
/* 1035 */             return null;
/*      */           }  
/*      */       } 
/*      */     } 
/* 1039 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JAXBContextImpl createAugmented(Class<?> clazz) throws JAXBException {
/* 1046 */     Class[] newList = new Class[this.classes.length + 1];
/* 1047 */     System.arraycopy(this.classes, 0, newList, 0, this.classes.length);
/* 1048 */     newList[this.classes.length] = clazz;
/*      */     
/* 1050 */     JAXBContextBuilder builder = new JAXBContextBuilder(this);
/* 1051 */     builder.setClasses(newList);
/* 1052 */     return builder.build();
/*      */   }
/*      */   
/* 1055 */   private static final Comparator<QName> QNAME_COMPARATOR = new Comparator<QName>() {
/*      */       public int compare(QName lhs, QName rhs) {
/* 1057 */         int r = lhs.getLocalPart().compareTo(rhs.getLocalPart());
/* 1058 */         if (r != 0) return r;
/*      */         
/* 1060 */         return lhs.getNamespaceURI().compareTo(rhs.getNamespaceURI());
/*      */       }
/*      */     };
/*      */   
/*      */   public static class JAXBContextBuilder
/*      */   {
/*      */     private boolean retainPropertyInfo = false;
/*      */     private boolean supressAccessorWarnings = false;
/* 1068 */     private String defaultNsUri = ""; @NotNull
/* 1069 */     private RuntimeAnnotationReader annotationReader = (RuntimeAnnotationReader)new RuntimeInlineAnnotationReader(); @NotNull
/* 1070 */     private Map<Class, Class> subclassReplacements = Collections.emptyMap();
/*      */     
/*      */     private boolean c14nSupport = false;
/*      */     
/*      */     private Class[] classes;
/*      */     private Collection<TypeReference> typeRefs;
/*      */     private boolean xmlAccessorFactorySupport = false;
/*      */     private boolean allNillable;
/*      */     private boolean improvedXsiTypeHandling = true;
/*      */     private boolean disableSecurityProcessing = true;
/*      */     
/*      */     public JAXBContextBuilder(JAXBContextImpl baseImpl) {
/* 1082 */       this.supressAccessorWarnings = baseImpl.supressAccessorWarnings;
/* 1083 */       this.retainPropertyInfo = baseImpl.retainPropertyInfo;
/* 1084 */       this.defaultNsUri = baseImpl.defaultNsUri;
/* 1085 */       this.annotationReader = baseImpl.annotationReader;
/* 1086 */       this.subclassReplacements = baseImpl.subclassReplacements;
/* 1087 */       this.c14nSupport = baseImpl.c14nSupport;
/* 1088 */       this.classes = baseImpl.classes;
/* 1089 */       this.typeRefs = baseImpl.bridges.keySet();
/* 1090 */       this.xmlAccessorFactorySupport = baseImpl.xmlAccessorFactorySupport;
/* 1091 */       this.allNillable = baseImpl.allNillable;
/* 1092 */       this.disableSecurityProcessing = baseImpl.disableSecurityProcessing;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setRetainPropertyInfo(boolean val) {
/* 1096 */       this.retainPropertyInfo = val;
/* 1097 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setSupressAccessorWarnings(boolean val) {
/* 1101 */       this.supressAccessorWarnings = val;
/* 1102 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setC14NSupport(boolean val) {
/* 1106 */       this.c14nSupport = val;
/* 1107 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setXmlAccessorFactorySupport(boolean val) {
/* 1111 */       this.xmlAccessorFactorySupport = val;
/* 1112 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setDefaultNsUri(String val) {
/* 1116 */       this.defaultNsUri = val;
/* 1117 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setAllNillable(boolean val) {
/* 1121 */       this.allNillable = val;
/* 1122 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setClasses(Class[] val) {
/* 1126 */       this.classes = val;
/* 1127 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setAnnotationReader(RuntimeAnnotationReader val) {
/* 1131 */       this.annotationReader = val;
/* 1132 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setSubclassReplacements(Map<Class<?>, Class<?>> val) {
/* 1136 */       this.subclassReplacements = val;
/* 1137 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setTypeRefs(Collection<TypeReference> val) {
/* 1141 */       this.typeRefs = val;
/* 1142 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setImprovedXsiTypeHandling(boolean val) {
/* 1146 */       this.improvedXsiTypeHandling = val;
/* 1147 */       return this;
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder setDisableSecurityProcessing(boolean val) {
/* 1151 */       this.disableSecurityProcessing = val;
/* 1152 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public JAXBContextImpl build() throws JAXBException {
/* 1158 */       if (this.defaultNsUri == null) {
/* 1159 */         this.defaultNsUri = "";
/*      */       }
/*      */       
/* 1162 */       if (this.subclassReplacements == null) {
/* 1163 */         this.subclassReplacements = Collections.emptyMap();
/*      */       }
/*      */       
/* 1166 */       if (this.annotationReader == null) {
/* 1167 */         this.annotationReader = (RuntimeAnnotationReader)new RuntimeInlineAnnotationReader();
/*      */       }
/*      */       
/* 1170 */       if (this.typeRefs == null) {
/* 1171 */         this.typeRefs = Collections.emptyList();
/*      */       }
/*      */       
/* 1174 */       return new JAXBContextImpl(this);
/*      */     }
/*      */     
/*      */     public JAXBContextBuilder() {}
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\JAXBContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */