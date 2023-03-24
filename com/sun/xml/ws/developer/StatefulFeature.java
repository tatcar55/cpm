/*    */ package com.sun.xml.ws.developer;
/*    */ 
/*    */ import com.sun.xml.ws.api.ha.StickyFeature;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/*    */ public class StatefulFeature
/*    */   extends WebServiceFeature
/*    */   implements StickyFeature
/*    */ {
/*    */   public static final String ID = "http://jax-ws.dev.java.net/features/stateful";
/*    */   
/*    */   @ManagedAttribute
/*    */   public String getID() {
/* 78 */     return "http://jax-ws.dev.java.net/features/stateful";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\StatefulFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */