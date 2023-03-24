/*     */ package com.sun.xml.ws.runtime.dev;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.commons.AbstractMOMRegistrationAware;
/*     */ import com.sun.xml.ws.commons.MOMRegistrationAware;
/*     */ import com.sun.xml.ws.commons.WSEndpointCollectionBasedMOMListener;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.glassfish.gmbal.AMXMetadata;
/*     */ import org.glassfish.gmbal.Description;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedObject
/*     */ @Description("Session manager used by RM and SC")
/*     */ @AMXMetadata(type = "WSRMSCSessionManager")
/*     */ public abstract class SessionManager
/*     */   extends AbstractMOMRegistrationAware
/*     */ {
/*  83 */   private static final Logger LOGGER = Logger.getLogger(SessionManager.class);
/*     */   
/*  85 */   private static Properties config = null;
/*     */   
/*  87 */   private static final Object LOCK = new Object();
/*  88 */   private static final Map<WSEndpoint, SessionManager> SESSION_MANAGERS = new HashMap<WSEndpoint, SessionManager>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final WSEndpointCollectionBasedMOMListener listener = new WSEndpointCollectionBasedMOMListener(LOCK, "RM_SC_SessionManager", SESSION_MANAGERS); static {
/*  96 */     listener.initialize();
/*     */   }
/*     */   
/*     */   public static final String TIMEOUT_INTERVAL = "session-timeout";
/*     */   public static final String SESSION_THRESHOLD = "session-threshold";
/*     */   
/*     */   public static Properties getConfig() {
/* 103 */     return config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setConfig(Properties aConfig) {
/* 110 */     config = aConfig;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeSessionManager(WSEndpoint endpoint) {
/* 207 */     synchronized (LOCK) {
/*     */       try {
/* 209 */         LOGGER.entering();
/* 210 */         Object o = SESSION_MANAGERS.remove(endpoint);
/* 211 */         LOGGER.config(String.format("removeSessionManager(%s): %s", new Object[] { endpoint, o }));
/*     */         
/* 213 */         SessionManager sessionManager = (SessionManager)o;
/* 214 */         if (sessionManager != null && sessionManager.isRegisteredAtMOM()) {
/* 215 */           listener.unregisterFromMOM((MOMRegistrationAware)sessionManager, endpoint);
/*     */         }
/*     */       } finally {
/* 218 */         LOGGER.exiting();
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
/*     */   public static SessionManager getSessionManager(WSEndpoint endpoint, boolean isSC, Properties props) {
/* 232 */     synchronized (LOCK) {
/*     */       try {
/* 234 */         LOGGER.entering();
/* 235 */         SessionManager sm = SESSION_MANAGERS.get(endpoint);
/* 236 */         if (sm == null) {
/* 237 */           ServiceFinder<SessionManager> finder = ServiceFinder.find(SessionManager.class);
/*     */           
/* 239 */           if (finder != null && ((SessionManager[])finder.toArray()).length > 0) {
/* 240 */             sm = ((SessionManager[])finder.toArray())[0];
/*     */           } else {
/* 242 */             sm = new SessionManagerImpl(endpoint, isSC, props);
/*     */           } 
/* 244 */           SESSION_MANAGERS.put(endpoint, sm);
/* 245 */           if (listener.canRegisterAtMOM()) {
/* 246 */             listener.registerAtMOM((MOMRegistrationAware)sm, endpoint);
/*     */           }
/* 248 */           LOGGER.config(String.format("getSessionManager(%s): created: %s", new Object[] { endpoint, sm }));
/*     */         } else {
/* 250 */           LOGGER.config(String.format("getSessionManager(%s): found existing: %s", new Object[] { endpoint, sm }));
/*     */         } 
/* 252 */         return sm;
/*     */       } finally {
/* 254 */         LOGGER.exiting();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SessionManager getSessionManager(WSEndpoint endpoint, Properties props) {
/* 260 */     return getSessionManager(endpoint, false, props);
/*     */   }
/*     */   
/*     */   public abstract Session getSession(String paramString);
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("The set of valid Session keys")
/*     */   public abstract Set<String> keys();
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("The collection of valid Sessions")
/*     */   protected abstract Collection<Session> sessions();
/*     */   
/*     */   public abstract void terminateSession(String paramString);
/*     */   
/*     */   public abstract Session createSession(String paramString, Class paramClass);
/*     */   
/*     */   public abstract Session createSession(String paramString, Object paramObject);
/*     */   
/*     */   public abstract Session createSession(String paramString, SecurityContextTokenInfo paramSecurityContextTokenInfo);
/*     */   
/*     */   public abstract Session createSession(String paramString);
/*     */   
/*     */   public abstract void saveSession(String paramString);
/*     */   
/*     */   public abstract IssuedTokenContext getSecurityContext(String paramString, boolean paramBoolean);
/*     */   
/*     */   public abstract void addSecurityContext(String paramString, IssuedTokenContext paramIssuedTokenContext);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\dev\SessionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */