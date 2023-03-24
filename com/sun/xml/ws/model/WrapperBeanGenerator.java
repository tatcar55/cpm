/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.ws.org.objectweb.asm.AnnotationVisitor;
/*     */ import com.sun.xml.ws.org.objectweb.asm.ClassWriter;
/*     */ import com.sun.xml.ws.org.objectweb.asm.FieldVisitor;
/*     */ import com.sun.xml.ws.org.objectweb.asm.MethodVisitor;
/*     */ import com.sun.xml.ws.org.objectweb.asm.Type;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Holder;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperBeanGenerator
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(WrapperBeanGenerator.class.getName());
/*     */   
/*  75 */   private static final FieldFactory FIELD_FACTORY = new FieldFactory();
/*     */   
/*  77 */   private static final AbstractWrapperBeanGenerator RUNTIME_GENERATOR = new RuntimeWrapperBeanGenerator((AnnotationReader<Type, Class, ?, Method>)new RuntimeInlineAnnotationReader(), (Navigator<Type, Class, ?, Method>)Navigator.REFLECTION, FIELD_FACTORY);
/*     */ 
/*     */   
/*     */   private static final class RuntimeWrapperBeanGenerator
/*     */     extends AbstractWrapperBeanGenerator<Type, Class, Method, Field>
/*     */   {
/*     */     protected RuntimeWrapperBeanGenerator(AnnotationReader<Type, Class<?>, ?, Method> annReader, Navigator<Type, Class<?>, ?, Method> nav, AbstractWrapperBeanGenerator.BeanMemberFactory<Type, WrapperBeanGenerator.Field> beanMemberFactory) {
/*  84 */       super(annReader, nav, beanMemberFactory);
/*     */     }
/*     */     
/*     */     protected Type getSafeType(Type type) {
/*  88 */       return type;
/*     */     }
/*     */     
/*     */     protected Type getHolderValueType(Type paramType) {
/*  92 */       if (paramType instanceof ParameterizedType) {
/*  93 */         ParameterizedType p = (ParameterizedType)paramType;
/*  94 */         if (p.getRawType().equals(Holder.class)) {
/*  95 */           return p.getActualTypeArguments()[0];
/*     */         }
/*     */       } 
/*  98 */       return null;
/*     */     }
/*     */     
/*     */     protected boolean isVoidType(Type type) {
/* 102 */       return (type == void.class);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class FieldFactory implements AbstractWrapperBeanGenerator.BeanMemberFactory<Type, Field> {
/*     */     private FieldFactory() {}
/*     */     
/*     */     public WrapperBeanGenerator.Field createWrapperBeanMember(Type paramType, String paramName, List<Annotation> jaxb) {
/* 110 */       return new WrapperBeanGenerator.Field(paramName, paramType, WrapperBeanGenerator.getASMType(paramType), jaxb);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] createBeanImage(String className, String rootName, String rootNS, String typeName, String typeNS, Collection<Field> fields) throws Exception {
/* 120 */     ClassWriter cw = new ClassWriter(0);
/*     */ 
/*     */     
/* 123 */     cw.visit(49, 33, replaceDotWithSlash(className), null, "java/lang/Object", null);
/*     */     
/* 125 */     AnnotationVisitor root = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlRootElement;", true);
/* 126 */     root.visit("name", rootName);
/* 127 */     root.visit("namespace", rootNS);
/* 128 */     root.visitEnd();
/*     */     
/* 130 */     AnnotationVisitor type = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlType;", true);
/* 131 */     type.visit("name", typeName);
/* 132 */     type.visit("namespace", typeNS);
/* 133 */     if (fields.size() > 1) {
/* 134 */       AnnotationVisitor propVisitor = type.visitArray("propOrder");
/* 135 */       for (Field field : fields) {
/* 136 */         propVisitor.visit("propOrder", field.fieldName);
/*     */       }
/* 138 */       propVisitor.visitEnd();
/*     */     } 
/* 140 */     type.visitEnd();
/*     */     
/* 142 */     for (Field field : fields) {
/* 143 */       FieldVisitor fv = cw.visitField(1, field.fieldName, field.asmType.getDescriptor(), field.getSignature(), null);
/*     */       
/* 145 */       for (Annotation ann : field.jaxbAnnotations) {
/* 146 */         if (ann instanceof XmlMimeType) {
/* 147 */           AnnotationVisitor mime = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlMimeType;", true);
/* 148 */           mime.visit("value", ((XmlMimeType)ann).value());
/* 149 */           mime.visitEnd(); continue;
/* 150 */         }  if (ann instanceof XmlJavaTypeAdapter) {
/* 151 */           AnnotationVisitor ada = fv.visitAnnotation("Ljavax/xml/bind/annotation/adapters/XmlJavaTypeAdapter;", true);
/* 152 */           ada.visit("value", getASMType(((XmlJavaTypeAdapter)ann).value()));
/*     */ 
/*     */           
/* 155 */           ada.visitEnd(); continue;
/* 156 */         }  if (ann instanceof javax.xml.bind.annotation.XmlAttachmentRef) {
/* 157 */           AnnotationVisitor att = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlAttachmentRef;", true);
/* 158 */           att.visitEnd(); continue;
/* 159 */         }  if (ann instanceof javax.xml.bind.annotation.XmlList) {
/* 160 */           AnnotationVisitor list = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlList;", true);
/* 161 */           list.visitEnd(); continue;
/* 162 */         }  if (ann instanceof XmlElement) {
/* 163 */           AnnotationVisitor elem = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlElement;", true);
/* 164 */           XmlElement xmlElem = (XmlElement)ann;
/* 165 */           elem.visit("name", xmlElem.name());
/* 166 */           elem.visit("namespace", xmlElem.namespace());
/* 167 */           if (xmlElem.nillable()) {
/* 168 */             elem.visit("nillable", Boolean.valueOf(true));
/*     */           }
/* 170 */           if (xmlElem.required()) {
/* 171 */             elem.visit("required", Boolean.valueOf(true));
/*     */           }
/* 173 */           elem.visitEnd(); continue;
/*     */         } 
/* 175 */         throw new WebServiceException("Unknown JAXB annotation " + ann);
/*     */       } 
/*     */ 
/*     */       
/* 179 */       fv.visitEnd();
/*     */     } 
/*     */     
/* 182 */     MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
/* 183 */     mv.visitCode();
/* 184 */     mv.visitVarInsn(25, 0);
/* 185 */     mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
/* 186 */     mv.visitInsn(177);
/* 187 */     mv.visitMaxs(1, 1);
/* 188 */     mv.visitEnd();
/*     */     
/* 190 */     cw.visitEnd();
/*     */     
/* 192 */     if (LOGGER.isLoggable(Level.FINE)) {
/*     */       
/* 194 */       StringBuilder sb = new StringBuilder();
/* 195 */       sb.append("\n");
/* 196 */       sb.append("@XmlRootElement(name=").append(rootName).append(", namespace=").append(rootNS).append(")");
/*     */ 
/*     */ 
/*     */       
/* 200 */       sb.append("\n");
/* 201 */       sb.append("@XmlType(name=").append(typeName).append(", namespace=").append(typeNS);
/*     */       
/* 203 */       if (fields.size() > 1) {
/* 204 */         sb.append(", propOrder={");
/* 205 */         for (Field field : fields) {
/* 206 */           sb.append(" ");
/* 207 */           sb.append(field.fieldName);
/*     */         } 
/* 209 */         sb.append(" }");
/*     */       } 
/* 211 */       sb.append(")");
/*     */ 
/*     */       
/* 214 */       sb.append("\n");
/* 215 */       sb.append("public class ").append(className).append(" {");
/*     */ 
/*     */       
/* 218 */       for (Field field : fields) {
/* 219 */         sb.append("\n");
/*     */ 
/*     */         
/* 222 */         for (Annotation ann : field.jaxbAnnotations) {
/* 223 */           sb.append("\n    ");
/*     */           
/* 225 */           if (ann instanceof XmlMimeType) {
/* 226 */             sb.append("@XmlMimeType(value=").append(((XmlMimeType)ann).value()).append(")"); continue;
/* 227 */           }  if (ann instanceof XmlJavaTypeAdapter) {
/* 228 */             sb.append("@XmlJavaTypeAdapter(value=").append(getASMType(((XmlJavaTypeAdapter)ann).value())).append(")"); continue;
/* 229 */           }  if (ann instanceof javax.xml.bind.annotation.XmlAttachmentRef) {
/* 230 */             sb.append("@XmlAttachmentRef"); continue;
/* 231 */           }  if (ann instanceof javax.xml.bind.annotation.XmlList) {
/* 232 */             sb.append("@XmlList"); continue;
/* 233 */           }  if (ann instanceof XmlElement) {
/* 234 */             XmlElement xmlElem = (XmlElement)ann;
/* 235 */             sb.append("\n    ");
/* 236 */             sb.append("@XmlElement(name=").append(xmlElem.name()).append(", namespace=").append(xmlElem.namespace());
/*     */             
/* 238 */             if (xmlElem.nillable()) {
/* 239 */               sb.append(", nillable=true");
/*     */             }
/* 241 */             if (xmlElem.required()) {
/* 242 */               sb.append(", required=true");
/*     */             }
/* 244 */             sb.append(")"); continue;
/*     */           } 
/* 246 */           throw new WebServiceException("Unknown JAXB annotation " + ann);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 251 */         sb.append("\n    ");
/* 252 */         sb.append("public ");
/* 253 */         if (field.getSignature() == null) {
/* 254 */           sb.append(field.asmType.getDescriptor());
/*     */         } else {
/* 256 */           sb.append(field.getSignature());
/*     */         } 
/* 258 */         sb.append(" ");
/* 259 */         sb.append(field.fieldName);
/*     */       } 
/*     */       
/* 262 */       sb.append("\n\n}");
/* 263 */       LOGGER.fine(sb.toString());
/*     */     } 
/*     */     
/* 266 */     return cw.toByteArray();
/*     */   }
/*     */   
/*     */   private static String replaceDotWithSlash(String name) {
/* 270 */     return name.replace('.', '/');
/*     */   }
/*     */   
/*     */   static Class createRequestWrapperBean(String className, Method method, QName reqElemName, ClassLoader cl) {
/*     */     byte[] image;
/* 275 */     LOGGER.log(Level.FINE, "Request Wrapper Class : {0}", className);
/*     */     
/* 277 */     List<Field> requestMembers = RUNTIME_GENERATOR.collectRequestBeanMembers(method);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 282 */       image = createBeanImage(className, reqElemName.getLocalPart(), reqElemName.getNamespaceURI(), reqElemName.getLocalPart(), reqElemName.getNamespaceURI(), requestMembers);
/*     */     
/*     */     }
/* 285 */     catch (Exception e) {
/* 286 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 289 */     return Injector.inject(cl, className, image);
/*     */   }
/*     */   
/*     */   static Class createResponseWrapperBean(String className, Method method, QName resElemName, ClassLoader cl) {
/*     */     byte[] image;
/* 294 */     LOGGER.log(Level.FINE, "Response Wrapper Class : {0}", className);
/*     */     
/* 296 */     List<Field> responseMembers = RUNTIME_GENERATOR.collectResponseBeanMembers(method);
/*     */ 
/*     */     
/*     */     try {
/* 300 */       image = createBeanImage(className, resElemName.getLocalPart(), resElemName.getNamespaceURI(), resElemName.getLocalPart(), resElemName.getNamespaceURI(), responseMembers);
/*     */     
/*     */     }
/* 303 */     catch (Exception e) {
/* 304 */       throw new WebServiceException(e);
/*     */     } 
/*     */ 
/*     */     
/* 308 */     return Injector.inject(cl, className, image);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Type getASMType(Type t) {
/* 313 */     assert t != null;
/*     */     
/* 315 */     if (t instanceof Class) {
/* 316 */       return Type.getType((Class)t);
/*     */     }
/*     */     
/* 319 */     if (t instanceof ParameterizedType) {
/* 320 */       ParameterizedType pt = (ParameterizedType)t;
/* 321 */       if (pt.getRawType() instanceof Class) {
/* 322 */         return Type.getType((Class)pt.getRawType());
/*     */       }
/*     */     } 
/* 325 */     if (t instanceof java.lang.reflect.GenericArrayType) {
/* 326 */       return Type.getType(FieldSignature.vms(t));
/*     */     }
/*     */     
/* 329 */     if (t instanceof java.lang.reflect.WildcardType) {
/* 330 */       return Type.getType(FieldSignature.vms(t));
/*     */     }
/*     */     
/* 333 */     if (t instanceof TypeVariable) {
/* 334 */       TypeVariable tv = (TypeVariable)t;
/* 335 */       if (tv.getBounds()[0] instanceof Class) {
/* 336 */         return Type.getType((Class)tv.getBounds()[0]);
/*     */       }
/*     */     } 
/*     */     
/* 340 */     throw new IllegalArgumentException("Not creating ASM Type for type = " + t);
/*     */   }
/*     */ 
/*     */   
/*     */   static Class createExceptionBean(String className, Class exception, String typeNS, String elemName, String elemNS, ClassLoader cl) {
/* 345 */     return createExceptionBean(className, exception, typeNS, elemName, elemNS, cl, true);
/*     */   }
/*     */   
/*     */   static Class createExceptionBean(String className, Class exception, String typeNS, String elemName, String elemNS, ClassLoader cl, boolean decapitalizeExceptionBeanProperties) {
/*     */     byte[] image;
/* 350 */     Collection<Field> fields = RUNTIME_GENERATOR.collectExceptionBeanMembers(exception, decapitalizeExceptionBeanProperties);
/*     */ 
/*     */     
/*     */     try {
/* 354 */       image = createBeanImage(className, elemName, elemNS, exception.getSimpleName(), typeNS, fields);
/*     */     
/*     */     }
/* 357 */     catch (Exception e) {
/* 358 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 361 */     return Injector.inject(cl, className, image);
/*     */   }
/*     */   
/*     */   private static class Field
/*     */     implements Comparable<Field> {
/*     */     private final Type reflectType;
/*     */     private final Type asmType;
/*     */     private final String fieldName;
/*     */     private final List<Annotation> jaxbAnnotations;
/*     */     
/*     */     Field(String paramName, Type paramType, Type asmType, List<Annotation> jaxbAnnotations) {
/* 372 */       this.reflectType = paramType;
/* 373 */       this.asmType = asmType;
/* 374 */       this.fieldName = paramName;
/* 375 */       this.jaxbAnnotations = jaxbAnnotations;
/*     */     }
/*     */     
/*     */     String getSignature() {
/* 379 */       if (this.reflectType instanceof Class) {
/* 380 */         return null;
/*     */       }
/* 382 */       if (this.reflectType instanceof TypeVariable) {
/* 383 */         return null;
/*     */       }
/* 385 */       return FieldSignature.vms(this.reflectType);
/*     */     }
/*     */     
/*     */     public int compareTo(Field o) {
/* 389 */       return this.fieldName.compareTo(o.fieldName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void write(byte[] b, String className) {
/* 395 */     className = className.substring(className.lastIndexOf(".") + 1);
/*     */     try {
/* 397 */       FileOutputStream fo = new FileOutputStream(className + ".class");
/* 398 */       fo.write(b);
/* 399 */       fo.flush();
/* 400 */       fo.close();
/* 401 */     } catch (IOException e) {
/*     */       
/* 403 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\WrapperBeanGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */