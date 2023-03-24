/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmiUtils
/*     */   implements RmiConstants
/*     */ {
/*     */   public static String getTypeSig(String typeName) {
/*  39 */     String sig = "";
/*     */     int idx;
/*  41 */     if ((idx = typeName.lastIndexOf("[]")) > 0) {
/*  42 */       return "[" + getTypeSig(typeName.substring(0, idx));
/*     */     }
/*  44 */     if (typeName.equals("boolean"))
/*  45 */       return "Z"; 
/*  46 */     if (typeName.equals("byte"))
/*  47 */       return "B"; 
/*  48 */     if (typeName.equals("char"))
/*  49 */       return "C"; 
/*  50 */     if (typeName.equals("short"))
/*  51 */       return "S"; 
/*  52 */     if (typeName.equals("int"))
/*  53 */       return "I"; 
/*  54 */     if (typeName.equals("long"))
/*  55 */       return "J"; 
/*  56 */     if (typeName.equals("float"))
/*  57 */       return "F"; 
/*  58 */     if (typeName.equals("double"))
/*  59 */       return "D"; 
/*  60 */     if (typeName.equals("void"))
/*  61 */       return "V"; 
/*  62 */     return "L" + typeName.replace('.', '/') + ";";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRealName(String name, ClassLoader loader) throws ClassNotFoundException {
/*  68 */     String tmpName = name;
/*  69 */     if (name.lastIndexOf("[]") > 0)
/*  70 */       tmpName = getTypeSig(name).replace('/', '.'); 
/*  71 */     tmpName = getLoadableClassName(tmpName, loader);
/*  72 */     return tmpName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class getClassForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
/*  78 */     Class<?> tmpClass = (Class)typeClassMap.get(name);
/*  79 */     if (tmpClass != null)
/*  80 */       return tmpClass; 
/*  81 */     String tmpName = name;
/*  82 */     if (name.lastIndexOf("[]") > 0)
/*  83 */       tmpName = getTypeSig(name).replace('/', '.'); 
/*  84 */     tmpName = getLoadableClassName(tmpName, classLoader);
/*  85 */     tmpClass = Class.forName(tmpName, true, classLoader);
/*  86 */     return tmpClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLoadableClassName(String className, ClassLoader classLoader) throws ClassNotFoundException {
/*     */     try {
/*  95 */       Class<?> seiClass = Class.forName(className, true, classLoader);
/*  96 */     } catch (ClassNotFoundException e) {
/*  97 */       int idx = className.lastIndexOf('.');
/*  98 */       if (idx > -1) {
/*  99 */         String tmp = className.substring(0, idx) + "$";
/* 100 */         tmp = tmp + className.substring(idx + 1);
/* 101 */         return getLoadableClassName(tmp, classLoader);
/*     */       } 
/* 103 */       throw e;
/*     */     } 
/* 105 */     return className;
/*     */   }
/*     */   
/* 108 */   private static final Map typeClassMap = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 111 */     typeClassMap.put("boolean", boolean.class);
/* 112 */     typeClassMap.put("byte", byte.class);
/* 113 */     typeClassMap.put("char", char.class);
/* 114 */     typeClassMap.put("double", double.class);
/* 115 */     typeClassMap.put("float", float.class);
/* 116 */     typeClassMap.put("int", int.class);
/* 117 */     typeClassMap.put("long", long.class);
/* 118 */     typeClassMap.put("short", short.class);
/* 119 */     typeClassMap.put("void", void.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\RmiUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */