/*     */ package com.sun.xml.rpc.client.http;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CookieJar
/*     */   implements Serializable
/*     */ {
/*  48 */   private Hashtable cookieJar = new Hashtable<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void recordAnyCookies(URLConnection connection) {
/*  66 */     HttpURLConnection httpConn = (HttpURLConnection)connection;
/*     */ 
/*     */     
/*  69 */     int hi = 1; String headerKey;
/*  70 */     for (; (headerKey = httpConn.getHeaderFieldKey(hi)) != null; 
/*  71 */       hi++) {
/*  72 */       if (headerKey.equalsIgnoreCase("set-cookie")) {
/*  73 */         String cookieValue = httpConn.getHeaderField(hi);
/*     */         
/*  75 */         recordCookie(httpConn, cookieValue);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recordCookie(HttpURLConnection httpConn, String cookieValue) {
/*  86 */     HttpCookie cookie = new HttpCookie(httpConn.getURL(), cookieValue);
/*     */ 
/*     */ 
/*     */     
/*  90 */     String[] twodot = { "com", "edu", "net", "org", "gov", "mil", "int" };
/*  91 */     String domain = cookie.getDomain();
/*     */     
/*  93 */     if (domain == null) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     domain = domain.toLowerCase();
/*     */     
/*  99 */     String host = httpConn.getURL().getHost();
/*     */     
/* 101 */     host = host.toLowerCase();
/*     */     
/* 103 */     boolean domainOK = host.equals(domain);
/*     */     
/* 105 */     if (!domainOK && host.endsWith(domain)) {
/* 106 */       int dotsNeeded = 2;
/*     */       
/* 108 */       for (int i = 0; i < twodot.length; i++) {
/* 109 */         if (domain.endsWith(twodot[i])) {
/* 110 */           dotsNeeded = 1;
/*     */         }
/*     */       } 
/*     */       
/* 114 */       int lastChar = domain.length();
/*     */       
/* 116 */       for (; lastChar > 0 && dotsNeeded > 0; dotsNeeded--) {
/* 117 */         lastChar = domain.lastIndexOf('.', lastChar - 1);
/*     */       }
/*     */       
/* 120 */       if (lastChar > 0) {
/* 121 */         domainOK = true;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     if (domainOK) {
/* 126 */       recordCookie(cookie);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recordCookie(HttpCookie cookie) {
/* 136 */     recordCookieToJar(cookie, this.cookieJar, true);
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
/*     */   private void setCookie(URL url, String cookieHeader) {
/* 163 */     HttpCookie cookie = new HttpCookie(url, cookieHeader);
/*     */     
/* 165 */     recordCookie(cookie);
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
/*     */   private void recordCookieToJar(HttpCookie cookie, Hashtable<String, Vector> jar, boolean doNotify) {
/* 179 */     if (shouldRejectCookie(cookie)) {
/*     */       return;
/*     */     }
/*     */     
/* 183 */     String domain = cookie.getDomain().toLowerCase();
/* 184 */     Vector cookieList = (Vector)jar.get(domain);
/*     */     
/* 186 */     if (cookieList == null) {
/* 187 */       cookieList = new Vector();
/*     */     }
/*     */     
/* 190 */     if (addOrReplaceCookie(cookieList, cookie, doNotify)) {
/* 191 */       jar.put(domain, cookieList);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addOrReplaceCookie(Vector<HttpCookie> cookies, HttpCookie cookie, boolean doNotify) {
/* 215 */     int numCookies = cookies.size();
/* 216 */     String path = cookie.getPath();
/* 217 */     String name = cookie.getName();
/* 218 */     HttpCookie replaced = null;
/* 219 */     int replacedIndex = -1;
/*     */     
/* 221 */     for (int i = 0; i < numCookies; i++) {
/* 222 */       HttpCookie existingCookie = cookies.elementAt(i);
/* 223 */       String existingPath = existingCookie.getPath();
/*     */       
/* 225 */       if (path.equals(existingPath)) {
/* 226 */         String existingName = existingCookie.getName();
/*     */         
/* 228 */         if (name.equals(existingName)) {
/*     */ 
/*     */           
/* 231 */           replaced = existingCookie;
/* 232 */           replacedIndex = i;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     if (replaced != null) {
/* 241 */       cookies.setElementAt(cookie, replacedIndex);
/*     */     } else {
/* 243 */       cookies.addElement(cookie);
/*     */     } 
/*     */     
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldRejectCookie(HttpCookie cookie) {
/* 256 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void applyRelevantCookies(URLConnection connection) {
/* 261 */     applyRelevantCookies(connection.getURL(), connection);
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyRelevantCookies(URL url, URLConnection connection) {
/* 266 */     HttpURLConnection httpConn = (HttpURLConnection)connection;
/* 267 */     String host = url.getHost();
/*     */     
/* 269 */     applyCookiesForHost(host, url, httpConn);
/*     */ 
/*     */     
/*     */     int index;
/*     */     
/* 274 */     while ((index = host.indexOf('.', 1)) >= 0) {
/*     */ 
/*     */       
/* 277 */       host = host.substring(index + 1);
/*     */       
/* 279 */       applyCookiesForHost(host, url, httpConn);
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
/*     */   private void applyCookiesForHost(String host, URL url, HttpURLConnection httpConn) {
/* 294 */     Vector cookieList = (Vector)this.cookieJar.get(host);
/*     */     
/* 296 */     if (cookieList == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     String path = url.getFile();
/* 304 */     int queryInd = path.indexOf('?');
/*     */     
/* 306 */     if (queryInd > 0)
/*     */     {
/*     */       
/* 309 */       path = path.substring(0, queryInd);
/*     */     }
/*     */     
/* 312 */     Enumeration<HttpCookie> cookies = cookieList.elements();
/* 313 */     Vector<HttpCookie> cookiesToSend = new Vector(10);
/*     */     
/* 315 */     while (cookies.hasMoreElements()) {
/* 316 */       HttpCookie cookie = cookies.nextElement();
/* 317 */       String cookiePath = cookie.getPath();
/*     */       
/* 319 */       if (path.startsWith(cookiePath))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 324 */         if (!cookie.hasExpired()) {
/* 325 */           cookiesToSend.addElement(cookie);
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 353 */     if (cookiesToSend.size() > 1) {
/* 354 */       for (int i = 0; i < cookiesToSend.size() - 1; i++) {
/* 355 */         HttpCookie headC = cookiesToSend.elementAt(i);
/* 356 */         String head = headC.getPath();
/*     */ 
/*     */ 
/*     */         
/* 360 */         if (!head.endsWith("/")) {
/* 361 */           head = head + "/";
/*     */         }
/*     */         
/* 364 */         for (int j = i + 1; j < cookiesToSend.size(); j++) {
/* 365 */           HttpCookie scanC = cookiesToSend.elementAt(j);
/* 366 */           String scan = scanC.getPath();
/*     */           
/* 368 */           if (!scan.endsWith("/")) {
/* 369 */             scan = scan + "/";
/*     */           }
/*     */           
/* 372 */           int headCount = 0;
/* 373 */           int index = -1;
/*     */           
/* 375 */           while ((index = head.indexOf('/', index + 1)) != -1) {
/* 376 */             headCount++;
/*     */           }
/*     */           
/* 379 */           index = -1;
/*     */           
/* 381 */           int scanCount = 0;
/*     */           
/* 383 */           while ((index = scan.indexOf('/', index + 1)) != -1) {
/* 384 */             scanCount++;
/*     */           }
/*     */           
/* 387 */           if (scanCount > headCount) {
/* 388 */             cookiesToSend.setElementAt(headC, j);
/* 389 */             cookiesToSend.setElementAt(scanC, i);
/*     */             
/* 391 */             headC = scanC;
/* 392 */             head = scan;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 399 */     cookies = cookiesToSend.elements();
/*     */     
/* 401 */     String cookieStr = null;
/*     */     
/* 403 */     while (cookies.hasMoreElements()) {
/* 404 */       HttpCookie cookie = cookies.nextElement();
/*     */       
/* 406 */       if (cookieStr == null) {
/* 407 */         cookieStr = cookie.getNameValue(); continue;
/*     */       } 
/* 409 */       cookieStr = cookieStr + "; " + cookie.getNameValue();
/*     */     } 
/*     */ 
/*     */     
/* 413 */     if (cookieStr != null)
/* 414 */       httpConn.setRequestProperty("Cookie", cookieStr); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\http\CookieJar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */