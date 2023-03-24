/*     */ package com.sun.xml.bind.v2;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.util.TypeCast;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextFactory
/*     */ {
/*     */   public static final String USE_JAXB_PROPERTIES = "_useJAXBProperties";
/*     */   
/*     */   public static JAXBContext createContext(Class[] classes, Map<String, Object> properties) throws JAXBException {
/*     */     Map<Class<?>, Class<?>> subclassReplacements;
/*  84 */     if (properties == null) {
/*  85 */       properties = Collections.emptyMap();
/*     */     } else {
/*  87 */       properties = new HashMap<String, Object>(properties);
/*     */     } 
/*  89 */     String defaultNsUri = getPropertyValue(properties, "com.sun.xml.bind.defaultNamespaceRemap", String.class);
/*     */     
/*  91 */     Boolean c14nSupport = getPropertyValue(properties, "com.sun.xml.bind.c14n", Boolean.class);
/*  92 */     if (c14nSupport == null) {
/*  93 */       c14nSupport = Boolean.valueOf(false);
/*     */     }
/*  95 */     Boolean disablesecurityProcessing = getPropertyValue(properties, "com.sun.xml.bind.disableXmlSecurity", Boolean.class);
/*  96 */     if (disablesecurityProcessing == null) {
/*  97 */       disablesecurityProcessing = Boolean.valueOf(false);
/*     */     }
/*  99 */     Boolean allNillable = getPropertyValue(properties, "com.sun.xml.bind.treatEverythingNillable", Boolean.class);
/* 100 */     if (allNillable == null) {
/* 101 */       allNillable = Boolean.valueOf(false);
/*     */     }
/* 103 */     Boolean retainPropertyInfo = getPropertyValue(properties, "retainReferenceToInfo", Boolean.class);
/* 104 */     if (retainPropertyInfo == null) {
/* 105 */       retainPropertyInfo = Boolean.valueOf(false);
/*     */     }
/* 107 */     Boolean supressAccessorWarnings = getPropertyValue(properties, "supressAccessorWarnings", Boolean.class);
/* 108 */     if (supressAccessorWarnings == null) {
/* 109 */       supressAccessorWarnings = Boolean.valueOf(false);
/*     */     }
/* 111 */     Boolean improvedXsiTypeHandling = getPropertyValue(properties, "com.sun.xml.bind.improvedXsiTypeHandling", Boolean.class);
/* 112 */     if (improvedXsiTypeHandling == null) {
/* 113 */       String improvedXsiSystemProperty = Util.getSystemProperty("com.sun.xml.bind.improvedXsiTypeHandling");
/* 114 */       if (improvedXsiSystemProperty == null) {
/* 115 */         improvedXsiTypeHandling = Boolean.valueOf(true);
/*     */       } else {
/* 117 */         improvedXsiTypeHandling = Boolean.valueOf(improvedXsiSystemProperty);
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     Boolean xmlAccessorFactorySupport = getPropertyValue(properties, "com.sun.xml.bind.XmlAccessorFactory", Boolean.class);
/*     */     
/* 123 */     if (xmlAccessorFactorySupport == null) {
/* 124 */       xmlAccessorFactorySupport = Boolean.valueOf(false);
/* 125 */       Util.getClassLogger().log(Level.FINE, "Property com.sun.xml.bind.XmlAccessorFactoryis not active.  Using JAXB's implementation");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     RuntimeAnnotationReader ar = getPropertyValue(properties, JAXBRIContext.ANNOTATION_READER, RuntimeAnnotationReader.class);
/*     */     
/* 132 */     Collection<TypeReference> tr = getPropertyValue(properties, "com.sun.xml.bind.typeReferences", (Class)Collection.class);
/* 133 */     if (tr == null) {
/* 134 */       tr = Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 139 */       subclassReplacements = TypeCast.checkedCast(getPropertyValue(properties, "com.sun.xml.bind.subclassReplacements", Map.class), Class.class, Class.class);
/*     */     }
/* 141 */     catch (ClassCastException e) {
/* 142 */       throw new JAXBException(Messages.INVALID_TYPE_IN_MAP.format(new Object[0]), e);
/*     */     } 
/*     */     
/* 145 */     if (!properties.isEmpty()) {
/* 146 */       throw new JAXBException(Messages.UNSUPPORTED_PROPERTY.format(new Object[] { properties.keySet().iterator().next() }));
/*     */     }
/*     */     
/* 149 */     JAXBContextImpl.JAXBContextBuilder builder = new JAXBContextImpl.JAXBContextBuilder();
/* 150 */     builder.setClasses(classes);
/* 151 */     builder.setTypeRefs(tr);
/* 152 */     builder.setSubclassReplacements(subclassReplacements);
/* 153 */     builder.setDefaultNsUri(defaultNsUri);
/* 154 */     builder.setC14NSupport(c14nSupport.booleanValue());
/* 155 */     builder.setAnnotationReader(ar);
/* 156 */     builder.setXmlAccessorFactorySupport(xmlAccessorFactorySupport.booleanValue());
/* 157 */     builder.setAllNillable(allNillable.booleanValue());
/* 158 */     builder.setRetainPropertyInfo(retainPropertyInfo.booleanValue());
/* 159 */     builder.setSupressAccessorWarnings(supressAccessorWarnings.booleanValue());
/* 160 */     builder.setImprovedXsiTypeHandling(improvedXsiTypeHandling.booleanValue());
/* 161 */     builder.setDisableSecurityProcessing(disablesecurityProcessing.booleanValue());
/* 162 */     return (JAXBContext)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> T getPropertyValue(Map<String, Object> properties, String keyName, Class<T> type) throws JAXBException {
/* 169 */     Object o = properties.get(keyName);
/* 170 */     if (o == null) return null;
/*     */     
/* 172 */     properties.remove(keyName);
/* 173 */     if (!type.isInstance(o)) {
/* 174 */       throw new JAXBException(Messages.INVALID_PROPERTY_VALUE.format(new Object[] { keyName, o }));
/*     */     }
/* 176 */     return type.cast(o);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JAXBRIContext createContext(Class[] classes, Collection<TypeReference> typeRefs, Map<Class<?>, Class<?>> subclassReplacements, String defaultNsUri, boolean c14nSupport, RuntimeAnnotationReader ar, boolean xmlAccessorFactorySupport, boolean allNillable, boolean retainPropertyInfo) throws JAXBException {
/* 200 */     return createContext(classes, typeRefs, subclassReplacements, defaultNsUri, c14nSupport, ar, xmlAccessorFactorySupport, allNillable, retainPropertyInfo, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static JAXBRIContext createContext(Class[] classes, Collection<TypeReference> typeRefs, Map<Class<?>, Class<?>> subclassReplacements, String defaultNsUri, boolean c14nSupport, RuntimeAnnotationReader ar, boolean xmlAccessorFactorySupport, boolean allNillable, boolean retainPropertyInfo, boolean improvedXsiTypeHandling) throws JAXBException {
/* 227 */     JAXBContextImpl.JAXBContextBuilder builder = new JAXBContextImpl.JAXBContextBuilder();
/* 228 */     builder.setClasses(classes);
/* 229 */     builder.setTypeRefs(typeRefs);
/* 230 */     builder.setSubclassReplacements(subclassReplacements);
/* 231 */     builder.setDefaultNsUri(defaultNsUri);
/* 232 */     builder.setC14NSupport(c14nSupport);
/* 233 */     builder.setAnnotationReader(ar);
/* 234 */     builder.setXmlAccessorFactorySupport(xmlAccessorFactorySupport);
/* 235 */     builder.setAllNillable(allNillable);
/* 236 */     builder.setRetainPropertyInfo(retainPropertyInfo);
/* 237 */     builder.setImprovedXsiTypeHandling(improvedXsiTypeHandling);
/* 238 */     return (JAXBRIContext)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JAXBContext createContext(String contextPath, ClassLoader classLoader, Map<String, Object> properties) throws JAXBException {
/* 246 */     FinalArrayList<Class<?>> classes = new FinalArrayList();
/* 247 */     StringTokenizer tokens = new StringTokenizer(contextPath, ":");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     while (tokens.hasMoreTokens()) {
/* 255 */       List<Class<?>> indexedClasses; boolean foundJaxbIndex = false, foundObjectFactory = foundJaxbIndex;
/* 256 */       String pkg = tokens.nextToken();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 261 */         Class<?> o = classLoader.loadClass(pkg + ".ObjectFactory");
/* 262 */         classes.add(o);
/* 263 */         foundObjectFactory = true;
/* 264 */       } catch (ClassNotFoundException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 270 */         indexedClasses = loadIndexedClasses(pkg, classLoader);
/* 271 */       } catch (IOException e) {
/*     */         
/* 273 */         throw new JAXBException(e);
/*     */       } 
/* 275 */       if (indexedClasses != null) {
/* 276 */         classes.addAll(indexedClasses);
/* 277 */         foundJaxbIndex = true;
/*     */       } 
/*     */       
/* 280 */       if (!foundObjectFactory && !foundJaxbIndex) {
/* 281 */         throw new JAXBException(Messages.BROKEN_CONTEXTPATH.format(new Object[] { pkg }));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 286 */     return createContext((Class[])classes.toArray((Object[])new Class[classes.size()]), properties);
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
/*     */   private static List<Class> loadIndexedClasses(String pkg, ClassLoader classLoader) throws IOException, JAXBException {
/* 299 */     String resource = pkg.replace('.', '/') + "/jaxb.index";
/* 300 */     InputStream resourceAsStream = classLoader.getResourceAsStream(resource);
/*     */     
/* 302 */     if (resourceAsStream == null) {
/* 303 */       return null;
/*     */     }
/*     */     
/* 306 */     BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
/*     */     
/*     */     try {
/* 309 */       FinalArrayList<Class<?>> classes = new FinalArrayList();
/* 310 */       String className = in.readLine();
/* 311 */       while (className != null) {
/* 312 */         className = className.trim();
/* 313 */         if (className.startsWith("#") || className.length() == 0) {
/* 314 */           className = in.readLine();
/*     */           
/*     */           continue;
/*     */         } 
/* 318 */         if (className.endsWith(".class")) {
/* 319 */           throw new JAXBException(Messages.ILLEGAL_ENTRY.format(new Object[] { className }));
/*     */         }
/*     */         
/*     */         try {
/* 323 */           classes.add(classLoader.loadClass(pkg + '.' + className));
/* 324 */         } catch (ClassNotFoundException e) {
/* 325 */           throw new JAXBException(Messages.ERROR_LOADING_CLASS.format(new Object[] { className, resource }, ), e);
/*     */         } 
/*     */         
/* 328 */         className = in.readLine();
/*     */       } 
/* 330 */       return (List)classes;
/*     */     } finally {
/* 332 */       in.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\ContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */