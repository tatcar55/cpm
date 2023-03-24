/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.bind.v2.model.core.Element;
/*     */ import com.sun.xml.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.ReferencePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.WildcardMode;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAnyElement;
/*     */ import javax.xml.bind.annotation.XmlElementRef;
/*     */ import javax.xml.bind.annotation.XmlElementRefs;
/*     */ import javax.xml.bind.annotation.XmlMixed;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ReferencePropertyInfoImpl<T, C, F, M>
/*     */   extends ERPropertyInfoImpl<T, C, F, M>
/*     */   implements ReferencePropertyInfo<T, C>, DummyPropertyInfo<T, C, F, M>
/*     */ {
/*     */   private Set<Element<T, C>> types;
/*  82 */   private Set<ReferencePropertyInfoImpl<T, C, F, M>> subTypes = new LinkedHashSet<ReferencePropertyInfoImpl<T, C, F, M>>();
/*     */ 
/*     */   
/*     */   private final boolean isMixed;
/*     */ 
/*     */   
/*     */   private final WildcardMode wildcard;
/*     */ 
/*     */   
/*     */   private final C domHandler;
/*     */ 
/*     */   
/*     */   private Boolean isRequired;
/*     */ 
/*     */   
/*     */   public ReferencePropertyInfoImpl(ClassInfoImpl<T, C, F, M> classInfo, PropertySeed<T, C, F, M> seed) {
/*  98 */     super(classInfo, seed);
/*     */     
/* 100 */     this.isMixed = (seed.readAnnotation(XmlMixed.class) != null);
/*     */     
/* 102 */     XmlAnyElement xae = (XmlAnyElement)seed.readAnnotation(XmlAnyElement.class);
/* 103 */     if (xae == null) {
/* 104 */       this.wildcard = null;
/* 105 */       this.domHandler = null;
/*     */     } else {
/* 107 */       this.wildcard = xae.lax() ? WildcardMode.LAX : WildcardMode.SKIP;
/* 108 */       this.domHandler = (C)nav().asDecl(reader().getClassValue(xae, "value"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<? extends Element<T, C>> ref() {
/* 113 */     return getElements();
/*     */   }
/*     */   
/*     */   public PropertyKind kind() {
/* 117 */     return PropertyKind.REFERENCE;
/*     */   }
/*     */   
/*     */   public Set<? extends Element<T, C>> getElements() {
/* 121 */     if (this.types == null)
/* 122 */       calcTypes(false); 
/* 123 */     assert this.types != null;
/* 124 */     return this.types;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calcTypes(boolean last) {
/*     */     XmlElementRef[] ann;
/* 135 */     this.types = new LinkedHashSet<Element<T, C>>();
/* 136 */     XmlElementRefs refs = (XmlElementRefs)this.seed.readAnnotation(XmlElementRefs.class);
/* 137 */     XmlElementRef ref = (XmlElementRef)this.seed.readAnnotation(XmlElementRef.class);
/*     */     
/* 139 */     if (refs != null && ref != null) {
/* 140 */       this.parent.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS.format(new Object[] { nav().getClassName(this.parent.getClazz()) + '#' + this.seed.getName(), ref.annotationType().getName(), refs.annotationType().getName() }, ), ref, refs));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     if (refs != null) {
/* 148 */       ann = refs.value();
/*     */     }
/* 150 */     else if (ref != null) {
/* 151 */       ann = new XmlElementRef[] { ref };
/*     */     } else {
/* 153 */       ann = null;
/*     */     } 
/*     */     
/* 156 */     this.isRequired = Boolean.valueOf(!isCollection());
/*     */     
/* 158 */     if (ann != null) {
/* 159 */       Navigator<T, C, F, M> nav = nav();
/* 160 */       AnnotationReader<T, C, F, M> reader = reader();
/*     */       
/* 162 */       T defaultType = (T)nav.ref(XmlElementRef.DEFAULT.class);
/* 163 */       C je = (C)nav.asDecl(JAXBElement.class);
/*     */       
/* 165 */       for (XmlElementRef r : ann) {
/*     */         boolean yield;
/* 167 */         T type = (T)reader.getClassValue(r, "type");
/* 168 */         if (nav().isSameType(type, defaultType))
/* 169 */           type = (T)nav.erasure(getIndividualType()); 
/* 170 */         if (nav.getBaseClass(type, je) != null) {
/* 171 */           yield = addGenericElement(r);
/*     */         } else {
/* 173 */           yield = addAllSubtypes(type);
/*     */         } 
/*     */ 
/*     */         
/* 177 */         if (this.isRequired.booleanValue() && !isRequired(r)) {
/* 178 */           this.isRequired = Boolean.valueOf(false);
/*     */         }
/* 180 */         if (last && !yield) {
/*     */ 
/*     */           
/* 183 */           if (nav().isSameType(type, nav.ref(JAXBElement.class))) {
/*     */             
/* 185 */             this.parent.builder.reportError(new IllegalAnnotationException(Messages.NO_XML_ELEMENT_DECL.format(new Object[] { getEffectiveNamespaceFor(r), r.name() }, ), this));
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 191 */             this.parent.builder.reportError(new IllegalAnnotationException(Messages.INVALID_XML_ELEMENT_REF.format(new Object[] { type }, ), this));
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 204 */     for (ReferencePropertyInfoImpl<T, C, F, M> info : this.subTypes) {
/* 205 */       PropertySeed<T, C, F, M> sd = info.seed;
/* 206 */       refs = (XmlElementRefs)sd.readAnnotation(XmlElementRefs.class);
/* 207 */       ref = (XmlElementRef)sd.readAnnotation(XmlElementRef.class);
/*     */       
/* 209 */       if (refs != null && ref != null) {
/* 210 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.MUTUALLY_EXCLUSIVE_ANNOTATIONS.format(new Object[] { nav().getClassName(this.parent.getClazz()) + '#' + this.seed.getName(), ref.annotationType().getName(), refs.annotationType().getName() }, ), ref, refs));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       if (refs != null) {
/* 218 */         ann = refs.value();
/*     */       }
/* 220 */       else if (ref != null) {
/* 221 */         ann = new XmlElementRef[] { ref };
/*     */       } else {
/* 223 */         ann = null;
/*     */       } 
/*     */ 
/*     */       
/* 227 */       if (ann != null) {
/* 228 */         Navigator<T, C, F, M> nav = nav();
/* 229 */         AnnotationReader<T, C, F, M> reader = reader();
/*     */         
/* 231 */         T defaultType = (T)nav.ref(XmlElementRef.DEFAULT.class);
/* 232 */         C je = (C)nav.asDecl(JAXBElement.class);
/*     */         
/* 234 */         for (XmlElementRef r : ann) {
/*     */           boolean yield;
/* 236 */           T type = (T)reader.getClassValue(r, "type");
/* 237 */           if (nav().isSameType(type, defaultType)) {
/* 238 */             type = (T)nav.erasure(getIndividualType());
/*     */           }
/* 240 */           if (nav.getBaseClass(type, je) != null) {
/* 241 */             yield = addGenericElement(r, info);
/*     */           } else {
/*     */             
/* 244 */             yield = addAllSubtypes(type);
/*     */           } 
/*     */           
/* 247 */           if (last && !yield) {
/*     */ 
/*     */             
/* 250 */             if (nav().isSameType(type, nav.ref(JAXBElement.class))) {
/*     */               
/* 252 */               this.parent.builder.reportError(new IllegalAnnotationException(Messages.NO_XML_ELEMENT_DECL.format(new Object[] { getEffectiveNamespaceFor(r), r.name() }, ), this));
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 258 */               this.parent.builder.reportError(new IllegalAnnotationException(Messages.INVALID_XML_ELEMENT_REF.format(new Object[0]), this));
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 272 */     this.types = Collections.unmodifiableSet(this.types);
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 276 */     if (this.isRequired == null)
/* 277 */       calcTypes(false); 
/* 278 */     return this.isRequired.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean is2_2 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRequired(XmlElementRef ref) {
/* 293 */     if (!is2_2) return true;
/*     */     
/*     */     try {
/* 296 */       return ref.required();
/* 297 */     } catch (LinkageError e) {
/* 298 */       is2_2 = false;
/* 299 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addGenericElement(XmlElementRef r) {
/* 308 */     String nsUri = getEffectiveNamespaceFor(r);
/*     */     
/* 310 */     return addGenericElement(this.parent.owner.getElementInfo(this.parent.getClazz(), new QName(nsUri, r.name())));
/*     */   }
/*     */   
/*     */   private boolean addGenericElement(XmlElementRef r, ReferencePropertyInfoImpl<T, C, F, M> info) {
/* 314 */     String nsUri = info.getEffectiveNamespaceFor(r);
/* 315 */     ElementInfo<T, C, F, M> ei = (ElementInfo<T, C, F, M>)this.parent.owner.getElementInfo(info.parent.getClazz(), new QName(nsUri, r.name()));
/* 316 */     this.types.add(ei);
/* 317 */     return true;
/*     */   }
/*     */   
/*     */   private String getEffectiveNamespaceFor(XmlElementRef r) {
/* 321 */     String nsUri = r.namespace();
/*     */     
/* 323 */     XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, this.parent.getClazz(), this);
/* 324 */     if (xs != null && xs.attributeFormDefault() == XmlNsForm.QUALIFIED)
/*     */     {
/*     */       
/* 327 */       if (nsUri.length() == 0) {
/* 328 */         nsUri = this.parent.builder.defaultNsUri;
/*     */       }
/*     */     }
/* 331 */     return nsUri;
/*     */   }
/*     */   
/*     */   private boolean addGenericElement(ElementInfo<T, C> ei) {
/* 335 */     if (ei == null)
/* 336 */       return false; 
/* 337 */     this.types.add(ei);
/* 338 */     for (ElementInfo<T, C> subst : (Iterable<ElementInfo<T, C>>)ei.getSubstitutionMembers())
/* 339 */       addGenericElement(subst); 
/* 340 */     return true;
/*     */   }
/*     */   
/*     */   private boolean addAllSubtypes(T type) {
/* 344 */     Navigator<T, C, F, M> nav = nav();
/*     */ 
/*     */     
/* 347 */     NonElement<T, C> t = this.parent.builder.getClassInfo((C)nav.asDecl(type), this);
/* 348 */     if (!(t instanceof ClassInfo))
/*     */     {
/* 350 */       return false;
/*     */     }
/* 352 */     boolean result = false;
/*     */     
/* 354 */     ClassInfo<T, C> c = (ClassInfo<T, C>)t;
/* 355 */     if (c.isElement()) {
/* 356 */       this.types.add(c.asElement());
/* 357 */       result = true;
/*     */     } 
/*     */ 
/*     */     
/* 361 */     for (ClassInfo<T, C> ci : (Iterable<ClassInfo<T, C>>)this.parent.owner.beans().values()) {
/* 362 */       if (ci.isElement() && nav.isSubClassOf(ci.getType(), type)) {
/* 363 */         this.types.add(ci.asElement());
/* 364 */         result = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 369 */     for (ElementInfo<T, C> ei : (Iterable<ElementInfo<T, C>>)this.parent.owner.getElementMappings(null).values()) {
/* 370 */       if (nav.isSubClassOf(ei.getType(), type)) {
/* 371 */         this.types.add(ei);
/* 372 */         result = true;
/*     */       } 
/*     */     } 
/*     */     
/* 376 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void link() {
/* 382 */     super.link();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 387 */     calcTypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addType(PropertyInfoImpl<T, C, F, M> info) {
/* 393 */     this.subTypes.add((ReferencePropertyInfoImpl<T, C, F, M>)info);
/*     */   }
/*     */   
/*     */   public final boolean isMixed() {
/* 397 */     return this.isMixed;
/*     */   }
/*     */   
/*     */   public final WildcardMode getWildcard() {
/* 401 */     return this.wildcard;
/*     */   }
/*     */   
/*     */   public final C getDOMHandler() {
/* 405 */     return this.domHandler;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ReferencePropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */