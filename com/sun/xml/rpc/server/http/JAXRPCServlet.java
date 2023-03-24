/*     */ package com.sun.xml.rpc.server.http;
/*     */ 
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
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
/*     */ public class JAXRPCServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   public void init(ServletConfig servletConfig) throws ServletException {
/*  51 */     super.init(servletConfig);
/*     */     
/*  53 */     this.localizer = new Localizer();
/*  54 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
/*     */ 
/*     */ 
/*     */     
/*  58 */     ServletContext context = servletConfig.getServletContext();
/*  59 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  60 */     if (classLoader == null) {
/*  61 */       classLoader = getClass().getClassLoader();
/*     */     }
/*     */     
/*     */     try {
/*  65 */       JAXRPCRuntimeInfoParser parser = new JAXRPCRuntimeInfoParser(classLoader);
/*     */       
/*  67 */       InputStream is = context.getResourceAsStream("/WEB-INF/jaxrpc-ri-runtime.xml");
/*  68 */       JAXRPCRuntimeInfo info = parser.parse(is);
/*  69 */       context.setAttribute("com.sun.xml.rpc.server.http.info", info);
/*     */ 
/*     */       
/*  72 */       String delegateClassName = servletConfig.getInitParameter("delegate");
/*     */ 
/*     */       
/*  75 */       if (delegateClassName == null && servletConfig.getInitParameter("configuration.file") != null)
/*     */       {
/*     */ 
/*     */         
/*  79 */         delegateClassName = "com.sun.xml.rpc.server.http.ea.JAXRPCServletDelegate";
/*     */       }
/*     */       
/*  82 */       if (delegateClassName == null) {
/*  83 */         delegateClassName = "com.sun.xml.rpc.server.http.JAXRPCServletDelegate";
/*     */       }
/*     */       
/*  86 */       Class<?> delegateClass = Class.forName(delegateClassName, true, Thread.currentThread().getContextClassLoader());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       this.delegate = (ServletDelegate)delegateClass.newInstance();
/*  92 */       this.delegate.init(servletConfig);
/*     */     }
/*  94 */     catch (ServletException e) {
/*  95 */       logger.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/*  96 */       throw e;
/*  97 */     } catch (Throwable e) {
/*  98 */       String message = this.localizer.localize(this.messageFactory.getMessage("error.servlet.caughtThrowableInInit", new Object[] { e }));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       logger.log(Level.SEVERE, message, e);
/* 104 */       throw new ServletException(message);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 109 */     if (this.delegate != null) {
/* 110 */       this.delegate.destroy();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 118 */     if (this.delegate != null) {
/* 119 */       this.delegate.doPost(request, response);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
/* 127 */     if (this.delegate != null) {
/* 128 */       this.delegate.doGet(request, response);
/*     */     }
/*     */   }
/*     */   
/* 132 */   protected ServletDelegate delegate = null;
/*     */   
/*     */   private LocalizableMessageFactory messageFactory;
/*     */   
/*     */   private Localizer localizer;
/*     */   
/*     */   private static final String DELEGATE_PROPERTY = "delegate";
/*     */   
/*     */   private static final String DEFAULT_DELEGATE_CLASS_NAME = "com.sun.xml.rpc.server.http.JAXRPCServletDelegate";
/*     */   
/*     */   private static final String EA_CONFIG_FILE_PROPERTY = "configuration.file";
/*     */   
/*     */   private static final String EA_DELEGATE_CLASS_NAME = "com.sun.xml.rpc.server.http.ea.JAXRPCServletDelegate";
/*     */   
/*     */   private static final String JAXRPC_RI_RUNTIME = "/WEB-INF/jaxrpc-ri-runtime.xml";
/*     */   
/*     */   public static final String JAXRPC_RI_RUNTIME_INFO = "com.sun.xml.rpc.server.http.info";
/*     */   
/*     */   public static final String JAXRPC_RI_PROPERTY_PUBLISH_WSDL = "com.sun.xml.rpc.server.http.publishWSDL";
/*     */   
/*     */   public static final String JAXRPC_RI_PROPERTY_PUBLISH_MODEL = "com.sun.xml.rpc.server.http.publishModel";
/*     */   
/*     */   public static final String JAXRPC_RI_PROPERTY_PUBLISH_STATUS_PAGE = "com.sun.xml.rpc.server.http.publishStatusPage";
/* 155 */   private static final Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\JAXRPCServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */