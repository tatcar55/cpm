/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.WSFeatureList;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLFeaturedObject;
/*    */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ abstract class AbstractFeaturedObjectImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements WSDLFeaturedObject
/*    */ {
/*    */   protected WebServiceFeatureList features;
/*    */   
/*    */   protected AbstractFeaturedObjectImpl(XMLStreamReader xsr) {
/* 56 */     super(xsr);
/*    */   }
/*    */   protected AbstractFeaturedObjectImpl(String systemId, int lineNumber) {
/* 59 */     super(systemId, lineNumber);
/*    */   }
/*    */   
/*    */   public final void addFeature(WebServiceFeature feature) {
/* 63 */     if (this.features == null) {
/* 64 */       this.features = new WebServiceFeatureList();
/*    */     }
/* 66 */     this.features.add(feature);
/*    */   }
/*    */   @NotNull
/*    */   public WebServiceFeatureList getFeatures() {
/* 70 */     if (this.features == null)
/* 71 */       return new WebServiceFeatureList(); 
/* 72 */     return this.features;
/*    */   }
/*    */   
/*    */   public final WebServiceFeature getFeature(String id) {
/* 76 */     if (this.features != null) {
/* 77 */       for (WebServiceFeature f : this.features) {
/* 78 */         if (f.getID().equals(id)) {
/* 79 */           return f;
/*    */         }
/*    */       } 
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType) {
/* 88 */     if (this.features == null) {
/* 89 */       return null;
/*    */     }
/* 91 */     return (F)this.features.get(featureType);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\AbstractFeaturedObjectImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */