/*     */ package com.sun.xml.ws.transport.http.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.net.CookieHandler;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CookieManager
/*     */   extends CookieHandler
/*     */ {
/*     */   private CookiePolicy policyCallback;
/* 144 */   private CookieStore cookieJar = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieManager() {
/* 157 */     this(null, null);
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
/*     */   public CookieManager(CookieStore store, CookiePolicy cookiePolicy) {
/* 176 */     this.policyCallback = (cookiePolicy == null) ? CookiePolicy.ACCEPT_ORIGINAL_SERVER : cookiePolicy;
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (store == null) {
/* 181 */       this.cookieJar = new InMemoryCookieStore();
/*     */     } else {
/* 183 */       this.cookieJar = store;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCookiePolicy(CookiePolicy cookiePolicy) {
/* 201 */     if (cookiePolicy != null) this.policyCallback = cookiePolicy;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieStore getCookieStore() {
/* 211 */     return this.cookieJar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
/* 220 */     if (uri == null || requestHeaders == null) {
/* 221 */       throw new IllegalArgumentException("Argument is null");
/*     */     }
/*     */     
/* 224 */     Map<String, List<String>> cookieMap = new HashMap<String, List<String>>();
/*     */ 
/*     */     
/* 227 */     if (this.cookieJar == null) {
/* 228 */       return Collections.unmodifiableMap(cookieMap);
/*     */     }
/* 230 */     boolean secureLink = "https".equalsIgnoreCase(uri.getScheme());
/* 231 */     List<HttpCookie> cookies = new ArrayList<HttpCookie>();
/* 232 */     String path = uri.getPath();
/* 233 */     if (path == null || path.length() == 0) {
/* 234 */       path = "/";
/*     */     }
/* 236 */     for (HttpCookie cookie : this.cookieJar.get(uri)) {
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (pathMatches(path, cookie.getPath()) && (secureLink || !cookie.getSecure())) {
/*     */ 
/*     */         
/* 243 */         if (cookie.isHttpOnly()) {
/* 244 */           String s = uri.getScheme();
/* 245 */           if (!"http".equalsIgnoreCase(s) && !"https".equalsIgnoreCase(s)) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 250 */         String ports = cookie.getPortlist();
/* 251 */         if (ports != null && ports.length() != 0) {
/* 252 */           int port = uri.getPort();
/* 253 */           if (port == -1) {
/* 254 */             port = "https".equals(uri.getScheme()) ? 443 : 80;
/*     */           }
/* 256 */           if (isInPortList(ports, port))
/* 257 */             cookies.add(cookie); 
/*     */           continue;
/*     */         } 
/* 260 */         cookies.add(cookie);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 266 */     List<String> cookieHeader = sortByPath(cookies);
/*     */     
/* 268 */     cookieMap.put("Cookie", cookieHeader);
/* 269 */     return Collections.unmodifiableMap(cookieMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
/* 278 */     if (uri == null || responseHeaders == null) {
/* 279 */       throw new IllegalArgumentException("Argument is null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 284 */     if (this.cookieJar == null) {
/*     */       return;
/*     */     }
/* 287 */     for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
/*     */ 
/*     */       
/* 290 */       String headerKey = entry.getKey();
/* 291 */       if (headerKey == null || (!headerKey.equalsIgnoreCase("Set-Cookie2") && !headerKey.equalsIgnoreCase("Set-Cookie"))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 300 */       for (String headerValue : entry.getValue()) {
/*     */         try {
/* 302 */           List<HttpCookie> cookies = HttpCookie.parse(headerValue);
/* 303 */           for (HttpCookie cookie : cookies) {
/* 304 */             if (cookie.getPath() == null) {
/*     */ 
/*     */               
/* 307 */               String path = uri.getPath();
/* 308 */               if (!path.endsWith("/")) {
/* 309 */                 int i = path.lastIndexOf("/");
/* 310 */                 if (i > 0) {
/* 311 */                   path = path.substring(0, i + 1);
/*     */                 } else {
/* 313 */                   path = "/";
/*     */                 } 
/*     */               } 
/* 316 */               cookie.setPath(path);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 323 */             if (cookie.getDomain() == null) {
/* 324 */               cookie.setDomain(uri.getHost());
/*     */             }
/* 326 */             String ports = cookie.getPortlist();
/* 327 */             if (ports != null) {
/* 328 */               int port = uri.getPort();
/* 329 */               if (port == -1) {
/* 330 */                 port = "https".equals(uri.getScheme()) ? 443 : 80;
/*     */               }
/* 332 */               if (ports.length() == 0) {
/*     */ 
/*     */                 
/* 335 */                 cookie.setPortlist("" + port);
/* 336 */                 if (shouldAcceptInternal(uri, cookie)) {
/* 337 */                   this.cookieJar.add(uri, cookie);
/*     */                 }
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/* 343 */               if (isInPortList(ports, port) && shouldAcceptInternal(uri, cookie))
/*     */               {
/* 345 */                 this.cookieJar.add(uri, cookie);
/*     */               }
/*     */               continue;
/*     */             } 
/* 349 */             if (shouldAcceptInternal(uri, cookie)) {
/* 350 */               this.cookieJar.add(uri, cookie);
/*     */             }
/*     */           }
/*     */         
/* 354 */         } catch (IllegalArgumentException e) {}
/*     */       } 
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
/*     */   private boolean shouldAcceptInternal(URI uri, HttpCookie cookie) {
/*     */     try {
/* 368 */       return this.policyCallback.shouldAccept(uri, cookie);
/* 369 */     } catch (Exception ignored) {
/* 370 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isInPortList(String lst, int port) {
/* 376 */     int i = lst.indexOf(",");
/* 377 */     int val = -1;
/* 378 */     while (i > 0) {
/*     */       try {
/* 380 */         val = Integer.parseInt(lst.substring(0, i));
/* 381 */         if (val == port) {
/* 382 */           return true;
/*     */         }
/* 384 */       } catch (NumberFormatException numberFormatException) {}
/*     */       
/* 386 */       lst = lst.substring(i + 1);
/* 387 */       i = lst.indexOf(",");
/*     */     } 
/* 389 */     if (lst.length() != 0) {
/*     */       try {
/* 391 */         val = Integer.parseInt(lst);
/* 392 */         if (val == port) {
/* 393 */           return true;
/*     */         }
/* 395 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */     
/* 398 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean pathMatches(String path, String pathToMatchWith) {
/* 405 */     if (path == pathToMatchWith)
/* 406 */       return true; 
/* 407 */     if (path == null || pathToMatchWith == null)
/* 408 */       return false; 
/* 409 */     if (path.startsWith(pathToMatchWith)) {
/* 410 */       return true;
/*     */     }
/* 412 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> sortByPath(List<HttpCookie> cookies) {
/* 421 */     Collections.sort(cookies, new CookiePathComparator());
/*     */     
/* 423 */     List<String> cookieHeader = new ArrayList<String>();
/* 424 */     for (HttpCookie cookie : cookies) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 429 */       if (cookies.indexOf(cookie) == 0 && cookie.getVersion() > 0) {
/* 430 */         cookieHeader.add("$Version=\"1\"");
/*     */       }
/*     */       
/* 433 */       cookieHeader.add(cookie.toString());
/*     */     } 
/* 435 */     return cookieHeader;
/*     */   }
/*     */   
/*     */   static class CookiePathComparator
/*     */     implements Comparator<HttpCookie>, Serializable {
/*     */     public int compare(HttpCookie c1, HttpCookie c2) {
/* 441 */       if (c1 == c2) return 0; 
/* 442 */       if (c1 == null) return -1; 
/* 443 */       if (c2 == null) return 1;
/*     */ 
/*     */       
/* 446 */       if (!c1.getName().equals(c2.getName())) return 0;
/*     */ 
/*     */       
/* 449 */       if (c1.getPath().startsWith(c2.getPath()))
/* 450 */         return -1; 
/* 451 */       if (c2.getPath().startsWith(c1.getPath())) {
/* 452 */         return 1;
/*     */       }
/* 454 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\CookieManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */