/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
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
/*     */ public final class TCPAdapterList
/*     */   extends AbstractList<TCPAdapter>
/*     */   implements DeploymentDescriptorParser.AdapterFactory<TCPAdapter>
/*     */ {
/*  59 */   private final List<TCPAdapter> adapters = new ArrayList<TCPAdapter>();
/*  60 */   private final Map<PortInfo, String> addressMap = new HashMap<PortInfo, String>();
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPAdapter createAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/*  65 */     TCPAdapter tcpAdapter = new TCPAdapter(name, urlPattern, endpoint);
/*  66 */     this.adapters.add(tcpAdapter);
/*  67 */     WSDLPort port = endpoint.getPort();
/*  68 */     if (port != null) {
/*  69 */       PortInfo portInfo = new PortInfo(port.getOwner().getName(), port.getName().getLocalPart(), endpoint.getImplementationClass());
/*  70 */       this.addressMap.put(portInfo, getValidPath(urlPattern));
/*     */     } 
/*  72 */     return tcpAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getValidPath(@NotNull String urlPattern) {
/*  79 */     if (urlPattern.endsWith("/*")) {
/*  80 */       return urlPattern.substring(0, urlPattern.length() - 2);
/*     */     }
/*  82 */     return urlPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PortAddressResolver createPortAddressResolver(final String baseAddress, final Class<?> endpointImpl) {
/*  90 */     return new PortAddressResolver()
/*     */       {
/*     */         public String getAddressFor(@NotNull QName serviceName, @NotNull String portName) {
/*  93 */           String urlPattern = (String)TCPAdapterList.this.addressMap.get(new TCPAdapterList.PortInfo(serviceName, portName, endpointImpl));
/*  94 */           if (urlPattern == null)
/*     */           {
/*     */             
/*  97 */             for (Map.Entry<TCPAdapterList.PortInfo, String> e : (Iterable<Map.Entry<TCPAdapterList.PortInfo, String>>)TCPAdapterList.this.addressMap.entrySet()) {
/*  98 */               if (serviceName.equals((e.getKey()).serviceName) && portName.equals((e.getKey()).portName)) {
/*  99 */                 urlPattern = e.getValue();
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/* 104 */           return (urlPattern == null) ? null : (baseAddress + urlPattern);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPAdapter get(int index) {
/* 112 */     return this.adapters.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 117 */     return this.adapters.size();
/*     */   }
/*     */   
/*     */   private static class PortInfo {
/*     */     private final QName serviceName;
/*     */     private final String portName;
/*     */     private final Class<?> implClass;
/*     */     
/*     */     PortInfo(@NotNull QName serviceName, @NotNull String portName, Class<?> implClass) {
/* 126 */       this.serviceName = serviceName;
/* 127 */       this.portName = portName;
/* 128 */       this.implClass = implClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object portInfo) {
/* 133 */       if (portInfo instanceof PortInfo) {
/* 134 */         PortInfo that = (PortInfo)portInfo;
/* 135 */         if (this.implClass == null) {
/* 136 */           return (this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && that.implClass == null);
/*     */         }
/* 138 */         return (this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && this.implClass.equals(that.implClass));
/*     */       } 
/* 140 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 145 */       int retVal = this.serviceName.hashCode() + this.portName.hashCode();
/* 146 */       return (this.implClass != null) ? (retVal + this.implClass.hashCode()) : retVal;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPAdapterList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */