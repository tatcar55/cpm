/*     */ package com.sun.xml.ws.transport.http.servlet;
/*     */ 
/*     */ import com.sun.istack.localization.Localizable;
/*     */ import com.sun.istack.localization.Localizer;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSServletDelegate
/*     */ {
/*     */   public final List<ServletAdapter> adapters;
/*  84 */   private final Map<String, ServletAdapter> fixedUrlPatternEndpoints = new HashMap<String, ServletAdapter>();
/*  85 */   private final List<ServletAdapter> pathUrlPatternEndpoints = new ArrayList<ServletAdapter>();
/*  86 */   private final Map<Locale, Localizer> localizerMap = new HashMap<Locale, Localizer>();
/*  87 */   private final JAXWSRIServletProbeProvider probe = new JAXWSRIServletProbeProvider();
/*     */   
/*     */   public WSServletDelegate(List<ServletAdapter> adapters, ServletContext context) {
/*  90 */     this.adapters = adapters;
/*     */     
/*  92 */     for (ServletAdapter info : adapters) {
/*  93 */       registerEndpointUrlPattern(info);
/*     */     }
/*     */     
/*  96 */     this.localizerMap.put(defaultLocalizer.getLocale(), defaultLocalizer);
/*     */     
/*  98 */     if (logger.isLoggable(Level.INFO)) {
/*  99 */       logger.info(WsservletMessages.SERVLET_INFO_INITIALIZE());
/*     */     }
/*     */ 
/*     */     
/* 103 */     String publishStatusPageParam = context.getInitParameter("com.sun.xml.ws.server.http.publishStatusPage");
/*     */     
/* 105 */     if (publishStatusPageParam != null) {
/* 106 */       HttpAdapter.publishStatusPage = Boolean.parseBoolean(publishStatusPageParam);
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 111 */     if (logger.isLoggable(Level.INFO)) {
/* 112 */       logger.info(WsservletMessages.SERVLET_INFO_DESTROY());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doHead(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
/*     */     try {
/* 120 */       ServletAdapter target = getTarget(request);
/* 121 */       if (target != null) {
/* 122 */         if (logger.isLoggable(Level.FINEST)) {
/* 123 */           logger.finest(WsservletMessages.SERVLET_TRACE_GOT_REQUEST_FOR_ENDPOINT(target.name));
/*     */         }
/*     */         
/* 126 */         target.handle(context, request, response);
/*     */       } else {
/* 128 */         response.setStatus(404);
/*     */       } 
/* 130 */     } catch (JAXWSExceptionBase e) {
/* 131 */       logger.log(Level.SEVERE, defaultLocalizer.localize((Localizable)e), (Throwable)e);
/* 132 */       response.setStatus(500);
/* 133 */     } catch (Throwable e) {
/* 134 */       if (e instanceof Localizable) {
/* 135 */         logger.log(Level.SEVERE, defaultLocalizer.localize((Localizable)e), e);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 140 */         logger.log(Level.SEVERE, "caught throwable", e);
/*     */       } 
/*     */       
/* 143 */       response.setStatus(500);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
/*     */     try {
/* 152 */       ServletAdapter target = getTarget(request);
/* 153 */       if (target != null) {
/* 154 */         if (logger.isLoggable(Level.FINEST)) {
/* 155 */           logger.finest(WsservletMessages.SERVLET_TRACE_GOT_REQUEST_FOR_ENDPOINT(target.name));
/*     */         }
/*     */         
/* 158 */         final String path = request.getContextPath() + target.getValidPath();
/* 159 */         this.probe.startedEvent(path);
/*     */         
/* 161 */         target.invokeAsync(context, request, response, new HttpAdapter.CompletionCallback()
/*     */             {
/*     */               public void onCompletion() {
/* 164 */                 WSServletDelegate.this.probe.endedEvent(path);
/*     */               }
/*     */             });
/*     */       } else {
/* 168 */         Localizer localizer = getLocalizerFor((ServletRequest)request);
/* 169 */         writeNotFoundErrorPage(localizer, response, "Invalid Request");
/*     */       } 
/* 171 */     } catch (JAXWSExceptionBase e) {
/* 172 */       logger.log(Level.SEVERE, defaultLocalizer.localize((Localizable)e), (Throwable)e);
/* 173 */       response.setStatus(500);
/* 174 */     } catch (Throwable e) {
/* 175 */       if (e instanceof Localizable) {
/* 176 */         logger.log(Level.SEVERE, defaultLocalizer.localize((Localizable)e), e);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 181 */         logger.log(Level.SEVERE, "caught throwable", e);
/*     */       } 
/*     */       
/* 184 */       response.setStatus(500);
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
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
/* 197 */     doGet(request, response, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPut(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
/*     */     try {
/* 207 */       ServletAdapter target = getTarget(request);
/* 208 */       if (target != null) {
/* 209 */         if (logger.isLoggable(Level.FINEST)) {
/* 210 */           logger.finest(WsservletMessages.SERVLET_TRACE_GOT_REQUEST_FOR_ENDPOINT(target.name));
/*     */         }
/*     */       } else {
/*     */         
/* 214 */         Localizer localizer = getLocalizerFor((ServletRequest)request);
/* 215 */         writeNotFoundErrorPage(localizer, response, "Invalid request");
/*     */         return;
/*     */       } 
/* 218 */       WSBinding wSBinding = target.getEndpoint().getBinding();
/* 219 */       if (wSBinding instanceof javax.xml.ws.http.HTTPBinding) {
/* 220 */         target.handle(context, request, response);
/*     */       } else {
/* 222 */         response.setStatus(405);
/*     */       } 
/* 224 */     } catch (JAXWSExceptionBase e) {
/* 225 */       logger.log(Level.SEVERE, defaultLocalizer.localize((Localizable)e), (Throwable)e);
/* 226 */       response.setStatus(500);
/* 227 */     } catch (Throwable e) {
/* 228 */       if (e instanceof Localizable) {
/* 229 */         logger.log(Level.SEVERE, defaultLocalizer.localize((Localizable)e), e);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 234 */         logger.log(Level.SEVERE, "caught throwable", e);
/*     */       } 
/* 236 */       response.setStatus(500);
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
/*     */   public void doDelete(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws ServletException {
/* 248 */     doPut(request, response, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeNotFoundErrorPage(Localizer localizer, HttpServletResponse response, String message) throws IOException {
/* 257 */     response.setStatus(404);
/* 258 */     response.setContentType("text/html");
/* 259 */     PrintWriter out = response.getWriter();
/* 260 */     out.println("<html>");
/* 261 */     out.println("<head><title>");
/* 262 */     out.println(WsservletMessages.SERVLET_HTML_TITLE());
/* 263 */     out.println("</title></head>");
/* 264 */     out.println("<body>");
/* 265 */     out.println(WsservletMessages.SERVLET_HTML_NOT_FOUND(message));
/* 266 */     out.println("</body>");
/* 267 */     out.println("</html>");
/*     */   }
/*     */   
/*     */   private void registerEndpointUrlPattern(ServletAdapter a) {
/* 271 */     String urlPattern = a.urlPattern;
/* 272 */     if (urlPattern.indexOf("*.") != -1) {
/*     */       
/* 274 */       logger.warning(WsservletMessages.SERVLET_WARNING_IGNORING_IMPLICIT_URL_PATTERN(a.name));
/*     */     }
/* 276 */     else if (urlPattern.endsWith("/*")) {
/* 277 */       this.pathUrlPatternEndpoints.add(a);
/*     */     }
/* 279 */     else if (this.fixedUrlPatternEndpoints.containsKey(urlPattern)) {
/* 280 */       logger.warning(WsservletMessages.SERVLET_WARNING_DUPLICATE_ENDPOINT_URL_PATTERN(a.name));
/*     */     } else {
/*     */       
/* 283 */       this.fixedUrlPatternEndpoints.put(urlPattern, a);
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
/*     */   protected ServletAdapter getTarget(HttpServletRequest request) {
/* 302 */     String path = request.getRequestURI().substring(request.getContextPath().length());
/*     */ 
/*     */     
/* 305 */     ServletAdapter result = this.fixedUrlPatternEndpoints.get(path);
/* 306 */     if (result == null) {
/* 307 */       for (ServletAdapter candidate : this.pathUrlPatternEndpoints) {
/* 308 */         String noSlashStar = candidate.getValidPath();
/* 309 */         if (path.equals(noSlashStar) || path.startsWith(noSlashStar + "/") || path.startsWith(noSlashStar + "?")) {
/* 310 */           result = candidate;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 316 */     return result;
/*     */   }
/*     */   
/*     */   protected Localizer getLocalizerFor(ServletRequest request) {
/* 320 */     Locale locale = request.getLocale();
/* 321 */     if (locale.equals(defaultLocalizer.getLocale())) {
/* 322 */       return defaultLocalizer;
/*     */     }
/*     */     
/* 325 */     synchronized (this.localizerMap) {
/* 326 */       Localizer localizer = this.localizerMap.get(locale);
/* 327 */       if (localizer == null) {
/* 328 */         localizer = new Localizer(locale);
/* 329 */         this.localizerMap.put(locale, localizer);
/*     */       } 
/* 331 */       return localizer;
/*     */     } 
/*     */   }
/*     */   
/* 335 */   private static final Localizer defaultLocalizer = new Localizer();
/* 336 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.servlet.http");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\WSServletDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */