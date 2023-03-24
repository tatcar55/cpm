/*    */ package com.sun.xml.ws.api;
/*    */ 
/*    */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*    */ import java.lang.annotation.Annotation;
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
/*    */ public class WebServiceFeatureFactory
/*    */ {
/*    */   public static WSFeatureList getWSFeatureList(Iterable<Annotation> ann) {
/* 64 */     WebServiceFeatureList list = new WebServiceFeatureList();
/* 65 */     list.parseAnnotations(ann);
/* 66 */     return (WSFeatureList)list;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static WebServiceFeature getWebServiceFeature(Annotation ann) {
/* 78 */     return WebServiceFeatureList.getFeature(ann);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\WebServiceFeatureFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */