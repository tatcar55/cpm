/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.JMException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.external.amx.MBeanListener;
/*     */ import org.glassfish.gmbal.GmbalException;
/*     */ import org.glassfish.gmbal.generic.UnaryVoidFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMXRegistrationManager
/*     */ {
/*     */   private int suspendCount;
/*     */   private final ManagedObjectManagerInternal mom;
/*     */   private final ObjectName rootParentName;
/*  72 */   final Object lock = new Object();
/*     */ 
/*     */   
/*     */   private final LinkedHashSet<MBeanImpl> deferredRegistrations;
/*     */ 
/*     */   
/*     */   MBeanImpl root;
/*     */   
/*     */   boolean isJMXRegistrationEnabled;
/*     */   
/*     */   private RootParentListener callback;
/*     */   
/*     */   private MBeanListener rpListener;
/*     */ 
/*     */   
/*     */   public JMXRegistrationManager(ManagedObjectManagerInternal mom, ObjectName rootParentName) {
/*  88 */     this.suspendCount = 0;
/*  89 */     this.mom = mom;
/*  90 */     this.rootParentName = rootParentName;
/*  91 */     this.deferredRegistrations = new LinkedHashSet<MBeanImpl>();
/*  92 */     this.root = null;
/*  93 */     this.isJMXRegistrationEnabled = false;
/*  94 */     this.callback = null;
/*  95 */     this.rpListener = null;
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
/*     */   public void setRoot(MBeanImpl root) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 108 */     synchronized (this.lock) {
/* 109 */       this.root = root;
/* 110 */       if (this.rootParentName == null) {
/* 111 */         this.isJMXRegistrationEnabled = true;
/* 112 */         register(root);
/*     */       }
/*     */       else {
/*     */         
/* 116 */         if (this.suspendCount > 0) {
/* 117 */           this.deferredRegistrations.add(root);
/* 118 */           root.suspended(true);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         this.callback = new RootParentListener();
/* 126 */         this.rpListener = new MBeanListener(this.mom.getMBeanServer(), this.rootParentName, this.callback);
/*     */         
/* 128 */         this.rpListener.startListening();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void clear() {
/* 135 */     synchronized (this.lock) {
/* 136 */       this.root = null;
/* 137 */       this.isJMXRegistrationEnabled = false;
/*     */       
/* 139 */       if (this.rpListener != null) {
/* 140 */         this.rpListener.stopListening();
/*     */       }
/* 142 */       this.rpListener = null;
/* 143 */       this.callback = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspendRegistration() {
/* 151 */     synchronized (this.lock) {
/* 152 */       this.suspendCount++;
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
/*     */   public void resumeRegistration() {
/* 164 */     synchronized (this.lock) {
/* 165 */       this.suspendCount--;
/* 166 */       if (this.suspendCount == 0) {
/* 167 */         for (MBeanImpl mb : this.deferredRegistrations) {
/*     */           try {
/* 169 */             if (this.isJMXRegistrationEnabled) {
/* 170 */               mb.register();
/*     */             }
/* 172 */             mb.suspended(false);
/* 173 */           } catch (JMException ex) {
/* 174 */             Exceptions.self.deferredRegistrationException(ex, mb);
/*     */           } 
/*     */         } 
/*     */         
/* 178 */         this.deferredRegistrations.clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(MBeanImpl mb) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/* 197 */     synchronized (this.lock) {
/* 198 */       if (this.suspendCount > 0) {
/* 199 */         this.deferredRegistrations.add(mb);
/* 200 */         mb.suspended(true);
/*     */       }
/* 202 */       else if (this.isJMXRegistrationEnabled) {
/* 203 */         mb.register();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(MBeanImpl mb) throws InstanceNotFoundException, MBeanRegistrationException {
/* 222 */     synchronized (this.lock) {
/* 223 */       boolean wasSuspended = mb.suspended();
/*     */       
/* 225 */       if (wasSuspended) {
/* 226 */         this.deferredRegistrations.remove(mb);
/* 227 */         mb.suspended(false);
/*     */       }
/* 229 */       else if (this.isJMXRegistrationEnabled) {
/* 230 */         mb.unregister();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class RootParentListener
/*     */     implements MBeanListener.Callback
/*     */   {
/*     */     private void traverse(MBeanImpl mb, UnaryVoidFunction<MBeanImpl> pre, UnaryVoidFunction<MBeanImpl> post) {
/* 242 */       if (pre != null) {
/* 243 */         pre.evaluate(mb);
/*     */       }
/*     */       
/* 246 */       for (Map<String, MBeanImpl> nameToMBean : mb.children().values()) {
/* 247 */         for (MBeanImpl child : nameToMBean.values()) {
/* 248 */           traverse(child, pre, post);
/*     */         }
/*     */       } 
/*     */       
/* 252 */       if (post != null) {
/* 253 */         post.evaluate(mb);
/*     */       }
/*     */     }
/*     */     
/* 257 */     private final UnaryVoidFunction<MBeanImpl> REGISTER_FUNC = new UnaryVoidFunction<MBeanImpl>()
/*     */       {
/*     */         public void evaluate(MBeanImpl arg) {
/* 260 */           if (!arg.suspended()) {
/*     */             try {
/* 262 */               arg.register();
/* 263 */             } catch (Exception ex) {
/* 264 */               throw new GmbalException("Registration exception", ex);
/*     */             } 
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*     */     public void mbeanRegistered(ObjectName arg0, MBeanListener arg1) {
/* 271 */       synchronized (JMXRegistrationManager.this.lock) {
/* 272 */         if (!JMXRegistrationManager.this.isJMXRegistrationEnabled) {
/* 273 */           JMXRegistrationManager.this.isJMXRegistrationEnabled = true;
/*     */           
/* 275 */           if (JMXRegistrationManager.this.root != null) {
/* 276 */             traverse(JMXRegistrationManager.this.root, this.REGISTER_FUNC, null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 282 */     private final UnaryVoidFunction<MBeanImpl> UNREGISTER_FUNC = new UnaryVoidFunction<MBeanImpl>()
/*     */       {
/*     */         public void evaluate(MBeanImpl arg) {
/* 285 */           if (!arg.suspended()) {
/*     */             try {
/* 287 */               arg.unregister();
/* 288 */             } catch (Exception ex) {
/* 289 */               throw new GmbalException("Registration exception", ex);
/*     */             } 
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*     */     public void mbeanUnregistered(ObjectName arg0, MBeanListener arg1) {
/* 296 */       synchronized (JMXRegistrationManager.this.lock) {
/* 297 */         if (JMXRegistrationManager.this.isJMXRegistrationEnabled) {
/* 298 */           JMXRegistrationManager.this.isJMXRegistrationEnabled = false;
/*     */           
/* 300 */           if (JMXRegistrationManager.this.root != null)
/* 301 */             traverse(JMXRegistrationManager.this.root, null, this.UNREGISTER_FUNC); 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private RootParentListener() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\JMXRegistrationManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */