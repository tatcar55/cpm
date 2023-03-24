/*    */ package com.sun.xml.ws.transport.tcp.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class VersionController
/*    */ {
/* 48 */   private static final VersionController instance = new VersionController(new Version(1, 0), new Version(1, 0));
/*    */   
/*    */   private final Version framingVersion;
/*    */   
/*    */   private final Version connectionManagementVersion;
/*    */ 
/*    */   
/*    */   private VersionController(Version framingVersion, Version connectionManagementVersion) {
/* 56 */     this.framingVersion = framingVersion;
/* 57 */     this.connectionManagementVersion = connectionManagementVersion;
/*    */   }
/*    */   
/*    */   public static VersionController getInstance() {
/* 61 */     return instance;
/*    */   }
/*    */   
/*    */   public Version getFramingVersion() {
/* 65 */     return this.framingVersion;
/*    */   }
/*    */   
/*    */   public Version getConnectionManagementVersion() {
/* 69 */     return this.connectionManagementVersion;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isVersionSupported(Version framingVersion, Version connectionManagementVersion) {
/* 78 */     return (this.framingVersion.equals(framingVersion) && this.connectionManagementVersion.equals(connectionManagementVersion));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Version getClosestSupportedFramingVersion(Version framingVersion) {
/* 86 */     return this.framingVersion;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Version getClosestSupportedConnectionManagementVersion(Version connectionManagementVersion) {
/* 93 */     return this.connectionManagementVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\VersionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */