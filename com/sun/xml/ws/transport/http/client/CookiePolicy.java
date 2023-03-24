/*    */ package com.sun.xml.ws.transport.http.client;
/*    */ 
/*    */ import java.net.URI;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CookiePolicy
/*    */ {
/* 60 */   public static final CookiePolicy ACCEPT_ALL = new CookiePolicy() {
/*    */       public boolean shouldAccept(URI uri, HttpCookie cookie) {
/* 62 */         return true;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public static final CookiePolicy ACCEPT_NONE = new CookiePolicy() {
/*    */       public boolean shouldAccept(URI uri, HttpCookie cookie) {
/* 71 */         return false;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 78 */   public static final CookiePolicy ACCEPT_ORIGINAL_SERVER = new CookiePolicy() {
/*    */       public boolean shouldAccept(URI uri, HttpCookie cookie) {
/* 80 */         return HttpCookie.domainMatches(cookie.getDomain(), uri.getHost());
/*    */       }
/*    */     };
/*    */   
/*    */   boolean shouldAccept(URI paramURI, HttpCookie paramHttpCookie);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\CookiePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */