/*     */ package com.sun.xml.ws.transport.http.client;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InMemoryCookieStore
/*     */   implements CookieStore
/*     */ {
/*  65 */   private static final Logger LOGGER = Logger.getLogger(HttpTransportPipe.class.getName());
/*     */ 
/*     */   
/*  68 */   private List<HttpCookie> cookieJar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private Map<String, List<HttpCookie>> domainIndex = null;
/*  75 */   private Map<URI, List<HttpCookie>> uriIndex = null;
/*     */ 
/*     */   
/*  78 */   private ReentrantLock lock = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InMemoryCookieStore() {
/*  85 */     this.cookieJar = new ArrayList<HttpCookie>();
/*  86 */     this.domainIndex = new HashMap<String, List<HttpCookie>>();
/*  87 */     this.uriIndex = new HashMap<URI, List<HttpCookie>>();
/*     */     
/*  89 */     this.lock = new ReentrantLock(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(URI uri, HttpCookie cookie) {
/*  98 */     if (cookie == null) {
/*  99 */       throw new NullPointerException("cookie is null");
/*     */     }
/*     */ 
/*     */     
/* 103 */     this.lock.lock();
/*     */     
/*     */     try {
/* 106 */       this.cookieJar.remove(cookie);
/*     */ 
/*     */       
/* 109 */       if (cookie.getMaxAge() != 0L) {
/* 110 */         this.cookieJar.add(cookie);
/*     */         
/* 112 */         if (cookie.getDomain() != null) {
/* 113 */           addIndex(this.domainIndex, cookie.getDomain(), cookie);
/*     */         }
/*     */         
/* 116 */         addIndex(this.uriIndex, getEffectiveURI(uri), cookie);
/*     */       } 
/*     */     } finally {
/* 119 */       this.lock.unlock();
/*     */     } 
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
/*     */   public List<HttpCookie> get(URI uri) {
/* 134 */     if (uri == null) {
/* 135 */       throw new NullPointerException("uri is null");
/*     */     }
/*     */     
/* 138 */     List<HttpCookie> cookies = new ArrayList<HttpCookie>();
/* 139 */     boolean secureLink = "https".equalsIgnoreCase(uri.getScheme());
/* 140 */     this.lock.lock();
/*     */     
/*     */     try {
/* 143 */       getInternal1(cookies, this.domainIndex, uri.getHost(), secureLink);
/*     */       
/* 145 */       getInternal2(cookies, this.uriIndex, getEffectiveURI(uri), secureLink);
/*     */     } finally {
/* 147 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 150 */     return cookies;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<HttpCookie> getCookies() {
/*     */     List<HttpCookie> list;
/* 160 */     this.lock.lock();
/*     */     try {
/* 162 */       Iterator<HttpCookie> it = this.cookieJar.iterator();
/* 163 */       while (it.hasNext()) {
/* 164 */         if (((HttpCookie)it.next()).hasExpired()) {
/* 165 */           it.remove();
/*     */         }
/*     */       } 
/* 168 */       list = Collections.unmodifiableList(this.cookieJar);
/* 169 */     } catch (Exception e) {
/* 170 */       list = Collections.unmodifiableList(this.cookieJar);
/* 171 */       LOGGER.log(Level.INFO, (String)null, e);
/*     */     } finally {
/* 173 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 176 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<URI> getURIs() {
/*     */     List<URI> list;
/* 187 */     this.lock.lock();
/*     */     try {
/* 189 */       Iterator<URI> it = this.uriIndex.keySet().iterator();
/* 190 */       while (it.hasNext()) {
/* 191 */         URI uri = it.next();
/* 192 */         List<HttpCookie> cookies = this.uriIndex.get(uri);
/* 193 */         if (cookies == null || cookies.size() == 0)
/*     */         {
/*     */           
/* 196 */           it.remove();
/*     */         }
/*     */       } 
/* 199 */       list = new ArrayList<URI>(this.uriIndex.keySet());
/* 200 */     } catch (Exception e) {
/* 201 */       list = new ArrayList(this.uriIndex.keySet());
/* 202 */       LOGGER.log(Level.INFO, (String)null, e);
/*     */     } finally {
/* 204 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 207 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(URI uri, HttpCookie ck) {
/* 217 */     if (ck == null) {
/* 218 */       throw new NullPointerException("cookie is null");
/*     */     }
/*     */     
/* 221 */     boolean modified = false;
/* 222 */     this.lock.lock();
/*     */     try {
/* 224 */       modified = this.cookieJar.remove(ck);
/*     */     } finally {
/* 226 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 229 */     return modified;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll() {
/* 238 */     this.lock.lock();
/*     */     try {
/* 240 */       this.cookieJar.clear();
/* 241 */       this.domainIndex.clear();
/* 242 */       this.uriIndex.clear();
/*     */     } finally {
/* 244 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 247 */     return true;
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
/*     */   private boolean netscapeDomainMatches(String domain, String host) {
/* 267 */     if (domain == null || host == null) {
/* 268 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 272 */     boolean isLocalDomain = ".local".equalsIgnoreCase(domain);
/* 273 */     int embeddedDotInDomain = domain.indexOf('.');
/* 274 */     if (embeddedDotInDomain == 0) {
/* 275 */       embeddedDotInDomain = domain.indexOf('.', 1);
/*     */     }
/* 277 */     if (!isLocalDomain && (embeddedDotInDomain == -1 || embeddedDotInDomain == domain.length() - 1)) {
/* 278 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 282 */     int firstDotInHost = host.indexOf('.');
/* 283 */     if (firstDotInHost == -1 && isLocalDomain) {
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     int domainLength = domain.length();
/* 288 */     int lengthDiff = host.length() - domainLength;
/* 289 */     if (lengthDiff == 0)
/*     */     {
/* 291 */       return host.equalsIgnoreCase(domain); } 
/* 292 */     if (lengthDiff > 0) {
/*     */ 
/*     */       
/* 295 */       String D = host.substring(lengthDiff);
/*     */       
/* 297 */       return D.equalsIgnoreCase(domain);
/* 298 */     }  if (lengthDiff == -1)
/*     */     {
/* 300 */       return (domain.charAt(0) == '.' && host.equalsIgnoreCase(domain.substring(1)));
/*     */     }
/*     */ 
/*     */     
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getInternal1(List<HttpCookie> cookies, Map<String, List<HttpCookie>> cookieIndex, String host, boolean secureLink) {
/* 311 */     ArrayList<HttpCookie> toRemove = new ArrayList<HttpCookie>();
/* 312 */     for (Map.Entry<String, List<HttpCookie>> entry : cookieIndex.entrySet()) {
/* 313 */       String domain = entry.getKey();
/* 314 */       List<HttpCookie> lst = entry.getValue();
/* 315 */       for (HttpCookie c : lst) {
/* 316 */         if ((c.getVersion() == 0 && netscapeDomainMatches(domain, host)) || (c.getVersion() == 1 && HttpCookie.domainMatches(domain, host))) {
/*     */           
/* 318 */           if (this.cookieJar.indexOf(c) != -1) {
/*     */             
/* 320 */             if (!c.hasExpired()) {
/*     */ 
/*     */               
/* 323 */               if ((secureLink || !c.getSecure()) && !cookies.contains(c))
/*     */               {
/* 325 */                 cookies.add(c); } 
/*     */               continue;
/*     */             } 
/* 328 */             toRemove.add(c);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 333 */           toRemove.add(c);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 338 */       for (HttpCookie c : toRemove) {
/* 339 */         lst.remove(c);
/* 340 */         this.cookieJar.remove(c);
/*     */       } 
/*     */       
/* 343 */       toRemove.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void getInternal2(List<HttpCookie> cookies, Map<T, List<HttpCookie>> cookieIndex, Comparable<T> comparator, boolean secureLink) {
/* 355 */     for (Map.Entry<T, List<HttpCookie>> entry : cookieIndex.entrySet()) {
/* 356 */       T index = entry.getKey();
/* 357 */       if (comparator.compareTo(index) == 0) {
/* 358 */         List<HttpCookie> indexedCookies = entry.getValue();
/*     */         
/* 360 */         if (indexedCookies != null) {
/* 361 */           Iterator<HttpCookie> it = indexedCookies.iterator();
/* 362 */           while (it.hasNext()) {
/* 363 */             HttpCookie ck = it.next();
/* 364 */             if (this.cookieJar.indexOf(ck) != -1) {
/*     */               
/* 366 */               if (!ck.hasExpired()) {
/*     */                 
/* 368 */                 if ((secureLink || !ck.getSecure()) && !cookies.contains(ck))
/*     */                 {
/* 370 */                   cookies.add(ck); } 
/*     */                 continue;
/*     */               } 
/* 373 */               it.remove();
/* 374 */               this.cookieJar.remove(ck);
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 379 */             it.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void addIndex(Map<T, List<HttpCookie>> indexStore, T index, HttpCookie cookie) {
/* 392 */     if (index != null) {
/* 393 */       List<HttpCookie> cookies = indexStore.get(index);
/* 394 */       if (cookies != null) {
/*     */         
/* 396 */         cookies.remove(cookie);
/*     */         
/* 398 */         cookies.add(cookie);
/*     */       } else {
/* 400 */         cookies = new ArrayList<HttpCookie>();
/* 401 */         cookies.add(cookie);
/* 402 */         indexStore.put(index, cookies);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URI getEffectiveURI(URI uri) {
/*     */     URI uRI;
/*     */     try {
/* 415 */       uRI = new URI("http", uri.getHost(), null, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 421 */     catch (URISyntaxException ignored) {
/* 422 */       uRI = uri;
/*     */     } 
/*     */     
/* 425 */     return uRI;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\InMemoryCookieStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */