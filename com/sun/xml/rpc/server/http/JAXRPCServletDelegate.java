/*     */ package com.sun.xml.rpc.server.http;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.runtime.ImplementorCache;
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
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
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
/*  73 */   private SOAPEncodingConstants soapEncodingConstants = null; private ServletConfig servletConfig; private ServletContext servletContext; private JAXRPCRuntimeInfo jaxrpcInfo; private Localizer defaultLocalizer; private LocalizableMessageFactory messageFactory;
/*     */   private ImplementorCache implementorCache;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  77 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */   private Map fixedUrlPatternEndpoints; private List pathUrlPatternEndpoints; private Map localizerMap; private WSDLPublisher publisher; private boolean publishWSDL; private boolean publishModel; private boolean publishStatusPage;
/*     */   
/*     */   public void init(ServletConfig servletConfig) throws ServletException {
/*  82 */     init(servletConfig, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(ServletConfig servletConfig, SOAPVersion ver) throws ServletException {
/*  87 */     init(ver);
/*  88 */     this.defaultLocalizer = new Localizer();
/*  89 */     this.localizerMap = new HashMap<Object, Object>();
/*  90 */     this.localizerMap.put(this.defaultLocalizer.getLocale(), this.defaultLocalizer);
/*  91 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
/*     */ 
/*     */     
/*  94 */     this.servletConfig = servletConfig;
/*  95 */     this.servletContext = servletConfig.getServletContext();
/*     */     
/*  97 */     if (logger.isLoggable(Level.INFO)) {
/*  98 */       logger.info(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.info.initialize")));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.fixedUrlPatternEndpoints = new HashMap<Object, Object>();
/* 104 */     this.pathUrlPatternEndpoints = new ArrayList();
/*     */     
/* 106 */     this.jaxrpcInfo = (JAXRPCRuntimeInfo)this.servletContext.getAttribute("com.sun.xml.rpc.server.http.info");
/*     */ 
/*     */     
/* 109 */     if (this.jaxrpcInfo == null) {
/* 110 */       warnMissingContextInformation();
/*     */     } else {
/* 112 */       Map<Object, Object> endpointsByName = new HashMap<Object, Object>();
/* 113 */       Iterator<RuntimeEndpointInfo> iter = this.jaxrpcInfo.getEndpoints().iterator();
/* 114 */       while (iter.hasNext()) {
/*     */         
/* 116 */         RuntimeEndpointInfo info = iter.next();
/* 117 */         if (endpointsByName.containsKey(info.getName())) {
/* 118 */           logger.warning(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.warning.duplicateEndpointName", info.getName())));
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 124 */         endpointsByName.put(info.getName(), info);
/* 125 */         registerEndpointUrlPattern(info);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 130 */     String publishWSDLParam = this.servletContext.getInitParameter("com.sun.xml.rpc.server.http.publishWSDL");
/*     */ 
/*     */     
/* 133 */     this.publishWSDL = (publishWSDLParam == null) ? true : Boolean.valueOf(publishWSDLParam).booleanValue();
/*     */ 
/*     */ 
/*     */     
/* 137 */     String publishModelParam = this.servletContext.getInitParameter("com.sun.xml.rpc.server.http.publishModel");
/*     */ 
/*     */     
/* 140 */     this.publishModel = (publishModelParam == null) ? true : Boolean.valueOf(publishModelParam).booleanValue();
/*     */ 
/*     */ 
/*     */     
/* 144 */     String publishStatusPageParam = this.servletContext.getInitParameter("com.sun.xml.rpc.server.http.publishStatusPage");
/*     */ 
/*     */     
/* 147 */     this.publishStatusPage = (publishStatusPageParam == null) ? true : Boolean.valueOf(publishStatusPageParam).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     this.implementorCache = (ImplementorCache)createImplementorCache();
/* 153 */     this.publisher = new WSDLPublisher(this.servletContext, this.jaxrpcInfo);
/*     */     
/* 155 */     if (this.secondDelegate != null)
/* 156 */       this.secondDelegate.postInit(servletConfig); 
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 160 */     this.implementorCache.destroy();
/* 161 */     if (logger.isLoggable(Level.INFO)) {
/* 162 */       logger.info(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.info.destroy")));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 171 */     if (this.secondDelegate != null) {
/* 172 */       this.secondDelegate.doGet(request, response);
/*     */     } else {
/* 174 */       doGetDefault(request, response);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doGetDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/*     */     try {
/* 183 */       MimeHeaders headers = getHeaders(request);
/* 184 */       Localizer localizer = getLocalizerFor((ServletRequest)request);
/*     */       
/* 186 */       if (checkForContent(headers)) {
/* 187 */         writeInvalidMethodType(localizer, response, "Invalid Method Type");
/*     */ 
/*     */ 
/*     */         
/* 191 */         if (logger.isLoggable(Level.INFO)) {
/* 192 */           logger.severe(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.html.method")));
/*     */ 
/*     */           
/* 195 */           logger.severe("Must use Http POST for the service request");
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 200 */       RuntimeEndpointInfo targetEndpoint = getEndpointFor(request);
/* 201 */       if (targetEndpoint != null && request.getQueryString() != null) {
/* 202 */         if (request.getQueryString().equals("WSDL")) {
/* 203 */           if (this.publishWSDL && targetEndpoint.getWSDLFileName() != null)
/*     */           {
/*     */             
/* 206 */             this.publisher.handle(targetEndpoint, this.fixedUrlPatternEndpoints, request, response);
/*     */           
/*     */           }
/*     */           else
/*     */           {
/*     */             
/* 212 */             writeNotFoundErrorPage(localizer, response, "Invalid request");
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 217 */         else if (request.getQueryString().equals("model")) {
/* 218 */           if (this.publishModel && targetEndpoint.getModelFileName() != null) {
/*     */             
/* 220 */             response.setContentType("application/x-gzip");
/* 221 */             InputStream istream = this.servletContext.getResourceAsStream(targetEndpoint.getModelFileName());
/*     */ 
/*     */             
/* 224 */             copyStream(istream, (OutputStream)response.getOutputStream());
/* 225 */             istream.close();
/*     */           } else {
/* 227 */             writeNotFoundErrorPage(localizer, response, "Invalid request");
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 233 */           writeNotFoundErrorPage(localizer, response, "Invalid request");
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 238 */       else if (request.getPathInfo() == null) {
/* 239 */         if (this.publishStatusPage) {
/*     */           
/* 241 */           response.setContentType("text/html");
/* 242 */           PrintWriter out = response.getWriter();
/* 243 */           out.println("<html>");
/* 244 */           out.println("<head><title>");
/*     */           
/* 246 */           out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.title")));
/*     */ 
/*     */           
/* 249 */           out.println("</title></head>");
/* 250 */           out.println("<body>");
/*     */           
/* 252 */           out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.title2")));
/*     */ 
/*     */           
/* 255 */           if (this.jaxrpcInfo == null) {
/*     */             
/* 257 */             out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.noInfoAvailable")));
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 262 */             out.println("<table width='100%' border='1'>");
/* 263 */             out.println("<tr>");
/* 264 */             out.println("<td>");
/*     */             
/* 266 */             out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.columnHeader.portName")));
/*     */ 
/*     */ 
/*     */             
/* 270 */             out.println("</td>");
/* 271 */             out.println("<td>");
/*     */             
/* 273 */             out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.columnHeader.status")));
/*     */ 
/*     */ 
/*     */             
/* 277 */             out.println("</td>");
/* 278 */             out.println("<td>");
/*     */             
/* 280 */             out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.columnHeader.information")));
/*     */ 
/*     */ 
/*     */             
/* 284 */             out.println("</td>");
/* 285 */             out.println("</tr>");
/* 286 */             String baseAddress = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 294 */             Iterator<RuntimeEndpointInfo> iter = this.jaxrpcInfo.getEndpoints().iterator();
/*     */             
/* 296 */             while (iter.hasNext()) {
/*     */               
/* 298 */               RuntimeEndpointInfo info = iter.next();
/*     */               
/* 300 */               String endpointAddress = baseAddress + getValidPathForEndpoint(info);
/*     */               
/* 302 */               out.println("<tr>");
/* 303 */               out.println("<td>" + info.getName() + "</td>");
/* 304 */               out.println("<td>");
/* 305 */               if (info.isDeployed()) {
/*     */                 
/* 307 */                 out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.status.active")));
/*     */               
/*     */               }
/*     */               else {
/*     */ 
/*     */                 
/* 313 */                 out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.status.error")));
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 318 */               out.println("</td>");
/* 319 */               out.println("<td>");
/* 320 */               out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.information.table", new Object[] { endpointAddress, info.getPortName(), info.getRemoteInterface().getName(), info.getImplementationClass().getName() })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 332 */               out.println("</td>");
/* 333 */               out.println("</tr>");
/*     */             } 
/* 335 */             out.println("</table>");
/*     */           } 
/* 337 */           out.println("</body>");
/* 338 */           out.println("</html>");
/*     */         } else {
/* 340 */           writeNotFoundErrorPage(localizer, response, "Invalid request");
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 346 */       else if (this.publishStatusPage) {
/* 347 */         response.setContentType("text/html");
/* 348 */         PrintWriter out = response.getWriter();
/* 349 */         out.println("<html>");
/* 350 */         out.println("<head><title>");
/*     */         
/* 352 */         out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.title")));
/*     */ 
/*     */         
/* 355 */         out.println("</title></head>");
/* 356 */         out.println("<body>");
/* 357 */         out.println("</body>");
/* 358 */         out.println("</html>");
/*     */       } else {
/* 360 */         writeNotFoundErrorPage(localizer, response, "Invalid request");
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 366 */     catch (Exception e) {
/* 367 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 368 */       throw new ServletException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/*     */     try {
/* 378 */       MimeHeaders headers = getHeaders(request);
/* 379 */       SOAPMessageContext messageContext = new SOAPMessageContext();
/*     */       
/* 381 */       if (!checkContentType(headers)) {
/* 382 */         writeInvalidContentType(response, headers);
/*     */         
/*     */         return;
/*     */       } 
/* 386 */       SOAPMessage message = getSOAPMessageFromRequest(request, headers, messageContext);
/*     */ 
/*     */       
/* 389 */       if (message == null) {
/* 390 */         if (logger.isLoggable(Level.INFO)) {
/* 391 */           logger.info(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.info.emptyRequestMessage")));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 396 */         messageContext.writeSimpleErrorResponse(this.soapEncodingConstants.getFaultCodeClient(), this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.info.emptyRequestMessage")));
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 404 */         messageContext.setMessage(message);
/* 405 */         RuntimeEndpointInfo targetEndpoint = getEndpointFor(request);
/*     */         
/* 407 */         if (targetEndpoint != null) {
/* 408 */           if (logger.isLoggable(Level.FINEST)) {
/* 409 */             logger.finest(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.trace.gotRequestForEndpoint", targetEndpoint.getName())));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 416 */           Implementor implementor = (Implementor)this.implementorCache.getImplementorFor(targetEndpoint);
/*     */ 
/*     */ 
/*     */           
/* 420 */           if (implementor == null) {
/* 421 */             logger.severe(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.error.noImplementorForEndpoint", targetEndpoint.getName())));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 426 */             messageContext.writeSimpleErrorResponse(this.soapEncodingConstants.getFaultCodeServer(), this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.faultstring.portNotFound", targetEndpoint.getName())));
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 434 */             if (logger.isLoggable(Level.FINEST)) {
/* 435 */               logger.finest(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.trace.invokingImplementor", implementor.toString())));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 442 */             ServletEndpointContextImpl endpointContext = implementor.getContext();
/*     */             
/* 444 */             SOAPMessageContext clientContext = null;
/*     */             
/*     */             try {
/* 447 */               endpointContext.setMessageContext((MessageContext)messageContext);
/* 448 */               endpointContext.setHttpServletRequest(request);
/*     */ 
/*     */               
/* 451 */               messageContext.setProperty("com.sun.xml.rpc.server.http.ServletContext", this.servletContext);
/*     */ 
/*     */               
/* 454 */               messageContext.setProperty("com.sun.xml.rpc.server.http.HttpServletRequest", request);
/*     */ 
/*     */               
/* 457 */               messageContext.setProperty("com.sun.xml.rpc.server.http.HttpServletResponse", response);
/*     */ 
/*     */               
/* 460 */               messageContext.setProperty("com.sun.xml.rpc.server.http.Implementor", implementor);
/*     */ 
/*     */ 
/*     */               
/* 464 */               if (this.systemHandlerDelegate == null) {
/* 465 */                 implementor.getTie().handle((SOAPMessageContext)messageContext);
/* 466 */               } else if (this.systemHandlerDelegate.processRequest((SOAPMessageContext)messageContext)) {
/* 467 */                 implementor.getTie().handle((SOAPMessageContext)messageContext);
/* 468 */                 this.systemHandlerDelegate.processResponse((SOAPMessageContext)messageContext);
/*     */               } 
/* 470 */             } catch (Exception e) {
/* 471 */               throw e;
/*     */             } finally {
/* 473 */               endpointContext.clear();
/*     */             } 
/*     */             
/* 476 */             this.implementorCache.releaseImplementor(targetEndpoint, implementor);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 481 */           logger.severe(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.error.noEndpointSpecified")));
/*     */ 
/*     */ 
/*     */           
/* 485 */           messageContext.writeSimpleErrorResponse(this.soapEncodingConstants.getFaultCodeClient(), this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.faultstring.missingPort")));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 493 */       SOAPMessage reply = messageContext.getMessage();
/*     */       
/* 495 */       if (reply.saveRequired()) {
/* 496 */         reply.saveChanges();
/*     */       }
/*     */       
/* 499 */       writeReply(response, messageContext);
/* 500 */     } catch (JAXRPCExceptionBase e) {
/* 501 */       logger.log(Level.SEVERE, this.defaultLocalizer.localize((Localizable)e), (Throwable)e);
/* 502 */       response.setStatus(500);
/*     */       try {
/* 504 */         SOAPMessageContext messageContext = new SOAPMessageContext();
/* 505 */         messageContext.writeSimpleErrorResponse(this.soapEncodingConstants.getFaultCodeServer(), this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.faultstring.internalServerError", this.defaultLocalizer.localize((Localizable)e))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 511 */         writeReply(response, messageContext);
/* 512 */       } catch (Throwable e2) {
/* 513 */         logger.log(Level.SEVERE, "caught throwable while recovering", e2);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 518 */     catch (Throwable e) {
/* 519 */       if (e instanceof Localizable) {
/* 520 */         logger.log(Level.SEVERE, this.defaultLocalizer.localize((Localizable)e), e);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 525 */         logger.log(Level.SEVERE, "caught throwable", e);
/*     */       } 
/*     */       
/* 528 */       response.setStatus(500);
/*     */       try {
/* 530 */         SOAPMessageContext messageContext = new SOAPMessageContext();
/* 531 */         messageContext.writeSimpleErrorResponse(this.soapEncodingConstants.getFaultCodeServer(), this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.faultstring.missingPort")));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 536 */         writeReply(response, messageContext);
/* 537 */       } catch (Throwable e2) {
/* 538 */         logger.log(Level.SEVERE, "caught throwable while recovering", e2);
/*     */         return;
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
/*     */   protected void writeReply(HttpServletResponse resp, SOAPMessageContext messageContext) throws SOAPException, IOException {
/* 553 */     if (checkMessageContextProperty(messageContext, "com.sun.xml.rpc.server.OneWayOperation")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 558 */     SOAPMessage reply = messageContext.getMessage();
/* 559 */     int statusCode = 0;
/* 560 */     if (messageContext.isFailure()) {
/* 561 */       if (logger.isLoggable(Level.FINEST)) {
/* 562 */         logger.finest(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.trace.writingFaultResponse")));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 568 */       if (checkMessageContextProperty(messageContext, "com.sun.xml.rpc.server.http.ClientBadRequest")) {
/*     */         
/* 570 */         resp.setStatus(400);
/*     */         
/* 572 */         setContentTypeAndFlush(resp);
/*     */         return;
/*     */       } 
/* 575 */       resp.setStatus(500);
/*     */     } else {
/*     */       
/* 578 */       if (logger.isLoggable(Level.FINEST)) {
/* 579 */         logger.finest(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.trace.writingSuccessResponse")));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 584 */       resp.setStatus(200);
/*     */     } 
/*     */     
/* 587 */     ServletOutputStream servletOutputStream = resp.getOutputStream();
/* 588 */     String[] headers = reply.getMimeHeaders().getHeader("Content-Type");
/* 589 */     if (headers != null && headers.length > 0) {
/* 590 */       resp.setContentType(headers[0]);
/*     */     } else {
/* 592 */       resp.setContentType("text/xml");
/*     */     } 
/* 594 */     putHeaders(reply.getMimeHeaders(), resp);
/*     */     
/* 596 */     reply.writeTo((OutputStream)servletOutputStream);
/* 597 */     servletOutputStream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeNotFoundErrorPage(Localizer localizer, HttpServletResponse response, String message) throws IOException {
/* 605 */     response.setStatus(404);
/* 606 */     response.setContentType("text/html");
/* 607 */     PrintWriter out = response.getWriter();
/* 608 */     out.println("<html>");
/* 609 */     out.println("<head><title>");
/* 610 */     out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.title")));
/*     */ 
/*     */     
/* 613 */     out.println("</title></head>");
/* 614 */     out.println("<body>");
/* 615 */     out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.notFound", message)));
/*     */ 
/*     */     
/* 618 */     out.println("</body>");
/* 619 */     out.println("</html>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeInvalidContentType(HttpServletResponse response, MimeHeaders headers) throws SOAPException, IOException {
/* 626 */     response.setStatus(415);
/* 627 */     String[] contentTypes = headers.getHeader("Content-Type");
/* 628 */     if (contentTypes != null && contentTypes.length >= 1) {
/* 629 */       response.setHeader("ContentType-Received", contentTypes[0]);
/*     */     }
/*     */     
/* 632 */     setContentTypeAndFlush(response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setContentTypeAndFlush(HttpServletResponse response) throws IOException {
/* 640 */     response.setContentType("text/xml");
/* 641 */     response.flushBuffer();
/* 642 */     response.getWriter().close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeInvalidMethodType(Localizer localizer, HttpServletResponse response, String message) throws IOException {
/* 650 */     response.setStatus(405);
/* 651 */     response.setContentType("text/html");
/* 652 */     PrintWriter out = response.getWriter();
/* 653 */     out.println("<html>");
/* 654 */     out.println("<head><title>");
/* 655 */     out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.title")));
/*     */ 
/*     */     
/* 658 */     out.println("</title></head>");
/* 659 */     out.println("<body>");
/* 660 */     out.println(localizer.localize(this.messageFactory.getMessage("servlet.html.method", message)));
/*     */ 
/*     */     
/* 663 */     out.println("</body>");
/* 664 */     out.println("</html>");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void warnMissingContextInformation() {
/* 669 */     if (this.secondDelegate != null) {
/* 670 */       this.secondDelegate.warnMissingContextInformation();
/*     */     } else {
/* 672 */       logger.warning(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.warning.missingContextInformation")));
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
/*     */   protected ImplementorCache createImplementorCache() {
/* 685 */     if (this.secondDelegate != null) {
/* 686 */       return this.secondDelegate.createImplementorCache(this.servletConfig);
/*     */     }
/* 688 */     return new ImplementorCache(this.servletConfig);
/*     */   }
/*     */   
/*     */   protected static MimeHeaders getHeaders(HttpServletRequest req) {
/* 692 */     Enumeration<String> enums = req.getHeaderNames();
/* 693 */     MimeHeaders headers = new MimeHeaders();
/*     */     
/* 695 */     while (enums.hasMoreElements()) {
/* 696 */       String headerName = enums.nextElement();
/* 697 */       String headerValue = req.getHeader(headerName);
/* 698 */       headers.addHeader(headerName, headerValue);
/*     */     } 
/*     */     
/* 701 */     return headers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void putHeaders(MimeHeaders headers, HttpServletResponse res) {
/* 707 */     headers.removeHeader("Content-Type");
/* 708 */     headers.removeHeader("Content-Length");
/* 709 */     Iterator<MimeHeader> it = headers.getAllHeaders();
/* 710 */     while (it.hasNext()) {
/* 711 */       MimeHeader header = it.next();
/* 712 */       res.setHeader(header.getName(), header.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static byte[] readFully(InputStream istream) throws IOException {
/* 717 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 718 */     byte[] buf = new byte[1024];
/* 719 */     int num = 0;
/*     */     
/* 721 */     if (istream != null) {
/* 722 */       while ((num = istream.read(buf)) != -1) {
/* 723 */         bout.write(buf, 0, num);
/*     */       }
/*     */     }
/* 726 */     byte[] ret = bout.toByteArray();
/* 727 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEndpointUrlPattern(RuntimeEndpointInfo info) {
/* 732 */     String urlPattern = ((RuntimeEndpointInfo)info).getUrlPattern();
/* 733 */     if (urlPattern.indexOf("*.") != -1) {
/*     */       
/* 735 */       logger.warning(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.warning.ignoringImplicitUrlPattern", ((RuntimeEndpointInfo)info).getName())));
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 740 */     else if (urlPattern.endsWith("/*")) {
/* 741 */       this.pathUrlPatternEndpoints.add(info);
/*     */     }
/* 743 */     else if (this.fixedUrlPatternEndpoints.containsKey(urlPattern)) {
/* 744 */       logger.warning(this.defaultLocalizer.localize(this.messageFactory.getMessage("servlet.warning.duplicateEndpointUrlPattern", ((RuntimeEndpointInfo)info).getName())));
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 750 */       this.fixedUrlPatternEndpoints.put(urlPattern, info);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getValidPathForEndpoint(RuntimeEndpointInfo info) {
/* 756 */     String s = info.getUrlPattern();
/* 757 */     if (s.endsWith("/*")) {
/* 758 */       return s.substring(0, s.length() - 2);
/*     */     }
/* 760 */     return s;
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
/*     */   protected RuntimeEndpointInfo getEndpointFor(HttpServletRequest request) {
/* 775 */     String path = request.getRequestURI().substring(request.getContextPath().length());
/*     */ 
/*     */     
/* 778 */     RuntimeEndpointInfo result = (RuntimeEndpointInfo)this.fixedUrlPatternEndpoints.get(path);
/*     */     
/* 780 */     if (result == null) {
/* 781 */       Iterator<RuntimeEndpointInfo> iter = this.pathUrlPatternEndpoints.iterator();
/* 782 */       while (iter.hasNext()) {
/*     */         
/* 784 */         RuntimeEndpointInfo candidate = iter.next();
/*     */         
/* 786 */         if (candidate.getUrlPattern().startsWith(path)) {
/* 787 */           result = candidate;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 793 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPMessage getSOAPMessageFromRequest(HttpServletRequest request, MimeHeaders headers, SOAPMessageContext messageContext) throws IOException {
/* 801 */     SOAPMessage message = null;
/*     */     
/* 803 */     ServletInputStream servletInputStream = request.getInputStream();
/*     */     
/* 805 */     byte[] bytes = readFully((InputStream)servletInputStream);
/*     */     
/* 807 */     int length = (request.getContentLength() == -1) ? bytes.length : request.getContentLength();
/*     */ 
/*     */ 
/*     */     
/* 811 */     ByteInputStream in = new ByteInputStream(bytes, length);
/* 812 */     message = messageContext.createMessage(headers, (InputStream)in);
/*     */     
/* 814 */     return message;
/*     */   }
/*     */   
/*     */   protected boolean checkContentType(MimeHeaders headers) {
/* 818 */     String[] contentTypes = headers.getHeader("Content-Type");
/* 819 */     if (contentTypes != null && contentTypes.length >= 1) {
/* 820 */       String contentType = contentTypes[0];
/* 821 */       if (contentType.indexOf("text/xml") != -1 || contentType.indexOf("application/fastinfoset") != -1)
/*     */       {
/*     */         
/* 824 */         return true;
/*     */       }
/*     */     } 
/* 827 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean checkContentLength(MimeHeaders headers) {
/* 831 */     String[] contentLength = headers.getHeader("Content-Length");
/* 832 */     if (contentLength != null && contentLength.length > 0) {
/* 833 */       int length = (new Integer(contentLength[0])).intValue();
/* 834 */       if (length > 0) {
/* 835 */         return true;
/*     */       }
/*     */     } 
/* 838 */     return false;
/*     */   }
/*     */   
/*     */   boolean checkForContent(MimeHeaders headers) {
/* 842 */     if (checkContentType(headers) && 
/* 843 */       checkContentLength(headers)) {
/* 844 */       return true;
/*     */     }
/* 846 */     return false;
/*     */   }
/*     */   
/*     */   protected Localizer getLocalizerFor(ServletRequest request) {
/* 850 */     Locale locale = request.getLocale();
/* 851 */     if (locale.equals(this.defaultLocalizer.getLocale())) {
/* 852 */       return this.defaultLocalizer;
/*     */     }
/*     */     
/* 855 */     synchronized (this.localizerMap) {
/* 856 */       Localizer localizer = (Localizer)this.localizerMap.get(locale);
/* 857 */       if (localizer == null) {
/* 858 */         localizer = new Localizer(locale);
/* 859 */         this.localizerMap.put(locale, localizer);
/*     */       } 
/* 861 */       return localizer;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void copyStream(InputStream istream, OutputStream ostream) throws IOException {
/* 867 */     byte[] buf = new byte[1024];
/* 868 */     int num = 0;
/* 869 */     while ((num = istream.read(buf)) != -1) {
/* 870 */       ostream.write(buf, 0, num);
/*     */     }
/* 872 */     ostream.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkMessageContextProperty(SOAPMessageContext messageContext, String property) {
/* 879 */     String prop = (String)messageContext.getProperty(property);
/* 880 */     if (prop != null && 
/* 881 */       prop.equalsIgnoreCase("true")) {
/* 882 */       return true;
/*     */     }
/* 884 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setMessageContextProperty(SOAPMessageContext messageContext, String property) {
/* 890 */     messageContext.setProperty(property, "true");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecondDelegate(ServletSecondDelegate secondDelegate) {
/* 895 */     this.secondDelegate = secondDelegate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSystemHandlerDelegate(SystemHandlerDelegate systemHandlerDelegate) {
/* 900 */     this.systemHandlerDelegate = systemHandlerDelegate;
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
/* 917 */   private static final Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*     */ 
/*     */ 
/*     */   
/* 921 */   private ServletSecondDelegate secondDelegate = null;
/*     */ 
/*     */   
/* 924 */   private SystemHandlerDelegate systemHandlerDelegate = null;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\JAXRPCServletDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */