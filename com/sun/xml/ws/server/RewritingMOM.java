/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.gmbal.AMXClient;
/*     */ import org.glassfish.gmbal.GmbalMBean;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RewritingMOM
/*     */   implements ManagedObjectManager
/*     */ {
/*     */   private final ManagedObjectManager mom;
/*     */   private static final String gmbalQuotingCharsRegex = "\n|\\|\"|\\*|\\?|:|=|,";
/*     */   private static final String replacementChar = "-";
/*     */   
/*     */   RewritingMOM(ManagedObjectManager mom) {
/* 381 */     this.mom = mom;
/*     */   }
/*     */   private String rewrite(String x) {
/* 384 */     return x.replaceAll("\n|\\|\"|\\*|\\?|:|=|,", "-");
/*     */   }
/*     */ 
/*     */   
/*     */   public void suspendJMXRegistration() {
/* 389 */     this.mom.suspendJMXRegistration(); }
/* 390 */   public void resumeJMXRegistration() { this.mom.resumeJMXRegistration(); }
/* 391 */   public GmbalMBean createRoot() { return this.mom.createRoot(); } public GmbalMBean createRoot(Object root) {
/* 392 */     return this.mom.createRoot(root);
/*     */   } public GmbalMBean createRoot(Object root, String name) {
/* 394 */     return this.mom.createRoot(root, rewrite(name));
/*     */   } public Object getRoot() {
/* 396 */     return this.mom.getRoot();
/*     */   } public GmbalMBean register(Object parent, Object obj, String name) {
/* 398 */     return this.mom.register(parent, obj, rewrite(name));
/*     */   } public GmbalMBean register(Object parent, Object obj) {
/* 400 */     return this.mom.register(parent, obj);
/*     */   } public GmbalMBean registerAtRoot(Object obj, String name) {
/* 402 */     return this.mom.registerAtRoot(obj, rewrite(name));
/*     */   }
/* 404 */   public GmbalMBean registerAtRoot(Object obj) { return this.mom.registerAtRoot(obj); }
/* 405 */   public void unregister(Object obj) { this.mom.unregister(obj); }
/* 406 */   public ObjectName getObjectName(Object obj) { return this.mom.getObjectName(obj); }
/* 407 */   public AMXClient getAMXClient(Object obj) { return this.mom.getAMXClient(obj); }
/* 408 */   public Object getObject(ObjectName oname) { return this.mom.getObject(oname); }
/* 409 */   public void stripPrefix(String... str) { this.mom.stripPrefix(str); }
/* 410 */   public void stripPackagePrefix() { this.mom.stripPackagePrefix(); }
/* 411 */   public String getDomain() { return this.mom.getDomain(); }
/* 412 */   public void setMBeanServer(MBeanServer server) { this.mom.setMBeanServer(server); }
/* 413 */   public MBeanServer getMBeanServer() { return this.mom.getMBeanServer(); }
/* 414 */   public void setResourceBundle(ResourceBundle rb) { this.mom.setResourceBundle(rb); }
/* 415 */   public ResourceBundle getResourceBundle() { return this.mom.getResourceBundle(); }
/* 416 */   public void addAnnotation(AnnotatedElement element, Annotation annotation) { this.mom.addAnnotation(element, annotation); }
/* 417 */   public void setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel level) { this.mom.setRegistrationDebug(level); }
/* 418 */   public void setRuntimeDebug(boolean flag) { this.mom.setRuntimeDebug(flag); }
/* 419 */   public void setTypelibDebug(int level) { this.mom.setTypelibDebug(level); }
/* 420 */   public String dumpSkeleton(Object obj) { return this.mom.dumpSkeleton(obj); }
/* 421 */   public void suppressDuplicateRootReport(boolean suppressReport) { this.mom.suppressDuplicateRootReport(suppressReport); }
/* 422 */   public void close() throws IOException { this.mom.close(); }
/* 423 */   public void setJMXRegistrationDebug(boolean x) { this.mom.setJMXRegistrationDebug(x); } public boolean isManagedObject(Object x) {
/* 424 */     return this.mom.isManagedObject(x);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\RewritingMOM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */