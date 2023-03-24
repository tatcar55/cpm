/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmiStructure
/*     */   implements RmiConstants
/*     */ {
/*     */   private ProcessorEnvironment env;
/*     */   private Class implClassDef;
/*     */   private HashMap members;
/*     */   private Class defRemote;
/*     */   
/*     */   private static RmiStructure forClass(ProcessorEnvironment env, Class implClassDef) {
/*  48 */     RmiStructure sc = new RmiStructure(env, implClassDef);
/*  49 */     sc.initialize();
/*  50 */     return sc;
/*     */   }
/*     */   
/*     */   public static Map modelTypeSOAP(ProcessorEnvironment env, RmiType type) {
/*  54 */     Class cDec = null;
/*  55 */     RmiStructure rt = null;
/*     */     try {
/*  57 */       cDec = type.getTypeClass(env.getClassLoader());
/*  58 */       rt = forClass(env, cDec);
/*  59 */     } catch (ClassNotFoundException e) {
/*  60 */       throw new ModelerException("rmimodeler.class.not.found", type.toString());
/*     */     } 
/*     */ 
/*     */     
/*  64 */     if (rt == null) {
/*  65 */       return null;
/*     */     }
/*  67 */     return rt.getMembers();
/*     */   }
/*     */   
/*     */   private HashMap getMembers() {
/*  71 */     return (HashMap)this.members.clone();
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
/*     */   private RmiStructure(ProcessorEnvironment env, Class implClassDef) {
/*  91 */     this.env = env;
/*  92 */     this.implClassDef = implClassDef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*     */     try {
/* 102 */       this.defRemote = RmiUtils.getClassForName(REMOTE_CLASSNAME, this.env.getClassLoader());
/*     */ 
/*     */     
/*     */     }
/* 106 */     catch (ClassNotFoundException e) {
/* 107 */       throw new ModelerException("rmimodeler.class.not.found", RuntimeException.class.getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (!this.implClassDef.isInterface() && !Modifier.isAbstract(this.implClassDef.getModifiers())) {
/*     */ 
/*     */ 
/*     */       
/* 117 */       boolean hasDefaultConstructor = false;
/*     */       try {
/* 119 */         hasDefaultConstructor = (this.implClassDef.getConstructor(new Class[0]) != null);
/*     */ 
/*     */       
/*     */       }
/* 123 */       catch (NoSuchMethodException e) {}
/*     */       
/* 125 */       if (!hasDefaultConstructor) {
/* 126 */         throw new ModelerException("rmimodeler.no.empty.constructor", this.implClassDef.getName().toString());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 132 */     this.members = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     Vector<Class<?>> interfacesImplemented = new Vector();
/*     */ 
/*     */     
/* 143 */     interfacesImplemented.addElement(this.implClassDef);
/* 144 */     if (this.defRemote.isAssignableFrom(this.implClassDef)) {
/* 145 */       log(this.env, "remote interface implemented by: " + this.implClassDef.getName());
/*     */ 
/*     */       
/* 148 */       throw new ModelerException("rmimodeler.type.cannot.implement.remote", this.implClassDef.getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     if (!collectMembers(this.implClassDef, this.members)) {
/* 157 */       this.members = new HashMap<Object, Object>();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean collectMembers(Class interfaceDef, HashMap<String, MemberInfo> map) {
/* 166 */     Field[] fields = interfaceDef.getFields();
/*     */     
/* 168 */     for (int i = 0; fields != null && i < fields.length; i++) {
/* 169 */       int modifier = fields[i].getModifiers();
/* 170 */       if (Modifier.isPublic(modifier) && (!Modifier.isFinal(modifier) || !Modifier.isStatic(modifier)) && !Modifier.isTransient(modifier))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 177 */         if (map.get(fields[i].getName()) == null) {
/* 178 */           MemberInfo memInfo = new MemberInfo(fields[i].getName(), RmiType.getRmiType(fields[i].getType()), true);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           if (fields[i].getDeclaringClass().equals(interfaceDef)) {
/* 184 */             memInfo.setDeclaringClass(fields[i].getDeclaringClass());
/*     */           }
/* 186 */           map.put(fields[i].getName(), memInfo);
/*     */         }  } 
/*     */     } 
/* 189 */     return true;
/*     */   }
/*     */   
/*     */   private static void log(ProcessorEnvironment env, String msg) {
/* 193 */     if (env.verbose())
/* 194 */       System.out.println("[RmiStructure: " + msg + "]"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\RmiStructure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */