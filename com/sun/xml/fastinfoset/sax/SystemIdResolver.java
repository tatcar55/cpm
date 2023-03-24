/*     */ package com.sun.xml.fastinfoset.sax;
/*     */ 
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemIdResolver
/*     */ {
/*     */   public static String getAbsoluteURIFromRelative(String localPath) {
/*     */     String urlString;
/*  28 */     if (localPath == null || localPath.length() == 0) {
/*  29 */       return "";
/*     */     }
/*     */     
/*  32 */     String absolutePath = localPath;
/*  33 */     if (!isAbsolutePath(localPath)) {
/*     */       try {
/*  35 */         absolutePath = getAbsolutePathFromRelativePath(localPath);
/*     */       }
/*  37 */       catch (SecurityException se) {
/*  38 */         return "file:" + localPath;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  43 */     if (null != absolutePath) {
/*  44 */       urlString = absolutePath.startsWith(File.separator) ? ("file://" + absolutePath) : ("file:///" + absolutePath);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  49 */       urlString = "file:" + localPath;
/*     */     } 
/*     */     
/*  52 */     return replaceChars(urlString);
/*     */   }
/*     */   
/*     */   private static String getAbsolutePathFromRelativePath(String relativePath) {
/*  56 */     return (new File(relativePath)).getAbsolutePath();
/*     */   }
/*     */   
/*     */   public static boolean isAbsoluteURI(String systemId) {
/*  60 */     if (systemId == null) {
/*  61 */       return false;
/*     */     }
/*     */     
/*  64 */     if (isWindowsAbsolutePath(systemId)) {
/*  65 */       return false;
/*     */     }
/*     */     
/*  68 */     int fragmentIndex = systemId.indexOf('#');
/*  69 */     int queryIndex = systemId.indexOf('?');
/*  70 */     int slashIndex = systemId.indexOf('/');
/*  71 */     int colonIndex = systemId.indexOf(':');
/*     */     
/*  73 */     int index = systemId.length() - 1;
/*  74 */     if (fragmentIndex > 0) {
/*  75 */       index = fragmentIndex;
/*     */     }
/*  77 */     if (queryIndex > 0 && queryIndex < index) {
/*  78 */       index = queryIndex;
/*     */     }
/*  80 */     if (slashIndex > 0 && slashIndex < index) {
/*  81 */       index = slashIndex;
/*     */     }
/*  83 */     return (colonIndex > 0 && colonIndex < index);
/*     */   }
/*     */   
/*     */   public static boolean isAbsolutePath(String systemId) {
/*  87 */     if (systemId == null)
/*  88 */       return false; 
/*  89 */     File file = new File(systemId);
/*  90 */     return file.isAbsolute();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isWindowsAbsolutePath(String systemId) {
/*  95 */     if (!isAbsolutePath(systemId))
/*  96 */       return false; 
/*  97 */     if (systemId.length() > 2 && systemId.charAt(1) == ':' && Character.isLetter(systemId.charAt(0)) && (systemId.charAt(2) == '\\' || systemId.charAt(2) == '/'))
/*     */     {
/*     */ 
/*     */       
/* 101 */       return true;
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   private static String replaceChars(String str) {
/* 107 */     StringBuffer buf = new StringBuffer(str);
/* 108 */     int length = buf.length();
/* 109 */     for (int i = 0; i < length; i++) {
/* 110 */       char currentChar = buf.charAt(i);
/*     */       
/* 112 */       if (currentChar == ' ') {
/* 113 */         buf.setCharAt(i, '%');
/* 114 */         buf.insert(i + 1, "20");
/* 115 */         length += 2;
/* 116 */         i += 2;
/*     */       
/*     */       }
/* 119 */       else if (currentChar == '\\') {
/* 120 */         buf.setCharAt(i, '/');
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public static String getAbsoluteURI(String systemId) {
/* 128 */     String absoluteURI = systemId;
/* 129 */     if (isAbsoluteURI(systemId)) {
/* 130 */       if (systemId.startsWith("file:")) {
/* 131 */         String str = systemId.substring(5);
/*     */         
/* 133 */         if (str != null && str.startsWith("/")) {
/* 134 */           if (str.startsWith("///") || !str.startsWith("//")) {
/* 135 */             int secondColonIndex = systemId.indexOf(':', 5);
/* 136 */             if (secondColonIndex > 0) {
/* 137 */               String localPath = systemId.substring(secondColonIndex - 1);
/*     */               try {
/* 139 */                 if (!isAbsolutePath(localPath)) {
/* 140 */                   absoluteURI = systemId.substring(0, secondColonIndex - 1) + getAbsolutePathFromRelativePath(localPath);
/*     */                 }
/*     */               }
/* 143 */               catch (SecurityException se) {
/* 144 */                 return systemId;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/*     */           
/* 150 */           return getAbsoluteURIFromRelative(systemId.substring(5));
/*     */         } 
/*     */         
/* 153 */         return replaceChars(absoluteURI);
/*     */       } 
/*     */       
/* 156 */       return systemId;
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return getAbsoluteURIFromRelative(systemId);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sax\SystemIdResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */