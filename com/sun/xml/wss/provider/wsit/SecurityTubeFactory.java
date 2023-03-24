/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.ClientPipeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.assembler.ServerPipelineHook;
/*     */ import com.sun.xml.ws.assembler.dev.ClientPipelineHook;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.MetroClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.TubeFactory;
/*     */ import com.sun.xml.ws.assembler.dev.TubelineAssemblyContextUpdater;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*     */ import com.sun.xml.ws.security.encoding.LazyStreamCodec;
/*     */ import com.sun.xml.ws.security.impl.policy.SecurityFeatureConfigurator;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*     */ import com.sun.xml.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.config.SecurityConfigProvider;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.jaxws.impl.SecurityClientTube;
/*     */ import com.sun.xml.wss.jaxws.impl.SecurityServerTube;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import com.sun.xml.xwss.XWSSClientTube;
/*     */ import com.sun.xml.xwss.XWSSServerTube;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.message.config.AuthConfigFactory;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SecurityTubeFactory
/*     */   implements TubeFactory, TubelineAssemblyContextUpdater
/*     */ {
/*  93 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private static final String SERVLET_CONTEXT_CLASSNAME = "javax.servlet.ServletContext";
/*     */ 
/*     */   
/*     */   private static final String ENDPOINT = "ENDPOINT";
/*     */   
/*     */   private static final String NEXT_PIPE = "NEXT_PIPE";
/*     */   
/*     */   private static final String POLICY = "POLICY";
/*     */   
/*     */   private static final String SEI_MODEL = "SEI_MODEL";
/*     */   
/*     */   private static final String WSDL_MODEL = "WSDL_MODEL";
/*     */   
/*     */   private static final String GF_SERVER_SEC_PIPE = "com.sun.enterprise.webservice.CommonServerSecurityPipe";
/*     */   
/* 111 */   private static final boolean disable = Boolean.getBoolean("DISABLE_XWSS_SECURITY");
/* 112 */   private static long maxNonceAge = SecurityConfigProvider.INSTANCE.getMaxNonceAge(); static {
/* 113 */     if (maxNonceAge == 900000L) {
/* 114 */       String maxNAge = System.getProperty("MAX_NONCE_AGE");
/* 115 */       maxNonceAge = (maxNAge != null) ? Long.parseLong(maxNAge) : 900000L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void prepareContext(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 120 */     if (isSecurityEnabled(context.getPolicyMap(), context.getWsdlPort())) {
/* 121 */       context.setCodec(createSecurityCodec(context.getBinding()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void prepareContext(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 126 */     if (isSecurityEnabled(context.getPolicyMap(), context.getWsdlPort())) {
/* 127 */       context.setCodec(createSecurityCodec(context.getEndpoint().getBinding()));
/*     */     }
/*     */   }
/*     */   
/*     */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 132 */     if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
/* 133 */       initHaBackingStores(context.getEndpoint());
/*     */     }
/*     */ 
/*     */     
/* 137 */     ServerPipelineHook[] hooks = getServerTubeLineHooks();
/* 138 */     ServerPipelineHook hook = null;
/* 139 */     if (hooks != null && hooks.length > 0 && hooks[0] instanceof ServerPipeCreator) {
/*     */       
/* 141 */       hook = hooks[0];
/*     */ 
/*     */       
/* 144 */       initializeJMAC();
/*     */     } else {
/* 146 */       hook = (ServerPipelineHook)context.getEndpoint().getContainer().getSPI(ServerPipelineHook.class);
/*     */     } 
/*     */     
/* 149 */     if (hook != null) {
/*     */       
/* 151 */       Tube head = context.getTubelineHead();
/* 152 */       Tube securityTube = hook.createSecurityTube(context);
/* 153 */       if (head == securityTube) {
/*     */ 
/*     */ 
/*     */         
/* 157 */         Pipe securityPipe = hook.createSecurityPipe(context.getPolicyMap(), context.getSEIModel(), context.getWsdlPort(), context.getEndpoint(), context.getAdaptedTubelineHead());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 163 */         securityTube = PipeAdapter.adapt(securityPipe);
/*     */       } 
/* 165 */       return securityTube;
/* 166 */     }  if (isSecurityEnabled(context.getPolicyMap(), context.getWsdlPort())) {
/* 167 */       if (hooks != null && hooks.length == 0)
/* 168 */         return createSecurityTube(context); 
/* 169 */       if (hooks != null && hooks.length > 0) {
/* 170 */         hook = hooks[0];
/* 171 */         Tube head = context.getTubelineHead();
/* 172 */         Tube securityTube = hook.createSecurityTube(context);
/* 173 */         if (head == securityTube) {
/*     */ 
/*     */ 
/*     */           
/* 177 */           Pipe securityPipe = hook.createSecurityPipe(context.getPolicyMap(), context.getSEIModel(), context.getWsdlPort(), context.getEndpoint(), context.getAdaptedTubelineHead());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           securityTube = PipeAdapter.adapt(securityPipe);
/*     */         } 
/* 185 */         return securityTube;
/*     */       } 
/*     */       
/* 188 */       log.log(Level.FINE, "cannot not use Unified Tube.");
/*     */       
/* 190 */       return (Tube)new SecurityServerTube(context, context.getTubelineHead());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 196 */       if (!context.isPolicyAvailable() && isSecurityConfigPresent(context)) {
/* 197 */         return initializeXWSSServerTube(context);
/*     */       }
/* 199 */     } catch (NoClassDefFoundError err) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     return context.getTubelineHead();
/*     */   }
/*     */   
/*     */   private void initHaBackingStores(WSEndpoint endpoint) {
/* 208 */     boolean wasNonceBsInitialized = false;
/* 209 */     boolean wasScBsInitialized = false;
/*     */     
/* 211 */     for (WebServiceFeature _feature : endpoint.getBinding().getFeatures()) {
/* 212 */       if (_feature instanceof SecurityFeatureConfigurator.SecurityStickyFeature) {
/* 213 */         SecurityFeatureConfigurator.SecurityStickyFeature feature = (SecurityFeatureConfigurator.SecurityStickyFeature)_feature;
/* 214 */         if (!wasNonceBsInitialized && feature.isNonceManagerUsed()) {
/* 215 */           NonceManager.getInstance(maxNonceAge, endpoint);
/* 216 */           wasNonceBsInitialized = true;
/*     */         } 
/* 218 */         if (!wasScBsInitialized && feature.isScUsed()) {
/* 219 */           SessionManager.getSessionManager(endpoint, true, null);
/* 220 */           wasScBsInitialized = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 227 */     ClientPipelineHook hook = null;
/* 228 */     ClientPipelineHook[] hooks = getClientTublineHooks(context);
/* 229 */     if (context.getSEIModel() != null) {
/* 230 */       JAXBUtil.setSEIJAXBContext(context.getSEIModel().getJAXBContext());
/*     */     }
/* 232 */     if (hooks != null && hooks.length > 0) {
/* 233 */       ClientPipelineHook[] arr$ = hooks; int len$ = arr$.length, i$ = 0; if (i$ < len$) { ClientPipelineHook h = arr$[i$];
/* 234 */         if (h instanceof ClientPipeCreator) {
/*     */           
/* 236 */           hook = h;
/*     */ 
/*     */           
/* 239 */           initializeJMAC();
/*     */         } else {
/*     */           
/* 242 */           hook = h;
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 247 */     if (hook == null)
/*     */     {
/* 249 */       hook = (ClientPipelineHook)context.getContainer().getSPI(ClientPipelineHook.class);
/*     */     }
/*     */ 
/*     */     
/* 253 */     if (hook != null) {
/* 254 */       Tube head = context.getTubelineHead();
/* 255 */       Tube securityTube = hook.createSecurityTube(context);
/* 256 */       if (head == securityTube) {
/*     */ 
/*     */         
/* 259 */         ClientPipeAssemblerContext pipeContext = new ClientPipeAssemblerContext(context.getAddress(), context.getWsdlPort(), context.getService(), context.getBinding(), context.getContainer());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 265 */         Pipe securityPipe = hook.createSecurityPipe(context.getPolicyMap(), pipeContext, context.getAdaptedTubelineHead());
/* 266 */         if (isSecurityEnabled(context.getPolicyMap(), context.getWsdlPort())) {
/* 267 */           ((MetroClientTubelineAssemblyContext)context).setScInitiator((SecureConversationInitiator)securityPipe);
/*     */         }
/* 269 */         securityTube = PipeAdapter.adapt(securityPipe);
/*     */       } 
/* 271 */       return securityTube;
/* 272 */     }  if (isSecurityEnabled(context.getPolicyMap(), context.getWsdlPort())) {
/*     */ 
/*     */       
/* 275 */       SecurityClientTube securityClientTube = new SecurityClientTube(context, context.getTubelineHead());
/*     */       
/* 277 */       ((MetroClientTubelineAssemblyContext)context).setScInitiator((SecureConversationInitiator)securityClientTube);
/*     */       
/* 279 */       return (Tube)securityClientTube;
/* 280 */     }  if (!context.isPolicyAvailable() && isSecurityConfigPresent(context))
/*     */     {
/*     */       
/* 283 */       return initializeXWSSClientTube(context);
/*     */     }
/* 285 */     return context.getTubelineHead();
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientPipelineHook[] getClientTublineHooks(ClientTubelineAssemblyContext context) {
/*     */     try {
/* 291 */       ClientPipelineHook[] hooks = loadSPs(ClientPipelineHook.class);
/* 292 */       if (hooks != null && hooks.length > 0) {
/* 293 */         return hooks;
/*     */       }
/* 295 */     } catch (ServiceConfigurationError ex) {
/* 296 */       if (ex.getCause() instanceof InstantiationException) {
/* 297 */         return new ClientPipelineHook[0];
/*     */       }
/* 299 */       return null;
/*     */     } 
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSecurityEnabled(PolicyMap policyMap, WSDLPort wsdlPort) {
/* 312 */     if (policyMap == null || wsdlPort == null) {
/* 313 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 317 */       PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(wsdlPort.getOwner().getName(), wsdlPort.getName());
/*     */       
/* 319 */       Policy policy = policyMap.getEndpointEffectivePolicy(endpointKey);
/*     */       
/* 321 */       if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri)))
/*     */       {
/*     */ 
/*     */         
/* 325 */         return true;
/*     */       }
/*     */       
/* 328 */       for (WSDLBoundOperation wbo : wsdlPort.getBinding().getBindingOperations()) {
/* 329 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(wsdlPort.getOwner().getName(), wsdlPort.getName(), wbo.getName());
/*     */ 
/*     */         
/* 332 */         policy = policyMap.getOperationEffectivePolicy(operationKey);
/* 333 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 336 */           return true;
/*     */         }
/*     */         
/* 339 */         policy = policyMap.getInputMessageEffectivePolicy(operationKey);
/* 340 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 343 */           return true;
/*     */         }
/*     */         
/* 346 */         policy = policyMap.getOutputMessageEffectivePolicy(operationKey);
/* 347 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 350 */           return true;
/*     */         }
/*     */         
/* 353 */         policy = policyMap.getFaultMessageEffectivePolicy(operationKey);
/* 354 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 357 */           return true;
/*     */         }
/*     */       } 
/* 360 */     } catch (PolicyException e) {
/* 361 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 364 */     return false;
/*     */   }
/*     */   
/*     */   private Codec createSecurityCodec(WSBinding binding) {
/* 368 */     StreamSOAPCodec primaryCodec = Codecs.createSOAPEnvelopeXmlCodec(binding.getSOAPVersion());
/* 369 */     LazyStreamCodec lsc = new LazyStreamCodec(primaryCodec);
/* 370 */     return (Codec)Codecs.createSOAPBindingCodec(binding, (StreamSOAPCodec)lsc);
/*     */   }
/*     */   
/*     */   private static <P> P[] loadSPs(Class<P> svcClass) {
/* 374 */     return (P[])ServiceFinder.find(svcClass).toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerPipelineHook[] getServerTubeLineHooks() {
/*     */     try {
/* 382 */       ServerPipelineHook[] hooks = loadSPs(ServerPipelineHook.class);
/* 383 */       if (hooks != null && hooks.length > 0) {
/* 384 */         return hooks;
/*     */       }
/* 386 */     } catch (ServiceConfigurationError ex) {
/*     */       
/* 388 */       if (ex.getCause() instanceof InstantiationException) {
/* 389 */         return new ServerPipelineHook[0];
/*     */       }
/* 391 */       return null;
/*     */     } 
/* 393 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSecurityConfigPresent(ClientTubelineAssemblyContext context) {
/*     */     try {
/* 399 */       String configUrl = "META-INF/client_security_config.xml";
/* 400 */       URL url = SecurityUtil.loadFromClasspath(configUrl);
/* 401 */       if (url != null) {
/* 402 */         return true;
/*     */       }
/* 404 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 411 */     if (disable) {
/* 412 */       return false;
/*     */     }
/* 414 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSecurityConfigPresent(ServerTubelineAssemblyContext context) {
/* 419 */     QName serviceQName = context.getEndpoint().getServiceName();
/*     */     
/* 421 */     String serviceLocalName = serviceQName.getLocalPart();
/* 422 */     Container container = context.getEndpoint().getContainer();
/*     */     
/* 424 */     Object ctxt = null;
/* 425 */     if (container != null) {
/*     */       try {
/* 427 */         Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 428 */         ctxt = container.getSPI(contextClass);
/* 429 */       } catch (ClassNotFoundException e) {
/* 430 */         log.log(Level.WARNING, LogStringsMessages.WSITPVD_0066_SERVLET_CONTEXT_NOTFOUND(), e);
/*     */       } 
/*     */     }
/* 433 */     String serverName = "server";
/* 434 */     if (ctxt != null) {
/*     */       
/*     */       try {
/* 437 */         String serverConfig = "/WEB-INF/" + serverName + "_" + "security_config.xml";
/* 438 */         URL url = SecurityUtil.loadFromContext(serverConfig, ctxt);
/*     */         
/* 440 */         if (url == null) {
/* 441 */           serverConfig = "/WEB-INF/" + serviceLocalName + "_" + "security_config.xml";
/* 442 */           url = SecurityUtil.loadFromContext(serverConfig, ctxt);
/*     */         } 
/*     */         
/* 445 */         if (url != null) {
/* 446 */           return true;
/*     */         }
/* 448 */       } catch (XWSSecurityRuntimeException ex) {
/*     */         
/* 450 */         return false;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 455 */       String serverConfig = "META-INF/" + serverName + "_" + "security_config.xml";
/* 456 */       URL url = SecurityUtil.loadFromClasspath(serverConfig);
/* 457 */       if (url == null) {
/* 458 */         serverConfig = "META-INF/" + serviceLocalName + "_" + "security_config.xml";
/* 459 */         url = SecurityUtil.loadFromClasspath(serverConfig);
/*     */       } 
/*     */       
/* 462 */       if (url != null) {
/* 463 */         return true;
/*     */       }
/*     */     } 
/* 466 */     return false;
/*     */   }
/*     */   
/*     */   private Tube initializeXWSSClientTube(ClientTubelineAssemblyContext context) {
/* 470 */     return (Tube)new XWSSClientTube(context.getWsdlPort(), context.getService(), context.getBinding(), context.getTubelineHead());
/*     */   }
/*     */   
/*     */   private Tube initializeXWSSServerTube(ServerTubelineAssemblyContext context) {
/* 474 */     return (Tube)new XWSSServerTube(context.getEndpoint(), context.getWsdlPort(), context.getTubelineHead());
/*     */   }
/*     */ 
/*     */   
/*     */   private Tube createSecurityTube(ServerTubelineAssemblyContext context) {
/* 479 */     HashMap<Object, Object> props = new HashMap<Object, Object>();
/* 480 */     props.put("POLICY", context.getPolicyMap());
/* 481 */     props.put("SEI_MODEL", context.getSEIModel());
/* 482 */     props.put("WSDL_MODEL", context.getWsdlPort());
/* 483 */     props.put("ENDPOINT", context.getEndpoint());
/* 484 */     props.put("NEXT_PIPE", context.getAdaptedTubelineHead());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 489 */       ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 490 */       Class<?> gfServerPipeClass = null;
/* 491 */       if (loader != null) {
/* 492 */         gfServerPipeClass = loader.loadClass("com.sun.enterprise.webservice.CommonServerSecurityPipe");
/*     */       } else {
/* 494 */         gfServerPipeClass = Class.forName("com.sun.enterprise.webservice.CommonServerSecurityPipe");
/*     */       } 
/* 496 */       if (gfServerPipeClass != null) {
/*     */         
/* 498 */         Constructor[] ctors = (Constructor[])gfServerPipeClass.getDeclaredConstructors();
/* 499 */         Constructor<Pipe> ctor = null;
/* 500 */         for (int i = 0; i < ctors.length; i++) {
/* 501 */           ctor = ctors[i];
/* 502 */           Class[] paramTypes = ctor.getParameterTypes();
/* 503 */           if (paramTypes[0].equals(Map.class)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 508 */         if (ctor != null) {
/* 509 */           return PipeAdapter.adapt(ctor.newInstance(new Object[] { props, context.getAdaptedTubelineHead(), Boolean.valueOf(false) }));
/*     */         }
/*     */       } 
/*     */       
/* 513 */       return context.getTubelineHead();
/* 514 */     } catch (InstantiationException ex) {
/* 515 */       throw new WebServiceException(ex);
/* 516 */     } catch (IllegalAccessException ex) {
/* 517 */       throw new WebServiceException(ex);
/* 518 */     } catch (IllegalArgumentException ex) {
/* 519 */       throw new WebServiceException(ex);
/* 520 */     } catch (InvocationTargetException ex) {
/* 521 */       throw new WebServiceException(ex);
/* 522 */     } catch (SecurityException ex) {
/* 523 */       throw new WebServiceException(ex);
/* 524 */     } catch (ClassNotFoundException ex) {
/* 525 */       throw new WebServiceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeJMAC() {
/* 533 */     final ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 534 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public Object run()
/*     */           {
/* 542 */             AuthConfigFactory factory = AuthConfigFactory.getFactory();
/* 543 */             if (factory == null || !(factory instanceof JMACAuthConfigFactory)) {
/* 544 */               AuthConfigFactory.setFactory(new JMACAuthConfigFactory(loader));
/*     */             }
/* 546 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\SecurityTubeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */