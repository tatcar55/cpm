/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.ws.Binding;
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
/*     */ public class WSServletDelegate
/*     */ {
/*     */   public final List<EndpointAdapter> adapters;
/*  76 */   private final Map<String, EndpointAdapter> fixedUrlPatternEndpoints = new HashMap<String, EndpointAdapter>();
/*  77 */   private final List<EndpointAdapter> pathUrlPatternEndpoints = new ArrayList<EndpointAdapter>();
/*     */   
/*     */   public WSServletDelegate(List<EndpointAdapter> adapters, ServletContext context) {
/*  80 */     this.adapters = adapters;
/*     */     
/*  82 */     for (EndpointAdapter info : adapters) {
/*  83 */       registerEndpointUrlPattern(info);
/*     */     }
/*     */     
/*  86 */     if (logger.isLoggable(Level.INFO)) {
/*  87 */       logger.log(Level.INFO, "Initializing Servlet for {0}", this.fixedUrlPatternEndpoints);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  93 */     if (logger.isLoggable(Level.INFO)) {
/*  94 */       logger.log(Level.INFO, "Destroying Servlet for {0}", this.fixedUrlPatternEndpoints);
/*     */     }
/*     */     
/*  97 */     for (EndpointAdapter a : this.adapters) {
/*     */       try {
/*  99 */         a.dispose();
/* 100 */       } catch (Throwable e) {
/* 101 */         logger.log(Level.SEVERE, e.getMessage(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
/*     */     try {
/* 109 */       EndpointAdapter target = getTarget(request);
/* 110 */       if (target != null) {
/* 111 */         if (logger.isLoggable(Level.FINEST)) {
/* 112 */           logger.log(Level.FINEST, "Got request for endpoint {0}", target.getUrlPattern());
/*     */         }
/* 114 */         target.handle(context, request, response);
/*     */       } else {
/* 116 */         writeNotFoundErrorPage(response, "Invalid Request");
/*     */       } 
/* 118 */     } catch (WebServiceException e) {
/* 119 */       logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
/* 120 */       response.setStatus(500);
/* 121 */     } catch (Throwable e) {
/* 122 */       logger.log(Level.SEVERE, "caught throwable", e);
/* 123 */       response.setStatus(500);
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
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
/* 136 */     doGet(request, response, context);
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
/*     */   public void doPut(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
/*     */     try {
/* 149 */       EndpointAdapter target = getTarget(request);
/* 150 */       if (target != null) {
/* 151 */         if (logger.isLoggable(Level.FINEST)) {
/* 152 */           logger.log(Level.FINEST, "Got request for endpoint {0}", target.getUrlPattern());
/*     */         }
/*     */       } else {
/* 155 */         writeNotFoundErrorPage(response, "Invalid request");
/*     */         return;
/*     */       } 
/* 158 */       Binding binding = target.getEndpoint().getBinding();
/* 159 */       if (binding instanceof javax.xml.ws.http.HTTPBinding) {
/* 160 */         target.handle(context, request, response);
/*     */       } else {
/* 162 */         response.setStatus(405);
/*     */       } 
/* 164 */     } catch (WebServiceException e) {
/* 165 */       logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
/* 166 */       response.setStatus(500);
/* 167 */     } catch (Throwable e) {
/* 168 */       logger.log(Level.SEVERE, "caught throwable", e);
/* 169 */       response.setStatus(500);
/* 170 */       response.setStatus(500);
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
/*     */   public void doDelete(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
/* 185 */     doPut(request, response, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeNotFoundErrorPage(HttpServletResponse response, String message) throws IOException {
/* 193 */     response.setStatus(404);
/* 194 */     response.setContentType("text/html");
/* 195 */     PrintWriter out = response.getWriter();
/* 196 */     out.println("<html>");
/* 197 */     out.println("<head><title>");
/* 198 */     out.println("Web Services");
/* 199 */     out.println("</title></head>");
/* 200 */     out.println("<body>");
/* 201 */     out.println("Not found " + message);
/* 202 */     out.println("</body>");
/* 203 */     out.println("</html>");
/*     */   }
/*     */   
/*     */   private void registerEndpointUrlPattern(EndpointAdapter a) {
/* 207 */     String urlPattern = a.getUrlPattern();
/* 208 */     if (urlPattern.indexOf("*.") != -1) {
/*     */       
/* 210 */       logger.log(Level.WARNING, "Ignoring implicit url-pattern {0}", urlPattern);
/* 211 */     } else if (urlPattern.endsWith("/*")) {
/* 212 */       this.pathUrlPatternEndpoints.add(a);
/*     */     }
/* 214 */     else if (this.fixedUrlPatternEndpoints.containsKey(urlPattern)) {
/* 215 */       logger.log(Level.WARNING, "Ignoring duplicate url-pattern {0}", urlPattern);
/*     */     } else {
/* 217 */       this.fixedUrlPatternEndpoints.put(urlPattern, a);
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
/*     */   protected EndpointAdapter getTarget(HttpServletRequest request) {
/* 238 */     String path = request.getRequestURI().substring(request.getContextPath().length());
/*     */ 
/*     */     
/* 241 */     EndpointAdapter result = this.fixedUrlPatternEndpoints.get(path);
/* 242 */     if (result == null) {
/* 243 */       for (EndpointAdapter candidate : this.pathUrlPatternEndpoints) {
/* 244 */         String noSlashStar = candidate.getValidPath();
/* 245 */         if (path.equals(noSlashStar) || path.startsWith(noSlashStar + "/") || path.startsWith(noSlashStar + "?")) {
/* 246 */           result = candidate;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 252 */     return result;
/*     */   }
/*     */   
/* 255 */   private static final Logger logger = Logger.getLogger(WSServletDelegate.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\WSServletDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */