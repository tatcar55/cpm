/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.DefaultRealmAuthenticationAdapter;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.Subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RealmAuthenticationAdapter
/*     */ {
/*     */   public static final String UsernameAuthenticator = "com.sun.xml.xwss.RealmAuthenticator";
/*     */   private static final String SERVLET_CONTEXT_CLASSNAME = "javax.servlet.ServletContext";
/*     */   private static final String JAR_PREFIX = "META-INF/";
/*     */   
/*     */   public abstract boolean authenticate(Subject paramSubject, String paramString1, String paramString2) throws XWSSecurityException;
/*     */   
/*     */   public boolean authenticate(Subject callerSubject, String username, String password, Map runtimeProps) throws XWSSecurityException {
/*  93 */     return authenticate(callerSubject, username, password);
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
/*     */   public boolean authenticate(Subject callerSubject, String username, String passwordDigest, String nonce, String created) throws XWSSecurityException {
/* 107 */     throw new XWSSecurityException("Default Implementation : Override this authenticate method in your RealmAuthenticationAdapter");
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
/*     */   public boolean authenticate(Subject callerSubject, String username, String passwordDigest, String nonce, String created, Map runtimeProps) throws XWSSecurityException {
/* 123 */     return authenticate(callerSubject, username, passwordDigest, nonce, created);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RealmAuthenticationAdapter newInstance(Object context) {
/* 132 */     RealmAuthenticationAdapter adapter = null;
/* 133 */     URL url = null;
/*     */     
/* 135 */     if (context == null) {
/* 136 */       url = SecurityUtil.loadFromClasspath("META-INF/services/com.sun.xml.xwss.RealmAuthenticator");
/*     */     } else {
/* 138 */       url = SecurityUtil.loadFromContext("/META-INF/services/com.sun.xml.xwss.RealmAuthenticator", context);
/*     */     } 
/*     */     
/* 141 */     if (url != null) {
/* 142 */       Object obj = SecurityUtil.loadSPIClass(url, "com.sun.xml.xwss.RealmAuthenticator");
/* 143 */       if (obj != null && !(obj instanceof RealmAuthenticationAdapter)) {
/* 144 */         throw new XWSSecurityRuntimeException("Class :" + obj.getClass().getName() + " is not a valid RealmAuthenticationProvider");
/*     */       }
/* 146 */       adapter = (RealmAuthenticationAdapter)obj;
/*     */     } 
/*     */     
/* 149 */     if (adapter != null) {
/* 150 */       return adapter;
/*     */     }
/* 152 */     return (RealmAuthenticationAdapter)new DefaultRealmAuthenticationAdapter();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\RealmAuthenticationAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */