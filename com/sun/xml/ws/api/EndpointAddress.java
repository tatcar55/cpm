/*     */ package com.sun.xml.ws.api;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.ProxySelector;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EndpointAddress
/*     */ {
/*     */   @Nullable
/*     */   private URL url;
/*     */   private final URI uri;
/*     */   private final String stringForm;
/*     */   private volatile boolean dontUseProxyMethod;
/*     */   private Proxy proxy;
/*     */   
/*     */   public EndpointAddress(URI uri) {
/* 115 */     this.uri = uri;
/* 116 */     this.stringForm = uri.toString();
/*     */     try {
/* 118 */       initURL();
/* 119 */       this.proxy = chooseProxy();
/* 120 */     } catch (MalformedURLException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointAddress(String url) throws URISyntaxException {
/* 130 */     this.uri = new URI(url);
/* 131 */     this.stringForm = url;
/*     */     try {
/* 133 */       initURL();
/* 134 */       this.proxy = chooseProxy();
/* 135 */     } catch (MalformedURLException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initURL() throws MalformedURLException {
/* 142 */     String scheme = this.uri.getScheme();
/*     */     
/* 144 */     if (scheme == null) {
/* 145 */       this.url = new URL(this.uri.toString());
/*     */       return;
/*     */     } 
/* 148 */     scheme = scheme.toLowerCase();
/* 149 */     if ("http".equals(scheme) || "https".equals(scheme)) {
/* 150 */       this.url = new URL(this.uri.toASCIIString());
/*     */     } else {
/* 152 */       this.url = this.uri.toURL();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EndpointAddress create(String url) {
/*     */     try {
/* 162 */       return new EndpointAddress(url);
/* 163 */     } catch (URISyntaxException e) {
/* 164 */       throw new WebServiceException("Illegal endpoint address: " + url, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Proxy chooseProxy() {
/* 169 */     ProxySelector sel = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*     */         {
/*     */           
/*     */           public ProxySelector run()
/*     */           {
/* 174 */             return ProxySelector.getDefault();
/*     */           }
/*     */         });
/*     */     
/* 178 */     if (sel == null) {
/* 179 */       return Proxy.NO_PROXY;
/*     */     }
/*     */     
/* 182 */     if (!sel.getClass().getName().equals("sun.net.spi.DefaultProxySelector"))
/*     */     {
/* 184 */       return null;
/*     */     }
/* 186 */     Iterator<Proxy> it = sel.select(this.uri).iterator();
/* 187 */     if (it.hasNext()) {
/* 188 */       return it.next();
/*     */     }
/* 190 */     return Proxy.NO_PROXY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getURL() {
/* 200 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getURI() {
/* 210 */     return this.uri;
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
/*     */   public URLConnection openConnection() throws IOException {
/* 227 */     if (this.url == null) {
/* 228 */       throw new WebServiceException("URI=" + this.uri + " doesn't have the corresponding URL");
/*     */     }
/* 230 */     if (this.proxy != null && !this.dontUseProxyMethod) {
/*     */       try {
/* 232 */         return this.url.openConnection(this.proxy);
/* 233 */       } catch (UnsupportedOperationException e) {
/*     */ 
/*     */ 
/*     */         
/* 237 */         this.dontUseProxyMethod = true;
/*     */       } 
/*     */     }
/* 240 */     return this.url.openConnection();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 245 */     return this.stringForm;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\EndpointAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */