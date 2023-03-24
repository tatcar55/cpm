/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.InstanceResolver;
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.server.EndpointFactory;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapterList;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.ws.Binding;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.EndpointContext;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.WebServicePermission;
/*     */ import javax.xml.ws.spi.Invoker;
/*     */ import javax.xml.ws.spi.http.HttpContext;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.EntityResolver;
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
/*     */ public class EndpointImpl
/*     */   extends Endpoint
/*     */ {
/* 100 */   private static final WebServicePermission ENDPOINT_PUBLISH_PERMISSION = new WebServicePermission("publishEndpoint");
/*     */   
/*     */   private Object actualEndpoint;
/*     */   
/*     */   private final WSBinding binding;
/*     */   
/*     */   @Nullable
/*     */   private final Object implementor;
/*     */   
/*     */   private List<Source> metadata;
/*     */   
/*     */   private Executor executor;
/*     */   
/*     */   private Map<String, Object> properties;
/*     */   
/*     */   private boolean stopped;
/*     */   
/*     */   @Nullable
/*     */   private EndpointContext endpointContext;
/*     */   
/*     */   @NotNull
/*     */   private final Class<?> implClass;
/*     */   
/*     */   private final Invoker invoker;
/*     */   
/*     */   private Container container;
/*     */   
/*     */   public EndpointImpl(@NotNull BindingID bindingId, @NotNull Object impl, WebServiceFeature... features) {
/* 128 */     this(bindingId, impl, impl.getClass(), InstanceResolver.createSingleton(impl).createInvoker(), features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointImpl(@NotNull BindingID bindingId, @NotNull Class implClass, Invoker invoker, WebServiceFeature... features) {
/* 135 */     this(bindingId, null, implClass, new InvokerImpl(invoker), features);
/*     */   }
/*     */   
/*     */   private EndpointImpl(@NotNull BindingID bindingId, Object impl, @NotNull Class<?> implClass, Invoker invoker, WebServiceFeature... features) {
/*     */     this.properties = Collections.emptyMap();
/* 140 */     this.binding = (WSBinding)BindingImpl.create(bindingId, features);
/* 141 */     this.implClass = implClass;
/* 142 */     this.invoker = invoker;
/* 143 */     this.implementor = impl;
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
/*     */   public EndpointImpl(WSEndpoint wse, Object serverContext) {
/* 156 */     this(wse, serverContext, (EndpointContext)null);
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
/*     */   public EndpointImpl(WSEndpoint wse, Object serverContext, EndpointContext ctxt) {
/*     */     this.properties = Collections.emptyMap();
/* 169 */     this.endpointContext = ctxt;
/* 170 */     this.actualEndpoint = new HttpEndpoint(null, getAdapter(wse, ""));
/* 171 */     ((HttpEndpoint)this.actualEndpoint).publish(serverContext);
/* 172 */     this.binding = wse.getBinding();
/* 173 */     this.implementor = null;
/* 174 */     this.implClass = null;
/* 175 */     this.invoker = null;
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
/*     */   public EndpointImpl(WSEndpoint wse, String address) {
/* 187 */     this(wse, address, (EndpointContext)null);
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
/*     */   public EndpointImpl(WSEndpoint wse, String address, EndpointContext ctxt) {
/*     */     URL url;
/*     */     this.properties = Collections.emptyMap();
/*     */     try {
/* 203 */       url = new URL(address);
/* 204 */     } catch (MalformedURLException ex) {
/* 205 */       throw new IllegalArgumentException("Cannot create URL for this address " + address);
/*     */     } 
/* 207 */     if (!url.getProtocol().equals("http")) {
/* 208 */       throw new IllegalArgumentException(url.getProtocol() + " protocol based address is not supported");
/*     */     }
/* 210 */     if (!url.getPath().startsWith("/")) {
/* 211 */       throw new IllegalArgumentException("Incorrect WebService address=" + address + ". The address's path should start with /");
/*     */     }
/*     */     
/* 214 */     this.endpointContext = ctxt;
/* 215 */     this.actualEndpoint = new HttpEndpoint(null, getAdapter(wse, url.getPath()));
/* 216 */     ((HttpEndpoint)this.actualEndpoint).publish(address);
/* 217 */     this.binding = wse.getBinding();
/* 218 */     this.implementor = null;
/* 219 */     this.implClass = null;
/* 220 */     this.invoker = null;
/*     */   }
/*     */   
/*     */   public Binding getBinding() {
/* 224 */     return (Binding)this.binding;
/*     */   }
/*     */   
/*     */   public Object getImplementor() {
/* 228 */     return this.implementor;
/*     */   }
/*     */   public void publish(String address) {
/*     */     URL url;
/* 232 */     canPublish();
/*     */     
/*     */     try {
/* 235 */       url = new URL(address);
/* 236 */     } catch (MalformedURLException ex) {
/* 237 */       throw new IllegalArgumentException("Cannot create URL for this address " + address);
/*     */     } 
/* 239 */     if (!url.getProtocol().equals("http")) {
/* 240 */       throw new IllegalArgumentException(url.getProtocol() + " protocol based address is not supported");
/*     */     }
/* 242 */     if (!url.getPath().startsWith("/")) {
/* 243 */       throw new IllegalArgumentException("Incorrect WebService address=" + address + ". The address's path should start with /");
/*     */     }
/*     */     
/* 246 */     createEndpoint(url.getPath());
/* 247 */     ((HttpEndpoint)this.actualEndpoint).publish(address);
/*     */   }
/*     */   
/*     */   public void publish(Object serverContext) {
/* 251 */     canPublish();
/* 252 */     if (!HttpContext.class.isAssignableFrom(serverContext.getClass())) {
/* 253 */       throw new IllegalArgumentException(serverContext.getClass() + " is not a supported context.");
/*     */     }
/* 255 */     createEndpoint(((HttpContext)serverContext).getPath());
/* 256 */     ((HttpEndpoint)this.actualEndpoint).publish(serverContext);
/*     */   }
/*     */   
/*     */   public void publish(HttpContext serverContext) {
/* 260 */     canPublish();
/* 261 */     createEndpoint(serverContext.getPath());
/* 262 */     ((HttpEndpoint)this.actualEndpoint).publish(serverContext);
/*     */   }
/*     */   
/*     */   public void stop() {
/* 266 */     if (isPublished()) {
/* 267 */       ((HttpEndpoint)this.actualEndpoint).stop();
/* 268 */       this.actualEndpoint = null;
/* 269 */       this.stopped = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPublished() {
/* 274 */     return (this.actualEndpoint != null);
/*     */   }
/*     */   
/*     */   public List<Source> getMetadata() {
/* 278 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public void setMetadata(List<Source> metadata) {
/* 282 */     if (isPublished()) {
/* 283 */       throw new IllegalStateException("Cannot set Metadata. Endpoint is already published");
/*     */     }
/* 285 */     this.metadata = metadata;
/*     */   }
/*     */   
/*     */   public Executor getExecutor() {
/* 289 */     return this.executor;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor executor) {
/* 293 */     this.executor = executor;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getProperties() {
/* 297 */     return new HashMap<String, Object>(this.properties);
/*     */   }
/*     */   
/*     */   public void setProperties(Map<String, Object> map) {
/* 301 */     this.properties = new HashMap<String, Object>(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createEndpoint(String urlPattern) {
/* 310 */     SecurityManager sm = System.getSecurityManager();
/* 311 */     if (sm != null) {
/* 312 */       sm.checkPermission(ENDPOINT_PUBLISH_PERMISSION);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 317 */       Class.forName("com.sun.net.httpserver.HttpServer");
/* 318 */     } catch (Exception e) {
/* 319 */       throw new UnsupportedOperationException("Couldn't load light weight http server", e);
/*     */     } 
/* 321 */     this.container = getContainer();
/* 322 */     MetadataReader metadataReader = EndpointFactory.getExternalMetadatReader(this.implClass, this.binding);
/* 323 */     WSEndpoint wse = WSEndpoint.create(this.implClass, true, this.invoker, getProperty(QName.class, "javax.xml.ws.wsdl.service"), getProperty(QName.class, "javax.xml.ws.wsdl.port"), this.container, this.binding, getPrimaryWsdl(metadataReader), buildDocList(), (EntityResolver)null, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     this.actualEndpoint = new HttpEndpoint(this.executor, getAdapter(wse, urlPattern));
/*     */   }
/*     */   
/*     */   private <T> T getProperty(Class<T> type, String key) {
/* 340 */     Object o = this.properties.get(key);
/* 341 */     if (o == null) return null; 
/* 342 */     if (type.isInstance(o)) {
/* 343 */       return type.cast(o);
/*     */     }
/* 345 */     throw new IllegalArgumentException("Property " + key + " has to be of type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<SDDocumentSource> buildDocList() {
/* 353 */     List<SDDocumentSource> r = new ArrayList<SDDocumentSource>();
/*     */     
/* 355 */     if (this.metadata != null) {
/* 356 */       for (Source source : this.metadata) {
/*     */         try {
/* 358 */           XMLStreamBufferResult xsbr = (XMLStreamBufferResult)XmlUtil.identityTransform(source, (Result)new XMLStreamBufferResult());
/* 359 */           String systemId = source.getSystemId();
/*     */           
/* 361 */           r.add(SDDocumentSource.create(new URL(systemId), (XMLStreamBuffer)xsbr.getXMLStreamBuffer()));
/* 362 */         } catch (TransformerException te) {
/* 363 */           throw new ServerRtException("server.rt.err", new Object[] { te });
/* 364 */         } catch (IOException te) {
/* 365 */           throw new ServerRtException("server.rt.err", new Object[] { te });
/* 366 */         } catch (SAXException e) {
/* 367 */           throw new ServerRtException("server.rt.err", new Object[] { e });
/* 368 */         } catch (ParserConfigurationException e) {
/* 369 */           throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 374 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SDDocumentSource getPrimaryWsdl(MetadataReader metadataReader) {
/* 382 */     EndpointFactory.verifyImplementorClass(this.implClass, metadataReader);
/* 383 */     String wsdlLocation = EndpointFactory.getWsdlLocation(this.implClass, metadataReader);
/* 384 */     if (wsdlLocation != null) {
/* 385 */       ClassLoader cl = this.implClass.getClassLoader();
/* 386 */       URL url = cl.getResource(wsdlLocation);
/* 387 */       if (url != null) {
/* 388 */         return SDDocumentSource.create(url);
/*     */       }
/* 390 */       throw new ServerRtException("cannot.load.wsdl", new Object[] { wsdlLocation });
/*     */     } 
/* 392 */     return null;
/*     */   }
/*     */   
/*     */   private void canPublish() {
/* 396 */     if (isPublished()) {
/* 397 */       throw new IllegalStateException("Cannot publish this endpoint. Endpoint has been already published.");
/*     */     }
/*     */     
/* 400 */     if (this.stopped) {
/* 401 */       throw new IllegalStateException("Cannot publish this endpoint. Endpoint has been already stopped.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference getEndpointReference(Element... referenceParameters) {
/* 407 */     return getEndpointReference((Class)W3CEndpointReference.class, referenceParameters);
/*     */   }
/*     */   
/*     */   public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
/* 411 */     if (!isPublished()) {
/* 412 */       throw new WebServiceException("Endpoint is not published yet");
/*     */     }
/* 414 */     return ((HttpEndpoint)this.actualEndpoint).getEndpointReference(clazz, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndpointContext(EndpointContext ctxt) {
/* 419 */     this.endpointContext = ctxt;
/*     */   }
/*     */   
/*     */   private HttpAdapter getAdapter(WSEndpoint endpoint, String urlPattern) {
/* 423 */     HttpAdapterList adapterList = null;
/* 424 */     if (this.endpointContext != null) {
/* 425 */       if (this.endpointContext instanceof Component) {
/* 426 */         adapterList = (HttpAdapterList)((Component)this.endpointContext).getSPI(HttpAdapterList.class);
/*     */       }
/*     */       
/* 429 */       if (adapterList == null) {
/* 430 */         for (Endpoint e : this.endpointContext.getEndpoints()) {
/* 431 */           if (e.isPublished() && e != this) {
/* 432 */             adapterList = ((HttpEndpoint)((EndpointImpl)e).actualEndpoint).getAdapterOwner();
/* 433 */             assert adapterList != null;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 439 */     if (adapterList == null) {
/* 440 */       adapterList = new ServerAdapterList();
/*     */     }
/* 442 */     return adapterList.createAdapter("", urlPattern, endpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Container getContainer() {
/* 449 */     if (this.endpointContext != null) {
/* 450 */       if (this.endpointContext instanceof Component) {
/* 451 */         Container c = (Container)((Component)this.endpointContext).getSPI(Container.class);
/* 452 */         if (c != null) {
/* 453 */           return c;
/*     */         }
/*     */       } 
/* 456 */       for (Endpoint e : this.endpointContext.getEndpoints()) {
/* 457 */         if (e.isPublished() && e != this) {
/* 458 */           return ((EndpointImpl)e).container;
/*     */         }
/*     */       } 
/*     */     } 
/* 462 */     return new ServerContainer();
/*     */   }
/*     */   
/*     */   private static class InvokerImpl extends Invoker {
/*     */     private Invoker spiInvoker;
/*     */     
/*     */     InvokerImpl(Invoker spiInvoker) {
/* 469 */       this.spiInvoker = spiInvoker;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/*     */       try {
/* 475 */         this.spiInvoker.inject((WebServiceContext)wsc);
/* 476 */       } catch (IllegalAccessException e) {
/* 477 */         throw new WebServiceException(e);
/* 478 */       } catch (InvocationTargetException e) {
/* 479 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object invoke(@NotNull Packet p, @NotNull Method m, @NotNull Object... args) throws InvocationTargetException, IllegalAccessException {
/* 484 */       return this.spiInvoker.invoke(m, args);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\EndpointImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */