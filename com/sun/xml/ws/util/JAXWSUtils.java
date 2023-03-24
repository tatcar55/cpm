/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JAXWSUtils
/*     */ {
/*     */   public static String getUUID() {
/*  61 */     return UUID.randomUUID().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFileOrURLName(String fileOrURL) {
/*     */     try {
/*  69 */       return escapeSpace((new URL(fileOrURL)).toExternalForm());
/*  70 */     } catch (MalformedURLException e) {
/*  71 */       return (new File(fileOrURL)).getCanonicalFile().toURL().toExternalForm();
/*     */     }
/*  73 */     catch (Exception e) {
/*     */       
/*  75 */       return fileOrURL;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL getFileOrURL(String fileOrURL) throws IOException {
/*     */     try {
/*  81 */       URL url = new URL(fileOrURL);
/*  82 */       String scheme = String.valueOf(url.getProtocol()).toLowerCase();
/*  83 */       if (scheme.equals("http") || scheme.equals("https"))
/*  84 */         return new URL(url.toURI().toASCIIString()); 
/*  85 */       return url;
/*  86 */     } catch (URISyntaxException e) {
/*  87 */       return (new File(fileOrURL)).toURL();
/*  88 */     } catch (MalformedURLException e) {
/*  89 */       return (new File(fileOrURL)).toURL();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL getEncodedURL(String urlStr) throws MalformedURLException {
/*  94 */     URL url = new URL(urlStr);
/*  95 */     String scheme = String.valueOf(url.getProtocol()).toLowerCase();
/*  96 */     if (scheme.equals("http") || scheme.equals("https")) {
/*     */       try {
/*  98 */         return new URL(url.toURI().toASCIIString());
/*  99 */       } catch (URISyntaxException e) {
/* 100 */         MalformedURLException malformedURLException = new MalformedURLException(e.getMessage());
/* 101 */         malformedURLException.initCause(e);
/* 102 */         throw malformedURLException;
/*     */       } 
/*     */     }
/* 105 */     return url;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String escapeSpace(String url) {
/* 110 */     StringBuilder buf = new StringBuilder();
/* 111 */     for (int i = 0; i < url.length(); i++) {
/*     */       
/* 113 */       if (url.charAt(i) == ' ') {
/* 114 */         buf.append("%20");
/*     */       } else {
/* 116 */         buf.append(url.charAt(i));
/*     */       } 
/* 118 */     }  return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String absolutize(String name) {
/*     */     try {
/* 125 */       URL baseURL = (new File(".")).getCanonicalFile().toURL();
/* 126 */       return (new URL(baseURL, name)).toExternalForm();
/* 127 */     } catch (IOException e) {
/*     */ 
/*     */       
/* 130 */       return name;
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
/*     */   public static void checkAbsoluteness(String systemId) {
/*     */     try {
/* 143 */       new URL(systemId);
/* 144 */     } catch (MalformedURLException mue) {
/*     */       try {
/* 146 */         new URI(systemId);
/* 147 */       } catch (URISyntaxException e) {
/* 148 */         throw new IllegalArgumentException("system ID '" + systemId + "' isn't absolute", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchQNames(QName target, QName pattern) {
/* 159 */     if (target == null || pattern == null)
/*     */     {
/* 161 */       return false;
/*     */     }
/* 163 */     if (pattern.getNamespaceURI().equals(target.getNamespaceURI())) {
/* 164 */       String regex = pattern.getLocalPart().replaceAll("\\*", ".*");
/* 165 */       return Pattern.matches(regex, target.getLocalPart());
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\JAXWSUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */