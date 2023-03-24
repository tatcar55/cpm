/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaBean
/*     */   implements RmiConstants
/*     */ {
/*     */   private HashMap members;
/*     */   private Class remoteBean;
/*     */   ProcessorEnvironment env;
/*     */   
/*     */   private static JavaBean forType(ProcessorEnvironment env, RmiType type) {
/*  48 */     JavaBean bean = null;
/*     */     try {
/*  50 */       String implClassName = type.getClassName();
/*     */ 
/*     */       
/*  53 */       ClassLoader classLoader = env.getClassLoader();
/*  54 */       Class<?> beanClass = classLoader.loadClass(implClassName);
/*  55 */       bean = new JavaBean(env, beanClass);
/*  56 */       bean.initialize();
/*  57 */     } catch (ClassNotFoundException e) {
/*  58 */       throw new ModelerException("rmimodeler.nestedRmiModelerError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */ 
/*     */     
/*  62 */     return bean;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map modelTypeSOAP(ProcessorEnvironment env, RmiType type) {
/*  67 */     JavaBean bean = forType(env, type);
/*  68 */     if (bean == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     return bean.getMembers();
/*     */   }
/*     */   
/*     */   private HashMap getMembers() {
/*  75 */     return (HashMap)this.members.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JavaBean(ProcessorEnvironment env, Class remoteBean) {
/*  86 */     this.env = env;
/*  87 */     this.remoteBean = remoteBean;
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  91 */     this.members = new HashMap<Object, Object>();
/*  92 */     collectMembers(this.remoteBean, this.members, this.remoteBean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void collectMembers(Class<?> remoteBean, Map<String, MemberInfo> members, Class baseBean) {
/*     */     BeanInfo beanInfo;
/*     */     try {
/* 102 */       beanInfo = Introspector.getBeanInfo(remoteBean);
/* 103 */     } catch (IntrospectionException e) {
/* 104 */       throw new ModelerException("rmimodeler.invalid.rmi.type:", remoteBean.getName().toString());
/*     */     } 
/*     */ 
/*     */     
/* 108 */     PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     for (int i = 0; i < properties.length; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       Class<?> propertyType = properties[i].getPropertyType();
/* 120 */       Method readMethod = properties[i].getReadMethod();
/* 121 */       Method writeMethod = properties[i].getWriteMethod();
/* 122 */       if (propertyType != null && readMethod != null && writeMethod != null && (readMethod.getParameterTypes()).length == 0 && (writeMethod.getParameterTypes()).length == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 129 */         String propertyName = properties[i].getName();
/* 130 */         RmiType type = RmiType.getRmiType(readMethod.getReturnType());
/* 131 */         if (type == null) {
/* 132 */           throw new ModelerException("rmimodeler.could.not.resolve.property.type", remoteBean.getName() + ":" + propertyName);
/*     */         }
/*     */ 
/*     */         
/* 136 */         MemberInfo memInfo = new MemberInfo(propertyName, type, false);
/* 137 */         memInfo.setReadMethod(readMethod.getName());
/* 138 */         memInfo.setWriteMethod(writeMethod.getName());
/* 139 */         if (!writeMethod.getDeclaringClass().equals(baseBean)) {
/* 140 */           memInfo.setDeclaringClass(writeMethod.getDeclaringClass());
/*     */         }
/* 142 */         members.put(propertyName, memInfo);
/*     */       } 
/* 144 */     }  if (remoteBean.getSuperclass() != null)
/* 145 */       collectMembers(remoteBean.getSuperclass(), members, baseBean); 
/* 146 */     Class[] interfaces = remoteBean.getInterfaces();
/* 147 */     for (int j = 0; j < interfaces.length; j++) {
/* 148 */       collectMembers(interfaces[j], members, baseBean);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void log(ProcessorEnvironment env, String msg) {
/* 153 */     if (env.verbose())
/* 154 */       System.out.println("[JavaBean: " + msg + "]"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\JavaBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */