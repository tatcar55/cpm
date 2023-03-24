/*     */ package com.sun.xml.ws.transport.tcp.server;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
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
/*     */ public final class TCPStandaloneContext
/*     */   implements TCPContext
/*     */ {
/*     */   private final ClassLoader classloader;
/*  64 */   private final Map<String, Object> attributes = new HashMap<String, Object>();
/*     */   
/*     */   public TCPStandaloneContext(ClassLoader classloader) {
/*  67 */     this.classloader = classloader;
/*     */   }
/*     */   
/*     */   public InputStream getResourceAsStream(String resource) throws IOException {
/*  71 */     return this.classloader.getResourceAsStream(resource);
/*     */   }
/*     */   
/*     */   public Set<String> getResourcePaths(String path) {
/*     */     try {
/*  76 */       return populateResourcePaths(path);
/*  77 */     } catch (Exception ex) {
/*     */ 
/*     */       
/*  80 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */   
/*     */   public URL getResource(String resource) {
/*  85 */     if (resource.charAt(0) == '/') {
/*  86 */       resource = resource.substring(1, resource.length());
/*     */     }
/*     */     
/*  89 */     return this.classloader.getResource(resource);
/*     */   }
/*     */   
/*     */   private Enumeration<URL> getResources(String resource) throws IOException {
/*  93 */     if (resource.charAt(0) == '/') {
/*  94 */       resource = resource.substring(1, resource.length());
/*     */     }
/*     */     
/*  97 */     return this.classloader.getResources(resource);
/*     */   }
/*     */   
/*     */   private Set<String> populateResourcePaths(String path) throws Exception {
/* 101 */     Set<String> resources = new HashSet<String>();
/*     */     
/* 103 */     for (Enumeration<URL> initResources = getResources(path); initResources.hasMoreElements(); ) {
/* 104 */       URI resourceURI = ((URL)initResources.nextElement()).toURI();
/* 105 */       if (resourceURI.getScheme().equals("file")) {
/* 106 */         gatherResourcesWithFileMode(path, resourceURI, resources); continue;
/* 107 */       }  if (resourceURI.getScheme().equals("jar")) {
/* 108 */         gatherResourcesWithJarMode(path, resourceURI, resources);
/*     */       }
/*     */     } 
/*     */     
/* 112 */     return resources;
/*     */   }
/*     */   
/*     */   private void gatherResourcesWithFileMode(String path, URI resourceURI, Set<String> resources) {
/* 116 */     File file = new File(resourceURI);
/* 117 */     String[] list = file.list(new FilenameFilter() {
/*     */           public boolean accept(File file, String name) {
/* 119 */             return (name.charAt(0) != '.');
/*     */           }
/*     */         });
/*     */     
/* 123 */     for (String filename : list) {
/* 124 */       resources.add(path + filename);
/*     */     }
/*     */   }
/*     */   
/*     */   private void gatherResourcesWithJarMode(String path, URI resourceURI, Set<String> resources) {
/* 129 */     String resourceURIAsString = resourceURI.toASCIIString();
/* 130 */     int pathDelim = resourceURIAsString.indexOf('!');
/* 131 */     String zipFile = resourceURIAsString.substring("jar:file:/".length(), (pathDelim != -1) ? pathDelim : resourceURIAsString.length());
/* 132 */     ZipFile file = null;
/*     */ 
/*     */     
/* 135 */     try { file = new ZipFile(zipFile);
/*     */       
/* 137 */       String pathToCompare = path;
/* 138 */       if (pathToCompare.charAt(0) == '/') {
/* 139 */         pathToCompare = pathToCompare.substring(1, pathToCompare.length());
/*     */       }
/* 141 */       if (!pathToCompare.endsWith("/")) {
/* 142 */         pathToCompare = pathToCompare + "/";
/*     */       }
/*     */       
/* 145 */       for (Enumeration<? extends ZipEntry> e = file.entries(); e.hasMoreElements(); ) {
/* 146 */         ZipEntry entry = e.nextElement();
/* 147 */         if (entry.getName().startsWith(pathToCompare) && !entry.getName().equals(pathToCompare)) {
/* 148 */           resources.add("/" + entry.getName());
/*     */         }
/*     */       }  }
/* 151 */     catch (IOException e) {  }
/*     */     finally
/* 153 */     { if (file != null) {
/*     */         try {
/* 155 */           file.close();
/* 156 */         } catch (IOException ex) {}
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttribute(String name) {
/* 163 */     return this.attributes.get(name);
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, Object value) {
/* 167 */     this.attributes.put(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPStandaloneContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */