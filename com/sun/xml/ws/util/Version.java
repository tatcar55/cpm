/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Version
/*     */ {
/*     */   public final String BUILD_ID;
/*     */   public final String BUILD_VERSION;
/*     */   public final String MAJOR_VERSION;
/*     */   public final String SVN_REVISION;
/*  74 */   public static final Version RUNTIME_VERSION = create(Version.class.getResourceAsStream("version.properties"));
/*     */   
/*     */   private Version(String buildId, String buildVersion, String majorVersion, String svnRev) {
/*  77 */     this.BUILD_ID = fixNull(buildId);
/*  78 */     this.BUILD_VERSION = fixNull(buildVersion);
/*  79 */     this.MAJOR_VERSION = fixNull(majorVersion);
/*  80 */     this.SVN_REVISION = fixNull(svnRev);
/*     */   }
/*     */   
/*     */   public static Version create(InputStream is) {
/*  84 */     Properties props = new Properties();
/*     */     try {
/*  86 */       props.load(is);
/*  87 */     } catch (IOException e) {
/*     */     
/*  89 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/*  93 */     return new Version(props.getProperty("build-id"), props.getProperty("build-version"), props.getProperty("major-version"), props.getProperty("svn-revision"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String fixNull(String v) {
/* 101 */     if (v == null) return "unknown"; 
/* 102 */     return v;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 106 */     return this.BUILD_VERSION + " svn-revision#" + this.SVN_REVISION;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */