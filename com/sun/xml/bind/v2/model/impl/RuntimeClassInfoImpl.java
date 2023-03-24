/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.AccessorFactory;
/*     */ import com.sun.xml.bind.AccessorFactoryImpl;
/*     */ import com.sun.xml.bind.InternalAccessorFactory;
/*     */ import com.sun.xml.bind.XmlAccessorFactory;
/*     */ import com.sun.xml.bind.annotation.XmlLocation;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeValuePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Locator;
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
/*     */ class RuntimeClassInfoImpl
/*     */   extends ClassInfoImpl<Type, Class, Field, Method>
/*     */   implements RuntimeClassInfo, RuntimeElement
/*     */ {
/*     */   private Accessor<?, Locator> xmlLocationAccessor;
/*     */   private AccessorFactory accessorFactory;
/*     */   private boolean supressAccessorWarnings = false;
/*     */   private Accessor<?, Map<QName, String>> attributeWildcardAccessor;
/*     */   private boolean computedTransducer;
/*     */   private Transducer xducer;
/*     */   
/*     */   protected AccessorFactory createAccessorFactory(Class clazz) {
/*     */     AccessorFactoryImpl accessorFactoryImpl;
/*     */     AccessorFactory accFactory = null;
/*     */     JAXBContextImpl context = ((RuntimeModelBuilder)this.builder).context;
/*     */     if (context != null) {
/*     */       this.supressAccessorWarnings = context.supressAccessorWarnings;
/*     */       if (context.xmlAccessorFactorySupport) {
/*     */         XmlAccessorFactory factoryAnn = findXmlAccessorFactoryAnnotation(clazz);
/*     */         if (factoryAnn != null)
/*     */           try {
/*     */             accFactory = factoryAnn.value().newInstance();
/*     */           } catch (InstantiationException e) {
/*     */             this.builder.reportError(new IllegalAnnotationException(Messages.ACCESSORFACTORY_INSTANTIATION_EXCEPTION.format(new Object[] { factoryAnn.getClass().getName(), nav().getClassName(clazz) }, ), this));
/*     */           } catch (IllegalAccessException e) {
/*     */             this.builder.reportError(new IllegalAnnotationException(Messages.ACCESSORFACTORY_ACCESS_EXCEPTION.format(new Object[] { factoryAnn.getClass().getName(), nav().getClassName(clazz) }, ), this));
/*     */           }  
/*     */       } 
/*     */     } 
/*     */     if (accFactory == null)
/*     */       accessorFactoryImpl = AccessorFactoryImpl.getInstance(); 
/*     */     return (AccessorFactory)accessorFactoryImpl;
/*     */   }
/*     */   
/*     */   protected XmlAccessorFactory findXmlAccessorFactoryAnnotation(Class clazz) {
/*     */     XmlAccessorFactory factoryAnn = (XmlAccessorFactory)reader().getClassAnnotation(XmlAccessorFactory.class, clazz, this);
/*     */     if (factoryAnn == null)
/*     */       factoryAnn = (XmlAccessorFactory)reader().getPackageAnnotation(XmlAccessorFactory.class, clazz, this); 
/*     */     return factoryAnn;
/*     */   }
/*     */   
/*     */   public Method getFactoryMethod() {
/*     */     return super.getFactoryMethod();
/*     */   }
/*     */   
/*     */   public RuntimeClassInfoImpl(RuntimeModelBuilder modelBuilder, Locatable upstream, Class clazz) {
/* 102 */     super(modelBuilder, upstream, clazz);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     this.computedTransducer = false;
/* 214 */     this.xducer = null; this.accessorFactory = createAccessorFactory(clazz);
/*     */   } public final RuntimeClassInfoImpl getBaseClass() { return (RuntimeClassInfoImpl)super.getBaseClass(); } protected ReferencePropertyInfoImpl createReferenceProperty(PropertySeed<Type, Class<?>, Field, Method> seed) { return new RuntimeReferencePropertyInfoImpl(this, seed); }
/*     */   protected AttributePropertyInfoImpl createAttributeProperty(PropertySeed<Type, Class<?>, Field, Method> seed) { return new RuntimeAttributePropertyInfoImpl(this, seed); }
/* 217 */   public Transducer getTransducer() { if (!this.computedTransducer) {
/* 218 */       this.computedTransducer = true;
/* 219 */       this.xducer = calcTransducer();
/*     */     } 
/* 221 */     return this.xducer; } protected ValuePropertyInfoImpl createValueProperty(PropertySeed<Type, Class<?>, Field, Method> seed) {
/*     */     return new RuntimeValuePropertyInfoImpl(this, seed);
/*     */   } protected ElementPropertyInfoImpl createElementProperty(PropertySeed<Type, Class<?>, Field, Method> seed) {
/*     */     return new RuntimeElementPropertyInfoImpl(this, seed);
/*     */   } protected MapPropertyInfoImpl createMapProperty(PropertySeed<Type, Class<?>, Field, Method> seed) {
/*     */     return new RuntimeMapPropertyInfoImpl(this, seed);
/*     */   }
/* 228 */   private Transducer calcTransducer() { RuntimeValuePropertyInfo valuep = null;
/* 229 */     if (hasAttributeWildcard())
/* 230 */       return null; 
/* 231 */     for (RuntimeClassInfoImpl ci = this; ci != null; ci = ci.getBaseClass()) {
/* 232 */       for (RuntimePropertyInfo pi : ci.getProperties()) {
/* 233 */         if (pi.kind() == PropertyKind.VALUE) {
/* 234 */           valuep = (RuntimeValuePropertyInfo)pi;
/*     */           continue;
/*     */         } 
/* 237 */         return null;
/*     */       } 
/*     */     } 
/* 240 */     if (valuep == null)
/* 241 */       return null; 
/* 242 */     if (!valuep.getTarget().isSimpleType()) {
/* 243 */       return null;
/*     */     }
/* 245 */     return new TransducerImpl(getClazz(), TransducedAccessor.get(((RuntimeModelBuilder)this.builder).context, (RuntimeNonElementRef)valuep)); }
/*     */   
/*     */   public List<? extends RuntimePropertyInfo> getProperties() {
/*     */     return (List)super.getProperties();
/*     */   }
/*     */   public RuntimePropertyInfo getProperty(String name) {
/*     */     return (RuntimePropertyInfo)super.getProperty(name);
/*     */   }
/* 253 */   private Accessor<?, Map<QName, String>> createAttributeWildcardAccessor() { assert this.attributeWildcard != null;
/* 254 */     return ((RuntimePropertySeed)this.attributeWildcard).getAccessor(); }
/*     */   public void link() { getTransducer(); super.link(); }
/*     */   public <B> Accessor<B, Map<QName, String>> getAttributeWildcard() { for (RuntimeClassInfoImpl c = this; c != null; c = c.getBaseClass()) { if (c.attributeWildcard != null) { if (c.attributeWildcardAccessor == null)
/*     */           c.attributeWildcardAccessor = c.createAttributeWildcardAccessor();  return (Accessor)c.attributeWildcardAccessor; }
/*     */        }
/* 259 */      return null; } protected RuntimePropertySeed createFieldSeed(Field field) { Accessor accessor; boolean readOnly = Modifier.isStatic(field.getModifiers());
/*     */     
/*     */     try {
/* 262 */       if (this.supressAccessorWarnings) {
/* 263 */         accessor = ((InternalAccessorFactory)this.accessorFactory).createFieldAccessor(this.clazz, field, readOnly, this.supressAccessorWarnings);
/*     */       } else {
/* 265 */         accessor = this.accessorFactory.createFieldAccessor(this.clazz, field, readOnly);
/*     */       } 
/* 267 */     } catch (JAXBException e) {
/* 268 */       this.builder.reportError(new IllegalAnnotationException(Messages.CUSTOM_ACCESSORFACTORY_FIELD_ERROR.format(new Object[] { nav().getClassName(this.clazz), e.toString() }, ), this));
/*     */ 
/*     */       
/* 271 */       accessor = Accessor.getErrorInstance();
/*     */     } 
/* 273 */     return new RuntimePropertySeed(super.createFieldSeed(field), accessor); }
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimePropertySeed createAccessorSeed(Method getter, Method setter) {
/*     */     Accessor accessor;
/*     */     try {
/* 280 */       accessor = this.accessorFactory.createPropertyAccessor(this.clazz, getter, setter);
/* 281 */     } catch (JAXBException e) {
/* 282 */       this.builder.reportError(new IllegalAnnotationException(Messages.CUSTOM_ACCESSORFACTORY_PROPERTY_ERROR.format(new Object[] { nav().getClassName(this.clazz), e.toString() }, ), this));
/*     */ 
/*     */       
/* 285 */       accessor = Accessor.getErrorInstance();
/*     */     } 
/* 287 */     return new RuntimePropertySeed(super.createAccessorSeed(getter, setter), accessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFieldXmlLocation(Field f) {
/* 293 */     if (reader().hasFieldAnnotation(XmlLocation.class, f))
/*     */     {
/*     */       
/* 296 */       this.xmlLocationAccessor = (Accessor<?, Locator>)new Accessor.FieldReflection(f); } 
/*     */   }
/*     */   
/*     */   public Accessor<?, Locator> getLocatorField() {
/* 300 */     return this.xmlLocationAccessor;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class RuntimePropertySeed
/*     */     implements PropertySeed<Type, Class, Field, Method>
/*     */   {
/*     */     private final Accessor acc;
/*     */     
/*     */     private final PropertySeed<Type, Class, Field, Method> core;
/*     */     
/*     */     public RuntimePropertySeed(PropertySeed<Type, Class<?>, Field, Method> core, Accessor acc) {
/* 312 */       this.core = core;
/* 313 */       this.acc = acc;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 317 */       return this.core.getName();
/*     */     }
/*     */     
/*     */     public <A extends Annotation> A readAnnotation(Class<A> annotationType) {
/* 321 */       return (A)this.core.readAnnotation(annotationType);
/*     */     }
/*     */     
/*     */     public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 325 */       return this.core.hasAnnotation(annotationType);
/*     */     }
/*     */     
/*     */     public Type getRawType() {
/* 329 */       return this.core.getRawType();
/*     */     }
/*     */     
/*     */     public Location getLocation() {
/* 333 */       return this.core.getLocation();
/*     */     }
/*     */     
/*     */     public Locatable getUpstream() {
/* 337 */       return this.core.getUpstream();
/*     */     }
/*     */     
/*     */     public Accessor getAccessor() {
/* 341 */       return this.acc;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class TransducerImpl<BeanT>
/*     */     implements Transducer<BeanT>
/*     */   {
/*     */     private final TransducedAccessor<BeanT> xacc;
/*     */ 
/*     */     
/*     */     private final Class<BeanT> ownerClass;
/*     */ 
/*     */     
/*     */     public TransducerImpl(Class<BeanT> ownerClass, TransducedAccessor<BeanT> xacc) {
/* 357 */       this.xacc = xacc;
/* 358 */       this.ownerClass = ownerClass;
/*     */     }
/*     */     
/*     */     public boolean useNamespace() {
/* 362 */       return this.xacc.useNamespace();
/*     */     }
/*     */     
/*     */     public boolean isDefault() {
/* 366 */       return false;
/*     */     }
/*     */     
/*     */     public void declareNamespace(BeanT bean, XMLSerializer w) throws AccessorException {
/*     */       try {
/* 371 */         this.xacc.declareNamespace(bean, w);
/* 372 */       } catch (SAXException e) {
/* 373 */         throw new AccessorException(e);
/*     */       } 
/*     */     }
/*     */     @NotNull
/*     */     public CharSequence print(BeanT o) throws AccessorException {
/*     */       try {
/* 379 */         CharSequence value = this.xacc.print(o);
/* 380 */         if (value == null)
/* 381 */           throw new AccessorException(Messages.THERE_MUST_BE_VALUE_IN_XMLVALUE.format(new Object[] { o })); 
/* 382 */         return value;
/* 383 */       } catch (SAXException e) {
/* 384 */         throw new AccessorException(e);
/*     */       } 
/*     */     }
/*     */     public BeanT parse(CharSequence lexical) throws AccessorException, SAXException {
/*     */       BeanT inst;
/* 389 */       UnmarshallingContext ctxt = UnmarshallingContext.getInstance();
/*     */       
/* 391 */       if (ctxt != null) {
/* 392 */         inst = (BeanT)ctxt.createInstance(this.ownerClass);
/*     */       }
/*     */       else {
/*     */         
/* 396 */         inst = (BeanT)ClassFactory.create(this.ownerClass);
/*     */       } 
/* 398 */       this.xacc.parse(inst, lexical);
/* 399 */       return inst;
/*     */     }
/*     */     
/*     */     public void writeText(XMLSerializer w, BeanT o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 403 */       if (!this.xacc.hasValue(o))
/* 404 */         throw new AccessorException(Messages.THERE_MUST_BE_VALUE_IN_XMLVALUE.format(new Object[] { o })); 
/* 405 */       this.xacc.writeText(w, o, fieldName);
/*     */     }
/*     */     
/*     */     public void writeLeafElement(XMLSerializer w, Name tagName, BeanT o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 409 */       if (!this.xacc.hasValue(o))
/* 410 */         throw new AccessorException(Messages.THERE_MUST_BE_VALUE_IN_XMLVALUE.format(new Object[] { o })); 
/* 411 */       this.xacc.writeLeafElement(w, tagName, o, fieldName);
/*     */     }
/*     */     
/*     */     public QName getTypeName(BeanT instance) {
/* 415 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeClassInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */