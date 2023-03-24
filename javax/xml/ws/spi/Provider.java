/*     */ package javax.xml.ws.spi;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Provider
/*     */ {
/*     */   public static final String JAXWSPROVIDER_PROPERTY = "javax.xml.ws.spi.Provider";
/*     */   static final String DEFAULT_JAXWSPROVIDER = "com.sun.xml.internal.ws.spi.ProviderImpl";
/*     */   private static final Method loadMethod;
/*     */   private static final Method iteratorMethod;
/*     */   
/*     */   static {
/*  86 */     Method tLoadMethod = null;
/*  87 */     Method tIteratorMethod = null;
/*     */     try {
/*  89 */       Class<?> clazz = Class.forName("java.util.ServiceLoader");
/*  90 */       tLoadMethod = clazz.getMethod("load", new Class[] { Class.class });
/*  91 */       tIteratorMethod = clazz.getMethod("iterator", new Class[0]);
/*  92 */     } catch (ClassNotFoundException ce) {
/*     */     
/*  94 */     } catch (NoSuchMethodException ne) {}
/*     */ 
/*     */     
/*  97 */     loadMethod = tLoadMethod;
/*  98 */     iteratorMethod = tIteratorMethod;
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
/*     */   
/*     */   public static Provider provider() {
/*     */     try {
/* 140 */       Object provider = getProviderUsingServiceLoader();
/* 141 */       if (provider == null) {
/* 142 */         provider = FactoryFinder.find("javax.xml.ws.spi.Provider", "com.sun.xml.internal.ws.spi.ProviderImpl");
/*     */       }
/* 144 */       if (!(provider instanceof Provider)) {
/* 145 */         Class<Provider> pClass = Provider.class;
/* 146 */         String classnameAsResource = pClass.getName().replace('.', '/') + ".class";
/* 147 */         ClassLoader loader = pClass.getClassLoader();
/* 148 */         if (loader == null) {
/* 149 */           loader = ClassLoader.getSystemClassLoader();
/*     */         }
/* 151 */         URL targetTypeURL = loader.getResource(classnameAsResource);
/* 152 */         throw new LinkageError("ClassCastException: attempting to cast" + provider.getClass().getClassLoader().getResource(classnameAsResource) + "to" + targetTypeURL.toString());
/*     */       } 
/*     */ 
/*     */       
/* 156 */       return (Provider)provider;
/* 157 */     } catch (WebServiceException ex) {
/* 158 */       throw ex;
/* 159 */     } catch (Exception ex) {
/* 160 */       throw new WebServiceException("Unable to createEndpointReference Provider", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Provider getProviderUsingServiceLoader() {
/* 166 */     if (loadMethod != null) {
/*     */       Object loader; Iterator<Provider> it;
/*     */       try {
/* 169 */         loader = loadMethod.invoke(null, new Object[] { Provider.class });
/* 170 */       } catch (Exception e) {
/* 171 */         throw new WebServiceException("Cannot invoke java.util.ServiceLoader#load()", e);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 176 */         it = (Iterator<Provider>)iteratorMethod.invoke(loader, new Object[0]);
/* 177 */       } catch (Exception e) {
/* 178 */         throw new WebServiceException("Cannot invoke java.util.ServiceLoader#iterator()", e);
/*     */       } 
/* 180 */       return it.hasNext() ? it.next() : null;
/*     */     } 
/* 182 */     return null;
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
/*     */   public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 217 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReference createW3CEndpointReference(String address, QName interfaceName, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters, List<Element> elements, Map<QName, String> attributes) {
/* 464 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */   public Endpoint createAndPublishEndpoint(String address, Object implementor, WebServiceFeature... features) {
/* 489 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */   public Endpoint createEndpoint(String bindingId, Object implementor, WebServiceFeature... features) {
/* 510 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
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
/*     */   public Endpoint createEndpoint(String bindingId, Class<?> implementorClass, Invoker invoker, WebServiceFeature... features) {
/* 533 */     throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
/*     */   }
/*     */   
/*     */   public abstract ServiceDelegate createServiceDelegate(URL paramURL, QName paramQName, Class<? extends Service> paramClass);
/*     */   
/*     */   public abstract Endpoint createEndpoint(String paramString, Object paramObject);
/*     */   
/*     */   public abstract Endpoint createAndPublishEndpoint(String paramString, Object paramObject);
/*     */   
/*     */   public abstract EndpointReference readEndpointReference(Source paramSource);
/*     */   
/*     */   public abstract <T> T getPort(EndpointReference paramEndpointReference, Class<T> paramClass, WebServiceFeature... paramVarArgs);
/*     */   
/*     */   public abstract W3CEndpointReference createW3CEndpointReference(String paramString1, QName paramQName1, QName paramQName2, List<Element> paramList1, String paramString2, List<Element> paramList2);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\spi\Provider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */