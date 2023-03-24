/*    */ package org.glassfish.external.probe.provider;
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
/*    */ 
/*    */ public enum PluginPoint
/*    */ {
/* 49 */   SERVER("server", "server"),
/* 50 */   APPLICATIONS("applications", "server/applications");
/*    */   
/*    */   String name;
/*    */   String path;
/*    */   
/*    */   PluginPoint(String lname, String lpath) {
/* 56 */     this.name = lname;
/* 57 */     this.path = lpath;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 61 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 65 */     return this.path;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\probe\provider\PluginPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */