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
/*    */ public final class Version
/*    */ {
/*    */   private final int major;
/*    */   private final int minor;
/*    */   
/*    */   public Version(int major, int minor) {
/* 51 */     this.major = major;
/* 52 */     this.minor = minor;
/*    */   }
/*    */   
/*    */   public int getMajor() {
/* 56 */     return this.major;
/*    */   }
/*    */   
/*    */   public int getMinor() {
/* 60 */     return this.minor;
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 64 */     if (object instanceof Version) {
/* 65 */       Version version = (Version)object;
/* 66 */       return (this.major == version.major && this.minor == version.minor);
/*    */     } 
/*    */     
/* 69 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 73 */     return this.major << 16 | this.minor;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 77 */     return this.major + "." + this.minor;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */