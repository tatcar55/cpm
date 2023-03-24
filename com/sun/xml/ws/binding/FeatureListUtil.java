/*    */ package com.sun.xml.ws.binding;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public class FeatureListUtil
/*    */ {
/*    */   @NotNull
/*    */   public static WebServiceFeatureList mergeList(WebServiceFeatureList... lists) {
/* 64 */     WebServiceFeatureList result = new WebServiceFeatureList();
/* 65 */     for (WebServiceFeatureList list : lists) {
/* 66 */       result.addAll((Iterable<WebServiceFeature>)list);
/*    */     }
/* 68 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static <F extends WebServiceFeature> F mergeFeature(@NotNull Class<F> featureType, @Nullable WebServiceFeatureList list1, @Nullable WebServiceFeatureList list2) throws WebServiceException {
/* 74 */     F feature1 = (list1 != null) ? list1.<F>get(featureType) : null;
/* 75 */     F feature2 = (list2 != null) ? list2.<F>get(featureType) : null;
/* 76 */     if (feature1 == null) {
/* 77 */       return feature2;
/*    */     }
/* 79 */     if (feature2 == null) {
/* 80 */       return feature1;
/*    */     }
/*    */     
/* 83 */     if (feature1.equals(feature2)) {
/* 84 */       return feature1;
/*    */     }
/*    */ 
/*    */     
/* 88 */     throw new WebServiceException((new StringBuilder()).append(feature1).append(", ").append(feature2).toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> featureType, @Nullable WebServiceFeatureList list1, @Nullable WebServiceFeatureList list2) throws WebServiceException {
/* 96 */     WebServiceFeature mergedFeature = mergeFeature((Class)featureType, list1, list2);
/* 97 */     return (mergedFeature != null && mergedFeature.isEnabled());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\binding\FeatureListUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */