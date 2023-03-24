/*    */ package com.oracle.webservices.api.databinding;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class DatabindingModeFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "http://jax-ws.java.net/features/databinding";
/*    */   public static final String GLASSFISH_JAXB = "glassfish.jaxb";
/*    */   private String mode;
/*    */   private Map<String, Object> properties;
/*    */   
/*    */   public DatabindingModeFeature(String mode) {
/* 67 */     this.mode = mode;
/* 68 */     this.properties = new HashMap<String, Object>();
/*    */   }
/*    */   
/*    */   public String getMode() {
/* 72 */     return this.mode;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 76 */     return "http://jax-ws.java.net/features/databinding";
/*    */   }
/*    */   
/*    */   public Map<String, Object> getProperties() {
/* 80 */     return this.properties;
/*    */   }
/*    */   public static Builder builder() {
/* 83 */     return new Builder(new DatabindingModeFeature(null));
/*    */   }
/*    */   
/*    */   public static final class Builder {
/* 87 */     Builder(DatabindingModeFeature x) { this.o = x; } private final DatabindingModeFeature o; public DatabindingModeFeature build() {
/* 88 */       return this.o;
/*    */     } public Builder value(String x) {
/* 90 */       this.o.mode = x; return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\databinding\DatabindingModeFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */