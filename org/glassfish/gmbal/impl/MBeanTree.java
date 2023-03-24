/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.gmbal.GmbalMBean;
/*     */ import org.glassfish.gmbal.generic.FacetAccessor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanTree
/*     */ {
/*  64 */   private Object root = null;
/*  65 */   private MBeanImpl rootEntity = null;
/*     */   private Map<Object, MBeanImpl> objectMap;
/*     */   private Map<ObjectName, Object> objectNameMap;
/*     */   private String domain;
/*     */   private ObjectName rootParentName;
/*     */   private String rootParentPrefix;
/*     */   private String nullParentsParentPath;
/*     */   private String typeString;
/*     */   private ManagedObjectManagerInternal mom;
/*     */   private MethodMonitor mm;
/*     */   private JMXRegistrationManager jrm;
/*     */   private boolean suppressReport = false;
/*     */   private Map<String, String> typePartMap;
/*     */   
/*     */   private void addToObjectMaps(MBeanImpl mbean) {
/*  80 */     ObjectName oname = mbean.objectName();
/*  81 */     for (Object obj : mbean.facets()) {
/*  82 */       this.objectMap.put(obj, mbean);
/*     */     }
/*  84 */     this.objectNameMap.put(oname, mbean.target());
/*     */   }
/*     */   
/*     */   private void removeFromObjectMaps(MBeanImpl mbean) {
/*  88 */     ObjectName oname = mbean.objectName();
/*  89 */     for (Object obj : mbean.facets()) {
/*  90 */       this.objectMap.remove(obj);
/*     */     }
/*     */     
/*  93 */     this.objectNameMap.remove(oname);
/*     */   }
/*     */   
/*     */   public synchronized GmbalMBean setRoot(Object root, String rootName) {
/*     */     ObjectName oname;
/*  98 */     MBeanImpl rootMB = this.mom.constructMBean(null, root, rootName);
/*     */ 
/*     */     
/*     */     try {
/* 102 */       oname = objectName(null, rootMB.type(), rootMB.name());
/* 103 */     } catch (MalformedObjectNameException ex) {
/* 104 */       throw Exceptions.self.noRootObjectName(ex);
/*     */     } 
/* 106 */     rootMB.objectName(oname);
/*     */     
/* 108 */     addToObjectMaps(rootMB);
/* 109 */     this.root = root;
/* 110 */     this.rootEntity = rootMB;
/* 111 */     boolean success = false;
/*     */     
/*     */     try {
/* 114 */       this.jrm.setRoot(rootMB);
/* 115 */       success = true;
/* 116 */     } catch (InstanceAlreadyExistsException ex) {
/* 117 */       if (this.suppressReport) {
/* 118 */         return null;
/*     */       }
/* 120 */       throw Exceptions.self.rootRegisterFail(ex, oname);
/* 121 */     } catch (Exception ex) {
/* 122 */       throw Exceptions.self.rootRegisterFail(ex, oname);
/*     */     } finally {
/* 124 */       if (!success) {
/* 125 */         removeFromObjectMaps(rootMB);
/* 126 */         this.root = null;
/* 127 */         this.rootEntity = null;
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return rootMB;
/*     */   }
/*     */   
/*     */   public synchronized Object getRoot() {
/* 135 */     return this.root;
/*     */   }
/*     */   
/*     */   private String parentPath(ObjectName rootParentName) {
/* 139 */     String prefix, pp = rootParentName.getKeyProperty("pp");
/* 140 */     String type = rootParentName.getKeyProperty("type");
/* 141 */     String name = rootParentName.getKeyProperty("name");
/*     */     
/* 143 */     if (pp == null) {
/* 144 */       throw Exceptions.self.ppNullInRootParent();
/*     */     }
/*     */     
/* 147 */     if (type == null) {
/* 148 */       throw Exceptions.self.typeNullInRootParent();
/*     */     }
/*     */ 
/*     */     
/* 152 */     if (pp.equals("/")) {
/* 153 */       prefix = pp;
/*     */     }
/* 155 */     else if (pp.endsWith("/")) {
/* 156 */       prefix = pp;
/*     */     } else {
/* 158 */       prefix = pp + "/";
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (name == null) {
/* 163 */       return prefix + type;
/*     */     }
/* 165 */     return prefix + type + '[' + name + ']';
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
/*     */   synchronized void suspendRegistration() {
/* 194 */     this.jrm.suspendRegistration();
/*     */   }
/*     */   
/*     */   synchronized void resumeRegistration() {
/* 198 */     this.jrm.resumeRegistration();
/*     */   }
/*     */   
/*     */   public synchronized FacetAccessor getFacetAccessor(Object obj) {
/* 202 */     return this.objectMap.get(obj);
/*     */   }
/*     */   
/*     */   private void checkCorrectRoot(MBeanImpl entity) {
/* 206 */     MBeanImpl current = entity;
/*     */     do {
/* 208 */       if (current == this.rootEntity) {
/*     */         return;
/*     */       }
/*     */       
/* 212 */       current = current.parent();
/* 213 */     } while (current != null);
/*     */     
/* 215 */     throw Exceptions.self.notPartOfThisTree(entity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getQuotedName(String name) {
/* 225 */     StringBuilder buf = new StringBuilder(name.length() + 10);
/* 226 */     buf.append('"');
/* 227 */     int len = name.length();
/* 228 */     boolean needsQuotes = false;
/* 229 */     for (int i = 0; i < len; i++) {
/* 230 */       char c = name.charAt(i);
/* 231 */       switch (c) {
/*     */         case '\n':
/* 233 */           c = 'n';
/* 234 */           buf.append('\\');
/* 235 */           needsQuotes = true;
/*     */           break;
/*     */         
/*     */         case '"':
/*     */         case '*':
/*     */         case '?':
/*     */         case '\\':
/* 242 */           buf.append('\\');
/* 243 */           needsQuotes = true;
/*     */           break;
/*     */         
/*     */         case ',':
/*     */         case ':':
/*     */         case '=':
/* 249 */           needsQuotes = true;
/*     */           break;
/*     */       } 
/* 252 */       buf.append(c);
/*     */     } 
/*     */     
/* 255 */     if (needsQuotes) {
/* 256 */       buf.append('"');
/* 257 */       return buf.toString();
/*     */     } 
/* 259 */     return name;
/*     */   }
/*     */   
/*     */   public MBeanTree(ManagedObjectManagerInternal mom, String domain, ObjectName rootParentName, String typeString) {
/* 263 */     this.typePartMap = new WeakHashMap<String, String>(); this.mom = mom; this.domain = domain; this.rootParentName = rootParentName; if (rootParentName == null) { this.rootParentPrefix = null; this.nullParentsParentPath = "pp=/,"; }
/*     */     else
/*     */     { this.rootParentPrefix = parentPath(rootParentName); this.nullParentsParentPath = "pp=" + this.rootParentPrefix + ","; }
/* 266 */      this.typeString = typeString; this.objectMap = new HashMap<Object, MBeanImpl>(); this.objectNameMap = new HashMap<ObjectName, Object>(); this.mm = MethodMonitorFactory.makeStandard(getClass()); this.jrm = new JMXRegistrationManager(mom, rootParentName); } private synchronized String getTypePart(String type) { String result = this.typePartMap.get(type);
/* 267 */     if (result == null) {
/* 268 */       StringBuilder sb = new StringBuilder();
/* 269 */       sb.append(this.typeString);
/* 270 */       sb.append("=");
/* 271 */       sb.append(getQuotedName(type));
/* 272 */       result = sb.toString();
/*     */       
/* 274 */       this.typePartMap.put(type, result);
/*     */     } 
/*     */     
/* 277 */     return result; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ObjectName objectName(MBeanImpl parent, String type, String name) throws MalformedObjectNameException {
/* 283 */     this.mm.enter(this.mom.registrationDebug(), "objectName", new Object[] { parent, type, name });
/*     */ 
/*     */     
/* 286 */     ObjectName oname = null;
/*     */     try {
/*     */       String ppPart;
/* 289 */       if (parent != null) {
/* 290 */         checkCorrectRoot(parent);
/*     */       }
/*     */       
/* 293 */       StringBuilder result = new StringBuilder();
/*     */       
/* 295 */       result.append(this.domain);
/* 296 */       result.append(":");
/*     */ 
/*     */ 
/*     */       
/* 300 */       if (parent == null) {
/* 301 */         ppPart = this.nullParentsParentPath;
/*     */       } else {
/* 303 */         ppPart = parent.getParentPathPart(this.rootParentPrefix);
/*     */       } 
/*     */       
/* 306 */       this.mm.info(this.mom.registrationDebug(), new Object[] { "ppPart", ppPart });
/* 307 */       result.append(ppPart);
/*     */ 
/*     */       
/* 310 */       String typePart = getTypePart(type);
/* 311 */       this.mm.info(this.mom.registrationDebug(), new Object[] { "typePart", typePart });
/* 312 */       result.append(typePart);
/*     */ 
/*     */       
/* 315 */       if (name.length() > 0) {
/* 316 */         result.append(',');
/* 317 */         result.append("name");
/* 318 */         result.append("=");
/* 319 */         result.append(getQuotedName(name));
/*     */       } 
/*     */       
/* 322 */       String on = result.toString();
/*     */       try {
/* 324 */         oname = new ObjectName(on);
/* 325 */       } catch (MalformedObjectNameException exc) {
/* 326 */         throw Exceptions.self.malformedObjectName(exc, on);
/*     */       } 
/*     */     } finally {
/* 329 */       this.mm.exit(this.mom.registrationDebug(), oname);
/*     */     } 
/*     */     
/* 332 */     return oname;
/*     */   }
/*     */   
/*     */   public MBeanImpl getParentEntity(Object parent) {
/* 336 */     if (parent == null) {
/* 337 */       throw Exceptions.self.parentCannotBeNull();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 342 */     MBeanImpl parentEntity = this.objectMap.get(parent);
/* 343 */     if (parentEntity == null) {
/* 344 */       throw Exceptions.self.parentNotFound(parent);
/*     */     }
/*     */     
/* 347 */     return parentEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized GmbalMBean register(MBeanImpl parentEntity, Object obj, MBeanImpl mb) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
/* 357 */     this.mm.enter(this.mom.registrationDebug(), "register", new Object[] { parentEntity, obj, mb });
/*     */     
/*     */     try {
/* 360 */       MBeanImpl oldMB = this.objectMap.get(obj);
/* 361 */       if (oldMB != null) {
/* 362 */         throw Exceptions.self.objectAlreadyRegistered(obj, oldMB);
/*     */       }
/*     */       
/* 365 */       ObjectName oname = objectName(parentEntity, mb.type(), mb.name());
/*     */       
/* 367 */       mb.objectName(oname);
/*     */       
/* 369 */       Object oldObj = this.objectNameMap.get(oname);
/* 370 */       if (oldObj != null) {
/* 371 */         throw Exceptions.self.objectAlreadyRegistered(obj, (MBeanImpl)this.objectMap.get(oldObj));
/*     */       }
/*     */ 
/*     */       
/* 375 */       addToObjectMaps(mb);
/*     */       
/* 377 */       parentEntity.addChild(mb);
/*     */       
/* 379 */       this.jrm.register(mb);
/*     */       
/* 381 */       return mb;
/*     */     } finally {
/* 383 */       this.mm.exit(this.mom.registrationDebug());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void unregister(Object obj) throws InstanceNotFoundException, MBeanRegistrationException {
/* 389 */     if (obj == this.root) {
/* 390 */       this.root = null;
/* 391 */       this.rootEntity = null;
/*     */     } 
/*     */     
/* 394 */     MBeanImpl mb = this.objectMap.get(obj);
/* 395 */     if (mb == null) {
/* 396 */       throw Exceptions.self.objectNotFound(obj);
/*     */     }
/*     */     
/* 399 */     for (Map<String, MBeanImpl> nameToMBean : mb.children().values()) {
/* 400 */       for (MBeanImpl child : nameToMBean.values()) {
/* 401 */         unregister(child.target());
/*     */       }
/*     */     } 
/*     */     
/* 405 */     removeFromObjectMaps(mb);
/* 406 */     this.jrm.unregister(mb);
/*     */     
/* 408 */     if (mb.parent() != null) {
/* 409 */       mb.parent().removeChild(mb);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ObjectName getObjectName(Object obj) {
/* 418 */     if (obj instanceof MBeanImpl) {
/* 419 */       return ((MBeanImpl)obj).objectName();
/*     */     }
/*     */ 
/*     */     
/* 423 */     MBeanImpl result = this.objectMap.get(obj);
/* 424 */     if (result != null) {
/* 425 */       return result.objectName();
/*     */     }
/* 427 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Object getObject(ObjectName oname) {
/* 432 */     return this.objectNameMap.get(oname);
/*     */   }
/*     */   
/*     */   public synchronized MBeanImpl getMBeanImpl(Object obj) {
/* 436 */     return this.objectMap.get(obj);
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/* 440 */     if (this.root != null) {
/*     */       try {
/* 442 */         unregister(this.root);
/* 443 */       } catch (InstanceNotFoundException ex) {
/* 444 */         Exceptions.self.shouldNotHappen(ex);
/* 445 */       } catch (MBeanRegistrationException ex) {
/* 446 */         Exceptions.self.shouldNotHappen(ex);
/*     */       } 
/*     */     }
/*     */     
/* 450 */     this.objectMap.clear();
/* 451 */     this.objectNameMap.clear();
/* 452 */     this.rootEntity = null;
/* 453 */     this.jrm.clear();
/*     */   }
/*     */   
/*     */   public ObjectName getRootParentName() {
/* 457 */     return this.rootParentName;
/*     */   }
/*     */   
/*     */   synchronized void setSuppressDuplicateSetRootReport(boolean suppressReport) {
/* 461 */     this.suppressReport = suppressReport;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\MBeanTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */