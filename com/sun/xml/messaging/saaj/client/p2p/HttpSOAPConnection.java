/*     */ package com.sun.xml.messaging.saaj.client.p2p;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.util.Base64;
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.messaging.saaj.util.ParseUtil;
/*     */ import com.sun.xml.messaging.saaj.util.SAAJUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPConnection;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpSOAPConnection
/*     */   extends SOAPConnection
/*     */ {
/*  67 */   public static final String vmVendor = SAAJUtil.getSystemProperty("java.vendor.url");
/*     */   private static final String sunVmVendor = "http://java.sun.com/";
/*     */   private static final String ibmVmVendor = "http://www.ibm.com/";
/*  70 */   private static final boolean isSunVM = "http://java.sun.com/".equals(vmVendor);
/*  71 */   private static final boolean isIBMVM = "http://www.ibm.com/".equals(vmVendor);
/*     */   
/*     */   private static final String JAXM_URLENDPOINT = "javax.xml.messaging.URLEndpoint";
/*  74 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.client.p2p", "com.sun.xml.messaging.saaj.client.p2p.LocalStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   MessageFactory messageFactory = null;
/*     */   
/*     */   boolean closed = false;
/*     */   private static final String SSL_PKG;
/*     */   
/*     */   public HttpSOAPConnection() throws SOAPException {
/*     */     try {
/*  86 */       this.messageFactory = MessageFactory.newInstance("Dynamic Protocol");
/*  87 */     } catch (NoSuchMethodError ex) {
/*     */       
/*  89 */       this.messageFactory = MessageFactory.newInstance();
/*  90 */     } catch (Exception ex) {
/*  91 */       log.log(Level.SEVERE, "SAAJ0001.p2p.cannot.create.msg.factory", ex);
/*  92 */       throw new SOAPExceptionImpl("Unable to create message factory", ex);
/*     */     } 
/*     */   }
/*     */   private static final String SSL_PROVIDER; private static final int dL = 0;
/*     */   public void close() throws SOAPException {
/*  97 */     if (this.closed) {
/*  98 */       log.severe("SAAJ0002.p2p.close.already.closed.conn");
/*  99 */       throw new SOAPExceptionImpl("Connection already closed");
/*     */     } 
/*     */     
/* 102 */     this.messageFactory = null;
/* 103 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage call(SOAPMessage message, Object endPoint) throws SOAPException {
/* 108 */     if (this.closed) {
/* 109 */       log.severe("SAAJ0003.p2p.call.already.closed.conn");
/* 110 */       throw new SOAPExceptionImpl("Connection is closed");
/*     */     } 
/*     */     
/* 113 */     Class<?> urlEndpointClass = null;
/* 114 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */     try {
/* 116 */       if (loader != null) {
/* 117 */         urlEndpointClass = loader.loadClass("javax.xml.messaging.URLEndpoint");
/*     */       } else {
/* 119 */         urlEndpointClass = Class.forName("javax.xml.messaging.URLEndpoint");
/*     */       } 
/* 121 */     } catch (ClassNotFoundException ex) {
/*     */       
/* 123 */       if (log.isLoggable(Level.FINEST)) {
/* 124 */         log.finest("SAAJ0090.p2p.endpoint.available.only.for.JAXM");
/*     */       }
/*     */     } 
/* 127 */     if (urlEndpointClass != null && 
/* 128 */       urlEndpointClass.isInstance(endPoint)) {
/* 129 */       String url = null;
/*     */       
/*     */       try {
/* 132 */         Method m = urlEndpointClass.getMethod("getURL", (Class[])null);
/* 133 */         url = (String)m.invoke(endPoint, (Object[])null);
/* 134 */       } catch (Exception ex) {
/*     */         
/* 136 */         log.log(Level.SEVERE, "SAAJ0004.p2p.internal.err", ex);
/* 137 */         throw new SOAPExceptionImpl("Internal error: " + ex.getMessage());
/*     */       } 
/*     */       
/*     */       try {
/* 141 */         endPoint = new URL(url);
/* 142 */       } catch (MalformedURLException mex) {
/* 143 */         log.log(Level.SEVERE, "SAAJ0005.p2p.", mex);
/* 144 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 149 */     if (endPoint instanceof String) {
/*     */       try {
/* 151 */         endPoint = new URL((String)endPoint);
/* 152 */       } catch (MalformedURLException mex) {
/* 153 */         log.log(Level.SEVERE, "SAAJ0006.p2p.bad.URL", mex);
/* 154 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 158 */     if (endPoint instanceof URL)
/*     */       try {
/* 160 */         SOAPMessage response = post(message, (URL)endPoint);
/* 161 */         return response;
/* 162 */       } catch (Exception ex) {
/*     */         
/* 164 */         throw new SOAPExceptionImpl(ex);
/*     */       }  
/* 166 */     log.severe("SAAJ0007.p2p.bad.endPoint.type");
/* 167 */     throw new SOAPExceptionImpl("Bad endPoint type " + endPoint);
/*     */   }
/*     */ 
/*     */   
/*     */   SOAPMessage post(SOAPMessage message, URL endPoint) throws SOAPException, IOException {
/* 172 */     boolean isFailure = false;
/*     */     
/* 174 */     URL url = null;
/* 175 */     HttpURLConnection httpConnection = null;
/*     */     
/* 177 */     int responseCode = 0;
/*     */     try {
/* 179 */       if (endPoint.getProtocol().equals("https"))
/*     */       {
/* 181 */         initHttps();
/*     */       }
/* 183 */       URI uri = new URI(endPoint.toString());
/* 184 */       String userInfo = uri.getRawUserInfo();
/*     */       
/* 186 */       url = endPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       if (!url.getProtocol().equalsIgnoreCase("http") && !url.getProtocol().equalsIgnoreCase("https")) {
/*     */         
/* 195 */         log.severe("SAAJ0052.p2p.protocol.mustbe.http.or.https");
/* 196 */         throw new IllegalArgumentException("Protocol " + url.getProtocol() + " not supported in URL " + url);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       httpConnection = createConnection(url);
/*     */       
/* 204 */       httpConnection.setRequestMethod("POST");
/*     */       
/* 206 */       httpConnection.setDoOutput(true);
/* 207 */       httpConnection.setDoInput(true);
/* 208 */       httpConnection.setUseCaches(false);
/* 209 */       httpConnection.setInstanceFollowRedirects(true);
/*     */       
/* 211 */       if (message.saveRequired()) {
/* 212 */         message.saveChanges();
/*     */       }
/* 214 */       MimeHeaders headers = message.getMimeHeaders();
/*     */       
/* 216 */       Iterator<MimeHeader> it = headers.getAllHeaders();
/* 217 */       boolean hasAuth = false;
/* 218 */       while (it.hasNext()) {
/* 219 */         MimeHeader header = it.next();
/*     */         
/* 221 */         String[] values = headers.getHeader(header.getName());
/* 222 */         if (values.length == 1) {
/* 223 */           httpConnection.setRequestProperty(header.getName(), header.getValue());
/*     */         }
/*     */         else {
/*     */           
/* 227 */           StringBuffer concat = new StringBuffer();
/* 228 */           int i = 0;
/* 229 */           while (i < values.length) {
/* 230 */             if (i != 0)
/* 231 */               concat.append(','); 
/* 232 */             concat.append(values[i]);
/* 233 */             i++;
/*     */           } 
/*     */           
/* 236 */           httpConnection.setRequestProperty(header.getName(), concat.toString());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 241 */         if ("Authorization".equals(header.getName())) {
/* 242 */           hasAuth = true;
/* 243 */           if (log.isLoggable(Level.FINE)) {
/* 244 */             log.fine("SAAJ0091.p2p.https.auth.in.POST.true");
/*     */           }
/*     */         } 
/*     */       } 
/* 248 */       if (!hasAuth && userInfo != null) {
/* 249 */         initAuthUserInfo(httpConnection, userInfo);
/*     */       }
/*     */       
/* 252 */       OutputStream out = httpConnection.getOutputStream();
/*     */       try {
/* 254 */         message.writeTo(out);
/* 255 */         out.flush();
/*     */       } finally {
/* 257 */         out.close();
/*     */       } 
/*     */       
/* 260 */       httpConnection.connect();
/*     */ 
/*     */       
/*     */       try {
/* 264 */         responseCode = httpConnection.getResponseCode();
/*     */ 
/*     */         
/* 267 */         if (responseCode == 500) {
/* 268 */           isFailure = true;
/*     */ 
/*     */         
/*     */         }
/* 272 */         else if (responseCode / 100 != 2) {
/* 273 */           log.log(Level.SEVERE, "SAAJ0008.p2p.bad.response", (Object[])new String[] { httpConnection.getResponseMessage() });
/*     */ 
/*     */           
/* 276 */           throw new SOAPExceptionImpl("Bad response: (" + responseCode + httpConnection.getResponseMessage());
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 282 */       catch (IOException e) {
/*     */         
/* 284 */         responseCode = httpConnection.getResponseCode();
/* 285 */         if (responseCode == 500) {
/* 286 */           isFailure = true;
/*     */         } else {
/* 288 */           throw e;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 293 */     } catch (SOAPException ex) {
/* 294 */       throw ex;
/* 295 */     } catch (Exception ex) {
/* 296 */       log.severe("SAAJ0009.p2p.msg.send.failed");
/* 297 */       throw new SOAPExceptionImpl("Message send failed", ex);
/*     */     } 
/*     */     
/* 300 */     SOAPMessage response = null;
/* 301 */     InputStream httpIn = null;
/* 302 */     if (responseCode == 200 || isFailure) {
/*     */       try {
/* 304 */         MimeHeaders headers = new MimeHeaders();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 310 */         int i = 1;
/*     */         
/*     */         while (true) {
/* 313 */           String key = httpConnection.getHeaderFieldKey(i);
/* 314 */           String value = httpConnection.getHeaderField(i);
/*     */           
/* 316 */           if (key == null && value == null) {
/*     */             break;
/*     */           }
/* 319 */           if (key != null) {
/* 320 */             StringTokenizer values = new StringTokenizer(value, ",");
/*     */             
/* 322 */             while (values.hasMoreTokens())
/* 323 */               headers.addHeader(key, values.nextToken().trim()); 
/*     */           } 
/* 325 */           i++;
/*     */         } 
/*     */         
/* 328 */         httpIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 333 */         byte[] bytes = readFully(httpIn);
/*     */         
/* 335 */         int length = (httpConnection.getContentLength() == -1) ? bytes.length : httpConnection.getContentLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 342 */         if (length == 0) {
/* 343 */           response = null;
/* 344 */           log.warning("SAAJ0014.p2p.content.zero");
/*     */         } else {
/* 346 */           ByteInputStream in = new ByteInputStream(bytes, length);
/* 347 */           response = this.messageFactory.createMessage(headers, (InputStream)in);
/*     */         }
/*     */       
/* 350 */       } catch (SOAPException ex) {
/* 351 */         throw ex;
/* 352 */       } catch (Exception ex) {
/* 353 */         log.log(Level.SEVERE, "SAAJ0010.p2p.cannot.read.resp", ex);
/* 354 */         throw new SOAPExceptionImpl("Unable to read response: " + ex.getMessage());
/*     */       } finally {
/*     */         
/* 357 */         if (httpIn != null)
/* 358 */           httpIn.close(); 
/* 359 */         httpConnection.disconnect();
/*     */       } 
/*     */     }
/* 362 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage get(Object endPoint) throws SOAPException {
/* 369 */     if (this.closed) {
/* 370 */       log.severe("SAAJ0011.p2p.get.already.closed.conn");
/* 371 */       throw new SOAPExceptionImpl("Connection is closed");
/*     */     } 
/* 373 */     Class<?> urlEndpointClass = null;
/*     */     
/*     */     try {
/* 376 */       urlEndpointClass = Class.forName("javax.xml.messaging.URLEndpoint");
/* 377 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/* 381 */     if (urlEndpointClass != null && 
/* 382 */       urlEndpointClass.isInstance(endPoint)) {
/* 383 */       String url = null;
/*     */       
/*     */       try {
/* 386 */         Method m = urlEndpointClass.getMethod("getURL", (Class[])null);
/* 387 */         url = (String)m.invoke(endPoint, (Object[])null);
/* 388 */       } catch (Exception ex) {
/* 389 */         log.severe("SAAJ0004.p2p.internal.err");
/* 390 */         throw new SOAPExceptionImpl("Internal error: " + ex.getMessage());
/*     */       } 
/*     */       
/*     */       try {
/* 394 */         endPoint = new URL(url);
/* 395 */       } catch (MalformedURLException mex) {
/* 396 */         log.severe("SAAJ0005.p2p.");
/* 397 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 402 */     if (endPoint instanceof String) {
/*     */       try {
/* 404 */         endPoint = new URL((String)endPoint);
/* 405 */       } catch (MalformedURLException mex) {
/* 406 */         log.severe("SAAJ0006.p2p.bad.URL");
/* 407 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 411 */     if (endPoint instanceof URL)
/*     */       try {
/* 413 */         SOAPMessage response = doGet((URL)endPoint);
/* 414 */         return response;
/* 415 */       } catch (Exception ex) {
/* 416 */         throw new SOAPExceptionImpl(ex);
/*     */       }  
/* 418 */     throw new SOAPExceptionImpl("Bad endPoint type " + endPoint);
/*     */   }
/*     */   
/*     */   SOAPMessage doGet(URL endPoint) throws SOAPException, IOException {
/* 422 */     boolean isFailure = false;
/*     */     
/* 424 */     URL url = null;
/* 425 */     HttpURLConnection httpConnection = null;
/*     */     
/* 427 */     int responseCode = 0;
/*     */     
/*     */     try {
/* 430 */       if (endPoint.getProtocol().equals("https")) {
/* 431 */         initHttps();
/*     */       }
/* 433 */       URI uri = new URI(endPoint.toString());
/* 434 */       String userInfo = uri.getRawUserInfo();
/*     */       
/* 436 */       url = endPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 443 */       if (!url.getProtocol().equalsIgnoreCase("http") && !url.getProtocol().equalsIgnoreCase("https")) {
/*     */         
/* 445 */         log.severe("SAAJ0052.p2p.protocol.mustbe.http.or.https");
/* 446 */         throw new IllegalArgumentException("Protocol " + url.getProtocol() + " not supported in URL " + url);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 452 */       httpConnection = createConnection(url);
/*     */       
/* 454 */       httpConnection.setRequestMethod("GET");
/*     */       
/* 456 */       httpConnection.setDoOutput(true);
/* 457 */       httpConnection.setDoInput(true);
/* 458 */       httpConnection.setUseCaches(false);
/* 459 */       HttpURLConnection.setFollowRedirects(true);
/*     */       
/* 461 */       httpConnection.connect();
/*     */ 
/*     */       
/*     */       try {
/* 465 */         responseCode = httpConnection.getResponseCode();
/*     */ 
/*     */         
/* 468 */         if (responseCode == 500) {
/* 469 */           isFailure = true;
/* 470 */         } else if (responseCode / 100 != 2) {
/* 471 */           log.log(Level.SEVERE, "SAAJ0008.p2p.bad.response", (Object[])new String[] { httpConnection.getResponseMessage() });
/*     */ 
/*     */           
/* 474 */           throw new SOAPExceptionImpl("Bad response: (" + responseCode + httpConnection.getResponseMessage());
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 480 */       catch (IOException e) {
/*     */         
/* 482 */         responseCode = httpConnection.getResponseCode();
/* 483 */         if (responseCode == 500) {
/* 484 */           isFailure = true;
/*     */         } else {
/* 486 */           throw e;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 491 */     } catch (SOAPException ex) {
/* 492 */       throw ex;
/* 493 */     } catch (Exception ex) {
/* 494 */       log.severe("SAAJ0012.p2p.get.failed");
/* 495 */       throw new SOAPExceptionImpl("Get failed", ex);
/*     */     } 
/*     */     
/* 498 */     SOAPMessage response = null;
/* 499 */     InputStream httpIn = null;
/* 500 */     if (responseCode == 200 || isFailure) {
/*     */       try {
/* 502 */         MimeHeaders headers = new MimeHeaders();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 508 */         int i = 1;
/*     */         
/*     */         while (true) {
/* 511 */           String key = httpConnection.getHeaderFieldKey(i);
/* 512 */           String value = httpConnection.getHeaderField(i);
/*     */           
/* 514 */           if (key == null && value == null) {
/*     */             break;
/*     */           }
/* 517 */           if (key != null) {
/* 518 */             StringTokenizer values = new StringTokenizer(value, ",");
/*     */             
/* 520 */             while (values.hasMoreTokens())
/* 521 */               headers.addHeader(key, values.nextToken().trim()); 
/*     */           } 
/* 523 */           i++;
/*     */         } 
/*     */         
/* 526 */         httpIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 536 */         if (httpIn == null || httpConnection.getContentLength() == 0 || httpIn.available() == 0) {
/*     */ 
/*     */           
/* 539 */           response = null;
/* 540 */           log.warning("SAAJ0014.p2p.content.zero");
/*     */         } else {
/* 542 */           response = this.messageFactory.createMessage(headers, httpIn);
/*     */         }
/*     */       
/* 545 */       } catch (SOAPException ex) {
/* 546 */         throw ex;
/* 547 */       } catch (Exception ex) {
/* 548 */         log.log(Level.SEVERE, "SAAJ0010.p2p.cannot.read.resp", ex);
/*     */ 
/*     */         
/* 551 */         throw new SOAPExceptionImpl("Unable to read response: " + ex.getMessage());
/*     */       } finally {
/*     */         
/* 554 */         if (httpIn != null)
/* 555 */           httpIn.close(); 
/* 556 */         httpConnection.disconnect();
/*     */       } 
/*     */     }
/* 559 */     return response;
/*     */   }
/*     */   
/*     */   private byte[] readFully(InputStream istream) throws IOException {
/* 563 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 564 */     byte[] buf = new byte[1024];
/* 565 */     int num = 0;
/*     */     
/* 567 */     while ((num = istream.read(buf)) != -1) {
/* 568 */       bout.write(buf, 0, num);
/*     */     }
/*     */     
/* 571 */     byte[] ret = bout.toByteArray();
/*     */     
/* 573 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 583 */     if (isIBMVM) {
/* 584 */       SSL_PKG = "com.ibm.net.ssl.internal.www.protocol";
/* 585 */       SSL_PROVIDER = "com.ibm.net.ssl.internal.ssl.Provider";
/*     */     } else {
/*     */       
/* 588 */       SSL_PKG = "com.sun.net.ssl.internal.www.protocol";
/* 589 */       SSL_PROVIDER = "com.sun.net.ssl.internal.ssl.Provider";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initHttps() {
/* 595 */     String pkgs = SAAJUtil.getSystemProperty("java.protocol.handler.pkgs");
/* 596 */     if (log.isLoggable(Level.FINE)) {
/* 597 */       log.log(Level.FINE, "SAAJ0053.p2p.providers", (Object[])new String[] { pkgs });
/*     */     }
/* 599 */     if (pkgs == null || pkgs.indexOf(SSL_PKG) < 0) {
/* 600 */       if (pkgs == null) {
/* 601 */         pkgs = SSL_PKG;
/*     */       } else {
/* 603 */         pkgs = pkgs + "|" + SSL_PKG;
/* 604 */       }  System.setProperty("java.protocol.handler.pkgs", pkgs);
/* 605 */       if (log.isLoggable(Level.FINE)) {
/* 606 */         log.log(Level.FINE, "SAAJ0054.p2p.set.providers", (Object[])new String[] { pkgs });
/*     */       }
/*     */       try {
/* 609 */         Class<?> c = Class.forName(SSL_PROVIDER);
/* 610 */         Provider p = (Provider)c.newInstance();
/* 611 */         Security.addProvider(p);
/* 612 */         if (log.isLoggable(Level.FINE)) {
/* 613 */           log.log(Level.FINE, "SAAJ0055.p2p.added.ssl.provider", (Object[])new String[] { SSL_PROVIDER });
/*     */         
/*     */         }
/*     */       }
/* 617 */       catch (Exception ex) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initAuthUserInfo(HttpURLConnection conn, String userInfo) {
/* 626 */     if (userInfo != null) {
/*     */       String user, password;
/* 628 */       int delimiter = userInfo.indexOf(':');
/* 629 */       if (delimiter == -1) {
/* 630 */         user = ParseUtil.decode(userInfo);
/* 631 */         password = null;
/*     */       } else {
/* 633 */         user = ParseUtil.decode(userInfo.substring(0, delimiter++));
/* 634 */         password = ParseUtil.decode(userInfo.substring(delimiter));
/*     */       } 
/*     */       
/* 637 */       String plain = user + ":";
/* 638 */       byte[] nameBytes = plain.getBytes();
/* 639 */       byte[] passwdBytes = password.getBytes();
/*     */ 
/*     */       
/* 642 */       byte[] concat = new byte[nameBytes.length + passwdBytes.length];
/*     */       
/* 644 */       System.arraycopy(nameBytes, 0, concat, 0, nameBytes.length);
/* 645 */       System.arraycopy(passwdBytes, 0, concat, nameBytes.length, passwdBytes.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 651 */       String auth = "Basic " + new String(Base64.encode(concat));
/* 652 */       conn.setRequestProperty("Authorization", auth);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void d(String s) {
/* 660 */     log.log(Level.SEVERE, "SAAJ0013.p2p.HttpSOAPConnection", (Object[])new String[] { s });
/*     */ 
/*     */     
/* 663 */     System.err.println("HttpSOAPConnection: " + s);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpURLConnection createConnection(URL endpoint) throws IOException {
/* 668 */     return (HttpURLConnection)endpoint.openConnection();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\client\p2p\HttpSOAPConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */