/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.ws.spi.db.BindingHelper;
/*     */ import com.sun.xml.ws.util.StringUtils;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*     */ public abstract class AbstractWrapperBeanGenerator<T, C, M, A extends Comparable>
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(AbstractWrapperBeanGenerator.class.getName());
/*     */   
/*     */   private static final String RETURN = "return";
/*     */   
/*     */   private static final String EMTPY_NAMESPACE_ID = "";
/*  78 */   private static final Class[] jaxbAnns = new Class[] { XmlAttachmentRef.class, XmlMimeType.class, XmlJavaTypeAdapter.class, XmlList.class, XmlElement.class };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final Set<String> skipProperties = new HashSet<String>();
/*     */   static {
/*  85 */     skipProperties.add("getCause");
/*  86 */     skipProperties.add("getLocalizedMessage");
/*  87 */     skipProperties.add("getClass");
/*  88 */     skipProperties.add("getStackTrace");
/*  89 */     skipProperties.add("getSuppressed");
/*     */   }
/*     */ 
/*     */   
/*     */   private final AnnotationReader<T, C, ?, M> annReader;
/*     */   private final Navigator<T, C, ?, M> nav;
/*     */   private final BeanMemberFactory<T, A> factory;
/*     */   
/*     */   protected AbstractWrapperBeanGenerator(AnnotationReader<T, C, ?, M> annReader, Navigator<T, C, ?, M> nav, BeanMemberFactory<T, A> factory) {
/*  98 */     this.annReader = annReader;
/*  99 */     this.nav = nav;
/* 100 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Annotation> collectJAXBAnnotations(M method) {
/* 109 */     List<Annotation> jaxbAnnotation = new ArrayList<Annotation>();
/* 110 */     for (Class jaxbClass : jaxbAnns) {
/* 111 */       Annotation ann = this.annReader.getMethodAnnotation(jaxbClass, method, null);
/* 112 */       if (ann != null) {
/* 113 */         jaxbAnnotation.add(ann);
/*     */       }
/*     */     } 
/* 116 */     return jaxbAnnotation;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Annotation> collectJAXBAnnotations(M method, int paramIndex) {
/* 121 */     List<Annotation> jaxbAnnotation = new ArrayList<Annotation>();
/* 122 */     for (Class jaxbClass : jaxbAnns) {
/* 123 */       Annotation ann = this.annReader.getMethodParameterAnnotation(jaxbClass, method, paramIndex, null);
/* 124 */       if (ann != null) {
/* 125 */         jaxbAnnotation.add(ann);
/*     */       }
/*     */     } 
/* 128 */     return jaxbAnnotation;
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
/*     */   
/*     */   public List<A> collectRequestBeanMembers(M method) {
/*     */     // Byte code:
/*     */     //   0: new java/util/ArrayList
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_2
/*     */     //   8: iconst_m1
/*     */     //   9: istore_3
/*     */     //   10: aload_0
/*     */     //   11: getfield nav : Lcom/sun/xml/bind/v2/model/nav/Navigator;
/*     */     //   14: aload_1
/*     */     //   15: invokeinterface getMethodParameters : (Ljava/lang/Object;)[Ljava/lang/Object;
/*     */     //   20: astore #4
/*     */     //   22: aload #4
/*     */     //   24: arraylength
/*     */     //   25: istore #5
/*     */     //   27: iconst_0
/*     */     //   28: istore #6
/*     */     //   30: iload #6
/*     */     //   32: iload #5
/*     */     //   34: if_icmpge -> 266
/*     */     //   37: aload #4
/*     */     //   39: iload #6
/*     */     //   41: aaload
/*     */     //   42: astore #7
/*     */     //   44: iinc #3, 1
/*     */     //   47: aload_0
/*     */     //   48: getfield annReader : Lcom/sun/xml/bind/v2/model/annotation/AnnotationReader;
/*     */     //   51: ldc_w javax/jws/WebParam
/*     */     //   54: aload_1
/*     */     //   55: iload_3
/*     */     //   56: aconst_null
/*     */     //   57: invokeinterface getMethodParameterAnnotation : (Ljava/lang/Class;Ljava/lang/Object;ILcom/sun/xml/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   62: checkcast javax/jws/WebParam
/*     */     //   65: astore #8
/*     */     //   67: aload #8
/*     */     //   69: ifnull -> 101
/*     */     //   72: aload #8
/*     */     //   74: invokeinterface header : ()Z
/*     */     //   79: ifne -> 260
/*     */     //   82: aload #8
/*     */     //   84: invokeinterface mode : ()Ljavax/jws/WebParam$Mode;
/*     */     //   89: getstatic javax/jws/WebParam$Mode.OUT : Ljavax/jws/WebParam$Mode;
/*     */     //   92: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   95: ifeq -> 101
/*     */     //   98: goto -> 260
/*     */     //   101: aload_0
/*     */     //   102: aload #7
/*     */     //   104: invokevirtual getHolderValueType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   107: astore #9
/*     */     //   109: aload #9
/*     */     //   111: ifnull -> 119
/*     */     //   114: aload #9
/*     */     //   116: goto -> 125
/*     */     //   119: aload_0
/*     */     //   120: aload #7
/*     */     //   122: invokevirtual getSafeType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   125: astore #10
/*     */     //   127: aload #8
/*     */     //   129: ifnull -> 155
/*     */     //   132: aload #8
/*     */     //   134: invokeinterface name : ()Ljava/lang/String;
/*     */     //   139: invokevirtual length : ()I
/*     */     //   142: ifle -> 155
/*     */     //   145: aload #8
/*     */     //   147: invokeinterface name : ()Ljava/lang/String;
/*     */     //   152: goto -> 174
/*     */     //   155: new java/lang/StringBuilder
/*     */     //   158: dup
/*     */     //   159: invokespecial <init> : ()V
/*     */     //   162: ldc 'arg'
/*     */     //   164: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   167: iload_3
/*     */     //   168: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   171: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   174: astore #11
/*     */     //   176: aload #8
/*     */     //   178: ifnull -> 204
/*     */     //   181: aload #8
/*     */     //   183: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   188: invokevirtual length : ()I
/*     */     //   191: ifle -> 204
/*     */     //   194: aload #8
/*     */     //   196: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   201: goto -> 206
/*     */     //   204: ldc ''
/*     */     //   206: astore #12
/*     */     //   208: aload_0
/*     */     //   209: aload_1
/*     */     //   210: iload_3
/*     */     //   211: invokespecial collectJAXBAnnotations : (Ljava/lang/Object;I)Ljava/util/List;
/*     */     //   214: astore #13
/*     */     //   216: aload_0
/*     */     //   217: aload #13
/*     */     //   219: aload #11
/*     */     //   221: aload #12
/*     */     //   223: aload #10
/*     */     //   225: invokespecial processXmlElement : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   228: aload_0
/*     */     //   229: getfield factory : Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator$BeanMemberFactory;
/*     */     //   232: aload #10
/*     */     //   234: aload #11
/*     */     //   236: invokestatic getPropertyName : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   239: aload #13
/*     */     //   241: invokeinterface createWrapperBeanMember : (Ljava/lang/Object;Ljava/lang/String;Ljava/util/List;)Ljava/lang/Object;
/*     */     //   246: checkcast java/lang/Comparable
/*     */     //   249: astore #14
/*     */     //   251: aload_2
/*     */     //   252: aload #14
/*     */     //   254: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   259: pop
/*     */     //   260: iinc #6, 1
/*     */     //   263: goto -> 30
/*     */     //   266: aload_2
/*     */     //   267: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #153	-> 0
/*     */     //   #154	-> 8
/*     */     //   #156	-> 10
/*     */     //   #157	-> 44
/*     */     //   #158	-> 47
/*     */     //   #159	-> 67
/*     */     //   #160	-> 98
/*     */     //   #162	-> 101
/*     */     //   #168	-> 109
/*     */     //   #169	-> 127
/*     */     //   #171	-> 176
/*     */     //   #175	-> 208
/*     */     //   #178	-> 216
/*     */     //   #179	-> 228
/*     */     //   #181	-> 251
/*     */     //   #156	-> 260
/*     */     //   #183	-> 266
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   67	193	8	webParam	Ljavax/jws/WebParam;
/*     */     //   109	151	9	holderType	Ljava/lang/Object;
/*     */     //   127	133	10	paramType	Ljava/lang/Object;
/*     */     //   176	84	11	paramName	Ljava/lang/String;
/*     */     //   208	52	12	paramNamespace	Ljava/lang/String;
/*     */     //   216	44	13	jaxbAnnotation	Ljava/util/List;
/*     */     //   251	9	14	member	Ljava/lang/Comparable;
/*     */     //   44	216	7	param	Ljava/lang/Object;
/*     */     //   22	244	4	arr$	[Ljava/lang/Object;
/*     */     //   27	239	5	len$	I
/*     */     //   30	236	6	i$	I
/*     */     //   0	268	0	this	Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator;
/*     */     //   0	268	1	method	Ljava/lang/Object;
/*     */     //   8	260	2	requestMembers	Ljava/util/List;
/*     */     //   10	258	3	paramIndex	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   109	151	9	holderType	TT;
/*     */     //   127	133	10	paramType	TT;
/*     */     //   216	44	13	jaxbAnnotation	Ljava/util/List<Ljava/lang/annotation/Annotation;>;
/*     */     //   251	9	14	member	TA;
/*     */     //   44	216	7	param	TT;
/*     */     //   0	268	0	this	Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator<TT;TC;TM;TA;>;
/*     */     //   0	268	1	method	TM;
/*     */     //   8	260	2	requestMembers	Ljava/util/List<TA;>;
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
/*     */   public List<A> collectResponseBeanMembers(M method) {
/*     */     // Byte code:
/*     */     //   0: new java/util/ArrayList
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_2
/*     */     //   8: ldc 'return'
/*     */     //   10: astore_3
/*     */     //   11: ldc ''
/*     */     //   13: astore #4
/*     */     //   15: iconst_0
/*     */     //   16: istore #5
/*     */     //   18: aload_0
/*     */     //   19: getfield annReader : Lcom/sun/xml/bind/v2/model/annotation/AnnotationReader;
/*     */     //   22: ldc_w javax/jws/WebResult
/*     */     //   25: aload_1
/*     */     //   26: aconst_null
/*     */     //   27: invokeinterface getMethodAnnotation : (Ljava/lang/Class;Ljava/lang/Object;Lcom/sun/xml/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   32: checkcast javax/jws/WebResult
/*     */     //   35: astore #6
/*     */     //   37: aload #6
/*     */     //   39: ifnull -> 94
/*     */     //   42: aload #6
/*     */     //   44: invokeinterface name : ()Ljava/lang/String;
/*     */     //   49: invokevirtual length : ()I
/*     */     //   52: ifle -> 63
/*     */     //   55: aload #6
/*     */     //   57: invokeinterface name : ()Ljava/lang/String;
/*     */     //   62: astore_3
/*     */     //   63: aload #6
/*     */     //   65: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   70: invokevirtual length : ()I
/*     */     //   73: ifle -> 85
/*     */     //   76: aload #6
/*     */     //   78: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   83: astore #4
/*     */     //   85: aload #6
/*     */     //   87: invokeinterface header : ()Z
/*     */     //   92: istore #5
/*     */     //   94: aload_0
/*     */     //   95: aload_0
/*     */     //   96: getfield nav : Lcom/sun/xml/bind/v2/model/nav/Navigator;
/*     */     //   99: aload_1
/*     */     //   100: invokeinterface getReturnType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   105: invokevirtual getSafeType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   108: astore #7
/*     */     //   110: aload_0
/*     */     //   111: aload #7
/*     */     //   113: invokevirtual isVoidType : (Ljava/lang/Object;)Z
/*     */     //   116: ifne -> 166
/*     */     //   119: iload #5
/*     */     //   121: ifne -> 166
/*     */     //   124: aload_0
/*     */     //   125: aload_1
/*     */     //   126: invokespecial collectJAXBAnnotations : (Ljava/lang/Object;)Ljava/util/List;
/*     */     //   129: astore #8
/*     */     //   131: aload_0
/*     */     //   132: aload #8
/*     */     //   134: aload_3
/*     */     //   135: aload #4
/*     */     //   137: aload #7
/*     */     //   139: invokespecial processXmlElement : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   142: aload_2
/*     */     //   143: aload_0
/*     */     //   144: getfield factory : Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator$BeanMemberFactory;
/*     */     //   147: aload #7
/*     */     //   149: aload_3
/*     */     //   150: invokestatic getPropertyName : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   153: aload #8
/*     */     //   155: invokeinterface createWrapperBeanMember : (Ljava/lang/Object;Ljava/lang/String;Ljava/util/List;)Ljava/lang/Object;
/*     */     //   160: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   165: pop
/*     */     //   166: iconst_m1
/*     */     //   167: istore #8
/*     */     //   169: aload_0
/*     */     //   170: getfield nav : Lcom/sun/xml/bind/v2/model/nav/Navigator;
/*     */     //   173: aload_1
/*     */     //   174: invokeinterface getMethodParameters : (Ljava/lang/Object;)[Ljava/lang/Object;
/*     */     //   179: astore #9
/*     */     //   181: aload #9
/*     */     //   183: arraylength
/*     */     //   184: istore #10
/*     */     //   186: iconst_0
/*     */     //   187: istore #11
/*     */     //   189: iload #11
/*     */     //   191: iload #10
/*     */     //   193: if_icmpge -> 399
/*     */     //   196: aload #9
/*     */     //   198: iload #11
/*     */     //   200: aaload
/*     */     //   201: astore #12
/*     */     //   203: iinc #8, 1
/*     */     //   206: aload_0
/*     */     //   207: aload #12
/*     */     //   209: invokevirtual getHolderValueType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   212: astore #13
/*     */     //   214: aload_0
/*     */     //   215: getfield annReader : Lcom/sun/xml/bind/v2/model/annotation/AnnotationReader;
/*     */     //   218: ldc_w javax/jws/WebParam
/*     */     //   221: aload_1
/*     */     //   222: iload #8
/*     */     //   224: aconst_null
/*     */     //   225: invokeinterface getMethodParameterAnnotation : (Ljava/lang/Class;Ljava/lang/Object;ILcom/sun/xml/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   230: checkcast javax/jws/WebParam
/*     */     //   233: astore #14
/*     */     //   235: aload #13
/*     */     //   237: ifnull -> 393
/*     */     //   240: aload #14
/*     */     //   242: ifnull -> 258
/*     */     //   245: aload #14
/*     */     //   247: invokeinterface header : ()Z
/*     */     //   252: ifeq -> 258
/*     */     //   255: goto -> 393
/*     */     //   258: aload #14
/*     */     //   260: ifnull -> 286
/*     */     //   263: aload #14
/*     */     //   265: invokeinterface name : ()Ljava/lang/String;
/*     */     //   270: invokevirtual length : ()I
/*     */     //   273: ifle -> 286
/*     */     //   276: aload #14
/*     */     //   278: invokeinterface name : ()Ljava/lang/String;
/*     */     //   283: goto -> 306
/*     */     //   286: new java/lang/StringBuilder
/*     */     //   289: dup
/*     */     //   290: invokespecial <init> : ()V
/*     */     //   293: ldc 'arg'
/*     */     //   295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   298: iload #8
/*     */     //   300: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   303: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   306: astore #15
/*     */     //   308: aload #14
/*     */     //   310: ifnull -> 336
/*     */     //   313: aload #14
/*     */     //   315: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   320: invokevirtual length : ()I
/*     */     //   323: ifle -> 336
/*     */     //   326: aload #14
/*     */     //   328: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   333: goto -> 338
/*     */     //   336: ldc ''
/*     */     //   338: astore #16
/*     */     //   340: aload_0
/*     */     //   341: aload_1
/*     */     //   342: iload #8
/*     */     //   344: invokespecial collectJAXBAnnotations : (Ljava/lang/Object;I)Ljava/util/List;
/*     */     //   347: astore #17
/*     */     //   349: aload_0
/*     */     //   350: aload #17
/*     */     //   352: aload #15
/*     */     //   354: aload #16
/*     */     //   356: aload #13
/*     */     //   358: invokespecial processXmlElement : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   361: aload_0
/*     */     //   362: getfield factory : Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator$BeanMemberFactory;
/*     */     //   365: aload #13
/*     */     //   367: aload #15
/*     */     //   369: invokestatic getPropertyName : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   372: aload #17
/*     */     //   374: invokeinterface createWrapperBeanMember : (Ljava/lang/Object;Ljava/lang/String;Ljava/util/List;)Ljava/lang/Object;
/*     */     //   379: checkcast java/lang/Comparable
/*     */     //   382: astore #18
/*     */     //   384: aload_2
/*     */     //   385: aload #18
/*     */     //   387: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   392: pop
/*     */     //   393: iinc #11, 1
/*     */     //   396: goto -> 189
/*     */     //   399: aload_2
/*     */     //   400: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #197	-> 0
/*     */     //   #200	-> 8
/*     */     //   #201	-> 11
/*     */     //   #202	-> 15
/*     */     //   #203	-> 18
/*     */     //   #204	-> 37
/*     */     //   #205	-> 42
/*     */     //   #206	-> 55
/*     */     //   #208	-> 63
/*     */     //   #209	-> 76
/*     */     //   #211	-> 85
/*     */     //   #213	-> 94
/*     */     //   #214	-> 110
/*     */     //   #215	-> 124
/*     */     //   #216	-> 131
/*     */     //   #217	-> 142
/*     */     //   #221	-> 166
/*     */     //   #222	-> 169
/*     */     //   #223	-> 203
/*     */     //   #225	-> 206
/*     */     //   #226	-> 214
/*     */     //   #227	-> 235
/*     */     //   #228	-> 255
/*     */     //   #231	-> 258
/*     */     //   #233	-> 308
/*     */     //   #235	-> 340
/*     */     //   #236	-> 349
/*     */     //   #237	-> 361
/*     */     //   #239	-> 384
/*     */     //   #222	-> 393
/*     */     //   #242	-> 399
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   131	35	8	jaxbRespAnnotations	Ljava/util/List;
/*     */     //   214	179	13	paramType	Ljava/lang/Object;
/*     */     //   235	158	14	webParam	Ljavax/jws/WebParam;
/*     */     //   308	85	15	paramName	Ljava/lang/String;
/*     */     //   340	53	16	paramNamespace	Ljava/lang/String;
/*     */     //   349	44	17	jaxbAnnotation	Ljava/util/List;
/*     */     //   384	9	18	member	Ljava/lang/Comparable;
/*     */     //   203	190	12	param	Ljava/lang/Object;
/*     */     //   181	218	9	arr$	[Ljava/lang/Object;
/*     */     //   186	213	10	len$	I
/*     */     //   189	210	11	i$	I
/*     */     //   0	401	0	this	Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator;
/*     */     //   0	401	1	method	Ljava/lang/Object;
/*     */     //   8	393	2	responseMembers	Ljava/util/List;
/*     */     //   11	390	3	responseElementName	Ljava/lang/String;
/*     */     //   15	386	4	responseNamespace	Ljava/lang/String;
/*     */     //   18	383	5	isResultHeader	Z
/*     */     //   37	364	6	webResult	Ljavax/jws/WebResult;
/*     */     //   110	291	7	returnType	Ljava/lang/Object;
/*     */     //   169	232	8	paramIndex	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   131	35	8	jaxbRespAnnotations	Ljava/util/List<Ljava/lang/annotation/Annotation;>;
/*     */     //   214	179	13	paramType	TT;
/*     */     //   349	44	17	jaxbAnnotation	Ljava/util/List<Ljava/lang/annotation/Annotation;>;
/*     */     //   384	9	18	member	TA;
/*     */     //   203	190	12	param	TT;
/*     */     //   0	401	0	this	Lcom/sun/xml/ws/model/AbstractWrapperBeanGenerator<TT;TC;TM;TA;>;
/*     */     //   0	401	1	method	TM;
/*     */     //   8	393	2	responseMembers	Ljava/util/List<TA;>;
/*     */     //   110	291	7	returnType	TT;
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
/*     */   private void processXmlElement(List<Annotation> jaxb, String elemName, String elemNS, T type) {
/* 246 */     XmlElement elemAnn = null;
/* 247 */     for (Annotation a : jaxb) {
/* 248 */       if (a.annotationType() == XmlElement.class) {
/* 249 */         elemAnn = (XmlElement)a;
/* 250 */         jaxb.remove(a);
/*     */         break;
/*     */       } 
/*     */     } 
/* 254 */     String name = (elemAnn != null && !elemAnn.name().equals("##default")) ? elemAnn.name() : elemName;
/*     */ 
/*     */     
/* 257 */     String ns = (elemAnn != null && !elemAnn.namespace().equals("##default")) ? elemAnn.namespace() : elemNS;
/*     */ 
/*     */     
/* 260 */     boolean nillable = (this.nav.isArray(type) || (elemAnn != null && elemAnn.nillable()));
/*     */ 
/*     */     
/* 263 */     boolean required = (elemAnn != null && elemAnn.required());
/* 264 */     XmlElementHandler handler = new XmlElementHandler(name, ns, nillable, required);
/* 265 */     XmlElement elem = (XmlElement)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { XmlElement.class }, handler);
/* 266 */     jaxb.add(elem);
/*     */   }
/*     */   
/*     */   private static class XmlElementHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     private String name;
/*     */     private String namespace;
/*     */     private boolean nillable;
/*     */     private boolean required;
/*     */     
/*     */     XmlElementHandler(String name, String namespace, boolean nillable, boolean required) {
/* 278 */       this.name = name;
/* 279 */       this.namespace = namespace;
/* 280 */       this.nillable = nillable;
/* 281 */       this.required = required;
/*     */     }
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 285 */       String methodName = method.getName();
/* 286 */       if (methodName.equals("name"))
/* 287 */         return this.name; 
/* 288 */       if (methodName.equals("namespace"))
/* 289 */         return this.namespace; 
/* 290 */       if (methodName.equals("nillable"))
/* 291 */         return Boolean.valueOf(this.nillable); 
/* 292 */       if (methodName.equals("required")) {
/* 293 */         return Boolean.valueOf(this.required);
/*     */       }
/* 295 */       throw new WebServiceException("Not handling " + methodName);
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
/*     */   public Collection<A> collectExceptionBeanMembers(C exception) {
/* 316 */     return collectExceptionBeanMembers(exception, true);
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
/*     */   public Collection<A> collectExceptionBeanMembers(C exception, boolean decapitalize) {
/* 337 */     TreeMap<String, A> fields = new TreeMap<String, A>();
/* 338 */     getExceptionProperties(exception, fields, decapitalize);
/*     */ 
/*     */     
/* 341 */     XmlType xmlType = (XmlType)this.annReader.getClassAnnotation(XmlType.class, exception, null);
/* 342 */     if (xmlType != null) {
/* 343 */       String[] propOrder = xmlType.propOrder();
/*     */       
/* 345 */       if (propOrder.length > 0 && propOrder[0].length() != 0) {
/* 346 */         List<A> list = new ArrayList<A>();
/* 347 */         for (String prop : propOrder) {
/* 348 */           Comparable comparable = (Comparable)fields.get(prop);
/* 349 */           if (comparable != null) {
/* 350 */             list.add((A)comparable);
/*     */           } else {
/* 352 */             throw new WebServiceException("Exception " + exception + " has @XmlType and its propOrder contains unknown property " + prop);
/*     */           } 
/*     */         } 
/*     */         
/* 356 */         return list;
/*     */       } 
/*     */     } 
/*     */     
/* 360 */     return fields.values();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getExceptionProperties(C exception, TreeMap<String, A> fields, boolean decapitalize) {
/* 365 */     C sc = (C)this.nav.getSuperClass(exception);
/* 366 */     if (sc != null) {
/* 367 */       getExceptionProperties(sc, fields, decapitalize);
/*     */     }
/* 369 */     Collection<? extends M> methods = this.nav.getDeclaredMethods(exception);
/*     */     
/* 371 */     for (M method : methods) {
/*     */ 
/*     */ 
/*     */       
/* 375 */       if (!this.nav.isPublicMethod(method) || (this.nav.isStaticMethod(method) && this.nav.isFinalMethod(method))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 380 */       if (!this.nav.isPublicMethod(method)) {
/*     */         continue;
/*     */       }
/*     */       
/* 384 */       String name = this.nav.getMethodName(method);
/*     */       
/* 386 */       if ((!name.startsWith("get") && !name.startsWith("is")) || skipProperties.contains(name) || name.equals("get") || name.equals("is")) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 392 */       T returnType = getSafeType((T)this.nav.getReturnType(method));
/* 393 */       if ((this.nav.getMethodParameters(method)).length == 0) {
/* 394 */         String fieldName = name.startsWith("get") ? name.substring(3) : name.substring(2);
/* 395 */         if (decapitalize) fieldName = StringUtils.decapitalize(fieldName); 
/* 396 */         fields.put(fieldName, this.factory.createWrapperBeanMember(returnType, fieldName, Collections.emptyList()));
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
/*     */   private static String getPropertyName(String name) {
/* 408 */     String propertyName = BindingHelper.mangleNameToVariableName(name);
/*     */ 
/*     */     
/* 411 */     return getJavaReservedVarialbeName(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String getJavaReservedVarialbeName(@NotNull String name) {
/* 420 */     String reservedName = reservedWords.get(name);
/* 421 */     return (reservedName == null) ? name : reservedName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 427 */   private static final Map<String, String> reservedWords = new HashMap<String, String>(); static {
/* 428 */     reservedWords.put("abstract", "_abstract");
/* 429 */     reservedWords.put("assert", "_assert");
/* 430 */     reservedWords.put("boolean", "_boolean");
/* 431 */     reservedWords.put("break", "_break");
/* 432 */     reservedWords.put("byte", "_byte");
/* 433 */     reservedWords.put("case", "_case");
/* 434 */     reservedWords.put("catch", "_catch");
/* 435 */     reservedWords.put("char", "_char");
/* 436 */     reservedWords.put("class", "_class");
/* 437 */     reservedWords.put("const", "_const");
/* 438 */     reservedWords.put("continue", "_continue");
/* 439 */     reservedWords.put("default", "_default");
/* 440 */     reservedWords.put("do", "_do");
/* 441 */     reservedWords.put("double", "_double");
/* 442 */     reservedWords.put("else", "_else");
/* 443 */     reservedWords.put("extends", "_extends");
/* 444 */     reservedWords.put("false", "_false");
/* 445 */     reservedWords.put("final", "_final");
/* 446 */     reservedWords.put("finally", "_finally");
/* 447 */     reservedWords.put("float", "_float");
/* 448 */     reservedWords.put("for", "_for");
/* 449 */     reservedWords.put("goto", "_goto");
/* 450 */     reservedWords.put("if", "_if");
/* 451 */     reservedWords.put("implements", "_implements");
/* 452 */     reservedWords.put("import", "_import");
/* 453 */     reservedWords.put("instanceof", "_instanceof");
/* 454 */     reservedWords.put("int", "_int");
/* 455 */     reservedWords.put("interface", "_interface");
/* 456 */     reservedWords.put("long", "_long");
/* 457 */     reservedWords.put("native", "_native");
/* 458 */     reservedWords.put("new", "_new");
/* 459 */     reservedWords.put("null", "_null");
/* 460 */     reservedWords.put("package", "_package");
/* 461 */     reservedWords.put("private", "_private");
/* 462 */     reservedWords.put("protected", "_protected");
/* 463 */     reservedWords.put("public", "_public");
/* 464 */     reservedWords.put("return", "_return");
/* 465 */     reservedWords.put("short", "_short");
/* 466 */     reservedWords.put("static", "_static");
/* 467 */     reservedWords.put("strictfp", "_strictfp");
/* 468 */     reservedWords.put("super", "_super");
/* 469 */     reservedWords.put("switch", "_switch");
/* 470 */     reservedWords.put("synchronized", "_synchronized");
/* 471 */     reservedWords.put("this", "_this");
/* 472 */     reservedWords.put("throw", "_throw");
/* 473 */     reservedWords.put("throws", "_throws");
/* 474 */     reservedWords.put("transient", "_transient");
/* 475 */     reservedWords.put("true", "_true");
/* 476 */     reservedWords.put("try", "_try");
/* 477 */     reservedWords.put("void", "_void");
/* 478 */     reservedWords.put("volatile", "_volatile");
/* 479 */     reservedWords.put("while", "_while");
/* 480 */     reservedWords.put("enum", "_enum");
/*     */   }
/*     */   
/*     */   protected abstract T getSafeType(T paramT);
/*     */   
/*     */   protected abstract T getHolderValueType(T paramT);
/*     */   
/*     */   protected abstract boolean isVoidType(T paramT);
/*     */   
/*     */   public static interface BeanMemberFactory<T, A> {
/*     */     A createWrapperBeanMember(T param1T, String param1String, List<Annotation> param1List);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\AbstractWrapperBeanGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */