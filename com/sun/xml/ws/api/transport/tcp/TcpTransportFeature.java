/*    */ package com.sun.xml.ws.api.transport.tcp;
/*    */ 
/*    */ import com.sun.xml.ws.api.FeatureConstructor;
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
/*    */ public class TcpTransportFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   public static final String ID = "com.sun.xml.ws.transport.TcpTransportFeature";
/*    */   
/*    */   public TcpTransportFeature() {
/* 62 */     this(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @FeatureConstructor({"enabled"})
/*    */   public TcpTransportFeature(boolean enabled) {
/* 72 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 78 */     return "com.sun.xml.ws.transport.TcpTransportFeature";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\transport\tcp\TcpTransportFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */