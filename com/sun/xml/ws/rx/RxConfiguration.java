/*    */ package com.sun.xml.ws.rx;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import org.glassfish.gmbal.ManagedObjectManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface RxConfiguration
/*    */ {
/* 56 */   public static final String ACK_REQUESTED_HEADER_SET = RxConfiguration.class.getName() + ".ACK_REQUESTED_HEADER_SET";
/*    */   
/*    */   boolean isReliableMessagingEnabled();
/*    */   
/*    */   boolean isMakeConnectionSupportEnabled();
/*    */   
/*    */   SOAPVersion getSoapVersion();
/*    */   
/*    */   AddressingVersion getAddressingVersion();
/*    */   
/*    */   boolean requestResponseOperationsDetected();
/*    */   
/*    */   ManagedObjectManager getManagedObjectManager();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\RxConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */