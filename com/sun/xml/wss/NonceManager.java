/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.commons.AbstractMOMRegistrationAware;
/*     */ import com.sun.xml.ws.commons.MOMRegistrationAware;
/*     */ import com.sun.xml.ws.commons.WSEndpointCollectionBasedMOMListener;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.DefaultNonceManager;
/*     */ import com.sun.xml.wss.impl.misc.HANonceManager;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.net.URL;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ @ManagedObject
/*     */ @Description("per-endpoint NonceManager")
/*     */ @AMXMetadata(type = "WSNonceManager")
/*     */ public abstract class NonceManager
/*     */   extends AbstractMOMRegistrationAware
/*     */ {
/*  79 */   protected static final Logger LOGGER = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */   
/*     */   public static final String nonceManager = "com.sun.xml.xwss.NonceManager";
/*     */   
/*     */   private static final String NONCE_MANAGER = "NonceManager";
/*  84 */   private static WeakHashMap<WSEndpoint, NonceManager> nonceMgrMap = new WeakHashMap<WSEndpoint, NonceManager>();
/*  85 */   private static NonceManager jaxRPCNonceManager = null;
/*     */   private long maxNonceAge;
/*  87 */   private static final Object LOCK = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final WSEndpointCollectionBasedMOMListener listener = new WSEndpointCollectionBasedMOMListener(LOCK, "NonceManager", nonceMgrMap); static {
/*  93 */     listener.initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public long getMaxNonceAge() {
/* 102 */     return this.maxNonceAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxNonceAge(long maxNonceAge) {
/* 110 */     this.maxNonceAge = maxNonceAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NonceException
/*     */     extends XWSSecurityException
/*     */   {
/*     */     public NonceException(String message) {
/* 124 */       super(message);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public NonceException(String message, Throwable cause) {
/* 133 */       super(message, cause);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public NonceException(Throwable cause) {
/* 141 */       super(cause);
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
/*     */   public static NonceManager getInstance(long maxNonceAge, WSEndpoint endpoint) {
/* 161 */     synchronized (LOCK) {
/* 162 */       DefaultNonceManager defaultNonceManager; if (endpoint == null && 
/* 163 */         LOGGER.isLoggable(Level.FINE)) {
/* 164 */         LOGGER.log(Level.FINE, String.format("getInstance(): endpoint is null: using singleton", new Object[0]));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 169 */       NonceManager nonceMgr = (endpoint != null) ? nonceMgrMap.get(endpoint) : jaxRPCNonceManager;
/*     */ 
/*     */       
/* 172 */       if (nonceMgr != null) {
/* 173 */         if (LOGGER.isLoggable(Level.FINE)) {
/* 174 */           LOGGER.log(Level.FINE, String.format("getInstance(%s): found existing: %s", new Object[] { endpoint, nonceMgr }));
/*     */         }
/*     */ 
/*     */         
/* 178 */         return nonceMgr;
/*     */       } 
/*     */       
/* 181 */       URL url = SecurityUtil.loadFromClasspath("/META-INF/services/com.sun.xml.xwss.NonceManager");
/* 182 */       if (url != null) {
/* 183 */         Object obj = SecurityUtil.loadSPIClass(url, "com.sun.xml.xwss.NonceManager");
/* 184 */         if (obj != null && !(obj instanceof NonceManager)) {
/* 185 */           throw new XWSSecurityRuntimeException("Class :" + obj.getClass().getName() + " is not a valid NonceManager");
/*     */         }
/* 187 */         nonceMgr = (NonceManager)obj;
/*     */       } 
/*     */       
/* 190 */       if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
/* 191 */         HANonceManager hANonceManager = new HANonceManager(maxNonceAge);
/*     */       }
/* 193 */       else if (url == null) {
/* 194 */         defaultNonceManager = new DefaultNonceManager();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 199 */       if (defaultNonceManager == null) {
/* 200 */         defaultNonceManager = new DefaultNonceManager();
/*     */       }
/*     */       
/* 203 */       defaultNonceManager.setMaxNonceAge(maxNonceAge);
/*     */       
/* 205 */       if (endpoint != null) {
/* 206 */         nonceMgrMap.put(endpoint, defaultNonceManager);
/* 207 */         if (listener.canRegisterAtMOM()) {
/* 208 */           listener.registerAtMOM((MOMRegistrationAware)defaultNonceManager, endpoint);
/*     */         }
/*     */       } else {
/* 211 */         jaxRPCNonceManager = (NonceManager)defaultNonceManager;
/*     */       } 
/*     */       
/* 214 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 215 */         LOGGER.log(Level.FINE, String.format("getInstance(%s): created: %s", new Object[] { endpoint, defaultNonceManager }));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 220 */       return (NonceManager)defaultNonceManager;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void deleteInstance(WSEndpoint endpoint) {
/* 225 */     synchronized (LOCK) {
/* 226 */       Object o = (endpoint != null) ? nonceMgrMap.remove(endpoint) : jaxRPCNonceManager;
/* 227 */       if (endpoint == null) {
/* 228 */         jaxRPCNonceManager = null;
/*     */       }
/* 230 */       NonceManager nonceManager = (NonceManager)o;
/* 231 */       if (endpoint != null && o != null && nonceManager.isRegisteredAtMOM()) {
/* 232 */         listener.unregisterFromMOM((MOMRegistrationAware)nonceManager, endpoint);
/*     */       }
/* 234 */       if (LOGGER.isLoggable(Level.FINE))
/* 235 */         LOGGER.log(Level.FINE, String.format("deleteInstance(%s): %s", new Object[] { endpoint, o })); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract boolean validateNonce(String paramString1, String paramString2) throws NonceException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\NonceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */