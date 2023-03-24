/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.nav.ReflectionNavigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*     */ import com.sun.xml.bind.v2.runtime.FilterTransducer;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.bind.v2.runtime.InlineBinaryTransducer;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.MimeTypedTransducer;
/*     */ import com.sun.xml.bind.v2.runtime.SchemaTypeTransducer;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import javax.activation.MimeType;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class RuntimeModelBuilder
/*     */   extends ModelBuilder<Type, Class, Field, Method>
/*     */ {
/*     */   @Nullable
/*     */   public final JAXBContextImpl context;
/*     */   
/*     */   public RuntimeModelBuilder(JAXBContextImpl context, RuntimeAnnotationReader annotationReader, Map<Class<?>, Class<?>> subclassReplacements, String defaultNamespaceRemap) {
/*  93 */     super((AnnotationReader<Type, Class, Field, Method>)annotationReader, (Navigator<Type, Class, Field, Method>)Navigator.REFLECTION, subclassReplacements, defaultNamespaceRemap);
/*  94 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeNonElement getClassInfo(Class clazz, Locatable upstream) {
/*  99 */     return (RuntimeNonElement)super.getClassInfo(clazz, upstream);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeNonElement getClassInfo(Class clazz, boolean searchForSuperClass, Locatable upstream) {
/* 104 */     return (RuntimeNonElement)super.getClassInfo(clazz, searchForSuperClass, upstream);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeEnumLeafInfoImpl createEnumLeafInfo(Class<Enum> clazz, Locatable upstream) {
/* 109 */     return new RuntimeEnumLeafInfoImpl<Enum, Object>(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeClassInfoImpl createClassInfo(Class clazz, Locatable upstream) {
/* 114 */     return new RuntimeClassInfoImpl(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeElementInfoImpl createElementInfo(RegistryInfoImpl<Type, Class<?>, Field, Method> registryInfo, Method method) throws IllegalAnnotationException {
/* 119 */     return new RuntimeElementInfoImpl(this, registryInfo, method);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeArrayInfoImpl createArrayInfo(Locatable upstream, Type arrayType) {
/* 124 */     return new RuntimeArrayInfoImpl(this, upstream, (Class)arrayType);
/*     */   }
/*     */   
/*     */   public ReflectionNavigator getNavigator() {
/* 128 */     return (ReflectionNavigator)this.nav;
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeTypeInfoSetImpl createTypeInfoSet() {
/* 133 */     return new RuntimeTypeInfoSetImpl(this.reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeTypeInfoSet link() {
/* 138 */     return (RuntimeTypeInfoSet)super.link();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transducer createTransducer(RuntimeNonElementRef ref) {
/*     */     IDTransducerImpl iDTransducerImpl;
/*     */     MimeTypedTransducer mimeTypedTransducer;
/*     */     InlineBinaryTransducer inlineBinaryTransducer;
/*     */     SchemaTypeTransducer schemaTypeTransducer;
/* 150 */     Transducer<?> t = ref.getTarget().getTransducer();
/* 151 */     RuntimePropertyInfo src = ref.getSource();
/* 152 */     ID id = src.id();
/*     */     
/* 154 */     if (id == ID.IDREF) {
/* 155 */       return RuntimeBuiltinLeafInfoImpl.STRING;
/*     */     }
/* 157 */     if (id == ID.ID) {
/* 158 */       iDTransducerImpl = new IDTransducerImpl(t);
/*     */     }
/* 160 */     MimeType emt = src.getExpectedMimeType();
/* 161 */     if (emt != null) {
/* 162 */       mimeTypedTransducer = new MimeTypedTransducer((Transducer)iDTransducerImpl, emt);
/*     */     }
/* 164 */     if (src.inlineBinaryData()) {
/* 165 */       inlineBinaryTransducer = new InlineBinaryTransducer((Transducer)mimeTypedTransducer);
/*     */     }
/* 167 */     if (src.getSchemaType() != null) {
/* 168 */       if (src.getSchemaType().equals(createXSSimpleType())) {
/* 169 */         return RuntimeBuiltinLeafInfoImpl.STRING;
/*     */       }
/* 171 */       schemaTypeTransducer = new SchemaTypeTransducer((Transducer)inlineBinaryTransducer, src.getSchemaType());
/*     */     } 
/*     */     
/* 174 */     return (Transducer)schemaTypeTransducer;
/*     */   }
/*     */   
/*     */   private static QName createXSSimpleType() {
/* 178 */     return new QName("http://www.w3.org/2001/XMLSchema", "anySimpleType");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class IDTransducerImpl<ValueT>
/*     */     extends FilterTransducer<ValueT>
/*     */   {
/*     */     public IDTransducerImpl(Transducer<ValueT> core) {
/* 189 */       super(core);
/*     */     }
/*     */ 
/*     */     
/*     */     public ValueT parse(CharSequence lexical) throws AccessorException, SAXException {
/* 194 */       String value = WhiteSpaceProcessor.trim(lexical).toString();
/* 195 */       UnmarshallingContext.getInstance().addToIdTable(value);
/* 196 */       return (ValueT)this.core.parse(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeModelBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */