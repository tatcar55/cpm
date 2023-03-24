/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.property.AttributeProperty;
/*     */ import com.sun.xml.bind.v2.runtime.property.Property;
/*     */ import com.sun.xml.bind.v2.runtime.property.PropertyFactory;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.StructureLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XsiTypeLoader;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassBeanInfoImpl<BeanT>
/*     */   extends JaxBeanInfo<BeanT>
/*     */   implements AttributeAccessor<BeanT>
/*     */ {
/*     */   public final Property<BeanT>[] properties;
/*     */   private Property<? super BeanT> idProperty;
/*     */   private Loader loader;
/*     */   private Loader loaderWithTypeSubst;
/*     */   private RuntimeClassInfo ci;
/*     */   private final Accessor<? super BeanT, Map<QName, String>> inheritedAttWildcard;
/*     */   private final Transducer<BeanT> xducer;
/*     */   public final ClassBeanInfoImpl<? super BeanT> superClazz;
/*     */   private final Accessor<? super BeanT, Locator> xmlLocatorField;
/*     */   private final Name tagName;
/*     */   private boolean retainPropertyInfo = false;
/*     */   private AttributeProperty<BeanT>[] attributeProperties;
/*     */   private Property<BeanT>[] uriProperties;
/*     */   private final Method factoryMethod;
/*     */   
/*     */   ClassBeanInfoImpl(JAXBContextImpl owner, RuntimeClassInfo ci) {
/* 141 */     super(owner, (RuntimeTypeInfo)ci, (Class<BeanT>)ci.getClazz(), ci.getTypeName(), ci.isElement(), false, true);
/*     */     
/* 143 */     this.ci = ci;
/* 144 */     this.inheritedAttWildcard = ci.getAttributeWildcard();
/* 145 */     this.xducer = ci.getTransducer();
/* 146 */     this.factoryMethod = ci.getFactoryMethod();
/* 147 */     this.retainPropertyInfo = owner.retainPropertyInfo;
/*     */ 
/*     */     
/* 150 */     if (this.factoryMethod != null) {
/* 151 */       int classMod = this.factoryMethod.getDeclaringClass().getModifiers();
/*     */       
/* 153 */       if (!Modifier.isPublic(classMod) || !Modifier.isPublic(this.factoryMethod.getModifiers())) {
/*     */         
/*     */         try {
/* 156 */           this.factoryMethod.setAccessible(true);
/* 157 */         } catch (SecurityException e) {
/*     */           
/* 159 */           logger.log(Level.FINE, "Unable to make the method of " + this.factoryMethod + " accessible", e);
/* 160 */           throw e;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 166 */     if (ci.getBaseClass() == null) {
/* 167 */       this.superClazz = null;
/*     */     } else {
/* 169 */       this.superClazz = owner.getOrCreate(ci.getBaseClass());
/*     */     } 
/* 171 */     if (this.superClazz != null && this.superClazz.xmlLocatorField != null) {
/* 172 */       this.xmlLocatorField = this.superClazz.xmlLocatorField;
/*     */     } else {
/* 174 */       this.xmlLocatorField = ci.getLocatorField();
/*     */     } 
/*     */     
/* 177 */     Collection<? extends RuntimePropertyInfo> ps = ci.getProperties();
/* 178 */     this.properties = (Property<BeanT>[])new Property[ps.size()];
/* 179 */     int idx = 0;
/* 180 */     boolean elementOnly = true;
/* 181 */     for (RuntimePropertyInfo info : ps) {
/* 182 */       Property<? super BeanT> p = PropertyFactory.create(owner, info);
/* 183 */       if (info.id() == ID.ID)
/* 184 */         this.idProperty = p; 
/* 185 */       this.properties[idx++] = (Property)p;
/* 186 */       elementOnly &= info.elementOnlyContent();
/* 187 */       checkOverrideProperties(p);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 192 */     hasElementOnlyContentModel(elementOnly);
/*     */ 
/*     */     
/* 195 */     if (ci.isElement()) {
/* 196 */       this.tagName = owner.nameBuilder.createElementName(ci.getElementName());
/*     */     } else {
/* 198 */       this.tagName = null;
/*     */     } 
/* 200 */     setLifecycleFlags();
/*     */   }
/*     */   
/*     */   private void checkOverrideProperties(Property p) {
/* 204 */     ClassBeanInfoImpl<BeanT> bi = this; ClassBeanInfoImpl<? super BeanT> classBeanInfoImpl;
/* 205 */     while ((classBeanInfoImpl = bi.superClazz) != null) {
/* 206 */       Property<BeanT>[] arrayOfProperty = classBeanInfoImpl.properties;
/* 207 */       if (arrayOfProperty == null)
/* 208 */         break;  for (Property<BeanT> superProperty : arrayOfProperty) {
/* 209 */         if (superProperty != null) {
/* 210 */           String spName = superProperty.getFieldName();
/* 211 */           if (spName != null && spName.equals(p.getFieldName())) {
/* 212 */             superProperty.setHiddenByOverride(true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void link(JAXBContextImpl grammar) {
/* 221 */     if (this.uriProperties != null) {
/*     */       return;
/*     */     }
/* 224 */     super.link(grammar);
/*     */     
/* 226 */     if (this.superClazz != null) {
/* 227 */       this.superClazz.link(grammar);
/*     */     }
/* 229 */     getLoader(grammar, true);
/*     */ 
/*     */     
/* 232 */     if (this.superClazz != null) {
/* 233 */       if (this.idProperty == null) {
/* 234 */         this.idProperty = this.superClazz.idProperty;
/*     */       }
/* 236 */       if (!this.superClazz.hasElementOnlyContentModel()) {
/* 237 */         hasElementOnlyContentModel(false);
/*     */       }
/*     */     } 
/*     */     
/* 241 */     FinalArrayList<AttributeProperty> finalArrayList = new FinalArrayList();
/* 242 */     FinalArrayList<Property<BeanT>> finalArrayList1 = new FinalArrayList();
/* 243 */     for (ClassBeanInfoImpl<BeanT> bi = this; bi != null; classBeanInfoImpl = bi.superClazz) {
/* 244 */       ClassBeanInfoImpl<? super BeanT> classBeanInfoImpl; for (int i = 0; i < bi.properties.length; i++) {
/* 245 */         Property<BeanT> p = bi.properties[i];
/* 246 */         if (p instanceof AttributeProperty)
/* 247 */           finalArrayList.add((AttributeProperty)p); 
/* 248 */         if (p.hasSerializeURIAction())
/* 249 */           finalArrayList1.add(p); 
/*     */       } 
/*     */     } 
/* 252 */     if (grammar.c14nSupport) {
/* 253 */       Collections.sort((List<AttributeProperty>)finalArrayList);
/*     */     }
/* 255 */     if (finalArrayList.isEmpty()) {
/* 256 */       this.attributeProperties = (AttributeProperty<BeanT>[])EMPTY_PROPERTIES;
/*     */     } else {
/* 258 */       this.attributeProperties = finalArrayList.<AttributeProperty<BeanT>>toArray((AttributeProperty<BeanT>[])new AttributeProperty[finalArrayList.size()]);
/*     */     } 
/* 260 */     if (finalArrayList1.isEmpty()) {
/* 261 */       this.uriProperties = (Property<BeanT>[])EMPTY_PROPERTIES;
/*     */     } else {
/* 263 */       this.uriProperties = finalArrayList1.<Property<BeanT>>toArray((Property<BeanT>[])new Property[finalArrayList1.size()]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void wrapUp() {
/* 268 */     for (Property<BeanT> p : this.properties)
/* 269 */       p.wrapUp(); 
/* 270 */     this.ci = null;
/* 271 */     super.wrapUp();
/*     */   }
/*     */   
/*     */   public String getElementNamespaceURI(BeanT bean) {
/* 275 */     return this.tagName.nsUri;
/*     */   }
/*     */   
/*     */   public String getElementLocalName(BeanT bean) {
/* 279 */     return this.tagName.localName;
/*     */   }
/*     */ 
/*     */   
/*     */   public BeanT createInstance(UnmarshallingContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException, SAXException {
/* 284 */     BeanT bean = null;
/* 285 */     if (this.factoryMethod == null) {
/* 286 */       bean = (BeanT)ClassFactory.create0(this.jaxbType);
/*     */     } else {
/* 288 */       Object o = ClassFactory.create(this.factoryMethod);
/* 289 */       if (this.jaxbType.isInstance(o)) {
/* 290 */         bean = (BeanT)o;
/*     */       } else {
/* 292 */         throw new InstantiationException("The factory method didn't return a correct object");
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (this.xmlLocatorField != null)
/*     */       
/*     */       try {
/* 299 */         this.xmlLocatorField.set(bean, new LocatorImpl((Locator)context.getLocator()));
/* 300 */       } catch (AccessorException e) {
/* 301 */         context.handleError((Exception)e);
/*     */       }  
/* 303 */     return bean;
/*     */   }
/*     */   
/*     */   public boolean reset(BeanT bean, UnmarshallingContext context) throws SAXException {
/*     */     try {
/* 308 */       if (this.superClazz != null)
/* 309 */         this.superClazz.reset(bean, context); 
/* 310 */       for (Property<BeanT> p : this.properties)
/* 311 */         p.reset(bean); 
/* 312 */       return true;
/* 313 */     } catch (AccessorException e) {
/* 314 */       context.handleError((Exception)e);
/* 315 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getId(BeanT bean, XMLSerializer target) throws SAXException {
/* 320 */     if (this.idProperty != null) {
/*     */       try {
/* 322 */         return this.idProperty.getIdValue(bean);
/* 323 */       } catch (AccessorException e) {
/* 324 */         target.reportError(null, (Throwable)e);
/*     */       } 
/*     */     }
/* 327 */     return null;
/*     */   }
/*     */   
/*     */   public void serializeRoot(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 331 */     if (this.tagName == null) {
/* 332 */       String message; Class<?> beanClass = bean.getClass();
/*     */       
/* 334 */       if (beanClass.isAnnotationPresent((Class)XmlRootElement.class)) {
/* 335 */         message = Messages.UNABLE_TO_MARSHAL_UNBOUND_CLASS.format(new Object[] { beanClass.getName() });
/*     */       } else {
/* 337 */         message = Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { beanClass.getName() });
/*     */       } 
/* 339 */       target.reportError(new ValidationEventImpl(1, message, null, null));
/*     */     } else {
/* 341 */       target.startElement(this.tagName, bean);
/* 342 */       target.childAsSoleContent(bean, null);
/* 343 */       target.endElement();
/* 344 */       if (this.retainPropertyInfo) {
/* 345 */         target.currentProperty.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeBody(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 351 */     if (this.superClazz != null) {
/* 352 */       this.superClazz.serializeBody(bean, target);
/*     */     }
/*     */     try {
/* 355 */       for (Property<BeanT> p : this.properties) {
/* 356 */         if (this.retainPropertyInfo) {
/* 357 */           target.currentProperty.set(p);
/*     */         }
/* 359 */         boolean isThereAnOverridingProperty = p.isHiddenByOverride();
/* 360 */         if (!isThereAnOverridingProperty || bean.getClass().equals(this.jaxbType)) {
/* 361 */           p.serializeBody(bean, target, null);
/* 362 */         } else if (isThereAnOverridingProperty) {
/*     */           
/* 364 */           Class<?> beanClass = bean.getClass();
/* 365 */           if (Navigator.REFLECTION.getDeclaredField(beanClass, p.getFieldName()) == null) {
/* 366 */             p.serializeBody(bean, target, null);
/*     */           }
/*     */         } 
/*     */       } 
/* 370 */     } catch (AccessorException e) {
/* 371 */       target.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 376 */     for (AttributeProperty<BeanT> p : this.attributeProperties) {
/*     */       try {
/* 378 */         if (this.retainPropertyInfo) {
/* 379 */           Property parentProperty = target.getCurrentProperty();
/* 380 */           target.currentProperty.set(p);
/* 381 */           p.serializeAttributes(bean, target);
/* 382 */           target.currentProperty.set(parentProperty);
/*     */         } else {
/* 384 */           p.serializeAttributes(bean, target);
/*     */         } 
/* 386 */         if (p.attName.equals("http://www.w3.org/2001/XMLSchema-instance", "nil")) {
/* 387 */           this.isNilIncluded = true;
/*     */         }
/* 389 */       } catch (AccessorException e) {
/* 390 */         target.reportError(null, (Throwable)e);
/*     */       } 
/*     */     } 
/*     */     try {
/* 394 */       if (this.inheritedAttWildcard != null) {
/* 395 */         Map<QName, String> map = (Map<QName, String>)this.inheritedAttWildcard.get(bean);
/* 396 */         target.attWildcardAsAttributes(map, null);
/*     */       } 
/* 398 */     } catch (AccessorException e) {
/* 399 */       target.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeURIs(BeanT bean, XMLSerializer target) throws SAXException {
/*     */     try {
/* 405 */       if (this.retainPropertyInfo) {
/* 406 */         Property parentProperty = target.getCurrentProperty();
/* 407 */         for (Property<BeanT> p : this.uriProperties) {
/* 408 */           target.currentProperty.set(p);
/* 409 */           p.serializeURIs(bean, target);
/*     */         } 
/* 411 */         target.currentProperty.set(parentProperty);
/*     */       } else {
/* 413 */         for (Property<BeanT> p : this.uriProperties) {
/* 414 */           p.serializeURIs(bean, target);
/*     */         }
/*     */       } 
/* 417 */       if (this.inheritedAttWildcard != null) {
/* 418 */         Map<QName, String> map = (Map<QName, String>)this.inheritedAttWildcard.get(bean);
/* 419 */         target.attWildcardAsURIs(map, null);
/*     */       } 
/* 421 */     } catch (AccessorException e) {
/* 422 */       target.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 427 */     if (this.loader == null) {
/*     */ 
/*     */       
/* 430 */       StructureLoader sl = new StructureLoader(this);
/* 431 */       this.loader = (Loader)sl;
/* 432 */       if (this.ci.hasSubClasses()) {
/* 433 */         this.loaderWithTypeSubst = (Loader)new XsiTypeLoader(this);
/*     */       } else {
/*     */         
/* 436 */         this.loaderWithTypeSubst = this.loader;
/*     */       } 
/*     */       
/* 439 */       sl.init(context, this, this.ci.getAttributeWildcard());
/*     */     } 
/* 441 */     if (typeSubstitutionCapable) {
/* 442 */       return this.loaderWithTypeSubst;
/*     */     }
/* 444 */     return this.loader;
/*     */   }
/*     */   
/*     */   public Transducer<BeanT> getTransducer() {
/* 448 */     return this.xducer;
/*     */   }
/*     */   
/* 451 */   private static final AttributeProperty[] EMPTY_PROPERTIES = new AttributeProperty[0];
/*     */   
/* 453 */   private static final Logger logger = Util.getClassLogger();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\ClassBeanInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */