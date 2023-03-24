/*     */ package org.glassfish.gmbal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.management.MBeanServer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class ManagedObjectManagerNOPImpl
/*     */   implements ManagedObjectManager
/*     */ {
/*  62 */   static final ManagedObjectManager self = new ManagedObjectManagerNOPImpl();
/*     */   
/*  64 */   private static final GmbalMBean gmb = new GmbalMBeanNOPImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspendJMXRegistration() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeJMXRegistration() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManagedObject(Object obj) {
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public GmbalMBean createRoot() {
/*  82 */     return gmb;
/*     */   }
/*     */   
/*     */   public GmbalMBean createRoot(Object root) {
/*  86 */     return gmb;
/*     */   }
/*     */   
/*     */   public GmbalMBean createRoot(Object root, String name) {
/*  90 */     return gmb;
/*     */   }
/*     */   
/*     */   public Object getRoot() {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public GmbalMBean register(Object parent, Object obj, String name) {
/*  98 */     return gmb;
/*     */   }
/*     */   
/*     */   public GmbalMBean register(Object parent, Object obj) {
/* 102 */     return gmb;
/*     */   }
/*     */   
/*     */   public GmbalMBean registerAtRoot(Object obj, String name) {
/* 106 */     return gmb;
/*     */   }
/*     */   
/*     */   public GmbalMBean registerAtRoot(Object obj) {
/* 110 */     return gmb;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister(Object obj) {}
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName(Object obj) {
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   public Object getObject(ObjectName oname) {
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stripPrefix(String... str) {}
/*     */ 
/*     */   
/*     */   public String getDomain() {
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMBeanServer(MBeanServer server) {}
/*     */ 
/*     */   
/*     */   public MBeanServer getMBeanServer() {
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResourceBundle(ResourceBundle rb) {}
/*     */ 
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnnotation(AnnotatedElement element, Annotation annotation) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel level) {}
/*     */ 
/*     */   
/*     */   public void setRuntimeDebug(boolean flag) {}
/*     */ 
/*     */   
/*     */   public String dumpSkeleton(Object obj) {
/* 162 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTypelibDebug(int level) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void stripPackagePrefix() {}
/*     */ 
/*     */   
/*     */   public void suppressDuplicateRootReport(boolean suppressReport) {}
/*     */ 
/*     */   
/*     */   public AMXClient getAMXClient(Object obj) {
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   public void setJMXRegistrationDebug(boolean flag) {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\ManagedObjectManagerNOPImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */