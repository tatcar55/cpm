/*     */ package com.sun.xml.ws.api;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.spi.ServiceDelegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSService
/*     */   extends ServiceDelegate
/*     */   implements ComponentRegistry
/*     */ {
/*  88 */   private final Set<Component> components = new CopyOnWriteArraySet<Component>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T getPort(WSEndpointReference paramWSEndpointReference, Class<T> paramClass, WebServiceFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> Dispatch<T> createDispatch(QName paramQName, WSEndpointReference paramWSEndpointReference, Class<T> paramClass, Service.Mode paramMode, WebServiceFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Dispatch<Object> createDispatch(QName paramQName, WSEndpointReference paramWSEndpointReference, JAXBContext paramJAXBContext, Service.Mode paramMode, WebServiceFeature... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract Container getContainer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <S> S getSPI(@NotNull Class<S> spiType) {
/* 125 */     for (Component c : this.components) {
/* 126 */       S s = c.getSPI(spiType);
/* 127 */       if (s != null) {
/* 128 */         return s;
/*     */       }
/*     */     } 
/* 131 */     return (S)getContainer().getSPI(spiType);
/*     */   }
/*     */   @NotNull
/*     */   public Set<Component> getComponents() {
/* 135 */     return this.components;
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
/*     */   public static WSService create(URL wsdlDocumentLocation, QName serviceName) {
/* 151 */     return (WSService)new WSServiceDelegate(wsdlDocumentLocation, serviceName, Service.class, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSService create(QName serviceName) {
/* 162 */     return create(null, serviceName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSService create() {
/* 169 */     return create(null, new QName(WSService.class.getName(), "dummy"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class InitParams
/*     */   {
/*     */     private Container container;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setContainer(Container c) {
/* 185 */       this.container = c;
/*     */     }
/*     */     public Container getContainer() {
/* 188 */       return this.container;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   protected static final ThreadLocal<InitParams> INIT_PARAMS = new ThreadLocal<InitParams>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   protected static final InitParams EMPTY_PARAMS = new InitParams();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Service create(URL wsdlDocumentLocation, QName serviceName, InitParams properties) {
/* 221 */     if (INIT_PARAMS.get() != null)
/* 222 */       throw new IllegalStateException("someone left non-null InitParams"); 
/* 223 */     INIT_PARAMS.set(properties);
/*     */     try {
/* 225 */       Service svc = Service.create(wsdlDocumentLocation, serviceName);
/* 226 */       if (INIT_PARAMS.get() != null)
/* 227 */         throw new IllegalStateException("Service " + svc + " didn't recognize InitParams"); 
/* 228 */       return svc;
/*     */     } finally {
/*     */       
/* 231 */       INIT_PARAMS.set(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSService unwrap(final Service svc) {
/* 242 */     return AccessController.<WSService>doPrivileged(new PrivilegedAction<WSService>() {
/*     */           public WSService run() {
/*     */             try {
/* 245 */               Field f = svc.getClass().getField("delegate");
/* 246 */               f.setAccessible(true);
/* 247 */               Object delegate = f.get(svc);
/* 248 */               if (!(delegate instanceof WSService))
/* 249 */                 throw new IllegalArgumentException(); 
/* 250 */               return (WSService)delegate;
/* 251 */             } catch (NoSuchFieldException e) {
/* 252 */               AssertionError x = new AssertionError("Unexpected service API implementation");
/* 253 */               x.initCause(e);
/* 254 */               throw x;
/* 255 */             } catch (IllegalAccessException e) {
/* 256 */               IllegalAccessError x = new IllegalAccessError(e.getMessage());
/* 257 */               x.initCause(e);
/* 258 */               throw x;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\WSService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */