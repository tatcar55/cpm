/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.Which;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.model.core.ErrorHandler;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.core.Ref;
/*     */ import com.sun.xml.bind.v2.model.core.RegistryInfo;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBuilder<T, C, F, M>
/*     */   implements ModelBuilderI<T, C, F, M>
/*     */ {
/* 104 */   private final Map<QName, TypeInfo> typeNames = new HashMap<QName, TypeInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   final Map<String, RegistryInfoImpl<T, C, F, M>> registries = new HashMap<String, RegistryInfoImpl<T, C, F, M>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   private final ErrorHandler proxyErrorHandler = new ErrorHandler() {
/*     */       public void error(IllegalAnnotationException e) {
/* 144 */         ModelBuilder.this.reportError(e);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelBuilder(AnnotationReader<T, C, F, M> reader, Navigator<T, C, F, M> navigator, Map<C, C> subclassReplacements, String defaultNamespaceRemap) {
/* 155 */     this.reader = reader;
/* 156 */     this.nav = navigator;
/* 157 */     this.subclassReplacements = subclassReplacements;
/* 158 */     if (defaultNamespaceRemap == null)
/* 159 */       defaultNamespaceRemap = ""; 
/* 160 */     this.defaultNsUri = defaultNamespaceRemap;
/* 161 */     reader.setErrorHandler(this.proxyErrorHandler);
/* 162 */     this.typeInfoSet = createTypeInfoSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 171 */       XmlSchema s = null;
/* 172 */       s.location();
/* 173 */     } catch (NullPointerException e) {
/*     */     
/* 175 */     } catch (NoSuchMethodError e) {
/*     */       Messages res;
/*     */       
/* 178 */       if (SecureLoader.getClassClassLoader(XmlSchema.class) == null) {
/* 179 */         res = Messages.INCOMPATIBLE_API_VERSION_MUSTANG;
/*     */       } else {
/* 181 */         res = Messages.INCOMPATIBLE_API_VERSION;
/*     */       } 
/*     */       
/* 184 */       throw new LinkageError(res.format(new Object[] { Which.which(XmlSchema.class), Which.which(ModelBuilder.class) }));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 197 */       WhiteSpaceProcessor.isWhiteSpace("xyz");
/* 198 */     } catch (NoSuchMethodError e) {
/*     */       
/* 200 */       throw new LinkageError(Messages.RUNNING_WITH_1_0_RUNTIME.format(new Object[] { Which.which(WhiteSpaceProcessor.class), Which.which(ModelBuilder.class) }));
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
/* 211 */   private static final Logger logger = Logger.getLogger(ModelBuilder.class.getName());
/*     */   final TypeInfoSetImpl<T, C, F, M> typeInfoSet;
/*     */   
/*     */   protected TypeInfoSetImpl<T, C, F, M> createTypeInfoSet() {
/* 215 */     return new TypeInfoSetImpl<T, C, F, M>(this.nav, this.reader, BuiltinLeafInfoImpl.createLeaves(this.nav));
/*     */   }
/*     */   public final AnnotationReader<T, C, F, M> reader;
/*     */   public final Navigator<T, C, F, M> nav;
/*     */   public final String defaultNsUri;
/*     */   private final Map<C, C> subclassReplacements;
/*     */   private ErrorHandler errorHandler;
/*     */   private boolean hadError;
/*     */   public boolean hasSwaRef;
/*     */   private boolean linked;
/*     */   
/*     */   public NonElement<T, C> getClassInfo(C clazz, Locatable upstream) {
/* 227 */     return getClassInfo(clazz, false, upstream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getClassInfo(C clazz, boolean searchForSuperClass, Locatable upstream) {
/*     */     // Byte code:
/*     */     //   0: getstatic com/sun/xml/bind/v2/model/impl/ModelBuilder.$assertionsDisabled : Z
/*     */     //   3: ifne -> 18
/*     */     //   6: aload_1
/*     */     //   7: ifnonnull -> 18
/*     */     //   10: new java/lang/AssertionError
/*     */     //   13: dup
/*     */     //   14: invokespecial <init> : ()V
/*     */     //   17: athrow
/*     */     //   18: aload_0
/*     */     //   19: getfield typeInfoSet : Lcom/sun/xml/bind/v2/model/impl/TypeInfoSetImpl;
/*     */     //   22: aload_1
/*     */     //   23: invokevirtual getClassInfo : (Ljava/lang/Object;)Lcom/sun/xml/bind/v2/model/core/NonElement;
/*     */     //   26: astore #4
/*     */     //   28: aload #4
/*     */     //   30: ifnull -> 36
/*     */     //   33: aload #4
/*     */     //   35: areturn
/*     */     //   36: aload_0
/*     */     //   37: getfield nav : Lcom/sun/xml/bind/v2/model/nav/Navigator;
/*     */     //   40: aload_1
/*     */     //   41: invokeinterface isEnum : (Ljava/lang/Object;)Z
/*     */     //   46: ifeq -> 79
/*     */     //   49: aload_0
/*     */     //   50: aload_1
/*     */     //   51: aload_3
/*     */     //   52: invokevirtual createEnumLeafInfo : (Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/bind/v2/model/impl/EnumLeafInfoImpl;
/*     */     //   55: astore #5
/*     */     //   57: aload_0
/*     */     //   58: getfield typeInfoSet : Lcom/sun/xml/bind/v2/model/impl/TypeInfoSetImpl;
/*     */     //   61: aload #5
/*     */     //   63: invokevirtual add : (Lcom/sun/xml/bind/v2/model/impl/EnumLeafInfoImpl;)V
/*     */     //   66: aload #5
/*     */     //   68: astore #4
/*     */     //   70: aload_0
/*     */     //   71: aload #4
/*     */     //   73: invokespecial addTypeName : (Lcom/sun/xml/bind/v2/model/core/NonElement;)V
/*     */     //   76: goto -> 368
/*     */     //   79: aload_0
/*     */     //   80: getfield subclassReplacements : Ljava/util/Map;
/*     */     //   83: aload_1
/*     */     //   84: invokeinterface containsKey : (Ljava/lang/Object;)Z
/*     */     //   89: istore #5
/*     */     //   91: iload #5
/*     */     //   93: ifeq -> 120
/*     */     //   96: iload_2
/*     */     //   97: ifne -> 120
/*     */     //   100: aload_0
/*     */     //   101: aload_0
/*     */     //   102: getfield subclassReplacements : Ljava/util/Map;
/*     */     //   105: aload_1
/*     */     //   106: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   111: aload_3
/*     */     //   112: invokevirtual getClassInfo : (Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/bind/v2/model/core/NonElement;
/*     */     //   115: astore #4
/*     */     //   117: goto -> 368
/*     */     //   120: aload_0
/*     */     //   121: getfield reader : Lcom/sun/xml/bind/v2/model/annotation/AnnotationReader;
/*     */     //   124: aload_1
/*     */     //   125: ldc_w javax/xml/bind/annotation/XmlTransient
/*     */     //   128: invokeinterface hasClassAnnotation : (Ljava/lang/Object;Ljava/lang/Class;)Z
/*     */     //   133: ifne -> 141
/*     */     //   136: iload #5
/*     */     //   138: ifeq -> 174
/*     */     //   141: aload_0
/*     */     //   142: aload_0
/*     */     //   143: getfield nav : Lcom/sun/xml/bind/v2/model/nav/Navigator;
/*     */     //   146: aload_1
/*     */     //   147: invokeinterface getSuperClass : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   152: iload_2
/*     */     //   153: new com/sun/xml/bind/v2/model/annotation/ClassLocatable
/*     */     //   156: dup
/*     */     //   157: aload_3
/*     */     //   158: aload_1
/*     */     //   159: aload_0
/*     */     //   160: getfield nav : Lcom/sun/xml/bind/v2/model/nav/Navigator;
/*     */     //   163: invokespecial <init> : (Lcom/sun/xml/bind/v2/model/annotation/Locatable;Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/nav/Navigator;)V
/*     */     //   166: invokevirtual getClassInfo : (Ljava/lang/Object;ZLcom/sun/xml/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/bind/v2/model/core/NonElement;
/*     */     //   169: astore #4
/*     */     //   171: goto -> 368
/*     */     //   174: aload_0
/*     */     //   175: aload_1
/*     */     //   176: aload_3
/*     */     //   177: invokevirtual createClassInfo : (Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/bind/v2/model/impl/ClassInfoImpl;
/*     */     //   180: astore #6
/*     */     //   182: aload_0
/*     */     //   183: getfield typeInfoSet : Lcom/sun/xml/bind/v2/model/impl/TypeInfoSetImpl;
/*     */     //   186: aload #6
/*     */     //   188: invokevirtual add : (Lcom/sun/xml/bind/v2/model/impl/ClassInfoImpl;)V
/*     */     //   191: aload #6
/*     */     //   193: invokevirtual getProperties : ()Ljava/util/List;
/*     */     //   196: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   201: astore #7
/*     */     //   203: aload #7
/*     */     //   205: invokeinterface hasNext : ()Z
/*     */     //   210: ifeq -> 352
/*     */     //   213: aload #7
/*     */     //   215: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   220: checkcast com/sun/xml/bind/v2/model/core/PropertyInfo
/*     */     //   223: astore #8
/*     */     //   225: aload #8
/*     */     //   227: invokeinterface kind : ()Lcom/sun/xml/bind/v2/model/core/PropertyKind;
/*     */     //   232: getstatic com/sun/xml/bind/v2/model/core/PropertyKind.REFERENCE : Lcom/sun/xml/bind/v2/model/core/PropertyKind;
/*     */     //   235: if_acmpne -> 310
/*     */     //   238: aload_0
/*     */     //   239: aload_1
/*     */     //   240: aload #8
/*     */     //   242: checkcast com/sun/xml/bind/v2/model/annotation/Locatable
/*     */     //   245: invokespecial addToRegistry : (Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)V
/*     */     //   248: aload_0
/*     */     //   249: aload #8
/*     */     //   251: invokespecial getParametrizedTypes : (Lcom/sun/xml/bind/v2/model/core/PropertyInfo;)[Ljava/lang/Class;
/*     */     //   254: astore #9
/*     */     //   256: aload #9
/*     */     //   258: ifnull -> 310
/*     */     //   261: aload #9
/*     */     //   263: astore #10
/*     */     //   265: aload #10
/*     */     //   267: arraylength
/*     */     //   268: istore #11
/*     */     //   270: iconst_0
/*     */     //   271: istore #12
/*     */     //   273: iload #12
/*     */     //   275: iload #11
/*     */     //   277: if_icmpge -> 310
/*     */     //   280: aload #10
/*     */     //   282: iload #12
/*     */     //   284: aaload
/*     */     //   285: astore #13
/*     */     //   287: aload #13
/*     */     //   289: aload_1
/*     */     //   290: if_acmpeq -> 304
/*     */     //   293: aload_0
/*     */     //   294: aload #13
/*     */     //   296: aload #8
/*     */     //   298: checkcast com/sun/xml/bind/v2/model/annotation/Locatable
/*     */     //   301: invokespecial addToRegistry : (Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)V
/*     */     //   304: iinc #12, 1
/*     */     //   307: goto -> 273
/*     */     //   310: aload #8
/*     */     //   312: invokeinterface ref : ()Ljava/util/Collection;
/*     */     //   317: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   322: astore #9
/*     */     //   324: aload #9
/*     */     //   326: invokeinterface hasNext : ()Z
/*     */     //   331: ifeq -> 349
/*     */     //   334: aload #9
/*     */     //   336: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   341: checkcast com/sun/xml/bind/v2/model/core/TypeInfo
/*     */     //   344: astore #10
/*     */     //   346: goto -> 324
/*     */     //   349: goto -> 203
/*     */     //   352: aload #6
/*     */     //   354: invokevirtual getBaseClass : ()Lcom/sun/xml/bind/v2/model/impl/ClassInfoImpl;
/*     */     //   357: pop
/*     */     //   358: aload #6
/*     */     //   360: astore #4
/*     */     //   362: aload_0
/*     */     //   363: aload #4
/*     */     //   365: invokespecial addTypeName : (Lcom/sun/xml/bind/v2/model/core/NonElement;)V
/*     */     //   368: aload_0
/*     */     //   369: getfield reader : Lcom/sun/xml/bind/v2/model/annotation/AnnotationReader;
/*     */     //   372: ldc_w javax/xml/bind/annotation/XmlSeeAlso
/*     */     //   375: aload_1
/*     */     //   376: aload_3
/*     */     //   377: invokeinterface getClassAnnotation : (Ljava/lang/Class;Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   382: checkcast javax/xml/bind/annotation/XmlSeeAlso
/*     */     //   385: astore #5
/*     */     //   387: aload #5
/*     */     //   389: ifnull -> 447
/*     */     //   392: aload_0
/*     */     //   393: getfield reader : Lcom/sun/xml/bind/v2/model/annotation/AnnotationReader;
/*     */     //   396: aload #5
/*     */     //   398: ldc 'value'
/*     */     //   400: invokeinterface getClassArrayValue : (Ljava/lang/annotation/Annotation;Ljava/lang/String;)[Ljava/lang/Object;
/*     */     //   405: astore #6
/*     */     //   407: aload #6
/*     */     //   409: arraylength
/*     */     //   410: istore #7
/*     */     //   412: iconst_0
/*     */     //   413: istore #8
/*     */     //   415: iload #8
/*     */     //   417: iload #7
/*     */     //   419: if_icmpge -> 447
/*     */     //   422: aload #6
/*     */     //   424: iload #8
/*     */     //   426: aaload
/*     */     //   427: astore #9
/*     */     //   429: aload_0
/*     */     //   430: aload #9
/*     */     //   432: aload #5
/*     */     //   434: checkcast com/sun/xml/bind/v2/model/annotation/Locatable
/*     */     //   437: invokevirtual getTypeInfo : (Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/bind/v2/model/core/NonElement;
/*     */     //   440: pop
/*     */     //   441: iinc #8, 1
/*     */     //   444: goto -> 415
/*     */     //   447: aload #4
/*     */     //   449: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #236	-> 0
/*     */     //   #237	-> 18
/*     */     //   #238	-> 28
/*     */     //   #239	-> 33
/*     */     //   #241	-> 36
/*     */     //   #242	-> 49
/*     */     //   #243	-> 57
/*     */     //   #244	-> 66
/*     */     //   #245	-> 70
/*     */     //   #246	-> 76
/*     */     //   #247	-> 79
/*     */     //   #248	-> 91
/*     */     //   #250	-> 100
/*     */     //   #252	-> 120
/*     */     //   #254	-> 141
/*     */     //   #257	-> 174
/*     */     //   #258	-> 182
/*     */     //   #261	-> 191
/*     */     //   #262	-> 225
/*     */     //   #264	-> 238
/*     */     //   #265	-> 248
/*     */     //   #266	-> 256
/*     */     //   #267	-> 261
/*     */     //   #268	-> 287
/*     */     //   #269	-> 293
/*     */     //   #267	-> 304
/*     */     //   #275	-> 310
/*     */     //   #276	-> 346
/*     */     //   #278	-> 352
/*     */     //   #280	-> 358
/*     */     //   #281	-> 362
/*     */     //   #287	-> 368
/*     */     //   #288	-> 387
/*     */     //   #289	-> 392
/*     */     //   #290	-> 429
/*     */     //   #289	-> 441
/*     */     //   #295	-> 447
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   57	19	5	li	Lcom/sun/xml/bind/v2/model/impl/EnumLeafInfoImpl;
/*     */     //   287	17	13	prmzdClass	Ljava/lang/Class;
/*     */     //   265	45	10	arr$	[Ljava/lang/Class;
/*     */     //   270	40	11	len$	I
/*     */     //   273	37	12	i$	I
/*     */     //   256	54	9	prmzdClasses	[Ljava/lang/Class;
/*     */     //   346	0	10	t	Lcom/sun/xml/bind/v2/model/core/TypeInfo;
/*     */     //   324	25	9	i$	Ljava/util/Iterator;
/*     */     //   225	124	8	p	Lcom/sun/xml/bind/v2/model/core/PropertyInfo;
/*     */     //   203	149	7	i$	Ljava/util/Iterator;
/*     */     //   182	186	6	ci	Lcom/sun/xml/bind/v2/model/impl/ClassInfoImpl;
/*     */     //   91	277	5	isReplaced	Z
/*     */     //   429	12	9	t	Ljava/lang/Object;
/*     */     //   407	40	6	arr$	[Ljava/lang/Object;
/*     */     //   412	35	7	len$	I
/*     */     //   415	32	8	i$	I
/*     */     //   0	450	0	this	Lcom/sun/xml/bind/v2/model/impl/ModelBuilder;
/*     */     //   0	450	1	clazz	Ljava/lang/Object;
/*     */     //   0	450	2	searchForSuperClass	Z
/*     */     //   0	450	3	upstream	Lcom/sun/xml/bind/v2/model/annotation/Locatable;
/*     */     //   28	422	4	r	Lcom/sun/xml/bind/v2/model/core/NonElement;
/*     */     //   387	63	5	sa	Ljavax/xml/bind/annotation/XmlSeeAlso;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   57	19	5	li	Lcom/sun/xml/bind/v2/model/impl/EnumLeafInfoImpl<TT;TC;TF;TM;>;
/*     */     //   346	0	10	t	Lcom/sun/xml/bind/v2/model/core/TypeInfo<TT;TC;>;
/*     */     //   225	124	8	p	Lcom/sun/xml/bind/v2/model/core/PropertyInfo<TT;TC;>;
/*     */     //   182	186	6	ci	Lcom/sun/xml/bind/v2/model/impl/ClassInfoImpl<TT;TC;TF;TM;>;
/*     */     //   429	12	9	t	TT;
/*     */     //   0	450	0	this	Lcom/sun/xml/bind/v2/model/impl/ModelBuilder<TT;TC;TF;TM;>;
/*     */     //   0	450	1	clazz	TC;
/*     */     //   28	422	4	r	Lcom/sun/xml/bind/v2/model/core/NonElement<TT;TC;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToRegistry(C clazz, Locatable p) {
/* 304 */     String pkg = this.nav.getPackageName(clazz);
/* 305 */     if (!this.registries.containsKey(pkg)) {
/*     */       
/* 307 */       C c = (C)this.nav.findClass(pkg + ".ObjectFactory", clazz);
/* 308 */       if (c != null) {
/* 309 */         addRegistry(c, p);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class[] getParametrizedTypes(PropertyInfo p) {
/*     */     try {
/* 320 */       Type pType = ((RuntimePropertyInfo)p).getIndividualType();
/* 321 */       if (pType instanceof ParameterizedType) {
/* 322 */         ParameterizedType prmzdType = (ParameterizedType)pType;
/* 323 */         if (prmzdType.getRawType() == JAXBElement.class) {
/* 324 */           Type[] actualTypes = prmzdType.getActualTypeArguments();
/* 325 */           Class[] result = new Class[actualTypes.length];
/* 326 */           for (int i = 0; i < actualTypes.length; i++) {
/* 327 */             result[i] = (Class)actualTypes[i];
/*     */           }
/* 329 */           return result;
/*     */         } 
/*     */       } 
/* 332 */     } catch (Exception e) {
/* 333 */       logger.log(Level.FINE, "Error in ModelBuilder.getParametrizedTypes. " + e.getMessage());
/*     */     } 
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTypeName(NonElement<T, C> r) {
/* 342 */     QName t = r.getTypeName();
/* 343 */     if (t == null)
/*     */       return; 
/* 345 */     TypeInfo old = (TypeInfo)this.typeNames.put(t, r);
/* 346 */     if (old != null)
/*     */     {
/* 348 */       reportError(new IllegalAnnotationException(Messages.CONFLICTING_XML_TYPE_MAPPING.format(new Object[] { r.getTypeName() }, ), (Locatable)old, (Locatable)r));
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
/*     */   public NonElement<T, C> getTypeInfo(T t, Locatable upstream) {
/* 362 */     NonElement<T, C> r = this.typeInfoSet.getTypeInfo(t);
/* 363 */     if (r != null) return r;
/*     */     
/* 365 */     if (this.nav.isArray(t)) {
/* 366 */       ArrayInfoImpl<T, C, F, M> ai = createArrayInfo(upstream, t);
/*     */       
/* 368 */       addTypeName((NonElement)ai);
/* 369 */       this.typeInfoSet.add(ai);
/* 370 */       return (NonElement)ai;
/*     */     } 
/*     */     
/* 373 */     C c = (C)this.nav.asDecl(t);
/* 374 */     assert c != null : t.toString() + " must be a leaf, but we failed to recognize it.";
/* 375 */     return getClassInfo(c, upstream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(Ref<T, C> ref) {
/* 383 */     assert !ref.valueList;
/* 384 */     C c = (C)this.nav.asDecl(ref.type);
/* 385 */     if (c != null && this.reader.getClassAnnotation(XmlRegistry.class, c, null) != null) {
/* 386 */       if (!this.registries.containsKey(this.nav.getPackageName(c)))
/* 387 */         addRegistry(c, null); 
/* 388 */       return null;
/*     */     } 
/* 390 */     return getTypeInfo((T)ref.type, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumLeafInfoImpl<T, C, F, M> createEnumLeafInfo(C clazz, Locatable upstream) {
/* 395 */     return new EnumLeafInfoImpl<T, C, F, M>(this, upstream, clazz, (T)this.nav.use(clazz));
/*     */   }
/*     */   
/*     */   protected ClassInfoImpl<T, C, F, M> createClassInfo(C clazz, Locatable upstream) {
/* 399 */     return new ClassInfoImpl<T, C, F, M>(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ElementInfoImpl<T, C, F, M> createElementInfo(RegistryInfoImpl<T, C, F, M> registryInfo, M m) throws IllegalAnnotationException {
/* 404 */     return new ElementInfoImpl<T, C, F, M>(this, registryInfo, m);
/*     */   }
/*     */   
/*     */   protected ArrayInfoImpl<T, C, F, M> createArrayInfo(Locatable upstream, T arrayType) {
/* 408 */     return new ArrayInfoImpl<T, C, F, M>(this, upstream, arrayType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryInfo<T, C> addRegistry(C registryClass, Locatable upstream) {
/* 417 */     return new RegistryInfoImpl<T, C, F, M>(this, upstream, registryClass);
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
/*     */   public RegistryInfo<T, C> getRegistry(String packageName) {
/* 429 */     return this.registries.get(packageName);
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
/*     */   public TypeInfoSet<T, C, F, M> link() {
/* 447 */     assert !this.linked;
/* 448 */     this.linked = true;
/*     */     
/* 450 */     for (ElementInfoImpl<T, C, F, M> ei : this.typeInfoSet.getAllElements()) {
/* 451 */       ei.link();
/*     */     }
/* 453 */     for (ClassInfoImpl ci : this.typeInfoSet.beans().values()) {
/* 454 */       ci.link();
/*     */     }
/* 456 */     for (EnumLeafInfoImpl li : this.typeInfoSet.enums().values()) {
/* 457 */       li.link();
/*     */     }
/* 459 */     if (this.hadError) {
/* 460 */       return null;
/*     */     }
/* 462 */     return this.typeInfoSet;
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
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/* 478 */     this.errorHandler = errorHandler;
/*     */   }
/*     */   
/*     */   public final void reportError(IllegalAnnotationException e) {
/* 482 */     this.hadError = true;
/* 483 */     if (this.errorHandler != null)
/* 484 */       this.errorHandler.error(e); 
/*     */   }
/*     */   
/*     */   public boolean isReplaced(C sc) {
/* 488 */     return this.subclassReplacements.containsKey(sc);
/*     */   }
/*     */ 
/*     */   
/*     */   public Navigator<T, C, F, M> getNavigator() {
/* 493 */     return this.nav;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationReader<T, C, F, M> getReader() {
/* 498 */     return this.reader;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\ModelBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */