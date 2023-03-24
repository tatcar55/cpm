/*    */ package com.sun.xml.ws.api;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentsFeature
/*    */   extends WebServiceFeature
/*    */   implements ServiceSharedFeatureMarker
/*    */ {
/*    */   private final List<ComponentFeature> componentFeatures;
/*    */   
/*    */   public ComponentsFeature(List<ComponentFeature> componentFeatures) {
/* 73 */     this.enabled = true;
/* 74 */     this.componentFeatures = componentFeatures;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getID() {
/* 79 */     return ComponentsFeature.class.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ComponentFeature> getComponentFeatures() {
/* 87 */     return this.componentFeatures;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\ComponentsFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */