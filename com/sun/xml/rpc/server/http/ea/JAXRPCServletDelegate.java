/*     */ package com.sun.xml.rpc.server.http.ea;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.server.http.Implementor;
/*     */ import com.sun.xml.rpc.server.http.JAXRPCServletException;
/*     */ import com.sun.xml.rpc.server.http.ServletDelegate;
/*     */ import com.sun.xml.rpc.server.http.ServletEndpointContextImpl;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.runtime.RuntimeEndpointInfo;
/*     */ import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.runtime.ServletSecondDelegate;
/*     */ import com.sun.xml.rpc.spi.runtime.SystemHandlerDelegate;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletInputStream;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.rpc.handler.MessageContext;
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
/*     */ public class JAXRPCServletDelegate
/*     */   implements ServletDelegate
/*     */ {
/*     */   private ServletConfig _servletConfig;
/*     */   private ServletContext _servletContext;
/*     */   private ImplementorFactory _implementorFactory;
/*     */   private WSDLPublisher _wsdlPublisher;
/*     */   private Localizer _localizer;
/*     */   private Map _localizerMap;
/*     */   private LocalizableMessageFactory _messageFactory;
/*     */   private static final String CONFIG_FILE_PROPERTY = "configuration.file";
/*     */   private static final String WSDL_QUERY_STRING = "WSDL";
/*     */   private static final String FAULT_STRING_MISSING_PORT = "Missing port information";
/*     */   private static final String FAULT_STRING_PORT_NOT_FOUND = "Port not found";
/*     */   private static final String FAULT_STRING_INTERNAL_SERVER_ERROR = "Internal Server Error";
/*     */   
/*     */   public void init(ServletConfig servletConfig) throws ServletException {
/*     */     try {
/*  75 */       this._servletConfig = servletConfig;
/*  76 */       this._servletContext = servletConfig.getServletContext();
/*  77 */       this._localizer = new Localizer();
/*  78 */       this._localizerMap = new HashMap<Object, Object>();
/*  79 */       this._localizerMap.put(this._localizer.getLocale(), this._localizer);
/*  80 */       this._messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
/*     */ 
/*     */       
/*  83 */       if (_logger.isLoggable(Level.INFO)) {
/*  84 */         _logger.info(this._localizer.localize(this._messageFactory.getMessage("info.servlet.initializing")));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  90 */       this._implementorFactory = new ImplementorFactory(servletConfig, getConfigFile(servletConfig));
/*     */ 
/*     */ 
/*     */       
/*  94 */       this._wsdlPublisher = new WSDLPublisher(servletConfig, getConfigFile(servletConfig));
/*     */     
/*     */     }
/*  97 */     catch (JAXRPCServletException e) {
/*  98 */       String message = this._localizer.localize((Localizable)e);
/*  99 */       throw new ServletException(message);
/* 100 */     } catch (Throwable e) {
/* 101 */       String message = this._localizer.localize(this._messageFactory.getMessage("error.servlet.caughtThrowable", new Object[] { e }));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       throw new ServletException(message);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected InputStream getConfigFile(ServletConfig servletConfig) {
/* 111 */     String configFilePath = servletConfig.getInitParameter("configuration.file");
/*     */     
/* 113 */     if (configFilePath == null) {
/* 114 */       throw new JAXRPCServletException("error.servlet.init.config.parameter.missing", new Object[] { "configuration.file" });
/*     */     }
/*     */ 
/*     */     
/* 118 */     InputStream configFile = this._servletContext.getResourceAsStream(configFilePath);
/*     */     
/* 120 */     if (configFile == null) {
/* 121 */       throw new JAXRPCServletException("error.servlet.init.config.fileNotFound", new Object[] { configFilePath });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 126 */     return configFile;
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 130 */     if (_logger.isLoggable(Level.INFO)) {
/* 131 */       _logger.info(this._localizer.localize(this._messageFactory.getMessage("info.servlet.destroying")));
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (this._implementorFactory != null) {
/* 136 */       this._implementorFactory.destroy();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
/*     */     try {
/* 143 */       MimeHeaders headers = getHeaders(req);
/* 144 */       ServletInputStream servletInputStream = req.getInputStream();
/*     */       
/* 146 */       byte[] bytes = readFully((InputStream)servletInputStream);
/* 147 */       int length = (req.getContentLength() == -1) ? bytes.length : req.getContentLength();
/*     */ 
/*     */ 
/*     */       
/* 151 */       ByteInputStream in = new ByteInputStream(bytes, length);
/*     */       
/* 153 */       SOAPMessageContext messageContext = new SOAPMessageContext();
/* 154 */       SOAPMessage message = messageContext.createMessage(headers, (InputStream)in);
/*     */       
/* 156 */       if (message == null) {
/* 157 */         if (_logger.isLoggable(Level.INFO)) {
/* 158 */           _logger.info(this._localizer.localize(this._messageFactory.getMessage("info.servlet.gotEmptyRequestMessage")));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 163 */         messageContext.writeInternalServerErrorResponse();
/*     */       } else {
/* 165 */         messageContext.setMessage(message);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 182 */         String pathInfo = req.getPathInfo();
/* 183 */         if (pathInfo != null && pathInfo.length() > 1) {
/* 184 */           String name = (pathInfo.charAt(0) == '/') ? pathInfo.substring(1) : pathInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 189 */           if (_logger.isLoggable(Level.FINEST)) {
/* 190 */             _logger.finest(this._localizer.localize(this._messageFactory.getMessage("trace.servlet.requestForPortNamed", name)));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 197 */           Implementor implementor = this._implementorFactory.getImplementorFor(name);
/*     */ 
/*     */           
/* 200 */           if (implementor == null) {
/* 201 */             _logger.severe(this._localizer.localize(this._messageFactory.getMessage("error.servlet.noImplementorForPort", name)));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 206 */             messageContext.writeSimpleErrorResponse(SOAPConstants.FAULT_CODE_SERVER, "Port not found(\"" + name + "\")");
/*     */           }
/*     */           else {
/*     */             
/* 210 */             if (_logger.isLoggable(Level.FINEST)) {
/* 211 */               _logger.finest(this._localizer.localize(this._messageFactory.getMessage("trace.servlet.handingRequestOverToImplementor", implementor.toString())));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 218 */             ServletEndpointContextImpl endpointContext = implementor.getContext();
/*     */ 
/*     */             
/*     */             try {
/* 222 */               endpointContext.setMessageContext((MessageContext)messageContext);
/* 223 */               endpointContext.setHttpServletRequest(req);
/*     */ 
/*     */               
/* 226 */               messageContext.setProperty("com.sun.xml.rpc.server.http.ServletContext", this._servletContext);
/*     */ 
/*     */               
/* 229 */               messageContext.setProperty("com.sun.xml.rpc.server.http.HttpServletRequest", req);
/*     */ 
/*     */               
/* 232 */               messageContext.setProperty("com.sun.xml.rpc.server.http.HttpServletResponse", resp);
/*     */ 
/*     */               
/* 235 */               messageContext.setProperty("com.sun.xml.rpc.server.http.Implementor", implementor);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 240 */               implementor.getTie().handle((SOAPMessageContext)messageContext);
/* 241 */             } catch (Exception e) {
/* 242 */               throw e;
/*     */             } finally {
/* 244 */               endpointContext.clear();
/*     */             } 
/*     */             
/* 247 */             if (_logger.isLoggable(Level.FINEST)) {
/* 248 */               _logger.finest(this._localizer.localize(this._messageFactory.getMessage("trace.servlet.gotResponseFromImplementor", implementor.toString())));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 254 */             this._implementorFactory.releaseImplementor(name, implementor);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 259 */           _logger.severe(this._localizer.localize(this._messageFactory.getMessage("error.servlet.noPortSpecified")));
/*     */ 
/*     */ 
/*     */           
/* 263 */           messageContext.writeSimpleErrorResponse(SOAPConstants.FAULT_CODE_SERVER, "Missing port information");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 269 */       SOAPMessage reply = messageContext.getMessage();
/*     */       
/* 271 */       if (reply.saveRequired()) {
/* 272 */         reply.saveChanges();
/*     */       }
/*     */       
/* 275 */       writeReply(resp, messageContext);
/* 276 */     } catch (JAXRPCExceptionBase e) {
/* 277 */       _logger.log(Level.SEVERE, this._localizer.localize((Localizable)e), (Throwable)e);
/* 278 */       resp.setStatus(500);
/*     */       try {
/* 280 */         SOAPMessageContext messageContext = new SOAPMessageContext();
/* 281 */         messageContext.writeSimpleErrorResponse(SOAPConstants.FAULT_CODE_SERVER, "Internal Server Error (" + this._localizer.localize((Localizable)e) + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 287 */         writeReply(resp, messageContext);
/* 288 */       } catch (Throwable e2) {
/* 289 */         _logger.log(Level.SEVERE, this._localizer.localize(this._messageFactory.getMessage("error.servlet.caughtThrowableWhileRecovering", new Object[] { e2 })), e2);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 297 */     catch (Throwable e) {
/* 298 */       if (e instanceof Localizable) {
/* 299 */         _logger.log(Level.SEVERE, this._localizer.localize((Localizable)e), e);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 304 */         _logger.log(Level.SEVERE, this._localizer.localize(this._messageFactory.getMessage("error.servlet.caughtThrowable", new Object[] { e })), e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 312 */       resp.setStatus(500);
/*     */       try {
/* 314 */         SOAPMessageContext messageContext = new SOAPMessageContext();
/* 315 */         messageContext.writeSimpleErrorResponse(SOAPConstants.FAULT_CODE_SERVER, "Internal Server Error (" + e.toString() + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 321 */         writeReply(resp, messageContext);
/* 322 */       } catch (Throwable e2) {
/* 323 */         _logger.log(Level.SEVERE, this._localizer.localize(this._messageFactory.getMessage("error.servlet.caughtThrowableWhileRecovering", new Object[] { e2 })), e2);
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
/*     */ 
/*     */   
/*     */   protected void writeReply(HttpServletResponse resp, SOAPMessageContext messageContext) throws SOAPException, IOException {
/* 338 */     SOAPMessage reply = messageContext.getMessage();
/*     */     
/* 340 */     if (messageContext.isFailure()) {
/* 341 */       if (_logger.isLoggable(Level.FINEST)) {
/* 342 */         _logger.finest(this._localizer.localize(this._messageFactory.getMessage("trace.servlet.writingFaultResponse")));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 347 */       resp.setStatus(500);
/*     */     } else {
/* 349 */       if (_logger.isLoggable(Level.FINEST)) {
/* 350 */         _logger.finest(this._localizer.localize(this._messageFactory.getMessage("trace.servlet.writingSuccessResponse")));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 355 */       resp.setStatus(200);
/*     */     } 
/*     */     
/* 358 */     ServletOutputStream servletOutputStream = resp.getOutputStream();
/* 359 */     String[] headers = reply.getMimeHeaders().getHeader("Content-Type");
/* 360 */     if (headers != null && headers.length > 0) {
/* 361 */       resp.setContentType(headers[0]);
/*     */     } else {
/* 363 */       resp.setContentType("text/xml");
/*     */     } 
/* 365 */     putHeaders(reply.getMimeHeaders(), resp);
/* 366 */     reply.writeTo((OutputStream)servletOutputStream);
/*     */     
/* 368 */     servletOutputStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 374 */     Localizer localizer = getLocalizerFor((ServletRequest)request);
/*     */ 
/*     */     
/*     */     try {
/* 378 */       if (request.getPathInfo() != null) {
/*     */         
/* 380 */         response.setContentType("text/html");
/* 381 */         response.setStatus(200);
/* 382 */         PrintWriter httpOut = response.getWriter();
/* 383 */         httpOut.println("<html>");
/* 384 */         httpOut.println("<head><title>");
/* 385 */         httpOut.println(localizer.localize(this._messageFactory.getMessage("html.nonRootPage.title")));
/*     */ 
/*     */         
/* 388 */         httpOut.println("</title></head><body>");
/* 389 */         httpOut.println(localizer.localize(this._messageFactory.getMessage("html.nonRootPage.body1")));
/*     */ 
/*     */ 
/*     */         
/* 393 */         String requestURI = request.getRequestURI();
/* 394 */         int i = requestURI.lastIndexOf(request.getPathInfo());
/* 395 */         if (i == -1) {
/* 396 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.nonRootPage.body2")));
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 401 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.nonRootPage.body3a")));
/*     */ 
/*     */ 
/*     */           
/* 405 */           httpOut.println(requestURI.substring(0, i));
/* 406 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.nonRootPage.body3b")));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 412 */         httpOut.println("</body></html>");
/* 413 */       } else if (request.getQueryString() != null && request.getQueryString().equals("WSDL")) {
/*     */ 
/*     */         
/* 416 */         if (this._wsdlPublisher.hasDocument()) {
/* 417 */           this._wsdlPublisher.publish(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "/", response);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 427 */           response.setContentType("text/html");
/* 428 */           response.setStatus(200);
/* 429 */           PrintWriter httpOut = response.getWriter();
/* 430 */           httpOut.println("<html>");
/* 431 */           httpOut.println("<head><title>");
/* 432 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.wsdlPage.title")));
/*     */ 
/*     */           
/* 435 */           httpOut.println("</title></head><body>");
/* 436 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.wsdlPage.noWsdl")));
/*     */ 
/*     */ 
/*     */           
/* 440 */           httpOut.println("</body></html>");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 458 */         response.setContentType("text/html");
/* 459 */         response.setStatus(200);
/* 460 */         PrintWriter httpOut = response.getWriter();
/* 461 */         httpOut.println("<html>");
/* 462 */         httpOut.println("<head><title>");
/* 463 */         httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.title")));
/*     */ 
/*     */         
/* 466 */         httpOut.println("</title></head><body>");
/* 467 */         httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.body1")));
/*     */ 
/*     */ 
/*     */         
/* 471 */         if (this._implementorFactory != null) {
/* 472 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.body2a")));
/*     */ 
/*     */ 
/*     */           
/* 476 */           Iterator<String> iterator = this._implementorFactory.names();
/* 477 */           if (!iterator.hasNext()) {
/* 478 */             httpOut.print("NONE");
/*     */           } else {
/* 480 */             boolean first = true;
/* 481 */             while (iterator.hasNext()) {
/* 482 */               String portName = iterator.next();
/* 483 */               if (!first) {
/* 484 */                 httpOut.print(", ");
/*     */               }
/* 486 */               httpOut.print('"');
/* 487 */               httpOut.print(portName);
/* 488 */               String portURI = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "/" + portName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 497 */               httpOut.print('"');
/* 498 */               httpOut.print(" (");
/* 499 */               httpOut.print(portURI);
/* 500 */               httpOut.print(')');
/* 501 */               first = false;
/*     */             } 
/*     */           } 
/* 504 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.body2b")));
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 509 */           if (this._wsdlPublisher.hasDocument()) {
/* 510 */             httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.body3a")));
/*     */ 
/*     */ 
/*     */             
/* 514 */             httpOut.println(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "?WSDL");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 522 */             httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.body3b")));
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 528 */           httpOut.println(localizer.localize(this._messageFactory.getMessage("html.rootPage.body4")));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 533 */         httpOut.println("</body></html>");
/*     */       } 
/* 535 */     } catch (IOException e) {
/* 536 */       _logger.log(Level.SEVERE, e.getMessage(), e);
/* 537 */       throw new ServletException(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Localizer getLocalizerFor(ServletRequest request) {
/* 542 */     Locale locale = request.getLocale();
/* 543 */     if (locale.equals(this._localizer.getLocale())) {
/* 544 */       return this._localizer;
/*     */     }
/*     */     
/* 547 */     synchronized (this._localizerMap) {
/* 548 */       Localizer localizer = (Localizer)this._localizerMap.get(locale);
/* 549 */       if (localizer == null) {
/* 550 */         localizer = new Localizer(locale);
/* 551 */         this._localizerMap.put(locale, localizer);
/*     */       } 
/* 553 */       return localizer;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static MimeHeaders getHeaders(HttpServletRequest req) {
/* 558 */     Enumeration<String> enums = req.getHeaderNames();
/* 559 */     MimeHeaders headers = new MimeHeaders();
/*     */     
/* 561 */     while (enums.hasMoreElements()) {
/* 562 */       String headerName = enums.nextElement();
/* 563 */       String headerValue = req.getHeader(headerName);
/* 564 */       headers.addHeader(headerName, headerValue);
/*     */     } 
/*     */     
/* 567 */     return headers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void putHeaders(MimeHeaders headers, HttpServletResponse res) {
/* 573 */     headers.removeHeader("Content-Type");
/* 574 */     headers.removeHeader("Content-Length");
/* 575 */     Iterator<MimeHeader> it = headers.getAllHeaders();
/* 576 */     while (it.hasNext()) {
/* 577 */       MimeHeader header = it.next();
/* 578 */       res.setHeader(header.getName(), header.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static byte[] readFully(InputStream istream) throws IOException {
/* 583 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 584 */     byte[] buf = new byte[1024];
/* 585 */     int num = 0;
/* 586 */     while ((num = istream.read(buf)) != -1) {
/* 587 */       bout.write(buf, 0, num);
/*     */     }
/* 589 */     byte[] ret = bout.toByteArray();
/* 590 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEndpointUrlPattern(RuntimeEndpointInfo info) {
/* 595 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecondDelegate(ServletSecondDelegate delegate) {
/* 600 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSystemHandlerDelegate(SystemHandlerDelegate systemHandlerDelegate) {
/* 605 */     throw new UnsupportedOperationException();
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
/* 624 */   private static final Logger _logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ea\JAXRPCServletDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */