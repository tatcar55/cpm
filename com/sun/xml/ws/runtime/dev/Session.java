/*     */ package com.sun.xml.ws.runtime.dev;
/*     */ 
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import org.glassfish.gmbal.Description;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
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
/*     */ @ManagedData
/*     */ @Description("RM and SC session information")
/*     */ public class Session
/*     */ {
/*     */   public static final String SESSION_ID_KEY = "com.sun.xml.ws.sessionid";
/*     */   public static final String SESSION_KEY = "com.sun.xml.ws.session";
/*     */   private SessionManager manager;
/*     */   private String key;
/*     */   private Object userData;
/*     */   private SecurityContextTokenInfo securityInfo;
/*     */   
/*     */   protected Session() {}
/*     */   
/*     */   public Session(SessionManager manager, String key, Object userData) {
/*  99 */     this();
/*     */     
/* 101 */     this.manager = manager;
/* 102 */     this.userData = userData;
/* 103 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Session key")
/*     */   public String getSessionKey() {
/* 114 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getUserData() {
/* 123 */     return this.userData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Security context token info")
/*     */   public SecurityContextTokenInfo getSecurityInfo() {
/* 134 */     return this.securityInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecurityInfo(SecurityContextTokenInfo securityInfo) {
/* 143 */     this.securityInfo = securityInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/* 151 */     this.manager.saveSession(getSessionKey());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\dev\Session.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */