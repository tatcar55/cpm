/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.marshaller.SAX2DOMEx;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*     */ import com.sun.xml.ws.developer.EPRRecipe;
/*     */ import com.sun.xml.ws.developer.StatefulWebServiceManager;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import com.sun.xml.ws.util.InjectionPlan;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ import org.glassfish.ha.store.api.Storeable;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StatefulInstanceResolver<T>
/*     */   extends AbstractMultiInstanceResolver<T>
/*     */   implements StatefulWebServiceManager<T>
/*     */ {
/*     */   @Nullable
/*     */   private volatile T fallback;
/*     */   private HAMap haMap;
/* 106 */   private volatile long timeoutMilliseconds = 0L;
/*     */ 
/*     */   
/*     */   private volatile StatefulWebServiceManager.Callback<T> timeoutCallback;
/*     */   
/*     */   private volatile Timer timer;
/*     */   
/*     */   private final ClassLoader appCL;
/*     */   
/*     */   private final boolean haEnabled;
/*     */ 
/*     */   
/*     */   private static final class HAInstance<T>
/*     */     implements Storeable
/*     */   {
/*     */     @NotNull
/*     */     transient T instance;
/*     */     
/*     */     private byte[] buf;
/*     */     
/* 126 */     private long lastAccess = 0L;
/*     */ 
/*     */     
/*     */     private boolean isNew = false;
/*     */ 
/*     */     
/* 132 */     private long version = -1L;
/*     */     
/*     */     private long maxIdleTime;
/*     */ 
/*     */     
/*     */     public HAInstance() {}
/*     */ 
/*     */     
/*     */     public HAInstance(T instance, long timeout) {
/* 141 */       this.instance = instance;
/* 142 */       this.lastAccess = System.currentTimeMillis();
/* 143 */       this.maxIdleTime = timeout;
/*     */     }
/*     */     
/*     */     public T getInstance(final ClassLoader cl) {
/* 147 */       if (this.instance == null) {
/*     */         try {
/* 149 */           ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(this.buf))
/*     */             {
/*     */               protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/* 152 */                 Class<?> clazz = cl.loadClass(desc.getName());
/* 153 */                 if (clazz == null) {
/* 154 */                   clazz = super.resolveClass(desc);
/*     */                 }
/* 156 */                 return clazz;
/*     */               }
/*     */             };
/* 159 */           this.instance = (T)in.readObject();
/* 160 */           in.close();
/* 161 */         } catch (Exception ioe) {
/* 162 */           throw new WebServiceException(ioe);
/*     */         } 
/*     */       }
/* 165 */       return this.instance;
/*     */     }
/*     */ 
/*     */     
/*     */     public long _storeable_getVersion() {
/* 170 */       return this.version;
/*     */     }
/*     */ 
/*     */     
/*     */     public void _storeable_setVersion(long version) {
/* 175 */       this.version = version;
/*     */     }
/*     */ 
/*     */     
/*     */     public long _storeable_getLastAccessTime() {
/* 180 */       return this.lastAccess;
/*     */     }
/*     */ 
/*     */     
/*     */     public void _storeable_setLastAccessTime(long time) {
/* 185 */       this.lastAccess = time;
/*     */     }
/*     */ 
/*     */     
/*     */     public long _storeable_getMaxIdleTime() {
/* 190 */       return this.maxIdleTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public void _storeable_setMaxIdleTime(long time) {
/* 195 */       this.maxIdleTime = time;
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] _storeable_getAttributeNames() {
/* 200 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] _storeable_getDirtyStatus() {
/* 205 */       return new boolean[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public void _storeable_writeState(OutputStream os) throws IOException {
/* 210 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 211 */       ObjectOutputStream boos = new ObjectOutputStream(bos);
/* 212 */       boos.writeObject(this.instance);
/* 213 */       boos.close();
/* 214 */       this.buf = bos.toByteArray();
/*     */       
/* 216 */       ObjectOutputStream oos = new ObjectOutputStream(os);
/* 217 */       oos.writeLong(this.version);
/* 218 */       oos.writeLong(this.lastAccess);
/* 219 */       oos.writeLong(this.maxIdleTime);
/* 220 */       oos.writeBoolean(this.isNew);
/* 221 */       oos.writeInt(this.buf.length);
/* 222 */       oos.write(this.buf);
/* 223 */       oos.close();
/*     */     }
/*     */ 
/*     */     
/*     */     public void _storeable_readState(InputStream is) throws IOException {
/* 228 */       ObjectInputStream ois = new ObjectInputStream(is);
/* 229 */       this.version = ois.readLong();
/* 230 */       this.lastAccess = ois.readLong();
/* 231 */       this.maxIdleTime = ois.readLong();
/* 232 */       this.isNew = ois.readBoolean();
/* 233 */       int len = ois.readInt();
/* 234 */       this.buf = new byte[len];
/* 235 */       ois.readFully(this.buf);
/* 236 */       ois.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Instance
/*     */   {
/*     */     @NotNull
/*     */     final T instance;
/*     */     volatile TimerTask task;
/*     */     
/*     */     public Instance(T instance) {
/* 248 */       this.instance = instance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void restartTimer() {
/* 255 */       cancel();
/* 256 */       if (StatefulInstanceResolver.this.timeoutMilliseconds == 0L) {
/*     */         return;
/*     */       }
/*     */       
/* 260 */       this.task = new TimerTask()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 264 */               StatefulWebServiceManager.Callback<T> cb = StatefulInstanceResolver.this.timeoutCallback;
/* 265 */               if (cb != null) {
/* 266 */                 if (StatefulInstanceResolver.logger.isLoggable(Level.FINEST)) {
/* 267 */                   StatefulInstanceResolver.logger.log(Level.FINEST, "Invoking timeout callback for instance/timeouttask = [ {0} / {1} ]", new Object[] { this.this$1.instance, this });
/*     */                 }
/* 269 */                 cb.onTimeout(StatefulInstanceResolver.Instance.this.instance, StatefulInstanceResolver.this);
/*     */                 
/*     */                 return;
/*     */               } 
/* 273 */               StatefulInstanceResolver.this.unexport(StatefulInstanceResolver.Instance.this.instance);
/* 274 */             } catch (Throwable e) {
/*     */               
/* 276 */               StatefulInstanceResolver.logger.log(Level.SEVERE, "time out handler failed", e);
/*     */             } 
/*     */           }
/*     */         };
/* 280 */       StatefulInstanceResolver.this.timer.schedule(this.task, StatefulInstanceResolver.this.timeoutMilliseconds);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void cancel() {
/* 287 */       if (this.task != null) {
/* 288 */         boolean result = this.task.cancel();
/* 289 */         if (StatefulInstanceResolver.logger.isLoggable(Level.FINEST)) {
/* 290 */           StatefulInstanceResolver.logger.log(Level.FINEST, "Timeout callback CANCELED for instance/timeouttask/cancel result = [ {0} / {1} / {2} ]", new Object[] { this.instance, this, Boolean.valueOf(result) });
/*     */         }
/*     */       }
/* 293 */       else if (StatefulInstanceResolver.logger.isLoggable(Level.FINEST)) {
/* 294 */         StatefulInstanceResolver.logger.log(Level.FINEST, "Timeout callback NOT CANCELED for instance = [ {0} ]; task is null ...", this.instance);
/*     */       } 
/*     */       
/* 297 */       this.task = null;
/*     */     }
/*     */     
/*     */     public synchronized void setTask(TimerTask t) {
/* 301 */       this.task = t;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatefulInstanceResolver(Class<T> clazz) {
/* 309 */     super(clazz);
/* 310 */     this.appCL = clazz.getClassLoader();
/*     */     
/* 312 */     boolean ha = false;
/* 313 */     if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured() && 
/* 314 */       Serializable.class.isAssignableFrom(clazz)) {
/* 315 */       logger.log(Level.WARNING, "{0} doesn''t implement Serializable. High availibility is disabled i.e.if a failover happens, stateful instance state is not failed over.", clazz);
/*     */       
/* 317 */       ha = true;
/*     */     } 
/*     */     
/* 320 */     this.haEnabled = ha;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public T resolve(Packet request) {
/* 327 */     HeaderList headers = request.getMessage().getHeaders();
/* 328 */     Header header = headers.get(COOKIE_TAG, true);
/* 329 */     String id = null;
/* 330 */     if (header != null) {
/*     */       
/* 332 */       id = header.getStringContent();
/* 333 */       Instance o = this.haMap.get(id);
/* 334 */       if (o != null) {
/* 335 */         if (logger.isLoggable(Level.FINEST)) {
/* 336 */           logger.log(Level.FINEST, "Restarting timer for objectId/Instance = [ {0} / {1} ]", new Object[] { id, o });
/*     */         }
/* 338 */         o.restartTimer();
/* 339 */         return o.instance;
/*     */       } 
/*     */ 
/*     */       
/* 343 */       logger.log(Level.INFO, "Request had an unrecognized object ID {0}", id);
/*     */     } else {
/* 345 */       logger.fine("No objectId header received");
/*     */     } 
/*     */ 
/*     */     
/* 349 */     T flbk = this.fallback;
/* 350 */     if (flbk != null) {
/* 351 */       return flbk;
/*     */     }
/*     */     
/* 354 */     if (id == null) {
/* 355 */       throw new WebServiceException(ServerMessages.STATEFUL_COOKIE_HEADER_REQUIRED(COOKIE_TAG));
/*     */     }
/* 357 */     throw new WebServiceException(ServerMessages.STATEFUL_COOKIE_HEADER_INCORRECT(COOKIE_TAG, id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postInvoke(@NotNull Packet request, @NotNull T servant) {
/* 367 */     this.haMap.put(servant);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
/* 372 */     super.start(wsc, endpoint);
/*     */     
/* 374 */     this.haMap = new HAMap();
/*     */     
/* 376 */     if (endpoint.getBinding().getAddressingVersion() == null) {
/* 377 */       throw new WebServiceException(ServerMessages.STATEFUL_REQURES_ADDRESSING(this.clazz));
/*     */     }
/*     */ 
/*     */     
/* 381 */     for (Field field : this.clazz.getDeclaredFields()) {
/* 382 */       if (field.getType() == StatefulWebServiceManager.class) {
/* 383 */         if (!Modifier.isStatic(field.getModifiers())) {
/* 384 */           throw new WebServiceException(ServerMessages.STATIC_RESOURCE_INJECTION_ONLY(StatefulWebServiceManager.class, field));
/*     */         }
/* 386 */         (new InjectionPlan.FieldInjectionPlan(field)).inject(null, this);
/*     */       } 
/*     */     } 
/*     */     
/* 390 */     for (Method method : this.clazz.getDeclaredMethods()) {
/* 391 */       Class[] paramTypes = method.getParameterTypes();
/* 392 */       if (paramTypes.length == 1)
/*     */       {
/*     */ 
/*     */         
/* 396 */         if (paramTypes[0] == StatefulWebServiceManager.class) {
/* 397 */           if (!Modifier.isStatic(method.getModifiers())) {
/* 398 */             throw new WebServiceException(ServerMessages.STATIC_RESOURCE_INJECTION_ONLY(StatefulWebServiceManager.class, method));
/*     */           }
/*     */           
/* 401 */           (new InjectionPlan.MethodInjectionPlan(method)).inject(null, this);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 408 */     synchronized (this.haMap) {
/* 409 */       for (Instance t : this.haMap.values()) {
/* 410 */         t.cancel();
/* 411 */         dispose(t.instance);
/*     */       } 
/* 413 */       this.haMap.destroy();
/*     */     } 
/* 415 */     if (this.fallback != null) {
/* 416 */       dispose(this.fallback);
/* 417 */       this.fallback = null;
/*     */     } 
/* 419 */     stopTimer();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public W3CEndpointReference export(T o) {
/* 425 */     return export(W3CEndpointReference.class, o);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> epr, T o) {
/* 431 */     return export(epr, o, (EPRRecipe)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> epr, T o, EPRRecipe recipe) {
/* 436 */     return export(epr, InvokerTube.getCurrentPacket(), o, recipe);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> epr, WebServiceContext context, T o) {
/* 442 */     if (context instanceof WSWebServiceContext) {
/* 443 */       WSWebServiceContext wswsc = (WSWebServiceContext)context;
/* 444 */       return export(epr, wswsc.getRequestPacket(), o);
/*     */     } 
/*     */     
/* 447 */     throw new WebServiceException(ServerMessages.STATEFUL_INVALID_WEBSERVICE_CONTEXT(context));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> adrsVer, @NotNull Packet currentRequest, T o) {
/* 453 */     return export(adrsVer, currentRequest, o, (EPRRecipe)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> adrsVer, @NotNull Packet currentRequest, T o, EPRRecipe recipe) {
/* 458 */     return export(adrsVer, currentRequest.webServiceContextDelegate.getEPRAddress(currentRequest, this.owner), currentRequest.webServiceContextDelegate.getWSDLAddress(currentRequest, this.owner), o, recipe);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> adrsVer, String endpointAddress, T o) {
/* 465 */     return export(adrsVer, endpointAddress, (String)null, o, (EPRRecipe)null);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public <EPR extends EndpointReference> EPR export(Class<EPR> adrsVer, String endpointAddress, String wsdlAddress, T o, EPRRecipe recipe) {
/* 470 */     if (endpointAddress == null) {
/* 471 */       throw new IllegalArgumentException("No address available");
/*     */     }
/*     */     
/* 474 */     String key = this.haMap.get(o);
/*     */     
/* 476 */     if (key != null) {
/* 477 */       return createEPR(key, adrsVer, endpointAddress, wsdlAddress, recipe);
/*     */     }
/*     */ 
/*     */     
/* 481 */     synchronized (this) {
/*     */ 
/*     */       
/* 484 */       key = this.haMap.get(o);
/* 485 */       if (key != null) {
/* 486 */         return createEPR(key, adrsVer, endpointAddress, wsdlAddress, recipe);
/*     */       }
/*     */       
/* 489 */       if (o != null) {
/* 490 */         prepare(o);
/*     */       }
/* 492 */       key = UUID.randomUUID().toString();
/* 493 */       Instance instance = new Instance(o);
/* 494 */       if (logger.isLoggable(Level.FINEST)) {
/* 495 */         logger.log(Level.FINEST, "Storing instance ID/Instance/Object/TimerTask = [ {0} / {1} / {2} / {3} ]", new Object[] { key, instance, instance.instance, instance.task });
/*     */       }
/* 497 */       this.haMap.put(key, instance);
/* 498 */       if (this.timeoutMilliseconds != 0L) {
/* 499 */         instance.restartTimer();
/*     */       }
/*     */     } 
/*     */     
/* 503 */     return createEPR(key, adrsVer, endpointAddress, wsdlAddress, recipe);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <EPR extends EndpointReference> EPR createEPR(String key, Class<EPR> eprClass, String address, String wsdlAddress, EPRRecipe recipe) {
/* 513 */     List<Element> referenceParameters = new ArrayList<Element>();
/* 514 */     List<Element> metadata = new ArrayList<Element>();
/*     */     
/* 516 */     Document doc = DOMUtil.createDom();
/* 517 */     Element cookie = doc.createElementNS(COOKIE_TAG.getNamespaceURI(), COOKIE_TAG.getPrefix() + ":" + COOKIE_TAG.getLocalPart());
/*     */ 
/*     */     
/* 520 */     cookie.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + COOKIE_TAG.getPrefix(), COOKIE_TAG.getNamespaceURI());
/*     */     
/* 522 */     cookie.setTextContent(key);
/* 523 */     referenceParameters.add(cookie);
/*     */     
/* 525 */     if (recipe != null) {
/* 526 */       for (Header h : recipe.getReferenceParameters()) {
/* 527 */         doc = DOMUtil.createDom();
/* 528 */         SAX2DOMEx s2d = new SAX2DOMEx(doc);
/*     */         try {
/* 530 */           h.writeTo((ContentHandler)s2d, XmlUtil.DRACONIAN_ERROR_HANDLER);
/* 531 */           referenceParameters.add((Element)doc.getLastChild());
/* 532 */         } catch (SAXException e) {
/* 533 */           throw new WebServiceException("Unable to write EPR Reference parameters " + h, e);
/*     */         } 
/*     */       } 
/* 536 */       Transformer t = XmlUtil.newTransformer();
/* 537 */       for (Source s : recipe.getMetadata()) {
/*     */         try {
/* 539 */           DOMResult r = new DOMResult();
/* 540 */           t.transform(s, r);
/* 541 */           Document d = (Document)r.getNode();
/* 542 */           metadata.add(d.getDocumentElement());
/* 543 */         } catch (TransformerException e) {
/* 544 */           throw new IllegalArgumentException("Unable to write EPR metadata " + s, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 550 */     return eprClass.cast(this.owner.getEndpointReference(eprClass, address, wsdlAddress, metadata, referenceParameters));
/*     */   }
/*     */ 
/*     */   
/*     */   public void unexport(@Nullable T o) {
/* 555 */     if (o == null) {
/*     */       return;
/*     */     }
/* 558 */     Instance i = this.haMap.remove(o);
/* 559 */     if (logger.isLoggable(Level.FINEST)) {
/* 560 */       logger.log(Level.FINEST, "Removed Instance = [ {0} ], remaining instance keys = [ {1} ]", new Object[] { o, this.haMap.instances.keySet() });
/*     */     }
/* 562 */     if (i != null)
/* 563 */       i.cancel(); 
/*     */   }
/*     */   
/*     */   public T resolve(EndpointReference epr) {
/*     */     class CookieSniffer extends DefaultHandler { StringBuilder buf;
/*     */       
/*     */       CookieSniffer() {
/* 570 */         this.buf = new StringBuilder();
/* 571 */         this.inCookie = false;
/*     */       }
/*     */       boolean inCookie;
/*     */       public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 575 */         if (localName.equals(StatefulInstanceResolver.COOKIE_TAG.getLocalPart()) && uri.equals(StatefulInstanceResolver.COOKIE_TAG.getNamespaceURI())) {
/* 576 */           this.inCookie = true;
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void characters(char[] ch, int start, int length) throws SAXException {
/* 582 */         if (this.inCookie) {
/* 583 */           this.buf.append(ch, start, length);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void endElement(String uri, String localName, String qName) throws SAXException {
/* 589 */         this.inCookie = false;
/*     */       } }
/*     */     ;
/* 592 */     CookieSniffer sniffer = new CookieSniffer();
/* 593 */     epr.writeTo(new SAXResult(sniffer));
/*     */     
/* 595 */     Instance o = this.haMap.get(sniffer.buf.toString());
/* 596 */     if (o != null) {
/* 597 */       return o.instance;
/*     */     }
/* 599 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFallbackInstance(T o) {
/* 604 */     if (o != null) {
/* 605 */       prepare(o);
/*     */     }
/* 607 */     this.fallback = o;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTimeout(long milliseconds, StatefulWebServiceManager.Callback<T> callback) {
/* 612 */     if (milliseconds < 0L) {
/* 613 */       throw new IllegalArgumentException();
/*     */     }
/* 615 */     this.timeoutMilliseconds = milliseconds;
/* 616 */     this.timeoutCallback = callback;
/* 617 */     this.haMap.getExpiredTask().cancel();
/* 618 */     if (this.timeoutMilliseconds > 0L) {
/* 619 */       startTimer();
/* 620 */       this.timer.schedule(this.haMap.newExpiredTask(), this.timeoutMilliseconds, this.timeoutMilliseconds);
/*     */     } else {
/* 622 */       stopTimer();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void touch(T o) {
/* 628 */     Instance i = this.haMap.touch(o);
/* 629 */     if (i != null) {
/* 630 */       i.restartTimer();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void startTimer() {
/* 636 */     if (this.timer == null) {
/* 637 */       this.timer = new Timer("JAX-WS stateful web service timeout timer");
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void stopTimer() {
/* 642 */     if (this.timer != null) {
/* 643 */       this.timer.cancel();
/* 644 */       this.timer = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class HAMap
/*     */   {
/* 650 */     final Map<String, StatefulInstanceResolver<T>.Instance> instances = new HashMap<String, StatefulInstanceResolver<T>.Instance>();
/*     */     
/* 652 */     final Map<T, String> reverseInstances = new HashMap<T, String>();
/*     */     
/*     */     final BackingStore<String, StatefulInstanceResolver.HAInstance> bs;
/*     */     TimerTask expiredTask;
/*     */     
/*     */     HAMap() {
/* 658 */       HighAvailabilityProvider.StoreType type = StatefulInstanceResolver.this.haEnabled ? HighAvailabilityProvider.StoreType.IN_MEMORY : HighAvailabilityProvider.StoreType.NOOP;
/* 659 */       this.bs = HighAvailabilityProvider.INSTANCE.createBackingStore(HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(type), StatefulInstanceResolver.this.owner.getServiceName() + ":" + StatefulInstanceResolver.this.owner.getPortName() + ":STATEFUL_WEB_SERVICE", String.class, StatefulInstanceResolver.HAInstance.class);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 664 */       this.expiredTask = newExpiredTask();
/*     */     }
/*     */     
/*     */     TimerTask getExpiredTask() {
/* 668 */       return this.expiredTask;
/*     */     }
/*     */     
/*     */     private TimerTask newExpiredTask() {
/* 672 */       this.expiredTask = new TimerTask()
/*     */         {
/*     */           public void run() {
/* 675 */             HighAvailabilityProvider.removeExpired(StatefulInstanceResolver.HAMap.this.bs);
/*     */           }
/*     */         };
/* 678 */       return this.expiredTask;
/*     */     }
/*     */     
/*     */     synchronized String get(T t) {
/* 682 */       return this.reverseInstances.get(t);
/*     */     }
/*     */     
/*     */     synchronized StatefulInstanceResolver<T>.Instance touch(T t) {
/* 686 */       String id = get(t);
/* 687 */       if (id != null) {
/* 688 */         StatefulInstanceResolver<T>.Instance i = get(id);
/* 689 */         if (i != null) {
/* 690 */           put(id, i);
/* 691 */           return i;
/*     */         } 
/*     */       } 
/* 694 */       return null;
/*     */     }
/*     */     
/*     */     synchronized StatefulInstanceResolver<T>.Instance get(String id) {
/* 698 */       StatefulInstanceResolver<T>.Instance i = this.instances.get(id);
/* 699 */       if (i == null) {
/* 700 */         StatefulInstanceResolver.HAInstance<T> hai = (StatefulInstanceResolver.HAInstance<T>)HighAvailabilityProvider.loadFrom(this.bs, id, null);
/* 701 */         if (hai != null) {
/* 702 */           T t = hai.getInstance(StatefulInstanceResolver.this.appCL);
/* 703 */           i = new StatefulInstanceResolver.Instance(t);
/* 704 */           this.instances.put(id, i);
/* 705 */           this.reverseInstances.put(t, id);
/*     */         } 
/*     */       } 
/* 708 */       return i;
/*     */     }
/*     */     
/*     */     synchronized void put(String id, StatefulInstanceResolver<T>.Instance newi) {
/* 712 */       StatefulInstanceResolver<T>.Instance oldi = this.instances.get(id);
/* 713 */       boolean isNew = (oldi == null);
/* 714 */       if (!isNew) {
/* 715 */         this.reverseInstances.remove(oldi.instance);
/*     */         
/* 717 */         newi.setTask(oldi.task);
/*     */       } 
/*     */       
/* 720 */       this.instances.put(id, newi);
/* 721 */       this.reverseInstances.put(newi.instance, id);
/* 722 */       StatefulInstanceResolver.HAInstance<T> hai = new StatefulInstanceResolver.HAInstance<T>(newi.instance, StatefulInstanceResolver.this.timeoutMilliseconds);
/* 723 */       HighAvailabilityProvider.saveTo(this.bs, id, (Serializable)hai, isNew);
/*     */     }
/*     */     
/*     */     synchronized void put(T t) {
/* 727 */       String id = this.reverseInstances.get(t);
/* 728 */       if (id != null) {
/* 729 */         put(id, new StatefulInstanceResolver.Instance(t));
/*     */       }
/*     */     }
/*     */     
/*     */     synchronized void remove(String id) {
/* 734 */       StatefulInstanceResolver<T>.Instance i = this.instances.get(id);
/* 735 */       if (i != null) {
/* 736 */         this.instances.remove(id);
/* 737 */         this.reverseInstances.remove(i.instance);
/* 738 */         HighAvailabilityProvider.removeFrom(this.bs, id);
/*     */       } 
/*     */     }
/*     */     
/*     */     synchronized StatefulInstanceResolver<T>.Instance remove(T t) {
/* 743 */       String id = this.reverseInstances.get(t);
/* 744 */       if (id != null) {
/* 745 */         this.reverseInstances.remove(t);
/* 746 */         StatefulInstanceResolver<T>.Instance i = this.instances.remove(id);
/* 747 */         HighAvailabilityProvider.removeFrom(this.bs, id);
/* 748 */         return i;
/*     */       } 
/* 750 */       return null;
/*     */     }
/*     */     
/*     */     synchronized void destroy() {
/* 754 */       this.instances.clear();
/* 755 */       this.reverseInstances.clear();
/* 756 */       HighAvailabilityProvider.destroy(this.bs);
/*     */     }
/*     */     
/*     */     Collection<StatefulInstanceResolver<T>.Instance> values() {
/* 760 */       return this.instances.values();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 766 */   private static final QName COOKIE_TAG = new QName("http://jax-ws.dev.java.net/xml/ns/", "objectId", "jaxws");
/*     */   
/* 768 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\StatefulInstanceResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */