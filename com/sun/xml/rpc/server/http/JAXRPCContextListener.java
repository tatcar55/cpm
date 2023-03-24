/*    */ package com.sun.xml.rpc.server.http;
/*    */ 
/*    */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*    */ import com.sun.xml.rpc.util.localization.Localizer;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextAttributeEvent;
/*    */ import javax.servlet.ServletContextAttributeListener;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JAXRPCContextListener
/*    */   implements ServletContextAttributeListener, ServletContextListener
/*    */ {
/* 49 */   private Localizer localizer = new Localizer();
/* 50 */   private LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.jaxrpcservlet");
/*    */   
/*    */   private ServletContext context;
/*    */ 
/*    */   
/*    */   public void attributeAdded(ServletContextAttributeEvent event) {}
/*    */ 
/*    */   
/*    */   public void attributeRemoved(ServletContextAttributeEvent event) {}
/*    */ 
/*    */   
/*    */   public void attributeReplaced(ServletContextAttributeEvent event) {}
/*    */   
/*    */   public void contextDestroyed(ServletContextEvent event) {
/* 64 */     this.context = null;
/* 65 */     if (logger.isLoggable(Level.INFO)) {
/* 66 */       logger.info(this.localizer.localize(this.messageFactory.getMessage("listener.info.destroy")));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void contextInitialized(ServletContextEvent event) {
/* 73 */     this.context = event.getServletContext();
/* 74 */     if (logger.isLoggable(Level.INFO)) {
/* 75 */       logger.info(this.localizer.localize(this.messageFactory.getMessage("listener.info.initialize")));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 85 */   private static final Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\JAXRPCContextListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */