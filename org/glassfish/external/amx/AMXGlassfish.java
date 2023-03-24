/*     */ package org.glassfish.external.amx;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AMXGlassfish
/*     */ {
/*     */   public static final String DEFAULT_JMX_DOMAIN = "amx";
/*  57 */   public static final AMXGlassfish DEFAULT = new AMXGlassfish("amx");
/*     */   
/*     */   private final String mJMXDomain;
/*     */   
/*     */   private final ObjectName mDomainRoot;
/*     */ 
/*     */   
/*     */   public AMXGlassfish(String jmxDomain) {
/*  65 */     this.mJMXDomain = jmxDomain;
/*  66 */     this.mDomainRoot = newObjectName("", "domain-root", null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getGlassfishVersion() {
/*  73 */     String version = System.getProperty("glassfish.version");
/*  74 */     return version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String amxJMXDomain() {
/*  85 */     return this.mJMXDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String amxSupportDomain() {
/*  91 */     return amxJMXDomain() + "-support";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String dasName() {
/*  97 */     return "server";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String dasConfig() {
/* 103 */     return dasName() + "-config";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName domainRoot() {
/* 109 */     return this.mDomainRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName monitoringRoot() {
/* 115 */     return newObjectName("/", "mon", null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName serverMon(String serverName) {
/* 121 */     return newObjectName("/mon", "server-mon", serverName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectName serverMonForDAS() {
/* 126 */     return serverMon("server");
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
/*     */   public ObjectName newObjectName(String pp, String type, String name) {
/* 142 */     String props = prop("pp", pp) + "," + prop("type", type);
/* 143 */     if (name != null) {
/* 144 */       props = props + "," + prop("name", name);
/*     */     }
/*     */     
/* 147 */     return newObjectName(props);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName newObjectName(String s) {
/* 153 */     String name = s;
/* 154 */     if (!name.startsWith(amxJMXDomain())) {
/* 155 */       name = amxJMXDomain() + ":" + name;
/*     */     }
/*     */     
/* 158 */     return AMXUtil.newObjectName(name);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String prop(String key, String value) {
/* 163 */     return key + "=" + value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getBootAMXMBeanObjectName() {
/* 171 */     return AMXUtil.newObjectName(amxSupportDomain() + ":type=boot-amx");
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
/*     */   public void invokeBootAMX(MBeanServerConnection conn) {
/*     */     try {
/* 184 */       conn.invoke(getBootAMXMBeanObjectName(), "bootAMX", null, null);
/*     */     }
/* 186 */     catch (Exception e) {
/*     */       
/* 188 */       e.printStackTrace();
/* 189 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void invokeWaitAMXReady(MBeanServerConnection conn, ObjectName objectName) {
/*     */     try {
/* 200 */       conn.invoke(objectName, "waitAMXReady", null, null);
/*     */     }
/* 202 */     catch (Exception e) {
/*     */       
/* 204 */       throw new RuntimeException(e);
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
/*     */   public <T extends MBeanListener.Callback> MBeanListener<T> listenForDomainRoot(MBeanServerConnection server, T callback) {
/* 216 */     MBeanListener<T> listener = new MBeanListener<T>(server, domainRoot(), callback);
/* 217 */     listener.startListening();
/* 218 */     return listener;
/*     */   }
/*     */   
/*     */   private static final class WaitForDomainRootListenerCallback extends MBeanListener.CallbackImpl {
/*     */     private final MBeanServerConnection mConn;
/*     */     
/*     */     public WaitForDomainRootListenerCallback(MBeanServerConnection conn) {
/* 225 */       this.mConn = conn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mbeanRegistered(ObjectName objectName, MBeanListener listener) {
/* 230 */       super.mbeanRegistered(objectName, listener);
/* 231 */       AMXGlassfish.invokeWaitAMXReady(this.mConn, objectName);
/* 232 */       this.mLatch.countDown();
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
/*     */   public ObjectName waitAMXReady(MBeanServerConnection server) {
/* 244 */     WaitForDomainRootListenerCallback callback = new WaitForDomainRootListenerCallback(server);
/* 245 */     listenForDomainRoot(server, callback);
/* 246 */     callback.await();
/* 247 */     return callback.getRegistered();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends MBeanListener.Callback> MBeanListener<T> listenForBootAMX(MBeanServerConnection server, T callback) {
/* 258 */     MBeanListener<T> listener = new MBeanListener<T>(server, getBootAMXMBeanObjectName(), callback);
/* 259 */     listener.startListening();
/* 260 */     return listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BootAMXCallback
/*     */     extends MBeanListener.CallbackImpl
/*     */   {
/*     */     private final MBeanServerConnection mConn;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BootAMXCallback(MBeanServerConnection conn) {
/* 275 */       this.mConn = conn;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mbeanRegistered(ObjectName objectName, MBeanListener listener) {
/* 281 */       super.mbeanRegistered(objectName, listener);
/* 282 */       this.mLatch.countDown();
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
/*     */   public ObjectName bootAMX(MBeanServerConnection conn) throws IOException {
/* 296 */     ObjectName domainRoot = domainRoot();
/*     */     
/* 298 */     if (!conn.isRegistered(domainRoot)) {
/*     */ 
/*     */       
/* 301 */       BootAMXCallback callback = new BootAMXCallback(conn);
/* 302 */       listenForBootAMX(conn, callback);
/* 303 */       callback.await();
/*     */       
/* 305 */       invokeBootAMX(conn);
/*     */       
/* 307 */       WaitForDomainRootListenerCallback drCallback = new WaitForDomainRootListenerCallback(conn);
/* 308 */       listenForDomainRoot(conn, drCallback);
/* 309 */       drCallback.await();
/*     */       
/* 311 */       invokeWaitAMXReady(conn, domainRoot);
/*     */     }
/*     */     else {
/*     */       
/* 315 */       invokeWaitAMXReady(conn, domainRoot);
/*     */     } 
/* 317 */     return domainRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName bootAMX(MBeanServer server) {
/*     */     try {
/* 324 */       return bootAMX(server);
/*     */     }
/* 326 */     catch (IOException e) {
/*     */       
/* 328 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\amx\AMXGlassfish.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */