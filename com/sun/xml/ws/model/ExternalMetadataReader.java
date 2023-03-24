/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.oracle.xmlns.webservices.jaxws_databinding.ExistingAnnotationsType;
/*     */ import com.oracle.xmlns.webservices.jaxws_databinding.JavaMethod;
/*     */ import com.oracle.xmlns.webservices.jaxws_databinding.JavaParam;
/*     */ import com.oracle.xmlns.webservices.jaxws_databinding.JavaWsdlMappingType;
/*     */ import com.oracle.xmlns.webservices.jaxws_databinding.ObjectFactory;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBResult;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class ExternalMetadataReader
/*     */   extends ReflectAnnotationReader
/*     */ {
/*     */   private static final String NAMESPACE_WEBLOGIC_WSEE_DATABINDING = "http://xmlns.oracle.com/weblogic/weblogic-wsee-databinding";
/*     */   private static final String NAMESPACE_JAXWS_RI_EXTERNAL_METADATA = "http://xmlns.oracle.com/webservices/jaxws-databinding";
/*  97 */   private Map<String, JavaWsdlMappingType> readers = new HashMap<String, JavaWsdlMappingType>();
/*     */ 
/*     */   
/*     */   public ExternalMetadataReader(Collection<File> files, Collection<String> resourcePaths, ClassLoader classLoader, boolean xsdValidation) {
/* 101 */     if (files != null) {
/* 102 */       for (File file : files) {
/*     */         try {
/* 104 */           String namespace = Util.documentRootNamespace(newSource(file));
/* 105 */           JavaWsdlMappingType externalMapping = parseMetadata(xsdValidation, newSource(file), namespace);
/* 106 */           this.readers.put(externalMapping.getJavaTypeName(), externalMapping);
/* 107 */         } catch (Exception e) {
/* 108 */           throw new RuntimeModelerException("runtime.modeler.external.metadata.unable.to.read", new Object[] { file.getAbsolutePath() });
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 113 */     if (resourcePaths != null) {
/* 114 */       for (String resourcePath : resourcePaths) {
/*     */         try {
/* 116 */           String namespace = Util.documentRootNamespace(newSource(resourcePath, classLoader));
/* 117 */           JavaWsdlMappingType externalMapping = parseMetadata(xsdValidation, newSource(resourcePath, classLoader), namespace);
/* 118 */           this.readers.put(externalMapping.getJavaTypeName(), externalMapping);
/* 119 */         } catch (Exception e) {
/* 120 */           throw new RuntimeModelerException("runtime.modeler.external.metadata.unable.to.read", new Object[] { resourcePath });
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private StreamSource newSource(String resourcePath, ClassLoader classLoader) {
/* 127 */     InputStream is = classLoader.getResourceAsStream(resourcePath);
/* 128 */     return new StreamSource(is);
/*     */   }
/*     */   
/*     */   private JavaWsdlMappingType parseMetadata(boolean xsdValidation, StreamSource source, String namespace) throws JAXBException, IOException, TransformerException {
/* 132 */     if ("http://xmlns.oracle.com/weblogic/weblogic-wsee-databinding".equals(namespace))
/* 133 */       return Util.transform(source); 
/* 134 */     if ("http://xmlns.oracle.com/webservices/jaxws-databinding".equals(namespace)) {
/* 135 */       return Util.read(source, xsdValidation);
/*     */     }
/* 137 */     throw new RuntimeModelerException("runtime.modeler.external.metadata.unsupported.schema", new Object[] { namespace, Arrays.<String>asList(new String[] { "http://xmlns.oracle.com/weblogic/weblogic-wsee-databinding", "http://xmlns.oracle.com/webservices/jaxws-databinding" }).toString() });
/*     */   }
/*     */ 
/*     */   
/*     */   private StreamSource newSource(File file) {
/*     */     try {
/* 143 */       return new StreamSource(new FileInputStream(file));
/* 144 */     } catch (FileNotFoundException e) {
/* 145 */       throw new RuntimeModelerException("runtime.modeler.external.metadata.unable.to.read", new Object[] { file.getAbsolutePath() });
/*     */     } 
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(Class<A> annType, Class<?> cls) {
/* 150 */     JavaWsdlMappingType r = reader(cls);
/* 151 */     return (r == null) ? super.<A>getAnnotation(annType, cls) : (A)Util.<Annotation>annotation(r, annType);
/*     */   }
/*     */   
/*     */   private JavaWsdlMappingType reader(Class<?> cls) {
/* 155 */     return this.readers.get(cls.getName());
/*     */   }
/*     */   
/*     */   Annotation[] getAnnotations(List<Object> objects) {
/* 159 */     ArrayList<Annotation> list = new ArrayList<Annotation>();
/* 160 */     for (Object a : objects) {
/* 161 */       if (Annotation.class.isInstance(a)) {
/* 162 */         list.add(Annotation.class.cast(a));
/*     */       }
/*     */     } 
/* 165 */     return list.<Annotation>toArray(new Annotation[list.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations(final Class<?> c) {
/* 170 */     Merger<Annotation[]> merger = new Merger<Annotation[]>(reader(c)) {
/*     */         Annotation[] reflection() {
/* 172 */           return ExternalMetadataReader.this.getAnnotations(c);
/*     */         }
/*     */         
/*     */         Annotation[] external() {
/* 176 */           return ExternalMetadataReader.this.getAnnotations(this.reader.getClassAnnotation());
/*     */         }
/*     */       };
/* 179 */     return merger.merge();
/*     */   }
/*     */   
/*     */   public Annotation[] getAnnotations(final Method m) {
/* 183 */     Merger<Annotation[]> merger = new Merger<Annotation[]>(reader(m.getDeclaringClass())) {
/*     */         Annotation[] reflection() {
/* 185 */           return ExternalMetadataReader.this.getAnnotations(m);
/*     */         }
/*     */         
/*     */         Annotation[] external() {
/* 189 */           JavaMethod jm = ExternalMetadataReader.this.getJavaMethod(m, this.reader);
/* 190 */           return (jm == null) ? new Annotation[0] : ExternalMetadataReader.this.getAnnotations(jm.getMethodAnnotation());
/*     */         }
/*     */       };
/* 193 */     return merger.merge();
/*     */   }
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A getAnnotation(final Class<A> annType, final Method m) {
/* 198 */     Merger<Annotation> merger = new Merger<Annotation>(reader(m.getDeclaringClass())) {
/*     */         Annotation reflection() {
/* 200 */           return (Annotation)ExternalMetadataReader.this.getAnnotation(annType, m);
/*     */         }
/*     */         
/*     */         Annotation external() {
/* 204 */           JavaMethod jm = ExternalMetadataReader.this.getJavaMethod(m, this.reader);
/* 205 */           return ExternalMetadataReader.Util.<Annotation>annotation(jm, annType);
/*     */         }
/*     */       };
/* 208 */     return (A)merger.merge();
/*     */   }
/*     */   
/*     */   public Annotation[][] getParameterAnnotations(final Method m) {
/* 212 */     Merger<Annotation[][]> merger = new Merger<Annotation[][]>(reader(m.getDeclaringClass())) {
/*     */         Annotation[][] reflection() {
/* 214 */           return ExternalMetadataReader.this.getParameterAnnotations(m);
/*     */         }
/*     */         
/*     */         Annotation[][] external() {
/* 218 */           JavaMethod jm = ExternalMetadataReader.this.getJavaMethod(m, this.reader);
/* 219 */           Annotation[][] a = m.getParameterAnnotations();
/* 220 */           for (int i = 0; i < (m.getParameterTypes()).length; i++) {
/* 221 */             if (jm != null) {
/* 222 */               JavaParam jp = jm.getJavaParams().getJavaParam().get(i);
/* 223 */               a[i] = ExternalMetadataReader.this.getAnnotations(jp.getParamAnnotation());
/*     */             } 
/* 225 */           }  return a;
/*     */         }
/*     */       };
/* 228 */     return merger.merge();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getProperties(Map<String, Object> prop, Class<?> cls) {
/* 233 */     JavaWsdlMappingType r = reader(cls);
/*     */ 
/*     */     
/* 236 */     if (r == null || ExistingAnnotationsType.MERGE.equals(r.getExistingAnnotations())) {
/* 237 */       super.getProperties(prop, cls);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getProperties(Map<String, Object> prop, Method m) {
/* 245 */     JavaWsdlMappingType r = reader(m.getDeclaringClass());
/*     */ 
/*     */     
/* 248 */     if (r == null || ExistingAnnotationsType.MERGE.equals(r.getExistingAnnotations())) {
/* 249 */       super.getProperties(prop, m);
/*     */     }
/*     */     
/* 252 */     if (r != null) {
/* 253 */       JavaMethod jm = getJavaMethod(m, r);
/* 254 */       Element[] e = Util.annotation(jm);
/* 255 */       prop.put("eclipselink-oxm-xml.xml-element", findXmlElement(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getProperties(Map<String, Object> prop, Method m, int pos) {
/* 262 */     JavaWsdlMappingType r = reader(m.getDeclaringClass());
/*     */ 
/*     */     
/* 265 */     if (r == null || ExistingAnnotationsType.MERGE.equals(r.getExistingAnnotations())) {
/* 266 */       super.getProperties(prop, m, pos);
/*     */     }
/*     */     
/* 269 */     if (r != null) {
/* 270 */       JavaMethod jm = getJavaMethod(m, r);
/* 271 */       if (jm == null)
/* 272 */         return;  JavaParam jp = jm.getJavaParams().getJavaParam().get(pos);
/* 273 */       Element[] e = Util.annotation(jp);
/* 274 */       prop.put("eclipselink-oxm-xml.xml-element", findXmlElement(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   JavaMethod getJavaMethod(Method method, JavaWsdlMappingType r) {
/* 280 */     JavaWsdlMappingType.JavaMethods javaMethods = r.getJavaMethods();
/* 281 */     if (javaMethods == null) {
/* 282 */       return null;
/*     */     }
/*     */     
/* 285 */     List<JavaMethod> sameName = new ArrayList<JavaMethod>();
/* 286 */     for (JavaMethod jm : javaMethods.getJavaMethod()) {
/* 287 */       if (method.getName().equals(jm.getName())) {
/* 288 */         sameName.add(jm);
/*     */       }
/*     */     } 
/*     */     
/* 292 */     if (sameName.isEmpty()) {
/* 293 */       return null;
/*     */     }
/* 295 */     if (sameName.size() == 1) {
/* 296 */       return sameName.get(0);
/*     */     }
/* 298 */     Class<?>[] argCls = method.getParameterTypes();
/* 299 */     for (JavaMethod jm : sameName) {
/* 300 */       JavaMethod.JavaParams params = jm.getJavaParams();
/* 301 */       if (params != null && params.getJavaParam() != null && params.getJavaParam().size() == argCls.length) {
/* 302 */         int count = 0;
/* 303 */         for (int i = 0; i < argCls.length; i++) {
/* 304 */           JavaParam jp = params.getJavaParam().get(i);
/* 305 */           if (argCls[i].getName().equals(jp.getJavaType())) {
/* 306 */             count++;
/*     */           }
/*     */         } 
/* 309 */         if (count == argCls.length) {
/* 310 */           return jm;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 316 */     return null;
/*     */   }
/*     */   
/*     */   Element findXmlElement(Element[] xa) {
/* 320 */     if (xa == null) return null; 
/* 321 */     for (Element e : xa) {
/* 322 */       if (e.getLocalName().equals("java-type")) return e; 
/* 323 */       if (e.getLocalName().equals("xml-element")) return e; 
/*     */     } 
/* 325 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Merger<T>
/*     */   {
/*     */     JavaWsdlMappingType reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Merger(JavaWsdlMappingType r) {
/* 344 */       this.reader = r;
/*     */     }
/*     */ 
/*     */     
/*     */     abstract T reflection();
/*     */     
/*     */     abstract T external();
/*     */     
/*     */     T merge() {
/* 353 */       T reflection = reflection();
/* 354 */       if (this.reader == null) {
/* 355 */         return reflection;
/*     */       }
/*     */       
/* 358 */       T external = external();
/* 359 */       if (!ExistingAnnotationsType.MERGE.equals(this.reader.getExistingAnnotations())) {
/* 360 */         return external;
/*     */       }
/*     */       
/* 363 */       if (reflection instanceof Annotation)
/* 364 */         return (T)doMerge((Annotation)reflection, (Annotation)external); 
/* 365 */       if (reflection instanceof Annotation[][]) {
/* 366 */         return (T)doMerge((Annotation[][])reflection, (Annotation[][])external);
/*     */       }
/* 368 */       return (T)doMerge((Annotation[])reflection, (Annotation[])external);
/*     */     }
/*     */ 
/*     */     
/*     */     private Annotation doMerge(Annotation reflection, Annotation external) {
/* 373 */       return (external != null) ? external : reflection;
/*     */     }
/*     */     
/*     */     private Annotation[][] doMerge(Annotation[][] reflection, Annotation[][] external) {
/* 377 */       for (int i = 0; i < reflection.length; i++) {
/* 378 */         reflection[i] = doMerge(reflection[i], (external.length > i) ? external[i] : null);
/*     */       }
/* 380 */       return reflection;
/*     */     }
/*     */     
/*     */     private Annotation[] doMerge(Annotation[] annotations, Annotation[] externalAnnotations) {
/* 384 */       HashMap<String, Annotation> mergeMap = new HashMap<String, Annotation>();
/* 385 */       if (annotations != null) {
/* 386 */         for (Annotation reflectionAnnotation : annotations) {
/* 387 */           mergeMap.put(reflectionAnnotation.annotationType().getName(), reflectionAnnotation);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 392 */       if (externalAnnotations != null) {
/* 393 */         for (Annotation externalAnnotation : externalAnnotations) {
/* 394 */           mergeMap.put(externalAnnotation.annotationType().getName(), externalAnnotation);
/*     */         }
/*     */       }
/* 397 */       Collection<Annotation> values = mergeMap.values();
/* 398 */       int size = values.size();
/* 399 */       return (size == 0) ? null : values.<Annotation>toArray(new Annotation[size]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Util
/*     */   {
/*     */     static final String SchemaFileName = "jaxws-databinding.xsd";
/*     */     static Schema schema;
/*     */     static JAXBContext jaxbContext;
/*     */     
/*     */     static {
/* 411 */       SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/*     */       try {
/* 413 */         URL xsdUrl = Util.class.getClassLoader().getResource("jaxws-databinding.xsd");
/* 414 */         if (xsdUrl != null) {
/* 415 */           schema = sf.newSchema(xsdUrl);
/*     */         }
/* 417 */       } catch (SAXException e1) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 422 */       Class[] cls = { ObjectFactory.class };
/*     */       try {
/* 424 */         jaxbContext = JAXBContext.newInstance(cls);
/* 425 */       } catch (JAXBException e) {
/* 426 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public static JavaWsdlMappingType read(Source src, boolean xsdValidation) throws IOException, JAXBException {
/*     */       try {
/* 433 */         Unmarshaller um = jaxbContext.createUnmarshaller();
/* 434 */         if (xsdValidation) {
/* 435 */           if (schema == null);
/*     */ 
/*     */           
/* 438 */           um.setSchema(schema);
/*     */         } 
/* 440 */         Object o = um.unmarshal(src);
/* 441 */         return getJavaWsdlMapping(o);
/* 442 */       } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */         
/* 446 */         URL url = new URL(src.getSystemId());
/* 447 */         Source s = new StreamSource(url.openStream());
/* 448 */         Unmarshaller um = jaxbContext.createUnmarshaller();
/* 449 */         if (xsdValidation) {
/* 450 */           if (schema == null);
/*     */ 
/*     */           
/* 453 */           um.setSchema(schema);
/*     */         } 
/* 455 */         Object o = um.unmarshal(s);
/* 456 */         return getJavaWsdlMapping(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public static JavaWsdlMappingType transform(Source src) throws TransformerException, JAXBException {
/* 461 */       Source xsl = new StreamSource(Util.class.getResourceAsStream("/jaxws-databinding-translate-namespaces.xml"));
/* 462 */       JAXBResult result = new JAXBResult(jaxbContext);
/* 463 */       Transformer transformer = TransformerFactory.newInstance().newTemplates(xsl).newTransformer();
/*     */       
/* 465 */       transformer.transform(src, result);
/* 466 */       return getJavaWsdlMapping(result.getResult());
/*     */     }
/*     */ 
/*     */     
/*     */     static JavaWsdlMappingType getJavaWsdlMapping(Object o) {
/* 471 */       Object val = (o instanceof JAXBElement) ? ((JAXBElement)o).getValue() : o;
/* 472 */       if (val instanceof JavaWsdlMappingType) return (JavaWsdlMappingType)val;
/*     */ 
/*     */ 
/*     */       
/* 476 */       return null;
/*     */     }
/*     */     
/*     */     static <T> T findInstanceOf(Class<T> type, List<Object> objects) {
/* 480 */       for (Object o : objects) {
/* 481 */         if (type.isInstance(o)) {
/* 482 */           return type.cast(o);
/*     */         }
/*     */       } 
/* 485 */       return null;
/*     */     }
/*     */     
/*     */     public static <T> T annotation(JavaWsdlMappingType jwse, Class<T> anntype) {
/* 489 */       if (jwse == null || jwse.getClassAnnotation() == null) {
/* 490 */         return null;
/*     */       }
/* 492 */       return findInstanceOf(anntype, jwse.getClassAnnotation());
/*     */     }
/*     */     
/*     */     public static <T> T annotation(JavaMethod jm, Class<T> anntype) {
/* 496 */       if (jm == null || jm.getMethodAnnotation() == null) {
/* 497 */         return null;
/*     */       }
/* 499 */       return findInstanceOf(anntype, jm.getMethodAnnotation());
/*     */     }
/*     */     
/*     */     public static <T> T annotation(JavaParam jp, Class<T> anntype) {
/* 503 */       if (jp == null || jp.getParamAnnotation() == null) {
/* 504 */         return null;
/*     */       }
/* 506 */       return findInstanceOf(anntype, jp.getParamAnnotation());
/*     */     }
/*     */     
/*     */     public static Element[] annotation(JavaMethod jm) {
/* 510 */       if (jm == null || jm.getMethodAnnotation() == null) {
/* 511 */         return null;
/*     */       }
/* 513 */       return findElements(jm.getMethodAnnotation());
/*     */     }
/*     */     
/*     */     public static Element[] annotation(JavaParam jp) {
/* 517 */       if (jp == null || jp.getParamAnnotation() == null) {
/* 518 */         return null;
/*     */       }
/* 520 */       return findElements(jp.getParamAnnotation());
/*     */     }
/*     */     
/*     */     private static Element[] findElements(List<Object> objects) {
/* 524 */       List<Element> elems = new ArrayList<Element>();
/* 525 */       for (Object o : objects) {
/* 526 */         if (o instanceof Element) {
/* 527 */           elems.add((Element)o);
/*     */         }
/*     */       } 
/* 530 */       return elems.<Element>toArray(new Element[elems.size()]);
/*     */     }
/*     */     
/*     */     static String documentRootNamespace(Source src) throws XMLStreamException {
/* 534 */       XMLInputFactory factory = XMLInputFactory.newInstance();
/* 535 */       XMLStreamReader streamReader = factory.createXMLStreamReader(src);
/* 536 */       XMLStreamReaderUtil.nextElementContent(streamReader);
/* 537 */       String namespaceURI = streamReader.getName().getNamespaceURI();
/* 538 */       XMLStreamReaderUtil.close(streamReader);
/* 539 */       return namespaceURI;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\ExternalMetadataReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */