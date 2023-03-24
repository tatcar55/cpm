/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeChangeNotification;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
/*     */ import org.glassfish.gmbal.GmbalMBean;
/*     */ import org.glassfish.gmbal.generic.FacetAccessor;
/*     */ import org.glassfish.gmbal.generic.FacetAccessorImpl;
/*     */ import org.glassfish.gmbal.generic.OperationTracer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanImpl
/*     */   extends NotificationBroadcasterSupport
/*     */   implements FacetAccessor, GmbalMBean
/*     */ {
/*     */   private boolean registered;
/*     */   private final MBeanSkeleton skel;
/*     */   private final String type;
/*     */   private String name;
/*     */   private ObjectName oname;
/*     */   private MBeanImpl parent;
/*     */   private final Set<String> subTypes;
/*     */   private Map<String, Map<String, MBeanImpl>> children;
/*     */   private Object target;
/*     */   private MBeanServer server;
/*     */   private String parentPathForObjectName;
/*     */   private boolean suspended;
/*     */   
/*     */   public MBeanImpl(MBeanSkeleton skel, Object obj, MBeanServer server, String type) {
/* 427 */     this.facetAccessorDelegate = (FacetAccessor)new FacetAccessorImpl(this); this.registered = false; this.skel = skel; this.type = type; this.name = ""; this.oname = null; this.parent = null; this.children = new HashMap<String, Map<String, MBeanImpl>>(); this.target = obj; String[] stypes = skel.getMBeanType().subTypes(); if (stypes.length > 0) { this.subTypes = new HashSet<String>(Arrays.asList(stypes)); } else { this.subTypes = null; }  addFacet(obj); addFacet(new AMXImpl(this)); this.server = server; this.parentPathForObjectName = null; this.suspended = false;
/*     */   }
/*     */   public synchronized boolean equals(Object obj) { if (this == obj) return true;  if (!(obj instanceof MBeanImpl)) return false;  MBeanImpl other = (MBeanImpl)obj; return (this.parent == other.parent() && this.name.equals(other.name()) && this.type.equals(other.type())); }
/*     */   public synchronized int hashCode() { if (this.parent == null) return this.name.hashCode() ^ this.type.hashCode();  return this.name.hashCode() ^ this.type.hashCode() ^ this.parent.hashCode(); }
/* 431 */   public String toString() { return "MBeanImpl[type=" + this.type + ",name=" + this.name + ",oname=" + this.oname + "]"; } public MBeanSkeleton skeleton() { return this.skel; } public String type() { return this.type; } public Object target() { return this.target; } public synchronized String name() { return this.name; } public synchronized void name(String str) { this.name = str; } public synchronized ObjectName objectName() { return this.oname; } public synchronized void objectName(ObjectName oname) { this.oname = oname; } public synchronized MBeanImpl parent() { return this.parent; } public synchronized void parent(MBeanImpl entity) { if (this.parent == null) { this.parent = entity; } else { throw Exceptions.self.nodeAlreadyHasParent(entity); }  } public synchronized Map<String, Map<String, MBeanImpl>> children() { Map<String, Map<String, MBeanImpl>> result = new HashMap<String, Map<String, MBeanImpl>>(); for (Map.Entry<String, Map<String, MBeanImpl>> entry : this.children.entrySet()) result.put(entry.getKey(), Collections.unmodifiableMap(new HashMap<String, MBeanImpl>(entry.getValue())));  return Collections.unmodifiableMap(result); } public synchronized void addChild(MBeanImpl child) { child.parent(this); if (this.subTypes != null && !this.subTypes.contains(child.type())) throw Exceptions.self.invalidSubtypeOfParent(this.oname, this.subTypes, child.objectName(), child.type());  Map<String, MBeanImpl> map = this.children.get(child.type()); if (map == null) { map = new HashMap<String, MBeanImpl>(); this.children.put(child.type(), map); }  boolean isSingleton = child.skeleton().getMBeanType().isSingleton(); if (isSingleton && map.size() > 0) throw Exceptions.self.childMustBeSingleton(this.oname, child.type(), child.objectName());  map.put(child.name(), child); } public synchronized void removeChild(MBeanImpl child) { Map<String, MBeanImpl> map = this.children.get(child.type()); if (map != null) { map.remove(child.name()); if (map.size() == 0) this.children.remove(child.type());  }  } public <T> T facet(Class<T> cls, boolean debug) { return (T)this.facetAccessorDelegate.facet(cls, debug); } private void restNameHelper(StringBuilder sb) { if (parent() != null) { parent().restNameHelper(sb); sb.append('/'); }  sb.append(type()); if (!this.name.equals("")) { sb.append('['); sb.append(this.name); sb.append(']'); }  } private synchronized String restName() { StringBuilder sb = new StringBuilder(60); restNameHelper(sb); return sb.toString(); } public synchronized String getParentPathPart(String rootParentPrefix) { if (this.parentPathForObjectName == null) { String qname; StringBuilder result = new StringBuilder(); result.append("pp"); result.append("="); if (rootParentPrefix == null) { qname = "/" + restName(); } else { qname = rootParentPrefix + "/" + restName(); }  result.append(MBeanTree.getQuotedName(qname)); result.append(','); this.parentPathForObjectName = result.toString(); }  return this.parentPathForObjectName; } public synchronized boolean suspended() { return this.suspended; } public synchronized void suspended(boolean flag) { this.suspended = flag; } public synchronized void register() throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException { if (!this.registered) { if (skeleton().mom().jmxRegistrationDebug()) Exceptions.self.registeringMBean(this.oname);  try { AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */               public Object run() throws Exception { MBeanImpl.this.server.registerMBean(MBeanImpl.this, MBeanImpl.this.oname); return null; }
/*     */             }); } catch (PrivilegedActionException exc) { Throwable e = exc.getCause(); if (e instanceof InstanceAlreadyExistsException) throw (InstanceAlreadyExistsException)e;  if (e instanceof MBeanRegistrationException) throw (MBeanRegistrationException)e;  if (e instanceof NotCompliantMBeanException) throw (NotCompliantMBeanException)e;  Exceptions.self.unexpectedException("MBeanServer.registerMBean", e); }  this.registered = true; } else { Exceptions.self.registerMBeanRegistered(this.oname); }  } public synchronized void unregister() throws InstanceNotFoundException, MBeanRegistrationException { if (this.registered) { if (skeleton().mom().jmxRegistrationDebug()) Exceptions.self.unregisteringMBean(this.oname);  this.registered = false; try { AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */               public Object run() throws Exception { MBeanImpl.this.server.unregisterMBean(MBeanImpl.this.oname); return null; }
/* 435 */             }); } catch (PrivilegedActionException exc) { Throwable e = exc.getCause(); if (e instanceof InstanceNotFoundException) throw (InstanceNotFoundException)e;  if (e instanceof MBeanRegistrationException) throw (MBeanRegistrationException)e;  Exceptions.self.unexpectedException("MBeanServer.unregisterMBean", e); }  } else { Exceptions.self.unregisterMBeanNotRegistered(this.oname); }  } public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException { OperationTracer.clear(); return this.skel.getAttribute(this, attribute); } public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException { OperationTracer.clear(); this.skel.setAttribute(this, this, attribute); } public AttributeList getAttributes(String[] attributes) { OperationTracer.clear(); return this.skel.getAttributes(this, attributes); } public AttributeList setAttributes(AttributeList attributes) { OperationTracer.clear(); return this.skel.setAttributes(this, this, attributes); } public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException { OperationTracer.clear(); return this.skel.invoke(this, actionName, params, signature); } private static final MBeanNotificationInfo[] ATTRIBUTE_CHANGE_NOTIFICATION_INFO = new MBeanNotificationInfo[] { new MBeanNotificationInfo(new String[] { "jmx.attribute.change" }, AttributeChangeNotification.class.getName(), "An Attribute of this MBean has changed") }; private FacetAccessor facetAccessorDelegate; public MBeanNotificationInfo[] getNotificationInfo() { return (MBeanNotificationInfo[])ATTRIBUTE_CHANGE_NOTIFICATION_INFO.clone(); } public MBeanInfo getMBeanInfo() { return this.skel.getMBeanInfo(); } public <T> void addFacet(T obj) { this.facetAccessorDelegate.addFacet(obj); }
/*     */ 
/*     */   
/*     */   public void removeFacet(Class<?> cls) {
/* 439 */     this.facetAccessorDelegate.removeFacet(cls);
/*     */   }
/*     */   
/*     */   public Object invoke(Method method, boolean debug, Object... args) {
/* 443 */     return this.facetAccessorDelegate.invoke(method, debug, args);
/*     */   }
/*     */   
/*     */   public Collection<Object> facets() {
/* 447 */     return this.facetAccessorDelegate.facets();
/*     */   }
/*     */   
/*     */   public Object get(Field field, boolean debug) {
/* 451 */     return this.facetAccessorDelegate.get(field, debug);
/*     */   }
/*     */   
/*     */   public void set(Field field, Object value, boolean debug) {
/* 455 */     this.facetAccessorDelegate.set(field, value, debug);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\MBeanImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */