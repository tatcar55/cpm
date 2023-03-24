/*    */ package com.sun.xml.ws.api.ha;
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
/*    */ public class HaInfo
/*    */ {
/*    */   private final String replicaInstance;
/*    */   private final String key;
/*    */   private final boolean failOver;
/*    */   
/*    */   public HaInfo(String key, String replicaInstance, boolean failOver) {
/* 83 */     this.key = key;
/* 84 */     this.replicaInstance = replicaInstance;
/* 85 */     this.failOver = failOver;
/*    */   }
/*    */   
/*    */   public String getReplicaInstance() {
/* 89 */     return this.replicaInstance;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 93 */     return this.key;
/*    */   }
/*    */   
/*    */   public boolean isFailOver() {
/* 97 */     return this.failOver;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\ha\HaInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */