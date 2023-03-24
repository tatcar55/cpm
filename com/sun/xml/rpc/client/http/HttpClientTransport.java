/*     */ package com.sun.xml.rpc.client.http;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.rpc.client.ClientTransport;
/*     */ import com.sun.xml.rpc.client.ClientTransportException;
/*     */ import com.sun.xml.rpc.client.StubPropertyConstants;
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
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
/*     */ 
/*     */ public class HttpClientTransport
/*     */   implements ClientTransport, StubPropertyConstants
/*     */ {
/*     */   public static final String HTTP_SOAPACTION_PROPERTY = "http.soap.action";
/*  67 */   private static final SimpleTypeEncoder base64Encoder = XSDBase64BinaryEncoder.getInstance();
/*     */   
/*  69 */   private static String LAST_ENDPOINT = "";
/*     */   private static boolean redirect = true;
/*     */   private static final int START_REDIRECT_COUNT = 3;
/*  72 */   private static int redirectCount = 3; private static boolean DEBUG;
/*     */   
/*     */   public HttpClientTransport() {
/*  75 */     this(null);
/*     */   }
/*     */   private MessageFactory _messageFactory; private OutputStream _logStream;
/*     */   public HttpClientTransport(OutputStream logStream) {
/*     */     try {
/*  80 */       this._messageFactory = MessageFactory.newInstance();
/*  81 */       this._logStream = logStream;
/*  82 */     } catch (Exception e) {
/*  83 */       throw new ClientTransportException("http.client.cannotCreateMessageFactory");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invoke(String endpoint, SOAPMessageContext context) throws ClientTransportException {
/*     */     try {
/*  94 */       HttpURLConnection httpConnection = createHttpConnection(endpoint, context);
/*     */ 
/*     */       
/*  97 */       setupContextForInvoke(context);
/*     */       
/*  99 */       CookieJar cookieJar = sendCookieAsNeeded(context, httpConnection);
/*     */       
/* 101 */       moveHeadersFromContextToConnection(context, httpConnection);
/*     */       
/* 103 */       if (DEBUG) {
/* 104 */         checkMessageContentType(httpConnection.getRequestProperty("Content-Type"), false);
/*     */       }
/*     */       
/* 107 */       writeMessageToConnection(context, httpConnection);
/*     */       
/* 109 */       boolean isFailure = connectForResponse(httpConnection, context);
/* 110 */       int statusCode = httpConnection.getResponseCode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (checkForRedirect(statusCode)) {
/* 119 */         redirectRequest(httpConnection, context);
/*     */         
/*     */         return;
/*     */       } 
/* 123 */       MimeHeaders headers = collectResponseMimeHeaders(httpConnection);
/*     */       
/* 125 */       saveCookieAsNeeded(context, httpConnection, cookieJar);
/*     */       
/* 127 */       SOAPMessage response = null;
/*     */       
/*     */       try {
/* 130 */         response = readResponse(httpConnection, isFailure, headers);
/* 131 */       } catch (SOAPException e) {
/* 132 */         if (statusCode == 204 || (isFailure && statusCode != 500))
/*     */         {
/*     */           
/* 135 */           throw new ClientTransportException("http.status.code", new Object[] { new Integer(statusCode), httpConnection.getResponseMessage() });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 141 */         throw e;
/*     */       } 
/* 143 */       httpConnection = null;
/*     */       
/* 145 */       logResponseMessage(context, response);
/*     */       
/* 147 */       if (DEBUG) {
/* 148 */         checkMessageContentType(headers.getHeader("Content-Type")[0], true);
/*     */       }
/*     */       
/* 151 */       context.setMessage(response);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 156 */     catch (ClientTransportException e) {
/*     */       
/* 158 */       throw e;
/* 159 */     } catch (Exception e) {
/* 160 */       if (e instanceof Localizable) {
/* 161 */         throw new ClientTransportException("http.client.failed", (Localizable)e);
/*     */       }
/*     */ 
/*     */       
/* 165 */       throw new ClientTransportException("http.client.failed", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeOneWay(String endpoint, SOAPMessageContext context) {
/*     */     try {
/* 177 */       HttpURLConnection httpConnection = createHttpConnection(endpoint, context);
/*     */ 
/*     */       
/* 180 */       setupContextForInvoke(context);
/*     */       
/* 182 */       moveHeadersFromContextToConnection(context, httpConnection);
/*     */       
/* 184 */       writeMessageToConnection(context, httpConnection);
/*     */       
/* 186 */       forceMessageToBeSent(httpConnection, context);
/*     */     }
/* 188 */     catch (Exception e) {
/* 189 */       if (e instanceof Localizable) {
/* 190 */         throw new ClientTransportException("http.client.failed", (Localizable)e);
/*     */       }
/*     */ 
/*     */       
/* 194 */       throw new ClientTransportException("http.client.failed", new LocalizableExceptionAdapter(e));
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
/*     */   protected void logResponseMessage(SOAPMessageContext context, SOAPMessage response) throws IOException, SOAPException {
/* 206 */     if (this._logStream != null) {
/* 207 */       String s = "Response\n";
/* 208 */       this._logStream.write(s.getBytes());
/* 209 */       s = "Http Status Code: " + context.getProperty("com.sun.xml.rpc.client.http.HTTPStatusCode") + "\n\n";
/*     */ 
/*     */ 
/*     */       
/* 213 */       this._logStream.write(s.getBytes());
/* 214 */       Iterator<MimeHeader> iter = response.getMimeHeaders().getAllHeaders();
/*     */       
/* 216 */       while (iter.hasNext()) {
/*     */         
/* 218 */         MimeHeader header = iter.next();
/* 219 */         s = header.getName() + ": " + header.getValue() + "\n";
/* 220 */         this._logStream.write(s.getBytes());
/*     */       } 
/* 222 */       this._logStream.flush();
/* 223 */       response.writeTo(this._logStream);
/* 224 */       s = "******************\n\n";
/* 225 */       this._logStream.write(s.getBytes());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPMessage readResponse(HttpURLConnection httpConnection, boolean isFailure, MimeHeaders headers) throws IOException, SOAPException {
/* 235 */     InputStream contentIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     byte[] bytes = readFully(contentIn);
/* 241 */     int length = (httpConnection.getContentLength() == -1) ? bytes.length : httpConnection.getContentLength();
/*     */ 
/*     */ 
/*     */     
/* 245 */     ByteInputStream in = new ByteInputStream(bytes, length);
/*     */ 
/*     */     
/* 248 */     if (DEBUG && 
/* 249 */       httpConnection.getContentType().indexOf("text/html") >= 0) {
/* 250 */       System.out.println("");
/* 251 */       for (int i = 0; i < length; i++) {
/* 252 */         System.out.print((char)bytes[i]);
/*     */       }
/* 254 */       System.out.println("");
/*     */     } 
/*     */ 
/*     */     
/* 258 */     SOAPMessage response = this._messageFactory.createMessage(headers, (InputStream)in);
/*     */     
/* 260 */     contentIn.close();
/*     */     
/* 262 */     return response;
/*     */   }
/*     */   
/*     */   protected MimeHeaders collectResponseMimeHeaders(HttpURLConnection httpConnection) {
/* 266 */     MimeHeaders headers = new MimeHeaders();
/* 267 */     for (int i = 1;; i++) {
/* 268 */       String key = httpConnection.getHeaderFieldKey(i);
/* 269 */       if (key == null) {
/*     */         break;
/*     */       }
/* 272 */       String value = httpConnection.getHeaderField(i);
/*     */       try {
/* 274 */         headers.addHeader(key, value);
/* 275 */       } catch (IllegalArgumentException e) {}
/*     */     } 
/*     */ 
/*     */     
/* 279 */     return headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean connectForResponse(HttpURLConnection httpConnection, SOAPMessageContext context) throws IOException {
/* 287 */     httpConnection.connect();
/* 288 */     return checkResponseCode(httpConnection, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void forceMessageToBeSent(HttpURLConnection httpConnection, SOAPMessageContext context) throws IOException {
/*     */     try {
/* 297 */       httpConnection.connect();
/* 298 */       httpConnection.getInputStream();
/* 299 */       checkResponseCode(httpConnection, context);
/*     */     }
/* 301 */     catch (IOException io) {}
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
/*     */   protected boolean checkResponseCode(HttpURLConnection httpConnection, SOAPMessageContext context) throws IOException {
/* 314 */     boolean isFailure = false;
/*     */     
/*     */     try {
/* 317 */       int statusCode = httpConnection.getResponseCode();
/* 318 */       context.setProperty("com.sun.xml.rpc.client.http.HTTPStatusCode", Integer.toString(statusCode));
/*     */ 
/*     */       
/* 321 */       if (httpConnection.getResponseCode() == 500) {
/*     */         
/* 323 */         isFailure = true;
/*     */       } else {
/* 325 */         if (httpConnection.getResponseCode() == 401)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 330 */           throw new ClientTransportException("http.client.unauthorized", httpConnection.getResponseMessage());
/*     */         }
/*     */         
/* 333 */         if (httpConnection.getResponseCode() == 404)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 338 */           throw new ClientTransportException("http.not.found", httpConnection.getResponseMessage());
/*     */         }
/*     */         
/* 341 */         if (statusCode == 302 || statusCode == 301)
/*     */         
/*     */         { 
/* 344 */           isFailure = true;
/*     */           
/* 346 */           if (!redirect || redirectCount <= 0) {
/* 347 */             throw new ClientTransportException("http.status.code", new Object[] { new Integer(statusCode), getStatusMessage(httpConnection) });
/*     */           
/*     */           } }
/*     */         
/*     */         else
/*     */         
/* 353 */         { if (statusCode < 200 || (statusCode >= 303 && statusCode < 500))
/*     */           {
/* 355 */             throw new ClientTransportException("http.status.code", new Object[] { new Integer(statusCode), getStatusMessage(httpConnection) });
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 360 */           if (statusCode >= 500)
/* 361 */             isFailure = true;  } 
/*     */       } 
/* 363 */     } catch (IOException e) {
/*     */       
/* 365 */       if (httpConnection.getResponseCode() == 500) {
/*     */         
/* 367 */         isFailure = true;
/*     */       } else {
/* 369 */         throw e;
/*     */       } 
/*     */     } 
/*     */     
/* 373 */     return isFailure;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatusMessage(HttpURLConnection httpConnection) throws IOException {
/* 379 */     int statusCode = httpConnection.getResponseCode();
/* 380 */     String message = httpConnection.getResponseMessage();
/* 381 */     if (statusCode == 201 || (statusCode >= 300 && statusCode != 304 && statusCode < 400)) {
/*     */ 
/*     */ 
/*     */       
/* 385 */       String location = httpConnection.getHeaderField("Location");
/* 386 */       if (location != null)
/* 387 */         message = message + " - Location: " + location; 
/*     */     } 
/* 389 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logRequestMessage(SOAPMessageContext context) throws IOException, SOAPException {
/* 395 */     if (this._logStream != null) {
/* 396 */       String s = "******************\nRequest\n";
/* 397 */       this._logStream.write(s.getBytes());
/* 398 */       Iterator<MimeHeader> iter = context.getMessage().getMimeHeaders().getAllHeaders();
/*     */       
/* 400 */       while (iter.hasNext()) {
/*     */         
/* 402 */         MimeHeader header = iter.next();
/* 403 */         s = header.getName() + ": " + header.getValue() + "\n";
/* 404 */         this._logStream.write(s.getBytes());
/*     */       } 
/* 406 */       this._logStream.flush();
/* 407 */       context.getMessage().writeTo(this._logStream);
/* 408 */       s = "\n";
/* 409 */       this._logStream.write(s.getBytes());
/* 410 */       this._logStream.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeMessageToConnection(SOAPMessageContext context, HttpURLConnection httpConnection) throws IOException, SOAPException {
/* 418 */     OutputStream contentOut = httpConnection.getOutputStream();
/* 419 */     context.getMessage().writeTo(contentOut);
/* 420 */     contentOut.flush();
/* 421 */     contentOut.close();
/* 422 */     logRequestMessage(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void moveHeadersFromContextToConnection(SOAPMessageContext context, HttpURLConnection httpConnection) {
/* 428 */     Iterator<MimeHeader> iter = context.getMessage().getMimeHeaders().getAllHeaders();
/*     */     
/* 430 */     while (iter.hasNext()) {
/*     */       
/* 432 */       MimeHeader header = iter.next();
/* 433 */       httpConnection.setRequestProperty(header.getName(), header.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CookieJar sendCookieAsNeeded(SOAPMessageContext context, HttpURLConnection httpConnection) {
/* 442 */     Boolean shouldMaintainSessionProperty = (Boolean)context.getProperty("javax.xml.rpc.session.maintain");
/*     */     
/* 444 */     boolean shouldMaintainSession = (shouldMaintainSessionProperty == null) ? false : shouldMaintainSessionProperty.booleanValue();
/*     */ 
/*     */ 
/*     */     
/* 448 */     if (shouldMaintainSession) {
/* 449 */       CookieJar cookieJar = (CookieJar)context.getProperty("com.sun.xml.rpc.client.http.CookieJar");
/*     */ 
/*     */       
/* 452 */       if (cookieJar == null) {
/* 453 */         cookieJar = new CookieJar();
/*     */       }
/* 455 */       cookieJar.applyRelevantCookies(httpConnection);
/* 456 */       return cookieJar;
/*     */     } 
/* 458 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveCookieAsNeeded(SOAPMessageContext context, HttpURLConnection httpConnection, CookieJar cookieJar) {
/* 466 */     if (cookieJar != null) {
/* 467 */       cookieJar.recordAnyCookies(httpConnection);
/* 468 */       context.setProperty("com.sun.xml.rpc.client.http.CookieJar", cookieJar);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupContextForInvoke(SOAPMessageContext context) throws SOAPException, Exception {
/* 476 */     if (context.getMessage().saveRequired()) {
/* 477 */       context.getMessage().saveChanges();
/*     */     }
/* 479 */     String soapAction = (String)context.getProperty("http.soap.action");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 486 */     if (soapAction == null) {
/* 487 */       context.getMessage().getMimeHeaders().setHeader("SOAPAction", "\"\"");
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 492 */       context.getMessage().getMimeHeaders().setHeader("SOAPAction", "\"" + soapAction + "\"");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     String credentials = (String)context.getProperty("javax.xml.rpc.security.auth.username");
/* 499 */     if (credentials != null) {
/* 500 */       credentials = credentials + ":" + (String)context.getProperty("javax.xml.rpc.security.auth.password");
/*     */       
/* 502 */       credentials = base64Encoder.objectToString(credentials.getBytes(), null);
/*     */       
/* 504 */       context.getMessage().getMimeHeaders().setHeader("Authorization", "Basic " + credentials);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpURLConnection createHttpConnection(String endpoint, SOAPMessageContext context) throws IOException {
/* 515 */     boolean verification = false;
/*     */     
/* 517 */     String verificationProperty = (String)context.getProperty("com.sun.xml.rpc.client.http.HostnameVerificationProperty");
/*     */ 
/*     */     
/* 520 */     if (verificationProperty != null && 
/* 521 */       verificationProperty.equalsIgnoreCase("true")) {
/* 522 */       verification = true;
/*     */     }
/*     */ 
/*     */     
/* 526 */     String redirectProperty = (String)context.getProperty("com.sun.xml.rpc.client.http.RedirectRequestProperty");
/*     */ 
/*     */     
/* 529 */     if (redirectProperty != null && 
/* 530 */       redirectProperty.equalsIgnoreCase("false")) {
/* 531 */       redirect = false;
/*     */     }
/*     */     
/* 534 */     checkEndpoints(endpoint);
/*     */     
/* 536 */     HttpURLConnection httpConnection = createConnection(endpoint);
/*     */     
/* 538 */     if (!verification)
/*     */     {
/* 540 */       if (httpConnection instanceof HttpsURLConnection) {
/* 541 */         ((HttpsURLConnection)httpConnection).setHostnameVerifier(new HttpClientVerifier());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 548 */     httpConnection.setAllowUserInteraction(true);
/*     */     
/* 550 */     httpConnection.setDoOutput(true);
/* 551 */     httpConnection.setDoInput(true);
/*     */ 
/*     */     
/* 554 */     httpConnection.setRequestMethod("POST");
/*     */     
/* 556 */     httpConnection.setRequestProperty("Content-Type", "text/xml");
/*     */     
/* 558 */     return httpConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpURLConnection createConnection(String endpoint) throws IOException {
/* 563 */     return (HttpURLConnection)(new URL(endpoint)).openConnection();
/*     */   }
/*     */   
/*     */   private void redirectRequest(HttpURLConnection httpConnection, SOAPMessageContext context) {
/* 567 */     String redirectEndpoint = httpConnection.getHeaderField("Location");
/* 568 */     if (redirectEndpoint != null) {
/* 569 */       httpConnection.disconnect();
/* 570 */       invoke(redirectEndpoint, context);
/*     */     } else {
/* 572 */       System.out.println("redirection Failed");
/*     */     } 
/*     */   }
/*     */   private boolean checkForRedirect(int statusCode) {
/* 576 */     return ((statusCode == 301 || statusCode == 302) && redirect && redirectCount-- > 0);
/*     */   }
/*     */   
/*     */   private void checkEndpoints(String currentEndpoint) {
/* 580 */     if (!LAST_ENDPOINT.equalsIgnoreCase(currentEndpoint)) {
/* 581 */       redirectCount = 3;
/* 582 */       LAST_ENDPOINT = currentEndpoint;
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] readFully(InputStream istream) throws IOException {
/* 587 */     if (istream == null)
/* 588 */       return new byte[0]; 
/* 589 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 590 */     byte[] buf = new byte[1024];
/* 591 */     int num = 0;
/* 592 */     while ((num = istream.read(buf)) != -1) {
/* 593 */       bout.write(buf, 0, num);
/*     */     }
/* 595 */     byte[] ret = bout.toByteArray();
/* 596 */     return ret;
/*     */   }
/*     */   
/*     */   private static void checkMessageContentType(String contentType, boolean response) {
/* 600 */     if (contentType.indexOf("text/html") >= 0) {
/* 601 */       System.out.println("##### WARNING " + (response ? "RESPONSE" : "REQUEST") + " CONTENT TYPE INCLUDES 'text/html'");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 607 */     System.out.println("##### CHECKING " + (response ? "RESPONSE" : "REQUEST") + " CONTENT TYPE '" + contentType + "'");
/*     */ 
/*     */ 
/*     */     
/* 611 */     String negotiation = System.getProperty("com.sun.xml.rpc.client.ContentNegotiation", "none").intern();
/*     */ 
/*     */ 
/*     */     
/* 615 */     if (negotiation == "none") {
/*     */       
/* 617 */       if (contentType.indexOf("text/xml") < 0) {
/* 618 */         throw new RuntimeException("Invalid content type '" + contentType + "' in " + (response ? "response" : "request") + " with conneg set to '" + negotiation + "'.");
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 623 */     else if (negotiation == "optimistic") {
/*     */       
/* 625 */       if (contentType.indexOf("application/fastinfoset") < 0) {
/* 626 */         throw new RuntimeException("Invalid content type '" + contentType + "' in " + (response ? "response" : "request") + " with conneg set to '" + negotiation + "'.");
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 631 */     else if (negotiation == "pessimistic") {
/*     */       
/* 633 */       if (response && contentType.indexOf("application/fastinfoset") < 0)
/*     */       {
/* 635 */         throw new RuntimeException("Invalid content type '" + contentType + "' in " + (response ? "response" : "request") + " with conneg set to '" + negotiation + "'.");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class HttpClientVerifier
/*     */     implements HostnameVerifier
/*     */   {
/*     */     public boolean verify(String s, SSLSession sslSession) {
/* 646 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 655 */     String value = System.getProperty("debug", "false");
/* 656 */     DEBUG = (value.equals("on") || value.equals("true"));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\http\HttpClientTransport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */