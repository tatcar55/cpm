/*      */ package com.sun.xml.bind.v2.schemagen;
/*      */ 
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Nullable;
/*      */ import com.sun.xml.bind.Util;
/*      */ import com.sun.xml.bind.api.CompositeStructure;
/*      */ import com.sun.xml.bind.api.ErrorListener;
/*      */ import com.sun.xml.bind.v2.TODO;
/*      */ import com.sun.xml.bind.v2.model.core.Adapter;
/*      */ import com.sun.xml.bind.v2.model.core.ArrayInfo;
/*      */ import com.sun.xml.bind.v2.model.core.AttributePropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.ClassInfo;
/*      */ import com.sun.xml.bind.v2.model.core.Element;
/*      */ import com.sun.xml.bind.v2.model.core.ElementInfo;
/*      */ import com.sun.xml.bind.v2.model.core.ElementPropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.EnumConstant;
/*      */ import com.sun.xml.bind.v2.model.core.EnumLeafInfo;
/*      */ import com.sun.xml.bind.v2.model.core.ID;
/*      */ import com.sun.xml.bind.v2.model.core.MapPropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.MaybeElement;
/*      */ import com.sun.xml.bind.v2.model.core.NonElement;
/*      */ import com.sun.xml.bind.v2.model.core.NonElementRef;
/*      */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*      */ import com.sun.xml.bind.v2.model.core.ReferencePropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*      */ import com.sun.xml.bind.v2.model.core.TypeInfoSet;
/*      */ import com.sun.xml.bind.v2.model.core.TypeRef;
/*      */ import com.sun.xml.bind.v2.model.core.ValuePropertyInfo;
/*      */ import com.sun.xml.bind.v2.model.core.WildcardMode;
/*      */ import com.sun.xml.bind.v2.model.impl.ClassInfoImpl;
/*      */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*      */ import com.sun.xml.bind.v2.runtime.SwaRefAdapter;
/*      */ import com.sun.xml.bind.v2.schemagen.episode.Bindings;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.Any;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.AttrDecls;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.AttributeType;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ComplexExtension;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ComplexType;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ComplexTypeHost;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ContentModelContainer;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ExplicitGroup;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.Import;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.List;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.LocalAttribute;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.LocalElement;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.Occurs;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.Schema;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.SimpleExtension;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.SimpleRestriction;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.SimpleType;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.SimpleTypeHost;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.TopLevelAttribute;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.TopLevelElement;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.TypeDefParticle;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.TypeHost;
/*      */ import com.sun.xml.bind.v2.util.CollisionCheckStack;
/*      */ import com.sun.xml.bind.v2.util.StackRecorder;
/*      */ import com.sun.xml.txw2.TXW;
/*      */ import com.sun.xml.txw2.TxwException;
/*      */ import com.sun.xml.txw2.TypedXmlWriter;
/*      */ import com.sun.xml.txw2.output.ResultFactory;
/*      */ import com.sun.xml.txw2.output.XmlSerializer;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Writer;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.activation.MimeType;
/*      */ import javax.xml.bind.SchemaOutputResolver;
/*      */ import javax.xml.bind.annotation.XmlElement;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.xml.sax.SAXParseException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class XmlSchemaGenerator<T, C, F, M>
/*      */ {
/*  147 */   private static final Logger logger = Util.getClassLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   private final Map<String, Namespace> namespaces = new TreeMap<String, Namespace>(NAMESPACE_COMPARATOR);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ErrorListener errorListener;
/*      */ 
/*      */ 
/*      */   
/*      */   private Navigator<T, C, F, M> navigator;
/*      */ 
/*      */ 
/*      */   
/*      */   private final TypeInfoSet<T, C, F, M> types;
/*      */ 
/*      */ 
/*      */   
/*      */   private final NonElement<T, C> stringType;
/*      */ 
/*      */ 
/*      */   
/*      */   private final NonElement<T, C> anyType;
/*      */ 
/*      */ 
/*      */   
/*  183 */   private final CollisionCheckStack<ClassInfo<T, C>> collisionChecker = new CollisionCheckStack();
/*      */   
/*      */   public XmlSchemaGenerator(Navigator<T, C, F, M> navigator, TypeInfoSet<T, C, F, M> types) {
/*  186 */     this.navigator = navigator;
/*  187 */     this.types = types;
/*      */     
/*  189 */     this.stringType = types.getTypeInfo(navigator.ref(String.class));
/*  190 */     this.anyType = types.getAnyTypeInfo();
/*      */ 
/*      */     
/*  193 */     for (ClassInfo<T, C> ci : (Iterable<ClassInfo<T, C>>)types.beans().values())
/*  194 */       add(ci); 
/*  195 */     for (ElementInfo<T, C> ei1 : (Iterable<ElementInfo<T, C>>)types.getElementMappings(null).values())
/*  196 */       add(ei1); 
/*  197 */     for (EnumLeafInfo<T, C> ei : (Iterable<EnumLeafInfo<T, C>>)types.enums().values())
/*  198 */       add(ei); 
/*  199 */     for (ArrayInfo<T, C> a : (Iterable<ArrayInfo<T, C>>)types.arrays().values())
/*  200 */       add(a); 
/*      */   }
/*      */   
/*      */   private Namespace getNamespace(String uri) {
/*  204 */     Namespace n = this.namespaces.get(uri);
/*  205 */     if (n == null)
/*  206 */       this.namespaces.put(uri, n = new Namespace(uri)); 
/*  207 */     return n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(ClassInfo<T, C> clazz) {
/*  219 */     assert clazz != null;
/*      */     
/*  221 */     String nsUri = null;
/*      */     
/*  223 */     if (clazz.getClazz() == this.navigator.asDecl(CompositeStructure.class)) {
/*      */       return;
/*      */     }
/*  226 */     if (clazz.isElement()) {
/*      */       
/*  228 */       nsUri = clazz.getElementName().getNamespaceURI();
/*  229 */       Namespace ns = getNamespace(nsUri);
/*  230 */       ns.classes.add(clazz);
/*  231 */       ns.addDependencyTo(clazz.getTypeName());
/*      */ 
/*      */       
/*  234 */       add(clazz.getElementName(), false, (NonElement<T, C>)clazz);
/*      */     } 
/*      */     
/*  237 */     QName tn = clazz.getTypeName();
/*  238 */     if (tn != null) {
/*  239 */       nsUri = tn.getNamespaceURI();
/*      */     
/*      */     }
/*  242 */     else if (nsUri == null) {
/*      */       return;
/*      */     } 
/*      */     
/*  246 */     Namespace n = getNamespace(nsUri);
/*  247 */     n.classes.add(clazz);
/*      */ 
/*      */     
/*  250 */     for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)clazz.getProperties()) {
/*  251 */       n.processForeignNamespaces(p, 1);
/*  252 */       if (p instanceof AttributePropertyInfo) {
/*  253 */         AttributePropertyInfo<T, C> ap = (AttributePropertyInfo<T, C>)p;
/*  254 */         String aUri = ap.getXmlName().getNamespaceURI();
/*  255 */         if (aUri.length() > 0) {
/*      */           
/*  257 */           getNamespace(aUri).addGlobalAttribute(ap);
/*  258 */           n.addDependencyTo(ap.getXmlName());
/*      */         } 
/*      */       } 
/*  261 */       if (p instanceof ElementPropertyInfo) {
/*  262 */         ElementPropertyInfo<T, C> ep = (ElementPropertyInfo<T, C>)p;
/*  263 */         for (TypeRef<T, C> tref : (Iterable<TypeRef<T, C>>)ep.getTypes()) {
/*  264 */           String eUri = tref.getTagName().getNamespaceURI();
/*  265 */           if (eUri.length() > 0 && !eUri.equals(n.uri)) {
/*  266 */             getNamespace(eUri).addGlobalElement(tref);
/*  267 */             n.addDependencyTo(tref.getTagName());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  272 */       if (generateSwaRefAdapter(p)) {
/*  273 */         n.useSwaRef = true;
/*      */       }
/*  275 */       MimeType mimeType = p.getExpectedMimeType();
/*  276 */       if (mimeType != null) {
/*  277 */         n.useMimeNs = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  283 */     ClassInfo<T, C> bc = clazz.getBaseClass();
/*  284 */     if (bc != null) {
/*  285 */       add(bc);
/*  286 */       n.addDependencyTo(bc.getTypeName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(ElementInfo<T, C> elem) {
/*      */     ElementInfo ei;
/*  294 */     assert elem != null;
/*      */     
/*  296 */     boolean nillable = false;
/*      */     
/*  298 */     QName name = elem.getElementName();
/*  299 */     Namespace n = getNamespace(name.getNamespaceURI());
/*      */ 
/*      */     
/*  302 */     if (elem.getScope() != null) {
/*  303 */       ei = this.types.getElementInfo(elem.getScope().getClazz(), name);
/*      */     } else {
/*  305 */       ei = this.types.getElementInfo(null, name);
/*      */     } 
/*      */     
/*  308 */     XmlElement xmlElem = (XmlElement)ei.getProperty().readAnnotation(XmlElement.class);
/*      */     
/*  310 */     if (xmlElem == null) {
/*  311 */       nillable = false;
/*      */     } else {
/*  313 */       nillable = xmlElem.nillable();
/*      */     } 
/*      */     
/*  316 */     n.getClass(); n.elementDecls.put(name.getLocalPart(), new Namespace.ElementWithType(nillable, elem.getContentType()));
/*      */ 
/*      */     
/*  319 */     n.processForeignNamespaces((PropertyInfo<T, C>)elem.getProperty(), 1);
/*      */   }
/*      */   
/*      */   public void add(EnumLeafInfo<T, C> envm) {
/*  323 */     assert envm != null;
/*      */     
/*  325 */     String nsUri = null;
/*      */     
/*  327 */     if (envm.isElement()) {
/*      */       
/*  329 */       nsUri = envm.getElementName().getNamespaceURI();
/*  330 */       Namespace ns = getNamespace(nsUri);
/*  331 */       ns.enums.add(envm);
/*  332 */       ns.addDependencyTo(envm.getTypeName());
/*      */ 
/*      */       
/*  335 */       add(envm.getElementName(), false, (NonElement<T, C>)envm);
/*      */     } 
/*      */     
/*  338 */     QName typeName = envm.getTypeName();
/*  339 */     if (typeName != null) {
/*  340 */       nsUri = typeName.getNamespaceURI();
/*      */     }
/*  342 */     else if (nsUri == null) {
/*      */       return;
/*      */     } 
/*      */     
/*  346 */     Namespace n = getNamespace(nsUri);
/*  347 */     n.enums.add(envm);
/*      */ 
/*      */     
/*  350 */     n.addDependencyTo(envm.getBaseType().getTypeName());
/*      */   }
/*      */   
/*      */   public void add(ArrayInfo<T, C> a) {
/*  354 */     assert a != null;
/*      */     
/*  356 */     String namespaceURI = a.getTypeName().getNamespaceURI();
/*  357 */     Namespace n = getNamespace(namespaceURI);
/*  358 */     n.arrays.add(a);
/*      */ 
/*      */     
/*  361 */     n.addDependencyTo(a.getItemType().getTypeName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(QName tagName, boolean isNillable, NonElement<T, C> type) {
/*  375 */     if (type != null && type.getType() == this.navigator.ref(CompositeStructure.class)) {
/*      */       return;
/*      */     }
/*      */     
/*  379 */     Namespace n = getNamespace(tagName.getNamespaceURI());
/*  380 */     n.getClass(); n.elementDecls.put(tagName.getLocalPart(), new Namespace.ElementWithType(isNillable, type));
/*      */ 
/*      */     
/*  383 */     if (type != null) {
/*  384 */       n.addDependencyTo(type.getTypeName());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEpisodeFile(XmlSerializer out) {
/*  391 */     Bindings root = (Bindings)TXW.create(Bindings.class, out);
/*      */     
/*  393 */     if (this.namespaces.containsKey(""))
/*  394 */       root._namespace("http://java.sun.com/xml/ns/jaxb", "jaxb"); 
/*  395 */     root.version("2.1");
/*      */ 
/*      */ 
/*      */     
/*  399 */     for (Map.Entry<String, Namespace> e : this.namespaces.entrySet()) {
/*  400 */       String prefix; Bindings group = root.bindings();
/*      */ 
/*      */       
/*  403 */       String tns = e.getKey();
/*  404 */       if (!tns.equals("")) {
/*  405 */         group._namespace(tns, "tns");
/*  406 */         prefix = "tns:";
/*      */       } else {
/*  408 */         prefix = "";
/*      */       } 
/*      */       
/*  411 */       group.scd("x-schema::" + (tns.equals("") ? "" : "tns"));
/*  412 */       group.schemaBindings().map(false);
/*      */       
/*  414 */       for (ClassInfo<T, C> ci : (Iterable<ClassInfo<T, C>>)(e.getValue()).classes) {
/*  415 */         if (ci.getTypeName() == null)
/*      */           continue; 
/*  417 */         if (ci.getTypeName().getNamespaceURI().equals(tns)) {
/*  418 */           Bindings child = group.bindings();
/*  419 */           child.scd('~' + prefix + ci.getTypeName().getLocalPart());
/*  420 */           child.klass().ref(ci.getName());
/*      */         } 
/*      */         
/*  423 */         if (ci.isElement() && ci.getElementName().getNamespaceURI().equals(tns)) {
/*  424 */           Bindings child = group.bindings();
/*  425 */           child.scd(prefix + ci.getElementName().getLocalPart());
/*  426 */           child.klass().ref(ci.getName());
/*      */         } 
/*      */       } 
/*      */       
/*  430 */       for (EnumLeafInfo<T, C> en : (Iterable<EnumLeafInfo<T, C>>)(e.getValue()).enums) {
/*  431 */         if (en.getTypeName() == null)
/*      */           continue; 
/*  433 */         Bindings child = group.bindings();
/*  434 */         child.scd('~' + prefix + en.getTypeName().getLocalPart());
/*  435 */         child.klass().ref(this.navigator.getClassName(en.getClazz()));
/*      */       } 
/*      */       
/*  438 */       group.commit(true);
/*      */     } 
/*      */     
/*  441 */     root.commit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(SchemaOutputResolver resolver, ErrorListener errorListener) throws IOException {
/*  448 */     if (resolver == null) {
/*  449 */       throw new IllegalArgumentException();
/*      */     }
/*  451 */     if (logger.isLoggable(Level.FINE))
/*      */     {
/*  453 */       logger.log(Level.FINE, "Wrigin XML Schema for " + toString(), (Throwable)new StackRecorder());
/*      */     }
/*      */ 
/*      */     
/*  457 */     resolver = new FoolProofResolver(resolver);
/*  458 */     this.errorListener = errorListener;
/*      */     
/*  460 */     Map<String, String> schemaLocations = this.types.getSchemaLocations();
/*      */     
/*  462 */     Map<Namespace, Result> out = new HashMap<Namespace, Result>();
/*  463 */     Map<Namespace, String> systemIds = new HashMap<Namespace, String>();
/*      */ 
/*      */ 
/*      */     
/*  467 */     this.namespaces.remove("http://www.w3.org/2001/XMLSchema");
/*      */ 
/*      */ 
/*      */     
/*  471 */     for (Namespace n : this.namespaces.values()) {
/*  472 */       String schemaLocation = schemaLocations.get(n.uri);
/*  473 */       if (schemaLocation != null) {
/*  474 */         systemIds.put(n, schemaLocation); continue;
/*      */       } 
/*  476 */       Result output = resolver.createOutput(n.uri, "schema" + (out.size() + 1) + ".xsd");
/*  477 */       if (output != null) {
/*  478 */         out.put(n, output);
/*  479 */         systemIds.put(n, output.getSystemId());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  485 */     for (Map.Entry<Namespace, Result> e : out.entrySet()) {
/*  486 */       Result result = e.getValue();
/*  487 */       ((Namespace)e.getKey()).writeTo(result, systemIds);
/*  488 */       if (result instanceof StreamResult) {
/*  489 */         OutputStream outputStream = ((StreamResult)result).getOutputStream();
/*  490 */         if (outputStream != null) {
/*  491 */           outputStream.close(); continue;
/*      */         } 
/*  493 */         Writer writer = ((StreamResult)result).getWriter();
/*  494 */         if (writer != null) writer.close();
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Namespace
/*      */   {
/*      */     @NotNull
/*      */     final String uri;
/*      */ 
/*      */ 
/*      */     
/*  511 */     private final Set<Namespace> depends = new LinkedHashSet<Namespace>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean selfReference;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  521 */     private final Set<ClassInfo<T, C>> classes = new LinkedHashSet<ClassInfo<T, C>>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  526 */     private final Set<EnumLeafInfo<T, C>> enums = new LinkedHashSet<EnumLeafInfo<T, C>>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  531 */     private final Set<ArrayInfo<T, C>> arrays = new LinkedHashSet<ArrayInfo<T, C>>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  536 */     private final MultiMap<String, AttributePropertyInfo<T, C>> attributeDecls = new MultiMap<String, AttributePropertyInfo<T, C>>(null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  541 */     private final MultiMap<String, ElementDeclaration> elementDecls = new MultiMap<String, ElementDeclaration>(new ElementWithType(true, XmlSchemaGenerator.this.anyType));
/*      */ 
/*      */ 
/*      */     
/*      */     private Form attributeFormDefault;
/*      */ 
/*      */ 
/*      */     
/*      */     private Form elementFormDefault;
/*      */ 
/*      */     
/*      */     private boolean useSwaRef;
/*      */ 
/*      */     
/*      */     private boolean useMimeNs;
/*      */ 
/*      */ 
/*      */     
/*      */     public Namespace(String uri) {
/*  560 */       this.uri = uri;
/*  561 */       assert !XmlSchemaGenerator.access$1000(XmlSchemaGenerator.this).containsKey(uri);
/*  562 */       XmlSchemaGenerator.access$1000(XmlSchemaGenerator.this).put(uri, this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void processForeignNamespaces(PropertyInfo<T, C> p, int processingDepth) {
/*  574 */       for (TypeInfo<T, C> t : (Iterable<TypeInfo<T, C>>)p.ref()) {
/*  575 */         if (t instanceof ClassInfo && processingDepth > 0) {
/*  576 */           List<PropertyInfo> l = ((ClassInfo)t).getProperties();
/*  577 */           for (PropertyInfo<T, C> subp : l) {
/*  578 */             processForeignNamespaces(subp, --processingDepth);
/*      */           }
/*      */         } 
/*  581 */         if (t instanceof Element) {
/*  582 */           addDependencyTo(((Element)t).getElementName());
/*      */         }
/*  584 */         if (t instanceof NonElement) {
/*  585 */           addDependencyTo(((NonElement)t).getTypeName());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void addDependencyTo(@Nullable QName qname) {
/*  594 */       if (qname == null) {
/*      */         return;
/*      */       }
/*      */       
/*  598 */       String nsUri = qname.getNamespaceURI();
/*      */       
/*  600 */       if (nsUri.equals("http://www.w3.org/2001/XMLSchema")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  605 */       if (nsUri.equals(this.uri)) {
/*  606 */         this.selfReference = true;
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  611 */       this.depends.add(XmlSchemaGenerator.this.getNamespace(nsUri));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeTo(Result result, Map<Namespace, String> systemIds) throws IOException {
/*      */       try {
/*  622 */         Schema schema = (Schema)TXW.create(Schema.class, ResultFactory.createSerializer(result));
/*      */ 
/*      */         
/*  625 */         Map<String, String> xmlNs = XmlSchemaGenerator.this.types.getXmlNs(this.uri);
/*      */         
/*  627 */         for (Map.Entry<String, String> e : xmlNs.entrySet()) {
/*  628 */           schema._namespace(e.getValue(), e.getKey());
/*      */         }
/*      */         
/*  631 */         if (this.useSwaRef) {
/*  632 */           schema._namespace("http://ws-i.org/profiles/basic/1.1/xsd", "swaRef");
/*      */         }
/*  634 */         if (this.useMimeNs) {
/*  635 */           schema._namespace("http://www.w3.org/2005/05/xmlmime", "xmime");
/*      */         }
/*  637 */         this.attributeFormDefault = Form.get(XmlSchemaGenerator.this.types.getAttributeFormDefault(this.uri));
/*  638 */         this.attributeFormDefault.declare("attributeFormDefault", schema);
/*      */         
/*  640 */         this.elementFormDefault = Form.get(XmlSchemaGenerator.this.types.getElementFormDefault(this.uri));
/*      */         
/*  642 */         this.elementFormDefault.declare("elementFormDefault", schema);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  647 */         if (!xmlNs.containsValue("http://www.w3.org/2001/XMLSchema") && !xmlNs.containsKey("xs"))
/*      */         {
/*  649 */           schema._namespace("http://www.w3.org/2001/XMLSchema", "xs"); } 
/*  650 */         schema.version("1.0");
/*      */         
/*  652 */         if (this.uri.length() != 0) {
/*  653 */           schema.targetNamespace(this.uri);
/*      */         }
/*      */ 
/*      */         
/*  657 */         for (Namespace ns : this.depends) {
/*  658 */           schema._namespace(ns.uri);
/*      */         }
/*      */         
/*  661 */         if (this.selfReference && this.uri.length() != 0)
/*      */         {
/*      */           
/*  664 */           schema._namespace(this.uri, "tns");
/*      */         }
/*      */         
/*  667 */         schema._pcdata("\n");
/*      */ 
/*      */         
/*  670 */         for (Namespace n : this.depends) {
/*  671 */           Import imp = schema._import();
/*  672 */           if (n.uri.length() != 0)
/*  673 */             imp.namespace(n.uri); 
/*  674 */           String refSystemId = systemIds.get(n);
/*  675 */           if (refSystemId != null && !refSystemId.equals(""))
/*      */           {
/*  677 */             imp.schemaLocation(XmlSchemaGenerator.relativize(refSystemId, result.getSystemId()));
/*      */           }
/*  679 */           schema._pcdata("\n");
/*      */         } 
/*  681 */         if (this.useSwaRef) {
/*  682 */           schema._import().namespace("http://ws-i.org/profiles/basic/1.1/xsd").schemaLocation("http://ws-i.org/profiles/basic/1.1/swaref.xsd");
/*      */         }
/*  684 */         if (this.useMimeNs) {
/*  685 */           schema._import().namespace("http://www.w3.org/2005/05/xmlmime").schemaLocation("http://www.w3.org/2005/05/xmlmime");
/*      */         }
/*      */ 
/*      */         
/*  689 */         for (Map.Entry<String, ElementDeclaration> e : this.elementDecls.entrySet()) {
/*  690 */           ((ElementDeclaration)e.getValue()).writeTo(e.getKey(), schema);
/*  691 */           schema._pcdata("\n");
/*      */         } 
/*  693 */         for (ClassInfo<T, C> c : this.classes) {
/*  694 */           if (c.getTypeName() == null) {
/*      */             continue;
/*      */           }
/*      */           
/*  698 */           if (this.uri.equals(c.getTypeName().getNamespaceURI()))
/*  699 */             writeClass(c, (TypeHost)schema); 
/*  700 */           schema._pcdata("\n");
/*      */         } 
/*  702 */         for (EnumLeafInfo<T, C> e : this.enums) {
/*  703 */           if (e.getTypeName() == null) {
/*      */             continue;
/*      */           }
/*      */           
/*  707 */           if (this.uri.equals(e.getTypeName().getNamespaceURI()))
/*  708 */             writeEnum(e, (SimpleTypeHost)schema); 
/*  709 */           schema._pcdata("\n");
/*      */         } 
/*  711 */         for (ArrayInfo<T, C> a : this.arrays) {
/*  712 */           writeArray(a, schema);
/*  713 */           schema._pcdata("\n");
/*      */         } 
/*  715 */         for (Map.Entry<String, AttributePropertyInfo<T, C>> e : this.attributeDecls.entrySet()) {
/*  716 */           TopLevelAttribute a = schema.attribute();
/*  717 */           a.name(e.getKey());
/*  718 */           if (e.getValue() == null) {
/*  719 */             writeTypeRef((TypeHost)a, XmlSchemaGenerator.this.stringType, "type");
/*      */           } else {
/*  721 */             writeAttributeTypeRef(e.getValue(), (AttributeType)a);
/*  722 */           }  schema._pcdata("\n");
/*      */         } 
/*      */ 
/*      */         
/*  726 */         schema.commit();
/*  727 */       } catch (TxwException e) {
/*  728 */         XmlSchemaGenerator.logger.log(Level.INFO, e.getMessage(), (Throwable)e);
/*  729 */         throw new IOException(e.getMessage());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeTypeRef(TypeHost th, NonElementRef<T, C> typeRef, String refAttName) {
/*  746 */       switch (typeRef.getSource().id()) {
/*      */         case LAX:
/*  748 */           th._attribute(refAttName, new QName("http://www.w3.org/2001/XMLSchema", "ID"));
/*      */           return;
/*      */         case SKIP:
/*  751 */           th._attribute(refAttName, new QName("http://www.w3.org/2001/XMLSchema", "IDREF"));
/*      */           return;
/*      */         
/*      */         case STRICT:
/*      */           break;
/*      */         default:
/*  757 */           throw new IllegalStateException();
/*      */       } 
/*      */ 
/*      */       
/*  761 */       MimeType mimeType = typeRef.getSource().getExpectedMimeType();
/*  762 */       if (mimeType != null) {
/*  763 */         th._attribute(new QName("http://www.w3.org/2005/05/xmlmime", "expectedContentTypes", "xmime"), mimeType.toString());
/*      */       }
/*      */ 
/*      */       
/*  767 */       if (XmlSchemaGenerator.this.generateSwaRefAdapter(typeRef)) {
/*  768 */         th._attribute(refAttName, new QName("http://ws-i.org/profiles/basic/1.1/xsd", "swaRef", "ref"));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  773 */       if (typeRef.getSource().getSchemaType() != null) {
/*  774 */         th._attribute(refAttName, typeRef.getSource().getSchemaType());
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  779 */       writeTypeRef(th, typeRef.getTarget(), refAttName);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeTypeRef(TypeHost th, NonElement<T, C> type, String refAttName) {
/*  795 */       Element e = null;
/*  796 */       if (type instanceof MaybeElement) {
/*  797 */         MaybeElement me = (MaybeElement)type;
/*  798 */         boolean isElement = me.isElement();
/*  799 */         if (isElement) e = me.asElement(); 
/*      */       } 
/*  801 */       if (type instanceof Element) {
/*  802 */         e = (Element)type;
/*      */       }
/*  804 */       if (type.getTypeName() == null) {
/*  805 */         if (e != null && e.getElementName() != null) {
/*  806 */           th.block();
/*  807 */           if (type instanceof ClassInfo) {
/*  808 */             writeClass((ClassInfo<T, C>)type, th);
/*      */           } else {
/*  810 */             writeEnum((EnumLeafInfo<T, C>)type, (SimpleTypeHost)th);
/*      */           } 
/*      */         } else {
/*      */           
/*  814 */           th.block();
/*  815 */           if (type instanceof ClassInfo) {
/*  816 */             if (XmlSchemaGenerator.this.collisionChecker.push(type)) {
/*  817 */               XmlSchemaGenerator.this.errorListener.warning(new SAXParseException(Messages.ANONYMOUS_TYPE_CYCLE.format(new Object[] { XmlSchemaGenerator.access$1600(this.this$0).getCycleString() }, ), null));
/*      */             
/*      */             }
/*      */             else {
/*      */               
/*  822 */               writeClass((ClassInfo<T, C>)type, th);
/*      */             } 
/*  824 */             XmlSchemaGenerator.this.collisionChecker.pop();
/*      */           } else {
/*  826 */             writeEnum((EnumLeafInfo<T, C>)type, (SimpleTypeHost)th);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  830 */         th._attribute(refAttName, type.getTypeName());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeArray(ArrayInfo<T, C> a, Schema schema) {
/*  838 */       ComplexType ct = schema.complexType().name(a.getTypeName().getLocalPart());
/*  839 */       ct._final("#all");
/*  840 */       LocalElement le = ct.sequence().element().name("item");
/*  841 */       le.type(a.getItemType().getTypeName());
/*  842 */       le.minOccurs(0).maxOccurs("unbounded");
/*  843 */       le.nillable(true);
/*  844 */       ct.commit();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeEnum(EnumLeafInfo<T, C> e, SimpleTypeHost th) {
/*  851 */       SimpleType st = th.simpleType();
/*  852 */       writeName((NonElement<T, C>)e, (TypedXmlWriter)st);
/*      */       
/*  854 */       SimpleRestriction simpleRestriction = st.restriction();
/*  855 */       writeTypeRef((TypeHost)simpleRestriction, e.getBaseType(), "base");
/*      */       
/*  857 */       for (EnumConstant c : e.getConstants()) {
/*  858 */         simpleRestriction.enumeration().value(c.getLexicalValue());
/*      */       }
/*  860 */       st.commit();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeClass(ClassInfo<T, C> c, TypeHost parent) {
/*      */       ComplexExtension complexExtension1, complexExtension2;
/*  871 */       if (containsValueProp(c)) {
/*  872 */         if (c.getProperties().size() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  878 */           ValuePropertyInfo<T, C> vp = c.getProperties().get(0);
/*  879 */           SimpleType st = ((SimpleTypeHost)parent).simpleType();
/*  880 */           writeName((NonElement<T, C>)c, (TypedXmlWriter)st);
/*  881 */           if (vp.isCollection()) {
/*  882 */             writeTypeRef((TypeHost)st.list(), vp.getTarget(), "itemType");
/*      */           } else {
/*  884 */             writeTypeRef((TypeHost)st.restriction(), vp.getTarget(), "base");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  900 */         ComplexType complexType = ((ComplexTypeHost)parent).complexType();
/*  901 */         writeName((NonElement<T, C>)c, (TypedXmlWriter)complexType);
/*  902 */         if (c.isFinal()) {
/*  903 */           complexType._final("extension restriction");
/*      */         }
/*  905 */         SimpleExtension se = complexType.simpleContent().extension();
/*  906 */         se.block();
/*  907 */         for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)c.getProperties()) {
/*  908 */           ValuePropertyInfo vp; switch (p.kind()) {
/*      */             case LAX:
/*  910 */               handleAttributeProp((AttributePropertyInfo<T, C>)p, (AttrDecls)se);
/*      */               continue;
/*      */             case SKIP:
/*  913 */               TODO.checkSpec("what if vp.isCollection() == true?");
/*  914 */               vp = (ValuePropertyInfo)p;
/*  915 */               se.base(vp.getTarget().getTypeName());
/*      */               continue;
/*      */           } 
/*      */ 
/*      */           
/*      */           assert false;
/*  921 */           throw new IllegalStateException();
/*      */         } 
/*      */         
/*  924 */         se.commit();
/*      */         
/*  926 */         TODO.schemaGenerator("figure out what to do if bc != null");
/*  927 */         TODO.checkSpec("handle sec 8.9.5.2, bullet #4");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  937 */       ComplexType ct = ((ComplexTypeHost)parent).complexType();
/*  938 */       writeName((NonElement<T, C>)c, (TypedXmlWriter)ct);
/*  939 */       if (c.isFinal())
/*  940 */         ct._final("extension restriction"); 
/*  941 */       if (c.isAbstract()) {
/*  942 */         ct._abstract(true);
/*      */       }
/*      */       
/*  945 */       ComplexType complexType1 = ct;
/*  946 */       ComplexType complexType2 = ct;
/*      */ 
/*      */       
/*  949 */       ClassInfo<T, C> bc = c.getBaseClass();
/*  950 */       if (bc != null) {
/*  951 */         if (bc.hasValueProperty()) {
/*      */           
/*  953 */           SimpleExtension se = ct.simpleContent().extension();
/*  954 */           SimpleExtension simpleExtension1 = se;
/*  955 */           complexType2 = null;
/*  956 */           se.base(bc.getTypeName());
/*      */         } else {
/*  958 */           ComplexExtension ce = ct.complexContent().extension();
/*  959 */           complexExtension1 = ce;
/*  960 */           complexExtension2 = ce;
/*      */           
/*  962 */           ce.base(bc.getTypeName());
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  967 */       if (complexExtension2 != null) {
/*      */         
/*  969 */         ArrayList<Tree> children = new ArrayList<Tree>();
/*  970 */         for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)c.getProperties()) {
/*      */           
/*  972 */           if (p instanceof ReferencePropertyInfo && ((ReferencePropertyInfo)p).isMixed()) {
/*  973 */             ct.mixed(true);
/*      */           }
/*  975 */           Tree t = buildPropertyContentModel(p);
/*  976 */           if (t != null) {
/*  977 */             children.add(t);
/*      */           }
/*      */         } 
/*  980 */         Tree top = Tree.makeGroup(c.isOrdered() ? GroupKind.SEQUENCE : GroupKind.ALL, children);
/*      */ 
/*      */         
/*  983 */         top.write((TypeDefParticle)complexExtension2);
/*      */       } 
/*      */ 
/*      */       
/*  987 */       for (PropertyInfo<T, C> p : (Iterable<PropertyInfo<T, C>>)c.getProperties()) {
/*  988 */         if (p instanceof AttributePropertyInfo) {
/*  989 */           handleAttributeProp((AttributePropertyInfo<T, C>)p, (AttrDecls)complexExtension1);
/*      */         }
/*      */       } 
/*  992 */       if (c.hasAttributeWildcard()) {
/*  993 */         complexExtension1.anyAttribute().namespace("##other").processContents("skip");
/*      */       }
/*  995 */       ct.commit();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeName(NonElement<T, C> c, TypedXmlWriter xw) {
/* 1002 */       QName tn = c.getTypeName();
/* 1003 */       if (tn != null)
/* 1004 */         xw._attribute("name", tn.getLocalPart()); 
/*      */     }
/*      */     
/*      */     private boolean containsValueProp(ClassInfo<T, C> c) {
/* 1008 */       for (PropertyInfo p : c.getProperties()) {
/* 1009 */         if (p instanceof ValuePropertyInfo) return true; 
/*      */       } 
/* 1011 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree buildPropertyContentModel(PropertyInfo<T, C> p) {
/* 1018 */       switch (p.kind()) {
/*      */         case STRICT:
/* 1020 */           return handleElementProp((ElementPropertyInfo<T, C>)p);
/*      */         
/*      */         case LAX:
/* 1023 */           return null;
/*      */         case null:
/* 1025 */           return handleReferenceProp((ReferencePropertyInfo<T, C>)p);
/*      */         case null:
/* 1027 */           return handleMapProp((MapPropertyInfo<T, C>)p);
/*      */         
/*      */         case SKIP:
/*      */           assert false;
/* 1031 */           throw new IllegalStateException();
/*      */       } 
/*      */       assert false;
/* 1034 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree handleElementProp(final ElementPropertyInfo<T, C> ep) {
/* 1048 */       if (ep.isValueList()) {
/* 1049 */         return new Tree.Term() {
/*      */             protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1051 */               TypeRef<T, C> t = ep.getTypes().get(0);
/* 1052 */               LocalElement e = parent.element();
/* 1053 */               e.block();
/* 1054 */               QName tn = t.getTagName();
/* 1055 */               e.name(tn.getLocalPart());
/* 1056 */               List lst = e.simpleType().list();
/* 1057 */               XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)lst, (NonElementRef<T, C>)t, "itemType");
/* 1058 */               XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, tn);
/* 1059 */               writeOccurs((Occurs)e, (isOptional || !ep.isRequired()), repeated);
/*      */             }
/*      */           };
/*      */       }
/*      */       
/* 1064 */       ArrayList<Tree> children = new ArrayList<Tree>();
/* 1065 */       for (TypeRef<T, C> t : (Iterable<TypeRef<T, C>>)ep.getTypes()) {
/* 1066 */         children.add(new Tree.Term() {
/*      */               protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1068 */                 LocalElement e = parent.element();
/*      */                 
/* 1070 */                 QName tn = t.getTagName();
/*      */                 
/* 1072 */                 PropertyInfo propInfo = t.getSource();
/* 1073 */                 TypeInfo parentInfo = (propInfo == null) ? null : propInfo.parent();
/*      */                 
/* 1075 */                 if (XmlSchemaGenerator.Namespace.this.canBeDirectElementRef(t, tn, parentInfo)) {
/* 1076 */                   if (!t.getTarget().isSimpleType() && t.getTarget() instanceof ClassInfo && XmlSchemaGenerator.this.collisionChecker.findDuplicate(t.getTarget())) {
/* 1077 */                     e.ref(new QName(XmlSchemaGenerator.Namespace.this.uri, tn.getLocalPart()));
/*      */                   } else {
/*      */                     
/* 1080 */                     QName elemName = null;
/* 1081 */                     if (t.getTarget() instanceof Element) {
/* 1082 */                       Element te = (Element)t.getTarget();
/* 1083 */                       elemName = te.getElementName();
/*      */                     } 
/*      */                     
/* 1086 */                     Collection<TypeInfo> refs = propInfo.ref();
/*      */                     TypeInfo ti;
/* 1088 */                     if (refs != null && !refs.isEmpty() && elemName != null && ((ti = refs.iterator().next()) == null || ti instanceof ClassInfoImpl)) {
/*      */                       
/* 1090 */                       ClassInfoImpl cImpl = (ClassInfoImpl)ti;
/* 1091 */                       if (cImpl != null && cImpl.getElementName() != null) {
/* 1092 */                         e.ref(new QName(cImpl.getElementName().getNamespaceURI(), tn.getLocalPart()));
/*      */                       } else {
/* 1094 */                         e.ref(new QName("", tn.getLocalPart()));
/*      */                       } 
/*      */                     } else {
/* 1097 */                       e.ref(tn);
/*      */                     } 
/*      */                   } 
/*      */                 } else {
/* 1101 */                   e.name(tn.getLocalPart());
/* 1102 */                   XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)e, (NonElementRef<T, C>)t, "type");
/* 1103 */                   XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, tn);
/*      */                 } 
/*      */                 
/* 1106 */                 if (t.isNillable()) {
/* 1107 */                   e.nillable(true);
/*      */                 }
/* 1109 */                 if (t.getDefaultValue() != null)
/* 1110 */                   e._default(t.getDefaultValue()); 
/* 1111 */                 writeOccurs((Occurs)e, isOptional, repeated);
/*      */               }
/*      */             });
/*      */       } 
/*      */       
/* 1116 */       final Tree choice = Tree.makeGroup(GroupKind.CHOICE, children).makeOptional(!ep.isRequired()).makeRepeated(ep.isCollection());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1121 */       final QName ename = ep.getXmlName();
/* 1122 */       if (ename != null) {
/* 1123 */         return new Tree.Term() {
/*      */             protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1125 */               LocalElement e = parent.element();
/* 1126 */               if (ename.getNamespaceURI().length() > 0 && 
/* 1127 */                 !ename.getNamespaceURI().equals(XmlSchemaGenerator.Namespace.this.uri)) {
/*      */ 
/*      */                 
/* 1130 */                 e.ref(new QName(ename.getNamespaceURI(), ename.getLocalPart()));
/*      */                 
/*      */                 return;
/*      */               } 
/* 1134 */               e.name(ename.getLocalPart());
/* 1135 */               XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, ename);
/*      */               
/* 1137 */               if (ep.isCollectionNillable()) {
/* 1138 */                 e.nillable(true);
/*      */               }
/* 1140 */               writeOccurs((Occurs)e, !ep.isCollectionRequired(), repeated);
/*      */               
/* 1142 */               ComplexType p = e.complexType();
/* 1143 */               choice.write((TypeDefParticle)p);
/*      */             }
/*      */           };
/*      */       }
/* 1147 */       return choice;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean canBeDirectElementRef(TypeRef<T, C> t, QName tn, TypeInfo parentInfo) {
/* 1158 */       Element te = null;
/* 1159 */       ClassInfo ci = null;
/* 1160 */       QName targetTagName = null;
/*      */       
/* 1162 */       if (t.isNillable() || t.getDefaultValue() != null)
/*      */       {
/* 1164 */         return false;
/*      */       }
/*      */       
/* 1167 */       if (t.getTarget() instanceof Element) {
/* 1168 */         te = (Element)t.getTarget();
/* 1169 */         targetTagName = te.getElementName();
/* 1170 */         if (te instanceof ClassInfo) {
/* 1171 */           ci = (ClassInfo)te;
/*      */         }
/*      */       } 
/*      */       
/* 1175 */       String nsUri = tn.getNamespaceURI();
/* 1176 */       if (!nsUri.equals(this.uri) && nsUri.length() > 0 && (!(parentInfo instanceof ClassInfo) || ((ClassInfo)parentInfo).getTypeName() != null)) {
/* 1177 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1181 */       if (ci != null && targetTagName != null && te.getScope() == null && 
/* 1182 */         targetTagName.getLocalPart().equals(tn.getLocalPart())) {
/* 1183 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1188 */       if (te != null) {
/* 1189 */         return (targetTagName != null && targetTagName.equals(tn));
/*      */       }
/*      */       
/* 1192 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void handleAttributeProp(AttributePropertyInfo<T, C> ap, AttrDecls attr) {
/* 1225 */       LocalAttribute localAttribute = attr.attribute();
/*      */       
/* 1227 */       String attrURI = ap.getXmlName().getNamespaceURI();
/* 1228 */       if (attrURI.equals("")) {
/* 1229 */         localAttribute.name(ap.getXmlName().getLocalPart());
/*      */         
/* 1231 */         writeAttributeTypeRef(ap, (AttributeType)localAttribute);
/*      */         
/* 1233 */         this.attributeFormDefault.writeForm(localAttribute, ap.getXmlName());
/*      */       } else {
/* 1235 */         localAttribute.ref(ap.getXmlName());
/*      */       } 
/*      */       
/* 1238 */       if (ap.isRequired())
/*      */       {
/* 1240 */         localAttribute.use("required");
/*      */       }
/*      */     }
/*      */     
/*      */     private void writeAttributeTypeRef(AttributePropertyInfo<T, C> ap, AttributeType a) {
/* 1245 */       if (ap.isCollection()) {
/* 1246 */         writeTypeRef((TypeHost)a.simpleType().list(), (NonElementRef<T, C>)ap, "itemType");
/*      */       } else {
/* 1248 */         writeTypeRef((TypeHost)a, (NonElementRef<T, C>)ap, "type");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree handleReferenceProp(final ReferencePropertyInfo<T, C> rp) {
/* 1260 */       ArrayList<Tree> children = new ArrayList<Tree>();
/*      */       
/* 1262 */       for (Element<T, C> e : (Iterable<Element<T, C>>)rp.getElements()) {
/* 1263 */         children.add(new Tree.Term() {
/*      */               protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1265 */                 LocalElement eref = parent.element();
/*      */                 
/* 1267 */                 boolean local = false;
/*      */                 
/* 1269 */                 QName en = e.getElementName();
/* 1270 */                 if (e.getScope() != null) {
/*      */                   
/* 1272 */                   boolean qualified = en.getNamespaceURI().equals(XmlSchemaGenerator.Namespace.this.uri);
/* 1273 */                   boolean unqualified = en.getNamespaceURI().equals("");
/* 1274 */                   if (qualified || unqualified) {
/*      */ 
/*      */ 
/*      */                     
/* 1278 */                     if (unqualified) {
/* 1279 */                       if (XmlSchemaGenerator.Namespace.this.elementFormDefault.isEffectivelyQualified) {
/* 1280 */                         eref.form("unqualified");
/*      */                       }
/* 1282 */                     } else if (!XmlSchemaGenerator.Namespace.this.elementFormDefault.isEffectivelyQualified) {
/* 1283 */                       eref.form("qualified");
/*      */                     } 
/*      */                     
/* 1286 */                     local = true;
/* 1287 */                     eref.name(en.getLocalPart());
/*      */ 
/*      */                     
/* 1290 */                     if (e instanceof ClassInfo) {
/* 1291 */                       XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)eref, (NonElement<T, C>)e, "type");
/*      */                     } else {
/* 1293 */                       XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)eref, ((ElementInfo)e).getContentType(), "type");
/*      */                     } 
/*      */                   } 
/*      */                 } 
/* 1297 */                 if (!local)
/* 1298 */                   eref.ref(en); 
/* 1299 */                 writeOccurs((Occurs)eref, isOptional, repeated);
/*      */               }
/*      */             });
/*      */       } 
/*      */       
/* 1304 */       final WildcardMode wc = rp.getWildcard();
/* 1305 */       if (wc != null) {
/* 1306 */         children.add(new Tree.Term() {
/*      */               protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1308 */                 Any any = parent.any();
/* 1309 */                 String pcmode = XmlSchemaGenerator.getProcessContentsModeName(wc);
/* 1310 */                 if (pcmode != null) any.processContents(pcmode); 
/* 1311 */                 any.namespace("##other");
/* 1312 */                 writeOccurs((Occurs)any, isOptional, repeated);
/*      */               }
/*      */             });
/*      */       }
/*      */ 
/*      */       
/* 1318 */       final Tree choice = Tree.makeGroup(GroupKind.CHOICE, children).makeRepeated(rp.isCollection()).makeOptional(!rp.isRequired());
/*      */       
/* 1320 */       final QName ename = rp.getXmlName();
/*      */       
/* 1322 */       if (ename != null) {
/* 1323 */         return new Tree.Term() {
/*      */             protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1325 */               LocalElement e = parent.element().name(ename.getLocalPart());
/* 1326 */               XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, ename);
/* 1327 */               if (rp.isCollectionNillable())
/* 1328 */                 e.nillable(true); 
/* 1329 */               writeOccurs((Occurs)e, true, repeated);
/*      */               
/* 1331 */               ComplexType p = e.complexType();
/* 1332 */               choice.write((TypeDefParticle)p);
/*      */             }
/*      */           };
/*      */       }
/* 1336 */       return choice;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Tree handleMapProp(final MapPropertyInfo<T, C> mp) {
/* 1347 */       return new Tree.Term() {
/*      */           protected void write(ContentModelContainer parent, boolean isOptional, boolean repeated) {
/* 1349 */             QName ename = mp.getXmlName();
/*      */             
/* 1351 */             LocalElement e = parent.element();
/* 1352 */             XmlSchemaGenerator.Namespace.this.elementFormDefault.writeForm(e, ename);
/* 1353 */             if (mp.isCollectionNillable()) {
/* 1354 */               e.nillable(true);
/*      */             }
/* 1356 */             e = e.name(ename.getLocalPart());
/* 1357 */             writeOccurs((Occurs)e, isOptional, repeated);
/* 1358 */             ComplexType p = e.complexType();
/*      */ 
/*      */ 
/*      */             
/* 1362 */             e = p.sequence().element();
/* 1363 */             e.name("entry").minOccurs(0).maxOccurs("unbounded");
/*      */             
/* 1365 */             ExplicitGroup seq = e.complexType().sequence();
/* 1366 */             XmlSchemaGenerator.Namespace.this.writeKeyOrValue(seq, "key", mp.getKeyType());
/* 1367 */             XmlSchemaGenerator.Namespace.this.writeKeyOrValue(seq, "value", mp.getValueType());
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private void writeKeyOrValue(ExplicitGroup seq, String tagName, NonElement<T, C> typeRef) {
/* 1373 */       LocalElement key = seq.element().name(tagName);
/* 1374 */       key.minOccurs(0);
/* 1375 */       writeTypeRef((TypeHost)key, typeRef, "type");
/*      */     }
/*      */     
/*      */     public void addGlobalAttribute(AttributePropertyInfo<T, C> ap) {
/* 1379 */       this.attributeDecls.put(ap.getXmlName().getLocalPart(), ap);
/* 1380 */       addDependencyTo(ap.getTarget().getTypeName());
/*      */     }
/*      */     
/*      */     public void addGlobalElement(TypeRef<T, C> tref) {
/* 1384 */       this.elementDecls.put(tref.getTagName().getLocalPart(), new ElementWithType(false, tref.getTarget()));
/* 1385 */       addDependencyTo(tref.getTarget().getTypeName());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1390 */       StringBuilder buf = new StringBuilder();
/* 1391 */       buf.append("[classes=").append(this.classes);
/* 1392 */       buf.append(",elementDecls=").append(this.elementDecls);
/* 1393 */       buf.append(",enums=").append(this.enums);
/* 1394 */       buf.append("]");
/* 1395 */       return super.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract class ElementDeclaration
/*      */     {
/*      */       public abstract boolean equals(Object param2Object);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public abstract int hashCode();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public abstract void writeTo(String param2String, Schema param2Schema);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class ElementWithType
/*      */       extends ElementDeclaration
/*      */     {
/*      */       private final boolean nillable;
/*      */ 
/*      */ 
/*      */       
/*      */       private final NonElement<T, C> type;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ElementWithType(boolean nillable, NonElement<T, C> type) {
/* 1436 */         this.type = type;
/* 1437 */         this.nillable = nillable;
/*      */       }
/*      */       
/*      */       public void writeTo(String localName, Schema schema) {
/* 1441 */         TopLevelElement e = schema.element().name(localName);
/* 1442 */         if (this.nillable)
/* 1443 */           e.nillable(true); 
/* 1444 */         if (this.type != null) {
/* 1445 */           XmlSchemaGenerator.Namespace.this.writeTypeRef((TypeHost)e, this.type, "type");
/*      */         } else {
/* 1447 */           e.complexType();
/*      */         } 
/* 1449 */         e.commit();
/*      */       }
/*      */       
/*      */       public boolean equals(Object o) {
/* 1453 */         if (this == o) return true; 
/* 1454 */         if (o == null || getClass() != o.getClass()) return false;
/*      */         
/* 1456 */         ElementWithType that = (ElementWithType)o;
/* 1457 */         return this.type.equals(that.type);
/*      */       }
/*      */       
/*      */       public int hashCode() {
/* 1461 */         return this.type.hashCode(); } } } class ElementWithType extends Namespace.ElementDeclaration { private final boolean nillable; private final NonElement<T, C> type; public ElementWithType(boolean nillable, NonElement<T, C> type) { super((XmlSchemaGenerator.Namespace)XmlSchemaGenerator.this); this.type = type; this.nillable = nillable; } public void writeTo(String localName, Schema schema) { TopLevelElement e = schema.element().name(localName); if (this.nillable) e.nillable(true);  if (this.type != null) { this.this$1.writeTypeRef((TypeHost)e, this.type, "type"); } else { e.complexType(); }  e.commit(); } public int hashCode() { return this.type.hashCode(); }
/*      */      public boolean equals(Object o) {
/*      */       if (this == o)
/*      */         return true; 
/*      */       if (o == null || getClass() != o.getClass())
/*      */         return false; 
/*      */       ElementWithType that = (ElementWithType)o;
/*      */       return this.type.equals(that.type);
/*      */     } }
/*      */   private boolean generateSwaRefAdapter(NonElementRef<T, C> typeRef) {
/* 1471 */     return generateSwaRefAdapter(typeRef.getSource());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean generateSwaRefAdapter(PropertyInfo<T, C> prop) {
/* 1478 */     Adapter<T, C> adapter = prop.getAdapter();
/* 1479 */     if (adapter == null) return false; 
/* 1480 */     Object o = this.navigator.asDecl(SwaRefAdapter.class);
/* 1481 */     if (o == null) return false; 
/* 1482 */     return o.equals(adapter.adapterType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1490 */     StringBuilder buf = new StringBuilder();
/* 1491 */     for (Namespace ns : this.namespaces.values()) {
/* 1492 */       if (buf.length() > 0) buf.append(','); 
/* 1493 */       buf.append(ns.uri).append('=').append(ns);
/*      */     } 
/* 1495 */     return super.toString() + '[' + buf + ']';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getProcessContentsModeName(WildcardMode wc) {
/* 1504 */     switch (wc) {
/*      */       case LAX:
/*      */       case SKIP:
/* 1507 */         return wc.name().toLowerCase();
/*      */       case STRICT:
/* 1509 */         return null;
/*      */     } 
/* 1511 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String relativize(String uri, String baseUri) {
/*      */     try {
/* 1534 */       assert uri != null;
/*      */       
/* 1536 */       if (baseUri == null) return uri;
/*      */       
/* 1538 */       URI theUri = new URI(Util.escapeURI(uri));
/* 1539 */       URI theBaseUri = new URI(Util.escapeURI(baseUri));
/*      */       
/* 1541 */       if (theUri.isOpaque() || theBaseUri.isOpaque()) {
/* 1542 */         return uri;
/*      */       }
/* 1544 */       if (!Util.equalsIgnoreCase(theUri.getScheme(), theBaseUri.getScheme()) || !Util.equal(theUri.getAuthority(), theBaseUri.getAuthority()))
/*      */       {
/* 1546 */         return uri;
/*      */       }
/* 1548 */       String uriPath = theUri.getPath();
/* 1549 */       String basePath = theBaseUri.getPath();
/*      */ 
/*      */       
/* 1552 */       if (!basePath.endsWith("/")) {
/* 1553 */         basePath = Util.normalizeUriPath(basePath);
/*      */       }
/*      */       
/* 1556 */       if (uriPath.equals(basePath)) {
/* 1557 */         return ".";
/*      */       }
/* 1559 */       String relPath = calculateRelativePath(uriPath, basePath, fixNull(theUri.getScheme()).equals("file"));
/*      */       
/* 1561 */       if (relPath == null)
/* 1562 */         return uri; 
/* 1563 */       StringBuilder relUri = new StringBuilder();
/* 1564 */       relUri.append(relPath);
/* 1565 */       if (theUri.getQuery() != null)
/* 1566 */         relUri.append('?').append(theUri.getQuery()); 
/* 1567 */       if (theUri.getFragment() != null) {
/* 1568 */         relUri.append('#').append(theUri.getFragment());
/*      */       }
/* 1570 */       return relUri.toString();
/* 1571 */     } catch (URISyntaxException e) {
/* 1572 */       throw new InternalError("Error escaping one of these uris:\n\t" + uri + "\n\t" + baseUri);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String fixNull(String s) {
/* 1577 */     if (s == null) return ""; 
/* 1578 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String calculateRelativePath(String uri, String base, boolean fileUrl) {
/* 1584 */     boolean onWindows = (File.pathSeparatorChar == ';');
/*      */     
/* 1586 */     if (base == null) {
/* 1587 */       return null;
/*      */     }
/* 1589 */     if ((fileUrl && onWindows && startsWithIgnoreCase(uri, base)) || uri.startsWith(base)) {
/* 1590 */       return uri.substring(base.length());
/*      */     }
/* 1592 */     return "../" + calculateRelativePath(uri, Util.getParentUriPath(base), fileUrl);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean startsWithIgnoreCase(String s, String t) {
/* 1597 */     return s.toUpperCase().startsWith(t.toUpperCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1604 */   private static final Comparator<String> NAMESPACE_COMPARATOR = new Comparator<String>() {
/*      */       public int compare(String lhs, String rhs) {
/* 1606 */         return -lhs.compareTo(rhs);
/*      */       }
/*      */     };
/*      */   
/*      */   private static final String newline = "\n";
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\XmlSchemaGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */