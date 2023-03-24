/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ExceptionModelerBase
/*     */   implements RmiConstants
/*     */ {
/*     */   protected RmiModeler modeler;
/*     */   protected Class defRuntimeException;
/*     */   protected ProcessorEnvironment env;
/*  52 */   protected static final String THROWABLE_CLASSNAME = Throwable.class.getName();
/*     */   
/*  54 */   protected static final String OBJECT_CLASSNAME = Object.class.getName();
/*     */   
/*     */   protected static final int MESSAGE_FLAG = 2;
/*     */   
/*     */   protected static final int LOCALIZED_MESSAGE_FLAG = 4;
/*     */   protected static Method GET_MESSAGE_METHOD;
/*     */   protected Map faultMap;
/*     */   
/*     */   static {
/*     */     try {
/*  64 */       GET_MESSAGE_METHOD = Throwable.class.getDeclaredMethod("getMessage", new Class[0]);
/*     */ 
/*     */     
/*     */     }
/*  68 */     catch (Exception e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public ExceptionModelerBase(RmiModeler modeler) {
/*  73 */     this.modeler = modeler;
/*  74 */     this.env = modeler.getProcessorEnvironment();
/*  75 */     this.faultMap = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  80 */       this.defRuntimeException = RmiUtils.getClassForName(RuntimeException.class.getName(), this.env.getClassLoader());
/*     */ 
/*     */ 
/*     */       
/*  84 */       GET_MESSAGE_METHOD = RmiUtils.getClassForName(Throwable.class.getName(), this.env.getClassLoader()).getDeclaredMethod("getMessage", new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  90 */     catch (ClassNotFoundException e) {
/*  91 */       throw new ModelerException("rmimodeler.class.not.found", RuntimeException.class.getName());
/*     */     
/*     */     }
/*  94 */     catch (NoSuchMethodException e) {
/*  95 */       throw new ModelerException("rmimodeler.no.such.method", new Object[] { "getMessage", Throwable.class.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fault modelException(String typeUri, String wsdlUri, Class exceptionClass) {
/* 106 */     String className = exceptionClass.getName();
/* 107 */     checkForJavaExceptions(className);
/* 108 */     return createFault(typeUri, wsdlUri, exceptionClass);
/*     */   }
/*     */   
/*     */   protected void checkForJavaExceptions(String className) {
/* 112 */     if (Names.isInJavaOrJavaxPackage(className)) {
/* 113 */       throw new ModelerException("rmimodeler.java.exceptions.not.allowed", className);
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
/*     */   protected static Set getDuplicateMembers(Map members) {
/* 125 */     Set<RmiType> types = new HashSet();
/* 126 */     Set<Method> duplicateMembers = new HashSet();
/* 127 */     Iterator<Map.Entry> iter = members.entrySet().iterator();
/*     */ 
/*     */ 
/*     */     
/* 131 */     while (iter.hasNext()) {
/* 132 */       Method member = (Method)((Map.Entry)iter.next()).getValue();
/* 133 */       RmiType type = RmiType.getRmiType(member.getReturnType());
/* 134 */       String memberName = member.getName();
/* 135 */       if (types.contains(type)) {
/* 136 */         duplicateMembers.add(member); continue;
/*     */       } 
/* 138 */       types.add(type);
/*     */     } 
/*     */     
/* 141 */     return duplicateMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void collectMembers(Class<?> classDef, Map members) {
/*     */     try {
/* 147 */       if (this.defRuntimeException.isAssignableFrom(classDef)) {
/* 148 */         throw new ModelerException("rmimodeler.must.not.extend.runtimeexception", classDef.getName());
/*     */       }
/*     */ 
/*     */       
/* 152 */       collectExceptionMembers(classDef, members);
/* 153 */     } catch (Exception e) {
/* 154 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void collectExceptionMembers(Class classDef, Map<String, Method> members) {
/*     */     try {
/* 161 */       if (classDef.equals(Throwable.class)) {
/* 162 */         members.put("getMessage", GET_MESSAGE_METHOD);
/*     */         return;
/*     */       } 
/* 165 */       if (classDef.getSuperclass() != null)
/* 166 */         collectExceptionMembers(classDef.getSuperclass(), members); 
/* 167 */       Method[] methods = classDef.getMethods();
/*     */       
/* 169 */       for (int i = 0; i < methods.length; i++) {
/* 170 */         Class<?> decClass = methods[i].getDeclaringClass();
/* 171 */         if (!Modifier.isStatic(methods[i].getModifiers()) && !decClass.equals(Throwable.class) && !decClass.equals(Object.class)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 176 */           String memberName = methods[i].getName();
/* 177 */           if ((memberName.startsWith("get") || memberName.startsWith("is")) && (methods[i].getParameterTypes()).length == 0)
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 182 */             if (!members.containsKey(memberName))
/* 183 */               members.put(memberName, methods[i]); 
/*     */           }
/*     */         } 
/*     */       } 
/* 187 */     } catch (Exception e) {
/* 188 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Fault getMappedFault(String className) {
/* 197 */     return (Fault)this.faultMap.get(className);
/*     */   }
/*     */   
/*     */   private void log(ProcessorEnvironment env, String msg) {
/* 201 */     if (env.verbose())
/* 202 */       System.out.println("[" + Names.stripQualifier(getClass().getName()) + ": " + msg + "]"); 
/*     */   }
/*     */   
/*     */   public abstract Fault createFault(String paramString1, String paramString2, Class paramClass);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\ExceptionModelerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */