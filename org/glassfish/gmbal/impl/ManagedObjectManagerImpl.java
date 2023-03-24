/*      */ package org.glassfish.gmbal.impl;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.management.ManagementFactory;
/*      */ import java.lang.reflect.AnnotatedElement;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeSet;
/*      */ import java.util.WeakHashMap;
/*      */ import javax.management.JMException;
/*      */ import javax.management.MBeanAttributeInfo;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.ObjectName;
/*      */ import org.glassfish.external.statistics.AverageRangeStatistic;
/*      */ import org.glassfish.external.statistics.BoundaryStatistic;
/*      */ import org.glassfish.external.statistics.BoundedRangeStatistic;
/*      */ import org.glassfish.external.statistics.CountStatistic;
/*      */ import org.glassfish.external.statistics.RangeStatistic;
/*      */ import org.glassfish.external.statistics.Statistic;
/*      */ import org.glassfish.external.statistics.StringStatistic;
/*      */ import org.glassfish.external.statistics.TimeStatistic;
/*      */ import org.glassfish.gmbal.AMXClient;
/*      */ import org.glassfish.gmbal.AMXMBeanInterface;
/*      */ import org.glassfish.gmbal.AMXMetadata;
/*      */ import org.glassfish.gmbal.Description;
/*      */ import org.glassfish.gmbal.GmbalMBean;
/*      */ import org.glassfish.gmbal.IncludeSubclass;
/*      */ import org.glassfish.gmbal.InheritedAttribute;
/*      */ import org.glassfish.gmbal.InheritedAttributes;
/*      */ import org.glassfish.gmbal.ManagedAttribute;
/*      */ import org.glassfish.gmbal.ManagedData;
/*      */ import org.glassfish.gmbal.ManagedObject;
/*      */ import org.glassfish.gmbal.ManagedObjectManager;
/*      */ import org.glassfish.gmbal.generic.Algorithms;
/*      */ import org.glassfish.gmbal.generic.ClassAnalyzer;
/*      */ import org.glassfish.gmbal.generic.DelayedObjectToString;
/*      */ import org.glassfish.gmbal.generic.DumpIgnore;
/*      */ import org.glassfish.gmbal.generic.FacetAccessor;
/*      */ import org.glassfish.gmbal.generic.FacetAccessorImpl;
/*      */ import org.glassfish.gmbal.generic.MethodMonitor;
/*      */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*      */ import org.glassfish.gmbal.generic.ObjectUtility;
/*      */ import org.glassfish.gmbal.generic.Pair;
/*      */ import org.glassfish.gmbal.generic.Predicate;
/*      */ import org.glassfish.gmbal.generic.UnaryFunction;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedAccessibleDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedClassAnalyzer;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedClassDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedFieldDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedMethodDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedType;
/*      */ import org.glassfish.gmbal.typelib.TypeEvaluator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ManagedObjectManagerImpl
/*      */   implements ManagedObjectManagerInternal
/*      */ {
/*      */   @AMXMetadata
/*      */   static class DefaultAMXMetadataHolder {}
/*      */   
/*  135 */   private static final AMXMetadata DEFAULT_AMX_METADATA = DefaultAMXMetadataHolder.class.<AMXMetadata>getAnnotation(AMXMetadata.class);
/*      */ 
/*      */   
/*  138 */   private static ObjectUtility myObjectUtil = (new ObjectUtility(true, 0, 4)).useToString(EvaluatedType.class).useToString(ManagedObjectManager.class);
/*      */   
/*      */   private static final class StringComparator
/*      */     implements Serializable, Comparator<String> {
/*      */     private static final long serialVersionUID = 8274851916877850245L;
/*      */     
/*      */     private StringComparator() {}
/*      */     
/*      */     public int compare(String o1, String o2) {
/*  147 */       return -o1.compareTo(o2);
/*      */     }
/*      */   }
/*  150 */   private static Comparator<String> REV_COMP = new StringComparator();
/*      */   
/*      */   @DumpIgnore
/*      */   private final MethodMonitor mm;
/*      */   
/*      */   private final String domain;
/*      */   
/*      */   private final MBeanTree tree;
/*      */   
/*      */   private final Map<EvaluatedClassDeclaration, MBeanSkeleton> skeletonMap;
/*      */   
/*      */   private final Map<EvaluatedType, TypeConverter> typeConverterMap;
/*      */   
/*      */   private final Map<AnnotatedElement, Map<Class, Annotation>> addedAnnotations;
/*      */   
/*      */   private final MBeanSkeleton amxSkeleton;
/*      */   private final Set<String> amxAttributeNames;
/*      */   private boolean rootCreated;
/*      */   private ResourceBundle resourceBundle;
/*      */   private MBeanServer server;
/*      */   private ManagedObjectManager.RegistrationDebugLevel regDebugLevel;
/*      */   private boolean runDebugFlag;
/*      */   private boolean jmxRegistrationDebugFlag;
/*  173 */   private final SortedSet<String> typePrefixes = new TreeSet<String>(REV_COMP); private boolean stripPackagePrefix = false; List<Pair<Class, Class>> statsData; private Map<AnnotatedElement, Map<Class, Annotation>> annotationCache; private void addAnnotationIfNotNull(AnnotatedElement elemement, Annotation annotation) {
/*      */     if (annotation != null)
/*      */       addAnnotation(elemement, annotation); 
/*      */   } private void initializeStatisticsSupport() {
/*      */     for (Pair<Class<?>, Class<?>> pair : this.statsData) {
/*      */       Class dummy = (Class)pair.first();
/*      */       Class real = (Class)pair.second();
/*      */       addAnnotationIfNotNull(real, (Annotation)dummy.getAnnotation(ManagedData.class));
/*      */       addAnnotationIfNotNull(real, (Annotation)dummy.getAnnotation(Description.class));
/*      */       addAnnotationIfNotNull(real, (Annotation)dummy.getAnnotation(InheritedAttributes.class));
/*      */     } 
/*      */   } private void init() {
/*      */     this.server = AccessController.<MBeanServer>doPrivileged(new PrivilegedAction<MBeanServer>() { public MBeanServer run() {
/*      */             return ManagementFactory.getPlatformMBeanServer();
/*      */           } }
/*      */       );
/*      */     this.rootCreated = false;
/*      */     this.resourceBundle = null;
/*      */     this.regDebugLevel = ManagedObjectManager.RegistrationDebugLevel.NONE;
/*      */     this.runDebugFlag = false;
/*      */     this.jmxRegistrationDebugFlag = false;
/*      */     this.tree.clear();
/*      */     this.skeletonMap.clear();
/*      */     this.typeConverterMap.clear();
/*      */     this.addedAnnotations.clear();
/*      */     this.mm.clear();
/*      */     initializeStatisticsSupport();
/*      */   } public ManagedObjectManagerImpl(String domain) {
/*      */     this(domain, null);
/*      */     init();
/*      */   } public ManagedObjectManagerImpl(ObjectName rootParentName) {
/*      */     this(rootParentName.getDomain(), rootParentName);
/*      */     init();
/*      */   } public void close() throws IOException {
/*      */     this.mm.enter(registrationDebug(), "close", new Object[0]);
/*      */     try {
/*      */       init();
/*      */     } finally {
/*      */       this.mm.exit(registrationDebug());
/*      */     } 
/*      */   } private synchronized void checkRootNotCreated(String methodName) {
/*      */     if (this.rootCreated)
/*      */       throw Exceptions.self.createRootCalled(methodName); 
/*      */   } private synchronized void checkRootCreated(String methodName) {
/*      */     if (!this.rootCreated)
/*      */       throw Exceptions.self.createRootNotCalled(methodName); 
/*      */   } public synchronized void suspendJMXRegistration() {
/*      */     this.mm.clear();
/*      */     this.tree.suspendRegistration();
/*      */   } public synchronized void resumeJMXRegistration() {
/*      */     this.mm.clear();
/*      */     this.tree.resumeRegistration();
/*      */   } public synchronized void stripPackagePrefix() {
/*      */     this.mm.clear();
/*      */     checkRootNotCreated("stripPackagePrefix");
/*      */     this.stripPackagePrefix = true;
/*      */   } public String toString() {
/*      */     return "ManagedObjectManagerImpl[domain=" + this.domain + "]";
/*      */   } public synchronized ObjectName getRootParentName() {
/*      */     checkRootCreated("getRootParentName");
/*      */     return this.tree.getRootParentName();
/*      */   } @ManagedObject
/*      */   @AMXMetadata(type = "gmbal-root", isSingleton = true)
/*      */   @Description("Dummy class used when no root is specified")
/*      */   private static class Root {
/*      */     private Root() {} public String toString() {
/*      */       return "GmbalDefaultRoot";
/*      */     } } public synchronized GmbalMBean createRoot() {
/*      */     return createRoot(new Root());
/*      */   }
/*      */   public synchronized GmbalMBean createRoot(Object root) {
/*      */     return createRoot(root, null);
/*      */   }
/*      */   public synchronized GmbalMBean createRoot(Object root, String name) {
/*      */     GmbalMBean result;
/*      */     this.mm.clear();
/*      */     checkRootNotCreated("createRoot");
/*      */     try {
/*      */       this.rootCreated = true;
/*      */       result = this.tree.setRoot(root, name);
/*      */       if (result == null)
/*      */         this.rootCreated = false; 
/*      */     } catch (RuntimeException exc) {
/*      */       this.rootCreated = false;
/*      */       throw exc;
/*      */     } 
/*      */     return result;
/*      */   }
/*      */   public synchronized Object getRoot() {
/*      */     this.mm.clear();
/*      */     return this.tree.getRoot();
/*      */   }
/*      */   private synchronized MBeanSkeleton getSkeleton(EvaluatedClassDeclaration cls) {
/*      */     this.mm.enter(registrationDebug(), "getSkeleton", new Object[] { cls });
/*      */     try {
/*      */       MBeanSkeleton result = this.skeletonMap.get(cls);
/*      */       boolean newSkeleton = (result == null);
/*      */       if (newSkeleton) {
/*      */         this.mm.info(registrationDebug(), new Object[] { "Skeleton not found" });
/*      */         Pair<EvaluatedClassDeclaration, EvaluatedClassAnalyzer> pair = getClassAnalyzer(cls, (Class)ManagedObject.class);
/*      */         EvaluatedClassAnalyzer ca = (EvaluatedClassAnalyzer)pair.second();
/*      */         EvaluatedClassDeclaration annotatedClass = (EvaluatedClassDeclaration)pair.first();
/*      */         this.mm.info(registrationFineDebug(), new Object[] { "Annotated class for skeleton is", annotatedClass });
/*      */         if (annotatedClass == null)
/*      */           throw Exceptions.self.managedObjectAnnotationNotFound(cls.name()); 
/*      */         MBeanSkeleton skel = new MBeanSkeleton(cls, ca, this);
/*      */         if (this.amxSkeleton == null) {
/*      */           result = skel;
/*      */         } else {
/*      */           result = this.amxSkeleton.compose(skel);
/*      */         } 
/*      */         this.skeletonMap.put(cls, result);
/*      */       } 
/*      */       this.mm.info((registrationFineDebug() || (registrationDebug() && newSkeleton)), new Object[] { "Skeleton", new DelayedObjectToString(result, myObjectUtil) });
/*      */       return result;
/*      */     } finally {
/*      */       this.mm.exit(registrationDebug());
/*      */     } 
/*      */   }
/*  292 */   private ManagedObjectManagerImpl(String domain, ObjectName rootParentName) { this.statsData = Algorithms.list((Object[])new Pair[] { Algorithms.pair(DummyStringStatistic.class, StringStatistic.class), Algorithms.pair(DummyTimeStatistic.class, TimeStatistic.class), Algorithms.pair(DummyStatistic.class, Statistic.class), Algorithms.pair(DummyBoundaryStatistic.class, BoundaryStatistic.class), Algorithms.pair(DummyBoundedRangeStatistic.class, BoundedRangeStatistic.class), Algorithms.pair(DummyCountStatistic.class, CountStatistic.class), Algorithms.pair(DummyRangeStatistic.class, RangeStatistic.class), Algorithms.pair(DummyAverageRangeStatistic.class, AverageRangeStatistic.class) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  917 */     this.annotationCache = new WeakHashMap<AnnotatedElement, Map<Class, Annotation>>(); this.mm = MethodMonitorFactory.makeStandard(getClass()); this.domain = domain; this.tree = new MBeanTree(this, domain, rootParentName, "type"); this.skeletonMap = new WeakHashMap<EvaluatedClassDeclaration, MBeanSkeleton>(); this.typeConverterMap = new WeakHashMap<EvaluatedType, TypeConverter>(); this.addedAnnotations = new HashMap<AnnotatedElement, Map<Class, Annotation>>(); EvaluatedClassDeclaration ecd = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(AMXMBeanInterface.class); this.amxAttributeNames = new HashSet<String>(); this.amxSkeleton = getSkeleton(ecd); for (MBeanAttributeInfo mbi : this.amxSkeleton.getMBeanInfo().getAttributes()) this.amxAttributeNames.add(mbi.getName());  } public synchronized TypeConverter getTypeConverter(EvaluatedType type) { this.mm.enter(registrationFineDebug(), "getTypeConverter", new Object[] { type }); TypeConverter result = null; try { boolean newTypeConverter = false; result = this.typeConverterMap.get(type); if (result == null) { this.mm.info(registrationFineDebug(), new Object[] { "Creating new TypeConverter" }); this.typeConverterMap.put(type, new TypeConverterImpl.TypeConverterPlaceHolderImpl(type)); result = TypeConverterImpl.makeTypeConverter(type, this); this.typeConverterMap.put(type, result); newTypeConverter = true; }  this.mm.info((registrationFineDebug() || (registrationDebug() && newTypeConverter)), new Object[] { "result", myObjectUtil.objectToString(result) }); } finally { this.mm.exit(registrationFineDebug(), result); }  return result; } private static Field getDeclaredField(final Class<?> cls, final String name) throws PrivilegedActionException, NoSuchFieldException { SecurityManager sman = System.getSecurityManager(); if (sman == null) return cls.getDeclaredField(name);  return AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>() {
/*      */           public Field run() throws Exception { return cls.getDeclaredField(name); }
/*      */         }); } private String getAMXTypeFromField(Class<?> cls, String fieldName) { try { final Field fld = getDeclaredField(cls, fieldName); if (Modifier.isFinal(fld.getModifiers()) && Modifier.isStatic(fld.getModifiers()) && fld.getType().equals(String.class)) { AccessController.doPrivileged(new PrivilegedAction() {
/*      */               public Object run() { fld.setAccessible(true); return null; }
/*  921 */             }); return (String)fld.get(null); }  return ""; } catch (PrivilegedActionException ex) { return ""; } catch (IllegalArgumentException ex) { return ""; } catch (IllegalAccessException ex) { return ""; } catch (NoSuchFieldException ex) { return ""; } catch (SecurityException ex) { return ""; }  } private boolean goodResult(String str) { return (str != null && str.length() > 0); } public synchronized String getTypeName(Class<?> cls, String fieldName, String nameFromAnnotation) { String result = getAMXTypeFromField(cls, fieldName); if (goodResult(result)) return result;  if (goodResult(nameFromAnnotation)) return nameFromAnnotation;  String className = cls.getName(); for (String str : this.typePrefixes) { if (className.startsWith(str)) return className.substring(str.length() + 1);  }  if (this.stripPackagePrefix) { int lastDot = className.lastIndexOf('.'); if (lastDot == -1) return className;  return className.substring(lastDot + 1); }  return className; } private Map<Class, Annotation> getAllAnnotations(Class cls) { Map<Class<?>, Annotation> result = this.annotationCache.get(cls);
/*      */     
/*  923 */     if (result == null) {
/*  924 */       final Map<Class<?>, Annotation> res = new HashMap<Class<?>, Annotation>();
/*      */ 
/*      */       
/*  927 */       ClassAnalyzer ca = ClassAnalyzer.getClassAnalyzer(cls);
/*  928 */       ca.findClasses(new Predicate<Class>()
/*      */           {
/*      */             public boolean evaluate(Class arg) {
/*  931 */               Annotation[] annots = arg.getDeclaredAnnotations();
/*  932 */               for (Annotation anno : annots) {
/*  933 */                 ManagedObjectManagerImpl.this.putIfNotPresent(res, anno.annotationType(), anno);
/*      */               }
/*      */ 
/*      */               
/*  937 */               Map<Class<?>, Annotation> emap = (Map<Class<?>, Annotation>)ManagedObjectManagerImpl.this.addedAnnotations.get(arg);
/*  938 */               if (emap != null) {
/*  939 */                 for (Map.Entry<Class<?>, Annotation> entry : emap.entrySet()) {
/*  940 */                   ManagedObjectManagerImpl.this.putIfNotPresent(res, entry.getKey(), entry.getValue());
/*      */                 }
/*      */               }
/*      */ 
/*      */               
/*  945 */               return true;
/*      */             }
/*      */           });
/*      */       
/*  949 */       this.annotationCache.put(cls, res);
/*  950 */       result = res;
/*      */     } 
/*      */     
/*  953 */     return result; }
/*      */   public synchronized boolean isManagedObject(Object obj) { EvaluatedClassDeclaration cdecl = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(obj.getClass()); ManagedObject mo = getFirstAnnotationOnClass(cdecl, ManagedObject.class); return (mo != null); }
/*      */   public synchronized MBeanImpl constructMBean(MBeanImpl parentEntity, Object obj, String name) { MBeanImpl result = null; this.mm.enter(registrationDebug(), "constructMean", new Object[] { obj, name }); String objName = name; try { Class<?> cls = obj.getClass(); EvaluatedClassDeclaration cdecl = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(cls); MBeanSkeleton skel = getSkeleton(cdecl); AMXMetadata amd = getFirstAnnotationOnClass(cdecl, AMXMetadata.class); if (amd == null)
/*      */         amd = getDefaultAMXMetadata();  String type = skel.getType(); this.mm.info(registrationDebug(), new Object[] { "Stripped type", type }); result = new MBeanImpl(skel, obj, this.server, type); if (objName == null) { objName = skel.getNameValue(result); if (objName == null)
/*      */           objName = "";  }  if (objName.length() == 0) { if (!amd.isSingleton())
/*      */           throw Exceptions.self.nonSingletonRequiresName(parentEntity, type);  } else if (amd.isSingleton()) { throw Exceptions.self.singletonCannotSpecifyName(parentEntity, type, name); }  this.mm.info(registrationDebug(), new Object[] { "Name value =", objName }); result.name(objName); }
/*      */     catch (JMException exc) { throw Exceptions.self.errorInConstructingMBean(objName, exc); }
/*      */     finally { this.mm.exit(registrationDebug(), result); }
/*  961 */      return result; } public synchronized <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> type) { this.mm.enter(registrationFineDebug(), "getAnnotation", new Object[] { element, type.getName() });
/*      */ 
/*      */     
/*      */     try { Annotation annotation;
/*  965 */       if (element instanceof Class) {
/*  966 */         Class cls = (Class)element;
/*  967 */         Map<Class<?>, Annotation> annos = getAllAnnotations(cls);
/*  968 */         return (T)annos.get(type);
/*      */       } 
/*  970 */       T result = element.getAnnotation(type);
/*  971 */       if (result == null) {
/*  972 */         this.mm.info(registrationFineDebug(), new Object[] { "No annotation on element: trying addedAnnotations map" });
/*      */ 
/*      */         
/*  975 */         Map<Class<?>, Annotation> map = this.addedAnnotations.get(element);
/*      */         
/*  977 */         if (map != null) {
/*  978 */           annotation = map.get(type);
/*      */         }
/*      */       } 
/*      */       
/*  982 */       this.mm.info(registrationFineDebug(), new Object[] { "result" + annotation });
/*      */       
/*  984 */       return (T)annotation; }
/*      */     finally
/*      */     
/*  987 */     { this.mm.exit(registrationFineDebug()); }  } public synchronized GmbalMBean register(Object parent, Object obj, String name) { this.mm.clear(); checkRootCreated("register"); this.mm.enter(registrationDebug(), "register", new Object[] { parent, obj, name }); if (obj instanceof String) throw Exceptions.self.objStringWrongRegisterCall((String)obj);  try { MBeanImpl parentEntity = this.tree.getParentEntity(parent); MBeanImpl mb = constructMBean(parentEntity, obj, name); return this.tree.register(parentEntity, obj, mb); } catch (JMException exc) { throw Exceptions.self.exceptionInRegister(exc); } finally { this.mm.exit(registrationDebug()); }  }
/*      */   public synchronized GmbalMBean register(Object parent, Object obj) { return register(parent, obj, null); }
/*      */   public synchronized GmbalMBean registerAtRoot(Object obj, String name) { return register(this.tree.getRoot(), obj, name); }
/*      */   public synchronized GmbalMBean registerAtRoot(Object obj) { return register(this.tree.getRoot(), obj, null); }
/*      */   public synchronized void unregister(Object obj) { this.mm.clear(); checkRootCreated("unregister"); this.mm.enter(registrationDebug(), "unregister", new Object[] { obj }); try { this.tree.unregister(obj); } catch (JMException exc) { throw Exceptions.self.exceptionInUnregister(exc); } finally { this.mm.exit(registrationDebug()); }  }
/*      */   public synchronized ObjectName getObjectName(Object obj) { this.mm.clear(); checkRootCreated("getObjectName"); this.mm.enter(registrationDebug(), "getObjectName", new Object[] { obj }); if (obj instanceof ObjectName) return (ObjectName)obj;  if (obj instanceof AMXClient) return ((AMXClient)obj).objectName();  ObjectName result = null; try { result = this.tree.getObjectName(obj); } finally { this.mm.exit(registrationDebug(), result); }  return result; }
/*      */   public AMXClient getAMXClient(Object obj) { ObjectName oname = getObjectName(obj); if (oname == null) return null;  return new AMXClient(this.server, oname); }
/*      */   public synchronized Object getObject(ObjectName oname) { checkRootCreated("getObject"); this.mm.enter(registrationDebug(), "getObject", new Object[] { oname }); Object result = null; try { result = this.tree.getObject(oname); } finally { this.mm.exit(registrationDebug(), result); }  return result; }
/*  995 */   public synchronized Collection<Annotation> getAnnotations(AnnotatedElement elem) { this.mm.enter(registrationFineDebug(), "getAnnotations", new Object[] { elem });
/*      */ 
/*      */     
/*  998 */     try { if (elem instanceof Class) {
/*  999 */         Class cls = (Class)elem;
/*      */         
/* 1001 */         return getAllAnnotations(cls).values();
/* 1002 */       }  if (elem instanceof java.lang.reflect.Method)
/* 1003 */         return Arrays.asList(elem.getAnnotations()); 
/* 1004 */       if (elem instanceof Field) {
/* 1005 */         return Arrays.asList(elem.getAnnotations());
/*      */       }
/*      */       
/* 1008 */       throw Exceptions.self.annotationsNotSupported(elem); }
/*      */     finally
/*      */     
/* 1011 */     { this.mm.exit(registrationFineDebug()); }  } public synchronized FacetAccessor getFacetAccessor(Object obj) { MBeanImpl mb = this.tree.getMBeanImpl(obj); if (mb != null) return this.tree.getFacetAccessor(obj);  return (FacetAccessor)new FacetAccessorImpl(obj); } public synchronized String getDomain() { return this.domain; }
/*      */   public synchronized void setMBeanServer(MBeanServer server) { this.mm.clear(); checkRootNotCreated("setMBeanServer"); this.server = server; }
/*      */   public synchronized MBeanServer getMBeanServer() { return this.server; }
/*      */   public synchronized void setResourceBundle(ResourceBundle rb) { this.mm.clear(); checkRootNotCreated("setResourceBundle"); this.resourceBundle = rb; }
/*      */   public synchronized ResourceBundle getResourceBundle() { return this.resourceBundle; }
/*      */   public synchronized String getDescription(EvaluatedDeclaration element) { Description desc; if (element instanceof EvaluatedClassDeclaration) { EvaluatedClassDeclaration ecd = (EvaluatedClassDeclaration)element; desc = getFirstAnnotationOnClass(ecd, Description.class); } else { desc = getAnnotation(element.element(), Description.class); }  String result = ""; if (desc != null) result = desc.value();  if (result.length() == 0) { result = Exceptions.self.noDescriptionAvailable(); } else if (this.resourceBundle != null) { result = this.resourceBundle.getString(result); }  return result; }
/*      */   public synchronized void addAnnotation(AnnotatedElement element, Annotation annotation) { this.mm.clear(); checkRootNotCreated("addAnnotation"); this.mm.enter(registrationDebug(), "addAnnotation", new Object[] { element, annotation }); if (annotation == null) throw Exceptions.self.cannotAddNullAnnotation(element);  try { Map<Class<?>, Annotation> map = this.addedAnnotations.get(element); if (map == null) { this.mm.info(registrationDebug(), new Object[] { "Creating new Map<Class,Annotation>" }); map = new HashMap<Class<?>, Annotation>(); this.addedAnnotations.put(element, map); }  Class<?> annotationType = annotation.annotationType(); Annotation ann = map.get(annotationType); if (ann != null) { this.mm.info(registrationDebug(), new Object[] { "Duplicate annotation" }); throw Exceptions.self.duplicateAnnotation(element, annotation.getClass().getName()); }  map.put(annotationType, annotation); } finally { this.mm.exit(registrationDebug()); }  }
/*      */   public <T extends Annotation> T getFirstAnnotationOnClass(EvaluatedClassDeclaration element, Class<T> type) { EvaluatedClassAnalyzer eca = new EvaluatedClassAnalyzer(element); List<EvaluatedClassDeclaration> ecds = eca.findClasses(forAnnotation(type, EvaluatedClassDeclaration.class)); if (ecds.size() > 0) return getAnnotation(((EvaluatedClassDeclaration)ecds.get(0)).element(), type);  return null; }
/* 1019 */   public synchronized Pair<EvaluatedClassDeclaration, EvaluatedClassAnalyzer> getClassAnalyzer(final EvaluatedClassDeclaration cls, final Class<? extends Annotation> annotationClass) { this.mm.enter(registrationDebug(), "getClassAnalyzer", new Object[] { cls, annotationClass });
/*      */ 
/*      */     
/*      */     try {
/* 1023 */       EvaluatedClassAnalyzer clsca = new EvaluatedClassAnalyzer(cls);
/*      */       
/* 1025 */       EvaluatedClassDeclaration annotatedClass = (EvaluatedClassDeclaration)Algorithms.getFirst(clsca.findClasses(forAnnotation(annotationClass, EvaluatedClassDeclaration.class)), new Runnable()
/*      */           {
/*      */             
/*      */             public void run()
/*      */             {
/* 1030 */               throw Exceptions.self.noAnnotationFound(annotationClass.getName(), cls.name());
/*      */             }
/*      */           });
/*      */ 
/*      */       
/* 1035 */       this.mm.info(registrationDebug(), new Object[] { "annotatedClass = " + annotatedClass });
/*      */       
/* 1037 */       List<EvaluatedClassDeclaration> classes = new ArrayList<EvaluatedClassDeclaration>();
/*      */       
/* 1039 */       classes.add(cls);
/*      */ 
/*      */       
/* 1042 */       IncludeSubclass incsub = getFirstAnnotationOnClass(cls, IncludeSubclass.class);
/*      */       
/* 1044 */       if (incsub != null) {
/* 1045 */         for (Class<?> klass : incsub.value()) {
/* 1046 */           EvaluatedClassDeclaration ecd = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(klass);
/*      */           
/* 1048 */           classes.add(ecd);
/* 1049 */           this.mm.info(registrationDebug(), new Object[] { "included subclass", klass });
/*      */         } 
/*      */       }
/*      */       
/* 1053 */       EvaluatedClassAnalyzer ca = new EvaluatedClassAnalyzer(classes);
/*      */       
/* 1055 */       return new Pair(annotatedClass, ca);
/*      */     } finally {
/*      */       
/* 1058 */       this.mm.exit(registrationDebug());
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized List<InheritedAttribute> getInheritedAttributes(EvaluatedClassAnalyzer ca) {
/* 1066 */     this.mm.enter(registrationDebug(), "getInheritedAttributes", new Object[] { ca });
/*      */     
/*      */     try {
/* 1069 */       Predicate<EvaluatedClassDeclaration> pred = Algorithms.or(forAnnotation((Class)InheritedAttribute.class, EvaluatedClassDeclaration.class), forAnnotation((Class)InheritedAttributes.class, EvaluatedClassDeclaration.class));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1077 */       List<EvaluatedClassDeclaration> iaClasses = ca.findClasses(pred);
/*      */ 
/*      */       
/* 1080 */       List<InheritedAttribute> isList = Algorithms.flatten(iaClasses, new UnaryFunction<EvaluatedClassDeclaration, List<InheritedAttribute>>()
/*      */           {
/*      */             public List<InheritedAttribute> evaluate(EvaluatedClassDeclaration cls) {
/* 1083 */               InheritedAttribute ia = ManagedObjectManagerImpl.this.<InheritedAttribute>getFirstAnnotationOnClass(cls, InheritedAttribute.class);
/*      */               
/* 1085 */               InheritedAttributes ias = ManagedObjectManagerImpl.this.<InheritedAttributes>getFirstAnnotationOnClass(cls, InheritedAttributes.class);
/*      */               
/* 1087 */               if (ia != null && ias != null) {
/* 1088 */                 throw Exceptions.self.badInheritedAttributeAnnotation(cls);
/*      */               }
/*      */               
/* 1091 */               List<InheritedAttribute> result = new ArrayList<InheritedAttribute>();
/*      */ 
/*      */               
/* 1094 */               if (ia != null) {
/* 1095 */                 result.add(ia);
/* 1096 */               } else if (ias != null) {
/* 1097 */                 result.addAll(Arrays.asList(ias.value()));
/*      */               } 
/*      */               
/* 1100 */               return result;
/*      */             }
/*      */           });
/*      */       
/* 1104 */       return isList;
/*      */     } finally {
/* 1106 */       this.mm.exit(registrationDebug());
/*      */     } 
/*      */   }
/*      */   
/*      */   private class ADHolder
/*      */     implements Predicate<InheritedAttribute>
/*      */   {
/*      */     private final EvaluatedMethodDeclaration method;
/*      */     private final ManagedObjectManagerInternal.AttributeDescriptorType adt;
/*      */     private AttributeDescriptor content;
/*      */     
/*      */     public ADHolder(EvaluatedMethodDeclaration method, ManagedObjectManagerInternal.AttributeDescriptorType adt) {
/* 1118 */       this.method = method;
/* 1119 */       this.adt = adt;
/*      */     }
/*      */     
/*      */     public boolean evaluate(InheritedAttribute ia) {
/* 1123 */       AttributeDescriptor ad = AttributeDescriptor.makeFromInherited(ManagedObjectManagerImpl.this, this.method, ia.id(), ia.methodName(), ia.description(), this.adt);
/*      */ 
/*      */       
/* 1126 */       boolean result = (ad != null);
/* 1127 */       if (result) {
/* 1128 */         this.content = ad;
/*      */       }
/*      */       
/* 1131 */       return result;
/*      */     }
/*      */     
/*      */     public AttributeDescriptor content() {
/* 1135 */       return this.content;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeDescriptor getAttributeDescriptorIfInherited(EvaluatedMethodDeclaration method, List<InheritedAttribute> ias, ManagedObjectManagerInternal.AttributeDescriptorType adt) {
/* 1144 */     ADHolder adh = new ADHolder(method, adt);
/* 1145 */     Algorithms.find(ias, adh);
/* 1146 */     return adh.content();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized <K, V> void putIfNotPresent(Map<K, V> map, K key, V value) {
/* 1152 */     this.mm.enter(registrationFineDebug(), "putIfNotPresent", new Object[] { key, value });
/*      */     
/*      */     try {
/* 1155 */       if (!map.containsKey(key)) {
/* 1156 */         this.mm.info(registrationFineDebug(), new Object[] { "Adding key, value to map" });
/* 1157 */         map.put(key, value);
/*      */       } else {
/* 1159 */         this.mm.info(registrationFineDebug(), new Object[] { "Key,value already in map" });
/*      */       } 
/*      */     } finally {
/* 1162 */       this.mm.exit(registrationFineDebug());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static void checkFieldType(EvaluatedFieldDeclaration field) {
/* 1168 */     if (!Modifier.isFinal(field.modifiers()) || !field.fieldType().isImmutable())
/*      */     {
/* 1170 */       Exceptions.self.illegalAttributeField(field);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Pair<Map<String, AttributeDescriptor>, Map<String, AttributeDescriptor>> getAttributes(EvaluatedClassAnalyzer ca, final ManagedObjectManagerInternal.AttributeDescriptorType adt) {
/*      */     try {
/* 1185 */       final Map<String, AttributeDescriptor> getters = new HashMap<String, AttributeDescriptor>();
/*      */       
/* 1187 */       final Map<String, AttributeDescriptor> setters = new HashMap<String, AttributeDescriptor>();
/*      */ 
/*      */       
/* 1190 */       Pair<Map<String, AttributeDescriptor>, Map<String, AttributeDescriptor>> result = new Pair(getters, setters);
/*      */ 
/*      */ 
/*      */       
/* 1194 */       final List<InheritedAttribute> ias = getInheritedAttributes(ca);
/*      */       
/* 1196 */       ca.findFields(new Predicate<EvaluatedFieldDeclaration>() { public boolean evaluate(EvaluatedFieldDeclaration field) {
/*      */               String description;
/* 1198 */               ManagedAttribute ma = ManagedObjectManagerImpl.this.<ManagedAttribute>getAnnotation(field.element(), ManagedAttribute.class);
/*      */               
/* 1200 */               if (ma == null) {
/* 1201 */                 return false;
/*      */               }
/* 1203 */               ManagedObjectManagerImpl.checkFieldType(field);
/*      */               
/* 1205 */               Description desc = ManagedObjectManagerImpl.this.<Description>getAnnotation(field.element(), Description.class);
/*      */ 
/*      */               
/* 1208 */               if (desc == null) {
/* 1209 */                 description = "No description available for " + field.name();
/*      */               } else {
/*      */                 
/* 1212 */                 description = desc.value();
/*      */               } 
/*      */               
/* 1215 */               AttributeDescriptor ad = AttributeDescriptor.makeFromAnnotated(ManagedObjectManagerImpl.this, (EvaluatedAccessibleDeclaration)field, ma.id(), description, adt);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1220 */               ManagedObjectManagerImpl.this.putIfNotPresent(getters, ad.id(), ad);
/*      */               
/* 1222 */               return true;
/*      */             } }
/*      */         );
/*      */       
/* 1226 */       ca.findMethods(new Predicate<EvaluatedMethodDeclaration>() { public boolean evaluate(EvaluatedMethodDeclaration method) {
/*      */               AttributeDescriptor ad;
/* 1228 */               ManagedAttribute ma = ManagedObjectManagerImpl.this.<ManagedAttribute>getAnnotation(method.element(), ManagedAttribute.class);
/*      */ 
/*      */               
/* 1231 */               if (ma == null) {
/* 1232 */                 ad = ManagedObjectManagerImpl.this.getAttributeDescriptorIfInherited(method, ias, adt);
/*      */               } else {
/*      */                 String description;
/* 1235 */                 Description desc = ManagedObjectManagerImpl.this.<Description>getAnnotation(method.element(), Description.class);
/*      */ 
/*      */                 
/* 1238 */                 if (desc == null) {
/* 1239 */                   description = "No description available for " + method.name();
/*      */                 } else {
/*      */                   
/* 1242 */                   description = desc.value();
/*      */                 } 
/*      */                 
/* 1245 */                 ad = AttributeDescriptor.makeFromAnnotated(ManagedObjectManagerImpl.this, (EvaluatedAccessibleDeclaration)method, ma.id(), description, adt);
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 1250 */               if (ad != null) {
/* 1251 */                 if (ad.atype() == AttributeDescriptor.AttributeType.GETTER) {
/* 1252 */                   ManagedObjectManagerImpl.this.putIfNotPresent(getters, ad.id(), ad);
/*      */                 } else {
/* 1254 */                   ManagedObjectManagerImpl.this.putIfNotPresent(setters, ad.id(), ad);
/*      */                 } 
/*      */               }
/*      */               
/* 1258 */               return false;
/*      */             } }
/*      */         );
/* 1261 */       return result;
/*      */     } finally {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel level) {
/* 1270 */     this.regDebugLevel = level;
/*      */   }
/*      */   
/*      */   public synchronized void setJMXRegistrationDebug(boolean flag) {
/* 1274 */     this.jmxRegistrationDebugFlag = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setRuntimeDebug(boolean flag) {
/* 1279 */     this.runDebugFlag = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void setTypelibDebug(int level) {
/* 1284 */     TypeEvaluator.setDebugLevel(level);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized String dumpSkeleton(Object obj) {
/* 1289 */     MBeanImpl impl = this.tree.getMBeanImpl(obj);
/* 1290 */     if (impl == null) {
/* 1291 */       return obj + " is not currently registered with mom " + this;
/*      */     }
/* 1293 */     MBeanSkeleton skel = impl.skeleton();
/* 1294 */     String skelString = myObjectUtil.objectToString(skel);
/* 1295 */     return "Skeleton for MBean for object " + obj + ":\n" + skelString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean registrationDebug() {
/* 1302 */     return (this.regDebugLevel == ManagedObjectManager.RegistrationDebugLevel.NORMAL || this.regDebugLevel == ManagedObjectManager.RegistrationDebugLevel.FINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean registrationFineDebug() {
/* 1308 */     return (this.regDebugLevel == ManagedObjectManager.RegistrationDebugLevel.FINE);
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized boolean runtimeDebug() {
/* 1313 */     return this.runDebugFlag;
/*      */   }
/*      */   
/*      */   public synchronized boolean jmxRegistrationDebug() {
/* 1317 */     return this.jmxRegistrationDebugFlag;
/*      */   }
/*      */   
/*      */   public synchronized void stripPrefix(String... args) {
/* 1321 */     checkRootNotCreated("stripPrefix");
/* 1322 */     for (String str : args) {
/* 1323 */       this.typePrefixes.add(str);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized <T extends EvaluatedDeclaration> Predicate<T> forAnnotation(final Class<? extends Annotation> annotation, Class<T> cls) {
/* 1332 */     return new Predicate<T>() {
/*      */         public boolean evaluate(T elem) {
/* 1334 */           return (ManagedObjectManagerImpl.this.getAnnotation(elem.element(), annotation) != null);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public AMXMetadata getDefaultAMXMetadata() {
/* 1340 */     return DEFAULT_AMX_METADATA;
/*      */   }
/*      */   
/*      */   public boolean isAMXAttributeName(String name) {
/* 1344 */     return this.amxAttributeNames.contains(name);
/*      */   }
/*      */   
/*      */   public void suppressDuplicateRootReport(boolean suppressReport) {
/* 1348 */     checkRootNotCreated("suppressDuplicateRootReport");
/* 1349 */     this.tree.setSuppressDuplicateSetRootReport(suppressReport);
/*      */   }
/*      */   
/*      */   @ManagedData
/*      */   @Description("Custom statistic type whose value is a string")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getCurrent", description = "Returns the String value of the statistic")})
/*      */   public static interface DummyStringStatistic extends DummyStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("Provides standard measurements of a range that has fixed limits")
/*      */   public static interface DummyBoundedRangeStatistic extends DummyBoundaryStatistic, DummyRangeStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("Adds an average to the range statistic")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getAverage", description = "The average value of this attribute since its last reset")})
/*      */   public static interface DummyAverageRangeStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("Specifies standard measurements of the lowest and highest values an attribute has held as well as its current value")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getHighWaterMark", description = "The highest value this attribute has held since the beginninYg of the measurement"), @InheritedAttribute(methodName = "getLowWaterMark", description = "The lowest value this attribute has held since the beginninYg of the measurement"), @InheritedAttribute(methodName = "getCurrent", description = "The current value of this attribute")})
/*      */   public static interface DummyRangeStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("Specifies standard count measurements")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getCount", description = "The count since the last reset")})
/*      */   public static interface DummyCountStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("Specifies standard measurements of the upper and lower limits of the value of an attribute")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getUpperBound", description = "The upper limit of the value of this attribute"), @InheritedAttribute(methodName = "getLowerBound", description = "The lower limit of the value of this attribute")})
/*      */   public static interface DummyBoundaryStatistic extends DummyStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("Specifies standard timing measurements")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getCount", description = "Number of times the operation was invoked since the beginning of this measurement"), @InheritedAttribute(methodName = "getMaxTime", description = "The maximum amount of time taken to complete one invocation of this operation since the beginning of this measurement"), @InheritedAttribute(methodName = "getMinTime", description = "The minimum amount of time taken to complete one invocation of this operation since the beginning of this measurement"), @InheritedAttribute(methodName = "getTotalTime", description = "The total amount of time taken to complete every invocation of this operation since the beginning of this measurement")})
/*      */   public static interface DummyTimeStatistic extends DummyStatistic {}
/*      */   
/*      */   @ManagedData
/*      */   @Description("The Statistic model and its sub-models specify the data models which are requried to be used to provide the performance data described by the specific attributes in the Stats models")
/*      */   @InheritedAttributes({@InheritedAttribute(methodName = "getName", description = "The name of this Statistic"), @InheritedAttribute(methodName = "getUnit", description = "The unit of measurement for this Statistic"), @InheritedAttribute(methodName = "getDescription", description = "A human-readable description of the Statistic"), @InheritedAttribute(methodName = "getStartTime", description = "The time of the first measurement represented as a long"), @InheritedAttribute(methodName = "getLastSampleTime", description = "The time of the first measurement represented as a long")})
/*      */   public static interface DummyStatistic {}
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\ManagedObjectManagerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */