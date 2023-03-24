/*    */ package com.sun.xml.ws.runtime.config;
/*    */ 
/*    */ import com.sun.xml.ws.api.FeatureConstructor;
/*    */ import java.util.List;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import org.glassfish.gmbal.ManagedAttribute;
/*    */ import org.glassfish.gmbal.ManagedData;
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
/*    */ @ManagedData
/*    */ public class TubelineFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "com.sun.xml.ws.runtime.config.TubelineFeature";
/*    */   
/*    */   @FeatureConstructor({"enabled"})
/*    */   public TubelineFeature(boolean enabled) {
/* 64 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 70 */     return "com.sun.xml.ws.runtime.config.TubelineFeature";
/*    */   }
/*    */ 
/*    */   
/*    */   List<String> getTubeFactories() {
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\runtime\config\TubelineFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */