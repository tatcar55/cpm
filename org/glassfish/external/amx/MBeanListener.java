/*     */ package org.glassfish.external.amx;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.MBeanServerNotification;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.external.arc.Stability;
/*     */ import org.glassfish.external.arc.Taxonomy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Taxonomy(stability = Stability.UNCOMMITTED)
/*     */ public class MBeanListener<T extends MBeanListener.Callback>
/*     */   implements NotificationListener
/*     */ {
/*     */   private final String mJMXDomain;
/*     */   private final String mType;
/*     */   private final String mName;
/*     */   private final ObjectName mObjectName;
/*     */   private final MBeanServerConnection mMBeanServer;
/*     */   private final T mCallback;
/*     */   
/*     */   private static void debug(Object o) {
/*  61 */     System.out.println("" + o);
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
/*     */   public String toString() {
/*  78 */     return "MBeanListener: ObjectName=" + this.mObjectName + ", type=" + this.mType + ", name=" + this.mName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  83 */     return this.mType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  88 */     return this.mName;
/*     */   }
/*     */ 
/*     */   
/*     */   public MBeanServerConnection getMBeanServer() {
/*  93 */     return this.mMBeanServer;
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
/*     */   public static class CallbackImpl
/*     */     implements Callback
/*     */   {
/* 109 */     private volatile ObjectName mRegistered = null;
/* 110 */     private volatile ObjectName mUnregistered = null; private final boolean mStopAtFirst;
/*     */     protected final CountDownLatch mLatch;
/*     */     
/*     */     public CallbackImpl() {
/* 114 */       this(true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectName getRegistered() {
/* 122 */       return this.mRegistered; } public ObjectName getUnregistered() {
/* 123 */       return this.mUnregistered;
/*     */     } public CallbackImpl(boolean stopAtFirst) {
/* 125 */       this.mLatch = new CountDownLatch(1);
/*     */       this.mStopAtFirst = stopAtFirst;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void await() {
/*     */       try {
/* 135 */         this.mLatch.await();
/*     */       }
/* 137 */       catch (InterruptedException e) {
/*     */         
/* 139 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mbeanRegistered(ObjectName objectName, MBeanListener listener) {
/* 145 */       this.mRegistered = objectName;
/* 146 */       if (this.mStopAtFirst)
/*     */       {
/* 148 */         listener.stopListening();
/*     */       }
/*     */     }
/*     */     
/*     */     public void mbeanUnregistered(ObjectName objectName, MBeanListener listener) {
/* 153 */       this.mUnregistered = objectName;
/* 154 */       if (this.mStopAtFirst)
/*     */       {
/* 156 */         listener.stopListening();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public T getCallback() {
/* 163 */     return this.mCallback;
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
/*     */   public MBeanListener(MBeanServerConnection server, ObjectName objectName, T callback) {
/* 178 */     this.mMBeanServer = server;
/* 179 */     this.mObjectName = objectName;
/* 180 */     this.mJMXDomain = null;
/* 181 */     this.mType = null;
/* 182 */     this.mName = null;
/* 183 */     this.mCallback = callback;
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
/*     */   public MBeanListener(MBeanServerConnection server, String domain, String type, T callback) {
/* 199 */     this(server, domain, type, null, callback);
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
/*     */   public MBeanListener(MBeanServerConnection server, String domain, String type, String name, T callback) {
/* 218 */     this.mMBeanServer = server;
/* 219 */     this.mJMXDomain = domain;
/* 220 */     this.mType = type;
/* 221 */     this.mName = name;
/* 222 */     this.mObjectName = null;
/* 223 */     this.mCallback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRegistered(MBeanServerConnection conn, ObjectName objectName) {
/*     */     try {
/* 231 */       return conn.isRegistered(objectName);
/*     */     }
/* 233 */     catch (Exception e) {
/*     */       
/* 235 */       throw new RuntimeException(e);
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
/*     */   public void startListening() {
/*     */     try {
/* 249 */       this.mMBeanServer.addNotificationListener(AMXUtil.getMBeanServerDelegateObjectName(), this, (NotificationFilter)null, this);
/*     */     }
/* 251 */     catch (Exception e) {
/*     */       
/* 253 */       throw new RuntimeException("Can't add NotificationListener", e);
/*     */     } 
/*     */     
/* 256 */     if (this.mObjectName != null) {
/*     */       
/* 258 */       if (isRegistered(this.mMBeanServer, this.mObjectName))
/*     */       {
/* 260 */         this.mCallback.mbeanRegistered(this.mObjectName, this);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 266 */       String props = "type=" + this.mType;
/* 267 */       if (this.mName != null)
/*     */       {
/* 269 */         props = props + "," + "name" + this.mName;
/*     */       }
/*     */       
/* 272 */       ObjectName pattern = AMXUtil.newObjectName(this.mJMXDomain + ":" + props);
/*     */       
/*     */       try {
/* 275 */         Set<ObjectName> matched = this.mMBeanServer.queryNames(pattern, null);
/* 276 */         for (ObjectName objectName : matched)
/*     */         {
/* 278 */           this.mCallback.mbeanRegistered(objectName, this);
/*     */         }
/*     */       }
/* 281 */       catch (Exception e) {
/*     */         
/* 283 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopListening() {
/*     */     try {
/* 294 */       this.mMBeanServer.removeNotificationListener(AMXUtil.getMBeanServerDelegateObjectName(), this);
/*     */     }
/* 296 */     catch (Exception e) {
/*     */       
/* 298 */       throw new RuntimeException("Can't remove NotificationListener " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleNotification(Notification notifIn, Object handback) {
/* 306 */     if (notifIn instanceof MBeanServerNotification) {
/*     */       
/* 308 */       MBeanServerNotification notif = (MBeanServerNotification)notifIn;
/* 309 */       ObjectName objectName = notif.getMBeanName();
/*     */       
/* 311 */       boolean match = false;
/* 312 */       if (this.mObjectName != null && this.mObjectName.equals(objectName)) {
/*     */         
/* 314 */         match = true;
/*     */       }
/* 316 */       else if (objectName.getDomain().equals(this.mJMXDomain)) {
/*     */         
/* 318 */         if (this.mType != null && this.mType.equals(objectName.getKeyProperty("type"))) {
/*     */           
/* 320 */           String mbeanName = objectName.getKeyProperty("name");
/* 321 */           if (this.mName != null && this.mName.equals(mbeanName))
/*     */           {
/* 323 */             match = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 328 */       if (match) {
/*     */         
/* 330 */         String notifType = notif.getType();
/* 331 */         if ("JMX.mbean.registered".equals(notifType)) {
/*     */           
/* 333 */           this.mCallback.mbeanRegistered(objectName, this);
/*     */         }
/* 335 */         else if ("JMX.mbean.unregistered".equals(notifType)) {
/*     */           
/* 337 */           this.mCallback.mbeanUnregistered(objectName, this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void mbeanRegistered(ObjectName param1ObjectName, MBeanListener param1MBeanListener);
/*     */     
/*     */     void mbeanUnregistered(ObjectName param1ObjectName, MBeanListener param1MBeanListener);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\amx\MBeanListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */