/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.v2.TODO;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationSource;
/*     */ import com.sun.xml.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*     */ import com.sun.xml.bind.v2.model.core.Element;
/*     */ import com.sun.xml.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.bind.v2.model.core.ElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.model.core.TypeRef;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import com.sun.xml.bind.v2.runtime.SwaRefAdapter;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.activation.MimeType;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlID;
/*     */ import javax.xml.bind.annotation.XmlIDREF;
/*     */ import javax.xml.bind.annotation.XmlInlineBinaryData;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ElementInfoImpl<T, C, F, M>
/*     */   extends TypeInfoImpl<T, C, F, M>
/*     */   implements ElementInfo<T, C>
/*     */ {
/*     */   private final QName tagName;
/*     */   private final NonElement<T, C> contentType;
/*     */   private final T tOfJAXBElementT;
/*     */   private final T elementType;
/*     */   private final ClassInfo<T, C> scope;
/*     */   private final XmlElementDecl anno;
/*     */   private ElementInfoImpl<T, C, F, M> substitutionHead;
/*     */   private FinalArrayList<ElementInfoImpl<T, C, F, M>> substitutionMembers;
/*     */   private final M method;
/*     */   private final Adapter<T, C> adapter;
/*     */   private final boolean isCollection;
/*     */   private final ID id;
/*     */   private final PropertyImpl property;
/*     */   private final MimeType expectedMimeType;
/*     */   private final boolean inlineBinary;
/*     */   private final QName schemaType;
/*     */   
/*     */   protected class PropertyImpl
/*     */     implements ElementPropertyInfo<T, C>, TypeRef<T, C>, AnnotationSource
/*     */   {
/*     */     public NonElement<T, C> getTarget() {
/* 142 */       return ElementInfoImpl.this.contentType;
/*     */     }
/*     */     public QName getTagName() {
/* 145 */       return ElementInfoImpl.this.tagName;
/*     */     }
/*     */     
/*     */     public List<? extends TypeRef<T, C>> getTypes() {
/* 149 */       return Collections.singletonList(this);
/*     */     }
/*     */     
/*     */     public List<? extends NonElement<T, C>> ref() {
/* 153 */       return Collections.singletonList(ElementInfoImpl.this.contentType);
/*     */     }
/*     */     
/*     */     public QName getXmlName() {
/* 157 */       return ElementInfoImpl.this.tagName;
/*     */     }
/*     */     
/*     */     public boolean isCollectionRequired() {
/* 161 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isCollectionNillable() {
/* 165 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isNillable() {
/* 169 */       return true;
/*     */     }
/*     */     
/*     */     public String getDefaultValue() {
/* 173 */       String v = ElementInfoImpl.this.anno.defaultValue();
/* 174 */       if (v.equals("\000")) {
/* 175 */         return null;
/*     */       }
/* 177 */       return v;
/*     */     }
/*     */     
/*     */     public ElementInfoImpl<T, C, F, M> parent() {
/* 181 */       return ElementInfoImpl.this;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 185 */       return "value";
/*     */     }
/*     */     
/*     */     public String displayName() {
/* 189 */       return "JAXBElement#value";
/*     */     }
/*     */     
/*     */     public boolean isCollection() {
/* 193 */       return ElementInfoImpl.this.isCollection;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isValueList() {
/* 200 */       return ElementInfoImpl.this.isCollection;
/*     */     }
/*     */     
/*     */     public boolean isRequired() {
/* 204 */       return true;
/*     */     }
/*     */     
/*     */     public PropertyKind kind() {
/* 208 */       return PropertyKind.ELEMENT;
/*     */     }
/*     */     
/*     */     public Adapter<T, C> getAdapter() {
/* 212 */       return ElementInfoImpl.this.adapter;
/*     */     }
/*     */     
/*     */     public ID id() {
/* 216 */       return ElementInfoImpl.this.id;
/*     */     }
/*     */     
/*     */     public MimeType getExpectedMimeType() {
/* 220 */       return ElementInfoImpl.this.expectedMimeType;
/*     */     }
/*     */     
/*     */     public QName getSchemaType() {
/* 224 */       return ElementInfoImpl.this.schemaType;
/*     */     }
/*     */     
/*     */     public boolean inlineBinaryData() {
/* 228 */       return ElementInfoImpl.this.inlineBinary;
/*     */     }
/*     */     
/*     */     public PropertyInfo<T, C> getSource() {
/* 232 */       return (PropertyInfo<T, C>)this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <A extends Annotation> A readAnnotation(Class<A> annotationType) {
/* 241 */       return (A)ElementInfoImpl.this.reader().getMethodAnnotation(annotationType, ElementInfoImpl.this.method, ElementInfoImpl.this);
/*     */     }
/*     */     
/*     */     public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
/* 245 */       return ElementInfoImpl.this.reader().hasMethodAnnotation(annotationType, ElementInfoImpl.this.method);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementInfoImpl(ModelBuilder<T, C, F, M> builder, RegistryInfoImpl<T, C, F, M> registry, M m) throws IllegalAnnotationException {
/* 255 */     super(builder, registry);
/*     */     
/* 257 */     this.method = m;
/* 258 */     this.anno = (XmlElementDecl)reader().getMethodAnnotation(XmlElementDecl.class, m, this);
/* 259 */     assert this.anno != null;
/* 260 */     assert this.anno instanceof com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */     
/* 262 */     this.elementType = (T)nav().getReturnType(m);
/* 263 */     T baseClass = (T)nav().getBaseClass(this.elementType, nav().asDecl(JAXBElement.class));
/* 264 */     if (baseClass == null) {
/* 265 */       throw new IllegalAnnotationException(Messages.XML_ELEMENT_MAPPING_ON_NON_IXMLELEMENT_METHOD.format(new Object[] { nav().getMethodName(m) }, ), this.anno);
/*     */     }
/*     */ 
/*     */     
/* 269 */     this.tagName = parseElementName(this.anno);
/* 270 */     T[] methodParams = (T[])nav().getMethodParameters(m);
/*     */ 
/*     */     
/* 273 */     Adapter<T, C> a = null;
/* 274 */     if (methodParams.length > 0) {
/* 275 */       XmlJavaTypeAdapter adapter = (XmlJavaTypeAdapter)reader().getMethodAnnotation(XmlJavaTypeAdapter.class, m, this);
/* 276 */       if (adapter != null) {
/* 277 */         a = new Adapter(adapter, reader(), nav());
/*     */       } else {
/* 279 */         XmlAttachmentRef xsa = (XmlAttachmentRef)reader().getMethodAnnotation(XmlAttachmentRef.class, m, this);
/* 280 */         if (xsa != null) {
/* 281 */           TODO.prototype("in Annotation Processing swaRefAdapter isn't avaialble, so this returns null");
/* 282 */           a = new Adapter(this.owner.nav.asDecl(SwaRefAdapter.class), this.owner.nav);
/*     */         } 
/*     */       } 
/*     */     } 
/* 286 */     this.adapter = a;
/*     */ 
/*     */     
/* 289 */     this.tOfJAXBElementT = (methodParams.length > 0) ? methodParams[0] : (T)nav().getTypeArgument(baseClass, 0);
/*     */ 
/*     */ 
/*     */     
/* 293 */     if (this.adapter == null) {
/* 294 */       T list = (T)nav().getBaseClass(this.tOfJAXBElementT, nav().asDecl(List.class));
/* 295 */       if (list == null) {
/* 296 */         this.isCollection = false;
/* 297 */         this.contentType = builder.getTypeInfo(this.tOfJAXBElementT, this);
/*     */       } else {
/* 299 */         this.isCollection = true;
/* 300 */         this.contentType = builder.getTypeInfo((T)nav().getTypeArgument(list, 0), this);
/*     */       } 
/*     */     } else {
/*     */       
/* 304 */       this.contentType = builder.getTypeInfo((T)this.adapter.defaultType, this);
/* 305 */       this.isCollection = false;
/*     */     } 
/*     */ 
/*     */     
/* 309 */     T s = (T)reader().getClassValue(this.anno, "scope");
/* 310 */     if (nav().isSameType(s, nav().ref(XmlElementDecl.GLOBAL.class))) {
/* 311 */       this.scope = null;
/*     */     } else {
/*     */       
/* 314 */       NonElement<T, C> scp = builder.getClassInfo((C)nav().asDecl(s), this);
/* 315 */       if (!(scp instanceof ClassInfo)) {
/* 316 */         throw new IllegalAnnotationException(Messages.SCOPE_IS_NOT_COMPLEXTYPE.format(new Object[] { nav().getTypeName(s) }, ), this.anno);
/*     */       }
/*     */ 
/*     */       
/* 320 */       this.scope = (ClassInfo<T, C>)scp;
/*     */     } 
/*     */     
/* 323 */     this.id = calcId();
/*     */     
/* 325 */     this.property = createPropertyImpl();
/*     */     
/* 327 */     this.expectedMimeType = Util.calcExpectedMediaType(this.property, builder);
/* 328 */     this.inlineBinary = reader().hasMethodAnnotation(XmlInlineBinaryData.class, this.method);
/* 329 */     this.schemaType = Util.calcSchemaType(reader(), this.property, registry.registryClass, getContentInMemoryType(), this);
/*     */   }
/*     */ 
/*     */   
/*     */   final QName parseElementName(XmlElementDecl e) {
/* 334 */     String local = e.name();
/* 335 */     String nsUri = e.namespace();
/* 336 */     if (nsUri.equals("##default")) {
/*     */       
/* 338 */       XmlSchema xs = (XmlSchema)reader().getPackageAnnotation(XmlSchema.class, nav().getDeclaringClassForMethod(this.method), this);
/*     */       
/* 340 */       if (xs != null) {
/* 341 */         nsUri = xs.namespace();
/*     */       } else {
/* 343 */         nsUri = this.builder.defaultNsUri;
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     return new QName(nsUri.intern(), local.intern());
/*     */   }
/*     */   
/*     */   protected PropertyImpl createPropertyImpl() {
/* 351 */     return new PropertyImpl();
/*     */   }
/*     */   
/*     */   public ElementPropertyInfo<T, C> getProperty() {
/* 355 */     return this.property;
/*     */   }
/*     */   
/*     */   public NonElement<T, C> getContentType() {
/* 359 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public T getContentInMemoryType() {
/* 363 */     if (this.adapter == null) {
/* 364 */       return this.tOfJAXBElementT;
/*     */     }
/* 366 */     return (T)this.adapter.customType;
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getElementName() {
/* 371 */     return this.tagName;
/*     */   }
/*     */   
/*     */   public T getType() {
/* 375 */     return this.elementType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean canBeReferencedByIDREF() {
/* 385 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private ID calcId() {
/* 390 */     if (reader().hasMethodAnnotation(XmlID.class, this.method)) {
/* 391 */       return ID.ID;
/*     */     }
/* 393 */     if (reader().hasMethodAnnotation(XmlIDREF.class, this.method)) {
/* 394 */       return ID.IDREF;
/*     */     }
/* 396 */     return ID.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClassInfo<T, C> getScope() {
/* 401 */     return this.scope;
/*     */   }
/*     */   
/*     */   public ElementInfo<T, C> getSubstitutionHead() {
/* 405 */     return this.substitutionHead;
/*     */   }
/*     */   
/*     */   public Collection<? extends ElementInfoImpl<T, C, F, M>> getSubstitutionMembers() {
/* 409 */     if (this.substitutionMembers == null) {
/* 410 */       return Collections.emptyList();
/*     */     }
/* 412 */     return (Collection<? extends ElementInfoImpl<T, C, F, M>>)this.substitutionMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void link() {
/* 420 */     if (this.anno.substitutionHeadName().length() != 0)
/* 421 */     { QName name = new QName(this.anno.substitutionHeadNamespace(), this.anno.substitutionHeadName());
/*     */       
/* 423 */       this.substitutionHead = this.owner.getElementInfo((C)null, name);
/* 424 */       if (this.substitutionHead == null) {
/* 425 */         this.builder.reportError(new IllegalAnnotationException(Messages.NON_EXISTENT_ELEMENT_MAPPING.format(new Object[] { name.getNamespaceURI(), name.getLocalPart() }, ), this.anno));
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 430 */         this.substitutionHead.addSubstitutionMember(this);
/*     */       }  }
/* 432 */     else { this.substitutionHead = null; }
/* 433 */      super.link();
/*     */   }
/*     */   
/*     */   private void addSubstitutionMember(ElementInfoImpl<T, C, F, M> child) {
/* 437 */     if (this.substitutionMembers == null)
/* 438 */       this.substitutionMembers = new FinalArrayList(); 
/* 439 */     this.substitutionMembers.add(child);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 443 */     return nav().getMethodLocation(this.method);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ElementInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */