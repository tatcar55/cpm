/*     */ package com.sun.xml.ws.transport.http;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public abstract class HttpAdapterList<T extends HttpAdapter>
/*     */   extends AbstractList<T>
/*     */   implements DeploymentDescriptorParser.AdapterFactory<T>
/*     */ {
/*  73 */   private final List<T> adapters = new ArrayList<T>();
/*  74 */   private final Map<PortInfo, String> addressMap = new HashMap<PortInfo, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   public T createAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/*  79 */     T t = createHttpAdapter(name, urlPattern, endpoint);
/*  80 */     this.adapters.add(t);
/*  81 */     WSDLPort port = endpoint.getPort();
/*  82 */     if (port != null) {
/*  83 */       PortInfo portInfo = new PortInfo(port.getOwner().getName(), port.getName().getLocalPart(), endpoint.getImplementationClass());
/*  84 */       this.addressMap.put(portInfo, getValidPath(urlPattern));
/*     */     } 
/*  86 */     return t;
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
/*     */   private String getValidPath(@NotNull String urlPattern) {
/*  99 */     if (urlPattern.endsWith("/*")) {
/* 100 */       return urlPattern.substring(0, urlPattern.length() - 2);
/*     */     }
/* 102 */     return urlPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PortAddressResolver createPortAddressResolver(final String baseAddress, final Class<?> endpointImpl) {
/* 112 */     return new PortAddressResolver()
/*     */       {
/*     */         public String getAddressFor(@NotNull QName serviceName, @NotNull String portName) {
/* 115 */           String urlPattern = (String)HttpAdapterList.this.addressMap.get(new HttpAdapterList.PortInfo(serviceName, portName, endpointImpl));
/* 116 */           if (urlPattern == null)
/*     */           {
/*     */             
/* 119 */             for (Map.Entry<HttpAdapterList.PortInfo, String> e : (Iterable<Map.Entry<HttpAdapterList.PortInfo, String>>)HttpAdapterList.this.addressMap.entrySet()) {
/* 120 */               if (serviceName.equals((e.getKey()).serviceName) && portName.equals((e.getKey()).portName)) {
/* 121 */                 urlPattern = e.getValue();
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/* 126 */           return (urlPattern == null) ? null : (baseAddress + urlPattern);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int index) {
/* 134 */     return this.adapters.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 139 */     return this.adapters.size();
/*     */   }
/*     */   
/*     */   protected abstract T createHttpAdapter(String paramString1, String paramString2, WSEndpoint<?> paramWSEndpoint);
/*     */   
/*     */   private static class PortInfo {
/*     */     private final QName serviceName;
/*     */     
/*     */     PortInfo(@NotNull QName serviceName, @NotNull String portName, Class<?> implClass) {
/* 148 */       this.serviceName = serviceName;
/* 149 */       this.portName = portName;
/* 150 */       this.implClass = implClass;
/*     */     }
/*     */     private final String portName; private final Class<?> implClass;
/*     */     
/*     */     public boolean equals(Object portInfo) {
/* 155 */       if (portInfo instanceof PortInfo) {
/* 156 */         PortInfo that = (PortInfo)portInfo;
/* 157 */         if (this.implClass == null) {
/* 158 */           return (this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && that.implClass == null);
/*     */         }
/* 160 */         return (this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && this.implClass.equals(that.implClass));
/*     */       } 
/* 162 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 167 */       int retVal = this.serviceName.hashCode() + this.portName.hashCode();
/* 168 */       return (this.implClass != null) ? (retVal + this.implClass.hashCode()) : retVal;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\HttpAdapterList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */