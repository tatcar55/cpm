/*    */ package com.oracle.webservices.api;
/*    */ 
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
/*    */ public class EnvelopeStyleFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   private EnvelopeStyle.Style[] styles;
/*    */   
/*    */   public EnvelopeStyleFeature(EnvelopeStyle.Style... s) {
/* 50 */     this.styles = s;
/*    */   }
/*    */   
/*    */   public EnvelopeStyle.Style[] getStyles() {
/* 54 */     return this.styles;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 58 */     return EnvelopeStyleFeature.class.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\EnvelopeStyleFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */