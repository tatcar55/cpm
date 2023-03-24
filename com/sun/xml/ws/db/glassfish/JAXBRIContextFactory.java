/*     */ package com.sun.xml.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.bind.api.CompositeStructure;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.v2.ContextFactory;
/*     */ import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;
/*     */ import com.sun.xml.bind.v2.runtime.MarshallerImpl;
/*     */ import com.sun.xml.ws.developer.JAXBContextFactory;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.ws.spi.db.BindingInfo;
/*     */ import com.sun.xml.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBRIContextFactory
/*     */   extends BindingContextFactory
/*     */ {
/*     */   public BindingContext newContext(JAXBContext context) {
/*  74 */     return new JAXBRIContextWrapper((JAXBRIContext)context, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext newContext(BindingInfo bi) {
/*  79 */     Class[] classes = (Class[])bi.contentClasses().toArray((Object[])new Class[bi.contentClasses().size()]);
/*  80 */     for (int i = 0; i < classes.length; i++) {
/*  81 */       if (WrapperComposite.class.equals(classes[i])) {
/*  82 */         classes[i] = CompositeStructure.class;
/*     */       }
/*     */     } 
/*  85 */     Map<TypeInfo, TypeReference> typeInfoMappings = typeInfoMappings(bi.typeInfos());
/*  86 */     Map<Class<?>, Class<?>> subclassReplacements = bi.subclassReplacements();
/*  87 */     String defaultNamespaceRemap = bi.getDefaultNamespace();
/*  88 */     Boolean c14nSupport = (Boolean)bi.properties().get("c14nSupport");
/*  89 */     RuntimeAnnotationReader ar = (RuntimeAnnotationReader)bi.properties().get("com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader");
/*  90 */     JAXBContextFactory jaxbContextFactory = (JAXBContextFactory)bi.properties().get(JAXBContextFactory.class.getName());
/*     */     try {
/*  92 */       JAXBRIContext context = (jaxbContextFactory != null) ? jaxbContextFactory.createJAXBContext(bi.getSEIModel(), toList((Class<?>[][])classes), toList(typeInfoMappings.values())) : ContextFactory.createContext(classes, typeInfoMappings.values(), subclassReplacements, defaultNamespaceRemap, (c14nSupport != null) ? c14nSupport.booleanValue() : false, ar, false, false, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       return new JAXBRIContextWrapper(context, typeInfoMappings);
/* 103 */     } catch (Exception e) {
/* 104 */       throw new DatabindingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> List<T> toList(T[] a) {
/* 109 */     List<T> l = new ArrayList<T>();
/* 110 */     l.addAll(Arrays.asList(a));
/* 111 */     return l;
/*     */   }
/*     */   
/*     */   private <T> List<T> toList(Collection<T> col) {
/* 115 */     if (col instanceof List) {
/* 116 */       return (List<T>)col;
/*     */     }
/* 118 */     List<T> l = new ArrayList<T>();
/* 119 */     l.addAll(col);
/* 120 */     return l;
/*     */   }
/*     */   
/*     */   private Map<TypeInfo, TypeReference> typeInfoMappings(Collection<TypeInfo> typeInfos) {
/* 124 */     Map<TypeInfo, TypeReference> map = new HashMap<TypeInfo, TypeReference>();
/* 125 */     for (TypeInfo ti : typeInfos) {
/* 126 */       Type type = WrapperComposite.class.equals(ti.type) ? CompositeStructure.class : ti.type;
/* 127 */       TypeReference tr = new TypeReference(ti.tagName, type, ti.annotations);
/* 128 */       map.put(ti, tr);
/*     */     } 
/* 130 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BindingContext getContext(Marshaller m) {
/* 135 */     return newContext((JAXBContext)((MarshallerImpl)m).getContext());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFor(String str) {
/* 140 */     return (str.equals("glassfish.jaxb") || str.equals(getClass().getName()) || str.equals("com.sun.xml.bind.v2.runtime"));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\glassfish\JAXBRIContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */