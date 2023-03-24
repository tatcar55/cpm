/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropUtil
/*     */ {
/*     */   public static boolean getBooleanSystemProperty(String name, boolean def) {
/*     */     try {
/*  63 */       return getBoolean(getProp(System.getProperties(), name), def);
/*  64 */     } catch (SecurityException sex) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  73 */         String value = System.getProperty(name);
/*  74 */         if (value == null) {
/*  75 */           return def;
/*     */         }
/*  77 */         if (def) {
/*  78 */           return !value.equalsIgnoreCase("false");
/*     */         }
/*  80 */         return value.equalsIgnoreCase("true");
/*     */       }
/*  82 */       catch (SecurityException securityException) {
/*  83 */         return def;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getProp(Properties props, String name) {
/*  93 */     Object val = props.get(name);
/*  94 */     if (val != null) {
/*  95 */       return val;
/*     */     }
/*  97 */     return props.getProperty(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(Object value, boolean def) {
/* 106 */     if (value == null) {
/* 107 */       return def;
/*     */     }
/* 109 */     if (value instanceof String) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       if (def) {
/* 115 */         return !((String)value).equalsIgnoreCase("false");
/*     */       }
/* 117 */       return ((String)value).equalsIgnoreCase("true");
/*     */     } 
/*     */     
/* 120 */     if (value instanceof Boolean) {
/* 121 */       return ((Boolean)value).booleanValue();
/*     */     }
/* 123 */     return def;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\PropUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */