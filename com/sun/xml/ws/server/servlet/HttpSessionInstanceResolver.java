/*    */ package com.sun.xml.ws.server.servlet;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.developer.servlet.HttpSessionScope;
/*    */ import com.sun.xml.ws.server.AbstractMultiInstanceResolver;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class HttpSessionInstanceResolver<T>
/*    */   extends AbstractMultiInstanceResolver<T>
/*    */ {
/*    */   public HttpSessionInstanceResolver(@NotNull Class<T> clazz) {
/* 62 */     super(clazz);
/*    */   }
/*    */   @NotNull
/*    */   public T resolve(Packet request) {
/* 66 */     HttpServletRequest sr = (HttpServletRequest)request.get("javax.xml.ws.servlet.request");
/* 67 */     if (sr == null) {
/* 68 */       throw new WebServiceException(this.clazz + " has @" + HttpSessionScope.class.getSimpleName() + " but it's deployed on non-servlet endpoint");
/*    */     }
/*    */     
/* 71 */     HttpSession session = sr.getSession();
/* 72 */     T o = this.clazz.cast(session.getAttribute(this.clazz.getName()));
/* 73 */     if (o == null) {
/* 74 */       o = (T)create();
/* 75 */       session.setAttribute(this.clazz.getName(), o);
/*    */     } 
/* 77 */     return o;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\servlet\HttpSessionInstanceResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */