/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.TODO;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import com.sun.xml.bind.v2.runtime.SwaRefAdapter;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collection;
/*     */ import javax.activation.MimeType;
/*     */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlElementWrapper;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlIDREF;
/*     */ import javax.xml.bind.annotation.XmlInlineBinaryData;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class PropertyInfoImpl<T, C, F, M>
/*     */   implements PropertyInfo<T, C>, Locatable, Comparable<PropertyInfoImpl>
/*     */ {
/*     */   protected final PropertySeed<T, C, F, M> seed;
/*     */   private final boolean isCollection;
/*     */   private final ID id;
/*     */   private final MimeType expectedMimeType;
/*     */   private final boolean inlineBinary;
/*     */   private final QName schemaType;
/*     */   protected final ClassInfoImpl<T, C, F, M> parent;
/*     */   private final Adapter<T, C> adapter;
/*     */   
/*     */   protected PropertyInfoImpl(ClassInfoImpl<T, C, F, M> parent, PropertySeed<T, C, F, M> spi) {
/*  99 */     this.seed = spi;
/* 100 */     this.parent = parent;
/*     */     
/* 102 */     if (parent == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       throw new AssertionError();
/*     */     }
/* 112 */     MimeType mt = Util.calcExpectedMediaType(this.seed, parent.builder);
/* 113 */     if (mt != null && !(kind()).canHaveXmlMimeType) {
/* 114 */       parent.builder.reportError(new IllegalAnnotationException(Messages.ILLEGAL_ANNOTATION.format(new Object[] { XmlMimeType.class.getName() }, ), this.seed.readAnnotation(XmlMimeType.class)));
/*     */ 
/*     */ 
/*     */       
/* 118 */       mt = null;
/*     */     } 
/* 120 */     this.expectedMimeType = mt;
/* 121 */     this.inlineBinary = this.seed.hasAnnotation(XmlInlineBinaryData.class);
/*     */     
/* 123 */     T t = this.seed.getRawType();
/*     */ 
/*     */     
/* 126 */     XmlJavaTypeAdapter xjta = getApplicableAdapter(t);
/* 127 */     if (xjta != null) {
/* 128 */       this.isCollection = false;
/* 129 */       this.adapter = new Adapter(xjta, reader(), nav());
/*     */     }
/*     */     else {
/*     */       
/* 133 */       this.isCollection = (nav().isSubClassOf(t, nav().ref(Collection.class)) || nav().isArrayButNotByteArray(t));
/*     */ 
/*     */       
/* 136 */       xjta = getApplicableAdapter(getIndividualType());
/* 137 */       if (xjta == null) {
/*     */         
/* 139 */         XmlAttachmentRef xsa = (XmlAttachmentRef)this.seed.readAnnotation(XmlAttachmentRef.class);
/* 140 */         if (xsa != null) {
/* 141 */           parent.builder.hasSwaRef = true;
/* 142 */           this.adapter = new Adapter(nav().asDecl(SwaRefAdapter.class), nav());
/*     */         } else {
/* 144 */           this.adapter = null;
/*     */ 
/*     */ 
/*     */           
/* 148 */           xjta = (XmlJavaTypeAdapter)this.seed.readAnnotation(XmlJavaTypeAdapter.class);
/* 149 */           if (xjta != null) {
/* 150 */             T ad = (T)reader().getClassValue(xjta, "value");
/* 151 */             parent.builder.reportError(new IllegalAnnotationException(Messages.UNMATCHABLE_ADAPTER.format(new Object[] { nav().getTypeName(ad), nav().getTypeName(t) }, ), xjta));
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 159 */         this.adapter = new Adapter(xjta, reader(), nav());
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     this.id = calcId();
/* 164 */     this.schemaType = Util.calcSchemaType(reader(), this.seed, parent.clazz, getIndividualType(), this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassInfoImpl<T, C, F, M> parent() {
/* 170 */     return this.parent;
/*     */   }
/*     */   
/*     */   protected final Navigator<T, C, F, M> nav() {
/* 174 */     return this.parent.nav();
/*     */   }
/*     */   protected final AnnotationReader<T, C, F, M> reader() {
/* 177 */     return this.parent.reader();
/*     */   }
/*     */   
/*     */   public T getRawType() {
/* 181 */     return this.seed.getRawType();
/*     */   }
/*     */   
/*     */   public T getIndividualType() {
/* 185 */     if (this.adapter != null)
/* 186 */       return (T)this.adapter.defaultType; 
/* 187 */     T raw = getRawType();
/* 188 */     if (!isCollection()) {
/* 189 */       return raw;
/*     */     }
/* 191 */     if (nav().isArrayButNotByteArray(raw)) {
/* 192 */       return (T)nav().getComponentType(raw);
/*     */     }
/* 194 */     T bt = (T)nav().getBaseClass(raw, nav().asDecl(Collection.class));
/* 195 */     if (nav().isParameterizedType(bt)) {
/* 196 */       return (T)nav().getTypeArgument(bt, 0);
/*     */     }
/* 198 */     return (T)nav().ref(Object.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 203 */     return this.seed.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isApplicable(XmlJavaTypeAdapter jta, T declaredType) {
/* 210 */     if (jta == null) return false;
/*     */     
/* 212 */     T type = (T)reader().getClassValue(jta, "type");
/* 213 */     if (nav().isSameType(declaredType, type)) {
/* 214 */       return true;
/*     */     }
/* 216 */     T ad = (T)reader().getClassValue(jta, "value");
/* 217 */     T ba = (T)nav().getBaseClass(ad, nav().asDecl(XmlAdapter.class));
/* 218 */     if (!nav().isParameterizedType(ba))
/* 219 */       return true; 
/* 220 */     T inMemType = (T)nav().getTypeArgument(ba, 1);
/*     */     
/* 222 */     return nav().isSubClassOf(declaredType, inMemType);
/*     */   }
/*     */   
/*     */   private XmlJavaTypeAdapter getApplicableAdapter(T type) {
/* 226 */     XmlJavaTypeAdapter jta = (XmlJavaTypeAdapter)this.seed.readAnnotation(XmlJavaTypeAdapter.class);
/* 227 */     if (jta != null && isApplicable(jta, type)) {
/* 228 */       return jta;
/*     */     }
/*     */     
/* 231 */     XmlJavaTypeAdapters jtas = (XmlJavaTypeAdapters)reader().getPackageAnnotation(XmlJavaTypeAdapters.class, this.parent.clazz, this.seed);
/* 232 */     if (jtas != null)
/* 233 */       for (XmlJavaTypeAdapter xjta : jtas.value()) {
/* 234 */         if (isApplicable(xjta, type)) {
/* 235 */           return xjta;
/*     */         }
/*     */       }  
/* 238 */     jta = (XmlJavaTypeAdapter)reader().getPackageAnnotation(XmlJavaTypeAdapter.class, this.parent.clazz, this.seed);
/* 239 */     if (isApplicable(jta, type)) {
/* 240 */       return jta;
/*     */     }
/*     */     
/* 243 */     C refType = (C)nav().asDecl(type);
/* 244 */     if (refType != null) {
/* 245 */       jta = (XmlJavaTypeAdapter)reader().getClassAnnotation(XmlJavaTypeAdapter.class, refType, this.seed);
/* 246 */       if (jta != null && isApplicable(jta, type)) {
/* 247 */         return jta;
/*     */       }
/*     */     } 
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Adapter<T, C> getAdapter() {
/* 258 */     return this.adapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String displayName() {
/* 263 */     return nav().getClassName(this.parent.getClazz()) + '#' + getName();
/*     */   }
/*     */   
/*     */   public final ID id() {
/* 267 */     return this.id;
/*     */   }
/*     */   
/*     */   private ID calcId() {
/* 271 */     if (this.seed.hasAnnotation(XmlID.class)) {
/*     */       
/* 273 */       if (!nav().isSameType(getIndividualType(), nav().ref(String.class))) {
/* 274 */         this.parent.builder.reportError(new IllegalAnnotationException(Messages.ID_MUST_BE_STRING.format(new Object[] { getName() }, ), this.seed));
/*     */       }
/*     */       
/* 277 */       return ID.ID;
/*     */     } 
/* 279 */     if (this.seed.hasAnnotation(XmlIDREF.class)) {
/* 280 */       return ID.IDREF;
/*     */     }
/* 282 */     return ID.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public final MimeType getExpectedMimeType() {
/* 287 */     return this.expectedMimeType;
/*     */   }
/*     */   
/*     */   public final boolean inlineBinaryData() {
/* 291 */     return this.inlineBinary;
/*     */   }
/*     */   
/*     */   public final QName getSchemaType() {
/* 295 */     return this.schemaType;
/*     */   }
/*     */   
/*     */   public final boolean isCollection() {
/* 299 */     return this.isCollection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void link() {
/* 308 */     if (this.id == ID.IDREF)
/*     */     {
/* 310 */       for (TypeInfo<T, C> ti : (Iterable<TypeInfo<T, C>>)ref()) {
/* 311 */         if (!ti.canBeReferencedByIDREF()) {
/* 312 */           this.parent.builder.reportError(new IllegalAnnotationException(Messages.INVALID_IDREF.format(new Object[] { this.parent.builder.nav.getTypeName(ti.getType()) }, ), this));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locatable getUpstream() {
/* 324 */     return this.parent;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 328 */     return this.seed.getLocation();
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
/*     */   protected final QName calcXmlName(XmlElement e) {
/* 343 */     if (e != null) {
/* 344 */       return calcXmlName(e.namespace(), e.name());
/*     */     }
/* 346 */     return calcXmlName("##default", "##default");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final QName calcXmlName(XmlElementWrapper e) {
/* 353 */     if (e != null) {
/* 354 */       return calcXmlName(e.namespace(), e.name());
/*     */     }
/* 356 */     return calcXmlName("##default", "##default");
/*     */   }
/*     */ 
/*     */   
/*     */   private QName calcXmlName(String uri, String local) {
/* 361 */     TODO.checkSpec();
/* 362 */     if (local.length() == 0 || local.equals("##default"))
/* 363 */       local = this.seed.getName(); 
/* 364 */     if (uri.equals("##default")) {
/* 365 */       XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, this.parent.getClazz(), this);
/*     */ 
/*     */       
/* 368 */       if (xs != null) {
/* 369 */         QName typeName; switch (xs.elementFormDefault()) {
/*     */           case QUALIFIED:
/* 371 */             typeName = this.parent.getTypeName();
/* 372 */             if (typeName != null) {
/* 373 */               uri = typeName.getNamespaceURI();
/*     */             } else {
/* 375 */               uri = xs.namespace();
/* 376 */             }  if (uri.length() == 0)
/* 377 */               uri = this.parent.builder.defaultNsUri; 
/*     */             break;
/*     */           case UNQUALIFIED:
/*     */           case UNSET:
/* 381 */             uri = ""; break;
/*     */         } 
/*     */       } else {
/* 384 */         uri = "";
/*     */       } 
/*     */     } 
/* 387 */     return new QName(uri.intern(), local.intern());
/*     */   }
/*     */   
/*     */   public int compareTo(PropertyInfoImpl that) {
/* 391 */     return getName().compareTo(that.getName());
/*     */   }
/*     */   
/*     */   public final <A extends Annotation> A readAnnotation(Class<A> annotationType) {
/* 395 */     return (A)this.seed.readAnnotation(annotationType);
/*     */   }
/*     */   
/*     */   public final boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 399 */     return this.seed.hasAnnotation(annotationType);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\PropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */