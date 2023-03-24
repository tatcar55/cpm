/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.gmbal.AMXMetadata;
/*     */ import org.glassfish.gmbal.InheritedAttribute;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ import org.glassfish.gmbal.generic.FacetAccessor;
/*     */ import org.glassfish.gmbal.generic.Pair;
/*     */ import org.glassfish.gmbal.generic.Predicate;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedClassAnalyzer;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedClassDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ManagedObjectManagerInternal
/*     */   extends ManagedObjectManager
/*     */ {
/*     */   TypeConverter getTypeConverter(EvaluatedType paramEvaluatedType);
/*     */   
/*     */   String getDescription(EvaluatedDeclaration paramEvaluatedDeclaration);
/*     */   
/*     */   <T extends Annotation> T getAnnotation(AnnotatedElement paramAnnotatedElement, Class<T> paramClass);
/*     */   
/*     */   Collection<Annotation> getAnnotations(AnnotatedElement paramAnnotatedElement);
/*     */   
/*     */   Pair<EvaluatedClassDeclaration, EvaluatedClassAnalyzer> getClassAnalyzer(EvaluatedClassDeclaration paramEvaluatedClassDeclaration, Class<? extends Annotation> paramClass);
/*     */   
/*     */   List<InheritedAttribute> getInheritedAttributes(EvaluatedClassAnalyzer paramEvaluatedClassAnalyzer);
/*     */   
/*     */   Pair<Map<String, AttributeDescriptor>, Map<String, AttributeDescriptor>> getAttributes(EvaluatedClassAnalyzer paramEvaluatedClassAnalyzer, AttributeDescriptorType paramAttributeDescriptorType);
/*     */   
/*     */   <K, V> void putIfNotPresent(Map<K, V> paramMap, K paramK, V paramV);
/*     */   
/*     */   String getTypeName(Class<?> paramClass, String paramString1, String paramString2);
/*     */   
/*     */   <T extends EvaluatedDeclaration> Predicate<T> forAnnotation(Class<? extends Annotation> paramClass, Class<T> paramClass1);
/*     */   
/*     */   FacetAccessor getFacetAccessor(Object paramObject);
/*     */   
/*     */   MBeanImpl constructMBean(MBeanImpl paramMBeanImpl, Object paramObject, String paramString);
/*     */   
/*     */   ObjectName getRootParentName();
/*     */   
/*     */   boolean registrationDebug();
/*     */   
/*     */   boolean registrationFineDebug();
/*     */   
/*     */   boolean jmxRegistrationDebug();
/*     */   
/*     */   boolean runtimeDebug();
/*     */   
/*     */   AMXMetadata getDefaultAMXMetadata();
/*     */   
/*     */   <T extends Annotation> T getFirstAnnotationOnClass(EvaluatedClassDeclaration paramEvaluatedClassDeclaration, Class<T> paramClass);
/*     */   
/*     */   boolean isAMXAttributeName(String paramString);
/*     */   
/*     */   public enum AttributeDescriptorType
/*     */   {
/* 125 */     MBEAN_ATTR, COMPOSITE_DATA_ATTR;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\ManagedObjectManagerInternal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */