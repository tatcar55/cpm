/*     */ package org.sqlite;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.DigestInputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Properties;
/*     */ import java.util.UUID;
/*     */ import org.sqlite.util.OSInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SQLiteJDBCLoader
/*     */ {
/*     */   private static boolean extracted = false;
/*     */   
/*     */   public static synchronized boolean initialize() throws Exception {
/*  61 */     if (!extracted) {
/*  62 */       cleanup();
/*     */     }
/*  64 */     loadSQLiteNativeLibrary();
/*  65 */     return extracted;
/*     */   }
/*     */   
/*     */   private static File getTempDir() {
/*  69 */     return new File(System.getProperty("org.sqlite.tmpdir", System.getProperty("java.io.tmpdir")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void cleanup() {
/*  77 */     String tempFolder = getTempDir().getAbsolutePath();
/*  78 */     File dir = new File(tempFolder);
/*     */     
/*  80 */     File[] nativeLibFiles = dir.listFiles(new FilenameFilter() {
/*  81 */           private final String searchPattern = "sqlite-" + SQLiteJDBCLoader.getVersion();
/*     */           public boolean accept(File dir, String name) {
/*  83 */             return (name.startsWith(this.searchPattern) && !name.endsWith(".lck"));
/*     */           }
/*     */         });
/*  86 */     if (nativeLibFiles != null) {
/*  87 */       for (File nativeLibFile : nativeLibFiles) {
/*  88 */         File lckFile = new File(nativeLibFile.getAbsolutePath() + ".lck");
/*  89 */         if (!lckFile.exists()) {
/*     */           try {
/*  91 */             nativeLibFile.delete();
/*     */           }
/*  93 */           catch (SecurityException e) {
/*  94 */             System.err.println("Failed to delete old native lib" + e.getMessage());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   static boolean getPureJavaFlag() {
/* 108 */     return Boolean.parseBoolean(System.getProperty("sqlite.purejava", "false"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean isPureJavaMode() {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNativeMode() throws Exception {
/* 129 */     initialize();
/* 130 */     return extracted;
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
/*     */   static String md5sum(InputStream input) throws IOException {
/* 142 */     BufferedInputStream in = new BufferedInputStream(input);
/*     */     
/*     */     try {
/* 145 */       MessageDigest digest = MessageDigest.getInstance("MD5");
/* 146 */       DigestInputStream digestInputStream = new DigestInputStream(in, digest);
/* 147 */       while (digestInputStream.read() >= 0);
/*     */ 
/*     */       
/* 150 */       ByteArrayOutputStream md5out = new ByteArrayOutputStream();
/* 151 */       md5out.write(digest.digest());
/* 152 */       return md5out.toString();
/*     */     }
/* 154 */     catch (NoSuchAlgorithmException e) {
/* 155 */       throw new IllegalStateException("MD5 algorithm is not available: " + e);
/*     */     } finally {
/*     */       
/* 158 */       in.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean contentsEquals(InputStream in1, InputStream in2) throws IOException {
/* 163 */     if (!(in1 instanceof BufferedInputStream)) {
/* 164 */       in1 = new BufferedInputStream(in1);
/*     */     }
/* 166 */     if (!(in2 instanceof BufferedInputStream)) {
/* 167 */       in2 = new BufferedInputStream(in2);
/*     */     }
/*     */     
/* 170 */     int ch = in1.read();
/* 171 */     while (ch != -1) {
/* 172 */       int i = in2.read();
/* 173 */       if (ch != i) {
/* 174 */         return false;
/*     */       }
/* 176 */       ch = in1.read();
/*     */     } 
/* 178 */     int ch2 = in2.read();
/* 179 */     return (ch2 == -1);
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
/*     */   private static boolean extractAndLoadLibraryFile(String libFolderForCurrentOS, String libraryFileName, String targetFolder) {
/* 192 */     String nativeLibraryFilePath = libFolderForCurrentOS + "/" + libraryFileName;
/*     */ 
/*     */     
/* 195 */     String uuid = UUID.randomUUID().toString();
/* 196 */     String extractedLibFileName = String.format("sqlite-%s-%s-%s", new Object[] { getVersion(), uuid, libraryFileName });
/* 197 */     String extractedLckFileName = extractedLibFileName + ".lck";
/*     */     
/* 199 */     File extractedLibFile = new File(targetFolder, extractedLibFileName);
/* 200 */     File extractedLckFile = new File(targetFolder, extractedLckFileName);
/*     */ 
/*     */     
/*     */     try {
/* 204 */       InputStream reader = SQLiteJDBCLoader.class.getResourceAsStream(nativeLibraryFilePath);
/* 205 */       if (!extractedLckFile.exists()) {
/* 206 */         (new FileOutputStream(extractedLckFile)).close();
/*     */       }
/* 208 */       FileOutputStream writer = new FileOutputStream(extractedLibFile);
/*     */       try {
/* 210 */         byte[] buffer = new byte[8192];
/* 211 */         int bytesRead = 0;
/* 212 */         while ((bytesRead = reader.read(buffer)) != -1) {
/* 213 */           writer.write(buffer, 0, bytesRead);
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 218 */         extractedLibFile.deleteOnExit();
/* 219 */         extractedLckFile.deleteOnExit();
/*     */ 
/*     */         
/* 222 */         if (writer != null) {
/* 223 */           writer.close();
/*     */         }
/* 225 */         if (reader != null) {
/* 226 */           reader.close();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 231 */       extractedLibFile.setReadable(true);
/* 232 */       extractedLibFile.setWritable(true, true);
/* 233 */       extractedLibFile.setExecutable(true);
/*     */ 
/*     */ 
/*     */       
/* 237 */       InputStream nativeIn = SQLiteJDBCLoader.class.getResourceAsStream(nativeLibraryFilePath);
/* 238 */       InputStream extractedLibIn = new FileInputStream(extractedLibFile);
/*     */       try {
/* 240 */         if (!contentsEquals(nativeIn, extractedLibIn)) {
/* 241 */           throw new RuntimeException(String.format("Failed to write a native library file at %s", new Object[] { extractedLibFile }));
/*     */         }
/*     */       } finally {
/*     */         
/* 245 */         if (nativeIn != null) {
/* 246 */           nativeIn.close();
/*     */         }
/* 248 */         if (extractedLibIn != null) {
/* 249 */           extractedLibIn.close();
/*     */         }
/*     */       } 
/*     */       
/* 253 */       return loadNativeLibrary(targetFolder, extractedLibFileName);
/*     */     }
/* 255 */     catch (IOException e) {
/* 256 */       System.err.println(e.getMessage());
/* 257 */       return false;
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
/*     */   
/*     */   private static boolean loadNativeLibrary(String path, String name) {
/* 270 */     File libPath = new File(path, name);
/* 271 */     if (libPath.exists()) {
/*     */       
/*     */       try {
/* 274 */         System.load((new File(path, name)).getAbsolutePath());
/* 275 */         return true;
/*     */       }
/* 277 */       catch (UnsatisfiedLinkError e) {
/* 278 */         System.err.println("Failed to load native library:" + name + ". osinfo: " + OSInfo.getNativeLibFolderPathForCurrentOS());
/* 279 */         System.err.println(e);
/* 280 */         return false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 285 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadSQLiteNativeLibrary() throws Exception {
/* 295 */     if (extracted) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 300 */     String sqliteNativeLibraryPath = System.getProperty("org.sqlite.lib.path");
/* 301 */     String sqliteNativeLibraryName = System.getProperty("org.sqlite.lib.name");
/* 302 */     if (sqliteNativeLibraryName == null) {
/* 303 */       sqliteNativeLibraryName = System.mapLibraryName("sqlitejdbc");
/* 304 */       if (sqliteNativeLibraryName != null && sqliteNativeLibraryName.endsWith(".dylib")) {
/* 305 */         sqliteNativeLibraryName = sqliteNativeLibraryName.replace(".dylib", ".jnilib");
/*     */       }
/*     */     } 
/*     */     
/* 309 */     if (sqliteNativeLibraryPath != null && 
/* 310 */       loadNativeLibrary(sqliteNativeLibraryPath, sqliteNativeLibraryName)) {
/* 311 */       extracted = true;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 317 */     String packagePath = SQLiteJDBCLoader.class.getPackage().getName().replaceAll("\\.", "/");
/* 318 */     sqliteNativeLibraryPath = String.format("/%s/native/%s", new Object[] { packagePath, OSInfo.getNativeLibFolderPathForCurrentOS() });
/* 319 */     boolean hasNativeLib = hasResource(sqliteNativeLibraryPath + "/" + sqliteNativeLibraryName);
/*     */ 
/*     */     
/* 322 */     if (!hasNativeLib && 
/* 323 */       OSInfo.getOSName().equals("Mac")) {
/*     */       
/* 325 */       String altName = "libsqlitejdbc.jnilib";
/* 326 */       if (hasResource(sqliteNativeLibraryPath + "/" + altName)) {
/* 327 */         sqliteNativeLibraryName = altName;
/* 328 */         hasNativeLib = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 333 */     if (!hasNativeLib) {
/* 334 */       extracted = false;
/* 335 */       throw new Exception(String.format("No native library is found for os.name=%s and os.arch=%s. path=%s", new Object[] { OSInfo.getOSName(), OSInfo.getArchName(), sqliteNativeLibraryPath }));
/*     */     } 
/*     */ 
/*     */     
/* 339 */     String tempFolder = getTempDir().getAbsolutePath();
/*     */     
/* 341 */     if (extractAndLoadLibraryFile(sqliteNativeLibraryPath, sqliteNativeLibraryName, tempFolder)) {
/* 342 */       extracted = true;
/*     */       
/*     */       return;
/*     */     } 
/* 346 */     extracted = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasResource(String path) {
/* 351 */     return (SQLiteJDBCLoader.class.getResource(path) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getNativeLibraryFolderForTheCurrentOS() {
/* 357 */     String osName = OSInfo.getOSName();
/* 358 */     String archName = OSInfo.getArchName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMajorVersion() {
/* 365 */     String[] c = getVersion().split("\\.");
/* 366 */     return (c.length > 0) ? Integer.parseInt(c[0]) : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMinorVersion() {
/* 373 */     String[] c = getVersion().split("\\.");
/* 374 */     return (c.length > 1) ? Integer.parseInt(c[1]) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 382 */     URL versionFile = SQLiteJDBCLoader.class.getResource("/META-INF/maven/io.github.willena/sqlite-jdbc/pom.properties");
/* 383 */     if (versionFile == null) {
/* 384 */       versionFile = SQLiteJDBCLoader.class.getResource("/META-INF/maven/io.github.willena/sqlite-jdbc/VERSION");
/*     */     }
/*     */     
/* 387 */     String version = "unknown";
/*     */     try {
/* 389 */       if (versionFile != null) {
/* 390 */         Properties versionData = new Properties();
/* 391 */         versionData.load(versionFile.openStream());
/* 392 */         version = versionData.getProperty("version", version);
/* 393 */         version = version.trim().replaceAll("[^0-9\\.]", "");
/*     */       } else {
/*     */         
/* 396 */         throw new FileNotFoundException("Version file is null");
/*     */       }
/*     */     
/* 399 */     } catch (IOException e) {
/* 400 */       System.err.println(e);
/*     */     } 
/* 402 */     return version;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\SQLiteJDBCLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */