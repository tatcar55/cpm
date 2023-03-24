/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaCompilerHelper
/*     */   extends ToolBase
/*     */ {
/*     */   protected OutputStream out;
/*     */   
/*     */   public JavaCompilerHelper(OutputStream out) {
/*  45 */     super(out, " ");
/*  46 */     this.out = out;
/*     */   }
/*     */   
/*     */   public boolean compile(String[] args) {
/*  50 */     return internalCompile(args);
/*     */   }
/*     */   
/*     */   protected String getResourceBundleName() {
/*  54 */     return "com.sun.xml.rpc.resources.javacompiler";
/*     */   }
/*     */   
/*     */   protected boolean internalCompile(String[] args) {
/*  58 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  59 */     Class<?> comSunToolsJavacMainClass = null;
/*     */     
/*     */     try {
/*  62 */       comSunToolsJavacMainClass = cl.loadClass("com.sun.tools.javac.Main");
/*     */       
/*     */       try {
/*  65 */         Method compileMethod = comSunToolsJavacMainClass.getMethod("compile", compile141MethodSignature);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  70 */           Object result = compileMethod.invoke(null, new Object[] { args, new PrintWriter(this.out) });
/*     */ 
/*     */ 
/*     */           
/*  74 */           if (!(result instanceof Integer)) {
/*  75 */             return false;
/*     */           }
/*  77 */           return (((Integer)result).intValue() == 0);
/*  78 */         } catch (IllegalAccessException e3) {
/*  79 */           return false;
/*  80 */         } catch (IllegalArgumentException e3) {
/*  81 */           return false;
/*  82 */         } catch (InvocationTargetException e3) {
/*  83 */           return false;
/*     */         } 
/*  85 */       } catch (NoSuchMethodException e2) {
/*     */         
/*  87 */         return internalCompilePre141(args);
/*     */       }
/*     */     
/*     */     }
/*  91 */     catch (ClassNotFoundException e) {
/*  92 */       onError(getMessage("javacompiler.classpath.error", "com.sun.tools.javac.Main"));
/*     */ 
/*     */ 
/*     */       
/*  96 */       return false;
/*  97 */     } catch (SecurityException e) {
/*  98 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean internalCompilePre141(String[] args) {
/* 103 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*     */     try {
/* 105 */       Class<?> sunToolsJavacMainClass = cl.loadClass("sun.tools.javac.Main");
/*     */       try {
/* 107 */         Constructor<?> constructor = sunToolsJavacMainClass.getConstructor(constructorSignature);
/*     */         
/*     */         try {
/* 110 */           Object javacMain = constructor.newInstance(new Object[] { this.out, "javac" });
/*     */           
/* 112 */           Method compileMethod = sunToolsJavacMainClass.getMethod("compile", compileMethodSignature);
/*     */ 
/*     */ 
/*     */           
/* 116 */           Object result = compileMethod.invoke(javacMain, new Object[] { args });
/*     */           
/* 118 */           if (!(result instanceof Boolean)) {
/* 119 */             return false;
/*     */           }
/* 121 */           return ((Boolean)result).booleanValue();
/* 122 */         } catch (InstantiationException e4) {
/* 123 */           return false;
/* 124 */         } catch (IllegalAccessException e4) {
/* 125 */           return false;
/* 126 */         } catch (IllegalArgumentException e4) {
/* 127 */           return false;
/* 128 */         } catch (InvocationTargetException e4) {
/* 129 */           return false;
/*     */         }
/*     */       
/* 132 */       } catch (NoSuchMethodException e3) {
/* 133 */         onError(getMessage("javacompiler.nosuchmethod.error", "getMethod(\"compile\", compileMethodSignature)"));
/*     */ 
/*     */ 
/*     */         
/* 137 */         return false;
/*     */       } 
/* 139 */     } catch (ClassNotFoundException e2) {
/* 140 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getGenericErrorMessage() {
/* 145 */     return "javacompiler.error";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void run() {}
/*     */   
/*     */   protected boolean parseArguments(String[] args) {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public void onError(Localizable msg) {
/* 156 */     report(getMessage("javacompiler.error", this.localizer.localize(msg)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   protected static final Class[] compile141MethodSignature = new Class[2]; static {
/* 167 */     compile141MethodSignature[0] = (new String[0]).getClass();
/* 168 */     compile141MethodSignature[1] = PrintWriter.class;
/*     */   }
/* 170 */   protected static final Class[] constructorSignature = new Class[2]; static {
/* 171 */     constructorSignature[0] = OutputStream.class;
/* 172 */     constructorSignature[1] = String.class;
/* 173 */   } protected static final Class[] compileMethodSignature = new Class[1]; static {
/* 174 */     compileMethodSignature[0] = compile141MethodSignature[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\JavaCompilerHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */