/*    */ package com.sun.xml.rpc.wsdl.framework;
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
/*    */ public class WSDLLocation
/*    */ {
/*    */   private LocationContext[] contexts;
/*    */   private int idPos;
/*    */   private LocationContext currentContext;
/*    */   
/*    */   WSDLLocation() {
/* 40 */     reset();
/*    */   }
/*    */   
/*    */   public void push() {
/* 44 */     int max = this.contexts.length;
/* 45 */     this.idPos++;
/* 46 */     if (this.idPos >= max) {
/* 47 */       LocationContext[] newContexts = new LocationContext[max * 2];
/* 48 */       System.arraycopy(this.contexts, 0, newContexts, 0, max);
/* 49 */       max *= 2;
/* 50 */       this.contexts = newContexts;
/*    */     } 
/* 52 */     this.currentContext = this.contexts[this.idPos];
/* 53 */     if (this.currentContext == null) {
/* 54 */       this.contexts[this.idPos] = this.currentContext = new LocationContext();
/*    */     }
/* 56 */     if (this.idPos > 0) {
/* 57 */       this.currentContext.setParent(this.contexts[this.idPos - 1]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void pop() {
/* 63 */     this.idPos--;
/* 64 */     if (this.idPos >= 0) {
/* 65 */       this.currentContext = this.contexts[this.idPos];
/*    */     }
/*    */   }
/*    */   
/*    */   public void reset() {
/* 70 */     this.contexts = new LocationContext[32];
/* 71 */     this.idPos = 0;
/* 72 */     this.contexts[this.idPos] = this.currentContext = new LocationContext();
/*    */   }
/*    */   
/*    */   public String getLocation() {
/* 76 */     return this.currentContext.getLocation();
/*    */   }
/*    */   
/*    */   public void setLocation(String loc) {
/* 80 */     this.currentContext.setLocation(loc);
/*    */   }
/*    */   
/*    */   private static class LocationContext {
/*    */     private String location;
/*    */     private LocationContext parentLocation;
/*    */     
/*    */     private LocationContext() {}
/*    */     
/*    */     void setLocation(String loc) {
/* 90 */       this.location = loc;
/*    */     }
/*    */     
/*    */     String getLocation() {
/* 94 */       return this.location;
/*    */     }
/*    */     
/*    */     void setParent(LocationContext parent) {
/* 98 */       this.parentLocation = parent;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\WSDLLocation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */