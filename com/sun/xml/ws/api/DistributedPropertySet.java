/*    */ package com.sun.xml.ws.api;
/*    */ 
/*    */ import com.oracle.webservices.api.message.BaseDistributedPropertySet;
/*    */ import com.oracle.webservices.api.message.PropertySet;
/*    */ import com.sun.istack.NotNull;
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
/*    */ public abstract class DistributedPropertySet
/*    */   extends BaseDistributedPropertySet
/*    */ {
/*    */   public void addSatellite(@NotNull PropertySet satellite) {
/* 57 */     addSatellite((PropertySet)satellite);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSatellite(@NotNull Class keyClass, @NotNull PropertySet satellite) {
/* 64 */     addSatellite(keyClass, (PropertySet)satellite);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 71 */     copySatelliteInto((com.oracle.webservices.api.message.DistributedPropertySet)r);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeSatellite(PropertySet satellite) {
/* 78 */     removeSatellite((PropertySet)satellite);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\DistributedPropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */