/*     */ package com.sun.xml.ws.api.transport.tcp;
/*     */ 
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public class SelectOptimalTransportFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "com.sun.xml.ws.transport.SelectOptimalTransportFeature";
/*     */   private Transport transport;
/*     */   
/*     */   public enum Transport
/*     */   {
/*  68 */     TCP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Transport getDefault() {
/*  76 */       return TCP;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectOptimalTransportFeature() {
/*  87 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"enabled"})
/*     */   public SelectOptimalTransportFeature(boolean enabled) {
/*  97 */     this(enabled, Transport.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"enabled", "transport"})
/*     */   public SelectOptimalTransportFeature(boolean enabled, Transport transport) {
/* 105 */     this.enabled = enabled;
/* 106 */     this.transport = transport;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 113 */     return "com.sun.xml.ws.transport.SelectOptimalTransportFeature";
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public Transport getTransport() {
/* 118 */     return this.transport;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\transport\tcp\SelectOptimalTransportFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */