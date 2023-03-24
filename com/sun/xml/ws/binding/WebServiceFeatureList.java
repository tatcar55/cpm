/*     */ package com.sun.xml.ws.binding;
/*     */ 
/*     */ import com.oracle.webservices.api.EnvelopeStyleFeature;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.util.Which;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.ws.api.FeatureListValidator;
/*     */ import com.sun.xml.ws.api.FeatureListValidatorAnnotation;
/*     */ import com.sun.xml.ws.api.ImpliesWebServiceFeature;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFeaturedObject;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.model.RuntimeModelerException;
/*     */ import com.sun.xml.ws.resources.ModelerMessages;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.RespectBinding;
/*     */ import javax.xml.ws.RespectBindingFeature;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.Addressing;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import javax.xml.ws.soap.MTOM;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import javax.xml.ws.spi.WebServiceFeatureAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WebServiceFeatureList
/*     */   extends AbstractMap<Class<? extends WebServiceFeature>, WebServiceFeature>
/*     */   implements WSFeatureList
/*     */ {
/*     */   public static WebServiceFeatureList toList(Iterable<WebServiceFeature> features) {
/*  87 */     if (features instanceof WebServiceFeatureList)
/*  88 */       return (WebServiceFeatureList)features; 
/*  89 */     WebServiceFeatureList w = new WebServiceFeatureList();
/*  90 */     if (features != null)
/*  91 */       w.addAll(features); 
/*  92 */     return w;
/*     */   }
/*     */   
/*  95 */   private Map<Class<? extends WebServiceFeature>, WebServiceFeature> wsfeatures = new HashMap<Class<? extends WebServiceFeature>, WebServiceFeature>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidating = false;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WSDLFeaturedObject parent;
/*     */ 
/*     */ 
/*     */   
/*     */   public WebServiceFeatureList(@NotNull WebServiceFeature... features) {
/* 108 */     if (features != null) {
/* 109 */       for (WebServiceFeature f : features) {
/* 110 */         addNoValidate(f);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void validate() {
/* 116 */     if (!this.isValidating) {
/* 117 */       this.isValidating = true;
/*     */ 
/*     */       
/* 120 */       for (WebServiceFeature ff : this) {
/* 121 */         validate(ff);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validate(WebServiceFeature feature) {
/* 128 */     FeatureListValidatorAnnotation fva = feature.getClass().<FeatureListValidatorAnnotation>getAnnotation(FeatureListValidatorAnnotation.class);
/* 129 */     if (fva != null) {
/* 130 */       Class<? extends FeatureListValidator> beanClass = fva.bean();
/*     */       try {
/* 132 */         FeatureListValidator validator = beanClass.newInstance();
/* 133 */         validator.validate(this);
/* 134 */       } catch (InstantiationException e) {
/* 135 */         throw new WebServiceException(e);
/* 136 */       } catch (IllegalAccessException e) {
/* 137 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public WebServiceFeatureList(WebServiceFeatureList features) {
/* 143 */     if (features != null) {
/* 144 */       this.wsfeatures.putAll(features.wsfeatures);
/* 145 */       this.parent = features.parent;
/* 146 */       this.isValidating = features.isValidating;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebServiceFeatureList(@NotNull Class<?> endpointClass) {
/* 154 */     parseAnnotations(endpointClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseAnnotations(Iterable<Annotation> annIt) {
/* 164 */     for (Annotation ann : annIt) {
/* 165 */       WebServiceFeature feature = getFeature(ann);
/* 166 */       if (feature != null) {
/* 167 */         add(feature);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WebServiceFeature getFeature(Annotation a) {
/* 180 */     WebServiceFeature ftr = null;
/* 181 */     if (!a.annotationType().isAnnotationPresent((Class)WebServiceFeatureAnnotation.class)) {
/* 182 */       ftr = null;
/* 183 */     } else if (a instanceof Addressing) {
/* 184 */       Addressing addAnn = (Addressing)a;
/*     */       try {
/* 186 */         ftr = new AddressingFeature(addAnn.enabled(), addAnn.required(), addAnn.responses());
/* 187 */       } catch (NoSuchMethodError e) {
/*     */         
/* 189 */         throw new RuntimeModelerException(ModelerMessages.RUNTIME_MODELER_ADDRESSING_RESPONSES_NOSUCHMETHOD(toJar(Which.which(Addressing.class))), new Object[0]);
/*     */       } 
/* 191 */     } else if (a instanceof MTOM) {
/* 192 */       MTOM mtomAnn = (MTOM)a;
/* 193 */       ftr = new MTOMFeature(mtomAnn.enabled(), mtomAnn.threshold());
/* 194 */     } else if (a instanceof RespectBinding) {
/* 195 */       RespectBinding rbAnn = (RespectBinding)a;
/* 196 */       ftr = new RespectBindingFeature(rbAnn.enabled());
/*     */     } else {
/* 198 */       ftr = getWebServiceFeatureBean(a);
/*     */     } 
/* 200 */     return ftr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseAnnotations(Class<?> endpointClass) {
/* 208 */     for (Annotation a : endpointClass.getAnnotations()) {
/* 209 */       WebServiceFeature ftr = getFeature(a);
/* 210 */       if (ftr != null) {
/* 211 */         if (ftr instanceof MTOMFeature) {
/*     */           
/* 213 */           BindingID bindingID = BindingID.parse(endpointClass);
/* 214 */           MTOMFeature bindingMtomSetting = bindingID.createBuiltinFeatureList().<MTOMFeature>get(MTOMFeature.class);
/* 215 */           if (bindingMtomSetting != null && (bindingMtomSetting.isEnabled() ^ ftr.isEnabled()) != 0) {
/* 216 */             throw new RuntimeModelerException(ModelerMessages.RUNTIME_MODELER_MTOM_CONFLICT(bindingID, Boolean.valueOf(ftr.isEnabled())), new Object[0]);
/*     */           }
/*     */         } 
/*     */         
/* 220 */         add(ftr);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toJar(String url) {
/* 229 */     if (!url.startsWith("jar:"))
/* 230 */       return url; 
/* 231 */     url = url.substring(4);
/* 232 */     return url.substring(0, url.lastIndexOf('!'));
/*     */   }
/*     */   private static WebServiceFeature getWebServiceFeatureBean(Annotation a) {
/*     */     WebServiceFeature bean;
/* 236 */     WebServiceFeatureAnnotation wsfa = a.annotationType().<WebServiceFeatureAnnotation>getAnnotation(WebServiceFeatureAnnotation.class);
/* 237 */     Class<? extends WebServiceFeature> beanClass = wsfa.bean();
/*     */ 
/*     */     
/* 240 */     Constructor<FeatureConstructor> ftrCtr = null;
/* 241 */     String[] paramNames = null;
/* 242 */     for (Constructor<FeatureConstructor> con : beanClass.getConstructors()) {
/* 243 */       FeatureConstructor ftrCtrAnn = con.<FeatureConstructor>getAnnotation(FeatureConstructor.class);
/* 244 */       if (ftrCtrAnn != null) {
/* 245 */         if (ftrCtr == null) {
/* 246 */           ftrCtr = con;
/* 247 */           paramNames = ftrCtrAnn.value();
/*     */         } else {
/* 249 */           throw new WebServiceException(ModelerMessages.RUNTIME_MODELER_WSFEATURE_MORETHANONE_FTRCONSTRUCTOR(a, beanClass));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 254 */     if (ftrCtr == null) {
/* 255 */       bean = getWebServiceFeatureBeanViaBuilder(a, beanClass);
/* 256 */       if (bean != null) {
/* 257 */         return bean;
/*     */       }
/* 259 */       throw new WebServiceException(ModelerMessages.RUNTIME_MODELER_WSFEATURE_NO_FTRCONSTRUCTOR(a, beanClass));
/*     */     } 
/*     */ 
/*     */     
/* 263 */     if ((ftrCtr.getParameterTypes()).length != paramNames.length) {
/* 264 */       throw new WebServiceException(ModelerMessages.RUNTIME_MODELER_WSFEATURE_ILLEGAL_FTRCONSTRUCTOR(a, beanClass));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 269 */       Object[] params = new Object[paramNames.length];
/* 270 */       for (int i = 0; i < paramNames.length; i++) {
/* 271 */         Method m = a.annotationType().getDeclaredMethod(paramNames[i], new Class[0]);
/* 272 */         params[i] = m.invoke(a, new Object[0]);
/*     */       } 
/* 274 */       bean = (WebServiceFeature)ftrCtr.newInstance(params);
/* 275 */     } catch (Exception e) {
/* 276 */       throw new WebServiceException(e);
/*     */     } 
/* 278 */     return bean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static WebServiceFeature getWebServiceFeatureBeanViaBuilder(Annotation annotation, Class<? extends WebServiceFeature> beanClass) {
/*     */     try {
/* 286 */       Method featureBuilderMethod = beanClass.getDeclaredMethod("builder", new Class[0]);
/* 287 */       Object builder = featureBuilderMethod.invoke(beanClass, new Object[0]);
/* 288 */       Method buildMethod = builder.getClass().getDeclaredMethod("build", new Class[0]);
/*     */       
/* 290 */       for (Method builderMethod : builder.getClass().getDeclaredMethods()) {
/* 291 */         if (!builderMethod.equals(buildMethod)) {
/* 292 */           String methodName = builderMethod.getName();
/* 293 */           Method annotationMethod = annotation.annotationType().getDeclaredMethod(methodName, new Class[0]);
/* 294 */           Object annotationFieldValue = annotationMethod.invoke(annotation, new Object[0]);
/* 295 */           Object[] arg = { annotationFieldValue };
/* 296 */           if (!skipDuringOrgJvnetWsToComOracleWebservicesPackageMove(builderMethod, annotationFieldValue))
/*     */           {
/*     */             
/* 299 */             builderMethod.invoke(builder, arg);
/*     */           }
/*     */         } 
/*     */       } 
/* 303 */       Object result = buildMethod.invoke(builder, new Object[0]);
/* 304 */       if (result instanceof WebServiceFeature) {
/* 305 */         return (WebServiceFeature)result;
/*     */       }
/* 307 */       throw new WebServiceException("Not a WebServiceFeature: " + result);
/*     */     }
/* 309 */     catch (NoSuchMethodException e) {
/* 310 */       return null;
/* 311 */     } catch (IllegalAccessException e) {
/* 312 */       throw new WebServiceException(e);
/* 313 */     } catch (InvocationTargetException e) {
/* 314 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean skipDuringOrgJvnetWsToComOracleWebservicesPackageMove(Method builderMethod, Object annotationFieldValue) {
/* 323 */     Class<?> annotationFieldValueClass = annotationFieldValue.getClass();
/* 324 */     if (!annotationFieldValueClass.isEnum()) {
/* 325 */       return false;
/*     */     }
/* 327 */     Class<?>[] builderMethodParameterTypes = builderMethod.getParameterTypes();
/* 328 */     if (builderMethodParameterTypes.length != 1) {
/* 329 */       throw new WebServiceException("expected only 1 parameter");
/*     */     }
/* 331 */     String builderParameterTypeName = builderMethodParameterTypes[0].getName();
/* 332 */     if (!builderParameterTypeName.startsWith("com.oracle.webservices.test.features_annotations_enums.apinew") && !builderParameterTypeName.startsWith("com.oracle.webservices.api"))
/*     */     {
/* 334 */       return false;
/*     */     }
/* 336 */     return false;
/*     */   }
/*     */   
/*     */   public Iterator<WebServiceFeature> iterator() {
/* 340 */     if (this.parent != null)
/* 341 */       return new MergedFeatures(this.parent.getFeatures()); 
/* 342 */     return this.wsfeatures.values().iterator();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeature[] toArray() {
/* 347 */     if (this.parent != null)
/* 348 */       return (new MergedFeatures(this.parent.getFeatures())).toArray(); 
/* 349 */     return (WebServiceFeature[])this.wsfeatures.values().toArray((Object[])new WebServiceFeature[0]);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(@NotNull Class<? extends WebServiceFeature> feature) {
/* 353 */     WebServiceFeature ftr = get((Class)feature);
/* 354 */     return (ftr != null && ftr.isEnabled());
/*     */   }
/*     */   
/*     */   public boolean contains(@NotNull Class<? extends WebServiceFeature> feature) {
/* 358 */     WebServiceFeature ftr = get((Class)feature);
/* 359 */     return (ftr != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <F extends WebServiceFeature> F get(@NotNull Class<F> featureType) {
/* 364 */     WebServiceFeature f = (WebServiceFeature)featureType.cast(this.wsfeatures.get(featureType));
/* 365 */     if (f == null && this.parent != null) {
/* 366 */       return (F)this.parent.getFeatures().get(featureType);
/*     */     }
/* 368 */     return (F)f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(@NotNull WebServiceFeature f) {
/* 375 */     if (addNoValidate(f) && this.isValidating)
/* 376 */       validate(f); 
/*     */   }
/*     */   
/*     */   private boolean addNoValidate(@NotNull WebServiceFeature f) {
/* 380 */     if (!this.wsfeatures.containsKey(f.getClass())) {
/* 381 */       this.wsfeatures.put(f.getClass(), f);
/*     */       
/* 383 */       if (f instanceof ImpliesWebServiceFeature) {
/* 384 */         ((ImpliesWebServiceFeature)f).implyFeatures(this);
/*     */       }
/* 386 */       return true;
/*     */     } 
/*     */     
/* 389 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(@NotNull Iterable<WebServiceFeature> list) {
/* 396 */     for (WebServiceFeature f : list) {
/* 397 */       add(f);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setMTOMEnabled(boolean b) {
/* 406 */     this.wsfeatures.put(MTOMFeature.class, new MTOMFeature(b));
/*     */   }
/*     */   
/*     */   public boolean equals(Object other) {
/* 410 */     if (!(other instanceof WebServiceFeatureList)) {
/* 411 */       return false;
/*     */     }
/* 413 */     WebServiceFeatureList w = (WebServiceFeatureList)other;
/* 414 */     return (this.wsfeatures.equals(w.wsfeatures) && this.parent == w.parent);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 418 */     return this.wsfeatures.toString();
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
/*     */   public void mergeFeatures(@NotNull Iterable<WebServiceFeature> features, boolean reportConflicts) {
/* 432 */     for (WebServiceFeature wsdlFtr : features) {
/* 433 */       if (get(wsdlFtr.getClass()) == null) {
/* 434 */         add(wsdlFtr); continue;
/* 435 */       }  if (reportConflicts && 
/* 436 */         isEnabled((Class)wsdlFtr.getClass()) != wsdlFtr.isEnabled()) {
/* 437 */         LOGGER.warning(ModelerMessages.RUNTIME_MODELER_FEATURE_CONFLICT(get(wsdlFtr.getClass()), wsdlFtr));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeFeatures(WebServiceFeature[] features, boolean reportConflicts) {
/* 445 */     for (WebServiceFeature wsdlFtr : features) {
/* 446 */       if (get(wsdlFtr.getClass()) == null) {
/* 447 */         add(wsdlFtr);
/* 448 */       } else if (reportConflicts && 
/* 449 */         isEnabled((Class)wsdlFtr.getClass()) != wsdlFtr.isEnabled()) {
/* 450 */         LOGGER.warning(ModelerMessages.RUNTIME_MODELER_FEATURE_CONFLICT(get(wsdlFtr.getClass()), wsdlFtr));
/*     */       } 
/*     */     } 
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
/*     */   public void mergeFeatures(@NotNull WSDLPort wsdlPort, boolean honorWsdlRequired, boolean reportConflicts) {
/* 475 */     if (honorWsdlRequired && !isEnabled((Class)RespectBindingFeature.class))
/*     */       return; 
/* 477 */     if (!honorWsdlRequired) {
/* 478 */       addAll((Iterable<WebServiceFeature>)wsdlPort.getFeatures());
/*     */       
/*     */       return;
/*     */     } 
/* 482 */     for (WebServiceFeature wsdlFtr : wsdlPort.getFeatures()) {
/* 483 */       if (get(wsdlFtr.getClass()) == null) {
/*     */ 
/*     */         
/*     */         try {
/* 487 */           Method m = wsdlFtr.getClass().getMethod("isRequired", new Class[0]);
/*     */           try {
/* 489 */             boolean required = ((Boolean)m.invoke(wsdlFtr, new Object[0])).booleanValue();
/* 490 */             if (required)
/* 491 */               add(wsdlFtr); 
/* 492 */           } catch (IllegalAccessException e) {
/* 493 */             throw new WebServiceException(e);
/* 494 */           } catch (InvocationTargetException e) {
/* 495 */             throw new WebServiceException(e);
/*     */           } 
/* 497 */         } catch (NoSuchMethodException e) {
/*     */           
/* 499 */           add(wsdlFtr);
/*     */         }  continue;
/* 501 */       }  if (reportConflicts && 
/* 502 */         isEnabled((Class)wsdlFtr.getClass()) != wsdlFtr.isEnabled()) {
/* 503 */         LOGGER.warning(ModelerMessages.RUNTIME_MODELER_FEATURE_CONFLICT(get(wsdlFtr.getClass()), wsdlFtr));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentFeaturedObject(@NotNull WSDLFeaturedObject parent) {
/* 516 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <F extends WebServiceFeature> F getFeature(@NotNull WebServiceFeature[] features, @NotNull Class<F> featureType) {
/* 521 */     for (WebServiceFeature f : features) {
/* 522 */       if (f.getClass() == featureType)
/* 523 */         return (F)f; 
/*     */     } 
/* 525 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private final class MergedFeatures
/*     */     implements Iterator<WebServiceFeature>
/*     */   {
/* 532 */     private final Stack<WebServiceFeature> features = new Stack<WebServiceFeature>();
/*     */ 
/*     */     
/*     */     public MergedFeatures(WSFeatureList parent) {
/* 536 */       for (WebServiceFeature f : WebServiceFeatureList.this.wsfeatures.values()) {
/* 537 */         this.features.push(f);
/*     */       }
/*     */       
/* 540 */       for (WebServiceFeature f : parent) {
/* 541 */         if (!WebServiceFeatureList.this.wsfeatures.containsKey(f.getClass())) {
/* 542 */           this.features.push(f);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 548 */       return !this.features.empty();
/*     */     }
/*     */     
/*     */     public WebServiceFeature next() {
/* 552 */       if (!this.features.empty()) {
/* 553 */         return this.features.pop();
/*     */       }
/* 555 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 559 */       if (!this.features.empty()) {
/* 560 */         this.features.pop();
/*     */       }
/*     */     }
/*     */     
/*     */     public WebServiceFeature[] toArray() {
/* 565 */       return (WebServiceFeature[])this.features.toArray((Object[])new WebServiceFeature[0]);
/*     */     }
/*     */   }
/*     */   
/* 569 */   private static final Logger LOGGER = Logger.getLogger(WebServiceFeatureList.class.getName());
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Class<? extends WebServiceFeature>, WebServiceFeature>> entrySet() {
/* 573 */     return this.wsfeatures.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public WebServiceFeature put(Class<? extends WebServiceFeature> key, WebServiceFeature value) {
/* 578 */     return this.wsfeatures.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SOAPVersion getSoapVersion(WSFeatureList features) {
/* 583 */     EnvelopeStyleFeature env = (EnvelopeStyleFeature)features.get(EnvelopeStyleFeature.class);
/* 584 */     if (env != null) {
/* 585 */       return SOAPVersion.from(env);
/*     */     }
/*     */     
/* 588 */     env = (EnvelopeStyleFeature)features.get(EnvelopeStyleFeature.class);
/* 589 */     return (env != null) ? SOAPVersion.from(env) : null;
/*     */   }
/*     */   
/*     */   public static boolean isFeatureEnabled(Class<? extends WebServiceFeature> type, WebServiceFeature[] features) {
/* 593 */     WebServiceFeature ftr = getFeature(features, (Class)type);
/* 594 */     return (ftr != null && ftr.isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public static WebServiceFeature[] toFeatureArray(WSBinding binding) {
/* 599 */     if (!binding.isFeatureEnabled(EnvelopeStyleFeature.class)) {
/* 600 */       WebServiceFeature[] f = { (WebServiceFeature)binding.getSOAPVersion().toFeature() };
/* 601 */       binding.getFeatures().mergeFeatures(f, false);
/*     */     } 
/* 603 */     return binding.getFeatures().toArray();
/*     */   }
/*     */   
/*     */   public WebServiceFeatureList() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\binding\WebServiceFeatureList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */