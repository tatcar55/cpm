/*      */ package com.sun.xml.bind.v2.model.impl;
/*      */ 
/*      */ import com.sun.istack.FinalArrayList;
/*      */ import com.sun.xml.bind.annotation.OverrideAnnotationOf;
/*      */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*      */ import com.sun.xml.bind.v2.model.annotation.MethodLocatable;
/*      */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*      */ import com.sun.xml.bind.v2.model.core.Element;
/*      */ import com.sun.xml.bind.v2.model.core.ID;
/*      */ import com.sun.xml.bind.v2.model.core.NonElement;
/*      */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*      */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*      */ import com.sun.xml.bind.v2.runtime.Location;
/*      */ import com.sun.xml.bind.v2.util.EditDistance;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.AbstractList;
/*      */ import java.util.ArrayList;
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
/*      */ import javax.xml.bind.annotation.XmlAccessOrder;
/*      */ import javax.xml.bind.annotation.XmlAccessType;
/*      */ import javax.xml.bind.annotation.XmlAccessorOrder;
/*      */ import javax.xml.bind.annotation.XmlAccessorType;
/*      */ import javax.xml.bind.annotation.XmlAnyAttribute;
/*      */ import javax.xml.bind.annotation.XmlAnyElement;
/*      */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*      */ import javax.xml.bind.annotation.XmlAttribute;
/*      */ import javax.xml.bind.annotation.XmlElement;
/*      */ import javax.xml.bind.annotation.XmlElementRef;
/*      */ import javax.xml.bind.annotation.XmlElementRefs;
/*      */ import javax.xml.bind.annotation.XmlElementWrapper;
/*      */ import javax.xml.bind.annotation.XmlElements;
/*      */ import javax.xml.bind.annotation.XmlID;
/*      */ import javax.xml.bind.annotation.XmlIDREF;
/*      */ import javax.xml.bind.annotation.XmlInlineBinaryData;
/*      */ import javax.xml.bind.annotation.XmlList;
/*      */ import javax.xml.bind.annotation.XmlMimeType;
/*      */ import javax.xml.bind.annotation.XmlMixed;
/*      */ import javax.xml.bind.annotation.XmlSchemaType;
/*      */ import javax.xml.bind.annotation.XmlTransient;
/*      */ import javax.xml.bind.annotation.XmlType;
/*      */ import javax.xml.bind.annotation.XmlValue;
/*      */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*      */ import javax.xml.namespace.QName;
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
/*      */ public class ClassInfoImpl<T, C, F, M>
/*      */   extends TypeInfoImpl<T, C, F, M>
/*      */   implements ClassInfo<T, C>, Element<T, C>
/*      */ {
/*      */   protected final C clazz;
/*      */   private final QName elementName;
/*      */   private final QName typeName;
/*      */   private FinalArrayList<PropertyInfoImpl<T, C, F, M>> properties;
/*      */   private String[] propOrder;
/*      */   private ClassInfoImpl<T, C, F, M> baseClass;
/*      */   private boolean baseClassComputed = false;
/*      */   private boolean hasSubClasses = false;
/*      */   protected PropertySeed<T, C, F, M> attributeWildcard;
/*  161 */   private M factoryMethod = null;
/*      */   
/*      */   ClassInfoImpl(ModelBuilder<T, C, F, M> builder, Locatable upstream, C clazz) {
/*  164 */     super(builder, upstream);
/*  165 */     this.clazz = clazz;
/*  166 */     assert clazz != null;
/*      */ 
/*      */     
/*  169 */     this.elementName = parseElementName(clazz);
/*      */ 
/*      */     
/*  172 */     XmlType t = (XmlType)reader().getClassAnnotation(XmlType.class, clazz, this);
/*  173 */     this.typeName = parseTypeName(clazz, t);
/*      */     
/*  175 */     if (t != null) {
/*  176 */       String[] propOrder = t.propOrder();
/*  177 */       if (propOrder.length == 0) {
/*  178 */         this.propOrder = null;
/*      */       }
/*  180 */       else if (propOrder[0].length() == 0) {
/*  181 */         this.propOrder = DEFAULT_ORDER;
/*      */       } else {
/*  183 */         this.propOrder = propOrder;
/*      */       } 
/*      */     } else {
/*  186 */       this.propOrder = DEFAULT_ORDER;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  191 */     XmlAccessorOrder xao = (XmlAccessorOrder)reader().getPackageAnnotation(XmlAccessorOrder.class, clazz, this);
/*  192 */     if (xao != null && xao.value() == XmlAccessOrder.UNDEFINED) {
/*  193 */       this.propOrder = null;
/*      */     }
/*      */ 
/*      */     
/*  197 */     xao = (XmlAccessorOrder)reader().getClassAnnotation(XmlAccessorOrder.class, clazz, this);
/*  198 */     if (xao != null && xao.value() == XmlAccessOrder.UNDEFINED) {
/*  199 */       this.propOrder = null;
/*      */     }
/*      */     
/*  202 */     if (nav().isInterface(clazz)) {
/*  203 */       builder.reportError(new IllegalAnnotationException(Messages.CANT_HANDLE_INTERFACE.format(new Object[] { nav().getClassName(clazz) }, ), this));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  208 */     if (!hasFactoryConstructor(t) && 
/*  209 */       !nav().hasDefaultConstructor(clazz)) {
/*  210 */       if (nav().isInnerClass(clazz)) {
/*  211 */         builder.reportError(new IllegalAnnotationException(Messages.CANT_HANDLE_INNER_CLASS.format(new Object[] { nav().getClassName(clazz) }, ), this));
/*      */       }
/*  213 */       else if (this.elementName != null) {
/*  214 */         builder.reportError(new IllegalAnnotationException(Messages.NO_DEFAULT_CONSTRUCTOR.format(new Object[] { nav().getClassName(clazz) }, ), this));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfoImpl<T, C, F, M> getBaseClass() {
/*  222 */     if (!this.baseClassComputed) {
/*      */       
/*  224 */       C s = (C)nav().getSuperClass(this.clazz);
/*  225 */       if (s == null || s == nav().asDecl(Object.class)) {
/*  226 */         this.baseClass = null;
/*      */       } else {
/*  228 */         NonElement<T, C> b = this.builder.getClassInfo(s, true, this);
/*  229 */         if (b instanceof ClassInfoImpl) {
/*  230 */           this.baseClass = (ClassInfoImpl)b;
/*  231 */           this.baseClass.hasSubClasses = true;
/*      */         } else {
/*  233 */           this.baseClass = null;
/*      */         } 
/*      */       } 
/*  236 */       this.baseClassComputed = true;
/*      */     } 
/*  238 */     return this.baseClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Element<T, C> getSubstitutionHead() {
/*  247 */     ClassInfoImpl<T, C, F, M> c = getBaseClass();
/*  248 */     while (c != null && !c.isElement())
/*  249 */       c = c.getBaseClass(); 
/*  250 */     return c;
/*      */   }
/*      */   
/*      */   public final C getClazz() {
/*  254 */     return this.clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfoImpl<T, C, F, M> getScope() {
/*  265 */     return null;
/*      */   }
/*      */   
/*      */   public final T getType() {
/*  269 */     return (T)nav().use(this.clazz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeReferencedByIDREF() {
/*  277 */     for (PropertyInfo<T, C> p : getProperties()) {
/*  278 */       if (p.id() == ID.ID)
/*  279 */         return true; 
/*      */     } 
/*  281 */     ClassInfoImpl<T, C, F, M> base = getBaseClass();
/*  282 */     if (base != null) {
/*  283 */       return base.canBeReferencedByIDREF();
/*      */     }
/*  285 */     return false;
/*      */   }
/*      */   
/*      */   public final String getName() {
/*  289 */     return nav().getClassName(this.clazz);
/*      */   }
/*      */   
/*      */   public <A extends Annotation> A readAnnotation(Class<A> a) {
/*  293 */     return (A)reader().getClassAnnotation(a, this.clazz, this);
/*      */   }
/*      */   
/*      */   public Element<T, C> asElement() {
/*  297 */     if (isElement()) {
/*  298 */       return this;
/*      */     }
/*  300 */     return null;
/*      */   }
/*      */   
/*      */   public List<? extends PropertyInfo<T, C>> getProperties() {
/*  304 */     if (this.properties != null) return (List)this.properties;
/*      */ 
/*      */     
/*  307 */     XmlAccessType at = getAccessType();
/*      */     
/*  309 */     this.properties = new FinalArrayList();
/*      */     
/*  311 */     findFieldProperties(this.clazz, at);
/*      */     
/*  313 */     findGetterSetterProperties(at);
/*      */     
/*  315 */     if (this.propOrder == DEFAULT_ORDER || this.propOrder == null) {
/*  316 */       XmlAccessOrder ao = getAccessorOrder();
/*  317 */       if (ao == XmlAccessOrder.ALPHABETICAL) {
/*  318 */         Collections.sort((List<PropertyInfoImpl<T, C, F, M>>)this.properties);
/*      */       }
/*      */     } else {
/*  321 */       PropertySorter sorter = new PropertySorter();
/*  322 */       for (PropertyInfoImpl<T, C, F, M> p : this.properties) {
/*  323 */         sorter.checkedGet(p);
/*      */       }
/*  325 */       Collections.sort((List<PropertyInfoImpl<T, C, F, M>>)this.properties, sorter);
/*  326 */       sorter.checkUnusedProperties();
/*      */     } 
/*      */ 
/*      */     
/*  330 */     PropertyInfoImpl<T, C, F, M> vp = null;
/*  331 */     PropertyInfoImpl<T, C, F, M> ep = null;
/*      */     
/*  333 */     for (PropertyInfoImpl<T, C, F, M> p : this.properties) {
/*  334 */       switch (p.kind()) {
/*      */         case TRANSIENT:
/*      */         case ANY_ATTRIBUTE:
/*      */         case ATTRIBUTE:
/*  338 */           ep = p;
/*      */           continue;
/*      */         case VALUE:
/*  341 */           if (vp != null)
/*      */           {
/*  343 */             this.builder.reportError(new IllegalAnnotationException(Messages.MULTIPLE_VALUE_PROPERTY.format(new Object[0]), vp, p));
/*      */           }
/*      */ 
/*      */           
/*  347 */           if (getBaseClass() != null) {
/*  348 */             this.builder.reportError(new IllegalAnnotationException(Messages.XMLVALUE_IN_DERIVED_TYPE.format(new Object[0]), p));
/*      */           }
/*      */           
/*  351 */           vp = p;
/*      */           continue;
/*      */         
/*      */         case ELEMENT:
/*      */           continue;
/*      */       } 
/*      */       
/*      */       assert false;
/*      */     } 
/*  360 */     if (ep != null && vp != null)
/*      */     {
/*  362 */       this.builder.reportError(new IllegalAnnotationException(Messages.ELEMENT_AND_VALUE_PROPERTY.format(new Object[0]), vp, ep));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  369 */     return (List)this.properties;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void findFieldProperties(C c, XmlAccessType at) {
/*  375 */     C sc = (C)nav().getSuperClass(c);
/*  376 */     if (shouldRecurseSuperClass(sc)) {
/*  377 */       findFieldProperties(sc, at);
/*      */     }
/*      */     
/*  380 */     for (F f : nav().getDeclaredFields(c)) {
/*  381 */       Annotation[] annotations = reader().getAllFieldAnnotations(f, this);
/*  382 */       boolean isDummy = reader().hasFieldAnnotation(OverrideAnnotationOf.class, f);
/*      */       
/*  384 */       if (nav().isTransient(f)) {
/*      */         
/*  386 */         if (hasJAXBAnnotation(annotations)) {
/*  387 */           this.builder.reportError(new IllegalAnnotationException(Messages.TRANSIENT_FIELD_NOT_BINDABLE.format(new Object[] { nav().getFieldName(f) }, ), getSomeJAXBAnnotation(annotations)));
/*      */         }
/*      */         continue;
/*      */       } 
/*  391 */       if (nav().isStaticField(f)) {
/*      */         
/*  393 */         if (hasJAXBAnnotation(annotations))
/*  394 */           addProperty(createFieldSeed(f), annotations, false);  continue;
/*      */       } 
/*  396 */       if (at == XmlAccessType.FIELD || (at == XmlAccessType.PUBLIC_MEMBER && nav().isPublicField(f)) || hasJAXBAnnotation(annotations))
/*      */       {
/*      */         
/*  399 */         if (isDummy) {
/*  400 */           ClassInfo<T, C> top = getBaseClass();
/*  401 */           while (top != null && top.getProperty("content") == null) {
/*  402 */             top = top.getBaseClass();
/*      */           }
/*  404 */           DummyPropertyInfo<T, C, F, M> prop = (DummyPropertyInfo)top.getProperty("content");
/*  405 */           PropertySeed<T, C, F, M> seed = createFieldSeed(f);
/*  406 */           prop.addType(createReferenceProperty(seed));
/*      */         } else {
/*  408 */           addProperty(createFieldSeed(f), annotations, false);
/*      */         } 
/*      */       }
/*  411 */       checkFieldXmlLocation(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean hasValueProperty() {
/*  417 */     ClassInfoImpl<T, C, F, M> bc = getBaseClass();
/*  418 */     if (bc != null && bc.hasValueProperty()) {
/*  419 */       return true;
/*      */     }
/*  421 */     for (PropertyInfo<T, C> p : getProperties()) {
/*  422 */       if (p instanceof com.sun.xml.bind.v2.model.core.ValuePropertyInfo) return true;
/*      */     
/*      */     } 
/*  425 */     return false;
/*      */   }
/*      */   
/*      */   public PropertyInfo<T, C> getProperty(String name) {
/*  429 */     for (PropertyInfo<T, C> p : getProperties()) {
/*  430 */       if (p.getName().equals(name))
/*  431 */         return p; 
/*      */     } 
/*  433 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkFieldXmlLocation(F f) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T extends Annotation> T getClassOrPackageAnnotation(Class<T> type) {
/*  446 */     Annotation annotation = reader().getClassAnnotation(type, this.clazz, this);
/*  447 */     if (annotation != null) {
/*  448 */       return (T)annotation;
/*      */     }
/*  450 */     return (T)reader().getPackageAnnotation(type, this.clazz, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XmlAccessType getAccessType() {
/*  458 */     XmlAccessorType xat = getClassOrPackageAnnotation(XmlAccessorType.class);
/*  459 */     if (xat != null) {
/*  460 */       return xat.value();
/*      */     }
/*  462 */     return XmlAccessType.PUBLIC_MEMBER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XmlAccessOrder getAccessorOrder() {
/*  469 */     XmlAccessorOrder xao = getClassOrPackageAnnotation(XmlAccessorOrder.class);
/*  470 */     if (xao != null) {
/*  471 */       return xao.value();
/*      */     }
/*  473 */     return XmlAccessOrder.UNDEFINED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class PropertySorter
/*      */     extends HashMap<String, Integer>
/*      */     implements Comparator<PropertyInfoImpl>
/*      */   {
/*  486 */     PropertyInfoImpl[] used = new PropertyInfoImpl[ClassInfoImpl.this.propOrder.length];
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<String> collidedNames;
/*      */ 
/*      */ 
/*      */     
/*      */     PropertySorter() {
/*  495 */       super(ClassInfoImpl.this.propOrder.length);
/*  496 */       for (String name : ClassInfoImpl.this.propOrder) {
/*  497 */         if (put(name, Integer.valueOf(size())) != null)
/*      */         {
/*  499 */           ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.DUPLICATE_ENTRY_IN_PROP_ORDER.format(new Object[] { name }, ), ClassInfoImpl.this));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public int compare(PropertyInfoImpl o1, PropertyInfoImpl o2) {
/*  505 */       int lhs = checkedGet(o1);
/*  506 */       int rhs = checkedGet(o2);
/*      */       
/*  508 */       return lhs - rhs;
/*      */     }
/*      */     
/*      */     private int checkedGet(PropertyInfoImpl p) {
/*  512 */       Integer i = get(p.getName());
/*  513 */       if (i == null) {
/*      */         
/*  515 */         if ((p.kind()).isOrdered) {
/*  516 */           ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.PROPERTY_MISSING_FROM_ORDER.format(new Object[] { p.getName() }, ), p));
/*      */         }
/*      */ 
/*      */         
/*  520 */         i = Integer.valueOf(size());
/*  521 */         put(p.getName(), i);
/*      */       } 
/*      */ 
/*      */       
/*  525 */       int ii = i.intValue();
/*  526 */       if (ii < this.used.length) {
/*  527 */         if (this.used[ii] != null && this.used[ii] != p) {
/*  528 */           if (this.collidedNames == null) this.collidedNames = new HashSet<String>();
/*      */           
/*  530 */           if (this.collidedNames.add(p.getName()))
/*      */           {
/*  532 */             ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.DUPLICATE_PROPERTIES.format(new Object[] { p.getName() }, ), p, this.used[ii]));
/*      */           }
/*      */         } 
/*  535 */         this.used[ii] = p;
/*      */       } 
/*      */       
/*  538 */       return i.intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void checkUnusedProperties() {
/*  545 */       for (int i = 0; i < this.used.length; i++) {
/*  546 */         if (this.used[i] == null) {
/*  547 */           String unusedName = ClassInfoImpl.this.propOrder[i];
/*  548 */           String nearest = EditDistance.findNearest(unusedName, new AbstractList<String>() {
/*      */                 public String get(int index) {
/*  550 */                   return ((PropertyInfoImpl)ClassInfoImpl.this.properties.get(index)).getName();
/*      */                 }
/*      */                 
/*      */                 public int size() {
/*  554 */                   return ClassInfoImpl.this.properties.size();
/*      */                 }
/*      */               });
/*  557 */           boolean isOverriding = (i > ClassInfoImpl.this.properties.size() - 1) ? false : ((PropertyInfoImpl)ClassInfoImpl.this.properties.get(i)).hasAnnotation((Class)OverrideAnnotationOf.class);
/*  558 */           if (!isOverriding) {
/*  559 */             ClassInfoImpl.this.builder.reportError(new IllegalAnnotationException(Messages.PROPERTY_ORDER_CONTAINS_UNUSED_ENTRY.format(new Object[] { unusedName, nearest }, ), ClassInfoImpl.this));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasProperties() {
/*  567 */     return !this.properties.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T pickOne(T... args) {
/*  575 */     for (T arg : args) {
/*  576 */       if (arg != null)
/*  577 */         return arg; 
/*  578 */     }  return null;
/*      */   }
/*      */   
/*      */   private static <T> List<T> makeSet(T... args) {
/*  582 */     FinalArrayList<T> finalArrayList = new FinalArrayList();
/*  583 */     for (T arg : args) {
/*  584 */       if (arg != null) finalArrayList.add(arg); 
/*  585 */     }  return (List<T>)finalArrayList;
/*      */   }
/*      */   
/*      */   private static final class ConflictException extends Exception {
/*      */     final List<Annotation> annotations;
/*      */     
/*      */     public ConflictException(List<Annotation> one) {
/*  592 */       this.annotations = one;
/*      */     } }
/*      */   
/*      */   private static final class DuplicateException extends Exception {
/*      */     final Annotation a1;
/*      */     
/*      */     public DuplicateException(Annotation a1, Annotation a2) {
/*  599 */       this.a1 = a1;
/*  600 */       this.a2 = a2;
/*      */     }
/*      */     
/*      */     final Annotation a2;
/*      */   }
/*      */   
/*      */   private enum SecondaryAnnotation
/*      */   {
/*  608 */     JAVA_TYPE(1, new Class[] { XmlJavaTypeAdapter.class }),
/*  609 */     ID_IDREF(2, new Class[] { XmlID.class, XmlIDREF.class }),
/*  610 */     BINARY(4, new Class[] { XmlInlineBinaryData.class, XmlMimeType.class, XmlAttachmentRef.class }),
/*  611 */     ELEMENT_WRAPPER(8, new Class[] { XmlElementWrapper.class }),
/*  612 */     LIST(16, new Class[] { XmlList.class }),
/*  613 */     SCHEMA_TYPE(32, new Class[] { XmlSchemaType.class });
/*      */ 
/*      */ 
/*      */     
/*      */     final int bitMask;
/*      */ 
/*      */ 
/*      */     
/*      */     final Class<? extends Annotation>[] members;
/*      */ 
/*      */ 
/*      */     
/*      */     SecondaryAnnotation(int bitMask, Class<? extends Annotation>... members) {
/*  626 */       this.bitMask = bitMask;
/*  627 */       this.members = members;
/*      */     }
/*      */   }
/*      */   
/*  631 */   private static final SecondaryAnnotation[] SECONDARY_ANNOTATIONS = SecondaryAnnotation.values();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private enum PropertyGroup
/*      */   {
/*  640 */     TRANSIENT((String)new boolean[] { false, false, false, false, false, false }),
/*  641 */     ANY_ATTRIBUTE((String)new boolean[] { true, false, false, false, false, false }),
/*  642 */     ATTRIBUTE((String)new boolean[] { true, true, true, false, true, true }),
/*  643 */     VALUE((String)new boolean[] { true, true, true, false, true, true }),
/*  644 */     ELEMENT((String)new boolean[] { true, true, true, true, true, true }),
/*  645 */     ELEMENT_REF((String)new boolean[] { true, false, false, true, false, false }),
/*  646 */     MAP((String)new boolean[] { false, false, false, true, false, false });
/*      */ 
/*      */ 
/*      */     
/*      */     final int allowedsecondaryAnnotations;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PropertyGroup(boolean... bits) {
/*  656 */       int mask = 0;
/*  657 */       assert bits.length == ClassInfoImpl.SECONDARY_ANNOTATIONS.length;
/*  658 */       for (int i = 0; i < bits.length; i++) {
/*  659 */         if (bits[i])
/*  660 */           mask |= (ClassInfoImpl.SECONDARY_ANNOTATIONS[i]).bitMask; 
/*      */       } 
/*  662 */       this.allowedsecondaryAnnotations = mask ^ 0xFFFFFFFF;
/*      */     }
/*      */     
/*      */     boolean allows(ClassInfoImpl.SecondaryAnnotation a) {
/*  666 */       return ((this.allowedsecondaryAnnotations & a.bitMask) == 0);
/*      */     }
/*      */   }
/*      */   
/*  670 */   private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  675 */   private static final HashMap<Class, Integer> ANNOTATION_NUMBER_MAP = new HashMap<Class<?>, Integer>();
/*      */   static {
/*  677 */     Class[] annotations = { XmlTransient.class, XmlAnyAttribute.class, XmlAttribute.class, XmlValue.class, XmlElement.class, XmlElements.class, XmlElementRef.class, XmlElementRefs.class, XmlAnyElement.class, XmlMixed.class, OverrideAnnotationOf.class };
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
/*  691 */     HashMap<Class<?>, Integer> m = ANNOTATION_NUMBER_MAP;
/*      */ 
/*      */     
/*  694 */     for (Class<?> c : annotations) {
/*  695 */       m.put(c, Integer.valueOf(m.size()));
/*      */     }
/*      */     
/*  698 */     int index = 20;
/*  699 */     for (SecondaryAnnotation sa : SECONDARY_ANNOTATIONS) {
/*  700 */       for (Class<? extends Annotation> member : sa.members)
/*  701 */         m.put(member, Integer.valueOf(index)); 
/*  702 */       index++;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkConflict(Annotation a, Annotation b) throws DuplicateException {
/*  707 */     assert b != null;
/*  708 */     if (a != null) {
/*  709 */       throw new DuplicateException(a, b);
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addProperty(PropertySeed<T, C, F, M> seed, Annotation[] annotations, boolean dummy) {
/*  732 */     XmlTransient t = null;
/*  733 */     XmlAnyAttribute aa = null;
/*  734 */     XmlAttribute a = null;
/*  735 */     XmlValue v = null;
/*  736 */     XmlElement e1 = null;
/*  737 */     XmlElements e2 = null;
/*  738 */     XmlElementRef r1 = null;
/*  739 */     XmlElementRefs r2 = null;
/*  740 */     XmlAnyElement xae = null;
/*  741 */     XmlMixed mx = null;
/*  742 */     OverrideAnnotationOf ov = null;
/*      */ 
/*      */     
/*  745 */     int secondaryAnnotations = 0;
/*      */     
/*      */     try {
/*  748 */       for (Annotation ann : annotations) {
/*  749 */         Integer index = ANNOTATION_NUMBER_MAP.get(ann.annotationType());
/*  750 */         if (index != null) {
/*  751 */           switch (index.intValue()) { case 0:
/*  752 */               checkConflict(t, ann); t = (XmlTransient)ann; break;
/*  753 */             case 1: checkConflict(aa, ann); aa = (XmlAnyAttribute)ann; break;
/*  754 */             case 2: checkConflict(a, ann); a = (XmlAttribute)ann; break;
/*  755 */             case 3: checkConflict(v, ann); v = (XmlValue)ann; break;
/*  756 */             case 4: checkConflict(e1, ann); e1 = (XmlElement)ann; break;
/*  757 */             case 5: checkConflict(e2, ann); e2 = (XmlElements)ann; break;
/*  758 */             case 6: checkConflict(r1, ann); r1 = (XmlElementRef)ann; break;
/*  759 */             case 7: checkConflict(r2, ann); r2 = (XmlElementRefs)ann; break;
/*  760 */             case 8: checkConflict(xae, ann); xae = (XmlAnyElement)ann; break;
/*  761 */             case 9: checkConflict(mx, ann); mx = (XmlMixed)ann; break;
/*  762 */             case 10: checkConflict((Annotation)ov, ann); ov = (OverrideAnnotationOf)ann;
/*      */               break;
/*      */             default:
/*  765 */               secondaryAnnotations |= 1 << index.intValue() - 20;
/*      */               break; }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */       } 
/*  772 */       PropertyGroup group = null;
/*  773 */       int groupCount = 0;
/*      */       
/*  775 */       if (t != null) {
/*  776 */         group = PropertyGroup.TRANSIENT;
/*  777 */         groupCount++;
/*      */       } 
/*  779 */       if (aa != null) {
/*  780 */         group = PropertyGroup.ANY_ATTRIBUTE;
/*  781 */         groupCount++;
/*      */       } 
/*  783 */       if (a != null) {
/*  784 */         group = PropertyGroup.ATTRIBUTE;
/*  785 */         groupCount++;
/*      */       } 
/*  787 */       if (v != null) {
/*  788 */         group = PropertyGroup.VALUE;
/*  789 */         groupCount++;
/*      */       } 
/*  791 */       if (e1 != null || e2 != null) {
/*  792 */         group = PropertyGroup.ELEMENT;
/*  793 */         groupCount++;
/*      */       } 
/*  795 */       if (r1 != null || r2 != null || xae != null || mx != null || ov != null) {
/*  796 */         group = PropertyGroup.ELEMENT_REF;
/*  797 */         groupCount++;
/*      */       } 
/*      */       
/*  800 */       if (groupCount > 1) {
/*      */         
/*  802 */         List<Annotation> err = makeSet(new Annotation[] { t, aa, a, v, pickOne(new Annotation[] { e1, e2 }), pickOne(new Annotation[] { r1, r2, xae }) });
/*  803 */         throw new ConflictException(err);
/*      */       } 
/*      */       
/*  806 */       if (group == null) {
/*      */ 
/*      */         
/*  809 */         assert groupCount == 0;
/*      */ 
/*      */         
/*  812 */         if (nav().isSubClassOf(seed.getRawType(), nav().ref(Map.class)) && !seed.hasAnnotation(XmlJavaTypeAdapter.class))
/*      */         
/*  814 */         { group = PropertyGroup.MAP; }
/*      */         else
/*  816 */         { group = PropertyGroup.ELEMENT; } 
/*  817 */       } else if (group.equals(PropertyGroup.ELEMENT) && 
/*  818 */         nav().isSubClassOf(seed.getRawType(), nav().ref(Map.class)) && !seed.hasAnnotation(XmlJavaTypeAdapter.class)) {
/*  819 */         group = PropertyGroup.MAP;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  825 */       if ((secondaryAnnotations & group.allowedsecondaryAnnotations) != 0) {
/*      */         
/*  827 */         for (SecondaryAnnotation sa : SECONDARY_ANNOTATIONS) {
/*  828 */           if (!group.allows(sa))
/*      */           {
/*  830 */             for (Class<? extends Annotation> m : sa.members) {
/*  831 */               Annotation offender = seed.readAnnotation(m);
/*  832 */               if (offender != null) {
/*      */                 
/*  834 */                 this.builder.reportError(new IllegalAnnotationException(Messages.ANNOTATION_NOT_ALLOWED.format(new Object[] { m.getSimpleName() }, ), offender));
/*      */                 
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/*      */         assert false;
/*      */       } 
/*      */       
/*  845 */       switch (group) {
/*      */         case TRANSIENT:
/*      */           return;
/*      */         
/*      */         case ANY_ATTRIBUTE:
/*  850 */           if (this.attributeWildcard != null) {
/*  851 */             this.builder.reportError(new IllegalAnnotationException(Messages.TWO_ATTRIBUTE_WILDCARDS.format(new Object[] { nav().getClassName(getClazz()) }, ), aa, this.attributeWildcard));
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/*  856 */           this.attributeWildcard = seed;
/*      */           
/*  858 */           if (inheritsAttributeWildcard()) {
/*  859 */             this.builder.reportError(new IllegalAnnotationException(Messages.SUPER_CLASS_HAS_WILDCARD.format(new Object[0]), aa, getInheritedAttributeWildcard()));
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */ 
/*      */           
/*  866 */           if (!nav().isSubClassOf(seed.getRawType(), nav().ref(Map.class))) {
/*  867 */             this.builder.reportError(new IllegalAnnotationException(Messages.INVALID_ATTRIBUTE_WILDCARD_TYPE.format(new Object[] { nav().getTypeName(seed.getRawType()) }, ), aa, getInheritedAttributeWildcard()));
/*      */             return;
/*      */           } 
/*      */           return;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case ATTRIBUTE:
/*  876 */           this.properties.add(createAttributeProperty(seed));
/*      */           return;
/*      */         case VALUE:
/*  879 */           this.properties.add(createValueProperty(seed));
/*      */           return;
/*      */         case ELEMENT:
/*  882 */           this.properties.add(createElementProperty(seed));
/*      */           return;
/*      */         case ELEMENT_REF:
/*  885 */           this.properties.add(createReferenceProperty(seed));
/*      */           return;
/*      */         case MAP:
/*  888 */           this.properties.add(createMapProperty(seed));
/*      */           return;
/*      */       } 
/*      */       
/*      */       assert false;
/*  893 */     } catch (ConflictException x) {
/*      */       
/*  895 */       List<Annotation> err = x.annotations;
/*      */       
/*  897 */       this.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS.format(new Object[] { nav().getClassName(getClazz()) + '#' + seed.getName(), ((Annotation)err.get(0)).annotationType().getName(), ((Annotation)err.get(1)).annotationType().getName() }, ), err.get(0), err.get(1)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  904 */     catch (DuplicateException e) {
/*      */       
/*  906 */       this.builder.reportError(new IllegalAnnotationException(Messages.DUPLICATE_ANNOTATIONS.format(new Object[] { e.a1.annotationType().getName() }, ), e.a1, e.a2));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ReferencePropertyInfoImpl<T, C, F, M> createReferenceProperty(PropertySeed<T, C, F, M> seed) {
/*  915 */     return new ReferencePropertyInfoImpl<T, C, F, M>(this, seed);
/*      */   }
/*      */   
/*      */   protected AttributePropertyInfoImpl<T, C, F, M> createAttributeProperty(PropertySeed<T, C, F, M> seed) {
/*  919 */     return new AttributePropertyInfoImpl<T, C, F, M>(this, seed);
/*      */   }
/*      */   
/*      */   protected ValuePropertyInfoImpl<T, C, F, M> createValueProperty(PropertySeed<T, C, F, M> seed) {
/*  923 */     return new ValuePropertyInfoImpl<T, C, F, M>(this, seed);
/*      */   }
/*      */   
/*      */   protected ElementPropertyInfoImpl<T, C, F, M> createElementProperty(PropertySeed<T, C, F, M> seed) {
/*  927 */     return new ElementPropertyInfoImpl<T, C, F, M>(this, seed);
/*      */   }
/*      */   
/*      */   protected MapPropertyInfoImpl<T, C, F, M> createMapProperty(PropertySeed<T, C, F, M> seed) {
/*  931 */     return new MapPropertyInfoImpl<T, C, F, M>(this, seed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findGetterSetterProperties(XmlAccessType at) {
/*  941 */     Map<String, M> getters = new LinkedHashMap<String, M>();
/*  942 */     Map<String, M> setters = new LinkedHashMap<String, M>();
/*      */     
/*  944 */     C c = this.clazz;
/*      */     do {
/*  946 */       collectGetterSetters(this.clazz, getters, setters);
/*      */ 
/*      */       
/*  949 */       c = (C)nav().getSuperClass(c);
/*  950 */     } while (shouldRecurseSuperClass(c));
/*      */ 
/*      */ 
/*      */     
/*  954 */     Set<String> complete = new TreeSet<String>(getters.keySet());
/*  955 */     complete.retainAll(setters.keySet());
/*      */     
/*  957 */     resurrect(getters, complete);
/*  958 */     resurrect(setters, complete);
/*      */ 
/*      */     
/*  961 */     for (String name : complete) {
/*  962 */       M getter = getters.get(name);
/*  963 */       M setter = setters.get(name);
/*      */       
/*  965 */       Annotation[] ga = (getter != null) ? reader().getAllMethodAnnotations(getter, (Locatable)new MethodLocatable(this, getter, nav())) : EMPTY_ANNOTATIONS;
/*  966 */       Annotation[] sa = (setter != null) ? reader().getAllMethodAnnotations(setter, (Locatable)new MethodLocatable(this, setter, nav())) : EMPTY_ANNOTATIONS;
/*      */       
/*  968 */       boolean hasAnnotation = (hasJAXBAnnotation(ga) || hasJAXBAnnotation(sa));
/*  969 */       boolean isOverriding = false;
/*  970 */       if (!hasAnnotation)
/*      */       {
/*      */         
/*  973 */         isOverriding = (getter != null && nav().isOverriding(getter, c) && setter != null && nav().isOverriding(setter, c));
/*      */       }
/*      */ 
/*      */       
/*  977 */       if ((at == XmlAccessType.PROPERTY && !isOverriding) || (at == XmlAccessType.PUBLIC_MEMBER && isConsideredPublic(getter) && isConsideredPublic(setter) && !isOverriding) || hasAnnotation) {
/*      */         Annotation[] r;
/*      */ 
/*      */         
/*  981 */         if (getter != null && setter != null && !nav().isSameType(nav().getReturnType(getter), nav().getMethodParameters(setter)[0])) {
/*      */ 
/*      */           
/*  984 */           this.builder.reportError(new IllegalAnnotationException(Messages.GETTER_SETTER_INCOMPATIBLE_TYPE.format(new Object[] { nav().getTypeName(nav().getReturnType(getter)), nav().getTypeName(nav().getMethodParameters(setter)[0]) }, ), (Locatable)new MethodLocatable(this, getter, nav()), (Locatable)new MethodLocatable(this, setter, nav())));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  996 */         if (ga.length == 0) {
/*  997 */           r = sa;
/*      */         }
/*  999 */         else if (sa.length == 0) {
/* 1000 */           r = ga;
/*      */         } else {
/* 1002 */           r = new Annotation[ga.length + sa.length];
/* 1003 */           System.arraycopy(ga, 0, r, 0, ga.length);
/* 1004 */           System.arraycopy(sa, 0, r, ga.length, sa.length);
/*      */         } 
/*      */         
/* 1007 */         addProperty(createAccessorSeed(getter, setter), r, false);
/*      */       } 
/*      */     } 
/*      */     
/* 1011 */     getters.keySet().removeAll(complete);
/* 1012 */     setters.keySet().removeAll(complete);
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
/*      */ 
/*      */   
/*      */   private void collectGetterSetters(C c, Map<String, M> getters, Map<String, M> setters) {
/* 1029 */     C sc = (C)nav().getSuperClass(c);
/* 1030 */     if (shouldRecurseSuperClass(sc)) {
/* 1031 */       collectGetterSetters(sc, getters, setters);
/*      */     }
/* 1033 */     Collection<? extends M> methods = nav().getDeclaredMethods(c);
/* 1034 */     Map<String, List<M>> allSetters = new LinkedHashMap<String, List<M>>();
/* 1035 */     for (M method : methods) {
/* 1036 */       boolean used = false;
/*      */       
/* 1038 */       if (nav().isBridgeMethod(method)) {
/*      */         continue;
/*      */       }
/* 1041 */       String name = nav().getMethodName(method);
/* 1042 */       int arity = (nav().getMethodParameters(method)).length;
/*      */       
/* 1044 */       if (nav().isStaticMethod(method)) {
/* 1045 */         ensureNoAnnotation(method);
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1050 */       String propName = getPropertyNameFromGetMethod(name);
/* 1051 */       if (propName != null && arity == 0) {
/* 1052 */         getters.put(propName, method);
/* 1053 */         used = true;
/*      */       } 
/*      */ 
/*      */       
/* 1057 */       propName = getPropertyNameFromSetMethod(name);
/* 1058 */       if (propName != null && arity == 1) {
/* 1059 */         List<M> propSetters = allSetters.get(propName);
/* 1060 */         if (null == propSetters) {
/* 1061 */           propSetters = new ArrayList<M>();
/* 1062 */           allSetters.put(propName, propSetters);
/*      */         } 
/* 1064 */         propSetters.add(method);
/* 1065 */         used = true;
/*      */       } 
/*      */       
/* 1068 */       if (!used) {
/* 1069 */         ensureNoAnnotation(method);
/*      */       }
/*      */     } 
/*      */     
/* 1073 */     for (Map.Entry<String, M> entry : getters.entrySet()) {
/* 1074 */       String propName = entry.getKey();
/* 1075 */       M getter = entry.getValue();
/* 1076 */       List<M> propSetters = allSetters.remove(propName);
/* 1077 */       if (null == propSetters) {
/*      */         continue;
/*      */       }
/*      */       
/* 1081 */       T getterType = (T)nav().getReturnType(getter);
/* 1082 */       for (M setter : propSetters) {
/* 1083 */         T setterType = (T)nav().getMethodParameters(setter)[0];
/* 1084 */         if (nav().isSameType(setterType, getterType)) {
/* 1085 */           setters.put(propName, setter);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1092 */     for (Map.Entry<String, List<M>> e : allSetters.entrySet()) {
/* 1093 */       setters.put(e.getKey(), ((List<M>)e.getValue()).get(0));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldRecurseSuperClass(C sc) {
/* 1101 */     return (sc != null && (this.builder.isReplaced(sc) || reader().hasClassAnnotation(sc, XmlTransient.class)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isConsideredPublic(M m) {
/* 1109 */     return (m == null || nav().isPublicMethod(m));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resurrect(Map<String, M> methods, Set<String> complete) {
/* 1117 */     for (Map.Entry<String, M> e : methods.entrySet()) {
/* 1118 */       if (complete.contains(e.getKey()))
/*      */         continue; 
/* 1120 */       if (hasJAXBAnnotation(reader().getAllMethodAnnotations(e.getValue(), this))) {
/* 1121 */         complete.add(e.getKey());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureNoAnnotation(M method) {
/* 1130 */     Annotation[] annotations = reader().getAllMethodAnnotations(method, this);
/* 1131 */     for (Annotation a : annotations) {
/* 1132 */       if (isJAXBAnnotation(a)) {
/* 1133 */         this.builder.reportError(new IllegalAnnotationException(Messages.ANNOTATION_ON_WRONG_METHOD.format(new Object[0]), a));
/*      */         return;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isJAXBAnnotation(Annotation a) {
/* 1145 */     return ANNOTATION_NUMBER_MAP.containsKey(a.annotationType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean hasJAXBAnnotation(Annotation[] annotations) {
/* 1152 */     return (getSomeJAXBAnnotation(annotations) != null);
/*      */   }
/*      */   
/*      */   private static Annotation getSomeJAXBAnnotation(Annotation[] annotations) {
/* 1156 */     for (Annotation a : annotations) {
/* 1157 */       if (isJAXBAnnotation(a))
/* 1158 */         return a; 
/* 1159 */     }  return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getPropertyNameFromGetMethod(String name) {
/* 1170 */     if (name.startsWith("get") && name.length() > 3)
/* 1171 */       return name.substring(3); 
/* 1172 */     if (name.startsWith("is") && name.length() > 2)
/* 1173 */       return name.substring(2); 
/* 1174 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getPropertyNameFromSetMethod(String name) {
/* 1184 */     if (name.startsWith("set") && name.length() > 3)
/* 1185 */       return name.substring(3); 
/* 1186 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertySeed<T, C, F, M> createFieldSeed(F f) {
/* 1196 */     return new FieldPropertySeed<T, C, F, M>(this, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertySeed<T, C, F, M> createAccessorSeed(M getter, M setter) {
/* 1203 */     return new GetterSetterPropertySeed<T, C, F, M>(this, getter, setter);
/*      */   }
/*      */   
/*      */   public final boolean isElement() {
/* 1207 */     return (this.elementName != null);
/*      */   }
/*      */   
/*      */   public boolean isAbstract() {
/* 1211 */     return nav().isAbstract(this.clazz);
/*      */   }
/*      */   
/*      */   public boolean isOrdered() {
/* 1215 */     return (this.propOrder != null);
/*      */   }
/*      */   
/*      */   public final boolean isFinal() {
/* 1219 */     return nav().isFinal(this.clazz);
/*      */   }
/*      */   
/*      */   public final boolean hasSubClasses() {
/* 1223 */     return this.hasSubClasses;
/*      */   }
/*      */   
/*      */   public final boolean hasAttributeWildcard() {
/* 1227 */     return (declaresAttributeWildcard() || inheritsAttributeWildcard());
/*      */   }
/*      */   
/*      */   public final boolean inheritsAttributeWildcard() {
/* 1231 */     return (getInheritedAttributeWildcard() != null);
/*      */   }
/*      */   
/*      */   public final boolean declaresAttributeWildcard() {
/* 1235 */     return (this.attributeWildcard != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertySeed<T, C, F, M> getInheritedAttributeWildcard() {
/* 1242 */     for (ClassInfoImpl<T, C, F, M> c = getBaseClass(); c != null; c = c.getBaseClass()) {
/* 1243 */       if (c.attributeWildcard != null)
/* 1244 */         return c.attributeWildcard; 
/* 1245 */     }  return null;
/*      */   }
/*      */   
/*      */   public final QName getElementName() {
/* 1249 */     return this.elementName;
/*      */   }
/*      */   
/*      */   public final QName getTypeName() {
/* 1253 */     return this.typeName;
/*      */   }
/*      */   
/*      */   public final boolean isSimpleType() {
/* 1257 */     List<? extends PropertyInfo> props = (List)getProperties();
/* 1258 */     if (props.size() != 1) return false; 
/* 1259 */     return (((PropertyInfo)props.get(0)).kind() == PropertyKind.VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void link() {
/* 1267 */     getProperties();
/*      */ 
/*      */     
/* 1270 */     Map<String, PropertyInfoImpl> names = new HashMap<String, PropertyInfoImpl>();
/* 1271 */     for (PropertyInfoImpl<T, C, F, M> p : this.properties) {
/* 1272 */       p.link();
/* 1273 */       PropertyInfoImpl old = names.put(p.getName(), p);
/* 1274 */       if (old != null) {
/* 1275 */         this.builder.reportError(new IllegalAnnotationException(Messages.PROPERTY_COLLISION.format(new Object[] { p.getName() }, ), p, old));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1280 */     super.link();
/*      */   }
/*      */   
/*      */   public Location getLocation() {
/* 1284 */     return nav().getClassLocation(this.clazz);
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
/*      */   private boolean hasFactoryConstructor(XmlType t) {
/* 1296 */     if (t == null) return false;
/*      */     
/* 1298 */     String method = t.factoryMethod();
/* 1299 */     T fClass = (T)reader().getClassValue(t, "factoryClass");
/* 1300 */     if (method.length() > 0) {
/* 1301 */       if (nav().isSameType(fClass, nav().ref(XmlType.DEFAULT.class))) {
/* 1302 */         fClass = (T)nav().use(this.clazz);
/*      */       }
/* 1304 */       for (M m : nav().getDeclaredMethods(nav().asDecl(fClass))) {
/*      */         
/* 1306 */         if (nav().getMethodName(m).equals(method) && nav().isSameType(nav().getReturnType(m), nav().use(this.clazz)) && (nav().getMethodParameters(m)).length == 0 && nav().isStaticMethod(m)) {
/*      */ 
/*      */ 
/*      */           
/* 1310 */           this.factoryMethod = m;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1314 */       if (this.factoryMethod == null) {
/* 1315 */         this.builder.reportError(new IllegalAnnotationException(Messages.NO_FACTORY_METHOD.format(new Object[] { nav().getClassName(nav().asDecl(fClass)), method }, ), this));
/*      */       }
/*      */     }
/* 1318 */     else if (!nav().isSameType(fClass, nav().ref(XmlType.DEFAULT.class))) {
/* 1319 */       this.builder.reportError(new IllegalAnnotationException(Messages.FACTORY_CLASS_NEEDS_FACTORY_METHOD.format(new Object[] { nav().getClassName(nav().asDecl(fClass)) }, ), this));
/*      */     } 
/*      */     
/* 1322 */     return (this.factoryMethod != null);
/*      */   }
/*      */   
/*      */   public Method getFactoryMethod() {
/* 1326 */     return (Method)this.factoryMethod;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1331 */     return "ClassInfo(" + this.clazz + ')';
/*      */   }
/*      */   
/* 1334 */   private static final String[] DEFAULT_ORDER = new String[0];
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ClassInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */