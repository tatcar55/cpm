/*      */ package com.sun.xml.ws.model;
/*      */ 
/*      */ import com.oracle.webservices.api.EnvelopeStyle;
/*      */ import com.oracle.webservices.api.EnvelopeStyleFeature;
/*      */ import com.oracle.webservices.api.databinding.DatabindingMode;
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.localization.Localizable;
/*      */ import com.sun.xml.ws.api.BindingID;
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.WSBinding;
/*      */ import com.sun.xml.ws.api.WSFeatureList;
/*      */ import com.sun.xml.ws.api.databinding.DatabindingConfig;
/*      */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*      */ import com.sun.xml.ws.api.model.ExceptionType;
/*      */ import com.sun.xml.ws.api.model.MEP;
/*      */ import com.sun.xml.ws.api.model.Parameter;
/*      */ import com.sun.xml.ws.api.model.ParameterBinding;
/*      */ import com.sun.xml.ws.api.model.soap.SOAPBinding;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPart;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*      */ import com.sun.xml.ws.model.soap.SOAPBindingImpl;
/*      */ import com.sun.xml.ws.resources.ModelerMessages;
/*      */ import com.sun.xml.ws.resources.ServerMessages;
/*      */ import com.sun.xml.ws.spi.db.BindingContext;
/*      */ import com.sun.xml.ws.spi.db.BindingHelper;
/*      */ import com.sun.xml.ws.spi.db.TypeInfo;
/*      */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.rmi.RemoteException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.logging.Logger;
/*      */ import javax.jws.Oneway;
/*      */ import javax.jws.WebMethod;
/*      */ import javax.jws.WebParam;
/*      */ import javax.jws.WebResult;
/*      */ import javax.jws.WebService;
/*      */ import javax.jws.soap.SOAPBinding;
/*      */ import javax.xml.bind.annotation.XmlElement;
/*      */ import javax.xml.bind.annotation.XmlSeeAlso;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.ws.Action;
/*      */ import javax.xml.ws.AsyncHandler;
/*      */ import javax.xml.ws.BindingType;
/*      */ import javax.xml.ws.FaultAction;
/*      */ import javax.xml.ws.Holder;
/*      */ import javax.xml.ws.RequestWrapper;
/*      */ import javax.xml.ws.Response;
/*      */ import javax.xml.ws.ResponseWrapper;
/*      */ import javax.xml.ws.WebFault;
/*      */ import javax.xml.ws.soap.MTOM;
/*      */ import javax.xml.ws.soap.MTOMFeature;
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
/*      */ public class RuntimeModeler
/*      */ {
/*      */   private final WebServiceFeatureList features;
/*      */   private BindingID bindingId;
/*      */   private WSBinding wsBinding;
/*      */   private final Class portClass;
/*      */   private AbstractSEIModelImpl model;
/*      */   private SOAPBindingImpl defaultBinding;
/*      */   private String packageName;
/*      */   private String targetNamespace;
/*      */   private boolean isWrapped = true;
/*      */   private ClassLoader classLoader;
/*      */   private final WSDLPort binding;
/*      */   private QName serviceName;
/*      */   private QName portName;
/*      */   private Set<Class> classUsesWebMethod;
/*      */   private DatabindingConfig config;
/*      */   private MetadataReader metadataReader;
/*      */   public static final String PD_JAXWS_PACKAGE_PD = ".jaxws.";
/*      */   public static final String JAXWS_PACKAGE_PD = "jaxws.";
/*      */   public static final String RESPONSE = "Response";
/*      */   public static final String RETURN = "return";
/*      */   public static final String BEAN = "Bean";
/*      */   public static final String SERVICE = "Service";
/*      */   public static final String PORT = "Port";
/*  132 */   public static final Class HOLDER_CLASS = Holder.class;
/*  133 */   public static final Class<RemoteException> REMOTE_EXCEPTION_CLASS = RemoteException.class;
/*  134 */   public static final Class<RuntimeException> RUNTIME_EXCEPTION_CLASS = RuntimeException.class;
/*  135 */   public static final Class<Exception> EXCEPTION_CLASS = Exception.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DecapitalizeExceptionBeanProperties = "com.sun.xml.ws.api.model.DecapitalizeExceptionBeanProperties";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String SuppressDocLitWrapperGeneration = "com.sun.xml.ws.api.model.SuppressDocLitWrapperGeneration";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DocWrappeeNamespapceQualified = "com.sun.xml.ws.api.model.DocWrappeeNamespapceQualified";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RuntimeModeler(@NotNull DatabindingConfig config) {
/*  165 */     this.portClass = (config.getEndpointClass() != null) ? config.getEndpointClass() : config.getContractClass();
/*  166 */     this.serviceName = config.getMappingInfo().getServiceName();
/*  167 */     this.binding = config.getWsdlPort();
/*  168 */     this.classLoader = config.getClassLoader();
/*  169 */     this.portName = config.getMappingInfo().getPortName();
/*  170 */     this.config = config;
/*  171 */     this.wsBinding = config.getWSBinding();
/*  172 */     this.metadataReader = config.getMetadataReader();
/*  173 */     this.targetNamespace = config.getMappingInfo().getTargetNamespace();
/*  174 */     if (this.metadataReader == null) this.metadataReader = new ReflectAnnotationReader(); 
/*  175 */     if (this.wsBinding != null) {
/*  176 */       this.bindingId = this.wsBinding.getBindingId();
/*  177 */       if (config.getFeatures() != null) this.wsBinding.getFeatures().mergeFeatures(config.getFeatures(), false); 
/*  178 */       if (this.binding != null) this.wsBinding.getFeatures().mergeFeatures((Iterable)this.binding.getFeatures(), false); 
/*  179 */       this.features = WebServiceFeatureList.toList((Iterable)this.wsBinding.getFeatures());
/*      */     } else {
/*  181 */       this.bindingId = config.getMappingInfo().getBindingID();
/*  182 */       this.features = WebServiceFeatureList.toList(config.getFeatures());
/*  183 */       if (this.binding != null) this.bindingId = this.binding.getBinding().getBindingId(); 
/*  184 */       if (this.bindingId == null) this.bindingId = getDefaultBindingID(); 
/*  185 */       if (!this.features.contains(MTOMFeature.class)) {
/*  186 */         MTOM mtomAn = getAnnotation(this.portClass, MTOM.class);
/*  187 */         if (mtomAn != null) this.features.add(WebServiceFeatureList.getFeature(mtomAn)); 
/*      */       } 
/*  189 */       if (!this.features.contains(EnvelopeStyleFeature.class)) {
/*  190 */         EnvelopeStyle es = getAnnotation(this.portClass, EnvelopeStyle.class);
/*  191 */         if (es != null) this.features.add(WebServiceFeatureList.getFeature((Annotation)es)); 
/*      */       } 
/*  193 */       this.wsBinding = this.bindingId.createBinding((WSFeatureList)this.features);
/*      */     } 
/*      */   }
/*      */   
/*      */   private BindingID getDefaultBindingID() {
/*  198 */     BindingType bt = getAnnotation(this.portClass, BindingType.class);
/*  199 */     if (bt != null) return BindingID.parse(bt.value()); 
/*  200 */     SOAPVersion ver = WebServiceFeatureList.getSoapVersion((WSFeatureList)this.features);
/*  201 */     boolean mtomEnabled = this.features.isEnabled(MTOMFeature.class);
/*  202 */     if (SOAPVersion.SOAP_12.equals(ver)) {
/*  203 */       return mtomEnabled ? (BindingID)BindingID.SOAP12_HTTP_MTOM : (BindingID)BindingID.SOAP12_HTTP;
/*      */     }
/*  205 */     return mtomEnabled ? (BindingID)BindingID.SOAP11_HTTP_MTOM : (BindingID)BindingID.SOAP11_HTTP;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClassLoader(ClassLoader classLoader) {
/*  214 */     this.classLoader = classLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPortName(QName portName) {
/*  223 */     this.portName = portName;
/*      */   }
/*      */   
/*      */   private <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> T) {
/*  227 */     return (T)this.metadataReader.getAnnotation(T, clazz);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T extends Annotation> T getAnnotation(Method method, Class<T> T) {
/*  236 */     return (T)this.metadataReader.getAnnotation(T, method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Annotation[] getAnnotations(Method method) {
/*  245 */     return this.metadataReader.getAnnotations(method);
/*      */   }
/*      */   
/*      */   private Annotation[] getAnnotations(Class<?> c) {
/*  249 */     return this.metadataReader.getAnnotations(c);
/*      */   }
/*      */   private Annotation[][] getParamAnnotations(Method method) {
/*  252 */     return this.metadataReader.getParameterAnnotations(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  260 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractSEIModelImpl buildRuntimeModel() {
/*  271 */     this.model = new SOAPSEIModel(this.features);
/*  272 */     this.model.contractClass = this.config.getContractClass();
/*  273 */     this.model.endpointClass = this.config.getEndpointClass();
/*  274 */     this.model.classLoader = this.classLoader;
/*  275 */     this.model.wsBinding = this.wsBinding;
/*  276 */     this.model.databindingInfo.properties().putAll(this.config.properties());
/*  277 */     if (this.model.contractClass == null) this.model.contractClass = this.portClass; 
/*  278 */     if (this.model.endpointClass == null && !this.portClass.isInterface()) this.model.endpointClass = this.portClass; 
/*  279 */     Class<?> seiClass = this.portClass;
/*  280 */     this.metadataReader.getProperties(this.model.databindingInfo.properties(), this.portClass);
/*  281 */     WebService webService = getAnnotation(this.portClass, WebService.class);
/*  282 */     if (webService == null) {
/*  283 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { this.portClass.getCanonicalName() });
/*      */     }
/*      */     
/*  286 */     Class<?> seiFromConfig = configEndpointInterface();
/*  287 */     if (webService.endpointInterface().length() > 0 || seiFromConfig != null) {
/*  288 */       if (seiFromConfig != null) {
/*  289 */         seiClass = seiFromConfig;
/*      */       } else {
/*  291 */         seiClass = getClass(webService.endpointInterface(), ModelerMessages.localizableRUNTIME_MODELER_CLASS_NOT_FOUND(webService.endpointInterface()));
/*      */       } 
/*  293 */       this.model.contractClass = seiClass;
/*  294 */       this.model.endpointClass = this.portClass;
/*  295 */       WebService seiService = getAnnotation(seiClass, WebService.class);
/*  296 */       if (seiService == null) {
/*  297 */         throw new RuntimeModelerException("runtime.modeler.endpoint.interface.no.webservice", new Object[] { webService.endpointInterface() });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  302 */       SOAPBinding sbPortClass = getAnnotation(this.portClass, SOAPBinding.class);
/*  303 */       SOAPBinding sbSei = getAnnotation(seiClass, SOAPBinding.class);
/*  304 */       if (sbPortClass != null && (
/*  305 */         sbSei == null || sbSei.style() != sbPortClass.style() || sbSei.use() != sbPortClass.use())) {
/*  306 */         logger.warning(ServerMessages.RUNTIMEMODELER_INVALIDANNOTATION_ON_IMPL("@SOAPBinding", this.portClass.getName(), seiClass.getName()));
/*      */       }
/*      */     } 
/*      */     
/*  310 */     if (this.serviceName == null)
/*  311 */       this.serviceName = getServiceName(this.portClass, this.metadataReader); 
/*  312 */     this.model.setServiceQName(this.serviceName);
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
/*  328 */     if (this.portName == null) this.portName = getPortName(this.portClass, this.metadataReader, this.serviceName.getNamespaceURI()); 
/*  329 */     this.model.setPortName(this.portName);
/*      */ 
/*      */     
/*  332 */     DatabindingMode dbm2 = getAnnotation(this.portClass, DatabindingMode.class);
/*  333 */     if (dbm2 != null) this.model.databindingInfo.setDatabindingMode(dbm2.value());
/*      */     
/*  335 */     processClass(seiClass);
/*  336 */     if (this.model.getJavaMethods().size() == 0) {
/*  337 */       throw new RuntimeModelerException("runtime.modeler.no.operations", new Object[] { this.portClass.getName() });
/*      */     }
/*  339 */     this.model.postProcess();
/*      */ 
/*      */ 
/*      */     
/*  343 */     this.config.properties().put(BindingContext.class.getName(), this.model.bindingContext);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  349 */     if (this.binding != null)
/*  350 */       this.model.freeze(this.binding); 
/*  351 */     return this.model;
/*      */   }
/*      */   
/*      */   private Class configEndpointInterface() {
/*  355 */     if (this.config.getEndpointClass() == null || this.config.getEndpointClass().isInterface())
/*  356 */       return null; 
/*  357 */     return this.config.getContractClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class getClass(String className, Localizable errorMessage) {
/*      */     try {
/*  369 */       if (this.classLoader == null) {
/*  370 */         return Thread.currentThread().getContextClassLoader().loadClass(className);
/*      */       }
/*  372 */       return this.classLoader.loadClass(className);
/*  373 */     } catch (ClassNotFoundException e) {
/*  374 */       throw new RuntimeModelerException(errorMessage);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean noWrapperGen() {
/*  379 */     Object o = this.config.properties().get("com.sun.xml.ws.api.model.SuppressDocLitWrapperGeneration");
/*  380 */     return (o != null && o instanceof Boolean) ? ((Boolean)o).booleanValue() : false;
/*      */   }
/*      */   
/*      */   private Class getRequestWrapperClass(String className, Method method, QName reqElemName) {
/*  384 */     ClassLoader loader = (this.classLoader == null) ? Thread.currentThread().getContextClassLoader() : this.classLoader;
/*      */     try {
/*  386 */       return loader.loadClass(className);
/*  387 */     } catch (ClassNotFoundException e) {
/*  388 */       if (noWrapperGen()) return WrapperComposite.class; 
/*  389 */       logger.fine("Dynamically creating request wrapper Class " + className);
/*  390 */       return WrapperBeanGenerator.createRequestWrapperBean(className, method, reqElemName, loader);
/*      */     } 
/*      */   }
/*      */   
/*      */   private Class getResponseWrapperClass(String className, Method method, QName resElemName) {
/*  395 */     ClassLoader loader = (this.classLoader == null) ? Thread.currentThread().getContextClassLoader() : this.classLoader;
/*      */     try {
/*  397 */       return loader.loadClass(className);
/*  398 */     } catch (ClassNotFoundException e) {
/*  399 */       if (noWrapperGen()) return WrapperComposite.class; 
/*  400 */       logger.fine("Dynamically creating response wrapper bean Class " + className);
/*  401 */       return WrapperBeanGenerator.createResponseWrapperBean(className, method, resElemName, loader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Class getExceptionBeanClass(String className, Class exception, String name, String namespace) {
/*  407 */     boolean decapitalizeExceptionBeanProperties = true;
/*  408 */     Object o = this.config.properties().get("com.sun.xml.ws.api.model.DecapitalizeExceptionBeanProperties");
/*  409 */     if (o != null && o instanceof Boolean) decapitalizeExceptionBeanProperties = ((Boolean)o).booleanValue(); 
/*  410 */     ClassLoader loader = (this.classLoader == null) ? Thread.currentThread().getContextClassLoader() : this.classLoader;
/*      */     try {
/*  412 */       return loader.loadClass(className);
/*  413 */     } catch (ClassNotFoundException e) {
/*  414 */       logger.fine("Dynamically creating exception bean Class " + className);
/*  415 */       return WrapperBeanGenerator.createExceptionBean(className, exception, this.targetNamespace, name, namespace, loader, decapitalizeExceptionBeanProperties);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void determineWebMethodUse(Class<Object> clazz) {
/*  420 */     if (clazz == null)
/*      */       return; 
/*  422 */     if (!clazz.isInterface()) {
/*  423 */       if (clazz == Object.class) {
/*      */         return;
/*      */       }
/*  426 */       for (Method method : clazz.getMethods()) {
/*  427 */         if (method.getDeclaringClass() == clazz) {
/*      */           
/*  429 */           WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  430 */           if (webMethod != null && !webMethod.exclude()) {
/*  431 */             this.classUsesWebMethod.add(clazz); break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  436 */     determineWebMethodUse(clazz.getSuperclass());
/*      */   }
/*      */   
/*      */   void processClass(Class<?> clazz) {
/*  440 */     this.classUsesWebMethod = (Set)new HashSet<Class<?>>();
/*  441 */     determineWebMethodUse(clazz);
/*  442 */     WebService webService = getAnnotation(clazz, WebService.class);
/*  443 */     QName portTypeName = getPortTypeName(clazz, this.targetNamespace, this.metadataReader);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  449 */     this.packageName = "";
/*  450 */     if (clazz.getPackage() != null) {
/*  451 */       this.packageName = clazz.getPackage().getName();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  457 */     this.targetNamespace = portTypeName.getNamespaceURI();
/*  458 */     this.model.setPortTypeName(portTypeName);
/*  459 */     this.model.setTargetNamespace(this.targetNamespace);
/*  460 */     this.model.defaultSchemaNamespaceSuffix = this.config.getMappingInfo().getDefaultSchemaNamespaceSuffix();
/*  461 */     this.model.setWSDLLocation(webService.wsdlLocation());
/*      */     
/*  463 */     SOAPBinding soapBinding = getAnnotation(clazz, SOAPBinding.class);
/*  464 */     if (soapBinding != null) {
/*  465 */       if (soapBinding.style() == SOAPBinding.Style.RPC && soapBinding.parameterStyle() == SOAPBinding.ParameterStyle.BARE) {
/*  466 */         throw new RuntimeModelerException("runtime.modeler.invalid.soapbinding.parameterstyle", new Object[] { soapBinding, clazz });
/*      */       }
/*      */ 
/*      */       
/*  470 */       this.isWrapped = (soapBinding.parameterStyle() == SOAPBinding.ParameterStyle.WRAPPED);
/*      */     } 
/*  472 */     this.defaultBinding = createBinding(soapBinding);
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
/*  492 */     for (Method method : clazz.getMethods()) {
/*  493 */       if (clazz.isInterface() || (
/*  494 */         !getBooleanSystemProperty("com.sun.xml.ws.legacyWebMethod").booleanValue() ? 
/*  495 */         !isWebMethodBySpec(method, clazz) : (
/*      */ 
/*      */         
/*  498 */         method.getDeclaringClass() == Object.class || !isWebMethod(method))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  504 */         processMethod(method);
/*      */       }
/*      */     } 
/*  507 */     XmlSeeAlso xmlSeeAlso = getAnnotation(clazz, XmlSeeAlso.class);
/*  508 */     if (xmlSeeAlso != null) {
/*  509 */       this.model.addAdditionalClasses(xmlSeeAlso.value());
/*      */     }
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
/*      */   private boolean isWebMethodBySpec(Method method, Class clazz) {
/*  525 */     int modifiers = method.getModifiers();
/*  526 */     boolean staticFinal = (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
/*      */     
/*  528 */     assert Modifier.isPublic(modifiers);
/*  529 */     assert !clazz.isInterface();
/*      */     
/*  531 */     WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  532 */     if (webMethod != null) {
/*  533 */       if (webMethod.exclude()) {
/*  534 */         return false;
/*      */       }
/*  536 */       if (staticFinal) {
/*  537 */         throw new RuntimeModelerException(ModelerMessages.localizableRUNTIME_MODELER_WEBMETHOD_MUST_BE_NONSTATICFINAL(method));
/*      */       }
/*  539 */       return true;
/*      */     } 
/*      */     
/*  542 */     if (staticFinal) {
/*  543 */       return false;
/*      */     }
/*      */     
/*  546 */     Class<?> declClass = method.getDeclaringClass();
/*  547 */     return (getAnnotation(declClass, WebService.class) != null);
/*      */   }
/*      */   
/*      */   private boolean isWebMethod(Method method) {
/*  551 */     int modifiers = method.getModifiers();
/*  552 */     if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
/*  553 */       return false;
/*      */     }
/*  555 */     Class<?> clazz = method.getDeclaringClass();
/*  556 */     boolean declHasWebService = (getAnnotation(clazz, WebService.class) != null);
/*  557 */     WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  558 */     if (webMethod != null && !webMethod.exclude() && declHasWebService)
/*  559 */       return true; 
/*  560 */     return (declHasWebService && !this.classUsesWebMethod.contains(clazz));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPBindingImpl createBinding(SOAPBinding soapBinding) {
/*  569 */     SOAPBindingImpl rtSOAPBinding = new SOAPBindingImpl();
/*  570 */     SOAPBinding.Style style = (soapBinding != null) ? soapBinding.style() : SOAPBinding.Style.DOCUMENT;
/*  571 */     rtSOAPBinding.setStyle(style);
/*  572 */     assert this.bindingId != null;
/*  573 */     this.model.bindingId = this.bindingId;
/*  574 */     SOAPVersion soapVersion = this.bindingId.getSOAPVersion();
/*  575 */     rtSOAPBinding.setSOAPVersion(soapVersion);
/*  576 */     return rtSOAPBinding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getNamespace(@NotNull String packageName) {
/*      */     String[] tokens;
/*  586 */     if (packageName.length() == 0) {
/*  587 */       return null;
/*      */     }
/*  589 */     StringTokenizer tokenizer = new StringTokenizer(packageName, ".");
/*      */     
/*  591 */     if (tokenizer.countTokens() == 0) {
/*  592 */       tokens = new String[0];
/*      */     } else {
/*  594 */       tokens = new String[tokenizer.countTokens()];
/*  595 */       for (int j = tokenizer.countTokens() - 1; j >= 0; j--) {
/*  596 */         tokens[j] = tokenizer.nextToken();
/*      */       }
/*      */     } 
/*  599 */     StringBuilder namespace = new StringBuilder("http://");
/*  600 */     for (int i = 0; i < tokens.length; i++) {
/*  601 */       if (i != 0)
/*  602 */         namespace.append('.'); 
/*  603 */       namespace.append(tokens[i]);
/*      */     } 
/*  605 */     namespace.append('/');
/*  606 */     return namespace.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isServiceException(Class<?> exception) {
/*  615 */     return (EXCEPTION_CLASS.isAssignableFrom(exception) && !RUNTIME_EXCEPTION_CLASS.isAssignableFrom(exception) && !REMOTE_EXCEPTION_CLASS.isAssignableFrom(exception));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processMethod(Method method) {
/*      */     JavaMethodImpl javaMethod;
/*  625 */     WebMethod webMethod = getAnnotation(method, WebMethod.class);
/*  626 */     if (webMethod != null && webMethod.exclude()) {
/*      */       return;
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
/*  646 */     String methodName = method.getName();
/*  647 */     boolean isOneway = (getAnnotation(method, Oneway.class) != null);
/*      */ 
/*      */     
/*  650 */     if (isOneway) {
/*  651 */       for (Class<?> exception : method.getExceptionTypes()) {
/*  652 */         if (isServiceException(exception)) {
/*  653 */           throw new RuntimeModelerException("runtime.modeler.oneway.operation.no.checked.exceptions", new Object[] { this.portClass.getCanonicalName(), methodName, exception.getName() });
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  661 */     if (method.getDeclaringClass() == this.portClass) {
/*  662 */       javaMethod = new JavaMethodImpl(this.model, method, method, this.metadataReader);
/*      */     } else {
/*      */       try {
/*  665 */         Method tmpMethod = this.portClass.getMethod(method.getName(), method.getParameterTypes());
/*      */         
/*  667 */         javaMethod = new JavaMethodImpl(this.model, tmpMethod, method, this.metadataReader);
/*  668 */       } catch (NoSuchMethodException e) {
/*  669 */         throw new RuntimeModelerException("runtime.modeler.method.not.found", new Object[] { method.getName(), this.portClass.getName() });
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  677 */     MEP mep = getMEP(method);
/*  678 */     javaMethod.setMEP(mep);
/*      */     
/*  680 */     String action = null;
/*      */ 
/*      */     
/*  683 */     String operationName = method.getName();
/*  684 */     if (webMethod != null) {
/*  685 */       action = webMethod.action();
/*  686 */       operationName = (webMethod.operationName().length() > 0) ? webMethod.operationName() : operationName;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  692 */     if (this.binding != null) {
/*  693 */       WSDLBoundOperation bo = this.binding.getBinding().get(new QName(this.targetNamespace, operationName));
/*  694 */       if (bo != null) {
/*  695 */         WSDLInput wsdlInput = bo.getOperation().getInput();
/*  696 */         String wsaAction = wsdlInput.getAction();
/*  697 */         if (wsaAction != null && !wsdlInput.isDefaultAction()) {
/*  698 */           action = wsaAction;
/*      */         } else {
/*  700 */           action = bo.getSOAPAction();
/*      */         } 
/*      */       } 
/*      */     } 
/*  704 */     javaMethod.setOperationQName(new QName(this.targetNamespace, operationName));
/*  705 */     SOAPBinding methodBinding = getAnnotation(method, SOAPBinding.class);
/*  706 */     if (methodBinding != null && methodBinding.style() == SOAPBinding.Style.RPC) {
/*  707 */       logger.warning(ModelerMessages.RUNTIMEMODELER_INVALID_SOAPBINDING_ON_METHOD(methodBinding, method.getName(), method.getDeclaringClass().getName()));
/*  708 */     } else if (methodBinding == null && !method.getDeclaringClass().equals(this.portClass)) {
/*  709 */       methodBinding = getAnnotation(method.getDeclaringClass(), SOAPBinding.class);
/*  710 */       if (methodBinding != null && methodBinding.style() == SOAPBinding.Style.RPC && methodBinding.parameterStyle() == SOAPBinding.ParameterStyle.BARE) {
/*  711 */         throw new RuntimeModelerException("runtime.modeler.invalid.soapbinding.parameterstyle", new Object[] { methodBinding, method.getDeclaringClass() });
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  716 */     if (methodBinding != null && this.defaultBinding.getStyle() != methodBinding.style()) {
/*  717 */       throw new RuntimeModelerException("runtime.modeler.soapbinding.conflict", new Object[] { methodBinding.style(), method.getName(), this.defaultBinding.getStyle() });
/*      */     }
/*      */ 
/*      */     
/*  721 */     boolean methodIsWrapped = this.isWrapped;
/*  722 */     SOAPBinding.Style style = this.defaultBinding.getStyle();
/*  723 */     if (methodBinding != null) {
/*  724 */       SOAPBindingImpl mySOAPBinding = createBinding(methodBinding);
/*  725 */       style = mySOAPBinding.getStyle();
/*  726 */       if (action != null)
/*  727 */         mySOAPBinding.setSOAPAction(action); 
/*  728 */       methodIsWrapped = methodBinding.parameterStyle().equals(SOAPBinding.ParameterStyle.WRAPPED);
/*      */       
/*  730 */       javaMethod.setBinding((SOAPBinding)mySOAPBinding);
/*      */     } else {
/*  732 */       SOAPBindingImpl sb = new SOAPBindingImpl((SOAPBinding)this.defaultBinding);
/*  733 */       if (action != null) {
/*  734 */         sb.setSOAPAction(action);
/*      */       } else {
/*  736 */         String defaults = (SOAPVersion.SOAP_11 == sb.getSOAPVersion()) ? "" : null;
/*  737 */         sb.setSOAPAction(defaults);
/*      */       } 
/*  739 */       javaMethod.setBinding((SOAPBinding)sb);
/*      */     } 
/*  741 */     if (!methodIsWrapped) {
/*  742 */       processDocBareMethod(javaMethod, operationName, method);
/*  743 */     } else if (style.equals(SOAPBinding.Style.DOCUMENT)) {
/*  744 */       processDocWrappedMethod(javaMethod, methodName, operationName, method);
/*      */     } else {
/*      */       
/*  747 */       processRpcMethod(javaMethod, methodName, operationName, method);
/*      */     } 
/*  749 */     this.model.addJavaMethod(javaMethod);
/*      */   }
/*      */   
/*      */   private MEP getMEP(Method m) {
/*  753 */     if (getAnnotation(m, Oneway.class) != null) {
/*  754 */       return MEP.ONE_WAY;
/*      */     }
/*  756 */     if (Response.class.isAssignableFrom(m.getReturnType()))
/*  757 */       return MEP.ASYNC_POLL; 
/*  758 */     if (Future.class.isAssignableFrom(m.getReturnType())) {
/*  759 */       return MEP.ASYNC_CALLBACK;
/*      */     }
/*  761 */     return MEP.REQUEST_RESPONSE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processDocWrappedMethod(JavaMethodImpl javaMethod, String methodName, String operationName, Method method) {
/*      */     String requestClassName, responseClassName;
/*  773 */     boolean methodHasHeaderParams = false;
/*  774 */     boolean isOneway = (getAnnotation(method, Oneway.class) != null);
/*  775 */     RequestWrapper reqWrapper = getAnnotation(method, RequestWrapper.class);
/*  776 */     ResponseWrapper resWrapper = getAnnotation(method, ResponseWrapper.class);
/*  777 */     String beanPackage = this.packageName + ".jaxws.";
/*  778 */     if (this.packageName == null || this.packageName.length() == 0) {
/*  779 */       beanPackage = "jaxws.";
/*      */     }
/*      */     
/*  782 */     if (reqWrapper != null && reqWrapper.className().length() > 0) {
/*  783 */       requestClassName = reqWrapper.className();
/*      */     } else {
/*  785 */       requestClassName = beanPackage + capitalize(method.getName());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  790 */     if (resWrapper != null && resWrapper.className().length() > 0) {
/*  791 */       responseClassName = resWrapper.className();
/*      */     } else {
/*  793 */       responseClassName = beanPackage + capitalize(method.getName()) + "Response";
/*      */     } 
/*      */     
/*  796 */     String reqName = operationName;
/*  797 */     String reqNamespace = this.targetNamespace;
/*  798 */     String reqPartName = "parameters";
/*  799 */     if (reqWrapper != null) {
/*  800 */       if (reqWrapper.targetNamespace().length() > 0)
/*  801 */         reqNamespace = reqWrapper.targetNamespace(); 
/*  802 */       if (reqWrapper.localName().length() > 0)
/*  803 */         reqName = reqWrapper.localName(); 
/*      */       try {
/*  805 */         if (reqWrapper.partName().length() > 0)
/*  806 */           reqPartName = reqWrapper.partName(); 
/*  807 */       } catch (LinkageError e) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  812 */     QName reqElementName = new QName(reqNamespace, reqName);
/*  813 */     javaMethod.setRequestPayloadName(reqElementName);
/*  814 */     Class requestClass = getRequestWrapperClass(requestClassName, method, reqElementName);
/*      */     
/*  816 */     Class responseClass = null;
/*  817 */     String resName = operationName + "Response";
/*  818 */     String resNamespace = this.targetNamespace;
/*  819 */     QName resElementName = null;
/*  820 */     String resPartName = "parameters";
/*  821 */     if (!isOneway) {
/*  822 */       if (resWrapper != null) {
/*  823 */         if (resWrapper.targetNamespace().length() > 0)
/*  824 */           resNamespace = resWrapper.targetNamespace(); 
/*  825 */         if (resWrapper.localName().length() > 0)
/*  826 */           resName = resWrapper.localName(); 
/*      */         try {
/*  828 */           if (resWrapper.partName().length() > 0)
/*  829 */             resPartName = resWrapper.partName(); 
/*  830 */         } catch (LinkageError e) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  835 */       resElementName = new QName(resNamespace, resName);
/*  836 */       responseClass = getResponseWrapperClass(responseClassName, method, resElementName);
/*      */     } 
/*      */     
/*  839 */     TypeInfo typeRef = new TypeInfo(reqElementName, requestClass, new Annotation[0]);
/*      */     
/*  841 */     typeRef.setNillable(false);
/*  842 */     WrapperParameter requestWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.IN, 0);
/*      */     
/*  844 */     requestWrapper.setPartName(reqPartName);
/*  845 */     requestWrapper.setBinding(ParameterBinding.BODY);
/*  846 */     javaMethod.addParameter(requestWrapper);
/*  847 */     WrapperParameter responseWrapper = null;
/*  848 */     if (!isOneway) {
/*  849 */       typeRef = new TypeInfo(resElementName, responseClass, new Annotation[0]);
/*  850 */       typeRef.setNillable(false);
/*  851 */       responseWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.OUT, -1);
/*  852 */       javaMethod.addParameter(responseWrapper);
/*  853 */       responseWrapper.setBinding(ParameterBinding.BODY);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  859 */     WebResult webResult = getAnnotation(method, WebResult.class);
/*  860 */     XmlElement xmlElem = getAnnotation(method, XmlElement.class);
/*  861 */     QName resultQName = getReturnQName(method, webResult, xmlElem);
/*  862 */     Class<?> returnType = method.getReturnType();
/*  863 */     boolean isResultHeader = false;
/*  864 */     if (webResult != null) {
/*  865 */       isResultHeader = webResult.header();
/*  866 */       methodHasHeaderParams = (isResultHeader || methodHasHeaderParams);
/*  867 */       if (isResultHeader && xmlElem != null) {
/*  868 */         throw new RuntimeModelerException("@XmlElement cannot be specified on method " + method + " as the return value is bound to header", new Object[0]);
/*      */       }
/*  870 */       if (resultQName.getNamespaceURI().length() == 0 && webResult.header())
/*      */       {
/*  872 */         resultQName = new QName(this.targetNamespace, resultQName.getLocalPart());
/*      */       }
/*      */     } 
/*      */     
/*  876 */     if (javaMethod.isAsync()) {
/*  877 */       returnType = getAsyncReturnType(method, returnType);
/*  878 */       resultQName = new QName("return");
/*      */     } 
/*  880 */     resultQName = qualifyWrappeeIfNeeded(resultQName, resNamespace);
/*  881 */     if (!isOneway && returnType != null && !returnType.getName().equals("void")) {
/*  882 */       Annotation[] rann = getAnnotations(method);
/*  883 */       if (resultQName.getLocalPart() != null) {
/*  884 */         TypeInfo rTypeReference = new TypeInfo(resultQName, returnType, rann);
/*  885 */         this.metadataReader.getProperties(rTypeReference.properties(), method);
/*  886 */         rTypeReference.setGenericType(method.getGenericReturnType());
/*  887 */         ParameterImpl returnParameter = new ParameterImpl(javaMethod, rTypeReference, WebParam.Mode.OUT, -1);
/*  888 */         if (isResultHeader) {
/*  889 */           returnParameter.setBinding(ParameterBinding.HEADER);
/*  890 */           javaMethod.addParameter(returnParameter);
/*      */         } else {
/*  892 */           returnParameter.setBinding(ParameterBinding.BODY);
/*  893 */           responseWrapper.addWrapperChild(returnParameter);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  899 */     Class<?>[] parameterTypes = method.getParameterTypes();
/*  900 */     Type[] genericParameterTypes = method.getGenericParameterTypes();
/*  901 */     Annotation[][] pannotations = getParamAnnotations(method);
/*  902 */     int pos = 0;
/*  903 */     for (Class<?> clazzType : parameterTypes) {
/*  904 */       String partName = null;
/*  905 */       String paramName = "arg" + pos;
/*      */       
/*  907 */       boolean isHeader = false;
/*      */       
/*  909 */       if (!javaMethod.isAsync() || !AsyncHandler.class.isAssignableFrom(clazzType)) {
/*      */ 
/*      */ 
/*      */         
/*  913 */         boolean isHolder = HOLDER_CLASS.isAssignableFrom(clazzType);
/*      */         
/*  915 */         if (isHolder && 
/*  916 */           clazzType == Holder.class) {
/*  917 */           clazzType = BindingHelper.erasure(((ParameterizedType)genericParameterTypes[pos]).getActualTypeArguments()[0]);
/*      */         }
/*      */         
/*  920 */         WebParam.Mode paramMode = isHolder ? WebParam.Mode.INOUT : WebParam.Mode.IN;
/*  921 */         WebParam webParam = null;
/*  922 */         xmlElem = null;
/*  923 */         for (Annotation annotation : pannotations[pos]) {
/*  924 */           if (annotation.annotationType() == WebParam.class) {
/*  925 */             webParam = (WebParam)annotation;
/*  926 */           } else if (annotation.annotationType() == XmlElement.class) {
/*  927 */             xmlElem = (XmlElement)annotation;
/*      */           } 
/*      */         } 
/*  930 */         QName paramQName = getParameterQName(method, webParam, xmlElem, paramName);
/*  931 */         if (webParam != null) {
/*  932 */           isHeader = webParam.header();
/*  933 */           methodHasHeaderParams = (isHeader || methodHasHeaderParams);
/*  934 */           if (isHeader && xmlElem != null) {
/*  935 */             throw new RuntimeModelerException("@XmlElement cannot be specified on method " + method + " parameter that is bound to header", new Object[0]);
/*      */           }
/*  937 */           if (webParam.partName().length() > 0) {
/*  938 */             partName = webParam.partName();
/*      */           } else {
/*  940 */             partName = paramQName.getLocalPart();
/*  941 */           }  if (isHeader && paramQName.getNamespaceURI().equals("")) {
/*  942 */             paramQName = new QName(this.targetNamespace, paramQName.getLocalPart());
/*      */           }
/*  944 */           paramMode = webParam.mode();
/*  945 */           if (isHolder && paramMode == WebParam.Mode.IN)
/*  946 */             paramMode = WebParam.Mode.INOUT; 
/*      */         } 
/*  948 */         paramQName = qualifyWrappeeIfNeeded(paramQName, reqNamespace);
/*  949 */         typeRef = new TypeInfo(paramQName, clazzType, pannotations[pos]);
/*      */         
/*  951 */         this.metadataReader.getProperties(typeRef.properties(), method, pos);
/*  952 */         typeRef.setGenericType(genericParameterTypes[pos]);
/*  953 */         ParameterImpl param = new ParameterImpl(javaMethod, typeRef, paramMode, pos++);
/*      */         
/*  955 */         if (isHeader) {
/*  956 */           param.setBinding(ParameterBinding.HEADER);
/*  957 */           javaMethod.addParameter(param);
/*  958 */           param.setPartName(partName);
/*      */         } else {
/*  960 */           param.setBinding(ParameterBinding.BODY);
/*  961 */           if (paramMode != WebParam.Mode.OUT) {
/*  962 */             requestWrapper.addWrapperChild(param);
/*      */           }
/*  964 */           if (paramMode != WebParam.Mode.IN) {
/*  965 */             if (isOneway) {
/*  966 */               throw new RuntimeModelerException("runtime.modeler.oneway.operation.no.out.parameters", new Object[] { this.portClass.getCanonicalName(), methodName });
/*      */             }
/*      */             
/*  969 */             responseWrapper.addWrapperChild(param);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  977 */     if (methodHasHeaderParams) {
/*  978 */       resPartName = "result";
/*      */     }
/*  980 */     if (responseWrapper != null)
/*  981 */       responseWrapper.setPartName(resPartName); 
/*  982 */     processExceptions(javaMethod, method);
/*      */   }
/*      */   
/*      */   private QName qualifyWrappeeIfNeeded(QName resultQName, String ns) {
/*  986 */     Object o = this.config.properties().get("com.sun.xml.ws.api.model.DocWrappeeNamespapceQualified");
/*  987 */     boolean qualified = (o != null && o instanceof Boolean) ? ((Boolean)o).booleanValue() : false;
/*  988 */     if (qualified && (
/*  989 */       resultQName.getNamespaceURI() == null || "".equals(resultQName.getNamespaceURI()))) {
/*  990 */       return new QName(ns, resultQName.getLocalPart());
/*      */     }
/*      */     
/*  993 */     return resultQName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processRpcMethod(JavaMethodImpl javaMethod, String methodName, String operationName, Method method) {
/*      */     QName resultQName;
/* 1005 */     boolean isOneway = (getAnnotation(method, Oneway.class) != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1012 */     Map<Integer, ParameterImpl> resRpcParams = new TreeMap<Integer, ParameterImpl>();
/* 1013 */     Map<Integer, ParameterImpl> reqRpcParams = new TreeMap<Integer, ParameterImpl>();
/*      */ 
/*      */     
/* 1016 */     String reqNamespace = this.targetNamespace;
/* 1017 */     String respNamespace = this.targetNamespace;
/*      */     
/* 1019 */     if (this.binding != null && SOAPBinding.Style.RPC.equals(this.binding.getBinding().getStyle())) {
/* 1020 */       QName opQName = new QName(this.binding.getBinding().getPortTypeName().getNamespaceURI(), operationName);
/* 1021 */       WSDLBoundOperation op = this.binding.getBinding().get(opQName);
/* 1022 */       if (op != null) {
/*      */         
/* 1024 */         if (op.getRequestNamespace() != null) {
/* 1025 */           reqNamespace = op.getRequestNamespace();
/*      */         }
/*      */ 
/*      */         
/* 1029 */         if (op.getResponseNamespace() != null) {
/* 1030 */           respNamespace = op.getResponseNamespace();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1035 */     QName reqElementName = new QName(reqNamespace, operationName);
/* 1036 */     javaMethod.setRequestPayloadName(reqElementName);
/* 1037 */     QName resElementName = null;
/* 1038 */     if (!isOneway) {
/* 1039 */       resElementName = new QName(respNamespace, operationName + "Response");
/*      */     }
/*      */     
/* 1042 */     Class<WrapperComposite> wrapperType = WrapperComposite.class;
/* 1043 */     TypeInfo typeRef = new TypeInfo(reqElementName, wrapperType, new Annotation[0]);
/* 1044 */     WrapperParameter requestWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.IN, 0);
/* 1045 */     requestWrapper.setInBinding(ParameterBinding.BODY);
/* 1046 */     javaMethod.addParameter(requestWrapper);
/* 1047 */     WrapperParameter responseWrapper = null;
/* 1048 */     if (!isOneway) {
/* 1049 */       typeRef = new TypeInfo(resElementName, wrapperType, new Annotation[0]);
/* 1050 */       responseWrapper = new WrapperParameter(javaMethod, typeRef, WebParam.Mode.OUT, -1);
/* 1051 */       responseWrapper.setOutBinding(ParameterBinding.BODY);
/* 1052 */       javaMethod.addParameter(responseWrapper);
/*      */     } 
/*      */     
/* 1055 */     Class<?> returnType = method.getReturnType();
/* 1056 */     String resultName = "return";
/* 1057 */     String resultTNS = this.targetNamespace;
/* 1058 */     String resultPartName = resultName;
/* 1059 */     boolean isResultHeader = false;
/* 1060 */     WebResult webResult = getAnnotation(method, WebResult.class);
/*      */     
/* 1062 */     if (webResult != null) {
/* 1063 */       isResultHeader = webResult.header();
/* 1064 */       if (webResult.name().length() > 0)
/* 1065 */         resultName = webResult.name(); 
/* 1066 */       if (webResult.partName().length() > 0) {
/* 1067 */         resultPartName = webResult.partName();
/* 1068 */         if (!isResultHeader)
/* 1069 */           resultName = resultPartName; 
/*      */       } else {
/* 1071 */         resultPartName = resultName;
/* 1072 */       }  if (webResult.targetNamespace().length() > 0)
/* 1073 */         resultTNS = webResult.targetNamespace(); 
/* 1074 */       isResultHeader = webResult.header();
/*      */     } 
/*      */     
/* 1077 */     if (isResultHeader) {
/* 1078 */       resultQName = new QName(resultTNS, resultName);
/*      */     } else {
/* 1080 */       resultQName = new QName(resultName);
/*      */     } 
/* 1082 */     if (javaMethod.isAsync()) {
/* 1083 */       returnType = getAsyncReturnType(method, returnType);
/*      */     }
/*      */     
/* 1086 */     if (!isOneway && returnType != null && returnType != void.class) {
/* 1087 */       Annotation[] rann = getAnnotations(method);
/* 1088 */       TypeInfo rTypeReference = new TypeInfo(resultQName, returnType, rann);
/* 1089 */       this.metadataReader.getProperties(rTypeReference.properties(), method);
/* 1090 */       rTypeReference.setGenericType(method.getGenericReturnType());
/* 1091 */       ParameterImpl returnParameter = new ParameterImpl(javaMethod, rTypeReference, WebParam.Mode.OUT, -1);
/* 1092 */       returnParameter.setPartName(resultPartName);
/* 1093 */       if (isResultHeader) {
/* 1094 */         returnParameter.setBinding(ParameterBinding.HEADER);
/* 1095 */         javaMethod.addParameter(returnParameter);
/* 1096 */         rTypeReference.setGlobalElement(true);
/*      */       } else {
/* 1098 */         ParameterBinding rb = getBinding(operationName, resultPartName, false, WebParam.Mode.OUT);
/* 1099 */         returnParameter.setBinding(rb);
/* 1100 */         if (rb.isBody())
/* 1101 */         { rTypeReference.setGlobalElement(false);
/* 1102 */           WSDLPart p = getPart(new QName(this.targetNamespace, operationName), resultPartName, WebParam.Mode.OUT);
/* 1103 */           if (p == null) {
/* 1104 */             resRpcParams.put(Integer.valueOf(resRpcParams.size() + 10000), returnParameter);
/*      */           } else {
/* 1106 */             resRpcParams.put(Integer.valueOf(p.getIndex()), returnParameter);
/*      */           }  }
/* 1108 */         else { javaMethod.addParameter(returnParameter); }
/*      */       
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1114 */     Class<?>[] parameterTypes = method.getParameterTypes();
/* 1115 */     Type[] genericParameterTypes = method.getGenericParameterTypes();
/* 1116 */     Annotation[][] pannotations = getParamAnnotations(method);
/* 1117 */     int pos = 0;
/* 1118 */     for (Class<?> clazzType : parameterTypes) {
/* 1119 */       String paramName = "";
/* 1120 */       String paramNamespace = "";
/* 1121 */       String partName = "";
/* 1122 */       boolean isHeader = false;
/*      */       
/* 1124 */       if (!javaMethod.isAsync() || !AsyncHandler.class.isAssignableFrom(clazzType)) {
/*      */         QName paramQName;
/*      */ 
/*      */         
/* 1128 */         boolean isHolder = HOLDER_CLASS.isAssignableFrom(clazzType);
/*      */         
/* 1130 */         if (isHolder && 
/* 1131 */           clazzType == Holder.class) {
/* 1132 */           clazzType = BindingHelper.erasure(((ParameterizedType)genericParameterTypes[pos]).getActualTypeArguments()[0]);
/*      */         }
/* 1134 */         WebParam.Mode paramMode = isHolder ? WebParam.Mode.INOUT : WebParam.Mode.IN;
/* 1135 */         for (Annotation annotation : pannotations[pos]) {
/* 1136 */           if (annotation.annotationType() == WebParam.class) {
/* 1137 */             WebParam webParam = (WebParam)annotation;
/* 1138 */             paramName = webParam.name();
/* 1139 */             partName = webParam.partName();
/* 1140 */             isHeader = webParam.header();
/* 1141 */             WebParam.Mode mode = webParam.mode();
/* 1142 */             paramNamespace = webParam.targetNamespace();
/* 1143 */             if (isHolder && mode == WebParam.Mode.IN)
/* 1144 */               mode = WebParam.Mode.INOUT; 
/* 1145 */             paramMode = mode;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1150 */         if (paramName.length() == 0) {
/* 1151 */           paramName = "arg" + pos;
/*      */         }
/* 1153 */         if (partName.length() == 0) {
/* 1154 */           partName = paramName;
/* 1155 */         } else if (!isHeader) {
/* 1156 */           paramName = partName;
/*      */         } 
/* 1158 */         if (partName.length() == 0) {
/* 1159 */           partName = paramName;
/*      */         }
/*      */ 
/*      */         
/* 1163 */         if (!isHeader) {
/*      */           
/* 1165 */           paramQName = new QName("", paramName);
/*      */         } else {
/* 1167 */           if (paramNamespace.length() == 0)
/* 1168 */             paramNamespace = this.targetNamespace; 
/* 1169 */           paramQName = new QName(paramNamespace, paramName);
/*      */         } 
/* 1171 */         typeRef = new TypeInfo(paramQName, clazzType, pannotations[pos]);
/*      */         
/* 1173 */         this.metadataReader.getProperties(typeRef.properties(), method, pos);
/* 1174 */         typeRef.setGenericType(genericParameterTypes[pos]);
/* 1175 */         ParameterImpl param = new ParameterImpl(javaMethod, typeRef, paramMode, pos++);
/* 1176 */         param.setPartName(partName);
/*      */         
/* 1178 */         if (paramMode == WebParam.Mode.INOUT) {
/* 1179 */           ParameterBinding pb = getBinding(operationName, partName, isHeader, WebParam.Mode.IN);
/* 1180 */           param.setInBinding(pb);
/* 1181 */           pb = getBinding(operationName, partName, isHeader, WebParam.Mode.OUT);
/* 1182 */           param.setOutBinding(pb);
/*      */         }
/* 1184 */         else if (isHeader) {
/* 1185 */           typeRef.setGlobalElement(true);
/* 1186 */           param.setBinding(ParameterBinding.HEADER);
/*      */         } else {
/* 1188 */           ParameterBinding pb = getBinding(operationName, partName, false, paramMode);
/* 1189 */           param.setBinding(pb);
/*      */         } 
/*      */         
/* 1192 */         if (param.getInBinding().isBody())
/* 1193 */         { typeRef.setGlobalElement(false);
/* 1194 */           if (!param.isOUT()) {
/* 1195 */             WSDLPart p = getPart(new QName(this.targetNamespace, operationName), partName, WebParam.Mode.IN);
/* 1196 */             if (p == null) {
/* 1197 */               reqRpcParams.put(Integer.valueOf(reqRpcParams.size() + 10000), param);
/*      */             } else {
/* 1199 */               reqRpcParams.put(Integer.valueOf(p.getIndex()), param);
/*      */             } 
/*      */           } 
/* 1202 */           if (!param.isIN()) {
/* 1203 */             if (isOneway) {
/* 1204 */               throw new RuntimeModelerException("runtime.modeler.oneway.operation.no.out.parameters", new Object[] { this.portClass.getCanonicalName(), methodName });
/*      */             }
/*      */             
/* 1207 */             WSDLPart p = getPart(new QName(this.targetNamespace, operationName), partName, WebParam.Mode.OUT);
/* 1208 */             if (p == null) {
/* 1209 */               resRpcParams.put(Integer.valueOf(resRpcParams.size() + 10000), param);
/*      */             } else {
/* 1211 */               resRpcParams.put(Integer.valueOf(p.getIndex()), param);
/*      */             } 
/*      */           }  }
/* 1214 */         else { javaMethod.addParameter(param); }
/*      */       
/*      */       } 
/* 1217 */     }  for (ParameterImpl p : reqRpcParams.values())
/* 1218 */       requestWrapper.addWrapperChild(p); 
/* 1219 */     for (ParameterImpl p : resRpcParams.values())
/* 1220 */       responseWrapper.addWrapperChild(p); 
/* 1221 */     processExceptions(javaMethod, method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processExceptions(JavaMethodImpl javaMethod, Method method) {
/* 1231 */     Action actionAnn = getAnnotation(method, Action.class);
/* 1232 */     FaultAction[] faultActions = new FaultAction[0];
/* 1233 */     if (actionAnn != null)
/* 1234 */       faultActions = actionAnn.fault(); 
/* 1235 */     for (Class<?> exception : method.getExceptionTypes()) {
/*      */ 
/*      */       
/* 1238 */       if (EXCEPTION_CLASS.isAssignableFrom(exception))
/*      */       {
/* 1240 */         if (!RUNTIME_EXCEPTION_CLASS.isAssignableFrom(exception) && !REMOTE_EXCEPTION_CLASS.isAssignableFrom(exception)) {
/*      */           Class<?> exceptionBean;
/*      */           
/*      */           Annotation[] anns;
/*      */           
/* 1245 */           WebFault webFault = getAnnotation(exception, WebFault.class);
/* 1246 */           Method faultInfoMethod = getWSDLExceptionFaultInfo(exception);
/* 1247 */           ExceptionType exceptionType = ExceptionType.WSDLException;
/* 1248 */           String namespace = this.targetNamespace;
/* 1249 */           String name = exception.getSimpleName();
/* 1250 */           String beanPackage = this.packageName + ".jaxws.";
/* 1251 */           if (this.packageName.length() == 0)
/* 1252 */             beanPackage = "jaxws."; 
/* 1253 */           String className = beanPackage + name + "Bean";
/* 1254 */           String messageName = exception.getSimpleName();
/* 1255 */           if (webFault != null) {
/* 1256 */             if (webFault.faultBean().length() > 0)
/* 1257 */               className = webFault.faultBean(); 
/* 1258 */             if (webFault.name().length() > 0)
/* 1259 */               name = webFault.name(); 
/* 1260 */             if (webFault.targetNamespace().length() > 0)
/* 1261 */               namespace = webFault.targetNamespace(); 
/* 1262 */             if (webFault.messageName().length() > 0)
/* 1263 */               messageName = webFault.messageName(); 
/*      */           } 
/* 1265 */           if (faultInfoMethod == null) {
/* 1266 */             exceptionBean = getExceptionBeanClass(className, exception, name, namespace);
/* 1267 */             exceptionType = ExceptionType.UserDefined;
/* 1268 */             anns = getAnnotations(exceptionBean);
/*      */           } else {
/* 1270 */             exceptionBean = faultInfoMethod.getReturnType();
/* 1271 */             anns = getAnnotations(faultInfoMethod);
/*      */           } 
/* 1273 */           QName faultName = new QName(namespace, name);
/* 1274 */           TypeInfo typeRef = new TypeInfo(faultName, exceptionBean, anns);
/* 1275 */           CheckedExceptionImpl checkedException = new CheckedExceptionImpl(javaMethod, exception, typeRef, exceptionType);
/*      */           
/* 1277 */           checkedException.setMessageName(messageName);
/* 1278 */           for (FaultAction fa : faultActions) {
/* 1279 */             if (fa.className().equals(exception) && !fa.value().equals("")) {
/* 1280 */               checkedException.setFaultAction(fa.value());
/*      */               break;
/*      */             } 
/*      */           } 
/* 1284 */           javaMethod.addException(checkedException);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Method getWSDLExceptionFaultInfo(Class<?> exception) {
/* 1297 */     if (getAnnotation(exception, WebFault.class) == null)
/* 1298 */       return null; 
/*      */     try {
/* 1300 */       return exception.getMethod("getFaultInfo", new Class[0]);
/* 1301 */     } catch (NoSuchMethodException e) {
/* 1302 */       return null;
/*      */     } 
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
/*      */   protected void processDocBareMethod(JavaMethodImpl javaMethod, String operationName, Method method) {
/* 1315 */     String resultName = operationName + "Response";
/* 1316 */     String resultTNS = this.targetNamespace;
/* 1317 */     String resultPartName = null;
/* 1318 */     boolean isResultHeader = false;
/* 1319 */     WebResult webResult = getAnnotation(method, WebResult.class);
/* 1320 */     if (webResult != null) {
/* 1321 */       if (webResult.name().length() > 0)
/* 1322 */         resultName = webResult.name(); 
/* 1323 */       if (webResult.targetNamespace().length() > 0)
/* 1324 */         resultTNS = webResult.targetNamespace(); 
/* 1325 */       resultPartName = webResult.partName();
/* 1326 */       isResultHeader = webResult.header();
/*      */     } 
/*      */     
/* 1329 */     Class<?> returnType = method.getReturnType();
/* 1330 */     Type gReturnType = method.getGenericReturnType();
/* 1331 */     if (javaMethod.isAsync()) {
/* 1332 */       returnType = getAsyncReturnType(method, returnType);
/*      */     }
/*      */     
/* 1335 */     if (returnType != null && !returnType.getName().equals("void")) {
/* 1336 */       Annotation[] rann = getAnnotations(method);
/* 1337 */       if (resultName != null) {
/* 1338 */         QName responseQName = new QName(resultTNS, resultName);
/* 1339 */         TypeInfo rTypeReference = new TypeInfo(responseQName, returnType, rann);
/* 1340 */         rTypeReference.setGenericType(gReturnType);
/* 1341 */         this.metadataReader.getProperties(rTypeReference.properties(), method);
/* 1342 */         ParameterImpl returnParameter = new ParameterImpl(javaMethod, rTypeReference, WebParam.Mode.OUT, -1);
/*      */         
/* 1344 */         if (resultPartName == null || resultPartName.length() == 0) {
/* 1345 */           resultPartName = resultName;
/*      */         }
/* 1347 */         returnParameter.setPartName(resultPartName);
/* 1348 */         if (isResultHeader) {
/* 1349 */           returnParameter.setBinding(ParameterBinding.HEADER);
/*      */         } else {
/* 1351 */           ParameterBinding rb = getBinding(operationName, resultPartName, false, WebParam.Mode.OUT);
/* 1352 */           returnParameter.setBinding(rb);
/*      */         } 
/* 1354 */         javaMethod.addParameter(returnParameter);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1359 */     Class<?>[] parameterTypes = method.getParameterTypes();
/* 1360 */     Type[] genericParameterTypes = method.getGenericParameterTypes();
/* 1361 */     Annotation[][] pannotations = getParamAnnotations(method);
/* 1362 */     int pos = 0;
/* 1363 */     for (Class<?> clazzType : parameterTypes) {
/* 1364 */       String paramName = operationName;
/* 1365 */       String partName = null;
/* 1366 */       String requestNamespace = this.targetNamespace;
/* 1367 */       boolean isHeader = false;
/*      */ 
/*      */       
/* 1370 */       if (!javaMethod.isAsync() || !AsyncHandler.class.isAssignableFrom(clazzType)) {
/*      */ 
/*      */ 
/*      */         
/* 1374 */         boolean isHolder = HOLDER_CLASS.isAssignableFrom(clazzType);
/*      */         
/* 1376 */         if (isHolder && 
/* 1377 */           clazzType == Holder.class) {
/* 1378 */           clazzType = BindingHelper.erasure(((ParameterizedType)genericParameterTypes[pos]).getActualTypeArguments()[0]);
/*      */         }
/*      */         
/* 1381 */         WebParam.Mode paramMode = isHolder ? WebParam.Mode.INOUT : WebParam.Mode.IN;
/* 1382 */         for (Annotation annotation : pannotations[pos]) {
/* 1383 */           if (annotation.annotationType() == WebParam.class) {
/* 1384 */             WebParam webParam = (WebParam)annotation;
/* 1385 */             paramMode = webParam.mode();
/* 1386 */             if (isHolder && paramMode == WebParam.Mode.IN)
/* 1387 */               paramMode = WebParam.Mode.INOUT; 
/* 1388 */             isHeader = webParam.header();
/* 1389 */             if (isHeader)
/* 1390 */               paramName = "arg" + pos; 
/* 1391 */             if (paramMode == WebParam.Mode.OUT && !isHeader)
/* 1392 */               paramName = operationName + "Response"; 
/* 1393 */             if (webParam.name().length() > 0)
/* 1394 */               paramName = webParam.name(); 
/* 1395 */             partName = webParam.partName();
/* 1396 */             if (!webParam.targetNamespace().equals("")) {
/* 1397 */               requestNamespace = webParam.targetNamespace();
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1403 */         QName requestQName = new QName(requestNamespace, paramName);
/* 1404 */         if (!isHeader && paramMode != WebParam.Mode.OUT) javaMethod.setRequestPayloadName(requestQName);
/*      */         
/* 1406 */         TypeInfo typeRef = new TypeInfo(requestQName, clazzType, pannotations[pos]);
/*      */ 
/*      */         
/* 1409 */         this.metadataReader.getProperties(typeRef.properties(), method, pos);
/* 1410 */         typeRef.setGenericType(genericParameterTypes[pos]);
/* 1411 */         ParameterImpl param = new ParameterImpl(javaMethod, typeRef, paramMode, pos++);
/* 1412 */         if (partName == null || partName.length() == 0) {
/* 1413 */           partName = paramName;
/*      */         }
/* 1415 */         param.setPartName(partName);
/* 1416 */         if (paramMode == WebParam.Mode.INOUT) {
/* 1417 */           ParameterBinding pb = getBinding(operationName, partName, isHeader, WebParam.Mode.IN);
/* 1418 */           param.setInBinding(pb);
/* 1419 */           pb = getBinding(operationName, partName, isHeader, WebParam.Mode.OUT);
/* 1420 */           param.setOutBinding(pb);
/*      */         }
/* 1422 */         else if (isHeader) {
/* 1423 */           param.setBinding(ParameterBinding.HEADER);
/*      */         } else {
/* 1425 */           ParameterBinding pb = getBinding(operationName, partName, false, paramMode);
/* 1426 */           param.setBinding(pb);
/*      */         } 
/*      */         
/* 1429 */         javaMethod.addParameter(param);
/*      */       } 
/* 1431 */     }  validateDocBare(javaMethod);
/* 1432 */     processExceptions(javaMethod, method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateDocBare(JavaMethodImpl javaMethod) {
/* 1442 */     int numInBodyBindings = 0;
/* 1443 */     for (Parameter param : javaMethod.getRequestParameters()) {
/* 1444 */       if (param.getBinding().equals(ParameterBinding.BODY) && param.isIN()) {
/* 1445 */         numInBodyBindings++;
/*      */       }
/* 1447 */       if (numInBodyBindings > 1) {
/* 1448 */         throw new RuntimeModelerException(ModelerMessages.localizableNOT_A_VALID_BARE_METHOD(this.portClass.getName(), javaMethod.getMethod().getName()));
/*      */       }
/*      */     } 
/*      */     
/* 1452 */     int numOutBodyBindings = 0;
/* 1453 */     for (Parameter param : javaMethod.getResponseParameters()) {
/* 1454 */       if (param.getBinding().equals(ParameterBinding.BODY) && param.isOUT()) {
/* 1455 */         numOutBodyBindings++;
/*      */       }
/* 1457 */       if (numOutBodyBindings > 1) {
/* 1458 */         throw new RuntimeModelerException(ModelerMessages.localizableNOT_A_VALID_BARE_METHOD(this.portClass.getName(), javaMethod.getMethod().getName()));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private Class getAsyncReturnType(Method method, Class<?> returnType) {
/* 1464 */     if (Response.class.isAssignableFrom(returnType)) {
/* 1465 */       Type ret = method.getGenericReturnType();
/* 1466 */       return BindingHelper.erasure(((ParameterizedType)ret).getActualTypeArguments()[0]);
/*      */     } 
/* 1468 */     Type[] types = method.getGenericParameterTypes();
/* 1469 */     Class[] params = method.getParameterTypes();
/* 1470 */     int i = 0;
/* 1471 */     for (Class<?> cls : params) {
/* 1472 */       if (AsyncHandler.class.isAssignableFrom(cls)) {
/* 1473 */         return BindingHelper.erasure(((ParameterizedType)types[i]).getActualTypeArguments()[0]);
/*      */       }
/* 1475 */       i++;
/*      */     } 
/*      */     
/* 1478 */     return returnType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String capitalize(String name) {
/* 1487 */     if (name == null || name.length() == 0) {
/* 1488 */       return name;
/*      */     }
/* 1490 */     char[] chars = name.toCharArray();
/* 1491 */     chars[0] = Character.toUpperCase(chars[0]);
/* 1492 */     return new String(chars);
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
/*      */   public static QName getServiceName(Class<?> implClass) {
/* 1504 */     return getServiceName(implClass, (MetadataReader)null);
/*      */   }
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass, boolean isStandard) {
/* 1508 */     return getServiceName(implClass, null, isStandard);
/*      */   }
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass, MetadataReader reader) {
/* 1512 */     return getServiceName(implClass, reader, true);
/*      */   }
/*      */   
/*      */   public static QName getServiceName(Class<?> implClass, MetadataReader reader, boolean isStandard) {
/* 1516 */     if (implClass.isInterface()) {
/* 1517 */       throw new RuntimeModelerException("runtime.modeler.cannot.get.serviceName.from.interface", new Object[] { implClass.getCanonicalName() });
/*      */     }
/*      */ 
/*      */     
/* 1521 */     String name = implClass.getSimpleName() + "Service";
/* 1522 */     String packageName = "";
/* 1523 */     if (implClass.getPackage() != null) {
/* 1524 */       packageName = implClass.getPackage().getName();
/*      */     }
/* 1526 */     WebService webService = getAnnotation(WebService.class, implClass, reader);
/* 1527 */     if (isStandard && webService == null) {
/* 1528 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { implClass.getCanonicalName() });
/*      */     }
/*      */     
/* 1531 */     if (webService != null && webService.serviceName().length() > 0) {
/* 1532 */       name = webService.serviceName();
/*      */     }
/* 1534 */     String targetNamespace = getNamespace(packageName);
/* 1535 */     if (webService != null && webService.targetNamespace().length() > 0) {
/* 1536 */       targetNamespace = webService.targetNamespace();
/* 1537 */     } else if (targetNamespace == null) {
/* 1538 */       throw new RuntimeModelerException("runtime.modeler.no.package", new Object[] { implClass.getName() });
/*      */     } 
/*      */     
/* 1541 */     return new QName(targetNamespace, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName getPortName(Class<?> implClass, String targetNamespace) {
/* 1551 */     return getPortName(implClass, (MetadataReader)null, targetNamespace);
/*      */   }
/*      */   
/*      */   public static QName getPortName(Class<?> implClass, String targetNamespace, boolean isStandard) {
/* 1555 */     return getPortName(implClass, null, targetNamespace, isStandard);
/*      */   }
/*      */   
/*      */   public static QName getPortName(Class<?> implClass, MetadataReader reader, String targetNamespace) {
/* 1559 */     return getPortName(implClass, reader, targetNamespace, true);
/*      */   }
/*      */   public static QName getPortName(Class<?> implClass, MetadataReader reader, String targetNamespace, boolean isStandard) {
/*      */     String name;
/* 1563 */     WebService webService = getAnnotation(WebService.class, implClass, reader);
/* 1564 */     if (isStandard && webService == null) {
/* 1565 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { implClass.getCanonicalName() });
/*      */     }
/*      */ 
/*      */     
/* 1569 */     if (webService != null && webService.portName().length() > 0) {
/* 1570 */       name = webService.portName();
/* 1571 */     } else if (webService != null && webService.name().length() > 0) {
/* 1572 */       name = webService.name() + "Port";
/*      */     } else {
/* 1574 */       name = implClass.getSimpleName() + "Port";
/*      */     } 
/*      */     
/* 1577 */     if (targetNamespace == null) {
/* 1578 */       if (webService != null && webService.targetNamespace().length() > 0) {
/* 1579 */         targetNamespace = webService.targetNamespace();
/*      */       } else {
/* 1581 */         String packageName = null;
/* 1582 */         if (implClass.getPackage() != null) {
/* 1583 */           packageName = implClass.getPackage().getName();
/*      */         }
/* 1585 */         if (packageName != null) {
/* 1586 */           targetNamespace = getNamespace(packageName);
/*      */         }
/* 1588 */         if (targetNamespace == null) {
/* 1589 */           throw new RuntimeModelerException("runtime.modeler.no.package", new Object[] { implClass.getName() });
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1596 */     return new QName(targetNamespace, name);
/*      */   }
/*      */   
/*      */   static <A extends Annotation> A getAnnotation(Class<A> t, Class<?> cls, MetadataReader reader) {
/* 1600 */     return (reader == null) ? cls.<A>getAnnotation(t) : (A)reader.getAnnotation(t, cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static QName getPortTypeName(Class<?> implOrSeiClass) {
/* 1609 */     return getPortTypeName(implOrSeiClass, null, null);
/*      */   }
/*      */   
/*      */   public static QName getPortTypeName(Class<?> implOrSeiClass, MetadataReader metadataReader) {
/* 1613 */     return getPortTypeName(implOrSeiClass, null, metadataReader);
/*      */   }
/*      */   
/*      */   public static QName getPortTypeName(Class<?> implOrSeiClass, String tns, MetadataReader reader) {
/* 1617 */     assert implOrSeiClass != null;
/* 1618 */     WebService webService = getAnnotation(WebService.class, implOrSeiClass, reader);
/* 1619 */     Class<?> clazz = implOrSeiClass;
/* 1620 */     if (webService == null) {
/* 1621 */       throw new RuntimeModelerException("runtime.modeler.no.webservice.annotation", new Object[] { implOrSeiClass.getCanonicalName() });
/*      */     }
/*      */     
/* 1624 */     if (!implOrSeiClass.isInterface()) {
/* 1625 */       String epi = webService.endpointInterface();
/* 1626 */       if (epi.length() > 0) {
/*      */         try {
/* 1628 */           clazz = Thread.currentThread().getContextClassLoader().loadClass(epi);
/* 1629 */         } catch (ClassNotFoundException e) {
/* 1630 */           throw new RuntimeModelerException("runtime.modeler.class.not.found", new Object[] { epi });
/*      */         } 
/* 1632 */         WebService ws = getAnnotation(WebService.class, clazz, reader);
/* 1633 */         if (ws == null) {
/* 1634 */           throw new RuntimeModelerException("runtime.modeler.endpoint.interface.no.webservice", new Object[] { webService.endpointInterface() });
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1640 */     webService = getAnnotation(WebService.class, clazz, reader);
/* 1641 */     String name = webService.name();
/* 1642 */     if (name.length() == 0) {
/* 1643 */       name = clazz.getSimpleName();
/*      */     }
/* 1645 */     if (tns == null || "".equals(tns.trim())) tns = webService.targetNamespace(); 
/* 1646 */     if (tns.length() == 0)
/* 1647 */       tns = getNamespace(clazz.getPackage().getName()); 
/* 1648 */     if (tns == null) {
/* 1649 */       throw new RuntimeModelerException("runtime.modeler.no.package", new Object[] { clazz.getName() });
/*      */     }
/* 1651 */     return new QName(tns, name);
/*      */   }
/*      */   
/*      */   private ParameterBinding getBinding(String operation, String part, boolean isHeader, WebParam.Mode mode) {
/* 1655 */     if (this.binding == null) {
/* 1656 */       if (isHeader) {
/* 1657 */         return ParameterBinding.HEADER;
/*      */       }
/* 1659 */       return ParameterBinding.BODY;
/*      */     } 
/* 1661 */     QName opName = new QName(this.binding.getBinding().getPortType().getName().getNamespaceURI(), operation);
/* 1662 */     return this.binding.getBinding().getBinding(opName, part, mode);
/*      */   }
/*      */   
/*      */   private WSDLPart getPart(QName opName, String partName, WebParam.Mode mode) {
/* 1666 */     if (this.binding != null) {
/* 1667 */       WSDLBoundOperation bo = this.binding.getBinding().get(opName);
/* 1668 */       if (bo != null)
/* 1669 */         return bo.getPart(partName, mode); 
/*      */     } 
/* 1671 */     return null;
/*      */   }
/*      */   
/*      */   private static Boolean getBooleanSystemProperty(final String prop) {
/* 1675 */     return AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */         {
/*      */           public Boolean run() {
/* 1678 */             String value = System.getProperty(prop);
/* 1679 */             return (value != null) ? Boolean.valueOf(value) : Boolean.FALSE;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private static QName getReturnQName(Method method, WebResult webResult, XmlElement xmlElem) {
/* 1686 */     String webResultName = null;
/* 1687 */     if (webResult != null && webResult.name().length() > 0) {
/* 1688 */       webResultName = webResult.name();
/*      */     }
/* 1690 */     String xmlElemName = null;
/* 1691 */     if (xmlElem != null && !xmlElem.name().equals("##default")) {
/* 1692 */       xmlElemName = xmlElem.name();
/*      */     }
/* 1694 */     if (xmlElemName != null && webResultName != null && !xmlElemName.equals(webResultName)) {
/* 1695 */       throw new RuntimeModelerException("@XmlElement(name)=" + xmlElemName + " and @WebResult(name)=" + webResultName + " are different for method " + method, new Object[0]);
/*      */     }
/* 1697 */     String localPart = "return";
/* 1698 */     if (webResultName != null) {
/* 1699 */       localPart = webResultName;
/* 1700 */     } else if (xmlElemName != null) {
/* 1701 */       localPart = xmlElemName;
/*      */     } 
/*      */     
/* 1704 */     String webResultNS = null;
/* 1705 */     if (webResult != null && webResult.targetNamespace().length() > 0) {
/* 1706 */       webResultNS = webResult.targetNamespace();
/*      */     }
/* 1708 */     String xmlElemNS = null;
/* 1709 */     if (xmlElem != null && !xmlElem.namespace().equals("##default")) {
/* 1710 */       xmlElemNS = xmlElem.namespace();
/*      */     }
/* 1712 */     if (xmlElemNS != null && webResultNS != null && !xmlElemNS.equals(webResultNS)) {
/* 1713 */       throw new RuntimeModelerException("@XmlElement(namespace)=" + xmlElemNS + " and @WebResult(targetNamespace)=" + webResultNS + " are different for method " + method, new Object[0]);
/*      */     }
/* 1715 */     String ns = "";
/* 1716 */     if (webResultNS != null) {
/* 1717 */       ns = webResultNS;
/* 1718 */     } else if (xmlElemNS != null) {
/* 1719 */       ns = xmlElemNS;
/*      */     } 
/*      */     
/* 1722 */     return new QName(ns, localPart);
/*      */   }
/*      */   
/*      */   private static QName getParameterQName(Method method, WebParam webParam, XmlElement xmlElem, String paramDefault) {
/* 1726 */     String webParamName = null;
/* 1727 */     if (webParam != null && webParam.name().length() > 0) {
/* 1728 */       webParamName = webParam.name();
/*      */     }
/* 1730 */     String xmlElemName = null;
/* 1731 */     if (xmlElem != null && !xmlElem.name().equals("##default")) {
/* 1732 */       xmlElemName = xmlElem.name();
/*      */     }
/* 1734 */     if (xmlElemName != null && webParamName != null && !xmlElemName.equals(webParamName)) {
/* 1735 */       throw new RuntimeModelerException("@XmlElement(name)=" + xmlElemName + " and @WebParam(name)=" + webParamName + " are different for method " + method, new Object[0]);
/*      */     }
/* 1737 */     String localPart = paramDefault;
/* 1738 */     if (webParamName != null) {
/* 1739 */       localPart = webParamName;
/* 1740 */     } else if (xmlElemName != null) {
/* 1741 */       localPart = xmlElemName;
/*      */     } 
/*      */     
/* 1744 */     String webParamNS = null;
/* 1745 */     if (webParam != null && webParam.targetNamespace().length() > 0) {
/* 1746 */       webParamNS = webParam.targetNamespace();
/*      */     }
/* 1748 */     String xmlElemNS = null;
/* 1749 */     if (xmlElem != null && !xmlElem.namespace().equals("##default")) {
/* 1750 */       xmlElemNS = xmlElem.namespace();
/*      */     }
/* 1752 */     if (xmlElemNS != null && webParamNS != null && !xmlElemNS.equals(webParamNS)) {
/* 1753 */       throw new RuntimeModelerException("@XmlElement(namespace)=" + xmlElemNS + " and @WebParam(targetNamespace)=" + webParamNS + " are different for method " + method, new Object[0]);
/*      */     }
/* 1755 */     String ns = "";
/* 1756 */     if (webParamNS != null) {
/* 1757 */       ns = webParamNS;
/* 1758 */     } else if (xmlElemNS != null) {
/* 1759 */       ns = xmlElemNS;
/*      */     } 
/*      */     
/* 1762 */     return new QName(ns, localPart);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\RuntimeModeler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */